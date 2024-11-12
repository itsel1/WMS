package com.example.temp.manager.vo.insp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
public class InspStockListVO{
	public InspStockListVO() {
		orderNo="";
		nno="";
		orgStation="";
		cneeTel="";
		cneeHp="";
		cneeAddr="";
		cneeAddrDetail="";
		cneeName="";
		cneeZip = "";
		wUserId="";
		wUserIp="";
		wDate="";
		groupIdx="";
		subNo="";
		itemDetail="";
		reqCnt="";
		totalWhCnt="";
		whCnt="";
		userId="";
		itemImgUrl="";
		whStatusName="";
		whMemo = "";
		stockNo = "";
		trkNo = "";
		trkCom="";
		adminMsg="";
		userMsg="";
		itemCnt = "";
		rackCode = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String orderNo;
	private String nno;
	private String orgStation;
	private String cneeTel;
	private String cneeHp;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String cneeZip;
	private String cneeName;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String groupIdx;
	private String subNo;
	private String itemDetail;
	private String reqCnt;
	private String totalWhCnt;
	private String whStatusName;
	private String whCnt;
	private String userId;
	private String itemImgUrl;
	private String inDate;
	private String transCode;
	private String whMemo;
	private String trkCom;
	private String trkNo;
	private String stockNo;
	private String adminMsg;
	private String userMsg;
	private String itemCnt;
	private String rackCode;
	private ArrayList<String> filePath;
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
	public String getInDate() {
		return this.getWDate().substring(0,10);
	}
}
