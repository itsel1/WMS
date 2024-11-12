package com.example.temp.api.logistics.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.CommonUtils;
import com.example.temp.api.Order;
import com.example.temp.api.Tracking;
import com.example.temp.api.logistics.mapper.LogisticsMapper;

@Service
public class YongsungHandler {

	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	CommonUtils utils;
	
	
	private final String HOST = "https://eparcel.kr/apiv2";
	private final String API_KEY = "3ecd931bfc114f048f4e90c91";
	
	
	public synchronized ArrayList<Tracking> getTracking(String hawbNo)  {
		
		String path = "/GetTracking";
		String httpUrl = HOST + path;
		String lang = "en-US";
		
		HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
		ArrayList<String> regNoList = new ArrayList<String>();
		regNoList.add(hawbNo);
		requestBodyMap.put("Type", "regno");
		requestBodyMap.put("RegNoList", regNoList);
		requestBodyMap.put("ApiKey", API_KEY);
		
		String requestBody = utils.convertStringToMap(requestBodyMap);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders(lang));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String code = jsonObject.optString("Code");
			String message = jsonObject.optString("Message");
			
			if ("0".equals(code)) {
				JSONObject dataObject = jsonObject.getJSONArray("Data").getJSONObject(0);
				JSONArray dataList = dataObject.getJSONArray("TrackingList");
				ArrayList<Tracking> ysTrkList = getYongsungTrackingList(dataList);
				return ysTrkList;
			} else {
				System.err.println(message);
				return null;
			}
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}
	
	private ArrayList<Tracking> getYongsungTrackingList(JSONArray jsonArray) {
		ArrayList<Tracking> trackingList = new ArrayList<Tracking>();
		
		for (int i = jsonArray.length() - 1; i >= 0; i--) {
			JSONObject dataObject = jsonArray.getJSONObject(i);
			Tracking trkInfo = new Tracking();
			String code = dataObject.optString("Status");
			String location = dataObject.optString("Location");
			String dateTime = dataObject.optString("IssueDateTime");
			String description = dataObject.optString("StatusDesc") + " / " + dataObject.optString("IssueDetail");
			
			if ("700".equals(code)) {
				trkInfo.setStatusCode("600");
			} else if ("550".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("500".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("400".equals(code)) {
				trkInfo.setStatusCode("480");
			} else if ("300".equals(code)) {
				trkInfo.setStatusCode("450");
			} else if ("200".equals(code)) {
				trkInfo.setStatusCode("400");
			} else {
				continue;
			}
			
			trkInfo.setDescription(description);
			trkInfo.setDateTime(dateTime);
			trkInfo.setLocation(location);
			trackingList.add(trkInfo);
		}
		
		return trackingList;
	}

	private HashMap<String, String> buildApiHeaders(String lang) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept-Language", lang);
		return headers;
	}

}