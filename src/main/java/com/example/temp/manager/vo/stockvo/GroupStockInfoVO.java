package com.example.temp.manager.vo.stockvo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupStockInfoVO{
	public GroupStockInfoVO() {
		groupIdx = "";
		orgStation = "";
		userId = "";
		orderNo = "";
		hawbNo = "";
		stockCnt = "";
	}
	private String groupIdx;
	private String orgStation;
	private String userId;
	private String orderNo;
	private String hawbNo;
	private String stockCnt;
}
