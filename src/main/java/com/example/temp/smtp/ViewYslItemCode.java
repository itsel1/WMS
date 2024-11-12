package com.example.temp.smtp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewYslItemCode {
	public ViewYslItemCode() {
		itemCode ="";
		sagawaCode ="";
		itemNameEng ="";
		itemNameJp ="";
		origin ="";
		imgurl ="";
	}
	
	private String itemCode;
	private String sagawaCode;
	private String itemNameEng;
	private String itemNameJp;
	private String origin;
	private String imgurl;
	
}
	
