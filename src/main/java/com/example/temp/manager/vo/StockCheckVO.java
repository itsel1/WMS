package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockCheckVO {

	public StockCheckVO() {
		
		idx = 0;
		orgStation = "";
		wrUserId = "";
		wDate = "";
		wUserIp = "";
		checkDate = "";
		remark = "";

		takeInCode = "";
		whCnt = 0;
		checkCnt = 0;
		
		rackCode = "";
	}

	private int idx;
	private String orgStation;
	private String wrUserId;
	private String wDate;
	private String wUserIp;
	private String checkDate;
	private String remark;
	
	private String takeInCode;
	private int whCnt;
	private int checkCnt;
	
	private String rackCode;
}
