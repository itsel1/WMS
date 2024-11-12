package com.example.temp.api.shipment;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ShippingController {
	
	@Autowired
	ShippingService shipService;

	@RequestMapping(value = "/api/shipment/createShipments", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> createShipments(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result = shipService.createShipment(request);
		return result;
	}

}
