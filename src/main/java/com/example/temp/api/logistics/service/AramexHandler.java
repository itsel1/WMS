package com.example.temp.api.logistics.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.api.CommonUtils;
import com.example.temp.api.Item;
import com.example.temp.api.Order;
import com.example.temp.api.logistics.dto.AramexParameter;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.common.vo.ComnVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class AramexHandler {
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	CommonUtils utils;
	
	@Value("${filePath}")
	String filePath;
	
	ComnVO comnS3Info;
	AmazonS3 amazonS3;
	
	
	private static final String HOST = "https://ws.aramex.net";
	private static final String ACNT_NUM = "172813";
	private static final String ACNT_CNTRY_CODE = "KR";
	private static final String ACNT_ENTITY = "SEL";
	private static final String ACNT_PIN = "321321";
	private static final String USER_NAME = "overseas2@aciexpress.net";
	private static final String PASSWORD = "Aci5606!";
	private static final String VERSION = "v1.0";
	
	
	public Order createShipments(Order order) {
		
		String path = "/ShippingAPI.V2/Shipping/Service_1_0.svc/json/CreateShipments";
		String httpUrl = HOST + path;
		String errorMsg = "";
		HashMap<String, Object> requestBodyMap = getShipmentRequestBody(order);
		String requestBody = getRequestBody(requestBodyMap);
		System.out.println(requestBody);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, utils.buildApiDefaltHeaders());
		System.out.println(responseMap);
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			JSONObject shipment = jsonObject.getJSONArray("Shipments").getJSONObject(0);
			boolean hasErrors = shipment.optBoolean("HasErrors");
			
			if (!hasErrors) {
				JSONObject shipmentLabel = shipment.getJSONObject("ShipmentLabel");
				order.setHawbNo(shipment.optString("ID"));
				order.setLabelUrl(shipmentLabel.optString("LabelURL"));
				
				JSONObject shipmentDetails = shipment.getJSONObject("ShipmentDetails");
				JSONObject chargeableWeight = shipmentDetails.getJSONObject("ChargeableWeight");
				JSONObject customsValueAmount = shipmentDetails.getJSONObject("CustomsValueAmount");
				AramexParameter araParams = new AramexParameter();
				
				araParams.setOrigin(shipmentDetails.optString("Origin"));
				araParams.setDestination(shipmentDetails.optString("Destination"));
				araParams.setChgWtUnit(chargeableWeight.optString("Unit"));
				araParams.setChgWtValue(chargeableWeight.optDouble("Value"));
				araParams.setDescriptionOfGoods(shipmentDetails.optString("DescriptionOfGoods"));
				araParams.setGoodsOriginCountry(shipmentDetails.optString("GoodsOriginCountry"));
				araParams.setNumberOfPieces(shipmentDetails.optInt("NumberOfPieces"));
				araParams.setProductGroup(shipmentDetails.optString("ProductGroup"));
				araParams.setProductType(shipmentDetails.optString("ProductType"));
				araParams.setPaymentType(shipmentDetails.optString("PaymentType"));
				araParams.setPaymentOptions(shipmentDetails.optString("PaymentOptions"));
				araParams.setCustomsValueAmountCurrencyCode(customsValueAmount.optString("CurrencyCode"));
				araParams.setCustomsValueAmountValue(customsValueAmount.optDouble("Value"));
				araParams.setServices(shipmentDetails.optString("Services"));
				araParams.setOriginCity(shipmentDetails.optString("OriginCity"));
				araParams.setDestinationCity(shipmentDetails.optString("DestinationCity"));
				order.setAraParams(araParams);
				
			} else {
				JSONArray notifications = shipment.getJSONArray("Notifications");
				for (int i = 0; i < notifications.length(); i++) {
					JSONObject noti = notifications.getJSONObject(i);
					String[] messages = noti.optString("Message").split(" - ");
					errorMsg += messages[1] + ",";
				}
				System.err.println(errorMsg);
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			errorMsg += "현지 배송사 시스템 연동 중 오류 발생,";
		}
		
		order.setStatus(errorMsg);
		
		return order;
	}
	
	
	private HashMap<String, Object> getShipmentRequestBody(Order order) {
		ArrayList<Item> orderItemList = order.getItemList(); 
		HashMap<String, Object> requestBody = new HashMap<>();
		
		String shipperCountryCode = "";
		if ("082".equals(order.getOrgStation())) {
			shipperCountryCode = "KR";
		} else if ("213".equals(order.getOrgStation())) {
			shipperCountryCode = "US";
		} else if ("441".equals(order.getOrgStation())) {
			shipperCountryCode = "GB";
		}
		
		HashMap<String, Object> clientInfo = new HashMap<>();
		clientInfo.put("UserName", USER_NAME);
		clientInfo.put("Password", PASSWORD);
		clientInfo.put("Version", VERSION);
		clientInfo.put("AccountNumber", ACNT_NUM);
		clientInfo.put("AccountPin", ACNT_PIN);
		clientInfo.put("AccountEntity", ACNT_ENTITY);
		clientInfo.put("AccountCountryCode", ACNT_CNTRY_CODE);
		clientInfo.put("Source", 24);
		requestBody.put("ClientInfo", clientInfo);
		
		HashMap<String, Object> labelInfo = new HashMap<>();
		labelInfo.put("ReportID", 9201);
		labelInfo.put("ReportType", "URL");
		requestBody.put("LabelInfo", labelInfo);
		
		ArrayList<HashMap<String, Object>> shipmentList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> shipment = new HashMap<>();
		shipment.put("Reference1", "");
		shipment.put("Reference2", "");
		shipment.put("Reference3", "");
		
		HashMap<String, Object> shipper = new HashMap<>();
		shipper.put("Reference1", order.getShipperReference());
		shipper.put("Reference2", "");
		shipper.put("AccountNumber", ACNT_NUM);
		
		HashMap<String, Object> shipperPartyAddress = new HashMap<>();
		shipperPartyAddress.put("Line1", order.getShipperAddr());
		shipperPartyAddress.put("Line2", order.getShipperAddrDetail());
		shipperPartyAddress.put("Line3", "");
		shipperPartyAddress.put("City", order.getShipperCity());
		shipperPartyAddress.put("StateOrProvinceCode", order.getShipperState());
		shipperPartyAddress.put("PostCode", order.getShipperZip());
		shipperPartyAddress.put("CountryCode", shipperCountryCode);
		shipperPartyAddress.put("Longitude", 0);
		shipperPartyAddress.put("Latitude", 0);
		shipperPartyAddress.put("BuildingNumber", null);
		shipperPartyAddress.put("BuildingName", null);
		shipperPartyAddress.put("Floor", null);
		shipperPartyAddress.put("Apartment", null);
		shipperPartyAddress.put("POBox", null);
		shipperPartyAddress.put("Description", null);
		shipper.put("PartyAddress", shipperPartyAddress);
		
		String shipperHp = order.getShipperHp();
		if (shipperHp.isEmpty()) {
			shipperHp = order.getShipperTel();
		}
		
		HashMap<String, Object> shipperContact = new HashMap<>();
		shipperContact.put("Department", "");
		shipperContact.put("PersonName", order.getShipperName());
		shipperContact.put("Title", "");
		shipperContact.put("CompanyName", order.getShipperName());
		shipperContact.put("PhoneNumber1", order.getShipperTel());
		shipperContact.put("PhoneNumber1Ext", "");
		shipperContact.put("PhoneNumber2", "");
		shipperContact.put("PhoneNumber2Ext", "");
		shipperContact.put("FaxNumber", "");
		shipperContact.put("CellPhone", shipperHp);
		shipperContact.put("EmailAddress", order.getUserEmail());
		shipperContact.put("Type", "");
		shipper.put("Contact", shipperContact);
		shipment.put("Shipper", shipper);
		
		HashMap<String, Object> consignee = new HashMap<>();
		consignee.put("Reference1", "");
		consignee.put("Reference2", "");
		consignee.put("AccountNumber", "");
		
		HashMap<String, Object> consigneePartyAddress = new HashMap<>();
		consigneePartyAddress.put("Line1", order.getCneeAddr());
		consigneePartyAddress.put("Line2", order.getCneeAddrDetail());
		consigneePartyAddress.put("Line3", "");
		consigneePartyAddress.put("City", order.getCneeCity());
		consigneePartyAddress.put("StateOrProvinceCode", "");
		consigneePartyAddress.put("PostCode", order.getCneeZip());
		consigneePartyAddress.put("CountryCode", order.getDstnNation().toUpperCase());
		consigneePartyAddress.put("Longitude", 0);
		consigneePartyAddress.put("Latitude", 0);
		consigneePartyAddress.put("BuildingNumber", null);
		consigneePartyAddress.put("BuildingName", null);
		consigneePartyAddress.put("Floor", null);
		consigneePartyAddress.put("Apartment", null);
		consigneePartyAddress.put("POBox", null);
		consigneePartyAddress.put("Description", null);
		consignee.put("PartyAddress", consigneePartyAddress);

		String cneeHp = order.getCneeHp();
		if (cneeHp.isEmpty()) {
			cneeHp = order.getCneeTel();
		}
		HashMap<String, Object> consigneeContact = new HashMap<>();
		consigneeContact.put("Department", "");
		consigneeContact.put("PersonName", order.getCneeName());
		consigneeContact.put("Title", "");
		consigneeContact.put("CompanyName", order.getCneeName());
		consigneeContact.put("PhoneNumber1", order.getCneeTel());
		consigneeContact.put("PhoneNumber1Ext", "");
		consigneeContact.put("PhoneNumber2", "");
		consigneeContact.put("PhoneNumber2Ext", "");
		consigneeContact.put("FaxNumber", "");
		consigneeContact.put("CellPhone", cneeHp);
		consigneeContact.put("EmailAddress", order.getCneeEmail());
		consigneeContact.put("Type", "");
		consignee.put("Contact", consigneeContact);
		shipment.put("Consignee", consignee);
		
		Date now = new Date();
		long timestamp = now.getTime();
		String dateString = "\\/"+"Date("+timestamp+"+0900"+")\\/";

		shipment.put("ShippingDateTime", dateString);
		shipment.put("DueDate", dateString);
		shipment.put("Comments", order.getDlvReqMsg());
		shipment.put("PickupLocation", "");
		shipment.put("OperationsInstructions", "");
		shipment.put("AccountingInstrcutions", "");
		
		HashMap<String, Object> details = new HashMap<>();
		
		HashMap<String, Object> dimensions = new HashMap<>();
		String dimUnit = order.getDimUnit().toUpperCase();
		double width = order.getUserWidth();
		double height = order.getUserHeight();
		double length = order.getUserLength();
		
		if ("IN".equals(dimUnit)) {
			width = width*2.54;
			height = height*2.54;
			length = length*2.54;
		}

		dimensions.put("Width", width);
		dimensions.put("Height", height);
		dimensions.put("Length", length);
		dimensions.put("Unit", "CM");
		details.put("Dimensions", dimensions);
		
		HashMap<String, Object> actualWeight = new HashMap<>();
		String wtUnit = order.getWtUnit().toUpperCase();
		double wta = order.getUserWta();
		wta = utils.getConvertWeightKg(wta, wtUnit);
		actualWeight.put("Unit", "KG");
		actualWeight.put("Value", wta);
		details.put("ActualWeight", actualWeight);
		details.put("ChargeableWeight", null);
		details.put("DescriptionOfGoods", orderItemList.get(0).getItemDetail());
		details.put("GoodsOriginCountry", orderItemList.get(0).getMakeCntry());
		details.put("NumberOfPieces", 1);
		
		String productType = "PPX";
		String productGroup = "EXP";
		
		if ("SY".equals(order.getDstnNation().toUpperCase())) {
			productType = "OND";
			productGroup = "DOM";
		}

		details.put("ProductGroup", productGroup);
		details.put("ProductType", productType);
		details.put("PaymentType", "P");
		details.put("PaymentOptions", "");
		details.put("CustomsValueAmount", null);
		details.put("CashOnDeliveryAmount", null);
		details.put("InsuranceAmount", null);
		details.put("CashAdditionalAmount", null);
		details.put("CashAdditionalAmountDescription", "");
		details.put("CollectAmount", null);
		
		String services = "";
		
		if ("DDP".equals(order.getPayment())) {
			services = "FRDM";
		}
		
		details.put("Services", services);

		double totalAmount = 0;
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < orderItemList.size(); i++) {
			HashMap<String, Object> item = new HashMap<>();
			item.put("PackageType", "Box");
			item.put("Quantity", orderItemList.get(i).getItemCnt());
			item.put("Weight", null);
			HashMap<String, Object> customsValue = new HashMap<>();
			customsValue.put("CurrencyCode", orderItemList.get(i).getChgCurrency().toUpperCase());
			customsValue.put("Value", orderItemList.get(i).getUnitValue()*orderItemList.get(i).getItemCnt());
			item.put("CustomsValue", customsValue);
			item.put("Comments", orderItemList.get(i).getItemDiv());
			item.put("GoodsDescription", orderItemList.get(i).getItemDetail());
			item.put("Reference", "");
			item.put("CommodityCode", "");
			
			totalAmount += orderItemList.get(i).getUnitValue()*orderItemList.get(i).getItemCnt();
			itemList.add(item);
		}
		
		details.put("Items", itemList);
		
		HashMap<String, Object> customsValueAmount = new HashMap<>();
		customsValueAmount.put("Value", totalAmount);
		customsValueAmount.put("CurrencyCode", orderItemList.get(0).getChgCurrency().toUpperCase());
		details.put("CustomsValueAmount", customsValueAmount);
		
		shipment.put("Details", details);
		shipment.put("ForeignHAWB", order.getNno());
		shipment.put("PickupGUID", "");
		shipment.put("TransportType", 0);
		
		shipmentList.add(shipment);
		requestBody.put("Shipments", shipmentList);
		
		HashMap<String, Object> transaction = new HashMap<>();
		transaction.put("Reference1", "001");
		transaction.put("Reference2", "");
		transaction.put("Reference3", "");
		transaction.put("Reference4", "");
		transaction.put("Reference5", "");
		requestBody.put("Transaction", transaction);
		
		return requestBody;
	}
	
	
	private String getRequestBody(HashMap<String, Object> map) {
		Gson gson = new GsonBuilder()
							.disableHtmlEscaping()
							.serializeNulls()
							.create();
		String jsonString = gson.toJson(map);
		jsonString = jsonString.replace("\\\\/", "\\/");
		return jsonString;
	}
	
	
	public String getLabelZplFormat(Order order) {
		String encodeText = "";
		
		String nno = order.getNno();
		String date = nno.substring(0,8);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);
		String dateFmt = localDate.format(outputFormatter);
		
		String address = order.getCneeAddr().trim();
		if (!order.getCneeAddrDetail().isEmpty()) {
			address += " - " + order.getCneeAddrDetail().trim();
		}
		
		String[] addresses = new String[2];
		if (address.length() > 81) {
			addresses[0] = address.substring(0,81);
			addresses[1] = address.substring(81);
		} else {
			addresses[0] = address;
			addresses[1] = "";
		}
		
		String shipperCountryCode = "";
		if ("082".equals(order.getOrgStation())) {
			shipperCountryCode = "KR";
		} else if ("213".equals(order.getOrgStation())) {
			shipperCountryCode = "US";
		} else if ("441".equals(order.getOrgStation())) {
			shipperCountryCode = "GB";
		}
		
		AramexParameter araParams = order.getAraParams();
		
		String description = "Description: " + araParams.getDescriptionOfGoods();
		if (description.length() > 68) {
			description = description.substring(0,68);
		}
		
		String cneeTel = order.getCneeTel();
		String cneeHp = order.getCneeHp();
		if (cneeHp.isEmpty()) {
			cneeHp = cneeTel;
		}

		StringBuilder sb = new StringBuilder();
		
		// 시작
		sb.append("^XA");
		
        // 사각형 및 구역
        sb.append("^FO20,15^GB772,1188,2^FS");
        sb.append("^FO20,15^GB772,170,1^FS");
        sb.append("^FO20,15^GB250,170,1^FS");
        sb.append("^FO20,184^GB140,90,1^FS");
        sb.append("^FO160,184^GB632,90,1^FS");
        sb.append("^FO20,273^GB140,70,1^FS");
        sb.append("^FO160,273^GB140,70,1^FS");
        sb.append("^FO300,273^GB492,70,1^FS");
        sb.append("^FO20,342^GB772,50,1^FS");
        sb.append("^FO20,391^GB772,150,1^FS");
        sb.append("^FO20,540^GB772,150,1^FS");
        sb.append("^FO20,922^GB772,50,5^FS");
        sb.append("^FO20,1112^GB772,90,1^FS");

        // 아라멕스 로고
        sb.append("^FO25,25^GFA,1170,1170,30,,:K078N07CI0FP0EJ038L03E,J07FF83FC1FE1FF80FFE07F83FC0FFC003FF8J03IF001FFI03FE,I01IFE3FE1FE7FF83IF87F83FE3IF00IFCJ0JFC01FF8007FE,I03JF3FE1KF0JFE7F83FE7IF81JFI03KF00FFC00FFC,I07LFE1JFE1MF83LFC7JF8007KF80FFC01FF8,I0MFE1JFC3MF83LFE7JF800LFC07FE01FF8,001MFE1JFC7MF83RFC01LFE03FF03FF,003MFE1JF87MF83RFC03MF03FF87FE,007FFE3IFE1IF00IFC7IF83IF9KF7FFC03FF807FF01FF87FE,007FF007FFE1FFC00FFE00IF83FFC07IF80FFE07FE001FF80FFCFFC,00FFE001FFE1FF801FFC003FF83FF803IF007FE07FCI0FF807JF8,00FFCI0FFE1FF801FF8001FF83FF001FFE007FE0FF8I07FC07JF,00FF8I07FE1FF003FFI01FF83FF001FFC003FE0FF8I03FC03JF,01FF8I07FE1FF003FEJ0FF83FEI0FFC003FE1FFJ03FE01IFE,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1NFE00IFC,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1NFE00IF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE003FF,01FEJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF,01FEJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE00IFC,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1FEN01IFE,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1FFN03IFE,01FFJ07FE1FF003FEJ0FF83FEI0FF8003FE1FFN03JF,00FF8I07FE1FF003FFI01FF83FEI0FF8003FE0FFN07JF8,00FFCI0FFE1FF001FFI01FF83FEI0FF8003FE0FF8M0FFDFFC,00FFE001FFE1FF001FF8003FF83FEI0FF8003FE0FFCI06I0FFCFFC,007FF003FFE1FF001FFE007FF83FEI0FF8003FE07FE001F801FF87FE,007FFC0IFE1FFI0IF81IF83FEI0FF8003FE07FF803FE03FF03FF,003MFE1FFI07MF83FEI0FF8003FE03MF07FE03FF,001MFE1FFI07MF83FEI0FF8003FE01MF07FE01FF8,001MFE1FFI03MF83FEI0FF8003FE00LFE0FFC00FFC,I0MFE1FFI01MF83FEI0FF8003FE007KFC1FF800FFE,I03JFBFE1FFJ0JFE7F83FEI0FF8003FE003KF81FF8007FE,I01IFE3FE1FFJ03IFC7F83FEI0FF8003FE001JFE03FFI03FF,J07FFC3FE1FFJ01IF07F83FEI0FF8003FEI07IF807FEI01FF8,J01FER03FCgH0FFC,^FS");

        // 바코드
        sb.append("^FO295,30^BY3^BCN,110,Y,N,N^FD34067165414^FS");

        // 필드 데이터
        sb.append("^FO30,90^A0N,20,20^FDOrigin:^FS");
        sb.append("^FO35,120^ABN,40,27^FD").append(araParams.getOrigin()).append("^FS");
        sb.append("^FO30,190^A0N,20,20^FDDestination:^FS");
        sb.append("^FO35,215^ABN,40,27^FD").append(araParams.getDestination()).append("^FS");
        sb.append("^FO170,190^A0N,20,20^FDDate: ").append(dateFmt).append("^FS");
        sb.append("^FO170,220^A0N,20,20^FDForeign Ref: ").append(nno).append("^FS");
        sb.append("^FO170,250^A0N,20,20^FDRef1:^FS");
        sb.append("^FO35,285^ABN,40,27^FD").append(araParams.getProductGroup()).append("^FS");
        sb.append("^FO175,285^ABN,40,27^FD").append(araParams.getProductType()).append("^FS");
        sb.append("^FO315,285^ABN,40,27^FD").append(araParams.getPaymentType()).append("^FS");
        sb.append("^FO30,400^A0N,25,25^FDWeight: ").append(order.getUserWta()).append(" ").append(order.getWtUnit().toUpperCase()).append("    Chargeable: ").append(araParams.getChgWtValue()).append(" ").append(araParams.getChgWtUnit()).append("^FS");
        sb.append("^FO30,435^A0N,25,25^FDServices:").append(araParams.getServices()).append("^FS");
        sb.append("^FO30,470^A0N,25,25^FDPieces: ").append(araParams.getNumberOfPieces()).append("^FS");
        sb.append("^FO30,505^A0N,25,25^FD").append(description).append("^FS");
        sb.append("^FO30,550^A0N,25,25^FDAccount:    ").append(ACNT_NUM).append("^FS");
        sb.append("^FO30,585^A0N,25,25^FD").append(order.getShipperName()).append("^FS");
        sb.append("^FO30,620^A0N,25,25^FD").append(order.getShipperName()).append("^FS");
        sb.append("^FO30,655^A0N,25,25^FD").append(araParams.getOriginCity()).append("^FS");
        sb.append("^FO580,655^A0N,25,25^FD").append(shipperCountryCode).append("^FS");
        sb.append("^FO30,700^A0N,27,27^FD").append(order.getCneeName()).append("^FS");
        sb.append("^FO30,730^A0N,27,27^FD").append(order.getCneeName()).append("^FS");
        sb.append("^FO30,765^A0N,23,23^FD").append(addresses[0]).append("^FS");
        sb.append("^FO30,795^A0N,23,23^FD").append(addresses[1]).append("^FS");
        sb.append("^FO30,825^A0N,25,25^FD").append(araParams.getDestinationCity()).append("^FS");
        sb.append("^FO30,855^A0N,25,25^FD").append(order.getCneeState()).append("^FS");
        sb.append("^FO580,855^A0N,25,25^FD").append(order.getDstnNation().toUpperCase()).append("^FS");
        sb.append("^FO30,890^A0N,25,25^FD").append(cneeTel).append("^FS");
        sb.append("^FO550,890^A0N,25,25^FD").append(cneeHp).append("^FS");
        sb.append("^FO30,930^A0N,25,25^FDRoute:^FS");
        sb.append("^FO30,975^A0N,25,25^FDRemarks:^FS");
        sb.append("^FO30,1122^A0N,20,20^FDShipper Ref: ").append(order.getShipperReference()).append("^FS");
        sb.append("^FO30,1150^A0N,20,20^FDConsignee Ref: ").append(order.getCneeReference1()).append("^FS");
        sb.append("^FO30,1175^A0N,20,20^FDConsignee Ref2:").append(order.getCneeReference2()).append("^FS");
        
        // 끝
        sb.append("^XZ");
		
        try {
        	encodeText = utils.encodeToBase64(sb.toString());
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
        
		return encodeText;
	}



}
