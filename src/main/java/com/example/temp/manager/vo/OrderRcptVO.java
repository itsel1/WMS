package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRcptVO {
	private String hawbNo;
	private String orgStation;
	private String boxCnt;
	private String wta;
	private String wtc;
	private String wtUnit;
	private String wUserId;
	private String wUserIp;
	private String boxWeight;
	private String[] width;
	private String[] length;
	private String[] height;
}
