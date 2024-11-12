package com.example.temp.api.shipment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShipmentVO {

	private String rstStatus;
	private String rstCode;
	private String rstMsg;
	private String rstNno;
	private String rstHawbNo;
	private String rstTrkNo;
	private String rstTrkCom;
	
	public ShipmentVO() {
		rstStatus = "";
		rstCode = "";
		rstMsg = "";
		rstNno = "";
		rstHawbNo = "";
		rstTrkNo = "";
		rstTrkCom = "";
	}
}
