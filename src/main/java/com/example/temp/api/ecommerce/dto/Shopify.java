package com.example.temp.api.ecommerce.dto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Data;

@Data
public class Shopify {
	
	private String userId;
	private String shopifyUrl;
	private String apiKey;
	private String apiToken;
	private String useYn;
	private String wDate;
	
	private String dateTimeFrom;
	private String dateTimeTo;
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String comEName;
	private String userTel;
	private String userZip;
	private String userEState;
	private String userECity;
	private String userEAddr;
	private String userEAddrDetail;
	private String userHp;
	private String userEmail;
	private String storeUrl;
	private String orgNation;
	
	private String errorMsg;
	private String userIp;

	private ArrayList<ShopifyOrder> shopifyOrderList;
	
	public Shopify() {
		userId = "";
		shopifyUrl = "";
		apiKey = "";
		apiToken = "";
		useYn = "";
		wDate = "";
		
		dateTimeFrom = "";
		dateTimeTo = "";
		
		comEName = "";
		userTel = "";
		userZip = "";
		userEState = "";
		userECity = "";
		userEAddr = "";
		userEAddrDetail = "";
		userHp = "";
		userEmail = "";
		storeUrl = "";
		orgNation = "";
		
		errorMsg = "";
		userIp = "";
		
		shopifyOrderList = new ArrayList<ShopifyOrder>();
	}
	
	public void dncryptData() {
		try {
			if(userTel != null)
				userTel = AES256Cipher.AES_Decode(userTel, symmetryKey);
			if(userHp != null)
				userHp = AES256Cipher.AES_Decode(userHp, symmetryKey);
			if(userEmail != null)
			    userEmail = AES256Cipher.AES_Decode(userEmail, symmetryKey);
			if(userEAddr != null)
			    userEAddr= AES256Cipher.AES_Decode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
			    userEAddrDetail= AES256Cipher.AES_Decode(userEAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}
	}
}
