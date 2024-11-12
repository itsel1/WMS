package com.example.temp.api.shipment;

public class VietnamPostProvince {

	private String province;
	private String code;
	
	public VietnamPostProvince(String province, String code) {
		this.province = province;
		this.code = code;
	}
	
	public String getProvince() {
		return province;
	}
	
	public String getCode() {
		return code;
	}
}
