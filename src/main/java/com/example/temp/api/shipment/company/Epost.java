package com.example.temp.api.shipment.company;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.example.temp.api.SAXParserHandler;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SEED128;

@Service
public class Epost {
	
	@Autowired
	ShippingMapper shipMapper;
	
	/* 우체국 API Auth Key
	 * custNo = 계약번호
	 * apprNo = 고객승인번호
	 * authKey = 인증키 (API 30일간 미호출 시 사용기간 만료, 재신청 필요
	 * secretKey 비밀키 (SEED128 암호화 키)
	 */
	/* 2024.03.23 ~ 우체국 계약 코드 변경
	private static String custNo = "0005019394";
	private static String apprNo = "4003181674";
	private static String authKey = "3a18c0024fe0c7fa81703204460327";
	private static String secretKey = "3a18c0024fe0c7fa";
	private static String officeSer = "231221001";
	*/
	private static String custNo = "0004464443";
	private static String apprNo = "4001380915";
	private static String authKey = "a218dd5b2cfe77f431710201718111";
	private static String secretKey = "a218dd5b2cfe77f4";
	private static String officeSer = "240318496";
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public Epost() {
		apiHeader.put("Connection", "keep-alive");
		apiHeader.put("Host", "ship.epost.go.kr");
		apiHeader.put("User-Agent", "Apache-HttpClient/4.5.1 (Java/1.8.0_91)");
	}
	
	public String createRequestBody(HashMap<String, Object> params) {
		
		String requestVal = "";
		String nno = (String) params.get("nno");
		params.put("subNo", "1");
		UserOrderListVO orderInfo = new UserOrderListVO();
		UserOrderItemVO itemInfo = new UserOrderItemVO();
		
		try {
		
		orderInfo = shipMapper.selectTmpOrderListInfo(params);
		orderInfo.dncryptData();
		itemInfo = shipMapper.selectTmpOrderItemInfoOne(params);
		
		int weight = (int) Math.ceil(Double.parseDouble(orderInfo.getUserWta()));
		double height = Double.parseDouble(orderInfo.getUserHeight());
		double width = Double.parseDouble(orderInfo.getUserWidth());
		double length = Double.parseDouble(orderInfo.getUserLength());
		int volume = (int) Math.ceil(height+width+length);
		
		// 주문정보
		requestVal += "custNo="+custNo;
		requestVal += "&apprNo="+apprNo;
		requestVal += "&payType=1&reqType=1&officeSer="+officeSer;
		requestVal += "&microYn=N";
		requestVal += "&orderNo="+nno;
		requestVal += "&insuYn=N";
		requestVal += "&ordCompNm="+orderInfo.getShipperName();
		requestVal += "&recNm="+orderInfo.getCneeName();
		requestVal += "&recZip="+orderInfo.getCneeZip();
		requestVal += "&recAddr1="+orderInfo.getCneeAddr();
		requestVal += "&recAddr2="+orderInfo.getCneeAddrDetail();
		if (orderInfo.getCneeTel().equals("")) {
			orderInfo.setCneeTel(orderInfo.getCneeHp());
		}
		String cneeTel = orderInfo.getCneeTel().replaceAll("[^0-9]", "");
		String cneeHp = orderInfo.getCneeHp().replaceAll("[^0-9]", "");
		requestVal += "&recTel="+cneeTel;
		requestVal += "&recMob="+cneeHp;
		
		// 상품 정보 (대표 상품 1개)
		requestVal += "&contCd=029";
		requestVal += "&goodsNm="+itemInfo.getItemDetail();
		requestVal += "&qty="+itemInfo.getTotalItemCnt();
		requestVal += "&testYn=N";
		
		System.out.println(requestVal);
		
		SEED128 seed = new SEED128();
		requestVal = seed.getEncryptData(secretKey, requestVal);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requestVal;
	}

	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String errMsg = "";
		
		try {
			
			String url = "http://ship.epost.go.kr/api.InsertOrder.jparcel?key="+authKey+"&regData="+requestVal;
			
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost("", url, apiHeader);
			
			if (!responseStr.equals("")) {
				
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				SAXParserHandler handler = new SAXParserHandler();
				parser.parse(new InputSource(new StringReader(responseStr)), handler);
				Map<String, String> dataMap = handler.getDataMap();
				
				if (!dataMap.containsKey("error_code")) {
					String hawbNo = dataMap.get("regiNo");
					rstVal.setRstStatus("SUCCESS");
					rstVal.setRstCode("S");
					rstVal.setRstHawbNo(hawbNo);
					
				} else {
					errMsg = dataMap.get("message");
				}
				
			} else {
				errMsg = "API 응답 데이터 없음";
			}
		} catch (Exception e) {
			if (!errMsg.equals("")) {
				errMsg += ", " + e.getMessage();
			} else {
				errMsg += e.getMessage();
			}
		}

		if (!errMsg.equals("")) {
			rstVal.setRstStatus("FAIL");
			rstVal.setRstCode("F");
			rstVal.setRstMsg(errMsg);
		}
		
		return rstVal;
	}


}
