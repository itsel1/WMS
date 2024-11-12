
package com.example.temp.trans.gts;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.ozon.OzonAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
public class GtsAPI {

	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	GtsMapper mapper;
	
	@Autowired
	OzonAPI ozonApi;
	
	ComnVO comnS3Info;
	
	private AmazonS3 amazonS3;
	
	boolean threadStart = false;
	
	boolean threadStop = false;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();

	public ApiShopifyResultVO createShipment(String nno) throws Exception {
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		GtsOrderVO orderInfo = new GtsOrderVO(); 
		orderInfo = mapper.selectGtsShipmentInfo(nno);
		String jsonVal = makeGtsJson(orderInfo); 
		String outResult = new String();
		JsonParser parse = new JsonParser();
		HttpResponse response =null;
		try {
			HashMap<String,Object> trackInfo = new HashMap<String,Object>();
			String requestURL = "https://api.postshipping.com/api2/shipments";
			//String requestURL = "https://testste.com";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Token", "F262319391B160C880DD425064D7534C");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				resultShopify.setStatus("OK");
				//resultShopify.setHawbNo(element.getAsJsonObject().get("orderId").getAsString());
				FileOutputStream fos = null;
				InputStream is = null;
				//String ImageDir = realFilePath + "image/" + "aramex/";
				String hawbNo = element.getAsJsonArray().get(0).getAsJsonObject().get("AlternateRef").getAsString();
				String shipmentReference = element.getAsJsonArray().get(0).getAsJsonObject().get("ShipmentNumber").getAsString();
				
				resultShopify.setHawbNo(hawbNo);
				resultShopify.setShipperReference(shipmentReference);
				
//				fos = new FileOutputStream(ImageDir+ hawbNo+".PDF");
//				try {
//					URL url = new URL(element.getAsJsonArray().get(0).getAsJsonObject().get("LabelURL").getAsString());
//					URLConnection urlConnection = url.openConnection();
//					is = urlConnection.getInputStream();
//					byte[] buffer = new byte[1024];
//					int readBytes;
//					while((readBytes = is.read(buffer)) != -1) {
//						fos.write(buffer,0,readBytes);
//					}
//					fos.close();
//					is.close();
//					AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
//					amazonS3 = new AmazonS3Client(awsCredentials);
//					PutObjectResult asssd = new PutObjectResult();
//					File file = new File(ImageDir+ hawbNo+".PDF");
//					Calendar c = Calendar.getInstance();
//					String year = String.valueOf(c.get(Calendar.YEAR));
//			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
//					if(amazonS3 != null) {
//						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getWUserId()+"_"+hawbNo, file);
//						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//						asssd = amazonS3.putObject(putObjectRequest);
//					}
//					amazonS3 = null;
//					file.delete();
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
			}else {
				resultShopify.setStatus("ERROR");
			}
		}catch (Exception e) {
			// TODO: handle exception
			resultShopify.setStatus("ERROR");
		}
		return resultShopify;
	}
	
	public String makeGtsJson(GtsOrderVO orderInfo) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> registDataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> registData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> senderDetails = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> receiverDetails = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> packageDetails = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> shipmentResponseItem = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> piece= new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> pickupDetails = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> items = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> pieceItems = new ArrayList<LinkedHashMap<String, Object>>();
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LinkedHashMap<String, Object> weight = new LinkedHashMap<String, Object>();
		
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		registData.put("ThirdPartyToken","28B58A4EB94F4388801D5B8323FC2AE9");
		
		//SenderDetails Start
		senderDetails.put("SenderName",orderInfo.getShipperName());
		senderDetails.put("SenderCompanyName",orderInfo.getShipperName());
		senderDetails.put("SenderCountryCode",orderInfo.getShipperCntry());
		senderDetails.put("SenderAdd1",orderInfo.getShipperAddr());
		senderDetails.put("SenderAdd2",orderInfo.getShipperAddrDetail());
		senderDetails.put("SenderAddCity",orderInfo.getShipperCity());
		senderDetails.put("SenderAddPostcode",orderInfo.getShipperZip());
		senderDetails.put("SenderPhone",orderInfo.getShipperHp());
		//senderDetails End
		registData.put("SenderDetails", senderDetails);
		//ReceiverDetails Start
		receiverDetails.put("ReceiverName",orderInfo.getCneeName());
		receiverDetails.put("ReceiverCompanyName",orderInfo.getCneeName());
		receiverDetails.put("ReceiverCountryCode",orderInfo.getDstnNation());
		receiverDetails.put("ReceiverAdd1",orderInfo.getCneeAddr());
		receiverDetails.put("ReceiverAdd2",orderInfo.getCneeAddrDetail());
		receiverDetails.put("ReceiverAddCity",orderInfo.getCneeCity());
		receiverDetails.put("ReceiverAddPostcode",orderInfo.getCneeZip());
		receiverDetails.put("ReceiverPhone",orderInfo.getCneeHp());
		receiverDetails.put("ReceiverEmail", orderInfo.getCneeEmail());
		//receiverDetails End
		registData.put("ReceiverDetails", receiverDetails);
		//PackageDetails Start
		packageDetails.put("CustomValue",orderInfo.getTotalValue());
		packageDetails.put("CustomCurrencyCode",orderInfo.getCurrency());
		packageDetails.put("ShipmentTerm",orderInfo.getPayment());
		packageDetails.put("GoodsOriginCountryCode",orderInfo.getMakeCntry());
		packageDetails.put("Weight",orderInfo.getUserWta());
		packageDetails.put("WeightMeasurement",orderInfo.getWtUnit());
		packageDetails.put("NoOfItems",orderInfo.getItemCnt());
		packageDetails.put("CubicL",orderInfo.getUserLength());
		packageDetails.put("CubicW",orderInfo.getUserWidth());
		packageDetails.put("CubicH",orderInfo.getUserHeight());
		packageDetails.put("CubicWeight",orderInfo.getUserWtc());
		//추후 POD,D2D 선택 입력.
		if(orderInfo.getShipperReference().equals("2")) {
			//POD
			packageDetails.put("ServiceTypeName","GBSRUPOD");
		}else {
			//D2D
			packageDetails.put("ServiceTypeName","GBSRUCOU");
		}
		packageDetails.put("SenderRef1",orderInfo.getOrderNo());
		packageDetails.put("DeadWeight",orderInfo.getUserWta());
		ArrayList<HashMap<String,Object>> orderItem = new ArrayList<HashMap<String,Object>>(); 
		orderItem = mapper.selectGtsItemInfo(orderInfo.getNno().toString());
		for(int i=0; i < orderItem.size();i++) {
			shipmentResponseItem = new LinkedHashMap<String, Object>(); 
			shipmentResponseItem.put("ItemNoOfPcs", orderItem.get(i).get("itemCnt"));
			shipmentResponseItem.put("ItemCubicL", "0");
			shipmentResponseItem.put("ItemCubicW", "0");
			shipmentResponseItem.put("ItemCubicH", "0");
			shipmentResponseItem.put("ItemWeight", orderItem.get(i).get("totalWta"));
			shipmentResponseItem.put("ItemCubicWeight", orderItem.get(i).get("totalWtc"));
			shipmentResponseItem.put("ItemCustomValue", orderItem.get(i).get("totalVal"));
			shipmentResponseItem.put("ItemCustomCurrencyCode", orderItem.get(i).get("chgCurrency"));
			shipmentResponseItem.put("Notes", orderItem.get(i).get("itemDetail"));
			shipmentResponseItem.put("ItemDeadWeight", orderItem.get(i).get("totalWta"));
			shipmentResponseItem.put("ItemGoodsOriginCountryCode", orderItem.get(i).get("makeCntry"));
			
			
			piece = new LinkedHashMap<String, Object>();
			piece.put("HarmonisedCode", orderItem.get(i).get("hsCode"));
			piece.put("GoodsDescription", orderItem.get(i).get("itemDetail"));
			piece.put("Content", orderItem.get(i).get("itemDetail"));
			piece.put("Notes", "");
			piece.put("SenderRef1", orderItem.get(i).get("subNo"));
			piece.put("Quantity", orderItem.get(i).get("itemCnt"));
			piece.put("Weight", orderItem.get(i).get("itemWta"));
			piece.put("ManufactureCountryCode", orderItem.get(i).get("makeCntry"));
			piece.put("OriginCountryCode", orderItem.get(i).get("makeCntry"));
			piece.put("CurrencyCode", orderItem.get(i).get("chgCurrency"));
			piece.put("ProductURL", orderItem.get(i).get("itemUrl"));
			piece.put("GoodsValue", orderItem.get(i).get("unitValue"));
			piece.put("CustomsValue", orderItem.get(i).get("unitValue"));
			pieceItems.add(piece);
			
			
			shipmentResponseItem.put("Pieces",pieceItems);
			items.add(shipmentResponseItem);
		}
		packageDetails.put("ShipmentResponseItem",items);
		//packageDetails End
		registData.put("PackageDetails", packageDetails);
		//PickupDetails Start
		
		//PickupDetails End
		registData.put("PickupDetails", pickupDetails);
		//items start
//		ArrayList<GtsItemVO> itemInfo = new ArrayList<GtsItemVO>();
//		itemInfo = mapper.selectGtsShipmentItemInfo(orderInfo.getNno());
//		double totalWeight = 0;
//		double amountPaid = 0;
//		
//		for(int i =0; i < itemInfo.size(); i++) {
//			LinkedHashMap<String, Object> itemDetail = new LinkedHashMap<String, Object>();
//			itemDetail.put("lineItemKey", "");
//			itemDetail.put("sku", itemInfo.get(i).getSku());
//			itemDetail.put("name", itemInfo.get(i).getItemDetail());
//			itemDetail.put("imageUrl", "");
//			itemDetail.put("quantity", itemInfo.get(i).getItemCnt());
//			itemDetail.put("unitPrice", itemInfo.get(i).getUnitValue());
//			itemDetail.put("taxAmount", "");
//			itemDetail.put("shippingAmount", "");
//			itemDetail.put("warehouseLocation", "");
//			itemDetail.put("productId", "");
//			itemDetail.put("fulfillmentSku", "");
//			itemDetail.put("upc", "");
//			items.add(itemDetail);
//			amountPaid += Double.parseDouble(itemInfo.get(i).getUnitValue());
//			totalWeight += Double.parseDouble(itemInfo.get(i).getUserWtaItem());
//		}
//		
//		//for문 아이템 자료..
//		
//		registData.put("items",items);
		//items end
		
		registDataList.add(registData);
		return getJsonStringFromMapArray(registDataList);
		//return "";
	}
	
	public static String getJsonStringFromMap( HashMap<String, Object> map ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        return jsonObject.toString();
    }
	
	public static String getJsonStringFromMapArray( ArrayList<LinkedHashMap<String, Object>>  map ) throws Exception {
		JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.get(0).entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }
	
	public ArrayList<HashMap<String, Object>> makePodDetatailArray (String hawbNo) throws Exception{
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		HttpResponse response =null;
		String outResult = new String();
		JsonParser parse = new JsonParser();
		
		String shipperReference = mapper.selectShipperReference(hawbNo);
		
		String requestURL = "https://api.postshipping.com/api2/tracks?ReferenceNumber="+shipperReference;
		//String requestURL = "https://api.postshipping.com/api2/tracks?ReferenceNumber=1000016986";
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Token", "F262319391B160C880DD425064D7534C");
		getRequest.setHeader("Accept", "application/json");
		getRequest.setHeader("charset", "UTF-8");
		response = client.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			outResult = handler.handleResponse(response);
			JsonElement element = parse.parse(outResult);
			JsonArray jsonArray = element.getAsJsonObject().get("TrackingDetail").getAsJsonArray();
			if(!jsonArray.get(0).getAsJsonObject().get("TrackingError").getAsString().equals("")) {
				Exception e = new Exception("No Pod Data");
				throw e;
			}
			int maxI = jsonArray.size()-1;
			for(int i =maxI; i > -1; i--) {
				podDetatil  = new LinkedHashMap<String,Object>();
				if(jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString().equals("1")) {
					podDetatil.put("UpdateCode","S500");
					podDetatil.put("UpdateDateTime", jsonArray.get(i).getAsJsonObject().get("TrackingUTCDate").getAsString());
					podDetatil.put("UpdateLocation", jsonArray.get(i).getAsJsonObject().get("TrackingLocation").getAsString());
					podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("TrackingEventName").getAsString());
					podDetatil.put("ProblemCode",jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString()); 
					podDetatil.put("Comments", jsonArray.get(i).getAsJsonObject().get("TrackingEventName").getAsString());
					podDetatailArray.add(podDetatil);
				}else if(!jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString().equals("91")&&!jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString().equals("90")) {
					podDetatil.put("UpdateCode",jsonArray.get(i).getAsJsonObject().get("TrackingEventName").getAsString());
					podDetatil.put("UpdateDateTime", jsonArray.get(i).getAsJsonObject().get("TrackingUTCDate").getAsString());
					podDetatil.put("UpdateLocation", jsonArray.get(i).getAsJsonObject().get("TrackingLocation").getAsString());
					podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("TrackingEventName").getAsString());
					podDetatil.put("ProblemCode",jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString()); 
					podDetatil.put("Comments", jsonArray.get(i).getAsJsonObject().get("TrackingEventName").getAsString());
					podDetatailArray.add(podDetatil);
				}
			}
			
			aciPodInfo = mapper.selectAciPodInfo(hawbNo);
			if(!aciPodInfo.get("hawbDate").equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S011");
				podDetatil.put("UpdateDateTime", aciPodInfo.get("hawbDate"));
				podDetatil.put("UpdateLocation", "Incheon,Seoul,KR");
				podDetatil.put("UpdateDescription", "Finished warehousing.");
				podDetatil.put("ProblemCode","S001"); 
				podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatailArray.add(podDetatil);
			}
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","S001");
			podDetatil.put("UpdateDateTime", aciPodInfo.get("orderDate"));
			podDetatil.put("UpdateLocation", "Korea");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatil.put("ProblemCode","S001"); 
			podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
			podDetatailArray.add(podDetatil);
		}
		return podDetatailArray;
	}
	
	public void setReferToEventInfo (HashMap<String,Object> targetTrackingInfo) throws Exception{
		HttpResponse response =null;
		String outResult = new String();
		JsonParser parse = new JsonParser();
		
		String requestURL = "https://api.postshipping.com/api2/tracks?ReferenceNumber="+targetTrackingInfo.get("hawbNo").toString();
		//String requestURL = "https://api.postshipping.com/api2/tracks?ReferenceNumber=1000016986";
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Token", "F262319391B160C880DD425064D7534C");
		getRequest.setHeader("Accept", "application/json");
		getRequest.setHeader("charset", "UTF-8");
		response = client.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			outResult = handler.handleResponse(response);
			JsonElement element = parse.parse(outResult);
			JsonArray jsonArray = element.getAsJsonObject().get("TrackingDetail").getAsJsonArray();
			if(!jsonArray.get(0).getAsJsonObject().get("TrackingError").getAsString().equals("")) {
				Exception e = new Exception("No Pod Data");
				throw e;
			}
			for(int i =0; i < jsonArray.size(); i++) {
				try {
					String referenceNum = jsonArray.get(i).getAsJsonObject().get("TrackingEventCode").getAsString();
					HashMap<String,Object> parameters = new HashMap<String,Object>();
					parameters.put("reference", referenceNum);
					parameters.put("transCode", "GTS");
					parameters.put("hawbNo", targetTrackingInfo.get("hawbNo").toString());
					parameters.put("orderNo", targetTrackingInfo.get("orderNo").toString());
					parameters.put("time", LocalDateTime.now(Clock.systemUTC()));
					if(!ozonApi.insertOzonTrack(parameters)) {
						continue;
					}
					if(referenceNum.equals("91")) {
						parameters.put("reference", referenceNum);
						parameters.put("transCode", "GTS");
						parameters.put("hawbNo", targetTrackingInfo.get("hawbNo").toString());
						parameters.put("orderNo", targetTrackingInfo.get("orderNo").toString());
						parameters.put("time", LocalDateTime.now(Clock.systemUTC()));
						if(!ozonApi.insertOzonTrack(parameters)) {
							continue;
						}
					}
				}catch (Exception e) {
					// TODO: handle exception
					continue;
				}				
				
			}
		}
	}
	
	public void selectBlApplyGts(HttpServletRequest request, String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO rtnVal = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		UserOrderListVO userInfo = new UserOrderListVO();
		
		comnService.insertTBFromTMP(orderNno);
		
		resultShopify = createShipment(orderNno);
		if(resultShopify.getStatus().equals("OK")) {
			//hawb 업데이트
			comnService.updateHawbNoInTbHawb(resultShopify.getHawbNo(), orderNno);
			comnService.updateShipperReference(resultShopify.getShipperReference(), orderNno);
			comnService.updateHawbNoInTbOrderList(resultShopify.getHawbNo(), orderNno);
		}else {
			//TMP로 옮기고 TB 삭제, error 코드 확인할 것.
			comnService.deleteHawbNoInTbHawb(orderNno);
			comnService.insertTMPFromTB(orderNno, resultShopify.getErrorMsg(), userId, userIp);
		}
		
	}
	
	public void insertPostalCode() throws Exception{
		try {
			ArrayList<HashMap<String,Object>> gtsInfo = new ArrayList<HashMap<String,Object>>(); 
			gtsInfo = mapper.selectGtsLockerInfo();
			for(int i = 0; i < gtsInfo.size(); i++) {
				HttpResponse response =null;
				String outResult = new String();
				JsonParser parse = new JsonParser();
				
				String requestURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+
									gtsInfo.get(i).get("LATITUDE").toString().trim()+","+gtsInfo.get(i).get("LONGITUDE").toString().trim()+
									"&key=AIzaSyD4doXpfGrkXxPUNWQHiwz5yq1tOGkrcpI";
				
				HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
				HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
				getRequest.setHeader("Content-Type", "application/json");
				getRequest.setHeader("Accept", "application/json");
				getRequest.setHeader("charset", "UTF-8");
				response = client.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
					ResponseHandler<String> handler = new BasicResponseHandler();
					outResult = handler.handleResponse(response);
					JsonElement element = parse.parse(outResult);
					boolean checks = false;
					for(int k = 0; k < element.getAsJsonObject().get("results").getAsJsonArray().size(); k++) {
						if(checks) {
							break;
						}
						JsonArray jsonArray = element.getAsJsonObject().get("results").getAsJsonArray().get(k).getAsJsonObject().get("address_components").getAsJsonArray();
						for(int j = 0; j < jsonArray.size(); j++) {
							if(jsonArray.get(j).getAsJsonObject().get("types").getAsJsonArray().get(0).getAsString().equals("postal_code")) {
								String postalCode = jsonArray.get(j).getAsJsonObject().get("long_name").getAsString();
								mapper.updateGtsPostalCode(postalCode,gtsInfo.get(i).get("LOCKER_NUMBER").toString());
								checks = true;
							}
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void insertPoint() throws Exception{
		ArrayList<HashMap<String,Object>> gtsInfo = new ArrayList<HashMap<String,Object>>(); 
		try {
			gtsInfo = mapper.selectGtsLockerInfos();
			String athu = ozonApi.selectTokenByHawb();
		
			for(int i = 0; i < gtsInfo.size(); i++) {
				if(threadStop) {
					System.out.println(LocalDateTime.now()+" Thread Stop");
					break;
				}
		//	for(int i = 0; i < 10; i++) {
				ArrayList<LinkedHashMap<String, Object>> registDataList = new ArrayList<LinkedHashMap<String, Object>>();
				LinkedHashMap<String, Object> registData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> priceData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> weightData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> lengthData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> widthData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> heightData = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> dimensionData = new LinkedHashMap<String, Object>();
				ArrayList<LinkedHashMap<String, Object>> scheduleDataList = new ArrayList<LinkedHashMap<String, Object>>();
				
				
				registData.put("Code", gtsInfo.get(i).get("LOCKER_NUMBER").toString());
				registData.put("Name", "ACI"+"_"+gtsInfo.get(i).get("LOCKER_NUMBER").toString());
				registData.put("DeliveryTypeId", 2);
				registData.put("ProviderId", 132);
				registData.put("Address", gtsInfo.get(i).get("ADDRESS").toString()+" "+gtsInfo.get(i).get("ADDRESS_DETAIL").toString()+", "+gtsInfo.get(i).get("CITY").toString()+", "+gtsInfo.get(i).get("REGION").toString()+", "+gtsInfo.get(i).get("POSTAL_CODE").toString());
				registData.put("HowToGet", "Pick Up");
				registData.put("StoragePeriod", 3);
				registData.put("Latitude", Double.parseDouble(gtsInfo.get(i).get("LAT").toString()));
				registData.put("Longitude", Double.parseDouble(gtsInfo.get(i).get("LON").toString()));
				registData.put("PostalCode", gtsInfo.get(i).get("POSTAL_CODE").toString());
				registData.put("DeliveryForLegalUsers", true);
				registData.put("FreeWifi", false);
				registData.put("ClothesDressingRoom", false);
				registData.put("ShoesDressingRoom", false);
				
				if(gtsInfo.get(i).get("PAY_1").toString().equals("CASH")) {
					registData.put("AvailableCash", true);
					registData.put("AvailableCreditCard", false);
				}else if(gtsInfo.get(i).get("PAY_1").toString().equals("CARD")){
					registData.put("AvailableCreditCard", true);
					if(gtsInfo.get(i).get("PAY_2").toString().equals("CASH")) {
						registData.put("AvailableCash", true);
					}else{
						registData.put("AvailableCash", false);
					}
				}else if(gtsInfo.get(i).get("PAY_1").toString().equals("ONSITE")) {
					registData.put("AvailableCreditCard", true);
					registData.put("AvailableCash", true);
				}
				
				registData.put("OrderPartialRedemption", false);
				registData.put("DomesticMerchandiseReturn", false);
				registData.put("CrossborderMerchandiseReturn", false);
				
				priceData.put("Min", 0);
				priceData.put("Max", 0);
				registData.put("Price", priceData);
				
				weightData.put("Min", 0);
				weightData.put("Max", Double.parseDouble(gtsInfo.get(i).get("MAX_WEIGHT_G").toString())*10);
				registData.put("Weight", weightData);
				
				lengthData.put("Min", 0);
				lengthData.put("Max", Double.parseDouble(gtsInfo.get(i).get("MAX_LENGTH").toString())*10);
				registData.put("Length", lengthData);
				
				widthData.put("Min", 0);
				widthData.put("Max", Double.parseDouble(gtsInfo.get(i).get("MAX_WIDTH").toString())*10);
				registData.put("Width", widthData);
				
				heightData.put("Min", 0);
				heightData.put("Max", Double.parseDouble(gtsInfo.get(i).get("MAX_HEIGHT").toString())*10);
				registData.put("Height", heightData);
				
				dimensionData.put("Min", 0);
				dimensionData.put("Max", Double.parseDouble(gtsInfo.get(i).get("MAX_DIMENSION").toString())*10);
				registData.put("DimensionSum", dimensionData);
				
				LinkedHashMap<String, Object> scheduleData = new LinkedHashMap<String, Object>();
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 1);
				if(!gtsInfo.get(i).get("MON").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("MON").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 2);
				if(!gtsInfo.get(i).get("TUE").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("TUE").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 3);
				if(!gtsInfo.get(i).get("WED").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("WED").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 4);
				if(!gtsInfo.get(i).get("THU").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("THU").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 5);
				if(!gtsInfo.get(i).get("FRI").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("FRI").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 6);
				if(!gtsInfo.get(i).get("SAT").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("SAT").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				scheduleData = new LinkedHashMap<String, Object>();
				scheduleData.put("Day", 7);
				if(!gtsInfo.get(i).get("SUN").toString().equals("NONE")) {
					scheduleData.put("IsHoliday", false);
					scheduleData.put("FromTo", gtsInfo.get(i).get("SUN").toString());
					if(gtsInfo.get(i).get("BREAK").toString().equals("NONE")) {
						scheduleData.put("BreakFromTo", "");
					}else {
						scheduleData.put("BreakFromTo", gtsInfo.get(i).get("BREAK").toString());
					}
				}else {
					scheduleData.put("IsHoliday", true);
					scheduleData.put("FromTo", "08:00-22:00");
					scheduleData.put("BreakFromTo", "");
				}
				scheduleDataList.add(scheduleData);
				
				registData.put("Schedule", scheduleDataList);
				if(gtsInfo.get(i).get("USEABLE").toString().equals("true")) {
					registData.put("IsActive", true);
				}else {
					registData.put("IsActive", false);
				}
				
				registDataList.add(registData);
				
				HttpResponse response =null;
				String requestURL = "https://api-logistic-platform.ozon.ru:443/v1/Points";
				//String requestURL = "https://testste.com";
				HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
				HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
				postRequest.setHeader("Content-Type", "application/json");
				postRequest.setHeader("Authorization", "Bearer "+ athu);
				postRequest.setEntity(new StringEntity(getJsonStringFromMapArray(registDataList), "UTF-8")); //json 메시지 입력
				response = client.execute(postRequest);
				if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
					mapper.updateSendYn(gtsInfo.get(i).get("LOCKER_NUMBER").toString());
					System.out.println(LocalDateTime.now()+" Insert Success => "+gtsInfo.get(i).get("LOCKER_NUMBER").toString());
				}else {
					threadStop = true;
					System.out.println(LocalDateTime.now()+" Insert Fail => "+gtsInfo.get(i).get("LOCKER_NUMBER").toString());
				}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void insertRoutes() throws Exception{
		String athu = ozonApi.selectTokenByHawb();
		ArrayList<String> uidList = new ArrayList<String>();
		uidList = ozonApi.selectUidList();
		for(int i = 0; i < uidList.size(); i++) {
			ArrayList<LinkedHashMap<String, Object>> registDataList = new ArrayList<LinkedHashMap<String, Object>>();
			LinkedHashMap<String, Object> d2d = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> poud = new LinkedHashMap<String, Object>();
			d2d.put("ServiceCode", "ACID2D");
			d2d.put("RegionFromUid", "c71b4655-4ec9-5563-afe3-884294efa592");
			d2d.put("RegionToUid", uidList.get(i));
			d2d.put("DeliveryDaysMin", 10);
			d2d.put("DeliveryDaysMax", 15);
			d2d.put("IsActive", true);
			
			poud.put("ServiceCode", "ACIPOUD");
			poud.put("RegionFromUid", "c71b4655-4ec9-5563-afe3-884294efa592");
			poud.put("RegionToUid", uidList.get(i));
			poud.put("DeliveryDaysMin", 10);
			poud.put("DeliveryDaysMax", 15);
			poud.put("IsActive", true);
			
			registDataList.add(d2d);
			registDataList.add(poud);
			
			HttpResponse response =null;
			String requestURL = "https://api-logistic-platform.ozon.ru:443/v1/Routes";
			//String requestURL = "https://testste.com";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Authorization", "Bearer "+ athu);
			postRequest.setEntity(new StringEntity(getJsonStringFromMapArray(registDataList), "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				mapper.updateSendYn(uidList.get(i));
				System.out.println(LocalDateTime.now()+" Insert Success => "+uidList.get(i));
			}else {
				threadStop = true;
				System.out.println(LocalDateTime.now()+" Insert Fail => "+uidList.get(i));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 500)
	@Transactional(rollbackFor=Exception.class) 
    public void insertHawbQueue() throws Exception {
    	try {
    		if(threadStart) {
    			threadStart = false;
    			insertRoutes();
    		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void setThread() {
		System.out.println(LocalDateTime.now()+" Thread Start");
		threadStop = false;
		threadStart = true;
	}
	
	public void setThreadStop() {
		threadStop = true;
	}
	
}
