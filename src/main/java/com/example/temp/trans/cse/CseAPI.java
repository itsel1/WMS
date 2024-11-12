
package com.example.temp.trans.cse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.ozon.OzonAPI;
import com.google.gson.JsonParser;

@RestController
public class CseAPI {

	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	CseMapper mapper;
	
	@Autowired
	OzonAPI ozonApi;
	
	ComnVO comnS3Info;
	
	private AmazonS3 amazonS3;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();

	public ApiShopifyResultVO createShipment(String nno) throws Exception {
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		CseOrderVO orderInfo = new CseOrderVO(); 
		orderInfo = mapper.selectCseShipmentInfo(nno);
		String jsonVal = makeCseXml(orderInfo); 
		String outResult = new String();
		JsonParser parse = new JsonParser();
		try {
			URL url = new URL("https://web.cse.ru/1c/ws/Web1C.1cws");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setUseCaches(true);
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");
			
			// 서버로 데이터 전송
			OutputStream outputStream = connection.getOutputStream();
			
			byte[] b = jsonVal.getBytes("UTF-8");
			
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			InputStream inputStream = connection.getInputStream();
			Document doc = dBuilder.parse(inputStream);
			doc.getDocumentElement().normalize();
			NodeList rtnNodeList = doc.getElementsByTagName("m:return");
			Node rtnNode = rtnNodeList.item(0);
			Element eElement = (Element) rtnNode;
//			
			String errChkMsg = getTagValue("m:Error", eElement);
			if(errChkMsg.equals("true")) {
				resultShopify.setStatus("ERROR");
			}else {
				NodeList itemeNodeList = eElement.getElementsByTagName("m:Items");
				Node itemNode = itemeNodeList.item(0);
				Element itemeElement = (Element) itemNode;
				String values = getTagValue("m:Value", itemeElement);
				resultShopify.setStatus("OK");
			}
//			Node testChk = node.item(0);
//			eElementErr.getChildNodes().item(1)
//			String responseData = "";
//			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));	
//			StringBuffer sb = new StringBuffer();	       
//			while ((responseData = br.readLine()) != null) {
//				sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
//			}
//				resultShopify.setHawbNo(element.getAsJsonObject().get("orderId").getAsString());
//				FileOutputStream fos = null;
//				InputStream is = null;
//				String ImageDir = realFilePath + "image/" + "aramex/";
//				String hawbNo = element.getAsJsonArray().get(0).getAsJsonObject().get("ShipmentNumber").getAsString();
//				resultShopify.setHawbNo(hawbNo);
//				fos = new FileOutputStream(ImageDir+ hawbNo+".PDF");
//				try {
//					URL url = new URL(element.getAsJsonArray().get(0).getAsJsonObject().get("LabelURL").getAsString());
//					URLConnection urlConnection = url.openConnection();
//					is = urlConnection.getInputStream();
//					byte[] buffer = new byte[1024];
//					int readBytes;
//					while((readBytes = is.read(buffer)) != -1) {
//						fos.write(buffer,0,readBytes);
//					}
//					fos.close();
//					is.close();
//					AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
//					amazonS3 = new AmazonS3Client(awsCredentials);
//					PutObjectResult asssd = new PutObjectResult();
//					File file = new File(ImageDir+ hawbNo+".PDF");
//					Calendar c = Calendar.getInstance();
//					String year = String.valueOf(c.get(Calendar.YEAR));
//			 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
//					if(amazonS3 != null) {
//						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getWUserId()+"_"+hawbNo, file);
//						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//						asssd = amazonS3.putObject(putObjectRequest);
//					}
//					amazonS3 = null;
//					file.delete();
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultShopify.setStatus("ERROR");
		}
		return resultShopify;
	}
	
	public String makeCseXml(CseOrderVO orderInfo) throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		String temp = "";
		temp += "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:car=\"http://www.cargo3.ru\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">";
		temp += "<soap:Header/>";
		temp += "<soap:Body>";
		temp += "<car:SaveWaybillOffice>";
		temp += "<car:Language/>";
		temp += "<car:Login>ACI EXPRESS CORP</car:Login>";
		temp += "<car:Password>zDdJTsnTzlpVLa</car:Password>";
		temp += "<car:Company/>";
		temp += "<car:Number/>";
		
		//order no / hawb no 로 추정
		temp += "<car:ClientNumber>"+orderInfo.getOrderNo()+"</car:ClientNumber>";
		//order no / hawb no 로 추정
		
		temp += "<car:OrderData>";
			temp += "<car:ClientContact/>";
			
			//수취인
			temp += "<car:Recipient>";
				//이름
				temp += "<car:Client>"+orderInfo.getCneeName()+"</car:Client>";
				temp += "<car:Official></car:Official>";
				//주소
				temp += "<car:Address>";
					//zipcode
					temp += "<car:Geography>postcode-"+orderInfo.getCneeZip()+"</car:Geography>";
					//나머지 주소
					temp += "<car:Info>"+orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail()+"</car:Info>";
					temp += "<car:Comment></car:Comment>";
					temp += "<car:FreeForm>true</car:FreeForm>";
				temp += "</car:Address>";
				//전화번호
				temp += "<car:Phone>"+orderInfo.getCneeHp()+"</car:Phone>";
				//email
				temp += "<car:EMail>"+orderInfo.getCneeEmail()+"</car:EMail>";
				temp += "<car:Urgency>18c4f207-458b-11dc-9497-0015170f8c09</car:Urgency>";
				temp += "<car:Cargo>";
					//상품명?
					temp += "<car:CargoDescription>"+orderInfo.getItemName()+", etc</car:CargoDescription>";
					//박스 개수
					temp += "<car:CargoPackageQty>"+orderInfo.getBoxCnt()+"</car:CargoPackageQty>";
					//무게
					temp += "<car:Weight>"+orderInfo.getUserWta()+"</car:Weight>";
					temp += "<car:VolumeWeight>"+orderInfo.getUserWtc()+"</car:VolumeWeight>";
					//총 가격
					temp += "<car:DeclaredValueRate>"+orderInfo.getTotalValue()+"</car:DeclaredValueRate>";
					//화폐 단위
					temp += "<car:DeclaredValueRateCurrency>ff3f7c38-4430-11dc-9497-0015170f8c09</car:DeclaredValueRateCurrency>";
					temp += "<car:PackageID></car:PackageID>";
					//box 정보
					temp += "<car:CargoPackages>";
						temp += "<car:Length>"+orderInfo.getUserLength()+"</car:Length>";
						temp += "<car:Width>"+orderInfo.getUserWidth()+"</car:Width>";
						temp += "<car:Height>"+orderInfo.getUserHeight()+"</car:Height>";
						temp += "<car:Weight>"+orderInfo.getUserWta()+"</car:Weight>";
						temp += "<car:VolumeWeight>"+orderInfo.getUserWtc()+"</car:VolumeWeight>";
						temp += "<car:PackageQty>"+orderInfo.getItemCnt()+"</car:PackageQty>";
						temp += "<car:PackageID></car:PackageID>";
					temp += "</car:CargoPackages>";
				temp += "</car:Cargo>";
			temp += "</car:Recipient>";
			//수취인
			
			//발송인
			temp += "<car:Sender>";
				//이름
				temp += "<car:Client>"+orderInfo.getShipperName()+"</car:Client>";
				temp += "<car:Address>";
					//zipcode
					temp += "<car:Geography>2f189a41-506c-11e9-9f13-001e67086478</car:Geography>";
					//나머지 주소
					temp += "<car:Info>"+orderInfo.getShipperAddr()+orderInfo.getShipperAddrDetail()+"</car:Info>";
					temp += "<car:FreeForm>true</car:FreeForm>";
				temp += "</car:Address>";
				temp += "<car:Phone>"+orderInfo.getShipperHp()+"</car:Phone>";
			temp += "</car:Sender>";
			//발송인
			
			//픽업 날짜 / error 대상
			temp += "<car:TakeDate>"+currentTime.toString()+"</car:TakeDate>";
			temp += "<car:TypeOfCargo>81dd8a13-8235-494f-84fd-9c04c51d50ec</car:TypeOfCargo>";
			temp += "<car:TypeOfPayer>0</car:TypeOfPayer>";
			temp += "<car:WayOfPayment>1</car:WayOfPayment>";
			temp += "<car:DeliveryOfCargo/>";
		temp += "</car:OrderData>";
		temp += "<car:Office/>";
		temp += "</car:SaveWaybillOffice>";
		temp += "</soap:Body>";
		temp += "</soap:Envelope>";
		return temp;
		//return "";
	}
	
	public String makePodCseXml(String hawbNo) throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		String temp = "";
		
		temp += "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://www.cargo3.ru\">";
		temp += "<SOAP-ENV:Body>";
		temp += "<ns1:Tracking>";
		temp += "<ns1:login>ACI EXPRESS CORP</ns1:login>";
		temp += "<ns1:password>zDdJTsnTzlpVLa</ns1:password>";
		temp += "<ns1:documents>";
		temp += "<ns1:Key>Documents</ns1:Key>";
		temp += "<ns1:Properties>";
		temp += "<ns1:Key>DocumentType</ns1:Key>";
		temp += "<ns1:Value>Waybill</ns1:Value>";
		temp += "<ns1:ValueType>string</ns1:ValueType>";
		temp += "</ns1:Properties>";
		temp += "<ns1:Properties>";
		temp += "<ns1:Key>OnlySelectedType</ns1:Key>";
		temp += "<ns1:Value>true</ns1:Value>";
		temp += "<ns1:ValueType>boolean</ns1:ValueType>";
		temp += "</ns1:Properties>";
		temp += "<ns1:List>";
		temp += "<ns1:Key>"+hawbNo+"</ns1:Key>";
		temp += "</ns1:List>";
		temp += "</ns1:documents>";
		temp += "<ns1:parameters>";
		temp += "<ns1:Key>Parameters</ns1:Key>";
		temp += "</ns1:parameters>";
		temp += "</ns1:Tracking>";
		temp += "</SOAP-ENV:Body>";
		temp += "</SOAP-ENV:Envelope>";
		
		return temp;
	}
	
	public static String getJsonStringFromMap( HashMap<String, Object> map ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        return jsonObject.toString();
    }
	
	public static String getJsonStringFromMapArray( ArrayList<LinkedHashMap<String, Object>>  map ) throws Exception {
		JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.get(0).entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }
	
	public ArrayList<HashMap<String, Object>> makePodDetatailArray (String hawbNo) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		HashMap<String, Object> aciPodInfo = new HashMap<String,Object>();
		aciPodInfo = mapper.selectAciPodInfo(hawbNo);
		String jsonVal = makePodCseXml(hawbNo); 
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		try {
			URL url = new URL("https://web.cse.ru/1c/ws/Web1C.1cws");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setUseCaches(true);
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");
			
			OutputStream outputStream = connection.getOutputStream();
			
			byte[] b = jsonVal.getBytes("UTF-8");
			
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			InputStream inputStream = connection.getInputStream();
			Document doc = dBuilder.parse(inputStream);
			doc.getDocumentElement().normalize();
			NodeList rtnNodeList = doc.getElementsByTagName("m:return");
			
			Node rtnNode = rtnNodeList.item(0);
			Element eElement = (Element) rtnNode;

			String errChkMsg = getTagValue("m:Error", eElement);
			if(errChkMsg.equals("true")) {
				resultShopify.setStatus("ERROR");
			}else {
				NodeList routeNodeList = doc.getElementsByTagName("m:List");
				for(int i =routeNodeList.getLength()-1; i>-1 ;i--) {
					NodeList childNodeList = routeNodeList.item(i).getChildNodes();
					HashMap<String,Object> outs = new HashMap<String,Object> ();
					String updateDatetime = "";
					String geography = "";
					for(int j = 0 ; j < childNodeList.getLength(); j++) {
						boolean guidChk = false;
						boolean datetChk = false;
						boolean geoChk = false;
						NodeList endNodeList = childNodeList.item(j).getChildNodes();
						
						for(int k = 0; k <endNodeList.getLength(); k++) {
							if(endNodeList.item(k).getNodeName().equals("m:Key")) {
								if(endNodeList.item(k).getTextContent().equals("GUID")) {
									guidChk = true;
								}else if(endNodeList.item(k).getTextContent().equals("DateTime")) {
									datetChk = true;
								}else if(endNodeList.item(k).getTextContent().equals("Geography")) {
									geoChk = true;
								}
							}else if(endNodeList.item(k).getNodeName().equals("m:Value")) {
								if(guidChk) {
									String temp = endNodeList.item(k).getTextContent();
									HashMap<String,Object> parameters = new HashMap<String,Object>();
									parameters.put("transCode", "CSE");
									parameters.put("reference", temp);
											
									outs = mapper.selectOzonTrack(parameters);
									
									guidChk = false;
								}else if(datetChk) {
									updateDatetime = endNodeList.item(k).getTextContent();
									datetChk = false;
								}else if(geoChk) {
									geography = endNodeList.item(k).getTextContent();
									geoChk = false;
								}
							}
						}
					}
					if(outs != null) {
						podDetatil  = new LinkedHashMap<String,Object>();
						if(outs.get("eventId").toString().equals("402")) {
							podDetatil.put("UpdateCode","S500");
							podDetatil.put("UpdateDateTime", updateDatetime);
							podDetatil.put("UpdateLocation", geography);
							podDetatil.put("UpdateDescription", outs.get("eventName").toString());
							podDetatil.put("ProblemCode",""); 
							podDetatil.put("Comments", outs.get("eventDescription").toString());
							podDetatailArray.add(podDetatil);
						}else if(!outs.get("eventId").toString().equals("101")&&!outs.get("eventId").toString().equals("102")) {
							podDetatil.put("UpdateCode",outs.get("eventId").toString());
							podDetatil.put("UpdateDateTime", updateDatetime);
							podDetatil.put("UpdateLocation", geography);
							podDetatil.put("UpdateDescription", outs.get("eventName").toString());
							podDetatil.put("ProblemCode",""); 
							podDetatil.put("Comments", outs.get("eventDescription").toString());
							podDetatailArray.add(podDetatil);
						}
					}
						
				}
				if(!aciPodInfo.get("hawbDate").equals("")) {
					podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode","S011");
					podDetatil.put("UpdateDateTime", aciPodInfo.get("hawbDate"));
					podDetatil.put("UpdateLocation", "Incheon,Seoul,KR");
					podDetatil.put("UpdateDescription", "Finished warehousing.");
					podDetatil.put("ProblemCode","S001"); 
					podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
					podDetatailArray.add(podDetatil);
				}
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S001");
				podDetatil.put("UpdateDateTime", aciPodInfo.get("orderDate"));
				podDetatil.put("UpdateLocation", "Korea");
				podDetatil.put("UpdateDescription", "Order information has been entered");
				podDetatil.put("ProblemCode","S001"); 
				podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatailArray.add(podDetatil);
				resultShopify.setStatus("OK");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultShopify.setStatus("ERROR");
		}
		
		return podDetatailArray;
	}
	
	private static String getTagValue(String tag, Element eElement) {
		if(eElement.getElementsByTagName(tag).item(0) == null) {
			return "";
		}
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return "";
	    return nValue.getNodeValue();
	}
	
	private static String getTagValueList(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    String rtn = "";
	    for(int i =0; i < nlList.getLength(); i++) {
	    	Node nValue = (Node) nlList.item(0);
		    if(nValue == null) 
		        return null;
		    else {
		    	rtn += nValue.getNodeValue()+",";
		    }
	    }
	    
	    return rtn;
	}
	
	public void setReferToEventInfo (HashMap<String,Object> targetTrackingInfo) throws Exception{
		String jsonVal = makePodCseXml(targetTrackingInfo.get("hawbNo").toString()); 
		URL url = new URL("https://web.cse.ru/1c/ws/Web1C.1cws");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setUseCaches(true);
		connection.setRequestProperty("Accept", "application/xml");
		connection.setRequestProperty("Content-Type", "application/xml");
		
		OutputStream outputStream = connection.getOutputStream();
		
		byte[] b = jsonVal.getBytes("UTF-8");
		
		outputStream.write(b);
		outputStream.flush();
		outputStream.close();
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList rtnNodeList = doc.getElementsByTagName("m:return");
		
		Node rtnNode = rtnNodeList.item(0);
		Element eElement = (Element) rtnNode;

		String errChkMsg = getTagValue("m:Error", eElement);
		if(errChkMsg.equals("true")) {
			
		}else {
			NodeList routeNodeList = doc.getElementsByTagName("m:List");
			for(int i =routeNodeList.getLength()-1; i>-1 ;i--) {
				NodeList childNodeList = routeNodeList.item(i).getChildNodes();
				HashMap<String,Object> outs = new HashMap<String,Object> ();
				String updateDatetime = "";
				String geography = "";
				String reference = "";
				for(int j = 0 ; j < childNodeList.getLength(); j++) {
					boolean guidChk = false;
					boolean datetChk = false;
					boolean geoChk = false;
					NodeList endNodeList = childNodeList.item(j).getChildNodes();
					
					for(int k = 0; k <endNodeList.getLength(); k++) {
						if(endNodeList.item(k).getNodeName().equals("m:Key")) {
							if(endNodeList.item(k).getTextContent().equals("GUID")) {
								guidChk = true;
							}else if(endNodeList.item(k).getTextContent().equals("DateTime")) {
								datetChk = true;
							}else if(endNodeList.item(k).getTextContent().equals("Geography")) {
								geoChk = true;
							}
						}else if(endNodeList.item(k).getNodeName().equals("m:Value")) {
							if(guidChk) {
								reference = endNodeList.item(k).getTextContent();
								HashMap<String,Object> parameters = new HashMap<String,Object>();
								parameters.put("transCode", "CSE");
								parameters.put("reference", reference);
										
								outs = mapper.selectOzonTrack(parameters);
								
								guidChk = false;
							}else if(datetChk) {
								updateDatetime = endNodeList.item(k).getTextContent();
								datetChk = false;
							}else if(geoChk) {
								geography = endNodeList.item(k).getTextContent();
								geoChk = false;
							}
						}
					}
				}
				if(outs != null) {
					try {
						String referenceNum = reference;
						HashMap<String,Object> parameters = new HashMap<String,Object>();
						parameters.put("reference", referenceNum);
						parameters.put("transCode", "CSE");
						parameters.put("hawbNo", targetTrackingInfo.get("hawbNo").toString());
						parameters.put("orderNo", targetTrackingInfo.get("orderNo").toString());
						parameters.put("time", updateDatetime);
						if(!ozonApi.insertOzonTrack(parameters)) {
							continue;
						}
					}catch (Exception e) {
						// TODO: handle exception
						continue;
					}	
				}
			}
		}
	}
}
