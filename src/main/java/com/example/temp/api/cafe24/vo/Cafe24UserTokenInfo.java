package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24UserTokenInfo {
	
	public Cafe24UserTokenInfo() {
		userId = "";
		cafe24Id = "";
		accessToken = "";
		expiresAt = "";
		refreshToken = "";
		refreshTokenExpiresAt = "";
		clientId = "";
		mallId = "";
		scopes = "";
		issuedAt = "";
	}
	
	private String userId;
	private String cafe24Id;
	private String accessToken;
	private String expiresAt;
	private String refreshToken;
	private String refreshTokenExpiresAt;
	private String clientId;
	private String mallId;
	private String scopes;
	private String issuedAt;
	
}
