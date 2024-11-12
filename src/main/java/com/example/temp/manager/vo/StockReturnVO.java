package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockReturnVO{
	public StockReturnVO() {
		idx="";
		dstnStation="";
		orgStation="";
		userId="";
		rStatus="";
		stockNo="";
		rTrkCom="";
		rTrkNo="";
		chgAmt="";
		wUserId="";
		wUserIp="";
		wDate="";
	}
	//stock
	private String idx;
	private String dstnStation;
	private String orgStation;
	private String userId;
	private String rStatus;
	private String stockNo;
	private String rTrkCom;
	private String rTrkNo;
	private String chgAmt;
	private String wUserId;
	private String wUserIp;
	private String wDate;
}
