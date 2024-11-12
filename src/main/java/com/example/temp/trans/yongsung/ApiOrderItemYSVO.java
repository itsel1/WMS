package com.example.temp.trans.yongsung;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiOrderItemYSVO extends ApiOrderItemListVO{
	public ApiOrderItemYSVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
