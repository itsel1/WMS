package com.example.temp.api.ecommerce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.temp.api.Export;
import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.ecommerce.dto.Shopify;
import com.example.temp.api.ecommerce.dto.ShopifyOrder;
import com.google.gson.Gson;

@Service
public class ShopifyHandler {
	
	private LinkedHashMap<String, Object> buildApiHeaders(String accessToken) {
		LinkedHashMap<String, Object> apiHeaders = new LinkedHashMap<>();
		apiHeaders.put("Content-Type", "application/json");
		apiHeaders.put("X-Shopify-Access-Token", accessToken);
		return apiHeaders;
	}
	
	
	private HashMap<String, String> apiGet(String apiUrl, LinkedHashMap<String, Object> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();

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
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
				conn.disconnect();
			} else {
				
				if (conn.getResponseCode() == 401) {
					returnMap.put("status_msg", "[API] Invalid API key or access token (unrecognized login or wrong password)");
				} else if (conn.getResponseCode() == 402) {
					returnMap.put("status_msg", "This shop's plan does not have access to this feature");
				} else if (conn.getResponseCode() == 403) {
					returnMap.put("status_msg", "User does not have access");
				} else if (conn.getResponseCode() == 404) {
					returnMap.put("status_msg", "Not Found");
				} else if (conn.getResponseCode() == 422) {
					returnMap.put("status_msg", "The fulfillment order is not in an open state.");
				} else if (conn.getResponseCode() == 429) {
					returnMap.put("status_msg", "Exceeded 2 calls per second for api client. Reduce request rates to resume uninterrupted service.");
				} else {
					returnMap.put("status_msg", "An unexpected error occurred");
				}
				returnMap.put("status", "fail");
			}
			
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
	    	returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
		
	}
	
	
	private HashMap<String, String> apiPost(String apiUrl, String requestBody, LinkedHashMap<String, Object> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();

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
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
				conn.disconnect();
			} else {
				
				if (conn.getResponseCode() == 401) {
					returnMap.put("status_msg", "[API] Invalid API key or access token (unrecognized login or wrong password)");
				} else if (conn.getResponseCode() == 402) {
					returnMap.put("status_msg", "This shop's plan does not have access to this feature");
				} else if (conn.getResponseCode() == 403) {
					returnMap.put("status_msg", "User does not have access");
				} else if (conn.getResponseCode() == 404) {
					returnMap.put("status_msg", "Not Found");
				} else if (conn.getResponseCode() == 422) {
					returnMap.put("status_msg", "The fulfillment order is not in an open state.");
				} else if (conn.getResponseCode() == 429) {
					returnMap.put("status_msg", "Exceeded 2 calls per second for api client. Reduce request rates to resume uninterrupted service.");
				} else {
					returnMap.put("status_msg", "An unexpected error occurred");
				}
				returnMap.put("status", "fail");
			}
			
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
	}
	
	
	private String convertHashMapToJsonString(HashMap<String, Object> map) {
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	
	public int getOrderListCount(Shopify shopifyDto) {
		
		String shopifyUrl = shopifyDto.getShopifyUrl();
		String queryString = "?financial_status=paid&fulfillment_status=unfulfilled&created_at_min="+shopifyDto.getDateTimeFrom()+"&created_at_max="+shopifyDto.getDateTimeTo();
		String httpUrl = "https://"+shopifyUrl+"/admin/api/2023-10/orders/count.json"+queryString;
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders(shopifyDto.getApiToken()));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			int orderCount = jsonObject.optInt("count", 0);
			return orderCount;
		} else {
			System.err.println(responseMap.get("status_msg"));
			return -1;
		}
		
	}
	
	
	public JSONArray getOrderList(Shopify shopifyDto) {

		String shopifyUrl = shopifyDto.getShopifyUrl();
		String queryString = "?financial_status=paid&fulfillment_status=unfulfilled&limit=250&created_at_min="+shopifyDto.getDateTimeFrom()+"&created_at_max="+shopifyDto.getDateTimeTo();
		String httpUrl = "https://"+shopifyUrl+"/admin/api/2023-10/orders.json"+queryString;
		
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders(shopifyDto.getApiToken()));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			JSONArray jsonArray = jsonObject.getJSONArray("orders");
			return jsonArray;
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	

	public String getFulfillmentOrder(Shopify shopifyDto, String orderId) {
		
		String shopifyUrl = shopifyDto.getShopifyUrl();
		String httpUrl = "https://"+shopifyUrl+"/admin/api/2023-10/orders/"+orderId+"/fulfillment_orders.json";
		HashMap<String, String> responseMap = apiGet(httpUrl, buildApiHeaders(shopifyDto.getApiToken()));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			JSONArray jsonArray = jsonObject.getJSONArray("fulfillment_orders");
			JSONObject orderObject = jsonArray.getJSONObject(0);
			String fulfillOrderId = orderObject.optString("id");
			
			return fulfillOrderId;
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	
	public Export getShopifyExportDeclareInfo(Order order) {
		Export export = new Export();
		String userId = order.getUserId().toUpperCase().trim();
		
		if ("ADMINCOREELLE".equals(userId)) {
			export.setNno(order.getNno());
			export.setUserId(order.getUserId());
			export.setExpType("E");
			export.setExpCor("주식회사 코리엘");
			export.setExpRprsn("조신일");
			export.setExpAddr("서울 강남구 테헤란로 325 (역삼동) 지하 1층, 코리엘");
			export.setExpZip("06151");
			export.setExpRgstrNo("2138641988");
			export.setExpCstCd("코리엘**1181017");
			export.setAgtCor("");
			export.setAgtCstCd("");
			export.setAgtBizNo("");
			export.setExpNo("");
			export.setSendYn("N");
			export.encryptData();
			return export;
		} else if ("ITSEL2".equals(userId)) {
			export.setNno(order.getNno());
			export.setUserId(order.getUserId());
			export.setExpType("E");
			export.setExpCor("주식회사 미투유코리아");
			export.setExpRprsn("장보윤");
			export.setExpAddr("서울특별시강서구마곡서로152, 비동 505호,506호");
			export.setExpZip("07788");
			export.setExpRgstrNo("3858700862");
			export.setExpCstCd("");
			export.setAgtCor("");
			export.setAgtCstCd("");
			export.setAgtBizNo("");
			export.setExpNo("");
			export.setSendYn("N");
			export.encryptData();
			return export;
		} else {
			return null;
		}
	}

	
	public ArrayList<ShopifyOrder> createFulfillment(Shopify shopifyDto) {
		ArrayList<ShopifyOrder> orders = new ArrayList<>();
		String shopifyUrl = shopifyDto.getShopifyUrl();
		String accessToken = shopifyDto.getApiToken();
		String httpUrl = "https://"+shopifyUrl+"/admin/api/2023-10/fulfillments.json";
		
		for (int i = 0; i < shopifyDto.getShopifyOrderList().size(); i++) {
			ShopifyOrder order = shopifyDto.getShopifyOrderList().get(i);
			
			String requestBody = convertHashMapToJsonString(setFulfillmentRequestBody(order));
			HashMap<String, String> responseMap = apiPost(httpUrl, requestBody, buildApiHeaders(accessToken));
			
			if ("success".equals(responseMap.get("status"))) {
				JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
				JSONObject fulfillment = jsonObject.getJSONObject("fulfillment");
				order.setFulfillmentId(fulfillment.optString("id"));
				orders.add(order);
			} else {
				System.err.println(responseMap.get("status_msg"));
			}
		}
		
		return orders;
	}
	
	
	private HashMap<String, Object> setFulfillmentRequestBody(ShopifyOrder order) {
		
		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> fulfillmentMap = new HashMap<>();
		HashMap<String, Object> trackingMap = new HashMap<>();
		HashMap<String, Object> fulfillOrder = new HashMap<>();
		ArrayList<HashMap<String, Object>> fulfillOrders = new ArrayList<HashMap<String, Object>>();
		
		fulfillOrder.put("fulfillment_order_id", order.getFulfillOrderId());
		fulfillOrders.add(fulfillOrder);
		
		trackingMap.put("company", order.getTransName());
		trackingMap.put("number", order.getHawbNo());
		
		fulfillmentMap.put("tracking_info", trackingMap);
		fulfillmentMap.put("notify_customer", true);
		fulfillmentMap.put("line_items_by_fulfillment_order", fulfillOrders);
		requestMap.put("fulfillment", fulfillmentMap);
		
		return requestMap;
	}
}
