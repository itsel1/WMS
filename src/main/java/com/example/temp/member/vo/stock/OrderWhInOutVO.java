package com.example.temp.member.vo.stock;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderWhInOutVO {
	
	public OrderWhInOutVO(){
		nno = "";
		orderDate = "";
		stationName = "";
		orderNo = "";
		hawbNo = "";
		cneeName = "";
		cusItemCode = "";
		itemDetail = "";
		minWhDate = "";
		maxWhDate = "";
		rqCnt = "";
		woCnt = "";
		wfCnt = "";
		rtCnt = "";
		trCnt = "";
		whCnt = "";
		whOutDate = "";
		supplierCode = "";
		outHawbNo = "";
	}

	private String nno;
	private String orderDate;
	private String stationName;
	private String orderNo;
	private String hawbNo;
	private String cneeName;
	private String cusItemCode;
	private String itemDetail;
	private String minWhDate;
	private String maxWhDate;
	private String itemCnt;
	private String rqCnt;
	private String woCnt;
	private String wfCnt;
	private String rtCnt;
	private String trCnt;
	private String whCnt;
	private String whOutDate;
	private String supplierCode;
	private String outHawbNo;
}
