package com.example.temp.api.shipment;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.shipment.company.Epost;
import com.example.temp.api.shipment.company.Fastbox;
import com.example.temp.api.shipment.company.YongSung;
import com.example.temp.api.shipment.company.YunExpress;
import com.example.temp.api.shop.Shopify;
import com.example.temp.common.vo.ComnVO;
import com.google.gson.Gson;

@Service
public class ShippingServiceImpl implements ShippingService {
	
	@Autowired
	ShippingMapper shipMapper;
	
	@Autowired
	YunExpress yunExp;
	
	@Autowired
	Epost epost;
	
	@Autowired
	YongSung yongsung;
	
	@Autowired
	Fastbox fastbox;
	
	@Autowired
	Shopify shopify;
	
	@Value("${filePath}")
    String realFilePath;

	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	// 배송 데이터 등록 ~ 라벨 생성
	@Override
	public HashMap<String, Object> createShipment(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> collectOrderList = new ArrayList<HashMap<String, Object>>();
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		Boolean checked = Boolean.parseBoolean(request.getParameter("check"));
		
		params.put("userId", userId);
		params.put("userIp", request.getRemoteAddr());
		params.put("orderType", request.getParameter("orderType"));
		
		ArrayList<HashMap<String, Object>> tmpOrders = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> chkOrder = new HashMap<String, Object>();
		
		// 선택 배송자료 등록인 경우
		if (checked) {
			
			if (request.getParameterValues("targets") != null) {
				String[] chkList = request.getParameterValues("targets");
				String[] transCodeList = request.getParameterValues("transCodeList");
				String[] uploadTypeList = request.getParameterValues("uploadType");
				
				for (int roop = 0; roop < chkList.length; roop++) {
					chkOrder = new HashMap<String, Object>();
					chkOrder.put("nno", chkList[roop]);
					chkOrder.put("transCode", transCodeList[roop]);
					chkOrder.put("uploadType", uploadTypeList[roop]);
					tmpOrders.add(chkOrder);
				}
				
			} else {
				result.put("STATUS", "F10");
				result.put("MSG", "배송 신청 가능한 데이터가 없습니다.");
				return result;
			}
			
		// 전체 배송자료 등록인 경우
		} else {
			
			tmpOrders = shipMapper.selectTmpOrderListAll(params);
			
		}
		
		int succCnt = 0;
		int totalCnt = tmpOrders.size();
		
		for (int roop = 0; roop < totalCnt; roop++) {
			
			String errMsg = "";
			String status = "";
			String requestVal = "";
			ShipmentVO shipment = new ShipmentVO();

			String nno = (String) tmpOrders.get(roop).get("nno");
			String transCode = (String) tmpOrders.get(roop).get("transCode");
			String uploadType = (String) tmpOrders.get(roop).get("uploadType");

			params.put("nno", nno);
			params.put("transCode", transCode);
			params.put("uploadType", uploadType);
			
			try {
				// API 요청 데이터 생성
				requestVal = createRequestBody(params);
				// API 호출
				shipment = createShipment(params, requestVal);
				
				// API 호출 결과 분기 - 성공
				if (shipment.getRstStatus().equals("SUCCESS")) {
					ShipmentVO applyShipment = new ShipmentVO();
					params.put("hawbNo", shipment.getRstHawbNo());
					
					// 라벨 생성
					createLabel(params);
					
					// TMP > TB 데이터 삽입 처리
					applyShipment = applyShipment(params);
					
					// TMP > TB 데이터 삽입 처리 결과 분기 - 성공
					if (applyShipment.getRstStatus().equals("SUCCESS")) {
						HashMap<String, Object> collectOrderOne = new HashMap<String, Object>();
						
						collectOrderOne.put("nno", nno);
						collectOrderOne.put("transCode", transCode);
						collectOrderOne.put("uploadType", uploadType);
						collectOrderOne.put("userId", userId);
						collectOrderList.add(collectOrderOne);
						
						succCnt++;
						
					// TMP > TB 데이터 삽입 처리 결과 분기 - 실패
					} else {
						errMsg += applyShipment.getRstMsg();
						status += "DB 오류 발생,";
						throw new Exception();
					}
					
				// API 호출 결과 분기 - 실패	
				} else {
					errMsg += shipment.getRstMsg();
					status += "배송 데이터 오류 발생,";
					throw new Exception();
				}
				
			} catch (Exception e) {
				params.put("errorMsg", errMsg);
				params.put("status", status);
				shipMapper.insertTbApiError(params);
				shipMapper.updateTmpOrderListStatus(params);
				continue;
			}
			
		}

		// API로 수집한 쇼핑몰 데이터 상태 업데이트 처리 진행
		updateShopFulfillService(collectOrderList);
		// 정식 수출신고 건 각 콘솔사에 면장번호 업데이트 처리
		updateExportLicenseNumber(collectOrderList);
		
		result.put("STATUS", "S10");
		result.put("MSG", "배송 신청 처리 되었습니다.\n\n[전체] : " + totalCnt + "\n[성공] : " + succCnt);
		
		return result;
	}

	// 배송사 API 요청 데이터 생성 처리
	@Override
	public String createRequestBody(HashMap<String, Object> params) {
		String transCode = (String) params.get("transCode");
		String requestVal = "";
		
		switch (transCode) {
		case "YT":
			requestVal = jsonObjectToString(yunExp.createRequestBody(params));
			break;
		case "EPT":
			requestVal = epost.createRequestBody(params);
			break;
		case "YSL":
			requestVal = jsonObjectToString(yongsung.createRequestBody(params));
			break;
		case "FB":
			requestVal = jsonObjectToString(fastbox.createRequestBody(params));
			break;
		case "FB-EMS":
			requestVal = jsonObjectToString(fastbox.createRequestBody(params));
			break;
		default:
			break;
		}

		return requestVal;
	}
	
	
	// 배송사 API 호출하여 배송 데이터 등록
	@Override
	public ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String transCode = (String) params.get("transCode");
		
		switch (transCode) {
		case "YT":
			rstVal = yunExp.createShipment(params, requestVal);
			break;
		case "EPT":
			rstVal = epost.createShipment(params, requestVal);
			break;
		case "YSL":
			rstVal = yongsung.createShipment(params, requestVal);
			break;
		case "FB":
			rstVal = fastbox.createShipment(params, requestVal);
			break;
		default:
			rstVal.setRstCode("S");
			rstVal.setRstStatus("SUCCESS");
			rstVal.setRstHawbNo("");
			break;
		}

		return rstVal;
	}
	
	// 배송사 API로 리턴받은 송장 라벨 처리
	@Override
	public void createLabel(HashMap<String, Object> params) {
		String transCode = (String) params.get("transCode");
		HashMap<String, Object> printInfo = new HashMap<String, Object>();
		String hawbNo = (String) params.get("hawbNo");
		String userId = (String) params.get("userId");
		
		switch (transCode) {
		case "YT":
			printInfo = yunExp.getLabelUrl(params);
			break;
		case "EPT":
			break;
		default:
			printInfo.put("labelType", "WRITE");
			printInfo.put("labelInfo", "");
			break;
		}
		
		
		try {
			
			if (!printInfo.isEmpty()) {
				String labelType = (String) printInfo.get("labelType");
				String labelInfo = (String) printInfo.get("labelInfo");
				
				String imageDir = realFilePath + "image/label/";
				File file = new File(imageDir + hawbNo + ".PDF");
				
				if (labelInfo.equals("")) {
					
				} else {
					if (labelType.toUpperCase().equals("URL")) {
						URL imgUrl = new URL(labelInfo);
						ReadableByteChannel read = Channels.newChannel(imgUrl.openStream());
						FileOutputStream fos = new FileOutputStream(file);
						fos.getChannel().transferFrom(read, 0, Long.MAX_VALUE);
						fos.close();
						
					} else if (labelType.toUpperCase().equals("BASE64")) {
						FileOutputStream fos = new FileOutputStream(file);
						byte[] decoder = Base64.getDecoder().decode(labelInfo);
						fos.write(decoder);;
						fos.close();
						
					}
				}
	
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult putObjectResult = new PutObjectResult();
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
				String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
				if(amazonS3 != null) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/hawb/" + year + "/" + week, userId+"_"+hawbNo, file);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					putObjectResult = amazonS3.putObject(putObjectRequest);
				}
				amazonS3 = null;
				file.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 배송사 API 데이터 등록 성공 시 처리, 배송사 접수번호, ACI 가송장 부여 분기에 따라 처리
	@Override
	@Transactional
	public ShipmentVO applyShipment(HashMap<String, Object> params) {
		ShipmentVO shipment = new ShipmentVO();
		String hawbNo = (String) params.get("hawbNo");
		
		if (!hawbNo.equals("")) {
			shipment = shipMapper.spTransBl(params);
		} else {
			shipment = shipMapper.spTransBlApply(params); 
		}
		
		return shipment;
	}
	

	// 쇼핑몰 연동하여 등록된 주문 시 실행
	@Override
	public void updateShopFulfillService(ArrayList<HashMap<String, Object>> collectOrderList) {
		
		for (int i = 0; i < collectOrderList.size(); i++) {
			HashMap<String, Object> orderInfo = (HashMap<String, Object>) collectOrderList.get(i);
			String shopName = (String) orderInfo.get("uploadType");
			shopName = shopName.toUpperCase();
			
			if (shopName.equals("")||shopName.equals("API")||shopName.equals("HAND")||shopName.equals("EXCEL")) {
				continue;
			}
			
			switch (shopName) {
			case "SHOPIFY":
				shopify.createFulfillment(orderInfo);
				break;
			default:
				break;
			}
		}
	}
	
	// 정식 수출신고 건 콘솔사 API로 면장 업데이트
	@Override
	public void updateExportLicenseNumber(ArrayList<HashMap<String, Object>> collectOrderList) {
		
		for (int i = 0; i < collectOrderList.size(); i++) {
			HashMap<String, Object> orderInfo = (HashMap<String, Object>) collectOrderList.get(i);
			String transCode = (String) orderInfo.get("transCode");
			int expChk = shipMapper.selectExportChk(orderInfo);
			
			if (expChk == 0) {
				continue;
			}
			
			switch (transCode.toUpperCase()) {
			case "YSL":
				yongsung.updateExportLicenseInfo(orderInfo);
				break;
			case "FB-EMS":
				//fastbox.updateExportLicenseInfo(orderInfo);
				break;
			case "FB":
				//fastbox.updateExportLicenseInfo(orderInfo);
				break;
			default:
				break;
			}
		}
	}
	
	public String jsonObjectToString(Object object) {
		Gson gson = new Gson();
		String requestVal = gson.toJson(object);
		
		return requestVal;
	}
	
	public String getTransNameByTransCode(String transCode) {
		String transName = "";
		
		switch (transCode.toUpperCase()) {
		case "YT":
			transName = "YunExpress";
			break;
		case "FB-EMS":
			transName = "EMS";
			break;
		case "FB":
			transName = "UPS";
			break;
		case "EPT":
			transName = "Korea Post";
			break;
		}
		
		return transName;
	}
	
	
	public String convertUnicode(String str) throws Exception {
		String res = "";
		
		try {
			
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < str.length(); i++) {
				int code = str.codePointAt(i);
				if (code < 128) {
					sb.append(String.format("%c", code));
				} else {
					sb.append(String.format("\\u%04X", code));
				}
			}
			
			res = sb.toString();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return res;
	}
	


}