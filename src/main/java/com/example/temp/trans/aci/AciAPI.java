package com.example.temp.trans.aci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.common.service.ComnService;
import com.example.temp.security.SecurityKeyVO;

@Service
public class AciAPI {
	
	@Autowired
	AciMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public ArrayList<HashMap<String, Object>> makePodDetatailArray(String hawbNo, HttpServletRequest request) throws Exception{
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			String matchNum = comnService.selectMatchNumByHawb(hawbNo);
			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
			
			HashMap<String, Object> dataOne = new HashMap<String, Object>(); 
			dataOne = mapper.selectOrderDate(hawbNo);
			String RegistInDate = "";
			String orderDate = (String) dataOne.get("orderDate");
			String orderTime = (String) dataOne.get("orderTime");
			RegistInDate = orderDate+"T"+orderTime+"+09:00";
			
			HashMap<String, Object> dataOne2 = new HashMap<String, Object>(); 
			dataOne2 = mapper.selectHawbDate(hawbNo);
			String housIndate = "";
			if (dataOne2 != null) {
				String housDate = (String) dataOne2.get("hawbDate");
				String houstime = (String) dataOne2.get("hawbTime");
				housIndate = housDate+"T"+houstime+"+09:00";	
			}
			
			
			HashMap<String,String> mawbDate = new HashMap<String,String>();
			mawbDate = mapper.selectMawbDate(hawbNo);
			String shipOutDate = "";
			
			if (mawbDate != null) {
				String depDate = (String) mawbDate.get("depDate");
				String depTime = (String) mawbDate.get("depTime");
				shipOutDate = depDate+"T"+depTime+"+09:00";
			}
			
			
			if (!shipOutDate.equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","300");
				podDetatil.put("UpdateDateTime", shipOutDate);
				podDetatil.put("UpdateLocation", "ACI WORLDWIDE, South Korea");
				podDetatil.put("UpdateDescription", "Departed to Destination or transit country");
				podDetatailArray.add(podDetatil);
			}
			
			if (!housIndate.equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","200");
				podDetatil.put("UpdateDateTime", housIndate);
				podDetatil.put("UpdateLocation", "ACI WORLDWIDE, South Korea");
				podDetatil.put("UpdateDescription", "Finished warehousing");
				podDetatailArray.add(podDetatil);
			}

			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","100");
			podDetatil.put("UpdateDateTime", RegistInDate);
			podDetatil.put("UpdateLocation", "South Korea");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatailArray.add(podDetatil);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return podDetatailArray;
	}

}
