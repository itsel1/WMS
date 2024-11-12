package com.example.temp.api.logistics.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.common.vo.ComnVO;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.CommonUtils;
import com.example.temp.api.Item;
import com.example.temp.api.Order;
import com.example.temp.api.Tracking;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.dto.CJParameter;
import com.google.gson.Gson;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Service
public class CJHandler {
	
	@Autowired
	LogisticsMapper logisticsMapper;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	CommonUtils utils;
	
	@Value("${filePath}")
	String filePath;
	
	ComnVO comnS3Info;
	AmazonS3 amazonS3;
	
	private static final String CUST_ID = "30534487";
	private static final String BIZ_REG_NUM = "1058152457";
	private static final String HOST = "https://dxapi.cjlogistics.com:5052";
	//private static final String HOST = "https://dxapi-dev.cjlogistics.com:5054";
	
	public CJInfo checkCJTokenInfo() {
		
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("custId", CUST_ID);
		sqlParams.put("bizNo", BIZ_REG_NUM);
		
		CJInfo cjInfo = new CJInfo();
		cjInfo = logisticsMapper.selectCJInfo(sqlParams);

		if ("Y".equals(cjInfo.getIsTokenExpired())) {
			cjInfo = getOneDayTokenInfo(cjInfo); 
		}
		
		return cjInfo;

	}

	
	public CJInfo getOneDayTokenInfo(CJInfo cjInfo) {
		
		String path = "/ReqOneDayToken";
		HashMap<String, Object> requestBodyMap = new HashMap<>();
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("CUST_ID", CUST_ID);
		dataMap.put("BIZ_REG_NUM", BIZ_REG_NUM);
		requestBodyMap.put("DATA", dataMap);
		
		String requestBody = utils.convertStringToMap(requestBodyMap);
		String httpUrl = HOST + path;
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders(null));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String resultCode = jsonObject.optString("RESULT_CD");
			String resultMsg = jsonObject.optString("RESULT_DETAIL");
			
			if ("S".equals(resultCode)) {
				
				try {
					
					JSONObject dataObject = jsonObject.getJSONObject("DATA");
					String tokenNum = dataObject.optString("TOKEN_NUM");
					String tokenExprtnDtm = dataObject.optString("TOKEN_EXPRTN_DTM");
					
					SimpleDateFormat beforeFmt = new SimpleDateFormat("yyyyMMddHHmmss");
					SimpleDateFormat afterFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date fmtDate = beforeFmt.parse(tokenExprtnDtm);
					String fmtTokenExpiryDate = afterFmt.format(fmtDate);
				
					if (!tokenNum.equals(cjInfo.getTokenNo())) {
						cjInfo.setTokenEDate(fmtTokenExpiryDate);
						cjInfo.setTokenNo(tokenNum);
						
						logisticsMapper.updateCJTokenInfo(cjInfo);
					}
					
				} catch (ParseException e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println(resultMsg);
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
		}
		
		return cjInfo;
	}
	
	
	public void registBooking(Order order, String tokenNum) {
		
		CJParameter cjParameter = new CJParameter();
		cjParameter.setCustId(CUST_ID);
		cjParameter.setNno(order.getNno());
		cjParameter.setHawbNo(order.getHawbNo());
		cjParameter.setUserId(order.getUserId());
		
		String path = "/RegBook";
		String httpUrl = HOST + path;
		
		String requestBody = utils.convertStringToMap(getRegistBookingRequestBody(tokenNum, order));
		System.out.println(requestBody);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders(tokenNum));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String resultCd = jsonObject.optString("RESULT_CD").toUpperCase();
			String resultDetail = jsonObject.optString("RESULT_DETAIL");
			
			cjParameter.setResultCd(resultCd);
			cjParameter.setResultMsg(resultDetail);
			
		} else {
			cjParameter.setResultCd("E");
			cjParameter.setResultMsg(responseMap.get("status_msg"));
		}
		
		if ("S".equals(cjParameter.getResultCd())) {
			logisticsMapper.insertCJSuccess(cjParameter);
			
		} else {
			logisticsMapper.insertCjFail(cjParameter);
		}
		
	}
	
	
	private HashMap<String, Object> getRegistReturnBookingRequestBody(String tokenNum, Order order) {
		HashMap<String, Object> requestBody = new HashMap<>();
		ArrayList<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String, Object> data = new LinkedHashMap<>();
		
		String nno = order.getNno();

		String shipperZip = order.getShipperZip().replaceAll("[^0-9]", "");
		String shipperTel = order.getShipperTel().replaceAll("[^0-9]", "");
		String shipperHp = order.getShipperHp().replaceAll("[^0-9]", "");
		String[] sendrTelNo = getTelNumberSplit(shipperTel);
		String[] sendrCellNo = getTelNumberSplit(shipperHp);
		
		String cneeZip = order.getCneeZip().replaceAll("[^0-9]", "");
		String cneeTel = order.getCneeTel().replaceAll("[^0-9]", "");
		String cneeHp = order.getCneeHp().replaceAll("[^0-9]", "");
		String[] rcvrTelNo = getTelNumberSplit(cneeTel);
		String[] rcvrCellNo = getTelNumberSplit(cneeHp);
		
		String shipperAddr = order.getShipperAddr().trim();
		String shipperAddrDetail = order.getShipperAddrDetail().trim();
		String sendrAddr;
		String sendrDetailAddr;
		
		if (shipperAddrDetail == null) {
			shipperAddrDetail = "";
		}
		
		if (shipperAddr.length() > 100) {
			sendrAddr = shipperAddr.substring(0, 100);
			sendrDetailAddr = shipperAddr.substring(100) + (shipperAddrDetail.isEmpty() ? "" : " " + shipperAddrDetail);
		} else {
			int lastSpaceIndex = shipperAddr.lastIndexOf(" ");
			if (lastSpaceIndex == -1) {
				lastSpaceIndex = shipperAddr.length() - 1;
			}
			sendrAddr = shipperAddr.substring(0, lastSpaceIndex);
			sendrDetailAddr = shipperAddr.substring(lastSpaceIndex + 1) + (shipperAddrDetail.isEmpty() ? "" : " " + shipperAddrDetail);
		}

		String cneeAddr = order.getCneeAddr().trim();
		String cneeAddrDetail = order.getCneeAddrDetail().trim();
		String rcvrAddr;
		String rcvrDetailAddr;
		
		if (cneeAddrDetail == null) {
			cneeAddrDetail = "";
		}
		
		if (cneeAddr.length() > 100) {
			rcvrAddr = cneeAddr.substring(0, 100);
			rcvrDetailAddr = cneeAddr.substring(100) + (cneeAddrDetail.isEmpty() ? "" : " " + cneeAddrDetail);
		} else {
			int lastSpaceIndex = cneeAddr.lastIndexOf(" ");
			if (lastSpaceIndex == -1) {
				lastSpaceIndex = cneeAddr.length() - 1;
			}
			rcvrAddr = cneeAddr.substring(0, lastSpaceIndex);
			rcvrDetailAddr = cneeAddr.substring(lastSpaceIndex + 1) + (cneeAddrDetail.isEmpty() ? "" : " " + cneeAddrDetail);
		}
		
		
		String rcptYmd = utils.getDateTime("yyyyMMdd");
		String rcptDv = "02";
		String workDvCd = "01";
		String reqDvCd = "01";
		String mpckKey = rcptYmd + "_" + CUST_ID + "_" + order.getNno();
		String calDvCd = "01";
		String frtDvCd = "03";
		String cntrItemCd = "01";
		String boxTypeCd = "01";
		String dlvDv = "01";
		String prtSt = "01";
		
		
		data.put("TOKEN_NUM", tokenNum);
		data.put("CUST_ID", CUST_ID);
		data.put("RCPT_YMD", rcptYmd);
		data.put("CUST_USE_NO", nno);
		data.put("RCPT_DV", rcptDv);
		data.put("WORK_DV_CD", workDvCd);
		data.put("REQ_DV_CD", reqDvCd);
		data.put("MPCK_KEY", mpckKey);
		data.put("CAL_DV_CD", calDvCd);
		data.put("FRT_DV_CD", frtDvCd);
		data.put("CNTR_ITEM_CD", cntrItemCd);
		data.put("BOX_TYPE_CD", boxTypeCd);
		data.put("BOX_QTY", 1);
		data.put("CUST_MGMT_DLCM_CD", CUST_ID);
		data.put("SENDR_NM", order.getShipperName());
		data.put("SENDR_TEL_NO1", sendrTelNo[0]);
		data.put("SENDR_TEL_NO2", sendrTelNo[1]);
		data.put("SENDR_TEL_NO3", sendrTelNo[2]);
		data.put("SENDR_CELL_NO1", sendrCellNo[0]);
		data.put("SENDR_CELL_NO2", sendrCellNo[1]);
		data.put("SENDR_CELL_NO3", sendrCellNo[2]);
		data.put("SENDR_ZIP_NO", shipperZip);
		data.put("SENDR_ADDR", sendrAddr);
		data.put("SENDR_DETAIL_ADDR", sendrDetailAddr);
		data.put("RCVR_NM", "ACI월드와이드");
		data.put("RCVR_TEL_NO1", "070");
		data.put("RCVR_TEL_NO2", "4436");
		data.put("RCVR_TEL_NO3", "6514");
		data.put("RCVR_CELL_NO1", "");
		data.put("RCVR_CELL_NO2", "");
		data.put("RCVR_CELL_NO3", "");
		data.put("RCVR_ZIP_NO", "22839");
		data.put("RCVR_ADDR", "인천광역시 서구 백범로 832 (가좌동)");
		data.put("RCVR_DETAIL_ADDR", "AFC");
		data.put("INVC_NO", "");
		data.put("PRT_ST", prtSt);
		data.put("REMARK_1", "");
		data.put("DLV_DV", dlvDv);
		
		for (int i = 0; i < order.getItemList().size(); i++) {
			HashMap<String, Object> itemData = new HashMap<>();
			Item item = order.getItemList().get(i);
			itemData.put("MPCK_SEQ", item.getSubNo());
			itemData.put("GDS_NM", item.getItemDetail());
			itemData.put("GDS_QTY", item.getItemCnt());
			
			array.add(itemData);
		}
		
		data.put("ARRAY", array);
		requestBody.put("DATA", data);
		
		return requestBody;
	}


	private HashMap<String, Object> getRegistBookingRequestBody(String tokenNum, Order order) {
		HashMap<String, Object> requestBody = new HashMap<>();
		ArrayList<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String, Object> data = new LinkedHashMap<>();
		
		String nno = order.getNno();
		int boxQty = order.getBoxCnt();
		
		String shipperZip = order.getShipperZip().replaceAll("[^0-9]", "");
		String shipperTel = order.getShipperTel().replaceAll("[^0-9]", "");
		String shipperHp = order.getShipperHp().replaceAll("[^0-9]", "");
		String[] sendrTelNo = getTelNumberSplit(shipperTel);
		String[] sendrCellNo = getTelNumberSplit(shipperHp);
		
		String cneeZip = order.getCneeZip().replaceAll("[^0-9]", "");
		String cneeTel = order.getCneeTel().replaceAll("[^0-9]", "");
		String cneeHp = order.getCneeHp().replaceAll("[^0-9]", "");
		String[] rcvrTelNo = getTelNumberSplit(cneeTel);
		String[] rcvrCellNo = getTelNumberSplit(cneeHp);
		
		String shipperAddr = order.getShipperAddr().trim();
		String shipperAddrDetail = order.getShipperAddrDetail().trim();
		String sendrAddr;
		String sendrDetailAddr;
		
		if (shipperAddrDetail == null) {
			shipperAddrDetail = "";
		}
		
		if (shipperAddr.length() > 100) {
			sendrAddr = shipperAddr.substring(0, 100);
			sendrDetailAddr = shipperAddr.substring(100) + (shipperAddrDetail.isEmpty() ? "" : " " + shipperAddrDetail);
		} else {
			int lastSpaceIndex = shipperAddr.lastIndexOf(" ");
			if (lastSpaceIndex == -1) {
				lastSpaceIndex = shipperAddr.length() - 1;
			}
			sendrAddr = shipperAddr.substring(0, lastSpaceIndex);
			sendrDetailAddr = shipperAddr.substring(lastSpaceIndex + 1) + (shipperAddrDetail.isEmpty() ? "" : " " + shipperAddrDetail);
		}

		String cneeAddr = order.getCneeAddr().trim();
		String cneeAddrDetail = order.getCneeAddrDetail().trim();
		String rcvrAddr;
		String rcvrDetailAddr;
		
		if (cneeAddrDetail == null) {
			cneeAddrDetail = "";
		}
		
		if (cneeAddr.length() > 100) {
			rcvrAddr = cneeAddr.substring(0, 100);
			rcvrDetailAddr = cneeAddr.substring(100) + (cneeAddrDetail.isEmpty() ? "" : " " + cneeAddrDetail);
		} else {
			int lastSpaceIndex = cneeAddr.lastIndexOf(" ");
			if (lastSpaceIndex == -1) {
				lastSpaceIndex = cneeAddr.length() - 1;
			}
			rcvrAddr = cneeAddr.substring(0, lastSpaceIndex);
			rcvrDetailAddr = cneeAddr.substring(lastSpaceIndex + 1) + (cneeAddrDetail.isEmpty() ? "" : " " + cneeAddrDetail);
		}
		
		
		String rcptYmd = utils.getDateTime("yyyyMMdd");
		String rcptDv = "01";
		String workDvCd = "01";
		String reqDvCd = "01";
		String mpckKey = rcptYmd + "_" + CUST_ID + "_" + order.getHawbNo();
		String calDvCd = "01";
		String frtDvCd = "03";
		String cntrItemCd = "01";
		String boxTypeCd = "01";
		String dlvDv = "01";
		String prtSt = "02";
		
		
		data.put("TOKEN_NUM", tokenNum);
		data.put("CUST_ID", CUST_ID);
		data.put("RCPT_YMD", rcptYmd);
		data.put("CUST_USE_NO", nno);
		data.put("RCPT_DV", rcptDv);
		data.put("WORK_DV_CD", workDvCd);
		data.put("REQ_DV_CD", reqDvCd);
		data.put("MPCK_KEY", mpckKey);
		data.put("CAL_DV_CD", calDvCd);
		data.put("FRT_DV_CD", frtDvCd);
		data.put("CNTR_ITEM_CD", cntrItemCd);
		data.put("BOX_TYPE_CD", boxTypeCd);
		data.put("BOX_QTY", boxQty);
		data.put("CUST_MGMT_DLCM_CD", CUST_ID);
		data.put("SENDR_NM", order.getShipperName());
		data.put("SENDR_TEL_NO1", sendrTelNo[0]);
		data.put("SENDR_TEL_NO2", sendrTelNo[1]);
		data.put("SENDR_TEL_NO3", sendrTelNo[2]);
		data.put("SENDR_CELL_NO1", sendrCellNo[0]);
		data.put("SENDR_CELL_NO2", sendrCellNo[1]);
		data.put("SENDR_CELL_NO3", sendrCellNo[2]);
		data.put("SENDR_ZIP_NO", shipperZip);
		data.put("SENDR_ADDR", sendrAddr);
		data.put("SENDR_DETAIL_ADDR", sendrDetailAddr);
		data.put("RCVR_NM", order.getCneeName());
		data.put("RCVR_TEL_NO1", rcvrTelNo[0]);
		data.put("RCVR_TEL_NO2", rcvrTelNo[1]);
		data.put("RCVR_TEL_NO3", rcvrTelNo[2]);
		data.put("RCVR_CELL_NO1", rcvrCellNo[0]);
		data.put("RCVR_CELL_NO2", rcvrCellNo[1]);
		data.put("RCVR_CELL_NO3", rcvrCellNo[2]);
		data.put("RCVR_ZIP_NO", cneeZip);
		data.put("RCVR_ADDR", rcvrAddr);
		data.put("RCVR_DETAIL_ADDR", rcvrDetailAddr);
		data.put("INVC_NO", order.getHawbNo());
		data.put("PRT_ST", prtSt);
		data.put("REMARK_1", order.getDlvReqMsg());
		data.put("DLV_DV", dlvDv);
		
		for (int i = 0; i < order.getItemList().size(); i++) {
			HashMap<String, Object> itemData = new HashMap<>();
			Item item = order.getItemList().get(i);
			itemData.put("MPCK_SEQ", item.getSubNo());
			itemData.put("GDS_NM", item.getItemDetail());
			itemData.put("GDS_QTY", item.getItemCnt());
			
			array.add(itemData);
		}
		
		data.put("ARRAY", array);
		requestBody.put("DATA", data);
		
		return requestBody;
	}
	
		
	public ArrayList<Tracking> requestOneGoodsTracking(String tokenNum, String hawbNo) {
		
		String path = "/ReqOneGdsTrc";
		String httpUrl = HOST + path;

		HashMap<String, Object> requestBodyMap = new HashMap<>();
		HashMap<String, Object> data = new HashMap<>();
		
		data.put("TOKEN_NUM", tokenNum);
		data.put("CLNTNUM", CUST_ID);
		data.put("INVC_NO", hawbNo);
		
		requestBodyMap.put("DATA", data);
		
		String requestBody = utils.convertStringToMap(requestBodyMap);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders(tokenNum));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			String resultCd = jsonObject.optString("RESULT_CD");
			
			if ("S".equals(resultCd)) {
				JSONArray dataList = jsonObject.getJSONArray("DATA");
				ArrayList<Tracking> cjTrkList = getCJTrackingList(dataList);
				return cjTrkList;
			} else {
				System.err.println(jsonObject.optString("RESULT_DETAIL"));
				return null;
			}
			
		} else {
			System.err.println(responseMap.get("status_msg"));
			return null;
		}
	}
	
	
	private ArrayList<Tracking> getCJTrackingList(JSONArray jsonArray) {
		ArrayList<Tracking> trackingList = new ArrayList<>();
		
		for (int i = jsonArray.length() - 1; i >= 0; i--) {
			JSONObject dataObject = jsonArray.getJSONObject(i);
			Tracking trkInfo = new Tracking();
			String location = dataObject.optString("DEALT_BRAN_NM");
			String dateTime = dataObject.optString("SCAN_YMD") + " " + dataObject.optString("SCAN_HOUR");
			String code = dataObject.optString("CRG_ST");
			String description = dataObject.optString("CRG_ST_NM", getPackageStatusCode(code));
			String dealtEmpNm = dataObject.optString("DEALT_EMP_NM","***");
			
			if ("01".equals(code)) {
				trkInfo.setStatusCode("300");
			} else if ("11".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("12".equals(code)) {
				trkInfo.setStatusCode("300");
			} else if ("41".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("42".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("82".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("84".equals(code)) {
				trkInfo.setStatusCode("500");
			} else if ("91".equals(code)) {
				trkInfo.setStatusCode("600");
			} else {
				trkInfo.setStatusCode("300");
			}
			
			trkInfo.setDescription(description);
			trkInfo.setDateTime(dateTime);
			trkInfo.setLocation(location);
			trkInfo.setDescriptionKor(description);
			trkInfo.setLocationKor(location + " (담당택배원 : " + dealtEmpNm + ")");
			trackingList.add(trkInfo);
		}
		
		return trackingList;
	}


	public CJParameter requestRefinedAddress(String tokenNum, String address) {
		
		String path = "/ReqAddrRfn";
		String httpUrl = HOST + path;
		
		CJParameter cjParameter = new CJParameter();
		HashMap<String, Object> requestBodyMap = new HashMap<>();
		HashMap<String, Object> data = new HashMap<>();
		
		data.put("CLNTNUM", CUST_ID);
		data.put("CLNTMGMCUSTCD", CUST_ID);
		data.put("ADDRESS", address);
		data.put("TOKEN_NUM", tokenNum);
		
		requestBodyMap.put("DATA", data);
		
		String requestBody = utils.convertStringToMap(requestBodyMap);
		
		HashMap<String, String> responseMap = utils.apiPost(httpUrl, requestBody, buildApiHeaders(tokenNum));
		
		if ("success".equals(responseMap.get("status"))) {
			JSONObject jsonObject = new JSONObject(responseMap.get("responseBody"));
			System.out.println(jsonObject);
			JSONObject dataObject = jsonObject.getJSONObject("DATA");
			
			cjParameter.setResultCd(jsonObject.optString("RESULT_CD").toUpperCase());
			cjParameter.setResultMsg(jsonObject.optString("RESULT_DETAIL"));
			cjParameter.setClsfCd(dataObject.optString("CLSFCD"));
			cjParameter.setSubClsfCd(dataObject.optString("SUBCLSFCD"));
			cjParameter.setClsfAddr(dataObject.optString("CLSFADDR"));
			cjParameter.setCllDlvBranNm(dataObject.optString("CLLDLVBRANNM"));
			cjParameter.setCllDlvEmpNm(dataObject.optString("CLLDLVEMPNM"));
			cjParameter.setCllDlvEmpNickNm(dataObject.optString("CLLDLVEMPNICKNM"));
			cjParameter.setRspsDiv(dataObject.optString("RSPSDIV"));
			cjParameter.setP2pCd(dataObject.optString("P2PCD"));

		} else {
			cjParameter.setResultCd("E");
			cjParameter.setResultMsg(responseMap.get("status_msg"));
		}
		
		return cjParameter;
		
	}
	
	
	public void createCJLabel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String orderType) {
		
		try {
			
			
			CJInfo cjInfo = checkCJTokenInfo();
			String tokenNum = cjInfo.getTokenNo();
			String tokenEDate = cjInfo.getTokenEDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
			LocalDateTime tokenExpiryDate = LocalDateTime.parse(tokenEDate, formatter);
			
			
			String pdfPath = filePath + "pdf";
			String barcodePath = pdfPath + "/barcode/";
			File dir = new File(barcodePath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			
			float mm = 2.83465f;
			int totalPage = 0;
			
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String fontPath = cssResource.getURI().getPath().substring(0, cssResource.getURI().getPath().lastIndexOf("/"));
			InputStream fontStream = new FileInputStream(fontPath + "/static/fonts/NotoSansKR-Bold.ttf");
			
			PDDocument doc = new PDDocument();
			
			PDType0Font fontBold = PDType0Font.load(doc, fontStream);
			
			for (int index = 0; index < orderNnoList.size(); index++) {
				HashMap<String, Object> sqlParams = new HashMap<>();
				sqlParams.put("nno", orderNnoList.get(index));
				Order order = logisticsMapper.selectCJLabelData(sqlParams);
				order.dncryptData();
				
				
				LocalDateTime currentTime = LocalDateTime.now();
				if (tokenExpiryDate.isBefore(currentTime) || tokenExpiryDate.isBefore(currentTime.plusMinutes(30))) {
					cjInfo = getOneDayTokenInfo(cjInfo);
					tokenNum = cjInfo.getTokenNo();
					tokenExpiryDate = LocalDateTime.parse(cjInfo.getTokenEDate(), formatter);
				}
				
				
				String hawbNo = order.getHawbNo();
				String hawbNoFmt = hawbNo.substring(0,4) + "-" + hawbNo.substring(4,8) + "-" + hawbNo.substring(8,12);
				
				String shipperTel = order.getShipperTel().replaceAll("[^0-9]", "");
				String[] sendrTelNo = getTelNumberSplit(shipperTel);
				String shipperName = order.getShipperName();
				String shipperAddr = order.getShipperAddr().trim();
				if (!"".equals(order.getShipperAddrDetail())) {
					shipperAddr += " " + order.getShipperAddrDetail().trim();
				}
				
				String cneeTel = order.getCneeTel().replaceAll("[^0-9]", "");
				String cneeHp = order.getCneeHp().replaceAll("[^0-9]", "");
				String[] rcvrTelNo = getTelNumberSplit(cneeTel);
				String[] rcvrCellNo = getTelNumberSplit(cneeHp);
				String cneeName = order.getCneeName().substring(0,order.getCneeName().length()-1) + "*";
				String cneeAddr = order.getCneeAddr().trim();
				if (!"".equals(order.getCneeAddrDetail())) {
					cneeAddr += " " + order.getCneeAddrDetail().trim();
				}
				
				CJParameter cjParameter = requestRefinedAddress(tokenNum, cneeAddr);
				
				if ("S".equals(cjParameter.getResultCd())) {
					
					String cllDlvText = cjParameter.getCllDlvBranNm() + "-" + cjParameter.getCllDlvEmpNickNm();
					
					for (int subIndex = 0; subIndex < order.getBoxCnt(); subIndex++) {
						
						int outputCnt = subIndex + 1;
						String outputStr = String.valueOf(outputCnt)+"/"+order.getBoxCnt();
								
						PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
						PDPage blankPage = new PDPage(pageSize);
						doc.addPage(blankPage);
						PDPage page = doc.getPage(totalPage);
						PDPageContentStream cs = new PDPageContentStream(doc, page);
						
						float xStart = 0;
						float xEnd = pageSize.getWidth();
						float yEnd = pageSize.getHeight();
						
						
						cs.beginText();
						cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
						cs.setFont(fontBold, 12);
						cs.showText(hawbNoFmt);
						cs.endText();
						
						cs.beginText();
						cs.newLineAtOffset(xEnd - 45*mm, yEnd - 7*mm);
						cs.setFont(fontBold, 8);
						cs.showText(outputStr);
						cs.endText();
						
						String clsfCd1 = cjParameter.getClsfCd().substring(0,1);
						String clsfCd2 = cjParameter.getClsfCd().substring(1);
						
						Barcode dlvEmpBarcode = BarcodeFactory.createCode128A(cjParameter.getClsfCd());
						float barHeight = 30*mm;
						dlvEmpBarcode.setBarHeight((int)barHeight);
						dlvEmpBarcode.setDrawingQuietSection(false);
						File dlvEmpBarcodeFile = new File(barcodePath + hawbNo + "_" + cjParameter.getClsfCd() + ".jpeg");
						BarcodeImageHandler.saveJPEG(dlvEmpBarcode, dlvEmpBarcodeFile);
						PDImageXObject dlvEmpBarcodeImage = PDImageXObject.createFromFileByContent(dlvEmpBarcodeFile, doc);

						cs.drawImage(dlvEmpBarcodeImage, xStart + 5*mm, yEnd - 25*mm, 30*mm, 15*mm);
						
						cs.beginText();
						cs.newLineAtOffset(xStart + 37*mm, yEnd - 25*mm);
						cs.setFont(fontBold, 34);
						cs.showText(clsfCd1);
						cs.endText();
						
						float textWidth = (fontBold.getStringWidth(clsfCd1) / 1000) * 34;
						
						cs.beginText();
						cs.newLineAtOffset(xStart + (37*mm+textWidth), yEnd - 25*mm);
						cs.setFont(fontBold, 50);
						cs.showText(clsfCd2);
						cs.endText();
						
						textWidth = textWidth + (fontBold.getStringWidth(clsfCd2) / 1000) * 50;
						
						cs.beginText();
						cs.newLineAtOffset(xStart + (38*mm+textWidth), yEnd - 22*mm);
						cs.setFont(fontBold, 34);
						cs.showText("-"+cjParameter.getSubClsfCd());
						cs.endText();
						
						textWidth = textWidth + (fontBold.getStringWidth("-"+cjParameter.getSubClsfCd()) / 1000) * 34;
						
						cs.beginText();
						cs.newLineAtOffset(xStart + (40*mm+textWidth), yEnd - 23*mm);
						cs.setFont(fontBold, 24);
						cs.showText(cjParameter.getRspsDiv());
						cs.endText();
						
						
						String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
						if (cneeHp.length() != 0) {
							cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
						}
						
						cs.beginText();
						cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
						cs.setFont(fontBold, 10);
						cs.showText(cneeInfo);
						cs.endText();
						
						
						Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
						barHeight = 19*mm;
						hawbNoBarcode.setBarHeight((int)barHeight);
						hawbNoBarcode.setDrawingQuietSection(false);
						File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
						BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
						PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
						
						cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
						
						
						float startX = xStart + 6*mm;
						float startY = yEnd - 35*mm;
						
						cs.beginText();
						cs.newLineAtOffset(startX, startY);
						cs.setFont(fontBold, 9);
						cs.setLeading(10);


						String[] addressWords = cneeAddr.split(" ");
						StringBuilder line = new StringBuilder();
						for (String word : addressWords) {
							String tempLine = line + (line.length() == 0 ? "" : " ") + word;
							float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
							if (tempWidth > 107*mm) {
								cs.showText(line.toString());
								cs.newLine();
								line = new StringBuilder(word);
							} else {
								line.append(line.length() == 0 ? "" : " ").append(word);
							}
						}
						
						if (line.length() > 0) {
							cs.showText(line.toString());
						}
						
						cs.endText();
						
						cs.beginText();
						cs.newLineAtOffset(startX, yEnd - 46*mm);
						cs.setFont(fontBold, 24);
						cs.showText(cjParameter.getClsfAddr());
						cs.endText();
						
						
						cs.beginText();
						cs.newLineAtOffset(startX, yEnd - 49*mm);
						cs.setFont(fontBold, 7);
						cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
						cs.setLeading(7.5f);
						cs.newLine();
						cs.setFont(fontBold, 8);
						cs.showText(shipperAddr);
						cs.endText();

						
						cs.beginText();
						cs.newLineAtOffset(xStart + 69*mm, yEnd - 50*mm);
						cs.setFont(fontBold, 10);
						cs.showText("극소"+order.getBoxCnt());
						cs.endText();
						
						cs.beginText();
						cs.newLineAtOffset(xEnd - 22*mm, yEnd - 50*mm);
						cs.setFont(fontBold, 10);
						cs.showText("0");
						cs.endText();
						
						cs.beginText();
						cs.newLineAtOffset(xEnd - 10*mm, yEnd - 50*mm);
						cs.setFont(fontBold, 10);
						cs.showText("신용");
						cs.endText();
						
						
						float itemDetailMaxWidth = 108*mm;
						int fontSize = 9;
						float yCurrent = yEnd - 58*mm;
						float lineHeight = fontSize + 2;

						String[] itemDetails = order.getItemDetail().split("\\|");
						String[] itemCnts = order.getItemCnt().split("\\|");
						float itemLimitLine = 15*mm;
						
			            int itemsDisplayed = 0;
						for (int itemIndex = 0; itemIndex < itemDetails.length; itemIndex++) {
							
							String itemDetail = itemDetails[itemIndex];
							String itemCnt = itemCnts[itemIndex];

							String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
							int lineCount = itemLines.length;
							
							if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
								break;
							}
							
							cs.beginText();
							cs.setFont(fontBold, fontSize);
							cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
							for (String itemLine : itemLines) {
								cs.showText(itemLine);
								cs.newLineAtOffset(0, -lineHeight);
							}
							
							cs.endText();
							
							cs.beginText();
							cs.setFont(fontBold, fontSize);
							cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
							cs.showText(itemCnt);
							cs.endText();
							
							//yCurrent -= lineHeight * (lineCount > 1 ? lineCount : 1);
							yCurrent -= lineHeight * lineCount;
							itemsDisplayed++;
						}
						
						if (itemsDisplayed < itemDetails.length) {
							String extraText = "외 " + (itemDetails.length - itemsDisplayed) + "개";
							cs.beginText();
							cs.newLineAtOffset(xStart + 4*mm, yCurrent);
							cs.setFont(fontBold, 8);
							cs.showText(extraText);
							cs.endText();
						}


						float textMaxWidth = 75*mm;
						String dlvMsg = order.getDlvReqMsg();
						
						cs.beginText();
						cs.newLineAtOffset(2*mm, 11.5f*mm);
						cs.setFont(fontBold, 8);
						cs.setLeading(8.5f);
						
						String[] dlvMsgWords = dlvMsg.split(" ");
						line = new StringBuilder();
						for (String word : dlvMsgWords) {
							String tempLine = line + (line.length() == 0 ? "" : " ") + word;
							float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
							if (tempWidth > textMaxWidth) {
								cs.showText(line.toString());
								cs.newLine();
								line = new StringBuilder(word);
							} else {
								line.append(line.length() == 0 ? "" : " ").append(word);
							}
						}
						
						if (line.length() > 0) {
							cs.showText(line.toString());
						}
						
						cs.endText();

						cs.beginText();
						cs.newLineAtOffset(8*mm, 2*mm);
						cs.setFont(fontBold, 18);
						cs.showText(cllDlvText);
						cs.endText();

						cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
						
						fontSize = 9;
						textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

						cs.beginText();
						cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
						cs.setFont(fontBold, fontSize);
						cs.showText(hawbNo);
						cs.endText();
						
						cs.close();
						
						totalPage++;
						
						if (dlvEmpBarcodeFile.exists()) {
							dlvEmpBarcodeFile.delete();
						}
						
						if (hawbNoBarcodeFile.exists()) {
							hawbNoBarcodeFile.delete();
						}
					}
					
				} else {
					
					String resultMsg = getReqAddrFailMsg(cjParameter.getResultCd());
					
					PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
					PDPage blankPage = new PDPage(pageSize);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(totalPage);
					PDPageContentStream cs = new PDPageContentStream(doc, page);
					
					
					float xStart = 0;
					float xEnd = pageSize.getWidth();
					float yEnd = pageSize.getHeight();
					
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
					cs.setFont(fontBold, 12);
					cs.showText(hawbNoFmt);
					cs.endText();
					
					String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
					if (cneeHp.length() != 0) {
						cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
					}
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
					cs.setFont(fontBold, 10);
					cs.showText(cneeInfo);
					cs.endText();
					
					float barHeight = 30*mm;
					
					Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
					barHeight = 19*mm;
					hawbNoBarcode.setBarHeight((int)barHeight);
					hawbNoBarcode.setDrawingQuietSection(false);
					File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
					BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
					PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
					
					cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
					
					float startX = xStart + 6*mm;
					float startY = yEnd - 35*mm;
					
					cs.beginText();
					cs.newLineAtOffset(startX, startY);
					cs.setFont(fontBold, 9);
					cs.setLeading(10);


					String[] addressWords = cneeAddr.split(" ");
					StringBuilder line = new StringBuilder();
					for (String word : addressWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
						if (tempWidth > 107*mm) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();
					

					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 46*mm);
					cs.setFont(fontBold, 24);
					cs.showText(resultMsg);
					cs.endText();
					

					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 49*mm);
					cs.setFont(fontBold, 7);
					cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
					cs.setLeading(7.5f);
					cs.newLine();
					cs.setFont(fontBold, 8);
					cs.showText(shipperAddr);
					cs.endText();

					float itemDetailMaxWidth = 108*mm;
					int fontSize = 9;
					float yCurrent = yEnd - 58*mm;
					float lineHeight = fontSize + 2;

					String[] itemDetails = order.getItemDetail().split("\\|");
					String[] itemCnts = order.getItemCnt().split("\\|");
					float itemLimitLine = 15*mm;
					
		            int itemsDisplayed = 0;
					for (int itemIndex = 0; itemIndex < itemDetails.length; itemIndex++) {
						
						String itemDetail = itemDetails[itemIndex];
						String itemCnt = itemCnts[itemIndex];

						String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
						int lineCount = itemLines.length;
						
						if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
							break;
						}
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
						for (String itemLine : itemLines) {
							cs.showText(itemLine);
							cs.newLineAtOffset(0, -lineHeight);
						}
						
						cs.endText();
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
						cs.showText(itemCnt);
						cs.endText();
						
						//yCurrent -= lineHeight * (lineCount > 1 ? lineCount : 1);
						yCurrent -= lineHeight * lineCount;
						itemsDisplayed++;
					}
					
					if (itemsDisplayed < itemDetails.length) {
						String extraText = "외 " + (itemDetails.length - itemsDisplayed) + "개";
						cs.beginText();
						cs.newLineAtOffset(xStart + 4*mm, yCurrent);
						cs.setFont(fontBold, 8);
						cs.showText(extraText);
						cs.endText();
					}


					float textMaxWidth = 75*mm;
					String dlvMsg = order.getDlvReqMsg();
					
					cs.beginText();
					cs.newLineAtOffset(2*mm, 11.5f*mm);
					cs.setFont(fontBold, 8);
					cs.setLeading(8.5f);
					
					String[] dlvMsgWords = dlvMsg.split(" ");
					line = new StringBuilder();
					for (String word : dlvMsgWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
						if (tempWidth > textMaxWidth) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();

					cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
					
					
					fontSize = 9;
					float textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

					cs.beginText();
					cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
					cs.setFont(fontBold, fontSize);
					cs.showText(hawbNo);
					cs.endText();
					
					cs.close();
					
					totalPage++;

					if (hawbNoBarcodeFile.exists()) {
						hawbNoBarcodeFile.delete();
					}
					
				}
				

				
			}
			
			response.setHeader("Content-Disposition", "inline; filename=label.pdf");
			response.setContentType("application/pdf");
			doc.save(response.getOutputStream());
			doc.close();	
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	

	public void storeCJLabel(ApiOrderListVO apiOrderList, ArrayList<ApiOrderItemListVO> apiOrderItemList) {
		
		try {
			
			apiOrderList.dncryptData();
			CJInfo cjInfo = checkCJTokenInfo();
			String tokenNum = cjInfo.getTokenNo();
			
			
			String pdfPath = filePath + "pdf";
			String barcodePath = pdfPath + "/barcode/";
			File dir = new File(barcodePath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			
			float mm = 2.83465f;
			int totalPage = 0;
			
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String fontPath = cssResource.getURI().getPath().substring(0, cssResource.getURI().getPath().lastIndexOf("/"));
			InputStream fontStream = new FileInputStream(fontPath + "/static/fonts/NotoSansKR-Bold.ttf");
			
			PDDocument doc = new PDDocument();
			
			PDType0Font fontBold = PDType0Font.load(doc, fontStream);

			
			String hawbNo = apiOrderList.getHawbNo();
			String hawbNoFmt = hawbNo.substring(0,4) + "-" + hawbNo.substring(4,8) + "-" + hawbNo.substring(8,12);
			
			String shipperTel = apiOrderList.getShipperTel().replaceAll("[^0-9]", "");
			String[] sendrTelNo = getTelNumberSplit(shipperTel);
			String shipperName = apiOrderList.getShipperName();
			String shipperAddr = apiOrderList.getShipperAddr().trim();
			if (!"".equals(apiOrderList.getShipperAddrDetail())) {
				shipperAddr += " " + apiOrderList.getShipperAddrDetail().trim();
			}
			
			String cneeTel = apiOrderList.getCneeTel().replaceAll("[^0-9]", "");
			String cneeHp = apiOrderList.getCneeHp().replaceAll("[^0-9]", "");
			String[] rcvrTelNo = getTelNumberSplit(cneeTel);
			String[] rcvrCellNo = getTelNumberSplit(cneeHp);
			String cneeName = apiOrderList.getCneeName().substring(0,apiOrderList.getCneeName().length()-1) + "*";
			String cneeAddr = apiOrderList.getCneeAddr().trim();
			if (!"".equals(apiOrderList.getCneeAddrDetail())) {
				cneeAddr += " " + apiOrderList.getCneeAddrDetail().trim();
			}
			
			CJParameter cjParameter = requestRefinedAddress(tokenNum, cneeAddr);
			System.out.println(cjParameter);
			
			int boxCnt = Integer.parseInt(apiOrderList.getBoxCnt());
			
			if ("S".equals(cjParameter.getResultCd())) {

				String cllDlvText = cjParameter.getCllDlvBranNm() + "-" + cjParameter.getCllDlvEmpNickNm();
				
				for (int i = 0; i < boxCnt; i++) {
					int outputCnt = i+1;
					String outputStr = outputCnt + "/" + boxCnt;
					
					PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
					PDPage blankPage = new PDPage(pageSize);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(totalPage);
					PDPageContentStream cs = new PDPageContentStream(doc, page);
					
					float xStart = 0;
					float xEnd = pageSize.getWidth();
					float yEnd = pageSize.getHeight();
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
					cs.setFont(fontBold, 12);
					cs.showText(hawbNoFmt);
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 45*mm, yEnd - 7*mm);
					cs.setFont(fontBold, 8);
					cs.showText(outputStr);
					cs.endText();
					
					String clsfCd1 = cjParameter.getClsfCd().substring(0,1);
					String clsfCd2 = cjParameter.getClsfCd().substring(1);
					
					Barcode dlvEmpBarcode = BarcodeFactory.createCode128A(cjParameter.getClsfCd());
					float barHeight = 30*mm;
					dlvEmpBarcode.setBarHeight((int)barHeight);
					dlvEmpBarcode.setDrawingQuietSection(false);
					File dlvEmpBarcodeFile = new File(barcodePath + hawbNo + "_" + cjParameter.getClsfCd() + ".jpeg");
					BarcodeImageHandler.saveJPEG(dlvEmpBarcode, dlvEmpBarcodeFile);
					PDImageXObject dlvEmpBarcodeImage = PDImageXObject.createFromFileByContent(dlvEmpBarcodeFile, doc);

					cs.drawImage(dlvEmpBarcodeImage, xStart + 5*mm, yEnd - 25*mm, 30*mm, 15*mm);
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 37*mm, yEnd - 25*mm);
					cs.setFont(fontBold, 34);
					cs.showText(clsfCd1);
					cs.endText();
					
					float textWidth = (fontBold.getStringWidth(clsfCd1) / 1000) * 34;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (37*mm+textWidth), yEnd - 25*mm);
					cs.setFont(fontBold, 50);
					cs.showText(clsfCd2);
					cs.endText();
					
					textWidth = textWidth + (fontBold.getStringWidth(clsfCd2) / 1000) * 50;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (38*mm+textWidth), yEnd - 22*mm);
					cs.setFont(fontBold, 34);
					cs.showText("-"+cjParameter.getSubClsfCd());
					cs.endText();
					
					textWidth = textWidth + (fontBold.getStringWidth("-"+cjParameter.getSubClsfCd()) / 1000) * 34;
					
					cs.beginText();
					cs.newLineAtOffset(xStart + (40*mm+textWidth), yEnd - 23*mm);
					cs.setFont(fontBold, 24);
					cs.showText(cjParameter.getRspsDiv());
					cs.endText();
					
					
					String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
					if (cneeHp.length() != 0) {
						cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
					}
					
					cs.beginText();
					cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
					cs.setFont(fontBold, 10);
					cs.showText(cneeInfo);
					cs.endText();
					
					
					Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
					barHeight = 19*mm;
					hawbNoBarcode.setBarHeight((int)barHeight);
					hawbNoBarcode.setDrawingQuietSection(false);
					File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
					BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
					PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
					
					cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
					
					
					float startX = xStart + 6*mm;
					float startY = yEnd - 35*mm;
					
					cs.beginText();
					cs.newLineAtOffset(startX, startY);
					cs.setFont(fontBold, 9);
					cs.setLeading(10);

					String[] addressWords = cneeAddr.split(" ");
					StringBuilder line = new StringBuilder();
					for (String word : addressWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
						if (tempWidth > 107*mm) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 46*mm);
					cs.setFont(fontBold, 24);
					cs.showText(cjParameter.getClsfAddr());
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(startX, yEnd - 49*mm);
					cs.setFont(fontBold, 7);
					cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
					cs.setLeading(7.5f);
					cs.newLine();
					cs.setFont(fontBold, 8);
					cs.showText(shipperAddr);
					cs.endText();

					
					cs.beginText();
					cs.newLineAtOffset(xStart + 69*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("극소"+boxCnt);
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 22*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("0");
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(xEnd - 10*mm, yEnd - 50*mm);
					cs.setFont(fontBold, 10);
					cs.showText("신용");
					cs.endText();
					
					
					float itemDetailMaxWidth = 108*mm;
					int fontSize = 9;
					float yCurrent = yEnd - 58*mm;
					float lineHeight = fontSize + 2;
					
					float itemLimitLine = 15*mm;
					int itemsDisplayed = 0;
					for (int ii = 0; ii < apiOrderItemList.size(); ii++) {
						String itemDetail = apiOrderItemList.get(ii).getItemDetail();
						String itemCnt = apiOrderItemList.get(ii).getItemCnt();
						
						String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
						int lineCount = itemLines.length;
						
						if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
							break;
						}
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
						for (String itemLine : itemLines) {
							cs.showText(itemLine);
							cs.newLineAtOffset(0, -lineHeight);
						}
						
						cs.endText();
						
						cs.beginText();
						cs.setFont(fontBold, fontSize);
						cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
						cs.showText(itemCnt);
						cs.endText();

						yCurrent -= lineHeight * lineCount;
						itemsDisplayed++;
					}
					
					if (itemsDisplayed < apiOrderItemList.size()) {
						String extraText = "외 " + (apiOrderItemList.size() - itemsDisplayed) + "개";
						cs.beginText();
						cs.newLineAtOffset(xStart + 4*mm, yCurrent);
						cs.setFont(fontBold, 8);
						cs.showText(extraText);
						cs.endText();
					}
					
					float textMaxWidth = 75*mm;
					String dlvMsg = apiOrderList.getDlvReqMsg();
					
					cs.beginText();
					cs.newLineAtOffset(2*mm, 11.5f*mm);
					cs.setFont(fontBold, 8);
					cs.setLeading(8.5f);
					
					String[] dlvMsgWords = dlvMsg.split(" ");
					line = new StringBuilder();
					for (String word : dlvMsgWords) {
						String tempLine = line + (line.length() == 0 ? "" : " ") + word;
						float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
						if (tempWidth > textMaxWidth) {
							cs.showText(line.toString());
							cs.newLine();
							line = new StringBuilder(word);
						} else {
							line.append(line.length() == 0 ? "" : " ").append(word);
						}
					}
					
					if (line.length() > 0) {
						cs.showText(line.toString());
					}
					
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(8*mm, 2*mm);
					cs.setFont(fontBold, 18);
					cs.showText(cllDlvText);
					cs.endText();

					cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
					
					fontSize = 9;
					textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

					cs.beginText();
					cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
					cs.setFont(fontBold, fontSize);
					cs.showText(hawbNo);
					cs.endText();
					
					cs.close();
					
					totalPage++;
					
					if (dlvEmpBarcodeFile.exists()) {
						dlvEmpBarcodeFile.delete();
					}
					
					if (hawbNoBarcodeFile.exists()) {
						hawbNoBarcodeFile.delete();
					}
				}
				
			} else {
				
				String resultMsg = getReqAddrFailMsg(cjParameter.getResultCd());
				
				PDRectangle pageSize = new PDRectangle(123*mm, 100*mm);
				PDPage blankPage = new PDPage(pageSize);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(totalPage);
				PDPageContentStream cs = new PDPageContentStream(doc, page);
				
				
				float xStart = 0;
				float xEnd = pageSize.getWidth();
				float yEnd = pageSize.getHeight();
				
				
				cs.beginText();
				cs.newLineAtOffset(xStart + 16*mm, yEnd - 7*mm);
				cs.setFont(fontBold, 12);
				cs.showText(hawbNoFmt);
				cs.endText();
				
				String cneeInfo = cneeName + " " + rcvrTelNo[0] + "-" + rcvrTelNo[1] + "-**** / ";
				if (cneeHp.length() != 0) {
					cneeInfo += rcvrCellNo[0] + "-" + rcvrCellNo[1] + "-****"; 
				}
				
				cs.beginText();
				cs.newLineAtOffset(xStart + 6*mm, yEnd - 31*mm);
				cs.setFont(fontBold, 10);
				cs.showText(cneeInfo);
				cs.endText();
				
				float barHeight = 30*mm;
				
				Barcode hawbNoBarcode = BarcodeFactory.createCode128C(hawbNo);
				barHeight = 19*mm;
				hawbNoBarcode.setBarHeight((int)barHeight);
				hawbNoBarcode.setDrawingQuietSection(false);
				File hawbNoBarcodeFile = new File(barcodePath + hawbNo + "_short.jpeg");
				BarcodeImageHandler.saveJPEG(hawbNoBarcode, hawbNoBarcodeFile);
				PDImageXObject hawbNoBarcodeImage = PDImageXObject.createFromFileByContent(hawbNoBarcodeFile, doc);
				
				cs.drawImage(hawbNoBarcodeImage, xEnd - 45*mm, yEnd - 31*mm, 35*mm, 5*mm);
				
				float startX = xStart + 6*mm;
				float startY = yEnd - 35*mm;
				
				cs.beginText();
				cs.newLineAtOffset(startX, startY);
				cs.setFont(fontBold, 9);
				cs.setLeading(10);


				String[] addressWords = cneeAddr.split(" ");
				StringBuilder line = new StringBuilder();
				for (String word : addressWords) {
					String tempLine = line + (line.length() == 0 ? "" : " ") + word;
					float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 9;
					if (tempWidth > 107*mm) {
						cs.showText(line.toString());
						cs.newLine();
						line = new StringBuilder(word);
					} else {
						line.append(line.length() == 0 ? "" : " ").append(word);
					}
				}
				
				if (line.length() > 0) {
					cs.showText(line.toString());
				}
				
				cs.endText();
				

				cs.beginText();
				cs.newLineAtOffset(startX, yEnd - 46*mm);
				cs.setFont(fontBold, 24);
				cs.showText(resultMsg);
				cs.endText();
				

				cs.beginText();
				cs.newLineAtOffset(startX, yEnd - 49*mm);
				cs.setFont(fontBold, 7);
				cs.showText(shipperName + "   " + sendrTelNo[0]+"-"+sendrTelNo[1]+"-"+sendrTelNo[2]);
				cs.setLeading(7.5f);
				cs.newLine();
				cs.setFont(fontBold, 8);
				cs.showText(shipperAddr);
				cs.endText();

				float itemDetailMaxWidth = 108*mm;
				int fontSize = 9;
				float yCurrent = yEnd - 58*mm;
				float lineHeight = fontSize + 2;

				float itemLimitLine = 15*mm;
				int itemsDisplayed = 0;
				for (int ii = 0; ii < apiOrderItemList.size(); ii++) {
					String itemDetail = apiOrderItemList.get(ii).getItemDetail();
					String itemCnt = apiOrderItemList.get(ii).getItemCnt();
					
					String[] itemLines = splitTextToLines(itemDetail, fontBold, fontSize, itemDetailMaxWidth);
					int lineCount = itemLines.length;
					
					if (yCurrent - lineHeight * (lineCount + 1) < itemLimitLine) {
						break;
					}
					
					cs.beginText();
					cs.setFont(fontBold, fontSize);
					cs.newLineAtOffset(xStart + 4*mm,  yCurrent);
					for (String itemLine : itemLines) {
						cs.showText(itemLine);
						cs.newLineAtOffset(0, -lineHeight);
					}
					
					cs.endText();
					
					cs.beginText();
					cs.setFont(fontBold, fontSize);
					cs.newLineAtOffset(xEnd - 10*mm, yCurrent);
					cs.showText(itemCnt);
					cs.endText();

					yCurrent -= lineHeight * lineCount;
					itemsDisplayed++;
				}
				
				if (itemsDisplayed < apiOrderItemList.size()) {
					String extraText = "외 " + (apiOrderItemList.size() - itemsDisplayed) + "개";
					cs.beginText();
					cs.newLineAtOffset(xStart + 4*mm, yCurrent);
					cs.setFont(fontBold, 8);
					cs.showText(extraText);
					cs.endText();
				}
				
				float textMaxWidth = 75*mm;
				String dlvMsg = apiOrderList.getDlvReqMsg();
				cs.beginText();
				cs.newLineAtOffset(2*mm, 11.5f*mm);
				cs.setFont(fontBold, 8);
				cs.setLeading(8.5f);
				
				String[] dlvMsgWords = dlvMsg.split(" ");
				line = new StringBuilder();
				for (String word : dlvMsgWords) {
					String tempLine = line + (line.length() == 0 ? "" : " ") + word;
					float tempWidth = (fontBold.getStringWidth(tempLine) / 1000) * 8;
					if (tempWidth > textMaxWidth) {
						cs.showText(line.toString());
						cs.newLine();
						line = new StringBuilder(word);
					} else {
						line.append(line.length() == 0 ? "" : " ").append(word);
					}
				}
				
				if (line.length() > 0) {
					cs.showText(line.toString());
				}
				
				cs.endText();

				cs.drawImage(hawbNoBarcodeImage, xEnd - 43*mm, 3.5f*mm, 35*mm, 9.5f*mm);
				
				
				fontSize = 9;
				float textWidth = fontBold.getStringWidth(hawbNo) / 1000 * fontSize;

				cs.beginText();
				cs.newLineAtOffset((xEnd - 43*mm)+(35*mm - textWidth)/2, 1.5f);
				cs.setFont(fontBold, fontSize);
				cs.showText(hawbNo);
				cs.endText();
				
				cs.close();
				
				totalPage++;

				if (hawbNoBarcodeFile.exists()) {
					hawbNoBarcodeFile.delete();
				}
			}
			


			
			doc.save(filePath + apiOrderList.getNno() + ".pdf");
			doc.close();
			File base64Img = new File(filePath + apiOrderList.getNno() + ".pdf");
			
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult pores = new PutObjectResult();
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String week = String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
			if (amazonS3 != null) {
				String bucketName = comnS3Info.getBucketName() + "/outbound/hawb/" + year + "/" + week;
				PutObjectRequest poreq = new PutObjectRequest(bucketName, apiOrderList.getUserId() + "_" + apiOrderList.getHawbNo(), base64Img);
				poreq.setCannedAcl(CannedAccessControlList.PublicRead);
				pores = amazonS3.putObject(poreq);
			}
			
			amazonS3 = null;
			base64Img.delete();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	private String[] splitTextToLines(String str, PDType0Font font, int fontSize, float maxWidth) throws Exception {
		String[] words = str.split(" ");
		StringBuilder line = new StringBuilder();
		StringBuilder result = new StringBuilder();
		for (String word : words) {
			String tempLine = line + (line.length() == 0 ? "" : " ") + word;
			float tempWidth = (font.getStringWidth(tempLine) / 1000) * fontSize;
			if (tempWidth > maxWidth) {
				result.append(line).append("\n");
				line = new StringBuilder(word);
			} else {
				line.append(line.length() == 0 ? "" : " ").append(word);
			}
		}
		result.append(line);
		
		return result.toString().split("\n");
	}
	
	
	private HashMap<String, String> buildApiHeaders(String apiKey) {
		HashMap<String, String> headers = new HashMap<>();
		if (apiKey != null) {
			headers.put("CJ-Gateway-APIKey", apiKey);	
		}
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		return headers;
	}
	
	
	private String[] getTelNumberSplit(String telNo) {
		telNo = telNo.replaceFirst("^082", "").replaceFirst("^82", "");
		
		String[] array = new String[3];
		
		array[0] = "";
		array[1] = "";
		array[2] = "";
		
		if (telNo.length() > 8) {
			
			Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
	        Matcher matcher = tellPattern.matcher(telNo);
	        if(matcher.matches()) {
	        	array = new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
	        }else{
	            String str1 = telNo.substring(0, 3);
	            String str2 = telNo.substring(3, 7);
	            String str3 = telNo.substring(7, 11);
	            array = new String[]{str1, str2, str3};
	        }
		} else {
			
			if (telNo.length() == 8) {
				array[0] = telNo.substring(0,4);
				array[1] = telNo.substring(4,telNo.length());
				array[2] = "";
			}
		}
		
		return array;
	}
	
	
	private String getPackageStatusCode(String code) {
		String status = "";
		
		switch (code) {
		case "01":
			status = "집하지시";
			break;
		case "11":
			status = "집하처리";
			break;
		case "12":
			status = "미집하";
			break;
		case "41":
			status = "간선상차";
			break;
		case "42":
			status = "간선하차";
			break;
		case "82":
			status = "배송출발";
			break;
		case "84":
			status = "미배달";
			break;
		case "91":
			status = "배송완료";
			break;
		default:
			status = "상태확인불가";
			break;
		}
		
		return status;
	}
	
	
	public String getReqAddrFailMsg(String resultCd) {
		String msg = "";
		System.out.println(resultCd);
		
		switch (resultCd) {
		case "-20002":
			msg = "수취인 주소 분석 실패";
		break;
		case "-20003":
			msg = "집배권역 설정값 조회 실패";
		break;
		case "-20004":
			msg = "집배권역 점소정보 사용 중지 상태";
		break;
		case "-20005":
			msg = "배송SM 미설정";
		break;
		case "-20006":
			msg = "허브터미널 분류 코드 추출 실패";
		break;
		case "-20007":
			msg = "서브터미널 분류 주소 추출 실패";
		break;
		case "-20009":
			msg = "배달 불가능 지역";
		break;
		default:
			msg = "주소 정제 실패";
		break;
		}
		
		System.out.println(msg);
		return msg;
	}
}
