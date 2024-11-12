package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24ProductVo {
	
	public Cafe24ProductVo() {
		manufacturerCode = "";
		madeInCode = "";
		englishProductMaterial = "";
		productMaterial = "";
		clothFabric = "";
		detailImage = "";
		hscode = "";
		countryHscode = "";
		summaryDescription = "";
		productWeight = "";
	}
	
	private String manufacturerCode;
	private String madeInCode;
	private String englishProductMaterial;
	private String productMaterial;
	private String clothFabric;
	private String detailImage;
	private String hscode;
	private String countryHscode;
	private String summaryDescription;
	private String productWeight;
 
}
