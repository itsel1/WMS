package com.example.temp.trans.cj;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CJParameterVo {
	
	public CJParameterVo() {
		nno = "";
		userId = "";
		addr="";
		addrDetail=""; 
		depDate="";
		hawbNo="";
	}

	private String nno = "";
	private String userId = ""; 
	private String addr = "";
	private String addrDetail = "";
	private String depDate = "";
	private String hawbNo = "";
	
}
