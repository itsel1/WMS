package com.example.temp.api.aci.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.temp.api.CommonUtils;
import com.example.temp.api.Order;
import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.aci.service.ApiV1ReturnService;
import com.example.temp.api.aci.vo.ExportDeclareVO;
import com.example.temp.api.ecommerce.service.ShopifyHandler;
import com.example.temp.api.logistics.dto.AramexParameter;
import com.example.temp.api.logistics.service.AramexHandler;
import com.example.temp.api.logistics.service.CJHandler;
import com.example.temp.api.logistics.service.FastboxHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.VietnamPostHandler;
import com.example.temp.api.shipment.ShippingServiceImpl;
import com.example.temp.api.shipment.company.Aramex;
import com.example.temp.api.shipment.company.Dk;
import com.example.temp.api.shipment.company.Epost;
import com.example.temp.api.shipment.company.FastBoxTest;
import com.example.temp.api.shipment.company.Mus;
import com.example.temp.api.shipment.company.YunExpress;
import com.example.temp.api.shop.ShopMapper;
import com.example.temp.api.shop.shopee.HandleShopeeService;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.member.mapper.MemberECommerceMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.smtp.SmtpService;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.hanjin.HanjinMapper;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.itemCarry.ItcMapper;
import com.example.temp.trans.shipStation.ShipStationAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.t86.Type86Mapper;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.example.temp.trans.yunexpress.YunExpressAPI;
import com.google.gson.Gson;


@RestController
public class ApiV1ReturnController {
	@Autowired
	ApiV1ReturnService returnService;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	FedexAPI fedexApi;
	
	@Autowired
	EmsApi emsApi;
	
	@Autowired
	FastboxAPI fbApi;
	
	@Autowired
	Type86API t86Api;
	
	@Autowired
	YongSungAPI ysApi;
	
	@Autowired
	HanjinAPI hjApi;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	SmtpService smtpService;
	
	@Autowired
	ManagerService mgrService;
	
	@Autowired
	HanjinMapper hjMapper;
	
	@Autowired
	Type86Mapper t86Mapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	ShipStationAPI ssApi;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	YunExpressAPI yunApi;
	
	@Autowired
	Epost epost;
	
	@Autowired
	ShopMapper shopMapper;
	
	@Autowired
	ShippingServiceImpl shipping;
	
	@Autowired
	Aramex aramex;
	
	@Autowired
	YunExpress yunexpress;
	
	@Autowired
	Dk dk;
	
	@Autowired
	Mus mus;
	
	ComnVO comnS3Info;
	
	@Autowired
	ItcMapper itcMapper;
	
	@Autowired
	ItcAPI itcApi;
	
	@Autowired
	FastBoxTest fastboxTest;
	
	@Autowired
	HandleShopeeService shopee;
	
	@Autowired
	ShopifyHandler shopify;
	
	@Autowired
	CJHandler cjHandler;
	
	@Autowired
	MemberECommerceMapper memberEcMapper;
	
	@Autowired
	VietnamPostHandler vnPostHandler;
	
	@Autowired
	FastboxHandler fbHandler;

	@Autowired
	CommonUtils commUtils;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	AramexHandler araHandler;

	LinkedBlockingQueue<HawbVO> queue = new LinkedBlockingQueue<HawbVO>();
	
	
	@RequestMapping(value = "/api/v1/return/userIdChk", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public String apiReturnUserIdChk(HttpServletRequest request, HttpServletResponse response, Model model, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception {
		String jsonString = "";
		Gson gson = new Gson();
		HashMap<String, Object> errors = new HashMap<String,Object>();
		LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();
		
		errors = returnService.checkApiOrderInfo(jsonHeader, request);
		
		if (errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors,jsonHeader,jsonData,request);
		    jsonString = gson.toJson(errors);
		} else {
			String sellerId = jsonData.get("SELLER_ID").toString();
			int count = returnService.selectSellerIdChk(sellerId);
			
			if (count > 0) {
				HashMap<String, Object> result = returnService.selectSellerIdChkAprv(sellerId);

				if (!result.get("APRV_YN").equals("Y")) {
					rst.put("STATUS", "FAIL");
					rst.put("CODE", "P10");
					rst.put("SELLER_ID", result.get("USER_ID").toString());
					rst.put("MSG", sellerId + " is not approved.");
					jsonString = gson.toJson(rst);
				} else {
					rst.put("STATUS", "SUCCESS");
					rst.put("CODE", "S10");
					rst.put("SELLER_ID", result.get("USER_ID").toString());
					rst.put("MSG", sellerId + " is exists.");
					jsonString = gson.toJson(rst);
				}
				
			} else {
				rst.put("STATUS", "FAIL");
				rst.put("CODE", "P01");
				rst.put("SELLER_ID", sellerId);
				rst.put("MSG", sellerId + " is not exists.");
				jsonString = gson.toJson(rst);
			}
		}
		
		return jsonString;
	}
	
	@RequestMapping(value = "/api/v1/return/returnAddress", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String apiReturnAddress(HttpServletRequest request, HttpServletResponse response, Model model, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception {
		String jsonString = "";
	
		Gson gson = new Gson();
		HashMap<String, Object> errors = new HashMap<String, Object>();
		LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();	
		LinkedHashMap<String, Object> addrInfo = new LinkedHashMap<String, Object>();
		
		errors = returnService.checkApiOrderInfo(jsonHeader, request);
		
		if (errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors, jsonHeader, jsonData, request);
			jsonString = gson.toJson(errors);
		} else {
			String stationName = jsonData.get("STATION_CODE").toString();
			
			int count = returnService.selectReturnStationAddrCnt(stationName);
			
			if (count > 0) {
				LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>();
				info = returnService.selectReturnStationAddr(stationName);
				addrInfo.put("STATION_ADDR", info.get("STATION_ADDR"));
				addrInfo.put("STATION_ADDR_DETAIL", info.get("STATION_ADDR_DETAIL"));
				addrInfo.put("STATION_ZIP", info.get("STATION_ZIP"));
				addrInfo.put("COM_NAME", info.get("COMPANY_NAME"));
				addrInfo.put("ATTN_NAME", info.get("ATTN_NAME"));
				addrInfo.put("ATTN_TEL", info.get("STATION_TEL"));
				rst.put("STATUS", "SUCCESS");
				rst.put("CODE", "S01");
				rst.put("STATION_CODE", stationName);
				rst.put("STATION_INFO", addrInfo);
				rst.put("MSG", stationName + " is valid data.");
				jsonString = gson.toJson(rst);
			} else {
				rst.put("STATUS", "FAIL");
				rst.put("CODE", "P01");
				rst.put("STATION_CODE", stationName);
				rst.put("MSG", stationName + " is invalid data.");
				jsonString = gson.toJson(rst);
			}
		}
		
		
		return jsonString;
	}
	
	
	
	@RequestMapping(value = "/api/v1/item/{eshipId}/{blNo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> LookUpReturnItem(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("eshipId") String eshipId, @PathVariable("blNo") String blNo) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.getEshopItem(jsonHeader, eshipId, blNo, request);
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		returnService.insertApiconn(errors, jsonHeader, jsonData, request);
		return errors;
	}
	
	@RequestMapping(value = "/api/v1/eshopOrder", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> getEshopOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> info = new HashMap<String, Object>();
		
		String apiKey = request.getParameter("apiKey");
		String eshopId = request.getParameter("eshopId");
		String blNo = request.getParameter("blNo");
		
		info = returnService.selectEshopOrderInfo(request, apiKey, eshopId, blNo);
		
		return info;
	}
	
	@RequestMapping(value = "/api/v1/eshopOrderItem", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> getEshopOrderItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> info = new HashMap<String, Object>();
		
		String blNo = request.getParameter("blNo");
		
		info = returnService.selectEshopOrderItem(blNo);
		
		return info;
	}
	
	
	@RequestMapping(value = "/api/v1/return/approval/reference/{sellerId}/{parameters}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> approvalRefReturnRequest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("sellerId") String sellerId, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.acceptReturnRequest("ref",parameters,sellerId,jsonHeader,request);
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		returnService.insertApiconn(errors, jsonHeader, jsonData, request);
		return errors;
	}
	
	@RequestMapping(value = "/api/v1/return/approval/regNo/{sellerId}/{parameters}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> approvalRegNoReturnRequest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("sellerId") String sellerId, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.acceptReturnRequest("regNo",parameters,sellerId,jsonHeader,request);
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		returnService.insertApiconn(errors, jsonHeader, jsonData, request);
		return errors;
	}
	
	@RequestMapping(value = "/api/v1/return/approval/koblNo/{sellerId}/{parameters}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> approvalKoblNoReturnRequest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("sellerId") String sellerId, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.acceptReturnRequest("koblNo",parameters,sellerId,jsonHeader,request);
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		returnService.insertApiconn(errors, jsonHeader, jsonData, request);
		return errors;
	}

	//@RequestMapping(value = "/api/v1/return", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> insertReturnPost(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		
		errors = returnService.setOrderRequestVal(jsonHeader, jsonData, request);
		
		if(errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors,jsonHeader,jsonData,request);
			return errors;
		}else {
			errors = returnService.insertOrderRequestData(errors);
			returnService.insertApiconn(errors, jsonHeader, jsonData, request);
			return errors;
		}
	}

	 //JSON DATA 받기 
	@RequestMapping(value = "/api/v1/return", method = RequestMethod.PATCH, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> insertReturnPatch(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception{
		//업데이트/ 한번 들어왔다가 접수 취소 당하고 다시 넣는 경우?
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.setOrderRequestVal(jsonHeader, jsonData, request);
		if(errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors,jsonHeader,jsonData,request);
			return errors;
		}else {
			errors = returnService.updateOrderRequestData(errors);//update 구현해야 함.!
			returnService.insertApiconn(errors, jsonHeader, jsonData, request);
			return errors;
		}
	} 
	
	//test
	@RequestMapping(value = "/api/v1/test/item/{sellerId}/{blNo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> LookUpReturnItemTest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("sellerId") String sellerId, @PathVariable("blNo") String blNo) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.getEshopItem(jsonHeader, sellerId, blNo, request);
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		returnService.insertApiconn(errors, jsonHeader, jsonData, request);
		return errors;
	}
	
	/*
	@RequestMapping(value = "/api/v1/test/return", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> insertReturnPostTest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.setOrderRequestVal(jsonHeader, jsonData, request);
		if(errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors,jsonHeader,jsonData,request);
			return errors;
		}else {
			ReturnRequestVO tempRtnVal = (ReturnRequestVO)errors.get("rtnVal");
			tempRtnVal.setWUserId("test"+tempRtnVal.getWUserId());
			tempRtnVal.setWUserIp("test"+tempRtnVal.getWUserIp());
			errors.put("rtnVal", tempRtnVal);
			errors = returnService.insertOrderRequestData(errors);
			returnService.insertApiconn(errors, jsonHeader, jsonData, request);
			return errors;
		}
	}
	
	@RequestMapping(value = "/api/v1/test/return", method = RequestMethod.PATCH, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> insertReturnPatchTest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception{
		HashMap<String,Object> errors = new HashMap<String,Object>();
		errors = returnService.setOrderRequestVal(jsonHeader, jsonData, request);
		if(errors.get("STATUS").equals("FAIL")) {
			returnService.insertApiconn(errors,jsonHeader,jsonData,request);
			return errors;
		}else {
			ReturnRequestVO tempRtnVal = (ReturnRequestVO)errors.get("rtnVal");
			tempRtnVal.setWUserId("test"+tempRtnVal.getWUserId());
			tempRtnVal.setWUserIp("test"+tempRtnVal.getWUserIp());
			errors.put("rtnVal", tempRtnVal);
			errors = returnService.updateOrderRequestData(errors);//update 구현해야 함.!
			returnService.insertApiconn(errors, jsonHeader, jsonData, request);
			return errors;
		}
	}
	*/
	//@RequestMapping(value = "/api/v1/return/status/reference/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> returnStatusOrderReference(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>(); 
		returnVal = returnService.selectReturnSttatus(request,jsonHeader,parameters,"ORDER_REFERENCE");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	//@RequestMapping(value = "/api/v1/return/status/regNo/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> returnStatusOrderRegNo(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>(); 
		returnVal = returnService.selectReturnSttatus(request,jsonHeader,parameters,"REG_NO");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	//@RequestMapping(value = "/api/v1/return/status/koblNo/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String,Object> returnStatusKoblNo(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @PathVariable("parameters") String parameters) throws Exception{
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>(); 
		returnVal = returnService.selectReturnSttatus(request,jsonHeader,parameters,"KOBL_NO");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}

	/* 반품 API */
	@RequestMapping(value = "/api/v1/return", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiRegistReturn(HttpServletRequest request, HttpServletResponse response, 
			@RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		
		JSONObject jsonBody = new JSONObject(jsonData);
		
		rstMap = returnService.setApiRequestVal(jsonHeader, jsonData, request);
		apiParams.put("jsonHeader", jsonHeader.toString());
		apiParams.put("jsonData", jsonBody.toString());
		apiParams.put("wUserId", jsonHeader.get("userid").toString());
		apiParams.put("wUserIp", request.getRemoteAddr());
		apiParams.put("connUrl", request.getServletPath());
		
		JSONObject jsonRstBody = new JSONObject(rstMap);
		apiParams.put("rtnContents", jsonRstBody.toString());
		apiService.insertApiConn(apiParams);

		
		return rstMap;
	}
	
	@RequestMapping(value = "/api/v1/test/return", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiRegistReturnTest(HttpServletRequest request, HttpServletResponse response, 
			@RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		
		JSONObject jsonBody = new JSONObject(jsonData);
		
		rstMap = returnService.setApiRequestVal(jsonHeader, jsonData, request);
		apiParams.put("jsonHeader", jsonHeader.toString());
		apiParams.put("jsonData", jsonBody.toString());
		apiParams.put("wUserId", jsonHeader.get("userid").toString());
		apiParams.put("wUserIp", request.getRemoteAddr());
		apiParams.put("connUrl", request.getServletPath());
		JSONObject jsonRstBody = new JSONObject(rstMap);
		apiParams.put("rtnContents", jsonRstBody.toString());
		apiService.insertApiConn(apiParams);

		
		return rstMap;
	}
	
	@RequestMapping(value = "/api/v1/return/status/reference/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiReturnPodRef(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, 
			@PathVariable("parameters") String parameters) throws Exception {
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>();
		returnVal = returnService.setApiPodVal(request, jsonHeader, parameters, "ORDER_REFERENCE");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	@RequestMapping(value = "/api/v1/return/status/regNo/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiReturnPodRegNo(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, 
			@PathVariable("parameters") String parameters) throws Exception {
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>();
		returnVal = returnService.setApiPodVal(request, jsonHeader, parameters, "REG_NO");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	@RequestMapping(value = "/api/v1/return/status/trkNo/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiReturnPodTrkNo(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, 
			@PathVariable("parameters") String parameters) throws Exception {
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>();
		returnVal = returnService.setApiPodVal(request, jsonHeader, parameters, "TRK_NO");
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	@RequestMapping(value = "/api/v1/test/return/status/reference/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiReturnPodRefTest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, 
			@PathVariable("parameters") String parameters) throws Exception {
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>();
		if (parameters.equals("202307041418428093BJJ")) {
			returnVal = returnService.setApiPodValTest2(request, jsonHeader, parameters, "ORDER_REFERENCE");
		} else {
			returnVal = returnService.setApiPodValTest(request, jsonHeader, parameters, "ORDER_REFERENCE");	
		}
		
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	@RequestMapping(value = "/api/v1/test/return/status/regNo/{parameters}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public HashMap<String, Object> apiReturnPodRegNoTest(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, 
			@PathVariable("parameters") String parameters) throws Exception {
		HashMap<String,Object> returnVal = new HashMap<String,Object>();
		HashMap<String,Object> jsonData = new LinkedHashMap<>();
		if (parameters.equals("202307041418428093BJJ")) {
			returnVal = returnService.setApiPodValTest2(request, jsonHeader, parameters, "REG_NO");
		} else {
			returnVal = returnService.setApiPodValTest(request, jsonHeader, parameters, "REG_NO");	
		}
		
		returnService.insertApiconn(returnVal, jsonHeader, jsonData, request);
		return returnVal;
	}
	
	@RequestMapping(value = "/api/test/getApiKey", method = RequestMethod.GET)
	public String getApiKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnString = "";
		
		try {

			Date today = new Date();
			String date = new SimpleDateFormat("yyyyMMddHHmmss").format(today);
			String apiKey = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO";
	
			//apiKey = SecurityKeyVO.getSymmetryKey();
			String aciKey = "";
			String str = date+"|testuser";
			//str = date+"|pickpack";
			aciKey = AES256Cipher.AES_Encode(str, apiKey);
	
			returnString = aciKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		return returnString;
	}


	@RequestMapping(value = "/api/test/returnData", method = RequestMethod.GET)
	public void testtest2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			
			/*
			LabelPrintingRequest labelRequest = new LabelPrintingRequest();
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setAccountCountryCode("KR");
			clientInfo.setAccountEntity("SEL");
			clientInfo.setAccountNumber("172813");
			clientInfo.setAccountPin("321321");
			clientInfo.setUserName("overseas2@aciexpress.net");
			clientInfo.setPassword("Aci5606!");
			clientInfo.setVersion("1.0");
			labelRequest.setClientInfo(clientInfo);
			
			Transaction transaction = new Transaction();
			transaction.setReference1("001");
			transaction.setReference2("");
			transaction.setReference3("");
			transaction.setReference4("");
			transaction.setReference5(""); 
			labelRequest.setTransaction(transaction);
			
			labelRequest.setShipmentNumber("34067141334");
			labelRequest.setOriginEntity("SEL");
			
			LabelInfo labelInfo = new LabelInfo();
			labelInfo.setReportID(9201);  
			labelInfo.setReportType("URL");
			labelRequest.setLabelInfo(labelInfo);
			
			LabelPrintingResponse apiResult = new LabelPrintingResponse();
			apiResult = apiProxy.printLabel(labelRequest);
			String labelUrl = apiResult.getShipmentLabel().getLabelURL();
			System.out.println(labelUrl);
			
			FileOutputStream fos = null;
			InputStream is = null;
			String ImageDir = realFilePath + "image/" + "aramex/";
			fos = new FileOutputStream(ImageDir+ "34067141334.PDF");
			
			URL url = new URL(labelUrl);
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
			File file = new File(ImageDir+ "34067141334.PDF");
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/2024/30", "dralthea"+"_"+"34067141334", file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			file.delete();
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String jsonObjectToString(Object object) {
		Gson gson = new Gson();
		String requestVal = gson.toJson(object);
		
		return requestVal;
	}

	@RequestMapping(value = "/api/test/getExcelData", method = RequestMethod.GET)
	public void testtest3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			String depDate = request.getParameter("depDate");
			
			ArrayList<ExportDeclareVO> excelData = new ArrayList<ExportDeclareVO>();
			excelData = comnMapper.selectExportDeclareData(depDate);
			
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/dev/exportDeclareExcel.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
	
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
	
			Font font = wb.createFont();
			font.setFontHeightInPoints((short)10);
			
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(false);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
	
			int rowNo = 2;
			
			for (int i = 0; i < excelData.size(); i++) {
				excelData.get(i).dncryptData();
				
				row = sheet.createRow(rowNo);
				
				cell = row.createCell(0);
				cell.setCellValue(excelData.get(i).getOrderNo());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(excelData.get(i).getOrderDate());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(excelData.get(i).getHawbNo());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(excelData.get(i).getExpCor());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(excelData.get(i).getExpRprsn());
				cell.setCellStyle(cellStyle);

				cell = row.createCell(9);
				cell.setCellValue(excelData.get(i).getExpAddr());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(excelData.get(i).getExpZip());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(11);
				cell.setCellValue(excelData.get(i).getExpRgstrNo().replaceAll("[^0-9]", ""));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(excelData.get(i).getExpCstCd());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(13);
				cell.setCellValue("AE0087");
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(17);
				cell.setCellValue(excelData.get(i).getCneeName());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(18);
				cell.setCellValue(excelData.get(i).getDstnNation());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(22);
				cell.setCellValue(excelData.get(i).getPayment());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(23);
				cell.setCellValue(excelData.get(i).getUnitCurrency());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(24);
				cell.setCellValue(excelData.get(i).getTotalItemValue());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(25);
				cell.setCellValue(excelData.get(i).getBoxCnt());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(27);
				cell.setCellValue(excelData.get(i).getWta());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(28);
				cell.setCellValue(excelData.get(i).getWtUnit());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(33);
				cell.setCellValue(excelData.get(i).getSubNo());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(34);
				cell.setCellValue(excelData.get(i).getItemCnt());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(35);
				cell.setCellValue(excelData.get(i).getQtyUnit());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(36);
				cell.setCellValue(excelData.get(i).getUnitValue());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(37);
				cell.setCellValue(excelData.get(i).getItemValue());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(39);
				cell.setCellValue(excelData.get(i).getItemDetail().replaceAll("[^a-zA-Z0-9 ]", ""));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(41);
				cell.setCellValue(excelData.get(i).getHsCode());
				cell.setCellStyle(cellStyle);
				
				
	
				rowNo++;
			}
			
			String fileName = depDate+".xlsx";
			response.setContentType("ms_vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
	
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/api/testView", method = RequestMethod.GET)
	public String apiTestView(HttpServletRequest request, HttpServletResponse reesponse, Model model) throws Exception {
		String str = "";
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("nno", "202408221516042993JBJ");
		sqlParams.put("userId", "itsel2");
		sqlParams.put("orderType", "NOMAL");
		
		Order order = logisticsService.selectTbOrderList(sqlParams);
		order.dncryptData();
		AramexParameter araParams = new AramexParameter();
		araParams.setOrigin("SEL");
		araParams.setDestination("JNB");
		araParams.setChgWtUnit("KG");
		araParams.setChgWtValue(2.17);
		araParams.setDescriptionOfGoods("MENS JACKET");
		araParams.setNumberOfPieces(1);
		araParams.setProductGroup("EXP");
		araParams.setProductType("PPX");
		araParams.setPaymentType("P");
		araParams.setPaymentOptions("");
		araParams.setCustomsValueAmountCurrencyCode("USD");
		araParams.setCustomsValueAmountValue(12);
		araParams.setServices("");
		araParams.setOriginCity("Gangseo-Gu");
		araParams.setDestinationCity("Kyalami");
		order.setAraParams(araParams);

		String zplText = araHandler.getLabelZplFormat(order);
		
		str = zplText;
		return str;
	}
	
	
	@RequestMapping(value = "/api/getZpl", method = RequestMethod.GET)
	public String getZPLCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String origin = "SEL";
        String destination = "DOH";
        String date = "Aug 16, 2024";
        String productGroup = "EXP";
        String productType = "PPX";
        String payment = "P";
        String foreignRef = "202408161814559506CHC";
        String description = "Description: *Ktown4u Applicant Benefit : Exclusive Selfie Photocard (B Ver.) Random 1p out of 2p* YOON SAN-HA (ASTRO) - [Video Call Sign Event] 1st Mini Album [DUSK] (Random Ver.)";
        if (description.length() > 68) {
        	description = description.substring(0, 68);
        }
        String account = "172813";
        String hypercape1 = "hypercape";
        String hypercape2 = "hypercape";
        String address = "Seodaemun-Gu";
        String country = "KR";
        String weight = "0.59 KG";
        String chargeable = "0.59 KG";
        String pieces = "1";

        StringBuilder zplBuilder = new StringBuilder();

        // 라벨 디자인
        zplBuilder.append("^XA\n");
        
        // 사각형 및 구역 정의
        zplBuilder.append("^FO20,15^GB772,1188,2^FS");
        zplBuilder.append("^FO20,15^GB772,170,1^FS");
        zplBuilder.append("^FO20,15^GB250,170,1^FS");
        zplBuilder.append("^FO20,184^GB140,90,1^FS");
        zplBuilder.append("^FO160,184^GB632,90,1^FS");
        zplBuilder.append("^FO20,273^GB140,70,1^FS");
        zplBuilder.append("^FO160,273^GB140,70,1^FS");
        zplBuilder.append("^FO300,273^GB492,70,1^FS");
        zplBuilder.append("^FO20,342^GB772,50,1^FS");
        zplBuilder.append("^FO20,391^GB772,150,1^FS");
        zplBuilder.append("^FO20,540^GB772,150,1^FS");
        zplBuilder.append("^FO20,922^GB772,50,5^FS");
        zplBuilder.append("^FO20,1112^GB772,90,1^FS");
        
        // 아라멕스 로고
        zplBuilder.append("^FO25,25^GFA,1170,1170,30,,:K078N07CI0FP0EJ038L03E,J07FF83FC1FE1FF80FFE07F83FC0FFC003FF8J03IF001FFI03FE,I01IFE3FE1FE7FF83IF87F83FE3IF00IFCJ0JFC01FF8007FE,I03JF3FE1KF0JFE7F83FE7IF81JFI03KF00FFC00FFC,I07LFE1JFE1MF83LFC7JF8007KF80FFC01FF8,I0MFE1JFC3MF83LFE7JF800LFC07FE01FF8,001MFE1JFC7MF83RFC01LFE03FF03FF,003MFE1JF87MF83RFC03MF03FF87FE,007FFE3IFE1IF00IFC7IF83IF9KF7FFC03FF807FF01FF87FE,007FF007FFE1FFC00FFE00IF83FFC07IF80FFE07FE001FF80FFCFFC,00FFE001FFE1FF801FFC003FF83FF803IF007FE07FCI0FF807JF8,00FFCI0FFE1FF801FF8001FF83FF001FFE007FE0FF8I07FC07JF,00FF8I07FE1FF003FFI01FF83FF001FFC003FE0FF8I03FC03JF,01FF8I07FE1FF003FEJ0FF83FEI0FFC003FE1FFJ03FE01IFE,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1NFE00IFC,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1NFE00IF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE003FF,01FEJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF,01FEJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE007FF8,01FFJ03FE1FF003FCJ07F83FEI0FF8003FE1NFE00IFC,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1FEN01IFE,01FFJ03FE1FF003FEJ0FF83FEI0FF8003FE1FFN03IFE,01FFJ07FE1FF003FEJ0FF83FEI0FF8003FE1FFN03JF,00FF8I07FE1FF003FFI01FF83FEI0FF8003FE0FFN07JF8,00FFCI0FFE1FF001FFI01FF83FEI0FF8003FE0FF8M0FFDFFC,00FFE001FFE1FF001FF8003FF83FEI0FF8003FE0FFCI06I0FFCFFC,007FF003FFE1FF001FFE007FF83FEI0FF8003FE07FE001F801FF87FE,007FFC0IFE1FFI0IF81IF83FEI0FF8003FE07FF803FE03FF03FF,003MFE1FFI07MF83FEI0FF8003FE03MF07FE03FF,001MFE1FFI07MF83FEI0FF8003FE01MF07FE01FF8,001MFE1FFI03MF83FEI0FF8003FE00LFE0FFC00FFC,I0MFE1FFI01MF83FEI0FF8003FE007KFC1FF800FFE,I03JFBFE1FFJ0JFE7F83FEI0FF8003FE003KF81FF8007FE,I01IFE3FE1FFJ03IFC7F83FEI0FF8003FE001JFE03FFI03FF,J07FFC3FE1FFJ01IF07F83FEI0FF8003FEI07IF807FEI01FF8,J01FER03FCgH0FFC,^FS");

        // 바코드
        zplBuilder.append("^FO295,30^BY3^BCN,110,Y,N,N^FD34067165414^FS");

        // 텍스트 필드
        zplBuilder.append("^FO30,90^A0N,20,20^FDOrigin:^FS");
        zplBuilder.append("^FO35,120^ABN,40,27^FD").append(origin).append("^FS");
        zplBuilder.append("^FO30,190^A0N,20,20^FDDestination:^FS");
        zplBuilder.append("^FO35,215^ABN,40,27^FD").append(destination).append("^FS");
        zplBuilder.append("^FO170,190^A0N,20,20^FDDate:").append(date).append("^FS");
        zplBuilder.append("^FO170,220^A0N,20,20^FDForeign Ref: ").append(foreignRef).append("^FS");
        zplBuilder.append("^FO170,250^A0N,20,20^FDRef1:^FS");
        zplBuilder.append("^FO35,285^ABN,40,27^FD").append(productGroup).append("^FS");
        zplBuilder.append("^FO175,285^ABN,40,27^FD").append(productType).append("^FS");
        zplBuilder.append("^FO315,285^ABN,40,27^FD").append(payment).append("^FS");
        zplBuilder.append("^FO30,400^A0N,25,25^FDWeight: ").append(weight).append("     Chargeable: ").append(chargeable).append("^FS");
        zplBuilder.append("^FO30,435^A0N,25,25^FDServices:^FS");
        
        
        zplBuilder.append("^FO30,470^A0N,25,25^FDPieces: ").append(pieces).append("^FS");
        zplBuilder.append("^FO30,505^A0N,25,25^FD").append(description).append("^FS");
        zplBuilder.append("^FO30,550^A0N,25,25^FDAccount:").append(account).append("^FS");
        zplBuilder.append("^FO30,585^A0N,25,25^FD").append(hypercape1).append("^FS");
        zplBuilder.append("^FO30,620^A0N,25,25^FD").append(hypercape2).append("^FS");
        zplBuilder.append("^FO30,655^A0N,25,25^FD").append(address).append("^FS");
        zplBuilder.append("^FO742,655^A0N,25,25^FD").append(country).append("^FS");
        
        

        zplBuilder.append("^XZ\n");

        return zplBuilder.toString();
        
	}
	

}

