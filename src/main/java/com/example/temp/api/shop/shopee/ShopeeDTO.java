package com.example.temp.api.shop.shopee;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ShopeeDTO {
	
	private long shopeeId;
	private String userId;
	private String merchantYn;
	private String refreshToken;
	private String accessToken;
	private String tokenCreated;
	private String tokenExpires;
	private String authExpires;
	private String shopName;
	private String shopRegion;
	private String useYn;
	private String wDate;
	private String dDate;
	private String dUserId;
	private String dUserIp;
	
	// api
	private long timeFrom;
	private long timeTo;
	private String errorMsg;
	private String orderSn;
	private String nextCursor;
	private boolean more;
	
	private boolean hasNextPage;
	private String offsetStr;
	private int offsetInt;
	
	private ArrayList<String> orderList;
	private ArrayList<Long> globalItemList;
	private ArrayList<Long> itemList;
	private ArrayList<Long> modelList;
	
	
	public ShopeeDTO() {
		
		shopeeId = 0;
		userId = "";
		merchantYn = "";
		refreshToken = "";
		accessToken = "";
		tokenCreated = "";
		tokenExpires = "";
		authExpires = "";
		shopName = "";
		shopRegion = "";
		useYn = "";
		wDate = "";
		dDate = "";
		dUserId = "";
		dUserIp = "";
		
		timeFrom = 0;
		timeTo = 0;
		errorMsg = "";
		orderSn = "";
		nextCursor = "";
		more = false;
		
		hasNextPage = false;
		offsetStr = "";
		offsetInt = 0;
		
		orderList = new ArrayList<String>();
		itemList = new ArrayList<Long>();
		modelList = new ArrayList<Long>();
		globalItemList = new ArrayList<Long>();
	}
}
