package com.example.temp.member.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerVO {
	
	public CustomerVO(){
		userId = "";
		userName = "";
		userPw = "";
		userTel = "";
		userAddr = "";
		userAddrDetail = "";
		userEmail = "";
		etprYn = "";
		aprvYn = "";
		depositYn = "";
		inspPrice = "";
		userGrade = "";
		wtErrRange = "";
		inspYn = "";
		takeYn = "";
		nomalYn = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		userZip = "";
		role = "";
		userEAddr = "";
		comName = "";
		comEName = "";
		userEAddrDetail = "";
		dimUnit = "";
		wtUnit = "";
		apiKey = "";
		stockWeightYn = "";
		storeUrl = "";
		storeName = "";
		userHp = "";
		currency="";
		invoiceNation="";
		
	}

	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String userId;
	private String userName;
	private String userPw;
	private String invoiceNation;
	private String currency;
	
	private String etprYn;
	private String aprvYn;
	private String depositYn;
	private String inspPrice;
	private String userGrade;
	private String wtErrRange;
	private String inspYn;
	private String takeYn;
	private String nomalYn;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	
	private String userZip;
	private String role;
	
	private String comName;
	private String comEName;
	
	private String dimUnit;
	private String wtUnit;
	private String apiKey;
	
	private String stockWeightYn;
	private String storeUrl;
	private String storeName;
	
	private String userHp;
	private String userTel;
	private String userEmail;
	
	private String userAddr;
	private String userAddrDetail;
	
	private String userEAddr;
	private String userEAddrDetail;
	
	private String symmetryKey = keyVO.getSymmetryKey();

	public void dncryptData() {
		try {
			
			if(userHp != null)
				userHp = AES256Cipher.AES_Decode(userHp, symmetryKey);
			if(userTel != null)
				userTel = AES256Cipher.AES_Decode(userTel, symmetryKey);
			if(userEmail != null)
				userEmail = AES256Cipher.AES_Decode(userEmail, symmetryKey);
			if(userAddr != null)
				userAddr = AES256Cipher.AES_Decode(userAddr, symmetryKey);
			if(userAddrDetail != null)
				userAddrDetail = AES256Cipher.AES_Decode(userAddrDetail, symmetryKey);
			if(userEAddr != null)
				userEAddr = AES256Cipher.AES_Decode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
				userEAddrDetail = AES256Cipher.AES_Decode(userEAddrDetail, symmetryKey);

		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
}
