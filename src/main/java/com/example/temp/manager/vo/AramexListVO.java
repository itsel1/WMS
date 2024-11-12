package com.example.temp.manager.vo;
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
public class AramexListVO{
	public AramexListVO() {
		rownum = "";
		resYn = "";
		dupYn = "";
		num = "";
		airline = "";
		consol = "";
		mawbno = "";
		origin = "";
		destination = "";
		flt1etd = "";
		finaleta = "";
		awb = "";
		pickupdate = "";
		pcs = "";
		weight = "";
		unit = "";
		commoditydescription = "";
		customs = "";
		customscurrency = "";
		shippername = "";
		shipperaddress = "";
		shippertel = "";
		origincity = "";
		originzipcode = "";
		origincountry = "";
		consigneename = "";
		consigneeaddress = "";
		consigneetel = "";
		consigneeemail = "";
		destcity = "";
		deststate = "";
		destzipcode = "";
		destcountry = "";
		chargingweight = "";
		hsCode = "";
		flt1carrier = "";
		flt1number = "'";
		errorCode="N";
		errorMsg= "";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String rownum;
	private String resYn;
	private String dupYn;
	private String num;
	private String airline;
	private String consol;
	private String mawbno;
	private String origin;
	private String destination;
	private String flt1etd;
	private String finaleta;
	private String awb;
	private String pickupdate;
	private String pcs;
	private String weight;
	private String unit;
	private String commoditydescription;
	private String customs;
	private String customscurrency;
	private String shippername;
	private String shipperaddress;
	private String shippertel;
	private String origincity;
	private String originzipcode;
	private String origincountry;
	private String consigneename;
	private String consigneeaddress;
	private String consigneetel;
	private String consigneeemail;
	private String destcity;
	private String deststate;
	private String destzipcode;
	private String destcountry;
	private String chargingweight;
	private String hsCode;
	private String flt1carrier;
	private String flt1number;
	private String errorCode;
	private String errorMsg;
	


	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void encryptData() {
		try {
			shipperaddress=AES256Cipher.AES_Encode(shipperaddress, symmetryKey);
			shippertel=AES256Cipher.AES_Encode(shippertel, symmetryKey);
			consigneeaddress=AES256Cipher.AES_Encode(consigneeaddress, symmetryKey);
			consigneetel=AES256Cipher.AES_Encode(consigneetel, symmetryKey);
			consigneeemail=AES256Cipher.AES_Encode(consigneeemail, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dncryptData() {
		try {
			shipperaddress=AES256Cipher.AES_Decode(shipperaddress, symmetryKey);
			shippertel=AES256Cipher.AES_Decode(shippertel, symmetryKey);
			consigneetel=AES256Cipher.AES_Decode(consigneetel, symmetryKey);
			consigneeemail=AES256Cipher.AES_Decode(consigneeemail, symmetryKey);
			consigneeaddress=AES256Cipher.AES_Decode(consigneeaddress, symmetryKey);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

