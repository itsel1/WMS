package com.example.temp.api.aci.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnListVO {
	public ReturnListVO() {
		nno = "";
		////
		state = "";
		stateKr="";
		whReqMsg = "";
		dlvReqMsg = "";
		returnType = "";
		////
		orderNo = "";
		hawbNo = "";
		orgStation = "";
		dstnNation = "";
		userId = "";
		orderType = "";
		orderDate = "";
		userWta = "";
		boxCnt = "";
		fileReason ="";
		fileCopyBank ="";
		fileCapture ="";
		fileMessenger ="";
		fileCl ="";
		fileIc ="";
		koblNo ="";
		orderReference = "";
		sellerId="";
		writeDate="";
		pickupName = "";
		taxType = "";
		
		userMsg="";
		adminMsg="";
		
		reTrkDate="";
		idx = 0;
		readYn = "";
	}
	
		private int idx;
		private String pickType;
		private String nno;
		//
		private String state;
		private String stateKr;
		private String whReqMsg;
		private String dlvReqMsg;
		private String returnType;
		//
		private String orderNo;
		private String hawbNo;
		private String orgStation;
		private String dstnNation;
		private String userId;
		private String orderType;
		private String orderDate;
		private String userWta;
		private String boxCnt;
		private String fileReason;
		private String fileCopyBank;
		private String fileCapture;
		private String fileMessenger;
		private String fileCl;
		private String fileIc;
		private String koblNo;
		private String regNo;
		private String reTrkCom;
		private String reTrkNo;
		private String reTrkDate;
		private String orderReference;
		private String sellerId;
		private String writeDate;
		private String registDate;
		private String transCode;
		private String transWDate;
		private String pickupName;
		private String taxType;
		// 

		private String userMsg;
		private String adminMsg;
		private String readYn;
	
	
}
