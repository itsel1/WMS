package com.example.temp.trans.efs;

import com.example.temp.api.aci.vo.ApiOrderListVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiOrderEFSVO extends ApiOrderListVO{
	public ApiOrderEFSVO() {
		new ApiOrderListVO();
	    orderNo2 = "";
	    shippingType = "";
	    chgCurrency = "";
	    userData1 = "";
	    userData2 = "";
	    userData3 = "";
	    delvNo = "";
	    delvCom = "";
	    unitCurrency = "";
	}
	
	protected String orderNo2;
	protected String shippingType;
	protected String chgCurrency;
	protected String userData1;
	protected String userData2;
	protected String userData3;
	protected String delvNo;
	protected String delvCom;
	protected String unitCurrency;
	
	
	
	
}
