package com.example.temp.manager.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.store.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.temp.manager.mapper.ManagerAramexMapper;
import com.example.temp.manager.service.ManagerAramexService;
import com.example.temp.manager.vo.AramexListVO;
import com.example.temp.manager.vo.HawbVO;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import net.aramex.ws.ShippingTrackAPI.v1.TrackingClientInfo;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingResult;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentRequest;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentResponse;


@Service
@Transactional
public class ManagerAramexServiceImpl implements ManagerAramexService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	private ManagerAramexMapper mapper;

	
	@Override
	public int selectTotalCountAramex(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTotalCountAramex(parameterInfo);
	}


	@Override
	public ArrayList<AramexListVO> selectAramexList(HashMap<String, Object> parameterInfo) throws Exception {
		ArrayList<AramexListVO> returnList = new ArrayList<AramexListVO>();
		returnList = mapper.selectAramexList(parameterInfo);
		return returnList;
	}


	@Override
	public void updateAramexListError(HashMap<String, Object> parameterInfo) throws Exception {
		
		mapper.updateAramexListError(parameterInfo);
	}


	@Override
	public String insertAramexExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request, HashMap<String, String> parameters) throws Exception {
		String result="F";
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		AramexListVO aramexListVO = new AramexListVO();
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		
		
		DataFormatter formatter = new DataFormatter();
		SimpleDateFormat setDate = new SimpleDateFormat("yyyyMMdd");
		
		
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						XSSFRow rowPivot = sheet.getRow(0);
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							String rowResult="Err!:";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) 
							{
								int valSize = 0;
								Date times2 = new Date();
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if(cell==null) {
									value="";
								}else {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											if( DateUtil.isCellDateFormatted(cell)) {
												Date date = cell.getDateCellValue();
												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											}else {
												if(rowPivot.getCell(columnIndex).getStringCellValue().equals("Weight")) {
													System.out.println(value);
												}
												cell.setCellType( HSSFCell.CELL_TYPE_STRING );
												value = cell.getStringCellValue();
											}
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = cell.getStringCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = cell.getErrorCellValue() + "";
											break;
									}
								}
								
								
								switch (rowPivot.getCell(columnIndex).getStringCellValue()) {
									case "Airline":
										aramexListVO.setAirline(value);
									break;
									case "Consol":
										aramexListVO.setConsol(value);
									break;
									case "Origin":
										aramexListVO.setOrigin(value);
									break;
									case "Destination":
										aramexListVO.setDestination(value);
									break;
									case "FLT1Carrier":
										aramexListVO.setFlt1carrier(value);
									break;
									case "FLT1Number":
										aramexListVO.setFlt1number(value);
									break;
									case "FLT1ETD":
										aramexListVO.setFlt1etd(value);
									break;
									case "FinalETA":
										aramexListVO.setFinaleta(value);
									break;
									case "AWB":
										aramexListVO.setAwb(value);
									break;
									case "PickupDate":
										aramexListVO.setPickupdate(value);
									break;
									case "PCS":
										aramexListVO.setPcs(value);
									break;
									case "Weight":
										aramexListVO.setWeight(value);
									break;
									case "Unit":
										aramexListVO.setUnit(value);
									break;
									case "CommodityDescription":
										aramexListVO.setCommoditydescription(value);
									break;
									case "Customs":
										aramexListVO.setCustoms(value);
									break;
									case "CustomsCurrency":
										aramexListVO.setCustomscurrency(value);
									break;
									case "ShipperName":
										aramexListVO.setShippername(value);
									break;
									case "ShipperAddress":
										aramexListVO.setShipperaddress(value);
									break;
									case "ShipperTel":
										aramexListVO.setShippertel(value);
									break;
									case "OriginCity":
										aramexListVO.setOrigincity(value);
									break;
									case "OriginZipCode":
										aramexListVO.setOriginzipcode(value);
									break;
									case "OriginCountry":
										aramexListVO.setOrigincountry(value);
									break;
									case "ConsigneeName":
										aramexListVO.setConsigneename(value);
									break;
									case "ConsigneeAddress":
										aramexListVO.setConsigneeaddress(value);
									break;
									case "ConsigneeTel":
										aramexListVO.setConsigneetel(value);
									break;
									case "ConsigneeEmail":
										aramexListVO.setConsigneeemail(value);
									break;
									case "DestCity":
										aramexListVO.setDestcity(value);
									break;
									case "DestState":
										aramexListVO.setDeststate(value);
									break;
									case "DestZipCode":
										aramexListVO.setDestzipcode(value);
									break;
									case "DestCountry":
										aramexListVO.setDestcountry(value);
									break;
									case "ChargingWeight":
										aramexListVO.setChargingweight(value);
									break;
									case "HS_CODE":
										aramexListVO.setHsCode(value);
									break;

								}
							} // 현재row vo에 set 완료
							/* OrderListVO */
							aramexListVO.encryptData();
							mapper.insertAramexExcelUpload(aramexListVO);
								// vo 검증로직은 여기
							
						}
					}
				}else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						HSSFRow rowPivot = sheet.getRow(0);
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							String rowResult="Err!:";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) 
							{
								int valSize = 0;
								Date times2 = new Date();
								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if(cell==null) {
									value="";
								}else {
									
									// 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									switch (cell.getCellType()) {
										case HSSFCell.CELL_TYPE_NUMERIC:
											
											if( DateUtil.isCellDateFormatted(cell)) {
												Date date = cell.getDateCellValue();
												value = new SimpleDateFormat("yyyyMMddHHmm").format(date);
											}else {
												cell.setCellType( HSSFCell.CELL_TYPE_STRING );
												value = cell.getStringCellValue();
											}
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = cell.getStringCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = cell.getErrorCellValue() + "";
											break;
									}
								}
								
								
								switch (rowPivot.getCell(columnIndex).getStringCellValue()) {
									case "Airline":
										aramexListVO.setAirline(value);
									break;
									case "Consol":
										aramexListVO.setConsol(value);
									break;
									case "Origin":
										aramexListVO.setOrigin(value);
									break;
									case "Destination":
										aramexListVO.setDestination(value);
									break;
									case "FLT1ETD":
										aramexListVO.setFlt1etd(value);
									break;
									case "FinalETA":
										aramexListVO.setFinaleta(value);
									break;
									case "FLT1Carrier":
										aramexListVO.setFlt1carrier(value);
									break;
									case "FLT1Number":
										aramexListVO.setFlt1number(value);
									break;
									case "AWB":
										aramexListVO.setAwb(value);
									break;
									case "PickupDate":
										aramexListVO.setPickupdate(value);
									break;
									case "PCS":
										aramexListVO.setPcs(value);
									break;
									case "Weight":
										aramexListVO.setWeight(value);
									break;
									case "Unit":
										aramexListVO.setUnit(value);
									break;
									case "CommodityDescription":
										aramexListVO.setCommoditydescription(value);
									break;
									case "Customs":
										aramexListVO.setCustoms(value);
									break;
									case "CustomsCurrency":
										aramexListVO.setCustomscurrency(value);
									break;
									case "ShipperName":
										aramexListVO.setShippername(value);
									break;
									case "ShipperAddress":
										aramexListVO.setShipperaddress(value);
									break;
									case "ShipperTel":
										aramexListVO.setShippertel(value);
									break;
									case "OriginCity":
										aramexListVO.setOrigincity(value);
									break;
									case "OriginZipCode":
										aramexListVO.setOriginzipcode(value);
									break;
									case "OriginCountry":
										aramexListVO.setOrigincountry(value);
									break;
									case "ConsigneeName":
										aramexListVO.setConsigneename(value);
									break;
									case "ConsigneeAddress":
										aramexListVO.setConsigneeaddress(value);
									break;
									case "ConsigneeTel":
										aramexListVO.setConsigneetel(value);
									break;
									case "ConsigneeEmail":
										aramexListVO.setConsigneeemail(value);
									break;
									case "DestCity":
										aramexListVO.setDestcity(value);
									break;
									case "DestState":
										aramexListVO.setDeststate(value);
									break;
									case "DestZipCode":
										aramexListVO.setDestzipcode(value);
									break;
									case "DestCountry":
										aramexListVO.setDestcountry(value);
									break;
									case "ChargingWeight":
										aramexListVO.setChargingweight(value);
									break;
									case "HS_CODE":
										aramexListVO.setHsCode(value);
									break;
	
								}
								
							} 
							// 현재row vo에 set 완료
							// vo 검증로직은 여기
							aramexListVO.encryptData();
							mapper.insertAramexExcelUpload(aramexListVO);
						}
					}
				}
			} catch (IOException e) {
				logger.error("Exception", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
				
			} catch (Exception e) {
				logger.error("Exception", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				delFile.delete();
			}
	
		 result = "등록되었습니다.";
		}
		return result;
	}
	
	public Map filesUpload(MultipartHttpServletRequest multi, String uploadPaths) {
	    String path = uploadPaths; // 저장 경로 설정
	    
	    String newFileName = ""; // 업로드 되는 파일명
	    File dir = new File(path);
	    if(!dir.isDirectory()){
	        dir.mkdir();
	    }
	    
	    Iterator<String> files = multi.getFileNames();
	    Map<String, String> resMap = new HashMap<String, String>();
	    String fileName = "";
	    while(files.hasNext()){
	        String uploadFile = files.next();
	        MultipartFile mFile = multi.getFile(uploadFile);
	        fileName = mFile.getOriginalFilename();
	        newFileName = System.currentTimeMillis()+"."+fileName.substring(fileName.lastIndexOf(".")+1);
	        try {
	            mFile.transferTo(new File(path+newFileName));
	            resMap.put(newFileName, path+newFileName);
	        } catch (Exception e) {
	            logger.error("Exception", e);
	        }
	    }
	    return resMap;
	}


	@Override
	public HashMap<String, Object> applyAramex(HashMap<String, Object> parameters) throws Exception {
		
		return mapper.applyAramex(parameters);
	}


	@Override
	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectPodBlInfo(parameters);
	}
	
	@Override
	public ArrayList<HashMap<String, Object>>  aramexPodPage(String hawbNo)
			throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			
			TrackingClientInfo clientInfo = new TrackingClientInfo();
			clientInfo.setAccountCountryCode("KR");
			clientInfo.setAccountEntity("SEL");
			clientInfo.setAccountNumber("172813");
			clientInfo.setAccountPin("321321");
			clientInfo.setUserName("overseas2@aciexpress.net");
			clientInfo.setPassword("Aci5606!");
			clientInfo.setVersion("1.0");
			//shipments.setClientInfo(clientInfo);
			
			net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
			transaction.setReference1("001");
			
			TrackingShipmentRequest trakShip = new TrackingShipmentRequest();
			
			trakShip.setClientInfo(clientInfo);
			trakShip.setTransaction(transaction);
			
			String[] trakBL = new String[1];
			trakBL[0] = hawbNo;
			
			trakShip.setShipments(trakBL);
			
			TrackingService_1_0Proxy trackingProxy = new TrackingService_1_0Proxy(); 
			TrackingShipmentResponse rtnTracking = new TrackingShipmentResponse();
			rtnTracking = trackingProxy.trackShipments(trakShip); 
			TrackingResult[] trkRst = new TrackingResult[0];
			trkRst = rtnTracking.getTrackingResults()[0].getValue();
			
			for(int i= 0 ; i < trkRst.length; i++) {
				//System.out.println(rtnTracking.getTrackingResults()[i].getKey());	
				LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
				if(trkRst[i].getUpdateCode().equals("SH005")){
					//podDetatil.put("UpdateCode","S500");
					podDetatil.put("UpdateCode","SH005");
				}else {
					podDetatil.put("UpdateCode",trkRst[i].getUpdateCode());
				}
				podDetatil.put("UpdateDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
				podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
				podDetatil.put("UpdateDescription", trkRst[i].getUpdateDescription());
				podDetatil.put("ProblemCode",trkRst[i].getProblemCode()); 
				podDetatil.put("Comments", trkRst[i].getComments());
				podDetatailArray.add(podDetatil);
			}
			
 		}catch (Exception e) {
 			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
 			podDetatil.put("UpdateCode","ERR"); 
			podDetatil.put("UpdateDateTime", "");
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "");
			podDetatil.put("ProblemCode",e.getMessage()); 
			podDetatil.put("Comments", "");
			podDetatailArray.add(podDetatil);
		}
		
		return podDetatailArray;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>>  aramexPodPage2(String hawbNo) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			
			TrackingClientInfo clientInfo = new TrackingClientInfo();
			clientInfo.setAccountCountryCode("KR");
			clientInfo.setAccountEntity("SEL");
			clientInfo.setAccountNumber("172813");
			clientInfo.setAccountPin("321321");
			clientInfo.setUserName("overseas2@aciexpress.net");
			clientInfo.setPassword("Aci5606!");
			clientInfo.setVersion("1.0");
			//shipments.setClientInfo(clientInfo);
			
			net.aramex.ws.ShippingTrackAPI.v1.Transaction transaction = new net.aramex.ws.ShippingTrackAPI.v1.Transaction();
			transaction.setReference1("001");
			
			TrackingShipmentRequest trakShip = new TrackingShipmentRequest();
			
			trakShip.setClientInfo(clientInfo);
			trakShip.setTransaction(transaction);
			
			String[] trakBL = new String[1];
			trakBL[0] = hawbNo;
			
			trakShip.setShipments(trakBL);
			
			TrackingService_1_0Proxy trackingProxy = new TrackingService_1_0Proxy(); 
			TrackingShipmentResponse rtnTracking = new TrackingShipmentResponse();
			rtnTracking = trackingProxy.trackShipments(trakShip); 
			TrackingResult[] trkRst = new TrackingResult[0];
			trkRst = rtnTracking.getTrackingResults()[0].getValue();
			
			for(int i= 0 ; i < trkRst.length; i++) {
				//System.out.println(rtnTracking.getTrackingResults()[i].getKey());	
				LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
				if(trkRst[i].getUpdateCode().equals("SH005")){
					podDetatil.put("UpdateCode","S500");
				}else {
					podDetatil.put("UpdateCode",trkRst[i].getUpdateCode());
				}
				podDetatil.put("UpdateDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(trkRst[i].getUpdateDateTime().getTime()));
				podDetatil.put("UpdateLocation", trkRst[i].getUpdateLocation());
				podDetatil.put("UpdateDescription", trkRst[i].getUpdateDescription());
				podDetatil.put("ProblemCode",trkRst[i].getProblemCode()); 
				podDetatil.put("Comments", trkRst[i].getComments());
				podDetatailArray.add(podDetatil);
			}
			
 		}catch (Exception e) {
 			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
 			podDetatil.put("UpdateCode","ERR"); 
			podDetatil.put("UpdateDateTime", "");
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "");
			podDetatil.put("ProblemCode",e.getMessage()); 
			podDetatil.put("Comments", "");
			podDetatailArray.add(podDetatil);
		}
		
		return podDetatailArray;
	}


	@Override
	public void sendAramexWeightFtp() throws Exception {
		// TODO Auto-generated method stub
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        LocalDateTime currentTime = LocalDateTime.now();
	        String fileName = "";
	        // book 엘리먼트
	        Document doc = docBuilder.newDocument();
	        doc.setXmlStandalone(true); //standalone="no" 를 없애준다.
	        
	        Element infoLinkDocument = doc.createElement("InfoLinkDocument");
	        doc.appendChild(infoLinkDocument);
	        
	        Element accessRequest = doc.createElement("AccessRequest");
	        infoLinkDocument.appendChild(accessRequest);
	        
	        Element documentType = doc.createElement("DocumentType");
	        documentType.appendChild(doc.createTextNode("3"));
	        accessRequest.appendChild(documentType);
	        
	        Element entityID = doc.createElement("EntityID");
	        entityID.appendChild(doc.createTextNode("967"));
	        accessRequest.appendChild(entityID);
	        
	        Element entityPIN = doc.createElement("EntityPIN");
	        entityPIN.appendChild(doc.createTextNode("SEL967"));
	        accessRequest.appendChild(entityPIN);
	        
	        Element replyEmailAddress = doc.createElement("ReplyEmailAddress");
	        replyEmailAddress.appendChild(doc.createTextNode("MohdHamdan@aramex.com;Nurhashidalubis@aramex.com;darrels@aramex.com;marcus.chua@aramex.com;itsel2@aciexpress.net;itkr@aciexpress.net;acihq1@aciexpress.net;acihq5@aciexpress.net;overseas2@aciexpress.net;"));
	        accessRequest.appendChild(replyEmailAddress);
	        
	        Element notifyOnSuccess = doc.createElement("NotifyOnSuccess");
	        notifyOnSuccess.appendChild(doc.createTextNode("1"));
	        accessRequest.appendChild(notifyOnSuccess);
	        
	        Element reference1 = doc.createElement("Reference1");
	        reference1.appendChild(doc.createTextNode("SIN WEIGHT EXCEL UPDATE by Marcus"));
	        accessRequest.appendChild(reference1);
	        
	        ///for문 hawb정보 (그날 날짜)
	        ArrayList<HawbVO> hawbList = new ArrayList<HawbVO>(); 
	        hawbList = mapper.selectAramexHawbList();
	        for(int i =0; i<hawbList.size();i++) {
	        	hawbList.get(i).getWidth();
	        	Element hawbUpdate = doc.createElement("HAWBUpdate");
	            infoLinkDocument.appendChild(hawbUpdate);
	            
	            Element hawbNumber = doc.createElement("HAWBNumber");
	            hawbNumber.appendChild(doc.createTextNode(hawbList.get(i).getHawbNo()));
	            hawbUpdate.appendChild(hawbNumber);
	            
	            Element hawbOriginEntity = doc.createElement("HAWBOriginEntity");
	            hawbOriginEntity.appendChild(doc.createTextNode(""));
	            hawbUpdate.appendChild(hawbOriginEntity);
	            
	            Element updateEntity = doc.createElement("UpdateEntity");
	            updateEntity.appendChild(doc.createTextNode("SEL"));
	            hawbUpdate.appendChild(updateEntity);
	            
	            Element problemCode = doc.createElement("ProblemCode");
	            problemCode.appendChild(doc.createTextNode(""));
	            hawbUpdate.appendChild(problemCode);
	            
	            Element piNumber = doc.createElement("PINumber");
	            piNumber.appendChild(doc.createTextNode("SH368"));
	            hawbUpdate.appendChild(piNumber);
	            
	            Element productGroup = doc.createElement("ProductGroup");
	            productGroup.appendChild(doc.createTextNode("EXP"));
	            hawbUpdate.appendChild(productGroup);
	            
	            Element pieces = doc.createElement("Pieces");
	            pieces.appendChild(doc.createTextNode("1"));
	            hawbUpdate.appendChild(pieces);
	            
	            Element comment1 = doc.createElement("Comment1");
	            String msg = hawbList.get(i).getWta()+","+hawbList.get(i).getWtc()+",KG";
	            comment1.appendChild(doc.createTextNode(msg));
	            hawbUpdate.appendChild(comment1);
	            
	            msg = "|"+hawbList.get(i).getWidth()+","+hawbList.get(i).getLength()+","+hawbList.get(i).getHeight()+","+hawbList.get(i).getWta()+"|";
	            Element comment2 = doc.createElement("Comment2");
	            comment2.appendChild(doc.createTextNode(msg));
	            hawbUpdate.appendChild(comment2);
	            
	            Element actionDate = doc.createElement("ActionDate");
	            actionDate.appendChild(doc.createTextNode(hawbList.get(i).getWDate()));
	            hawbUpdate.appendChild(actionDate);
	            
	            Element sourceID = doc.createElement("SourceID");
	            sourceID.appendChild(doc.createTextNode("2"));
	            hawbUpdate.appendChild(sourceID);
	            if((((i+1)%500) == 0) || (i==(hawbList.size()-1))) {//500개 끊기
	            	fileName = "";
	            	TransformerFactory transformerFactory = TransformerFactory.newInstance();

	                Transformer transformer = transformerFactory.newTransformer();
	                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
	                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
	                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행

	                DOMSource source = new DOMSource(doc);
	                currentTime = LocalDateTime.now();
	                String times = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	                fileName = "ARAMEX_WEIGHT_"+times+".xml";
	                File file = new File(realFilePath+"weightXml/"+fileName);
	                StreamResult result = new StreamResult(new FileOutputStream(file));
	                transformer.transform(source, result);
	            }
	        }
	        fileName= "";
	        File dir = new File(realFilePath+"weightXml/");
	        File files[] = dir.listFiles();
	        
	        for (int i = 0; i < files.length; i++) {
	            //System.out.println("file: " + files[i].getName());
	        	//2023.05.25 이전 접속 경로 
	            //sendSFTPServcer("Infolink.aramex.net",22,"ACIExpress","nN26B5Qe","/in/DocType3/",realFilePath+"weightXml/",files[i].getName());
	        	//sendSFTPServcer("sftp.aramex.cloud",22,"aciexpress","Xk9ubzp4pcLbprn","/in/DocType3/",realFilePath+"weightXml/",files[i].getName());
	        	// sftp 접속 오류로 api 전송으로 변경
	        	sendXmlToServer("https://infolink.aramex.net/post.aspx", "ACIExpress", "eJ79BxsE", realFilePath+"weightXml/", files[i].getName());
	        }
	        for (int i = 0; i < files.length; i++) {
	            //System.out.println("file: " + files[i].getName());
	        	files[i].delete();
	        }
	        
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
        
	}
	
	private void sendXmlToServer(String url, String id, String password, String filePath, String fileName) {

		try {
			String xmlData = new String(Files.readAllBytes(Paths.get(filePath+fileName)), StandardCharsets.UTF_8);
			
			URL connUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) connUrl.openConnection();
			
			String auth = id + ":" + password;
	        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
	        
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream outputStream = conn.getOutputStream();
			
			byte[] b = xmlData.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
			
			// 응답 코드 확인
	        int responseCode = conn.getResponseCode();
	        System.out.println("Response Code: " + responseCode);

	        // 응답 본문 읽기
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
	            String line;
	            StringBuilder response = new StringBuilder();

	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }

	            // 응답 출력
	            System.out.println("Response Body:\n" + response.toString());
	        }
	        // 연결 종료
	        conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean sendSFTPServcer(String url, int port, String id, String password, String folder, String localPath, String fileName) throws Exception {
		JSch jsch = new JSch();
		
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		try {
			
			session = jsch.getSession(id, url, port);
			
			session.setPassword(password);
			
			Properties config = new Properties();


			config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.connect();
            
            channel = session.openChannel("sftp");
            channel.connect();
            
            channelSftp = (ChannelSftp) channel;
            
            File file = new File(localPath+fileName);
            
            FileInputStream in = null;
            
            in = new FileInputStream(file);
            
            channelSftp.cd(folder);
            channelSftp.put(in,fileName);
            in.close();
            channelSftp.exit();
            channel.disconnect();
            session.disconnect();

			
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
		
		return true;	
	}
	
	public boolean sendFtpServer(String ip, int port, String id, String password, String folder, String localPath, String file) {
		boolean isSuccess = false;
        FTPClient ftp = null;
        
        int reply;
        try {
            ftp = new FTPClient();
            ftp.connect(ip, port);
            System.out.println("Connected to " + ip + " on "+ftp.getRemotePort());
            
            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            
            if(!ftp.login(id, password)) {
                ftp.logout();
                throw new Exception("ftp 서버에 로그인하지 못했습니다.");
            }
            
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();

            try{
                ftp.makeDirectory(folder);
            }catch(Exception e){
                logger.error("Exception", e);
            }
            ftp.changeWorkingDirectory(folder);
            
            
            //ftp서버에 한글파일을 쓸때 한글깨짐 방지
            String tempFileName = new String(file.getBytes("utf-8"),"iso_8859_1");
            String sourceFile = localPath + file;        
            File uploadFile = new File(sourceFile);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(uploadFile);
                 //tempFileName 업로드 될 타겟의 풀 경로가 들어가야 함.  ex) /A/A01/A001/aa.zip   파일명만 들어갈경우 unix에서 전송 실패하는 경우가 생김.
                isSuccess = ftp.storeFile(tempFileName, fis);  
            } catch(IOException e) {
                logger.error("Exception", e);
                isSuccess = false;
            } finally {
                if (fis != null) {
                    try {fis.close(); } catch(IOException e) {}
                }
            }//end try
            
            ftp.logout();
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            if (ftp != null && ftp.isConnected()) {
                try { ftp.disconnect(); } catch (IOException e) {}
            }
        }
        return isSuccess;
	}

	
}
