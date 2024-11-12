package com.example.temp.manager.vo;

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
public class ManifestVO {

	public ManifestVO() {
		nno="";
		orgStation="";
		mawbNo="";
		hawbNo="";
		boxCnt=0;
		wta="";
		wtc="";
		itemDetail="";
		wDate="";
		origin="";
		itemCnt=0;
		unitValue="";
		unitCurrency="";
		hsCode="";
		transCode="";
		shipperName="";
		shipperZip="";
		shipperTel="";
		shipperHp="";
		shipperCntry="";
		shipperCity="";
		shipperState="";
		shipperAddr="";
		shipperAddrDetail="";
		cneeName="";
		cneeAddr="";
		cneeZip="";
		cneeTel="";
		cneeHp="";
		cneeCntry="";
		cneeCity="";
		cneeState="";
		cneeDistrict="";
		cneeAddrDetail="";
		userLength="";
		userWidth="";
		userHeight="";
		userEmail="";
		transName="";
		orderDate="";
		status="";
		nativeCneeAddr="";
		nativeCneeName="";
		nativeCneeAddrDetail="";
		code="";
		

	    orgNation="";
	    dstnNation="";
	    depDate="";
	    itemValue="";
	    chgCurrency="";
	    

	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
    private String orgStation;
    private String mawbNo;
    private String hawbNo;
    private int boxCnt;
    private String wta;
    private String wtc;
    private String itemDetail;
    private String wDate;
    private String origin;
    private int itemCnt;
    private String unitValue;
    private String unitCurrency;
    private String hsCode;
    private String transCode;
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
    private String transName;
    private String orderDate;
    private String status;
    private String nativeCneeAddr;
    private String nativeCneeName;
    private String nativeCneeAddrDetail;
    private String code;
    
    private String orgNation;
    private String dstnNation;
    private String depDate;
    private String itemValue;
    private String chgCurrency;
    
    
    private String symmetryKey = keyVO.getSymmetryKey();


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
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
