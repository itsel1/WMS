package com.example.temp.api.shop.shopee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class ApiCall {
	
	private static final String HOST = "https://partner.shopeemobile.com";
	private static final String PARTNER_KEY = "6567766350437346695a667a7a61525558754a414b54506b4d6c4f597a664d6d";
	static final long PARTNER_ID = 2008151L;
	
	
	public String makeSignature(String path, long timestamp, String access_token, long id) {
		
		String baseString = String.format("%s%s%s%s%s", PARTNER_ID, path, timestamp, access_token, id);
		
		try {
			
			byte[] baseStringByte = baseString.getBytes("UTF-8");
			byte[] partnerKeyByte = PARTNER_KEY.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(baseStringByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	
	public String makeSignature(String path, long timestamp) {
		
		String baseString = String.format("%s%s%s", PARTNER_ID, path, timestamp);
		
		try {
			
			byte[] baseStringByte = baseString.getBytes("UTF-8");
			byte[] partnerKeyByte = PARTNER_KEY.getBytes("UTF-8");
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(partnerKeyByte, "HmacSHA256");
			mac.init(secretKey);
			
			BigInteger signature = new BigInteger(1, mac.doFinal(baseStringByte));
			
			return String.format("%064x", signature);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public String makeHttpUrl(String path, long timestamp, long id, String access_token, String signature, String requestParameters, boolean merchantYn) {
		
		String tmpUrl;
		String httpUrl;
		
		if (path == null || timestamp == 0 || id == 0 || access_token == null || signature == null) {
			throw new IllegalArgumentException("Required parameters must not be null");
		}
		
		try {
			if (requestParameters == null || requestParameters.length() < 1) {
				if (merchantYn) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s", PARTNER_ID, timestamp, id, access_token, signature);	
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s", PARTNER_ID, timestamp, id, access_token, signature);	
				}
				
			} else {
				if (merchantYn) {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&merchant_id=%s&access_token=%s&sign=%s&%s", PARTNER_ID, timestamp, id, access_token, signature, requestParameters);
				} else {
					tmpUrl = String.format("?partner_id=%s&timestamp=%s&shop_id=%s&access_token=%s&sign=%s&%s", PARTNER_ID, timestamp, id, access_token, signature, requestParameters);
				}
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("알 수 없는 오류 발생 : " + e.getMessage());
			return null;
		}
	}

	public String makeHttpUrl(String path, long timestamp, String signature, String requestParameters) {
		
		String tmpUrl;
		String httpUrl;
		
		try {
		
			if (path == null || timestamp == 0 || signature == null) {
				throw new IllegalArgumentException("Required parameters must not be null");
			}
			
			if (requestParameters == null || requestParameters.length() < 1) {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s", PARTNER_ID, timestamp, signature);
			} else {
				tmpUrl = String.format("?partner_id=%s&timestamp=%s&sign=%s&%s", PARTNER_ID, timestamp, signature, requestParameters);
			}
			
			httpUrl = HOST + path + tmpUrl;
			return httpUrl;
			
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("알 수 없는 오류 발생 : " + e.getMessage());
			return null;
		}
	}
	
	public HashMap<String, String> apiGet(String apiUrl, LinkedHashMap<String, Object> apiHeaders) {
		
		HashMap<String, String> apiResult = new HashMap<>();
		String responseBody = null;
		
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoOutput(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				responseBody = builder.toString();
				apiResult.put("status", "success");
				apiResult.put("status_msg", "");
				apiResult.put("responseBody", responseBody);
				
				conn.disconnect();
			}
			
		} catch (MalformedURLException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return apiResult;
	}
	
	public HashMap<String, String> apiPost(String apiUrl, String requestBody, LinkedHashMap<String, Object> apiHeaders) {

		HashMap<String, String> apiResult = new HashMap<>();
		String responseBody = null;
		
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestBody);
			wr.flush();
			
			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				responseBody = builder.toString();
				apiResult.put("status", "success");
				apiResult.put("status_msg", "");
				apiResult.put("responseBody", responseBody);
				
				conn.disconnect();
			}
			
		} catch (MalformedURLException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
			apiResult.put("status", "fail");
			apiResult.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return apiResult;
	}
	
	
}
