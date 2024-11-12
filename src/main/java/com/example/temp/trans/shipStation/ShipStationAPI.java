
package com.example.temp.trans.shipStation;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.xmlbeans.impl.xb.xsdschema.AppinfoDocument.Appinfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.api.shopify.ShopifyAPI;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.security.SecurityKeyVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import oracle.net.aso.p;

@RestController
public class ShipStationAPI {

	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ShipStationMapper mapper;
	
	@Autowired
	ApiMapper apiMapper;
	
	ComnVO comnS3Info;
	
	private AmazonS3 amazonS3;
	
	@Autowired
	ShopifyAPI shopifyApi;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();

	public ApiShopifyResultVO createShipment(String nno) throws Exception {
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		ShipStationOrderVO orderInfo = new ShipStationOrderVO(); 
		orderInfo = mapper.selectListInfoForShipStation(nno);
		String jsonVal = makeShipStationJson(nno); 
		String outResult = new String();
		JsonParser parse = new JsonParser();
		HttpResponse response =null;
		try {
			String requestURL = "https://ssapi.shipstation.com/orders/createorder";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				
				resultShopify.setStatus("OK");
				//resultShopify.setHawbNo(element.getAsJsonObject().get("orderId").getAsString());
				String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String orderId = element.getAsJsonObject().get("orderId").getAsString();
				String jsonVal2 = makeLabelJson(orderId, currentTime);
				HttpResponse response2 =null;
				JsonParser parse2 = new JsonParser();
				String outResult2 = new String();
				try {
					String requestURL2 = "https://ssapi.shipstation.com/orders/createlabelfororder";
					HttpClient client2 = HttpClientBuilder.create().build(); // HttpClient 생성
					HttpPost postRequest2 = new HttpPost(requestURL2); //POST 메소드 URL 새성
					postRequest2.setHeader("Accept", "application/json");
					postRequest2.setHeader("Content-Type", "application/json");
					postRequest2.setHeader("charset", "UTF-8");
					postRequest2.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
					postRequest2.setEntity(new StringEntity(jsonVal2, "UTF-8")); //json 메시지 입력
					response2 = client2.execute(postRequest2);
					if (response2.getStatusLine().getStatusCode() == 200 || response2.getStatusLine().getStatusCode() == 201) {
						ResponseHandler<String> handler2 = new BasicResponseHandler();
						outResult2 = handler2.handleResponse(response2);
						JsonElement element2 = parse2.parse(outResult2);
						String shipmentId = element2.getAsJsonObject().get("shipmentId").getAsString();
						String hawbNo = element2.getAsJsonObject().get("trackingNumber").getAsString();
						HashMap<String,Object> stationInfo = new HashMap<String,Object>();
						stationInfo.put("nno",nno);
						stationInfo.put("userId",orderInfo.getUserId());
						stationInfo.put("orderNo",orderInfo.getOrderNo());
						stationInfo.put("hawbNo",hawbNo);
						stationInfo.put("orderId",orderId);
						stationInfo.put("shipmentId",shipmentId);
						mapper.insertShipStationId(stationInfo);
						resultShopify.setHawbNo(hawbNo);
						String base64Pdf = element2.getAsJsonObject().get("labelData").getAsString();
						String ImageDir = realFilePath + "image/" + "aramex/";
						File file = new File(ImageDir+resultShopify.getHawbNo()+".PDF");
						try { 
							FileOutputStream fos = new FileOutputStream(file);
							byte[] decoder = Base64.getDecoder().decode(base64Pdf);

							fos.write(decoder);
							fos.close();
							AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
							amazonS3 = new AmazonS3Client(awsCredentials);
							PutObjectResult asssd = new PutObjectResult();
							Calendar c = Calendar.getInstance();
							String year = String.valueOf(c.get(Calendar.YEAR));
					 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
							if(amazonS3 != null) {
								PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getUserId()+"_"+resultShopify.getHawbNo(), file);
								putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
								asssd = amazonS3.putObject(putObjectRequest);
							}
							amazonS3 = null;
							resultShopify.setStatus("OK");
						}catch (Exception e) {
							// TODO: handle exception
							resultShopify.setStatus("ERROR");
						}
					}
				}catch (Exception e) {
					// TODO: handle exception
					resultShopify.setStatus("ERROR");
				}
			}else {
				resultShopify.setStatus("ERROR");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			resultShopify.setStatus("ERROR");
		}
		
		return resultShopify;
	}
	
	public ApiShopifyResultVO createShipmentTest(String nno) throws Exception {
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		ShipStationOrderVO orderInfo = new ShipStationOrderVO(); 
		orderInfo = mapper.selectListInfoForShipStation(nno);
		String jsonVal = makeShipStationJson(nno); 
		String outResult = new String();
		JsonParser parse = new JsonParser();
		HttpResponse response =null;
		try {
			String requestURL = "https://ssapi.shipstation.com/orders/createorder";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				
				resultShopify.setStatus("OK");
				//resultShopify.setHawbNo(element.getAsJsonObject().get("orderId").getAsString());
				String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String orderId = element.getAsJsonObject().get("orderId").getAsString();
				String jsonVal2 = makeLabelJson(orderId, currentTime);
				HttpResponse response2 =null;
				JsonParser parse2 = new JsonParser();
				String outResult2 = new String();
				try {
					String requestURL2 = "https://ssapi.shipstation.com/orders/createlabelfororder";
					HttpClient client2 = HttpClientBuilder.create().build(); // HttpClient 생성
					HttpPost postRequest2 = new HttpPost(requestURL2); //POST 메소드 URL 새성
					postRequest2.setHeader("Accept", "application/json");
					postRequest2.setHeader("Content-Type", "application/json");
					postRequest2.setHeader("charset", "UTF-8");
					postRequest2.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
					postRequest2.setEntity(new StringEntity(jsonVal2, "UTF-8")); //json 메시지 입력
					response2 = client2.execute(postRequest2);
					if (response2.getStatusLine().getStatusCode() == 200 || response2.getStatusLine().getStatusCode() == 201) {
						ResponseHandler<String> handler2 = new BasicResponseHandler();
						outResult2 = handler2.handleResponse(response2);
						JsonElement element2 = parse2.parse(outResult2);
						String shipmentId = element2.getAsJsonObject().get("shipmentId").getAsString();
						String hawbNo = element2.getAsJsonObject().get("trackingNumber").getAsString();
						HashMap<String,Object> stationInfo = new HashMap<String,Object>();
						stationInfo.put("nno",nno);
						stationInfo.put("userId",orderInfo.getUserId());
						stationInfo.put("orderNo",orderInfo.getOrderNo());
						stationInfo.put("hawbNo",hawbNo);
						stationInfo.put("orderId",orderId);
						stationInfo.put("shipmentId",shipmentId);
						mapper.insertShipStationId(stationInfo);
						resultShopify.setHawbNo(hawbNo);
						String base64Pdf = element2.getAsJsonObject().get("labelData").getAsString();
						String ImageDir = realFilePath + "image/" + "aramex/";
						File file = new File(ImageDir+resultShopify.getHawbNo()+".PDF");
						try { 
							FileOutputStream fos = new FileOutputStream(file);
							byte[] decoder = Base64.getDecoder().decode(base64Pdf);

							fos.write(decoder);
							fos.close();
							AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
							amazonS3 = new AmazonS3Client(awsCredentials);
							PutObjectResult asssd = new PutObjectResult();
							Calendar c = Calendar.getInstance();
							String year = String.valueOf(c.get(Calendar.YEAR));
					 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
							if(amazonS3 != null) {
								PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getUserId()+"_"+resultShopify.getHawbNo(), file);
								putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
								asssd = amazonS3.putObject(putObjectRequest);
							}
							amazonS3 = null;
							resultShopify.setStatus("OK");
						}catch (Exception e) {
							// TODO: handle exception
							resultShopify.setStatus("ERROR");
						}
						
					} else {	// createlabelfororder에 실패할 경우
						HttpResponse response3 =null;
						String requestURL3 = "https://ssapi.shipstation.com/orders/"+orderId;
						HttpClient client3 = HttpClientBuilder.create().build();
						HttpDelete deleteRequest = new HttpDelete(requestURL3);
						deleteRequest.setHeader("Accept", "application/json");
						deleteRequest.setHeader("Content-Type", "application/json");
						deleteRequest.setHeader("charset", "UTF-8");
						deleteRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
						response3 = client3.execute(deleteRequest);
						if (response3.getStatusLine().getStatusCode() == 200 || response3.getStatusLine().getStatusCode() == 201) {
							resultShopify.setStatus("DELETE");
						}
					}
				}catch (Exception e) {
					// TODO: handle exception
					resultShopify.setStatus("ERROR");
				}
			}else {
				resultShopify.setStatus("ERROR");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			resultShopify.setStatus("ERROR");
		}
		
		return resultShopify;
	}
	
	public String makeLabelJson(String orderId, String currentTime) throws Exception {
		LinkedHashMap<String, Object> registData = new LinkedHashMap<String, Object>();
		registData.put("orderId", orderId);
		registData.put("carrierCode", "stamps_com");
		registData.put("serviceCode", "usps_first_class_mail");
		registData.put("packageCode", "package");
		registData.put("confirmation", "none");
		registData.put("shipDate", currentTime);
		registData.put("testLabel", false);
		
		return getJsonStringFromMap(registData);
	}
	
	public String makeShipStationJson(String nno) throws Exception {
		ShipStationOrderVO orderInfo = new ShipStationOrderVO(); 
		LinkedHashMap<String, Object> registData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> billTo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> shipTo = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> items = new ArrayList<LinkedHashMap<String, Object>>();
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LinkedHashMap<String, Object> weight = new LinkedHashMap<String, Object>();
		
		orderInfo = mapper.selectListInfoForShipStation(nno);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		registData.put("orderNumber",orderInfo.getOrderNo());
		registData.put("orderDate",currentTime);
		registData.put("orderStatus",orderInfo.getOrderStatus());
		registData.put("customerUsername",orderInfo.getCneeName());
		
		//billTo start
		billTo.put("name", "");
		billTo.put("company", "");
		billTo.put("street1", "");
		billTo.put("street2", "");
		billTo.put("street3", "");
		billTo.put("city", "");
		billTo.put("state", "");
		billTo.put("postalCode", "");
		billTo.put("country", "");
		billTo.put("phone", "");
		billTo.put("residential", "");
		
		registData.put("billTo",billTo);
		//billTo end
		
		//shipTo start
		shipTo.put("name", orderInfo.getCneeName()+"("+orderInfo.getOrderNo()+")");
		shipTo.put("company", orderInfo.getInvCompany());
		shipTo.put("street1", orderInfo.getCneeAddr());
		shipTo.put("street2", orderInfo.getCneeAddrDetail());
		shipTo.put("street3", "");
		shipTo.put("city", orderInfo.getCneeCity());
		shipTo.put("state", orderInfo.getCneeState());
		shipTo.put("postalCode", orderInfo.getCneeZip());
		shipTo.put("country", orderInfo.getDstnNation());
		shipTo.put("phone", orderInfo.getCneeTel());
		shipTo.put("residential", false);
		
		registData.put("shipTo",shipTo);
		//shipTo end
		
		//items start
		ArrayList<ShipStationItemVO> itemInfo = new ArrayList<ShipStationItemVO>();
		itemInfo = mapper.selectItemInfoForShipStation(nno);
		double totalWeight = 0;
		double amountPaid = 0;
		
		for(int i =0; i < itemInfo.size(); i++) {
			LinkedHashMap<String, Object> itemDetail = new LinkedHashMap<String, Object>();
			itemDetail.put("lineItemKey", "");
			itemDetail.put("sku", itemInfo.get(i).getSku());
			itemDetail.put("name", itemInfo.get(i).getItemDetail());
			itemDetail.put("imageUrl", "");
			itemDetail.put("quantity", itemInfo.get(i).getItemCnt());
			itemDetail.put("unitPrice", itemInfo.get(i).getUnitValue());
			itemDetail.put("taxAmount", "");
			itemDetail.put("shippingAmount", "");
			itemDetail.put("warehouseLocation", "");
			itemDetail.put("productId", "");
			itemDetail.put("fulfillmentSku", "");
			itemDetail.put("upc", "");
			items.add(itemDetail);
			amountPaid += Double.parseDouble(itemInfo.get(i).getUnitValue());
			totalWeight += Double.parseDouble(itemInfo.get(i).getUserWtaItem());
		}
		
		//for문 아이템 자료..
		
		registData.put("items",items);
		//items end
		
		registData.put("confirmation","none");
		registData.put("amountPaid",amountPaid);
		registData.put("carrierCode","stamps_com");
		registData.put("serviceCode","usps_first_class_mail");
		registData.put("packageCode","package");
		registData.put("shipDate","");
		
		//weight start
		if(totalWeight>=16) {
			totalWeight = 15;
			int min = 1;
			int max = 10;
			Random random = new Random();
			double value = random.nextInt(max) + min;
			double sndVal = value/10;
			totalWeight = totalWeight+sndVal;
		}
		
		weight.put("value", totalWeight);
		weight.put("units", "ounces");
		registData.put("weight",weight);
		//weight end
		
		return getJsonStringFromMap(registData);
		//return "";
	}
	
	public void voidLabel(String nno) throws Exception {
		HashMap<String,Object> shipStationInfo = new HashMap<String,Object>();
		shipStationInfo = mapper.selectShipStationInfo(nno);
		if(shipStationInfo != null) {
			String requestURL = "https://ssapi.shipstation.com/shipments/voidlabel";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			HttpResponse response =null;
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
			String jsonVal = "{\"shipmentId\":"+shipStationInfo.get("shipmentId")+"}";
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
		}
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

	public ApiShopifyResultVO registOrderShipStationUsps(HttpServletRequest request, String nno) throws Exception {
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		ShipStationOrderVO orderInfo = new ShipStationOrderVO(); 
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		apiParams.put("nno", nno);
		apiParams.put("jsonHeader", "");
		apiParams.put("wUserId", request.getSession().getAttribute("USER_ID"));
		apiParams.put("wUserIp", request.getRemoteAddr());
		apiParams.put("connUrl", "/ssapi/orders/createorder");
		
		orderInfo = mapper.selectListInfoForShipStation(nno);
		String jsonVal = makeShipStationJson(nno);
		apiParams.put("jsonData", jsonVal);
		
		String outResult = new String();
		JsonParser parse = new JsonParser();
		HttpResponse response =null;
		
		try {
			String requestURL = "https://ssapi.shipstation.com/orders/createorder";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				apiParams.put("rtnContents", element.toString());
				apiMapper.insertApiConn(apiParams);
				
				if (element.getAsJsonObject().get("orderId").getAsString().equals("") || element.getAsJsonObject().get("orderId") == null) {
					throw new Exception();
				} else {
					resultShopify.setStatus("OK");
					String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					String orderId = element.getAsJsonObject().get("orderId").getAsString();
					String jsonVal2 = makeLabelJson(orderId, currentTime);
					HttpResponse response2 =null;
					JsonParser parse2 = new JsonParser();
					String outResult2 = new String();
					try {
						String requestURL2 = "https://ssapi.shipstation.com/orders/createlabelfororder";
						HttpClient client2 = HttpClientBuilder.create().build(); // HttpClient 생성
						HttpPost postRequest2 = new HttpPost(requestURL2); //POST 메소드 URL 새성
						postRequest2.setHeader("Accept", "application/json");
						postRequest2.setHeader("Content-Type", "application/json");
						postRequest2.setHeader("charset", "UTF-8");
						postRequest2.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
						postRequest2.setEntity(new StringEntity(jsonVal2, "UTF-8")); //json 메시지 입력
						response2 = client2.execute(postRequest2);
						if (response2.getStatusLine().getStatusCode() == 200 || response2.getStatusLine().getStatusCode() == 201) {
							ResponseHandler<String> handler2 = new BasicResponseHandler();
							outResult2 = handler2.handleResponse(response2);
							JsonElement element2 = parse2.parse(outResult2);
							
							apiParams.put("connUrl", "/ssapi/orders/createlabelfororder");
							apiParams.put("rtnContents", element2.toString());
							apiMapper.insertApiConn(apiParams);
							
							if (element2.getAsJsonObject().get("shipmentId").getAsString().equals("") || element2.getAsJsonObject().get("shipmentId") == null) {
								throw new Exception();
							} else {
								String shipmentId = element2.getAsJsonObject().get("shipmentId").getAsString();
								String hawbNo = element2.getAsJsonObject().get("trackingNumber").getAsString();
								HashMap<String,Object> stationInfo = new HashMap<String,Object>();
								stationInfo.put("nno",nno);
								stationInfo.put("userId",orderInfo.getUserId());
								stationInfo.put("orderNo",orderInfo.getOrderNo());
								stationInfo.put("hawbNo",hawbNo);
								stationInfo.put("orderId",orderId);
								stationInfo.put("shipmentId",shipmentId);
								mapper.insertShipStationId(stationInfo);
								resultShopify.setHawbNo(hawbNo);
								String base64Pdf = element2.getAsJsonObject().get("labelData").getAsString();
								String ImageDir = realFilePath + "image/" + "aramex/";
								File file = new File(ImageDir+resultShopify.getHawbNo()+".PDF");
								try {
									FileOutputStream fos = new FileOutputStream(file);
									byte[] decoder = Base64.getDecoder().decode(base64Pdf);

									fos.write(decoder);
									fos.close();
									AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
									amazonS3 = new AmazonS3Client(awsCredentials);
									PutObjectResult asssd = new PutObjectResult();
									Calendar c = Calendar.getInstance();
									String wDate = orderInfo.getWDate();
									String year = wDate.substring(0,4);
									String month = wDate.substring(4,6);
									String day = wDate.substring(6,8);
									wDate = year + "-" + month + "-" + day;
									String week = Integer.toString(getWeekOfYear(wDate));
									//String year = String.valueOf(c.get(Calendar.YEAR));
									//String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
									if(amazonS3 != null) {
										PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getUserId()+"_"+resultShopify.getHawbNo(), file);
										putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
										asssd = amazonS3.putObject(putObjectRequest);
									}
									amazonS3 = null;
									resultShopify.setStatus("OK");
									resultShopify.setUserId(orderInfo.getUserId());
								}catch (Exception e) {
									resultShopify.setStatus("ERROR");
								}
								
								// shopify fulfillment update
								if (orderInfo.getUserId().toLowerCase().equals("bmsmileus")) {
									ArrayList<HashMap<String, Object>> fulfillInfo = new ArrayList<HashMap<String, Object>>();
									ApiShopifyInfoVO shopifyVo = new ApiShopifyInfoVO();
									shopifyVo = mapper.selectShopifyInfo(nno);
									fulfillInfo = mapper.selectShopifyFulfillInfo(nno);
									LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
									apiHeader.put("Content-Type", "application/json");
									apiHeader.put("X-Shopify-Access-Token", shopifyVo.getPassword());
									apiHeader.put("Accept", "application/json");
									LinkedHashMap<String, Object> jsonObject = new LinkedHashMap<String, Object>();
									HashMap<String, Object> fulfillment = new HashMap<String, Object>();
									ArrayList<HashMap<String, Object>> fulfillList = new ArrayList<HashMap<String, Object>>();
									HashMap<String, Object> fulfillJson = new HashMap<String, Object>();
									for (int i = 0; i < fulfillInfo.size(); i++) {
										fulfillJson = new HashMap<String, Object>();
										fulfillJson.put("fulfillment_order_id", fulfillInfo.get(i).get("fulFillId").toString());
										fulfillList.add(fulfillJson);
									}
									fulfillment.put("line_items_by_fulfillment_order", fulfillList);
									HashMap<String, Object> trackingInfo = new HashMap<String, Object>();
									trackingInfo.put("number", hawbNo);
									fulfillment.put("tracking_info", trackingInfo);
									jsonObject.put("fulfillment", fulfillment);
									Gson gson = new Gson();
									String apiJson = gson.toJson(jsonObject);
									
									String apiUrl = "https://"+shopifyVo.getShopifyUrl()+"/admin/api/2023-07/fulfillments.json";
									JSONObject returnJson = shopifyApi.apiPost(apiUrl, apiHeader, request, apiJson);

								}
							}
						} else {	// createlabelfororder에 실패할 경우
							HttpResponse response3 =null;
							String requestURL3 = "https://ssapi.shipstation.com/orders/"+orderId;
							HttpClient client3 = HttpClientBuilder.create().build();
							HttpDelete deleteRequest = new HttpDelete(requestURL3);
							deleteRequest.setHeader("Accept", "application/json");
							deleteRequest.setHeader("Content-Type", "application/json");
							deleteRequest.setHeader("charset", "UTF-8");
							deleteRequest.setHeader("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
							response3 = client3.execute(deleteRequest);
							if (response3.getStatusLine().getStatusCode() == 200 || response3.getStatusLine().getStatusCode() == 201) {
								resultShopify.setStatus("DELETE");
							}
						}
					} catch (Exception e) {
						resultShopify.setStatus("ERROR");
					}
				}
				
			} else {
				resultShopify.setStatus("ERROR");
			}
		} catch (Exception e) {
			resultShopify.setStatus("ERROR");
		}
		
		return resultShopify;
	}
	
	private int getWeekOfYear(String date) {
		Calendar calendar = Calendar.getInstance();
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public synchronized ProcedureVO selectBlApply(String orderNno, String userId, String userIp) {
		ProcedureVO rst = new ProcedureVO();
		LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", orderNno);
		parameters.put("wUserId", userId);
		parameters.put("wUserIp", userIp);
		parameters.put("userId", userId);
		parameters.put("userIp", userIp);
		String url = "";
		
		try {
			
			headers.put("Content-Type", "application/json");
			headers.put("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
			 url = "https://ssapi.shipstation.com/orders/createorder";
			String requestData = createRequestData(parameters);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject respData = action.apiPost(requestData, url, headers, orderNno, userId, userIp);
			
			parameters.put("connUrl", "/orders/createorder");
			parameters.put("jsonHeader", "");
			parameters.put("jsonData", requestData);
			parameters.put("rtnContents", respData.toString());
			apiMapper.insertApiConn(parameters);
			
			String orderId = "";
			String shipmentId = "";
			String trkNo = "";
			String orderNo = "";
			
			if (respData != null) {
				System.out.println(respData);
				orderId = respData.optString("orderId");
				orderNo = respData.optString("orderNumber");
				
				parameters.put("orderId", orderId);

				String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				
				LinkedHashMap<String, Object> requestLabelData = new LinkedHashMap<String, Object>();
				requestLabelData.put("orderId", orderId);
				requestLabelData.put("carrierCode", "stamps_com");
				requestLabelData.put("serviceCode", "usps_first_class_mail");
				requestLabelData.put("packageCode", "package");
				requestLabelData.put("confirmation", "none");
				requestLabelData.put("shipDate", currentTime);
				requestLabelData.put("testLabel", false);
				Gson gson = new Gson();
				String jsonVal = gson.toJson(requestLabelData);
				
				headers.put("Accept", "application/json");
				headers.put("charset", "UTF-8");
				url = "https://ssapi.shipstation.com/orders/createlabelfororder";
				
				JSONObject respLabelData = action.apiPost(jsonVal, url, headers, orderNno, userId, userIp);
				
				parameters.put("connUrl", "/orders/createlabelfororder");
				parameters.put("jsonHeader", "");
				parameters.put("jsonData", jsonVal);
				parameters.put("rtnContents", respLabelData.toString());
				apiMapper.insertApiConn(parameters);
				
				if (respLabelData != null) {
					
					System.out.println(respLabelData);
					
					shipmentId = respLabelData.optString("shipmentId");
					trkNo = respLabelData.optString("trackingNumber");
					
					HashMap<String, Object> stationInfo = new HashMap<String, Object>();
					stationInfo.put("nno", orderNno);
					stationInfo.put("userId", userId);
					stationInfo.put("orderNo", orderNo);
					stationInfo.put("hawbNo", trkNo);
					stationInfo.put("orderId", orderId);
					stationInfo.put("shipmentId", shipmentId);
					mapper.insertShipStationId(stationInfo);
					
					String base64Pdf = respLabelData.optString("labelData");
					String imageDir = realFilePath + "image/" + "aramex/";
					File file = new File(imageDir+trkNo+".PDF");
					
					FileOutputStream fos = new FileOutputStream(file);
					byte[] decoder = Base64.getDecoder().decode(base64Pdf);

					fos.write(decoder);
					fos.close();
					AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
					amazonS3 = new AmazonS3Client(awsCredentials);
					PutObjectResult asssd = new PutObjectResult();
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
					if(amazonS3 != null) {
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, userId+"_"+trkNo, file);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						asssd = amazonS3.putObject(putObjectRequest);
					}
				
					amazonS3 = null;
					file.delete();
					
					parameters.put("hawbNo", trkNo);
					comnService.createParcelBl(parameters);
					
					rst.setRstStatus("SUCCESS");
					rst.setRstHawbNo(trkNo);
					rst.setOrgHawbNo(trkNo);
					
					
				} else {
					throw new Exception();
				}

			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			parameters.put("status", "System Error");
			mapper.updateErrorStatus(parameters);
			rst.setRstStatus("FAIL");
			rst.setRstMsg(parameters.get("status").toString());
			rst.setRstHawbNo("");
		}
		
		
		return rst;
	}
	
	public void createLabel(String orderNno, String userId, String userIp) throws Exception {
		ArrayList<HashMap<String, Object>> orderInfo = new ArrayList<HashMap<String,Object>>();
		orderInfo = mapper.selectShipStationTest();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Basic ZjJkM2NkOTYyYzU0NGZhM2EwNjQ3M2Y0NWIyZjU2ZmI6ZmVlNGM5Y2MwOTNkNDY3ZGIwM2EzM2JjNmNkN2NkNDY=");
		headers.put("Accept", "application/json");
		headers.put("charset", "UTF-8");
		String url = "https://ssapi.shipstation.com/orders/createlabelfororder";
		
		for (int i = 0; i < orderInfo.size(); i++) {
			parameters = new HashMap<String, Object>();
			String nno = orderInfo.get(i).get("nno").toString();
			String orderId = orderInfo.get(i).get("orderId").toString();
			String hawbNo = "";
			
			parameters.put("nno", nno);
			parameters.put("wUserId", userId);
			parameters.put("wUserIp", userIp);
			parameters.put("userId", userId);
			parameters.put("userIp", userIp);
			
			String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			LinkedHashMap<String, Object> requestLabelData = new LinkedHashMap<String, Object>();
			requestLabelData.put("orderId", orderId);
			requestLabelData.put("carrierCode", "stamps_com");
			requestLabelData.put("serviceCode", "usps_first_class_mail");
			requestLabelData.put("packageCode", "package");
			requestLabelData.put("confirmation", "none");
			requestLabelData.put("shipDate", currentTime);
			requestLabelData.put("testLabel", false);
			Gson gson = new Gson();
			String jsonVal = gson.toJson(requestLabelData);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject respLabelData = action.apiPost(jsonVal, url, headers, nno, userId, userIp);
			
			parameters.put("connUrl", "/orders/createlabelfororder");
			parameters.put("jsonHeader", "");
			parameters.put("jsonData", jsonVal);
			parameters.put("rtnContents", respLabelData.toString());
			apiMapper.insertApiConn(parameters);
			
			if (respLabelData != null) {
				
				System.out.println(respLabelData);
				
				String shipmentId = respLabelData.optString("shipmentId");
				hawbNo = respLabelData.optString("trackingNumber");
				
				parameters.put("orderId", orderId);
				parameters.put("hawbNo", hawbNo);

				mapper.updateShipStationTest(parameters);
				
				String base64Pdf = respLabelData.optString("labelData");
				String imageDir = realFilePath + "image/" + "aramex/";
				File file = new File(imageDir+hawbNo+".PDF");
				
				FileOutputStream fos = new FileOutputStream(file);
				byte[] decoder = Base64.getDecoder().decode(base64Pdf);

				fos.write(decoder);
				fos.close();
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult asssd = new PutObjectResult();
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
		 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
				if(amazonS3 != null) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, userId+"_"+hawbNo, file);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					asssd = amazonS3.putObject(putObjectRequest);
				}
			
				amazonS3 = null;
				file.delete();
				
				parameters.put("hawbNo", hawbNo);
				comnService.createParcelBl(parameters);

			}
		}
		
		
		
	}

	public String createRequestData(HashMap<String, Object> parameters) throws Exception {
		ShipStationOrderVO orderInfo = new ShipStationOrderVO(); 
		LinkedHashMap<String, Object> registData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> billTo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> shipTo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> insuranceOptions = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> items = new ArrayList<LinkedHashMap<String, Object>>();
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LinkedHashMap<String, Object> weight = new LinkedHashMap<String, Object>();
		
		orderInfo = mapper.selectShipStationOrderInfo(parameters);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		registData.put("orderNumber",orderInfo.getOrderNo());
		registData.put("orderDate",currentTime);
		registData.put("orderStatus",orderInfo.getOrderStatus());
		registData.put("customerUsername",orderInfo.getCneeName());
		
		//billTo start
		billTo.put("name", "");
		billTo.put("company", "");
		billTo.put("street1", "");
		billTo.put("street2", "");
		billTo.put("street3", "");
		billTo.put("city", "");
		billTo.put("state", "");
		billTo.put("postalCode", "");
		billTo.put("country", "");
		billTo.put("phone", "");
		billTo.put("residential", "");
		
		registData.put("billTo",billTo);
		//billTo end
		
		//shipTo start
		shipTo.put("name", orderInfo.getCneeName());
		shipTo.put("company", "");
		shipTo.put("street1", orderInfo.getCneeAddr());
		shipTo.put("street2", orderInfo.getCneeAddrDetail());
		shipTo.put("street3", "");
		shipTo.put("city", orderInfo.getCneeCity());
		shipTo.put("state", orderInfo.getCneeState());
		shipTo.put("postalCode", orderInfo.getCneeZip());
		shipTo.put("country", orderInfo.getDstnNation());
		shipTo.put("phone", orderInfo.getCneeTel());
		shipTo.put("residential", false);
		
		registData.put("shipTo",shipTo);
		//shipTo end
		
		insuranceOptions.put("provider", "None");
		insuranceOptions.put("insureShipment", false);
		insuranceOptions.put("insuredValue", 0);
		//registData.put("insuranceOptions",insuranceOptions);
		
		//items start
		ArrayList<ShipStationItemVO> itemInfo = new ArrayList<ShipStationItemVO>();
		itemInfo = mapper.selectShipStationItemInfo(parameters);
		double totalWeight = 0;
		double amountPaid = 0;
		
		for(int i =0; i < itemInfo.size(); i++) {
			LinkedHashMap<String, Object> itemDetail = new LinkedHashMap<String, Object>();
			itemDetail.put("lineItemKey", "");
			if (itemInfo.get(i).getSku().equals("")) {
				itemDetail.put("sku", orderInfo.getOrderNo()+"-"+itemInfo.get(i).getSubNo());
			} else {
				itemDetail.put("sku", itemInfo.get(i).getSku());	
			}
			itemDetail.put("name", itemInfo.get(i).getItemDetail());
			itemDetail.put("imageUrl", "");
			itemDetail.put("quantity", itemInfo.get(i).getItemCnt());
			itemDetail.put("unitPrice", itemInfo.get(i).getUnitValue());
			itemDetail.put("taxAmount", "");
			itemDetail.put("shippingAmount", "");
			itemDetail.put("warehouseLocation", "");
			itemDetail.put("productId", "");
			itemDetail.put("fulfillmentSku", "");
			itemDetail.put("upc", "");
			items.add(itemDetail);
			amountPaid += Double.parseDouble(itemInfo.get(i).getUnitValue());
			totalWeight += Double.parseDouble(itemInfo.get(i).getUserWtaItem());
		}
		
		registData.put("items",items);
		//items end
		
		registData.put("confirmation","delivery");
		registData.put("amountPaid",amountPaid);
		registData.put("carrierCode","stamps_com");
		registData.put("serviceCode","usps_first_class_mail");
		registData.put("packageCode","package");
		registData.put("shipDate","");
		
		weight.put("value", totalWeight);
		weight.put("units", "ounces");
		registData.put("weight",weight);
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(registData);
		
		return jsonVal;
	}




}
