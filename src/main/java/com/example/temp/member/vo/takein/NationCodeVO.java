package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NationCodeVO {
	
	public NationCodeVO(){
		nationCode =""; 
		nationEName ="";	
		nationName = "";
	}

	private String nationCode;	
	private String nationEName;
	private String nationName;

}
