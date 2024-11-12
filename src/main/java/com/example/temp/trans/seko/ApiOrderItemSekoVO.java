package com.example.temp.trans.seko;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class ApiOrderItemSekoVO extends ApiOrderItemListVO{
	public ApiOrderItemSekoVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
