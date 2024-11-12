package com.example.temp.trans.yongsung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazonaws.client.AwsSyncClientParams;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;

@Service
public class YongSungAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	YongSungMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	MemberMapper usrMapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public synchronized String fnMakeYongsungJsonTakeIn(String nno) throws Exception{
		
		String jsonVal = makeYongsungJsonTakeIn(nno);
		
		String rtnVal = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL("https://eparcel.kr/apiv2/RegData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
	}
	
	public synchronized String fnMakeYongsungJson(String nno) throws Exception{
		
		String jsonVal = makeYongsungJson(nno);
		System.out.println("YSL REQUEST BODY:");
		System.out.println(jsonVal);
		
		
		String rtnVal = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL("https://eparcel.kr/apiv2/RegData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("==rst==");
		System.out.println(outResult.toString());
		System.out.println("==/rst==");
		

		
		return outResult.toString();
	}
	
	
	public String makeYongsungJson(String nno) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> goodsList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> goodsOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderItemYSVO> orderItem = new ArrayList<ApiOrderItemYSVO>();
		ArrayList<ApiOrderYSVO> orderInfo = new ArrayList<ApiOrderYSVO>();
		
		try {
			
			orderInfo = mapper.selectListInfoForYS(nno);
			
			for(int i = 0; i < orderInfo.size(); i++) {
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
				dataOne = new LinkedHashMap<String, Object>();
				goodsList = new ArrayList<LinkedHashMap<String, Object>>();
				dataOne.put("NationCode", orderInfo.get(i).getDstnNation());
				if(orderInfo.get(i).getGetBuy().equals("") || orderInfo.get(i).getGetBuy().equals("1")) {
					dataOne.put("ShippingType", "EP_REG");
				}else {
					dataOne.put("ShippingType", "EP_TAX");
				}
				dataOne.put("OrderNo1", orderInfo.get(i).getNno());
				dataOne.put("OrderNo2", orderInfo.get(i).getOrderNo());
				dataOne.put("SenderName", orderInfo.get(i).getShipperName());
				dataOne.put("SenderTelno", orderInfo.get(i).getShipperTel());
				dataOne.put("SenderAddr", orderInfo.get(i).getShipperAddr()+" "+orderInfo.get(i).getShipperAddrDetail());
				dataOne.put("ReceiverName", orderInfo.get(i).getNativeCneeName());
				dataOne.put("ReceiverNameYomigana", orderInfo.get(i).getCneeName());
				dataOne.put("ReceiverTelNo1", orderInfo.get(i).getCneeTel());
				dataOne.put("ReceiverTelNo2", orderInfo.get(i).getCneeHp());
				dataOne.put("ReceiverZipcode", orderInfo.get(i).getCneeZip());
				String addrInfo = "";
				
				if(orderInfo.get(i).getDstnNation().equals("JP")) {
					dataOne.put("ReceiverState", "");//일본용 맞춤 삭제
					dataOne.put("ReceiverCity", "");//일본용 맞춤 삭제
					if(orderInfo.get(i).getCneeState().contains("JP-")) {
						addrInfo += mapper.selectStateNameByCode(orderInfo.get(i).getCneeState());
						// 추가
						System.out.println("Cnee State가 JP-로 시작하는 경우 : "+addrInfo);
					}else if(!Pattern.matches("^[0-9a-zA-Z가-힣]*$", orderInfo.get(i).getCneeState())) {
						addrInfo += orderInfo.get(i).getCneeState();
					}else {
						if(orderInfo.get(i).getCneeState() != null && !orderInfo.get(i).getCneeState().equals("")) {
							
							String tempState = orderInfo.get(i).getCneeState().replaceAll("[^A-Za-z]","%");
							if(mapper.selectStateNameByName(tempState)!= null) {
								addrInfo += mapper.selectStateNameByName(tempState);
								System.out.println("Cnee State가 null이 아닌 경우 : "+addrInfo);
							}else {
								addrInfo += tempState;
								System.out.println("Cnee State가 null인 경우 : " + addrInfo);
							}
						}
					}
					
					if(!orderInfo.get(i).getNativeCneeAddr().equals("")) {
						addrInfo += orderInfo.get(i).getCneeCity()+orderInfo.get(i).getNativeCneeAddr()+orderInfo.get(i).getNativeCneeAddrDetail();
						System.out.println("test 1 : " + addrInfo);
					}else {
						addrInfo += orderInfo.get(i).getCneeCity()+orderInfo.get(i).getCneeAddr()+orderInfo.get(i).getCneeAddrDetail();
						System.out.println("test 2 : " + addrInfo);
					}
					
					
					//addrInfo += orderInfo.get(i).getCneeAddr()+" "+orderInfo.get(i).getCneeAddrDetail();
				}else if(orderInfo.get(i).getDstnNation().equals("HK")){
					dataOne.put("ReceiverState", orderInfo.get(i).getCneeState());//홍콩
					dataOne.put("ReceiverCity", orderInfo.get(i).getCneeCity());//홍콩
					dataOne.put("ReceiverDistrict", orderInfo.get(i).getCneeDistrict());//홍콩
					addrInfo += orderInfo.get(i).getCneeAddr() + " "+ orderInfo.get(i).getCneeAddrDetail();
				}else {
					dataOne.put("ReceiverState", orderInfo.get(i).getCneeState());//타이완
					dataOne.put("ReceiverCity", orderInfo.get(i).getCneeCity());//타이완
					addrInfo += orderInfo.get(i).getCneeAddr() + " "+ orderInfo.get(i).getCneeAddrDetail();
				}
					
				
				//dataOne.put("ReceiverDistrict", orderInfo.get(i).getCneeCntry());//변경 예정
				
				dataOne.put("ReceiverDetailAddr",addrInfo);
				System.out.println("최종 addr =====>");
				System.out.println(addrInfo);
				dataOne.put("ReceiverEmail", orderInfo.get(i).getCneeEmail());
				dataOne.put("ReceiverSocialNo", orderInfo.get(i).getCustomsNo());
				dataOne.put("RealWeight", Double.parseDouble(orderInfo.get(i).getUserWta()));
				dataOne.put("WeightUnit", orderInfo.get(i).getWtUnit());
				dataOne.put("BoxCount", Integer.parseInt(orderInfo.get(i).getBoxCnt()));
				if(orderInfo.get(i).getChgCurrency().equals("") || orderInfo.get(i).getChgCurrency() == null) {
					dataOne.put("CurrencyUnit", orderInfo.get(i).getUnitCurrency());
				}else {
					dataOne.put("CurrencyUnit", orderInfo.get(i).getChgCurrency());
				}
				
				dataOne.put("DelvMessage", orderInfo.get(i).getDlvReqMsg());
				dataOne.put("UserData1", orderInfo.get(i).getUserData1());
				dataOne.put("UserData2", orderInfo.get(i).getUserData2());
				dataOne.put("UserData3", orderInfo.get(i).getUserData3());
				dataOne.put("DimWidth", Double.parseDouble(orderInfo.get(i).getUserWidth()));
				dataOne.put("DimLength", Double.parseDouble(orderInfo.get(i).getUserLength()));
				dataOne.put("DimHeight", Double.parseDouble(orderInfo.get(i).getUserHeight()));
				dataOne.put("DimUnit", orderInfo.get(i).getDimUnit());
				dataOne.put("DelvNo", orderInfo.get(i).getDelvNo());
				dataOne.put("DelvCom", orderInfo.get(i).getDelvCom());
				if(orderInfo.get(i).getPayment().equals("DDU")) {
					dataOne.put("Incoterms", orderInfo.get(i).getPayment());
				}else if(orderInfo.get(i).getPayment().equals("DDP")) {
					dataOne.put("Incoterms", orderInfo.get(i).getPayment());
				}else {
					dataOne.put("Incoterms", "DDU");
				}
				
				orderItem = mapper.selectItemInfoForYS(orderInfo.get(i).getNno());
				for(int j = 0 ; j < orderItem.size(); j++) {
					goodsOne = new LinkedHashMap<String, Object>();
					goodsOne.put("GoodsName", orderItem.get(j).getItemDetail());
					goodsOne.put("Qty", Double.parseDouble(orderItem.get(j).getItemCnt()));
					goodsOne.put("UnitPrice", Double.parseDouble(orderItem.get(j).getUnitValue()));
					goodsOne.put("BrandName", orderItem.get(j).getBrand());
					goodsOne.put("SKU", orderItem.get(j).getSku());
					goodsOne.put("HSCODE", orderItem.get(j).getHsCode());
					goodsOne.put("PurchaseUrl", orderItem.get(j).getItemUrl());
					
					if(orderItem.get(j).getUserId().contains("test_")) {
						String id = orderItem.get(j).getUserId();
						orderItem.get(j).setUserId(id.replace("test_", ""));
					}
					goodsOne.put("SagawaItemCode", mapper.selectNomalCode(orderItem.get(j).getCusItemCode(),orderItem.get(j).getUserId()));
					goodsList.add(goodsOne);
				}
				dataOne.put("GoodsList", goodsList);
				dataList.add(dataOne);
			}
			
			System.out.println("*********** dataList ************");
			System.out.println(dataList);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		rtnJsonArray.put("DataList", dataList);
		rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public String makeYongsungJsonTakeIn(String nno) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> goodsList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> goodsOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderItemYSVO> orderItem = new ArrayList<ApiOrderItemYSVO>();
		ArrayList<ApiOrderYSVO> orderInfo = new ArrayList<ApiOrderYSVO>();
		
		try {
			
			orderInfo = mapper.selectListInfoForYS(nno);
			
			for(int i = 0; i < orderInfo.size(); i++) {
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
				dataOne = new LinkedHashMap<String, Object>();
				goodsList = new ArrayList<LinkedHashMap<String, Object>>();
				dataOne.put("NationCode", orderInfo.get(i).getDstnNation());
				if(orderInfo.get(i).getGetBuy().equals("") || orderInfo.get(i).getGetBuy().equals("1")) {
					dataOne.put("ShippingType", "EP_REG");
				}else {
					dataOne.put("ShippingType", "EP_TAX");
				}
				
				dataOne.put("OrderNo1", orderInfo.get(i).getOrderNo());
				dataOne.put("OrderNo2", orderInfo.get(i).getOrderNo2());
				dataOne.put("SenderName", orderInfo.get(i).getShipperName());
				dataOne.put("SenderTelno", orderInfo.get(i).getShipperTel());
				dataOne.put("SenderAddr", orderInfo.get(i).getShipperAddr()+" "+orderInfo.get(i).getShipperAddrDetail());
				dataOne.put("ReceiverName", orderInfo.get(i).getCneeName());
				dataOne.put("ReceiverNameYomigana", orderInfo.get(i).getCneeName());
				dataOne.put("ReceiverTelNo1", orderInfo.get(i).getCneeTel());
				dataOne.put("ReceiverTelNo2", orderInfo.get(i).getCneeHp());
				dataOne.put("ReceiverZipcode", orderInfo.get(i).getCneeZip());
				String addrInfo = "";
				if(orderInfo.get(i).getDstnNation().equals("JP")) {
					dataOne.put("ReceiverState", "");//일본용 맞춤 삭제
					dataOne.put("ReceiverCity", "");//일본용 맞춤 삭제
					if(orderInfo.get(i).getCneeState().contains("JP-")) {
						addrInfo += mapper.selectStateNameByCode(orderInfo.get(i).getCneeState());
					}
					else {
						if(orderInfo.get(i).getCneeState() != null && !orderInfo.get(i).getCneeState().equals("")) {
							
							String tempState = orderInfo.get(i).getCneeState().replaceAll("[^A-Za-z]","%");
							if(mapper.selectStateNameByName(tempState)!= null) {
								addrInfo += mapper.selectStateNameByName(tempState);
							}else {
								addrInfo += tempState;
							}
						}
					}
					addrInfo += " " + orderInfo.get(i).getCneeCity()+" "+orderInfo.get(i).getCneeAddr() + " "+ orderInfo.get(i).getCneeAddrDetail();
				}else if(orderInfo.get(i).getDstnNation().equals("HK")){
					dataOne.put("ReceiverState", orderInfo.get(i).getCneeState());//홍콩
					dataOne.put("ReceiverCity", orderInfo.get(i).getCneeCity());//홍콩
					dataOne.put("ReceiverDistrict", orderInfo.get(i).getCneeDistrict());//홍콩
					addrInfo += orderInfo.get(i).getCneeAddr() + " "+ orderInfo.get(i).getCneeAddrDetail();
				}else {
					dataOne.put("ReceiverState", orderInfo.get(i).getCneeState());//타이완
					dataOne.put("ReceiverCity", orderInfo.get(i).getCneeCity());//타이완
					addrInfo += orderInfo.get(i).getCneeAddr() + " "+ orderInfo.get(i).getCneeAddrDetail();
				}
				//dataOne.put("ReceiverDistrict", orderInfo.get(i).getCneeCntry());//변경 예정
				dataOne.put("ReceiverDetailAddr",addrInfo);
				dataOne.put("ReceiverEmail", orderInfo.get(i).getCneeEmail());
				dataOne.put("ReceiverSocialNo", orderInfo.get(i).getCustomsNo());
				dataOne.put("RealWeight", Double.parseDouble(orderInfo.get(i).getUserWta()));
				dataOne.put("WeightUnit", orderInfo.get(i).getWtUnit());
				dataOne.put("BoxCount", Integer.parseInt(orderInfo.get(i).getBoxCnt()));
				if(orderInfo.get(i).getChgCurrency().equals("") || orderInfo.get(i).getChgCurrency() == null) {
					dataOne.put("CurrencyUnit", orderInfo.get(i).getUnitCurrency());
				}else {
					dataOne.put("CurrencyUnit", orderInfo.get(i).getChgCurrency());
				}
				
				dataOne.put("DelvMessage", orderInfo.get(i).getDlvReqMsg());
				dataOne.put("UserData1", orderInfo.get(i).getUserData1());
				dataOne.put("UserData2", orderInfo.get(i).getUserData2());
				dataOne.put("UserData3", orderInfo.get(i).getUserData3());
				dataOne.put("DimWidth", Double.parseDouble(orderInfo.get(i).getUserWidth()));
				dataOne.put("DimLength", Double.parseDouble(orderInfo.get(i).getUserLength()));
				dataOne.put("DimHeight", Double.parseDouble(orderInfo.get(i).getUserHeight()));
				dataOne.put("DimUnit", orderInfo.get(i).getDimUnit());
				dataOne.put("DelvNo", orderInfo.get(i).getDelvNo());
				dataOne.put("DelvCom", orderInfo.get(i).getDelvCom());
				orderItem = mapper.selectItemInfoForYS(orderInfo.get(i).getNno());
				for(int j = 0 ; j < orderItem.size(); j++) {
					goodsOne = new LinkedHashMap<String, Object>();
					goodsOne.put("GoodsName", orderItem.get(j).getItemDetail());
					goodsOne.put("Qty", Double.parseDouble(orderItem.get(j).getItemCnt()));
					goodsOne.put("UnitPrice", Double.parseDouble(orderItem.get(j).getUnitValue()));
					goodsOne.put("BrandName", orderItem.get(j).getBrand());
					goodsOne.put("SKU", orderItem.get(j).getSku());
					goodsOne.put("HSCODE", orderItem.get(j).getHsCode());
					goodsOne.put("PurchaseUrl", orderItem.get(j).getItemUrl());
					goodsOne.put("SagawaItemCode", mapper.selectTakeInCode(orderItem.get(j).getCusItemCode(),orderItem.get(j).getUserId()));
					goodsList.add(goodsOne);
				}
				dataOne.put("GoodsList", goodsList);
				dataList.add(dataOne);
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		rtnJsonArray.put("DataList", dataList);
		rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public synchronized String makeYoungSungPodKR(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<String> regNoList = new ArrayList<String>();
		String matchNum =  comnService.selectMatchNumByHawb(hawbNo);
		regNoList.add(matchNum);
		rtnJsonArray.put("Type", "regno");
		rtnJsonArray.put("RegNoList", regNoList);
		rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		
		String jsonVal = getJsonStringFromMap(rtnJsonArray);
		String rtnVal = "";
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL("https://eparcel.kr/apiv2/GetTracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			os.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
//		return "";
	}
	
	public synchronized String makeYoungSungPodEN(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<String> regNoList = new ArrayList<String>();
		String matchNum =  comnService.selectMatchNumByHawb(hawbNo);
		regNoList.add(matchNum);
		rtnJsonArray.put("Type", "regno");
		rtnJsonArray.put("RegNoList", regNoList);
		rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		
		String jsonVal = getJsonStringFromMap(rtnJsonArray);
		String rtnVal = "";
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL("https://eparcel.kr/apiv2/GetTracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "en-US");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
//		return "";
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetatailArray(String rtnJson, String rtnJson2, String hawbNo, HttpServletRequest request) throws Exception{
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		JSONObject json2 = new JSONObject(String.valueOf(rtnJson2));
		try {
			if (!json.get("Code").toString().equals("0")) {
				throw new Exception();
			} else {
				JSONArray dataJsonList = new JSONArray(String.valueOf(json.get("Data").toString()));
				JSONArray dataJsonList2 = new JSONArray(String.valueOf(json2.get("Data").toString()));
				ArrayList<ApiOrderYSVO> tempVO = new ArrayList<ApiOrderYSVO>();
				tempVO = mapper.selectListInfoForYS(mapper.selectNNOByHawbNo(hawbNo));
				JSONObject dataJson = (JSONObject) dataJsonList.get(0);
				JSONObject dataJson2 = (JSONObject) dataJsonList2.get(0);
				if(!dataJson.get("Status").toString().equals("-22")) {
					JSONArray trackingList = dataJson.getJSONArray("TrackingList");
					JSONArray trackingList2 = dataJson2.getJSONArray("TrackingList");
					for(int i = 0; i < trackingList.length(); i++) {
						LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
						JSONObject trackingJson = (JSONObject)trackingList.get(i);
						JSONObject trackingJson2 = (JSONObject)trackingList2.get(i);
						
						if(trackingJson2.get("Status").toString().equals("700")){
							podDetatil.put("UpdateCode","S500");
						}else {
							podDetatil.put("UpdateCode",trackingJson2.get("Status"));
						}
						
						podDetatil.put("UpdateDateTime", trackingJson2.get("IssueDateTime"));
						podDetatil.put("UpdateLocation", trackingJson2.get("Location"));
						podDetatil.put("UpdateDescription", trackingJson2.get("StatusDesc"));
						podDetatil.put("ProblemCode",trackingJson2.get("Status")); 
						if(trackingJson2.get("IssueDetail").toString().contains("용성종합") || trackingJson2.get("IssueDetail").toString().contains("에이씨아이")) {
							podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
						}else{
							podDetatil.put("Comments", trackingJson2.get("IssueDetail"));
						}
						podDetatil.put("delvNo", dataJson.get("DelvNo"));
						podDetatailArray.add(podDetatil);
					}
				}else if(dataJson.get("Status").toString().equals("-22")) {
					LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode", "-200"); 
					podDetatil.put("UpdateDateTime", "");
					podDetatil.put("UpdateLocation", "");
					podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
					podDetatil.put("ProblemCode","-22"); 
					podDetatil.put("Comments", "데이터가 없습니다.(No Data)");
					podDetatil.put("delvNo", "");
					podDetatailArray.add(podDetatil);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return podDetatailArray;
	}
	public ProcedureVO getYongSungRegNo(String ysRtn, String nno, String userId, String userIp) throws Exception{
		ProcedureVO rtnVal2 = new ProcedureVO();
		String rtnHawbNo = "";
		JSONObject json = new JSONObject(String.valueOf(ysRtn));
		System.out.println("***************************");
		System.out.println(json);
		try {
			if (!json.get("Code").toString().equals("0")) {
				throw new Exception();
			} else {
				JSONArray test = new JSONArray(String.valueOf(json.get("Data").toString()));
				MatchingVO matchVo = new MatchingVO();
				for(int i = 0; i < test.length(); i++) {
					JSONObject json3 = (JSONObject) test.get(i);
					rtnHawbNo = json3.get("RegNo").toString();
				}
				//여기에 매칭되는거 insert할 것.
				String hawbNo = mapper.selectHawbNoByNNO(nno);
				matchVo.setKeyHawbNo(hawbNo);
				matchVo.setValueMatchNo(rtnHawbNo);
				matchVo.setMatchTransCode("YSL");
				matchVo.setNno(nno);
				comnService.deleteMatchingInfo(matchVo);
				comnService.insertMatchingInfo(matchVo);
				
				rtnVal2.setRstStatus("SUCCESS");
				rtnVal2.setRstMsg("SUCCESS");
				rtnVal2.setRstHawbNo(matchVo.getValueMatchNo());
				rtnVal2.setOrgHawbNo(hawbNo);
			}
		} catch (Exception e) {
			String failMsg = "";
			JSONArray test = new JSONArray(String.valueOf(json.get("ErrorList").toString()));
			for(int i = 0; i < test.length(); i++) {
				JSONObject json3 = (JSONObject) test.get(i);
				JSONArray jArray = new JSONArray(String.valueOf(json3.get("ErrorMessageList").toString()));
				for(int j = 0 ; j < jArray.length(); j++) {
					JSONObject json4 = (JSONObject) jArray.get(j);
					failMsg += json4.get("ErrorMessage").toString()+"\n";
					if(json4.get("ErrorCode").toString().equals("-473")) {
						if(!rtnVal2.getRstCode().contains("D30"))
							rtnVal2.setRstCode("D30");
					}
				}
				HashMap<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("nno", nno);
				parameters.put("errorMsg",failMsg);
				parameters.put("useYn", "Y");
				parameters.put("wUserId", userId);
				parameters.put("wUserIp", userIp);
				mapper.insertErrorMatch(parameters);
				
			}
			
			// TODO: handle exception
			comnService.deleteHawbNoInTbHawb(nno);
			rtnVal2.setRstStatus("FAIL");
			rtnVal2.setRstMsg(failMsg);
		}
		
		return rtnVal2;
	}
	public static String getJsonStringFromMap( HashMap<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject.toString();
    }
	
	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception{
		// TODO Auto-generated method stub
		return mapper.selectPodBlInfo(parameters);
	}
	
	public String selectKeyHawbByMatchNo(String matchNo) throws Exception{
		return mapper.selectKeyHawbByMatchNo(matchNo);
	}


	public synchronized String fnMakeYongsungJsonDel(String nno) throws Exception {
		// TODO Auto-generated method stub
		String jsonVal = makeYongsungJsonDel(nno);
		String rtnVal = "";
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL("https://eparcel.kr/apiv2/DeleteData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
	}
	
	public String makeYongsungJsonDel(String nno) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		String matchNum = selectMatchNoByNNo(nno);
		try {
			rtnJsonArray.put("RegNo", matchNum);
			rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public String getYslWeight() throws Exception {
		
		ArrayList<String> matchList = new ArrayList<String>();
		matchList = mapper.selectMatchNumAll();
		String result = "";
		for(int j = 0; j < matchList.size(); j++) {
			result = fnMakeYongsungJsonWeight(matchList.get(j));
			JSONObject json = new JSONObject(String.valueOf(result));
			try {
				if (!json.get("Code").toString().equals("0")) {
					throw new Exception();
				} else {
					JSONArray test = new JSONArray(String.valueOf(json.get("Data").toString()));
					MatchingVO matchVo = new MatchingVO();
					String rtnHawbNo = "";
					String rtnWTA = "";
					String rtnWTC = "";
					String rtnSagawaNo = "";
					for(int i = 0; i < test.length(); i++) {
						JSONObject json3 = (JSONObject) test.get(i);
						if(Integer.parseInt(json3.get("Status").toString())>=20) {
							rtnHawbNo = json3.get("RegNo").toString();
							rtnWTA = json3.get("RealWeight").toString();
							rtnWTC = json3.get("VolumeWeight").toString();
							rtnSagawaNo = json3.getString("DelvNo").toString();
						}
						
					}
					//여기에 매칭되는거 insert할 것.
					HashMap<String,String> parameters = new HashMap<String,String>();
					
					if(!rtnHawbNo.equals("")) {
						parameters.put("agencyBl", rtnHawbNo);
						parameters.put("wta", rtnWTA);
						parameters.put("wtc", rtnWTC);
						mapper.insertYslWeight(parameters);
						
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public synchronized String fnMakeYongsungJsonWeight(String nno) throws Exception {
		// TODO Auto-generated method stub
		String jsonVal = makeYongsungJsonWeight(nno);
		String rtnVal = "";
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		//String outResult = new String();
		try {
//			String requestURL = "https://eparcel.kr/apiv2/GetData";
//			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
//			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성 
//			postRequest.setHeader("Accept", "application/json");
//			postRequest.setHeader("Content-Type", "application/json");
//			postRequest.setHeader("Accept-Language", "ko-KR");
//			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력 
//			
//			HttpResponse response = client.execute(postRequest);
//			
//			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				outResult = handler.handleResponse(response);
//			} 
			
			
			
			URL url = new URL("https://eparcel.kr/apiv2/GetData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace(); 
		}
		return outResult.toString();
	}
	
	public String makeYongsungJsonWeight(String nno) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<String> temp = new ArrayList<String>();
		try {
			temp.add(nno);
			rtnJsonArray.put("RegNoList", temp);
			rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public synchronized String fnMakeYSUpdateExpLicenceNoJson(String nno) throws Exception{
		String jsonVal = makeYSUpdateExpLicenceNoJson(nno);
		
		String rtnVal = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		if(jsonVal.equals(""))
			return "";
		
		HashMap<String,Object> sendData = new HashMap<String,Object>();
		
		sendData.put("hawbNo",nno);
		sendData.put("serviceName", "explicenceSendMus");
		sendData.put("status", "Success");
		sendData.put("sequence", "YSL Update Explicence Function call");
		comnService.insertSendTable(sendData);
		try {
			URL url = new URL("https://eparcel.kr/apiv2/UpdateExpLicenceNo");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			sendData = new HashMap<String,Object>();
			sendData.put("hawbNo",nno);
			sendData.put("serviceName", "explicenceSendMus");
			sendData.put("status", "Success");
			sendData.put("sequence", "YSL Update Explicence Url Connect");
			comnService.insertSendTable(sendData);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
			
			sendData = new HashMap<String,Object>();
			sendData.put("hawbNo",nno);
			sendData.put("serviceName", "explicenceSendMus");
			sendData.put("status", "Success");
			sendData.put("sequence", "YSL Update Explicence Url Disconnect");
			comnService.insertSendTable(sendData);
			
			JSONObject json = new JSONObject(String.valueOf(outResult.toString()));
			JSONArray test = new JSONArray(String.valueOf(json.get("Data").toString()));
			String rtnMsg = "";
			for(int i = 0; i < test.length(); i++) {
				JSONObject json3 = (JSONObject) test.get(i);
				if(json3.has("StatusDesc"))
					rtnMsg += json3.get("StatusDesc").toString()+" ";
				else
					rtnMsg += json.get("Message")+" ";
			}
			if (json.get("Code").toString().equals("0")) {
				mapper.updateExpLicence(nno,rtnMsg);
			}else {
				mapper.updateExpLicenceN(nno,rtnMsg);
			}
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
	}

	public String makeYSUpdateExpLicenceNoJson(String nno) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		String matchNum = selectMatchNoByNNo(nno);
		YsApiHawbVO listInfo = new YsApiHawbVO();
		listInfo = mapper.selectHawbInfoForYS(nno);
		try {
			if(listInfo== null)
				return "";
			rtnJsonArray.put("ApiKey","3ecd931bfc114f048f4e90c91");
			ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
			LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
			dataOne.put("RegNo",matchNum);
			dataOne.put("ExpLicenceNo",listInfo.getExpNo());
			dataOne.put("ExpLicencePCS",listInfo.getExpLicencePCS());
			dataOne.put("ExpLicenceWeight",listInfo.getExpLicenceWeight());
			dataOne.put("ExpLicenceDivision","");
			dataList.add(dataOne);
			rtnJsonArray.put("DataList", dataList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	
	public String selectMatchNoByNNo(String nno) throws Exception {
		return comnService.selectMatchNumByHawb(mapper.selectHawbNoByNNO(nno));
	}
	
	public synchronized void selectBlApplyYSL(String orderNno, String userId, String userIp, String inputType) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Thread Start...");
		ProcedureVO rtnVal = new ProcedureVO();
		
		if(!inputType.equals("INSP")) {
			comnService.selectBlApply(orderNno, userId, userIp);
		}
		
		
		String ysRtn = fnMakeYongsungJson(orderNno);

	
		
		try {
			
			rtnVal = getYongSungRegNo(ysRtn, orderNno, userId, userIp);
		
			if(rtnVal.getRstStatus().equals("FAIL")) {
				UserOrderListVO orderInfos = new UserOrderListVO ();
				
				orderInfos = comnService.selectUserRegistOrderOne(orderNno);
				
				if(orderInfos.getDstnNation().equals("JP")) {
					ArrayList<UserOrderItemVO> orderItems = new ArrayList<UserOrderItemVO>(); 
					orderItems = usrMapper.selectUserRegistOrderItemOne(orderInfos);
					for(int i =0; i < orderItems.size(); i++) {
						UserOrderItemVO itemOneInfo = new UserOrderItemVO();
						itemOneInfo = usrMapper.selectUserRegistOrderItemOneForYslItem(orderItems.get(i));
						if(!itemOneInfo.getTakeInCode().equals("N")||!itemOneInfo.getTakeInCode2().equals("N")) {
							continue;
						}
						HashMap<String,Object> cusItemParameter = new HashMap<String,Object>();
						cusItemParameter.put("cusItemCode",orderItems.get(i).getCusItemCode());
						cusItemParameter.put("itemDetail",orderItems.get(i).getItemDetail());
						cusItemParameter.put("nativeItemDetail",orderItems.get(i).getNativeItemDetail());
						cusItemParameter.put("makeCntry",orderItems.get(i).getMakeCntry());
						cusItemParameter.put("itemImgUrl",orderItems.get(i).getItemImgUrl());
						cusItemParameter.put("userId",orderItems.get(i).getUserId());
						insertCusItemCode(cusItemParameter);
					}
					
				}
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
			} else {	
				// 2023.10.18 용성 주문 등록 성공 시 바로 면장 번호 업데이트 되도록 추가 (정식 신고 건)
				
				int expYn = mapper.selectExpLicenceChk(orderNno);
				
				if (expYn != 0) {
					
					LinkedHashMap<String, Object> expJsonData = new LinkedHashMap<String, Object>();
					YsApiHawbVO yslInfo = new YsApiHawbVO();
					yslInfo = mapper.selectYslOrderExpInfo(orderNno);
					expJsonData.put("ApiKey", "3ecd931bfc114f048f4e90c91");
					ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
					LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
					dataOne.put("RegNo",yslInfo.getHawbNo());
					dataOne.put("ExpLicenceNo", yslInfo.getExpNo().replaceAll("-", ""));
					dataOne.put("ExpLicencePCS",yslInfo.getExpLicencePCS());
					dataOne.put("ExpLicenceWeight",yslInfo.getExpLicenceWeight());
					dataOne.put("ExpLicenceDivision","");
					dataList.add(dataOne);
					expJsonData.put("DataList", dataList);
					String jsonVal = getJsonStringFromMap(expJsonData);
					
					String inputLine = null;
					StringBuffer outResult = new StringBuffer();

					HashMap<String,Object> sendData = new HashMap<String,Object>();
					
					sendData.put("hawbNo",orderNno);
					sendData.put("serviceName", "explicenceSendMus");
					sendData.put("status", "Success");
					sendData.put("sequence", "YSL Update Explicence Function call");
					comnService.insertSendTable(sendData);
					
					URL url = new URL("https://eparcel.kr/apiv2/UpdateExpLicenceNo");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setRequestProperty("Accept-Language", "ko-KR");
					conn.setConnectTimeout(50000);
					conn.setReadTimeout(50000);
					OutputStream os = conn.getOutputStream();
					
					os.write(jsonVal.getBytes());
					os.flush();
					sendData = new HashMap<String,Object>();
					sendData.put("hawbNo",orderNno);
					sendData.put("serviceName", "explicenceSendMus");
					sendData.put("status", "Success");
					sendData.put("sequence", "YSL Update Explicence Url Connect");
					comnService.insertSendTable(sendData);
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					while((inputLine = in.readLine())!=null) {
						outResult.append(inputLine);
					}
					conn.disconnect();
					
					sendData = new HashMap<String,Object>();
					sendData.put("hawbNo",orderNno);
					sendData.put("serviceName", "explicenceSendMus");
					sendData.put("status", "Success");
					sendData.put("sequence", "YSL Update Explicence Url Disconnect");
					comnService.insertSendTable(sendData);
					
					JSONObject json = new JSONObject(String.valueOf(outResult.toString()));
					JSONArray test = new JSONArray(String.valueOf(json.get("Data").toString()));
					String rtnMsg = "";
					for(int i = 0; i < test.length(); i++) {
						JSONObject json3 = (JSONObject) test.get(i);
						if(json3.has("StatusDesc"))
							rtnMsg += json3.get("StatusDesc").toString()+" ";
						else
							rtnMsg += json.get("Message")+" ";
					}
					if (json.get("Code").toString().equals("0")) {
						mapper.updateExpLicence(orderNno,rtnMsg);
					}else {
						mapper.updateExpLicenceN(orderNno,rtnMsg);
					}
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
		}
		System.out.println("Thread End...");
	}
	
	public synchronized void selectBlApplyYSLCheck(String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		ProcedureVO rtnVal = new ProcedureVO();
		comnService.selectBlApply(orderNno, userId, userIp);
		
		String ysRtn = fnMakeYongsungJson(orderNno);
		try {
			
			rtnVal = getYongSungRegNo(ysRtn, orderNno, userId, userIp);
			
			if(rtnVal.getRstStatus().equals("FAIL")) {
				UserOrderListVO orderInfos = new UserOrderListVO ();
				
				orderInfos = comnService.selectUserRegistOrderOne(orderNno);
				
				if(orderInfos.getDstnNation().equals("JP")) {
					ArrayList<UserOrderItemVO> orderItems = new ArrayList<UserOrderItemVO>(); 
					orderItems = usrMapper.selectUserRegistOrderItemOne(orderInfos);
					for(int i =0; i < orderItems.size(); i++) {
						UserOrderItemVO itemOneInfo = new UserOrderItemVO();
						itemOneInfo = usrMapper.selectUserRegistOrderItemOneForYslItem(orderItems.get(i));
						if(!itemOneInfo.getTakeInCode().equals("N")||!itemOneInfo.getTakeInCode2().equals("N")) {
							continue;
						}
						HashMap<String,Object> cusItemParameter = new HashMap<String,Object>();
						cusItemParameter.put("cusItemCode",orderItems.get(i).getCusItemCode());
						cusItemParameter.put("itemDetail",orderItems.get(i).getItemDetail());
						cusItemParameter.put("nativeItemDetail",orderItems.get(i).getNativeItemDetail());
						cusItemParameter.put("makeCntry",orderItems.get(i).getMakeCntry());
						cusItemParameter.put("itemImgUrl",orderItems.get(i).getItemImgUrl());
						cusItemParameter.put("userId",orderItems.get(i).getUserId());
						insertCusItemCode(cusItemParameter);
					}
					
				}
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
			}else {
				fnMakeYongsungJsonDel(orderNno);
			}
		}catch (Exception e) {
			// TODO: handle exception
			comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
		}
			
	}
	
	public void insertCusItemCode(HashMap<String,Object> parameters) throws Exception {
		mapper.insertCusItemCode(parameters);
	}

	public ArrayList<HashMap<String, Object>> makePodDetailForArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		try {
			if (!json.get("Code").toString().equals("0")) {
				throw new Exception();
			} else {
				JSONArray dataJsonList = new JSONArray(String.valueOf(json.get("Data").toString()));
				ArrayList<ApiOrderYSVO> tempVO = new ArrayList<ApiOrderYSVO>();
				tempVO = mapper.selectListInfoForYS(mapper.selectNNOByHawbNo(hawbNo));
				JSONObject dataJson = (JSONObject) dataJsonList.get(0);
				if(!dataJson.get("Status").toString().equals("-22")) {
					JSONArray trackingList = dataJson.getJSONArray("TrackingList");
					for(int i = 0; i < trackingList.length(); i++) {
						LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
						JSONObject trackingJson = (JSONObject)trackingList.get(i);
						String dateTime = trackingJson.get("IssueDateTime").toString();
						
						if (trackingJson.get("Status").toString().equals("700")) {
							podDetatil.put("UpdateCode", "600");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Delivered");
						} else if (trackingJson.get("Status").toString().equals("500")) {
							podDetatil.put("UpdateCode", "500");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Out for Delivery");
						} else if (trackingJson.get("Status").toString().equals("200")) {
							podDetatil.put("UpdateCode", "400");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Arrival in destination country");
						} else if (trackingJson.get("Status").toString().equals("30")) {
							podDetatil.put("UpdateCode", "300");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
						} else if (trackingJson.get("Status").toString().equals("20")) {
							podDetatil.put("UpdateCode", "200");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Finished warehousing");
						} else if (trackingJson.get("Status").toString().equals("10")) {
							podDetatil.put("UpdateCode", "100");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Order information has been entered");
						} else {
							continue;
						}
						
						podDetatailArray.add(podDetatil);
					}
				}else if(dataJson.get("Status").toString().equals("-22")) {
					LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode", "-200"); 
					podDetatil.put("UpdateDateTime", "");
					podDetatil.put("UpdateLocation", "");
					podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
					podDetatailArray.add(podDetatil);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailForEbay(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		try {
			if (!json.get("Code").toString().equals("0")) {
				throw new Exception();
			} else {
				JSONArray dataJsonList = new JSONArray(String.valueOf(json.get("Data").toString()));
				ArrayList<ApiOrderYSVO> tempVO = new ArrayList<ApiOrderYSVO>();
				tempVO = mapper.selectListInfoForYS(mapper.selectNNOByHawbNo(hawbNo));
				JSONObject dataJson = (JSONObject) dataJsonList.get(0);
				if(!dataJson.get("Status").toString().equals("-22")) {
					JSONArray trackingList = dataJson.getJSONArray("TrackingList");
					for(int i = 0; i < trackingList.length(); i++) {
						LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
						JSONObject trackingJson = (JSONObject)trackingList.get(i);
						String dateTime = trackingJson.get("IssueDateTime").toString();
						
						if (trackingJson.get("Status").toString().equals("700")) {
							podDetatil.put("UpdateCode", "600");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Delivered");
						} else if (trackingJson.get("Status").toString().equals("500")) {
							podDetatil.put("UpdateCode", "500");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Out for Delivery");
						} else if (trackingJson.get("Status").toString().equals("400")) {
							podDetatil.put("UpdateCode", "480");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Released customs");
						} else if (trackingJson.get("Status").toString().equals("300")) {
							podDetatil.put("UpdateCode", "450");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Customs processing");
						} else if (trackingJson.get("Status").toString().equals("200")) {
							podDetatil.put("UpdateCode", "400");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Arrival in destination country");
						} else if (trackingJson.get("Status").toString().equals("100")) {
							podDetatil.put("UpdateCode", "350");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Aircraft departure");
						} else if (trackingJson.get("Status").toString().equals("30")) {
							podDetatil.put("UpdateCode", "300");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Shipped out");
						} else if (trackingJson.get("Status").toString().equals("20")) {
							podDetatil.put("UpdateCode", "200");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Finished warehousing");
						} else if (trackingJson.get("Status").toString().equals("10")) {
							podDetatil.put("UpdateCode", "100");
							podDetatil.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
							podDetatil.put("UpdateLocation", trackingJson.get("Location"));
							podDetatil.put("UpdateDescription", "Order information has been entered");
						} else {
							continue;
						}
						
						podDetatailArray.add(podDetatil);
					}
				}else if(dataJson.get("Status").toString().equals("-22")) {
					LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode", "-200"); 
					podDetatil.put("UpdateDateTime", "");
					podDetatil.put("UpdateLocation", "");
					podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
					podDetatailArray.add(podDetatil);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		return podDetatailArray;
	}

	public synchronized ArrayList<HashMap<String, Object>> processYslPod(String hawbNo, HttpServletRequest request) throws Exception {
		System.out.println("Thread Start!");
		System.out.println(hawbNo);
		ArrayList<HashMap<String, Object>> detailArray = new ArrayList<HashMap<String, Object>>();
		String rtnJson = makeYoungSungPodKR(hawbNo);
		String rtnJson2 = makeYoungSungPodEN(hawbNo);
		detailArray = makePodDetatailArray(rtnJson, rtnJson2, hawbNo, request);
		System.out.println("Thread End!");
		return detailArray;
	}


	public synchronized void fnGetDeliveryInfo(String nno) {
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		String hawbNo = "";
		String matchNo = "";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("transCode", "YSL");
		try {
			hawbNo = mapper.selectHawbNoByNNO(nno);
			matchNo = mapper.selectMatchNum(hawbNo);
			parameters.put("hawbNo", hawbNo);
			parameters.put("matchNo", matchNo);
			LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(matchNo);
			rtnJsonArray.put("RegNoList", temp);
			rtnJsonArray.put("ApiKey", "3ecd931bfc114f048f4e90c91");
			String jsonVal = getJsonStringFromMap(rtnJsonArray);
			
			URL url = new URL("https://eparcel.kr/apiv2/GetData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine())!=null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
			JSONObject json = new JSONObject(String.valueOf(outResult.toString()));
			if (!json.get("Code").toString().equals("0")) {
				throw new Exception();
			} else { 
				JSONArray jsonArr = new JSONArray(String.valueOf(json.get("Data").toString()));
				String delvNo = "";
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject json2 = (JSONObject) jsonArr.get(i);
					delvNo = json2.get("DelvNo").toString();
				}
				if (!delvNo.equals("")) {
					parameters.put("delvNo", delvNo);
					comnMapper.insertDeliveryInfo(parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
