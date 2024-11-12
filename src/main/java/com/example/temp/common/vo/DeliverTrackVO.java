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

@Setter
@Getter
public class DeliverTrackVO{
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public DeliverTrackVO() {
		cneeName="";
		nativeCneeName="";
		nativeCneeAddr="";
		naticeCneeAddrDetail="";
		hawbInTime="";
		mawbInTime="";
		depDate="";
		depTime="";
		arrDate="";
		arrTime="";
		area="";
		orderInDate="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String orderNo;
	private String orderInDate;
	private String cneeName;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String naticeCneeAddrDetail;
	private String hawbInTime;
	private String mawbInTime;
	private String depDate;
	private String depTime;
	private String arrDate;
	private String arrTime;
	private String area;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			if(nativeCneeAddr != null)
				nativeCneeAddr = AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(naticeCneeAddrDetail != null)
				naticeCneeAddrDetail = AES256Cipher.AES_Encode(naticeCneeAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if(nativeCneeAddr != null)
				nativeCneeAddr = AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(naticeCneeAddrDetail != null)
				naticeCneeAddrDetail = AES256Cipher.AES_Decode(naticeCneeAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
}
