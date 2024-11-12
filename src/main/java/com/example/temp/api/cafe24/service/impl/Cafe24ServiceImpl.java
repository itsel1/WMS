package com.example.temp.api.cafe24.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.cafe24.mapper.Cafe24Mapper;
import com.example.temp.api.cafe24.service.Cafe24Service;
import com.example.temp.api.cafe24.vo.Cafe24BuyerInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24ManufacturersVo;
import com.example.temp.api.cafe24.vo.Cafe24OrderParameter;
import com.example.temp.api.cafe24.vo.Cafe24OrdersItemsVo;
import com.example.temp.api.cafe24.vo.Cafe24ProductVo;
import com.example.temp.api.cafe24.vo.Cafe24ReceiverInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24ShopInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24StoreInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24SupplierVo;
import com.example.temp.api.cafe24.vo.Cafe24UserInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24UserTokenInfo;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.AciFakeBlVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.trans.ems.EmsApi;

@Service
public class Cafe24ServiceImpl implements Cafe24Service {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Cafe24Mapper mapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private ManagerMapper managerMapper;

	@Autowired
	private Cafe24Mapper cafe24mapper;

	@Autowired
	private ComnService commService;

	@Autowired
	EmsApi emsApi;

	@Override
	public void insertUserCafe24Info(Cafe24UserInfoVo cafe24UserInfo) throws Exception {
		mapper.insertCafe24UserInfo(cafe24UserInfo);
	}

	@Override
	public Cafe24UserInfoVo selectUserCafe24Info(Cafe24UserInfoVo cafe24UserInfo) {
		return mapper.selectUserCafe24Info(cafe24UserInfo);
	}

	@Override
	public int insertUserCafe24Token(Cafe24UserTokenInfo cafe24Token) {
		return mapper.insertUserCafe24Token(cafe24Token);
	}

	@Override
	public ArrayList<Cafe24UserTokenInfo> selectAdminCafe24TokenList(Cafe24UserTokenInfo prameterInfo) {
		return mapper.selectAdminCafe24TokenList(prameterInfo);
	}

	@Override
	public ArrayList<Cafe24UserTokenInfo> selectUserCafe24TokenList(Cafe24UserTokenInfo cafe24TokenInfo) {
		return mapper.selectUserCafe24TokenList(cafe24TokenInfo);
	}

	@Override
	public Cafe24UserTokenInfo selectUserCafe24Token(Cafe24UserInfoVo parameterInfo) {
		return mapper.selectUserCafe24Token(parameterInfo);
	}

	@Override
	public int selectOrderChk(Cafe24OrderParameter cafe24OrderParameter) {
		return mapper.selectOrderChk(cafe24OrderParameter);
	}

	@Override
	// 사용자별로 토큰 리푸레쉬
	public Cafe24UserTokenInfo userTokenChk(Cafe24UserTokenInfo cafe24UserTokenInfo) throws Exception {
		try {
			String defFormat = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat dateFormat = new SimpleDateFormat(defFormat);
			Date exprisAt = dateFormat.parse(cafe24UserTokenInfo.getExpiresAt());
			Calendar cal = Calendar.getInstance();
			cal.setTime(exprisAt);
			cal.add(Calendar.MINUTE, -20);
			Date chckekExpiresAt = cal.getTime();
			Date now = new Date();
			if (now.after(chckekExpiresAt)) {
				cafe24UserTokenInfo = getUserTrokenRefesh(cafe24UserTokenInfo);
			}

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return cafe24UserTokenInfo;
	}

	// 토큰 리프레쉬
	public Cafe24UserTokenInfo getUserTrokenRefesh(Cafe24UserTokenInfo cafe24UserTokenInfo) {
		try {
			String clientId = "nXedVhOIDC0S3VF6gMegWM";
			String clientSecretKey = "3F3UsSWkMRTsTxKZRyINAB";
			String authorization = clientId + ":" + clientSecretKey;
			String base64Auth = "";
			base64Auth = new String(Base64.encodeBase64(authorization.getBytes()));
			String userId = "";
			String mallId = "";
			String refreshToken = "";
			userId = cafe24UserTokenInfo.getUserId();
			mallId = cafe24UserTokenInfo.getMallId();
			refreshToken = cafe24UserTokenInfo.getRefreshToken();
			HttpURLConnection connection = null;
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/oauth/token";
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + base64Auth);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			String parameter = "grant_type=refresh_token&refresh_token=" + refreshToken;
			OutputStream os = connection.getOutputStream();
			os.write(parameter.getBytes("UTF-8"));
			os.flush();
			int responseCode = connection.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String sb = in.readLine();
			JSONObject jsonObject = new JSONObject(sb);
			if (responseCode == 200) {
				TimeZone tz;
				String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
				SimpleDateFormat df = new SimpleDateFormat(dateFormat);
				tz = TimeZone.getTimeZone("Asia/Seoul");
				df.setTimeZone(tz);
				String seoulDateFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(seoulDateFormat);
				Cafe24UserTokenInfo cafe24Token = new Cafe24UserTokenInfo();
				cafe24Token.setUserId(userId);
				cafe24Token.setAccessToken((String) jsonObject.get("access_token"));
				cafe24Token.setRefreshToken((String) jsonObject.get("refresh_token"));
				cafe24Token.setClientId((String) jsonObject.get("client_id"));
				cafe24Token.setCafe24Id((String) jsonObject.get("user_id"));
				cafe24Token.setMallId((String) jsonObject.get("mall_id"));
				Date date = df.parse((String) jsonObject.get("expires_at"));
				cafe24Token.setExpiresAt(sdf.format(date));
				date = df.parse((String) jsonObject.get("refresh_token_expires_at"));
				cafe24Token.setRefreshTokenExpiresAt(sdf.format(date));
				date = df.parse((String) jsonObject.get("issued_at"));
				cafe24Token.setIssuedAt(sdf.format(date));
				int rst = 0;
				rst = insertUserCafe24Token(cafe24Token);
				cafe24UserTokenInfo = cafe24Token;
			}

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return cafe24UserTokenInfo;
	}

	@Override
	public ArrayList<Cafe24ShopInfoVo> userCafe24shops(Cafe24UserTokenInfo cafe24TokenInfo) {
		ArrayList<Cafe24ShopInfoVo> cafe24ShopInfoList = new ArrayList<Cafe24ShopInfoVo>();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24TokenInfo.getAccessToken();
			String mallId = cafe24TokenInfo.getMallId();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/shops";
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONArray jArray = responseJson.getJSONArray("shops");
			for (int ii = 0; ii < jArray.length(); ii++) {
				Cafe24ShopInfoVo cafe24ShopInfo = new Cafe24ShopInfoVo();
				JSONObject obj = jArray.getJSONObject(ii);
				cafe24ShopInfo.setUserId(cafe24TokenInfo.getUserId());
				cafe24ShopInfo.setMallId(cafe24TokenInfo.getMallId());
				cafe24ShopInfo.setShopNo(obj.get("shop_no").toString());
				cafe24ShopInfo.setCurrencyCode(obj.get("currency_code").toString());
				cafe24ShopInfo.setCurrencyName(obj.get("currency_name").toString());
				cafe24ShopInfo.setPrimaryDomain(obj.get("primary_domain").toString());
				cafe24ShopInfo.setShopName(obj.get("shop_name").toString());
				cafe24ShopInfo.setLanguageCode(obj.get("language_code").toString());
				cafe24ShopInfo.setLanguageName(obj.get("language_name").toString());
				cafe24ShopInfo.setBaseDomain(obj.get("base_domain").toString());
				cafe24ShopInfo.setSlaveDomain(obj.get("slave_domain").toString());
				cafe24ShopInfo.setUseReferenceCurrency(obj.get("reference_currency_code").toString());
				cafe24ShopInfoList.add(cafe24ShopInfo);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return cafe24ShopInfoList;
	}

	@Override
	public int getOrdersCnt(Cafe24OrderParameter cafe24OrderParameter) {
		int orderCnt = 0;
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String startDate = cafe24OrderParameter.getStartDate();
			String endDate = cafe24OrderParameter.getEndDate();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders/count?shop_no="
					+ cafe24OrderParameter.getShopNo() + "&start_date=" + startDate + "&end_date=" + endDate
					+ "&order_status=N00,N10,N20,N21,N22,N30,N40,C00,C10,C34,C36,C40,C47,C48,C49,R00,R10,R12,R13,R30,R34,R36,R40,E00,E10,N01,E12,E13,E20,E30,E32,E34,E36,E40"
					+ "&date_type=pay_date";
			responseJson = callCafe24API(accessToken, requestUrl);
			orderCnt = Integer.parseInt(responseJson.get("count").toString());
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return orderCnt;
	}

	@Override
	public String getCafe24Datas(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date now = new Date();
		String nowDate = sdf.format(now);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -360);
		String now3ago = sdf.format(cal.getTime());
		if (cafe24OrderParameter.getStartDate() == null || cafe24OrderParameter.getStartDate().equals("")) {
			cafe24OrderParameter.setStartDate(now3ago);
		}
		if (cafe24OrderParameter.getEndDate() == null || cafe24OrderParameter.getEndDate().equals("")) {
			cafe24OrderParameter.setEndDate(nowDate);
		}
		int orderCnt = getOrdersCnt(cafe24OrderParameter);
		int startNo = 0;
		int endNo = 40;
		// 주문 수량 최대 호출 40개 이상히면 페이징을 통해 데이터 가져옴
		int tempTotalCount = (orderCnt % 40 == 0) ? (orderCnt / 40) : (orderCnt / 40) + 1;
		for (int i = 0; i < tempTotalCount; i++) {
			cafe24OrderParameter.setOffset(startNo);
			cafe24OrderParameter.setLimit(endNo);
			getOrders(cafe24OrderParameter);
			startNo = startNo + 40;
			endNo = endNo + 40;
		}
		return "";
	}

	@Override
	public void getOrders(Cafe24OrderParameter cafe24OrderParameter) {
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String startDate = cafe24OrderParameter.getStartDate();
			String endDate = cafe24OrderParameter.getEndDate();
			String orderId = cafe24OrderParameter.getOrderId();
			String userId = cafe24OrderParameter.getUserId();
			String dateFormat = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date now = new Date();
			String nowDate = sdf.format(now);
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders?" + "shop_no="
					+ cafe24OrderParameter.getShopNo() + "&start_date=" + startDate + "&end_date=" + endDate + ""
					+ "&offset=" + cafe24OrderParameter.getOffset() + "&limit=" + cafe24OrderParameter.getLimit();
			if (!orderId.equals("")) {
				requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders/" + orderId;
			}
			responseJson = callCafe24API(accessToken, requestUrl);
			ManagerVO customerInfo = new ManagerVO();
			customerInfo = managerMapper.getSelectUserInfo(userId);
			customerInfo.dncryptData();
			JSONArray jArray = responseJson.getJSONArray("orders");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj = jArray.getJSONObject(i);
				orderId = obj.get("order_id").toString();
				cafe24OrderParameter.setOrderId(orderId);
				int orderChk = selectOrderChk(cafe24OrderParameter);
				if (orderChk != 0) {
					continue;
				}
				String nno = "";
				nno = commService.selectNNO();
				Cafe24ReceiverInfoVo cafe24ReceiverInfo = new Cafe24ReceiverInfoVo();
				Cafe24BuyerInfoVo cafe24BuyerInfoVo = new Cafe24BuyerInfoVo();
				Cafe24ShopInfoVo cafe24ShopInfo = new Cafe24ShopInfoVo();
				Cafe24StoreInfoVo cafe24StoreInfoVo = new Cafe24StoreInfoVo();
				// fakeBL
				AciFakeBlVO aciFakeBl = new AciFakeBlVO();
				aciFakeBl.setNno(nno);
				aciFakeBl.setOrderNo(orderId);
				aciFakeBl.setTransCode("ACI");
				aciFakeBl.setWUserId(userId);
				aciFakeBl.setWUserIp(cafe24OrderParameter.getRemoteAddr());
				aciFakeBl = commService.getAciFakeBl(aciFakeBl);
				String fakeBl = aciFakeBl.getRstHawbNo();
				cafe24ReceiverInfo = getOrderRecivers(cafe24OrderParameter);
				cafe24BuyerInfoVo = getOrderBuyer(cafe24OrderParameter);
				cafe24ShopInfo = getShopsDetail(cafe24OrderParameter);
				cafe24StoreInfoVo = getStore(cafe24OrderParameter);
				// DDU 세금미포함 , DDP 세금포함
				UserOrderListVO userOrderList = new UserOrderListVO();
				userOrderList.setNno(nno);
				userOrderList.setOrgStation("082");
				userOrderList.setUserId(cafe24OrderParameter.getUserId());
				userOrderList.setOrderType("INSP");
				userOrderList.setOrderNo(orderId);

				userOrderList.setHawbNo(fakeBl);
				TimeZone tz;
				String dateFormatYmd = "yyyy-MM-dd'T'HH:mm:ss";
				SimpleDateFormat df = new SimpleDateFormat(dateFormatYmd);
				tz = TimeZone.getTimeZone("Asia/Seoul");
				df.setTimeZone(tz);
				Date orderDate = df.parse(obj.get("order_date").toString());
				String sOrderDate = "";
				SimpleDateFormat orderDateFormat = new SimpleDateFormat("yyyyMMdd");
				sOrderDate = orderDateFormat.format(orderDate);

				userOrderList.setOrderDate(sOrderDate);
				userOrderList.setShipperName(cafe24StoreInfoVo.getCompanyName());

				// cafe24 영문 주소가 확인되지 않아 wms 가입 영문 주소로 발송지 등록
				userOrderList.setShipperAddr(customerInfo.getUserEAddr());
				userOrderList.setShipperAddrDetail(customerInfo.getUserEAddrDetail());
				userOrderList.setShipperZip(cafe24StoreInfoVo.getZipCode());
				userOrderList.setShipperTel(cafe24StoreInfoVo.getPhone());
				userOrderList.setExpBusinessName(cafe24StoreInfoVo.getCompanyName());
				userOrderList.setExpBusinessNum(cafe24StoreInfoVo.getCompanyRegistrationNo());
				userOrderList.setBuySite(cafe24StoreInfoVo.getBaseDomain());
				userOrderList.setUserEmail(cafe24BuyerInfoVo.getEmail());
				userOrderList.setBoxCnt("1");
				userOrderList.setCneeName(cafe24ReceiverInfo.getName());
				userOrderList.setCustomsNo(cafe24ReceiverInfo.getClearanceInformation());
				userOrderList.setNativeCneeName(cafe24ReceiverInfo.getNameFurigana());
				userOrderList.setDstnNation(cafe24ReceiverInfo.getCountryCode());
				userOrderList.setCneeTel(cafe24ReceiverInfo.getPhone());
				userOrderList.setCneeHp(cafe24ReceiverInfo.getCellphone());
				userOrderList.setCneeCntry(cafe24ReceiverInfo.getCountryCode());
				userOrderList.setCneeState(cafe24ReceiverInfo.getAddressState());
				userOrderList.setCneeCity(cafe24ReceiverInfo.getAddressCity());
				userOrderList.setCneeZip(cafe24ReceiverInfo.getZipCode());
				userOrderList.setCneeAddr(cafe24ReceiverInfo.getAddress1());
				userOrderList.setCneeAddrDetail(cafe24ReceiverInfo.getAddress2());
				userOrderList.setDlvReqMsg(cafe24ReceiverInfo.getShippingMessage());
				userOrderList.setWUserId(cafe24OrderParameter.getWUserId());
				userOrderList.setWUserIp(cafe24OrderParameter.getRemoteAddr());
				userOrderList.setWDate(nowDate);

				HashMap<String, Object> transParameter = new HashMap<String, Object>();
				transParameter.put("userId", cafe24OrderParameter.getUserId());
				transParameter.put("orgStation", "082");
				transParameter.put("dstnNation", cafe24ReceiverInfo.getCountryCode());

				String transCode = commService.selectUserTransCode(transParameter);

				userOrderList.setTransCode(transCode);
				userOrderList.encryptData();
				memberMapper.insertUserOrderListTemp(userOrderList);
				ArrayList<Cafe24OrdersItemsVo> cafe24OrderItems = new ArrayList<Cafe24OrdersItemsVo>();
				cafe24OrderItems = getOrdersItems(cafe24OrderParameter);

				for (int z = 0; z < cafe24OrderItems.size(); z++) {
					String subNo = Integer.toString(z + 1);
					UserOrderItemVO orderItem = new UserOrderItemVO();
					orderItem.setNno(nno);
					orderItem.setSubNo(subNo);
					orderItem.setItemDetail(cafe24OrderItems.get(z).getItemDetail());
					orderItem.setBrand(cafe24OrderItems.get(z).getBrand());
					orderItem.setNativeItemDetail(cafe24OrderItems.get(z).getNativeItemDetail());
					orderItem.setItemMeterial(cafe24OrderItems.get(z).getItemMeterial());
					orderItem.setMakeCntry(cafe24OrderItems.get(z).getMakeCntry());
					orderItem.setItemCnt(cafe24OrderItems.get(z).getItemCnt());
					orderItem.setCusItemCode(cafe24OrderItems.get(z).getCusItemCode());
					orderItem.setItemImgUrl(cafe24OrderItems.get(z).getItemImgUrl());
					orderItem.setUnitValue(cafe24OrderItems.get(z).getUnitValue());
					orderItem.setItemExplan(cafe24OrderItems.get(z).getItemExplan());
					orderItem.setUserItemWta(cafe24OrderItems.get(z).getUserWta());
					orderItem.setWtUnit(cafe24OrderItems.get(z).getWtUnit());
					orderItem.setUnitCurrency(cafe24ShopInfo.getCurrencyCode());
					orderItem.setChgCurrency(cafe24ShopInfo.getCurrencyCode());
					orderItem.setOrgStation("082");
					orderItem.setTransCode(transCode);
					orderItem.setUserId(cafe24OrderParameter.getUserId());
					orderItem.setWUserId(cafe24OrderParameter.getWUserId());
					orderItem.setWUserIp(cafe24OrderParameter.getRemoteAddr());
					orderItem.setQtyUnit("EA");
					orderItem.setWDate(nowDate);

					// cafe24 OrdereItem insert
					memberMapper.insertUserOrderItemTemp(orderItem);
					Cafe24OrdersItemsVo cafe24OrderItem = new Cafe24OrdersItemsVo();
					cafe24OrderItem.setNno(nno);
					cafe24OrderItem.setSubNo(subNo);
					cafe24OrderItem.setUserId(cafe24OrderParameter.getUserId());
					cafe24OrderItem.setSupplierId(cafe24OrderItems.get(z).getSupplierId());
					cafe24OrderItem.setMallId(mallId);
					cafe24OrderItem.setOrderId(orderId);
					cafe24OrderItem.setShopNo(cafe24OrderParameter.getShopNo());
					cafe24OrderItem.setCusItemCode(cafe24OrderItems.get(z).getCusItemCode());
					cafe24mapper.insertcafe24OrderItemSuppliers(cafe24OrderItem);
				}
				cafe24mapper.insertCafe24OrderInfo(cafe24OrderParameter);
				commService.comnBlApplyCheck(nno, transCode, userId, cafe24OrderParameter.getRemoteAddr(), "default");
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	public JSONObject callCafe24API(String accessToken, String requestUrl) {
		HttpURLConnection connection = null;
		JSONObject responseJson = null;
		try {
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("X-Cafe24-Api-Version", "2021-09-01");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String sb = in.readLine();
			responseJson = new JSONObject(sb.toString());
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return responseJson;
	}

	public Cafe24StoreInfoVo getStore(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24StoreInfoVo storeInfo = new Cafe24StoreInfoVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/store?shop_no?=" + shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONObject obj = responseJson.getJSONObject("store");
			storeInfo.setCompanyRegistrationNo(obj.getString("company_registration_no"));
			storeInfo.setCompanyName(obj.getString("company_name"));
			storeInfo.setPhone(obj.getString("phone"));
			storeInfo.setCountry(obj.getString("country"));
			storeInfo.setZipCode(obj.getString("zipcode"));
			storeInfo.setAddress1(obj.getString("address1"));
			storeInfo.setAddress2(obj.getString("address2"));
			storeInfo.setBaseDomain(obj.getString("base_domain"));
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return storeInfo;
	}

	public Cafe24ShopInfoVo getShopsDetail(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24ShopInfoVo shopInfo = new Cafe24ShopInfoVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/shops/" + shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONObject obj = responseJson.getJSONObject("shop");
			shopInfo.setUserId(cafe24OrderParameter.getUserId());
			shopInfo.setMallId(cafe24OrderParameter.getMallId());
			shopInfo.setShopNo(obj.get("shop_no").toString());
			shopInfo.setPrimaryDomain(obj.get("primary_domain").toString());
			shopInfo.setShopName(obj.get("shop_name").toString());
			shopInfo.setBaseDomain(obj.get("base_domain").toString());
			shopInfo.setCurrencyCode(obj.get("currency_code").toString());
			shopInfo.setCurrencyName(obj.get("currency_name").toString());
			shopInfo.setLanguageCode(obj.get("language_code").toString());
			shopInfo.setLanguageName(obj.get("language_name").toString());
			shopInfo.setSlaveDomain(obj.get("slave_domain").toString());
			shopInfo.setUseReferenceCurrency(obj.get("reference_currency_code").toString());

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return shopInfo;
	}

	public Cafe24BuyerInfoVo getOrderBuyer(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24BuyerInfoVo cafe24BuyerInfoVo = new Cafe24BuyerInfoVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String orderId = cafe24OrderParameter.getOrderId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders/" + orderId
					+ "/buyer?shop_no=" + shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONObject obj = responseJson.getJSONObject("buyer");
			cafe24BuyerInfoVo.setName(obj.get("name").toString());
			cafe24BuyerInfoVo.setEmail(obj.get("email").toString());
			cafe24BuyerInfoVo.setPhone(obj.get("phone").toString());
			cafe24BuyerInfoVo.setCellphone(obj.get("cellphone").toString());
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return cafe24BuyerInfoVo;
	}

	public Cafe24ReceiverInfoVo getOrderRecivers(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24ReceiverInfoVo cafe24ReceiverInfo = new Cafe24ReceiverInfoVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String orderId = cafe24OrderParameter.getOrderId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders/" + orderId
					+ "/receivers?shop_no=" + shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONArray jArray = responseJson.getJSONArray("receivers");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj = jArray.getJSONObject(i);
				cafe24ReceiverInfo.setName(obj.get("name").toString());
				cafe24ReceiverInfo.setPhone(obj.get("phone").toString());
				cafe24ReceiverInfo.setCellphone(obj.get("cellphone").toString());
				cafe24ReceiverInfo.setShippingMessage(obj.get("shipping_message").toString());
				cafe24ReceiverInfo.setNameFurigana(obj.get("name_furigana").toString());
				cafe24ReceiverInfo.setZipCode(obj.get("zipcode").toString());
				cafe24ReceiverInfo.setAddress1(obj.get("address1").toString());
				cafe24ReceiverInfo.setAddress2(obj.get("address2").toString());
				cafe24ReceiverInfo.setAddressState(obj.get("address_state").toString());
				cafe24ReceiverInfo.setAddressCity(obj.get("address_city").toString());
				cafe24ReceiverInfo.setCountryCode(obj.get("country_code").toString());
				cafe24ReceiverInfo.setClearanceInformationType(obj.get("clearance_information_type").toString());
				cafe24ReceiverInfo.setClearanceInformation(obj.get("clearance_information").toString());
				cafe24ReceiverInfo.setShippingMessage(obj.getString("shipping_message"));
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return cafe24ReceiverInfo;
	}

	public ArrayList<Cafe24OrdersItemsVo> getOrdersItems(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		ArrayList<Cafe24OrdersItemsVo> ordersItems = new ArrayList<Cafe24OrdersItemsVo>();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String orderId = cafe24OrderParameter.getOrderId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/orders/" + orderId
					+ "/items?shop_no=" + shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONArray jArray = responseJson.getJSONArray("items");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj = jArray.getJSONObject(i);
				Cafe24OrdersItemsVo ordersItem = new Cafe24OrdersItemsVo();
				Cafe24ProductVo product = new Cafe24ProductVo();
				Cafe24ManufacturersVo manufacturers = new Cafe24ManufacturersVo();
				cafe24OrderParameter.setProductNo(obj.get("product_no").toString());
				product = getProduct(cafe24OrderParameter);
				cafe24OrderParameter.setManufacturerCode(product.getManufacturerCode());
				manufacturers = getManufacturers(cafe24OrderParameter);
				String itemDetail = "";
				if (!obj.get("eng_product_name").toString().equals("")) {
					itemDetail = obj.get("eng_product_name").toString();
				} else {
					itemDetail = obj.get("product_name").toString();
				}
				ordersItem.setItemDetail(itemDetail);
				ordersItem.setNativeItemDetail(obj.get("product_name_default").toString());
				ordersItem.setItemMeterial(product.getEnglishProductMaterial());
				ordersItem.setHsCode(obj.getString("hs_code"));
				ordersItem.setMakeCntry(obj.get("made_in_code").toString());
				ordersItem.setItemCnt(obj.get("quantity").toString());
				ordersItem.setSupplierId(obj.get("supplier_id").toString());
				ordersItem.setCusItemCode(obj.get("product_no").toString());
				ordersItem.setBrand(manufacturers.getManufacturerName());
				ordersItem.setItemMeterial(product.getEnglishProductMaterial());
				ordersItem.setItemImgUrl(product.getDetailImage());
				ordersItem.setUnitValue(obj.get("product_price").toString());
				ordersItem.setItemExplan(product.getSummaryDescription());
				ordersItem.setUserWta(product.getProductWeight());
				ordersItem.setWtUnit("KG");
				ordersItems.add(ordersItem);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return ordersItems;
	}

	public Cafe24ProductVo getProduct(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24ProductVo product = new Cafe24ProductVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String shopNo = cafe24OrderParameter.getShopNo();
			String productNo = cafe24OrderParameter.getProductNo();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/products/" + productNo + "?shop_no="
					+ shopNo;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONObject obj = responseJson.getJSONObject("product");
			product.setManufacturerCode(obj.get("manufacturer_code").toString());
			product.setMadeInCode(obj.get("made_in_code").toString());
			product.setEnglishProductMaterial(obj.get("english_product_material").toString());
			product.setProductMaterial(obj.get("product_material").toString());
			product.setClothFabric(obj.get("cloth_fabric").toString());
			product.setDetailImage(obj.get("detail_image").toString());
			product.setHscode(obj.get("hscode").toString());
			product.setCountryHscode(obj.get("country_hscode").toString());
			product.setSummaryDescription(obj.get("summary_description").toString());
			product.setProductWeight(obj.get("product_weight").toString());
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return product;
	}

	public Cafe24ManufacturersVo getManufacturers(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24ManufacturersVo manufacturers = new Cafe24ManufacturersVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String manufacturerCode = cafe24OrderParameter.getManufacturerCode();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/manufacturers/" + manufacturerCode;
			responseJson = callCafe24API(accessToken, requestUrl);
			JSONObject obj = responseJson.getJSONObject("manufacturer");
			manufacturers.setManufacturerName(obj.getString("manufacturer_name"));

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return manufacturers;
	}

	public Cafe24SupplierVo getSupplie(Cafe24OrderParameter cafe24OrderParameter) throws Exception {
		Cafe24SupplierVo supplierVo = new Cafe24SupplierVo();
		try {
			JSONObject responseJson = null;
			String accessToken = cafe24OrderParameter.getAccessToken();
			String mallId = cafe24OrderParameter.getMallId();
			String supplierCode = cafe24OrderParameter.getSupplierCode();
			String requestUrl = "https://" + mallId + ".cafe24api.com/api/v2/admin/suppliers?supplier_code"
					+ supplierCode;
			responseJson = callCafe24API(accessToken, requestUrl);
		} catch (Exception e) { 
			logger.error("Exception", e);  
		}
		return supplierVo;
	}

	@Override
	public void mergeSupplier(HashMap<String, Object> parameterInfo) throws Exception {
		String userId = (String) parameterInfo.get("userId");
		Cafe24UserTokenInfo prameterInfo = new Cafe24UserTokenInfo();
		prameterInfo.setUserId(userId);
		ArrayList<Cafe24UserTokenInfo> cafe24TokenInfoList = new ArrayList<Cafe24UserTokenInfo>();
		cafe24TokenInfoList = mapper.selectAdminCafe24TokenList(prameterInfo);
		for (int i = 0; i < cafe24TokenInfoList.size(); i++) {
			Cafe24UserTokenInfo cafe24TokenInfo = new Cafe24UserTokenInfo();
			cafe24TokenInfo = cafe24TokenInfoList.get(i);
			cafe24TokenInfo = userTokenChk(cafe24TokenInfo);
		}
	}
}
