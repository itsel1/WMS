package com.example.temp.manager.mapper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.BizInfo;
import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.vo.CusItemVO;
import com.example.temp.manager.vo.DlvChgInfoVO;
import com.example.temp.manager.vo.DlvPriceVO;
import com.example.temp.manager.vo.ExpLicenceExcelVO;
import com.example.temp.manager.vo.ExpLicenceListVO;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.HawbChkVO;
import com.example.temp.manager.vo.HawbListVO;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.ManifastVO;
import com.example.temp.manager.vo.ManifestVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderInspListOneVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.OrderListVO;
import com.example.temp.manager.vo.OrderRcptVO;
import com.example.temp.manager.vo.OrderWeightVO;
import com.example.temp.manager.vo.PriceVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StationDefaultVO;
import com.example.temp.manager.vo.StockAllVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.StockOutVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.VolumeVO;
import com.example.temp.manager.vo.WhNoticeFileVO;
import com.example.temp.manager.vo.WhNoticeVO;
import com.example.temp.manager.vo.insp.InnerProductVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UpdateOrderItemVO;
import com.example.temp.member.vo.UpdateOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;


@Repository
@Mapper
public interface ManagerMapper {
	public ArrayList<ManagerVO> getTest() throws Exception;

	public ArrayList<ManagerVO> getSelectInfo(String value) throws Exception;

	public int getTotalCountInfo(HashMap<String, Object> infoMap) throws Exception;

	public ArrayList<ManagerVO> getSelectUserList(HashMap<String, Object> testMap) throws Exception;

	public void insertMemberInfos(ManagerVO userInfoVO) throws Exception;

	public ManagerVO getSelectUserInfo(String userId) throws Exception;

	public void updateUserInfo(ManagerVO userInfoVO) throws Exception;

	public ArrayList<OrderInspListVO> selectOrderInspc() throws Exception;

	public int selectOrderInspcCnt(HashMap<String, Object> orderInfo) throws Exception;

	public ArrayList<OrderInspListOneVO> selectOrderInspc(HashMap<String, Object> orderInfo) throws Exception;

	public ArrayList<MawbVO> selectMawbList(String orgStation) throws Exception;

	public void insertMawb(MawbVO mawbVO) throws Exception;

	public int existMawb(String mawbNo) throws Exception;

	public void updateMawb(MawbVO mawbVO) throws Exception;

	public ArrayList<StockResultVO> selectTestSS() throws Exception;

	public ArrayList<MawbVO> selectMawbList2(MawbVO mawbVO) throws Exception;

	public ArrayList<HawbVO> selectHawbList(String mawbNo) throws Exception;

	public void deleteMawbOne(String mawbNo) throws Exception;

	public void insertHawb(HawbVO hawbVo) throws Exception;

	public String hawbCount(String mawbNo) throws Exception;

	public void updateHawbMawb(MawbVO mawbVO) throws Exception;

	public void insertUserTransCom(HashMap<String,String> trkInfo) throws Exception;

	public void deleteUserTrkCom(String userId) throws Exception;

	public ArrayList<String> selectUserTrkNation(String userId) throws Exception;

	public ArrayList<OrderInspVO> selectOrderInspcItem(String targetNNO) throws Exception;

	public HawbVO selectHawbInfo(HawbVO hawbVo) throws Exception;

	public void insertHawbItem(OrderInspVO orderInspcVO) throws Exception;

	public void deleteUserInfo(String string) throws Exception;

	public void resetUserPw(ManagerVO userInfoVO) throws Exception;

	public ArrayList<TransComVO> selectTransCom() throws Exception;

	public void deleteTransCom(String parameter) throws Exception;

	public void updateTransCom(TransComVO transComVO) throws Exception;

	public void transComInsert(TransComVO transComVO) throws Exception;

	public void insertTransZone(ZoneVO zoneVO) throws Exception;

	public void selectTest(ZoneVO zoneVO) throws Exception;

	public void insertPrice(PriceVO priceVO) throws Exception;

	public int selectTotalCountNation(HashMap<String, Object> infoMap) throws Exception;

	public ArrayList<ZoneVO> selectRgstNationList(HashMap<String, Object> infoMap) throws Exception;

	public void updateTransZone(ZoneVO zoneVO) throws Exception;

	public int selectTotalCountZone(HashMap<String, Object> infoMap) throws Exception;

	public ArrayList<PriceVO> selectRgstZoneList(HashMap<String, Object> infoMap) throws Exception;

	public PriceVO selectZoneOne(PriceVO priceVO) throws Exception;

	public void updatePrice(PriceVO priceVO) throws Exception;

	public ArrayList<String> selectUserOrgNation(String userId) throws Exception;

	public ArrayList<String> selectUserDstnNation(String userId) throws Exception;

	public void insertNotice(NoticeVO noticeInfoVO) throws Exception;

	public ArrayList<ShpngListVO> selectShpngList(HashMap<String, Object> parameter) throws Exception;

	public void deleteOrderList(HashMap<String, String> deleteInfo) throws Exception;

	public ArrayList<ShpngListVO> selectDeleteList(HashMap<String, Object> params) throws Exception;

	public void recoveryOrderList(HashMap<String, String> recoverInfo) throws Exception;

	public ArrayList<StockAllVO> selectStockAllCol(HashMap<String,String> stockParameter) throws Exception;

	public void insertStockFile(StockAllVO stockAll) throws Exception;

	public void insertStockMsg(StockAllVO stockAll) throws Exception;

	public StockResultVO insertStock(StockAllVO stockAll) throws Exception;

	public ArrayList<StockAllVO> selectWhMemo(HashMap<String, String> stockParameter) throws Exception;

	public ArrayList<StockVO> selectStockByGrpIdx(String groupIdx) throws Exception;

	public void insertStockVolumn(StockAllVO stockAll) throws Exception;

	public ArrayList<UserOrderItemVO> selectUserRegistOrderItemOne(UserOrderListVO userOrder) throws Exception;

	public ArrayList<InspStockListVO> selectInspStockList(HashMap<String, Object> parameterInfo) throws Exception;
	
	public String selectAdminStation(String userId) throws Exception;

	public InspStockListVO selectInspStockOrderInfo(String nno) throws Exception;

	public ArrayList<String> selectNnoList(HashMap<String,Object> parameters) throws Exception;

	public ArrayList<String> selectFilePath(HashMap<String, Object> parameters) throws Exception;

	public void execStockDel(HashMap<String, String> cancleInspInfo) throws Exception;

	public StationDefaultVO selectStationDefaultCond(String userId) throws Exception;

	public HawbChkVO execNomalHawbChk(HawbVO hawbVo) throws Exception;

	public ProcedureVO execNomalHawbIn(OrderRcptVO orderRcpt) throws Exception;

	public ProcedureVO execNomalHawbVolume(VolumeVO volume) throws Exception;

	public void deleteVolume(String nno) throws Exception;

	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception;

	public void insertMsgInfo(StockMsgVO msgInfo) throws Exception;

	public ArrayList<HawbListVO> selectRegistHawbList(@Param("wUserId")String wUserId, @Param("transCode")String transCode) throws Exception;

	public ProcedureVO insertHawbMawb(HashMap<String, String> parameters) throws Exception;

	public ProcedureVO execMawbCancle(HashMap<String, String> parameters) throws Exception;

	public ArrayList<OrderListVO> selectOrderRcptList(OrderListVO parameters) throws Exception;

	public ProcedureVO execOrderRcptList(HashMap<String, String> parameters) throws Exception; 

	public ArrayList<ManifastVO> selectManifastMawb(String mawbNo) throws Exception;

	public ArrayList<SendVO> selectSendList(SendVO parameters) throws Exception;
	
	public ArrayList<SendVO> selectUnSendList(SendVO parameters) throws Exception;

	public ArrayList<StockResultVO> selectStockResultVO(@Param("groupIdx")String parameter, @Param("nno")String parameter2) throws Exception;

	public void insertTmpExcelVolume(OrderWeightVO orderWeightExcelVO) throws Exception;

	public ArrayList<OrderWeightVO> selectOrderWeightExcel(String orgStation) throws Exception;

	public ProcedureVO execOrderWeightList(HashMap<String, String> parameters) throws Exception;

	public void delOrderWeightList(HashMap<String, String> parameters) throws Exception;

	public ArrayList<NationVO> selectUserDstnNationInfo(String orgStation) throws Exception;

	public CusItemVO selectDefaultInfo(String cusItemCode) throws Exception;

	public StockVO selectStockByNo(@Param("stockNo")String stockNo, @Param("nno")String nno, @Param("alreadyInfo")String alreadyInfo) throws Exception;

	public String selectChkCntStock(String nno) throws Exception;

	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<OrderInspListVO> selectOrderInspcList(HashMap<String, Object> orderInfo)throws Exception;

	public ProcedureVO execSpWhoutStock(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<InspStockOutVO> selectStockOutTarget(HashMap<String, Object> parameter) throws Exception;

	public String selectTargetSubNo(String stockNo) throws Exception;

	public void deleteStockOut(HashMap<String, Object> parameter) throws Exception;

	public ProcedureVO execStockHawbIn(HashMap<String, String> stockInParam) throws Exception;

	public String selectUserTransComByNno(String nno) throws Exception;

	public ApiOrderListVO selectOrderListAramex(String nno) throws Exception;

	public ArrayList<ApiOrderItemListVO> selectOrderItemAramex(String nno) throws Exception;

	public String selectHawbNnoCheck(String nno) throws Exception;

	public void deleteInspcStockOutCzFail(String nno) throws Exception;

	public StockVO selectStockByNo2(String stockNo) throws Exception;
 
	public ProcedureVO execAddBlApply(@Param("nno")String nno, @Param("userId")String userId, @Param("userIp")String userIp) throws Exception;

	public String selectStockMaxNo() throws Exception;

	public void insertStockUnRgt(HashMap<String, String> tempMap) throws Exception;

	public String selectGroupIdx() throws Exception;

	public void insertUnStockMsg(HashMap<String, String> tempMap) throws Exception;

	public void insertUnRgtStockFile(HashMap<String, String> tempMap) throws Exception;

	public ArrayList<ApiOrderItemListVO> selectOrderItemAramexMember(String nno)  throws Exception;

	public ApiOrderListVO selectOrderListAramexMember(String nno) throws Exception;

	public ArrayList<String> selectTransCodeForAdmin(String adminId) throws Exception;

	public ArrayList<InnerProductVO> selectInnerProductList(HashMap<String,String> parameters) throws Exception;

	public void updateInvUserInfo(InvUserInfoVO invUserInfo) throws Exception;

	public void updateInnerProductList(HashMap<String, String> parameters) throws Exception;

	public HawbVO selectFtpCount(String orgStation) throws Exception;

	public ArrayList<InnerProductVO> selectInnerProductListAll(HashMap<String, String> parameters) throws Exception;

	public int selectShpngListCount(HashMap<String, Object> parameter)  throws Exception;
	
	public void updateItemTrkNo(@Param("trkNo")String trkNo, @Param("nno")String nno, @Param("subNo")String subNo) throws Exception;

	public ArrayList<InspStockOutVO> selectStockRackInfo(HashMap<String, Object> parameter) throws Exception;

	public int selectSendListCount(SendVO search) throws Exception;

	public int selectUnSendListCount(SendVO search) throws Exception;

	public String selectTransCodeInStock(String nno) throws Exception;

	public OrderListOptionVO selectOrderListOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public OrderItemOptionVO selectOrderItemOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;
	
	public OrderListExpOptionVO selectExpressListOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public OrderItemExpOptionVO selectExpressItemOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public void insertOrderListOption(OrderListOptionVO optionOrderVO) throws Exception;

	public void insertOrderItemOption(OrderItemOptionVO optionItemVO) throws Exception;

	public void insertOrderListExpOption(OrderListExpOptionVO expressOrderVO) throws Exception;

	public void insertOrderItemExpOption(OrderItemExpOptionVO expressItemVO) throws Exception;

	public void deleteOrderListOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public void deleteOrderpItemOtion(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public void deleteOrderListExpOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public void deleteOrderItemExpOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public ArrayList<String> selectOrderListOptionNation(String targetCode) throws Exception;

	public int selectCountOrderListOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;
	
	public int selectCountOrderItemOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;
	
	public int selectCountExpressListOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;
	
	public int selectCountExpressItemOption(@Param("targetCode")String targetCode, @Param("dstnNation")String dstnNation) throws Exception;

	public String selectTransCodeByNNO(String nno) throws Exception;

	public ArrayList<ExpLicenceExcelVO> selectExcelLicence(String nno) throws Exception;

	public void updateLicenceInfo(HashMap<String, String> expLicenceIn) throws Exception;

	public String selectOrderNoFromNNO(String nno) throws Exception;

	public ArrayList<ExpLicenceListVO> selectLicenceList(ExpLicenceListVO parameters) throws Exception;

	public ArrayList<HawbVO> selectExpFieldList(String mawbNo) throws Exception;

	public HawbVO selectExpLicenceCount(String orgStation) throws Exception;

	public ArrayList<OrderInspListVO> selectorderListByMawb(@Param("mawbNo")String mawbNo) throws Exception;

	public ArrayList<ExpLicenceExcelVO> selectExcelLicenceEFS(String string) throws Exception;

	public StockResultVO execStockAddInsert(StockAllVO stockAll) throws Exception;

	public String selectNNOByHawbNo(String hawbNo) throws Exception;

	public String selectExpCom(String hawbNo) throws Exception;

	public int expLicenceChk(HawbVO hawbVo) throws Exception;

	public ArrayList<String> selectNotSendEfsInfo() throws Exception;

	public StockVO selectStockByStockNo(String stockNo) throws Exception;

	public StockResultVO selectStockResultStockVO(String stockNo) throws Exception;

	public int selectStockYnByNNO(String nno) throws Exception;

	public ArrayList<String> selectDstnList(@Param("userId")String userId, @Param("orgNation")String _orgNation) throws Exception;

	public void insertDlvPrice(DlvPriceVO dlvPriceDetail) throws Exception;

	public ArrayList<DlvPriceVO> selectInvoiceName() throws Exception;

	public ArrayList<NationVO> selectStationInfo() throws Exception;

	public ArrayList<String> selectMenualDstnList(@Param("userId")String userId, @Param("orgStation")String orgStation) throws Exception;

	public void updateHawbMawb2(String hawbNo) throws Exception;

	public void insertCustomerChgInfo(HashMap<String, Object> parameters) throws Exception;

	public void updateCustomerChgInfo(HashMap<String, Object> parameters) throws Exception;
	
	public void deleteCustomerChgInfo(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<DlvChgInfoVO> selectDlvChgInfo(@Param("userId")String userId, @Param("orgStation")String orgStation) throws Exception;

	public void insertCustomerChgInfoIndividual(HashMap<String, Object> parameters) throws Exception;

	public void resetCustomerChgInfo(HashMap<String, Object> parameters) throws Exception;

	public void updateCustoerInvoice(HashMap<String, Object> parameters) throws Exception;

	public void updateCustomerEtcFee(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<HashMap<String, Object>> selectHawbTrans(String orgStation) throws Exception;

	public String selectSekoHawb(String hawbNo) throws Exception;

	public ArrayList<HawbVO> selectHawbListArr(String mawbNo) throws Exception;

	public ArrayList<MawbVO> selectMawbList3(MawbVO parameters) throws Exception;

	public void insertMawbArr(HawbVO hawbVo) throws Exception;

	public HashMap<String, Object> selectMawbArrInfo(String hawbNo) throws Exception;
	  
	public void updateMawbArr(HashMap<String, Object> mawbArrInfo) throws Exception;

	public String selectUdate(HashMap<String, Object> mawbArrInfo) throws Exception;

	public HashMap<String, Object> checkMawbArr(String hawbNo) throws Exception;

	public int expLicenceChk2(String hawbNo) throws Exception;

	public ArrayList<String> selectDistinctBlList() throws Exception;

	public HashMap<String,Object> selectPerDimByHawb(String hawbNo) throws Exception;

	public void insertWebHookList(HashMap<String, Object> webHookList) throws Exception;

	public void deleteWebHookList(String string) throws Exception;

	public void updateExpLicenceInfo(String hawbNo) throws Exception;

	public void updateLicenceInfoFail(HashMap<String, String> expLicenceIn) throws Exception;

	public ArrayList<String> selectExpLicenceYsl() throws Exception;

	public String selectExpLicenceYslChk(String nno) throws Exception;

	// 출고 목록 추가 ---------------
	public ArrayList<StockOutVO> selectStockOutList(HashMap<String, Object> parameterInfo);

	public int selectStockOutListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> stockOutListByMonth(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectStockOutByUserId(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectStationCodeList(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectStockOutListChart(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectStockOutListChart(StockOutVO vo);

	public ArrayList<StockOutVO> selectStockOutListExcel(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> stockOutListByMonthExcel(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> stockOutListByMonthExcel2(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectStockOutByUserId2(HashMap<String, Object> parameterInfo);

	public String selectStationCode(String stationName);

	public ArrayList<StockOutVO> selectClickedChart(HashMap<String, Object> parameterInfo);

	public HashMap<String, Object> selectInvoiceInfo(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectUserStockOutList(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectTransCodeFilter(HashMap<String, Object> params);

	public int selectTransCodeTotalCnt(HashMap<String, Object> params);

	public int selectInBoundCnt(HashMap<String, Object> parameterInfo);

	public int selectOutBoundCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<StockOutVO> selectStockOutMonthlyList(HashMap<String, Object> params2);

	public ArrayList<HashMap<String, Object>> selectDailyChart(HashMap<String, Object> params);

	public String selectHawbNo(String value);

	public String selectNNOByStockNo(HashMap<String, Object> parameter);

	public ArrayList<SendVO> selectSendExcelList(HashMap<String, Object> params);

	public ArrayList<NoticeVO> selectNoticeList(HashMap<String, Object> infoMap);

	public int selectDepositPrice(String userId);

	public void insertDepositPrice(ManagerVO userInfoVO);

	public ArrayList<HashMap<String, Object>> selectMawbManifestList(HashMap<String, Object> infoMap);

	public int selectMawbManifestListCnt(HashMap<String, Object> infoMap);

	public ArrayList<UserOrderListVO> selectOrderInfo(HashMap<String, Object> params);

	public ArrayList<UserOrderItemVO> selectOrderItem(String nno);

	public double selectItemTotalAmount(String nno);

	public UserOrderListVO selectOrderInfoByNNO(HashMap<String, Object> params);

	public UserOrderItemVO selectOrderItemByNNO(HashMap<String, Object> params);

	public ArrayList<ManifestVO> selectOrderInfoMani(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectManiItem(String string);

	public void insertNewOrderInfo(UpdateOrderListVO updateOrderList);

	public void insertNewOrderItemInfo(UpdateOrderItemVO updateItemList);

	public ArrayList<UserOrderListVO> selectOrderListT86(HashMap<String, Object> params);

	public ArrayList<HawbListVO> selectType86HawbList(HashMap<String, Object> params);

	public int selectOrderListNewInfo(String nno);

	public ArrayList<HashMap<String, String>> selectBagNoList(String mawbNo);

	public ArrayList<HashMap<String, Object>> selectHawbListByBagNo(HashMap<String, Object> params);

	public String[] selectNnoListByMawbNo(String mawbNo);

	public ArrayList<UserOrderListVO> selectOrderInfoHis(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectChangeOrderList(String mawbNo);

	public ArrayList<UserOrderListVO> selectChangeOrderInfo(String nno);

	public ArrayList<LinkedHashMap<String, Object>> selectChangeItemInfo(String nno);

	public UserOrderListVO selectFirstOrderInfo(String nno);

	public ArrayList<LinkedHashMap<String, Object>> selectFirstOrderItemInfo(HashMap<String, Object> params);

	public ArrayList<String> selectExpLicenceFB();

	public int selectUserMallCnt(FastboxInfoVO fastboxInfo);

	public void updateUserMallInfo(FastboxInfoVO fastboxInfo);

	public void insertUserMallInfo(FastboxInfoVO fastboxInfo);

	public FastboxInfoVO selectUserMallInfo(String userId);

	public ArrayList<HashMap<String, Object>> selectOrderListForErr(HashMap<String, Object> params);

	public ArrayList<String> selectErrorMsgByNNO(String nno);

	public void deleteExpLicenceInfo(String nno);

	public void updateMawbHawb(HashMap<String, Object> params);

	public int selectRecoveryListCnt(HashMap<String, Object> params);

	//----------------s
	public void insertWhoutOrderIn(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectWhoutOrder(HashMap<String, Object> parameters);

	public void insertWhoutFile(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectWhoutFiles(HashMap<String, Object> parameters);

	public void deleteWhoutFile(HashMap<String, Object> parameters);

	public int insertWhNoticeIn(HashMap<String, Object> parameters);

	public void updateWhNoticeIn(HashMap<String, Object> parameters);

	public int selectNoticeListCnt(HashMap<String, Object> params);

	public HashMap<String, Object> selectNoticeInfo(int idx);

	public void insertReplyNotice(HashMap<String, Object> parameters);

	//----------------
	public int selectWhNoticeListCnt(HashMap<String, Object> params);

	public ArrayList<WhNoticeVO> selectWhNoticeList(HashMap<String, Object> params);

	public void insertWhNoticeInfo(HashMap<String, Object> parameters);

	public void updateWhNoticeGIdx(HashMap<String, Object> parameters);

	public void insertWhNoticeFile(HashMap<String, Object> parameters);

	public WhNoticeVO selectWhNoticeDetail(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectWhNoticeFileList(HashMap<String, Object> params);

	public int selectWhNoticeFileYn(HashMap<String, Object> params);

	public void noticeFileDel(HashMap<String, Object> params);

	public void updateWhNoticeInfo(HashMap<String, Object> parameters);

	public void insertWhNoticeReplyInfo(HashMap<String, Object> parameters);

	public HashMap<String, Object> execNoticeDel(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectWhNoticeFiles(int idx);

	public CommercialVO selectCommercialInfo(String string);

	public String selectUploadDate(String hawbNo);

	public ArrayList<CommercialItemVO> selectCommercialItem(String string);

	public ArrayList<UserOrderListVO> selectOrderListLabelChange(HashMap<String, Object> parameterInfo);

	public int selectOrderListLabelChangeCnt(HashMap<String, Object> parameterInfo);
	
	public ArrayList<HawbListVO> selectChangeLabelList(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectMonthReportHawbList(HashMap<String, Object> parameters);

	public Integer selectPerDimByHawbNo(String hawbNo);

	public HashMap<String, Object> selectWeightInfo(String nno);

	public int selectDeleteOrderListCnt(HashMap<String, Object> params);

	public ArrayList<ShpngListVO> selectDeleteOrderList(HashMap<String, Object> params);

	public HashMap<String, Object> selectExpBusinessInfo(String hawb);

	public ArrayList<UserOrderListVO> selectYunExpressOrderInfo(HashMap<String, Object> parameters);

	public ArrayList<UserOrderItemVO> selectYunExpressItemInfo(HashMap<String, Object> parameters);

	public ArrayList<ShpngListVO> selectYunExpOrderList(HashMap<String, Object> parameters);

	public UserOrderListVO selectYunExpOrderInfo(HashMap<String, Object> parameters);

	public void insertYunExpDeliveryInfo(HashMap<String, Object> parameters);

	public HashMap<String, Object> selectUserIdByNno(String nno);

	public String selectOrgStationByUserId(String userId);
	
	public HashMap<String, Object> selectCheckMawbNo(String mawbNo);

	public void updateMawbNo(HawbVO hawbVo);

	public void insertHisMawbApply(HawbVO hawbVo);

	public void updateBagNo(HashMap<String, Object> params);

	public ArrayList<ManagerVO> selectSendEmailreturnSeller();

	public void insertHisSendEmail(ArrayList<ManagerVO> userInfo);

	public ArrayList<BizInfo> selectBizInfoList(HashMap<String, Object> parameters);

	public int selectBizInfoListCnt(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectUserInfoList(HashMap<String, Object> parameters);

	public void insertBizInfo(BizInfo bizInfo);

	public BizInfo selectBizInfoOne(HashMap<String, Object> parameters);

	public void updateBizInfo(BizInfo bizInfo);

	public int expLicenceChkV2(HawbVO hawbVo);

	public UserOrderListVO selectExportOrderInfo(HawbVO hawbVo);

	public HashMap<String, Object> selectExportInfo(String nno);

	public ExpLicenceExcelVO selectExcelLicenceEFSV2(String nno);

	public void updateLicenceInfoV2(HashMap<String, String> expLicenceIn);

	public ArrayList<String> selectExpNoYsl();

	public ArrayList<String> selectExpNoFB();

	public HashMap<String, Object> selectMawbInfo(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectFastboxData();

	public ArrayList<HashMap<String, Object>> selectTransCodeList(HashMap<String, Object> parameterInfo);

	public int selectMawbApplyListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<SendVO> selectMawbApplyList(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectTaxTypeList();


}
