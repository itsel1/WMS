package com.example.temp.api.shipment.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ResaleMallVO;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.api.shipment.ShippingServiceImpl;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Service
public class Fastbox {

	@Autowired
	ShippingMapper shipMapper;
	
	@Autowired
	ShippingServiceImpl shipping;
	
	
	private static String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
	private static String authKey = "tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	public Fastbox() {
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Authorization", "Bearer "+authKey);
		apiHeader.put("consumerKey", consumerKey);
		apiHeader.put("Accept-Language", "ko-KR");
	}
	
	public Object createRequestBody(HashMap<String, Object> params) {
		ResaleMallVO mallInfo = new ResaleMallVO();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> orderData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> orderDataList = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> itemDataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> itemData = new LinkedHashMap<String, Object>();
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		orderInfo = shipMapper.selectTmpOrderListInfo(params);
		itemInfoList = shipMapper.selectTmpOrderItemInfo(params);
		orderInfo.dncryptData();
		
		mallInfo = shipMapper.selectFastboxMallInfo(orderInfo.getUserId());
		mallInfo.dncryptData();
		
		String address = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			address += " " + orderInfo.getCneeAddrDetail();
		}
		
		String currencyCode = itemInfoList.get(0).getUnitCurrency();
		if (currencyCode.equals("")) {
			currencyCode = itemInfoList.get(0).getChgCurrency();
		}
		
		String cneeTel = orderInfo.getCneeTel();
		cneeTel = cneeTel.replaceAll("[^0-9-]", "");
		String cneeHp = orderInfo.getCneeHp();
		cneeHp = cneeHp.replaceAll("[^0-9-]", "");
		if (cneeHp.equals("")) {
			cneeHp = cneeTel;
		}
		
	
		String wantDelCom = "";
		if (orderInfo.getTransCode().equals("FB")) {
			wantDelCom = "UPS";
		} else {
			wantDelCom = "EMS";
		}
		
		String payment = orderInfo.getPayment();
		
		address = address.replaceAll("line.separator".toString(), "");
		orderData.put("seller_name", mallInfo.getSellerName());
		orderData.put("ord_date", orderInfo.getOrderDate());
		orderData.put("ord_bundle_no", orderInfo.getOrderNo());
		orderData.put("currency_code", currencyCode);
		orderData.put("country_domain", orderInfo.getDstnNation());
		orderData.put("receiver_name", orderInfo.getCneeName());
		orderData.put("receiver_name_voice", orderInfo.getNativeCneeName());
		orderData.put("receiver_phone", cneeTel);
		orderData.put("receiver_cell", cneeHp);
		orderData.put("receiver_zipcode", orderInfo.getCneeZip());
		orderData.put("receiver_address1", orderInfo.getCneeState());
		orderData.put("receiver_address2", orderInfo.getCneeCity());
		orderData.put("receiver_address3", address);
		orderData.put("want_deliverer", wantDelCom);
		orderData.put("resale_brand_name", mallInfo.getBrandName());
		orderData.put("resale_ceo_name", mallInfo.getAttnName());
		orderData.put("resale_company_name", mallInfo.getComName());
		orderData.put("resale_shop_biz_no", mallInfo.getComRegNo());
		orderData.put("resale_shop_address_eng", mallInfo.getSellerAddrDetail()+" "+mallInfo.getSellerAddr());
		
		if (orderInfo.getExpYn() > 0) {
			orderData.put("resale_use_export_declare", "P");
			orderData.put("resale_declare_price_type", "S");
			orderData.put("resale_customs_id", "미투유코1171017");
			orderData.put("resale_declare_zipcode", "07641");
			orderData.put("resale_declare_address1", "서울특별시 강서구 외발산동 217-1");
			orderData.put("resale_declare_address2", "ACI 빌딩");	
		} else {
			orderData.put("resale_use_export_declare", "F");
		}
		
		orderData.put("resale_sender_name", mallInfo.getShipperName());
		
		if (payment.equals("DDP")) {
			orderData.put("resale_delivery_duty_type", "P");
		} else {
			orderData.put("resale_delivery_duty_type", "U");
		}
		
		for (int i = 0; i < itemInfoList.size(); i++) {
			itemData = new LinkedHashMap<String, Object>();
			int number = i+1;
			itemData.put("seller_ord_code", orderInfo.getOrderNo());
			itemData.put("seller_ord_item_code", orderInfo.getOrderNo()+"-"+number);
			itemData.put("prd_code", itemInfoList.get(i).getCusItemCode());
			itemData.put("input_prd_name", itemInfoList.get(i).getItemDetail());
			itemData.put("input_item_name", itemInfoList.get(i).getItemDiv());
			itemData.put("ord_qty", itemInfoList.get(i).getItemCnt());
			itemData.put("selling_price", itemInfoList.get(i).getUnitValue());
			itemData.put("hs_code", itemInfoList.get(i).getHsCode());
			itemData.put("prd_category", itemInfoList.get(i).getItemDiv());
			itemData.put("prd_category_info", itemInfoList.get(i).getItemDiv());
			
			if (orderInfo.getDstnNation().equals("JP")) {
				itemData.put("material", itemInfoList.get(i).getItemMeterial());
				itemData.put("cloth_material", itemInfoList.get(i).getItemMeterial());
			}
			itemDataList.add(itemData);
		}
		orderData.put("item_list", itemDataList);
		orderDataList.add(orderData);

		requestData.put("mall_id", "aciexpress2");
		requestData.put("request_data", orderDataList);
		
		return requestData;
	}

	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String errMsg = "";
		
		try {
			String url = "https://dhub-api.cafe24.com/api/order/add/resale";
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);
			
			if (!responseStr.equals("")) {
				JSONObject response = new JSONObject(responseStr);
				JSONArray responseList = new JSONArray(String.valueOf(response.optString("response")));
				JSONObject meta = response.optJSONObject("meta");
				JSONObject responseOne = (JSONObject) responseList.get(0);

				if (meta.optString("code").equals("200")) {
					
					// result = true
					if (responseOne.optBoolean("result")) {
						
					} else {
						
						// result_reason 값이 Json 타입인 경우
						if (responseOne.get("result_reason") instanceof JSONObject) {
							JSONObject resultReason = responseOne.optJSONObject("result_reason");
							
							
						// result_reason 값이 String 타입인 경우
						} else if (responseOne.get("result_reason") instanceof String) {
							errMsg = responseOne.optString("result_reason");
						}
					}
					
				} else {
					
				}
			} else {
				errMsg = "API 응답 데이터 없음";
			}
			
		} catch (Exception e) {
			
		}
		
		return rstVal;
	}
}
