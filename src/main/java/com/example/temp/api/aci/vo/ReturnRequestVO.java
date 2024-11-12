package com.example.temp.api.aci.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequestVO {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ReturnRequestVO(){
		nno = "";
		pickType="";
		orgStation="";
		dstnNation="";
		sellerId="";
		orderDate="";
		orderNo="";
		orderReference = "";
		koblNo="";
		calculateId = "";
		reTrkCom="";
		reTrkNo="";
		reTrkDate="";
		whMsg="";
		deliveryMsg="";
		returnType="";
		food="";
		senderName="";
		senderZip="";
		senderState = "";
		senderCity="";
		senderAddr="";
		senderAddrDetail="";
		senderBuildNm="";
		senderTel="";
		senderHp="";
		senderEmail="";
		nativeSenderName="";
		nativeSenderAddr="";
		nativeSenderAddrDetail="";
		attnName="";
		attnTel="";
		attnEmail="";
		pickupName="";
		pickupTel="";
		pickupZip="";
		pickupMobile = "";
		pickupAddr="";
		pickupEngAddr="";
		returnReason="";
		returnReasonDetail="";
		fileReasonType="";
		fileReason="";
		fileCaptureType="";
		fileCapture="";
		fileMessengerType="";
		fileMessenger="";
		fileClType="";
		fileCl="";
		fileIcType="";
		fileIc="";
		fileCopyBankType="";
		fileCopyBank="";
		taxType="";
		taxReturn="";
		rootSite="";
		state="";
		wUserId="";
		wUserIp="";
		wDate="";
		//cncl = 0;
		//cnclText = "";
		//rmnd = 0;
		a002Date="";
		a000Date="";
		b001Date="";
		c001Date="";
		c004Date="";
		d005Date="";
		
		orgNationName="";
		dstnNationName="";
		stateKr="";
		
		mainItem="";
		totalValue="";
		itemCnt=0;
		failMsg="";
		failReason="";
		rackCode="";
		whOutCnt=0;
		itemWta=0;
		wtUnit="";
		cusItemCode="";
		}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String nno;
	private String regNo;
	private String orderType;
	private String pickType;
	private String orgStation;
	private String dstnNation;
	private String userId;
	private String calculateId;
	private String divisionKey;
	private String sellerId;
	private String orderDate;
	private String orderReference;
	private String orderNo;
	private String koblNo;
	private String reTrkCom;
	private String reTrkNo;
	private String reTrkDate;
	private String whMsg;
	private String deliveryMsg;
	private String returnType;
	private String food;
	private String senderName;
	private String senderZip;
	private String senderState;
	private String senderCity;
	private String senderAddr;
	private String senderAddrDetail;
	private String senderBuildNm;
	private String senderTel;
	private String senderHp;
	private String senderEmail;
	private String nativeSenderName;
	private String nativeSenderAddr;
	private String nativeSenderAddrDetail;
	private String cdRemark;
	private String attnName;
	private String attnTel;
	private String attnEmail;
	private String pickupName;
	private String pickupTel;
	private String pickupMobile;
	private String pickupZip;
	private String pickupAddr;
	private String pickupEngAddr;
	private String pickupAddrDetail;
	private String returnReason;
	private String returnReasonDetail;
	private String fileReasonType;
	private String fileReason;
	private String fileReasonExten;
	private String fileCaptureType;
	private String fileCapture;
	private String fileCaptureExten;
	private String fileMessengerType;
	private String fileMessenger;
	private String fileMessengerExten;
	private String fileClType;
	private String fileCl;
	private String fileClExten;
	private String fileIcType;
	private String fileIc;
	private String fileIcExten;
	private String fileCopyBankType;
	private String fileCopyBank;
	private String fileCopyBankExten;
	private String taxType;
	private String taxReturn;
	private String rootSite;
	private String state;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String a002Date;
	private String a000Date;
	private String b001Date;
	private String c001Date;
	private String c004Date;
	private String d005Date;
	
	private String orgNationName;
	private String dstnNationName;
	private String stateKr;
	
	private String mainItem;
	private String totalValue;
	private int itemCnt;
	private String failMsg;
	private String failReason;
	private String rackCode;
	private int whOutCnt;
	private float itemWta;
	private String wtUnit;
	
	private String cusItemCode;
	
	public void encryptData() {
		try {
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Encode(senderAddr, symmetryKey);
			if(senderTel != null)
				senderTel = AES256Cipher.AES_Encode(senderTel, symmetryKey);
			if(senderHp != null)
				senderHp = AES256Cipher.AES_Encode(senderHp, symmetryKey);
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Encode(senderAddr, symmetryKey);
			if(senderAddrDetail != null)
				senderAddrDetail = AES256Cipher.AES_Encode(senderAddrDetail, symmetryKey);
			if(senderBuildNm != null)
				senderBuildNm = AES256Cipher.AES_Encode(senderBuildNm, symmetryKey);
			if(attnTel != null) 
				attnTel = AES256Cipher.AES_Encode(attnTel, symmetryKey);
			if(attnEmail != null)
				attnEmail = AES256Cipher.AES_Encode(attnEmail, symmetryKey);
			if(pickupAddr != null)
				pickupAddr = AES256Cipher.AES_Encode(pickupAddr, symmetryKey);
			if(pickupTel != null)
				pickupTel = AES256Cipher.AES_Encode(pickupTel, symmetryKey);
			if(pickupMobile != null)
				pickupMobile = AES256Cipher.AES_Encode(pickupMobile, symmetryKey);
			if(pickupAddrDetail != null)
				pickupAddrDetail = AES256Cipher.AES_Encode(pickupAddrDetail, symmetryKey);
			if(pickupEngAddr != null)
				pickupEngAddr = AES256Cipher.AES_Encode(pickupEngAddr, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Decode(senderAddr, symmetryKey);
			if(senderTel != null)
				senderTel = AES256Cipher.AES_Decode(senderTel, symmetryKey);
			if(senderHp != null)
				senderHp = AES256Cipher.AES_Decode(senderHp, symmetryKey);
			if(senderAddr != null)
				senderAddr = AES256Cipher.AES_Decode(senderAddr, symmetryKey);
			if(senderAddrDetail != null)
				senderAddrDetail = AES256Cipher.AES_Decode(senderAddrDetail, symmetryKey);
			if(senderBuildNm != null)
				senderBuildNm = AES256Cipher.AES_Decode(senderBuildNm, symmetryKey);
			if(attnTel != null) 
				attnTel = AES256Cipher.AES_Decode(attnTel, symmetryKey);
			if(attnEmail != null)
				attnEmail = AES256Cipher.AES_Decode(attnEmail, symmetryKey);
			if(pickupAddr != null)
				pickupAddr = AES256Cipher.AES_Decode(pickupAddr, symmetryKey);
			if(pickupTel != null)
				pickupTel = AES256Cipher.AES_Decode(pickupTel, symmetryKey);
			if(pickupMobile != null)
				pickupMobile = AES256Cipher.AES_Decode(pickupMobile, symmetryKey);
			if(pickupAddrDetail != null)
				pickupAddrDetail = AES256Cipher.AES_Decode(pickupAddrDetail, symmetryKey);
			if(pickupEngAddr != null)
				pickupEngAddr = AES256Cipher.AES_Decode(pickupEngAddr, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	
	
	
}
