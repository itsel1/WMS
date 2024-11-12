package com.example.temp.manager.vo;

import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HawbVO{
	public HawbVO() {
		nno = "";
		orderNo ="";
		dstnStation ="";
		orgStation ="";
		trkCom = "";
		hawbNo = "";
		boxCnt = "1";
		wta = 0;
		wtc = 0;
		wtUnit = "";
		comName = "";
		cneeName = "";
		userName = "";
		transName = "";
		transCode = "";
		ftpInDate = "";
		expRegNo = "";
		expNo = "";
		expValue = "";
		arrIn = "";
		bagNo = "";
		agencyBl = "";
		wUserId = "";
		wUserIp = "";
		fbSendYn = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String idx;
	private String nno;
	private String orderNo;
	private String userId;
	private String dstnStation;
	private String orgStation;
	private String trkCom;
	private String transCode;
	private String hawbNo;
	private String boxCnt;
	private float wta;
	private float wtc;
	private String wtUnit;
	private String mawbNo;
	private String wUserId;
	private String wUserIp;
	private String comName;
	private String cneeName;
	private String userName;
	private String transName;
	private String registCount;
	private String totalCount;
	private String readyCount;
	private String failCount;
	private String ftpInDate;
	private String expRegNo;
	private String expNo;
	private String expWDate;
	private String expValue;
	private String wDate;
	private String apiStatus;
	private float height;
	private float width;
	private float length;
	private String sendYn;
	private String arrIn;
	
	private String bagNo;
	private String agencyBl;
	private String fbSendYn;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		
	}
	
	public void dncryptData() {
	}
}
