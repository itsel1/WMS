package com.example.temp.api.webhook.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoluTrkVO {
	private String nno;
	private String hawbNo;
	private String trackingNum;
	private String status;
	private String statusDesceiption;
	private String details;
	private String chechpointerStatus;
	private String type;
	private String issueDateTime;
}
