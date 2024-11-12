package com.example.temp.member.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

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
public class ReturnOrderListVO {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ReturnOrderListVO() {
		nno = "";
		orgStation = "";
		dstnNation = "";
		dstnStation = "";
		userId = "";
		orderType = "";
		orderNo = "";
		orderDate = "";
		pickType = "";
		returnType = "";
		koblNo = "";
		trkCom = "";
		trkNo = "";
		trkDate = "";
		whMsg = "";
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
		cneeState = "";
		cneeCity = "";
		cneeAddrDetail = "";
		nativeCneeName = "";
		nativeCneeAddr = "";
		nativeCneeAddrDetail = "";
		attnName = "";
		attnTel = "";
		attnEmail = "";
		returnReason = "";
		returnReasonDetail = "";
		taxType = "";
		taxReturn = "";
		state = "";
		WUserId = "";
		WUserIp = "";
		WDate = "";
		food = "";
		cdRemark = "";
		itemImgUrl = "";
		stateName = "";
		siteType = "";
		
		orderReference = "";
		calculateId = "";
		dlvReqMsg = "";
		
		whInDate = "";
		
		totalItemCnt = 0;
		
		whStatus = "";
		whStatusSub = "";
		whStatusDetail = "";
		
		whinCnt = 0;
		
		hawbNo = "";
		agencyBl = "";
		transCode = "";
		
		boxCnt = 0;
		wta = 0;
		wtc = 0;
		mawbNo = "";
		
		tmpChk = 0;
		groupIdx = "";
		
		userMemo = "";
		adminMemo = "";
		
		fileCnt = 0;
		
		width = 0;
		height = 0;
		length = 0;
	}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String nno;
	private String orgStation;
	private String dstnNation;
	private String dstnStation;
	private String userId;
	private String orderType;
	private String orderNo;
	private String orderDate;
	private String pickType;
	private String returnType;
	private String koblNo;
	private String trkCom;
	private String trkNo;
	private String trkDate;
	private String whMsg;
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
	private String cneeState;
	private String cneeCity;
	private String cneeAddrDetail;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String attnName;
	private String attnTel;
	private String attnEmail;
	private String returnReason;
	private String returnReasonDetail;
	private String taxType;
	private String taxReturn;
	private String state;
	private String WUserId;
	private String WUserIp;
	private String WDate;
	private String food;
	private String cdRemark;
	private String itemImgUrl;
	private String stateName;
	private String siteType;
	
	// 우체국 업로드 자료를 위한 변수
	private String itemDetail;
	//private float itemWta;
	private int itemCnt;
	
	// api 추가
	private String orderReference;
	private String calculateId;
	private String dlvReqMsg;
	
	// 2023.06.21 입고일 추가
	private String whInDate;
	
	private int totalItemCnt;
	private String whStatus;
	private String whStatusSub;
	private String whStatusDetail;
	
	private int whinCnt;
	
	private String hawbNo;
	private String agencyBl;
	private String transCode;
	
	private int boxCnt;
	private double wta;
	private double wtc;
	private String mawbNo;
	
	private int tmpChk;
	private String groupIdx;
	
	private String userMemo;
	private String adminMemo;
	
	private int fileCnt;
	

	private float width;
	private float length;
	private float height;
	
	
	ArrayList<HashMap<String, Object>> itemList;
	
	public void encrypData() {
		try {
			if (shipperAddr != null) {
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			}
			if (shipperAddrDetail != null) {
				shipperAddrDetail = AES256Cipher.AES_Encode(shipperAddrDetail, symmetryKey);
			}
			if (shipperTel != null) {
				shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
			}
			if (shipperHp != null) {
				shipperHp = AES256Cipher.AES_Encode(shipperHp, symmetryKey);
			}
			if (cneeAddr != null) {
				cneeAddr = AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			}
			if (cneeTel != null) {
				cneeTel = AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			}
			if (cneeHp != null) {
				cneeHp = AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			}
			if (cneeAddrDetail != null) {
				cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			}
			if (nativeCneeAddr != null) {
				nativeCneeAddr = AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			}
			if (nativeCneeAddrDetail != null) {
				nativeCneeAddrDetail = AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			}
			if (attnTel != null) {
				attnTel = AES256Cipher.AES_Encode(attnTel, symmetryKey);
			}
			if (attnEmail != null) {
				attnEmail = AES256Cipher.AES_Encode(attnEmail, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
	
	public void dncryptData() {
		try {
			if (shipperAddr != null) {
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			}
			if (shipperAddrDetail != null) {
				shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
			}
			if (shipperTel != null) {
				shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
			}
			if (shipperHp != null) {
				shipperHp = AES256Cipher.AES_Decode(shipperHp, symmetryKey);
			}
			if (cneeAddr != null) {
				cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			}
			if (cneeTel != null) {
				cneeTel = AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			}
			if (cneeHp != null) {
				cneeHp = AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			}
			if (cneeAddrDetail != null) {
				cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			}
			if (nativeCneeAddr != null) {
				nativeCneeAddr = AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			}
			if (nativeCneeAddrDetail != null) {
				nativeCneeAddrDetail = AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
			}
			if (attnTel != null) {
				attnTel = AES256Cipher.AES_Decode(attnTel, symmetryKey);
			}
			if (attnEmail != null) {
				attnEmail = AES256Cipher.AES_Decode(attnEmail, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
}
