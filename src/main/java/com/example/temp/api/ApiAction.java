package com.example.temp.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.service.ApiService;


public class ApiAction {

	private ApiAction() {
	}

	private static class LazyHolder {
		private static final ApiAction instance = new ApiAction();
	}

	public static ApiAction getInstance() {
		return LazyHolder.instance;
	}
	
	public JSONObject apiGet(String httpUrl, LinkedHashMap<String, Object> headers) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);

			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					Object value = headers.get(key);
					connection.setRequestProperty(key, value.toString());
				}
			}

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public JSONObject apiGet(String httpUrl, LinkedHashMap<String, Object> headers, String nno, String userId, String userIp) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);

			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					Object value = headers.get(key);
					connection.setRequestProperty(key, value.toString());
				}
			}

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public JSONObject apiGet(String reqVal, String httpUrl, String nno, String userId, String userIp) {

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();

				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public JSONObject apiGet(String httpUrl) {

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();

				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public JSONObject apiPost(String reqVal, String httpUrl, LinkedHashMap<String, Object> apiHeader) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setDefaultUseCaches(false);

			if (apiHeader != null && !apiHeader.isEmpty()) {
				for (String key : apiHeader.keySet()) {
					Object value = apiHeader.get(key);
					connection.setRequestProperty(key, value.toString());
				}
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(reqVal);
			wr.flush();

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();
				return responseData;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public JSONObject apiPost(String reqVal, String httpUrl, LinkedHashMap<String, Object> headers, String nno, String userId, String userIp) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setDefaultUseCaches(false);

			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					Object value = headers.get(key);
					connection.setRequestProperty(key, value.toString());
				}
			}

			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(reqVal);
			wr.flush();

			StringBuilder sb = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				JSONObject responseData = new JSONObject(sb.toString());
				connection.disconnect();
				return responseData;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

}
