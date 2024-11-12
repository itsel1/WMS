package com.example.temp.manager.vo.insp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InspStockOutVO {
	private String nno;
	private String subNo;
	private String itemDetail;
	private String itemCnt;
	private String whoutCnt;
	
	private String rackCode;
	private String stockNo;
}
