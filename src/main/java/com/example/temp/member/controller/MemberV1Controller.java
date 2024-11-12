package com.example.temp.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.api.cafe24.service.Cafe24Service;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.smtp.SmtpService;

import net.sf.json.JSONArray;

@Controller
public class MemberV1Controller {
	
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
	
	@RequestMapping(value = "/cstmr/apply/v1/shpngAgncy", method = RequestMethod.GET)
	public String userShpngAgncyApplyV1(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<UserTransComVO> transComList = new ArrayList<UserTransComVO>();
		ArrayList<UserTransComVO> userDstnNation = usrService.selectDstnTrans((String)request.getSession().getAttribute("USER_ID"));
		model.addAttribute("userDstnNation",userDstnNation);
		String transCode = request.getParameter("transCode");
		model.addAttribute("transCode",transCode);
		
		
		return "user/apply/shpng/list/v1/shpngAgncy";
	}
	
	@RequestMapping(value = "/cstmr/apply/v1/shpngAgncyTable", method = RequestMethod.POST)
	@ResponseBody
	public Object userShpngAgncyApplyTableV1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dataOne = new HashMap<String, Object>();
		dataOne.put("email", "itsel2@aciexrpess.net");
		dataOne.put("name", "김보람");
		data.add(dataOne);
		dataOne = new HashMap<String, Object>();
		dataOne.put("email", "itsel4@aciexpress.net");
		dataOne.put("name", "김가빈");
		data.add(dataOne);
		
		map.put("data", data);
		
		return map;
	}

}
