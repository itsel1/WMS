package com.example.temp.api.aci.vo;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.security.SecurityKeyVO;

import lombok.Data;

@Data
public class BizInfo {
	
	private String userId;
	private String regName;
	private String regRprsn;
	private String regNo;
	private String regAddr;
	private String regZip;
	private String bizCustomsNo;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String symmetryKey = keyVO.getSymmetryKey();
	
	public BizInfo() {
		userId = "";
		regName = "";
		regRprsn = "";
		regNo = "";
		regAddr = "";
		regZip = "";
		bizCustomsNo = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
	}
	
	public void encryptData() {
		try {
			
			if (regNo != null) {
				regNo = AES256Cipher.AES_Encode(regNo, symmetryKey);
			}
			
			if (regAddr != null) {
				regAddr = AES256Cipher.AES_Encode(regAddr, symmetryKey);
			}
			
			if (bizCustomsNo != null) {
				bizCustomsNo = AES256Cipher.AES_Encode(bizCustomsNo, symmetryKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dncryptData() {
		try {
			
			if (regNo != null) {
				regNo = AES256Cipher.AES_Decode(regNo, symmetryKey);
			}
			
			if (regAddr != null) {
				regAddr = AES256Cipher.AES_Decode(regAddr, symmetryKey);
			}
			
			if (bizCustomsNo != null) {
				bizCustomsNo = AES256Cipher.AES_Decode(bizCustomsNo, symmetryKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
