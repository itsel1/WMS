package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TmpTakeinOrderItemVO {
	
	public TmpTakeinOrderItemVO(){
		nno ="";
		subNo  =""; 
		orgStation   ="";
		userId  ="";
		cusItemCode="";
		itemCnt="";
		unitValue ="";
		stockCnt = "";
		unitCurrency = "";
		nativeItemDetail = "";
		
		chgCurrency="";
		wUserId = "";
		wUserIp = "";
	}
	
	private String nno;
	private String subNo;
	private String orgStation;
	private String userId;
	private String cusItemCode;
	private String itemCnt;
	private String unitValue;
	private String stockCnt;
	private String unitCurrency;
	private String nativeItemDetail;

	private String chgCurrency;
	private String wUserId;
	private String wUserIp;
}
