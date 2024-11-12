package com.example.temp.common.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommercialVO {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public CommercialVO() {
		hawbNo="";
		depDate="";
		comEName="";
		shipperCntry="";
		userEAddr="";
		userEAddrDetail="";
		userTel="";
		cneeName="";
		dstnNation="";
		cneeCntry="";
		cneeState="";
		cneeCity="";
		cneeAddr="";
		cneeAddrDetail="";
		cneeTel="";
		depStation="";
		arrStation="";
		buySite="";
		payment="";
		totalItemCnt=1;
		totalItemValue=0;
		boxCnt=1;
		userWta=0;
		userWtc=0;
		sagawaNo="";
		matchNo = "";
		orderDate="";
		orderType="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String orderType;
	private String hawbNo;
	private String depDate;
	private String comEName;
	private String shipperCntry;
	private String userEAddr;
	private String userEAddrDetail;
	private String userTel;
	private String cneeName;
	private String dstnNation;
	private String cneeCntry;
	private String cneeState;
	private String cneeCity;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String cneeTel;
	private String depStation;
	private String arrStation;
	private String buySite;
	private String payment;
	private int totalItemCnt;
	private float totalItemValue;
	private int boxCnt;
	private double userWta;
	private double userWtc;
	private String sagawaNo;
	private String matchNo;
	private String orderDate;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			if(userTel != null)
				userTel = AES256Cipher.AES_Encode(userTel, symmetryKey);
			if(userEAddr != null)
				userEAddr = AES256Cipher.AES_Encode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
				userEAddrDetail = AES256Cipher.AES_Encode(userEAddrDetail, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail=AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if(userTel != null)
				userTel = AES256Cipher.AES_Decode(userTel, symmetryKey);
			if(userEAddr != null)
				userEAddr = AES256Cipher.AES_Decode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
				userEAddrDetail = AES256Cipher.AES_Decode(userEAddrDetail, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail=AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
	}
}
