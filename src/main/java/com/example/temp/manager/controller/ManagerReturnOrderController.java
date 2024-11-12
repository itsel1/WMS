package com.example.temp.manager.controller;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.util.Base64;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerInvoiceService;
import com.example.temp.manager.service.ManagerReturnOrderService;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.mapper.MemberReturnOrderMapper;
import com.example.temp.member.service.MemberReturnOrderService;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.smtp.SmtpService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;


@Controller
public class ManagerReturnOrderController {
	
	@Autowired
	ManagerReturnOrderService service;
	
	@Autowired
	MemberReturnOrderService memberReturnOrderService;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	MemberReturnOrderMapper memberReturnOrderMapper;
	
	@Autowired
	ManagerService mgrService;
	
	@Autowired
	MemberService usrService;
	
	@Autowired
	ManagerInvoiceService mgrInvService;
	
	@Autowired
	private SmtpService smtpService;

	@Value("${filePath}")
	String realFilePath;
	
	@RequestMapping(value = "/mngr/return/orderList", method= RequestMethod.GET)
	public String mngrReturnOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("state", request.getParameter("state"));
		
		int curPage = 1;
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = service.selectReturnOrderListCnt(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);
		
		orderList = service.selectReturnOrderList(parameters);
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
	
		return "adm/return/orderList";
	}

	@RequestMapping(value = "/mngr/order/epostExcelDown", method = RequestMethod.GET)
	public void mngrReturnOrderExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		service.returnOrderEpostExcelDown(request, response);
	}
	
	@RequestMapping(value = "/mngr/order/epostExcelUp", method = RequestMethod.POST)
	@ResponseBody
	public String mngrReturnOrderExcelUp(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi) throws Exception {
		String result = "";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		
		try {
			service.reeturnOrderEpostExcelUp(request, response, multi);
			result = "S";
		} catch (Exception e) { 
			e.printStackTrace();
			result = "F";
		}
		return result;
	}
	
	@RequestMapping(value = "/mngr/return/sendCjReturn", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrSendCJReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/addItem", method = RequestMethod.POST)
	public String userReturnAddItem(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = Integer.parseInt(request.getParameter("cnt"))+1;
		model.addAttribute("cnt", cnt);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		return "user/return/addItem";
	}
	
	@RequestMapping(value = "/mngr/return/orderInfo", method = RequestMethod.GET)
	public String mngrReturnOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", request.getParameter("nno"));
		parameters.put("userId", request.getParameter("userId"));
		
		ReturnOrderListVO orderList = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		
		orderList = memberReturnOrderService.selectReturnOrderInfo(parameters);
		itemList = memberReturnOrderService.selectReturnItemList(parameters);
		orderList.dncryptData();
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = memberReturnOrderService.selectDestinationCode(parameters);
		model.addAttribute("nationList", nationList);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		HashMap<String, Object> fileList = new HashMap<String, Object>();
		if (orderList.getTaxType().equals("Y")) {
			fileList = memberReturnOrderService.selectReturnFileList(parameters.get("nno").toString());	
		}
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		return "adm/return/orderInfo";
	}
	
	@RequestMapping(value = "/mngr/return/orderUp", method = RequestMethod.POST)
	public String mngrReturnOrderUpPost(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi, ReturnOrderListVO returnOrder) throws Exception {
		memberReturnOrderService.updateReturnOrderInfoProc(request,multi,returnOrder);
		return "redirect:/mngr/return/orderList";
	}
	
	@RequestMapping(value = "/mngr/return/orderDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst.put("nno", request.getParameter("nno"));
		try {
			memberReturnOrderService.deleteReturnOrderAllList(rst);
			rst.put("STATUS", "SUCCESS");
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
		}
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/orderWHWork", method = RequestMethod.GET)
	public String mngrReturnOrderWhWork(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("state", request.getParameter("state"));
		
		int curPage = 1;
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = service.selectReturnOrderCntForWhWork(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnOrderListForWhWork(parameters);
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("params", parameters);
		model.addAttribute("paging", paging);
		
		return "adm/return/orderWhWork";
	}
	
	@RequestMapping(value = "/mngr/return/whInProcess", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderWhInProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		String barcodeNo = request.getParameter("barcodeNo");
		parameters.put("barcodeNo", barcodeNo);
		
		try {
			orderInfo = service.selectReturnOrderCheck(parameters);
			String nno = orderInfo.get("NNO").toString();
			String state = orderInfo.get("STATE").toString();
			String stateName = orderInfo.get("STATE_NAME").toString();
			if (!orderInfo.get("NNO").toString().equals("")) {
				if (state.equals("B001") || state.equals("C001")) {
					if (state.equals("B001")) {
						parameters.put("nno", nno);
						ReturnOrderListVO orderVO = new ReturnOrderListVO();
						orderVO.setState("C001");
						orderVO.setNno(nno);
						service.updateReturnOrderStateInfo(orderVO);
						memberReturnOrderMapper.insertReturnOrderStateLog(orderVO);
					}
					rst.put("STATUS", "S10");
					rst.put("NNO", nno);
					rst.put("MSG", "");
				} else {
					rst.put("STATUS", "S20");
					rst.put("NNO", "");
					rst.put("MSG", "해당 반품의 상태를 확인해 주세요.\n[" + stateName + "]");
				}
			} else {
				rst.put("STATUS", "F10");
				rst.put("MSG", "입력한 정보와 매칭되는 데이터가 없습니다.");
				rst.put("NNO", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rst.put("STATUS", "F10");
			rst.put("MSG", "시스템 처리 중 오류가 발생 하였습니다.\n시스템 관리자에게 문의해 주세요.");
			rst.put("NNO", "");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/whInProc", method = RequestMethod.GET)
	public String mngrReturnOrderWhInProcGet(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ReturnOrderListVO orderInfo = new ReturnOrderListVO();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", request.getParameter("nno"));
		
		orderInfo = service.selectReturnOrderInfo(parameters);
		orderInfo.dncryptData();
		
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		itemList = service.selectReturnItemInfo(parameters);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("orderInfo", orderInfo);
		
		//return "adm/return/orderWhIn";
		return "adm/return/orderWhInTest";
	}
	
	@RequestMapping(value = "/mngr/return/whInProc", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderWhInProcPost(MultipartHttpServletRequest request) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			service.execWhInProcess(request);
			rst.put("STATUS", "S10");
			rst.put("NNO", request.getParameter("nno"));
			ArrayList<String> groupIdx = service.selectGroupIdxByNno(request.getParameter("nno"));
			rst.put("GROUP_IDX", groupIdx);
		} catch (Exception e) {
			e.printStackTrace();
			rst.put("STATUS", "F10");
			rst.put("NNO", "");
			rst.put("GROUP_IDX", "");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/whInResult", method = RequestMethod.GET)
	public String mngrReturnWhInResult(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		ArrayList<HashMap<String, Object>> stockInfo = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		
		stockInfo = service.selectStockInfoList(parameters);
		
		model.addAttribute("whRst", stockInfo.get(0).get("whRst").toString());
		model.addAttribute("nno", nno);
		model.addAttribute("stockInfo", stockInfo);
		
		return "adm/return/whInResult";
	}
	
	@RequestMapping(value = "/mngr/return/pdfPopup", method = RequestMethod.GET)
	public void mngrReturnPdfPopup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("nno", request.getParameter("nno"));
		model.addAttribute("groupIdx", request.getParameter("groupIdx"));
		service.createReturnStockPdf(request, response);
	}
	
	@RequestMapping(value = "/mngr/return/orderShipment", method = RequestMethod.GET)
	public String mngrReturnOrderShipment(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		
		int curPage = 1;
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = service.selectReturnOrderShipmentCnt(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnOrderShipment(parameters);
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("params", parameters);
		model.addAttribute("paging", paging);
		
		return "adm/return/orderShipment";
	}
	
	@RequestMapping(value = "/mngr/return/orderShipOut", method = RequestMethod.GET)
	public String mngrReturnOrderShipOut(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		service.deleteTmpWhoutInfo(parameters);
		ArrayList<HashMap<String, Object>> transCodeList = new ArrayList<HashMap<String, Object>>();
		transCodeList = service.selectTransCodeList(parameters);
		HashMap<String, Object> transCodeInfo = new HashMap<String, Object>();
		if (transCodeList.size() == 0) {
			transCodeInfo.put("transCode", "ACI");
			transCodeInfo.put("transName", "ACI");
			transCodeList.add(transCodeInfo);
		}
		model.addAttribute("transCodeList", transCodeList);
		model.addAttribute("nno", nno);
		return "adm/return/orderShipOut";
	}
	
	@RequestMapping(value = "/mngr/return/orderShipOut", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderShipOutPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = service.execWhoutProc(request);
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/orderShipOutChk", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderShipOutChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("nno", request.getParameter("nno"));
			parameters.put("stockNo", request.getParameter("stockNo"));
			parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
			parameters.put("wUserIp", request.getRemoteAddr());
			rst = service.execSpWhoutStockReturnIn(parameters);
		} catch (Exception e) {
			rst.put("rstStatus", "FAIL");
			rst.put("rstCode", "");
			rst.put("rstMsg", e.getMessage());
			rst.put("outNno", "");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/return/orderShipOutDetail", method = RequestMethod.POST)
	public String mngrReturnOrderShipOutDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<HashMap<String, Object>> stockOutList = new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String, Object>> stockInfoList = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stockNo", request.getParameter("stockNo"));
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		parameters.put("nno", request.getParameter("nno"));
		
		int subNo = 0;
		int scanCnt = 0;
		if (!request.getParameter("stockNo").equals("")) {
			subNo = service.selectSubNoByStockNo(parameters);	
		}
		
		stockInfoList = service.selectStockUnCheckInfoList(parameters);
		stockOutList = service.selectStockOutList(parameters);
		
		for (int i = 0; i < stockOutList.size(); i++) {
			scanCnt += Integer.parseInt(stockOutList.get(i).get("whoutCnt").toString());
		}
		
		model.addAttribute("stockInfo", stockInfoList);
		model.addAttribute("stockOut", stockOutList);
		model.addAttribute("subNo", subNo);
		model.addAttribute("scanCnt", scanCnt);
		return "adm/return/orderShipOutDetail";
	}
	
	@RequestMapping(value = "/mngr/return/orderStockOutReset", method = RequestMethod.GET)
	public String mngrReturnOrderStockOutReset(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String nno = request.getParameter("nno");
		parameters.put("nno", nno);
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		service.deleteStockOutList(parameters);
		String rtnUrl = "";
		
		if (request.getParameter("actWork") != null) {
			rtnUrl = "redirect:/mngr/return/orderShipment";
		} else {
			rtnUrl = "redirect:/mngr/return/orderShipOut?nno="+nno;
		}
		
		return rtnUrl;
	}
	
	@RequestMapping(value = "/mngr/return/hawbNoList", method = RequestMethod.GET)
	public String mngrReturnHawbNoList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		
		int curPage = 1;
		int totalCount = service.selectReturnTmpListCnt(parameters);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 30);
		parameters.put("paging", paging);
		returnMap = service.selectReturnTmpList(parameters);
		
		model.addAttribute("params", parameters);
		model.addAttribute("paging", paging);
		model.addAttribute("orderList", returnMap);
		return "adm/return/hawbNoList";
	}
	

	@RequestMapping(value = "/mngr/return/orderRegist", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		boolean sendChk = false;
		String userIp = request.getRemoteAddr();
		
		try {
			if (request.getParameterValues("targets") == null) {
				rstMap.put("STATUS", "N");
			} else {
				String[] orderNno = request.getParameterValues("targets");
				String[] userList = request.getParameterValues("userList");
				String[] transCodeList = request.getParameterValues("transCodeList");
				
				for (int i = 0; i < orderNno.length; i++) {
					ReturnOrderListVO orderVo = new ReturnOrderListVO();
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					String nno = orderNno[i];
					String transCode = transCodeList[i];
					String userId = userList[i];
					orderVo.setNno(nno);
					orderVo.setState("C004");
					comnService.comnBlApply(nno, transCode, userId, userIp, "RETURN");
					
					parameters.put("nno", nno);
					parameters.put("transCode", transCode);
					parameters.put("userId", userId);
					parameters.put("wUserIp", userIp);
					parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
					parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
					
					service.insertReturnOrderWeightInfo(parameters);
					
					if (transCode.equals("YSL")) {
						sendChk = true;
					}
					
					service.updateReturnOrderStateInfo(orderVo);
					memberReturnOrderService.insertReturnOrderStateLog(orderVo);
					
				}
				if(sendChk) {
					smtpService.sendMail();
				}
				rstMap.put("STATUS", "S");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rstMap.put("STATUS", "F");
		}

		return rstMap;
	}
	
	@RequestMapping(value = "/mngr/return/orderCancel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		try {
			if (request.getParameterValues("targets") == null) {
				rstMap.put("status", "F");
				rstMap.put("msg", "선택된 데이터가 없습니다.\n다시 시도해 주세요.");
			} else {
				String[] orderNno = request.getParameterValues("targets");
				for (int i = 0; i < orderNno.length; i++) {
					ReturnOrderListVO orderVO = new ReturnOrderListVO();
					String nno = orderNno[i];
					orderVO.setNno(nno);
					orderVO.setState("C002");
					service.execReturnTmpOrderCancel(orderVO);
				}
				rstMap.put("status", "S");
				rstMap.put("msg", "처리 완료 되었습니다.");
			}
		} catch (Exception e) {
			rstMap.put("status", "F");
			rstMap.put("msg", "처리 중 오류가 발생하였습니다.\n시스템 관리자에게 문의해 주세요.");
		}
		return rstMap;
	}
	
	@RequestMapping(value = "/mngr/return/orderHawbUp", method = RequestMethod.GET)
	public String mngrReturnOrderHawbUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			
			String nno = request.getParameter("nno");
			String userId = request.getParameter("userId");
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nno", nno);
			parameters.put("userId", userId);
			
			ArrayList<String> userOrgNation = service.selectUserOrgNation(parameters);
			ArrayList<UserTransComVO> userDstnNation = service.selectDstnTrans(parameters);
			UserOrderListVO userOrder = new UserOrderListVO();
			
			userOrder = service.selectUserOrderInfo(parameters);
			userOrder.dncryptData();
			
			ArrayList<UserOrderItemVO> userOrderItem = new ArrayList<UserOrderItemVO>();
			userOrderItem = service.selectUserOrderItem(parameters);
			
			parameters.put("userOrgNation", userOrgNation);
			ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			nationList = comnService.selectNationCode();
			
			String transCom = userOrder.getTransCode();
			
			model.addAttribute("nationList", nationList);
			model.addAttribute("userOrder", userOrder);
			model.addAttribute("userOrderItem", userOrderItem);
			model.addAttribute("userOrgStation", userOrgStation);
			model.addAttribute("userDstnNation", userDstnNation);
			model.addAttribute("transCode", transCom);
			model.addAttribute("userId", userId);
			
			OrderListOptionVO optionOrderVO = new OrderListOptionVO();
			OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
			OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
			OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
			
			String dstnNation = userOrder.getDstnNation();
			
			optionOrderVO = mgrService.SelectOrderListOption(transCom,dstnNation);
			optionItemVO = mgrService.SelectOrderItemOption(transCom,dstnNation);
			expressOrderVO = mgrService.SelectExpressListOption(transCom,dstnNation);
			expressItemVO = mgrService.SelectExpressItemOption(transCom,dstnNation);
			
			model.addAttribute("optionOrderVO", optionOrderVO);
			model.addAttribute("optionItemVO", optionItemVO);
			model.addAttribute("expressOrderVO", expressOrderVO);
			model.addAttribute("expressItemVO", expressItemVO);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

		return "adm/return/orderHawbUp";
	}
	
	@RequestMapping(value = "/mngr/return/orderHawbUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderHawbUpPost(HttpServletRequest request, HttpServletResponse response, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
		
		try {
			Date date  = new Date();
			String dateTime = format.format(date);

			userOrderList.setOrderType("RETURN");
			userOrderList.setWUserId((String)request.getSession().getAttribute("USER_ID"));
			userOrderList.setWUserIp(request.getRemoteAddr());
			userOrderList.setWDate(dateTime);
			
			usrService.updateUserOrderListARA(userOrderList, userOrderItemList);
			rstMap.put("STATUS", "S");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			rstMap.put("STATUS", "F");
			
		}
		
		return rstMap;
	}
	
	@RequestMapping(value = "/mngr/return/whoutList", method = RequestMethod.GET)
	public String mngrReturnWhoutList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("hawbNo", request.getParameter("hawbNo"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("transCode", request.getParameter("transCode"));
		
		int curPage = 1;

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		int totalCnt = service.selectReturnOrderWhoutListCnt(parameters);

		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnOrderWhoutList(parameters);

		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
		
		return "adm/return/whoutList";
	}
	
	@RequestMapping(value = "/mngr/return/orderUpWhIn", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnOrderUpWhIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		String nno = request.getParameter("nno");
		
		try {
			
			ReturnOrderListVO orderVO = new ReturnOrderListVO();
			orderVO.setNno(nno);
			orderVO.setState("C001");
			
			service.updateReturnOrderStateInfo(orderVO);
			memberReturnOrderService.insertReturnOrderStateLog(orderVO);
			
			rstMap.put("status", "S");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			rstMap.put("status", "F");
			
		}
		return rstMap;
	}
	
	@RequestMapping(value = "/mngr/return/stockList", method = RequestMethod.GET)
	public String mngrReturnStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));

		int curPage = 1;

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		int totalCnt = service.selectReturnOrderStockListCnt(parameters);

		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnOrderStockList(parameters);
		
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}

		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
		
		return "adm/return/stockList";
	}
	
	@RequestMapping(value = "/mngr/return/csList", method = RequestMethod.GET)
	public String mngrReturnCsList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> csList = new ArrayList<HashMap<String, Object>>();
			parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			parameters.put("userId", request.getParameter("searchUserId"));
			parameters.put("koblNo", request.getParameter("koblNo"));
			parameters.put("trkNo", request.getParameter("trkNo"));
			parameters.put("chk", request.getParameter("chk"));
			
			int curPage = 1;

			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			int totalCnt = service.selectReturnCsListCnt(parameters);
			
			PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
			parameters.put("paging", paging);
			
			csList = service.selectReturnCsList(parameters);
			
			model.addAttribute("csList", csList);
			model.addAttribute("paging", paging);
			model.addAttribute("params", parameters);
			model.addAttribute("userId", (String) request.getSession().getAttribute("USER_ID"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "adm/return/csList";
	}
	
	@RequestMapping(value = "/mngr/return/deleteReturnCs", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrReturnDeleteReturnCs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		try {
			parameters.put("nno", request.getParameter("nno"));
			parameters.put("idx", request.getParameter("idx"));
			service.deleteReturnCs(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	
	@RequestMapping(value = "/mngr/return/taxReturnList", method = RequestMethod.GET)
	public String mngrReturnTaxReturnList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		ArrayList<HashMap<String, Object>> realOrderList = new ArrayList<HashMap<String,Object>>();

		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));

		int curPage = 1;

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		int totalCnt = service.selectReturnTaxListCnt(parameters);

		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnTaxList(parameters);
		
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
			HashMap<String, Object> realOrderInfo = new HashMap<String, Object>();
			if (orderList.get(i).getFileCnt() == 0) {
				continue;
			}
			realOrderInfo = service.selectReturnOrderFileList(orderList.get(i).getNno());
			realOrderInfo.put("nno", orderList.get(i).getNno());
			realOrderInfo.put("userId", orderList.get(i).getUserId());
			realOrderInfo.put("koblNo", orderList.get(i).getKoblNo());
			realOrderInfo.put("trkNo", orderList.get(i).getTrkNo());
			realOrderInfo.put("attnName", orderList.get(i).getAttnName());
			realOrderInfo.put("attnTel", orderList.get(i).getAttnTel());
			realOrderInfo.put("attnEmail", orderList.get(i).getAttnEmail());
			realOrderInfo.put("userMemo", orderList.get(i).getUserMemo());
			realOrderInfo.put("adminMemo", orderList.get(i).getAdminMemo());
			
			realOrderList.add(realOrderInfo);	
		}

		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", realOrderList);
		
		return "adm/return/orderTaxList";
	}
	
	@RequestMapping(value = "/mngr/return/dispIn", method = RequestMethod.GET)
	public String mngrReturnDispIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String nno = request.getParameter("nno");
		parameters.put("nno", nno);

		ReturnOrderListVO orderInfo = new ReturnOrderListVO();
		orderInfo = service.selectReturnOrderInfo(parameters);
		orderInfo.dncryptData();
		
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		itemList = service.selectReturnWhInItemInfo(parameters);
		
		ArrayList<HashMap<String, Object>> etcTypeList = new ArrayList<HashMap<String, Object>>();
		etcTypeList = mgrInvService.selectEtcType(parameters);
		
		model.addAttribute("nno", nno);
		model.addAttribute("etcTypeList", etcTypeList);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("itemList", itemList);
		return "adm/return/dispIn";
	}
	
	@RequestMapping(value = "/mngr/return/dispIn", method = RequestMethod.POST)
	public void mngrReturnDispInPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			parameters.put("userId", request.getParameter("userId"));
			parameters.put("etcDate", request.getParameter("etcDate"));
			parameters.put("etcType", request.getParameter("etcType"));
			parameters.put("nno", request.getParameter("nno"));
			parameters.put("hawbNo", "");
			parameters.put("etcValue", request.getParameter("etcValue"));
			parameters.put("etcCurrency", request.getParameter("etcCurrency"));
			parameters.put("etcRemark", request.getParameter("remark"));
			parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
			parameters.put("wUserIp", request.getRemoteAddr());
			service.insertEtcInfo(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/mngr/return/orderStockDetail", method = RequestMethod.GET)
	public String mngrReturnOrderStockDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ReturnOrderListVO orderList = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> whInImgList = new ArrayList<HashMap<String,Object>>();

		String nno = request.getParameter("nno");
		String userId = request.getParameter("userId");
		parameters.put("nno", nno);
		parameters.put("userId", userId);
		
		orderList = memberReturnOrderService.selectReturnOrderInfo(parameters);
		orderList.dncryptData();
		itemList = memberReturnOrderService.selectReturnItemList(parameters);
		whInImgList = memberReturnOrderService.selectWhInImageList(parameters);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("whInImgList", whInImgList);
		
		return "adm/return/stockDetail";
	}
	
	
	@RequestMapping(value = "/mngr/return/orderWHWorkMb", method = RequestMethod.GET)
	public String mngrReturnOrderWHWorkMb(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		
		return "adm/return/orderWhWorkMobile";
	}
	
}
