package com.example.temp.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.temp.manager.service.ManagerExcelService;

@Controller
public class ManagerExcelController {

	@Autowired
	
	ManagerExcelService mgrExcelService;
	
	
	@RequestMapping(value = "/mngr/Excel/UediDown", method = RequestMethod.GET)
	public String managerMawbManiDown(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
			String rst = "";
			request.getParameter("mawbNo");
			rst = mgrExcelService.excelUediDown(request.getParameter("mawbNo"), response,  request);			
			return "/mngr/rls/mawbList";
	}
	
	
	@RequestMapping(value = "/mngr/Excel/rusExcelDown", method = RequestMethod.GET)
	public String managerRusExcelDown(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
			String rst = "";
			request.getParameter("mawbNo");
			rst = mgrExcelService.rusExcelDown(request.getParameter("mawbNo"), response,  request);
			return "/mngr/rls/mawbList";
	}
	
	
}
