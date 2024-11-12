package com.example.temp.api.aci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.smtp.ViewYslItemCode;

@Service
public interface ApiV1Service {
	
	public ArrayList<Map<String, Object>> insertOrderInsp(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;
	
	public ArrayList<Map<String, Object>> updateOrderInsp(Map<String,Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public String selectUserKey(String userId) throws Exception;

	public String selectUserAllowIp(String userID, String remoteAddr) throws Exception;

	public ArrayList<Map<String, Object>> stockOutStatus(Map<String, Object> jsonHeader, String date, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<Map<String, Object>> stockInStatus(Map<String, Object> jsonHeader, String string, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> blPod(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public void sendFedexApi(HttpServletRequest request, HttpServletResponse response, String nno) throws Exception;

	public ArrayList<ApiShopifyInfoVO> selectStoreUrl(String orgStation) throws Exception;

	public String shopifyOrderListGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	ArrayList<Map<String, Object>> insertOrderTakeIn(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<Map<String, Object>> insertOrderNomal(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> insertResgtItemCode(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;
	
	public void insertYslItemCode() throws Exception;

	public ArrayList<ViewYslItemCode> selectViewYslItem() throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> mawbLookUp(Map<String, Object> jsonHeader, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>>  hawbLookUpInMawb(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> takeinStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> userStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockDetail(HashMap<Object, Object> stockParameter);

	public HashMap<Object, Object> selectStationInfo(HashMap<Object, Object> parameterInfo);

	public int inspStockTotalCnt(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupDetail(HashMap<Object, Object> stockParameter);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupImg(HashMap<Object, Object> stockParameter);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupMsg(HashMap<Object, Object> stockParameter);

	public ArrayList<LinkedHashMap<String, Object>>  hawbLookUp(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> takeInItemReg(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;
	
	public ArrayList<LinkedHashMap<String, Object>> takeInloopUpDate(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> holdBLRestart(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public String shopifyOrderListGetTest(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ArrayList<Map<String, Object>> modifyOrderTakeIn(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<Map<String, Object>> modifyOrderInsp(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<Map<String, Object>> modifyOrderNomal(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<Map<String, Object>> deleteOrder(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public LinkedHashMap<String, Object> lookUpMessage(Map<String, Object> jsonHeader, Map<String, Object> jsonData, HttpServletRequest request, String userKey) throws Exception;

	public String getNno() throws Exception;

	public HashMap<String, Object> spStockChk(HashMap<String, Object> rstStockNo) throws Exception;

	public HashMap<String, Object> apiValueCheck(HttpServletRequest request, Map<String, Object> jsonHeader) throws Exception;

	public HashMap<String, Object> stationCheck(HashMap<String, Object> prarmeterInfo) throws Exception;

	public HashMap<String, Object> returnNoChk(HashMap<String, Object> returnNoChkRst) throws Exception;
	
	public void returnListInsert(HashMap<String, Object> orderInfo) throws Exception;

	public void returnItemInsert(HashMap<String, Object> orderInfo) throws Exception;

	public void insertApiConn(HashMap<String, Object> parameters) throws Exception;

	public void updateApiConn(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> selectReturnType(HashMap<String, Object> rstRetunType) throws Exception;

	public HashMap<String, String> spReqReturnDel(HashMap<String, String> parameters) throws Exception;

	public int requestReturnListCnt(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> requestReturnList(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> spReturnApprv(HashMap<String, Object> prameterInfo) throws Exception;

	public HashMap<String, Object> spStockMsg(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> seletMsg(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockNew(HashMap<Object, Object> parameterInfo) throws Exception;

	public int inspStocNewTotalCnt(HashMap<Object, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockItemList(HashMap<Object, Object> stockParameter) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockList(HashMap<Object, Object> stockParameter2) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockAddList(HashMap<Object, Object> stockParameter2) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockMsgList(HashMap<Object, Object> stockParameter2) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockImgList(HashMap<Object, Object> stockParameter2) throws Exception;

	public String shopifyOrderListCntGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ArrayList<Map<String, Object>> insertOrderEtc(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData, HttpServletRequest request, String userKey) throws Exception;

	public Map<String, Object> instrumentGet(Map<String, Object> jsonHeader, HttpServletRequest request) throws Exception;

	public Map<String, Object> instrumentPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData,
			HttpServletRequest request) throws Exception;

	public ArrayList<Map<String, Object>> instrumentListPost(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
			HttpServletRequest request) throws Exception;

	public void updateYslItemCode(String itemCode) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspStockAddDetail(HashMap<Object, Object> stockParameter2) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectOrderMsg(HashMap<Object, Object> stockParameter) throws Exception;

	public int inspInUnknownCnt(HashMap<Object, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownList(HashMap<Object, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownImg(HashMap<Object, Object> stockParameter2) throws Exception;

	public String shopifyOrderListFulfilUpdate(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String shopifyOrderListFulfilUpdateUSPS(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	ArrayList<LinkedHashMap<String, Object>> blPodtest(Map<String, Object> jsonHeader, Map<String, Object>[] jsonData,
			HttpServletRequest request, String userKey) throws Exception;

	public ArrayList<LinkedHashMap<String,Object>> selectUserArea(String userId) throws Exception;

	public LinkedHashMap<String, Object> whinPost(Map<String, Object> jsonHeader, Map<String, Object> jsonData,
			HttpServletRequest request) throws Exception;

	public void insertApiConnChk(HashMap<String, Object> apiParams) throws Exception;

	public LinkedHashMap<String, Object> selectBlPrint(Map<String, Object> jsonHeader, Map<String, Object> jsonData,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	public LinkedHashMap<String, Object> selectBlList(Map<String, Object> jsonHeader,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	public LinkedHashMap<String, Object> selectBlPrintSingle(Map<String, Object> jsonHeader,
			Map<String, Object> jsonData, HttpServletRequest request, HttpServletResponse response) throws Exception;


}

