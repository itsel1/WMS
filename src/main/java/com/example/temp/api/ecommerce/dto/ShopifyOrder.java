package com.example.temp.api.ecommerce.dto;

import lombok.Data;

@Data
public class ShopifyOrder {

	private String nno;
	private String userId;
	private String orderNo;
	private String orderId;
	private String hawbNo;
	private String transName;
	private String trkNo;
	private String fulfillOrderId;
	private String fulfillmentId;
	
	public ShopifyOrder() {
		nno = "";
		userId = "";
		orderNo = "";
		orderId = "";
		hawbNo = "";
		transName = "";
		trkNo = "";
		fulfillOrderId = "";
		fulfillmentId = "";
	}
}
