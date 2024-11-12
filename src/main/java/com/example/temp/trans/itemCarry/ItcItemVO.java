package com.example.temp.trans.itemCarry;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItcItemVO {
	public ItcItemVO() {
		nno = "";
		subNo = "";
		itemUrl = "";
		brand = "";
		hsCode = "";
		itemDetail = "";
		makeCntry = "";
		chgCurrency = "";
		unitValue = "";
		itemCnt = "";
		itemMeterial = "";
		cusItemCode = "";
	}
	
	private String nno;
	private String subNo;
	private String itemUrl;
	private String brand;
	private String hsCode;
	private String itemDetail;
	private String makeCntry;
	private String chgCurrency;
	private String unitValue;
	private String itemCnt;
	private String itemMeterial;
	private String cusItemCode;
}
