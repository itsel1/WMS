package com.example.temp.api.ecommerce.dto;

import lombok.Data;

@Data
public class Item {
	
	private String nno;
	private String orgStation;
	private String userId;
	private String hsCode;
	private String itemDetail;
	private String unitCurrency;
	private String brand;
	private String makeCntry;
	private String makeCom;
	private String itemDiv;
	private String wtUnit;
	private String qtyUnit;
	private String packageUnit;
	private String chgCurrency;
	private String itemMeterial;
	private String takeInCode;
	private String itemUrl;
	private String itemImgUrl;
	private String status;
	private String trkCom;
	private String trkNo;
	private String trkDate;
	private String nativeItemDetail;
	private String cusItemCode;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String dimUnit;
	private String itemColor;
	private String itemSize;
	private double exchangeRate;
	private double unitValue;
	private double chgAmt;
	private double userWta;
	private double userWtc;
	private int subNo;
	private int itemCnt;
	
	public Item() {
		
		nno = "";
		orgStation = "";
		userId = "";
		hsCode = "";
		itemDetail = "";
		unitCurrency = "";
		brand = "";
		makeCntry = "";
		makeCom = "";
		itemDiv = "";
		wtUnit = "";
		qtyUnit = "";
		packageUnit = "";
		chgCurrency = "";
		itemMeterial = "";
		takeInCode = "";
		itemUrl = "";
		itemImgUrl = "";
		status = "";
		trkCom = "";
		trkNo = "";
		trkDate = "";
		nativeItemDetail = "";
		cusItemCode = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		dimUnit = "";
		itemColor = "";
		itemSize = "";
		exchangeRate = 0;
		unitValue = 0;
		chgAmt = 0;
		userWta = 0;
		userWtc = 0;
		subNo = 0;
		itemCnt = 1;
		
	}
}
