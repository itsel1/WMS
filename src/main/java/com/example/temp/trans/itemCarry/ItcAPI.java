package com.example.temp.trans.itemCarry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ItcAPI {
	
	@Autowired
	ItcMapper itcMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	ManagerService mgrService;
	
	@Value("${filePath}")
    String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public String itcJsonSend(String nno) throws Exception{
		ApiOrderListVO orderInfo = new ItcOrderVO();
		orderInfo = itcMapper.selectOrderInfo(nno);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		String jsonVal = fnMakeItcJson(orderInfo);
		String inputLine = null;
		//StringBuffer outResult = new StringBuffer();
		String outResult = new String();
		try {
			String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/regdata.php";
//			String requestURL = "http://dev.allsaja.com/static/ajax/apiv1/regdata.php";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성 
			postRequest.setHeader("Content-Type", "application/json");
			HashMap<String,Object> itcApiInfo = new HashMap<String,Object>();
			itcApiInfo = itcMapper.selectAciInfo(orderInfo.getOrgStation());
			postRequest.setHeader("Api_id", itcApiInfo.get("apiId").toString());
			postRequest.setHeader("Api_key", itcApiInfo.get("apiKey").toString());
//			postRequest.setHeader("Api_id", "testkr");
//			postRequest.setHeader("Api_key", "ZANWA74AaGEP5QQ");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력 
			
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity);
			}
			
			
			
//			URL url = new URL("https://info.itemcarry.com/static/ajax/apiv1/regdata.php");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setConnectTimeout(50000);
//			conn.setReadTimeout(50000);
//			
//			OutputStream os = conn.getOutputStream();
//			
//			os.write(jsonVal.getBytes());
//			os.flush();
//			
//			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//			while((inputLine = in.readLine())!=null) {
//				outResult.append(inputLine);
//			}
//			conn.disconnect();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return outResult.toString();
	}
	
	public String fnMakeItcJson(ApiOrderListVO orderInfo) throws Exception{
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> boxList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> boxOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> goodsList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> goodsOne = new LinkedHashMap<String, Object>();
		ArrayList<ItcItemVO> orderItem = new ArrayList<ItcItemVO>();
		
		
		dataOne.put("B", orderInfo.getOrderNo()); //주문번호
		dataOne.put("C", orderInfo.getCneeName()); //이름
		dataOne.put("D", ""); //Consignee Name
		if(orderInfo.getCneeTel().equals("")) {
			dataOne.put("E", orderInfo.getCneeHp()); //연락처2
		}else {
			dataOne.put("E", orderInfo.getCneeTel()); //연락처1
		}
		
		dataOne.put("F", orderInfo.getCneeHp()); //연락처2
		
		dataOne.put("G", orderInfo.getCneeZip()); //우편번호
		dataOne.put("H", orderInfo.getCneeAddr()); //주소1
		dataOne.put("I", orderInfo.getCneeAddrDetail()); //주소2
		dataOne.put("J", ""); //주소 (영어)
		
		//boxInfo
		boxOne.put("K", orderInfo.getWtUnit()); //무게단위
		boxOne.put("L", orderInfo.getUserWta()); //무게
		boxOne.put("M", orderInfo.getUserWidth()); //가로
		boxOne.put("N", orderInfo.getUserLength()); //세로
		boxOne.put("O", orderInfo.getUserHeight()); //높이
		boxList.add(boxOne);
		dataOne.put("BoxInfo", boxList);
		if(!orderInfo.getOrderType().toUpperCase().equals("TAKEIN"))
			orderItem = itcMapper.selectOrderItem(orderInfo.getNno());
		else
			orderItem = itcMapper.selectOrderItemTakeIn(orderInfo.getNno());
		//ItemInfo
		for(int i = 0; i < orderItem.size(); i++) {
			goodsOne = new LinkedHashMap<String, Object>();
			goodsOne.put("P", orderItem.get(i).getItemUrl()); //상품URL
			goodsOne.put("Q", orderItem.get(i).getHsCode()); //HS_CODE
			goodsOne.put("R", orderItem.get(i).getBrand()); //브랜드
			goodsOne.put("S", orderItem.get(i).getItemDetail()); //상품명
			goodsOne.put("T", orderItem.get(i).getMakeCntry()); //원산지
			goodsOne.put("U", orderItem.get(i).getChgCurrency()); //통화
			goodsOne.put("V", orderItem.get(i).getUnitValue()); //단가
			goodsOne.put("W", orderItem.get(i).getItemCnt()); //수량
			goodsOne.put("Z", orderItem.get(i).getItemMeterial()); //성분
			goodsOne.put("AA", ""); //상품 레퍼런스
			goodsOne.put("AB", orderItem.get(i).getCusItemCode()); //상품코드
			goodsList.add(goodsOne);
		}
		
		dataOne.put("Items", goodsList);
		
		dataOne.put("X", ""); //주문레퍼런스1
		dataOne.put("Y", orderInfo.getNno()); //주문레퍼런스2 (출력용 레이블 구분값) 주문서 업체 고유값
		dataOne.put("AC", "Y"); // Y = SAGAWA / N = JP_POST
		dataOne.put("AD", orderInfo.getShipperName());//
		dataOne.put("AE", orderInfo.getShipperTel());//
		dataOne.put("AF", orderInfo.getShipperHp());//
		dataOne.put("AG", orderInfo.getShipperZip());//
		dataOne.put("AH", orderInfo.getShipperAddr());//
		dataOne.put("AI", orderInfo.getShipperAddrDetail());//
		
		dataList.add(dataOne);
		rtnJsonArray.put("DataList", dataList);
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public ProcedureVO getItcResult(String jsonResult, String nno, String userId, String userIp) throws Exception {
		ProcedureVO returnVal = new ProcedureVO();
		String rtnHawbNo = "";
		String rtnURL = "";
		JSONObject json = new JSONObject(String.valueOf(jsonResult));
		try {
			if(json.get("code").toString().equals("01")) {
				String failMsg = "";
				JSONArray jsonArry = new JSONArray(String.valueOf(json.get("message").toString()));
				for(int i = 0; i < jsonArry.length(); i++) {
					String errMsg = jsonArry.getString(i);
					HashMap<String,Object> parameters = new HashMap<String,Object>();
					parameters.put("nno", nno);
					parameters.put("errorMsg",errMsg);
					parameters.put("useYn", "Y");
					parameters.put("wUserId", userId);
					parameters.put("wUserIp", userIp);
					//mapper.insertErrorMatch(parameters);
					
					failMsg += errMsg+"  ";
				}
				 
				// TODO: handle exception
				comnService.deleteHawbNoInTbHawb(nno);
				returnVal.setRstStatus("FAIL");
				returnVal.setRstMsg(failMsg);
			}else {
				JSONArray jsonArry = new JSONArray(String.valueOf(json.get("data").toString()));
				JSONObject jsonDetail = jsonArry.getJSONObject(0);
				rtnHawbNo = jsonDetail.getString("WayBill");
				rtnURL = jsonDetail.getString("PrintLableUrl");
				returnVal.setRstNno(nno);
				returnVal.setRstStatus("SUCCESS");
				returnVal.setRstMsg("SUCCESS");
				returnVal.setRstHawbNo(rtnHawbNo);
				returnVal.setOrgHawbNo(rtnHawbNo);
				itcMapper.updateHawbOrderList(returnVal);
				
				FileOutputStream fos = null;
				InputStream is = null;
				String ImageDir = realFilePath + "image/" + "aramex/";
				fos = new FileOutputStream(ImageDir+ rtnHawbNo +".PDF");
				
				try {
					URL url = new URL(rtnURL);
					URLConnection urlConnection = url.openConnection();
					is = urlConnection.getInputStream();
					byte[] buffer = new byte[2048];
					int readBytes;
					while((readBytes = is.read(buffer)) != -1) {
						fos.write(buffer,0,readBytes);
					}
					
					fos.close();
					is.close();
					AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
					amazonS3 = new AmazonS3Client(awsCredentials);
					PutObjectResult asssd = new PutObjectResult();
					File file = new File(ImageDir+ rtnHawbNo+".PDF");
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
					if(amazonS3 != null) {
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, userId+"_"+rtnHawbNo, file);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						asssd = amazonS3.putObject(putObjectRequest);
					}
					amazonS3 = null;
					//file.delete();
				}catch (Exception e) {
					// TODO: handle exception
					comnService.deleteHawbNoInTbHawb(nno);
					returnVal.setRstStatus("FAIL");
					returnVal.setRstMsg(e.getMessage());
					return returnVal;  
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			comnService.deleteHawbNoInTbHawb(nno);
			returnVal.setRstStatus("FAIL");
			returnVal.setRstMsg(e.getMessage());
			return returnVal;
		}
		
		
		return returnVal;
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

	public String itcJsonUpdate(String nno) throws Exception{
		ApiOrderListVO orderInfo = new ItcOrderVO();
		orderInfo = itcMapper.selectOrderInfoHawb(nno);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		String jsonVal = fnMakeUpdateItcJson(orderInfo);
		//StringBuffer outResult = new StringBuffer();+
		String outResult = new String();
		try {
			HashMap<String,Object> itcApiInfo = new HashMap<String,Object>();
			String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/updatedata.php";
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPut postRequest = new HttpPut(requestURL); //POST 메소드 URL 새성 
			postRequest.setHeader("Content-Type", "application/json");
			
			itcApiInfo = itcMapper.selectAciInfo(orderInfo.getOrgStation());
			postRequest.setHeader("Api_id", itcApiInfo.get("apiId").toString());
			postRequest.setHeader("Api_key", itcApiInfo.get("apiKey").toString());
//			postRequest.setHeader("Api_id", "aciexpress");
//			postRequest.setHeader("Api_key", "3QDkVDPfW2HYC34");
			postRequest.setEntity(new StringEntity(jsonVal, "UTF-8")); //json 메시지 입력 
			
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				outResult = handler.handleResponse(response);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity);
			}
			
//			URL url = new URL("https://info.itemcarry.com/static/ajax/apiv1/regdata.php");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setConnectTimeout(50000);
//			conn.setReadTimeout(50000);
//			
//			OutputStream os = conn.getOutputStream();
//			
//			os.write(jsonVal.getBytes());
//			os.flush();
//			
//			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//			while((inputLine = in.readLine())!=null) {
//				outResult.append(inputLine);
//			}
//			conn.disconnect();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return outResult.toString();
	}
	
	public String fnMakeUpdateItcJson(ApiOrderListVO orderInfo) throws Exception{
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> boxList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> boxOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> goodsList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> goodsOne = new LinkedHashMap<String, Object>();
		
		ArrayList<ItcItemVO> orderItem = new ArrayList<ItcItemVO>();
		dataOne.put("OrdNo", orderInfo.getHawbNo()); //송장번호
		//dataOne.put("B", orderInfo.getOrderNo()); //주문번호
		dataOne.put("C", orderInfo.getCneeName()); //이름
		dataOne.put("D", orderInfo.getCneeName()); //Consignee Name
		dataOne.put("E", orderInfo.getCneeTel()); //연락처1
		dataOne.put("F", orderInfo.getCneeHp()); //연락처2
		dataOne.put("G", orderInfo.getCneeZip()); //우편번호
		dataOne.put("H", orderInfo.getCneeAddr()); //주소1
		dataOne.put("I", orderInfo.getCneeAddrDetail()); //주소2
		dataOne.put("J", ""); //주소 (영어)
		
		//boxInfo
		boxOne.put("K", orderInfo.getWtUnit()); //무게단위
		boxOne.put("L", orderInfo.getUserWta()); //무게
		boxOne.put("M", orderInfo.getUserWidth()); //가로
		boxOne.put("N", orderInfo.getUserLength()); //세로
		boxOne.put("O", orderInfo.getUserHeight()); //높이
		boxList.add(boxOne);
		dataOne.put("BoxInfo", boxList);
		
		orderItem = itcMapper.selectOrderItem(orderInfo.getNno());
		//ItemInfo
		for(int i = 0; i < orderItem.size(); i++) {
			goodsOne = new LinkedHashMap<String, Object>();
			goodsOne.put("P", orderItem.get(i).getItemUrl()); //상품URL
			goodsOne.put("Q", orderItem.get(i).getHsCode()); //HS_CODE
			goodsOne.put("R", orderItem.get(i).getBrand()); //브랜드
			goodsOne.put("S", orderItem.get(i).getItemDetail()); //상품명
			goodsOne.put("T", orderItem.get(i).getMakeCntry()); //원산지
			goodsOne.put("U", orderItem.get(i).getChgCurrency()); //통화
			goodsOne.put("V", orderItem.get(i).getUnitValue()); //단가
			goodsOne.put("W", orderItem.get(i).getItemCnt()); //수량
			goodsOne.put("Z", orderItem.get(i).getItemMeterial()); //성분
			goodsOne.put("AA", ""); //상품 레퍼런스
			goodsOne.put("AB", orderItem.get(i).getCusItemCode()); //상품코드
			goodsList.add(goodsOne);
		}
		
		dataOne.put("Items", goodsList);
		
		dataOne.put("X", ""); //주문레퍼런스1
		dataOne.put("Y", orderInfo.getNno()); //주문레퍼런스2 (출력용 레이블 구분값) 주문서 업체 고유값
//		dataOne.put("AC", "Y"); // Y = SAGAWA / N = JP_POS
		dataOne.put("AD", orderInfo.getShipperName());//
		dataOne.put("AE", orderInfo.getShipperTel());//
		dataOne.put("AF", orderInfo.getShipperHp());//
		dataOne.put("AG", orderInfo.getShipperZip());//
		dataOne.put("AH", orderInfo.getShipperAddr());//
		dataOne.put("AI", orderInfo.getShipperAddrDetail());//
		
		dataList.add(dataOne);
		rtnJsonArray.put("DataList", dataList);
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public ArrayList<HashMap<String, Object>> makeItcPod (String hawbNo) throws Exception{
		ApiOrderListVO orderInfo = new ItcOrderVO();
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		HttpResponse response =null;
		String outResult = new String();
		JsonParser parse = new JsonParser();
		
		orderInfo = itcMapper.selectOrderInfoHawb(mgrService.selectNNOByHawbNo(hawbNo));
		
//		String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/deliveryStatus.php?jsonData={\"DataList\":[{\"WayBill\":\""+hawbNo+"\"}]}";
		String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/deliveryStatus.php?jsonData="+URLEncoder.encode("{\"DataList\":[{\"WayBill\":\""+hawbNo+"\"}]}","UTF-8");
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Accept", "application/json");
		getRequest.setHeader("charset", "UTF-8");
		
		HashMap<String,Object> itcApiInfo = new HashMap<String,Object>();
		itcApiInfo = itcMapper.selectAciInfo(orderInfo.getOrgStation());
		getRequest.setHeader("Api_id", itcApiInfo.get("apiId").toString());
		getRequest.setHeader("Api_key", itcApiInfo.get("apiKey").toString());
		
//		getRequest.setHeader("Api_id", "aciexpress");
//		getRequest.setHeader("Api_key", "3QDkVDPfW2HYC34");
		
		
		
		response = client.execute(getRequest);
		ResponseHandler<String> handler = new BasicResponseHandler();
		outResult = handler.handleResponse(response);
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			JsonElement element = parse.parse(outResult);
			JsonObject dataInfo = element.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
			if(dataInfo.get("MSG") != null) {
				if(!dataInfo.get("MSG").getAsString().equals("no record found")) {
					JsonArray jsonArray = element.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("INF");
					for(int i =jsonArray.size()-1; i > -1; i--) {
						podDetatil  = new LinkedHashMap<String,Object>();
						
						if(jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("TA")||jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("MB"))
							continue;
						else {
							podDetatil.put("UpdateCode",jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString());
							podDetatil.put("UpdateDateTime", jsonArray.get(i).getAsJsonObject().get("LCLDATE").getAsString());
							if(jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("OB"))
								podDetatil.put("UpdateLocation", "");
							else
								podDetatil.put("UpdateLocation", "JAPAN");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
							podDetatil.put("ProblemCode",jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString()); 
							podDetatil.put("Comments", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
							podDetatailArray.add(podDetatil);
						}

					}
				}
			}
			aciPodInfo = itcMapper.selectAciPodInfo(hawbNo);
			if(!aciPodInfo.get("hawbDate").equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S011");
				podDetatil.put("UpdateDateTime", aciPodInfo.get("hawbDate"));
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "Finished warehousing.");
				podDetatil.put("ProblemCode","S001"); 
				podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatailArray.add(podDetatil);
			}
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","S001");
			podDetatil.put("UpdateDateTime", aciPodInfo.get("orderDate"));
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatil.put("ProblemCode","S001"); 
			podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
			podDetatailArray.add(podDetatil);
			
		}
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makeItcPodNew(String hawbNo) throws Exception{
		ApiOrderListVO orderInfo = new ItcOrderVO();
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		HttpResponse response =null;
		String outResult = new String();
		JsonParser parse = new JsonParser();
		
		orderInfo = itcMapper.selectOrderInfoHawb(mgrService.selectNNOByHawbNo(hawbNo));
		
//		String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/deliveryStatus.php?jsonData={\"DataList\":[{\"WayBill\":\""+hawbNo+"\"}]}";
		String requestURL = "http://info.itemcarry.com/static/ajax/apiv1/deliveryStatus.php?jsonData="+URLEncoder.encode("{\"DataList\":[{\"WayBill\":\""+hawbNo+"\"}]}","UTF-8");
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(requestURL); //POST 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Accept", "application/json");
		getRequest.setHeader("charset", "UTF-8");
		
		HashMap<String,Object> itcApiInfo = new HashMap<String,Object>();
		itcApiInfo = itcMapper.selectAciInfo(orderInfo.getOrgStation());
		getRequest.setHeader("Api_id", itcApiInfo.get("apiId").toString());
		getRequest.setHeader("Api_key", itcApiInfo.get("apiKey").toString());
		
//		getRequest.setHeader("Api_id", "aciexpress");
//		getRequest.setHeader("Api_key", "3QDkVDPfW2HYC34");
		
		
		
		response = client.execute(getRequest);
		ResponseHandler<String> handler = new BasicResponseHandler();
		outResult = handler.handleResponse(response);
		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
			JsonElement element = parse.parse(outResult);
			JsonObject dataInfo = element.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
			if(dataInfo.get("MSG") != null) {
				if(!dataInfo.get("MSG").getAsString().equals("no record found")) {
					JsonArray jsonArray = element.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("INF");
					for(int i =jsonArray.size()-1; i > -1; i--) {
						podDetatil  = new LinkedHashMap<String,Object>();
						String date = jsonArray.get(i).getAsJsonObject().get("LCLDATE").getAsString();
						String time = jsonArray.get(i).getAsJsonObject().get("LCLTIME").getAsString();
						String datetime = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8)+" "+time.substring(0,2)+":"+time.substring(2);
						
						if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("LD")) {
							podDetatil.put("UpdateCode", "600");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "JAPAN");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
						} else if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("BD")) {
							podDetatil.put("UpdateCode", "500");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "JAPAN");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
						} else if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("CR")) {
							podDetatil.put("UpdateCode", "480");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "JAPAN");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
						} else if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("IB")) {
							podDetatil.put("UpdateCode", "400");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "JAPAN");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
						} else if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("TA")) {
							podDetatil.put("UpdateCode", "350");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "");
							podDetatil.put("UpdateDescription", jsonArray.get(i).getAsJsonObject().get("STATUS_US").getAsString());
						} else if (jsonArray.get(i).getAsJsonObject().get("STATUS").getAsString().equals("OB")) {
							podDetatil.put("UpdateCode", "300");
							podDetatil.put("UpdateDateTime", datetime);
							podDetatil.put("UpdateLocation", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
							podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
						} else {
							continue;
						}
						podDetatailArray.add(podDetatil);
					}
				}
			}
			aciPodInfo = itcMapper.selectAciPodInfo(hawbNo);
			String hawbDate = aciPodInfo.get("hawbDate").toString();
			if(!aciPodInfo.get("hawbDate").equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","200");
				podDetatil.put("UpdateDateTime", hawbDate.substring(0, hawbDate.length()-3));
				podDetatil.put("UpdateLocation", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatil.put("UpdateDescription", "Finished warehousing.");
				podDetatailArray.add(podDetatil);
			}
			String orderDate = aciPodInfo.get("orderDate").toString();
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","100");
			podDetatil.put("UpdateDateTime", orderDate.substring(0, orderDate.length()-3));
			podDetatil.put("UpdateLocation", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatailArray.add(podDetatil);
			
		}
		return podDetatailArray;
	}
	
	
	public void selectBlApplyITC(String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<BlApplyVO> blApply = new ArrayList<BlApplyVO>();
		
		BlApplyVO tmp = new BlApplyVO();
		ProcedureVO rtnVal = new ProcedureVO();
		tmp = comnService.selectBlApply(orderNno,userId,userIp);
		
		String ysRtn = itcJsonSend(orderNno);
		try {
			
			rtnVal = getItcResult(ysRtn, orderNno, userId, userIp);
			
			if(rtnVal.getRstStatus().equals("FAIL")) {
				UserOrderListVO orderInfos = new UserOrderListVO ();
				
				orderInfos = comnService.selectUserRegistOrderOne(orderNno);
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(), userId, userIp);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(), userId, userIp);
		}
			
	}
}
