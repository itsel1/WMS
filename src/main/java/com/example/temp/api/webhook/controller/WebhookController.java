package com.example.temp.api.webhook.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.temp.api.webhook.service.WebhookService;
import com.example.temp.common.service.ComnService;
import com.google.gson.Gson;

@RestController
public class WebhookController {

	@Autowired
	WebhookService webhookService;
	
	@Autowired
	ComnService comnService;
	
	@RequestMapping(value="/webhook/label", method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public void createLabelPrint(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
		webhookService.createLabelPrint(request, response, jsonHeader, jsonData);
	}
	
	@RequestMapping(value="/webhook/tracking", method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public void updateTracking(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
		webhookService.updateTrackingInfo(request, response, jsonHeader, jsonData);
	}
	
	@RequestMapping(value="/webhook/Test", method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public void testss(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
		System.out.println(jsonData);
	}
	
	public String returnRequestWebHook(String orderReference, String regNo, String koblNo) throws Exception {
		HashMap<String, Object> rtnVal = new LinkedHashMap<String, Object>();
		HashMap<String, Object> jsonVal = new LinkedHashMap<String, Object>();
		HashMap<String,Object> parameterInfo = new HashMap<String,Object>();
		HttpResponse response =null;
		parameterInfo.put("orderReference", orderReference);
		parameterInfo.put("regNo", regNo);
		parameterInfo.put("koblNo", koblNo);
		parameterInfo.put("divName", "returnStatus");
		
		String userId = comnService.selectReturnRequestId(parameterInfo);
		parameterInfo.put("userId", userId);
		parameterInfo.put("sellerId", userId);
		String requestURL = comnService.selectWebHookInfoOne(parameterInfo);
		if(requestURL==null || requestURL.equals("")) {
			return "";
		}
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
		
		
		rtnVal = comnService.selectReturnRequestStatus(parameterInfo);
		if(rtnVal.get("RETURN_STATUS").equals("A000")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","1차 접수");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM","");
			jsonVal.put("RE_TRK_NO","");
			jsonVal.put("DATE",rtnVal.get("FIRST_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("A001")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","접수 신청");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM","");
			jsonVal.put("RE_TRK_NO","");
			jsonVal.put("DATE",rtnVal.get("W_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("A002")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","접수 완료");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("W_RECEIVE_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("B001")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","수거 중");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("W_START_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("C001")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","입고");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("WH_IN_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("C002")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","출고");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("W_START_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("C003")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","출고 대기");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("STOCK_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}else if(rtnVal.get("RETURN_STATUS").equals("D004")) {
			LinkedHashMap<String, Object> D004List2 = new LinkedHashMap<String, Object>();
			LinkedHashMap<String,Object> temp = new LinkedHashMap<String,Object>();
			ArrayList<LinkedHashMap<String,Object>> stockFile = new ArrayList<LinkedHashMap<String,Object>>();
			ArrayList<LinkedHashMap<String,Object>> rtnStockFile = new ArrayList<LinkedHashMap<String,Object>>();
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","검수 불합격");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("STOCK_DATE"));
			
			temp = comnService.selectStockMsg(rtnVal.get("NNO").toString());
			stockFile = comnService.selectStockFile(rtnVal.get("NNO").toString());
			
			D004List2.put("D004_CODE", temp.get("STATUS"));
			D004List2.put("D004_CODE", temp.get("WH_MEMO"));
			
			for(int fileIndex = 0; fileIndex < stockFile.size(); fileIndex++) {
				temp = new LinkedHashMap<String,Object>();
				temp.put("SUB_NO", stockFile.get(fileIndex).get("SUB_NO").toString());
				temp.put("IMG_URL", "http://"+stockFile.get(fileIndex).get("FILE_DIR").toString());
				rtnStockFile.add(temp);
			}
			
			D004List2.put("D004_IMG", rtnStockFile);
			
//			D004_CODE
//			D004_DETAIL
//			D004_IMG
			jsonVal.put("D004_DATA_LIST",D004List2);
		}else if(rtnVal.get("RETURN_STATUS").equals("D005")) {
			jsonVal.put("RETURN_STATUS",rtnVal.get("RETURN_STATUS"));
			jsonVal.put("STATUS_NAME","폐기");
			jsonVal.put("ORDER_NUMBER",rtnVal.get("ORDER_NUMBER"));
			jsonVal.put("ORDER_REFERENCE",rtnVal.get("ORDER_REFERENCE"));
			jsonVal.put("KOBL_NO",rtnVal.get("KOBL_NO"));
			jsonVal.put("REG_NO",rtnVal.get("REG_NO"));
			jsonVal.put("RE_TRK_COM",rtnVal.get("RE_TRK_COM"));
			jsonVal.put("RE_TRK_NO",rtnVal.get("RE_TRK_NO"));
			jsonVal.put("DATE",rtnVal.get("DEL_DATE"));
			jsonVal.put("D004_DATA_LIST","");
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(jsonVal);
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setEntity(new StringEntity(jsonString, "UTF-8")); //json 메시지 입력
		response = client.execute(postRequest);
		
		
		return "";
	}
}
