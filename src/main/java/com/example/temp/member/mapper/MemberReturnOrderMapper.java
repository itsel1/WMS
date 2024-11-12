package com.example.temp.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Repository
@Mapper
public interface MemberReturnOrderMapper {

	public ArrayList<NationVO> selectDestinationCode(HashMap<String, Object> params);

	public UserOrderListVO selectOrderList(HashMap<String, Object> parameters);

	public ArrayList<UserOrderItemVO> selectOrderItemList(HashMap<String, Object> parameters);

	int selectOrderListCnt(HashMap<String, Object> parameters);

	public void insertReturnOrderList(ReturnOrderListVO returnOrder) throws Exception;

	public void insertReturnFileList(HashMap<String, Object> params) throws Exception;

	public void insertReturnItemList(HashMap<String, Object> itemMap) throws Exception;

	public void deleteReturnOrderInfo(HashMap<String, Object> params);

	public int selectReturnOrderListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters);

	public ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectReturnItemList(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectReturnStateList();

	public int selectHawbCheck(HashMap<String, Object> parameters);

	public UserVO selectUserEshopInfo(HashMap<String, Object> params);

	public void insertApiConn(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectEshopApiInfo(String userId);

	public HashMap<String, Object> selectReturnFileList(String nno);

	public void updateReturnFileList(HashMap<String, Object> parameters);

	public void updateReturnOrderList(ReturnOrderListVO order);

	public void updateReturnItemList(HashMap<String, Object> itemInfo);

	public void deleteReturnItemList(ReturnOrderListVO order);

	public int selectReturnFileCnt(HashMap<String, Object> parameters);

	public void deleteReturnOrderAllList(HashMap<String, Object> rst);

	public String selectCustomerEshopInfoYn(HashMap<String, Object> parameters);

	public void insertReturnOrderStateLog(ReturnOrderListVO returnOrder);

	public int selectReturnStockListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnStockList(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectWhInImageList(HashMap<String, Object> parameters);

	public int selectReturnWhoutListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnWhoutList(HashMap<String, Object> parameters);

	public int selectTmpOrderChk(String nno);

	public UserVO selectUserComInfo(String userId);



}
