package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockFileVO{
	public StockFileVO() {
		idx = "";
		nno="";
		subNo="";
		orgStation="";
		dstnStation="";
		userId="";
		wUserId="";
		wUserIp="";
		wDate="";
	}
	//stock
	private String idx;
	private String nno;
	private String subNo;
	private String dstnStation;
	private String orgStation;
	private String userId;
	private String fileDir;
	private String wUserId;
	private String wUserIp;
	private String wDate;

}
