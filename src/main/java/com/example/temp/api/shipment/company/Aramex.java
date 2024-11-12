package com.example.temp.api.shipment.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Service
public class Aramex {
	
	@Autowired
	ShippingMapper shipMapper;
	
	@Autowired
	ComnService comnService;

	private static String userName = "overseas2@aciexpress.net";
	private static String password = "Aci5606!";
	private static String version = "v1.0";
	private static String accountNumber = "172813";
	private static String accountPin = "321321";
	private static String accountEntity = "SEL";
	private static String accountCountryCode = "KR";
	
	
	public Object createRequestBody(HashMap<String, Object> params) throws Exception {
		String nno = (String) params.get("nno");
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> clientInfo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> labelInfo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> shipments = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> shipmentsList = new ArrayList<LinkedHashMap<String, Object>>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		
		parameterInfo.put("nno", nno);
		orderInfo = shipMapper.selectTmpOrderListInfo(parameterInfo);
		itemInfoList = shipMapper.selectTmpOrderItemInfo(parameterInfo);
		orderInfo.dncryptData();
		
		
		clientInfo.put("UserName", userName);
		clientInfo.put("Password", password);
		clientInfo.put("Version", version);
		clientInfo.put("AccountNumber", accountNumber);
		clientInfo.put("AccountPin", accountPin);
		clientInfo.put("AccountEntity", accountEntity);
		clientInfo.put("AccountCountryCode", accountCountryCode);
		clientInfo.put("Source", 24);
		requestData.put("ClientInfo", clientInfo);
		
		labelInfo.put("ReportID", 9201);
		labelInfo.put("ReportType", "URL");
		requestData.put("LabelInfo", labelInfo);
		
		shipments.put("Reference1", "");
		shipments.put("Reference2", "");
		shipments.put("Reference3", "");
		
		LinkedHashMap<String, Object> shipper = new LinkedHashMap<String, Object>();
		shipper.put("Reference1", orderInfo.getShipperReference());
		shipper.put("Reference2", "");
		shipper.put("Reference3", "");
		shipper.put("AccountNumber", "172813");
		
		LinkedHashMap<String, Object> shipperPartyAddress = new LinkedHashMap<String, Object>();
		shipperPartyAddress.put("Line1", orderInfo.getShipperAddr());
		shipperPartyAddress.put("Line2", orderInfo.getShipperAddrDetail());
		shipperPartyAddress.put("Line3", "");
		shipperPartyAddress.put("City", orderInfo.getShipperCity());
		shipperPartyAddress.put("StateOrProvinceCode", "");
		shipperPartyAddress.put("PostCode", orderInfo.getShipperZip());
		
		if (orderInfo.getShipperCntry().equals("")) {
			orderInfo.setShipperCntry(comnService.selectStationToNation(orderInfo.getOrgStation()));
		}
		shipperPartyAddress.put("CountryCode", orderInfo.getShipperCntry());
		shipperPartyAddress.put("Longitude", 0);
		shipperPartyAddress.put("Latitude", 0);
		shipperPartyAddress.put("BuildingNumber", "");
		shipperPartyAddress.put("BuildingName", "");
		shipperPartyAddress.put("Floor", "");
		shipperPartyAddress.put("Apartment", "");
		shipperPartyAddress.put("POBox", "");
		shipperPartyAddress.put("Description", "");
		shipper.put("PartyAddress", shipperPartyAddress);
		
		LinkedHashMap<String, Object> shipperContact = new LinkedHashMap<String, Object>();
		shipperContact.put("Department", "");
		shipperContact.put("PersonName", orderInfo.getShipperName());
		shipperContact.put("Title", "");
		shipperContact.put("CompanyName", orderInfo.getShipperName());
		
		String shipperHp = "";
		if (orderInfo.getShipperHp().equals("")) {
			shipperHp = orderInfo.getShipperTel();
		}
				
		shipperContact.put("PhoneNumber1", shipperHp);
		shipperContact.put("PhoneNumber1Ext", "");
		shipperContact.put("PhoneNumber2", "");
		shipperContact.put("PhoneNumber2Ext", "");
		shipperContact.put("FaxNumber", "");
		shipperContact.put("CellPhone", orderInfo.getShipperHp());
		shipperContact.put("EmailAddress", orderInfo.getShipperEmail());
		shipperContact.put("Type", "");
		shipper.put("Contact", shipperContact);
		shipments.put("Shipper", shipper);
		
		LinkedHashMap<String, Object> consignee = new LinkedHashMap<String, Object>();
		consignee.put("Reference1", orderInfo.getCneeReference1());
		consignee.put("Reference2", orderInfo.getCneeReference2());
		consignee.put("AccountNumber", "");
		
		LinkedHashMap<String, Object> consigneePartyAddress = new LinkedHashMap<String, Object>();
		consigneePartyAddress.put("Line1", orderInfo.getCneeAddr());
		consigneePartyAddress.put("Line2", orderInfo.getCneeAddrDetail());
		consigneePartyAddress.put("Line3", "");
		consigneePartyAddress.put("City", orderInfo.getCneeCity());
		consigneePartyAddress.put("StateOrProvinceCode", orderInfo.getCneeState());
		consigneePartyAddress.put("PostCode", orderInfo.getCneeZip());
		consigneePartyAddress.put("CountryCode", orderInfo.getCneeCntry());
		consigneePartyAddress.put("Longitude", 0);
		consigneePartyAddress.put("Latitude", 0);
		consigneePartyAddress.put("BuildingNumber", "");
		consigneePartyAddress.put("BuildingName", "");
		consigneePartyAddress.put("Floor", "");
		consigneePartyAddress.put("Apartment", "");
		consigneePartyAddress.put("POBox", "");
		consigneePartyAddress.put("Description", "");
		consignee.put("PartyAddress", consigneePartyAddress);
		
		LinkedHashMap<String, Object> consigneeContact = new LinkedHashMap<String, Object>();
		consigneeContact.put("Department", "");
		consigneeContact.put("PersonName", orderInfo.getCneeName());
		consigneeContact.put("Title", "");
		consigneeContact.put("CompanyName", orderInfo.getCneeName());
		consigneeContact.put("PhoneNumber1", orderInfo.getCneeTel());
		consigneeContact.put("PhoneNumber1Ext", "");
		consigneeContact.put("PhoneNumber2", "");
		consigneeContact.put("PhoneNumber2Ext", "");
		consigneeContact.put("FaxNumber", "");
		consigneeContact.put("CellPhone", orderInfo.getCneeHp());
		consigneeContact.put("EmailAddress", orderInfo.getCneeEmail());
		consigneeContact.put("Type", "");
		consignee.put("Contact", consigneeContact);
		shipments.put("Consignee", consignee);
		
		
		LinkedHashMap<String, Object> thirdParty = new LinkedHashMap<String, Object>();
		thirdParty.put("Reference1", "");
		thirdParty.put("Reference2", "");
		thirdParty.put("AccountNumber", "");
		
		LinkedHashMap<String, Object> thirdPartyAddress = new LinkedHashMap<String, Object>();
		thirdPartyAddress.put("Line1", "");
		thirdPartyAddress.put("Line2", "");
		thirdPartyAddress.put("Line3", "");
		thirdPartyAddress.put("City", "");
		thirdPartyAddress.put("StateOrProvinceCode", "");
		thirdPartyAddress.put("PostCode", "");
		thirdPartyAddress.put("CountryCode", "");
		thirdPartyAddress.put("Longitude", "");
		thirdPartyAddress.put("Latitude", "");
		thirdPartyAddress.put("BuildingNumber", "");
		thirdPartyAddress.put("BuildingName", "");
		thirdPartyAddress.put("Floor", "");
		thirdPartyAddress.put("Apartment", "");
		thirdPartyAddress.put("POBox", "");
		thirdPartyAddress.put("Description", "");
		thirdParty.put("PartyAddress", thirdPartyAddress);
		
		LinkedHashMap<String, Object> thirdPartyContact = new LinkedHashMap<String, Object>();
		thirdPartyContact.put("Department", "");
		thirdPartyContact.put("PersonName", "");
		thirdPartyContact.put("Title", "");
		thirdPartyContact.put("CompanyName", "");
		thirdPartyContact.put("PhoneNumber1", "");
		thirdPartyContact.put("PhoneNumber1Ext", "");
		thirdPartyContact.put("PhoneNumber2", "");
		thirdPartyContact.put("PhoneNumber2Ext", "");
		thirdPartyContact.put("FaxNumber", "");
		thirdPartyContact.put("CellPhone", "");
		thirdPartyContact.put("EmailAddress", "");
		thirdPartyContact.put("Type", "");
		thirdParty.put("Contact", thirdPartyContact);
		
		long currentTimestamp = System.currentTimeMillis();
        Date currentDate = new Date(currentTimestamp);
        TimeZone timeZone = TimeZone.getTimeZone("GMT+9");
        SimpleDateFormat offsetFormat = new SimpleDateFormat("Z");
        offsetFormat.setTimeZone(timeZone);
        String offset = offsetFormat.format(currentDate);
        offset = offset.substring(0, 4) + offset.substring(4);
        String formattedDate = String.format("\\/Date(%d%s)\\/", currentTimestamp, offset);
		
		shipments.put("ThirdParty", thirdParty);
		shipments.put("ShippingDateTime", formattedDate);
		shipments.put("DueDate", formattedDate);
		shipments.put("Comments", orderInfo.getDlvReqMsg());
		shipments.put("PickupLocation", "");
		shipments.put("OperationsInstructions", "");
		shipments.put("AccountingInstrcutions", "");
		
		LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, Object> dimensions = new LinkedHashMap<String, Object>();
		double length = 0;
		double width = 0;
		double height = 0;
		if (!orderInfo.getUserLength().equals("")) {
			length = Double.parseDouble(orderInfo.getUserLength());
		}
		if (!orderInfo.getUserWidth().equals("")) {
			width = Double.parseDouble(orderInfo.getUserWidth());
		}
		if (!orderInfo.getUserHeight().equals("")) {
			height = Double.parseDouble(orderInfo.getUserHeight());
		}
		
		dimensions.put("Length", length);
		dimensions.put("Width", width);
		dimensions.put("Height", height);
		dimensions.put("Unit", "CM");
		details.put("Dimensions", dimensions);
		
		LinkedHashMap<String, Object> actualWeight = new LinkedHashMap<String, Object>();
		actualWeight.put("Unit", "KG");
		actualWeight.put("Value", orderInfo.getUserWta());
		details.put("ActualWeight", actualWeight);
		details.put("ChargeableWeight", null);
		details.put("DescriptionOfGoods", itemInfoList.get(0).getItemDetail());
		details.put("GoodsOriginCountry", itemInfoList.get(0).getMakeCntry());
		details.put("NumberOfPieces", orderInfo.getBoxCnt());
		String productGroup = "";
		String productType = "";
		if (orderInfo.getCneeCntry().equals("SY")) {
			productGroup = "DOM";
			productType = "OND";
		} else {
			productGroup = "EXP";
			productType = "PPX";
		}
		details.put("ProductGroup", productGroup);
		details.put("ProductType", productType);
		details.put("PaymentType", "P");
		details.put("PaymentOptions", "");
		
		ArrayList<LinkedHashMap<String, Object>> items = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> itemOne = new LinkedHashMap<String, Object>();
		
		double totalValue = 0;
		
		for (int i = 0; i < itemInfoList.size(); i++) {
			itemOne = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> customsValue = new LinkedHashMap<String, Object>();
			
			double customValue = Double.parseDouble(itemInfoList.get(i).getUnitValue());
			
			itemOne.put("PackageType", "Box");
			itemOne.put("Quantity", Integer.parseInt(itemInfoList.get(i).getItemCnt()));
			itemOne.put("Weight", itemInfoList.get(i).getUserWta());
			
			String currency = itemInfoList.get(i).getUnitCurrency();
			if (currency.equals("")) {
				currency = itemInfoList.get(i).getChgCurrency();
			}
			customsValue.put("CurrencyCode", currency);
			customsValue.put("Value", customValue);
			
			itemOne.put("CustomsValue", customsValue);
			itemOne.put("Comments", itemInfoList.get(i).getItemDiv());
			itemOne.put("GoodsDescription", itemInfoList.get(i).getItemDetail());
			itemOne.put("Reference", "");
			itemOne.put("CommodityCode", itemInfoList.get(i).getCusItemCode());
			
			totalValue += customValue;
			items.add(itemOne);
		}

		details.put("Items", items);
		
		LinkedHashMap<String, Object> customsValueAmount = new LinkedHashMap<String, Object>();
		customsValueAmount.put("CurrencyCode", "USD");
		customsValueAmount.put("Value", totalValue);
		details.put("CustomsValueAmount", customsValueAmount);
		details.put("CashOnDeliveryAmount", 0);
		details.put("InsuranceAmount", 0);
		details.put("CashAdditionalAmount", 0);
		details.put("CashAdditionalAmountDescription", "");
		details.put("CollectAmount", 0);
		
		String services = "";
		if (orderInfo.getPayment().equals("DDP")) {
			services = "FRDM";
		}
		
		details.put("Services", services);
		
		shipments.put("Details", details);
		shipments.put("ForeignHAWB", nno);
		shipments.put("TransportType", "");
		shipments.put("PickupGUID", 0);
		shipments.put("Number", null);
		shipments.put("ScheduledDelivery", null);
		
		shipmentsList.add(shipments);
		
		requestData.put("Shipments", shipmentsList);
		
		LinkedHashMap<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("Reference1", "001");
		transaction.put("Reference2", "");
		transaction.put("Reference3", "");
		transaction.put("Reference4", "");
		transaction.put("Reference5", "");

		requestData.put("Transaction", transaction);
		
		
		return requestData;
	}
	
	public Object createTrackingRequestBody(HashMap<String, Object> params) throws Exception {
		
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> clientInfo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> transaction = new LinkedHashMap<String, Object>();
		ArrayList<String> hawbList = new ArrayList<String>();
		String hawbNo = (String) params.get("hawbNo");
		hawbList.add(hawbNo);
		
		String userName = "overseas2@aciexpress.net";
		String password = "Aci5606!";
		String version = "v1.0";
		String accountNumber = "172813";
		String accountPin = "321321";
		String accountEntity = "SEL";
		String accountCountryCode = "KR";
		int source = 24;
		
		clientInfo.put("UserName", userName);
		clientInfo.put("Password", password);
		clientInfo.put("Version", version);
		clientInfo.put("AccountNumber", accountNumber);
		clientInfo.put("AccountPin", accountPin);
		clientInfo.put("AccountEntity", accountEntity);
		clientInfo.put("AccountCountryCode", accountCountryCode);
		clientInfo.put("Source", source);
		
		requestData.put("ClientInfo", clientInfo);
		requestData.put("GetLastTrackingUpdateOnly", false);
		requestData.put("Shipments", hawbList);
		
		transaction.put("Reference1", "001");
		transaction.put("Reference2", "");
		transaction.put("Reference3", "");
		transaction.put("Reference4", "");
		transaction.put("Reference5", "");
		
		requestData.put("Transaction", transaction);
		
		return requestData;
	}
	
	
	public String getTrackingResponseBody(String requestVal) {
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		String responseStr = "";
		
		try {
			
			apiHeader.put("Accept", "application/xml");
			apiHeader.put("Content-Type", "application/json");
			
			String url = "https://ws.aramex.net/ShippingAPI.V2/Tracking/Service_1_0.svc/json/TrackShipments";
			ApiAction action = ApiAction.getInstance();
			responseStr = action.sendPost(requestVal, url, apiHeader);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return responseStr;
	}
	
	
	
}
