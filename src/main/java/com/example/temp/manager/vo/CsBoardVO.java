package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsBoardVO {
	
	public CsBoardVO() {
		nno = "";
		userId = "";
		status = "";
		adminYn= "";
		hawbNo = "";
		title = "";
		contents = "";
		wUserId = "";
		wUserIp= "";
		wDate = "";
		regDate = "";
	}

	private String nno;
	private String userId;
	private String status;
	private String adminYn;
	private String hawbNo;
	private String title;
	private String contents;
	private String wDate;
	private String wUserId;
	private String wUserIp;
	private String regDate;
}