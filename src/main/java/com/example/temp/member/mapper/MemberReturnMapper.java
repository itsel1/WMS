package com.example.temp.member.mapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.manager.vo.NationVO;

@Repository
@Mapper
public interface MemberReturnMapper {

	public void updateItems(HashMap<String, String> map) throws Exception;
	
	public void updateReturnData(HashMap<String, String> map1) throws Exception;
	
	public int getTotalOrderCnt(HashMap<String, Object> map) throws Exception;
	
	public ArrayList<ReturnListVO> getReturnData(HashMap<String, Object> map) throws Exception;
	
	public String getNno(@Param("orderReference")String orderReference, @Param("userId")String userId) throws Exception;
	
	public ReturnVO getAllExpressData(@Param("orderReference")String orderReference, @Param("userId")String userId) throws Exception;
	
	public ArrayList<ReturnItemVO> getAllExpressItemsData(String nno) throws Exception;
	
	public ReturnVO getReturnOrders(@Param("koblNo")String koblNo, @Param("userId")String userId) throws Exception;
	
	public ArrayList<ReturnItemVO> getReturnOrdersItem(String nno) throws Exception;
	
	public HashMap<String, Object> selectTempData(String nno) throws Exception;

	public void insertReturnRequest(ReturnRequestVO rtnRequestVO) throws Exception;

	public void insertReturnRequestItem(HashMap<String, Object> itemMap) throws Exception;

	public void updateReturnRequest(ReturnRequestVO rtnRequestVO) throws Exception;

	public void deleteReturnRequest(String nno) throws Exception;

	public void deleteReturnRequestItem(String nno) throws Exception;

	public void updateReturnState(@Param("state")String state, @Param("orderReference")String orderReference) throws Exception;

	public double selectDepositInfo(Object userId) throws Exception;

	public ArrayList<NationVO> selectNationCode();

	public int selectReturnInspListCnt(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectReturnInspList(HashMap<String, Object> params);

	public int selectReturnSendOutListCnt(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectReturnSendOutList(HashMap<String, Object> params);

	public int selectReturnDiscardListCnt(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectReturnDiscardList(HashMap<String, Object> params);

	public InvUserInfoVO selectAttnInfo(String userId);

	public ReturnRequestVO selectCurrentInfo(String userId);

	public int selectCurrentInfoCnt(String userId);

	public ReturnRequestVO selectReturnOrder(HashMap<String, Object> params);

	public ArrayList<ReturnItemVO> selectReturnOrderItem(String nno);

	public int selectAciOrderCnt(String koblNo);

	public String selectReturnNno(HashMap<String, Object> params);

	public ArrayList<ReturnItemVO> selectCusItemCode(String nno);

	public void insertCusItemCodeInfo(HashMap<String, Object> params2);

	public int selectItemCode(HashMap<String, Object> params2);

	public String selectDstnNation(String nno);

	public String selectReturnOrderReference(String nno);

}
