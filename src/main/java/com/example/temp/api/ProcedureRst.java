package com.example.temp.api;

import lombok.Data;

@Data
public class ProcedureRst {
	
	private String rstStatus;
	private String rstCode;
	private String rstMsg;
	private String rstNno;
	private String rstHawbNo;
	private String rstLabelUrl;

	public ProcedureRst() {
		rstStatus = "";
		rstCode = "";
		rstMsg = "";
		rstNno = "";
		rstHawbNo = "";
		rstLabelUrl = "";
	}
}
