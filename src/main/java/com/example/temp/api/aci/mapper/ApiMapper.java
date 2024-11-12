package com.example.temp.api.aci.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.InsterumentVO;
import com.example.temp.api.aci.vo.MsgBodyVO;
import com.example.temp.api.aci.vo.MsgHeaderVO;
import com.example.temp.api.aci.vo.ShipperInfo;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.smtp.ViewMatchingInfo;
import com.example.temp.smtp.ViewYslItemCode;
import com.example.temp.trans.yongsung.ApiOrderItemYSVO;
import com.example.temp.trans.yongsung.ApiOrderYSVO;

@Repository
@Mapper
public interface ApiMapper {
   
	public String selectUserKey(String userId) throws Exception;

	public void insertApiConn(HashMap<String, Object> parameters) throws Exception;

	public void updateApiConn(HashMap<String, Object> parameters) throws Exception;

	public void insertApiOrderList(ApiOrderListVO apiOrderList) throws Exception;

	public String selectUserAllowIp(@Param("userId")String userId, @Param("remoteIp")String remoteIp) throws Exception;

	public ApiOrderListVO selectApiShipperInfo(String userId) throws Exception;

	public int selectOrderInfo(ApiOrderListVO temp) throws Exception;

	public void updateApiOrderList(ApiOrderListVO temp) throws Exception;

	public void insertApiOrderItem(ApiOrderItemListVO apiOrderItemListVO) throws Exception;

	public void updateApiOrderItem(ApiOrderItemListVO apiOrderItemListVO) throws Exception;

	public void deleteApiOrderItem(String nno) throws Exception;

	public String selectApiOrderNNO(@Param("orderNo")String orderNo, @Param("userId")String userId) throws Exception;

	public ArrayList<Map<String, Object>> selectStockOutStatus(@Param("userId")String userId, @Param("depDate")String date) throws Exception;

	public ArrayList<Map<String, Object>> selectStockInStatus(@Param("userId")String userId, @Param("whInDate")String date) throws Exception;

	public String selectWdate(String hawbNo) throws Exception;

	public HashMap<String, Object> selectPodBlInfo(String hawbNo) throws Exception;

	public ArrayList<Map<String, Object>> selectItemWhoutInfo(@Param("userId")String userId, @Param("nno")String nno) throws Exception;

	public ArrayList<Map<String, Object>> selectStockInDetatil(@Param("userId")String userId, @Param("groupIdx")String groupIdx) throws Exception;

	public ArrayList<ApiOrderItemYSVO> selectItemInfoForYS(String nno) throws Exception;

	public ArrayList<ApiOrderYSVO> selectListInfoForYS(String nno) throws Exception;

	public ApiOrderListVO selectFedexInfo(HashMap<String, Object> tempParameter) throws Exception;

	public void insertUserOrderList(ApiOrderListVO apiOrderList) throws Exception;

	public void insertUserOrderItem(ApiOrderItemListVO apiOrderItemList) throws Exception;

	public void deleteApiOrderList(String nno)throws Exception;

	public ArrayList<String> selectOrgStationAll() throws Exception;

	public void insertExpBaseInfo(ExpLicenceVO licence) throws Exception;

	public int checkNation(HashMap<String, Object> parameters) throws Exception;

	public boolean cusItemCodeExist(HashMap<String, Object> parameters) throws Exception;

	public void insertYslItemCode() throws Exception;

	public ArrayList<ViewYslItemCode> selectViewYslItem() throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> takeinStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> userStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStock(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockDetail(HashMap<Object, Object> stockParameter);

	public HashMap<Object, Object> selectStationInfo(HashMap<Object, Object> parameterInfo);

	public int inspStockTotalCnt(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupDetail(HashMap<Object, Object> stockParameter);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupImg(HashMap<Object, Object> stockParameter);

	public ArrayList<LinkedHashMap<String, Object>> inspStockGroupMsg(HashMap<Object, Object> stockParameter);

	public LinkedHashMap<String, Object> insertUserTakeinInfo(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> execSpHoldBl(HashMap<String, Object> parameters) throws Exception;

	public int checkTakeInCode(@Param("userId")String userId, @Param("cusItemCode")String cusItemCode) throws Exception;

	public void updateUserTakeinInfo(HashMap<String, Object> parameters) throws Exception;

	public Object selectAlready(String orderNo) throws Exception;

	public int checkCusItemCode(ApiOrderItemListVO apiOrderItem) throws Exception;

	public MsgHeaderVO selectMsgHeader(@Param("orderNo")String orderNo, @Param("userId")String apiUserId) throws Exception;

	public ArrayList<MsgBodyVO> selectMsgBody(String groupIdx) throws Exception;

	public void updateHawb(BlApplyVO rtnval) throws Exception;
	
	public String getNno() throws Exception;

	public HashMap<String, Object> spStockChk(HashMap<String, Object> rstStockNo) throws Exception;

	public HashMap<String, Object> stationCheck(HashMap<String, Object> orderInfo) throws Exception;

	public HashMap<String, Object> returnNoChk(HashMap<String, Object> returnNoChkRst) throws Exception;

	public void returnItemInsert(HashMap<String, Object> orderInfo) throws Exception;

	public void returnListInsert(HashMap<String, Object> orderInfo) throws Exception;

	public HashMap<String, Object> selectReturnType(HashMap<String, Object> rstRetunType) throws Exception;

	public HashMap<String, String> spReqReturnDel(HashMap<String, String> parameters) throws Exception;

	public int requestReturnListCnt(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> requestReturnList(HashMap<String, Object> parameters) throws Exception;

	public void insertStockOrder(ApiOrderItemListVO apiOrderItem) throws Exception;

	public int checkOrder(@Param("orderNo")String orderNo, @Param("userId")String apiUserId) throws Exception;

	public void updateCusItemCode(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> spReturnApprv(HashMap<String, Object> prameterInfo);

	public HashMap<String, Object> spStockMsg(HashMap<String, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> seletMsg(HashMap<String, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockNew(HashMap<Object, Object> parameterInfo);

	public int inspStocNewTotalCnt(HashMap<Object, Object> parameterInfo);

	public ArrayList<LinkedHashMap<String, Object>> inspStockItemList(HashMap<Object, Object> stockParameter);

	public LinkedHashMap<String, Object> inspStockItems(HashMap<Object, Object> stockParameter2);

	public ArrayList<LinkedHashMap<String, Object>> inspStockList(HashMap<Object, Object> stockParameter2);

	public ArrayList<LinkedHashMap<String, Object>> inspStockAddList(HashMap<Object, Object> stockParameter2);

	public ArrayList<LinkedHashMap<String, Object>> inspStockMsgList(HashMap<Object, Object> stockParameter2);

	public ArrayList<LinkedHashMap<String, Object>> inspStockImgList(HashMap<Object, Object> stockParameter2);

	public String selectStationCode(String stationName) throws Exception;

	public void insertTbTakeinEtcOrder(HashMap<String, Object> etcOrderList) throws Exception;

	public ApiOrderListVO selectAlreadyOrder(@Param("userId")String userId, @Param("orderNo")String orderNo) throws Exception;

	public ArrayList<InsterumentVO> selectInsterument(HashMap<String, Object> parameters) throws Exception;

	public String selectTransPer(String transCode) throws Exception;

	public void updateYslItemCode(String itemCode) throws Exception;
	public ArrayList<LinkedHashMap<String, Object>> inspStockAddDetail(HashMap<Object, Object> stockParameter2) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectOrderMsg(HashMap<Object, Object> stockParameter) throws Exception;

	public int inspInUnknownCnt(HashMap<Object, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownList(HashMap<Object, Object> parameterInfo) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> inspInUnknownImg(HashMap<Object, Object> stockParameter2) throws Exception;

	public void updateExpBaseInfo(ExpLicenceVO licence) throws Exception;

	public String selectDefaultTransCom(HashMap<String, Object> tempparameters) throws Exception;

	public int checkOrderHawb(@Param("hawbNo")String hawbNo, @Param("userId")String apiUserId) throws Exception;

	public ArrayList<LinkedHashMap<String,Object>> selectUserArea(String userId) throws Exception;

	public TakeinInfoVO selectTakeInItem(HashMap<String, Object> params) throws Exception;

	public String selectHawbInDate(String hawbNo) throws Exception;

	public String selectMawbInDate(String hawbNo) throws Exception;

	public String selectRegInDate(String hawbNo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectViewDeliveryList() throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectBlInfo(HashMap<String, Object> parameters);

	public int selectStationCodeYn(HashMap<String, Object> sqlParams);

	public int selectCheckId(HashMap<Object, Object> parameterInfo);

	public void insertApiConnChk(HashMap<String, Object> apiParams);

	public void updateApiOrderListHawb(ApiOrderListVO updateShopifyOrder);

	public LinkedHashMap<String, Object> selectBlPrintInfo(HashMap<String, Object> parameters);

	public int selectCheckBlCnt(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectBlList(HashMap<String, Object> parameters);

	public void insertExportDeclare(ExportDeclare expInfo);

	public ShipperInfo selectShipperInfo(String userId);

	public int selectHawbNo(ApiOrderListVO apiOrderList);

	public ArrayList<LinkedHashMap<String, Object>> selectBlPrintInfos(HashMap<String, Object> parameters);

	public HashMap<String, String> selectYslItemCode(ApiOrderItemListVO apiOrderItemListVO);
}
