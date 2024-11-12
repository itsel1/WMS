package com.example.temp.api.logistics.dto;

import lombok.Data;

@Data
public class AramexParameter {

	private String origin;
	private String destination;
	private String chgWtUnit;
	private double chgWtValue;
	private String DescriptionOfGoods;
	private String GoodsOriginCountry;
	private int numberOfPieces;
	private String productGroup;
	private String productType;
	private String paymentType;
	private String paymentOptions;
	private String customsValueAmountCurrencyCode;
	private double customsValueAmountValue;
	private String services;
	private String originCity;
	private String destinationCity;

	public AramexParameter() {
		origin = "";
		destination = "";
		chgWtUnit = "";
		chgWtValue = 0;
		DescriptionOfGoods = "";
		GoodsOriginCountry = "";
		numberOfPieces = 0;
		productGroup = "";
		productType = "";
		paymentType = "";
		paymentOptions = "";
		customsValueAmountCurrencyCode = "";
		customsValueAmountValue = 0;
		services = "";
		originCity = "";
		destinationCity = "";
	}
}
