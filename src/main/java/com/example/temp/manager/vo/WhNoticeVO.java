package com.example.temp.manager.vo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WhNoticeVO {
	private int idx;
	private int groupIdx;
	private int step;
	private int indent;
	private String orgStation;
	private String status;
	private String category;
	private String title;
	private String content;
	private String WUserId;
	private String WUserIp;
	private String WDate;
	
	private ArrayList<WhNoticeFileVO> fileList;

	public WhNoticeVO() {
		idx=0;
		groupIdx=0;
		step=0;
		indent=0;
		orgStation="";
		status="";
		category="";
		title="";
		content="";
		WUserId="";
		WUserIp="";
		WDate="";
		
	}

}
