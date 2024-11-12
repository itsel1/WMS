package com.example.temp.api.logistics.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.api.logistics.service.CJHandler;
import com.example.temp.api.logistics.service.LogisticsService;

@Controller
public class LogisticsController {
	
	@Autowired
	CJHandler cjHandler;
	
	@Autowired
	LogisticsService service;
	
	@RequestMapping(value = "/api/logistics/createShipment", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> createShipment(HttpServletRequest request) {
		HashMap<String, Object> response = new HashMap<>();
		
		response = service.registLogisticsOrder(request);
		
		return response; 
	}
/*
	public void sendCJRegistBookingData() {
		service.processCJRegBook();
	}
	*/
}
