package com.example.temp.common.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfPrintVO {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PdfPrintVO() {
		salesno="";
		orgStationName="";
		orgNationCode="";
		areano="";
		localno="";
		cityname="";
		guname="";
		dongname="";
		nno="";
		orgStation="";
		dstnNation="";
		dstnStation="";
		userId="";
		orderType="";
		orderNo="";
		hawbNo="";
		boxCnt="";
		userWta="";
		userWtc="";
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
		cneeAddrDetail="";
		userLength="";
		userWidth="";
		userHeight="";
		userEmail="";
		wUserId="";
		wUserIp="";
		wDate="";
		transCode="";
		orderDate="";
		whStatus="";
		cneeEmail="";
		customsNo="";
		nativeCneeName="";
		nativeCneeAddr="";
		nativeCneeAddrDetail="";
		itemCnt ="";
		comName = "";
		comEName = "";
		cneeDistrict = "";
		totalCnt = "";
		hawbNo2 = "";
		wtUnit = "";
		
		
		
		itemDetail="";
		
		
		dlvReqMsg="";
		
		orderText = "";
		
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String salesno;
	private String areano;
	private String localno;
	private String cityname;
	private String guname;
	private String dongname;
	private String nno;
	private String orgStation;
	private String orgStationName;
	private String orgNationCode;
	private String dstnNation;
	private String dstnStation;
	private String userId;
	private String orderType;
	private String orderNo;
	private String hawbNo;
	private String hawbNo2;
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
	private String whStatus;
	private String cneeEmail;
	private String customsNo;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String itemCnt;
	private String comName;
	private String comEName;
	private String totalCnt;
	private String trkNo;
	private String trkCom;
	private String wtUnit;
	private String orderText;
	private String itemDetail;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private String dlvReqMsg;
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
			logger.error("Exception", e);
		}
		
	}
	
	public void dncryptData() {
		try {
			if (nativeCneeName != null)	
				nativeCneeName = nativeCneeName.replaceAll("(\r\n|\r|\n|\n\r)", " ");
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
			if(nativeCneeAddr != null) {
				nativeCneeAddr=AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
				nativeCneeAddr = nativeCneeAddr.replaceAll("(\r\n|\r|\n|\n\r)", " ");;
			}
			if(nativeCneeAddrDetail != null) {
				nativeCneeAddrDetail=AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
				nativeCneeAddrDetail = nativeCneeAddrDetail.replaceAll("(\r\n|\r|\n|\n\r)", " ");;
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
}
