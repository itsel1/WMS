package com.example.temp.manager.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NationVO{
	public NationVO() {
		nationCode ="";
		nationThrCode = "";
		nationName ="";
		stationCode ="";
		stationName ="";
		nationEName ="";
	}
	
	private String nationCode;
	private String nationThrCode;
	private String nationName;
	private String stationCode;
	private String stationName;
	private String nationEName;
}
