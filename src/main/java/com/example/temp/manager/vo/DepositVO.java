package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositVO {

	private int idx;
	private String userId;
	private String orgStation;
	private String payType;				// 입금방식
	private String applyDate;			// 입금예정일
	private String applyAmt;			// 입금 예정액
	private String receivedDate;		// 입금일
	private String receivedAmt;			// 입금액
	private String receivedCurrency;	// 신청통화
	private String wDate;
	private String wUserId;
	private String wUserIp;
	private String depositAmt;			// 예치금
	private String depositCurrency;		// 입금 통화
	private String depositAmtTotal;		// 예치금 총액
	private String exchangeRate;		// 환율
	private String remark;				// 비고
	private String depositor;			// 예금주
	private String comName;
}
