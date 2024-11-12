package com.example.temp.trans.parcll;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.google.gson.Gson;

@Service
@Component
@Controller
public class ParcllAPI {
	
	@Autowired
	ParcllMapper mapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	private static String apiUser = "US5394768";
	private static String apiKey = "4JmfPMZYHxYzRllv5LfgiA==";
	private static String token = apiUser+"&"+apiKey;
	private static byte[] tokenBytes = token.getBytes();
	private static String tokenVal = Base64.getEncoder().encodeToString(tokenBytes);

	public HashMap<String, Object> createShipment(String nno, String userId, String userIp) {
		LinkedHashMap<String, Object> header = new LinkedHashMap<String, Object>();
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", nno);
		parameterInfo.put("userId", userId);
		parameterInfo.put("userIp", userIp);
		
		try {
			String url = "https://gapi.yunexpressusa.com/api/WayBill/CreateOrder";
			header.put("Content-Type", "application/json");
			header.put("Authorization", "Basic "+tokenVal);
			String requestVal = makeShipmentJson(nno);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject respVal = action.apiPost(requestVal, url, header, nno, userId, userIp);
			
			parameterInfo.put("connUrl", "/api/WayBill/CreateOrder");
			parameterInfo.put("jsonHeader", "");
			parameterInfo.put("jsonData", requestVal);
			parameterInfo.put("rtnContents", respVal.toString());
			apiMapper.insertApiConn(parameterInfo);
			
			if (!respVal.get("Code").toString().equals("0000")) {
				throw new Exception();
			} else {
				JSONArray itemArr = new JSONArray(String.valueOf(respVal.get("Item").toString()));
				JSONObject itemOne = (JSONObject) itemArr.get(0);
				if (itemOne.get("Success").toString().equals("0")) {
					throw new Exception();
				} else {
					String matchNo = itemOne.get("WayBillNumber").toString();
					String trkNo = itemOne.get("TrackingNumber").toString();
					
					ArrayList<String> hawbList = new ArrayList<String>();
					hawbList.add(trkNo);
					
					JSONArray requestArr = new JSONArray(hawbList);
					String printRequestVal = requestArr.toString();
					
					String printUrl = "https://gapi.yunexpressusa.com/api/Label/Print";
					JSONObject printVal = action.apiPost(printRequestVal, printUrl, header, nno, userId, userIp);
					
					parameterInfo.put("connUrl", "/api/Label/Print");
					parameterInfo.put("jsonHeader", "");
					parameterInfo.put("jsonData", printRequestVal);
					parameterInfo.put("rtnContents", printVal.toString());
					apiMapper.insertApiConn(parameterInfo);
					
					if (!printVal.get("Code").toString().equals("0000")) {
						throw new Exception();
					} else {
						JSONArray printArr = new JSONArray(String.valueOf(printVal.get("Item").toString()));
						JSONObject printOne = (JSONObject) printArr.get(0);
						if (printOne.isNull("Url") || printOne.getString("Url").isEmpty()) {
							throw new Exception();
						} else {
							String labelUrl = printOne.get("Url").toString();
							String hawbNo = mapper.selectHawbNoByNNO(nno);
							parameterInfo.put("transCode", "ACI-US");
							parameterInfo.put("hawbNo", hawbNo);
							parameterInfo.put("matchNo", matchNo);
							parameterInfo.put("delvNo", trkNo);
							comnMapper.insertDeliveryInfo(parameterInfo);
							
							parameterInfo.put("subTransCode", "PARCLL");
							comnMapper.insertSubTransCode(parameterInfo);
							String user = mapper.selectUserIdByNno(parameterInfo);
							
							String imageDir = realFilePath + "image/" + "aramex/";
							
							URL imgUrl = new URL(labelUrl);
							File file = new File(imageDir+hawbNo+".pdf");
							ReadableByteChannel read = Channels.newChannel(imgUrl.openStream());
							FileOutputStream fos = new FileOutputStream(file);
							
							fos.getChannel().transferFrom(read, 0, Long.MAX_VALUE);
							fos.close();
							
							AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
							amazonS3 = new AmazonS3Client(awsCredentials);
							PutObjectResult asssd = new PutObjectResult();
							Calendar c = Calendar.getInstance();
							String year = String.valueOf(c.get(Calendar.YEAR));
					 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
							if(amazonS3 != null) {
								PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/" + year + "/" + week, user+"_"+hawbNo, file);
								putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
								asssd = amazonS3.putObject(putObjectRequest);
							}
							amazonS3 = null;
							file.delete();
							
							rst.put("STATUS", "SUCCESS");
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
		}
		
		return rst;
	}

	private String makeShipmentJson(String nno) {
		ArrayList<LinkedHashMap<String, Object>> requestDataArr = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> receiverMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> senderMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> parcelMap = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> parcelMapArr = new ArrayList<LinkedHashMap<String,Object>>();
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", nno);
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		orderInfo = mapper.selectCreateOrderInfo(parameterInfo);
		orderInfo.dncryptData();
		
		requestData.put("CustomerOrderNumber", nno);
		requestData.put("ShippingMethodCode", "ECOWE");
		requestData.put("PackageCount", Integer.parseInt(orderInfo.getBoxCnt()));
		requestData.put("Weight", Float.parseFloat(orderInfo.getWta()));
		requestData.put("WeightUnits", "kg");
		requestData.put("SizeUnits", "cm");
		requestData.put("Length", 0);
		requestData.put("Width", 0);
		requestData.put("Height", 0);
		
		receiverMap.put("CountryCode", "US");
		receiverMap.put("FirstName", orderInfo.getCneeName());
		String cneeAddr = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			cneeAddr += " " + orderInfo.getCneeAddrDetail();
		}
		receiverMap.put("Street", cneeAddr);
		receiverMap.put("City", orderInfo.getCneeCity());
		receiverMap.put("State", orderInfo.getCneeState());
		receiverMap.put("Zip", orderInfo.getCneeZip());
		receiverMap.put("Phone", orderInfo.getCneeTel());
		
		requestData.put("Receiver", receiverMap);
		
		requestData.put("Sender", senderMap);
		
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		itemInfoList = mapper.selectCreateOrderItem(parameterInfo);
		
		for (int itemIndex = 0; itemIndex < itemInfoList.size(); itemIndex++) {
			parcelMap = new LinkedHashMap<String, Object>();
			parcelMap.put("EName", itemInfoList.get(itemIndex).getItemDetail());
			parcelMap.put("HSCode", itemInfoList.get(itemIndex).getHsCode());
			parcelMap.put("Quantity", Integer.parseInt(itemInfoList.get(itemIndex).getItemCnt()));
			parcelMap.put("UnitPrice", Float.parseFloat(itemInfoList.get(itemIndex).getUnitValue()));
			parcelMap.put("UnitWeight", itemInfoList.get(itemIndex).getUserWta());
			parcelMap.put("ProductUrl", itemInfoList.get(itemIndex).getItemUrl());
			parcelMap.put("CurrencyCode", itemInfoList.get(itemIndex).getUnitCurrency());
			
			parcelMapArr.add(parcelMap);
		}
		
		requestData.put("Parcels", parcelMapArr);
		requestDataArr.add(requestData);
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(requestDataArr);
		
		return jsonVal;
	}
	
	public ArrayList<HashMap<String, Object>> makeParcllPod(String hawbNo, HttpServletRequest request, String podType) {
		ArrayList<HashMap<String, Object>> rst = new ArrayList<HashMap<String,Object>>();
		LinkedHashMap<String, Object> header = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		HashMap<String, Object> matchInfo = new HashMap<String, Object>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("hawbNo", hawbNo);
		matchInfo = mapper.selectMatchInfo(parameterInfo);
		parameterInfo.put("nno", matchInfo.get("nno").toString());
		String matchNo = matchInfo.get("matchNo").toString();
		String nno = parameterInfo.get("nno").toString();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getRemoteAddr();
		try {
			String url = "https://gapi.yunexpressusa.com/api/Tracking/GetTrackInfo?OrderNumber="+matchNo;
			header.put("Content-Type", "application/json");
			header.put("Authorization", "Basic "+tokenVal);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject resultVal = action.apiGet(url, header, nno, userId, userIp);

			parameterInfo.put("connUrl", "/api/Tracking/GetTrackInfo?");
			parameterInfo.put("jsonHeader", "");
			parameterInfo.put("jsonData", matchNo);
			parameterInfo.put("rtnContents", resultVal.toString());
			apiMapper.insertApiConn(parameterInfo);
			
			if (!resultVal.get("Code").toString().equals("0000")) {
				throw new Exception();
			} else {
				Object itemObj = resultVal.get("Item");
				
				if (itemObj == null || itemObj.equals("")) {
					throw new Exception();
				} else {
					JSONObject itemOne = resultVal.getJSONObject("Item");
					String carrier = itemOne.get("CarrierName").toString();
					parameterInfo.put("carrier", carrier);
					mapper.updateDelveryInfo(parameterInfo);
					JSONArray trackList = new JSONArray(String.valueOf(itemOne.get("OrderTrackingDetails").toString()));
					if (podType.toUpperCase().equals("V")) {
						rst = makePodDetailArrayView(trackList);
					} else {
						rst = makePodDetailArray(trackList);
						
						String hawbInDate = apiMapper.selectHawbInDate(hawbNo);
						String mawbInDate = apiMapper.selectMawbInDate(hawbNo);
						String regInDate = apiMapper.selectRegInDate(hawbNo);
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "300");
						podDetail.put("UpdateDateTime", mawbInDate.substring(0, mawbInDate.length() - 3));
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Picked up by Shipping Partner");
						rst.add(podDetail);
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "200");
						podDetail.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Finished warehousing");
						rst.add(podDetail);
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "100");
						podDetail.put("UpdateDateTime", regInDate);
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Order information has been entered");
						rst.add(podDetail);
					}
				}
			}
			
		} catch (Exception e) {
			podDetail  = new LinkedHashMap<String, Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			rst.add(podDetail);
		}
		
		return rst;
	}
	
	private ArrayList<HashMap<String, Object>> makePodDetailArray(JSONArray trackList) {
		ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		try {
			for (int i = trackList.length() - 1; i > -1; i--) {
				JSONObject trackInfo = (JSONObject) trackList.get(i);
				podDetail = new LinkedHashMap<String, Object>();
				int trackingCode = trackInfo.getInt("TrackingStatus");
				String location = "";
				if (!trackInfo.isNull("ProcessLocation") || !trackInfo.getString("ProcessLocation").isEmpty()) {
					location = trackInfo.get("ProcessLocation").toString();
				}
				String procDate = trackInfo.get("ProcessDate").toString();
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				LocalDateTime localDateTime = LocalDateTime.parse(procDate, formatter);
				TimeZone kstTimeZone = TimeZone.getTimeZone("Asia/Seoul");
				DateTimeFormatter reFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String dateTime = localDateTime.atZone(kstTimeZone.toZoneId()).format(reFormatter);
				
				if (trackingCode == 50) {
					podDetail.put("UpdateCode", "600");
					podDetail.put("UpdateDateTime", dateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", "Delivered");
				} else if (trackingCode == 30) {
					podDetail.put("UpdateCode", "500");
					podDetail.put("UpdateDateTime", dateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", "Out for Delivery");
				} else if (trackingCode == 28) {
					podDetail.put("UpdateCode", "400");
					podDetail.put("UpdateDateTime", dateTime);
					podDetail.put("UpdateLocation", location);
					podDetail.put("UpdateDescription", "Arrival in destination country");
				} else {
					continue;
				}
				podDetailArray.add(podDetail);
			}
		} catch (Exception e) {
			podDetail  = new LinkedHashMap<String, Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetailArray.add(podDetail);
		}
		return podDetailArray;
	}

	private ArrayList<HashMap<String, Object>> makePodDetailArrayView(JSONArray trackList) {
		ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		try {
			for (int i = trackList.length() - 1; i > -1; i--) {
				JSONObject trackInfo = (JSONObject) trackList.get(i);
				podDetail = new LinkedHashMap<String, Object>();
				int trackingCode = trackInfo.getInt("TrackingStatus");
				String location = "";
				if (!trackInfo.isNull("ProcessLocation") || !trackInfo.getString("ProcessLocation").isEmpty()) {
					location = trackInfo.get("ProcessLocation").toString();
				}
				String procDate = trackInfo.get("ProcessDate").toString();
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				LocalDateTime localDateTime = LocalDateTime.parse(procDate, formatter);
				TimeZone kstTimeZone = TimeZone.getTimeZone("Asia/Seoul");
				DateTimeFormatter reFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String dateTime = localDateTime.atZone(kstTimeZone.toZoneId()).format(reFormatter);
				String desc = trackInfo.get("ProcessContent").toString();
				
				podDetail.put("UpdateCode", trackingCode);
				podDetail.put("UpdateDateTime", dateTime);
				podDetail.put("UpdateLocation", location);
				podDetail.put("UpdateDescription", desc);
				podDetailArray.add(podDetail);
			}
		} catch (Exception e) {
			podDetail  = new LinkedHashMap<String, Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetailArray.add(podDetail);
		}
		return podDetailArray;
	}
}
