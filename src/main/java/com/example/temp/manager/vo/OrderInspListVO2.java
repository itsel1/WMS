package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.MemberVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInspListVO2 {
	public OrderInspListVO2() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	    wUserId = member.getUsername();
	    wUserIp = "";
	    wDate = "";
	    transCode = "";
	    orderDate = "";
		status = "";
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
    private String cneeEmail;
    private String nativeCneeAddr;
    private String nativeCneeAddrDetail;
    private String customsNo;
	
	private ArrayList<OrderInspVO> orderInspItem;
	
	@Value("${resources.symmetryKey}")
	private String symmetryKey;
	
public String getCneeTel() {
		
		return this.cneeTel;
	}
	
	public void setCneeTel(String value) {
			try {
				value = AES256Cipher.AES_Encode(value, symmetryKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
					| BadPaddingException e) {
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
	
	public String getCneeEmail() {
		String value = cneeEmail;
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
		String value = userEmail;
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
    
	
	public String getNativeCneeAddr() {
		String value = nativeCneeAddr;
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
		String value = nativeCneeAddrDetail;
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
