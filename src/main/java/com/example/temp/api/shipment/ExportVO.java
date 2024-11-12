package com.example.temp.api.shipment;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExportVO {
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private String nno;
	private String orderNo;
	private String orderDate;
	private String hawbNo;
	private String agtCor;
	private String agtCstCd;
	private String agtBizNo;
	private String expCor;
	private String expRprsn;
	private String expAddr;
	private String expZip;
	private String expRgstrNo;
	private String expCstCd;
	private String cneeName;
	private String dstnNation;
	private String payment;
	private String wta;
	private String wtUnit;
	private String unitCurrency;
	private String itemInfo;
	private String totalItemValue;
	private String boxCnt;
	private String expNo;
	private String userWta;
	
	public ExportVO() {
		
		nno = "";
		orderNo = "";
		orderDate = "";
		hawbNo = "";
		agtCor = "";
		agtCstCd = "";
		agtBizNo = "";
		expCor = "";
		expRprsn = "";
		expAddr = "";
		expZip = "";
		expRgstrNo = "";
		expCstCd = "";
		cneeName = "";
		dstnNation = "";
		payment = "";
		wta = "";
		wtUnit = "";
		unitCurrency = "";
		itemInfo = "";
		totalItemValue = "";
		boxCnt = "";
		expNo = "";
		userWta = "";
	}
	
	public void decryptData() {
		try {
			if(expAddr != null)
				expAddr = AES256Cipher.AES_Decode(expAddr, symmetryKey);
			if(expRgstrNo != null)
				expRgstrNo = AES256Cipher.AES_Decode(expRgstrNo, symmetryKey);
			if(expCstCd != null)
				expCstCd = AES256Cipher.AES_Decode(expCstCd, symmetryKey);
			if(agtCstCd != null)
				agtCstCd = AES256Cipher.AES_Decode(agtCstCd, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
}
