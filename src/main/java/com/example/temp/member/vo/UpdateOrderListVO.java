package com.example.temp.member.vo;

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
import oracle.security.crypto.core.AES;

@Setter
@Getter
public class UpdateOrderListVO {
	
	public UpdateOrderListVO() {
		nno="";
		orderNo="";
		orderDate="";
		hawbNo="";
		boxCnt="";
		shipperName="";
		shipperAddr="";
		cneeName="";
		cneeAddr="";
		cneeZip="";
		cneeTel="";
		cneeHp="";
		wUserId="";
		wUserIp="";
		wDate="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String nno;
	private String orderNo;
	private String orderDate;
	private String hawbNo;
	private String boxCnt;
	private String shipperName;
	private String shipperAddr;
	private String cneeName;
	private String cneeAddr;
	private String cneeZip;
	private String cneeTel;
	private String cneeHp;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	
	public void encryptData() {
		try {
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel = AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp = AES256Cipher.AES_Encode(cneeHp, symmetryKey);
				
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dncryptData() {
		try {
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel = AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp = AES256Cipher.AES_Decode(cneeHp, symmetryKey);
				
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
