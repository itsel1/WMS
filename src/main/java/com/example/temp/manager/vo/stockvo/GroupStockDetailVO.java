package com.example.temp.manager.vo.stockvo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupStockDetailVO{
	public GroupStockDetailVO() {
		itemDetail = "";
		unitCnt = "";
	}
	private String itemDetail;
	private String unitCnt;
}
