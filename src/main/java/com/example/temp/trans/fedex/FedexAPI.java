package com.example.temp.trans.fedex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.vo.ApiAdminVO;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.shipStation.ShipStationOrderVO;
import com.example.temp.trans.yongsung.ApiOrderItemYSVO;
import com.example.temp.trans.yongsung.ApiOrderYSVO;

import net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy;

@Service
public class FedexAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	FedexMapper mapper;
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	ComnService comnService;
	@Autowired
	MemberService memberService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	TrackingService_1_0Proxy trackingProxy = new TrackingService_1_0Proxy(); 

	net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy apiTrackingProxy = new net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy();
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	public ApiShopifyResultVO sendFedexApiGround(String orgStation, String nno, String insertType) throws Exception {
		// TODO Auto-generated method stub
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		Map<String, Object> bl = new HashMap<String, Object>();
		String chkType ="";
		ApiAdminVO targetApiInfo = mapper.selectApiAdminInfo(orgStation);
		HashMap<String,Object> tempParameter = new HashMap<String,Object> ();
	  	
	  	tempParameter.put("nno", nno);
	  	ApiOrderListVO tempApiOrderInfo = new ApiOrderListVO ();
	  	ArrayList<ApiOrderItemListVO> tempApiOrderItemInfo = new ArrayList<ApiOrderItemListVO>();
	  	
//	  	if(insertType.equals("API")) {
//	  		tempApiOrderInfo = mapper.selectFedexInfo(tempParameter);
//	  		tempApiOrderItemInfo = mapper.selectFedexItemInfo(tempParameter);
//	  	}else {
//	  		tempApiOrderInfo = mapper.selectFedexInfoTmp(tempParameter);
//	  		tempApiOrderItemInfo = mapper.selectFedexItemInfoTmp(tempParameter);
//	  	}
	  	
	  	tempApiOrderInfo = mapper.selectFedexInfo(tempParameter);
  		tempApiOrderItemInfo = mapper.selectFedexItemInfo(tempParameter);
	  	
	  	tempApiOrderInfo.setSymmetryKey(originKey.getSymmetryKey());//key 받아야 함
	  	tempApiOrderInfo.dncryptData();

	  	ApiOrderListVO tempShipperInfo= apiMapper.selectApiShipperInfo(tempApiOrderInfo.getUserId());

	  	String temp = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://fedex.com/ws/ship/v23\">";
	  	temp += "<SOAP-ENV:Body>";
	  	temp += "<ns1:ProcessShipmentRequest>";
	  	temp += "<ns1:WebAuthenticationDetail>";
	  	temp += "<ns1:ParentCredential>";
	  	temp += "<ns1:Key>"+targetApiInfo.getApiKey()+"</ns1:Key>"; //고정 값
	  	temp += "<ns1:Password>"+targetApiInfo.getApiPassword()+"</ns1:Password>"; //고정 값
	  	temp += "</ns1:ParentCredential>";
	  	temp += "<ns1:UserCredential>";
	  	temp += "<ns1:Key>"+targetApiInfo.getApiKey()+"</ns1:Key>"; //고정 값
	  	temp += "<ns1:Password>"+targetApiInfo.getApiPassword()+"</ns1:Password>"; //고정 값
	  	temp += "</ns1:UserCredential>";
	  	temp += "</ns1:WebAuthenticationDetail>";
	  	temp += "<ns1:ClientDetail>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";	//고정 값
	  	temp += "<ns1:MeterNumber>"+targetApiInfo.getApiMeterNumber()+"</ns1:MeterNumber>";		//고정 값
	  	temp += "</ns1:ClientDetail>";
	  	temp += "<ns1:TransactionDetail>";
	  	temp += "<ns1:CustomerTransactionId>*** Ground International Shipping Request using PHP ***</ns1:CustomerTransactionId>"; //고정 값
	  	temp += "</ns1:TransactionDetail>";	
	  	temp += "<ns1:Version>";
	  	temp += "<ns1:ServiceId>ship</ns1:ServiceId>";	//고정 값
	  	temp += "<ns1:Major>23</ns1:Major>";			//고정 값
	  	temp += "<ns1:Intermediate>0</ns1:Intermediate>";//고정 값
	  	temp += "<ns1:Minor>0</ns1:Minor>";				//고정 값
	  	temp += "</ns1:Version>";
	  	temp += "<ns1:RequestedShipment>";
	  	
	  	ZonedDateTime zdt = ZonedDateTime.now();
	  	OffsetDateTime odt = zdt.toOffsetDateTime();
	  	temp += "<ns1:ShipTimestamp>"+odt.withNano(0)+"</ns1:ShipTimestamp>";
	  	
	  	temp += "<ns1:DropoffType>REGULAR_PICKUP</ns1:DropoffType>"; //고정 값
	  	
  		temp += "<ns1:ServiceType>FEDEX_GROUND</ns1:ServiceType>";
	  	 
	  	temp += "<ns1:PackagingType>YOUR_PACKAGING</ns1:PackagingType>"; //고정 값
	  	temp += "<ns1:Shipper>";
	  	temp += "<ns1:Contact>";
	  	
	  	
	  	temp += "<ns1:PersonName>"+tempShipperInfo.getComEName()+"</ns1:PersonName>";
	  	temp += "<ns1:CompanyName>"+tempShipperInfo.getComEName()+"</ns1:CompanyName>";
	  	temp += "<ns1:PhoneNumber>"+tempShipperInfo.getShipperTel()+"</ns1:PhoneNumber>";
	  	temp += "</ns1:Contact>";
	  	temp += "<ns1:Address>";
	  	temp += "<ns1:StreetLines>14056 Artesia Blvd</ns1:StreetLines>";//고정
	  	temp += "<ns1:City>Cerritos</ns1:City>";//고정
	  	temp += "<ns1:StateOrProvinceCode>CA</ns1:StateOrProvinceCode>";//고정
	  	temp += "<ns1:PostalCode>90703</ns1:PostalCode>";//고정
	  	temp += "<ns1:CountryCode>US</ns1:CountryCode>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:Shipper>";
	  	temp += "<ns1:Recipient>";
	  	temp += "<ns1:Contact>";
	  	temp += "<ns1:PersonName>"+tempApiOrderInfo.getCneeName()+"</ns1:PersonName>";
	  	temp += "<ns1:CompanyName/>";
	  	temp += "<ns1:PhoneNumber>"+tempApiOrderInfo.getCneeTel()+"</ns1:PhoneNumber>";
	  	temp += "</ns1:Contact>";
	  	
	  	temp += "<ns1:Address>";
	  	String _addr = tempApiOrderInfo.getCneeAddr()+" "+tempApiOrderInfo.getCneeAddrDetail();
	  	if(_addr.length()>60) {
			resultShopify.setStatus("ERROR");
			resultShopify.setHawbNo("-");
			resultShopify.setErrorMsg("The combined length of address and addressDetail is too long.");
			return resultShopify;
	  	}
	  	if(_addr.length()>30) {
	  			temp += "<ns1:StreetLines>"+_addr.substring(0,30)+"</ns1:StreetLines>";
	  			_addr = _addr.substring(30,_addr.length());
	  	}
	  	temp += "<ns1:StreetLines>"+_addr.substring(0,_addr.length())+"</ns1:StreetLines>"; 
	  	temp += "<ns1:City>"+tempApiOrderInfo.getCneeCity()+"</ns1:City>";
	  	temp += "<ns1:StateOrProvinceCode>"+tempApiOrderInfo.getCneeState()+"</ns1:StateOrProvinceCode>";
	  	temp += "<ns1:PostalCode>"+tempApiOrderInfo.getCneeZip()+"</ns1:PostalCode>";
	  	temp += "<ns1:CountryCode>"+tempApiOrderInfo.getDstnStation()+"</ns1:CountryCode>";
	  	temp += "<ns1:Residential>false</ns1:Residential>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:Recipient>";
	  	
	  	//관세 통관 정보
	  	temp += "<ns1:ShippingChargesPayment>";
	  	temp += "<ns1:PaymentType>SENDER</ns1:PaymentType>";//고정
	  	temp += "<ns1:Payor>";
	  	temp += "<ns1:ResponsibleParty>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";//고정
	  	temp += "<ns1:Address>";
	  	temp += "<ns1:CountryCode>US</ns1:CountryCode>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:ResponsibleParty>";
	  	temp += "</ns1:Payor>";
	  	temp += "</ns1:ShippingChargesPayment>";
	  	
	  	
	  	temp += "<ns1:CustomsClearanceDetail>";
	  	temp += "<ns1:DutiesPayment>";
	  	temp += "<ns1:PaymentType>SENDER</ns1:PaymentType>";//고정
	  	temp += "<ns1:Payor>";
	  	temp += "<ns1:ResponsibleParty>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";//고정
	  	temp += "<ns1:Address>";
	  	temp += "<ns1:CountryCode>US</ns1:CountryCode>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:ResponsibleParty>";
	  	temp += "</ns1:Payor>";
	  	temp += "</ns1:DutiesPayment>";
	  	temp += "<ns1:DocumentContent>NON_DOCUMENTS</ns1:DocumentContent>";//고정
	  	temp += "<ns1:CustomsValue>";
	  	temp += "<ns1:Currency>USD</ns1:Currency>";//고정
	  	double totalValue = 0;
	  	int totalCnt = 0;
	  	for(int itemRoop = 0; itemRoop < tempApiOrderItemInfo.size(); itemRoop++) {
	  		totalValue += Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getUnitValue())*Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getItemCnt());
	  		totalCnt += Integer.parseInt(tempApiOrderItemInfo.get(itemRoop).getItemCnt());
	  	}
	  	 
	  	temp += "<ns1:Amount>"+totalValue+"</ns1:Amount>";//totalvalue 총합 (단가 * 아이템개수)+각 아이템별로
	  	temp += "</ns1:CustomsValue>";
	  	
	  	double totalWeight = Double.parseDouble(tempApiOrderInfo.getUserWta());
	  	for(int itemRoop = 0; itemRoop < tempApiOrderItemInfo.size(); itemRoop++) {
	  		double tempWeight=0;
	  		temp += "<ns1:Commodities>";
		  		temp += "<ns1:NumberOfPieces>1</ns1:NumberOfPieces>";//고정
			  	temp += "<ns1:Description>"+tempApiOrderItemInfo.get(itemRoop).getItemDetail()+"</ns1:Description>";
			  	temp += "<ns1:CountryOfManufacture>US</ns1:CountryOfManufacture>";//고정
			  	temp += "<ns1:Weight>";
		  		if(tempApiOrderInfo.getWtUnit().toUpperCase().equals("KG")) {
		  			tempWeight += Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getUserWta())*2.2046;
		  		}
		  		
		  		temp += "<ns1:Units>"+"LB"+"</ns1:Units>";
		  		temp += "<ns1:Value>"+tempWeight/totalCnt+"</ns1:Value>";
			  	temp += "</ns1:Weight>";
			  	temp += "<ns1:Quantity>"+tempApiOrderItemInfo.get(itemRoop).getItemCnt()+"</ns1:Quantity>";
			  	temp += "<ns1:QuantityUnits>"+tempApiOrderItemInfo.get(itemRoop).getQtyUnit()+"</ns1:QuantityUnits>";
			  	temp += "<ns1:UnitPrice>";
				  	temp += "<ns1:Currency>USD</ns1:Currency>";//고정
				  	temp += "<ns1:Amount>"+Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getUnitValue())+"</ns1:Amount>";
			  	temp += "</ns1:UnitPrice>";
			  	temp += "<ns1:CustomsValue>";
				  	temp += "<ns1:Currency>USD</ns1:Currency>";//고정
				  	temp += "<ns1:Amount>"+Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getUnitValue())*Double.parseDouble(tempApiOrderItemInfo.get(itemRoop).getItemCnt())+"</ns1:Amount>";
			  	temp += "</ns1:CustomsValue>";
		  	temp += "</ns1:Commodities>";
	  	}
	  	
	  	temp += "</ns1:CustomsClearanceDetail>";
	  	temp += "<ns1:LabelSpecification>";
	  	temp += "<ns1:LabelFormatType>COMMON2D</ns1:LabelFormatType>";//고정
	  	temp += "<ns1:ImageType>PDF</ns1:ImageType>";//고정
	  	//temp += "<ns1:LabelStockType>PAPER_7X4.75</ns1:LabelStockType>";//고정
	  	temp += "<ns1:LabelStockType>STOCK_4X6</ns1:LabelStockType>";//고정
	  	temp += "</ns1:LabelSpecification>";
	  	temp += "<ns1:PackageCount>1</ns1:PackageCount>";//고정
	  	temp += "<ns1:RequestedPackageLineItems>";
	  	temp += "<ns1:SequenceNumber>1</ns1:SequenceNumber>";//고정
	  	temp += "<ns1:GroupPackageCount>1</ns1:GroupPackageCount>";//고정
	  	temp += "<ns1:Weight>";
	  	temp += "<ns1:Units>"+"LB"+"</ns1:Units>";
	  	temp += "<ns1:Value>"+totalWeight+"</ns1:Value>";
	  	temp += "</ns1:Weight>";
	  	temp += "<ns1:Dimensions>";
	  	String lengthStr = "";
	  	String widthStr = "";
	  	String heightStr = "";
	  	
	  	int length=0;
	  	int width=0;
	  	int height=0;
	  	if(tempApiOrderInfo.getDimUnit().toUpperCase().equals("CM")){
	  		lengthStr = Double.toString(Double.parseDouble(tempApiOrderInfo.getUserLength())/2.54);
	  		widthStr = Double.toString(Double.parseDouble(tempApiOrderInfo.getUserWidth())/2.54);
	  		heightStr = Double.toString(Double.parseDouble(tempApiOrderInfo.getUserHeight())/2.54);
	  		
	  		if(lengthStr.equals("0.0"))
	  			length = 5;
	  		else
	  			length = Integer.parseInt(lengthStr.split("[.]")[0].toString());
	  		
	  		if(widthStr.equals("0.0"))
	  			width = 5;
	  		else
	  			width = Integer.parseInt(widthStr.split("[.]")[0].toString());
	  		
	  		if(heightStr.equals("0.0"))
	  			height = 5;
	  		else
	  			height = Integer.parseInt(heightStr.split("[.]")[0].toString());
	  		
	  		
	  	}else {
	  		if(lengthStr.equals("0.0"))
	  			length = 5;
	  		else
	  			length = Integer.parseInt(tempApiOrderInfo.getUserLength().split("[.]")[0].toString());
	  		
	  		if(widthStr.equals("0.0"))
	  			width = 5;
	  		else
	  			width = Integer.parseInt(tempApiOrderInfo.getUserWidth().split("[.]")[0].toString());
	  		
	  		if(heightStr.equals("0.0"))
	  			height = 5;
	  		else
	  			height = Integer.parseInt(tempApiOrderInfo.getUserHeight().split("[.]")[0].toString());
	  	}
	  	temp += "<ns1:Length>"+length+"</ns1:Length>";
	  	temp += "<ns1:Width>"+width+"</ns1:Width>";
	  	temp += "<ns1:Height>"+height+"</ns1:Height>";
//	  	temp += "<ns1:Units>"+tempApiOrderInfo.getDimUnit()+"</ns1:Units>";
	  	temp += "<ns1:Units>IN</ns1:Units>";
	  	temp += "</ns1:Dimensions>";
	  	temp += "<ns1:CustomerReferences>";
	  	temp += "<ns1:CustomerReferenceType>CUSTOMER_REFERENCE</ns1:CustomerReferenceType>";//고정
	  	temp += "<ns1:Value>"+tempApiOrderInfo.getOrderNo()+"</ns1:Value>";//고정
	  	temp += "</ns1:CustomerReferences>";
	  	temp += "</ns1:RequestedPackageLineItems>";
	  	temp += "</ns1:RequestedShipment>";
	  	temp += "</ns1:ProcessShipmentRequest>";
	  	temp += "</SOAP-ENV:Body>";
	  	temp += "</SOAP-ENV:Envelope>";
	  	
	  	URL url = new URL("https://ws.fedex.com:443/web-services");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setUseCaches(true);
		connection.setRequestMethod("POST");

		connection.setRequestProperty("Accept", "text/xml");
		connection.setRequestProperty("Content-Type", "text/xml");
		

		OutputStream outputStream = connection.getOutputStream();
		
		byte[] b = temp.getBytes("UTF-8");
		
		outputStream.write(b);
		outputStream.flush();
		outputStream.close();
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("ProcessShipmentReply");
		
		//NodeList errNode = doc.getElementsByTagName("HighestSeverity");
		Node errChk = errNode.item(0);
		
		Element eElementErr = (Element) errChk;
		
		String errChkMsg = getTagValue("HighestSeverity", eElementErr);
		
		if(errChkMsg.equals("ERROR")) {
			NodeList HighestNode = eElementErr.getChildNodes();
			int nodeListLength = HighestNode.getLength();
			for(int nodeListRoop = 0 ; nodeListRoop < nodeListLength ; nodeListRoop++) {
				if(HighestNode.item(nodeListRoop).getNodeName().equals("Notifications")) {
					NodeList NotifiNode = eElementErr.getChildNodes().item(nodeListRoop).getChildNodes();
					int nodeLength = NotifiNode.getLength();
					for(int nodeRoop = 0; nodeRoop < nodeLength; nodeRoop++) {
						if(NotifiNode.item(nodeRoop).getNodeName().equals("Code")) {
							resultShopify.setStatusList(resultShopify.getStatusList()+NotifiNode.item(nodeRoop).getChildNodes().item(0).getNodeValue()+",");
						}
					}
				}
			}
			
			//에러처리 혹은 에러메시지 전부 담거나 출력 할 방법 생각.. 
			resultShopify.setStatus("ERROR");
			resultShopify.setHawbNo("-");
			resultShopify.setErrorMsg(errChkMsg);
			return resultShopify;
		}
		
		NodeList trkNode = doc.getElementsByTagName("MasterTrackingId");
		//NodeList errNode = doc.getElementsByTagName("HighestSeverity");
		Node trkInfoList = errNode.item(0);
		
		Element trkInfos = (Element) trkInfoList;
		
		String hawbNo = getTagValue("TrackingNumber", trkInfos);
		
		NodeList nList = doc.getElementsByTagName("Parts");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String base64Pdf = getTagValue("Image", eElement);
		inputStream.close();
		
		String ImageDir = realFilePath + "image/" + "aramex/";
		File file = new File(ImageDir+hawbNo+".PDF");

		try {
			// To be short I use a corrupted PDF string, so make sure to use a valid one if
			// you want to preview the PDF file
			
			FileOutputStream fos = new FileOutputStream(file);
			byte[] decoder = Base64.getDecoder().decode(base64Pdf);

			fos.write(decoder);
			fos.close();
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, tempApiOrderInfo.getUserId()+"_"+hawbNo, file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		file.delete();
		
//	    InputStream inputStream = connection.getInputStream();
//		byte[] res = new byte[2048];
//		int i = 0;
//		StringBuilder response2 = new StringBuilder();
//		while ((i = inputStream.read(res)) != -1) {
//			response2.append(new String(res, 0, i));
//		}
		resultShopify.setStatus("OK");
		resultShopify.setHawbNo(hawbNo);
		return resultShopify;
		
	}
	
	public ApiShopifyResultVO sendFedexApiSmart(String orgStation, String nno, String insertType) throws Exception {
		// TODO Auto-generated method stub
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		Map<String, Object> bl = new HashMap<String, Object>();
		//String nno = "202010261702559659DII";//nno 받아야 함
		String chkType ="";
		ApiAdminVO targetApiInfo = mapper.selectApiAdminInfo(orgStation);//orgstation
		//ApiAdminVO targetApiInfo = mapper.selectApiAdminInfo("082");
		HashMap<String,Object> tempParameter = new HashMap<String,Object> ();
	  	
	  	tempParameter.put("nno", nno);
	  	
	  	
	  	ApiOrderListVO tempApiOrderInfo = new ApiOrderListVO();
	  	ArrayList<ApiOrderItemListVO> tempApiOrderItemInfo = new ArrayList<ApiOrderItemListVO>();
	  	
//	  	if(insertType.equals("API")) {
//	  		tempApiOrderInfo = mapper.selectFedexInfo(tempParameter);
//	  		tempApiOrderItemInfo = mapper.selectFedexItemInfo(tempParameter);
//	  	}else {
//	  		tempApiOrderInfo = mapper.selectFedexInfoTmp(tempParameter);
//	  		tempApiOrderItemInfo = mapper.selectFedexItemInfoTmp(tempParameter);
//	  	}
	  	
	  	tempApiOrderInfo = mapper.selectFedexInfo(tempParameter);
  		tempApiOrderItemInfo = mapper.selectFedexItemInfo(tempParameter);
	  	
	  	tempApiOrderInfo.setSymmetryKey(originKey.getSymmetryKey());//key 받아야 함
	  	tempApiOrderInfo.dncryptData();
	  	
	  	
	  	ApiOrderListVO tempShipperInfo= apiMapper.selectApiShipperInfo(tempApiOrderInfo.getUserId());
	  	tempShipperInfo.setSymmetryKey(originKey.getSymmetryKey());//key 받아야 함
	  	tempShipperInfo.dncryptData();
	  	double weight = 0;
  		if(tempApiOrderInfo.getWtUnit().toLowerCase().equals("kg")) {
  			weight = Double.parseDouble(tempApiOrderInfo.getUserWta())*2.2046; // LB로 변환
  			if(weight>0.94 && weight <1)
  				weight = 1;
  		}else {
  			weight = Double.parseDouble(tempApiOrderInfo.getUserWta()); 
  		}
  		
  		String cneeTel = tempApiOrderInfo.getCneeTel();
  		if (cneeTel.equals("")) {
  			cneeTel = tempApiOrderInfo.getCneeHp();
  		}
  			
	  	String temp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://fedex.com/ws/ship/v22\">";
	  	temp += "<soapenv:Header/>";
	  	temp += "<soapenv:Body>";
	  	temp += "<ns1:ProcessShipmentRequest>";
	  	temp += "<ns1:WebAuthenticationDetail>";
	  	temp += "<ns1:ParentCredential>";
	  	temp += "<ns1:Key>"+targetApiInfo.getApiKey()+"</ns1:Key>"; //고정 값
	  	temp += "<ns1:Password>"+targetApiInfo.getApiPassword()+"</ns1:Password>"; //고정 값
	  	temp += "</ns1:ParentCredential>";
	  	temp += "<ns1:UserCredential>";
	  	temp += "<ns1:Key>"+targetApiInfo.getApiKey()+"</ns1:Key>"; //고정 값
	  	temp += "<ns1:Password>"+targetApiInfo.getApiPassword()+"</ns1:Password>"; //고정 값
	  	temp += "</ns1:UserCredential>";
	  	temp += "</ns1:WebAuthenticationDetail>";
	  	temp += "<ns1:ClientDetail>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";	//고정 값
	  	temp += "<ns1:MeterNumber>"+targetApiInfo.getApiMeterNumber()+"</ns1:MeterNumber>";		//고정 값
	  	temp += "</ns1:ClientDetail>";
	  	temp += "<ns1:TransactionDetail>";
	  	temp += "<ns1:CustomerTransactionId>*** Ground International Shipping Request using PHP ***</ns1:CustomerTransactionId>"; //고정 값
	  	temp += "</ns1:TransactionDetail>";	
	  	temp += "<ns1:Version>";
	  	temp += "<ns1:ServiceId>ship</ns1:ServiceId>";	//고정 값
	  	temp += "<ns1:Major>22</ns1:Major>";			//고정 값
	  	temp += "<ns1:Intermediate>0</ns1:Intermediate>";//고정 값
	  	temp += "<ns1:Minor>0</ns1:Minor>";				//고정 값
	  	temp += "</ns1:Version>";
	  	temp += "<ns1:RequestedShipment>";
	  	
	  	ZonedDateTime zdt = ZonedDateTime.now();
	  	OffsetDateTime odt = zdt.toOffsetDateTime();
	  	temp += "<ns1:ShipTimestamp>"+odt.withNano(0)+"</ns1:ShipTimestamp>";
	  	
	  	temp += "<ns1:DropoffType>REGULAR_PICKUP</ns1:DropoffType>"; //고정 값
	  	
  		temp += "<ns1:ServiceType>SMART_POST</ns1:ServiceType>"; // SMART일 경우 SMART
  		
	  	temp += "<ns1:PackagingType>YOUR_PACKAGING</ns1:PackagingType>"; //고정 값
	  	
	  	temp += "<ns1:TotalWeight>";
  		temp += "<ns1:Units>"+"LB"+"</ns1:Units>";
  		
  		temp += "<ns1:Value>"+weight+"</ns1:Value>";
  		temp += "</ns1:TotalWeight>";
  		
  		
	  	temp += "<ns1:Shipper>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";
	  	temp += "<ns1:Contact>";
	  	
	  	//temp += "<ns1:PersonName>"+tempShipperInfo.getShipperName()+"</ns1:PersonName>";
	  	temp += "<ns1:CompanyName>"+tempShipperInfo.getComEName()+"</ns1:CompanyName>";
	  	temp += "<ns1:PhoneNumber>"+tempShipperInfo.getShipperTel()+"</ns1:PhoneNumber>";
	  	temp += "</ns1:Contact>";
	  	temp += "<ns1:Address>";
	  	temp += "<ns1:StreetLines>14056 Artesia Blvd</ns1:StreetLines>";//고정
	  	temp += "<ns1:City>Cerritos</ns1:City>";//고정
	  	temp += "<ns1:StateOrProvinceCode>CA</ns1:StateOrProvinceCode>";//고정
	  	temp += "<ns1:PostalCode>90703</ns1:PostalCode>";//고정
	  	temp += "<ns1:CountryCode>US</ns1:CountryCode>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:Shipper>";
	  	
	  	temp += "<ns1:Recipient>";
	  	temp += "<ns1:Contact>";
	  	temp += "<ns1:PersonName>"+tempApiOrderInfo.getCneeName()+"</ns1:PersonName>";
	  	temp += "<ns1:CompanyName>"+""+"</ns1:CompanyName>";
	  	temp += "<ns1:PhoneNumber>"+cneeTel+"</ns1:PhoneNumber>";
	  	temp += "</ns1:Contact>";
	  	temp += "<ns1:Address>";
	  	String _addr = tempApiOrderInfo.getCneeAddr()+" "+tempApiOrderInfo.getCneeAddrDetail();
	  	if(_addr.length()>60) {
			resultShopify.setStatus("ERROR");
			resultShopify.setHawbNo("-");
			resultShopify.setErrorMsg("The combined length of address and addressDetail is too long.");
			return resultShopify;
	  	}
	  	if(_addr.length()>30) {
	  			temp += "<ns1:StreetLines>"+_addr.substring(0,30)+"</ns1:StreetLines>";
	  			_addr = _addr.substring(30,_addr.length());
	  	}
	  	temp += "<ns1:StreetLines>"+_addr.substring(0,_addr.length())+"</ns1:StreetLines>";
	  	//temp += "<ns1:StreetLines>"+tempApiOrderInfo.getCneeAddr()+" "+tempApiOrderInfo.getCneeAddrDetail()+"</ns1:StreetLines>";
	  	temp += "<ns1:City>"+tempApiOrderInfo.getCneeCity()+"</ns1:City>";
	  	temp += "<ns1:StateOrProvinceCode>"+tempApiOrderInfo.getCneeState()+"</ns1:StateOrProvinceCode>";
	  	temp += "<ns1:PostalCode>"+tempApiOrderInfo.getCneeZip()+"</ns1:PostalCode>";
	  	temp += "<ns1:CountryCode>"+tempApiOrderInfo.getDstnNation()+"</ns1:CountryCode>";
	  	temp += "<ns1:Residential>false</ns1:Residential>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:Recipient>";
	  	
	  	
	  	//관세 통관 정보
	  	temp += "<ns1:ShippingChargesPayment>";
	  	temp += "<ns1:PaymentType>SENDER</ns1:PaymentType>";//고정
	  	temp += "<ns1:Payor>";
	  	temp += "<ns1:ResponsibleParty>";
	  	temp += "<ns1:AccountNumber>"+targetApiInfo.getApiAccountNumber()+"</ns1:AccountNumber>";//고정
	  	
	  	
	  	temp += "<ns1:Contact>";
	  	temp += "<ns1:PersonName>"+""+"</ns1:PersonName>";
	  	temp += "<ns1:CompanyName>"+""+"</ns1:CompanyName>";
	  	temp += "<ns1:PhoneNumber>"+""+"</ns1:PhoneNumber>";
	  	temp += "</ns1:Contact>";
	  	
	  	
	  	temp += "<ns1:Address>";
	  	temp += "<ns1:CountryCode>US</ns1:CountryCode>";//고정
	  	temp += "</ns1:Address>";
	  	temp += "</ns1:ResponsibleParty>";
	  	temp += "</ns1:Payor>";
	  	temp += "</ns1:ShippingChargesPayment>";
	  	
	  	temp += "<ns1:SmartPostDetail>";
	  	String indicia = targetApiInfo.getIndicia();
	  	if(weight > 0.94) {
	  		indicia = "PARCEL_SELECT";
	  	}
	  	temp +="<ns1:Indicia>"+indicia+"</ns1:Indicia>";//값 수정 필요
	  	temp +="<ns1:AncillaryEndorsement>"+targetApiInfo.getAncillary()+"</ns1:AncillaryEndorsement>";//값 수정 필요
	  	temp +="<ns1:HubId>"+targetApiInfo.getHubId()+"</ns1:HubId>";//값 수정 필요
	  	temp +="</ns1:SmartPostDetail>";
	  	
	  	temp += "<ns1:LabelSpecification>";
	  	temp += "<ns1:LabelFormatType>COMMON2D</ns1:LabelFormatType>";//고정
	  	temp += "<ns1:ImageType>PDF</ns1:ImageType>";//고정
//	  	temp += "<ns1:LabelStockType>PAPER_7X4.75</ns1:LabelStockType>";//고정
	  	temp += "<ns1:LabelStockType>STOCK_4X6</ns1:LabelStockType>";//고정
	  	temp += "</ns1:LabelSpecification>";
	  	temp += "<ns1:PackageCount>1</ns1:PackageCount>";//고정
	  	temp += "<ns1:RequestedPackageLineItems>";
	  	temp += "<ns1:SequenceNumber>1</ns1:SequenceNumber>";//고정
	  	temp += "<ns1:GroupPackageCount>1</ns1:GroupPackageCount>";//고정
	  	temp += "<ns1:Weight>";
//	  	temp += "<ns1:Units>"+tempApiOrderInfo.getWtUnit()+"</ns1:Units>";
//	  	temp += "<ns1:Value>"+tempApiOrderInfo.getUserWta()+"</ns1:Value>";
	  	temp += "<ns1:Units>"+"LB"+"</ns1:Units>";
	  	temp += "<ns1:Value>"+weight+"</ns1:Value>";
	  	temp += "</ns1:Weight>";
	  	temp += "<ns1:CustomerReferences>";
	  	temp += "<ns1:CustomerReferenceType>CUSTOMER_REFERENCE</ns1:CustomerReferenceType>";//고정
	  	temp += "<ns1:Value>"+tempApiOrderInfo.getOrderNo()+"</ns1:Value>";//고정
	  	temp += "</ns1:CustomerReferences>";
	  	temp += "</ns1:RequestedPackageLineItems>";
	  	temp += "</ns1:RequestedShipment>";
	  	temp += "</ns1:ProcessShipmentRequest>";
	  	temp += "</soapenv:Body>";
	  	temp += "</soapenv:Envelope>";
	  	
	  	System.out.println(temp);
	  	
	  	URL url = new URL("https://ws.fedex.com:443/web-services");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		
		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setUseCaches(true);
		connection.setRequestMethod("POST");

		connection.setRequestProperty("Accept", "text/xml");
		connection.setRequestProperty("Content-Type", "text/xml");
		

		OutputStream outputStream = connection.getOutputStream();
		
		byte[] b = temp.getBytes("UTF-8");
		
		outputStream.write(b);
		outputStream.flush();
		outputStream.close();
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("ProcessShipmentReply");
		//NodeList errNode = doc.getElementsByTagName("HighestSeverity");
		Node errChk = errNode.item(0);
		
		Element eElementErr = (Element) errChk;
		String errChkMsg = getTagValue("HighestSeverity", eElementErr);
		
		if(errChkMsg.equals("ERROR")) {
			NodeList HighestNode = eElementErr.getChildNodes();
			int nodeListLength = HighestNode.getLength();
			for(int nodeListRoop = 0 ; nodeListRoop < nodeListLength ; nodeListRoop++) {
				if(HighestNode.item(nodeListRoop).getNodeName().equals("Notifications")) {
					NodeList NotifiNode = eElementErr.getChildNodes().item(nodeListRoop).getChildNodes();
					int nodeLength = NotifiNode.getLength();
					for(int nodeRoop = 0; nodeRoop < nodeLength; nodeRoop++) {
						if(NotifiNode.item(nodeRoop).getNodeName().equals("Code")) {
							resultShopify.setStatusList(resultShopify.getStatusList()+NotifiNode.item(nodeRoop).getChildNodes().item(0).getNodeValue()+",");
						}
					}
				}
			}
			
			//에러처리 혹은 에러메시지 전부 담거나 출력 할 방법 생각.. 
			resultShopify.setStatus("ERROR");
			resultShopify.setHawbNo("-");
			resultShopify.setErrorMsg(errChkMsg);
			return resultShopify;
		}
		
		NodeList trkNode = doc.getElementsByTagName("MasterTrackingId");
		//NodeList errNode = doc.getElementsByTagName("HighestSeverity");
		Node trkInfoList = errNode.item(0);
		
		Element trkInfos = (Element) trkInfoList;
		
		String rateZone = getTagValue("RateZone",trkInfos);
		tempApiOrderInfo.setRateZone(rateZone);
		
		mapper.insertZoneInfo(tempApiOrderInfo);
		
		
		//String hawbNo = getTagValue("UspsApplicationId", trkInfos)+getTagValue("TrackingNumber", trkInfos);
		String hawbNo = trkInfos.getElementsByTagName("StringBarcodes").item(0).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
		
		
		
		NodeList nList = doc.getElementsByTagName("Parts");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String base64Pdf = getTagValue("Image", eElement);
		
		inputStream.close();
		String ImageDir = realFilePath + "image/" + "aramex/";
		File file = new File(ImageDir+hawbNo+".PDF");

		try {
			// To be short I use a corrupted PDF string, so make sure to use a valid one if
			// you want to preview the PDF file
			
			FileOutputStream fos = new FileOutputStream(file);
			byte[] decoder = Base64.getDecoder().decode(base64Pdf);

			fos.write(decoder);
			fos.close();
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, tempApiOrderInfo.getUserId()+"_"+hawbNo, file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//file.delete();
		
//	    InputStream inputStream = connection.getInputStream();
//		byte[] res = new byte[2048];
//		int i = 0;
//		StringBuilder response2 = new StringBuilder();
//		while ((i = inputStream.read(res)) != -1) {
//			response2.append(new String(res, 0, i));
//		}
		resultShopify.setStatus("OK");
		resultShopify.setHawbNo(hawbNo);
		return resultShopify;
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

	public void deleteOrderListByNno(String newNno) throws Exception {
		// TODO Auto-generated method stub
		comnService.deleteOrderListByNno(newNno);
	}
	
	public ArrayList<String> fnCutString(String fullAddress) {
		String[] cutString = fullAddress.split(" ");
		ArrayList<String> rtnAddress = new ArrayList<String>();
		String _addString = "";
		for(int index = 0; index < cutString.length; index++) {
			if((_addString+" "+cutString[index]).length()<31) {
				if(index == 0) {
					_addString = cutString[index];
				}else {
					_addString = _addString+" "+cutString[index];
				}
				
				
			}else {
					rtnAddress.add(_addString);
					_addString = cutString[index];
			}
			
			
		}
		return rtnAddress;
	}
	
	public void getTrackingFedex() throws Exception {
		
		ArrayList<ApiOrderFedexVO> trackingNum = new ArrayList<ApiOrderFedexVO>();
		
		trackingNum = mapper.selectFedexSmartHawbNo();
		
		
		for(int i = 0; i < trackingNum.size();i++) {
//		for(int i = 0; i < 1;i++) {
			String temp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v16=\"http://fedex.com/ws/track/v16\">";
			temp+="<soapenv:Header/>";
			temp+="<soapenv:Body>";
			temp+="<v16:TrackRequest>";
			temp+="<v16:WebAuthenticationDetail>";
			temp+="<v16:ParentCredential>";
			temp+="<v16:Key>DxkeCh97Bh8r06cp</v16:Key>";
			temp+="<v16:Password>LfWMXjwXiCSZb9e57tXenRYdN</v16:Password>";
			temp+="</v16:ParentCredential>";
			temp+="<v16:UserCredential>";
			temp+="<v16:Key>DxkeCh97Bh8r06cp</v16:Key>";
			temp+="<v16:Password>LfWMXjwXiCSZb9e57tXenRYdN</v16:Password>";
			temp+="</v16:UserCredential>";
			temp+="</v16:WebAuthenticationDetail>";
			temp+="<v16:ClientDetail>";
			temp+="<v16:AccountNumber>187513588</v16:AccountNumber>";
			temp+="<v16:MeterNumber>252498009</v16:MeterNumber>";
			temp+="</v16:ClientDetail>";
			temp+="<v16:TransactionDetail>";
			temp+="<v16:CustomerTransactionId>TrackByNumber_v16</v16:CustomerTransactionId>";
			temp+="<v16:Localization>";
			temp+="<v16:LanguageCode>EN</v16:LanguageCode>";
			temp+="<v16:LocaleCode>US</v16:LocaleCode>";
			temp+="</v16:Localization>";
			temp+="</v16:TransactionDetail>";
			temp+="<v16:Version>";
			temp+="<v16:ServiceId>trck</v16:ServiceId>";
			temp+="<v16:Major>16</v16:Major>";
			temp+="<v16:Intermediate>0</v16:Intermediate>";
			temp+="<v16:Minor>0</v16:Minor>";
			temp+="</v16:Version>";
			temp+="<v16:SelectionDetails>";
			if(trackingNum.get(i).getTransCode().equals("FED")) {
				double weight = Double.parseDouble(trackingNum.get(i).getUserWta())*2.2046;
				if(weight > 9) {
					temp+="<v16:CarrierCode>FDXG</v16:CarrierCode>";
				}else {
					temp+="<v16:CarrierCode>FXSP</v16:CarrierCode>";
				}
			}else if(trackingNum.get(i).getTransCode().equals("FES")) {
				temp+="<v16:CarrierCode>FXSP</v16:CarrierCode>";
			}else if(trackingNum.get(i).getTransCode().equals("FEG")){
				temp+="<v16:CarrierCode>FDXG</v16:CarrierCode>";
			}
			
			temp+="<v16:PackageIdentifier>";
			temp+="<v16:Type>TRACKING_NUMBER_OR_DOORTAG</v16:Type>";
			temp+="<v16:Value>"+trackingNum.get(i).getHawbNo()+"</v16:Value>";
			temp+="</v16:PackageIdentifier>";
			temp+="<v16:ShipmentAccountNumber/>";
			temp+="<v16:SecureSpodAccount/>";
			temp+="<v16:Destination>";
			temp+="<v16:GeographicCoordinates>ratesevertitqueaequora</v16:GeographicCoordinates>";
			temp+="</v16:Destination>";
			temp+="</v16:SelectionDetails>";
			temp+="</v16:TrackRequest>";
			temp+="</soapenv:Body>";
			temp+="</soapenv:Envelope>";
			URL url;
			try {
				url = new URL("https://ws.fedex.com:443/web-services");
			
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(20000);
		
				connection.setDoOutput(true);
		
				connection.setUseCaches(true);
				connection.setRequestMethod("POST");
		
				connection.setRequestProperty("Accept", "text/xml");
				connection.setRequestProperty("Content-Type", "text/xml");
				
		
				OutputStream outputStream = connection.getOutputStream();
				
				byte[] b = temp.getBytes("UTF-8");
				
				outputStream.write(b);
				outputStream.flush();
				outputStream.close();
				
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				InputStream inputStream = connection.getInputStream();
				Document doc = dBuilder.parse(inputStream);
				doc.getDocumentElement().normalize();
				NodeList errNode = doc.getElementsByTagName("TrackDetails");
				//NodeList errNode = doc.getElementsByTagName("HighestSeverity");
				Node errChk = errNode.item(0);
				
				Element eElementErr = (Element) errChk;
				String errChkMsg = getTagValue("Severity", eElementErr);
				if(errChkMsg.equals("SUCCESS")) {
					NodeList codeNode = doc.getElementsByTagName("StatusDetail");
					Node codes = codeNode.item(0);
					if(codes!=null) {
						Element eElementCode = (Element) codes;
						String codeMsg = getTagValue("Code", eElementCode);
						if(codeMsg.equals("DL")) {
							trackingNum.get(i).cneeAddrDncrypt(originKey.getSymmetryKey());
							trackingNum.get(i).cneeAddrDetailDncrypt(originKey.getSymmetryKey());
							HashMap<String,String> parameter = new HashMap<String,String>();
							parameter.put("nno", trackingNum.get(i).getNno());
							parameter.put("transCode", trackingNum.get(i).getTransCode());
							parameter.put("orderNo", trackingNum.get(i).getOrderNo());
							parameter.put("hawbNo", trackingNum.get(i).getHawbNo());
							parameter.put("dlvStatus", codeMsg.toString());
							parameter.put("cneeName", trackingNum.get(i).getCneeName());
							
							NodeList tNode = doc.getElementsByTagName("PackageWeight");
							Node nodeDetail = tNode.item(0);
							Element eElement= (Element) nodeDetail;
							String wtUnit = getTagValue("Units", eElement);
							String wtValue = getTagValue("Value", eElement);
							
							parameter.put("wtValue", wtValue);
							parameter.put("wtUnit", wtUnit);
							
							tNode = doc.getElementsByTagName("Events");
							nodeDetail = tNode.item(0);
							eElement= (Element) nodeDetail;
							
							String dlvTime = getTagValue("Timestamp", eElement);
							String toDlvTime = dlvTime.substring(0, 10) + " " + dlvTime.substring(11, 19);
							parameter.put("dlvTime", toDlvTime);
							
							
							
							tNode = doc.getElementsByTagName("DestinationAddress");
							nodeDetail = tNode.item(0);
							if(nodeDetail!=null) {
								eElement= (Element) nodeDetail;
								String StateOrProvinceCode = getTagValue("StateOrProvinceCode", eElement);
								String city = getTagValue("City", eElement);
								String CountryName = getTagValue("CountryName", eElement);
								parameter.put("cneeAddress1", city+" "+StateOrProvinceCode+" "+CountryName);
								
								parameter.put("city", city);
								parameter.put("state", StateOrProvinceCode);
								parameter.put("countryName", CountryName);
								
								parameter.put("cneeAddress2", trackingNum.get(i).getCneeAddr()+" "+trackingNum.get(i).getCneeAddrDetail());
							}else {
								parameter.put("cneeAddress1", "");
								
								parameter.put("city", "");
								parameter.put("state", "");
								parameter.put("countryName", "");
								
								parameter.put("cneeAddress2", "");
							}
							
							
							
							mapper.insertDlvResult(parameter);
							
							
						}
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		
	}
	
	
	public BlApplyVO selectBlApplyFED(String orderNno, String username, String remoteAddr,
			String transCode) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO rtnVal = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		UserOrderListVO userInfo = new UserOrderListVO();
		
		comnService.insertTBFromTMP(orderNno);
		
		userInfo = comnService.selectUserRegistOrderOne(orderNno);
		
		if (userInfo.getUserId().toLowerCase().equals("bmsmileus")) {
			resultShopify = sendFedexApiSmart(userInfo.getOrgStation(),orderNno,"NOM");
		} else {
			if(transCode.equals("FEG")) {
				resultShopify = sendFedexApiGround(userInfo.getOrgStation(),orderNno,"NOM");
			}else {
				resultShopify = sendFedexApiSmart(userInfo.getOrgStation(),orderNno,"NOM");
			}	
		}

		if(resultShopify.getStatus().equals("OK")) {
			//hawb 업데이트
			comnService.updateHawbNoInTbHawb(resultShopify.getHawbNo(), orderNno);
			comnService.updateHawbNoInTbOrderList(resultShopify.getHawbNo(), orderNno);
		}else {
			//TMP로 옮기고 TB 삭제, error 코드 확인할 것.
			HashMap<String,String> parameters = new HashMap<String,String>();

			parameters.put("nno", orderNno);
			parameters.put("status", resultShopify.getStatusList());
			comnService.deleteHawbNoInTbHawb(orderNno);
			comnService.insertTmpFromOrderList(orderNno, resultShopify.getErrorMsg());
			comnService.insertTmpFromOrderItem(orderNno);
			deleteOrderListByNno(orderNno);
			//mapper.updateErrorStatus(parameters);
		}
		
		return rtnVal;
		
	}
	
	public String fnCreateLabelJson(String[] nno) throws Exception {
		
		String jsonVal = createLabelJson(nno);
		
		String rtnVal = "";
		String inputLine = null;
		
		String pcode = "aciexpress";
		String branch = "001";
		String apikey = "e0c8e9add515697c81df2ede009878e0";
				
		/*
		StringBuffer outResult = new StringBuffer();
		try {
			//URL url = new URL("https://eparcel.kr/apiv2/RegData");
			//URL url = new URL(" https://globalparcelservice.com/connect/api/label/create");
			URL url = new URL("https://globalparcelservice.com/sandbox/global/api/label/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("pcode", pcode);
			conn.setRequestProperty("branch", branch);
			conn.setRequestProperty("apikey", apikey);
			conn.setRequestProperty("lang", "en");
			conn.setRequestProperty("Content-Type", "application/json");
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
		
		*/
		return jsonVal;
	}

	private String createLabelJson(String[] nno) {

		//String w_date = "2020071717540973";
		
		//String secret_key = "844123aa58161392";
		String secret_key = "d93a7716d31c8211"; /* sandbox */

       
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderListVO> orderInfo = new ArrayList<ApiOrderListVO>();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> pack = new LinkedHashMap<String, Object>();
		
		HashMap<String, Object> nnoList = new HashMap<String, Object>();
		nnoList.put("nno", nno);
		
		try {

			orderInfo = mapper.selectTmpOrderInfo(nnoList);
			
			for (int i = 0; i < orderInfo.size(); i++) {
				String hashKey = "";
				requestData = new LinkedHashMap<String, Object>();
				
				String key = "";
				String weight = "";
				String weight_unit = "";
				String length = "";
				String width = "";
				String height = "";
				String dimension_unit = "";
				String recv_addr1 = "";
				String recv_addr2 = "";
				String recv_city = "";
				String recv_state = "";
				String recv_zip_code = "";
				String recv_country = "";
				
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
				
				/* carrier */
				dataOne = new LinkedHashMap<String, Object>();
				/*
				if (orderInfo.get(i).getTransCode().equals("USP")) {
					dataOne.put("provider", "USPS");
				} else if (orderInfo.get(i).getTransCode().equals("FED") || orderInfo.get(i).getTransCode().equals("FEG") || orderInfo.get(i).getTransCode().equals("FES")){
					dataOne.put("provider", "FedEx");
				} else {
					dataOne.put("provider", orderInfo.get(i).getTransCode());
				}
				
				if (orderInfo.get(i).getTransCode().equals("USP")) {
					dataOne.put("service_type", "LOWEST");	
				} else if (orderInfo.get(i).getTransCode().equals("FED")) {
					dataOne.put("service_type", "SMART_POST");
				} else if (orderInfo.get(i).getTransCode().equals("FEG")) {
					dataOne.put("service_type", "GROUND");
				}
				*/
				dataOne.put("provider", "USPS");
				dataOne.put("service_type", "LOWEST");
				dataOne.put("packaging_type", "YOUR_PACKAGING");
				dataOne.put("transit_time", "");
				dataOne.put("contract_id", "");
				requestData.put("carrier", dataOne);

				
				dataOne = new LinkedHashMap<String, Object>();
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				String day = format.format(now);
				
				dataOne.put("shipping_date", day.substring(0, 10));
				dataOne.put("shipping_time", day.substring(10));
				dataOne.put("shipping_timezone", "America/Los_Angeles");
				requestData.put("shipping_datetime", dataOne);
				
				
				/* port */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("type", "");
				
				ArrayList<String> cityCode = new ArrayList<String>();
				cityCode.add("LAX");
				dataOne.put("name", cityCode);
				requestData.put("port", dataOne);
				
				/* package_total_gross_weight */
				dataOne = new LinkedHashMap<String, Object>();
				Double wta = Double.parseDouble(orderInfo.get(i).getUserWta());
				Double wtc = Double.parseDouble(orderInfo.get(i).getUserWtc());
				
				dataOne.put("value", Math.max(wta, wtc));
				weight = dataOne.get("value").toString().trim();
				
				
				String wtUnit = orderInfo.get(i).getWtUnit();
				
				if (wtUnit.toUpperCase().equals("KG")) {
					dataOne.put("unit", "kg");
				} else if (wtUnit.toUpperCase().equals("LB")) {
					dataOne.put("unit", "lbs");
				} else if (wtUnit.toUpperCase().equals("G")) {
					dataOne.put("unit", "g");
				} else if (wtUnit.toUpperCase().equals("OZ")) {
					dataOne.put("unit", "oz");
				}
				
				weight_unit = dataOne.get("unit").toString().trim();
				requestData.put("package_total_gross_weight", dataOne);
				
				
				/* package_dimensions */
				dataOne = new LinkedHashMap<String, Object>();
				String dimUnit = orderInfo.get(i).getDimUnit();
				
				if (dimUnit != null) {
					if (dimUnit.toUpperCase().equals("CM")) {
						dataOne.put("unit", "cm");
					} else if (dimUnit.toUpperCase().equals("IN")) {
						dataOne.put("unit", "in");
					}	
				} else {
					dataOne.put("unit", "cm");
				}
				
				
				dimension_unit = dataOne.get("unit").toString().trim();
				
				dataOne.put("length", orderInfo.get(i).getUserLength());
				dataOne.put("width", orderInfo.get(i).getUserWidth());
				dataOne.put("height", orderInfo.get(i).getUserHeight());
				
				length = dataOne.get("length").toString().trim();
				width = dataOne.get("width").toString().trim();
				height = dataOne.get("height").toString().trim();
				requestData.put("package_dimensions", dataOne);
				
				/* ship_from */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("send_firm", orderInfo.get(i).getShipperName());
				dataOne.put("send_name", orderInfo.get(i).getShipperName());
				System.out.println(orderInfo.get(i).getShipperAddr());
				dataOne.put("send_addr1", orderInfo.get(i).getShipperAddr());
				dataOne.put("send_addr2", orderInfo.get(i).getShipperAddrDetail());
				dataOne.put("send_city_locality", orderInfo.get(i).getShipperCity());
				dataOne.put("send_state_province", orderInfo.get(i).getShipperState());
				dataOne.put("send_zip_postal_code", orderInfo.get(i).getShipperZip());
				dataOne.put("send_country_code", "US");
				dataOne.put("send_email", orderInfo.get(i).getShipperEmail());
				dataOne.put("send_sms", orderInfo.get(i).getShipperTel());
				dataOne.put("send_address_residential_indicator", "no");
				requestData.put("ship_from", dataOne);
				
				/* ship_to */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("recv_firm", "");
				dataOne.put("recv_name", orderInfo.get(i).getCneeName());
				dataOne.put("recv_addr1", orderInfo.get(i).getCneeAddr());
				dataOne.put("recv_addr2", orderInfo.get(i).getCneeAddrDetail());
				dataOne.put("recv_city_locality", orderInfo.get(i).getCneeCity());
				dataOne.put("recv_state_province", orderInfo.get(i).getCneeState());
				dataOne.put("recv_zip_postal_code", orderInfo.get(i).getCneeZip());
				dataOne.put("recv_zip4_code", "");
				dataOne.put("recv_country_code", orderInfo.get(i).getDstnNation());
				dataOne.put("recv_email", orderInfo.get(i).getCneeEmail());
				dataOne.put("recv_sms", orderInfo.get(i).getCneeTel());
				dataOne.put("recv_address_residential_indicator", "yes");
				
				recv_addr1 = dataOne.get("recv_addr1").toString().trim();
				if (recv_addr2.equals("")) {
					recv_addr2 = dataOne.get("recv_addr2").toString();	
				} else {
					recv_addr2 = dataOne.get("recv_addr2").toString().trim();
				}
				recv_city = dataOne.get("recv_city_locality").toString().trim();
				recv_state = dataOne.get("recv_state_province").toString().trim();
				recv_zip_code = dataOne.get("recv_zip_postal_code").toString().trim();
				recv_country = dataOne.get("recv_country_code").toString().trim();
				requestData.put("ship_to", dataOne);
				
				/* return_address */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("rtn_firm", "GLOBAL PARCEL SERVICE");
				dataOne.put("rtn_name", "RETURN DEPARTMENT");
				dataOne.put("rtn_addr1", "20910 NORMANDIE AVE UNIT D");
				dataOne.put("rtn_addr2", "");
				dataOne.put("rtn_city_locality", "TORRANCE");
				dataOne.put("rtn_state_province", "CA");
				dataOne.put("rtn_zip_postal_code", "90502");
				dataOne.put("rtn_zip4_code", "");
				dataOne.put("rtn_country_code", "US");
				dataOne.put("rtn_email", "");
				dataOne.put("rtn_sms", "");
				requestData.put("return_address", dataOne);
				
				/* hash_key */
				key = secret_key+" "+weight+" "+weight_unit+" "+length+" "+width+" "+height+" "+dimension_unit+" "+recv_addr1+" "+recv_addr2+" "+recv_city+" "+recv_state+" "+recv_zip_code+" "+recv_country;
				//key = secret_key+weight+weight_unit+length+width+height+dimension_unit+recv_addr1+recv_addr2+recv_city+recv_state+recv_zip_code+recv_country;
				//key = reKey.trim();

				System.out.println(key);
				
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] messageDigest = md.digest(key.getBytes());
				StringBuffer sb = new StringBuffer();
				for(int idx = 0; idx < messageDigest.length; idx++) {
					sb.append(Integer.toString((messageDigest[idx] & 0xff) + 0x100, 16).substring(1));
				}
				hashKey = sb.toString();
				
				System.out.println(hashKey);
				requestData.put("hash_key", hashKey);
				requestData.put("has_dangerous_goods", "no");
				requestData.put("has_hazardous_material", "no");
				
				/* label */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("format", "pdf");
				dataOne.put("layout", "1");
				requestData.put("label", dataOne);
				
				/* customer_reference */
				dataOne = new LinkedHashMap<String, Object>();
				dataOne.put("cust_ref1", "");
				dataOne.put("cust_ref2", "");
				dataOne.put("cust_ref3", "");
				dataOne.put("cust_ref4", "");
				dataOne.put("cust_ref5", "");
				requestData.put("customer_reference", dataOne);
				
				
				dataList.add(i, requestData);
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnJsonArray.put("packages", dataList);
		
		return getJsonStringFromMap(rtnJsonArray);
	}
	
	public static String getJsonStringFromMap(LinkedHashMap<String, Object> data) {
		
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}
		
		
		return jsonObject.toString();
		
		
	}
	
}
