package com.example.temp.member.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.MidiSystem;

import org.apache.ibatis.session.SqlSessionException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.api.Export;
import com.example.temp.api.ecommerce.dto.Item;
import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.ecommerce.dto.Shopee;
import com.example.temp.api.ecommerce.dto.Shopify;
import com.example.temp.api.ecommerce.service.ShopeeHandler;
import com.example.temp.api.ecommerce.service.ShopifyHandler;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.member.mapper.MemberECommerceMapper;
import com.example.temp.member.service.MemberECommerceService;

@Service
public class MemberECommerceServiceImpl implements MemberECommerceService {

	@Autowired
	MemberECommerceMapper mapper;
	
	@Autowired
	ShopifyHandler shopifyHandler;
	
	@Autowired
	ShopeeHandler shopeeHandler;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	MemberECommerceService service;
	
	@Override
	public Shopify selectShopifyInfo(HashMap<String, Object> sqlParams) {
		return mapper.selectShopifyInfo(sqlParams);
	}

	@Override
	public int selectShopeeInfoCount(String userId) {
		return mapper.selectShopeeInfoCount(userId);
	}

	@Override
	public ArrayList<Shopee> selectShopeeInfo(HashMap<String, Object> sqlParams) {
		return mapper.selectShopeeInfo(sqlParams);
	}
	
	@Override
	public Shopee selectShopeeAppInfo(HashMap<String, Object> sqlParams) {
		return mapper.selectShopeeAppInfo(sqlParams);
	}

	@Override
	public Shopee selectShopeeInfoOne(HashMap<String, Object> sqlParams) {
		return mapper.selectShopeeInfoOne(sqlParams);
	}

	@Override
	public void execShopifyInfo(Shopify shopifyDto) {
		mapper.execShopifyInfo(shopifyDto);
	}

	@Override
	public void updateShopeeInfoUseYn(Shopee shopeeDto) {
		mapper.updateShopeeInfoUseYn(shopeeDto);
	}

	@Override
	@Transactional
	public void execShopeeInfo(Shopee shopeeDto) throws Exception {
		try {
			mapper.execShopeeInfo(shopeeDto);
			//mapper.deleteShopeeInfo(shopeeDto);
			//mapper.insertShopeeInfo(shopeeDto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
	
	
	@Override
	public void execShopeeAppInfo(HashMap<String, Object> sqlParams) {
		mapper.execShopeeAppInfo(sqlParams);
	}

	
	@Override
	public HashMap<String, Object> getEcommerceOrders(HttpServletRequest request) {
		HashMap<String, Object> returnMap = new HashMap<>();
		String shopType = request.getParameter("shopType").toUpperCase().trim();
		
		switch (shopType) {
		case "SHOPIFY":
			returnMap = getShopifyOrders(request);
			break;
		case "SHOPEE":
			returnMap = getShopeeOrders(request);
		break;
		default:
		break;
		}
		
		return returnMap;
	}
	
	
	@Transactional
	@Override
	public void insertShopifyOrderDetail(Order order) throws Exception {
		
		try {
			
			mapper.insertTmpOrder(order);
			for (int ii = 0; ii < order.getItemList().size(); ii++) {
				Item item = order.getItemList().get(ii);
				mapper.insertTmpItem(item);
			}
			
			mapper.insertShopifyFulfillment(order);
			Export export = shopifyHandler.getShopifyExportDeclareInfo(order);
			if (export != null) {
				mapper.insertExportDeclareInfo(export);
			}
			
		} catch (Exception  e) {
			System.err.println("An error occurred while inserting : " + e.getMessage());
			throw e;
		}

	}
	
	
	@Transactional
	@Override
	public void insertShopeeOrderDetail(Order order) throws Exception {
		
		try {
			mapper.insertTmpOrder(order);
			for (int ii = 0; ii < order.getItemList().size(); ii++) {
				Item item = order.getItemList().get(ii);
				mapper.insertTmpItem(item);
			}
			
		} catch (Exception e) {
			System.err.println("error occurred while inserting : " + e.getMessage());
			throw e;
		}
	}
	
	
	private String getDateTime(String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = new Date();
		String dateTime = format.format(date);
		return dateTime;
	}
	
	
	private String convertTimestampToDate(long timestamp) {
		timestamp = timestamp * 1000;
		Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return localDate.format(formatter);
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
	

	private HashMap<String, Object> getShopifyOrders(HttpServletRequest request) {
		
		HashMap<String, Object> returnMap = new HashMap<>();
		
		try {
			
			String userId = (String) request.getSession().getAttribute("USER_ID");
			String orderType = request.getParameter("orderType").toUpperCase().trim();
			
			int cnt = mapper.selectShopifyInfoCount(userId);
			if (cnt == 0) {
				returnMap.put("status", "success");
				returnMap.put("msg", "Shopify 연동 정보가 없습니다.");
				return returnMap;
			}
			
			HashMap<String, Object> sqlParams = new HashMap<>();
			sqlParams.put("userId", userId);
			Shopify shopifyDto = mapper.selectShopifyInfo(sqlParams);
			shopifyDto.dncryptData();
			shopifyDto.setDateTimeFrom(request.getParameter("fromDate")+"T00:00:00+09:00");
			shopifyDto.setDateTimeTo(request.getParameter("toDate")+"T23:59:59+09:00");
			shopifyDto.setUserIp(request.getRemoteAddr());

			int orderCount = shopifyHandler.getOrderListCount(shopifyDto);
			
			if (orderCount == -1 || orderCount == 0) {
				if (orderCount == 0) {
					returnMap.put("status", "success");
					returnMap.put("msg", "수집 가능한 주문이 없습니다.");
				} else {
					returnMap.put("status", "fail");
					returnMap.put("msg", "Shopify API 연동 오류가 발생 하였습니다.");
				}
				return returnMap;
			}
			
			JSONArray orderList = shopifyHandler.getOrderList(shopifyDto);
			if (orderList == null) {
				returnMap.put("status", "fail");
				returnMap.put("msg", "Shopify API 연동 오류가 발생 하였습니다.");
				return returnMap;
			} else {
				
				ArrayList<Order> shopifyOrderList = new ArrayList<>();
				
				for (int oi = 0; oi < orderList.length(); oi++) {
					JSONObject orderObject = orderList.getJSONObject(oi);
					
					sqlParams.put("orderNo", orderObject.optString("order_number"));
					int existOrderCount = mapper.selectExistOrderCount(sqlParams);
					if (existOrderCount == 1) {
						continue;
					}
					
					Order order = setShopifyOrder(orderType, shopifyDto, orderObject);
					if (order == null) {
						continue;
					}
					
					String fulfillOrderId = shopifyHandler.getFulfillmentOrder(shopifyDto, order.getOrderId());
					if (fulfillOrderId == null) {
						continue;
					}
					
					order.setFulfillOrderId(fulfillOrderId);
					order.encryptData();
					shopifyOrderList.add(order);
				}
				
				int totalCount = shopifyOrderList.size();
				
				for (Order order : shopifyOrderList) {
					
					try {
						service.insertShopifyOrderDetail(order);
					} catch (Exception e) {
						totalCount--;
						System.err.println("An error occurred while inserting : " + e.getMessage());
						continue;
					}
				}

				returnMap.put("status", "success");
				returnMap.put("msg", totalCount + "건 수집 완료 되었습니다.");
			}

		} catch (NullPointerException e) {
			System.err.println("A null pointer exception orrcured : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 필요한 데이터가 누락 되었습니다. 다시 시도해주세요.");
		} catch (RuntimeException e) {
			System.err.println("Runtime error occured : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 일시적인 문제가 발생 되었습니다. 다시 시도해주세요.");
		} catch (Exception e) {
			System.err.println("An unknown error occurred : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 예기치 않은 오류가 발생 되었습니다. 관리자에게 문의해 주세요.");
		}
		
		return returnMap;
	}
	

	private Order setShopifyOrder(String orderType, Shopify shopifyDto, JSONObject json) {
		
		Order order = new Order();
		
		try {
			System.out.println(json);
			String newNno = comnMapper.selectNNO();
			JSONObject receiverInfo = json.getJSONObject("shipping_address");
			JSONArray items = json.getJSONArray("line_items");
			String orgNation = shopifyDto.getOrgNation().toUpperCase().trim();
			String format = "yyyyMMddHHmmssSS";
			String userId = shopifyDto.getUserId().toUpperCase();
			
			order.setNno(newNno);
			
			if ("KR".equals(orgNation)) {
				order.setOrgStation("082");	
			} else if ("GB".equals(orgNation)) {
				order.setOrgStation("441");
			} else if ("US".equals(orgNation)) {
				order.setOrgStation("213");
			} else if ("DE".equals(orgNation)) {
				order.setOrgStation("049");
			}
			
			order.setDstnNation(receiverInfo.optString("country_code"));
			order.setDstnStation(receiverInfo.optString("country_code"));
			order.setOrgNation(orgNation);
			order.setUserId(shopifyDto.getUserId());
			order.setOrderType(orderType);
			order.setOrderNo(json.optString("order_number"));
			order.setBoxCnt(1);
			order.setHawbNo("");
			
			double userWta = Double.parseDouble(json.optString("total_weight"));
			userWta = userWta / 1000;
			
			order.setUserWta(userWta);
			order.setUserWtc(0);
			order.setShipperName(shopifyDto.getComEName());
			order.setShipperZip(shopifyDto.getUserZip());
			order.setShipperTel(shopifyDto.getUserTel());
			order.setShipperHp(shopifyDto.getUserHp());
			order.setShipperCntry(orgNation);
			order.setShipperCity(shopifyDto.getUserECity());
			order.setShipperState(shopifyDto.getUserEState());
			order.setShipperAddr(shopifyDto.getUserEAddr());
			order.setShipperAddrDetail(shopifyDto.getUserEAddrDetail());
			order.setShipperTaxType("0");
			order.setShipperTaxNo("");
			
			String receiverName = receiverInfo.optString("first_name") + " " + receiverInfo.optString("last_name");
			order.setCneeName(receiverName);
			order.setCneeAddr(receiverInfo.optString("address1"));
			order.setCneeZip(receiverInfo.optString("zip"));
			order.setCneeTel(receiverInfo.optString("phone").replaceAll("[^0-9]", ""));
			order.setCneeHp(receiverInfo.optString("phone").replaceAll("[^0-9]", ""));
			order.setCneeCntry(receiverInfo.optString("country"));
			order.setCneeCity(receiverInfo.optString("city"));
			
			if ("US".equals(receiverInfo.optString("country_code"))) {
				order.setCneeState(receiverInfo.optString("province_code"));
			} else {
				order.setCneeState(receiverInfo.optString("province"));
			}
			
			order.setCneeAddrDetail(receiverInfo.optString("address2"));
			order.setCneeDistrict("");
			order.setCneeWard("");
			order.setCneeTaxType("0");
			order.setCneeTaxNo("");
			order.setUserLength(0);
			order.setUserWidth(0);
			order.setUserHeight(0);
			order.setUserEmail(shopifyDto.getUserEmail());
			order.setWUserId(shopifyDto.getUserId());
			order.setWUserIp(shopifyDto.getUserIp());
			order.setWDate(getDateTime(format));
			String[] orderDate = json.optString("created_at").split("T");
			order.setOrderDate(orderDate[0].replaceAll("[^0-9]", ""));
			order.setCneeEmail(json.optString("contact_email"));
			order.setDimUnit("CM");
			order.setWtUnit("KG");
			order.setBuySite(shopifyDto.getStoreUrl());
			order.setGetBuy("1");
			order.setPayment("DDU");
			order.setUploadType("SHOPIFY");
			order.setFood("N");
			if ("ADMINCOREELLE".equals(userId)) {
				order.setCosmetic("Y");
			}
			order.setSign("N");
			order.setOrderId(json.optString("id"));
			
			
			ArrayList<Item> itemList = new ArrayList<Item>();
			for (int ii = 0; ii < items.length(); ii++) {
				format = "yyyy-MM-dd HH:mm:ss";
				JSONObject itemObject = items.getJSONObject(ii);
				Item item = new Item();
				int subNo = ii+1;
				String regex = "[^\\x20-\\x7E\\uAC00-\\uD7A3]";
				String itemDetail = itemObject.optString("title");
				itemDetail = itemDetail.replaceAll(regex, "");
				
				item.setNno(newNno);
				item.setSubNo(subNo);
				item.setOrgStation(order.getOrgStation());
				item.setUserId(shopifyDto.getUserId());
				item.setHsCode("");
				item.setItemDetail(itemDetail);
				item.setUnitCurrency(json.optString("currency"));
				item.setChgCurrency(json.optString("currency"));
				item.setItemCnt(itemObject.optInt("quantity"));
				item.setUnitValue(Double.parseDouble(itemObject.optString("price")));
				item.setBrand(itemObject.optString("vendor"));
				item.setWtUnit("KG");
				double itemWta = Double.parseDouble(itemObject.optString("grams"));
				itemWta = itemWta / 1000;
				item.setUserWta(itemWta);
				item.setWUserId(shopifyDto.getUserId());
				item.setWUserIp(shopifyDto.getUserIp());
				item.setWDate(getDateTime(format));
				item.setTrkDate(getDateTime(format));
				
				itemList.add(item);
			}
			
			order.setItemList(itemList);
			
			return order;
			
		} catch (NullPointerException e) {
			System.err.println("A null pointer exception occured : " + e.getMessage());
			return null;
		} catch (RuntimeException e) {
			System.err.println("A runtime Exception occured : " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("An unexpected Exception occured : " + e.getMessage());
			return null;
		}
		
	}
	
	
	private HashMap<String, Object> getShopeeOrders(HttpServletRequest request) {
		HashMap<String, Object> returnMap = new HashMap<>();
		
		try {
			
			String userId = (String) request.getSession().getAttribute("USER_ID");
			String orderType = request.getParameter("orderType").toUpperCase().trim();
			String fromDate = request.getParameter("fromDate") + " 00:00:00";
			String toDate = request.getParameter("toDate") + " 23:59:59";
			
			int cnt = mapper.selectShopeeInfoCount(userId);
			if (cnt == 0) {
				returnMap.put("status", "success");
				returnMap.put("msg", "Shopee 연동 정보가 없습니다.");
				return returnMap;
			}
			
			HashMap<String, Object> sqlParams = new HashMap<>();
			sqlParams.put("userId", userId);
			sqlParams.put("merchantYn", "N");
			sqlParams.put("useYn", "Y");
			
			ArrayList<Shopee> shopList = new ArrayList<>();
			shopList = mapper.selectShopeeInfo(sqlParams);
			
			ArrayList<Order> orders = new ArrayList<>();
			
			for (int i = 0; i < shopList.size(); i++) {
				shopList.get(i).dncryptData();
				Shopee shopOne = shopeeHandler.getRefreshToken(shopList.get(i));
				shopOne.setUserIp(request.getRemoteAddr());
				shopOne.setOrderType(orderType);
				shopOne.setTimeFrom(convertDateToTimestamp(fromDate));
				shopOne.setTimeTo(convertDateToTimestamp(toDate));
				
				if (!shopOne.getErrorMsg().isEmpty()) {
					continue;
				}
				
				JSONArray jsonArray = shopeeHandler.collectOrders(shopOne);
				//JSONArray jsonArray = shopeeHandler.collectShipments(shopOne);
				
				if (jsonArray.length() == 0) {
					continue;
				}
				
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					Order order = setShopeeOrder(orderType, shopOne, jsonObject);
					
					if (order == null) {
						continue;
					}
					
					order.encryptData();
					orders.add(order);
				}
				
			}
			
			int totalCount = orders.size();

			
			for (Order order : orders) {
				
				try {
					
					service.insertShopeeOrderDetail(order);
					
				} catch (Exception e) {
					totalCount--;
					System.err.println("An error occurred while inserting : " + e.getMessage());
					continue;
				}
			}
			
			
			
			returnMap.put("status", "success");
			returnMap.put("msg", totalCount + "건 수집 완료 되었습니다.");
			
		} catch (NullPointerException e) {
			System.err.println("A null pointer exception orrcured : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 필요한 데이터가 누락 되었습니다. 다시 시도해주세요.");
		} catch (RuntimeException e) {
			System.err.println("A runtime error occured : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 일시적인 문제가 발생 되었습니다. 다시 시도해주세요.");
		} catch (Exception e) {
			System.err.println("An unknown error occurred : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 예기치 않은 오류가 발생 되었습니다. 관리자에게 문의해 주세요.");
		}
		
		return returnMap;
	}

	
	private Order setShopeeOrder(String orderType, Shopee shopeeDto, JSONObject json) {
		
		Order order = new Order();
		
		try {

			String newNno = comnMapper.selectNNO();
			JSONObject receiverInfo = json.getJSONObject("recipient_address");
			JSONArray items = json.getJSONArray("item_list");
			String orgNation = shopeeDto.getOrgNation().toUpperCase().trim();
			String format = "yyyyMMddHHmmssSS";
			String userId = shopeeDto.getUserId().toUpperCase();
			
			order.setNno(newNno);
			
			if ("KR".equals(orgNation)) {
				order.setOrgStation("082");	
			} else if ("GB".equals(orgNation)) {
				order.setOrgStation("441");
			} else if ("US".equals(orgNation)) {
				order.setOrgStation("213");
			} else if ("DE".equals(orgNation)) {
				order.setOrgStation("049");
			}
			
			order.setDstnNation(receiverInfo.optString("region"));
			order.setDstnStation(receiverInfo.optString("region"));
			order.setOrgNation(orgNation);
			order.setUserId(shopeeDto.getUserId());
			order.setOrderType(orderType);
			order.setOrderNo(json.optString("order_sn"));
			order.setBoxCnt(1);
			order.setHawbNo("");
			order.setUserWta(0);
			order.setUserWtc(0);
			order.setShipperName(shopeeDto.getComEName());
			order.setShipperZip(shopeeDto.getUserZip());
			order.setShipperTel(shopeeDto.getUserTel());
			order.setShipperHp(shopeeDto.getUserHp());
			order.setShipperCntry(orgNation);
			order.setShipperCity(shopeeDto.getUserECity());
			order.setShipperState(shopeeDto.getUserEState());
			order.setShipperAddr(shopeeDto.getUserEAddr());
			order.setShipperAddrDetail(shopeeDto.getUserEAddrDetail());
			order.setShipperTaxType("0");
			order.setShipperTaxNo("");

			order.setCneeName(receiverInfo.optString("name"));
			order.setCneeAddr(receiverInfo.optString("full_address"));
			order.setCneeZip(receiverInfo.optString("zipcode"));
			order.setCneeTel(receiverInfo.optString("phone").replaceAll("[^0-9]", ""));
			order.setCneeHp(receiverInfo.optString("phone").replaceAll("[^0-9]", ""));
			order.setCneeCntry(receiverInfo.optString("region"));
			if ("SG".equals(receiverInfo.optString("region"))) {
				order.setCneeCity("Singapore");
				order.setCneeState("Singapore");
			} else {
				order.setCneeCity(receiverInfo.optString("city"));
				order.setCneeState(receiverInfo.optString("state"));	
			}
			order.setCneeAddrDetail(receiverInfo.optString(""));
			order.setCneeDistrict(receiverInfo.optString("district"));
			order.setCneeWard(receiverInfo.optString("town"));
			order.setCneeTaxType("0");
			order.setCneeTaxNo("");
			order.setUserLength(0);
			order.setUserWidth(0);
			order.setUserHeight(0);
			order.setUserEmail("");
			order.setWUserId("");
			order.setWUserIp("");
			order.setWDate(getDateTime(format));

			Object value = json.get("create_time");
			long timestamp = 0;
			if (value instanceof Long) {
				timestamp = (Long) value;
			} else if (value instanceof Integer) {
				timestamp = ((Integer) value).longValue();
			} else {
				timestamp = Long.valueOf((String) value);
			}
			
			String orderDate = convertTimestampToDate(timestamp);
			
			order.setOrderDate(orderDate);
			order.setCneeEmail("");
			order.setDimUnit("CM");
			order.setWtUnit("KG");
			order.setBuySite(shopeeDto.getStoreUrl());
			order.setGetBuy("1");
			order.setPayment("DDU");
			order.setUploadType("SHOPEE");
			order.setFood("N");
			if ("DRALTHEA".equals(userId)) {
				order.setCosmetic("Y");	
			} else {
				order.setCosmetic("N");
			}
			order.setSign("N");
			
			ArrayList<Item> itemList = new ArrayList<Item>();
			for (int ii = 0; ii < items.length(); ii++) {
				int subNo = ii+1;
				format = "yyyy-MM-dd HH:mm:ss";
				JSONObject itemObject = items.getJSONObject(ii);
				Item item = new Item();
				String itemDetail = itemObject.optString("item_name").trim();
				itemDetail = itemDetail.replaceAll("[^\\x20-\\x7E\\uAC00-\\uD7A3]", "");
				String itemSku = itemObject.optString("item_sku").trim();
				JSONObject imageObject = itemObject.getJSONObject("image_info");
				String imageUrl = imageObject.optString("image_url").trim();
				
				double originPrice = itemObject.optDouble("model_original_price");
				double discountPrice = 0;
				if (itemObject.has("model_discounted_price")) {
					discountPrice = itemObject.optDouble("model_discounted_price");	
				}
				
				item.setNno(newNno);
				item.setSubNo(subNo);
				item.setOrgStation(order.getOrgStation());
				item.setUserId(shopeeDto.getUserId());
				item.setHsCode("");
				item.setItemDetail(itemDetail);
				item.setUnitCurrency(json.optString("currency"));
				item.setItemCnt(itemObject.optInt("model_quantity_purchased"));
				if (discountPrice > 0) {
					item.setUnitValue(discountPrice);
				} else {
					item.setUnitValue(originPrice);
				}
				
				item.setBrand("");
				item.setMakeCntry("");
				item.setMakeCom("");
				item.setItemDiv("");
				item.setWtUnit("KG");
				item.setQtyUnit("EA");
				item.setPackageUnit("");
				item.setExchangeRate(0);
				item.setChgCurrency(json.optString("currency"));
				item.setChgAmt(0);
				item.setItemMeterial("");
				item.setTakeInCode("");
				item.setUserWta(itemObject.optDouble("weight", 0));
				item.setUserWtc(0);
				item.setItemUrl("");
				item.setItemImgUrl(imageUrl);
				item.setStatus("");
				item.setTrkCom("");
				item.setTrkNo("");
				item.setTrkDate(getDateTime(format));
				item.setNativeItemDetail(itemDetail);
				item.setCusItemCode(itemSku);
				item.setWUserId(shopeeDto.getUserId());
				item.setWUserIp(shopeeDto.getUserIp());
				item.setWDate(getDateTime(format));
				item.setDimUnit("");
				item.setItemColor("");
				item.setItemSize("");
				
				if ("TAKEIN".equals(orderType)) {
					item = setTakeInItem(item);
					
					if (!item.getStatus().isEmpty()) {
						order.setStatus(order.getStatus() + ", " + item.getStatus());
					}
				}

				itemList.add(item);
			}
			
			order.setItemList(itemList);
			
			return order;
			
		} catch (RuntimeException e) {
			System.err.println("A runtime Exception occured : " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("An unexpected Exception occured : " + e.getMessage());
			return null;
		}
	}

	
	private Item setTakeInItem(Item item) {
		
		HashMap<String, Object> sqlParams = new HashMap<>();
		
		sqlParams.put("userId", item.getUserId());
		sqlParams.put("cusItemCode", item.getCusItemCode());
		sqlParams.put("orgStation", item.getOrgStation());
		
		Item takeinItem = mapper.selectTakeinItem(sqlParams);
		
		if (takeinItem == null) {
			item.setStatus("SKU와 매칭되는 상품코드를 찾을 수 없습니다 ["+item.getCusItemCode()+"]");
			
		} else {
			item.setTakeInCode(takeinItem.getTakeInCode());
			item.setHsCode(takeinItem.getHsCode());
			item.setBrand(takeinItem.getBrand());
			
			if (!takeinItem.getNativeItemDetail().isEmpty() && takeinItem.getNativeItemDetail() != null) {
				item.setNativeItemDetail(takeinItem.getNativeItemDetail());
			}
			
			if (takeinItem.getUserWta() != 0) {
				item.setUserWta(takeinItem.getUserWta());
			}
			
			if (takeinItem.getUserWtc() != 0) {
				item.setUserWtc(takeinItem.getUserWtc());
			}
			
			if (!takeinItem.getWtUnit().isEmpty() && takeinItem.getWtUnit() != null) {
				item.setWtUnit(takeinItem.getWtUnit());
			}
			
			if (!takeinItem.getQtyUnit().isEmpty() && takeinItem.getQtyUnit() != null) {
				item.setQtyUnit(takeinItem.getQtyUnit());
			}
			
			if (!takeinItem.getItemUrl().isEmpty() && takeinItem.getItemUrl() != null) {
				item.setItemUrl(takeinItem.getItemUrl());
			}
			
			if (!takeinItem.getItemImgUrl().isEmpty() && takeinItem.getItemImgUrl() != null) {
				item.setItemImgUrl(takeinItem.getItemImgUrl());
			}
			
			if (!takeinItem.getItemMeterial().isEmpty() && takeinItem.getItemMeterial() != null) {
				item.setItemMeterial(takeinItem.getItemMeterial());
			}
			
			if (!takeinItem.getItemDiv().isEmpty() && takeinItem.getItemDiv() != null) {
				item.setItemDiv(takeinItem.getItemDiv());
			}
			
			if (!takeinItem.getMakeCntry().isEmpty() && takeinItem.getMakeCntry() != null) {
				item.setMakeCntry(takeinItem.getMakeCntry());
			}
			
			if (!takeinItem.getMakeCom().isEmpty() && takeinItem.getMakeCom() != null) {
				item.setMakeCom(takeinItem.getMakeCom());
			}
			
			if (!takeinItem.getItemColor().isEmpty() && takeinItem.getItemColor() != null) {
				item.setItemColor(takeinItem.getItemColor());
			}
			
			if (!takeinItem.getItemSize().isEmpty() && takeinItem.getItemSize() != null) {
				item.setItemSize(takeinItem.getItemSize());
			}
		}

		return item;
	}



}
