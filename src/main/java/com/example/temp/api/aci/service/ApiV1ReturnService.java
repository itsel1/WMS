package com.example.temp.api.aci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.vo.CustomerVO;



@Service
public interface ApiV1ReturnService {
	public HashMap<String, Object> insertOrderRequestData(HashMap<String, Object> errors) throws Exception;
	
	public HashMap<String, Object> setOrderRequestVal( Map<String,Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception;
	
	public HashMap<String, Object> updateOrderRequestData(HashMap<String, Object> errors) throws Exception;

	public void insertApiconn(HashMap<String, Object> errors, Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request) throws Exception;
	
	public HashMap<String, Object> getEshopItem(Map<String, Object> jsonHeader,  String eshipId, String blNo, HttpServletRequest request) throws Exception;
	
	public HashMap<String,Object> s3FileInsert(String imgNameVal, String types, String userId, String nno, String fileVal, String fileExten) throws Exception;
	
	public ArrayList<HashMap<String,String>> selectJsonField(String apiName, String method, String section) throws Exception;

	public void insertReturnRequest(ReturnRequestVO rtnVal) throws Exception;

	public void insertReturnRequestItem(ReturnRequestItemVO rtnItemVal) throws Exception;

	public HashMap<String, Object> selectReturnSttatus(HttpServletRequest request, @RequestHeader Map<String,Object> jsonHeader, String parameters, String valueKinds) throws Exception;

	public HashMap<String, Object> acceptReturnRequest(String string, String parameters, String sellerId, Map<String, Object> jsonHeader, HttpServletRequest request) throws Exception;
	
	public HashMap<String,Object> checkApiOrderInfo(Map<String,Object> jsonHeader, HttpServletRequest request) throws Exception;

	public int selectSellerIdChk(String sellerId);

	public HashMap<String, Object> selectSellerIdChkAprv(String sellerId);

	public HashMap<String, Object> selectMsgHis(HashMap<String, String> parameters);

	public void insertMsgInfo(StockMsgVO msgInfo);

	public ArrayList<StockVO> selectStockByGrpIdx2(String groupIdx);

	public ArrayList<StockResultVO> selectStockResultVO2(HashMap<String, Object> map);

	public void updateMsgInfo(HashMap<String, Object> params);

	public ArrayList<StockMsgVO> selectMsg(HashMap<String, String> parameters);

	public int selectMsgCnt(HashMap<String, String> parameters);

	public void deleteMsgInfo(HashMap<String, Object> params);

	public int selectReturnStationAddrCnt(String stationName);

	public LinkedHashMap<String, Object> selectReturnStationAddr(String stationName);

	public ArrayList<CustomerVO> selectUserList();

	public HashMap<String, Object> selectEshopOrderInfo(HttpServletRequest request, String apiKey, String eshopId,
			String blNo);

	public HashMap<String, Object> selectEshopOrderItem(String blNo);

	public LinkedHashMap<String, Object> setApiRequestVal(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request);

	public HashMap<String, Object> setApiPodVal(HttpServletRequest request, @RequestHeader Map<String,Object> jsonHeader, String parameters, String valueKinds);

	public HashMap<String, Object> setApiPodValTest(HttpServletRequest request, Map<String, Object> jsonHeader,
			String parameters, String string);

	public HashMap<String, Object> setApiPodValTest2(HttpServletRequest request, Map<String, Object> jsonHeader,
			String parameters, String string);
	
}