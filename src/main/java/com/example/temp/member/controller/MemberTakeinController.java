package com.example.temp.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.ManagerTakeinService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.service.MemberTakeinService;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.member.vo.takein.OrgStationVO;
import com.example.temp.member.vo.takein.TakeInUserStockOutVO;
import com.example.temp.member.vo.takein.TakeinInfoVO;
import com.example.temp.member.vo.takein.TakeinUserStockListVO;
import com.example.temp.member.vo.takein.TmpTakeinCodeVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;
import com.example.temp.member.vo.takein.UserTransVO;
import com.example.temp.smtp.SmtpService;

@Controller
public class MemberTakeinController {

	@Autowired
	ManagerTakeinService mgrTakeinService;
	
	@Autowired
	ManagerService mgrService;
 
	@Autowired
	MemberTakeinService usrTakeinService;
	
	@Autowired
	MemberService usrService;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	SmtpService smtpService;
	
	
	@RequestMapping(value="/cstmr/takein/takeinOrderListTmp",method=RequestMethod.GET) 
	public String takeinOrderListTmp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, String> parameterInfo = new HashMap<String, String>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));

		String root = request.getSession().getServletContext().getRealPath("/");
		String filePath = root + "WEB-INF/jsp/user/takein/userUploadFormTakein.xlsx";
		
		parameterInfo.put("sampleExcel", filePath);

		ArrayList<TmpTakeinOrderVO> takeinList = new ArrayList<TmpTakeinOrderVO>();
		ArrayList<UserTransVO> userTransList =  new ArrayList<UserTransVO>();

		try {
			userTransList = usrTakeinService.selectUserTransCom(parameterInfo);
			takeinList = usrTakeinService.selectTmpTakeinUserList(parameterInfo);
			ArrayList<TmpTakeinOrderItemListVO> orderItem2 = new ArrayList<TmpTakeinOrderItemListVO>();
			HashMap<String, Object> itemParmeter2= new HashMap<String, Object>();
			itemParmeter2.put("userId", (String)request.getSession().getAttribute("USER_ID"));
			itemParmeter2.put("orgStation", takeinList.get(0).getOrgStation());
			itemParmeter2.put("takeinList", takeinList);
			
			try {
				orderItem2 = usrTakeinService.selectTmpTakeinUserOrderItem2(itemParmeter2);	
			}catch (Exception e) {
				e.printStackTrace();
			}
			for(int index = 0 ; index < takeinList.size(); index++) {
				
				String errMsg ="";
				String cneeTel="";

				takeinList.get(index).dncryptData();
				
				HashMap<String, String> itemParmeter= new HashMap<String, String>();

				for(int j = 0; j < orderItem2.size();j++) {
					if(takeinList.get(index).getNno().equals(orderItem2.get(j).getNno())) {
						TmpTakeinOrderItemListVO orderItem = new TmpTakeinOrderItemListVO ();
						orderItem = orderItem2.get(j);
						takeinList.get(index).getOrderItem().add(orderItem);
						orderItem2.remove(j);
						j=-1;
					}
				}
				

				if(takeinList.get(index).getCneeHp().equals("")) {
					cneeTel = takeinList.get(index).getCneeTel();
				}else {
					cneeTel = takeinList.get(index).getCneeHp();
				}
				
				if( Integer.parseInt(takeinList.get(index).getAlready()) != 0){
					errMsg = errMsg+" 이미 등록된 주문번호 /";
				}
				
				if( Integer.parseInt(takeinList.get(index).getDupCnt()) != 1){
					errMsg = errMsg+" 중복된 주문번호 /";
				}
				
				if(takeinList.get(index).getOrgStationName().equals("") ){
					errMsg = errMsg+" 출발도시코드 오류 /";
				}
				
				if(takeinList.get(index).getDstnNationName().equals("")){
					errMsg = errMsg+" 도착국가 오류 /";
				}

				if(cneeTel.equals("")) {
					errMsg = errMsg+" 수취인명 연락처  누락 /";
				}
				

				if(takeinList.get(index).getCneeName().equals("")) {
					errMsg = errMsg+" 수취인명 누락 /";
				}
				
				
				if(takeinList.get(index).getDstnNationName().equals("")) {
					errMsg = errMsg+" 도착 국가 누락 /";
				}
				
				
				if(!takeinList.get(index).getTransCode().equals("CJ")&&
						!takeinList.get(index).getTransCode().equals("YSL")&&
						!takeinList.get(index).getTransCode().equals("ITC")&&
						!takeinList.get(index).getTransCode().equals("HJ")&&
						!takeinList.get(index).getTransCode().equals("EPT")) {
					if(takeinList.get(index).getCneeCity().equals("")) {
						errMsg = errMsg+" 수취인 도시 누락 /";
					}
					if(takeinList.get(index).getCneeState().equals("")) {
						errMsg = errMsg+" 수취인 주 누락 /";
					}
				}
				
				if(takeinList.get(index).getCneeZip().equals("")) {
					errMsg = errMsg+" 수취인 우편번호 누락 /";
				} else {
					if(takeinList.get(index).getTransCode().equals("CJ") || takeinList.get(index).getTransCode().equals("HJ") 
							|| takeinList.get(index).getTransCode().equals("EPT")) {
						if(takeinList.get(index).getCneeZip().length() < 5) {
							errMsg = errMsg+" 우편번호 오류 /";
						}
					}	
				}
				
				if(takeinList.get(index).getCneeAddr().equals("")) {
					errMsg = errMsg+" 수취인 주소누락 /";
				}
				
				if(takeinList.get(index).getTransCode().equals("EPT")) {
					if (takeinList.get(index).getCneeAddrDetail().equals("")) {
						errMsg = errMsg+" 수취인 상세주소 누락 /";
					}
				}
				
				if( takeinList.get(index).getOrderItem().size()==0) {
					errMsg = errMsg+" 등록 되지 않은 상품코드 /";
				}
				
				if(!takeinList.get(index).getStatus().equals("")) {
					ArrayList<String> errString = usrTakeinService.selectErrorMsg(takeinList.get(index).getNno());
					for(int roop = 0; roop < errString.size(); roop++) {
						errMsg = errMsg+" "+errString.get(roop)+" /";
					}
					
				}
				
				if (!takeinList.get(index).getExpValue().equals("")) {
					if (takeinList.get(index).getExpBusinessName().equals("")) {
						errMsg = errMsg+" 수출화주사업자명 누락 /";
					}
					if (takeinList.get(index).getExpBusinessNum().equals("")) {
						errMsg = errMsg+" 수출화주사업자번호 누락 /";
					}
					if (takeinList.get(index).getExpValue().equals("registExplicence1")) {
						if (takeinList.get(index).getAgencyBusinessName().equals("")) {
							errMsg = errMsg+" 수출대행사업자명 누락 /";
						}
					} else if (takeinList.get(index).getExpValue().equals("registExplicence2")) {
						if (takeinList.get(index).getExpNo().equals("")) {
							errMsg = errMsg+" 수출면장번호 누락 /";
						}
					}
				}
				
				int StockError = 0 ;

				for(int i= 0 ; i < takeinList.get(index).getOrderItem().size(); i++) {
					
					if(takeinList.get(index).getOrderItem().get(i).getItemDetail().equals("")) {
						errMsg = errMsg+" 등록 되지 않은 상품명 /";
					};
					if(takeinList.get(index).getOrderItem().get(i).getItemCnt().equals("0")||takeinList.get(index).getOrderItem().get(i).getItemCnt().equals("")) {
						errMsg = errMsg+" 상품수량 누락 /";
					};
					
					if(Integer.parseInt(takeinList.get(index).getOrderItem().get(i).getStockCnt()) < 0) {
						takeinList.get(index).getOrderItem().get(i).setStockMsg("해당 재고 수량 부족");
						StockError++;
					};
					
					if(takeinList.get(index).getOrderItem().get(i).getUnitValue().equals("0.0")) {
						errMsg = errMsg+" 상품 단가 누락 /";
					}
					
					if (takeinList.get(index).getTransCode().equals("YT")) {
						System.out.println(takeinList.get(index).getNno());
						if(takeinList.get(index).getOrderItem().get(i).getHsCode().length() != 6) {
							errMsg = errMsg+" HSCODE 6자리 필수 /";
						}
					}
					
				}
				
				if(StockError > 0) {
					takeinList.get(index).setErrMsg("재고 수량 /");
				}
				
				if(errMsg.length() > 0) {
					takeinList.get(index).setErrYn("Y");
					 int errMsgLen = errMsg.length();
					 errMsg = errMsg.substring(0,errMsgLen-1);
				}

				 takeinList.get(index).setErrMsg(errMsg);
			}
 		}catch (Exception e) {
 			e.printStackTrace();
		}

		model.addAttribute("takeinList",takeinList);
		model.addAttribute("userTransList",userTransList);
		model.addAttribute("parameterInfo", parameterInfo);
		return "user/takein/takeinOrderListTmp";
	}

	@RequestMapping(value="/cstmr/takein/takeinTmpDel",method=RequestMethod.POST)
	public String takeinTmpDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String[] arrayParam = request.getParameterValues("nno[]");		
		for(int index = 0 ; index < arrayParam.length ; index++) {
			
			HashMap<String, String> deleteNno = new HashMap<String, String>();
			deleteNno.put("nno", arrayParam[index]);

			usrTakeinService.deleteTmptakeinUserList(deleteNno);

		}
		return "redirect:/cstmr/takein/preorder";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinDel",method=RequestMethod.POST)
	public String takeinDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String[] arrayParam = request.getParameterValues("nno[]");		
		for(int index = 0 ; index < arrayParam.length ; index++) {
			HashMap<String, String> deleteNno = new HashMap<String, String>();
			
			deleteNno.put("nno", arrayParam[index]);
			deleteNno.put("userId", (String)request.getSession().getAttribute("USER_ID"));
			deleteNno.put("userIp", request.getRemoteAddr());

			usrTakeinService.deleteTakeinUserList(deleteNno);
		}
		return "redirect:/cstmr/takein/takeinOrderList";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinDvlIn",method=RequestMethod.POST)
	public String takeinDvlIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		
		
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_ID").toString();
		//String blTypeOrg = request.getParameter("blType");
		String blType = request.getParameter("blType");
		String blTypeOrg = "TAKEIN";
		String[] arrayParam = request.getParameterValues("targets1");
		String[] arrayerrYn = request.getParameterValues("targets2");
		String transCode ="";
		boolean sendChk = false;
		

		
		
	
		ArrayList<String> arrayNno = new  ArrayList<String>();
		//transCode="ARA";
		for(int roop = 0; roop < arrayParam.length; roop++) {
			try {
				transCode = usrService.selectTransCodeFromNNO(arrayParam[roop]);
				

				
				if(!arrayerrYn[roop].equals("Y")) {
					//comnService.comnBlApply(arrayParam[roop], transCode, userId, userIp, blTypeOrg);
					comnService.comnBlApplyV2(arrayParam[roop], transCode, userId, userIp, blTypeOrg, "");
					if (transCode.equals("YSL")) {
						sendChk = true;
					}
				}
				/*
				if (transCode.equals("YSL")) {
					continue;
				} else {
					if(!arrayerrYn[roop].equals("Y")) {
						comnService.comnBlApply(arrayParam[roop], transCode, userId, userIp, blTypeOrg);
					}
				}
				*/
				/*
				if(!arrayerrYn[roop].equals("Y")) {
					comnService.comnBlApply(arrayParam[roop], transCode, userId, userIp, blTypeOrg);
				}
				*/
//					switch (transCode) {
//					case "ACI":
//						comnService.selectBlApply(arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.sagawaPdf(request, arrayParam[roop], transCode);
//						break;
//					case "GTS":
//						comnService.selectBlApply(arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.sagawaPdf(request, arrayParam[roop], transCode);
//						break;
//					case "ARA":
//						usrService.selectBlApplyARA(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						break;
//					case "YSL":
//						usrService.selectBlApplyYSL(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.yslPdf(request, arrayParam[roop], transCode);
//						//usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
//						break;
//					case "EFS":
//						usrService.selectBlApplyEFS(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.efsPdf(request, arrayParam[roop], transCode);
//						//usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
//						break;
//					case "FED":
//						usrService.selectBlApplyFED(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr(),"FES");
//						break;
//					case "FES":
//						usrService.selectBlApplyFED(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr(),"FES");
//						break;
//					case "FEG":
//						usrService.selectBlApplyFED(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr(),"FEG");
//						break;
//					case "OCS":
//						usrService.selectBlApplyOCS(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.ocsPdf(request, arrayParam[roop], transCode);
//						break;
//					case "EPT":
//						usrService.selectBlApplyOCS(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.krPostPdf(request, arrayParam[roop], transCode);
//						break;
//					case "CJ":
//						comnService.selectBlApply(arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						break;
//					case "ITC":
//						usrService.selectBlApplyITC(request, arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						break;
//					default:
//						comnService.selectBlApply(arrayParam[roop],(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
//						comnService.sagawaPdf(request, arrayParam[roop], transCode);
//						break;
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		if (sendChk) {
			smtpService.sendMail();
		}
		
		
		//return "redirect:/cstmr/takein/takeinOrderListTmp";
		return "redirect:/cstmr/takein/preorder";
	}

	@RequestMapping(value = "/cstmr/takein/takeinExcelUpload", method = RequestMethod.POST)
	@ResponseBody
	public String takeinExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi)
			throws Exception {
		String result = "F";
		HashMap<String, String> parameters = new HashMap<String, String>();

		try {
			result = usrTakeinService.insertTmpTakeinExcelUpload(multi, request,parameters);
			//result = usrTakeinService.insertTmpTakeinExcelUploadTest(multi, request, parameters);
			result = "S";
 		}catch (Exception e) {
 			result = "F";
		}
		
		return result;
	}
	
	
	@RequestMapping(value="/cstmr/takein/takeinOrderList",method=RequestMethod.GET)
	public String takeinOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orderNo",request.getParameter("orderNo"));
		parameterInfo.put("hawbNo",request.getParameter("hawbNo"));
		parameterInfo.put("cneeName",request.getParameter("cneeName"));
		parameterInfo.put("startDate", request.getParameter("fromDate"));
		parameterInfo.put("endDate", request.getParameter("toDate"));
		ArrayList<TmpTakeinOrderVO> takeinList = new ArrayList<TmpTakeinOrderVO>();
		
		int curPage = 1;
		
		int totalCount = usrTakeinService.selectTotalCountTakeinList(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		PagingVO paging = new PagingVO(curPage,totalCount,5,30);
		parameterInfo.put("paging", paging);

		try {

			takeinList = usrTakeinService.selectTakeinUserList(parameterInfo);

			for(int index = 0 ; index < takeinList.size(); index++) {
				takeinList.get(index).dncryptData();
		
				HashMap<String, String> itemParmeter= new HashMap<String, String>();
				itemParmeter.put("userId", (String)request.getSession().getAttribute("USER_ID"));
				itemParmeter.put("orgStation", takeinList.get(index).getOrgStation());
				itemParmeter.put("nno", takeinList.get(index).getNno());
				
				ArrayList<TmpTakeinOrderItemListVO> orderItem = new ArrayList<TmpTakeinOrderItemListVO>();
				orderItem = usrTakeinService.selectTakeinUserOrderItem(itemParmeter);
				takeinList.get(index).setOrderItem(orderItem);
			}

 		}catch (Exception e) {
		
		}

		model.addAttribute("takeinList",takeinList);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("paging", paging);
		return "user/takein/takeinOrderList";
	}


	@RequestMapping(value="/cstmr/takein/takeinDelOrderList",method=RequestMethod.GET)
	public String takeinDelOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orderNo",request.getParameter("orderNo"));
		parameterInfo.put("hawbNo",request.getParameter("hawbNo"));
		parameterInfo.put("cneeName",request.getParameter("cneeName"));
		ArrayList<TmpTakeinOrderVO> takeinList = new ArrayList<TmpTakeinOrderVO>();
		
		int curPage = 1;
		
		int totalCount = usrTakeinService.selectDelTotalCountTakeinList(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		PagingVO paging = new PagingVO(curPage,totalCount,5,30);
		parameterInfo.put("paging", paging);

		try {

			takeinList = usrTakeinService.selectDelTakeinUserList(parameterInfo);

			for(int index = 0 ; index < takeinList.size(); index++) {
				takeinList.get(index).dncryptData();
		
				HashMap<String, String> itemParmeter= new HashMap<String, String>();
				itemParmeter.put("userId", (String)request.getSession().getAttribute("USER_ID"));
				itemParmeter.put("orgStation", takeinList.get(index).getOrgStation());
				itemParmeter.put("nno", takeinList.get(index).getNno());
				
				ArrayList<TmpTakeinOrderItemListVO> orderItem = new ArrayList<TmpTakeinOrderItemListVO>();
				orderItem = usrTakeinService.selectDelTakeinUserOrderItem(itemParmeter);
				takeinList.get(index).setOrderItem(orderItem);
			}

 		}catch (Exception e) {
		
		}
		
		model.addAttribute("takeinList",takeinList); 
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("paging", paging);
		return "user/takein/takeinDelOrderList";
	}

	@RequestMapping(value="/cstmr/takein/takeinRec",method=RequestMethod.POST)
	public String takeinRec(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String[] arrayParam = request.getParameterValues("nno[]");		
		for(int index = 0 ; index < arrayParam.length ; index++) {
			HashMap<String, String> recoveryNno = new HashMap<String, String>();
			recoveryNno.put("nno", arrayParam[index]);
			recoveryNno.put("userId", (String)request.getSession().getAttribute("USER_ID"));
			recoveryNno.put("userIp", request.getRemoteAddr());
			usrTakeinService.recoveryTakeinUserList(recoveryNno);
		}
		return "redirect:/cstmr/takein/takeinDelOrderList";
	}
	
	@RequestMapping(value = "/cstmr/takein/takeinCodeList", method = RequestMethod.GET)
	public String takeinCodeListTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<HashMap<String, Object>> takeinCodeList = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getSession().getAttribute("USER_ID"));
		
		int curPage = 1;
		//int totalCnt = usrTakeinService.selectTakeinCodeListCnt(parameterInfo);
		
		
		return "user/takein/takeinCodeList";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinInfoList",method=RequestMethod.GET)
	public String takeinInfoList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("useYn",request.getParameter("useYn"));
		parameterInfo.put("appvYn",request.getParameter("appvYn"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));

		if (request.getParameter("useYn") == null || request.getParameter("useYn") == "") {			
			parameterInfo.put("useYn", "Y");
		}
		
		int curPage = 1;
		int totalCount = usrTakeinService.selectUserTakeinCountTakeinList(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		PagingVO paging = new PagingVO(curPage,totalCount,5,30);
		parameterInfo.put("paging", paging);
		
		
		ArrayList<OrgStationVO> orgStation = new ArrayList<OrgStationVO>();
 
		ArrayList<TakeinInfoVO> takeInInfo = new ArrayList<TakeinInfoVO>();
		try {
			takeInInfo = usrTakeinService.selectUserTakeInInfo(parameterInfo);
			orgStation = usrTakeinService.selectOrgStation(parameterInfo);
		}catch (Exception e) {
			
		}
	
		model.addAttribute("takeinList",takeInInfo); 
		model.addAttribute("orgStation",orgStation);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("paging", paging);
	
		return "user/takein/takeinInfoList";
	}
	
	@RequestMapping(value="/cstmr/takein/popupTakeininfo",method=RequestMethod.GET)
	public String popuptakeinInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		
		ArrayList<CurrencyVO> currList = new ArrayList<CurrencyVO>();
		currList = usrTakeinService.selectCurrencyList(parameterInfo);
		
		ArrayList<OrgStationVO> orgStation = new ArrayList<OrgStationVO>();
		orgStation = usrTakeinService.selectOrgStation(parameterInfo);

		TakeinInfoVO takeInInfo = new TakeinInfoVO();
		takeInInfo = usrTakeinService.selectTakeInCode(parameterInfo);
		model.addAttribute("currList", currList);
		model.addAttribute("takeInInfo",takeInInfo); 
		model.addAttribute("orgStation",orgStation);
		return "user/takein/popupTakeininfo";
	}
	
	@RequestMapping(value = "/cstmr/takein/TakeinIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userJsonTakeinIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		parameterInfo.put("hsCode", request.getParameter("hsCode"));
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
		
		Rst = usrTakeinService.insertUserTakeinInfo(parameterInfo);

		return Rst;
	}

	@RequestMapping(value = "/cstmr/takein/TakeinUp", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userJsonTakeinUp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		parameterInfo.put("takeInCode",request.getParameter("takeInCode"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		parameterInfo.put("hsCode", request.getParameter("hsCode"));
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
		parameterInfo.put("itemColor", request.getParameter("itemColor"));
		parameterInfo.put("itemSize", request.getParameter("itemSize"));
		parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("wUserIp", request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		
		Rst = usrTakeinService.updateUserTakeinInfo(parameterInfo);

		return Rst;
	}


	@RequestMapping(value="/takein/apply/excelFormDown",method=RequestMethod.GET)
	public String userExcelFormDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		// 파일 업로드된 경로
	    String root = request.getSession().getServletContext().getRealPath("/");
	    String savePath =root + "WEB-INF/jsp/user/takein/";

	    // 서버에 실제 저장된 파일명
	    String filename = "takein_template_v2.xlsx" ;
	    // 실제 내보낼 파일명
	    String orgfilename = "takein_template_v2.xlsx";

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
		return "user/takein/takeinOrderListTmp";
	}

	@RequestMapping(value="/takein/apply/takeinfoTmpexcelFormDown",method=RequestMethod.GET)
	public String takeinfoTmpexcelFormDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		// 파일 업로드된 경로
	    String root = request.getSession().getServletContext().getRealPath("/");
	    String savePath =root + "WEB-INF/jsp/user/takein/";

	    // 서버에 실제 저장된 파일명
	    String filename = "takeinfoSample.xlsx" ;
	    // 실제 내보낼 파일명
	    String orgfilename = "takeinfoSample.xlsx";

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
		return "/cstmr/takein/popupTmpTakeininfo";
	   // return "";
	}

	@RequestMapping(value = "/cstmr/takein/takeinInfoExcelUpload", method = RequestMethod.POST)
	public String takeinInfoExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi)
			throws Exception {
		String result = "F";
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", (String)request.getSession().getAttribute("USER_ID"));

		try {
			result = usrTakeinService.insertTmpTakeinExcelUpload(multi, request,parameters);
 		}catch (Exception e) {
 			result = "F";
		}
		
		//return "redirect:/cstmr/takein/takeinOrderListTmp";
		return "redirect:/cstmr/takein/preorder";
	}

	@RequestMapping(value="/cstmr/takein/popupTakeininfoExcelUpload",method=RequestMethod.GET)
	public String popupTakeininfoExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		
		ArrayList<OrgStationVO> orgStation = new ArrayList<OrgStationVO>();
		orgStation = usrTakeinService.selectOrgStation(parameterInfo);

		TakeinInfoVO takeInInfo = new TakeinInfoVO();
		takeInInfo = usrTakeinService.selectTakeInCode(parameterInfo);

		model.addAttribute("takeInInfo",takeInInfo); 
		model.addAttribute("orgStation",orgStation);
		return "user/takein/popupTakeininfo";
	}

	
	@RequestMapping(value="/cstmr/takein/popupTmpTakeininfo",method=RequestMethod.GET)
	public String popupTakeininfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
 
		ArrayList<TmpTakeinCodeVO> tmpTakeinCodeVO = new ArrayList<TmpTakeinCodeVO>();
		try {
			tmpTakeinCodeVO = usrTakeinService.selectUserTmpTakeInInfo(parameterInfo);
		}catch (Exception e) {
			
		}

		for(int index = 0 ; index < tmpTakeinCodeVO.size(); index++) {
			String errMsg = "";
			
			tmpTakeinCodeVO.get(index).setErrYn("N");
			
			if(tmpTakeinCodeVO.get(index).getAlreadyYn().equals("Y")) {
				tmpTakeinCodeVO.get(index).setErrYn("Y");
				errMsg = errMsg + " 이미 등록된 상품코드 입니다 /";
			}
			
			if(tmpTakeinCodeVO.get(index).getOverlapYn().equals("Y")) {
				tmpTakeinCodeVO.get(index).setErrYn("Y");
				errMsg = errMsg + " 중복된 상품코드 입니다 /";
			}
			
			if(tmpTakeinCodeVO.get(index).getOrgStationName().equals("")) {
				tmpTakeinCodeVO.get(index).setErrYn("Y");
				errMsg = errMsg + " Station 오류 /";
			}
			
			if(tmpTakeinCodeVO.get(index).getCusItemCode().equals("")) {
				tmpTakeinCodeVO.get(index).setErrYn("Y");
				errMsg = errMsg + " 상품코드 오류 /";
			}
			
			if(tmpTakeinCodeVO.get(index).getItemDetail().equals("")) {
				tmpTakeinCodeVO.get(index).setErrYn("Y");
				errMsg = errMsg + " 상품명 오류 /";
			}

			 if(Float.parseFloat(tmpTakeinCodeVO.get(index).getUnitValue()) <=0){
				 tmpTakeinCodeVO.get(index).setErrYn("Y");
				  errMsg = errMsg + " 단가 오류 /";
			 }
			
			 
			if(errMsg.length() > 0) {
				errMsg = errMsg.substring(0, errMsg.length() - 1);
			}

			tmpTakeinCodeVO.get(index).setErrMsg(errMsg);
		}
		model.addAttribute("takeinList",tmpTakeinCodeVO); 
		return "user/takein/popupTmpTakeininfo";
	}
	

	@RequestMapping(value = "/cstmr/takein/takeinInDel", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userJsonTakeinInDel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String[] arrayParam = request.getParameterValues("idx[]");	
		
		for(int index = 0 ; index < arrayParam.length ; index++) {		
			parameterInfo.put("idx", arrayParam[index]);
			usrTakeinService.deleteUserTakeinCode(parameterInfo);
		}
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstStatus", "SUCCESS");	
		Rst.put("rstCode", "S01");
		Rst.put("rstMsg", "정상처리되었습니다.");

		return Rst;
	}
	
	
	@RequestMapping(value = "/cstmr/takein/takeinInInsert", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userJsonTakeinInInsert(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
	
		String[] arrayParam = request.getParameterValues("idx[]");	
		String[] arrayErrYn = request.getParameterValues("errYn[]");

		
		for(int index = 0 ; index < arrayParam.length ; index++) {		
			if(arrayErrYn[index].equals("N")) {
				HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
				
				parameterInfo.put("idx", arrayParam[index]);
				parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
				
				TmpTakeinCodeVO tmpTakeinCodeVO = new TmpTakeinCodeVO();
				tmpTakeinCodeVO = usrTakeinService.selectTmpTakeinCode(parameterInfo);
	
				parameterInfo.put("orgStation",tmpTakeinCodeVO.getOrgStation());
				parameterInfo.put("cusItemCode",tmpTakeinCodeVO.getCusItemCode());
				parameterInfo.put("hsCode", tmpTakeinCodeVO.getHsCode());
				parameterInfo.put("brand", tmpTakeinCodeVO.getBrand());
				parameterInfo.put("itemDetail", tmpTakeinCodeVO.getItemDetail());
				parameterInfo.put("nativeItemDetail",tmpTakeinCodeVO.getNativeItemDetail());
				parameterInfo.put("itemOption",tmpTakeinCodeVO.getItemOption());
				parameterInfo.put("unitValue",tmpTakeinCodeVO.getUnitValue());
				parameterInfo.put("unitCurrency",tmpTakeinCodeVO.getUnitCurrency());
				parameterInfo.put("wta",tmpTakeinCodeVO.getWta()); 
				parameterInfo.put("wtc",tmpTakeinCodeVO.getWtc());
				parameterInfo.put("wtUnit",tmpTakeinCodeVO.getWtUnit());
				parameterInfo.put("qtyUnit",tmpTakeinCodeVO.getQtyUnit());
				parameterInfo.put("itemUrl",tmpTakeinCodeVO.getItemUrl());
				parameterInfo.put("itemImgUrl",tmpTakeinCodeVO.getItemImgUrl());
				parameterInfo.put("itemMeterial",tmpTakeinCodeVO.getItemMeterial());
				parameterInfo.put("itemDiv",tmpTakeinCodeVO.getItemDiv());
				parameterInfo.put("makeCntry",tmpTakeinCodeVO.getMakeCntry());
				parameterInfo.put("makeCom",tmpTakeinCodeVO.getMakeCom());
				parameterInfo.put("itemColor",tmpTakeinCodeVO.getItemColor());
				parameterInfo.put("itemSize",tmpTakeinCodeVO.getItemSize());
				parameterInfo.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
				parameterInfo.put("wUserIp", request.getRemoteAddr());
				
			
				HashMap<String, Object> Rst = new HashMap<String, Object>();
				Rst = usrTakeinService.insertUserTakeinInfo(parameterInfo);
				
				if(Rst.get("rstStatus").equals("SUCCESS")) {
					usrTakeinService.delteUserTmpTakeinCode(parameterInfo);
				}
			}
		}
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst.put("rstStatus", "SUCCESS");	
		Rst.put("rstCode", "S01");
		Rst.put("rstMsg", "정상처리되었습니다.");

		return Rst;
	}
	
	
	@RequestMapping(value = "/cstmr/takein/tmpTakeinInfoExcelUpload", method = RequestMethod.POST)
	public String tmpTakeinInfoExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi)
			throws Exception {
		String result = "F";
		HashMap<String, String> parameters = new HashMap<String, String>();

		try {
			result = usrTakeinService.insertTmpTakeinInfoExcelUpload(multi, request,parameters);
 		}catch (Exception e) {
 			result = "F";
		}
		return "redirect:/cstmr/takein/popupTmpTakeininfo";
	}

	@RequestMapping(value="/cstmr/takein/takeinUserStockList",method=RequestMethod.GET)
	public String takeinUserStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		
		ArrayList<OrgStationVO> orgStation = new ArrayList<OrgStationVO>();
		orgStation = usrTakeinService.selectOrgStation(parameterInfo);

		
		int curPage = 1;
		int totalCount = usrTakeinService.selectTakeinUserStockListCnt(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		PagingVO paging = new PagingVO(curPage,totalCount,5,30);
		parameterInfo.put("paging", paging);
	
		ArrayList<TakeinUserStockListVO> takeinUserStockList = new ArrayList<TakeinUserStockListVO>();
		takeinUserStockList = usrTakeinService.selectTakeinUserStockList(parameterInfo);

		model.addAttribute("takeinUserStockList",takeinUserStockList);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("orgStation",orgStation);
		model.addAttribute("paging", paging);

		return "user/takein/takeinUserStockList";
	}

	@RequestMapping(value="/cstmr/takein/popupTakeinStockList",method=RequestMethod.GET)
	public String popupTakeinStockList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("groupIdx",request.getParameter("groupIdx"));
		
		ArrayList<TakeInUserStockOutVO> takeInUserStockOutVO = new ArrayList<TakeInUserStockOutVO>();
		takeInUserStockOutVO = usrTakeinService.selectTakeInUserStockOutList(parameterInfo);
		
		model.addAttribute("takeInUserStockOutList",takeInUserStockOutVO);
		return  "user/takein/popupTakeinStockList";
	}
	
	@RequestMapping(value="/cstmr/takein/userTakeinStockInMonth",method=RequestMethod.GET)
	public String userTakeinStockInMonth(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
			
			HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
			parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("orgStation",request.getParameter("orgStation"));
			parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
			parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
			
			if (request.getParameter("useYn") == null || request.getParameter("useYn") == "") {			
				parameterInfo.put("useYn", "Y");
			}
			
			int curPage = 1;
			int totalCount = usrTakeinService.selectUserTakeinStockInMonthCnt(parameterInfo);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}		
			
			PagingVO paging = new PagingVO(curPage,totalCount,5,30);
			parameterInfo.put("paging", paging);
			
			
			ArrayList<HashMap<String, Object>> stockStationList = new ArrayList<HashMap<String, Object>>();
			stockStationList = usrTakeinService.selectStockStationList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = usrTakeinService.selectUserTakeinStockInMonth(parameterInfo);
			
			model.addAttribute("stockStationList",stockStationList);
			model.addAttribute("listRst",listRst);
			model.addAttribute("parameterInfo",parameterInfo);
			model.addAttribute("paging",paging);

			return "user/takein/takeinStockInMonth";
	}
	
	@RequestMapping(value="/cstmr/takein/userTakeinStockInMonthExcelDown",method=RequestMethod.GET)
	public void userTakeinStockInMonthExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		
		usrTakeinService.selectUserTakeinStockInMonthExcel(request,response,parameterInfo);
		
	}
	
	
	@RequestMapping(value="/cstmr/takein/takeinStockOut",method=RequestMethod.GET)
	public String userTakeinStockOutDay(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
			parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("orgStation",request.getParameter("orgStation"));
			parameterInfo.put("fromDate",request.getParameter("fromDate"));
			parameterInfo.put("toDate",request.getParameter("toDate"));
			parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
			parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyyMMdd");
			Date date = new Date(); 
			
			String fromDate = dateFormat.format(date);
			String toDate = dateFormat.format(date);
			
			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {			
				parameterInfo.put("fromDate",fromDate);
			}
			
			if (request.getParameter("toDate") == null || request.getParameter("toDate") == "") {			
				parameterInfo.put("toDate",toDate);
			}
			
			int curPage = 1;
			int totalCount = usrTakeinService.selectUserTakeinStockOutDayCnt(parameterInfo);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}		
			
			PagingVO paging = new PagingVO(curPage,totalCount,5,30);
			parameterInfo.put("paging", paging);
			
			
			ArrayList<HashMap<String, Object>> stockStationList = new ArrayList<HashMap<String, Object>>();
			stockStationList = usrTakeinService.selectStockStationList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = usrTakeinService.selectUserTakeinStockOutDay(parameterInfo);
			
			model.addAttribute("stockStationList",stockStationList);
			model.addAttribute("listRst",listRst);
			model.addAttribute("parameterInfo",parameterInfo);
			model.addAttribute("paging",paging);
			return "user/takein/takeinStockOut";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinStockOutExcelDown",method=RequestMethod.GET)
	public void takeinStockOutExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("fromDate",request.getParameter("fromDate"));
		parameterInfo.put("toDate",request.getParameter("toDate"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		
		usrTakeinService.takeinStockOutExcelDown(request,response,parameterInfo);
	}


	
	@RequestMapping(value="/cstmr/takein/takeinStockInOut",method=RequestMethod.GET)
	public String userTakeinStockInOutDay(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
			parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("orgStation",request.getParameter("orgStation"));
			parameterInfo.put("fromDate",request.getParameter("fromDate"));
			parameterInfo.put("toDate",request.getParameter("toDate"));
			parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
			parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyyMMdd");
			Date date = new Date(); 
			
			String fromDate = dateFormat.format(date);
			String toDate = dateFormat.format(date);
			
			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {			
				parameterInfo.put("fromDate",fromDate);
			}
			if (request.getParameter("toDate") == null || request.getParameter("toDate") == "") {			
				parameterInfo.put("toDate",toDate);
			}
			if (request.getParameter("useYn") == null || request.getParameter("useYn") == "") {			
				parameterInfo.put("useYn", "Y");
			}			
			int curPage = 1;
			int totalCount = usrTakeinService.selectInOutStockListCnt(parameterInfo);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}		
			
			PagingVO paging = new PagingVO(curPage,totalCount,5,30);
			parameterInfo.put("paging", paging);
			
			
			ArrayList<HashMap<String, Object>> stockStationList = new ArrayList<HashMap<String, Object>>();
			stockStationList = usrTakeinService.selectStockStationList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = usrTakeinService.selectInOutStockList(parameterInfo);
			
			model.addAttribute("stockStationList",stockStationList);
			model.addAttribute("listRst",listRst);
			model.addAttribute("parameterInfo",parameterInfo);
			model.addAttribute("paging",paging);
				
			return "user/takein/takeinStockInOut";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinOrderStockOut",method=RequestMethod.GET)
	public String userTakeinOrderStockInOut(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
			parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("orgStation",request.getParameter("orgStation"));
			parameterInfo.put("fromDate",request.getParameter("fromDate"));
			parameterInfo.put("toDate",request.getParameter("toDate"));
			parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
			parameterInfo.put("orderNo",request.getParameter("orderNo"));
			parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyyMMdd");
			Date date = new Date(); 
			
			String fromDate = dateFormat.format(date);
			String toDate = dateFormat.format(date);
			
			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {			
				parameterInfo.put("fromDate",fromDate);
			}
			if (request.getParameter("toDate") == null || request.getParameter("toDate") == "") {			
				parameterInfo.put("toDate",toDate);
			}

			int curPage = 1;
			int totalCount = usrTakeinService.selectUserTakeinOrderStockOutCnt(parameterInfo);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}		
			
			PagingVO paging = new PagingVO(curPage,totalCount,5,100);
			parameterInfo.put("paging", paging);
			
			
			ArrayList<HashMap<String, Object>> stockStationList = new ArrayList<HashMap<String, Object>>();
			stockStationList = usrTakeinService.selectStockStationList(parameterInfo);
			
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = usrTakeinService.selectUserTakeinOrderStockOut(parameterInfo);
			
			model.addAttribute("stockStationList",stockStationList);
			model.addAttribute("listRst",listRst);
			model.addAttribute("parameterInfo",parameterInfo);
			model.addAttribute("paging",paging);
				
			return "user/takein/takeinOrderStockOut";
	}
	
	@RequestMapping(value="/cstmr/takein/takeinOrderStockOutExcelDown",method=RequestMethod.GET)
	public void takeinOrderStockOutExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("fromDate",request.getParameter("fromDate"));
		parameterInfo.put("toDate",request.getParameter("toDate"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("orderNo",request.getParameter("orderNo"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		usrTakeinService.takeinOrderStockOutExcelDownDef(request,response,parameterInfo);
	}
	
	@RequestMapping(value="/cstmr/takein/takeinStockInOutExcelDown",method=RequestMethod.GET)
	public void takeinStockInOutExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		HashMap<String, Object> parameterInfo= new HashMap<String, Object>();
		parameterInfo.put("userId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("orgStation",request.getParameter("orgStation"));
		parameterInfo.put("fromDate",request.getParameter("fromDate"));
		parameterInfo.put("toDate",request.getParameter("toDate"));
		parameterInfo.put("cusItemCode",request.getParameter("cusItemCode"));
		parameterInfo.put("orderNo",request.getParameter("orderNo"));
		parameterInfo.put("itemDetail",request.getParameter("itemDetail"));
		
		usrTakeinService.takeinStockInOutExcelDown(request,response,parameterInfo);
	}
	
	@RequestMapping(value = "/cstmr/takein/registTakeinOrder", method = RequestMethod.GET)
	public String memberTakeinRegistOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String transCom = new String();
		if (request.getParameter("transCode") != null) {
			transCom = request.getParameter("transCode");
		}
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<String> dstnList = new ArrayList<String>();
		for (int i = 0; i < userDstnNation.size(); i++) {
			dstnList.add(userDstnNation.get(i).getDstnNation());
		}
		parameterInfo.put("dstns", dstnList);
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = comnService.selectCurrencyListByDstnNation(parameterInfo);
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
		UserVO userInfo = usrService.selectUserInfo(member.getUsername());
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectUserNationCode(member.getUsername());
		ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
		transComList = usrService.selectTrkComList((String) request.getSession().getAttribute("USER_ID"));
		model.addAttribute("transComList", transComList);
		model.addAttribute("nationList", nationList);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("userOrgNation", userOrgNation);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("userId", member.getUsername());
		if(!transCom.equals("")) {
			OrderListOptionVO optionOrderVO = new OrderListOptionVO();
			OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
			OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
			OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
			String dstnNation = request.getParameter("dstnNation");
			optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
			optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
			expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
			expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
			model.addAttribute("optionOrderVO", optionOrderVO);
			model.addAttribute("optionItemVO", optionItemVO);
			model.addAttribute("expressOrderVO", expressOrderVO);
			model.addAttribute("expressItemVO", expressItemVO);
			model.addAttribute("targetCode", transCom);
			model.addAttribute("currencyList", currencyList);
			model.addAttribute("params", parameterInfo);
			//return "user/takein/registOneAll";
		} else {
			//return "user/takein/registOne";
		}
		return "user/takein/takeinOrderRegist";
	}
	
	@RequestMapping(value = "/cstmr/takein/popupTakeinUserItems", method = RequestMethod.GET)
	public String getMemberTakeinItems(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId", request.getParameter("userId"));
		
		ArrayList<HashMap<String, Object>> cusItemList = new ArrayList<HashMap<String, Object>> ();
		cusItemList = usrTakeinService.selectUserCusItemList(parameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("cusItemList", cusItemList);
		
		return "user/takein/popupTakeinUserItems";	
		
	}
	
	@RequestMapping(value = "/cstmr/takein/takeinCusItemInJson", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userTakeinCusItemInJson(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("userId", request.getParameter("userId"));
		parameterInfo.put("cusItemCode", request.getParameter("cusItemCode"));
		 	
		HashMap<String, Object> rst = new HashMap<String, Object>();
		try {
			rst = usrTakeinService.selectTakeinCusItemInJson(parameterInfo);
			rst.put("rstStatus", "SUCCESS");
			rst.put("cnt", request.getParameter("cnt"));
			ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
			currencyList = comnService.selectCurrencyListUse();
			rst.put("currencyList", currencyList);
		} catch (Exception e) {
			e.printStackTrace();
			rst.put("rstStatus", "FAIL");
		
		}
		
		
		return rst;
	}
	
	@RequestMapping(value = "/cstmr/takein/registTakeinOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public String userTakeinOrderInfoReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result = "F";


		System.out.println("******************************");
		System.out.println(request.getParameter("dstnNation"));
		System.out.println(request.getParameter("orgStation"));

		HashMap<String, String> parameters = new HashMap<String, String>();
		
		try {
			mgrTakeinService.insertUserTakeinOrderList(request, parameters);
			result = "S";
		} catch (Exception e) {
			e.printStackTrace();
			result = "F";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/cstmr/takein/modifyTakeinOrderInfo", method = RequestMethod.POST)
	public String userModifyTakeinOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = usrService.selectUserOrderOne(request.getParameter("nno"));
		
		UserVO userInfo =  usrService.selectUserInfo(member.getUsername());
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new  ArrayList<UserOrderItemVO>();
		userOrderItem = usrService.selectUserOrderItemOne(userOrder);
		
		model.addAttribute("urlType",request.getParameter("urlType"));
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		
		currencyList = comnService.selectCurrencyListUse();
		taxTypeList = mgrService.selectTaxTypeList();
		nationList = comnService.selectNationCode();
		String transCom = userOrder.getTransCode();
		model.addAttribute("nationList", nationList);
		model.addAttribute("userOrder",userOrder);
		model.addAttribute("userOrderItem",userOrderItem);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("transCode",transCom);
		
		String dstnNation = userOrder.getDstnNation();
		model.addAttribute("taxTypeList", taxTypeList);
		model.addAttribute("currencyList", currencyList);
		
		return "user/takein/takeinOrderModfiy";
	}
	
	@RequestMapping(value = "/cstmr/takein/modifyTakeinOrderInfoUp", method = RequestMethod.POST)
	@ResponseBody
	public String userModifyTakeinOrderInfoUp(HttpServletRequest request, HttpServletResponse response, Model model,  UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		String result = "F";
		
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		Date date = new Date();
		String time = format.format(date);
		
		userOrderList.setUserId(member.getUsername());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time);
		
		try {
			//usrService.updateUserOrderListARA(userOrderList, userOrderItemList);
			usrTakeinService.updateUserTakeinOrderList(userOrderList, userOrderItemList);
			result = "S";
		} catch (Exception e) {
			e.printStackTrace();
			result = "F";
		}
		
		return result;
	}
	



	@RequestMapping(value="/cstmr/takein/preorder",method=RequestMethod.GET)
	public String takeinPreOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String viewPath = "v1/user/takeinPreOrderList";
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		HashMap<String, String> sqlParams = new HashMap<String, String>();
		sqlParams.put("userId", userId);
		String root = request.getSession().getServletContext().getRealPath("/");
		String filePath = root + "WEB-INF/jsp/user/takein/userUploadFormTakein.xlsx";
		
		sqlParams.put("sampleExcel", filePath);

		ArrayList<TmpTakeinOrderVO> takeinList = new ArrayList<TmpTakeinOrderVO>();
		ArrayList<UserTransVO> userTransList =  new ArrayList<UserTransVO>();

		try {
			userTransList = usrTakeinService.selectUserTransCom(sqlParams);
			takeinList = usrTakeinService.selectTmpTakeinUserList(sqlParams);
			ArrayList<TmpTakeinOrderItemListVO> orderItem2 = new ArrayList<TmpTakeinOrderItemListVO>();
			HashMap<String, Object> itemParmeter2= new HashMap<String, Object>();
			itemParmeter2.put("userId", (String)request.getSession().getAttribute("USER_ID"));
			
			if (takeinList.size() > 0) {
				itemParmeter2.put("orgStation", takeinList.get(0).getOrgStation());
				itemParmeter2.put("takeinList", takeinList);
				orderItem2 = usrTakeinService.selectTmpTakeinUserOrderItem2(itemParmeter2);	
				
				for(int index = 0 ; index < takeinList.size(); index++) {
					
					String errMsg ="";
					String cneeTel="";

					takeinList.get(index).dncryptData();
					
					HashMap<String, String> itemParmeter= new HashMap<String, String>();
					
					for(int j = 0; j < orderItem2.size();j++) {
						if(takeinList.get(index).getNno().equals(orderItem2.get(j).getNno())) {
							TmpTakeinOrderItemListVO orderItem = new TmpTakeinOrderItemListVO ();
							orderItem = orderItem2.get(j);
							takeinList.get(index).getOrderItem().add(orderItem);
							orderItem2.remove(j);
							j=-1;
						}
					}
					

					if(takeinList.get(index).getCneeHp().equals("")) {
						cneeTel = takeinList.get(index).getCneeTel();
					}else {
						cneeTel = takeinList.get(index).getCneeHp();
					}
					
					if( Integer.parseInt(takeinList.get(index).getAlready()) != 0){
						errMsg = errMsg+" 이미 등록된 주문번호 /";
					}
					
					if( Integer.parseInt(takeinList.get(index).getDupCnt()) != 1){
						errMsg = errMsg+" 중복된 주문번호 /";
					}
					
					if(takeinList.get(index).getOrgStationName().equals("") ){
						errMsg = errMsg+" 출발도시코드 오류 /";
					}
					
					if(takeinList.get(index).getDstnNationName().equals("")){
						errMsg = errMsg+" 도착국가 오류 /";
					}

					if(cneeTel.equals("")) {
						errMsg = errMsg+" 수취인명 연락처  누락 /";
					}
					

					if(takeinList.get(index).getCneeName().equals("")) {
						errMsg = errMsg+" 수취인명 누락 /";
					}
					
					
					if(takeinList.get(index).getDstnNationName().equals("")) {
						errMsg = errMsg+" 도착 국가 누락 /";
					}
					
					
					if(takeinList.get(index).getTransCode().equals("")) {
						errMsg = errMsg+" 배송사 누락 /";
					}
					
					
					if(!takeinList.get(index).getTransCode().equals("CJ")&&
							!takeinList.get(index).getTransCode().equals("YSL")&&
							!takeinList.get(index).getTransCode().equals("ITC")&&
							!takeinList.get(index).getTransCode().equals("HJ")&&
							!takeinList.get(index).getTransCode().equals("EPT")) {
						if(takeinList.get(index).getCneeCity().equals("")) {
							errMsg = errMsg+" 수취인 도시 누락 /";
						}
						
						if (!takeinList.get(index).getTransCode().equals("VNP")) {
							if(takeinList.get(index).getCneeState().equals("")) {
								errMsg = errMsg+" 수취인 주 누락 /";
							}
						}
					}
					
					if(takeinList.get(index).getTransCode().equals("VNP")) {
						if(takeinList.get(index).getCneeDistrict().equals("")) {
							errMsg = errMsg+" 수취인 구역 누락 /";
						}
					}
					
					if(takeinList.get(index).getCneeZip().equals("")) {
						errMsg = errMsg+" 수취인 우편번호 누락 /";
					} else {
						if(takeinList.get(index).getTransCode().equals("CJ") || takeinList.get(index).getTransCode().equals("HJ") 
								|| takeinList.get(index).getTransCode().equals("EPT")) {
							if(takeinList.get(index).getCneeZip().length() < 5) {
								errMsg = errMsg+" 우편번호 오류 /";
							}
						}	
					}
					
					if(takeinList.get(index).getCneeAddr().equals("")) {
						errMsg = errMsg+" 수취인 주소누락 /";
					}
					
					if(takeinList.get(index).getTransCode().equals("EPT")) {
						if (takeinList.get(index).getCneeAddrDetail().equals("")) {
							errMsg = errMsg+" 수취인 상세주소 누락 /";
						}
					}
					
					if( takeinList.get(index).getOrderItem().size()==0) {
						errMsg = errMsg+" 등록 되지 않은 상품코드 /";
					}
					
					if(!takeinList.get(index).getStatus().equals("")) {
						ArrayList<String> errString = usrTakeinService.selectErrorMsg(takeinList.get(index).getNno());
						for(int roop = 0; roop < errString.size(); roop++) {
							errMsg = errMsg+" "+errString.get(roop)+" /";
						}
						
					}
					
					
					ExportDeclare expInfo = new ExportDeclare();
					expInfo.setNno(takeinList.get(index).getNno());
					expInfo.setUserId(takeinList.get(index).getUserId());
					expInfo.setExpType(takeinList.get(index).getExpType());
					expInfo.setExpCor(takeinList.get(index).getExpCor());
					expInfo.setExpRprsn(takeinList.get(index).getExpRprsn());
					expInfo.setExpAddr(takeinList.get(index).getExpAddr());
					expInfo.setExpZip(takeinList.get(index).getExpZip());
					expInfo.setExpRgstrNo(takeinList.get(index).getExpRgstrNo());
					expInfo.setExpCstCd(takeinList.get(index).getExpCstCd());
					expInfo.setAgtCor(takeinList.get(index).getAgtCor());
					expInfo.setAgtCstCd(takeinList.get(index).getAgtCstCd());
					expInfo.setAgtBizNo(takeinList.get(index).getAgtBizNo());
					expInfo.setExpNo(takeinList.get(index).getExpNo());
					
					expInfo.dncryptData();
					
					if (!expInfo.getExpType().equals("N")) {
						if (expInfo.getExpCor().equals("")) {
							errMsg = errMsg+ " 수출화주사업자상호명 누락 /";
						}
						
						if (expInfo.getExpType().equals("E")) {
							if (expInfo.getExpRprsn().equals("")) {
								errMsg = errMsg+ " 수출화주사업자대표명 누락 /";
							}
							
							if (expInfo.getExpRgstrNo().equals("")) {
								errMsg = errMsg + " 수출화주사업자번호 누락 /";
							}
							
							if (expInfo.getExpAddr().equals("")) {
								errMsg = errMsg + " 수출화주사업자주소 누락 /";
							}
							
							if (expInfo.getExpZip().equals("")) {
								errMsg = errMsg + " 수출화주사업우편번호 누락 /";
							}
							
						}
						
					}

					int StockError = 0 ;

					for(int i= 0 ; i < takeinList.get(index).getOrderItem().size(); i++) {
						
						if(takeinList.get(index).getOrderItem().get(i).getItemDetail().equals("")) {
							errMsg = errMsg+" 등록 되지 않은 상품명 /";
						};
						if(takeinList.get(index).getOrderItem().get(i).getItemCnt().equals("0")||takeinList.get(index).getOrderItem().get(i).getItemCnt().equals("")) {
							errMsg = errMsg+" 상품수량 누락 /";
						};
						
						if(Integer.parseInt(takeinList.get(index).getOrderItem().get(i).getStockCnt()) < 0) {
							takeinList.get(index).getOrderItem().get(i).setStockMsg("해당 재고 수량 부족");
							StockError++;
						};
						
						if(takeinList.get(index).getOrderItem().get(i).getUnitValue().equals("0.0")) {
							errMsg = errMsg+" 상품 단가 누락 /";
						}
						
						if (takeinList.get(index).getTransCode().equals("YT")) {
							if(takeinList.get(index).getOrderItem().get(i).getHsCode().length() < 6) {
								errMsg = errMsg+" HSCODE 6자리 필수 /";
							}
						}
						
						if (takeinList.get(index).getTransCode().equals("VNP")) {
							if (!takeinList.get(index).getOrderItem().get(i).getChgCurrency().equals("VND")) {
								errMsg = errMsg+" VND 통화 외 입력 불가 /";
							}
						}
						
					}
					
					if(StockError > 0) {
						takeinList.get(index).setErrMsg("재고 수량 /");
					}
					
					if(errMsg.length() > 0) {
						takeinList.get(index).setErrYn("Y");
						 int errMsgLen = errMsg.length();
						 errMsg = errMsg.substring(0,errMsgLen-1);
					}

					 takeinList.get(index).setErrMsg(errMsg);
				}
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		model.addAttribute("takeinList",takeinList);
		model.addAttribute("userTransList",userTransList);
		model.addAttribute("parameterInfo", sqlParams);
		
		return viewPath;
	}
	
	
}


