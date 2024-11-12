package com.example.temp.trans.cj;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CJOrderVo {
	 
	public CJOrderVo() {
		nno = "";
		orgStation = "";
		userId = "";
		hawbNo = "";
		shipperName = "";
		shipperZip = "";
		shipperTel = "";
		shipperHp = ""; 
		shipperAddr = "";
		shipperAddrDetail = "";
		buySite = "";
		cneeName = "";
		cneeAddr = "";
		cneeZip = "";
		cneeTel = "";
		cneeHp = "";
		cneeAddrDetail = "";
		dlvReqMsg = "";
		itemDetail = "";
		cusItemCode = ""; 
		totalItemCnt = 0;
		orderNo = "";
		dstnNation="";
	}
	private String nno = "";
	private String orgStation = "";
	private String userId = "";
	private String hawbNo = "";
	private String shipperName = "";
	private String shipperZip = "";
	private String shipperTel = "";
	private String shipperHp = "";
	private String shipperAddr = "";
	private String shipperAddrDetail = "";
	private String buySite = ""; 
	private String cneeName = "";
	private String cneeAddr = "";
	private String cneeZip = "";
	private String cneeTel = "";
	private String cneeHp = "";
	private String cneeAddrDetail = "";
	private String dlvReqMsg = "";
	private String itemDetail = "";
	private String cusItemCode = "";
	private int totalItemCnt;
	private String orderNo;
	private String dstnNation;
}
