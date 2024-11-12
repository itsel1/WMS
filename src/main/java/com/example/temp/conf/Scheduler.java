package com.example.temp.conf;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.temp.api.CommonVariables;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.api.logistics.service.FastboxHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.YongsungHandler;

@Controller
@Repository
@Service
public class Scheduler {
	
	@Value("${schedulerStatus}")
	String schedulerStatus;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	FastboxHandler fbHandler;
	
	@Autowired
	YongsungHandler ysHandler;
	
	
	@Scheduled(cron = "0 0/30 10-18 * * *", zone = "Asia/Seoul")
	@RequestMapping(value = "/api/logistics/sendCjData", method = RequestMethod.GET)
	public void sendCJRegistBookingData() {
		if ("on".equals(schedulerStatus)) {
			logisticsService.processCJRegBook();
		}
	}
	
	
	@Scheduled(cron = "0 15 18 * * *", zone = "Asia/Seoul")
	@RequestMapping(value = "/api/logistics/fb/getWeight", method = RequestMethod.GET)	// 로컬에서 실행하지 말 것
	public void updateFastboxWeight() {
		if ("on".equals(schedulerStatus)) {
			//fbHandler.getFastboxWeight();	
		}
	}
	
	
	@Scheduled(cron = "0 45 18 * * *", zone = "Asia/Seoul")
	@RequestMapping(value = "/api/logistics/ys/getWeight", method = RequestMethod.GET)
	public void updateYongsungWeight() {
		if ("on".equals(schedulerStatus)) {
			
		}
	}
	
	
	

}
