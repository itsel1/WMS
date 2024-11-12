package com.example.temp.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.regions.AwsSystemPropertyRegionProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerReturnOrderService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.service.MemberReturnOrderService;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Controller
public class MemberReturnOrderController {
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	MemberReturnOrderService service;
	
	@Autowired
	ManagerReturnOrderService mgrReturnService;
	
	//test
	@Value("${filePath}")
    String realFilePath;
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	String saveFileName;
	
	String url;
	
	String key = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO";

	@RequestMapping(value = "/cstmr/return/orderCheck", method = RequestMethod.GET)
	public String userReturnOrderCheck(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

		
		return "user/returnOrder/orderCheck";
	}
	
	
	@RequestMapping(value = "/cstmr/return/orderIn", method = RequestMethod.GET)
	public String userReturnOrderIn(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getSession().getAttribute("USER_ID").toString());
		nationList = service.selectDestinationCode(params);
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		
		InvUserInfoVO invUserInfo = new InvUserInfoVO();
		invUserInfo = comnService.selectInvUserInfo(params.get("userId").toString());
		invUserInfo.dncryptData();
		
		UserVO userInfo = new UserVO();
		userInfo = service.selectUserComInfo(params.get("userId").toString());
		userInfo.dncryptData();
		
		model.addAttribute("userComInfo", userInfo);
		model.addAttribute("userInfo", invUserInfo);
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("siteType", "NEW");
		//return "user/return/orderIn";
		return "user/returnOrder/orderInTest";
	}

	
	@RequestMapping(value = "/cstmr/return/orderInAci", method = RequestMethod.GET)
	public String userReturnOrderInAci(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String type = request.getParameter("type");
		String siteType = "";
		String koblNo = "";
		UserOrderListVO orderList = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> eshopBlInfo = new HashMap<String, Object>(); 
		
		UserVO userInfo = new UserVO();
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getSession().getAttribute("USER_ID").toString());
		nationList = service.selectDestinationCode(params);
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		InvUserInfoVO invUserInfo = new InvUserInfoVO();
		invUserInfo = comnService.selectInvUserInfo(params.get("userId").toString());
		invUserInfo.dncryptData();
		model.addAttribute("userInfo", invUserInfo);
		
		if (type.equals("w")) {
			siteType = "WMS";
			if (request.getParameter("bl") != null) {
				koblNo = request.getParameter("bl");
				parameters.put("hawbNo", koblNo);
				parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
				orderList = service.selectOrderList(parameters);
				orderList.dncryptData();
				model.addAttribute("orderList", orderList);
				parameters.put("nno", orderList.getNno());
				itemList = service.selectOrderItemList(parameters);
				model.addAttribute("itemList", itemList);
			}
		} else {
			siteType = "ESHOP";
			userInfo = service.selectUserEshopInfo(params);
			if (request.getParameter("bl") != null) {
				koblNo = request.getParameter("bl");
				parameters.put("hawbNo", koblNo);
				parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
				parameters.put("eshopId", userInfo.getEshopId());
				parameters.put("eshopApiKey", userInfo.getEshopApiKey());
				eshopBlInfo = service.getEshopBlInfo(parameters);
				model.addAttribute("orderList", eshopBlInfo.get("orderList"));
				model.addAttribute("itemList", eshopBlInfo.get("itemList"));
			}
		}
		
		model.addAttribute("koblNo", koblNo);
		model.addAttribute("siteType", siteType);
		return "user/returnOrder/orderInAci";
	}
	
	@RequestMapping(value = "/cstmr/return/checkBl", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userReturnCheckBl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String bl = request.getParameter("bl");
		String siteType = request.getParameter("siteType");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("bl", bl);
		parameters.put("userId", userId);
		int cnt = 0;
		
		if (siteType.toLowerCase().equals("w")) {
			cnt = service.selectHawbCheck(parameters);
		} else {
			
		}
		
		if (cnt < 1) {
			rst.put("code", "F");
			rst.put("msg", "데이터를 찾을 수 없습니다. 정확하게 기입해 주세요.");
		} else {
			rst.put("code", "S");
			rst.put("msg", "데이터 조회 성공");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/cstmr/return/checkEshopBl", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> uesrReturnCheckEshopBl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = service.getEshopBlCheck(request);
		
		return rst;
	}
	
	
	@RequestMapping(value = "/cstmr/return/orderInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userReturnOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("hawbNo", request.getParameter("bl"));
		parameters.put("userId", request.getSession().getAttribute("USER_ID").toString());
		UserOrderListVO orderList = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		
		int orderCnt = service.selectOrderListCnt(parameters);
		
		if (orderCnt < 1) {
			orderInfo.put("code", "F");
			orderInfo.put("codeMsg", "존재하지 않는 운송장번호 입니다. 정확하게 입력해 주세요.");
		} else {
			orderList = service.selectOrderList(parameters);
			orderList.dncryptData();
			parameters.put("nno", orderList.getNno());
			
			itemList = service.selectOrderItemList(parameters);
			
			orderInfo.put("code", "S");
			orderInfo.put("codeMsg", "Success!");
			orderInfo.put("orderList", orderList);
			orderInfo.put("itemList", itemList);
		}
		
		return orderInfo;
	}
	
	@RequestMapping(value = "/cstmr/return/orderInW", method = RequestMethod.GET)
	public String userReturnOrderInfoW(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("hawbNo", request.getParameter("koblNo"));
		parameters.put("userId", request.getSession().getAttribute("USER_ID").toString());

		UserOrderListVO orderList = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		orderList = service.selectOrderList(parameters);
		orderList.dncryptData();
		parameters.put("nno", orderList.getNno());
		itemList = service.selectOrderItemList(parameters);
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = service.selectDestinationCode(parameters);
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		model.addAttribute("koblNo", request.getParameter("koblNo"));
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("siteType", "wms");
		
		return "user/returnOrder/orderInW";
	}
	
	@RequestMapping(value = "/cstmr/return/itemList", method = RequestMethod.POST)
	public String userReturnItemList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = Integer.parseInt(request.getParameter("cnt"))+1;
		model.addAttribute("cnt", cnt);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		return "user/returnOrder/itemList";
	}
	
	@RequestMapping(value = "/cstmr/return/addItem", method = RequestMethod.POST)
	public String userReturnAddItem(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = Integer.parseInt(request.getParameter("cnt"))+1;
		model.addAttribute("cnt", cnt);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		return "user/returnOrder/addItem";
	}
	
	@RequestMapping(value = "/cstmr/return/orderIn", method = RequestMethod.POST)
	public String userReturnOrderInPost(HttpServletResponse response, HttpServletRequest request, MultipartHttpServletRequest multi, ReturnOrderListVO returnOrder) throws Exception {
		
		service.insertReturnOrderInfoProc(request,multi,returnOrder);
		return "redirect:/cstmr/return/orderList";
	}
	
	@RequestMapping(value = "/cstmr/return/orderUp", method = RequestMethod.POST)
	public String userReturnOrderUpPost(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi, ReturnOrderListVO returnOrder) throws Exception {
		service.updateReturnOrderInfoProc(request,multi,returnOrder);
		String rtnUrl = "";
		if (returnOrder.getState().equals("C002") || returnOrder.getState().equals("C003")) {
			rtnUrl = "redirect:/cstmr/return/stockList";
		} else {
			rtnUrl = "redirect:/cstmr/return/orderList";
		}
		return rtnUrl;
	}
	
	@RequestMapping(value = "/cstmr/return/excelFormDown", method = RequestMethod.GET)
	public void userReturnExcelFormDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		service.returnOrderExcelFormDown(request, response);
	}
	
	@RequestMapping(value = "/cstmr/return/tempOrderList", method = RequestMethod.GET)
	public String userReturnTempOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		
		return "user/returnOrder/orderTempList";
	}
	
	@RequestMapping(value = "/cstmr/return/orderExcelUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userReturnOrderExcelUp(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		
		return result;
	}
	
	@RequestMapping(value = "/cstmr/return/orderList", method = RequestMethod.GET)
	public String userReturnOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		/*
		 * parameters.put("userId", (String)
		 * request.getSession().getAttribute("USER_ID"));
		 */
		String userId = (String) request.getSession().getAttribute("USER_ID");
		if (userId.equals("goodsflow")) {
			parameters.put("userId", "itsel2");
		} else {
			parameters.put("userId", userId);
		}
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("state", request.getParameter("status"));

		int curPage = 1;
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = service.selectReturnOrderListCnt(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);
		
		orderList = service.selectReturnOrderList(parameters);
		
		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
		return "user/returnOrder/orderList";
	}

	@RequestMapping(value = "/cstmr/return/orderInfo", method = RequestMethod.GET)
	public String userReturnOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		
		ReturnOrderListVO orderList = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		orderList = service.selectReturnOrderInfo(parameters);
		itemList = service.selectReturnItemList(parameters);
		orderList.dncryptData();
		
		InvUserInfoVO invUserInfo = new InvUserInfoVO();
		invUserInfo = comnService.selectInvUserInfo(parameters.get("userId").toString());
		invUserInfo.dncryptData();
		if (orderList.getAttnName().equals("")) {
			orderList.setAttnName(invUserInfo.getInvUserName());
		}
		if (orderList.getAttnTel().equals("")) {
			orderList.setAttnTel(invUserInfo.getInvUserTel());
		}
		if (orderList.getAttnEmail().equals("")) {
			orderList.setAttnEmail(invUserInfo.getInvUserEmail());
		}
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getSession().getAttribute("USER_ID").toString());
		nationList = service.selectDestinationCode(params);
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		HashMap<String, Object> fileList = new HashMap<String, Object>();
		if (orderList.getTaxType().equals("Y")) {
			fileList = service.selectReturnFileList(nno);
		}
		model.addAttribute("fileList", fileList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		return "user/returnOrder/orderModify";
	}
	
	@RequestMapping(value = "/cstmr/return/checkAciOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userCheckAciOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> eshopRst = new HashMap<String, Object>();
		String nno = request.getParameter("nno");
		String koblNo = request.getParameter("bl");
		String siteType = request.getParameter("system").toLowerCase();
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("bl", koblNo);
		
		rst.put("STATUS", "SUCCESS");
		rst.put("MSG", "");
		
		try {
			
			if (siteType.equals("eshop")) {
				String eshopInfoYn = service.selectCustomerEshopInfoYn(parameters);
				if (eshopInfoYn.toUpperCase().equals("S")) {
					eshopRst = service.getEshopBlCheck(request);
					if (!eshopRst.get("code").equals("S")) {
						rst.put("STATUS", "FAIL");
						rst.put("MSG", "원운송장번호와 매칭되는 배송 정보가 없습니다.\n다시 한 번 확인해 주세요.");
					}
				} else if (eshopInfoYn.toUpperCase().equals("F")) {
					rst.put("STATUS", "FAIL");
					rst.put("MSG", "저장된 Eshop 정보가 없습니다.\n마이페이지 > API 정보에서 Eshop 정보를 입력해 주세요.");
				}
			} else if (siteType.equals("wms")) {
				int hawbChk = service.selectHawbCheck(parameters);
				if (hawbChk == 0) {
					rst.put("STATUS", "FAIL");
					rst.put("MSG", "원운송장번호와 매칭되는 배송 정보가 없습니다.\n다시 한 번 확인해 주세요.");
				}
			}
			
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "처리 중 오류가 발생 하였습니다.\n시스템 관리자에게 문의해 주세요.");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/cstmr/return/orderModifyAci", method = RequestMethod.GET)
	public String userReturnOrderModifyAci(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ReturnOrderListVO returnOrderInfo = new ReturnOrderListVO();
		UserOrderListVO orderList = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		HashMap<String, Object> eshopBlInfo = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		UserVO userInfo = new UserVO();
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		String siteType = "";
		
		parameters.put("userId", request.getSession().getAttribute("USER_ID").toString());
		parameters.put("nno", request.getParameter("nno"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("hawbNo", request.getParameter("koblNo"));
		
		returnOrderInfo = service.selectReturnOrderInfo(parameters);
		returnOrderInfo.dncryptData();
		
		String system = request.getParameter("sys");
		nationList = service.selectDestinationCode(parameters);
		model.addAttribute("nationList", nationList);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		if (system.equals("wms")) {
			siteType = "WMS";
			orderList = service.selectOrderList(parameters);
			orderList.dncryptData();
			returnOrderInfo.setOrderNo(orderList.getOrderNo());
			returnOrderInfo.setShipperName(orderList.getCneeName());
			returnOrderInfo.setShipperTel(orderList.getCneeTel());
			returnOrderInfo.setShipperZip(orderList.getCneeZip());
			returnOrderInfo.setShipperHp(orderList.getCneeHp());
			returnOrderInfo.setShipperAddr(orderList.getCneeAddr());
			returnOrderInfo.setShipperAddrDetail(orderList.getCneeAddrDetail());
			returnOrderInfo.setCneeName(orderList.getShipperName());
			returnOrderInfo.setCneeState(orderList.getShipperState());
			returnOrderInfo.setCneeCity(orderList.getShipperCity());
			returnOrderInfo.setCneeAddr(orderList.getShipperAddr());
			returnOrderInfo.setCneeAddrDetail(orderList.getShipperAddrDetail());
			returnOrderInfo.setCneeZip(orderList.getShipperZip());
			returnOrderInfo.setCneeTel(orderList.getShipperTel());
			model.addAttribute("orderList", returnOrderInfo);
			itemList = service.selectOrderItemList(parameters);
			model.addAttribute("itemList", itemList);
		} else {
			siteType = "ESHOP";
			userInfo = service.selectUserEshopInfo(parameters);
			parameters.put("eshopId", userInfo.getEshopId());
			parameters.put("eshopApiKey", userInfo.getEshopApiKey());
			eshopBlInfo = service.getEshopBlInfo(parameters);
			orderList = (UserOrderListVO) eshopBlInfo.get("orderList");
			returnOrderInfo.setOrderNo(orderList.getOrderNo());
			returnOrderInfo.setShipperName(orderList.getCneeName());
			returnOrderInfo.setShipperTel(orderList.getCneeTel());
			returnOrderInfo.setShipperZip(orderList.getCneeZip());
			returnOrderInfo.setShipperHp(orderList.getCneeHp());
			returnOrderInfo.setShipperAddr(orderList.getCneeAddr());
			returnOrderInfo.setShipperAddrDetail(orderList.getCneeAddrDetail());
			returnOrderInfo.setCneeName(orderList.getShipperName());
			returnOrderInfo.setCneeAddr(orderList.getShipperAddr());
			returnOrderInfo.setCneeTel(orderList.getShipperTel());
			model.addAttribute("orderList", returnOrderInfo);
			model.addAttribute("itemList", eshopBlInfo.get("itemList"));
		}
		HashMap<String, Object> fileList = new HashMap<String, Object>();
		if (returnOrderInfo.getTaxType().equals("Y")) {
			fileList = service.selectReturnFileList(request.getParameter("nno"));	
		}
		model.addAttribute("fileList", fileList);
		model.addAttribute("koblNo", request.getParameter("koblNo"));
		model.addAttribute("siteType", siteType);
		
		return "user/returnOrder/orderApiModify";
	}
	
	@RequestMapping(value = "/cstmr/return/orderDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userReturnOrderDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst.put("nno", request.getParameter("nno"));
		try {
			service.deleteReturnOrderAllList(rst);
			rst.put("STATUS", "SUCCESS");
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
		}
		return rst;
	}
	
	@RequestMapping(value = "/cstmr/return/stockList", method = RequestMethod.GET)
	public String userReturnStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		if (userId.equals("goodsflow")) {
			parameters.put("userId", "itseltest");
		} else {
			parameters.put("userId", userId);
		}
		/* parameters.put("userId", userId); */
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("whStatus", request.getParameter("whStatus"));
		
		int curPage = 1;
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = service.selectReturnStockListCnt(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);
		
		orderList = service.selectReturnStockList(parameters);
		
		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
		
		return "user/returnOrder/stockList";
	}
	
	@RequestMapping(value = "/cstmr/return/updateOrderInfo", method = RequestMethod.GET)
	public String userReturnUpdateOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		
		ReturnOrderListVO orderList = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		orderList = service.selectReturnOrderInfo(parameters);
		itemList = service.selectReturnItemList(parameters);
		orderList.dncryptData();
		
		InvUserInfoVO invUserInfo = new InvUserInfoVO();
		invUserInfo = comnService.selectInvUserInfo(parameters.get("userId").toString());
		invUserInfo.dncryptData();
		if (orderList.getAttnName().equals("")) {
			orderList.setAttnName(invUserInfo.getInvUserName());
		}
		if (orderList.getAttnTel().equals("")) {
			orderList.setAttnTel(invUserInfo.getInvUserTel());
		}
		if (orderList.getAttnEmail().equals("")) {
			orderList.setAttnEmail(invUserInfo.getInvUserEmail());
		}
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getSession().getAttribute("USER_ID").toString());
		nationList = service.selectDestinationCode(params);
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		HashMap<String, Object> fileList = new HashMap<String, Object>();
		if (orderList.getTaxType().equals("Y")) {
			fileList = service.selectReturnFileList(nno);
		}
		model.addAttribute("fileList", fileList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		
		return "user/returnOrder/updateOrderInfo";
	}
	
	@RequestMapping(value = "/cstmr/return/stockListDetail", method = RequestMethod.GET)
	public String userReturnStockListDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ReturnOrderListVO orderList = new ReturnOrderListVO();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> whInImgList = new ArrayList<HashMap<String,Object>>();

		String nno = request.getParameter("nno");
		parameters.put("nno", nno);
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		
		orderList = service.selectReturnOrderInfo(parameters);
		orderList.dncryptData();
		itemList = service.selectReturnItemList(parameters);
		whInImgList = service.selectWhInImageList(parameters);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("whInImgList", whInImgList);
		
		return "user/returnOrder/stockListDetail";
	}
	
	@RequestMapping(value = "/cstmr/return/requestReturnState", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userReturnRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		ReturnOrderListVO orderVo = new ReturnOrderListVO();
		
		try {
			String type = request.getParameter("type").toUpperCase();
			String nno = request.getParameter("nno");
			orderVo.setNno(nno);
			if (type.equals("WHOUT")) {
				orderVo.setState("C003");
			} else if (type.equals("DISP")) {
				int tmpChk = service.selectTmpOrderChk(nno);
				if (tmpChk > 0) {
					rstMap.put("type", type);
					rstMap.put("status", "N");
					return rstMap;
				} else {
					orderVo.setState("D005");	
				}
			} else {
				int tmpChk = service.selectTmpOrderChk(nno);
				if (tmpChk > 0) {
					rstMap.put("type", type);
					rstMap.put("status", "N");
					return rstMap;
				} else {
					orderVo.setState("C002");	
				}
			}
			mgrReturnService.updateReturnOrderStateInfo(orderVo);
			service.insertReturnOrderStateLog(orderVo);
			rstMap.put("type", type);
			rstMap.put("status", "S");
		} catch (Exception e) {
			e.printStackTrace();
			rstMap.put("type", "");
			rstMap.put("status", "F");
		}
		
		return rstMap;
	}
	
	
	@RequestMapping(value = "/cstmr/return/whoutList", method = RequestMethod.GET)
	public String userReturnWhoutList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
		/*
		 * parameters.put("userId", (String)
		 * request.getSession().getAttribute("USER_ID"));
		 */
		String userId = (String) request.getSession().getAttribute("USER_ID");
		if (userId.equals("goodsflow")) {
			parameters.put("userId", "itseltest");
		} else {
			parameters.put("userId", userId);
		}
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("koblNo", request.getParameter("koblNo"));
		parameters.put("trkNo", request.getParameter("trkNo"));
		parameters.put("hawbNo", request.getParameter("hawbNo"));

		int curPage = 1;

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		int totalCnt = service.selectReturnWhoutListCnt(parameters);

		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		parameters.put("paging", paging);

		orderList = service.selectReturnWhoutList(parameters);

		model.addAttribute("paging", paging);
		model.addAttribute("params", parameters);
		model.addAttribute("orderList", orderList);
		
		return "user/returnOrder/whoutList";
	}
	
}
