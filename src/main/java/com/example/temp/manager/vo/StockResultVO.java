package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockResultVO{
	public StockResultVO() {
		status="";
		nno="";
		subNo="";
		rstCode="";
		rstMsg="";
		groupIdx="";
		itemDetail="";
		brand="";
		cneeName="";
		rackCode="";
		stockNo="";
		stockType="";
	}
	private String status;
	private String nno;
	private String subNo;
	private String rstCode;
	private String rstMsg;
	private String groupIdx;
	private String itemDetail;
	private String brand;
	private String cneeName;
	private String rackCode;
	private String stockNo;
	private String stockType;
	private String rstIdx;
}
