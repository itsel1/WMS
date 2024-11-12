package com.example.temp.trans.seko;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.impl.ManagerServiceImpl;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class SekoAPI {
	
	@Autowired
	SekoMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerService mgrService;
	
	@Value("${filePath}")
    String realFilePath;
	
	ComnVO comnS3Info;
	
	private AmazonS3 amazonS3;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public ApiShopifyResultVO sendSekoShipment(String nno) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		ApiOrderSekoVO orderInfo = new ApiOrderSekoVO();
		String jsonVal = makeSekoJson(nno);
		String outResult = new String();
		JsonParser parse = new JsonParser();
		HttpResponse response =null;
		
		
		try {
			String requestURL = "https://api.omniparcel.com/labels/printshipment";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("charset", "UTF-8");
			orderInfo = mapper.selectListInfoForSeko(nno);
			if(orderInfo.getFood().toUpperCase().equals("Y")) {
				postRequest.addHeader("access_key", "68DB2B0472DB4FEF8DC68CAF17198DBCF210821A381B083A8E"); //KEY 입력
			}else {
				postRequest.addHeader("access_key", "843E022433020394EC5F11B9587153E89F6810BEDD91D2C02C"); //KEY 입력
			}
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
			
			response = client.execute(postRequest);
			
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				
				resultShopify.setStatus("S");

				resultShopify.setShipperReference("1489353");
				if(element.getAsJsonObject().get("Errors").getAsJsonArray().size() != 0) {
					resultShopify.setErrorMsg(element.getAsJsonObject().get("Errors").toString());
					resultShopify.setStatus("F");
					return resultShopify;
				}
				
				resultShopify.setHawbNo(element.getAsJsonObject().get("Consignments").getAsJsonArray().get(0).getAsJsonObject().get("Connote").getAsString());
				//무게 insert할 곳
				orderInfo.setHawbNo(resultShopify.getHawbNo());
				insertSekoWeight(orderInfo);
				
				String base64Pdf = element.getAsJsonObject().get("Consignments").getAsJsonArray().get(0).getAsJsonObject().get("OutputFiles").getAsJsonObject().get("LABEL_PDF_100X150").getAsJsonArray().get(0).getAsString();
				String ImageDir = realFilePath + "image/" + "aramex/";
				File file = new File(ImageDir+resultShopify.getHawbNo()+".PDF");
				
				try {
					// To be short I use a corrupted PDF string, so make sure to use a valid one if
					// you want to preview the PDF file
					
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
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, resultShopify.getShipperReference()+"-"+resultShopify.getHawbNo(), file);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						asssd = amazonS3.putObject(putObjectRequest);
					}
					amazonS3 = null;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return resultShopify;
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity);
				JsonElement element = parse.parse(outResult);
				resultShopify.setErrorMsg(element.getAsJsonObject().get("Errors").toString());
				resultShopify.setStatus("F");
				return resultShopify;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			HttpEntity entity = response.getEntity();
			outResult = EntityUtils.toString(entity);
			JsonElement element = parse.parse(outResult);
			resultShopify.setErrorMsg(element.getAsJsonObject().get("Errors").toString());
			resultShopify.setStatus("F");
			return resultShopify;
		}
		
	}

	public String makeSekoJson(String nno) throws Exception{
		ApiOrderSekoVO orderInfo = new ApiOrderSekoVO();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> origin = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> originAddress = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> destination = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> destinationAddress = new LinkedHashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String, Object>> commodities = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> commoditiesOne = new LinkedHashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String, Object>> packages = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> packagesOne = new LinkedHashMap<String, Object>();
		ArrayList<String> outputs = new ArrayList<String>();
		ArrayList<ApiOrderItemSekoVO> orderItem = new ArrayList<ApiOrderItemSekoVO>();
		
		try {
			orderInfo = mapper.selectListInfoForSeko(nno);
			orderInfo.setSymmetryKey(originKey.getSymmetryKey());
			orderInfo.dncryptData();
			
			dataOne = new LinkedHashMap<String, Object>();
			
			dataOne.put("DeliveryReference", orderInfo.getOrderNo());//customer Reference1
			dataOne.put("Reference2", "");//customer Reference2
			dataOne.put("Reference3", "");//customer Reference3
			
			dataOne.put("Service", "eCommerce Express Tracked");
			//Service Option
			
			//origin area
			origin.put("Id", "");
			origin.put("Name", orderInfo.getShipperName());
			origin.put("ContactPerson", orderInfo.getShipperName());
			origin.put("PhoneNumber", (orderInfo.getShipperHp() == null || orderInfo.getShipperHp().equals("")) ? orderInfo.getShipperTel():orderInfo.getShipperHp());
			origin.put("Email", orderInfo.getShipperEmail());
			origin.put("DeliveryInstructions", "");
			origin.put("CostCentreName", "");
			origin.put("SendTrackingEmail", "false");
			
			//origin Address Area
			originAddress.put("BuildingName", orderInfo.getShipperAddrDetail());
			originAddress.put("StreetAddress", orderInfo.getShipperAddr());
			originAddress.put("Suburb", orderInfo.getShipperCity());
			originAddress.put("City", orderInfo.getShipperState());
			originAddress.put("PostCode", orderInfo.getShipperZip());
			originAddress.put("CountryCode", "KR");
			//origin Address Area End
			
			origin.put("Address", originAddress);
			dataOne.put("Origin", origin);
			//origin Area End
			
			//Destination Area
			destination.put("Id", "");
			destination.put("Name", orderInfo.getCneeName());
			destination.put("ContactPerson", orderInfo.getCneeName());
			destination.put("PhoneNumber", (orderInfo.getCneeHp() == null || orderInfo.getCneeHp().equals("")) ? orderInfo.getCneeTel():orderInfo.getCneeHp());
			destination.put("Email", orderInfo.getCneeEmail());
			destination.put("DeliveryInstructions", orderInfo.getDlvReqMsg());
			destination.put("CostCentreName", "");
			destination.put("SendTrackingEmail", "true");
			
			//destination Address Area
			destinationAddress.put("BuildingName", orderInfo.getCneeAddrDetail());
			destinationAddress.put("StreetAddress", orderInfo.getCneeAddr());
			destinationAddress.put("Suburb", orderInfo.getCneeCity());
			destinationAddress.put("City", orderInfo.getCneeState());
			destinationAddress.put("PostCode", orderInfo.getCneeZip());
			destinationAddress.put("CountryCode", orderInfo.getDstnNation());
			//destination Address Area End
			
			destination.put("Address", destinationAddress);
			//destination Area End
			dataOne.put("Destination", destination);
			
			orderItem = mapper.selectItemInfoForSeko(orderInfo.getNno());
			for(int j =0; j < orderItem.size(); j++) {
				commoditiesOne = new LinkedHashMap<String, Object>();
				commoditiesOne.put("Description", orderItem.get(j).getItemDetail());
				commoditiesOne.put("Units", orderItem.get(j).getItemCnt());
				commoditiesOne.put("HarmonizedCode", orderItem.get(j).getHsCode());
				commoditiesOne.put("UnitValue", Double.parseDouble(orderItem.get(j).getUnitValue()));
				commoditiesOne.put("UnitKg", Double.parseDouble(orderItem.get(j).getUserWtaItem()));
				commoditiesOne.put("Currency", orderItem.get(j).getChgCurrency());
				commoditiesOne.put("Country", orderItem.get(j).getMakeCntry());
				commodities.add(commoditiesOne);
			}
			
			dataOne.put("Commodities", commodities);
			
			packagesOne.put("Height", Double.parseDouble(orderInfo.getUserHeight()));
			packagesOne.put("Length", Double.parseDouble(orderInfo.getUserLength()));
			packagesOne.put("Width", Double.parseDouble(orderInfo.getUserWidth()));
			packagesOne.put("Kg", Double.parseDouble(orderInfo.getUserWta()));
			packagesOne.put("Name", "");
			packagesOne.put("Type", "Box");
			packagesOne.put("OverLabelBarcode", "");
			
			packages.add(packagesOne);
			dataOne.put("Packages", packages);
			
			dataOne.put("issignaturerequired", false);
			if(orderInfo.getPayment().equals("DDU")) {
				dataOne.put("DutiesAndTaxesByReceiver", true);
			}else {
				dataOne.put("DutiesAndTaxesByReceiver", false);
			}
			dataOne.put("VendorId","");
			dataOne.put("NZIRDNumber","");
			dataOne.put("GBEORINumber","");
			dataOne.put("PrintToPrinter",false);
			dataOne.put("IncludeLineDetails",true);
			
			outputs.add("LABEL_PDF_100X150");
			dataOne.put("Outputs",outputs);
			        
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getJsonStringFromMap(dataOne);
	}
	
	public static String getJsonStringFromMap( HashMap<String, Object> map ) throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject.toString();
    }

	public void updateHawb(Map<String, Object> trackInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateHawb(trackInfo);
	}

	public ArrayList<HashMap<String, Object>>  makeSekoPod(String hawbNo) throws Exception {
		ApiOrderSekoVO orderInfo = new ApiOrderSekoVO();
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		HttpResponse response =null;
		ArrayList<Object> test = new ArrayList<Object> ();
		String outResult = new String();
		JsonParser parse = new JsonParser();
		String requestURL = "https://api.omniparcel.com/labels/statusv2";
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
		postRequest.setHeader("Accept", "application/json");
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("charset", "UTF-8");
		orderInfo = mapper.selectListInfoForSeko(mgrService.selectNNOByHawbNo(hawbNo));
		if(orderInfo.getFood().equals("Y")) {
			postRequest.addHeader("access_key", "68DB2B0472DB4FEF8DC68CAF17198DBCF210821A381B083A8E"); //KEY 입력
		}else {
			postRequest.addHeader("access_key", "843E022433020394EC5F11B9587153E89F6810BEDD91D2C02C"); //KEY 입력
		}
		test.add(hawbNo);
		
		postRequest.setEntity(new StringEntity(test.toString(), "UTF-8")); //json 메시지 입력
		
		response = client.execute(postRequest);
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			podDetatil  = new LinkedHashMap<String,Object>();
			HashMap<String,Object> mawbArrInfo = new HashMap<String,Object>();
			mawbArrInfo = mgrService.selectMawbArrInfo(hawbNo);
			if(mawbArrInfo != null) {
				if(!mawbArrInfo.get("userDate").toString().equals("1900-01-01 00:00:00.0")) {
					podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode","S500");
					podDetatil.put("UpdateDateTime", mawbArrInfo.get("userDate"));
					podDetatil.put("UpdateLocation", mawbArrInfo.get("orgStation")+", "+mawbArrInfo.get("nationCode"));
					
					podDetatil.put("UpdateDescription", "Delivered");
					podDetatil.put("ProblemCode","S500"); 
					podDetatil.put("Comments", "");//aci만 추가
					podDetatailArray.add(podDetatil);
				}
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S400");
				podDetatil.put("UpdateDateTime", mawbArrInfo.get("wDate"));
				podDetatil.put("UpdateLocation", mawbArrInfo.get("orgStation")+", "+mawbArrInfo.get("nationCode"));
				podDetatil.put("UpdateDescription", "Arrived at ACI location");
				podDetatil.put("ProblemCode","S400"); 
				podDetatil.put("Comments", "");//aci만 추가
				podDetatailArray.add(podDetatil);				
			}
			
			outResult = handler.handleResponse(response);
			JsonElement element = parse.parse(outResult);
			
			for(int i = element.getAsJsonArray().get(0).getAsJsonObject().get("Events").getAsJsonArray().size()-1; i >-1; i --) {
				podDetatil  = new LinkedHashMap<String,Object>();
				JsonArray trackingJson = element.getAsJsonArray().get(0).getAsJsonObject().get("Events").getAsJsonArray();
				if(trackingJson.get(i).getAsJsonObject().get("OmniCode").getAsString().equals("OP-1") ||
						trackingJson.get(i).getAsJsonObject().get("OmniCode").getAsString().equals("OP-4")	) {
					continue;
				}
				
				if(trackingJson.get(i).getAsJsonObject().get("Code").isJsonNull()) {
					podDetatil.put("UpdateCode","");
				}else {
					podDetatil.put("UpdateCode",trackingJson.get(i).getAsJsonObject().get("Code").getAsString());
				}
				
				podDetatil.put("UpdateDateTime", trackingJson.get(i).getAsJsonObject().get("EventDT").getAsString());
				podDetatil.put("UpdateLocation", trackingJson.get(i).getAsJsonObject().get("Location").getAsString());
				podDetatil.put("UpdateDescription", trackingJson.get(i).getAsJsonObject().get("Description").getAsString());
				podDetatil.put("ProblemCode",trackingJson.get(i).getAsJsonObject().get("OmniCode").getAsString()); 
				podDetatailArray.add(podDetatil);
			}
			//1. hawb 잡힌 시간(입고), order에 잡힌 시간 (등록) 
		}
		
		aciPodInfo = mapper.selectAciPodInfo(hawbNo);
		
		if(!aciPodInfo.get("hawbDate").equals("")) {
			podDetatil = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","S011");
			podDetatil.put("UpdateDateTime", aciPodInfo.get("hawbDate"));
			podDetatil.put("UpdateLocation", "Incheon,Seoul,KR");
			podDetatil.put("UpdateDescription", "Finished warehousing.");
			podDetatil.put("ProblemCode","S011"); 
			podDetatil.put("Comments", "");//aci만 추가
			podDetatailArray.add(podDetatil);
		}
		
		podDetatil = new LinkedHashMap<String,Object>();
		
		podDetatil.put("UpdateCode","S001");
		podDetatil.put("UpdateDateTime", aciPodInfo.get("orderDate"));
		podDetatil.put("UpdateLocation", "KR");
		podDetatil.put("UpdateDescription", "Order information has been entered");
		podDetatil.put("ProblemCode","S001"); 
		podDetatil.put("Comments", "");//aci만 추가
		podDetatailArray.add(podDetatil);
		
		return podDetatailArray;
	}

	public ArrayList<HashMap<String, Object>> makePodDetatailArray(String rtnJson, String hawbNo,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	public void insertSekoWeight(ApiOrderSekoVO orderInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertSekoWeight(orderInfo);
	}
	
	public Map<String,Object> selectBlApplySEK(String orderNno, String userId, String remoteAddr)
			throws Exception {
		// TODO Auto-generated method stub
		UserOrderListVO userInfo = new UserOrderListVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		comnService.insertTBFromTMP(orderNno);
		userInfo = comnService.selectUserRegistOrderOne(orderNno);
		Map<String,Object> rtnVal = new HashMap<String,Object>();
		resultShopify = sendSekoShipment(orderNno);
		if(resultShopify.getStatus().equals("S")) {//success
			rtnVal.put("rstCode", "SUCCESS");
			//apiResult = (ArrayList<HashMap<String, Object>>) test.get("Consignments");
			HashMap<String,Object> trackInfo = new HashMap<String,Object>();
			trackInfo.put("nno",orderNno);
			trackInfo.put("shipperReference",resultShopify.getShipperReference());
			trackInfo.put("hawbNo",resultShopify.getHawbNo());
			rtnVal.put("rstHawbNo", resultShopify.getHawbNo());
			rtnVal.put("rstNno", orderNno);
			updateHawb(trackInfo);
			//System.out.println(trackInfo.get("shipperReference")+"-"+trackInfo.get("hawbNo"));
		}else {//fail
			rtnVal.put("rstCode", "FAIL");
			rtnVal.put("rstHawbNo", "-");
			rtnVal.put("rstNno", orderNno);
			rtnVal.put("rstErrorMsg", resultShopify.getErrorMsg());
			comnService.insertTMPFromTB(orderNno, resultShopify.getErrorMsg(), userId, remoteAddr);
		}
//		if(resultShopify.getStatus().equals("OK")) {
//			//hawb 업데이트
//			mapper.updateHawbNoInTbHawb(resultShopify.getHawbNo(), orderNno);
//			mapper.updateHawbNoInTbOrderList(resultShopify.getHawbNo(), orderNno);
//		}else {
//			//TMP로 옮기고 TB 삭제, error 코드 확인할 것.
//			HashMap<String,String> parameters = new HashMap<String,String>();
//			
//			parameters.put("nno", orderNno);
//			parameters.put("status", resultShopify.getStatusList());
//			mapper.deleteHawbNoInTbHawb(orderNno);
//			mapper.insertTmpFromOrderList(orderNno, resultShopify.getErrorMsg());
//			mapper.insertTmpFromOrderItem(orderNno);
//			mapper.deleteOrderListByNno(orderNno);
//			//mapper.updateErrorStatus(parameters);
//			
//			
//		}
		
		return rtnVal;
	}

}

