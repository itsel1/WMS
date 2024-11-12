package com.example.temp.api.shipment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

public class ApiAction {
	
	private ApiAction() {
	}

	private static class LazyHolder {
		private static final ApiAction instance = new ApiAction();
	}

	public static ApiAction getInstance() {
		return LazyHolder.instance;
	}

	public String sendGet(String httpUrl, LinkedHashMap<String, Object> headers) {
		String responseData = "";
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
				responseData = sb.toString();
				connection.disconnect();
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseData;
	}
	
	public String sendPost(String reqVal, String httpUrl, LinkedHashMap<String, Object> apiHeader) {
		String responseData = "";
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("POST");
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
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK || connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				responseData = sb.toString();

				connection.disconnect();
				return responseData;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseData;
	}

}
