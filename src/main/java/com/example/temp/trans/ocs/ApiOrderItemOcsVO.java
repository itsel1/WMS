package com.example.temp.trans.ocs;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class ApiOrderItemOcsVO extends ApiOrderItemListVO{
	public ApiOrderItemOcsVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
