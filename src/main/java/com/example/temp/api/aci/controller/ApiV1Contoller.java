package com.example.temp.api.aci.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.aci.service.ApiV1Service;
import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.shipStation.ShipStationAPI;
import com.google.gson.Gson;

@RestController
public class ApiV1Contoller {
	
	@Autowired
	ApiV1Service apiV1Service;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ShipStationAPI shipStationApi;
	
	@Autowired
	EmsApi emsApi;
	
	@RequestMapping(value="/api/v1/area",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public String packageArea(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader) throws Exception{
		JSONObject area = new JSONObject();
		LinkedHashMap<String,Object> data = new LinkedHashMap<String,Object>();
		ArrayList<LinkedHashMap<String,Object>> areaValue = new ArrayList<LinkedHashMap<String,Object>>();
		areaValue = apiV1Service.selectUserArea(jsonHeader.get("userid").toString());
		data.put("data", areaValue);
		Gson gson = new Gson();
		String jsonString = gson.toJson(data);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/nomal/value/{orgStation}/{dstnNaion}",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public String checkNomalValue(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader) throws Exception{
		JSONObject area = new JSONObject();
		LinkedHashMap<String,Object> data = new LinkedHashMap<String,Object>();
		ArrayList<LinkedHashMap<String,Object>> areaValue = new ArrayList<LinkedHashMap<String,Object>>();
		areaValue = apiV1Service.selectUserArea(jsonHeader.get("userid").toString());
		data.put("data", areaValue);
		Gson gson = new Gson();
		String jsonString = gson.toJson(data);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/orderRegist",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String orderRegist(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.insertOrderInsp(jsonHeader, jsonData,request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		JSONArray jsonArr = new JSONArray();
		for (Map<String, Object> dataOne : jsonData) {
			JSONObject jsonObj = new JSONObject(dataOne);
			jsonArr.put(jsonObj);
		}
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		apiParams.put("header", jsonHeader.toString());
		apiParams.put("request", jsonArr.toString());
		apiParams.put("response", jsonString);
		apiParams.put("userId", jsonHeader.get("userid").toString());
		apiParams.put("userIp", request.getRemoteAddr());
		apiV1Service.insertApiConnChk(apiParams);
		
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/orderEtc",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String orderEtc(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.insertOrderEtc(jsonHeader, jsonData,request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/orderTakeRegist",method=RequestMethod.POST , produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderTakeRegist(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.insertOrderTakeIn(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/orderNomalRegist",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderNomalRegist(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.insertOrderNomal(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/orderModify",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String orderModify(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.modifyOrderInsp(jsonHeader, jsonData,request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/orderTakeModify",method=RequestMethod.POST , produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderTakeModify(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.modifyOrderTakeIn(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/orderNomalModify",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderNomalModify(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.modifyOrderNomal(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/orderDelete",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderDelete(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.deleteOrder(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/orderUpdate",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> orderUpdate(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.updateOrderInsp(jsonHeader, jsonData,request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/whOut",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> whout_api(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.stockOutStatus(jsonHeader, (String)jsonData.get("Date"), request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/whIn",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>> whin_api(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.stockInStatus(jsonHeader, (String)jsonData.get("Date"), request,userKey);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/pod",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> pod_api(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		
	  	ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	Map<String, Object> bl = new HashMap<String, Object>();

	  	String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
	  	rtnJsonArray = apiV1Service.blPod(jsonHeader, jsonData, request,userKey);
	  	
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/fedexApiOrderIn",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> fedexApiOrderIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
	  	ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	apiV1Service.sendFedexApi(request,response, "");
		return rtnJsonArray;
	}

	@RequestMapping(value="/api/v1/shopify",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<String> shopifyOrderListGet(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		ArrayList<ApiShopifyInfoVO> storeUrlList = apiV1Service.selectStoreUrl(orgStation);
		ArrayList<String> resultString = new ArrayList<String> ();
	  	
		for(int i = 0 ; i < storeUrlList.size(); i++) {
			resultString.add(apiV1Service.shopifyOrderListGet(storeUrlList.get(i),request, response));
		}
		return resultString;
	}
	
	@RequestMapping(value="/api/v1/shopifyFedUpdateUsps",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<String> shopifyOrderListUspsUpdate(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		ArrayList<ApiShopifyInfoVO> storeUrlList = apiV1Service.selectStoreUrl(orgStation);
		ArrayList<String> resultString = new ArrayList<String> ();
	  	
		for(int i = 0 ; i < storeUrlList.size(); i++) {
			resultString.add(apiV1Service.shopifyOrderListFulfilUpdateUSPS(storeUrlList.get(i),request, response));
		}
		return resultString;
	}
	
	@RequestMapping(value="/api/v1/shopifyFedUpdate",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<String> shopifyOrderListFedUpdate(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		ArrayList<ApiShopifyInfoVO> storeUrlList = apiV1Service.selectStoreUrl(orgStation);
		ArrayList<String> resultString = new ArrayList<String> ();
	  	
		for(int i = 0 ; i < storeUrlList.size(); i++) {
			resultString.add(apiV1Service.shopifyOrderListFulfilUpdate(storeUrlList.get(i),request, response));
		}
		return resultString;
	}
	
	@RequestMapping(value="/api/v1/checkShopify",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public void shopifyOrderListGetTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		shipStationApi.createShipment("202108170043165256BDB");
	}

	@RequestMapping(value="/api/v1/orderRegistItemCode",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String orderRegistItemCode(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.insertResgtItemCode(jsonHeader, jsonData,request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/mawbLookUp",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public String mawbLookUp(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.mawbLookUp(jsonHeader, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/hawbLookUpInMawb",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String hawbLookUpInMawb(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>> ();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.hawbLookUpInMawb(jsonHeader, jsonData, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/hawbLookUp",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String hawbLookUp(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>>  rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>> ();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.hawbLookUp(jsonHeader, jsonData, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}	
	
	@RequestMapping(value="/api/v1/takeinStock",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> takeinStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<Object, Object> parameterInfo = new  HashMap<Object,Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
	  	ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	rtnJsonArray = apiV1Service.takeinStock(parameterInfo); 
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/userStock",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> userStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<Object, Object> parameterInfo = new  HashMap<Object,Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("itemDetail", request.getParameter("itemDetail"));

	  	ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	rtnJsonArray = apiV1Service.userStock(parameterInfo); 

		return rtnJsonArray;
	}
		
	@RequestMapping(value="/api/v1/inspInStock",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> inspInStock(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model) throws Exception{	
	LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();
		
	  	//String apiUserId = jsonHeader.get("userId").toString();	  	

	  	/*
		JSONArray orderArray = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		*/
	  	
		/*api Chke Service*/
	  	/*
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader,jsonData);
		if(apiValueCheck.get("Status").equals("FAIL")) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			return mainRst;
		}
		*/

	  	String apiUserId = jsonHeader.get("userid").toString();
		String userKey = apiV1Service.selectUserKey(apiUserId);
		
		String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
		String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");
		

		if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "L10");
			mainRst.put("msg","ACI KEY IS NOT MATCHING");
			return mainRst;
		}

		
		/*
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String toStringDateTime =  dateFormat.format(dateTime);
		
		String getDateTime = apiKeyDecyptList[0].toLowerCase();
		String getUserId = apiKeyDecyptList[1].toLowerCase();
	    Date toDateGetDateTime = dateFormat.parse(getDateTime); 
		
	    System.out.println(toStringDateTime);
	    String apiKeyEncrypt = AES256Cipher.AES_Encode(toStringDateTime+"|"+apiUserId, userKey);
	    System.out.println(apiKeyEncrypt);
	
	    long curDateTime = dateTime.getTime() ;
	    long toTimeDateTime =toDateGetDateTime.getTime();
	    long minute = (curDateTime - toTimeDateTime ) / 60000;
	        */
	   
	    /*
	    if(Math.abs(minute) > 5) {
	    	mainRst.put("status", "FAIL");
			mainRst.put("code", "L20");
			mainRst.put("msg","apiKey 유효 시간 경과");
			return mainRst;
	    }
	    */
 
		LinkedHashMap<String, Object> pageInfo = new LinkedHashMap<String, Object>();

		HashMap<Object, Object> parameterInfo = new  HashMap<Object,Object>();
		parameterInfo.put("userId", apiUserId);
		parameterInfo.put("stationName", request.getParameter("Departure_Station"));
		parameterInfo.put("orderNo", request.getParameter("Order_No"));
		parameterInfo.put("fromDate", request.getParameter("From_Date"));
		parameterInfo.put("toDate", request.getParameter("To_Date"));
		parameterInfo.put("outYn", request.getParameter("Out_Yn"));
		parameterInfo.put("whStatus", request.getParameter("Whin_Status"));
		

		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	ArrayList<LinkedHashMap<String, Object>> inspStock = new ArrayList<LinkedHashMap<String, Object>>();

		int curPage = 1;
        if(StringUtil.isNumeric( request.getParameter("page") )) {
        	curPage = Integer.parseInt((String)request.getParameter("page"));
        }
        
        
        /*
		String toDate = "";
		if(request.getParameter("toDate") == null||request.getParameter("toDate") == ""){
			toDate = request.getParameter("fromDate");
			parameterInfo.put("toDate", toDate);
		}
		*/

  
		int totalCount = apiV1Service.inspStockTotalCnt(parameterInfo);

		if(totalCount == 0) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "F20");
			mainRst.put("msg","조회 된 결과가 없습니다.");
			return mainRst;
		}

		PagingVO paging = new PagingVO(curPage,totalCount,10,300);

	  	
	  	if(paging.getPagingEnd() < curPage) {
	  		mainRst.put("status", "FAIL");
			mainRst.put("code", "F30");
			mainRst.put("msg","해당 페이지 번호는 존재 하지 않습니다. 1 ~ "+paging.getPagingEnd());
			return mainRst;
	  	}
	  	
	  	parameterInfo.put("paging",paging);
		paging.setTotalCount(totalCount);
		pageInfo.put("Total_Count",paging.getTotalCount());
		pageInfo.put("Pivot_Board_Count",paging.getPivotBoardCount());
		pageInfo.put("Paging_End",paging.getPagingEnd());
		pageInfo.put("Current_Page",paging.getCurrentPage());
		
		mainRst.put("Page_Info", pageInfo);
	  	inspStock = apiV1Service.inspStock(parameterInfo);
	  	
	  	
	  	for(int index = 0 ; index < inspStock.size(); index++) {
		
	  		LinkedHashMap<String, Object> inspStockOrderInfo = new LinkedHashMap<String, Object>();
	  		inspStockOrderInfo.put("Departure_Station", inspStock.get(index).get("station"));
	  		inspStockOrderInfo.put("Order_Date", inspStock.get(index).get("orderDate"));
	  		inspStockOrderInfo.put("Order_No", inspStock.get(index).get("orderNo"));
	  		inspStockOrderInfo.put("Shipper_Name", inspStock.get(index).get("shipperName"));
	  		inspStockOrderInfo.put("Min_Whin_Date", inspStock.get(index).get("minWhInDate"));
	  		inspStockOrderInfo.put("Max_Whin_Date", inspStock.get(index).get("maxWhInDate"));
	  		inspStockOrderInfo.put("Total_Wo_Status_Cnt", inspStock.get(index).get("Total_Wo_Status_Cnt"));
	  		inspStockOrderInfo.put("Total_Wf_Status_Cnt", inspStock.get(index).get("Total_Wf_Status_Cnt"));
	  		inspStockOrderInfo.put("Shipping_Date", inspStock.get(index).get("Shipping_Date"));
	  		inspStockOrderInfo.put("BL_No", inspStock.get(index).get("BL_No"));
	  		inspStockOrderInfo.put("Actual_Weight", inspStock.get(index).get("Actual_Weight"));
	  		inspStockOrderInfo.put("Volume_Weight", inspStock.get(index).get("Volume_Weight"));
	  		inspStockOrderInfo.put("Shipping_Fee", inspStock.get(index).get("Shipping_Fee"));
	  		
	  		rtnJsonArray.add(index,inspStockOrderInfo);
	  		HashMap<Object, Object> stockParameter = new  HashMap<Object,Object>();
	  		
	  		stockParameter.put("nno", inspStock.get(index).get("nno"));
	  		stockParameter.put("userId",inspStock.get(index).get("userId"));
	  		stockParameter.put("orgStation",inspStock.get(index).get("orgStation"));
	  		stockParameter.put("whStatus", request.getParameter("whStatus"));
	  		stockParameter.put("outYn", request.getParameter("outYn"));
	  		stockParameter.put("orderNo", inspStock.get(index).get("orderNo"));

	  		ArrayList<LinkedHashMap<String, Object>> inspGroupStockDetail = new ArrayList<LinkedHashMap<String, Object>>();
	  		inspGroupStockDetail = apiV1Service.inspStockGroupDetail(stockParameter);	 
	  		
	  		/*
	  		ArrayList<LinkedHashMap<String, Object>> itemDetail = new ArrayList<LinkedHashMap<String, Object>>();
	  		ArrayList<LinkedHashMap<String, Object>> addItemDetail = new ArrayList<LinkedHashMap<String, Object>>();
	  		*/

	  		for(int index2 = 0 ; index2 < inspGroupStockDetail.size(); index2++) {
	  			//inspGroupStockDetail.get(index2).get(key);
	  			HashMap<Object, Object> stockParameter2 = new  HashMap<Object,Object>();
	  			stockParameter2.put("nno", inspStock.get(index).get("nno"));
	  			stockParameter2.put("subNo", inspGroupStockDetail.get(index2).get("SUB_NO"));
	  			stockParameter2.put("userId",inspStock.get(index).get("userId"));
	  			stockParameter2.put("groupIdx",inspStock.get(index).get("Group_Stock_No"));
	  			stockParameter2.put("orgStation",inspStock.get(index).get("orgStation"));
	  			stockParameter2.put("whStatus", request.getParameter("whStatus"));
	  			stockParameter2.put("outYn", request.getParameter("outYn"));
	  			
//	  			ArrayList<LinkedHashMap<String, Object>> itemList = new ArrayList<LinkedHashMap<String, Object>>();
//	  			itemList.get(index2).put("Wo_Status_Cnt", inspGroupStockDetail.get(index2).get("Wo_Status_Cnt"));
//	  			itemList.get(index2).put("Wf_Status_Cnt", inspGroupStockDetail.get(index2).get("Wf_Status_Cnt"));
//	  			itemList.get(index2).put("Whin_Memo", inspGroupStockDetail.get(index2).get("Whin_Memo"));
//	  			itemList.get(index2).put("Customer_Item_Code", inspGroupStockDetail.get(index2).get("Customer_Item_Code"));

		  		ArrayList<LinkedHashMap<String, Object>> inspStockDetail = new ArrayList<LinkedHashMap<String, Object>>();
		  		ArrayList<LinkedHashMap<String, Object>> inspStockAddDetail = new ArrayList<LinkedHashMap<String, Object>>();
		  		ArrayList<LinkedHashMap<String, Object>> inspGroupImgUrl = new ArrayList<LinkedHashMap<String, Object>>();
		  		
	  			stockParameter2.put("groupIdx",inspGroupStockDetail.get(index2).get("Group_Stock_No"));
	  			inspStockDetail = apiV1Service.inspStockDetail(stockParameter2);
	  			inspStockAddDetail = apiV1Service.inspStockAddDetail(stockParameter2);
	  			
		  		inspGroupStockDetail.get(index2).put("ItemDetail", inspStockDetail);
		  		inspGroupStockDetail.get(index2).put("AddItemDetail", inspStockAddDetail);

		  		inspGroupImgUrl = apiV1Service.inspStockGroupImg(stockParameter2);
		  		inspGroupStockDetail.get(index2).put("ItemImgUrl", inspGroupImgUrl);
		  		//inspGroupImgMsg = apiV1Service.inspStockGroupMsg(stockParameter2);
		  		//inspGroupStockDetail.get(index2).put("ItemMsg", inspGroupImgMsg);
		  		
		  		inspGroupStockDetail.get(index2).remove("Group_Stock_No");
		  		inspGroupStockDetail.get(index2).remove("NNO");
		  		inspGroupStockDetail.get(index2).remove("SUB_NO");		  		
	  		}

	  		ArrayList<LinkedHashMap<String, Object>> orderMsg = new ArrayList<LinkedHashMap<String, Object>>();
	  		orderMsg = apiV1Service.selectOrderMsg(stockParameter);
	  		
	  		rtnJsonArray.get(index).put("ItemList", inspGroupStockDetail);
	  		rtnJsonArray.get(index).put("OrderMsg", orderMsg);
	  	}
		mainRst.put("stockList", rtnJsonArray);
		return mainRst;
	}

	@RequestMapping(value="/api/v1/takeInItemReg",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String takeInItemReg(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.takeInItemReg(jsonHeader, jsonData, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/takeInLookUpItem",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String takeInLookUpItem(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.takeInItemReg(jsonHeader, jsonData, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/holdBLRestart",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String holdBlRestart(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
		rtnJsonArray = apiV1Service.holdBLRestart(jsonHeader, jsonData, request,userKey);
		Gson gson = new Gson();
		String jsonString = gson.toJson(rtnJsonArray);
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/inspInOrderStock",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> inspInOrderStock(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model) throws Exception{	
	
		Thread.sleep(3000); //1초 대기
		LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();
	  	//String apiUserId = jsonHeader.get("userId").toString();	  	

	  	/*
		JSONArray orderArray = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		*/
	  	
		/*api Chke Service*/
	  	/*
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader,jsonData);
		if(apiValueCheck.get("Status").equals("FAIL")) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			return mainRst;
		}
		*/

	  	String apiUserId = jsonHeader.get("userid").toString();
		String userKey = apiV1Service.selectUserKey(apiUserId);
		
		String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
		String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");
		

		if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "L10");
			mainRst.put("msg","ACI KEY IS NOT MATCHING");
			return mainRst;
		}
		/*
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String toStringDateTime =  dateFormat.format(dateTime);
		
		String getDateTime = apiKeyDecyptList[0].toLowerCase();
		String getUserId = apiKeyDecyptList[1].toLowerCase();
	    Date toDateGetDateTime = dateFormat.parse(getDateTime); 
		
	    System.out.println(toStringDateTime);
	    String apiKeyEncrypt = AES256Cipher.AES_Encode(toStringDateTime+"|"+apiUserId, userKey);
	    System.out.println(apiKeyEncrypt);
	
	    long curDateTime = dateTime.getTime() ;
	    long toTimeDateTime =toDateGetDateTime.getTime();
	    long minute = (curDateTime - toTimeDateTime ) / 60000;
	        */
	   
	    /*
	    if(Math.abs(minute) > 5) {
	    	mainRst.put("status", "FAIL");
			mainRst.put("code", "L20");
			mainRst.put("msg","apiKey 유효 시간 경과");
			return mainRst;
	    }
	    */
		LinkedHashMap<String, Object> pageInfo = new LinkedHashMap<String, Object>();

		HashMap<Object, Object> parameterInfo = new  HashMap<Object,Object>();
		parameterInfo.put("userId", apiUserId);
		parameterInfo.put("stationName", request.getParameter("Departure_Station"));
		parameterInfo.put("orderNo", request.getParameter("Order_No"));
		parameterInfo.put("fromDate", request.getParameter("From_Date"));
		parameterInfo.put("toDate", request.getParameter("To_Date"));
		parameterInfo.put("outYn", request.getParameter("Out_Yn"));
		parameterInfo.put("whStatus", request.getParameter("Whin_Status"));
		
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	ArrayList<LinkedHashMap<String, Object>> inspStock = new ArrayList<LinkedHashMap<String, Object>>();

		int curPage = 1;
        if(StringUtil.isNumeric( request.getParameter("page") )) {
        	curPage = Integer.parseInt((String)request.getParameter("page"));
        }

		String toDate = "";
		if(request.getParameter("toDate") == null||request.getParameter("toDate") == ""){
			toDate = request.getParameter("fromDate");
			parameterInfo.put("toDate", toDate);
		}


		int totalCount = apiV1Service.inspStocNewTotalCnt(parameterInfo);

		if(totalCount == 0) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "F20");
			mainRst.put("msg","조회 된 결과가 없습니다.");
			return mainRst;
		}

		PagingVO paging = new PagingVO(curPage,totalCount,10,300);
	  	
	  	if(paging.getPagingEnd() < curPage) {
	  		mainRst.put("status", "FAIL");
			mainRst.put("code", "F30");
			mainRst.put("msg","해당 페이지 번호는 존재 하지 않습니다. 1 ~ "+paging.getPagingEnd());
			return mainRst;
	  	}
	  	
	  	parameterInfo.put("paging",paging);
		paging.setTotalCount(totalCount);
		pageInfo.put("Total_Count",paging.getTotalCount());
		pageInfo.put("Pivot_Board_Count",paging.getPivotBoardCount());
		pageInfo.put("Paging_End",paging.getPagingEnd());
		pageInfo.put("Current_Page",paging.getCurrentPage());
		
		mainRst.put("Page_Info", pageInfo);
	  	inspStock = apiV1Service.inspStockNew(parameterInfo);

	  	for(int index = 0 ; index < inspStock.size(); index++) {
		
	  		LinkedHashMap<String, Object> inspStockOrderInfo = new LinkedHashMap<String, Object>();
	  		inspStockOrderInfo.put("Departure_Station", inspStock.get(index).get("station"));
	  		inspStockOrderInfo.put("Order_Date", inspStock.get(index).get("orderDate"));
	  		inspStockOrderInfo.put("Order_No", inspStock.get(index).get("orderNo"));
	  		inspStockOrderInfo.put("Shipper_Name", inspStock.get(index).get("shipperName"));
	  		inspStockOrderInfo.put("Min_Whin_Date", inspStock.get(index).get("minWhInDate"));
	  		inspStockOrderInfo.put("Max_Whin_Date", inspStock.get(index).get("maxWhInDate"));
	  		inspStockOrderInfo.put("Total_Wo_Status_Cnt", inspStock.get(index).get("OUT_DATE"));
	  		inspStockOrderInfo.put("Total_Wf_Status_Cnt", inspStock.get(index).get("Total_Wf_Status_Cnt"));
	  		inspStockOrderInfo.put("Shipping_Date", inspStock.get(index).get("OUT_DATE"));
	  		inspStockOrderInfo.put("BL_No", inspStock.get(index).get("OUT_BL"));
	  		
	  		rtnJsonArray.add(index,inspStockOrderInfo);
	  		
	  		HashMap<Object, Object> stockParameter = new  HashMap<Object,Object>();
	  		
	  		stockParameter.put("nno", inspStock.get(index).get("nno"));
	  		stockParameter.put("subNo", inspStock.get(index).get("subNo"));
	  		stockParameter.put("userId",inspStock.get(index).get("userId"));
	  		stockParameter.put("orgStation",inspStock.get(index).get("orgStation"));
	  		stockParameter.put("whStatus", request.getParameter("whStatus"));
	  		stockParameter.put("outYn", request.getParameter("outYn"));
	  		
	  		
	  		ArrayList<LinkedHashMap<String, Object>> ItemList = new ArrayList<LinkedHashMap<String, Object>>();
	  		ItemList = apiV1Service.inspStockItemList(stockParameter);

	  		for(int index2 = 0 ; index2 < ItemList.size(); index2++) {
	  			ArrayList<LinkedHashMap<String, Object>> stockList = new ArrayList<LinkedHashMap<String, Object>>();
	  			ArrayList<LinkedHashMap<String, Object>> stockAddList = new ArrayList<LinkedHashMap<String, Object>>();
	  			ArrayList<LinkedHashMap<String, Object>> stockImgList = new ArrayList<LinkedHashMap<String, Object>>();

	  			HashMap<Object, Object> stockParameter2 = new  HashMap<Object,Object>();
	  			stockParameter2.put("nno", inspStock.get(index).get("nno"));
	  			stockParameter2.put("subNo", ItemList.get(index2).get("SUB_NO"));
	  			stockParameter2.put("userId",inspStock.get(index).get("userId"));
	  			stockParameter2.put("orgStation",inspStock.get(index).get("orgStation"));
	  			
	  			stockList =  apiV1Service.inspStockList(stockParameter2);
	  			stockAddList = apiV1Service.inspStockAddList(stockParameter2);
	  			stockImgList  = apiV1Service.inspStockImgList(stockParameter2);

	  			ItemList.get(index2).put("itemDetail", stockList);
	  			ItemList.get(index2).put("AddedItemDetail", stockAddList);
	  			ItemList.get(index2).put("ItemImgUrl", stockImgList);
	  			
	  			ItemList.get(index2).remove("NNO");
	  			ItemList.get(index2).remove("SUB_NO");

	  		}
	  		
	  		ArrayList<LinkedHashMap<String, Object>> orderMsg = new ArrayList<LinkedHashMap<String, Object>>();
	  		orderMsg = apiV1Service.inspStockMsgList(stockParameter);
	  		rtnJsonArray.get(index).put("ItemList", ItemList);
	  		rtnJsonArray.get(index).put("OrderMsg", orderMsg);

	  	}

		mainRst.put("stockList", rtnJsonArray);

		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/inspInUnknown",method=RequestMethod.GET, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> inspInUnknown(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model) throws Exception{	

		Thread.sleep(3000); //1초 대기
		LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();

	  	String apiUserId = jsonHeader.get("userid").toString();
		String userKey = apiV1Service.selectUserKey(apiUserId);
		
		String apiKeyDecrypt = AES256Cipher.AES_Decode((String)jsonHeader.get("apikey"), userKey);
		String[] apiKeyDecyptList = apiKeyDecrypt.split("[|]");
		

		if(!apiKeyDecyptList[1].toLowerCase().equals(apiUserId.toLowerCase())) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "L10");
			mainRst.put("msg","ACI KEY IS NOT MATCHING");
			return mainRst;
		}
		
		/*
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String toStringDateTime =  dateFormat.format(dateTime);
		
		String getDateTime = apiKeyDecyptList[0].toLowerCase();
		String getUserId = apiKeyDecyptList[1].toLowerCase();
	    Date toDateGetDateTime = dateFormat.parse(getDateTime); 
		
	    System.out.println(toStringDateTime);
	    String apiKeyEncrypt = AES256Cipher.AES_Encode(toStringDateTime+"|"+apiUserId, userKey);
	    System.out.println(apiKeyEncrypt);
	
	    long curDateTime = dateTime.getTime() ;
	    long toTimeDateTime =toDateGetDateTime.getTime();
	    long minute = (curDateTime - toTimeDateTime ) / 60000;
	        */
	   
	    /*
	    if(Math.abs(minute) > 5) {
	    	mainRst.put("status", "FAIL");
			mainRst.put("code", "L20");
			mainRst.put("msg","apiKey 유효 시간 경과");
			return mainRst;
	    }
	    */
		
		
		LinkedHashMap<String, Object> pageInfo = new LinkedHashMap<String, Object>();

		HashMap<Object, Object> parameterInfo = new  HashMap<Object,Object>();
		parameterInfo.put("userId", apiUserId);
		parameterInfo.put("stationName", request.getParameter("Departure_Station"));
		parameterInfo.put("trkNo", request.getParameter("Trk_No"));
		
		
		
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
	  	ArrayList<LinkedHashMap<String, Object>> inspStock = new ArrayList<LinkedHashMap<String, Object>>();

		int curPage = 1;
        if(StringUtil.isNumeric( request.getParameter("page") )) {
        	curPage = Integer.parseInt((String)request.getParameter("page"));
        }

		String toDate = "";
		if(request.getParameter("toDate") == null||request.getParameter("toDate") == ""){
			toDate = request.getParameter("fromDate");
			parameterInfo.put("toDate", toDate);
		}


		int totalCount = apiV1Service.inspInUnknownCnt(parameterInfo);

		if(totalCount == 0) {
			mainRst.put("status", "FAIL");
			mainRst.put("code", "F20");
			mainRst.put("msg","조회 된 결과가 없습니다.");
			return mainRst;
		}

		PagingVO paging = new PagingVO(curPage,totalCount,10,300);

	  	if(paging.getPagingEnd() < curPage) {
	  		mainRst.put("status", "FAIL");
			mainRst.put("code", "F30");
			mainRst.put("msg","해당 페이지 번호는 존재 하지 않습니다. 1 ~ "+paging.getPagingEnd());
			return mainRst;
	  	}
	  	
	  	parameterInfo.put("paging",paging);
		paging.setTotalCount(totalCount);
		pageInfo.put("Total_Count",paging.getTotalCount());
		pageInfo.put("Pivot_Board_Count",paging.getPivotBoardCount());
		pageInfo.put("Paging_End",paging.getPagingEnd());
		pageInfo.put("Current_Page",paging.getCurrentPage());
		
		mainRst.put("Page_Info", pageInfo);
		

	  	inspStock = apiV1Service.inspInUnknownList(parameterInfo);

	  	ArrayList<LinkedHashMap<String, Object>> stockImgList = new ArrayList<LinkedHashMap<String, Object>>();
	  	for(int index = 0 ; index < inspStock.size(); index++) {

	  		
	  		LinkedHashMap<String, Object> inspInUnknownInfo = new LinkedHashMap<String, Object>();
	  		inspInUnknownInfo.put("Departure_Station",inspStock.get(index).get("station"));
	  		inspInUnknownInfo.put("WhIn_Date",inspStock.get(index).get("whInDate"));
	  		inspInUnknownInfo.put("Trking_Company",inspStock.get(index).get("trkCom"));
	  		inspInUnknownInfo.put("Trking_Number",inspStock.get(index).get("trkNo"));
	  		inspInUnknownInfo.put("Stock_No",inspStock.get(index).get("stockNo"));
	  		inspInUnknownInfo.put("Whin_Memo",inspStock.get(index).get("whMeme"));

	  		HashMap<Object, Object> stockParameter2 = new  HashMap<Object,Object>();
  			stockParameter2.put("groupIdx", inspStock.get(index).get("groupIdx"));
  			stockParameter2.put("userId",inspStock.get(index).get("userId"));
  			stockParameter2.put("orgStation",inspStock.get(index).get("orgStation"));
  			
  			stockImgList  = apiV1Service.inspInUnknownImg(stockParameter2);
  			inspInUnknownInfo.put("ItemImgUrl", stockImgList);

	  		rtnJsonArray.add(index,inspInUnknownInfo);
	  	}

		mainRst.put("stockList", rtnJsonArray);


		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/messageTest",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public String messageTest(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception{
//		LinkedHashMap<String, Object> rtnJson = new LinkedHashMap<String, Object>();
//		String userKey = apiV1Service.selectUserKey((String) jsonHeader.get("userid"));
//		rtnJson = apiV1Service.lookUpMessage(jsonHeader, jsonData, request,userKey);
//		Gson gson = new Gson();
		String jsonString ="{\r\n" + 
				"    \"Status\": \"SUCCESS\",\r\n" + 
				"    \"Status_Code\": \"10\",\r\n" + 
				"    \"Status_Msg\": \"\",\r\n" + 
				"    \"Order_No\": \"5fef5558bf9097d85d0f1c3a\",\r\n" + 
				"    \"Group_Stock_No\": \"\",\r\n" + 
				"    \"Msg\": [\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"test\",\r\n" + 
				"            \"wDate\": \"2021-02-18 15:13:30\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"123123123123\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:38:54\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"123123123123\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:38:55\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"관리자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"관리자 테스트\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:38:55\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"123123123123\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:38:56\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"123123123123\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:38:57\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"TESTSET11\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:48:51\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"TESTSET11\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:48:52\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"TESTSET11\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:48:53\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"User_Div\": \"사용자\",\r\n" + 
				"            \"wUserId\": \"crokettest\",\r\n" + 
				"            \"whMeme\": \"TESTSET11\",\r\n" + 
				"            \"wDate\": \"2021-02-19 11:48:54\"\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";
		return jsonString;
	}
	
	@RequestMapping(value="/api/v1/requestReturnList",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> requestReturnList(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object> jsonData) throws Exception{
		LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();

		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		if(apiValueCheck.get("Status").equals("FAIL")) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			return mainRst;
		}
		
	  	String userId = jsonHeader.get("userid").toString();
	  	JSONObject jObject = new JSONObject(jsonData);
	  	
		String userKey = apiV1Service.selectUserKey(userId);
	  	
	  	String station = jObject.get("Departure_Station").toString();
	  	String fromDate = jObject.get("From_Date").toString();
	  	String toDate = jObject.get("To_Date").toString();
	  	String returnNo = jObject.get("Return_No").toString();
	  	
	
	  	
	  	HashMap<String, Object>  parameters = new HashMap<String, Object>();
	  	parameters.put("station", station);
	  	parameters.put("userId", userId);
	  	parameters.put("fromDate", fromDate);
	  	parameters.put("toDate", toDate);
	  	parameters.put("returnNo", returnNo);

	
		int curPage = 1;
		
	  	String Page = jObject.get("Page").toString();
        if(StringUtil.isNumeric( Page )) {
        	curPage = Integer.parseInt(Page);
        }
        
		int totalCount = apiV1Service.requestReturnListCnt(parameters);

		if(totalCount == 0) {
			mainRst.put("Status", "FAIL");
			mainRst.put("Status_Code", "D10");
			mainRst.put("Status_Msg","조회 된 결과가 없습니다.");
			return mainRst;
		}

		PagingVO paging = new PagingVO(curPage,totalCount,100,300);

	  	if(paging.getPagingEnd() < curPage) {
	  		mainRst.put("Status", "FAIL");
			mainRst.put("Status_Code", "D20");
			mainRst.put("Status_Msg","해당 페이지 번호는 존재 하지 않습니다. 1 ~ "+paging.getPagingEnd());
			return mainRst;
	  	}
	  	
	  	paging.setTotalCount(totalCount);
	  	
		mainRst.put("Status", "SUCCESS");
		mainRst.put("Status_Code", "A10");
		mainRst.put("Status_Msg","");
	  	mainRst.put("Total_Count",paging.getTotalCount());
	  	mainRst.put("Pivot_Board_Count",paging.getPivotBoardCount());
	  	mainRst.put("Paging_End",paging.getPagingEnd());
	  	mainRst.put("Current_Page",paging.getCurrentPage());
	  	mainRst.put("Total_Count",paging.getTotalCount());
	  	mainRst.put("Pivot_Board_Count",paging.getPivotBoardCount());
	  	mainRst.put("Paging_End",paging.getPagingEnd());
	  	mainRst.put("Current_Page",paging.getCurrentPage());
		

		parameters.put("paging",paging);
		ArrayList<LinkedHashMap<String, Object>> List = new ArrayList<LinkedHashMap<String, Object>>();
		List = apiV1Service.requestReturnList(parameters);
		
		for(int index =0 ; index < List.size() ; index++) {
			
			 SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
			 List.get(index).put("Receiver_Address",AES256Cipher.AES_Decode(List.get(index).get("Receiver_Address").toString(),SecurityKeyVO.getSymmetryKey()));
			 List.get(index).put("Receiver_Address_Detail",AES256Cipher.AES_Decode(List.get(index).get("Receiver_Address_Detail").toString(),SecurityKeyVO.getSymmetryKey()));
			 List.get(index).put("Receiver_Tel",AES256Cipher.AES_Decode(List.get(index).get("Receiver_Tel").toString(),SecurityKeyVO.getSymmetryKey()));
			 List.get(index).put("Receiver_Hp",AES256Cipher.AES_Decode(List.get(index).get("Receiver_Hp").toString(),SecurityKeyVO.getSymmetryKey()));
			 List.get(index).put("Receiver_Email",AES256Cipher.AES_Decode(List.get(index).get("Receiver_Email").toString(),SecurityKeyVO.getSymmetryKey()));
			 List.get(index).put("Receiver_Address",AES256Cipher.AES_Encode(List.get(index).get("Receiver_Address").toString(),userKey));
			 List.get(index).put("Receiver_Address_Detail",AES256Cipher.AES_Encode(List.get(index).get("Receiver_Address_Detail").toString(),userKey));
			 List.get(index).put("Receiver_Tel",AES256Cipher.AES_Encode(List.get(index).get("Receiver_Tel").toString(),userKey));
			 List.get(index).put("Receiver_Hp",AES256Cipher.AES_Encode(List.get(index).get("Receiver_Hp").toString(),userKey));
			 List.get(index).put("Receiver_Email",AES256Cipher.AES_Encode(List.get(index).get("Receiver_Email").toString(),userKey));
		}

		mainRst.put("List", List);

		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/requestReturn",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> requestReturn(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object>[] jsonData) throws Exception{

		ArrayList<LinkedHashMap<String, Object>> mainRst = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> errorRst = new LinkedHashMap<String, Object>();
	  	String apiUserId = jsonHeader.get("userid").toString();	  	
		String userKey = apiV1Service.selectUserKey(apiUserId);
		
		JSONArray orderArray = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		

		String gApiNno = "";
		gApiNno = apiV1Service.getNno();
		
		
		HashMap<String, Object>  parameters = new HashMap<String, Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", orderArray.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("nno", gApiNno);
		apiV1Service.insertApiConn(parameters);
		

		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		errorRst.put("Return_No","");
		if(apiValueCheck.get("Status").equals("FAIL")) {
			errorRst.put("Status", apiValueCheck.get("Status"));
			errorRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			errorRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			mainRst.add(errorRst);
			return mainRst;
		}


		for(int index = 0 ; index < orderArray.length() ; index++) {
			jObject = orderArray.getJSONObject(index);
			LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();
			
			rst.put("Status", "");
			rst.put("Status_Code", "");
			rst.put("Status_Msg", "");
			rst.put("Return_No", "");		
			
			//rst.put("Status", "SUCESS");
			
			String rstStatus = "SUCESS";
			String rstCode = "";
			String rstMsg = "";
			String station = jObject.getString("Departure_Station");
			String returnNo = jObject.getString("Return_No");
			String returnType =jObject.getString("Return_Type");
			String cneeName = jObject.getString("Receiver_Name");
			String cneeState = jObject.getString("Receiver_State");
			String cneeDistrict = jObject.getString("Receiver_District");
			String cneeZip = jObject.getString("Receiver_Zip");
			String cneeAddr =jObject.getString("Receiver_Address");
			String cneeAddrDetail = jObject.getString("Receiver_Address_Detail");
			String cneeTel = jObject.getString("Receiver_Tel");
			String cneeHp = jObject.getString("Receiver_Hp");
			String cneeEmail = jObject.getString("Receiver_Email");
			String returnReason = jObject.getString("Return_Reason");
			String trkNo = jObject.getString("Request_Delivery_Company");
			String trkCom = jObject.getString("Request_BL");
			String trkNoBl = jObject.getString("Request_BL_ImgUrl");

			/*
			cneeAddr = "";  
			cneeAddrDetail = "";
			cneeTel = "";
			cneeHp = "";
			cneeEmail = "";
			*/
			
			/*
			cneeAddr = AES256Cipher.AES_Encode(cneeAddr,userKey);
			cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail,userKey);
			cneeTel = AES256Cipher.AES_Encode(cneeTel,userKey);
			cneeHp = AES256Cipher.AES_Encode(cneeHp,userKey);
			cneeEmail = AES256Cipher.AES_Encode(cneeEmail,userKey);
			*/
			
			/*
			cneeAddr = AES256Cipher.AES_Decode(cneeAddr,userKey);
			cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail,userKey);
			cneeTel = AES256Cipher.AES_Decode(cneeTel,userKey);
			cneeHp = AES256Cipher.AES_Decode(cneeHp,userKey);
			cneeEmail = AES256Cipher.AES_Decode(cneeEmail,userKey);
			*/
			
			if(cneeName.equals("")||cneeName==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "CneeName Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}
			
			if(cneeAddr.equals("")||cneeAddr==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "CneeAddr Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}
			
			String chkTel = "";
			chkTel = cneeTel;
			if(cneeTel.equals("")||cneeTel ==null) {
				chkTel = cneeHp;
			}
			
			if(chkTel.equals("")||chkTel ==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "cneeTel , cneeHp Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}

				try {
					if(!cneeAddr.equals("")||cneeAddr==null) {
						cneeAddr = AES256Cipher.AES_Decode(cneeAddr,userKey);
				
					}
					if(!cneeAddrDetail.equals("")||cneeAddrDetail==null) {
						cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail,userKey);
					}
					if(!cneeAddr.equals("")||cneeAddr==null) {
						cneeTel = AES256Cipher.AES_Decode(cneeTel,userKey);
					}
					if(!cneeHp.equals("")||cneeHp==null) {
						cneeHp = AES256Cipher.AES_Decode(cneeHp,userKey);
					}
					if(!cneeEmail.equals("")||cneeEmail==null) {
						cneeEmail = AES256Cipher.AES_Decode(cneeEmail,userKey);
					}
				} catch (Exception e) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "L40");
					rst.put("Status_Msg", e.getMessage());
					rst.put("Return_No", returnNo);				
					mainRst.add(rst);
					return mainRst;
				}
			
			SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
			
			if(!cneeAddr.equals("")||cneeAddr==null) {
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr,SecurityKeyVO.getSymmetryKey());
		
			}
			if(!cneeAddrDetail.equals("")||cneeAddrDetail==null) {
				cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail,SecurityKeyVO.getSymmetryKey());
			}
			if(!cneeTel.equals("")||cneeTel==null) {
				cneeTel = AES256Cipher.AES_Encode(cneeTel,SecurityKeyVO.getSymmetryKey());
			}
			if(!cneeHp.equals("")||cneeHp==null) {
				cneeHp = AES256Cipher.AES_Encode(cneeHp,SecurityKeyVO.getSymmetryKey());
			}
			if(!cneeEmail.equals("")||cneeEmail==null) {
				cneeEmail = AES256Cipher.AES_Encode(cneeEmail,SecurityKeyVO.getSymmetryKey());
			}


			HashMap<String, Object> orderInfo = new HashMap<String, Object>();
			orderInfo.put("userId", apiUserId);
			orderInfo.put("station",station);
			orderInfo.put("orgStation",station);
			orderInfo.put("returnNo",returnNo);
			orderInfo.put("returnType",returnType);
			orderInfo.put("returnReason",returnReason);
			orderInfo.put("trkCom",trkCom);
			orderInfo.put("trkNo",trkNo);
			orderInfo.put("trkNoBl",trkNoBl);
			orderInfo.put("cneeName", cneeName);
			orderInfo.put("cneeState",cneeState);
			orderInfo.put("cneeDistrict",cneeDistrict);
			orderInfo.put("cneeZip",cneeZip);
			orderInfo.put("cneeAddr",cneeAddr);
			orderInfo.put("cneeAddrDetail",cneeAddrDetail);
			orderInfo.put("cneeTel",cneeTel);
			orderInfo.put("cneeHp",cneeHp);
			orderInfo.put("cneeEmail",cneeEmail);
			orderInfo.put("wUserId",apiUserId);
			orderInfo.put("wUserIp",request.getRemoteAddr());
			
			if(returnNo.equals("")||returnNo == null) { 
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "Return_No Not a valid input value.";
				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}

			
			if(!returnType.equals("")||returnType == null) {
				HashMap<String,Object> rstRetunType =  new HashMap<String,Object>();
				rstRetunType.put("code", returnType);
				
				rstRetunType = apiV1Service.selectReturnType(rstRetunType);
				
				String status = "";
				status = (String) rstRetunType.get("status");

				if(status.equals("FAIL")) {
					rstStatus = "FAIL";
					rstCode = "P10";
					rstMsg = "Return_Type Not a valid input value."+rstRetunType.get("statusMsg");
					
					rst.put("Status", rstStatus);
					rst.put("Status_Code", rstCode);
					rst.put("Status_Msg", rstMsg);
					rst.put("Return_No", returnNo);				
					mainRst.add(rst);
					continue;
				}
			}
	
			HashMap<String, Object> returnNoChkRst = new HashMap<String, Object>();
			returnNoChkRst = apiV1Service.returnNoChk(orderInfo);
			
			int chkCnt = (int)returnNoChkRst.get("chkCnt");
			if(chkCnt != 0) {
				rstStatus = "FAIL";
				rstCode = "D20";
				rstMsg = "Already registed data";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				

				mainRst.add(rst);
				continue;
			}

			if(station.equals("")||station == null) { 
				rstMsg = "Departure_Station Not a valid input value.";
				rst.put("Status","FAIL");
				rst.put("Status_Code","P10");
				rst.put("Status_Msg",rstMsg);
				rst.put("Return_No",returnNo);
				mainRst.add(rst);
				continue;
			}
	
			if(!station.equals("")||station == null) { 
				HashMap<String, Object> prarmeterInfo = new HashMap<String, Object>();
				prarmeterInfo.put("stationName", station);
				
				HashMap<String, Object> stationChkRst = new HashMap<String, Object>();
				stationChkRst = apiV1Service.stationCheck(prarmeterInfo);

				if(stationChkRst.get("stationCode").equals("")||stationChkRst.get("stationCode")==null) {
					rstMsg = "Departure_Station Not a valid input value.";
					rst.put("Status","FAIL");
					rst.put("Status_Code","P10");
					rst.put("Status_Msg",rstMsg);
					rst.put("Return_No",returnNo);
					mainRst.add(rst);
					continue;
				}
			}
			
			
			if(station.equals("")||station == null) { 
				rstStatus = "FAIL";
				rstCode = "F10";
				rstMsg = "Departure_Station Not a valid input value.";
				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);
				rst.put("Return_Type",returnType);
				mainRst.add(rst);
				continue;
			}
			
			
			if(orderArray.getJSONObject(index).getJSONArray("stockList").length() == 0){
				rstStatus = "FAIL";
				rstCode = "P20";
				rstMsg = "StockNo Not a valid input value.";
				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);
				rst.put("Return_Type",returnType);
				mainRst.add(rst);
				continue;
				
			}
		
			ArrayList<HashMap<String, Object>> rstStock = new  ArrayList<HashMap<String, Object>>();
			for (int index2 = 0 ; index2 < orderArray.getJSONObject(index).getJSONArray("stockList").length();index2++) {
				String stockNo = orderArray.getJSONObject(index).getJSONArray("stockList").getJSONObject(index2).getString("stockNo");
				HashMap<String, Object> rstStockNo = new  HashMap<String, Object>();
				rstStockNo.put("stockNo", stockNo);
				rstStockNo.put("orgStation",station);
				rstStockNo.put("userId",apiUserId);
	
				
				HashMap<String, Object> rstStockNoChk = new  HashMap<String, Object>();
				rstStockNoChk = apiV1Service.spStockChk(rstStockNo);
				if(rstStockNoChk.get("rstStatus").equals("FAIL")) {
					rst.put("Status",rstStockNoChk.get("rstStatus"));
					rst.put("Status_Code", "P20");
					rst.put("Status_Msg", (String)rstStockNoChk.get("rstMsg") + (String)rstStockNoChk.get("rstStockNo"));
					rst.put("Return_No", returnNo);					
					break;
				}else {
					HashMap<String, Object> StockNo = new  HashMap<String, Object>();
					StockNo.put("stockNo", stockNo);
					rstStock.add(StockNo);
				}
			};
			
			if(rst.get("Status").equals("FAIL")) {
				mainRst.add(rst);
				continue;
			}

			int dupleCnt = 0 ; 
			for(int stockIndex=0 ; stockIndex < rstStock.size(); stockIndex++) {
				String orgStock =  (String)rstStock.get(stockIndex).get("stockNo");

				int dupleChk = 0 ;
				for(int stockIndex2=0 ; stockIndex2 < rstStock.size(); stockIndex2++) {
					if(orgStock.equals((String)rstStock.get(stockIndex2).get("stockNo"))){
						dupleChk++;
					}
					if(dupleChk > 1) {
						dupleCnt++;
					}
				}
				if(dupleCnt > 0) {
					rst.put("Status","FAIL");
					rst.put("Status_Code", "P20");
					rst.put("Status_Msg", "중복 된 재고번호가 존재 합니다.");
					rst.put("Return_No", returnNo);					
					break;
				}
			}
			if(rst.get("Status").equals("FAIL")) {
				mainRst.add(rst);
				continue;
			}
	

			String gNno = "";
			gNno = apiV1Service.getNno();
			orderInfo.put("nno", gNno);
			apiV1Service.returnListInsert(orderInfo);

		
			for (int index2 = 0 ; index2 < orderArray.getJSONObject(index).getJSONArray("stockList").length();index2++) {
				String stockNo = orderArray.getJSONObject(index).getJSONArray("stockList").getJSONObject(index2).getString("stockNo");
				orderInfo.put("stockNo", stockNo);
				apiV1Service.returnItemInsert(orderInfo);
			};

			rst.put("Status", "SUCCESS");
			rst.put("Status_Code", "A10");
			rst.put("Status_Msg", "정상 처리 되었습니다.");
			rst.put("Return_No", returnNo);
			rst.put("Return_Type", returnType);
			mainRst.add(rst);
		}
		
		parameters.put("rtnContents", mainRst.toString());
		apiV1Service.updateApiConn(parameters);
		
		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/requestReturnDel",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> requestReturnDel(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> mainRst = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> errorRst = new LinkedHashMap<String, Object>();
	  	String apiUserId = jsonHeader.get("userid").toString();	  	
		
		JSONArray orderArray = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		
		String gApiNno = "";
		gApiNno = apiV1Service.getNno();
		
		HashMap<String, Object>  parameters = new HashMap<String, Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", orderArray.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("nno", gApiNno);
		apiV1Service.insertApiConn(parameters);
		
		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		errorRst.put("Return_No","");
		if(apiValueCheck.get("Status").equals("FAIL")) {
			errorRst.put("Status", apiValueCheck.get("Status"));
			errorRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			errorRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			mainRst.add(errorRst);
			return mainRst;
		}
		
		for(int index = 0 ; index < orderArray.length() ; index++) {
			jObject = orderArray.getJSONObject(index);
			String station = jObject.getString("Departure_Station");
			String returnNo = jObject.getString("Return_No");
			
			LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();
			HashMap<String, String>  returnDelparameters = new HashMap<String, String>();
			returnDelparameters.put("station",station);
			returnDelparameters.put("userId",apiUserId);
			returnDelparameters.put("returnNo",returnNo);
			returnDelparameters.put("wUserId",apiUserId);
			returnDelparameters.put("wUserIp",request.getRemoteAddr());		

			HashMap<String, String>  spRst = new HashMap<String, String>();
			spRst = apiV1Service.spReqReturnDel(returnDelparameters);
			
			if(spRst.get("rstStatus").equals("SUCCESS")) {
				rst.put("Status", "SUCCESS");
				rst.put("Status_Code", "A10");
				rst.put("Status_Msg", "정상처리 되었습니다.");
				rst.put("Retrun_No", returnNo);
			}else {
				if(spRst.get("rstCode").equals("F10")) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "D10");
					rst.put("Status_Msg", "The data does not exist.");
					rst.put("Retrun_No", returnNo);
				}
				
				if(spRst.get("rstCode").equals("F20")) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "P10");
					rst.put("Status_Msg", "Already shipped Data");
					rst.put("Retrun_No", returnNo);
				}
			}
			mainRst.add(rst);
		}
		
		parameters.put("rtnContents", mainRst.toString());
		apiV1Service.updateApiConn(parameters);
		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/requestReturnModify",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> requestReturnModify(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> mainRst = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> errorRst = new LinkedHashMap<String, Object>();
	  	String apiUserId = jsonHeader.get("userid").toString();	  	
		String userKey = apiV1Service.selectUserKey(apiUserId);
		
		JSONArray orderArray = new JSONArray(jsonData);
		JSONObject jObject = new JSONObject();
		
		String gApiNno = "";
		gApiNno = apiV1Service.getNno();
		
		HashMap<String, Object>  parameters = new HashMap<String, Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", orderArray.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("nno", gApiNno);
		apiV1Service.insertApiConn(parameters);
		
		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		errorRst.put("Return_No","");
		if(apiValueCheck.get("Status").equals("FAIL")) {
			errorRst.put("Status", apiValueCheck.get("Status"));
			errorRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			errorRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			mainRst.add(errorRst);
			return mainRst;
		}

		for(int index = 0 ; index < orderArray.length() ; index++) {
			
			String rstStatus = "SUCESS";
			String rstCode = "";
			String rstMsg = "";
			String station = jObject.getString("Departure_Station");
			String returnNo = jObject.getString("Return_No");
			String returnType =jObject.getString("Return_Type");
			String cneeName = jObject.getString("Receiver_Name");
			String cneeState = jObject.getString("Receiver_State");
			String cneeDistrict = jObject.getString("Receiver_District");
			String cneeZip = jObject.getString("Receiver_Zip");
			String cneeAddr =jObject.getString("Receiver_Address");
			String cneeAddrDetail = jObject.getString("Receiver_Address_Detail");
			String cneeTel = jObject.getString("Receiver_Tel");
			String cneeHp = jObject.getString("Receiver_Hp");
			String cneeEmail = jObject.getString("Receiver_Email");
			String returnReason = jObject.getString("Return_Reason");
			String trkNo = jObject.getString("Request_Delivery_Company");
			String trkCom = jObject.getString("Request_BL");
			String trkNoBl = jObject.getString("Request_BL_ImgUrl");
			
			LinkedHashMap<String, Object> rst = new LinkedHashMap<String, Object>();
			HashMap<String, String>  returnDelparameters = new HashMap<String, String>();
			returnDelparameters.put("station",station);
			returnDelparameters.put("userId",apiUserId);
			returnDelparameters.put("returnNo",returnNo);
			returnDelparameters.put("wUserId",apiUserId);
			returnDelparameters.put("wUserIp",request.getRemoteAddr());		

			
			if(returnNo.equals("")||returnNo == null) { 
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "Return_No Not a valid input value.";
				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}

			if(cneeName.equals("")||cneeName==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "CneeName Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}
			
			if(cneeAddr.equals("")||cneeAddr==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "CneeAddr Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}
			
			String chkTel = "";
			chkTel = cneeTel;
			if(cneeTel.equals("")||cneeTel ==null) {
				chkTel = cneeHp;
			}
			
			if(chkTel.equals("")||chkTel ==null) {
				rstStatus = "FAIL";
				rstCode = "P10";
				rstMsg = "cneeTel , cneeHp Not a valid input value.";

				rst.put("Status", rstStatus);
				rst.put("Status_Code", rstCode);
				rst.put("Status_Msg", rstMsg);
				rst.put("Return_No", returnNo);				
				mainRst.add(rst);
				continue;
			}

				try {
					if(!cneeAddr.equals("")||cneeAddr==null) {
						cneeAddr = AES256Cipher.AES_Decode(cneeAddr,userKey);
				
					}
					if(!cneeAddrDetail.equals("")||cneeAddrDetail==null) {
						cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail,userKey);
					}
					if(!cneeAddr.equals("")||cneeAddr==null) {
						cneeTel = AES256Cipher.AES_Decode(cneeTel,userKey);
					}
					if(!cneeHp.equals("")||cneeHp==null) {
						cneeHp = AES256Cipher.AES_Decode(cneeHp,userKey);
					}
					if(!cneeEmail.equals("")||cneeEmail==null) {
						cneeEmail = AES256Cipher.AES_Decode(cneeEmail,userKey);
					}
				} catch (Exception e) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "L40");
					rst.put("Status_Msg", e.getMessage());
					rst.put("Return_No", returnNo);				
					mainRst.add(rst);
					return mainRst;
				}
			
			SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
			
			if(!cneeAddr.equals("")||cneeAddr==null) {
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr,SecurityKeyVO.getSymmetryKey());
		
			}
			if(!cneeAddrDetail.equals("")||cneeAddrDetail==null) {
				cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail,SecurityKeyVO.getSymmetryKey());
			}
			if(!cneeTel.equals("")||cneeTel==null) {
				cneeTel = AES256Cipher.AES_Encode(cneeTel,SecurityKeyVO.getSymmetryKey());
			}
			if(!cneeHp.equals("")||cneeHp==null) {
				cneeHp = AES256Cipher.AES_Decode(cneeHp,userKey);
			}
			if(!cneeEmail.equals("")||cneeEmail==null) {
				cneeEmail = AES256Cipher.AES_Encode(cneeEmail,SecurityKeyVO.getSymmetryKey());
			}

			HashMap<String, Object> orderInfo = new HashMap<String, Object>();
			orderInfo.put("userId", apiUserId);
			orderInfo.put("station",station);
			orderInfo.put("orgStation",station);
			orderInfo.put("returnNo",returnNo);
			orderInfo.put("returnType",returnType);
			orderInfo.put("returnReason",returnReason);
			orderInfo.put("trkCom",trkCom);
			orderInfo.put("trkNo",trkNo);
			orderInfo.put("trkNoBl",trkNoBl);
			orderInfo.put("cneeName", cneeName);
			orderInfo.put("cneeState",cneeState);
			orderInfo.put("cneeDistrict",cneeDistrict);
			orderInfo.put("cneeZip",cneeZip);
			orderInfo.put("cneeAddr",cneeAddr);
			orderInfo.put("cneeAddrDetail",cneeAddrDetail);
			orderInfo.put("cneeTel",cneeTel);
			orderInfo.put("cneeHp",cneeHp);
			orderInfo.put("cneeEmail",cneeEmail);
			orderInfo.put("wUserId",apiUserId);
			orderInfo.put("wUserIp",request.getRemoteAddr());
			
			ArrayList<HashMap<String, Object>> rstStock = new  ArrayList<HashMap<String, Object>>();
			for (int index2 = 0 ; index2 < orderArray.getJSONObject(index).getJSONArray("stockList").length();index2++) {
				String stockNo = orderArray.getJSONObject(index).getJSONArray("stockList").getJSONObject(index2).getString("stockNo");
				HashMap<String, Object> rstStockNo = new  HashMap<String, Object>();
				rstStockNo.put("stockNo", stockNo);
				rstStockNo.put("orgStation",station);
				rstStockNo.put("userId",apiUserId);
	
				
				HashMap<String, Object> rstStockNoChk = new  HashMap<String, Object>();
				rstStockNoChk = apiV1Service.spStockChk(rstStockNo);
				if(rstStockNoChk.get("rstStatus").equals("FAIL")) {
					rst.put("Status",rstStockNoChk.get("rstStatus"));
					rst.put("Status_Code", "P20");
					rst.put("Status_Msg", (String)rstStockNoChk.get("rstMsg") + (String)rstStockNoChk.get("rstStockNo"));
					rst.put("Return_No", returnNo);					
					break;
				}else {
					HashMap<String, Object> StockNo = new  HashMap<String, Object>();
					StockNo.put("stockNo", stockNo);
					rstStock.add(StockNo);
				}
			};
			
			if(rst.get("Status").equals("FAIL")) {
				mainRst.add(rst);
				continue;
			}

			int dupleCnt = 0 ; 
			for(int stockIndex=0 ; stockIndex < rstStock.size(); stockIndex++) {
				String orgStock =  (String)rstStock.get(stockIndex).get("stockNo");

				int dupleChk = 0 ;
				for(int stockIndex2=0 ; stockIndex2 < rstStock.size(); stockIndex2++) {
					if(orgStock.equals((String)rstStock.get(stockIndex2).get("stockNo"))){
						dupleChk++;
					}
					if(dupleChk > 1) {
						dupleCnt++;
					}
				}
				if(dupleCnt > 0) {
					rst.put("Status","FAIL");
					rst.put("Status_Code", "P20");
					rst.put("Status_Msg", "중복 된 재고번호가 존재 합니다.");
					rst.put("Return_No", returnNo);					
					break;
				}
			}
			if(rst.get("Status").equals("FAIL")) {
				mainRst.add(rst);
				continue;
			}
			
			HashMap<String, String>  spRst = new HashMap<String, String>();
			spRst = apiV1Service.spReqReturnDel(returnDelparameters);
			
			if(spRst.get("rstStatus").equals("SUCCESS")) {
				rst.put("Status", "SUCCESS");
				rst.put("Status_Code", "A10");
				rst.put("Status_Msg", "정상처리 되었습니다.");
				rst.put("Retrun_No", returnNo);
			}else {
				if(spRst.get("rstCode").equals("F10")) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "D10");
					rst.put("Status_Msg", "The data does not exist.");
					rst.put("Retrun_No", returnNo);
					continue;
				}
				
				if(spRst.get("rstCode").equals("F20")) {
					rst.put("Status", "FAIL");
					rst.put("Status_Code", "P10");
					rst.put("Status_Msg", "Already shipped Data");
					rst.put("Retrun_No", returnNo);
					continue;
				}
				continue;
			}

			String gNno = "";
			gNno = apiV1Service.getNno();
			orderInfo.put("nno", gNno);
			apiV1Service.returnListInsert(orderInfo);

		
			for (int index2 = 0 ; index2 < orderArray.getJSONObject(index).getJSONArray("stockList").length();index2++) {
				String stockNo = orderArray.getJSONObject(index).getJSONArray("stockList").getJSONObject(index2).getString("stockNo");
				orderInfo.put("stockNo", stockNo);
				apiV1Service.returnItemInsert(orderInfo);
			};

			rst.put("Status", "SUCCESS");
			rst.put("Status_Code", "A10");
			rst.put("Status_Msg", "정상 처리 되었습니다.");
			rst.put("Return_No", returnNo);
			rst.put("Return_Type", returnType);
			
			mainRst.add(rst);
		}
		
		parameters.put("rtnContents", mainRst.toString());
		apiV1Service.updateApiConn(parameters);

		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/requestReturnApprv",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public ArrayList<LinkedHashMap<String, Object>> requestReturnApprv(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object>[] jsonData) throws Exception{
		ArrayList<LinkedHashMap<String, Object>> mainRst = new ArrayList<LinkedHashMap<String, Object>>();

		JSONArray orderArray = new JSONArray(jsonData);
		
		LinkedHashMap<String, Object> apiErrorRst = new LinkedHashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		
		String gApiNno = "";
		gApiNno = apiV1Service.getNno();
		
		HashMap<String, Object>  parameters = new HashMap<String, Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", orderArray.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("nno", gApiNno);
		apiV1Service.insertApiConn(parameters);
	
		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		apiErrorRst.put("Return_No","");
		if(apiValueCheck.get("Status").equals("FAIL")) {
			apiErrorRst.put("Status", apiValueCheck.get("Status"));
			apiErrorRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			apiErrorRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			mainRst.add(apiErrorRst);
			return mainRst;
		}


		for(int index = 0 ; index < orderArray.length(); index++) {
			
			LinkedHashMap<String, Object> Rst = new LinkedHashMap<String, Object>();

			String orgStation =  orderArray.getJSONObject(index).get("Departure_Station").toString();
			String returnNo = orderArray.getJSONObject(index).get("Return_No").toString();
			String apprvYn = orderArray.getJSONObject(index).get("Apprv").toString();
			
			HashMap<String, Object> prameterInfo = new HashMap<String, Object>();
			prameterInfo.put("orgStation", orgStation);
			prameterInfo.put("returnNo", returnNo);
			prameterInfo.put("apprvYn", apprvYn);
			prameterInfo.put("userId", apiUserId);
			prameterInfo.put("wUserId", apiUserId);
			prameterInfo.put("wUserIp", request.getRemoteAddr());
			
			HashMap<String, Object> rst = new HashMap<String, Object>();
			rst = apiV1Service.spReturnApprv(prameterInfo);

			if(rst.get("rstStatus").equals("FAIL")) {
				Rst.put("Status", rst.get("rstStatus"));
				Rst.put("Status_Code",  rst.get("rstCode"));
				Rst.put("Status_Msg", rst.get("rstMsg"));
				Rst.put("Retrun_No",returnNo);
				mainRst.add(Rst);
			}else {
				Rst.put("Status", rst.get("rstStatus"));
				Rst.put("Status_Code",  rst.get("rstCode"));
				Rst.put("Status_Msg", rst.get("rstMsg"));
				Rst.put("Retrun_No",returnNo);
				mainRst.add(Rst);
			}
			
		}

		parameters.put("rtnContents", mainRst.toString());
		apiV1Service.updateApiConn(parameters);

		return mainRst;
	}

	@RequestMapping(value="/api/v1/message",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> apiMessage(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object> jsonData) throws Exception{
		LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();

		JSONObject json = new JSONObject(jsonData); 
		LinkedHashMap<String, Object> apiErrorRst = new LinkedHashMap<String, Object>();
		String apiUserId = jsonHeader.get("userid").toString();
		
		String gApiNno = "";
		gApiNno = apiV1Service.getNno();
		
		HashMap<String, Object>  parameters = new HashMap<String, Object>();
		parameters.put("jsonHeader", jsonHeader.toString());
		parameters.put("jsonData", json.toString());
		parameters.put("wUserId",apiUserId);
		parameters.put("wUserIp",request.getRemoteAddr());
		parameters.put("connUrl",request.getServletPath());
		parameters.put("nno", gApiNno);
		apiV1Service.insertApiConn(parameters);
	
		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		apiErrorRst.put("Return_No","");
		if(apiValueCheck.get("Status").equals("FAIL")) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			return mainRst;
		}

		String orgStation = json.get("Departure_Station").toString();
		String orderNo = json.get("Order_No").toString();
		String groupIdx = json.get("Group_Stock_No").toString();
		String whMsg  =  json.get("Msg").toString();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", apiUserId);
		parameterInfo.put("orderNo", orderNo);
		parameterInfo.put("groupIdx", groupIdx);
		parameterInfo.put("whMemo",whMsg);
		parameterInfo.put("wUserId",apiUserId);
		parameterInfo.put("wUserIp",request.getRemoteAddr());
		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = apiV1Service.spStockMsg(parameterInfo);
		
		if(!rst.get("rstStatus").equals("SUCCESS")) {
			mainRst.put("Status", rst.get("rstStatus"));
			mainRst.put("Status_Code", rst.get("rstCode"));
			mainRst.put("Status_Msg", rst.get("rstMsg"));
			mainRst.put("Order_No", orderNo);
			mainRst.put("Group_Stock_No", groupIdx);
		}else {
			mainRst.put("Status", rst.get("rstStatus"));
			mainRst.put("Status_Code", rst.get("rstCode"));
			mainRst.put("Status_Msg", rst.get("rstMsg"));
			mainRst.put("Order_No", orderNo);
			mainRst.put("Group_Stock_No", groupIdx);
		}
	
		parameters.put("rtnContents", mainRst.toString());
		apiV1Service.updateApiConn(parameters);

		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/messageList",method=RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> apiMessageList(HttpServletRequest request,@RequestHeader Map<String,Object> jsonHeader, HttpServletResponse response, Model model, @RequestBody Map<String,Object> jsonData) throws Exception{
		LinkedHashMap<String, Object> mainRst = new LinkedHashMap<String, Object>();
		
		/*api Chke Service*/
		HashMap<String, Object> apiValueCheck = new HashMap<String, Object>();
		apiValueCheck = apiV1Service.apiValueCheck(request,jsonHeader);
		if(apiValueCheck.get("Status").equals("FAIL")) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
			return mainRst;
		}

		JSONObject json = new JSONObject(jsonData); 
		
		String apiUserId = jsonHeader.get("userid").toString();		
		String orgStation = json.get("Departure_Station").toString();
		String orderNo = json.get("Order_No").toString();
		String groupIdx = json.get("Group_Stock_No").toString();

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", apiUserId);
		parameterInfo.put("orderNo", orderNo);
		parameterInfo.put("groupIdx", groupIdx);

		ArrayList<LinkedHashMap<String, Object>> rstMsg = new ArrayList<LinkedHashMap<String, Object>>();
		if(rstMsg.size() == 0) {
			mainRst.put("Status", apiValueCheck.get("Status"));
			mainRst.put("Status_Code", apiValueCheck.get("Status_Code"));
			mainRst.put("Status_Msg", apiValueCheck.get("Status_Msg"));
		}
		
		rstMsg = apiV1Service.seletMsg(parameterInfo);
		mainRst.put("Order_No", orderNo);
		mainRst.put("Group_Stock_No", groupIdx);
		mainRst.put("Msg", rstMsg);

		return mainRst;
	}
	
	@RequestMapping(value="/api/v1/instrument",method=RequestMethod.GET , produces = "application/json; charset=utf8")
	public Map<String, Object>  instrumentGet(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader) throws Exception{
//		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		
		Map<String, Object>  rtnJsonArray = new HashMap<String, Object>();
		rtnJsonArray = apiV1Service.instrumentGet(jsonHeader, request);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/instrument",method=RequestMethod.POST , produces = "application/json; charset=utf8")
	public Map<String, Object>  instrumentPost(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception{
//		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		
		Map<String, Object>  rtnJsonArray = new HashMap<String, Object>();
		rtnJsonArray = apiV1Service.instrumentPost(jsonHeader, jsonData, request);
		return rtnJsonArray;
	}
	
	@RequestMapping(value="/api/v1/instrumentList",method=RequestMethod.POST , produces = "application/json; charset=utf8")
	public ArrayList<Map<String, Object>>  instrumentListPost(HttpServletRequest request, HttpServletResponse response,@RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object>[] jsonData) throws Exception{
//		ArrayList<Map<String, Object>> rtnJsonArray = new ArrayList<Map<String, Object>>();
		
		ArrayList<Map<String, Object>>  rtnJsonArray = new ArrayList<Map<String, Object>>();
		rtnJsonArray = apiV1Service.instrumentListPost(jsonHeader, jsonData, request);
		return rtnJsonArray;
	}

	@RequestMapping(value = "/api/v1/whin", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> whinPost(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String,Object> jsonData) throws Exception {
		
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		rtnJsonArray = apiV1Service.whinPost(jsonHeader, jsonData, request);
		return rtnJsonArray;
	}
	
	@RequestMapping(value = "/api/v1/blPrintMulti", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> blPrint(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String,Object> jsonHeader, @RequestBody Map<String, Object> jsonData, Model model) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		try {
			rstMap = apiV1Service.selectBlPrint(jsonHeader, jsonData, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	
	@RequestMapping(value = "/api/v1/blPrint", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> blPrint2(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonData) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		rstMap = apiV1Service.selectBlPrintSingle(jsonHeader, jsonData, request, response);
		
		return rstMap;
	}
	
	@RequestMapping(value = "/api/v1/getBlList", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public LinkedHashMap<String, Object> getBlList(HttpServletRequest request, HttpServletResponse response, @RequestHeader Map<String, Object> jsonHeader) throws Exception {
		LinkedHashMap<String, Object> rstMap = new LinkedHashMap<String, Object>();
		
		rstMap = apiV1Service.selectBlList(jsonHeader, request, response);
		
		return rstMap;
	}



}
