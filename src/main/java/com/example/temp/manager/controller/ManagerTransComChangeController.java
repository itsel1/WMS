package com.example.temp.manager.controller;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.manager.service.ManagerTransComChangeService;


@Controller
public class ManagerTransComChangeController {
	
	@Autowired
	ManagerTransComChangeService mgrTransComService;

	@RequestMapping(value = "/comn/transComChange/userTransComChange", method = RequestMethod.GET)
	public String userTransComChange(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("userId",request.getParameter("userId"));
		parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameterInfo.put("dstnNation", request.getParameter("dstnNation"));

		ArrayList<HashMap<String, Object>> rst = new ArrayList<HashMap<String, Object>>();
		rst =  mgrTransComService.selectTransComChg(parameterInfo);
		
		
		
		
		String changeWtType =(String)rst.get(0).get("changeWtType");
		
		if(changeWtType.equals("")) {
			changeWtType = "WTA";
		}

		parameterInfo.put("changeWtType",changeWtType);
		
		HashMap<String, Object> nationParameterInfo = new HashMap<String, Object>();
		nationParameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		ArrayList<HashMap<String, Object>> rstNation = new ArrayList<HashMap<String, Object>>();
		rstNation =  mgrTransComService.selectNation(nationParameterInfo);
		
		HashMap<String, Object> transParameterInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> rstTrans = new ArrayList<HashMap<String, Object>>();
		transParameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		transParameterInfo.put("dstnNation", request.getParameter("dstnNation"));
		rstTrans =  mgrTransComService.selectTrans(transParameterInfo);

		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("nationList", rstNation);
		model.addAttribute("transList", rstTrans);
		model.addAttribute("list", rst);
		
		return "adm/rls/transCom/popupUserTrarnsComChangeList";
	}
	
	@RequestMapping(value = "/comn/transComChange/userTransComDel", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userTransComDel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("idx",request.getParameter("idx"));
		parameterInfo.put("dUserId",(String)request.getSession().getAttribute("USER_ID"));
		parameterInfo.put("dUserIp",request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		Rst = mgrTransComService.deleteTransComChangeDel(parameterInfo);

		return Rst;
	}
	
	@RequestMapping(value = "/comn/transComChange/userTransComIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> userTransComIn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("orgStation",(String)request.getSession().getAttribute("orgStation"));
			parameterInfo.put("dstnNation",request.getParameter("dstnNation"));
			parameterInfo.put("transCode",request.getParameter("transCode"));
			parameterInfo.put("userId",request.getParameter("userId"));
			parameterInfo.put("changeWtType",request.getParameter("changeWtType"));
			parameterInfo.put("minWta",request.getParameter("minWta"));
			parameterInfo.put("maxWta",request.getParameter("maxWta"));
			parameterInfo.put("wtUnit",request.getParameter("wtUnit"));
			parameterInfo.put("wUserId",(String)request.getSession().getAttribute("USER_ID"));
			parameterInfo.put("wUserIp",request.getRemoteAddr());
		
			HashMap<String, Object> Rst = new HashMap<String, Object>();
			Rst = mgrTransComService.insertTransComChangeIn(parameterInfo);

		return Rst;
	}
	
	

}
