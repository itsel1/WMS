package com.example.temp.member.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.temp.api.cafe24.service.Cafe24Service;
import com.example.temp.api.cafe24.vo.Cafe24OrderParameter;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.service.MemberStockService;
import com.example.temp.member.service.MemberTakeinService;
import com.example.temp.member.vo.CodeVO;
import com.example.temp.member.vo.UserGroupStockVO;
import com.example.temp.member.vo.UserInspStationVO;
import com.example.temp.member.vo.stock.OrderWhInOutVO;

@Controller
public class MemberStockController {
	
	@Autowired
	MemberStockService usrStockService;
	
	@Autowired
	MemberTakeinService usrTakeinService;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	Cafe24Service cafe24Service;

	@RequestMapping(value="/cstmr/stock/inspcUserStock",method=RequestMethod.GET)
	public String userStockInspcStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<UserGroupStockVO> inspStockList = new ArrayList<UserGroupStockVO>();
		ArrayList<UserInspStationVO> userInspStationVO = new ArrayList<UserInspStationVO>();
		ArrayList<CodeVO> whStatusVo = new ArrayList<CodeVO>();
		
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		
		userInspStationVO = usrStockService.userinSpStation(parameterInfo);
		
		whStatusVo = usrStockService.usrStockStatus();

		parameterInfo.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		parameterInfo.put("whStatus", request.getParameter("whStatus"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("whGroupStatus", "Y");
		
		int curPage = 1;
			
		int totalCount = usrStockService.selectTotalCountStock(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		
		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		
		try {
			inspStockList = usrStockService.userInspStockList(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		
		} catch (Exception e) {
			inspStockList = null;
		}
	
		model.addAttribute("paging", paging);
		model.addAttribute("inspStation", userInspStationVO);
		model.addAttribute("whStatus", whStatusVo);
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "user/stock/inspcUserStock";
	}
	
	@RequestMapping(value="/cstmr/stock/inspcUserDisposalStock",method=RequestMethod.GET)
	public String inspcUserStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<UserGroupStockVO> inspStockList = new ArrayList<UserGroupStockVO>();
		ArrayList<UserInspStationVO> userInspStationVO = new ArrayList<UserInspStationVO>();
		ArrayList<CodeVO> whStatusVo = new ArrayList<CodeVO>();
	
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		
		userInspStationVO = usrStockService.userinSpStation(parameterInfo);
		
		whStatusVo = usrStockService.usrStockStatus();

		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		parameterInfo.put("whStatus", request.getParameter("whStatus"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("whGroupStatus", "N");
		
		int curPage = 1;
			
		int totalCount = usrStockService.selectTotalCountStock(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		
		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		
		try {
			inspStockList = usrStockService.userInspStockList(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		
		} catch (Exception e) {
			inspStockList = null;
		}
	
		model.addAttribute("paging", paging);
		model.addAttribute("inspStation", userInspStationVO);
		model.addAttribute("whStatus", whStatusVo);
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "user/stock/inspcUserDisposalStock";
	}
	
	@RequestMapping(value="/cstmr/stock/inspcUserUnIdentifiedItems",method=RequestMethod.GET)
	public String inspcUserUnIdentifiedItems(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<UserGroupStockVO> inspStockList = new ArrayList<UserGroupStockVO>();
		ArrayList<UserInspStationVO> userInspStationVO = new ArrayList<UserInspStationVO>();
		ArrayList<CodeVO> whStatusVo = new ArrayList<CodeVO>();
	
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		
		userInspStationVO = usrStockService.userinSpStation(parameterInfo);
		
		whStatusVo = usrStockService.usrStockStatus();

		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		parameterInfo.put("whStatus", request.getParameter("whStatus"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("whGroupStatus", "N");
		
		int curPage = 1;
			
		int totalCount = usrStockService.selectTotalCountStockUnIdent(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		
		PagingVO paging = new PagingVO(curPage, totalCount,1, 30);
		parameterInfo.put("paging", paging);
		
		try {
			inspStockList = usrStockService.userInspStockUnidentList(parameterInfo);
			for(int index=0; index < inspStockList.size(); index++) {
				inspStockList.get(index).dncryptData();
			}
		
		} catch (Exception e) {
			inspStockList = null;
		}
	
		model.addAttribute("paging", paging);
		model.addAttribute("inspStation", userInspStationVO);
		model.addAttribute("whStatus", whStatusVo);
		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "user/stock/inspcUserUnIdentifiedItems";
	}
	
	
	@RequestMapping(value = "/cstmr/stock/popupGroupMsgHis", method = RequestMethod.GET)
	public String userPopupMsg(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		
		parameters.put("nno",request.getParameter("nno"));
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		parameters.put("subNo",request.getParameter("subNo"));
		
		msgHis = usrStockService.selectMsgHist(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		
		return "user/stock/popupGroupMsgHis";
	}
	
	@RequestMapping(value = "/cstmr/stock/popupGroupMsg", method = RequestMethod.GET)
	public String userCstrMsg(HttpServletRequest request, HttpServletResponse response, Model model,StockMsgVO msgInfo)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		
		msgInfo.setOrgStation(request.getParameter("orgStation"));
		msgInfo.setUserId((String)request.getSession().getAttribute("USER_ID"));
		msgInfo.setGroupIdx(request.getParameter("groupIdx"));
		msgInfo.setMsgDiv("MSG");
		msgInfo.setAdminYn("N");
		msgInfo.setWhMemo(request.getParameter("whMemo"));
		msgInfo.setWUserId((String)request.getSession().getAttribute("USER_ID"));
		msgInfo.setWUserIp(request.getRemoteAddr());
		
		parameters.put("orgStation",request.getParameter("orgStation"));
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		parameters.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		
		if(request.getParameter("whMemo")!=null && request.getParameter("whMemo")!="") {
			usrStockService.insertMsgInfo(msgInfo);
		};
		
		msgHis = usrStockService.selectMsgHist(parameters); 
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		
		return "user/stock/popupGroupMsgHis";
	}
	
	@RequestMapping(value = "/cstmr/stock/inspUserOrderWhInOut", method = RequestMethod.GET)
	public String inspWhInOut(HttpServletRequest request, HttpServletResponse response, Model model,StockMsgVO msgInfo) throws Exception {
		
		ArrayList<OrderWhInOutVO> list = new ArrayList<OrderWhInOutVO>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("hawbNo", request.getParameter("hawbNo"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		
		
		
		int curPage = 1;
		int totalCount = usrStockService.selectinspUserOrderWhInOutCnt(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}		

		
		PagingVO paging = new PagingVO(curPage,totalCount,5,30);
		parameterInfo.put("paging", paging);
		ArrayList<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		stationList = usrStockService.selectOrderStation(parameterInfo);

		list = usrStockService.selectinspUserOrderWhInOut(parameterInfo);
		
		model.addAttribute("stationList",stationList);
		model.addAttribute("parameterInfo",parameterInfo);
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		
		return "user/stock/inspUserOrderWhInOut";
	}
	
	
	@RequestMapping(value = "/cstmr/stock/inspUserOrderStockDetail", method = RequestMethod.GET)
	public String orderStockDetail(HttpServletRequest request, HttpServletResponse response, Model model,StockMsgVO msgInfo) throws Exception {
		
		ArrayList<OrderWhInOutVO> list = new ArrayList<OrderWhInOutVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("nno", request.getParameter("nno"));

		cafe24Service.mergeSupplier(parameterInfo);
		list = usrStockService.selectinspUserOrderStrockDetail(parameterInfo);
		
		
		
		
		
		
		/*
		Cafe24OrderParameter cafe24OrderParameter = new Cafe24OrderParameter();
		String accessToken = cafe24OrderParameter.getAccessToken();
		String mallId = cafe24OrderParameter.getMallId();
		String supplierCode  = cafe24OrderParameter.getSupplierCode();		
		cafe24OrderParameter.setAccessToken(null);
		*/
		//System.out.println(list.get(0).getSupplierCode());
		
		
		model.addAttribute("list",list);
		return "user/stock/inspUserOrderStockDetail";
	}
	
	
}
