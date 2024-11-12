package com.example.temp.trans.yongsung;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YsApiHawbVO{
	public YsApiHawbVO() {
		nno="";
		transCode="";
		orderNo="";
		hawbNo="";
		expRegNo="";
		expLicencePCS="";
		expLicenceWeight="";
		expNo = "";
	}
	private String nno;
	private String transCode;
	private String orderNo;
	private String hawbNo;
	private String expLicencePCS;
	private String expLicenceWeight;
	private String expRegNo;
	private String expNo;
	
	
}
