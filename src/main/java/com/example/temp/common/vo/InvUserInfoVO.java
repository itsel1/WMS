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
public class InvUserInfoVO{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public InvUserInfoVO() {
		invUserId ="";
		invUserName ="";
		invUserTel ="";
		invUserEmail ="";
		invComName ="";
		invComTel ="";
		invComAddr ="";
		invComAddrDetail ="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String invUserId;
	private String invUserName;
	private String invUserTel;
	private String invUserEmail;
	private String invComName;
	private String invComTel;
	private String invComAddr;
	private String invComAddrDetail;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	
	
	public void encryptData() {
		try {
			if(invUserTel != null)
				invUserTel = AES256Cipher.AES_Encode(invUserTel, symmetryKey);
			if(invComTel != null)
				invComTel = AES256Cipher.AES_Encode(invComTel, symmetryKey);
			if(invComAddr != null)	
				invComAddr = AES256Cipher.AES_Encode(invComAddr, symmetryKey);
			if(invUserEmail != null)	
				invUserEmail = AES256Cipher.AES_Encode(invUserEmail, symmetryKey);
			if(invComAddrDetail != null)	
				invComAddrDetail = AES256Cipher.AES_Encode(invComAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if(invUserTel != null)
				invUserTel = AES256Cipher.AES_Decode(invUserTel, symmetryKey);
			if(invComTel != null)
				invComTel = AES256Cipher.AES_Decode(invComTel, symmetryKey);
			if(invComAddr != null)	
				invComAddr = AES256Cipher.AES_Decode(invComAddr, symmetryKey);
			if(invUserEmail != null)	
				invUserEmail = AES256Cipher.AES_Decode(invUserEmail, symmetryKey);
			if(invComAddrDetail != null)	
				invComAddrDetail = AES256Cipher.AES_Decode(invComAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
	
	
	
	
}


