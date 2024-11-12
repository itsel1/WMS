package com.example.temp.member.vo.takein;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTransVO {
	
	public UserTransVO(){
		transCode = "";	
		transName	= "";
		nationCode = "";
	}
	private String transCode;	
	private String transName;
	private String nationCode;
	
	

}
