package com.example.temp.api.shipment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.ExpLicenceExcelVO;
import com.google.gson.Gson;

@Service
public class ExportDeclareAPI {

	@Autowired
	ManagerMapper mngrMapper;
	
	public String createMusRequestBody(HashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();

		ArrayList<LinkedHashMap<String, Object>> itemList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> itemOne = new LinkedHashMap<String, Object>();
		
		ArrayList<ExpLicenceExcelVO> expLicenceItem = new ArrayList<ExpLicenceExcelVO>();
		ExpLicenceExcelVO expLicence = new ExpLicenceExcelVO();
		
		String nno = params.get("nno").toString();
		expLicenceItem = mngrMapper.selectExcelLicence(nno);
		HashMap<String, Object> expInfo = new HashMap<String, Object>();
		expInfo = mngrMapper.selectExportInfo(nno);
		expLicence = mngrMapper.selectExcelLicenceEFSV2(nno);
		
		String exporter = expInfo.get("expCor").toString();
		String agency = expInfo.get("agtCor").toString();
		
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		rtnJsonArray.put("req_date", dateTime);
		rtnJsonArray.put("req_com_cd", "ACIX");
		rtnJsonArray.put("req_cus_cd", "MUSI");
		rtnJsonArray.put("req_awb_cnt", "1");

		dataOne.put("hawb", expLicenceItem.get(0).getHawb());
		dataOne.put("agency", agency);
		dataOne.put("exporter", exporter);
		dataOne.put("maker", "미상");
		dataOne.put("buyer", expLicence.getCneeName());
		dataOne.put("dest_cd", expLicence.getDstnNation());
		dataOne.put("t_wt", expLicence.getWta());
		dataOne.put("t_pkg", expLicence.getBoxCnt());
		dataOne.put("c_unit", expLicence.getCurrency());
		dataOne.put("t_amt", expLicence.getTotalValue());
		dataOne.put("inv_no", expLicence.getHawb());
		dataOne.put("inco_gb", expLicence.getPayment());
		dataOne.put("doc_return_gb", "N");
		
		
		for (int i = 0; i < expLicenceItem.size(); i++) {
			itemOne = new LinkedHashMap<String, Object>();
			itemOne.put("g_pum", expLicenceItem.get(i).getItemDetail());
			itemOne.put("p_pum", "");
			itemOne.put("item_nm", expLicenceItem.get(i).getItemDetail());
			itemOne.put("brand", expLicenceItem.get(i).getBrand());
			itemOne.put("qty", expLicenceItem.get(i).getItemCnt());
			itemOne.put("up", expLicenceItem.get(i).getUnitValue());
			itemOne.put("amt", expLicenceItem.get(i).getValue());
			itemOne.put("hscode", expLicenceItem.get(i).getHsCode());
			itemList.add(itemOne);
		}
		
		dataOne.put("item_info", itemList);
		dataList.add(dataOne);
		rtnJsonArray.put("awb_info", dataList);
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(rtnJsonArray);
		
		return jsonVal;
	}
}
