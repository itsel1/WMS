package com.example.temp.api.shop.shopee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.temp.api.shop.shopee.ShopeeDTO;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.api.shop.shopee.ApiCall;
import com.google.gson.Gson;
import com.itextpdf.text.log.SysoCounter;

@Service
@Component
public class HandleShopeeService {
	
	@Autowired
	ShippingMapper shippingMapper;

	ApiCall apiCall = new ApiCall();
	private final long PARTNER_ID = ApiCall.PARTNER_ID;
	
	public int selectShopeeInfoCnt(HashMap<String, Object> sqlParams) {
		return shippingMapper.selectShopeeInfoCnt(sqlParams);
	}

	public ArrayList<ShopeeDTO> selectShopeeInfoList(HashMap<String, Object> parameters) {
		return shippingMapper.selectShopeeInfoList(parameters);
	}

	public void insertShopeeInfo(ShopeeDTO sqlParams) {
		shippingMapper.insertShopeeInfo(sqlParams);
	}
	
	public void updateShopeeRefreshToken(ShopeeDTO sqlParams) {
		shippingMapper.updateShopeeRefreshToken(sqlParams);
	}

	public void updateShopeeUseYn(ShopeeDTO sqlParams) {
		shippingMapper.updateShopeeUseYn(sqlParams);
	}

	public void insertShopeeHistory(ShopeeDTO sqlParams) {
		shippingMapper.insertShopeeHistory(sqlParams);
	}

	public void deleteShopeeList(ShopeeDTO sqlParams) {
		shippingMapper.deleteShopeeList(sqlParams);
	}

	private LinkedHashMap<String, Object> buildApiHeaders() {
		LinkedHashMap<String, Object> apiHeaders = new LinkedHashMap<>();
		apiHeaders.put("Content-Type", "application/json");
		apiHeaders.put("Accept", "application/json");
		return apiHeaders;
	}
	
	private long convertDateToTimestamp(String timeString) {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(timeString);
			String dateString = sdf.format(date);
			long timestamp = sdf.parse(dateString).getTime();
			timestamp = timestamp / 1000L;
			return timestamp;
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}

	public HashMap<String, Object> linkShopeeAuthPage(HashMap<String, Object> parameters) {
		HashMap<String, Object> returnMap = new HashMap<>();
		String userId = (String) parameters.get("userId");
		
		String path = "/api/v2/shop/auth_partner";
		String redirectUrl = "http://wms.acieshop.com/api/"+userId+"/shopeeAuth";
		String requestParameters = "redirect="+redirectUrl;
		String sign;
		String httpUrl;
		long timestamp = System.currentTimeMillis() / 1000L;
		
		sign = apiCall.makeSignature(path, timestamp);
		httpUrl = apiCall.makeHttpUrl(path, timestamp, sign, requestParameters);
		
		if (!httpUrl.isEmpty()) {
			returnMap.put("status", "success");
			returnMap.put("url", httpUrl);
			
			return returnMap;
		} else {
			returnMap.put("status", "fail");
			returnMap.put("url", "");
			
			return returnMap;
		}
	}
	
	// shopee 인증 승인 후 토큰 키 최초 생성
	public HashMap<String, Object> createShopeeToken(String userId, HttpServletRequest request) {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		
		String path = "/api/v2/auth/token/get";
		String sign;
		String code;
		String httpUrl;
		String idKey = null;
		long id = 0;
		long timestamp = System.currentTimeMillis() / 1000L;
		boolean merchantYn = false;
		
		if (request.getParameter("code") == null || request.getParameter("code") == "") {
			throw new NullPointerException("Failed to grant authorization");
		}
		
		code = request.getParameter("code");
		
		// main 계정승인
		if (request.getParameter("main_account_id") != null && request.getParameter("main_account_id") != "") {
			merchantYn = true;
			id = Long.valueOf(request.getParameter("main_account_id"));
			idKey = "main_account_id";
		}
		
		// shop 계정 승인
		if (request.getParameter("shop_id") != null && request.getParameter("shop_id") != "") {
			merchantYn = false;
			id = Long.valueOf(request.getParameter("shop_id"));
			idKey = "shop_id";
		}
		
		sign = apiCall.makeSignature(path, timestamp);
		httpUrl = apiCall.makeHttpUrl(path, timestamp, sign, null);
		
		HashMap<String, Object> requestMap = new HashMap<>();
		requestMap.put("code", code);
		requestMap.put("partner_id", PARTNER_ID);
		requestMap.put(idKey, id);
		
		Gson gson = new Gson();
		String requestBody = gson.toJson(requestMap);
		
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiPost(httpUrl, requestBody, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String error = jsonObject.optString("error");
			String errorMessage = jsonObject.optString("message");

			if (!error.isEmpty()) {
				
				returnMap.put("status", "fail");
				returnMap.put("msg", errorMessage);
				returnMap.put("shopeeData", null);
				return returnMap;
				
			} else {
				
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("error_msg", "");

				String accessToken = jsonObject.getString("access_token");
				String refreshToken = jsonObject.getString("refresh_token");
				
				ArrayList<ShopeeDTO> shopeeDataList = new ArrayList<>();
				
				if (merchantYn) {
					
					JSONArray merchantIdList = jsonObject.getJSONArray("merchant_id_list");
					JSONArray shopIdList = jsonObject.getJSONArray("shop_id_list");

					for (int mi = 0; mi < merchantIdList.length(); mi++) {
						ShopeeDTO shopeeDataOne = new ShopeeDTO();
						long merchantId = 0;
						Object mid = merchantIdList.get(mi);
						if (mid instanceof Long) {
							merchantId = (Long) mid;
						} else if (mid instanceof Integer) {
							merchantId = ((Integer) mid).longValue();
						} else {
							merchantId = Long.valueOf((String) mid);
						}
						
						shopeeDataOne.setShopeeId(merchantId);
						shopeeDataOne.setUserId(userId);
						shopeeDataOne.setMerchantYn("Y");
						shopeeDataOne.setRefreshToken(refreshToken);
						shopeeDataOne.setAccessToken(accessToken);
						
						shopeeDataList.add(shopeeDataOne);
					}
					
					for (int si = 0; si < shopIdList.length(); si++) {
						ShopeeDTO shopeeDataOne = new ShopeeDTO();
						long shopId = 0;
						Object sid = shopIdList.get(si);
						if (sid instanceof Long) {
							shopId = (Long) sid;
						} else if (sid instanceof Integer) {
							shopId = ((Integer) sid).longValue();
						} else {
							shopId = Long.valueOf((String) sid);
						}
						
						shopeeDataOne.setShopeeId(shopId);
						shopeeDataOne.setUserId(userId);
						shopeeDataOne.setMerchantYn("N");
						shopeeDataOne.setRefreshToken(refreshToken);
						shopeeDataOne.setAccessToken(accessToken);
						
						shopeeDataList.add(shopeeDataOne);
					}


				} else {
					ShopeeDTO shopeeDataOne = new ShopeeDTO();

					shopeeDataOne.setShopeeId(id);
					shopeeDataOne.setUserId(userId);
					shopeeDataOne.setMerchantYn("N");
					shopeeDataOne.setRefreshToken(refreshToken);
					shopeeDataOne.setAccessToken(accessToken);

					shopeeDataList.add(shopeeDataOne);
				}
				
				returnMap.put("status", "success");
				returnMap.put("msg", "");
				returnMap.put("shopeeData", shopeeDataList);
				return returnMap;
			}
			
			
		} else {
			
			String errorMessage = responseMap.get("status_msg");
			returnMap.put("status", "fail");
			returnMap.put("msg", errorMessage);
			returnMap.put("shopeeData", null);
			return returnMap;
		}
		
	}
	
	public ArrayList<ShopeeDTO> getShopeeAccessTokenList(ArrayList<ShopeeDTO> shopeeParameters) {
		ArrayList<ShopeeDTO> shopeeInfoList = new ArrayList<>();
		
		for (int si = 0; si < shopeeParameters.size(); si++) {

			ShopeeDTO shopeeDto = shopeeParameters.get(si);
			shopeeDto = getShopeeAccessToken(shopeeDto);
			shopeeInfoList.add(shopeeDto);

			if (!shopeeDto.getErrorMsg().isEmpty()) {
				System.err.println("Error updating access token for ShopeeDTO at index " + si + ": " + shopeeDto.getErrorMsg());
			}
		}
		
		return shopeeInfoList;
	}
	
	public ShopeeDTO getShopeeAccessToken(ShopeeDTO shopeeParameter) {
		ShopeeDTO shopeeInfo = (ShopeeDTO) shopeeParameter;
		
		String path = "/api/v2/auth/access_token/get";
		String sign;
		String httpUrl;
		String idKey = "shop_id";
		String refreshToken = shopeeParameter.getRefreshToken();
		String merchantYn = shopeeParameter.getMerchantYn().toUpperCase();
		long id = shopeeParameter.getShopeeId();
		long timestamp = System.currentTimeMillis() / 1000L;
		
		
		if (merchantYn.equals("Y")) {
			idKey = "merchant_id";
		} else {
			idKey = "shop_id";
		}
		
		HashMap<String, Object> requestMap = new HashMap<>();
		requestMap.put("refresh_token", refreshToken);
		requestMap.put("partner_id", PARTNER_ID);
		requestMap.put(idKey, id);

		Gson gson = new Gson();
		String requestBody = gson.toJson(requestMap);
		
		sign = apiCall.makeSignature(path, timestamp);
		httpUrl = apiCall.makeHttpUrl(path, timestamp, sign, null);
		
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiPost(httpUrl, requestBody, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			String error = jsonObject.optString("error", "");
			String errorMessage = jsonObject.optString("message", "");

			if (!error.isEmpty()) {
				shopeeInfo.setAccessToken(shopeeInfo.getAccessToken());
				shopeeInfo.setRefreshToken(shopeeInfo.getRefreshToken());
				shopeeInfo.setErrorMsg(errorMessage);
			} else {
				shopeeInfo.setAccessToken(jsonObject.getString("access_token"));
				shopeeInfo.setErrorMsg("");
				
				if (!jsonObject.getString("refresh_token").equals(refreshToken)) {
					shopeeInfo.setRefreshToken(jsonObject.getString("refresh_token"));
					shippingMapper.updateShopeeRefreshToken(shopeeInfo);
				}
			}

		} else {
			shopeeInfo.setAccessToken(shopeeInfo.getAccessToken());
			shopeeInfo.setRefreshToken(shopeeInfo.getRefreshToken());
			shopeeInfo.setErrorMsg((String)responseMap.get("status_msg"));
		}
		
		return shopeeInfo;
	}
	
	// shopee auth 인증 만료일 조회
	public ShopeeDTO getShopeeAuthExpiresDate(ShopeeDTO shopeeParameters) {

		String shopName = "";
		String shopRegion = "";
		String expiresTime = "";
		String path = "";
		String sign;
		String httpUrl;
		long id = shopeeParameters.getShopeeId();
		long timestamp = System.currentTimeMillis() / 1000L;
		
		String access_token = shopeeParameters.getAccessToken();
		String merchantYn = shopeeParameters.getMerchantYn().toUpperCase();
		boolean merchantYnBool = false;
		
		if (merchantYn.equals("Y")) {
			path = "/api/v2/merchant/get_merchant_info";
			merchantYnBool = true;
		} else {
			path = "/api/v2/shop/get_shop_info";
			merchantYnBool = false;
		}
		
		
		
		sign = apiCall.makeSignature(path, timestamp, access_token, id);
		httpUrl = apiCall.makeHttpUrl(path, timestamp, id, access_token, sign, null, merchantYnBool);

		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			
			String error = jsonObject.optString("error");
			String errorMessage = jsonObject.optString("message");

			if (!error.isEmpty()) {
				shopeeParameters.setErrorMsg(errorMessage);
			} else {
				
				long authExpiresTime = 0L;
				
				if (merchantYnBool) {
					shopName = jsonObject.getString("merchant_name");
					shopRegion = jsonObject.optString("merchant_region");
				} else {
					shopName = jsonObject.getString("shop_name");
					shopRegion = jsonObject.optString("region");
				}
				
				if (jsonObject.has("expire_time")) {
					Object value = jsonObject.get("expire_time");
					
					if (value instanceof Long) {
						authExpiresTime = (Long) value;
					} else if (value instanceof Integer) {
						authExpiresTime = ((Integer) value).longValue();
					} else {
						authExpiresTime = Long.valueOf((String) value);
					}
					
					authExpiresTime = authExpiresTime * 1000;
					Date date = new Date(authExpiresTime);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					expiresTime = sdf.format(date);
					
				}
				
				shopeeParameters.setAuthExpires(expiresTime);
				shopeeParameters.setShopName(shopName);
				shopeeParameters.setShopRegion(shopRegion);
				shopeeParameters.setUseYn("Y");
				
			}
		}
		
		return shopeeParameters;
	}
	
	public HashMap<String, Object> collectOrder(HttpServletRequest request) {
		HashMap<String, Object> sqlParams = new HashMap<>();
		ArrayList<ShopeeDTO> shopeeDataList = new ArrayList<>();
		ArrayList<ShopeeDTO> shopeeAccessTokenList = new ArrayList<>();
		
		try {
			
			ZoneId zoneId = ZoneId.of("Asia/Seoul");
			LocalDateTime now = LocalDateTime.now(zoneId);
		
			String userId = (String) request.getSession().getAttribute("USER_ID");
			String useYn = "Y";
			String fromDate = "2024-07-05";
			String toDate = "2024-07-10";
			sqlParams.put("userId", userId);
			sqlParams.put("useYn", useYn);
			sqlParams.put("merchantYn", "N");
			sqlParams.put("shopeeId", 161712636);

			String timeFromString = fromDate + " 00:00:00";
			String timeToString = "";
			
			if (toDate.equals(LocalDate.now(zoneId).toString())) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				timeToString = now.format(formatter);
			} else {
				timeToString = toDate + " 23:59:59";
			}
			
			long timeFrom = convertDateToTimestamp(timeFromString);
			long timeTo = convertDateToTimestamp(timeToString);
			
			shopeeDataList = selectShopeeInfoList(sqlParams);
			for (int i = 0; i < shopeeDataList.size(); i++) {
				shopeeDataList.get(i).setTimeFrom(timeFrom);
				shopeeDataList.get(i).setTimeTo(timeTo);
			}
			shopeeAccessTokenList = getShopeeAccessTokenList(shopeeDataList);
			
			for (int si = 0; si < shopeeAccessTokenList.size(); si++) {
				ShopeeDTO shopeeInfo = (ShopeeDTO) shopeeAccessTokenList.get(si);
	
				if (shopeeInfo.getErrorMsg().isEmpty()) {
	
					shopeeInfo = getOrderList(shopeeInfo);
					
					if (shopeeInfo == null) {
						continue;
					}
					
					ArrayList<String> orderList = shopeeInfo.getOrderList();
					int orderListCnt = orderList.size();
					for (int oi = 0; oi < orderListCnt; oi += 50) {
						int end = Math.min(oi + 50, orderList.size());
						List<String> subOrderList = orderList.subList(oi, end);
						String orderSnList = String.join("%2C", subOrderList);
						
						getOrderDetail(shopeeInfo, orderSnList);
					}
					
				} else {
					continue;
				}
			}
			
		} catch (NullPointerException e) {
			System.err.println("널 포인터 오류가 발생했습니다: " + e.getMessage());
		} catch (Exception e) {
	        System.err.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
	    }
		
		return null;
	}

	public ShopeeDTO getOrderList(ShopeeDTO shopeeParameter) {

		String path = "/api/v2/order/get_order_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeParameter.getShopeeId();
		String accessToken = shopeeParameter.getAccessToken();
		
		String timeRangeField = "create_time";
		long timeFrom = shopeeParameter.getTimeFrom();
		long timeTo = shopeeParameter.getTimeTo();
		int pageSize = 100;
		String orderStatus = "READY_TO_SHIP";
		String requestParameters = "time_range_field="+timeRangeField+"&time_from="+timeFrom+"&time_to="+timeTo+"&page_size="+pageSize+"&cursor=&order_status="+orderStatus;
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetOrderListApiResponse(responseMap, shopeeParameter);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	private ShopeeDTO getOrderListNextPage(ShopeeDTO shopeeParameter) {
		
		String path = "/api/v2/order/get_order_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeParameter.getShopeeId();
		String accessToken = shopeeParameter.getAccessToken();
		
		String timeRangeField = "create_time";
		long timeFrom = shopeeParameter.getTimeFrom();
		long timeTo = shopeeParameter.getTimeTo();
		int pageSize = 100;
		String orderStatus = "READY_TO_SHIP";
		String nextCursor = shopeeParameter.getNextCursor();
		String requestParameters = "time_range_field="+timeRangeField+"&time_from="+timeFrom+"&time_to="+timeTo+"&page_size="+pageSize+"&cursor="+nextCursor+"&order_status="+orderStatus;
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());

		if ("success".equals(responseMap.get("status"))) {
			return handleGetOrderListApiResponse(responseMap, shopeeParameter);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	private ShopeeDTO handleGetOrderListApiResponse(HashMap<String, String> responseMap, ShopeeDTO shopeeParameter) {
		
		JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
		
		String error = jsonObject.optString("error","");
		String errorMessage = jsonObject.optString("message","");
		
		if (!error.isEmpty()) {
			System.err.println(errorMessage);
			return null;
		}
		
		JSONObject response = jsonObject.getJSONObject("response");
		System.out.println(response);
		boolean more = response.optBoolean("more", false);
		String nextCursor = response.optString("next_cursor", "");
		JSONArray orderList = response.getJSONArray("order_list");
		
		ArrayList<String> shopeeOrderList = shopeeParameter.getOrderList();
		for (int oi = 0; oi < orderList.length(); oi++) {
			JSONObject orderObject = orderList.getJSONObject(oi);
			String orderSn = orderObject.optString("order_sn");
			shopeeOrderList.add(orderSn);
		}
		
		shopeeParameter.setMore(more);
		shopeeParameter.setNextCursor(nextCursor);
		shopeeParameter.setOrderList(shopeeOrderList);
		
		if (more) {
			return getOrderListNextPage(shopeeParameter);
		} else {
			return shopeeParameter;
		}
	}

	// order details 조회 후 임시 테이블에 저장 (작성 중)
	public void getOrderDetail(ShopeeDTO shopeeParameter, String orderSnList) {
		String path = "/api/v2/order/get_order_detail";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeParameter.getShopeeId();
		String accessToken = shopeeParameter.getAccessToken();
		
		ArrayList<String> optionalFiels = new ArrayList<>();
		optionalFiels.add("buyer_username");
		optionalFiels.add("recipient_address");
		optionalFiels.add("actual_shipping_fee");
		optionalFiels.add("goods_to_declare");
		optionalFiels.add("item_list");
		optionalFiels.add("actual_shipping_fee_confirmed");
		optionalFiels.add("buyer_cpf_id");
		optionalFiels.add("fulfillment_flag");
		optionalFiels.add("package_list");
		optionalFiels.add("invoice_data");
		
		String responseOptionalFields = String.join("%2C", optionalFiels);
		String requestParameters = "order_sn_list="+orderSnList+"&response_optional_fields="+responseOptionalFields;
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		System.out.println(sign);
		System.out.println(httpUrl);
		
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		System.out.println(responseMap);
		
		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			String error = jsonObject.optString("error","");
			String errorMessage = jsonObject.optString("message","");
			
			if (!error.isEmpty()) {
				System.err.println(errorMessage);

			} else {
				
				JSONObject responseObject = jsonObject.getJSONObject("response");
				JSONArray orderList = responseObject.getJSONArray("order_list");
				System.out.println(responseObject);

			}
			
			
		} else {
			
		}
	}

	
	public ShopeeDTO getGlobalItemList(ShopeeDTO shopeeDto) {
		
		String path = "/api/v2/global_product/get_global_item_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		
		String requestParameters = "page_size=50&offset=";
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, true);
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetGlobalItemList(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}
	
	private ShopeeDTO getGlobalItemListNextPage(ShopeeDTO shopeeDto) {
		
		String path = "/api/v2/global_product/get_global_item_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		String offset = shopeeDto.getOffsetStr();
		
		String requestParameters = "page_size=50&offset="+offset;
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, true);
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetGlobalItemList(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	private ShopeeDTO handleGetGlobalItemList(HashMap<String, String> responseMap, ShopeeDTO shopeeDto) {
		JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
		
		String error = jsonObject.optString("error","");
		String errorMessage = jsonObject.optString("message","");
		
		if (!error.isEmpty()) {
			System.err.println(errorMessage);
			return null;
		}
		
		JSONObject response = jsonObject.getJSONObject("response");
		System.out.println(response);
		boolean hasNextPage = response.optBoolean("has_next_page", false);
		String offset = response.optString("offset");
		JSONArray itemList = response.getJSONArray("global_item_list");
		
		ArrayList<Long> shopeeItemList = shopeeDto.getItemList();
		for (int ii = 0; ii < itemList.length(); ii++) {
			JSONObject itemObject = itemList.getJSONObject(ii);
			Object value = itemObject.get("global_item_id");
			long itemId = 0;
			
			if (value instanceof Long) {
				itemId = (Long) value;
			} else if (value instanceof Integer) {
				itemId = ((Integer) value).longValue();
			} else {
				itemId = Long.valueOf((String) value);
			}
			
			shopeeItemList.add(itemId);
		}
		
		shopeeDto.setHasNextPage(hasNextPage);
		shopeeDto.setOffsetStr(offset);
		shopeeDto.setItemList(shopeeItemList);
		
		if (hasNextPage) {
			return getGlobalItemListNextPage(shopeeDto);
		} else {
			return shopeeDto;
		}
	}
	
	public void getGlobalItemInfo(ShopeeDTO shopeeDto, String itemListQuery) {
		
		String path = "/api/v2/global_product/get_global_item_info";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		
		String requestParameters = "global_item_id_list="+itemListQuery;
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, true);
		
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			
			String error = jsonObject.optString("error","");
			String errorMessage = jsonObject.optString("message","");
			
			if (!error.isEmpty()) {
				System.err.println(errorMessage);
			} else {
				JSONObject responseObject = jsonObject.getJSONObject("response");
				System.out.println(responseObject);
			}
		}
	}
	
	public ShopeeDTO getItemList(ShopeeDTO shopeeDto) {
		String path = "/api/v2/product/get_item_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		
		String requestParameters = "offset=0&page_size=100&item_status=NORMAL";
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetItemList(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	public ShopeeDTO getItemListNextPage(ShopeeDTO shopeeDto) {
		String path = "/api/v2/product/get_item_list";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		String offset = String.valueOf(shopeeDto.getOffsetInt());
		
		String requestParameters = "offset="+offset+"&page_size=100&item_status=NORMAL";
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		
		HashMap<String, String> responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetItemList(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	private ShopeeDTO handleGetItemList(HashMap<String, String> responseMap, ShopeeDTO shopeeDto) {
		JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
		
		String error = jsonObject.optString("error","");
		String errorMessage = jsonObject.optString("message","");
		
		if (!error.isEmpty()) {
			System.err.println(errorMessage);
			return null;
		}
		
		JSONObject response = jsonObject.getJSONObject("response");
		boolean hasNextPage = response.optBoolean("has_next_page", false);
		int offset = response.optInt("next_offset", 0);
		JSONArray itemList = response.getJSONArray("item");
		
		ArrayList<Long> shopeeItemList = shopeeDto.getItemList();
		for (int ii = 0; ii < itemList.length(); ii++) {
			JSONObject itemObject = itemList.getJSONObject(ii);
			Object value = itemObject.get("item_id");
			long itemId = 0;
			
			if (value instanceof Long) {
				itemId = (Long) value;
			} else if (value instanceof Integer) {
				itemId = ((Integer) value).longValue();
			} else {
				itemId = Long.valueOf((String) value);
			}
			
			shopeeItemList.add(itemId);
		}
		
		shopeeDto.setHasNextPage(hasNextPage); 
		shopeeDto.setOffsetInt(offset);
		shopeeDto.setItemList(shopeeItemList);
		
		if (hasNextPage) {
			return getItemListNextPage(shopeeDto);
		} else {
			return shopeeDto;
		}
	}
	
	public void getItemInfo(ShopeeDTO shopeeDto, String itemListQuery) {
		
		String path = "/api/v2/product/get_item_base_info";
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		String accessToken = shopeeDto.getAccessToken();
		
		String requestParameters = "item_id_list="+itemListQuery+"&need_tax_info=true";
		
		String sign = apiCall.makeSignature(path, timestamp, accessToken, shopId);
		String httpUrl = apiCall.makeHttpUrl(path, timestamp, shopId, accessToken, sign, requestParameters, false);
		
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap = apiCall.apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			
			String error = jsonObject.optString("error","");
			String errorMessage = jsonObject.optString("message","");
			
			if (!error.isEmpty()) {
				System.err.println(errorMessage);
			} else {
				JSONObject responseObject = jsonObject.getJSONObject("response");
				System.out.println(responseObject);
			}
		}
	}

}
