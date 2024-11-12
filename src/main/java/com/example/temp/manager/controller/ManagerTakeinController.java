package com.example.temp.manager.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.params.AbstractHttpParams;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.xmlbeans.soap.SOAPArrayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.aci.service.impl.ApiServiceImpl;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerInvoiceService;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.ManagerTakeinService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.RackVO;
import com.example.temp.manager.vo.StockCheckVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.takein.StockVO;
import com.example.temp.manager.vo.takein.TakeInStockVO;
import com.example.temp.manager.vo.takein.TakeinInfoVO;
import com.example.temp.manager.vo.takein.TakeinItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderItemVO;
import com.example.temp.manager.vo.takein.TakeinOrderListVO;
import com.example.temp.manager.vo.takein.TakeinOutStockVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.service.MemberTakeinService;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import ch.qos.logback.core.property.FileExistsPropertyDefiner;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import oracle.net.aso.h;


@Controller
public class ManagerTakeinController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ManagerTakeinService mgrTakeinService;
	
	@Autowired
	ManagerInvoiceService mgrInvService;
	
	@Autowired
	ManagerService mgrService;
	
	@Autowired 
	YongSungAPI ysApi;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiServiceImpl apiServiceImpl;
	
	@Autowired
	CJApi cjApi;
	
	@Autowired
	MemberService usrService;
	
	@Autowired
	MemberTakeinService usrTakeinService;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	
	@RequestMapping(value = "/mngr/takein/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<CJParameterVo> cJParameters = new ArrayList<CJParameterVo>();
		for(int i= 0 ; i < 3 ; i++) {
			CJParameterVo prameter = new CJParameterVo();
			prameter.setHawbNo("361000000083");
			cJParameters.add(prameter);
		}
		
		String orderType = "N";
		cjApi.cjPrint(request, response, cJParameters, orderType);
		
		return "";
	}
	 

	@RequestMapping(value = "/mngr/takein/takeinInfoList", method = RequestMethod.GET)
	public String managerAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		ArrayList<TakeinInfoVO> takeinInfo = new ArrayList<TakeinInfoVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("takeinCode",request.getParameter("takeinCode"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		parameterInfo.put("appvYn",request.getParameter("appvYn"));
		parameterInfo.put("useYn",request.getParameter("useYn"));
		
		if(parameterInfo.get("useYn") == null) {
			 parameterInfo.put("useYn","Y"); 
		}
		
		int curPage = 1;
		int totalCount = mgrTakeinService.takeinInfoTotalCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		parameterInfo.put("paging", paging);
		
		try {
			takeinInfo = mgrTakeinService.takeinInfoList(parameterInfo);
		} catch (Exception e) {
			
		}

		model.addAttribute("takeinInfo", takeinInfo);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/takein/takeinInfoList";
	}
	

	@RequestMapping(value = "/mngr/takein/popupTakeininfoReg", method = RequestMethod.GET)
	public String popupTakeininfoReg(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		TakeinInfoVO  takeinInfo = new TakeinInfoVO ();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("takeInCode", request.getParameter("takeinCode"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		
		takeinInfo = mgrTakeinService.selectTakeinInfo(parameterInfo);
			
		System.out.println(takeinInfo.getItemDetail());
		
		model.addAttribute("takeinInfo", takeinInfo);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/takein/popupTakeininfoReg";
		
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeininfoUp", method = RequestMethod.GET)
	public String popupTakeininfoUp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		TakeinInfoVO  takeinInfo = new TakeinInfoVO ();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("takeInCode", request.getParameter("takeInCode"));
		parameterInfo.put("groupIdx", request.getParameter("groupIdx"));
		
		/*
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		*/
		
		
		HashMap<String, Object> takeItemInfoRst = new HashMap<String, Object>();
		
		
		takeItemInfoRst = mgrTakeinService.selectTakeinItemInfo(parameterInfo);

		//parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		//takeinInfo = mgrTakeinService.selectTakeinInfo(parameterInfo);
		//System.out.println(takeinInfo.getItemDetail());
		
		model.addAttribute("takeItemInfo", takeItemInfoRst);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/takein/popupTakeininfoUp";
		
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakein", method = RequestMethod.GET)
	public String managerPopupTakein(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {	
		return "adm/rls/takein/popupTakeininfo";
	}

	@RequestMapping(value="/mngr/takein/popupTakeinInfo",method=RequestMethod.GET)
	public String mangerPopuptakeinInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		//parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));

		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		//parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("takeinCode",request.getParameter("takeinCode"));
		
		ArrayList<CurrencyVO> currList = new ArrayList<CurrencyVO>();
		currList = mgrTakeinService.selectCurencyList(parameterInfo);
				
		
		TakeinInfoVO takeInInfo = new TakeinInfoVO();
		takeInInfo = mgrTakeinService.selectTakeInCode(parameterInfo);

		model.addAttribute("currList", currList);
		model.addAttribute("takeInInfo",takeInInfo); 
		return "adm/rls/takein/popupTakeininfo";
	}
	

	@RequestMapping(value="/mngr/takein/popupTakeinStockList",method=RequestMethod.GET)
	public String mangerPopupTakeinStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
			parameterInfo.put("userId",request.getParameter("userId"));
			parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
			
		
			ArrayList<TakeInStockVO> takeInStocList = new ArrayList<TakeInStockVO>();   
			takeInStocList = mgrTakeinService.selectTakeinStockGroupIdx(parameterInfo);
			
			model.addAttribute("params", parameterInfo);
			model.addAttribute("takeInStocList",takeInStocList); 
			return "adm/rls/takein/popupTakeinStockList";
		}
	
	@RequestMapping(value = "/mngr/takein/updateManuDate", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrTakeinUpdateManuDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<>();
		
		try {
		
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orgStation", request.getParameter("orgStation"));
			params.put("groupIdx", request.getParameter("groupIdx"));
			params.put("userId", request.getParameter("userId"));
			params.put("mnDate", request.getParameter("mnDate"));
			
			mgrTakeinService.updateManuDate(params);
			
			rst.put("status", "success");
			rst.put("msg", "success");
		} catch (Exception e) {
			System.out.println(e.toString());
			rst.put("status", "fail");
			rst.put("msg", e.getMessage());
		}
		
		return rst;
	}
	
	
	@RequestMapping(value="/mngr/takein/popupTakeinOutList",method=RequestMethod.GET)
	public String mangerPopupTakeinOutList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		
		ArrayList<String> userList = new ArrayList<>();
		userList = mgrTakeinService.selectTakeinOutStockUserList(parameterInfo);
		
		model.addAttribute("userList", userList);
		
		if (request.getParameter("selectUserId") != null) {
			parameterInfo.put("userId", request.getParameter("selectUserId"));
		}
		
		//System.out.println(parameterInfo.get("orgStation"));
		
		ArrayList<TakeinOutStockVO> takeinOutStockList = new ArrayList<TakeinOutStockVO>();   
		takeinOutStockList = mgrTakeinService.selectTakeinOutStock(parameterInfo);
		
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("takeinOutStockList",takeinOutStockList); 
		return "adm/rls/takein/popupTakeinOutList";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinStockInReg", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonTakeinStockIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
		if(parameterInfo.get("groupIdx") == null) {
			parameterInfo.put("groupIdx","");
		};
		parameterInfo.put("useYn",request.getParameter("useYn"));
		if(parameterInfo.get("useYn") == null) {
			parameterInfo.put("useYn","Y");
		};
		
		parameterInfo.put("cusInvNo",request.getParameter("cusInvNo"));
		if(parameterInfo.get("cusInvNo") == null) {
			parameterInfo.put("cusInvNo","");
		};
		
		parameterInfo.put("remark",request.getParameter("remark"));
		if(parameterInfo.get("remark") == null) {
			parameterInfo.put("remark","");
		};
		
		String mnDate = "";
		
		if (request.getParameter("mnDate") != null) {
			mnDate = request.getParameter("mnDate");
		}
		
		parameterInfo.put("mnDate", mnDate);
		parameterInfo.put("whInCtn",request.getParameter("whInCtn"));
		parameterInfo.put("inSpector",request.getParameter("inSpector"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("rackCode",request.getParameter("rackCode"));
		parameterInfo.put("whInCtn",request.getParameter("whInCtn"));
		parameterInfo.put("whInDate",request.getParameter("whInDate"));
		parameterInfo.put("cusInvNo",request.getParameter("cusInvNo"));
		parameterInfo.put("cusSupplier",request.getParameter("cusSupplier"));
		parameterInfo.put("cusSupplierAddr",request.getParameter("cusSupplierAddr"));
		parameterInfo.put("cusSupplierTel",request.getParameter("cusSupplierTel"));
		parameterInfo.put("cusSupplierTel",request.getParameter("cusSupplierTel"));
		parameterInfo.put("whInType",request.getParameter("whInType"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrTakeinService.insertManagerTakeinStockIn(parameterInfo);
	
		return Rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/takeinStockUp", method = RequestMethod.PATCH)
	@ResponseBody
	public HashMap<String, Object> managerJsonTakeinStockUp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
		if(parameterInfo.get("groupIdx") == null) {
			parameterInfo.put("groupIdx","");
		};
		parameterInfo.put("useYn",request.getParameter("useYn"));
		if(parameterInfo.get("useYn") == null) {
			parameterInfo.put("useYn","Y");
		};
		
		parameterInfo.put("cusInvNo",request.getParameter("cusInvNo"));
		if(parameterInfo.get("cusInvNo") == null) {
			parameterInfo.put("cusInvNo","");
		};
		
		parameterInfo.put("remark",request.getParameter("remark"));
		if(parameterInfo.get("remark") == null) {
			parameterInfo.put("remark","");
		};
		
		String mnDate = "";
		if (request.getParameter("mnDate") != null) {
			mnDate = request.getParameter("mnDate");
		}
		
		parameterInfo.put("whInCtn",request.getParameter("whInCtn"));
		parameterInfo.put("inSpector",request.getParameter("inSpector"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("rackCode",request.getParameter("rackCode"));
		parameterInfo.put("whInCnt",request.getParameter("whInCnt"));
		parameterInfo.put("whInDate",request.getParameter("whInDate"));
		parameterInfo.put("cusInvNo",request.getParameter("cusInvNo"));
		parameterInfo.put("cusSupplier",request.getParameter("cusSupplier"));
		parameterInfo.put("cusSupplierAddr",request.getParameter("cusSupplierAddr"));
		parameterInfo.put("cusSupplierTel",request.getParameter("cusSupplierTel"));
		parameterInfo.put("whInType",request.getParameter("whInType"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		parameterInfo.put("mnDate", mnDate);
		

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrTakeinService.updateManagerTakeinStockUp(parameterInfo);

	
		return Rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/TakeinIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonTakeinIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		parameterInfo.put("hsCode", request.getParameter("hsCode"));
		parameterInfo.put("itemBarcode", request.getParameter("itemBarcode"));
		parameterInfo.put("brand", request.getParameter("brand"));
		parameterInfo.put("itemDetail", request.getParameter("itemDetail"));
		parameterInfo.put("nativeItemDetail", request.getParameter("nativeItemDetail"));
		parameterInfo.put("itemOption", request.getParameter("itemOption"));
		parameterInfo.put("unitValue", request.getParameter("unitValue"));
		parameterInfo.put("unitCurrency", request.getParameter("unitCurrency"));
		parameterInfo.put("wta", request.getParameter("wta")); 
		parameterInfo.put("wtc",  request.getParameter("wtc"));
		parameterInfo.put("wtUnit", request.getParameter("wtUnit"));
		parameterInfo.put("qtyUnit",  request.getParameter("qtyUnit"));
		parameterInfo.put("itemUrl", request.getParameter("itemUrl"));
		parameterInfo.put("itemImgUrl", request.getParameter("itemImgUrl"));
		parameterInfo.put("itemMeterial", request.getParameter("itemMeterial"));
		parameterInfo.put("itemDiv", request.getParameter("itemDiv"));
		parameterInfo.put("makeCntry", request.getParameter("makeCntry"));
		parameterInfo.put("makeCom", request.getParameter("makeCom"));
		parameterInfo.put("itemColor", request.getParameter("itemColor"));
		parameterInfo.put("itemSize", request.getParameter("itemSize"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		
		Rst = mgrTakeinService.insertMangerTakeinInfo(parameterInfo);

		return Rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/TakeinUp", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonTakeinUp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		parameterInfo.put("hsCode", request.getParameter("hsCode"));
		parameterInfo.put("itemBarcode", request.getParameter("itemBarcode"));
		parameterInfo.put("brand", request.getParameter("brand"));
		parameterInfo.put("itemDetail", request.getParameter("itemDetail"));
		parameterInfo.put("nativeItemDetail", request.getParameter("nativeItemDetail"));
		parameterInfo.put("itemOption", request.getParameter("itemOption"));
		parameterInfo.put("unitValue", request.getParameter("unitValue"));
		parameterInfo.put("unitCurrency", request.getParameter("unitCurrency"));
		parameterInfo.put("wta", request.getParameter("wta")); 
		parameterInfo.put("wtc",  request.getParameter("wtc"));
		parameterInfo.put("wtUnit", request.getParameter("wtUnit"));
		parameterInfo.put("qtyUnit",  request.getParameter("qtyUnit"));
		parameterInfo.put("itemUrl", request.getParameter("itemUrl"));
		parameterInfo.put("itemImgUrl", request.getParameter("itemImgUrl"));
		parameterInfo.put("itemMeterial", request.getParameter("itemMeterial"));
		parameterInfo.put("itemDiv", request.getParameter("itemDiv"));
		parameterInfo.put("makeCntry", request.getParameter("makeCntry"));
		parameterInfo.put("makeCom", request.getParameter("makeCom"));
		parameterInfo.put("useYn", request.getParameter("useYn"));
		parameterInfo.put("appvYn", request.getParameter("appvYn"));
		parameterInfo.put("makeCom", request.getParameter("makeCom"));
		parameterInfo.put("itemColor", request.getParameter("itemColor"));
		parameterInfo.put("itemSize", request.getParameter("itemSize"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		
		Rst = mgrTakeinService.updateMangerTakeinInfo(parameterInfo);

		return Rst;
	}


	@RequestMapping(value = "/mngr/takein/takeinInAppv", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerJsonTakeinInAppv(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("takeInCode", request.getParameter("takeinCode"));
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
	
 		HashMap<String, Object> Rst2 = new HashMap<String, Object>();
		Rst2 = mgrTakeinService.updateTakeinAppv(parameterInfo);
		
		Rst2.put("rstStatus", "SUCCESS");
			
		return Rst;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinItemList", method = RequestMethod.GET)
	public String managerTakeinItemList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<TakeinItemVO> takeinItemList = new ArrayList<TakeinItemVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));

		int curPage = 1;
		int totalCount = mgrTakeinService.takeinItemTotalCnt(parameterInfo);
	
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);
		
		try {
			takeinItemList = mgrTakeinService.takeinItemList(parameterInfo);
		} catch (Exception e) {
			
		}

		model.addAttribute("takeinItemList", takeinItemList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/takein/takeinItemList";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderList", method = RequestMethod.POST)
	public String managerDeletetakeinOrderList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] nnoList = request.getParameter("delTarget").split(",");
		String[] targetUserList = request.getParameter("delTargetUser").split(",");
		mgrService.deleteOrderList(nnoList, targetUserList, member.getUsername(), request.getRemoteAddr());
		return "redirect:/mngr/takein/takeinOrderList";
	}
	
	
	@RequestMapping(value = "/mngr/takein/takeinOrderList", method = RequestMethod.GET)
	public String mangerTakeinOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("hawbNo",request.getParameter("hawbNo"));
		parameterInfo.put("transCode",request.getParameter("transCode"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectTakeinOrderListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);
		
		
		ArrayList<TransComVO> transComList = new ArrayList<TransComVO>();
		transComList = mgrTakeinService.selectTakeinOrderListTransCode(parameterInfo);
		
		ArrayList<TakeinOrderListVO> takeinOrderList = new ArrayList<TakeinOrderListVO>();
		takeinOrderList = mgrTakeinService.selectTakeinOrderList(parameterInfo);
		for (int i = 0; i < takeinOrderList.size(); i++) {
			takeinOrderList.get(i).dncryptData();
		}
		
		model.addAttribute("orgStation", orgStation);
		model.addAttribute("adminId", (String) request.getSession().getAttribute("USER_ID"));
		model.addAttribute("takeinOrderList",takeinOrderList); 
		model.addAttribute("transComList",transComList);
		model.addAttribute("parameterInfo",parameterInfo);
		model.addAttribute("paging", paging);
		
		return "adm/rls/takein/takeinInOrderList";
	}
	
	
	@RequestMapping(value = "/mngr/takein/dev/takeinOrderList", method = RequestMethod.GET)
	public String managerTakeinOrderListDev(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("hawbNo",request.getParameter("hawbNo"));
		parameterInfo.put("transCode",request.getParameter("transCode"));
		parameterInfo.put("cneeName", request.getParameter("cneeName"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectTakeinOrderListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);
		
		ArrayList<TransComVO> transComList = new ArrayList<TransComVO>();
		transComList = mgrTakeinService.selectTakeinOrderListTransCode(parameterInfo);
		
		ArrayList<TakeinOrderListVO> takeinOrderList = new ArrayList<TakeinOrderListVO>();
		takeinOrderList = mgrTakeinService.selectTakeinOrderListDev(parameterInfo);
		for (int i = 0; i < takeinOrderList.size(); i++) {
			takeinOrderList.get(i).dncryptData();
			
			parameterInfo = new HashMap<>();
			parameterInfo.put("orgStation", orgStation);
			parameterInfo.put("userId", takeinOrderList.get(i).getUserId());
			parameterInfo.put("nno", takeinOrderList.get(i).getNno());
			
			ArrayList<TakeinOrderItemVO> takeinOrderItem = mgrTakeinService.selectTakeinOrderItemDev(parameterInfo);
			takeinOrderList.get(i).setTakeinOrderItem(takeinOrderItem);
		}
		
		model.addAttribute("orgStation", orgStation);
		model.addAttribute("adminId", (String) request.getSession().getAttribute("USER_ID"));
		model.addAttribute("takeinOrderList",takeinOrderList); 
		model.addAttribute("transComList",transComList);
		model.addAttribute("parameterInfo",parameterInfo);
		model.addAttribute("paging", paging);
		return "adm/rls/takein/takeinInOrderList_dev";
	}


	
	@RequestMapping(value = "/mngr/aplctList/modifyTakeinOrderOne", method = RequestMethod.GET)
	public String managerTakeinModifyOne(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = comnService.selectUserRegistOrderOne(request.getParameter("nno"));
		
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new ArrayList<UserOrderItemVO>();
		userOrderItem = mgrService.selectUserRegistOrderItemOne(userOrder);
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		ArrayList<String> userOrgNation = mgrService.selectUserOrgNation(userOrder.getUserId());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(userOrder.getUserId());
		
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		
		
		String transCom = request.getParameter("transCode");
		
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
		model.addAttribute("targetCode",transCom);
		
		
		model.addAttribute("nationList", nationList);
		model.addAttribute("types", request.getParameter("types"));
		model.addAttribute("userOrder", userOrder);
		model.addAttribute("userOrderItem", userOrderItem);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		
		return "adm/rls/takein/modifyTakeinOrderOne";
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeinBlScan", method = RequestMethod.GET)
	public String managerPopupTakeinOut(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		

		
		HashMap<String, Object> Rst = new HashMap<String, Object>(); 
		Rst = mgrTakeinService.selectWorkCnt(parameterInfo);
		Rst.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		
		model.addAttribute("Rst",Rst);

		return "adm/rls/takein/popupTakeinBlScan";
	}
	
	@RequestMapping(value = "/mngr/takein/takeInHawbChk", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeInHawbChk(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		String hawbNo = request.getParameter("hawbNo");
		if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
			hawbNo = hawbNo.substring(2,hawbNo.length());
			if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
				hawbNo = hawbNo.substring(4,hawbNo.length());
				if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
					hawbNo = hawbNo.substring(2,hawbNo.length());
				}
			}
		}
		
		parameterInfo.put("hawbNo", hawbNo);
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstMsg", parameterInfo.get("hawbNo")); 
		Rst  = mgrTakeinService.selectTakeinHawbChk(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderDetail", method = RequestMethod.GET)
	public String mangerTakeinOrderDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String rtnUrl = "";
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("hawbNo",request.getParameter("hawbNo"));
		
		
		TakeinOrderListVO  takeinOrderList = new TakeinOrderListVO();
		takeinOrderList = mgrTakeinService.selectTakeinOrder(parameterInfo);
		takeinOrderList.dncryptData();

		
		parameterInfo.put("nno",takeinOrderList.getNno());
		parameterInfo.put("userId",takeinOrderList.getUserId());

		mgrTakeinService.deleteTmpStockOut(parameterInfo);

		String adminId = (String) request.getSession().getAttribute("USER_ID");
		model.addAttribute("adminId", adminId);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("takeinOrderList", takeinOrderList);	
		
		rtnUrl = "adm/rls/takein/popupTakeinStockOut";
		/*
		if (adminId.equals("itsel2")) {
			rtnUrl = "adm/rls/takein/popupTakeinStockOutV2";	
		}
		*/
		return rtnUrl;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinStockInV2", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerTakeInStockInV2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("stockNo", request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		rst = mgrTakeinService.insertManagerTmpTakeinStockOutV2(parameterInfo);
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/takein/takeInStockIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managertakeInStockIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("stockNo", request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstMsg", parameterInfo.get("stockNo"));
		
		Rst = mgrTakeinService.insertManagerTmpTakeinStockOut(parameterInfo);
		System.out.println(Rst);

		return Rst;
	}
	
	@RequestMapping(value = "/mngr/takein/takeInEtcStockIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeInEtcStockIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("stockNo", request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstMsg", parameterInfo.get("stockNo"));
		
		Rst = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);

		return Rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/takeInEtcStockInAll", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeInEtcStockInAll(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		//parameterInfo.put("stockNo", request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstMsg", parameterInfo.get("stockNo"));
		ArrayList<HashMap<String, Object>> itemInfos =  mgrTakeinService.selectTakeinOrderItemCnt(parameterInfo.get("nno"));
		
		
		try {
			for(int i = 0; i < itemInfos.size(); i++) {
				boolean stopFlag = false;
				
				if(itemInfos.get(i).get("itemBarcode").equals("")) {
					HashMap<String,Object> stockParam = new HashMap<String,Object>();
					stockParam.put("itemCnt", itemInfos.get(i).get("itemCnt"));
					stockParam.put("takeInCode", itemInfos.get(i).get("takeInCode"));
					ArrayList<String> stockNoList = new ArrayList<String>();
					stockNoList = mgrTakeinService.selectStockList(stockParam);
					for(int stockIndex = 0; stockIndex < stockNoList.size(); stockIndex++ ) {
						parameterInfo.put("stockNo", stockNoList.get(stockIndex));
						HashMap<String, Object> RstTakeInStockReturn = new HashMap<String, Object>();
						RstTakeInStockReturn = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);
						if(!RstTakeInStockReturn.get("rstCode").equals("S10")) {
							System.out.println("********************");
							System.out.println(RstTakeInStockReturn.get("rstMsg").toString());
							System.out.println(RstTakeInStockReturn.get("rstMsg").toString());
							System.out.println(RstTakeInStockReturn.get("rstMsg").toString());
							System.out.println(RstTakeInStockReturn.get("rstMsg").toString());
							System.out.println(RstTakeInStockReturn.get("rstMsg").toString());
							stopFlag = true;
							break;
						}
					}
				}else {
					for(int j = 0; j < Integer.parseInt(itemInfos.get(i).get("itemCnt").toString()); j ++) {
						//itemInfos.get(i).get("takeInCode");
						parameterInfo.put("stockNo",itemInfos.get(i).get("itemBarcode"));
						Rst = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);
						if(!Rst.get("rstCode").equals("S10")) {
							System.out.println("********************");
							System.out.println(Rst.get("rstMsg").toString());
							System.out.println(Rst.get("rstMsg").toString());
							System.out.println(Rst.get("rstMsg").toString());
							System.out.println(Rst.get("rstMsg").toString());
							System.out.println(Rst.get("rstMsg").toString());
							stopFlag = true;
							break;
						}
					}
					/*
					if (itemInfos.get(i).get("takeInCode").toString().equals("AAA0312")) {
						
					} else {
						for(int j = 0; j < Integer.parseInt(itemInfos.get(i).get("itemCnt").toString()); j ++) {
							//itemInfos.get(i).get("takeInCode");
							parameterInfo.put("stockNo",itemInfos.get(i).get("itemBarcode"));
							Rst = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);
							if(!Rst.get("rstCode").equals("S10")) {
								stopFlag = true;
								break;
							}
						}	
					}
					*/
				}
				if(stopFlag) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		//Rst = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);

		return Rst;
	}
	
		
	@RequestMapping(value = "/mngr/takein/takeInOrderItemJson", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<TakeinOrderItemVO> managerTakeInOrderItemJson(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		
		ArrayList<TakeinOrderItemVO> takeinOrderItem = new ArrayList<TakeinOrderItemVO>();
		takeinOrderItem = mgrTakeinService.selectTakeinOrderItem(parameterInfo);

		return takeinOrderItem;
	}
	
	@RequestMapping(value = "/mngr/takein/TakeinHawbIn", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerTakeinHawbInV2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			String userId = request.getSession().getAttribute("USER_ID").toString();
			String userIp = request.getRemoteAddr();
			
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
			parameterInfo.put("nno", request.getParameter("nno"));
			parameterInfo.put("userId", request.getParameter("userId"));
			parameterInfo.put("wUserId", userId);
			parameterInfo.put("wUserIp", userIp);
			
			parameterInfo.put("boxCnt",request.getParameter("boxCnt")); 
			parameterInfo.put("wta",request.getParameter("wta"));
			
			//parameterInfo.put("wtc",request.getParameter("wtc")); 
			parameterInfo.put("wtc","0");

			parameterInfo.put("wtUnit",request.getParameter("wtUnit"));
			parameterInfo.put("dimUnit",request.getParameter("dimUnit"));
			parameterInfo.put("per",request.getParameter("per"));
			
			
			String nno = parameterInfo.get("nno").toString();
			
/*	
			String transCode = mgrService.selectUserTransComByNno(nno);
			ProcedureVO rtnVal = new ProcedureVO();

			if(transCode.equals("YSL")) {
				if(ysApi.selectMatchNoByNNo(nno) == null){
					String ysRtn = ysApi.fnMakeYongsungJson(nno);
					rtnVal = ysApi.getYongSungRegNo(ysRtn, nno, userId, userIp);
					if(rtnVal.getRstStatus().equals("FAIL")) {
						return "adm/rls/takein/popupTakeinBlScan";
					}
				}
			}
*/

			rst = mgrTakeinService.managerTakeStockHawbin(parameterInfo);


			String[] arrayParamWidth = request.getParameterValues("width[]");
			String[] arrayParamHeight = request.getParameterValues("height[]");
			String[] arrayParamLength = request.getParameterValues("length[]");
			
			
			double per =  Double.parseDouble(request.getParameter("per"));

			/*
			if(request.getParameter("dimUnit").equals("IN")) {
				per = per/2.54;
			}
			*/

			for(int index=0 ;index < arrayParamWidth.length;index++) {
				double width = 0;
				double height = 0;
				double length = 0;
				double wtc = 0 ;

				if(!arrayParamWidth[index].equals("")||arrayParamWidth[index] == null) {
					//width = Integer.parseInt(arrayParamWidth[index]);
					width = Double.parseDouble(arrayParamWidth[index]);
				}
				if(!arrayParamHeight[index].equals("")||arrayParamHeight[index] == null) {
					//height = Integer.parseInt((String)arrayParamHeight[index]);
					height = Double.parseDouble((String)arrayParamHeight[index]);
				}
				if(!arrayParamLength[index].equals("")||arrayParamLength[index] == null) {
					//length = Integer.parseInt(arrayParamLength[index]);
					length = Double.parseDouble(arrayParamLength[index]);
				}

				wtc  = width * height * length / per;

				if(wtc >0) {
					parameterInfo.put("width",width);
					parameterInfo.put("height",height);
					parameterInfo.put("length",length);
					parameterInfo.put("per",per);
					mgrTakeinService.managerTakeVolumeIn(parameterInfo);
				}
			}
			rst.put("rstCode", "S10");
		} catch (Exception e) {
			rst.put("rstCode", "F10");
			e.printStackTrace();
		}
		
		return rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/TakeinHawbIn", method = RequestMethod.GET)
	public String managerTakeinHawbIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getRemoteAddr();
		
		try {
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("wUserId", userId);
		parameterInfo.put("wUserIp", userIp);
		
		parameterInfo.put("boxCnt",request.getParameter("boxCnt")); 
		parameterInfo.put("wta",request.getParameter("wta"));
		
		//parameterInfo.put("wtc",request.getParameter("wtc")); 
		parameterInfo.put("wtc","0");

		parameterInfo.put("wtUnit",request.getParameter("wtUnit"));
		parameterInfo.put("dimUnit",request.getParameter("dimUnit"));
		parameterInfo.put("per",request.getParameter("per"));
		
		
		String nno = parameterInfo.get("nno").toString();
		String transCode = mgrService.selectUserTransComByNno(nno);
		ProcedureVO rtnVal = new ProcedureVO();
		
		if(transCode.equals("YSL")) {
			if(ysApi.selectMatchNoByNNo(nno) == null){
				String ysRtn = ysApi.fnMakeYongsungJson(nno);
				rtnVal = ysApi.getYongSungRegNo(ysRtn, nno, userId, userIp);
				if(rtnVal.getRstStatus().equals("FAIL")) {
					return "adm/rls/takein/popupTakeinBlScan";
				}
			}
		}

		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = mgrTakeinService.managerTakeStockHawbin(parameterInfo);


		String[] arrayParamWidth = request.getParameterValues("width[]");
		String[] arrayParamHeight = request.getParameterValues("height[]");
		String[] arrayParamLength = request.getParameterValues("length[]");
		
		
		double per =  Double.parseDouble(request.getParameter("per"));

		/*
		if(request.getParameter("dimUnit").equals("IN")) {
			per = per/2.54;
		}
		*/

		for(int index=0 ;index < arrayParamWidth.length;index++) {
			double width = 0;
			double height = 0;
			double length = 0;
			double wtc = 0 ;

			if(!arrayParamWidth[index].equals("")||arrayParamWidth[index] == null) {
				width = Integer.parseInt(arrayParamWidth[index]);
			}
			if(!arrayParamHeight[index].equals("")||arrayParamHeight[index] == null) {
				height = Integer.parseInt((String)arrayParamHeight[index]);
			}
			if(!arrayParamLength[index].equals("")||arrayParamLength[index] == null) {
				length = Integer.parseInt(arrayParamLength[index]);
			}

			wtc  = width * height * length / per;

			if(wtc >0) {
				parameterInfo.put("width",width);
				parameterInfo.put("height",height);
				parameterInfo.put("length",length);
				parameterInfo.put("per",per);
				mgrTakeinService.managerTakeVolumeIn(parameterInfo);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(transCode.equals("ARA")) {
//			ApiOrderListVO apiOrderList = new ApiOrderListVO();
//			ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
//			
//			apiOrderList = mgrService.selectOrderListAramex(nno);
//			apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
//			apiOrderList.dncryptData();
//			apiOrderItemList = mgrService.selectOrderItemAramex(nno);
//			if (apiOrderList.getCneeEmail().equals(""))
//				apiOrderList.setCneeEmail("-");
//
//			if (apiOrderList.getShipperEmail().equals(""))
//				apiOrderList.setShipperEmail("-");
//
//			for (int i = 0; i < apiOrderItemList.size(); i++) {
//				apiOrderItemList.get(i).setNno(apiOrderList.getNno());
//			}
//			
//			apiOrderList.setUserLength(parameterInfo.get("length").toString());
//			apiOrderList.setUserHeight(parameterInfo.get("height").toString());
//			apiOrderList.setUserWidth(parameterInfo.get("width").toString());
//			
//			ShipmentCreationResponse resultAramex = new ShipmentCreationResponse();
//			resultAramex = apiServiceImpl.aramexApi(apiOrderList, apiOrderItemList, request);
//		}
		
		return "redirect:/mngr/takein/popupTakeinBlScan";
	}
	
	
	@RequestMapping(value = "/mngr/takein/pdfTakeInPopup", method = RequestMethod.GET)
	public String managerpdfTakeInPopup(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		
		parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
		parameterInfo.put("stockNo",request.getParameter("stockNo"));
		createPdfTakeIn(request,response,model,parameterInfo);

		return "adm/rls/takein/pdfPopup";
		
	}

	@RequestMapping(value = "/mngr/takein/takeinEtcOrder", method = RequestMethod.GET)
	public String managerTakeinEtcOrder(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("trkCom", request.getParameter("trkCom"));
		parameterInfo.put("trkNo", request.getParameter("trkNo"));
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectEtcOrderListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 50);
		parameterInfo.put("paging", paging);


		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list = mgrTakeinService.selectEtcOrderList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		return "adm/rls/takein/takeinEtcOrder";		
	}

	@RequestMapping(value = "/mngr/takein/popupTakeinEtcOrderUp", method = RequestMethod.GET)
	public String managerPopupTakeinEtcOrderUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String nno = request.getParameter("nno");
		String userId = request.getParameter("userId");
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		params.put("userId", userId);
		params.put("orgStation", orgStation);
		
		HashMap<String, Object> etcOrderList = new HashMap<String, Object>();
		etcOrderList = mgrTakeinService.selectEtcOrderInfo(params);
		
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = mgrInvService.selectCurrencyList(params);
		
		ArrayList<HashMap<String, Object>> etcOrderItem = new ArrayList<HashMap<String, Object>>();
		etcOrderItem = mgrTakeinService.selectEtcOrderItemInfo(params);
		
		model.addAttribute("takeinItem", etcOrderItem);
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("takeinInfo", etcOrderList);
		
		return "adm/rls/takein/popupEtcOrderUp";
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeinEtcOrder", method = RequestMethod.GET)
	public String managerPopupTakeinEtcOrder(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> boxSizeList = new ArrayList<HashMap<String, Object>>();
		
		
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		currencyList = mgrInvService.selectCurrencyList(parameterInfo);
		boxSizeList = mgrInvService.selectBoxSizeList("");
		

		ArrayList<HashMap<String, Object>> rst = new ArrayList<HashMap<String, Object>>(); 
		rst = mgrTakeinService.selectGEA1101();
		model.addAttribute("rst", rst);
		
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("boxSizeList", boxSizeList);
		return "adm/rls/takein/popupTakeinEtcOrder";
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeinUserItems", method = RequestMethod.GET)
	public String managerPopupTakeinUserItems(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		
		
		ArrayList<HashMap<String, Object>> cusItemList = new ArrayList<HashMap<String, Object>> ();
		cusItemList = mgrTakeinService.selectUserCusItemList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("cusItemList", cusItemList);
		return "adm/rls/takein/popupTakeinUserItems";		
	}
	
	@RequestMapping(value = "/mngr/takein/EtcOrderIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object>  managerEtcOrderIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
	
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderDate", request.getParameter("orderDate"));
		parameterInfo.put("trkCom", request.getParameter("trkCom"));
		parameterInfo.put("trkNo", request.getParameter("trkNo"));
		parameterInfo.put("shippingFee", request.getParameter("shippingFee"));
		parameterInfo.put("remark", request.getParameter("remark"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		parameterInfo.put("etcCurrency", request.getParameter("etcCurrency"));
		parameterInfo.put("boxType", request.getParameter("boxType"));
		
		
		String nno = mgrTakeinService.seletTakeInNno();
		parameterInfo.put("nno", nno);
	
		float shippingFee = 0;
		
		if(!request.getParameter("shippingFee").isEmpty()) {
			shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		}

		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst.put("rstStatus","SUCCESS");
		
		
		if(shippingFee>0) {
			parameterInfo.put("etcValue", shippingFee);
			parameterInfo.put("etcType", "ETC_FEE");
			parameterInfo.put("etcDate", request.getParameter("orderDate"));
			rst = mgrInvService.mgrEctInUp(parameterInfo);
						
			if(!rst.get("rstStatus").equals("SUCCESS")) {
				return rst;
			};
		}
		
		
		String[] takeInCode = request.getParameterValues("takeInCode[]");
		String[] itemCnt = request.getParameterValues("itemCnt[]");
		
		mgrTakeinService.insertTbTakeinEtcOrder(parameterInfo);
		
	    int subNo = 1;
		for(int index = 0;index < takeInCode.length ; index++) {
			
			parameterInfo.put("takeInCode",takeInCode[index]);
			parameterInfo.put("itemCnt",itemCnt[index]);
			parameterInfo.put("subNo",subNo+index);
			mgrTakeinService.insertTbTakeinEtcOrderItem(parameterInfo);
		}


		return rst;
	}
	
	@RequestMapping(value = "/mngr/takein/EtcOrderUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerEtcOrderUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", request.getParameter("nno"));
		params.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		params.put("userId", request.getParameter("userId"));
		params.put("orderDate", request.getParameter("orderDate"));
		params.put("trkCom", request.getParameter("trkCom"));
		params.put("trkNo", request.getParameter("trkNo"));
		params.put("shippingFee", request.getParameter("shippingFee"));
		params.put("remark", request.getParameter("remark"));
		params.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		params.put("wUserIp", request.getRemoteAddr());
		params.put("etcCurrency", request.getParameter("etcCurrency"));
		
		float shippingFee = 0;
		
		if (!request.getParameter("shippingFee").isEmpty()) {
			shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		}
		
		rst.put("rstStatus", "SUCCESS");
		
		if (shippingFee > 0) {
			params.put("idx", request.getParameter("etcIdx"));
			params.put("etcValue", shippingFee);
			params.put("etcType", "ETC_FEE");
			params.put("etcDate", request.getParameter("orderDate"));
			rst = mgrInvService.mgrEctInUp(params);
			
			if (!rst.get("rstStatus").equals("SUCCESS")) {
				return rst;
			}
		}
		
		String[] takeInCode = request.getParameterValues("takeInCode[]");
		String[] itemCnt = request.getParameterValues("itemCnt[]");
		
		mgrTakeinService.updateTbTakeinEtcOrder(params);
		mgrTakeinService.deleteTbTakeinEtcOrderItem(params);
		
		int subNo = 1;
		for(int index = 0;index < takeInCode.length ; index++) {
			
			params.put("takeInCode",takeInCode[index]);
			params.put("itemCnt",itemCnt[index]);
			params.put("subNo",subNo+index);
			mgrTakeinService.insertTbTakeinEtcOrderItem(params);
		}

		return rst;
	}
	
	@RequestMapping(value = "/mngr/takein/cancelTakeinOrderRegist", method = RequestMethod.POST)
	@ResponseBody
	public String managerCancelTakeinOrderReg(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="targets[]") ArrayList<String> nnoList) throws Exception {
		String result = "";
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		try {
			for (int i = 0; i < nnoList.size(); i++) {
				parameterInfo.put("nno", nnoList.get(i));
				int cnt = mgrTakeinService.selectTbEtcCnt(parameterInfo);
				if (cnt > 0) {
					mgrInvService.deleteTakeinEtc(parameterInfo);
				}
				mgrTakeinService.SpTakeinEtcCancel(parameterInfo);
			}
			
			result = "S";
		} catch (Exception e) {
			result = "F";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/cancelTakeinOrderStockOut", method = RequestMethod.POST)
	public void managerCancelTakeinOrderStockOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		String nno = request.getParameter("nno");
		String userId = request.getParameter("userId");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", orgStation);
		params.put("nno", nno);
		params.put("userId", userId);
		
		try {
			
			mgrTakeinService.SpStockOutCancel(params);
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
	}
	
	@RequestMapping(value = "/mngr/takein/takeinCusItemInJson", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinCusItemInJson(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		 		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = mgrTakeinService.selectSpUserCusItemCHK(parameterInfo);
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/takein/userIdChk", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinUserIdChkJson(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		 		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		rst = mgrTakeinService.selectUserIdCHK(parameterInfo);
		
		return rst;
	}
	
	
	@RequestMapping(value = "/mngr/takein/popupTakeinEtcStockOut", method = RequestMethod.GET)
	public String managerpopupTakeinEtcStockOut(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("nno", request.getParameter("nno"));
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> boxSizeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> takeInEtcOrder = new HashMap<String, Object>();
		
		
		//mgrTakeinService.deleteTmpStockOut(parameterInfo);
		takeInEtcOrder = mgrTakeinService.selectTakeInEtcOrder(parameterInfo);
		currencyList = mgrInvService.selectCurrencyList(parameterInfo);
		boxSizeList = mgrInvService.selectBoxSizeList(request.getParameter("nno"));
		
		model.addAttribute("boxSizeList", boxSizeList);
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("takeInEtcOrder", takeInEtcOrder);
		return "adm/rls/takein/popupTakeinEtcStockOut";
		
	}
	
	@RequestMapping(value = "/mngr/takein/takeInEtcItemJson", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<HashMap<String, Object>> managerTakeInEtcItemJson(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		
		ArrayList<HashMap<String, Object>> etcItems = new ArrayList<HashMap<String, Object>>();
	
		etcItems = mgrTakeinService.selectEtcItems(parameterInfo);
		
				
		return etcItems;
	}

	@RequestMapping(value = "/mngr/takein/takeInEtcStockOut", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeInEtcStockOut(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("trkCom", request.getParameter("trkCom"));
		parameterInfo.put("trkNo", request.getParameter("trkNo"));
		parameterInfo.put("remark", request.getParameter("remark"));
		parameterInfo.put("orderDate", request.getParameter("orderDate"));

		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		float shippingFee = 0;
		
		if(!request.getParameter("shippingFee").isEmpty()) {
			shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		}
		
		mgrTakeinService.updateEtcOrder(parameterInfo);
		
		
		parameterInfo.put("idx", request.getParameter("etcIdx"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		Rst = mgrInvService.mgrEctDel(parameterInfo);
	
		if(shippingFee>0) {
			parameterInfo.put("idx", request.getParameter("etcIdx"));
			parameterInfo.put("etcValue", shippingFee);
			parameterInfo.put("etcCurrency", request.getParameter("etcCurrency"));
			parameterInfo.put("etcType", "ETC_FEE");
			parameterInfo.put("etcDate", request.getParameter("orderDate"));
			rst = mgrInvService.mgrEctInUp(parameterInfo);
		}
	
		rst = mgrTakeinService.SpTakeinEtcOut(parameterInfo);

		return rst;
	}

	@RequestMapping(value = "/mngr/board/createPdfTakeIn", method = RequestMethod.GET)
	@ResponseBody
	public void createPdfTakeIn(HttpServletRequest request, HttpServletResponse response, Model model,
			HashMap<String, Object> stockResultList) throws Exception {
	
	
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		
		String barcodePath = pdfPath2 +  stockResultList.get("groupIdx") + ".JPEG";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
	
		final PDDocument doc = new PDDocument();
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);
		float perMM = 1 / (10 * 2.54f) * 72;
		
		try {
			int marginTop = 30;
			/*for (int pdfIndex = 0; pdfIndex < stockResultList.size(); pdfIndex++) {*/
			/* StockResultVO tempResultVO = new StockResultVO(); */
			
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				HashMap<String,Object> parameterInfo = new HashMap<String,Object>();
				parameterInfo.put("groupIdx", stockResultList.get("groupIdx"));
				parameterInfo.put("stockNo", stockResultList.get("stockNo"));
	
				parameterInfo.put("gruopIdx", stockResultList.get("groupIdx"));
				parameterInfo.put("stockNo", stockResultList.get("stockNo"));
	
				tempStockVoList = mgrTakeinService.selectTakeInStockByGrpIdx(parameterInfo);
				// 컨텐츠 스트림 열기
				for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
					PDRectangle	asd = new PDRectangle(80*perMM, 60*perMM);
					PDPage blankPage = new PDPage(asd);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(stockIndex);
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createCode128(tempStockVoList.get(stockIndex).getStockNo());
					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);
	
					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);
	
					
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					
					int fontSize = 12; // Or whatever font size you want.
					float titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getWhStatusName()) / 1000 * fontSize;
					contentStream.drawImage(pdImage, (80*perMM - 220) / 2, 37, 220f,35f);
					
					drawText(tempStockVoList.get(stockIndex).getWhStatusName(),NanumGothic, 12, (80*perMM - titleWidth) / 2, 145,contentStream);
					
					fontSize = 14;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getCusItemCode()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getCusItemCode(),NanumGothic, 14, (80*perMM - titleWidth) / 2, 130,contentStream);
					
					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getUserId()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getUserId(),NanumGothic, 10, (80*perMM - titleWidth) / 2, 117,contentStream);
					
					/*
					fontSize = 11;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getRackCode()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getRackCode(),NanumGothic, 11, (80*perMM - titleWidth) / 2, 102,contentStream);
					*/
					
					fontSize = 12;
					String itemDetailSub = tempStockVoList.get(stockIndex).getItemDetail();
					if(NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize > 80*perMM) {
						itemDetailSub = itemDetailSub.substring(0,30);
						itemDetailSub = itemDetailSub+"...";
					}
					
					
					titleWidth = NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize;
					drawText(itemDetailSub,NanumGothic, 12, (80*perMM - titleWidth) / 2, 90,contentStream);
					
					fontSize = 13;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getStockNo()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getStockNo(),NanumGothic, 13, (80*perMM - titleWidth) / 2, 25,contentStream);
					contentStream.close();
					
					
					
			
				}
				
				
	
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		
		// PDF 파일 출력
		doc.save(response.getOutputStream());
		
		
		/*
	    PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPageable(new PDFPageable(doc));
	    job.print();
	    */
		
		
		doc.close();
		
		File barcodefile = new File(barcodePath); 
		if(barcodefile.exists()) {
			barcodefile.delete(); 
		}
	
		
	}

	private void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream contentStream) throws Exception {
	    contentStream.beginText(); 
	    contentStream.setFont(font, fontSize);
	    contentStream.newLineAtOffset(left, bottom);
	    contentStream.showText(text);
	    contentStream.endText();
	}
	
	@RequestMapping(value = "/mngr/takein/takeInStockInBatchAll", method = RequestMethod.POST)
	@ResponseBody
	public String managertakeInStockInBatchAll(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getRemoteAddr();
		String[] temp = null;
		ArrayList<String> nnoList = new ArrayList(Arrays.asList(request.getParameterValues("datas[]")));
		int arrayLenth = nnoList.size();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("wUserId", userId);
		parameterInfo.put("wUserIp", userIp);
		for(int i = 0 ; i < arrayLenth; i++) {
			double wta = 0;
			String nno = nnoList.get(i);
			
			HashMap<String, Object> takeInHawbChkResult = new HashMap<String, Object>();
			parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
			String hawbNo = comnService.selectHawbNoByNNO(nno);
			if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
				hawbNo = hawbNo.substring(2,hawbNo.length());
				if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
					hawbNo = hawbNo.substring(4,hawbNo.length());
					if(comnService.selectTransCodeFromHawb(hawbNo) == null || comnService.selectTransCodeFromHawb(hawbNo).equals("")) {
						hawbNo = hawbNo.substring(2,hawbNo.length());
					}
				}
			}
			parameterInfo.put("hawbNo", hawbNo);
			takeInHawbChkResult  = mgrTakeinService.selectTakeinHawbChk(parameterInfo);
			if(!takeInHawbChkResult.get("rstStatus").equals("FAIL")) {
				TakeinOrderListVO  takeinOrderList = new TakeinOrderListVO();
				takeinOrderList = mgrTakeinService.selectTakeinOrder(parameterInfo);
				parameterInfo.put("nno",nno);
				parameterInfo.put("userId",takeinOrderList.getUserId());
				
				mgrTakeinService.deleteTmpStockOut(parameterInfo);
				
				ArrayList<HashMap<String,Object>> itemInfoList = new ArrayList<HashMap<String,Object>>();
				itemInfoList = mgrTakeinService.selectStockByNno(nno);
						
				for(int j = 0; j < itemInfoList.size(); j++) {
					int successCnt = 0;
					ArrayList<String> stockNoList = new ArrayList<String>();
					parameterInfo.put("takeInCode", itemInfoList.get(j).get("takeInCode"));
					stockNoList = mgrTakeinService.selectStockList(itemInfoList.get(j));
					for(int stockIndex = 0; stockIndex < stockNoList.size(); stockIndex++ ) {
						parameterInfo.put("stockNo", stockNoList.get(stockIndex));
						HashMap<String, Object> RstTakeInStockReturn = new HashMap<String, Object>();
						
						RstTakeInStockReturn = mgrTakeinService.insertManagerTmpTakeinStockOut(parameterInfo);
						if(!RstTakeInStockReturn.get("rstStatus").equals("FAIL")) {
							successCnt++;
						}
					}
					TakeinInfoVO takeInInfo = mgrTakeinService.selectTakeinInfo(parameterInfo);
					wta += stockNoList.size()*Double.parseDouble(takeInInfo.getWta());
				}

				parameterInfo.put("boxCnt",1); 
				parameterInfo.put("wta",wta);
				
				//parameterInfo.put("wtc",request.getParameter("wtc")); 
				parameterInfo.put("wtc","0");

				parameterInfo.put("wtUnit","KG");
				parameterInfo.put("dimUnit","CM");
				parameterInfo.put("per","6000");
				
				String transCode = mgrService.selectUserTransComByNno(nno);
				ProcedureVO rtnVal = new ProcedureVO();
				
				if(transCode.equals("YSL")) {
					if(ysApi.selectMatchNoByNNo(nno) == null){
						String ysRtn = ysApi.fnMakeYongsungJson(nno);
						rtnVal = ysApi.getYongSungRegNo(ysRtn, nno, userId, userIp);
						if(rtnVal.getRstStatus().equals("FAIL")) {
							return "adm/rls/takein/popupTakeinBlScan";
						}
					}
				}

				HashMap<String, Object> rst = new HashMap<String, Object>();
				rst = mgrTakeinService.managerTakeStockHawbin(parameterInfo);
			}
		}
		
		return "redirect:/mngr/takein/popupTakeinBlScan";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinStockBarcodeBatch", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerTakeInStockBarcodeBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameterInfo = new HashMap<>();
		HashMap<String, Object> rst = new HashMap<>();
		
		try {
			
			parameterInfo.put("nno", request.getParameter("nno"));
			parameterInfo.put("userId", request.getParameter("userId"));
			parameterInfo.put("orgStation", request.getParameter("orgStation"));
			parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("wUserIp", request.getRemoteAddr());
			
			rst = mgrTakeinService.spWhoutStockTakeinBatch(parameterInfo);
			
			if ("F10".equals((String)rst.get("rstCode"))) {
				rst.put("rstStatus", "FAIL");
				rst.put("rstMsg", "A SQL Exception occured while inserting data");
				rst.put("rstCode", "F10");
			} else {
				rst.put("rstStatus", "SUCCESS");
				rst.put("rstMsg", "Success");
				rst.put("rstCode", "S10");
			}
			
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
			rst.put("rstStatus", "FAIL");
			rst.put("rstMsg", "A null pointer exception occured");
			rst.put("rstCode", "E10");
		} catch (SQLServerException e) {
			System.err.println(e.getMessage());
			rst.put("rstStatus", "FAIL");
			rst.put("rstMsg", "A database connection exception occured");
			rst.put("rstCode", "E10");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rst.put("rstStatus", "FAIL");
			rst.put("rstMsg", "A unknown exception occured");
			rst.put("rstCode", "E20");
		}

		return rst;
	}
	
	@RequestMapping(value = "/mngr/takein/takeInStockInBatch", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managertakeInStockInBatch(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("nno", request.getParameter("nno"));
		parameterInfo.put("userId", request.getParameter("userId"));
		//parameterInfo.put("stockNo", request.getParameter("stockNo"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstMsg", parameterInfo.get("stockNo"));
		ArrayList<HashMap<String, Object>> itemInfos =  mgrTakeinService.selectTakeinOrderItemCntBatch(parameterInfo.get("nno"));
		for(int i = 0; i < itemInfos.size(); i++) {
			boolean stopFlag = false;
			if(itemInfos.get(i).get("itemBarcode").equals("")) {
				HashMap<String,Object> stockParam = new HashMap<String,Object>();
				stockParam.put("itemCnt", itemInfos.get(i).get("itemCnt"));
				stockParam.put("takeInCode", itemInfos.get(i).get("takeInCode"));
				ArrayList<String> stockNoList = new ArrayList<String>();
				stockNoList = mgrTakeinService.selectStockList(stockParam);
				for(int stockIndex = 0; stockIndex < stockNoList.size(); stockIndex++ ) {
					parameterInfo.put("stockNo", stockNoList.get(stockIndex));
					HashMap<String, Object> RstTakeInStockReturn = new HashMap<String, Object>();
					RstTakeInStockReturn = mgrTakeinService.insertManagerTmpTakeinStockOut(parameterInfo);
					if(!RstTakeInStockReturn.get("rstCode").equals("S10")) {
						stopFlag = true;
						break;
					}
				}
			}else {
				for(int j = 0; j < Integer.parseInt(itemInfos.get(i).get("itemCnt").toString()); j ++) {
					//itemInfos.get(i).get("takeInCode");
						parameterInfo.put("stockNo",itemInfos.get(i).get("itemBarcode"));
					Rst = mgrTakeinService.insertManagerTmpTakeinStockOut(parameterInfo);
					if(!Rst.get("rstCode").equals("S10")) {
						stopFlag = true;
						break;
						
					}
				}
			}
			if(stopFlag) {
				break;
			}
		}
		//Rst = mgrTakeinService.insertManagerTmpTakeinEtcStockOut(parameterInfo);

		return Rst;
	}
	
	// 사입 삭제 추가
	@RequestMapping(value = "/mngr/takein/takeinOrderDel", method = RequestMethod.POST)
	public String takeinOrderDel(HttpServletRequest request, HttpServletResponse response, Model model, String targetParm) throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//String[] nnoList = request.getParameter("delTarget").split(",");
		String[] nnoList = request.getParameterValues("nno[]");
		String[] targetUserList = request.getParameter("delTargetUser").split(",");
		mgrTakeinService.deleteTakeinOrder(nnoList, targetUserList, member.getUsername(), request.getRemoteAddr());		
		
		return "redirect:/mngr/takein/takeinOrderList";
				
	}

	// 사입 주문 등록 팝업
	@RequestMapping(value = "/mngr/takein/popupTakeinReg", method = RequestMethod.GET)
	public String takeinOrderRegist(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
		userList = mgrTakeinService.getTakeinCusInfo();
		
		model.addAttribute("userList", userList);
		return "adm/rls/takein/popupTakeinOrder";
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeinOrderInfo", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> takeinOrderRegistInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		String userId = request.getParameter("userId");
		if (userId != null) {
			System.out.println(userId);
			ArrayList<String> userOrgNation = usrService.selectUserOrgNation(userId);
			params.put("userOrgNation", userOrgNation);
			ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(userId);
			UserVO userInfo = usrService.selectUserInfo(userId);
			ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(params);
			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			nationList = comnService.selectNationCode();
			ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
			transComList = usrService.selectTrkComList(userId);
			data.put("transComList", transComList);
			data.put("nationList", nationList);
			data.put("userInfo", userInfo);
			data.put("userOrgNation", userOrgNation);
			data.put("userOrgStation", userOrgStation);
			data.put("userDstnNation", userDstnNation);
					
		}
		
		return data;
	}
	
	
	@RequestMapping(value = "/mngr/takein/takeinOrderListTmp", method = RequestMethod.GET)
	public String takeinOrderListTmp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("orderNo", request.getParameter("orderNo"));
		parameterInfo.put("transCode", request.getParameter("transCode"));
		ArrayList<TmpTakeinOrderVO> takeinList = new ArrayList<TmpTakeinOrderVO>();

		int curPage = 1;
		int totalCount = mgrTakeinService.selectTakeinOrderListCntTmp(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 20);
		parameterInfo.put("paging", paging);
		
		ArrayList<TransComVO> transComList = new ArrayList<TransComVO>();
		transComList = mgrTakeinService.selectTakeinOrderListTransCode(parameterInfo);
		
		try {
			takeinList = mgrTakeinService.selectTmpTakeinUserList(parameterInfo);
			ArrayList<TmpTakeinOrderItemListVO> orderItem = new ArrayList<TmpTakeinOrderItemListVO>();
			HashMap<String, Object> itemParams = new HashMap<String, Object>();
			itemParams.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			itemParams.put("takeinList", takeinList);
			
			try {
				orderItem = mgrTakeinService.selectTmpTakeinUserOrderItem(itemParams);
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			
			for (int i = 0; i < takeinList.size(); i++) {
				String errMsg = "";
				String cneeTel = "";
				
				takeinList.get(i).dncryptData();
				
				HashMap<String, String> itemParams2 = new HashMap<String, String>();
				
				for (int j = 0; j < orderItem.size(); j++) {
					if (takeinList.get(i).getNno().equals(orderItem.get(j).getNno())) {
						TmpTakeinOrderItemListVO orderItem2 = new TmpTakeinOrderItemListVO();
						orderItem2 = orderItem.get(j);
						takeinList.get(i).getOrderItem().add(orderItem2);
						orderItem.remove(j);
						j = -1;
					}
				}
				
				if (takeinList.get(i).getCneeHp().equals("")) {
					cneeTel = takeinList.get(i).getCneeTel();
				} else {
					cneeTel = takeinList.get(i).getCneeHp();
				}
				
				if (Integer.parseInt(takeinList.get(i).getAlready()) != 0) {
					errMsg = errMsg + " 이미 등록된 주문번호 /";
				}
				
				if (Integer.parseInt(takeinList.get(i).getDupCnt()) != 1) {
					errMsg = errMsg + " 중복된 주문번호 /";
				}
				
				if (takeinList.get(i).getOrgStationName().equals("")) {
					errMsg = errMsg + " 출발도시코드 오류 /";
				}
				
				if (takeinList.get(i).getDstnNationName().equals("")) {
					errMsg = errMsg + " 도착국가 오류 /";
				}
				
				if (cneeTel.equals("")) {
					errMsg = errMsg + " 수취인 연락처 누락 /";
				}
				
				if (takeinList.get(i).getCneeName().equals("")) {
					errMsg = errMsg + " 수취인명 누락 /";
				}
				
				if (takeinList.get(i).getDstnNationName().equals("")) {
					errMsg = errMsg + " 도착 국가 누락 /";
				}
				
				if (!takeinList.get(i).getTransCode().equals("CJ") && !takeinList.get(i).getTransCode().equals("YSL") && !takeinList.get(i).getTransCode().equals("ITC") &&!takeinList.get(i).getTransCode().equals("HJ")) {
					if(takeinList.get(i).getCneeCity().equals("")) {
						errMsg = errMsg+" 수취인 도시 누락 /";
					}
				}
				
				if(takeinList.get(i).getTransCode().equals("CJ")) {
					
					if(takeinList.get(i).getCneeZip().length() < 5) {
						errMsg = errMsg+" 우편번호 오류 /";
					}
					
				}
				
				if(takeinList.get(i).getCneeAddr().equals("")) {
					errMsg = errMsg+" 수취인 주소누락 /";
				}
				
				if( takeinList.get(i).getOrderItem().size()==0) {
					errMsg = errMsg+" 등록 되지 않은 상품코드 /";
				}
				
				if(!takeinList.get(i).getStatus().equals("")) {
					errMsg = errMsg+usrTakeinService.selectErrorMsg(takeinList.get(i).getNno());
				}
				
				int StockError = 0;
				
				for (int x = 0; x < takeinList.get(i).getOrderItem().size(); x++) {
					if (takeinList.get(i).getOrderItem().get(x).getItemDetail().equals("")) {
						errMsg = errMsg + " 등록되지 않은 상품명 /";
					}
					
					if (takeinList.get(i).getOrderItem().get(x).getItemCnt().equals("0") || takeinList.get(i).getOrderItem().get(x).getItemCnt().equals("")) {
						errMsg = errMsg + " 상품수량 누락 /";
					}
					
					if (Integer.parseInt(takeinList.get(i).getOrderItem().get(x).getStockCnt()) < 0) {
						takeinList.get(i).getOrderItem().get(x).setStockMsg("해당 재고 수량 부족");
						StockError++;
					}
				}
				
				if (StockError > 0) {
					takeinList.get(i).setErrMsg("재고 수량 /");
				}
				
				if (errMsg.length() > 0) {
					takeinList.get(i).setErrYn("Y");
					int errMsgLen = errMsg.length();
					errMsg = errMsg.substring(0, errMsgLen-1);
				}
				
				takeinList.get(i).setErrMsg(errMsg);
				
				System.out.println(errMsg);
			}
			 
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		model.addAttribute("transComList", transComList);
		model.addAttribute("paging", paging);
		model.addAttribute("takeinList", takeinList);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rls/takein/takeinRegistOrderListTmp";
	}
	
	@RequestMapping(value = "/mngr/takein/registTakeinOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public String popupTakeinOrderInfoReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result = "F";


		System.out.println(request.getParameter("dstnNation"));
		System.out.println(request.getParameter("orgStation"));

		HashMap<String, String> parameters = new HashMap<String, String>();
		
		try {
			mgrTakeinService.insertUserTakeinOrderList(request, parameters);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinBlUpdateExcelDown", method = RequestMethod.POST)
	public void takeinBlUpdateExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameter.put("orderType", "TAKEIN");
		parameter.put("transCode", "EMN");
		parameter.put("fromDate", request.getParameter("fromDate"));
		parameter.put("toDate", request.getParameter("toDate"));
		
		String searchType = "";
		String searchKeywords = "";

		if(request.getParameter("fromDate")!=null) {
			parameter.put("fromDate", request.getParameter("fromDate"));
			parameter.put("toDate", request.getParameter("toDate"));
		}
		
		if(request.getParameter("keywords")!=null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		parameter.put("searchType", searchType);
		
		if(searchType.equals("0")) {
			parameter.put("userId", searchKeywords);
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", searchKeywords);
		}else if(searchType.equals("1")) {
			parameter.put("userId", searchKeywords);
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}else if(searchType.equals("3")) {
			parameter.put("userId", "");
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}else if(searchType.equals("4")) {
			parameter.put("userId", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", "");
		}else if(searchType.equals("5")) {
			parameter.put("userId", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", searchKeywords);
		}else {
			parameter.put("userId", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}
		
		mgrTakeinService.takeinEmsOrderExcelDown(request, response, parameter);
	}
	
	@RequestMapping(value= "/mngr/takein/excelUploadForBlUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String takeinBlUpdateExcelUp(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi) throws Exception {
		String result = "F";
		HashMap<String, Object> params = new HashMap<String, Object>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		params.put("orgStation", orgStation);
		
		result = mgrTakeinService.updateEmsBl(multi, request, params);
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderListTmpDel", method = RequestMethod.POST)
	public String takeinOrderListTmpDelete(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String[] arrayParam = request.getParameterValues("nno[]");
		for (int index = 0; index < arrayParam.length; index++) {
			HashMap<String, String> deleteNno = new HashMap<String, String>();
			deleteNno.put("nno", arrayParam[index]);
			
			mgrTakeinService.deleteTakeinOrderTmp(deleteNno);
		}
		
		return "redirect:/mngr/takein/takeinOrderListTmp";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderDlvIn", method = RequestMethod.POST)
	public String takeinOrderDlvIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		String[] arrayParam = request.getParameterValues("targets1");
		String[] arrayerrYn = request.getParameterValues("targets2");
		String[] userId = request.getParameterValues("userIds");
		String userIp = request.getSession().getAttribute("USER_ID").toString();
		//String blTypeOrg = request.getParameter("blType");
		String blTypeOrg = "TAKEIN";
		String transCode = "";
		
		ArrayList<String> arrayNno = new ArrayList<String>();
		for (int roop = 0; roop < arrayParam.length; roop++) {
			try {
				transCode = usrService.selectTransCodeFromNNO(arrayParam[roop]);
				
				if (!arrayerrYn[roop].equals("Y")) {
					comnService.comnBlApply(arrayParam[roop], transCode, userId[roop], userIp, blTypeOrg);
				}
			} catch (Exception e) {
				logger.error("Exception", e);
				continue;
			}
		}
		
		return "redirect:/mngr/takein/takeinOrderListTmp";
	}
	
	@RequestMapping(value = "/mngr/takein/apply/excelFormDown", method = RequestMethod.GET)
	public String managerTakeinExcelFormDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String root = request.getSession().getServletContext().getRealPath("/");
	    String savePath =root + "WEB-INF/jsp/user/takein/";

	    // 서버에 실제 저장된 파일명
	    String filename = "userUploadFormTakeinSample.xlsx" ;
	    // 실제 내보낼 파일명
	    String orgfilename = "userUploadFormTakeinSample.xlsx";

	    InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	    
	    try{
	        // 파일을 읽어 스트림에 담기
	        try{
	            file = new File(savePath, filename);
	            in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }
	
	        client = request.getHeader("User-Agent");
	 
	        // 파일 다운로드 헤더 지정
	        response.reset() ;
	        response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename=userUploadFormTakein.xlsx");

	        if(!skip){

	            // IE
	            if(client.indexOf("MSIE") != -1){
	                response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
	 
	            }else{
	                // 한글 파일명 처리
	                orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
	
	                response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
	                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	            } 
	             
	            response.setHeader ("Content-Length", ""+file.length() );
	
	            os = response.getOutputStream();
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;
	             
	            while( (leng = in.read(b)) > 0 ){
	                os.write(b,0,leng);
	            }
	 
	        }else{
	            response.setContentType("text/html;charset=UTF-8");
	        }

	        in.close();
	        os.close();
	 
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return "redirect:/mngr/takein/takeinOrderListTmp";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderListAll", method = RequestMethod.GET)
	public String managerTakeinOrderListAll(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int curPage = 1;
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>>();
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		parameter.put("wUserId", member.getUsername());
		parameter.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameter.put("orderType", "TAKEIN");
		String searchType = "";
		String searchKeywords = "";
		
		ArrayList<HashMap<String, String>> transCodeList = new ArrayList<HashMap<String, String>>();
		transCodeList = comnService.selectTransCodeList((String) request.getSession().getAttribute("ORG_STATION"));
		
		model.addAttribute("transCodeList", transCodeList);
		
		if(request.getParameter("transCode")!=null) {
			if (!request.getParameter("transCode").equals("")) {
				parameter.put("transCode", request.getParameter("transCode"));
			}
		}
		
		if(request.getParameter("fromDate")!=null) {
			parameter.put("fromDate", request.getParameter("fromDate"));
			parameter.put("toDate", request.getParameter("toDate"));
		}
		
		if(request.getParameter("keywords")!=null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		parameter.put("searchType", searchType);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", request.getParameter("keywords"));
		if(searchType.equals("0")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", searchKeywords);
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", searchKeywords);
		}else if(searchType.equals("1")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}else if(searchType.equals("3")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}else if(searchType.equals("4")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", "");
		}else if(searchType.equals("5")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", searchKeywords);
		}else {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}
		
		int totalCount = mgrService.selectShpngListCount(parameter);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20,50);
		// paging 끝

		parameter.put("paging", paging);
		
		try {
			shpngList = mgrService.selectShpngList(parameter, "TAKEIN");
		} catch (Exception e) {
			// TODO: handle exception
			shpngList = null;
		}
		
		model.addAttribute("params", parameter);
		model.addAttribute("shpngList", shpngList);
		model.addAttribute("paging",paging);
		
		return "adm/rls/takein/takeinOrderListAll";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinEmsOrderExcelDown", method = RequestMethod.POST)
	public void managerTakeinEmsOrderExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String[] nnoList = request.getParameter("delTarget").split(",");
		mgrTakeinService.takeinEmsOrderExcelDown(nnoList, request, response);
	}
	
	@RequestMapping(value = "/mngr/takein/modifyAdminOne", method = RequestMethod.GET)
	public String managerTakeinModifyAdminOne(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			UserOrderListVO userOrder = new UserOrderListVO();
			userOrder = comnService.selectUserRegistOrderOne(request.getParameter("nno"));
			
			userOrder.dncryptData();
			ArrayList<UserOrderItemVO> userOrderItem = new ArrayList<UserOrderItemVO>();
			userOrderItem = mgrService.selectUserRegistOrderItemOne(userOrder);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
	
			ArrayList<String> userOrgNation = mgrService.selectUserOrgNation(userOrder.getUserId());
			ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(userOrder.getUserId());
			ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
			
			parameters.put("userOrgNation", userOrgNation);
	//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
			ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			nationList = comnService.selectNationCode();
			
			taxTypeList = mgrService.selectTaxTypeList();
			String transCom = request.getParameter("transCode");
			
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
			model.addAttribute("targetCode",transCom);
			model.addAttribute("taxTypeList", taxTypeList);
			
			
			model.addAttribute("nationList", nationList);
			model.addAttribute("types", request.getParameter("types"));
			model.addAttribute("userOrder", userOrder);
			model.addAttribute("userOrderItem", userOrderItem);
			model.addAttribute("userOrgStation", userOrgStation);
			model.addAttribute("userDstnNation", userDstnNation);
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		
		return "adm/rls/takein/modifyAdminOneAll";
	}
	
	@RequestMapping(value = "/mngr/takein/modifyAdminOne", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinModifyAdminOnePOST(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		String result = "F";
		
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		ArrayList<OrderInspVO> userOrderListRegacy = new ArrayList<OrderInspVO>  ();
		userOrderListRegacy = mgrService.selectOrderInspcItem(userOrderList.getNno());
		
		Date time = new Date();
		String time1 = format1.format(time);
		/*orderList*/
		/* userOrderList.setDstnNation(userOrderList.getDstnNation()); */
		userOrderList.setDstnStation(userOrderList.getDstnNation());
		userOrderList.setOrgStation((String)request.getSession().getAttribute("ORG_STATION"));
		userOrderList.setOrderType("TAKEIN");


		/*orderItem*/
		/* userOrderItems = request.getParameter(name) */
		userOrderList.setUserId(userOrderListRegacy.get(0).getUserId());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);
		
		try {
			String[] orderNno = null;
			userOrderList.encryptData();
			mgrService.updateUserOrderList(userOrderList, userOrderItemList); 
			result = "S";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/popupTakeinOrderInfoUp", method = RequestMethod.POST)
	public String takeinOrderInfoUpdate(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String userId = request.getParameter("userId");
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(userId);
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(userId);
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = usrService.selectUserOrderOne(request.getParameter("nno"));

		UserVO userInfo =  usrService.selectUserInfo(userId);
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new  ArrayList<UserOrderItemVO>();
		userOrderItem = usrService.selectUserOrderItemOne(userOrder);
		
		model.addAttribute("urlType",request.getParameter("urlType"));
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);

		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		String transCom = userOrder.getTransCode();
		model.addAttribute("nationList", nationList);
		model.addAttribute("userOrder",userOrder);
		model.addAttribute("userOrderItem",userOrderItem);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("transCode",transCom);
		
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
		
		return "adm/rls/takein/takeinOrderInfoUp";
	}
	
	@RequestMapping(value = "/mngr/takein/modifyTakeinOrderInfoUp", method = RequestMethod.POST)
	@ResponseBody
	public String userModifyTakeinOrderInfoUp(HttpServletRequest request, HttpServletResponse response, Model model,  UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		String result = "F";
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		Date date = new Date();
		String time = format.format(date);
		
		userOrderList.setUserId(request.getParameter("userId"));
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time);
		
		try {
			usrService.updateUserOrderListARA(userOrderList, userOrderItemList);
			
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/registTakeinOrderInfo2", method = RequestMethod.GET)
	public String registTakeinOrderInfo2(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
		userList = mgrTakeinService.getTakeinCusInfo();
		
		model.addAttribute("userList", userList);
		
		return "adm/rls/takein/registTakeinOrder";
	}
	
	@RequestMapping(value = "/mngr/takein/nationList", method = RequestMethod.POST)
	@ResponseBody
	public Object userTakeinNationList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		String userId = request.getParameter("userId");
	
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(userId);
		//ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(request.getParameter("userId"));
		parameterInfo.put("userOrgNation", userOrgNation);
		UserVO userInfo = usrService.selectUserInfo(userId);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameterInfo);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userOrgStation", userOrgStation);
		data.put("nationList", nationList);
		data.put("userInfo", userInfo);
		
		return data;
		
	}
	
	// 사입 랙 관리
	// 랙 관리 화면
	@RequestMapping(value = "/mngr/takein/rack/rackCodeList", method = RequestMethod.GET)
	public String managerTakeinRackCodeList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<RackVO> rackList = new ArrayList<RackVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		parameterInfo.put("searchType", searchType);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeywords", searchKeywords);
		
		if (searchType.equals("0")) {
			parameterInfo.put("rackName", searchKeywords);
			parameterInfo.put("rackCode", searchKeywords);
		} else if (searchType.equals("1")) {
			parameterInfo.put("rackName", "");
			parameterInfo.put("rackCode", searchKeywords);
		} else {
			parameterInfo.put("rackName", searchKeywords);
			parameterInfo.put("rackCode", "");
		}
		 
		int curPage = 1;
		int totalCount = mgrTakeinService.selectTakeinRackListCount(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 15);
		parameterInfo.put("paging", paging);
		
		try {
			rackList = mgrTakeinService.selectTakeinRackList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		

		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", request.getParameter("keywords"));
		model.addAttribute("rackList", rackList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rack/takeinRackCodeList";
	}
	
	
	// 랙 관리 - 랙 등록 화면
	@RequestMapping(value = "/mngr/takein/rack/popupRegistRack", method = RequestMethod.GET)
	public String managerRegistTakeinRackView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("orderBy", request.getParameter("orderBy"));
		
		HashMap<String, Object> rackInfo = new HashMap<String, Object>();
		
		try {
			rackInfo = mgrTakeinService.selectOrderByCnt(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		model.addAttribute("rackInfo", rackInfo);
		
		return "adm/rack/popupRegistTakeinRack";
	}
	
	// 랙 관리 - 랙 등록
	@RequestMapping(value = "/mngr/takein/rack/registRack", method = RequestMethod.POST)
	@ResponseBody
	public String managerRegistTakeinRack(HttpServletRequest request, HttpServletResponse response, Model model, RackVO rackInfo) throws Exception {
		String result = "F";
		
		rackInfo.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		
		try {
			mgrTakeinService.insertRackInfo(rackInfo);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}
	
	// 랙 관리 - 랙코드 체크
	@RequestMapping(value = "/mngr/takein/rack/rackCodeCheck", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinRackCodeCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = mgrTakeinService.selectRackCodeCnt(request.getParameter("rackCode"));
		if (cnt > 0) {
			return "F";
		} else {
			return "S";
		}
	}
	
	// 랙 관리 - 랙 수정 정보 조회
	@RequestMapping(value = "/mngr/takein/rack/selectRackInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object managerTakeinRackInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		String orgStation = request.getParameter("orgStation");
		String rackCode = request.getParameter("rackCode");
		
		System.out.println(orgStation);
		System.out.println(rackCode);
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("rackCode", rackCode);
		
		try {
			HashMap<String, Object> rackInfo = new HashMap<String, Object>();
			rackInfo = mgrTakeinService.selectRackInfo(parameterInfo);
		
			data.put("rackInfo", rackInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return data;
	}
	
	// 랙 관리 - 랙 수정
	@RequestMapping(value = "/mngr/takein/rack/updateRackInfo", method = RequestMethod.PATCH)
	@ResponseBody
	public String managerTakeinRackInfoUpdate(HttpServletRequest request, HttpServletResponse response, Model model, RackVO rackInfo) throws Exception {
		String result = "";
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		params.put("rackCode", request.getParameter("rackCode"));
		params.put("rackName", request.getParameter("rackName"));
		params.put("orderBy", request.getParameter("orderBy"));
		params.put("useYn", request.getParameter("useYn"));
		params.put("rackRemark", request.getParameter("rackRemark"));
		
			
		try {
			mgrTakeinService.updateTakeinRackInfo(params);
			result = "S";
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}
	
	
	
	// 랙 관리 - 랙 삭제
	@RequestMapping(value = "/mngr/takein/rack/rackCodeListDel", method = RequestMethod.GET)
	public String mangagerTakeinRackCodeListDel(HttpServletRequest request, HttpServletResponse response, Model model, String targetParm) throws Exception {

		mgrTakeinService.deleteTakeinRackInfo(request, response, targetParm);
		
		return "redirect:/mngr/takein/rack/rackCodeList";
	}

	
	// 랙 재고 관리
	@RequestMapping(value = "/mngr/takein/rack/rackStockList", method = RequestMethod.GET)
	public String managerTakeinRackStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String rackName = request.getParameter("searchRackName");
		String userId = request.getParameter("searchUserId");
		String cusItemCode = request.getParameter("searchCusItemCode");
		String itemDetail = request.getParameter("searchItemDetail");
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		
		ArrayList<RackVO> rackList = new ArrayList<RackVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("rackName", rackName);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("itemDetail", itemDetail);
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectRackStockListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
			
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 15);
		parameterInfo.put("paging", paging);
		
		try {
			rackList = mgrTakeinService.selectRackStockList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		model.addAttribute("orgStation", orgStation);
		model.addAttribute("rackName", rackName);
		model.addAttribute("userId", userId);
		model.addAttribute("cusItemCode", cusItemCode);
		model.addAttribute("itemDetail", itemDetail);
		model.addAttribute("page", request.getParameter("page"));
		model.addAttribute("paging", paging);
		model.addAttribute("rackList", rackList);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rack/takeinRackStockList";
				
	}
	
	@RequestMapping(value = "/mngr/takein/rack/stockInfoList", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectTakeinStockInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String rackCode = request.getParameter("rackCode");
		String orgStation = request.getParameter("orgStation");
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("orgStation", orgStation);
		
		ArrayList<RackVO> stockInfo = new ArrayList<RackVO>();
		stockInfo = mgrTakeinService.selectStockInfo(parameterInfo);
		
		try {
			result.put("stockInfo", stockInfo);
			result.put("result",  "S");
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/mngr/takein/rack/moveRackStockInfo", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinRackStockMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		String takeInCode = request.getParameter("takeInCode");
		String rackCode = request.getParameter("rackCode");
		String userId = request.getParameter("userId");
		String cusItemCode = request.getParameter("cusItemCode");
		int whCnt = Integer.parseInt((String)request.getParameter("whCnt"));
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("takeInCode", takeInCode);
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("whCnt", whCnt);
		
		ArrayList<HashMap<String, Object>> rackCodeList = new ArrayList<HashMap<String, Object>>();
		rackCodeList = mgrTakeinService.selectRackCodeList(parameterInfo);
		
		RackVO stockInfo = new RackVO();
		stockInfo = mgrTakeinService.selectMoveStockInfo(parameterInfo);
		
		try {
			result.put("rackCodeList", rackCodeList);
			result.put("stockInfo", stockInfo);
			result.put("result", "S");
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/rack/stockInfoUp", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinRackStockInfoUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "F";
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("rackCode", request.getParameter("rackCode"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		parameterInfo.put("takeInCode", request.getParameter("takeInCode"));
		
		System.out.println(request.getParameter("rackCode"));
		System.out.println(request.getParameter("userId"));
		System.out.println(request.getParameter("cusItemCode"));
		System.out.println(request.getParameter("takeInCode"));
		System.out.println(request.getParameter("newRackCode"));
		try {
			if(!request.getParameter("newRackCode").equals(null)) {
				System.out.println("sdfsf");
				int cnt = mgrTakeinService.selectRackCode(request.getParameter("newRackCode"));
				
				if (cnt > 0) {
					parameterInfo.put("count", Integer.valueOf(request.getParameter("count")));
					parameterInfo.put("newRackCode", request.getParameter("newRackCode"));
					mgrTakeinService.updateRackStockInfo(parameterInfo);
					result = "S";
				} else {
					result = "F";
				}
			} else {
				result = "F";
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}
	
	@RequestMapping(value  = "/mngr/takein/rack/reloadRackList", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinReloadRackList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String rackName = request.getParameter("rackName");
		String userId = request.getParameter("userId");
		String cusItemCode = request.getParameter("cusItemCode");
		String itemDetail = request.getParameter("itemDetail");

		ArrayList<RackVO> rackList = new ArrayList<RackVO>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("rackName", rackName);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("itemDetail", itemDetail);
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectCheckRackListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 15);
		parameterInfo.put("paging", paging);
		
		rackList = mgrTakeinService.selectCheckRackList(parameterInfo);
		
		try {
			result.put("rackList", rackList);
			result.put("result", "S");
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
	}

	
	// 랙 재고 파악
	@RequestMapping(value = "/mngr/takein/rack/stockCheckList", method = RequestMethod.GET)
	public String managerTakeinStockCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<StockCheckVO> checkList = new ArrayList<StockCheckVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("checkDate", request.getParameter("checkDate"));
		parameterInfo.put("rackCode", request.getParameter("rackCode"));
		parameterInfo.put("wrUserId", request.getParameter("wrUserId"));
		
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectStockCheckListCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 30);
		parameterInfo.put("paging", paging);
		
		try {
			checkList = mgrTakeinService.selectStockCheckList(parameterInfo); 
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("checkList", checkList);
		model.addAttribute("parameterInfo", parameterInfo);
		
		return "adm/rack/takeinStockCheckList";
	}
	
	@RequestMapping(value = "/mngr/takein/rack/registStockCheck", method = RequestMethod.GET)
	public String managerTakeinRegistStockCheckView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		StockCheckVO stockCheckInfo = new StockCheckVO();
		stockCheckInfo.setWrUserId(member.getUsername());
		
		model.addAttribute("stockCheckInfo", stockCheckInfo);
		
		return "adm/rack/popupRegistTakeinStockCheck";
	}
	
	@RequestMapping(value = "/mngr/takein/rack/registStockCheck", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinRegistStockCheck(HttpServletRequest request, HttpServletResponse response, StockCheckVO stockCheckVO) throws Exception {
		String result = "";
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String time1 = format.format(time);
		
		stockCheckVO.setOrgStation((String)request.getSession().getAttribute("ORG_STATION"));
		stockCheckVO.setWrUserId(member.getUsername());
		stockCheckVO.setWUserIp(request.getRemoteAddr());
		stockCheckVO.setWDate(time1);
		
		try {
			mgrTakeinService.insertStockCheckInfo(stockCheckVO);
			result = "S";
		} catch (Exception e) {
			result = "F";
			logger.error("Exception", e);
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/rack/deleteStockCheckInfo", method = RequestMethod.GET)
	public String managerTakeinDelStockCheckInfo(HttpServletRequest request, HttpServletResponse response, String targetParm) throws Exception {
		String Result = "";
		
		mgrTakeinService.deleteStockCheck(targetParm);
		Result = "redirect:/mngr/takein/rack/stockCheckList";
		
		return Result;
	}
	

	// 재고 파악 detail
	@RequestMapping(value = "/mngr/takein/rack/stockCheckInfo/{idx}", method = RequestMethod.GET)
	public String managerTakeinStockCheckInfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("idx") int idx) {
		
		String rackName = request.getParameter("searchRackName");
		String userId = request.getParameter("searchUserId");
		String cusItemCode = request.getParameter("searchCusItemCode");
		String itemDetail = request.getParameter("searchItemDetail");
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		
		ArrayList<RackVO> rackList = new ArrayList<RackVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("idx", idx);
		parameterInfo.put("rackName", rackName);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("itemDetail", itemDetail);
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectCheckRackListCnt(parameterInfo);


		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 15);
		parameterInfo.put("paging", paging);
		
		try {
			rackList = mgrTakeinService.selectCheckRackList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		model.addAttribute("orgStation", orgStation);
		model.addAttribute("rackName", rackName);
		model.addAttribute("userId", userId);
		model.addAttribute("cusItemCode", cusItemCode);
		model.addAttribute("itemDetail", itemDetail);
		model.addAttribute("page", request.getParameter("page"));
		model.addAttribute("idx", idx);
		model.addAttribute("paging", paging);
		model.addAttribute("rackList", rackList);
		model.addAttribute("parameterInfo", parameterInfo);
		
		
		return "adm/rack/takeinStockCheckInfo";
	}
	
	@RequestMapping(value = "/mngr/takein/rack/stockCheckInfo", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinReStockCheckInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String rackName = request.getParameter("rackName");
		String userId = request.getParameter("userId");
		String cusItemCode = request.getParameter("cusItemCode");
		String itemDetail = request.getParameter("itemDetail");

		ArrayList<RackVO> rackList = new ArrayList<RackVO>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("rackName", rackName);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("itemDetail", itemDetail);
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("idx", idx);
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectCheckRackListCnt(parameterInfo);


		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 5, 15);
		parameterInfo.put("paging", paging);
		
		rackList = mgrTakeinService.selectCheckRackList(parameterInfo);
		
		try {
			result.put("rackList", rackList);
			result.put("result", "S");
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
	}
	
	// 재고 리스트
	@RequestMapping(value = "/mngr/takein/rack/stockInfoList2", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectStockInfoList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		
	
		System.out.println(request.getParameter("index"));
		System.out.println(request.getParameter("rackCode"));
		System.out.println(request.getParameter("orgStation"));
		
		int idx = Integer.parseInt(request.getParameter("index"));
		String rackCode = request.getParameter("rackCode");
		String orgStation = request.getParameter("orgStation");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("idx", idx);
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("orgStation", orgStation);
		
		ArrayList<RackVO> stockInfo = new ArrayList<RackVO>();
		stockInfo = mgrTakeinService.selectStockListByRackCode(parameterInfo);
	
		try {
			result.put("stockInfo", stockInfo);
			result.put("result", "S");
			System.out.println(result);
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result; 
	}

	// 체크 수량 등록
	@RequestMapping(value = "/mngr/takein/rack/checkStockInfoUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerTakeinCheckStockInfoUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String rackCode = request.getParameter("rackCode");
		String takeInCode = request.getParameter("takeInCode");
		String cusItemCode = request.getParameter("cusItemCode");
		int idx = Integer.parseInt((String)request.getParameter("index"));
		int whCnt = Integer.parseInt((String)request.getParameter("whCnt"));
		int checkCnt = Integer.parseInt((String)request.getParameter("checkCnt"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String day = format.format(time);
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("takeInCode", takeInCode);
		parameterInfo.put("idx", String.valueOf(idx));
		parameterInfo.put("whCnt", String.valueOf(whCnt));
		parameterInfo.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("remark", request.getParameter("remark"));
		parameterInfo.put("checkCnt", String.valueOf(checkCnt));
		parameterInfo.put("wrUserId", member.getUsername());
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		parameterInfo.put("checkDate", day);
		parameterInfo.put("cusItemCode", cusItemCode);
		
		int cnt = mgrTakeinService.selectCheckStockListCount(parameterInfo);
		
		try {
			if (cnt > 0) {
				mgrTakeinService.checkStockUpdate(parameterInfo);
				result.put("page", request.getParameter("page"));
				result.put("rackCode", rackCode);
				result.put("takeInCode", takeInCode);
				result.put("cusItemCode", cusItemCode);
				result.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
				result.put("idx", idx);
				result.put("remark", request.getParameter("remark"));
				result.put("whCnt", whCnt);
				result.put("checkCnt", checkCnt);
				result.put("result", "S");
			} else {
				mgrTakeinService.checkStockInsert(parameterInfo);
				result.put("page", request.getParameter("page"));
				result.put("rackCode", rackCode);
				result.put("takeInCode", takeInCode);
				result.put("cusItemCode", cusItemCode);
				result.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
				result.put("idx", idx);
				result.put("remark", request.getParameter("remark"));
				result.put("whCnt", whCnt);
				result.put("checkCnt", checkCnt);
				result.put("result", "S");
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/mngr/takein/rack/moveRackStockInfoMoved", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerTakeinCheckStockMoveInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		String takeInCode = request.getParameter("takeInCode");
		String rackCode = request.getParameter("rackCode");
		String userId = request.getParameter("userId");
		String cusItemCode = request.getParameter("cusItemCode");
		int whCnt = Integer.parseInt((String)request.getParameter("whCnt"));
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("takeInCode", takeInCode);
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("userId", userId);
		parameterInfo.put("cusItemCode", cusItemCode);
		parameterInfo.put("whCnt", whCnt);
		
		ArrayList<HashMap<String, Object>> rackCodeList = new ArrayList<HashMap<String, Object>>();
		rackCodeList = mgrTakeinService.selectRackCodeList(parameterInfo);
		
		RackVO stockInfo = new RackVO();
		stockInfo = mgrTakeinService.selectStockInfoMoved(parameterInfo);
		
		try {
			result.put("rackCodeList", rackCodeList);
			result.put("stockInfo", stockInfo);
			result.put("result", "S");
			System.out.println(result);
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
	
	}
	
	// 재고 랙 이동 및 체크 수량 등록
	@RequestMapping(value = "/mngr/takein/rack/checkStockInfoMoveUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerTakeinCheckStockInfoMoveUp(HttpServletRequest request, 
			HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String day = format.format(time);
		
		String newRackCode = request.getParameter("newRackCode");
		String takeInCode = request.getParameter("takeInCode");
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		String rackCode = request.getParameter("rackCode");

		int whCnt = Integer.parseInt(request.getParameter("whCnt"));
		int idx = Integer.parseInt(request.getParameter("idx"));
		int count = Integer.parseInt(request.getParameter("checkCnt"));
		
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("rackCode", rackCode);
		parameterInfo.put("newRackCode", newRackCode);
		parameterInfo.put("takeInCode", takeInCode);
		parameterInfo.put("whCnt", whCnt);
		parameterInfo.put("count", count);
		parameterInfo.put("idx", idx);
		parameterInfo.put("checkCnt", count);
		parameterInfo.put("wrUserId", member.getUsername());
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		parameterInfo.put("checkDate", day);
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		
		
		parameterInfo.put("userId", request.getParameter("userId"));
		
		int cnt = mgrTakeinService.selectRackCodeCheckStock(parameterInfo);
		
		try {
			if (cnt > 0) {
				mgrTakeinService.updateRackMoveAndStockCheck(parameterInfo);
				mgrTakeinService.moveRack(parameterInfo);
				mgrTakeinService.deleteOriginalRackStockCheck(parameterInfo);
				result.put("rackCode", newRackCode);
				result.put("orgStation", orgStation);
				result.put("idx", idx);
				result.put("result", "S");
			} else {
				mgrTakeinService.insertRackMoveAndStockCheck(parameterInfo);
				mgrTakeinService.moveRack(parameterInfo);
				mgrTakeinService.deleteOriginalRackStockCheck(parameterInfo);
				result.put("rackCode", newRackCode);
				result.put("orgStation", orgStation);
				result.put("idx", idx);
				result.put("result", "S");
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/takein/takeinCancelOrder", method = RequestMethod.GET)
	public String managerTakeinCancelOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "adm/rls/takein/takeinCancelOrderList";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinCancelDetail", method = RequestMethod.GET)
	public String managerTakeinCancelOrderDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", orgStation);
		
		int curPage = 1;
		int totalCount = mgrTakeinService.selectTakeinOrderListByCancelCnt(params);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));	
		}

		PagingVO paging = new PagingVO(curPage, totalCount,10, 15);
		params.put("paging", paging);
		
		ArrayList<TakeinOrderListVO> takeinOrderList = new ArrayList<TakeinOrderListVO>();
		takeinOrderList = mgrTakeinService.selectTakeinOrderListByCancel(params);
		
		for (int i = 0; i < takeinOrderList.size(); i++) {
			takeinOrderList.get(i).dncryptData();
		}
		
		model.addAttribute("takeinOrderList", takeinOrderList);
		model.addAttribute("page", curPage);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		
		return "adm/rls/takein/takeinCancelOrderDetail";
	}
	
	@RequestMapping(value = "/mngr/takein/cancelTakeinOrder", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinOrderCancelProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetHawb = request.getParameter("hawbNo");
		String datass = "";
		String result = "";
		
		try {
			result = mgrTakeinService.execTakeinOrderDel(request, targetHawb);
			if (!result.equals("SUCCESS")) {
				throw new Exception();
			} else {
				datass = "S";
			}
		} catch (Exception e) {
			if (result.equals("FAILED")) {
				datass = "N";
			} else {
				datass = "F";	
			}
		}
		
		return datass;
	}

	@RequestMapping(value = "/mngr/takein/cancelTakeinOrderAll", method = RequestMethod.POST)
	@ResponseBody
	public String managerTakeinOrderCancelAllProc(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="targets[]") ArrayList<String> hawbNoList) throws Exception {
		String result = "";
		String datass = "";
		try {
			for (int i = 0; i < hawbNoList.size(); i++) {
				datass = mgrTakeinService.execTakeinOrderDel(request, hawbNoList.get(i));
				if (!datass.equals("SUCCESS")) {
					throw new Exception();
				} else {
					result = "S";
				}
			}
			result = "S";
		} catch (Exception e) {
			result = "F";
		}
		

		return result;
	}

	// 2023.01.06 EMS 로직 변경하면서 장승은 대리님 요청으로 BL 업데이트 기능 추가 (임시)
	@RequestMapping(value = "/mngr/takein/updateTakeinEmsOrderBl", method = RequestMethod.GET)
	public String managerTakeinEmsBlUpdate(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		
		HashMap<String, Object> blInfo = new HashMap<String, Object>();
		blInfo = mgrTakeinService.selectEmsBlInfo(params);
		
		model.addAttribute("nno", nno);
		model.addAttribute("blInfo", blInfo);
		
		return "adm/rls/takein/popupTakeinBlUpdate";
	}
	
	@RequestMapping(value = "/mngr/takein/updateTakeinEmsOrderBl", method = RequestMethod.POST)
	public void managerTakeinEmsBlUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nno = request.getParameter("nno");
		String agencyBl = request.getParameter("agencyBl");
		String transCode = request.getParameter("transCode");
		String hawbNo = request.getParameter("hawbNo");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		params.put("agencyBl", agencyBl);
		params.put("transCode", transCode);
		params.put("hawbNo", hawbNo);
		
		mgrTakeinService.updateTakeinAgencyBl(params);
	}


	@RequestMapping(value = "/mngr/takein/itemList", method = RequestMethod.POST)
	public String mngrTakeinItemList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = Integer.parseInt(request.getParameter("cnt")) + 1;
		model.addAttribute("cnt", cnt);
		String transCom = request.getParameter("selectTrans");
		
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		String dstnNation = request.getParameter("selectDstnNation");
		
		optionOrderVO = mgrService.SelectOrderListOption(transCom,dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom,dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom,dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom,dstnNation);
		
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("targetCode",transCom);
		
		
		return "adm/rls/takein/itemList";
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderListExcelDown", method = RequestMethod.POST)
	public void takeinOrderListAllExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		mgrTakeinService.takeinOrderListAllExcelDown(request, response);
	}
	
	@RequestMapping(value = "/mngr/takein/takeinOrderListExcelDownTest", method = RequestMethod.GET)
	public ResponseEntity<byte[]> takeinOrderListExcelDownTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String nowDate = format.format(date);
			
			byte[] excelBytes = mgrTakeinService.selectAllTakeinOrderList(request, response);
			
			String fileName = "order_list_"+nowDate+".xlsx";
			
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("filename", fileName);
            headers.setContentLength(excelBytes.length);

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/mngr/takein/resetTakeinScanHis", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> resetTakeinScanHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		params.put("wUserId", request.getSession().getAttribute("USER_ID"));
		params.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		
		rst = mgrTakeinService.execSpWhoutStockReset(params);
		
		System.out.println(rst);
		
		
		return rst;
	}
	
}
