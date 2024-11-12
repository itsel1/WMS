package com.example.temp.member.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.cafe24.service.Cafe24Service;
import com.example.temp.api.cafe24.vo.Cafe24UserTokenInfo;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.AllowIpVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.smtp.SmtpService;
import com.itextpdf.text.log.SysoCounter;

@Controller
public class MemberController {
	
	@Autowired
	MemberService usrService;
	
	@Autowired
	ManagerService mgrService;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	Cafe24Service cafe24Service;
	
	@Autowired
	private SmtpService smtpService;

	@Value("${filePath}")
    String realFilePath;

	@RequestMapping(value = "/cstmr/dashboard", method = RequestMethod.GET)
	public String cstmrMainPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		return "importFile/user/dashboard";
	}
	
	@RequestMapping(value = "/cstmr/main", method = RequestMethod.GET)
	public String cstmrMainHome(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		return "importFile/user/dashboard";
	}
	
	
	@RequestMapping(value="/cstmr/myPage/userDeposit", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> memberHeaderDeposit(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String userId = request.getParameter("userId");
		HashMap<String, Object> deposit = new HashMap<String, Object>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {
			deposit = usrService.selectUserDeposit(request, userId);
			result.put("deposit", deposit);
			result.put("result", "S");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "F");
		}
		
		
		return result;
	}
	
	/**
	 * Page : USER 배송대행 안내 페이지
	 * URI : /cstmr/shpngAgncy/Info
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shpngAgncy/Info",method=RequestMethod.GET)
	public String userShpngAgncyInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 해외 주소지 페이지
	 * URI : /cstmr/shpngAgncy/ovrssAdres
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shpngAgncy/ovrssAdres",method=RequestMethod.GET)
	public String userOvrssAdresInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 신청 방법 페이지
	 * URI : /cstmr/shpngAgncy/applyInfo
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shpngAgncy/applyInfo",method=RequestMethod.GET)
	public String userApplyInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 회원등급 안내
	 * URI : /cstmr/shpngAgncy/member
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shpngAgncy/member",method=RequestMethod.GET)
	public String userMember(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 배송대행 해외 지역별 요금
	 * URI : /cstmr/rates/ovrssChrgs
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/rates/ovrssChrgs",method=RequestMethod.GET)
	public String userOvrssChrgs(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 배송대행 해외 지역별 요금
	 * URI : /cstmr/rates/rtrnChrgs
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/rates/rtrnChrgs",method=RequestMethod.GET)
	public String userRtrnChrgs(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 부가서비스 요금
	 * URI : /cstmr/rates/extraSrvc
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/rates/extraSrvc",method=RequestMethod.GET)
	public String userExtraSrvc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 관 부과세 안내
	 * URI : /cstmr/csdcsClrnc/gdnc
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/csdcsClrnc/gdnc",method=RequestMethod.GET)
	public String userGdnc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 목록통관 및 일반통관
	 * URI : /cstmr/csdcsClrnc/lstgnClrnc
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/csdcsClrnc/lstgnClrnc",method=RequestMethod.GET)
	public String userLstgnClrnc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 수입 수출 금지품목
	 * URI : /cstmr/csdcsClrnc/itmimExprt
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/csdcsClrnc/itmimExprt",method=RequestMethod.GET)
	public String userItmimExprt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 관세청 고시 환율
	 * URI : /cstmr/csdcsClrnc/exchn
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/csdcsClrnc/exchn",method=RequestMethod.GET)
	public String userExchn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}

	/**
	 * Page : USER 해외 쇼핑몰
	 * URI : /cstmr/shop/ovrss
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shop/ovrss",method=RequestMethod.GET)
	public String userOvrssShop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 국내 쇼핑몰
	 * URI : /cstmr/shop/dmstc
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/shop/dmstc",method=RequestMethod.GET)
	public String userDmstcShop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/shpngAgncy
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngAgncy",method=RequestMethod.GET)
	public String userShpngAgncyApply(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(userId);

		if (userDstnNation.get(0).getOrgName().equals("대한민국")) {
			model.addAttribute("station", "082");
		} else {
			model.addAttribute("station", "");
			
		}
		model.addAttribute("userDstnNation",userDstnNation);
		String transCode = request.getParameter("transCode");
		model.addAttribute("userId", userId);
		model.addAttribute("transCode",transCode);
		String rtnUrl = "user/apply/shpng/list/shpngAgncy";
		return rtnUrl;
	}

	@RequestMapping(value = "/cstmr/noticePopup", method = RequestMethod.GET)
	public String userNoticePopup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "user/noticePopup";
	}
	
	@RequestMapping(value="/cstmr/apply/registOrderListAll",method=RequestMethod.POST)
	@ResponseBody
	public String userRegistOrderListAll(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String userId = request.getSession().getAttribute("USER_ID").toString();
		//String userId = "chariscell";
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		ArrayList<ShpngListVO> shpngList = new ArrayList<ShpngListVO> (); 
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		String transCode = request.getParameter("searchTransCode");
		parameter.put("userId", userId);
		parameter.put("orderType", "NOMAL");
		parameter.put("transCode", transCode);
		String txtType ="";
		String searchTxt ="";
		if(request.getParameter("txtType") != null) {
			txtType = request.getParameter("txtType");
			searchTxt = request.getParameter("searchTxt");
		}
		parameter.put("txtType", txtType);
		if(txtType.equals("0")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail",searchTxt);
		}else if(txtType.equals("1")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("2")) {
			parameter.put("orderNo","");
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("3")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail","");
		}else if(txtType.equals("4")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail",searchTxt);
		}else {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}
		shpngList = usrService.selectShpngListAll(parameter);
		
		String result= "F";
		boolean sendChk = false;
			if(shpngList.size() == 0) {
				return "N";
			}
			
			BlApplyVO tmp = new BlApplyVO(); 
			/*
			for (int roop = 0; roop < shpngList.size(); roop++) {
				if (shpngList.get(roop).getTransCode().equals("YSL")) {
					result = "Y";
				} else {
					comnService.comnBlApply(shpngList.get(roop).getNno(),shpngList.get(roop).getTransCode(),userId,userIp, request.getParameter("blType"));
				}
			}
			*/
			int totalChk = shpngList.size();
			int succChk = 0;
			for(int roop = 0; roop< shpngList.size(); roop++) {
				//comnService.comnBlApply(shpngList.get(roop).getNno(),shpngList.get(roop).getTransCode(),userId,userIp, request.getParameter("blType"));
				result = comnService.comnBlApplyV2(shpngList.get(roop).getNno(),shpngList.get(roop).getTransCode(),userId,userIp, request.getParameter("blType"), shpngList.get(roop).getUploadType());
				
				if (result.equals("S")) {
					succChk++;
				}
				
				if(shpngList.get(roop).getTransCode().equals("YSL")) {
					sendChk = true;
				}
			}
			
			if(sendChk) {
				smtpService.sendMail();
			}
			result="S";
			
			result = "배송 신청 처리 되었습니다.\n\n[전체] : " + totalChk + "\n[성공] : " + succChk;
		
		return result;
	}
	
	
	@RequestMapping(value="/cstmr/apply/shpngAgncyTable",method=RequestMethod.POST)
	public String userShpngAgncyTable(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> (); 
		int curPage = 1;
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		String searchIdx = request.getParameter("searchIdx");
		String transCode = request.getParameter("searchIdx");
		HashMap<String, Object> idxInfo = new HashMap<String,Object>();
		if(!searchIdx.equals("All")) {
			idxInfo = usrService.selectUserTransInfo(searchIdx);
			parameter.put("transCode", idxInfo.get("transCode").toString());
		}
		
		parameter.put("userId", member.getUsername());
		//parameter.put("userId", "trexi1");
		parameter.put("orderType", "NOMAL");
		String txtType ="";
		String searchTxt ="";
		if(request.getParameter("txtType") != null) {
			txtType = request.getParameter("txtType");
			searchTxt = request.getParameter("searchTxt");
		}
		parameter.put("txtType", txtType);
		if(txtType.equals("0")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail",searchTxt);
		}else if(txtType.equals("1")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("2")) {
			parameter.put("orderNo","");
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("3")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail","");
		}else if(txtType.equals("4")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail",searchTxt);
		}else {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}
		
		//String[] types = targetTypes.split("_");
		String rtnUrl = "user/apply/shpng/list/shpngAgncyAll";
		
		int totalCount = usrService.selectShpngListCount(parameter);
		if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,10,50);
		
		parameter.put("paging", paging);
		model.addAttribute("paging", paging);
		shpngList = usrService.selectShpngList(parameter);
		model.addAttribute("shpngList", shpngList);
		model.addAttribute("userId", (String) request.getSession().getAttribute("USER_ID"));
		return rtnUrl;
	}
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/excelFormDow
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/excelFormDown",method=RequestMethod.GET)
	@ResponseBody
	public String userExcelFormDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
	    usrService.createExcelFile(request, response);
	    // 서버에 실제 저장된 파일명
		return "";
	}
	
	@RequestMapping(value = "/cstmr/apply/excelFormDownForVision", method = RequestMethod.GET)
	@ResponseBody
	public void userExcelFormDownForVision(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/cstmr/apply/kkwFormDown", method = RequestMethod.GET)
	@ResponseBody
	public void kukkiwonFormDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		try {
			String filePath = realFilePath + "excel/downloadData";
			String fileName = "kukkiwon_order_form.xlsx";
			
			File file = new File(filePath, fileName);
			int fileSize = (int) file.length();
			if (fileSize > 0) {
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileName, "UTF-8");
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", encodedFilename);
				response.setContentLengthLong(fileSize);
				
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				
				in = new BufferedInputStream(new FileInputStream(file));
				
				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
					int bytesRead = 0;
					
					while ((bytesRead = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
					
					out.flush();
				} finally {
					in.close();
					out.close();
				}
			} else {
				throw new FileNotFoundException("파일이 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cstmr/apply/kkwExcelUp", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> kukkiwonExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();

		rst = usrService.insertKukkiwonOrder(request, multi);

		return rst;
	}
	
	/**
	 * Page : USER 일반배송 수정
	 * URI : /cstmr/apply/modifyShpng
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/modifyShpng",method=RequestMethod.POST)
	public String userModifyShpng(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
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
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
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
		model.addAttribute("userId", member.getUsername());
		model.addAttribute("taxTypeList", taxTypeList);
		
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
		return "user/apply/shpng/modify/userShpngModify";
	}
	
	
	
	/**
	 * Page : USER 배송대행 신청 엑셀업로드
	 * URI : /cstmr/apply/shpngAgncyExcelUpload
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/shpngAgncyExcelUpload",method=RequestMethod.POST)
	@ResponseBody
	public String userShpngAgncyExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = "F";
		String types = "";
		HashMap<String, Object> userTransInfo = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
//		userTransInfo = usrService.selectUserTransInfo(request.getParameter("formSelect"));
//		types = userTransInfo.get("transCode").toString();
		//String[] types = request.getParameter("formSelect").split("_");
		try {
			switch(types) {
				case "SAGAWA":
					result = usrService.insertExcelDataForSagawa(multi, request,member.getUsername(), "NOMAL", types);
					break;
				case "TEST111":
					result = usrService.insertExcelDataNew(multi, request,member.getUsername(), "NOMAL");
					break;
				default:
					result = usrService.insertExcelData(multi, request,member.getUsername(), "NOMAL");	
					break;
			}
 		}catch (Exception e) {
			// TODO: handle exception
 			result = "F";
		}
		return result;
	}
	
	@RequestMapping(value = "/cstmr/apply/shpngAgncyExcelUploadBatch", method = RequestMethod.POST)
	@ResponseBody
	public String userShpngAgncyExcelUploadBatch(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = "F";
		String types = "";
		HashMap<String, Object> userTransInfo = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("USER_ID");

		try {
			switch(types) {
				case "SAGAWA":
					result = usrService.insertExcelDataForSagawa(multi, request,member.getUsername(), "NOMAL", types);
					break;
				case "TEST111":
					result = usrService.insertExcelDataNew(multi, request,member.getUsername(), "NOMAL");
					break;
				default:
					result = usrService.insertExcelDataTest(multi, request,member.getUsername(), "NOMAL");	
					break;
			}
 		}catch (Exception e) {
 			result = "F";
		}
		return result;
	}
	
	/**
	 * Page : USER 배송대행 단일 신청페이지
	 * URI : /cstmr/apply/shpng/registOne
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpng/registOne",method=RequestMethod.GET)
	public String userRegistOne(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String transCom = new String();
		if(request.getParameter("transCode")!=null)
			transCom = request.getParameter("transCode");
		
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<String> dstnList = new ArrayList<String>();
		for (int i = 0; i < userDstnNation.size(); i++) {
			dstnList.add(userDstnNation.get(i).getDstnNation());
		}
		params.put("dstns", dstnList);
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = comnService.selectCurrencyListByDstnNation(params);
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
		UserVO userInfo =  usrService.selectUserInfo(member.getUsername()); 
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		//ArrayList<NationVO> orgNationList = new ArrayList<NationVO>();
		//nationList = comnService.selectNationCode();
		nationList = comnService.selectUserNationCode(member.getUsername());
		//orgNationList = comnService.selectNationInStation();
		ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
		transComList = usrService.selectTrkComList((String)request.getSession().getAttribute("USER_ID"));
		model.addAttribute("transComList",transComList);
		model.addAttribute("nationList", nationList);
		//model.addAttribute("orgNationList", orgNationList);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("userOrgNation", userOrgNation);     
		model.addAttribute("userOrgStation",userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("userId", member.getUsername());

		if(!transCom.equals("")) {
			ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
			OrderListOptionVO optionOrderVO = new OrderListOptionVO();
			OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
			OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
			OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
			String dstnNation = request.getParameter("dstnNation");
			optionOrderVO = mgrService.SelectOrderListOption(transCom,dstnNation);
			optionItemVO = mgrService.SelectOrderItemOption(transCom,dstnNation);
			expressOrderVO = mgrService.SelectExpressListOption(transCom,dstnNation);
			expressItemVO = mgrService.SelectExpressItemOption(transCom,dstnNation);
			taxTypeList = mgrService.selectTaxTypeList();
			model.addAttribute("optionOrderVO", optionOrderVO);
			model.addAttribute("optionItemVO", optionItemVO);
			model.addAttribute("expressOrderVO", expressOrderVO);
			model.addAttribute("expressItemVO", expressItemVO);
			model.addAttribute("targetCode",transCom);
			model.addAttribute("selectDstn",dstnNation);
			model.addAttribute("currencyList", currencyList);
			model.addAttribute("params", params);
			model.addAttribute("taxTypeList", taxTypeList);
			return "user/apply/shpng/regist/registOneAll";
		}else {
			return "user/apply/shpng/regist/registOne";
		}
	}
	
	
	@RequestMapping(value = "/cstmr/apply/searchItemCode", method = RequestMethod.GET)
	public String userSearchItemHSCode(HttpServletRequest request, Model model) throws Exception {
		ArrayList<HashMap<String, Object>> codeList = new ArrayList<HashMap<String, Object>>();
		codeList = usrService.selectHsCodeList();
		
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		
		model.addAttribute("cnt", cnt);
		model.addAttribute("codeList", codeList);
		return "user/apply/comn/popupHsCodeInfo";
	}
	
	
	/**
	 * Page : USER 배송대행 단일 신청 등록
	 * URI : /cstmr/apply/registOrderInfo
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/registOrderInfo",method=RequestMethod.POST)
	@ResponseBody
	public String userRegistOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception{
		String result="F";
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		UserVO userInfo = new UserVO();
		userInfo = usrService.selectUserInfo(member.getUsername());
		
		
		Date time = new Date();
		String time1 = format1.format(time);
		/*orderList*/
		userOrderList.setOrderType("NOMAL");
		/*orderItem*/
		/* userOrderItems = request.getParameter(name) */
		
		userOrderList.setUserId(member.getUsername());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);
		//userOrderList.setShipperAddr(userInfo.getUserEAddr()+" "+userInfo.getUserEAddrDetail());
		//userOrderList.setShipperZip(userInfo.getUserZip());
		
		try {
			//userOrderList.encryptData();
			switch (userOrderList.getTransCode()) {
		    case "ACI":
		        // a-z
				/* usrService.insertUserOrderListSagawa(userOrderList, userOrderItemList); */
		    	usrService.insertUserOrderListARA(userOrderList, userOrderItemList);
		        break;
		    case "ARA":
		        // A-Z
		    	usrService.insertUserOrderListARA(userOrderList, userOrderItemList);
		        break;
	        default:
	        	usrService.insertUserOrderListARA(userOrderList, userOrderItemList);
	        	break;
		    }
			
			
			result = "S";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}
	
	/**
	 * Page : USER 배송대행 단일 신청 - 수정
	 * URI : /cstmr/apply/modifyOrderInfo
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/modifyOrderInfo",method=RequestMethod.POST)
	@ResponseBody
	public String userModifyOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception{
		String result="F";
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		Date time = new Date();
		String time1 = format1.format(time);
		/*orderList*/
		/* userOrderList.setDstnNation(userOrderList.getDstnNation()); */
		userOrderList.setDstnStation(userOrderList.getDstnNation());
		userOrderList.setOrderType("NOMAL");
		/*orderItem*/
		/* userOrderItems = request.getParameter(name) */
		
		userOrderList.setUserId(member.getUsername());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);
		
		try {
			String[] orderNno = null;
			//userOrderList.encryptData();
			usrService.updateUserOrderListARA(userOrderList, userOrderItemList);
			
			result = "S";
		}catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}
	
	
	/**
	 * Page : USER 배송대행 신청 - 운송장 등록 리스트 - 쿠팡 Table Form
	 * URI : /mngr/aplctList/cupangForm
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngSubpage",method=RequestMethod.POST)
	public String userApplySubpage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String target = request.getParameter("selOption");
		if(target.equals("1")) {
			return "user/apply/shpng/cupang";
		}else if (target.equals("2")) {
			return "user/apply/shpng/11st";
		}else if (target.equals("3")) {
			return "user/apply/shpng/naver";
		}else if (target.equals("4")) {
			return "user/apply/shpng/aci";
		}else if (target.equals("5")) {
			return "user/apply/shpng/custom";
		}else {
			return "";
		}
		
	}
	
	/**
	 * Page : USER 검품배송 신청
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */ 
	
	@RequestMapping(value="/cstmr/apply/inspDlvry",method=RequestMethod.GET)
	public String userInspcDlvry(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		//String transCode = request.getParameter("transCode");
		String transCode = "";
		for (int i = 0; i < userDstnNation.size(); i++) {
			transCode = userDstnNation.get(i).getIdx();
		}
		model.addAttribute("transCode",transCode);   
		model.addAttribute("userDstnNation",userDstnNation);
		String rtnUrl = "user/apply/inspc/list/inspDlvry"; 
		
		return rtnUrl;
	}
	
	/**
	 * Page : USER 검품배송 신청
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */ 
	
	@RequestMapping(value="/cstmr/apply/inspDlvryTable",method=RequestMethod.POST)
	public String userInspcDlvryTable(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		int curPage = 1;
		model.addAttribute("nationInfo",userDstnNation);
		String searchIdx = request.getParameter("searchIdx");
		String transCode = request.getParameter("searchIdx");
		HashMap<String, Object> idxInfo = new HashMap<String,Object>();
		if(searchIdx != null && !searchIdx.equals("All")) {
			idxInfo = usrService.selectUserTransInfo(searchIdx);
			transCode = idxInfo.get("transCode").toString();
		}
		parameter.put("transCode", transCode);
		model.addAttribute("testTrans",transCode);
		String rtnUrl = "user/apply/inspc/list/inspDlvryAll"; 
		parameter.put("userId", member.getUsername());
		parameter.put("orderType", "INSP");
		
		String txtType ="";
		String searchTxt ="";
		if(request.getParameter("txtType") != null) {
			txtType = request.getParameter("txtType");
			searchTxt = request.getParameter("searchTxt");
		}
		parameter.put("txtType", txtType);
		if(txtType.equals("0")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail",searchTxt);
		}else if(txtType.equals("1")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("2")) {
			parameter.put("orderNo","");
			parameter.put("trkNo",searchTxt);
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("3")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail","");
		}else if(txtType.equals("4")) {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail",searchTxt);
		}else {
			parameter.put("orderNo","");
			parameter.put("trkNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}
		 
		int totalCount = usrService.selectShpngListCount(parameter);
		if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,10,20);
		
		parameter.put("paging", paging);
		inspList = usrService.selectShpngList(parameter);
		model.addAttribute("paging", paging);
		model.addAttribute("inspList", inspList);
		return rtnUrl;
	}
	
	/**
	 * Page : USER 검품배송 신청
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/modifyInfoInsp",method=RequestMethod.POST)
	public String userModifyInfoInsp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		HashMap<String,String> orderInfo = new HashMap<String,String>();
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = usrService.selectUserOrderOne(request.getParameter("nno"));
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new  ArrayList<UserOrderItemVO>();
		userOrderItem = usrService.selectUserOrderItemOne(userOrder);
		
//		String targetTrans = request.getParameter("totals");
//		model.addAttribute("targetCode",targetTrans);
		//model.addAttribute("urlType","allAbout");
//		String targetTypes = request.getParameter("totals");
//		String[] types = targetTypes.split("_");
		
		model.addAttribute("urlType",request.getParameter("urlType"));
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = comnService.selectNationCode();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		
		String transCom = userOrder.getTransCode();
		model.addAttribute("userOrder",userOrder);
		model.addAttribute("userOrderItem",userOrderItem);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("nationList", nationList);
		model.addAttribute("transCode",transCom);
		model.addAttribute("userId", member.getUsername());
		
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		
		String dstnNation = userOrder.getDstnNation();
		
		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
		taxTypeList = mgrService.selectTaxTypeList();
		
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("taxTypeList", taxTypeList);
		
		
		return "user/apply/inspc/modify/userInspcModify";
	}
	
	
	/**
	 * Page : USER 검품배송 엑셀 업로드
	 * URI : /cstmr/apply/inspAgncyExcelUpload
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/inspAgncyExcelUpload",method=RequestMethod.POST)
	@ResponseBody
	public String userInspAgncyExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = "F";
		//String[] types = request.getParameter("formSelect").split("_");
		String types = request.getParameter("formSelect");
		
		
		try {
			switch(types) {
				case "ACI":
					result = usrService.insertExcelDataForSagawa(multi, request,member.getUsername(), "INSP", types);
					break;
				default :
					result = usrService.insertExcelData(multi, request,member.getUsername(), "INSP");
					break;
			}
 		}catch (Exception e) {
			// TODO: handle exception
 			result = "F";
		}
 		
		return result;
	}
	
	/**
	 * Page : USER 배송대행 단일 신청 - 검수
	 * URI : /cstmr/apply/registOrderInfoInsp
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/registOrderInfoInsp",method=RequestMethod.POST)
	@ResponseBody
	public String userRegistOrderInfoInsp(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception{
		String result="F";
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		Date time = new Date();
		String time1 = format1.format(time);
		/*orderList*/
		/* userOrderList.setDstnNation(userOrderList.getDstnNation()); */
		userOrderList.setDstnStation(userOrderList.getDstnNation());
		userOrderList.setOrderType("INSP");
		/*orderItem*/
		/* userOrderItems = request.getParameter(name) */
		
		userOrderList.setUserId(member.getUsername());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);
		
		try {
			//userOrderList.encryptData();
			
			switch (userOrderList.getTransCode()) {
		    case "ACI":
		        // a-z
				/* usrService.insertUserOrderListSagawa(userOrderList, userOrderItemList); */
		    	usrService.insertUserOrderListARA(userOrderList, userOrderItemList);
		        break;
	        default :
		        // A-Z
		    	usrService.insertUserOrderListARA(userOrderList, userOrderItemList);
		        break;
		    }
			result = "S";
		}catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}
	
	
	/**
	 * Page : USER 배송대행 단일 신청 - 검수 - 수정
	 * URI : /cstmr/apply/modifyOrderInfoInsp
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/modifyOrderInfoInsp",method=RequestMethod.POST)
	@ResponseBody
	public String userModifyOrderInfoInsp(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception{ 
		String result="F";
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		Date time = new Date();
		String time1 = format1.format(time);
		request.getParameter("userDstnNation");
		/*orderList*/
		/* userOrderList.setDstnNation(userOrderList.getDstnNation()); */
		userOrderList.setDstnStation(userOrderList.getDstnNation());
		userOrderList.setOrderType("INSP");
		/*orderItem*/
		/* userOrderItems = request.getParameter(name) */
		
		userOrderList.setUserId(member.getUsername());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);
		
		try {
			String[] orderNno = null;
			//userOrderList.encryptData();
			usrService.updateUserOrderListARA(userOrderList, userOrderItemList);
			 
			result = "S";
		}catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}
	
	/**
	 * Page : USER 배송대행 단일 신청페이지 공통
	 * URI : /cstmr/apply/insp/registOne
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/insp/registOne",method=RequestMethod.GET)
	public String userRegistOneInsp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String transCom = new String();
		if(request.getParameter("transCode")!=null)
			transCom = request.getParameter("transCode");
		
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//ArrayList<NationVO> nationList = comnService.selectNationCode();
		ArrayList<NationVO> nationList = comnService.selectUserNationCode(member.getUsername());
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<String> dstnList = new ArrayList<String>();
		for (int i = 0; i < userDstnNation.size(); i++) {
			dstnList.add(userDstnNation.get(i).getDstnNation());
		}
		params.put("dstns", dstnList);
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = comnService.selectCurrencyListByDstnNation(params);
		
		transComList = usrService.selectTrkComList((String)request.getSession().getAttribute("USER_ID"));
		model.addAttribute("transComList",transComList);
		
		UserVO userInfo =  usrService.selectUserInfo(member.getUsername());
		
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("nationList", nationList);
		model.addAttribute("userOrgNation", userOrgNation);
		model.addAttribute("userOrgStation",userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		
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
			taxTypeList = mgrService.selectTaxTypeList();
			
			model.addAttribute("taxTypeList", taxTypeList);
			model.addAttribute("optionOrderVO", optionOrderVO);
			model.addAttribute("optionItemVO", optionItemVO);
			model.addAttribute("expressOrderVO", expressOrderVO);
			model.addAttribute("expressItemVO", expressItemVO);
			model.addAttribute("targetCode",transCom);
			model.addAttribute("selectDstn",dstnNation);
			model.addAttribute("currencyList", currencyList);
			model.addAttribute("params", params);
			model.addAttribute("userId", member.getUsername());
			return "user/apply/inspc/regist/registOneAll";
		}else {
			return "user/apply/inspc/regist/registOne";
		}
		
	}
	
	/**
	 * Page : USER 검품배송 신청 - subpage
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/inspcSubpage",method=RequestMethod.POST)
	public String userInspcSubpage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String target = request.getParameter("selOption");
		if(target.equals("1")) {
			return "user/apply/inspc/cupang";
		}else if (target.equals("2")) {
			return "user/apply/inspc/11st";
		}else if (target.equals("3")) {
			return "user/apply/inspc/naver";
		}else if (target.equals("4")) {
			return "user/apply/inspc/aci";
		}else if (target.equals("5")) {
			return "user/apply/inspc/custom";
		}else {
			return "";
		}
	}
	
	/**
	 * Page : USER 사입배송 신청
	 * URI : /cstmr/apply/prchsDlvry
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/prchsDlvry",method=RequestMethod.GET)
	public String userPrchsDlvry(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "user/apply/prchs/prchsDlvry";
	}
	

	/**
	 * Page : USER 사입배송 신청
	 * URI : /cstmr/apply/prchsDlvry
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/prchsSubpage",method=RequestMethod.POST)
	public String userPrchsSubpage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String target = request.getParameter("selOption");
		if(target.equals("1")) {
			return "user/apply/prchs/cupang";
		}else if (target.equals("2")) {
			return "user/apply/prchs/11st";
		}else if (target.equals("3")) {
			return "user/apply/prchs/naver";
		}else if (target.equals("4")) {
			return "user/apply/prchs/aci";
		}else if (target.equals("5")) {
			return "user/apply/prchs/custom";
		}else {
			return "";
		}
	}
	
	@RequestMapping(value="/cstmr/apply/errorChkReload",method=RequestMethod.POST)
	@ResponseBody
	public String userShpngErrorReload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		usrService.updateErrorTmp(request.getSession().getAttribute("USER_ID").toString());
		return "";
	}
	
	/**
	 * Page : USER 배송대행 신청 공통 등록 과정 (temp -> order)
	 * URI : /cstmr/apply/registOrderList
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/registOrderList",method=RequestMethod.POST)
	@ResponseBody
	public String userShpngAgncyApplyPost(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String result= "F";
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		boolean sendChk = false;
			if(request.getParameterValues("targets") == null) {
				return "N";
			}
			
			BlApplyVO tmp = new BlApplyVO(); 
			String[] orderNno = request.getParameterValues("targets");
			String[] transCode = request.getParameterValues("transCode");
			String[] uploadTypes = request.getParameterValues("uploadType");
			
			String orderType = request.getParameter("orderType").toUpperCase();
			
			int totalChk = orderNno.length;
			int succChk = 0;

			if(orderType.equals("insp")) {
				for(int roop = 0; roop< orderNno.length; roop++) {
					result = comnService.comBlApplyCheckV2(orderNno[roop],transCode[roop],userId,userIp, orderType, uploadTypes[roop]);
					if (result.equals("S")) {
						succChk++;
					}
					//comnService.comnBlApplyCheck(orderNno[roop],transCode[roop],userId,userIp, request.getParameter("blType"));
					if(transCode[roop].equals("YSL")) {
						sendChk = true;
					}
				}
			}else {
//				return "S";
				for(int roop = 0; roop< orderNno.length; roop++) {
					result = comnService.comnBlApplyV2(orderNno[roop],transCode[roop],userId,userIp, orderType, uploadTypes[roop]);
					if (result.equals("S")) {
						succChk++;
					}
					//comnService.comnBlApply(orderNno[roop],transCode[roop],userId,userIp, orderType);
					if(transCode[roop].equals("YSL")) {
						sendChk = true;
					}
				}
			}
			
			//smtpService.sendMail();
			if(sendChk) {
				smtpService.sendMail();
			}
			result="S";
			//result="F";
			
			result = "배송 신청 처리 되었습니다.\n\n[전체] : " + totalChk + "\n[성공] : " + succChk;
		
		return result;
	}
	
	@RequestMapping(value = "/cstmr/apply/registTmpOrderList", method = RequestMethod.POST)
	@ResponseBody
	public String userTmpShpngAgncyApplyPost(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String result= "F";
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		boolean sendChk = false;
			if(request.getParameterValues("targets") == null) {
				return "N";
			}
			
			BlApplyVO tmp = new BlApplyVO(); 
			String[] orderNno = request.getParameterValues("targets");
			String[] transCode = request.getParameterValues("transCode");
			String orderType = request.getParameter("orderType");

			if(orderType.equals("insp")) {
				for (int i = 0; i < orderNno.length; i++) {
					if (transCode[i].equals("YSL")) {
						comnService.updateTmpOrderStatus(orderNno[i]);
					} else {
						comnService.comnBlApplyCheck(orderNno[i], transCode[i], userId, userIp, request.getParameter("blType"));
					}
				}
			} else {
				for (int i = 0; i < orderNno.length; i++) {
					if (transCode[i].equals("YSL")) {
						comnService.updateTmpOrderStatus(orderNno[i]);
					} else {
						comnService.comnBlApply(orderNno[i], transCode[i], userId, userIp, request.getParameter("blType"));
					}
				}
			}
			if(sendChk) {
				//smtpService.sendMail();
			}
			result = "S";
			
		return result;
	}
	
	/**
	 * Page : USER 배송대행 삭제 공통
	 * URI : /cstmr/apply/delOrderListTmp
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/delOrderListTmp",method=RequestMethod.POST)
	@ResponseBody
	public String userShpngAgncyApplyDeleteTmp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result= "F";
		try {
			comnService.deleteUserOrderTmp(request.getParameterValues("targets"),member.getUsername());
			
			result="S";
		}catch (Exception e) {
			// TODO: handle exception
			result="F";
		}
		return result;
	}
	
	/**
	 * Page : USER 배송대행 삭제 공통
	 * URI : /cstmr/apply/delOrderList
	 * Method : POST
	 * */
	@RequestMapping(value="/cstmr/apply/delOrderList",method=RequestMethod.POST)
	@ResponseBody
	public String userShpngAgncyApplyDelete(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result= "F";
		try {
			comnService.deleteUserOrder(request.getParameterValues("targets"),request);
			
			result="S";
		}catch (Exception e) {
			// TODO: handle exception
			result="F";
		}
		return result;
	}
	

	
	
	/**
	 * Page : USER 배송대행 단일 신청페이지 추가 아이템 공통
	 * URI : /cstmr/apply/itemList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/itemList",method=RequestMethod.POST)
	public String userItemList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int cnt = Integer.parseInt(request.getParameter("cnt"))+1;
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		String transCom = request.getParameter("transCode");
		String userId = request.getParameter("userId");
		
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(userId);
		ArrayList<String> dstnList = new ArrayList<String>();
		for (int i = 0; i < userDstnNation.size(); i++) {
			dstnList.add(userDstnNation.get(i).getDstnNation());
		}
		params.put("dstns", dstnList);
		ArrayList<HashMap<String, Object>> currencyList = new ArrayList<HashMap<String, Object>>();
		currencyList = comnService.selectCurrencyListByDstnNation(params);
		
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		
		String dstnNation = request.getParameter("dstnNation");
		
		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
		
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("targetCode",transCom);
		
		model.addAttribute("dstnNation", dstnNation);
		model.addAttribute("nations", nations);
		model.addAttribute("cnt",cnt);
		return "user/apply/comn/itemListAll";
	}
	
	/**
	 * Page : USER 배송대행 단일 신청페이지 택배회사 공통
	 * URI : /cstmr/apply/transComList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/transComList",method=RequestMethod.POST)
	@ResponseBody
	public Object userApplyTransComList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String,String> params = new HashMap<String, String>();
		if(member.getRole().equals("ADMIN")) {
			params.put("userId", request.getParameter("param3"));
		}else {
			params.put("userId", member.getUsername());
		}
		String orgNation = comnService.selectStationToNation(request.getParameter("param1"));
		params.put("orgNation", orgNation);
		params.put("dstnNation", request.getParameter("param2"));
		
		params.put("role", member.getRole());
		List<UserTransComVO> transComList = usrService.userTransCom(params);
		if(transComList.size() == 0) {
			params.put("dstnNation", request.getParameter(""));
			transComList = usrService.userTransCom(params);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("transComList", transComList);
		data.put("result","S");
		return data;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/shpngRegistList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngRegistList",method=RequestMethod.GET) 
	public String userShpngRegistList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//ArrayList<UserTransComVO> userDstnNation = usrService.selectUserOrgTrans(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans((String)request.getSession().getAttribute("USER_ID"));
		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
		String transCode = "";
		transCode = request.getParameter("idx");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
		model.addAttribute("trkComList",trkComList);
		model.addAttribute("selTransCode", transCode);
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","allAbout");
		
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		model.addAttribute("searchTxt",request.getParameter("searchTxt"));
		
		return "user/apply/registList/shpng/shpngRegistList";
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/shpngRegistList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngRegistErrorList",method=RequestMethod.GET) 
	public String userShpngRegistErrorList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//ArrayList<UserTransComVO> userDstnNation = usrService.selectUserOrgTrans(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans((String)request.getSession().getAttribute("USER_ID"));
		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
		String transCode = "";
		transCode = request.getParameter("transCode");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
		model.addAttribute("trkComList",trkComList);
		model.addAttribute("selTransCode", transCode);
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","error");
		
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		model.addAttribute("searchTxt",request.getParameter("searchTxt"));
		
		return "user/apply/registList/shpng/shpngRegistList";
	}
	
	/**
	 * Page : USER 배송대행 신청 리스트
	 * URI : /cstmr/apply/shpngRegistListTable
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngRegistListTable",method=RequestMethod.POST) 
	public String userShpngRegistListTable (HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
		String startDate="";
		String endDate = "";
		String idx = request.getParameter("idx");
		
		int curPage = 1;
		
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		parameter.put("urlType",request.getParameter("urlType"));
		parameter.put("userId", member.getUsername());

		parameter.put("orderType", "NOMAL");
		parameter.put("idx", idx);
		HashMap<String, Object> infoByIdx = new HashMap<String,Object> ();
		if(!idx.equals("All")) {
			infoByIdx = usrService.selectUserTransInfo(idx);
			parameter.put("transCode", infoByIdx.get("transCode").toString());
			parameter.put("dstnNation", infoByIdx.get("dstnNation").toString());
		}
		
		
		String txtType = "";
		String searchTxt = "";
		
		if(!request.getParameter("startDate").isEmpty()) {
			startDate = request.getParameter("startDate").replaceAll("-", "");
			parameter.put("startDate", startDate);
		}else {
			parameter.put("startDate", startDate);
		}
		
		if(!request.getParameter("endDate").isEmpty()) {
			endDate = request.getParameter("endDate").replaceAll("-", "");
			parameter.put("endDate", endDate);
		}else {
			parameter.put("endDate", endDate);
		}
		
		if(request.getParameter("txtType") != null) {
			txtType = request.getParameter("txtType");
			searchTxt = request.getParameter("searchTxt");
		}
		parameter.put("txtType", txtType);
		if(txtType.equals("0")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("hawbNo",searchTxt);
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail",searchTxt);
		}else if(txtType.equals("1")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("2")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo",searchTxt);
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("3")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail","");
		}else if(txtType.equals("4")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail",searchTxt);
		}else {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}
		
		int totalCount = usrService.selectRegistOrderCount(parameter);
		if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,10,50);
		
		parameter.put("paging", paging);
		
		if(request.getParameter("urlType").equals("allAbout"))
			shpngList = usrService.selectRegistOrderList(parameter);
		else if(request.getParameter("urlType").equals("before"))
			shpngList = usrService.selectSendBeforeOrderList(parameter);
		else if(request.getParameter("urlType").equals("after"))	
			shpngList = usrService.selectSendAfterOrderList(parameter);	
		else if(request.getParameter("urlType").equals("error"))
			shpngList = usrService.selectSendErrorOrderList(parameter);
		
		model.addAttribute("paging", paging);
		model.addAttribute("txtType", txtType);
		model.addAttribute("searchTxt", searchTxt);
		model.addAttribute("searchKeyword", searchTxt);
		model.addAttribute("shpngList", shpngList);
		if(request.getParameter("urlType").equals("error"))
			return "user/apply/registList/shpng/shpngRegistListErrorAll";
		return "user/apply/registList/shpng/shpngRegistListAll";
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/shpngSendBefore
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngSendBefore",method=RequestMethod.GET)
	public String userShpngSendBefore(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans((String)request.getSession().getAttribute("USER_ID"));
		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
		String transCode = "";
		transCode = request.getParameter("transCode");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
		model.addAttribute("trkComList",trkComList);
		model.addAttribute("selTransCode", transCode);
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
//		model.addAttribute("userDstnNation",userDstnNation);
//		String targetTrans = request.getParameter("totals");
//		model.addAttribute("targetCode",targetTrans);
		model.addAttribute("urlType","before");
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		model.addAttribute("searchTxt",request.getParameter("searchTxt"));
		
		return "user/apply/registList/shpng/shpngRegistList";
		
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
//		String transCode = "";
//		transCode = request.getParameter("transCode");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
//		shpngList = usrService.selectSendBeforeOrderList(member.getUsername(),"NOMAL",transCode);
//		trkComList = usrService.selectTrkComList();
//		model.addAttribute("trkComList",trkComList);
//		model.addAttribute("shpngList", shpngList);
//		model.addAttribute("selTransCode", transCode);
//		return "user/apply/registList/shpng/shpngSendBeforeList"+transCode;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/shpngSendAfter
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/shpngSendAfter",method=RequestMethod.GET)
	public String userShpngSendAfter(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
//		model.addAttribute("userDstnNation",userDstnNation);
//		String targetTrans = request.getParameter("totals");
//		model.addAttribute("targetCode",targetTrans);
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans((String)request.getSession().getAttribute("USER_ID"));
		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
		String transCode = "";
		transCode = request.getParameter("transCode");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
		model.addAttribute("trkComList",trkComList);
		model.addAttribute("selTransCode", transCode);
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","after");
		
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		model.addAttribute("searchTxt",request.getParameter("searchTxt"));
		return "user/apply/registList/shpng/shpngRegistList";
		
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
//		String transCode = "";
//		transCode = request.getParameter("transCode");
//		if(request.getParameter("transCode") == null) {
//			transCode = "ACI";
//		}
//		shpngList = usrService.selectSendAfterOrderList(member.getUsername(),"NOMAL",transCode);
//		trkComList = usrService.selectTrkComList();
//		model.addAttribute("trkComList",trkComList);
//		model.addAttribute("shpngList", shpngList);
//		model.addAttribute("selTransCode", transCode);
//		return "user/apply/registList/shpng/shpngSendAfterList"+transCode;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/inspRegistList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/inspRegistList",method=RequestMethod.GET)
	public String userInspRegistList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		model.addAttribute("userDstnNation",userDstnNation);
		String transCode = request.getParameter("transCode");
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","allAbout");
		return "user/apply/registList/inspc/inspRegistList";
		
		
		
//		
//		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		String transCode = "";
//		try {
//			transCode = request.getParameter("transCode");
//			if(request.getParameter("transCode") == null) {
//				transCode = "ARA";
//			}
//			inspList = usrService.selectRegistOrderList(member.getUsername(),"INSP",transCode);
//			ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
//			trkComList = usrService.selectTrkComList();
//			model.addAttribute("trkComList",trkComList);
//			model.addAttribute("inspList", inspList);
//			model.addAttribute("selTransCode", transCode);
//		}catch (Exception e) {
//			// TODO: handle exception
//			inspList = null;
//		}
//		return "user/apply/registList/inspc/inspRegistList"+transCode;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/inspRegistListTable
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/inspRegistListTable",method=RequestMethod.POST)
	public String userInspRegistListTable(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> ();
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
		model.addAttribute("userDstnNation",userDstnNation);
		String idx = request.getParameter("idx");
		String transCode = "";
		HashMap<String,Object> idxInfo = new HashMap<String,Object>();
		if(!idx.equals("All")) {
			idxInfo = usrService.selectTransIdx(idx);
			transCode = idxInfo.get("transCode").toString();
		}
		model.addAttribute("targetCode",transCode);
		int curPage = 1;
		HashMap<String, Object> parameter = new HashMap<String,Object>();
		parameter.put("urlType",request.getParameter("urlType"));
		parameter.put("userId", member.getUsername());
		parameter.put("orderType", "INSP");
		parameter.put("transCode", transCode);
		parameter.put("idx",idx);
		String txtType ="";
		String searchTxt ="";
		if(request.getParameter("txtType") != null) {
			txtType = request.getParameter("txtType");
			searchTxt = request.getParameter("searchTxt");
		}
		parameter.put("txtType", txtType);
		if(txtType.equals("0")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("hawbNo",searchTxt);
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail",searchTxt);
		}else if(txtType.equals("1")) {
			parameter.put("orderNo",searchTxt);
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("2")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo",searchTxt);
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}else if(txtType.equals("3")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo",searchTxt);
			parameter.put("itemDetail","");
		}else if(txtType.equals("4")) {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail",searchTxt);
		}else {
			parameter.put("orderNo","");
			parameter.put("hawbNo","");
			parameter.put("zipNo","");
			parameter.put("itemDetail","");
		}
		
		int totalCount = usrService.selectRegistOrderCount(parameter);
		if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,10,20);
		
		parameter.put("paging", paging);
		
		if(request.getParameter("urlType").equals("allAbout"))
			inspList = usrService.selectRegistOrderList(parameter);
		else if(request.getParameter("urlType").equals("before"))
			inspList = usrService.selectSendBeforeOrderList(parameter);
		else if(request.getParameter("urlType").equals("after"))	
			inspList = usrService.selectSendAfterOrderList(parameter);	

		model.addAttribute("paging", paging);
		model.addAttribute("txtType", txtType);
		model.addAttribute("searchTxt", searchTxt);
		model.addAttribute("inspList", inspList);
		return "user/apply/registList/inspc/inspRegistListAll";
	}
	
	@RequestMapping(value = "/cstmr/apply/inspTrkNoUpdate", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userInspTrkNoUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
		
			String nno = request.getParameter("nno");
			int subNo = Integer.parseInt(request.getParameter("subNo"));
			String trkNo = request.getParameter("trkNo").trim();
			String userId = (String) request.getSession().getAttribute("USER_ID");
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nno", nno);
			parameters.put("subNo", subNo);
			parameters.put("trkNo", trkNo);
			parameters.put("userId", userId);
			
			usrService.updateInspItemTrkNo(parameters);
			rst.put("code", "S");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			rst.put("code", "F");
		}

		
		return rst;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/inspSendBefore
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/inspSendBefore",method=RequestMethod.GET)
	public String userInspSendBefore(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		String transCode = "";
		transCode = request.getParameter("transCode");
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","before");
		return "user/apply/registList/inspc/inspRegistList";
		
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		String transCode = "";
//		try {
//			transCode = request.getParameter("transCode");
//			if(request.getParameter("transCode") == null) {
//				/* transCode = "ACI"; */
//				transCode = "ARA";
//			}
//			
//			inspList = usrService.selectSendBeforeOrderList(member.getUsername(),"INSP",transCode);
//			ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
//			trkComList = usrService.selectTrkComList();
//			model.addAttribute("trkComList",trkComList);
//			model.addAttribute("inspList", inspList);
//			model.addAttribute("selTransCode", transCode);
//		}catch (Exception e) {
//			// TODO: handle exception
//			inspList = null;
//		}
//		return "user/apply/registList/inspc/inspSendBeforeList"+transCode;
	}
	
	/**
	 * Page : USER 배송대행 신청
	 * URI : /cstmr/apply/inspSendAfter
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/inspSendAfter",method=RequestMethod.GET)
	public String userInspSendAfter(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> ();
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans(member.getUsername());
		String transCode = "";
		transCode = request.getParameter("transCode");
		model.addAttribute("userDstnNation",userDstnNation);
		model.addAttribute("transCode",transCode);
		model.addAttribute("urlType","after");
		return "user/apply/registList/inspc/inspRegistList";
//		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>> ();
//		String transCode = "";
//		try {
//			transCode = request.getParameter("transCode");
//			if(request.getParameter("transCode") == null) {
//				/* transCode = "ACI"; */
//				transCode = "ARA";
//			}
//			inspList = usrService.selectSendAfterOrderList(member.getUsername(),"INSP",transCode);
//			ArrayList<UserTransComVO> trkComList = new ArrayList<UserTransComVO>();
//			trkComList = usrService.selectTrkComList();
//			model.addAttribute("trkComList",trkComList);
//			model.addAttribute("inspList", inspList);
//			model.addAttribute("selTransCode", transCode);
//		}catch (Exception e) {
//			// TODO: handle exception
//			inspList = null;
//		}
//		return "user/apply/registList/inspc/inspSendAfterList"+transCode;
	}
	
	/**
	 * Page : USER 검품배송 신청
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/modifyRegistInfoInsp",method=RequestMethod.POST)
	public String userModifyRegistInfoInsp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
		HashMap<String,String> orderInfo = new HashMap<String,String>();
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = comnService.selectUserRegistOrderOne(request.getParameter("nno"));
		
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new  ArrayList<UserOrderItemVO>();
		userOrderItem = usrService.selectUserRegistOrderItemOne(userOrder);
		
//		String targetTrans = request.getParameter("totals");
//		model.addAttribute("targetCode",targetTrans);
		//model.addAttribute("urlType","allAbout");
//		String targetTypes = request.getParameter("totals");
//		String[] types = targetTypes.split("_");
		
		model.addAttribute("urlType",request.getParameter("urlType"));
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		nationList = comnService.selectNationCode();
		String transCom = userOrder.getTransCode();
		
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		
		String dstnNation = userOrder.getDstnNation();
		
		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
		taxTypeList = mgrService.selectTaxTypeList();
		
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("nationList", nationList);
		
		model.addAttribute("userOrder",userOrder);
		model.addAttribute("userOrderItem",userOrderItem);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("taxTypeList", taxTypeList);
		return "user/apply/registList/registModifyInspAll";
		//return "user/apply/registList/modifyInspOne"+userOrder.getTransCode();
	}
	
	/**
	 * Page : USER 검품배송 신청
	 * URI : /cstmr/apply/inspcDlvry   
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/modifyRegistShpng",method=RequestMethod.POST) 
	public String userModifyRegistShpng(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(member.getUsername());
		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = comnService.selectUserRegistOrderOne(request.getParameter("nno"));
		
		UserVO userInfo =  usrService.selectUserInfo(member.getUsername());
		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new  ArrayList<UserOrderItemVO>();
		userOrderItem = usrService.selectUserRegistOrderItemOne(userOrder);
		
//		String targetTrans = request.getParameter("totals");
//		model.addAttribute("targetCode",targetTrans);
//		model.addAttribute("urlType","allAbout");
//		String targetTypes = request.getParameter("totals");
//		String[] types = targetTypes.split("_");
		
		model.addAttribute("urlType",request.getParameter("urlType"));
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		taxTypeList = mgrService.selectTaxTypeList();
		nationList = comnService.selectNationCode();
		String transCom = userOrder.getTransCode();
		
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		
		String dstnNation = userOrder.getDstnNation();
		
		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
		
		model.addAttribute("taxTypeList", taxTypeList);
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("nationList", nationList);
		
		model.addAttribute("userOrder",userOrder);
		model.addAttribute("userOrderItem",userOrderItem);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		
		return "user/apply/registList/registModifyShpngAll";
		//return "user/apply/registList/modifyShpngOne"+userOrder.getTransCode();
	}

	/**
	 * Page : USER 결제하기
	 * URI : /cstmr/apply/pymnt
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/apply/pymnt",method=RequestMethod.GET)
	public String userPymnt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 해외 반송 신청서
	 * URI : /cstmr/rtrn/rtrnAplct
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/rtrn/rtrnAplct",method=RequestMethod.GET)
	public String userRtrnAplct(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 반송 결제하기
	 * URI : /cstmr/rtrn/rtrnPymnt
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/rtrn/rtrnPymnt",method=RequestMethod.GET)
	public String userRtrnPymnt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 배송 발송 현황
	 * URI : /cstmr/dlvry-stts
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dlvry/stts",method=RequestMethod.GET)
	public String userDlvryStts(HttpServletRequest request, HttpServletResponse response, Model model, SendVO search) throws Exception{
		try {
			int curPage = 1;
			search.setUserId((String)request.getSession().getAttribute("USER_ID"));  
			//search.setUserId("delivered");
			int totalCount = usrService.selectSendListCount(search);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			PagingVO paging = new PagingVO(curPage, totalCount,20,50);
			search.setPaging(paging);	
			ArrayList<SendVO> sendList = usrService.selectSendList(search);
			
			for(int i = 0; i < sendList.size(); i++) {
				sendList.get(i).dncryptData();
			}
			
			model.addAttribute("search", search);
			model.addAttribute("sendList", sendList);
			model.addAttribute("paging", paging);	
		}catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return "user/send/sendList";
	}
	
	@RequestMapping(value = "/cstmr/dlvry/excelDown", method = RequestMethod.POST)
	public void userDlvryExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fromDate = request.getParameter("startDate");
		String toDate = request.getParameter("endDate");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		params.put("userId", (String) request.getSession().getAttribute("USER_ID"));
	
		ArrayList<String> nnoList = new ArrayList<String>();
		nnoList = usrService.selectSendNnoList(params);
		/*
		ArrayList<String> nnoList = new ArrayList<String>();
		
		String[] targets = request.getParameter("targetInfos").split(",");
		for (int i = 0; i < targets.length; i++) {
			nnoList.add(targets[i]);
		}
		*/
		usrService.excelDownUserDlvry(request, response, nnoList);
	}
	
	/**
	 * Page : USER 배송 미발송 현황
	 * URI : /cstmr/dlvry-undlvStts
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dlvry/undlvStts",method=RequestMethod.GET)
	public String userDlvryUndlvStts(HttpServletRequest request, HttpServletResponse response, Model model, SendVO search) throws Exception{
		
		int curPage = 1;
		search.setUserId((String)request.getSession().getAttribute("USER_ID"));  
		int totalCount = usrService.selectUnSendListCount(search);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20,50);
				
		search.setPaging(paging);	
		
		ArrayList<SendVO> sendList = usrService.selectUnSendList(search);
		
		for(int i = 0; i < sendList.size(); i++) {
			sendList.get(i).dncryptData();
		}
		
		model.addAttribute("sendList", sendList);
		model.addAttribute("paging", paging);
		return "user/send/sendUnList";
	}

	/**
	 * Page : USER 배송 POD 조회
	 * URI : /cstmr/dlvry-podLokup
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dlvry/podLokup",method=RequestMethod.GET)
	public String userPodLokup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 예치금 신청
	 * URI : /cstmr/dpst-dpstAplct
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dpst/dpstAplct",method=RequestMethod.GET)
	public String userDpstAplct(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 예치금 입출금 리스트
	 * URI : /cstmr/dpst-wthdrList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dpst/wthdrList",method=RequestMethod.GET)
	public String userWthdrList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}

	/**
	 * Page : USER 예치금 환불 신청
	 * URI : /cstmr/dpst-rqstRfnd
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/dpst/rqstRfnd",method=RequestMethod.GET)
	public String userRqstRfnd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 공지사항
	 * URI : /cstmr/srvc/ntc
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/srvc/ntc",method=RequestMethod.GET)
	public String userSrvcNtc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int curPage = 1;
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId",member.getUsername());
		infoMap.put("role", member.getRole());
		infoMap.put("searchWord", request.getParameter("searchWord"));
		int totalCount = comnService.selectTotalCntNotice(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20,50);
		infoMap.put("paging", paging);
		
		ArrayList<NoticeVO> noticeInfo = comnService.selectNotice(infoMap);
		for(int i = 0; i < noticeInfo.size(); i++) {
			noticeInfo.get(i).setDate(noticeInfo.get(i).getWDate());
		}
		model.addAttribute("noticeInfo", noticeInfo);
		model.addAttribute("paging", paging);
		return "comn/notice/comnNotice";
	}
	
	@RequestMapping(value="/cstmr/srvc/ntcDetail",method=RequestMethod.GET)
	public String userSrvcNtcDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("role", member.getRole());
		infoMap.put("userId",member.getUsername());
		infoMap.put("ntcNo", request.getParameter("ntcNo"));
		
		NoticeVO noticeInfoDetail = comnService.selectNoticeDetail(infoMap);
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date date = format1.parse(noticeInfoDetail.getWDate());
		String time1 = format2.format(date);
		noticeInfoDetail.setDate(time1);
		model.addAttribute("noticeDetail", noticeInfoDetail);
		return "comn/notice/comnNoticeDetail";
	}

	/**
	 * Page : USER 자주하는 질문
	 * URI : /cstmr/srvc/frqntAskqs
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/srvc/frqntAskqs",method=RequestMethod.GET)
	public String userSrvcFrqntAskqs(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}

	/**
	 * Page : USER 1:1 문의하기
	 * URI : /cstmr/srvc/cntct
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/srvc/cntct",method=RequestMethod.GET)
	public String userSrvcCntct(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 이용후기
	 * URI : /cstmr/srvc/rvws
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/srvc/rvws",method=RequestMethod.GET)
	public String userSrvcRvws(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 이벤트
	 * URI : /cstmr/srvc/event
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/srvc/event",method=RequestMethod.GET)
	public String userSrvcEvent(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 마이페이지/신청서 리스트
	 * URI : /cstmr/myPage/aplctList
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/aplctList",method=RequestMethod.GET)
	public String userMypageList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}

	/**
	 * Page : USER 정보 수정 - 조회
	 * URI : /cstmr/myPage/userInfo
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/userInfo",method=RequestMethod.GET)
	public String userMypageUserInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		ArrayList<String> userOrgNation = new ArrayList<String>();
		ArrayList<UserTransComVO> userDstnNation = new ArrayList<UserTransComVO>();
		ArrayList<AllowIpVO> allowIpList = new ArrayList<AllowIpVO>();
		InvUserInfoVO invUserInfo = new InvUserInfoVO(); 
		
		userOrgNation = usrService.selectUserOrgNation(member.getUsername());
		userDstnNation = usrService.selectUserDstnNation(member.getUsername());
		UserVO comUserInfo = usrService.selectUserInfo(member.getUsername());
		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		allowIpList = usrService.selectAllowIpList(member.getUsername());
		invUserInfo = comnService.selectInvUserInfo(member.getUsername());
		invUserInfo.dncryptData();
		
		model.addAttribute("userOrgNation", userOrgNation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		model.addAttribute("invUserInfo", invUserInfo);
		
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("allowIpList",allowIpList);
		
		return "user/myPage/modifyUserInfo";
		//return "user/myPage/modifyUserInfo2";
	}
	
	@RequestMapping(value = "/cstmr/myPage/userPwCheck", method = RequestMethod.GET)
	@ResponseBody
	public String userMyPagePwCheck(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = "";
		
		String userId = member.getUsername();
		String userPw = request.getParameter("userPw");
		
		ManagerVO userInfo = new ManagerVO();
		
		userInfo.setUserId(userId);
		userInfo.setUserPw(userInfo.encryptSHA256(userPw));
		
		int count = usrService.selectUserPassword(userInfo);
		
		if (count > 0) {
			result = "Y";
		} else {
			result = "N";
		}
		
		return result;
	}
	
	/**
	 * Page : uploadExcelForm 설정 화면 호출
	 * URI : /cstmr/myPage/uploadExcelForm
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/uploadExcelForm",method=RequestMethod.GET)
	public String uploadExcelFormGet(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<String> orgNationOption = new ArrayList<String>();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		orgNationOption = usrService.selectOrgNationOption(userId);
		model.addAttribute("orgNationOption",orgNationOption);
		
		return "user/myPage/settingExcelUpload";
	}
	
	/**
	 * Page : uploadExcelForm 설정 화면 호출
	 * URI : /cstmr/myPage/uploadExcelForm
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/uploadExcelFormField",method=RequestMethod.GET)
	public String uploadExcelFormFieldGet(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		//SelectOrderListOption(transCom,dstnNation)
		OrderListOptionVO reqiredValue = new OrderListOptionVO();
		OrderItemOptionVO reqiredItemValue = new OrderItemOptionVO();
		reqiredValue = mgrService.SelectOrderListOption(request.getParameter("transCodeOptionsInfo"),request.getParameter("dstnNationOptionsInfo"));
		reqiredItemValue = mgrService.SelectOrderItemOption(request.getParameter("transCodeOptionsInfo"),request.getParameter("dstnNationOptionsInfo"));
		
		model.addAttribute("reqiredValue",reqiredValue);
		model.addAttribute("reqiredItemValue",reqiredItemValue);
		return "user/myPage/settingExcelUploadField";
	}
	
	@RequestMapping(value="cstmr/myPage/orgStationOption",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<HashMap<String,Object>> uploadExcelFormGetOrgStationOption(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		ArrayList<HashMap<String,Object>> orgStationOption = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId",request.getSession().getAttribute("USER_ID"));
		parameters.put("orgNation", request.getParameter("orgNationOptionsInfo"));
		orgStationOption = usrService.selectOrgStationOption(parameters);
		return orgStationOption;
	}
	
	@RequestMapping(value="cstmr/myPage/dstnNationOptions",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> uploadExcelFormGetDstnNationOptions(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		ArrayList<String> dstnNationOption = new ArrayList<String>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId",request.getSession().getAttribute("USER_ID"));
		parameters.put("orgNation", request.getParameter("orgNationOptionsInfo"));
		parameters.put("orgStation", request.getParameter("orgStationOptionsInfo"));
		dstnNationOption = usrService.selectDstnNationOption(parameters);
		return dstnNationOption;
	}
	
	@RequestMapping(value="cstmr/myPage/transCodeOptions",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> uploadExcelFormGetTransCodeOptions(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		ArrayList<String> transCodeOption = new ArrayList<String>();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId",request.getSession().getAttribute("USER_ID"));
		parameters.put("orgNation", request.getParameter("orgNationOptionsInfo"));
		parameters.put("orgStation", request.getParameter("orgStationOptionsInfo"));
		parameters.put("dstnNation", request.getParameter("dstnNationOptionsInfo"));
		transCodeOption = usrService.selectTransCodeOption(parameters);
		return transCodeOption;
	}
	
	@RequestMapping(value="cstmr/myPage/uploadExcelForm",method=RequestMethod.POST)
	@ResponseBody
	public String uploadExcelFormFieldPost(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId",request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp",request.getSession().getAttribute("USER_IP"));
		parameters.put("orgNation", request.getParameter("orgNationOptions"));
		parameters.put("orgStation", request.getParameter("orgStationOptions"));
		parameters.put("dstnNation", request.getParameter("dstnNationOptions"));
		parameters.put("transCode", request.getParameter("transCodeOptions"));
		usrService.insertUploadSet(parameters);
		usrService.insertUploadColumn(request);
		return "S";
	}
	
	
	@RequestMapping(value="/cstmr/myPage/userInfoApi",method=RequestMethod.GET)
	public String userMypageUserInfoApi(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<AllowIpVO> allowIpList = new ArrayList<AllowIpVO>();
		ArrayList<HashMap<String,Object>> webhookList = new ArrayList<HashMap<String,Object>>();
		UserVO comUserInfo = usrService.selectUserInfo(member.getUsername());
		allowIpList = usrService.selectAllowIpList(member.getUsername());
		webhookList = comnService.selectWebHookInfo(member.getUsername());
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("allowIpList",allowIpList);
		model.addAttribute("webhookList",webhookList);
		return "user/myPage/modifyUserApiKey"; 
	}

	
	@RequestMapping(value = "/cstmr/myPage/linkShopee", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userMyPageLinkShopee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<>();
		HashMap<String, Object> params = new HashMap<>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		params.put("userId", userId);
		
		//int linkCnt = usrService.selectShopeeAuthCnt(params);
		
		long timeset = System.currentTimeMillis() / 1000L;
		String host = "https://partner.shopeemobile.com";
		String path = "/api/v2/shop/auth_partner";
		String redirectUrl = "http://wms.acieshop.com/api/"+userId+"/shopeeAuth";
		//redirectUrl = "https://wms.acieshop.com/api/shop/shopeeAuth/"+userId;
		
		long partner_id = 2008151L;
		String tmp_partner_key = "6567766350437346695a667a7a61525558754a414b54506b4d6c4f597a664d6d";
		String tmp_base_string = String.format("%s%s%s", partner_id, path, timeset);
		
		byte[] partner_key;
		byte[] base_string;
		String sign;
		
		base_string = tmp_base_string.getBytes("UTF-8");
		partner_key = tmp_partner_key.getBytes("UTF-8");
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
		mac.init(secret_key);
		
		sign = String.format("%064x", new BigInteger(1, mac.doFinal(base_string)));
		String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&redirect=%s", partner_id, timeset, sign, redirectUrl);
		rst.put("url", url);
		/*
		if (linkCnt > 0) {
			
			String expiryDate = usrService.selectShopeeAuthExpiryDate(params);
			rst.put("code", "D");
			rst.put("expiryDate", expiryDate);
			rst.put("url", url);

		} else {
			rst.put("code", "N");
			rst.put("expiryDate", "");
			rst.put("url", url);
		}
		*/
		return rst;
	}
	
	@RequestMapping(value="/cstmr/myPage/userInfoApi",method=RequestMethod.PATCH)
	@ResponseBody
	public String userMypageUserInfoApiPatch(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String queryResult = "";
		try {
			usrService.updateWebHook(request);
			queryResult = "S";
		}catch (Exception e) {
			// TODO: handle exception
			queryResult = "F";
		}
		return queryResult; 
	}
	
	
	/**
	 * Page : USER 접속 IP 설정 - 등록
	 * URI : /cstmr/myPage/registAllowIp
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/registAllowIp",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> userMypageRegistAllowIp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,String> result = new HashMap<String,String>();
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put("allowIp", request.getParameter("param1"));
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_ID"));
		
		String apiKey = usrService.selectApiKey(parameters.get("userId"));
		
		if(apiKey.equals("")|apiKey.isEmpty()) {
			StringBuffer temp = new StringBuffer();
			Random rnd = new Random();
			for (int i = 0; i < 32; i++) {
			    int rIndex = rnd.nextInt(3);
			    switch (rIndex) {
			    case 0:
			        // a-z
			        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
			        break;
			    case 1:
			        // A-Z
			        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
			        break;
			    case 2:
			        // 0-9
			        temp.append((rnd.nextInt(10)));
			        break;
			    }
			}
			usrService.insertApiKey(temp.toString(),parameters.get("userId"));
		}
				
		try {
			usrService.insertAllowIp(parameters);
			result.put("result", "S");
			result.put("allowIp", request.getParameter("param1"));
		}catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}
	
	/**
	 * Page : USER 접속 IP 설정 - 등록
	 * URI : /cstmr/myPage/registAllowIp
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/apiKeyAllow",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> userMypageApiKeyAllow(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,String> result = new HashMap<String,String>();
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		
		String apiKey = usrService.selectApiKey(parameters.get("userId"));
				
		try {
			if(apiKey.equals("")|apiKey.isEmpty()) {
				StringBuffer temp = new StringBuffer();
				Random rnd = new Random();
				for (int i = 0; i < 32; i++) {
				    int rIndex = rnd.nextInt(3);
				    switch (rIndex) {
				    case 0:
				        // a-z
				        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				        break;
				    case 1:
				        // A-Z
				        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				        break;
				    case 2:
				        // 0-9
				        temp.append((rnd.nextInt(10)));
				        break;
				    }
				}
				usrService.insertApiKey(temp.toString(),parameters.get("userId"));
			}
			result.put("result", "S");
		}catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}
	
	
	/**
	 * Page : USER 접속 IP 설정 - 삭제
	 * URI : /cstmr/myPage/deleteAllowIp
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/myPage/deleteAllowIp",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> userMypageDeleteAllowIp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,String> result = new HashMap<String,String>();
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put("allowIp", request.getParameter("param1"));
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_ID"));
		
		try {
			usrService.deleteAllowIp(parameters);
			result.put("result", "S");
		}catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}
	
	/**
	 * Page : USER 사용자 정보 수정 
	 * URI : /cstmr/myPage/userInfo
	 * Method : PATCH
	 */
	@RequestMapping(value = "/cstmr/myPage/userInfo", method = RequestMethod.PATCH)
	@ResponseBody
	public String managerModifyUserInfo(HttpServletRequest request, HttpServletResponse response,
			Model model, UserVO userInfoVO, InvUserInfoVO invUserInfo) throws Exception {
		String queryResult = "";
		try {
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
			Date time = new Date();
			String time1 = format1.format(time);
			if(!userInfoVO.getUserPw().isEmpty()) {
				userInfoVO.setUserPw(userInfoVO.encryptSHA256(userInfoVO.getUserPw()));
			}
			MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userInfoVO.setWUserId(member.getUsername());
			userInfoVO.setUserId(member.getUsername());
			userInfoVO.setWUserIp(request.getRemoteAddr());
			userInfoVO.setWDate(time1);
			userInfoVO.encryptData();
			usrService.updateUserInfo(userInfoVO, invUserInfo, request);
			queryResult = "S";
		} catch (Exception e) {
			queryResult = "F";
		}

		return queryResult;
	}

	@RequestMapping(value = "/cstmr/myPage/checkPw", method = RequestMethod.POST)
	@ResponseBody
	public String memberCheckPassword(HttpServletRequest request, HttpServletResponse response, Model model, UserVO userInfoVO) throws Exception {
		String result = "";
		String userId = request.getParameter("userId"); 
		String userPw = request.getParameter("checkPw");
		
		UserVO vo = new UserVO();
		
		try {
			userInfoVO = usrService.selectUserPwCheck(userId);
			if (vo.encryptSHA256(userPw).equals(userInfoVO.getUserPw())) {
				result = "S";
			} else {
				result = "F";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
		
	
	/**
	 * Page : User 마이 페이지 - 회원정보 - 택배회사 리스트 표출
	 * Method : GET
	 */
	@RequestMapping(value = "/cstmr/myPage/transCom", method = RequestMethod.GET)
	public String userTransComList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String,ArrayList<ZoneVO>> trkMap = new HashMap<String,ArrayList<ZoneVO>>();
		ArrayList<HashMap<String,Object>> blList = new ArrayList<HashMap<String,Object>>(); 
		HashMap<String,Object> TransMaps = new HashMap<String,Object>(); 
		TransMaps.put("orgs", request.getParameterValues("orgNation"));
		TransMaps.put("dstns", request.getParameterValues("dstnNation"));
		trkMap = comnService.makeTransMap(TransMaps);
		String userTrkValueList = request.getParameter("trkComValueList");
		blList = comnService.selectBlList();
		ArrayList<HashMap<String,Object>> userTrkCode = new ArrayList<HashMap<String,Object>>();
		String[] userTrk = null;
		if(userTrkValueList == null || userTrkValueList.isEmpty()) {
			userTrkCode = comnService.selectUserTrkCom(request.getParameter("userId"));
			model.addAttribute("userTrkCode", userTrkCode);
		}else {
			userTrk = userTrkValueList.split(",");
			model.addAttribute("userTrkCode", userTrk);
		}
		model.addAttribute("trkMap", trkMap);
		model.addAttribute("blList",blList);
		return "comn/transCom/trackComList";
	}

	/**
	 * Page : USER 재고관리/상품등록
	 * URI : /cstmr/stock/rgstr
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/stock/prdctRgstr",method=RequestMethod.GET)
	public String userStockPrdctRgstr(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	

	/**
	 * Page : USER 재고관리/사입상품 입고 등록
	 * URI : /cstmr/stock/rgstr
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/stock/prchsRgstr",method=RequestMethod.GET)
	public String userStockPrchsRgstr(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 재고관리/사입 재고 현황
	 * URI : /cstmr/stock/gdsrcRgstr
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/stock/invntStts",method=RequestMethod.GET)
	public String userStockInvntStts(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "default_Page3";
	}
	
	/**
	 * Page : USER 검품 현황
	 * URI : /cstmr/stock/inspcStock
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/stock/inspcStock",method=RequestMethod.GET)
	public String userStockInspcStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ArrayList<InspStockListVO> inspStockList = new ArrayList<InspStockListVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		int curPage = 1;
		parameterInfo.put("itemDetail", request.getParameter("prdctNameSch"));
		parameterInfo.put("cneeZip", request.getParameter("adresSch"));
		parameterInfo.put("trkNo", request.getParameter("trackNoSch"));
		parameterInfo.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		int totalCount = usrService.selectTotalCountStock(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20, 50);
		parameterInfo.put("paging", paging);

		inspStockList = usrService.selectInspStockOrderInfo(parameterInfo);
		for(int index=0; index < inspStockList.size(); index++) {
			inspStockList.get(index).dncryptData();
		}

		model.addAttribute("inspStockList", inspStockList);
		return "user/stock/inspcStock";
	}
	
	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : cstmr/stock/popupMsg 
	 * Method : GET
	 */
	@RequestMapping(value = "/cstmr/stock/popupMsg", method = RequestMethod.GET)
	public String userPopupMsg(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		
		parameters.put("nno",request.getParameter("nno"));
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		parameters.put("subNo",request.getParameter("subNo"));
		
		msgHis = usrService.selectMsgHist(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		
		return "user/stock/popupMsgHis";
	}
	
	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : cstmr/stock/mgrMsg
	 * Method : POST
	 */
	@RequestMapping(value = "/cstmr/stock/mgrMsg", method = RequestMethod.POST)
	public String userCstrMsg(HttpServletRequest request, HttpServletResponse response, Model model,StockMsgVO msgInfo)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String,String>();
		msgInfo.setMsgDiv("MSG");
		msgInfo.setAdminYn("N");
		msgInfo.setWUserId((String)request.getSession().getAttribute("USER_ID"));
		msgInfo.setWUserIp(request.getRemoteAddr());
		
		parameters.put("nno",request.getParameter("nno"));
		parameters.put("groupIdx",request.getParameter("groupIdx"));
		
		usrService.insertMsgInfo(msgInfo);
		
		msgHis = usrService.selectMsgHist(parameters); 
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters",parameters);
		
		return "user/stock/popupMsgHis";
	}
	
	/**
	 * Page : USER 재고관리/사입 재고 현황
	 * URI : /cstmr/stock/gdsrcRgstr
	 * Method : GET
	 * */
	@RequestMapping(value="/cstmr/test/invoicePrint",method=RequestMethod.GET)
	public String userInvoicePrint(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "user/apply/registList/invoicePrint";
	}
	
	
	@RequestMapping(value="/cstmr/apply/applyExcelDown",method=RequestMethod.GET)
	public String userApplyExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "user/apply/registList/invoicePrint";
	}
	
	@RequestMapping(value="/cstmr/apply/updateTrkNos",method=RequestMethod.POST)
	@ResponseBody
	public String userUpdateTrkNos(HttpServletRequest request, HttpServletResponse response, Model model, UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception{
		
		for(int i = 0 ; i < userOrderItemList.getTrkNo().length; i++) {
			usrService.updateItemTrkNo(userOrderItemList.getTrkNo()[i],userOrderList.getNno(),userOrderItemList.getSubNo()[i]);
		}
		return "S";
	}
	
	@RequestMapping(value="/cstmr/myPage/userCafe24Info",method=RequestMethod.GET)
	public String userMypageUserCafe24getOAuth(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		try {

			Cafe24UserTokenInfo parameterInfo = new Cafe24UserTokenInfo();
			parameterInfo.setUserId((String)request.getSession().getAttribute("USER_ID"));
			
			ArrayList<Cafe24UserTokenInfo> cafe24UserTokenInfo = new ArrayList<Cafe24UserTokenInfo>();
			cafe24UserTokenInfo = cafe24Service.selectUserCafe24TokenList(parameterInfo);
		
			model.addAttribute("cafe24UserTokenInfo",cafe24UserTokenInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return "user/myPage/userCafe24Info"; 
	}

	@RequestMapping(value = "/cstmr/myPage/userCsList", method = RequestMethod.GET)
	public String userMypageUserCsList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> csList = new ArrayList<HashMap<String, Object>>();
		csList = usrService.selectUserCsList(parameterInfo);
		
		
		model.addAttribute("csList", csList);
		return "user/myPage/userCsList";
	}
	
	@RequestMapping(value = "/cstmr/printCommercial", method = RequestMethod.GET)
	public void userPrintCommercial(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String printType = request.getParameter("formType");
		String[] targetTmp = request.getParameter("targetInfo").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		usrService.printCommercialPdf(request, response, orderNnoList, printType);
	}
	
	@RequestMapping(value = "/cstmr/printCommercialExcel", method = RequestMethod.GET)
	public void userPrintCommercialExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		usrService.printCommercialExcel(request,response, orderNnoList,printType);
	}
	
	@RequestMapping(value = "/cstmr/printPackingListExcel", method = RequestMethod.GET)
	public void userPrintPackingListExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//comnPrintHawbLegacy(request, response, model);
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		usrService.printPackingListExcel(request,response, orderNnoList,printType);
	}
	
	@RequestMapping(value = "/cstmr/printPackingListPdf", method = RequestMethod.GET)
	public void userPrintPackingListPdf(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			
		
		String printType = request.getParameter("formType");
		String[] targetTmp = request.getParameter("targetInfo").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		usrService.printPackingListPdf(request, response, orderNnoList, printType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cstmr/stock/checkWarehousing", method = RequestMethod.GET)
	public String userCheckWarehousing(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "user/checkWarehousing";
	}
	
	@RequestMapping(value = "/cstmr/stock/checkTrackingNo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> checkTrackingNoApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String apiKey = "56AB8817E3E4F6AED22AD8AB61A3BD68";
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		apiHeader.put("apikey", apiKey);
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		try {

			String whinYn = "N";
			
			if (request.getParameter("trackNo") == null) {
				rst.put("Status", "Fail");
				rst.put("StatusCode", "F2");
				rst.put("StatusMsg", "Tracking Number is not exists");
				rst.put("Data", null);
			}
			
			String trackingNo = request.getParameter("trackNo");
			String url = "https://aciinstrument.com/api/?actWork=TrackingIn&trackingNo="+trackingNo;
			
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendGet(url, apiHeader);
			
			if (!responseStr.equals("")) {
				JSONObject jsonObject = new JSONObject(responseStr);
				
				if (jsonObject.optString("rstCode").equals("S10")) {
					whinYn = "Y";
					
					data.put("whinYn", whinYn);
					data.put("whinDate", jsonObject.optString("receivingDate"));
					data.put("wta", jsonObject.optString("wta"));
					data.put("width", jsonObject.optString("width"));
					data.put("length", jsonObject.optString("length"));
					data.put("height", jsonObject.optString("height"));
					data.put("trackNo", jsonObject.optString("trackingNo"));
					
					rst.put("Status", "Success");
					rst.put("StatusCode", "S1");
					rst.put("Data", data);
					
				} else {
					data.put("whinYn", whinYn);
					data.put("whinDate", "");
					data.put("wta", "");
					data.put("width", "");
					data.put("length", "");
					data.put("height", "");
					data.put("trackNo", "");
					
					rst.put("Status", "Fail");
					rst.put("StatusCode", "F1");
					rst.put("Data", data);
				}
				
			} else {
				rst.put("Status", "Fail");
				rst.put("StatusCode", "F0");
				rst.put("Data", data);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			rst.put("Status", "Fail");
			rst.put("StatusCode", "F0");
			rst.put("Data", data);
		}
		
		return rst;
	}
	
	
	@RequestMapping(value = "/cstmr/apply/shpngList", method = RequestMethod.GET)
	public String cstmrShpngList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("USER_ID");

		String fromDate = "";
		String toDate = "";
		String hawbNo = "";
		String orderNo = "";

		if (request.getParameter("fromDate") != null) {
			fromDate = request.getParameter("fromDate");
		}
		
		if (request.getParameter("toDate") != null) {
			toDate = request.getParameter("toDate");
		}
		
		if (request.getParameter("hawbNo") != null) {
			hawbNo = request.getParameter("hawbNo");
		}
		
		if (request.getParameter("orderNo") != null) {
			orderNo = request.getParameter("orderNo");
		}
		
		parameters.put("userId", userId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		parameters.put("hawbNo", hawbNo);
		parameters.put("orderNo", orderNo);
		parameters.put("orderType", "NOMAL");
		
		int curPage = 1;
		int totalCount = usrService.selectAllOrderListCount(parameters);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 100);
		parameters.put("paging", paging);
		
		ArrayList<SendVO> list = new ArrayList<SendVO>();
		list = usrService.selectAllOrderList(parameters);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("parameters", parameters);
		
		return "user/apply/registList/shpng/shpngList";
	}
	
	@RequestMapping(value = "/cstmr/apply/blPrintExcelDownCheck", method = RequestMethod.GET)
	public ResponseEntity<byte[]> blPrintExcelDownCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/cstmr/apply/blPrintExcelDownByDate", method = RequestMethod.GET)
	public ResponseEntity<byte[]> blPrintExcelDownByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println(request.getParameter("orderType"));
			System.out.println(request.getParameter("urlType"));
			System.out.println(request.getParameter("startDate"));
			System.out.println(request.getParameter("endDate"));
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/cstmr/apply/shpngListExcelDown", method = RequestMethod.GET)
	public ResponseEntity<byte[]> shpngListExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("USER_ID");

		parameters.put("userId", userId);
		parameters.put("orderType", "NOMAL");
		parameters.put("fromDate", request.getParameter("fromDate"));
		parameters.put("toDate", request.getParameter("toDate"));
		parameters.put("orderNo", request.getParameter("orderNo"));
		parameters.put("hawbNo", request.getParameter("hawbNo"));
		
		ArrayList<SendVO> list = new ArrayList<SendVO>();
		list = usrService.selectAllExcelOrderList(parameters);
	
		
		String fileName = "shipping_list";

		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/send/excelSample/sendListExcel.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		
		Row row = null;
		Cell cell = null;
		
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);
		
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9);
		font.setFontName("맑은 고딕");
		
		CellStyle leftStyle = wb.createCellStyle();
		leftStyle.setFont(font);
		leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		
		CellStyle rightStyle = wb.createCellStyle();
		rightStyle.setFont(font);
		rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		
		int rowNo = 1;
		String agencyBl = "";

		for (int i = 0; i < list.size(); i++) {
			list.get(i).dncryptData();
			
			row = sheet.createRow(rowNo);
			
			if (list.get(i).getSubNo() == 1) {
				cell = row.createCell(0);
				cell.setCellValue(list.get(i).getOrderNo());
				cell.setCellStyle(leftStyle);

				cell = row.createCell(1);
				cell.setCellValue(list.get(i).getOrgNationName());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(list.get(i).getDstnNationName());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(3);
				if ("ICSKR2023".equals(userId.toUpperCase())) {
					cell.setCellValue(list.get(i).getMatchNo());	
				} else {
					cell.setCellValue(list.get(i).getHawbNo());
				}
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(list.get(i).getValueMatchNo());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(list.get(i).getCneeName());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(list.get(i).getCneeZip());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(list.get(i).getCneeTel());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(list.get(i).getCneeState());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(9);
				cell.setCellValue(list.get(i).getCneeCity());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(list.get(i).getCneeAddr());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(11);
				cell.setCellValue(list.get(i).getCneeAddrDetail());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(list.get(i).getWta());
				cell.setCellStyle(rightStyle);
				
				cell = row.createCell(13);
				cell.setCellValue(list.get(i).getWtc());
				cell.setCellStyle(rightStyle);
				
			}
			
			cell = row.createCell(14);
			cell.setCellValue(list.get(i).getItemDetail());
			cell.setCellStyle(leftStyle);
			
			cell = row.createCell(15);
			cell.setCellValue(list.get(i).getUnitValue());
			cell.setCellStyle(rightStyle);
			
			cell = row.createCell(16);
			cell.setCellValue(list.get(i).getItemCnt());
			cell.setCellStyle(rightStyle);
			
			
			cell = row.createCell(17);
			cell.setCellValue(list.get(i).getItemValue());
			cell.setCellStyle(rightStyle);
			
			rowNo++;
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		wb.write(outputStream);
		byte[] excelBytes = outputStream.toByteArray();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("filename", "shipping_list.xlsx");
        headers.setContentLength(excelBytes.length);

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}
