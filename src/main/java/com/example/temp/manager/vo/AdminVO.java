package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class AdminVO {
	public AdminVO() {
		adminId="";
		adminName="";
		adminTel="";
		adminEmail="";
		adminPw="";
		adminHp="";
		orgStation="";
		wUserId="";
		wUserIp="";
		wDate="";
		role="";
		useYn="";
		
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String adminId;
	private String adminName;
	private String adminTel;
	private String adminEmail;
	private String adminPw;
	private String adminHp;
	private String orgStation;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String role;
	private String useYn;
	private String symmetryKey = keyVO.getSymmetryKey();
	
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
			System.out.println("encrypt Password Error!!");
			sha = null;
		}
		return sha;
	}
	/*
	 * public void encryptData() { try { if(adminPw != null) adminPw =
	 * AES256Cipher.AES_Encode(adminPw, symmetryKey); } catch (InvalidKeyException |
	 * UnsupportedEncodingException | NoSuchAlgorithmException |
	 * NoSuchPaddingException | InvalidAlgorithmParameterException |
	 * IllegalBlockSizeException | BadPaddingException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public void dncryptData() { try { if(adminPw != null) adminPw =
	 * AES256Cipher.AES_Decode(adminPw, symmetryKey); } catch (InvalidKeyException |
	 * UnsupportedEncodingException | NoSuchAlgorithmException |
	 * NoSuchPaddingException | InvalidAlgorithmParameterException |
	 * IllegalBlockSizeException | BadPaddingException e) { e.printStackTrace(); } }
	 */
}