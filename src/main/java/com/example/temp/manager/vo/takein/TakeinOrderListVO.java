package com.example.temp.manager.vo.takein;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinOrderListVO {
	public TakeinOrderListVO() { 
		nno = "";
		num = "";
		rownum = "";
		orgStation = "";
		dstnNation = "";
		dstnStation = "";
		userId = "";
		orderType = "";
		orderNo = "";
		orderDate = "";
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
		transCode = "";
		cneeEmail = "";
		customsNo = "";
		nativeCneeName = "";
		nativeCneeAddr = "";
		nativeCneeAddrDetail = "";
		dimUnit = "";
		wtUnit = "";
		buySite = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		getBuy = "";
		mallType = "";
		whReqMsg = "";
		dlvReqMsg = "";
		cneeDistrict = "";
		itemDivCnt = "";
		totalItemValue = "";
		totalItemCnt = "";
		totalWta = "";
		cusItemCode = "";
		wtTakeUnit = "";
		per = "6000";
		comEName = "";
		comName = "";
		
		width = 0;
		length = 0;
		height = 0;
		cusItemCode2 = "";
		
		itemCnt = "1";
		rackCode = "";
		subNo = "1";
		rackName = "";
		
		rn = 0;
		rowspan = 0;
		takeInCode = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
	private String num;
	private String rownum;
	private String orgStation;
	private String dstnNation;
	private String dstnStation;
	private String userId;
	private String orderType;
	private String orderNo;
	private String orderDate;
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
	private String cneeAddrDetail;
	private String userLength;
	private String userWidth;
	private String userHeight;
	private String userEmail;
	private String transCode;
	private String cneeEmail;
	private String customsNo;
	private String nativeCneeName;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String dimUnit;
	private String wtUnit;
	private String buySite;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String getBuy;
	private String mallType;
	private String whReqMsg;
	private String dlvReqMsg;
	private String cneeDistrict;
	private String itemDivCnt;
	private String totalItemValue;
	private String totalWta;
	private String wtTakeUnit;
	private String totalItemCnt;
	private String cusItemCode;
	private String per;
	
	private String comEName;
	private String comName;
	
	private float width;
	private float height;
	private float length;
	
    private String symmetryKey = keyVO.getSymmetryKey();
	
	private ArrayList<TakeinOrderItemVO> takeinOrderItem;
	private ArrayList<HashMap<String, Object>> itemCodeMap;
	private String cusItemCode2;
	
	private String itemCnt;
	private String rackCode;
	private String subNo;
	private String rackName;
	
	private int rn;
	private int rowspan;
	private String takeInCode;
	
	public void encryptData() {
		try {
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
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			if(customsNo != null)
				customsNo=AES256Cipher.AES_Encode(customsNo, symmetryKey);
			
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dncryptData() {
		try {
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
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
			if(customsNo != null)
				customsNo=AES256Cipher.AES_Decode(customsNo, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
