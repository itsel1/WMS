package com.example.temp.member.vo.takein;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeInUserStockOutVO {
	
	public TakeInUserStockOutVO(){
		userId="";
		takeInCode="";
		itemBarcode="";
		cusItemCode="";
		rackCode="";
		stockNo="";
		itemDetail="";
		outDate ="";
		outHawb="";
		remark = "";
		whInDate = "";
		groupIdx="";
		mnDate = "";
	}
	
	private String userId;
	private String takeInCode;
	private String itemBarcode;
	private String cusItemCode;
	private String rackCode;
	private String stockNo;
	private String itemDetail; 
	private String outHawb;
	private String groupIdx;
	private String remark;
	private String outDate;
	private String whInDate;
	private String mnDate;
}
