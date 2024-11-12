package com.example.temp.manager.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;

@Repository
@Mapper
public interface ManagerReturnOrderMapper {

	public ArrayList<HashMap<String, Object>> selectReturnStateList();

	public int selectReturnOrderListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderEpostList();

	public int selectReturnOrderCntForWhWork(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderListForWhWork(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectReturnOrderCheck(HashMap<String, Object> parameters);

	public ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectReturnItemInfo(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectStockInfo();

	public void insertStockInfo(HashMap<String, Object> parameters);

	public void insertInspFile(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectStockInfoList(HashMap<String, Object> parameters);

	public void updateReturnOrderWhStatus(HashMap<String, Object> parameters);

	public void updateReturnOrderStateInfo(ReturnOrderListVO orderVO);

	public String selectGroupIdx();

	public HashMap<String, Object> execSpWhoutStockReturnIn(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectStockOutList(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectStockUnCheckInfoList(HashMap<String, Object> parameters);

	public int selectSubNoByStockNo(HashMap<String, Object> parameters);

	public void deleteStockOutList(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectTransCodeList(HashMap<String, Object> parameters);

	public ArrayList<UserOrderItemVO> selectStockOutReturnItemInfo(HashMap<String, Object> parameters);

	public void insertReturnItemToTmpItemInfo(UserOrderItemVO userOrderItem);

	public void insertReturnOrderToTmpOrderInfo(UserOrderListVO userOrderInfo);

	public ArrayList<ShpngListVO> selectReturnOrderTmpList(HashMap<String, Object> parameters);

	public ArrayList<String> selectErrorList(String nno);

	public ArrayList<String> selectErrorMatch(String nno);

	public ArrayList<String> selectErrorItem(@Param("nno")String nno, @Param("subNo")String subNo);

	public int selectReturnTmpListCnt(HashMap<String, Object> parameters);

	public void updateStockOutNno(HashMap<String, Object> parameters);

	public void updateEpostInfo(ReturnOrderListVO orderInfo);

	public UserOrderListVO selectUserOrderInfo(HashMap<String, Object> parameters);

	public ArrayList<UserOrderItemVO> selectUserOrderItem(HashMap<String, Object> parameters);

	public ArrayList<String> selectUserOrgNation(HashMap<String, Object> parameters);

	public ArrayList<UserTransComVO> selectDstnTrans(HashMap<String, Object> parameters);

	public void insertHawbInfo(HashMap<String, Object> parameters);

	public void insertVolumeInfo(HashMap<String, Object> parameters);

	public void insertHawbItemInfo(HashMap<String, Object> parameters);

	public ArrayList<String> selectGroupIdxByNno(String nno);

	public void deleteTmpReturnOrderInfo(ReturnOrderListVO orderVO);

	public int selectReturnOrderShipmentCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderShipment(HashMap<String, Object> parameters);

	public int selectReturnOrderWhoutListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderWhoutList(HashMap<String, Object> parameters);

	public void deleteTmpWhoutStock(ReturnOrderListVO orderVO);

	public int selectReturnOrderStockListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnOrderStockList(HashMap<String, Object> parameters);

	public int selectReturnCsListCnt(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectReturnCsList(HashMap<String, Object> parameters);

	public void deleteReturnCs(HashMap<String, Object> parameters);

	public void insertEtcValue(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectStockByGrpIdx(HashMap<String, Object> parameters);

	public int selectReturnTaxListCnt(HashMap<String, Object> parameters);

	public ArrayList<ReturnOrderListVO> selectReturnTaxList(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectReturnWhInItemInfo(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectReturnOrderFileList(String nno);

	public void deleteTmpWhoutInfo(HashMap<String, Object> parameters);


}
