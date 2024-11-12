package com.example.temp.trans.gts;

import com.example.temp.api.aci.vo.ApiOrderListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class GtsOrderVO extends ApiOrderListVO{
	public GtsOrderVO() {
		new ApiOrderListVO();
		totalValue = "";
		itemCnt = "";
		currency = "";
		makeCntry = "";
	}
	
	private String totalValue;
	private String itemCnt;
	private String currency;
	private String makeCntry;
}
