package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderWeightVO {
	public OrderWeightVO() {
		orgStation="";
		hawbNo="";
		wta="";
		width="";
		height="";
		length="";
		per="";
		wtc="";
		dimUnit="";
		wtUnit="";
		wUserId="";
		wUserIp="";
		wDate="";
		errorYn = "";
		errorMsg = "";
		userId = "";
		nno = "";
	}
	
	private String orgStation;
	private String hawbNo;
	private String wta;
	private String width;
	private String height;
	private String length;
	private String per;
	private String wtc;
	private String dimUnit;
	private String wtUnit;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String errorYn;
	private String errorMsg;
	private String userId;
	private String nno;

	
}
