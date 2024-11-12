package com.example.temp.api.aci.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.CommonUtils;
import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.service.ApiV1Service;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.InsterumentVO;
import com.example.temp.api.aci.vo.MsgBodyVO;
import com.example.temp.api.aci.vo.MsgHeaderVO;
import com.example.temp.api.aci.vo.ShipperInfo;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.dto.CJParameter;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.api.logistics.service.CJHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.VietnamPostHandler;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.company.Epost;
import com.example.temp.api.shipment.company.YongSung;
import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.api.shopify.ShopifyAPI;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.PdfPrintVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.ManagerTakeinService;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.OrderRcptVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.VolumeVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.ViewYslItemCode;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.comn.ComnAPI;
import com.example.temp.trans.comn.HawbLookUpVo;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FBOrderVO;
import com.example.temp.trans.fastbox.FastBoxMapper;
import com.example.temp.trans.fastbox.FastBoxParameterVO;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.hanjin.HanjinMapper;
import com.example.temp.trans.hanjin.HanjinVO;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.parcll.ParcllAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.example.temp.trans.yunexpress.YunExpressAPI;
import com.google.gson.Gson;

import net.aramex.ws.ShippingAPI.v1.Address;
import net.aramex.ws.ShippingAPI.v1.ClientInfo;
import net.aramex.ws.ShippingAPI.v1.Contact;
import net.aramex.ws.ShippingAPI.v1.Dimensions;
import net.aramex.ws.ShippingAPI.v1.LabelInfo;
import net.aramex.ws.ShippingAPI.v1.Money;
import net.aramex.ws.ShippingAPI.v1.Party;
import net.aramex.ws.ShippingAPI.v1.Service_1_0Proxy;
import net.aramex.ws.ShippingAPI.v1.Shipment;
import net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest;
import net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse;
import net.aramex.ws.ShippingAPI.v1.ShipmentDetails;
import net.aramex.ws.ShippingAPI.v1.ShipmentItem;
import net.aramex.ws.ShippingAPI.v1.Transaction;
import net.aramex.ws.ShippingAPI.v1.Weight;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingClientInfo;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingResult;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentRequest;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentResponse;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import oracle.jdbc.proxy.annotation.GetCreator;


@Service
public class ApiV1ServiceImpl implements ApiV1Service{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ApiMapper mapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	FastBoxMapper fbMapper;
	
	@Autowired
	HanjinMapper hjMapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	YongSungAPI ysApi;
	@Autowired
	ComnAPI comnApi;
	@Autowired
	ShopifyAPI shopifyApi;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	MemberService memberService;
	@Autowired
	ServiceFunction serviceFunction;
	
	@Autowired
	OcsAPI ocsApi;
	
	@Autowired
	EfsAPI efsApi;
	
	@Autowired
	CJApi cjapi;
	
	@Autowired
	SekoAPI sekoApi;
	
	@Autowired
	Type86API t86Api;
	
	@Autowired
	EmsApi emsApi;
	
	@Autowired
	FastboxAPI fastboxApi;
	
	@Autowired
	HanjinAPI hjApi; 
	
	@Autowired
	YongSungAPI yslApi;
	
	@Autowired
	YunExpressAPI yunApi;
	
	@Autowired
	ManagerTakeinService mgrTakeinService;
	
	@Autowired
	ManagerService mgrService;
	
	@Value("${orderInfoChkStatus}")
    String orderInfoChkStatus;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	ParcllAPI prclApi;
	
	@Autowired
	Epost epost;
	
	@Autowired
	YongSung yongsung;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	CJHandler cjHandler;
	
	@Autowired
	CommonUtils commUtils;
	
	@Autowired
	VietnamPostHandler vnpHandler;
	
	private static final String UPPERCASE_ASCII =
		    "AEIOU" // grave
		    + "AEIOUY" // acute
		    + "AEIOUY" // circumflex
		    + "AON" // tilde
		    + "AEIOUY" // umlaut
		    + "A" // ring
		    + "C" // cedilla
		    + "OU" // double acute
		    ;

	private static final String UPPERCASE_UNICODE =
	    "\u00C0\u00C8\u00CC\u00D2\u00D9"
	    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
	    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
	    + "\u00C3\u00D5\u00D1"
	    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
	    + "\u00C5"
	    + "\u00C7"
	    + "\u0150\u0170"
	    ;

	
//	LinkedBlockingQueue<ApiOrderListVO> queue = new LinkedBlockingQueue<ApiOrderListVO>();
	LinkedBlockingQueue<HashMap<String,Object>> queue = new LinkedBlockingQueue<HashMap<String,Object>>();
	//LinkedBlockingQueue<ApiOrderListVO> updateQueue = new LinkedBlockingQueue<ApiOrderListVO>();
	LinkedBlockingQueue<HashMap<String,Object>> updateQueue = new LinkedBlockingQueue<HashMap<String,Object>>();
	HashSet<String> orderSet = new HashSet<String>();
	String YYYYMMDDHHMISS = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
	String YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
	Service_1_0Proxy apiProxy = new Service_1_0Proxy();

	TrackingService_1_0Proxy trackingProxy = new TrackingService_1_0Proxy(); 

	net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy apiTrackingProxy = new net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy();
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	private SecurityKeyVO originKey = new SecurityKeyVO();
//	@Scheduled(fixedDelay = 5000) 
//    public void checkMemory() throws Exception {
//		
//		
//		
//    	System.out.println("MAX : " + Runtime.getRuntime().maxMemory());
//    	System.out.println("TOTAL : " + Runtime.getRuntime().totalMemory());
//    	System.out.println("FREE : " + Runtime.getRuntime().freeMemory());
//    	System.out.println("TOTAL-FREE : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//    	System.out.println("--------------------------------------------------------------------------");
//    } 

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 1)
	@Transactional(rollbackFor=Exception.class) 
    public void insertByQueue() throws Exception {
    	try {
    		if(queue.peek() != null) {
    			HashMap<String,Object> temp = new HashMap<String,Object>();
    			ApiOrderListVO tempOrderList = new ApiOrderListVO();
    			ArrayList<ApiOrderItemListVO> tempOrderItemList = new ArrayList<ApiOrderItemListVO>();
    			
    			temp = queue.poll();
    			tempOrderList = (ApiOrderListVO)temp.get("orderList");
    			tempOrderItemList = (ArrayList<ApiOrderItemListVO>)temp.get("orderItemList");
    			
				mapper.insertApiOrderList(tempOrderList);
				orderSet.remove(tempOrderList.getOrderNo()+tempOrderList.getUserId());
				for(int index = 0; index < tempOrderItemList.size(); index++) {
					mapper.insertApiOrderItem(tempOrderItemList.get(index));
				}
				temp = null;
				tempOrderList=null;
				tempOrderItemList=null;
	        	Thread.sleep(1);
    		}
		} catch (InterruptedException e) {
			logger.error("Exception", e);
		}
    } 
	
//	@SuppressWarnings("unchecked")
//	@Scheduled(fixedDelay = 1)
//	@Transactional(rollbackFor=Exception.class) 
//    public void shopifyOrderListGetThread() throws Exception {
//    	try {
//			ArrayList<String> orgStationList = new ArrayList<String>(); 
//			orgStationList = mapper.selectOrgStationAll(); 
//			int roop = 0; 
//			for(roop = 0; roop < orgStationList.size(); roop++) { 
//				ArrayList<ApiShopifyInfoVO> storeUrlList = mapper.selectStoreUrl(orgStationList.get(roop)); 
//				ArrayList<String> resultString = new ArrayList<String> ();
//				for(int i = 0 ; i <storeUrlList.size(); i++) {
//					resultString.add(shopifyOrderListGetForThread(storeUrlList.get(i))); 
//				} 
//			}
//    		Thread.sleep(1000);
////        	Thread.sleep(1200000);
//		} catch (InterruptedException e) {
//			logger.error("Exception", e);
//		}
//    } 
	
//	@SuppressWarnings("unchecked")
//	@Scheduled(fixedDelay = 1)
//	@Transactional(rollbackFor=Exception.class) 
//    public void updateByQueue() throws Exception {
//    	try {
//    		if(updateQueue.peek() != null) {
//    			HashMap<String,Object> temp = new HashMap<String,Object>();
//    			ApiOrderListVO tempOrderList = new ApiOrderListVO();
//    			ArrayList<ApiOrderItemListVO> tempOrderItemList = new ArrayList<ApiOrderItemListVO>();
//    			
//    			temp = updateQueue.poll();
//    			tempOrderList = (ApiOrderListVO)temp.get("orderList");
//    			tempOrderItemList = (ArrayList<ApiOrderItemListVO>)temp.get("orderItemList");
//    			
//        		mapper.updateApiOrderList(tempOrderList);
//        		for(int index = 0; index < tempOrderItemList.size(); index++) {
//        			mapper.insertApiOrderItem(tempOrderItemList.get(index));
//				}
//	        	Thread.sleep(1);
//    		}
//		} catch (InterruptedException e) {
//			logger.error("Exception", e);
//		}
//    }
	
	public void insertApiOrderListQueue(ApiOrderListVO targetVO, ArrayList<ApiOrderItemListVO> targetItemList) throws Exception {
			String val = targetVO.getOrderNo()+targetVO.getUserId();
			HashMap<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("orderList", targetVO);
			tempMap.put("orderItemList", targetItemList);
			orderSet.add(val);
			queue.offer(tempMap);
			tempMap=null;
	}
	
	public boolean updateApiOrderListQueue(ApiOrderListVO targetVO, ArrayList<ApiOrderItemListVO> targetItemList) throws Exception {
		int count = 0;
		count = mapper.selectOrderInfo(targetVO);
		if(count > 0) {
			HashMap<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("orderList", targetVO);
			tempMap.put("orderItemList", targetItemList);
//			mapper.updateApiOrderList(targetVO);
//			updateQueue.offer(targetVO);
			updateQueue.offer(tempMap);
			tempMap = null;
			return false;
		}
		return true;
	}
	
	public Map<String, Object> checkApiOrderInfo(Map<String,Object> jsonHeader, HttpServletRequest request,String userKey) throws Exception{
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		if(orderInfoChkStatus.equals("on")) {
			try {
				if(!jsonHeader.get("content-type").equals("application/json")){
					temp.put("Error_Msg","In case content type is not JSON type");
					temp.put("Status_Code","S10");
					return temp;
				}
//				if(!request.isSecure()){
//					temp.put("Error_Msg","In case HTTPS is not connected");
//					temp.put("Status_Code","S20");
//		  		temp.put("Order_No","-");
//					return temp;
//				}
				
				String ipChk = mapper.selectUserAllowIp(apiUserId, request.getRemoteAddr());
				
		 		if(!ipChk.equals("TRUE")) {
		 			temp.put("Error_Msg","In case CONNECT IP is not allow Ip");
		 			temp.put("Status_Code","S30");
		 			return temp;
		 		}
			
			
				String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
				String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");
			
			
				if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
					temp.put("Error_Msg","ACI KEY IS NOT MATCHING");
					temp.put("Status_Code","L10");
					return temp;
				}
				String from = apiKeyDecyptList[0].toLowerCase();

				boolean regex = Pattern.matches(YYYYMMDDHHMISS, from);
				
				if(!regex) {
					temp.put("Error_Msg","ACI KEY IS NOT MATCHING");
					temp.put("Status_Code","L20");
					return temp;
				}
				
				LocalDateTime date1 = LocalDateTime.parse(from ,DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ));
				LocalDateTime currntTime = LocalDateTime.now();

				if(date1.plusMinutes(5).isBefore(currntTime)) {
					temp.put("Error_Msg","Transfer Time Exceeded");
					temp.put("Status_Code","L30");
					return temp;
				}
			}catch(IllegalBlockSizeException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
				temp.put("Error_Msg","ACI KEY IS NOT SUPPORTED");
				temp.put("Status_Code","L30");
				return temp; 
			}catch (Exception e) {
				// TODO: handle exception
				temp.put("Error_Msg",e.toString());
				temp.put("Status_Code","D00");
				return temp; 
			}
		}
		return temp; 
	}


	@Override
	public ArrayList<Map<String, Object>> insertOrderInsp(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		String transCodeByRemark = "";
		String expRegNo = "";
		BlApplyVO rtnval = new BlApplyVO ();
		
		if (apiUserId.equals("itsel2")) {
			
		} else {
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("BL_No","");
			tempItem.put("BL_Print_Url","");
//			rtnItemVal.add(tempItem);
			temp.put("Detail",tempItem);
			rtnVal.add(temp);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jObject.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnVal;
		}
		}
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			ApiOrderListVO defaultApiOrderList = new ApiOrderListVO();
			ShipperInfo shipperInfo = new ShipperInfo();
			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
			String decryptRtn ="";
			try {
				tempItem = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				jObject = jArray1.getJSONObject(index1);
				JSONArray jArray = jObject.getJSONArray("GoodsInfo");
				
				String newNno = new String();
				newNno = comnService.selectNNO();
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jObject.toString());
				parameters.put("wUserId",apiUserId);
				parameters.put("wUserIp",apiUserIp);
				parameters.put("connUrl",request.getServletPath());
				parameters.put("nno", newNno);
				parameters.put("orgStation",jObject.getString("Departure_Station"));
				parameters.put("dstnNation",jObject.getString("Arrival_Nation"));
				parameters.put("userId", apiUserId);
				transCodeByRemark = comnService.selectUserTransCode(parameters);
				parameters.put("transCode", transCodeByRemark);
				parameters.put("dstnNation", jObject.getString("Arrival_Nation"));
				mapper.insertApiConn(parameters); 
				int cnts = mapper.checkNation(parameters);
				if(cnts==0) {
					HashMap<String,Object> tempparameters = new HashMap<String,Object>();
					tempparameters.put("userId", apiUserId);
					tempparameters.put("orgStation",jObject.getString("Departure_Station"));
					tempparameters.put("dstnNation","DEF");
					if(mapper.selectDefaultTransCom(tempparameters) != null) {
						transCodeByRemark = mapper.selectDefaultTransCom(tempparameters);
					}else {
						temp.put("Status_Code","P10");
						temp.put("Error_Msg","배송가능 국가가 아닙니다. 데이터를 확인 해 주세요");
						throw new Exception();
					}
				}
				
				//2024.03.20 수정
				//temp = serviceFunction.orderJsonColumnChk(jObject, transCodeByRemark);
				temp = serviceFunction.orderJsonColumnChkV2(jObject, transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				//defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				shipperInfo = mapper.selectShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrderType("INSP");
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				if(jObject.has("BL_No")) {
					apiOrderList.setHawbNo(jObject.optString("BL_No",""));
				}
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.optString("Order_Number",""));
				}
				apiOrderList.setTransCode(transCodeByRemark);
				apiOrderList = serviceFunction.setApiOrderListValue(apiOrderList, jObject, transCodeByRemark);
				

				if (jObject.has("Exp_Licence_YN")) {
					String expType = jObject.optString("Exp_Licence_YN");
					
					if (!expType.equals("N")) {
						
						apiOrderList.setExpType(expType);
						
						if (jObject.optString("Exp_Business_Name", "").equals("")) {
							temp.put("Error_Msg", "Exp_Business_Name IS NULL OR EMPTY");
							temp.put("Status_Code","P10");
							throw new Exception();
						} else {
							apiOrderList.setExpCor(jObject.optString("Exp_Business_Name"));
						}
							
						if (expType.equals("E")) {
							
							String expVal = "";
							
							if (jObject.optString("Exp_Business_Num","").equals("")) {
								expVal = expVal + "Exp_Business_Num, ";
							}
							
							if (jObject.optString("Exp_Business_Representative").equals("")) {
								expVal = expVal + "Exp_Business_Representative, ";
							}

							if (jObject.optString("Exp_Business_Addr").equals("")) {
								expVal = expVal + "Exp_Business_Addr, ";
							}

							if (jObject.optString("Exp_Business_Zip").equals("")) {
								expVal = expVal + "Exp_Business_Zip, ";
							}
							
							if (!expVal.equals("")) {
								temp.put("Error_Msg", expVal+" IS NULL OR EMPTY");
								temp.put("Status_Code","P10");
								throw new Exception();
							}
						}
						
						apiOrderList.setExpRprsn(jObject.optString("Exp_Business_Representative",""));
						apiOrderList.setExpAddr(jObject.optString("Exp_Business_Addr",""));
						apiOrderList.setExpZip(jObject.optString("Exp_Business_Zip",""));
						apiOrderList.setExpRgstrNo(jObject.optString("Exp_Business_Num",""));
						apiOrderList.setExpCstCd(jObject.optString("Exp_Shipper_Code",""));
						apiOrderList.setAgtCor(jObject.optString("Agency_Business_Name",""));
						apiOrderList.setAgtCstCd(jObject.optString("Agency_Business_Code",""));
						apiOrderList.setAgtBizNo(jObject.optString("Agency_Business_Num",""));
						apiOrderList.setSendYn("N");
						apiOrderList.setExpNo(jObject.optString("Exp_No",""));
						
					} else {
						apiOrderList.setExpType("N");
					}
					
				}
				
				// 2024.06.26 추가
				if (jObject.has("Tax_Number") || jObject.has("TaxNumber")) {
					if  (jObject.has("Tax_Number")) {
						apiOrderList.setShipperTaxNo(jObject.optString("Tax_Number"));
					}
					if (jObject.has("TaxNumber")) {
						apiOrderList.setShipperTaxNo(jObject.optString("TaxNumber"));
					}
				} else {
					apiOrderList.setShipperTaxNo("");
				}
				
				if (jObject.has("Tax_Type")) {
					try {
						String taxTypeString = jObject.optString("Tax_Type");
						int shipperTaxType = Integer.parseInt(taxTypeString);
						//int shipperTaxType = jObject.optInt("Tax_Type");
						if (shipperTaxType < 0 || shipperTaxType > 12) {
							temp.put("Status_Code", "D10");
							temp.put("Error_Msg", "Tax_Type is invalid value.");
							throw new Exception();
						} else {
							apiOrderList.setShipperTaxType(String.valueOf(shipperTaxType));
						}
					} catch (NumberFormatException e) {
						apiOrderList = null;
						apiOrderItemList = null;
						temp.put("Status_Code", "D10");
						temp.put("Error_Msg", "Tax_Type is invalid format.");
						throw new Exception();
					}
				}
				
				if (jObject.has("Receiver_Tax_Number")) {
					apiOrderList.setCneeTaxNo(jObject.optString("Receiver_Tax_Number"));
				} else {
					apiOrderList.setCneeTaxNo("");
				}
				
				if (jObject.has("Receiver_Tax_Type")) {
					try {
						String cneeTaxTypeString = jObject.optString("Receiver_Tax_Type");
						int cneeTaxType = Integer.parseInt(cneeTaxTypeString);
						//int cneeTaxType = jObject.optInt("Receiver_Tax_Type");
						if (cneeTaxType < 0 || cneeTaxType > 12) {
							temp.put("Status_Code", "D10");
							temp.put("Error_Msg", "Receiver_Tax_Type is invalid value.");
							throw new Exception();
						} else {
							apiOrderList.setCneeTaxType(String.valueOf(cneeTaxType));
						}
					} catch (NumberFormatException e) {
						apiOrderList = null;
						apiOrderItemList = null;
						temp.put("Status_Code", "D10");
						temp.put("Error_Msg", "Receiver_Tax_Type is invalid format.");
						throw new Exception();
					}
				}
				
				if (jObject.has("Cosmetic_Yn")) {
					String cosmeticYn = jObject.optString("Cosmetic_Yn", "N");
					switch (cosmeticYn.toUpperCase()) {
					case "Y":
						apiOrderList.setCosmetic("Y");
						break;
					case "N":
						apiOrderList.setCosmetic("N");
						break;
					default:
						apiOrderList.setCosmetic("N");
						break;
					}
				}
				
				if (jObject.has("Sign_Yn")) {
					String signYn = jObject.optString("Sign_Yn", "N");
					switch (signYn.toUpperCase()) {
					case "Y":
						apiOrderList.setSign("Y");
						break;
					case "N":
						apiOrderList.setSign("N");
						break;
					default:
						apiOrderList.setSign("N");
						break;
					}
				}
				
				
				if (jObject.has("Receiver_Ward")) {
					apiOrderList.setCneeWard(jObject.optString("Receiver_Ward"));
				} else {
					apiOrderList.setCneeWard("");
				}
				
				
				if (jObject.has("DeclarationType") || jObject.has("Declaration_Type")) {
					if  (jObject.has("DeclarationType")) {
						apiOrderList.setShipperTaxNo(jObject.optString("DeclarationType"));
					}
					if (jObject.has("Declaration_Type")) {
						apiOrderList.setShipperTaxNo(jObject.optString("Declaration_Type"));
					}
				} else {
					apiOrderList.setDeclType("4");
				}
				
				
				apiOrderList.setUploadType("API");
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				
				//serviceFunction.settingShipperInfo(apiOrderList, defaultApiOrderList);
				
				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}
				
					
				/*무게에 따른 TransComChg 시작*/
				//String transCom= serviceFunction.transComChg(apiOrderList, transCodeByRemark);
    			apiOrderList.setTransCode(transCodeByRemark);
    			/*무게에 따른 TransComChg 끝*/
    			shipperInfo.decryptData();
    			String dstnNation = apiOrderList.getDstnNation();
    			
    			if(apiOrderList.getShipperName().equals("")) {
    				if (dstnNation.equals("KR")) {
    					apiOrderList.setShipperName(shipperInfo.getShipperName());
    				} else {
    					apiOrderList.setShipperName(shipperInfo.getShipperEName());
    				}
    			}
    			
    			if(apiOrderList.getShipperZip().equals("")) {
    				apiOrderList.setShipperZip(shipperInfo.getShipperZip());
    			}
    			
    			if(apiOrderList.getShipperTel().equals("")) {
    				apiOrderList.setShipperTel(shipperInfo.getShipperTel());
    			}
    			
    			if(apiOrderList.getShipperAddr().equals("")) {
    				if (dstnNation.equals("KR")) {
    					apiOrderList.setShipperAddr(shipperInfo.getShipperAddr());
    				} else {
    					apiOrderList.setShipperAddr(shipperInfo.getShipperEAddr());
    				}
    			}
    			
    			if(apiOrderList.getShipperAddrDetail().equals("")) {
    				if (dstnNation.equals("KR")) {
    					apiOrderList.setShipperAddrDetail(shipperInfo.getShipperAddrDetail());
    				} else {
    					apiOrderList.setShipperAddrDetail(shipperInfo.getShipperEAddrDetail());
    				}
    			}
    			
    			if(apiOrderList.getShipperHp().equals("")) {
    				apiOrderList.setShipperHp(shipperInfo.getShipperHp());
    			}
    			
    			if(apiOrderList.getShipperEmail().equals("")) {
    				apiOrderList.setShipperEmail(shipperInfo.getShipperEmail());
    			}
    			
				int jArrayLen = jArray.length();
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					
					temp = serviceFunction.itemJsonColumnChk(itemObj, apiOrderList);
					
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					
					temp = serviceFunction.itemJsonValueChk(itemObj, index1,apiOrderList);
					
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					
					if ("VNP".equals(apiOrderList.getTransCode().toUpperCase())) {
						if (!"VND".equals(itemObj.optString("Chg_Currency").toUpperCase())) {
							temp.put("Status_Code", "P20");
							temp.put("Error_Msg","베트남 발송 시 통화는 VND만 입력 가능합니다.");
							throw new Exception();	
						}
					}
					
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					apiOrderItem = serviceFunction.setApiOrderItemValue(apiOrderItem, itemObj, apiOrderList.getTransCode());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					apiOrderItem.setTransCode(apiOrderList.getTransCode());
					
					if(itemObj.has("Stock_Yn")) {
						// TODO = Y일 경우 출고 되었는지 확인, N 일경우 그냥 진행 하는 부분 하기
						if(itemObj.getString("Stock_Yn").equals("Y")) {
							apiOrderItem.setStockNo(itemObj.getString("Stock_No"));
							/////////StockNo 처리방 생각... Table 새로파는데, NNO SUBNO 같이 받아야 할 것 같은데.. 
							mapper.insertStockOrder(apiOrderItem);
							if(mapper.checkCusItemCode(apiOrderItem)!=1) {
								//error
								temp.put("Status_Code","C10");
								temp.put("Error_Msg","Stock_No is not matching code");
								throw new Exception();
							}
						}
					}
					apiOrderItemList.add(apiOrderItem);
				}
				
				if(mapper.selectHawbNo(apiOrderList) != 0) {
					temp.put("Status_Code", "D20");
					temp.put("Error_Msg","Already registered BL No");
					throw new Exception();
				}
				
				if(mapper.selectOrderInfo(apiOrderList)!=0) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","Already registered data");
					throw new Exception();
				}
				if(orderSet.contains(apiOrderList.getOrderNo()+apiOrderList.getUserId())) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","This OrderNo is registered in logic");
					throw new Exception();
				}

				applyTransfer(apiOrderList,apiOrderItemList, apiUserId, apiUserIp, transCodeByRemark, request, temp,expRegNo);
				
				if (!apiOrderList.getExpType().equals("N")) {
		 			
		 			ExportDeclare expInfo = new ExportDeclare();
		 			expInfo.setNno(apiOrderList.getNno());
		 			expInfo.setUserId(apiOrderList.getUserId());
		 			expInfo.setExpType(apiOrderList.getExpType());
		 			expInfo.setExpCor(apiOrderList.getExpCor());
		 			expInfo.setExpRprsn(apiOrderList.getExpRprsn());
		 			expInfo.setExpAddr(apiOrderList.getExpAddr());
		 			expInfo.setExpZip(apiOrderList.getExpZip());
		 			expInfo.setExpRgstrNo(apiOrderList.getExpRgstrNo());
		 			expInfo.setExpCstCd(apiOrderList.getExpCstCd());
		 			expInfo.setAgtCor(apiOrderList.getAgtCor());
		 			expInfo.setAgtCstCd(apiOrderList.getAgtCstCd());
		 			expInfo.setAgtBizNo(apiOrderList.getAgtBizNo());
		 			expInfo.setExpNo(apiOrderList.getExpNo());
		 			expInfo.setSendYn(apiOrderList.getSendYn());
		 			expInfo.encryptData();
		 			try {
		 				mapper.insertExportDeclare(expInfo);
		 			} catch (Exception ex) {
		 				temp.put("Status_Code","D10");
						temp.put("Error_Msg","Exception! SQL Error!!");
						fn_TransComApply_rollback(apiOrderList);
						//fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
		 			}
		 			
		 			if (transCodeByRemark.equals("YSL") && !expInfo.getExpNo().equals("") && !expInfo.getExpType().equals("E")) {
		 				HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		 				orderInfo.put("nno", apiOrderList.getNno());
		 				yongsung.updateExportLicenseInfo(orderInfo);
		 			}
		 			
		 		}

				
				temp.put("Error_Msg","-");
				if(!temp.containsKey("Status_Code")) {
					temp.put("Status_Code","A10");
				}
					
				/* mapper.insertApiOrderList(apiOrderList); */
				
		 		if(jObject.has("Hold_Yn")) {
		 			parameters.put("hawbNo",apiOrderList.getHawbNo());
		 			String holdYn = jObject.getString("Hold_Yn");
		 			String reason = jObject.getString("Reason");
		 			if(holdYn.toLowerCase().equals("")) {
		 				holdYn = "N";
		 				reason = "";
		 			}
		 			parameters.put("holdYn",holdYn);
			 		parameters.put("remark",reason);
			 		rst = new HashMap<String,Object>();
			 		try {
			 		rst = mapper.execSpHoldBl(parameters);
			 		}catch (Exception e) {
						// TODO: handle exception
			 			temp.put("Status_Code","D10");
						temp.put("Error_Msg","Exception! SQL Error!!");
						fn_TransComApply_rollback(apiOrderList);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(apiOrderList);
						throw new Exception();
			 		}
		 		}
		 		
			}catch (Exception e) {
				if(e.getMessage()!=null) {
					temp.put("Status_Code","D10");
					temp.put("Error_Msg",e.getMessage());
				}
				// TODO: handle exception
				if(temp.get("Status_Code").equals("D20")) {
					
				}else if(temp.get("Status_Code").equals("P30")){
					
				}else if(temp.get("Status_Code").equals("P20")){
					
				}else if(temp.get("Status_Code").equals("P10")){
					
				}else if(temp.get("Status_Code").equals("ARA")){
					
				}else if(temp.get("Status_Code").equals("D30")){
					
				}
			}finally {
				if(temp.get("Status_Code").equals("A10")) {
					String hawbNo = rtnval.getHawbNo();
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			 		
			 		if(transCodeByRemark.equals("ARA")) {
			 			
			 			tempItem.put("BL_No",apiOrderList.getHawbNo());
			 			tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
						hawbNo = apiOrderList.getHawbNo();
						
			 		}else if(transCodeByRemark.equals("ACI")) {
			 			
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
						
			 		}else if (transCodeByRemark.equals("CJ")) {
			 			
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/tracking?hawbNo="+hawbNo);
			 		}else if (transCodeByRemark.equals("VNP")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/tracking?hawbNo="+hawbNo);
			 		}else {
			 			
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
			 			tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		}
			 		
				}else {
					if(mapper.selectOrderInfo(apiOrderList)!=0) {
						ApiOrderListVO TempApiOrderList = new ApiOrderListVO();
						TempApiOrderList = mapper.selectAlreadyOrder(apiOrderList.getUserId(),apiOrderList.getOrderNo());
						String wDate = TempApiOrderList.getWDate();
						String year = wDate.substring(0,4);
						String month = wDate.substring(4,6);
						String day = wDate.substring(6,8);
						wDate = year+"-"+month+"-"+day;
						String week = Integer.toString(getWeekOfYear(wDate));
						tempItem.put("BL_No",TempApiOrderList.getHawbNo());
						//tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+TempApiOrderList.getHawbNo());
						tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+TempApiOrderList.getHawbNo());
					}else {
						tempItem.put("BL_No","");
						tempItem.put("BL_Print_Url","");
						tempItem.put("BL_Pod_Url","");
					}
					
				}
				//rtnItemVal.add(tempItem);
				
				temp.put("Detail",tempItem);
				if(jObject.has("Order_Number")) {
					temp.put("Order_No",jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					temp.put("Order_No",jObject.getString("Order_No"));
				}
				
				parameters.put("rtnContents", temp.toString());
				mapper.updateApiConn(parameters);
				rtnVal.add(temp);
			}
		}
		return rtnVal;
	}
	
	
	@Override
	public ArrayList<Map<String, Object>> insertOrderTakeIn(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		String transCodeByRemark = "";
		String expRegNo = "";
		BlApplyVO rtnval = new BlApplyVO ();
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("BL_No","");
			tempItem.put("BL_Print_Url","");
//			rtnItemVal.add(tempItem);
			temp.put("Detail",tempItem);
			rtnVal.add(temp);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jObject.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnVal;
		}
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			ApiOrderListVO defaultApiOrderList = new ApiOrderListVO();
			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
			String decryptRtn ="";
			try {
				tempItem = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				jObject = jArray1.getJSONObject(index1);
				JSONArray jArray = jObject.getJSONArray("GoodsInfo");
				String newNno = new String();
				newNno = comnService.selectNNO();
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jObject.toString());
				parameters.put("wUserId",apiUserId);
				parameters.put("wUserIp",apiUserIp);
				parameters.put("connUrl",request.getServletPath());
				parameters.put("nno", newNno);
				parameters.put("orgStation",jObject.getString("Departure_Station"));
				parameters.put("dstnNation",jObject.getString("Arrival_Nation"));
				parameters.put("userId", apiUserId);
				transCodeByRemark = comnService.selectUserTransCode(parameters);
				parameters.put("transCode", transCodeByRemark);
				parameters.put("dstnNation", jObject.getString("Arrival_Nation"));
				mapper.insertApiConn(parameters); 
				int cnts = mapper.checkNation(parameters);
				if(cnts==0) {
					HashMap<String,Object> tempparameters = new HashMap<String,Object>();
					tempparameters.put("userId", apiUserId);
					tempparameters.put("orgStation",jObject.getString("Departure_Station"));
					tempparameters.put("dstnNation","DEF");
					if(mapper.selectDefaultTransCom(tempparameters) != null) {
						transCodeByRemark = mapper.selectDefaultTransCom(tempparameters);
					}else {
						temp.put("Status_Code","P10");
						temp.put("Error_Msg","배송가능 국가가 아닙니다. 데이터를 확인 해 주세요");
						throw new Exception();
					}
				}
				temp = serviceFunction.orderJsonColumnChk(jObject, transCodeByRemark); //컬럼명 필수만 확인
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);//값 필수만 확인
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("takein");
				if(jObject.has("BL_No")) {
					apiOrderList.setHawbNo(jObject.getString("BL_No"));
				}else if (jObject.has("Hwab_No")) {
					apiOrderList.setHawbNo(jObject.getString("Hwab_No"));
				}else if (jObject.has("Hawb_No")) {
					apiOrderList.setHawbNo(jObject.getString("Hawb_No"));
				}
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					apiOrderList.setOrderNo(jObject.getString("Order_No"));
				}
				apiOrderList.setTransCode(transCodeByRemark);
				apiOrderList = serviceFunction.setApiOrderListValue(apiOrderList, jObject, transCodeByRemark);
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				
				apiOrderList.setTransCode(transCodeByRemark);
				
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				
				serviceFunction.settingShipperInfo(apiOrderList, defaultApiOrderList);
				
				/*무게에 따른 TransComChg 시작*/
				String transCom= serviceFunction.transComChg(apiOrderList, transCodeByRemark);
    			apiOrderList.setTransCode(transCom);
				/*무게에 따른 TransComChg 끝*/	
				
				int jArrayLen = jArray.length();
				boolean itemCodeFlag = false;
				String failItemCode = "";
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						int chkTakeInCode = mapper.checkTakeInCode(apiUserId,itemObj.getString("Customer_Item_Code"));
						if(chkTakeInCode == 0) {
							temp.put("Status_Code","D10");
							failItemCode = itemObj.getString("Customer_Item_Code")+",";
							itemCodeFlag = true;
						}
					}
				}
				if(itemCodeFlag) {
					temp.put("Error_Msg","[Customer_Item_Code] IS NOT FOUND.("+failItemCode.substring(0, failItemCode.length()-1)+")");
					throw new Exception();
				}
				
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("cusItemCode", itemObj.get("Customer_Item_Code").toString());
					params.put("userId", apiOrderList.getUserId());
					TakeinInfoVO takeInItem = mapper.selectTakeInItem(params);
					temp = serviceFunction.itemJsonValueChk(itemObj, index, apiOrderList);
					
					if(temp.size()>0) {
//						parameters.put("rtnContents", tempRtnItemVal.toString());
//						mapper.updateApiConn(parameters);
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					apiOrderItem.setHsCode(takeInItem.getHsCode());
					apiOrderItem.setItemDetail(takeInItem.getItemDetail());
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(takeInItem.getUnitValue());
					apiOrderItem.setBrand(takeInItem.getBrand());
					apiOrderItem.setMakeCntry(takeInItem.getMakeCntry());
					apiOrderItem.setMakeCom(takeInItem.getMakeCom());
					apiOrderItem.setItemDiv(takeInItem.getItemDiv());
					apiOrderItem.setQtyUnit(takeInItem.getQtyUnit());
					apiOrderItem.setChgCurrency(takeInItem.getUnitCurrency());
					apiOrderItem.setWUserIp(apiUserIp);
//					apiOrderItem.setWDate(itemObj.getString("W_DATE"));
					apiOrderItem.setItemUrl(takeInItem.getItemUrl());
					apiOrderItem.setItemImgUrl(takeInItem.getItemImgUrl());
					apiOrderItem.setTrkCom("");
					apiOrderItem.setTrkNo("");
					apiOrderItem.setTrkDate("");
					apiOrderItem.setNativeItemDetail(takeInItem.getNativeItemDetail());
					apiOrderItemList.add(apiOrderItem);
				}
				if(mapper.selectOrderInfo(apiOrderList)!=0) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","Already registed data");
					throw new Exception();
				}
				if(orderSet.contains(apiOrderList.getOrderNo()+apiOrderList.getUserId())) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","This OrderNo is registered in logic");
					throw new Exception();
				}
				/* Aramex Api 결과*/

				applyTransfer(apiOrderList,apiOrderItemList, apiUserId, apiUserIp, transCodeByRemark, request, temp,expRegNo);
				
				temp.put("Error_Msg","-");
				if(!temp.containsKey("Status_Code")) {
					temp.put("Status_Code","A10");
				}
				
				if(jObject.has("Hold_Yn")) {
		 			parameters.put("hawbNo",apiOrderList.getHawbNo());
		 			String holdYn = jObject.getString("Hold_Yn");
		 			String reason = jObject.getString("Reason");
		 			if(holdYn.toLowerCase().equals("")) {
		 				holdYn = "N";
		 				reason = "";
		 			}
		 			parameters.put("holdYn",holdYn);
			 		parameters.put("remark",reason);
			 		rst = new HashMap<String,Object>();
			 		try {
			 		rst = mapper.execSpHoldBl(parameters);
			 		}catch (Exception e) {
						// TODO: handle exception
			 			temp.put("Status_Code","D10");
						temp.put("Error_Msg","Exception! SQL Error!!");
						fn_TransComApply_rollback(apiOrderList);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(apiOrderList);
						throw new Exception();
			 		}
		 		}
					
				if(!apiOrderList.getExpLicenceYn().equals("")) {
					ExpLicenceVO licence = new ExpLicenceVO();
					licence.setExpRegNo(expRegNo);
					if(apiOrderList.getExpNo() != null && !apiOrderList.getExpNo().equals(""))
						licence.setExpNo(apiOrderList.getExpNo());
					else
						licence.setExpNo("");
					licence.setOrderNo(apiOrderList.getOrderNo());
					licence.setNno(apiOrderList.getNno());
					licence.setExpBusinessNum(apiOrderList.getExpBusinessNum());
					licence.setExpShipperCode(apiOrderList.getExpShipperCode());
					licence.setExpBusinessName(apiOrderList.getExpBusinessName());
					licence.setSimpleYn(apiOrderList.getSimpleYn());
					mapper.insertExpBaseInfo(licence);
				}
				/* mapper.insertApiOrderList(apiOrderList); */
			}catch (Exception e) {

				if(e.getMessage()!=null) {
					temp.put("Status_Code","D10");
					temp.put("Error_Msg",e.getMessage());
				}
				
				// TODO: handle exception
				if(temp.get("Status_Code").equals("D20")) {
					
				}else if(temp.get("Status_Code").equals("P30")){
					
				}else if(temp.get("Status_Code").equals("P20")){
					
				}else if(temp.get("Status_Code").equals("P10")){
					
				}else if(temp.get("Status_Code").equals("ARA")){
					
				}else if(temp.get("Status_Code").equals("D30")){
					
				}
			}finally {
				if(temp.get("Status_Code").equals("A10")) {
					String hawbNo = rtnval.getHawbNo();
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			 		if(transCodeByRemark.equals("ARA")) {
			 			tempItem.put("BL_No",apiResult.getShipments()[0].getID());
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
						hawbNo = apiOrderList.getHawbNo();
			 		}else if(transCodeByRemark.equals("ACI")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+hawbNo);
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		}else {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
			 			tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		}
			 		
				}else {
					if(mapper.selectOrderInfo(apiOrderList)!=0) {
						ApiOrderListVO TempApiOrderList = new ApiOrderListVO();
						TempApiOrderList = mapper.selectAlreadyOrder(apiOrderList.getUserId(),apiOrderList.getOrderNo());
						String wDate = TempApiOrderList.getWDate();
						String year = wDate.substring(0,4);
						String month = wDate.substring(4,6);
						String day = wDate.substring(6,8);
						wDate = year+"-"+month+"-"+day;
						String week = Integer.toString(getWeekOfYear(wDate));
						tempItem.put("BL_No",TempApiOrderList.getHawbNo());
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+TempApiOrderList.getHawbNo());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+TempApiOrderList.getHawbNo());
					}else {
						tempItem.put("BL_No","");
						tempItem.put("BL_Print_Url","");
						tempItem.put("BL_Pod_Url","");
					}
					
				}
				//rtnItemVal.add(tempItem);
				
				temp.put("Detail",tempItem);
				if(jObject.has("Order_Number")) {
					temp.put("Order_No",jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					temp.put("Order_No",jObject.getString("Order_No"));
				}
				parameters.put("rtnContents", temp.toString());
				mapper.updateApiConn(parameters);
				rtnVal.add(temp);
			}
		}
		return rtnVal;
	}
	
	@Override
	public ArrayList<Map<String, Object>> insertOrderNomal(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		String transCodeByRemark = "";
		String expRegNo = "";
		BlApplyVO rtnval = new BlApplyVO ();
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("BL_No","");
			tempItem.put("BL_Print_Url","");
//			rtnItemVal.add(tempItem);
			temp.put("Detail",tempItem);
			rtnVal.add(temp);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jObject.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnVal;
		}
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			ApiOrderListVO defaultApiOrderList = new ApiOrderListVO();
			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
			String decryptRtn ="";
			try {
				tempItem = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				jObject = jArray1.getJSONObject(index1);
				JSONArray jArray = jObject.getJSONArray("GoodsInfo");
				String newNno = new String();
				newNno = comnService.selectNNO();
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jObject.toString());
				parameters.put("wUserId",apiUserId);
				parameters.put("wUserIp",apiUserIp);
				parameters.put("connUrl",request.getServletPath());
				parameters.put("nno", newNno);
				parameters.put("orgStation",jObject.getString("Departure_Station"));
				parameters.put("dstnNation",jObject.getString("Arrival_Nation"));
				parameters.put("userId", apiUserId);
				transCodeByRemark = comnService.selectUserTransCode(parameters);
				parameters.put("transCode", transCodeByRemark);
				parameters.put("dstnNation", jObject.getString("Arrival_Nation"));
				mapper.insertApiConn(parameters); 
				int cnts = mapper.checkNation(parameters);
				if(cnts==0) {
					HashMap<String,Object> tempparameters = new HashMap<String,Object>();
					tempparameters.put("userId", apiUserId);
					tempparameters.put("orgStation",jObject.getString("Departure_Station"));
					tempparameters.put("dstnNation","DEF");
					if(mapper.selectDefaultTransCom(tempparameters) != null) {
						transCodeByRemark = mapper.selectDefaultTransCom(tempparameters);
					}else {
						temp.put("Status_Code","P10");
						temp.put("Error_Msg","배송가능 국가가 아닙니다. 데이터를 확인 해 주세요");
						throw new Exception();
					}
				}
				
				temp = serviceFunction.orderJsonColumnChk(jObject, transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1, transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				//체크 X 구간
				defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrderType("NOMAL");
				apiOrderList.setUserId(apiUserId);
				if(jObject.has("BL_No")) {
					apiOrderList.setHawbNo(jObject.getString("BL_No"));
				}else if (jObject.has("Hwab_No")) {
					apiOrderList.setHawbNo(jObject.getString("Hwab_No"));
				}else if (jObject.has("Hawb_No")) {
					apiOrderList.setHawbNo(jObject.getString("Hawb_No"));
				}
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					apiOrderList.setOrderNo(jObject.getString("Order_No"));
				}
				apiOrderList.setTransCode(transCodeByRemark);
				//체크 X 구간
				
				apiOrderList = serviceFunction.setApiOrderListValue(apiOrderList, jObject, transCodeByRemark);
				
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				
				serviceFunction.settingShipperInfo(apiOrderList,defaultApiOrderList);
				

				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}

				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				
				/*무게에 따른 TransComChg 끝*/
    			String transCom= serviceFunction.transComChg(apiOrderList, transCodeByRemark);
    			apiOrderList.setTransCode(transCom);
    			/*무게에 따른 TransComChg 끝*/
    			
    			
    			
				int jArrayLen = jArray.length();
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					temp = serviceFunction.itemJsonColumnChk(itemObj, apiOrderList);
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					temp = serviceFunction.itemJsonValueChk(itemObj, index1,apiOrderList);
					
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					apiOrderItem = serviceFunction.setApiOrderItemValue(apiOrderItem, itemObj, apiOrderList.getTransCode());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					apiOrderItem.setTransCode(apiOrderList.getTransCode());
					apiOrderItemList.add(apiOrderItem);
				}
				if(mapper.selectOrderInfo(apiOrderList)!=0) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","Already registed data");
					throw new Exception();
				}
				if(orderSet.contains(apiOrderList.getOrderNo()+apiOrderList.getUserId())) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","The OrderNo is registered in the logic.");
					throw new Exception();
				}
				
				/* Aramex Api 결과*/
				applyTransfer(apiOrderList,apiOrderItemList, apiUserId, apiUserIp, transCodeByRemark, request, temp,expRegNo);
				
				/* apiResult */
				temp.put("Error_Msg","-");
				if(!temp.containsKey("Status_Code")) {
					temp.put("Status_Code","A10");
				}
				
		 		if(jObject.has("Hold_Yn")) {
		 			parameters.put("hawbNo",apiOrderList.getHawbNo());
		 			String holdYn = jObject.getString("Hold_Yn");
		 			String reason = jObject.getString("Reason");
		 			if(holdYn.toLowerCase().equals("")) {
		 				holdYn = "N";
		 				reason = "";
		 			}
		 			parameters.put("holdYn",holdYn);
			 		parameters.put("remark",reason);
			 		rst = new HashMap<String,Object>();
			 		try {
			 		rst = mapper.execSpHoldBl(parameters);
			 		}catch (Exception e) {
						// TODO: handle exception
			 			temp.put("Status_Code","D10");
						temp.put("Error_Msg","Exception! SQL Error!!");
						fn_TransComApply_rollback(apiOrderList);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(apiOrderList);
						throw new Exception();
			 		}
		 		}
					
		 		if(!apiOrderList.getExpLicenceYn().equals("")) {
					ExpLicenceVO licence = new ExpLicenceVO();
					licence.setExpRegNo(expRegNo);
					if(apiOrderList.getExpNo() != null && !apiOrderList.getExpNo().equals(""))
						licence.setExpNo(apiOrderList.getExpNo());
					else
						licence.setExpNo("");
					licence.setOrderNo(apiOrderList.getOrderNo());
					licence.setNno(apiOrderList.getNno());
					licence.setExpBusinessNum(apiOrderList.getExpBusinessNum());
					licence.setExpShipperCode(apiOrderList.getExpShipperCode());
					licence.setExpBusinessName(apiOrderList.getExpBusinessName());
					licence.setSimpleYn(apiOrderList.getSimpleYn());
					licence.setExpValue(apiOrderList.getExpValue());
					mapper.insertExpBaseInfo(licence);
				}
				/* mapper.insertApiOrderList(apiOrderList); */
			}catch (Exception e) {
				if(e.getMessage()!=null) {
					temp.put("Status_Code","D10");
					temp.put("Error_Msg",e.getMessage());
				}
				
				// TODO: handle exception
				if(temp.get("Status_Code").equals("D20")) {
					
				}else if(temp.get("Status_Code").equals("P30")){
					
				}else if(temp.get("Status_Code").equals("P20")){
					
				}else if(temp.get("Status_Code").equals("P10")){
					
				}else if(temp.get("Status_Code").equals("ARA")){
					
				}else if(temp.get("Status_Code").equals("D30")){
					
				}
				
			}finally {
				if(temp.get("Status_Code").equals("A10")) {
					String hawbNo = rtnval.getHawbNo();
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			 		if(transCodeByRemark.equals("ARA")) {
			 			tempItem.put("BL_No",apiResult.getShipments()[0].getID());
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
						hawbNo = apiOrderList.getHawbNo();
			 		}else if(transCodeByRemark.equals("ACI")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+hawbNo);
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		}else {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
			 			if(!apiOrderList.getTransCode().equals("SEK")) {
							tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiOrderList.getHawbNo());
						}else {
							tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+"1489353"+"-"+apiOrderList.getHawbNo());
						}
			 			tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		}
			 		
				}else {
					if(mapper.selectOrderInfo(apiOrderList)!=0) {
						ApiOrderListVO TempApiOrderList = new ApiOrderListVO();
						TempApiOrderList = mapper.selectAlreadyOrder(apiOrderList.getUserId(),apiOrderList.getOrderNo());
						String wDate = TempApiOrderList.getWDate();
						String year = wDate.substring(0,4);
						String month = wDate.substring(4,6);
						String day = wDate.substring(6,8);
						wDate = year+"-"+month+"-"+day;
						String week = Integer.toString(getWeekOfYear(wDate));
						tempItem.put("BL_No",TempApiOrderList.getHawbNo());
						if(!apiOrderList.getTransCode().equals("SEK")) {
							tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+TempApiOrderList.getHawbNo());
						}else {
							tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+"1489353"+"-"+TempApiOrderList.getHawbNo());
						}
						
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+TempApiOrderList.getHawbNo());
					}else {
						tempItem.put("BL_No","");
						tempItem.put("BL_Print_Url","");
						tempItem.put("BL_Pod_Url","");
					}
					
				}
				//rtnItemVal.add(tempItem);
				temp.put("Detail",tempItem);
				if(jObject.has("Order_Number")) {
					temp.put("Order_No",jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					temp.put("Order_No",jObject.getString("Order_No"));
				}
				parameters.put("rtnContents", temp.toString());
				mapper.updateApiConn(parameters);
				rtnVal.add(temp);
			}
		}
		return rtnVal;
	}
	
	@Override
	public ArrayList<Map<String, Object>> updateOrderInsp(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("BL_No","");
			tempItem.put("BL_Print_Url","");
//			rtnItemVal.add(tempItem);
			temp.put("Detail",tempItem);
			rtnVal.add(temp);
			return rtnVal;
		}
		
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
			String decryptRtn ="";
			try {
				tempItem = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				jObject = jArray1.getJSONObject(index1);
				JSONArray jArray = jObject.getJSONArray("GoodsInfo");
				String regarcyNno = mapper.selectApiOrderNNO(jObject.getString("Order_Number"),apiUserId);
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jObject.toString());
				parameters.put("wUserId",apiUserId);
				parameters.put("wUserIp",apiUserIp);
				parameters.put("connUrl",request.getServletPath());
				parameters.put("nno", regarcyNno);
				mapper.insertApiConn(parameters); 

				temp = serviceFunction.orderJsonColumnChk(jObject, "");
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,"");
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				apiOrderList.setNno(regarcyNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("INSP");
				apiOrderList.setOrderNo(jObject.getString("Order_Number"));
				apiOrderList.setBoxCnt((String)jObject.get("Box_Count"));
				apiOrderList.setHawbNo(jObject.getString("Hawb_No"));
				apiOrderList.setUserWta(jObject.getString("Actual_Weight"));
				apiOrderList.setUserWtc(jObject.getString("Volume_Weight"));
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("SHIPPER_HP"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("BuySite"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				apiOrderList.setCneeState(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.getString("Volume_Length"));
				apiOrderList.setUserWidth(jObject.getString("Volume_Width"));
				apiOrderList.setUserHeight(jObject.getString("Volume_Height"));
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				/* apiOrderList.setUserEmail(jObject.getString("USER_EMAIL")); */
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				apiOrderList.setTransCode(jObject.getString("Transfer_Company_Code"));
				//여기까지 작업함 이후 작업해야함 까먹지말 것
				if(Pattern.matches(YYYYMMDD, jObject.getString("Order_Date"))) {
					apiOrderList.setOrderDate(jObject.getString("Order_Date"));
				}else {
					Date time = new Date();
					String time1 = format1.format(time);
					apiOrderList.setOrderDate(time1);
				}
				apiOrderList.setCneeEmail(jObject.getString("Receiver_Email"));
				apiOrderList.setCustomsNo(jObject.getString("Custom_Clearance_ID"));
				apiOrderList.setNativeCneeName(jObject.getString("Native_Receiver_Name"));
				apiOrderList.setNativeCneeAddr(jObject.getString("Native_Receiver_Address"));
				apiOrderList.setNativeCneeAddrDetail(jObject.getString("Native_Receiver_Address_Detail"));
				apiOrderList.setDimUnit(jObject.getString("Size_Unit"));
				apiOrderList.setWtUnit(jObject.getString("Weight_Unit"));
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				int jArrayLen = jArray.length();
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					}
					
					temp = serviceFunction.itemJsonValueChk(itemObj, index1,apiOrderList);
					
					if(temp.size()>0) {
						temp.put("Status_Code","P20");
						throw new Exception();
					} 
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setOrderNo(apiOrderList.getOrderNo());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.getString("Item_Count"));
					apiOrderItem.setUnitValue(itemObj.getString("Unit_Value"));
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
//					apiOrderItem.setWtUnit(itemObj.getString("WT_UNIT"));
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
//					apiOrderItem.setPackageUnit(itemObj.getString("PACKAGE_UNIT"));
//					apiOrderItem.setExchangeRate(itemObj.getString("EXCHANGE_RATE"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
//					apiOrderItem.setChgAmt(itemObj.getString("CHG_AMT"));
//					apiOrderItem.setChnItemDetail(itemObj.getString("CHN_ITEM_DETAIL"));
					/* apiOrderItem.setItemMeterial(itemObj.getString("Item_Meterial")); */
//					apiOrderItem.setTakeInCode(itemObj.getString("TAKE_IN_CODE"));
//					apiOrderItem.setUserWtaItem(itemObj.getString("USER_WTA"));
//					apiOrderItem.setUserWtcItem(itemObj.getString("USER_WTC"));
//					apiOrderItem.setWUserId(itemObj.getString("W_USER_ID")); 
					apiOrderItem.setWUserIp(apiUserIp);
//					apiOrderItem.setWDate(itemObj.getString("W_DATE"));
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
					apiOrderItemList.add(apiOrderItem);
				}
				apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
				apiOrderList.encryptData();
				mapper.deleteApiOrderItem(regarcyNno);
				if(updateApiOrderListQueue(apiOrderList,apiOrderItemList)) {
					
					throw new Exception();
				}
				
				
				apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
				apiOrderList.encryptData();
//				updateApiOrderListQueue(apiOrderList,apiOrderItemList);
				temp.put("Error_Msg","-");
				if(!temp.containsKey("Status_Code")) {
					temp.put("Status_Code","A10");
				}
				
				
			}catch (Exception e) {
				// TODO: handle exception
				if(temp.get("Status_Code").equals("P10")) {
					
				}else if(temp.get("Status_Code").equals("P20")) {
					
				}else if(temp.get("Status_Code").equals("P30")) {
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
				}else {
					temp.put("Status_Code","D10");
					temp.put("Error_Msg","NO Data");
				}
			}finally {
				String wDate = mapper.selectWdate(apiOrderList.getHawbNo());
				String year = wDate.substring(0,4);
				String month = wDate.substring(4,6);
				String day = wDate.substring(6,8);
				wDate = year+"-"+month+"-"+day;
				String week = Integer.toString(getWeekOfYear(wDate));
				tempItem.put("BL_No",apiOrderList.getHawbNo());
				tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiOrderList.getHawbNo());
//				rtnItemVal.add(tempItem);
				parameters.put("rtnContents", tempItem.toString());
				mapper.updateApiConn(parameters);
				temp.put("Detail",tempItem);
				temp.put("Order_No",apiOrderList.getOrderNo());
				
				rtnVal.add(temp);
			}
		}
		return rtnVal;
	}
	
	@Override
	public ArrayList<Map<String, Object>> stockOutStatus(Map<String, Object> jsonHeader, String date, HttpServletRequest request, String userKey) throws Exception {
		ArrayList<Map<String, Object>> rtnStockOutStatus = new ArrayList<Map<String, Object>>();   
		ArrayList<Map<String, Object>> rmtStockOutStatusList = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> rmtItemInfoList = new ArrayList<Map<String, Object>>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("Dep_Date","");
	        tempItem.put("Wta","");
	        tempItem.put("TRANS_CODE","");
	        tempItem.put("Wtc","");
	        tempItem.put("USER_ID","");
	        tempItem.put("Hawb_No","");
	        tempItem.put("Order_No","");
			rmtStockOutStatusList.add(tempItem);
			temp.put("Detail",rmtStockOutStatusList);
			rtnStockOutStatus.add(temp);
			
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", date);
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnStockOutStatus;
		}
		rmtStockOutStatusList = mapper.selectStockOutStatus(apiUserId,date);
		//rmtItemInfoList= mapper.selectStockOutStatus(request.getHeader("userid"),date);
		
		
	  for(int index = 0 ; index < rmtStockOutStatusList.size() ; index++) {
		  String nno = (String) rmtStockOutStatusList.get(index).get("NNO");
		   rmtItemInfoList= mapper.selectItemWhoutInfo(apiUserId,nno);
		   rmtStockOutStatusList.get(index).put("ItemInfo", rmtItemInfoList);
		   rmtStockOutStatusList.get(index).remove("NNO");
	  }

		temp.put("Status","SUCCESS");
		temp.put("Status_Code","A10");
		if(rmtStockOutStatusList.isEmpty()) {
			tempItem.put("Dep_Date","");
	        tempItem.put("Wta","");
	        tempItem.put("TRANS_CODE","");
	        tempItem.put("Wtc","");
	        tempItem.put("USER_ID","");
	        tempItem.put("Hawb_No","");
	        tempItem.put("Order_No","");
	        rmtStockOutStatusList.add(tempItem);
		}
		temp.put("Detail",rmtStockOutStatusList);
		rtnStockOutStatus.add(temp);
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", date);
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", temp.toString());
		mapper.insertApiConn(parameters); 
		return rtnStockOutStatus;
	}

	@Override
	public ArrayList<Map<String, Object>> stockInStatus(Map<String, Object> jsonHeader, String date, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> rtnStockOutStatus = new ArrayList<Map<String, Object>>();   
		ArrayList<Map<String, Object>> rmtStockOutStatusList = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> rmtStockOutDetailList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		
//		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("Dep_Date","");
	        tempItem.put("Wta","");
	        tempItem.put("TRANS_CODE","");
	        tempItem.put("Wtc","");
	        tempItem.put("USER_ID","");
	        tempItem.put("Hawb_No","");
	        tempItem.put("Order_No","");
			rmtStockOutStatusList.add(tempItem);
			temp.put("Detail",rmtStockOutStatusList);
			rtnStockOutStatus.add(temp);
			
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", date);
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnStockOutStatus;
		}

		rmtStockOutStatusList = mapper.selectStockInStatus(apiUserId,date);
		for(int index = 0 ; index < rmtStockOutStatusList.size() ; index++) {
			String groupIdx = (String) rmtStockOutStatusList.get(index).get("GroupStockNum");
			rmtStockOutDetailList = mapper.selectStockInDetatil(request.getHeader("userid"),groupIdx);
			rmtStockOutStatusList.get(index).put("Stock_Detail",rmtStockOutDetailList);
		}
		
		rmtStockOutDetailList = mapper.selectStockInStatus(apiUserId,date);
		temp.put("Status","SUCCESS");
		temp.put("Status_Code","A10");

		temp.put("Detail",rmtStockOutStatusList);
		rtnStockOutStatus.add(temp);
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", date);
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", temp.toString());
		mapper.insertApiConn(parameters);
		return rtnStockOutStatus;
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

	@Override
	public ArrayList<LinkedHashMap<String, Object>> blPod(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {

		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if (!temp.isEmpty()) {
			Iterator iter = temp.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				RstHashMap.put(key, temp.get(key));
			}
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			mapper.insertApiConn(parameters);
			return rtnJsonArray;
		}

		HashMap<String, Object> bl = new HashMap<String, Object>();

		// JrUGT5p4c9ZmO75BNlD7XjrLn9z96ouRnWWXhi1WcDE=
		if(jArrayData.length()>50) {
			
			RstHashMap.put("Error", "-900");
			RstHashMap.put("ErrorMsg", "요청 개수가 너무 많습니다. (최대 50)");
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			
			
			return rtnJsonArray;
		}
		for (int trkIndex = 0; trkIndex < jArrayData.length(); trkIndex++) {

			JSONObject itemObj = jArrayData.getJSONObject(trkIndex);
			if(itemObj.has("bl")) {
				bl.put("BL", itemObj.get("bl"));
			}else if(itemObj.has("BL")) {
				bl.put("BL", itemObj.get("BL"));
			}else if(itemObj.has("Bl")) {
				bl.put("BL", itemObj.get("Bl"));
			}else if(itemObj.has("BL_No")) {
				bl.put("BL", itemObj.get("BL_No"));
			}else if(itemObj.has("BL_NO")) {
				bl.put("BL", itemObj.get("BL_NO"));
			}
			//bl.put("BL", itemObj.get("bl"));
			RstHashMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> podDetatil = new LinkedHashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray = new ArrayList<HashMap<String, Object>>();

			
			String HawbNo = (String) bl.get("BL");
			if (HawbNo.equals("") || HawbNo == null) {
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				rtnJsonArray.add(RstHashMap);
				continue;
			}
			
			String hawbInDate = mapper.selectHawbInDate(HawbNo);	// 입고
			String mawbInDate = mapper.selectMawbInDate(HawbNo);	// 출고
			String regInDate = mapper.selectRegInDate(HawbNo);		// 주문등록

			HashMap<String, Object> BlInfo = new HashMap<String, Object>();

			BlInfo = mapper.selectPodBlInfo(HawbNo);
			if (BlInfo == null) {
				podDetatil = new LinkedHashMap<String, Object>();
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				rtnJsonArray.add(RstHashMap);
				continue;
			}

			RstHashMap.put("Bl", BlInfo.get("HAWB_NO"));
			RstHashMap.put("OrderNo", BlInfo.get("ORDER_NO"));
			RstHashMap.put("Consignee", BlInfo.get("CNEE_NAME"));
			RstHashMap.put("WarehousingDate", BlInfo.get("WH_IN_DATE"));
			RstHashMap.put("ShippingDate", BlInfo.get("DEP_DATE"));
			RstHashMap.put("DeliveryCompany", BlInfo.get("DeliveryCompany"));

			String getTransCode = "";
			getTransCode = (String) BlInfo.get("TRANS_CODE");

			if (getTransCode.equals("SAGAWA")) {

			} else if (getTransCode.equals("ARA")) {
				TrackingClientInfo clientInfo = new TrackingClientInfo();
				clientInfo.setAccountCountryCode("KR");
				clientInfo.setAccountEntity("SEL");
				clientInfo.setAccountNumber("172813");
				clientInfo.setAccountPin("321321");
				clientInfo.setUserName("overseas2@aciexpress.net");
				clientInfo.setPassword("Aci5606!");
				clientInfo.setVersion("1.0");
				// shipments.setClientInfo(clientInfo);

				net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
				transaction.setReference1("001");

				TrackingShipmentRequest trakShip = new TrackingShipmentRequest();

				trakShip.setClientInfo(clientInfo);
				trakShip.setTransaction(transaction);

				String[] trakBL = new String[1];
				trakBL[0] = HawbNo;

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
					podDetatil.put("ProblemCode", "-22");
					podDetatil.put("Comments", "No Data.");
					podDetatailArray.add(podDetatil);
				} else {
					trkRst = rtnTracking.getTrackingResults()[0].getValue();
					for (int i = 0; i < trkRst.length; i++) {
						// System.out.println(rtnTracking.getTrackingResults()[i].getKey());
						podDetatil = new LinkedHashMap<String, Object>();
						/*
						podDetatil.put("UpdateCode", trkRst[i].getUpdateCode());
						podDetatil.put("UpdateDateTime", trkRst[i].getUpdateDateTime());
						podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
						podDetatil.put("UpdateDescription", trkRst[i].getUpdateDescription());
						podDetatil.put("ProblemCode", trkRst[i].getProblemCode());
						podDetatil.put("Comments", trkRst[i].getComments());
						*/
						
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
					
					//HawbNo
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
				//String rtnJson = ysApi.makeYoungSungPodKR(HawbNo);
				//String rtnJson2 = ysApi.makeYoungSungPodEN(HawbNo);
				//podDetatailArray = ysApi.makePodDetatailArray(rtnJson, rtnJson2, HawbNo, request);
				podDetatailArray = ysApi.processYslPod(HawbNo, request);
				//podDetatailArray = ysApi.makePodDetailForArray(rtnJson2, HawbNo, request);

			} else if (getTransCode.equals("OCS")) {
				String rtnJson = ocsApi.makeOCSPod(HawbNo);
				podDetatailArray = ocsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("EFS")) {
				String rtnJson = efsApi.makeEfsPod(HawbNo);
				podDetatailArray = efsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("SEK")) {
				podDetatailArray = sekoApi.makeSekoPod(HawbNo);
			} else if (getTransCode.equals("CJ")) {
				podDetatailArray = cjapi.makePodDetailForArray(HawbNo);
			} else if (getTransCode.equals("ACI-US")) {
				String podType = "A";
				String subTransCode = comnService.selectSubTransCode(HawbNo);
				String rtnJson = "";
				if (subTransCode.equals("ACI-T86")) {
					rtnJson = t86Api.makeT86Pod(HawbNo);
					podDetatailArray = t86Api.makePodDetailArray(rtnJson, HawbNo, request);
				} else if (subTransCode.equals("PARCLL")) {
					podDetatailArray =  prclApi.makeParcllPod(HawbNo, request, podType);
				}
			} else if (getTransCode.equals("EMN")) {
				podDetatailArray = emsApi.makeEmsForPod(HawbNo);
			}
			
			RstHashMap.put("TraceStatus", podDetatailArray);

			rtnJsonArray.add(RstHashMap);
		}
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId", apiUserId);
		parameters.put("wUserIp", apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", rtnJsonArray.toString());
		mapper.insertApiConn(parameters);
		return rtnJsonArray;
	}

	@Override
	public void sendFedexApi(HttpServletRequest request, HttpServletResponse response, String nno) throws Exception {
		//sendFedexApiSmart(request, nno);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public BlApplyVO fn_TransComApply(String transCodeByRemark, ApiOrderListVO apiOrderList, ArrayList<ApiOrderItemListVO> apiOrderItemList, HttpServletRequest request) {
		BlApplyVO rtn = new BlApplyVO();
		ProcedureVO rtnVal = new ProcedureVO();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", apiOrderList.getNno());
		parameterInfo.put("userId", apiOrderList.getWUserId());
		parameterInfo.put("userIp", apiOrderList.getWUserIp());
		parameterInfo.put("transCode", transCodeByRemark);
		ShipmentVO shipment = new ShipmentVO();
		try {
			mapper.insertUserOrderList(apiOrderList);
			
			for(int i = 0 ; i < apiOrderItemList.size(); i++) {
				mapper.insertUserOrderItem(apiOrderItemList.get(i));
			}

			//userOrderList.encryptData();
			switch (transCodeByRemark) {
		    case "ACI":
		        // a-z
				/* usrService.insertUserOrderListSagawa(userOrderList, userOrderItemList); */
		    	rtn = comnService.selectBlApply(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp());
		        break;
		    case "SEK":
		        // a-z
				/* usrService.insertUserOrderListSagawa(userOrderList, userOrderItemList); */
		    	Map<String,Object> sekRtn = new HashMap<String,Object>();
		    	sekRtn = sekoApi.selectBlApplySEK(apiOrderList.getNno(), apiOrderList.getWUserId(), apiOrderList.getWUserIp());
		    	if(sekRtn.get("rstCode").equals("SUCCESS")) {
		    		rtn.setStatus("SUCCESS");
			    	rtn.setHawbNo(sekRtn.get("rstHawbNo").toString());
			    	rtn.setRstMsg("-");
			    	rtn.setRstCode("A10");
			    	
		    	}else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo(sekRtn.get("rstHawbNo").toString());
			    	rtn.setRstMsg(sekRtn.get("rstErrorMsg").toString());
			    	rtn.setRstCode("D10");
		    	}
		    	
		        break;
		    case "ACI-US":
		    	rtn = comnService.selectBlApply(apiOrderList.getNno(), apiOrderList.getWUserId(), apiOrderList.getWUserIp());
		    	break;
		    case "FB":
		    	rtnVal = fastboxApi.selectBlApplyforFB(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp(), transCodeByRemark);
		    	if (!rtnVal.getRstStatus().equals("FAIL")) {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(rtnVal.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo(rtnVal.getRstHawbNo());
			    	rtn.setRstMsg(rtnVal.getRstMsg());
			    	rtn.setRstCode("D10");
		    	}
		    	break;
		    case "FB-EMS":
		    	rtnVal = fastboxApi.selectBlApplyforFB(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp(), transCodeByRemark);
		    	if (!rtnVal.getRstStatus().equals("FAIL")) {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(rtnVal.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo(rtnVal.getRstHawbNo());
			    	rtn.setRstMsg(rtnVal.getRstMsg());
			    	rtn.setRstCode("D10");
		    	}
		    	break;
		    case "HJ":
		    	rtnVal = hjApi.selectBlApply(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp());
		    	if (!rtnVal.getRstStatus().equals("FAIL")) {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(rtnVal.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo(rtnVal.getRstHawbNo());
			    	rtn.setRstMsg(rtnVal.getRstMsg());
			    	rtn.setRstCode("D10");
		    	}
		    	break;
		    case "EPT":
		    	String requestVal = epost.createRequestBody(parameterInfo);
		    	shipment = epost.createShipment(parameterInfo, requestVal);
		    	if (shipment.getRstStatus().equals("SUCCESS")) {
		    		parameterInfo.put("hawbNo", shipment.getRstHawbNo());
		    		comnService.createParcelBl(parameterInfo);
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(shipment.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
		    		rtn.setHawbNo("");
		    		rtn.setRstMsg(shipment.getRstMsg());
		    		rtn.setRstCode("D10");
		    	}
		    	break;
		    case "YT":
		    	String jsonVal = yunApi.createRequestVal(apiOrderList.getNno());
		    	rtnVal = yunApi.createShipment(apiOrderList.getNno(), apiOrderList.getWUserId(), apiOrderList.getWUserIp(), jsonVal);
		    	if (rtnVal.getRstStatus().equals("SUCCESS")) {
		    		yunApi.createLabel(rtnVal.getRstHawbNo(), apiOrderList.getNno(), apiOrderList.getWUserId(), apiOrderList.getWUserIp());
		    		parameterInfo.put("hawbNo", rtnVal.getRstHawbNo());
		    		comnService.createParcelBl(parameterInfo);
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(rtnVal.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo("");
			    	rtn.setRstMsg(shipment.getRstMsg());
			    	rtn.setRstCode("D10");
		    	}
		    	break;
		    case "YSL":
		    	requestVal = jsonObjectToString(yongsung.createRequestBody(parameterInfo));
		    	shipment = yongsung.createShipment(parameterInfo, requestVal);
		    	if (shipment.getRstStatus().equals("SUCCESS")) {
		    		parameterInfo.put("hawbNo", shipment.getRstHawbNo());
		    		comnService.createParcelBl(parameterInfo);
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(shipment.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
		    		rtn.setHawbNo("");
		    		rtn.setRstMsg(shipment.getRstMsg());
		    		rtn.setRstCode("D10");
		    	}
		    	break;
		    case "CJ":
		    	Order order = new Order();
		    	order.setNno(apiOrderList.getNno());
		    	order.setWUserId(apiOrderList.getWUserId());
		    	order.setWUserIp(apiOrderList.getWUserIp());
		    	order.setHawbNo("");
		    	
		    	ProcedureRst procRst = logisticsService.registShipment(order);
		    	System.out.println(procRst);
		    	System.out.println(procRst.getRstMsg());
		    	if ("FAIL".equals(procRst.getRstStatus())) {
		    		rtn.setStatus("FAIL");
		    		rtn.setHawbNo("");
		    		rtn.setRstMsg(procRst.getRstMsg());
		    		rtn.setRstCode("D30");
		    	} else {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(procRst.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	}
		    	break;
		    case "VNP":
		    	parameterInfo.put("orderType", apiOrderList.getOrderType().toUpperCase());
		    	ProcedureRst shipmentRst = new ProcedureRst();
		    	Order apiOrder = new Order();
		    	apiOrder = logisticsMapper.selectTmpOrder(parameterInfo);
		    	apiOrder.dncryptData();
		    	apiOrder = vnpHandler.createOrder(apiOrder);
		    	if (apiOrder.getStatus().isEmpty()) {
		    		shipmentRst = logisticsMapper.execSpRegShipment(apiOrder);
		    		if ("SUCCESS".equals(shipmentRst.getRstStatus().toUpperCase())) {
						commUtils.storeLabelFile(apiOrder);
						rtn.setStatus("SUCCESS");
						rtn.setHawbNo(apiOrder.getHawbNo());
						rtn.setRstMsg("-");
						rtn.setRstCode("A10");
					} else {
						rtn.setStatus("FAIL");
						rtn.setHawbNo("");
						rtn.setRstMsg(shipmentRst.getRstMsg());
						rtn.setRstCode("D30");
					}
		    	} else {
					rtn.setStatus("FAIL");
					rtn.setHawbNo("");
					rtn.setRstMsg(apiOrder.getStatus());
					rtn.setRstCode("D30");
		    	}
		    	break;
	        default:
	        	rtn = comnService.selectBlApply(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp());
	        	break;
		    }
		}catch (Exception e) {
			logger.error("Exception", e);
		}
		return rtn;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void fn_TransComApply_rollback(ApiOrderListVO apiOrderList) throws Exception {
		mapper.deleteApiOrderList(apiOrderList.getNno());
		mapper.deleteApiOrderItem(apiOrderList.getNno());
	}
	
	@Override
	public ArrayList<ApiShopifyInfoVO> selectStoreUrl(String orgStation) throws Exception {
		return shopifyApi.selectStoreUrl(orgStation);
	}

	@Override
	public String shopifyOrderListGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		return shopifyApi.shopifyOrderListGet(apiShopifyInfoVO,httpRequest,httpResponse);
	}
	
	@Override
	public String shopifyOrderListFulfilUpdate(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		return shopifyApi.shopifyOrderListFulfilUpdate(apiShopifyInfoVO,httpRequest, httpResponse);
	}
	
	@Override
	public String shopifyOrderListFulfilUpdateUSPS(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		return shopifyApi.shopifyOrderListFulfilUpdateUSPS(apiShopifyInfoVO,httpRequest, httpResponse);
	}


	@Override
	public ArrayList<LinkedHashMap<String, Object>> insertResgtItemCode(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		String errorMsg = "";
		String itemCode = "";

		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", RstHashMap.toString());
			
			RstHashMap.put("Customer_Item_Code", "-");
			RstHashMap.put("Status", "ERROR");
			RstHashMap.put("Result", temp.toString());
			rtnJsonArray.add(RstHashMap);
			mapper.insertApiConn(parameters); 
			
			return rtnJsonArray;
		}
		
		parameters = new HashMap<String,Object>();
		
		for (int Index = 0 ; Index < jArrayData.length() ;  Index++) {
			try {
				errorMsg = "";
				RstHashMap = new LinkedHashMap<String,Object>();
				JSONObject itemObj = jArrayData.getJSONObject(Index);
				Iterator iter = itemObj.keySet().iterator();
				String json = "";
				while (iter.hasNext()) {
					String key = (String) iter.next();
					if("".equals(itemObj.get(key)) || itemObj.get(key) == null) {
						errorMsg += "["+key+"]";
					}
				}
				
				if(errorMsg.length()>0) {
					errorMsg += "IS EMPTY OR NULL";
					throw new Exception();
				}
				
				itemCode = itemObj.getString("Customer_Item_Code");
				parameters.put("cusItemCode", itemCode);
				parameters.put("itemDetail", itemObj.getString("Item_Detail"));
				parameters.put("nativeItemDetail", itemObj.getString("Item_Native_Detail"));
				parameters.put("makeCntry", itemObj.getString("Make_Country"));
				parameters.put("itemImgUrl", itemObj.getString("Item_Url"));
				if(apiUserId.contains("test_")) {
					apiUserId = apiUserId.replace("test_", "");
				}
				parameters.put("userId", apiUserId);
				if(!mapper.cusItemCodeExist(parameters)) {
					
					ysApi.insertCusItemCode(parameters);
					
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "SUCCESS");
					RstHashMap.put("Result", itemCode+" Regist.");
				}else {
					mapper.updateCusItemCode(parameters);
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "UPDATE");
					RstHashMap.put("Result", "UPDATE "+itemCode+" ITEM INFO.");
				}
			}catch (Exception e) {
				// TODO: handle exception.
				if(errorMsg.length()>0) {
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", errorMsg);
				}else {
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", e.getMessage());
				}
			}finally {
				rtnJsonArray.add(RstHashMap);
			}
		}
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("userId", apiUserId);
		parameters.put("rtnContents", rtnJsonArray.toString());
		mapper.insertApiConn(parameters); 
		
		return rtnJsonArray;
	}
	public ArrayList<LinkedHashMap<String, Object>> takeinStock(HashMap<Object, Object> parameterInfo) {
		return mapper.takeinStock(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> userStock(HashMap<Object, Object> parameterInfo) {
	
		return mapper.userStock(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStock(HashMap<Object, Object> parameterInfo) {
	
		return  mapper.inspStock(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockDetail(HashMap<Object, Object> stockParameter) {
		
		return mapper.inspStockDetail(stockParameter);
	}

	@Override
	public HashMap<Object, Object> selectStationInfo(HashMap<Object, Object> parameterInfo) {

		return mapper.selectStationInfo(parameterInfo);
	}

	@Override
	public int inspStockTotalCnt(HashMap<Object, Object> parameterInfo) {
		
		return mapper.inspStockTotalCnt(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupDetail(HashMap<Object, Object> stockParameter) {
		
		return mapper.inspStockGroupDetail(stockParameter);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupImg(HashMap<Object, Object> stockParameter) {

		return mapper.inspStockGroupImg(stockParameter);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupMsg(HashMap<Object, Object> stockParameter) {

		return mapper.inspStockGroupMsg(stockParameter);
	}

	@Override
	public String getNno() throws Exception {
		
		return mapper.getNno();
	}

	@Override
	public HashMap<String, Object> spStockChk(HashMap<String, Object> rstStockNo) throws Exception {

		return mapper.spStockChk(rstStockNo);
	}

	@Override
	public HashMap<String, Object> apiValueCheck(HttpServletRequest request,Map<String, Object> jsonHeader) throws Exception {
		
		HashMap<String,Object> rst = new HashMap<String,Object>();
		
	  	String apiUserId = jsonHeader.get("userid").toString();	  
	  	String apiUserIp = request.getRemoteAddr();
		String userKey = mapper.selectUserKey(apiUserId);

		
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String toStringDateTime =  dateFormat.format(dateTime);
	    
	    /*
	    String apiKeyEncrypt = AES256Cipher.AES_Encode(toStringDateTime+"|"+apiUserId, userKey);
		*/
	    
	    
		rst.put("Status","SUCCESS");
		rst.put("Status_Code","10");
		rst.put("Status_Msg","");


		try {
			
			if(!jsonHeader.get("content-type").equals("application/json")){
				rst.put("Status","FAIL");
				rst.put("Status_Code","S10");
				rst.put("Status_Msg","In case content type is not JSON type");
				return rst;
			}
			
			/*
			if(!request.isSecure()){
				rst.put("Status","FAIL");
				rst.put("Status_Code","S20");
				rst.put("Status_Msg","In case HTTPS is not connected");
				return rst;
			}
			*/

			String ipChk = mapper.selectUserAllowIp(apiUserId, apiUserIp);			
	 		if(!ipChk.equals("TRUE")) {
	 			rst.put("Status","FAIL");
	 			rst.put("Status_Code","S30");
	 			rst.put("Status_Msg","In case CONNECT IP is not allow Ip");
	 			return rst;
	 		}

			String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
			String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");
		
			if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
				rst.put("Status","FAIL");
				rst.put("Status_Code","L10");
				rst.put("Status_Msg","ACI KEY IS NOT MATCHING");				
				return rst;
			}
			
			String from = apiKeyDecyptList[0].toLowerCase();
			boolean regex = Pattern.matches(YYYYMMDDHHMISS, from);
			
			if(!regex) {
				rst.put("Status","FAIL");
				rst.put("Status_Code","L20");
				rst.put("Status_Msg","ACI KEY IS NOT MATCHING");		
				return rst;
			}
			
			LocalDateTime date1 = LocalDateTime.parse(from ,DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ));
			LocalDateTime currntTime = LocalDateTime.now();
			/*
			if(date1.plusMinutes(5).isBefore(currntTime)) {
				rst.put("Status","FAIL");
				rst.put("Status_Code","L30");
				rst.put("Status_Msg","Transfer Time Exceeded");		
				return rst;
			}
			*/
		}catch(IllegalBlockSizeException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			
			rst.put("Status","FAIL");
			rst.put("Status_Code","L30");
			rst.put("Status_Msg","ACI KEY IS NOT SUPPORTED");
		
		}catch (Exception e) {
			
			rst.put("Status","FAIL");
			rst.put("Status_Code","D00");
			rst.put("Status_Msg",e.toString());
			
		}
		return rst;
	}

	@Override
	public HashMap<String, Object> stationCheck(HashMap<String, Object> prarmeterInfo) throws Exception{
		
		return mapper.stationCheck(prarmeterInfo);
	}

	@Override
	public HashMap<String, Object> returnNoChk(HashMap<String, Object> returnNoChkRst) throws Exception{
		return mapper.returnNoChk(returnNoChkRst);
	}

	@Override
	public void returnItemInsert(HashMap<String, Object> orderInfo) throws Exception{
		
		mapper.returnItemInsert(orderInfo);
	}

	@Override
	public void returnListInsert(HashMap<String, Object> orderInfo) throws Exception{

		mapper.returnListInsert(orderInfo);
		
	}

	@Override
	public void insertApiConn(HashMap<String, Object> parameters) throws Exception {
		mapper.insertApiConn(parameters);  
	}

	@Override
	public void updateApiConn(HashMap<String, Object> parameters) throws Exception {
		mapper.updateApiConn(parameters);
		
	}

	@Override
	public HashMap<String, Object> selectReturnType(HashMap<String, Object> rstRetunType) throws Exception{
		return mapper.selectReturnType(rstRetunType);
	}

	@Override
	public HashMap<String, String> spReqReturnDel(HashMap<String, String> parameters) throws Exception {
		
		return mapper.spReqReturnDel(parameters);
	}

	@Override
	public int requestReturnListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.requestReturnListCnt(parameters);
	}

	@Override
	public void insertYslItemCode() throws Exception {
		// TODO Auto-generated method stub
		mapper.insertYslItemCode();
	}
	
	@Override
	public ArrayList<ViewYslItemCode> selectViewYslItem() throws Exception {
		return mapper.selectViewYslItem();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> mawbLookUp(Map<String, Object> jsonHeader, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> mawb = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> rtnMawbList = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<MawbVO> mawbLookUpList = new ArrayList<MawbVO>();
		
		HashMap<String,String> parameters = new HashMap<String,String>();
		String sDate = "";
		String eDate = "";
		if(request.getParameter("sDate") != null) {
			sDate = request.getParameter("sDate");
			if(request.getParameter("eDate") != null)
				eDate = request.getParameter("eDate");
		}else {
			sDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			eDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		}
		
		mawbLookUpList = comnApi.mawbListLookUp(sDate, eDate);
		for(int index = 0; index < mawbLookUpList.size(); index++) {
			mawb = new LinkedHashMap<String, Object>();
			mawb.put("MAWBNO", mawbLookUpList.get(index).getMawbNo());
			mawb.put("ARRDATE", mawbLookUpList.get(index).getArrDate());
			mawb.put("FLTNO", mawbLookUpList.get(index).getFltNo());
			mawb.put("STATION", mawbLookUpList.get(index).getOrgStation());
			mawb.put("HCTN", mawbLookUpList.get(index).getHawbCnt());
			rtnMawbList.add(mawb);
		}
		return rtnMawbList;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> hawbLookUpInMawb(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> rtnVal = new LinkedHashMap<String, Object>();
		ArrayList<String> rtnHawbList = new ArrayList<String> ();
		String apiUserId = jsonHeader.get("userid").toString();
		JSONArray jArrayData = new JSONArray(jsonData);
		
		for(int i = 0; i < jArrayData.length(); i++) {
			String mawbNo = jArrayData.getJSONObject(i).getString("MawbNo");
			rtnHawbList = new ArrayList<String> ();
			rtnVal = new LinkedHashMap<String, Object>();
			
			rtnHawbList = comnApi.hawbLookUpInMawb(mawbNo);
			rtnVal.put("Mawb_No", mawbNo);
			rtnVal.put("Hawb_List", rtnHawbList);
			rtnJsonArray.add(rtnVal);
		}
				
		return rtnJsonArray;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> hawbLookUp(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> rtnHawbInfo = new LinkedHashMap<String, Object> ();
		String apiUserId = jsonHeader.get("userid").toString();
		JSONArray jArrayData = new JSONArray(jsonData);
		
		for(int i = 0; i < jArrayData.length(); i++) {
			String hawbNo = jArrayData.getJSONObject(i).getString("HawbNo");
			rtnHawbInfo = new LinkedHashMap<String, Object> ();
			HawbLookUpVo hawbInfos = comnApi.hawbLookUp(hawbNo);
//			hawbInfos.setSymmetryKey(userKey);
			hawbInfos.dncryptData();
			
			rtnHawbInfo.put("HAWBDATE", hawbInfos.getWDate());
			rtnHawbInfo.put("ARRDATE", hawbInfos.getArrDate());
			rtnHawbInfo.put("HAWBNO", request.getParameter("HawbNo"));
			rtnHawbInfo.put("MAWBNO", hawbInfos.getMawbNo());
			rtnHawbInfo.put("SHIPPERCODE", "");
			rtnHawbInfo.put("SHIPPERNAME", hawbInfos.getShipperName());
			rtnHawbInfo.put("SHIPPERTEL", hawbInfos.getShipperTel());
			rtnHawbInfo.put("SHIPPERADDR", hawbInfos.getShipperAddr());
			rtnHawbInfo.put("SHIPPERURL", hawbInfos.getShipperUrl());
			rtnHawbInfo.put("ORDERNO", hawbInfos.getOrderNo());
			rtnHawbInfo.put("GETBY", hawbInfos.getGetBuy());
			rtnHawbInfo.put("CNEENAME", hawbInfos.getCneeName());
			rtnHawbInfo.put("CNEETEL", hawbInfos.getCneeTel());
			rtnHawbInfo.put("CNEEHP", hawbInfos.getCneeHp());
			rtnHawbInfo.put("CNEETONGNO", hawbInfos.getCustomsNo());
			rtnHawbInfo.put("CNEEZIP", hawbInfos.getCneeZip());
			rtnHawbInfo.put("CNEEADDR1", hawbInfos.getCneeAddr());
			rtnHawbInfo.put("CNEEADDR2", hawbInfos.getCneeAddrDetail());
			rtnHawbInfo.put("BOXCNT", hawbInfos.getBoxCnt());
			rtnHawbInfo.put("BOXWT", hawbInfos.getUserWta());
			rtnHawbInfo.put("BUYSITE", hawbInfos.getBuySite());
			rtnHawbInfo.put("ORIGINCITY", hawbInfos.getOriginCity());
			rtnHawbInfo.put("MALLTYPE2", hawbInfos.getMallType());
			rtnHawbInfo.put("SPCNO", "");
			rtnHawbInfo.put("SPCNAME", "");
			rtnHawbInfo.put("DLVAGENT", hawbInfos.getTransCode());
			rtnHawbInfo.put("COMNAME", "");
			rtnHawbInfo.put("COMTEL", "");
			rtnHawbInfo.put("COMADDR", "");
			rtnHawbInfo.put("SANO", hawbInfos.getSano());
			rtnHawbInfo.put("SAMALLNO", hawbInfos.getSamallno());
			rtnHawbInfo.put("SAADDR", hawbInfos.getSaaddr());
			rtnHawbInfo.put("SACEO", hawbInfos.getSaceo());
			rtnHawbInfo.put("STATION", hawbInfos.getOrgStation());
			rtnJsonArray.add(rtnHawbInfo);
		}
		
		return rtnJsonArray;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> takeInItemReg(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",request.getRemoteAddr());
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", RstHashMap.toString());
			
			RstHashMap.put("Customer_Item_Code", "-");
			RstHashMap.put("Status", "ERROR");
			RstHashMap.put("Result", temp);
			rtnJsonArray.add(RstHashMap);
			mapper.insertApiConn(parameters); 
			return rtnJsonArray;
		}
		
		for (int Index = 0 ; Index < jArrayData.length() ;  Index++) {
			parameters = new HashMap<String,Object>();
			RstHashMap  = new LinkedHashMap<String,Object>();
			JSONObject itemObj = jArrayData.getJSONObject(Index);
			parameters.put("orgStation",itemObj.getString("Departure_Station"));
			parameters.put("cusItemCode",itemObj.getString("Customer_Item_Code"));
			parameters.put("userId", apiUserId);
			parameters.put("brand",itemObj.getString("Brand"));
			parameters.put("hsCode",itemObj.getString("Hs_Code"));
			parameters.put("itemDetail",itemObj.getString("Item_Detail"));
			parameters.put("nativeItemDetail",itemObj.getString("Native_Item_Detail"));
			parameters.put("itemOption",itemObj.getString("Item_Option"));
			parameters.put("itemColor",itemObj.getString("Item_Color"));
			parameters.put("itemSize",itemObj.getString("Item_Size"));
			parameters.put("itemDiv",itemObj.getString("Item_Div"));
			if(itemObj.has("Item_Meterial")) {
				parameters.put("itemMeterial",itemObj.getString("Item_Meterial"));
			}else {
				parameters.put("itemMeterial",itemObj.getString("Item_Material"));
			}
			parameters.put("itemUrl",itemObj.getString("Item_Url"));
			parameters.put("itemImgUrl",itemObj.getString("Item_Img_Url"));
			if(itemObj.getString("Unit_Value").equals("")) {
		        RstHashMap.put("rstStatus", "FAIL");
		        RstHashMap.put("rstMsg", "Unit_Value가 비어있습니다");
				RstHashMap.put("Customer_Item_Code", itemObj.getString("Customer_Item_Code"));
				rtnJsonArray.add(RstHashMap);
				continue;
			}else {
				parameters.put("unitValue",itemObj.getString("Unit_Value"));
			}
			parameters.put("unitCurrency",itemObj.getString("Unit_Currency"));
			parameters.put("makeCntry",itemObj.getString("Make_Country"));
			parameters.put("makeCom",itemObj.getString("Make_Company"));
			parameters.put("wta",itemObj.getString("WTA"));
			parameters.put("wtc",itemObj.getString("WTC"));
			parameters.put("wtUnit",itemObj.getString("Weight_Unit"));
			parameters.put("qtyUnit",itemObj.getString("Quantity_Unit"));
			parameters.put("wUserIp",request.getRemoteAddr());
			parameters.put("wUserId",apiUserId);
			int cusItemCnt = mapper.checkTakeInCode(apiUserId,parameters.get("cusItemCode").toString());
			
			if(cusItemCnt == 1) {
				try {
					mapper.updateUserTakeinInfo(parameters);
					RstHashMap.put("rstStatus", "SUCCESS");
			        RstHashMap.put("rstMsg", "정상처리되었습니다.");
					RstHashMap.put("Customer_Item_Code", itemObj.getString("Customer_Item_Code"));
				}catch (Exception e) {
					logger.error("Exception", e);
					RstHashMap.put("rstStatus", "FAIL");
			        RstHashMap.put("rstMsg", "SQL Error");
					RstHashMap.put("Customer_Item_Code", itemObj.getString("Customer_Item_Code"));
				}
			}else {
				RstHashMap = mapper.insertUserTakeinInfo(parameters);
			}
			RstHashMap.put("Customer_Item_Code", itemObj.getString("Customer_Item_Code"));
			rtnJsonArray.add(RstHashMap);
		}
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("userId", apiUserId);
		parameters.put("rtnContents", rtnJsonArray.toString());
		mapper.insertApiConn(parameters); 
		return rtnJsonArray;
	}
	

	@Override
	public ArrayList<LinkedHashMap<String, Object>> takeInloopUpDate(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> holdBLRestart(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		//8260
		//temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			RstHashMap.put("Customer_Item_Code", "-");
			RstHashMap.put("Status", "ERROR");
			RstHashMap.put("Result", temp);
			
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",request.getRemoteAddr());
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", RstHashMap.toString());
			rtnJsonArray.add(RstHashMap);
			mapper.insertApiConn(parameters); 
			return rtnJsonArray;
		}
		
		for (int Index = 0 ; Index < jArrayData.length() ;  Index++) {
			JSONObject itemObj = jArrayData.getJSONObject(Index);
			try {
				rst = new HashMap<String,Object>();
				RstHashMap  = new LinkedHashMap<String,Object>();
				parameters = new HashMap<String,Object>();
				parameters.put("userId", apiUserId);
				if(itemObj.getString("BL_No").equals("")) {
					parameters.put("hawbNo",null);
				}else {
					parameters.put("hawbNo",itemObj.getString("BL_No"));
				}
				
				if(itemObj.getString("Hold_Yn").equals("")) {
					parameters.put("holdYn","Y");
				}else {
					parameters.put("holdYn",itemObj.getString("Hold_Yn"));
				}
				parameters.put("remark",itemObj.getString("Reason"));
				parameters.put("wUserId", apiUserId);
				parameters.put("wUserIp",request.getRemoteAddr());
				rst = mapper.execSpHoldBl(parameters);
				
		 		if(!rst.get("rstCode").equals("S10")) {
		 			rst.put("Error_Msg",rst.get("rstMsg"));
		 			rst.put("Status_Code",rst.get("rstCode"));
		 			rst.remove("rstHawbNo");
		 			rst.remove("rstCode");
					RstHashMap.put("BL_No", itemObj.getString("BL_No"));
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", rst);
		 		}else {
		 			RstHashMap.put("BL_No", itemObj.getString("BL_No"));
					RstHashMap.put("Status", "SUCCESS");
					rst.put("Status_Code",rst.get("rstCode"));
					rst.remove("rstHawbNo");
					rst.remove("rstCode");
					RstHashMap.put("Result", rst);
		 		}
		 		rtnJsonArray.add(RstHashMap);
	 		}catch (Exception e) {
				// TODO: handle exception
	 			rst.put("Status_Code","D10");
	 			rst.put("rstStatus","ERROR");
	 			rst.put("rstMsg","Exception! SQL Error!!");
				RstHashMap.put("BL_No", itemObj.getString("BL_No"));
				RstHashMap.put("Status", "ERROR");
				RstHashMap.put("Result", rst);
				rtnJsonArray.add(RstHashMap);
			}
		}
		return rtnJsonArray;
	}

	@Override
	public String shopifyOrderListGetTest(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		return shopifyApi.shopifyOrderListGetTest(apiShopifyInfoVO,httpRequest,httpResponse);
	}
	
	@Override
	public String shopifyOrderListCntGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		return shopifyApi.shopifyOrderListCntGet(apiShopifyInfoVO,httpRequest,httpResponse);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ArrayList<Map<String, Object>> modifyOrderTakeIn(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>> ();
		ArrayList<Map<String, Object>> rtnDelVal = new ArrayList<Map<String, Object>>();
		JSONArray jArrayData = new JSONArray(jsonData);
		String apiUserId = jsonHeader.get("userid").toString();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		String hawbNo = "";
		for(int i = 0; i < jArrayData.length(); i++) {
			JSONObject jObject = jArrayData.getJSONObject(i);
			if(jObject.has("BL_No")) {
				hawbNo = jObject.getString("BL_No");
			}else if (jObject.has("Hwab_No")) {
				hawbNo = jObject.getString("Hwab_No");
			}else if (jObject.has("Hawb_No")) {
				hawbNo = jObject.getString("Hawb_No");
			}
			int checkOrder = mapper.checkOrderHawb(hawbNo, apiUserId);
			if(checkOrder != 0) {
				rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
				if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
					rtnVal = insertOrderTakeIn(jsonHeader, jsonData, request, userKey);
				}else {
					rtnVal = rtnDelVal;
				}
				
			}else {
				if(jObject.has("Order_Number")) {
					RstHashMap.put("Order_No",jObject.getString("Order_Number"));
				}else if (jObject.has("Order_No")) {
					RstHashMap.put("Order_No",jObject.getString("Order_No"));
				}else {
					RstHashMap.put("Order_No","");
				}
				
				checkOrder = mapper.checkOrder(RstHashMap.get("Order_No").toString(), apiUserId);
				if(checkOrder != 0) {
					rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
					if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
						rtnVal = insertOrderInsp(jsonHeader, jsonData, request, userKey);	
					}else {
						rtnVal = rtnDelVal;
					}
				}else {
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", "This Order_No is not registered.");
					parameters.put("jsonHeader", jsonHeader.toString());
					parameters.put("jsonData", jArrayData.toString());
					parameters.put("wUserId",apiUserId);
					parameters.put("wUserIp",request.getRemoteAddr());
					parameters.put("connUrl",request.getServletPath());
					parameters.put("rtnContents", RstHashMap.toString());
					rtnVal.add(RstHashMap);
					mapper.insertApiConn(parameters); 
					continue;
				}
			}
		}
		return rtnVal;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ArrayList<Map<String, Object>> modifyOrderInsp(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>> ();
		ArrayList<Map<String, Object>> rtnDelVal = new ArrayList<Map<String, Object>>();
		JSONArray jArrayData = new JSONArray(jsonData);
		String apiUserId = jsonHeader.get("userid").toString();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		String hawbNo = "";
		for(int i = 0; i < jArrayData.length(); i++) {
			JSONObject jObject = jArrayData.getJSONObject(i);
			if(jObject.has("BL_No")) {
				hawbNo = jObject.getString("BL_No");
			}else if (jObject.has("Hwab_No")) {
				hawbNo = jObject.getString("Hwab_No");
			}else if (jObject.has("Hawb_No")) {
				hawbNo = jObject.getString("Hawb_No");
			}
			int checkOrder = mapper.checkOrderHawb(hawbNo, apiUserId);
			if(checkOrder != 0) {
				rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
				if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
					rtnVal = insertOrderInsp(jsonHeader, jsonData, request, userKey);	
				}else {
					rtnVal = rtnDelVal;
				}
			}else {
				if(jObject.has("Order_Number")) {
					RstHashMap.put("Order_No",jObject.getString("Order_Number"));
				}else if (jObject.has("Order_No")) {
					RstHashMap.put("Order_No",jObject.getString("Order_No"));
				}else {
					RstHashMap.put("Order_No","");
				}
				
				checkOrder = mapper.checkOrder(RstHashMap.get("Order_No").toString(), apiUserId);
				if(checkOrder != 0) {
					rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
					if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
						rtnVal = insertOrderInsp(jsonHeader, jsonData, request, userKey);	
					}else {
						rtnVal = rtnDelVal;
					}
				}else {
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", "This Order_No is not registered.");
					parameters.put("jsonHeader", jsonHeader.toString());
					parameters.put("jsonData", jArrayData.toString());
					parameters.put("wUserId",apiUserId);
					parameters.put("wUserIp",request.getRemoteAddr());
					parameters.put("connUrl",request.getServletPath());
					parameters.put("rtnContents", RstHashMap.toString());
					rtnVal.add(RstHashMap);
					mapper.insertApiConn(parameters); 
					continue;
				}
			}
			
		}
		return rtnVal;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ArrayList<Map<String, Object>> modifyOrderNomal(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {

		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>> ();
		ArrayList<Map<String, Object>> rtnDelVal = new ArrayList<Map<String, Object>>();
		JSONArray jArrayData = new JSONArray(jsonData);
		String apiUserId = jsonHeader.get("userid").toString();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		String hawbNo = "";
		for(int i = 0; i < jArrayData.length(); i++) {
			JSONObject jObject = jArrayData.getJSONObject(i);
			if(jObject.has("BL_No")) {
				hawbNo = jObject.getString("BL_No");
			}else if (jObject.has("Hwab_No")) {
				hawbNo = jObject.getString("Hwab_No");
			}else if (jObject.has("Hawb_No")) {
				hawbNo = jObject.getString("Hawb_No");
			}
			int checkOrder = mapper.checkOrder(hawbNo, apiUserId);
			if(checkOrder != 0) {
				rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
				if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
					rtnVal = insertOrderNomal(jsonHeader, jsonData, request, userKey);
				}else {
					rtnVal = rtnDelVal;
				}
			}else {
				if(jObject.has("Order_Number")) {
					RstHashMap.put("Order_No",jObject.getString("Order_Number"));
				}else if (jObject.has("Order_No")) {
					RstHashMap.put("Order_No",jObject.getString("Order_No"));
				}else {
					RstHashMap.put("Order_No","");
				}
				
				checkOrder = mapper.checkOrder(RstHashMap.get("Order_No").toString(), apiUserId);
				if(checkOrder != 0) {
					rtnDelVal = deleteOrder(jsonHeader,jsonData,request,userKey);
					if(rtnDelVal.get(0).get("Status_Code").equals("200")) {
						rtnVal = insertOrderInsp(jsonHeader, jsonData, request, userKey);	
					}else {
						rtnVal = rtnDelVal;
					}
				}else {
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", "This Order_No is not registered.");
					parameters.put("jsonHeader", jsonHeader.toString());
					parameters.put("jsonData", jArrayData.toString());
					parameters.put("wUserId",apiUserId);
					parameters.put("wUserIp",request.getRemoteAddr());
					parameters.put("connUrl",request.getServletPath());
					parameters.put("rtnContents", RstHashMap.toString());
					rtnVal.add(RstHashMap);
					mapper.insertApiConn(parameters); 
					continue;
				}
			}
			
		}
		return rtnVal;
	}

	@Override
	public ArrayList<Map<String, Object>> deleteOrder(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
			HttpServletRequest request, String userKey) throws Exception {
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

		String apiUserId = jsonHeader.get("userid").toString();
		LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if (!temp.isEmpty()) {
			Iterator iter = temp.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				RstHashMap.put(key, temp.get(key));
			}
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", request.getRemoteAddr());
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			mapper.insertApiConn(parameters);
			return rtnJsonArray;
		}
		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		
		// JrUGT5p4c9ZmO75BNlD7XjrLn9z96ouRnWWXhi1WcDE=
		if(jArrayData.length()>50) {
			
			RstHashMap.put("Status_Code", "-900");
			RstHashMap.put("ErrorMsg", "요청 개수가 너무 많습니다. (최대 50)");
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", request.getRemoteAddr());
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			
			
			return rtnJsonArray;
		}
		for (int trkIndex = 0; trkIndex < jArrayData.length(); trkIndex++) {
			HashMap<String,String> deleteInfo = new HashMap<String,String>();
			RstHashMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> podDetatil = new LinkedHashMap<String, Object>();
			JSONObject itemObj = jArrayData.getJSONObject(trkIndex);
			if(!itemObj.has("Order_Number") && !itemObj.has("Order_No") ) {
				podDetatil.put("Order_No", "");
				podDetatil.put("Status_Code", "-300");
				podDetatil.put("Error_Msg", "parameter 명이 잘못되었습니다. (Incorrect parameter name.)");
				rtnJsonArray.add(podDetatil);
				continue;
			}
			if(itemObj.has("Order_Number")) {
				orderInfo.put("orderNo", itemObj.get("Order_Number"));
			}else if(itemObj.has("Order_No")) {
				orderInfo.put("orderNo", itemObj.get("Order_No"));
			}
			
			String targetOrderNo = (String) orderInfo.get("orderNo");
			if (targetOrderNo.equals("") || targetOrderNo == null) {
				podDetatil.put("Order_No", "");
				podDetatil.put("Status_Code", "-200");
				podDetatil.put("Error_Msg", "OrderNo가 잘못되었습니다. (Incorrect OrderNo.)");
				rtnJsonArray.add(podDetatil);
				continue;
			}
			String targetNNO = mapper.selectApiOrderNNO(targetOrderNo, apiUserId);
			
			if(targetNNO != null && !targetNNO.equals("")) {
				try {
					int tempTarget = mgrMapper.selectStockYnByNNO(targetNNO);
					
					if(tempTarget == 0) {
						deleteInfo.put("nno", targetNNO);
						deleteInfo.put("userId", apiUserId);
						deleteInfo.put("userIp", request.getRemoteAddr());
						mgrMapper.deleteOrderList(deleteInfo);
						podDetatil.put("Order_No", targetOrderNo);
						podDetatil.put("Status_Code", "200");
						podDetatil.put("Error_Msg", "삭제되었습니다.");
					}else {
						podDetatil.put("Order_No", targetOrderNo);
						podDetatil.put("Status_Code", "-201");
						podDetatil.put("Error_Msg", "이미 입고된 상품입니다.");
					}
					
					
					rtnJsonArray.add(podDetatil);
				}catch (Exception e) {
					// TODO: handle exception
					podDetatil.put("Order_No", targetOrderNo);
					podDetatil.put("Status_Code", "-100");
					podDetatil.put("Error_Msg", "SQL Error입니다. 관리자에게 문의해주세요");
					rtnJsonArray.add(podDetatil);
				}
			}else {
				podDetatil.put("Order_No", targetOrderNo);
				podDetatil.put("Status_Code", "-202");
				podDetatil.put("Error_Msg", "Order No를 찾을 수 없습니다.");
				rtnJsonArray.add(podDetatil);
			}
			
			
		}
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId", apiUserId);
		parameters.put("wUserIp", request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", rtnJsonArray.toString());
		mapper.insertApiConn(parameters);
		return rtnJsonArray;
	}

	@Override
	public LinkedHashMap<String, Object> lookUpMessage(Map<String, Object> jsonHeader,
			Map<String, Object> jsonData, HttpServletRequest request, String userKey) throws Exception {

		LinkedHashMap<String, Object> rtnJson= new LinkedHashMap<String, Object>();
		StockMsgVO stockMsg = new StockMsgVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> msgDetail = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String,Object> rst = new LinkedHashMap<String,Object>();
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if (!temp.isEmpty()) {
			Iterator iter = temp.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				RstHashMap.put(key, temp.get(key));
			}
			rtnJson.put("jsonHeader", jsonHeader.toString());
			rtnJson.put("jsonData", jsonData.toString());
			rtnJson.put("wUserId", apiUserId);
			rtnJson.put("wUserIp", request.getRemoteAddr());
			rtnJson.put("connUrl",request.getServletPath());
			rtnJson.put("rtnContents", RstHashMap.toString());
			mapper.insertApiConn(rtnJson);
			return RstHashMap;
		}
		JSONObject itemObj = new JSONObject(jsonData);
		
		String nno = mapper.selectApiOrderNNO(itemObj.getString("Order_No"), apiUserId);
		if(nno == null || nno.equals("")) {
			RstHashMap.put("Error", "D10");
			RstHashMap.put("ErrorMsg", "Order No is wrong");
			rtnJson.put("jsonHeader", jsonHeader.toString());
			rtnJson.put("jsonData", jsonData.toString());
			rtnJson.put("wUserId", apiUserId);
			rtnJson.put("wUserIp", request.getRemoteAddr());
			rtnJson.put("connUrl",request.getServletPath());
			rtnJson.put("rtnContents", RstHashMap.toString());
			mapper.insertApiConn(rtnJson);
			return RstHashMap;
		}
		
		String orderNo = itemObj.getString("Order_No");
		
		if(!itemObj.getString("Msg").equals("")) {
			stockMsg.setNno(nno);
			stockMsg.setSubNo(itemObj.getString("Sub_No"));
			stockMsg.setMsgDiv("MSG");
			stockMsg.setGroupIdx(itemObj.getString("Group_Idx"));
			stockMsg.setUserId(apiUserId);
			stockMsg.setWUserId(apiUserId);
			stockMsg.setWUserIp(request.getRemoteAddr());
			stockMsg.setStatus(itemObj.getString("Status"));
			stockMsg.setAdminYn("N");
			stockMsg.setWhMemo(itemObj.getString("Msg"));
			try {
				mgrMapper.insertMsgInfo(stockMsg);
			}catch (Exception e) {
				// TODO: handle exception
				RstHashMap.put("Error", "D10");
				RstHashMap.put("ErrorMsg", "SQL Error!!");
				rtnJson.put("jsonHeader", jsonHeader.toString());
				rtnJson.put("jsonData", jsonData.toString());
				rtnJson.put("wUserId", apiUserId);
				rtnJson.put("wUserIp", request.getRemoteAddr());
				rtnJson.put("connUrl",request.getServletPath());
				rtnJson.put("rtnContents", RstHashMap.toString());
				mapper.insertApiConn(rtnJson);
				return RstHashMap;
			}
		}
		MsgHeaderVO msgInfo = new MsgHeaderVO();
		msgInfo = mapper.selectMsgHeader(orderNo,apiUserId);
		
		ArrayList<MsgBodyVO> msgBodyInfo = new ArrayList<MsgBodyVO>();
		
		msgBodyInfo = mapper.selectMsgBody(msgInfo.getGroupIdx());
		
		msgInfo.setDetail(msgBodyInfo);
		
		
		RstHashMap.put("Order_No", orderNo);
		RstHashMap.put("Group_Idx", msgInfo.getGroupIdx());
		RstHashMap.put("Status", msgInfo.getStatus());
		for(int i = 0 ; i < msgBodyInfo.size(); i++) {
			rst = new LinkedHashMap<String, Object>();
			rst.put("Seq",msgBodyInfo.get(i).getSeq());
			rst.put("Sub_No",msgBodyInfo.get(i).getSubNo());
			rst.put("Msg",msgBodyInfo.get(i).getMsg());
			rst.put("Registration_Date",msgBodyInfo.get(i).getWDate());
			rst.put("User_Div",msgBodyInfo.get(i).getUserDiv());
			msgDetail.add(rst);
		}
		
		
		RstHashMap.put("Detail", msgDetail);
		
		rtnJson.put("jsonHeader", jsonHeader.toString());
		rtnJson.put("jsonData", jsonData.toString());
		rtnJson.put("wUserId", apiUserId);
		rtnJson.put("wUserIp", request.getRemoteAddr());
		rtnJson.put("connUrl",request.getServletPath());
		rtnJson.put("rtnContents", RstHashMap.toString());
		mapper.insertApiConn(rtnJson);
		return RstHashMap;
	}
	
	@Override
	public ArrayList<Map<String, Object>> insertOrderEtc(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
			HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
//			rtnItemVal.add(tempItem);
			temp.put("Status", "Fail");
			rtnVal.add(temp);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jObject.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",request.getRemoteAddr());
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters); 
			return rtnVal;
		}
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			ApiOrderListVO defaultApiOrderList = new ApiOrderListVO();
			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
			String decryptRtn ="";
			try {
				HashMap<String, Object> etcOrderList = new HashMap<String, Object>();
				HashMap<String, Object> etcOrderItem = new HashMap<String, Object>();
				tempItem = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				jObject = jArray1.getJSONObject(index1);
				JSONArray jArray = jObject.getJSONArray("GoodsInfo");
				String newNno = new String();
				String date = null;
				
				newNno = comnService.selectNNO();
				
				etcOrderList.put("nno",newNno);
				String StationCode = mapper.selectStationCode(jObject.getString("Org_Station"));
				etcOrderList.put("orgStation",StationCode);
				etcOrderList.put("userId", apiUserId);
				if(jObject.getString("Order_Date").equals("")) {
					date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
				}else {
					date = jObject.getString("Order_Date");
				}
				etcOrderList.put("orderDate", date);
				etcOrderList.put("trkCom", jObject.getString("Customer_Company"));
				etcOrderList.put("trkNo", jObject.getString("Order_No"));
				etcOrderList.put("orderText", jObject.getString("Order_Text"));
				etcOrderList.put("shippingFee", "");
				etcOrderList.put("remark", "");
				etcOrderList.put("wUserId", apiUserId);
				etcOrderList.put("wUserIp", request.getRemoteAddr());
				etcOrderList.put("etcCurrency", jObject.getString("Currency"));
				etcOrderList.put("boxType", jObject.getString("Box_Size"));
				mapper.insertTbTakeinEtcOrder(etcOrderList);
				for(int itemIndex=0;itemIndex<jArray.length();itemIndex++) {
					JSONObject itemObj = jArray.getJSONObject(itemIndex);
					etcOrderItem.put("nno",newNno);
					etcOrderItem.put("orgStation",jObject.getString("Org_Station"));
					etcOrderItem.put("userId", apiUserId);
					etcOrderItem.put("wUserId", apiUserId);
					etcOrderItem.put("wUserIp", request.getRemoteAddr());
					etcOrderItem.put("takeInCode", itemObj.getString("Customer_Item_Code"));
					etcOrderItem.put("itemCnt", itemObj.getString("Item_Count"));
					etcOrderItem.put("subNo", itemIndex+1);
					mgrTakeinService.insertTbTakeinEtcOrderItem(etcOrderItem);
				}
				
				temp.put("Status_Code","A10");
				temp.put("Error_Msg","-");
				temp.put("Status", "SUCCESS");
			}catch (Exception e) {
				// TODO: handle exception
				temp.put("Status_Code","D10");
				temp.put("Error_Msg",e.getMessage());
				temp.put("Status", "Fail");
			}finally {
				rtnVal.add(temp);
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jObject.toString());
				parameters.put("wUserId",apiUserId);
				parameters.put("wUserIp",request.getRemoteAddr());
				parameters.put("connUrl",request.getServletPath());
				parameters.put("rtnContents", temp.toString());
				mapper.insertApiConn(parameters); 
			}
		}
		
		
		return rtnVal;
	}
	
	@Override
	public Map<String, Object> instrumentGet(Map<String, Object> jsonHeader, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> temp2 = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String orgStation = "";
		if(jsonHeader.get("orgstation") == null || jsonHeader.get("orgstation").equals("")) {
			orgStation  = request.getParameter("orgstation");
		} else if(jsonHeader.get("orgStation") == null || jsonHeader.get("orgStation").equals("")) {
			orgStation  = request.getParameter("orgStation");
		}else {
			orgStation = jsonHeader.get("orgstation").toString();
		}
		
		//temp = checkApiOrderInfo(jsonHeader, request, userKey);
		ArrayList<InsterumentVO> instermentList = new ArrayList<InsterumentVO>(); 
		
//		parameters.put("userId", request.getSession().getAttribute("USER_ID"));
		parameters.put("orgStation", orgStation);
		
		try {
			instermentList = mapper.selectInsterument(parameters);
			temp.put("Status", "SUCCESS");
			temp.put("Status_Code", "A10");
			temp.put("Error_Msg","-");
			temp.put("Details",instermentList);
		}catch (Exception e) {
			// TODO: handle exception
			temp.put("Status", "ERROR");
			temp.put("Status_Code", "D30");
			temp.put("Error_Msg","SQL ERROR");
			temp.put("Details",instermentList);
		}
		
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", "");
		parameters.put("wUserId",orgStation+"_Insterment");
		parameters.put("wUserIp",request.getRemoteAddr()+"Get");
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", temp.toString());
		mapper.insertApiConn(parameters); 
		
		
		return temp;
	}

	@Override
	public Map<String, Object> instrumentPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		String orgStation = "";
		String errorList = "";
		String result = "";
		String hawbNo = "";
		try {
			// TODO: handle exception
			if(jsonHeader.get("orgstation") == null || jsonHeader.get("orgstation").equals("")) {
				orgStation  = request.getParameter("orgstation");
			}else {
				orgStation = jsonHeader.get("orgstation").toString();
			}
			VolumeVO volume = new VolumeVO ();
			
			
			JSONObject _jObject = new JSONObject(jsonData);
			ApiOrderListVO apiOrderList = new ApiOrderListVO();
			UserOrderListVO orderInfo = new UserOrderListVO();
			ApiOrderListVO defaultApiOrderList = new ApiOrderListVO();
	
			if(!_jObject.has("BLNO")) {
				errorList += "[BLNO] ";
			}
			
			if(!_jObject.has("STATION")) {
				errorList += "[STATION] ";
			}
			
			if(!_jObject.has("WTA")) {
				errorList += "[WTA] ";
			}
			
			if(!_jObject.has("WTV")) {
				errorList += "[WTV] ";
			}
			
			if(!_jObject.has("VOL_WIDTH")) {
				errorList += "[VOL_WIDTH] ";
			}
			
			if(!_jObject.has("VOL_HEIGHT")) {
				errorList += "[VOL_HEIGHT] ";
			}
			
			if(!_jObject.has("MAWBNO")) {
				errorList += "[MAWBNO] ";
			}
			
			if(!_jObject.has("VOL_LENGTH")) {
				errorList += "[VOL_LENGTH] ";
			}
			
			hawbNo = _jObject.get("BLNO").toString();
			if(errorList.equals("")) {
				String nno = mgrMapper.selectNNOByHawbNo(hawbNo);
				if(nno == null || nno.equals("")) {
					result = "F";
					HashMap<String,Object> parameters2 = new HashMap<String,Object>();
					parameters2.put("jsonHeader", jsonHeader.toString());
					parameters2.put("jsonData", jsonData.toString());
					parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
					parameters2.put("wUserIp",request.getRemoteAddr());
					parameters2.put("connUrl",request.getServletPath());
					temp.put("Status", "FAIL");
					temp.put("Status_Code", "P10");
					temp.put("Error_Msg","BLNO ["+hawbNo+"] IS WRONG");
					temp.put("Detail","");
					temp.put("BLNO", hawbNo);
					parameters2.put("rtnContents", temp.toString());
					mapper.insertApiConn(parameters2); 
					return temp;
				}else {
					try {
						orderInfo = comnService.selectUserRegistOrderOne(nno);
						if(orderInfo.getOrderType().toUpperCase().equals("NOMAL")){
							double wtc =0;
							OrderRcptVO orderRcpt = new OrderRcptVO();
							ProcedureVO procedure = new ProcedureVO();
							ProcedureVO procedureVol = new ProcedureVO();
							ProcedureVO procedureRcpt = new ProcedureVO();
							ProcedureVO rstProcedure = new ProcedureVO();
							orderRcpt.setHawbNo(orderInfo.getHawbNo());
							orderRcpt.setOrgStation(orderInfo.getOrgStation());
							orderRcpt.setBoxCnt(orderInfo.getBoxCnt());
							orderRcpt.setWta(_jObject.get("WTA").toString());
							HashMap<String,Object> dimInfo = new HashMap<String,Object>();
							dimInfo = mgrMapper.selectPerDimByHawb(orderInfo.getHawbNo());
							if(dimInfo.get("perDim").equals("5000.0")) {
								double height = Double.parseDouble(_jObject.get("VOL_HEIGHT").toString());
								double width = Double.parseDouble(_jObject.get("VOL_WIDTH").toString());
								double length = Double.parseDouble(_jObject.get("VOL_LENGTH").toString());
								double dimPer = Double.parseDouble(dimInfo.get("perDim").toString());
								wtc = (height*width*length)/dimPer;
								orderRcpt.setWtc(Double.toString(wtc));
							}else {
								orderRcpt.setWtc(_jObject.get("WTV").toString());
							}
							orderRcpt.setWUserId(orgStation+"instrument");
							orderRcpt.setWUserIp(request.getRemoteAddr());
							procedure = mgrMapper.execNomalHawbIn(orderRcpt);
							
							mgrMapper.deleteVolume(nno);
							volume.setNno(nno);
							volume.setWUserId(orgStation+"instrument");
							volume.setWUserIp(request.getRemoteAddr());
							volume.setHeight(_jObject.get("VOL_HEIGHT").toString());
							volume.setWidth(_jObject.get("VOL_WIDTH").toString());
							volume.setLength(_jObject.get("VOL_LENGTH").toString());
							String per = mapper.selectTransPer(orderInfo.getTransCode());
							int temp2 = (int)Double.parseDouble(per);
							volume.setPer(Integer.toString(temp2));
							volume.setWtUnit(orderInfo.getWtUnit());
							volume.setDimUnit(orderInfo.getDimUnit());
						
							procedureVol =mgrMapper.execNomalHawbVolume(volume);
							if (!procedureVol.getRstStatus().equals("SUCCESS")) {
								result = procedureVol.getRstMsg();
							}
							String[] asd = new String[1];
							asd[0] = orderInfo.getTransCode()+","+nno;
							request.getSession().setAttribute("ORG_STATION", jsonHeader.get("orgstation").toString());
							request.getSession().setAttribute("USER_IP", request.getRemoteAddr());
							request.getSession().setAttribute("USER_ID", orgStation+"_Insterment"+"Post");
							procedureRcpt= mgrService.execOrderRcptList(asd,request);
							HawbVO hawbVo = new HawbVO();
							hawbVo.setHawbNo(hawbNo);
							hawbVo.setNno(nno);
							hawbVo.setMawbNo(_jObject.get("MAWBNO").toString());
							hawbVo.setWUserId(orgStation+"instrument");
							hawbVo.setWUserIp(request.getRemoteAddr());
							mgrService.insertHawb(hawbVo);
							result = "S";
							//TEST용 실환경 시, 삭제
							//mgrMapper.updateHawbMawb2(parameters.get("hawbNo"));
							//TEST용 실환경 시, 삭제				
							HashMap<String,Object> parameters2 = new HashMap<String,Object>();
							parameters2.put("jsonHeader", jsonHeader.toString());
							parameters2.put("jsonData", jsonData.toString());
							parameters2.put("wUserId",orgStation+"_Insterment");
							parameters2.put("wUserIp",request.getRemoteAddr());
							parameters2.put("connUrl",request.getServletPath());
							
							temp.put("Status", "SUCCESS");
							temp.put("Status_Code", "A10");
							temp.put("Error_Msg","-");
							temp.put("Detail","");
							temp.put("BLNO", hawbNo);
							parameters2.put("rtnContents", temp.toString());
							
							mapper.insertApiConn(parameters2); 
						}else if (orderInfo.getOrderType().toUpperCase().equals("adsasdasdasd")) {
							double wtc =0;
							OrderRcptVO orderRcpt = new OrderRcptVO();
							ProcedureVO procedure = new ProcedureVO();
							ProcedureVO procedureVol = new ProcedureVO();
							ProcedureVO procedureRcpt = new ProcedureVO();
							ProcedureVO rstProcedure = new ProcedureVO();
							orderRcpt.setHawbNo(orderInfo.getHawbNo());
							orderRcpt.setOrgStation(orderInfo.getOrgStation());
							orderRcpt.setBoxCnt(orderInfo.getBoxCnt());
							orderRcpt.setWta(_jObject.get("WTA").toString());
							HashMap<String,Object> dimInfo = new HashMap<String,Object>();
							dimInfo = mgrMapper.selectPerDimByHawb(orderInfo.getHawbNo());
							if(dimInfo.get("perDim").equals("5000.0")) {
								double height = Double.parseDouble(_jObject.get("VOL_HEIGHT").toString());
								double width = Double.parseDouble(_jObject.get("VOL_WIDTH").toString());
								double length = Double.parseDouble(_jObject.get("VOL_LENGTH").toString());
								double dimPer = Double.parseDouble(dimInfo.get("perDim").toString());
								wtc = (height*width*length)/dimPer;
								orderRcpt.setWtc(Double.toString(wtc));
							}else {
								orderRcpt.setWtc(_jObject.get("WTV").toString());
							}
							orderRcpt.setWUserId(orgStation+"instrument");
							orderRcpt.setWUserIp(request.getRemoteAddr());
							procedure = mgrMapper.execNomalHawbIn(orderRcpt);
							
							mgrMapper.deleteVolume(nno);
							volume.setNno(nno);
							volume.setWUserId(orgStation+"instrument");
							volume.setWUserIp(request.getRemoteAddr());
							volume.setHeight(_jObject.get("VOL_HEIGHT").toString());
							volume.setWidth(_jObject.get("VOL_WIDTH").toString());
							volume.setLength(_jObject.get("VOL_LENGTH").toString());
							String per = mapper.selectTransPer(orderInfo.getTransCode());
							int temp2 = (int)Double.parseDouble(per);
							volume.setPer(Integer.toString(temp2));
							volume.setWtUnit(orderInfo.getWtUnit());
							volume.setDimUnit(orderInfo.getDimUnit());
						
							procedureVol =mgrMapper.execNomalHawbVolume(volume);
							if (!procedureVol.getRstStatus().equals("SUCCESS")) {
								result = procedureVol.getRstMsg();
							}
							String[] asd = new String[1];
							asd[0] = orderInfo.getTransCode()+","+nno;
							request.getSession().setAttribute("ORG_STATION", jsonHeader.get("orgstation").toString());
							request.getSession().setAttribute("USER_IP", request.getRemoteAddr());
							request.getSession().setAttribute("USER_ID", orgStation+"_Insterment"+"Post");
							procedureRcpt= mgrService.execOrderRcptList(asd,request);
							HawbVO hawbVo = new HawbVO();
							hawbVo.setHawbNo(hawbNo);
							hawbVo.setNno(nno);
							hawbVo.setMawbNo(_jObject.get("MAWBNO").toString());
							hawbVo.setWUserId(orgStation+"instrument");
							hawbVo.setWUserIp(request.getRemoteAddr());
							mgrService.insertHawb(hawbVo);
							result = "S";
							//TEST용 실환경 시, 삭제
							//mgrMapper.updateHawbMawb2(parameters.get("hawbNo"));
							//TEST용 실환경 시, 삭제				
							HashMap<String,Object> parameters2 = new HashMap<String,Object>();
							parameters2.put("jsonHeader", jsonHeader.toString());
							parameters2.put("jsonData", jsonData.toString());
							parameters2.put("wUserId",orgStation+"_Insterment");
							parameters2.put("wUserIp",request.getRemoteAddr());
							parameters2.put("connUrl",request.getServletPath());
							
							temp.put("Status", "SUCCESS");
							temp.put("Status_Code", "A10");
							temp.put("Error_Msg","-");
							temp.put("Detail","");
							temp.put("BLNO", hawbNo);
							parameters2.put("rtnContents", temp.toString());
							
							mapper.insertApiConn(parameters2); 
						}else {
							result = "F";
							HashMap<String,Object> parameters2 = new HashMap<String,Object>();
							parameters2.put("jsonHeader", jsonHeader.toString());
							parameters2.put("jsonData", jsonData.toString());
							parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
							parameters2.put("wUserIp",request.getRemoteAddr());
							parameters2.put("connUrl",request.getServletPath());
							
							temp.put("Status", "FAIL");
							temp.put("Status_Code", "P10");
							temp.put("Error_Msg","Order Type Error!: "+orderInfo.getOrderType().toUpperCase());
							temp.put("Detail","");
							temp.put("BLNO", hawbNo);
							parameters2.put("rtnContents", temp.toString());
							mapper.insertApiConn(parameters2); 
						}
					} catch (Exception e) {
						// TODO: handle exception
						result = "F";
						HashMap<String,Object> parameters2 = new HashMap<String,Object>();
						parameters2.put("jsonHeader", jsonHeader.toString());
						parameters2.put("jsonData", jsonData.toString());
						parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
						parameters2.put("wUserIp",request.getRemoteAddr());
						parameters2.put("connUrl",request.getServletPath());
						
						temp.put("Status", "FAIL");
						temp.put("Status_Code", "P10");
						temp.put("Error_Msg",e.getStackTrace());
						temp.put("Detail","");
						temp.put("BLNO", hawbNo);
						parameters2.put("rtnContents", temp.toString());
						mapper.insertApiConn(parameters2); 
					}
				}
			}else {
				result = "F";
				HashMap<String,Object> parameters2 = new HashMap<String,Object>();
				parameters2.put("jsonHeader", jsonHeader.toString());
				parameters2.put("jsonData", jsonData.toString());
				parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
				parameters2.put("wUserIp",request.getRemoteAddr());
				parameters2.put("connUrl",request.getServletPath());
				temp.put("Status", "FAIL");
				temp.put("Status_Code", "P10");
				temp.put("Error_Msg",errorList+"has been ERROR or Empty");
				temp.put("Detail","");
				temp.put("BLNO", hawbNo);
				parameters2.put("rtnContents", temp.toString());
				mapper.insertApiConn(parameters2); 
			}
		}catch (Exception e) {
			// TODO: handle exception
			result = "F";
			HashMap<String,Object> parameters2 = new HashMap<String,Object>();
			parameters2.put("jsonHeader", jsonHeader.toString());
			parameters2.put("jsonData", jsonData.toString());
			parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
			parameters2.put("wUserIp",request.getRemoteAddr());
			parameters2.put("connUrl",request.getServletPath());
			
			temp.put("Status", "FAIL");
			temp.put("Status_Code", "P10");
			temp.put("Error_Msg","");
			temp.put("Detail","");
			temp.put("BLNO", hawbNo);
			parameters2.put("rtnContents", temp.toString());
			mapper.insertApiConn(parameters2); 
		}
			
		return temp;
	}

	@Override
	public ArrayList<Map<String, Object>> instrumentListPost(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
			HttpServletRequest request) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject _jObject = new JSONObject(jsonData);
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		HashMap<String,Object> parameters2 = new HashMap<String,Object>();
		String orgStation = "";
		String errorList = "";
		String hawbNo = "";
		if(jsonHeader.get("orgstation") == null || jsonHeader.get("orgstation").equals("")) {
			orgStation  = request.getParameter("orgstation");
		}else {
			orgStation = jsonHeader.get("orgstation").toString();
		}
		VolumeVO volume = new VolumeVO ();
		String result = "";
		
		for(int index1 =0; index1 < jArray1.length(); index1++) {
			_jObject = jArray1.getJSONObject(index1);
			temp = new HashMap<String, Object>();
			UserOrderListVO orderInfo = new UserOrderListVO();

			if(!_jObject.has("BLNO")) {
				errorList += "[BLNO] ";
			}
			
			if(!_jObject.has("STATION")) {
				errorList += "[STATION] ";
			}
			
			if(!_jObject.has("WTA")) {
				errorList += "[WTA] ";
			}
			
			if(!_jObject.has("WTV")) {
				errorList += "[WTV] ";
			}
			
			if(!_jObject.has("VOL_WIDTH")) {
				errorList += "[VOL_WIDTH] ";
			}
			
			if(!_jObject.has("VOL_HEIGHT")) {
				errorList += "[VOL_HEIGHT] ";
			}
			
			if(!_jObject.has("MAWBNO")) {
				errorList += "[MAWBNO] ";
			}
			
			if(!_jObject.has("VOL_LENGTH")) {
				errorList += "[VOL_LENGTH] ";
			}
			
			hawbNo = _jObject.get("BLNO").toString();
			if(errorList.equals("")) {
				String nno = mgrMapper.selectNNOByHawbNo(hawbNo);
				if(nno == null || nno.equals("")) {
					result = "F";
					temp.put("Status", "FAIL");
					temp.put("Status_Code", "D10");
					temp.put("Error_Msg","BLNO ["+hawbNo+"] IS WRONG");
					temp.put("Detail","");
					temp.put("BLNO", hawbNo);
					rtnVal.add(temp);
				}else {
					try {
						orderInfo = comnService.selectUserRegistOrderOne(nno);
						if(orderInfo.getOrderType().toUpperCase().equals("NOMAL")){
							OrderRcptVO orderRcpt = new OrderRcptVO();
							ProcedureVO procedure = new ProcedureVO();
							ProcedureVO procedureVol = new ProcedureVO();
							ProcedureVO procedureRcpt = new ProcedureVO();
							ProcedureVO rstProcedure = new ProcedureVO();
							orderRcpt.setHawbNo(orderInfo.getHawbNo());
							orderRcpt.setOrgStation(orderInfo.getOrgStation());
							orderRcpt.setBoxCnt(orderInfo.getBoxCnt());
							orderRcpt.setWta(_jObject.get("WTA").toString());
							orderRcpt.setWtc(_jObject.get("WTV").toString());
							orderRcpt.setWUserId(orgStation+"instrument");
							orderRcpt.setWUserIp(request.getRemoteAddr());
							procedure = mgrMapper.execNomalHawbIn(orderRcpt);
							
							mgrMapper.deleteVolume(nno);
							volume.setNno(nno);
							volume.setWUserId(orgStation+"instrument");
							volume.setWUserIp(request.getRemoteAddr());
							volume.setHeight(_jObject.get("VOL_HEIGHT").toString());
							volume.setWidth(_jObject.get("VOL_WIDTH").toString());
							volume.setLength(_jObject.get("VOL_LENGTH").toString());
							String per = mapper.selectTransPer(orderInfo.getTransCode());
							int temp2 = (int)Double.parseDouble(per);
							volume.setPer(Integer.toString(temp2));
							volume.setWtUnit(orderInfo.getWtUnit());
							volume.setDimUnit(orderInfo.getDimUnit());
						
						
							procedureVol =mgrMapper.execNomalHawbVolume(volume);
							if (!procedureVol.getRstStatus().equals("SUCCESS")) {
								result = procedureVol.getRstMsg();
							}
							String[] asd = new String[1];
							asd[0] = orderInfo.getTransCode()+","+nno;
							procedureRcpt= mgrService.execOrderRcptList(asd,request);
							HawbVO hawbVo = new HawbVO();
							hawbVo.setHawbNo(hawbNo);
							hawbVo.setNno(nno);
							hawbVo.setMawbNo(_jObject.get("MAWBNO").toString());
							hawbVo.setWUserId(orgStation+"instrument");
							hawbVo.setWUserIp(request.getRemoteAddr());
							mgrService.insertHawb(hawbVo);
							result = "S";
							//TEST용 실환경 시, 삭제
							mgrMapper.updateHawbMawb2(hawbVo.getHawbNo());
							//TEST용 실환경 시, 삭제				
							parameters2.put("jsonHeader", jsonHeader.toString());
							parameters2.put("jsonData", jsonData.toString());
							parameters2.put("wUserId",orgStation+"_Insterment");
							parameters2.put("wUserIp",request.getRemoteAddr());
							parameters2.put("connUrl",request.getServletPath());
							
							temp.put("Status", "SUCCESS");
							temp.put("Status_Code", "A10");
							temp.put("Error_Msg","-");
							temp.put("Detail","");
							temp.put("BLNO", hawbNo);
							rtnVal.add(temp);
						
						}
					} catch (Exception e) {
						// TODO: handle exception
						result = "F";
						parameters2.put("jsonHeader", jsonHeader.toString());
						parameters2.put("jsonData", jsonData.toString());
						parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
						parameters2.put("wUserIp",request.getRemoteAddr());
						parameters2.put("connUrl",request.getServletPath());
						
						temp.put("Status", "FAIL");
						temp.put("Status_Code", "SQL ERROR");
						temp.put("Error_Msg","관리자에게 문의해 주세요");
						temp.put("Detail",e.getStackTrace());
						temp.put("BLNO", hawbNo);
						rtnVal.add(temp);
					}
				}
			}else {
				result = "F";
				parameters2.put("jsonHeader", jsonHeader.toString());
				parameters2.put("jsonData", jsonData.toString());
				parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
				parameters2.put("wUserIp",request.getRemoteAddr());
				parameters2.put("connUrl",request.getServletPath());
				temp.put("Status", "FAIL");
				temp.put("Status_Code", "P10");
				temp.put("Error_Msg",errorList+"has been ERROR or Empty");
				temp.put("Detail","");
				temp.put("BLNO", hawbNo);
				rtnVal.add(temp);
			}
		}
		parameters2.put("jsonHeader", jsonHeader.toString());
		parameters2.put("jsonData", jArray1.toString());
		parameters2.put("wUserId",orgStation+"_Insterment"+"Post");
		parameters2.put("wUserIp",request.getRemoteAddr());
		parameters2.put("connUrl",request.getServletPath());
		parameters2.put("rtnContents", rtnVal.toString());
		mapper.insertApiConn(parameters2); 
		return rtnVal;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> requestReturnList(HashMap<String, Object> parameters)
			throws Exception {
	
		return mapper.requestReturnList(parameters);
	}

	public int checkNation(HashMap<String, Object> transParameter) throws Exception{
		// TODO Auto-generated method stub
		return mapper.checkNation(transParameter);
	}
	@Override
	public HashMap<String, Object> spReturnApprv(HashMap<String, Object> prameterInfo) throws Exception {
		
		return mapper.spReturnApprv(prameterInfo);
	}

	@Override
	public HashMap<String, Object> spStockMsg(HashMap<String, Object> parameterInfo) throws Exception {

		return  mapper.spStockMsg(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> seletMsg(HashMap<String, Object> parameterInfo) {
		
		return mapper.seletMsg(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockNew(HashMap<Object, Object> parameterInfo) {
		
		return mapper.inspStockNew(parameterInfo);
	}

	@Override
	public int inspStocNewTotalCnt(HashMap<Object, Object> parameterInfo) {

		return mapper.inspStocNewTotalCnt(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockItemList(HashMap<Object, Object> stockParameter) {
		
		return mapper.inspStockItemList(stockParameter);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockList(HashMap<Object, Object> stockParameter2) {
		
		return mapper.inspStockList(stockParameter2);
	}

	@Override
	public  ArrayList<LinkedHashMap<String, Object>> inspStockAddList(HashMap<Object, Object> stockParameter2) {
	
		return mapper.inspStockAddList(stockParameter2);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockMsgList(HashMap<Object, Object> stockParameter2) {
	
		return mapper.inspStockMsgList(stockParameter2);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspStockImgList(HashMap<Object, Object> stockParameter2) {
		
		return mapper.inspStockImgList(stockParameter2);
	}

	
	@Override
	public void updateYslItemCode(String itemCode) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateYslItemCode(itemCode);
	}
	public ArrayList<LinkedHashMap<String, Object>> inspStockAddDetail(HashMap<Object, Object> stockParameter2) throws Exception {
	
		return mapper.inspStockAddDetail(stockParameter2);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectOrderMsg(HashMap<Object, Object> stockParameter) throws Exception {
		
		return mapper.selectOrderMsg(stockParameter);
	}

	@Override
	public int inspInUnknownCnt(HashMap<Object, Object> parameterInfo) throws Exception {
	
		return mapper.inspInUnknownCnt(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownList(HashMap<Object, Object> parameterInfo) throws Exception {

		return mapper.inspInUnknownList(parameterInfo);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownImg(HashMap<Object, Object> stockParameter2) throws Exception {

		return mapper.inspInUnknownImg(stockParameter2);
	}
	
	public void insertExpBaseInfo(ExpLicenceVO licence) throws Exception {
		mapper.insertExpBaseInfo(licence);
	}

	public void updateExpBaseInfo(ExpLicenceVO licence) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateExpBaseInfo(licence);
	}

	@Override
	public String selectUserKey(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserKey(userId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public ShipmentCreationResponse aramexApi(ApiOrderListVO apiOrderList, ArrayList<ApiOrderItemListVO> apiOrderItemList, String userId, String userIp) throws Exception{
		
		ShipmentCreationRequest shipments = new ShipmentCreationRequest();
		
		ApiOrderListVO temps = mapper.selectApiShipperInfo(apiOrderList.getUserId());
		Shipment[] shipment = new Shipment[1];
		shipment[0] = new Shipment();
		Party shipper = new Party(); //Shipments - Shipment - Shipper
		shipper.setReference1(apiOrderList.getShipperReference());
		shipper.setReference2("");
		shipper.setAccountNumber("172813");
		
		Address shipperPartyAddress = new Address(); //Shipments - Shipment - Shipper - partyAddress
		shipperPartyAddress.setLine1(apiOrderList.getShipperAddr());
		shipperPartyAddress.setLine2(apiOrderList.getShipperAddrDetail());
		shipperPartyAddress.setLine3("");
		shipperPartyAddress.setCity(apiOrderList.getShipperCity());
		shipperPartyAddress.setStateOrProvinceCode("");
		shipperPartyAddress.setPostCode(apiOrderList.getShipperZip());
		if(apiOrderList.getShipperCntry().equals("")) {
			apiOrderList.setShipperCntry(comnService.selectStationToNation(apiOrderList.getOrgStation()));
			shipperPartyAddress.setCountryCode(apiOrderList.getShipperCntry());
		}else {
			shipperPartyAddress.setCountryCode(apiOrderList.getShipperCntry());
		}
		shipper.setPartyAddress(shipperPartyAddress);
		
		Contact shipperContact = new Contact();//Shipments - Shipment - Shipper - Contact
		shipperContact.setDepartment("");
		shipperContact.setPersonName(apiOrderList.getShipperName());
		shipperContact.setTitle("");
		shipperContact.setCompanyName(apiOrderList.getShipperName());
		if(apiOrderList.getShipperHp() == null || "".equals(apiOrderList.getShipperHp())){
			apiOrderList.setShipperHp(apiOrderList.getShipperTel());
		}
		shipperContact.setPhoneNumber1(apiOrderList.getShipperTel());
		shipperContact.setPhoneNumber1Ext("");
		shipperContact.setPhoneNumber2("");
		shipperContact.setPhoneNumber2Ext("");
		shipperContact.setFaxNumber("");
		shipperContact.setCellPhone(apiOrderList.getShipperHp());
		shipperContact.setEmailAddress(apiOrderList.getShipperEmail());
		shipperContact.setType("");
		shipper.setContact(shipperContact);
		
		shipment[0].setShipper(shipper);
		//---------------------------------------- SHIPPER END -------------------------------
		
		Party consignee = new Party(); //Shipments - Shipment - Consignee
		consignee.setReference1(apiOrderList.getCneeReference1());
		consignee.setReference2(apiOrderList.getCneeReference2());
		if(apiOrderList.getUserId().toLowerCase().equals("gdakorea")) {
			if(apiOrderList.getPayment().equals("DDU")) {
				consignee.setAccountNumber("");
			}
		}else {
			consignee.setAccountNumber("");
		}
		
		Address consigneePartyAddress = new Address(); //Shipments - Shipment - Consignee - consigneePartyAddress
		consigneePartyAddress.setLine1(apiOrderList.getCneeAddr());
		consigneePartyAddress.setLine2(apiOrderList.getCneeAddrDetail());
		consigneePartyAddress.setLine3("");
		consigneePartyAddress.setCity(apiOrderList.getCneeCity());
		consigneePartyAddress.setStateOrProvinceCode("");
		consigneePartyAddress.setPostCode(apiOrderList.getCneeZip());
		consigneePartyAddress.setCountryCode(apiOrderList.getDstnNation());
		consignee.setPartyAddress(consigneePartyAddress);
		
		Contact consigneeContact = new Contact();//Shipments - Shipment - Consignee - consigneeContact
		consigneeContact.setDepartment("");
		consigneeContact.setPersonName(apiOrderList.getCneeName());
		consigneeContact.setTitle("");
		consigneeContact.setCompanyName(apiOrderList.getCneeName());
		consigneeContact.setPhoneNumber1(apiOrderList.getCneeTel());
		consigneeContact.setPhoneNumber1Ext("");
		consigneeContact.setPhoneNumber2("");
		consigneeContact.setPhoneNumber2Ext("");
		consigneeContact.setFaxNumber("");
		consigneeContact.setCellPhone(apiOrderList.getCneeHp());
		consigneeContact.setEmailAddress(apiOrderList.getCneeEmail());
		consigneeContact.setType("");
		consignee.setContact(consigneeContact);

		shipment[0].setConsignee(consignee);
		//---------------------------------------- CONSIGNEE END -------------------------------
		
		Party thirdParty = new Party(); //Shipments - Shipment - ThirdParty
		thirdParty.setReference1("");
		thirdParty.setReference2("");
		thirdParty.setAccountNumber("");
		
		Address thirdPartyPartyAddress = new Address(); //Shipments - Shipment - ThirdParty - thirdPartyPartyAddress
		thirdPartyPartyAddress.setLine1("");
		thirdPartyPartyAddress.setLine2("");
		thirdPartyPartyAddress.setLine3("");
		thirdPartyPartyAddress.setCity("");
		thirdPartyPartyAddress.setStateOrProvinceCode("");
		thirdPartyPartyAddress.setPostCode("");
		thirdPartyPartyAddress.setCountryCode("");
		thirdParty.setPartyAddress(thirdPartyPartyAddress);
		
		Contact thirdPartyContact = new Contact();//Shipments - Shipment - ThirdParty - thirdPartyContact
		thirdPartyContact.setDepartment("");
		thirdPartyContact.setPersonName("");
		thirdPartyContact.setTitle("");
		thirdPartyContact.setCompanyName("");
		thirdPartyContact.setPhoneNumber1("");
		thirdPartyContact.setPhoneNumber1Ext("");
		thirdPartyContact.setPhoneNumber2("");
		thirdPartyContact.setPhoneNumber2Ext("");
		thirdPartyContact.setFaxNumber("");
		thirdPartyContact.setCellPhone("");
		thirdPartyContact.setEmailAddress("");
		thirdPartyContact.setType("");
		thirdParty.setContact(thirdPartyContact);

		shipment[0].setThirdParty(thirdParty);
		//---------------------------------------- THIRD PARTY END -------------------------------
		shipment[0].setReference1("");
		shipment[0].setReference2("");
		shipment[0].setReference3("");
		shipment[0].setForeignHAWB(apiOrderList.getNno());
		shipment[0].setTransportType_x0020_(0);
		Calendar time = Calendar.getInstance();
		
		shipment[0].setShippingDateTime(time.getInstance()); //XX
		shipment[0].setDueDate(time.getInstance()); //XX
		shipment[0].setPickupLocation("");
		shipment[0].setPickupGUID("");
		if(apiOrderList.getOrderType().toUpperCase().equals("TAKEIN")) {
			shipment[0].setNumber(apiOrderList.getHawbNo());
		}
		shipment[0].setComments(apiOrderList.getDlvReqMsg());
		shipment[0].setAccountingInstrcutions("");
		shipment[0].setOperationsInstructions("");
		
		
		ShipmentDetails details = new ShipmentDetails(); //Shipments - Details
		Dimensions dimensions = new Dimensions(); //Shipments - Details - Dimensions
		if(apiOrderList.getUserLength().equals("")) {
			dimensions.setLength(0);
		}else {
			dimensions.setLength(Double.parseDouble(apiOrderList.getUserLength()));
		}
		
		if(apiOrderList.getUserWidth().equals("")) {
			dimensions.setWidth(0);
		}else {
			dimensions.setWidth(Double.parseDouble(apiOrderList.getUserWidth()));
		}
		
		if(apiOrderList.getUserHeight().equals("")) {
			dimensions.setHeight(0);
		}else {
			dimensions.setHeight(Double.parseDouble(apiOrderList.getUserHeight()));
		}
		
		
		dimensions.setUnit("CM");
		details.setDimensions(dimensions); 
		//---------------------------------------- DIMENSIONS END -------------------------------
		Weight actualWeight = new Weight(); //Shipments - Details - Weight
		actualWeight.setValue(Double.parseDouble(apiOrderList.getUserWta()));
		actualWeight.setUnit("KG");
		details.setActualWeight(actualWeight);
		
//		Weight chargeableWeight = new Weight(); //Shipments - Details - Weight
//		chargeableWeight.setUnit("");
//		chargeableWeight.setValue(0); 
//		details.setChargeableWeight(chargeableWeight);
		//---------------------------------------- ACTUALWEIGHT END -------------------------------
		
		if(apiOrderList.getCneeCntry().equals("SY")) {
			details.setProductGroup("DOM");
			details.setProductType("OND");
		}else {
			details.setProductGroup("EXP");
			details.setProductType("PPX");
		}
		details.setPaymentType("P");
		details.setPaymentOptions("");
		
		if(apiOrderList.getPayment().equals("DDP")) {
			details.setServices("FRDM");
		}else if(apiOrderList.getPayment().equals("DDU")) {
			details.setServices("");
		}else {
			details.setServices("");
		}
		
		details.setNumberOfPieces(Integer.parseInt(apiOrderList.getBoxCnt()));
		details.setDescriptionOfGoods(apiOrderItemList.get(0).getItemDetail());
		details.setGoodsOriginCountry(apiOrderItemList.get(0).getMakeCntry());
		
		Money cashOnDeliveryAmount = new Money(); //Shipments - Details - cashOnDeliveryAmount
		cashOnDeliveryAmount.setValue(0);
		cashOnDeliveryAmount.setCurrencyCode("USD");
		details.setCashOnDeliveryAmount(cashOnDeliveryAmount);
		//---------------------------------------- cashOnDeliveryAmount END -------------------------------
		
		Money insuranceAmount = new Money();
		insuranceAmount.setValue(0);
		insuranceAmount.setCurrencyCode("");
		details.setInsuranceAmount(insuranceAmount);
		//---------------------------------------- insuranceAmount END -------------------------------
		
		Money collectAmount = new Money();
		collectAmount.setValue(0);
		collectAmount.setCurrencyCode("USD");
		details.setCollectAmount(collectAmount);
		//---------------------------------------- collectAmount END -------------------------------		
		
		Money cashAdditionalAmount = new Money();
		cashAdditionalAmount.setValue(0);
		cashAdditionalAmount.setCurrencyCode("USD");
		details.setCashAdditionalAmount(cashAdditionalAmount);
		//---------------------------------------- cashAdditionalAmount END -------------------------------
		
		details.setCashAdditionalAmountDescription("");
		
		//---------------------------------------- customsValueAmount END -------------------------------
		double totalValue = 0;
		ShipmentItem[] items = new ShipmentItem[apiOrderItemList.size()];
		for(int i=0; i< apiOrderItemList.size(); i++) {
			items[i] = new ShipmentItem();
			items[i].setPackageType("Box");
			items[i].setQuantity(Integer.parseInt(apiOrderItemList.get(i).getItemCnt()));
			items[i].setComments(apiOrderItemList.get(i).getItemDiv());
			items[i].setGoodsDescription(apiOrderItemList.get(i).getItemDetail());
			items[i].setReference("");
			
//			Weight weight = new Weight();
//			weight.setUnit("KG");
//			weight.setValue(Double.parseDouble(apiOrderItemList.get(i).getUserWtaItem()));
//			
//			items[i].setWeight(weight);
			
			Money customValue = new Money();
			customValue.setValue(Double.parseDouble(apiOrderItemList.get(i).getUnitValue()));
			totalValue += customValue.getValue();
			customValue.setCurrencyCode(apiOrderItemList.get(i).getChgCurrency());
		}
		Money customsValueAmount = new Money();
		customsValueAmount.setValue(totalValue);
		customsValueAmount.setCurrencyCode("USD");
		details.setCustomsValueAmount(customsValueAmount);
		
		details.setItems(items);
		shipment[0].setDetails(details);
		shipments.setShipments(shipment);
		//---------------------------------------- SHIPMENT END -------------------------------
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setAccountCountryCode("KR");
		clientInfo.setAccountEntity("SEL");
		clientInfo.setAccountNumber("172813");
		clientInfo.setAccountPin("321321");
		clientInfo.setUserName("overseas2@aciexpress.net");
		clientInfo.setPassword("Aci5606!");
		clientInfo.setVersion("1.0");
		shipments.setClientInfo(clientInfo);
		//---------------------------------------- ClientInfo END -------------------------------
		
		Transaction transaction = new Transaction();
		transaction.setReference1("001");
		transaction.setReference2("");
		transaction.setReference3("");
		transaction.setReference4("");
		transaction.setReference5(""); 
		shipments.setTransaction(transaction);
		//---------------------------------------- Transaction END -------------------------------
		   
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setReportID(9201);  
		labelInfo.setReportType("URL");
		shipments.setLabelInfo(labelInfo);
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
//		ShipmentCreationRequest asdasd = new ShipmentCreationRequest(clientInfo, transaction, shipment, labelInfo);
		apiResult = apiProxy.createShipments(shipments);
		
		FileOutputStream fos = null;
		InputStream is = null;
		String ImageDir = realFilePath + "image/" + "aramex/";
		fos = new FileOutputStream(ImageDir+ apiResult.getShipments()[0].getID()+".PDF");
		
		try {
			URL url = new URL(apiResult.getShipments()[0].getShipmentLabel().getLabelURL());
			URLConnection urlConnection = url.openConnection();
			is = urlConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int readBytes;
			while((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer,0,readBytes);
			}
			
			fos.close();
			is.close();
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			File file = new File(ImageDir+ apiResult.getShipments()[0].getID()+".PDF");
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID(), file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			//file.delete();
			return apiResult;  
		}catch (Exception e) {
			// TODO: handle exception
			return apiResult;  
		}
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public ShipmentCreationResponse updateAramexApi(ApiOrderListVO apiOrderList, ArrayList<ApiOrderItemListVO> apiOrderItemList, HttpServletRequest request) throws Exception{
		ShipmentCreationRequest shipments = new ShipmentCreationRequest();
		Shipment[] shipment = new Shipment[1];
		shipment[0] = new Shipment();
		Party shipper = new Party(); //Shipments - Shipment - Shipper
		shipper.setReference1("");
		shipper.setReference2("");
		shipper.setAccountNumber("172813");

		Address shipperPartyAddress = new Address(); //Shipments - Shipment - Shipper - partyAddress
		shipperPartyAddress.setLine1(apiOrderList.getShipperAddr());
		shipperPartyAddress.setLine2(apiOrderList.getShipperAddrDetail());
		shipperPartyAddress.setLine3("");
		shipperPartyAddress.setCity(apiOrderList.getShipperCity());
		shipperPartyAddress.setStateOrProvinceCode("");
		shipperPartyAddress.setPostCode(apiOrderList.getShipperZip());
		shipperPartyAddress.setCountryCode(apiOrderList.getShipperCntry());
		shipper.setPartyAddress(shipperPartyAddress);
		
		Contact shipperContact = new Contact();//Shipments - Shipment - Shipper - Contact
		shipperContact.setDepartment("");
		shipperContact.setPersonName("AciWolrdWide");
		shipperContact.setTitle("");
		shipperContact.setCompanyName("ACI");
		if(apiOrderList.getShipperHp() == null || "".equals(apiOrderList.getShipperHp())){
			apiOrderList.setShipperHp(apiOrderList.getShipperTel());
		}
		shipperContact.setPhoneNumber1(apiOrderList.getShipperTel());
		shipperContact.setPhoneNumber1Ext("");
		shipperContact.setPhoneNumber2("");
		shipperContact.setPhoneNumber2Ext("");
		shipperContact.setFaxNumber("");
		shipperContact.setCellPhone(apiOrderList.getShipperHp());
		shipperContact.setEmailAddress(apiOrderList.getShipperEmail());
		shipperContact.setType("");
		shipper.setContact(shipperContact);
		
		shipment[0].setShipper(shipper);
		//---------------------------------------- SHIPPER END -------------------------------
		
		Party consignee = new Party(); //Shipments - Shipment - Consignee
		consignee.setReference1("");
		consignee.setReference2("");
		consignee.setAccountNumber("");
		
		Address consigneePartyAddress = new Address(); //Shipments - Shipment - Consignee - consigneePartyAddress
		consigneePartyAddress.setLine1(apiOrderList.getCneeAddr());
		consigneePartyAddress.setLine2(apiOrderList.getCneeAddrDetail());
		consigneePartyAddress.setLine3("");
		consigneePartyAddress.setCity(apiOrderList.getCneeCity());
		consigneePartyAddress.setStateOrProvinceCode("");
		consigneePartyAddress.setPostCode(apiOrderList.getCneeZip());
		consigneePartyAddress.setCountryCode(apiOrderList.getCneeCntry());
		consignee.setPartyAddress(consigneePartyAddress);
		
		Contact consigneeContact = new Contact();//Shipments - Shipment - Consignee - consigneeContact
		consigneeContact.setDepartment("");
		consigneeContact.setPersonName(apiOrderList.getCneeName());
		consigneeContact.setTitle("");
		consigneeContact.setCompanyName(apiOrderList.getCneeName());
		consigneeContact.setPhoneNumber1(apiOrderList.getCneeTel());
		consigneeContact.setPhoneNumber1Ext("");
		consigneeContact.setPhoneNumber2("");
		consigneeContact.setPhoneNumber2Ext("");
		consigneeContact.setFaxNumber("");
		consigneeContact.setCellPhone(apiOrderList.getCneeHp());
		consigneeContact.setEmailAddress(apiOrderList.getCneeEmail());
		consigneeContact.setType("");
		consignee.setContact(consigneeContact);

		shipment[0].setConsignee(consignee);
		//---------------------------------------- CONSIGNEE END -------------------------------
		
		Party thirdParty = new Party(); //Shipments - Shipment - ThirdParty
		thirdParty.setReference1("");
		thirdParty.setReference2("");
		thirdParty.setAccountNumber("");
		
		
		Address thirdPartyPartyAddress = new Address(); //Shipments - Shipment - ThirdParty - thirdPartyPartyAddress
		thirdPartyPartyAddress.setLine1("");
		thirdPartyPartyAddress.setLine2("");
		thirdPartyPartyAddress.setLine3("");
		thirdPartyPartyAddress.setCity("");
		thirdPartyPartyAddress.setStateOrProvinceCode("");
		thirdPartyPartyAddress.setPostCode("");
		thirdPartyPartyAddress.setCountryCode("");
		thirdParty.setPartyAddress(thirdPartyPartyAddress);

		Contact thirdPartyContact = new Contact();//Shipments - Shipment - ThirdParty - thirdPartyContact
		thirdPartyContact.setDepartment("");
		thirdPartyContact.setPersonName("");
		thirdPartyContact.setTitle("");
		thirdPartyContact.setCompanyName("");
		thirdPartyContact.setPhoneNumber1("");
		thirdPartyContact.setPhoneNumber1Ext("");
		thirdPartyContact.setPhoneNumber2("");
		thirdPartyContact.setPhoneNumber2Ext("");
		thirdPartyContact.setFaxNumber("");
		thirdPartyContact.setCellPhone("");
		thirdPartyContact.setEmailAddress("");
		thirdPartyContact.setType("");
		thirdParty.setContact(thirdPartyContact);
		
		shipment[0].setThirdParty(thirdParty);
		//---------------------------------------- THIRD PARTY END -------------------------------
		shipment[0].setReference1("");
		shipment[0].setReference2("");
		shipment[0].setReference3("");
		shipment[0].setForeignHAWB(apiOrderList.getNno()+"1");
		shipment[0].setTransportType_x0020_(0);
		Calendar time = Calendar.getInstance();
		
		shipment[0].setShippingDateTime(time.getInstance()); //XX
		shipment[0].setDueDate(time.getInstance()); //XX
		shipment[0].setPickupLocation("");
		shipment[0].setPickupGUID("");
		//shipment[0].setNumber(apiOrderList.getHawbNo());
		shipment[0].setNumber("32835289104");
		shipment[0].setComments(apiOrderList.getDlvReqMsg());
		shipment[0].setAccountingInstrcutions("");
		shipment[0].setOperationsInstructions("");	
		
		ShipmentDetails details = new ShipmentDetails(); //Shipments - Details
		Dimensions dimensions = new Dimensions(); //Shipments - Details - Dimensions
		dimensions.setLength(Double.parseDouble(apiOrderList.getUserLength()));
		dimensions.setWidth(Double.parseDouble(apiOrderList.getUserWidth()));
		dimensions.setHeight(Double.parseDouble(apiOrderList.getUserHeight()));
		dimensions.setUnit("CM");
		details.setDimensions(dimensions); 
		//---------------------------------------- DIMENSIONS END -------------------------------
		Weight actualWeight = new Weight(); //Shipments - Details - Weight
		actualWeight.setValue(15.6);
		actualWeight.setUnit("KG");
		details.setActualWeight(actualWeight);
//		Weight chargeableWeight = new Weight(); //Shipments - Details - Weight
//		chargeableWeight.setUnit("");
//		chargeableWeight.setValue(0); 
//		details.setChargeableWeight(chargeableWeight);
		//---------------------------------------- ACTUALWEIGHT END -------------------------------
		details.setProductGroup("EXP");
		details.setProductType("PPX");
		details.setPaymentType("P");
		details.setPaymentOptions("");
		details.setServices("");
		details.setNumberOfPieces(Integer.parseInt(apiOrderItemList.get(0).getItemCnt()));
		details.setDescriptionOfGoods(apiOrderItemList.get(0).getItemDetail());
		details.setGoodsOriginCountry(apiOrderItemList.get(0).getMakeCntry());
		
		Money cashOnDeliveryAmount = new Money(); //Shipments - Details - cashOnDeliveryAmount
		cashOnDeliveryAmount.setValue(0);
		cashOnDeliveryAmount.setCurrencyCode("USD");
		details.setCashOnDeliveryAmount(cashOnDeliveryAmount);
		//---------------------------------------- cashOnDeliveryAmount END -------------------------------
		
		Money insuranceAmount = new Money();
		insuranceAmount.setValue(0);
		insuranceAmount.setCurrencyCode("");
		details.setInsuranceAmount(insuranceAmount);
		//---------------------------------------- insuranceAmount END -------------------------------
		
		Money collectAmount = new Money();
		collectAmount.setValue(0);
		collectAmount.setCurrencyCode("USD");
		details.setCollectAmount(collectAmount);
		//---------------------------------------- collectAmount END -------------------------------		
		
		Money cashAdditionalAmount = new Money();
		cashAdditionalAmount.setValue(0);
		cashAdditionalAmount.setCurrencyCode("USD");
		details.setCashAdditionalAmount(cashAdditionalAmount);
		//---------------------------------------- cashAdditionalAmount END -------------------------------
		
		details.setCashAdditionalAmountDescription("");
		
		//---------------------------------------- customsValueAmount END -------------------------------
		double totalValue = 0;
		ShipmentItem[] items = new ShipmentItem[apiOrderItemList.size()];
		for(int i=0; i< apiOrderItemList.size(); i++) {
			items[i] = new ShipmentItem();
			items[i].setPackageType("Box");
			items[i].setQuantity(Integer.parseInt(apiOrderItemList.get(i).getItemCnt()));
			items[i].setComments(apiOrderItemList.get(i).getItemDiv());
			items[i].setGoodsDescription(apiOrderItemList.get(i).getItemDetail());
			items[i].setReference("");
			
//			Weight weight = new Weight();
//			weight.setUnit("KG");
//			weight.setValue(Double.parseDouble(apiOrderItemList.get(i).getUserWtaItem()));
//			
//			items[i].setWeight(weight);
			
			Money customValue = new Money();
			customValue.setValue(Double.parseDouble(apiOrderItemList.get(i).getUnitValue()) * Integer.parseInt(apiOrderItemList.get(i).getItemCnt()));
			totalValue += customValue.getValue();
			customValue.setCurrencyCode(apiOrderItemList.get(i).getChgCurrency());
		}
		Money customsValueAmount = new Money();
		customsValueAmount.setValue(totalValue);
		customsValueAmount.setCurrencyCode("USD");
		details.setCustomsValueAmount(customsValueAmount);
		
		details.setItems(items);
		
		shipment[0].setDetails(details);
		shipments.setShipments(shipment);
		//---------------------------------------- SHIPMENT END -------------------------------
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setAccountCountryCode("KR");
		clientInfo.setAccountEntity("SEL");
		clientInfo.setAccountNumber("172813");
		clientInfo.setAccountPin("321321");
		clientInfo.setUserName("overseas2@aciexpress.net");
		clientInfo.setPassword("Aci5606!");
		clientInfo.setVersion("1.0");
		shipments.setClientInfo(clientInfo);
		//---------------------------------------- ClientInfo END -------------------------------
		
		Transaction transaction = new Transaction();
		transaction.setReference1("001");
		transaction.setReference2("");
		transaction.setReference3("");
		transaction.setReference4("");
		transaction.setReference5(""); 
		shipments.setTransaction(transaction);
		//---------------------------------------- Transaction END -------------------------------
		   
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setReportID(9201);  
		labelInfo.setReportType("URL");
		shipments.setLabelInfo(labelInfo);
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
//		ShipmentCreationRequest asdasd = new ShipmentCreationRequest(clientInfo, transaction, shipment, labelInfo);
		apiResult = apiProxy.createShipments(shipments);
		
		FileOutputStream fos = null;
		InputStream is = null;
		String ImageDir = request.getSession().getServletContext().getRealPath("/") + "image/" + "aramex/";
		fos = new FileOutputStream(ImageDir+ apiResult.getShipments()[0].getID()+".PDF");
		
		try {
			URL url = new URL(apiResult.getShipments()[0].getShipmentLabel().getLabelURL());
			URLConnection urlConnection = url.openConnection();
			is = urlConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int readBytes;
			while((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer,0,readBytes);
			}
			
			fos.close();
			is.close();
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			File file = new File(ImageDir+ apiResult.getShipments()[0].getID()+".PDF");
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID(), file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			file.delete();
			return apiResult;  
		}catch (Exception e) {
			// TODO: handle exception
			return apiResult;  
		}
	}
	

	@Override
	public String selectUserAllowIp(String userID, String remoteAddr) throws Exception {
		return mapper.selectUserAllowIp(userID, remoteAddr);
	}
	
	@Override
	public ArrayList<LinkedHashMap<String, Object>> blPodtest(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {

		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

		String apiUserId = "TEST";
		String apiUserIp = request.getRemoteAddr();
		LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JSONArray jArrayData = new JSONArray(jsonData);

		HashMap<String, Object> bl = new HashMap<String, Object>();

		// JrUGT5p4c9ZmO75BNlD7XjrLn9z96ouRnWWXhi1WcDE=
		if(jArrayData.length()>50) {
			
			RstHashMap.put("Error", "-900");
			RstHashMap.put("ErrorMsg", "요청 개수가 너무 많습니다. (최대 50)");
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", "");
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			
			
			return rtnJsonArray;
		}
		for (int trkIndex = 0; trkIndex < jArrayData.length(); trkIndex++) {

			JSONObject itemObj = jArrayData.getJSONObject(trkIndex);
			if(itemObj.has("bl")) {
				bl.put("BL", itemObj.get("bl"));
			}else if(itemObj.has("BL")) {
				bl.put("BL", itemObj.get("BL"));
			}else if(itemObj.has("Bl")) {
				bl.put("BL", itemObj.get("Bl"));
			}else if(itemObj.has("BL_No")) {
				bl.put("BL", itemObj.get("BL_No"));
			}else if(itemObj.has("BL_NO")) {
				bl.put("BL", itemObj.get("BL_NO"));
			}
			//bl.put("BL", itemObj.get("bl"));
			RstHashMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> podDetatil = new LinkedHashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray = new ArrayList<HashMap<String, Object>>();

			
			String HawbNo = (String) bl.get("BL");
			if (HawbNo.equals("") || HawbNo == null) {
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				rtnJsonArray.add(RstHashMap);
				continue;
			}

			HashMap<String, Object> BlInfo = new HashMap<String, Object>();

			BlInfo = mapper.selectPodBlInfo(HawbNo);
			if (BlInfo == null) {
				podDetatil = new LinkedHashMap<String, Object>();
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				rtnJsonArray.add(RstHashMap);
				continue;
			}

			RstHashMap.put("Bl", BlInfo.get("HAWB_NO"));
			RstHashMap.put("OrderNo", BlInfo.get("ORDER_NO"));
			RstHashMap.put("Consignee", BlInfo.get("CNEE_NAME"));
			RstHashMap.put("WarehousingDate", BlInfo.get("WH_IN_DATE"));
			RstHashMap.put("ShippingDate", BlInfo.get("DEP_DATE"));
			RstHashMap.put("DeliveryCompany", BlInfo.get("DeliveryCompany"));

			String getTransCode = "";
			getTransCode = (String) BlInfo.get("TRANS_CODE");

			if (getTransCode.equals("SAGAWA")) {

			} else if (getTransCode.equals("ARA")) {
				TrackingClientInfo clientInfo = new TrackingClientInfo();
				clientInfo.setAccountCountryCode("KR");
				clientInfo.setAccountEntity("SEL");
				clientInfo.setAccountNumber("172813");
				clientInfo.setAccountPin("321321");
				clientInfo.setUserName("overseas2@aciexpress.net");
				clientInfo.setPassword("Aci5606!");
				clientInfo.setVersion("1.0");
				// shipments.setClientInfo(clientInfo);

				net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
				transaction.setReference1("001");

				TrackingShipmentRequest trakShip = new TrackingShipmentRequest();

				trakShip.setClientInfo(clientInfo);
				trakShip.setTransaction(transaction);

				String[] trakBL = new String[1];
				trakBL[0] = HawbNo;

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
					podDetatil.put("ProblemCode", "-22");
					podDetatil.put("Comments", "No Data.");
					podDetatailArray.add(podDetatil);
				} else {
					trkRst = rtnTracking.getTrackingResults()[0].getValue();
					for (int i = 0; i < trkRst.length; i++) {
						// System.out.println(rtnTracking.getTrackingResults()[i].getKey());
						podDetatil = new LinkedHashMap<String, Object>();
						podDetatil.put("UpdateCode", trkRst[i].getUpdateCode());
						podDetatil.put("UpdateDateTime", trkRst[i].getUpdateDateTime());
						podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
						podDetatil.put("UpdateDescription", trkRst[i].getUpdateDescription());
						podDetatil.put("ProblemCode", trkRst[i].getProblemCode());
						podDetatil.put("Comments", trkRst[i].getComments());

						podDetatailArray.add(podDetatil);

					}
				}
			} else if (getTransCode.equals("YSL")) {
				String rtnJson = ysApi.makeYoungSungPodKR(HawbNo);
				String rtnJson2 = ysApi.makeYoungSungPodEN(HawbNo);
				podDetatailArray = ysApi.makePodDetatailArray(rtnJson, rtnJson2, HawbNo, request);

			} else if (getTransCode.equals("OCS")) {
				String rtnJson = ocsApi.makeOCSPod(HawbNo);
				podDetatailArray = ocsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("EFS")) {
				String rtnJson = efsApi.makeEfsPod(HawbNo);
				podDetatailArray = efsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("SEK")) {
				podDetatailArray = sekoApi.makeSekoPod(HawbNo);
			} else if (getTransCode.equals("CJ")) {
				podDetatailArray = cjapi.makePodDetailArray(HawbNo);
			}

			RstHashMap.put("TraceStatus", podDetatailArray);

			rtnJsonArray.add(RstHashMap);
		}
		parameters.put("jsonHeader", "");
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId", apiUserId);
		parameters.put("wUserIp", apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", rtnJsonArray.toString());
		return rtnJsonArray;
	}

	public String selectDefaultTransCom(HashMap<String, Object> tempparameters) throws Exception{
		// TODO Auto-generated method stub
		return mapper.selectDefaultTransCom(tempparameters);
	}

	@Override
	public ArrayList<LinkedHashMap<String,Object>> selectUserArea(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserArea(userId);
	}
	
	public Map<String, Object> applyTransfer(ApiOrderListVO apiOrderList,
			ArrayList<ApiOrderItemListVO> apiOrderItemList, String apiUserId, String apiUserIp,
			String transCodeByRemark, HttpServletRequest request, Map<String, Object> returnVal, String expRegNo) throws Exception {
		BlApplyVO transComResult = new BlApplyVO();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		
		if(transCodeByRemark.equals("ARA")) {
			apiResult = aramexApi(apiOrderList,apiOrderItemList, apiUserId, apiUserIp);
			if(apiResult.getHasErrors()) {
				String notifi = "";
				
				for(int i = 0; i < apiResult.getShipments()[0].getNotifications().length; i++) {
					notifi += apiResult.getShipments()[0].getNotifications()[i].getCode();
				}
				String notifiMsg = "";
				for(int i = 0; i < apiResult.getShipments()[0].getNotifications().length; i++) {
					notifiMsg += apiResult.getShipments()[0].getNotifications()[i].getMessage();
				}
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",notifiMsg);
				returnVal.put("Status_Code","ARA");
				throw new Exception();
			}else {
				apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
			}
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			returnVal.put("funcRtn","Success");
			expRegNo = apiOrderList.getHawbNo();
			insertApiOrderListQueue(apiOrderList,apiOrderItemList);
		}else if(transCodeByRemark.equals("YSL")){//용성 가능
			
			//용성 등록 로직
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);//수정
			
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			
			if(request.getServletPath().contains("Modify")) {
				transComResult.setHawbNo(apiOrderList.getHawbNo());
				mapper.updateHawb(transComResult);
			}
			
			String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
			ProcedureVO rtnValYS = new ProcedureVO();
			rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
			String result = rtnValYS.getRstStatus();
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			expRegNo = rtnValYS.getRstHawbNo();
			
			if(result.equals("FAIL")) {
				returnVal.put("Error_Msg",rtnValYS.getRstMsg());
				if(rtnValYS.getRstCode().equals("D30")) {
					returnVal.put("Status_Code","D30");
				}else {
					returnVal.put("Status_Code","API ERROR");
				}
				returnVal.put("funcRtn","Fail");
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			returnVal.put("funcRtn","Success");
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			
			/*
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
			//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			*/
		}else if(transCodeByRemark.equals("OCS")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
			ProcedureVO rtnValOcs = new ProcedureVO();
			
			rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
			String result = rtnValOcs.getRstStatus();
			if(result.equals("FAIL")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",rtnValOcs.getRstMsg());
				returnVal.put("Status_Code","API ERROR");
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(rtnValOcs.getRstHawbNo());
			expRegNo = apiOrderList.getHawbNo();
			returnVal.put("funcRtn","Success");
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
		}else if(transCodeByRemark.equals("EFS")){
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			if(request.getServletPath().contains("Modify")) {
				transComResult.setHawbNo(apiOrderList.getHawbNo());
				mapper.updateHawb(transComResult);
			}
			
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
			ProcedureVO rtnValEfs = new ProcedureVO();
			rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
			String result = rtnValEfs.getRstStatus();
			
			expRegNo = rtnValEfs.getRstHawbNo();
			
			if(result.equals("FAIL")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",rtnValEfs.getRstMsg());
				if(rtnValEfs.getRstCode().equals("D30")) {
					returnVal.put("Status_Code","D30");
				}else {
					returnVal.put("Status_Code","API ERROR");
				}
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			expRegNo = rtnValEfs.getRstHawbNo();
			returnVal.put("funcRtn","Success");
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
		}else if(transCodeByRemark.equals("ACI")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			//returnVal.put()
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
			//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
		}else if(transCodeByRemark.equals("ACI-US")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
		}else if(transCodeByRemark.equals("FB") || transCodeByRemark.equals("FB-EMS")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn", "Fail");
				returnVal.put("Error_Msg", transComResult.getRstMsg());
				returnVal.put("Status_Code", transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			returnVal.put("funcRtn", "Success");
			expRegNo = transComResult.getHawbNo();
		}else if(transCodeByRemark.equals("EPT")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			
			if(request.getServletPath().contains("Modify")) {
				transComResult.setHawbNo(apiOrderList.getHawbNo());
				mapper.updateHawb(transComResult);
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
			//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
			comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
		}else if(transCodeByRemark.equals("SEK")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			expRegNo = transComResult.getHawbNo();
			//comnApi.comnSaveHawb(request, apiOrderList.getNno());
			//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
		}else if(transCodeByRemark.equals("CJ")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			
			if(!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			//cjHandler.storeCJLabel(apiOrderList, apiOrderItemList);
			//returnVal.put()
			//comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
		} else if (transCodeByRemark.equals("HJ")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			
			if (!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
		} else if (transCodeByRemark.equals("YT")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if (!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
		} else if (transCodeByRemark.equals("VNP")) {
			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
			apiOrderList.encryptData();
			transComResult = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
			if (!transComResult.getStatus().equals("SUCCESS")) {
				returnVal.put("funcRtn","Fail");
				returnVal.put("Error_Msg",transComResult.getRstMsg());
				returnVal.put("Status_Code",transComResult.getRstCode());
				fn_TransComApply_rollback(apiOrderList);
				throw new Exception();
			}
			apiOrderList.setHawbNo(transComResult.getHawbNo());
			returnVal.put("funcRtn","Success");
			expRegNo = transComResult.getHawbNo();
		}
			
		
		return returnVal;
	}

	@Override
	public LinkedHashMap<String, Object> whinPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
		
		LinkedHashMap<String, Object> rtnJson = new LinkedHashMap<String, Object>();
		String aciAuthKey = "56AB8817E3E4F6AED22AD8AB61A3BD68";
		LinkedHashMap<String, Object> rstHashMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> blList = new ArrayList<LinkedHashMap<String,Object>>();
		
		Map<String, Object> temp = new HashMap<String, Object>();
		temp = checkWhinPostInfo(jsonHeader, request, aciAuthKey);
		if (!temp.isEmpty()) {
			Iterator iter = temp.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				rstHashMap.put(key, temp.get(key));
			}
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jsonData.toString());
			parameters.put("wUserId", "eshop");
			parameters.put("wUserIp", "eshop");
			parameters.put("connUrl", request.getServletPath());
			parameters.put("rtnContents", rstHashMap.toString());
			mapper.insertApiConn(parameters);
			return rstHashMap;
		}

		JSONObject jsonObject = new JSONObject(jsonData);
		if (jsonObject.has("DepDate")) {
			String depDate = (String) jsonObject.get("DepDate");
			if (depDate.equals("") || depDate == null) {
				sqlParams.put("DepDate", "");
			} else {
				sqlParams.put("DepDate", depDate);
			}
		} else {
			rstHashMap.put("StatusCode", "L40");
			rstHashMap.put("ErrorMsg", "Not found DepDate field");
			rstHashMap.put("DataList", blList);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jsonData.toString());
			parameters.put("wUserId", "eshop");
			parameters.put("wUserIp", "eshop");
			parameters.put("connUrl", request.getServletPath());
			parameters.put("rtnContents", rstHashMap.toString());
			mapper.insertApiConn(parameters);
			return rstHashMap;
		}
		
		if (jsonObject.has("Station")) {
			String station = (String) jsonObject.get("Station");
			
			if (station.equals("") || station == null) {
				rstHashMap.put("StatusCode", "L60");
				rstHashMap.put("ErrorMsg", "Station value is empty");
				rstHashMap.put("DataList", blList);
				parameters.put("jsonHeader", jsonHeader.toString());
				parameters.put("jsonData", jsonData.toString());
				parameters.put("wUserId", "eshop");
				parameters.put("wUserIp", "eshop");
				parameters.put("connUrl", request.getServletPath());
				parameters.put("rtnContents", rstHashMap.toString());
				mapper.insertApiConn(parameters);
				return rstHashMap;
			} else {
				sqlParams.put("Station", station);
				int cnt = mapper.selectStationCodeYn(sqlParams);
				if (cnt == 0) {
					rstHashMap.put("StatusCode", "L70");
					rstHashMap.put("ErrorMsg", station + " Station Code is not exists");
					rstHashMap.put("DataList", blList);
					parameters.put("jsonHeader", jsonHeader.toString());
					parameters.put("jsonData", jsonData.toString());
					parameters.put("wUserId", "eshop");
					parameters.put("wUserIp", "eshop");
					parameters.put("connUrl", request.getServletPath());
					parameters.put("rtnContents", rstHashMap.toString());
					mapper.insertApiConn(parameters);
					return rstHashMap;
				}
			}
		} else {
			rstHashMap.put("StatusCode", "L50");
			rstHashMap.put("ErrorMsg", "Not found Station field");
			rstHashMap.put("DataList", blList);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jsonData.toString());
			parameters.put("wUserId", "eshop");
			parameters.put("wUserIp", "eshop");
			parameters.put("connUrl", request.getServletPath());
			parameters.put("rtnContents", rstHashMap.toString());
			mapper.insertApiConn(parameters);
			return rstHashMap;
		}

		ArrayList<LinkedHashMap<String, Object>> blInfo = new ArrayList<LinkedHashMap<String,Object>>();
		blInfo = mapper.selectBlInfo(sqlParams);
		
		for (int i = 0; i < blInfo.size(); i++) {
			LinkedHashMap<String, Object> blMap = new LinkedHashMap<String, Object>();
			blMap.put("HawbNo", blInfo.get(i).get("HAWB_NO").toString());
			blMap.put("BoxCnt", blInfo.get(i).get("BOX_CNT").toString());
			blList.add(blMap);
		}
		
		rstHashMap.put("StatusCode", "S10");
		rstHashMap.put("ErrorMsg", "");
		rstHashMap.put("DataList", blList);
		
		return rstHashMap;
	}

	private Map<String, Object> checkWhinPostInfo(Map<String, Object> jsonHeader, HttpServletRequest request, String aciAuthKey) {
		Map<String, Object> temp = new HashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> blList = new ArrayList<LinkedHashMap<String,Object>>();
		String aciApiKey = jsonHeader.get("acikey").toString();
		
		try {
			String apiKeyDecrypt = AES256Cipher.AES_Decode(aciApiKey, aciAuthKey);
			String[] apiKeyDecryptList = apiKeyDecrypt.split("[|]");
			
			if (!apiKeyDecryptList[0].toUpperCase().equals(aciAuthKey.toUpperCase())) {
				temp.put("StatusCode", "L10");
				temp.put("ErrorMsg", "API KEY IS NOT MATCHING");
				temp.put("DataList", blList);
				return temp;
			}
			
			String from = apiKeyDecryptList[1].toLowerCase();
			boolean regex = Pattern.matches(YYYYMMDDHHMISS, from);
			
			if (!regex) {
				temp.put("StatusCode", "L20");
				temp.put("ErrorMsg", "DATE FORMAT IS NOT MATCHING");
				temp.put("DataList", blList);
				return temp;
			}
		} catch(IllegalBlockSizeException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			temp.put("ErrorMsg","ACI KEY IS NOT SUPPORTED");
			temp.put("StatusCode","L30");
			temp.put("DataList", blList);
			return temp; 
		} catch (Exception e) {
			temp.put("ErrorMsg",e.toString());
			temp.put("StatusCode","D10");
			temp.put("DataList", blList);
			return temp; 
		}
		
		return temp;
	}

	@Override
	public void insertApiConnChk(HashMap<String, Object> apiParams) throws Exception {
		mapper.insertApiConnChk(apiParams);
	}

	@Override
	public LinkedHashMap<String, Object> selectBlPrint(Map<String, Object> jsonHeader, Map<String, Object> jsonData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		Map<String, Object> temp = new HashMap<String, Object>();

		temp = chkApiHeader(jsonHeader, request);
		
		if (!temp.isEmpty()) {
			Iterator iter = temp.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				rstMap.put(key, temp.get(key));
			}
			
			return rstMap;
		}
		
		JSONObject requestJson = new JSONObject(jsonData);
		String printType = requestJson.optString("print_type");
		JSONArray jArray = new JSONArray(jsonData.get("BLNO").toString());
		ArrayList<LinkedHashMap<String, Object>> rtnArray = new ArrayList<LinkedHashMap<String, Object>>();
		
		try {
		
			for (int i = 0; i < jArray.length(); i++) {
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				LinkedHashMap<String, Object> rtnJson = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> blInfo = new LinkedHashMap<String, Object>();
				
				String hawbNo = jArray.optString(i);
				parameters.put("hawbNo", hawbNo);
				
				int blCnt = mapper.selectCheckBlCnt(parameters);
				if (blCnt == 1) {
					blInfo = mapper.selectBlPrintInfo(parameters);
					String transCode = blInfo.get("transCode").toString();
					String base64Print = printHawbLegacy(request, response, transCode, blInfo);
					
					String printSize = "";
					switch (transCode) {
					case "YSL":
						printSize = "31";
						break;
					case "EFS":
						printSize = "31";
						break;
					case "EPT":
						printSize = "64";
						break;
					case "CJ":
						printSize = "54";
						break;
					case "HJ":
						printSize = "54";
						break;
					default :
						printSize = "46";
						break;
					}
					
					rtnJson.put("BlStatus", "Success");
					rtnJson.put("TransCode", transCode);
					rtnJson.put("PrintType", printSize);
					rtnJson.put("BlNo", hawbNo);
					rtnJson.put("BlPdf", base64Print);
				} else {
					rtnJson.put("BlStatus", "Fail");
					rtnJson.put("TransCode", "");
					rtnJson.put("PrintType", "");
					rtnJson.put("BlNo", hawbNo);
					rtnJson.put("BlPdf", "");
				}
				
				rtnArray.add(rtnJson);
			}
		
		rstMap.put("status", "S10");
		rstMap.put("result", rtnArray);
		
		} catch (Exception e) {
			rstMap.put("status", "D10");
			rstMap.put("result", e.toString());
			return rstMap;
		}

		return rstMap;
	}

	private Map<String, Object> chkApiHeader(Map<String, Object> jsonHeader, HttpServletRequest request) throws Exception {
		Map<String, Object> temp = new HashMap<String, Object>();
		String aciKey = "3AF35C5E33E439E612576618CFAAE1C1";
		
		try {

			if (!jsonHeader.containsKey("acikey")) {
				temp.put("result", "ACI KEY IS NOT EXISTS");
				temp.put("status", "L10");
				return temp;
			}
			
			if (!jsonHeader.get("content-type").equals("application/json")) {
				temp.put("result","In case content type is not JSON type");
				temp.put("status","L20");
				return temp;
			}
			
			if (!jsonHeader.get("acikey").equals(aciKey)) {
				temp.put("result", "ACI KEY IS NOT MATCHING");
				temp.put("status", "L30");
				return temp;
			}
		} catch (Exception e) {
			temp.put("result", e.toString());
			temp.put("status", "D10");
			return temp;
		}
		
		
		return temp;
	}

	private String printHawbLegacy(HttpServletRequest request, HttpServletResponse response, String transCode, LinkedHashMap<String, Object> blInfo) throws Exception {
		String printImage = "";

		switch (transCode) {
			case "ARA":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "FED":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "EMN":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "EMS":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "USP":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "UPS":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "DHL":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "FEG":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "FES":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "ITC":
				printImage = loadPdf(request, response, blInfo);
				break;
			case "YSL":
				printImage = yslPdf(request, response, blInfo);
				break;
			case "ACI":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "YES":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "SAGAWA":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "OCS":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "EPT":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "EFS":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "ETCS":
				printImage = printPdfLegacy(request, response, blInfo);
				break;
			case "CJ":
				//printImage = printPdfLegacy(request, response, blInfo);
				printImage = cjPdf(request, response, blInfo);
				break;
			case "FB":
				printImage = fbPdf(request, response, blInfo);
				break;
			case "FB-EMS":
				printImage = fbPdf(request, response, blInfo);
				break;
			case "HJ":
				printImage = hjPrint(request, response, blInfo);
				break;
			case "ACI-US":
				printImage = loadPdf(request, response, blInfo);
				break;
			default:
				printImage = printPdfLegacy(request, response, blInfo);
				break;
		}
		
		return printImage;
	}
	
	private String cjPdf(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) {
		
		try {

			CJInfo cjInfo = cjHandler.checkCJTokenInfo();
			String tokenNum = cjInfo.getTokenNo();
			
			String pdfPath = realFilePath + "pdf";
			String barcodePath = pdfPath + "/barcode/";
			File dir = new File(barcodePath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			
			float mm = 2.83465f;
			int totalPage = 0;
			
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String fontPath = cssResource.getURI().getPath().substring(0, cssResource.getURI().getPath().lastIndexOf("/"));
			InputStream fontStream = new FileInputStream(fontPath + "/static/fonts/NotoSansKR-Bold.ttf");
			
			PDDocument doc = new PDDocument();
			
			PDType0Font fontBold = PDType0Font.load(doc, fontStream);
			
			String nno = (String) blInfo.get("nno");

			HashMap<String, Object> sqlParams = new HashMap<>();
			sqlParams.put("nno", nno);
			Order order = logisticsMapper.selectCJLabelData(sqlParams);
			order.dncryptData();
			
			String hawbNo = order.getHawbNo();
			String hawbNoFmt = hawbNo.substring(0,4) + "-" + hawbNo.substring(4,8) + "-" + hawbNo.substring(8,12);
			
			String shipperTel = order.getShipperTel().replaceAll("[^0-9]", "");
			String[] sendrTelNo = getTelNumberSplit(shipperTel);
			String shipperName = order.getShipperName();
			String shipperAddr = order.getShipperAddr().trim();
			if (!"".equals(order.getShipperAddrDetail())) {
				shipperAddr += " " + order.getShipperAddrDetail().trim();
			}
			
			String cneeTel = order.getCneeTel().replaceAll("[^0-9]", "");
			String cneeHp = order.getCneeHp().replaceAll("[^0-9]", "");
			String[] rcvrTelNo = getTelNumberSplit(cneeTel);
			String[] rcvrCellNo = getTelNumberSplit(cneeHp);
			String cneeName = order.getCneeName().substring(0,order.getCneeName().length()-1) + "*";
			String cneeAddr = order.getCneeAddr().trim();
			if (!"".equals(order.getCneeAddrDetail())) {
				cneeAddr += " " + order.getCneeAddrDetail().trim();
			}
			
			CJParameter cjParameter = cjHandler.requestRefinedAddress(tokenNum, cneeAddr);
			System.out.println(cjParameter);
			
			if ("S".equals(cjParameter.getResultCd())) {
				
				String cllDlvText = cjParameter.getCllDlvBranNm() + "-" + cjParameter.getCllDlvEmpNickNm();
				
				for (int subIndex = 0; subIndex < order.getBoxCnt(); subIndex++) {
					
					int outputCnt = subIndex + 1;
					String outputStr = String.valueOf(outputCnt)+"/"+order.getBoxCnt();
							
					PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
					PDPage blankPage = new PDPage(pageSize);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(totalPage);
					PDPageContentStream cs = new PDPageContentStream(doc, page);
					
					float xStart = 0;
					float xEnd = pageSize.getWidth();
					float yEnd = pageSize.getHeight();
					
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
					cs.setFont(fontBold, 12);
					cs.showText(hawbNoFmt);
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 45*mm, yEnd - 7*mm);
					cs.setFont(fontBold, 8);
					cs.showText(outputStr);
					cs.endText();
					
					String clsfCd1 = cjParameter.getClsfCd().substring(0,1);
					String clsfCd2 = cjParameter.getClsfCd().substring(1);
					
					Barcode dlvEmpBarcode = BarcodeFactory.createCode128A(cjParameter.getClsfCd());
					float barHeight = 30*mm;
					dlvEmpBarcode.setBarHeight((int)barHeight);
					dlvEmpBarcode.setDrawingQuietSection(false);
					File dlvEmpBarcodeFile = new File(barcodePath + hawbNo + "_" + cjParameter.getClsfCd() + ".jpeg");
					BarcodeImageHandler.saveJPEG(dlvEmpBarcode, dlvEmpBarcodeFile);
					PDImageXObject dlvEmpBarcodeImage = PDImageXObject.createFromFileByContent(dlvEmpBarcodeFile, doc);

					cs.drawImage(dlvEmpBarcodeImage, xStart + 5*mm, yEnd - 25*mm, 30*mm, 15*mm);
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 37*mm, yEnd - 25*mm);
					cs.setFont(fontBold, 34);
					cs.showText(clsfCd1);
					cs.endText();
					
					float textWidth = (fontBold.getStringWidth(clsfCd1) / 1000) * 34;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (37*mm+textWidth), yEnd - 25*mm);
					cs.setFont(fontBold, 50);
					cs.showText(clsfCd2);
					cs.endText();
					
					textWidth = textWidth + (fontBold.getStringWidth(clsfCd2) / 1000) * 50;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (38*mm+textWidth), yEnd - 22*mm);
					cs.setFont(fontBold, 34);
					cs.showText("-"+cjParameter.getSubClsfCd());
					cs.endText();
					
					textWidth = textWidth + (fontBold.getStringWidth("-"+cjParameter.getSubClsfCd()) / 1000) * 34;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (40*mm+textWidth), yEnd - 23*mm);
					cs.setFont(fontBold, 24);
					cs.showText(cjParameter.getRspsDiv());
					cs.endText();
					
					
					String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
					if (cneeHp.length() != 0) {
						cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
					}
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
					cs.setFont(fontBold, 10);
					cs.showText(cneeInfo);
					cs.endText();
					
					
					Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
					barHeight = 19*mm;
					hawbNoBarcode.setBarHeight((int)barHeight);
					hawbNoBarcode.setDrawingQuietSection(false);
					File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
					BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
					PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
					
					cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
					
					
					float startX = xStart + 6*mm;
					float startY = yEnd - 35*mm;
					
					cs.beginText();
					cs.newLineAtOffset(startX, startY);
					cs.setFont(fontBold, 9);
					cs.setLeading(10);


					String[] addressWords = cneeAddr.split(" ");
					StringBuilder line = new StringBuilder();
					for (String word : addressWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
						if (tempWidth > 107*mm) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 46*mm);
					cs.setFont(fontBold, 24);
					cs.showText(cjParameter.getClsfAddr());
					cs.endText();
					
					
					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 49*mm);
					cs.setFont(fontBold, 7);
					cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
					cs.setLeading(7.5f);
					cs.newLine();
					cs.setFont(fontBold, 8);
					cs.showText(shipperAddr);
					cs.endText();

					
					cs.beginText();
					cs.newLineAtOffset(xStart + 69*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("극소"+order.getBoxCnt());
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 22*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("0");
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 10*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("신용");
					cs.endText();
					
					
					float itemDetailMaxWidth = 108*mm;
					int fontSize = 9;
					float yCurrent = yEnd - 58*mm;
					float lineHeight = fontSize + 2;

					String[] itemDetails = order.getItemDetail().split("\\|");
					String[] itemCnts = order.getItemCnt().split("\\|");
					float itemLimitLine = 15*mm;
					
		            int itemsDisplayed = 0;
					for (int itemIndex = 0; itemIndex < itemDetails.length; itemIndex++) {
						
						String itemDetail = itemDetails[itemIndex];
						String itemCnt = itemCnts[itemIndex];

						String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
						int lineCount = itemLines.length;
						
						if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
							break;
						}
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
						for (String itemLine : itemLines) {
							cs.showText(itemLine);
							cs.newLineAtOffset(0, -lineHeight);
						}
						
						cs.endText();
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
						cs.showText(itemCnt);
						cs.endText();
						
						//yCurrent -= lineHeight * (lineCount > 1 ? lineCount : 1);
						yCurrent -= lineHeight * lineCount;
						itemsDisplayed++;
					}
					
					if (itemsDisplayed < itemDetails.length) {
						String extraText = "외 " + (itemDetails.length - itemsDisplayed) + "개";
						cs.beginText();
						cs.newLineAtOffset(xStart + 4*mm, yCurrent);
						cs.setFont(fontBold, 8);
						cs.showText(extraText);
						cs.endText();
					}


					float textMaxWidth = 75*mm;
					String dlvMsg = order.getDlvReqMsg();
					
					cs.beginText();
					cs.newLineAtOffset(2*mm, 11.5f*mm);
					cs.setFont(fontBold, 8);
					cs.setLeading(8.5f);
					
					String[] dlvMsgWords = dlvMsg.split(" ");
					line = new StringBuilder();
					for (String word : dlvMsgWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
						if (tempWidth > textMaxWidth) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();

					cs.beginText();
					cs.newLineAtOffset(8*mm, 2*mm);
					cs.setFont(fontBold, 18);
					cs.showText(cllDlvText);
					cs.endText();

					cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
					
					fontSize = 9;
					textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

					cs.beginText();
					cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
					cs.setFont(fontBold, fontSize);
					cs.showText(hawbNo);
					cs.endText();
					
					cs.close();
					
					totalPage++;
					
					if (dlvEmpBarcodeFile.exists()) {
						dlvEmpBarcodeFile.delete();
					}
					
					if (hawbNoBarcodeFile.exists()) {
						hawbNoBarcodeFile.delete();
					}
				}
			
			} else {
				String resultMsg = cjHandler.getReqAddrFailMsg(cjParameter.getResultCd());
				
				PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
				PDPage blankPage = new PDPage(pageSize);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(totalPage);
				PDPageContentStream cs = new PDPageContentStream(doc, page);
				
				
				float xStart = 0;
				float xEnd = pageSize.getWidth();
				float yEnd = pageSize.getHeight();
				
				
				cs.beginText();
				cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
				cs.setFont(fontBold, 12);
				cs.showText(hawbNoFmt);
				cs.endText();
				
				String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
				if (cneeHp.length() != 0) {
					cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
				}
				
				cs.beginText();
				cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
				cs.setFont(fontBold, 10);
				cs.showText(cneeInfo);
				cs.endText();
				
				float barHeight = 30*mm;
				
				Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
				barHeight = 19*mm;
				hawbNoBarcode.setBarHeight((int)barHeight);
				hawbNoBarcode.setDrawingQuietSection(false);
				File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
				BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
				PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
				
				cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
				
				float startX = xStart + 6*mm;
				float startY = yEnd - 35*mm;
				
				cs.beginText();
				cs.newLineAtOffset(startX, startY);
				cs.setFont(fontBold, 9);
				cs.setLeading(10);


				String[] addressWords = cneeAddr.split(" ");
				StringBuilder line = new StringBuilder();
				for (String word : addressWords) {
					String tempLine = line + (line.length() == 0 ? "" : " ") + word;
					float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
					if (tempWidth > 107*mm) {
						cs.showText(line.toString());
						cs.newLine();
						line = new StringBuilder(word);
					} else {
						line.append(line.length() == 0 ? "" : " ").append(word);
					}
				}
				
				if (line.length() > 0) {
					cs.showText(line.toString());
				}
				
				cs.endText();
				

				cs.beginText();
				cs.newLineAtOffset(startX, yEnd - 46*mm);
				cs.setFont(fontBold, 24);
				cs.showText(resultMsg);
				cs.endText();
				

				cs.beginText();
				cs.newLineAtOffset(startX, yEnd - 49*mm);
				cs.setFont(fontBold, 7);
				cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
				cs.setLeading(7.5f);
				cs.newLine();
				cs.setFont(fontBold, 8);
				cs.showText(shipperAddr);
				cs.endText();

				float itemDetailMaxWidth = 108*mm;
				int fontSize = 9;
				float yCurrent = yEnd - 58*mm;
				float lineHeight = fontSize + 2;

				String[] itemDetails = order.getItemDetail().split("\\|");
				String[] itemCnts = order.getItemCnt().split("\\|");
				float itemLimitLine = 15*mm;
				
	            int itemsDisplayed = 0;
				for (int itemIndex = 0; itemIndex < itemDetails.length; itemIndex++) {
					
					String itemDetail = itemDetails[itemIndex];
					String itemCnt = itemCnts[itemIndex];

					String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
					int lineCount = itemLines.length;
					
					if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
						break;
					}
					
					cs.beginText();
					cs.setFont(fontBold, fontSize);
					cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
					for (String itemLine : itemLines) {
						cs.showText(itemLine);
						cs.newLineAtOffset(0, -lineHeight);
					}
					
					cs.endText();
					
					cs.beginText();
					cs.setFont(fontBold, fontSize);
					cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
					cs.showText(itemCnt);
					cs.endText();
					
					//yCurrent -= lineHeight * (lineCount > 1 ? lineCount : 1);
					yCurrent -= lineHeight * lineCount;
					itemsDisplayed++;
				}
				
				if (itemsDisplayed < itemDetails.length) {
					String extraText = "외 " + (itemDetails.length - itemsDisplayed) + "개";
					cs.beginText();
					cs.newLineAtOffset(xStart + 4*mm, yCurrent);
					cs.setFont(fontBold, 8);
					cs.showText(extraText);
					cs.endText();
				}


				float textMaxWidth = 75*mm;
				String dlvMsg = order.getDlvReqMsg();
				
				cs.beginText();
				cs.newLineAtOffset(2*mm, 11.5f*mm);
				cs.setFont(fontBold, 8);
				cs.setLeading(8.5f);
				
				String[] dlvMsgWords = dlvMsg.split(" ");
				line = new StringBuilder();
				for (String word : dlvMsgWords) {
					String tempLine = line + (line.length() == 0 ? "" : " ") + word;
					float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
					if (tempWidth > textMaxWidth) {
						cs.showText(line.toString());
						cs.newLine();
						line = new StringBuilder(word);
					} else {
						line.append(line.length() == 0 ? "" : " ").append(word);
					}
				}
				
				if (line.length() > 0) {
					cs.showText(line.toString());
				}
				
				cs.endText();

				cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
				
				
				fontSize = 9;
				float textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

				cs.beginText();
				cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
				cs.setFont(fontBold, fontSize);
				cs.showText(hawbNo);
				cs.endText();
				
				cs.close();
				
				totalPage++;

				if (hawbNoBarcodeFile.exists()) {
					hawbNoBarcodeFile.delete();
				}
				
			}
			
			
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			doc.save(byteArrayOutputStream);
			byte[] pdfBytes = byteArrayOutputStream.toByteArray();
			String base64Pdf = org.apache.commons.codec.binary.Base64.encodeBase64String(pdfBytes);
			
			return base64Pdf;
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	
	private String[] getTelNumberSplit(String telNo) {
		telNo = telNo.replaceFirst("^082", "").replaceFirst("^82", "");
		
		String[] array = new String[3];
		
		array[0] = "";
		array[1] = "";
		array[2] = "";
		
		if (telNo.length() > 8) {
			
			Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
	        Matcher matcher = tellPattern.matcher(telNo);
	        if(matcher.matches()) {
	        	array = new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
	        }else{
	            String str1 = telNo.substring(0, 3);
	            String str2 = telNo.substring(3, 7);
	            String str3 = telNo.substring(7, 11);
	            array = new String[]{str1, str2, str3};
	        }
		} else {
			
			if (telNo.length() == 8) {
				array[0] = telNo.substring(0,4);
				array[1] = telNo.substring(4,telNo.length());
				array[2] = "";
			}
		}
		
		return array;
	}
	
	
	private String[] splitTextToLines(String str, PDType0Font font, int fontSize, float maxWidth) throws Exception {
		String[] words = str.split(" ");
		StringBuilder line = new StringBuilder();
		StringBuilder result = new StringBuilder();
		for (String word : words) {
			String tempLine = line + (line.length() == 0 ? "" : " ") + word;
			float tempWidth = (font.getStringWidth(tempLine) / 1000) * fontSize;
			if (tempWidth > maxWidth) {
				result.append(line).append("\n");
				line = new StringBuilder(word);
			} else {
				line.append(line.length() == 0 ? "" : " ").append(word);
			}
		}
		result.append(line);
		
		return result.toString().split("\n");
	}

	
	private String hjPrint(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) throws Exception {
		
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		String pdfPath = realFilePath + "/pdf";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		
		PDDocument document = new PDDocument();
		response.setContentType("application/pdf");
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		InputStream koreanB = new FileInputStream(subPath + "/static/fonts/NanumBarunGothicBold.ttf");

		PDType0Font NanumGothic = PDType0Font.load(document, korean);
		PDType0Font NanumGothicB = PDType0Font.load(document, koreanB);
		
		float perMM = 1 / (10 * 2.54f) * 72;
		
		int totalPage = 0;
		
		parameterInfo = new HashMap<String, Object>();
		String nno = blInfo.get("nno").toString();
		parameterInfo.put("nno", nno);
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		HanjinVO printInfo = new HanjinVO();
		
		orderInfo = hjMapper.selectPrintOrderInfo(parameterInfo);
		orderInfo.dncryptData();
		printInfo = hjMapper.selectPrintLabelInfo(parameterInfo);
		
		PDRectangle pageStandard = new PDRectangle(123*perMM, 100*perMM);
		PDPage blankPage = new PDPage(pageStandard);
		document.addPage(blankPage);
		PDPage page = document.getPage(totalPage);
		PDPageContentStream cs = new PDPageContentStream(document, page);
		
		//String hawbNo = printInfo.getWblNum();
		String hawbNo = orderInfo.getHawbNo();
		String hawbNoFmt = hawbNo.substring(0,4) + "-" + hawbNo.substring(4,8) + "-" + hawbNo.substring(8,12);
		String cneeTel = orderInfo.getCneeTel();
		String cneeHp = orderInfo.getCneeHp();
		cneeTel = cneeTel.replaceAll("[^0-9]", "");
		cneeHp = cneeHp.replaceAll("[^0-9]", "");
		String cneeTel1 = "";
		String cneeTel2 = "";
		String cneeHp1 = "";
		String cneeHp2 = "";
		String cneeTelFmt = "";
		String cneeAddr = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			cneeAddr += " " + orderInfo.getCneeAddrDetail();
		}
		String shipperAddr = orderInfo.getShipperAddr();
		if (!orderInfo.getShipperAddrDetail().equals("")) {
			shipperAddr += " " + orderInfo.getShipperAddrDetail();
		}
		
		
		if (!cneeTel.equals("")) {
			if (cneeTel.length() > 9) {
				String[] tellArray;
				try {
					tellArray = phoneNumberSplit(cneeTel);
					cneeTel1 = tellArray[0];
					cneeTel2 = tellArray[1];
					cneeTelFmt += cneeTel1 + "-" + cneeTel2 + "-**** / ";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!cneeHp.equals("")) {
			if (cneeHp.length() > 10) {
				String[] phoneArray;
				try {
					phoneArray = phoneNumberSplit(cneeHp);
					cneeHp1 = phoneArray[0];
					cneeHp2 = phoneArray[1];
					cneeTelFmt += cneeHp1 + "-" + cneeHp2 + "-****";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		
		float yStart = pageStandard.getHeight();
		float xEnd = pageStandard.getWidth();
		float yHalf = yStart / 2;
		
		// HawbNo
		cs.beginText();
		cs.newLineAtOffset(48, yStart - 23);
		cs.setFont(NanumGothic, 12);
		cs.showText(hawbNoFmt);
		cs.endText();
		
		// hub_cod
		cs.beginText();
		cs.newLineAtOffset(10, yStart - 52);
		cs.setFont(NanumGothicB, 26);
		cs.showText(printInfo.getHubCod());
		cs.endText();
		
		// tml_cod
		cs.beginText();
		cs.newLineAtOffset(48, yStart - 52);
		cs.setFont(NanumGothicB, 26);
		cs.showText(printInfo.getTmlCod());
		cs.endText();
		
		// dom_mid
		cs.beginText();
		cs.newLineAtOffset(105, yStart - 52);
		cs.setFont(NanumGothicB, 26);
		cs.showText(printInfo.getDomMid());
		cs.endText();
		
		// es_cod
		cs.beginText();
		cs.newLineAtOffset(140, yStart - 42);
		cs.setFont(NanumGothicB, 18);
		cs.showText(printInfo.getEsCod());
		cs.endText();
		
		// es_name
		cs.beginText();
		cs.newLineAtOffset(140, yStart - 60);
		cs.setFont(NanumGothicB, 16);
		cs.showText(printInfo.getEsNam());
		cs.endText();
		
		// s_tml_cod
		cs.beginText();
		cs.newLineAtOffset(15, yStart - 67);
		cs.setFont(NanumGothic, 10);
		cs.showText(printInfo.getSTmlCod());
		cs.endText();
		
		// s_tml_nam
		cs.beginText();
		cs.newLineAtOffset(55, yStart - 67);
		cs.setFont(NanumGothic, 10);
		cs.showText(printInfo.getSTmlNam());
		cs.endText();

		// grp_rnk
		cs.beginText();
		cs.newLineAtOffset(220, yStart - 52);
		cs.setFont(NanumGothicB, 28);
		cs.showText(printInfo.getGrpRnk());
		cs.endText();
		
		// cen_cod
		cs.beginText();
		cs.newLineAtOffset(220, yStart - 65);
		cs.setFont(NanumGothic, 10);
		cs.showText(printInfo.getCenCod());
		cs.endText();
		
		// cen_nam
		cs.beginText();
		cs.newLineAtOffset(245, yStart - 65);
		cs.setFont(NanumGothic, 10);
		cs.showText(printInfo.getCenNam());
		cs.endText();
		
		// dom_rgn
		if (printInfo.getDomRgn().equals("7") || printInfo.getDomRgn().equals("9")) {
			cs.beginText();
			cs.newLineAtOffset(xEnd - 50, yStart - 50);
			cs.setFont(NanumGothicB, 13);
			if (printInfo.getDomRgn().equals("7")) {
				cs.showText("제주");
			} else if (printInfo.getDomRgn().equals("9")) {
				cs.showText("도서");
			}
			cs.endText();
		}
		
		float yStart2 = yStart - 68;
		
		String cneeInfo  = orderInfo.getCneeName() + "\t\t" + cneeTelFmt;
		
		cs.beginText();
		cs.newLineAtOffset(18, yStart2 - 22);
		cs.setFont(NanumGothic, 9);
		cs.showText(cneeInfo);
		cs.endText();
		
		float startLine1 = yStart2 - 34;
		int limit1 = 0;
		int addrLineHeight = 12;
		
		if (cneeAddr.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
			limit1 = 34;
		} else {
			limit1 = 34;
		}
		
		int arraySize1 = (int) Math.ceil((double) cneeAddr.length() / limit1);
		String[] stringArr1 = new String[arraySize1];
		
		
		
		
		
		int index1 = 0;
		for (int start = 0; start < cneeAddr.length(); start += limit1) {
			stringArr1[index1++] = cneeAddr.substring(start, Math.min(start+limit1, cneeAddr.length()));
		}
		
		for (int j = 0; j < stringArr1.length; j++) {
			cs.beginText();
			cs.newLineAtOffset(18, startLine1);
			cs.setFont(NanumGothic, 9);
			cs.showText(stringArr1[j]);
			cs.endText();
			
			startLine1 -= addrLineHeight;
		}
		
		String shipperInfo = orderInfo.getShipperName() + "\t" + orderInfo.getShipperTel();
		cs.beginText();
		cs.newLineAtOffset(18, yHalf + 2);
		cs.setFont(NanumGothic, 9);
		cs.showText(shipperInfo);
		cs.endText();
		
		cs.beginText();
		cs.newLineAtOffset(18, yHalf - 10);
		cs.setFont(NanumGothic, 9);
		cs.showText(shipperAddr);
		cs.endText();
		
		Barcode tmlCodBarcode = BarcodeFactory.createCode128(printInfo.getTmlCod());
		tmlCodBarcode.setBarHeight(50);
		
		String barcodePath1 = pdfPath2+hawbNo+"_tmlCod.JPEG";
		File barcodeFile1 = new File(barcodePath1);
		
		try {
			BarcodeImageHandler.saveJPEG(tmlCodBarcode, barcodeFile1);
		} catch (OutputException e) {
			e.printStackTrace();
		}
		
		PDImageXObject tmlCodBarcodeImg = PDImageXObject.createFromFileByContent(barcodeFile1, document);
		int tmlCodeWidth = tmlCodBarcodeImg.getWidth();
		int tmlCodeHeight = tmlCodBarcodeImg.getHeight();
		int targetTmlCodWidth = 100;
		int targetTmlCodHeight = (int) ((double) targetTmlCodWidth * tmlCodeHeight / tmlCodeWidth);
		cs.drawImage(tmlCodBarcodeImg, xEnd - 105, yStart2 - 42, targetTmlCodWidth, targetTmlCodHeight);
		
		cs.beginText();
		//cs.newLineAtOffset(xEnd - 50, yHalf + 25);
		cs.newLineAtOffset(xEnd - 50, yHalf + 20);
		cs.setFont(NanumGothic, 9);
		cs.showText("신용");
		cs.endText();
		
		String trCod = "";
		
		if (printInfo.getWjYesno().equals("Y")) {
			trCod = printInfo.getWjTrcod();
			
			cs.beginText();
			cs.newLineAtOffset(82, 36);
			cs.setFont(NanumGothic, 10);
			cs.showText(printInfo.getWjBrcod());
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(82, 22);
			cs.setFont(NanumGothic, 10);
			cs.showText(printInfo.getWjBrnme());
			cs.endText();
		} else {
			trCod = "H";
		}
		
		cs.beginText();
		cs.newLineAtOffset(15, 22);
		cs.setFont(NanumGothicB, 26);
		cs.showText(trCod);
		cs.endText();
		
		cs.beginText();
		cs.newLineAtOffset(18, 8);
		cs.setFont(NanumGothic, 9);
		cs.showText(orderInfo.getDlvReqMsg());
		cs.endText();
		
		Barcode hawbBarcode = BarcodeFactory.createInt2of5(hawbNo);
		hawbBarcode.setBarHeight(70);
		String barcodePath2 = pdfPath2+hawbNo+"_blNo.JPEG";
		File barcodeFile2 = new File(barcodePath2);
		
		try {
			BarcodeImageHandler.saveJPEG(hawbBarcode, barcodeFile2);
		} catch (OutputException e) {
			e.printStackTrace();
		}
		
		PDImageXObject hawbBarcodeImg = PDImageXObject.createFromFileByContent(barcodeFile2, document);
		int hawbBarWidth = hawbBarcodeImg.getWidth();
		int hawbBarHeight = hawbBarcodeImg.getHeight();
		int targetBarWidth = 120;
		int targetBarHeight = (int) ((double) targetBarWidth * hawbBarHeight / hawbBarWidth);

		cs.drawImage(hawbBarcodeImg, xEnd - 140, 32, targetBarWidth, targetBarHeight);
		
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		itemList = hjMapper.selectPrintItemInfo(parameterInfo);
		
		float itemX = 10;
		float itemY = yHalf - 30;
		float lineHeight = 12;

		int maxItems = 4;
		int itemCount = itemList.size();
		for (int itemIdx = 0; itemIdx < itemList.size(); itemIdx++) {
			cs.beginText();
			cs.newLineAtOffset(itemX, itemY);
			cs.setFont(NanumGothic, 9);
			String text = itemList.get(itemIdx).getItemDetail();
			StringBuilder builder = new StringBuilder();
			int n = text.length();
			
			for (int j = 0; j < n; j++) {
				char c = text.charAt(j);
				int pos = UPPERCASE_UNICODE.indexOf(c);
				if (pos > -1) {
					builder.append(UPPERCASE_ASCII.charAt(pos));
				} else {
					builder.append(c);
				}
			}
			
			if (itemIdx < maxItems) {
				cs.showText(builder.toString());
				cs.endText();
			} else {
				cs.showText("외 " + (itemCount - maxItems) + "개");
				cs.endText();
				break;
			}
			
			//cs.showText(itemList.get(itemIdx).getItemDetail());
			//cs.endText();
			
			itemY -= lineHeight;
		}
		
		cs.beginText();
		cs.newLineAtOffset(xEnd - 120, 20);
		cs.setFont(NanumGothicB, 10);
		cs.showText(hawbNoFmt);
		cs.endText();

		cs.close();
		
		if (barcodeFile1.exists()) {
			barcodeFile1.delete();
		}
		
		if (barcodeFile2.exists()) {
			barcodeFile2.delete();
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		document.save(byteArrayOutputStream);
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();
		String base64Pdf = org.apache.commons.codec.binary.Base64.encodeBase64String(pdfBytes);
		
		return base64Pdf;
	}

	private String fbPdf(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}

		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		float perMM = 1 / (10 * 2.54f) * 72;
		float perIn = 72;
		
		PDDocument document = new PDDocument();
		response.setContentType("application/pdf");
		
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

		PDType0Font NanumGothic = PDType0Font.load(document, korean);
		
		SecurityKeyVO securityKey = new SecurityKeyVO();
		String symmetryKey = securityKey.getSymmetryKey();
		
		int totalPage = 0;
		
		String nno = blInfo.get("nno").toString();
		FastBoxParameterVO fbParameterOne = new FastBoxParameterVO();
		fbParameterOne.setNno(nno);
		FBOrderVO fbOrderList = new FBOrderVO();
		fbOrderList = fbMapper.selectFbNnoOne(fbParameterOne);
		
		PDRectangle pageStandard = new PDRectangle(4 * perIn, 6 * perIn);
		PDPage blankPage = new PDPage(pageStandard);
		//PDPage blankPage = new PDPage(PDRectangle.A6);
		document.addPage(blankPage);
		PDPage page = document.getPage(totalPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		nno = fbOrderList.getNno();
		
		packingInfo = comnMapper.selectPdfPrintItemInfo(nno);
		String hawbNo = fbOrderList.getHawbNo();
		String storeName = fbOrderList.getStoreName();
		String orderNo = fbOrderList.getOrderNo();
		String dstnNation = fbOrderList.getDstnNation();
		String chgCurrency = fbOrderList.getChgCurrency();
		String itemCnt = String.valueOf(fbOrderList.getItemCnt());
		String totalValue = String.valueOf(fbOrderList.getTotalValue());
		
		Barcode barcodes = BarcodeFactory.createCode128(hawbNo);
		barcodes.setLabel("Barco");
		barcodes.setDrawingText(true);

		String barcodePath = pdfPath2 +hawbNo+".JPEG";
		File barcodefile = new File(barcodePath);
		
		try {
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
		} catch (OutputException e) {
			e.printStackTrace();
		}
		
		PDImageXObject hawbBarCode = PDImageXObject.createFromFileByContent(barcodefile, document);
		contentStream.drawImage(hawbBarCode, 16*perMM, 136*perMM, 70*perMM, 12*perMM);

		contentStream.beginText();
		contentStream.newLineAtOffset(30*perMM, 132*perMM);
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
		contentStream.showText(hawbNo);
		contentStream.endText();
		
		float titleWidth = NanumGothic.getStringWidth(hawbNo) / 1000 * 9;
		int fontSize = 10;
		// bottom
		//contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);
		// top
		contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM);
		// left
		//contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
		contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, 3 * perMM, 109 * perMM);
		// right
		//contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset(5 * perMM, 125.5f * perMM);
		contentStream.showText("상점명");
		contentStream.endText();
		
		contentStream.drawLine(20 * perMM, 130 * perMM, 20 * perMM, 109 * perMM);
		contentStream.drawLine(3 * perMM, 123 * perMM, (pageStandard.getWidth()) - 3 * perMM, 123 * perMM);
		
		titleWidth = NanumGothic.getStringWidth((0+1) + "/" + 1)  / 1000 * fontSize;
		
	
		contentStream.drawLine((pageStandard.getWidth()) - 20 * perMM, 130 * perMM, (pageStandard.getWidth()) - 20 * perMM, 109 * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 36 * perMM, 130 * perMM, (pageStandard.getWidth()) - 36 * perMM, 109 * perMM);

		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset(5 * perMM, 118.5f * perMM);
		contentStream.showText("합포번호");
		contentStream.endText();
		
		contentStream.drawLine(3 * perMM, 116 * perMM, (pageStandard.getWidth()) - 3 * perMM, 116 * perMM);
		contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset(5 * perMM, 111.5f * perMM);
		contentStream.showText("국가코드");
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 125.5f * perMM);
		contentStream.showText("수량합계");
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 118.5f * perMM);
		contentStream.showText("가격합계");
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 111.5f * perMM);
		contentStream.showText("통화");
		contentStream.endText();

		
		// 주문 정보 START
		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset(21 * perMM,  125.5f * perMM);
		contentStream.showText(storeName);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset(21 * perMM, 118.5f * perMM);
		contentStream.showText(orderNo);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset(21 * perMM, 111.5f * perMM);
		contentStream.showText(dstnNation);
		contentStream.endText();

		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 125.5f * perMM);
		contentStream.showText(itemCnt);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 118.5f * perMM);
		contentStream.showText(totalValue);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, 9);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 111.5f * perMM);
		contentStream.showText(chgCurrency);
		contentStream.endText();

		
		contentStream.drawLine(3 * perMM, 109 * perMM, 3 * perMM, 102 * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 16 * perMM, 109 * perMM, (pageStandard.getWidth()) - 16 * perMM, 102 * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 102 * perMM);
		contentStream.drawLine(3 * perMM, 102 * perMM, (pageStandard.getWidth()) - 3 * perMM, 102 * perMM);
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset(4.5f * perMM, 104 * perMM);
		contentStream.showText("상품명");
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(NanumGothic, fontSize);
		contentStream.newLineAtOffset((pageStandard.getWidth()) - 12.5f * perMM, 104 * perMM);
		contentStream.showText("수량");
		contentStream.endText();
		
		//contentStream.drawLine(3 * perMM, 102 * perMM, 3 * perMM, 90 * perMM);
		
		// 상품정보 START
		ArrayList<LinkedHashMap<String, Object>> itemList = new ArrayList<LinkedHashMap<String, Object>>();
		itemList = fbMapper.selectFBItem(nno);
		
		float startLine = 96.5f;
		
		for (int j = 0; j < itemList.size(); j++) {
			String itemDetail = "";
			itemDetail = itemList.get(j).get("itemDetail").toString();
			/*
			if (itemList.get(j).get("itemDetail").toString().length() > 20) {
				itemDetail = stringTransfer(itemList.get(j).get("itemDetail").toString(), 20)+"...";	
			} else {
				itemDetail = itemList.get(j).get("itemDetail").toString();
			}
			*/
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 8);
			contentStream.newLineAtOffset(4.5f * perMM, startLine * perMM);
			//contentStream.showText(itemList.get(j).get("itemDetail").toString()+" / "+itemList.get(j).get("itemDiv").toString());
			//contentStream.showText(itemDetail+" / " +itemList.get(j).get("itemDiv").toString());
			contentStream.showText(itemDetail);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 8);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 6 * perMM, startLine * perMM);
			contentStream.showText(itemList.get(j).get("itemCnt").toString());
			contentStream.endText();
			
			startLine -= 4.5f;
		}
		
		float endLine = startLine;
		
		contentStream.drawLine(3 * perMM, 102 * perMM, 3 * perMM, endLine * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 16 * perMM, 102 * perMM, (pageStandard.getWidth()) - 16 * perMM, endLine * perMM);
		contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 102 * perMM, (pageStandard.getWidth()) - 3 * perMM, endLine * perMM);
		contentStream.drawLine(3 * perMM, endLine * perMM, (pageStandard.getWidth()) - 3 * perMM, endLine * perMM);
		
		float endLine2 = endLine - 4;

		contentStream.close();
		
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		document.save(byteArrayOutputStream);
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();
		String base64Pdf = org.apache.commons.codec.binary.Base64.encodeBase64String(pdfBytes);
		
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

		return base64Pdf;
	}

	private String printPdfLegacy(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String pdfPath = realFilePath + "/pdf";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		
		// 2023.01.19 추가
		PDFMergerUtility merger = new PDFMergerUtility();
		
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		// 문서 만들기
		final PDDocument doc = new PDDocument();

		// 폰트 생성
		// ttf 파일 사용하기
		InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
		InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
		InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

		PDType0Font MsGothic = PDType0Font.load(doc, japanese);
		PDType0Font ARIAL = PDType0Font.load(doc, english);
		PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);

		float perMM = 1 / (10 * 2.54f) * 72;

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;
			
			pdfPrintInfo = comnMapper.selectPdfPrintInfo(blInfo.get("nno").toString(), blInfo.get("transCode").toString());
			pdfPrintInfo.dncryptData();
			//packingInfo = comnMapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
			String blType = comnMapper.selectBlTypeTransCom(pdfPrintInfo);
			if ("".equals(blType)) {
				blType = "C";
			}
			
			for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
				if (blType.equals("A")) {
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
					// 배경이미지 로드

					barcodes.setBarHeight(70);
					barcodes.setBarWidth(50);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
					int fontSize = 12; // Or whatever font size you want.
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
							45f);

					/* 사각형 */
					contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 149 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 3 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
					contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
							(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

					// 1st Vertical Line
					contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
					// 1st horizontal Line
					// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
					// 2nd horizontal Line
					contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

					// 3rd horizontal Line
					contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

					// 2nd Vertical Line
					contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
					// 3rd Vertical Line
					contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

					// 4th horizontal Line
					contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);

					/* 글자 표기 */
					drawText("Piece(s)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
					fontSize = 20;
					titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 7 * perMM, 115 * perMM,
							contentStream);

					fontSize = 17;
					drawText("Weight", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
					drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
					// Order No.
					fontSize = 7;
					drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
					fontSize = 12;
					drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

					fontSize = 7;
					drawText("Origin", ARIAL, fontSize, 10 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 15 * perMM, 100 * perMM, contentStream);
					drawText("========>", ARIAL, fontSize, 34 * perMM, 100 * perMM, contentStream);

					fontSize = 7;
					drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

					// Shipper
					// 5th horizontal Line
					contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
					fontSize = 9;
					drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 60 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 60 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 74 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 74 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
							fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

					// receiver
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

					// Receiver
					fontSize = 9;
					drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
							5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

					// 바코드
					contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
					
				} else if (blType.equals("B")) {
					ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
					itemInfos = comnMapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
					String hawbNo = pdfPrintInfo.getHawbNo();
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					float pageWidth = pageStandard.getWidth();
					float pageHeight = pageStandard.getHeight();
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
					hawbBarcode.setBarHeight(70);
					hawbBarcode.setDrawingQuietSection(false);
					
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					
					// aci 로고
					//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

					int fontSize = 15;
					float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;

					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					
					// HawbNo 바코드
					int barcodeW = pdImage.getWidth();
					System.out.println(barcodeW);
					int barcodeH = pdImage.getHeight();
					System.out.println(barcodeH);
					//int pdfWidth = 160;
					//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
					int pdfHeight = 45;
					int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
					
					float leftX = 5;
					float rightX = pageWidth - 5;
					
					float centerX = (rightX - leftX - pdfWidth) / 2 + leftX;
					
					//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
					//contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					contentStream.drawImage(pdImage, centerX, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					
					//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
					contentStream.drawLine(5, 5, 5, (pageHeight - 5));
					contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
					contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
					contentStream.drawLine((pageWidth - 5), 5, 5, 5);
					
					
					PDExtendedGraphicsState graphicsState  = new PDExtendedGraphicsState();
					if (pdfPrintInfo.getUserId().toLowerCase().equals("pickpack")) {
						graphicsState.setNonStrokingAlphaConstant(0.3f);

						int ftSize = 15;
						String textString = "PickPack Worldwide LLC";
						float ftWidth = ARIALBOLD.getStringWidth(textString) / 1000 * ftSize;
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 10) - ftWidth), 10);
						contentStream.setFont(ARIALBOLD, ftSize);
						contentStream.setGraphicsStateParameters(graphicsState);
						contentStream.showText(textString);
						contentStream.endText();
					}
					
					PDExtendedGraphicsState graphicsState2  = new PDExtendedGraphicsState();
					graphicsState2.setNonStrokingAlphaConstant(1f);
					contentStream.setGraphicsStateParameters(graphicsState2);

					float xPoint = 5;
					float yPoint = (152 * perMM - 21 * perMM) - 7;
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					xPoint = xPoint + 5;
					
					String contentText = "";
					contentText = hawbNo;
					float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					
					yPoint = yPoint - textHeight;
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint + 2);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 14;
					contentText = pdfPrintInfo.getOrderNo();
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint = yPoint - textHeight;
					
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 10;
					contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					xPoint = (pageWidth - 10) - textWidth;


					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, 342);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					contentStream.drawLine(5, 335, (pageWidth - 5), 335);
					
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint -= (textHeight + 3);
					xPoint = 10;
					contentText = pdfPrintInfo.getShipperName();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperTel();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					float startX = xPoint;
					float startY = yPoint;
					float currentX = startX;
					float currentY = startY;
					float widthLimit = 260;
					float leading = textHeight;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					String[] words = contentText.split(" ");
					
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY - 5;
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getCneeName();
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(contentText);
					contentStream.endText();
					
					String cneeTel = pdfPrintInfo.getCneeTel();
					if (cneeTel.equals("") ) {
						cneeTel = pdfPrintInfo.getCneeHp();
					}
					
					yPoint -= textHeight;
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(cneeTel);
					contentStream.endText();
					
					yPoint -= textHeight;
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
					if (!pdfPrintInfo.getCneeCity().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
					}
					if (!pdfPrintInfo.getCneeState().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeState()+" ";
					}
					cityStateInfo += pdfPrintInfo.getDstnNation();

					words = cityStateInfo.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint -= textHeight;
					String cneeAddr = pdfPrintInfo.getCneeAddr();
					if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
						cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
					}
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					words = cneeAddr.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = (currentY - 5);
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= 12;
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
						String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
						int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
						String itemText = itemDetail + " / " + itemCnt;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, currentY);
						
						String[] itemWords = itemText.split(" ");
						
						for (String word : itemWords) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						currentY -= leading;
						
					}
					
					
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
				} else if (blType.equals("C")) {
					ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
					itemInfos = comnMapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
					String hawbNo = pdfPrintInfo.getHawbNo();
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					float pageWidth = pageStandard.getWidth();
					float pageHeight = pageStandard.getHeight();
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
					hawbBarcode.setBarHeight(70);
					hawbBarcode.setDrawingQuietSection(false);
					
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					
					// aci 로고
					//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

					int fontSize = 15;
					float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;
					
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					
					// HawbNo 바코드
					int barcodeW = pdImage.getWidth();
					int barcodeH = pdImage.getHeight();
					//int pdfWidth = 160;
					//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
					int pdfHeight = 45;
					int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
					
					float imageX = (pageWidth - pdfWidth) / 2;
					float dstnHeight = (((152 * perMM - 21 * perMM) - 7) - (pageHeight - 5)) / 2;
					
					String dstnNation = pdfPrintInfo.getDstnNation();
					float dstnNationWidth = ARIALBOLD.getStringWidth(dstnNation) / 1000 * fontSize;
					float dstnLineWidth = dstnNationWidth + 15;
					
					//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
					contentStream.drawImage(pdImage, 23, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					
					//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
					contentStream.drawLine(5, 5, 5, (pageHeight - 5));
					contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
					contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
					contentStream.drawLine((pageWidth - 5), 5, 5, 5);
					
					float xPoint = 5;
					float yPoint = (152 * perMM - 21 * perMM) - 7;
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint, ((pageWidth - 5) - dstnLineWidth), (pageHeight - 5));
					contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint + 30, (pageWidth - 5), yPoint + 30);
					
					contentStream.beginText();
					contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 7.5f, yPoint + 10);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(pdfPrintInfo.getDstnNation());
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 5, yPoint + 40);
					contentStream.setFont(ARIAL, 13);
					contentStream.showText("Dest");
					contentStream.endText();
					
											
					xPoint = xPoint + 5;
					
					String contentText = "";
					contentText = hawbNo;
					float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					
					yPoint = yPoint - textHeight;
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint + 2);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 14;
					contentText = pdfPrintInfo.getOrderNo();
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					yPoint = yPoint - textHeight;
					
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					String orderDate = pdfPrintInfo.getOrderDate();
					orderDate = "(" + orderDate + ")";
					
					fontSize = 10;
					xPoint = (xPoint + 5) + textWidth;

					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(orderDate);
					contentStream.endText();
					
					//fontSize = 10;
					contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					xPoint = (pageWidth - 10) - textWidth;


					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, 342);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					contentStream.drawLine(5, 335, (pageWidth - 5), 335);
					
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint -= (textHeight + 3);
					xPoint = 10;
					contentText = pdfPrintInfo.getShipperName();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperTel();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					float startX = xPoint;
					float startY = yPoint;
					float currentX = startX;
					float currentY = startY;
					float widthLimit = 260;
					float leading = textHeight;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					String[] words = contentText.split(" ");
					
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY - 5;
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getCneeName();
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(contentText);
					contentStream.endText();
					
					String cneeTel = pdfPrintInfo.getCneeTel();
					if (cneeTel.equals("") ) {
						cneeTel = pdfPrintInfo.getCneeHp();
					}
					
					yPoint -= textHeight;
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(cneeTel);
					contentStream.endText();
					
					yPoint -= textHeight;
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
					if (!pdfPrintInfo.getCneeCity().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
					}
					if (!pdfPrintInfo.getCneeState().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeState()+" ";
					}
					cityStateInfo += pdfPrintInfo.getDstnNation();

					words = cityStateInfo.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY;
					
					yPoint -= textHeight;
					String cneeAddr = pdfPrintInfo.getCneeAddr();
					if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
						cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
					}
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					words = cneeAddr.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = (currentY - 5);
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= 12;
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					// startX ~ widthLimit

					for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
						String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
						int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
						String itemText = itemDetail + " / " + itemCnt;
						float wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
						
						String trimStr = "";
						if (wordWidth <= widthLimit) {
							trimStr = itemText;
						} else {
							float trimRatio = widthLimit / wordWidth;
							int trimmedLength = (int) (itemText.length() * trimRatio);
							trimStr = itemText.substring(0, trimmedLength);	
						}
						
						while (wordWidth > widthLimit) {
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							contentStream.showText(trimStr);
							contentStream.endText();
							
							currentY -= leading;
							
							itemText = itemText.substring(trimStr.length());
							wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
						}
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, currentY);
						contentStream.showText(itemText);
						contentStream.endText();
						
						currentY -= leading;
					}
					
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
				}
			}


		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		doc.save(byteArrayOutputStream);
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();
		String base64Pdf = org.apache.commons.codec.binary.Base64.encodeBase64String(pdfBytes);
		
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

		
		return base64Pdf;
	}

	private String yslPdf(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String pdfPath = realFilePath + "/pdf";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		// 문서 만들기
		final PDDocument doc = new PDDocument();

		// 폰트 생성
		// ttf 파일 사용하기
		InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
		InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
		InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

		PDType0Font MsGothic = PDType0Font.load(doc, japanese);
		PDType0Font ARIAL = PDType0Font.load(doc, english);
		PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);

		float perMM = 1 / (10 * 2.54f) * 72;
		
		String printType = "YSL";

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;
			
			String transCode = blInfo.get("transCode").toString();
			String hawbNo = blInfo.get("hawbNo").toString();
			String nno = blInfo.get("nno").toString();
			
			String blType = "default";
			pdfPrintInfo = comnMapper.selectTempInfo(nno, transCode);
			pdfPrintInfo.dncryptData();
			
			if (blType.equals("default")) {
				blType = comnMapper.selectBlTypeTransCom(pdfPrintInfo);
			}
			
			if (blType.equals("A")) {
				PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(0);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

				barcodes2.setSize(400, 800);
				barcodes2.setBarHeight(0);
				barcodes2.setBarWidth(0);

				barcodes2.setLabel("Barcode creation test...");
				barcodes2.setDrawingText(true);

				File barcodefile2 = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

				PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

				int fontSize = 10; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(transCode + "입고 (" + pdfPrintInfo.getTotalCnt() + ")")
						/ 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 38, 200f, 30f);

				contentStream.drawImage(pdImage2, (80 * perMM - 200) / 2, 7, 200f, 15f);

				drawText(transCode + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
						(80 * perMM - 80), 30 * perMM - 11, contentStream);

				fontSize = 9;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo() + " ▽", NanumGothic, fontSize, 10 * perMM, 26, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo2() + " ▽", NanumGothic, fontSize, (10 * perMM), 30 * perMM - 11,
						contentStream);

				contentStream.close();
				
			} else if (blType.equals("B")) {
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 55 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);

					contentStream.drawLine(3 * perMM, 3 * perMM, (pageStandard.getWidth()) - 3 * perMM, 3 * perMM);
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight() - 3 * perMM),
							(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight() - 3 * perMM));
					contentStream.drawLine(97 * perMM, 3 * perMM, 97 * perMM,
							(pageStandard.getHeight()) - 3 * perMM);
					contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageStandard.getHeight()) - 3 * perMM);

					// barcode1
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createCode128(pdfPrintInfo.getHawbNo2());
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);
					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					// barcode2
					net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory
							.createCode128(pdfPrintInfo.getHawbNo());
					barcodes2.setSize(400, 800);
					barcodes2.setBarHeight(0);
					barcodes2.setBarWidth(0);
					barcodes2.setLabel("Barcode creation test...");
					barcodes2.setDrawingText(true);
					File barcodefile2 = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);
					PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

					// barcode print
					contentStream.drawImage(pdImage, (100 * perMM - 48 * perMM) / 2,
							(pageStandard.getHeight()) - 15 * perMM, 200f, 30f);
					contentStream.drawImage(pdImage2, (100 * perMM - 48 * perMM) / 2,
							(pageStandard.getHeight()) - 27 * perMM, 200f, 15f);

					int fontSize = 10; // Or whatever font size you want.
					float titleWidth = NanumGothic
							.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")") / 1000
							* fontSize;

					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo() + " △", NanumGothic, fontSize, 48 * perMM, 24 * perMM,
							contentStream);

					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo2() + "  △", NanumGothic, fontSize, (46 * perMM), 36 * perMM,
							contentStream);

					// 바코드 아래 가로
					contentStream.drawLine(3 * perMM, 22 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							22 * perMM);
					// 바코드 왼쪽 세로
					contentStream.drawLine(27 * perMM, 22 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 3 * perMM);
					// 바코드 왼쪽 위에 가로
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 10 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 10 * perMM);

					// 입고 배송업체 및 개수
					// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
					// fontSize, 6*perMM, (pageStandard.getHeight()-8*perMM),contentStream);
					// 배송국가 header
					// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
					// fontSize, 7*perMM, 52*perMM,contentStream);
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 17 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 17 * perMM);
					drawText("도착국가", NanumGothic, fontSize, 8 * perMM, (pageStandard.getHeight() - 15 * perMM),
							contentStream);
					fontSize = 20;
					drawText(pdfPrintInfo.getDstnNation(), NanumGothic, fontSize, 11 * perMM, 28 * perMM,
							contentStream);

					fontSize = 15;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, (100 * perMM - titleWidth) / 2,
							14 * perMM, contentStream);
					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth("(" + pdfPrintInfo.getShipperName() + ")") / 1000
							* fontSize;
					drawText("(" + pdfPrintInfo.getShipperName() + ")", NanumGothic, fontSize,
							(100 * perMM - titleWidth) / 2, 9 * perMM, contentStream);

					// 컨텐츠 스트림 닫기
					contentStream.close();
					totalPage++;
				}
			} else if (blType.equals("C")) {
				String yslNo = pdfPrintInfo.getHawbNo2();
				String aciNo = pdfPrintInfo.getHawbNo();
				String orderNo = pdfPrintInfo.getOrderNo();
				
				String yslBarcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
				String aciBarcodePath = pdfPath2 + userId + "_" + aciNo + ".JPEG";
				
				PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);
				
				PDPage page = doc.getPage(totalPage);
				
				float pageWidth = pageStandard.getWidth();
				float pageHeight = pageStandard.getHeight();
				
				PDPageContentStream cts = new PDPageContentStream(doc, page);
				Barcode yslBarcode = BarcodeFactory.createCode128(yslNo);
				yslBarcode.setBarHeight(70);
				yslBarcode.setDrawingQuietSection(false);
				Barcode aciBarcode = BarcodeFactory.createCode128(aciNo);
				//Barcode aciBarcode = BarcodeFactory.createCode128(orderNo);
				aciBarcode.setBarHeight(70);
				aciBarcode.setDrawingQuietSection(false);
				
				File barcodeFile = new File(yslBarcodePath);
				BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);
				
				File barcodeFile2 = new File(aciBarcodePath);
				BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);
				
				PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
				PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
				
				String pdfText = "";
				pdfText = printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")";
				
				int yslImgW = yslBarcodeImage.getWidth();
				int yslImgH = yslBarcodeImage.getHeight();
				int pdfYslWidth = 250;
				int pdfYslHeight = (int) ((double) pdfYslWidth * yslImgH / yslImgW);
				
				int aciImgW = aciBarcodeImage.getWidth();
				int aciImgH = aciBarcodeImage.getHeight();
				int pdfAciWidth = 220;
				int pdfAciHeight = (int) ((double) pdfAciWidth * aciImgH / aciImgW);
				
				float xPoint = (pageWidth - pdfYslWidth) / 2;

				int fontSize = 10;
				float textHeight = fontSize * NanumGothic.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
				float textWidth = NanumGothic.getStringWidth(pdfText) / 1000 * fontSize;
				
				cts.beginText();
				cts.newLineAtOffset(20, (pageHeight - 20) - textHeight);
				cts.setFont(NanumGothic, fontSize);
				cts.showText(yslNo+" ▽");
				cts.endText();
				
				cts.beginText();
				cts.newLineAtOffset((pageWidth - 20) - textWidth, (pageHeight - 20) - textHeight);
				cts.setFont(NanumGothic, fontSize);
				cts.showText(pdfText);
				cts.endText();
				
				
				float yPoint = (pageHeight - 30) - textHeight - pdfYslHeight;
				
				cts.drawImage(yslBarcodeImage, xPoint, yPoint, pdfYslWidth, pdfYslHeight);
				
				yPoint = yPoint - 20;
				
				cts.beginText();
				cts.newLineAtOffset(20, yPoint);
				cts.setFont(NanumGothic, fontSize);
				cts.showText(aciNo+" ▽");
				//cts.showText(orderNo+" ▽");
				cts.endText();
				
				pdfText = pdfPrintInfo.getOrderNo();
				textWidth = NanumGothic.getStringWidth(pdfText) / 1000 * fontSize;
				
				cts.beginText();
				cts.newLineAtOffset((pageWidth - 20) - textWidth, yPoint);
				cts.setFont(NanumGothic, fontSize);
				cts.showText(pdfText);
				cts.endText();
				
				xPoint = (pageWidth - pdfAciWidth) / 2;
				yPoint = (yPoint - 10) - pdfAciHeight;
				
				cts.drawImage(aciBarcodeImage, xPoint, yPoint, pdfAciWidth, pdfAciHeight);
				
				cts.close();
				totalPage++;
				
				
				File barcodefile = new File(yslBarcodePath);
				if (barcodefile.exists()) {
					barcodefile.delete();
				}
				
				File barcodefile2 = new File(aciBarcodePath);
				if (barcodefile2.exists()) {
					barcodefile2.delete();
				}
			}

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		doc.save(byteArrayOutputStream);
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();
		String base64Pdf = org.apache.commons.codec.binary.Base64.encodeBase64String(pdfBytes);
		
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}
		
		return base64Pdf;
	}
	
	private void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream contentStream) throws Exception {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(left, bottom);
		contentStream.showText(text);
		contentStream.endText();
	}


	private String loadPdf(HttpServletRequest request, HttpServletResponse response, LinkedHashMap<String, Object> blInfo) throws Exception {
		String imgPath = realFilePath + "image/" + "aramex/";
		
		SecurityKeyVO originKey = new SecurityKeyVO();
		URL website = null;
		HttpURLConnection http = null;
		int statusCode = 0;
		
		String wDate = blInfo.get("wDate").toString();
		String userId = blInfo.get("userId").toString();
		String hawbNo = blInfo.get("hawbNo").toString();
		
		String year = wDate.substring(0,4);
		String month = wDate.substring(4, 6);
		String day = wDate.substring(6, 8);
		wDate = year + "-" + month + "-" + day;
		String week = Integer.toString(getWeekOfYear(wDate));
		
		website = new URL("http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+userId+"_"+hawbNo);
		http = (HttpURLConnection) website.openConnection();
		statusCode = http.getResponseCode();
		
		if (statusCode != 200) {
			website = new URL("http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+AES256Cipher.AES_Encode(hawbNo, originKey.getSymmetryKey()));
		}
		
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(imgPath + hawbNo + ".pdf");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 처음부터 끝까지 다운로드
		fos.close();
		
		String base64Pdf = "";
		File file = new File(imgPath + hawbNo + ".pdf");
		if (file.exists()) {
			byte[] pdfBytes = FileUtils.readFileToByteArray(file);
			base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
			
			file.delete();
		}

		return base64Pdf;
	}
	
	private void drawTextLine(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int targetType) throws Exception {
		String asd = "";
		int splitSize = 0;
		int fontss = 0;
		if (targetType == 1) {
			splitSize = 30000;
			fontss = 12;
		} else if (targetType == 2) {
			splitSize = 37500;
		} else if (targetType == 3) {
			splitSize = 35000;
		} else if (targetType == 4) {
			splitSize = 21000;
		} else {
			if (fontSize > 10) {
				splitSize = 14700;
				fontss = 12;

			} else if (fontSize > 7) {
				splitSize = 14700;
				fontss = 8;

			} else {
				splitSize = 14700;
				fontss = 7;
			}
		}

		String temp = "";
		int bottomVal = 0;
		if (font.getStringWidth(text) > splitSize) {
			for (int index = 0; index < text.length(); index++) {
				temp += text.charAt(index);
				if (font.getStringWidth(temp) > splitSize) {
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
					contentStream.showText(temp);
					temp = "";
					bottomVal++;
					contentStream.endText();
				}
			}
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
			contentStream.showText(temp);
			contentStream.endText();
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
		}
	}
	
	
	public static String[] phoneNumberSplit(String phoneNumber) throws Exception{
        Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
        Matcher matcher = tellPattern.matcher(phoneNumber);
        if(matcher.matches()) {
            //정규식에 적합하면 matcher.group으로 리턴
            return new String[]{ matcher.group(1), matcher.group(2), matcher.group(3)};
        }else{
            //정규식에 적합하지 않으면 substring으로 휴대폰 번호 나누기
            String str1 = phoneNumber.substring(0, 3);
            String str2 = phoneNumber.substring(3, 7);
            String str3 = phoneNumber.substring(7, 11);
            return new String[]{str1, str2, str3};
        }
    }

	@Override
	public LinkedHashMap<String, Object> selectBlList(Map<String, Object> jsonHeader, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String aciKey = "3AF35C5E33E439E612576618CFAAE1C1";

		try {
			if (!jsonHeader.containsKey("acikey")) {
				rstMap.put("result", "ACI KEY IS NOT EXISTS");
				rstMap.put("status", "L10");
				return rstMap;
			}
						
			if (!jsonHeader.get("acikey").equals(aciKey)) {
				rstMap.put("result", "ACI KEY IS NOT MATCHING");
				rstMap.put("status", "L40");
				return rstMap;
			}
			
			String afcCode = request.getParameter("afcCode").toUpperCase();
			String sendDate = request.getParameter("sendDate");

			if (afcCode.equals("")) {
				rstMap.put("result", "afcCode is empty");
				rstMap.put("status", "F10");
				return rstMap;
			}
			
			if (sendDate.equals("")) {
				rstMap.put("result", "sendDate is empty");
				rstMap.put("status", "F20");
				return rstMap;
			}
			
			if (sendDate.length() != 8) {
				rstMap.put("result", "sendDate is invalid date format");
				rstMap.put("status", "F30");
				return rstMap;
			}
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("afcCode", afcCode);
			parameters.put("sendDate", sendDate);
			
			ArrayList<HashMap<String, Object>> blList = new ArrayList<HashMap<String,Object>>();
			blList = mapper.selectBlList(parameters);

			ArrayList<LinkedHashMap<String, Object>> jsonArray = new ArrayList<LinkedHashMap<String,Object>>();
			for (int i = 0; i < blList.size(); i++) {
				LinkedHashMap<String, Object> jsonData = new LinkedHashMap<String, Object>();
				jsonData.put("bl", blList.get(i).get("hawbNo").toString());
				jsonData.put("system", blList.get(i).get("site").toString());
				jsonData.put("station", blList.get(i).get("station").toString());
				jsonData.put("userId", blList.get(i).get("userId").toString());
				
				jsonArray.add(jsonData);
			}
			
			rstMap.put("status", "S10");
			rstMap.put("result", jsonArray);

		} catch (Exception e) {
			rstMap.put("result", e.toString());
			rstMap.put("status", "D10");
			return rstMap;
		}


		return rstMap;
	}

	@Override
	public LinkedHashMap<String, Object> selectBlPrintSingle(Map<String, Object> jsonHeader,
			Map<String, Object> jsonData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> rtnJson = new LinkedHashMap<String, Object>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String aciKey = "3AF35C5E33E439E612576618CFAAE1C1";
		
		if (!jsonHeader.containsKey("acikey")) {
			rtnJson.put("status", "Fail");
			rtnJson.put("system", "WMS");
			rtnJson.put("userId", "");
			rtnJson.put("transCode", "");
			rtnJson.put("blNo", "");
			rtnJson.put("labelType", "");
			rtnJson.put("extension", "");
			rtnJson.put("base64", "");
			rtnJson.put("msg", "ACI Key Is Not Exists");
			return rtnJson;
		}
		
		if (!jsonHeader.get("content-type").equals("application/json")) {
			rtnJson.put("status", "Fail");
			rtnJson.put("system", "WMS");
			rtnJson.put("userId", "");
			rtnJson.put("transCode", "");
			rtnJson.put("blNo", "");
			rtnJson.put("labelType", "");
			rtnJson.put("extension", "");
			rtnJson.put("base64", "");
			rtnJson.put("msg", "In case content type is not JSON type");
			return rtnJson;
		}
		
		if (!jsonHeader.get("acikey").equals(aciKey)) {
			rtnJson.put("status", "Fail");
			rtnJson.put("system", "WMS");
			rtnJson.put("userId", "");
			rtnJson.put("transCode", "");
			rtnJson.put("blNo", "");
			rtnJson.put("labelType", "");
			rtnJson.put("extension", "");
			rtnJson.put("base64", "");
			rtnJson.put("msg", "ACI Key Is Not Matching");
			return rtnJson;
		}
		
		JSONObject requestJson = new JSONObject(jsonData);
		String printType = requestJson.optString("printType");
		String hawbNo = requestJson.optString("blNo");
		
		try {
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			LinkedHashMap<String, Object> blInfo = new LinkedHashMap<String, Object>();
			
			parameters.put("hawbNo", hawbNo);
			
			int blCnt = mapper.selectCheckBlCnt(parameters);
			if (blCnt == 1) {
				blInfo = mapper.selectBlPrintInfo(parameters);
				String transCode = blInfo.get("transCode").toString();
				String userId = blInfo.get("userId").toString().toUpperCase();
				String base64Print = printHawbLegacy(request, response, transCode, blInfo);
				
				String printSize = "";
				switch (transCode) {
				case "YSL":
					printSize = "3x1";
					break;
				case "EFS":
					printSize = "3x1";
					break;
				case "EPT":
					printSize = "6x4";
					break;
				case "CJ":
					printSize = "5x4";
					break;
				case "HJ":
					printSize = "5x4";
					break;
				default :
					printSize = "4x6";
					break;
				}
				rtnJson.put("status", "Success");
				rtnJson.put("system", "WMS");
				rtnJson.put("userId", userId);
				rtnJson.put("transCode", transCode);
				rtnJson.put("blNo", blInfo.get("hawbNo").toString());
				rtnJson.put("labelType", printSize);
				rtnJson.put("extension", "pdf");
				rtnJson.put("base64", base64Print);
				rtnJson.put("msg", "");
			} else if (blCnt > 1) {
				rtnJson.put("status", "Fail");
				rtnJson.put("system", "WMS");
				rtnJson.put("userId", "");
				rtnJson.put("transCode", "");
				rtnJson.put("blNo", hawbNo);
				rtnJson.put("labelType", "");
				rtnJson.put("extension", "");
				rtnJson.put("base64", "");
				rtnJson.put("msg", hawbNo + " - More than one piece of data has been inquired.");
			} else {
				rtnJson.put("status", "Fail");
				rtnJson.put("system", "WMS");
				rtnJson.put("userId", "");
				rtnJson.put("transCode", "");
				rtnJson.put("blNo", hawbNo);
				rtnJson.put("labelType", "");
				rtnJson.put("extension", "");
				rtnJson.put("base64", "");
				rtnJson.put("msg", hawbNo + " - No Data.");
			}
			
		} catch (Exception e) {
			rtnJson.put("status", "Fail");
			rtnJson.put("system", "WMS");
			rtnJson.put("userId", "");
			rtnJson.put("transCode", "");
			rtnJson.put("blNo", "");
			rtnJson.put("labelType", "");
			rtnJson.put("extension", "");
			rtnJson.put("base64", "");
			rtnJson.put("msg", e.toString());
		}
		
		return rtnJson;
	}


	public String jsonObjectToString(Object object) {
		Gson gson = new Gson();
		String requestVal = gson.toJson(object);
		
		return requestVal;
	}

	
}

