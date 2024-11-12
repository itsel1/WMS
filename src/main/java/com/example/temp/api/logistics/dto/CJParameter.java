package com.example.temp.api.logistics.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CJParameter {
	
	private String custId;
	private String bizNo;
	private String tokenNo;
	private String tokenEDate;
	private String hawbNo;
	private String nno;
	private String userId;
	private String resultCd;
	private String resultMsg;
	
	private String clsfCd;				// 도착지 코드
	private String subClsfCd;			// 도착자 서브코드
	private String clsfAddr;			// 주소 약칭
	private String cllDlvBranNm;		// 배송집배점 명
	private String cllDlvEmpNm;			// 배송 SM명
	private String cllDlvEmpNickNm;		// SM 분류코드
	private String rspsDiv;				// 권역 구분
	private String p2pCd;				// p2p 코드
	
	
	
	public CJParameter() {
		custId = "";
		bizNo = "";
		tokenNo = "";
		tokenEDate = "";
		hawbNo = "";
		nno = "";
		userId = "";
		resultCd = "";
		resultMsg = "";
		
		clsfCd = "";
		subClsfCd = "";
		clsfAddr = "";
		cllDlvBranNm = "";
		cllDlvEmpNickNm = "";
		rspsDiv = "";
		p2pCd = "";
	}
	
}
