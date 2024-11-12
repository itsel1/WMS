package com.example.temp.member.vo.takein;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinDeleteVO {
	
	public TakeinDeleteVO(){
		nno = "";
		orgStation ="";
		stationName = "";
		dstnNation ="";
		nationName ="";
		userId ="";
		orderType ="";
		orderNo = "";
		orderDate ="";
		hawbNo = "";
		userWta = "";
		userWtc = "";
		cneeCity = "";
		cneeState = "";
		cneeName = "";
		cneeZip = "";
		cneeAddr = "";
		cneeAddrDetail = "";	
	}
	
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String nno;
	private String orgStation;
	private String stationName;
	private String dstnNation;
	private String nationName;
	private String userId;
	private String orderType;
	private String orderNo;
	private String orderDate;
	private String hawbNo;
	private String userWta;
	private String userWtc;
	private String cneeCity;
	private String cneeState;
	private String cneeName;
	private String cneeTel;
	private String cneeHp;
	private String cneeZip;
	private String cneeAddr;
	private String cneeAddrDetail;

	
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void dncryptData() {
		try {
			if(cneeTel != null) {
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			}
			if(cneeHp != null) {
				cneeHp=AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			}
			if(cneeAddr != null) {
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			}
			if(cneeAddrDetail != null) {
				cneeAddrDetail=AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}

}
