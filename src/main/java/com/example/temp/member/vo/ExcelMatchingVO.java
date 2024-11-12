package com.example.temp.member.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExcelMatchingVO {
	public ExcelMatchingVO() {
		column = "";
		columnNum = "";
		columnName = "";
		matchingName = "";
		orgStation = "";
		dstnNation = "";
		transCode = "";
		userId = "";
	}
	private String column;
	private String columnNum;
	private String columnName;
	private String matchingName;
	private String orgStation;
	private String dstnNation;
	private String transCode;
	private String userId;
}
