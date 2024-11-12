package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiAdminVO {
	
	public ApiAdminVO() {
		orgStation = "";
		transName = "";
		apiKey = "";
		apiPassword = "";
		apiAccountNumber = "";
		apiMeterNumber = "";
		indicia = "";
		ancillary = "";
		hubId = "";
	}
	
	private String orgStation;
	private String transName;
	private String apiKey;
	private String apiPassword;
	private String apiAccountNumber;
	private String apiMeterNumber;
	private String indicia;
	private String ancillary;
	private String hubId;
}
