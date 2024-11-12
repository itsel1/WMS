package com.example.temp.conf;

public class TranslationVO {
	private String language;
	
	public TranslationVO(String language) {
		this.language = language;
	}
	
	public String getInformationText() {
		switch (language) {
		case "en":
			return "Information";
		case "ko":
			return "정보";
		default:
			return "Information";
		}
	}
	
	private String informationText;
	private String userId;
	private String password;
	private String passwordConfirm;
	private String comName;
	private String userName;
	private String userEmail;
	private String userTel;
	private String userHp;
	private String userAddr;
	private String userAddrDetail;
	private String comEName;
	private String userCountry;
	private String userEState;
	private String userECity;
	private String userEAddr;
	private String userEAddrDetail;
	private String invComName;
	private String invComTel;
	private String invUserTel;
	private String invUserEmail;
	private String invComAddr;
	private String invComAddrDetail;


}
