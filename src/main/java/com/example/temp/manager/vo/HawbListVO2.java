package com.example.temp.manager.vo;

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
public class HawbListVO2 {
	public HawbListVO2() {
		userId= "";
		userName= "";
		orgStation= "";
		transCode= "";
		hawbNo= "";
		dstnNation= "";
		orderNo= "";
		cneeName= "";
		customsNo= "";
		cneeTel= "";
		cneeHp= "";
		cneeCntry= "";
		cneeState= "";
		cneeAddr= "";
		cneeAddrDetail= "";
		wta= "";
		wtc= "";
		wUserId = "";
		wUserIp = "";
		boxCnt = "";
		shipperName="";
	}
	
	
	private String userId;
	private String userName;
	private String orgStation;
	private String transCode;
	private String hawbNo;
	private String dstnNation;
	private String orderNo;
	private String cneeName;
	private String customsNo;
	private String cneeTel;
	private String cneeHp;
	private String cneeCntry;
	private String cneeState;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String wta;
	private String wtc;
	private String wUserId;
	private String wUserIp;
	private String boxCnt;
	private String shipperName;
	@Value("${resources.symmetryKey}")
	private String symmetryKey;
	
	
	public String getCustomsNo() {
		String value = customsNo;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setCustomsNo(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.customsNo = value;
	}
	
	public String getCneeTel() {
		String value = cneeTel;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setCneeTel(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.cneeTel = value;
	}
	
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
	
	public String getCneeHp() {
		String value = cneeHp;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
		
	}
	
	public void setCneeHp(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.cneeHp = value;
	}
}
