package com.example.temp.trans.shipStation;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class ShipStationItemVO extends ApiOrderItemListVO{
	public ShipStationItemVO() {
		new ApiOrderItemListVO();
		sku = "";
		
	}
	protected String sku;
}
