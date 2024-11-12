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

@Setter
@Getter
public class UserGroupStockVO {
	
	public UserGroupStockVO() {
		rownum = "";
		nno = "";
		userId = "";
		groupIdx = "";
		rackCode = "";
		stationName = "";
		orgStation = "";
		orderNo = "";
		hawbNo = "";
		cneeName = "";
		cneeTel = "";
		cneeHp = "";
		cneeAddr = "";
		cneeAddrDetail = "";
		groupCode = "";
		whStatus = "";
		whStatusName = "";
		whInDate = "";
		whCnt = "";
		whMemo = "";
		userMsg = "";
		adminMsg = "";
		fileDir = "";
		fileDir2 = "";
		fileDir3 = "";
		fileDir4 = "";
		fileDir5 = "";
	}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String rownum;
	private String nno;
	private String userId;
	private String groupIdx;
	private String rackCode;
	private String stationName;
	private String orgStation;
	private String orderNo;
	private String hawbNo;
	private String cneeName;
	private String cneeTel;
	private String cneeHp;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String groupCode;
	private String whStatus;
	private String whStatusName;
	private String whInDate;
	private String whCnt;
	private String whMemo;
	private String userMsg;
	private String adminMsg;
	private String fileDir;
	private String fileDir2;
	private String fileDir3;
	private String fileDir4;
	private String fileDir5;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			cneeHp=AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			cneeAddrDetail=AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
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
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
