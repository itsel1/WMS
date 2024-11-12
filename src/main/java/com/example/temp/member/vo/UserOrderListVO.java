package com.example.temp.member.vo;

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
import oracle.security.crypto.core.AES;

@Setter
@Getter
public class UserOrderListVO {
	public UserOrderListVO() {
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
	    wUserId = "";
	    wUserIp = "";
	    wDate = "";
	    transCode = "";
	    orderDate = "";
		status = "";
		nativeCneeAddr="";
	    nativeCneeName="";
	    nativeCneeAddrDetail="";
	    cneeEmail= "";
	    customsNo = "";
	    types="";
	    transName = "";
	    whReqMsg = "";
	    dlvReqMsg = "";
	    cneeDistrict = "";
	    payment="";
	    shipperReference="";
	    cneeReference1="";
	    cneeReference2="";
	    food="";
	    nationalIdDate = "";				//
		nationalIdAuthority = "";			//
		cneeBirth = "";					//
		taxNo = "";				
		expValue = "noExplicence";
		sendYn="";
		expBusinessNum="";
		expBusinessName="";
		expShipperCode="";
		
		agencyBusinessName="";
		simpleYn="";
		expNo="";
		
		wta="";
		wtc="";
		buySite="";
		unitValue="";
		
		itemDetail = "";
		totalItemCnt = 0;
		
		errName="";
		
		unitCurrency="";
		companyName="";
		
		taxId="";
		eoriNo="";
		declType="4";
		uploadType="";
		getBuy="";
		expYn=0;
		
		
		// 2024.03.22 수출신고 변경으로 추가
		expType = "";
		expCor = "";
		expRprsn = "";
		expAddr = "";
		expZip = "";
		expRgstrNo = "";
		expCstCd = "";
		agtCor = "";
		agtCstCd = "";
		agtBizNo = "";
		
		chgCurrency = "";
		cusItemCode = "";
		subNo = "";
		totalItemValue = "";
		rownum = 1;
		
		iossNo = "";
		
		shipperEmail = "";
		
		cneeWard = "";
		
		
		cosmetic = "";
		sign = "";
		shipperTaxType = "";
		shipperTaxNo = "";
		cneeTaxType = "";
		cneeTaxNo = "";
	}
	
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
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
    private String cneeDistrict;
    private String cneeAddrDetail;
    private String userLength;
    private String userWidth;
    private String userHeight;
    private String userEmail;
    private String transCode;
    private String transName;
    private String orderDate;
    private String status;
    private String nativeCneeAddr;
    private String nativeCneeName;
    private String nativeCneeAddrDetail;
    private String cneeEmail;
    private String customsNo;
    private String dimUnit;
    private String wtUnit;
    private String buySite;
    private String getBuy;
    private String mallType;
    private String whReqMsg;
    private String dlvReqMsg;
    private String types;
    private String payment;
    private String shipperReference;
    private String cneeReference1;
    private String cneeReference2;
    private String food;
    private String nationalIdDate;				//
	private String nationalIdAuthority;			//
	private String cneeBirth;					//
	private String taxNo;						//
	private String expValue;				// 수출신고 관련
	private String sendYn;
	private String expBusinessNum;
	private String expBusinessName;
	private String expShipperCode;
	private String expNo;
	private String simpleYn;
	
	private String agencyBusinessName;		// 수출대행자사업자명
    
    private String wUserId;
    private String wUserIp;
    private String wDate;
    private String symmetryKey = keyVO.getSymmetryKey();
    
    private String wta;
    private String wtc;
    private String unitValue;


    private String itemDetail;
    private String itemCnt;
    
    private String errName;
    
    private int totalItemCnt;
    
    private String unitCurrency;
    private String companyName;
    
    private String taxId;
    private String eoriNo;
    private String declType;
    private String uploadType;
    private int expYn;
    
    // 2024.03.22 수출신고 변경으로 추가
    private String expType;
    private String expCor;
    private String expRprsn;
    private String expAddr;
    private String expZip;
    private String expRgstrNo;
    private String expCstCd;
    private String agtCor;
    private String agtCstCd;
    private String agtBizNo;

    private String chgCurrency;
    private String cusItemCode;
    
    private String subNo;
    private String totalItemValue;
    private int rownum;
    
    private String iossNo; 
    private String shipperEmail;
    
    private String cneeWard;
    
    
    private String cosmetic;
    private String sign;
    private String shipperTaxType;
    private String shipperTaxNo;
    private String cneeTaxType;
    private String cneeTaxNo;
    

    
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
			
			// 2024.03.22 수출신고 변경으로 추가
			if(expAddr != null)
				expAddr = AES256Cipher.AES_Encode(expAddr, symmetryKey);
			if(expRgstrNo != null)
				expRgstrNo = AES256Cipher.AES_Encode(expRgstrNo, symmetryKey);
			if(expCstCd != null)
				expCstCd = AES256Cipher.AES_Encode(expCstCd, symmetryKey);
			if(agtCstCd != null)
				agtCstCd = AES256Cipher.AES_Encode(agtCstCd, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			// 2024.03.22 수출신고 변경으로 추가
			if(expAddr != null)
				expAddr = AES256Cipher.AES_Decode(expAddr, symmetryKey);
			if(expRgstrNo != null)
				expRgstrNo = AES256Cipher.AES_Decode(expRgstrNo, symmetryKey);
			if(expCstCd != null)
				expCstCd = AES256Cipher.AES_Decode(expCstCd, symmetryKey);
			if(agtCstCd != null)
				agtCstCd = AES256Cipher.AES_Decode(agtCstCd, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void encryptShipperAddr() {
		try {
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	
	public void encryptCneeAddr() {
		try {
			if(cneeAddr != null)
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void encryptCneeTel() {
		try {
			if(cneeTel != null)
				cneeTel = AES256Cipher.AES_Encode(cneeTel, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void encryptCneeHp() {
		try {
			if(cneeHp != null)
				cneeHp = AES256Cipher.AES_Encode(cneeHp, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
