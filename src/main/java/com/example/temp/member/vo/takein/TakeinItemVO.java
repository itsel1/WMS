package com.example.temp.member.vo.takein;

import com.example.temp.security.SecurityKeyVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TakeinItemVO {
	
	public TakeinItemVO(){
		cusItemCode="";
		itemCnt="";
		unitValue="";
	}
	
	SecurityKeyVO keyVO = new SecurityKeyVO();
	
	private String cusItemCode; 
	private String itemCnt;
	private String unitValue;

}
