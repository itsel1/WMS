package com.example.temp.api.shipment.company;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Service
public class YunExpress {
	
	@Autowired
	ShippingMapper shipMapper;
	
	/* 윤익스프레스 API Auth Key */
	private static String apiUser = "CNK0074294";
	private static String apiKey = "274f422e9eac40bf87f0a8e2f4c794e7";
	private static String token = apiUser+"&"+apiKey;
	private static byte[] tokenBytes = token.getBytes();
	private static String tokenVal = Base64.getEncoder().encodeToString(tokenBytes);
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public YunExpress() {
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Authorization", "Basic "+tokenVal);
		apiHeader.put("Accept", "application/json");
		apiHeader.put("charset", "UTF-8");
		apiHeader.put("__lang", "en-us");
	}
	
	/* 
	 * 2024-01-08
	 * 윤익스프레스 요청 데이터 생성
	 */
	public Object createRequestBody(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");
		ArrayList<LinkedHashMap<String, Object>> requestDataList = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> itemDataList = new ArrayList<LinkedHashMap<String,Object>>();
		ArrayList<LinkedHashMap<String, Object>> orderExtraList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> receiverData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> senderData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> itemData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> extraData = new LinkedHashMap<String, Object>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		
		parameterInfo.put("nno", nno);
		orderInfo = shipMapper.selectTmpOrderListInfo(parameterInfo);
		itemInfoList = shipMapper.selectTmpOrderItemInfo(parameterInfo);
		orderInfo.dncryptData();
		
		String userId = orderInfo.getUserId();
		double userWta = makeDecimalPlaces(Double.parseDouble(orderInfo.getUserWta()), 100);
		String dimUnit = orderInfo.getDimUnit();
		if (orderInfo.getDimUnit().toUpperCase().equals("IN")) {
			dimUnit = "inch";
		}
		
		double length = Double.parseDouble(orderInfo.getUserLength());
		double width = Double.parseDouble(orderInfo.getUserWidth());
		double height = Double.parseDouble(orderInfo.getUserHeight());
		
		int declType = Integer.parseInt(orderInfo.getDeclType());
		int payment = 0;
		if (orderInfo.getPayment().toUpperCase().equals("DDP")) {
			payment = 1;
		}
		
		String shippingMethodCode = "KRTHZXR";
		
		String cosmeticYn = orderInfo.getCosmetic();
		String dstnNation = orderInfo.getDstnNation().toUpperCase();
		
		// 미국 통관 이슈로 도착지 미국 & 화장품 여부 Y인 경우 shipping Method Code > KRMUZXR 변경하여 전송
		if (dstnNation.equals("US")) {
			if (userId.toLowerCase().equals("admincoreelle") || userId.toLowerCase().equals("theskinstory") || userId.toLowerCase().equals("beautynetkr") 
					|| userId.toLowerCase().equals("happychan") || userId.toLowerCase().equals("dralthea")) {
				shippingMethodCode = "KRMUZXR";
			}
		}
		
		if (dstnNation.equals("US") && cosmeticYn.equals("Y")) {
			shippingMethodCode = "KRMUZXR";
		}
		
		
		String shipperTaxType = orderInfo.getShipperTaxType();
		String shipperTaxNo = orderInfo.getShipperTaxNo();
		String cneeTaxType = orderInfo.getCneeTaxType();
		String cneeTaxNo = orderInfo.getCneeTaxNo();
		
		String taxType = "";
		String taxNumber = "";
		String eoriNumber = "";
		String iossNumber = "";
		
		if (!cneeTaxNo.equals("") && !cneeTaxType.equals("0")) {
			taxType = cneeTaxType;
			taxNumber = cneeTaxNo;
		}
		
		if (!shipperTaxNo.equals("") && !shipperTaxType.equals("0")) {
			taxType = shipperTaxType;
			taxNumber = shipperTaxNo;
		}
		
		switch (taxType) {
		case "4":
			eoriNumber = taxNumber;
			taxNumber = "";
			iossNumber = "";
			break;
		case "6":
			iossNumber = taxNumber;
			taxNumber = "";
			eoriNumber = "";
			break;
		default:
			eoriNumber = "";
			iossNumber = "";
			break;
		}
		
		

		// 기본 정보
		requestData.put("CustomerOrderNumber", orderInfo.getNno());
		requestData.put("ShippingMethodCode", shippingMethodCode);
		requestData.put("TrackingNumber", "");
		requestData.put("TransactionNumber", "");
		requestData.put("TaxNumber", taxNumber);
		requestData.put("EoriNumber", eoriNumber);
		requestData.put("TaxCountryCode", dstnNation);
		requestData.put("ImporterName", "");
		requestData.put("ImporterAddress", "");
		requestData.put("TaxProve", "");
		requestData.put("TaxRemark", "");
		requestData.put("WarehouseAddressCode", "");
		requestData.put("SizeUnits", dimUnit);
		requestData.put("Length", (int) Math.ceil(length));
		requestData.put("Width", (int) Math.ceil(width));
		requestData.put("Height", (int) Math.ceil(height));
		requestData.put("PackageCount", Integer.parseInt(orderInfo.getBoxCnt()));
		requestData.put("Weight", userWta);
		
		// Receiver 정보
		receiverData.put("CountryCode", dstnNation);
		receiverData.put("FirstName", orderInfo.getCneeName());
		receiverData.put("LastName", "");
		receiverData.put("Company", "");
		receiverData.put("Street", orderInfo.getCneeAddr());
		receiverData.put("StreetAddress1", orderInfo.getCneeAddrDetail());
		receiverData.put("StreetAddress2", "");
		receiverData.put("City", orderInfo.getCneeCity());
		receiverData.put("State", orderInfo.getCneeState());
		receiverData.put("Zip", orderInfo.getCneeZip());
		receiverData.put("Phone", orderInfo.getCneeTel());
		receiverData.put("HouseNumber", "");
		receiverData.put("Email", orderInfo.getCneeEmail());
		receiverData.put("MobileNumber", orderInfo.getCneeHp());
		receiverData.put("CertificateCode", "");
		requestData.put("Receiver", receiverData);
		
		// Sender 정보
		senderData.put("CountryCode", "KR");
		senderData.put("FirstName", "");
		senderData.put("LastName", "");
		senderData.put("Company", orderInfo.getShipperName());
		senderData.put("Street", orderInfo.getShipperAddr());
		senderData.put("City", orderInfo.getShipperCity());
		senderData.put("State", orderInfo.getShipperState());
		senderData.put("Zip", orderInfo.getShipperZip());
		senderData.put("Phone", orderInfo.getShipperTel());
		requestData.put("Sender", senderData);
		
		requestData.put("ApplicationType", declType);
		requestData.put("ReturnOption", 0);
		requestData.put("TariffPrepay", payment);
		requestData.put("InsuranceOption", 0);
		requestData.put("SourceCode", "");
		
		requestData.put("IossCode", iossNumber);
		
		String extraCode = "V1";
		String extraName = "YunExpress Prepaid";
		
		if (!iossNumber.equals("")) {
			extraCode = "";
			extraName = "";
		}
		
		extraData.put("ExtraCode", extraCode);
		extraData.put("ExtraName", extraName);
		extraData.put("ExtraValue", "");
		extraData.put("ExtraNote", "");
		orderExtraList.add(extraData);
		requestData.put("OrderExtra", orderExtraList);
		
		int limitChars = 45;
		
		if (orderInfo.getDstnNation().toUpperCase().equals("US")) {
			limitChars = 100;
		}

		for (int i = 0; i < itemInfoList.size(); i++) {
			itemData = new LinkedHashMap<String, Object>();
			String itemName = itemInfoList.get(i).getItemDetail();
			itemName = itemName.replaceAll("[^a-zA-Z0-9 \\-,;.]", "");
			
			int itemNameLength = itemName.length();
			
			if (itemNameLength > limitChars) {
				itemName = itemName.substring(0, limitChars);
			}
			
			itemData.put("EName", itemName);
			itemData.put("CName", itemInfoList.get(i).getNativeItemDetail());
			itemData.put("HSCode", itemInfoList.get(i).getHsCode());
			itemData.put("Quantity", Integer.parseInt(itemInfoList.get(i).getItemCnt()));
			itemData.put("UnitPrice", makeDecimalPlaces(Double.parseDouble(itemInfoList.get(i).getUnitValue()), 100));
			itemData.put("UnitWeight", itemInfoList.get(i).getUserWta());
			itemData.put("Remark", "");
			itemData.put("ProductUrl", itemInfoList.get(i).getItemUrl());
			itemData.put("SKU", "");
			itemData.put("InvoiceRemark", "");
			itemData.put("CurrencyCode", itemInfoList.get(i).getChgCurrency());
			itemData.put("Attachment", "");
			itemData.put("InvoicePart", "");
			itemData.put("InvoiceUsage", "");
			
			itemDataList.add(itemData);
		}
		
		requestData.put("Parcels", itemDataList);
		requestDataList.add(requestData);
		
		return requestDataList;
	}
	
	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String errMsg = "";
		
		try {
			String url = "http://oms.api.yunexpress.com/api/WayBill/CreateOrder";
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);

			if (!responseStr.equals("")) {
				
				JSONObject response = new JSONObject(responseStr);
				
				JSONArray responseList = new JSONArray(String.valueOf(response.optString("Item")));
				JSONObject responseOne = (JSONObject) responseList.get(0);
				
				System.out.println(response);
				if (!responseOne.optString("Success").equals("0")) {
					String hawbNo = responseOne.optString("WayBillNumber");
					rstVal.setRstStatus("SUCCESS");
					rstVal.setRstCode("S");
					rstVal.setRstHawbNo(hawbNo);
				} else {
					errMsg = responseOne.optString("Remark");
					errMsg = errMsg.replaceAll(",","");
				}
				
			} else {
				errMsg = "API 응답 데이터 없음";
			}
			
		} catch (Exception e) {
			if (!errMsg.equals("")) {
				errMsg += ", " + e.getMessage();
			} else {
				errMsg += e.getMessage();
			}
		}
		
		if (!errMsg.equals("")) {
			rstVal.setRstStatus("FAIL");
			rstVal.setRstCode("F");
			rstVal.setRstMsg(errMsg);
		}
		
		return rstVal;
	}
	
	public HashMap<String, Object> getLabelUrl(HashMap<String, Object> params) {
		HashMap<String, Object> printInfo = new HashMap<String, Object>();
		String labelUrl = "";
		String hawbNo = (String) params.get("hawbNo");
		
		try {
			String url = "http://oms.api.yunexpress.com/api/Label/Print";
			
			ArrayList<String> hawbList = new ArrayList<String>();
			hawbList.add(hawbNo);
			JSONArray requestBody = new JSONArray(hawbList);
			
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestBody.toString(), url, apiHeader);
			
			if (!responseStr.equals("")) {
				JSONObject response = new JSONObject(responseStr);
				
				JSONArray responseArray = new JSONArray(String.valueOf(response.optString("Item")));
				JSONObject responseOne = (JSONObject) responseArray.get(0);
				String printUrl = responseOne.optString("Url");
				
				labelUrl = printUrl;
				
			}

			printInfo.put("labelInfo", labelUrl);
			printInfo.put("labelType", "URL");
			
		} catch (Exception e) {
			
		}
		
		return printInfo;
	}
	
	private double makeDecimalPlaces(double val, int digit) {
		double roundedValue = 0;
		roundedValue = Math.ceil(val * digit) / digit;
		
		return roundedValue;
	}

	public Object createRequestBodyTest(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");
		ArrayList<LinkedHashMap<String, Object>> requestDataList = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> itemDataList = new ArrayList<LinkedHashMap<String,Object>>();
		ArrayList<LinkedHashMap<String, Object>> orderExtraList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> receiverData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> senderData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> itemData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> extraData = new LinkedHashMap<String, Object>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		
		parameterInfo.put("nno", nno);
		orderInfo = shipMapper.selectTmpOrderListInfoTest(parameterInfo);
		itemInfoList = shipMapper.selectTmpOrderItemInfoTest(parameterInfo);
		orderInfo.dncryptData();
		
		String userId = orderInfo.getUserId();
		double userWta = makeDecimalPlaces(Double.parseDouble(orderInfo.getUserWta()), 100);
		String dimUnit = orderInfo.getDimUnit();
		if (orderInfo.getDimUnit().toUpperCase().equals("IN")) {
			dimUnit = "inch";
		}
		
		double length = Double.parseDouble(orderInfo.getUserLength());
		double width = Double.parseDouble(orderInfo.getUserWidth());
		double height = Double.parseDouble(orderInfo.getUserHeight());
		
		int declType = Integer.parseInt(orderInfo.getDeclType());
		int payment = 0;
		if (orderInfo.getPayment().toUpperCase().equals("DDP")) {
			payment = 1;
		}
		
		String shippingMethodCode = "KRTHZXR";
		
		String cosmeticYn = orderInfo.getCosmetic();
		String dstnNation = orderInfo.getDstnNation().toUpperCase();
		
		// 미국 통관 이슈로 도착지 미국 & 화장품 여부 Y인 경우 shipping Method Code > KRMUZXR 변경하여 전송
		if (dstnNation.equals("US")) {
			if (userId.toLowerCase().equals("admincoreelle") || userId.toLowerCase().equals("trexi1") || userId.toLowerCase().equals("theskinstory") || userId.toLowerCase().equals("beautynetkr") 
					|| userId.toLowerCase().equals("happychan") || userId.toLowerCase().equals("dralthea")) {
				shippingMethodCode = "KRMUZXR";
			}
		}
		
		if (dstnNation.equals("US") && cosmeticYn.equals("Y")) {
			shippingMethodCode = "KRMUZXR";
		}
		
		
		String shipperTaxType = orderInfo.getShipperTaxType();
		String shipperTaxNo = orderInfo.getShipperTaxNo();
		String cneeTaxType = orderInfo.getCneeTaxType();
		String cneeTaxNo = orderInfo.getCneeTaxNo();
		
		String taxType = "";
		String taxNumber = "";
		String eoriNumber = "";
		String iossNumber = "";
		
		if (!cneeTaxNo.equals("") && !cneeTaxType.equals("0")) {
			taxType = cneeTaxType;
			taxNumber = cneeTaxNo;
		}
		
		if (!shipperTaxNo.equals("") && !shipperTaxType.equals("0")) {
			taxType = shipperTaxType;
			taxNumber = shipperTaxNo;
		}
		
		switch (taxType) {
		case "4":
			eoriNumber = taxNumber;
			taxNumber = "";
			iossNumber = "";
			break;
		case "6":
			iossNumber = taxNumber;
			taxNumber = "";
			eoriNumber = "";
			break;
		default:
			eoriNumber = "";
			iossNumber = "";
			break;
		}
		
		

		// 기본 정보
		requestData.put("CustomerOrderNumber", orderInfo.getNno());
		requestData.put("ShippingMethodCode", shippingMethodCode);
		requestData.put("TrackingNumber", "");
		requestData.put("TransactionNumber", "");
		requestData.put("TaxNumber", taxNumber);
		requestData.put("EoriNumber", eoriNumber);
		requestData.put("TaxCountryCode", dstnNation);
		requestData.put("ImporterName", "");
		requestData.put("ImporterAddress", "");
		requestData.put("TaxProve", "");
		requestData.put("TaxRemark", "");
		requestData.put("WarehouseAddressCode", "");
		requestData.put("SizeUnits", dimUnit);
		requestData.put("Length", (int) Math.ceil(length));
		requestData.put("Width", (int) Math.ceil(width));
		requestData.put("Height", (int) Math.ceil(height));
		requestData.put("PackageCount", Integer.parseInt(orderInfo.getBoxCnt()));
		requestData.put("Weight", userWta);
		
		// Receiver 정보
		receiverData.put("CountryCode", dstnNation);
		receiverData.put("FirstName", orderInfo.getCneeName());
		receiverData.put("LastName", "");
		receiverData.put("Company", "");
		receiverData.put("Street", orderInfo.getCneeAddr());
		receiverData.put("StreetAddress1", orderInfo.getCneeAddrDetail());
		receiverData.put("StreetAddress2", "");
		receiverData.put("City", orderInfo.getCneeCity());
		receiverData.put("State", orderInfo.getCneeState());
		receiverData.put("Zip", orderInfo.getCneeZip());
		receiverData.put("Phone", orderInfo.getCneeTel());
		receiverData.put("HouseNumber", "");
		receiverData.put("Email", orderInfo.getCneeEmail());
		receiverData.put("MobileNumber", orderInfo.getCneeHp());
		receiverData.put("CertificateCode", "");
		requestData.put("Receiver", receiverData);
		
		// Sender 정보
		senderData.put("CountryCode", "KR");
		senderData.put("FirstName", "");
		senderData.put("LastName", "");
		senderData.put("Company", orderInfo.getShipperName());
		senderData.put("Street", orderInfo.getShipperAddr());
		senderData.put("City", orderInfo.getShipperCity());
		senderData.put("State", orderInfo.getShipperState());
		senderData.put("Zip", orderInfo.getShipperZip());
		senderData.put("Phone", orderInfo.getShipperTel());
		requestData.put("Sender", senderData);
		
		requestData.put("ApplicationType", declType);
		requestData.put("ReturnOption", 0);
		requestData.put("TariffPrepay", payment);
		requestData.put("InsuranceOption", 0);
		requestData.put("SourceCode", "");
		
		requestData.put("IossCode", iossNumber);
		
		String extraCode = "V1";
		String extraName = "YunExpress Prepaid";
		
		if (!iossNumber.equals("")) {
			extraCode = "";
			extraName = "";
		}
		
		
		extraData.put("ExtraCode", "");
		extraData.put("ExtraName", "");
		extraData.put("ExtraValue", "");
		extraData.put("ExtraNote", "");
		orderExtraList.add(extraData);
		requestData.put("OrderExtra", orderExtraList);
		
		int limitChars = 45;
		
		if (orderInfo.getDstnNation().toUpperCase().equals("US")) {
			limitChars = 100;
		}

		for (int i = 0; i < itemInfoList.size(); i++) {
			itemData = new LinkedHashMap<String, Object>();
			String itemName = itemInfoList.get(i).getItemDetail();
			itemName = itemName.replaceAll("[^a-zA-Z0-9 \\-,;.]", "");
			
			int itemNameLength = itemName.length();
			
			if (itemNameLength > limitChars) {
				itemName = itemName.substring(0, limitChars);
			}
			
			itemData.put("EName", itemName);
			itemData.put("CName", itemInfoList.get(i).getNativeItemDetail());
			itemData.put("HSCode", itemInfoList.get(i).getHsCode());
			itemData.put("Quantity", Integer.parseInt(itemInfoList.get(i).getItemCnt()));
			itemData.put("UnitPrice", makeDecimalPlaces(Double.parseDouble(itemInfoList.get(i).getUnitValue()), 100));
			itemData.put("UnitWeight", itemInfoList.get(i).getUserWta());
			itemData.put("Remark", "");
			itemData.put("ProductUrl", itemInfoList.get(i).getItemUrl());
			itemData.put("SKU", "");
			itemData.put("InvoiceRemark", "");
			itemData.put("CurrencyCode", itemInfoList.get(i).getChgCurrency());
			itemData.put("Attachment", "");
			itemData.put("InvoicePart", "");
			itemData.put("InvoiceUsage", "");
			
			itemDataList.add(itemData);
		}
		
		requestData.put("Parcels", itemDataList);
		requestDataList.add(requestData);
		
		return requestDataList;
	}

	public ShipmentVO createShipmentTest(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String errMsg = "";
		
		try {
			String url = "http://oms.api.yunexpress.com/api/WayBill/CreateOrder";
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);
			System.out.println(requestVal);
			if (!responseStr.equals("")) {
				
				JSONObject response = new JSONObject(responseStr);
				
				JSONArray responseList = new JSONArray(String.valueOf(response.optString("Item")));
				JSONObject responseOne = (JSONObject) responseList.get(0);
				
				System.out.println(response);
				if (!responseOne.optString("Success").equals("0")) {
					String hawbNo = responseOne.optString("WayBillNumber");
					rstVal.setRstStatus("SUCCESS");
					rstVal.setRstCode("S");
					rstVal.setRstHawbNo(hawbNo);
				} else {
					errMsg = responseOne.optString("Remark");
					errMsg = errMsg.replaceAll(",","");
				}
				
			} else {
				errMsg = "API 응답 데이터 없음";
			}
			
		} catch (Exception e) {
			if (!errMsg.equals("")) {
				errMsg += ", " + e.getMessage();
			} else {
				errMsg += e.getMessage();
			}
		}
		
		if (!errMsg.equals("")) {
			rstVal.setRstStatus("FAIL");
			rstVal.setRstCode("F");
			rstVal.setRstMsg(errMsg);
		}
		
		return rstVal;
	}

}
