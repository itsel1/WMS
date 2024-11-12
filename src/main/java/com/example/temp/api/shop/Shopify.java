package com.example.temp.api.shop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShippingServiceImpl;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.service.impl.ComnServiceImpl;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Service
@Component
public class Shopify {
	
	@Autowired
	ShopMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ComnServiceImpl comnServiceImpl;
	
	@Autowired
	ShippingServiceImpl shipping;
	
	public HashMap<String, Object> selectShopifyInfo(HashMap<String, Object> parameters) {
		return mapper.selectShopifyApiInfo(parameters);
	}
	
	protected HashMap<String, Object> collectOrder(HttpServletRequest request) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> shopifyInfo = new HashMap<String, Object>();
		LinkedHashMap<String, Object> header = new LinkedHashMap<String, Object>();
		
		String url = "";
		String userId = (String) request.getSession().getAttribute("USER_ID");
		int orderCnt = 0;
		String orgStation = "";
		
		try {
			
			params.put("userId", userId);
			shopifyInfo = mapper.selectShopifyApiInfo(params);
			
			String shopifyUrl = shopifyInfo.get("shopifyUrl").toString();
			String apiKey = shopifyInfo.get("apiKey").toString();
			String accessToken = shopifyInfo.get("accessToken").toString();
			
			header.put("Content-Type", "application/json");
			header.put("X-Shopify-Access-Token", accessToken);
			
			// 주문 개수 조회
			url = "https://"+shopifyUrl+"/admin/api/2023-10/orders/count.json?financial_status=paid&fulfillment_status=unfulfilled";
			
			ApiAction action = ApiAction.getInstance();
			String orderCntString = action.sendGet(url, header);
			
			System.out.println(orderCntString);
			
			if (!orderCntString.equals("")) {
				JSONObject orderCntResponse = new JSONObject(orderCntString);
				orderCnt = orderCntResponse.optInt("count");
			} else {
				throw new Exception();
			}

			if (orderCnt == 0) {
				rst.put("Status", "N0");
				rst.put("Msg", "주문 요청 건이 없습니다.");
				return rst;
			}
			
			
			UserVO shipperInfo = new UserVO();
			shipperInfo = mapper.selectShipperInfoByUserId(params);
			shipperInfo.dncryptData();

			if (shipperInfo.getOrgStation().equals("KR")) {
				orgStation = "082";
			} else if (shipperInfo.getOrgStation().equals("GB")) {
				orgStation = "441";
			} else if (shipperInfo.getOrgStation().equals("US")) {
				orgStation = "213";
			} else if (shipperInfo.getOrgStation().equals("DE")) {
				orgStation = "049";
			}
			
			
			url = "https://"+shopifyUrl+"/admin/api/2023-10/orders.json?financial_status=paid&fulfillment_status=unfulfilled&limit=250";
			String orderString = action.sendGet(url, header);
			
			System.out.println(orderString);
			
			if (!orderString.equals("")) {
				JSONObject orderResponse = new JSONObject(orderString);
				
				JSONArray orderList = new JSONArray(String.valueOf(orderResponse.optString("orders")));
				int checkCnt = 0;
				
				for (int index = 0; index < orderList.length(); index++) {
					String newNno = "";
					params = new HashMap<String, Object>();
					UserOrderListVO shopifyOrder = new UserOrderListVO();
					
					try {
						
						JSONObject orderInfo = (JSONObject) orderList.get(index);
						String orderNo = orderInfo.optString("order_number");
						//orderNo = userId+"_"+orderNo;
						String orderId = orderInfo.optString("id");
						
						params.put("userId", userId);
						params.put("orderNo", orderNo);
						
						int orderDuplCnt = mapper.selectDuplicatedOrderCnt(params);
						if (orderDuplCnt > 0) {
							continue;
						}
						
						params.put("orderId", orderId);
						
						url = "https://"+shopifyUrl+"/admin/api/2023-10/orders/"+orderId+"/fulfillment_orders.json";
						String fulfillString = action.sendGet(url, header);
						
						System.out.println(fulfillString);
						
						if (!fulfillString.equals("")) {
							JSONObject fulfillResponse = new JSONObject(fulfillString);
							JSONArray fulfillList = new JSONArray(String.valueOf(fulfillResponse.optString("fulfillment_orders")));
							JSONObject fulfillInfo = (JSONObject) fulfillList.getJSONObject(0);
							String fulfillOrderId = fulfillInfo.optString("id");
							params.put("fulfillOrderId", fulfillOrderId);
							
						} else {
							continue;
						}

						newNno = comnService.selectNNO();
						params.put("nno", newNno);
						JSONObject cneeInfo = orderInfo.optJSONObject("shipping_address");
						JSONArray itemList = orderInfo.optJSONArray("line_items");

						shopifyOrder.setNno(newNno);
						shopifyOrder.setOrgStation(orgStation);
						shopifyOrder.setDstnNation(cneeInfo.optString("country_code"));
						shopifyOrder.setDstnStation(cneeInfo.optString("country_code"));
						shopifyOrder.setUserId(userId);
						shopifyOrder.setOrderType("NOMAL");
						shopifyOrder.setOrderNo(orderNo);
						shopifyOrder.setBoxCnt("1");
						// weight 단위 g
						double userWta = Double.parseDouble(orderInfo.optString("total_weight"));
						userWta = userWta / 1000;
						shopifyOrder.setUserWta(String.valueOf(userWta));
						shopifyOrder.setUserWtc("0");
						shopifyOrder.setShipperName(shipperInfo.getComEName());
						shopifyOrder.setShipperZip(shipperInfo.getUserZip());
						shopifyOrder.setShipperTel(shipperInfo.getUserTel());
						shopifyOrder.setShipperHp(shipperInfo.getUserHp());
						shopifyOrder.setShipperCntry(shipperInfo.getOrgStation());
						shopifyOrder.setShipperCity(shipperInfo.getUserECity());
						shopifyOrder.setShipperState(shipperInfo.getUserEState());
						shopifyOrder.setShipperAddr(shipperInfo.getUserEAddr());
						shopifyOrder.setShipperAddrDetail(shipperInfo.getUserEAddrDetail());
						String cneeName = cneeInfo.optString("first_name")+" "+cneeInfo.optString("last_name");
						shopifyOrder.setCneeName(cneeName);
						shopifyOrder.setCneeAddr(cneeInfo.optString("address1"));
						shopifyOrder.setCneeZip(cneeInfo.optString("zip"));
						shopifyOrder.setCneeTel(cneeInfo.optString("phone").replaceAll(" ", ""));
						shopifyOrder.setCneeHp(cneeInfo.optString("phone").replaceAll(" ", ""));
						shopifyOrder.setCneeCntry(cneeInfo.optString("country"));
						shopifyOrder.setCneeCity(cneeInfo.optString("city"));
						if (cneeInfo.optString("country_code").toUpperCase().equals("US")) {
							shopifyOrder.setCneeState(cneeInfo.optString("province_code"));
						} else {
							shopifyOrder.setCneeState(cneeInfo.optString("province"));
						}
						shopifyOrder.setCneeAddrDetail(cneeInfo.optString("address2"));
						shopifyOrder.setUserLength("0");
						shopifyOrder.setUserWidth("0");
						shopifyOrder.setUserHeight("0");
						shopifyOrder.setUserEmail(shipperInfo.getUserEmail());
						shopifyOrder.setWUserId(userId);
						shopifyOrder.setWUserIp(request.getRemoteAddr());
						Date dateTime = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						String date = format.format(dateTime);
						shopifyOrder.setWDate(date);
						
						params.put("orgStation", shopifyOrder.getOrgStation());
						params.put("dstnNation", shopifyOrder.getDstnNation());
						String transCode = comnService.selectUserTransCode(params);
						
						shopifyOrder.setTransCode(transCode);
						String[] createDate = orderInfo.optString("created_at").split("T");
						shopifyOrder.setOrderDate(createDate[0].replaceAll("-", ""));
						shopifyOrder.setCneeEmail(orderInfo.optString("contact_email"));
						shopifyOrder.setDimUnit("CM");
						shopifyOrder.setWtUnit("KG");
						shopifyOrder.setBuySite(shipperInfo.getStoreUrl());
						shopifyOrder.setGetBuy("1");
						shopifyOrder.setPayment("DDU");
						shopifyOrder.setUploadType("Shopify");
						
						for (int item = 0; item < itemList.length(); item++) {
							UserOrderItemVO shopifyItem = new UserOrderItemVO();
							JSONObject itemOne = (JSONObject) itemList.get(item);
							int subNo = item+1;
							String itemDetail = itemOne.optString("title");
							String regex = "[^\\x20-\\x7E\\uAC00-\\uD7A3]";
							itemDetail = itemDetail.replaceAll(regex, "");
							
							shopifyItem.setNno(newNno);
							shopifyItem.setSubNo(String.valueOf(subNo));
							shopifyItem.setOrgStation(orgStation);
							shopifyItem.setUserId(userId);
							shopifyItem.setHsCode("");
							shopifyItem.setItemDetail(itemDetail.trim());
							shopifyItem.setUnitCurrency(orderInfo.optString("currency"));
							shopifyItem.setItemCnt(itemOne.optString("quantity"));
							shopifyItem.setUnitValue(itemOne.optString("price"));
							shopifyItem.setBrand(itemOne.optString("vendor"));
							shopifyItem.setWtUnit("KG");
							shopifyItem.setChgCurrency(orderInfo.optString("currency"));
							double itemWta = Double.parseDouble(orderInfo.optString("total_weight"));
							itemWta = itemWta / 1000;
							shopifyItem.setUserWta((float)itemWta);
							shopifyItem.setWUserId(userId);
							shopifyItem.setWUserIp(request.getRemoteAddr());
							shopifyItem.setWDate(date);
							
							mapper.insertUserShopOrderItemTemp(shopifyItem);
						}

						shopifyOrder.encryptData();
						mapper.insertUserShopOrderListTemp(shopifyOrder);
						mapper.insertShopifyFulfillment(params);
						
						if (userId.toLowerCase().equals("admincoreelle")) {
							shopifyOrder.setExpType("E");
							shopifyOrder.setExpCor("주식회사 코리엘");
							shopifyOrder.setExpRprsn("조신일");
							shopifyOrder.setExpAddr("서울 강남구 테헤란로 325 (역삼동) 지하 1층, 코리엘");
							shopifyOrder.setExpZip("06151");
							shopifyOrder.setExpRgstrNo("2138641988");
							shopifyOrder.setExpCstCd("코리엘**1181017");
							shopifyOrder.setAgtCor("");
							shopifyOrder.setAgtCstCd("");
							shopifyOrder.setAgtBizNo("");
							
							comnServiceImpl.execExportDeclareInfo(shopifyOrder);
						}
						
						checkCnt++;
						
					} catch (Exception e) {
						checkCnt--;
						mapper.deleteTmpOrder(newNno);
						mapper.deleteShopifyFulfillment(params);
						
					}
					
				}
				
				
				if (checkCnt > 0) {
					rst.put("Status", "S0");
					rst.put("Msg", "주문 "+orderList.length()+"건 중 "+checkCnt+"건 적용 완료");	
				} else {
					rst.put("Status", "F1");
					rst.put("Msg", "DB 에러 발생");
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			rst.put("Status", "F0");
			rst.put("Msg", "주문 수집 중 시스템 오류가 발생 하였습니다. 관리자에게 문의해 주세요.");
			return rst;
		}

		return rst;
	}

	public void createFulfillment(HashMap<String, Object> orderInfo) {
		LinkedHashMap<String, Object> header = new LinkedHashMap<String, Object>();
		HashMap<String, Object> fulfillInfo = new HashMap<String, Object>();
		
		String url = "";
		
		try {
			
			fulfillInfo = mapper.selectShopifyFulfillInfo(orderInfo);
		
			String shopifyUrl = fulfillInfo.get("shopifyUrl").toString();
			String apiKey = fulfillInfo.get("apiKey").toString();
			String accessToken = fulfillInfo.get("accessToken").toString();
			String fulfillOrderId = fulfillInfo.get("fulfillOrderId").toString();
			String hawbNo = fulfillInfo.get("hawbNo").toString();
			
			header.put("Content-Type", "application/json");
			header.put("X-Shopify-Access-Token", accessToken);
			
			String transName = shipping.getTransNameByTransCode((String) orderInfo.get("transCode"));
			
			
			LinkedHashMap<String, Object> requestMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> fulfillmentMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> trackingInfoMap = new LinkedHashMap<String, Object>();
			ArrayList<HashMap<String, Object>> fulfillOrders = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> fulfillOrder = new LinkedHashMap<String, Object>();
			fulfillOrder.put("fulfillment_order_id", fulfillOrderId);
			fulfillOrders.add(fulfillOrder);
			trackingInfoMap.put("company", transName);
			trackingInfoMap.put("number", hawbNo);
			fulfillmentMap.put("tracking_info", trackingInfoMap);
			fulfillmentMap.put("notify_customer", true);
			fulfillmentMap.put("line_items_by_fulfillment_order", fulfillOrders);
			requestMap.put("fulfillment", fulfillmentMap);
			
			String requestVal = shipping.jsonObjectToString(requestMap);
			
			url = "https://"+shopifyUrl+"/admin/api/2023-10/fulfillments.json";
			
			ApiAction action = ApiAction.getInstance();
			String responseString = action.sendPost(requestVal, url, header);
			
			if (!responseString.equals("")) {
				JSONObject response = new JSONObject(responseString);
				JSONObject fulfillment = (JSONObject) response.opt("fulfillment");
				String fulfillmentId = fulfillment.optString("id");
				
				orderInfo.put("fulfillmentId", fulfillmentId);
				
				mapper.updateShopifyFulfillment(orderInfo);
				
			}
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}

	}
	
}
