package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInspListVO {
	public OrderInspListVO() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	    cneeDistrict = "";
	    cneeAddrDetail = "";
	    userLength = "";
	    userWidth = "";
	    userHeight = "";
	    userEmail = "";
	    wUserId = member.getUsername();
	    wUserIp = "";
	    wDate = "";
	    transCode = "";
	    orderDate = "";
		status = "";
		subNo="";
		wtUnit="";
		takeInCode="";
		trkCom="";
		trkNo="";
		cusItemCode="";

		nationCode="";
		hsCode="";
		itemDetail="";
		itemCnt="";
		unitValue="";
		brand="";
		makeCntry="";
		makeCom="";
		itemDiv="";
		qtyUnit="";
		packageUnit="";
		exchangeRate="";
		chgCurrency="";
		chgAmt="";
		chnItemDetail="";
		itemMeterial="";
		itemUrl="";
		itemImgUrl="";
		trkDate="";
		whReqMsg = "";
		blNo2 ="";
		
		totalValue = "";
		wta = "";
		wtc = "";
		agencyBl = "";
		
		rnum = 0;
	}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
	private String blNo2;
    private String orgStation;
    private String dstnNation;
    private String dstnStation;
    private String userId;
    private String orderType;
    private String orderNo;
    private String hawbNo;
    private String orgHawbNo;
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
    private String wUserId;
    private String wUserIp;
    private String wDate;
    private String transCode;
    private String orderDate;
    private String status;
    private String cneeEmail;
    private String nativeCneeName;
    private String nativeCneeAddr;
    private String nativeCneeAddrDetail;
    private String customsNo;
    
    private String orgStationName;
    private String dstnNationName;
    private String whReqMsg;
    
  //COMMON
  	private String subNo;
  	private String wtUnit;
  	private String takeInCode;
  	private String trkCom;
  	private String trkNo;
  	private String cusItemCode;
  	
  	//ITEM
  	private String nationCode;
  	private String hsCode;
  	private String itemDetail;
  	private String itemCnt;
  	private String unitValue;
  	private String brand;
  	private String makeCntry;
  	private String makeCom;
  	private String itemDiv;
  	private String qtyUnit;
  	private String packageUnit;
  	private String exchangeRate;
  	private String chgCurrency;
  	private String chgAmt;
  	private String chnItemDetail;
  	private String itemMeterial;
  	private String itemUrl;
  	private String itemImgUrl;
  	private String trkDate;
    
  	//RUS
  	private String itemWta;
  	private String itemExplan;
  	private String nationalIdDate;
  	private String nationalIdAuthority;
  	private String cneeBirth;
  	private String taxNo;
  	private String orgNation;
  	private String itemBarcode;
  	private String inBoxNum;
    
  	private String totalValue;
  	private String wta;
  	private String wtc;
  	
  	private String agencyBl;
  	
    private String symmetryKey = keyVO.getSymmetryKey();
    
    private int rnum;
	
	
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
				cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp=AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail=AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			if(userEmail != null)
				userEmail=AES256Cipher.AES_Encode(userEmail, symmetryKey);
			if(cneeEmail != null)
				cneeEmail=AES256Cipher.AES_Encode(cneeEmail, symmetryKey);
			if(customsNo != null)
				customsNo=AES256Cipher.AES_Encode(customsNo, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
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
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp=AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail=AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			if(userEmail != null)
				userEmail=AES256Cipher.AES_Decode(userEmail, symmetryKey);
			if(cneeEmail != null)
				cneeEmail=AES256Cipher.AES_Decode(cneeEmail, symmetryKey);
			if(customsNo != null)
				customsNo=AES256Cipher.AES_Decode(customsNo, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
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