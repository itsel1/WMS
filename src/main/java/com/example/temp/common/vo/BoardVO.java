package com.example.temp.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVO {
	private String idx;
	private String contextSeq;
	private String groupDepth;
	private String depth;
	private String title;
	private String content;
	private String bType;
	private String wDate;
	private String wUserId;
	private String wUserIp;
}
