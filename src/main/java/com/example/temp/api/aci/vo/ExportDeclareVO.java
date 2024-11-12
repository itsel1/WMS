package com.example.temp.api.aci.vo;

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
public class ExportDeclareVO {

	public ExportDeclareVO() {
		nno = "";
		orderNo = "";
		orderDate = "";
		hawbNo = "";
		agencyBusinessName = "";
		agtCor = "";
		
		expCor = "";
		expRprsn = "";
		expAddr = "";
		expZip = "";
		expRgstrNo = "";
		expCstCd = "";
		
		
		comName = "";
		userName = "";
		userAddr = "";
		userAddrDetail = "";
		userZip = "";
		expBusinessNum = "";
		expShipperCode = "";
		dstnNation = "";
		payment = "";
		unitCurrency = "";
		totalItemValue = "";
		boxCnt = 0;
		wta = "";
		wtUnit = "";
		subNo = 0;
		itemCnt = 0;
		qtyUnit = "";
		unitValue = "";
		itemValue = "";
		itemDetail = "";
		hsCode = "";
		cneeName = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String nno;
	private String orderNo;
	private String orderDate;
	private String hawbNo;
	private String agencyBusinessName;
	private String agtCor;
	
	private String expCor;
	private String expRprsn;
	private String expAddr;
	private String expZip;
	private String expRgstrNo;
	private String expCstCd;
	
	
	private String comName;
	private String userName;
	private String userAddr;
	private String userAddrDetail;
	private String userZip;
	private String expBusinessNum;
	private String expShipperCode;
	private String dstnNation;
	private String payment;
	private String unitCurrency;
	private String totalItemValue;
	private int boxCnt;
	private String wta;
	private String wtUnit;
	private int subNo;
	private int itemCnt;
	private String qtyUnit;
	private String unitValue;
	private String itemValue;
	private String itemDetail;
	private String hsCode;
	private String cneeName;
	/*
	public void encryptData() {
		try {
			if(userAddr != null)
				userAddr=AES256Cipher.AES_Encode(userAddr, symmetryKey);
			if(userAddrDetail != null)
				userAddrDetail=AES256Cipher.AES_Encode(userAddrDetail, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	public void dncryptData() {
		try {
			if(expAddr != null) {
				expAddr=AES256Cipher.AES_Decode(expAddr, symmetryKey);
			}
			if(expRgstrNo != null) {
				expRgstrNo=AES256Cipher.AES_Decode(expRgstrNo, symmetryKey);
			}
			if(expCstCd != null) {
				expCstCd=AES256Cipher.AES_Decode(expCstCd, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
