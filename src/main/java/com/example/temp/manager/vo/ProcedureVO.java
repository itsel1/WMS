package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcedureVO {
	public ProcedureVO(){
		rstStatus ="";
		rstCode ="";
		rstMsg ="";
		rstNno ="";
		rstHawbNo ="";
		rstMawbNo ="";
		outNno ="";
		rstTransCode ="";
		orgHawbNo ="";
	}
	private String rstStatus;
	private String rstCode;
	private String rstMsg;
	private String rstNno;
	private String rstHawbNo;
	private String rstMawbNo;
	private String outNno;
	private String rstTransCode;
	private String orgHawbNo;
}
