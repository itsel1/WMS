package com.example.temp.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.takein.TakeinOrderListVO;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.takein.NationCodeVO;
import com.example.temp.member.vo.takein.OrgStationVO;
import com.example.temp.member.vo.takein.TakeInUserStockOutVO;
import com.example.temp.member.vo.takein.TakeinInfoVO;
import com.example.temp.member.vo.takein.TakeinOrderVO;
import com.example.temp.member.vo.takein.TakeinUserStockListVO;
import com.example.temp.member.vo.takein.TmpTakeinCodeVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;
import com.example.temp.member.vo.takein.UserTransVO;

@Repository
@Mapper
public interface MemberTakeinMapper {

	void deleteTmptakeinUserList(HashMap<String, String> deleteNno);

	String selectNNO();
	
	void insertTmpTakeinOrder(TmpTakeinOrderVO takeinInfo);

	void insertTmpTakeinOrderItem(TmpTakeinOrderItemVO takeinItem);

	ArrayList<TmpTakeinOrderVO> selectTmpTakeinUserList(HashMap<String, String> parameterInfo);

	ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem(HashMap<String, String> itemParmeter);

	void insertTakeinDlv(HashMap<String, String> insertNno);

	CustomerVO selectCustomInfo(HashMap<String, String> userId);

	ArrayList<TmpTakeinOrderVO> selectTakeinUserList(HashMap<String, Object> parameterInfo);

	ArrayList<TmpTakeinOrderItemListVO> selectTakeinUserOrderItem(HashMap<String, String> itemParmeter);

	int selectTotalCountTakeinList(HashMap<String, Object> parameterInfo);

	void deleteTakeinUserList(HashMap<String, String> deleteNno);

	int selectDelTotalCountTakeinList(HashMap<String, Object> parameterInfo);

	ArrayList<TmpTakeinOrderVO> selectDelTakeinUserList(HashMap<String, Object> parameterInfo);

	ArrayList<TmpTakeinOrderItemListVO> selectDelTakeinUserOrderItem(HashMap<String, String> itemParmeter);

	void recoveryTakeinUserList(HashMap<String, String> recoveryNno);

	int selectUserTakeinCountTakeinList(HashMap<String, Object> parameterInfo);

	ArrayList<TakeinInfoVO> selectUserTakeInInfo(HashMap<String, Object> parameterInfo);

	TakeinInfoVO selectTakeInCode(HashMap<String, Object> parameterInfo);

	ArrayList<OrgStationVO> selectOrgStation(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> insertUserTakeinInfo(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> updateUserTakeinInfo(HashMap<String, Object> parameterInfo);

	ArrayList<TmpTakeinCodeVO> selectUserTmpTakeInInfo(HashMap<String, Object> parameterInfo);

	void deleteUserTakeinCode(HashMap<String, Object> parameterInfo);

	void insertTmpTakeinInfo(TmpTakeinCodeVO tmpTakeinCodeVO);

	NationCodeVO selectNationCode(HashMap<String, Object> parmeterInfo);

	OrgStationVO selectOrgStationCheck(HashMap<String, Object> parameterInfo);

	TmpTakeinCodeVO selectTmpTakeinCode(HashMap<String, Object> parameterInfo);

	void delteUserTmpTakeinCode(HashMap<String, Object> parameterInfo);

	ArrayList<TakeinUserStockListVO> selectTakeinUserStockList(HashMap<String, Object> parameterInfo);

	int selectTakeinUserStockListCnt(HashMap<String, Object> parameterInfo);

	ArrayList<UserTransVO> selectUserTransCom(HashMap<String, String> parameterInfo);

	ArrayList<String> selectErrorMsg(String status) throws Exception;

	ArrayList<TakeInUserStockOutVO> selectTakeInUserStockOutList(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserTakeinStockInMonth(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectStockStationList(HashMap<String, Object> parameterInfo);

	int selectUserTakeinStockInCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserTakeinStockOutDay(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectInOutStockList(HashMap<String, Object> parameterInfo);

	int selectUserTakeinOrderStockOutCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserTakeinOrderStockOut(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserTakeinOrderStockOutExcel(HashMap<String, Object> parameterInfo);

	int selectUserTakeinStockInMonthCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserTakeinStockInMonthExcel(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> takeinStockInOutExcelDown(HashMap<String, Object> parameterInfo);

	int selectInOutStockListCnt(HashMap<String, Object> parameterInfo);

	int selectUserTakeinStockOutDayCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> takeinStockOutExcelDown(HashMap<String, Object> parameterInfo);

	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem2(HashMap<String, Object> itemParmeter2) throws Exception;

	TakeinOrderListVO selectTakeinOrderTmpList(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectUserCusItemList(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> selectTakeinCusItemInJson(HashMap<String, Object> parameterInfo);

	ArrayList<CurrencyVO> selectCurrencyList(HashMap<String, Object> parameterInfo);

	void deleteUserOrderItemTmp(String nno);

	void deleteUserOrderItem(String nno);

	int confirmOrderListTemp(UserOrderListVO userOrderList);

	int confirmOrderList(UserOrderListVO userOrderList);

	void insertTakeinOrderItemTemp(UserOrderItemVO orderItem);

	void insertTakeinOrderItem(UserOrderItemVO orderItem);

	int selectTakeinCodeListCnt(HashMap<String, Object> parameterInfo);

	void deleteExplicenceInfo(HashMap<String, String> deleteNno);


}