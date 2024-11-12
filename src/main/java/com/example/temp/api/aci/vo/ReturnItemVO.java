package com.example.temp.api.aci.vo;

import java.io.UnsupportedEncodingException; 
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.xmpbox.type.DateType;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnItemVO {
	
	private List<ReturnItemVO> returnItemVOList;
	public List<ReturnItemVO> getReturnItemVOList() {
		return returnItemVOList;
	}
	public void setReturnItemVOList(List<ReturnItemVO> returnItemVOList) {
		this.returnItemVOList = returnItemVOList;
	}
	
	public ReturnItemVO() {
		
		nno = "";
		subNo = 0;
		userId = "";
		hsCode = "";
		itemDetail = "";
		nativeItemDetail = "";
		unitCurrency = "";
		itemCnt = 0;
		unitValue = 0;
		brand = "";
		makeCntry = "";
		makeCom = "";
		itemDiv = "";
		wtUnit = "";
		qtyUnit = "";
		packageUnit = "";
		exchangeRate = 0;
		chgCurrency = "";
		chgAmt = "";
		itemMeterial = "";
		takeInCode = "";
		cusItemCode = "";
		itemUrl = "";
		itemImgUrl = "";
		trkCom = "";
		trkNo = "";
		trkDate = "";
		userWta = 0;
		userWtc = 0;
		wUserId = "";
		wUserIp = "";
		wDate = "";
		itemColor = "";
		itemSize = "";
		itemWta = 0;
		hawbNo = "";
		////////
		itemMaterial="";
		
		itemDetailEng="";
		}
	
	
	private String nno;
	private int subNo;
	private String orgStation;
	private String userId;
	private String hsCode;
	private String itemDetail;
	private String nativeItemDetail;
	private String unitCurrency;
	private int itemCnt;
	private float unitValue;
	private String brand;
	private String makeCntry;
	private String makeCom;
	private String itemDiv;
	private String wtUnit;
	private String qtyUnit;
	private String packageUnit;
	private float exchangeRate;
	private String chgCurrency;
	private String chgAmt;
	private String itemMeterial;
	private String takeInCode;
	private String cusItemCode;
	private String itemUrl;
	private String itemImgUrl;
	private String trkCom;
	private String trkNo;
	private String trkDate;
	private float userWta;
	private float userWtc;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String itemColor;
	private String itemSize;
	
	private String itemDetailEng;
	
	///////////////
	private String itemMaterial;
	private float itemWta;
	
	
	////////////
	private String hawbNo;
}
