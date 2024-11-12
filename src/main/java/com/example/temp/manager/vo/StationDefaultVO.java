package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StationDefaultVO{
	public StationDefaultVO() {
		wtUnit = "KG";
		dimUnit = "CM";
		per = "6000";
	}
	
	private String wtUnit;
	private String dimUnit;
	private String per;
}
