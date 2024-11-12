package com.example.temp.order.customer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {
	
	
	@RequestMapping(value = "/user/order/regist", method = RequestMethod.GET)
	public String customerPreorderList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String filePath = "v1/user/order/regist";
		
		return filePath;
	}

}
