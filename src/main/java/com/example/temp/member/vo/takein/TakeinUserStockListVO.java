package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinUserStockListVO {
	
	public TakeinUserStockListVO(){
		takeInCode = "";
		cusItemCode = "";
		orgStationName ="";
		itemDetail = "";
		itemOption = "";
		whInCnt = "";
		whOutCnt = "";
		groupIdx = "";
		orgStation = "";
		userId = "";
		whInDate = "";
		inSpector = "";
		useYn = "";
		cusInvNo = "";
		cusSupplier = "";
		cusSupplierAddr = "";
		cusSupplierTel = "";
		cusOutNo = "";
		remark = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		qtyUnit = "";
		mnDate = "";
	}
	
	private String takeInCode;
	private String cusItemCode;
	private String orgStationName;
	private String whCnt;
	private String hsCode;
	private String groupIdx;
	private String orgStation;
	private String userId;
	private String itemDetail;
	private String itemOption;
	private String whInDate;
	private String whInCnt;
	private String whOutCnt;
	private String inSpector;
	private String useYn;
	private String cusInvNo;
	private String cusSupplier;
	private String cusSupplierAddr;
	private String cusSupplierTel;
	private String cusOutNo;
	private String remark;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String qtyUnit;
	private String mnDate;
	
}
