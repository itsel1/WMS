package com.example.temp.member.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderListVO;
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

@Service
public interface MemberTakeinService {

	String insertTakeinExcelUpload(MultipartHttpServletRequest multi, HashMap<String, String> parameters);


	void deleteTmptakeinUserList(HashMap<String, String> deleteNno);

	String insertTmpTakeinExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, String> parameters) throws IOException, Exception;
	
	ArrayList<TmpTakeinOrderVO> selectTmpTakeinUserList(HashMap<String, String> parameterInfo)  throws IOException;

	ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem(HashMap<String, String> itemParmeter);
	

	void insertTakeinDlv(HashMap<String, String> insertNno);


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


	String insertTmpTakeinInfoExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, String> parameters) throws IOException;


	TmpTakeinCodeVO selectTmpTakeinCode(HashMap<String, Object> parameterInfo);


	void delteUserTmpTakeinCode(HashMap<String, Object> parameterInfo);


	ArrayList<TakeinUserStockListVO> selectTakeinUserStockList(HashMap<String, Object> parameterInfo);


	int selectTakeinUserStockListCnt(HashMap<String, Object> parameterInfo);


	ArrayList<UserTransVO> selectUserTransCom(HashMap<String, String> parameterInfo);


	ArrayList<String> selectErrorMsg(String nno) throws Exception;


	ArrayList<TakeInUserStockOutVO> selectTakeInUserStockOutList(HashMap<String, Object> parameterInfo);


	ArrayList<HashMap<String, Object>> selectUserTakeinStockInMonth(HashMap<String, Object> parameterInfo);


	ArrayList<HashMap<String, Object>> selectStockStationList(HashMap<String, Object> parameterInfo);


	int selectUserTakeinStockInCnt(HashMap<String, Object> parameterInfo);


	ArrayList<HashMap<String, Object>> selectUserTakeinStockOutDay(HashMap<String, Object> parameterInfo);


	ArrayList<HashMap<String, Object>> selectInOutStockList(HashMap<String, Object> parameterInfo);


	int selectUserTakeinOrderStockOutCnt(HashMap<String, Object> parameterInfo);


	ArrayList<HashMap<String, Object>> selectUserTakeinOrderStockOut(HashMap<String, Object> parameterInfo);


	void takeinOrderStockOutExcelDownDef(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameterInfo);


	int selectUserTakeinStockInMonthCnt(HashMap<String, Object> parameterInfo);


	void selectUserTakeinStockInMonthExcel(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo);


	void takeinStockInOutExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo);



	int selectInOutStockListCnt(HashMap<String, Object> parameterInfo);


	int selectUserTakeinStockOutDayCnt(HashMap<String, Object> parameterInfo);


	void takeinStockOutExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo);


	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem2(HashMap<String, Object> itemParmeter2) throws Exception;


	String insertTakeinOrderInfo(HttpServletRequest request, HashMap<String, String> parameterInfo);


	ArrayList<HashMap<String, Object>> selectUserCusItemList(HashMap<String, Object> parameterInfo);


	HashMap<String, Object> selectTakeinCusItemInJson(HashMap<String, Object> parameterInfo);


	ArrayList<CurrencyVO> selectCurrencyList(HashMap<String, Object> parameterInfo) throws Exception;

	void updateUserTakeinOrderList(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception;


	int selectTakeinCodeListCnt(HashMap<String, Object> parameterInfo) throws Exception;

}
