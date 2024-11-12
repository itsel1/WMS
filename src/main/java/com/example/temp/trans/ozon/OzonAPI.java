
package com.example.temp.trans.ozon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.cse.CseAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
public class OzonAPI {

	@Value("${filePath}")
    String realFilePath;
	
	@Value("${ozonUrl}")
	String ozonPath;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	GtsAPI gtsApi;
	
	@Autowired
	CseAPI cseApi;
	
	@Autowired
	OzonMapper mapper;
	
	ComnVO comnS3Info;
	
	private AmazonS3 amazonS3;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 5 * * * *")
//	@Transactional(rollbackFor=Exception.class) 
	public void parcelSchdule() throws AddressException, MessagingException, Exception {
		String requestURL = ozonPath+"/NewPostings";
		
		
		ArrayList<HashMap<String,Object>> ozonUserList = new ArrayList<HashMap<String,Object>>(); 
		ozonUserList = mapper.selectOzonUserInfo();
		
		for(int i = 0; i < ozonUserList.size(); i++) {
			HashMap<String,Object> ozonUser = new HashMap<String,Object>();
			ozonUser = ozonUserList.get(i);
			ArrayList<HashMap<String,Object>> targetTrackingInfoList = new ArrayList<HashMap<String,Object>>();
			targetTrackingInfoList = mapper.selectTargetTrackingInfo(ozonUser.get("clientId").toString());
			
			for(int index = 0; index <targetTrackingInfoList.size(); index++) {
				HashMap<String,Object> targetTrackingInfo = new HashMap<String,Object>();
				targetTrackingInfo = targetTrackingInfoList.get(index);
				if(targetTrackingInfo.get("transCode").toString().equals("GTS")) {
					gtsApi.setReferToEventInfo(targetTrackingInfo);
				}else if (targetTrackingInfo.get("transCode").toString().equals("CSE")) {
					cseApi.setReferToEventInfo(targetTrackingInfo);
				}
			}
			
			try {
				requestURL = ozonPath+"/NewPostings";
				HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
				HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
				String outResult = new String();
				JsonParser parse = new JsonParser();
				
				getRequest.setHeader("Accept", "application/json");
				getRequest.setHeader("Content-Type", "application/json");
				getRequest.setHeader("Authorization", "Bearer "+ozonUser.get("token"));
				HttpResponse response = client.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
					ResponseHandler<String> handler = new BasicResponseHandler();
					outResult = handler.handleResponse(response);
					JsonElement element = parse.parse(outResult);
					JsonArray newPostingArray = element.getAsJsonObject().getAsJsonArray("Data");
					for(int jsonIndex = 0 ; jsonIndex< newPostingArray.size(); jsonIndex++) {
						String hawbNo = newPostingArray.get(jsonIndex).getAsJsonObject().get("TrackingNumber").toString();
						String orderNo = newPostingArray.get(jsonIndex).getAsJsonObject().get("PostingNumber").toString();
						HashMap<String,Object> orderInfo = new HashMap<String,Object>();
						HashMap<String,Object> targetTrackingInfo = new HashMap<String,Object>();
						orderInfo = mapper.selectOrderInfo(hawbNo);
						if(orderInfo.get("transCode").equals("GTS")) {
							//TRANS_CODE as transCode,
							//HAWB_NO as hawbNo,
							//ORDER_NO as orderNo,
							//CLIENT_ID as clientId
							targetTrackingInfo.put("transCode", "GTS");
							targetTrackingInfo.put("hawbNo", hawbNo);
							targetTrackingInfo.put("orderNo", orderNo);
							targetTrackingInfo.put("clientId", ozonUser.get("clientId").toString());
							gtsApi.setReferToEventInfo(targetTrackingInfo);
						}else if (orderInfo.get("transCode").equals("CSE")) {
							targetTrackingInfo.put("transCode", "CSE");
							targetTrackingInfo.put("hawbNo", hawbNo);
							targetTrackingInfo.put("orderNo", orderNo);
							targetTrackingInfo.put("clientId", ozonUser.get("clientId").toString());
							cseApi.setReferToEventInfo(targetTrackingInfo);
						}
					}
				} else {
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0 */2 * * *")
//	@Transactional(rollbackFor=Exception.class) 
	public void ozonScheduler() throws AddressException, MessagingException, Exception {
		String requestURL = ozonPath+"/GetAuthToken";
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성 
		String outResult = new String();
		JsonParser parse = new JsonParser();
		
		postRequest.setHeader("Accept", "application/json");
		postRequest.setHeader("Content-Type", "application/json");
		
		ArrayList<HashMap<String,Object>> ozonUserList = new ArrayList<HashMap<String,Object>>(); 
		ozonUserList = mapper.selectOzonUserInfo();
		for(int i = 0; i < ozonUserList.size(); i++) {
			HashMap<String,Object> ozonUpdateInfo = new HashMap<String,Object>();
			try {
				HashMap<String,Object> jsonValMap = new HashMap<String,Object>();
				String jsonVal = "";
				ozonUpdateInfo.put("idx", ozonUserList.get(i).get("idx"));
				jsonValMap.put("clientId", ozonUserList.get(i).get("clientId"));
				jsonValMap.put("clientSecret", ozonUserList.get(i).get("clientSecret"));
				jsonVal = getJsonStringFromMap(jsonValMap);
				postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
				HttpResponse response = client.execute(postRequest);
				if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
					ResponseHandler<String> handler = new BasicResponseHandler();
					outResult = handler.handleResponse(response);
					JsonElement element = parse.parse(outResult);
					String token = element.getAsJsonObject().get("Data").getAsString();
					ozonUpdateInfo.put("token", token);
					ozonUpdateInfo.put("apiStatus", "success");
					mapper.updateOzonInfo(ozonUpdateInfo);
				} else {
					ozonUpdateInfo.put("token", "");
					ozonUpdateInfo.put("apiStatus", "fail - response not 200");
					mapper.updateOzonInfo(ozonUpdateInfo);
				}
			}catch (Exception e) {
				// TODO: handle exception
				ozonUpdateInfo.put("token", "");
				ozonUpdateInfo.put("apiStatus", "fail - try/catch");
				mapper.updateOzonInfo(ozonUpdateInfo);
			}
			
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
	
	public String setTrackingEvent (HashMap<String,Object> evnetInfo) throws Exception{
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		HttpResponse response =null;
		String outResult = new String();
		String jsonVal = makeTrackingEvent(evnetInfo); 
		JsonParser parse = new JsonParser();
		String authorizationToken = mapper.selectTokenByHawb();
		String requestURL = ozonPath+"/PostingEvents";
		//String requestURL = "https://api.postshipping.com/api2/tracks?ReferenceNumber=1000016986";
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성 
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("Authorization", "Bearer "+authorizationToken);
		postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력
		response = client.execute(postRequest); 
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			outResult = "T";
		}else {
			outResult = "F";
		}
		return outResult;
	}
	
	private String makeTrackingEvent(HashMap<String,Object> evnetInfo) throws Exception {
		LinkedHashMap<String, Object> trackingData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> data = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataDetail = new LinkedHashMap<String, Object>();
		trackingData.put("PostingNumber", evnetInfo.get("orderNo").toString());
		trackingData.put("TrackingNumber", evnetInfo.get("hawbNo").toString());
		trackingData.put("ServiceCode", "");
		trackingData.put("CourierName", evnetInfo.get("transCode").toString());
		trackingData.put("CourierPhone", "");
		trackingData.put("PointCode", "");
		
		dataDetail.put("Guid", "");
		dataDetail.put("EventId", evnetInfo.get("eventId").toString());
		dataDetail.put("ProviderEventCode", evnetInfo.get("reference").toString());
		dataDetail.put("EventMessage", evnetInfo.get("eventDescription").toString());
		dataDetail.put("Moment", evnetInfo.get("time").toString());
		dataDetail.put("DeliveryPlanDate", null);
		dataDetail.put("EventPlaceUid", null);
		data.add(dataDetail);
		
		trackingData.put("Data", data);
		
		
		return getJsonStringFromMap(trackingData);
	}
	
	private static String getTagValue(String tag, Element eElement) {
		if(eElement.getElementsByTagName(tag).item(0) == null) {
			return "";
		}
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return "";
	    return nValue.getNodeValue();
	}
	
	private static String getTagValueList(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    String rtn = "";
	    for(int i =0; i < nlList.getLength(); i++) {
	    	Node nValue = (Node) nlList.item(0);
		    if(nValue == null) 
		        return null;
		    else {
		    	rtn += nValue.getNodeValue()+",";
		    }
	    }
	    
	    return rtn;
	}

	public HashMap<String,Object> selectEventInfo(HashMap<String, Object> parameters) throws Exception{
		// TODO Auto-generated method stub
		return mapper.selectEventInfo(parameters);
	}
	
	public Boolean insertOzonTrack(HashMap<String,Object> parameters) throws Exception{
		HashMap<String,Object> eventInfo = new HashMap<String,Object>();
		HashMap<String,Object> parametersIn = new HashMap<String,Object>();
		parametersIn.put("reference", parameters.get("reference"));
		parametersIn.put("transCode", parameters.get("transCode"));
		eventInfo = selectEventInfo(parameters);
		if(eventInfo == null) {
			return false;
		}
		eventInfo.put("hawbNo", parameters.get("hawbNo"));
		eventInfo.put("orderNo", parameters.get("orderNo"));
		eventInfo.put("time", parameters.get("time"));
		eventInfo.put("transCode", parametersIn.get("transCode"));
		if(setTrackingEvent(eventInfo).equals("T")) {
			mapper.insertOzonTrack(parametersIn);
		}else {
			return false;
		}
		return true;
	}
	
	public String selectTokenByHawb() throws Exception {
		return mapper.selectTokenByHawb();
	}

	public ArrayList<String> selectUidList() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUidList();
	}
	
}
