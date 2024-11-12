package com.example.temp.trans.cse;

import com.example.temp.api.aci.vo.ApiOrderListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class CseOrderVO extends ApiOrderListVO{
	public CseOrderVO() {
		new ApiOrderListVO();
		totalValue = "";
		itemCnt = "";
		currency = "";
		makeCntry = "";
		itemName = "";
	}
	
	private String totalValue;
	private String itemCnt;
	private String currency;
	private String makeCntry;
	private String itemName;
}
