package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackVO {

	public RackVO() {
		
		orgStation = "";
		rackCode = "";
		rackName = "";
		rackRemark = "";
		orderBy = 0;
		useYn = "";
		
		takeInCode = "";
		cusItemCode = "";
		itemDetail = "";
		stockType = "";


		cnt = 0;
		userId = "";
		
		itemRemark = "";
		
		outNno = "";
		whCnt = 0;
		
		idx = 0;
		wUserId = "";
		wUserIp = "";
		wDate = "";
		checkDate = "";
		remark = "";
		checkCnt = 0;
		
		
		
	}
	
	private String orgStation;
	private String rackCode;
	private String rackName;
	private String rackRemark;
	private int orderBy;
	
	private String takeInCode;
	private String cusItemCode;
	private String itemDetail;
	private String stockType;
	
	private int cnt;
	private String userId;
	
	private String itemRemark;
	
	private String outNno;
	private int whCnt;
	
	private String useYn;
	
	private int idx;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String checkDate;
	private String remark;
	
	private int checkCnt;
	
	
}

