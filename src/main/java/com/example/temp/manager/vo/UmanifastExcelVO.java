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
@Getter
@Setter
public class UmanifastExcelVO {
	public UmanifastExcelVO() {
		
		hawbNo = "";
		boxCnt = "";
		wta = "";
		shipperName = "";
		shipperAddr = "";
		nationCode = "";
		totalValue = "";
		totalCnt = "";
		currency = "";
		cneeName = "";
		cneeAddr = "";
		dstnNation = "";
		itemDetail = "";
		hsCode = "";
		expBusinessName="";
		expBusinessNum = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String hawbNo;
	private String boxCnt;
	private String wta;
	private String shipperName;
	private String shipperAddr;
	private String nationCode;
	private String totalValue;
	private String totalCnt;
	private String currency;
	private String cneeName;
	private String cneeAddr;
	private String dstnNation;
	private String dstnStation;
	private String itemDetail;
	private String hsCode;
	private String expBusinessName;
	private String expBusinessNum;
private String symmetryKey = keyVO.getSymmetryKey();
	
	

	public void dncryptData() {
		try {			
			shipperAddr=AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}