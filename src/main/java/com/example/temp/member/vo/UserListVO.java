package com.example.temp.member.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListVO {
	public UserListVO() {
		nno = "";
		state = "";
		hawbNo = "";
		userId = "";
		//반송유형
		//수거택배
		orderNo = "";
		orgStation = "";
		dstnNation = "";
		
		orderType = "";
		orderDate = "";
		userWta = "";
		boxCnt = "";
		rtnReasonUrl = "";
		bankBookUrl = "";
		naverCapUrl = "";
		tockTockUrl = "";
		rtnCommUrl = "";
	}
	

		private String nno;
		private String state;
		private String hawbNo;
		private String userId;
		//반송유형
		//수거택배
		private String orderNo;
		private String orgStation;
		private String dstnNation;
		
		private String orderType;
		private String orderDate;
		private String userWta;
		private String boxCnt;
		private String rtnReasonUrl;
		private String bankBookUrl;
		private String naverCapUrl;
		private String tockTockUrl;
		private String rtnCommUrl;
		
	
}
