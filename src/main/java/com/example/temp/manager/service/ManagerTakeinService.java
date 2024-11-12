package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.manager.vo.takein.StockVO;
import com.example.temp.manager.vo.takein.TakeInStockVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.manager.vo.takein.TakeinItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderListVO;
import com.example.temp.manager.vo.takein.TakeinOutStockVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.RackVO;
import com.example.temp.manager.vo.StockCheckVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.stockvo.GroupStockDetailVO;
import com.example.temp.manager.vo.stockvo.GroupStockInfoVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;


@Service
public interface ManagerTakeinService {

	public HashMap<String, Object> insertTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinInfoVO> takeinInfoList(HashMap<String, Object> parameterInfo) throws Exception;

	public int takeinInfoTotalCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public TakeinInfoVO selectTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> updateTakeinAppv(HashMap<String, Object> parameterInfo) throws Exception;

	public TakeinInfoVO selectTakeInCode(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertMangerTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> updateMangerTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertManagerTakeinStockIn(HashMap<String, Object> parameterInfo) throws Exception;

	public int takeinItemTotalCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinItemVO> takeinItemList(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderListVO> selectTakeinOrderList(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItem(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectTakeinHawbChk(HashMap<String, Object> parameterInfo) throws Exception;

	public TakeinOrderListVO selectTakeinOrder(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertManagerTmpTakeinStockOut(HashMap<String, Object> parameterInfo) throws Exception;

	public void deleteTmpStockOut(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> managerTakeStockHawbin(HashMap<String, Object> parameterInfo) throws Exception;

	public void managerTakeVolumeIn(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<StockVO> selectTakeInStockByGrpIdx(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOutStockVO> selectTakeinOutStock(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeInStockVO> selectTakeinStockGroupIdx(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectTakeinOrderListCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectWorkCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectTakeinItemInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> updateManagerTakeinStockUp(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectSpUserCusItemCHK(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectUserCusItemList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectUserIdCHK(HashMap<String, Object> parameterInfo) throws Exception;

	public String seletTakeInNno() throws Exception;

	public void insertTbTakeinEtcOrderItem(HashMap<String, Object> parameterInfo) throws Exception;

	public void insertTbTakeinEtcOrder(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectEtcOrderList(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectEtcOrderListCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectTakeInEtcOrder(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectEtcItems(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertManagerTmpTakeinEtcStockOut(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> SpTakeinEtcOut(HashMap<String, Object> parameterInfo) throws Exception;

	public void updateEtcOrder(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectTakeinOrderItemCnt(Object nno) throws Exception;

	public ArrayList<TransComVO> selectTakeinOrderListTransCode(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String,Object>> selectStockByNno(String string) throws Exception;

	public ArrayList<String> selectStockList(HashMap<String, Object> itemInfoList) throws Exception;

	public ArrayList<HashMap<String, Object>> selectTakeinOrderItemCntBatch(Object nno) throws Exception;

	// 사입 삭제 추가
	public void deleteTakeinOrder(String[] nnoList, String[] targetUserList, String username, String remoteAddr);

	public void insertUserTakeinOrderList(HttpServletRequest request, HashMap<String, String> parameters);

	public ArrayList<TmpTakeinOrderVO> selectTmpTakeinUserList(HashMap<String, Object> parameterInfo);

	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem(HashMap<String, Object> itemParams);

	public int selectTakeinOrderListCntTmp(HashMap<String, Object> parameterInfo);

	public void deleteTakeinOrderTmp(HashMap<String, String> deleteNno);

	public ArrayList<HashMap<String, Object>> getTakeinCusInfo();

	public TakeinOrderListVO selectTakeinOrderTmpList(HashMap<String, Object> parameterInfo);

	public ArrayList<TmpTakeinOrderItemVO> selectTakeinOrderItemTmpList(HashMap<String, Object> parameterInfo);

	public TmpTakeinOrderVO selectTakeinOrderInfo(HashMap<String, Object> parameterInfo);

	public ArrayList<TmpTakeinOrderItemVO> selectTakeinOrderItemInfo(HashMap<String, Object> parameterInfo);

	// 사입 랙 관리
	public int selectTakeinRackListCount(HashMap<String, Object> parameterInfo);
	public ArrayList<RackVO> selectTakeinRackList(HashMap<String, Object> parameterInfo);

	public HashMap<String, Object> selectOrderByCnt(HashMap<String, Object> parameterInfo);

	public void insertRackInfo(RackVO rackInfo);

	public HashMap<String, Object> selectRackInfo(HashMap<String, Object> parameterInfo);

	public ArrayList<RackVO> selectTakeinRackListOrderBy(HashMap<String, Object> parameterInfo);

	public void updateTakeinRackInfo(HashMap<String, Object> params);

	public void deleteTakeinRackInfo(HttpServletRequest request, HttpServletResponse response, String targetParm);

	public int selectRackCodeCnt(String rackCode);

	public int selectRackStockListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<RackVO> selectRackStockList(HashMap<String, Object> parameterInfo);

	public ArrayList<RackVO> selectStockInfo(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectRackCodeList(HashMap<String, Object> parameterInfo);

	public RackVO selectMoveStockInfo(HashMap<String, Object> parameterInfo);

	public int selectRackCode(String parameter);

	public void updateRackStockInfo(HashMap<String, Object> parameterInfo);

	public int selectCheckRackListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<RackVO> selectCheckRackList(HashMap<String, Object> parameterInfo);

	public int selectStockCheckListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<StockCheckVO> selectStockCheckList(HashMap<String, Object> parameterInfo);

	public void insertStockCheckInfo(StockCheckVO stockCheckVO);

	public void deleteStockCheck(String targetParm);

	public int selectCheckStockListCount(HashMap<String, Object> parameterInfo);

	public void checkStockUpdate(HashMap<String, Object> parameterInfo);

	public void checkStockInsert(HashMap<String, Object> parameterInfo);

	public RackVO selectStockInfoMoved(HashMap<String, Object> parameterInfo);

	public int selectRackCodeCheckStock(HashMap<String, Object> parameterInfo);

	public void updateRackMoveAndStockCheck(HashMap<String, Object> parameterInfo);

	public void moveRack(HashMap<String, Object> parameterInfo);

	public void deleteOriginalRackStockCheck(HashMap<String, Object> parameterInfo);

	public void insertRackMoveAndStockCheck(HashMap<String, Object> parameterInfo);

	public ArrayList<RackVO> selectStockListByRackCode(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectGEA1101();

	public ArrayList<CurrencyVO> selectCurencyList(HashMap<String, Object> parameterInfo);

	public ArrayList<TakeinOrderListVO> selectTakeinOrderListByCancel(HashMap<String, Object> params);

	public int selectTakeinOrderListByCancelCnt(HashMap<String, Object> params);

	public String execTakeinOrderDel(HttpServletRequest request, String targetHawb);

	public HashMap<String, Object> selectEtcOrderInfo(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectEtcOrderItemInfo(HashMap<String, Object> params);

	public void updateTbTakeinEtcOrder(HashMap<String, Object> params);

	public void deleteTbTakeinEtcOrderItem(HashMap<String, Object> params);

	public int selectTbEtcCnt(HashMap<String, Object> parameterInfo);

	public void SpTakeinEtcCancel(HashMap<String, Object> parameterInfo);

	public void SpStockOutCancel(HashMap<String, Object> params);

	public void takeinEmsOrderExcelDown(String[] nnoList, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public HashMap<String, Object> selectEmsBlInfo(HashMap<String, Object> params) throws Exception;

	public void updateTakeinAgencyBl(HashMap<String, Object> params) throws Exception;

	public ArrayList<HashMap<String, Object>> selectTakeinTransCodeList(HashMap<String, Object> parameter) throws Exception;

	public void takeinEmsOrderExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameter) throws Exception;

	public String updateEmsBl(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, Object> params) throws Exception;

	public void updateStockOut(HashMap<String, Object> params) throws Exception;

	public void takeinOrderListAllExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public byte[] selectAllTakeinOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public HashMap<String, Object> insertManagerTmpTakeinStockOutV2(HashMap<String, Object> parameterInfo) throws Exception;

	public void updateManuDate(HashMap<String, Object> params) throws Exception;

	public HashMap<String, Object> execSpWhoutStockReset(HashMap<String, Object> params) throws Exception;

	public void updateUserOrderList(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList);

	public ArrayList<String> selectTakeinOutStockUserList(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderListVO> selectTakeinOrderListNew(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItemNew(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectTakeinOrderListCntNew(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> spWhoutStockTakeinBatch(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderListVO> selectTakeinOrderListDev(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItemDev(HashMap<String, Object> parameterInfo) throws Exception;

}
