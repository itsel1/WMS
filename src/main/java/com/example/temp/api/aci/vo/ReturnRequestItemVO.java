package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequestItemVO {
	
	
	public ReturnRequestItemVO(){
		nno = "";
		subNo = 1;
		userId="";
		itemDetail = "";
		brand = "";
		itemWta = 0;
		wtUnit = "";
		itemCnt = 1;
		unitValue = 0;
		unitCurrency = "";
		makeCntry = "";
		makeCom = "";
		hsCode = "";
		itemUrl = "";
		itemImgUrl = "";
		orgStation="";
		wUserId="";
		wUserIp="";
		cusItemCode="";
		nativeItemDetail="";
		itemDetailEng="";
	}
	private String nno;
	private int subNo;
	private String userId;
	private String orgStation;
	private String itemDetail;
	private String brand;
	private double itemWta;
	private String wtUnit;
	private int itemCnt;
	private double unitValue;
	private String unitCurrency;
	private String makeCntry;
	private String makeCom;
	private String hsCode;
	private String itemUrl;
	private String itemImgUrl;
	private String wUserId;
	private String wUserIp;
	private String cusItemCode;
	private String nativeItemDetail;
	private String itemDetailEng;

}
