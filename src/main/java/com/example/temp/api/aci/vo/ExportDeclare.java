package com.example.temp.api.aci.vo;

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

import lombok.Data;

@Data
public class ExportDeclare {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	public ExportDeclare() {
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
			logger.error("Exception", e);
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
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	
	
}
