package com.example.temp.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Service
public interface MemberReturnOrderService {

	ArrayList<NationVO> selectDestinationCode(HashMap<String, Object> params) throws Exception;

	UserOrderListVO selectOrderList(HashMap<String, Object> parameters) throws Exception;

	ArrayList<UserOrderItemVO> selectOrderItemList(HashMap<String, Object> parameters) throws Exception;

	int selectOrderListCnt(HashMap<String, Object> parameters) throws Exception;

	void insertReturnOrderInfoProc(HttpServletRequest request, MultipartHttpServletRequest multi, ReturnOrderListVO order) throws Exception;
	
	Map<String, String> filesUpload(MultipartHttpServletRequest multi, String nno, String userId) throws Exception;

	void insertReturnOrderList(ReturnOrderListVO returnOrder, Map<String, String> resMap) throws Exception;

	void insertReturnItemList(HashMap<String, Object> itemMap) throws Exception;

	void deleteReturnOrderInfo(HashMap<String, Object> params) throws Exception;

	ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters) throws Exception;

	int selectReturnOrderListCnt(HashMap<String, Object> parameters) throws Exception;

	ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters) throws Exception;

	ArrayList<HashMap<String, Object>> selectReturnItemList(HashMap<String, Object> parameters) throws Exception;

	ArrayList<HashMap<String, Object>> selectReturnStateList() throws Exception;

	int selectHawbCheck(HashMap<String, Object> parameters) throws Exception;

	UserVO selectUserEshopInfo(HashMap<String, Object> params) throws Exception;

	HashMap<String, Object> getEshopBlCheck(HttpServletRequest request) throws Exception;

	HashMap<String, Object> getEshopBlInfo(HashMap<String, Object> parameters) throws Exception;

	HashMap<String, Object> selectReturnFileList(String nno) throws Exception;

	void updateReturnOrderInfoProc(HttpServletRequest request, MultipartHttpServletRequest multi,
			ReturnOrderListVO returnOrder) throws Exception;

	void deleteReturnOrderAllList(HashMap<String, Object> rst) throws Exception;

	String selectCustomerEshopInfoYn(HashMap<String, Object> parameters) throws Exception;

	int selectReturnStockListCnt(HashMap<String, Object> parameters) throws Exception;

	ArrayList<ReturnOrderListVO> selectReturnStockList(HashMap<String, Object> parameters) throws Exception;

	ArrayList<HashMap<String, Object>> selectWhInImageList(HashMap<String, Object> parameters) throws Exception;

	void insertReturnOrderStateLog(ReturnOrderListVO orderVo) throws Exception;

	int selectReturnWhoutListCnt(HashMap<String, Object> parameters) throws Exception;

	ArrayList<ReturnOrderListVO> selectReturnWhoutList(HashMap<String, Object> parameters) throws Exception;

	int selectTmpOrderChk(String nno) throws Exception;

	void returnOrderExcelFormDown(HttpServletRequest request, HttpServletResponse response) throws Exception;

	UserVO selectUserComInfo(String userId) throws Exception;


}
