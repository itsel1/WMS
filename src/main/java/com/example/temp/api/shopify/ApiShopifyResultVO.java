package com.example.temp.api.shopify;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiShopifyResultVO {
	
	public ApiShopifyResultVO() {
		hawbNo="";
		status="";
		errorMsg="";
		statusList ="";
		shipperReference="";
		userId="";
	}
	
	private String hawbNo;
	private String status;
	private String errorMsg;
	private String statusList;
	private String shipperReference;
	private String treatporegipocd;
	private String exchgPoCd;
	private String prerecevprc;
	private String userId;
}
