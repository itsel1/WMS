package com.example.temp.manager.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendVO {
	public SendVO() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		nno = "";
		userId ="";
		hawbNo="";
		nationCode="";
		dstnNation="";
		
		orgNationName="";
		dstnNationName="";
		
		transCode = "";
		transName= "";
		
		boxCnt="";
		cneeName="";
		shipperName="";
		nativeCneeName="";
		cneeZip="";
		wta="";
		wtc="";
		cneeTel="";
		cneeAddr="";
		shipperTel="";
		shipperAddr="";
		nativeCneeAddr = "";
		nativeCneeAddrDetail ="";
		mainItem="";
		totalValue="";
		mawbNo="";
		orderNo="";
		orderDate="";
		orgStation="";
		startDate = "";
		endDate = "";
		
		cneeCity = "";
		cneeState = "";
		cneeAddrDetail = "";
		cneeHp="";
		valueMatchNo="";
		

		subNo = 1;
		itemDetail = "";
		totalItemValue = "";
		itemCnt = 1;
		itemValue = "";
		unitValue = "";
		matchNo = "";
		
		rnum = 1;
		
		remark = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String nno;
	private String userId;
	private String hawbNo;
	private String mawbNo;
	private String nationCode;
	private String dstnNation;
	
	private String orgNationName;
	private String dstnNationName;
	
	private String boxCnt;
	private String cneeName;
	private String shipperName;
	private String nativeCneeName;
	private String orderNo;
	
	private String transCode;
	private String transName;
	
	private String cneeZip;
	
	private String wta;
	private String wtc;
	private String mainItem;
	private String totalValue;
	
	private String arrDate;
	private String depDate;
	
	private String cneeTel;
	private String cneeAddr;
	private String shipperTel;
	private String shipperAddr;
	
	private String cneeState;
	private String cneeCity;
	private String cneeAddrDetail;
	private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private String orgStation;
	private String orderDate;
	private String startDate;
	private String endDate;
	private String shipperReference;
	
	private String cneeHp;
	
	private PagingVO paging;
	
	private String valueMatchNo;
	
	private int subNo;
	private String itemDetail;
	private String totalItemValue;
	private int itemCnt;
	private String itemValue;
	private String unitValue;
	private String matchNo;
	private int rnum;
	
	private String remark;
	
	public void encryptData() {
		try {
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail = AES256Cipher.AES_Encode(cneeAddrDetail, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			if(cneeHp != null) 
				cneeHp=AES256Cipher.AES_Encode(cneeHp, symmetryKey);
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
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeAddrDetail != null)
				cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr=AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail=AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
			if(cneeHp != null) 
				cneeHp=AES256Cipher.AES_Decode(cneeHp, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
