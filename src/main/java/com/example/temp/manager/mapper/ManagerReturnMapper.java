package com.example.temp.manager.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.StockAllVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;

@Repository
@Mapper
public interface ManagerReturnMapper {

	public int getTotalReturnRequestCnt(HashMap<String, Object> map) throws Exception;

	public ArrayList<ReturnListVO> getReturnRequestData(HashMap<String, Object> map) throws Exception;
	
	public void updateReturnExcel(ReturnVO vo) throws Exception;

	public ArrayList<ReturnVO> selectReturnListExcel(ReturnVO returnVO) throws Exception;

	public ArrayList<ReturnItemVO> getReturnItemByNno(String nno) throws Exception;

	public ReturnRequestVO getAllExpressData(@Param("orderReference")String orderReference, @Param("userId")String userId) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspList(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspItem(Object nno) throws Exception;

	public int selectReturnInspListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspOne(HashMap<String, Object> parameterOption) throws Exception;

	public StockResultVO insertReturnStock(StockAllVO stockAll) throws Exception;

	public void updateReturnStatus(HashMap<String, Object> parameterOption) throws Exception;

	public void insertReturnStockFile(StockAllVO stockAll) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnSuccessInspList(
			HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnInspSuccessListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnFailInspList(HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnInspFailListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public void updateStockMsgStatus(StockAllVO stockAll) throws Exception;

	public ArrayList<InspStockOutVO> selectReturnStockRackInfo(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<InspStockOutVO> selectReturnStockOutTarget(HashMap<String, Object> parameter) throws Exception;

	public void deleteReturnStockOut(HashMap<String, Object> parameter) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspPassOne(String orderReference) throws Exception;

	public String selectReturnTransCode(@Param("dstnNation")Object dstnNation, @Param("sellerId")Object sellerId) throws Exception;

	public void insertTmpReturnOrder(UserOrderListVO tmpOrderInfo) throws Exception;

	public void insertTmpReturnItem(UserOrderItemVO tmpOrderItem) throws Exception;

	public void deleteTmpInfo(String nno) throws Exception;

	public int selectTempInfo(String nno) throws Exception;

	public String selectNnoByOrderReference(String orderReference) throws Exception;

	public void cancleStock(String nno) throws Exception;

	public void insertDepositHisRequest(HashMap<String, Object> depositInfo) throws Exception;
	
	public void insertDepositHis(HashMap<String, Object> depositInfo) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspDelOne(HashMap<String, Object> parameterOption) throws Exception;
	
	public ArrayList<HashMap<String,Object>> selectDepositUserList(HashMap<String, Object> infoMap) throws Exception;

	public ArrayList<HashMap<String, Object>> selectDepositUserHis(String userId) throws Exception;

	public HashMap<String, Object> selectDepositUserOne(String userId) throws Exception;

	public String selectOrderReferenceByKobl(String koblNo) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspAddOne(HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnOptionCnt(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnOption(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspReadItem(Object nno) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectStockImgList(@Param("nno")String nno, @Param("subNo")String subNo);

	public int selectReturnRequestCnt(HashMap<String, Object> map);

	public ArrayList<ReturnListVO> selectReturnRequest(HashMap<String, Object> map);

	public void deleteReturnOrder(HashMap<String, Object> params);

	public int selectTaxReturnRequestCnt(HashMap<String, Object> map);

	public ArrayList<ReturnListVO> selectTaxReturnRequest(HashMap<String, Object> map);

	public int selectB001ReturnListCnt(HashMap<String, Object> parameterOption);

	public ArrayList<LinkedHashMap<String, Object>> selectB001ReturnList(HashMap<String, Object> parameterOption);

	public String selectReturnNno(HashMap<String, Object> params);

	public void updateReturnStockIn(String nno);

	public ArrayList<UserTransComVO> selectReturnInspTransList(String dstnNation);

	public LinkedHashMap<String, Object> selectReturnOrderInfo(HashMap<String, Object> params);

	public OrderInspVO selectReturnOrderStock(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectReturnOrderInfo2(HashMap<String, Object> params);

	public ArrayList<OrderInspVO> selectReturnInspOrderItemList(HashMap<String, Object> parameter);

	public BlApplyVO selectBlApply(TestssVO parameters) throws Exception;

	public ArrayList<ReturnListVO> selectReturnRequest2(HashMap<String, Object> map);

	public int selectReturnRequestCnt2(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectDepositInfo(HashMap<String, Object> params);

	public HashMap<String, Object> selectDepositTotal(String userId);

	public int selectDepositInfoCnt(HashMap<String, Object> params);

	public Double selectDepositCostNow(HashMap<String, Object> params);

	public void insertDepositAdd(HashMap<String, Object> params);

	public Double selectDepositInspPrice(HashMap<String, Object> depositInfo);

	public int selectReturnCSListCnt(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectReturnCSList(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectReturnStation(HashMap<String, Object> params);

	public int selectReturnJapanSuccessListCnt(HashMap<String, Object> parameterOption);

	public ArrayList<LinkedHashMap<String, Object>> selectReturnJapanSuccessList(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<UserOrderItemVO> selectReturnItemOne(String nno);

	public String selectErrorMessage(String nno);

	public ReturnRequestVO selectTaxReturnInfo(String nno);

	public int selectOrderInspcCnt(HashMap<String, Object> orderInfo);

	public ArrayList<ReturnRequestVO> selectReturnOrderInspList(ReturnRequestVO parameters);

	public ReturnRequestVO selectReturnInspOrder(String orderReference);

	public ArrayList<ReturnRequestItemVO> selectReturnInspOrderItem(String nno);

	public String selectReturnOrderNNO(String orderReference);

	public ReturnRequestVO selectReturnInspOrder2(HashMap<String, Object> parameterOption);

	public int selectStockTotalCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<GroupStockVO> selectReturnInspOrderInfo(HashMap<String, Object> parameterInfo);

	public ReturnRequestVO selectReturnInfo(String nno);

	public ArrayList<String> selectLoadFileImage(String groupIdx);

	public HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters);

	public void updateStockCancel(String nno);

	public ReturnRequestVO selectReturnRequestInfo(HashMap<String, Object> params);

	public HashMap<String, Object> selectStockInfo(String parameter);

	public HashMap<String, Object> spStockDisposal(HashMap<String, Object> parameters);

	public int selectTrashReturnListCnt(HashMap<String, Object> params);

	public ArrayList<ReturnRequestVO> selectTrashReturnList(HashMap<String, Object> params);

	public ReturnRequestVO selectReturnInfos(HashMap<String, Object> parameterOption);

	public ReturnRequestVO selectReturnRequestApply(HashMap<String, Object> params);

	public ReturnRequestVO selectReturnInfoForStockOut(HashMap<String, Object> params);

	public int selectStockMsgCnt(Object object);

	public HashMap<String, Object> selectStockMsg(Object object);

	public String selectReturnStatus(HashMap<String, Object> parameterOption);


}