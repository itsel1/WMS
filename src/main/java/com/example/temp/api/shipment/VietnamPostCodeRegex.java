package com.example.temp.api.shipment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.text.Normalizer;

import java.io.Reader;
import java.io.FileReader;

public class VietnamPostCodeRegex {
	
	private static final Map<String, VietnamPostProvince> provinceMap = new ConcurrentHashMap<>();
	private static final Map<String, VietnamPostDistrict> districtMap = new ConcurrentHashMap<>();
	private static final Map<String, VietnamPostWard> wardMap = new ConcurrentHashMap<>();
	private static final Map<String, String> englishToVietnameseMap = new HashMap<>();
	private static final String provinceFiles = "C:\\Temp\\excel\\province.csv";
	private static final String districtFiles = "C:\\Temp\\excel\\district.csv";
	private static final String wardFiles = "C:\\Temp\\excel\\ward.csv";
	
	
	static {
		try {
			englishToVietnameseMap.put("city", "thành phố");
	        englishToVietnameseMap.put("district", "quận");
	        englishToVietnameseMap.put("ward", "phường");
	        
			loadProvinceData(provinceFiles);
			loadDistrictData(districtFiles);
			loadWardData(wardFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadProvinceData(String filePath) throws IOException {
		try (Reader in = new FileReader(filePath)) {
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("code", "province").parse(in);
			for (CSVRecord record : records) {
				String province = normalizeName(record.get("province"));
				String code = record.get("code");
				provinceMap.put(province, new VietnamPostProvince(record.get("province"), code));
			}

		}
	}

	private static void loadDistrictData(String filePath) throws IOException {
		try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("code", "district", "districtEng").parse(in);
            for (CSVRecord record : records) {
                String district = normalizeName(record.get("district"));
                String districtEng = normalizeName(record.get("districtEng"));
            	String code = record.get("code");
                districtMap.put(district, new VietnamPostDistrict(record.get("district"), record.get("districtEng"), code));
                districtMap.put(districtEng, new VietnamPostDistrict(record.get("district"), record.get("districtEng"), code));
            }
        }
	}

	private static void loadWardData(String filePath) throws IOException {
		try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("code", "ward", "wardEng").parse(in);
            for (CSVRecord record : records) {
                String ward = normalizeName(record.get("ward"));
                String wardEng = normalizeName(record.get("wardEng"));
            	String code = record.get("code");
            	wardMap.put(ward, new VietnamPostWard(record.get("ward"), record.get("wardEng"), code));
                wardMap.put(wardEng, new VietnamPostWard(record.get("ward"), record.get("wardEng"), code));
            }
        }
	}

	private static String normalizeName(String data) {
		if (data == null) {
			return null;
		}

        for (Map.Entry<String, String> entry : englishToVietnameseMap.entrySet()) {
        	data = data.replaceAll("(?i)" + entry.getKey(), entry.getValue());
        }
		
		String normalized = Normalizer.normalize(data, Normalizer.Form.NFD);
		normalized = normalized.replaceAll("\\p{M}", "");
        normalized = normalized.replaceAll("\\s+", "").toLowerCase();

        return normalized;
	}

	public static String getProvinceCode(String inputString) {
		String normalizedInput = normalizeName(inputString);
		VietnamPostProvince province = provinceMap.get(normalizedInput);
		return (province != null) ? province.getCode() : null;
	}
	
	public static String getDistrictCode(String inputString) {
		String normalizedInput = normalizeName(inputString);
		VietnamPostDistrict district = districtMap.get(normalizedInput);
		return (district != null) ? district.getCode() : null;
	}
	
	public static String getWardCode(String inputString) {
		String normalizedInput = normalizeName(inputString);
		VietnamPostWard ward = wardMap.get(normalizedInput);
		return (ward != null) ? ward.getCode() : null;
	}
}
