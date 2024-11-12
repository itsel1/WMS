package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsterumentVO {
	public InsterumentVO() {
		transCode = "";
		transName ="";
		wtUnit = "";
		orderNo = "";
		hawbNo = "";
		userWta = "";
		wtErrRange = "";
		wtErrRangeYn = "";
		mawbNo = "";
		boxCnt = "";
		userWtv= "";
		userWidth= "";
		userLength= "";
		userHeight= "";
	}
	private String transCode;
	private String transName;
	private String dstnNation;
	private String boxCnt;
	private String orderNo;
	private String hawbNo;
	private String userWta;
	private String userWtv;
	private String userWidth;
	private String userLength;
	private String userHeight;
	private String wtUnit;
	private String wtErrRange;
	private String wtErrRangeYn;
	private String mawbNo;
	
}
