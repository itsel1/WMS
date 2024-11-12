package com.example.temp.api.shipment.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ExportVO;
import com.example.temp.api.shipment.ShippingMapper;

@Service
public class Mus {
	
	@Autowired
	ShippingMapper shipMapper;
	
	private static String reqComCd = "ACIX";
	private static String reqCusCd = "MUSI";
	private static String maker = "미상";
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public Mus() {
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Accept-Language", "ko-KR");
	}
	
	public Object createRequestBody(HashMap<String, Object> params) {
		
		ExportVO expInfo = shipMapper.selectExportDelareRequestBody(params);
		expInfo.decryptData();
		
		JSONObject requestData = new JSONObject();
		JSONArray requestDataList = new JSONArray();
		JSONObject requestOne = new JSONObject();
		JSONArray itemDataList = new JSONArray();
		JSONObject itemOne = new JSONObject(); 
		
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		requestData.put("req_date", dateTime);
		requestData.put("req_com_cd", reqComCd);
		requestData.put("req_cus_cd", reqCusCd);
		requestData.put("req_awb_cnt", 1);

		requestOne.put("hawb", expInfo.getHawbNo());
		requestOne.put("agency", expInfo.getAgtCor());
		requestOne.put("exporter", expInfo.getExpCor());
		requestOne.put("maker", maker);
		requestOne.put("buyer", expInfo.getCneeName());
		requestOne.put("dest_cd", expInfo.getDstnNation());
		requestOne.put("t_wt", expInfo.getWta());
		requestOne.put("t_pkg", expInfo.getBoxCnt());
		requestOne.put("c_unit", expInfo.getUnitCurrency());
		requestOne.put("t_amt", expInfo.getTotalItemValue());
		requestOne.put("inv_no", expInfo.getHawbNo());
		requestOne.put("doc_return_gb", "N");
		requestOne.put("inco_gb", expInfo.getPayment());
		
		String itemInfo = expInfo.getItemInfo();
		String[] itemArrays = itemInfo.split("\\|\\^\\|");
		for (int roop= 0; roop < itemArrays.length; roop++) {
			itemOne = new JSONObject();
			
			String itemSet = itemArrays[roop];
			if (itemSet.isEmpty()) {
				continue;
			}
			
			String[] itemPart = itemSet.split("\\|");
			String subNo = itemPart[0];
			String itemCnt = itemPart[1];
			String unitValue = itemPart[2];
			String itemDetail = itemPart[3];
			String hsCode = itemPart[4];
			hsCode = hsCode.replaceAll("[^0-9]", "");
			String unitCurrency = itemPart[5];
			String itemValue = itemPart[6];
			String brand = itemPart[7];
			
			itemOne.put("g_pum", itemDetail);
			itemOne.put("p_pum", "");
			itemOne.put("item_nm", itemDetail);
			itemOne.put("brand", brand);
			itemOne.put("qty", itemCnt);
			itemOne.put("up", unitValue);
			itemOne.put("amt", itemValue);
			itemOne.put("hscode", hsCode);
			
			itemDataList.put(itemOne);
		}
		
		requestOne.put("item_info", itemDataList);
		
		requestDataList.put(requestOne);
		requestData.put("awb_info", requestDataList);
		
		return requestData;
	}
	
	public HashMap<String, Object> createExportDeclareNumber(HashMap<String, Object> params, String requestVal) {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		String nno = (String) params.get("nno");
		String expNo = "";
		String hawbNo = "";
		String status = "";
		
		String url = "https://if.tlogin.net/aci/exp/json/receive.do";
		
		ApiAction action = ApiAction.getInstance();
		String responseStr = action.sendPost(requestVal, url, apiHeader);
		
		System.out.println(responseStr);
		
		if ("".equals(responseStr)) {
			status = "FAIL";
		} else {
			JSONObject response = new JSONObject(responseStr);
			String resultCd = response.optString("result_cd");
			
			if (resultCd.toUpperCase().equals("S")) {
				JSONObject resultData = (JSONObject) response.optJSONObject("result_data");
				JSONArray awbResult = new JSONArray(String.valueOf(resultData.optString("awb_result")));
				JSONObject awbResultOne = (JSONObject) awbResult.get(0);
				
				status = "SUCCESS";
				expNo = awbResultOne.optString("expo_singo_no");
				hawbNo = awbResultOne.optString("hawb");
				
			} else {
				status = "FAIL";
			}
			
		}
		
		rst.put("status", status);
		rst.put("nno", nno);
		rst.put("expNo", expNo);
		rst.put("hawbNo", hawbNo);
		
		return rst;
	}

}
