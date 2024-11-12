package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DlvPriceVO {
	public DlvPriceVO() {
		orgStation="";
		orgNation="";
		dstnNation="";
		zoneCode="";
		wt="";
		wtUnit="";
		price="";
		dlvPriceName="";
		dlvCode="";
		wUserId="";
		wUserIp="";
		currency="";
	}
	
	
	protected String wUserId;
	protected String wUserIp;
	protected String orgStation;
	protected String orgNation;
	protected String dstnNation;
	protected String zoneCode;
	protected String wt;
	protected String wtUnit;
	protected String price;
	protected String dlvPriceName;
	protected String dlvCode;
	protected String currency;
}
