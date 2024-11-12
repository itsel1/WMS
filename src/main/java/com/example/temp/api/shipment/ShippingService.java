package com.example.temp.api.shipment;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public interface ShippingService {

	public String createRequestBody(HashMap<String, Object> params);
	public HashMap<String, Object> createShipment(HttpServletRequest request);
	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal);
	public void createLabel(HashMap<String, Object> params);
	public ShipmentVO applyShipment(HashMap<String, Object> params);
	public void updateShopFulfillService(ArrayList<HashMap<String, Object>> collectOrderList);
	public void updateExportLicenseNumber(ArrayList<HashMap<String, Object>> collectOrderList);
}
