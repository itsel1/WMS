package com.example.temp.api.logistics.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.Tracking;

@Service
public interface LogisticsService {

	public HashMap<String, Object> registLogisticsOrder(HttpServletRequest request);
	
	public Order selectTempList(HashMap<String, Object> sqlParams);

	public ArrayList<Order> selectTempListAll(HashMap<String, Object> sqlParams);
	
	public ProcedureRst registShipment(Order order);

	public void storeToS3(Order order);
	
	public void processCJRegBook();
	
	public ArrayList<Tracking> getAciTrackingList(HashMap<String, Object> sqlParams);
	
	public Order selectTbOrderList(HashMap<String, Object> sqlParams);
}
