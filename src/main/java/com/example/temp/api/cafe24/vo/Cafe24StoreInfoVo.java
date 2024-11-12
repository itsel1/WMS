package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24StoreInfoVo {
	
	public Cafe24StoreInfoVo() {
		userId = "";
		mallId = "";
		shopNo = "";
		companyName = "";
		country = "";
		email = "";
		companyRegistrationNo = "";
		phone = "";
		zipCode = "";
		address1 = "";
		address2 = "";
	}
	
	private String userId;
	private String mallId;
	private String shopNo;
	private String companyName;
	private String country;
	private String email;
	private String companyRegistrationNo;
	private String phone;
	private String zipCode;
	private String address1;
	private String address2;
	private String baseDomain;
	
	
 
}
