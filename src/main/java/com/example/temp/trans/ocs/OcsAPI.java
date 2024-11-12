package com.example.temp.trans.ocs;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OcsAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	OcsMapper mapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public String fnMakeOCSJson(String nno) throws Exception{
		
		String jsonVal = makeOCSJson(nno);
		String rtnVal = "";
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		String outResult = new String();
		try {
			String requestURL = "https://api.ocsamerica.com/shipment/cwb";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성 
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("charset", "UTF-8");
			postRequest.addHeader("apiKey", "a28k05jc20ax"); //KEY 입력 
			//postRequest.addHeader("Authorization", token); // token 이용시
			

			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력 

			HttpResponse response = client.execute(postRequest);
			
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
			} else {
				String outs = "";
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity);
				//outResult = "{\"errors\": [ { \"errorMessage\": \" Response Error!! : Error Code ["+response.getStatusLine().getStatusCode()+"]\" } ]}";
				//{"Message":"An error has occurred."}
						
			}
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult;
//		return "";
	}
	
	
	public String makeOCSJson(String nno) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> ShipmentItemList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> ShipmentItemOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderItemOcsVO> orderItem = new ArrayList<ApiOrderItemOcsVO>();
		ApiOrderOcsVO orderInfo = new ApiOrderOcsVO();
		
		try {
			
			orderInfo = mapper.selectListInfoForOCS(nno);
			
			orderInfo.setSymmetryKey(originKey.getSymmetryKey());
			orderInfo.dncryptData();
			dataOne = new LinkedHashMap<String, Object>();
			ShipmentItemList = new ArrayList<LinkedHashMap<String, Object>>();
			dataOne.put("CustomerCode","C01788");//
			dataOne.put("ServiceType", "SPS");//
			dataOne.put("Pieces", orderInfo.getBoxCnt());//
			dataOne.put("TotalWeight", orderInfo.getUserWta());//
			
			double volumeWeight = (Double.parseDouble(orderInfo.getUserHeight()) * Double.parseDouble(orderInfo.getUserWidth()) * Double.parseDouble(orderInfo.getUserLength())) / 139;
			
			dataOne.put("TotalVolumetricWeight", volumeWeight);//계산식 있어야 됨... ??? 
			dataOne.put("DestCountryCode", orderInfo.getDstnNation());//
			
			dataOne.put("OriginStationCode", comnMapper.selectOrgStationName(orderInfo.getOrgStation()));//
			dataOne.put("Reference", orderInfo.getOrderNo());//
			String processDate = "";
			
			if(!dateCheck(orderInfo.getOrderDate(), "YYYY-MM-DD")) {
				//현재 날짜의 타입 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				//Date로 파싱
				Date dates = dateFormat.parse(orderInfo.getOrderDate());
				//변경할 타입으로의 형 변환
				processDate = new SimpleDateFormat("yyyy-MM-dd").format(dates);
			}
			
			dataOne.put("ShipmentDate", processDate);//
//			if(orderInfo.getCneeName().length() > 35) {
//				orderInfo.setCneeName(orderInfo.getCneeName().substring(0, 30)+"...");
//			}
			String nativeCneeName =orderInfo.getNativeCneeName();
			//byte[] nativeCneeNameUtf8 = nativeCneeName.getBytes(StandardCharsets.UTF_16);
			//String _newNativeCneeNameUtf8String = new String(nativeCneeNameUtf8, StandardCharsets.UTF_16);
//			String nativeCneeName = "";
			dataOne.put("DeliveryNativeContactName", nativeCneeName);//
			dataOne.put("DeliveryCompanyName1", (!orderInfo.getCneeName().equals(""))?orderInfo.getCneeName(): ".");//
			dataOne.put("DeliveryContactName", (!orderInfo.getCneeName().equals(""))?orderInfo.getCneeName(): ".");//
			dataOne.put("DeliverySectionName", ".");
//			String address = orderInfo.getCneeAddr() + " " + orderInfo.getCneeAddrDetail();
//			if(address.length()>35) {
//				address = address.subSequence(0, 30)+"...";
//			}
			
			String nativeAddr =orderInfo.getNativeCneeAddr()+" "+orderInfo.getNativeCneeAddrDetail();
			//byte[] nativaAddrUtf8 = nativeAddr.getBytes(StandardCharsets.UTF_16);
			//String _newNativaAddrUtf8String = new String(nativeCneeNameUtf8, StandardCharsets.UTF_16);
//			String nativeAddr = "";
			dataOne.put("DeliveryNativeAddress", nativeAddr);//
			dataOne.put("DeliveryStreetAddress1", (!orderInfo.getCneeAddr().equals(""))?orderInfo.getCneeAddr(): ".");//
			dataOne.put("DeliveryStreetAddress2", (!orderInfo.getCneeAddrDetail().equals(""))?orderInfo.getCneeAddrDetail(): ".");//
			dataOne.put("DeliveryCityName", (!orderInfo.getCneeCity().equals(""))?orderInfo.getCneeCity(): ".");//
			dataOne.put("DeliveryRegionName", (!orderInfo.getCneeState().equals(""))?orderInfo.getCneeState(): ".");//
			dataOne.put("DeliveryZipCode", orderInfo.getCneeZip());//
			String nationName = mapper.selectNationName(orderInfo.getDstnNation());
			dataOne.put("DeliveryCountryName", nationName);//
			dataOne.put("DeliveryTelephone", orderInfo.getCneeTel());//
			dataOne.put("DeliveryEmailAddress", orderInfo.getCneeEmail());//
			dataOne.put("FreightChargePayee","Shipper"); //
			dataOne.put("DutyTaxPayee", "Shipper");//
			dataOne.put("OverwriteCityRegion", true);
			orderItem = mapper.selectItemInfoForOCS(orderInfo.getNno());
			for(int j = 0 ; j < orderItem.size(); j++) {
				ShipmentItemOne = new LinkedHashMap<String, Object>();
				if(orderItem.get(j).getItemDetail().length()>60) {
					orderItem.get(j).setItemDetail(orderItem.get(j).getItemDetail().substring(0,55)+"...");
				}
				ShipmentItemOne.put("ItemNameDetail", orderItem.get(j).getItemDetail());//
				ShipmentItemOne.put("Quantity", Double.parseDouble(orderItem.get(j).getItemCnt()));//
				ShipmentItemOne.put("UnitType",orderItem.get(j).getQtyUnit());//
				ShipmentItemOne.put("UnitPrice", Double.parseDouble(orderItem.get(j).getUnitValue()));//
				ShipmentItemList.add(ShipmentItemOne);
			}
			dataOne.put("ShipmentItem", ShipmentItemList);//
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getJsonStringFromMap(dataOne);
	}
	
	public String makeOCSPod(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		rtnJsonArray.put("apikey","a28k05jc20ax");
		
		String jsonVal = getJsonStringFromMap(rtnJsonArray);
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String inputLine = null;
		String outResult = new String();
		try {
			String requestURL = "https://api.ocsamerica.com/tracking/cwb/"+hawbNo;
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
			getRequest.setHeader("Accept", "application/json");
			getRequest.setHeader("Content-Type", "application/json");
			getRequest.addHeader("apiKey", "a28k05jc20ax"); //KEY 입력 
			//postRequest.addHeader("Authorization", token); // token 이용시

			HttpResponse response = client.execute(getRequest);
			
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity, "UTF-8");
				
			}
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
		return outResult.toString();
		//return "";
	}
	
	
	public ArrayList<HashMap<String, Object>> makePodDetailArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception{
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONArray trackingData = new JSONArray(String.valueOf(rtnJson));
		try {
			for(int i = 0; i < trackingData.length(); i++) {
				JSONObject trakingOne = new JSONObject(String.valueOf(trackingData.get(i)));
				LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
				if(trakingOne.get("trackingPoint").toString().equals("")){
					podDetatil.put("UpdateCode","S500");
				}else {
					podDetatil.put("UpdateCode",trakingOne.get("trackingPoint").toString());
				}
				podDetatil.put("UpdateDateTime", trakingOne.get("trackingDateTime").toString());
				podDetatil.put("UpdateLocation", trakingOne.get("location").toString());
				podDetatil.put("UpdateDescription", trakingOne.get("trackingPoint").toString());
				podDetatil.put("ProblemCode",trakingOne.get("trackingMemo").toString()); 
				podDetatailArray.add(podDetatil);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makeOCSPodDetatailArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception{
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		try {
			JSONArray dataJsonList = new JSONArray(String.valueOf(rtnJson));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return podDetatailArray;
	}
	
	public static boolean dateCheck(String date, String format) throws Exception{
        SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }

	public ProcedureVO updateHawbNo(String ocsRtn, String nno) throws Exception{
		ProcedureVO rtnVal2 = new ProcedureVO();
		String rtnHawbNo = "";
		JSONObject json = new JSONObject(String.valueOf(ocsRtn));
		
		try {
			if (json.has("errors")) {
				//error 처리
				rtnVal2.setRstStatus("FAIL");
				rtnVal2.setRstMsg(ocsRtn);
			} else {
				json.get("trackingNumber");
				json.get("shipmentReference");
				json.get("shipmentDate");
				json.get("dateCreated");
				HashMap<String,String> parameters = new HashMap<String,String>();
				parameters.put("hawbNo", json.get("trackingNumber").toString());
				parameters.put("nno", nno);
				mapper.updateHawbNo(parameters);
				//여기에 매칭되는거 insert할 것.
				rtnVal2.setRstStatus("SUCCESS");
				rtnVal2.setRstMsg("SUCCESS");
				rtnVal2.setRstHawbNo(json.get("trackingNumber").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtnVal2;
	}
	public static String getJsonStringFromMap( HashMap<String, Object> map ) throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject.toString();
    }


	public HashMap<String, Object> fnMakeOcsDutyTax(String hawbNo) throws Exception{
		// TODO Auto-generated method stub
		String outResult = new String();
		try {
			String requestURL = "https://api.ocsamerica.com/shipment/cwb/"+hawbNo+"/dutytaxamount";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
			getRequest.setHeader("Accept", "application/json");
			getRequest.setHeader("Content-Type", "application/json");
			getRequest.setHeader("charset", "UTF-8");
			getRequest.addHeader("apiKey", "a28k05jc20ax"); //KEY 입력 
			//postRequest.addHeader("Authorization", token); // token 이용시
			


			HttpResponse response = client.execute(getRequest);
			
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity);
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		ObjectMapper objMapper = new ObjectMapper();
		HashMap<String,Object> rtnMap = objMapper.readValue(outResult, HashMap.class);
		return rtnMap;
	}
	
	public BlApplyVO selectBlApplyOCS(String orderNno, String username, String remoteAddr) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO rtnVal = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		UserOrderListVO userInfo = new UserOrderListVO();
		
		comnService.insertTBFromTMP(orderNno);
		
		userInfo = comnService.selectUserRegistOrderOne(orderNno);
		
		String ocsRtn = fnMakeOCSJson(orderNno);
		ProcedureVO rtnValOcs = new ProcedureVO();
		JSONObject json = new JSONObject(String.valueOf(ocsRtn));
		String msgs = "";
		try {
			if (json.has("errors")) {
				//error 처리
				json.get("errors");
				JSONArray test = new JSONArray(String.valueOf(json.get("errors").toString()));
				for(int i = 0; i < test.length(); i++) {
					JSONObject json2 = new JSONObject(String.valueOf(test.get(i).toString()));
					String errMsg = json2.get("errorMessage").toString();
					msgs += errMsg.replaceAll("[.]", "")+",";
				}
				resultShopify.setErrorMsg(msgs);
				comnService.deleteHawbNoInTbHawb(orderNno);
				comnService.insertTmpFromOrderList(orderNno, resultShopify.getErrorMsg());
				comnService.insertTmpFromOrderItem(orderNno);
				comnService.deleteOrderListByNno(orderNno);
				rtnValOcs.setRstStatus("FAIL");
				rtnValOcs.setRstMsg(ocsRtn);
			} else {
				json.get("trackingNumber");
				json.get("shipmentReference");
				json.get("shipmentDate");
				json.get("dateCreated");
				comnService.updateHawbNoInTbHawb(json.get("trackingNumber").toString(), orderNno);
				comnService.updateHawbNoInTbOrderList(json.get("trackingNumber").toString(), orderNno);
				//여기에 매칭되는거 insert할 것.
				rtnValOcs.setRstStatus("SUCCESS");
				rtnValOcs.setRstMsg("SUCCESS");
				rtnValOcs.setRstHawbNo(json.get("trackingNumber").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtnVal;
		
	}
	
//	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception{
//		// TODO Auto-generated method stub
//		return mapper.selectPodBlInfo(parameters);
//	}
//	
//	public String selectKeyHawbByMatchNo(String matchNo) throws Exception{
//		return mapper.selectKeyHawbByMatchNo(matchNo);
//	}
}
