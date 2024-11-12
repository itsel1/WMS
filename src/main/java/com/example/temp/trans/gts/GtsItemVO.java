package com.example.temp.trans.gts;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class GtsItemVO extends ApiOrderItemListVO{
	public GtsItemVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
