package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpLicenceListVO {
	public ExpLicenceListVO () {
		idx="";
		mawbNo="";
		legacyMawbNo="";
		depDate="";
		depTime="";
		arrDate="";
		arrTime="";
		fltNo="";
		dstnStation="";
		orgStation="";
		hawbCnt="";
		wUserId="";
		wUserIp="";
		transCode = "";
		transName = "";
	}
	private String idx;
	private String mawbNo;
	private String legacyMawbNo;
	private String depDate;
	private String depTime;
	private String arrDate;
	private String arrTime;
	private String fltNo;
	private String dstnStation;
	private String orgStation;
	private String hawbCnt;
	private String sendYn;
	private String licenceCnt;
	private String licenceHawbCnt;
	private String wUserId;
	private String wUserIp;
	private String transCode;
	private String transName;
}
