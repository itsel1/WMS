package com.example.temp.common.vo;

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
public class CommercialItemVO {
	public CommercialItemVO() {
		itemDetail = "";
		itemCnt = "";
		unitValue = "";
		chgCurrency = "";
		wta = 0;
		wtc = 0;
		
	}
	
	private String itemDetail;
	private String itemCnt;
	private String unitValue;
	private String chgCurrency;
	private double wta;
	private double wtc;
	
	
}
