package com.example.temp.api.ecommerce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.ecommerce.dto.Item;
import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.ecommerce.dto.Shopee;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.member.mapper.MemberECommerceMapper;
import com.google.gson.Gson;

@Service
public class ShopeeHandler {
	
	@Autowired
	MemberECommerceMapper ecMapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	private static final String HOST = "https://partner.shopeemobile.com";
	private static final String PARTNER_KEY = "6567766350437346695a667a7a61525558754a414b54506b4d6c4f597a664d6d";
	private static final long PARTNER_ID = 2008151L;
	
	
	private String getSign(String path, long timestamp, String accessToken, long id) {
		
		String tmpStr = String.format("%s%s%s%s%s", PARTNER_ID, path, timestamp, accessToken, id);
		
		try {
			
			byte[] tmpStrByte = tmpStr.getBytes("UTF-8");
			byte[] partnerKeyByte = PARTNER_KEY.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(tmpStrByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			
			System.err.println(e.getMessage());
			
			return null;
		}
	}
	
	
	private String getSign(String path, long timestamp) {
		
		String tmpStr = String.format("%s%s%s", PARTNER_ID, path, timestamp);
		
		try {
			
			byte[] tmpStrByte = tmpStr.getBytes("UTF-8");
			byte[] partnerKeyByte = PARTNER_KEY.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(tmpStrByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			
			System.err.println(e.getMessage());
			
			return null;
		}
	}
	
	
	private String getAppSign(String path, long timestamp, String accessToken, long id, String partnerKey, long partnerId) {
		
		String tmpStr = String.format("%s%s%s%s%s", partnerId, path, timestamp, accessToken, id);
		
		try {
			
			byte[] tmpStrByte = tmpStr.getBytes("UTF-8");
			byte[] partnerKeyByte = partnerKey.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(tmpStrByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			
			System.err.println(e.getMessage());
			
			return null;
		}
	}
	
	
	private String getAppSign(String path, long timestamp, String partnerKey, long partnerId) {
		
		String tmpStr = String.format("%s%s%s", partnerId, path, timestamp);
		
		try {
			
			byte[] tmpStrByte = tmpStr.getBytes("UTF-8");
			byte[] partnerKeyByte = partnerKey.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(tmpStrByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			
			System.err.println(e.getMessage());
			
			return null;
		}
	}
	
	
	private String getEndPoint(String path, long timestamp, String signature, String requestParameters, String accessToken, long id, String merchantYn) {
		
		String tmpUrl;
		String httpUrl;
		
		if (path == null || timestamp == 0 || id == 0 || accessToken == null || signature == null) {
			throw new IllegalArgumentException("Required parameters must not be null");
		}

		try {
			if (requestParameters == null || requestParameters.length() < 1) {
				if ("Y".equals(merchantYn)) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s", PARTNER_ID, timestamp, id, accessToken, signature);	
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s", PARTNER_ID, timestamp, id, accessToken, signature);	
				}
				
			} else {
				if ("Y".equals(merchantYn)) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s&%s", PARTNER_ID, timestamp, id, accessToken, signature, requestParameters);
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s&%s", PARTNER_ID, timestamp, id, accessToken, signature, requestParameters);
				}
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	
	
	private String getEndPoint(String path, long timestamp, String signature, String requestParameters) {

		String tmpUrl;
		String httpUrl;
		
		try {
		
			if (path == null || timestamp == 0 || signature == null) {
				throw new IllegalArgumentException("Required parameters must not be null");
			}
			
			if (requestParameters == null || requestParameters.length() < 1) {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s", PARTNER_ID, timestamp, signature);
			} else {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s&%s", PARTNER_ID, timestamp, signature, requestParameters);
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
			
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	
	private String getAppEndPoint(String path, long timestamp, long id, String accessToken, String signature, String requestParameters, String merchantYn, long partnerId) {
		
		String tmpUrl;
		String httpUrl;
		
		if (path == null || timestamp == 0 || id == 0 || accessToken == null || signature == null) {
			throw new IllegalArgumentException("Required parameters must not be null");
		}

		try {
			if (requestParameters == null || requestParameters.length() < 1) {
				if ("Y".equals(merchantYn)) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s", partnerId, timestamp, id, accessToken, signature);	
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s", partnerId, timestamp, id, accessToken, signature);	
				}
				
			} else {
				if ("Y".equals(merchantYn)) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s&%s", partnerId, timestamp, id, accessToken, signature, requestParameters);
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s&%s", partnerId, timestamp, id, accessToken, signature, requestParameters);
				}
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	
	
	private String getAppEndPoint(String path, long timestamp, String signature, String requestParameters, long partnerId) {

		String tmpUrl;
		String httpUrl;
		
		try {
		
			if (path == null || timestamp == 0 || signature == null) {
				throw new IllegalArgumentException("Required parameters must not be null");
			}
			
			if (requestParameters == null || requestParameters.length() < 1) {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s", partnerId, timestamp, signature);
			} else {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s&%s", partnerId, timestamp, signature, requestParameters);
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
			
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	private HashMap<String, String> apiGet(String apiUrl, LinkedHashMap<String, Object> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();

		try {
			
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoOutput(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
			}
			
			conn.disconnect();
			
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
	    	returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
		
	}
	
	
	private HashMap<String, String> apiPost(String apiUrl, String requestBody, LinkedHashMap<String, Object> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestBody);
			wr.flush();
			
			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
			}
			
			conn.disconnect();
			
		} catch (NullPointerException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Null Pointer Exception : "  + e.getMessage());
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
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
	
	
	private String convertHashMapToJsonString(HashMap<String, Object> map) {
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	
	private long convertObjectToLong(Object value) {
		long id = 0;
		
		if (value instanceof Long) {
			id = (Long) value;
		} else if (value instanceof Integer) {
			id = ((Integer) value).longValue();
		} else {
			id = Long.valueOf((String) value);
		}
		
		return id;
	}
	
	
	public String createShopeeAuthPageUrl(String userId) {

		String path = "/api/v2/shop/auth_partner";
		String redirectUrl = "http://wms.acieshop.com/api/"+userId+"/submitAuth";
		String requestParameters = "redirect="+redirectUrl;
		long timestamp = System.currentTimeMillis() / 1000L;
		
		String sign = getSign(path, timestamp);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters);
		
		if (!httpUrl.isEmpty()) {
			return httpUrl;
		} else {
			return null;
		}
	}
	
	
	public String createShopeeSellerAuthPageUrl(HashMap<String, Object> parameters) {
		String userId = (String) parameters.get("userId");
		long partnerId = convertObjectToLong(parameters.get("partnerId"));
		String partnerKey = (String) parameters.get("partnerKey");
		
		Shopee shopeeDto = new Shopee();
		shopeeDto.setUserId(userId);
		shopeeDto.setPartnerId(partnerId);
		shopeeDto.setPartnerKey(partnerKey);
		
		String path = "/api/v2/shop/auth_partner";
		String redirectUrl = "http://wms.acieshop.com/api/"+shopeeDto.getUserId()+"/submitAuth";
		String requestParameters = "redirect="+redirectUrl;
		long timestamp = System.currentTimeMillis() / 1000L;
		
		String sign = getAppSign(path, timestamp, partnerKey, partnerId);
		String httpUrl = getAppEndPoint(path, timestamp, sign, requestParameters, partnerId);
		
		if (!httpUrl.isEmpty()) {
			return httpUrl;
		} else {
			return null;
		}
	}


	public ArrayList<Shopee> getToken(Shopee shopeeDto) {
		ArrayList<Shopee> authList = new ArrayList<>();
		
		String path = "/api/v2/auth/token/get";
		long timestamp = System.currentTimeMillis() / 1000L;
		String idKey = null;
		
		if ("Y".equals(shopeeDto.getMerchantYn())) {
			idKey = "main_account_id";
		} else {
			idKey = "shop_id";
		}
		
		String sign = getSign(path, timestamp);
		String httpUrl = getEndPoint(path, timestamp, sign, null);
		
		HashMap<String, Object> requestBodyMap = new HashMap<>();
		requestBodyMap.put("code", shopeeDto.getCode());
		//requestBodyMap.put("partner_id", shopeeDto.getPartnerId());
		requestBodyMap.put("partner_id", PARTNER_ID);
		requestBodyMap.put(idKey, shopeeDto.getShopeeId());
				
		String requestBody = convertHashMapToJsonString(requestBodyMap);
		
		HashMap<String, String> responseMap = apiPost(httpUrl, requestBody, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String error = jsonObject.optString("error", "");
			String errorMessage = jsonObject.optString("message", "");
			
			if (error.isEmpty()) {

				String accessToken = jsonObject.getString("access_token");
				String refreshToken = jsonObject.getString("refresh_token");
				
				if ("Y".equals(shopeeDto.getMerchantYn())) {
					JSONArray merchantIdList = jsonObject.getJSONArray("merchant_id_list");
					JSONArray shopIdList = jsonObject.getJSONArray("shop_id_list");
					
					for (int mi = 0; mi < merchantIdList.length(); mi++) {
						Shopee authInfo = new Shopee();
						long id = convertObjectToLong(merchantIdList.get(mi));
						
						authInfo.setShopeeId(id);
						authInfo.setUserId(shopeeDto.getUserId());
						authInfo.setMerchantYn("Y");
						authInfo.setRefreshToken(refreshToken);
						authInfo.setAccessToken(accessToken);
						authInfo.setErrorMsg("");
						authInfo.setPartnerId(shopeeDto.getPartnerId());
						authInfo.setPartnerKey(shopeeDto.getPartnerKey());
						authList.add(authInfo);
					}
					
					for (int si = 0; si < shopIdList.length(); si++) {
						Shopee authInfo = new Shopee();
						long id = convertObjectToLong(shopIdList.get(si));
						
						authInfo.setShopeeId(id);
						authInfo.setUserId(shopeeDto.getUserId());
						authInfo.setMerchantYn("N");
						authInfo.setRefreshToken(refreshToken);
						authInfo.setAccessToken(accessToken);
						authInfo.setErrorMsg("");
						authInfo.setPartnerId(shopeeDto.getPartnerId());
						authInfo.setPartnerKey(shopeeDto.getPartnerKey());
						authList.add(authInfo);
					}
					
				} else {
					
					Shopee authInfo = new Shopee();
					authInfo.setShopeeId(shopeeDto.getShopeeId());
					authInfo.setUserId(shopeeDto.getUserId());
					authInfo.setMerchantYn("N");
					authInfo.setRefreshToken(refreshToken);
					authInfo.setAccessToken(accessToken);
					authInfo.setErrorMsg("");
					authInfo.setPartnerId(shopeeDto.getPartnerId());
					authInfo.setPartnerKey(shopeeDto.getPartnerKey());
					authList.add(authInfo);
				}
				
			} else {
				
				shopeeDto.setErrorMsg(errorMessage);
				authList.add(shopeeDto);
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			shopeeDto.setErrorMsg(responseMap.get("status_msg"));
			authList.add(shopeeDto);
			
		}
		
		return authList;
	}
	
	
	public Shopee getRefreshToken(Shopee shopeeDto) {
		
		String idKey = null;
		String path = "/api/v2/auth/access_token/get";
		long timestamp = System.currentTimeMillis() / 1000L;
		
		if ("Y".equals(shopeeDto.getMerchantYn())) {
			idKey = "merchant_id";
		} else {
			idKey = "shop_id";
		}
		
		HashMap<String, Object> requestBodyMap = new HashMap<>();
		requestBodyMap.put("refresh_token", shopeeDto.getRefreshToken());
		//requestBodyMap.put("partner_id", shopeeDto.getPartnerId());
		requestBodyMap.put("partner_id", PARTNER_ID);
		requestBodyMap.put(idKey, shopeeDto.getShopeeId());
		
		String requestBody = convertHashMapToJsonString(requestBodyMap);
		String sign = getSign(path, timestamp);
		String httpUrl = getEndPoint(path, timestamp, sign, null);
		
		
		HashMap<String, String> responseMap = apiPost(httpUrl, requestBody, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			String error = jsonObject.optString("error", "");
			String errorMessage = jsonObject.optString("message", "");
			
			if (error.isEmpty()) {
				
				String accessToken = jsonObject.getString("access_token");
				String refreshToken = jsonObject.getString("refresh_token");
				
				System.out.println(shopeeDto.getShopeeId());
				System.out.println(accessToken);
				
				if (!refreshToken.equals(shopeeDto.getRefreshToken())) {
					shopeeDto.setRefreshToken(refreshToken);
					ecMapper.updateShopeeRefreshToken(shopeeDto);
				}
				
				shopeeDto.setAccessToken(accessToken);
				shopeeDto.setRefreshToken(refreshToken);
				shopeeDto.setErrorMsg("");
				
			} else {
				shopeeDto.setErrorMsg(errorMessage);
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			shopeeDto.setErrorMsg(responseMap.get("status_msg"));
		}
		
		return shopeeDto;
	}
	
	
	public Shopee getShopInfo(Shopee shopeeDto) {
		
		long id = shopeeDto.getShopeeId();
		long timestamp = System.currentTimeMillis() / 1000L;
		String path = "";
		String accessToken = shopeeDto.getAccessToken();
		String merchantYn = shopeeDto.getMerchantYn().toUpperCase();
		
		if ("Y".equals(merchantYn)) {
			path = "/api/v2/merchant/get_merchant_info";
		} else {
			path = "/api/v2/shop/get_shop_info";
		}
		
		String sign = getSign(path, timestamp, accessToken, id);
		String httpUrl = getEndPoint(path, timestamp, sign, null, accessToken, id, merchantYn);
		// 시스템 개별 app 사용으로 변경 시 아래 메서드 호출 
		//String sign2 = getAppSign(path, timestamp, accessToken, id, shopeeDto.getPartnerKey(), shopeeDto.getPartnerId());
		//String httpUrl2 = getAppEndPoint(path, timestamp, id, accessToken, sign2, null, merchantYn, shopeeDto.getPartnerId());
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());

		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String error = jsonObject.optString("error", "");
			String errorMessage = jsonObject.optString("message", "");
			
			if (error.isEmpty()) {
				long authExpiresTime = 0;
				String shopName = "";
				String shopRegion = "";
				
				if ("Y".equals(merchantYn)) {
					shopName = jsonObject.getString("merchant_name");
					shopRegion = jsonObject.getString("merchant_region");
				} else {
					shopName = jsonObject.getString("shop_name");
					shopRegion = jsonObject.getString("region");
				}
				
				if (jsonObject.has("expire_time")) {
					authExpiresTime = convertObjectToLong(jsonObject.get("expire_time"));
				}
				
				authExpiresTime = authExpiresTime * 1000;
				Date date = new Date(authExpiresTime);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String expiresTime = sdf.format(date);
				
				shopeeDto.setAuthExpires(expiresTime);
				shopeeDto.setErrorMsg("");
				shopeeDto.setShopName(shopName);
				shopeeDto.setShopRegion(shopRegion);
				shopeeDto.setUseYn("Y");
				
			} else {
				shopeeDto.setErrorMsg(errorMessage);
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			shopeeDto.setErrorMsg(responseMap.get("status_msg"));
		}
		
		return shopeeDto;
		
	}

	
	
	public JSONArray collectOrders(Shopee shopeeDto) {
		
		JSONArray returnJsonArray = new JSONArray();
		shopeeDto = getOrderList(shopeeDto);

		ArrayList<String> orderList = shopeeDto.getOrderList();
		ArrayList<String> snList = new ArrayList<String>();
		
		for (int i = 0; i < orderList.size(); i += 50) {
			int end = Math.min(i + 50, orderList.size());
			List<String> subOrderList = orderList.subList(i, end);
			String orderSnList = String.join("%2C", subOrderList);
			snList.add(orderSnList);
		}
		
		for (String sn : snList) {
			JSONArray jsonArray = getOrderDetailList(shopeeDto, sn);
			for (int ii = 0; ii < jsonArray.length(); ii++) {
				JSONObject jsonObject = jsonArray.getJSONObject(ii);
				returnJsonArray.put(jsonObject);
				
			}
		}

		return returnJsonArray;
	}
	
	
	public JSONArray collectShipments(Shopee shopeeDto) {
		
		JSONArray returnJsonArray = new JSONArray();
		shopeeDto = getShipmentList(shopeeDto);

		ArrayList<String> snList = new ArrayList<String>();
		
		ArrayList<HashMap<String, Object>> shipmentList = shopeeDto.getShipmentList();
		
		for (int i = 0; i < shipmentList.size(); i += 50) {
			int end = Math.min(i + 50, shipmentList.size());
			List<HashMap<String, Object>> subOrderList = shipmentList.subList(i, end);
			
			List<String> orderSnValues = new ArrayList<>();
			
			for (HashMap<String, Object> order : subOrderList) {
				String orderSn = (String) order.get("orderSn");
				if (orderSn != null) {
					orderSnValues.add(orderSn);
				}
			}
			
			String orderSnList = String.join("%2C", orderSnValues);
			snList.add(orderSnList);
		}
/*
		for (String sn : snList) {
			JSONArray jsonArray = getOrderDetailList(shopeeDto, sn);
			for (int ii = 0; ii < jsonArray.length(); ii++) {
				JSONObject jsonObject = jsonArray.getJSONObject(ii);
				returnJsonArray.put(jsonObject);
				
			}
		}*/

		return returnJsonArray;
	}
	
	
	public Shopee getOrderList(Shopee shopeeDto) {
		
		String path = "/api/v2/order/get_order_list";
		String timeRangeField = "create_time";
		String accessToken = shopeeDto.getAccessToken();
		
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		long timeFrom = shopeeDto.getTimeFrom();
		long timeTo = shopeeDto.getTimeTo();
		
		int pageSize = 100;
		
		String merchantYn = shopeeDto.getMerchantYn();
		String orderStatus = "READY_TO_SHIP";
		String requestParameters = "time_range_field="+timeRangeField+"&time_from="+timeFrom+"&time_to="+timeTo+"&page_size="+pageSize+"&cursor=&order_status="+orderStatus; 
		
		String sign = getSign(path, timestamp, accessToken, shopId);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters, accessToken, shopId, merchantYn);
		//String sign2 = getAppSign(path, timestamp, accessToken, shopId, shopeeDto.getPartnerKey(), shopeeDto.getPartnerId());
		//String httpUrl2 = getAppEndPoint(path, timestamp, shopId, accessToken, sign2, requestParameters, merchantYn, shopeeDto.getPartnerId());
		
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetOrderListResponse(responseMap, shopeeDto); 
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	
	private Shopee getOrderListNextPage(Shopee shopeeDto) {

		String path = "/api/v2/order/get_order_list";
		String accessToken = shopeeDto.getAccessToken();
		String timeRangeField = "create_time";
		String merchantYn = shopeeDto.getMerchantYn();
		
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		long timeFrom = shopeeDto.getTimeFrom();
		long timeTo = shopeeDto.getTimeTo();
		int pageSize = 100;
		
		String orderStatus = "READY_TO_SHIP";
		String nextCursor = shopeeDto.getNextCursor();
		String requestParameters = "time_range_field="+timeRangeField+"&time_from="+timeFrom+"&time_to="+timeTo+"&page_size="+pageSize+"&cursor="+nextCursor+"&order_status="+orderStatus;
		
		String sign = getSign(path, timestamp, accessToken, shopId);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters, accessToken, shopId, merchantYn);
		//String sign2 = getAppSign(path, timestamp, accessToken, shopId, shopeeDto.getPartnerKey(), shopeeDto.getPartnerId());
		//String httpUrl2 = getAppEndPoint(path, timestamp, shopId, accessToken, sign2, requestParameters, merchantYn, shopeeDto.getPartnerId());
		
		if (sign == null || httpUrl == null) {
			return null;
		}
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetOrderListResponse(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}


	private Shopee handleGetOrderListResponse(HashMap<String, String> responseMap, Shopee shopeeDto) {
		JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("userId", shopeeDto.getUserId());
		
		String error = jsonObject.optString("error","");
		String errorMessage = jsonObject.optString("message","");
		
		if (!error.isEmpty()) {
			System.err.println(errorMessage);
			return null;
		}
		
		JSONObject response = jsonObject.getJSONObject("response");
		boolean more = response.optBoolean("more", false);
		String nextCursor = response.optString("next_cursor", "");
		JSONArray orderList = response.getJSONArray("order_list");
		
		ArrayList<String> shopeeOrderList = shopeeDto.getOrderList();
		for (int oi = 0; oi < orderList.length(); oi++) {
			JSONObject orderObject = orderList.getJSONObject(oi);
			String orderSn = orderObject.optString("order_sn");
			sqlParams.put("orderNo", orderSn);
			
			int cnt = ecMapper.selectExistOrderCount(sqlParams);
			if (cnt == 1) {
				continue;
			}
			
			shopeeOrderList.add(orderSn);
		}
		
		shopeeDto.setMore(more);
		shopeeDto.setNextCursor(nextCursor);
		shopeeDto.setOrderList(shopeeOrderList);
		
		if (more) {
			return getOrderListNextPage(shopeeDto);
		} else {
			return shopeeDto;
		}

	}
	
	
	public JSONArray getOrderDetailList(Shopee shopeeDto, String orderSnList) {
		String path = "/api/v2/order/get_order_detail";
		String accessToken = shopeeDto.getAccessToken();
		String merchantYn = shopeeDto.getMerchantYn();
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		
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
		
		String sign = getSign(path, timestamp, accessToken, shopId);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters, accessToken, shopId, merchantYn);
		//String sign2 = getAppSign(path, timestamp, accessToken, shopId, shopeeDto.getPartnerKey(), shopeeDto.getPartnerId());
		//String httpUrl2 = getAppEndPoint(path, timestamp, shopId, accessToken, sign2, requestParameters, merchantYn, shopeeDto.getPartnerId());
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			String error = jsonObject.optString("error","");
			String errorMessage = jsonObject.optString("message","");
			
			if (!error.isEmpty()) {
				System.err.println(errorMessage);
				return null;
			} else {
				JSONObject orderObject = jsonObject.getJSONObject("response");
				JSONArray jsonArray = orderObject.getJSONArray("order_list");
				return jsonArray;
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}
	
	
	public Shopee getShipmentList(Shopee shopeeDto) {
		String path = "/api/v2/order/get_shipment_list";
		String accessToken = shopeeDto.getAccessToken();
		
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		
		int pageSize = 100;
		
		String requestParameters = "cursor=&page_size="+pageSize;
		
		String sign = getSign(path, timestamp, accessToken, shopId);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters, accessToken, shopId, "N");
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetShipmentListResponse(responseMap, shopeeDto); 
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	
	private Shopee getShipmentListNextPage(Shopee shopeeDto) {
		String path = "/api/v2/order/get_shipment_list";
		String accessToken = shopeeDto.getAccessToken();
		long timestamp = System.currentTimeMillis() / 1000L;
		long shopId = shopeeDto.getShopeeId();
		
		int pageSize = 100;
		
		String requestParameters = "cursor="+shopeeDto.getNextCursor()+"&page_size="+pageSize;
		
		String sign = getSign(path, timestamp, accessToken, shopId);
		String httpUrl = getEndPoint(path, timestamp, sign, requestParameters, accessToken, shopId, "N");
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			return handleGetOrderListResponse(responseMap, shopeeDto);
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}
	
	
	private Shopee handleGetShipmentListResponse(HashMap<String, String> responseMap, Shopee shopeeDto) {
		JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("userId", shopeeDto.getUserId());
		
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
		
		ArrayList<String> shopeeOrderList = shopeeDto.getOrderList();
		ArrayList<HashMap<String, Object>> shipmentList = new ArrayList<HashMap<String, Object>>();
		for (int oi = 0; oi < orderList.length(); oi++) {
			HashMap<String, Object> shipmentInfo = new HashMap<String, Object>();
			JSONObject orderObject = orderList.getJSONObject(oi);
			String orderSn = orderObject.optString("order_sn");
			sqlParams.put("orderNo", orderSn);
			
			int cnt = ecMapper.selectExistOrderCount(sqlParams);
			if (cnt == 1) {
				continue;
			}
			
			shipmentInfo.put("orderSn", orderSn);
			shipmentInfo.put("packageNo", orderObject.optString("package_number"));
			shipmentList.add(shipmentInfo);
			
			shopeeOrderList.add(orderSn);
		}
		
		shopeeDto.setMore(more);
		shopeeDto.setNextCursor(nextCursor);
		shopeeDto.setShipmentList(shipmentList);
		
		if (more) {
			return getShipmentListNextPage(shopeeDto);
		} else {
			return shopeeDto;
		}
		
	}
	

}
