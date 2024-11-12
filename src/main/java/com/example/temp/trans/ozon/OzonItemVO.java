package com.example.temp.trans.ozon;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class OzonItemVO extends ApiOrderItemListVO{
	public OzonItemVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
