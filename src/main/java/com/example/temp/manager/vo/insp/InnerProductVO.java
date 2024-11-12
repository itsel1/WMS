package com.example.temp.manager.vo.insp;

import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InnerProductVO{
	public InnerProductVO() {
		itemDetail = "";
		itemCnt = "";
		qtyUnit = "";
		wta = "";
		wtc = "";
		inDate = "";
		endDate = "";
		carryInNum = "";
	}
	
	private String carryInNum;
	private String itemDetail;
	private String itemCnt;
	private String qtyUnit;
	private String wta;
	private String wtc;
	private String inDate;
	private String endDate;
	
}
