package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyVO {

	public CurrencyVO() {
		
		idx = 0;
		currency = "";
		nationCode = "";
		nationName = "";
		nationEName = "";
		
	}
	
	private int idx;
	private String currency;
	private String nationCode;
	private String nationName;
	private String nationEName;
}
