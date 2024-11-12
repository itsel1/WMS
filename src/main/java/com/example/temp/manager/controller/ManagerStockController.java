package com.example.temp.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerStockService;
import com.example.temp.manager.vo.stockvo.GroupStockDetailVO;
import com.example.temp.manager.vo.stockvo.GroupStockInfoVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.itextpdf.text.log.SysoCounter;


@Controller
public class ManagerStockController {
	
	@Autowired
	ManagerStockService mgrStockService;


	@RequestMapping(value = "/mngr/stock/inspcAdminStockAdd", method = RequestMethod.GET)
	public String managerAcntEnterpriseAdd(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
	    ArrayList<GroupStockVO> inspStockList = new ArrayList<GroupStockVO>();
	 
		int curPage = 1;
	  
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation =(String)request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		

		int totalCount = mgrStockService.selectTotalCountStockAdd(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}


		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		

		try {
			inspStockList = mgrStockService.selectInspStockAddOrderInfo(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		
		} catch (Exception e) {
			inspStockList = null;
		}
		
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspcStock/inspcAdminStock";
	}
	
	@RequestMapping(value = "/mngr/stock/inspcAdminStock", method = RequestMethod.GET)
	public String managerAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
	    ArrayList<GroupStockVO> inspStockList = new ArrayList<GroupStockVO>();
	 
		int curPage = 1;
	  
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation =(String)request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		

		int totalCount = mgrStockService.selectTotalCountStock(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}


		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		

		try {
			inspStockList = mgrStockService.selectInspStockOrderInfo(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		
		} catch (Exception e) {
			inspStockList = null;
		}
		
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspcStock/inspcAdminStock";
	}
	
	
	@RequestMapping(value = "/mngr/stock/inspcAdminStockDisposal", method = RequestMethod.GET)
	public String inspcAdminStockDisposal(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
	  ArrayList<GroupStockVO> inspStockList = new ArrayList<GroupStockVO>();
	 
		int curPage = 1;
	  
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation =(String)request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		

		int totalCount = mgrStockService.selectStockDisposalCnt(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}


		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		

		try {
	
			inspStockList = mgrStockService.selectStockDisposal(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		} catch (Exception e) {
			inspStockList = null;
		}
		
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspcStock/inspcAdminStockDisposal";
	}
	
	
	@RequestMapping(value = "/mngr/stock/unIdentifiedItems", method = RequestMethod.GET)
	public String inspcAdminUnIdentifiedItems(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
	  ArrayList<GroupStockVO> inspStockList = new ArrayList<GroupStockVO>();
	 
		int curPage = 1;
	  
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation =(String)request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		

		int totalCount = mgrStockService.selectStockUnidentifiedItemCount(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}


		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		

		try {
	
			inspStockList = mgrStockService.selectStockUnidentifiedItem(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		} catch (Exception e) {
			inspStockList = null;
		}
		
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/inspcStock/inspcAdminUnIdentifiedItems";
	}
	
	
	@RequestMapping(value = "/mngr/rls/groupidx/popupMsg", method = RequestMethod.GET)                                                                               
	public String managerPopupMsg(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		parameters.put("orgStation",request.getParameter("orgStation"));
		parameters.put("userId",request.getParameter("userId"));
		
		
		msgHis = mgrStockService.selectMsgHist(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		
		return "adm/rls/inspcStock/popupMsgHistory";
	}
	

	@RequestMapping(value = "/mngr/rls/groupStockMsg", method = RequestMethod.POST)
	public String managerMgrMsg(HttpServletRequest request, HttpServletResponse response, Model model,StockMsgVO msgInfo)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		
 		msgInfo.setOrgStation(request.getParameter("orgStation"));
 		msgInfo.setGroupIdx(request.getParameter("groupIdx"));
 		msgInfo.setUserId(request.getParameter("userId"));
		msgInfo.setMsgDiv("MSG");
		msgInfo.setAdminYn("Y");
		msgInfo.setWUserId((String)request.getSession().getAttribute("USER_ID"));
		msgInfo.setWUserIp(request.getRemoteAddr());
		
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		parameters.put("orgStation",request.getParameter("orgStation"));
		parameters.put("userId",request.getParameter("userId"));

		
		mgrStockService.insertMsgInfo(msgInfo);
		
		
		msgHis = mgrStockService.selectMsgHist(parameters); 
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		 
		return "adm/rls/inspcStock/popupMsgHistory";
	}
	
	
	@RequestMapping(value = "/mngr/stock/popupReturn", method = RequestMethod.GET)
	public String stockReturn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		GroupStockInfoVO groupStockInfo = new GroupStockInfoVO();
		ArrayList<GroupStockDetailVO> groupSubNoStockDetail = new ArrayList<GroupStockDetailVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("userId", request.getParameter("userId"));			
		parameterInfo.put("groupIdx", request.getParameter("groupIdx"));
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
	
		try {
			groupStockInfo = mgrStockService.selectGgroupStock(parameterInfo);
			groupSubNoStockDetail =  mgrStockService.selectGgroupStockDetail(parameterInfo);
		} catch (Exception e) {
			groupStockInfo = null;
			groupSubNoStockDetail = null;
		}
		
		model.addAttribute("inspStockList", groupStockInfo);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("groupSubNoStockDetail", groupSubNoStockDetail);
		return "adm/rls/inspcStock/popupReturn";
	}
	
	@RequestMapping(value = "/mngr/stock/popupTrash", method = RequestMethod.GET)
	public String stockTrash(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		GroupStockInfoVO groupStockInfo = new GroupStockInfoVO();
		ArrayList<GroupStockDetailVO> groupSubNoStockDetail = new ArrayList<GroupStockDetailVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("userId", request.getParameter("userId"));			
		parameterInfo.put("groupIdx", request.getParameter("groupIdx"));
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
	
		try {
			groupStockInfo = mgrStockService.selectGgroupStock(parameterInfo);
			groupSubNoStockDetail =  mgrStockService.selectGgroupStockDetail(parameterInfo);
		} catch (Exception e) {
			groupStockInfo = null;
			groupSubNoStockDetail = null;
		}
		
		model.addAttribute("inspStockList", groupStockInfo);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("groupSubNoStockDetail", groupSubNoStockDetail);
		return "adm/rls/inspcStock/popupTrash";
	}
	
	
	
	@RequestMapping(value="/comn/popupReturnJson",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> popupReturnJson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashMap<String, Object> parameters = new HashMap<String,Object>();
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));
		parameters.put("trkCom", request.getParameter("trkCom"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("trkDate", request.getParameter("trkDate"));
		parameters.put("chgAmt", request.getParameter("chgAmt"));
 		parameters.put("disposalType", request.getParameter("disposalType"));
		parameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());		
		
		Rst = mgrStockService.spStockDisposal(parameters);
		
		return Rst;
	}
	
	
	@RequestMapping(value="/comn/disposalJson",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> disposalJson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashMap<String, Object> parameters = new HashMap<String,Object>();
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));
 		parameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());		
		
		Rst = mgrStockService.spStockDisposalcancle(parameters);
		
		return Rst;
	}
	
	
	@RequestMapping(value="/comn/StockCancle",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> spStockCancle(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashMap<String, Object> parameters = new HashMap<String,Object>();
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));
		parameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());		
		
		Rst = mgrStockService.spStockCancle(parameters);
		
		return Rst;
	}
	

}
