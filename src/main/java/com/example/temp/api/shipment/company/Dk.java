package com.example.temp.api.shipment.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ExportVO;
import com.example.temp.api.shipment.ShippingMapper;

@Service
public class Dk {

	@Autowired
	ShippingMapper shipMapper;
	
	private static String agentKey = "7BB8278A5E7847E5BB4EF7AB76E78906";
	private static String dataVersion = "1.0";
	private static String dataFormat = "JSON";
	private static String expressCode = "AE0087";										// 특송업체부호
	private static String goodsPost = "22839";											// 물품소재지 우편번호
	private static String goodsAddr = "인천광역시 서구 가좌동 585-82 ACI 월드와이드";	// 물품소재지 주소
	private static String shedNm = "ACI 월드와이드";									// 장치장소명 (물품소재지명)
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public Dk() {
		apiHeader.put("Content-Type", "application/json; charset=utf-8");
	}
	
	
	public Object createRequestBody(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");

		ExportVO expInfo = shipMapper.selectExportDelareRequestBody(params);
		expInfo.decryptData();
		
		String orderNo = expInfo.getOrderNo();
		String convertStr = "";
		
		if (orderNo.length() > 30) {
			orderNo = orderNo.substring(0, 30);
		}
		String orderDate = expInfo.getOrderDate();
		String orderBillNo = expInfo.getHawbNo();
		String cstBrkCode = "";
		String extrAgtCstmsCode = expInfo.getAgtCstCd();
		String extrAgtBizsiteSeq = expInfo.getAgtBizNo();
		String extrAgtCmpName = expInfo.getAgtCor();
		String extrCmpName = expInfo.getExpCor();
		String extrBossName = expInfo.getExpRprsn();
		String extrAddr = expInfo.getExpAddr();
		String extrPostCode = expInfo.getExpZip();
		String extrBizNo = expInfo.getExpRgstrNo();// 숫자만
		extrBizNo = extrBizNo.replaceAll("[^0-9]","");
		String extrCstmsCode = expInfo.getExpCstCd();
		String makrCmpName = "";
		String makrPost = "";
		String makrCstmsCode = "";
		String byerCmpName = expInfo.getCneeName();
		String exptDestNation = expInfo.getDstnNation();
		String portLoding = "";
		String portLodingGbn = "";
		String exptPaymtMethod = "";
		String shptAmtUnit = expInfo.getUnitCurrency();
		String shptAmt = expInfo.getTotalItemValue();
		String shptPkg = expInfo.getBoxCnt();
		String shptPkgUnit = "";
		String shptWt = expInfo.getWta();
		String shptWtUnit = expInfo.getWtUnit();
		int freightAmt = 0;
		String cstBrkCmt= "";
		String freeTradeYn = "";
		String shptDiscountAmt = "";
		String exptDlvrCondition = expInfo.getPayment();
		

		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> requestDataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> requestOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> itemDataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> itemOne = new LinkedHashMap<String, Object>();
		

		requestOne.put("OrderNo", orderNo);
		requestOne.put("OrderDate", orderDate);
		requestOne.put("OrderBillNo", orderBillNo);
		requestOne.put("CstBrkCode", cstBrkCode);
		requestOne.put("ExtrAgtCstmsCode", extrAgtCstmsCode);
		requestOne.put("ExtrAgtBizsiteSeq", extrAgtBizsiteSeq);
		requestOne.put("ExtrAgtCmpName", extrAgtCmpName);
		requestOne.put("ExtrCmpName", extrCmpName);
		requestOne.put("ExtrBossName", extrBossName);
		requestOne.put("ExtrAddr", extrAddr);
		requestOne.put("ExtrPostCode", extrPostCode);
		requestOne.put("ExtrBizNo", extrBizNo);
		requestOne.put("ExtrCstmsCode", extrCstmsCode);
		requestOne.put("MakrCmpName", makrCmpName);
		requestOne.put("MakrPost", makrPost);
		requestOne.put("MakrCstmsCode", makrCstmsCode);
		requestOne.put("ByerCmpName", byerCmpName);
		requestOne.put("ExptDestNation", exptDestNation);
		requestOne.put("PortLoding", portLoding);
		requestOne.put("PortLodingGbn", portLodingGbn);
		requestOne.put("ExptPaymtMethod", exptPaymtMethod);
		requestOne.put("ShptAmtUnit", shptAmtUnit);
		requestOne.put("ShptAmt", shptAmt);
		requestOne.put("ShptPkg", shptPkg);
		requestOne.put("ShptPkgUnit", shptPkgUnit);
		requestOne.put("ShptWt", shptWt);
		requestOne.put("ShptWtUnit", shptWtUnit);
		requestOne.put("FreightAmt", freightAmt);
		requestOne.put("CstBrkCmt", cstBrkCmt);
		requestOne.put("FreeTradeYn", freeTradeYn);
		requestOne.put("ShptDiscountAmt", shptDiscountAmt);
		requestOne.put("ExprsCd", expressCode);
		requestOne.put("ExptDlvrCondition", exptDlvrCondition);
		requestOne.put("GoodsPost", goodsPost);
		requestOne.put("GoodsAddr", goodsAddr);
		requestOne.put("ShedNm", shedNm);

		String itemInfo = expInfo.getItemInfo();
		String[] itemArrays = itemInfo.split("\\|\\^\\|");
		for (int roop= 0; roop < itemArrays.length; roop++) {
			itemOne = new LinkedHashMap<String, Object>();
			
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
			
			itemOne.put("ItemSeq", subNo);
			itemOne.put("ItemQty", itemCnt);
			itemOne.put("ItemUnitPrice", unitValue);
			itemOne.put("ItemAmt", itemValue);
			itemOne.put("ItemMatrial", "");
			itemOne.put("ItemName", itemDetail);
			itemOne.put("ItemProductNo", "");
			itemOne.put("ItemHsCode", hsCode);
			itemOne.put("ItemGiftYn", "");
			itemOne.put("ItemDiscountAmt", "");
			itemOne.put("SmplFixDrwbYn", "");

			itemDataList.add(itemOne);
		}
		
		requestOne.put("OrderItems", itemDataList);
		requestDataList.add(requestOne);
		requestData.put("Request", requestDataList);
		
		return requestData;
	}
	
	
	public HashMap<String, Object> createExportDeclareNumber(HashMap<String, Object> params, String requestVal) {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			
			String nno = (String) params.get("nno");
			/*
			UUID uid = UUID.randomUUID();
			String transferId = uid.toString();
			*/
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
			String transferId = dateFormat.format(date);
			
			String operation = "apply";
			String validation = "";
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(requestVal.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		    }
			
			validation = sb.toString();
			
			String url = "https://api.dxlnc.net/aci/ExpEcomm?TransferId="+transferId+"&AgentKey="+agentKey+"&DataVersion="
						+dataVersion+"&DataFormat="+dataFormat+"&Operation="+operation+"&Validation="+validation;
			
			System.out.println(url);
			
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);
			
			if ("".equals(responseStr)) {
				System.out.println("fail to create dk export declare no");
				rst.put("status", "FAIL");
				
			} else {
				System.out.println(responseStr);
				rst.put("status", "SUCCESS");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rst;
	}

}
