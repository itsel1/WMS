package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserTransComVO;


@Service
public interface ManagerReturnService {

	public int getTotalReturnRequestCnt(HashMap<String, Object> map) throws Exception;

	public ArrayList<ReturnListVO> getReturnRequestData(HashMap<String, Object> map) throws Exception;

	public String updateReturnExcel(MultipartHttpServletRequest multi, HttpServletRequest request, HashMap<String, String> parameters) throws Exception;

	public void returnExcelDown(ReturnVO returnVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ArrayList<ReturnItemVO> getReturnItemByNno(String nno) throws Exception;

	public ReturnRequestVO getAllExpressData(String orderReference, String userId) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspList(HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnInspListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspOne(HashMap<String, Object> parameterOption) throws Exception;

	public LinkedHashMap<String, Object> returnRequestWhProcess(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi, String orderReference) throws Exception;
	
	public LinkedHashMap<String, Object> returnRequestWhFailProcess(HttpServletRequest request, MultipartHttpServletRequest multi,HttpServletResponse response, String orderReference) throws Exception;

	public void updateOrderRegistInReturn(String targetStatus, String orderReference) throws Exception;

	public int selectReturnInspSuccessListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnSuccessInspList(
			HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnInspFailListCnt(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnFailInspList(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<InspStockOutVO> selectStockRackInfo(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<InspStockOutVO> selectReturnStockOutTarget(HashMap<String, Object> parameter) throws Exception;

	public void deleteReturnStockOut(HashMap<String, Object> parameter) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspPassOne(String orderReference) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspItem(Object object) throws Exception;

	public String selectReturnTransCode(Object dstnNation,Object sellerId) throws Exception;

	public String insertOrderInfo(HttpServletRequest request) throws Exception;

	public void updateReturnStatus(HashMap<String, Object> parameterOption) throws Exception;

	public void cancleStock(String orderReference) throws Exception;

	public void insertDepositHisRequest(HttpServletRequest request) throws Exception;
	
	public void insertDepositHis(HashMap<String, Object> depositInfo) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspDelOne(HashMap<String, Object> parameterOption) throws Exception;

	public void insertDepositDelHis(HttpServletRequest request) throws Exception;
	
	public ArrayList<HashMap<String,Object>> selectDepositUserList(HashMap<String, Object> infoMap) throws Exception;

	public ArrayList<HashMap<String, Object>> selectDepositUserHis(String userId) throws Exception;

	public HashMap<String, Object> selectDepositUserOne(String userId) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspAddOne(HashMap<String, Object> parameterOption) throws Exception;

	public void insertDepositAddHis(HttpServletRequest request) throws Exception;

	public int selectReturnOptionCnt(HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectReturnOption(HashMap<String, Object> parameterOption) throws Exception;

	public LinkedHashMap<String, Object> selectReturnInspReadOne(HashMap<String, Object> parameterOption) throws Exception;

	public int selectReturnRequestCnt(HashMap<String, Object> map);

	public ArrayList<ReturnListVO> selectReturnRequest(HashMap<String, Object> map);

	public void deleteReturnOrder(HashMap<String, Object> params);

	public int selectTaxReturnRequestCnt(HashMap<String, Object> map);

	public ArrayList<ReturnListVO> selectTaxReturnRequest(HashMap<String, Object> map);

	public int selectB001ReturnListCnt(HashMap<String, Object> parameterOption);

	public ArrayList<LinkedHashMap<String, Object>> selectB001ReturnList(HashMap<String, Object> parameterOption) throws Exception;

	public String selectReturnNno(HashMap<String, Object> params);

	public void updateReturnStockIn(String nno);

	public ArrayList<UserTransComVO> selectReturnInspTransList(String dstnNation);

	public LinkedHashMap<String, Object> selectReturnOrderInfo(HashMap<String, Object> params);

	public OrderInspVO selectReturnOrderStock(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectReturnOrderInfo2(HashMap<String, Object> params);

	public ArrayList<OrderInspVO> selectReturnInspOrderItemList(HashMap<String, Object> parameter);

	public ArrayList<ReturnListVO> selectReturnRequest2(HashMap<String, Object> map);

	public int selectReturnRequestCnt2(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectDepositInfo(HashMap<String, Object> params);

	public HashMap<String, Object> selectDepositTotal(String userId);

	public int selectDepositInfoCnt(HashMap<String, Object> params);

	public Double selectDepositCostNow(HashMap<String, Object> params);

	public void insertDepositAdd(HashMap<String, Object> params);

	public int selectReturnCSListCnt(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectReturnCSList(HashMap<String, Object> map);

	public ArrayList<HashMap<String, Object>> selectReturnStation(HashMap<String, Object> params);

	public int selectReturnJapanSuccessListCnt(HashMap<String, Object> parameterOption);

	public ArrayList<LinkedHashMap<String, Object>> selectReturnJapanSuccessList(
			HashMap<String, Object> parameterOption) throws Exception;

	public ArrayList<UserOrderItemVO> selectReturnItemOne(String nno);

	public String selectErrorMessage(String nno);

	public ReturnRequestVO selectTaxReturnInfo(String nno) throws Exception;

	public int selectOrderInspcCnt(HashMap<String, Object> orderInfo) throws Exception;

	public ArrayList<ReturnRequestVO> selectReturnOrderInspList(ReturnRequestVO parameters) throws Exception;

	public ReturnRequestVO selectReturnInspOrder(String orderReference) throws Exception;

	public ArrayList<ReturnRequestItemVO> selectReturnInspOrderItem(String nno) throws Exception;

	public String selectReturnOrderNNO(String orderReference) throws Exception;

	public int selectStockTotalCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<GroupStockVO> selectReturnInspOrderInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public ReturnRequestVO selectReturnInfo(String nno) throws Exception;

	public ArrayList<String> selectLoadFileImage(String groupIdx) throws Exception;

	public HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters) throws Exception;

	public void updateStockCancel(String nno) throws Exception;

	public ReturnRequestVO selectReturnRequestInfo(HashMap<String, Object> params) throws Exception;

	public int selectTrashReturnListCnt(HashMap<String, Object> params) throws Exception;

	public ArrayList<ReturnRequestVO> selectTrashReturnList(HashMap<String, Object> params) throws Exception;

	public ReturnRequestVO selectReturnRequestApply(HashMap<String, Object> params) throws Exception;

	public ReturnRequestVO selectReturnInfoForStockOut(HashMap<String, Object> params) throws Exception;

	public String selectReturnStatus(HashMap<String, Object> parameterOption);


}