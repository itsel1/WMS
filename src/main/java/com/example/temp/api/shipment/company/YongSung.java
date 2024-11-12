package com.example.temp.api.shipment.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ExportVO;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.api.shipment.ShippingServiceImpl;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.vo.PdfPrintVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Service
public class YongSung {
	
	private static String apiKey = "3ecd931bfc114f048f4e90c91";

	@Autowired
	ShippingMapper shipMapper;
	
	@Autowired
	ShippingServiceImpl shipping;
	
	@Value("${filePath}")
	String realFilePath;
	
	@Autowired
	ComnMapper comnMapper;
	
	private final LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
	
	public YongSung() {
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Accept-Language", "ko-KR");
	}

	public Object createRequestBody(HashMap<String, Object> params) {
		String nno = (String) params.get("nno");
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		UserOrderListVO orderInfo = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemInfoList = new ArrayList<UserOrderItemVO>();
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> orderDataList = new ArrayList<LinkedHashMap<String,Object>>();
		ArrayList<LinkedHashMap<String, Object>> itemDataList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> orderDataOne = new LinkedHashMap<String, Object>();
		
		sqlParams.put("nno", nno);
		orderInfo = shipMapper.selectTmpOrderListInfo(sqlParams);
		orderInfo.dncryptData();
		itemInfoList = shipMapper.selectTmpOrderItemInfo(sqlParams);
		
		orderDataOne.put("NationCode", orderInfo.getDstnNation());
		
		// ShippingType B2B, B2C 여부, Get_Buy 1: BTC, 2: BTB
		String shippingType = "EP_REG";
		if (orderInfo.getGetBuy().equals("2")) {
			shippingType = "EP_TAX";
		}
		orderDataOne.put("ShippingType", shippingType);
		orderDataOne.put("OrderNo1", orderInfo.getOrderNo());
		orderDataOne.put("OrderNo2", "");
		orderDataOne.put("FirstMileNo", "");
		orderDataOne.put("SenderName", orderInfo.getShipperName());
		orderDataOne.put("SenderTelno", orderInfo.getShipperTel());
		
		String shipperAdddress = orderInfo.getShipperAddr();
		if (!orderInfo.getShipperAddrDetail().equals("")) {
			shipperAdddress += " " + orderInfo.getShipperAddrDetail();
		}
		orderDataOne.put("SenderAddr", shipperAdddress);
		orderDataOne.put("ReceiverName", orderInfo.getCneeName());
		String nativeReceiverName = orderInfo.getCneeName();
		if (!orderInfo.getNativeCneeName().equals("")) {
			nativeReceiverName = orderInfo.getNativeCneeName();
		}
		orderDataOne.put("ReceiverNameYomigana", nativeReceiverName);
		orderDataOne.put("ReceiverNameExpEng", "");
		
		String receiverTel = orderInfo.getCneeTel();
		if (orderInfo.getCneeTel().equals("") && !orderInfo.getCneeHp().equals("")) {
			receiverTel = orderInfo.getCneeHp();
		}
		orderDataOne.put("ReceiverTelNo1", receiverTel);
		orderDataOne.put("ReceiverTelNo2", orderInfo.getCneeHp());
		orderDataOne.put("ReceiverZipcode", orderInfo.getCneeZip());
		orderDataOne.put("ReceiverState", orderInfo.getCneeState());
		orderDataOne.put("ReceiverCity", orderInfo.getCneeCity());
		orderDataOne.put("ReceiverDistrict", orderInfo.getCneeDistrict());
		
		String receiverAddress = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			receiverAddress += " " + orderInfo.getCneeAddrDetail();
		}
		orderDataOne.put("ReceiverDetailAddr", receiverAddress);
		orderDataOne.put("ReceiverEmail", orderInfo.getCneeEmail());
		orderDataOne.put("ReceiverSocialNo", orderInfo.getCustomsNo());
		orderDataOne.put("RealWeight", Double.parseDouble(orderInfo.getUserWta()));
		orderDataOne.put("WeightUnit", orderInfo.getWtUnit());
		orderDataOne.put("BoxCount", orderInfo.getBoxCnt());
		orderDataOne.put("CurrencyUnit", itemInfoList.get(0).getChgCurrency());
		orderDataOne.put("DelvMessage", orderInfo.getDlvReqMsg());
		orderDataOne.put("UserData1", "");
		orderDataOne.put("UserData2", "");
		orderDataOne.put("UserData3", "");
		orderDataOne.put("DimWidth", Double.parseDouble(orderInfo.getUserWidth()));
		orderDataOne.put("DimLength", Double.parseDouble(orderInfo.getUserLength()));
		orderDataOne.put("DimHeight", Double.parseDouble(orderInfo.getUserHeight()));
		orderDataOne.put("DimUnit", orderInfo.getDimUnit());
		orderDataOne.put("DelvNo", "");
		orderDataOne.put("DelvCom", "");
		orderDataOne.put("StockMode", "");
		orderDataOne.put("SalesSite", orderInfo.getBuySite());
		String payment = "";
		if (orderInfo.getPayment().equals("DDP")) {
			payment = "DDP";
		} else {
			payment = "DDU";
		}
		orderDataOne.put("Incoterms", payment);
		
		for (int i = 0; i < itemInfoList.size(); i++) {
			LinkedHashMap<String, Object> itemDataOne = new LinkedHashMap<String, Object>();
			
			itemDataOne.put("GoodsName", itemInfoList.get(i).getItemDetail());
			itemDataOne.put("Qty", Integer.parseInt(itemInfoList.get(i).getItemCnt()));
			itemDataOne.put("UnitPrice", Double.parseDouble(itemInfoList.get(i).getUnitValue()));
			itemDataOne.put("BrandName", itemInfoList.get(i).getBrand());
			itemDataOne.put("SKU", "");
			itemDataOne.put("HSCODE", itemInfoList.get(i).getHsCode());
			itemDataOne.put("PurchaseUrl", itemInfoList.get(i).getItemUrl());
			itemDataOne.put("Material", itemInfoList.get(i).getItemMeterial());
			itemDataOne.put("Barcode", "");
			itemDataOne.put("ItemOption", "");
			itemDataOne.put("GoodsNameExpEn", "");
			itemDataOne.put("HscodeExpEn", "");
			itemDataOne.put("SagawaItemCode", itemInfoList.get(i).getSagawaItemCode());
			
			itemDataList.add(itemDataOne);
		}
		
		orderDataOne.put("GoodsList", itemDataList);
		orderDataList.add(orderDataOne);
		
		requestData.put("ApiKey", apiKey);
		requestData.put("DataList", orderDataList);
		
		return requestData;
	}

	public synchronized ShipmentVO createShipment(HashMap<String, Object> params, String requestVal) {
		ShipmentVO rstVal = new ShipmentVO();
		String errMsg = "";
		
		try {
			
			String url = "https://eparcel.kr/apiv2/RegData";
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);
			
			if (!responseStr.equals("")) {
				
				JSONObject response = new JSONObject(responseStr);
				
				if (response.optString("Code").equals("0")) {
					JSONArray responseList = new JSONArray(String.valueOf(response.optString("Data")));
					JSONObject responseOne = (JSONObject) responseList.get(0);
					
					String hawbNo = responseOne.optString("RegNo");
					rstVal.setRstStatus("SUCCESS");
					rstVal.setRstCode("S");
					rstVal.setRstHawbNo(hawbNo);
					
				} else {
					JSONArray errorList = new JSONArray(String.valueOf(response.optString("ErrorList")));
					JSONObject errorOne = (JSONObject) errorList.get(0);
					JSONArray errorMsgList = new JSONArray(String.valueOf(errorOne.optString("ErrorMessageList")));
					
					for (int errRoop = 0; errRoop < errorMsgList.length(); errRoop++) {
						
						JSONObject errorMsgOne = (JSONObject) errorMsgList.get(errRoop);
						String errorMessage = errorMsgOne.optString("ErrorMessage");
						
						if (errRoop != errorMsgList.length() - 1) {
							errMsg += errorMessage+", ";
						} else {
							errMsg += errorMessage;
						}
						
					}
				}
				
			} else {
				errMsg = "API 응답 데이터 없음";
			}
			
		} catch (Exception e) {
			if (!errMsg.equals("")) {
				errMsg += ", " + e.getMessage();
			} else {
				errMsg += e.getMessage();
			}
		}
		
		if (!errMsg.equals("")) {
			rstVal.setRstStatus("FAIL");
			rstVal.setRstCode("F");
			rstVal.setRstMsg(errMsg);
		}
		
		return rstVal;
	}

	public synchronized void updateExportLicenseInfo(HashMap<String, Object> orderInfo) {
		String requestVal = "";
		ExportVO exportInfo = new ExportVO();
		String sendResult = "";
		
		try {
			
			exportInfo = shipMapper.selectExportInfo(orderInfo);
			
			LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
			ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String,Object>>();
			
			
			dataOne.put("RegNo", exportInfo.getHawbNo());
			dataOne.put("ExpLicenceNo", exportInfo.getExpNo());
			dataOne.put("ExpLicencePCS", exportInfo.getBoxCnt());
			dataOne.put("ExpLicenceWeight", exportInfo.getWta());
			dataOne.put("ExpLicenceDivision", 0);
			
			dataList.add(dataOne);
			requestData.put("ApiKey", apiKey);
			requestData.put("DataList", dataList);
			requestVal = shipping.jsonObjectToString(requestData);
			
			String url = "https://eparcel.kr/apiv2/UpdateExpLicenceNo";
			ApiAction action = ApiAction.getInstance();
			String responseStr = action.sendPost(requestVal, url, apiHeader);
			sendResult = "F";
			
			if (!responseStr.equals("")) {
				JSONObject response = new JSONObject(responseStr);
				
				if (response.optString("Code").equals("0")) {
					sendResult = "S";
				}
			}

		} catch (Exception e) {
			sendResult = "F";
		}
		
		orderInfo.put("sendYn", sendResult);
		shipMapper.updateExportLicenseInfo(orderInfo);
	}
	
	public void createLabel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType, String orderType) {
		
		try {
			ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
			PdfPrintVO pdfPrintInfo = new PdfPrintVO();
			String userId = (String) request.getSession().getAttribute("USER_ID");
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
			
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String mainPath = cssResource.getURL().getPath();
			String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
			// 문서 만들기
			final PDDocument doc = new PDDocument();
	
			// 폰트 생성
			// ttf 파일 사용하기
			InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
			InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
			InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
			InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
	
			PDType0Font MsGothic = PDType0Font.load(doc, japanese);
			PDType0Font ARIAL = PDType0Font.load(doc, english);
			PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
			PDType0Font NanumGothic = PDType0Font.load(doc, korean);
	
			float perMM = 1 / (10 * 2.54f) * 72;
			
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			for (int i = 0; i < orderNnoList.size(); i++) {
				String blType = "default";
				if (request.getParameter("blType") != null) {
					blType = request.getParameter("blType");
				}
				
				pdfPrintInfo = comnMapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				
				String yslNo = pdfPrintInfo.getHawbNo2();
				String aciNo = pdfPrintInfo.getHawbNo();
				
				String barcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
				String barcodePath2 = pdfPath2 + userId + "_" + aciNo + ".JPEG";
				
				if (blType.equals("default")) {
					blType = comnMapper.selectBlTypeTransCom(pdfPrintInfo);
				}
				
				if (blType.equals("A")) {
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					
					PDPage page = doc.getPage(totalPage);
					
					float pageWidth = pageStandard.getWidth();
					float pageHeight = pageStandard.getHeight();
					
					PDPageContentStream cts = new PDPageContentStream(doc, page);
					Barcode yslBarcode = BarcodeFactory.createCode128(yslNo);
					yslBarcode.setBarHeight(70);
					yslBarcode.setDrawingQuietSection(false);
					Barcode aciBarcode = BarcodeFactory.createCode128(aciNo);
					aciBarcode.setBarHeight(70);
					aciBarcode.setDrawingQuietSection(false);
					
					File barcodeFile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);
					
					File barcodeFile2 = new File(barcodePath2);
					BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);
					
					PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
					PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
					
					String pdfText = "";
					pdfText = printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")";
					
					int yslImgW = yslBarcodeImage.getWidth();
					int yslImgH = yslBarcodeImage.getHeight();
					int pdfYslWidth = 250;
					int pdfYslHeight = (int) ((double) pdfYslWidth * yslImgH / yslImgW);
					System.out.println(pdfYslHeight);
					
					int aciImgW = aciBarcodeImage.getWidth();
					int aciImgH = aciBarcodeImage.getHeight();
					int pdfAciWidth = 220;
					int pdfAciHeight = (int) ((double) pdfAciWidth * aciImgH / aciImgW);
					System.out.println(pdfAciHeight);
					
					float xPoint = (pageWidth - pdfYslWidth) / 2;

					int fontSize = 10;
					float textHeight = fontSize * NanumGothic.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					float textWidth = NanumGothic.getStringWidth(pdfText) / 1000 * fontSize;
					
					cts.beginText();
					cts.newLineAtOffset(20, (pageHeight - 20) - textHeight);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(yslNo+" ▽");
					cts.endText();
					
					cts.beginText();
					cts.newLineAtOffset((pageWidth - 20) - textWidth, (pageHeight - 20) - textHeight);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(pdfText);
					cts.endText();
					
					
					float yPoint = (pageHeight - 30) - textHeight - pdfYslHeight;
					
					cts.drawImage(yslBarcodeImage, xPoint, yPoint, pdfYslWidth, pdfYslHeight);
					
					yPoint = yPoint - 20;
					
					cts.beginText();
					cts.newLineAtOffset(20, yPoint);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(aciNo+" ▽");
					cts.endText();
					
					pdfText = pdfPrintInfo.getOrderNo();
					textWidth = NanumGothic.getStringWidth(pdfText) / 1000 * fontSize;
					
					cts.beginText();
					cts.newLineAtOffset((pageWidth - 20) - textWidth, yPoint);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(pdfText);
					cts.endText();
					
					xPoint = (pageWidth - pdfAciWidth) / 2;
					yPoint = (yPoint - 10) - pdfAciHeight;
					
					cts.drawImage(aciBarcodeImage, xPoint, yPoint, pdfAciWidth, pdfAciHeight);
					
					
					//cts.drawImage(yslBarcodeImage, xPoint, pageHeight - 15, pdfYslWidth, pdfYslHeight);
					
					
					cts.close();
					totalPage++;
					
				}
				
				
				
				File barcodefile = new File(barcodePath);
				if (barcodefile.exists()) {
					//barcodefile.delete();
				}
				
				File barcodefile2 = new File(barcodePath2);
				if (barcodefile2.exists()) {
					//barcodefile2.delete();
				}
				
			}
		


			// 파일 다운로드 설정
			response.setContentType("application/pdf");
			String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
			
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

			// PDF 파일 출력
			doc.save(response.getOutputStream());
			doc.close();
			
		} catch (Exception e) {
			
		}
		
		
	}
}
