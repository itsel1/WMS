package com.example.temp.api.aci.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnExcelVO {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	public ReturnExcelVO() {
		
		nno = "";
		state = "";
		orgStation = "";
		
		shipperName = "";
		shipperTel = "";
		shipperHp = "";
		shipperZip = "";
		shipperAddr = "";
		shipperAddrDetail = "";
		
		pickupName = "";
		pickupTel = "";
		pickupHp = "";
		pickupZip = "";
		pickupAddr = "";
		pickupAddrDetail = "";

		cneeName = "";
		cneeTel = "";
		cneeHp = "";
		cneeZip = "";
		cneeAddr = "";
		cneeAddrDetail = "";
		payment = "";
		emsNo = "";
		orderNo = "";
		item = "";
		weight = "";
		size = "";
		boxCnt = 0;
		koblNo = "";
		returnType = "";
		sellerId = "";
		orderNo = "";
		trkCom = "";
		trkNo = "";
		trkWDate = "";
		hawbNo = "";
		orderDate = "";
		dstnNation = "";
		
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String nno;
	private String state;
	private String orgStation;
	
	private String shipperName;
	private String shipperTel;
	private String shipperHp;
	private String shipperZip;
	private String shipperAddr;
	private String shipperAddrDetail;

	private String pickupName;
	private String pickupTel;
	private String pickupHp;
	private String pickupZip;
	private String pickupAddr;
	private String pickupAddrDetail;
	
	private String cneeName;
	private String cneeTel;
	private String cneeHp;
	private String cneeZip;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String payment;
	private String emsNo;
	private String orderNo;
	private String item;
	private String weight;
	private String size;
	private int boxCnt;
	private String koblNo;
	private String returnType;
	private String sellerId;
	private String trkCom;
	private String trkNo;
	private String trkWDate;
	private String hawbNo;
	private String orderDate;
	private String dstnNation;
	private CommonsMultipartFile file = null;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(pickupAddr, symmetryKey);
			if(shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Encode(pickupAddrDetail, symmetryKey);
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Encode(pickupTel, symmetryKey);
			if(shipperHp != null)
				shipperHp = AES256Cipher.AES_Encode(pickupHp, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
	
	public void dncryptData() {
		try {
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(pickupAddr, symmetryKey);
			if(shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Decode(pickupAddrDetail, symmetryKey);
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Decode(pickupTel, symmetryKey);
			if(shipperHp != null)
				shipperHp = AES256Cipher.AES_Decode(pickupHp, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
	
}