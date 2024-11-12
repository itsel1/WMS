package com.example.temp.common.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.ExportDeclareVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.common.vo.AciFakeBlVO;
import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
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
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.trans.comn.HawbLookUpVo;

@Repository
@Mapper
public interface ComnMapper {

	public ArrayList<NoticeVO> selectNoticeList(HashMap<String, Object> infoMap) throws Exception;

	public void insertMemberInfo(ManagerVO userInfo) throws Exception;
	
	public ArrayList<NationVO> selectNationCode() throws Exception;

	public ArrayList<ZoneVO> makeTransMap(HashMap<String,Object> TransMaps) throws Exception;
	
	public ArrayList<HashMap<String,Object>> selectUserTrkCom(String userId) throws Exception;

	public int selectTotalCntNotice(HashMap<String, Object> infoMap) throws Exception;

	public NoticeVO selectNoticeDetail(HashMap<String, Object> infoMap) throws Exception;

	public String selectStationTonation(String parameter) throws Exception;

	public ArrayList<NationVO> selectStationToNationList(HashMap<String, Object> parameters) throws Exception;

	public PdfPrintVO selectPdfPrintInfo(@Param("orderNno")String orderNno, @Param("printType") String printType) throws Exception;
	
	public PdfPrintVO selectTempInfo(@Param("orderNno")String orderNno, @Param("printType") String printType) throws Exception;

	public int selectUserCnt(String userId) throws Exception;

	public void insertUserTransCom(HashMap<String,String> trkInfo) throws Exception;

	public ArrayList<String> selectUserItem(String nno) throws Exception;

	public String selectSessionInfo(HashMap<String, String> sessionInfo) throws Exception;

	public DeliverTrackVO selectDeliverInfo(@Param("hawbNo") String hawbNo, @Param("orgStation") String orgStation) throws Exception;

	public String selectTransComByNno(String nno) throws Exception;

	public ArrayList<HashMap<String, String>> selectHawbListARA(ArrayList<String> list) throws Exception;

	public String selectOrgStationName(String orgStation) throws Exception;

	public ArrayList<NationVO> selectNationInStation() throws Exception;

	public void insertInvUserInfo(InvUserInfoVO invUserInfo) throws Exception;

	public InvUserInfoVO selectInvUserInfo(String userId) throws Exception;

	public int selectSendListCount(SendVO search) throws Exception;

	public int selectUnSendListCount(SendVO search) throws Exception;

	public ArrayList<HashMap<String, String>> selectHawbListSaved(HashMap<String,Object> parameters) throws Exception;

	public PdfPrintVO selectOcsInfo(@Param("orderNno")String orderNno, @Param("printType") String printType) throws Exception;
	
	public HashMap<String, String> selectTransComChange(HashMap<String, String> transComChangeParameters) throws Exception;

	public ProcedureVO selectTransComChangeForVo(HashMap<String, Object> transParameter) throws Exception;

	public String selectUserTransCode(HashMap<String, Object> transParameter) throws Exception;

	public String selectMatchNumByHawb(String hawbNo) throws Exception;

	public String selectTransCodeFromHawb(String hawbNo) throws Exception;

	public HashMap<String, Object> execSpHoldBl(HashMap<String, Object> pramaterInfo) throws Exception;

	public ArrayList<MawbVO> mawbListLookUp(HashMap<String, String> parameters) throws Exception;

	public ArrayList<String> selectHawbByMawb(String mawbNo) throws Exception;

	public HawbLookUpVo selectHawbInfoJson(String hawbNo) throws Exception;

	public PdfPrintVO selectTempInfoETCS(String string) throws Exception;

	public HashMap<String, Object> selectItemInfos(String nno) throws Exception;

	public ArrayList<ShpngListVO> selectRegistedOrderList(HashMap<String, Object> excelParameter) throws Exception;

	public ArrayList<ShpngListVO> selectSendBeforeOrderForExcel(HashMap<String, Object> excelParameter) throws Exception;

	public ArrayList<ShpngListVO> selectSendAfterOrderForExcel(HashMap<String, Object> excelParameter) throws Exception;

	public ArrayList<HashMap<String, Object>> selectWebHookInfo(String userId) throws Exception;
	
	public String selectWebHookInfoOne(HashMap<String,Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectBlList() throws Exception;

	public String selectBlTypeTransCom(PdfPrintVO printInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectUserTrkComBlType(String userId) throws Exception;

	public CommercialVO selectCommercialInfo(String nno) throws Exception;

	public ArrayList<CommercialItemVO> selectCommercialItem(String nno) throws Exception;

	public void insertSendTable(HashMap<String, Object> sendData) throws Exception;

	public String selectHawbNoByNNO(String nno) throws Exception;
	
	public BlApplyVO selectBlApply(TestssVO parameters) throws Exception;
	
	public void insertTmpFromOrderList(@Param("nno")String nno, @Param("status")String status) throws Exception;
	
	public void insertTmpFromOrderItem(String nno) throws Exception;
	
	public void deleteUserOrderItemTmp(@Param("nno")String string) throws Exception;
	
	public void deleteUserOrderListTmp(@Param("nno")String string) throws Exception;
	
	public void deleteExpLicence(String nno) throws Exception;
	
	public void updateUserOrderListStatus(String userId) throws Exception;
	
	public void deleteUserOrder(@Param("orderNno")String string, @Param("userId")String userId, @Param("userIp")String userIp) throws Exception;
	
	public void insertUserOrderListFromTmp(@Param("nno")String orderNno) throws Exception;

	public void insertUserOrderItemFromTmp(@Param("nno")String orderNno) throws Exception;
	
	public void deleteUserOrderItem(String nno) throws Exception;
	
	public UserOrderListVO selectUserRegistOrderOne(String nno) throws Exception;
	
	public void updateHawbNoInTbHawb(@Param("hawbNo")String hawbNo, @Param("nno")String nno) throws Exception;
	
	public void deleteHawbNoInTbHawb(String nno) throws Exception;

	public void deleteOrderListByNno(String orderNno) throws Exception;

	public void updateShipperReference(@Param("shipperReference")String shipperReference, @Param("nno")String nno);
	
	public void updateHawbNoInTbOrderList(@Param("hawbNo")String hawbNo, @Param("nno")String nno) throws Exception;

	public AciFakeBlVO getAciFakeBl(AciFakeBlVO aciFakeBlVO) throws Exception;

	public void insertTmpFromOrderList_EMS(@Param("nno")String nno, @Param("status")String status) throws Exception;
	
	public void insertMatchingInfo(MatchingVO matchVo) throws Exception;
	
	public void deleteMatchingInfo(MatchingVO matchVo) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public String selectHawbDate(String hawbNo) throws Exception;

	public String selectNNO() throws Exception;

	public String selectNewRegNo(ReturnRequestVO rtnVal) throws Exception;

	public void deleteRegNo(String regNo) throws Exception;

	public String selectLegacyRegNo(ReturnRequestVO rtnVal) throws Exception;

	public String selectReturnRequestId(HashMap<String, Object> parameterInfo) throws Exception;

	public LinkedHashMap<String, Object> selectReturnRequestStatus(HashMap<String, Object> parameterInfo) throws Exception;

	public LinkedHashMap<String,Object> selectStockMsg(String nno) throws Exception;

	public ArrayList<LinkedHashMap<String, Object>> selectStockFile(String nno) throws Exception;

	public ArrayList<NationVO> selectOrgStation();

	public ArrayList<HashMap<String, Object>> selectReturnStationList(String orgStation);

	public ArrayList<CurrencyVO> selectCurrencyList();

	public String selectUID();

	public int selectUserIdCheck(HashMap<String, Object> params);

	public UserVO selectUserEmail(String userId);

	public void updateUserPw(ManagerVO userInfo);

	public BlApplyVO selectBlApplyT86(TestssVO parameters);

	public ArrayList<HashMap<String, String>> selectHawbListSavedT86(HashMap<String, Object> parameters);

	public int selectMatchHawbNoCnt(String hawbNo);

	public ArrayList<NationVO> selectUserNationCode(String username);

	public ArrayList<HashMap<String, Object>> selectCurrencyListByDstnNation(HashMap<String, Object> parameters);

	public ArrayList<NationVO> selectUserDstnNationList(String userId);

	public BlApplyVO selectBlApplyFB(TestssVO parameters);

	public Object updateTmpOrderStatus(String nno);

	public ArrayList<String> selectYslOrderList();

	public ArrayList<HashMap<String, Object>> selectPdfPrintItemInfo(String nno);

	public HashMap<String, Object> selectPdfPrintInfoSimple(String string);

	public HashMap<String, Object> selectMatchCarriers(String hawbNo);

	public String selectUploadDate(String hawbNo);

	public void insertDeliveryInfo(HashMap<String, Object> parameters);

	public String selectAgencyBl(HashMap<String, Object> parameters);

	public BlApplyVO spTransBl(HashMap<String, Object> parameters);

	public void insertSubTransCode(HashMap<String, Object> parameters);

	public String selectSubTransCode(String hawbNo);

	public ArrayList<HashMap<String, Object>> selectReturnMemoList(HashMap<String, Object> parameters);

	public void insertReturnCsMemo(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectRecentMemoInfo(HashMap<String, Object> parameters);

	public void updateReturnCsReadYn(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectReturnPrintInfo(String nno);

	public void insertAraErrorMatch(HashMap<String, Object> araErrorParams);

	public String selectTrackingNoYT(String hawbNo);

	public void insertTbErrorMatch(HashMap<String, Object> parameterInfo);

	public int selectShopifyOrderCnt(String orderNno);

	public ManagerVO selectUserInfoByUserId(HashMap<String, Object> parameters);

	public ArrayList<ExportDeclareVO> selectExportDeclareData(String mawbNo);

	public void deleteExportDeclareInfo(UserOrderListVO userOrderList);
	
	public int selectExportDeclareInfoCnt(ExportDeclare expInfo);

	public void updateExportDeclareInfo(ExportDeclare expInfo);

	public void insertExportDeclareInfo(ExportDeclare expInfo);

	public ArrayList<UserOrderListVO> selectYslData();

	public ArrayList<UserOrderListVO> selectMawbOrderInfo(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectAgencyWeight();

	public ArrayList<String> selectYunChangeNno();

	public void updateYunChangeHawbNo(HashMap<String, Object> params);
	
	public ArrayList<HashMap<String, Object>> selectViewDeliveryInfo(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectFastboxWeightLog();

	public ArrayList<ShpngListVO> selectExcelOrderList(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectCurrencyListUse();

	public ArrayList<HashMap<String, String>> selectTransCodeList(String orgStation);

}


