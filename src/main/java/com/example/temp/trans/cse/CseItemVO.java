package com.example.temp.trans.cse;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class CseItemVO extends ApiOrderItemListVO{
	public CseItemVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
