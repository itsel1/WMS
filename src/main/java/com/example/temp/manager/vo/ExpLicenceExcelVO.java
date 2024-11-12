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
public class ExpLicenceExcelVO{
	public ExpLicenceExcelVO() {
		hawb="";
		shipperName="";
		dstnNation="";
		itemDetail="";
		brand="";
		itemCnt="";
		unitValue="";
		value="";
		hsCode="";
		wta="";
		boxCnt="";
		currency="";
		cneeName="";
		totalValue="";
		expNo = "";
		expBusinessNum = "";
		shipperAddr = "";
		shipperAddrDetail = "";
		cneeAddr = "";
		cneeAddrDetail = "";
		payment = "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String hawb;
	private String shipperName;
	private String dstnNation;
	private String itemDetail;
	private String cneeName;
	private String brand;
	private String itemCnt;
	private String unitValue;
	private String value;
	private String hsCode;
	private String wta;
	private String boxCnt;
	private String currency;
	private String totalValue;
	private String shipperAddr;
	private String shipperAddrDetail;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String expNo;
	private String expBusinessNum;
	private String symmetryKey = keyVO.getSymmetryKey();
	private String payment;
	
	public String dncryptData() {
		try {
			if(shipperAddr != null) {
				try {
					shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "shipperAddr";
				}
			}
			
			if(shipperAddrDetail != null) {
				try {
					shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "shipperAddrDetail";
				}
			}
			
			if(cneeAddr != null) {
				try {
					cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeAddr";
				}
			}
			
			if(cneeAddrDetail != null) {
				try {
					cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeAddrDetail";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "";
	}
	
}
