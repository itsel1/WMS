package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VolumeVO{
	public VolumeVO() {
		nno="";
		subNo="";
		orgStation="";
		userId="";
		width ="";
		height ="";
		length ="";	
		per ="";
		dimUnit ="";
		wUserId="";
		wUserIp="";
		wDate="";
	}
	//stock
	private String nno;
	private String subNo;
	private String orgStation;
	private String userId;
	private String width;
	private String height;
	private String length;	
	private String per;
	private String dimUnit;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String wtUnit;
}
