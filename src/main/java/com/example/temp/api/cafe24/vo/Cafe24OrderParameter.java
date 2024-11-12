package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24OrderParameter {
	
	public Cafe24OrderParameter() {
		userId = "";
		mallId = "";
		accessToken = "";
		shopNo = "";
		businessCountryCode = "";
		currencyCode = "";
		startDate = "";
		endDate = "";
		orderId = "";
		productNo = "";
		manufacturerCode = "";
		supplierCode = "";
		remoteAddr = "";
	}
	
	private String userId;
	private String mallId;
	private String accessToken;
	private String shopNo;
	private String businessCountryCode;
	private String currencyCode;
	private String startDate;
	private String endDate;
	private int offset;
	private int limit;
	private String orderId;
	private String productNo;
	private String manufacturerCode;
	private String supplierCode;
	private String wUserId;
	private String remoteAddr;
 
}
