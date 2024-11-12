package com.example.temp.api.ecommerce.dto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Data;

@Data
public class Order {
	
	private String nno;
	private String orgStation;
	private String dstnNation;
	private String dstnStation;
	private String orgNation;
	private String userId;
	private String orderType;
	private String orderNo;
	private String hawbNo;
	private int boxCnt;
	private double userWta;
	private double userWtc;
	private String shipperName;
	private String shipperZip;
	private String shipperTel;
	private String shipperHp;
	private String shipperCntry;
	private String shipperCity;
	private String shipperState;
	private String shipperAddr;
	private String shipperAddrDetail;
	private String shipperReference;
	private String shipperTaxNo;
	private String shipperTaxType;
	private String cneeName;
	private String cneeAddr;
	private String cneeZip;
	private String cneeTel;
	private String cneeHp;
	private String cneeCntry;
	private String cneeCity;
	private String cneeState;
	private String cneeDistrict;
	private String cneeWard;
	private String cneeAddrDetail;
	private String cneeReference1;
	private String cneeReference2;
	private String cneeTaxNo;
	private String cneeTaxType;
	private double userLength;
	private double userWidth;
	private double userHeight;
	private String userEmail;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String transCode;
	private String orderDate;
	private String status;
	private String cneeEmail;
	private String customsNo;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String dimUnit;
	private String wtUnit;
	private String buySite;
	private String getBuy;
	private String mallType;
	private String whReqMsg;
	private String dlvReqMsg;
	private String payment;
	private String food;
	private int declType;
	private String uploadType;
	private String cosmetic;
	private String sign;
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	ArrayList<Item> itemList;
	
	private String fulfillOrderId;
	private String orderId;

	public Order() {
		nno = "";
		orgStation = "";
		dstnNation = "";
		dstnStation = "";
		orgNation = "";
		userId = "";
		orderType = "";
		orderNo = "";
		hawbNo = "";
		boxCnt = 1;
		userWta = 0;
		userWtc = 0;
		shipperName = "";
		shipperZip = "";
		shipperTel = "";
		shipperHp = "";
		shipperCntry = "";
		shipperCity = "";
		shipperState = "";
		shipperAddr = "";
		shipperAddrDetail = "";
		shipperReference = "";
		shipperTaxNo = "";
		shipperTaxType = "";
		cneeName = "";
		cneeAddr = "";
		cneeZip = "";
		cneeTel = "";
		cneeHp = "";
		cneeCntry = "";
		cneeCity = "";
		cneeState = "";
		cneeDistrict = "";
		cneeWard = "";
		cneeAddrDetail = "";
		cneeReference1 = "";
		cneeReference2 = "";
		cneeTaxNo = "";
		cneeTaxType = "";
		userLength = 0;
		userWidth = 0;
		userHeight = 0;
		userEmail = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		transCode = "";
		orderDate = "";
		status = "";
		cneeEmail = "";
		customsNo = "";
		nativeCneeName = "";
		nativeCneeAddr = "";
		nativeCneeAddrDetail = "";
		dimUnit = "";
		wtUnit = "";
		buySite = "";
		getBuy = "";
		mallType = "";
		whReqMsg = "";
		dlvReqMsg = "";
		payment = "";
		food = "";
		declType = 4;
		uploadType = "";
		cosmetic = "";
		sign = "";
		itemList = new ArrayList<Item>();
		
		fulfillOrderId = "";
		orderId = "";
	}
	
	 public void encryptData() {
		try {
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
			if(shipperHp != null)
				shipperHp = AES256Cipher.AES_Encode(shipperHp, symmetryKey);
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			if(shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Encode(shipperAddrDetail, symmetryKey);
			if(cneeAddr != null)
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel =  AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp =  AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			if(userEmail != null)
				userEmail = AES256Cipher.AES_Encode(userEmail, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr= AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail= AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			if(cneeEmail != null)
				cneeEmail= AES256Cipher.AES_Encode(cneeEmail, symmetryKey);
			if(customsNo != null)
				customsNo = AES256Cipher.AES_Encode(customsNo, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public void dncryptData() {
		try {
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
			if(shipperHp != null)
				shipperHp = AES256Cipher.AES_Decode(shipperHp, symmetryKey);
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			if(shipperAddrDetail != null)
				shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
			if(cneeAddr != null)
				cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel =  AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp =  AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			if(userEmail != null)
				userEmail = AES256Cipher.AES_Decode(userEmail, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr= AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail= AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
			if(cneeEmail != null)
				cneeEmail= AES256Cipher.AES_Decode(cneeEmail, symmetryKey);
			if(customsNo != null)
				customsNo = AES256Cipher.AES_Decode(customsNo, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}
	}
}
