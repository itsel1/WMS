package com.example.temp.api;

import lombok.Data;

@Data
public class CommonVariables {
	
	private String orgStation;
	private String nno;
	private String hawbNo;
	private String userId;
	private String matchNo;
	private String trkNo;
	private String transCode;
	private double wta;
	private double wtc;
	
	public CommonVariables() {
		orgStation = "";
		nno = "";
		hawbNo = "";
		userId = "";
		matchNo = "";
		trkNo = "";
		transCode = "";
		wta = 0;
		wtc = 0;
	}
}
