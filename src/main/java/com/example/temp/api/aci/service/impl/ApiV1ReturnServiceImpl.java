package com.example.temp.api.aci.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransactionRollbackException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Track;
import javax.validation.constraints.Email;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.mapper.ApiV1ReturnMapper;
import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.aci.service.ApiV1Service;
import com.example.temp.api.aci.service.ApiV1ReturnService;
import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.shopify.ShopifyAPI;
import com.example.temp.api.webhook.controller.WebhookController;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.manager.service.ManagerReturnService;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.mapper.MemberReturnOrderMapper;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.comn.ComnAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FastBoxMapper;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.parcll.ParcllAPI;
import com.example.temp.trans.parcll.ParcllMapper;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.val;
import net.aramex.ws.ShippingAPI.v1.Service_1_0Proxy;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingClientInfo;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingResult;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentRequest;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentResponse;

@Service
public class ApiV1ReturnServiceImpl implements ApiV1ReturnService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApiV1ReturnMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	ManagerReturnService mgrRtnService;
	
	@Autowired
	WebhookController webhookController;
	
	@Autowired
	ApiV1Service apiV1Service;
	
	@Autowired
	MemberReturnOrderMapper memberReturnOrderMapper;

	
	@Value("${orderInfoChkStatus}")
    String orderInfoChkStatus;
	
	String YYYYMMDDHHMISS = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
	String YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	
	@Value("${filePath}")
	String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	@Autowired
	OcsAPI ocsApi;
	
	@Autowired
	EfsAPI efsApi;
	
	@Autowired
	CJApi cjapi;
	
	@Autowired
	SekoAPI sekoApi;
	
	@Autowired
	YongSungAPI ysApi;
	
	@Autowired
	ComnAPI comnApi;
	
	@Autowired
	ShopifyAPI shopifyApi;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	EmsApi emsApi;
	
	@Autowired
	Type86API t86Api;
	
	@Autowired
	FastboxAPI fastboxApi;
	
	@Autowired
	HanjinAPI hjApi;
	
	@Autowired
	ParcllMapper prclMapper;
	
	@Autowired
	FastBoxMapper fbMapper;
	
	Service_1_0Proxy apiProxy = new Service_1_0Proxy();

	TrackingService_1_0Proxy trackingProxy = new TrackingService_1_0Proxy(); 

	net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy apiTrackingProxy = new net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy();


	@Override
	public HashMap<String, Object> setOrderRequestVal( Map<String,Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> errors = new HashMap<String,Object>();
		ReturnRequestVO rtnVal = new ReturnRequestVO();
		ArrayList<ReturnRequestItemVO> rtnItemVal = new ArrayList<ReturnRequestItemVO>();
		
			
		//header
		errors = checkApiOrderInfo(jsonHeader, request);
		
		if(errors.get("STATUS").equals("FAIL")){
			return errors;
		}
		JSONObject dataObj = new JSONObject(jsonData);
		String userKey = apiService.selectUserKey(jsonHeader.get("userid").toString());		
		String chekck = mapper.checkSellerId(dataObj.get("SELLER_ID").toString());
		
		if(chekck == null) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG","SELLER_ID is invalid value.");
			errors.put("STATUS_CODE","LE002");
			return errors;
		}
		
		
		String chkResult = checkJsonField("RETURN_INFO_INSERT","POST","array",dataObj);
		if(chkResult.length()!=0) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG",chkResult);
			errors.put("STATUS_CODE","LE001");
			return errors;
		}

		String senderDecode = AES256Cipher.AES_Decode(dataObj.get("SENDER").toString(), userKey);
		String returnDecode = AES256Cipher.AES_Decode(dataObj.get("RETURN").toString(), userKey);
		String fileDecode = AES256Cipher.AES_Decode(dataObj.get("FILE").toString(), userKey);
	
		String nno = "";
		String orderReference = "";
		String calculateId = "";
		String koblNo = "";
		

		chkResult = checkJsonField("RETURN_INFO_INSERT","POST","data",dataObj);
		if(chkResult.length()!=0) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG",chkResult);
			errors.put("STATUS_CODE","LE001");
			return errors;
		}
		
		if(request.getMethod().toString().toUpperCase().equals("POST")) {
			nno = comnService.selectNNO();
			
			if(dataObj.get("CALCULATE_ID").toString().equals("")) {
				calculateId = dataObj.get("SELLER_ID").toString();
			}else {
				calculateId = dataObj.get("CALCULATE_ID").toString();
			}
			if(dataObj.get("ORDER_REFERENCE").toString().equals("")) {
				orderReference = nno;
			}else {
				orderReference = dataObj.get("ORDER_REFERENCE").toString();
			}
			koblNo = dataObj.get("KOBL_NO").toString();
		}else if (request.getMethod().toString().toUpperCase().equals("PATCH")) {
			if(dataObj.get("CALCULATE_ID").toString().equals("")) {
				calculateId = dataObj.get("SELLER_ID").toString();
			}else {
				calculateId = dataObj.get("CALCULATE_ID").toString();
			}
			orderReference = dataObj.get("ORDER_REFERENCE").toString();
			koblNo = dataObj.get("KOBL_NO").toString();
			nno = mapper.selectNnoByETC(orderReference,calculateId,koblNo);
			if(nno == null) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG","Unregistered Data");
				errors.put("STATUS_CODE","NE001");
				return errors;
			}
		}
		
		rtnVal.setNno(nno);
		rtnVal.setPickType(dataObj.get("PICK_TYPE").toString());//object.get("PICK_TYPE").toString();
		rtnVal.setOrgStation("082");
		rtnVal.setDstnNation(dataObj.get("DSTN_NATION").toString());//object.get("DSTN_NATION").toString();
		//rtnVal.setUserId(dataObj.get("USER_ID").toString());//object.get("USER_ID").toString();
		rtnVal.setUserId(jsonHeader.get("userid").toString());//object.get("USER_ID").toString();
		rtnVal.setSellerId(dataObj.get("SELLER_ID").toString());//object.get("SELLER_ID").toString()
		rtnVal.setOrderNo(dataObj.get("ORDER_NO").toString());//object.get("ORDER_NO").toString();
		rtnVal.setOrderDate(dataObj.get("ORDER_DATE").toString());//object.get("ORDER_DATE").toString();
		rtnVal.setKoblNo(dataObj.get("KOBL_NO").toString());
		rtnVal.setCalculateId(calculateId);
		rtnVal.setOrderReference(orderReference);
		if(dataObj.get("PICK_TYPE").toString().equals("A")) {
			if(dataObj.get("RE_TRK_COM").toString().equals("")) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG","RE_TRK_COM is invalid value.");
				errors.put("STATUS_CODE","LE002");
				return errors;
			}else if(dataObj.get("RE_TRK_NO").toString().equals("")) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG","RE_TRK_NO is invalid value.");
				errors.put("STATUS_CODE","LE002");
				return errors;
			}
		}
		rtnVal.setReTrkCom(dataObj.get("RE_TRK_COM").toString());
		rtnVal.setReTrkNo(dataObj.get("RE_TRK_NO").toString());
		rtnVal.setWhMsg(dataObj.get("WARE_HOUSE_MSG").toString());
		rtnVal.setDeliveryMsg(dataObj.get("DELIVERY_MSG").toString());
		rtnVal.setReturnType(dataObj.get("RETURN_TYPE").toString());
		rtnVal.setFood(dataObj.get("FOOD").toString());
		rtnVal.setRootSite(dataObj.get("SYSTEM").toString());
		if(dataObj.get("PICK_TYPE").toString().equals("A")) {
			rtnVal.setState("A002");
		}else {
			rtnVal.setState("A001");
		}
		rtnVal.setWUserId(jsonHeader.get("userid").toString());
		rtnVal.setWUserIp(request.getRemoteAddr());
		
		JSONObject senderObj = new JSONObject(senderDecode);
	
		chkResult = checkJsonField("RETURN_INFO_INSERT","POST","sender",senderObj);
		if(chkResult.length()!=0) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG",chkResult);
			errors.put("STATUS_CODE","LE001");
			return errors;
		}
		rtnVal.setSenderName(senderObj.get("SENDER_NAME").toString());
		rtnVal.setSenderZip(senderObj.get("SENDER_ZIP").toString());
		rtnVal.setSenderState(senderObj.get("SENDER_STATE").toString());
		rtnVal.setSenderCity(senderObj.get("SENDER_CITY").toString());
		rtnVal.setSenderAddr(senderObj.get("SENDER_ADDR").toString());
		//rtnVal.setSenderAddrDetail(senderObj.get("SENDER_ADDR_DETAIL").toString());
		rtnVal.setSenderBuildNm(senderObj.get("SENDER_BUILD_NM").toString());
		rtnVal.setSenderTel(senderObj.get("SENDER_TEL").toString());
		rtnVal.setSenderHp(senderObj.get("SENDER_HP").toString());
		rtnVal.setSenderEmail(senderObj.get("SENDER_EMAIL").toString());
		rtnVal.setNativeSenderName(senderObj.get("NATIVE_SENDER_NAME").toString());
		rtnVal.setNativeSenderAddr(senderObj.get("NATIVE_SENDER_ADDR").toString());
		rtnVal.setNativeSenderAddrDetail(senderObj.get("NATIVE_SENDER_ADDR_DETAIL").toString());
		rtnVal.setCdRemark(senderObj.get("CD_REMARK").toString());
		
		JSONObject returnObj = new JSONObject(returnDecode);
		chkResult = checkJsonField("RETURN_INFO_INSERT","POST","return",returnObj);
		if(chkResult.length()!=0) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG",chkResult);
			errors.put("STATUS_CODE","LE001");
			return errors;
		}
		
		rtnVal.setAttnName(returnObj.get("ATTN_NAME").toString());
		rtnVal.setAttnTel(returnObj.get("ATTN_TEL").toString());
		rtnVal.setAttnEmail(returnObj.get("ATTN_EMAIL").toString());
		rtnVal.setPickupName(returnObj.get("PICKUP_NAME").toString());
		rtnVal.setPickupTel(returnObj.get("PICKUP_TEL").toString());
		rtnVal.setPickupMobile(returnObj.get("PICKUP_MOBILE").toString());
		rtnVal.setPickupZip(returnObj.get("PICKUP_ZIP").toString());
		rtnVal.setPickupAddr(returnObj.get("PICKUP_ADDR").toString());
		rtnVal.setPickupEngAddr(returnObj.get("PICKUP_ENG_ADDR").toString());
		rtnVal.setPickupAddrDetail(returnObj.get("PICKUP_ADDR_DETAIL").toString());
		rtnVal.setReturnReason(returnObj.get("RETURN_REASON").toString());
		rtnVal.setReturnReasonDetail(returnObj.get("RETURN_REASON_DETAIL").toString());
		
		
		if (fileDecode.equals("")) {
			rtnVal.setTaxType("N");
		} else {
			JSONObject fileObj = new JSONObject(fileDecode);
			chkResult = checkJsonField("RETURN_INFO_INSERT","POST","file",fileObj);
			if(chkResult.length()!=0) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG",chkResult);
				errors.put("STATUS_CODE","LE001");
				return errors;
			}
			
			rtnVal.setTaxType(fileObj.get("TAX_TYPE").toString());
			if(rtnVal.getTaxType().equals("Y")) {
				rtnVal.setFileReasonType(fileObj.get("FILE_REASON_TYPE").toString());
				rtnVal.setFileReason(fileObj.get("FILE_REASON").toString());
				rtnVal.setFileReasonExten(fileObj.get("FILE_REASON_EXTEN").toString());
				//rtnVal.setFileReasonExten("jpg");
				
				rtnVal.setFileCaptureType(fileObj.get("FILE_CAPTURE_TYPE").toString());
				rtnVal.setFileCapture(fileObj.get("FILE_CAPTURE").toString());
				rtnVal.setFileCaptureExten(fileObj.get("FILE_CAPTURE_EXTEN").toString());
				//rtnVal.setFileCaptureExten("jpg");
				
				rtnVal.setFileMessengerType(fileObj.get("FILE_MESSENGER_TYPE").toString());
				rtnVal.setFileMessenger(fileObj.get("FILE_MESSENGER").toString());
				rtnVal.setFileMessengerExten(fileObj.get("FILE_MESSENGER_EXTEN").toString());
				//rtnVal.setFileMessengerExten("jpg");
				
				rtnVal.setFileClType(fileObj.get("FILE_CL_TYPE").toString());
				rtnVal.setFileCl(fileObj.get("FILE_CL").toString());
				rtnVal.setFileClExten(fileObj.get("FILE_CL_EXTEN").toString());
				//rtnVal.setFileClExten("jpg");
				
//				rtnVal.setFileIcType(fileObj.get("FILE_IC_TYPE").toString());
//				rtnVal.setFileIc(fileObj.get("FILE_IC").toString());
//				rtnVal.setFileIcExten(fileObj.get("FILE_IC_EXTEN").toString());
				//rtnVal.setFileIcExten("jpg");
				
				rtnVal.setFileCopyBankType(fileObj.get("FILE_COPY_BANK_TYPE").toString());
				rtnVal.setFileCopyBank(fileObj.get("FILE_COPY_BANK").toString());
				rtnVal.setFileCopyBankExten(fileObj.get("FILE_COPY_BANK_EXTEN").toString());
				//rtnVal.setFileCopyBankExten("jpg");
				
				rtnVal.setTaxReturn(fileObj.get("TAX_RETURN").toString());
			}
		}
		
		/*
		JSONObject fileObj = new JSONObject(fileDecode);
		chkResult = checkJsonField("RETURN_INFO_INSERT","POST","file",fileObj);
		if(chkResult.length()!=0) {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG",chkResult);
			errors.put("STATUS_CODE","LE001");
			return errors;
		}
		
		rtnVal.setTaxType(fileObj.get("TAX_TYPE").toString());
		if(rtnVal.getTaxType().equals("Y")) {
			rtnVal.setFileReasonType(fileObj.get("FILE_REASON_TYPE").toString());
			rtnVal.setFileReason(fileObj.get("FILE_REASON").toString());
			rtnVal.setFileReasonExten(fileObj.get("FILE_REASON_EXTEN").toString());
			//rtnVal.setFileReasonExten("jpg");
			
			rtnVal.setFileCaptureType(fileObj.get("FILE_CAPTURE_TYPE").toString());
			rtnVal.setFileCapture(fileObj.get("FILE_CAPTURE").toString());
			rtnVal.setFileCaptureExten(fileObj.get("FILE_CAPTURE_EXTEN").toString());
			//rtnVal.setFileCaptureExten("jpg");
			
			rtnVal.setFileMessengerType(fileObj.get("FILE_MESSENGER_TYPE").toString());
			rtnVal.setFileMessenger(fileObj.get("FILE_MESSENGER").toString());
			rtnVal.setFileMessengerExten(fileObj.get("FILE_MESSENGER_EXTEN").toString());
			//rtnVal.setFileMessengerExten("jpg");
			
			rtnVal.setFileClType(fileObj.get("FILE_CL_TYPE").toString());
			rtnVal.setFileCl(fileObj.get("FILE_CL").toString());
			rtnVal.setFileClExten(fileObj.get("FILE_CL_EXTEN").toString());
			//rtnVal.setFileClExten("jpg");
			
//			rtnVal.setFileIcType(fileObj.get("FILE_IC_TYPE").toString());
//			rtnVal.setFileIc(fileObj.get("FILE_IC").toString());
//			rtnVal.setFileIcExten(fileObj.get("FILE_IC_EXTEN").toString());
			//rtnVal.setFileIcExten("jpg");
			
			rtnVal.setFileCopyBankType(fileObj.get("FILE_COPY_BANK_TYPE").toString());
			rtnVal.setFileCopyBank(fileObj.get("FILE_COPY_BANK").toString());
			rtnVal.setFileCopyBankExten(fileObj.get("FILE_COPY_BANK_EXTEN").toString());
			//rtnVal.setFileCopyBankExten("jpg");
			
			rtnVal.setTaxReturn(fileObj.get("TAX_RETURN").toString());
		}
		*/
		
		if(checkJsonFieldSender("RETURN_INFO_INSERT","POST",senderObj)) {
			rtnVal.setState("A000");
		}
		
		System.out.println(dataObj.get("ITEM"));
		
		JSONArray itemArray = new JSONArray(dataObj.get("ITEM").toString());
		for(int i = 0; i< itemArray.length(); i++) {
			ReturnRequestItemVO tempItem = new ReturnRequestItemVO();
			JSONObject itemInfo = itemArray.getJSONObject(i);
			chkResult = checkJsonField("RETURN_INFO_INSERT","POST","item",itemInfo);
			if(chkResult.length()!=0) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG",chkResult);
				errors.put("STATUS_CODE","LE001");
				return errors;
			}
			
			System.out.println(itemInfo.get("ITEM_WTA"));
			tempItem.setNno(nno);
			tempItem.setSubNo(Integer.parseInt(itemInfo.get("SUB_NO").toString()));
			tempItem.setUserId(dataObj.get("SELLER_ID").toString());
			tempItem.setOrgStation("082");
			tempItem.setItemDetail(itemInfo.get("ITEM_DETAIL").toString());
			tempItem.setBrand(itemInfo.get("BRAND").toString());
			if (itemInfo.get("ITEM_WTA").toString() == "") {
				tempItem.setItemWta(0);
			} else {
				tempItem.setItemWta(Double.parseDouble(itemInfo.get("ITEM_WTA").toString()));
			}
			
			if (itemInfo.get("WT_UNIT").toString() == "") {
				tempItem.setWtUnit("");
			} else {
				tempItem.setWtUnit(itemInfo.get("WT_UNIT").toString());	
			}
			
			if (itemInfo.get("ITEM_CNT").toString() == "") {
				tempItem.setItemCnt(1);
			} else {
				tempItem.setItemCnt(Integer.parseInt(itemInfo.get("ITEM_CNT").toString()));	
			}
			
			if (itemInfo.get("UNIT_VALUE").toString() == "") {
				tempItem.setUnitValue(0);
			} else {
				tempItem.setUnitValue(Integer.parseInt(itemInfo.get("UNIT_VALUE").toString()));
			}

			/*
			if(!dataObj.has("ITEM_WTA")) {
				tempItem.setItemWta(0);
			} else {
				tempItem.setItemWta(Double.parseDouble(itemInfo.get("ITEM_WTA").toString()));
			}
			
			if(!dataObj.has("WT_UNIT")) {
				tempItem.setWtUnit("");
			} else {
				tempItem.setWtUnit(itemInfo.get("WT_UNIT").toString());
			}
			*/
			
			tempItem.setUnitCurrency(itemInfo.get("UNIT_CURRENCY").toString());
			tempItem.setMakeCntry(itemInfo.get("MAKE_CNTRY").toString());
			tempItem.setMakeCom(itemInfo.get("MAKE_COM").toString());
			tempItem.setHsCode(itemInfo.get("HS_CODE").toString());
			tempItem.setItemUrl(itemInfo.get("ITEM_URL").toString());
			tempItem.setItemImgUrl(itemInfo.get("ITEM_IMG_URL").toString());
			rtnItemVal.add(tempItem);
			
		}
		
		errors.put("MSG", "Return Order Request has been received");
		errors.put("STATUS", "SUCCESS");
		errors.put("ORDER_REFERENCE", orderReference);
		errors.put("rtnVal", rtnVal);
		errors.put("rtnItemVal", rtnItemVal);
		
		
		return errors;
	}
	
	public String checkJsonField(String apiName, String method, String section, JSONObject object) throws Exception {
		ArrayList<HashMap<String,String>> jsonField = new ArrayList<HashMap<String,String>>();
		String rtnVal = "";
		jsonField = selectJsonField(apiName,method,section);
		
		for(int i = 0; i < jsonField.size(); i++) {
			String jsonName = jsonField.get(i).get("jsonName");
			if(!object.has(jsonName)) {
				object.put(jsonName, "");

				//rtnVal = jsonName + " is required. or "+jsonName+" value is null.";
			}else {
				if(object.get(jsonName).toString().equals("null")) {
					object.put(jsonName,"");
				}
				if(jsonField.get(i).get("regex").equals("pass")) {
					continue;
				}else if(jsonField.get(i).get("regex").equals("zipRegex")) {
					//String pattern = "^[a-zA-Z0-9]*$";	// 영문자
					String pattern = "^[0-9]*$"; //숫자만
					String val = object.get(jsonName).toString();
					Boolean regex = Pattern.matches(pattern, val);
					if(!regex) {
						rtnVal = jsonName + " is invalid value.";
						break;
					}
				}else if(jsonField.get(i).get("regex").equals("nationRegex")) {
					String val = object.get(jsonName).toString();
					boolean nationTF = mapper.selectNation(val);
					if(!nationTF) {
						rtnVal = jsonName + " is not in ISO 3166-1 Alpha-2 value.";
						break;
					}
				}else if(jsonField.get(i).get("regex").equals("dateRegex")) {
					String orderDatePattern = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";  // 날짜 8자리
					String val = object.get(jsonName).toString();
					Boolean regex = Pattern.matches(orderDatePattern, val);
					if(!regex) {
						rtnVal = jsonName + " is invalid value.";
						break;
					}
				}
//				else if(jsonField.get(i).get("regex").equals("telRegex")) {
//					String telVal = object.get(jsonName).getAsString().replaceAll("[^0-9]", "");
//					
//					
//				}else if(jsonField.get(i).get("regex").equals("hpRegex")) {
//					
//				}
				else if(jsonField.get(i).get("regex").equals("emailRegex")) {
					System.out.println("email check");
					String senderEmailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 정규식
					String val = object.get(jsonName).toString();
					Boolean regex = Pattern.matches(senderEmailPattern, val);
					
					if (val.length() == 0) {
						object.put(jsonName, "");
					} else if (val.length() != 0) {
						if(!regex) {
							rtnVal = jsonName + " is invalid value.";
							break;
						}
					}
				}else if(jsonField.get(i).get("regex").equals("intRegex")) {
					try {
						int check = Integer.parseInt(object.get(jsonName).toString());
					}catch (Exception e) {
						// TODO: handle exception
						rtnVal = jsonName + " is not Integer.";
						break;
					}
				}else if(jsonField.get(i).get("regex").equals("doubleRegex")) {
					try {
						double check = Double.parseDouble(object.get(jsonName).toString());
					}catch (Exception e) {
						// TODO: handle exception
						rtnVal = jsonName + " is not float.";
						break;
					}
				}else if(jsonField.get(i).get("regex").equals("taxType")) {
					String val = object.get(jsonName).toString();
					if(val.equals("")) {
						rtnVal = jsonName + " is Empty.";
						break;
					}
				}
				
			}
		}
		return rtnVal;
	}
	
	public boolean checkJsonFieldSender(String apiName, String method, JSONObject object) throws Exception {
		ArrayList<HashMap<String,String>> jsonField = new ArrayList<HashMap<String,String>>();
		jsonField = selectJsonField(apiName,method,"sender");
		for(int i = 0; i < jsonField.size(); i++) {
			String jsonName = jsonField.get(i).get("jsonName");
			if(object.get(jsonName).toString().equals("null")) {
				object.put(jsonName,"");
			}
			if(object.get(jsonName).equals("")) {
				return true;
			}
		}
		return false;
	}
	
	
	@Transactional(rollbackFor= Exception.class) 
	public HashMap<String, Object> insertOrderRequestData(HashMap<String, Object> errors) throws Exception {
		// TODO Auto-generated method stub
		ReturnRequestVO rtnVal = new ReturnRequestVO();
		ArrayList<ReturnRequestItemVO> rtnItemVal = new ArrayList<ReturnRequestItemVO>();
		boolean fileError = false;
		String fileName ="";
		String errorCode = "";
		rtnVal = (ReturnRequestVO)errors.get("rtnVal");
		rtnItemVal = (ArrayList<ReturnRequestItemVO>)errors.get("rtnItemVal");
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		try {
			String userId = rtnVal.getUserId();
			String nno = rtnVal.getNno();
			
			if(rtnVal.getTaxType().equals("Y")) {
				HashMap<String,Object> s3FileResult = new  HashMap<String,Object>();
				s3FileResult=s3FileInsert("FR", rtnVal.getFileReasonType(), userId, nno, rtnVal.getFileReason(),rtnVal.getFileReasonExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileReason("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FR."+rtnVal.getFileReasonExten());
				}else {
					fileName="ReasonFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FC", rtnVal.getFileCaptureType(), userId, nno, rtnVal.getFileCapture(),rtnVal.getFileCaptureExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCapture("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FC."+rtnVal.getFileCaptureExten());
				}else {
					fileName="CaptureFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FM", rtnVal.getFileMessengerType(), userId, nno, rtnVal.getFileMessenger(),rtnVal.getFileMessengerExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileMessenger("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FM."+rtnVal.getFileMessengerExten());
				}else {
					fileName="MessengerFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FCL", rtnVal.getFileClType(), userId, nno, rtnVal.getFileCl(),rtnVal.getFileClExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCl("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FCL."+rtnVal.getFileClExten());
				}else {
					fileName="ClFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
//				s3FileResult=s3FileInsert("FIC", rtnVal.getFileIcType(), userId, nno, rtnVal.getFileIc(),rtnVal.getFileIcExten());
//				if((boolean)s3FileResult.get("STATUS")) {
//					rtnVal.setFileIc("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FIC."+rtnVal.getFileReasonExten());
//				}else {
//					fileName="IcFile";
//					fileError = true;
//					if(s3FileResult.containsKey("URL_STATUS")) {
//						errorCode = s3FileResult.get("URL_STATUS").toString();
//					}
//					throw new Exception();
//				}
				
				s3FileResult=s3FileInsert("FCB", rtnVal.getFileCopyBankType(), userId, nno, rtnVal.getFileCopyBank(),rtnVal.getFileCopyBankExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCopyBank("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FCB."+rtnVal.getFileCopyBankExten());
				}else {
					fileName="BankCopy";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
			}
			insertReturnRequest(rtnVal);
			if(rtnVal.getState().equals("A002")) {
				mgrRtnService.updateOrderRegistInReturn(rtnVal.getState(), rtnVal.getOrderReference());
			}
			
			for(int i =0; i < rtnItemVal.size(); i++) {
				rtnItemVal.get(i).setWUserId(rtnVal.getWUserId());
				rtnItemVal.get(i).setWUserIp(rtnVal.getWUserIp());
				insertReturnRequestItem(rtnItemVal.get(i));
			}
			//s3FileInsert(String imgNameVal, String types, String userId, String nno, String fileVal)
			//webhookController.returnRequestWebHook(rtnVal.getOrderReference(), rtnVal.getRegNo(), rtnVal.getKoblNo());
		}catch (Exception e) {
			// TODO: handle exception
			comnService.deleteRegNo(rtnVal.getRegNo());
			errors = new HashMap<String,Object>();
			if (fileError) {
				errors.put("STATUS", "FAIL");
				if(!errorCode.equals("")) {
					errors.put("ERROR_MSG", "FILE ERROR. Please Check file data.("+fileName+"), Http "+errorCode+" Error.");
				}else {
					errors.put("ERROR_MSG", "FILE ERROR. Please Check file data.("+fileName+")");
				}
			}
			else if(e.getCause().getMessage().contains("PRIMARY KEY constraint")) {
				errors.put("STATUS", "FAIL");
				rtnVal.setRegNo(comnService.selectLegacyRegNo(rtnVal));
				errors.put("ERROR_MSG", "Already insert data.(SELLER ID - KOBL NO)");
				errors.put("STATUS_CODE", "NE002");
			} else if(e.getCause().getMessage().contains("UNIQUE KEY constraint")) {
				errors.put("STATUS", "FAIL");
				rtnVal.setRegNo(comnService.selectLegacyRegNo(rtnVal));
				errors.put("ERROR_MSG", "Already insert data.(ORDER REFERENCE)");
				errors.put("STATUS_CODE", "NE002");
			}
			else {
				errors.put("STATUS", "FAIL");
				errors.put("ERROR_MSG", "SQL ERROR. Please Check data. or contact manager.");
				errors.put("STATUS_CODE", "SE001");
			}
		}finally {
			errors.remove("rtnVal");
			errors.remove("rtnItemVal");
			errors.put("REG_NO", rtnVal.getRegNo());
		}
		
		return errors;
	}
	
	@Override
	public HashMap<String, Object> updateOrderRequestData(HashMap<String, Object> errors) throws Exception {
		// TODO Auto-generated method stub
		ReturnRequestVO rtnVal = new ReturnRequestVO();
		ArrayList<ReturnRequestItemVO> rtnItemVal = new ArrayList<ReturnRequestItemVO>();
		boolean fileError = false;
		String fileName ="";
		String errorCode = "";
		rtnVal = (ReturnRequestVO)errors.get("rtnVal");
		rtnItemVal = (ArrayList<ReturnRequestItemVO>)errors.get("rtnItemVal");
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		try {
			String userId = rtnVal.getUserId();
			String nno = rtnVal.getNno();
			//s3FileInsert(String imgNameVal, String types, String userId, String nno, String fileVal)
			if(rtnVal.getTaxType().equals("Y")) {
				HashMap<String,Object> s3FileResult = new  HashMap<String,Object>();
				s3FileResult=s3FileInsert("FR", rtnVal.getFileReasonType(), userId, nno, rtnVal.getFileReason(),rtnVal.getFileReasonExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileReason("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FR");
				}else {
					fileName="ReasonFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FC", rtnVal.getFileCaptureType(), userId, nno, rtnVal.getFileCapture(),rtnVal.getFileCaptureExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCapture("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FC");
				}else {
					fileName="CaptureFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FM", rtnVal.getFileMessengerType(), userId, nno, rtnVal.getFileMessenger(),rtnVal.getFileMessengerExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileMessenger("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FM");
				}else {
					fileName="MessengerFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FCL", rtnVal.getFileClType(), userId, nno, rtnVal.getFileCl(),rtnVal.getFileClExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCl("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FCL");
				}else {
					fileName="ClFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FIC", rtnVal.getFileIcType(), userId, nno, rtnVal.getFileIc(),rtnVal.getFileIcExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileIc("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FIC");
				}else {
					fileName="IcFile";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
				
				s3FileResult=s3FileInsert("FCB", rtnVal.getFileCopyBankType(), userId, nno, rtnVal.getFileCopyBank(),rtnVal.getFileCopyBankExten());
				if((boolean)s3FileResult.get("STATUS")) {
					rtnVal.setFileCopyBank("http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+nno+"_"+"FCB");
				}else {
					fileName="BankCopy";
					fileError = true;
					if(s3FileResult.containsKey("URL_STATUS")) {
						errorCode = s3FileResult.get("URL_STATUS").toString();
					}
					throw new Exception();
				}
			}
			//insertReturnRequest
			updateReturnRequest(rtnVal);
			for(int i =0; i < rtnItemVal.size(); i++) {
				rtnItemVal.get(i).setWUserId(rtnVal.getWUserId());
				rtnItemVal.get(i).setWUserIp(rtnVal.getWUserIp());
				updateReturnRequestItem(rtnItemVal.get(i));
			}
		}catch (Exception e) {
			// TODO: handle exception
			errors = new HashMap<String,Object>();
			if(e.getCause().getMessage().contains("PRIMARY KEY constraint")) {
				errors.put("STATUS", "FAIL");
				errors.put("ERROR_MSG", "Already insert data.");
				errors.put("STATUS_CODE", "NE002");
			}else {
				errors.put("STATUS", "FAIL");
				errors.put("ERROR_MSG", "SQL ERROR. Please Check data. or contact manager.");
				errors.put("STATUS_CODE", "SE001");
			}
		}finally {
			errors.remove("rtnVal");
			errors.remove("rtnItemVal");
			errors.put("REG_NO", rtnVal.getRegNo());
		}
		
		return errors;
	}

	@Override
	public HashMap<String,Object> checkApiOrderInfo(Map<String,Object> jsonHeader, HttpServletRequest request) throws Exception{
		HashMap<String,Object> temp = new HashMap<String, Object>();
	
		String apiUserId = jsonHeader.get("userid").toString();
		String userKey = apiService.selectUserKey(jsonHeader.get("userid").toString());	
		
		temp.put("STATUS", "SUCCESS");
		if(orderInfoChkStatus.equals("on")) {
			try {
//				if(!jsonHeader.get("content-type").equals("application/json")){
//					temp.put("ERROR_MSG","In case content type is not JSON type");
//					temp.put("STATUS_CODE","HE001");
//					temp.put("STATUS", "FAIL");
//					return temp;
//				}
//				if(!request.isSecure()){
//					temp.put("Error_Msg","In case HTTPS is not connected");
//					temp.put("Status_Code","S20");
//		  		temp.put("Order_No","-");
//					return temp;
//				}
				
				String ipChk = apiService.selectUserAllowIp(apiUserId, request.getRemoteAddr());
		
		 		if(!ipChk.equals("TRUE")) {
		 			temp.put("ERROR_MSG","In case CONNECT IP is not allow Ip");
		 			temp.put("STATUS_CODE","HE003");
					temp.put("STATUS", "FAIL");
		 			return temp;
		 		}
			
				String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
				String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");

				if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
					temp.put("ERROR_MSG","KEY IS NOT WORKING");
		 			temp.put("STATUS_CODE","AE001");
					temp.put("STATUS", "FAIL");
					return temp;
				}
				String from = apiKeyDecyptList[0].toLowerCase();
				
				boolean regex = Pattern.matches(YYYYMMDDHHMISS, from);
				
				if(!regex) {
					temp.put("ERROR_MSG","KEY IS NOT WORKING");
					temp.put("STATUS_CODE","AE002");
					temp.put("STATUS", "FAIL");
					return temp;
				}
				
				LocalDateTime date1 = LocalDateTime.parse(from ,DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ));
				LocalDateTime currntTime = LocalDateTime.now();
				if(date1.plusMinutes(5).isBefore(currntTime)) {
					temp.put("ERROR_MSG","Transfer Time Exceeded");
					temp.put("STATUS_CODE","AE003");
					temp.put("STATUS", "FAIL");
					return temp;
				}
			}catch(IllegalBlockSizeException e) {
				temp.put("ERROR_MSG","Crypto ERROR");
				temp.put("STATUS_CODE","HE004");
				temp.put("STATUS", "FAIL");
				return temp; 
			}catch (IllegalArgumentException e) {
				logger.error("Exception", e);
				System.out.println(e.toString());
				temp.put("ERROR_MSG","ILLEGAL ARGUMENT ERROR");
				temp.put("STATUS_CODE","HE005");
				temp.put("STATUS", "FAIL");
				return temp; 
			}catch (ArrayIndexOutOfBoundsException e) {
				logger.error("Exception", e);
				temp.put("ERROR_MSG","INDEX OUT OF RANGE ERROR");
				temp.put("STATUS_CODE","HE006");
				temp.put("STATUS", "FAIL");
				return temp; 
			}
			catch (Exception e) {
				// TODO: handle exception
				temp.put("ERROR_MSG","Exception Error");
				temp.put("STATUS_CODE","DE001");
				temp.put("STATUS", "FAIL");
				return temp; 
			}
		}
		return temp; 
	}

	@Override
	public void insertApiconn(HashMap<String, Object> errors, Map<String, Object> jsonHeader,
			Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jsonData.toString());
		parameters.put("wUserId",jsonHeader.get("userid").toString());
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", errors.toString());
		apiService.insertApiConn(parameters);
	}
	
	@Override
	public HashMap<String, Object> getEshopItem(Map<String, Object> jsonHeader,  String eshipId, String blNo, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = checkApiOrderInfo(jsonHeader, request);
		if(errors.get("STATUS").equals("FAIL")){
			return errors;
		}
		HttpResponse response =null;
		String outResult = new String();
		JsonParser parse = new JsonParser();
		String requestURL = "https://acieshop.com/eshopapi/getBlItem.php?BL="+blNo;
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(requestURL);
		getRequest.setHeader("Content-Type", "application/json");
		response = client.execute(getRequest);
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			outResult = handler.handleResponse(response);
			JsonElement element = parse.parse(outResult);
			if(element.getAsJsonObject().get("bl").isJsonNull()) {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG","KOBL_NO is invalid data.");
				errors.put("STATUS_CODE","NE001");
			}else {
				errors.put("STATUS","SUCCESS");
				errors.put("SELLER_ID",eshipId);
				errors.put("KOBL_NO",blNo);
				ArrayList<HashMap<String,Object>> itemList = new ArrayList<HashMap<String,Object>>();
				JsonArray eshopItemList = element.getAsJsonObject().get("items").getAsJsonArray();
				for(int i = 0; i < eshopItemList.size(); i ++) {
					HashMap<String,Object> item= new HashMap<String,Object>();
					item.put("SUB_NO", eshopItemList.get(i).getAsJsonObject().get("sub_no").getAsInt());
					item.put("ITEM_DETAIL", eshopItemList.get(i).getAsJsonObject().get("item_detail").getAsString());
					item.put("BRAND", eshopItemList.get(i).getAsJsonObject().get("item_brand").getAsString());
					item.put("ITEM_CNT", eshopItemList.get(i).getAsJsonObject().get("unit_cnt").getAsInt());
					item.put("UNIT_VALUE", eshopItemList.get(i).getAsJsonObject().get("unit_value").getAsDouble());
					item.put("UNIT_CURRENCY", "USD");
					item.put("MAKE_CNTRY", eshopItemList.get(i).getAsJsonObject().get("make_country").getAsString());
					item.put("MAKE_COM", eshopItemList.get(i).getAsJsonObject().get("make_company").getAsString());
					item.put("HS_CODE", eshopItemList.get(i).getAsJsonObject().get("hs_code").getAsString());
					item.put("ITEM_URL", "");
					item.put("ITEM_IMG_URL", "");
					itemList.add(item);
				}
				errors.put("ITEM_INFO",itemList);
			}
		}else {
			errors.put("STATUS","FAIL");
			errors.put("ERROR_MSG","Server Error. Ask Manager");
			errors.put("STATUS_CODE","DE001");
		}
		return errors;
	}

	@Override
	public HashMap<String,Object> s3FileInsert(String imgNameVal, String types, String userId, String nno, String fileVal, String fileExten) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> rtnVal = new HashMap<String,Object>();
		try {
			FileOutputStream fos = null;
			InputStream is = null;
			String imageDir = realFilePath + "image/return/";
			File file = new File(imageDir);
			if (!file.isDirectory()) {
				file.mkdir();
			}
			fos = new FileOutputStream(imageDir+nno+"_"+imgNameVal+"."+fileExten);
			file = new File(imageDir+nno+"_"+imgNameVal+"."+fileExten);
			if(types.equals("U")||types.equals("URL")) {
				URL url = new URL(fileVal);
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				is = urlConnection.getInputStream();
				byte[] buffer = new byte[1024];
				int readBytes;
				while((readBytes = is.read(buffer)) != -1) {
					fos.write(buffer,0,readBytes);
				}
				fos.close();
				is.close();
			}else if (types.equals("F")||types.equals("FILE")) {
				byte[] decodedBase64 = Base64.decodeBase64(fileVal);
				fos.write(decodedBase64);
				fos.close();
			}
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/return/"+year+"/"+userId+"/"+nno, nno+"_"+imgNameVal+"."+fileExten, file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			//file.delete();
			rtnVal.put("STATUS", true);
			return rtnVal;
		}catch (Exception e) {
			// TODO: handle exception
			if(e.getMessage().contains("403")) {
				rtnVal.put("URL_STATUS", "403");
			} 
			rtnVal.put("STATUS", false);
			return rtnVal;  
		}
	}
	
	public void updateReturnRequest(ReturnRequestVO rtnVal) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateReturnRequest(rtnVal);
	}
	
	public void updateReturnRequestItem(ReturnRequestItemVO returnRequestItemVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateReturnRequestItem(returnRequestItemVO);
	}
	
	@Override
	public ArrayList<HashMap<String,String>> selectJsonField(String apiName, String method, String section) throws Exception {
		return mapper.selectJsonField(apiName,method,section);
	}

	@Override
	public void insertReturnRequest(ReturnRequestVO rtnVal) throws Exception {
		// TODO Auto-generated method stub
		rtnVal.setOrderType("RETURN_API");
		rtnVal.setRegNo(comnService.selectNewRegNo(rtnVal));
		mapper.insertReturnRequest(rtnVal);
	}

	@Override
	public void insertReturnRequestItem(ReturnRequestItemVO rtnItemVal) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertReturnRequestItem(rtnItemVal);
	}

	@Override
	public HashMap<String, Object> selectReturnSttatus(HttpServletRequest request, @RequestHeader Map<String,Object> jsonHeader, String parameters, String valueKinds) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> errors = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		errors = checkApiOrderInfo(jsonHeader, request);
		

		if(errors.get("STATUS").equals("FAIL")){
			return errors;
		}
		HashMap<String, Object> requesetInfo= new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameterMap= new HashMap<String, Object>();
		HashMap<String, Object> jsonVal = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> statusList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> status = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> podStatusList = new ArrayList<LinkedHashMap<String, Object>>();
		
		if(valueKinds.equals("ORDER_REFERENCE")) {
			parameterMap.put("orderReference", parameters);
		}else if(valueKinds.equals("REG_NO")) {
			parameterMap.put("regNo", parameters);
		}else if(valueKinds.equals("KOBL_NO")) {
			parameterMap.put("koblNo", parameters);
		}
		
		String sellerId = comnService.selectReturnRequestId(parameterMap);
		parameterMap.put("sellerId", sellerId);
		requesetInfo = comnService.selectReturnRequestStatus(parameterMap);
		//String userKey = apiV1Service.selectUserKey(sellerId);
		
		jsonVal.put("ORDER_NUMBER",requesetInfo.get("ORDER_NUMBER"));
		jsonVal.put("ORDER_REFERENCE",requesetInfo.get("ORDER_REFERENCE"));
		jsonVal.put("KOBL_NO",requesetInfo.get("KOBL_NO"));
		jsonVal.put("REG_NO",requesetInfo.get("REG_NO"));
		if(requesetInfo.get("RETURN_STATUS").equals("A000")) {
			status.put("STATUS_CODE","A000");
			status.put("STATUS_DESC","1차 접수");
			status.put("STATUS_DATE",requesetInfo.get("W_DATE"));
			statusList.add(status);
		}else {
			status.put("RETURN_STATUS","A001");
			status.put("STATUS_NAME","접수 신청");
			status.put("DATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(requesetInfo.get("W_DATE")));
			//status.put("DATE",requesetInfo.get("W_DATE"));
			statusList.add(status);
		}
		
		//접수 완료
		if(!requesetInfo.get("W_RECEIVE_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS","A002");
			status.put("STATUS_NAME","접수 완료");
			status.put("DATE",requesetInfo.get("W_RECEIVE_DATE"));
			statusList.add(status);
		}
		//수거 중
		if(!requesetInfo.get("W_START_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS","B001");
			status.put("STATUS_NAME","수거 중");
			status.put("DATE",requesetInfo.get("W_START_DATE"));
			statusList.add(status);
		}
		
		//입고
		if(!requesetInfo.get("WH_IN_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS","C001");
			status.put("STATUS_NAME","입고");
			status.put("DATE",requesetInfo.get("WH_IN_DATE"));
			statusList.add(status);
		}
		
		//입고 완료 출고 대기중
		if(!requesetInfo.get("STOCK_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			if(requesetInfo.get("WH_STATUS").equals("WO")) {
				status.put("RETURN_STATUS","C003");
				status.put("STATUS_NAME","출고 대기");
				status.put("DATE",requesetInfo.get("STOCK_DATE"));
				statusList.add(status);
				
				LinkedHashMap<String, Object> D004List2 = new LinkedHashMap<String, Object>();
				
				ArrayList<LinkedHashMap<String,Object>> stockFile = new ArrayList<LinkedHashMap<String,Object>>();
				ArrayList<LinkedHashMap<String,Object>> rtnStockFile = new ArrayList<LinkedHashMap<String,Object>>();
				
				stockFile = comnService.selectStockFile(requesetInfo.get("NNO").toString());
				
				D004List2.put("D004_DETAIL", "");
				D004List2.put("D004_CODE", "");
				
				for(int fileIndex = 0; fileIndex < stockFile.size(); fileIndex++) {
					LinkedHashMap<String,Object> temp = new LinkedHashMap<String,Object>();
					temp.put("SUB_NO", stockFile.get(fileIndex).get("SUB_NO").toString());
					temp.put("IMG_URL", "http://"+stockFile.get(fileIndex).get("FILE_DIR").toString());
					rtnStockFile.add(temp);
				}
				
				D004List2.put("RETURN_IMG", rtnStockFile);
				jsonVal.put("RETURN_IMG_LIST",D004List2);
				
				
			}else if (requesetInfo.get("WH_STATUS").equals("WF")) {
				status.put("RETURN_STATUS","D004");
				status.put("STATUS_NAME","검수 불합격");
				status.put("DATE",requesetInfo.get("STOCK_DATE"));
				statusList.add(status);
				
				LinkedHashMap<String, Object> D004List2 = new LinkedHashMap<String, Object>();
				LinkedHashMap<String,Object> temp = new LinkedHashMap<String,Object>();
				ArrayList<LinkedHashMap<String,Object>> stockFile = new ArrayList<LinkedHashMap<String,Object>>();
				ArrayList<LinkedHashMap<String,Object>> rtnStockFile = new ArrayList<LinkedHashMap<String,Object>>();
				
				temp = comnService.selectStockMsg(requesetInfo.get("NNO").toString());
				stockFile = comnService.selectStockFile(requesetInfo.get("NNO").toString());
				
				D004List2.put("D004_DETAIL", temp.get("WH_MEMO"));
				D004List2.put("D004_CODE", temp.get("STATUS"));
				
				for(int fileIndex = 0; fileIndex < stockFile.size(); fileIndex++) {
					temp = new LinkedHashMap<String,Object>();
					temp.put("SUB_NO", stockFile.get(fileIndex).get("SUB_NO").toString());
					temp.put("IMG_URL", "http://"+stockFile.get(fileIndex).get("FILE_DIR").toString());
					rtnStockFile.add(temp);
				}
				
				D004List2.put("RETURN_IMG", rtnStockFile);
				jsonVal.put("RETURN_IMG_LIST",D004List2);
				
			}
			
			
		}
		if(!requesetInfo.get("ACCEPT_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS","C004");
			status.put("STATUS_NAME","출고 승인");
			status.put("DATE",requesetInfo.get("ACCEPT_DATE"));
			statusList.add(status);
		}
		
		if(!requesetInfo.get("DEL_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS","D005");
			status.put("STATUS_NAME","폐기");
			status.put("DATE",requesetInfo.get("DEL_DATE"));
			statusList.add(status);
		}
		
		try {
		
		if(!requesetInfo.get("ORDER_DATE").equals("")) {
			status = new LinkedHashMap<String, Object>();
			status.put("RETURN_STATUS", "C002");
			status.put("STATUS_NAME", "출고");
			status.put("DATE", requesetInfo.get("ORDER_DATE"));
			statusList.add(status);
			
			HashMap<String, Object> bl = new HashMap<String, Object>();
			bl.put("BL", requesetInfo.get("HAWB_NO"));
			
			LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> podDetatil = new LinkedHashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray = new ArrayList<HashMap<String, Object>>();
			
			String hawbNo = (String) bl.get("BL");
			if(hawbNo.equals("") || hawbNo == null) {
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				podStatusList.add(RstHashMap);
			}
			
			HashMap<String, Object> BlInfo = new HashMap<String, Object>();
			
			BlInfo = mapper.selectPodBlInfo(hawbNo);
			if(BlInfo == null) {
				podDetatil = new LinkedHashMap<String, Object>();
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TRACE_STATUS", podDetatailArray);
				podStatusList.add(RstHashMap);
			}
			
			RstHashMap.put("HAWB_NO", BlInfo.get("HAWB_NO"));
			RstHashMap.put("ORDER_NO", BlInfo.get("ORDER_NO"));
			RstHashMap.put("CONSIGNEE", BlInfo.get("CNEE_NAME"));
			RstHashMap.put("WH_DATE", BlInfo.get("WH_IN_DATE"));
			RstHashMap.put("SHIPPING_DATE", BlInfo.get("DEP_DATE"));
			if (BlInfo.get("DeliveryCompany").equals("ACI-동남아,일본")) {
				RstHashMap.put("TRANS_COM", "YSL");	
			} else {
				RstHashMap.put("TRANS_COM", BlInfo.get("DeliveryCompany"));
			}
			
			String getTransCode = "";
			getTransCode = (String) BlInfo.get("TRANS_CODE");
			
			String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
			String mawbInDate = apiMapper.selectMawbInDate(hawbNo);		// 출고
			String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
			
			if (getTransCode.equals("ARA")) {
				TrackingClientInfo clientInfo = new TrackingClientInfo();
				clientInfo.setAccountCountryCode("KR");
				clientInfo.setAccountEntity("SEL");
				clientInfo.setAccountNumber("172813");
				clientInfo.setAccountPin("321321");
				clientInfo.setUserName("overseas2@aciexpress.net");
				clientInfo.setPassword("Aci5606!");
				clientInfo.setVersion("1.0");

				net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
				transaction.setReference1("001");

				TrackingShipmentRequest trakShip = new TrackingShipmentRequest();

				trakShip.setClientInfo(clientInfo);
				trakShip.setTransaction(transaction);

				String[] trakBL = new String[1];
				trakBL[0] = hawbNo;

				trakShip.setShipments(trakBL);

				TrackingShipmentResponse rtnTracking = new TrackingShipmentResponse();
				rtnTracking = trackingProxy.trackShipments(trakShip);
				TrackingResult[] trkRst = new TrackingResult[0];

				if (rtnTracking.getTrackingResults().length == 0) {
					podDetatil = new LinkedHashMap<String, Object>();
					podDetatil.put("UpdateCode", "-200");
					podDetatil.put("UpdateDateTime", "");
					podDetatil.put("UpdateLocation", "");
					podDetatil.put("UpdateDescription", "No Data.");
					podDetatailArray.add(podDetatil);
				} else {
					trkRst = rtnTracking.getTrackingResults()[0].getValue();
					for (int i = 0; i < trkRst.length; i++) {
						podDetatil = new LinkedHashMap<String, Object>();
						if (trkRst[i].getUpdateCode().toString().equals("SH005")) {
							podDetatil.put("UpdateCode", "600");
							podDetatil.put("UpdateDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
							podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
							podDetatil.put("UpdateDescription", "Delivered");
						} else if (trkRst[i].getUpdateCode().toString().equals("SH003")) {
							podDetatil.put("UpdateCode", "500");
							podDetatil.put("UpdateDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
							podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
							podDetatil.put("UpdateDescription", "Out for Delivery");
						} else if (trkRst[i].getUpdateCode().toString().equals("SH022") && trkRst[i].getUpdateLocation().toString().equals("Dubai, United Arab Emirates")) {
							podDetatil.put("UpdateCode", "400");
							podDetatil.put("UpdateDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
							podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
							podDetatil.put("UpdateDescription", "Arrival in destination country");
						} else {
							continue;
						}
						podDetatailArray.add(podDetatil);
					}
					
					podDetatil = new LinkedHashMap<String, Object>();
					podDetatil.put("UpdateCode", "300");
					podDetatil.put("UpdateDateTime", mawbInDate.substring(0, mawbInDate.length() - 3));
					podDetatil.put("UpdateLocation", "Republic of Korea");
					podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
					podDetatailArray.add(podDetatil);
					
					podDetatil = new LinkedHashMap<String, Object>();
					podDetatil.put("UpdateCode", "200");
					podDetatil.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
					podDetatil.put("UpdateLocation", "Republic of Korea");
					podDetatil.put("UpdateDescription", "Finished warehousing");
					podDetatailArray.add(podDetatil);
					
					podDetatil = new LinkedHashMap<String, Object>();
					podDetatil.put("UpdateCode", "100");
					podDetatil.put("UpdateDateTime", regInDate);
					podDetatil.put("UpdateLocation", "Republic of Korea");
					podDetatil.put("UpdateDescription", "Order information has been entered");
					podDetatailArray.add(podDetatil);
				}
			} else if (getTransCode.equals("YSL")) {
				String rtnJson = ysApi.makeYoungSungPodEN(hawbNo);
				podDetatailArray = ysApi.makePodDetailForArray(rtnJson, hawbNo, request);
			} else if (getTransCode.equals("CJ")) {
				podDetatailArray = cjapi.makePodDetailForArray(hawbNo);
			} else if (getTransCode.equals("EMN")) {
				podDetatailArray = emsApi.makeEmsForPod(hawbNo);
			} else if (getTransCode.equals("ACI-T86")) {
				String rtnJson = t86Api.makeT86Pod(hawbNo);
				podDetatailArray = t86Api.makePodDetailArray(rtnJson, hawbNo, request);
			} else if (getTransCode.equals("FB")) {
				String rtnJson = fastboxApi.makeFastBoxPod(hawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, hawbNo, request);
			} else if (getTransCode.equals("FB-EMS")) {
				String rtnJson = fastboxApi.makeFastBoxPod(hawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, hawbNo, request);
			} else if (getTransCode.equals("HJ")) {
				String podType = "A";
				podDetatailArray = hjApi.makeHJPod(hawbNo, request, podType, apiUserId);
			}
			
			RstHashMap.put("TRACE_STATUS", podDetatailArray);

			podStatusList.add(RstHashMap);
			statusList.addAll(podStatusList);
			
		}
		
		
		//
		
		//
		
		jsonVal.put("STATUS_LIST",statusList);
		
		//test = comnService.selectReturnSttatus(parameters);
		//test.get("STATUS")
		
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return jsonVal;
	}

	@Override
	public HashMap<String, Object> acceptReturnRequest(String type, String parameters, String sellerId, Map<String, Object> jsonHeader, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> errors = new HashMap<String,Object>();
		HashMap<String,Object> parameterInfo = new HashMap<String,Object>();
		errors = checkApiOrderInfo(jsonHeader, request);
		
		if(errors.get("STATUS").equals("FAIL")){
			return errors;
		}
		
		parameterInfo.put("type", type);
		parameterInfo.put("parameters", parameters);
		parameterInfo.put("sellerId", sellerId);
		parameterInfo.put("wUserId", jsonHeader.get("userid").toString());
		try {
			int result = mapper.acceptReturnRequest(parameterInfo);
			if(result != 0) {
				errors.put("STATUS", "SUCCESS");
				errors.put("MSG", "승인되었습니다");
				errors.put("TYPE", type);
				errors.put("PARAMETER", parameters);	
			}else {
				errors.put("STATUS", "FAIL");
				errors.put("MSG", "승인 대상이 아닙니다.");
				errors.put("TYPE", type);
				errors.put("PARAMETER", parameters);
			}
			
		}catch (Exception e) {
			errors.put("STATUS", "FAIL");
			errors.put("ERROR_MSG", "SQL ERROR. Please Check data. or contact manager.");
			errors.put("STATUS_CODE", "SE001");
		}
		return errors;
	}

	@Override
	public int selectSellerIdChk(String sellerId) {
		// TODO Auto-generated method stub
		return mapper.selectSellerIdChk(sellerId);
	}

	@Override
	public HashMap<String, Object> selectSellerIdChkAprv(String sellerId) {
		// TODO Auto-generated method stub
		return mapper.selectSellerIdChkAprv(sellerId);
	}

	@Override
	public HashMap<String, Object> selectMsgHis(HashMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return mapper.selectMsgHis(parameters);
	}

	@Override
	public void insertMsgInfo(StockMsgVO msgInfo) {
		mapper.insertMsgInfo(msgInfo);
		
	}
	
	@Override
	public ArrayList<StockResultVO> selectStockResultVO2(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		ArrayList<StockResultVO> targetStockList = new ArrayList<StockResultVO>();
		try {
			
		
		ServerSocket ss = null;
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		targetStockList.addAll(mapper.selectStockResult(map));
		
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return targetStockList;
	}

	@Override
	public ArrayList<StockVO> selectStockByGrpIdx2(String groupIdx) {
		// TODO Auto-generated method stub
		return mapper.selectStockByGrpIdx2(groupIdx);
	}

	@Override
	public void updateMsgInfo(HashMap<String, Object> params) {
		mapper.updateMsgInfo(params);
		
	}

	@Override
	public ArrayList<StockMsgVO> selectMsg(HashMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return mapper.selectMsg(parameters);
	}

	@Override
	public int selectMsgCnt(HashMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return mapper.selectMsgCnt(parameters);
	}

	@Override
	public void deleteMsgInfo(HashMap<String, Object> params) {
		mapper.deleteMsgInfo(params);
		
	}

	@Override
	public int selectReturnStationAddrCnt(String stationName) {
		// TODO Auto-generated method stub
		return mapper.selectReturnStationAddrCnt(stationName);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnStationAddr(String stationName) {
		// TODO Auto-generated method stub
		return mapper.selectReturnStationAddr(stationName);
	}

	@Override
	public ArrayList<CustomerVO> selectUserList() {
		// TODO Auto-generated method stub
		return mapper.selectUserList();
	}

	@Override
	public HashMap<String, Object> selectEshopOrderInfo(HttpServletRequest request, String apiKey, String eshopId, String blNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}

	@Override
	public HashMap<String, Object> selectEshopOrderItem(String blNo) {
		HashMap<String,Object> errors = new HashMap<String,Object>();

		try {
			
			HttpResponse response =null;
			String outResult = new String();
			JsonParser parse = new JsonParser();
			String requestURL = "https://acieshop.com/eshopapi/getBlItem.php?BL="+blNo;
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpGet getRequest = new HttpGet(requestURL);
			getRequest.setHeader("Content-Type", "application/json");
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
				JsonElement element = parse.parse(outResult);
				if(element.getAsJsonObject().get("bl").isJsonNull()) {
					errors.put("STATUS","FAIL");
					errors.put("ERROR_MSG","KOBL_NO is invalid data.");
					errors.put("STATUS_CODE","NE001");
				}else {
					errors.put("STATUS","SUCCESS");
					errors.put("KOBL_NO",blNo);
					ArrayList<HashMap<String,Object>> itemList = new ArrayList<HashMap<String,Object>>();
					JsonArray eshopItemList = element.getAsJsonObject().get("items").getAsJsonArray();
					for(int i = 0; i < eshopItemList.size(); i ++) {
						HashMap<String,Object> item= new HashMap<String,Object>();
						item.put("SUB_NO", eshopItemList.get(i).getAsJsonObject().get("sub_no").getAsInt());
						item.put("ITEM_DETAIL", eshopItemList.get(i).getAsJsonObject().get("item_detail").getAsString());
						item.put("BRAND", eshopItemList.get(i).getAsJsonObject().get("item_brand").getAsString());
						item.put("ITEM_CNT", eshopItemList.get(i).getAsJsonObject().get("unit_cnt").getAsInt());
						item.put("UNIT_VALUE", eshopItemList.get(i).getAsJsonObject().get("unit_value").getAsDouble());
						item.put("UNIT_CURRENCY", "USD");
						item.put("MAKE_CNTRY", eshopItemList.get(i).getAsJsonObject().get("make_country").getAsString());
						item.put("MAKE_COM", eshopItemList.get(i).getAsJsonObject().get("make_company").getAsString());
						item.put("HS_CODE", eshopItemList.get(i).getAsJsonObject().get("hs_code").getAsString());
						item.put("ITEM_URL", "");
						item.put("ITEM_IMG_URL", "");
						itemList.add(item);
					}
					errors.put("ITEM_INFO",itemList);
				}
			}else {
				errors.put("STATUS","FAIL");
				errors.put("ERROR_MSG","Server Error. Ask Manager");
				errors.put("STATUS_CODE","DE001");
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
			
		return errors;
	}

	/* 반품 API */
	public LinkedHashMap<String, Object> checkReturnApiOrderInfo(Map<String, Object> jsonHeader, HttpServletRequest request) throws Exception {
		LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();
		
		String apiUserId = jsonHeader.get("userid").toString();
		String userKey = apiService.selectUserKey(apiUserId);
		
		rst.put("STATUS", "SUCCESS");
		
		try {
			String ipChk = apiService.selectUserAllowIp(apiUserId, request.getRemoteAddr());
			
			if (!ipChk.equals("TRUE")) {
				rst.put("ERROR_MSG","In case CONNECT IP is not allow Ip");
				rst.put("STATUS_CODE","HE003");
	 			rst.put("STATUS", "FAIL");
				rst.put("MSG", "");
				rst.put("ORDER_REFERENCE", "");
				rst.put("REG_NO", "");
	 			return rst;
			}
			
			String apiKeyDecrypt = AES256Cipher.AES_Decode((String) jsonHeader.get("apikey"), userKey);
			String[] apiKeyDecryptList = apiKeyDecrypt.split("[|]");
			
			if (!apiKeyDecryptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
				rst.put("ERROR_MSG", "KEY IS NOT WORKING");
				rst.put("STATUS_CODE", "AE001");
				rst.put("STATUS", "FAIL");
				rst.put("MSG", "");
				rst.put("ORDER_REFERENCE", "");
				rst.put("REG_NO", "");
				return rst;
			}
			
			String from = apiKeyDecryptList[0].toLowerCase();
			boolean regex = Pattern.matches(YYYYMMDDHHMISS, from);
			
			if (!regex) {
				rst.put("ERROR_MSG", "KEY IS NOT WORKING");
				rst.put("STATUS_CODE", "AE002");
				rst.put("STATUS", "FAIL");
				rst.put("MSG", "");
				rst.put("ORDER_REFERENCE", "");
				rst.put("REG_NO", "");
				return rst;
			}
			
			LocalDateTime date = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			LocalDateTime currentTime = LocalDateTime.now();
			if (date.plusMinutes(5).isBefore(currentTime)) {
				rst.put("ERROR_MSG", "Transfer Time Exceeded");
				rst.put("STATUS_CODE", "AE003");
				rst.put("STATUS", "FAIL");
				rst.put("MSG", "");
				rst.put("ORDER_REFERENCE", "");
				rst.put("REG_NO", "");
				return rst;
			}
		} catch(IllegalBlockSizeException e) {
			rst.put("ERROR_MSG","Crypto ERROR");
			rst.put("STATUS_CODE","HE004");
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "");
			rst.put("ORDER_REFERENCE", "");
			rst.put("REG_NO", "");
			return rst; 
		} catch (IllegalArgumentException e) {
			rst.put("ERROR_MSG","ILLEGAL ARGUMENT ERROR");
			rst.put("STATUS_CODE","HE005");
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "");
			rst.put("ORDER_REFERENCE", "");
			rst.put("REG_NO", "");
			return rst; 
		} catch (ArrayIndexOutOfBoundsException e) {
			rst.put("ERROR_MSG","INDEX OUT OF RANGE ERROR");
			rst.put("STATUS_CODE","HE006");
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "");
			rst.put("ORDER_REFERENCE", "");
			rst.put("REG_NO", "");
			return rst; 
		} catch (Exception e) {
			rst.put("ERROR_MSG","Exception Error");
			rst.put("STATUS_CODE","DE001");
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "");
			rst.put("ORDER_REFERENCE", "");
			rst.put("REG_NO", "");
			return rst; 
		}
		
		return rst;
	}
	
	@Override
	public LinkedHashMap<String, Object> setApiRequestVal(Map<String, Object> jsonHeader, Map<String, Object> jsonData,
			HttpServletRequest request) {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		ReturnOrderListVO returnOrder = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> returnItem = new ArrayList<HashMap<String,Object>>();
		HashMap<Object, Object> parameterInfo = new HashMap<Object, Object>();
		HashMap<String, Object> fileParams = new HashMap<String, Object>();
		
		try {
			rstMap = checkReturnApiOrderInfo(jsonHeader, request);
			
			if (rstMap.get("STATUS").equals("FAIL")) {
				return rstMap;
			}
			
			JSONObject dataObject = new JSONObject(jsonData);
			String userKey = apiService.selectUserKey(jsonHeader.get("userid").toString());
			parameterInfo.put("sellerId", dataObject.get("SELLER_ID").toString());
			int checkId = apiMapper.selectCheckId(parameterInfo);
			
			if (checkId == 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG", "SELLER_ID is invalid value");
				rstMap.put("STATUS_CODE", "LE002");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			String checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "array", dataObject);
			if (checkResult.length() != 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG", checkResult);
				rstMap.put("STATUS_CODE", "LE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			InvUserInfoVO invUserInfo = new InvUserInfoVO();
			invUserInfo = comnService.selectInvUserInfo(parameterInfo.get("sellerId").toString());
			invUserInfo.dncryptData();
			
			// 수취업체 정보
			String senderDecode = AES256Cipher.AES_Decode(dataObject.get("SENDER").toString(), userKey);
			// 발송인 정보
			String returnDecode = AES256Cipher.AES_Decode(dataObject.get("RETURN").toString(), userKey);
			// 파일 정보
			String fileDecode = AES256Cipher.AES_Decode(dataObject.get("FILE").toString(), userKey);
			
			String nno = "";
			String orderReference = "";
			String calculateId = "";
			String koblNo = "";
			
			checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "data", dataObject);
			if (checkResult.length() != 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG", checkResult);
				rstMap.put("STATUS_CODE", "LE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			if (request.getMethod().toString().toUpperCase().equals("POST")) {
				nno = comnService.selectNNO();
				
				if (dataObject.get("CALCULATE_ID").toString().equals("")) {
					calculateId = dataObject.get("SELLER_ID").toString();
				} else {
					calculateId = dataObject.get("CALCULATE_ID").toString();
				}
				if (dataObject.get("ORDER_REFERENCE").toString().equals("")) {
					orderReference = nno;
				} else {
					orderReference = dataObject.get("ORDER_REFERENCE").toString();
				}
				koblNo = dataObject.get("KOBL_NO").toString();
				
			} else if (request.getMethod().toString().toUpperCase().equals("PATCH")) {
				if(dataObject.get("CALCULATE_ID").toString().equals("")) {
					calculateId = dataObject.get("SELLER_ID").toString();
				}else {
					calculateId = dataObject.get("CALCULATE_ID").toString();
				}
				orderReference = dataObject.get("ORDER_REFERENCE").toString();
				koblNo = dataObject.get("KOBL_NO").toString();
				nno = mapper.selectNnoByETC(orderReference,calculateId,koblNo);
				if(nno == null) {
					rstMap = new LinkedHashMap<String, Object>();
					rstMap.put("ERROR_MSG","Unregistered Data");
					rstMap.put("STATUS_CODE","NE001");
					rstMap.put("STATUS","FAIL");
					rstMap.put("MSG", "");
					rstMap.put("ORDER_REFERENCE", "");
					rstMap.put("REG_NO", "");
					return rstMap;
				}
			}
			
			returnOrder.setNno(nno);
			returnOrder.setPickType(dataObject.optString("PICK_TYPE"));
			returnOrder.setState("A001");
			returnOrder.setOrgStation("082");
			returnOrder.setDstnNation(dataObject.optString("DSTN_NATION"));
			returnOrder.setUserId(dataObject.optString("SELLER_ID"));
			returnOrder.setOrderNo(dataObject.optString("ORDER_NO"));
			returnOrder.setOrderDate(dataObject.optString("ORDER_DATE"));
			returnOrder.setKoblNo(dataObject.optString("KOBL_NO"));
			returnOrder.setCalculateId(calculateId);
			returnOrder.setOrderReference(orderReference);
			returnOrder.setTrkCom(dataObject.optString("RE_TRK_COM"));
			returnOrder.setTrkNo(dataObject.optString("RE_TRK_NO"));
			returnOrder.setWhMsg(dataObject.optString("WARE_HOUSE_MSG"));
			returnOrder.setDlvReqMsg(dataObject.optString("DELIVERY_MSG"));
			returnOrder.setReturnType(dataObject.optString("RETURN_TYPE"));
			returnOrder.setFood(dataObject.optString("FOOD"));
			returnOrder.setSiteType(dataObject.optString("SYSTEM"));
			returnOrder.setWUserId(jsonHeader.get("userid").toString());
			returnOrder.setWUserIp(request.getRemoteAddr());
			
			// 수취업체 정보
			JSONObject senderObject = new JSONObject(senderDecode);
			checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "sender", senderObject);
			if (checkResult.length() != 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG", checkResult);
				rstMap.put("STATUS_CODE", "LE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			ManagerVO userInfo = mapper.selectUserAddrInfo(parameterInfo.get("sellerId").toString());
			userInfo.dncryptData();
			
			if (senderObject.optString("SENDER_NAME").equals("")) {
				returnOrder.setCneeName(userInfo.getStoreName());
			} else {
				returnOrder.setCneeName(senderObject.optString("SENDER_NAME"));	
			}
			
			if (senderObject.optString("SENDER_ZIP").equals("")) {
				returnOrder.setCneeZip(userInfo.getUserZip());
			} else {
				returnOrder.setCneeZip(senderObject.optString("SENDER_ZIP"));	
			}

			if (senderObject.optString("SENDER_STATE").equals("")) {
				returnOrder.setCneeState(userInfo.getUserEState());
			} else {
				returnOrder.setCneeState(senderObject.optString("SENDER_STATE"));	
			}

			if (senderObject.optString("SENDER_CITY").equals("")) {
				returnOrder.setCneeCity(userInfo.getUserECity());
			} else {
				returnOrder.setCneeCity(senderObject.optString("SENDER_CITY"));
			}

			if (senderObject.optString("SENDER_ADDR").equals("")) {
				returnOrder.setCneeAddr(userInfo.getUserAddr());
			} else {
				returnOrder.setCneeAddr(senderObject.optString("SENDER_ADDR"));
			}
			
			returnOrder.setCneeAddrDetail(userInfo.getUserAddrDetail());

			if (senderObject.optString("SENDER_TEL").equals("")) {
				returnOrder.setCneeTel(userInfo.getUserTel());
			} else {
				returnOrder.setCneeTel(senderObject.optString("SENDER_TEL"));
			}

			if (senderObject.optString("SENDER_HP").equals("")) {
				returnOrder.setCneeHp(userInfo.getUserTel());	
			} else {
				returnOrder.setCneeHp(senderObject.optString("SENDER_HP"));
			}
			
			returnOrder.setNativeCneeAddr(senderObject.optString("NATIVE_SENDER_ADDR"));
			returnOrder.setNativeCneeAddrDetail(senderObject.optString("NATIVE_SENDER_ADDR_DETAIL"));
			returnOrder.setNativeCneeName(senderObject.optString("NATIVE_SENDER_NAME"));
			returnOrder.setCdRemark(senderObject.optString("CD_REMARK"));
			
			// 발송인 정보
			JSONObject returnObject = new JSONObject(returnDecode);
			checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "return", returnObject);
			if (checkResult.length() != 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG", checkResult);
				rstMap.put("STATUS_CODE", "LE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			returnOrder.setAttnName(returnObject.optString("ATTN_NAME"));
			returnOrder.setAttnEmail(returnObject.optString("ATTN_EMAIL"));
			returnOrder.setAttnTel(returnObject.optString("ATTN_TEL"));
			
			if (returnOrder.getAttnName().equals("")) {
				returnOrder.setAttnName(invUserInfo.getInvUserName());
			}
			if (returnOrder.getAttnTel().equals("")) {
				returnOrder.setAttnTel(invUserInfo.getInvUserTel());
			}
			if (returnOrder.getAttnEmail().equals("")) {
				returnOrder.setAttnEmail(invUserInfo.getInvUserEmail());
			}
			
			returnOrder.setShipperName(returnObject.optString("PICKUP_NAME"));
			returnOrder.setShipperTel(returnObject.optString("PICKUP_TEL"));
			returnOrder.setShipperHp(returnObject.optString("PICKUP_MOBILE"));
			returnOrder.setShipperZip(returnObject.optString("PICKUP_ZIP"));
			if (!returnObject.optString("PICKUP_ENG_ADDR").equals("")) {
				returnOrder.setShipperAddr(returnObject.optString("PICKUP_ENG_ADDR"));
			} else {
				returnOrder.setShipperAddr(returnObject.optString("PICKUP_ADDR"));
			}
			returnOrder.setShipperAddrDetail(returnObject.optString("PICKUP_ADDR_DETAIL"));
			returnOrder.setReturnReason(returnObject.optString("RETURN_REASON"));
			returnOrder.setReturnReasonDetail(returnObject.optString("RETURN_REASON_DETAIL"));
			
			// 위약반송 파일 정보
			if (fileDecode.equals("")) {
				returnOrder.setTaxType("N");
			} else {
				JSONObject fileObject = new JSONObject(fileDecode);
				checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "file", fileObject);
				if (checkResult.length() != 0) {
					rstMap = new LinkedHashMap<String, Object>();
					rstMap.put("ERROR_MSG", checkResult);
					rstMap.put("STATUS_CODE", "LE001");
					rstMap.put("STATUS", "FAIL");
					rstMap.put("MSG", "");
					rstMap.put("ORDER_REFERENCE", "");
					rstMap.put("REG_NO", "");
					return rstMap;
				}
				returnOrder.setTaxType(fileObject.optString("TAX_TYPE"));
				if (returnOrder.getTaxType().equals("Y")) {
					fileParams.put("fileReasonType", fileObject.optString("FILE_REASON_TYPE"));
					fileParams.put("fileReason", fileObject.optString("FILE_REASON"));
					fileParams.put("fileReasonExten", fileObject.optString("FILE_REASON_EXTEN"));
					fileParams.put("fileCaptureType", fileObject.optString("FILE_CAPTURE_TYPE"));
					fileParams.put("fileCapture", fileObject.optString("FILE_CAPTURE"));
					fileParams.put("fileCaptureExten", fileObject.optString("FILE_CAPTURE_EXTEN"));
					fileParams.put("fileMessengerType", fileObject.optString("FILE_MESSENGER_TYPE"));
					fileParams.put("fileMessenger", fileObject.optString("FILE_MESSENGER"));
					fileParams.put("fileMessengerExten", fileObject.optString("FILE_MESSENGER_EXTEN"));
					fileParams.put("fileClType", fileObject.optString("FILE_CL_TYPE"));
					fileParams.put("fileCl", fileObject.optString("FILE_CL"));
					fileParams.put("fileClExten", fileObject.optString("FILE_CL_EXTEN"));
					/*
					fileParams.put("fileIcType", fileObject.optString("FILE_IC_TYPE"));
					fileParams.put("fileIc", fileObject.optString("FILE_IC"));
					fileParams.put("fileIcExten", fileObject.optString("FILE_IC_EXTEN"));
					*/
					fileParams.put("fileCopyBankType", fileObject.optString("FILE_COPY_BANK_TYPE"));
					fileParams.put("fileCopyBank", fileObject.optString("FILE_COPY_BANK"));
					fileParams.put("fileCopyBankExten", fileObject.optString("FILE_COPY_BANK_EXTEN"));
					returnOrder.setTaxReturn(fileObject.optString("TAX_RETURN"));
				}
			}

			// 상품 정보
			JSONArray itemArray = new JSONArray(dataObject.optString("ITEM"));
			for (int i = 0; i < itemArray.length(); i++) {
				HashMap<String, Object> itemInfo = new HashMap<String, Object>();
				JSONObject itemObject = itemArray.getJSONObject(i);
				checkResult = checkJsonField("RETURN_INFO_INSERT", "POST", "item", itemObject);
				if (checkResult.length() != 0) {
					rstMap = new LinkedHashMap<String, Object>();
					rstMap.put("ERROR_MSG", checkResult);
					rstMap.put("STATUS_CODE", "LE001");
					rstMap.put("STATUS", "FAIL");
					rstMap.put("MSG", "");
					rstMap.put("ORDER_REFERENCE", "");
					rstMap.put("REG_NO", "");
					return rstMap;
				}
				
				itemInfo.put("nno", nno);
				if (itemObject.optString("SUB_NO").equals("")) {
					itemInfo.put("subNo", i+1);
				} else {
					itemInfo.put("subNo", itemObject.optString("SUB_NO"));
				}
				itemInfo.put("userId", returnOrder.getUserId());
				itemInfo.put("orgStation", "082");
				itemInfo.put("itemDetail", itemObject.optString("ITEM_DETAIL"));
				itemInfo.put("brand", itemObject.optString("BRAND"));
				
				if (itemObject.optString("ITEM_WTA").equals("") || itemObject.isNull("ITEM_WTA")) {
					itemInfo.put("itemWta", 0);
				} else {
					itemInfo.put("itemWta", itemObject.optDouble("ITEM_WTA"));
				}
				
				if (itemObject.optString("WT_UNIT").equals("")) {
					itemInfo.put("wtUnit", "KG");
				} else {
					itemInfo.put("wtUnit", itemObject.optString("WT_UNIT").toUpperCase());
				}
				
				if (itemObject.optString("ITEM_CNT").equals("") || itemObject.isNull("ITEM_CNT") || itemObject.optString("ITEM_CNT").equals("0")) {
					itemInfo.put("itemCnt", 1);
				} else {
					itemInfo.put("itemCnt", itemObject.optInt("ITEM_CNT"));
				}
				
				if (itemObject.optString("UNIT_VALUE").equals("") || itemObject.isNull("UNIT_VALUE")) {
					itemInfo.put("unitValue", 0);
				} else {
					itemInfo.put("unitValue", itemObject.optDouble("UNIT_VALUE"));
				}
				
				itemInfo.put("unitCurrency", itemObject.optString("UNIT_CURRENCY"));
				itemInfo.put("makeCntry", itemObject.optString("MAKE_CNTRY"));
				itemInfo.put("makeCom", itemObject.optString("MAKE_COM"));
				itemInfo.put("hsCode", itemObject.optString("HS_CODE"));
				itemInfo.put("itemUrl", itemObject.optString("ITEM_URL"));
				itemInfo.put("itemImgUrl", itemObject.optString("ITEM_IMG_URL"));
				itemInfo.put("nativeItemDetail", "");
				itemInfo.put("wUserId", returnOrder.getUserId());
				itemInfo.put("wUserIp", request.getRemoteAddr());
				
				returnItem.add(itemInfo);
			}
			
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			
			HashMap<String, Object> fileInfo = new HashMap<String, Object>();
			fileInfo.put("nno", nno);
			if (!rstMap.get("STATUS").equals("FAIL")) {
				int koblCnt = mapper.selectKoblNoCnt(returnOrder);
				int orderRefCnt = mapper.selectOrderReferenceCnt(returnOrder);
				
				if (koblCnt > 0 || orderRefCnt > 0) {
					rstMap = new LinkedHashMap<String, Object>();
					rstMap.put("ERROR_MSG", "Already insert data");
					rstMap.put("STATUS_CODE", "NE002");
					rstMap.put("STATUS", "FAIL");
					rstMap.put("MSG", "");
					rstMap.put("ORDER_REFERENCE", "");
					rstMap.put("REG_NO", "");
					return rstMap;
				} else {
					try {
						if (returnOrder.getTaxType().equals("Y")) {
							HashMap<String, Object> fileResult = new HashMap<String, Object>();
							fileResult = s3FileInsert("FR", fileParams.get("fileReasonType").toString(), returnOrder.getUserId(), nno, 
									fileParams.get("fileReason").toString(), fileParams.get("fileReasonExten").toString());
							if ((boolean)fileResult.get("STATUS")) {
								fileInfo.put("fileReason", "http://img.mtuai.com/outbound/return/"+year+"/"+returnOrder.getUserId()+"/"+nno+"/"+nno+"_"+"FR."+fileParams.get("fileReasonExten").toString());
							} else {
								throw new Exception();
							}
							
							fileResult = s3FileInsert("FC", fileParams.get("fileCaptureType").toString(), returnOrder.getUserId(), nno, 
									fileParams.get("fileCapture").toString(), fileParams.get("fileCaptureExten").toString());
							if ((boolean)fileResult.get("STATUS")) {
								fileInfo.put("fileCapture", "http://img.mtuai.com/outbound/return/"+year+"/"+returnOrder.getUserId()+"/"+nno+"/"+nno+"_"+"FC."+fileParams.get("fileCaptureExten").toString());
							} else {
								throw new Exception();
							}
							
							fileResult = s3FileInsert("FM", fileParams.get("fileMessengerType").toString(), returnOrder.getUserId(), nno, 
									fileParams.get("fileMessenger").toString(), fileParams.get("fileMessengerExten").toString());
							if ((boolean)fileResult.get("STATUS")) {
								fileInfo.put("fileMessenger", "http://img.mtuai.com/outbound/return/"+year+"/"+returnOrder.getUserId()+"/"+nno+"/"+nno+"_"+"FM."+fileParams.get("fileMessengerExten").toString());
							} else {
								throw new Exception();
							}
							
							fileResult = s3FileInsert("FCL", fileParams.get("fileClType").toString(), returnOrder.getUserId(), nno, 
									fileParams.get("fileCl").toString(), fileParams.get("fileClExten").toString());
							if ((boolean)fileResult.get("STATUS")) {
								fileInfo.put("fileComm", "http://img.mtuai.com/outbound/return/"+year+"/"+returnOrder.getUserId()+"/"+nno+"/"+nno+"_"+"FCL."+fileParams.get("fileClExten").toString());
							} else {
								throw new Exception();
							}
							
							fileResult = s3FileInsert("FCB", fileParams.get("fileCopyBankType").toString(), returnOrder.getUserId(), nno, 
									fileParams.get("fileCopyBank").toString(), fileParams.get("fileCopyBankExten").toString());
							if ((boolean)fileResult.get("STATUS")) {
								fileInfo.put("fileBank", "http://img.mtuai.com/outbound/return/"+year+"/"+returnOrder.getUserId()+"/"+nno+"/"+nno+"_"+"FCB."+fileParams.get("fileCopyBankExten").toString());
							} else {
								throw new Exception();
							}
							
							// Goodsflow API에 수출면장 파일에 대한 항목이 없어서 fileIc 제외
							fileInfo.put("fileLicense", "");
							memberReturnOrderMapper.insertReturnFileList(fileInfo);
						}
						
						returnOrder.encrypData();
						memberReturnOrderMapper.insertReturnOrderList(returnOrder);
						memberReturnOrderMapper.insertReturnOrderStateLog(returnOrder);
						for (int itemIdx = 0; itemIdx < returnItem.size(); itemIdx++) {
							memberReturnOrderMapper.insertReturnItemList(returnItem.get(itemIdx));
						}		
					} catch (SQLException e) {
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("nno", nno);
						memberReturnOrderMapper.deleteReturnOrderInfo(parameters);
						
						rstMap = new LinkedHashMap<String, Object>();
						rstMap.put("ERROR_MSG", "SQL ERROR. Please Check data. or contact manager.");
						rstMap.put("STATUS_CODE", "SE001");
						rstMap.put("STATUS", "FAIL");
						rstMap.put("MSG", "");
						rstMap.put("ORDER_REFERENCE", "");
						rstMap.put("REG_NO", "");
						return rstMap;
					}
				}
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("MSG", "Return Order Request has been received");
				rstMap.put("STATUS", "SUCCESS");
				rstMap.put("ORDER_REFERENCE", orderReference);
				rstMap.put("ERROR_MSG", "");
				rstMap.put("STATUS_CODE", "S1000");
				rstMap.put("REG_NO", nno);
			}
		} catch (Exception e) {
			rstMap = new LinkedHashMap<String, Object>();
			rstMap.put("ERROR_MSG", "Exception Error");
			rstMap.put("STATUS_CODE", "DE001");
			rstMap.put("STATUS", "FAIL");
			rstMap.put("MSG", "");
			rstMap.put("ORDER_REFERENCE", "");
			rstMap.put("REG_NO", "");
			return rstMap;
		}
		
		return rstMap;
	}

	@Override
	public HashMap<String, Object> setApiPodVal(HttpServletRequest request, @RequestHeader Map<String,Object> jsonHeader, String parameters, String valueKinds) {
		LinkedHashMap<String, Object> returnVal = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		HashMap<Object, Object> parameterInfo = new HashMap<Object, Object>();
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>();
		
		try {
			rstMap = checkReturnApiOrderInfo(jsonHeader, request);
			
			if (rstMap.get("STATUS").equals("FAIL")) {
				return rstMap;
			}
			
			HashMap<String, Object> requestInfo = new LinkedHashMap<String, Object>();
			
			if (valueKinds.equals("ORDER_REFERENCE")) {
				parameterInfo.put("orderReference", parameters);
			} else if (valueKinds.equals("REG_NO")) {
				parameterInfo.put("nno", parameters);
			} else if (valueKinds.equals("KOBL_NO")) {
				parameterInfo.put("koblNo", parameters);
			} else if (valueKinds.equals("TRK_NO")) {
				parameterInfo.put("trkNo", parameters);
			}
			
			int orderChk = mapper.selectReturnOrderCheck(parameterInfo);
			if (orderChk == 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG","parameters is not exists.");
				rstMap.put("STATUS_CODE","NE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			String nno = mapper.selectReturnOrderNno(parameterInfo);
			parameterInfo.put("nno", nno);
			orderInfo = mapper.selectReturnOrderStatusInfo(parameterInfo);

			returnVal.put("ORDER_NUMBER", orderInfo.get("orderNo").toString());
			returnVal.put("ORDER_REFERENCE", orderInfo.get("orderReference").toString());
			returnVal.put("REG_NO", nno);
			returnVal.put("KOBL_NO", orderInfo.get("koblNo").toString());
			returnVal.put("DSTN_COUNTRY", orderInfo.get("dstnNation").toString());
			returnVal.put("SELLER_ID", orderInfo.get("userId").toString());
			returnVal.put("RE_TRK_COM", orderInfo.get("trkCom").toString());
			returnVal.put("RE_TRK_NO", orderInfo.get("trkNo").toString());
			
			LinkedHashMap<String, Object> returnStatusObj = new LinkedHashMap<String, Object>();
			returnStatusObj.put("STATUS", orderInfo.get("state").toString());
			
			ArrayList<HashMap<String, Object>> inspFileList = new ArrayList<HashMap<String,Object>>();
			inspFileList = mapper.selectInspectionFileList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> imgList = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> imgOne = new HashMap<String, Object>();
			
			for (int idx = 0; idx < inspFileList.size(); idx++) {
				imgOne = new HashMap<String, Object>();
				imgOne.put("IMG_URL", inspFileList.get(idx).get("fileDir").toString());
				imgList.add(imgOne);
			}
			
			LinkedHashMap<String, Object> inspResultObj = new LinkedHashMap<String, Object>();
			if (orderInfo.get("whStatus").toString().equals("WO")) {
				inspResultObj.put("RESULT", "S");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			} else if (orderInfo.get("whStatus").toString().equals("WF")) {
				inspResultObj.put("RESULT", "F");
				if (orderInfo.get("whStatusSub").toString().equals("WF1")) {
					inspResultObj.put("DETAIL", "상품 파손");
				} else {
					inspResultObj.put("DETAIL", orderInfo.get("whStatusDetail").toString());
				}
				inspResultObj.put("IMG_LIST", imgList);
			} else {
				inspResultObj.put("RESULT", "");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			}
			
			returnStatusObj.put("INSP_RESULT", inspResultObj);
			returnVal.put("RETURN_STATUS", returnStatusObj);
			
			LinkedHashMap<String, Object> trackingInfoObj = new LinkedHashMap<String, Object>();
			HashMap<String, Object> whoutInfo = new HashMap<String, Object>();
			whoutInfo = mapper.selectWhOutInfo(parameterInfo);
			trackingInfoObj.put("HAWB_NO", "");
			trackingInfoObj.put("TRANS_COM", "");
			trackingInfoObj.put("TRACKING_NO", "");

			String transCode = "";
			String hawbNo = "";
			if (!whoutInfo.get("nno").toString().equals("")) {
				trackingInfoObj.put("HAWB_NO", whoutInfo.get("hawbNo").toString());
				trackingInfoObj.put("TRANS_COM", whoutInfo.get("transName").toString());
				trackingInfoObj.put("TRACKING_NO", whoutInfo.get("trackingNo").toString());
				transCode = whoutInfo.get("transCode").toString();
				hawbNo = whoutInfo.get("hawbNo").toString();
			}
			
			LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
			ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
			
			if (!transCode.equals("")) {
				String rtnJson = "";
				if (transCode.equals("ARA")) {
					podDetailArray = getPodDetailArrayAra(hawbNo);
				} else if (transCode.equals("YSL")) {
					rtnJson = ysApi.makeYoungSungPodEN(hawbNo);
					podDetailArray = getPodDetailArrayYsl(rtnJson);
				} else if (transCode.equals("HJ")) {
					podDetailArray = getPodDetailArrayHj(hawbNo);
				} else if (transCode.equals("ACI-US")) {
					String subTransCode = comnService.selectSubTransCode(hawbNo);
					if (subTransCode.equals("ACI-T86")) {
						rtnJson = t86Api.makeT86Pod(hawbNo);
						podDetailArray = getPodDetailArrayT86(rtnJson);
					} else if (subTransCode.equals("PARCLL")) {
						podDetailArray = getPodDetailArrayPrcl(hawbNo);
					}
				} else if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
					podDetailArray = getPodDetailArrayFb(hawbNo, nno);
				}
			}
			
			ArrayList<HashMap<String, Object>> statusList = new ArrayList<HashMap<String,Object>>();
			statusList = mapper.selectReturnStateLog(nno);
			
			for (int i = 0; i < statusList.size(); i++) {
				podDetail = new LinkedHashMap<String, Object>();
				String state = statusList.get(i).get("state").toString();
				String date = statusList.get(i).get("date").toString();

				if (state.equals("C004")) {
					podDetail.put("CODE", "300");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Shipped out");
				} else if (state.equals("C003")) {
					podDetail.put("CODE", "230");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Waiting for Ship out");
				} else if (state.equals("C002")) {
					podDetail.put("CODE", "220");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Finished warehousing");
				} else if (state.equals("C001")) {
					podDetail.put("CODE", "200");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Arrived at Processing Facility");
				} else if (state.equals("B001")) {
					podDetail.put("CODE", "150");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Picking up parcels");
				} else {
					continue;
				}
				podDetailArray.add(podDetail);
			}
			
			String formatDate = "yyyy-MM-dd HH:mm:ss";
			String orderInDate = nno.substring(0, 14);

			SimpleDateFormat inputDate = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat outputDate = new SimpleDateFormat(formatDate);
			Date date = inputDate.parse(orderInDate);
			String outputDateString = outputDate.format(date);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "100");
			podDetail.put("DATE_TIME", outputDateString);
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Order information has been entered");
			podDetailArray.add(podDetail);
			
			trackingInfoObj.put("DETAILS", podDetailArray);
			returnVal.put("TRACKING_INFO", trackingInfoObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnVal;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayFb(String hawbNo, String nno) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> trackJson = new LinkedHashMap<String, Object>();
		String consumerKey = "";
		String auth = "";
		String jsonString = "";
		
		try {
			
			consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
			auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
			
			trackJson.put("mall_id", "aciexpress2");
			trackJson.put("fb_invoice_no", hawbNo);
			
			Gson gson = new Gson();
			jsonString = gson.toJson(trackJson);
			
			String inputLine = null;
			StringBuffer outResult = new StringBuffer();
			
			URL url = new URL("https://dhub-api.cafe24.com/api/Tracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
			
			JSONObject jsonObject = new JSONObject(String.valueOf(outResult.toString()));
			
			JSONObject meta = new JSONObject(String.valueOf(jsonObject.get("meta").toString()));
			
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = new JSONObject(String.valueOf(jsonObject.get("response").toString()));
				JSONArray jsonArr = new JSONArray(String.valueOf(response.get("trace").toString()));
				
				for (int i = 0; i < jsonArr.length(); i++) {
					podDetail = new LinkedHashMap<String, Object>();
					JSONObject traceJson = (JSONObject) jsonArr.get(i);
					
					String trkDate = traceJson.getString("set_date_time").toString();
					String datess = trkDate.replaceAll("T", " ");
					String date = datess.substring(0, 16);
					String location = traceJson.get("location").toString();
					String location2;
					
					if (location == "" || location.equals("") || location == null || location.equals("null")) {
						location2 = "-";
					} else {
						location2 = traceJson.get("location").toString();
					}
					
					if (traceJson.get("status_code").toString().equals("Delivered")) {
						podDetail.put("CODE", "600");
						podDetail.put("DATE_TIME", date);
						podDetail.put("LOCATION", location2);
						podDetail.put("DESCRIPTION", "Delivered");
					} else if (traceJson.get("status_code").toString().equals("OutForDelivery")) {
						podDetail.put("CODE", "500");
						podDetail.put("DATE_TIME", date);
						podDetail.put("LOCATION", location2);
						podDetail.put("DESCRIPTION", "Out for Delivery");
					} else if (traceJson.get("status_code").toString().equals("InTransit")) {
						podDetail.put("CODE", "400");
						podDetail.put("DATE_TIME", date);
						podDetail.put("LOCATION", location2);
						podDetail.put("DESCRIPTION", "In trasit");
					} else {
						continue;
					}
					podDetailArray.add(podDetail);
				}
			}
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayPrcl(String hawbNo) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> header = new LinkedHashMap<String, Object>();
		String apiUser = "US5394768";
		String apiKey = "4JmfPMZYHxYzRllv5LfgiA==";
		String token = apiUser+"&"+apiKey;
		byte[] tokenBytes = token.getBytes();
		String tokenVal = java.util.Base64.getEncoder().encodeToString(tokenBytes);

		try {
			HashMap<String, Object> matchInfo = new HashMap<String, Object>();
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("hawbNo", hawbNo);
			matchInfo = prclMapper.selectMatchInfo(parameterInfo);
			String matchNo = matchInfo.get("matchNo").toString();
			
			String url = "https://gapi.yunexpressusa.com/api/Tracking/GetTrackInfo?OrderNumber="+matchNo;
			header.put("Content-Type", "application/json");
			header.put("Authorization", "Basic "+tokenVal);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject resultVal = action.apiGet(url, header, "", "", "");
			
			if (!resultVal.get("Code").toString().equals("0000")) {
				throw new Exception();
			} else {
				Object itemObj = resultVal.get("Item");
				if (itemObj == null || itemObj.equals("")) {
					throw new Exception();
				} else {
					JSONObject itemOne = resultVal.getJSONObject("Item");
					JSONArray trackList = new JSONArray(String.valueOf(itemOne.get("OrderTrackingDetails").toString()));
					for (int i = trackList.length() - 1; i > -1; i--) {
						JSONObject trackInfo = (JSONObject) trackList.get(i);
						podDetail = new LinkedHashMap<String, Object>();
						int trackingCode = trackInfo.getInt("TrackingStatus");
						String location = "";
						if (!trackInfo.isNull("ProcessLocation") || !trackInfo.getString("ProcessLocation").isEmpty()) {
							location = trackInfo.get("ProcessLocation").toString();
						}
						String procDate = trackInfo.get("ProcessDate").toString();
						DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
						LocalDateTime localDateTime = LocalDateTime.parse(procDate, formatter);
						TimeZone kstTimeZone = TimeZone.getTimeZone("Asia/Seoul");
						DateTimeFormatter reFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						String dateTime = localDateTime.atZone(kstTimeZone.toZoneId()).format(reFormatter);
						
						if (trackingCode == 50) {
							podDetail.put("CODE", "600");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", location);
							podDetail.put("DESCRIPTION", "Delivered");
						} else if (trackingCode == 30) {
							podDetail.put("CODE", "500");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", location);
							podDetail.put("DESCRIPTION", "Out for Delivery");
						} else if (trackingCode == 28) {
							podDetail.put("CODE", "400");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", location);
							podDetail.put("DESCRIPTION", "Arrival in destination country");
						} else {
							continue;
						}
						podDetailArray.add(podDetail);
					}
				}
			}
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayT86(String rtnJson) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		try {
			JSONObject jsonVal = new JSONObject(String.valueOf(rtnJson));
			if (!jsonVal.optString("status").equals("S")) {
				throw new Exception();
			} else {
				JSONArray jsonArr = new JSONArray(String.valueOf(jsonVal.get("tracking").toString()));
				JSONObject jsonOne = (JSONObject) jsonArr.get(0);
				
				if (!jsonOne.optString("status").equals("S")) {
					throw new Exception();
				} else {
					JSONArray dataArr = new JSONArray(String.valueOf(jsonOne.get("details").toString()));
					if (dataArr.length() < 1) {
						throw new Exception();
					} else {
						for (int i = 0; i < dataArr.length(); i++) {
							podDetail = new LinkedHashMap<String, Object>();
							JSONObject detailOne = (JSONObject) dataArr.get(i);
							String dateTime = detailOne.optString("datetime");
							
							if (detailOne.optString("event_id").equals("900")) {
								podDetail.put("CODE", "600");
								podDetail.put("DATE_TIME", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("LOCATION", detailOne.optString("location"));
								podDetail.put("DESCRIPTION", "Delivered");
							} else if (detailOne.optString("description").toUpperCase().equals("OUT FOR DELIVERY")) {
								podDetail.put("CODE", "500");
								podDetail.put("DATE_TIME", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("LOCATION", detailOne.optString("location"));
								podDetail.put("DESCRIPTION", "Out for Delivery");
							} else if (detailOne.optString("event_id").equals("100") && detailOne.optString("sub_event_id").equals("117")) {
								podDetail.put("CODE", "400");
								podDetail.put("DATE_TIME", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("LOCATION", detailOne.optString("location"));
								podDetail.put("DESCRIPTION", "Arrival in destination country");
							} else {
								continue;
							}
							podDetailArray.add(podDetail);
						}
					}
				}
			}
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayHj(String hawbNo) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();

		try {
			String url = "http://api.withusoft.com/api/tracking.asp?invoice_no="+hawbNo;
			ApiAction action = ApiAction.getInstance();
			JSONObject jsonVal = new JSONObject();
			jsonVal = action.apiGet(url);
			
			JSONArray jsonArr = new JSONArray(String.valueOf(jsonVal.get("tracking_info").toString()));
			JSONObject jsonOne = (JSONObject) jsonArr.get(0);
			
			if (jsonOne.optString("jngnum").equals("")) {
				throw new Exception();
			} else {
				for (int i = jsonArr.length()-1; i > -1; i--) {
					podDetail = new LinkedHashMap<String, Object>();
					JSONObject jsonObj = (JSONObject) jsonArr.get(i);
					String jngdat = jsonObj.get("jngdat").toString();
					String date = jngdat.substring(0, 4)+"-"+jngdat.substring(4, 6)+"-"+jngdat.substring(6);
					String jngtim = jsonObj.get("jngtim").toString();
					String time = jngtim.substring(0, 2)+":"+jngtim.substring(2);
					String datetime = date + " " + time;
					String jngnum = jsonObj.optString("jngnum");
					
					if (jngnum.equals("19")) {
						podDetail.put("CODE", "600");
						podDetail.put("DATE_TIME", datetime);
						podDetail.put("LOCATION", "Korea, "+jsonObj.get("postnm").toString());
						podDetail.put("DESCRIPTION", "Delivered");
					} else if (jngnum.equals("18")) {
						podDetail.put("CODE", "500");
						podDetail.put("DATE_TIME", datetime);
						podDetail.put("LOCATION", "Korea, "+jsonObj.get("postnm").toString());
						podDetail.put("DESCRIPTION", "Out for Delivery");
					} else if (jngnum.equals("11")) {
						podDetail.put("CODE", "400");
						podDetail.put("DATE_TIME", datetime);
						podDetail.put("LOCATION", "Korea, "+jsonObj.get("postnm").toString());
						podDetail.put("DESCRIPTION", "Picked up by Shipping Partner");
					} else {
						continue;
					}
					podDetailArray.add(podDetail);
				}	
			}
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayYsl(String rtnJson) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		try {
			JSONObject returnObj = new JSONObject(String.valueOf(rtnJson));
			if (!returnObj.get("Code").toString().equals("0")) {
				throw new Exception();
			} else {
				JSONArray jsonArr = new JSONArray(String.valueOf(returnObj.get("Data").toString()));
				JSONObject jsonOne = (JSONObject) jsonArr.get(0);
				if (jsonOne.get("Status").toString().equals("-22")) {
					throw new Exception();
				} else {
					JSONArray dataArr = jsonOne.getJSONArray("TrackingList");
					for (int i = 0; i < dataArr.length(); i++) {
						podDetail = new LinkedHashMap<String, Object>();
						JSONObject dataOne = (JSONObject) dataArr.get(i);
						String status = dataOne.optString("Status");
						String dateTime = dataOne.optString("IssueDateTime");
						if (status.equals("700")) {
							podDetail.put("CODE", "600");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", dataOne.optString("Location"));
							podDetail.put("DESCRIPTION", "Delivered");
						} else if (status.equals("500")) {
							podDetail.put("CODE", "500");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", dataOne.optString("Location"));
							podDetail.put("DESCRIPTION", "Out for Delivery");
						} else if (status.equals("200")) {
							podDetail.put("CODE", "400");
							podDetail.put("DATE_TIME", dateTime);
							podDetail.put("LOCATION", dataOne.optString("Location"));
							podDetail.put("DESCRIPTION", "Arrival in destination country");
						} else {
							continue;
						}
						podDetailArray.add(podDetail);
					}
				}
			}
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		
		return podDetailArray;
	}

	private ArrayList<LinkedHashMap<String, Object>> getPodDetailArrayAra(String hawbNo) {
		ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		try {
			TrackingClientInfo clientInfo = new TrackingClientInfo();
			clientInfo.setAccountCountryCode("KR");
			clientInfo.setAccountEntity("SEL");
			clientInfo.setAccountNumber("172813");
			clientInfo.setAccountPin("321321");
			clientInfo.setUserName("overseas2@aciexpress.net");
			clientInfo.setPassword("Aci5606!");
			clientInfo.setVersion("1.0");

			net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
			transaction.setReference1("001");

			TrackingShipmentRequest trakShip = new TrackingShipmentRequest();

			trakShip.setClientInfo(clientInfo);
			trakShip.setTransaction(transaction);

			String[] trakBL = new String[1];
			trakBL[0] = hawbNo;

			trakShip.setShipments(trakBL);

			TrackingShipmentResponse rtnTracking = new TrackingShipmentResponse();
			rtnTracking = trackingProxy.trackShipments(trakShip);
			TrackingResult[] trkRst = new TrackingResult[0];
			
			if (rtnTracking.getTrackingResults().length == 0) {
				podDetail = new LinkedHashMap<String, Object>();
				podDetail.put("CODE", "-200");
				podDetail.put("DATE_TIME", "");
				podDetail.put("LOCATION", "");
				podDetail.put("DESCRIPTION", "No Data");
				podDetailArray.add(podDetail);
			} else {
				trkRst = rtnTracking.getTrackingResults()[0].getValue();
				for (int i = 0; i < trkRst.length; i++) {
					podDetail = new LinkedHashMap<String, Object>();
					if (trkRst[i].getUpdateCode().toString().equals("SH005")) {
						podDetail.put("CODE", "600");
						podDetail.put("DATE_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
						podDetail.put("LOCATION", trkRst[i].getUpdateLocation());
						podDetail.put("DESCRIPTION", "Delivered");
					} else if (trkRst[i].getUpdateCode().toString().equals("SH003")) {
						podDetail.put("CODE", "500");
						podDetail.put("DATE_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
						podDetail.put("LOCATION", trkRst[i].getUpdateLocation());
						podDetail.put("DESCRIPTION", "Out for Delivery");
					} else if (trkRst[i].getUpdateCode().toString().equals("SH022") && trkRst[i].getUpdateLocation().toString().equals("Dubai, United Arab Emirates")) {
						podDetail.put("UpdateCode", "400");
						podDetail.put("DATE_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
						podDetail.put("LOCATION", trkRst[i].getUpdateLocation());
						podDetail.put("DESCRIPTION", "Arrival in destination country");
					} else {
						continue;
					}
					podDetailArray.add(podDetail);
				}
			}
			
		} catch (Exception e) {
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}

		return podDetailArray;
	}

	@Override
	public HashMap<String, Object> setApiPodValTest(HttpServletRequest request, Map<String, Object> jsonHeader, String parameters, String valueKinds) {
		LinkedHashMap<String, Object> returnVal = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		HashMap<Object, Object> parameterInfo = new HashMap<Object, Object>();
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>();
		
		try {
			rstMap = checkReturnApiOrderInfo(jsonHeader, request);
			
			if (rstMap.get("STATUS").equals("FAIL")) {
				return rstMap;
			}
			
			HashMap<String, Object> requestInfo = new LinkedHashMap<String, Object>();
			
			if (valueKinds.equals("ORDER_REFERENCE")) {
				parameterInfo.put("orderReference", parameters);
			} else if (valueKinds.equals("REG_NO")) {
				if (parameters.equals("KR0000000036")) {
					parameterInfo.put("nno", "202307071526449472CHF");
				} else if (parameters.equals("KR0000000037")) {
					parameterInfo.put("nno", "202307071527079473ECE");
				} else if (parameters.equals("KR0000000038")) {
					parameterInfo.put("nno", "202307071527139474BCD");
				} else if (parameters.equals("KR0000000039")) {
					parameterInfo.put("nno", "202307071527189475CDJ");
				} else if (parameters.equals("KR0000000040")) {
					parameterInfo.put("nno", "202307191122390974GGC");
				} else if (parameters.equals("KR0000000041")) {
					parameterInfo.put("nno", "202307191122500975EDH");
				} else if (parameters.equals("KR0000000042")) {
					parameterInfo.put("nno", "202307191122520976HJD");
				} else {
					parameterInfo.put("nno", parameters);
				}
			}
			
			int orderChk = mapper.selectReturnOrderCheck(parameterInfo);
			if (orderChk == 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG","parameters is not exists.");
				rstMap.put("STATUS_CODE","NE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			String nno = mapper.selectReturnOrderNno(parameterInfo);
			parameterInfo.put("nno", nno);
			orderInfo = mapper.selectReturnOrderStatusInfo(parameterInfo);

			returnVal.put("ORDER_NUMBER", orderInfo.get("orderNo").toString());
			returnVal.put("ORDER_REFERENCE", orderInfo.get("orderReference").toString());
			if (valueKinds.equals("REG_NO")) {
				if (parameters.equals("KR0000000036")) {
					returnVal.put("REG_NO", "KR0000000036");
				} else if (parameters.equals("KR0000000037")) {
					returnVal.put("REG_NO", "KR0000000037");
				} else if (parameters.equals("KR0000000038")) {
					returnVal.put("REG_NO", "KR0000000038");
				} else if (parameters.equals("KR0000000039")) {
					returnVal.put("REG_NO", "KR0000000039");
				} else if (parameters.equals("KR0000000040")) {
					returnVal.put("REG_NO", "KR0000000040");
				} else if (parameters.equals("KR0000000041")) {
					returnVal.put("REG_NO", "KR0000000041");
				} else if (parameters.equals("KR0000000042")) {
					returnVal.put("REG_NO", "KR0000000042");
				} else {
					returnVal.put("REG_NO", nno);
				}
			}
			//returnVal.put("REG_NO", nno);
			returnVal.put("KOBL_NO", orderInfo.get("koblNo").toString());
			returnVal.put("DSTN_COUNTRY", orderInfo.get("dstnNation").toString());
			returnVal.put("SELLER_ID", orderInfo.get("userId").toString());
			returnVal.put("RE_TRK_COM", orderInfo.get("trkCom").toString());
			returnVal.put("RE_TRK_NO", orderInfo.get("trkNo").toString());
			
			LinkedHashMap<String, Object> returnStatusObj = new LinkedHashMap<String, Object>();
			returnStatusObj.put("STATUS", orderInfo.get("state").toString());
			returnStatusObj.put("DATE_TIME", orderInfo.get("stateDate").toString());
			
			ArrayList<HashMap<String, Object>> inspFileList = new ArrayList<HashMap<String,Object>>();
			inspFileList = mapper.selectInspectionFileList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> imgList = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> imgOne = new HashMap<String, Object>();
			
			for (int idx = 0; idx < inspFileList.size(); idx++) {
				imgOne = new HashMap<String, Object>();
				imgOne.put("IMG_URL", inspFileList.get(idx).get("fileDir").toString());
				imgList.add(imgOne);
			}
		
			
			LinkedHashMap<String, Object> inspResultObj = new LinkedHashMap<String, Object>();
			if (orderInfo.get("whStatus").toString().equals("WO")) {
				inspResultObj.put("RESULT", "S");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			} else if (orderInfo.get("whStatus").toString().equals("WF")) {
				inspResultObj.put("RESULT", "F");
				if (orderInfo.get("whStatusSub").toString().equals("WF1")) {
					inspResultObj.put("DETAIL", "상품 파손");
				} else {
					inspResultObj.put("DETAIL", orderInfo.get("whStatusDetail").toString());
				}
				inspResultObj.put("IMG_LIST", imgList);
			} else {
				inspResultObj.put("RESULT", "");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			}
			
			returnStatusObj.put("INSP_RESULT", inspResultObj);
			returnVal.put("RETURN_STATUS", returnStatusObj);
			
			LinkedHashMap<String, Object> trackingInfoObj = new LinkedHashMap<String, Object>();
			HashMap<String, Object> whoutInfo = new HashMap<String, Object>();
			whoutInfo = mapper.selectWhOutInfo(parameterInfo);
			trackingInfoObj.put("HAWB_NO", "");
			trackingInfoObj.put("TRANS_COM", "");
			trackingInfoObj.put("TRACKING_NO", "");

			String transCode = "";
			String hawbNo = "";
			
			if (!whoutInfo.get("nno").toString().equals("")) {
				trackingInfoObj.put("HAWB_NO", whoutInfo.get("hawbNo").toString());
				trackingInfoObj.put("TRANS_COM", whoutInfo.get("transName").toString());
				trackingInfoObj.put("TRACKING_NO", whoutInfo.get("trackingNo").toString());
				transCode = whoutInfo.get("transCode").toString();
				hawbNo = whoutInfo.get("hawbNo").toString();
			}
			
			LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
			ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
			
			if (!transCode.equals("")) {
				String rtnJson = "";
				if (transCode.equals("ARA")) {
					podDetailArray = getPodDetailArrayAra(hawbNo);
				} else if (transCode.equals("YSL")) {
					rtnJson = ysApi.makeYoungSungPodEN(hawbNo);
					podDetailArray = getPodDetailArrayYsl(rtnJson);
				} else if (transCode.equals("HJ")) {
					podDetailArray = getPodDetailArrayHj(hawbNo);
				} else if (transCode.equals("ACI-US")) {
					if (parameterInfo.get("nno").equals("202307191122500975EDH") || parameterInfo.get("nno").equals("202307191122520976HJD")) {
					} else {
						String subTransCode = comnService.selectSubTransCode(hawbNo);
						if (subTransCode.equals("ACI-T86")) {
							rtnJson = t86Api.makeT86Pod(hawbNo);
							podDetailArray = getPodDetailArrayT86(rtnJson);
						} else if (subTransCode.equals("PARCLL")) {
							podDetailArray = getPodDetailArrayPrcl(hawbNo);
						}	
					}
				} else if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
					podDetailArray = getPodDetailArrayFb(hawbNo, nno);
				}
			}
			
			ArrayList<HashMap<String, Object>> statusList = new ArrayList<HashMap<String,Object>>();
			statusList = mapper.selectReturnStateLog(nno);
			
			for (int i = 0; i < statusList.size(); i++) {
				podDetail = new LinkedHashMap<String, Object>();
				String state = statusList.get(i).get("state").toString();
				String date = statusList.get(i).get("date").toString();

				if (state.equals("C004")) {
					podDetail.put("CODE", "300");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Shipped out");
				} else if (state.equals("C003")) {
					podDetail.put("CODE", "230");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Waiting for Ship out");
				} else if (state.equals("C002")) {
					podDetail.put("CODE", "220");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Finished warehousing");
				} else if (state.equals("C001")) {
					podDetail.put("CODE", "200");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Arrived at Processing Facility");
				} else if (state.equals("B001")) {
					podDetail.put("CODE", "150");
					podDetail.put("DATE_TIME", date);
					podDetail.put("LOCATION", "Korea");
					podDetail.put("DESCRIPTION", "Picking up parcels");
				} else {
					continue;
				}
				podDetailArray.add(podDetail);
			}
			
			String formatDate = "yyyy-MM-dd HH:mm:ss";
			String orderInDate = nno.substring(0, 14);

			SimpleDateFormat inputDate = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat outputDate = new SimpleDateFormat(formatDate);
			Date date = inputDate.parse(orderInDate);
			String outputDateString = outputDate.format(date);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "100");
			podDetail.put("DATE_TIME", outputDateString);
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Order information has been entered");
			podDetailArray.add(podDetail);
			
			trackingInfoObj.put("DETAILS", podDetailArray);
			returnVal.put("TRACKING_INFO", trackingInfoObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnVal;
	}

	@Override
	public HashMap<String, Object> setApiPodValTest2(HttpServletRequest request, Map<String, Object> jsonHeader, String parameters, String valueKinds) {
		LinkedHashMap<String, Object> returnVal = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		HashMap<Object, Object> parameterInfo = new HashMap<Object, Object>();
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>();
		
		try {
			rstMap = checkReturnApiOrderInfo(jsonHeader, request);
			
			if (rstMap.get("STATUS").equals("FAIL")) {
				return rstMap;
			}
			
			HashMap<String, Object> requestInfo = new LinkedHashMap<String, Object>();
			
			if (valueKinds.equals("ORDER_REFERENCE")) {
				parameterInfo.put("orderReference", parameters);
			} else if (valueKinds.equals("REG_NO")) {
				parameterInfo.put("nno", parameters);
			}
			
			int orderChk = mapper.selectReturnOrderCheck(parameterInfo);
			if (orderChk == 0) {
				rstMap = new LinkedHashMap<String, Object>();
				rstMap.put("ERROR_MSG","parameters is not exists.");
				rstMap.put("STATUS_CODE","NE001");
				rstMap.put("STATUS", "FAIL");
				rstMap.put("MSG", "");
				rstMap.put("ORDER_REFERENCE", "");
				rstMap.put("REG_NO", "");
				return rstMap;
			}
			
			String nno = mapper.selectReturnOrderNno(parameterInfo);
			parameterInfo.put("nno", nno);
			orderInfo = mapper.selectReturnOrderStatusInfo(parameterInfo);

			returnVal.put("ORDER_NUMBER", orderInfo.get("orderNo").toString());
			returnVal.put("ORDER_REFERENCE", orderInfo.get("orderReference").toString());
			returnVal.put("REG_NO", nno);
			returnVal.put("KOBL_NO", orderInfo.get("koblNo").toString());
			returnVal.put("DSTN_COUNTRY", orderInfo.get("dstnNation").toString());
			returnVal.put("SELLER_ID", orderInfo.get("userId").toString());
			returnVal.put("RE_TRK_COM", orderInfo.get("trkCom").toString());
			returnVal.put("RE_TRK_NO", orderInfo.get("trkNo").toString());
			
			LinkedHashMap<String, Object> returnStatusObj = new LinkedHashMap<String, Object>();
			returnStatusObj.put("STATUS", "C004");
			returnStatusObj.put("DATE_TIME", "2023-07-06 17:50:33");
			
			ArrayList<HashMap<String, Object>> inspFileList = new ArrayList<HashMap<String,Object>>();
			inspFileList = mapper.selectInspectionFileList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> imgList = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> imgOne = new HashMap<String, Object>();
			
			for (int idx = 0; idx < inspFileList.size(); idx++) {
				imgOne = new HashMap<String, Object>();
				imgOne.put("IMG_URL", inspFileList.get(idx).get("fileDir").toString());
				imgList.add(imgOne);
			}
		
			
			LinkedHashMap<String, Object> inspResultObj = new LinkedHashMap<String, Object>();
			if (orderInfo.get("whStatus").toString().equals("WO")) {
				inspResultObj.put("RESULT", "S");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			} else if (orderInfo.get("whStatus").toString().equals("WF")) {
				inspResultObj.put("RESULT", "F");
				if (orderInfo.get("whStatusSub").toString().equals("WF1")) {
					inspResultObj.put("DETAIL", "상품 파손");
				} else {
					inspResultObj.put("DETAIL", orderInfo.get("whStatusDetail").toString());
				}
				inspResultObj.put("IMG_LIST", imgList);
			} else {
				inspResultObj.put("RESULT", "");
				inspResultObj.put("DETAIL", "");
				inspResultObj.put("IMG_LIST", imgList);
			}
			
			returnStatusObj.put("INSP_RESULT", inspResultObj);
			returnVal.put("RETURN_STATUS", returnStatusObj);
			
			LinkedHashMap<String, Object> trackingInfoObj = new LinkedHashMap<String, Object>();
			trackingInfoObj.put("HAWB_NO", "150000026611");
			trackingInfoObj.put("TRANS_COM", "ACI-동남아,일본");
			trackingInfoObj.put("TRACKING_NO", "570253593582");

			String transCode = "YSL";
			String hawbNo = "150000026611";

			LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
			ArrayList<LinkedHashMap<String, Object>> podDetailArray = new ArrayList<LinkedHashMap<String,Object>>();
			
			if (!transCode.equals("")) {
				String rtnJson = "";
				if (transCode.equals("ARA")) {
					podDetailArray = getPodDetailArrayAra(hawbNo);
				} else if (transCode.equals("YSL")) {
					rtnJson = ysApi.makeYoungSungPodEN(hawbNo);
					podDetailArray = getPodDetailArrayYsl(rtnJson);
				} else if (transCode.equals("HJ")) {
					podDetailArray = getPodDetailArrayHj(hawbNo);
				} else if (transCode.equals("ACI-US")) {
					String subTransCode = comnService.selectSubTransCode(hawbNo);
					if (subTransCode.equals("ACI-T86")) {
						rtnJson = t86Api.makeT86Pod(hawbNo);
						podDetailArray = getPodDetailArrayT86(rtnJson);
					} else if (subTransCode.equals("PARCLL")) {
						podDetailArray = getPodDetailArrayPrcl(hawbNo);
					}
				} else if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
					podDetailArray = getPodDetailArrayFb(hawbNo, nno);
				}
			}
			
			ArrayList<HashMap<String, Object>> statusList = new ArrayList<HashMap<String,Object>>();
			//statusList = mapper.selectReturnStateLog(nno);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "300");
			podDetail.put("DATE_TIME", "2023-07-06 17:50:33");
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Shipped out");
			podDetailArray.add(podDetail);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "230");
			podDetail.put("DATE_TIME", "2023-07-06 17:22:33");
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Waiting for Ship out");
			podDetailArray.add(podDetail);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "220");
			podDetail.put("DATE_TIME", "2023-07-05 14:44:03");
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Finished warehousing");
			podDetailArray.add(podDetail);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "200");
			podDetail.put("DATE_TIME", "2023-07-05 11:13:57");
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Arrived at Processing Facility");
			podDetailArray.add(podDetail);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "150");
			podDetail.put("DATE_TIME", "2023-07-05 11:08:11");
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Picking up parcels");
			podDetailArray.add(podDetail);
			
			String formatDate = "yyyy-MM-dd HH:mm:ss";
			String orderInDate = nno.substring(0, 14);

			SimpleDateFormat inputDate = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat outputDate = new SimpleDateFormat(formatDate);
			Date date = inputDate.parse(orderInDate);
			String outputDateString = outputDate.format(date);
			
			podDetail = new LinkedHashMap<String, Object>();
			podDetail.put("CODE", "100");
			podDetail.put("DATE_TIME", outputDateString);
			podDetail.put("LOCATION", "Korea");
			podDetail.put("DESCRIPTION", "Order information has been entered");
			podDetailArray.add(podDetail);
			
			trackingInfoObj.put("DETAILS", podDetailArray);
			returnVal.put("TRACKING_INFO", trackingInfoObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnVal;
	}

	
}