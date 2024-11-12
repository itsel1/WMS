package com.example.temp.manager.vo.takein;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinOutStockVO {
	
	public TakeinOutStockVO(){
		userId="";
		rackCode="";
		takeInCode="";
		cusItemCode="";
		itemBarcode="";
		itemDetail="";
		cnt="";
		stockCnt ="";
	}

	private String userId;
	private String rackCode;
	private String takeInCode;
	private String cusItemCode;
	private String itemBarcode;
	private String itemDetail;	
	private String cnt;
	private String stockCnt;
} 
