package com.example.temp.manager.vo.stockvo;
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
public class GroupStockVO{
	public GroupStockVO() {
		nno ="";
		orgStation="";
		cneeTel="";
		cneeHp="";
		cneeAddr="";
		cneeAddrDetail="";
		groupIdx= "";
		rackCode = "";
		cneeName="";
		fileDir = "";
		WhStatusName ="";
		whMemo = "";
		userMsg = "";
		adminMsg = "";
		userId= "";
		orderNo ="";
		hawbNo = "";
		fileCnt=1;
		orderReference="";
		stateKr="";
		state="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
	private String orgStation;
	private String cneeTel;
	private String cneeHp;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String cneeName;
	private String groupIdx;
	private String rackCode;
	private String fileDir;
	private String fileDir2;
	private String fileDir3;
	private String fileDir4;
	private String fileDir5;
	private String WhStatusName;
	private String whMemo;
	private String userMsg;
	private String adminMsg;
	private String userId;
	private String orderNo;
	private String hawbNo;
	private String newYn;
	private int fileCnt;
	private String orderReference;
	private String stateKr;
	private String state;

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

