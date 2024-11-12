package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24UserInfoVo {
	
	public Cafe24UserInfoVo() {
		userId = "";
		cafe24Id = "";
		mallId = "";
		clientId = "";
		clientSecretKey = "";
	}
	
	private String userId;
	private String cafe24Id;
	private String mallId;
	private String clientId;
	private String clientSecretKey;
	
	 
} 
