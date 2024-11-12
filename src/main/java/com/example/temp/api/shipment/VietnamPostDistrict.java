package com.example.temp.api.shipment;

public class VietnamPostDistrict {

	private String district;
	private String districtEng;
	private String code;
	
	public VietnamPostDistrict(String district, String districtEng, String code) {
		this.district = district;
		this.districtEng = districtEng;
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public String getDistrictEng() {
		return districtEng;
	}
	
}
