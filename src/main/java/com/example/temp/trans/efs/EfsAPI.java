package com.example.temp.trans.efs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.security.SecurityKeyVO;

@Service
public class EfsAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	EfsMapper mapper;
	
	@Autowired
	MemberMapper usrMapper;
	
	@Autowired
	ComnService comnService;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public String fnMakeEfsJson(String nno) throws Exception{
		
		String jsonVal = makeEfsJson(nno);
		System.out.println(jsonVal);
		//String userKey = apiService.selectUserKey((String) jsonHeader.get("userid"));
		String outResult = new String();
		try {
			String requestURL = "http://www.efs.asia:200/api/in/";
			
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> testParam = new ArrayList<>();
			//testParam.add(new BasicNameValuePair("apikey",("8433e4d3904e627d417857ef5bce2917")));
			// 226ac50645ae82faf4d3afebe2589ef5
			testParam.add(new BasicNameValuePair("apikey",("226ac50645ae82faf4d3afebe2589ef5")));
			testParam.add(new BasicNameValuePair("req_function",("createShipment")));
			testParam.add(new BasicNameValuePair("send_data",(jsonVal)));
			postRequest.setEntity(new UrlEncodedFormEntity(testParam, "UTF-8"));
			HttpResponse response = client.execute(postRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity, "UTF-8");
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				outResult = handler.handleResponse(response);
				System.out.println("success");
				System.out.println(entity);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity, "UTF-8");
				System.out.println("fail");
				System.out.println(entity);
			}
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
			outResult="FAIL";
		}
		return outResult;
//		return "";
	}
	
	public ProcedureVO getCheckResult(String rtnMsg, String nno) throws Exception{
		ProcedureVO rtnVal = new ProcedureVO();
		String msg = rtnMsg;
		msg = msg.replace("\r\n", "|");
		if(msg.contains("|Y|")) {
			String[] tempString = msg.split("[|]");
			String hawbNo = tempString[2];
			rtnVal.setRstHawbNo(hawbNo);
			rtnVal.setRstStatus("SUCCESS");
			MatchingVO matchVo = new MatchingVO();
			String orgHawb = mapper.selectHawbNoByNNO(nno);
			matchVo.setKeyHawbNo(orgHawb);
			matchVo.setValueMatchNo(hawbNo);
			matchVo.setMatchTransCode("EFS");
			matchVo.setNno(nno);
			comnService.deleteMatchingInfo(matchVo);
			comnService.insertMatchingInfo(matchVo);
		}else {
			rtnVal.setRstStatus("FAIL");
			String[] tempString = msg.split("[N]");
			String[] tempString2 = tempString[1].split("[|]");
			String sendMsg = tempString2[1].replace("Consignee", "Receiver");
			rtnVal.setRstMsg(sendMsg);
		}
		return rtnVal;
	}
	
	
	public String makeEfsJson(String nno) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ApiOrderItemEFSVO> orderItem = new ArrayList<ApiOrderItemEFSVO>();
		ArrayList<ApiOrderEFSVO> orderInfo = new ArrayList<ApiOrderEFSVO>();
		String EfsString = "";
		
		try {
			
			orderInfo = mapper.selectListInfoForEFS(nno);
			orderInfo.get(0).setSymmetryKey(originKey.getSymmetryKey());
			orderInfo.get(0).dncryptData();
			EfsString +="|";//송장번호
			EfsString +=orderInfo.get(0).getHawbNo()+"|";//발송품 참조 번호
			if(orderInfo.get(0).getTransCode().equals("EFSD")) {
				EfsString +="DHL"+"|";//발송타입 - Premium,EMS,TNT,DHL,DTP...
			}else if (orderInfo.get(0).getTransCode().equals("EFSP")) {
				EfsString +="Premium"+"|";//발송타입 - Premium,EMS,TNT,DHL,DTP...
			}else {
				EfsString +="Premium"+"|";//발송타입 - Premium,EMS,TNT,DHL,DTP...
			}
			EfsString +=orderInfo.get(0).getShipperName()+"|";//송화인명
			EfsString +=orderInfo.get(0).getShipperAddr() + " " + orderInfo.get(0).getShipperAddrDetail()+"|";//송화인 주소
			EfsString +=orderInfo.get(0).getShipperZip()+"|";//송화인 우편번호
			EfsString +=orderInfo.get(0).getShipperTel()+"|";//송화인 전화번호
			EfsString +=orderInfo.get(0).getShipperHp()+"|";//송화인 휴대전화 번호
			EfsString +=orderInfo.get(0).getShipperCntry()+"|";//송화인 국가코드
			EfsString +=orderInfo.get(0).getShipperCity()+"|";//송화인 도시코드
			EfsString +=orderInfo.get(0).getCneeName()+"|";//수화인 이름
			EfsString +=orderInfo.get(0).getCneeAddrDetail() + " " + orderInfo.get(0).getCneeAddr()+ "|";//수화인 주소
			EfsString +=orderInfo.get(0).getCneeZip()+"|";//수화인 우편번호
			EfsString +=orderInfo.get(0).getCneeTel()+"|";//수화인 전화번호
			EfsString +=orderInfo.get(0).getCneeHp()+"|";//수화인 휴대전화번호
			EfsString +=orderInfo.get(0).getDstnNation()+"|";//수화인 국가코드
			EfsString +=orderInfo.get(0).getCneeCity()+" "+orderInfo.get(0).getCneeState()+"|";
			
			orderItem = mapper.selectItemInfoForEFS(orderInfo.get(0).getNno());
			double totalVal = 0;
			for(int j = 0 ; j < orderItem.size(); j++) {
				if(j != 0) {
					EfsString +=",";//판매 쇼핑몰명
				}
				EfsString +="{";
				EfsString +="\"\",";//판매 쇼핑몰명
				EfsString +="\"\",";//장바구니 번호
				EfsString +="\"";
				EfsString +=orderInfo.get(0).getOrderNo();//주문번호
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getCusItemCode();//상품번호
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getItemDetail();//상품명
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getItemDiv();//상품종류
				EfsString +="\",";
				EfsString +="\"";
				EfsString +="";//상품 옵션1
				EfsString +="\",";
				EfsString +="\"";
				EfsString +="";//상품 옵션2(KR)
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getItemCnt();//상품수량
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getUnitCurrency();
				EfsString +="\",";
				EfsString +="\"";
				EfsString +=orderItem.get(j).getUnitValue();
				EfsString +="\"}";
				totalVal = totalVal + Double.parseDouble(orderItem.get(j).getUnitValue());
			}
			EfsString +="|";
			EfsString +=orderInfo.get(0).getUserWta()+"|";
			EfsString +=totalVal+"|";
			EfsString +=orderInfo.get(0).getDlvReqMsg();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return EfsString;
	}
	
	public String makeEfsPod(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<String> regNoList = new ArrayList<String>();
		String matchNum =  comnService.selectMatchNumByHawb(hawbNo);
		String outResult = new String();
		try {
			String requestURL = "http://www.efs.asia:200/api/in/";
			
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
			postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> testParam = new ArrayList<>();
			testParam.add(new BasicNameValuePair("apikey",("226ac50645ae82faf4d3afebe2589ef5")));
			testParam.add(new BasicNameValuePair("req_function",("getTrackStatusALL")));
			testParam.add(new BasicNameValuePair("send_data",matchNum));
			postRequest.setEntity(new UrlEncodedFormEntity(testParam, "UTF-8"));
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity, "UTF-8");
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				outResult = handler.handleResponse(response);
			} else {
				HttpEntity entity = response.getEntity();
				outResult = EntityUtils.toString(entity, "UTF-8");
			}
			
		}catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
			outResult="FAIL";
		}
		return outResult;
//		return "";
	}
	
	public String getEfsWeight() throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<String> regNoList = new ArrayList<String>();
		
		ArrayList<String> matchNum =  mapper.selectMatchNumAll();
		String outResult = new String();
		for(int i = 0 ; i < matchNum.size(); i++) {
			try {
				String requestURL = "http://www.efs.asia:200/api/in/";
				
				HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
				HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
				postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
				List<NameValuePair> testParam = new ArrayList<>();
				testParam.add(new BasicNameValuePair("apikey",("226ac50645ae82faf4d3afebe2589ef5")));
				testParam.add(new BasicNameValuePair("req_function",("getTrackStatus")));
				testParam.add(new BasicNameValuePair("send_data",matchNum.get(i)));
				postRequest.setEntity(new UrlEncodedFormEntity(testParam, "UTF-8"));
				HttpResponse response = client.execute(postRequest);
				
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					outResult = EntityUtils.toString(entity, "UTF-8");
					String[] temp = outResult.split(matchNum.get(i));
					String[] weightDatas = temp[1].split("[|]");
					String weightPart = weightDatas[7];
					if(weightPart != null && !weightPart.equals("")) {
						HashMap<String,String> parameters = new HashMap<String,String>();
						String[] wt = weightPart.split(",");
						String wta = wt[0];
						String wtc = wt[1];
						parameters.put("wta",wta);
						parameters.put("wtc",wtc);
						parameters.put("agencyBl",matchNum.get(i));
						mapper.insertEfsWeight(parameters);
					}
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				outResult = handler.handleResponse(response);
				} else {
					HttpEntity entity = response.getEntity();
					outResult = EntityUtils.toString(entity, "UTF-8");
				}
				
			}catch (Exception e) {
				// TODO: handle exception 
				e.printStackTrace();
				outResult="FAIL";
			}
		}
		return outResult;
//		return "";
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception{
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			String matchNum = comnService.selectMatchNumByHawb(hawbNo);
			String[] temp = rtnJson.split(matchNum);
			String[] podDatas = temp[1].split("[|]");
			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
			for(int i = podDatas.length-1; i > 2; i--) {
				podDetatil  = new LinkedHashMap<String,Object>();
				String podDataLine = podDatas[i].replaceAll("\"", "");
				podDataLine = podDataLine.replaceAll("\r\n", "");
				String[] podDetails = podDataLine.split(",");
				if(podDetails[0].equals("33")){
					podDetatil.put("UpdateCode","S500");
				}else {
					podDetatil.put("UpdateCode",podDetails[0]);
				}
				podDetatil.put("UpdateDateTime", podDetails[2]);
				podDetatil.put("UpdateLocation", podDetails[3]);
				podDetatil.put("UpdateDescription", podDetails[1]);
				podDetatil.put("ProblemCode",podDetails[0]); 
				podDetatil.put("Comments", podDetails[1]);
				podDetatailArray.add(podDetatil);
			}
			
			String RegistInDate = comnService.selectOrderDate(hawbNo);
			String housIndate = comnService.selectHawbDate(hawbNo);
			
			if(!housIndate.equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S011");
				podDetatil.put("UpdateDateTime", housIndate);
				podDetatil.put("UpdateLocation", "Korea");
				podDetatil.put("UpdateDescription", "Finished warehousing.");
				podDetatil.put("ProblemCode","S001"); 
				podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatailArray.add(podDetatil);
			}
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","S001");
			podDetatil.put("UpdateDateTime", RegistInDate);
			podDetatil.put("UpdateLocation", "Korea");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatil.put("ProblemCode","S001"); 
			podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
			podDetatailArray.add(podDetatil);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return podDetatailArray;
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


	public String fnMakeEfsJsonDel(String nno) throws Exception {
		// TODO Auto-generated method stub
		String jsonVal = makeEfsJsonDel(nno);
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
	
	public String makeEfsJsonDel(String nno) throws Exception {
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
	
	public String selectMatchNoByNNo(String nno) throws Exception {
		return comnService.selectMatchNumByHawb(mapper.selectHawbNoByNNO(nno));
	}
	
	public void selectBlApplyEFS(String orderNno, String userId, String userIp) throws Exception {
		ArrayList<BlApplyVO> blApply = new ArrayList<BlApplyVO>();
		BlApplyVO tmp = new BlApplyVO();
		try {
			ProcedureVO rtnVal = new ProcedureVO();
			tmp = comnService.selectBlApply(orderNno, userId, userIp);
			String rtnMsg = fnMakeEfsJson(orderNno);
			rtnVal = getCheckResult(rtnMsg, orderNno);
			
			if(rtnVal.getRstStatus().equals("FAIL")) {
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			comnService.insertTMPFromTB(orderNno, "Exception error!!",userId, userIp);
		}
	}
}
