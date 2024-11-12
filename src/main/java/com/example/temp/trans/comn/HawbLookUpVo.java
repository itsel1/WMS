package com.example.temp.trans.comn;

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

@Getter
@Setter
public class HawbLookUpVo {
	
	public HawbLookUpVo() {
		wDate="";
		arrDate="";
		mawbNo = "";
		shipperCode="";
		shipperName="";
		shipperTel="";
		shipperAddr="";
		shipperAddrDetail="";
		shipperUrl="";
		orderNo="";
		getBuy="";
		cneeName="";
		cneeTel="";
		cneeHp="";
		customsNo="";
		cneeZip="";
		cneeAddr="";
		cneeAddrDetail="";
		boxCnt="";
		dimUnit="";
		userWta="";
		buySite="";
		originCity="";
		mallType="";
		transCode="";
		sano="";
		samallno="";
		saaddr="";
		saceo="";
		orgStation="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	private String wDate;
	private String arrDate;
	private String mawbNo;
	private String shipperCode;
	private String shipperName;
	private String shipperTel;
	private String shipperAddr;
	private String shipperAddrDetail;
	private String shipperUrl;
	private String orderNo;
	private String getBuy;
	private String cneeName;
	private String cneeTel;
	private String cneeHp;
	private String customsNo;
	private String cneeZip;
	private String cneeAddr;
	private String cneeAddrDetail;
	private String boxCnt;
	private String dimUnit;
	private String userWta;
	private String buySite;
	private String originCity;
	private String mallType;
	private String transCode;
	private String sano;
	private String samallno;
	private String saaddr;
	private String saceo;
	private String orgStation;
	
	  public void encryptData() {
			try {
				if(shipperTel != null)
					shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
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
				if(customsNo != null)
					customsNo = AES256Cipher.AES_Encode(customsNo, symmetryKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public String dncryptData() {
			try {
			if(shipperTel != null) {
				try {
					shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "shipperTel";
				} 
			}
			
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
			
			if(shipperAddrDetail != null){
				try {
					shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "shipperAddrDetail";
				}
			}
			if(cneeAddr != null){
				try {
					cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeAddr";
				}
			}
			if(cneeTel != null){
				try {
					cneeTel =  AES256Cipher.AES_Decode(cneeTel, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeTel";
				}
			}
			if(cneeHp != null){
				try {
					cneeHp =  AES256Cipher.AES_Decode(cneeHp, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeHp";
				}
			}
			if(cneeAddrDetail != null){
				try {
					cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "cneeAddrDetail";
				}
			}
			if(customsNo != null){
				try {
					customsNo = AES256Cipher.AES_Decode(customsNo, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "customsNo";
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return "";
		}

}
