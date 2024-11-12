package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24ReceiverInfoVo {
	
	public Cafe24ReceiverInfoVo() {
		 name = "";
		 phone = "";
		 cellphone = ""; 
		 shippingMessage = "";
		 nameFurigana = "";
		 zipCode = "";
		 address1 = "";
		 address2 = "";
		 addressState = "";
		 addressCity = "";
		 countryCode = "";
		 clearanceInformationType = "";
		 clearanceInformation = "";
	}
	
	 private String name;
	 private String phone;
	 private String cellphone;
	 private String shippingMessage;
	 private String nameFurigana;
	 private String zipCode;
	 private String address1;
	 private String address2;
	 private String addressState;
	 private String addressCity;
	 private String countryCode;
	 private String clearanceInformationType;
	 private String clearanceInformation;
 
}
