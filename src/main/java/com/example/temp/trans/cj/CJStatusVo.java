package com.example.temp.trans.cj;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CJStatusVo {
	
	public CJStatusVo() {
		orgStation = "";
		userId = "";
		nno = ""; 
		hawbNo = "";
		errorYn = "";
		errorMsg = "";
		wUserId = "";
		wUserIp = "";
		wDate = "";
	}
	private String orgStation = "";
	private String userId = "";
	private String nno = ""; 
	private String hawbNo = "";
	private String errorYn = "";
	private String errorMsg = "";
	private String wUserId = "";
	private String wUserIp = "";
	private String wDate = "";
}
