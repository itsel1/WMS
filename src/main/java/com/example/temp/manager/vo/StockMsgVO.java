package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockMsgVO{
	public StockMsgVO() {
		idx="";
		nno="";
		subNo="";
		msgDiv="";
		whMemo="";
		userId="";
		adminYn="";
		wDate="";
		groupIdx="";
		wUserId="";
		wUserIp="";
		status="";
	}
	//stock
	private String idx;
	private String nno;
	private String subNo;
	private String msgDiv;
	private String whMemo;
	private String userId;
	private String adminYn;
	private String wDate;
	private String date;
	private String groupIdx;
	private String wUserId;
	private String wUserIp;
	private String status;
	
	public String getDate() {
		return this.getWDate();
	}

}
