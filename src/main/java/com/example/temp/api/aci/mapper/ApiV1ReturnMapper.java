package com.example.temp.api.aci.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.ReturnOrderListVO;




@Repository
@Mapper
public interface ApiV1ReturnMapper {
	
	public ArrayList<HashMap<String,String>> selectJsonField(@Param("apiName")String apiName, @Param("method")String method, @Param("section")String section) throws Exception;

	public void insertReturnRequest(ReturnRequestVO rtnVal) throws Exception;

	public void insertReturnRequestItem(ReturnRequestItemVO rtnItemVal) throws Exception;
	
	
	public boolean selectNation(String nationCode) throws Exception;

	public String selectNnoByETC(@Param("orderReference")String orderReference, @Param("calculateId")String calculateId, @Param("koblNo")String koblNo) throws Exception;

	public void updateReturnRequest(ReturnRequestVO rtnVal) throws Exception;

	public void updateReturnRequestItem(ReturnRequestItemVO returnRequestItemVO) throws Exception;

	public HashMap<String, Object> selectReturnStatus(HashMap<String, Object> parametersMap) throws Exception;

	public String checkSellerId(String sellerId) throws Exception;

	public int acceptReturnRequest(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectSellerIdChk(String sellerId);

	public HashMap<String, Object> selectSellerIdChkAprv(String sellerId);

	public HashMap<String, Object> selectMsgHis(HashMap<String, String> parameters);

	public void insertMsgInfo(StockMsgVO msgInfo);

	public HashMap<String, Object> selectPodBlInfo(String hawbNo);

	public Collection<? extends StockResultVO> selectStockResult(HashMap<String, Object> map);

	public ArrayList<StockVO> selectStockByGrpIdx2(String groupIdx);

	public void updateMsgInfo(HashMap<String, Object> params);

	public ArrayList<StockMsgVO> selectMsg(HashMap<String, String> parameters);

	public int selectMsgCnt(HashMap<String, String> parameters);

	public void deleteMsgInfo(HashMap<String, Object> params);

	public int selectReturnStationAddrCnt(String stationName);

	public LinkedHashMap<String, Object> selectReturnStationAddr(String stationName);

	public ArrayList<CustomerVO> selectUserList();

	public int selectKoblNoCnt(ReturnOrderListVO returnOrder);

	public int selectOrderReferenceCnt(ReturnOrderListVO returnOrder);

	public int selectReturnOrderCheck(HashMap<Object, Object> parameterInfo);

	public LinkedHashMap<String, Object> selectReturnOrderStatusInfo(HashMap<Object, Object> parameterInfo);

	public String selectReturnOrderNno(HashMap<Object, Object> parameterInfo);

	public HashMap<String, Object> selectWhOutInfo(HashMap<Object, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectReturnStateLog(String nno);

	public ArrayList<HashMap<String, Object>> selectInspectionFileList(HashMap<Object, Object> parameterInfo);

	public ManagerVO selectUserAddrInfo(String string);

}

