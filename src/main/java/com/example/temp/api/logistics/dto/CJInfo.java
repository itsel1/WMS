package com.example.temp.api.logistics.dto;

import lombok.Data;

@Data
public class CJInfo {
	
	private String custId;
	private String bizNo;
	private String tokenNo;
	private String tokenEDate;
	private String isTokenExpired;
	private String wDate;
	private String tokenWDate;
	
	public CJInfo() {
		custId = "";
		bizNo = "";
		tokenNo = "";
		tokenEDate = "";
		isTokenExpired = "";
		wDate = "";
		tokenWDate = "";
	}
}
