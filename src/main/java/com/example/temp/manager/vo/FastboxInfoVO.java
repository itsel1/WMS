package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FastboxInfoVO {
	
	public FastboxInfoVO() {
		userId="";
		mallId="";
		sellerName="";
		wDate="";
		wUserId="";
		wUserIp="";
		consumerKey="";
		token="";
		comRegNo="";
	}
	
	private String userId;
	private String mallId;
	private String sellerName;
	private String consumerKey;
	private String token;
	private String wDate;
	private String wUserId;
	private String wUserIp;
	private String comRegNo;
}
