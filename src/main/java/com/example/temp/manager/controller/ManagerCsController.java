package com.example.temp.manager.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.temp.manager.service.ManagerCsBoardService;

@Controller
public class ManagerCsController {
	
	@Autowired
	ManagerCsBoardService service;

	@RequestMapping(value = "/mngr/cs/csInfoDetail", method = RequestMethod.GET)
	public String managerCsInfoDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = "202212261403235836JFI";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		
		HashMap<String, Object> csInfo = new HashMap<String, Object>();
		csInfo = service.selectCsInfoDetail(params);
		
		model.addAttribute("csInfo", csInfo);
		return "adm/cs/csInfoDetail";
	}
}