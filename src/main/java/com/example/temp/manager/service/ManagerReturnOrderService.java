package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;

@Service
public interface ManagerReturnOrderService {

	ArrayList<HashMap<String, Object>> selectReturnStateList();

	int selectReturnOrderListCnt(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters);

	void returnOrderEpostExcelDown(HttpServletRequest request, HttpServletResponse response);

	void reeturnOrderEpostExcelUp(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest multi);

	int selectReturnOrderCntForWhWork(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnOrderListForWhWork(HashMap<String, Object> parameters);

	HashMap<String, Object> selectReturnOrderCheck(HashMap<String, Object> parameters);

	ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters);

	ArrayList<HashMap<String, Object>> selectReturnItemInfo(HashMap<String, Object> parameters);

	void execWhInProcess(MultipartHttpServletRequest request);

	ArrayList<HashMap<String, Object>> selectStockInfoList(HashMap<String, Object> parameters);

	void updateReturnOrderStateInfo(ReturnOrderListVO orderVO);

	HashMap<String, Object> execSpWhoutStockReturnIn(HashMap<String, Object> parameters);

	ArrayList<HashMap<String, Object>> selectStockOutList(HashMap<String, Object> parameters);

	ArrayList<HashMap<String, Object>> selectStockUnCheckInfoList(HashMap<String, Object> parameters);

	int selectSubNoByStockNo(HashMap<String, Object> parameters);

	void deleteStockOutList(HashMap<String, Object> parameters);

	HashMap<String, Object> execWhoutProc(HttpServletRequest request);

	ArrayList<HashMap<String, Object>> selectTransCodeList(HashMap<String, Object> parameters);

	LinkedHashMap<String, ArrayList<ShpngListVO>> selectReturnTmpList(HashMap<String, Object> parameters);

	int selectReturnTmpListCnt(HashMap<String, Object> parameters);

	UserOrderListVO selectUserOrderInfo(HashMap<String, Object> parameters);

	ArrayList<UserOrderItemVO> selectUserOrderItem(HashMap<String, Object> parameters);

	ArrayList<String> selectUserOrgNation(HashMap<String, Object> parameters);

	ArrayList<UserTransComVO> selectDstnTrans(HashMap<String, Object> parameters);

	void insertReturnOrderWeightInfo(HashMap<String, Object> parameters);

	ArrayList<String> selectGroupIdxByNno(String parameter);

	void execReturnTmpOrderCancel(ReturnOrderListVO orderVO);

	int selectReturnOrderShipmentCnt(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnOrderShipment(HashMap<String, Object> parameters);

	int selectReturnOrderWhoutListCnt(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnOrderWhoutList(HashMap<String, Object> parameters);

	int selectReturnOrderStockListCnt(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnOrderStockList(HashMap<String, Object> parameters);

	int selectReturnCsListCnt(HashMap<String, Object> parameters);

	ArrayList<HashMap<String, Object>> selectReturnCsList(HashMap<String, Object> parameters);

	void deleteReturnCs(HashMap<String, Object> parameters);

	void createReturnStockPdf(HttpServletRequest request, HttpServletResponse response) throws Exception;

	int selectReturnTaxListCnt(HashMap<String, Object> parameters);

	ArrayList<ReturnOrderListVO> selectReturnTaxList(HashMap<String, Object> parameters);

	ArrayList<HashMap<String, Object>> selectReturnWhInItemInfo(HashMap<String, Object> parameters);

	void insertEtcInfo(HashMap<String, Object> parameters);

	HashMap<String, Object> selectReturnOrderFileList(String nno);

	void deleteTmpWhoutInfo(HashMap<String, Object> parameters);

}
