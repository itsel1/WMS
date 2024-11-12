package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24ManufacturersVo {
	
	public Cafe24ManufacturersVo() {
		manufacturerCode = "";
		manufacturerName = "";
	}
	
	private String manufacturerCode;
	private String manufacturerName;
 
}
