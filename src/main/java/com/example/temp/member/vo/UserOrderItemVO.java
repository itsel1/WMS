package com.example.temp.member.vo;

import java.security.MessageDigest;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderItemVO {
	public UserOrderItemVO() {
		nno = "";
		subNo = "";
		orgStation = "";
		userId = "";
		nationCode = "";
		hsCode = "";
		itemDetail = "";
		itemCnt = "";
		unitValue = "";
		brand = "";
		makeCntry = "";
		makeCom = "";
		itemDiv = "";
		wtUnit = "";
		qtyUnit = "";
		packageUnit = "";
		exchangeRate = "";
		chgCurrency = "";
		chgAmt = "";
		cusItemCode = "";
		itemMeterial = "";
		takeInCode = "";
		takeInCode2 = "";
		userItemWta = "";
		userItemWtc = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		itemUrl = "";
		itemImgUrl = "";
		status = "";
		trkNo = "";
		trkCom = "";
		trkDate ="";
		nativeItemDetail = "";
		dimUnit = "";
		unitCurrency="";
		itemExplan="";	
		itemBarcode="";
		inBoxNum="";
		transCode="";
		
		changeHsCode="";
		
		userWta=0;
		userWtc=0;
		
		totalItemCnt = 0;
		
		sagawaItemCode = "";
	}
	
	
	private String nno;							//NNO
	private String unitCurrency;				//??
	private String subNo;						//상품 SUB NO
	private String orgStation;					//출발Station
	private String userId;						//사용자 ID
	private String nationCode;					//국가 코드
	private String hsCode;						//HS 코드
	private String itemDetail;					//상품명
	private String itemCnt;						//상품 개수
	private String unitValue;					//상품 단가
	private String brand;						//브랜드
	private String makeCntry;					//제조 국가
	private String makeCom;						//제조사
	private String itemDiv;						//상품 분류
	private String wtUnit;						//무게 단위
	private String qtyUnit;						//상품 단위
	private String packageUnit;					//묶음 단위
	private String exchangeRate;				//환율
	private String chgCurrency;					//통화 (USD,KRW)
	private String chgAmt;						//??
	private String cusItemCode;					//상품코드
	private String itemMeterial;				//상품재질
	private String takeInCode;					//사입코드??
	private String takeInCode2;					//사입코드??
	private String userItemWta;					//실무게
	private String userItemWtc;					//부피무게
	private String wUserId;						//등록ID
	private String wUserIp;						//등록IP
	private String wDate;						//DB등록날짜
	private String itemUrl;						//상품 정보 URL
	private String itemImgUrl; 					//상품 이미지 URL
	private String status; 						//상태 (A / S)	
	private String trkNo;  						//국내 택배 번호
	private String trkCom; 						//국내 택배 회사
	private String trkDate; 					//운송 날짜
	private String nativeItemDetail;			//대상국가 원어 상품명
	private String dimUnit;						//길이단위
	private String itemExplan;					//상품설명
	private String itemBarcode;					//상품바코드
	private String inBoxNum;					//BOX 번호
	private String transCode;					//배송사코드
	private String changeHsCode;
	private float userWta;
	private float userWtc;
	
	private float itemWta;
	
	private int totalItemCnt;
	
	private String sagawaItemCode;
}
