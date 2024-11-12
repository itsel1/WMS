package com.example.temp.member.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVO {
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private int idx;
	private String userId;
	private String userName;
	private String userTel;
	private String userHp;
	private String userAddr;
	private String userAddrDetail;
	private String userEmail;
	private String etprYn;
	private String aprvYn;
	private String depositYn;
	private String inspPrice;
	private String userGrade;
	private String inspYn;
	private String takeYn;
	private String trkComList;
	private ArrayList<String> trkComList2; 
	private String wtErrRange;
	private String userPw;
	private String userZip;
	private String role;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String userEAddr;
	private String userEAddrDetail;
	private String comName;
	private String comEName;
	private String apiKey;
	private String stockWeightYn;
	private String storeUrl;
	private String StoreName;
	private String userUid;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private String eshopId;
	private String eshopApiKey;
	
	private String userEState;
	private String userECity;
	
	private String orgStation;
	
	public String encryptSHA256(String str) {
		String sha="";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for(int i =0; i< byteData.length; i++) {
				sb.append(Integer.toString((byteData[i]&0xff)+ 0x100, 16).substring(1));
			}
			sha = sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("encrypt Password Error!!");
			sha = null;
		}
		return sha;
	}
	
	public void encryptData() {
		try {
			if(userAddr != null)
				userAddr = AES256Cipher.AES_Encode(userAddr, symmetryKey);
			if(userAddrDetail != null)
				userAddrDetail =  AES256Cipher.AES_Encode(userAddrDetail, symmetryKey);
			if(userTel != null)
				userTel = AES256Cipher.AES_Encode(userTel, symmetryKey);
			if(userHp != null)
				userHp = AES256Cipher.AES_Encode(userHp, symmetryKey);
			if(userEmail != null)
			    userEmail = AES256Cipher.AES_Encode(userEmail, symmetryKey);
			if(userEAddr != null)
			    userEAddr= AES256Cipher.AES_Encode(userEAddr, symmetryKey);
			if(userEAddrDetail != null)
			    userEAddrDetail= AES256Cipher.AES_Encode(userEAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dncryptData() {
		try {
			if(userAddr != null)
				userAddr = AES256Cipher.AES_Decode(userAddr, symmetryKey);
			if(userAddrDetail != null)
				userAddrDetail =  AES256Cipher.AES_Decode(userAddrDetail, symmetryKey);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
