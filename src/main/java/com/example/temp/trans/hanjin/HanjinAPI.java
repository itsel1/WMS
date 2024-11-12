package com.example.temp.trans.hanjin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.google.gson.Gson;
import com.openhtmltopdf.css.parser.property.PrimitivePropertyBuilders.LineHeight;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

@Service
@Component
@Controller
public class HanjinAPI {
	
	@Value("${filePath}")
	String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;

	@Autowired
	HanjinMapper hjMapper;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ComnMapper comnMapper;
	
	private static final String UPPERCASE_ASCII =
		    "AEIOU" // grave
		    + "AEIOUY" // acute
		    + "AEIOUY" // circumflex
		    + "AON" // tilde
		    + "AEIOUY" // umlaut
		    + "A" // ring
		    + "C" // cedilla
		    + "OU" // double acute
		    ;

		private static final String UPPERCASE_UNICODE =
		    "\u00C0\u00C8\u00CC\u00D2\u00D9"
		    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
		    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
		    + "\u00C3\u00D5\u00D1"
		    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
		    + "\u00C5"
		    + "\u00C7"
		    + "\u0150\u0170"
		    ;

	
	public ProcedureVO selectBlApply(String nno, String userId, String userIp) {
		ProcedureVO rst = new ProcedureVO();
		LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("userId", userIp);
		parameters.put("userIp", userIp);
		String errorMsg = "";
		HashMap<String, Object> shipRst = new HashMap<String, Object>(); 
		
		try {
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			String url = "http://tracking.banyanlogistics.com:8080/HanjinAPI/PrintWBLsSC_withu.jsp";
			String requestData = makePrintWBLJson(nno);
			
			ApiAction action = ApiAction.getInstance();
			JSONObject respData = action.apiPost(requestData, url, headers, nno, userId, userIp);
			
			parameters.put("connUrl", "/HanjinAPI/PrintWBLsSC_withu.jsp");
			parameters.put("jsonHeader", "");
			parameters.put("jsonData", requestData);
			parameters.put("rtnContents", respData.toString());
			apiMapper.insertApiConn(parameters);
			
			if (respData != null) {
				JSONArray respArr = new JSONArray(String.valueOf(respData.get("address_list")));
				JSONObject respOne = (JSONObject) respArr.getJSONObject(0);
				int errCnt = respData.getInt("error_cnt");
				
				if (errCnt != 0) {
					errorMsg = respOne.get("result_message").toString();
					parameters.put("status", errorMsg);
					throw new Exception();
				} else {
					String hawbNo = respOne.get("wbl_num").toString();
					String wjYesno = respOne.get("wj_yesno").toString();
					if (wjYesno.equals("Y")) {
						parameters.put("orderLine", "99");
					} else {
						parameters.put("orderLine", "03");
					}
					parameters.put("hawbNo", hawbNo);
					// 배송정보 등록
					shipRst = shipmentHanjinApi(parameters);
					
					if (shipRst.get("STATUS").toString().equals("SUCCESS")) {
						
						insertRefnInfo(respOne, nno);
						
						/* 	운송장 생성 후 서버에 저장
						 *	2023.06.28 respOne 데이터 DB에 저장 후 운송장 출력시마다 송장 그리는 방식으로 변경 
						 * */
						//savePdf(respOne);
						
						parameters.put("hawbNo", hawbNo);
						comnService.createParcelBl(parameters);
						
						rst.setRstStatus("SUCCESS");
						rst.setRstHawbNo(hawbNo);
						rst.setOrgHawbNo(hawbNo);
						
					}
				}
			} else {
				parameters.put("status", "운송장 생성 실패");
				throw new Exception();
			}
		} catch (Exception e) {
			if (parameters.get("status") == null) {
				parameters.put("status", "System Error");
			}
			hjMapper.updateErrorStatus(parameters);
			rst.setRstStatus("FAIL");
			rst.setRstMsg(parameters.get("status").toString());
			rst.setRstHawbNo("");
		}
		
		return rst;
	}

	@Transactional
	private void insertRefnInfo(JSONObject respOne, String nno) {
		HanjinVO hjInfo = new HanjinVO();
		try {
			hjInfo.setNno(nno);
			hjInfo.setPdTim(respOne.get("pd_tim").toString());
			hjInfo.setRstMsg(respOne.get("result_message").toString());
			hjInfo.setPrtAdd(respOne.get("prt_add").toString());
			hjInfo.setZipCod(respOne.get("zip_cod").toString());
			hjInfo.setWjYesno(respOne.get("wj_yesno").toString());
			hjInfo.setWjBrcod(respOne.get("wj_brcod").toString());
			hjInfo.setHubCod(respOne.get("hub_cod").toString());
			hjInfo.setDomMid(respOne.get("dom_mid").toString());
			hjInfo.setEsNam(respOne.get("es_nam").toString());
			hjInfo.setGrpRnk(respOne.get("grp_rnk").toString());
			hjInfo.setCenCod(respOne.get("cen_cod").toString());
			hjInfo.setDomRgn(respOne.get("dom_rgn").toString());
			hjInfo.setRstCode(respOne.get("result_code").toString());
			hjInfo.setCenNam(respOne.get("cen_nam").toString());
			hjInfo.setWjTrcod(respOne.get("wj_trcod").toString());
			hjInfo.setSTmlNam(respOne.get("s_tml_nam").toString());
			hjInfo.setTmlCod(respOne.get("tml_cod").toString());
			hjInfo.setEsCod(respOne.get("es_cod").toString());
			hjInfo.setWjBrnme(respOne.get("wj_brnme").toString());
			hjInfo.setSTmlCod(respOne.get("s_tml_cod").toString());
			hjInfo.setTmlNam(respOne.get("tml_nam").toString());
			hjInfo.setWblNum(respOne.get("wbl_num").toString());
			
			hjMapper.insertRefnAddrInfo(hjInfo);
			hjMapper.updateCneeZipInfo(hjInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String makePrintWBLJson(String nno) {
		String clientId = "WOOJIN";
		String csrNum = "3450981";
		//String csrNum = "3007934";
		String sndZip = "22856";
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		LinkedHashMap<String, Object> jsonData = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		
		parameters.put("nno", nno);
		orderInfo = hjMapper.selectAddressInfo(parameters);
		orderInfo.dncryptData();
		String address = orderInfo.getCneeAddr();
		if (!orderInfo.getCneeAddrDetail().equals("")) {
			address += " " + orderInfo.getCneeAddrDetail();
		}
		
		jsonData.put("client_id", clientId);
		dataOne.put("msg_key", nno);
		dataOne.put("csr_num", csrNum);
		dataOne.put("address", address);
		dataOne.put("snd_zip", sndZip);
		dataList.add(dataOne);
		jsonData.put("address_list", dataList);
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(jsonData);
		
		return jsonVal;
	}
	
	private HashMap<String, Object> shipmentHanjinApi(HashMap<String, Object> parameters) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();
		String requestVal = makeShipmentJson(parameters);
		String nno = parameters.get("nno").toString();
		String userId = parameters.get("userId").toString();
		String userIp = parameters.get("userIp").toString();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		String cuscde = "SK00012";
		//String cuscde = "SK00003";
		String url = "http://api.withusoft.com/api/order.asp?cuscde="+cuscde;
		
		ApiAction action = ApiAction.getInstance();
		JSONObject respData = action.apiPost(requestVal, url, headers, nno, userId, userIp);
		
		parameters.put("connUrl", "/api/order.asp");
		parameters.put("jsonHeader", "");
		parameters.put("jsonData", requestVal);
		parameters.put("rtnContents", respData.toString());
		apiMapper.insertApiConn(parameters);
		
		if (respData != null) {
			if (!respData.get("success").toString().toLowerCase().equals("true")) {
				rst.put("STATUS", "FAIL");
			} else {
				rst.put("STATUS", "SUCCESS");
			}
		} else {
			rst.put("STATUS", "FAIL");
		}
		
		return rst;
	}
	
	public String makeShipmentJson(HashMap<String, Object> parameters) {
		LinkedHashMap<String, Object> jsonData = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		String cuscde = "SK00012";
		//String cuscde = "SK00003";
		UserOrderListVO orderInfo = new UserOrderListVO();
		orderInfo = hjMapper.selectHjOrderInfo(parameters);
		orderInfo.dncryptData();
		
		dataOne.put("invoice_no", parameters.get("hawbNo").toString());
		dataOne.put("cuscde", cuscde);
		dataOne.put("order_gubun", "01");
		dataOne.put("order_no", orderInfo.getOrderNo());
		dataOne.put("order_line", parameters.get("orderLine").toString());
		dataOne.put("shpr_nm", orderInfo.getShipperName());
		dataOne.put("shpr_zip", orderInfo.getShipperZip());
		dataOne.put("shpr_addr1", orderInfo.getShipperAddr());
		dataOne.put("shpr_addr2", orderInfo.getShipperAddrDetail());
		dataOne.put("shpr_addr3", "");
		dataOne.put("shpr_tel_no", orderInfo.getShipperTel());
		dataOne.put("shpr_cell_no", "");
		dataOne.put("cnee_nm", orderInfo.getCneeName());
		dataOne.put("cnee_zip", orderInfo.getCneeZip());
		dataOne.put("cnee_addr1", orderInfo.getCneeAddr());
		dataOne.put("cnee_addr2", orderInfo.getCneeAddrDetail());
		dataOne.put("cnee_email", "");
		dataOne.put("cnee_tel_no", orderInfo.getCneeTel());
		dataOne.put("cnee_cell_no", orderInfo.getCneeHp());
		dataOne.put("remark", orderInfo.getDlvReqMsg());
		dataOne.put("mall_item_nm", orderInfo.getItemDetail());
		dataOne.put("mall_item_cnt", orderInfo.getItemCnt());
		dataOne.put("mall_item_amt", "");
		dataOne.put("mall_item_weight", "");
		
		jsonData.put("order_info", dataOne);
		
		Gson gson = new Gson();
		String jsonVal = gson.toJson(jsonData);
		
		return jsonVal;
	}

	private void savePdf(JSONObject respOne) {
		String nno = respOne.get("msg_key").toString();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		String hawbNo = respOne.get("wbl_num").toString();
		
		String pdTim = respOne.get("pd_tim").toString();
		String wjYesno = respOne.get("wj_yesno").toString();
		String wjBrcod = respOne.get("wj_brcod").toString();
		String hubCod = respOne.get("hub_cod").toString();
		String domMid = respOne.get("dom_mid").toString();
		String esNam = respOne.get("es_nam").toString();
		String grpRnk = respOne.get("grp_rnk").toString();
		String cenCod = respOne.get("cen_cod").toString();
		String domRgn = respOne.get("dom_rgn").toString();
		String cenNam = respOne.get("cen_nam").toString();
		String wjTrcod = respOne.get("wj_trcod").toString();
		String sTmlNam = respOne.get("s_tml_nam").toString();
		String tmlCod = respOne.get("tml_cod").toString();
		String esCod = respOne.get("es_cod").toString();
		String wjBrnme = respOne.get("wj_brnme").toString();
		String sTmlCod = respOne.get("s_tml_cod").toString();
		String tmlNam = respOne.get("tml_nam").toString();
		
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
		
		try {
			float perIn = 72;
			
			PDDocument document = new PDDocument();
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String mainPath = cssResource.getURL().getPath();
			String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
			InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
			InputStream koreanB = new FileInputStream(subPath + "/static/fonts/NanumBarunGothicBold.ttf");

			PDType0Font NanumGothic = PDType0Font.load(document, korean);
			PDType0Font NanumGothicB = PDType0Font.load(document, koreanB);

			int totalPage = 0;
			
			UserOrderListVO orderInfo = new UserOrderListVO();
			orderInfo = hjMapper.selectPrintOrderInfo(parameters);
			orderInfo.dncryptData();
			
			float perMM = 1 / (10 * 2.54f) * 72;
			//PDRectangle pageStandard = new PDRectangle(6f * perIn, 4f * perIn);
			PDRectangle pageStandard = new PDRectangle(123*perMM, 100*perMM);
			PDPage blankPage = new PDPage(pageStandard);
			document.addPage(blankPage);
			PDPage page = document.getPage(totalPage);
			PDPageContentStream cs = new PDPageContentStream(document, page);
			
			String hawbNoFmt = hawbNo.substring(0,4)+"-"+hawbNo.substring(4,8)+"-"+hawbNo.substring(8,12);
			String cneeTel = orderInfo.getCneeTel();
			String cneeHp = orderInfo.getCneeHp();
			cneeTel = cneeTel.replaceAll("[^0-9]", "");
			cneeHp = cneeHp.replaceAll("[^0-9]", "");
			String cneeTel1 = "";
			String cneeTel2 = "";
			String cneeHp1 = "";
			String cneeHp2 = "";
			String cneeTelFmt = "";
			String cneeAddr = orderInfo.getCneeAddr();
			if (!orderInfo.getCneeAddrDetail().equals("")) {
				cneeAddr += " " + orderInfo.getCneeAddrDetail();
			}
			String shipperAddr = orderInfo.getShipperAddr();
			if (!orderInfo.getShipperAddrDetail().equals("")) {
				shipperAddr += " " + orderInfo.getShipperAddrDetail();
			}
			
			if (!cneeTel.equals("")) {
				if (cneeTel.length() > 9) {
					String[] tellArray;
					try {
						tellArray = phoneNumberSplit(cneeTel);
						cneeTel1 = tellArray[0];
						cneeTel2 = tellArray[1];
						cneeTelFmt += cneeTel1 + "-" + cneeTel2 + "-**** / ";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			if (!cneeHp.equals("")) {
				if (cneeHp.length() > 10) {
					String[] phoneArray;
					try {
						phoneArray = phoneNumberSplit(cneeHp);
						cneeHp1 = phoneArray[0];
						cneeHp2 = phoneArray[1];
						cneeTelFmt += cneeHp1 + "-" + cneeHp2 + "-****";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
			float yStart = pageStandard.getHeight();
			float xEnd = pageStandard.getWidth();
			float yHalf = yStart / 2;
			
			// HawbNo
			cs.beginText();
			cs.newLineAtOffset(10, yStart - 20);
			cs.setFont(NanumGothic, 12);
			cs.showText(hawbNoFmt);
			cs.endText();
			
			// hub_cod
			cs.beginText();
			cs.newLineAtOffset(10, yStart - 50);
			cs.setFont(NanumGothicB, 26);
			cs.showText(hubCod);
			cs.endText();
			
			// tml_cod
			cs.beginText();
			cs.newLineAtOffset(30, yStart - 50);
			cs.setFont(NanumGothicB, 26);
			cs.showText(tmlCod);
			cs.endText();
			
			// dom_mid
			cs.beginText();
			cs.newLineAtOffset(85, yStart - 50);
			cs.setFont(NanumGothicB, 26);
			cs.showText(domMid);
			cs.endText();
			
			// es_cod
			cs.beginText();
			cs.newLineAtOffset(130, yStart - 37);
			cs.setFont(NanumGothicB, 18);
			cs.showText(esCod);
			cs.endText();
			
			// es_name
			cs.beginText();
			cs.newLineAtOffset(130, yStart - 58);
			cs.setFont(NanumGothicB, 16);
			cs.showText(esNam);
			cs.endText();
			
			// s_tml_cod
			cs.beginText();
			cs.newLineAtOffset(15, yStart - 62);
			cs.setFont(NanumGothic, 10);
			cs.showText(sTmlCod);
			cs.endText();
			
			// s_tml_nam
			cs.beginText();
			cs.newLineAtOffset(43, yStart - 62);
			cs.setFont(NanumGothic, 10);
			cs.showText(sTmlNam);
			cs.endText();

			// grp_rnk
			cs.beginText();
			cs.newLineAtOffset(215, yStart - 47);
			cs.setFont(NanumGothicB, 28);
			cs.showText(grpRnk);
			cs.endText();
			
			// cen_cod
			cs.beginText();
			cs.newLineAtOffset(210, yStart - 62);
			cs.setFont(NanumGothic, 10);
			cs.showText(cenCod);
			cs.endText();
			
			// cen_nam
			cs.beginText();
			cs.newLineAtOffset(235, yStart - 62);
			cs.setFont(NanumGothic, 10);
			cs.showText(cenNam);
			cs.endText();
			
			// dom_rgn
			if (domRgn.equals("7") || domRgn.equals("9")) {
				cs.beginText();
				cs.newLineAtOffset(xEnd - 58, yStart - 50);
				cs.setFont(NanumGothicB, 13);
				if (domRgn.equals("7")) {
					cs.showText("제주");
				} else if (domRgn.equals("9")) {
					cs.showText("도서");
				}
				cs.endText();
			}
			
			cs.setLineWidth(1);
			cs.moveTo(10, yStart - 68);
			cs.lineTo(xEnd - 10, yStart - 68);
			cs.stroke();
			
			cs.setLineWidth(1);
			cs.moveTo(10, yHalf);
			cs.lineTo(xEnd - 10, yHalf);
			cs.stroke();
			
			float yStart2 = yStart - 68;
			
			cs.beginText();
			cs.newLineAtOffset(12, yStart2 - 13);
			cs.setFont(NanumGothic, 9);
			cs.showText(orderInfo.getCneeName());
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(12, yStart2 - 25);
			cs.setFont(NanumGothic, 9);
			cs.showText(cneeAddr);
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(100, yStart2 - 13);
			cs.setFont(NanumGothic, 9);
			cs.showText(cneeTelFmt);
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(12, yHalf + 20);
			cs.setFont(NanumGothic, 9);
			cs.showText(orderInfo.getShipperName());
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(100, yHalf + 20);
			cs.setFont(NanumGothic, 9);
			cs.showText(orderInfo.getShipperTel());
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(12, yHalf + 7);
			cs.setFont(NanumGothic, 9);
			cs.showText(shipperAddr);
			cs.endText();
			
			// 도착지 터미널 바코드
			Barcode barcodes1 = BarcodeFactory.createCode128(tmlCod);
			barcodes1.setLabel("Barco");
			barcodes1.setDrawingText(true);

			String barcodePath1 = pdfPath2 +hawbNo+"_tmlCod.JPEG";
			File barcodefile1 = new File(barcodePath1);
			
			try {
				BarcodeImageHandler.saveJPEG(barcodes1, barcodefile1);
			} catch (OutputException e) {
				e.printStackTrace();
			}
			
			PDImageXObject tmlCodBarCode = PDImageXObject.createFromFileByContent(barcodefile1, document);
			
			int tmlCodeWidth = tmlCodBarCode.getWidth();
			int tmlCodeHeight = tmlCodBarCode.getHeight();
			int targetWidth1 = 100;
			int targetHeight1 = (int) ((double) targetWidth1 * tmlCodeHeight / tmlCodeWidth);
			
			cs.drawImage(tmlCodBarCode, xEnd - 105, yStart2 - 32, targetWidth1, targetHeight1);
			
			cs.beginText();
			cs.newLineAtOffset(xEnd - 50, yHalf + 25);
			cs.setFont(NanumGothic, 9);
			cs.showText("신용");
			cs.endText();
			
			String trCod = "";
			String barcodePath3 = pdfPath2 +hawbNo+"_trCod.JPEG";
			
			if (wjYesno.equals("Y")) {
				trCod = wjTrcod;
				// 직배송 터미널 바코드
				Barcode barcodes3 = BarcodeFactory.createCode39(trCod, false);
				
				barcodes3.setLabel("Barco");
				barcodes3.setDrawingText(true);

				File barcodefile3 = new File(barcodePath3);
				
				try {
					BarcodeImageHandler.saveJPEG(barcodes3, barcodefile3);
				} catch (OutputException e) {
					e.printStackTrace();
				}
				
				PDImageXObject trCodBarCode = PDImageXObject.createFromFileByContent(barcodefile3, document);
				cs.drawImage(trCodBarCode, 12, yHalf - 30, 100, 20);

				cs.beginText();
				cs.newLineAtOffset(80, yHalf - 44);
				cs.setFont(NanumGothic, 10);
				cs.showText(wjBrcod);
				cs.endText();
				
				cs.beginText();
				cs.newLineAtOffset(80, yHalf - 58);
				cs.setFont(NanumGothic, 10);
				cs.showText(wjBrnme);
				cs.endText();
			} else {
				trCod = "H";
			}

			cs.beginText();
			cs.newLineAtOffset(12, yHalf - 58);
			cs.setFont(NanumGothicB, 26);
			cs.showText(trCod);
			cs.endText();
			
			cs.beginText();
			cs.newLineAtOffset(18, 13);
			cs.setFont(NanumGothic, 10);
			cs.showText(orderInfo.getDlvReqMsg());
			cs.endText();
			
			// 운송장 바코드
			Barcode barcodes2 = BarcodeFactory.createInt2of5(hawbNo);
			
			barcodes2.setLabel("Barco");
			barcodes2.setDrawingText(true);

			String barcodePath2 = pdfPath2 +hawbNo+"_blNo.JPEG";
			File barcodefile2 = new File(barcodePath2);
			
			try {
				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);
			} catch (OutputException e) {
				e.printStackTrace();
			}
			
			PDImageXObject HawbBarCode = PDImageXObject.createFromFileByContent(barcodefile2, document);
			int barcodeWidth = HawbBarCode.getWidth();
			int barcodeHeight = HawbBarCode.getHeight();
			int targetWidth2 = 130;
			int targetHeight2 = (int) ((double) targetWidth2 * barcodeHeight / barcodeWidth);
			
			cs.drawImage(HawbBarCode, xEnd - 150, 30, targetWidth2, targetHeight2);
			
			ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
			itemList = hjMapper.selectPrintItemInfo(parameters);

			float itemX = 130;
			float itemY = yHalf - 15;
			float lineHeight = 12;
			
			int maxItems = 4;
			int itemCount = itemList.size();
			for (int itemIdx = 0; itemIdx < itemList.size(); itemIdx++) {
				cs.beginText();
				cs.newLineAtOffset(itemX, itemY);
				cs.setFont(NanumGothic, 9);
				cs.showText(itemList.get(itemIdx).getItemDetail());
				cs.endText();
				/*
				if (itemIdx < maxItems) {
					cs.showText(itemList.get(itemIdx).getItemDetail());
					cs.endText();
				} else {
					cs.showText("외 " + (itemCount - maxItems) + "개");
					cs.endText();
					break;
				}
				*/
				itemY -= lineHeight;
			}

			cs.beginText();
			cs.newLineAtOffset(xEnd - 120, 18);
			cs.setFont(NanumGothicB, 10);
			cs.showText(hawbNoFmt);
			cs.endText();
			/*
			cs.beginText();
			cs.newLineAtOffset(260, 10);
			cs.setFont(NanumGothic, 10);
			cs.showText("운임type:D");
			cs.endText();
			*/
			cs.close();

			
			String fileName = realFilePath + "image/aramex/" + hawbNo + ".pdf";
			document.save(fileName);
			document.close();
			
			String userId = orderInfo.getUserId();
			
			//File base64ToImg = new File(fileName);
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult objectResult = new PutObjectResult();
			File file = new File(fileName);
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
			String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
			if (amazonS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(
						comnS3Info.getBucketName() + "/outbound/hawb/" + year + "/" + week, userId + "_" + hawbNo,file);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				objectResult = amazonS3.putObject(putObjectRequest);
			}
			amazonS3 = null;
			file.delete();
			
			String[] barcodePaths = {barcodePath1, barcodePath2, barcodePath3};
			for (String path : barcodePaths) {
				File barcodeFile = new File(path);
				if (barcodeFile.exists()) {
					barcodeFile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String[] phoneNumberSplit(String phoneNumber) throws Exception{
        Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
        Matcher matcher = tellPattern.matcher(phoneNumber);
        if(matcher.matches()) {
            //정규식에 적합하면 matcher.group으로 리턴
            return new String[]{ matcher.group(1), matcher.group(2), matcher.group(3)};
        }else{
            //정규식에 적합하지 않으면 substring으로 휴대폰 번호 나누기
            String str1 = phoneNumber.substring(0, 3);
            String str2 = phoneNumber.substring(3, 7);
            String str3 = phoneNumber.substring(7, 11);
            return new String[]{str1, str2, str3};
        }
    }

	public ArrayList<HashMap<String, Object>> makeHJPod(String hawbNo, HttpServletRequest request, String podType, String apiUserId) throws Exception {
		ArrayList<HashMap<String, Object>> podDetailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		String nno = hjMapper.selectNnoByHawbNo(hawbNo);
		//hawbNo = "509000340041";
		String url = "http://api.withusoft.com/api/tracking.asp?invoice_no="+hawbNo;
		ApiAction action = ApiAction.getInstance();
		JSONObject jsonVal = new JSONObject();
		if (podType.equals("A")) {
			jsonVal = action.apiGet(hawbNo, url, nno, apiUserId, request.getRemoteAddr());
		} else {
			jsonVal = action.apiGet(hawbNo, url, nno, request.getSession().getAttribute("USER_ID").toString(), request.getRemoteAddr());	
		}
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("connUrl", "/api/tracking.asp");
		parameters.put("jsonHeader", "");
		parameters.put("jsonData", "");
		parameters.put("rtnContents", jsonVal.toString());
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		//apiMapper.insertApiConn(parameters);
		
		try {
			JSONArray jsonArr = new JSONArray(String.valueOf(jsonVal.get("tracking_info").toString()));
			
			String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
			String mawbInDate = apiMapper.selectMawbInDate(hawbNo);		// 출고
			String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
			
			for (int i = jsonArr.length()-1; i > -1; i--) {
				podDetatil  = new LinkedHashMap<String,Object>();
				JSONObject jsonObj = (JSONObject) jsonArr.get(i);

				if (!jsonObj.get("jngnum").toString().equals("")) {
					String jngdat = jsonObj.get("jngdat").toString();
					String date = jngdat.substring(0, 4)+"-"+jngdat.substring(4, 6)+"-"+jngdat.substring(6);
					String jngtim = jsonObj.get("jngtim").toString();
					String time = jngtim.substring(0, 2)+":"+jngtim.substring(2);
					String datetime = date + " " + time;
					
					// 집하
					if (jsonObj.get("jngnum").toString().equals("11")) {
						podDetatil.put("UpdateCode", "310"); 
						podDetatil.put("UpdateDateTime", datetime);
						podDetatil.put("UpdateLocation", "Korea, "+jsonObj.get("postnm").toString());
						if (podType.equals("V")) {
							podDetatil.put("UpdateDescription", "집하");	
						} else {
							podDetatil.put("UpdateDescription", "Pickup the parcels");	
						}
					// 간선하차	
					} else if (jsonObj.get("jngnum").toString().equals("17")) {
						podDetatil.put("UpdateCode", "400"); 
						podDetatil.put("UpdateDateTime", datetime);
						podDetatil.put("UpdateLocation", "Korea, "+jsonObj.get("postnm").toString());
						if (podType.equals("V")) {
							podDetatil.put("UpdateDescription", jsonObj.get("jngcde").toString());	
						} else {
							podDetatil.put("UpdateDescription", "Arrival in destination country");	
						}
					// 배달준비
					} else if (jsonObj.get("jngnum").toString().equals("18")) {
						podDetatil.put("UpdateCode", "500"); 
						podDetatil.put("UpdateDateTime", datetime);
						podDetatil.put("UpdateLocation", "Korea, "+jsonObj.get("postnm").toString());
						if (podType.equals("V")) {
							podDetatil.put("UpdateDescription", jsonObj.get("jngcde").toString());	
						} else {
							podDetatil.put("UpdateDescription", "Out for Delivery");	
						}
					// 배송완료
					} else if (jsonObj.get("jngnum").toString().equals("19")) {
						podDetatil.put("UpdateCode", "600"); 
						podDetatil.put("UpdateDateTime", datetime);
						podDetatil.put("UpdateLocation", "Korea, "+jsonObj.get("postnm").toString());
						if (podType.equals("V")) {
							podDetatil.put("UpdateDescription", "배달완료");	
						} else {
							podDetatil.put("UpdateDescription", "Delivered");	
						}
					} else {
						continue;
					}
					podDetailArray.add(podDetatil);
				}
			}

			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "300"); 
			podDetatil.put("UpdateDateTime", mawbInDate);
			podDetatil.put("UpdateLocation", "Korea, (주) 에이씨아이 월드와이드");
			if (podType.equals("V")) {
				podDetatil.put("UpdateDescription", "출고 완료");	
			} else {
				podDetatil.put("UpdateDescription", "Shipped out");	
			}
			podDetailArray.add(podDetatil);
			
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "200"); 
			podDetatil.put("UpdateDateTime", hawbInDate);
			podDetatil.put("UpdateLocation", "Korea, (주) 에이씨아이 월드와이드");
			if (podType.equals("V")) {
				podDetatil.put("UpdateDescription", "입고 완료");	
			} else {
				podDetatil.put("UpdateDescription", "Finished warehousing");	
			}
			podDetailArray.add(podDetatil);
			
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "100"); 
			podDetatil.put("UpdateDateTime", regInDate);
			podDetatil.put("UpdateLocation", "Korea");
			if (podType.equals("V")) {
				podDetatil.put("UpdateDescription", "입고 대기 (주문 접수)");	
			} else {
				podDetatil.put("UpdateDescription", "Order information has been entered");	
			}
			podDetailArray.add(podDetatil);
			
		} catch (Exception e) {
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "-200"); 
			podDetatil.put("UpdateDateTime", "");
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetailArray.add(podDetatil);
		}
		
		return podDetailArray;
	}

	public void hjPrint(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String orderType) throws Exception {
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
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
		
		try {
			
			PDDocument document = new PDDocument();
			response.setContentType("application/pdf");
			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String mainPath = cssResource.getURL().getPath();
			String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
			InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
			InputStream koreanB = new FileInputStream(subPath + "/static/fonts/NanumBarunGothicBold.ttf");

			PDType0Font NanumGothic = PDType0Font.load(document, korean);
			PDType0Font NanumGothicB = PDType0Font.load(document, koreanB);
			
			float perMM = 1 / (10 * 2.54f) * 72;
			
			int totalPage = 0;
			
			for (int i = 0; i < orderNnoList.size(); i++) {
				parameterInfo = new HashMap<String, Object>();
				String nno = orderNnoList.get(i);
				parameterInfo.put("nno", nno);
				
				UserOrderListVO orderInfo = new UserOrderListVO();
				HanjinVO printInfo = new HanjinVO();
				
				orderInfo = hjMapper.selectPrintOrderInfo(parameterInfo);
				orderInfo.dncryptData();
				printInfo = hjMapper.selectPrintLabelInfo(parameterInfo);
				
				PDRectangle pageStandard = new PDRectangle(123*perMM, 100*perMM);
				PDPage blankPage = new PDPage(pageStandard);
				document.addPage(blankPage);
				PDPage page = document.getPage(totalPage);
				PDPageContentStream cs = new PDPageContentStream(document, page);
				
				//String hawbNo = printInfo.getWblNum();
				String hawbNo = orderInfo.getHawbNo();
				String hawbNoFmt = hawbNo.substring(0,4) + "-" + hawbNo.substring(4,8) + "-" + hawbNo.substring(8,12);
				String cneeTel = orderInfo.getCneeTel();
				String cneeHp = orderInfo.getCneeHp();
				cneeTel = cneeTel.replaceAll("[^0-9]", "");
				cneeHp = cneeHp.replaceAll("[^0-9]", "");
				String cneeTel1 = "";
				String cneeTel2 = "";
				String cneeHp1 = "";
				String cneeHp2 = "";
				String cneeTelFmt = "";
				String cneeAddr = orderInfo.getCneeAddr();
				if (!orderInfo.getCneeAddrDetail().equals("")) {
					cneeAddr += " " + orderInfo.getCneeAddrDetail();
				}
				String shipperAddr = orderInfo.getShipperAddr();
				if (!orderInfo.getShipperAddrDetail().equals("")) {
					shipperAddr += " " + orderInfo.getShipperAddrDetail();
				}
				
				
				if (!cneeTel.equals("")) {
					if (cneeTel.length() > 9) {
						String[] tellArray;
						try {
							tellArray = phoneNumberSplit(cneeTel);
							cneeTel1 = tellArray[0];
							cneeTel2 = tellArray[1];
							cneeTelFmt += cneeTel1 + "-" + cneeTel2 + "-**** / ";
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				if (!cneeHp.equals("")) {
					if (cneeHp.length() > 10) {
						String[] phoneArray;
						try {
							phoneArray = phoneNumberSplit(cneeHp);
							cneeHp1 = phoneArray[0];
							cneeHp2 = phoneArray[1];
							cneeTelFmt += cneeHp1 + "-" + cneeHp2 + "-****";
						} catch (Exception e) {
							e.printStackTrace();
						}
					}	
				}
				
				float yStart = pageStandard.getHeight();
				float xEnd = pageStandard.getWidth();
				float yHalf = yStart / 2;
				
				// HawbNo
				cs.beginText();
				cs.newLineAtOffset(48, yStart - 23);
				cs.setFont(NanumGothic, 12);
				cs.showText(hawbNoFmt);
				cs.endText();
				
				// hub_cod
				cs.beginText();
				cs.newLineAtOffset(10, yStart - 52);
				cs.setFont(NanumGothicB, 26);
				cs.showText(printInfo.getHubCod());
				cs.endText();
				
				// tml_cod
				cs.beginText();
				cs.newLineAtOffset(48, yStart - 52);
				cs.setFont(NanumGothicB, 26);
				cs.showText(printInfo.getTmlCod());
				cs.endText();
				
				// dom_mid
				cs.beginText();
				cs.newLineAtOffset(105, yStart - 52);
				cs.setFont(NanumGothicB, 26);
				cs.showText(printInfo.getDomMid());
				cs.endText();
				
				// es_cod
				cs.beginText();
				cs.newLineAtOffset(140, yStart - 42);
				cs.setFont(NanumGothicB, 18);
				cs.showText(printInfo.getEsCod());
				cs.endText();
				
				// es_name
				cs.beginText();
				cs.newLineAtOffset(140, yStart - 60);
				cs.setFont(NanumGothicB, 16);
				cs.showText(printInfo.getEsNam());
				cs.endText();
				
				// s_tml_cod
				cs.beginText();
				cs.newLineAtOffset(15, yStart - 67);
				cs.setFont(NanumGothic, 10);
				cs.showText(printInfo.getSTmlCod());
				cs.endText();
				
				// s_tml_nam
				cs.beginText();
				cs.newLineAtOffset(55, yStart - 67);
				cs.setFont(NanumGothic, 10);
				cs.showText(printInfo.getSTmlNam());
				cs.endText();

				// grp_rnk
				cs.beginText();
				cs.newLineAtOffset(220, yStart - 52);
				cs.setFont(NanumGothicB, 28);
				cs.showText(printInfo.getGrpRnk());
				cs.endText();
				
				// cen_cod
				cs.beginText();
				cs.newLineAtOffset(220, yStart - 65);
				cs.setFont(NanumGothic, 10);
				cs.showText(printInfo.getCenCod());
				cs.endText();
				
				// cen_nam
				cs.beginText();
				cs.newLineAtOffset(245, yStart - 65);
				cs.setFont(NanumGothic, 10);
				cs.showText(printInfo.getCenNam());
				cs.endText();
				
				// dom_rgn
				if (printInfo.getDomRgn().equals("7") || printInfo.getDomRgn().equals("9")) {
					cs.beginText();
					cs.newLineAtOffset(xEnd - 50, yStart - 50);
					cs.setFont(NanumGothicB, 13);
					if (printInfo.getDomRgn().equals("7")) {
						cs.showText("제주");
					} else if (printInfo.getDomRgn().equals("9")) {
						cs.showText("도서");
					}
					cs.endText();
				}
				
				float yStart2 = yStart - 68;
				
				String cneeInfo  = orderInfo.getCneeName() + "\t\t" + cneeTelFmt;
				
				cs.beginText();
				cs.newLineAtOffset(18, yStart2 - 22);
				cs.setFont(NanumGothic, 9);
				cs.showText(cneeInfo);
				cs.endText();
				
				float startLine1 = yStart2 - 34;
				int limit1 = 0;
				int addrLineHeight = 12;
				
				if (cneeAddr.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
					limit1 = 34;
				} else {
					limit1 = 34;
				}
				
				int arraySize1 = (int) Math.ceil((double) cneeAddr.length() / limit1);
				String[] stringArr1 = new String[arraySize1];
				
				
				
				
				
				int index1 = 0;
				for (int start = 0; start < cneeAddr.length(); start += limit1) {
					stringArr1[index1++] = cneeAddr.substring(start, Math.min(start+limit1, cneeAddr.length()));
				}
				
				for (int j = 0; j < stringArr1.length; j++) {
					cs.beginText();
					cs.newLineAtOffset(18, startLine1);
					cs.setFont(NanumGothic, 9);
					cs.showText(stringArr1[j]);
					cs.endText();
					
					startLine1 -= addrLineHeight;
				}
				
				String shipperInfo = orderInfo.getShipperName() + "\t" + orderInfo.getShipperTel();
				cs.beginText();
				cs.newLineAtOffset(18, yHalf + 2);
				cs.setFont(NanumGothic, 9);
				cs.showText(shipperInfo);
				cs.endText();
				
				cs.beginText();
				cs.newLineAtOffset(18, yHalf - 10);
				cs.setFont(NanumGothic, 9);
				cs.showText(shipperAddr);
				cs.endText();
				
				Barcode tmlCodBarcode = BarcodeFactory.createCode128(printInfo.getTmlCod());
				tmlCodBarcode.setBarHeight(50);
				
				String barcodePath1 = pdfPath2+hawbNo+"_tmlCod.JPEG";
				File barcodeFile1 = new File(barcodePath1);
				
				try {
					BarcodeImageHandler.saveJPEG(tmlCodBarcode, barcodeFile1);
				} catch (OutputException e) {
					e.printStackTrace();
				}
				
				PDImageXObject tmlCodBarcodeImg = PDImageXObject.createFromFileByContent(barcodeFile1, document);
				int tmlCodeWidth = tmlCodBarcodeImg.getWidth();
				int tmlCodeHeight = tmlCodBarcodeImg.getHeight();
				int targetTmlCodWidth = 100;
				int targetTmlCodHeight = (int) ((double) targetTmlCodWidth * tmlCodeHeight / tmlCodeWidth);
				cs.drawImage(tmlCodBarcodeImg, xEnd - 105, yStart2 - 42, targetTmlCodWidth, targetTmlCodHeight);
				
				cs.beginText();
				//cs.newLineAtOffset(xEnd - 50, yHalf + 25);
				cs.newLineAtOffset(xEnd - 50, yHalf + 20);
				cs.setFont(NanumGothic, 9);
				cs.showText("신용");
				cs.endText();
				
				String trCod = "";
				
				if (printInfo.getWjYesno().equals("Y")) {
					trCod = printInfo.getWjTrcod();
					
					cs.beginText();
					cs.newLineAtOffset(82, 36);
					cs.setFont(NanumGothic, 10);
					cs.showText(printInfo.getWjBrcod());
					cs.endText();
					
					cs.beginText();
					cs.newLineAtOffset(82, 22);
					cs.setFont(NanumGothic, 10);
					cs.showText(printInfo.getWjBrnme());
					cs.endText();
				} else {
					trCod = "H";
				}
				
				cs.beginText();
				cs.newLineAtOffset(15, 22);
				cs.setFont(NanumGothicB, 26);
				cs.showText(trCod);
				cs.endText();
				
				cs.beginText();
				cs.newLineAtOffset(18, 8);
				cs.setFont(NanumGothic, 9);
				cs.showText(orderInfo.getDlvReqMsg());
				cs.endText();
				
				Barcode hawbBarcode = BarcodeFactory.createInt2of5(hawbNo);
				hawbBarcode.setBarHeight(70);
				String barcodePath2 = pdfPath2+hawbNo+"_blNo.JPEG";
				File barcodeFile2 = new File(barcodePath2);
				
				try {
					BarcodeImageHandler.saveJPEG(hawbBarcode, barcodeFile2);
				} catch (OutputException e) {
					e.printStackTrace();
				}
				
				PDImageXObject hawbBarcodeImg = PDImageXObject.createFromFileByContent(barcodeFile2, document);
				int hawbBarWidth = hawbBarcodeImg.getWidth();
				int hawbBarHeight = hawbBarcodeImg.getHeight();
				int targetBarWidth = 120;
				int targetBarHeight = (int) ((double) targetBarWidth * hawbBarHeight / hawbBarWidth);

				cs.drawImage(hawbBarcodeImg, xEnd - 140, 32, targetBarWidth, targetBarHeight);
				
				ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
				itemList = hjMapper.selectPrintItemInfo(parameterInfo);
				
				float itemX = 10;
				float itemY = yHalf - 30;
				float lineHeight = 12;

				int maxItems = 4;
				int itemCount = itemList.size();
				for (int itemIdx = 0; itemIdx < itemList.size(); itemIdx++) {
					cs.beginText();
					cs.newLineAtOffset(itemX, itemY);
					cs.setFont(NanumGothic, 9);
					String text = itemList.get(itemIdx).getItemDetail();
					StringBuilder builder = new StringBuilder();
					int n = text.length();
					
					for (int j = 0; j < n; j++) {
						char c = text.charAt(j);
						int pos = UPPERCASE_UNICODE.indexOf(c);
						if (pos > -1) {
							builder.append(UPPERCASE_ASCII.charAt(pos));
						} else {
							builder.append(c);
						}
					}
					
					if (itemIdx < maxItems) {
						cs.showText(builder.toString());
						cs.endText();
					} else {
						cs.showText("외 " + (itemCount - maxItems) + "개");
						cs.endText();
						break;
					}
					
					//cs.showText(itemList.get(itemIdx).getItemDetail());
					//cs.endText();
					
					itemY -= lineHeight;
				}
				
				cs.beginText();
				cs.newLineAtOffset(xEnd - 120, 20);
				cs.setFont(NanumGothicB, 10);
				cs.showText(hawbNoFmt);
				cs.endText();

				cs.close();
				
				if (barcodeFile1.exists()) {
					barcodeFile1.delete();
				}
				
				if (barcodeFile2.exists()) {
					barcodeFile2.delete();
				}
				
				totalPage++;
				
				if (orderType.toLowerCase().equals("y")) {
					packingInfo = comnMapper.selectPdfPrintItemInfo(nno);
					pageStandard = new PDRectangle(123*perMM, 100*perMM);
					System.out.println(pageStandard.getWidth() +" : "+ pageStandard.getHeight());
					blankPage = new PDPage(pageStandard);
					document.addPage(blankPage);
					page = document.getPage(totalPage);
					PDPageContentStream cts = new PDPageContentStream(document, page);
					
					cts.drawLine(10, 270, (pageStandard.getWidth()) - 10, 270);
					cts.drawLine(10, 220, (pageStandard.getWidth()) - 10, 220);
					cts.drawLine(10, 270, 10, 220);
					cts.drawLine((pageStandard.getWidth()) - 10, 270, (pageStandard.getWidth()) - 10, 220);
					cts.drawLine((pageStandard.getWidth()) - 70, 270, (pageStandard.getWidth()) - 70, 220);
					cts.drawLine((pageStandard.getWidth()) - 70, 250, (pageStandard.getWidth()) - 10, 250);
					
					cts.beginText();
					cts.setFont(NanumGothic, 20);
					cts.newLineAtOffset(14.5f, 245f);
					cts.showText(hawbNo);
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 14);
					cts.newLineAtOffset(15f, 227f);
					cts.showText(orderInfo.getOrderNo());
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 257);
					cts.showText("총 내품수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 12);
					cts.newLineAtOffset((pageStandard.getWidth()) - 45, 233.5f);
					cts.showText(String.valueOf(orderInfo.getTotalItemCnt()));
					cts.endText();
					
					cts.drawLine(10, 220, 10, 200);
					cts.drawLine((pageStandard.getWidth()) - 10, 220, (pageStandard.getWidth()) - 10, 200);
					cts.drawLine(10, 200, (pageStandard.getWidth()) - 10, 200);
					
					cts.drawLine((pageStandard.getWidth()) - 135, 220, (pageStandard.getWidth()) - 135, 200);
					cts.drawLine((pageStandard.getWidth()) - 90, 220, (pageStandard.getWidth()) - 90, 200);
					cts.drawLine((pageStandard.getWidth()) - 55, 220, (pageStandard.getWidth()) - 55, 200);
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset(15, 205.5f);
					cts.showText("내품명");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 130, 205.5f);
					cts.showText("사입코드");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 82, 205.5f);
					cts.showText("수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 45, 205.5f);
					cts.showText("Rack");
					cts.endText();

					int limit = 47;
					float startLine = 188;
					String itemName = "";
					
					for (int items = 0; items < packingInfo.size(); items++) {
						itemName = packingInfo.get(items).get("itemDetail").toString();
						
						if (itemName.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
							limit = 37;
						} 
						int arraySize = (int) Math.ceil((double) itemName.length() / limit);
						String[] stringArr = new String[arraySize];
						
						int index = 0;
						for (int start = 0; start < itemName.length(); start += limit) {
							stringArr[index++] = itemName.substring(start, Math.min(start+limit, itemName.length()));
						}
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 132, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("takeInCode").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 79, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("itemCnt").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 47, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("rackCode").toString());
						cts.endText();
						
						for (int x = 0; x < stringArr.length; x++) {
							cts.beginText();
							cts.setFont(NanumGothic, 8);
							cts.newLineAtOffset(12, startLine - 3.5f);
							cts.showText(stringArr[x]);
							cts.endText();
							
							if (x == stringArr.length-1)  {
								startLine -= 17;
							} else {
								startLine -= 15;
							}
						}
					}
					float endLine = startLine;
					
					cts.drawLine(10, 200, (pageStandard.getWidth()) - 10, 200);
					cts.drawLine(10, 205.5f, 10, endLine+6.5f);
					cts.drawLine((pageStandard.getWidth()) - 10, 205.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					cts.drawLine(10, endLine+6.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					cts.close();
					
					totalPage++;
					
				}
			}
			
			String fileName = URLEncoder.encode("LabelPdf", "UTF-8");
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
			document.save(response.getOutputStream());
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
