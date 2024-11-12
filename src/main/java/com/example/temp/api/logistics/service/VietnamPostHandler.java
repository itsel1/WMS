package com.example.temp.api.logistics.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.temp.api.CommonUtils;
import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.Tracking;
import com.example.temp.api.logistics.mapper.LogisticsMapper;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;



@Service
public class VietnamPostHandler {
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	CommonUtils utils;
	
	@Value("${filePath}")
	private String realFilePath;

	private static final String HOST = "http://ws.ems.com.vn";
	private static final String API_PATH = "/api/sandbox/orders";
	private static final String TOKEN = "48efc56a6b5a6ce7ff2134efb89b87c3";
	private static final Pattern DIACRITICS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	private List<Location> locations;
	private String filePath;
	
	
	public Order createOrder(Order order) {

		String httpUrl = HOST + API_PATH + "/create?merchant_token="+TOKEN;
		String rstMsg = "";
		
		HashMap<String, Object> requestBodyMap = getCreateOrderRequestBody(order);
		
		if (requestBodyMap != null) {
			
			String requestBody = utils.convertStringToMap(requestBodyMap);
			System.out.println(requestBody);
			
			HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, utils.buildApiDefaltHeaders());
			
			if ("success".equals(responseMap.get("status"))) {
				
				JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
				String code = jsonObject.optString("code").toUpperCase();
				
				if ("SUCCESS".equals(code)) {
					JSONObject dataObject = jsonObject.getJSONObject("data");
					String hawbNo = dataObject.optString("tracking_code");
					String labelUrl = dataObject.optString("label_url");
					
					order.setHawbNo(hawbNo);
					order.setLabelUrl(labelUrl);
					
				} else {
					rstMsg = jsonObject.optString("message");
				}
				
			} else {
				System.err.println(responseMap.get("status_msg"));
				rstMsg = "현지 배송사 시스템 연동 중 오류 발생,";
			}
		} else {
			rstMsg = "Province 또는 District 필드 값 분석 실패,";
		}

		order.setStatus(rstMsg);
		
		return order;
	}
	
	
	private HashMap<String, Object> getCreateOrderRequestBody(Order order) {
		String cneeProvince = order.getCneeCity();
		String cneeDistrict = order.getCneeDistrict();
		
		Location location = findLocationCodeByNormalizedFields(cneeProvince, cneeDistrict);
		
		if (location == null) {
			return null;
		}
		
		HashMap<String, Object> requestBody = new HashMap<>();

		String dutyType = "";
		if ("DDU".equals(order.getPayment().toUpperCase())) {
			dutyType = "DDU";
		} else {
			dutyType = "DDP";
		}
		
		String dimUnit = order.getDimUnit().toUpperCase();
		double userWidth = order.getUserWidth();
		double userHeight = order.getUserHeight();
		double userLength = order.getUserLength();

		if ("IN".equals(dimUnit) || "INCH".equals(dimUnit)) {
			userWidth = userWidth * 2.54;
			userHeight = userHeight * 2.54;
			userLength = userLength * 2.54;
		}
		
		int width = (int) Math.round(userWidth);
		int height = (int) Math.round(userHeight);
		int length = (int) Math.round(userLength);
		String size = length + "x" + width + "x" + height;
		
		String cneeAddress = order.getCneeAddr();
		if ("".equals(order.getCneeAddrDetail())) {
			cneeAddress += " " + order.getCneeAddrDetail();
		}
		
		int totalAmount = (int) order.getUnitValue();
		int totalQuantity = Integer.parseInt(order.getItemCnt());
		
		double userWta = order.getUserWta();
		String wtUnit = order.getWtUnit().toUpperCase();
		if ("KG".equals(wtUnit)) {
			userWta = userWta * 1000;
		} else if ("OZ".equals(wtUnit)) {
			userWta = userWta * 28.3495;
		} else if ("LB".equals(wtUnit)) {
			userWta = userWta * 453.592;
		}
		
		int totalWeight = (int) userWta;
		
		requestBody.put("order_code", order.getNno());
		requestBody.put("from_name", order.getShipperName());
		requestBody.put("from_phone", order.getShipperTel());
		requestBody.put("from_address", order.getShipperAddr());
		requestBody.put("to_name", order.getCneeName());
		requestBody.put("to_phone", order.getCneeTel());
		requestBody.put("to_province", location.getProvinceCode());
		requestBody.put("to_district", location.getDistrictCode());
		requestBody.put("to_ward", "");
		requestBody.put("to_address", cneeAddress);
		requestBody.put("product_name", order.getItemDetail());
		requestBody.put("total_amount", totalAmount);
		requestBody.put("total_quantity", totalQuantity);
		requestBody.put("total_weight", totalWeight);
		requestBody.put("money_collect", 0);
		requestBody.put("description", order.getDlvReqMsg());
		requestBody.put("size", size);
		requestBody.put("service", 21);
		requestBody.put("checked", false);
		requestBody.put("fragile", false);
		requestBody.put("delivery_duty_type", dutyType);
		
		return requestBody;
	}
	
	
	public void cancelOrder(Order order) {
		
		String httpUrl = HOST + API_PATH + "/manual-cancel-order?merchant_token="+TOKEN;
		
		HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
		requestBodyMap.put("tracking_code", order.getHawbNo());
		String requestBody = utils.convertStringToMap(requestBodyMap);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, utils.buildApiDefaltHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject.optString("message"));
		} else {
			System.err.println(responseMap.get("status_msg"));
		}
		
	}
	
	
	public ArrayList<Tracking> getOrderTrackingDetails(String hawbNo) {
		
		String httpUrl = HOST + API_PATH + "/detail/"+hawbNo+"?merchant_token="+TOKEN;
		
		HashMap<String, String> responseMap = utils.apiGet(httpUrl, utils.buildApiDefaltHeaders());
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String code = jsonObject.optString("code").toUpperCase();
			
			if ("SUCCESS".equals(code)) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				System.out.println(dataObject);
				JSONArray dataList = dataObject.getJSONArray("__get_status");
				ArrayList<Tracking> vnpTrkList = getVietnamPostTrackingList(dataList);
				return vnpTrkList;
			} else {
				System.err.println(jsonObject.optString("message"));
				return null;
			}
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
		
	}


	private ArrayList<Tracking> getVietnamPostTrackingList(JSONArray jsonArray) {
		ArrayList<Tracking> trackingList = new ArrayList<Tracking>();
		
		for (int i = jsonArray.length() - 1; i >= 0; i--) {
			JSONObject dataObject = jsonArray.getJSONObject(i);
			Tracking trkInfo = new Tracking();
			String code = dataObject.optString("status");
			String location = dataObject.optString("address");
			String traceDate = dataObject.getString("tracedate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime localDateTime = LocalDateTime.parse(traceDate, formatter);
			ZonedDateTime vietnamTime = localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
			ZonedDateTime koreaTime = vietnamTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
			String dateTime = koreaTime.format(formatter);
			
			if ("13".equals(code)) {
				trkInfo.setStatusCode("600");
				trkInfo.setDescription("Shipment returned");
			} else if ("9".equals(code)) {
				trkInfo.setStatusCode("400");
				trkInfo.setDescription("Shipment destroyed under instruction by Asendia");
			} else if ("8".equals(code)) {
				trkInfo.setStatusCode("500");
				trkInfo.setDescription("Shipment return is processing");
			} else if ("7".equals(code)) {
				trkInfo.setStatusCode("600");
				trkInfo.setDescription("Successful delivered");
			} else if ("6".equals(code)) {
				trkInfo.setStatusCode("600");
				trkInfo.setDescription("Unsuccessful delivered");
			} else if ("5".equals(code)) {
				trkInfo.setStatusCode("500");
				trkInfo.setDescription("Shipment is out for delivery");
			} else if ("4".equals(code)) {
				trkInfo.setStatusCode("500");
				trkInfo.setDescription("Shipment departed from Office of Exchaning");
			} else if ("3".equals(code)) {
				trkInfo.setStatusCode("480");
				trkInfo.setDescription("Shipment clearance processing completed");
			} else if ("2".equals(code)) {
				trkInfo.setStatusCode("400");
				trkInfo.setDescription("Shipment arrived Vietnam");
			} else {
				continue;
			}
			
			trkInfo.setLocation(location);
			trkInfo.setDateTime(dateTime);
			trackingList.add(trkInfo);
			
		}
		
		return trackingList;
	}


	@PostConstruct
	public void init() throws IOException {
		this.filePath = realFilePath + "excel/vnp_code_list.csv";
		this.locations = loadCodeData(filePath);
	}
	

	private List<Location> loadCodeData(String path) throws IOException {

		List<Location> locations = new ArrayList<>();
		
        try (InputStream inputStream = new FileInputStream(filePath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {

               String firstLine = bufferedReader.readLine();
               if (firstLine != null && firstLine.startsWith("\uFEFF")) {
                   firstLine = firstLine.substring(1);
               }

               CSVParser csvParser = CSVParser.parse(
                   bufferedReader,
                   CSVFormat.DEFAULT.withFirstRecordAsHeader().withHeader(
                       "province_code", "province_description", "province_eng_name", "district_code", "district_description", "district_eng_name")
               );

               for (CSVRecord csvRecord : csvParser) {
                   Location location = new Location(
                           csvRecord.get("province_code"),
                           csvRecord.get("province_description"),
                           csvRecord.get("province_eng_name"),
                           csvRecord.get("district_code"),
                           csvRecord.get("district_description"),
                           csvRecord.get("district_eng_name")
                   );
                   locations.add(location);
               }
           }
           return locations;
	}
	
	
	public Location findLocationCodeByNormalizedFields(String province, String district) {
        String normalizedProvince = normalize(province);
        String normalizedDistrict = normalize(district);

        for (Location location : locations) {
            if (matchesProvince(location, normalizedProvince) && matchesDistrict(location, normalizedDistrict)) {
                return location;
            }
        }
        return null;
    }
	
	
	private boolean matchesProvince(Location location, String normalizedProvince) {
        return normalize(location.getProvinceDescription()).equals(normalizedProvince) ||
               normalize(location.getProvinceEngName()).equals(normalizedProvince);
    }
	

    private boolean matchesDistrict(Location location, String normalizedDistrict) {
        String districtDescription = normalize(location.getDistrictDescription());
        String districtEngName = normalize(location.getDistrictEngName());

        if (normalizedDistrict.toLowerCase().startsWith("district")) {
            String districtNumber = normalizedDistrict.substring(8).trim();
            return districtDescription.equals("quan" + districtNumber) ||
                   districtEngName.equals("quan" + districtNumber);
        }

        return districtDescription.equals(normalizedDistrict) ||
               districtEngName.equals(normalizedDistrict);
    }
    

    private static String normalize(String input) {
        if (input == null) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return DIACRITICS_PATTERN.matcher(normalized).replaceAll("").toLowerCase().replaceAll("\\s+", "");
    }

	
	public static class Location {
		private String provinceCode;
		private String provinceDescription;
		private String provinceEngName;
		private String districtCode;
		private String districtDescription;
		private String districtEngName;
		
		public Location(String provinceCode, String provinceDescription, String provinceEngName, 
				String districtCode, String districtDescription, String districtEngName) {
			this.provinceCode = provinceCode;
			this.provinceDescription = provinceDescription;
			this.provinceEngName = provinceEngName;
			this.districtCode = districtCode;
			this.districtDescription = districtDescription;
			this.districtEngName = districtEngName;
		}
		
		public String getProvinceCode() {
			return provinceCode;
		}
		
		public String getProvinceDescription() {
			return provinceDescription;
		}
		
		public String getProvinceEngName() {
			return provinceEngName;
		}
		
		public String getDistrictCode() {
			return districtCode;
		}
		
		public String getDistrictDescription() {
			return districtDescription;
		}
		
		public String getDistrictEngName() {
			return districtEngName;
		}

	}
}
