package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MsgBodyVO {
	public MsgBodyVO() {
		subNo = "";
		seq = "";
		msg = "";
		wDate = "";
		userDiv = "";
	}
	private String subNo;
	private String seq;
	private String msg;
	private String wDate;
	private String userDiv;
    
}
