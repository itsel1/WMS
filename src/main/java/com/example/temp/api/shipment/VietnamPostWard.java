package com.example.temp.api.shipment;

public class VietnamPostWard {

	private String ward;
	private String wardEng;
	private String code;
	
	public VietnamPostWard(String ward, String wardEng, String code) {
		this.ward = ward;
		this.wardEng = wardEng;
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getWard() {
		return ward;
	}
	
	public String getWardEng() {
		return wardEng;
	}
}
