package com.example.temp.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerAdminService;
import com.example.temp.manager.vo.AdminVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.YslExcelVO;
import com.example.temp.trans.fastbox.FastboxAPI;

import ch.qos.logback.core.net.SyslogOutputStream;
import oracle.net.aso.p;

@Controller
public class ManagerAdminController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ManagerAdminService admService;
	
	@Autowired
	FastboxAPI fbApi;
	
	@RequestMapping(value = "/mngr/acnt/adminList", method = RequestMethod.GET)
	public String managerAcntAdminList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int curPage = 1;
		String userId = request.getParameter("userId");
		 
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		
		int totalCount = admService.selectAdminListCnt(params);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount,5,50);
				
		params.put("paging", paging);
		ArrayList<AdminVO> userList = admService.selectAdminList(params);
		
		model.addAttribute("params", params);
		model.addAttribute("userList", userList);
		model.addAttribute("paging", paging);
			
		return "adm/member/adminList";
	}

	// 관리자 등록 
	@RequestMapping(value="/mngr/acnt/registAdmin", method=RequestMethod.GET)
	public String registAdminUser(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "adm/member/registAdminInfo";
	}
	
	@RequestMapping(value="/mngr/acnt/registAdmin", method=RequestMethod.POST)
	@ResponseBody
	public String registAdminUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, AdminVO adminVO, String nationCodes) throws Exception {
		String queryResult = "";
		
		try {
			if (!adminVO.getAdminPw().isEmpty()) {
				adminVO.setAdminPw(adminVO.encryptSHA256(adminVO.getAdminPw()));
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
				Date time = new Date();
				String time1 = format1.format(time);
				MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
				adminVO.setWUserId(member.getUsername());
				adminVO.setWUserIp(request.getRemoteAddr());
				adminVO.setWDate(time1);
				adminVO.setRole("ADMIN");
				adminVO.setOrgStation(orgStation);
				admService.insertAdminInfos(adminVO);
				queryResult = "S";
			} else {
				queryResult = "F";
			}
		} catch (Exception e) {
			queryResult = "F";
		}
		return queryResult;
		
	}
	
	// 관리자 등록 시 아이디 중복여부 체크
	@RequestMapping(value = "/mngr/acnt/idCntCheck", method = RequestMethod.POST)
	@ResponseBody
	public String adminIdCntCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = admService.selectUserCnt(request.getParameter("adminId"));
		if(cnt > 0) {
			return "F";
		}else {
			return "S";
		}
	}
	
	
	// 관리자 삭제
	@RequestMapping(value = "/mngr/acnt/deleteAdmin", method = RequestMethod.GET)
	public String adminDeleteUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, AdminVO adminVO, String targetParm) throws Exception {
		
		String Result = "";
		
		admService.deleteUserInfo(targetParm);
		Result = "redirect:/mngr/acnt/adminList";
		
		return Result;
	}
	
	// 관리자 정보 수정 페이지
	@RequestMapping(value = "/mngr/acnt/modiInfo/{adminId}", method = RequestMethod.GET)
	public String modifyAdminInfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("adminId") String adminId) throws Exception {
		AdminVO adminVO = admService.selectAdminInfo(adminId);
		
		model.addAttribute("adminId", adminId);
		model.addAttribute("adminVO", adminVO);
		
		return "adm/member/modifyAdmin";
	}
	
	@RequestMapping(value = "/mngr/acnt/modiInfo/{adminId}", method = RequestMethod.PATCH)
	@ResponseBody
	public String modifyAdminUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("adminId") String adminId, 
			Model model, AdminVO adminVO, String password) throws Exception {
		String queryResult = "";
		
		try {
			adminVO.setAdminPw(adminVO.encryptSHA256(adminVO.getAdminPw()));
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
			Date time = new Date();
			String time1 = format1.format(time);
			MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			adminVO.setWUserId(member.getUsername());
			adminVO.setWUserIp(request.getRemoteAddr());
			adminVO.setWDate(time1);
			admService.updateAdminInfo(adminVO, request);
			queryResult = "S";
		} catch (Exception e) {
			queryResult = "F";
			logger.error("Exception", e);
		}
		
		return queryResult;
	}
	
	// 비밀번호 초기화
	@RequestMapping(value = "/mngr/acnt/admin/resetPw/{adminId}", method = RequestMethod.POST)
	@ResponseBody
	public String adminPasswordReset(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("adminId") String adminId, AdminVO adminVO) throws Exception {
		String queryResult = "";
		
		try {
			String defaultPw = "me2uKK!";
			adminVO.setAdminPw(adminVO.encryptSHA256(defaultPw));
			admService.resetAdminPw(adminVO);
			queryResult = "S";
		} catch (Exception e) {
			queryResult = "F";
		}
		
		return queryResult;
	}
	
	
	@RequestMapping(value = "/mngr/acnt/fastboxUserList", method = RequestMethod.GET)
	public String managerFastboxSellerList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<FastboxUserInfoVO> userList = new ArrayList<FastboxUserInfoVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		if (request.getParameter("userId") != null) {
			params.put("userId", request.getParameter("userId"));
		}
		
		int curPage = 1;
		int totalCnt = admService.selectFastboxUserListCnt(params);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page")); 
		}
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		params.put("paging", paging);
		
		userList = admService.selectFastboxUserList(params);
		
		model.addAttribute("params", params);
		model.addAttribute("paging", paging);
		model.addAttribute("userList", userList);
		
		return "adm/member/fbUserList";
	}
	
	@RequestMapping(value = "/mngr/acnt/registFbUserInfo", method = RequestMethod.GET)
	public String managerFastboxRegistInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		ArrayList<HashMap<String, Object>> userList = new ArrayList<HashMap<String,Object>>();
		userList = admService.selectUserList();
		
		
		model.addAttribute("userList", userList);
		return "adm/member/fbRegistInfo";
	}
	
	@RequestMapping(value = "/mngr/acnt/getSelectUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerSelectUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		FastboxUserInfoVO userInfo = new FastboxUserInfoVO();
		String userId = request.getParameter("userId");
		
		userInfo = admService.selectUserInfo(userId);
		userInfo.dncryptData();
		
		result.put("userInfo", userInfo);
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/acnt/registFastboxUserInfo", method = RequestMethod.POST)
	public void managerFastboxRegistInfo(HttpServletRequest request, HttpServletResponse response, FastboxUserInfoVO userInfo) throws Exception {
		String wUserId = (String) request.getSession().getAttribute("USER_ID");
		String wUserIp = request.getRemoteAddr();
		userInfo.setWUserId(wUserId);
		userInfo.setWUserIp(wUserIp);
		userInfo.encryptData();
		admService.insertFastboxUserInfo(userInfo);
	}
	
	@RequestMapping(value = "/mngr/acnt/registFbUserModify", method = RequestMethod.GET)
	public String managerFastboxUserModify(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userId = request.getParameter("userId");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", idx);
		params.put("userId", userId);
		
		FastboxUserInfoVO userInfo = new FastboxUserInfoVO();
		userInfo = admService.selectFastboxUserInfo(params);
		userInfo.dncryptData();
		
		model.addAttribute("idx", idx);
		model.addAttribute("userInfo", userInfo);
		
		return "adm/member/fbUserModify";
	}
	
	@RequestMapping(value = "/mngr/acnt/modifyFastboxUserInfo", method = RequestMethod.POST)
	public void managerFastboxModifyInfo(HttpServletRequest request, HttpServletResponse response, FastboxUserInfoVO userInfo) throws Exception {
		int idx = Integer.parseInt(request.getParameter("idx"));
		String wUserId = (String) request.getSession().getAttribute("USER_ID");
		String wUserIp = request.getRemoteAddr();
		userInfo.setIdx(idx);
		userInfo.setWUserId(wUserId);
		userInfo.setWUserIp(wUserIp);
		userInfo.encryptData();
		
		admService.updateFastboxUserInfo(userInfo);
	}
	
	@RequestMapping(value = "/mngr/acnt/fastboxUserSend", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<HashMap<String, Object>> managerFastboxUserSend(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("userList[]") ArrayList<String> userList) throws Exception {
		ArrayList<HashMap<String, Object>> proc = new ArrayList<HashMap<String, Object>>();
		
		proc = fbApi.createSeller(request, response, userList);
		
		return proc;
	}
	
	@RequestMapping(value = "/mngr/testGrid", method = RequestMethod.GET)
	public String mngrTestGrid(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "test3";
	}
}
