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
import org.springframework.beans.factory.annotation.Value;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SagawaVO2 {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SagawaVO2() {
		salesno="";
		areano="";
		localno="";
		cityname="";
		guname="";
		dongname="";
		nno="";
		orgStation="";
		dstnNation="";
		dstnStation="";
		userId="";
		orderType="";
		orderNo="";
		hawbNo="";
		boxCnt="";
		userWta="";
		userWtc="";
		shipperName="";
		shipperZip="";
		shipperTel="";
		shipperHp="";
		shipperCntry="";
		shipperCity="";
		shipperState="";
		shipperAddr="";
		shipperAddrDetail="";
		cneeName="";
		cneeAddr="";
		cneeZip="";
		cneeTel="";
		cneeHp="";
		cneeCntry="";
		cneeCity="";
		cneeState="";
		cneeAddrDetail="";
		userLength="";
		userWidth="";
		userHeight="";
		userEmail="";
		wUserId="";
		wUserIp="";
		wDate="";
		transCode="";
		orderDate="";
		whStatus="";
		cneeEmail="";
		customsNo="";
		nativeCneeName="";
		nativeCneeAddr="";
		nativeCneeAddrDetail="";
		itemCnt ="";
	}
	
	private String salesno;
	private String areano;
	private String localno;
	private String cityname;
	private String guname;
	private String dongname;
	private String nno;
	private String orgStation;
	private String dstnNation;
	private String dstnStation;
	private String userId;
	private String orderType;
	private String orderNo;
	private String hawbNo;
	private String boxCnt;
	private String userWta;
	private String userWtc;
	private String shipperName;
	private String shipperZip;
	private String shipperTel;
	private String shipperHp;
	private String shipperCntry;
	private String shipperCity;
	private String shipperState;
	private String shipperAddr;
	private String shipperAddrDetail;
	private String cneeName;
	private String cneeAddr;
	private String cneeZip;
	private String cneeTel;
	private String cneeHp;
	private String cneeCntry;
	private String cneeCity;
	private String cneeState;
	private String cneeAddrDetail;
	private String userLength;
	private String userWidth;
	private String userHeight;
	private String userEmail;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String transCode;
	private String orderDate;
	private String whStatus;
	private String cneeEmail;
	private String customsNo;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String itemCnt;
	@Value("${resources.symmetryKey}")
	private String symmetryKey;
	
	public String getCneeTel() {
		String value = cneeTel;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
		return value;
	}
	
	public void setCneeTel(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
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
			logger.error("Exception", e);
		}
		return value;
	}
	
	public void setCneeAddr(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
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
			logger.error("Exception", e);
		}
		return value;
	}
	
	public void setCneeAddrDetail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
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
			logger.error("Exception", e);
		}
		return value;
		
	}
	
	public void setCneeHp(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}	
		this.cneeHp = value;
	}
	
	public String getCneeEmail() {
		String value = cneeEmail;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
		return value;
	}
	
	public void setCneeEmail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}	
		this.cneeEmail = value;
	}
	
	public String getCustomsNo() {
		String value = customsNo;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
		return value;
	}
	
	public void setCustomsNo(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}	
		this.customsNo = value;
	}
}
