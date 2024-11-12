package com.example.temp.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.common.service.ComnService;
import com.example.temp.manager.mapper.ManagerTakeinMapper;
import com.example.temp.manager.service.ManagerTakeinService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.RackVO;
import com.example.temp.manager.vo.StockCheckVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.takein.StockVO;
import com.example.temp.manager.vo.takein.TakeInStockVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.manager.vo.takein.TakeinItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderListVO;
import com.example.temp.manager.vo.takein.TakeinOutStockVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.mapper.MemberTakeinMapper;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;

import oracle.net.aso.k;

@Service
@Transactional
public class ManagerTakeinServiceImpl implements ManagerTakeinService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberTakeinMapper memberTakeinMapper;
	
	@Autowired
	private ComnService commMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private ManagerTakeinMapper mapper;

	@Override
	public HashMap<String, Object> insertTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.insertTakeinInfo(parameterInfo);
	}

	@Override
	public ArrayList<TakeinInfoVO> takeinInfoList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.takeinInfoList(parameterInfo);
	}

	@Override
	public int takeinInfoTotalCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.takeinInfoTotalCnt(parameterInfo);
	}

	@Override
	public TakeinInfoVO selectTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinInfo(parameterInfo);
	}

	@Override
	public HashMap<String, Object> updateTakeinAppv(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.updateTakeinAppv(parameterInfo);
	}

	@Override
	public TakeinInfoVO selectTakeInCode(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeInCode(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertMangerTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.insertMangerTakeinInfo(parameterInfo);
	}

	@Override
	public HashMap<String, Object> updateMangerTakeinInfo(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.updateMangerTakeinInfo(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertManagerTakeinStockIn(HashMap<String, Object> parameterInfo) throws Exception {
 
		return mapper.insertManagerTakeinStockIn(parameterInfo);
	}

	@Override
	public int takeinItemTotalCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.takeinItemTotalCnt(parameterInfo);
	}

	@Override
	public ArrayList<TakeinItemVO> takeinItemList(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.takeinItemList(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderListVO> selectTakeinOrderList(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectTakeinOrderList(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItem(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectTakeinOrderItem(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectTakeinHawbChk(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectTakeinHawbChk(parameterInfo);
	}

	@Override
	public TakeinOrderListVO selectTakeinOrder(HashMap<String, Object> parameterInfo) throws Exception {
		
		return  mapper.selectTakeinOrder(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertManagerTmpTakeinStockOut(HashMap<String, Object> parameterInfo) throws Exception {
	
		return mapper.insertManagerTmpTakeinStockOut(parameterInfo);
	}

	@Override
	public void deleteTmpStockOut(HashMap<String, Object> parameterInfo) throws Exception {
		
		mapper.deleteTmpStockOut(parameterInfo);
	}

	@Override
	public HashMap<String, Object> managerTakeStockHawbin(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.managerTakeStockHawbin(parameterInfo);
	}

	@Override
	public void managerTakeVolumeIn(HashMap<String, Object> parameterInfo) throws Exception {
		mapper.managerTakeVolumeIn(parameterInfo);
	}

	@Override
	public ArrayList<StockVO> selectTakeInStockByGrpIdx(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectTakeInStockByGrpIdx(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOutStockVO> selectTakeinOutStock(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectTakeinOutStock(parameterInfo);
	}

	@Override
	public ArrayList<TakeInStockVO> selectTakeinStockGroupIdx(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinStockGroupIdx(parameterInfo);
	}

	@Override
	public int selectTakeinOrderListCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectTakeinOrderListCnt(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectWorkCnt(HashMap<String, Object> parameterInfo) throws Exception {		// 
		return  mapper.selectWorkCnt(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectTakeinItemInfo(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectTakeinItemInfo(parameterInfo);
	}

	@Override
	public HashMap<String, Object> updateManagerTakeinStockUp(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.updateManagerTakeinStockUp(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectSpUserCusItemCHK(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectSpUserCusItemCHK(parameterInfo);

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserCusItemList(HashMap<String, Object> parameterInfo) throws Exception {
	
		return mapper.selectUserCusItemList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectUserIdCHK(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectUserIdCHK(parameterInfo);
	}

	@Override
	public String seletTakeInNno() throws Exception {

		return  mapper.seletTakeInNno();
	}
	

	@Override
	public void insertTbTakeinEtcOrderItem(HashMap<String, Object> parameterInfo) throws Exception {
		 mapper.insertTbTakeinEtcOrderItem(parameterInfo);
		
	}
	
	@Override
	public void insertTbTakeinEtcOrder(HashMap<String, Object> parameterInfo) throws Exception {
		mapper.insertTbTakeinEtcOrder(parameterInfo);
		
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectEtcOrderList(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectEtcOrderList(parameterInfo);
	}

	@Override
	public int selectEtcOrderListCnt(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectEtcOrderListCnt(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectTakeInEtcOrder(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectTakeInEtcOrder(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectEtcItems(HashMap<String, Object> parameterInfo) throws Exception {
	
		return mapper.selectEtcItems(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertManagerTmpTakeinEtcStockOut(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.insertManagerTmpTakeinEtcStockOut(parameterInfo);
	}

	@Override
	public HashMap<String, Object> SpTakeinEtcOut(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.SpTakeinEtcOut(parameterInfo);
	}

	@Override
	public void updateEtcOrder(HashMap<String, Object> parameterInfo) throws Exception {
		
		mapper.updateEtcOrder(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTakeinOrderItemCnt(Object nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTakeinOrderItemCnt(nno);
	}

	@Override
	public ArrayList<TransComVO> selectTakeinOrderListTransCode(HashMap<String, Object> parameterInfo)  throws Exception{
		
		return mapper.selectTakeinOrderListTransCode(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String,Object>> selectStockByNno(String nno) throws Exception {
		return mapper.selectStockByNno(nno);
	}

	@Override
	public ArrayList<String> selectStockList(HashMap<String, Object> itemInfoList) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockList(itemInfoList);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTakeinOrderItemCntBatch(Object nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTakeinOrderItemCntBatch(nno);
	}

	// 사입 삭제 추가
	@Override
	public void deleteTakeinOrder(String[] nnoList, String[] targetUserList, String username, String remoteAddr) {
		HashMap<String, String> deleteInfo = new HashMap<String, String>();
		deleteInfo.put("userId", username);
		deleteInfo.put("userIp", remoteAddr);
		for (int i = 0; i < nnoList.length; i++) {
			deleteInfo.put("nno", nnoList[i]);
			mapper.deleteTakeinOrder(deleteInfo);
		}
		
	}

	/*
	@Override
	public void insertUserTakeinOrderList(HttpServletRequest request, HashMap<String, String> parameters) {
		
		String pattern = "^[0-9]*$"; //숫자만
		String patternFloat = "^[0-9]+(.[0-9])*$"; //숫자만


		try {
			HashMap<String, Object> transParameter = new HashMap<String, Object>();
		
			transParameter.put("userId", request.getParameter("userId"));
			transParameter.put("orgStation", request.getParameter("orgStation"));
			transParameter.put("dstnNation", request.getParameter("dstnNation"));
			
			CustomerVO customerInfo = new CustomerVO();
			
			HashMap<String, String> userId = new HashMap<String, String>();
			userId.put("userId", (String) request.getParameter("userId"));
			
			customerInfo = memberTakeinMapper.selectCustomInfo(userId);
			customerInfo.dncryptData();

			TmpTakeinOrderVO  takeinInfo = new TmpTakeinOrderVO();
			TmpTakeinOrderItemVO  takeinItem = new TmpTakeinOrderItemVO();
			
			String chkOrderNo = "";
			String chkNno = "";
			String chkOrgStaion="";
			String chkDstnStaion="";
			int setSubNo = 1;
			
			takeinInfo.setOrderNo(request.getParameter("orderNo"));
			takeinInfo.setOrderDate(request.getParameter("orderDate"));
			takeinInfo.setOrgStation(request.getParameter("orgStation"));
			takeinInfo.setDstnNation(request.getParameter("dstnNation"));
			takeinInfo.setShipperName(request.getParameter("shipperName"));
			takeinInfo.setShipperTel(request.getParameter("shipperTel"));
			takeinInfo.setShipperHp(request.getParameter("shipperHp"));
			takeinInfo.setShipperZip(request.getParameter("shipperZip"));
			takeinInfo.setShipperState(request.getParameter("shipperState"));
			takeinInfo.setShipperCity(request.getParameter("shipperCity"));
			takeinInfo.setShipperAddr(request.getParameter("shipperAddr"));
			takeinInfo.setShipperAddrDetail(request.getParameter("shipperAddrDetail"));
			takeinInfo.setUserEmail(request.getParameter("shipperEmail"));
			takeinInfo.setCneeName(request.getParameter("cneeName"));
			takeinInfo.setCneeTel(request.getParameter("cneeTel"));
			takeinInfo.setCneeHp(request.getParameter("cneeHp"));
			takeinInfo.setCneeEmail(request.getParameter("cneeEmail"));
			takeinInfo.setCneeZip(request.getParameter("cneeZip"));
			takeinInfo.setCneeCity(request.getParameter("cneeCity"));
			takeinInfo.setCneeState(request.getParameter("cneeState"));
			takeinInfo.setCneeAddr(request.getParameter("cneeAddr"));
			takeinInfo.setCneeAddrDetail(request.getParameter("cneeAddrDetail"));
			takeinInfo.setDlvReqMsg(request.getParameter("dlvReqMsg"));
			takeinInfo.setBuySite(request.getParameter("buySite"));
			takeinInfo.setWhReqMsg(request.getParameter("whReqMsg"));
			
			//takeinItem.setCusItemCode(request.getParameterValues("cusItemCode"));
			//takeinItem.setItemCnt(request.getParameterValues("itemCnt"));
		
			
			if(request.getParameter("unitValue").equals("")) {
				try {
					
					HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
					parameterInfo.put("userId", request.getParameter("userId"));
					parameterInfo.put("orgStation", request.getParameter("orgStation"));
					parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
	
					HashMap<String, String> unitValueRs = new HashMap<String, String>();
					unitValueRs = mapper.selectUserCusItemListTmp(parameterInfo);
					
					String unitValue = String.valueOf(unitValueRs.get("unitValue"));
					
					if (unitValue.equals("")) {
						takeinItem.setUnitValue("0");
					} else {
						takeinItem.setUnitValue(unitValue);
					}
					
				} catch (Exception e) {
					logger.error("Exception", e);
				}
				
			} else {
				takeinItem.setUnitValue(request.getParameter("unitValue"));
			}
			
			takeinInfo.setTransCode(commMapper.selectUserTransCode(transParameter));
			
			if (takeinInfo.getOrderNo().equals("") || takeinInfo.getOrderNo().equals(chkOrderNo)) {
			
				takeinInfo.setOrderNo(chkOrderNo);
				takeinInfo.setNno(chkNno);
				takeinInfo.setOrgStation(chkOrgStaion);
				takeinInfo.setDstnNation(chkDstnStaion);
				setSubNo++;
				
			} else {
			
				String newNno = "";
				newNno = memberTakeinMapper.selectNNO();
				takeinInfo.setNno(newNno);
				takeinInfo.setUserId(request.getParameter("userId"));
				takeinInfo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
				takeinInfo.setWUserIp(request.getRemoteAddr());
				takeinInfo.setOrderType("TAKEIN");
				takeinInfo.encryptData();
				
				HashMap<String, String> transComChangeParameters = new HashMap<String, String>();
				transComChangeParameters.put("nno", newNno);
				transComChangeParameters.put("orgStation", takeinInfo.getOrgStation());
				transComChangeParameters.put("dstnNation", takeinInfo.getDstnNation());
				transComChangeParameters.put("userId", request.getParameter("userId"));
				transComChangeParameters.put("transCode", takeinInfo.getTransCode());
				transComChangeParameters.put("wta", "1");
				transComChangeParameters.put("wtc", "1");
				transComChangeParameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
				transComChangeParameters.put("wUserIp", request.getRemoteAddr());
				HashMap<String, String> transComChangeRs = new HashMap<String, String>();
				transComChangeRs = commMapper.selectTransComChange(transComChangeParameters);
				takeinInfo.setTransCode(transComChangeRs.get("rstTransCode"));
				
				memberTakeinMapper.insertTmpTakeinOrder(takeinInfo);
				setSubNo = 1;
			}
			
			String getSubNo = Integer.toString(setSubNo);
			takeinItem.setOrgStation(takeinInfo.getOrgStation());
			takeinItem.setNno(takeinInfo.getNno());
			takeinItem.setSubNo(getSubNo);
			takeinItem.setUserId(request.getParameter("userId"));
			memberTakeinMapper.insertTmpTakeinOrderItem(takeinItem);
			chkOrderNo = takeinInfo.getOrderNo();
			chkNno = takeinInfo.getNno();
			chkOrgStaion = takeinInfo.getOrgStation();
			chkDstnStaion = takeinInfo.getDstnNation();
			
		} catch (Exception e) {
			logger.error("Exception", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
	}
	*/

	@Override
	public ArrayList<TmpTakeinOrderVO> selectTmpTakeinUserList(HashMap<String, Object> parameterInfo) {
		return mapper.selectTmpTakeinUserList(parameterInfo);
	}

	@Override
	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem(HashMap<String, Object> itemParams) {
		return mapper.selectTmpTakeinUserOrderItem(itemParams);
	}

	@Override
	public void insertUserTakeinOrderList(HttpServletRequest request, HashMap<String, String> parameters) {
		
		try {
			TmpTakeinOrderVO takeinInfo = new TmpTakeinOrderVO();
		
			TmpTakeinOrderItemVO takeinItem = new TmpTakeinOrderItemVO();
			
			HashMap<String, Object> transParameter = new HashMap<String, Object>();
			
			transParameter.put("userId", request.getParameter("userId"));
			transParameter.put("orgStation", request.getParameter("orgStation"));
			transParameter.put("dstnNation", request.getParameter("dstnNation"));
			
			CustomerVO customerInfo = new CustomerVO();
			
			HashMap<String, String> userId = new HashMap<String, String>();
			userId.put("userId", (String) request.getParameter("userId"));
			
			customerInfo = memberTakeinMapper.selectCustomInfo(userId);
			customerInfo.dncryptData();
	
			takeinInfo.setOrderNo(request.getParameter("orderNo"));
			takeinInfo.setOrderDate(request.getParameter("orderDate"));
			takeinInfo.setOrgStation(request.getParameter("orgStation"));
			takeinInfo.setDstnNation(request.getParameter("dstnNation"));
			takeinInfo.setShipperName(request.getParameter("shipperName"));
			takeinInfo.setShipperTel(request.getParameter("shipperTel"));
			takeinInfo.setShipperHp(request.getParameter("shipperHp"));
			takeinInfo.setShipperZip(request.getParameter("shipperZip"));
			takeinInfo.setShipperState(request.getParameter("shipperState"));
			takeinInfo.setShipperCity(request.getParameter("shipperCity"));
			takeinInfo.setShipperAddr(request.getParameter("shipperAddr"));
			takeinInfo.setShipperAddrDetail(request.getParameter("shipperAddrDetail"));
			takeinInfo.setUserEmail(request.getParameter("shipperEmail"));
			takeinInfo.setCneeName(request.getParameter("cneeName"));
			takeinInfo.setCneeTel(request.getParameter("cneeTel"));
			takeinInfo.setCneeHp(request.getParameter("cneeHp"));
			takeinInfo.setCneeEmail(request.getParameter("cneeEmail"));
			takeinInfo.setCneeZip(request.getParameter("cneeZip"));
			takeinInfo.setCneeCity(request.getParameter("cneeCity"));
			takeinInfo.setCneeState(request.getParameter("cneeState"));
			takeinInfo.setCneeAddr(request.getParameter("cneeAddr"));
			takeinInfo.setCneeAddrDetail(request.getParameter("cneeAddrDetail"));
			takeinInfo.setDlvReqMsg(request.getParameter("dlvReqMsg"));
			takeinInfo.setBuySite(request.getParameter("buySite"));
			takeinInfo.setWhReqMsg(request.getParameter("whReqMsg"));
			
			if (request.getParameter("buySite").equals("")) {
				takeinInfo.setBuySite(customerInfo.getStoreUrl());
			}
			
			takeinInfo.setTransCode(commMapper.selectUserTransCode(transParameter));
			
			String newNno = "";
			newNno = memberTakeinMapper.selectNNO();
			takeinInfo.setNno(newNno);
			takeinInfo.setUserId(request.getParameter("userId"));
			takeinInfo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
			takeinInfo.setWUserIp(request.getRemoteAddr());
			takeinInfo.setOrderType("TAKEIN");
			takeinInfo.encryptData();
			
			HashMap<String, String> transComChangeParameters = new HashMap<String, String>();
			transComChangeParameters.put("nno", newNno);
			transComChangeParameters.put("orgStation", takeinInfo.getOrgStation());
			transComChangeParameters.put("dstnNation", takeinInfo.getDstnNation());
			transComChangeParameters.put("userId", request.getParameter("userId"));
			transComChangeParameters.put("transCode", takeinInfo.getTransCode());
			transComChangeParameters.put("wta", "1");
			transComChangeParameters.put("wtc", "1");
			transComChangeParameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
			transComChangeParameters.put("wUserIp", request.getRemoteAddr());
			HashMap<String, String> transComChangeRs = new HashMap<String, String>();
			transComChangeRs = commMapper.selectTransComChange(transComChangeParameters);
			takeinInfo.setTransCode(transComChangeRs.get("rstTransCode"));
			
			memberTakeinMapper.insertTmpTakeinOrder(takeinInfo);
			
			String[] tempString = new String[request.getParameterValues("itemDetail").length];
			String[] tempString2 = new String[request.getParameterValues("itemDetail").length];
			
			Arrays.fill(tempString, "");
			
			for (int roop = 0; roop < tempString.length; roop++) {
				takeinItem.setNno(takeinInfo.getNno());
				takeinItem.setSubNo(Integer.toString(roop+1));
				takeinItem.setOrgStation(takeinInfo.getOrgStation());
				takeinItem.setUserId(request.getParameter("userId"));
				takeinItem.setCusItemCode(request.getParameterValues("cusItemCode")[roop]);
				takeinItem.setItemCnt(request.getParameterValues("itemCnt")[roop]);
				
				if (request.getParameterValues("unitValue")[roop].equals("")) {
					takeinItem.setUnitValue("0");
					/*
					try {
						HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
						parameterInfo.put("userId", request.getParameter("userId"));
						parameterInfo.put("orgStation", request.getParameter("orgStation"));
						parameterInfo.put("cusItemCode", request.getParameterValues("cusItemCode")[roop]);
						//parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		
						HashMap<String, String> unitValueRs = new HashMap<String, String>();
						unitValueRs = mapper.selectUserCusItemListTmp(parameterInfo);
					} catch (Exception e) {
						logger.error("Exception", e);
					}
					*/
				} else {
					takeinItem.setUnitValue(request.getParameterValues("unitValue")[roop]);
				}
				
				memberTakeinMapper.insertTmpTakeinOrderItem(takeinItem);
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		
	}

	@Override
	public int selectTakeinOrderListCntTmp(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinOrderListCntTmp(parameterInfo);
	}

	@Override
	public void deleteTakeinOrderTmp(HashMap<String, String> deleteNno) {
		mapper.deleteTakeinOrderTmp(deleteNno);
	}

	@Override
	public ArrayList<HashMap<String, Object>> getTakeinCusInfo() {
		return mapper.getTakeinCusInfo();
	}

	@Override
	public TakeinOrderListVO selectTakeinOrderTmpList(HashMap<String, Object> parameterInfo) {
		return memberTakeinMapper.selectTakeinOrderTmpList(parameterInfo);
	}

	@Override
	public ArrayList<TmpTakeinOrderItemVO> selectTakeinOrderItemTmpList(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinOrderItemTmpList(parameterInfo);
	}

	@Override
	public TmpTakeinOrderVO selectTakeinOrderInfo(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinOrderInfo(parameterInfo);
	}

	@Override
	public ArrayList<TmpTakeinOrderItemVO> selectTakeinOrderItemInfo(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinOrderItemInfo(parameterInfo);
	}

	@Override
	public int selectTakeinRackListCount(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinRackListCount(parameterInfo);
	}

	@Override
	public ArrayList<RackVO> selectTakeinRackList(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinRackList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectOrderByCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectOrderByCnt(parameterInfo);
	}

	@Override
	public void insertRackInfo(RackVO rackInfo) {
		mapper.insertRackInfo(rackInfo);
	}

	@Override
	public HashMap<String, Object> selectRackInfo(HashMap<String, Object> parameterInfo) {
		return mapper.selectRackInfo(parameterInfo);
	}

	@Override
	public ArrayList<RackVO> selectTakeinRackListOrderBy(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinRackListOrderBy(parameterInfo);
	}

	@Override
	public void updateTakeinRackInfo(HashMap<String, Object> params) {
		mapper.updateTakeinRackInfo(params);
	}


	@Override
	public void deleteTakeinRackInfo(HttpServletRequest request, HttpServletResponse response, String targetParm) {
		String[] targets = targetParm.split(",");
		for (int roop = 0; roop < targets.length; roop++) {
			int cnt = mapper.selectTakeinRackStockCnt(targets[roop]);
			System.out.println(cnt);
			if (cnt == 0) {
				mapper.deleteTakeinRackInfo(targets[roop]);
			} 
		}
		
	}

	
	@Override 
	public int selectRackCodeCnt(String rackCode) { 
		return mapper.selectRackCodeCnt(rackCode);
	}

	@Override
	public int selectRackStockListCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectRackStockListCnt(parameterInfo);
	}

	@Override
	public ArrayList<RackVO> selectRackStockList(HashMap<String, Object> parameterInfo) {
		return mapper.selectRackStockList(parameterInfo);
	}

	@Override
	public ArrayList<RackVO> selectStockInfo(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockInfo(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectRackCodeList(HashMap<String, Object> parameterInfo) {
		return mapper.selectRackCodeList(parameterInfo);
	}

	@Override
	public RackVO selectMoveStockInfo(HashMap<String, Object> parameterInfo) {
		return mapper.selectMoveStockInfo(parameterInfo);
	}

	@Override
	public int selectRackCode(String parameter) {
		return mapper.selectRackCodeCheck(parameter);
	}

	@Override
	public void updateRackStockInfo(HashMap<String, Object> parameterInfo) {
		mapper.updateRackStockInfo(parameterInfo);
		
	}

	@Override
	public int selectCheckRackListCnt(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectCheckRackListCnt(parameterInfo);
	}

	@Override
	public ArrayList<RackVO> selectCheckRackList(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectCheckRackList(parameterInfo);
	}

	@Override
	public int selectStockCheckListCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockCheckListCnt(parameterInfo);
	}

	@Override
	public ArrayList<StockCheckVO> selectStockCheckList(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockCheckList(parameterInfo);
	}

	@Override
	public void insertStockCheckInfo(StockCheckVO stockCheckVO) {
		mapper.insertStockCheckInfo(stockCheckVO);
		
	}

	@Override
	public void deleteStockCheck(String targetParm) {
		String[] targets = targetParm.split(",");
		for (int roop = 0; roop < targets.length; roop++) {
			mapper.deleteStockCheck(targets[roop]);
		}
		
	}

	@Override
	public int selectCheckStockListCount(HashMap<String, Object> parameterInfo) {
		return mapper.selectCheckStockListCount(parameterInfo);
	}

	@Override
	public void checkStockUpdate(HashMap<String, Object> parameterInfo) {
		mapper.checkStockUpdate(parameterInfo);
		
	}

	@Override
	public void checkStockInsert(HashMap<String, Object> parameterInfo) {
		mapper.checkStockInsert(parameterInfo);
		
	}

	@Override
	public RackVO selectStockInfoMoved(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockInfoMoved(parameterInfo);
	}

	@Override
	public int selectRackCodeCheckStock(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectRackCodeCheckStock(parameterInfo);
	}

	@Override
	public void updateRackMoveAndStockCheck(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		mapper.updateRackMoveAndStockCheck(parameterInfo);
		
	}

	@Override
	public void moveRack(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		mapper.moveRack(parameterInfo);
		
	}

	@Override
	public void deleteOriginalRackStockCheck(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		mapper.deleteOriginalRackStockCheck(parameterInfo);
		
	}

	@Override
	public void insertRackMoveAndStockCheck(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		mapper.insertRackMoveAndStockCheck(parameterInfo);
		
	}

	@Override
	public ArrayList<RackVO> selectStockListByRackCode(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectStockListByRackCode(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectGEA1101() {
		// TODO Auto-generated method stub
		return mapper.selectGEA1101();
	}

	@Override
	public ArrayList<CurrencyVO> selectCurencyList(HashMap<String, Object> parameterInfo) {
		return mapper.selectCurencyList(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderListVO> selectTakeinOrderListByCancel(HashMap<String, Object> params) {
		return mapper.selectTakeinOrderListByCancel(params);
	}

	@Override
	public int selectTakeinOrderListByCancelCnt(HashMap<String, Object> params) {
		return mapper.selectTakeinOrderListByCancelCnt(params);
	}

	@Override
	public String execTakeinOrderDel(HttpServletRequest request, String targetHawb) {
		String result = "";
		HashMap<String, Object> params = new HashMap<String, Object>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		String userId = "";
		
		try {
			String nno = mapper.selectNno(targetHawb);
			if (nno == null || nno.length() < 1) {
				result = "FAILED";
			} else {
			params.put("hawbNo", targetHawb);
			params.put("nno", nno);
			mapper.deleteTakinOrderVolume(params);
			mapper.deleteTakeinOrderHawbInfo(params);
			userId = mapper.selectUserIdByHawbNo(params);
			params.put("orgStation", orgStation);
			params.put("userId", userId);
			mapper.updateTakeinStockInfo(params);
			result = "SUCCESS";
			
			}
			
		} catch (Exception e) {
			result = "FAIL";
		}
		
		
		return result;
	}

	@Override
	public HashMap<String, Object> selectEtcOrderInfo(HashMap<String, Object> params) {
		return mapper.selectEtcOrderInfo(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectEtcOrderItemInfo(HashMap<String, Object> params) {
		return mapper.selectEtcOrderItemInfo(params);
	}

	@Override
	public void updateTbTakeinEtcOrder(HashMap<String, Object> params) {
		mapper.updateTbTakeinEtcOrder(params);
	}

	@Override
	public void deleteTbTakeinEtcOrderItem(HashMap<String, Object> params) {
		mapper.deleteTbTakeinEtcOrderItem(params);
		
	}

	@Override
	public int selectTbEtcCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectTbEtcCnt(parameterInfo);
	}

	@Override
	public void SpTakeinEtcCancel(HashMap<String, Object> parameterInfo) {
		mapper.deleteTbTakeinEtcOrder(parameterInfo);
		mapper.deleteTbTakeinEtcOrderItem(parameterInfo);
	}

	@Override
	public void SpStockOutCancel(HashMap<String, Object> params) {
		mapper.updateStockOutCancel(params);
		mapper.updateTakeinEtcOrderCancel(params);
	}

	@Override
	public void takeinEmsOrderExcelDown(String[] nnoList, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/takein/excelSample/emsExcelSample.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
	
			Row row = null;
			Cell cell = null;
			
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			
			int rowNo = 5;
			for (int i = 0; i < nnoList.length; i++) {
				String contents="";
				String number="";
				String weight="";
				String value="";
				String hscode="";
				String origin="";
				String totWeight="";
				String totValue="";
				
				row = sheet.createRow(rowNo);
				
				String nno = nnoList[i];
				params.put("nno", nno);
				
				TakeinOrderListVO orderInfo = new TakeinOrderListVO();
				ArrayList<TakeinOrderItemVO> itemList = new ArrayList<TakeinOrderItemVO>();
				
				orderInfo = mapper.selectTakeinExcelOrderInfo(params);
				orderInfo.dncryptData();
				
				cell = row.createCell(0);
				cell.setCellValue("Merchandise");
				
				cell = row.createCell(1);
				cell.setCellValue(orderInfo.getCneeName());
				
				cell = row.createCell(7);
				cell.setCellValue(orderInfo.getCneeTel());
				
				cell = row.createCell(8);
				cell.setCellValue(orderInfo.getDstnNation());
				
				cell = row.createCell(10);
				cell.setCellValue(orderInfo.getCneeZip());
				
				cell = row.createCell(11);
				cell.setCellValue(orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail());
				
				cell = row.createCell(13);
				cell.setCellValue(orderInfo.getCneeCity());
				
				cell = row.createCell(14);
				cell.setCellValue(orderInfo.getCneeState());
				
				float userWta = Float.parseFloat(orderInfo.getUserWta());
				cell = row.createCell(16);
				
				if (!orderInfo.getWtUnit().toString().toLowerCase().equals("g")) {
					if (orderInfo.getWtUnit().toString().toLowerCase().equals("kg")) {
						cell.setCellValue((int)Math.ceil(userWta*1000));
					} else if (orderInfo.getWtUnit().toString().toLowerCase().equals("lb")) {
						cell.setCellValue((int)Math.ceil(userWta)*454);
					}
				} else {
					cell.setCellValue((int)Math.ceil(userWta));	
				}
				
				itemList = mapper.selectTakeinExcelItemInfo(params);
				for (int j = 0; j < itemList.size(); j++) {
					contents += itemList.get(j).getItemDetail();
					number += itemList.get(j).getItemCnt();
					hscode += itemList.get(j).getHsCode();
					origin += itemList.get(j).getMakeCntry();
					value += itemList.get(j).getUnitValue();
					totValue += itemList.get(j).getTotalValue().toString();
					int itemCnt = Integer.parseInt(itemList.get(j).getItemCnt());
					Float itemWt = Float.parseFloat(itemList.get(j).getUserWta());
					
					if (!orderInfo.getWtUnit().toString().toLowerCase().equals("g")) {
						if (orderInfo.getWtUnit().toString().toLowerCase().equals("kg")) {
							weight += itemWt*1000;
							totWeight += (int)Math.ceil((itemWt*itemCnt)*1000);
							//totWeight += String.format("%.2f", (itemWt*itemCnt)*1000);
						} else if (orderInfo.getWtUnit().toString().toLowerCase().equals("lb")) {
							weight += itemWt*454;
							totWeight += (int)Math.ceil((itemWt*itemCnt)*454);
							//totWeight += String.format("%.2f", (itemWt*itemCnt)*454);
						}
					} else {
						weight += itemWt;
						totWeight += (int)Math.ceil((itemWt*itemCnt));
						//totWeight += String.format("%.2f", (itemWt*itemCnt));
					}
					
					if (j != itemList.size()-1) {
						contents += ";";
						number += ";";
						weight += ";";
						value += ";";
						hscode += ";";
						origin += ";";
						totWeight += ";";
						totValue += ";";
					}
				}
				
				cell = row.createCell(17);
				cell.setCellValue(contents);
				
				cell = row.createCell(18);
				cell.setCellValue(number);
				
				cell = row.createCell(19);
				cell.setCellValue(totWeight);
				
				cell = row.createCell(20);
				cell.setCellValue(totValue);
				
				cell = row.createCell(21);
				cell.setCellValue(itemList.get(0).getChgCurrency());
				
				cell = row.createCell(22);
				cell.setCellValue(hscode);
				
				cell = row.createCell(23);
				cell.setCellValue(origin);
				
				cell = row.createCell(27);
				cell.setCellValue("E");
				
				cell = row.createCell(28);
				cell.setCellValue("em");
				
				cell = row.createCell(29);
				cell.setCellValue(orderInfo.getOrderNo());
				
				cell = row.createCell(43);
				cell.setCellValue("N");
				
				cell = row.createCell(63);
				cell.setCellValue((int)Math.ceil(orderInfo.getLength()));
				
				cell = row.createCell(64);
				cell.setCellValue((int)Math.ceil(orderInfo.getWidth()));
				
				cell = row.createCell(65);
				cell.setCellValue((int)Math.ceil(orderInfo.getHeight()));
				
				rowNo++;
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String nowDate = formatter.format(date);
			
			response.setContentType("ms_vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+nowDate+"_"+request.getSession().getAttribute("USER_ID").toString()+".xlsx");
			
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public HashMap<String, Object> selectEmsBlInfo(HashMap<String, Object> params) throws Exception {
		return mapper.selectEmsBlInfo(params);
	}

	@Override
	public void updateTakeinAgencyBl(HashMap<String, Object> params) throws Exception {
		mapper.updateTakeinAgencyBl(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTakeinTransCodeList(HashMap<String, Object> parameter) throws Exception {
		return mapper.selectTakeinTransCodeList(parameter);
	}

	@Override
	public void takeinEmsOrderExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameter) throws Exception {
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/takein/excelSample/excelUpEmsBl.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		
		Row row = null;
		Cell cell = null;
		
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		
		int rowNo = 1;
		ArrayList<HashMap<String, Object>> blInfos = new ArrayList<HashMap<String, Object>>();
		blInfos = mapper.selectTakeinEmsBlList(parameter);
		
		for (int i = 0; i < blInfos.size(); i++) {
			row = sheet.createRow(rowNo);
			
			cell = row.createCell(0);
			cell.setCellValue(blInfos.get(i).get("userId").toString());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(blInfos.get(i).get("orderNo").toString());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(blInfos.get(i).get("hawbNo").toString());
			cell.setCellStyle(cellStyle);
			
			rowNo++;
		}
		
		response.setContentType("ms_vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=excelEmsBlUpdate.xlsx");
		
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		
	}

	@Override
	public String updateEmsBl(MultipartHttpServletRequest multi, HttpServletRequest request, HashMap<String, Object> params) throws Exception {
		String result = "F";
		
		Map<String, String> resMap = new HashMap<String, String>();
		String excelRoot = request.getSession().getServletContext().getRealPath("/")+"/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		int currentRow = 0;

		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					XSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						HashMap<String, Object> blInfo = new HashMap<String, Object>();
						XSSFRow row = sheet.getRow(rowIndex);
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							currentRow = rowIndex;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								XSSFCell cell = row.getCell(columnIndex);
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) {
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]").length != 1) {
											if (value.split("[.]")[1].equals("0")) {
												value = value.split("[.]")[0];
											}
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										value = cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										value = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										value = cell.getErrorCellValue() + "";
										break;
									}
								}
								switch (columnIndex) {
								case 0: // USER ID
									blInfo.put("userId", value);
								break;
								case 1: // ORDER NO
									blInfo.put("orderNo", value);
								break;
								case 2: // ACI BL
									blInfo.put("hawbNo", value);
								break;
								case 3: // EMS BL
									blInfo.put("agencyBl", value);
								break;
								}
							}
							if (!blInfo.get("agencyBl").equals("")) {
								int cnt = mapper.selectTakeinOrderNno(blInfo);
								if (cnt > 0) {
									mapper.updateMatchingInfos(blInfo);
									result = "S";	
								} else {
									result = "F";	
								}
							} else {
								result = "F";
							}
						}
					}
				} else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					HSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						HashMap<String, Object> blInfo = new HashMap<String, Object>();
						HSSFRow row = sheet.getRow(rowIndex);
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							currentRow = rowIndex;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								HSSFCell cell = row.getCell(columnIndex);
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) {
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]").length != 1) {
											if (value.split("[.]")[1].equals("0")) {
												value = value.split("[.]")[0];
											}
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										value = cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										value = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										value = cell.getErrorCellValue() + "";
										break;
									}
								}
								switch (columnIndex) {
								case 0: // USER ID
									blInfo.put("userId", value);
								break;
								case 1: // ORDER NO
									blInfo.put("orderNo", value);
								break;
								case 2: // ACI BL
									blInfo.put("hawbNo", value);
								break;
								case 3: // EMS BL
									blInfo.put("agencyBl", value);
								break;
								}
							}
							if (!blInfo.get("agencyBl").equals("")) {
								int cnt = mapper.selectTakeinOrderNno(blInfo);
								if (cnt > 0) {
									mapper.updateMatchingInfos(blInfo);
									result = "S";	
								} else {
									result = "F";	
								}
							} else {
								result = "F";
							}
						}
					}
				}
			} catch (Exception e) {
				result = "F";
			}
		}
		
		return result;
	}
	 
	
	public Map filesUpload(MultipartHttpServletRequest multi, String uploadPaths) {
		String path = uploadPaths; // 저장 경로 설정

		String newFileName = ""; // 업로드 되는 파일명
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		Iterator<String> files = multi.getFileNames();
		Map<String, String> resMap = new HashMap<String, String>();
		String fileName = "";
		while (files.hasNext()) {
			String uploadFile = files.next();
			MultipartFile mFile = multi.getFile(uploadFile);
			fileName = mFile.getOriginalFilename();
			newFileName = System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
			try {
				mFile.transferTo(new File(path + newFileName));
				resMap.put(newFileName, path + newFileName);
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		}
		return resMap;
	}

	@Override
	public void updateStockOut(HashMap<String, Object> params) throws Exception {
		mapper.updateStockOut(params);
	}

	@Override
	public void takeinOrderListAllExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowDate = format.format(date);
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("orgStation",orgStation);
		
		ArrayList<TakeinOrderListVO> takeinOrderList = new ArrayList<TakeinOrderListVO>();
		takeinOrderList = mapper.selectTakeinOrderListForExcel(parameterInfo);
		
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/takein/excelSample/";
		if (orgStation.equals("082")) {
			filePath += "takeinOrderList_KR.xlsx";
		} else {
			filePath += "takeinOrderList_EN.xlsx";
		}
		FileInputStream fis = new FileInputStream(filePath);
		
		Row row = null;
		Cell cell = null;

		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setFont(font);
		
		CellStyle cellStyleC = wb.createCellStyle();
		cellStyleC.setWrapText(false);
		cellStyleC.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleC.setFont(font);
		
		CellStyle cellStyleR = wb.createCellStyle();
		cellStyleR.setWrapText(false);
		cellStyleR.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleR.setFont(font);
		
		int rowNo = 1;
		int number = 1;
		
		for (int i = 0; i < takeinOrderList.size(); i++) {
			takeinOrderList.get(i).dncryptData();
			String wDate = takeinOrderList.get(i).getNno();
			wDate = wDate.substring(0, 8);
			row = sheet.createRow(rowNo);
			
			cell = row.createCell(0);
			cell.setCellValue(number);
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(1);
			cell.setCellValue(wDate);
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(2);
			cell.setCellValue(takeinOrderList.get(i).getOrderNo());
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(3);
			cell.setCellValue(takeinOrderList.get(i).getUserId());
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(4);
			cell.setCellValue(takeinOrderList.get(i).getHawbNo());
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(5);
			cell.setCellValue(takeinOrderList.get(i).getCneeName());
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(6);
			cell.setCellValue(takeinOrderList.get(i).getDstnNation());
			cell.setCellStyle(cellStyleC);
			
			
			String cneeAddr = takeinOrderList.get(i).getCneeZip() + " " + takeinOrderList.get(i).getCneeAddr();
			if (!takeinOrderList.get(i).getCneeAddrDetail().equals("")) {
				cneeAddr += " " + takeinOrderList.get(i).getCneeAddrDetail();
			}
			
			cell = row.createCell(7);
			cell.setCellValue(cneeAddr);
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue(takeinOrderList.get(i).getCneeTel());
			cell.setCellStyle(cellStyleC);
			
			cell = row.createCell(9);
			cell.setCellValue(takeinOrderList.get(i).getCneeHp());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue(takeinOrderList.get(i).getTotalItemCnt());
			cell.setCellStyle(cellStyleR);
			
			cell = row.createCell(11);
			cell.setCellValue(takeinOrderList.get(i).getTotalWta() + " " + takeinOrderList.get(i).getWtTakeUnit());
			cell.setCellStyle(cellStyleR);
			
			ArrayList<HashMap<String, Object>> itemMap = mapper.selectTakeinOrderItemForExcel(takeinOrderList.get(i).getNno());
			for (int roop = 0; roop < itemMap.size(); roop++) {

				cell = row.createCell(12);
				cell.setCellValue(itemMap.get(roop).get("cusItemCode").toString());
				cell.setCellStyle(cellStyleC);
				
				cell = row.createCell(13);
				cell.setCellValue(itemMap.get(roop).get("itemCnt").toString());
				cell.setCellStyle(cellStyleR);
				
				cell = row.createCell(14);
				cell.setCellValue(itemMap.get(roop).get("rackCode").toString());
				cell.setCellStyle(cellStyleC);
				
				if (roop != itemMap.size() - 1) {
					rowNo++;	
					row = sheet.createRow(rowNo);
				}
			}

			number++;
			rowNo++;
		}
		
		
		String fileName = "order_list_"+nowDate+".xlsx";
		response.setContentType("ms_vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);

		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		out.flush();
	}

	@Override
	public byte[] selectAllTakeinOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		try {

			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
			parameterInfo.put("orgStation",orgStation);
			
			ArrayList<TakeinOrderListVO> takeinOrderList = new ArrayList<TakeinOrderListVO>();
			takeinOrderList = mapper.selectAllTakeinOrderList(parameterInfo);
			
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/takein/excelSample/";
			if (orgStation.equals("082")) {
				filePath += "takeinOrderList_KR.xlsx";
			} else {
				filePath += "takeinOrderList_EN.xlsx";
			}
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);

			Font font = wb.createFont();
			font.setFontHeightInPoints((short)10);
			
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(false);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle.setFont(font);
			
			CellStyle cellStyleC = wb.createCellStyle();
			cellStyleC.setWrapText(false);
			cellStyleC.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyleC.setFont(font);
			
			CellStyle cellStyleR = wb.createCellStyle();
			cellStyleR.setWrapText(false);
			cellStyleR.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyleR.setFont(font);
			
			int rowNo = 1;
			int number = 1;
			
			for (int i = 0; i < takeinOrderList.size(); i++) {
				takeinOrderList.get(i).dncryptData();
				String wDate = takeinOrderList.get(i).getNno();
				wDate = wDate.substring(0, 8);
				row = sheet.createRow(rowNo);
				
				if (takeinOrderList.get(i).getSubNo().equals("1")) {
					cell = row.createCell(0);
					cell.setCellValue(takeinOrderList.get(i).getNum());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(1);
					cell.setCellValue(wDate);
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(2);
					cell.setCellValue(takeinOrderList.get(i).getOrderNo());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(3);
					cell.setCellValue(takeinOrderList.get(i).getUserId());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(4);
					cell.setCellValue(takeinOrderList.get(i).getHawbNo());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(5);
					cell.setCellValue(takeinOrderList.get(i).getCneeName());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(6);
					cell.setCellValue(takeinOrderList.get(i).getDstnNation());
					cell.setCellStyle(cellStyleC);
					
					
					String cneeAddr = takeinOrderList.get(i).getCneeZip() + " " + takeinOrderList.get(i).getCneeAddr();
					if (!takeinOrderList.get(i).getCneeAddrDetail().equals("")) {
						cneeAddr += " " + takeinOrderList.get(i).getCneeAddrDetail();
					}
					
					cell = row.createCell(7);
					cell.setCellValue(cneeAddr);
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(8);
					cell.setCellValue(takeinOrderList.get(i).getCneeTel());
					cell.setCellStyle(cellStyleC);
					
					cell = row.createCell(9);
					cell.setCellValue(takeinOrderList.get(i).getCneeHp());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(10);
					cell.setCellValue(takeinOrderList.get(i).getTotalItemCnt());
					cell.setCellStyle(cellStyleR);
					
					cell = row.createCell(11);
					cell.setCellValue(takeinOrderList.get(i).getTotalWta() + " " + takeinOrderList.get(i).getWtTakeUnit());
					cell.setCellStyle(cellStyleR);
					
				}
				
				cell = row.createCell(12);
				cell.setCellValue(takeinOrderList.get(i).getCusItemCode());
				cell.setCellStyle(cellStyleC);
				
				cell = row.createCell(13);
				cell.setCellValue(takeinOrderList.get(i).getItemCnt());
				cell.setCellStyle(cellStyleR);
				
				cell = row.createCell(14);
				cell.setCellValue(takeinOrderList.get(i).getRackCode());
				cell.setCellStyle(cellStyleC);

				number++;
				rowNo++;
			}
			
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            return outputStream.toByteArray();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public HashMap<String, Object> insertManagerTmpTakeinStockOutV2(HashMap<String, Object> parameterInfo)
			throws Exception {
		return mapper.insertManagerTmpTakeinStockOutV2(parameterInfo);
	}

	@Override
	public void updateManuDate(HashMap<String, Object> params) throws Exception {
		mapper.updateManuDate(params);
	}

	@Override
	public HashMap<String, Object> execSpWhoutStockReset(HashMap<String, Object> params) throws Exception {
		return mapper.execSpWhoutStockReset(params);
	}

	@Override
	public void updateUserOrderList(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) {

		try {
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}

	@Override
	public ArrayList<String> selectTakeinOutStockUserList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinOutStockUserList(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderListVO> selectTakeinOrderListNew(HashMap<String, Object> parameterInfo)
			throws Exception {
		return mapper.selectTakeinOrderListNew(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItemNew(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinOrderItemNew(parameterInfo);
	}
	
	@Override
	public int selectTakeinOrderListCntNew(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinOrderListCntNew(parameterInfo);
	}

	@Override
	public HashMap<String, Object> spWhoutStockTakeinBatch(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.spWhoutStockTakeinBatch(parameterInfo);
	}

	@Override
	public ArrayList<TakeinOrderListVO> selectTakeinOrderListDev(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinOrderListDev(parameterInfo);
	}
	
	@Override
	public ArrayList<TakeinOrderItemVO> selectTakeinOrderItemDev(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinOrderItemDev(parameterInfo);
	}
}
