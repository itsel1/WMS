package com.example.temp.api.aci.vo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.example.temp.common.encryption.AES256Cipher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiOrderListVO {
	public ApiOrderListVO() {
		nno = "";
	    orgStation = "";
	    dstnNation = "";
	    dstnStation = "";
	    userId = "";
	    orderType = "";
	    orderNo = "";
	    hawbNo = "";
	    boxCnt = "";
	    userWta = "";
	    userWtc = "";
	    shipperName = "";
	    shipperZip = "";
	    shipperTel = "";
	    shipperHp = "";
	    shipperCntry = "";
	    shipperCity = "";
	    shipperState = "";
	    shipperAddr = "";
	    shipperAddrDetail = "";
	    shipperEmail = "";
	    cneeName = "";
	    cneeAddr = "";
	    cneeZip = "";
	    cneeTel = "";
	    cneeHp = "";
	    cneeCntry = "";
	    cneeCity = "";
	    cneeState = "";
	    cneeAddrDetail = "";
	    userLength = "";
	    userWidth = "";
	    userHeight = "";
	    userEmail = "";
	    wUserId = "";
	    wUserIp = "";
	    wDate = "";
	    transCode = "";
	    orderDate = "";
		status = "";
		nativeCneeAddr="";
	    nativeCneeName="";
	    nativeCneeAddrDetail="";
	    cneeEmail= "";
	    customsNo = "";
	    buySite = "";
	    getBuy = "";
	    mallType = "";
	    whReqMsg = "";
	    dlvReqMsg = "";
	    cneeDistrict = "";
	    comName = "";
	    comEName = "";
	    expLicenceYn = "";
	    expBusinessNum="";
	    expBusinessName="";
	    expShipperCode="";
	    simpleYn = "";
	    suspension = "";
	    rateZone="";
	    payment = "";
	    shipperReference="";
	    cneeReference1="";
	    cneeReference2="";
	    food= "";
	    nationalIdDate ="";
	    nationalIdAuthority="";
	    cneeBirth="";
	    taxNo="";
	    expValue = "";
	    userName = "";
	    unitCurrency = "";
	    
	    agencyBusinessName = "";
	    
	    taxId="";
	    eoriNo="";
	    declType="";
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
	    
	    regName = "";
	    regRprsn = "";
	    regAddr = "";
	    regZip = "";
	    regNo = "";
	    bizCustomsNo = "";
	    
	    iossNo = "";
	    
	    shipperTaxType = "";
	    shipperTaxNo = "";
	    cneeTaxType = "";
	    cneeTaxNo = "";
	    cosmetic = "";
	    sign = "";
	    cneeWard = "";


	}
	
	protected String nno;
    protected String orgStation;
    protected String dstnNation;
    protected String dstnStation;
    protected String userId;
    protected String orderType;
    protected String orderNo;
    protected String hawbNo;
    protected String boxCnt;
    protected String userWta;
    protected String userWtc;
    protected String shipperName;
    protected String shipperZip;
    protected String shipperTel;
    protected String shipperHp;
    protected String shipperCntry;
    protected String shipperCity;
    protected String shipperState;
    protected String shipperAddr;
    protected String shipperAddrDetail;
    protected String shipperEmail;
    protected String cneeName;
    protected String cneeAddr;
    protected String cneeZip;
    protected String cneeTel;
    protected String cneeHp;
    protected String cneeCntry;
    protected String cneeCity;
    protected String cneeState;
    protected String cneeAddrDetail;
    protected String userLength;
    protected String userWidth;
    protected String userHeight;
    protected String userEmail;
    protected String wUserId;
    protected String wUserIp;
    protected String wDate;
    protected String transCode;
    protected String orderDate;
    protected String status;
    protected String nativeCneeAddr;
    protected String nativeCneeName;
    protected String nativeCneeAddrDetail;
    protected String cneeEmail;
    protected String customsNo;
    protected String dimUnit;
    protected String wtUnit;
    protected String symmetryKey;
    protected String orderNoYn;
    protected String buySite;
    protected String getBuy;
    protected String mallType;
    protected String whReqMsg;
    protected String dlvReqMsg;
    protected String cneeDistrict;
    protected String comName;
    protected String comEName;
    protected String expLicenceYn;
    protected String expBusinessNum;
    protected String expBusinessName;
    protected String expShipperCode;
    protected String expNo;
    protected String simpleYn;
    protected String suspension;
    protected String rateZone;
    protected String payment;
    protected String shipperReference;
    protected String cneeReference1="";
    protected String cneeReference2="";
    protected String food = "";
    protected String nationalIdDate;
    protected String nationalIdAuthority;
    protected String cneeBirth;
    protected String taxNo;
    protected String nationName;
    protected String expValue;
    protected String wDateTwo;
    protected String userName;
    protected String unitCurrency;
    
    
    protected String taxId;
    protected String eoriNo;
    protected String declType;
    protected String uploadType;
    
    // 2023.10.11 수출신고대행사업자명 추가
    protected String agencyBusinessName;
    
    
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
    
    
    private String regName;
    private String regRprsn;
    private String regAddr;
    private String regZip;
    private String regNo;
    private String bizCustomsNo;
    
    private String iossNo;
    
    private String shipperTaxType;
    private String shipperTaxNo;
    private String cneeTaxType;
    private String cneeTaxNo;
    private String cosmetic;
    private String sign;
    private String cneeWard;
    

    
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
			if(shipperEmail != null)
				shipperEmail = AES256Cipher.AES_Encode(shipperEmail, symmetryKey);
			if(nativeCneeAddr != null)
				nativeCneeAddr= AES256Cipher.AES_Encode(nativeCneeAddr, symmetryKey);
			if(nativeCneeAddrDetail != null)
				nativeCneeAddrDetail= AES256Cipher.AES_Encode(nativeCneeAddrDetail, symmetryKey);
			if(cneeEmail != null)
				cneeEmail= AES256Cipher.AES_Encode(cneeEmail, symmetryKey);
			if(customsNo != null)
				customsNo = AES256Cipher.AES_Encode(customsNo, symmetryKey);
			
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e) {
			
			
		}
		
	}
	
	public String dncryptData() {
		try {
			if(shipperTel != null) {
				try {
					shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "shipperTel";
				} 
			}
			
			if(shipperHp != null) {
				try {
					shipperHp = AES256Cipher.AES_Decode(shipperHp, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "shipperHp";
				} 
			}

			if(shipperAddr != null) {
				try {
					shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "shipperAddr";
				}
			}
				
			if(shipperEmail != null){
				try {
					shipperEmail= AES256Cipher.AES_Decode(shipperEmail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "shipperEmail";
				}
			}
			
			if(shipperAddrDetail != null){
				try {
					shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "shipperAddrDetail";
				}
			}
			if(cneeAddr != null){
				try {
					cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "cneeAddr";
				}
			}
			if(cneeTel != null){
				try {
					cneeTel =  AES256Cipher.AES_Decode(cneeTel, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "cneeTel";
				}
			}
			if(cneeHp != null){
				try {
					cneeHp =  AES256Cipher.AES_Decode(cneeHp, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "cneeHp";
				}
			}
			if(cneeAddrDetail != null){
				try {
					cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "cneeAddrDetail";
				}
			}
			if(nativeCneeAddr != null){
				try {
					nativeCneeAddr= AES256Cipher.AES_Decode(nativeCneeAddr, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e) {
					
					
					return "nativeCneeAddr";
				}
			}
			if(nativeCneeAddrDetail != null){
				try {
					nativeCneeAddrDetail= AES256Cipher.AES_Decode(nativeCneeAddrDetail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "nativeCneeAddrDetail";
				}
			}
			if(cneeEmail != null){
				try {
					cneeEmail= AES256Cipher.AES_Decode(cneeEmail, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "cneeEmail";
				}
			}
			if(customsNo != null){
				try {
					customsNo = AES256Cipher.AES_Decode(customsNo, symmetryKey);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
					
					
					return "Custom_Clearance_ID";
				}
			}
		
		}catch (Exception e) {
			
		}
		return "";
	}
    
	
	public void shipperTelDncrypt(String originKey) {
		if(shipperTel != null) {
			try {
				shipperTel = AES256Cipher.AES_Decode(shipperTel, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			} 
		}
	}
	
	public void shipperHpDncrypt(String originKey) {
		if(shipperHp != null) {
			try {
				shipperHp = AES256Cipher.AES_Decode(shipperHp, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			} 
		}
	}
	
	public void shipperAddrDncrypt(String originKey) {
		if(shipperAddr != null) {
			try {
				shipperAddr = AES256Cipher.AES_Decode(shipperAddr, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void shipperAddrDetailDncrypt(String originKey) {		
		if(shipperAddrDetail != null){
			try {
				shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void cneeAddrDncrypt(String originKey) {
		if(cneeAddr != null){
			try {
				cneeAddr = AES256Cipher.AES_Decode(cneeAddr, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void cneeTelDncrypt(String originKey) {
		if(cneeTel != null){
			try {
				cneeTel =  AES256Cipher.AES_Decode(cneeTel, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void cneeHpDncrypt(String originKey) {
		if(cneeHp != null){
			try {
				cneeHp =  AES256Cipher.AES_Decode(cneeHp, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void cneeAddrDetailDncrypt(String originKey) {
		if(cneeAddrDetail != null){
			try {
				cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void userEmailDncrypt(String originKey) {
		if(userEmail != null){
			try {
				userEmail = AES256Cipher.AES_Decode(userEmail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void nativeCneeAddrDncrypt(String originKey) {
		if(nativeCneeAddr != null){
			try {
				nativeCneeAddr= AES256Cipher.AES_Decode(nativeCneeAddr, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e) {
				
				
			}
		}
	}
	public void nativeCneeAddrDetailDncrypt(String originKey) {
		if(nativeCneeAddrDetail != null){
			try {
				nativeCneeAddrDetail= AES256Cipher.AES_Decode(nativeCneeAddrDetail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void cneeEmailDncrypt(String originKey) {
		if(cneeEmail != null){
			try {
				cneeEmail= AES256Cipher.AES_Decode(cneeEmail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void customsNoDncrypt(String originKey) {
		if(customsNo != null){
			try {
				customsNo = AES256Cipher.AES_Decode(customsNo, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void shipperEmailDncrypt(String originKey) {
		if(shipperEmail != null){
			try {
				shipperEmail = AES256Cipher.AES_Decode(shipperEmail, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	public void expAddrDncrypt(String originKey) {
		if (expAddr != null || !expAddr.equals("")) {
			try {
				expAddr = AES256Cipher.AES_Decode(expAddr, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	
	public void expRgstrNoDncrypt(String originKey) {
		if (expRgstrNo != null || !expRgstrNo.equals("")) {
			try {
				expRgstrNo = AES256Cipher.AES_Decode(expRgstrNo, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	
	public void expCstCdDncrypt(String originKey) {
		if (expCstCd != null || !expCstCd.equals("")) {
			try {
				expCstCd = AES256Cipher.AES_Decode(expCstCd, originKey);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |IllegalArgumentException e) {
				
				
			}
		}
	}
	
	


}
