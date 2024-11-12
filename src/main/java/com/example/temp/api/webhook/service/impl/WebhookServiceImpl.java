package com.example.temp.api.webhook.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
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
import com.example.temp.api.webhook.mapper.WebhookMapper;
import com.example.temp.api.webhook.service.WebhookService;
import com.example.temp.api.webhook.vo.SoluTrkVO;
import com.example.temp.common.vo.ComnVO;

@Service
public class WebhookServiceImpl implements WebhookService{
	
	@Autowired
	WebhookMapper mapper;
	
	@Value("${filePath}")
    String realFilePath;

	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	@Override
	/**  
	 * 1. receive label PDF file from shipgate webhook.
     * 2. create label print. 
	 * 3. save label PDF file in server. 
	 * 4. upload file at aws S3.
	 * */
	public void createLabelPrint(HttpServletRequest request, HttpServletResponse response, Map<String, Object> jsonHeader, Map<String, Object> jsonData) throws Exception {
		JSONObject webhookBody = new JSONObject(jsonData);
		JSONObject result = (JSONObject) webhookBody.get("result");
		JSONArray trkNumList = (JSONArray) result.get("trackingNumberList");
		byte[] decodedBase64 = Base64.decodeBase64(result.getString("base64"));
		
		File base64ToImg = new File(realFilePath+"pdf/test.pdf");
		
		FileOutputStream fos = new FileOutputStream(base64ToImg);
		fos.write(decodedBase64);
		fos.close();
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		amazonS3 = new AmazonS3Client(awsCredentials);
		PutObjectResult asssd = new PutObjectResult();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
		if(amazonS3 != null) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, jsonHeader.get("userid").toString()+"_"+trkNumList.get(0), base64ToImg);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			asssd = amazonS3.putObject(putObjectRequest);
		}
		amazonS3 = null;
		base64ToImg.delete();
		
		
	}
	@Override
	public void updateTrackingInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> jsonHeader, Map<String, Object> jsonData) throws Exception {
		JSONObject webhookBody = new JSONObject(jsonData);
		JSONObject result = (JSONObject) webhookBody.get("result");
		JSONArray trackingLogList = (JSONArray) result.get("trackingLogList");
		
//		trakingVO.setTrackingNum(result.get("trackingNum").toString());
//		trakingVO.setStatus(result.get("status").toString());
		
		for(int index = 0; index < trackingLogList.length(); index++) {
			SoluTrkVO trakingVO = new SoluTrkVO();
			JSONObject trackingLogInfo = (JSONObject) trackingLogList.get(index);
			trakingVO.setTrackingNum(result.get("trackingNum").toString());
			trakingVO.setStatus(result.get("status").toString());
			trakingVO.setStatusDesceiption(trackingLogInfo.get("statusDescription").toString());
			trakingVO.setDetails(trackingLogInfo.get("details").toString());
			trakingVO.setChechpointerStatus(trackingLogInfo.get("checkpointerStatus").toString());
			trakingVO.setType(trackingLogInfo.get("type").toString());
			trakingVO.setIssueDateTime(trackingLogInfo.get("issueDateTime").toString());
			mapper.insertSoluTrkData(trakingVO);
		}
		
		
		
	}
	
}
