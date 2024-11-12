package com.example.temp.member.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KukkiwonVO {
	public KukkiwonVO() {
		nno = "";
		orderNo = "";
		serialNo = "";
		poomDan = "";
		danNo = "";
		cneeName = "";
	}

	private String nno;
	private String orderNo;
	private String serialNo;
	private String poomDan;
	private String danNo;
	private String cneeName;
}
