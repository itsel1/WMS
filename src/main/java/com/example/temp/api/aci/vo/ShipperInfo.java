package com.example.temp.api.aci.vo;

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

import lombok.Data;

@Data
public class ShipperInfo {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	
	private String shipperName;
	private String shipperEmail;
	private String shipperTel;
	private String shipperHp;
	private String shipperZip;
	private String shipperAddr;
	private String shipperAddrDetail;
	
	private String shipperEName;
	private String shipperEAddr;
	private String shipperEAddrDetail;

	public ShipperInfo() {
		shipperName = "";
		shipperEmail = "";
		shipperTel = "";
		shipperHp = "";
		shipperZip = "";
		shipperAddr = "";
		shipperAddrDetail = "";
		shipperEName = "";
		shipperEAddr = "";
		shipperEAddrDetail = "";
	}
	
	public void encryptData() {
		try {
			
			if (shipperEmail != null) {
				shipperEmail = AES256Cipher.AES_Encode(shipperEmail, symmetryKey);
			}
			
			if (shipperTel != null) {
				shipperTel = AES256Cipher.AES_Encode(shipperTel, symmetryKey);
			}
			
			if (shipperHp != null) {
				shipperHp = AES256Cipher.AES_Encode(shipperHp, symmetryKey);
			}
			
			if (shipperAddr != null) {
				shipperAddr = AES256Cipher.AES_Encode(shipperAddr, symmetryKey);
			}

			if (shipperAddrDetail != null) {
				shipperAddrDetail = AES256Cipher.AES_Encode(shipperAddrDetail, symmetryKey);
			}

			if (shipperEAddr != null) {
				shipperEAddr = AES256Cipher.AES_Encode(shipperEAddr, symmetryKey);
			}

			if (shipperEAddrDetail != null) {
				shipperEAddrDetail = AES256Cipher.AES_Encode(shipperEAddrDetail, symmetryKey);
			}
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
	
	public void decryptData() {
		try {
			
			if (shipperEmail != null) {
				shipperEmail = AES256Cipher.AES_Decode(shipperEmail, symmetryKey);
			}
			
			if (shipperTel != null) {
				shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
			}
			
			if (shipperHp != null) {
				shipperHp = AES256Cipher.AES_Decode(shipperHp, symmetryKey);
			}
			
			if (shipperAddr != null) {
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
			}

			if (shipperAddrDetail != null) {
				shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
			}

			if (shipperEAddr != null) {
				shipperEAddr = AES256Cipher.AES_Decode(shipperEAddr, symmetryKey);
			}

			if (shipperEAddrDetail != null) {
				shipperEAddrDetail = AES256Cipher.AES_Decode(shipperEAddrDetail, symmetryKey);
			}
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Exception", e);
		}
	}
}
