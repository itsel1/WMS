package com.example.temp.api.aci.vo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MsgHeaderVO {
	public MsgHeaderVO() {
		status = "";
		orderNo = "";
		groupIdx = "";
		detail = new ArrayList<MsgBodyVO>();
	}
	
	private String status;
	private String orderNo;
	private String groupIdx;
	private ArrayList<MsgBodyVO> detail; 
    
}
