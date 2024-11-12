package com.example.temp.member.vo.takein;

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
public class TakeinOrderVO {
	
	public TakeinOrderVO(){
		nno ="";
		orgStation ="";
		dstnNation ="";
		dstnNationName ="";
		dstnStation ="";
		userId ="";
		orderType ="";
		orderNo ="";
		orderDate ="";
		hawbNo ="";
		boxCnt ="";
		userWta ="";
		userWtc ="";
		shipperName ="";
		shipperZip ="";
		shipperTel ="";
		shipperHp ="";
		shipperCntry ="";
		shipperCity ="";
		shipperState ="";
		shipperAddr ="";
		shipperAddrDetail ="";
		cneeName ="";
		cneeAddr ="";
		cneeZip ="";
		cneeTel ="";
		cneeHp ="";
		cneeCntry ="";
		cneeCity ="";
		cneeState ="";
		cneeAddrDetail ="";
		userLength ="";
		userWidth ="";
		userHeight ="";
		userEmail ="";
		transCode ="";
		cneeEmail ="";
		customsNo ="";
		nativeCneeName ="";
		nativeCneeAddr ="";
		nativeCneeAddrDetail ="";
		dimUnit ="";
		wtUnit ="";
		buySite ="";
		wUserId ="";
		wUserIp ="";
		wDate ="";
		getBuy ="";
		mallType ="";
		whReqMsg ="";
		dlvReqMsg ="";
		cneeDistrict ="";
	}

	SecurityKeyVO keyVO = new SecurityKeyVO();
	private String nno ;
	private String orgStation ;
	private String dstnNation ;
	private String dstnNationName;
	private String dstnStation ;
	private String userId ;
	private String orderType ;
	private String orderNo ;
	private String orderDate ;
	private String hawbNo ;
	private String boxCnt ;
	private String userWta ;
	private String userWtc ;
	private String shipperName ;
	private String shipperZip ;
	private String shipperTel ;
	private String shipperHp ;
	private String shipperCntry ;
	private String shipperCity ;
	private String shipperState ;
	private String shipperAddr ;
	private String shipperAddrDetail ;
	private String cneeName ;
	private String cneeAddr ;
	private String cneeZip ;
	private String cneeTel ;
	private String cneeHp ;
	private String cneeCntry ;
	private String cneeCity ;
	private String cneeState ;
	private String cneeAddrDetail ;
	private String userLength ;
	private String userWidth ;
	private String userHeight ;
	private String userEmail ;
	private String transCode ;
	private String cneeEmail ;
	private String customsNo ;
	private String nativeCneeName ;
	private String nativeCneeAddr ;
	private String nativeCneeAddrDetail ;
	private String dimUnit ;
	private String wtUnit ;
	private String buySite ;
	private String wUserId ;
	private String wUserIp ;
	private String wDate ;
	private String getBuy ;
	private String mallType ;
	private String whReqMsg ;
	private String dlvReqMsg ;
	private String cneeDistrict ;


	private String symmetryKey = keyVO.getSymmetryKey();
	
	public void dncryptData() {
		try {
			if(cneeTel != null) {
				cneeTel=AES256Cipher.AES_Decode(cneeTel, symmetryKey);
			}
			if(cneeHp != null) {
				cneeHp=AES256Cipher.AES_Decode(cneeHp, symmetryKey);
			}
			if(cneeAddr != null) {
				cneeAddr=AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
			}
			if(cneeAddrDetail != null) {
				cneeAddrDetail=AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
			}
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}

}
