package com.example.temp.manager.controller;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerInspInService;

import com.example.temp.security.SecurityKeyVO;


@Controller
public class ManagerInspinController {
	
	
	@Autowired
	ManagerInspInService mgrInspInService;

	
	@RequestMapping(value = "/mngr/inspin/inspAddInList", method = RequestMethod.GET)
	public String managerAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
			
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("trkCom",request.getParameter("trkCom"));
		parameterInfo.put("trkNo",request.getParameter("trkNo"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		

		ArrayList<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		
		infoList = mgrInspInService.selectInspaddList(parameterInfo);

		model.addAttribute("infoList", infoList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspin/inspAddInList";
	}
	
	
	@RequestMapping(value = "/mngr/inspin/inspAddInStockDetail", method = RequestMethod.GET)
	public String managerInspAddInStockDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
			
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();		
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
		parameterInfo.put("whStatus",request.getParameter("whStatus"));
		
		HashMap<String, Object> groupStockinfo = new HashMap<String, Object>();
		groupStockinfo =  mgrInspInService.selectInspaddGroupStockInfo(parameterInfo);

		ArrayList<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		infoList = mgrInspInService.selectInspaddStockDetail(parameterInfo);

		model.addAttribute("groupStockinfo", groupStockinfo);
		model.addAttribute("infoList", infoList);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/inspin/inspAddInStockDetail";
	}
	
	@RequestMapping(value = "/mngr/inspin/returnReqList", method = RequestMethod.GET)
	public String managerReturnReqList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		
		int curPage = 1;
		int totalCount = mgrInspInService.selectReqReturnListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);

		ArrayList<HashMap<String, Object>> rstList = new ArrayList<HashMap<String, Object>>();
		rstList =  mgrInspInService.selectReqReturnList(parameterInfo);
		
		SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
		for(int index = 0 ; index < rstList.size() ; index++){
			SecurityKeyVO.getSymmetryKey();
			rstList.get(index).put("cneeAddr", AES256Cipher.AES_Decode(rstList.get(index).get("cneeAddr").toString(),SecurityKeyVO.getSymmetryKey()));
			rstList.get(index).put("cneeAddrDetail", AES256Cipher.AES_Decode(rstList.get(index).get("cneeAddrDetail").toString(),SecurityKeyVO.getSymmetryKey()));
			rstList.get(index).put("cneeTel", AES256Cipher.AES_Decode(rstList.get(index).get("cneeTel").toString(),SecurityKeyVO.getSymmetryKey()));
			rstList.get(index).put("cneeHp", AES256Cipher.AES_Decode(rstList.get(index).get("cneeHp").toString(),SecurityKeyVO.getSymmetryKey()));
			rstList.get(index).put("cneeEmail", AES256Cipher.AES_Decode(rstList.get(index).get("cneeEmail").toString(),SecurityKeyVO.getSymmetryKey()));
		}

		model.addAttribute("rstList", rstList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspin/returnReqList";
	}
	
	@RequestMapping(value = "/mngr/inspin/returnReqListDetail", method = RequestMethod.GET)
	public String managerReturnReqListDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno",request.getParameter("nno"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));

		HashMap<String, Object> rstInfo = new HashMap<String, Object>();
		rstInfo =  mgrInspInService.selectReqReturnDetatil(parameterInfo);

		ArrayList<HashMap<String, Object>> itemInfo = new ArrayList<HashMap<String, Object>>();
		itemInfo =  mgrInspInService.selectReqReturnItemDetatil(parameterInfo);

		model.addAttribute("rstInfo", rstInfo);
		model.addAttribute("itemInfo", itemInfo);
		
		return "adm/rls/inspin/returnReqListDetail";
	}
	
	@RequestMapping(value = "/mngr/inspin/popupReturnStockList", method = RequestMethod.GET)
	public String popupReturnStockList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		
		ArrayList<HashMap<String, Object>> stockList = new ArrayList<HashMap<String, Object>>();		
		stockList =  mgrInspInService.selectReturnWhoutStockList(parameterInfo);
		
		model.addAttribute("stockList", stockList);
		
		return "adm/rls/inspin/popupReturnStockList";
	}
	
	
	@RequestMapping(value = "/mngr/inspin/ReturnStockWhoutTmp", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> adminReturnStockWhoutTmp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno",(String)request.getParameter("nno"));
		parameterInfo.put("stockNo",(String)request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = mgrInspInService.spWhoutStockReturn(parameterInfo); 
		
		return rst;
	}

}
