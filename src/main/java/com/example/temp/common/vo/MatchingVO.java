package com.example.temp.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingVO {

	public MatchingVO() {
		keyHawbNo = "";
		valueMatchNo = "";
		valueHawbNo = "";
		valueOrderNo = "";
		matchTransCode = "";
		nno = "";
	}
	
	private String keyHawbNo;
	private String valueMatchNo;
	private String valueHawbNo;
	private String valueOrderNo;
	private String matchTransCode;
	private String nno;
}
