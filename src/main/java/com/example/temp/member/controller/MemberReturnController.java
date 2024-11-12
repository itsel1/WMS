package com.example.temp.member.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.api.aci.service.ApiV1ReturnService;
import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.api.webhook.controller.WebhookController;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerReturnService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.service.MemberReturnService;


@Controller
public class MemberReturnController {
	
	@Autowired
	MemberReturnService memberReturnService;
	@Autowired
	ApiV1ReturnService rtnService;
	@Autowired
	ManagerReturnService mgrRtnService;
	@Autowired
	ComnService comnService;
	@Autowired
	WebhookController webhookController;
	
	//test
	@Value("${filePath}")
    String realFilePath;
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	String saveFileName;
	
	String url;
	
	String key = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO";
	
	@RequestMapping(value = "/return/list", method = RequestMethod.GET)
	public String getOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			int curPage = 1;
			String depositable = "T";
			ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
			HashMap<String, Object> map = new HashMap<String, Object>();

			if (request.getParameter("code") != null) {
				map.put("code", request.getParameter("code"));
			} else {
				map.put("code", "");
			}
			
			if (request.getParameter("koblNo") != null) {
				map.put("koblNo", request.getParameter("koblNo"));
			} 
			
			map.put("userId", request.getSession().getAttribute("USER_ID"));

			int totalCount = memberReturnService.getTotalOrderCnt(map);
			if(request.getParameter("page")!=null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			PagingVO paging = new PagingVO(curPage,totalCount,10,50);
			map.put("paging", paging);
			try {
				vo = memberReturnService.getReturnData(map);

			} catch (Exception e) {
				vo = null;
			}
			
			double depositPrice = memberReturnService.selectDepositInfo(request.getSession().getAttribute("USER_ID"));
			
			if(depositPrice<500) {
				depositable = "F";
			}
			
			model.addAttribute("depositable",depositable);
			model.addAttribute("paging", paging);
			model.addAttribute("map", map);
			model.addAttribute("vo", vo);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "user/return/returnList";
		
	}
	
	@RequestMapping(value = "/return/regist", method = RequestMethod.GET)
	public String userReturnRegistInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		
		return "user/return/registReturnInfo";
	}
	
	@RequestMapping(value = "/return/getShippingInfo", method = RequestMethod.GET)
	public HashMap<String, Object> userSelectShippingInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			String koblNo = request.getParameter("koblNo");
			String userId = request.getSession().getAttribute("USER_ID").toString();
			
			ReturnVO rtn = new ReturnVO();
			ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
			
			rtn = memberReturnService.getReturnOrders(koblNo,userId);
			
			if (rtn != null) {
				rtn.dncryptData();
				itemList = memberReturnService.getReturnOrdersItem(rtn.getNno());
				result.put("rtn", rtn);
				result.put("itemList", itemList);
				result.put("result", "S");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "F");
			result.put("msg", "조회 결과를 찾을 수 없습니다. 다시 입력해 주세요.");
		}
		return result;
	}

	@RequestMapping(value = "/return/getSendInfo", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectWMSOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		ReturnVO rtn = new ReturnVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		
		rtn = memberReturnService.getReturnOrders(koblNo,userId);
		if(rtn != null) {
			rtn.dncryptData(); 
			itemList = memberReturnService.getReturnOrdersItem(rtn.getNno());
			result.put("rtn", rtn);
			result.put("itemList", itemList);
			result.put("result", "S");
		}else {
			result.put("result", "F");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/return/registReturn", method = RequestMethod.GET)
	public String userRegistReturn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		
		String orderReference = request.getParameter("orderReference");
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		ReturnVO rtn = new ReturnVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		
		// 주문목록에서 검색
		String eMsg = "0";
		
		rtn = memberReturnService.getReturnOrders(koblNo,userId);
		if(rtn != null) {
			eMsg = "0";
			rtn.dncryptData(); 
			itemList = memberReturnService.getReturnOrdersItem(rtn.getNno());
			model.addAttribute("nno", rtn.getNno());
			model.addAttribute("orderReference", rtn.getOrderReference());
			model.addAttribute("koblNo", koblNo);
			model.addAttribute("rtn", rtn);
			model.addAttribute("itemList", itemList);
			model.addAttribute("eMsg", eMsg);
		}else {
			eMsg = "1";
			model.addAttribute("nno", "");
			model.addAttribute("eMsg", eMsg);
		}
		
		return "user/return/registReturn";
	}
	
	
	@RequestMapping(value = "/return/popupMsg", method = RequestMethod.GET)
	public String returnCsMessage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("nno", request.getParameter("nno"));
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		
		msgHis = rtnService.selectMsg(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", parameters);
		
		return "user/return/returnMsgPopup";
	}
	
	@RequestMapping(value = "/return/sendCsMsg", method = RequestMethod.POST)
	public String sendReturnCsMessage(HttpServletRequest request, HttpServletResponse response, Model model, StockMsgVO msgInfo) throws Exception {

		HashMap<String, Object> msgHis = new HashMap<String, Object>();
		HashMap<String, String> params = new HashMap<String, String>();
		
		msgInfo.setOrgStation(request.getParameter("orgStation"));
		msgInfo.setUserId(request.getParameter("userId"));
		
		msgInfo.setMsgDiv("RETURN");
		msgInfo.setAdminYn("N");
		msgInfo.setNno(request.getParameter("nno"));
		msgInfo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		msgInfo.setWUserIp(request.getRemoteAddr());
		msgInfo.setReadYn("N");
		
		
		params.put("orgStation", request.getParameter("orgStation"));
		params.put("userId", request.getParameter("userId"));
		params.put("nno", request.getParameter("nno"));
		
		rtnService.insertMsgInfo(msgInfo);
		
		msgHis = rtnService.selectMsgHis(params);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", params);
		
		return "user/return/returnMsgPopup";
	}
	
	@RequestMapping(value = "/return/delCsMsg", method = RequestMethod.POST)
	public void deleteReturnCsMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getParameter("userId"));
		params.put("nno", request.getParameter("nno"));
		params.put("idx", request.getParameter("idx"));
		
		
		rtnService.deleteMsgInfo(params);
		
	}
	
	
	@RequestMapping(value = "/return/depositList", method = RequestMethod.GET)
	public String getDepositList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		ArrayList<HashMap<String, Object>> depositInfo = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("userId", userId);
		
		params.put("calculation", request.getParameter("calculation"));
		
		if (request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {
			params.put("fromDate", request.getParameter("fromDate"));
			params.put("toDate", request.getParameter("toDate"));	
		}
		
		
		HashMap<String, Object> depositNow = new HashMap<String, Object>();
		depositNow = mgrRtnService.selectDepositTotal(userId);
	
		int curPage = 1;
		int totalCount = mgrRtnService.selectDepositInfoCnt(params);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 10);
		
		params.put("paging", paging);
		
		
		depositInfo = mgrRtnService.selectDepositInfo(params);
		
		model.addAttribute("params", params);
		model.addAttribute("depoistPrice", depositNow);
		model.addAttribute("depositInfo", depositInfo);
		model.addAttribute("userId", userId);
		model.addAttribute("paging", paging);
		
		return "user/return/memDepositList";
	}
	
	@RequestMapping(value = "/return/orderInfo/{nno}", method = RequestMethod.GET)
	public String userReturnOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("nno") String nno) throws Exception {
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		params.put("userId", userId);
		
		try {

			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			nationList = memberReturnService.selectNationCode();
			model.addAttribute("nationList", nationList);
			
			ReturnRequestVO orderInfo = new ReturnRequestVO();
			ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
			
			orderInfo = memberReturnService.selectReturnOrder(params);
			itemList = memberReturnService.selectReturnOrderItem(nno);
			orderInfo.dncryptData();

			int cnt = memberReturnService.selectAciOrderCnt(orderInfo.getKoblNo());
			
			String aciCnt = "";
			
			if (cnt > 0) {
				aciCnt = "Y";
			} else {
				aciCnt = "N";
			}
			
			model.addAttribute("pick_type", orderInfo.getPickType());
			model.addAttribute("aciCnt", aciCnt);
			model.addAttribute("itemList", itemList);
			model.addAttribute("orderInfo", orderInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "user/return/returnOrderInfo";
	}
	
	@RequestMapping(value = "/return/info/{orderReference}", method = RequestMethod.GET)
	public String returnInfoUpLoad(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception {
			
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = memberReturnService.selectNationCode();
		model.addAttribute("nationList", nationList);
		
		ReturnVO rtn = new ReturnVO();
		
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		String eMsg = "0";
		
		rtn = memberReturnService.getAllExpressData(orderReference, userId);
		
		if (rtn.getState().equals("A000") || rtn.getState().equals("A001")) {
			itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());
			model.addAttribute("koblNo", rtn.getKoblNo());
			model.addAttribute("rtn", rtn);
			model.addAttribute("itemList", itemList);
			model.addAttribute("nno", rtn.getNno());
			model.addAttribute("eMsg", eMsg);
			
			//return "user/return/updateReturnInfo";
			return "user/return/updateReturn";
			
		} else {
			eMsg = "0"; 
			itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());
			model.addAttribute("koblNo", rtn.getKoblNo());
			model.addAttribute("rtn", rtn);
			model.addAttribute("itemList", itemList);
			model.addAttribute("nno", rtn.getNno());
			model.addAttribute("eMsg", eMsg);
			
			return "user/return/loadReturnInfo";
		}
	}
	
	@RequestMapping(value = "/return/{orderReference}", method = RequestMethod.GET)
	public String returnUpdatePageLoad(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception {
		
		try {
			String userId = request.getSession().getAttribute("USER_ID").toString();
		
			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			nationList = memberReturnService.selectNationCode();
			model.addAttribute("nationList", nationList);
			
			ReturnVO rtn = new ReturnVO();
			ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
			
			// 주문목록에서 검색
			String eMsg = "0";
			
			rtn = memberReturnService.getAllExpressData(orderReference, userId);
			//String nno = memberReturnService.getNno(orderReference, userId);
			if(rtn.getState().equals("A001") || rtn.getState().equals("A002") || rtn.getState().equals("A000")) {
				
			}else if(rtn.getState().equals("B001") || rtn.getState().equals("C001")) {
				
			}else if(rtn.getState().equals("C002") || rtn.getState().equals("C003") || rtn.getState().equals("C003")) {
				
			}else if(rtn.getState().equals("D005") || rtn.getState().equals("D004") )
			
			// 등록되지 않은 운송장
			eMsg = "0"; 
			
			itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());
			model.addAttribute("koblNo", rtn.getKoblNo());
			model.addAttribute("rtn", rtn);
			model.addAttribute("itemList", itemList);
			model.addAttribute("nno", rtn.getNno());
			model.addAttribute("eMsg", eMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return "user/return/updateExpress";
			return "user/return/updateReturn";
	}
	
	@Transactional
	@RequestMapping(value = "/return/{orderReference}", method = RequestMethod.PATCH)
	public String returnCourierUpdateData(HttpServletRequest request, MultipartHttpServletRequest multi, HttpServletResponse response, Model model, ReturnRequestVO rtnRequestVO, @PathVariable("orderReference") String orderReference) throws Exception{
		Map<String, String> resMap = new HashMap<String, String>();
		int subNoLen = request.getParameterValues("subNo").length;
		String koblNo = request.getParameter("koblNo");
		String nno = request.getParameter("nno");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		if(rtnRequestVO.getTaxType().equals("Y")) {
			resMap = memberReturnService.filesUpload(multi,nno,userId);
		}
		rtnRequestVO.setOrgStation("082");
		rtnRequestVO.setDstnNation(request.getParameter("dstnNation").toString());
		rtnRequestVO.setSellerId(userId);
		if(request.getParameter("calculateId").isEmpty()) {
			rtnRequestVO.setCalculateId(userId);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("calculateId"));
		}
		
		rtnRequestVO.setUserId(userId);
		rtnRequestVO.setOrderReference(orderReference);
		rtnRequestVO.setOrderDate(request.getParameter("orderDate"));
		rtnRequestVO.setOrderNo(request.getParameter("orderNo"));
		
		if(rtnRequestVO.getPickType().equals("B")) {
			rtnRequestVO.setState("A002");
		}else {
			rtnRequestVO.setState("B001");
		}
		//rtnRequestVO.setState("A002");
		rtnRequestVO.setRootSite("WMS");
		rtnRequestVO.setWUserId(userId);
		rtnRequestVO.setWUserIp(userIp);
		//rtnRequestVO.encryptData();
		//memberReturnService.insertReturnRequest(rtnRequestVO);
		memberReturnService.updateReturnRequest(rtnRequestVO, resMap);
		
		memberReturnService.deleteReturnRequestItem(nno);
		for(int i=0; i<subNoLen; i++) {
			HashMap<String,Object> itemMap = new HashMap<String,Object>();
			itemMap.put("nno", nno);
			itemMap.put("orgStation", "082");
			itemMap.put("userId", userId);
			itemMap.put("subNo", request.getParameterValues("subNo")[i]);
			itemMap.put("itemDetail", request.getParameterValues("itemDetail")[i]);
			itemMap.put("brand", request.getParameterValues("brand")[i]);
			itemMap.put("itemWta", request.getParameterValues("itemWta")[i]);
			itemMap.put("wtUnit", request.getParameterValues("wtUnit")[i]);
			itemMap.put("itemCnt", request.getParameterValues("itemCnt")[i]);
			itemMap.put("unitValue", request.getParameterValues("unitValue")[i]);
			itemMap.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
			itemMap.put("makeCntry", request.getParameterValues("makeCntry")[i]);
			itemMap.put("makeCom", request.getParameterValues("makeCom")[i]);
			itemMap.put("hsCode", request.getParameterValues("hsCode")[i]);
			itemMap.put("itemUrl", request.getParameterValues("itemUrl")[i]);
			itemMap.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
			if (request.getParameter("dstnNation").equals("JP")) {
				itemMap.put("cusItemCode", request.getParameterValues("cusItemCode")[i]);
				itemMap.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);	
			}
			itemMap.put("wUserId", userId);
			itemMap.put("wUserIp", userIp);
			memberReturnService.insertReturnRequestItem(itemMap);
		}
		//webhookController.returnRequestWebHook(rtnRequestVO.getOrderReference(), rtnRequestVO.getRegNo(), rtnRequestVO.getKoblNo());
		return "redirect:/return/list";
	}

	@RequestMapping(value = "/return/list", method = RequestMethod.DELETE)
	public String returnDeleteRequest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		for(int i = 0; i < request.getParameterValues("nno").length; i++) {
			memberReturnService.deleteReturnRequest(request.getParameterValues("nno")[i]);
		}
		return "redirect:/return/list";
	}
	
	@RequestMapping(value = "/return/list/{orderReference}", method = RequestMethod.POST)
	public String returnPostRequest(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> params2 = new HashMap<String, Object>();
		
		String type = request.getParameter("acceptType");
		
		ArrayList<ReturnItemVO> itemCodes = new ArrayList<ReturnItemVO>();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		params.put("userId", userId);
		params.put("orderReference", orderReference);
		
		String nno = memberReturnService.selectReturnNno(params);
		String dstnNation = memberReturnService.selectDstnNation(nno);
		
		if (dstnNation.equals("JP")) {
			if (type.equals("accept")) {
			
				itemCodes = memberReturnService.selectCusItemCode(nno);
			
				for (int i = 0; i < itemCodes.size(); i++) {
					params2.put("cusItemCode", itemCodes.get(i).getCusItemCode());
					params2.put("itemDetail", itemCodes.get(i).getItemDetail());
					params2.put("nativeItemDetail", itemCodes.get(i).getNativeItemDetail());
					params2.put("makeCntry", itemCodes.get(i).getMakeCntry());
					params2.put("itemImgUrl", itemCodes.get(i).getItemImgUrl());
					params2.put("userId", userId);
					
					int cnt = memberReturnService.selectItemCode(params2);
					
					if (cnt == 0) {
						memberReturnService.insertCusItemCodeInfo(params2);
					} else {
						continue;
					}
				}
				memberReturnService.updateReturnState(type,orderReference);
			} else {
				memberReturnService.updateReturnState(type,orderReference);
			}
		} else {
			memberReturnService.updateReturnState(type,orderReference);
		}
		
		
		return "redirect:/return/inspList";
	}
	
	// hawb_No 로 검색 -> 출력
	@RequestMapping(value = "/return/kind", method = RequestMethod.GET)
	public String callReturnKind(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "user/return/returnKind";
	}
	
	@RequestMapping(value = "/return/returnCourier", method = RequestMethod.GET)
	public String returnCourierPageLoad(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		model.addAttribute("nationList", nationList);

		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		
		
		String orderReference = request.getParameter("orderReference");
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		ReturnVO rtn = new ReturnVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		
		// 주문목록에서 검색
		String eMsg = "0";
		
		rtn = memberReturnService.getReturnOrders(koblNo,userId);
		if(rtn != null) {
			eMsg = "0";
			rtn.dncryptData(); 
			itemList = memberReturnService.getReturnOrdersItem(rtn.getNno());
			model.addAttribute("nno", rtn.getNno());
			model.addAttribute("orderReference", rtn.getOrderReference());
			model.addAttribute("koblNo", koblNo);
			model.addAttribute("rtn", rtn);
			model.addAttribute("itemList", itemList);
			model.addAttribute("eMsg", eMsg);
		}else {
			eMsg = "1";
			model.addAttribute("nno", "");
			model.addAttribute("eMsg", eMsg);
		}
		//return "user/return/registReturnInfo";
		//return "user/return/returnCourier";
		return "user/return/returnAci";
		//return "user/return/registReturn";
	}
	
	// insert return and return items
	@Transactional
	@RequestMapping(value = "/return/returnCourier", method = RequestMethod.POST)
	public String returnCourierInsertData(HttpServletRequest request, MultipartHttpServletRequest multi, HttpServletResponse response, Model model, ReturnRequestVO rtnRequestVO) throws Exception{
		/*
		 * 순서 : file 먼저 s3 or file server에 insert -> 데이터 insert
		 * 
		 * */
		Map<String, String> resMap = new HashMap<String, String>();
		int subNoLen = request.getParameterValues("itemDetail").length;
		String koblNo = request.getParameter("koblNo");
		String nno = request.getParameter("nno");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		if(rtnRequestVO.getTaxType().equals("Y")) {
			resMap = memberReturnService.filesUpload(multi,nno,userId);
		}
		HashMap<String,Object> tempSelectData = new HashMap<String,Object>();
		tempSelectData= memberReturnService.selectTempData(nno);
		rtnRequestVO.setOrgStation("082");
		rtnRequestVO.setDstnNation(tempSelectData.get("orgStation").toString());
		rtnRequestVO.setSellerId(userId);
		if(request.getParameter("calculateId").isEmpty()) {
			rtnRequestVO.setCalculateId(userId);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("calculateId"));
		}
		if(request.getParameter("orderReference").isEmpty()) {
			rtnRequestVO.setOrderReference(nno);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("orderReference"));
		}
		rtnRequestVO.setUserId(userId);
		rtnRequestVO.setOrderDate(tempSelectData.get("orderDate").toString());
		rtnRequestVO.setOrderNo(tempSelectData.get("orderNo").toString());
		if(rtnRequestVO.getPickType().equals("B")) {
			rtnRequestVO.setState("A001");
		}else {
			rtnRequestVO.setState("B001");
		}
		
		rtnRequestVO.setRootSite("WMS");
		rtnRequestVO.setWUserId(userId);
		rtnRequestVO.setWUserIp(userIp);
		rtnRequestVO.setNno(comnService.selectNNO());
		memberReturnService.insertReturnRequest(rtnRequestVO,resMap);
		
		for(int i=0; i<subNoLen; i++) {
			HashMap<String,Object> itemMap = new HashMap<String,Object>();
			itemMap.put("nno", rtnRequestVO.getNno());
			itemMap.put("orgStation", "082");
			itemMap.put("userId", userId);
			itemMap.put("subNo", request.getParameterValues("subNo")[i]);
			itemMap.put("itemDetail", request.getParameterValues("itemDetail")[i]);
			if(request.getParameterValues("nativeItemDetail")[i] != null) {
				itemMap.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);	
			}
			if(request.getParameterValues("itemDetailEng")[i] != null) {
				itemMap.put("itemDetailEng", request.getParameterValues("itemDetailEng")[i]);
			}
			
			if(request.getParameterValues("cusItemCode")[i] != null) {
				itemMap.put("cusItemCode", request.getParameterValues("cusItemCode")[i]);
			}
			itemMap.put("brand", request.getParameterValues("brand")[i]);
			itemMap.put("itemWta", request.getParameterValues("itemWta")[i]);
			itemMap.put("wtUnit", request.getParameterValues("wtUnit")[i]);
			itemMap.put("itemCnt", request.getParameterValues("itemCnt")[i]);
			itemMap.put("unitValue", request.getParameterValues("unitValue")[i]);
			itemMap.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
			itemMap.put("makeCntry", request.getParameterValues("makeCntry")[i]);
			itemMap.put("makeCom", request.getParameterValues("makeCom")[i]);
			itemMap.put("hsCode", request.getParameterValues("hsCode")[i]);
			itemMap.put("itemUrl", request.getParameterValues("itemUrl")[i]);
			itemMap.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
			itemMap.put("wUserId", userId);
			itemMap.put("wUserIp", userIp);
			
			memberReturnService.insertReturnRequestItem(itemMap);
		}
		webhookController.returnRequestWebHook(rtnRequestVO.getOrderReference(), rtnRequestVO.getRegNo(), rtnRequestVO.getKoblNo());
		//mgrRtnService.updateOrderRegistInReturn(rtnRequestVO.getState(),rtnRequestVO.getOrderReference());
		return "redirect:/return/list";
	}
	
	@RequestMapping(value = "/return/status/{orderReference}", method = RequestMethod.POST)
	public String returnOthersPageLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable("orderReference") String orderReference) throws Exception {
		System.out.println("발송 controller 타는지 확인 ");
		mgrRtnService.updateOrderRegistInReturn("A002",orderReference);
		
		return "redirect:/return/list";
	}
	
	@RequestMapping(value = "/return/selectReturnAttnInfo", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectReturnAttnInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> curInfo = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		HashMap<String, Object> invInfo = new HashMap<String, Object>();
				
		try {
			if (type.equals("inv")) {
				InvUserInfoVO invUserInfo = new InvUserInfoVO();
				invUserInfo = memberReturnService.selectAttnInfo(userId);
				invUserInfo.dncryptData();
				invInfo.put("invUserName", invUserInfo.getInvUserName());
				invInfo.put("invUserTel", invUserInfo.getInvUserTel());
				invInfo.put("invUserEmail", invUserInfo.getInvUserEmail());
				result.put("result", "I");
				result.put("userInfo", invInfo);
			} else {
				int cnt = memberReturnService.selectCurrentInfoCnt(userId);
				
				if (cnt > 0) {
					ReturnRequestVO attnInfo = new ReturnRequestVO();
					attnInfo = memberReturnService.selectCurrentInfo(userId);
					attnInfo.dncryptData();
					
					curInfo.put("ATTN_NAME", attnInfo.getAttnName());
					curInfo.put("ATTN_TEL", attnInfo.getAttnTel());
					curInfo.put("ATTN_EMAIL", attnInfo.getAttnEmail());
					result.put("result", "S");
					result.put("userInfo", curInfo);
				} else {
					result.put("result", "F");
					result.put("msg", "최근 등록된 정보가 없습니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/return/returnOthers", method = RequestMethod.GET)
	public String returnOthersPageLoad(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		model.addAttribute("nationList", nationList);
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		//return "user/return/returnOthers";
		//return "user/return/registReturnOthers";
		
		return "user/return/returnOther";
	}
	
	@RequestMapping(value = "/return/returnItem", method = RequestMethod.POST)
	public String returnOthersItemAdd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String cnts = request.getParameter("cnt").toString();
		model.addAttribute("cnt", cnts);
		model.addAttribute("dstnNation", request.getParameter("dstnNation"));
		
		ArrayList<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
		currencyList = comnService.selectCurrencyList();
		model.addAttribute("currencyList", currencyList);
		return "user/return/returnOthersAdd";
	}
	
	// insert return and return items
	@Transactional
	@RequestMapping(value = "/return/returnOthers", method = RequestMethod.POST)
	public String returnOthersInsertData(HttpServletRequest request, MultipartHttpServletRequest multi, HttpServletResponse response, Model model, ReturnRequestVO rtnRequestVO) throws Exception{
		/*
		 * 순서 : file 먼저 s3 or file server에 insert -> 데이터 insert
		 * 
		 * */
		Map<String, String> resMap = new HashMap<String, String>();
		int subNoLen = request.getParameterValues("itemDetail").length;
		String koblNo = request.getParameter("koblNo");
		
		String nno = comnService.selectNNO();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		//위약 반송 여부
		if(rtnRequestVO.getTaxType().equals("Y")) {
			resMap = memberReturnService.filesUpload(multi,nno,userId);
		}
		rtnRequestVO.setNno(nno);
		rtnRequestVO.setOrgStation("082");
		rtnRequestVO.setDstnNation(request.getParameter("dstnNation"));
		rtnRequestVO.setSellerId(userId);
		if(request.getParameter("calculateId").isEmpty()) {
			rtnRequestVO.setCalculateId(userId);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("calculateId"));
		}
		
		rtnRequestVO.setUserId(userId);
		if(request.getParameter("orderReference").isEmpty()) {
			rtnRequestVO.setOrderReference(nno);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("orderReference"));
		}
		rtnRequestVO.setOrderDate(request.getParameter("orderDate"));
		rtnRequestVO.setOrderNo(request.getParameter("orderNo"));
		rtnRequestVO.setCdRemark("");
		// 픽업 타입이 직접 발송일 경우 (B)
		System.out.println(rtnRequestVO.getPickType());
		if(rtnRequestVO.getPickType().equals("B")) {
			rtnRequestVO.setState("A001"); // 접수 신청
		}else {
			rtnRequestVO.setState("B001"); // 접수 완료
		}
		
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String formatNow = now.format(formatter);
		
		if (request.getParameter("a002Date") != null) {
			String a002Date = request.getParameter("a002Date");
			SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			Date formatDate = newDate.parse(a002Date);
			String strDate = date.format(formatDate);
			rtnRequestVO.setA002Date(strDate+formatNow);
		} else {
			rtnRequestVO.setA002Date("");
		}
		rtnRequestVO.setB001Date("");
		rtnRequestVO.setC001Date("");
		rtnRequestVO.setC004Date("");
		rtnRequestVO.setD005Date("");
		rtnRequestVO.setRootSite("WMS");
		rtnRequestVO.setWUserId(userId);
		rtnRequestVO.setWUserIp(userIp);
		//rtnRequestVO.encryptData();
		memberReturnService.insertReturnRequest(rtnRequestVO, resMap);
		
		for(int i=0; i<subNoLen; i++) {
			HashMap<String,Object> itemMap = new HashMap<String,Object>();
			itemMap.put("nno", nno);
			itemMap.put("orgStation", "082");
			itemMap.put("userId", userId);
			itemMap.put("subNo", i+1);
			itemMap.put("itemDetail", request.getParameterValues("itemDetail")[i]);
			itemMap.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);
			itemMap.put("cusItemCode", request.getParameterValues("cusItemCode")[i]);
			itemMap.put("brand", request.getParameterValues("brand")[i]);
			itemMap.put("itemWta", request.getParameterValues("itemWta")[i]);
			if (request.getParameterValues("wtUnit")[i] == null) {
				itemMap.put("wtUnit", "KG");
			} else {
				itemMap.put("wtUnit", request.getParameterValues("wtUnit")[i]);	
			}
			itemMap.put("itemCnt", request.getParameterValues("itemCnt")[i]);
			itemMap.put("unitValue", request.getParameterValues("unitValue")[i]);
			itemMap.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
			itemMap.put("makeCntry", request.getParameterValues("makeCntry")[i]);
			itemMap.put("makeCom", request.getParameterValues("makeCom")[i]);
			itemMap.put("hsCode", request.getParameterValues("hsCode")[i]);
			itemMap.put("itemUrl", request.getParameterValues("itemUrl")[i]);
			itemMap.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
			itemMap.put("wUserId", userId);
			itemMap.put("wUserIp", userIp);
			
			memberReturnService.insertReturnRequestItem(itemMap);
		}
		//webhookController.returnRequestWebHook(rtnRequestVO.getOrderReference(), rtnRequestVO.getRegNo(), rtnRequestVO.getKoblNo());
		//mgrRtnService.updateOrderRegistInReturn(rtnRequestVO.getState(),rtnRequestVO.getOrderReference());
		return "redirect:/return/list";
	}
	
	// 반품 검수 불합격 LIST 페이지 출력
	@RequestMapping(value = "/return/return/D005/{orderReference}", method = RequestMethod.GET) 
	public String getD005tPage(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("orderReference") String orderReference) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = mgrRtnService.selectReturnInspReadOne(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			e.printStackTrace();
		}
		 
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne);
	
		return "user/return/readExpressFailTest";
	}
	
	@RequestMapping(value = "/return/return/C002/{orderReference}", method = RequestMethod.GET) 
	public String getC002tPage(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("orderReference") String orderReference) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		System.out.println("orderReference 값 확인 : " + orderReference);
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = mgrRtnService.selectReturnInspReadOne(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			e.printStackTrace();
		}
		 
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne);
	
		return "user/return/readExpressSuccess";
	}
	
	@RequestMapping(value = "/return/regist/returnOthers", method = RequestMethod.GET)
	public String returnOthersTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		model.addAttribute("nationList", nationList);
		
		return "user/return/registReturnOthers";
	}
	
	@RequestMapping(value = "/return/order/test", method = RequestMethod.GET)
	public String returnOrderTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		try {
			int curPage = 1;
			String depositable = "T";
			ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
			String option = "";
			String select = "";
			String keywords = "";
			map.put("orgStation", orgStation);
			if(request.getParameter("keywords")!=null) {
				keywords = request.getParameter("keywords");
			}
			option = request.getParameter("option");
			select = request.getParameter("select");
			map.put("userId", request.getSession().getAttribute("USER_ID"));
			map.put("option", option);
			map.put("select", select);
			map.put("keywords", keywords);
			model.addAttribute("option", option);
			model.addAttribute("select", select);
			model.addAttribute("keywords", keywords);
			int totalCount = memberReturnService.getTotalOrderCnt(map);
			if(request.getParameter("page")!=null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			PagingVO paging = new PagingVO(curPage,totalCount,10,50);
			map.put("paging", paging);
			try {
				vo = memberReturnService.getReturnData(map);

			} catch (Exception e) {
				vo = null;
			}
			double depositPrice = memberReturnService.selectDepositInfo(request.getSession().getAttribute("USER_ID"));
			
			if(depositPrice<500) {
				depositable = "F";
			}
			
			model.addAttribute("depositable",depositable);
			model.addAttribute("paging", paging);
			model.addAttribute("map", map);
			model.addAttribute("vo", vo);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "user/return/returnOrderList";
	}
	
	@RequestMapping(value = "/return/inspList", method = RequestMethod.GET)
	public String userReturnInspList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> inspList = new ArrayList<HashMap<String, Object>>();

		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		params.put("userId", userId);
		
		if (request.getParameter("inspType") != null) {
			params.put("inspType", request.getParameter("inspType"));
		} else {
			params.put("inspType", "");
		}
		
		if (request.getParameter("koblNo") != null) {
			params.put("koblNo", request.getParameter("koblNo"));
		}
		
		int curPage = 1;
		int totalCount = memberReturnService.selectReturnInspListCnt(params);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		params.put("paging", paging);
		
		inspList = memberReturnService.selectReturnInspList(params);
		
		model.addAttribute("inspList", inspList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		
		return "user/return/inspList";
	}
	
	@RequestMapping(value = "/return/sendOutList", method = RequestMethod.GET)
	public String userReturnSendOutList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> inspList = new ArrayList<HashMap<String, Object>>();

		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		params.put("userId", userId);
		
		if (request.getParameter("inspType") != null) {
			params.put("inspType", request.getParameter("inspType"));
		} else {
			params.put("inspType", "");
		}
		
		if (request.getParameter("koblNo") != null) {
			params.put("koblNo", request.getParameter("koblNo"));
		}
		
		int curPage = 1;
		int totalCount = memberReturnService.selectReturnSendOutListCnt(params);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		params.put("paging", paging);
		
		inspList = memberReturnService.selectReturnSendOutList(params);
		
		model.addAttribute("inspList", inspList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		
		
		return "user/return/sendOutList";
	}
	
	@RequestMapping(value = "/return/discardList", method = RequestMethod.GET)
	public String userReturnDiscardList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		

		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> inspList = new ArrayList<HashMap<String, Object>>();

		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		params.put("userId", userId);
		
		if (request.getParameter("inspType") != null) {
			params.put("inspType", request.getParameter("inspType"));
		} else {
			params.put("inspType", "");
		}
		
		if (request.getParameter("koblNo") != null) {
			params.put("koblNo", request.getParameter("koblNo"));
		}
		
		int curPage = 1;
		int totalCount = memberReturnService.selectReturnDiscardListCnt(params);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		params.put("paging", paging);
		
		inspList = memberReturnService.selectReturnDiscardList(params);
		
		model.addAttribute("inspList", inspList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);

		return "user/return/discardList";
	}
	
	@RequestMapping(value = "/return/orderInfo/taxReturn", method = RequestMethod.GET)
	public String cstmrReturnTaxInfoPopup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		
		ReturnRequestVO orderInfo = new ReturnRequestVO();
		orderInfo = mgrRtnService.selectTaxReturnInfo(nno);
		model.addAttribute("orderInfo", orderInfo);
		
		return "user/return/popupTaxReturn";
	}

}