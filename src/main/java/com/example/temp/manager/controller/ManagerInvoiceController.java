package com.example.temp.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.jam.internal.elements.ParameterImpl;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerInvoiceService;
import com.example.temp.security.SecurityKeyVO;
import com.itextpdf.text.log.SysoLogger;

@Controller
public class ManagerInvoiceController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ManagerInvoiceService mgrInvService;
	
	@Value("${filePath}")
    String realFilePath;

	@RequestMapping(value = "/mngr/invoice/invoiceEtcList", method = RequestMethod.GET)
	public String invoiceEtcList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("etcType", request.getParameter("etcType"));

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();

		etcTypeList = mgrInvService.selectEtcType(parameterInfo);

		int curPage = 1;
		int totalCount = mgrInvService.selectEtcCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);
		parameterInfo.put("paging", paging);
		list = mgrInvService.selectEtc(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("etcTypeList", etcTypeList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "adm/invoice/invoiceEtcList";
	}

	@RequestMapping(value = "/mngr/invoice/popupEtc", method = RequestMethod.GET)
	public String popupEtc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		HashMap<String, Object> etcInfoRow = new HashMap<String, Object>();
		etcInfoRow = mgrInvService.selectEtcInfoRow(parameterInfo);

		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = mgrInvService.selectCurrencyList(parameterInfo);

		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();
		etcTypeList = mgrInvService.selectEtcType(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("etcInfoRow", etcInfoRow);
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("etcTypeList", etcTypeList);

		return "adm/invoice/popupEtc";
	}

	@RequestMapping(value = "/mngr/invoice/popupEtcIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonPopupEtcIn(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("etcDate", request.getParameter("etcDate"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("etcCurrency", request.getParameter("etcCurrency"));
		parameterInfo.put("etcType", request.getParameter("etcType"));
		parameterInfo.put("etcValue", request.getParameter("etcValue"));
		parameterInfo.put("remark", request.getParameter("remark"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();

		// Rst = mgrTakeinService.insertManagerTakeinStockIn(parameterInfo);

		Rst = mgrInvService.mgrEctInUp(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/invoice/popupEtcInExcel", method = RequestMethod.POST)
	@ResponseBody
	public void managerJsonPopupEtcInExcel(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi,
			Model model) throws Exception {

		String excelRoot = realFilePath+"excel/expLicence/";
		mgrInvService.insertEtcExcelUpload(multi,request,excelRoot);
	}
	
	@RequestMapping(value = "/mngr/invoice/popupEtcExcelDown", method = RequestMethod.GET)
	@ResponseBody
	public String managerJsonPopupEtcExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String excelRoot = realFilePath+"excel/expLicence/";
		mgrInvService.createEtcExcel(response,request,excelRoot);
		return "";
	}

	@RequestMapping(value = "/mngr/invoice/popupEtcDel", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonPopupEtcDel(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.mgrEctDel(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/invoice/invoiceList", method = RequestMethod.GET)
	public String invoiceInvoiceList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		//parameterInfo.put("orgStation", "213");
		parameterInfo.put("inDate",request.getParameter("inDate"));
		parameterInfo.put("dlvType", request.getParameter("dlvType"));
		parameterInfo.put("invMonth", request.getParameter("invMonth"));
		
		if (parameterInfo.get("dlvType") == null) {
			parameterInfo.put("dlvType", "");
		}
		
		int curPage = 1;
		int totalCount = mgrInvService.selectInvListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 30);
		parameterInfo.put("paging", paging);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list = mgrInvService.selectInvList(parameterInfo);
		
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "adm/invoice/invoiceList";
	}

	@RequestMapping(value = "/mngr/invoice/invPriceApplyList", method = RequestMethod.GET)
	public String invoicepriceApplyList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		//parameterInfo.put("orgStation", "213");
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("fromDate",request.getParameter("fromDate"));
		parameterInfo.put("toDate",request.getParameter("toDate"));
		
		ArrayList<HashMap<String, Object>> unPricelist = new ArrayList<HashMap<String, Object>>();
		unPricelist = mgrInvService.selectUnPricelist(parameterInfo);
		
		
		int curPage = 1;
		int totalCount = mgrInvService.selectPricelistCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);

		
		ArrayList<HashMap<String, Object>> priceList = new ArrayList<HashMap<String, Object>>();
		priceList = mgrInvService.selectPricelist(parameterInfo);
		
		
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("unPricelist", unPricelist);
		model.addAttribute("priceList", priceList);
		model.addAttribute("paging", paging);
		return "adm/invoice/priceApply";
	}
	
	@RequestMapping(value = "/mngr/invoice/v1/invPriceApplyList", method = RequestMethod.GET)
	public String invoicepriceApplyListV1(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String,Object>>();
		
		LocalDate nowDate = LocalDate.now();
		LocalDate prevDate = nowDate.minusMonths(1);
		int prevYear = prevDate.getYear();
		int prevMonth = prevDate.getMonthOfYear();		
		Calendar cal = Calendar.getInstance();
		cal.set(prevYear, prevMonth-1, 1);
		String defaultFromDate = String.valueOf(cal.get(Calendar.YEAR))+String.valueOf(prevMonth)+"01";
		String defaultToDate = String.valueOf(cal.get(Calendar.YEAR))+String.valueOf(prevMonth)+String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String userId = "";
		
		if (request.getParameter("fromDate") != null) {
			defaultFromDate = request.getParameter("fromDate");
		}
		
		if (request.getParameter("toDate") != null) {
			defaultToDate = request.getParameter("toDate");
		}
		
		if (request.getParameter("userId") != null) {
			userId = request.getParameter("userId");
		}
		
		parameters.put("fromDate", defaultFromDate);
		parameters.put("toDate", defaultToDate);
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", userId);
		
		dataList = mgrInvService.selectPriceApplyDataList(parameters);
		
		model.addAttribute("dataList", dataList);
		model.addAttribute("parameters", parameters);
		
		return "adm/invoice/v1/priceApply";
	}
	
	@RequestMapping(value = "/mngr/invoice/invPriceApply", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerPriceApply(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("fromDate",request.getParameter("fromDate"));
		parameterInfo.put("toDate",request.getParameter("toDate"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		rst = mgrInvService.spInvoiceApply(parameterInfo);

		return rst;
	}
	
	@RequestMapping(value = "/mngr/invoice/invExcelDown", method = RequestMethod.GET)
	public void invExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute( "ORG_STATION")); 
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("invoiceNo",request.getParameter("invoiceNo"));
		if(request.getParameter("userId").toLowerCase().equals("hminter")) {
			mgrInvService.invExcelDownAciHm(request,response ,parameterInfo);
		} else if(request.getParameter("userId").toLowerCase().equals("bejewel")) {
			mgrInvService.invExcelDownEtc(request, response, parameterInfo);
		} else {
			mgrInvService.invExcelDownAci(request,response ,parameterInfo);
		}
	}
	
	@RequestMapping(value = "/mngr/invoice/dataExcelDown", method = RequestMethod.GET)
	public void dataExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION")); 
		//parameterInfo.put("orgStation", "213");
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("invoiceNo",request.getParameter("invoiceNo"));
		mgrInvService.invExcelDownDef(request,response ,parameterInfo);
	}
	
	@RequestMapping(value = "/mngr/invoice/compareWeight", method = RequestMethod.GET)
	public String managerCompareWeight(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<HashMap<String, Object>> transCodeList = new ArrayList<HashMap<String, Object>>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		
		transCodeList = mgrInvService.selectTransCodeList(orgStation);
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String,Object>> weightList = new ArrayList<HashMap<String,Object>>();
		
		parameterInfo.put("transCode", request.getParameter("transCode"));
		parameterInfo.put("startDate", request.getParameter("startDate"));
		parameterInfo.put("endDate", request.getParameter("endDate"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("matchHawbNo", request.getParameter("matchHawbNo"));
		
		int curPage = 1;
		int totalCount = mgrInvService.selectWeightListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);
		parameterInfo.put("paging", paging);
		
		weightList = mgrInvService.selectWeightList(parameterInfo);
		
		model.addAttribute("transCodeList", transCodeList);
		model.addAttribute("weightList", weightList);
		model.addAttribute("params", parameterInfo);
		model.addAttribute("paging", paging);
		
		return "adm/invoice/compareWeight";
	}
	
	@RequestMapping(value = "/mngr/invoice/compareWeightExcelDown", method = RequestMethod.GET)
	public String managerCompareWeightExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String rst = "";
		mgrInvService.weightListExcelDown(response,  request);
		return "adm/invoice/compareWeight";
	}
	
	@RequestMapping(value = "/mngr/invoice/clearance", method = RequestMethod.GET)
	public String managerClearance(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<HashMap<String,Object>> clearanceListCnt = new ArrayList<HashMap<String,Object>>();
		clearanceListCnt = mgrInvService.selectClearanceCnt(request);
		model.addAttribute("clearanceListCnt", clearanceListCnt);
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		return "adm/invoice/clearanceRegist";
	}
	
	@RequestMapping(value = "/mngr/invoice/clearanceOneExcelDown", method = RequestMethod.GET)
	public void managerClearanceExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String wDate = request.getParameter("wDate");
		mgrInvService.selectClearanceExcelDown(request, response);
	}
	
	@RequestMapping(value = "/mngr/invoice/clearanceExcelUpload", method = RequestMethod.POST)
	@ResponseBody
	public void managerClearanceExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi)
			throws Exception {
		String excelRoot = realFilePath+"excel/expLicence/";
		//mgrService.insertZoneExcelUpload(multi,request,excelRoot);
		mgrInvService.insertClearanceExcelUpload(multi,request,excelRoot);
	}
	
	@RequestMapping(value = "/mngr/invoice/clearanceOne", method = RequestMethod.GET)
	public String managerClearanceOne(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<HashMap<String,Object>> clearanceList = new ArrayList<HashMap<String,Object>>();
		clearanceList = mgrInvService.selectClearance(request);
		model.addAttribute("registDate", request.getParameter("wDate"));
		model.addAttribute("clearanceList", clearanceList);
		return "adm/invoice/clearanceDetail";
	}

	
	@RequestMapping(value = "/mngr/invoice/ARControlListTest", method = RequestMethod.GET)
	public String mngrARControlList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();

		int curPage = 1;
		int totalCount = mgrInvService.selectARListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);
		parameterInfo.put("paging", paging);
		
		list = mgrInvService.selectARList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("etcTypeList", etcTypeList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "adm/invoice/ARControlList";
	}
	
	

	@RequestMapping(value = "/mngr/invoice/ARComfirmList", method = RequestMethod.GET)
	public String mngrARComfirmList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();


		int curPage = 1;
		int totalCount = mgrInvService.selectARComfirmCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);
		parameterInfo.put("paging", paging);
		
		list = mgrInvService.selectARComfirmList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("etcTypeList", etcTypeList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "adm/invoice/ARComfirmList";
	}
	
	@RequestMapping(value = "/mngr/invoice/ARDecisionList", method = RequestMethod.GET)
	public String mngrARDecisionList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();


		int curPage = 1;
		int totalCount = mgrInvService.selectARDecisionCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);
		parameterInfo.put("paging", paging);
		
		list = mgrInvService.selectARDecisionList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("etcTypeList", etcTypeList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "adm/invoice/ARDecisionList";
	}
	
	@RequestMapping(value = "/mngr/invoice/popupInvoiceCollect", method = RequestMethod.GET)
	public String popupInvoiceCollect(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));

		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		userInfo = mgrInvService.selectUserInfo(parameterInfo);
		
		SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
		
		String userTel = AES256Cipher.AES_Decode(userInfo.get("USER_TEL").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddr = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddrDetail = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR_DETAIL").toString(),SecurityKeyVO.getSymmetryKey());
		
		
		userInfo.put("USER_TEL", userTel);
		userInfo.put("USER_ADDR", userAddr);
		userInfo.put("USER_ADDR_DETAIL", userAddrDetail);
		
		
		HashMap<String, Object> invoiceStatusList = new HashMap<String, Object>();
		invoiceStatusList = mgrInvService.selectInvoiceStatusList(parameterInfo);
		

		ArrayList<HashMap<String, Object>> receivedCodeList = new ArrayList<HashMap<String, Object>>();
		receivedCodeList = mgrInvService.selectreceivedCodeList(parameterInfo);
		
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = mgrInvService.selectCurrencyList(parameterInfo);
		
		
		ArrayList<HashMap<String, Object>> invoiceDetailList = new ArrayList<HashMap<String, Object>>();
		invoiceDetailList = mgrInvService.selectInvoiceDetailList(parameterInfo);
		
		
		ArrayList<HashMap<String, Object>> receivedDetailList = new ArrayList<HashMap<String, Object>>();
		receivedDetailList = mgrInvService.selectReceivedDetailList(parameterInfo);
		
//		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();
//		etcTypeList = mgrInvService.selectEtcType(parameterInfo);
//
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("currencyList", currencyList);
        model.addAttribute("parameterInfo", parameterInfo);
        model.addAttribute("invoiceStatusList", invoiceStatusList);
        model.addAttribute("invoiceDetailList", invoiceDetailList);
        model.addAttribute("receivedDetailList", receivedDetailList);
        model.addAttribute("receivedCodeList", receivedCodeList);
        

		return "adm/invoice/popupInvoiceCollect";
	}
	
	
	@RequestMapping(value = "/mngr/invoice/popupInvoiceDetail", method = RequestMethod.GET)
	public String popupInvoiceDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));

		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		userInfo = mgrInvService.selectUserInfo(parameterInfo);
		
		SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
		
		String userTel = AES256Cipher.AES_Decode(userInfo.get("USER_TEL").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddr = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddrDetail = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR_DETAIL").toString(),SecurityKeyVO.getSymmetryKey());
		
		
		userInfo.put("USER_TEL", userTel);
		userInfo.put("USER_ADDR", userAddr);
		userInfo.put("USER_ADDR_DETAIL", userAddrDetail);
		
		
		HashMap<String, Object> invoiceStatusList = new HashMap<String, Object>();
		invoiceStatusList = mgrInvService.selectInvoiceStatusList(parameterInfo);

		ArrayList<HashMap<String, Object>> invoiceDetailList = new ArrayList<HashMap<String, Object>>();
		invoiceDetailList = mgrInvService.selectInvoiceDetailList(parameterInfo);
		
		
		ArrayList<HashMap<String, Object>> receivedDetailList = new ArrayList<HashMap<String, Object>>();
		receivedDetailList = mgrInvService.selectReceivedDetailList(parameterInfo);
		

		model.addAttribute("userInfo", userInfo);
        model.addAttribute("parameterInfo", parameterInfo);
        model.addAttribute("invoiceStatusList", invoiceStatusList);
        model.addAttribute("invoiceDetailList", invoiceDetailList);
        model.addAttribute("receivedDetailList", receivedDetailList);
        

		return "adm/invoice/popupInvoiceDetail";
	}
	
	
	
	/*
	@RequestMapping(value = "/mngr/invoice/invoiceReceived", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerInvoiceReceived(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));

		
		//HashMap<String, Object> rst = new HashMap<String, Object>();
		
		//rst = mgrInvService.spInvoiceApply(parameterInfo);

		return parameterInfo;
	}
	*/

	@RequestMapping(value = "/mngr/invoice/popupReceivedCode", method = RequestMethod.GET)
	public String popupReceivedCode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		

		ArrayList<HashMap<String, Object>> receivedCodeList = new ArrayList<HashMap<String, Object>>();
		receivedCodeList = mgrInvService.selectReceivedCodeList(parameterInfo);
		model.addAttribute("receivedCodeList", receivedCodeList);

		return "adm/invoice/popupReceivedCode";
	}
	
	@RequestMapping(value = "/mngr/invoice/insertReceivedCode", method = RequestMethod.PUT)
	@ResponseBody
	public HashMap<String, Object> insertReceivedCode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("receivedName", request.getParameter("receivedCodeName"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.insertReceivedCode(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/invoice/deleteReceivedCode", method = RequestMethod.PUT)
	@ResponseBody
	public HashMap<String, Object> deleteReceivedCode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("receivedCode", request.getParameter("receivedCode"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.deleteReceivedCode(parameterInfo);

		return Rst;
	}


	@RequestMapping(value = "/mngr/invoice/invoiceCollectReg", method = RequestMethod.GET)
	public String popupInvoiceCollectReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));		
		parameterInfo.put("receivedDate", request.getParameter("receivedDate"));
		parameterInfo.put("receivedCode", request.getParameter("receivedCode"));
		parameterInfo.put("receivedAmt", request.getParameter("receivedAmt"));
		parameterInfo.put("exchangeRate", request.getParameter("exchangeRate"));
		parameterInfo.put("receivedCurrency", request.getParameter("receivedCurrency"));
		parameterInfo.put("invAmt", request.getParameter("invAmt"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserId",(String)request.getRemoteAddr());
		

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.insertInvoiceReceived(parameterInfo);


		return "redirect:/mngr/invoice/popupInvoiceCollect?invoiceNo="+request.getParameter("invoiceNo")+"&userId="+request.getParameter("userId");
	}
	
	@RequestMapping(value = "/mngr/invoice/invoiceReceivedDel", method = RequestMethod.GET)
	public String invoiceReceivedDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));
		parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserId",(String)request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.deleteInvoiceReceived(parameterInfo);

		return "redirect:/mngr/invoice/popupInvoiceCollect?invoiceNo="+request.getParameter("invoiceNo")+"&userId="+request.getParameter("userId");
	}
	
	
	
	@RequestMapping(value = "/mngr/invoice/invoiceClose", method = RequestMethod.GET)
	public String invoiceClose(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));
		//parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp",(String)request.getRemoteAddr());
		
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrInvService.insertInvoiceComfirm(parameterInfo);

		return "redirect:/mngr/invoice/popupInvoiceCollect?invoiceNo="+request.getParameter("invoiceNo")+"&userId="+request.getParameter("userId");
	}

	
	
	@RequestMapping(value = "/mngr/invoice/invoceUnposting", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> invoceUnposting(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("orgStation",(String)request.getParameter("orgStation"));
		parameterInfo.put("userId", (String)request.getParameter("userId"));
		parameterInfo.put("invoiceNo",(String)request.getParameter("invoiceNo"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp",(String)request.getRemoteAddr());

		Rst = mgrInvService.spInvoiceUnposting(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/invoice/invoceStatusChange", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> invoceStatusChange(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp",(String)request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		
		Rst.put("rstStatus", "Fail");
		
		if(request.getParameter("actWork").equals("decision")) {
			parameterInfo.put("decisionYn","Y");
			Rst = mgrInvService.spInvoiceDecision(parameterInfo);
		}
		
		
		
		if(request.getParameter("actWork").equals("cancle")) {
			mgrInvService.deleteInvoiceComfirm(parameterInfo);
			Rst.put("rstStatus", "SUCCESS");
		}

		return Rst;
	}
	
	
	@RequestMapping(value = "/mngr/invoice/ARControlList", method = RequestMethod.GET)
	public String managerInvoiceARControlList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<HashMap<String, Object>> ARControlList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));

		String searchType = "";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
			
			if (searchType.equals("0")) {
				parameterInfo.put("company", "");
				parameterInfo.put("userId", searchKeywords);
			} else if (searchType.equals("1")) {
				parameterInfo.put("company", searchKeywords);
				parameterInfo.put("userId", "");
			} else {
				parameterInfo.put("company", "");
				parameterInfo.put("userId", "");
			}
		}

		parameterInfo.put("searchType", searchType);

		
		int curPage = 1;
		int totalCount = mgrInvService.selectARControlListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 15);
		parameterInfo.put("paging", paging);
		
		try {
			ARControlList = mgrInvService.selectARControlList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		model.addAttribute("paging", paging);
		model.addAttribute("ARControlList", ARControlList);
		
		return "adm/invoice/ARControl";
	}
	
	
	@RequestMapping(value = "/mngr/invoice/payment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> managerInvoicePayment(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		params.put("userId", request.getParameter("userId"));
		System.out.println(request.getParameter("userId"));
		
		ArrayList<HashMap<String, Object>> invoiceDetail = new ArrayList<HashMap<String, Object>>();
		invoiceDetail = mgrInvService.selectInvoiceDetailByCom(params);
		
		result.put("invoiceDetail", invoiceDetail);
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/invoice/invoiceInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerInvoiceInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

	
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		params.put("userId", request.getParameter("userId"));
		params.put("invoiceNo", request.getParameter("invoiceNo"));
		
		System.out.println(request.getParameter("userId"));
		System.out.println(request.getParameter("invoiceNo"));
		
		ArrayList<HashMap<String, Object>> invoiceDetail = new ArrayList<HashMap<String, Object>>();
		invoiceDetail = mgrInvService.selectInvoiceDetail(params);
		
		HashMap<String, Object> invoiceStatusList = new HashMap<String, Object>();
		invoiceStatusList = mgrInvService.selectInvoiceStatusList(params);
		
		ArrayList<HashMap<String, Object>> receivedCodeList = new ArrayList<HashMap<String, Object>>();
		receivedCodeList = mgrInvService.selectreceivedCodeList(params);
		
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = mgrInvService.selectCurrencyList(params);
		
		ArrayList<HashMap<String, Object>> receivedDetailList = new ArrayList<HashMap<String, Object>>();
		receivedDetailList = mgrInvService.selectReceivedDetailList(params);
		
		result.put("invoiceDetail", invoiceDetail);
		result.put("invoiceStatusList", invoiceStatusList);
		result.put("receivedCodeList", receivedCodeList);
		result.put("currencyList", currencyList);
		result.put("receivedDetailList", receivedDetailList);
		return result;
	}
	
	@RequestMapping(value = "/mngr/invoice/popupInvoice", method = RequestMethod.GET)
	public String managerPopupInvoiceList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));

		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		userInfo = mgrInvService.selectUserInfo2(parameterInfo);
		
		SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
		
		String userTel = AES256Cipher.AES_Decode(userInfo.get("USER_TEL").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddr = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR").toString(),SecurityKeyVO.getSymmetryKey());
		String userAddrDetail = AES256Cipher.AES_Decode(userInfo.get("USER_ADDR_DETAIL").toString(),SecurityKeyVO.getSymmetryKey());
		
		
		userInfo.put("USER_TEL", userTel);
		userInfo.put("USER_ADDR", userAddr);
		userInfo.put("USER_ADDR_DETAIL", userAddrDetail);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		list = mgrInvService.selectInvoiceNo(parameterInfo);

		model.addAttribute("list", list);
		model.addAttribute("userInfo", userInfo);

		return "adm/invoice/popupInvoiceCollect2";
	}
	
	@RequestMapping(value = "/mngr/invoice/popupInvoiceInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerInvoicePopupInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		params.put("userId", request.getParameter("userId"));
		params.put("invoiceNo", request.getParameter("invoiceNo"));
		
		System.out.println(request.getParameter("userId"));
		System.out.println(request.getParameter("invoiceNo"));
		
		ArrayList<HashMap<String, Object>> invoiceDetail = new ArrayList<HashMap<String, Object>>();
		invoiceDetail = mgrInvService.selectInvoiceDetail(params);
		
		HashMap<String, Object> invoiceStatusList = new HashMap<String, Object>();
		invoiceStatusList = mgrInvService.selectInvoiceStatusList(params);
		
		ArrayList<HashMap<String, Object>> receivedCodeList = new ArrayList<HashMap<String, Object>>();
		receivedCodeList = mgrInvService.selectreceivedCodeList(params);
		
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = mgrInvService.selectCurrencyList(params);
		
		ArrayList<HashMap<String, Object>> receivedDetailList = new ArrayList<HashMap<String, Object>>();
		receivedDetailList = mgrInvService.selectReceivedDetailList(params);
		
		result.put("parameterInfo", params);
		result.put("invoiceDetail", invoiceDetail);
		result.put("invoiceStatusList", invoiceStatusList);
		result.put("receivedCodeList", receivedCodeList);
		result.put("currencyList", currencyList);
		result.put("receivedDetailList", receivedDetailList);
		return result;
	}
	
	@RequestMapping(value = "/mngr/invoice/popupInvoiceCollectReg", method = RequestMethod.POST)
	@ResponseBody
	public String managerPopupInvoiceCollectReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result = "F";
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));		
		parameterInfo.put("receivedDate", request.getParameter("receivedDate"));
		parameterInfo.put("receivedCode", request.getParameter("receivedCode"));
		parameterInfo.put("receivedAmt", request.getParameter("receivedAmt"));
		parameterInfo.put("exchangeRate", request.getParameter("exchangeRate"));
		parameterInfo.put("receivedCurrency", request.getParameter("receivedCurrency"));
		parameterInfo.put("invAmt", request.getParameter("invAmt"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp",(String)request.getRemoteAddr());
		

		try {
			mgrInvService.insertInvoiceReceived2(parameterInfo);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}

		return result;
	}
	
	@RequestMapping(value = "/mngr/invoice/invoicePopUpReceivedDel", method = RequestMethod.POST)
	@ResponseBody
	public String managerPopupInvoiceReceivedDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result = "F";
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));
		parameterInfo.put("idx", request.getParameter("idx"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserId",(String)request.getRemoteAddr());


		try {
			mgrInvService.deleteInvoiceReceived2(parameterInfo);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;

		
	}
	
	@RequestMapping(value = "/mngr/invoice/invoicePopupClose", method = RequestMethod.POST)
	@ResponseBody
	public String managerInvoiceClose(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result = "F";
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("invoiceNo", request.getParameter("invoiceNo"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp",(String)request.getRemoteAddr());
		
		try {
			mgrInvService.insertInvoiceComfirm2(parameterInfo);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}

		return result;
	}
	
	
}
