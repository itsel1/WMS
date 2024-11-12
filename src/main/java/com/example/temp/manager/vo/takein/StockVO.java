package com.example.temp.manager.vo.takein;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockVO {
	
	public StockVO(){
		stockNo = "";
		stockTyep = "";
		whStatus = "";
		cusItemCode="";
		userId = "";
		rackCode = "";
		itemDetail = "";
		whStatusName = "";
	}
 
	private String stockNo ;
	private String stockTyep ;
	private String whStatus;
	private String cusItemCode;
	private String userId;
	private String rackCode;
	private String itemDetail; 
	private String whStatusName;
}
