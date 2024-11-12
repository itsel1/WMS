package com.example.temp.manager.vo.takein;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinItemVO {
	
	public TakeinItemVO(){
		takeInCode = "";
		orgStationName = "";
		cusItemCode = "";
		hsCode = "";
		rackCode = "";
		itemDetail = "";
		itemOption = "";
		whInCnt = "";
		whOutCnt = "";
		groupIdx = "";
		orgStation = "";
		userId = "";
		whInDate = "";
		inSpector = "";
		useYn = ""; 
		cusInvNo = "";
		cusSupplier = "";
		cusSupplierAddr = "";
		cusSupplierTel = "";
		cusOutNo = "";
		remark = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
		qtyUnit = "";
		mnDate = "";
	}
	private String takeInCode ;
	private String orgStationName ;
	private String cusItemCode ;
	private String hsCode ;
	private String rackCode;
	private String itemDetail ;
	private String itemOption ;
	private String whInCnt ;
	private String whOutCnt ;
	private String groupIdx ;
	private String orgStation ;
	private String userId ;
	private String whInDate ;
	private String inSpector ;
	private String useYn ;
	private String cusInvNo ;
	private String cusSupplier ;
	private String cusSupplierAddr ;
	private String cusSupplierTel ;
	private String cusOutNo ;
	private String remark ;
	private String wUserId ;
	private String wUserIp ;
	private String wDate ;
	private String qtyUnit;
	private String mnDate;

}
