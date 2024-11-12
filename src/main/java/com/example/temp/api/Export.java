package com.example.temp.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Data;

@Data
public class Export {
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String nno;
	private String userId;
	private String expType;
	private String expCor;
	private String expRprsn;
	private String expAddr;
	private String expZip;
	private String expRgstrNo;
	private String expCstCd;
	private String agtCor;
	private String agtCstCd;
	private String agtBizNo;
	private String expNo;
	private String sendYn;
	private String wDate;
	
	public Export() {
		nno = "";
		userId = "";
		expType = "";
		expCor = "";
		expRprsn = "";
		expAddr = "";
		expZip = "";
		expRgstrNo = "";
		expCstCd = "";
		agtCor = "";
		agtCstCd = "";
		agtBizNo = "";
		expNo = "";
		sendYn = "";
		wDate = "";
		
	}
	
	public void encryptData() {
		try {
			
			if (expAddr != null) {
				expAddr = AES256Cipher.AES_Encode(expAddr, symmetryKey);
			}
			
			if (expRgstrNo != null) {
				expRgstrNo = AES256Cipher.AES_Encode(expRgstrNo, symmetryKey);
			}
			
			if (expCstCd != null) {
				expCstCd = AES256Cipher.AES_Encode(expCstCd, symmetryKey);
			}
			
			if (agtCstCd != null) {
				agtCstCd = AES256Cipher.AES_Encode(agtCstCd, symmetryKey);
			}
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void dncryptData() {
		try {
			
			if (expAddr != null) {
				expAddr = AES256Cipher.AES_Decode(expAddr, symmetryKey);
			}
			
			if (expRgstrNo != null) {
				expRgstrNo = AES256Cipher.AES_Decode(expRgstrNo, symmetryKey);
			}
			
			if (expCstCd != null) {
				expCstCd = AES256Cipher.AES_Decode(expCstCd, symmetryKey);
			}

			if (agtCstCd != null) {
				agtCstCd = AES256Cipher.AES_Decode(agtCstCd, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	

}
