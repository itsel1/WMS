package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrgStationVO {
	
	public OrgStationVO(){
		stationCode = "";	
		stationName	= "";
		stationAddr	= "";
		stationTel	= "";
		nationCode	= "";
		nationName  = "";
	}

	private String stationCode;	
	private String stationName;
	private String stationAddr;
	private String stationTel;
	private String nationCode;
	private String nationName;

}
