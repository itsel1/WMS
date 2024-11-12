package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpLicenceVO {
	public ExpLicenceVO() {
		nno="";
		orderNo="";
		expBusinessNum="";
		expShipperCode="";
		expShipperZip="";
		expShipperAddr="";
		expBusinessName="";
		expRegNo="";
		expNo="";
		simpleYn="";
		sendYn = "";
		expValue = "";
		agencyBusinessName = "";
	}
	
	protected String nno;
	protected String expRegNo;
	protected String expNo;
	protected String orderNo;
	protected String expBusinessNum;
	protected String expShipperCode;
	protected String expShipperZip;
	protected String expShipperAddr;
	protected String expBusinessName;
    protected String simpleYn;
    protected String sendYn;
    protected String expValue;
    protected String agencyBusinessName;
}
