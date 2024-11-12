package com.example.temp.trans.fastbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Track;
import javax.swing.text.AbstractDocument.Content;
import javax.xml.transform.Result;

import org.apache.axis.encoding.AnyContentType;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.record.formula.functions.Na;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openxmlformats.schemas.drawingml.x2006.diagram.impl.CTOrgChartImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.accept.AbstractMappingContentNegotiationStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExecutionChain;

import com.amazonaws.Request;
import com.amazonaws.regions.AwsSystemPropertyRegionProvider;
import com.beust.jcommander.internal.Lists;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.ViewMatchingInfo;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.yongsung.ApiOrderItemYSVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PRAcroForm;
import com.microsoft.schemas._2003._10.Serialization.Arrays.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY;
import com.openhtmltopdf.css.style.derived.StringValue;
import com.spire.xls.ExcelPicture;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import ch.qos.logback.core.pattern.Converter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import oracle.net.aso.p;

@Slf4j
@Service
public class FastboxAPI {

	@Autowired
	FastBoxMapper mapper;
	
	@Autowired
	ComnMapper comnMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
	private static final String authKey = "tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";

	
	public ProcedureVO selectBlApply(String orderNno, String userId, String userIp, String transCode) throws Exception {
		ProcedureVO procVal = new ProcedureVO();
		
		
		return procVal;
	}
	
	
	public ProcedureVO selectBlApplyforFB(String orderNno, String userId, String userIp, String transCode) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		String fbRtn = connFastboxApi(orderNno, userId, userIp, transCode);
		try {
			rtnVal = getFastboxAgencyNo(fbRtn, orderNno, userId, userIp);
			if (rtnVal.getRstStatus().equals("FAIL")) {
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
			}
		} catch (Exception e) {
			comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
		}
		
		return rtnVal;
	}
	
	public void selectBlApplyFB(String orderNno, String userId, String userIp, String transCode) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		String fbRtn = connFastboxApi(orderNno, userId, userIp, transCode);
		
		try {
			rtnVal = getFastboxAgencyNo(fbRtn, orderNno, userId, userIp);
			if (rtnVal.getRstStatus().equals("FAIL")) {
				comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
			}
		} catch (Exception e) {
			comnService.insertTMPFromTB(orderNno, rtnVal.getRstMsg(),userId, userIp);
		}
	}
	
	
	private synchronized String connFastboxApi(String orderNno, String userId, String userIp, String transCode) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String jsonVal = makeFastboxOrderJson(orderNno, transCode);
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/order/add/resale");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+authKey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			String headers = getApiSendHeaders(conn);
			conn.disconnect();
			
			JSONObject respJson = new JSONObject(String.valueOf(outResult.toString()));
			
			params.put("nno", orderNno);
			params.put("connUrl", "/api/order/add/resale");
			params.put("headContents", headers);
			params.put("bodyContents", jsonVal);
			params.put("rstContents", respJson.toString());
			params.put("wUserId", userId);
			params.put("wUserIp", userIp);
			mapper.insertApiConn(params);
			
		} catch (Exception e) {
			logger.error("Exception : " + e);
		}

		return outResult.toString();
	}
	
	private String makeFastboxOrderJson(String orderNno, String transCode) throws Exception {
		LinkedHashMap<String, Object> requestData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> goodsList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> goodsOne = new LinkedHashMap<String, Object>();
		ArrayList<ApiOrderItemListVO> orderItem = new ArrayList<ApiOrderItemListVO>();
		ArrayList<ApiOrderListVO> orderInfo = new ArrayList<ApiOrderListVO>();
		FastboxUserInfoVO cusInfo = new FastboxUserInfoVO();
		
		try {
			orderInfo = mapper.selectOrderList(orderNno);
			cusInfo = mapper.selectFastboxCusInfo(orderInfo.get(0).getUserId());
			cusInfo.dncryptData();
			
			for (int i = 0; i < orderInfo.size(); i++) {
				orderInfo.get(i).setSymmetryKey(originKey.getSymmetryKey());
				orderInfo.get(i).dncryptData();
				dataOne = new LinkedHashMap<String, Object>();
				goodsList = new ArrayList<LinkedHashMap<String,Object>>();
				String addr = "";
				String address = "";
				
				if (orderInfo.get(i).getCneeAddrDetail().toString().length() > 0) {
					addr = orderInfo.get(i).getCneeAddr() + " " + orderInfo.get(i).getCneeAddrDetail();
				} else {
					addr = orderInfo.get(i).getCneeAddr();
				}

				address = addr.replaceAll("line.separator".toString(), "");
				dataOne.put("seller_name", cusInfo.getSellerName());
				dataOne.put("ord_date", orderInfo.get(i).getOrderDate());
				dataOne.put("ord_bundle_no", orderInfo.get(i).getNno());
				dataOne.put("currency_code", orderInfo.get(i).getUnitCurrency());
				dataOne.put("country_domain", orderInfo.get(i).getDstnNation());
				dataOne.put("receiver_name", orderInfo.get(i).getCneeName());
				dataOne.put("receiver_name_voice", orderInfo.get(i).getNativeCneeName());
				
				String cneeHp = orderInfo.get(i).getCneeHp();
				cneeHp = cneeHp.replaceAll("[^0-9-]","");
				String cneeTel = orderInfo.get(i).getCneeTel();
				cneeTel = cneeTel.replaceAll("[^0-9-]","");
				
				dataOne.put("receiver_phone", orderInfo.get(i).getCneeTel());
				if (cneeHp.length() < 1) {
					dataOne.put("receiver_cell", cneeTel);
				} else {
					dataOne.put("receiver_cell", cneeHp);
				}
				dataOne.put("receiver_zipcode", orderInfo.get(i).getCneeZip());
				dataOne.put("receiver_address1", orderInfo.get(i).getCneeState());
				dataOne.put("receiver_address2", orderInfo.get(i).getCneeCity());
				dataOne.put("receiver_address3", address);
				
				if (transCode.toUpperCase().equals("FB")) {
					dataOne.put("want_deliverer", "UPS");
				} else if (transCode.toUpperCase().equals("FB-EMS")) {
					dataOne.put("want_deliverer", "EMS");
				}
				
				dataOne.put("resale_brand_name", cusInfo.getBrandName());
				dataOne.put("resale_ceo_name", cusInfo.getAttnName());
				dataOne.put("resale_company_name", cusInfo.getComName());
				dataOne.put("resale_shop_biz_no", cusInfo.getComRegNo());
				dataOne.put("resale_shop_address_eng", cusInfo.getSellerAddrDetail()+" "+cusInfo.getSellerAddr());
				
				if (!orderInfo.get(i).getExpValue().equals("")) {
					dataOne.put("resale_use_export_declare", "P");
					dataOne.put("resale_declare_price_type", "S");
					dataOne.put("resale_customs_id", "미투유코1171017");
					dataOne.put("resale_declare_zipcode", "07641");
					dataOne.put("resale_declare_address1", "서울특별시 강서구 외발산동 217-1");
					dataOne.put("resale_declare_address2", "ACI 빌딩");	
				} else {
					dataOne.put("resale_use_export_declare", "F");
				}
				
				/* 
				if (cusInfo.getExpUseYn().equals("F")) {
					dataOne.put("resale_use_export_declare", cusInfo.getExpUseYn());
				} else {
					dataOne.put("resale_use_export_declare", cusInfo.getExpUseYn());
					dataOne.put("resale_declare_price_type", cusInfo.getExpUnitValue());
					dataOne.put("resale_customs_id", cusInfo.getCustomsNo());
					dataOne.put("resale_declare_zipcode", cusInfo.getShipperZip());
					dataOne.put("resale_declare_address1", cusInfo.getShipperAddr());
					dataOne.put("resale_declare_address2", cusInfo.getShipperAddrDetail());
				}
				*/
				dataOne.put("resale_sender_name", cusInfo.getShipperName());
				if (orderInfo.get(i).getPayment().toUpperCase().equals("DDP")) {
					dataOne.put("resale_delivery_duty_type", "P");
				} else {
					dataOne.put("resale_delivery_duty_type", "U");
				}
				
				orderItem = mapper.selectOrderItemList(orderInfo.get(i).getNno());
				for (int j = 0; j < orderItem.size(); j++) {
					int numbering = j+1;
					goodsOne = new LinkedHashMap<String, Object>();
					goodsOne.put("seller_ord_code", orderItem.get(j).getOrderNo());
					goodsOne.put("seller_ord_item_code", orderItem.get(j).getNno()+"-"+numbering);
					goodsOne.put("prd_code", orderItem.get(j).getCusItemCode());
					goodsOne.put("input_prd_name", orderItem.get(j).getItemDetail());
					goodsOne.put("input_item_name", orderItem.get(j).getItemDiv());
					goodsOne.put("ord_qty", orderItem.get(j).getItemCnt());
					goodsOne.put("selling_price", orderItem.get(j).getUnitValue());
					goodsOne.put("hs_code", orderItem.get(j).getHsCode());
					goodsOne.put("prd_category", orderItem.get(j).getItemDiv());
					goodsOne.put("prd_category_info", orderItem.get(j).getItemDiv());
					
					if (dataOne.get("country_domain").toString().equals("JP")) {
						goodsOne.put("material", orderItem.get(j).getItemMeterial());
						goodsOne.put("cloth_material", orderItem.get(j).getItemMeterial());
					}
					
					goodsList.add(goodsOne);
				}
				dataOne.put("item_list", goodsList);
				dataList.add(dataOne);
			}
			
			requestData.put("request_data", dataList);
			requestData.put("mall_id", "aciexpress2");
			
		} catch (Exception e) {
			logger.error("Exception : " + e);
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(requestData);
		
		return jsonString;
	}
	
	
	private ProcedureVO getFastboxAgencyNo(String fbRtn, String orderNno, String userId, String userIp) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		String rtnHawbNo = "";
		JSONObject json = new JSONObject(String.valueOf(fbRtn));
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("nno", orderNno);
		params.put("userId", userId);
		params.put("userIp", userIp);
		try {
			JSONArray jsonArr = new JSONArray(String.valueOf(json.get("response").toString()));
			JSONObject meta = json.getJSONObject("meta");
			
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject response = (JSONObject) jsonArr.getJSONObject(i);
					if (!response.get("result").toString().equals("false")) {	
						rtnHawbNo = response.get("fb_invoice_no").toString();
						
						params.put("hawbNo", rtnHawbNo);
						comnService.createParcelBl(params);
						rtnVal.setRstStatus("SUCCESS");
						rtnVal.setRstMawbNo("SUCCESS");
						rtnVal.setRstHawbNo(rtnHawbNo);
						rtnVal.setOrgHawbNo(rtnHawbNo);
						
					} else {
						throw new Exception();
					}
				}
			}
		} catch (Exception e) {
			String failMsg = "";
			JSONObject resultJson = new JSONObject();
			resultJson = json.getJSONObject("meta");
			String message = resultJson.get("message").toString();
			JSONArray jsonArr = new JSONArray(String.valueOf(json.get("response").toString()));
			
			if (jsonArr.length() > 0) {
				JSONObject newObj = new JSONObject();
				ArrayList<String> keyList = new ArrayList<String>();
				ArrayList<String> keyList2 = new ArrayList<String>();
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject response = (JSONObject) jsonArr.get(i);
					
					if (response.get("result_reason") instanceof JSONObject) {
						JSONObject resultReason = (JSONObject) response.get("result_reason");
						Iterator<String> keys = resultReason.keys();
						while (keys.hasNext()) {
							String key = keys.next().toString();
							if (key.equals("item_list")) {
								continue;
							} else {
								keyList.add(key);
							}
						}
						for (int o = 0; o < keyList.size(); o++) {
							failMsg += resultReason.get(keyList.get(o).toString())+"\n";
						}
						if (resultReason.has("item_list")) {
							JSONArray itemArray = new JSONArray(String.valueOf(resultReason.get("item_list").toString()));
							for (int j = 0 ; j < itemArray.length(); j++) {
								newObj = (JSONObject) itemArray.get(j);
							}
							Iterator<String> itemKeys = newObj.keys();
							while (itemKeys.hasNext()) {
								String itemKey = itemKeys.next().toString();
								keyList2.add(itemKey);
							}
						}
						for (int x = 0; x < keyList2.size(); x++) {
							failMsg += newObj.get(keyList2.get(x).toString())+"\n";
						}
					} else if (response.get("result_reason") instanceof String) {
						failMsg = response.optString("result_reason");
					}

					parameters.put("nno", orderNno);
					parameters.put("errorMsg",failMsg);
					parameters.put("useYn", "Y");
					parameters.put("wUserId", userId);
					parameters.put("wUserIp", userIp);
					mapper.insertErrorMatch(parameters);
				}
				
			} else {
				failMsg = message;
				parameters.put("nno", orderNno);
				parameters.put("errorMsg",failMsg);
				parameters.put("useYn", "Y");
				parameters.put("wUserId", userId);
				parameters.put("wUserIp", userIp);
				mapper.insertErrorMatch(parameters);
				
			}
			
			comnService.deleteHawbNoInTbHawb(orderNno);
			rtnVal.setRstStatus("FAIL");
			rtnVal.setRstMsg(failMsg);
		}
		return rtnVal;
	}


	// 패스트박스 송장 생성
	public void pdfFastBox(HttpServletRequest request, HttpServletResponse response, ArrayList<FastBoxParameterVO> fbParameters, String orderType) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}

		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		float perMM = 1 / (10 * 2.54f) * 72;
		float perIn = 72;
		
		PDDocument document = new PDDocument();
		response.setContentType("application/pdf");
		
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

		PDType0Font NanumGothic = PDType0Font.load(document, korean);
		
		SecurityKeyVO securityKey = new SecurityKeyVO();
		String symmetryKey = securityKey.getSymmetryKey();
		
		int totalPage = 0;
		
		for (int i = 0; i < fbParameters.size(); i++) {
			String nno = fbParameters.get(i).getNno();
			FastBoxParameterVO fbParameterOne = new FastBoxParameterVO();
			fbParameterOne.setNno(nno);
			FBOrderVO fbOrderList = new FBOrderVO();
			fbOrderList = mapper.selectFbNnoOne(fbParameterOne);
			
			
			PDRectangle pageStandard = new PDRectangle(4 * perIn, 6 * perIn);
			PDPage blankPage = new PDPage(pageStandard);
			//PDPage blankPage = new PDPage(PDRectangle.A6);
			document.addPage(blankPage);
			PDPage page = document.getPage(totalPage);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			
			nno = fbOrderList.getNno();
			
			packingInfo = comnMapper.selectPdfPrintItemInfo(nno);
			String hawbNo = fbOrderList.getHawbNo();
			String storeName = fbOrderList.getStoreName();
			String orderNo = fbOrderList.getOrderNo();
			String dstnNation = fbOrderList.getDstnNation();
			String chgCurrency = fbOrderList.getChgCurrency();
			String itemCnt = String.valueOf(fbOrderList.getItemCnt());
			String totalValue = String.valueOf(fbOrderList.getTotalValue());
			
			Barcode barcodes = BarcodeFactory.createCode128(hawbNo);
			barcodes.setLabel("Barco");
			barcodes.setDrawingText(true);

			String barcodePath = pdfPath2 +hawbNo+".JPEG";
			File barcodefile = new File(barcodePath);
			
			try {
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			} catch (OutputException e) {
				e.printStackTrace();
			}
			
			PDImageXObject hawbBarCode = PDImageXObject.createFromFileByContent(barcodefile, document);
			contentStream.drawImage(hawbBarCode, 16*perMM, 136*perMM, 70*perMM, 12*perMM);

			contentStream.beginText();
			contentStream.newLineAtOffset(30*perMM, 132*perMM);
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.showText(hawbNo);
			contentStream.endText();
			
			float titleWidth = NanumGothic.getStringWidth(hawbNo) / 1000 * 9;
			int fontSize = 10;
			// bottom
			//contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);
			// top
			contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM);
			// left
			//contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
			contentStream.drawLine(3 * perMM, 152 * perMM - 22 * perMM, 3 * perMM, 109 * perMM);
			// right
			//contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 22 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset(5 * perMM, 125.5f * perMM);
			contentStream.showText("상점명");
			contentStream.endText();
			
			contentStream.drawLine(20 * perMM, 130 * perMM, 20 * perMM, 109 * perMM);
			contentStream.drawLine(3 * perMM, 123 * perMM, (pageStandard.getWidth()) - 3 * perMM, 123 * perMM);
			
			titleWidth = NanumGothic.getStringWidth((0+1) + "/" + 1)  / 1000 * fontSize;
			
		
			contentStream.drawLine((pageStandard.getWidth()) - 20 * perMM, 130 * perMM, (pageStandard.getWidth()) - 20 * perMM, 109 * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 36 * perMM, 130 * perMM, (pageStandard.getWidth()) - 36 * perMM, 109 * perMM);

			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset(5 * perMM, 118.5f * perMM);
			contentStream.showText("합포번호");
			contentStream.endText();
			
			contentStream.drawLine(3 * perMM, 116 * perMM, (pageStandard.getWidth()) - 3 * perMM, 116 * perMM);
			contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset(5 * perMM, 111.5f * perMM);
			contentStream.showText("국가코드");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 125.5f * perMM);
			contentStream.showText("수량합계");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 118.5f * perMM);
			contentStream.showText("가격합계");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 34.5f * perMM, 111.5f * perMM);
			contentStream.showText("통화");
			contentStream.endText();

			
			// 주문 정보 START
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset(21 * perMM,  125.5f * perMM);
			contentStream.showText(storeName);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset(21 * perMM, 118.5f * perMM);
			contentStream.showText(orderNo);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset(21 * perMM, 111.5f * perMM);
			contentStream.showText(dstnNation);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 125.5f * perMM);
			contentStream.showText(itemCnt);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 118.5f * perMM);
			contentStream.showText(totalValue);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, 9);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 19 * perMM, 111.5f * perMM);
			contentStream.showText(chgCurrency);
			contentStream.endText();

			
			contentStream.drawLine(3 * perMM, 109 * perMM, 3 * perMM, 102 * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 16 * perMM, 109 * perMM, (pageStandard.getWidth()) - 16 * perMM, 102 * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 102 * perMM);
			contentStream.drawLine(3 * perMM, 102 * perMM, (pageStandard.getWidth()) - 3 * perMM, 102 * perMM);
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset(4.5f * perMM, 104 * perMM);
			contentStream.showText("상품명");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.newLineAtOffset((pageStandard.getWidth()) - 12.5f * perMM, 104 * perMM);
			contentStream.showText("수량");
			contentStream.endText();
			
			//contentStream.drawLine(3 * perMM, 102 * perMM, 3 * perMM, 90 * perMM);
			
			// 상품정보 START
			ArrayList<LinkedHashMap<String, Object>> itemList = new ArrayList<LinkedHashMap<String, Object>>();
			itemList = mapper.selectFBItem(nno);
			
			float startLine = 96.5f;
			
			for (int j = 0; j < itemList.size(); j++) {
				String itemDetail = "";
				itemDetail = itemList.get(j).get("itemDetail").toString();
				/*
				if (itemList.get(j).get("itemDetail").toString().length() > 20) {
					itemDetail = stringTransfer(itemList.get(j).get("itemDetail").toString(), 20)+"...";	
				} else {
					itemDetail = itemList.get(j).get("itemDetail").toString();
				}
				*/
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 8);
				contentStream.newLineAtOffset(4.5f * perMM, startLine * perMM);
				//contentStream.showText(itemList.get(j).get("itemDetail").toString()+" / "+itemList.get(j).get("itemDiv").toString());
				//contentStream.showText(itemDetail+" / " +itemList.get(j).get("itemDiv").toString());
				contentStream.showText(itemDetail);
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 8);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 6 * perMM, startLine * perMM);
				contentStream.showText(itemList.get(j).get("itemCnt").toString());
				contentStream.endText();
				
				startLine -= 4.5f;
			}
			
			float endLine = startLine;
			
			contentStream.drawLine(3 * perMM, 102 * perMM, 3 * perMM, endLine * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 16 * perMM, 102 * perMM, (pageStandard.getWidth()) - 16 * perMM, endLine * perMM);
			contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 102 * perMM, (pageStandard.getWidth()) - 3 * perMM, endLine * perMM);
			contentStream.drawLine(3 * perMM, endLine * perMM, (pageStandard.getWidth()) - 3 * perMM, endLine * perMM);
			
			float endLine2 = endLine - 4;

			contentStream.close();

			totalPage++;
			if (orderType.toLowerCase().equals("y")) {
				totalPage = totalPage;
				
				PDRectangle pageStandard2 = new PDRectangle(100 * perMM, 152 * perMM);
				PDPage blankPage2 = new PDPage(pageStandard2);
				document.addPage(blankPage2);
				PDPage page2 = document.getPage(totalPage);
				PDPageContentStream cts = new PDPageContentStream(document, page2);
				
				cts.drawLine(10, 420, (pageStandard.getWidth()) - 10, 420);
				cts.drawLine(10, 370, (pageStandard.getWidth()) - 10, 370);
				cts.drawLine(10, 420, 10, 370);
				cts.drawLine((pageStandard.getWidth()) - 10, 420, (pageStandard.getWidth()) - 10, 370);
				cts.drawLine((pageStandard.getWidth()) - 70, 420, (pageStandard.getWidth()) - 70, 370);
				cts.drawLine((pageStandard.getWidth()) - 70, 400, (pageStandard.getWidth()) - 10, 400);
				
				
				cts.beginText();
				cts.setFont(NanumGothic, 18);
				cts.newLineAtOffset(14.5f, 395f);
				cts.showText(hawbNo);
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 14);
				cts.newLineAtOffset(16.5f, 378f);
				cts.showText(orderNo);
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 407);
				cts.showText("총 내품수량");
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 12);
				cts.newLineAtOffset((pageStandard.getWidth()) - 45, 383.5f);
				cts.showText(itemCnt);
				cts.endText();
				
				cts.drawLine(10, 370, 10, 350);
				cts.drawLine((pageStandard.getWidth()) - 10, 370, (pageStandard.getWidth()) - 10, 350);
				cts.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				
				cts.drawLine((pageStandard.getWidth()) - 135, 370, (pageStandard.getWidth()) - 135, 350);
				cts.drawLine((pageStandard.getWidth()) - 90, 370, (pageStandard.getWidth()) - 90, 350);
				cts.drawLine((pageStandard.getWidth()) - 55, 370, (pageStandard.getWidth()) - 55, 350);
				
				cts.beginText();
				cts.setFont(NanumGothic, 10);
				cts.newLineAtOffset(15, 355.5f);
				cts.showText("내품명");
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 130, 355.5f);
				cts.showText("사입코드");
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 82, 355.5f);
				cts.showText("수량");
				cts.endText();
				
				cts.beginText();
				cts.setFont(NanumGothic, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 45, 355.5f);
				cts.showText("Rack");
				cts.endText();

				int limit = 0;
				float startLine2 = 338;
				String itemDetail = "";
				
				for (int items = 0; items < packingInfo.size(); items++) {
					float fixLine = 5;
					
					itemDetail = packingInfo.get(items).get("itemDetail").toString();
					itemDetail = packingInfo.get(items).get("itemDetail").toString();
					if (itemDetail.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
						limit = 22;
					} else {
						limit = 32;
					}
					
					int arraySize = (int) Math.ceil((double) itemDetail.length() / limit);
					String[] stringArr = new String[arraySize];
					
					int index = 0;
					for (int start = 0; start < itemDetail.length(); start += limit) {
						stringArr[index++] = itemDetail.substring(start, Math.min(start+limit, itemDetail.length()));
					}
					cts.beginText();
					cts.setFont(NanumGothic, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 132, startLine2 - 3);
					cts.showText(packingInfo.get(items).get("takeInCode").toString());
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 79, startLine2 - 3);
					cts.showText(packingInfo.get(items).get("itemCnt").toString());
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 47, startLine2 - 3);
					cts.showText(packingInfo.get(items).get("rackCode").toString());
					cts.endText();
					
					for (int x = 0; x < stringArr.length; x++) {
						cts.beginText();
						cts.setFont(NanumGothic, 8);
						cts.newLineAtOffset(12, startLine2 - 3.5f);
						cts.showText(stringArr[x]);
						cts.endText();
						
						if (x == stringArr.length-1)  {
							startLine2 -= 17;
						} else {
							startLine2 -= 15;
						}
					}
				}
				
				float endLine3 = startLine2;
				
				cts.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				cts.drawLine(10, 355.5f, 10, endLine3+6.5f);
				cts.drawLine((pageStandard.getWidth()) - 10, 355.5f, (pageStandard.getWidth()) - 10, endLine3+6.5f);
				cts.drawLine(10, endLine3+6.5f, (pageStandard.getWidth()) - 10, endLine3+6.5f);
				
				cts.close();
				
				totalPage++;
			}
		}

		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		
		// PDF 파일 출력
		document.save(response.getOutputStream());
		document.close();
	}
	
	// FB 송장 출력 시 상품명이 칸을 벗어나는 경우 메서드 실행
	public static String stringTransfer(String str, int limit) {
		if (str == null) {
			return str;
		}
		
		if (limit <= 0) {
			return str;
		}
		
		byte[] strbyte = str.getBytes();
		
		if (str.length() <= limit) {
			return str;
		}
		
		char[] charArray = str.toCharArray();
		
		int checkLimit = limit;
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] < 256) {
				checkLimit -= 1;
			} else {
				checkLimit -= 2;
			}
			
			if (checkLimit <= 0) {
				break;
			}
		}
		
		byte[] newByte = new byte[limit + checkLimit];
		
		for (int j = 0; j < newByte.length; j++) {
			newByte[j] = strbyte[j];
		}
		
		return new String(newByte);
	}
	
	public HashMap<String, Object> requestShipping(String mawbNo) throws Exception {
		HashMap<String, Object> rst = new HashMap<>();
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		dataList = mapper.selectRequestShippingDataList(mawbNo);
		int totalCnt = dataList.size();
		
		if (totalCnt == 0) {
			rst.put("code", "S");
			rst.put("msg", "배송지시 가능한 데이터가 없습니다.");
			return rst;
		}
		
		HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
		HashMap<String, Object> requestData = new HashMap<>();
		ArrayList<String> hawbList = new ArrayList<>();
		String arrivalDate = (String) dataList.get(0).get("arrDate");
		
		for (int i = 0; i < dataList.size(); i++) {
			HashMap<String, Object> dataOne = dataList.get(i);
			hawbList.add((String) dataOne.get("hawbNo"));
		}
		
		requestData.put("fb_invoice_no", hawbList);
		requestData.put("instruction_requester", "ACI");
		requestData.put("requester_phone", "070-4436-6514");
		requestData.put("packing_status", "O");
		requestData.put("delivery_type", "P");
		requestData.put("arrival_due_date", arrivalDate);
		requestBodyMap.put("mall_id", "aciexpress2");
		requestBodyMap.put("request_data", requestData);
		
		Gson gson = new Gson();
		String requestBody = gson.toJson(requestBodyMap);
		
		System.out.println("배송지시 api request : " + requestBody);
		
		String val = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			
			URL url = new URL("https://dhub-api.cafe24.com/api/delivery/instruction");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+authKey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(requestBody.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			
			conn.disconnect();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		String responseBody = outResult.toString();
		JSONObject jsonObject = new JSONObject(responseBody);
		JSONObject metaObject = jsonObject.getJSONObject("meta");
		String code = metaObject.optString("code");
		
		if ("200".equals(code)) {
			JSONObject response = jsonObject.getJSONObject("response");
			JSONArray responseArray = response.getJSONArray("result");
			
			for (int j = 0; j < responseArray.length(); j++) {
				JSONObject resultOne = responseArray.getJSONObject(j);
				HashMap<String, Object> sqlParams = new HashMap<>();
				sqlParams.put("hawbNo", resultOne.optString("fb_invoice_no"));
				sqlParams.put("nno", resultOne.optString("ord_bundle_no"));
				if (!resultOne.optBoolean("result")) {
					totalCnt--;
					String resultMsg = resultOne.optString("result_reason");
					sqlParams.put("sendYn", "F");
					sqlParams.put("msg", StringEscapeUtils.unescapeJava(resultMsg));
				} else {
					sqlParams.put("sendYn", "Y");
					sqlParams.put("msg", "");
				}
				
				mapper.insertTbFastboxSend(sqlParams);
			}
			
			rst.put("code", "S");
			rst.put("msg", "총 " + dataList.size()+"건 중 "+totalCnt+"건 처리 되었습니다.");
			return rst;
		} else {
			rst.put("code", "F");
			rst.put("msg", "배송지시 API 요청에 실패 하였습니다.");
			return rst;
		}
		
		
	}

	public ProcedureVO requestFastboxDelivery(String hawbNo, String mawbNo) throws Exception {
		ProcedureVO result = new ProcedureVO();
		String nno = mapper.selectNNObyHawbNo(hawbNo);
		String jsonVal = requestDelivery(nno, mawbNo);
		JSONObject json = new JSONObject(jsonVal);
		
		try {
			JSONObject meta = json.getJSONObject("meta");
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = json.getJSONObject("response");
				JSONArray jsonArr = new JSONArray(String.valueOf(response.get("result").toString()));
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject resultJson = (JSONObject) jsonArr.getJSONObject(i);
					if (!resultJson.get("result").toString().equals("false")) {
						result.setRstStatus("SUCCESS");
					} else {
						throw new Exception();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : ", e);
			result.setRstStatus("FAIL");
		}
		
		return result;
	}

	public String requestDelivery(String nno, String mawbNo) throws Exception {
		String rtnVal = makeFastBoxRequestDeliJson(nno, mawbNo);
		String val = "";
		System.out.println(rtnVal);
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		HashMap<String, Object> result = new HashMap<String, Object>();
		result = mapper.selectUserInfo(nno);
		String userId = mapper.selectUserId(nno);

		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/delivery/instruction");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+authKey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(rtnVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			
			conn.disconnect();
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(outResult.toString());
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		params.put("connUrl", "/api/delivery/instruction");
		params.put("headContents", val);
		params.put("bodyContents", rtnVal);
		params.put("rstContents", jsonString);
		params.put("wUserId", result.get("USER_ID").toString());
		params.put("wUserIp", result.get("W_USER_IP").toString());
		mapper.insertApiConn(params);
		
		return outResult.toString();
	}


	private String makeFastBoxRequestDeliJson(String nno, String mawbNo) throws Exception {
		String jsonString = "";
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		ArrayList<String> hawbNoList = new ArrayList<String>();
		ApiOrderListVO orderInfo = new ApiOrderListVO();
		String arrDate = "";
		FastboxInfoVO mallInfo = new FastboxInfoVO();
		
		try {
			orderInfo = mapper.selectOrderInfo(nno);
			orderInfo.shipperTelDncrypt(originKey.getSymmetryKey());
			arrDate = mapper.selectArrDateMawbNo(mawbNo);
			hawbNoList.add(orderInfo.getHawbNo());
			dataOne.put("fb_invoice_no", hawbNoList);
			dataOne.put("instruction_requester", orderInfo.getShipperName());
			dataOne.put("requester_phone", orderInfo.getShipperTel());
			dataOne.put("packing_status", "O");
			dataOne.put("delivery_type", "P");
			dataOne.put("arrival_due_date", arrDate);
			
			mallInfo = mapper.selectMallInfo(orderInfo.getUserId());
			//data.put("mall_id", mallInfo.getMallId());
			data.put("mall_id", "aciexpress2");
			data.put("request_data", dataOne);
			
			Gson gson = new Gson();
			jsonString = gson.toJson(data);
			System.out.println("*************");
			System.out.println(jsonString);
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		
		return jsonString;
	}
	
	public String fnMakeFastBoxUpdateExpLicenceNoJson(String nno) throws Exception {
		String jsonVal = makeFastBoxUpdateExpLicenceNoJson(nno);
		System.out.println("Fastbox Update Export Declare ----> ");
		System.out.println(jsonVal);
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		result = mapper.selectUserInfo(nno);
		
		String val = "";
		
		if(jsonVal.equals("")) {
			return "";
		}
		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/Export/Declaration/Number/Update");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+authKey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			conn.disconnect();
			
			JSONObject json = new JSONObject(String.valueOf(outResult.toString()));
			JSONObject meta = new JSONObject(String.valueOf(json.get("meta").toString()));
			
			if (!meta.get("code").toString().equals("200")) {
				String rtnMsg = "";
				rtnMsg += meta.get("message").toString();
				params.put("nno", nno);
				params.put("rtnMsg", rtnMsg);
				mapper.updateExpLicenceN(params);
			} else {
				JSONArray jsonArr = new JSONArray(String.valueOf(json.get("response").toString()));
				String rtnMsg = "";
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject json2 = (JSONObject) jsonArr.get(i);
					JSONObject json3 = new JSONObject(String.valueOf(json2.get("result_reason").toString())); 
					if (!json2.get("result").toString().equals("true")) {
						if (json3.has("fb_invoice_no")) {
							rtnMsg += json3.get("fb_invoice_no").toString()+" ";
						}
						if (json3.has("export_declare_no")) {
							rtnMsg += json3.get("export_declare_no").toString()+" ";
						}
						params.put("nno", nno);
						params.put("rtnMsg", rtnMsg);
						mapper.updateExpLicenceN(params);
					} else {
						params.put("nno", nno);
						params.put("rtnMsg", rtnMsg);
						mapper.updateExpLicence(params);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(outResult.toString());
		
		HashMap<String, Object> params2 = new HashMap<String, Object>();
		params2.put("nno", nno);
		params2.put("connUrl", "/api/Export/Declaration/Number/Update");
		params2.put("headContents", val);
		params2.put("bodyContents", jsonVal);
		params2.put("rstContents", jsonString);
		params2.put("wUserId", result.get("USER_ID").toString());
		params2.put("wUserIp", result.get("W_USER_IP").toString());
		mapper.insertApiConn(params2);
		
		return outResult.toString();
	}


	private String makeFastBoxUpdateExpLicenceNoJson(String nno) throws Exception {
		String jsonString = "";
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		
		FastboxInfoVO mallInfo = new FastboxInfoVO();
		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		//orderInfo = mapper.selectOrderExpLicenceInfo(nno);
		orderInfo = mapper.selectOrderExpInfo(nno);
		
		String expNo = (String) orderInfo.get("expNo");
		String hawbNo = (String) orderInfo.get("hawbNo");
		
		dataOne.put("fb_invoice_no", hawbNo);
		dataOne.put("export_declare_no", expNo);
		dataList.add(dataOne);
		
		//mallInfo = mapper.selectMallInfo(orderInfo.get("USER_ID").toString());
		//data.put("mall_id", mallInfo.getMallId());
		data.put("mall_id", "aciexpress2");
		data.put("request_data", dataList);

		Gson gson = new Gson();
		jsonString = gson.toJson(data);
		
		return jsonString;
	}

	public String makeFastBoxPod(String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> rtnJsonArray = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> trackJson = new LinkedHashMap<String, Object>();
		String jsonString = "";
		String nno = mapper.selectNNObyHawbNo(hawbNo);
		String val = "";
		
		trackJson.put("mall_id", "aciexpress2");
		trackJson.put("fb_invoice_no", hawbNo);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(trackJson);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/Tracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+authKey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			
			OutputStream os = conn.getOutputStream();								// Request Data에 jsonString을 담기 위해 객체 생성
			os.write(jsonString.getBytes());										// jsonString 세팅
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			
			conn.disconnect();
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		
		JSONObject jsonObject = new JSONObject(String.valueOf(outResult.toString()));

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", "");
		params.put("connUrl", "/api/Tracking");
		params.put("headContents", val);
		params.put("bodyContents", trackJson.toString());
		params.put("rstContents", jsonObject.toString());
		params.put("wUserId", request.getSession().getAttribute("USER_ID"));
		params.put("wUserIp", request.getRemoteAddr());
		mapper.insertApiConn(params);
		
		return outResult.toString();
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailArrayView(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		
		String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
		String mawbInDate = apiMapper.selectMawbInDate(hawbNo);		// 출고
		String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
	
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		JSONObject meta = new JSONObject(String.valueOf(json.get("meta").toString()));
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("hawbNo", hawbNo);
		HashMap<String, Object> blInfo = new HashMap<String, Object>();
		blInfo = mapper.selectBlInfo(parameters);
		try {
			
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = new JSONObject(String.valueOf(json.get("response").toString()));
				JSONArray traceList = new JSONArray(String.valueOf(response.get("trace").toString()));
				JSONObject order = new JSONObject(String.valueOf(response.get("order").toString()));
				String agencyBl = order.get("Domestic_Invoice_No").toString();
				parameters.put("agencyBl", agencyBl);
				parameters.put("transCode", blInfo.get("transCode").toString());
				parameters.put("nno", blInfo.get("nno").toString());
//				int cnt = mapper.selectBlCnt(parameters);
//				if (cnt < 1) {
//					mapper.insertMatchingInfo(parameters);
//				}
				for (int i = 0; i < traceList.length(); i++) {
					podDetail = new LinkedHashMap<String, Object>();
					JSONObject traceJson = (JSONObject) traceList.get(i);
					String location = traceJson.get("location").toString();
					String location2;
					
					if (location == "" || location.equals("") || location == null || location.equals("null")) {
						location2 = "-";
					} else {
						location2 = traceJson.get("location").toString();
					}
					
					
					String trkDate = traceJson.getString("set_date_time").toString();
					String datess = trkDate.replaceAll("T", " ");
					String date = datess.substring(0, 16);
					
					if (location2.contains("Fastbox")) {
						podDetail.put("UpdateLocation", "Republic of Korea");
					} else {
						podDetail.put("UpdateLocation", location2);
					}
					
					podDetail.put("UpdateCode", traceJson.get("status_code"));
					podDetail.put("UpdateDateTime", date);
					podDetail.put("UpdateDescription", traceJson.get("status_msg"));
					podDetatailArray.add(podDetail);
				}
			}
			
		} catch (Exception e) {
			logger.error("Exception : ", e);
			podDetail = new LinkedHashMap<String,Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetail.put("ProblemCode","-22"); 
			podDetail.put("Comments", "데이터가 없습니다.(No Data)");
			podDetatailArray.add(podDetail);
		}
		
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailArray(String rtnJson, String hawbNo, HttpServletRequest request) throws Exception {
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		JSONObject json = new JSONObject(String.valueOf(rtnJson));
		System.out.println(json);
		
		String hawbInDate = apiMapper.selectHawbInDate(hawbNo);	// 입고
		String mawbInDate = apiMapper.selectMawbInDate(hawbNo);	// 출고
		String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
		
		LinkedHashMap<String, Object> podDetail = new LinkedHashMap<String, Object>();
		JSONObject meta = new JSONObject(String.valueOf(json.get("meta").toString()));
		
		try {
			
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = new JSONObject(String.valueOf(json.get("response").toString()));
				JSONArray traceList = new JSONArray(String.valueOf(response.get("trace").toString()));
				
				for (int i = 0; i < traceList.length(); i++) {
					podDetail = new LinkedHashMap<String, Object>();
					JSONObject traceJson = (JSONObject) traceList.get(i);

					String trkDate = traceJson.getString("set_date_time").toString();
					String datess = trkDate.replaceAll("T", " ");
					String date = datess.substring(0, 16);
					String location = traceJson.get("location").toString();
					String location2;
					
					if (location == "" || location.equals("") || location == null || location.equals("null")) {
						location2 = "-";
					} else {
						location2 = traceJson.get("location").toString();
					}
					
					
					if (traceJson.get("status_code").toString().equals("Delivered")) {
						podDetail.put("UpdateCode", "600");
						podDetail.put("UpdateDateTime", date);
						podDetail.put("UpdateLocation", location2);
						podDetail.put("UpdateDescription", "Delivered");
					} else if (traceJson.get("status_code").toString().equals("OutForDelivery")) {
						podDetail.put("UpdateCode", "500");
						podDetail.put("UpdateDateTime", date);
						podDetail.put("UpdateLocation", location2);
						podDetail.put("UpdateDescription", "Out for Delivery");
					} else if (traceJson.get("status_code").toString().equals("InTransit")) {
						podDetail.put("UpdateCode", "400");
						podDetail.put("UpdateDateTime", date);
						podDetail.put("UpdateLocation", location2);
						podDetail.put("UpdateDescription", "In trasit");
					} else if (traceJson.get("status_code").toString().equals("RFI")) {
						podDetail.put("UpdateCode", "300");
						podDetail.put("UpdateDateTime", date);
						podDetail.put("UpdateLocation", location2);
						podDetail.put("UpdateDescription", "Shipped out");
					} else if (traceJson.get("status_code").toString().equals("RPE")) {
						podDetail.put("UpdateCode", "200");
						podDetail.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Finished warehousing");
					} else if (traceJson.get("status_code").toString().equals("ORE")) {
						podDetail.put("UpdateCode", "100");
						podDetail.put("UpdateDateTime", date);
						podDetail.put("UpdateLocation", "Republic of Korea");
						podDetail.put("UpdateDescription", "Order information has been entered");
					} else {
						continue;
					}
					podDetatailArray.add(podDetail);
				}
			}
			
		} catch (Exception e) {
			logger.error("Exception : ", e);
			podDetail = new LinkedHashMap<String,Object>();
			podDetail.put("UpdateCode", "-200"); 
			podDetail.put("UpdateDateTime", "");
			podDetail.put("UpdateLocation", "");
			podDetail.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetail.put("ProblemCode","-22"); 
			podDetail.put("Comments", "데이터가 없습니다.(No Data)");
			podDetatailArray.add(podDetail);
		}
		
		return podDetatailArray;
	}

	public ArrayList<HashMap<String, Object>> createSeller(HttpServletRequest request, HttpServletResponse response, ArrayList<String> userList) throws Exception {
		ArrayList<HashMap<String, Object>> proc = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		ProcedureVO rtnVal = new ProcedureVO();
		String jsonVal = null;
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String userIp = request.getRemoteAddr();
		
		for (int i = 0; i < userList.size(); i++) {
			results = new HashMap<String, Object>();
			String sellerName = userList.get(i);
			jsonVal = fnMakeCreateSellerJson(sellerName, userId, userIp); 
			rtnVal = getCreateSellerResponse(jsonVal, userId, userIp);
			if (rtnVal.getRstStatus().equals("FAIL")) {
				results.put("status", "F");
				results.put("userId", sellerName);
				results.put("msg", rtnVal.getRstMsg());
			} else if (rtnVal.getRstStatus().equals("SUCCESS")) {
				results.put("status", "S");
				results.put("userId", sellerName);
				results.put("msg", "등록 성공");
				
				mapper.updateFastboxSendYn(results);
			}
			
			proc.add(results);
		}
		
		
		return proc;
	}

	private ProcedureVO getCreateSellerResponse(String jsonVal, String userId, String userIp) throws Exception {
		ProcedureVO rtnVal = new ProcedureVO();
		JSONObject json = new JSONObject(String.valueOf(jsonVal));
		JSONObject meta = json.getJSONObject("meta");
		
		try {	
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				rtnVal.setRstStatus("SUCCESS");
				rtnVal.setRstMsg("SUCCESS");
			}
		} catch (Exception exception) {
			JSONArray msgList = new JSONArray(String.valueOf(meta.get("message").toString()));
			String failMsg = "";
			for (int i = 0; i < msgList.length(); i++) {
				JSONObject json1 = (JSONObject) msgList.getJSONObject(i);
				if (json1.has("seller_name")) {
					failMsg += "판매사명 / ";
				}
				if (json1.has("currency_code")) {
					failMsg += "결제통화 / ";
				}
			}
			rtnVal.setRstStatus("FAIL");
			rtnVal.setRstMsg(failMsg);
		}
		return rtnVal;
	}

	private String fnMakeCreateSellerJson(String sellerName, String userId, String userIp) throws Exception {
		String jsonVal = makeCreateSellerJson(sellerName);
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		
		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/seller/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("consumerKey", consumerKey);
			conn.setRequestProperty("Authorization", "Bearer "+auth);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonVal.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}

			String val = "";
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			
			conn.disconnect();
			
			params.put("nno", "");
			params.put("connUrl", "/api/order/add");
			params.put("headContents", val);
			params.put("bodyContents", jsonVal);
			params.put("rstContents", val);
			params.put("wUserId", userId);
			params.put("wUserIp", userIp);
			mapper.insertApiConn(params);
			

		} catch (Exception exception) {
			logger.error("Exception : " + exception);
		}
		
		return outResult.toString();
	}

	private String makeCreateSellerJson(String sellerName) throws Exception {
		LinkedHashMap<String, Object> resultData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> requestData = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		
		try {
			resultData.put("mall_id", "aciexpress2");
			dataOne.put("seller_name", sellerName);
			if (sellerName.equals("teststep3") || sellerName.equals("itsel3")) {
				dataOne.put("currency_code", "");	
			} else {
				dataOne.put("currency_code", "USD");			
			}
//			dataOne.put("currency_code", "USD");
			requestData.add(dataOne);
			resultData.put("request_data", requestData);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		Gson gson = new Gson();
		String jsonString = gson.toJson(resultData);
		
		return jsonString;
	}

	public void getFastboxWeight() throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<HashMap<String, Object>> viewList = new ArrayList<HashMap<String, Object>>();
		viewList = mapper.selectFastboxViewList();
		
		String mallId = "aciexpress2";
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		for (int idx = 0; idx < viewList.size(); idx++) {
			
			String inputLine = null;
			StringBuffer outResult = new StringBuffer();
			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> wtParams = new HashMap<String, Object>();
			LinkedHashMap<String, Object> jsonBody = new LinkedHashMap<String, Object>();
			
			String val = "";
			String rtnWTA = "";
			String rtnWTC = "";
			
			String hawbNo = viewList.get(idx).get("hawbNo").toString();
			String nno = viewList.get(idx).get("nno").toString();
			String transCode = viewList.get(idx).get("transCode").toString();
			
			jsonBody.put("mall_id", mallId);
			jsonBody.put("fb_invoice_no", hawbNo);
			Gson gson = new Gson();
			String jsonVal = gson.toJson(jsonBody);

			try {
				URL url = new URL("https://dhub-api.cafe24.com/api/order/detail");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("consumerKey", consumerKey);
				conn.setRequestProperty("Authorization", auth);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Accept-Language", "ko-KR");
				conn.setConnectTimeout(50000);
				conn.setReadTimeout(50000);
				
				OutputStream os = conn.getOutputStream();
				os.write(jsonVal.getBytes());											
				os.flush();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while ((inputLine = in.readLine()) != null) {
					outResult.append(inputLine);
				}
				
				Map headers = conn.getHeaderFields();
				Set<String> keys = headers.keySet();
				for  (String key : keys) {
					val += key + " : " + conn.getHeaderField(key) + ", ";
				}
				
				conn.disconnect();
				
				params.put("nno", nno);
				params.put("connUrl", "/api/fastbox/weight");
				params.put("headContents", val);
				params.put("bodyContents", jsonVal);
				params.put("rstContents", outResult.toString());
				params.put("wUserId", member.getUsername());
				params.put("wUserIp", member.getIp());
				mapper.insertApiConn(params);
				
				JSONObject jsonObject = new JSONObject(String.valueOf(outResult.toString()));
				JSONObject metaObj = jsonObject.getJSONObject("meta");
				
				if (!metaObj.get("code").toString().equals("200")) {
					throw new Exception();
				} else {
					JSONArray jsonArr = new JSONArray(String.valueOf(jsonObject.get("response").toString()));
					JSONObject response = (JSONObject) jsonArr.getJSONObject(0);
					if (!response.isNull("package_weight")) {
						Double wta = response.getDouble("package_weight");
						Double wtc = response.getDouble("volume_weight");
						wtParams.put("agencyBl", hawbNo);
						wtParams.put("wta", wta);
						wtParams.put("wtc", wtc);
						wtParams.put("transCode", transCode);
						mapper.insertFastboxWeight(wtParams);
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	private String fnMakeFastboxWeightJson(String nno, String hawbNo) {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LinkedHashMap<String, Object> json = new LinkedHashMap<String, Object>();
		String val = "";
		String mallId = "aciexpress2";
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		json.put("mall_id", mallId);
		json.put("fb_invoice_no", hawbNo);
		Gson gson = new Gson();
		String jsonVal = gson.toJson(json);
		return jsonVal;
	}

	private String fnMakeFastboxJsonWeight(String hawbNo) throws Exception {
		String jsonVal = "";
		String consumerKey = "";
		String auth = "";
		String mallId = "";
		String val = "";
		String nno = mapper.selectNNObyHawbNo(hawbNo);
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		LinkedHashMap<String, Object> json = new LinkedHashMap<String, Object>();
		
		mallId = "aciexpress2";
		consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		json.put("mall_id", mallId);
		json.put("fb_invoice_no", hawbNo);
		Gson gson = new Gson();
		jsonVal = gson.toJson(json);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/order/detail");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();		// URL 연결
			conn.setDoOutput(true);													// OutputStream으로 post 데이터를 넘김
			conn.setRequestMethod("POST");											// 요청 방식 POST
			conn.setRequestProperty("consumerKey", consumerKey);					// 요청 헤더 세팅
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/json");			// json 타입으로 전송
			conn.setRequestProperty("Accept", "application/json");					// 응답 데이터를 json 타입으로 요청
			conn.setRequestProperty("Accept-Language", "ko-KR");					// ko-KR 언어로 지원
			conn.setConnectTimeout(50000);											// timeout
			conn.setReadTimeout(50000);
			
			
			OutputStream os = conn.getOutputStream();								// Request Data에 jsonString을 담기 위해 객체 생성
			os.write(jsonVal.getBytes());											// jsonString 세팅
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			Map headers = conn.getHeaderFields();
			Set<String> keys = headers.keySet();
			for  (String key : keys) {
				val += key + " : " + conn.getHeaderField(key) + ", ";
			}
			
			conn.disconnect();
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		
		JSONObject jsonObject = new JSONObject(String.valueOf(outResult.toString()));

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", nno);
		params.put("connUrl", "/api/order/detail");
		params.put("headContents", val);
		params.put("bodyContents", jsonVal);
		params.put("rstContents", jsonObject.toString());
		params.put("wUserId", member.getUsername());
		params.put("wUserIp", member.getIp());
		mapper.insertApiConn(params);

		return outResult.toString();
	}

	public String selectAgencyBl(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectAgencyBl(parameters);
	}

	public void fnUpdateDeliveryInfo(String hawbNo, String nno, String transCode) {
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("hawbNo", hawbNo);
		parameters.put("nno", nno);
		parameters.put("matchNo", hawbNo);
		String rtnVal = "";
		if (transCode.equals("FB")) {
			parameters.put("transCode", "FB");
		} else {
			parameters.put("transCode", "FB-EMS");
		}
		
		try {
			rtnVal = fnMakeDeliveryInfo(hawbNo);
			JSONObject json = new JSONObject(String.valueOf(rtnVal));
			String delvNo = "";
			JSONObject meta = json.getJSONObject("meta");
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = new JSONObject(String.valueOf(json.get("response").toString()));
				JSONObject order = new JSONObject(String.valueOf(response.get("order").toString()));
				delvNo = order.get("Domestic_Invoice_No").toString();

				if (!delvNo.equals("")) {
					parameters.put("delvNo", delvNo);
					comnMapper.insertDeliveryInfo(parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String fnMakeDeliveryInfo(String hawbNo) {
		LinkedHashMap<String, Object> json = new LinkedHashMap<String, Object>();
		String mallId = "aciexpress2";
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		json.put("mall_id", mallId);
		json.put("fb_invoice_no", hawbNo);
		Gson gson = new Gson();
		String jsonVal = gson.toJson(json);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/Tracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();		// URL 연결
			conn.setDoOutput(true);													// OutputStream으로 post 데이터를 넘김
			conn.setRequestMethod("POST");											// 요청 방식 POST
			conn.setRequestProperty("consumerKey", consumerKey);					// 요청 헤더 세팅
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/json");			// json 타입으로 전송
			conn.setRequestProperty("Accept", "application/json");					// 응답 데이터를 json 타입으로 요청
			conn.setRequestProperty("Accept-Language", "ko-KR");					// ko-KR 언어로 지원
			conn.setConnectTimeout(50000);											// timeout
			conn.setReadTimeout(50000);
			
			
			OutputStream os = conn.getOutputStream();								// Request Data에 jsonString을 담기 위해 객체 생성
			os.write(jsonVal.getBytes());											// jsonString 세팅
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outResult.toString();
	}

	public void fnGetDeliveryInfo(String nno, String transCode) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("transCode", transCode);
		LinkedHashMap<String, Object> json = new LinkedHashMap<String, Object>();
		String mallId = "aciexpress2";
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		String hawbNo = mapper.selectHawbNoByNNO(nno);
		parameters.put("hawbNo", hawbNo);
		parameters.put("matchNo", hawbNo);
		json.put("mall_id", mallId);
		json.put("fb_invoice_no", hawbNo);
		Gson gson = new Gson();
		String jsonVal = gson.toJson(json);
		
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		
		try {
			URL url = new URL("https://dhub-api.cafe24.com/api/Tracking");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();		// URL 연결
			conn.setDoOutput(true);													// OutputStream으로 post 데이터를 넘김
			conn.setRequestMethod("POST");											// 요청 방식 POST
			conn.setRequestProperty("consumerKey", consumerKey);					// 요청 헤더 세팅
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/json");			// json 타입으로 전송
			conn.setRequestProperty("Accept", "application/json");					// 응답 데이터를 json 타입으로 요청
			conn.setRequestProperty("Accept-Language", "ko-KR");					// ko-KR 언어로 지원
			conn.setConnectTimeout(50000);											// timeout
			conn.setReadTimeout(50000);
			
			
			OutputStream os = conn.getOutputStream();								// Request Data에 jsonString을 담기 위해 객체 생성
			os.write(jsonVal.getBytes());											// jsonString 세팅
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
			
			JSONObject jsonObject = new JSONObject(String.valueOf(outResult.toString()));
			String delvNo = "";
			JSONObject meta = jsonObject.getJSONObject("meta");
			if (!meta.get("code").toString().equals("200")) {
				throw new Exception();
			} else {
				JSONObject response = new JSONObject(String.valueOf(jsonObject.get("response").toString()));
				JSONObject order = new JSONObject(String.valueOf(response.get("order").toString()));
				delvNo = order.get("Domestic_Invoice_No").toString();

				if (!delvNo.equals("")) {
					parameters.put("delvNo", delvNo);
					comnMapper.insertDeliveryInfo(parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getApiSendHeaders(HttpURLConnection connection) {
		
		String returnVal = "";
		Map headers = connection.getHeaderFields();
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			returnVal += key + " : " + connection.getHeaderField(key) + ", ";
		}
		
		return returnVal;
	}
	
}
