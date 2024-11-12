package com.example.temp.manager.vo;

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
public class FastboxUserInfoVO {
	
	public FastboxUserInfoVO() {
		idx=0;
		userId="";
		sellerName="";
		brandName="";
		attnName="";
		comName="";
		comRegNo="";
		sellerAddr="";
		sellerAddrDetail="";
		expUseYn="";
		expUnitValue="";
		customsNo="";
		shipperZip="";
		shipperAddr="";
		shipperAddrDetail="";
		shipperName="";
		fbUseYn="";
		fbSendYn="";
		wDate="";
		wUserId="";
		wUserIp="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private int idx;
	private String userId;
	private String sellerName;
	private String brandName;
	private String attnName;
	private String comName;
	private String comRegNo;
	private String sellerAddr;
	private String sellerAddrDetail;
	private String expUseYn;
	private String expUnitValue;
	private String customsNo;
	private String shipperZip;
	private String shipperAddr;
	private String shipperAddrDetail;
	private String shipperName;
	private String fbUseYn;
	private String fbSendYn;
	private String wDate;
	private String wUserId;
	private String wUserIp;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			if (customsNo != null)
				customsNo = AES256Cipher.AES_Encode(customsNo, symmetryKey);
			if (sellerAddr != null)
				sellerAddr = AES256Cipher.AES_Encode(sellerAddr, symmetryKey);
			if (sellerAddrDetail != null)
				sellerAddrDetail = AES256Cipher.AES_Encode(sellerAddrDetail, symmetryKey);
			if (shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			if (shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Encode(shipperAddrDetail, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dncryptData() {
		try {
			if (customsNo != null)
				customsNo = AES256Cipher.AES_Decode(customsNo, symmetryKey);
			if (sellerAddr != null)
				sellerAddr = AES256Cipher.AES_Decode(sellerAddr, symmetryKey);
			if (sellerAddrDetail != null)
				sellerAddrDetail = AES256Cipher.AES_Decode(sellerAddrDetail, symmetryKey);
			if (shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			if (shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
