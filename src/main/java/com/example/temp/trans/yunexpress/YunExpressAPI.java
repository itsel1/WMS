package com.example.temp.trans.yunexpress;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.google.gson.Gson;

@Service
@Component
@Controller
public class YunExpressAPI {
	
	/*
	 * test url : http://omsapi.uat.yunexpress.com
	 * production url : http://oms.api.yunexpress.com
	*/
	
	@Autowired
	YunExpressMapper ytMapper;
	
	@Autowired
	ApiMapper apiMapper;

	@Value("${filePath}")
    String realFilePath;

	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	private static String apiUser = "CNK0074294";
	private static String apiKey = "274f422e9eac40bf87f0a8e2f4c794e7";
	private static String token = apiUser+"&"+apiKey;
	private static byte[] tokenBytes = token.getBytes();
	private static String tokenVal = Base64.getEncoder().encodeToString(tokenBytes);
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public YunExpressAPI() {
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Authorization", "Basic "+tokenVal);
		apiHeader.put("Accept", "application/json");
		apiHeader.put("charset", "UTF-8");
		apiHeader.put("__lang", "en-us");
	}
	
	public ProcedureVO createShipment(String nno, String userId, String userIp, String jsonVal) {
		ProcedureVO rst = new ProcedureVO();
		System.out.println("========= YunExpress API =========");
		System.out.println(jsonVal);
		try {
			
			String url = "http://oms.api.yunexpress.com/api/WayBill/CreateOrder";
			ApiAction action = ApiAction.getInstance();
			JSONObject response = action.apiPost(jsonVal, url, apiHeader);
			System.out.println(response);
			if (response != null) {
				
				if (!response.optString("Code").equals("0000")) {
					rst.setRstStatus("FAIL");
					rst.setRstMsg(response.optString("Message"));
				} else {
					JSONArray responseList = new JSONArray(String.valueOf(response.optString("Item")));
					JSONObject responseOne = (JSONObject) responseList.get(0);
					
					if (!responseOne.optString("Success").equals("0")) {
						String hawbNo = responseOne.optString("WayBillNumber");
						rst.setRstStatus("SUCCESS");
						rst.setRstHawbNo(hawbNo);
					} else {
						rst.setRstStatus("FAIL");
						rst.setRstMsg(responseOne.optString("Remark"));
					}
				}
			}	
			
		} catch (Exception e) {
			rst.setRstStatus("FAIL");
			rst.setRstMsg("System Error");
		}
		
		return rst;
	}
	
	public void createLabel(String hawbNo, String orderNno, String userId, String userIp) {

		try {
			String url = "http://oms.api.yunexpress.com/api/Label/Print";
			
			ArrayList<String> hawbList = new ArrayList<String>();
			hawbList.add(hawbNo);
			JSONArray requestBody = new JSONArray(hawbList);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject response = action.apiPost(requestBody.toString(), url, apiHeader);
			
			if (response != null) {
				
				if (response.optString("Code").equals("0000")) {
					JSONArray responseArray = new JSONArray(String.valueOf(response.optString("Item")));
					JSONObject responseOne = (JSONObject) responseArray.get(0);
					String labelUrl = responseOne.optString("Url");
					
					if (!labelUrl.equals("")) {
						String imageDir = realFilePath + "image/aramex/";
						URL imgUrl = new URL(labelUrl);
						File file = new File(imageDir + hawbNo + ".PDF");
						
						ReadableByteChannel read = Channels.newChannel(imgUrl.openStream());
						FileOutputStream fos = new FileOutputStream(file);
						
						fos.getChannel().transferFrom(read, 0, Long.MAX_VALUE);
						fos.close();
						
						AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
						amazonS3 = new AmazonS3Client(awsCredentials);
						PutObjectResult asssd = new PutObjectResult();
						Calendar c = Calendar.getInstance();
						String year = String.valueOf(c.get(Calendar.YEAR));
						String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
						if(amazonS3 != null) {
							PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/" + year + "/" + week, userId+"_"+hawbNo, file);
							putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
							asssd = amazonS3.putObject(putObjectRequest);
						}
						amazonS3 = null;
						file.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public String createRequestVal(String nno) {
		
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
		orderInfo = ytMapper.selectTmpOrderInfo(parameterInfo);
		itemInfoList = ytMapper.selectTmpItemInfo(parameterInfo);
		orderInfo.dncryptData();
		
		String userId = orderInfo.getUserId();
		double userWta = fnMakeDecimalPlaces(Double.parseDouble(orderInfo.getUserWta()), 100);
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
		
		// 코리엘 계정 & 미국 발송 시 코드 변경
		if (orderInfo.getDstnNation().toUpperCase().equals("US")) {
			if (userId.toLowerCase().equals("admincoreelle") || userId.toLowerCase().equals("trexi1") || userId.toLowerCase().equals("theskinstory") || userId.toLowerCase().equals("beautynetkr") 
					|| userId.toLowerCase().equals("happychan") || userId.toLowerCase().equals("dralthea")) {
				shippingMethodCode = "KRMUZXR";
			}
		}
		
		// 기본 정보		
		requestData.put("CustomerOrderNumber", nno);
		requestData.put("ShippingMethodCode", shippingMethodCode);
		requestData.put("TrackingNumber", "");
		requestData.put("TransactionNumber", "");
		requestData.put("TaxNumber", orderInfo.getTaxId());
		requestData.put("EoriNumber", orderInfo.getEoriNo());
		requestData.put("TaxCountryCode", "");
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
		receiverData.put("CountryCode", orderInfo.getDstnNation());
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
		senderData.put("Company", "");
		senderData.put("Street", "");
		senderData.put("City", "");
		senderData.put("State", "");
		senderData.put("Zip", "");
		senderData.put("Phone", "");
		requestData.put("Sender", senderData);
		
		requestData.put("ApplicationType", declType);
		requestData.put("ReturnOption", 0);
		requestData.put("TariffPrepay", payment);
		requestData.put("InsuranceOption", 0);
		requestData.put("SourceCode", "");
		
		
		String iossNo = "";
		if (!orderInfo.getIossNo().equals("")) {
			iossNo = orderInfo.getIossNo();
		}
		
		requestData.put("IossCode", iossNo);
		
		String extraCode = "V1";
		String extraName = "YunExpress Prepaid";
		
		if (!iossNo.equals("")) {
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
			itemData.put("UnitPrice", fnMakeDecimalPlaces(Double.parseDouble(itemInfoList.get(i).getUnitValue()), 100));
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
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(requestDataList);
		
		return jsonVal;
	}
	
	public double fnMakeDecimalPlaces(double val, int digit) {
		
		double roundedValue = 0;
		roundedValue = Math.ceil(val * digit) / digit;
		
		return roundedValue;
	}

	public ArrayList<HashMap<String, Object>> createTrackingAPI(String hawbNo, HttpServletRequest request) throws Exception {

		ArrayList<HashMap<String, Object>> podDetailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetail  = new LinkedHashMap<String,Object>();
		
		String url = "http://oms.api.yunexpress.com/api/Tracking/GetTrackInfo?OrderNumber="+hawbNo;
		ApiAction action = ApiAction.getInstance();
		JSONObject response = action.apiGet(url, apiHeader);

		if (response != null) {
			
			String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
			String mawbInDate = apiMapper.selectMawbInDate(hawbNo);		// 출고
			String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
			
			if (regInDate != null) {
				podDetail  = new LinkedHashMap<String,Object>();
				podDetail.put("UpdateCode", "100"); 
				podDetail.put("UpdateDateTime", regInDate);
				podDetail.put("UpdateLocation", "Republic of Korea");
				podDetail.put("UpdateDescription", "Order information has been entered");	
				podDetailArray.add(podDetail);
			}
			
			if (hawbInDate != null) {
				podDetail  = new LinkedHashMap<String,Object>();
				podDetail.put("UpdateCode", "200"); 
				podDetail.put("UpdateDateTime", hawbInDate);
				podDetail.put("UpdateLocation", "Republic of Korea");
				podDetail.put("UpdateDescription", "Finished warehousing");	
				podDetailArray.add(podDetail);
			}

			if (mawbInDate != null) {
				podDetail  = new LinkedHashMap<String,Object>();
				podDetail.put("UpdateCode", "300"); 
				podDetail.put("UpdateDateTime", mawbInDate);
				podDetail.put("UpdateLocation", "Republic of Korea");
				podDetail.put("UpdateDescription", "Shipped out");	
				podDetailArray.add(podDetail);
			}
			
			JSONObject responseOne = (JSONObject) response.opt("Item");
			JSONArray trackingArray = new JSONArray(String.valueOf(responseOne.optString("OrderTrackingDetails")));

			for (int i = 0; i < trackingArray.length(); i++) {
				JSONObject trackingInfo = (JSONObject) trackingArray.get(i);
				String location = trackingInfo.optString("ProcessLocation");
				String desc = trackingInfo.optString("TrackCodeDescription");
				String nodeCode = trackingInfo.optString("TrackNodeCode");
				String status = trackingInfo.optString("TrackingStatus");
				String dateTimeStr = trackingInfo.optString("ProcessDate");
				
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr,formatter);
				
				LocalDate date = dateTime.toLocalDate();
				LocalTime time = dateTime.toLocalTime();
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				String formattedTime = time.format(timeFormatter);
				
				String updateDateTime = date.toString() + " " + formattedTime.toString();
				
				
				podDetail  = new LinkedHashMap<String,Object>();
				
				if (nodeCode.equals("MAIN_LINE_ARRIVE")) {
					podDetail.put("UpdateCode", "400");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else if (nodeCode.equals("MAIN_LINE_ARRIVE")) {
					podDetail.put("UpdateCode", "400");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else if (nodeCode.equals("CUSTOMS_PROCESSING")) {
					podDetail.put("UpdateCode", "450");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else if (nodeCode.equals("CUSTOMS_COMPLETE")) {
					podDetail.put("UpdateCode", "480");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else if (nodeCode.equals("DELIVERY_ATTEMPT")) {
					podDetail.put("UpdateCode", "500");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else if (nodeCode.equals("DELIVERED")) {
					podDetail.put("UpdateCode", "600");
					podDetail.put("UpdateDateTime", updateDateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", desc);
				} else {
					continue;
				}
				
				podDetailArray.add(podDetail);
			}
			
		} else {
			podDetail  = new LinkedHashMap<String,Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}
	

	public ArrayList<HashMap<String, Object>> createTracking(String hawbNo, HttpServletRequest request) {

		ArrayList<HashMap<String, Object>> podDetailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetail  = new LinkedHashMap<String,Object>();
		
		String url = "http://oms.api.yunexpress.com/api/Tracking/GetTrackInfo?OrderNumber="+hawbNo;
		ApiAction action = ApiAction.getInstance();
		JSONObject response = action.apiGet(url, apiHeader);

		if (response != null) {
			JSONObject responseOne = (JSONObject) response.opt("Item");
			JSONArray trackingArray = new JSONArray(String.valueOf(responseOne.optString("OrderTrackingDetails")));

			for (int i = 0; i < trackingArray.length(); i++) {
				JSONObject trackingInfo = (JSONObject) trackingArray.get(i);
				String location = trackingInfo.optString("ProcessLocation");
				String desc = trackingInfo.optString("TrackCodeDescription");
				String status = trackingInfo.optString("TrackingStatus");
				String dateTimeStr = trackingInfo.optString("ProcessDate");
				
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr,formatter);
				
				LocalDate date = dateTime.toLocalDate();
				LocalTime time = dateTime.toLocalTime();
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		        String formattedTime = time.format(timeFormatter);
				
				String updateDateTime = date.toString() + " " + formattedTime.toString();
				
				podDetail  = new LinkedHashMap<String,Object>();
				podDetail.put("UpdateCode", status);
				podDetail.put("UpdateDateTime", updateDateTime);
				podDetail.put("UpdateLocation", location);
				podDetail.put("UpdateDescription", desc);
				podDetailArray.add(podDetail);
			}
			
		} else {
			podDetail  = new LinkedHashMap<String,Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}	

}
