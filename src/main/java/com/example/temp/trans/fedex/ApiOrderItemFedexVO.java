package com.example.temp.trans.fedex;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class ApiOrderItemFedexVO extends ApiOrderItemListVO{
	public ApiOrderItemFedexVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
