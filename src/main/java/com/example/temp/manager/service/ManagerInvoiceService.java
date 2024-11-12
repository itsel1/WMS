package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public interface ManagerInvoiceService {
	
	
	public int selectEtcCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectEtc(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectEtcType(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> mgrEctInUp(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectEtcInfoRow(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectCurrencyList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> mgrEctDel(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectInvList(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectInvListCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectUnPricelist(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectPricelist(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectPricelistCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> spInvoiceApply(HashMap<String, Object> parameterInfo) throws Exception;

	
	public void invExcelDownAci(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) throws Exception;

	public void invExcelDownDef(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectBoxSizeList(String nno) throws Exception;

	public ArrayList<HashMap<String,Object>> selectClearance(HttpServletRequest request) throws Exception;

	public ArrayList<HashMap<String, Object>> selectClearanceCnt(HttpServletRequest request) throws Exception;

	public void insertClearanceExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			String excelRoot) throws Exception;

	public int selectARListCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectARList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectUserInfo(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectreceivedCodeList(HashMap<String, Object> parameterInfo) throws Exception;
	
    public ArrayList<HashMap<String, Object>> selectReceivedCodeList(HashMap<String, Object> parameterInfo) throws Exception;
	
	public HashMap<String, Object> insertReceivedCode(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> deleteReceivedCode(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectInvoiceDetailList(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectReceivedDetailList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> deleteInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> selectInvoiceStatusList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectARComfirmCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectARComfirmList(HashMap<String, Object> parameterInfo) throws Exception;

	public int selectARDecisionCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectARDecisionList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> spInvoiceDecision(HashMap<String, Object> parameterInfo) throws Exception;

	public void deleteInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> spInvoiceUnposting(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectWeightList(HashMap<String, Object> parameterInfo) throws Exception;

	public void weightListExcelDown(HttpServletResponse response, HttpServletRequest request) throws Exception;

	public void invExcelDownAciHm(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameterInfo) throws Exception;

	public void insertEtcExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request, String excelRoot) throws Exception;

	public void createEtcExcel(HttpServletResponse response, HttpServletRequest request, String excelRoot) throws Exception;

	public int selectARControlListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectARControlList(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectInvoiceNo(HashMap<String, Object> params);

	public HashMap<String, Object> selectUserInfo2(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectInvoiceDetail(HashMap<String, Object> params);

	public void insertInvoiceReceived2(HashMap<String, Object> parameterInfo);

	public void deleteInvoiceReceived2(HashMap<String, Object> parameterInfo);

	public void insertInvoiceComfirm2(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectInvoiceDetailByCom(HashMap<String, Object> params);

	public void invExcelDownEtc(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo);

	public int selectWeightListCnt(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectTransCodeList(String orgStation) throws Exception;

	public void selectClearanceExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void deleteTakeinEtc(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectPriceApplyDataList(HashMap<String, Object> parameters);


}
