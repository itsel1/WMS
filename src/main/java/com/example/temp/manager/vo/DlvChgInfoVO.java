package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DlvChgInfoVO extends DlvPriceVO{
	public DlvChgInfoVO() {
		orgStation="";
		userId="";
		volumeDiscount="";
		stockPickUpType="";
		stockPickUpBasic="";
		stockPickUpCharge="";
		wUserId="";
		wUserIp="";
		wDate="";
		dvlChgType="";
		dstnNation="";
		dlvCode="";
		actualDiscount="";
		surchargeType ="";
		fuelSurcharge="";
		caseFee="";
		clearanceFee="";
		currency="";
		exportDeclFee = "";
		surchargeType2="";
		surcharge="";
		fuelWtUnit="";
		surWtUnit="";
	}
	
	
	
	private String orgStation;
	private String userId;
	private String volumeDiscount;
	private String stockPickUpType;
	private String stockPickUpBasic;
	private String stockPickUpCharge;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String dvlChgType;
	private String dstnNation;
	private String dlvCode;
	private String actualDiscount;
	private String surchargeType;
	private String fuelSurcharge;
	private String caseFee;
	private String clearanceFee;
	private String currency;
	private String exportDeclFee;
	private String surchargeType2;
	private String surcharge;
	private String fuelWtUnit;
	private String surWtUnit;
}
