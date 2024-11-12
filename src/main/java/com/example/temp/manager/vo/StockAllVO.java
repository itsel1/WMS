package com.example.temp.manager.vo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockAllVO{
	public StockAllVO() {
		stockNo="";
		stockType="";
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
		groupIdx="";
		idx="";
		dstnStation="";
		fileDir="";
		uid="";
		whMemo="";
		adminYn="";
		wUserId="";
		wUserIp="";
		wDate="";
		whInCnt ="";
		fileIdx = "";
	}
	//stock
	private String stockNo;
	private String stockType;
	private String orgStation;
	private String userId;
	private String rackCode;
	private String nno;
	private String subNo;
	private String takeInCode;
	private String whInDate;
	private String whStatus;
	private String wta;
	private String wtc;
	private String wtUnit;
	private String outNno;
	private String trkCom;
	private String trkNo;
	private String groupIdx;
	private String idx;
	private String dstnStation;
	private String fileDir;
	private String uid;
	private String whMemo;
	private String adminYn;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String whInCnt;
	private String whStatusName;
	private String whCnt;
	private String whTotalCnt;
	private String width;
	private String height;
	private String length;
	private String per;
	private String dimUnit;
	private String fileIdx;
	private String addPassCnt;
	private String addFailCnt;
	private String addFileIdx;
	private String whDetailStatus;
	
	private ArrayList<String> fileDirList;
}
