package com.example.temp.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerDepositService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.DepositVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class ManagerDepositController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired 
	ManagerDepositService service;
	
	@Autowired
	ComnService comnService;
	
	@RequestMapping(value = "/mngr/deposit/depositApply", method = RequestMethod.GET)
	public String managerApplyDeposit(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String stationCode = request.getParameter("stationCode");
		String acntName = request.getParameter("acntName");
		String searchDate = request.getParameter("searchDate");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchDate", searchDate);
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		params.put("stationCode", stationCode);
		params.put("acntName", acntName);
		
		int curPage = 1;
		int totalCount = service.selectDepositListCnt(params);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount,5,50);
		
		params.put("paging", paging);
		
		ArrayList<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		stationList = service.selectStationList();
		
		ArrayList<DepositVO> depositList = new ArrayList<DepositVO>();
		depositList = service.selectDepositList(params);
		
		model.addAttribute("orgStation", orgStation);
		model.addAttribute("stationList", stationList);
		model.addAttribute("params", params);
		model.addAttribute("paging", paging);
		model.addAttribute("depositList", depositList);
		
		
		
		return "adm/deposit/applyDepositList";
	}
	
	@RequestMapping(value = "/mngr/deposit/depositApplyDetail", method = RequestMethod.GET)
	public String managerApplyDepositDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int idx = Integer.parseInt(request.getParameter("idx").toString());
		String userId = request.getParameter("userId");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", idx);
		params.put("userId", userId);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = service.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		DepositVO depositInfo = new DepositVO();
		depositInfo = service.selectDepositRecInfo(params);
		
		model.addAttribute("idx", idx);
		model.addAttribute("depositInfo", depositInfo);
		
		return "adm/deposit/applyDepositDetail";
	}
	
	@RequestMapping(value = "/mngr/deposit/registDepositApply", method = RequestMethod.POST)
	public String managerRegistDepositApply(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userId = request.getParameter("userId");
		
		String receivedDate = request.getParameter("receivedDate");
		Double receivedAmt = Double.parseDouble(request.getParameter("receivedAmt"));
		String depositCurrency = request.getParameter("depositCurrency");
		Double exchangeRate = Double.parseDouble(request.getParameter("exchangeRate"));
		Double depositAmt = Double.parseDouble(request.getParameter("depositAmt"));
		String remark = request.getParameter("remark");

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", idx);
		params.put("userId", userId);
		params.put("receivedDate", receivedDate);
		params.put("receivedAmt", receivedAmt);
		params.put("depositCurrency", depositCurrency);
		params.put("exchangeRate", exchangeRate);
		params.put("depositAmt", depositAmt);
		params.put("remark", remark);
		try {
			double depositNow = service.selectDepositAmtTot(params);
			double depositTot = depositNow + depositAmt;
			params.put("depositTot", depositTot);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		service.updateDepositRecInfo(params);
		
		
		return "redirect:/mngr/deposit/depositApplyDetail?idx="+idx+"&userId="+userId;
	}
	
	@RequestMapping(value = "/mngr/deposit/registDepositDel", method = RequestMethod.POST)
	public String managerRegistDepositDel(HttpServletRequest request, HttpServletResponse reponse, Model model) throws Exception {
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userId = request.getParameter("userId");
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("idx", idx);
		params.put("userId", userId);
		
		service.deleteDepositRecInfo(params);
		
		return "redirect:/mngr/deposit/depositApplyDetail?idx="+idx+"&userId="+userId;
	}
	
	@RequestMapping(value = "/mngr/deposit/popupApplyDeposit", method = RequestMethod.GET)
	public String managerPopupDeposit(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", orgStation);
		
		ArrayList<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		stationList = service.selectStationList();
		
		ArrayList<DepositVO> userList = new ArrayList<DepositVO>();
		userList = service.selectUserList(params);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = service.selectCurrencyList();
		
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("userList", userList);
		model.addAttribute("stationList", stationList);
		model.addAttribute("orgStation", orgStation);
		
		return "adm/deposit/popupApplyDeposit";
	}
	
	@RequestMapping(value = "/mngr/deposit/changeUserList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> managerChangeUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		String stationCode = request.getParameter("stationCode");
		params.put("orgStation", stationCode);
		
		ArrayList<DepositVO> userList = new ArrayList<DepositVO>();
		userList = service.selectUserList(params);
		
		for (int i = 0; i < userList.size(); i++) {
			result.add(userList.get(i).getUserId());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/deposit/popupApplyDeposit", method = RequestMethod.POST)
	public void managerPopupDepositRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		try {
			params.put("orgStation", request.getParameter("orgStation"));
			params.put("userId", request.getParameter("userId"));
			params.put("payType", request.getParameter("payType"));
			params.put("applyDate", request.getParameter("applyDate"));
			params.put("receivedCurrency", request.getParameter("receivedCurrency"));
			params.put("wUserId", member.getUsername());
			params.put("wUserIp", request.getRemoteAddr());
			params.put("depositor", request.getParameter("depositor"));
			params.put("applyAmt", request.getParameter("applyAmt"));
			
			service.insertDepositRecInfo(params);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	
	@RequestMapping(value = "/mngr/deposit/delDepositRecInfo", method = RequestMethod.GET)
	public void managerDepositApplyDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", Integer.parseInt(request.getParameter("idx")));
		params.put("userId", request.getParameter("userId"));
		
		service.deleteDepositApplyInfo(params);
		
	}
	
	@RequestMapping(value = "/mngr/deposit/userDepositList", method = RequestMethod.GET)
	public String managerUserDepositList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		
		return "adm/deposit/depositListByUserId";
	}
}
