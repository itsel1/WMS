package com.example.temp.api.shipment.company;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Service
public class GPS {
	
	@Autowired
	ShippingMapper shipMapper;

	private static long getUnixTimestamp() {
		
		Date date = new Date();
		long unixTime = date.getTime() / 1000L;
		
		return unixTime;
	}
	
	
	public Object createRequestBody(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");
		LinkedHashMap<String, Object> requestBody = new LinkedHashMap<>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		UserOrderListVO orderInfo = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		parameterInfo.put("nno", nno);
		orderInfo = shipMapper.selectTmpOrderListInfo(parameterInfo);
		itemInfoList = shipMapper.selectTmpOrderItemInfo(parameterInfo);
		orderInfo.dncryptData();
		
		
		int clearanceType = 0;
		String payment = orderInfo.getPayment().toUpperCase();
		String dimUnit = orderInfo.getDimUnit().toUpperCase();
		
		String dimensionUnit = "";
		
		if (dimUnit.equals("CM")) {
			dimensionUnit = "cm";
		} else if (dimUnit.equals("IN")) {
			dimensionUnit = "inch";
		}
		
		requestBody.put("syncGetLabel", true);
		requestBody.put("referenceId", orderInfo.getNno());
		requestBody.put("referenceId2", orderInfo.getOrderNo());
		requestBody.put("referenceId3", "");
		requestBody.put("trackingNo", "");
		requestBody.put("entryPoint", "");
		requestBody.put("serviceCode", "");
		requestBody.put("clearanceType", clearanceType);
		requestBody.put("dutyType", payment);
		requestBody.put("dimensionUnit", dimensionUnit);
		requestBody.put("weight", orderInfo.getWta());

		
		
		return requestBody;
	}
	
	
	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO shipment = new ShipmentVO();
		
		return shipment;
	}
	


}
