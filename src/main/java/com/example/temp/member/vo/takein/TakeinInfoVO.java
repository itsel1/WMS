package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinInfoVO {
	
	public TakeinInfoVO(){
		takeInCode = "";
		orgStation = "";
		stationName = "";
		userId = "";
		cusItemCode = "";
		hsCode = "";
		brand = "";
		itemDetail = "";
		nativeItemDetail = "";
		itemOption = "";
		unitValue = "";
		unitCurrency = "";
		wta = "";
		wtc = "";
		wtUnit = "";
		qtyUnit = "";
		itemUrl = "";
		itemImgUrl = "";
		itemMeterial = "";
		itemDiv = "";
		makeCntry = "";
		makeCom = "";
		itemColor = "";
		itemSize = "";
		wUserId = "";
		wUserIp = "";
		whInCnt = "";
		whOutCnt = "";
		wDate = "";
		useYn = "";
		appvYn = "";
	}

	private String takeInCode ;
	private String orgStation ;
	private String stationName;
	private String userId ;
	private String cusItemCode ;
	private String hsCode ;
	private String brand ;
	private String itemDetail ;
	private String nativeItemDetail ;
	private String itemOption ;
	private String unitValue ;
	private String unitCurrency ;
	private String wta ;
	private String wtc ;
	private String wtUnit ;
	private String qtyUnit ;
	private String itemUrl ;
	private String itemImgUrl ;
	private String itemMeterial ;
	private String itemDiv ;
	private String makeCntry ;
	private String makeCom ;
	private String itemColor;
	private String itemSize;
	private String whInCnt;
	private String whOutCnt;
	private String wUserId ;
	private String wUserIp ;
	private String wDate ;
	private String useYn ;
	private String appvYn;

}
