package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ManagerVO3 {
	public ManagerVO3() {
		idx= 0;
		userId="";
		userName="";
		userTel="";
		userAddr="";
		userAddrDetail="";
		userEmail="";
		etprYn="";
		aprvYn="";
		depositYn="";
		inspPrice="";
		userGrade="";
		inspYn="";
		takeYn="";
		trkComList="";
		trkComList2= new ArrayList<String>(); 
		wtErrRange="";
		userPw="";
		userZip="";
		role="";
		wUserId="";
		wUserIp="";
		wDate="";
		userEAddr="";
		userEAddrDetail="";
		comName="";
		comEName="";
	}
	
	
	private int idx;
	private String userId;
	private String userName;
	private String userTel;
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
	
	private String symmetryKey = "abcdefghijklmnopqrstuvwxyz123456";
	
	public String getUserTel() {
		String value = this.userTel;
		try {
			value = AES256Cipher.AES_Decode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		}
		return value;
	}
	
	public void setUserTel(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userTel = value;
	}
	
	public String getUserAddr() {
		String value = this.userAddr;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setUserAddr(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userAddr = value;
	}
	
	public String getUserAddrDetail() {
		String value = this.userAddrDetail;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setUserAddrDetail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userAddrDetail = value;
	}
	
	public String getUserEmail() {
		String value = this.userEmail;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
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
	
	public String getUserEAddr() {
		String value = this.userEAddr;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setUserEAddr(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userEAddr = value;
	}
	
	public String getUserEAddrDetail() {
		String value = this.userEAddrDetail;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setUserEAddrDetail(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.userEAddrDetail = value;
	}
	public String getComName() {
		String value = this.comName;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setComName(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.comName = value;
	}
	
	public String getComEName() {
		String value = this.comEName;
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setComEName(String value) {
		try {
			value = AES256Cipher.AES_Encode(value, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.comEName = value;
	}
	
	
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
}
