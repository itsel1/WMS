package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockVO{
	public StockVO() {
		stockNo="";
		stockType="";
		stockTyep="";
		orgStation="";
		userId="";
		rackCode="";
		nno="";
		subNo="";
		takeInCode="";
		whInDate="";
		whStatus="";
		wta="";
		wtc="";
		wtUnit="";
		outNno="";
		trkCom="";
		trkNo="";
		wUserId="";
		wUserIp="";
		wDate="";
		groupIdx="";
		hawbNo = "";
		itemDetail = "";
		pickType="";
	}
	//stock
	private String stockNo;
	private String stockType;
	private String stockTyep;
	private String orgStation;
	private String userId;
	private String rackCode;
	private String nno;
	private String subNo;
	private String takeInCode;
	private String whInDate;
	private String whStatus;
	private String whStatusName;
	private String wta;
	private String wtc;
	private String wtUnit;
	private String outNno;
	private String trkCom;
	private String trkNo;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String groupIdx;
	private String hawbNo;
	private String itemDetail;
	private String pickType;
	public String getStockType() {
		return this.getStockTyep();
	}

}
