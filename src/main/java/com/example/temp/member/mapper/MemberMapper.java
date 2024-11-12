package com.example.temp.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.member.vo.AllowIpVO;
import com.example.temp.member.vo.ExcelMatchingVO;
import com.example.temp.member.vo.KukkiwonVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.UserVO;

@Repository
@Mapper
public interface MemberMapper {

	public String getSelectTest() throws Exception;

	public void insertTest() throws Exception;

	public ArrayList<String> selectUserOrgNation(String userId) throws Exception;

	public ArrayList<UserTransComVO> selectUserDstnNation(String userId) throws Exception;

	public UserVO selectUserInfo(String userId) throws Exception;

	public void deleteUserTrkCom(String userId) throws Exception;

	public void insertUserTransCom(HashMap<String,String> trkInfo) throws Exception;

	public void updateUserInfo(UserVO userInfoVO) throws Exception;

	public void insertUserOrderList(UserOrderListVO userOrderList) throws Exception;

	public void insertUserOrderItem(UserOrderItemVO orderItem) throws Exception;

	public ArrayList<UserTransComVO>  userTransCom(HashMap<String, String> params) throws Exception;

	public ArrayList<ShpngListVO> selectShpngList(HashMap<String, Object> parameter) throws Exception;

	public UserOrderListVO selectUserForExcel(String userId) throws Exception;

	public void insertUserOrderListTemp(UserOrderListVO userOrderExcelList) throws Exception;

	public void insertUserOrderItemTemp(UserOrderItemVO userOrderExcelItem) throws Exception;

	public int confirmOrderList(UserOrderListVO userOrderExcelList) throws Exception;

	public int confirmOrderListTemp(UserOrderListVO userOrderExcelList) throws Exception;
	
	public void insertUserOrderItemListTemp(List<UserOrderItemVO> userOrderExcelItemList) throws Exception;

	public UserOrderListVO selectUserOrderOne(String nno) throws Exception;

	public ArrayList<UserOrderItemVO> selectUserOrderItemOne(UserOrderListVO userOrder) throws Exception;

	public void updateUserOrderListTemp(UserOrderListVO userOrderList) throws Exception;

	public void updateUserOrderItemTemp(UserOrderItemVO orderItem) throws Exception;

	public ArrayList<ShpngListVO> selectRegistOrderList(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<UserOrderItemVO> selectUserRegistOrderItemOne(UserOrderListVO userOrder) throws Exception;

	public ArrayList<String> selectNationArrayToStation(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectNnoList(HashMap<String, Object> parameterInfo) throws Exception;

	public InspStockListVO selectInspStockOrderInfo(String string) throws Exception;

	public ArrayList<InspStockListVO> selectInspStockList(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectFilePath(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception;

	public void insertMsgInfo(StockMsgVO msgInfo) throws Exception;

	public ArrayList<UserTransComVO> selectTrkComList(String userId) throws Exception;

	public ArrayList<UserTransComVO> selectDstnTrans(String userId) throws Exception;

	public ArrayList<UserTransComVO> selectUserOrgTrans(String userId) throws Exception;

	public ArrayList<SendVO> selectSendList(SendVO search) throws Exception;
	
	public ArrayList<SendVO> selectUnSendList(SendVO search) throws Exception;

	public void updateUserOrderList(UserOrderListVO userOrderList) throws Exception;

	public ArrayList<AllowIpVO> selectAllowIpList(String userId) throws Exception;

	public void insertAllowIp(HashMap<String, String> parameters) throws Exception;

	public void deleteAllowIp(HashMap<String, String> parameters) throws Exception;

	public String selectApiKey(String userId) throws Exception;

	public void insertApiKey(@Param("apiKey")String apiKey, @Param("userId")String userId) throws Exception;

	public int selectTotalCountStock(HashMap<String, Object> parameterInfo)  throws Exception;

	public void updateInvUserInfo(InvUserInfoVO invUserInfo) throws Exception;

	public void updateUserOrderListHawb(String nno) throws Exception;

	public ArrayList<ShpngListVO> selectSendBeforeOrderList(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<ShpngListVO> selectSendAfterOrderList(HashMap<String, Object> parameter) throws Exception;

	public void updateItemTrkNo(@Param("trkNo")String trkNo, @Param("nno")String nno, @Param("subNo")String subNo) throws Exception;

	public int selectSendListCount(SendVO search) throws Exception;

	public int selectUnSendListCount(SendVO search) throws Exception;

	public ArrayList<String> selectErrorList(String nno) throws Exception;

	public ArrayList<String> selectErrorItem(@Param("nno")String nno, @Param("subNo")String subNo) throws Exception;

	public String selectNationCode(String nationEname) throws Exception;

	public String selectTransCom(HashMap<String, Object> parameters1) throws Exception;

	public HashMap<String, Object> selectTransIdx(String idx) throws Exception;

	public int selectShpngListCount(HashMap<String, Object> parameter) throws Exception;

	public String selectTransCodeFromBigo(String transName)throws Exception;

	public int selectRegistOrderCount(HashMap<String, Object> parameter) throws Exception;

	public ArrayList<ShpngListVO> selectSendErrorOrderList(HashMap<String, Object> parameter) throws Exception;

	public String selectTransCodeFromNNO(String nno) throws Exception;

	public void deleteOrderListByNno(String nno) throws Exception;
	
	public void updateErrorStatus(HashMap<String, String> parameters) throws Exception;

	public HashMap<String, String> selectExcelColumn(String transCode) throws Exception;

	public ArrayList<HashMap<String,String>> selectExcelColumnExplain() throws Exception;

	public UserOrderItemVO selectUserRegistOrderItemOneForYslItem(UserOrderItemVO orderItems) throws Exception;

	public void updateErrorTmp(String userId) throws Exception;

	

	public ExcelMatchingVO selectExcelMatching(HashMap<String, Object> matchingParam) throws Exception;

	public ArrayList<String> selectOrgNationOption(String userId) throws Exception;

	public ArrayList<HashMap<String,Object>> selectOrgStationOption(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectDstnNationOption(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectTransCodeOption(HashMap<String, Object> parameters) throws Exception;

	public void insertUploadSet(HashMap<String, Object> parameters) throws Exception;

	public void deleteUploadSet(HashMap<String, Object> parameters) throws Exception;

	public void deleteUploadColumn(HashMap<String, Object> columnInfo) throws Exception;

	public void insertUploadColumn(HashMap<String, Object> columnInfo) throws Exception;

	public ArrayList<ShpngListVO> selectShpngListAll(HashMap<String, Object> parameter) throws Exception;

	public HashMap<String, Object> selectUserTransInfo(String idx) throws Exception;

	public HashMap<String, Object> selectUserDeposit(String userId);

	public UserVO selectUserPwCheck(String userId);

	public int selectUserPassword(ManagerVO userInfo);

	public ArrayList<String> selectErrorMatch(String nno);

	public ArrayList<HashMap<String, Object>> selectHsCodeList() throws Exception;

	public void deleteOrderErrorMatch(String nno);

	public ArrayList<HashMap<String, Object>> selectUserCsList(HashMap<String, Object> parameterInfo);

	public SendVO selectSendListOrderInfo(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectSendListOrderItems(HashMap<String, Object> params);

	public ArrayList<OrderInspListVO> selectSendExcelOrderInfo(HashMap<String, Object> params);

	public ArrayList<String> selectSendNnoList(HashMap<String, Object> params);

	public String selectSagawaNo(HashMap<String, Object> parameters);

	public CommercialVO selectCommercialInfo(String string);

	public String selectUploadDate(String hawbNo);

	public ArrayList<CommercialItemVO> selectCommercialItem(String string);

	public void updateEshopInfo(HashMap<String, Object> parameters);

	public void insertKukkiwonList(KukkiwonVO kukkiwonVO);

	public ArrayList<HashMap<String, Object>> selectDanList(HashMap<String, Object> parameters);

	public int selectAllOrderListCount(HashMap<String, Object> parameters);

	public ArrayList<SendVO> selectAllOrderList(HashMap<String, Object> parameters);
	
	public ArrayList<SendVO> selectAllExcelOrderList(HashMap<String, Object> parameters);

	public void insertIossNoInfo(HashMap<String, Object> iossInfo);

	public int selectShopeeAuthCnt(HashMap<String, Object> params);

	public String selectShopeeAuthExpiryDate(HashMap<String, Object> params);

	public void updateInspItemTrkNo(HashMap<String, Object> parameters);

	public ArrayList<CommercialItemVO> selectCommercialTakeinItem(String string);

}
