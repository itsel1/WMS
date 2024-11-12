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

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShpngListVO {
	public ShpngListVO () {
		userId="";
		companyName="";
		nno="";
		cneeName="";
		cneeZip="";
		cneeAddr="";
		cneeAddrDetail="";
		cneeTel="";
		cneeHp="";
		subNo="";
		hsCode="";
		itemDetail="";
		unitValue="";
		itemCnt="";
		itemValue="";
		orderDate="";
		delDate="";
		recDate="";
		resultCode="";
		dno="";
		orderNo="";
		status="";
		userWidth="";
		userLength="";
		userHeight="";
		hawbNo="";
		nativeCneeAddr="";
		nativeCneeName="";
		nativeCneeAddrDetail="";
		cneeEmail="";
		customsNo="";
		boxCnt="";
		userWta="";
		userWtc="";
		transCode="";
		transName="";
		itemMeterial="";
		makeCntry="";
		errName="";
		errorMsg = "";
		itemImgUrl = "";
		deliveryYn = "";
		trkNo = "";
		rownum = "";
		writeDate="";
		
		koblNo = "";
		
		taxId="";
		eoriNo="";
		declType="";
		uploadType="";
		

	    totalItemValue = "";
	    itemInfo = "";
		dstnNationEName = "";
		
		rn = 0;
		rowspan = 0;
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
		
	}
	SecurityKeyVO keyVO = new SecurityKeyVO();
	/* private String nationCode; */
	private String userId;
	private String companyName;
	private String orgStation;
	private String dstnNation;
	private String orgStationName;
	private String dstnNationName;
	private String nno;
	private String cneeName;
	private String cneeZip;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String cneeTel;
	private String cneeHp;
	private String subNo;
	private String hsCode;
	private String itemDetail;
	private String unitValue;
	private String itemCnt;
	private String itemValue;
	private String orderDate;
	private String delDate;
	private String recDate;
	private String resultCode;
	private String dno;
	private String orderNo;
	private String status;
	private String userWidth;
	private String userLength;
	private String userHeight;
	private String hawbNo;
	private String hawbNo2;
	private String nativeCneeAddr;
    private String nativeCneeName;
    private String nativeCneeAddrDetail;
    private String cneeEmail;
    private String customsNo;
    private String boxCnt;
    private String userWta;
    private String userWtc;
    private String transCode;
    private String transName;
    private String itemMeterial;
    private String makeCntry;
    private String errName;
    private String deliveryYn;
    private String itemImgUrl;
    private String trkNo;
    private String errorMsg;
    private String rownum;
    private String whReqMsg;
    private String writeDate;
    private String shipperReference;
    private ArrayList<String> errorList = new ArrayList<String>();
    private ArrayList<String> errorItem = new ArrayList<String>();
    
    private ArrayList<HashMap<String, Object>> danList =new ArrayList<HashMap<String, Object>>();
    
    private String koblNo;
    
    private String taxId;
    private String eoriNo;
    private String declType;
    private String uploadType;
    
    private String totalItemValue;
    private String itemInfo;
	private String dstnNationEName;
    
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private int rn;
	private int rowspan;
	
	private ArrayList<HashMap<String, Object>> itemList;
	
	public void encryptData() {
		try {
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null )
				cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(cneeHp != null)
				cneeHp=AES256Cipher.AES_Encode(cneeHp, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail=AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr= AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null )
			    nativeCneeAddrDetail= AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			if(cneeEmail != null )
			    cneeEmail= AES256Cipher.AES_Encode(cneeEmail, symmetryKey);
			if(customsNo != null )
			    customsNo = AES256Cipher.AES_Encode(customsNo, symmetryKey);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
