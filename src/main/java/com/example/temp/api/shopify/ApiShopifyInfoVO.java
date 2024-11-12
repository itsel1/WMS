package com.example.temp.api.shopify;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiShopifyInfoVO {
	
	public ApiShopifyInfoVO() {
		userId="";
		shopifyUrl="";
		apiKey="";
		password="";
		orgStation="";
	}
	
	private String userId;
	private String shopifyUrl;
	private String apiKey;
	private String password;
	private String orgStation;
}
