package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
public class InspcReturnVO {
	public InspcReturnVO() {
		nno = "";
		dstnNation = "";
		orgStation = "";
		sellerId = "";
		orderDate = "";
		hawbNo = "";
		inspcState = "";
		userId = "";
		
	}
	private String nno;
	private String dstnNation;
	private String orgStation;
	private String sellerId;
	private String orderDate;
	private String hawbNo;
	private String inspcState;
	private String userId;
}
