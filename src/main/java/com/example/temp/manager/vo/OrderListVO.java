package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderListVO {
	public OrderListVO() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		nno="";
		orgStation="";
		orderNo= "";
		dstnNation="";
		dstnStation="";
		orderType="";
		userId="";
		hawbNo="";
		boxCnt="";
		cneeName="";
		cneeAddr="";
		cneeZip="";
		cneeTel="";
		cneeHp="";
		cneeCntry="";
		cneeCity="";
		cneeState="";
		cneeAddrDetail="";
		shipperName="";
		shipperAddr="";
		shipperAddrDetail="";
		shipperZip="";
		shipperTel="";
		shipperHp="";
		whReqMsg="";
		userLength="";
		userWidth="";
		userHeight="";
		cneeEmail="";
		wUserId=member.getUsername();
		wUserIp="";
		wDate="";
		userEmail="";
		nativeCneeName="";
		nativeCneeAddr="";
		nativeCneeAddrDetail="";
		customsNo="";
		userWta="";
		userWtc="";
		transCode="";
		mainItem="";
		totalValue="";
		startDate="";
		endDate="";
		cneeDistrict = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno;
	private String orderNo;
    private String orgStation;
    private String dstnNation;
    private String dstnStation;
    private String orderType;
    private String userId;
    private String hawbNo;
    private String boxCnt;
    private String shipperName;
    private String shipperAddr;
    private String shipperAddrDetail;
    private String shipperZip;
    private String shipperTel;
    private String shipperHp;
    private String whReqMsg;
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
    private String cneeEmail;
    private String nativeCneeName;
    private String nativeCneeAddr;
    private String nativeCneeAddrDetail;
    private String customsNo;
    private String userWta;
    private String userWtc;
    private String transCode;
    private String mainItem;
    private String totalValue;
    private String startDate;
    private String endDate;
    private String symmetryKey = keyVO.getSymmetryKey();
	
	private ArrayList<OrderInspVO> orderInspItem;
	
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
