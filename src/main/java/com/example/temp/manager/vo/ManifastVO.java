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
import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ManifastVO {
	public ManifastVO() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		csid="";
		hawbNo="";
		boxCnt="";
		shipperName="";
		shipperTel="";
		shipperAddr="";
		cneeTel="";
		nativeCneeName="";
		cneeName="";
		cneeZip="";
		cneeAddr="";
		userWta="";
		userLength="";
		userWidth="";
		userHeight="";
		declareQuantity="";
		chgCurrency="";
		itemDetail1="";
		itemMeterial1="";
		itemCnt1="";
		unitValue1="";
		itemDetail2="";
		itemMeterial2="";
		itemCnt2="";
		unitValue2="";
		itemDetail3="";
		itemMeterial3="";
		itemCnt3="";
		unitValue3="";
		itemDetail4="";
		itemMeterial4="";
		itemCnt4="";
		unitValue4="";
		itemDetail5="";
		itemMeterial5="";
		itemCnt5="";
		unitValue5="";
		makeCntry="";
		nativeCneeAddr="";
		nativeCneeAddrDetail="";
		cusItemCode1 ="";
		cusItemCode2 ="";
		cusItemCode3 ="";
		cusItemCode4 ="";
		cusItemCode5 ="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String csid;
	private String hawbNo;
	private String boxCnt;
	private String shipperName;
	private String shipperTel;
	private String shipperAddr;
	private String cneeTel;
	private String nativeCneeName;
	private String cneeName;
	private String cneeZip;
	private String cneeAddr;
	private String userWta;
	private String userLength;
    private String userWidth;
    private String userHeight;
    private String declareQuantity;
    private String chgCurrency;
    private String cusItemCode1;
    private String itemDetail1;
    private String itemMeterial1;
    private String itemCnt1;
    private String unitValue1;
    private String cusItemCode2;
    private String itemDetail2;
    private String itemMeterial2;
    private String itemCnt2;
    private String unitValue2;
    private String cusItemCode3;
    private String itemDetail3;
    private String itemMeterial3;
    private String itemCnt3;
    private String unitValue3;
    private String cusItemCode4;
    private String itemDetail4;
    private String itemMeterial4;
    private String itemCnt4;
    private String unitValue4;
    private String cusItemCode5;
    private String itemDetail5;
    private String itemMeterial5;
    private String itemCnt5;
    private String unitValue5;
    private String makeCntry;
    private String nativeCneeAddr;
	private String nativeCneeAddrDetail;
	
	private String symmetryKey = keyVO.getSymmetryKey();
	
	
	public void encryptData() {
		try {
			if(shipperTel != null)
				shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Encode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Encode(cneeTel, symmetryKey);
			if(nativeCneeAddr!= null)
				nativeCneeAddr=AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail!= null)
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
			if(shipperAddr != null)
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			if(cneeAddr != null)
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			if(cneeTel != null)
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			if(nativeCneeAddr!= null)
				nativeCneeAddr=AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail!= null)
				nativeCneeAddrDetail=AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
