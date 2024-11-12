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

import com.example.temp.api.shop.shopee.ShopeeDTO;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Data;

@Data
public class Shopee {


	private long shopeeId;
	private String userId;
	private String merchantYn;
	private String refreshToken;
	private String accessToken;
	private String tokenCreated;
	private String tokenExpires;
	private String authExpires;
	private String shopName;
	private String shopRegion;
	private String useYn;
	private String wDate;
	
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
	private String userIp;
	private String orderType;
	
	private String code;
	private long timeFrom;
	private long timeTo;
	private String errorMsg;
	private String orderSn;
	private String nextCursor;
	private boolean more;
	
	private boolean hasNextPage;
	private String offsetStr;
	private int offsetInt;
	
	private long partnerId;
	private String partnerKey;
	private String keyExpiryDate;
	

	private ArrayList<String> orderList;
	private ArrayList<Long> globalItemList;
	private ArrayList<Long> itemList;
	private ArrayList<Long> modelList;
	private ArrayList<String> orderSnList;
	private ArrayList<HashMap<String, Object>> shipmentList;
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public Shopee() {
		
		shopeeId = 0;
		userId = "";
		merchantYn = "";
		refreshToken = "";
		accessToken = "";
		tokenCreated = "";
		tokenExpires = "";
		authExpires = "";
		shopName = "";
		shopRegion = "";
		useYn = "";
		wDate = "";
		
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
		userIp = "";
		orderType = "";
		
		code = "";
		timeFrom = 0;
		timeTo = 0;
		errorMsg = "";
			
		
		orderSn = "";
		nextCursor = "";
		more = false;
		
		hasNextPage = false;
		offsetStr = "";
		offsetInt = 0;
		
		partnerId = 0;
		partnerKey = "";
		keyExpiryDate = "";
		
		orderList = new ArrayList<String>();
		itemList = new ArrayList<Long>();
		modelList = new ArrayList<Long>();
		globalItemList = new ArrayList<Long>();
		orderSnList = new ArrayList<String>();
		shipmentList = new ArrayList<HashMap<String, Object>>();
		
	}
	
	
	public void dncryptData() {
		try {
			if(userTel != null)
				userTel = AES256Cipher.AES_Decode(userTel, symmetryKey);
			if(userEAddr != null)
				userEAddr = AES256Cipher.AES_Decode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
				userEAddrDetail = AES256Cipher.AES_Decode(userEAddrDetail, symmetryKey);
			if(userHp != null)
				userHp = AES256Cipher.AES_Decode(userHp, symmetryKey);
			if(userEmail != null)
				userEmail = AES256Cipher.AES_Decode(userEmail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
