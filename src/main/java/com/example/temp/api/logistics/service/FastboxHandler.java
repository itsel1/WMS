package com.example.temp.api.logistics.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.CommonUtils;
import com.example.temp.api.CommonVariables;
import com.example.temp.api.Tracking;
import com.example.temp.api.logistics.mapper.LogisticsMapper;

@Service
public class FastboxHandler {

	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	CommonUtils utils;
	
	private static final String CONSUMER_KEY = "bafb893d6b3651e2fb36dc344ec227e3";
	private static final String TOKEN_KEY = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
	private static final String MALL_ID = "aciexpress2";
	private static final String HOST = "https://dhub-api.cafe24.com";
	
	
	
	public ArrayList<Tracking> getTracking(String hawbNo) {
	
		String path = "/api/Tracking";
		String httpUrl = HOST + path;
		
		HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
		requestBodyMap.put("mall_id", MALL_ID);
		requestBodyMap.put("fb_invoice_no", hawbNo);
		
		String requestBody = utils.convertStringToMap(requestBodyMap);
		System.out.println(requestBody);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			JSONObject metaObject = jsonObject.getJSONObject("meta");
			
			if ("200".equals(metaObject.optString("code"))) {
				JSONArray dataList = jsonObject.getJSONObject("response").getJSONArray("trace");
				ArrayList<Tracking> fbTrkList = getFastboxTrackingList(dataList);
				return fbTrkList;
			} else {
				System.err.println(metaObject.opt("message"));
				return null;
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}
	
	
	private ArrayList<Tracking> getFastboxTrackingList(JSONArray jsonArray) {
		ArrayList<Tracking> trackingList = new ArrayList<Tracking>();
		
		try {
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject dataObject = jsonArray.getJSONObject(i);
				Tracking trkInfo = new Tracking();
				String code = dataObject.optString("status_code");
				String[] dateTimePart = dataObject.optString("ins_timestamp").split("\\.");
				String dateTime = dateTimePart[0];
				String location = dataObject.optString("location");
				String description = dataObject.optString("status_msg");
				
				if (location.isEmpty()) {
					continue;
				} else {
					if ("RFI".equals(code)) {
						trkInfo.setStatusCode("300");
						
					} else if ("InTransit".equals(code) && checkLocation(location)) {
						trkInfo.setStatusCode("400");
						
					} else if ("InTransit".equals(code) && description.equals("On the Way") && checkLocation(location)) {
						trkInfo.setStatusCode("500");
						
					} else if ("OutForDelivery".equals(code)) {
						trkInfo.setStatusCode("500");
						
					} else if ("Delivered".equals(code)) {
						trkInfo.setStatusCode("600");
						
					} else {
						continue;
					}	
				}
				
				trkInfo.setDescription(description);
				trkInfo.setDateTime(dateTime);
				trkInfo.setLocation(location);
				trackingList.add(trkInfo);
			}
			
			return trackingList;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
			
		}
	}
	
	
	private boolean checkLocation(String location) {
		location = location.toUpperCase();
		if (!location.contains("SEOUL") && !location.contains("INCHEON") && !location.contains("KOREA")) {
			return true;
		} else {
			return false;
		}
	}


	public void getFastboxWeight() {
		
		System.out.println("Fastbox Weight Update Thread Start");
		String path = "/api/Tracking";
		String httpUrl = HOST + path;
		
		ArrayList<CommonVariables> wtList = new ArrayList<CommonVariables>();
		ArrayList<CommonVariables> dataList = new ArrayList<CommonVariables>();
		dataList = logisticsMapper.selectViewFastboxWeight();

		HashMap<String, String> apiHeaders = buildApiHeaders();
		
		for (int i = 0; i < dataList.size(); i++) {
			CommonVariables cv = dataList.get(i);
			try {
				
				String requestBody = utils.convertStringToMap(getFastboxWeightRequestBody(cv));
				HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, apiHeaders);
				
				if ("fail".equals(responseMap.get("status"))) {
					System.err.println(responseMap.get("status_msg"));
					continue;
				}

				JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
				JSONObject metaObject = jsonObject.getJSONObject("meta");
				
				if ("200".equals(metaObject.opt("code"))) {
					JSONObject dataObject = jsonObject.getJSONArray("response").getJSONObject(0);
					if (!"".equals(dataObject.optString("package_weight"))) {
						cv.setWta(dataObject.optDouble("package_weight"));
						cv.setWtc(dataObject.optDouble("volume_weight"));
						wtList.add(cv);
					}
				}
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
		}
		
		for (int ii = 0; ii < wtList.size(); ii++) {
			CommonVariables cv = wtList.get(ii);
			logisticsMapper.insertFastboxWeight(cv);
		}
		System.out.println("Fastbox Weight Update Thread End");
	}
	
		
	private HashMap<String, Object> getFastboxWeightRequestBody(CommonVariables cv) {
		
		HashMap<String, Object> requestBodyMap = new HashMap<>();
		requestBodyMap.put("mall_id", MALL_ID);
		requestBodyMap.put("fb_invoice_no", cv.getHawbNo());
		
		return requestBodyMap;
	}


	private HashMap<String, String> buildApiHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("consumerKey", CONSUMER_KEY);
		headers.put("Authorization", TOKEN_KEY);
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("Accept-Language", "ko-KR");
		return headers;
	}
}