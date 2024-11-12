package com.example.temp.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO {
	private String rownum;
	private String idx;
	private String title;
	private String contents;
	private String dstnStation;
	private String orgStation;
	private String date;
	private String wDate;
	private String wUserId;
	private String wUserIp;
	private String nextYn;
	private String prevYn;
}
