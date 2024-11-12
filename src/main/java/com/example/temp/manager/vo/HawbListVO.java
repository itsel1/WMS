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
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HawbListVO {
	public HawbListVO() {
		nno = "";
		userId= "";
		userName= "";
		orgStation= "";
		transCode= "";
		transName = "";
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
		comEName ="";
		comName = ""; 
		cneeDistrict = "";
		checkCnt = 0;
		agencyBl = "";
				
	}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
	private String userId;
	private String userName;
	private String orgStation;
	private String transCode;
	private String transName;
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
	private String comEName;
	private String comName;
	private String shipperName;
	private String cneeDistrict;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private int checkCnt;
	private String agencyBl;
	
	public void encryptData() {
		try {
			cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			cneeHp=AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			cneeAddrDetail=AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			customsNo=AES256Cipher.AES_Encode(customsNo, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dncryptData() {
		try {
			cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			cneeHp=AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			cneeAddrDetail=AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			customsNo=AES256Cipher.AES_Decode(customsNo, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
