package com.example.temp.member.vo;

import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOrderItemVO {

	public UpdateOrderItemVO() {
		nno="";
		subNo="";
		hsCode="";
		itemDetail="";
		itemCnt="";
		unitValue="";
		brand="";
		makeCntry="";
		makeCom="";
		chgCurrency="";
		wUserId="";
		wUserIp="";
		wDate="";
		cusItemCode="";
	}
	
	private String nno;
	private String subNo;
	private String hsCode;
	private String itemDetail;
	private String itemCnt;
	private String unitValue;
	private String brand;
	private String makeCntry;
	private String makeCom;
	private String chgCurrency;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String cusItemCode;
	
}
