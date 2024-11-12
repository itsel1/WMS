package com.example.temp.api.aci.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PUT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.example.temp.api.ApiAction;
import com.example.temp.api.CommonUtils;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.Tracking;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.InsterumentVO;
import com.example.temp.api.aci.vo.MsgBodyVO;
import com.example.temp.api.aci.vo.MsgHeaderVO;
import com.example.temp.api.aci.vo.ShipperInfo;
import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.api.logistics.service.CJHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.VietnamPostHandler;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.company.Aramex;
import com.example.temp.api.shipment.company.Epost;
import com.example.temp.api.shipment.company.YongSung;
import com.example.temp.api.shipment.company.YunExpress;
import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.api.shopify.ShopifyAPI;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.ManagerTakeinService;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.OrderRcptVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.VolumeVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.SmtpService;
import com.example.temp.smtp.ViewMatchingInfo;
import com.example.temp.smtp.ViewYslItemCode;
import com.example.temp.trans.aci.AciAPI;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.comn.ComnAPI;
import com.example.temp.trans.comn.HawbLookUpVo;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.parcll.ParcllAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.shipStation.ShipStationAPI;
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


@Service
public class ApiServiceImpl implements ApiService{
	
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ApiMapper mapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	ManagerMapper mgrMapper;
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
	FastboxAPI fastboxApi;
	
	@Autowired
	EmsApi emsApi;
	
	@Autowired
	ItcAPI itcApi;
	
	@Autowired
	YongSungAPI yslApi;
	
	@Autowired
	ManagerTakeinService mgrTakeinService;
	
	@Autowired
	ManagerService mgrService;
	
	@Autowired
	HanjinAPI hjApi;
	
	@Autowired
	ParcllAPI prclApi;
	
	@Autowired
	Type86API type86Api;
	
	@Autowired
	ShipStationAPI shipStationApi;
	
	@Autowired
	YunExpressAPI yunApi;
	
	@Autowired
	AciAPI aciApi;
	
	@Value("${orderInfoChkStatus}")
    String orderInfoChkStatus;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	Epost epost;
	
	@Autowired
	YongSung yongsung;
	
	@Autowired
	Aramex aramex;
	
	@Autowired
	YunExpress yunExpress;
	
	@Autowired
	SmtpService smtpService;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	CJHandler cjHandler;
	
	@Autowired
	VietnamPostHandler vnpHandler;
	
	@Autowired
	CommonUtils commUtils;
	
	
	
	
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
//			// TODO Auto-generated catch block
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
//			// TODO Auto-generated catch block
//			logger.error("Exception", e);
//		}
//    }
	
	public void insertApiOrderListQueue(ApiOrderListVO targetVO, ArrayList<ApiOrderItemListVO> targetItemList) throws Exception {
		try {
			String val = targetVO.getOrderNo()+targetVO.getUserId();
			HashMap<String,Object> testMap = new HashMap<String,Object>();
			testMap.put("orderList", targetVO);
			testMap.put("orderItemList", targetItemList);
			orderSet.add(val);
			queue.offer(testMap);
			testMap=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateApiOrderListQueue(ApiOrderListVO targetVO, ArrayList<ApiOrderItemListVO> targetItemList) throws Exception {
		int count = 0;
		count = mapper.selectOrderInfo(targetVO);
		if(count > 0) {
			HashMap<String,Object> testMap = new HashMap<String,Object>();
			testMap.put("orderList", targetVO);
			testMap.put("orderItemList", targetItemList);
//			mapper.updateApiOrderList(targetVO);
//			updateQueue.offer(targetVO);
			updateQueue.offer(testMap);
			testMap = null;
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
				e.printStackTrace();
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
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);
				
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
				apiOrderList.setOrderType("INSP");
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
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				
				if (jObject.getString("Receiver_State").length() > 50) {
					temp.put("Error_Msg", "Receiver_State value is too long.");
					temp.put("Status_Code", "E001");
					throw new Exception();
				}
				
				if (transCodeByRemark.equals("ACI-US")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				if(jObject.has("Exp_Licence_YN")) {
					if(jObject.getString("Exp_Licence_YN").equals("S")) {
						apiOrderList.setExpLicenceYn("N");
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("Y");
					}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
						apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("N");
						if(jObject.has("Exp_No"))
							apiOrderList.setExpNo(jObject.getString("Exp_No"));
					}else {
						apiOrderList.setExpLicenceYn("");
						apiOrderList.setExpBusinessName("");
						apiOrderList.setExpBusinessNum("");
						apiOrderList.setExpShipperCode("");
						apiOrderList.setSimpleYn("");
					}
				}else {
					apiOrderList.setExpLicenceYn("");
					apiOrderList.setExpBusinessName("");
					apiOrderList.setExpBusinessNum("");
					apiOrderList.setExpShipperCode("");
					apiOrderList.setSimpleYn("");
				}
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				//러시아 주문
				if(jObject.has("National_Id_Date")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Date").toString());
				}
				
				if(jObject.has("National_Id_Authority")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Authority").toString());
				}
				
				if(jObject.has("Cnee_Birth")) {
					apiOrderList.setNationalIdDate(jObject.get("Cnee_Birth").toString());
				}
				
				if(jObject.has("Tax_No")) {
					apiOrderList.setNationalIdDate(jObject.get("Tax_No").toString());
				}
				
				
				/* apiOrderList.setUserEmail(jObject.getString("USER_EMAIL")); */
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if(jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
				}else {
					apiOrderList.setShipperReference("");
				}
				
				if(jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
				}else {
					apiOrderList.setCneeReference1("");
				}
				
				if(jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
				}else {
					apiOrderList.setCneeReference2("");
				}
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
					
				/*무게에 따른 TransComChg 시작*/
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
    			String transCom= rstValue.getRstTransCode();
    			apiOrderList.setTransCode(transCom);
    			/*무게에 따른 TransComChg 끝*/
				if(apiOrderList.getShipperName().isEmpty()) {
					apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
				}
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
				}
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddr().isEmpty()) {
					apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
					apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
					apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
					apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
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
					
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					}
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(itemObj.get("Unit_Value").toString());
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
					apiOrderItem.setWUserIp(apiUserIp);
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					
					//러시아 아이템
					if(itemObj.has("Item_Explan")){
						apiOrderItem.setItemExplan(itemObj.get("Item_Explan").toString());
					}
					if(itemObj.has("Item_Barcode")){
						apiOrderItem.setItemBarcode(itemObj.get("Item_Barcode").toString());
					}
					if(itemObj.has("In_Box_Num")){
						apiOrderItem.setInBoxNum(itemObj.get("In_Box_Num").toString());
					}
					
					if(itemObj.has("Stock_Yn")) {
						// TODO = Y일 경우 출고 되었는지 확인, N 일경우 그냥 진행 하는 부분 하기
						if(itemObj.getString("Stock_Yn").equals("Y")) {
							apiOrderItem.setStockNo(itemObj.getString("Stock_No"));
							/////////StockNo 처리방향 생각... Table 새로파는데, NNO SUBNO 같이 받아야 할 것 같은데.. 
							mapper.insertStockOrder(apiOrderItem);
							if(mapper.checkCusItemCode(apiOrderItem)!=1) {
								//error
								temp.put("Status_Code","C10");
								temp.put("Error_Msg","Stock_No is not matching code");
								throw new Exception();
							}
						}
					}
					apiOrderItem.setTransCode(apiOrderList.getTransCode());
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("EPT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				} else if (transCodeByRemark.equals("ACI-US")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}
				
				/* apiResult */
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
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
						//tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID());
			 			tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
						hawbNo = apiOrderList.getHawbNo();
			 		}else if(transCodeByRemark.equals("ACI")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						//tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+hawbNo);
			 			tempItem.put("BL_Print_Url","No Url");
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
						//tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+TempApiOrderList.getHawbNo());
						tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+TempApiOrderList.getHawbNo());
					}else {
						tempItem.put("BL_No","");
						tempItem.put("BL_Print_Url","No Url");
						tempItem.put("BL_Pod_Url","No Url");
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
				//temp = serviceFunction.orderJsonColumnChk(jObject, transCodeByRemark); //컬럼명 필수만 확인
				temp = serviceFunction.orderJsonColumnChkV2(jObject, transCodeByRemark);
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);//값 필수만 확인
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				//defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				shipperInfo = mapper.selectShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("takein");
				if(jObject.has("BL_No")) {
					apiOrderList.setHawbNo(jObject.optString("BL_No",""));
				}
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.optString("Order_Number",""));
				}
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				

				if (transCodeByRemark.equals("ACI-US")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("ACI-T86")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}
				
				
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				
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
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if (jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.optString("Shipper_Reference",""));
				} else {
					apiOrderList.setShipperReference("");
				}
				if (jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.optString("Receiver_Reference1",""));
				} else {
					apiOrderList.setCneeReference1("");
				}
				if (jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.optString("Receiver_Reference2", ""));
				} else {
					apiOrderList.setCneeReference2("");
				}
				if (jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.optString("Payment","DDU"));
				} else {
					apiOrderList.setPayment("DDU");
				}

				
				// 2024.06.26 추가
				if (jObject.has("Tax_Number")) {
					apiOrderList.setShipperTaxNo(jObject.optString("Tax_Number"));
				} else {
					apiOrderList.setShipperTaxNo("");
				}
				
				if (jObject.has("Tax_Type")) {
					try {
						int shipperTaxType = jObject.optInt("Tax_Type");
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
						int cneeTaxType = jObject.optInt("Receiver_Tax_Type");
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
				
				
				if (!jObject.has("Receiver_Ward")) {
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
				
				/*무게에 따른 TransComChg 시작*/
				/*
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
				String transCom= rstValue.getRstTransCode();
				apiOrderList.setTransCode(transCom);
				*/
				/*무게에 따른 TransComChg 끝*/	
				apiOrderList.setTransCode(transCodeByRemark);
				shipperInfo.decryptData();

				String dstnNation = apiOrderList.getDstnNation();
				
				if(apiOrderList.getShipperName().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperName(shipperInfo.getShipperName());
					} else {
						apiOrderList.setShipperName(shipperInfo.getShipperEName());
					}
				}
				
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(shipperInfo.getShipperZip());
				}
				
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(shipperInfo.getShipperTel());
				}
				
				if(apiOrderList.getShipperAddr().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperAddr(shipperInfo.getShipperAddr());
					} else {
						apiOrderList.setShipperAddr(shipperInfo.getShipperEAddr());
					}
				}
				
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperAddrDetail(shipperInfo.getShipperAddrDetail());
					} else {
						apiOrderList.setShipperAddrDetail(shipperInfo.getShipperEAddrDetail());
					}
				}
				
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(shipperInfo.getShipperHp());
				}
				
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(shipperInfo.getShipperEmail());
				}
				
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
					apiOrderItem.setTakeInCode(takeInItem.getTakeInCode());
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
					if (!"".equals(itemObj.optString("Chg_Currency"))) {
						apiOrderItem.setChgCurrency(itemObj.optString("Chg_Currency").toUpperCase());
					} else {
						apiOrderItem.setChgCurrency(takeInItem.getUnitCurrency());
					}
					//apiOrderItem.setChgCurrency(takeInItem.getUnitCurrency());
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

				if(mapper.selectHawbNo(apiOrderList) != 0) {
					temp.put("Status_Code", "D20");
					temp.put("Error_Msg","Already registered BL No");
					throw new Exception();
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
						
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					/*
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if (!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg", rtnval.getRstMsg());
						temp.put("Status_Code", rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					*/
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				} else if (transCodeByRemark.equals("HJ")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("EPT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
			 		}
		 		}

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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if (transCodeByRemark.equals("YSL") && !expInfo.getExpNo().equals("") && !expInfo.getExpType().equals("E")) {
						HashMap<String, Object> orderInfo = new HashMap<String, Object>();
						orderInfo.put("nno", apiOrderList.getNno());
						yongsung.updateExportLicenseInfo(orderInfo);
					}
					
				}
				/* mapper.insertApiOrderList(apiOrderList); */
			}catch (Exception e) {

				if(e.getMessage()!=null) {
					logger.error("Exception", e);
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
						
			 		} else if (transCodeByRemark.equals("CJ")) {
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
	public ArrayList<Map<String, Object>> insertOrderTestTakeIn(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		JSONArray jArray1 = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> tempItem = new HashMap<String, Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		HashMap<String,Object> rst = new HashMap<String,Object>();
		ShipmentCreationResponse apiResult = new ShipmentCreationResponse();
		String transCodeByRemark = "";
		String expRegNo = "";
		BlApplyVO rtnval = new BlApplyVO ();
		
//		temp = checkApiOrderInfo(jsonHeader, request, userKey);
//		if(!temp.isEmpty()) {
//			tempItem.put("BL_No","");
//			tempItem.put("BL_Print_Url","");
////			rtnItemVal.add(tempItem);
//			temp.put("Detail",tempItem);
//			rtnVal.add(temp);
//			parameters.put("jsonHeader", jsonHeader.toString());
//			parameters.put("jsonData", jObject.toString());
//			parameters.put("wUserId",apiUserId);
//			parameters.put("wUserIp",request.getRemoteAddr());
//			parameters.put("connUrl",request.getServletPath());
//			parameters.put("rtnContents", temp.toString());
//			mapper.insertApiConn(parameters); 
//			return rtnVal;
//		}
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
				parameters.put("wUserId","TEST_"+apiUserId);
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
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				parameters.replace("userId", "TEST_"+apiUserId);
				defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId("TEST_"+apiUserId);
				apiOrderList.setOrderType("takein");
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					apiOrderList.setOrderNo(jObject.getString("Order_No"));
				}
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				if(jObject.has("Exp_Licence_YN")) {
					if(jObject.getString("Exp_Licence_YN").equals("S")) {
						apiOrderList.setExpLicenceYn("N");
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("Y");
					}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
						apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("N");
						if(jObject.has("Exp_No"))
							apiOrderList.setExpNo(jObject.getString("Exp_No"));
					}else {
						apiOrderList.setExpLicenceYn("");
						apiOrderList.setExpBusinessName("");
						apiOrderList.setExpBusinessNum("");
						apiOrderList.setExpShipperCode("");
						apiOrderList.setSimpleYn("");
					}
				}else {
					apiOrderList.setExpLicenceYn("");
					apiOrderList.setExpBusinessName("");
					apiOrderList.setExpBusinessNum("");
					apiOrderList.setExpShipperCode("");
					apiOrderList.setSimpleYn("");
				}
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				apiOrderList.setWUserId("TEST_"+apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if(jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
				}else {
					apiOrderList.setShipperReference("");
				}
				
				if(jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
				}else {
					apiOrderList.setCneeReference1("");
				}
				
				if(jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
				}else {
					apiOrderList.setCneeReference2("");
				}
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				
				/*무게에 따른 TransComChg 시작*/
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
				String transCom= rstValue.getRstTransCode();
				apiOrderList.setTransCode(transCom);
				/*무게에 따른 TransComChg 끝*/	
				
				if(apiOrderList.getShipperName().isEmpty()) {
					apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
				}
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
				}
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddr().isEmpty()) {
					apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
					apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
					apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
					apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
				}
				int jArrayLen = jArray.length();
				boolean itemCodeFlag = false;
				String failItemCode = "";
				for(int index = 0 ; index < jArrayLen; index++) {
					JSONObject itemObj = jArray.getJSONObject(index);
					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						
						int chkTakeInCode = mapper.checkTakeInCode("TEST_"+apiUserId,itemObj.getString("Customer_Item_Code"));
						if(chkTakeInCode == 0) {
							failItemCode = itemObj.getString("Customer_Item_Code")+",";
							itemCodeFlag = true;
						}
					}
				}
				if(itemCodeFlag) {
					temp.put("Status_Code","D10");
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ysRtn = ysApi.fnMakeYongsungJsonTakeIn(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
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
					Calendar c = Calendar.getInstance();
					String hawbNo = rtnval.getHawbNo();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			 		if(transCodeByRemark.equals("ARA")) {
			 			tempItem.put("BL_No",apiResult.getShipments()[0].getID());
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
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
		ProcedureVO procedureVO = new ProcedureVO();
				
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
				
				defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("NOMAL");
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
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				
				if (transCodeByRemark.equals("ACI-US")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("ACI-T86")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}
				
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				/* apiOrderList.setUserEmail(jObject.getString("USER_EMAIL")); */
				
				if(jObject.has("Exp_Licence_YN")) {
					if(jObject.getString("Exp_Licence_YN").equals("S")) {
						apiOrderList.setExpLicenceYn("N");
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setExpValue("simpleExplicence");
						apiOrderList.setSimpleYn("Y");
						apiOrderList.setAgencyBusinessName("");
					}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
						apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("N");
						apiOrderList.setExpValue("registExplicence1");
						// 2023.10.11 수출신고 대행사업자명 추가
						apiOrderList.setAgencyBusinessName(jObject.getString("Agency_Business_Name"));
						if(jObject.has("Exp_No")) {
							apiOrderList.setExpNo(jObject.getString("Exp_No"));
							apiOrderList.setExpValue("registExplicence2");
						}
					}else {
						apiOrderList.setExpLicenceYn("");
						apiOrderList.setExpBusinessName("");
						apiOrderList.setExpBusinessNum("");
						apiOrderList.setExpShipperCode("");
						apiOrderList.setSimpleYn("");
						apiOrderList.setExpValue("noExplicence");
						apiOrderList.setAgencyBusinessName("");
					}
				}else {
					apiOrderList.setExpLicenceYn("");
					apiOrderList.setExpBusinessName("");
					apiOrderList.setExpBusinessNum("");
					apiOrderList.setExpShipperCode("");
					apiOrderList.setSimpleYn("");
					apiOrderList.setExpValue("noExplicence");
					apiOrderList.setAgencyBusinessName("");
				}
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				
				//러시아 주문
				if(jObject.has("National_Id_Date")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Date").toString());
				}
				
				if(jObject.has("National_Id_Authority")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Authority").toString());
				}
				
				if(jObject.has("Cnee_Birth")) {
					apiOrderList.setNationalIdDate(jObject.get("Cnee_Birth").toString());
				}
				
				if(jObject.has("Tax_No")) {
					apiOrderList.setNationalIdDate(jObject.get("Tax_No").toString());
				}
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if(jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
				}else {
					apiOrderList.setShipperReference("");
				}
				
				if(jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
				}else {
					apiOrderList.setCneeReference1("");
				}
				
				if(jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
				}else {
					apiOrderList.setCneeReference2("");
				}
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				
				if(jObject.has("TaxNumber")) {
					apiOrderList.setTaxId(jObject.getString("TaxNumber"));
				} else {
					apiOrderList.setTaxId("");
				}
				
				if(jObject.has("EoriNumber")) {
					apiOrderList.setEoriNo(jObject.getString("EoriNumber"));
				} else {
					apiOrderList.setEoriNo("");
				}
				
				if(jObject.has("DeclarationType")) {
					apiOrderList.setDeclType(jObject.getString("DeclarationType"));
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
				
				/*무게에 따른 TransComChg 시작*/
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
    			String transCom= rstValue.getRstTransCode();
    			apiOrderList.setTransCode(transCom);
    			/*무게에 따른 TransComChg 끝*/
    			
				if(apiOrderList.getShipperName().isEmpty()) {
					apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
				}
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
				}
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddr().isEmpty()) {
					apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
					apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
					apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
					apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
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

					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					}
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(itemObj.get("Unit_Value").toString());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					if(itemObj.has("Item_Weight")) {
						apiOrderItem.setUserWtaItem(itemObj.get("Item_Weight").toString());
					}
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
					apiOrderItem.setWUserIp(apiUserIp);
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
					//러시아 아이템
					if(itemObj.has("Item_Explan")){
						apiOrderItem.setItemExplan(itemObj.get("Item_Explan").toString());
					}
					if(itemObj.has("Item_Barcode")){
						apiOrderItem.setItemBarcode(itemObj.get("Item_Barcode").toString());
					}
					if(itemObj.has("In_Box_Num")){
						apiOrderItem.setInBoxNum(itemObj.get("In_Box_Num").toString());
					}
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					if(apiOrderList.getPayment().equals("")) {
						apiOrderList.setPayment("DDU");
					}
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("SEK")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//comnApi.comnSaveHawb(request, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				} else if (transCodeByRemark.equals("ACI-US")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("ACI-T86")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("FB") || transCodeByRemark.equals("FB-EMS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("HJ")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("YT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("EPT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}
				
				if(!transCodeByRemark.equals("SEK") && !transCodeByRemark.equals("ARA") && !transCodeByRemark.equals("FED")
						&& !transCodeByRemark.equals("FES") && !transCodeByRemark.equals("FEG") && !transCodeByRemark.equals("HJ")) {
					comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
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
					licence.setAgencyBusinessName(apiOrderList.getAgencyBusinessName());
					mapper.insertExpBaseInfo(licence);
					
					if (transCodeByRemark.equals("YSL")) {
						if (licence.getExpValue().equals("registExplicence2")) {
							yslApi.fnMakeYSUpdateExpLicenceNoJson(apiOrderList.getNno());
						}	
					}
					
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
	public ArrayList<Map<String, Object>> insertTestOrderNomal(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
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
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1,transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}

				defaultApiOrderList = mapper.selectApiShipperInfo("test_"+apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId("test_"+apiUserId);
				apiOrderList.setOrderType("NOMAL");
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.getString("Order_Number"));
				}else if(jObject.has("Order_No")) {
					apiOrderList.setOrderNo(jObject.getString("Order_No"));
				}
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				if(jObject.has("Exp_Licence_YN")) {
					if(jObject.getString("Exp_Licence_YN").equals("S")) {
						apiOrderList.setExpLicenceYn("N");
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("Y");
					}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
						apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("N");
						if(jObject.has("Exp_No"))
							apiOrderList.setExpNo(jObject.getString("Exp_No"));
					}else {
						apiOrderList.setExpLicenceYn("");
						apiOrderList.setExpBusinessName("");
						apiOrderList.setExpBusinessNum("");
						apiOrderList.setExpShipperCode("");
						apiOrderList.setSimpleYn("");
					}
				}else {
					apiOrderList.setExpLicenceYn("");
					apiOrderList.setExpBusinessName("");
					apiOrderList.setExpBusinessNum("");
					apiOrderList.setExpShipperCode("");
					apiOrderList.setSimpleYn("");
				}
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				apiOrderList.setWUserId("test_"+apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if(jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
				}else {
					apiOrderList.setShipperReference("");
				}
				
				if(jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
				}else {
					apiOrderList.setCneeReference1("");
				}
				
				if(jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
				}else {
					apiOrderList.setCneeReference2("");
				}
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				apiOrderList.setSymmetryKey(userKey);
				decryptRtn = apiOrderList.dncryptData();
				if(!decryptRtn.equals("")) {
					apiOrderList = null;
					apiOrderItemList = null;
					temp.put("Status_Code","P30");
					temp.put("Error_Msg",decryptRtn+" IS DECRYPT ERROR. VALUE IS WRONG.");
					throw new Exception();
				}
				
				/*무게에 따른 TransComChg 시작*/
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
    			String transCom= rstValue.getRstTransCode();
    			apiOrderList.setTransCode(transCom);
    			/*무게에 따른 TransComChg 끝*/
    			
				if(apiOrderList.getShipperName().isEmpty()) {
					apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
				}
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
				}
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddr().isEmpty()) {
					apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
					apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
					apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
					apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
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
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					}
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(itemObj.get("Unit_Value").toString());
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
//					apiOrderItem.setPackageUnit(itemObj.getString("PACKAGE_UNIT"));
//					apiOrderItem.setExchangeRate(itemObj.getString("EXCHANGE_RATE"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
//					apiOrderItem.setChgAmt(itemObj.getString("CHG_AMT"));
//					apiOrderItem.setChnItemDetail(itemObj.getString("CHN_ITEM_DETAIL"));
//					apiOrderItem.setItemMeterial(itemObj.getString("ITEM_METERIAL"));
//					apiOrderItem.setTakeInCode(itemObj.getString("TAKE_IN_CODE"));
					if(itemObj.has("Item_Weight")) {
						apiOrderItem.setUserWtaItem(itemObj.getString("Item_Weight"));
					}
//					apiOrderItem.setUserWtcItem(itemObj.getString("USER_WTC"));
//					apiOrderItem.setWUserId(itemObj.getString("W_USER_ID")); 
					apiOrderItem.setWUserIp(apiUserIp);
//					apiOrderItem.setWDate(itemObj.getString("W_DATE"));
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					
					
//					if(itemObj.has("Trking_Number")) {
//						System.out.println("TEST");
//						if(itemObj.get("Trking_Number").getClass().getSimpleName().equals("String")) {
//							apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
//						}else if(itemObj.get("Trking_Number").getClass().getSimpleName().equals("JSONArray")) {
//							JSONArray jArrayItem = new JSONArray(itemObj.get("Trking_Number"));							
//						}
//						return rtnVal;
//					}
						
					
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
							temp.put("Error_Msg",rtnValYS.getRstMsg());
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					ysApi.fnMakeYongsungJsonDel(apiOrderList.getNno());
					
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("SEK")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					//apiOrderList.setHawbNo(rtnval.getHawbNo());
					//comnApi.comnSaveHawb(request, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
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
					Calendar c = Calendar.getInstance();
					String hawbNo = rtnval.getHawbNo();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			 		if(transCodeByRemark.equals("ARA")) {
			 			tempItem.put("BL_No",apiResult.getShipments()[0].getID());
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+apiResult.getShipments()[0].getID());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+apiOrderList.getHawbNo());
			 		}else if(transCodeByRemark.equals("ACI")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getUserId()+"_"+hawbNo);
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/deliveryTrack/"+hawbNo);
			 		} else if (transCodeByRemark.equals("HJ")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/HJ_"+apiOrderList.getUserId()+"_"+hawbNo);
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
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
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
		if (apiUserId.equals("itsel2")) {
			
		} else {
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
		
		}
	
		HashMap<String, Object> bl = new HashMap<String, Object>();

		// JrUGT5p4c9ZmO75BNlD7XjrLn9z96ouRnWWXhi1WcDE=
		
		if(jArrayData.length()>200) {
			
			RstHashMap.put("Status_Code", "-900");
			RstHashMap.put("Error_Msg", "요청 개수가 너무 많습니다. (최대 200)");
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
				RstHashMap.put("Status_Code", "-200");
				RstHashMap.put("Error_Msg", "BL 값은 필수입니다.");
				/*
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				*/
				rtnJsonArray.add(RstHashMap);
				continue;
			}
			
			
			if (HawbNo.equals("YT2414521911000280")) {
				RstHashMap.put("Bl", "YT2414521911000280");
				RstHashMap.put("OrderNo", "TESTORDER-01");
				RstHashMap.put("Consignee", "J*de");
				RstHashMap.put("WarehousingDate", "20240308");
				RstHashMap.put("ShippingDate", "20240308");
				RstHashMap.put("DeliveryCompany", "ACI-해외배송");
				RstHashMap.put("BagNo", "");
				
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "100"); 
				podDetatil.put("UpdateDateTime", "2024-03-06 19:25:45");
				podDetatil.put("UpdateLocation", "Republic of Korea");
				podDetatil.put("UpdateDescription", "Order information has been entered");	
				podDetatailArray.add(podDetatil);
				
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "200"); 
				podDetatil.put("UpdateDateTime", "2024-03-08 15:40:52");
				podDetatil.put("UpdateLocation", "Republic of Korea");
				podDetatil.put("UpdateDescription", "Finished warehousing");	
				podDetatailArray.add(podDetatil);
				
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "300"); 
				podDetatil.put("UpdateDateTime", "2024-03-08 15:40:52");
				podDetatil.put("UpdateLocation", "Republic of Korea");
				podDetatil.put("UpdateDescription", "Shipped out");	
				podDetatailArray.add(podDetatil);

				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "400"); 
				podDetatil.put("UpdateDateTime", "2024-03-17 19:42:00");
				podDetatil.put("UpdateLocation", "LONDON, THE LONDON BOROUGH OF HILLINGDON, GB");
				podDetatil.put("UpdateDescription", "Flight arrival");	
				podDetatailArray.add(podDetatil);

				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "450"); 
				podDetatil.put("UpdateDateTime", "2024-03-17 22:01:40");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "Clearance processing");	
				podDetatailArray.add(podDetatil);

				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "480"); 
				podDetatil.put("UpdateDateTime", "2024-03-17 22:58:36");
				podDetatil.put("UpdateLocation", "LONDON, LONDON, GB");
				podDetatil.put("UpdateDescription", "Clearance processing completed");	
				podDetatailArray.add(podDetatil);

				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "500"); 
				podDetatil.put("UpdateDateTime", "2024-03-20 09:02:27");
				podDetatil.put("UpdateLocation", "Slough DO");
				podDetatil.put("UpdateDescription", "Out for delivery");	
				podDetatailArray.add(podDetatil);

				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode", "600"); 
				podDetatil.put("UpdateDateTime", "2024-03-20 10:04:36");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "Delivered");	
				podDetatailArray.add(podDetatil);
				
				RstHashMap.put("TraceStatus", podDetatailArray);
				rtnJsonArray.add(RstHashMap);
				return rtnJsonArray;
			}
			

			HashMap<String, Object> BlInfo = new HashMap<String, Object>();

			BlInfo = mapper.selectPodBlInfo(HawbNo);
			if (BlInfo == null) {
				RstHashMap.put("Status_Code", "-200");
				RstHashMap.put("Error_Msg", HawbNo+" 값에 해당하는 데이터를 찾을 수 없습니다.");
				/*
				podDetatil = new LinkedHashMap<String, Object>();
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
				podDetatil.put("ProblemCode", "-22");
				podDetatil.put("Comments", "BL을 확인하여 주십시오. (Check BL No.)");
				podDetatailArray.add(podDetatil);
				RstHashMap.put("TraceStatus", podDetatailArray);
				*/
				rtnJsonArray.add(RstHashMap);
				continue;
			}

			if (apiUserId.toLowerCase().equals("vision2038") && !BlInfo.get("TRK_NO").toString().equals("")) {
				RstHashMap.put("Bl", BlInfo.get("TRK_NO"));
			} else {
				RstHashMap.put("Bl", BlInfo.get("HAWB_NO"));	
			}
			RstHashMap.put("OrderNo", BlInfo.get("ORDER_NO"));
			RstHashMap.put("Consignee", BlInfo.get("CNEE_NAME"));
			RstHashMap.put("WarehousingDate", BlInfo.get("WH_IN_DATE"));
			RstHashMap.put("ShippingDate", BlInfo.get("DEP_DATE"));
			RstHashMap.put("DeliveryCompany", BlInfo.get("DeliveryCompany"));
			RstHashMap.put("BagNo", BlInfo.get("BAG_NO"));
			
			String getTransCode = "";
			getTransCode = (String) BlInfo.get("TRANS_CODE");


			String hawbInDate = mapper.selectHawbInDate(HawbNo);		// 입고
			String mawbInDate = mapper.selectMawbInDate(HawbNo);		// 출고
			String regInDate = mapper.selectRegInDate(HawbNo);		// 주문등록
			
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

					
					if (mawbInDate != null) {
						podDetatil = new LinkedHashMap<String, Object>();
						podDetatil.put("UpdateCode", "300");
						podDetatil.put("UpdateDateTime", mawbInDate.substring(0, mawbInDate.length() - 3));
						podDetatil.put("UpdateLocation", "Republic of Korea");
						podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
						podDetatailArray.add(podDetatil);	
					}
					
					if (hawbInDate != null) {
						podDetatil = new LinkedHashMap<String, Object>();
						podDetatil.put("UpdateCode", "200");
						podDetatil.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
						podDetatil.put("UpdateLocation", "Republic of Korea");
						podDetatil.put("UpdateDescription", "Finished warehousing");
						podDetatailArray.add(podDetatil);	
					}
					
					if (regInDate != null) {
						podDetatil = new LinkedHashMap<String, Object>();
						podDetatil.put("UpdateCode", "100");
						podDetatil.put("UpdateDateTime", regInDate);
						podDetatil.put("UpdateLocation", "Republic of Korea");
						podDetatil.put("UpdateDescription", "Order information has been entered");
						podDetatailArray.add(podDetatil);	
					}
				}
				
			} else if (getTransCode.equals("YSL")) {
				String rtnJson = ysApi.makeYoungSungPodEN(HawbNo);
				podDetatailArray = ysApi.makePodDetailForEbay(rtnJson, HawbNo, request);
				//String rtnJson = ysApi.makeYoungSungPodKR(HawbNo);
				//String rtnJson2 = ysApi.makeYoungSungPodEN(HawbNo);
				//podDetatailArray = ysApi.makePodDetatailArray(rtnJson, rtnJson2, HawbNo, request);
				//podDetatailArray = ysApi.processYslPod(HawbNo, request);
			} else if (getTransCode.equals("OCS")) {
				String rtnJson = ocsApi.makeOCSPod(HawbNo);
				podDetatailArray = ocsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("EFS")) {
				String rtnJson = efsApi.makeEfsPod(HawbNo);
				podDetatailArray = efsApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("SEK")) {
				podDetatailArray = sekoApi.makeSekoPod(HawbNo);
			} else if (getTransCode.equals("CJ")) {
				
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("userRole", "ADMIN");
				hashMap.put("hawbNo", HawbNo);
				podDetatailArray = new ArrayList<HashMap<String, Object>>();
				ArrayList<Tracking> aciTracking = logisticsService.getAciTrackingList(hashMap);
				HashMap<String, Object> podDetail = new HashMap<String, Object>();
				for (int idx = 0; idx < aciTracking.size(); idx++) {
					podDetail = new HashMap<String, Object>();
					podDetail.put("UpdateCode", aciTracking.get(idx).getStatusCode());
					podDetail.put("UpdateDateTime", aciTracking.get(idx).getDateTime());
					podDetail.put("UpdateLocation", aciTracking.get(idx).getLocation());
					podDetail.put("UpdateDescription", aciTracking.get(idx).getDescription());
					
					podDetatailArray.add(podDetail);
				}
				
				CJInfo cjInfo = cjHandler.checkCJTokenInfo();
				ArrayList<Tracking> cjTracking = cjHandler.requestOneGoodsTracking(cjInfo.getTokenNo(), HawbNo);
				if (cjTracking != null) {
					for (int idx = 0; idx < cjTracking.size(); idx++) {
						podDetail = new HashMap<String, Object>();
						podDetail.put("UpdateCode", cjTracking.get(idx).getStatusCode());
						podDetail.put("UpdateDateTime", cjTracking.get(idx).getDateTime());
						podDetail.put("UpdateLocation", cjTracking.get(idx).getLocation());
						podDetail.put("UpdateDescription", cjTracking.get(idx).getDescription());
						
						podDetatailArray.add(podDetail);
					}
				}
				
				//podDetatailArray = cjapi.makePodDetailArray(HawbNo);
			} else if (getTransCode.equals("VNP")) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("userRole", "ADMIN");
				hashMap.put("hawbNo", HawbNo);
				podDetatailArray = new ArrayList<HashMap<String, Object>>();
				ArrayList<Tracking> aciTracking = logisticsService.getAciTrackingList(hashMap);
				HashMap<String, Object> podDetail = new HashMap<String, Object>();
				for (int idx = 0; idx < aciTracking.size(); idx++) {
					podDetail = new HashMap<String, Object>();
					podDetail.put("UpdateCode", aciTracking.get(idx).getStatusCode());
					podDetail.put("UpdateDateTime", aciTracking.get(idx).getDateTime());
					podDetail.put("UpdateLocation", aciTracking.get(idx).getLocation());
					podDetail.put("UpdateDescription", aciTracking.get(idx).getDescription());
					
					podDetatailArray.add(podDetail);
				}
				
				ArrayList<Tracking> vnpTracking = vnpHandler.getOrderTrackingDetails(HawbNo);
				if (vnpTracking != null) {
					for (int idx = 0; idx < vnpTracking.size(); idx++) {
						podDetail = new HashMap<String, Object>();
						podDetail.put("UpdateCode", vnpTracking.get(idx).getStatusCode());
						podDetail.put("UpdateDateTime", vnpTracking.get(idx).getDateTime());
						podDetail.put("UpdateLocation", vnpTracking.get(idx).getLocation());
						podDetail.put("UpdateDescription", vnpTracking.get(idx).getDescription());
						
						podDetatailArray.add(podDetail);
					}
				}
				
				
			} else if (getTransCode.equals("HJ")) {
				String podType = "A";
				podDetatailArray = hjApi.makeHJPod(HawbNo, request, podType, apiUserId);
			} else if (getTransCode.equals("FB")) {
				String rtnJson = fastboxApi.makeFastBoxPod(HawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("FB-EMS")) {
				String rtnJson = fastboxApi.makeFastBoxPod(HawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, HawbNo, request);
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
			} else if (getTransCode.equals("YT")) {
				podDetatailArray = yunApi.createTrackingAPI(HawbNo, request);
			} else if (getTransCode.equals("ACI")) {
				podDetatailArray = aciApi.makePodDetatailArray(HawbNo, request);
			} else if (getTransCode.equals("EPT")) {
				podDetatailArray = comnService.makeEpostPodDetatailArray(HawbNo, request);
			}

			RstHashMap.put("TraceStatus", podDetatailArray);

			rtnJsonArray.add(RstHashMap);
		}
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId", apiUserId);
		parameters.put("wUserIp", apiUserIp);
		parameters.put("connUrl",request.getServletPath());
		parameters.put("rtnContents", "");
		mapper.insertApiConn(parameters);
		return rtnJsonArray;
	}

	private ArrayList<HashMap<String, Object>> makePodDetailArrayAra(String hawbNo) {
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
			podDetail.put("CODE", "-200");
			podDetail.put("DATE_TIME", "");
			podDetail.put("LOCATION", "");
			podDetail.put("DESCRIPTION", "No Data");
			podDetailArray.add(podDetail);
		}
		return null;
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
		ShipmentVO shipment = new ShipmentVO();
		String requestVal = "";
		com.example.temp.api.Order order = new com.example.temp.api.Order();
		ProcedureRst shipmentRst = new ProcedureRst();
		
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
			    	rtn.setRstCode("D30");
		    	}
		    	
		        break;
		    case "ACI-T86":
		    	rtnVal = type86Api.selectBlApply(apiOrderList.getNno(), apiOrderList.getWUserId(), apiOrderList.getWUserIp());
		    	if (!rtnVal.getRstStatus().equals("FAIL")) {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(rtnVal.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	} else {
		    		rtn.setStatus("FAIL");
			    	rtn.setHawbNo(rtnVal.getRstHawbNo());
			    	rtn.setRstMsg(rtnVal.getRstMsg());
			    	rtn.setRstCode("D30");
		    	}
		    	break;
		    case "ACI-US":
		    	rtn = comnService.selectBlApply(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp());
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
			    	rtn.setRstCode("D30");
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
			    	rtn.setRstCode("D30");
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
			    	rtn.setRstCode("D30");
		    	}
		    	break;
		    case "YT":
		    	String jsonVal = jsonObjectToString(yunExpress.createRequestBody(parameterInfo));
		    	System.out.println(jsonVal);
		    	shipment = yunExpress.createShipment(parameterInfo, jsonVal);
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
		    		rtn.setRstCode("D30");
		    	}
		    	break;
		    case "EPT":
		    	requestVal = epost.createRequestBody(parameterInfo);
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
		    		rtn.setRstCode("D30");
		    	}
		    	break;
		    case "CJ":
		    	order.setNno(apiOrderList.getNno());
		    	order.setWUserId(apiOrderList.getWUserId());
		    	order.setWUserIp(apiOrderList.getWUserIp());
		    	order.setHawbNo("");
		    	shipmentRst = logisticsService.registShipment(order);
		    	if ("FAIL".equals(shipmentRst.getRstStatus())) {
		    		rtn.setStatus("FAIL");
		    		rtn.setHawbNo("");
		    		rtn.setRstMsg(shipment.getRstMsg());
		    		rtn.setRstCode("D30");
		    	} else {
		    		rtn.setStatus("SUCCESS");
		    		rtn.setHawbNo(shipmentRst.getRstHawbNo());
		    		rtn.setRstMsg("-");
		    		rtn.setRstCode("A10");
		    	}
		    	break;
		    case "VNP":
		    	parameterInfo.put("orderType", apiOrderList.getOrderType().toUpperCase());
		    	order = logisticsMapper.selectTmpOrder(parameterInfo);
		    	order.dncryptData();
		    	order = vnpHandler.createOrder(order);
		    	if (order.getStatus().isEmpty()) {
		    		shipmentRst = logisticsMapper.execSpRegShipment(order);
		    		if ("SUCCESS".equals(shipmentRst.getRstStatus().toUpperCase())) {
						commUtils.storeLabelFile(order);
						rtn.setStatus("SUCCESS");
						rtn.setHawbNo(order.getHawbNo());
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
					rtn.setRstMsg(order.getStatus());
					rtn.setRstCode("D30");
		    	}
		    	break;
	        default:
	        	rtn = comnService.selectBlApply(apiOrderList.getNno(),apiOrderList.getWUserId(),apiOrderList.getWUserIp());
	        	break;
		    }
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
		return rtn;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void fn_TransComApply_rollback(String transCodeByRemark, ApiOrderListVO apiOrderList, ArrayList<ApiOrderItemListVO> apiOrderItemList, HttpServletRequest request) throws Exception {
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
	public ArrayList<LinkedHashMap<String, Object>> insertTestResgtItemCode(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		String errorMsg = "";
		String itemCode = "";
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", jArrayData.toString());
		parameters.put("wUserId","test_"+apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("userId", apiUserId);
		mapper.insertApiConn(parameters); 
		parameters = new HashMap<String,Object>();
		for (int Index = 0 ; Index < jArrayData.length() ;  Index++) {
			try {
				errorMsg = "";
				RstHashMap = new LinkedHashMap<String,Object>();
				JSONObject itemObj = jArrayData.getJSONObject(Index);
				Iterator iter = itemObj.keySet().iterator();
				String json = "";
				itemCode = itemObj.getString("Customer_Item_Code");
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
				
				parameters.put("cusItemCode", itemCode);
				parameters.put("itemDetail", itemObj.getString("Item_Detail"));
				parameters.put("nativeItemDetail", itemObj.getString("Item_Native_Detail"));
				parameters.put("makeCntry", itemObj.getString("Make_Country"));
				parameters.put("itemImgUrl", itemObj.getString("Item_Url"));
				parameters.put("userId", "test_"+apiUserId);
				if(!mapper.cusItemCodeExist(parameters)) {
					
					ysApi.insertCusItemCode(parameters);
					
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "SUCCESS");
					RstHashMap.put("Result", itemCode+" Regist.");
				}else {
					RstHashMap.put("Customer_Item_Code", itemCode);
					RstHashMap.put("Status", "ERROR");
					RstHashMap.put("Result", "Already Regist Customer_Item_Code.");
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
		return rtnJsonArray;
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
					// TODO: handle exception
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
	public ArrayList<LinkedHashMap<String, Object>> takeInTestItemReg(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiUserId = "TEST_"+jsonHeader.get("userid").toString();
		LinkedHashMap<String,Object> RstHashMap  = new LinkedHashMap<String,Object>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		//temp = checkApiOrderInfo(jsonHeader, request, userKey);
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
			int cusItemCnt = mapper.checkTakeInCode(apiUserId, parameters.get("cusItemCode").toString());
			
			if(cusItemCnt == 1) {
				try {
					mapper.updateUserTakeinInfo(parameters);
					RstHashMap.put("rstStatus", "SUCCESS");
			        RstHashMap.put("rstMsg", "");
					RstHashMap.put("Customer_Item_Code", itemObj.getString("Customer_Item_Code"));
				}catch (Exception e) {
					// TODO: handle exception
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
		parameters.put("connUrl",request.getServletPath());
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
	public ArrayList<Map<String, Object>> insertOrderTestEtc(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
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
				etcOrderList.put("orderText", "TEST_"+jObject.getString("Order_Text"));
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
					etcOrderItem.put("itemCnt", itemObj.get("Item_Count").toString());
					etcOrderItem.put("subNo", itemIndex+1);
					mgrTakeinService.insertTbTakeinEtcOrderItem(etcOrderItem);
				}
				
				temp.put("Status_Code","A10");
				temp.put("Error_Msg","-");
				temp.put("Status", "SUCCESS");
			}catch (Exception e) {
				// TODO: handle exception
				temp.put("Status_Code","D10");
				temp.put("Error_Msg","SQL ERROR!");
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
		//parameters.put("orgStation",  "082");
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
		//mapper.insertApiConn(parameters); 
		
		return temp;
	}

	/*
	@Override
	public Map<String, Object> instrumentPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		

		Map<String, Object> temp = new HashMap<String, Object>();

		
		JSONArray _jArray = new JSONArray(jsonData);
		for(int i =0; i < _jArray.length(); i++) {
		
		ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
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
			System.out.println("jsonData : "+_jObject);
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
	}

		return temp;
	}
	*/
	
	@Override
	   public Map<String, Object> instrumentPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception {
	      ArrayList<Map<String, Object>> rtnVal = new ArrayList<Map<String, Object>>();   
	      Map<String, Object> temp = new HashMap<String, Object>();
	      Map<String, Object> tempItem = new HashMap<String, Object>();
	      HashMap<String,Object> rst = new HashMap<String,Object>();
	      String orgStation = "";
	      String errorList = "";
	      String result = "";
	      String hawbNo = "";
	      try {
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
	               //mapper.insertApiConn(parameters2); 
	               return temp;
	            }else {
	               try {
	                  orderInfo = comnService.selectUserRegistOrderOne(nno);
	                  String orderType = orderInfo.getOrderType().toUpperCase();
	                  if(orderType.equals("NOMAL") ||orderType.equals("TAKEIN") ||orderType.equals("INSP") || orderType.equals("RETURN")){
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
	                     double height = Double.parseDouble(_jObject.get("VOL_HEIGHT").toString());
	                     double width = Double.parseDouble(_jObject.get("VOL_WIDTH").toString());
	                     double length = Double.parseDouble(_jObject.get("VOL_LENGTH").toString());
	                     if(dimInfo.get("perDim").equals("5000.0")) {
	                        double dimPer = Double.parseDouble(dimInfo.get("perDim").toString());
	                        wtc = (height*width*length)/dimPer;
	                        orderRcpt.setWtc(Double.toString(wtc));
	                     }else {
	                    	if (orderInfo.getUserId().equals("itseltest")) {
	                    		double wtv = Double.parseDouble(_jObject.get("WTV").toString());
	                    		orderRcpt.setWtc(String.valueOf(Math.ceil(wtv)));
	                    	} else {
	                    		orderRcpt.setWtc(_jObject.get("WTV").toString());	
	                    	}
	                    	//orderRcpt.setWtc(_jObject.get("WTV").toString());
	                     }
	                     orderRcpt.setWUserId(orgStation+"instrument");
	                     orderRcpt.setWUserIp(request.getRemoteAddr());
	                     procedure = mgrMapper.execNomalHawbIn(orderRcpt);
	                     
	                     mgrMapper.deleteVolume(nno);
	                     volume.setNno(nno);
	                     volume.setWUserId(orgStation+"instrument");
	                     volume.setWUserIp(request.getRemoteAddr());
	                     if (orderInfo.getUserId().equals("itseltest")) {
	                    	 volume.setHeight(String.valueOf(Math.ceil(height)));
	                    	 volume.setWidth(String.valueOf(Math.ceil(width)));
	                    	 volume.setLength(String.valueOf(Math.ceil(length)));
	                     } else {
	                    	 volume.setHeight(_jObject.get("VOL_HEIGHT").toString());
		                     volume.setWidth(_jObject.get("VOL_WIDTH").toString());
		                     volume.setLength(_jObject.get("VOL_LENGTH").toString());	 
	                     }
	                     
	                     String per = mapper.selectTransPer(orderInfo.getTransCode());
	                     int temp2 = (int)Double.parseDouble(per);
	                     volume.setPer(Integer.toString(temp2));
	                     volume.setWtUnit(orderInfo.getWtUnit());
	                     volume.setDimUnit(orderInfo.getDimUnit());
	                    // volume.setHeight(_jObject.get("VOL_HEIGHT").toString());
	                    // volume.setWidth(_jObject.get("VOL_WIDTH").toString());
	                    // volume.setLength(_jObject.get("VOL_LENGTH").toString());	 
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
	                     hawbVo.setTransCode(orderInfo.getTransCode());
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
	                     
	                    // mapper.insertApiConn(parameters2); 
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
	                     //mapper.insertApiConn(parameters2); 
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
	                 // mapper.insertApiConn(parameters2); 
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
							//mgrMapper.updateHawbMawb2(hawbVo.getHawbNo());
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

	@Override
	public ArrayList<LinkedHashMap<String, Object>> ebayBlPod(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String,Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();
		
		String apiUserId = jsonHeader.get("userid").toString();
		String apiUserIp = request.getRemoteAddr();
		LinkedHashMap<String, Object> RstHashMap = new LinkedHashMap<String, Object>();
 		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JSONArray jArrayData = new JSONArray(jsonData);
		temp = chkApiHeader(jsonHeader, request);
		
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
		if (jArrayData.length() > 100) {
			RstHashMap.put("Error_Msg", "Too Many Rquests (UP TO 100)");
			RstHashMap.put("Status_Code", "L40");
			rtnJsonArray.add(RstHashMap);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jArrayData.toString());
			parameters.put("wUserId", apiUserId);
			parameters.put("wUserIp", apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", rtnJsonArray.toString());
			
			
			return rtnJsonArray;
		}
		
		for (int index = 0; index < jArrayData.length(); index++) {
			JSONObject itemObj = jArrayData.getJSONObject(index);
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
			
			RstHashMap = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> podDetatil = new LinkedHashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray = new ArrayList<HashMap<String, Object>>();

			String HawbNo = (String) bl.get("BL");
			if (HawbNo.equals("") || HawbNo == null) {
				podDetatil.put("UpdateCode", "-200");
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "BL이 잘못되었습니다. (Incorrect Bl No.)");
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

			String getTransCode = "";
			getTransCode = (String) BlInfo.get("TRANS_CODE");
			
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
					String hawbInDate = mapper.selectHawbInDate(HawbNo);	// 입고
					String mawbInDate = mapper.selectMawbInDate(HawbNo);	// 출고
					String regInDate = mapper.selectRegInDate(HawbNo);		// 주문등록
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
				String rtnJson = ysApi.makeYoungSungPodEN(HawbNo);
				podDetatailArray = ysApi.makePodDetailForEbay(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("CJ")) {
				podDetatailArray = cjapi.makePodDetailForArray(HawbNo);
			} else if (getTransCode.equals("EMN")) {
				podDetatailArray = emsApi.makeEmsForPod(HawbNo);
			} else if (getTransCode.equals("FB")) {
				String rtnJson = fastboxApi.makeFastBoxPod(HawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("FB-EMS")) {
				String rtnJson = fastboxApi.makeFastBoxPod(HawbNo, request);
				podDetatailArray = fastboxApi.makePodDetailArray(rtnJson, HawbNo, request);
			} else if (getTransCode.equals("ITC")) {
				podDetatailArray = itcApi.makeItcPodNew(HawbNo);
			} else if (getTransCode.equals("HJ")) {
				String podType = "A";
				podDetatailArray = hjApi.makeHJPod(HawbNo, request, podType, apiUserId);
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

	public Map<String, Object> chkApiHeader(Map<String, Object> jsonHeader, HttpServletRequest request) {
		Map<String, Object> temp = new HashMap<String, Object>();
		String apiKey = "wGpjkGLGDDL56SWe9qS8uBafvghMGch6pw6EbxvjCFbuQ";
		
		try {
		
			if (!jsonHeader.get("content-type").equals("application/json")) {
				temp.put("Error_Msg","In case content type is not JSON type");
				temp.put("Status_Code","L10");
				return temp;
			}
			
			if (!jsonHeader.get("userid").toString().toLowerCase().equals("ebay")) {
				temp.put("Error_Msg", "USER IS IS NOT MATCHING");
				temp.put("Status_Code","L20");
				return temp;
			}
			
			if (!jsonHeader.get("apikey").equals(apiKey)) {
				temp.put("Error_Msg", "API KEY IS NOT MATCHING");
				temp.put("Status_Code", "L30");
				return temp;
			}
		} catch (Exception error) {
			temp.put("Error_Msg", error.toString());
			temp.put("Status_Code", "D10");
			return temp;
		}
		return temp;
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectViewDeliveryList() throws Exception {
		return mapper.selectViewDeliveryList();
	}

	@Override
	public ApiShopifyResultVO registOrderShipStationUsps(HttpServletRequest request, String nno) throws Exception {
		return shipStationApi.registOrderShipStationUsps(request, nno);
	}

	@Override
	public void updateApiOrderListHawb(ApiOrderListVO updateShopifyOrder) throws Exception {
		mapper.updateApiOrderListHawb(updateShopifyOrder);
	}

	@Override
	public String getShopifyOrderList(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return shopifyApi.getShopifyOrderList(apiShopifyInfoVO,request,response);
	}

	@Override
	public ArrayList<Map<String, Object>> insertOrderNomalTest(Map<String, Object> jsonHeader,
			Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {
		
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
		ProcedureVO procedureVO = new ProcedureVO();
		
		temp = checkApiOrderInfo(jsonHeader, request, userKey);
		if(!temp.isEmpty()) {
			tempItem.put("BL_No","");
			tempItem.put("BL_Print_Url","");
			temp.put("Detail",tempItem);
			rtnVal.add(temp);
			parameters.put("jsonHeader", jsonHeader.toString());
			parameters.put("jsonData", jObject.toString());
			parameters.put("wUserId",apiUserId);
			parameters.put("wUserIp",apiUserIp);
			parameters.put("connUrl",request.getServletPath());
			parameters.put("rtnContents", temp.toString());
			//mapper.insertApiConn(parameters);
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
				//mapper.insertApiConn(parameters); 
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
				
				defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("NOMAL");
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
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				
				if (transCodeByRemark.equals("ACI-US")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}

				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}
				
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				/* apiOrderList.setUserEmail(jObject.getString("USER_EMAIL")); */
				/*
				if(jObject.has("Exp_Licence_YN")) {
					if(jObject.getString("Exp_Licence_YN").equals("S")) {
						apiOrderList.setExpLicenceYn("N");
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setExpValue("simpleExplicence");
						apiOrderList.setSimpleYn("Y");
						apiOrderList.setAgencyBusinessName("");
					}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
						apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
						apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
						apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
						apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
						apiOrderList.setSimpleYn("N");
						apiOrderList.setExpValue("registExplicence1");
						// 2023.10.11 수출신고 대행사업자명 추가
						apiOrderList.setAgencyBusinessName(jObject.getString("Agency_Business_Name"));
						if(jObject.has("Exp_No")) {
							apiOrderList.setExpNo(jObject.getString("Exp_No"));
							apiOrderList.setExpValue("registExplicence2");
						}
					}else {
						apiOrderList.setExpLicenceYn("");
						apiOrderList.setExpBusinessName("");
						apiOrderList.setExpBusinessNum("");
						apiOrderList.setExpShipperCode("");
						apiOrderList.setSimpleYn("");
						apiOrderList.setExpValue("noExplicence");
						apiOrderList.setAgencyBusinessName("");
					}
				}else {
					apiOrderList.setExpLicenceYn("");
					apiOrderList.setExpBusinessName("");
					apiOrderList.setExpBusinessNum("");
					apiOrderList.setExpShipperCode("");
					apiOrderList.setSimpleYn("");
					apiOrderList.setExpValue("noExplicence");
					apiOrderList.setAgencyBusinessName("");
				}
				*/
				
				// 2024.03.18 수출신고항목 변경
				String expType = jObject.optString("Exp_Type").toUpperCase();
				String expCor = "";
				String expRprsn = "";
				String expAddr = "";
				String expZip = "";
				String expRgstrNo = "";
				String expCstCd = "";
				String expNo = "";
				
				if (!expType.equals("N")) {
					apiOrderList.setExpType(expType);
					if (jObject.has("Exp_Company_Name")) {
						expCor = jObject.optString("Exp_Company_Name");
					}
					if (jObject.has("Exp_Company_Representative")) {
						expRprsn = jObject.optString("Exp_Company_Representative");
					}
					if (jObject.has("Exp_Company_Address")) {
						expAddr = jObject.optString("Exp_Company_Address");
					}
					if (jObject.has("Exp_Company_Zip")) {
						expZip = jObject.optString("Exp_Company_Zip");
					}
					if (jObject.has("Exp_Registration_No")) {
						expRgstrNo = jObject.optString("Exp_Registration_No");
					}
					if (jObject.has("Exp_Customs_Code")) {
						expCstCd = jObject.optString("Exp_Customs_Code");
					}
					if (jObject.has("Exp_No")) {
						expNo = jObject.optString("Exp_No");
					}
				} else {
					apiOrderList.setExpType("N");
				}
				
				apiOrderList.setExpCor(expCor);
				apiOrderList.setExpRprsn(expRprsn);
				apiOrderList.setExpAddr(expAddr);
				apiOrderList.setExpZip(expZip);
				apiOrderList.setExpRgstrNo(expRgstrNo);
				apiOrderList.setExpCstCd(expCstCd);
				apiOrderList.setExpNo(expNo);
				apiOrderList.setSendYn("N");
				
				
				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				
				//러시아 주문
				if(jObject.has("National_Id_Date")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Date").toString());
				}
				
				if(jObject.has("National_Id_Authority")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Authority").toString());
				}
				
				if(jObject.has("Cnee_Birth")) {
					apiOrderList.setNationalIdDate(jObject.get("Cnee_Birth").toString());
				}
				
				if(jObject.has("Tax_No")) {
					apiOrderList.setNationalIdDate(jObject.get("Tax_No").toString());
				}
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if(jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
				}else {
					apiOrderList.setShipperReference("");
				}
				
				if(jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
				}else {
					apiOrderList.setCneeReference1("");
				}
				
				if(jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
				}else {
					apiOrderList.setCneeReference2("");
				}
				
				if(jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.getString("Payment"));
				}else {
					apiOrderList.setPayment("");
				}
				
				if(jObject.has("TaxNumber")) {
					apiOrderList.setTaxId(jObject.getString("TaxNumber"));
				} else {
					apiOrderList.setTaxId("");
				}
				
				if(jObject.has("EoriNumber")) {
					apiOrderList.setEoriNo(jObject.getString("EoriNumber"));
				} else {
					apiOrderList.setEoriNo("");
				}
				
				if(jObject.has("DeclarationType")) {
					apiOrderList.setDeclType(jObject.getString("DeclarationType"));
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
				
				/*무게에 따른 TransComChg 시작*/
				/*
				ProcedureVO rstValue = new ProcedureVO();
				HashMap<String,Object> transParameter = new HashMap<String,Object>();
				transParameter.put("nno", apiOrderList.getNno());
				transParameter.put("orgStation", apiOrderList.getOrgStation());
				transParameter.put("dstnNation", apiOrderList.getDstnNation());
				transParameter.put("userId", apiOrderList.getUserId());
				transParameter.put("wta", apiOrderList.getUserWta());
				transParameter.put("wtc", apiOrderList.getUserWtc());
				transParameter.put("wUserId", apiOrderList.getWUserId());
				transParameter.put("wUserIp", apiOrderList.getWUserIp());
				transParameter.put("transCode", transCodeByRemark);
				rstValue  = comnService.selectTransComChangeForVo(transParameter);
				String transCom= rstValue.getRstTransCode();
				apiOrderList.setTransCode(transCom);
				*/
				/*무게에 따른 TransComChg 끝*/
				apiOrderList.setTransCode(transCodeByRemark);
				
				if(apiOrderList.getShipperName().isEmpty()) {
					apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
				}
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
				}
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddr().isEmpty()) {
					apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
					apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
					apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(defaultApiOrderList.getShipperTel());
					apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
				}
				if(apiOrderList.getShipperEmail().isEmpty()) {
					apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
					apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
				}
				
				if (!apiOrderList.getExpType().equals("N")) {
					if (apiOrderList.getExpCor().equals("")) {
						apiOrderList.setExpCor(defaultApiOrderList.getRegName());
					}
					if (apiOrderList.getExpRprsn().equals("")) {
						apiOrderList.setExpRprsn(defaultApiOrderList.getRegRprsn());
					}
					if (apiOrderList.getExpAddr().equals("")) {
						apiOrderList.setExpAddr(defaultApiOrderList.getRegAddr());
						apiOrderList.expAddrDncrypt(originKey.getSymmetryKey());
					}
					if (apiOrderList.getExpZip().equals("")) {
						apiOrderList.setExpZip(defaultApiOrderList.getRegZip());
					}
					if (apiOrderList.getExpRgstrNo().equals("")) {
						apiOrderList.setExpRgstrNo(defaultApiOrderList.getRegNo());
						apiOrderList.expRgstrNoDncrypt(originKey.getSymmetryKey());
					}
					if (apiOrderList.getExpCstCd().equals("")) {
						apiOrderList.setExpCstCd(defaultApiOrderList.getBizCustomsNo());
						apiOrderList.expCstCdDncrypt(originKey.getSymmetryKey());
					}
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

					ApiOrderItemListVO apiOrderItem = new ApiOrderItemListVO();
					apiOrderItem.setNno(apiOrderList.getNno());
					apiOrderItem.setSubNo(Integer.toString(index+1));
					apiOrderItem.setOrgStation(apiOrderList.getOrgStation());
					apiOrderItem.setUserId(apiOrderList.getUserId());
					if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
						apiOrderItem.setCusItemCode("A00A001");
					}else {
						apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
					}
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(itemObj.get("Unit_Value").toString());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					if(itemObj.has("Item_Weight")) {
						apiOrderItem.setUserWtaItem(itemObj.get("Item_Weight").toString());
					}
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
					apiOrderItem.setWUserIp(apiUserIp);
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
					//러시아 아이템
					if(itemObj.has("Item_Explan")){
						apiOrderItem.setItemExplan(itemObj.get("Item_Explan").toString());
					}
					if(itemObj.has("Item_Barcode")){
						apiOrderItem.setItemBarcode(itemObj.get("Item_Barcode").toString());
					}
					if(itemObj.has("In_Box_Num")){
						apiOrderItem.setInBoxNum(itemObj.get("In_Box_Num").toString());
					}
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","ARA");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					if(apiOrderList.getPayment().equals("")) {
						apiOrderList.setPayment("DDU");
					}
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						if(rtnValYS.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
				}else if(transCodeByRemark.equals("OCS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","API ERROR");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}else if(transCodeByRemark.equals("EFS")){
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						if(rtnValEfs.getRstCode().equals("D30")) {
							temp.put("Status_Code","D30");
						}else {
							temp.put("Status_Code","API ERROR");
						}
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("SEK")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//comnApi.comnSaveHawb(request, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				} else if (transCodeByRemark.equals("ACI-US")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("ACI-T86")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("FB") || transCodeByRemark.equals("FB-EMS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("HJ")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("YT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("EPT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}
				
				if(!transCodeByRemark.equals("SEK") && !transCodeByRemark.equals("ARA") && !transCodeByRemark.equals("FED")
						&& !transCodeByRemark.equals("FES") && !transCodeByRemark.equals("FEG") && !transCodeByRemark.equals("HJ")) {
					comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
					
					if(!rst.get("rstCode").equals("S10")) {
						temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
				}
				
				if (!apiOrderList.getExpType().equals("N")) {
					ExportDeclare expInfo = new ExportDeclare();
					expInfo.setNno(apiOrderList.getNno());
					expInfo.setUserId(apiOrderList.getUserId());
					expInfo.setExpType(apiOrderList.getExpType());
					expInfo.setExpCor(apiOrderList.getExpCor());
					expInfo.setExpRprsn(apiOrderList.getExpRgstrNo());
					expInfo.setExpAddr(apiOrderList.getExpAddr());
					expInfo.setExpZip(apiOrderList.getExpZip());
					expInfo.setExpRgstrNo(apiOrderList.getExpRgstrNo());
					expInfo.setExpCstCd(apiOrderList.getExpCstCd());
					expInfo.setExpNo(apiOrderList.getExpNo());
					expInfo.setSendYn(apiOrderList.getSendYn());
					expInfo.encryptData();
					mapper.insertExportDeclare(expInfo);
					
					if (transCodeByRemark.equals("YSL")) {
						HashMap<String, Object> orderInfo = new HashMap<String, Object>();
						orderInfo.put("nno", apiOrderList.getNno());
						orderInfo.put("userId", apiOrderList.getUserId());
						
						if (!expInfo.getExpNo().equals("")) {
							yongsung.updateExportLicenseInfo(orderInfo);
						}
					}
				}

				/* mapper.insertApiOrderList(apiOrderList); */
			}catch (Exception e) {
				if(e.getMessage()!=null) {
					temp.put("Status_Code","D10");
					temp.put("Error_Msg",e.getMessage());
				}
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
	public ArrayList<Map<String, Object>> insertOrderNomalV2(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception {

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
		ProcedureVO procedureVO = new ProcedureVO();
		
		
		if (apiUserId.equals("itsel2")) {
			
		} else {
			temp = checkApiOrderInfo(jsonHeader, request, userKey);
			if(!temp.isEmpty()) {
				tempItem.put("BL_No","");
				tempItem.put("BL_Print_Url","");
//				rtnItemVal.add(tempItem);
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
		/*
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
		*/
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
				
				temp = serviceFunction.orderJsonValueChk(jObject, index1, transCodeByRemark);
				
				if(temp.size()>0) {
					temp.put("Status_Code","P10");
					throw new Exception();
				}
				
				//defaultApiOrderList = mapper.selectApiShipperInfo(apiUserId);
				shipperInfo = mapper.selectShipperInfo(apiUserId);
				apiOrderList.setNno(newNno);
				apiOrderList.setOrgStation(jObject.getString("Departure_Station"));
				apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
				apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
				apiOrderList.setUserId(apiUserId);
				apiOrderList.setOrderType("NOMAL");
				if(jObject.has("BL_No")) {
					apiOrderList.setHawbNo(jObject.optString("BL_No",""));
				}
				if(jObject.has("Order_Number")) {
					apiOrderList.setOrderNo(jObject.optString("Order_Number",""));
				}
				apiOrderList.setBoxCnt(jObject.get("Box_Count").toString());
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
				apiOrderList.setBuySite(jObject.getString("Buy_Site"));
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
				apiOrderList.setCneeCntry(jObject.getString("Receiver_Country"));
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
				

				if(mapper.selectHawbNo(apiOrderList) != 0) {
					temp.put("Status_Code", "D20");
					temp.put("Error_Msg","Already registered BL No");
					throw new Exception();
				}
				if(mapper.selectOrderInfo(apiOrderList)!=0) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","Already registed data");
					throw new Exception();
				}
				
				if (transCodeByRemark.equals("ACI-US")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("ACI-T86")) {
					if (jObject.getString("Receiver_State").length() > 2) {
						temp.put("Error_Msg", "Receiver_State value is too long.");
						temp.put("Status_Code", "E001");
						throw new Exception();
					}
				}
				
				if (transCodeByRemark.equals("YSL")) {
					if(apiOrderList.getCneeState().length() > 30) {
						temp.put("Error_Msg","Receiver_State value is too long.");
						temp.put("Status_Code","E001");
						throw new Exception();
					}	
				}
				
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
				/* apiOrderList.setUserEmail(jObject.getString("USER_EMAIL")); */
				
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

				if(jObject.has("Food")) {
					if(jObject.getString("Food").equals("Y")) {
						apiOrderList.setFood("Y");
					}else {
						apiOrderList.setFood("N");
					}
				}else {
					apiOrderList.setFood("N");
				}
				
				
				//러시아 주문
				if(jObject.has("National_Id_Date")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Date").toString());
				}
				
				if(jObject.has("National_Id_Authority")) {
					apiOrderList.setNationalIdDate(jObject.get("National_Id_Authority").toString());
				}
				
				if(jObject.has("Cnee_Birth")) {
					apiOrderList.setNationalIdDate(jObject.get("Cnee_Birth").toString());
				}
				
				if(jObject.has("Tax_No")) {
					apiOrderList.setNationalIdDate(jObject.get("Tax_No").toString());
				}
				apiOrderList.setWUserId(apiUserId);
				apiOrderList.setWUserIp(apiUserIp);
				/* apiOrderList.setWDate(jObject.getString("W_DATE")); */
				
				apiOrderList.setTransCode(transCodeByRemark);
				
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
				if (jObject.has("Shipper_Reference")) {
					apiOrderList.setShipperReference(jObject.optString("Shipper_Reference",""));
				} else {
					apiOrderList.setShipperReference("");
				}
				if (jObject.has("Receiver_Reference1")) {
					apiOrderList.setCneeReference1(jObject.optString("Receiver_Reference1",""));
				} else {
					apiOrderList.setCneeReference1("");
				}
				if (jObject.has("Receiver_Reference2")) {
					apiOrderList.setCneeReference2(jObject.optString("Receiver_Reference2", ""));
				} else {
					apiOrderList.setCneeReference2("");
				}
				if (jObject.has("Payment")) {
					apiOrderList.setPayment(jObject.optString("Payment","DDU"));
				} else {
					apiOrderList.setPayment("DDU");
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
				
				/*무게에 따른 TransComChg 시작*/
				/*
    			ProcedureVO rstValue = new ProcedureVO();
    			HashMap<String,Object> transParameter = new HashMap<String,Object>();
    			transParameter.put("nno", apiOrderList.getNno());
    			transParameter.put("orgStation", apiOrderList.getOrgStation());
    			transParameter.put("dstnNation", apiOrderList.getDstnNation());
    			transParameter.put("userId", apiOrderList.getUserId());
    			transParameter.put("wta", apiOrderList.getUserWta());
    			transParameter.put("wtc", apiOrderList.getUserWtc());
    			transParameter.put("wUserId", apiOrderList.getWUserId());
    			transParameter.put("wUserIp", apiOrderList.getWUserIp());
    			transParameter.put("transCode", transCodeByRemark);
    			rstValue  = comnService.selectTransComChangeForVo(transParameter);
    			String transCom= rstValue.getRstTransCode();
    			apiOrderList.setTransCode(transCom);
    			*/
    			/*무게에 따른 TransComChg 끝*/
				apiOrderList.setTransCode(transCodeByRemark);
				shipperInfo.decryptData();
				
				String dstnNation = apiOrderList.getDstnNation();
				
				if(apiOrderList.getShipperName().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperName(shipperInfo.getShipperName());
					} else {
						apiOrderList.setShipperName(shipperInfo.getShipperEName());
					}
				}
				
				if(apiOrderList.getShipperZip().isEmpty()) {
					apiOrderList.setShipperZip(shipperInfo.getShipperZip());
				}
				
				if(apiOrderList.getShipperTel().isEmpty()) {
					apiOrderList.setShipperTel(shipperInfo.getShipperTel());
				}
				
				if(apiOrderList.getShipperAddr().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperAddr(shipperInfo.getShipperAddr());
					} else {
						apiOrderList.setShipperAddr(shipperInfo.getShipperEAddr());
					}
				}
				
				if(apiOrderList.getShipperAddrDetail().isEmpty()) {
					if (dstnNation.equals("KR")) {
						apiOrderList.setShipperAddrDetail(shipperInfo.getShipperAddrDetail());
					} else {
						apiOrderList.setShipperAddrDetail(shipperInfo.getShipperEAddrDetail());
					}
				}
				
				if(apiOrderList.getShipperHp().isEmpty()) {
					apiOrderList.setShipperHp(shipperInfo.getShipperHp());
				}
				
				if(apiOrderList.getShipperEmail().isEmpty()) {
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
					
					if (apiOrderList.getUserId().equals("dabonea_ems")) {
						if ("".equals(itemObj.optString("Customer_Item_Code"))) {
							apiOrderItem.setCusItemCode(apiOrderList.getOrderNo()+"-"+apiOrderItem.getSubNo());
						} else {
							apiOrderItem.setCusItemCode(itemObj.optString("Customer_Item_Code"));
						}
						
					} else {
						if(itemObj.getString("Customer_Item_Code").equals("6080371982512")) {
							apiOrderItem.setCusItemCode("A00A001");
						}else {
							apiOrderItem.setCusItemCode(itemObj.getString("Customer_Item_Code"));
						}	
					}
					
					apiOrderItem.setHsCode(itemObj.getString("Hs_Code"));
					apiOrderItem.setItemDetail(itemObj.getString("Item_Detail"));
					apiOrderItem.setItemCnt(itemObj.get("Item_Cnt").toString());
					apiOrderItem.setUnitValue(itemObj.get("Unit_Value").toString());
					apiOrderItem.setWtUnit(apiOrderList.getWtUnit());
					if(itemObj.has("Item_Weight")) {
						apiOrderItem.setUserWtaItem(itemObj.get("Item_Weight").toString());
					}
					apiOrderItem.setBrand(itemObj.getString("Brand"));
					apiOrderItem.setMakeCntry(itemObj.getString("Make_Country"));
					apiOrderItem.setMakeCom(itemObj.getString("Make_Company"));
					
					if (apiOrderList.getUserId().equals("dabonea_ems")) {
						String hsCode = apiOrderItem.getHsCode();
						hsCode = hsCode.replaceAll("[^0-9]", "");
						
						switch (hsCode) {
						case "3304999000":
							apiOrderItem.setItemDiv("COSMETIC");
							break;
						case "9607191000":
							apiOrderItem.setItemDiv("PLASTIC");
							break;
						case "9603900000":
							apiOrderItem.setItemDiv("PLASTIC");
							break;
						default:
							apiOrderItem.setItemDiv("");
							break;
						}
					} else {
						apiOrderItem.setItemDiv(itemObj.getString("Item_Div"));
					}
					
					apiOrderItem.setQtyUnit(itemObj.getString("Qty_Unit"));
					apiOrderItem.setChgCurrency(itemObj.getString("Chg_Currency"));
					apiOrderItem.setWUserIp(apiUserIp);
					apiOrderItem.setItemUrl(itemObj.getString("Item_Url"));
					apiOrderItem.setItemImgUrl(itemObj.getString("Item_Img_Url"));
					apiOrderItem.setTrkCom(itemObj.getString("Trking_Company"));
					apiOrderItem.setTrkNo(itemObj.getString("Trking_Number"));
					apiOrderItem.setTrkDate(itemObj.getString("Trking_Date"));
					apiOrderItem.setNativeItemDetail(itemObj.getString("Native_Item_Detail"));
					//러시아 아이템
					if(itemObj.has("Item_Explan")){
						apiOrderItem.setItemExplan(itemObj.get("Item_Explan").toString());
					}
					if(itemObj.has("Item_Barcode")){
						apiOrderItem.setItemBarcode(itemObj.get("Item_Barcode").toString());
					}
					if(itemObj.has("In_Box_Num")){
						apiOrderItem.setInBoxNum(itemObj.get("In_Box_Num").toString());
					}
					apiOrderItem.setTransCode(apiOrderList.getTransCode());
					
					apiOrderItemList.add(apiOrderItem);
				}
/*
				if(mapper.selectHawbNo(apiOrderList) != 0) {
					temp.put("Status_Code", "D20");
					temp.put("Error_Msg","Already registered BL No");
					throw new Exception();
				}
				if(mapper.selectOrderInfo(apiOrderList)!=0) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","Already registed data");
					throw new Exception();
				}
				
				*/
				if(orderSet.contains(apiOrderList.getOrderNo()+apiOrderList.getUserId())) {
					temp.put("Status_Code","D20");
					temp.put("Error_Msg","The OrderNo is registered in the logic.");
					throw new Exception();
				}
				
				
				// 데이터 저장 시점
				// 배송사 송장번호 채번 및 배송 데이터 등록 진행
				/* Aramex Api 결과*/
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
						temp.put("Error_Msg",notifiMsg);
						temp.put("Status_Code","D30");
						throw new Exception();
					}else {
						apiOrderList.setHawbNo(apiResult.getShipments()[0].getID());
					}
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					if(apiOrderList.getPayment().equals("")) {
						apiOrderList.setPayment("DDU");
					}
					expRegNo = apiOrderList.getHawbNo();
					insertApiOrderListQueue(apiOrderList,apiOrderItemList);
					
				}else if(transCodeByRemark.equals("YSL")){
					//용성 등록 로직
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					String ysRtn = ysApi.fnMakeYongsungJson(apiOrderList.getNno());
					ProcedureVO rtnValYS = new ProcedureVO();
					rtnValYS = ysApi.getYongSungRegNo(ysRtn, apiOrderList.getNno(), apiUserId, apiUserIp);
					String result = rtnValYS.getRstStatus();
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					expRegNo = rtnValYS.getRstHawbNo();
					
					// 아이템코드 오류인 경우 메일 전송 추가
					if (result.equals("FAIL")) {
						if (apiOrderList.getDstnNation().equals("JP")) {
							for (int ii = 0; ii < apiOrderItemList.size(); ii++) {
								HashMap<String, String> yslItemCode = new HashMap<>();
								yslItemCode = mapper.selectYslItemCode(apiOrderItemList.get(ii));
								String takeinCode = yslItemCode.get("takeInCode");
								String takeinCode2 = yslItemCode.get("takeInCode2");
								
								if ("Y".equals(takeinCode) || "Y".equals(takeinCode2)) {
									continue;
								}
								
								HashMap<String,Object> cusItemParameter = new HashMap<String,Object>();
								cusItemParameter.put("cusItemCode", apiOrderItemList.get(ii).getCusItemCode());
								cusItemParameter.put("itemDetail", apiOrderItemList.get(ii).getItemDetail());
								cusItemParameter.put("nativeItemDetail", apiOrderItemList.get(ii).getNativeItemDetail());
								cusItemParameter.put("makeCntry", apiOrderItemList.get(ii).getMakeCntry());
								cusItemParameter.put("itemImgUrl", apiOrderItemList.get(ii).getItemImgUrl());
								cusItemParameter.put("userId", apiOrderItemList.get(ii).getUserId());
								try {
									ysApi.insertCusItemCode(cusItemParameter);
								} catch (Exception e) {
									temp.put("Error_Msg",rtnValYS.getRstMsg());
									temp.put("Status_Code","D30");
									fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
									throw new Exception();
								}
							}
							
							smtpService.sendMail();
						}
						
						temp.put("Error_Msg",rtnValYS.getRstMsg());
						temp.put("Status_Code","D30");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					
					/*
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if (!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg", rtnval.getRstMsg());
						temp.put("Status_Code", rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					*/
				}else if(transCodeByRemark.equals("OCS")) {
					
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					String ocsRtn = ocsApi.fnMakeOCSJson(apiOrderList.getNno());
					ProcedureVO rtnValOcs = new ProcedureVO();
					
					rtnValOcs = ocsApi.updateHawbNo(ocsRtn, apiOrderList.getNno());
					String result = rtnValOcs.getRstStatus();
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValOcs.getRstMsg());
						temp.put("Status_Code","D30");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					rtnval.setHawbNo(rtnValOcs.getRstHawbNo());
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					
				}else if(transCodeByRemark.equals("EFS")){
					
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					if(request.getServletPath().contains("Modify")) {
						rtnval.setHawbNo(apiOrderList.getHawbNo());
						mapper.updateHawb(rtnval);
					}
					
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					String efsRtn = efsApi.fnMakeEfsJson(apiOrderList.getNno());
					ProcedureVO rtnValEfs = new ProcedureVO();
					rtnValEfs = efsApi.getCheckResult(efsRtn, apiOrderList.getNno());
					String result = rtnValEfs.getRstStatus();
					
					expRegNo = rtnValEfs.getRstHawbNo();
					
					if(result.equals("FAIL")) {
						temp.put("Error_Msg",rtnValEfs.getRstMsg());
						temp.put("Status_Code","D30");
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
				}else if(transCodeByRemark.equals("ACI")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				}else if(transCodeByRemark.equals("SEK")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
					//comnApi.comnSaveHawb(request, apiOrderList.getNno());
					//insertApiOrderListQueue(apiOrderList,apiOrderItemList);
				} else if (transCodeByRemark.equals("ACI-US")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("ACI-T86")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("FB") || transCodeByRemark.equals("FB-EMS")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("HJ")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("YT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("EPT")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else if (transCodeByRemark.equals("VNP")) {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				} else {
					apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
					apiOrderList.encryptData();
					rtnval = fn_TransComApply(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					if(!rtnval.getStatus().equals("SUCCESS")) {
						temp.put("Error_Msg",rtnval.getRstMsg());
						temp.put("Status_Code",rtnval.getRstCode());
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
					}
					apiOrderList.setHawbNo(rtnval.getHawbNo());
				}
				
				if(!transCodeByRemark.equals("SEK") && !transCodeByRemark.equals("ARA") && !transCodeByRemark.equals("FED")
						&& !transCodeByRemark.equals("FES") && !transCodeByRemark.equals("FEG") && !transCodeByRemark.equals("HJ") && !transCodeByRemark.equals("CJ")
						&& !transCodeByRemark.equals("VNP")) {
					comnApi.comnSaveHawb(apiUserId, apiUserIp, transCodeByRemark, apiOrderList.getNno());
				}
				
				if (transCodeByRemark.equals("CJ")) {
					cjHandler.storeCJLabel(apiOrderList, apiOrderItemList);
				}
				
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
					}
			 		
			 		if(!rst.get("rstCode").equals("S10")) {
			 			temp.put("Error_Msg",rst.get("rstMsg"));
						temp.put("Status_Code",rst.get("rstCode"));
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
			 		}
		 		}
		 		
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
						fn_TransComApply_rollback(transCodeByRemark, apiOrderList, apiOrderItemList, request);
						throw new Exception();
		 			}
		 			
		 			if (transCodeByRemark.equals("YSL") && !expInfo.getExpNo().equals("") && !expInfo.getExpType().equals("E")) {
		 				HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		 				orderInfo.put("nno", apiOrderList.getNno());
		 				yongsung.updateExportLicenseInfo(orderInfo);
		 			}
		 			
		 		}
				/*
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
					licence.setAgencyBusinessName(apiOrderList.getAgencyBusinessName());
					mapper.insertExpBaseInfo(licence);
					
					if (transCodeByRemark.equals("YSL")) {
						if (licence.getExpValue().equals("registExplicence2")) {
							yslApi.fnMakeYSUpdateExpLicenceNoJson(apiOrderList.getNno());
						}	
					}
					
				}
		 		*/
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
			 		}else if (transCodeByRemark.equals("CJ")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+week+"/"+apiOrderList.getNno());
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/tracking?hawbNo="+hawbNo);
			 		}else if (transCodeByRemark.equals("VNP")) {
			 			if(rtnval.getHawbNo() == null || rtnval.getHawbNo().equals("")) {
			 				hawbNo = apiOrderList.getHawbNo();
			 			}
			 			String nno = apiOrderList.getNno();
			 			year = nno.substring(0,4);
			 			String date = nno.substring(4,8);
			 			tempItem.put("BL_No",hawbNo);
						tempItem.put("BL_Print_Url","http://img.mtuai.com/outbound/hawb/"+year+"/"+date+"/"+nno);
						tempItem.put("BL_Pod_Url","https://wms.acieshop.com/comn/tracking?hawbNo="+hawbNo);
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
				}

				parameters.put("rtnContents", temp.toString());
				mapper.updateApiConn(parameters);
				rtnVal.add(temp);
			}
		}

		return rtnVal;
	}
	
	public String jsonObjectToString(Object object) {
		Gson gson = new Gson();
		String requestVal = gson.toJson(object);
		
		return requestVal;
	}


}

