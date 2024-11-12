package com.example.temp.manager.mapper;

import java.util.ArrayList ;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
@Mapper


public interface ManagerInvoiceMapper {
	
	
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

	public ArrayList<HashMap<String, Object>> invoiceExcelDown(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectBoxSizeList(String nno) throws Exception;

	public ArrayList<HashMap<String, Object>> selectClearance(@Param("startDate")String startDate, @Param("endDate")String endDate) throws Exception;

	public ArrayList<HashMap<String, Object>> selectClearanceCnt(@Param("startDate")String startDate, @Param("endDate")String endDate) throws Exception;

	public void insertClearance(HashMap<String, Object> clearanceInfo) throws Exception;

	public String selectChkClearance(HashMap<String, Object> clearanceInfo) throws Exception;

	int selectARListCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectARList(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> selectUserInfo(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectreceivedCodeList(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> selectInvoiceInfo(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selecInvoiceList(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selecInvoiceEtcList(HashMap<String, Object> parameterInfo);
	
	public ArrayList<HashMap<String, Object>> selectReceivedCodeList(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> insertReceivedCode(HashMap<String, Object> parameterInfo) throws Exception;

	public HashMap<String, Object> deleteReceivedCode(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> insertInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<HashMap<String, Object>> selectInvoiceDetailList(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<HashMap<String, Object>> selectReceivedDetailList(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> deleteInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> selectInvoiceStatusList(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> insertInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception;

	int selectARComfirmCnt(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<HashMap<String, Object>> selectARComfirmList(HashMap<String, Object> parameterInfo) throws Exception;

	int selectARDecisionCnt(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<HashMap<String, Object>> selectARDecisionList(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> spInvoiceDecision(HashMap<String, Object> parameterInfo) throws Exception;

	void deleteInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> spInvoiceUnposting(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectWeightList(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<HashMap<String, Object>> selecInvoiceListHm(HashMap<String, Object> parameterInfo) throws Exception;

	public void insertEtcExcel(HashMap<String, Object> etcInfo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectWeightListExcel(HashMap<String, Object> parameters) throws Exception;

	public int selectDstnNationByInvNo(HashMap<String, Object> parameterInfo);

	public int selectARControlListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectARControlList(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectInvoiceNo(HashMap<String, Object> params);

	public HashMap<String, Object> selectUserInfo2(HashMap<String, Object> params);

	public ArrayList<HashMap<String, Object>> selectInvoiceDetail(HashMap<String, Object> params);

	public void insertInvoiceReceived2(HashMap<String, Object> parameterInfo);

	public void deleteInvoiceReceived2(HashMap<String, Object> parameterInfo);

	public void insertInvoiceComfirm2(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectInvoiceDetailByCom(HashMap<String, Object> params);

	public int selectWeightListCnt(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectTransCodeList(String orgStation);

	public void deleteTakeinEtc(HashMap<String, Object> parameterInfo);

	public ArrayList<HashMap<String, Object>> selectPriceApplyDataList(HashMap<String, Object> parameters);


}
