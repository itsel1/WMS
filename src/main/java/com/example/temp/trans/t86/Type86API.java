package com.example.temp.trans.t86;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.security.SecurityKeyVO;
import com.google.gson.JsonObject;

import java.security.MessageDigest;

@Service
public class Type86API {
	
	@Autowired
	Type86Mapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public HashMap<String, Object> selectBlApplyT86(String orderNno, String userId, String userIp) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		String rtn = fnMakeType86Json(orderNno);
		String result = "";
		System.out.println(rtn);
		try {
			
			rtnVal = getType86RegNo(rtn, orderNno, userId, userIp);
			
			if(rtnVal.getRstStatus().equals("FAIL")) {
				rst.put("STATUS", "FAIL");
			} else {
				rst.put("STATUS", "SUCCESS");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rst.put("STATUS", "FAIL");
		}
		
		return rst;
	}
	
	public ProcedureVO selectBlApply(String orderNno, String userId, String userIp) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		
		String jsonVal = fnMakeType86ApiJson(orderNno);
		JSONObject json = new JSONObject(String.valueOf(jsonVal));
		String rtnHawbNo = "";
		String rtnCarrier = "";
		String imgUrl = "";
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", orderNno);
		parameters.put("userId", userId);
		parameters.put("userIp", userIp);
		
		try {
			JSONArray jsonArr = new JSONArray(String.valueOf(json.get("packages").toString()));
			for (int idx = 0; idx < jsonArr.length(); idx++) {
				JSONObject object = (JSONObject) jsonArr.get(idx);
				if (!object.get("status").toString().equals("S")) {
					throw new Exception();
				} else {
					JSONObject obj = object.getJSONObject("service_request");
					rtnHawbNo = obj.get("tracking_number").toString();
					
					if (obj.get("carrier_name").toString().equals("USPS")) {
						rtnCarrier = "USP";	
					} else if (obj.get("carrier_name").toString().toUpperCase().equals("FEDEX")) {
						if (obj.get("service_type").toString().toUpperCase().equals("FEDEX GROUND")) {
							rtnCarrier = "FEG";
							
						} else {
							rtnCarrier = "FES";
						}
					} else if (obj.get("carrier_name").toString().equals("DHL")) {
						rtnCarrier = "DHL";
					} else if (obj.get("carrier_name").toString().equals("UPS")) {
						rtnCarrier = "UPS";
					}
					
					imgUrl = obj.get("label_download").toString();

					String imageDir = realFilePath + "image/" + "aramex/";
					
					URL url = new URL(imgUrl);
					File file = new File(imageDir+rtnHawbNo+".pdf");
					ReadableByteChannel read = Channels.newChannel(url.openStream());
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
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/" + year + "/" + week, userId+"_"+rtnHawbNo, file);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						asssd = amazonS3.putObject(putObjectRequest);
					}
					amazonS3 = null;
					//file.delete();
					
					parameters.put("hawbNo", rtnHawbNo);
					comnService.createParcelBl(parameters);
					
					rtnVal.setRstStatus("SUCCESS");
					rtnVal.setRstMsg("SUCCESS");
					rtnVal.setRstHawbNo(rtnHawbNo);
				}
			}
		} catch (Exception e) {
			String failMsg = "";
			JSONArray array = new JSONArray(String.valueOf(json.get("packages").toString()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				JSONArray jArray = new JSONArray(String.valueOf(jsonObject.get("message").toString()));
				for (int j = 0; j < jArray.length(); j++) {
					failMsg += jArray.get(j).toString()+"\n";
				}
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("nno", orderNno);
				params.put("connUrl", "https://globalparcelservice.com/connect/api/label/create");
				params.put("headContents", "");
				params.put("bodyContents", jsonObject);
				params.put("errorMsg", failMsg);
				params.put("useYn", "Y");
				params.put("wUserId", userId);
				params.put("wUserIp", userIp);
				mapper.insertApiConn(params);
				
			}
			
			rtnVal.setRstStatus("FAIL");
			rtnVal.setRstMsg(failMsg);
		}
		
		
		return rtnVal;
	}

	
	
	private String fnMakeType86ApiJson(String orderNno) throws Exception {

 		String jsonVal = makeType86ApiJson(orderNno);
 		System.out.println("********* REQUEST BODY ===========>");
		System.out.println(jsonVal);

		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			
			//URL url = new URL("https://globalparcelservice.com/connect/api/label/create");
			URL url = new URL("https://globalparcelservice.com/sandbox/global/api/label/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			conn.setRequestProperty("pcode", "TEST0001");
			conn.setRequestProperty("branch", "001");
			conn.setRequestProperty("apikey", "de9e39bfcadf2ed7183beb85e7d0619d");
			
			//conn.setRequestProperty("pcode", "aciexpress");
			//conn.setRequestProperty("branch", "001");
			//conn.setRequestProperty("apikey", "320c03f29199e91d3194563b1da080f6");
			
			conn.setRequestProperty("lang", "en");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outResult.toString();
	}

	private String makeType86ApiJson(String orderNno) throws Exception {

		// production
		//String secretKey = "f9043d4702225550";
		
		// sandbox
		String secretKey = "d93a7716d31c8211";
		
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderListVO> orderInfo = new ArrayList<ApiOrderListVO>();


		try {
			orderInfo = mapper.selectTmpOrderList(orderNno);
			
			for (int i = 0; i < orderInfo.size(); i++) {
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
					
				// shipping datetime
				LinkedHashMap<String, Object> shippingInfo = new LinkedHashMap<String, Object>();
				String weight = "";
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				String day = format.format(now);
				shippingInfo.put("shipping_date", "");
				shippingInfo.put("shipping_time", "");
				shippingInfo.put("shipping_timezone", "");
				dataOne.put("shipping_datetime", shippingInfo);
				
				// port
				LinkedHashMap<String, Object> port = new LinkedHashMap<String, Object>();
				port.put("type", "");
				ArrayList<String> cityCode = new ArrayList<String>();
				cityCode.add("LAX");
				port.put("name", cityCode);
				dataOne.put("port", port);
				
				// weight 
				LinkedHashMap<String, Object> packWeight = new LinkedHashMap<String, Object>();
				Double wta = Double.parseDouble(orderInfo.get(i).getUserWta());
				//Double wtc = Double.parseDouble(orderInfo.get(i).getUserWtc());
				weight = formatDouble(wta);
				packWeight.put("value", wta);
				String wtUnit = orderInfo.get(i).getWtUnit();
				if (wtUnit.toUpperCase().equals("KG")) {
					packWeight.put("unit", "kg");
				} else if (wtUnit.toUpperCase().equals("LB")) {
					packWeight.put("unit", "lbs");
				} else if (wtUnit.toUpperCase().equals("G")) {
					packWeight.put("unit", "g");
				} else if (wtUnit.toUpperCase().equals("OZ")) {
					packWeight.put("unit", "oz");
				}
				dataOne.put("package_total_gross_weight", packWeight);
				
				// 무게에 따른 carrier 지정 
				Double wt = wta;
				Double realWt = 1.0;
				
				if (wtUnit.toLowerCase().toString().equals("kg")) {
					realWt = wt;
				} else if (wtUnit.toLowerCase().toString().equals("g")) {
					realWt = wt*0.001;
				} else if (wtUnit.toLowerCase().toString().equals("lb")) {
					realWt = wt*0.453592;
				} else if (wtUnit.toLowerCase().toString().equals("oz")) {
					realWt = wt/35.274;
				}

				String provider = "";
				String service = "";
				
				// 2023.07 ACI-US 배송사 코드 통합으로 type86 무게 7kg 초과 시 무조건 UPS로 설정
				//provider = "UPS";
				//service = "Ground";
				
				// 2023.11.16 API를 통한 ACI-T86 건 USPS로 설정
				provider = "USPS";
				service = "LOWEST";
				
				//provider = "DHL";
				//service = "GND";

				// carrier
				LinkedHashMap<String, Object> carrier = new LinkedHashMap<String, Object>();
				carrier.put("provider", provider);
				carrier.put("service_type", service);
				carrier.put("packaging_type", "YOUR_PACKAGING");
				carrier.put("transit_time", "");
				carrier.put("contract_id", "");
				dataOne.put("carrier", carrier);
				
				// dimensions
				LinkedHashMap<String, Object> dimens = new LinkedHashMap<String, Object>();
				String dimUnit = orderInfo.get(i).getDimUnit();
				if(dimUnit != null) {
					if (dimUnit.toUpperCase().equals("CM")) {
						dimens.put("unit", "cm");
					} else if (dimUnit.toUpperCase().equals("IN")) {
						dimens.put("unit", "in");
					}
				} else {
					dimens.put("unit", "cm");
				}
				dimens.put("length", orderInfo.get(i).getUserLength());
				dimens.put("width", orderInfo.get(i).getUserWidth());
				dimens.put("height", orderInfo.get(i).getUserHeight());
				dataOne.put("package_dimensions", dimens);
				
				// shipper
				LinkedHashMap<String, Object> shipperInfo = new LinkedHashMap<String, Object>();
				shipperInfo.put("send_firm", orderInfo.get(i).getShipperName());
				shipperInfo.put("send_name", orderInfo.get(i).getShipperName());
				
				if (orderInfo.get(i).getOrgStation().equals("213")) {
					shipperInfo.put("send_addr1", orderInfo.get(i).getShipperAddr());
					shipperInfo.put("send_addr2", orderInfo.get(i).getShipperAddrDetail());
					shipperInfo.put("send_city_locality", orderInfo.get(i).getShipperCity());
					shipperInfo.put("send_state_province", orderInfo.get(i).getShipperState());
					shipperInfo.put("send_zip_postal_code", orderInfo.get(i).getShipperZip());
				} else {
					shipperInfo.put("send_addr1", "14056 Artesia Blvd");
					shipperInfo.put("send_addr2","");
					shipperInfo.put("send_city_locality","Cerritos");
					shipperInfo.put("send_state_province","CA");
					shipperInfo.put("send_zip_postal_code","90703");
				}
				shipperInfo.put("send_country_code", "US");
				shipperInfo.put("send_email", orderInfo.get(i).getShipperEmail());
				shipperInfo.put("send_sms", orderInfo.get(i).getShipperTel());
				shipperInfo.put("send_address_residential_indicator", "no");
				dataOne.put("ship_from", shipperInfo);
				
				// cnee
				LinkedHashMap<String, Object> cneeInfo = new LinkedHashMap<String, Object>();
				cneeInfo.put("recv_firm", orderInfo.get(i).getCneeName());
				cneeInfo.put("recv_name", orderInfo.get(i).getCneeName());
				cneeInfo.put("recv_addr1", orderInfo.get(i).getCneeAddr().trim());
				cneeInfo.put("recv_addr2", orderInfo.get(i).getCneeAddrDetail().trim());
				cneeInfo.put("recv_city_locality", orderInfo.get(i).getCneeCity().trim());
				cneeInfo.put("recv_state_province", orderInfo.get(i).getCneeState().trim());
				cneeInfo.put("recv_zip_postal_code", orderInfo.get(i).getCneeZip().trim());
				cneeInfo.put("recv_zip4_code", "");
				cneeInfo.put("recv_country_code", orderInfo.get(i).getDstnNation().trim());
				cneeInfo.put("recv_email", orderInfo.get(i).getCneeEmail());
				String regex = "[-+()]";
				String receiverTel = orderInfo.get(i).getCneeTel().replaceAll(regex, "");
				cneeInfo.put("recv_sms", receiverTel);
				cneeInfo.put("recv_address_residential_indicator", "no");
				dataOne.put("ship_to", cneeInfo);

				// return
				LinkedHashMap<String, Object> rtnInfo = new LinkedHashMap<String, Object>();
				rtnInfo.put("rtn_firm", "ACI");
				rtnInfo.put("rtn_name", "ACI");
				rtnInfo.put("rtn_addr1", "14056 Artesia Blvd");
				rtnInfo.put("rtn_addr2", "");
				rtnInfo.put("rtn_city_locality", "Cerritos");
				rtnInfo.put("rtn_state_province", "CA");
				rtnInfo.put("rtn_zip_postal_code", "90703");
				rtnInfo.put("rtn_zip4_code", "");
				rtnInfo.put("rtn_country_code", "US");
				rtnInfo.put("rtn_email", "");
				rtnInfo.put("rtn_sms", "");
				dataOne.put("return_address", rtnInfo);
				
				String key = "";
				//String weight = packWeight.get("value").toString();
				String weight_unit = packWeight.get("unit").toString();
				String length = dimens.get("length").toString();
				String width = dimens.get("width").toString();
				String height = dimens.get("height").toString();
				String dimension_unit = dimens.get("unit").toString();
				String recv_addr1 = cneeInfo.get("recv_addr1").toString();
				String recv_addr2 = cneeInfo.get("recv_addr2").toString();
				String recv_city = cneeInfo.get("recv_city_locality").toString();
				String recv_state = cneeInfo.get("recv_state_province").toString();
				String recv_zip_code = cneeInfo.get("recv_zip_postal_code").toString();
				String recv_country = cneeInfo.get("recv_country_code").toString();
				
				key = secretKey+weight+weight_unit+dimens.get("length")+dimens.get("width")+dimens.get("height")+dimension_unit+recv_addr1+recv_addr2+recv_city+recv_state+recv_zip_code+recv_country;				
				String value = key.trim();
				System.out.println("********");
				System.out.println(value);
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] bytes = md.digest(value.getBytes());
				StringBuffer bf = new StringBuffer();
				for (int j = 0; j < bytes.length; j++) {
					bf.append(Integer.toString((bytes[j] & 0xff) + 0x100, 16).substring(1));
				}
				String hashKey = bf.toString();


				dataOne.put("hash_key", hashKey);
				dataOne.put("has_dangerous_goods", "no");
				dataOne.put("has_hazardous_material", "no");
				
				// label
				LinkedHashMap<String, Object> label = new LinkedHashMap<String, Object>();
				label.put("format", "pdf");
				label.put("layout", "1");
				dataOne.put("label", label);
				
				
				// cust ref
				LinkedHashMap<String, Object> ref = new LinkedHashMap<String, Object>();
				ref.put("cust_ref1", "");
				ref.put("cust_ref2", "");
				ref.put("cust_ref3", "");
				ref.put("cust_ref4", "");
				ref.put("cust_ref5", "");
				dataOne.put("customer_reference", ref);
				
				dataList.add(dataOne);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnJsonArray.put("packages", dataList);
		
		return getJsonStringFromMap(rtnJsonArray);
	}

	private ProcedureVO getType86RegNo(String rtn, String nno, String userId, String userIp) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ProcedureVO rtnVal2 = new ProcedureVO();
		String rtnHawbNo = "";
		String rtnCarrier = "";
		String imgUrl = "";
		System.out.println("********* RESPONSE BODY ===========>");
		System.out.println(rtn);
		JSONObject json = new JSONObject(String.valueOf(rtn));
		
		HashMap<String, Object> params2 = new HashMap<String, Object>();
		String user = mapper.selectUserIdByNNO(nno);
		parameters.put("nno", nno);
		//parameters.put("transCode", "ACI-T86");
		
		try {
			JSONArray jsonArr = new JSONArray(String.valueOf(json.get("packages").toString()));
			
			for (int idx = 0; idx < jsonArr.length(); idx++) {
				JSONObject object = (JSONObject) jsonArr.get(idx);
				if (!object.get("status").toString().equals("S")) {
					throw new Exception();
				} else {
					JSONObject obj = object.getJSONObject("service_request");
					rtnHawbNo = obj.get("tracking_number").toString();
					
					if (obj.get("carrier_name").toString().equals("USPS")) {
						rtnCarrier = "USP";	
					} else if (obj.get("carrier_name").toString().toUpperCase().equals("FEDEX")) {
						if (obj.get("service_type").toString().toUpperCase().equals("FEDEX GROUND")) {
							rtnCarrier = "FEG";
							
						} else {
							rtnCarrier = "FES";
						}
					} else if (obj.get("carrier_name").toString().equals("DHL")) {
						rtnCarrier = "DHL";
					} else if (obj.get("carrier_name").toString().equals("UPS")) {
						rtnCarrier = "UPS";
					}
					
					imgUrl = obj.get("label_download").toString();
					
					SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
					Date time = new Date();
					String time1 = format1.format(time);
					
					params2.put("wDate", time1);
					params2.put("nno", nno);
					//mapper.updateOrderListDate(params2);
					
					MatchingVO matchVo = new MatchingVO();
					String hawbNo = mapper.selectHawbNoByNNO(nno);
					matchVo.setKeyHawbNo(hawbNo);
					matchVo.setValueMatchNo(hawbNo);
					matchVo.setMatchTransCode(rtnCarrier);
					matchVo.setNno(nno);
					//comnService.deleteMatchingInfo(matchVo);
					//comnService.insertMatchingInfo(matchVo);

					parameters.put("transCode", rtnCarrier);
					parameters.put("hawbNo", hawbNo);
					parameters.put("matchNo", hawbNo);
					parameters.put("delvNo", rtnHawbNo);
					comnMapper.insertDeliveryInfo(parameters);
					
					parameters.put("subTransCode", "ACI-T86");
					comnMapper.insertSubTransCode(parameters);
					
					String imageDir = realFilePath + "image/" + "aramex/";
					
					URL url = new URL(imgUrl);
					File file = new File(imageDir+hawbNo+".pdf");
					ReadableByteChannel read = Channels.newChannel(url.openStream());
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
					//file.delete();
					
					rtnVal2.setRstStatus("SUCCESS");
					rtnVal2.setRstMsg("SUCCESS");
				}
			}
		} catch (Exception e) {
			String failMsg = "";
			JSONArray array = new JSONArray(String.valueOf(json.get("packages").toString()));
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				JSONArray jArray = new JSONArray(String.valueOf(jsonObject.get("message").toString()));
				for (int j = 0; j < jArray.length(); j++) {
					failMsg += jArray.get(j).toString()+"\n";
				}
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("nno", nno);
				params.put("connUrl", "https://globalparcelservice.com/connect/api/label/create");
				params.put("headContents", "");
				params.put("bodyContents", jsonObject);
				params.put("errorMsg", failMsg);
				params.put("useYn", "Y");
				params.put("wUserId", userId);
				params.put("wUserIp", userIp);
				mapper.insertApiConn(params);
				//mapper.insertErrorMatch(params);
				
			}
			
			//comnService.deleteHawbNoInTbHawb(nno);
			rtnVal2.setRstStatus("FAIL");
			rtnVal2.setRstMsg(failMsg);
		}
	
		return rtnVal2;
	}


	private String fnMakeType86Json(String nno) throws Exception {
		
 		String jsonVal = makeType86Json(nno);
 		System.out.println("********* REQUEST BODY ===========>");
		System.out.println(jsonVal);

		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			
			URL url = new URL("https://globalparcelservice.com/connect/api/label/create");
			//URL url = new URL("https://globalparcelservice.com/sandbox/global/api/label/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			
			//conn.setRequestProperty("pcode", "TEST0001");
			//conn.setRequestProperty("branch", "001");
			//conn.setRequestProperty("apikey", "de9e39bfcadf2ed7183beb85e7d0619d");
	
			conn.setRequestProperty("pcode", "aciexpress");
			conn.setRequestProperty("branch", "001");
			conn.setRequestProperty("apikey", "320c03f29199e91d3194563b1da080f6");
			
			conn.setRequestProperty("lang", "en");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String result = "";
		
		
		return outResult.toString();
	}


	private String makeType86Json(String nno) throws Exception {
		
		// production
		String secretKey = "f9043d4702225550";
		
		// sandbox
		//String secretKey = "d93a7716d31c8211";
		
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderListVO> orderInfo = new ArrayList<ApiOrderListVO>();


		try {
			orderInfo = mapper.selectOrderList(nno);
			
			for (int i = 0; i < orderInfo.size(); i++) {
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
					
				// shipping datetime
				LinkedHashMap<String, Object> shippingInfo = new LinkedHashMap<String, Object>();
				String weight = "";
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				String day = format.format(now);
				shippingInfo.put("shipping_date", "");
				shippingInfo.put("shipping_time", "");
				shippingInfo.put("shipping_timezone", "");
				dataOne.put("shipping_datetime", shippingInfo);
				
				// port
				LinkedHashMap<String, Object> port = new LinkedHashMap<String, Object>();
				port.put("type", "");
				ArrayList<String> cityCode = new ArrayList<String>();
				cityCode.add("LAX");
				port.put("name", cityCode);
				dataOne.put("port", port);
				
				// weight 
				LinkedHashMap<String, Object> packWeight = new LinkedHashMap<String, Object>();
				Double wta = Double.parseDouble(orderInfo.get(i).getUserWta());
				//Double wtc = Double.parseDouble(orderInfo.get(i).getUserWtc());
				weight = formatDouble(wta);
				packWeight.put("value", wta);
				String wtUnit = orderInfo.get(i).getWtUnit();
				if (wtUnit.toUpperCase().equals("KG")) {
					packWeight.put("unit", "kg");
				} else if (wtUnit.toUpperCase().equals("LB")) {
					packWeight.put("unit", "lbs");
				} else if (wtUnit.toUpperCase().equals("G")) {
					packWeight.put("unit", "g");
				} else if (wtUnit.toUpperCase().equals("OZ")) {
					packWeight.put("unit", "oz");
				}
				dataOne.put("package_total_gross_weight", packWeight);
				
				// 무게에 따른 carrier 지정 
				Double wt = wta;
				Double realWt = 1.0;
				
				if (wtUnit.toLowerCase().toString().equals("kg")) {
					realWt = wt;
				} else if (wtUnit.toLowerCase().toString().equals("g")) {
					realWt = wt*0.001;
				} else if (wtUnit.toLowerCase().toString().equals("lb")) {
					realWt = wt*0.453592;
				} else if (wtUnit.toLowerCase().toString().equals("oz")) {
					realWt = wt/35.274;
				}

				String provider = "";
				String service = "";
				
				// 2023.07 ACI-US 배송사 코드 통합으로 type86 무게 7kg 초과 시 무조건 UPS로 설정
				//provider = "UPS";
				//service = "Ground";
				
				if (realWt > 2) {
					provider = "UPS";
					service = "Ground";
				} else if (realWt <= 0.5) {
					provider = "USPS";
					service = "LOWEST";
				} else if (realWt > 0.5 && realWt <= 2) {
					provider = "DHL";
					service = "GND";
				}
				
				
				//provider = "DHL";
				//service = "GND";

				// carrier
				LinkedHashMap<String, Object> carrier = new LinkedHashMap<String, Object>();
				carrier.put("provider", provider);
				carrier.put("service_type", service);
				carrier.put("packaging_type", "YOUR_PACKAGING");
				carrier.put("transit_time", "");
				carrier.put("contract_id", "");
				dataOne.put("carrier", carrier);
				
				// dimensions
				LinkedHashMap<String, Object> dimens = new LinkedHashMap<String, Object>();
				String dimUnit = orderInfo.get(i).getDimUnit();
				if(dimUnit != null) {
					if (dimUnit.toUpperCase().equals("CM")) {
						dimens.put("unit", "cm");
					} else if (dimUnit.toUpperCase().equals("IN")) {
						dimens.put("unit", "in");
					}
				} else {
					dimens.put("unit", "cm");
				}
				dimens.put("length", orderInfo.get(i).getUserLength());
				dimens.put("width", orderInfo.get(i).getUserWidth());
				dimens.put("height", orderInfo.get(i).getUserHeight());
				dataOne.put("package_dimensions", dimens);
				
				// shipper
				LinkedHashMap<String, Object> shipperInfo = new LinkedHashMap<String, Object>();
				shipperInfo.put("send_firm", orderInfo.get(i).getShipperName());
				shipperInfo.put("send_name", orderInfo.get(i).getShipperName());
				
				if (orderInfo.get(i).getOrgStation().equals("213")) {
					shipperInfo.put("send_addr1", orderInfo.get(i).getShipperAddr());
					shipperInfo.put("send_addr2", orderInfo.get(i).getShipperAddrDetail());
					shipperInfo.put("send_city_locality", orderInfo.get(i).getShipperCity());
					shipperInfo.put("send_state_province", orderInfo.get(i).getShipperState());
					shipperInfo.put("send_zip_postal_code", orderInfo.get(i).getShipperZip());
				} else {
					shipperInfo.put("send_addr1", "14056 Artesia Blvd");
					shipperInfo.put("send_addr2","");
					shipperInfo.put("send_city_locality","Cerritos");
					shipperInfo.put("send_state_province","CA");
					shipperInfo.put("send_zip_postal_code","90703");
				}
				shipperInfo.put("send_country_code", "US");
				shipperInfo.put("send_email", orderInfo.get(i).getShipperEmail());
				shipperInfo.put("send_sms", orderInfo.get(i).getShipperTel());
				shipperInfo.put("send_address_residential_indicator", "no");
				dataOne.put("ship_from", shipperInfo);
				
				// cnee
				LinkedHashMap<String, Object> cneeInfo = new LinkedHashMap<String, Object>();
				cneeInfo.put("recv_firm", orderInfo.get(i).getCneeName());
				cneeInfo.put("recv_name", orderInfo.get(i).getCneeName());
				cneeInfo.put("recv_addr1", orderInfo.get(i).getCneeAddr().trim());
				cneeInfo.put("recv_addr2", orderInfo.get(i).getCneeAddrDetail().trim());
				cneeInfo.put("recv_city_locality", orderInfo.get(i).getCneeCity().trim());
				cneeInfo.put("recv_state_province", orderInfo.get(i).getCneeState().trim());
				cneeInfo.put("recv_zip_postal_code", orderInfo.get(i).getCneeZip().trim());
				cneeInfo.put("recv_zip4_code", "");
				cneeInfo.put("recv_country_code", orderInfo.get(i).getDstnNation().trim());
				cneeInfo.put("recv_email", orderInfo.get(i).getCneeEmail());
				String regex = "[-+()]";
				String receiverTel = orderInfo.get(i).getCneeTel().replaceAll(regex, "");
				cneeInfo.put("recv_sms", receiverTel);
				cneeInfo.put("recv_address_residential_indicator", "no");
				dataOne.put("ship_to", cneeInfo);

				// return
				LinkedHashMap<String, Object> rtnInfo = new LinkedHashMap<String, Object>();
				rtnInfo.put("rtn_firm", "ACI");
				rtnInfo.put("rtn_name", "ACI");
				rtnInfo.put("rtn_addr1", "14056 Artesia Blvd");
				rtnInfo.put("rtn_addr2", "");
				rtnInfo.put("rtn_city_locality", "Cerritos");
				rtnInfo.put("rtn_state_province", "CA");
				rtnInfo.put("rtn_zip_postal_code", "90703");
				rtnInfo.put("rtn_zip4_code", "");
				rtnInfo.put("rtn_country_code", "US");
				rtnInfo.put("rtn_email", "");
				rtnInfo.put("rtn_sms", "");
				dataOne.put("return_address", rtnInfo);
				
				String key = "";
				//String weight = packWeight.get("value").toString();
				String weight_unit = packWeight.get("unit").toString();
				String length = dimens.get("length").toString();
				String width = dimens.get("width").toString();
				String height = dimens.get("height").toString();
				String dimension_unit = dimens.get("unit").toString();
				String recv_addr1 = cneeInfo.get("recv_addr1").toString();
				String recv_addr2 = cneeInfo.get("recv_addr2").toString();
				String recv_city = cneeInfo.get("recv_city_locality").toString();
				String recv_state = cneeInfo.get("recv_state_province").toString();
				String recv_zip_code = cneeInfo.get("recv_zip_postal_code").toString();
				String recv_country = cneeInfo.get("recv_country_code").toString();
				
				key = secretKey+weight+weight_unit+dimens.get("length")+dimens.get("width")+dimens.get("height")+dimension_unit+recv_addr1+recv_addr2+recv_city+recv_state+recv_zip_code+recv_country;				
				String value = key.trim();
				System.out.println("********");
				System.out.println(value);
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] bytes = md.digest(value.getBytes());
				StringBuffer bf = new StringBuffer();
				for (int j = 0; j < bytes.length; j++) {
					bf.append(Integer.toString((bytes[j] & 0xff) + 0x100, 16).substring(1));
				}
				String hashKey = bf.toString();


				dataOne.put("hash_key", hashKey);
				dataOne.put("has_dangerous_goods", "no");
				dataOne.put("has_hazardous_material", "no");
				
				// label
				LinkedHashMap<String, Object> label = new LinkedHashMap<String, Object>();
				label.put("format", "pdf");
				label.put("layout", "1");
				dataOne.put("label", label);
				
				
				// cust ref
				LinkedHashMap<String, Object> ref = new LinkedHashMap<String, Object>();
				ref.put("cust_ref1", "");
				ref.put("cust_ref2", "");
				ref.put("cust_ref3", "");
				ref.put("cust_ref4", "");
				ref.put("cust_ref5", "");
				dataOne.put("customer_reference", ref);
				
				dataList.add(dataOne);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnJsonArray.put("packages", dataList);
		
		return getJsonStringFromMap(rtnJsonArray);
	}


	private String getJsonStringFromMap(LinkedHashMap<String, Object> rtnJsonArray) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : rtnJsonArray.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}
		
		return jsonObject.toString();
		
	}
	
	public String makeT86Pod(String hawbNo) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> rtnJson = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> trackJson = new LinkedHashMap<String, Object>();
		String matchNum = mapper.selectMatchNumByHawb(hawbNo);
		//String matchNum = "420331419200190300900200035641";
		rtnJson.put("tracking_no", matchNum);
		rtnJsonArray.add(rtnJson);
		trackJson.put("tracking", rtnJsonArray);
		
		String jsonVal = getJsonStringFromMap(trackJson);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			URL url = new URL("https://globalparcelservice.com/connect/api/tracking/track_trace");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("pcode", "aciexpress");
			conn.setRequestProperty("branch", "001");
			conn.setRequestProperty("apikey", "320c03f29199e91d3194563b1da080f6");
			conn.setRequestProperty("lang", "en");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		
		return outResult.toString();
	}



	public ArrayList<HashMap<String, Object>> makePodDetailArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		System.out.println(json);
		String hawbInDate = apiMapper.selectHawbInDate(hawbNo);	// 입고
		String mawbInDate = apiMapper.selectMawbInDate(hawbNo);	// 출고
		String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록

		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		
		try {
			if (!json.get("status").toString().equals("S")) {
				throw new Exception();
			} else {
				JSONArray trackingList = new JSONArray(String.valueOf(json.get("tracking").toString()));
				for (int i = 0; i < trackingList.length(); i++) {
					JSONObject object = (JSONObject) trackingList.get(i);
					if (!object.get("status").toString().equals("S")) {
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "-200");
						podDetail.put("UpdateDateTime", "");
						podDetail.put("UpdateLocation", "");
						podDetail.put("UpdateDescription", "No Data");
						podDetatailArray.add(podDetail);
					} else {
						JSONArray objArr = new JSONArray(String.valueOf(object.get("details").toString()));
						for (int j = 0; j < objArr.length(); j++) {
							JSONObject tracking = (JSONObject) objArr.get(j);
							String dateTime = tracking.getString("datetime").toString();
							podDetail = new LinkedHashMap<String, Object>();
							if (tracking.get("event_id").toString().equals("900")) {
								podDetail.put("UpdateCode", "600");
								podDetail.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("UpdateLocation", tracking.get("location"));
								podDetail.put("UpdateDescription", "Delivered");
							} else if (tracking.get("description").toString().toUpperCase().equals("OUT FOR DELIVERY")) {
								podDetail.put("UpdateCode", "500");
								podDetail.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("UpdateLocation", tracking.get("location"));
								podDetail.put("UpdateDescription", "Out for Delivery");
							} else if (tracking.get("event_id").toString().equals("100") && tracking.get("sub_event_id").toString().equals("117")) {
								podDetail.put("UpdateCode", "400");
								podDetail.put("UpdateDateTime", dateTime.substring(0, dateTime.length() - 3));
								podDetail.put("UpdateLocation", tracking.get("location"));
								podDetail.put("UpdateDescription", "Arrival in destination country");
							} else {
								continue;
							}
							podDetatailArray.add(podDetail);
						}
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "300");
						podDetail.put("UpdateDateTime", mawbInDate.substring(0, mawbInDate.length() - 3));
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Picked up by Shipping Partner");
						podDetatailArray.add(podDetail);
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "200");
						podDetail.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Finished warehousing");
						podDetatailArray.add(podDetail);
						
						podDetail = new LinkedHashMap<String, Object>();
						podDetail.put("UpdateCode", "100");
						podDetail.put("UpdateDateTime", regInDate);
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Order information has been entered");
						podDetatailArray.add(podDetail);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		return podDetatailArray;
	}
	
	
	public String formatDouble(double number) {
		if (number == (long) number) {
			return String.format("%d", (long) number);
		} else {
			return String.format("%s", number);
		}
	}
	
}
