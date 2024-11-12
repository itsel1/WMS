package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOutVO {

	public StockOutVO() {


	}
	
	private String orgStation;
	private String stationName;
	private String userId;
	private String depMonth;
	private String depDate;
	private String depYear;
	
	private int cnt;
	private String fromDate;
	private String toDate;
}
