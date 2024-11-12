package com.example.temp.api.logistics.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.Export;
import com.example.temp.api.Item;
import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.Tracking;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;

@Service
public class LogisticsServiceImpl implements LogisticsService {
	
	@Autowired
	LogisticsService service;
	
	@Autowired
	LogisticsMapper mapper;

	@Autowired
	CJHandler cjHandler;

	@Autowired
	YongsungHandler ysHandler;
	
	@Value("${filePath}")
	String realFilePath;


	@Override
	public HashMap<String, Object> registLogisticsOrder(HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String userIp = request.getRemoteAddr();
		String orderType = request.getParameter("orderType").toUpperCase();

		ArrayList<Order> orderList = getTmpOrderList(request);
		for (int i = 0; i < orderList.size(); i++) {
			Order order = orderList.get(i);
			order.dncryptData();
			registLogisticsOrderOne(order, request);
		}
		
		return resultMap;
	}
	

	private void registLogisticsOrderOne(Order order, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String userIp = request.getRemoteAddr();
		String orderType = request.getParameter("orderType").toUpperCase();
		String transCode = order.getTransCode().toUpperCase().trim();
		
		ProcedureRst shipmentRst = new ProcedureRst();
		
		switch (transCode) {
		case "CJ":
			shipmentRst = registShipment(order);
			break;
		case "VNP":
			
			break;
		}
	}


	private ArrayList<Order> getTmpOrderList(HttpServletRequest request) {
		
		ArrayList<Order> orderList = new ArrayList<>();
		HashMap<String, Object> sqlParams = new HashMap<>();
		boolean checked = Boolean.parseBoolean(request.getParameter("check"));
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String userIp = request.getRemoteAddr();
		String orderType = request.getParameter("orderType").toUpperCase();
		
		if  (checked) {
			// 선택 자료
			if (request.getParameterValues("targets") != null) {
				String[] chkList = request.getParameterValues("targets");
				String[] transCodeList = request.getParameterValues("transCodeList");
				String[] uploadTypeList = request.getParameterValues("uploadType");
				
				for (int i = 0; i < chkList.length; i++) {
					sqlParams = new HashMap<>();
					sqlParams.put("userId", userId);
					sqlParams.put("transCode", transCodeList[i]);
					sqlParams.put("uploadType", uploadTypeList[i]);
					sqlParams.put("orderType", orderType);
					sqlParams.put("nno", chkList[i]);
					Order order = new Order();
					order = service.selectTempList(sqlParams);
					order.setWUserId(userId);
					order.setWUserIp(userIp);
					if (order != null) {
						orderList.add(order);	
					}
				}
			}
			
			return orderList;
			
		} else {
			// 전체 자료
			sqlParams.put("userId", userId);
			sqlParams.put("orderType", orderType);
			sqlParams.put("userIp", userIp);
			orderList = service.selectTempListAll(sqlParams);
			return orderList;
		}
	}


	@Override
	public Order selectTempList(HashMap<String, Object> sqlParams) {
		
		try {
			
			Order order = mapper.selectTmpOrder(sqlParams);
			
			if (order != null) {
				ArrayList<Item> item = mapper.selectTmpItems(sqlParams);
				order.setItemList(item);
				
				if (order.getItemList().isEmpty()) {
					return null;
				}
				
				Export export = mapper.selectExportDeclare(sqlParams);
				if (export != null) {
					order.setExport(export);
				}
			}
			
			return order;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}



	@Override
	public ArrayList<Order> selectTempListAll(HashMap<String, Object> sqlParams) {
		
		ArrayList<Order> orderList = new ArrayList<>();
		
		try {
			
			ArrayList<Order> tmpOrders = new ArrayList<>();
			tmpOrders = mapper.selectTmpOrderList(sqlParams);
			
			for (int i = 0; i < tmpOrders.size(); i++) {
				Order order = tmpOrders.get(i);
				HashMap<String, Object> params = new HashMap<>();
				
				try {
					params.put("userId", (String) sqlParams.get("userId"));
					params.put("orderType", (String) sqlParams.get("orderType"));
					params.put("nno", order.getNno());
					
					ArrayList<Item> item = mapper.selectTmpItems(params);
					
					if (!item.isEmpty()) {
						order.setItemList(item);
					} else {
						continue;
					}
					
					Export export = mapper.selectExportDeclare(params);
					if (export != null) {
						order.setExport(export);
					}
					order.setWUserId((String) sqlParams.get("userId"));
					order.setWUserIp((String) sqlParams.get("userIp"));
					orderList.add(order);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					continue;
				}
			}
			
			return orderList;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
	
	@Override
	public ProcedureRst registShipment(Order order) {
		ProcedureRst shipmentRst = new ProcedureRst();
		shipmentRst = mapper.execSpRegShipment(order);
		return shipmentRst;
	}
	
	
	@Override
	public void storeToS3(Order order) {
		
		try {
			
			String nno = order.getNno();
			String year = nno.substring(0,4);
			String date = nno.substring(4,8);
			
			String filePath = realFilePath + "pdf/" + order.getNno() + ".pdf";
			FileOutputStream fos = new FileOutputStream(filePath);
			
			URL url = new URL(order.getLabelUrl());
			ReadableByteChannel read = Channels.newChannel(url.openStream());
			
			fos.getChannel().transferFrom(read, 0, Long.MAX_VALUE);
			fos.close();

			AWSCredentials awsCredentials = new BasicAWSCredentials(ComnVO.getAccessKey(), ComnVO.getSecretKey());
			AmazonS3 awsS3 = AmazonS3ClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
						.withRegion(Regions.AP_NORTHEAST_2)
						.build();
			PutObjectResult putObjectResult = new PutObjectResult();
			File file = new File(filePath);
			
			String bucketPath = ComnVO.getBucketName() + "/outBound/hawb/" + year + "/" + date;
			System.out.println(bucketPath); 
			
			if (awsS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketPath, nno, file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				putObjectResult = awsS3.putObject(putObjectRequest);
			}
			
			awsS3 = null;
			
			if (file.exists()) {
				file.delete();
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


	@Override
	public void processCJRegBook() {
		
		CJInfo cjInfo = cjHandler.checkCJTokenInfo();
		String tokenNum = cjInfo.getTokenNo();
		String tokenEDate = cjInfo.getTokenEDate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
		LocalDateTime tokenExpiryDate = LocalDateTime.parse(tokenEDate, formatter);
		ArrayList<Order> orderList = mapper.selectCJRegBookOrderList();
		
		for (int i = 0; i < orderList.size(); i++) {
			
			try {
				
				LocalDateTime currentTime = LocalDateTime.now();
				if (tokenExpiryDate.isBefore(currentTime) || tokenExpiryDate.isBefore(currentTime.plusMinutes(30))) {
					cjInfo = cjHandler.getOneDayTokenInfo(cjInfo);
					tokenNum = cjInfo.getTokenNo();
					tokenExpiryDate = LocalDateTime.parse(cjInfo.getTokenEDate(), formatter);
				}
				
				Order order = orderList.get(i);
				order.dncryptData();
				
				ArrayList<Item> itemList = new ArrayList<Item>();
				String[] itemInfos = order.getItemInfo().split("\\|\\^\\|");
				for (int j = 0; j < itemInfos.length; j++) {
					String set = itemInfos[j];
					if (set.isEmpty()) {
						continue;
					}
					
					String[] part = set.split("\\|");
					Item item = new Item();
					item.setSubNo(Integer.parseInt(part[0]));
					item.setItemDetail(part[1]);
					item.setItemCnt(Integer.parseInt(part[2]));
					itemList.add(item);
				}
				
				order.setItemList(itemList);
				cjHandler.registBooking(order, tokenNum);
			
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
		}
		
	}
	

	@Override
	public ArrayList<Tracking> getAciTrackingList(HashMap<String, Object> sqlParams) {
		
		ArrayList<Tracking> trackingList = new ArrayList<>();
		HashMap<String, String> dateTimeInfo = mapper.selectTrackingDateTime(sqlParams);
		String hawbNo = (String) sqlParams.get("hawbNo");
		
		if (dateTimeInfo == null) {
			
			Tracking trkInfo = new Tracking();
			trkInfo.setHawbNo(hawbNo);
			trkInfo.setStatusCode("-200");
			trkInfo.setDescription("Invalid Invoice Number");
			trackingList.add(trkInfo);
			
		} else {
			
			Tracking trkInfo = new Tracking();
			trkInfo.setHawbNo(hawbNo);
			trkInfo.setStatusCode("100");
			trkInfo.setDescription("Order information received");
			trkInfo.setDateTime(dateTimeInfo.get("registDate"));
			trkInfo.setLocation("ACI WorldWide, South Korea");
			trkInfo.setOrderNo(dateTimeInfo.get("orderNo"));
			trkInfo.setCneeName(dateTimeInfo.get("cneeName"));
			trkInfo.setTrkNo(dateTimeInfo.get("trkNo"));
			trkInfo.setTransCode(dateTimeInfo.get("transCode"));
			trkInfo.setDescriptionKor("배송접수");
			trkInfo.setLocationKor("(주) 에이씨아이 월드와이드");
			trkInfo.setMatchNo(dateTimeInfo.get("matchNo"));
			trackingList.add(trkInfo);
			
			if (!"N".equals(dateTimeInfo.get("whInDate"))) {
				trkInfo = new Tracking();
				trkInfo.setHawbNo(hawbNo);
				trkInfo.setStatusCode("200");
				trkInfo.setDescription("Arrived at ACI fulfillment Center");
				trkInfo.setDateTime(dateTimeInfo.get("whInDate"));
				trkInfo.setLocation("ACI WorldWide, South Korea");
				trkInfo.setDescriptionKor("입고");
				trkInfo.setLocationKor("(주) 에이씨아이 월드와이드");
				trackingList.add(trkInfo);
			}
			
			if (!"N".equals(dateTimeInfo.get("whoutDate"))) {
				trkInfo = new Tracking();
				trkInfo.setHawbNo(hawbNo);
				trkInfo.setStatusCode("300");
				trkInfo.setDescription("Departed from ACI fulfillment Center");
				trkInfo.setDateTime(dateTimeInfo.get("whoutDate"));
				trkInfo.setLocation("South Korea");
				trkInfo.setDescriptionKor("출고");
				trkInfo.setLocationKor("(주) 에이씨아이 월드와이드");
				trackingList.add(trkInfo);
			}
		}
		
		return trackingList;
	}


	@Override
	public Order selectTbOrderList(HashMap<String, Object> sqlParams) {
		try {
			
			Order order = mapper.selectTbOrder(sqlParams);
			
			if (order != null) {
				ArrayList<Item> item = mapper.selectTbOrderItems(sqlParams);
				order.setItemList(item);
				
				if (order.getItemList().isEmpty()) {
					return null;
				}
				
				Export export = mapper.selectExportDeclare(sqlParams);
				if (export != null) {
					order.setExport(export);
				}
			}
			
			return order;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}


	
}
