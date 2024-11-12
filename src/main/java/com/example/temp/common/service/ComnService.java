package com.example.temp.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.temp.api.Order;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.common.vo.AciFakeBlVO;
import com.example.temp.common.vo.DeliverTrackVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.PdfPrintVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.comn.HawbLookUpVo;

@Service
public interface ComnService {
	public ArrayList<NoticeVO> selectNotice(HashMap<String, Object> infoMap) throws Exception;

	public boolean insertMemberInfos(ManagerVO userInfo,InvUserInfoVO invUserInfo, HttpServletRequest request) throws Exception;
	
	public ArrayList<NationVO> selectNationCode() throws Exception;
	
	public HashMap<String, ArrayList<ZoneVO>> makeTransMap(HashMap<String,Object> TransMaps) throws Exception;
	
	public ArrayList<HashMap<String,Object>> selectUserTrkCom(String userId) throws Exception;

	public int selectTotalCntNotice(HashMap<String, Object> infoMap) throws Exception;

	public NoticeVO selectNoticeDetail(HashMap<String, Object> infoMap) throws Exception;

	public String selectStationToNation(String parameter) throws Exception;

	public ArrayList<NationVO> selectStationToNationList(HashMap<String, Object> parameters) throws Exception;

	public PdfPrintVO selectPdfPrintInfo(String orderNnoList, String PrintType) throws Exception;

	public int selectUserCnt(String userId) throws Exception;

	public ArrayList<String> selectUserItem(String nno) throws Exception;

	public String selectSessionInfo(HashMap<String, String> sessionInfo) throws Exception;

	public DeliverTrackVO selectDeliverInfo(String hawbNo, String orgStation) throws Exception;

	public String selectTransComByNno(String nno) throws Exception;

	public ArrayList<HashMap<String, String>> selectHawbListARA(ArrayList<String> orderNnoList) throws Exception;

	public String selectOrgStationName(String attribute)throws Exception;

	public void aramexPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String orderType) throws Exception;
	
	public void loadPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,String printType) throws Exception;

	//public void sagawaPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;
	//public void savePdf(HttpServletRequest request, String orderNno, String printType) throws Exception;
	
	public void savePdf(String orderNno, String printType, String userId, String userIp) throws Exception;

	public ArrayList<NationVO> selectNationInStation() throws Exception;

	public InvUserInfoVO selectInvUserInfo(String username) throws Exception;

	public int selectSendListCount(SendVO search) throws Exception;

	public int selectUnSendListCount(SendVO search) throws Exception;

	public void yslPdf(String orderNno, String printType, String userId, String userIp, String blType) throws Exception;

	public void ocsPdf(String orderNno, String printType, String userId, String userIp) throws Exception;
	
	public HashMap<String, String> selectTransComChange(HashMap<String, String> transComChangeParameters) throws Exception;

	public HashMap<String, Object> execSpHoldBl(HashMap<String, Object> pramaterInfo) throws Exception;
	
	public ProcedureVO selectTransComChangeForVo(HashMap<String, Object> transParameter)  throws Exception;

	public void efsPdf(String orderNno, String printType, String userId, String userIp) throws Exception;

	public String selectUserTransCode(HashMap<String, Object> transParameter) throws Exception;

	public String selectMatchNumByHawb(String hawbNo) throws Exception;

	public String selectTransCodeFromHawb(String hawbNo) throws Exception;

	public ArrayList<MawbVO> mawbListLookUp(HashMap<String, String> parameters) throws Exception;

	public ArrayList<String> selectHawbByMawb(String mawbNo) throws Exception;

	public HawbLookUpVo selectHawbInfoJson(String hawbNo) throws Exception;

	public void etcsPdf(String userId, String orderNno) throws Exception;

	public void krPostPdf(String orderNno, String printType, String userId, String userIp) throws Exception;
	
	public void printPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType, String orderType) throws Exception;
	
	public void printGTSPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;

	public void yslPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType, String orderType) throws Exception;

	public void ocsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;
	
	public void efsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;
	
	public void etcsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) throws Exception;

	public void krPostPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;

	public void downExcelData(HashMap<String, Object> excelParameter, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void savedPdfSek(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception;

	public ArrayList<HashMap<String, Object>> selectWebHookInfo(String userId) throws Exception;
	
	public String selectWebHookInfoOne(HashMap<String,Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectBlList() throws Exception;

	public ArrayList<HashMap<String, Object>> selectUserTrkComBlType(String parameter) throws Exception;

	public void printCommercialPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;

	public void printCommercialExcel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception;

	public void insertSendTable(HashMap<String, Object> sendData) throws Exception;

	public void krPostZPLTest(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception;

	public String selectHawbNoByNNO(String nno) throws Exception;
	
	public void comnBlApply(String orderNno, String transCode, String usreId, String userIp, String blType) throws Exception;
	
	public BlApplyVO selectBlApply(String orderNno, String userId, String userIp) throws Exception;

	public void insertTmpFromOrderList(@Param("nno")String nno, @Param("status")String status) throws Exception;
	
	public void insertTmpFromOrderItem(String nno) throws Exception;
	
	public void deleteUserOrderTmp(String[] orderNno,String userId) throws Exception;
	
	public void deleteUserOrder(String[] orderNno,HttpServletRequest request) throws Exception;
	
	public void insertUserOrder(String[] orderNno,String userId, String userIp) throws Exception;
	
	public void insertUserOrderItemFromTmp(String orderNno) throws Exception;
	
	public void insertUserOrderListFromTmp(String orderNno) throws Exception;

	public void insertTBFromTMP(String orderNno) throws Exception;

	public void insertTMPFromTB(String orderNno, String status, String userId, String userIp) throws Exception;
	
	public void insertTMPFromTB_EMS(String orderNno, String status, String userId, String userIp) throws Exception;
	
	public void deleteUserOrderItemTmp(String nno) throws Exception;

	public void deleteUserOrderItem(String nno) throws Exception;
	
	public UserOrderListVO selectUserRegistOrderOne(String nno) throws Exception;

	public void updateHawbNoInTbHawb(String hawbNo, String orderNno) throws Exception;

	public void deleteHawbNoInTbHawb(String nno) throws Exception;

	public void deleteOrderListByNno(String orderNno) throws Exception;

	public AciFakeBlVO getAciFakeBl(AciFakeBlVO aciFakeBlVO) throws Exception; 

	public void updateUserOrderListStatus(String userId) throws Exception;
	
	public String[] confirmDialNum(String numbers) throws Exception;

	public void updateShipperReference(String shipperReference, String orderNno) throws Exception;
	
	public void updateHawbNoInTbOrderList(String hawbNo, String orderNno) throws Exception ;

	public void comnBlApplyCheck(String orderNno, String transCode, String userId, String userIp, String blType) throws Exception;
	
	public void insertMatchingInfo(MatchingVO matchVo) throws Exception;
	
	public void deleteMatchingInfo(MatchingVO matchVo) throws Exception;

	void comnBlApplyInsp(String orderNno, String transCode, String userId, String userIp, String blType) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public String selectHawbDate(String hawbNo) throws Exception;

	public void cjPdf(ArrayList<CJParameterVo> cJParameters, String userId, String userIp) throws Exception;

	public String selectNNO() throws Exception;

	public String selectNewRegNo(ReturnRequestVO rtnVal) throws Exception;

	public void deleteRegNo(String regNo) throws Exception;

	public String selectLegacyRegNo(ReturnRequestVO rtnVal) throws Exception;

	public String selectReturnRequestId(HashMap<String, Object> parameterInfo) throws Exception;

	public LinkedHashMap<String, Object> selectReturnRequestStatus(HashMap<String,Object> parameterInfo) throws Exception;

	public LinkedHashMap<String,Object> selectStockMsg(String nno) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectStockFile(String nno) throws Exception;

	public ArrayList<NationVO> selectOrgStation();

	public ArrayList<HashMap<String, Object>> selectReturnStationList(String orgStation);

	public ArrayList<CurrencyVO> selectCurrencyList();

	public int selectUserIdCheck(HashMap<String, Object> params);

	public UserVO selectUserEmail(String userId);

	public void updateUserPw(ManagerVO userInfo);

	public ArrayList<NationVO> selectUserNationCode(String username) throws Exception;

	public ArrayList<HashMap<String, Object>> selectCurrencyListByDstnNation(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<NationVO> selectUserDstnNationList(String userId);

	public void updateTmpOrderStatus(String string) throws Exception;

	public ArrayList<String> selectYslOrderList() throws Exception;

	public void comnPrintPacking(HttpServletRequest request, HttpServletResponse response, Model model,
			ArrayList<String> orderNnoList) throws Exception;

	public HashMap<String, Object> selectMatchCarriers(String hawbNo) throws Exception;

	public String selectAgencyBl(HashMap<String, Object> parameters) throws Exception;

	public BlApplyVO createParcelBl(HashMap<String, Object> parameters) throws Exception;

	public String selectSubTransCode(String hawbNo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectReturnMemoList(HashMap<String, Object> parameters);

	public void insertReturnCsMemo(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<HashMap<String, Object>> selectRecentMemoInfo(HashMap<String, Object> parameters);

	public void updateReturnCsReadYn(HashMap<String, Object> parameters) throws Exception;

	public void printBarcodePdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> nnoList) throws Exception;

	public String selectTrackingNoYT(String hawbNo) throws Exception;

	public String comnBlApplyV2(String orderNno, String transCode, String userId, String userIp, String orderType, String uploadType) throws Exception;

	public String comBlApplyCheckV2(String orderNno, String transCode, String userId, String userIp, String orderType, String uploadType) throws Exception;

	public void printEpostPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) throws Exception;

	public ArrayList<HashMap<String, Object>> makeEpostPodDetatailArray(String hawbNo, HttpServletRequest request) throws Exception;
	
	public void createLabel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType, String orderType);

	public void sendEmailToSeller(HashMap<String, Object> parameters) throws Exception;
	
	public void createLabelForApi(String orderNno, String printType, String userId, String userIp) throws Exception;

	public void yslPdfV2(String orderNno, String printType, String userId, String userIp, String string) throws Exception;

	public String createZipFiles(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public byte[] createOrderListExcelCheck(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ArrayList<HashMap<String, Object>> selectCurrencyListUse() throws Exception;
	
	public ArrayList<HashMap<String, String>> selectTransCodeList(String orgStation) throws Exception;


}
