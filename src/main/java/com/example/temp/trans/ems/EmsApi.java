package com.example.temp.trans.ems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.el.stream.Stream;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.security.SEED128;
import com.example.temp.security.SecurityKeyVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spire.xls.ExcelPicture;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Service
public class EmsApi {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	EmsMapper mapper;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public ApiShopifyResultVO createShipmentDev(String nno) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		String parameter = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		parameter = makeEmsParameter(nno);
		
		URL url = new URL("http://eship.epost.go.kr/api.EmsApplyInsertReceiveTempCmdNewDEV.ems?"+"key=ae17dd59a93f47fb41641258087370"+"&regData="+parameter);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/plain");
		

//		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//		while((inputLine = in.readLine())!=null) {
//			outResult.append(inputLine);
//		}
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("error_code");
		Node errChk = errNode.item(0);
		if(errChk == null) {
			resultShopify.setStatus("OK");
			resultShopify.setHawbNo(doc.getElementsByTagName("regino").item(0).getTextContent().trim());
			resultShopify.setShipperReference(doc.getElementsByTagName("reqno").item(0).getTextContent().trim());
		}else {
			resultShopify.setStatus("FAIL");
			resultShopify.setErrorMsg(doc.getElementsByTagName("message").item(0).getTextContent().trim());//error 메시지.
		}
		return resultShopify;
	}
	
	public ApiShopifyResultVO createShipmentCancle(String regNo, String regiNo) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		String parameter = "";
		parameter = makeEmsCancle(regNo, regiNo);
		
		URL url = new URL("http://eship.epost.go.kr/api.EmsApplyCancel.ems?key=ae17dd59a93f47fb41641258087370&regData="+parameter);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "text/plain");
		
		int responseCode = connection.getResponseCode();

		if(responseCode == 200) {
			resultShopify.setStatus("OK");
		}else {
			resultShopify.setStatus("FAIL");
		}
//		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
//		InputStream inputStream = connection.getInputStream();
//		Document doc = dBuilder.parse(inputStream);
//		doc.getDocumentElement().normalize();
//		NodeList errNode = doc.getElementsByTagName("error_code");
//		Node errChk = errNode.item(0);
//		if(errChk == null) {
//			resultShopify.setStatus("OK");
//			resultShopify.setHawbNo(doc.getElementsByTagName("regino").item(0).getTextContent().trim());
//			resultShopify.setShipperReference(doc.getElementsByTagName("reqno").item(0).getTextContent().trim());
//		}else {
//			resultShopify.setStatus("FAIL");
//			resultShopify.setErrorMsg(doc.getElementsByTagName("message").item(0).getTextContent().trim());//error 메시지.
//		}
		return resultShopify;
	}
	
	public ApiShopifyResultVO createShipment(String nno, String type) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		String parameter = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		parameter = makeEmsParameter(nno);
		
		//URL url = new URL("http://eship.epost.go.kr/api.EmsApplyInsertReceiveTempCmdNewDEV.ems?"+"key=ae17dd59a93f47fb41641258087370"+"&regData="+parameter);
		URL url = new URL("http://eship.epost.go.kr/api.EmsApplyInsertReceiveTempCmdNew.ems?"+"key=ae17dd59a93f47fb41641258087370"+"&regData="+parameter);
		
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/plain");
		
		int responseCode = connection.getResponseCode();

//		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//		while((inputLine = in.readLine())!=null) {
//			outResult.append(inputLine);
//		}
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("error_code");
		Node errChk = errNode.item(0);
		if(errChk == null) {
			resultShopify.setStatus("OK");
			resultShopify.setHawbNo(doc.getElementsByTagName("regino").item(0).getTextContent().trim());
			resultShopify.setShipperReference(doc.getElementsByTagName("reqno").item(0).getTextContent().trim());
			resultShopify.setTreatporegipocd(doc.getElementsByTagName("treatporegipocd").item(0).getTextContent().trim());
			if(doc.getElementsByTagName("exchgPoCd").item(0)==null) {
				resultShopify.setExchgPoCd("");
			}else {
				resultShopify.setExchgPoCd(doc.getElementsByTagName("exchgPoCd").item(0).getTextContent().trim());
			}
			resultShopify.setPrerecevprc(doc.getElementsByTagName("prerecevprc").item(0).getTextContent().trim());
			
			createEmsPdf(resultShopify, nno);
		}else {
			resultShopify.setStatus("FAIL");
			resultShopify.setErrorMsg(doc.getElementsByTagName("message").item(0).getTextContent().trim());//error 메시지.
		}
		
		return resultShopify;
	}
	
	public void createEmsPdf(ApiShopifyResultVO resultShopify, String nno) throws Exception {
	//public void createEmsPdf() throws Exception {
		ApiOrderListVO orderInfo = new ApiOrderListVO();
		orderInfo = mapper.selectOrderInfo(nno);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		ArrayList<HashMap<String,Object>> orderItemInfo = new ArrayList<HashMap<String,Object>>();
		orderItemInfo = mapper.selectOrderItem(nno);
		
		
		
		String pdfPath = realFilePath + "/pdf";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		String barcodePath = pdfPath2 +orderInfo.getNno()+".JPEG";
		net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(resultShopify.getHawbNo());
		File barcodefile = new File(barcodePath);
		BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
		
		
		
		String filePath = realFilePath+"excel/ems/label2.xlsx";
		String filePath2 = realFilePath+"excel/ems/"+resultShopify.getHawbNo()+".pdf";
		FileInputStream fis = new FileInputStream(filePath);
		
		Workbook wb = new Workbook();
		wb.loadFromStream(fis);
		Worksheet sheet = wb.getWorksheets().get(0);
		
		ExcelPicture excelBarcode = sheet.getPictures().add(7, 5, barcodePath);
		excelBarcode.setWidth(380);
		excelBarcode.setHeight(80);
		
		ExcelPicture excelBarcode2 = sheet.getPictures().add(54, 8, barcodePath);
		excelBarcode2.setWidth(380);
		excelBarcode2.setHeight(80);
		
		sheet.getCellRange("E4").setText(resultShopify.getHawbNo());
		sheet.getCellRange("H57").setText(resultShopify.getHawbNo());
		sheet.getCellRange("N9").setText(orderInfo.getWDate());
		sheet.getCellRange("N10").setText(resultShopify.getTreatporegipocd());
		sheet.getCellRange("D11").setText(orderInfo.getShipperTel());
		sheet.getCellRange("D12").setText(orderInfo.getShipperName());
		
		sheet.getCellRange("C14").setText(orderInfo.getShipperAddr()+orderInfo.getShipperAddrDetail());
		sheet.getCellRange("E17").setText(orderInfo.getShipperZip());
		
		sheet.getCellRange("L11").setText(orderInfo.getCneeTel());
		sheet.getCellRange("L12").setText(orderInfo.getCneeName());
		sheet.getCellRange("K14").setText(orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail());
		sheet.getCellRange("L17").setText(orderInfo.getCneeZip());
		sheet.getCellRange("P17").setText(orderInfo.getNationName());
		
		sheet.getCellRange("O18").setText(resultShopify.getPrerecevprc()+"원");
		//sheet.getCellRange("O18").setText("124555원");
		double wtc = Double.parseDouble(orderInfo.getUserWidth())*Double.parseDouble(orderInfo.getUserHeight())*Double.parseDouble(orderInfo.getUserLength())/6000;
		String wtcString = Double.toString(wtc*1000);
		sheet.getCellRange("M19").setText(wtcString);
		sheet.getCellRange("N20").setText(orderInfo.getUserWidth()+"*"+orderInfo.getUserHeight()+"*"+orderInfo.getUserLength());
		sheet.getCellRange("P21").setText(orderInfo.getDstnNation().substring(0,1));
		sheet.getCellRange("Q21").setText(orderInfo.getDstnNation().substring(1,orderInfo.getDstnNation().length()));
		sheet.getCellRange("P23").setText(resultShopify.getExchgPoCd());
		//sheet.getCellRange("P23").setText("JPTYOA");
		
		sheet.getCellRange("H27").setText(orderInfo.getShipperName());
		
		sheet.getCellRange("B31").setText(orderInfo.getShipperName());
		sheet.getCellRange("K31").setText(orderInfo.getShipperName());
		
		sheet.getCellRange("B32").setText(orderInfo.getShipperAddr()+orderInfo.getShipperAddrDetail());
		sheet.getCellRange("K32").setText(orderInfo.getShipperAddr()+orderInfo.getShipperAddrDetail());
		
		sheet.getCellRange("B34").setText(orderInfo.getShipperTel());
		sheet.getCellRange("K34").setText(orderInfo.getShipperTel());
		
		sheet.getCellRange("G31").setText(orderInfo.getWDateTwo());
		sheet.getCellRange("P31").setText(orderInfo.getWDateTwo());
		
		sheet.getCellRange("F33").setText(orderInfo.getWDateTwo());
		sheet.getCellRange("F34").setText(resultShopify.getHawbNo());
		sheet.getCellRange("N33").setText(orderInfo.getWDateTwo());
		sheet.getCellRange("N34").setText(resultShopify.getHawbNo());
		
		sheet.getCellRange("B36").setText(orderInfo.getCneeName());
		sheet.getCellRange("K36").setText(orderInfo.getCneeName());
		
		sheet.getCellRange("B37").setText(orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail());
		sheet.getCellRange("K37").setText(orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail());
		
		sheet.getCellRange("D44").setText(orderInfo.getNationName());
		sheet.getCellRange("L44").setText(orderInfo.getNationName());
		
		sheet.getCellRange("F50").setText(orderInfo.getShipperName());
		sheet.getCellRange("N50").setText(orderInfo.getShipperName());
		
		sheet.getCellRange("D54").setText(orderInfo.getShipperName());
		sheet.getCellRange("D56").setText(orderInfo.getCneeName());
		
		sheet.getCellRange("N55").setText(orderInfo.getNationName());
		sheet.getCellRange("N56").setText(orderInfo.getWDate());
		sheet.getCellRange("P57").setText(resultShopify.getPrerecevprc()+"원");
		//sheet.getCellRange("P57").setText("124555원");
		int totalWeight = 0;
		for(int i =0; i< orderItemInfo.size(); i++) {
			String weight = "";
			int row = 21;
			String col = "";
			String totalRow = "";
			if(!orderInfo.getWtUnit().toString().toLowerCase().equals("g")) {
				if(orderItemInfo.get(i).get("WT_UNIT").toString().toLowerCase().equals("kg")) {
					weight 		+= (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*1000);
					totalWeight += (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*1000);
				}else if(orderItemInfo.get(i).get("WT_UNIT").toString().toLowerCase().equals("lb")) {
					weight 		+= (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*454);
					totalWeight += (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*454);
				}
			}else {
				weight 		+= (int)Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString());
				totalWeight += (int)Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString());
			}
			row = row+i;
			col = "B";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_DETAIL").toString());
			col = "E";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_CNT").toString());
			col = "F";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(weight);
			col = "G";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("UNIT_VALUE").toString());
			col = "H";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("HS_CODE").toString());
			col = "J";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("MAKE_CNTRY").toString());
			
			row=46+i;
			col = "B";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_DETAIL").toString());
			col = "E";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_CNT").toString()+" /");
			col = "F";
			sheet.getCellRange(totalRow).setText("USD  "+orderItemInfo.get(i).get("UNIT_VALUE").toString());
			col = "G";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("UNIT_VALUE").toString());
			col = "H";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("HS_CODE").toString());
			
			col = "K";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_DETAIL").toString());
			col = "M";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("ITEM_CNT").toString()+" /");
			col = "N";
			sheet.getCellRange(totalRow).setText("USD  "+orderItemInfo.get(i).get("UNIT_VALUE").toString());
			col = "O";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("UNIT_VALUE").toString());
			col = "Q";
			totalRow = col+row;
			sheet.getCellRange(totalRow).setText(orderItemInfo.get(i).get("HS_CODE").toString());
			
		}
		sheet.getCellRange("M18").setText(Integer.toString(totalWeight));
		
		
		wb.saveToFile(filePath2, FileFormat.PDF);
		try {
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult asssd = new PutObjectResult();
			File file = new File(filePath2);
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if(amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/"+year+"/"+week, orderInfo.getUserId()+"_"+resultShopify.getHawbNo(), file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				asssd = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			//file.delete();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public String makeEmsCancle(String regNo, String regiNo) throws Exception {
		String rtnVal = "";
		SEED128 seed = new SEED128();
		
		rtnVal = "custno=0005039458&apprno=40013H0304&";
		rtnVal += "reqno="+regNo+"&regino="+regiNo+"&cancelyn=Y";
		String encryptRtnVal = seed.getEncryptData("ae17dd59a93f47fb", rtnVal);
		
		return encryptRtnVal;
	}
	

	
	public String makeEmsParameter(String nno) throws Exception {
		// TODO Auto-generated method stub
		String rtnVal = "";
		ApiOrderListVO orderInfo = new ApiOrderListVO();
		ArrayList<HashMap<String,Object>> orderItemInfo = new ArrayList<HashMap<String,Object>>();
		int totalWeight = 0;
		String emGubun="EM_gubun=";
		String contents="contents=";
		String number="number=";
		String weight="weight=";
		String value="value=";
		String hscode="hs_code=";
		String origin="origin=";
		
		String[] shipperTel = null;
		String[] shipperMobile = null;
		
		SEED128 seed = new SEED128();
		
		orderInfo = mapper.selectOrderInfo(nno);
		orderItemInfo = mapper.selectOrderItem(nno);
		orderInfo.setSymmetryKey(originKey.getSymmetryKey());
		orderInfo.dncryptData();
		
		if(!orderInfo.getShipperTel().equals("")) {
			String numValue = orderInfo.getShipperTel();
			numValue = numValue.replaceAll("[^0-9]", "");
			shipperTel = comnService.confirmDialNum(numValue);
		}
		
		if(!orderInfo.getShipperHp().equals("")) {
			String numValue = orderInfo.getShipperTel();
			numValue = numValue.replaceAll("[^0-9]", "");
			shipperMobile = comnService.confirmDialNum(numValue);
		}
			
		for(int i =0; i < orderItemInfo.size(); i++) {
			emGubun 	+= "Merchandise";
			contents 	+=orderItemInfo.get(i).get("ITEM_DETAIL").toString();
			number 		+=orderItemInfo.get(i).get("ITEM_CNT").toString();
			if(!orderInfo.getWtUnit().toString().toLowerCase().equals("g")) {
				if(orderItemInfo.get(i).get("WT_UNIT").toString().toLowerCase().equals("kg")) {
					weight 		+= (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*1000);
					totalWeight += (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*1000);
				}else if(orderItemInfo.get(i).get("WT_UNIT").toString().toLowerCase().equals("lb")) {
					weight 		+= (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*454);
					totalWeight += (int)(Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString())*454);
				}
			}else {
				weight 		+= (int)Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString());
				totalWeight += (int)Double.parseDouble(orderItemInfo.get(i).get("USER_WTA").toString());
			}
			
//			weight 		+="3";
			value 		+=orderItemInfo.get(i).get("UNIT_VALUE").toString();
			hscode 		+=orderItemInfo.get(i).get("HS_CODE").toString();
//			hscode 		+="8523491020";
			origin 		+=orderItemInfo.get(i).get("MAKE_CNTRY").toString();
			if(i != orderItemInfo.size()-1) {
				emGubun 	+=";";
				contents 	+=";";
				number 		+=";";
				weight 		+=";";
				value 		+=";";
				hscode 		+=";";
				origin 		+=";";
			}
		}
		//rtnVal +="&";
		rtnVal +="custno=0005039458&";
		rtnVal +="apprno=40013H0304&";
		rtnVal +="premiumcd=31&";
		rtnVal +="em_ee=em&";
		rtnVal +="countrycd="+orderInfo.getDstnNation()+"&";
		rtnVal +="totweight="+totalWeight+"&";
		rtnVal +="boyn=N&";
//		rtnVal +="boyprc=&";
//		rtnVal +="nextdayreserveyn=N&";
//		rtnVal +="reqhhmi=&";
		rtnVal +="orderno="+orderInfo.getHawbNo()+"&";
//		rtnVal +="premiumexportyn=Y&";
//		rtnVal +="cdremark=&";
		rtnVal +="sender="+orderInfo.getShipperName()+"&";
		rtnVal +="senderzipcode="+orderInfo.getShipperZip()+"&";
		rtnVal +="senderaddr1="+orderInfo.getShipperAddrDetail()+"&";
		rtnVal +="senderaddr2="+orderInfo.getShipperAddr()+"&";
		//rtnVal +="senderaddr1=201, Cheonglim Building&";
		//rtnVal +="senderaddr2=19, Samseong-ro 96-gil, Gangnam-gu, Seoul, Republic of Korea&";
		rtnVal +="sendertelno1=82&";
		rtnVal +="sendertelno2="+shipperTel[0]+"&";
		rtnVal +="sendertelno3="+shipperTel[1]+"&";
		rtnVal +="sendertelno4="+shipperTel[2]+"&";
//		rtnVal +="sendertelno2="+"123"+"&";
//		rtnVal +="sendertelno3="+"1234"+"&";
//		rtnVal +="sendertelno4="+"5678"+"&";
		rtnVal +="sendermobile1=82&";
		rtnVal +="sendermobile2="+shipperMobile[0]+"&";
		rtnVal +="sendermobile3="+shipperMobile[1]+"&";
		rtnVal +="sendermobile4="+shipperMobile[2]+"&";
//		rtnVal +="sendermobile2="+"010"+"&";
//		rtnVal +="sendermobile3="+"1234"+"&";
//		rtnVal +="sendermobile4="+"5678"+"&";
		rtnVal +="snd_massage="+orderInfo.getWhReqMsg()+"&";
		rtnVal +="receivename="+orderInfo.getCneeName()+"&";
		rtnVal +="receivezipcode="+orderInfo.getCneeZip()+"&";
		rtnVal +="receiveaddr1="+orderInfo.getCneeState()+"&";
		rtnVal +="receiveaddr2="+orderInfo.getCneeCity()+"&";
		rtnVal +="receiveaddr3="+orderInfo.getCneeAddr()+orderInfo.getCneeAddrDetail()+"&";
		rtnVal +="buildnm="+""+"&";
		
		String receiveTelNo = orderInfo.getCneeTel();
		receiveTelNo = receiveTelNo.replaceAll("[^0-9]", "");
		rtnVal +="receivetelno="+receiveTelNo+"&";
//		rtnVal +="receivemail="+orderInfo.get("CNEE_EMAIL").toString()+"&";
		//ITEM START
		rtnVal +=emGubun+"&";
		rtnVal +=contents+"&";
		rtnVal +=number+"&";
		rtnVal +=weight+"&";
		rtnVal +=value+"&";
		rtnVal +=hscode+"&";
		rtnVal +=origin+"&";
		rtnVal +="ecommerceyn=N&";
		rtnVal +="boxlength="+orderInfo.getUserLength()+"&";
		rtnVal +="boxwidth="+orderInfo.getUserWidth()+"&";
		rtnVal +="boxheight="+orderInfo.getUserHeight()+"&";
		rtnVal +="currenitcd="+orderItemInfo.get(0).get("CHG_CURRENCY").toString();
		//ITEM END
		
		//custno = 0005039458
		//apprno = 40013H0304
		//

		//String encryptRtnVal = seed.getEncryptData("ae17dd59a93f47fb", rtnVal);
		//String encryptRtnVal = seed.getEncryptData("a31827540c5227fc162751", rtnVal);
		String encryptRtnVal = seed.getEncryptData("a31827540c5227ef100703", rtnVal);
		return encryptRtnVal;
	}

	public void selectBlApplyCheck(String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO blApplyInfo = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		//blApplyInfo = comnService.selectBlApply(orderNno, userId, userIp);
		
		if(!mapper.selectHawbNoInTmp(orderNno).equals("")) {//? check 할 것. fakebl 관련..
			comnService.insertTBFromTMP(orderNno);
		}else {
			comnService.selectBlApply(orderNno, userId, userIp);
		}
		try {
			resultShopify = createShipmentDev(orderNno);
			if(resultShopify.getStatus().equals("OK")) {
				//삭제
				createShipmentCancle(resultShopify.getShipperReference(),resultShopify.getHawbNo());
			}else {
				comnService.deleteHawbNoInTbHawb(orderNno);
				comnService.insertTMPFromTB_EMS(orderNno, resultShopify.getErrorMsg(), userId, userIp);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			comnService.deleteHawbNoInTbHawb(orderNno);
			comnService.insertTMPFromTB_EMS(orderNno, e.getMessage(), userId, userIp);
		}
	}

	public void selectBlApply_FakeBl(String orderNno, String userId, String userIp, String inputType) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO blApplyInfo = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		//blApplyInfo = comnService.selectBlApply(orderNno, userId, userIp);
		String hawbNo = mapper.selectHawbNoInTB(orderNno);
		if(hawbNo == null || hawbNo.isEmpty()) {
			if(!mapper.selectHawbNoInTmp(orderNno).equals("")) {//? check 할 것. fakebl 관련..
				comnService.insertTBFromTMP(orderNno);
			}else {
				comnService.selectBlApply(orderNno, userId, userIp);
			}
		}
		
		try {
			//resultShopify = createShipment(orderNno);
			resultShopify = createShipment(orderNno, inputType);
			if(resultShopify.getStatus().equals("OK")) {
				MatchingVO matchVo = new MatchingVO(); 
				matchVo.setKeyHawbNo(comnService.selectHawbNoByNNO(orderNno));
				matchVo.setValueMatchNo(resultShopify.getHawbNo());
				matchVo.setMatchTransCode("EMS");
				matchVo.setNno(orderNno);
				comnService.updateShipperReference(resultShopify.getShipperReference(), orderNno);
				comnService.deleteMatchingInfo(matchVo);
				comnService.insertMatchingInfo(matchVo);
			}else {
				comnService.deleteHawbNoInTbHawb(orderNno);
				comnService.insertTMPFromTB_EMS(orderNno, resultShopify.getErrorMsg(), userId, userIp);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			comnService.deleteHawbNoInTbHawb(orderNno);
			comnService.insertTMPFromTB_EMS(orderNno, e.getMessage(), userId, userIp);
		}
	}
	
	public void selectBlApply_Nomal(String orderNno, String userId, String userIp, String inputType) throws Exception {
		// TODO Auto-generated method stub
		BlApplyVO blApplyInfo = new BlApplyVO();
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		if(!inputType.equals("INSP")) {
			blApplyInfo = comnService.selectBlApply(orderNno, userId, userIp);
		}
		//comnService.insertTBFromTMP(orderNno);
		try {
			resultShopify = createShipment(orderNno, inputType);
			if(resultShopify.getStatus().equals("OK")) {
				comnService.updateHawbNoInTbOrderList(resultShopify.getHawbNo(), orderNno);
				comnService.updateShipperReference(resultShopify.getShipperReference(), orderNno);
			}else {
				throw new Exception();				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			comnService.deleteHawbNoInTbHawb(orderNno);
			if(resultShopify.getErrorMsg().equals("")) {
				comnService.insertTMPFromTB(orderNno, e.getMessage(), userId, userIp);
			}else {
				comnService.insertTMPFromTB(orderNno, resultShopify.getErrorMsg(), userId, userIp);
			}
			
		}
	}
	
	public ArrayList<HashMap<String, Object>> makeEmsPod(String hawbNo) throws Exception {
		ArrayList<HashMap<String, Object>>  podDetatailArray =  new ArrayList<HashMap<String, Object>> ();
		URL url = new URL("http://biz.epost.go.kr/KpostPortal/openapi?regkey=ae17dd59a93f47fb41641258087370&target=emsTrace"+"&query="+hawbNo);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/plain");
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		//Document doc = krPostPdf("");
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("error_code");
		Node errChk = errNode.item(0);
		
		if(errChk != null) {
			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
			String errChkMsg = doc.getElementsByTagName("error_code").item(0).getTextContent().trim();
			if(errChkMsg.equals("")) {
				podDetatil.put("UpdateCode", "-200"); 
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
				podDetatil.put("ProblemCode","-22"); 
				podDetatil.put("Comments", errChkMsg);
				podDetatil.put("delvNo", "");
			}else {
				podDetatil.put("UpdateCode", "-200"); 
				podDetatil.put("UpdateDateTime", "");
				podDetatil.put("UpdateLocation", "");
				podDetatil.put("UpdateDescription", doc.getElementsByTagName("message").item(0).getTextContent().trim());
				podDetatil.put("ProblemCode", doc.getElementsByTagName("error_code").item(0).getTextContent().trim()); 
				podDetatil.put("Comments", doc.getElementsByTagName("message").item(0).getTextContent().trim());
				podDetatil.put("delvNo", "");
			}
			podDetatailArray.add(podDetatil);
		}else {
			doc.getElementsByTagName("item").item(0).getChildNodes().item(1).getNodeName();
			NodeList nodeList = doc.getElementsByTagName("item");
			for(int i =0; i < nodeList.getLength(); i++) {
				LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
				NodeList itemInfoNode = nodeList.item(i).getChildNodes();
				String ymd = "";
				String hms = "";
				for(int j = 0; j < itemInfoNode.getLength(); j++) {
					if(itemInfoNode.item(j).getNodeName().equals("upucd")) {
						if(itemInfoNode.item(j).getTextContent().trim().equals("I")) {
							podDetatil.put("UpdateCode", "S500");
						}else {
							podDetatil.put("UpdateCode", itemInfoNode.item(j).getTextContent().trim());
						}
					}else if(itemInfoNode.item(j).getNodeName().equals("eventregiponm")) {
						podDetatil.put("UpdateLocation", itemInfoNode.item(j).getTextContent().trim());
					}else if(itemInfoNode.item(j).getNodeName().equals("eventnm")) {
						podDetatil.put("UpdateDescription", itemInfoNode.item(j).getTextContent().trim());
						podDetatil.put("Comments", itemInfoNode.item(j).getTextContent().trim());
					}else if(itemInfoNode.item(j).getNodeName().equals("eventymd")) {
						ymd = itemInfoNode.item(j).getTextContent().trim();
					}else if(itemInfoNode.item(j).getNodeName().equals("eventhms")) {
						hms = itemInfoNode.item(j).getTextContent().trim();
					}
					
					
				}
				podDetatil.put("ProblemCode",""); 
				podDetatil.put("UpdateDateTime", ymd.replaceAll("[.]", "-")+" "+hms);
				podDetatailArray.add(podDetatil);
			}
		}
		
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makeEmsForPod(String hawbNo) throws Exception {
		ArrayList<HashMap<String, Object>>  podDetatailArray =  new ArrayList<HashMap<String, Object>> ();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
		String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
		
		URL url = new URL("http://biz.epost.go.kr/KpostPortal/openapi?regkey=ae17dd59a93f47fb41641258087370&target=emsTrace"+"&query="+hawbNo);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/plain");
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("error_code");
		Node errChk = errNode.item(0);
		
		if (errChk != null) {
			podDetatil.put("UpdateCode", "-200");
			podDetatil.put("UpdateDateTime", "");
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "No Data");
		} else {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			
			String xml = writer.toString();
			JSONObject object = XML.toJSONObject(xml);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object json = mapper.readValue(object.toString(), Object.class);
			String output = mapper.writeValueAsString(json);
			
			JSONObject rtnJson = new JSONObject(output);
			JSONObject trace = new JSONObject(String.valueOf(rtnJson.get("trace").toString()));
			JSONObject itemList = new JSONObject(String.valueOf(trace.get("itemlist").toString()));
			JSONArray items = itemList.getJSONArray("item");

			for (int i = items.length()-1; i > -1; i--) {
				podDetatil = new LinkedHashMap<String, Object>();
				JSONObject obj = (JSONObject) items.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyymmddHHmmss");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String sortDate = obj.get("sortingdate").toString();
				Date date = format.parse(sortDate);
				String dateTime = format2.format(date);

				if (obj.get("upucd").toString().equals("I")) {
					podDetatil.put("UpdateCode", "600");
					podDetatil.put("UpdateDateTime", dateTime);
					podDetatil.put("UpdateLocation", obj.get("eventregiponm"));
					podDetatil.put("UpdateDescription", "Delivered");
				} else if (obj.get("upucd").toString().equals("G")) {
					podDetatil.put("UpdateCode", "500");
					podDetatil.put("UpdateDateTime", dateTime);
					podDetatil.put("UpdateLocation", obj.get("eventregiponm"));
					podDetatil.put("UpdateDescription", "Out for Delivery");
				} else if (obj.get("upucd").toString().equals("D")) {
					podDetatil.put("UpdateCode", "400");
					podDetatil.put("UpdateDateTime", dateTime);
					podDetatil.put("UpdateLocation", obj.get("eventregiponm"));
					podDetatil.put("UpdateDescription", "Arrival in destination country");
				} else if (obj.get("upucd").toString().equals("C")) {
					podDetatil.put("UpdateCode", "300");
					podDetatil.put("UpdateDateTime", dateTime);
					podDetatil.put("UpdateLocation", obj.get("eventregiponm"));
					podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
				} else {
					continue;
				}
				
				podDetatailArray.add(podDetatil);
			}
		}
		
		podDetatil = new LinkedHashMap<String, Object>();
		podDetatil.put("UpdateCode", "200");
		podDetatil.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
		podDetatil.put("UpdateLocation", "Republic of Korea");
		podDetatil.put("UpdateDescription", "Finished warehousing");
		podDetatailArray.add(podDetatil);
		
		podDetatil = new LinkedHashMap<String, Object>();
		podDetatil.put("UpdateCode", "100");
		podDetatil.put("UpdateDateTime", regInDate);
		podDetatil.put("UpdateLocation", "Republic of Korea");
		podDetatil.put("UpdateDescription", "Order information has been entered");
		podDetatailArray.add(podDetatil);
		
		return podDetatailArray;
	}
	
	//테스트용 데이터
	public Document krPostPdf(String nno) throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(true); //standalone="no" 를 없애준다.
        
        Element trace = doc.createElement("trace");
        doc.appendChild(trace);

        // code 엘리먼트
        Element sendernm = doc.createElement("sendernm");
        sendernm.appendChild(doc.createCDATASection("BOGWANG EMS"));
        trace.appendChild(sendernm);
        
        Element receivernm = doc.createElement("receivernm");
        receivernm.appendChild(doc.createCDATASection("HONG GILDONG"));
        trace.appendChild(receivernm);
        
        Element regino = doc.createElement("regino");
        regino.appendChild(doc.createCDATASection("EG123456789KR"));
        trace.appendChild(regino);
        
        Element mailtypenm = doc.createElement("mailtypenm");
        mailtypenm.appendChild(doc.createCDATASection("EMS"));
        trace.appendChild(mailtypenm);
        
        Element mailkindnm = doc.createElement("mailkindnm");
        mailkindnm.appendChild(doc.createCDATASection("비서류"));
        trace.appendChild(mailkindnm);
        
        Element deliverydate = doc.createElement("deliverydate");
        deliverydate.appendChild(doc.createCDATASection("2019-05-06 09:03"));
        trace.appendChild(deliverydate);
        
        Element deliveryyn = doc.createElement("deliveryyn");
        deliveryyn.appendChild(doc.createCDATASection("Y"));
        trace.appendChild(deliveryyn);
        
        Element signernm = doc.createElement("signernm");
        signernm.appendChild(doc.createCDATASection("GILDONG HOTELS"));
        trace.appendChild(signernm);
        
        Element relationnm = doc.createElement("relationnm");
        relationnm.appendChild(doc.createCDATASection(""));
        trace.appendChild(relationnm);
        
        Element connectedregino = doc.createElement("connectedregino");
        connectedregino.appendChild(doc.createCDATASection(""));
        trace.appendChild(connectedregino);
        
        Element postmannm = doc.createElement("postmannm");
        postmannm.appendChild(doc.createCDATASection(""));
        trace.appendChild(postmannm);
        
        Element inboundoutboundnm = doc.createElement("inboundoutboundnm");
        inboundoutboundnm.appendChild(doc.createCDATASection("수출우편물"));
        trace.appendChild(inboundoutboundnm);
        
        Element recvpostzipcd = doc.createElement("recvpostzipcd");
        recvpostzipcd.appendChild(doc.createCDATASection("123456"));
        trace.appendChild(recvpostzipcd);
        
        Element recvposttelno = doc.createElement("recvposttelno");
        recvposttelno.appendChild(doc.createCDATASection("02-1234-5678"));
        trace.appendChild(recvposttelno);
        
        Element destcountrycd = doc.createElement("destcountrycd");
        destcountrycd.appendChild(doc.createCDATASection("AU"));
        trace.appendChild(destcountrycd);
        
        Element destcountrynm = doc.createElement("destcountrynm");
        destcountrynm.appendChild(doc.createCDATASection("오스트레일리아(호주)"));
        trace.appendChild(destcountrynm);
        
        Element gcdate = doc.createElement("gcdate");
        gcdate.appendChild(doc.createCDATASection("20190507"));
        trace.appendChild(gcdate);

        Element postimpccd = doc.createElement("postimpccd");
        postimpccd.appendChild(doc.createCDATASection("AUSYDB"));
        trace.appendChild(postimpccd);
        
        Element customsfailednm = doc.createElement("customsfailednm");
        customsfailednm.appendChild(doc.createCDATASection(""));
        trace.appendChild(customsfailednm);
        
        Element sendcnt = doc.createElement("sendcnt");
        sendcnt.appendChild(doc.createCDATASection("249"));
        trace.appendChild(sendcnt);
        
        Element sendflightno = doc.createElement("sendflightno");
        sendflightno.appendChild(doc.createCDATASection("OZ123"));
        trace.appendChild(sendflightno);
        
        Element airdate = doc.createElement("airdate");
        airdate.appendChild(doc.createCDATASection("THU MAY 02 20:00:00 KST 2019"));
        trace.appendChild(airdate);
        
        Element deliposttelno = doc.createElement("deliposttelno");
        deliposttelno.appendChild(doc.createCDATASection(""));
        trace.appendChild(deliposttelno);
        
        Element itemlist = doc.createElement("itemlist");
        trace.appendChild(itemlist);
        
        for(int i = 0; i < 2; i ++) {
        	Element item = doc.createElement("item");
            itemlist.appendChild(item);
            
            Element sortingdate = doc.createElement("sortingdate");
            sortingdate.appendChild(doc.createCDATASection("20191234567890  "));
            item.appendChild(sortingdate);
            
            Element eventhms = doc.createElement("eventhms");
            eventhms.appendChild(doc.createCDATASection("19:08"));
            item.appendChild(eventhms);
            
            Element eventregiponm = doc.createElement("eventregiponm");
            eventregiponm.appendChild(doc.createCDATASection("서울용산 "+i));
            item.appendChild(eventregiponm);
            
            Element delivrsltnm = doc.createElement("delivrsltnm");
            eventhms.appendChild(doc.createCDATASection(""));
            item.appendChild(delivrsltnm);
            
            Element nondelivreasnnm = doc.createElement("nondelivreasnnm");
            nondelivreasnnm.appendChild(doc.createCDATASection(""));
            item.appendChild(nondelivreasnnm);
            
            Element eventnm = doc.createElement("eventnm");
            eventnm.appendChild(doc.createCDATASection("접수"));
            item.appendChild(eventnm);
            
            Element eventymd = doc.createElement("eventymd");
            eventymd.appendChild(doc.createCDATASection("2019.05.01"));
            item.appendChild(eventymd);
            
            Element upucd = doc.createElement("upucd");
            upucd.appendChild(doc.createCDATASection(""));
            item.appendChild(upucd);
        }
		return doc;
	}
}
