package com.example.temp.member.vo;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShpngListVO2 {
	public ShpngListVO2 () {
		userId="";
		companyName="";
		nno="";
		cneeName="";
		cneeZip="";
		cneeAddr="";
		cneeAddrDetail="";
		cneeTel="";
		cneeHp="";
		subNo="";
		hsCode="";
		itemDetail="";
		unitValue="";
		itemCnt="";
		itemValue="";
		orderDate="";
		delDate="";
		recDate="";
		resultCode="";
		dno="";
		orderNo="";
		status="";
		hawbNo="";
	}
	
	/* private String nationCode; */
	private String userId;
	private String companyName;
	private String nno;
	private String cneeName;
	private String cneeZip;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String cneeTel;
	private String cneeHp;
	private String subNo;
	private String hsCode;
	private String itemDetail;
	private String unitValue;
	private String itemCnt;
	private String itemValue;
	private String orderDate;
	private String delDate;
	private String recDate;
	private String resultCode;
	private String dno;
	private String orderNo;
	private String status;
	private String hawbNo;
	
	@Value("${resources.symmetryKey}")
	private String symmetryKey;
	
	
	public String getCneeAddr() {
		String value = cneeAddr;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setCneeAddr(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.cneeAddr = value;
	}
	
	public String getCneeAddrDetail() {
		String value = cneeAddrDetail;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setCneeAddrDetail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.cneeAddrDetail = value;
	}
}
