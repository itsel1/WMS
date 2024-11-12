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
public class UserOrderListVO2 {
	public UserOrderListVO2() {
		nno = "";
	    orgStation = "";
	    dstnNation = "";
	    dstnStation = "";
	    userId = "";
	    orderType = "";
	    orderNo = "";
	    hawbNo = "";
	    boxCnt = "";
	    userWta = "";
	    userWtc = "";
	    shipperName = "";
	    shipperZip = "";
	    shipperTel = "";
	    shipperHp = "";
	    shipperCntry = "";
	    shipperCity = "";
	    shipperState = "";
	    shipperAddr = "";
	    shipperAddrDetail = "";
	    cneeName = "";
	    cneeAddr = "";
	    cneeZip = "";
	    cneeTel = "";
	    cneeHp = "";
	    cneeCntry = "";
	    cneeCity = "";
	    cneeState = "";
	    cneeAddrDetail = "";
	    userLength = "";
	    userWidth = "";
	    userHeight = "";
	    userEmail = "";
	    wUserId = "";
	    wUserIp = "";
	    wDate = "";
	    transCode = "";
	    orderDate = "";
		status = "";
		nativeCneeAddr="";
	    nativeCneeName="";
	    nativeCneeAddrDetail="";
	    cneeEmail= "";
	    customsNo = "";
	}
	
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
    private String status;
    private String nativeCneeAddr;
    private String nativeCneeName;
    private String nativeCneeAddrDetail;
    private String cneeEmail;
    private String customsNo;
    
    @Value("${resources.symmetryKey}")
	private String symmetryKey;
	
    public String getCneeTel() {
		String value = this.cneeTel;
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
		String value = this.cneeAddr;
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
		String value = this.cneeAddrDetail;
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
		String value = this.cneeHp;
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
	
	public String getCneeEmail() {
		String value = this.cneeEmail;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setCneeEmail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.cneeEmail = value;
	}
	
	public String getUserEmail() {
		String value = this.userEmail;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setUserEmail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userEmail = value;
	}
	
	public String getCustomsNo() {
		String value = this.customsNo;
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
    
	
	public String getNativeCneeAddr() {
		String value = this.nativeCneeAddr;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setNativeCneeAddr(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.nativeCneeAddr = value;
	}
	
	public String getNativeCneeAddrDetail() {
		String value = this.nativeCneeAddrDetail;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setNativeCneeAddrDetail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.nativeCneeAddrDetail = value;
	}
    
    
    
}
