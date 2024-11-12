package com.example.temp.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.AllowIpVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Service
public interface MemberService {

	public String selectTest() throws Exception;

	public void insertTest() throws Exception;

	public ArrayList<String> selectUserOrgNation(String username) throws Exception;

	public ArrayList<UserTransComVO> selectUserDstnNation(String username) throws Exception;

	public UserVO selectUserInfo(String username) throws Exception;

	public void updateUserInfo(UserVO userInfoVO, InvUserInfoVO invUserInfo, HttpServletRequest request) throws Exception;

	public void insertUserOrderListSagawa(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception;
	
	public void insertUserOrderListARA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception;

	public ArrayList<UserTransComVO> userTransCom(HashMap<String, String> params) throws Exception;

	public HashMap<String, ArrayList<ShpngListVO>> selectShpngList(HashMap<String, Object> parameter) throws Exception;

	public String insertExcelData(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception;

	public UserOrderListVO selectUserOrderOne(String nno) throws Exception;

	public ArrayList<UserOrderItemVO> selectUserOrderItemOne(UserOrderListVO userOrder) throws Exception;

	public void updateUserOrderListSAGAWA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception;
	
	public void updateUserOrderListARA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception;

	public HashMap<String, ArrayList<ShpngListVO>> selectRegistOrderList(HashMap<String, Object> parameter) throws Exception;


	public ArrayList<UserOrderItemVO> selectUserRegistOrderItemOne(UserOrderListVO userOrder) throws Exception;

	public ArrayList<String> selectNationArrayToStation(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<InspStockListVO> selectInspStockOrderInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception;

	public void insertMsgInfo(StockMsgVO msgInfo) throws Exception;

	public String insertExcelDataForSagawa(MultipartHttpServletRequest multi, HttpServletRequest request, String username, String string, String types) throws Exception;

	public ArrayList<UserTransComVO> selectTrkComList(String userId) throws Exception;

	public String insertExcelDataForSagawaManifast(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception;

	public ArrayList<UserTransComVO> selectDstnTrans(String attribute) throws Exception;

	public ArrayList<UserTransComVO> selectUserOrgTrans(String attribute) throws Exception;

	public ArrayList<SendVO> selectSendList(SendVO search) throws Exception;
	
	public ArrayList<SendVO> selectUnSendList(SendVO search) throws Exception;

	public ArrayList<AllowIpVO> selectAllowIpList(String username) throws Exception;

	public void insertAllowIp(HashMap<String, String> parameters) throws Exception;

	public void deleteAllowIp(HashMap<String, String> parameters) throws Exception;

	public String selectApiKey(String string) throws Exception;

	public void insertApiKey(String apiKey, String userId) throws Exception;

	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception;

	public BlApplyVO selectBlApplyARA(String orderNno, String userId, String userIp) throws Exception;

	public HashMap<String, ArrayList<ShpngListVO>> selectSendBeforeOrderList(HashMap<String, Object> parameter) throws Exception;

	public HashMap<String, ArrayList<ShpngListVO>> selectSendAfterOrderList(HashMap<String, Object> parameter) throws Exception;

	public void updateItemTrkNo(String trkNo, String nno, String subNo) throws Exception;

	public int selectSendListCount(SendVO search) throws Exception;

	public int selectUnSendListCount(SendVO search) throws Exception;

	public String selectTransCom(HashMap<String, Object> parameters1) throws Exception;

	public HashMap<String,Object> selectTransIdx(String parameters) throws Exception;

	public int selectShpngListCount(HashMap<String, Object> parameter) throws Exception;

	public String selectTransCodeFromBigo(String string) throws Exception;

	public int selectRegistOrderCount(HashMap<String, Object> parameter) throws Exception;

	public HashMap<String, ArrayList<ShpngListVO>> selectSendErrorOrderList(HashMap<String, Object> parameter) throws Exception;

	public String selectTransCodeFromNNO(String string) throws Exception;

	public String createExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void updateErrorTmp(String string) throws Exception;

	public void updateWebHook(HttpServletRequest request) throws Exception;

	public String insertExcelDataNew(MultipartHttpServletRequest multi, HttpServletRequest request, String username,String string) throws Exception;

	public ArrayList<String> selectOrgNationOption(String userId) throws Exception;

	public ArrayList<HashMap<String,Object>> selectOrgStationOption(HashMap<String,Object> parameters) throws Exception;

	public ArrayList<String> selectDstnNationOption(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectTransCodeOption(HashMap<String, Object> parameters) throws Exception;

	public void insertUploadSet(HashMap<String, Object> parameters) throws Exception;

	public void insertUploadColumn(HttpServletRequest request) throws Exception;

	public ArrayList<ShpngListVO> selectShpngListAll(HashMap<String, Object> parameter) throws Exception;

	public HashMap<String, Object> selectUserTransInfo(String parameter) throws Exception;

	public HashMap<String, Object> selectUserDeposit(HttpServletRequest request, String userId) throws Exception;

	public UserVO selectUserPwCheck(String userId);

	public int selectUserPassword(ManagerVO userInfo);

	public ArrayList<HashMap<String, Object>> selectHsCodeList() throws Exception;

	public ArrayList<HashMap<String, Object>> selectUserCsList(HashMap<String, Object> parameterInfo) throws Exception;

	public void excelDownUserDlvry(HttpServletRequest request, HttpServletResponse response, ArrayList<String> nnoList) throws Exception;

	public ArrayList<String> selectSendNnoList(HashMap<String, Object> params) throws Exception;

	public void printCommercialPdf(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception;

	public void printCommercialExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception;
	
	public void printPackingListExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception;

	public void createKukkiwonExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public HashMap<String, Object> insertKukkiwonOrder(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception;

	public int selectAllOrderListCount(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<SendVO> selectAllOrderList(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<SendVO> selectAllExcelOrderList(HashMap<String, Object> parameters) throws Exception;
	
	public String checkExcelData(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception;

	public int selectShopeeAuthCnt(HashMap<String, Object> params) throws Exception;

	public String selectShopeeAuthExpiryDate(HashMap<String, Object> params) throws Exception;

	public void updateInspItemTrkNo(HashMap<String, Object> parameters) throws Exception;

	public void printPackingListPdf(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception;

	public String insertExcelDataTest(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType);
}
