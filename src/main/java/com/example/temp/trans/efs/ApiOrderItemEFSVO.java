package com.example.temp.trans.efs;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiOrderItemEFSVO extends ApiOrderItemListVO{
	public ApiOrderItemEFSVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
