package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24BuyerInfoVo {
	
	public Cafe24BuyerInfoVo() {
		 name = "";
		 email = "";
		 phone = "";
		 cellphone = ""; 
		 customerNotification = "";
		 
	}
	
	 private String name;
	 private String email;
	 private String phone;
	 private String cellphone;
	 private String customerNotification;

}
