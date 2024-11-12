package com.example.temp.security;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecurityKeyVO {
	public SecurityKeyVO() {
		 symmetryKey = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO"; 
		/* symmetryKey = "abcdefghijklmnopqrstuvwxyz123456"; */
	}
	private String symmetryKey = "";
}
