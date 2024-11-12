package com.example.temp.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComnVO2 {
	private static final String BUCKET_NAME = "wms.mtuai.com";
	private static final String ACCESS_KEY = "AKIAJH34BQBA456FCYQA";
	private static final String SECRET_KEY = "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";
	public static String getBucketName() {
		return BUCKET_NAME;
	}
	public static String getAccessKey() {
		return ACCESS_KEY;
	}
	public static String getSecretKey() {
		return SECRET_KEY;
	}
}
