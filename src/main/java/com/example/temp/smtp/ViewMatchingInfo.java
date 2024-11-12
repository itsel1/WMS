package com.example.temp.smtp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewMatchingInfo {
	public ViewMatchingInfo() {
		nno="";
		hawbNo="";
		userId="";
		transCode="";
	}
	
	private String nno;
	private String hawbNo;
	private String userId;
	private String transCode;
}
