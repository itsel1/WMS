package com.example.temp.member.vo.takein;

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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TmpTakeinOrderVO {
	
	public TmpTakeinOrderVO(){
		nno="";
		orgStation="";
		dstnNation="";
		dstnStation="";		
		userId="";
		orderType="";
		orderNo="";
		hawbNo="";
		boxCnt="1";
		userWta="0.1";
		userWtc="0.1";
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
		transName="";
		orderDate="";
		status="";
		cneeEmail="";
		customsNo="";
		nativeCneeName="";
		nativeCneeAddr="";
		nativeCneeAddrDetail="";
		dimUnit="CM";
		wtUnit="KG";
		buySite="";
		getBuy="";
		mallType="";
		whReqMsg="";
		dlvReqMsg="";
		cneeDistrict="";
		orgStationName="";
		dstnNationName="";
		totalAmt="";
		dupCnt = "";
		already = "";
		errYn ="N";
		errMsg="";
		dUserId = "";
		dUserIp = "";
		dDate = "";
		rUserId = "";
		rUserIp = "";
		rDate = "";
		payment = "";
		orderItem = new ArrayList<TmpTakeinOrderItemListVO>();
		
		expNo = "";
		expBusinessName = "";
		expBusinessNum = "";
		expValue = "";
		agencyBusinessName = "";
		
		
		taxId="";
		eoriNo="";
		declType="4";
		uploadType="";
		

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
	    sendYn = "";
	    
	    cosmetic = "";
	    sign = "";
	    cneeWard = "";
	    shipperTaxType = "";
	    shipperTaxNo = "";
	    cneeTaxType = "";
	    cneeTaxNo = "";
	}

	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String nno ;
	private String orgStation ;
	private String dstnNation ;
	private String dstnStation ;
	private String userId ;
	private String orderType ;
	private String orderNo ;
	private String orderDate ;
	private String hawbNo ;
	private String boxCnt ;
	private String userWta ;
	private String userWtc ;
	private String shipperName ;
	private String shipperZip ;
	private String shipperTel ;
	private String shipperHp ;
	private String shipperCntry ;
	private String shipperCity ;
	private String shipperState ;
	private String shipperAddr ;
	private String shipperAddrDetail ;
	private String cneeName ;
	private String cneeAddr ;
	private String cneeZip ;
	private String cneeTel ;
	private String cneeHp ;
	private String cneeCntry ;
	private String cneeCity ;
	private String cneeState ;
	private String cneeAddrDetail ;
	private String userLength ;
	private String userWidth ;
	private String userHeight ;
	private String status;
	private String userEmail ;
	private String transCode ;
	private String transName;
	private String cneeEmail ;
	private String customsNo ;
	private String nativeCneeName ;
	private String nativeCneeAddr ;
	private String nativeCneeAddrDetail ;
	private String dimUnit ;
	private String wtUnit ;
	private String buySite ;
	private String wUserId ;
	private String wUserIp ;
	private String wDate ;
	private String getBuy ;
	private String mallType ;
	private String whReqMsg ;
	private String dlvReqMsg ;
	private String cneeDistrict ;
	private String orgStationName;
	private String dstnNationName;
	private String totalAmt;
	private String dupCnt;
	private String errYn;
	private String errMsg;
	private String already;
	private String dUserId ;
	private String dUserIp;
	private String dDate;
	private String rUserId ;
	private String rUserIp;
	private String rDate;
	private String payment;
	
	private String expNo;
	private String expBusinessName;
	private String expBusinessNum;
	private String expValue;
	private String agencyBusinessName;

    private String taxId;
    private String eoriNo;
    private String declType;
    private String uploadType;
	
	
	private ArrayList<TmpTakeinOrderItemListVO> orderItem;
	private String symmetryKey = keyVO.getSymmetryKey();
	
  // 2024.03.18 수출신고변경
    protected String expType;
    protected String expCor;
    protected String expRprsn;
    protected String expAddr;
    protected String expZip;
    protected String expRgstrNo;
    protected String expCstCd;
    protected String agtCor;
    protected String agtCstCd;
    protected String agtBizNo;
    protected String sendYn;
    
    // 2024.06.20 필드 추가
    private String cosmetic;
    private String sign;
    private String cneeWard;
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
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}


}
