package com.example.temp.api.aci.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.example.temp.security.SecurityKeyVO;

@Getter
@Setter
public class ReturnVO {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ReturnVO(){
		}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	// 신청
	private String state;	// 반송상태
	private String comment;		//코멘트
	private String pickType;
	private String nno;
	private String orgStation;	// 출발 도시 
	private String dstnNation;  // 도착 도시
	private String userId;		// 사용자 id
	private String sellerId;	// 셀러 id
	private String calculateId;
	private String orderNo;		// 주문번호
	private String orderReference;
	private String orderDate;	// 주문일자
	private String koblNo;		// 송장번호
	private String reTrkCom;
	private String reTrkNo;
	private String wareHouseMsg; // 창고입고 메세지
	private String deliveryMsg; // 배송 메세지
	private String returnType;	// 발송 구분
	private String food; 		// 음식물 포함여부
	private String system;		// system명
	 
	private String senderName;	// 구매처 이름
	private String senderZip;	// 구매처 우편번호
	private String senderState; // 구매처 state
	private String senderCity;	// 구매처 도시
	private String senderAddr;	// 구매처 주소
	private String senderAddrDetail;	// 구매처 상세주소 
	private String senderBuildNm;		// 구매처 빌딩이름 
	private String senderTel;	// 구매처 연락처1
	private String senderHp;	// 구매처 연락처2
	private String senderEmail; // 구매처 메일주소 
	private String nativeSenderName; 	// 구매처 현지 이름
	private String nativeSenderAddr;	// 구매처 현지 주소
	private String nativeSenderAddrDetail; //구매처 현지 상세주소
	private String cdRemark;	// 세금식별번호
	
	private String attnName;	// 반품담당자 이름 
	private String attnTel;		// 반품담당자 연락처
	private String attnEmail;	// 반품담당자 이메일
	private String pickupName;	// 반품 신청자 이름
	private String pickupTel;	// 반품 신청자 전화번호 컬럼1
	private String pickupHp;
	private String pickupMobile;		// 
	private String pickupZip;	// 반품 신청자 우편번호 
	private String pickupAddr;	// 반품 신청자 주소
	private String pickupAddrDetail;	// 반품 신청자 주소
	private String pickupEngAddr;	// 반품 영문 주소
	private String returnReason;
	private String returnReasonDetail;
	
	private String taxType;		// 위약반송여부
	private String fileReasonType;	// 반품 사유서
	private String fileReason;	// 반품 사유서
	private String fileReasonExten;	// 반품 사유서
	private String fileCaptureType; // 반품접수 캡쳐본
	private String fileCapture; // 반품접수 캡쳐본
	private String fileCaptureExten; // 반품접수 캡쳐본
	private String fileMessengerType; 	// 반품 메신져 캡쳐본
	private String fileMessenger; 	// 반품 메신져 캡쳐본
	private String fileMessengerExten; 	// 반품 메신져 캡쳐본
	private String fileClType;		// Commercial Invoice (입항 시)
	private String fileCl;		// Commercial Invoice (입항 시)
	private String fileClExten;		// Commercial Invoice (입항 시)
	private String fileIc;		// 수입신고필증(입항 시)
	private String fileCopyBankType;	// 환급 통장 사본
	private String fileCopyBank;	// 환급 통장 사본
	private String fileCopyBankExten;	// 환급 통장 사본
	private String taxReturn;	// 세금 환급금 수취 주체
	
	
	private String itemDetail;
	private String itemWta;
	private String itemCnt;
	private String A002Date;
	
	private String itemDetailEng;
	private String nativeItemDetail;
	private String cusItemCode;
	
	private String whMsg;
	
	private String orgNation;
	private String orgNationName;
	private String dstnNationName;
	
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Encode(senderAddr, symmetryKey);
			if(senderAddrDetail != null)
				senderAddrDetail = AES256Cipher.AES_Encode(senderAddrDetail, symmetryKey);
			if(senderTel != null)
				senderTel = AES256Cipher.AES_Encode(senderTel, symmetryKey);
			if(senderHp != null)
				senderHp = AES256Cipher.AES_Encode(senderHp, symmetryKey);
			if(pickupAddr != null)
				pickupAddr = AES256Cipher.AES_Encode(pickupAddr, symmetryKey);
			if(pickupTel != null)
				pickupTel = AES256Cipher.AES_Encode(pickupTel, symmetryKey);
			if(pickupMobile != null)
				pickupMobile = AES256Cipher.AES_Encode(pickupMobile, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Decode(senderAddr, symmetryKey);
			if(senderAddrDetail != null)
				senderAddrDetail = AES256Cipher.AES_Decode(senderAddrDetail, symmetryKey);
			if(senderTel != null)
				senderTel = AES256Cipher.AES_Decode(senderTel, symmetryKey);
			if(senderHp != null)
				senderHp = AES256Cipher.AES_Decode(senderHp, symmetryKey);
			if(pickupAddr != null)
				pickupAddr = AES256Cipher.AES_Decode(pickupAddr, symmetryKey);
			if(pickupTel != null)
				pickupTel = AES256Cipher.AES_Decode(pickupTel, symmetryKey);
			if(pickupMobile != null)
				pickupMobile = AES256Cipher.AES_Decode(pickupMobile, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	
}
