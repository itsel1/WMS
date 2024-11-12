package com.example.temp.api;

import lombok.Data;

@Data
public class Tracking {
	
	private String hawbNo;
	private String trkNo;
	private String description;
	private String dateTime;
	private String location;
	private String statusCode;
	private String orderNo;
	private String cneeName;
	private String transCode;
	private String descriptionKor;
	private String locationKor;
	private String matchNo;
	
	public Tracking() {
		hawbNo = "";
		trkNo = "";
		description = "";
		dateTime = "";
		location = "";
		statusCode = "";
		orderNo = "";
		cneeName = "";
		transCode = "";
		descriptionKor = "";
		locationKor = "";
		matchNo = "";
	}
}
