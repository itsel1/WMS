package com.example.temp.trans.shipStation;

import com.example.temp.api.aci.vo.ApiOrderListVO;

import lombok.Getter;
import lombok.Setter;
 
@Setter
@Getter
public class ShipStationOrderVO extends ApiOrderListVO{
	public ShipStationOrderVO() {
		new ApiOrderListVO();
		orderStatus = "";
		invCompany = "";
		
	}
	
	protected String orderStatus;
	protected String invCompany;
}
