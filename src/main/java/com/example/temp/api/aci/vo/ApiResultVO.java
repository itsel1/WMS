package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResultVO {
	
	public ApiResultVO() {
		orderNo="";
		status="";
		errorMsg="";
		errorCode="";
		errorJson = "";
	}
	
	private String orderNo;
	private String status;
	private String errorMsg;
	private String errorCode;
	private String errorJson;
}
