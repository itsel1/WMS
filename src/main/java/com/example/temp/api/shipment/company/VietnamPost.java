package com.example.temp.api.shipment.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.api.shipment.VietnamPostCodeRegex;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Service
public class VietnamPost {

	@Autowired
	ShippingMapper shipMapper;
	
	private static String merchantToken = "48efc56a6b5a6ce7ff2134efb89b87c3";
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public VietnamPost() {
		apiHeader.put("Content-Type", "application/json");
	}


	public Object createRequestBody(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");
		
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<>();
		ArrayList<LinkedHashMap<String, Object>> data = new ArrayList<>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<>();
		ArrayList<LinkedHashMap<String, Object>> items = new ArrayList<>();
		LinkedHashMap<String, Object> itemOne = new LinkedHashMap<>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", nno);
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		
		orderInfo = shipMapper.selectTmpOrderListInfo(parameterInfo);
		itemInfoList = shipMapper.selectTmpOrderItemInfo(parameterInfo);
		
		orderInfo.dncryptData();
		
		String userId = orderInfo.getUserId();
		String shipperAddress = orderInfo.getShipperAddr();
		if (!orderInfo.getShipperAddrDetail().equals("")) {
			shipperAddress += " " + orderInfo.getShipperAddrDetail();
		}
		
		String cneeAddress = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			cneeAddress += " " + orderInfo.getCneeAddrDetail();
		}
		
		String cneeTel = orderInfo.getCneeTel();
		if (cneeTel.equals("")) {
			cneeTel = orderInfo.getCneeHp();
		}
		
		String size = "";
		String dimUnit = orderInfo.getDimUnit();
		
		double userWidth = Double.parseDouble(orderInfo.getUserWidth());
		double userHeight = Double.parseDouble(orderInfo.getUserHeight());
		double userLength = Double.parseDouble(orderInfo.getUserLength());
		
		int width = 0;
		int height = 0;
		int length = 0;
		
		// 1 inch = 2.54 cm -> (inch) * 2.54 == (cm)
		if (dimUnit.toUpperCase().equals("CM")) {
			width = (int) Math.ceil(userWidth);
			height = (int) Math.ceil(userHeight);
			length = (int) Math.ceil(userLength);
			size = width+"x"+height+"x"+length;
		} else {
			width = (int) Math.ceil(userWidth * 2.54);
			height = (int) Math.ceil(userWidth * 2.54);
			length = (int) Math.ceil(userLength * 2.54);
			size = width+"x"+height+"x"+length;
		}
		
		int grossWeight = 0;
		double weight = Double.parseDouble(orderInfo.getUserWta());
		String weightUnit = orderInfo.getWtUnit();
		
		if (weightUnit.toUpperCase().equals("G")) {
			grossWeight = (int) Math.ceil(weight);
		} else {
			switch (weightUnit.toUpperCase()) {
			case "KG":
				grossWeight = (int) Math.ceil(weight * 1000);
				break;
			case "LB":
				grossWeight = (int) Math.ceil(weight * 453.592);
				break;
			case "OZ":
				grossWeight = (int) Math.ceil(weight * 28.3495);
				break;
			default:
				break;
			}
		}
		
		String cneeCity = orderInfo.getCneeCity();
		String cneeDistrict = orderInfo.getCneeDistrict();
		String provinceCode = getProvinceCode(cneeCity);
		String districtCode = getDistrictCode(cneeDistrict);
		String toAddress = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			toAddress += " " + orderInfo.getCneeAddrDetail();
		}
		
		int serviceCode = 1;
		String itemType = "11";

		dataOne.put("from_country", "KR");
		dataOne.put("to_country", orderInfo.getDstnNation());
		dataOne.put("service", serviceCode);
		dataOne.put("zipcode", orderInfo.getCneeZip());
		dataOne.put("size", size);
		dataOne.put("ordercode", orderInfo.getOrderNo());
		dataOne.put("gross_weight", grossWeight);
		dataOne.put("from_name", orderInfo.getShipperName());
		dataOne.put("from_phone", orderInfo.getShipperTel());
		dataOne.put("from_province", orderInfo.getShipperCity());
		dataOne.put("from_address", shipperAddress);
		dataOne.put("to_name", orderInfo.getCneeName());
		dataOne.put("to_phone", cneeTel);
		dataOne.put("to_province", provinceCode);
		dataOne.put("to_district", districtCode);
		dataOne.put("to_address", toAddress);
		dataOne.put("description", "");
		dataOne.put("pickup_request", 2);
		dataOne.put("item_type", itemType);
		
		for (int i = 0; i < itemInfoList.size(); i++) {
			itemOne = new LinkedHashMap<>();
			
			int itemWeight = 0;
			double userWeight = itemInfoList.get(i).getUserWta();
			String itemWeightUnit = orderInfo.getWtUnit();
			
			if (itemWeightUnit.toUpperCase().equals("G")) {
				itemWeight = (int) Math.ceil(weight);
			} else {
				switch (itemWeightUnit.toUpperCase()) {
				case "KG":
					itemWeight = (int) Math.ceil(userWeight * 1000);
					break;
				case "LB":
					itemWeight = (int) Math.ceil(userWeight * 453.592);
					break;
				case "OZ":
					itemWeight = (int) Math.ceil(userWeight * 28.3495);
					break;
				default:
					break;
				}
			}

			
			String hsCode = itemInfoList.get(i).getHsCode().replaceAll("[^0-9]", "");
			String nativeItemDetail = itemInfoList.get(i).getNativeItemDetail();
			if (nativeItemDetail.equals("")) {
				nativeItemDetail = itemInfoList.get(i).getItemDetail();
			}
			
			int amount = 1;
			
			itemOne.put("hscode", hsCode);
			itemOne.put("description", nativeItemDetail);
			itemOne.put("description_eng", itemInfoList.get(i).getItemDetail());
			itemOne.put("amount", amount);
			itemOne.put("currency_code", itemInfoList.get(i).getChgCurrency());
			itemOne.put("weight", itemWeight);
			itemOne.put("quantity", itemInfoList.get(i).getItemCnt());
			itemOne.put("unit", itemInfoList.get(i).getQtyUnit());
			itemOne.put("country_of_manufacture", itemInfoList.get(i).getMakeCntry());
			
			items.add(itemOne);
		}
		
		dataOne.put("items", items);
		data.add(dataOne);
		requestData.put("data", data);
		
		return requestData;
	}

	public String getProvinceCode(String inputString) {
		return VietnamPostCodeRegex.getProvinceCode(inputString);
	}

	public String getDistrictCode(String inputString) {
		return VietnamPostCodeRegex.getDistrictCode(inputString);
	}

	public String getWardCode(String inputString) {
		return VietnamPostCodeRegex.getWardCode(inputString);
	}
}
