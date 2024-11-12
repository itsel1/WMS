package com.example.temp.trans.itemCarry;

import com.example.temp.api.aci.vo.ApiOrderListVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItcOrderVO extends ApiOrderListVO{
	
	public ItcOrderVO() {
		nno = "";
		orderNo = "";
		shipperName = "";
		cneeName = "";
		shipperTel = "";
		shipperHp = "";
		zipCode = "";
		cneeAddr = "";
		cneeAddrDetail = "";
		wtUnit = "";
		userWta = "";
		userLength = "";
		userWidth = "";
		userHeight = "";
	}
	
	private String nno;
	private String orderNo;
	private String shipperName;
	private String cneeName;
	private String shipperTel;
	private String shipperHp;
	private String cneeTel;
	private String cneeHp;
	private String zipCode;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String wtUnit;
	private String userWta;
	private String userLength;
	private String userWidth;
	private String userHeight;
	
}
