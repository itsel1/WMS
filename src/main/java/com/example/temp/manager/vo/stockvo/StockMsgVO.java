package com.example.temp.manager.vo.stockvo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockMsgVO{
	public StockMsgVO() {
		orgStation ="";
		subNo="";
		msgDiv="";
		whMemo="";
		userId="";
		adminYn="";
		wDate="";
		groupIdx="";
		rackCode = "";
		wUserId="";
		wUserIp ="";
		nno = "";
		readYn="";
		mDate="";
		adminMemo="";
		adminWDate="";
		idx=0;
	}
	//stock
	private int idx;
	private String orgStation;
	private String subNo;
	private String msgDiv;
	private String whMemo;
	private String userId;
	private String adminYn;
	private String wDate;
	private String groupIdx;
	private String rackCode;
	private String wUserId;
	private String wUserIp;
	private String readYn;
	private String nno;
	private String mDate;
	private String adminMemo;
	private String adminWDate;
	
	public String getDate() {
		return this.getWDate();
	}

}