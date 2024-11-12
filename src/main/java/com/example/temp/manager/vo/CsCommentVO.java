package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsCommentVO {

	public CsCommentVO() {
		cno = 0;
		nno = "";
		coDiv = 0;
		crno = 0;
		userId = "";
		comment = "";
		modiYn = "";
		wDate = "";
		wUserId = "";
		wUserIp = "";
	}
	
	private int cno;
	private String nno;
	private int coDiv;
	private int crno;
	private String userId;
	private String comment;
	private String modiYn;
	private String wDate;
	private String wUserId;
	private String wUserIp;
}
