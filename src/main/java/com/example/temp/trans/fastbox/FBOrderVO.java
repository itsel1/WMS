package com.example.temp.trans.fastbox;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FBOrderVO {

	public FBOrderVO() {
		nno="";
		hawbNo="";
		storeName="";
		orderNo="";
		dstnNation="";
		chgCurrency="";
		itemCnt=0;
		totalValue="";
	}
	private String nno;
	private String hawbNo;
	private String storeName;
	private String orderNo;
	private String dstnNation;
	private String chgCurrency;
	private int itemCnt;
	private String totalValue;
}
