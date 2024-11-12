package com.example.temp.common.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.Content;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.ContentTooLongException;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.GlyphList;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.CommonUtils;
import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.logistics.mapper.LogisticsMapper;
import com.example.temp.api.logistics.service.AramexHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.VietnamPostHandler;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.ShipmentVO;
import com.example.temp.api.shipment.ShippingMapper;
import com.example.temp.api.shipment.ShippingService;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.AciFakeBlVO;
import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.DeliverTrackVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.common.vo.PdfPrintVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.ExpLicenceExcelVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.SmtpService;
import com.example.temp.trans.aci.AciAPI;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.cj.CJOrderVo;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.cj.CJPodVo;
import com.example.temp.trans.cj.CJTerminalVo;
import com.example.temp.trans.comn.HawbLookUpVo;
import com.example.temp.trans.cse.CseAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FBOrderVO;
import com.example.temp.trans.fastbox.FastBoxMapper;
import com.example.temp.trans.fastbox.FastBoxParameterVO;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.shipStation.ShipStationAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.example.temp.trans.yunexpress.YunExpressAPI;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.moebiusgames.pdfbox.table.PDFTable.PageSettings;
import com.openhtmltopdf.css.parser.property.PrimitivePropertyBuilders.FSPageWidth;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

@Service
public class ComnServiceImpl implements ComnService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${filePath}")
	String realFilePath;

	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	private SecurityKeyVO originKey = new SecurityKeyVO();

	@Autowired
	private ComnMapper mapper;

	@Autowired
	private MemberService usrService;

	@Autowired
	YongSungAPI ysApi;

	@Autowired
	EfsAPI efsApi;

	@Autowired
	AciAPI aciApi;

	@Autowired
	OcsAPI ocsApi;

	@Autowired
	SekoAPI sekoApi;

	@Autowired
	ItcAPI itcApi;

	@Autowired
	GtsAPI gtsAPI;

	@Autowired
	CseAPI cseAPI;

	@Autowired
	CJApi cjApi;

	@Autowired
	EmsApi emsApi;
	
	@Autowired
	Type86API t86Api;

	@Autowired
	FedexAPI fedexApi;
	
	@Autowired
	FastboxAPI fbApi;
	
	@Autowired
	HanjinAPI hjApi;
	
	@Autowired
	ShipStationAPI ssApi;
	
	@Autowired
	SmtpService smtpService;
	
	@Autowired
	YunExpressAPI ytApi;
	
	@Autowired
	ShippingService shippingService;
	
	@Autowired
	ShippingMapper shippingMapper;
	
	@Autowired
	FastBoxMapper fbMapper;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	CommonUtils commUtils;
	
	@Autowired
	LogisticsMapper logisticMapper;
	
	@Autowired
	VietnamPostHandler vnpHandler;
	
	@Autowired
	AramexHandler araHandler;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Override
	public ArrayList<NoticeVO> selectNotice(HashMap<String, Object> infoMap) throws Exception {
		return mapper.selectNoticeList(infoMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean insertMemberInfos(ManagerVO userInfo, InvUserInfoVO invUserInfo, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		userInfo.encryptData();
		userInfo.setUserPw(userInfo.encryptSHA256(userInfo.getUserPw()));
		userInfo.setAprvYn("N");
		if (request.getHeader("referer").contains("returnSignUp")) {
			userInfo.setRole("RETURN");
			// userInfo.setAprvYn("Y");
			userInfo.setAprvYn("N");
		} else if (request.getHeader("referer").contains("signUp")) {
			userInfo.setRole("USER");
			userInfo.setAprvYn("N");
		} else if (request.getHeader("referer").contains("normalReturnSignUp")) {
			userInfo.setRole("RETURN");
			userInfo.setAprvYn("N");
		} else {
			return false;
		}

		if (userInfo.getUserId() != null || !userInfo.getUserId().equals(null)) {
			HashMap<String, String> tests = new HashMap<String, String>();
			tests.put("userId", userInfo.getUserId());
			tests.put("wUserId", userInfo.getWUserId());
			tests.put("wUserIp", userInfo.getWUserIp());
			tests.put("wDate", userInfo.getWDate());
			int indexCnt = 0;
			String[] orgNationList = request.getParameterValues("orgNation");
			String[] dstnNationList = request.getParameterValues("dstnNation");
			for (int i = 0; i < orgNationList.length; i++) {
				for (int j = 0; j < dstnNationList.length; j++) {
					tests.put("orgNation", orgNationList[i]);
					tests.put("dstnNation", dstnNationList[j]);
					tests.put("transCode", "");
					tests.put("nationCode", "");
					tests.put("seq", Integer.toString((indexCnt + 1)));
					mapper.insertUserTransCom(tests);
				}
			}
		}
		String uid = mapper.selectUID();
		userInfo.setUserUid(uid);
		mapper.insertMemberInfo(userInfo);
		invUserInfo.setInvUserId(userInfo.getUserId());
		invUserInfo.encryptData();
		mapper.insertInvUserInfo(invUserInfo);
		
		smtpService.sendMailForReturnUser(userInfo);
		return true;

	}

	@Override
	public ArrayList<NationVO> selectNationCode() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNationCode();
	}

	@Override
	public HashMap<String, ArrayList<ZoneVO>> makeTransMap(HashMap<String, Object> transMaps) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<ZoneVO>> trkMap = new HashMap<String, ArrayList<ZoneVO>>();
		ArrayList<ZoneVO> temp = new ArrayList<ZoneVO>();
		ArrayList<ZoneVO> temp2 = new ArrayList<ZoneVO>();
		if (transMaps.get("dstns") == null || transMaps.get("orgs") == null) {
			return trkMap;
		}
		temp = mapper.makeTransMap(transMaps);

		for (int roop = 0; roop < temp.size(); roop++) {
			temp2 = new ArrayList<ZoneVO>();
			if (!temp.isEmpty() || trkMap.get(temp.get(roop).getOrgNation()) == null) {
				for (int roop2 = 0; roop2 < temp.size(); roop2++) {
					if (temp.get(roop).getOrgNation().equals(temp.get(roop2).getOrgNation())) {
						temp2.add(temp.get(roop2));
					}
				}
				trkMap.put(temp.get(roop).getOrgNation(), temp2);
			}
		}

		/* trkMap.put(key, value) */

		return trkMap;
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserTrkCom(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTrkCom(userId);
	}

	@Override
	public int selectTotalCntNotice(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCntNotice(infoMap);
	}

	@Override
	public NoticeVO selectNoticeDetail(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNoticeDetail(infoMap);
	}

	@Override
	public String selectStationToNation(String parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStationTonation(parameter);
	}

	@Override
	public ArrayList<NationVO> selectStationToNationList(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStationToNationList(parameters);
	}

	@Override
	public PdfPrintVO selectPdfPrintInfo(String orderNno, String PrintType) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectPdfPrintInfo(orderNno, PrintType);
	}

	@Override
	public int selectUserCnt(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserCnt(userId);
	}

	@Override
	public ArrayList<String> selectUserItem(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserItem(nno);
	}

	@Override
	public String selectSessionInfo(HashMap<String, String> sessionInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSessionInfo(sessionInfo);
	}

	@Override
	public DeliverTrackVO selectDeliverInfo(String hawbNo, String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDeliverInfo(hawbNo, orgStation);
	}

	@Override
	public String selectTransComByNno(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransComByNno(nno);
	}

	@Override
	public ArrayList<HashMap<String, String>> selectHawbListARA(ArrayList<String> orderNnoList) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbListARA(orderNnoList);
	}

	@Override
	public String selectOrgStationName(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrgStationName(orgStation);
	}

	private int getWeekOfYear(String date) {
		Calendar calendar = Calendar.getInstance();
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	@Override
	public void aramexPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String orderType)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String fileName = URLEncoder.encode("tempPdf", "UTF-8");
		String imgPath = realFilePath + "image/" + "aramex/";
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		SecurityKeyVO originKey = new SecurityKeyVO();

		float perMM = 1 / (10 * 2.54f) * 72;
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		
		String tempFileName = "";
		PDDocument doc = new PDDocument();
		// 문서 만들기
		// 폰트 생성
		// ttf 파일 사용하기
		InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
		InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
		InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		
		PDType0Font font = PDType0Font.load(doc, korean);
		
		ArrayList<HashMap<String, String>> hawbList = mapper.selectHawbListARA(orderNnoList);
		String orgStationName = mapper.selectOrgStationName((String) request.getSession().getAttribute("ORG_STATION"));
		File tempFile = null;
		int numbers = 1;
		for (int index = 0; index < hawbList.size(); index++) {
			doc = new PDDocument();
			
			String wDate = hawbList.get(index).get("wDate");
			String year = wDate.substring(0, 4);
			String month = wDate.substring(4, 6);
			String day = wDate.substring(6, 8);
			wDate = year + "-" + month + "-" + day;
			String week = Integer.toString(getWeekOfYear(wDate));
			URL website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/"
					+ hawbList.get(index).get("userId") + "_" + hawbList.get(index).get("hawbNo"));
			HttpURLConnection http = (HttpURLConnection) website.openConnection();
			int statusCode = http.getResponseCode();

			if (statusCode != 200) {// 이전에 암호화 된 애들 불러오는 것.
				website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/"
						+ AES256Cipher.AES_Encode(hawbList.get(index).get("hawbNo"), originKey.getSymmetryKey()));
			}
			// URL website = new
			// URL("http://img.mtuai.com/outbound/"+week+"/"+hawbList.get(index).get("userId")+"_"+hawbList.get(index).get("hawbNo"));
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(imgPath + hawbList.get(index).get("hawbNo") + ".pdf");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 처음부터 끝까지 다운로드
			fos.close();
			File file = new File(imgPath + hawbList.get(index).get("hawbNo") + ".pdf");

			pdfMerger.addSource(file);
			
			if (orderType.toLowerCase().equals("y")) {
				ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
				packingInfo = mapper.selectPdfPrintItemInfo(hawbList.get(index).get("nno"));
				
				PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(0);

				korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
				font = PDType0Font.load(doc, korean);
				PDPageContentStream cts = new PDPageContentStream(doc, page);
				
				cts.drawLine(10, 420, (pageStandard.getWidth()) - 10, 420);
				cts.drawLine(10, 370, (pageStandard.getWidth()) - 10, 370);
				cts.drawLine(10, 420, 10, 370);
				cts.drawLine((pageStandard.getWidth()) - 10, 420, (pageStandard.getWidth()) - 10, 370);
				cts.drawLine((pageStandard.getWidth()) - 70, 420, (pageStandard.getWidth()) - 70, 370);
				cts.drawLine((pageStandard.getWidth()) - 70, 400, (pageStandard.getWidth()) - 10, 400);
				
				cts.beginText();
				cts.setFont(font, 20);
				cts.newLineAtOffset(14.5f, 395f);
				cts.showText(hawbList.get(index).get("hawbNo"));
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 14);
				cts.newLineAtOffset(16.5f, 378f);
				cts.showText(hawbList.get(index).get("orderNo"));
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 407);
				cts.showText("총 내품수량");
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 12);
				cts.newLineAtOffset((pageStandard.getWidth()) - 45, 383.5f);
				cts.showText(String.valueOf(hawbList.get(index).get("totalItemCnt")));
				cts.endText();
				
				cts.drawLine(10, 370, 10, 350);
				cts.drawLine((pageStandard.getWidth()) - 10, 370, (pageStandard.getWidth()) - 10, 350);
				cts.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				
				cts.drawLine((pageStandard.getWidth()) - 135, 370, (pageStandard.getWidth()) - 135, 350);
				cts.drawLine((pageStandard.getWidth()) - 90, 370, (pageStandard.getWidth()) - 90, 350);
				cts.drawLine((pageStandard.getWidth()) - 55, 370, (pageStandard.getWidth()) - 55, 350);
				
				cts.beginText();
				cts.setFont(font, 10);
				cts.newLineAtOffset(15, 355.5f);
				cts.showText("내품명");
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 130, 355.5f);
				cts.showText("사입코드");
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 82, 355.5f);
				cts.showText("수량");
				cts.endText();
				
				cts.beginText();
				cts.setFont(font, 10);
				cts.newLineAtOffset((pageStandard.getWidth()) - 45, 355.5f);
				cts.showText("Rack");
				cts.endText();

				int limit = 0;
				float startLine = 338;
				String itemDetail = "";
				
				for (int itemIdx = 0; itemIdx < packingInfo.size(); itemIdx++) {

					itemDetail = packingInfo.get(itemIdx).get("itemDetail").toString();
					if (itemDetail.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
						limit = 22;
					} else {
						limit = 32;
					}
					int arraySize = (int) Math.ceil((double) itemDetail.length() / limit);
					String[] stringArr = new String[arraySize];
					
					int strIndex = 0;
					for (int start = 0; start < itemDetail.length(); start += limit) {
						stringArr[strIndex++] = itemDetail.substring(start, Math.min(start+limit, itemDetail.length()));
					}
					cts.beginText();
					cts.setFont(font, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 132, startLine - 3.5f);
					cts.showText(packingInfo.get(itemIdx).get("takeInCode").toString());
					cts.endText();
					
					cts.beginText();
					cts.setFont(font, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 79, startLine - 3.5f);
					cts.showText(packingInfo.get(itemIdx).get("itemCnt").toString());
					cts.endText();
					
					cts.beginText();
					cts.setFont(font, 9);
					cts.newLineAtOffset((pageStandard.getWidth()) - 47, startLine - 3.5f);
					cts.showText(packingInfo.get(itemIdx).get("rackCode").toString());
					cts.endText();
					
					for (int x = 0; x < stringArr.length; x++) {
						cts.beginText();
						cts.setFont(font, 8);
						cts.newLineAtOffset(12, startLine - 3.5f);
						cts.showText(stringArr[x]);
						cts.endText();
						
						if (x == stringArr.length-1)  {
							startLine -= 17;
						} else {
							startLine -= 15;
						}
					}
					//cts.drawLine(10, startLine+6.5f, (pageStandard.getWidth()) - 10, startLine+6.5f);
				}
				
				float endLine = startLine;
				
				cts.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				cts.drawLine(10, 355.5f, 10, endLine+6.5f);
				cts.drawLine((pageStandard.getWidth()) - 10, 355.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
				cts.drawLine(10, endLine+6.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
				cts.close();
				
				tempFileName = "packingListPdf"+numbers;
				tempFile = new File(imgPath + tempFileName +".pdf");
				doc.save(tempFile);
				pdfMerger.addSource(tempFile);
			}
			numbers++;
			doc.close();
		}

		pdfMerger.setDestinationStream(response.getOutputStream());
		pdfMerger.mergeDocuments(null);
		
		//  PrinterJob job = PrinterJob.getPrinterJob(); job.setPageable(new
		//  PDFPageable(doc)); job.print();
		 

		// 파일 삭제
		for (int index = 0; index < hawbList.size(); index++) {
			File targetPdfFile = new File(imgPath + hawbList.get(index) + ".pdf");
			if (targetPdfFile.exists()) {
				targetPdfFile.delete();
			}
		}
		
		File dirFile = new File(imgPath);
		String fileList[] = dirFile.list();
		for (int i = 0; i < fileList.length; i++) {
			String fileNm = fileList[i];
			if (fileNm.startsWith("packingListPdf")) {
				File delFile = new File(imgPath + File.separator + fileNm);
				delFile.delete();
			}
		}
		
	}

	@Override
	public void loadPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String fileName = URLEncoder.encode("tempPdf", "UTF-8");
		String imgPath = realFilePath + "image/" + "aramex/";
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		SecurityKeyVO originKey = new SecurityKeyVO();

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nnoList", orderNnoList);
		parameters.put("printType", printType);
		
		ArrayList<HashMap<String, String>> hawbList = new ArrayList<HashMap<String, String>>();
		hawbList = mapper.selectHawbListSaved(parameters);
		
		URL website = null;
		HttpURLConnection http = null;
		int statusCode = 0;
//		ArrayList<HashMap<String, String>> hawbList = mapper.selectHawbListSaved(parameters);
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");

		for (int index = 0; index < hawbList.size(); index++) {

			if (orgStation == null || orgStation.equals("")) {
				orgStation = hawbList.get(index).get("orgStation");
			}
			String wDate = hawbList.get(index).get("wDate");
			String year = wDate.substring(0, 4);
			String month = wDate.substring(4, 6);
			String day = wDate.substring(6, 8);
			wDate = year + "-" + month + "-" + day;
			String week = Integer.toString(getWeekOfYear(wDate));

			// URL website = new
			// URL("http://img.mtuai.com/outbound/"+week+"/"+AES256Cipher.AES_Encode(hawbList.get(index).get("hawbNo"),
			// originKey.getSymmetryKey()));
			
			String userId = "";

			/*
			website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/"
					+ hawbList.get(index).get("userId") + "_" + hawbList.get(index).get("hawbNo"));
			*/
			website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/"
					+ userId + "_" + hawbList.get(index).get("hawbNo"));
			http = (HttpURLConnection) website.openConnection();
			statusCode = http.getResponseCode();

			if (statusCode != 200) {// 이전에 암호화 된 애들 불러오는 것.
				website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/"
						+ AES256Cipher.AES_Encode(hawbList.get(index).get("hawbNo"), originKey.getSymmetryKey()));
				HttpURLConnection http2 = (HttpURLConnection) website.openConnection();
				int statusCode2 = http2.getResponseCode();
				if (statusCode != 200) {// 이전에 암호화 된 애들 불러오는 것.

				}
			}
			
			
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(imgPath + hawbList.get(index) + ".pdf");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 처음부터 끝까지 다운로드
			fos.close();
			File file = new File(imgPath + hawbList.get(index) + ".pdf");

			pdfMerger.addSource(file);
		}

		PDDocument doc = new PDDocument();
		pdfMerger.setDestinationStream(response.getOutputStream());
		pdfMerger.mergeDocuments(null);

		/*
		 * PrinterJob job = PrinterJob.getPrinterJob(); job.setPageable(new
		 * PDFPageable(doc)); job.print();
		 */

		// 파일 삭제
		for (int index = 0; index < hawbList.size(); index++) {
			File targetPdfFile = new File(imgPath + hawbList.get(index) + ".pdf");
			if (targetPdfFile.exists()) {
				targetPdfFile.delete();
			}
		}

	}

	@Override
	public void savedPdfSek(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String fileName = URLEncoder.encode("tempPdf", "UTF-8");
		String imgPath = realFilePath + "image/" + "aramex/";
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		SecurityKeyVO originKey = new SecurityKeyVO();

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nnoList", orderNnoList);
		parameters.put("printType", printType);
		ArrayList<HashMap<String, String>> hawbList = mapper.selectHawbListSaved(parameters);
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");

		for (int index = 0; index < hawbList.size(); index++) {

			if (orgStation == null || orgStation.equals("")) {
				orgStation = hawbList.get(index).get("orgStation");
			}
			String wDate = hawbList.get(index).get("wDate");
			String year = wDate.substring(0, 4);
			String month = wDate.substring(4, 6);
			String day = wDate.substring(6, 8);
			wDate = year + "-" + month + "-" + day;
			String week = Integer.toString(getWeekOfYear(wDate));
			// URL website = new
			// URL("http://img.mtuai.com/outbound/"+week+"/"+AES256Cipher.AES_Encode(hawbList.get(index).get("hawbNo"),
			// originKey.getSymmetryKey()));
			URL website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/" + "1489353" + "-"
					+ hawbList.get(index).get("hawbNo"));
			HttpURLConnection http = (HttpURLConnection) website.openConnection();

			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(imgPath + hawbList.get(index) + ".pdf");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 처음부터 끝까지 다운로드
			fos.close();
			File file = new File(imgPath + hawbList.get(index) + ".pdf");

			pdfMerger.addSource(file);
		}

		PDDocument doc = new PDDocument();
		pdfMerger.setDestinationStream(response.getOutputStream());
		pdfMerger.mergeDocuments(null);

		/*
		 * PrinterJob job = PrinterJob.getPrinterJob(); job.setPageable(new
		 * PDFPageable(doc)); job.print();
		 */

		// 파일 삭제
		for (int index = 0; index < hawbList.size(); index++) {
			File targetPdfFile = new File(imgPath + hawbList.get(index) + ".pdf");
			if (targetPdfFile.exists()) {
				targetPdfFile.delete();
			}
		}

	}

	@Override
	public void savePdf(String orderNno, String printType, String userId, String userIp) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			pdfPrintInfo = mapper.selectPdfPrintInfo(orderNno, printType);
			pdfPrintInfo.dncryptData();
			for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
				PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(totalPage);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				File markFile = new File(markPath);
				PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
				contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
				// 배경이미지 로드

				barcodes.setBarHeight(70);
				barcodes.setBarWidth(50);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
				int fontSize = 12; // Or whatever font size you want.
				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
				contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);

				/* 사각형 */
				contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
						152 * perMM - 149 * perMM);
				contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
						152 * perMM - 3 * perMM);
				contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
				contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

				if (printType.equals("YT")) {
					contentStream.drawLine(3 * perMM, 10 * perMM, (pageStandard.getWidth()) - 3 * perMM, 10 * perMM);
					drawText(orderNno, ARIAL, 10, 5 * perMM, 5 * perMM, contentStream);
				}
				// 1st Vertical Line
				contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
				// 1st horizontal Line
				// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
				// 2nd horizontal Line
				contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

				// Duty Terms.
//					fontSize = 7;
//					drawText("Duty Terms.",ARIAL, fontSize, 5*perMM, 146*perMM,contentStream);
//					fontSize = 20;
//					titleWidth = ARIAL.getStringWidth("DDP") / 1000 * fontSize;
//					drawText("DDP",ARIAL, fontSize, 8*perMM, 135*perMM,contentStream);

				// 3rd horizontal Line
				contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

				// 2nd Vertical Line
				contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
				// 3rd Vertical Line
				contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

				// 4th horizontal Line
				contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
				// 5th Vertical Line
				contentStream.drawLine(31 * perMM, 109 * perMM, 31 * perMM, 96 * perMM);
				/* 글자 표기 */
				drawText("Weight(A)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
				fontSize = 17;
				titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 5 * perMM, 115 * perMM, contentStream);
				fontSize = 8;
				titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
				drawText("KG", ARIAL, fontSize, 13 * perMM, 111 * perMM, contentStream);

				fontSize = 17;
				drawText("Weight(V)", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getUserWtc(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

				fontSize = 8;
				titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
				drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
				// Order No.
				fontSize = 7;
				drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
				fontSize = 15;
				drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
				fontSize = 12;
				drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

				drawText("Piece(s)", ARIAL, 7, 5 * perMM, 106 * perMM, contentStream);
				fontSize = 20;
				titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
				drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 8 * perMM, 98 * perMM,
						contentStream);

				fontSize = 7;
				drawText("Origin", ARIAL, fontSize, 34 * perMM, 106 * perMM, contentStream);
				fontSize = 15;
				drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 39 * perMM, 100 * perMM, contentStream);
				drawText("========>", ARIAL, fontSize, 50 * perMM, 100 * perMM, contentStream);

				fontSize = 7;
				drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
				fontSize = 15;
				drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

				// Shipper
				// 5th horizontal Line
				contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
				fontSize = 9;
				drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
				// 6th horizontal Line
				contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

				fontSize = 7;
				drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getUserId(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
						contentStream);

				fontSize = 7;
				drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
				drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
				fontSize = 9;
				drawText(pdfPrintInfo.getOrgNationCode(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
				drawText("State : ", ARIAL, fontSize, 50 * perMM, 78 * perMM, contentStream);
				fontSize = 9;
				drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 50 * perMM + titleWidth, 78 * perMM,
						contentStream);

				fontSize = 7;
				drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
				drawText("Zip Code : ", ARIAL, fontSize, 50 * perMM, 74 * perMM, contentStream);
				fontSize = 9;
				drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 50 * perMM + titleWidth, 74 * perMM,
						contentStream, 2);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
				drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
				fontSize = 9;
				drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL, fontSize,
						5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

				// receiver
				// 7th horizontal Line
				contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

				// Receiver
				fontSize = 9;
				drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
				// 6th horizontal Line
				contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

				fontSize = 7;
				drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
						contentStream);

				fontSize = 7;
				drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM, contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
				drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
				fontSize = 9;
				drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
				drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
						contentStream);

				fontSize = 7;
				drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
				drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
				fontSize = 9;
				drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
						contentStream, 2);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("ADDRESS : ") / 1000 * fontSize;
				drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
				fontSize = 9;
				drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
						5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

				// 바코드
				contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
				// 컨텐츠 스트림 닫기
				contentStream.close();
				totalPage++;
			}
			// 페이지 추가

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정

		// PDF 파일 출력
		doc.save(realFilePath + orderNno + "PDF.pdf");
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}
	



//	@Override - Origin
//	public void sagawaPdf(HttpServletRequest request, String orderNno, String printType) throws Exception {
//		SagawaVO sagawaInfo = new SagawaVO();
//		String userId = (String)request.getSession().getAttribute("USER_ID");
//		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
//		File dir = new File(pdfPath);
//		if (!dir.isDirectory()) {
//			dir.mkdir();
//		}
//		String pdfPath2 = pdfPath + "/barcode/";
//		File dir2 = new File(pdfPath2);
//		if (!dir2.isDirectory()) {
//			dir2.mkdir();
//		}
//		String barcodePath = pdfPath2 +userId+".JPEG";
//		String markPath = pdfPath+"/mark/aciMark.jpg";
//		ClassPathResource cssResource = new ClassPathResource("application.properties");
//		String mainPath = cssResource.getURL().getPath();
//		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
//		// 문서 만들기
//		final PDDocument doc = new PDDocument();
//		
//		
//	    // 폰트 생성
//	    // ttf 파일 사용하기
//	    InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
//	    InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
//	    InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
//	    InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
//	    
//	    
//	    PDType0Font MsGothic = PDType0Font.load(doc, japanese);
//	    PDType0Font ARIAL = PDType0Font.load(doc, english);
//	    PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
//	    PDType0Font NanumGothic = PDType0Font.load(doc, korean);
//	    
//	    float perMM = 1 / (10 * 2.54f) * 72;
//	    
//	    
//	    // 페이지 생성 후 PDF 만들기.
//	    try {
//	    	List<PDFont> fonts = new ArrayList<>();
//	    	fonts.add(MsGothic); 
//	    	fonts.add(ARIAL); 
//	    	fonts.add(ARIALBOLD); 
//	    	fonts.add(NanumGothic);
//	    	
//				
//			sagawaInfo = mapper.selectPdfPrintInfo(orderNno, printType);
//			sagawaInfo.dncryptData();
//			// 페이지 추가
//			PDRectangle	pageStandard = new PDRectangle(170*perMM, 110*perMM);
//			PDPage blankPage = new PDPage(pageStandard);
//	        doc.addPage(blankPage);
//	        
//	        // 현재 페이지 설정
//	        PDPage page = doc.getPage(0);
//	        
//	        // 컨텐츠 스트림 열기
//	        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//	        net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(sagawaInfo.getHawbNo());
//	        
//	        
//			
//			File barcodefile = new File(barcodePath);
//			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//			
//			File markFile = new File(markPath);
//			PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
//			contentStream.drawImage(markImage, 45, pageStandard.getHeight()-65, 60f, 25f);
//			// 배경이미지 로드
//	        
//	        
//
//			barcodes.setBarHeight(70);
//			barcodes.setBarWidth(50);
//
//			barcodes.setLabel("Barcode creation test...");
//			barcodes.setDrawingText(true);
//			
//			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//	        
//			/*사각형*/
//		    contentStream.drawLine(45, 20, pageStandard.getWidth()-45, 20);
//		    contentStream.drawLine(45, 20, 45, pageStandard.getHeight()-82);
//		    contentStream.drawLine(45, pageStandard.getHeight()-82, pageStandard.getWidth()-45, pageStandard.getHeight()-82);
//		    contentStream.drawLine(pageStandard.getWidth()-45, 20, pageStandard.getWidth()-45, pageStandard.getHeight()-82);
//
//		    /*직선*/
////			    contentStream.drawLine(45, pageStandard.getHeight()-90, 130, pageStandard.getHeight()-90);
//
//		    
//		    //가로
//		    contentStream.drawLine(45, pageStandard.getHeight()-105, pageStandard.getWidth()-45, pageStandard.getHeight()-105);
//		    
//		    //세로
////			    contentStream.drawLine(130, pageStandard.getHeight()-67, 130, pageStandard.getHeight()-90);
//		    
//		    contentStream.drawLine(180, pageStandard.getHeight()-82, 180, pageStandard.getHeight()-105);
////			    contentStream.drawLine(275, pageStandard.getHeight()-67, 275, pageStandard.getHeight()-90);
//		    contentStream.drawLine(335, pageStandard.getHeight()-82, 335, pageStandard.getHeight()-105);
//		    
////			    contentStream.drawLine(367, pageStandard.getHeight()-67, 367, pageStandard.getHeight()-90);
//		    
//		    
//		    
//		    
//		    //가로
//		    contentStream.drawLine(45, pageStandard.getHeight()-128, 130, pageStandard.getHeight()-128);
//		    //세로
//		    contentStream.drawLine(130, pageStandard.getHeight()-128, 130, pageStandard.getHeight()-105);
//		    
//		    //가로 긴줄
//		    contentStream.drawLine(45, pageStandard.getHeight()-165, pageStandard.getWidth()-45, pageStandard.getHeight()-165);
//		    
//		    //세로
//		    contentStream.drawLine(130, pageStandard.getHeight()-187, 130, pageStandard.getHeight()-165);
//		    //가로
//		    contentStream.drawLine(45, pageStandard.getHeight()-187, 130, pageStandard.getHeight()-187);
//		    
//		    /*글자 표기*/
//		    
//		    drawText("ORIGIN : "+sagawaInfo.getOrgStationName(), fonts.get(1), 10, 50, pageStandard.getHeight()-98, contentStream);
//		    drawText("DESTINATION : "+sagawaInfo.getDstnNation(), fonts.get(1), 10, 187, pageStandard.getHeight()-98, contentStream);
//		    drawText("PCS : "+sagawaInfo.getBoxCnt(), fonts.get(1), 10, 340, pageStandard.getHeight()-98, contentStream);
//		    
//		    drawText("SHIPPER INFO", fonts.get(1), 10, 50, pageStandard.getHeight()-120, contentStream);
//		    drawText("CONEE INFO", fonts.get(1), 10, 50, pageStandard.getHeight()-180, contentStream);
//	        
//		    //SHIPPER
//		    drawText("NAME : "+sagawaInfo.getShipperName(), fonts.get(1), 10, 135, pageStandard.getHeight()-120, contentStream);
//		    drawText("TEL : "+sagawaInfo.getShipperTel(), fonts.get(1), 10, 300, pageStandard.getHeight()-120, contentStream);
//		    drawTextLineEng("ADDRESS(EN) : "+sagawaInfo.getShipperAddr()+" "+sagawaInfo.getShipperAddrDetail(), fonts.get(1), 10, 50, pageStandard.getHeight()-143, contentStream, 1);
//		    
//		    //CONEE
//		    drawText("NAME : "+sagawaInfo.getCneeName(), fonts.get(1), 10, 135, pageStandard.getHeight()-180, contentStream);
//		    drawText("TEL : "+sagawaInfo.getCneeTel(), fonts.get(1), 10, 300, pageStandard.getHeight()-180, contentStream);
//		    drawTextLineEng("ADDRESS(EN) : "+sagawaInfo.getCneeAddr()+" "+sagawaInfo.getCneeAddrDetail(), fonts.get(1), 10, 50, pageStandard.getHeight()-203, contentStream,1);
//		    if(printType.equals("ACI")) {
//		    	drawText("ADDR(JP) : ", fonts.get(1), 10, 50, pageStandard.getHeight()-238, contentStream);
//		    	drawTextLine(sagawaInfo.getNativeCneeAddr()+sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 10, 107, pageStandard.getHeight()-238, contentStream,1);
//		    }
//		    if(printType.equals("YES")) {
//		    	contentStream.drawLine(45, pageStandard.getHeight()-228, pageStandard.getWidth()-45, pageStandard.getHeight()-228);
//		    	//세로
//			    contentStream.drawLine(130, pageStandard.getHeight()-228, 130, pageStandard.getHeight()-250);
//			    //가로
//			    contentStream.drawLine(45, pageStandard.getHeight()-250, 130, pageStandard.getHeight()-250);
//		    	drawText("DESCRIPTION ", fonts.get(1), 10, 50, pageStandard.getHeight()-243, contentStream);
//		    	drawText("ITEM COUNT : " + sagawaInfo.getItemCnt(), fonts.get(1), 10, 135, pageStandard.getHeight()-243, contentStream);
//		    	int totalItem = Integer.parseInt(sagawaInfo.getItemCnt());
//		    	if(totalItem > 2){
//		    		totalItem = 2;
//		    	}
//		    	ArrayList<String> itemDetailList = mapper.selectUserItem(sagawaInfo.getNno());
//		    	for(int roop = 0; roop < totalItem; roop++) {
//		    		String printTxt = itemDetailList.get(roop);
//		    		if(printTxt.length() > 60) {
//		    			printTxt = printTxt.substring(0,57);
//		    			printTxt = printTxt + "...";
//		    		}
//		    		drawText(printTxt, fonts.get(1), 10, 50, pageStandard.getHeight()-(266+(roop*18)), contentStream);
//		    	}
//		    	//drawTextLine(sagawaInfo.getNativeCneeAddr()+sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 10, 107, pageStandard.getHeight()-238, contentStream,1);
//		    }
//		    
//		    // 운송장 번호
//		    drawText(sagawaInfo.getHawbNo(), fonts.get(1), 15, 250, pageStandard.getHeight()-65, contentStream);
//		    //바코드
//	        contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
//	        //컨텐츠 스트림 닫기
//	        contentStream.close();
//	    }catch (Exception e) {
//			// TODO: handle exception
//	    	logger.error("Exception", e);
//		}
//	    
//		// 파일 다운로드 설정
//		doc.save(realFilePath+orderNno+"PDF.pdf");
////		PrinterJob job = PrinterJob.getPrinterJob();
////		job.setPageable(new PDFPageable(doc));
////		job.print();
//		doc.close();
//		
//		saveS3server(realFilePath+orderNno+"PDF.pdf",sagawaInfo.getUserId(),sagawaInfo.getHawbNo());
//		File barcodefile = new File(barcodePath);
//		if(barcodefile.exists()) {
//			barcodefile.delete();
//		}
//		
//	}

	public void saveS3server(String fileStored, String userId, String hawbNo) {
		File base64ToImg = new File(fileStored);

		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		amazonS3 = new AmazonS3Client(awsCredentials);
		PutObjectResult asssd = new PutObjectResult();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String week = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
		if (amazonS3 != null) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					comnS3Info.getBucketName() + "/outbound/hawb/" + year + "/" + week, userId + "_" + hawbNo,
					base64ToImg);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			asssd = amazonS3.putObject(putObjectRequest);
		}
		amazonS3 = null;
		base64ToImg.delete();
	}

//	@Override
//	public void sagawaPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception {
//		SagawaVO sagawaInfo = new SagawaVO();
//		String userId = (String)request.getSession().getAttribute("USER_ID");
//		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
//		File dir = new File(pdfPath);
//		if (!dir.isDirectory()) {
//			dir.mkdir();
//		}
//		String pdfPath2 = pdfPath + "/barcode/";
//		File dir2 = new File(pdfPath2);
//		if (!dir2.isDirectory()) {
//			dir2.mkdir();
//		}
//		String barcodePath = pdfPath2 +userId+".JPEG";
//		String markPath = pdfPath+"/mark/aciMark.jpg";
//		ClassPathResource cssResource = new ClassPathResource("application.properties");
//		String mainPath = cssResource.getURL().getPath();
//		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
//		// 문서 만들기
//		final PDDocument doc = new PDDocument();
//		
//		
//	    // 폰트 생성
//	    // ttf 파일 사용하기
//	    InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
//	    InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
//	    InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
//	    InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
//	    
//	    
//	    PDType0Font MsGothic = PDType0Font.load(doc, japanese);
//	    PDType0Font ARIAL = PDType0Font.load(doc, english);
//	    PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
//	    PDType0Font NanumGothic = PDType0Font.load(doc, korean);
//	    
//	    float perMM = 1 / (10 * 2.54f) * 72;
//	    
//	    
//	    // 페이지 생성 후 PDF 만들기.
//	    try {
//	    	List<PDFont> fonts = new ArrayList<>();
//	    	fonts.add(MsGothic); 
//	    	fonts.add(ARIAL); 
//	    	fonts.add(ARIALBOLD); 
//	    	fonts.add(NanumGothic);
//	    	
//			for(int i = 0; i < orderNnoList.size(); i++) {
//				
//				sagawaInfo = mapper.selectPdfPrintInfo(orderNnoList.get(i), printType);
//				sagawaInfo.dncryptData();
//				// 페이지 추가
//				PDRectangle	pageStandard = new PDRectangle(170*perMM, 110*perMM);
//				PDPage blankPage = new PDPage(pageStandard);
//		        doc.addPage(blankPage);
//		        
//		        // 현재 페이지 설정
//		        PDPage page = doc.getPage(i);
//		        
//		        // 컨텐츠 스트림 열기
//		        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//		        net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(sagawaInfo.getHawbNo());
//		        
//		        
//				
//				File barcodefile = new File(barcodePath);
//				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//				
//				File markFile = new File(markPath);
//				PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
//				contentStream.drawImage(markImage, 45, pageStandard.getHeight()-65, 60f, 25f);
//				// 배경이미지 로드
//		        
//		        
//	
//				barcodes.setBarHeight(70);
//				barcodes.setBarWidth(50);
//	
//				barcodes.setLabel("Barcode creation test...");
//				barcodes.setDrawingText(true);
//				
//				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        
//				/*사각형*/
//			    contentStream.drawLine(45, 20, pageStandard.getWidth()-45, 20);
//			    contentStream.drawLine(45, 20, 45, pageStandard.getHeight()-82);
//			    contentStream.drawLine(45, pageStandard.getHeight()-82, pageStandard.getWidth()-45, pageStandard.getHeight()-82);
//			    contentStream.drawLine(pageStandard.getWidth()-45, 20, pageStandard.getWidth()-45, pageStandard.getHeight()-82);
//
//			    /*직선*/
////			    contentStream.drawLine(45, pageStandard.getHeight()-90, 130, pageStandard.getHeight()-90);
//
//			    
//			    //가로
//			    contentStream.drawLine(45, pageStandard.getHeight()-105, pageStandard.getWidth()-45, pageStandard.getHeight()-105);
//			    
//			    //세로
////			    contentStream.drawLine(130, pageStandard.getHeight()-67, 130, pageStandard.getHeight()-90);
//			    
//			    contentStream.drawLine(180, pageStandard.getHeight()-82, 180, pageStandard.getHeight()-105);
////			    contentStream.drawLine(275, pageStandard.getHeight()-67, 275, pageStandard.getHeight()-90);
//			    contentStream.drawLine(335, pageStandard.getHeight()-82, 335, pageStandard.getHeight()-105);
//			    
////			    contentStream.drawLine(367, pageStandard.getHeight()-67, 367, pageStandard.getHeight()-90);
//			    
//			    
//			    
//			    
//			    //가로
//			    contentStream.drawLine(45, pageStandard.getHeight()-128, 130, pageStandard.getHeight()-128);
//			    //세로
//			    contentStream.drawLine(130, pageStandard.getHeight()-128, 130, pageStandard.getHeight()-105);
//			    
//			    //가로 긴줄
//			    contentStream.drawLine(45, pageStandard.getHeight()-165, pageStandard.getWidth()-45, pageStandard.getHeight()-165);
//			    
//			    //세로
//			    contentStream.drawLine(130, pageStandard.getHeight()-187, 130, pageStandard.getHeight()-165);
//			    //가로
//			    contentStream.drawLine(45, pageStandard.getHeight()-187, 130, pageStandard.getHeight()-187);
//			    
//			    /*글자 표기*/
//			    
//			    drawText("ORIGIN : "+sagawaInfo.getOrgStationName(), fonts.get(1), 10, 50, pageStandard.getHeight()-98, contentStream);
//			    drawText("DESTINATION : "+sagawaInfo.getDstnNation(), fonts.get(1), 10, 187, pageStandard.getHeight()-98, contentStream);
//			    drawText("PCS : "+sagawaInfo.getBoxCnt(), fonts.get(1), 10, 340, pageStandard.getHeight()-98, contentStream);
//			    
//			    drawText("SHIPPER INFO", fonts.get(1), 10, 50, pageStandard.getHeight()-120, contentStream);
//			    drawText("CONEE INFO", fonts.get(1), 10, 50, pageStandard.getHeight()-180, contentStream);
//		        
//			    //SHIPPER
//			    drawText("NAME : "+sagawaInfo.getShipperName(), fonts.get(1), 10, 135, pageStandard.getHeight()-120, contentStream);
//			    drawText("TEL : "+sagawaInfo.getShipperTel(), fonts.get(1), 10, 300, pageStandard.getHeight()-120, contentStream);
//			    drawTextLineEng("ADDRESS(EN) : "+sagawaInfo.getShipperAddr()+" "+sagawaInfo.getShipperAddrDetail(), fonts.get(1), 10, 50, pageStandard.getHeight()-143, contentStream, 1);
//			    //drawTextLineEng("ADDRESS(EN) : 123456789012345678901234567890123456789012345678901234567890", fonts.get(1), 10, 50, pageStandard.getHeight()-143, contentStream, 1);
//			    
//			    //CONEE
//			    drawText("NAME : "+sagawaInfo.getCneeName(), fonts.get(1), 10, 135, pageStandard.getHeight()-180, contentStream);
//			    drawText("TEL : "+sagawaInfo.getCneeTel(), fonts.get(1), 10, 300, pageStandard.getHeight()-180, contentStream);
//			    drawTextLineEng("ADDRESS(EN) : "+pdfPrintInfo.getCneeAddr()+" "+sagawaInfo.getCneeAddrDetail(), fonts.get(1), 10, 50, pageStandard.getHeight()-203, contentStream,1);
//			    //drawTextLineEng("ADDRESS(EN) : 123456789012345678901234567890123456789012345678901234567890", fonts.get(1), 10, 50, pageStandard.getHeight()-203, contentStream, 1);
//			    if(printType.equals("ACI")) {
//			    	drawText("ADDR(JP) : ", fonts.get(1), 10, 50, pageStandard.getHeight()-238, contentStream);
//			    	drawTextLine(sagawaInfo.getNativeCneeAddr()+sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 10, 107, pageStandard.getHeight()-238, contentStream,1);
//			    }
//			    if(printType.equals("YES")) {
//			    	contentStream.drawLine(45, pageStandard.getHeight()-228, pageStandard.getWidth()-45, pageStandard.getHeight()-228);
//			    	//세로
//				    contentStream.drawLine(130, pageStandard.getHeight()-228, 130, pageStandard.getHeight()-250);
//				    //가로
//				    contentStream.drawLine(45, pageStandard.getHeight()-250, 130, pageStandard.getHeight()-250);
//			    	drawText("DESCRIPTION ", fonts.get(1), 10, 50, pageStandard.getHeight()-243, contentStream);
//			    	drawText("ITEM COUNT : " + sagawaInfo.getItemCnt(), fonts.get(1), 10, 135, pageStandard.getHeight()-243, contentStream);
//			    	int totalItem = Integer.parseInt(sagawaInfo.getItemCnt());
//			    	if(totalItem > 2){
//			    		totalItem = 2;
//			    	}
//			    	ArrayList<String> itemDetailList = mapper.selectUserItem(sagawaInfo.getNno());
//			    	for(int roop = 0; roop < totalItem; roop++) {
//			    		String printTxt = itemDetailList.get(roop);
//			    		if(printTxt.length() > 60) {
//			    			printTxt = printTxt.substring(0,57);
//			    			printTxt = printTxt + "...";
//			    		}
//			    		drawText(printTxt, fonts.get(1), 10, 50, pageStandard.getHeight()-(266+(roop*18)), contentStream);
//			    	}
//			    	//drawTextLine(sagawaInfo.getNativeCneeAddr()+sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 10, 107, pageStandard.getHeight()-238, contentStream,1);
//			    }
////			    drawTextLine("田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎田寒太郎", fonts.get(0), 10, 107, pageStandard.getHeight()-243, contentStream,1);
//			    
//			    // 운송장 번호
//			    drawText(sagawaInfo.getHawbNo(), fonts.get(1), 15, 250, pageStandard.getHeight()-65, contentStream);
//			    //바코드
//		        contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
//		        //컨텐츠 스트림 닫기
//		        contentStream.close();
//			}
//			
//	    }catch (Exception e) {
//			// TODO: handle exception
//	    	logger.error("Exception", e);
//		}
//
//		// 파일 다운로드 설정
//		response.setContentType("application/pdf");
//		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
//		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
//		
//		// PDF 파일 출력
//		doc.save(response.getOutputStream());
////		PrinterJob job = PrinterJob.getPrinterJob();
////		job.setPageable(new PDFPageable(doc));
////		job.print();
//		doc.close();
//		File barcodefile = new File(barcodePath);
//		if(barcodefile.exists()) {
//			barcodefile.delete();
//		}
//		
//	}

	@Override
	public void yslPdf(String orderNno, String printType, String userId, String userIp, String blTypeOrg)
			throws Exception {
		PdfPrintVO printPdfInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);

			String blType = "default";
			if (blTypeOrg != null) {
				blType = blTypeOrg;
			}

			printPdfInfo = mapper.selectTempInfo(orderNno, printType);
			if (printPdfInfo == null) {
				return;
			}
			printPdfInfo.dncryptData();

			if (blType.equals("default")) {
				blType = mapper.selectBlTypeTransCom(printPdfInfo);
			}
			// 페이지 추가
			if (blType.equals("A")) {
				PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(0);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(printPdfInfo.getHawbNo2());

				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(printPdfInfo.getHawbNo());

				barcodes2.setSize(400, 800);
				barcodes2.setBarHeight(0);
				barcodes2.setBarWidth(0);

				barcodes2.setLabel("Barcode creation test...");
				barcodes2.setDrawingText(true);

				File barcodefile2 = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

				PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

				int fontSize = 10; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + printPdfInfo.getTotalCnt() + ")")
						/ 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 38, 200f, 30f);

				contentStream.drawImage(pdImage2, (80 * perMM - 200) / 2, 7, 200f, 15f);

				drawText(printType + "입고 (" + printPdfInfo.getTotalCnt() + ")", NanumGothic, fontSize,
						(80 * perMM - 80), 30 * perMM - 11, contentStream);

				fontSize = 9;
				titleWidth = NanumGothic.getStringWidth(printPdfInfo.getHawbNo()) / 1000 * fontSize;
				drawText(printPdfInfo.getHawbNo() + " ▽", NanumGothic, fontSize, 10 * perMM, 26, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(printPdfInfo.getHawbNo2()) / 1000 * fontSize;
				drawText(printPdfInfo.getHawbNo2() + " ▽", NanumGothic, fontSize, (10 * perMM), 30 * perMM - 11,
						contentStream);

				contentStream.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		doc.save(realFilePath + orderNno + "PDF.pdf");
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", printPdfInfo.getUserId(), printPdfInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

//	public void sagawaPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) throws Exception {
//		SagawaVO sagawaInfo = new SagawaVO();
//		String userId = (String)request.getSession().getAttribute("USER_ID");
//		int pageCount = 2;
//		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
//		File dir = new File(pdfPath);
//		if (!dir.isDirectory()) {
//			dir.mkdir();
//		}
//		String pdfPath2 = pdfPath + "/barcode/";
//		File dir2 = new File(pdfPath2);
//		if (!dir2.isDirectory()) {
//			dir2.mkdir();
//		}
//		String barcodePath = pdfPath2 +userId+".JPEG";
//		String webroot = pdfPath;
//		ClassPathResource cssResource = new ClassPathResource("application.properties");
//		String mainPath = cssResource.getURL().getPath();
//		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
//		// 문서 만들기
//		final PDDocument doc = new PDDocument();
//		
//		
//	    // 폰트 생성
//	    // ttf 파일 사용하기
//	    InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
//	    InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
//	    InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
//	    InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
//	    
//	    
//	    PDType0Font MsGothic = PDType0Font.load(doc, japanese);
//	    PDType0Font ARIAL = PDType0Font.load(doc, english);
//	    PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
//	    PDType0Font NanumGothic = PDType0Font.load(doc, korean);
//	    
//	    float perMM = 1 / (10 * 2.54f) * 72;
//	    
//	    
//	    // 페이지 생성 후 PDF 만들기.
//	    try {
//	    	List<PDFont> fonts = new ArrayList<>();
//	    	fonts.add(MsGothic); 
//	    	fonts.add(ARIAL); 
//	    	fonts.add(ARIALBOLD); 
//	    	fonts.add(NanumGothic);
//	    	
//			for(int i = 0; i < orderNnoList.size(); i++) {
//				
//				sagawaInfo = comnService.selectPdfPrintInfo(orderNnoList.get(i));
//				sagawaInfo.dncryptData();
//				// 페이지 추가
//				PDRectangle	asd = new PDRectangle(105*perMM, 210*perMM);
//				PDPage blankPage = new PDPage(asd);
//		        doc.addPage(blankPage);
//		        
//		        // 현재 페이지 설정
//		        PDPage page = doc.getPage(i);
//		        
//		        // 컨텐츠 스트림 열기
//		        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//		        
//		        String deliveryCode = sagawaInfo.getSalesno();
//		        String rtnDeliveryCode = "";
//		        if(deliveryCode.length() < 7) {
//		        	int test = 7 - deliveryCode.length();
//		        	for(int j =0; j < test; j++) {
//		        		rtnDeliveryCode += "$";
//		        	}
//		        	rtnDeliveryCode += deliveryCode;
//		        }else {
//		        	rtnDeliveryCode += deliveryCode;
//		        }
//		        
//		        net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
//						.createCodabar("c"+rtnDeliveryCode+"d");
//	
//				barcodes.setBarHeight(50);
//				barcodes.setBarWidth(1);
//	
//				barcodes.setLabel("Barcode creation test...");
//				barcodes.setDrawingText(true);
//				
//				BufferedImage image =BarcodeImageHandler.getImage(barcodes);
//				
//				File barcodefile = new File(barcodePath);
//				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//				// 배경이미지 로드
//			    PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        
//				
//				
//				/*
//				 * pdImage = PDImageXObject.createFromFile(
//				 * "C:\\Users\\operation\\git\\outBound\\tempProject\\src\\main\\webapp\\image\\test222.JPG"
//				 * ,doc); contentStream.drawImage(pdImage, 0, 0, 105*perMM, 210*perMM);
//				 */
//				 
//				 
//	            // 배경 이미지  그리기
//			    pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 20, 545, 63.7f, 28.3f);
//	//	        String orderDate = sagawaInfo.getOrderDate().substring(0, 4)+"年"+sagawaInfo.getOrderDate().substring(4, 6)+"月"+sagawaInfo.getOrderDate().substring(6, 8)+"日";
//		        
//		        // 글씨 쓰기
//				/* drawText("発送日:"+orderDate, MsGothic, 8, 89, 563, contentStream); */
//		        drawText("個数 "+sagawaInfo.getItemCnt(), fonts.get(0), 8, 180, 560, contentStream);
//		        
//		        
//		        
//		        drawText("便種 : 陸便", fonts.get(0), 9, 89, 545, contentStream);
//		        String texts = sagawaInfo.getSalesno();
//		        texts = texts.trim();
//		        String text2 = "";
//		        if(texts.length() < 5) {
//		        	int test = 5 - texts.length();
//		        	for(int j =0; j < test; j++) {
//		        		text2 += "  ";
//		        	}
//		        	text2 += texts;
//		        }else {
//		        	text2 = texts;
//		        }
//		        drawText(text2, fonts.get(0), 30, 160, 535, contentStream);
//		        
//		        drawText(sagawaInfo.getAreano(), fonts.get(0), 18, 250, 535, contentStream);
//		        
//		        drawText("干  "+sagawaInfo.getCneeZip(), fonts.get(0), 6, 23, 529, contentStream);
//		        drawText("TEL "+sagawaInfo.getCneeTel(), fonts.get(0), 6, 73, 529, contentStream);
//		        
//		        drawTextLine(sagawaInfo.getNativeCneeAddr(), fonts.get(0), 8, 23, 521, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 8, 23, 508, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeName(), fonts.get(0), 9, 23, 495, contentStream);
//		        
//		        
//		        barcodes = BarcodeFactory
//						.createCodabar("d"+sagawaInfo.getHawbNo()+"d");
//		        BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//		        pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 150, 493, 140f, 32.6f);	        
//		        
//		        drawText("お問合也送リ状: "+sagawaInfo.getHawbNo(), fonts.get(0), 7, 165, 486, contentStream);
//		        
//		        
//		        drawText("お問", fonts.get(0), 6, 193, 479, contentStream);
//				/* drawText("TEL 0120-333-803", MsGothic, 7, 220, 479, contentStream); pickup Branch Tel 
//				 * */
//		        
//		        drawTextLineEng(sagawaInfo.getShipperAddr()+sagawaInfo.getShipperAddrDetail(), fonts.get(1), 8, 23, 486, contentStream, 0);
//		        drawTextLineEng(sagawaInfo.getComEName(), fonts.get(1), 8, 23, 460, contentStream, 0);
//		        drawTextLine("Tel. "+sagawaInfo.getShipperTel(), fonts.get(0), 8, 152, 468, contentStream);
//		        
//				/* drawText("発送日 :"+orderDate, MsGothic, 7, 140, 406, contentStream); */
//		        drawText("お問合也送リ状: "+sagawaInfo.getHawbNo(), fonts.get(0), 7, 140, 398, contentStream);
//		        drawText("個数: "+sagawaInfo.getItemCnt(), fonts.get(0), 7, 250, 404, contentStream);
//		        
//		        drawText("干  "+sagawaInfo.getCneeZip(), fonts.get(0), 6, 23, 390, contentStream);
//		        drawText("TEL "+sagawaInfo.getCneeTel(), fonts.get(0), 6, 73, 390, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeAddr(), fonts.get(0), 8, 23, 382, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 8, 23, 366, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeName(), fonts.get(0), 9, 23, 355, contentStream);
//		        
//		        drawTextLine("", fonts.get(0), 6, 23, 345, contentStream);
//		        drawTextLineEng(sagawaInfo.getShipperAddr()+sagawaInfo.getShipperAddrDetail(), fonts.get(1), 8, 23, 333, contentStream, 0);
//		        drawTextLineEng(sagawaInfo.getComEName(), fonts.get(1), 8, 23, 313, contentStream, 0);
//		        drawText("TEL "+sagawaInfo.getShipperTel(), fonts.get(0), 6, 23, 303, contentStream);
//		        
//				/*
//				 * ArrayList<String> itemList = comnService.selectUserItem(sagawaInfo.getNno());
//				 * if(itemList.size() < 6) { for(int itemIndex = 0 ; itemIndex <
//				 * itemList.size(); itemIndex++) { drawTextLineItem(itemList.get(itemIndex),
//				 * MsGothic, 7, 23, 295-(itemIndex*7), contentStream, 0); } }else { for(int
//				 * itemIndex = 0 ; itemIndex < 5; itemIndex++) {
//				 * drawTextLineItem(itemList.get(itemIndex), MsGothic, 7, 23, 295-(itemIndex*7),
//				 * contentStream, 0); } }
//				 */
//		        
//		        
//		        
//		        
//		        drawTextLineStar("***************"
//		        				+"***************"
//		        				+"***************"
//		        				+"***************"
//		        				+"***************"
//		        				+"***************", MsGothic, 9, 145, 390, contentStream,1);
//		        
//		        barcodes = BarcodeFactory
//						.createCodabar("d002b");
//		        BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//		        pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 228, 375, 40f, 17f);
//		        drawText("2KG", fonts.get(0), 7, 245, 368, contentStream);
//		        
//		        barcodes = BarcodeFactory
//						.createCodabar("d005b");
//		        BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//		        pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 228, 347, 40f, 17f);
//		        drawText("5KG", fonts.get(0), 7, 245, 340, contentStream);
//		        
//		        barcodes = BarcodeFactory
//						.createCodabar("d010b");
//		        BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//		        pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 228, 320, 40f, 17f);
//		        drawText("10KG", fonts.get(0), 7, 242, 313, contentStream);
//		        
//		        drawTextLineStar(
//		        		 "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********", MsGothic, 9, 190, 305, contentStream,2);
//		        
//		        drawTextLineStar(
//		        		 "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********"
//		        		 + "*********", MsGothic, 9, 240, 305, contentStream,2);
//		        
//		        drawTextLineStar("**************", MsGothic, 7, 20, 230, contentStream,1);
//		        drawText("お問", fonts.get(0), 6, 145, 230, contentStream);
//		        drawText("TEL "+sagawaInfo.getCneeTel(), fonts.get(0), 6, 180, 228, contentStream);
//				/* drawText("発送日 :"+orderDate, MsGothic, 6, 145, 225, contentStream); */
//		        
//		        drawText(sagawaInfo.getItemCnt(), fonts.get(0), 11, 235, 230, contentStream);
//		        
//		        drawText("干  "+sagawaInfo.getCneeZip(), fonts.get(0), 9, 23, 212, contentStream);
//		        drawText("TEL "+sagawaInfo.getCneeTel(), fonts.get(0), 9, 93, 212, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeAddr(), fonts.get(0), 13, 49, 200, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeAddrDetail(), fonts.get(0), 13, 49, 171, contentStream);
//		        drawTextLine(sagawaInfo.getNativeCneeName(), fonts.get(0), 13, 49, 157, contentStream);
//		        
//		        barcodes = BarcodeFactory.createCodabar("d"+sagawaInfo.getHawbNo()+"d");
//		        BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//		        pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//		        contentStream.drawImage(pdImage, 35, 119, 150.2f, 33.0f);
//		        
//		        drawText("830011", fonts.get(0), 13, 55, 107, contentStream);
//		        drawText(sagawaInfo.getHawbNo(), fonts.get(0), 13, 100, 107, contentStream);
//		        drawText("119720230001", fonts.get(0), 13, 190, 107, contentStream);
//		        
//		        drawTextLineEng(sagawaInfo.getShipperAddr()+sagawaInfo.getShipperAddrDetail(), fonts.get(0), 7, 23, 70, contentStream, 1);
//		        drawTextLineEng(sagawaInfo.getComEName(), fonts.get(0), 7, 23, 50, contentStream, 1);
//		        drawTextLine("Tel. "+sagawaInfo.getShipperTel(), fonts.get(0), 7, 150, 70, contentStream);
//		        drawText("便種 :  便種", fonts.get(0), 7, 245, 45, contentStream);
//		        
//				/*
//				 * if(itemList.size() < 6) { for(int itemIndex = 0 ; itemIndex <
//				 * itemList.size(); itemIndex++) { drawTextLineItem(itemList.get(itemIndex),
//				 * MsGothic, 7, 23, 40-(itemIndex*7), contentStream, 0); } }else { for(int
//				 * itemIndex = 0 ; itemIndex < 5; itemIndex++) {
//				 * drawTextLineItem(itemList.get(itemIndex), MsGothic, 7, 23, 40-(itemIndex*7),
//				 * contentStream, 0); } }
//				 */
//		        
//		        drawTextLineStar(
//		        		 "**************"
//		        		+"**************"
//		        		+"**************"
//		        		+"**************"
//		        		 , MsGothic, 9, 210, 151, contentStream,3);
//		        
//		        
//		        // 컨텐츠 스트림 닫기
//		        contentStream.close();
//			}
//			
//	    }catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		// 파일 다운로드 설정
//		response.setContentType("application/pdf");
//		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
//		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
//		
//		// PDF 파일 출력
//		doc.save(response.getOutputStream());
////		PrinterJob job = PrinterJob.getPrinterJob();
////		job.setPageable(new PDFPageable(doc));
////		job.print();
//		doc.close();
//		File barcodefile = new File(barcodePath);
//		if(barcodefile.exists()) {
//			barcodefile.delete();
//		}
//		
//	}

	/**
	 * 글씨를 쓴다.
	 * 
	 * @param text
	 * @param font
	 * @param fontSize
	 * @param left
	 * @param bottom
	 * @param contentStream
	 * @throws Exception
	 */
	private void drawText(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream) throws Exception {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(left, bottom);
		contentStream.showText(text);
		contentStream.endText();
	}

	private void drawText2(String text, List<PDFont> fonts, int fontSize, float left, float bottom,
			PDPageContentStream contentStream) throws Exception {
		try {
			fonts.get(0).encode(text);
			contentStream.beginText();
			contentStream.setFont(fonts.get(0), fontSize);
			contentStream.newLineAtOffset(left, bottom);
			contentStream.showText(text);
			contentStream.endText();
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}
		int i = 0;
		contentStream.beginText();
		contentStream.newLineAtOffset(left, bottom);
		while (i < text.length()) {
			boolean found = false;
			for (PDFont font : fonts) {
				try {
					String s = text.substring(i, i + 1);
					font.encode(s);
					// it works! Try more with this font
					int j = i + 1;
					for (; j < text.length(); ++j) {
						String s2 = text.substring(j, j + 1);

						if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
							// Without this segment, the example would have a flaw:
							// This code tries to keep the current font, so
							// the second "abc" would appear in a different font
							// than the first one, which would be weird.
							// This segment assumes that the first font has WinAnsiEncoding.
							// (all static PDType1Font Times / Helvetica / Courier fonts)
							break;
						}
						try {
							font.encode(s2);
						} catch (IllegalArgumentException ex) {
							// it's over
							break;
						}
					}
					s = text.substring(i, j);
					contentStream.setFont(font, fontSize);
					contentStream.showText(s);
					i = j;
					found = true;
					break;
				} catch (IllegalArgumentException ex) {
					// didn't work, will try next font
				}
			}
			if (!found) {
				throw new IllegalArgumentException(
						"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
			}
		}
		contentStream.endText();
	}

	private void drawTextLine2(String text, List<PDFont> fonts, int fontSize, float left, float bottom,
			PDPageContentStream contentStream) throws Exception {
		int splitSize = 0;
		int fontss = 0;
		if (fontSize > 10) {
			splitSize = 22;
			fontss = 10;

		} else if (fontSize > 6) {
			splitSize = 16;
			fontss = 6;

		} else {
			splitSize = 23;
			fontss = 6;
		}
		try {
			fonts.get(0).encode(text);
			if (text.length() > splitSize) {
				for (int i = 0; i <= (int) (text.length() / splitSize); i++) {
					contentStream.beginText();
					contentStream.setFont(fonts.get(0), fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontss * i));
					if (text.substring((i * splitSize), text.length()).length() < splitSize) {
						contentStream.showText(text.substring((i * splitSize), text.length()));
					} else {
						contentStream.showText(text.substring((i * splitSize), splitSize + (splitSize * i)));
					}

					contentStream.endText();
				}
			} else {
				drawText(text, fonts.get(0), fontSize, left, bottom, contentStream);
			}
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}

		int i = 0;
		contentStream.beginText();
		contentStream.newLineAtOffset(left, bottom);
		while (i < text.length()) {
			if (text.length() > splitSize) {
				for (int k = 0; k <= (int) (text.length() / splitSize); k++) {
					contentStream.beginText();
					contentStream.setFont(fonts.get(i), fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontss * k));
					String targetStr = text.substring((k * splitSize), text.length());
					if (targetStr.length() < splitSize) {
						boolean found = false;
						for (PDFont font : fonts) {
							try {
								String s = targetStr.substring(i, i + 1);
								font.encode(s);
								// it works! Try more with this font
								int j = i + 1;
								for (; j < targetStr.length(); ++j) {
									String s2 = targetStr.substring(j, j + 1);

									if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
										// Without this segment, the example would have a flaw:
										// This code tries to keep the current font, so
										// the second "abc" would appear in a different font
										// than the first one, which would be weird.
										// This segment assumes that the first font has WinAnsiEncoding.
										// (all static PDType1Font Times / Helvetica / Courier fonts)
										break;
									}
									try {
										font.encode(s2);
									} catch (IllegalArgumentException ex) {
										// it's over
										break;
									}
								}
								s = targetStr.substring(i, j);
								contentStream.setFont(font, fontSize);
								contentStream.showText(s);
								i = j;
								found = true;
								break;
							} catch (IllegalArgumentException ex) {
								// didn't work, will try next font
							}
						}
						if (!found) {
							throw new IllegalArgumentException(
									"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
						}
					} else {
						boolean found = false;
						for (PDFont font : fonts) {
							try {
								String s = targetStr.substring(i, i + 1);
								font.encode(s);
								// it works! Try more with this font
								int j = i + 1;
								for (; j < targetStr.length(); ++j) {
									String s2 = targetStr.substring(j, j + 1);

									if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
										// Without this segment, the example would have a flaw:
										// This code tries to keep the current font, so
										// the second "abc" would appear in a different font
										// than the first one, which would be weird.
										// This segment assumes that the first font has WinAnsiEncoding.
										// (all static PDType1Font Times / Helvetica / Courier fonts)
										break;
									}
									try {
										font.encode(s2);
									} catch (IllegalArgumentException ex) {
										// it's over
										break;
									}
								}
								s = targetStr.substring(i, j);
								contentStream.setFont(font, fontSize);
								contentStream.showText(s);
								i = j;
								found = true;
								break;
							} catch (IllegalArgumentException ex) {
								// didn't work, will try next font
							}
						}
						if (!found) {
							throw new IllegalArgumentException(
									"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
						}
						contentStream.showText(text.substring((k * splitSize), splitSize + (splitSize * k)));
					}
				}
			} else {
				boolean found = false;
				for (PDFont font : fonts) {
					try {
						String s = text.substring(i, i + 1);
						font.encode(s);
						// it works! Try more with this font
						int j = i + 1;
						for (; j < text.length(); ++j) {
							String s2 = text.substring(j, j + 1);

							if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
								// Without this segment, the example would have a flaw:
								// This code tries to keep the current font, so
								// the second "abc" would appear in a different font
								// than the first one, which would be weird.
								// This segment assumes that the first font has WinAnsiEncoding.
								// (all static PDType1Font Times / Helvetica / Courier fonts)
								break;
							}
							try {
								font.encode(s2);
							} catch (IllegalArgumentException ex) {
								// it's over
								break;
							}
						}
						s = text.substring(i, j);
						contentStream.setFont(font, fontSize);
						contentStream.showText(s);
						i = j;
						found = true;
						break;
					} catch (IllegalArgumentException ex) {
						// didn't work, will try next font
					}
				}
				if (!found) {
					throw new IllegalArgumentException(
							"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
				}
			}
		}
		contentStream.endText();
	}

	private float drawTextLineLength(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int splitLength) throws Exception {
		String temp = "";
		int bottomVal = 0;
		float rtnVal = bottom;
		if (font.getStringWidth(text) > splitLength) {
			for (int index = 0; index < text.length(); index++) {
				temp += text.charAt(index);
				if (font.getStringWidth(temp) > splitLength) {
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal) - 1);
					contentStream.showText(temp);
					temp = "";
					rtnVal = bottom - (fontSize * bottomVal) - 1;
					bottomVal++;
					contentStream.endText();
				}
			}
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal) - 1);
			bottomVal++;
			rtnVal = bottom - (fontSize * bottomVal) - 1;
			contentStream.showText(temp);
			contentStream.endText();
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
			bottomVal++;
			rtnVal = bottom - (fontSize * bottomVal) - 1;
		}
		return rtnVal - 3;

	}

	private float drawTextLineLengthRight(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int splitLength, float width) throws Exception {
		String temp = "";
		int bottomVal = 0;
		float rtnVal = bottom;
		float perMM = 1 / (10 * 2.54f) * 72;
		if (font.getStringWidth(text) > splitLength) {
			for (int index = 0; index < text.length(); index++) {
				temp += text.charAt(index);
				if (font.getStringWidth(temp) > splitLength) {
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.newLineAtOffset(width - (font.getStringWidth(temp) / 1000 * fontSize) - 5 * perMM,
							bottom - (fontSize * bottomVal) - 1);
					contentStream.showText(temp);
					temp = "";
					rtnVal = bottom - (fontSize * bottomVal) - 1;
					bottomVal++;
					contentStream.endText();
				}
			}
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(width - (font.getStringWidth(temp) / 1000 * fontSize) - 5 * perMM,
					bottom - (fontSize * bottomVal) - 1);
			bottomVal++;
			rtnVal = bottom - (fontSize * bottomVal) - 1;
			contentStream.showText(temp);
			contentStream.endText();
		} else {
			// NanumGothic.getStringWidth("COMMERCIAL INVOICE") / 1000 * fontSize
			drawText(text, font, fontSize, width - (font.getStringWidth(text) / 1000 * fontSize) - 5 * perMM, bottom,
					contentStream);
			bottomVal++;
			rtnVal = bottom - (fontSize * bottomVal) - 1;
		}
		return rtnVal - 3;

	}

	private void drawTextLine(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int targetType) throws Exception {
		String asd = "";
		int splitSize = 0;
		int fontss = 0;
		if (targetType == 1) {
			splitSize = 30000;
			fontss = 12;
		} else if (targetType == 2) {
			splitSize = 37500;
		} else if (targetType == 3) {
			splitSize = 35000;
		} else if (targetType == 4) {
			splitSize = 21000;
		} else {
			if (fontSize > 10) {
				splitSize = 14700;
				fontss = 12;

			} else if (fontSize > 7) {
				splitSize = 14700;
				fontss = 8;

			} else {
				splitSize = 14700;
				fontss = 7;
			}
		}

		String temp = "";
		int bottomVal = 0;
		if (font.getStringWidth(text) > splitSize) {
			for (int index = 0; index < text.length(); index++) {
				temp += text.charAt(index);
				if (font.getStringWidth(temp) > splitSize) {
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
					contentStream.showText(temp);
					temp = "";
					bottomVal++;
					contentStream.endText();
				}
			}
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
			contentStream.showText(temp);
			contentStream.endText();
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
		}
	}

	private void drawTextLineEng2(String text, List<PDFont> fonts, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int type) throws Exception {
		int splitSize = 0;
		if (fontSize > 6) {
			splitSize = 24;

		} else {
			splitSize = 24;
		}

		if (type == 1) {
			splitSize = 30;
		}

		try {
			fonts.get(0).encode(text);
			if (text.length() > splitSize) {
				for (int i = 0; i <= (int) (text.length() / splitSize); i++) {
					contentStream.beginText();
					contentStream.setFont(fonts.get(0), fontSize);
					contentStream.newLineAtOffset(left, bottom - (splitSize * i));
					if (text.substring((i * splitSize), text.length()).length() < splitSize) {
						contentStream.showText(text.substring((i * splitSize), text.length()));
					} else {
						contentStream.showText(text.substring((i * splitSize), splitSize + (splitSize * i)));
					}

					contentStream.endText();
				}
			} else {
				drawText(text, fonts.get(0), fontSize, left, bottom, contentStream);
			}
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}

		int i = 0;
		contentStream.beginText();
		contentStream.newLineAtOffset(left, bottom);
		while (i < text.length()) {
			if (text.length() > splitSize) {
				for (int k = 0; k <= (int) (text.length() / splitSize); k++) {
					contentStream.beginText();
					contentStream.setFont(fonts.get(i), fontSize);
					contentStream.newLineAtOffset(left, bottom - (splitSize * k));
					String targetStr = text.substring((k * splitSize), text.length());
					if (targetStr.length() < splitSize) {
						boolean found = false;
						for (PDFont font : fonts) {
							try {
								String s = targetStr.substring(i, i + 1);
								font.encode(s);
								// it works! Try more with this font
								int j = i + 1;
								for (; j < targetStr.length(); ++j) {
									String s2 = targetStr.substring(j, j + 1);

									if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
										// Without this segment, the example would have a flaw:
										// This code tries to keep the current font, so
										// the second "abc" would appear in a different font
										// than the first one, which would be weird.
										// This segment assumes that the first font has WinAnsiEncoding.
										// (all static PDType1Font Times / Helvetica / Courier fonts)
										break;
									}
									try {
										font.encode(s2);
									} catch (IllegalArgumentException ex) {
										// it's over
										break;
									}
								}
								s = targetStr.substring(i, j);
								contentStream.setFont(font, fontSize);
								contentStream.showText(s);
								i = j;
								found = true;
								break;
							} catch (IllegalArgumentException ex) {
								// didn't work, will try next font
							}
						}
						if (!found) {
							throw new IllegalArgumentException(
									"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
						}
					} else {
						boolean found = false;
						for (PDFont font : fonts) {
							try {
								String s = targetStr.substring(i, i + 1);
								font.encode(s);
								// it works! Try more with this font
								int j = i + 1;
								for (; j < targetStr.length(); ++j) {
									String s2 = targetStr.substring(j, j + 1);

									if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
										// Without this segment, the example would have a flaw:
										// This code tries to keep the current font, so
										// the second "abc" would appear in a different font
										// than the first one, which would be weird.
										// This segment assumes that the first font has WinAnsiEncoding.
										// (all static PDType1Font Times / Helvetica / Courier fonts)
										break;
									}
									try {
										font.encode(s2);
									} catch (IllegalArgumentException ex) {
										// it's over
										break;
									}
								}
								s = targetStr.substring(i, j);
								contentStream.setFont(font, fontSize);
								contentStream.showText(s);
								i = j;
								found = true;
								break;
							} catch (IllegalArgumentException ex) {
								// didn't work, will try next font
							}
						}
						if (!found) {
							throw new IllegalArgumentException(
									"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
						}
					}
				}
			} else {
				boolean found = false;
				for (PDFont font : fonts) {
					try {
						String s = text.substring(i, i + 1);
						font.encode(s);
						// it works! Try more with this font
						int j = i + 1;
						for (; j < text.length(); ++j) {
							String s2 = text.substring(j, j + 1);

							if (isWinAnsiEncoding(s2.codePointAt(0)) && font != fonts.get(0)) {
								// Without this segment, the example would have a flaw:
								// This code tries to keep the current font, so
								// the second "abc" would appear in a different font
								// than the first one, which would be weird.
								// This segment assumes that the first font has WinAnsiEncoding.
								// (all static PDType1Font Times / Helvetica / Courier fonts)
								break;
							}
							try {
								font.encode(s2);
							} catch (IllegalArgumentException ex) {
								// it's over
								break;
							}
						}
						s = text.substring(i, j);
						contentStream.setFont(font, fontSize);
						contentStream.showText(s);
						i = j;
						found = true;
						break;
					} catch (IllegalArgumentException ex) {
						// didn't work, will try next font
					}
				}
				if (!found) {
					throw new IllegalArgumentException(
							"Could not show '" + text.substring(i, i + 1) + "' with the fonts provided");
				}
			}
		}
		contentStream.endText();
	}

	private void drawTextLineEng(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int type) throws Exception {
		int splitSize = 0;

		if (type == 1) {
			splitSize = 37500;
		} else {
			if (fontSize > 8) {
				splitSize = 14700;

			} else {
				splitSize = 14700;
			}
		}
		String temp = "";
		int bottomVal = 0;
		if (font.getStringWidth(text) > splitSize) {
			for (int index = 0; index < text.length(); index++) {
				temp += text.charAt(index);
				if (font.getStringWidth(temp) > splitSize) {
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
					contentStream.showText(temp);
					temp = "";
					bottomVal++;
					contentStream.endText();
				}
			}
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(left, bottom - (fontSize * bottomVal));
			contentStream.showText(temp);
			contentStream.endText();
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
		}
	}

	private void drawTextLineItem(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int type) throws Exception {
		String asd = "";
		int splitSize = 0;
		if (fontSize > 6) {
			splitSize = 24;

		}
		if (text.length() > splitSize) {
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(left, bottom);
			contentStream.showText(text.substring(0, 21) + "...");
			contentStream.endText();
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
		}

	}

	private void drawTextLineStar(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream, int type) throws Exception {
		String asd = "";
		int splitSize = 0;

		if (type == 1) {
			splitSize = 15;
		} else if (type == 2) {
			splitSize = 9;
		} else if (type == 3) {
			splitSize = 14;
		}

		if (text.length() > splitSize) {
			for (int i = 0; i <= (int) (text.length() / splitSize); i++) {
				contentStream.beginText();
				contentStream.setFont(font, fontSize);
				contentStream.newLineAtOffset(left, bottom - (6 * i));
				if (text.substring((i * splitSize), text.length()).length() < splitSize) {
					contentStream.showText(text.substring((i * splitSize), text.length()));
				} else {
					contentStream.showText(text.substring((i * splitSize), splitSize + (splitSize * i)));
				}

				contentStream.endText();
			}
		} else {
			drawText(text, font, fontSize, left, bottom, contentStream);
		}

	}

	static boolean isWinAnsiEncoding(int unicode) {
		String name = GlyphList.getAdobeGlyphList().codePointToName(unicode);
		if (".notdef".equals(name)) {
			return false;
		}
		return WinAnsiEncoding.INSTANCE.contains(name);
	}

	@Override
	public ArrayList<NationVO> selectNationInStation() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNationInStation();
	}

	@Override
	public InvUserInfoVO selectInvUserInfo(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectInvUserInfo(userId);
	}

	@Override
	public int selectSendListCount(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSendListCount(search);
	}

	@Override
	public int selectUnSendListCount(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUnSendListCount(search);
	}

	@Override
	public void ocsPdf(String orderNno, String printType, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			pdfPrintInfo = mapper.selectOcsInfo(orderNno, printType);
			pdfPrintInfo.dncryptData();

			// 페이지 추가
			for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
				// PDRectangle pageStandard = PDRectangle.LETTER;
				PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
				// new PDRectangle(180*perMM, 130*perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(totalPage);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
						.createNW7("A" + pdfPrintInfo.getHawbNo() + "A");

				// barcodes.setSize(100, 100);
				barcodes.setBarHeight(500);
				barcodes.setBarWidth(100);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				int fontSize = 12; // Or whatever font size you want.
				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
				// barcode
				contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 18 * perMM), 150f, 35f);

				// barcode under line text
				float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
				drawText(pdfPrintInfo.getHawbNo(), ARIAL, 9, (100 * perMM - 58 * perMM + 75 - (titleWidth / 2)),
						(152 * perMM - 18 * perMM - 4 * perMM), contentStream);
				contentStream.setLineWidth(0);

				// float xStart, float yStart, float xEnd, float yEnd

				// 4*6 PDRectangle Line
				contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
						152 * perMM - 149 * perMM);
				contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
						152 * perMM - 3 * perMM);
				contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
				contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

				// 1st Vertical Line
				contentStream.drawLine(40 * perMM, 149 * perMM, 40 * perMM, 129 * perMM);
				// 2nd Vertical Line
				contentStream.drawLine(22 * perMM, 149 * perMM, 22 * perMM, 129 * perMM);
				// 1st horizontal Line
				contentStream.drawLine(3 * perMM, 144 * perMM, 40 * perMM, 144 * perMM);
				// 2nd horizontal Line
				contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);
				drawText("Piece(s)", ARIAL, 7, 5 * perMM, 146 * perMM, contentStream);
				fontSize = 20;

				if (pdfPrintInfo.getBoxCnt().equals("1")) {
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 10 * perMM, 135 * perMM, contentStream);
				} else {
					titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 10 * perMM, 135 * perMM,
							contentStream);
				}

				fontSize = 17;
				drawText("Weight", ARIAL, 7, 24 * perMM, 146 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 135 * perMM, contentStream);
				fontSize = 8;
				titleWidth = ARIAL.getStringWidth("LB") / 1000 * fontSize;
				drawText("LB", ARIAL, fontSize, 34 * perMM, 132 * perMM, contentStream);

				// 3rd horizontal Line
				contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

				// 3rd Vertical Line
				contentStream.drawLine(30 * perMM, 129 * perMM, 30 * perMM, 109 * perMM);
				// 4th horizontal Line
				contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
				// Duty Terms.
				fontSize = 7;
				drawText("Duty Terms.", ARIAL, fontSize, 5 * perMM, 125.5f * perMM, contentStream);
				fontSize = 20;
				titleWidth = ARIAL.getStringWidth("DDP") / 1000 * fontSize;
				drawText("DDP", ARIAL, fontSize, 8 * perMM, 113 * perMM, contentStream);
				// Order No.
				fontSize = 7;
				drawText("Order No.", ARIAL, fontSize, 32 * perMM, 125.5f * perMM, contentStream);
				fontSize = 15;
				drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 32 * perMM, 113 * perMM, contentStream);

				// Shipper
				// 5th horizontal Line
				contentStream.drawLine(3 * perMM, 106 * perMM, (pageStandard.getWidth()) - 3 * perMM, 106 * perMM);
				fontSize = 9;
				drawText("Shipper", ARIAL, fontSize, 5 * perMM, 102 * perMM, contentStream);
				// 6th horizontal Line
				contentStream.drawLine(3 * perMM, 100 * perMM, (pageStandard.getWidth()) - 3 * perMM, 100 * perMM);

				fontSize = 7;
				drawText("Company Name : ", ARIAL, fontSize, 5 * perMM, 96 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Company Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText("AIR COURIERS INTERNATIONAL INC", ARIAL, fontSize, 5 * perMM + titleWidth, 96 * perMM,
						contentStream);

				fontSize = 7;
				drawText("Contract Name : ", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Contract Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText("HUGH KIM", ARIAL, fontSize, 5 * perMM + titleWidth, 92 * perMM, contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
				drawText("Country : ", ARIAL, fontSize, 5 * perMM, 88 * perMM, contentStream);
				fontSize = 9;
				drawText("UNITED STATES", ARIAL, fontSize, 5 * perMM + titleWidth, 88 * perMM, contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
				drawText("State : ", ARIAL, fontSize, 5 * perMM, 84 * perMM, contentStream);
				fontSize = 9;
				drawText("CA", ARIAL, fontSize, 5 * perMM + titleWidth, 84 * perMM, contentStream);

				fontSize = 7;
				drawText("City : ", ARIAL, fontSize, 5 * perMM, 80 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
				fontSize = 9;
				drawText("CERRITOS", ARIAL, fontSize, 5 * perMM + titleWidth, 80 * perMM, contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Address : ") / 1000 * fontSize;
				drawText("Address : ", ARIAL, fontSize, 5 * perMM, 76 * perMM, contentStream);
				fontSize = 9;
				drawTextLine("14056 ARTESIA BLVD", ARIAL, fontSize, 5 * perMM + titleWidth, 76 * perMM, contentStream,
						2);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
				drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 76 * perMM, contentStream);
				fontSize = 9;
				drawTextLine("90703", ARIAL, fontSize, 60 * perMM + titleWidth, 76 * perMM, contentStream, 2);

				fontSize = 7;
				drawTextLine("Address2 : ", ARIAL, fontSize, 5 * perMM, 72 * perMM, contentStream, 2);
				fontSize = 9;

				fontSize = 7;
				drawText("Tel.", ARIAL, fontSize, 5 * perMM, 68 * perMM, contentStream);
				fontSize = 9;
				drawText("310-965-0463", ARIAL, fontSize, 5 * perMM + titleWidth, 68 * perMM, contentStream);

				// receiver
				// 7th horizontal Line
				contentStream.drawLine(3 * perMM, 64 * perMM, (pageStandard.getWidth()) - 3 * perMM, 64 * perMM);
				fontSize = 9;
				drawText("Receiver", ARIAL, fontSize, 5 * perMM, 60 * perMM, contentStream);
				// 7th horizontal Line
				contentStream.drawLine(3 * perMM, 58 * perMM, (pageStandard.getWidth()) - 3 * perMM, 58 * perMM);

				fontSize = 7;
				drawText("Company Name : ", ARIAL, fontSize, 5 * perMM, 54 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Company Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 54 * perMM,
						contentStream);

				fontSize = 7;
				drawText("Company Name(JP) : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Company Name(JP) : ") / 1000 * fontSize;
				fontSize = 9;
				String nativeCneeName = pdfPrintInfo.getNativeCneeName().replaceAll("\\t", "");
				drawText(nativeCneeName, ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM, contentStream);

				fontSize = 7;
				drawText("Contract Name : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Contract Name : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(".", ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM, contentStream);

				fontSize = 7;
				drawText("Tel.", ARIAL, fontSize, 60 * perMM, 46 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Tel.  ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 60 * perMM + titleWidth, 46 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
				drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
				fontSize = 9;
				drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
						contentStream);

				fontSize = 7;
				drawText("State : ", ARIAL, fontSize, 60 * perMM, 42 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 60 * perMM + titleWidth, 42 * perMM,
						contentStream);

				fontSize = 7;
				drawText("City : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
				fontSize = 9;
				drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
						contentStream);

				fontSize = 7;
				titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
				drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 38 * perMM, contentStream);
				fontSize = 9;
				drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 38 * perMM,
						contentStream, 2);

				fontSize = 7;
				drawText("Address : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Address : ") / 1000 * fontSize;
				fontSize = 7;
				drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
						7 * perMM, 31 * perMM, contentStream, 3);

				fontSize = 7;
				drawText("Address(JP) : ", ARIAL, fontSize, 5 * perMM, 20 * perMM, contentStream);
				titleWidth = ARIAL.getStringWidth("Address(JP) : ") / 1000 * fontSize;
				fontSize = 7;
				String nativeCneeAddr = pdfPrintInfo.getNativeCneeAddr() + " " + pdfPrintInfo.getNativeCneeAddrDetail();
				//nativeCneeAddr = nativeCneeAddr.replaceAll("\\t", "");
				nativeCneeAddr = nativeCneeAddr.replaceAll("-", " ");
				drawTextLine(nativeCneeAddr, MsGothic, fontSize, 7 * perMM, 17 * perMM, contentStream, 3);

				contentStream.close();
				totalPage++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		doc.save(realFilePath + orderNno + "PDF.pdf");
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}
	}

	public HashMap<String, String> selectTransComChange(HashMap<String, String> transComChangeParameters)
			throws Exception {
		return mapper.selectTransComChange(transComChangeParameters);
	}

	@Override
	public ProcedureVO selectTransComChangeForVo(HashMap<String, Object> transParameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransComChangeForVo(transParameter);
	}

	@Override
	public void efsPdf(String orderNno, String printType, String userId, String userIp) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);

			pdfPrintInfo = mapper.selectTempInfo(orderNno, printType);
			pdfPrintInfo.dncryptData();
			// 페이지 추가
			PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
			PDPage blankPage = new PDPage(pageStandard);
			doc.addPage(blankPage);

			// 현재 페이지 설정
			PDPage page = doc.getPage(0);

			// 컨텐츠 스트림 열기
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

			barcodes.setSize(400, 800);
			barcodes.setBarHeight(0);
			barcodes.setBarWidth(0);

			barcodes.setLabel("Barcode creation test...");
			barcodes.setDrawingText(true);

			File barcodefile = new File(barcodePath);
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

			int fontSize = 10; // Or whatever font size you want.
			float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")") / 1000
					* fontSize;
			contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 31, 200f, 35f);

			drawText(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
					(80 * perMM - titleWidth) / 2, 30 * perMM - 11, contentStream);

			fontSize = 12;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getHawbNo(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 17, contentStream);

			fontSize = 10;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getHawbNo2(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 7, contentStream);

			contentStream.close();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		doc.save(realFilePath + orderNno + "PDF.pdf");
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public String selectUserTransCode(HashMap<String, Object> transParameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTransCode(transParameter);
	}

	@Override
	public String selectMatchNumByHawb(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMatchNumByHawb(hawbNo);
	}

	@Override
	public String selectTransCodeFromHawb(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeFromHawb(hawbNo);
	}

	@Override
	public HashMap<String, Object> execSpHoldBl(HashMap<String, Object> pramaterInfo) throws Exception {
		return mapper.execSpHoldBl(pramaterInfo);
	}

	@Override
	public ArrayList<MawbVO> mawbListLookUp(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.mawbListLookUp(parameters);
	}

	@Override
	public ArrayList<String> selectHawbByMawb(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbByMawb(mawbNo);
	}

	@Override
	public HawbLookUpVo selectHawbInfoJson(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbInfoJson(hawbNo);
	}

	@Override
	public void etcsPdf(String userId, String orderNno) throws Exception {
		// TODO Auto-generated method stub
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);

			pdfPrintInfo = mapper.selectTempInfoETCS(orderNno);
			pdfPrintInfo.dncryptData();
			// 페이지 추가
			PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
			PDPage blankPage = new PDPage(pageStandard);
			doc.addPage(blankPage);

			// 현재 페이지 설정
			PDPage page = doc.getPage(0);

			// 컨텐츠 스트림 열기
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
					.createCode39(pdfPrintInfo.getTrkNo().toUpperCase(), true);

			barcodes.setSize(400, 800);
			barcodes.setBarHeight(0);
			barcodes.setBarWidth(0);

			barcodes.setLabel("Barcode creation test...");
			barcodes.setDrawingText(true);

			File barcodefile = new File(barcodePath);
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

			int fontSize = 10; // Or whatever font size you want.
			float titleWidth = NanumGothic.getStringWidth("입고 (" + pdfPrintInfo.getTrkCom() + ")") / 1000 * fontSize;
			contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 31, 200f, 35f);

			drawText("입고 (" + pdfPrintInfo.getTrkCom() + ")", NanumGothic, fontSize, (80 * perMM - titleWidth) / 2,
					30 * perMM - 11, contentStream);

			fontSize = 10;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getTrkNo()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getTrkNo(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 19, contentStream);

			fontSize = 12;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderText()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getOrderText(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 6,
					contentStream);
			contentStream.close();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		// PDF 파일 출력
		doc.save(realFilePath + orderNno + "PDF.pdf");
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void krPostPdf(String orderNno, String printType, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String barcodePath2 = pdfPath2 + userId + "2.JPEG";
		String qrPath = pdfPath2 + userId + "qrCode.JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
//		String markPath2 = pdfPath+"/mark/test2.png";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			String terminalNo = "";
			String terminalName = "";
			String centerNo = "";
			String centerName = "";
			String deliveryTeamNo = "";
			String deliveryPointName = "";

			pdfPrintInfo = mapper.selectTempInfo(orderNno, printType);
			pdfPrintInfo.dncryptData();

			HashMap<String, Object> itemInfos = mapper.selectItemInfos(pdfPrintInfo.getNno());
			// 페이지 추가
			try {
				String addr = URLEncoder.encode(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(),
						"UTF-8");
				String obj = "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip="
						+ pdfPrintInfo.getCneeZip() + "&addr=" + addr + "&mdiv=1";

				// String obj =
				// "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip=22376&addr=인천광역시
				// 중구 영종대로252번길 12 운서동 영종LH2단지 아파트 205동1310호&mdiv=1";
				// If response status == 200
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc2 = dBuilder.parse(obj);
				doc2.getDocumentElement().normalize();
				NodeList resultNodeList = doc2.getElementsByTagName("result");

				Node nNode = resultNodeList.item(0);
				Element eElement = (Element) nNode;
				terminalNo = getTagValue("delivAreaCd", eElement).substring(0, 2);
				terminalName = getTagValue("arrCnpoNm", eElement);
				centerNo = getTagValue("delivAreaCd", eElement).substring(2, 5);
				centerName = getTagValue("delivPoNm", eElement);
				deliveryTeamNo = getTagValue("delivAreaCd", eElement).substring(5, 7);
				deliveryPointName = getTagValue("delivAreaCd", eElement).substring(7, 9);

			} catch (Exception e) {
				throw e;
			}

			PDRectangle pageStandard = new PDRectangle(168 * perMM, 107 * perMM);
			PDPage blankPage = new PDPage(pageStandard);
			doc.addPage(blankPage);

			// 현재 페이지 설정
			PDPage page = doc.getPage(0);

			// 컨텐츠 스트림 열기 바코드 1
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getCneeZip());

			barcodes.setSize(120, 800);
			barcodes.setBarHeight(1);
			barcodes.setBarWidth(1);

			File barcodefile = new File(barcodePath);
			BufferedImage source = BarcodeImageHandler.getImage(barcodes);

			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

			// 바코드 2
			net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

			barcodes2.setSize(300, 800);
			barcodes2.setBarHeight(1);
			barcodes2.setBarWidth(1);

			File barcodefile2 = new File(barcodePath2);

			BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

			File markFile = new File(markPath);
			PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
			contentStream.drawImage(markImage, 35 * perMM, pageStandard.getHeight() - 35f, 60f, 25f);

			// qrcode 생성
			File qrCodeFile = new File(qrPath);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(pdfPrintInfo.getHawbNo(), BarcodeFormat.QR_CODE, 200, 200); // 텍스트,
																													// 바코드
																													// 포맷,가로,세로

			MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF); // 진한색, 연한색
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

			ImageIO.write(qrImage, "png", qrCodeFile); // temp 위치에 qr이 이미지 생성됨.
			// InputStream is = new FileInputStream(temp.getAbsolutePath()); // 인풋 스트림으로
			// 변환(향후 S3로 업로드하기위한 작업)

			// 배경이미지 로드
			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

			PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(qrCodeFile, doc);

			PDImageXObject pdImage3 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

			int yWidth = 0;

			int fontSize = 10; // Or whatever font size you want.

			/* 왼쪽 */
			fontSize = 12;
			yWidth += fontSize;
			float titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getShipperName()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getShipperName(), NanumGothic, fontSize, 4 * perMM,
					pageStandard.getHeight() - yWidth - 19 * perMM, contentStream);

			fontSize = 11;
			yWidth += fontSize;
			String lineString = "Order No:" + pdfPrintInfo.getOrderNo();
			drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM, pageStandard.getHeight() - yWidth - 19 * perMM,
					contentStream, 0);

			fontSize = 7;
			yWidth = fontSize;
			lineString = "자가사용 목적 통관물품 상용판매시 처벌 받을 수 있습니다";
			drawText(lineString, NanumGothic, fontSize, 4 * perMM, pageStandard.getHeight() - yWidth - 38 * perMM,
					contentStream);

			yWidth += fontSize;
			lineString = itemInfos.get("itemDetail").toString();
			if (!itemInfos.get("itemTotalCnt").toString().equals("1")) {
				lineString = lineString + " others " + itemInfos.get("itemTotalCnt").toString();
			}
			drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM, pageStandard.getHeight() - yWidth - 38 * perMM,
					contentStream, 0);
			/* 왼쪽 끝 */

			/* 오른쪽 */
			fontSize = 15;
			yWidth = fontSize;
			titleWidth = NanumGothic.getStringWidth(terminalNo) / 1000 * fontSize;
			drawText(terminalNo, NanumGothic, fontSize, 63 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
					contentStream);

			fontSize = 8;
			yWidth = fontSize;
			titleWidth = NanumGothic.getStringWidth(terminalName) / 1000 * fontSize;
			drawText(terminalName, NanumGothic, fontSize, 71 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
					contentStream);

			fontSize = 15;
			yWidth = fontSize;
			titleWidth = NanumGothic.getStringWidth(centerNo) / 1000 * fontSize;
			drawText(centerNo, NanumGothic, fontSize, 79 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
					contentStream);

			fontSize = 8;
			yWidth = fontSize;
			titleWidth = NanumGothic.getStringWidth(centerName) / 1000 * fontSize;
			drawText(centerName, NanumGothic, fontSize, 88 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
					contentStream);

			fontSize = 15;
			yWidth = fontSize;
			titleWidth = NanumGothic.getStringWidth(deliveryTeamNo) / 1000 * fontSize;
			drawText(deliveryTeamNo, NanumGothic, fontSize, 97 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
					contentStream);

			fontSize = 15;
			titleWidth = NanumGothic.getStringWidth(deliveryPointName) / 1000 * fontSize;
			drawText(deliveryPointName, NanumGothic, fontSize, 105 * perMM, pageStandard.getHeight() - 15 - 8 * perMM,
					contentStream);

			contentStream.drawImage(pdImage2, 115 * perMM, pageStandard.getHeight() - 15 - 12 * perMM, 40f, 40f);

			fontSize = 15;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeZip()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getCneeZip(), NanumGothic, fontSize, 72 * perMM,
					pageStandard.getHeight() - 15 - 22 * perMM, contentStream);

			contentStream.drawImage(pdImage, 95 * perMM, pageStandard.getHeight() - 15 - 27 * perMM, 100f, 30f);

			fontSize = 15;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeName()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getCneeName(), NanumGothic, fontSize, 68 * perMM,
					pageStandard.getHeight() - 23 - 35 * perMM, contentStream);

			fontSize = 10;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeTel()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getCneeTel(), NanumGothic, fontSize, 120 * perMM,
					pageStandard.getHeight() - 23 - 33 * perMM, contentStream);

			fontSize = 12;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddr()) / 1000 * fontSize;
			drawTextLine(pdfPrintInfo.getCneeAddr(), NanumGothic, fontSize, 68 * perMM,
					pageStandard.getHeight() - 23 - 40 * perMM, contentStream, 0);

			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddrDetail()) / 1000 * fontSize;
			drawTextLine(pdfPrintInfo.getCneeAddrDetail(), NanumGothic, fontSize, 68 * perMM,
					pageStandard.getHeight() - 23 - 50 * perMM, contentStream, 0);

			contentStream.drawImage(pdImage3, 68 * perMM, pageStandard.getHeight() - 23 - 80 * perMM, 200f, 30f);

			fontSize = 12;
			titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
			drawText(pdfPrintInfo.getHawbNo(), NanumGothic, fontSize, 88 * perMM,
					pageStandard.getHeight() - 23 - 85 * perMM, contentStream);

			/* 오른쪽 끝 */
//				fontSize = 12;
//				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
//				drawText(pdfPrintInfo.getHawbNo(),NanumGothic, fontSize, (80*perMM - titleWidth) / 2, 17,contentStream);

			contentStream.close();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		doc.save(realFilePath + orderNno + "PDF.pdf");
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		File barcodefile2 = new File(barcodePath2);
		File barcodefile3 = new File(qrPath);

		if (barcodefile.exists()) {
			barcodefile.delete();
		}

		if (barcodefile2.exists()) {
			barcodefile2.delete();
		}

		if (barcodefile3.exists()) {
			barcodefile3.delete();
		}

	}

	@Override
	public void cjPdf(ArrayList<CJParameterVo> cJParameters, String userId, String userIp) throws Exception {

		String pdfPath = realFilePath + "/pdf";
		String markPath = pdfPath + "/mark/aciMark.jpg";

		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}

		try {
			PDDocument doc = new PDDocument();
			String fileName;

			Date now = new Date();
			SimpleDateFormat formatYmd = new SimpleDateFormat("yyyyMMdd");
			String nowYmd = formatYmd.format(now);

			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String mainPath = cssResource.getURL().getPath();
			String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
			InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

			PDType0Font NanumGothic = PDType0Font.load(doc, korean);
			float perMM = 1 / (10 * 2.54f) * 72;

			SecurityKeyVO securityKey = new SecurityKeyVO();
			String symmetryKey = securityKey.getSymmetryKey();

			for (int i = 0; i < cJParameters.size(); i++) {

				String nno = cJParameters.get(i).getNno();
				CJParameterVo cJPatemterOne = new CJParameterVo();
				cJPatemterOne.setNno(nno);
				CJOrderVo cjOrderList = new CJOrderVo();
				cjOrderList = cjApi.selectCjNnoOne(cJPatemterOne);

				PDRectangle pageStandard = new PDRectangle(123 * perMM, 100 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(i);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);

				nno = cjOrderList.getNno();

				String hawbNo = cjOrderList.getHawbNo();
				String hawbNoFomat = hawbNo.substring(0, 4) + "-" + hawbNo.substring(4, 8) + "-"
						+ hawbNo.substring(8, 12);
				String cneeName = cjOrderList.getCneeName();
				String cneeAddr = cjOrderList.getCneeAddr();
				String cneeAddrDetail = cjOrderList.getCneeAddrDetail();
				String cneeTel = cjOrderList.getCneeTel();
				String cneeHp = cjOrderList.getCneeHp();
				String shipperName = cjOrderList.getShipperName();
				String shipperTel = cjOrderList.getShipperTel();
				String shipperAddr = cjOrderList.getShipperAddr();
				String shipperAddrDetail = cjOrderList.getShipperAddrDetail();
				String dlvMsg = cjOrderList.getDlvReqMsg();
				String itemDetail = cjOrderList.getItemDetail();
				String buySite = cjOrderList.getBuySite();

				try {
					cneeTel = AES256Cipher.AES_Decode(cneeTel, symmetryKey);
					cneeHp = AES256Cipher.AES_Decode(cneeHp, symmetryKey);
					cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
					cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
					shipperTel = AES256Cipher.AES_Decode(shipperTel, symmetryKey);
					shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
					shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);

				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}

				cneeTel = cneeTel.replaceAll("[^0-9]", "");
				cneeHp = cneeHp.replaceAll("[^0-9]", "");

				String cneeTel1 = "";
				String cneeTel2 = "";
				String cneeTel3 = "";
				String cneeHp1 = "";
				String cneeHp2 = "";
				String cneeHp3 = "";

				if (cneeTel.length() > 9) {
					String[] tellArray;
					try {
						tellArray = cjApi.phoneNumberSplit(cneeTel);
						cneeTel1 = tellArray[0];
						cneeTel2 = tellArray[1];
						cneeTel3 = tellArray[2];
					} catch (Exception e) {
						logger.error("Exception", e);
					}

				}

				if (cneeHp.length() > 10) {
					String[] phoneArray;
					try {
						phoneArray = cjApi.phoneNumberSplit(cneeHp);
						cneeHp1 = phoneArray[0];
						cneeHp2 = phoneArray[1];
						cneeHp3 = phoneArray[2];
					} catch (Exception e) {
						logger.error("Exception", e);
					}

				}

				CJParameterVo cJParameterVo = new CJParameterVo();
				CJTerminalVo cJTerminalVo = new CJTerminalVo();

				String cjTerminal1 = "";
				String cjTerminal2 = "";
				String cjSubTermianl = "";
				String cjRcvrClsfAddr = "";
				String cjDlvClsfCd = "";
				String cjDlvPreArr = "";

				try {
					cJParameterVo.setAddr(cneeAddr + " " + cneeAddrDetail);
					cJParameterVo.setNno(nno);
					cJTerminalVo = cjApi.fnCJTerminal(cJParameterVo);

					if (cJTerminalVo.getDlvClsfCd().length() > 0) {
						cjDlvClsfCd = cJTerminalVo.getDlvClsfCd();
						cjTerminal1 = cJTerminalVo.getDlvClsfCd().substring(0, 1);
						cjTerminal2 = cJTerminalVo.getDlvClsfCd().substring(1, 4);
						cjSubTermianl = cJTerminalVo.getDlvSubClsfCd();
						cjRcvrClsfAddr = cJTerminalVo.getRcvrClsfAddr();
						cjDlvPreArr = cJTerminalVo.getDlvPreArrBranShortNm() + "-" + cJTerminalVo.getDlvPreArrEmpNm()
								+ "-" + cJTerminalVo.getDlvPreArrEmpNickNm();

					}
				} catch (Exception e) {
					logger.error("Exception", e);
				}

				if (!cJTerminalVo.getErrorCd().equals("0")) {

					contentStream.beginText();
					contentStream.newLineAtOffset(12 * perMM, 50 * perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream.showText(cJTerminalVo.getErrorMsg());
					contentStream.endText();
				}

				if (cneeAddr.length() > 40) {
					cneeAddrDetail = cneeAddr.substring(41, cneeAddr.length()) + " " + cneeAddrDetail;
					cneeAddr = cneeAddr.substring(0, 40);
				}

				contentStream.beginText();
				contentStream.newLineAtOffset(12 * perMM, 94 * perMM);
				contentStream.setFont(NanumGothic, 12);
				contentStream.showText(hawbNoFomat);
				contentStream.endText();

				contentStream.beginText();
				contentStream.newLineAtOffset(50 * perMM, 94 * perMM);
				contentStream.setFont(NanumGothic, 8);
				contentStream.showText(nowYmd);
				contentStream.endText();

				if (cJTerminalVo.getErrorCd().equals("0")) {

					contentStream.beginText();
					contentStream.newLineAtOffset(38 * perMM, 79 * perMM);
					contentStream.setFont(NanumGothic, 32);
					contentStream.showText(cjTerminal1);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(46 * perMM, 79 * perMM);
					contentStream.setFont(NanumGothic, 52);
					contentStream.showText(cjTerminal2);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(83 * perMM, 79 * perMM);
					contentStream.setFont(NanumGothic, 32);
					contentStream.showText("-" + cjSubTermianl);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 56 * perMM);
					contentStream.setFont(NanumGothic, 20);
					contentStream.showText(cjRcvrClsfAddr);
					contentStream.endText();

					// cjDlvPrearr
					contentStream.beginText();
					contentStream.newLineAtOffset(3 * perMM, 1 * perMM);
					contentStream.setFont(NanumGothic, 12);
					contentStream.showText(cjDlvPreArr);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 71 * perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream.showText(cneeName);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(30 * perMM, 71 * perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream
							.showText(cneeTel1 + "-" + cneeTel2 + "-****" + " / " + cneeHp1 + "-" + cneeHp2 + "-****");
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 67 * perMM);
					contentStream.setFont(NanumGothic, 9);
					contentStream.showText(cneeAddr);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 63 * perMM);
					contentStream.setFont(NanumGothic, 9);
					contentStream.showText(cneeAddrDetail);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 52 * perMM);
					contentStream.setFont(NanumGothic, 7);
					contentStream.showText(shipperName + " " + shipperTel);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(7 * perMM, 49 * perMM);
					contentStream.setFont(NanumGothic, 7);
					contentStream.showText(shipperAddr);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(5 * perMM, 39 * perMM);
					contentStream.setFont(NanumGothic, 9);
					contentStream.showText(itemDetail);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(2 * perMM, 11 * perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(cjRcvrClsfAddr);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(2 * perMM, 8 * perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(dlvMsg);
					contentStream.endText();

					contentStream.beginText();
					contentStream.newLineAtOffset(55 * perMM, 30 * perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(buySite);
					contentStream.endText();
				}

				contentStream.beginText();
				contentStream.newLineAtOffset(55 * perMM, 25 * perMM);
				contentStream.setFont(NanumGothic, 8);
				contentStream.showText("자가사용 목적 통관물품 상용판매시 처벌 받을수 있습니다");
				contentStream.endText();

				Barcode barcodes = BarcodeFactory.createCode128(hawbNo);
				barcodes.setLabel("Barco");
				barcodes.setDrawingText(true);

				String barcodePath = pdfPath2 + hawbNo + ".JPEG";
				File barcodefile = new File(barcodePath);

				try {
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
				} catch (OutputException e) {
					logger.error("Exception", e);
				}

				PDImageXObject hawbBarCode = PDImageXObject.createFromFileByContent(barcodefile, doc);
				contentStream.drawImage(hawbBarCode, 73 * perMM, 70 * perMM, 38 * perMM, 5 * perMM);

				contentStream.drawImage(hawbBarCode, 83 * perMM, 3 * perMM, 35 * perMM, 11 * perMM);

				contentStream.beginText();
				contentStream.newLineAtOffset(90 * perMM, 0 * perMM);
				contentStream.setFont(NanumGothic, 7);
				contentStream.showText(hawbNo);
				contentStream.endText();

				if (cJTerminalVo.getErrorCd().equals("0")) {
					barcodes = BarcodeFactory.createCode128(cjDlvClsfCd);
					barcodePath = pdfPath2 + "cjDlvClsfCd" + ".JPEG";
					barcodefile = new File(barcodePath);
					try {
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					} catch (OutputException e) {
						logger.error("Exception", e);
					}

					PDImageXObject cjDlvClsfCdBarCode = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(cjDlvClsfCdBarCode, 3 * perMM, 77 * perMM, 35 * perMM, 15 * perMM);
				}

				File aciLogFile = new File(markPath);
				PDImageXObject aciLog = PDImageXObject.createFromFileByContent(aciLogFile, doc);
				contentStream.drawImage(aciLog, 3 * perMM, 15 * perMM, 30 * perMM, 15 * perMM);
				contentStream.close();

				doc.save(realFilePath + nno + "PDF.pdf");
				doc.close();
				saveS3server(realFilePath + nno + "PDF.pdf", userId, hawbNo);

				if (barcodefile.exists()) {
					if (barcodefile.delete()) {
						System.out.println("파일삭제 성공");
					} else {
						System.out.println("파일삭제 실패");
					}
				}
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("Exception", e);
		}

	}

	public Image makeColorTransparent(BufferedImage im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {
			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	private String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	private BufferedImage imageToBufferedImage(Image image) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return bufferedImage;
	}

	@Override
	public void printPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType, String orderType) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
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
		
		// 2023.01.19 추가
		PDFMergerUtility merger = new PDFMergerUtility();
		
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			for (int i = 0; i < orderNnoList.size(); i++) {
				pdfPrintInfo = mapper.selectPdfPrintInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				packingInfo = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
					// 배경이미지 로드

					barcodes.setBarHeight(70);
					barcodes.setBarWidth(50);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
					int fontSize = 12; // Or whatever font size you want.
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
							45f);

					/* 사각형 */
					contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 149 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 3 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
					contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
							(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

					// 1st Vertical Line
					contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
					// 1st horizontal Line
					// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
					// 2nd horizontal Line
					contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

					// Duty Terms.
//					fontSize = 7;
//					drawText("Duty Terms.",ARIAL, fontSize, 5*perMM, 146*perMM,contentStream);
//					fontSize = 20;
//					titleWidth = ARIAL.getStringWidth("DDP") / 1000 * fontSize;
//					drawText("DDP",ARIAL, fontSize, 8*perMM, 135*perMM,contentStream);

					// 3rd horizontal Line
					contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

					// 2nd Vertical Line
					contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
					// 3rd Vertical Line
					contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

					// 4th horizontal Line
					contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);

					/* 글자 표기 */
					drawText("Piece(s)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
					fontSize = 20;
					titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 7 * perMM, 115 * perMM,
							contentStream);

					fontSize = 17;
					drawText("Weight", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
					drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
					// Order No.
					fontSize = 7;
					drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
					fontSize = 12;
					drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

					fontSize = 7;
					drawText("Origin", ARIAL, fontSize, 10 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 15 * perMM, 100 * perMM, contentStream);
					drawText("========>", ARIAL, fontSize, 34 * perMM, 100 * perMM, contentStream);

					fontSize = 7;
					drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

					// Shipper
					// 5th horizontal Line
					contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
					fontSize = 9;
					drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 60 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 60 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 74 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 74 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
							fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

					// receiver
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

					// Receiver
					fontSize = 9;
					drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
							5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

					// 바코드
					contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
				}
				
				if (orderType.toLowerCase().equals("y")) {
					totalPage = totalPage;
					
					for (int idx = 0; idx < Integer.parseInt(pdfPrintInfo.getBoxCnt()); idx++) {
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(totalPage);
					PDPageContentStream cts = new PDPageContentStream(doc, page);
					
					cts.drawLine(10, 420, (pageStandard.getWidth()) - 10, 420);
					cts.drawLine(10, 370, (pageStandard.getWidth()) - 10, 370);
					cts.drawLine(10, 420, 10, 370);
					cts.drawLine((pageStandard.getWidth()) - 10, 420, (pageStandard.getWidth()) - 10, 370);
					cts.drawLine((pageStandard.getWidth()) - 70, 420, (pageStandard.getWidth()) - 70, 370);
					cts.drawLine((pageStandard.getWidth()) - 70, 400, (pageStandard.getWidth()) - 10, 400);
					
					
					cts.beginText();
					cts.setFont(NanumGothic, 20);
					cts.newLineAtOffset(14.5f, 395f);
					cts.showText(pdfPrintInfo.getHawbNo());
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 14);
					cts.newLineAtOffset(16.5f, 378f);
					cts.showText(pdfPrintInfo.getOrderNo());
					cts.endText();

					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 407);
					cts.showText("총 내품수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 12);
					cts.newLineAtOffset((pageStandard.getWidth()) - 45, 383.5f);
					cts.showText(pdfPrintInfo.getTotalCnt());
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
					float startLine = 338;
					String itemDetail = "";
					
					for (int items = 0; items < packingInfo.size(); items++) {
						float fixLine = 5;
						
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
						cts.newLineAtOffset((pageStandard.getWidth()) - 132, startLine - 3);
						cts.showText(packingInfo.get(items).get("takeInCode").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 79, startLine - 3);
						cts.showText(packingInfo.get(items).get("itemCnt").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 47, startLine - 3);
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
					
					cts.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
					cts.drawLine(10, 355.5f, 10, endLine+6.5f);
					cts.drawLine((pageStandard.getWidth()) - 10, 355.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					cts.drawLine(10, endLine+6.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					
					cts.close();
					
					totalPage++;
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void printGTSPdfLegacy(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			for (int i = 0; i < orderNnoList.size(); i++) {
				pdfPrintInfo = mapper.selectPdfPrintInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
					// 배경이미지 로드

					barcodes.setBarHeight(70);
					barcodes.setBarWidth(50);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
					int fontSize = 12; // Or whatever font size you want.
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
							45f);

					/* 사각형 */
					contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 149 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 3 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
					contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
							(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

					// 1st Vertical Line
					contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
					// 1st horizontal Line
					// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
					// 2nd horizontal Line
					contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

					// Duty Terms.
//						fontSize = 7;
//						drawText("Duty Terms.",ARIAL, fontSize, 5*perMM, 146*perMM,contentStream);
//						fontSize = 20;
//						titleWidth = ARIAL.getStringWidth("DDP") / 1000 * fontSize;
//						drawText("DDP",ARIAL, fontSize, 8*perMM, 135*perMM,contentStream);

					// 3rd horizontal Line
					contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

					// 2nd Vertical Line
					contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
					// 3rd Vertical Line
					contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

					// 4th horizontal Line
					contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
					// 5th Vertical Line
					contentStream.drawLine(31 * perMM, 109 * perMM, 31 * perMM, 96 * perMM);
					/* 글자 표기 */
					drawText("Weight(A)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
					fontSize = 17;
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 5 * perMM, 115 * perMM, contentStream);
					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
					drawText("KG", ARIAL, fontSize, 13 * perMM, 111 * perMM, contentStream);

					fontSize = 17;
					drawText("Weight(V)", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWtc(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
					drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
					// Order No.
					fontSize = 7;
					drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
					fontSize = 12;
					drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

					drawText("Piece(s)", ARIAL, 7, 5 * perMM, 106 * perMM, contentStream);
					fontSize = 20;
					titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 8 * perMM, 98 * perMM,
							contentStream);

					fontSize = 7;
					drawText("Origin", ARIAL, fontSize, 34 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 39 * perMM, 100 * perMM, contentStream);
					drawText("========>", ARIAL, fontSize, 50 * perMM, 100 * perMM, contentStream);

					fontSize = 7;
					drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

					// Shipper
					// 5th horizontal Line
					contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
					fontSize = 9;
					drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getOrgNationCode(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 50 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 50 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 50 * perMM, 74 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 50 * perMM + titleWidth, 74 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
							fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

					// receiver
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

					// Receiver
					fontSize = 9;
					drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS : ") / 1000 * fontSize;
					drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
							5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

					// 바코드
					contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
					// 컨텐츠 스트림 닫기
					contentStream.close();
					totalPage++;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void yslPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType, String orderType) throws Exception {
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
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

				pdfPrintInfo = mapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				packingInfo = mapper.selectPdfPrintItemInfo(orderNnoList.get(i));
				
				
				if (blType.equals("default")) {
					blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
				}
				


				if (blType.equals("A")) {
					PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					//PDPage page = doc.getPage(i);
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					barcodes2.setSize(400, 800);
					barcodes2.setBarHeight(0);
					barcodes2.setBarWidth(0);

					barcodes2.setLabel("Barcode creation test...");
					barcodes2.setDrawingText(true);

					File barcodefile2 = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

					PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

					int fontSize = 10; // Or whatever font size you want.
					float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")")
							/ 1000 * fontSize;
					contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 38, 200f, 30f);

					contentStream.drawImage(pdImage2, (80 * perMM - 200) / 2, 7, 200f, 15f);

					drawText(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
							(80 * perMM - 80), 30 * perMM - 11, contentStream);

					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo() + " ▽ "+pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, 10 * perMM, 26, contentStream);

					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo2() + " ▽", NanumGothic, fontSize, (10 * perMM), 30 * perMM - 11,
							contentStream);

//					fontSize = 10;
//					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName()) / 1000 * fontSize;
//					drawText(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName(),NanumGothic, 10, (80*perMM - titleWidth) / 2, 117,contentStream);
//					
//					fontSize = 11;
//					titleWidth = NanumGothic.getStringWidth(tempResultVO.getRackCode()) / 1000 * fontSize;
//					drawText(tempResultVO.getRackCode(),NanumGothic, 11, (80*perMM - titleWidth) / 2, 102,contentStream);
//					
//					fontSize = 12;
//					String itemDetailSub = tempResultVO.getItemDetail();
//					if(NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize > 80*perMM) {
//						itemDetailSub = itemDetailSub.substring(0,30);
//						itemDetailSub = itemDetailSub+"...";
//					}
//					
//					
//					titleWidth = NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize;
//					drawText(itemDetailSub,NanumGothic, 12, (80*perMM - titleWidth) / 2, 90,contentStream);
//					
//					fontSize = 13;
//					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getStockNo()) / 1000 * fontSize;
//					drawText(tempStockVoList.get(stockIndex).getStockNo(),NanumGothic, 13, (80*perMM - titleWidth) / 2, 25,contentStream);
					// 컨텐츠 스트림 닫기
					contentStream.close();
					totalPage++;
				} else if (blType.equals("B")) {
					for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 55 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);

						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);

						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);

						contentStream.drawLine(3 * perMM, 3 * perMM, (pageStandard.getWidth()) - 3 * perMM, 3 * perMM);
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight() - 3 * perMM),
								(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight() - 3 * perMM));
						contentStream.drawLine(97 * perMM, 3 * perMM, 97 * perMM,
								(pageStandard.getHeight()) - 3 * perMM);
						contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageStandard.getHeight()) - 3 * perMM);

						// barcode1
						net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
								.createCode128(pdfPrintInfo.getHawbNo2());
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
						barcodes.setSize(400, 800);
						barcodes.setBarHeight(0);
						barcodes.setBarWidth(0);
						barcodes.setLabel("Barcode creation test...");
						barcodes.setDrawingText(true);
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

						// barcode2
						net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory
								.createCode128(pdfPrintInfo.getHawbNo());
						barcodes2.setSize(400, 800);
						barcodes2.setBarHeight(0);
						barcodes2.setBarWidth(0);
						barcodes2.setLabel("Barcode creation test...");
						barcodes2.setDrawingText(true);
						File barcodefile2 = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);
						PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

						// barcode print
						contentStream.drawImage(pdImage, (100 * perMM - 48 * perMM) / 2,
								(pageStandard.getHeight()) - 15 * perMM, 200f, 30f);
						contentStream.drawImage(pdImage2, (100 * perMM - 48 * perMM) / 2,
								(pageStandard.getHeight()) - 27 * perMM, 200f, 15f);

						int fontSize = 10; // Or whatever font size you want.
						float titleWidth = NanumGothic
								.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")") / 1000
								* fontSize;

						fontSize = 9;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getHawbNo() + " △", NanumGothic, fontSize, 48 * perMM, 24 * perMM,
								contentStream);

						fontSize = 10;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getHawbNo2() + "  △", NanumGothic, fontSize, (46 * perMM), 36 * perMM,
								contentStream);

						// 바코드 아래 가로
						contentStream.drawLine(3 * perMM, 22 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								22 * perMM);
						// 바코드 왼쪽 세로
						contentStream.drawLine(27 * perMM, 22 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 3 * perMM);
						// 바코드 왼쪽 위에 가로
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 10 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 10 * perMM);

						// 입고 배송업체 및 개수
						// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
						// fontSize, 6*perMM, (pageStandard.getHeight()-8*perMM),contentStream);
						// 배송국가 header
						// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
						// fontSize, 7*perMM, 52*perMM,contentStream);
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 17 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 17 * perMM);
						drawText("도착국가", NanumGothic, fontSize, 8 * perMM, (pageStandard.getHeight() - 15 * perMM),
								contentStream);
						fontSize = 20;
						drawText(pdfPrintInfo.getDstnNation(), NanumGothic, fontSize, 11 * perMM, 28 * perMM,
								contentStream);

						fontSize = 15;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderNo()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, (100 * perMM - titleWidth) / 2,
								14 * perMM, contentStream);
						fontSize = 9;
						titleWidth = NanumGothic.getStringWidth("(" + pdfPrintInfo.getShipperName() + ")") / 1000
								* fontSize;
						drawText("(" + pdfPrintInfo.getShipperName() + ")", NanumGothic, fontSize,
								(100 * perMM - titleWidth) / 2, 9 * perMM, contentStream);

						// 컨텐츠 스트림 닫기
						contentStream.close();
						totalPage++;
					}
				} else if (blType.equals("C")) {
					String yslNo = pdfPrintInfo.getHawbNo2();
					String aciNo = pdfPrintInfo.getHawbNo();
					String orderNo = pdfPrintInfo.getOrderNo();
					
					String yslBarcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
					String aciBarcodePath = pdfPath2 + userId + "_" + aciNo + ".JPEG";
					
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
					//Barcode aciBarcode = BarcodeFactory.createCode128(orderNo);
					aciBarcode.setBarHeight(70);
					aciBarcode.setDrawingQuietSection(false);
					
					File barcodeFile = new File(yslBarcodePath);
					BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);
					
					File barcodeFile2 = new File(aciBarcodePath);
					BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);
					
					PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
					PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
					
					String pdfText = "";
					pdfText = printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")";
					
					int yslImgW = yslBarcodeImage.getWidth();
					int yslImgH = yslBarcodeImage.getHeight();
					int pdfYslWidth = 250;
					int pdfYslHeight = (int) ((double) pdfYslWidth * yslImgH / yslImgW);
					
					int aciImgW = aciBarcodeImage.getWidth();
					int aciImgH = aciBarcodeImage.getHeight();
					int pdfAciWidth = 220;
					int pdfAciHeight = (int) ((double) pdfAciWidth * aciImgH / aciImgW);
					
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
					//cts.showText(orderNo+" ▽");
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
					
					cts.close();
					totalPage++;
					
					
					File barcodefile = new File(yslBarcodePath);
					if (barcodefile.exists()) {
						barcodefile.delete();
					}
					
					File barcodefile2 = new File(aciBarcodePath);
					if (barcodefile2.exists()) {
						barcodefile2.delete();
					}
				} else if (blType.equals("D")) {
					
					String yslNo = pdfPrintInfo.getHawbNo2();
					String aciNo = pdfPrintInfo.getHawbNo();
					String orderNo = pdfPrintInfo.getOrderNo();

					String yslBarcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
					String aciBarcodePath = pdfPath2 + userId + "_" + aciNo + ".JPEG";

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

					File barcodeFile = new File(yslBarcodePath);
					BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);

					File barcodeFile2 = new File(aciBarcodePath);
					BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);

					PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
					PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
					
					cts.drawLine(5, 5, 5, (pageHeight - 5));
					cts.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
					cts.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
					cts.drawLine((pageWidth - 5), 5, 5, 5);
					
					float xStart = 10;
					float yStart = (pageHeight - 20); 
					float yEnd = (pageHeight - 10);
					float xEnd = (pageWidth - 10);
					
					int fontSize = 10;
					
					cts.beginText();
					cts.newLineAtOffset(xStart + 20, yStart);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(yslNo + " ▽");
					cts.endText();

					float yslBarcodeWidth = yslBarcodeImage.getWidth();
					float yslBarcodeHeight = yslBarcodeImage.getHeight();
					float aciBarcodeWidth = aciBarcodeImage.getWidth();
					float aciBarcodeHeight = aciBarcodeImage.getHeight();
					
					int pdfYslW = 220;
					int pdfYslH = (int) ((double) pdfYslW * yslBarcodeHeight / yslBarcodeWidth);
					
					float pdfXStart = (xEnd - pdfYslW) / 2;
					float pdfYStart = (yStart - 7) - pdfYslH;

					cts.drawImage(aciBarcodeImage, pdfXStart, pdfYStart, pdfYslW, pdfYslH);
					
					
					yStart = pdfYStart - 13;
					
					cts.beginText();
					cts.newLineAtOffset(xStart + 40, yStart);
					cts.setFont(NanumGothic, fontSize);
					cts.showText(aciNo + " ▽");
					cts.endText();
					
					int pdfAciW = 180;
					int pdfAciH = (int) ((double) pdfAciW * aciBarcodeHeight / aciBarcodeWidth);
				
					yStart = yStart - (pdfAciH + 5);
					
					pdfXStart = (xEnd - pdfAciW) / 2;
					
					cts.drawImage(aciBarcodeImage, pdfXStart, yStart, pdfAciW, pdfAciH);
					
					xStart = 5;
					yStart = yStart - 3;
					xEnd = (pageWidth - 5);
					
					cts.drawLine(xStart, yStart, xEnd, yStart);
					
					
					
					
					cts.close();
					totalPage++;


					File barcodefile = new File(yslBarcodePath);
					if (barcodefile.exists()) {
						barcodefile.delete();
					}

					File barcodefile2 = new File(aciBarcodePath);
					if (barcodefile2.exists()) {
						barcodefile2.delete();
					}
				}
				
				
				if (orderType.toLowerCase().equals("y")) {
					totalPage = totalPage;
					
					String hawbNo = pdfPrintInfo.getHawbNo();
					String orderNo = pdfPrintInfo.getOrderNo();
					String itemCnt = String.valueOf(pdfPrintInfo.getItemCnt());
					String totalItemCnt = String.valueOf(pdfPrintInfo.getTotalCnt()); 
					
					PDRectangle pageStandard2 = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage2 = new PDPage(pageStandard2);
					doc.addPage(blankPage2);
					PDPage page2 = doc.getPage(totalPage);
					PDPageContentStream cts = new PDPageContentStream(doc, page2);
					
					cts.drawLine(10, 420, (pageStandard2.getWidth()) - 10, 420);
					cts.drawLine(10, 370, (pageStandard2.getWidth()) - 10, 370);
					cts.drawLine(10, 420, 10, 370);
					cts.drawLine((pageStandard2.getWidth()) - 10, 420, (pageStandard2.getWidth()) - 10, 370);
					cts.drawLine((pageStandard2.getWidth()) - 70, 420, (pageStandard2.getWidth()) - 70, 370);
					cts.drawLine((pageStandard2.getWidth()) - 70, 400, (pageStandard2.getWidth()) - 10, 400);
					
					
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
					cts.newLineAtOffset((pageStandard2.getWidth()) - 63.5f, 407);
					cts.showText("총 내품수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 12);
					cts.newLineAtOffset((pageStandard2.getWidth()) - 45, 383.5f);
					cts.showText(totalItemCnt);
					cts.endText();
					
					cts.drawLine(10, 370, 10, 350);
					cts.drawLine((pageStandard2.getWidth()) - 10, 370, (pageStandard2.getWidth()) - 10, 350);
					cts.drawLine(10, 350, (pageStandard2.getWidth()) - 10, 350);
					
					cts.drawLine((pageStandard2.getWidth()) - 135, 370, (pageStandard2.getWidth()) - 135, 350);
					cts.drawLine((pageStandard2.getWidth()) - 90, 370, (pageStandard2.getWidth()) - 90, 350);
					cts.drawLine((pageStandard2.getWidth()) - 55, 370, (pageStandard2.getWidth()) - 55, 350);
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset(15, 355.5f);
					cts.showText("내품명");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard2.getWidth()) - 130, 355.5f);
					cts.showText("사입코드");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard2.getWidth()) - 82, 355.5f);
					cts.showText("수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard2.getWidth()) - 45, 355.5f);
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
						cts.newLineAtOffset((pageStandard2.getWidth()) - 132, startLine2 - 3);
						cts.showText(packingInfo.get(items).get("takeInCode").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard2.getWidth()) - 79, startLine2 - 3);
						cts.showText(packingInfo.get(items).get("itemCnt").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard2.getWidth()) - 47, startLine2 - 3);
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
					
					cts.drawLine(10, 350, (pageStandard2.getWidth()) - 10, 350);
					cts.drawLine(10, 355.5f, 10, endLine3+6.5f);
					cts.drawLine((pageStandard2.getWidth()) - 10, 355.5f, (pageStandard2.getWidth()) - 10, endLine3+6.5f);
					cts.drawLine(10, endLine3+6.5f, (pageStandard2.getWidth()) - 10, endLine3+6.5f);
					
					cts.close();
					
					totalPage++;
				}
				
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}


		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		

		
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void ocsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception {
		// TODO Auto-generated method stub
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;
			for (int i = 0; i < orderNnoList.size(); i++) {

				pdfPrintInfo = mapper.selectOcsInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();

				// 페이지 추가
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					// PDRectangle pageStandard = PDRectangle.LETTER;
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					// new PDRectangle(180*perMM, 130*perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createNW7("A" + pdfPrintInfo.getHawbNo() + "A");

					// barcodes.setSize(100, 100);
					barcodes.setBarHeight(500);
					barcodes.setBarWidth(100);

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					int fontSize = 12; // Or whatever font size you want.
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					// barcode
					contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 18 * perMM), 150f, 35f);

					// barcode under line text
					float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
					drawText(pdfPrintInfo.getHawbNo(), ARIAL, 9, (100 * perMM - 58 * perMM + 75 - (titleWidth / 2)),
							(152 * perMM - 18 * perMM - 4 * perMM), contentStream);
					contentStream.setLineWidth(0);

					// float xStart, float yStart, float xEnd, float yEnd

					// 4*6 PDRectangle Line
					contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 149 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 3 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
					contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
							(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

					// 1st Vertical Line
					contentStream.drawLine(40 * perMM, 149 * perMM, 40 * perMM, 129 * perMM);
					// 2nd Vertical Line
					contentStream.drawLine(22 * perMM, 149 * perMM, 22 * perMM, 129 * perMM);
					// 1st horizontal Line
					contentStream.drawLine(3 * perMM, 144 * perMM, 40 * perMM, 144 * perMM);
					// 2nd horizontal Line
					contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);
					drawText("Piece(s)", ARIAL, 7, 5 * perMM, 146 * perMM, contentStream);
					fontSize = 20;

					if (pdfPrintInfo.getBoxCnt().equals("1")) {
						titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 10 * perMM, 135 * perMM, contentStream);
					} else {
						titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
						drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 10 * perMM, 135 * perMM,
								contentStream);
					}

					fontSize = 17;
					drawText("Weight", ARIAL, 7, 24 * perMM, 146 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 135 * perMM, contentStream);
					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("LB") / 1000 * fontSize;
					drawText("LB", ARIAL, fontSize, 34 * perMM, 132 * perMM, contentStream);

					// 3rd horizontal Line
					contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

					// 3rd Vertical Line
					contentStream.drawLine(30 * perMM, 129 * perMM, 30 * perMM, 109 * perMM);
					// 4th horizontal Line
					contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
					// Duty Terms.
					fontSize = 7;
					drawText("Duty Terms.", ARIAL, fontSize, 5 * perMM, 125.5f * perMM, contentStream);
					fontSize = 20;
					titleWidth = ARIAL.getStringWidth("DDP") / 1000 * fontSize;
					drawText("DDP", ARIAL, fontSize, 8 * perMM, 113 * perMM, contentStream);
					// Order No.
					fontSize = 7;
					drawText("Order No.", ARIAL, fontSize, 32 * perMM, 125.5f * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 32 * perMM, 113 * perMM, contentStream);

					// Shipper
					// 5th horizontal Line
					contentStream.drawLine(3 * perMM, 106 * perMM, (pageStandard.getWidth()) - 3 * perMM, 106 * perMM);
					fontSize = 9;
					drawText("Shipper", ARIAL, fontSize, 5 * perMM, 102 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 100 * perMM, (pageStandard.getWidth()) - 3 * perMM, 100 * perMM);

					fontSize = 7;
					drawText("Company Name : ", ARIAL, fontSize, 5 * perMM, 96 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Company Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText("AIR COURIERS INTERNATIONAL INC", ARIAL, fontSize, 5 * perMM + titleWidth, 96 * perMM,
							contentStream);
//					fontSize = 7;
//					drawText("Section",ARIAL, fontSize, 10, pageStandard.getHeight()-110,contentStream);

					fontSize = 7;
					drawText("Contract Name : ", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Contract Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText("HUGH KIM", ARIAL, fontSize, 5 * perMM + titleWidth, 92 * perMM, contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 88 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawText("UNITED STATES", ARIAL, fontSize, 5 * perMM + titleWidth, 88 * perMM, contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 5 * perMM, 84 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
					// pageStandard.getHeight()-151,contentStream);
					drawText("CA", ARIAL, fontSize, 5 * perMM + titleWidth, 84 * perMM, contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 80 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawText("CERRITOS", ARIAL, fontSize, 5 * perMM + titleWidth, 80 * perMM, contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Address : ") / 1000 * fontSize;
					drawText("Address : ", ARIAL, fontSize, 5 * perMM, 76 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine("14056 ARTESIA BLVD", ARIAL, fontSize, 5 * perMM + titleWidth, 76 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 76 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine("90703", ARIAL, fontSize, 60 * perMM + titleWidth, 76 * perMM, contentStream, 2);

					fontSize = 7;
					drawTextLine("Address2 : ", ARIAL, fontSize, 5 * perMM, 72 * perMM, contentStream, 2);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
//					drawText(pdfPrintInfo.getShipperAddrDetail(), ARIAL, fontSize, 40+titleWidth+10, 280*perMM-151,contentStream);

					fontSize = 7;
					drawText("Tel.", ARIAL, fontSize, 5 * perMM, 68 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawText("310-965-0463", ARIAL, fontSize, 5 * perMM + titleWidth, 68 * perMM, contentStream);

					// receiver
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 64 * perMM, (pageStandard.getWidth()) - 3 * perMM, 64 * perMM);
					fontSize = 9;
					drawText("Receiver", ARIAL, fontSize, 5 * perMM, 60 * perMM, contentStream);
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 58 * perMM, (pageStandard.getWidth()) - 3 * perMM, 58 * perMM);

					fontSize = 7;
					drawText("Company Name : ", ARIAL, fontSize, 5 * perMM, 54 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Company Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 54 * perMM,
							contentStream);

					fontSize = 7;
					drawText("Company Name(JP) : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Company Name(JP) : ") / 1000 * fontSize;
					fontSize = 9;
					String nativeCneeName = pdfPrintInfo.getNativeCneeName().replaceAll("\\t", "");
					drawText(nativeCneeName, ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM, contentStream);

//					fontSize = 7;
//					drawText("Company Name(JP) : ",ARIAL, fontSize, 5*perMM, 50*perMM,contentStream);
//					titleWidth = ARIAL.getStringWidth("Company Name(JP) : ") / 1000 * fontSize;
//					fontSize = 9;
//					drawText("石井玲央奈",ARIAL, fontSize, 5*perMM+titleWidth, 50*perMM,contentStream);

					fontSize = 7;
					drawText("Contract Name : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Contract Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(".", ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM, contentStream);

					fontSize = 7;
					drawText("Tel.", ARIAL, fontSize, 60 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Tel.  ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 60 * perMM + titleWidth, 46 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					drawText("State : ", ARIAL, fontSize, 60 * perMM, 42 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 60 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 38 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 38 * perMM,
							contentStream, 2);

//					fontSize = 7;
//					drawText("Address : ",ARIAL, fontSize, 5*perMM, 34*perMM,contentStream);
//					titleWidth = ARIAL.getStringWidth("Address : ") / 1000 * fontSize;
//					fontSize = 6;
//					drawTextLine(pdfPrintInfo.getCneeAddr()+" "+pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize, 7*perMM, 31*perMM, contentStream, 2);

					fontSize = 7;
					drawText("Address : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Address : ") / 1000 * fontSize;
					fontSize = 7;
					drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
							7 * perMM, 31 * perMM, contentStream, 3);

//					fontSize = 7;
//					drawText("Address(JP) : ",ARIAL, fontSize, 5*perMM, 23*perMM,contentStream);
//					titleWidth = ARIAL.getStringWidth("Address(JP) : ") / 1000 * fontSize;
//					fontSize = 6;
//					drawTextLine(pdfPrintInfo.getNativeCneeAddr()+" "+pdfPrintInfo.getNativeCneeAddrDetail(), ARIAL, fontSize, 5*perMM+titleWidth, 20*perMM, contentStream, 2);

					fontSize = 7;
					drawText("Address(JP) : ", ARIAL, fontSize, 5 * perMM, 20 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Address(JP) : ") / 1000 * fontSize;
					fontSize = 7;
					String nativeCneeAddr = pdfPrintInfo.getNativeCneeAddr() + " "
							+ pdfPrintInfo.getNativeCneeAddrDetail();
					nativeCneeAddr = nativeCneeAddr.replaceAll("−", " ");
					//nativeCneeAddr = nativeCneeAddr.replaceAll("\\t", "");
					drawTextLine(nativeCneeAddr, MsGothic, fontSize, 7 * perMM, 17 * perMM, contentStream, 3);

					contentStream.close();
					totalPage++;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}
	}

	@Override
	public void krPostPdfLegacy(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
		// TODO Auto-generated method stub
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String barcodePath2 = pdfPath2 + userId + "2.JPEG";
		String qrPath = pdfPath2 + userId + "qrCode.JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
//		String markPath2 = pdfPath+"/mark/test2.png";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			String terminalNo = "";
			String terminalName = "";
			String centerNo = "";
			String centerName = "";
			String deliveryTeamNo = "";
			String deliveryPointName = "";

			for (int i = 0; i < orderNnoList.size(); i++) {

				pdfPrintInfo = mapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();

				HashMap<String, Object> itemInfos = mapper.selectItemInfos(pdfPrintInfo.getNno());
				// 페이지 추가
				try {
					String addr = URLEncoder.encode(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(),
							"UTF-8");
					// String addr =pdfPrintInfo.getCneeAddr()+" "+pdfPrintInfo.getCneeAddrDetail();
					String obj = "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip="
							+ pdfPrintInfo.getCneeZip() + "&addr=" + addr + "&mdiv=1";

					// String obj =
					// "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip=22376&addr=인천광역시
					// 중구 영종대로252번길 12 운서동 영종LH2단지 아파트 205동1310호&mdiv=1";
					// If response status == 200
					DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
					Document doc2 = dBuilder.parse(obj);
					doc2.getDocumentElement().normalize();
					NodeList resultNodeList = doc2.getElementsByTagName("result");

					Node nNode = resultNodeList.item(0);
					Element eElement = (Element) nNode;
					terminalNo = getTagValue("delivAreaCd", eElement).substring(0, 2);
					terminalName = getTagValue("arrCnpoNm", eElement);
					centerNo = getTagValue("delivAreaCd", eElement).substring(2, 5);
					centerName = getTagValue("delivPoNm", eElement);
					deliveryTeamNo = getTagValue("delivAreaCd", eElement).substring(5, 7);
					deliveryPointName = getTagValue("delivAreaCd", eElement).substring(7, 9);

				} catch (Exception e) {
					throw e;
				}

				PDRectangle pageStandard = new PDRectangle(168 * perMM, 107 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(i);

				// 컨텐츠 스트림 열기 바코드 1
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getCneeZip());

				barcodes.setSize(120, 800);
				barcodes.setBarHeight(1);
				barcodes.setBarWidth(1);

				File barcodefile = new File(barcodePath);
				BufferedImage source = BarcodeImageHandler.getImage(barcodes);

				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				// 바코드 2
				net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

				barcodes2.setSize(300, 800);
				barcodes2.setBarHeight(1);
				barcodes2.setBarWidth(1);

				File barcodefile2 = new File(barcodePath2);

				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

				File markFile = new File(markPath);
				PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
				contentStream.drawImage(markImage, 35 * perMM, pageStandard.getHeight() - 35f, 60f, 25f);

				// qrcode 생성
				File qrCodeFile = new File(qrPath);
				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = qrCodeWriter.encode(pdfPrintInfo.getHawbNo(), BarcodeFormat.QR_CODE, 200, 200); // 텍스트,
																														// 바코드
																														// 포맷,가로,세로

				MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF); // 진한색, 연한색
				BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

				ImageIO.write(qrImage, "png", qrCodeFile); // temp 위치에 qr이 이미지 생성됨.
				// InputStream is = new FileInputStream(temp.getAbsolutePath()); // 인풋 스트림으로
				// 변환(향후 S3로 업로드하기위한 작업)

				// 배경이미지 로드

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(qrCodeFile, doc);

				PDImageXObject pdImage3 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

				int yWidth = 0;

				int fontSize = 10; // Or whatever font size you want.

				/* 왼쪽 */
				fontSize = 12;
				yWidth += fontSize;
				float titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getShipperName()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getShipperName(), NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 19 * perMM, contentStream);

				fontSize = 11;
				yWidth += fontSize;
				String lineString = "Order No:" + pdfPrintInfo.getOrderNo();
				drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 19 * perMM, contentStream, 0);

				fontSize = 7;
				yWidth = fontSize;
				lineString = "자가사용 목적 통관물품 상용판매시 처벌 받을 수 있습니다";
				drawText(lineString, NanumGothic, fontSize, 4 * perMM, pageStandard.getHeight() - yWidth - 38 * perMM,
						contentStream);

				yWidth += fontSize;
				lineString = itemInfos.get("itemDetail").toString();
				if (!itemInfos.get("itemTotalCnt").toString().equals("1")) {
					lineString = lineString + " others " + itemInfos.get("itemTotalCnt").toString();
				}
				drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 38 * perMM, contentStream, 0);
				/* 왼쪽 끝 */

				/* 오른쪽 */
				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(terminalNo) / 1000 * fontSize;
				drawText(terminalNo, NanumGothic, fontSize, 63 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
						contentStream);

				fontSize = 8;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(terminalName) / 1000 * fontSize;
				drawText(terminalName, NanumGothic, fontSize, 71 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
						contentStream);

				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(centerNo) / 1000 * fontSize;
				drawText(centerNo, NanumGothic, fontSize, 79 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
						contentStream);

				fontSize = 8;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(centerName) / 1000 * fontSize;
				drawText(centerName, NanumGothic, fontSize, 88 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
						contentStream);

				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(deliveryTeamNo) / 1000 * fontSize;
				drawText(deliveryTeamNo, NanumGothic, fontSize, 97 * perMM,
						pageStandard.getHeight() - yWidth - 8 * perMM, contentStream);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(deliveryPointName) / 1000 * fontSize;
				drawText(deliveryPointName, NanumGothic, fontSize, 105 * perMM,
						pageStandard.getHeight() - 15 - 8 * perMM, contentStream);

				contentStream.drawImage(pdImage2, 115 * perMM, pageStandard.getHeight() - 15 - 12 * perMM, 40f, 40f);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeZip()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeZip(), NanumGothic, fontSize, 72 * perMM,
						pageStandard.getHeight() - 15 - 22 * perMM, contentStream);

				contentStream.drawImage(pdImage, 95 * perMM, pageStandard.getHeight() - 15 - 27 * perMM, 100f, 30f);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeName()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeName(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 35 * perMM, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeTel()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeTel(), NanumGothic, fontSize, 120 * perMM,
						pageStandard.getHeight() - 23 - 33 * perMM, contentStream);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddr()) / 1000 * fontSize;
				drawTextLine(pdfPrintInfo.getCneeAddr(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 40 * perMM, contentStream, 0);

				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddrDetail()) / 1000 * fontSize;
				drawTextLine(pdfPrintInfo.getCneeAddrDetail(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 50 * perMM, contentStream, 0);

				contentStream.drawImage(pdImage3, 68 * perMM, pageStandard.getHeight() - 23 - 80 * perMM, 200f, 30f);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo(), NanumGothic, fontSize, 88 * perMM,
						pageStandard.getHeight() - 23 - 85 * perMM, contentStream);

				/* 오른쪽 끝 */
//				fontSize = 12;
//				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
//				drawText(pdfPrintInfo.getHawbNo(),NanumGothic, fontSize, (80*perMM - titleWidth) / 2, 17,contentStream);

				contentStream.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		File barcodefile2 = new File(barcodePath2);
		File barcodefile3 = new File(qrPath);

		if (barcodefile.exists()) {
			barcodefile.delete();
		}

		if (barcodefile2.exists()) {
			barcodefile2.delete();
		}

		if (barcodefile3.exists()) {
			barcodefile3.delete();
		}

	}

	@Override
	public void efsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception {
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);

			for (int i = 0; i < orderNnoList.size(); i++) {

				pdfPrintInfo = mapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				// 페이지 추가
				PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(i);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				int fontSize = 10; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")")
						/ 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 31, 200f, 35f);

				drawText(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
						(80 * perMM - titleWidth) / 2, 30 * perMM - 11, contentStream);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 17,
						contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo2(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 7,
						contentStream);

//				fontSize = 10;
//				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName()) / 1000 * fontSize;
//				drawText(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName(),NanumGothic, 10, (80*perMM - titleWidth) / 2, 117,contentStream);
//				
//				fontSize = 11;
//				titleWidth = NanumGothic.getStringWidth(tempResultVO.getRackCode()) / 1000 * fontSize;
//				drawText(tempResultVO.getRackCode(),NanumGothic, 11, (80*perMM - titleWidth) / 2, 102,contentStream);
//				
//				fontSize = 12;
//				String itemDetailSub = tempResultVO.getItemDetail();
//				if(NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize > 80*perMM) {
//					itemDetailSub = itemDetailSub.substring(0,30);
//					itemDetailSub = itemDetailSub+"...";
//				}
//				
//				
//				titleWidth = NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize;
//				drawText(itemDetailSub,NanumGothic, 12, (80*perMM - titleWidth) / 2, 90,contentStream);
//				
//				fontSize = 13;
//				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getStockNo()) / 1000 * fontSize;
//				drawText(tempStockVoList.get(stockIndex).getStockNo(),NanumGothic, 13, (80*perMM - titleWidth) / 2, 25,contentStream);
				contentStream.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void etcsPdfLegacy(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList)
			throws Exception {
		// TODO Auto-generated method stub
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);

			for (int i = 0; i < orderNnoList.size(); i++) {

				pdfPrintInfo = mapper.selectTempInfoETCS(orderNnoList.get(i));
				pdfPrintInfo.dncryptData();
				// 페이지 추가
				PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(i);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
						.createCode39(pdfPrintInfo.getTrkNo().toUpperCase(), true);

				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				int fontSize = 10; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth("입고 (" + pdfPrintInfo.getTrkCom() + ")") / 1000
						* fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 31, 200f, 35f);

				drawText("입고 (" + pdfPrintInfo.getTrkCom() + ")", NanumGothic, fontSize, (80 * perMM - titleWidth) / 2,
						30 * perMM - 11, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getTrkNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getTrkNo(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 19,
						contentStream);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderText()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getOrderText(), NanumGothic, fontSize, (80 * perMM - titleWidth) / 2, 6,
						contentStream);
				contentStream.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//			PrinterJob job = PrinterJob.getPrinterJob();
//			job.setPageable(new PDFPageable(doc));
//			job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	@Override
	public void downExcelData(HashMap<String, Object> excelParameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String swh = "USPS";
		if (swh.equals("DHL")) {

		} else if (swh.equals("USPS")) {
			// TODO Auto-generated method stub
			LocalDate currentDate = LocalDate.now();
			String savePath = realFilePath + "excel/expLicence/";
			String filename = "ORDER_DATA_" + currentDate + ".xlsx";

			Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
			Sheet sheet1 = xlsxWb.createSheet("Sheet1");

			// HEAD 스타일 설정 START
			CellStyle cellStyleHeader = xlsxWb.createCellStyle();
			cellStyleHeader.setWrapText(false);
			cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			// HEAD 스타일 설정 END

			// ROW-CELL 선언 START
			Row row = null;
			Cell cell = null;
			int rowCnt = 0;
			int celCnt = 0;
			// ROW-CELL 선언 END

			// HEADER 생성 START 작업중입니단
			row = sheet1.createRow(rowCnt);

			cell = row.createCell(celCnt);
			cell.setCellValue("No.");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("송장번호");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("주문번호");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("주문번호2");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("주문날짜");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("출발도시 코드");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("도착국가");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("수취인");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("수취인2");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("수취인 Tel.");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("우편번호");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("수취인 주소");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("수취인 주소2");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("Box 개수");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("실무게");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("상품번호");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("상품명");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("상품개수");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("단가");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			cell = row.createCell(celCnt);
			cell.setCellValue("금액");
			cell.setCellStyle(cellStyleHeader);
			celCnt++;

			rowCnt++;
			row = sheet1.createRow(rowCnt);

			// HEADER 생성 END
			ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
			ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
			LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>>();
			try {
				if (request.getParameter("urlType").equals("allAbout")) {
					selectShpngList = mapper.selectRegistedOrderList(excelParameter);
				} else if (request.getParameter("urlType").equals("before"))
					selectShpngList = mapper.selectSendBeforeOrderForExcel(excelParameter);
				else if (request.getParameter("urlType").equals("after"))
					selectShpngList = mapper.selectSendAfterOrderForExcel(excelParameter);

				for (int index = 0; index < selectShpngList.size(); index++) {
					selectShpngList.get(index).dncryptData();
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Exception", e);
			}
			tempSelectShpngList.add(selectShpngList.get(0));
			for (int roop = 1; roop < selectShpngList.size(); roop++) {
				if (selectShpngList.get(roop).getSubNo().equals("1")) {
					returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
					tempSelectShpngList = new ArrayList<ShpngListVO>();
					tempSelectShpngList.add(selectShpngList.get(roop));
				} else {
					tempSelectShpngList.add(selectShpngList.get(roop));
				}
			}
			returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);

			for (String key : returnMap.keySet()) {
				for (int index = 0; index < returnMap.get(key).size(); index++) {
					ArrayList<ShpngListVO> shpngListDetail = new ArrayList<ShpngListVO>();
					shpngListDetail = returnMap.get(key);
					if (shpngListDetail.get(index).getSubNo().equals("1")) {
						celCnt = 0;
						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getRownum());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getHawbNo());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getOrderNo());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getWhReqMsg());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getOrderDate());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getOrgStation());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getDstnNationName());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getCneeName());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getNativeCneeName());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getCneeTel());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getCneeZip());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getCneeAddr() + " "
								+ shpngListDetail.get(index).getCneeAddrDetail());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getNativeCneeAddr() + " "
								+ shpngListDetail.get(index).getNativeCneeAddrDetail());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getBoxCnt());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;

						cell = row.createCell(celCnt);
						cell.setCellValue(shpngListDetail.get(index).getUserWta());
						cell.setCellStyle(cellStyleHeader);
						celCnt++;
					}
					celCnt = 15;
					cell = row.createCell(celCnt);
					cell.setCellValue(shpngListDetail.get(index).getSubNo());
					cell.setCellStyle(cellStyleHeader);
					celCnt++;

					cell = row.createCell(celCnt);
					cell.setCellValue(shpngListDetail.get(index).getItemDetail());
					cell.setCellStyle(cellStyleHeader);
					celCnt++;

					cell = row.createCell(celCnt);
					cell.setCellValue(shpngListDetail.get(index).getItemCnt());
					cell.setCellStyle(cellStyleHeader);
					celCnt++;

					cell = row.createCell(celCnt);
					cell.setCellValue(shpngListDetail.get(index).getUnitValue());
					cell.setCellStyle(cellStyleHeader);
					celCnt++;

					cell = row.createCell(celCnt);
					cell.setCellValue(shpngListDetail.get(index).getItemValue());
					cell.setCellStyle(cellStyleHeader);
					celCnt++;

					rowCnt++;
					row = sheet1.createRow(rowCnt);
				}
			}
			try {
				try {
					File xlsFile = new File(savePath + filename);
					FileOutputStream fileOut = new FileOutputStream(xlsFile);
					xlsxWb.write(fileOut);
				} catch (FileNotFoundException e) {
					logger.error("Exception", e);
				} catch (IOException e) {
					logger.error("Exception", e);
				}

				InputStream in = null;
				OutputStream os = null;
				File file = null;
				boolean skip = false;
				String client = "";

				// 파일을 읽어 스트림에 담기
				try {
					file = new File(savePath, filename);
					in = new FileInputStream(file);
				} catch (FileNotFoundException fe) {
					skip = true;
				}

				client = request.getHeader("User-Agent");

				// 파일 다운로드 헤더 지정
				response.reset();
				response.setContentType("ms-vnd/excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + filename);

				if (!skip) {
					// IE
					if (client.indexOf("MSIE") != -1) {
						response.setHeader("Content-Disposition",
								"attachment; filename=" + new String(filename.getBytes("KSC5601"), "ISO8859_1"));
					} else {
						// 한글 파일명 처리
						filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
						response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
					}
					response.setHeader("Content-Length", "" + file.length());
					os = response.getOutputStream();
					byte b[] = new byte[(int) file.length()];
					int leng = 0;
					while ((leng = in.read(b)) > 0) {
						os.write(b, 0, leng);
					}
				} else {
					response.setContentType("text/html;charset=UTF-8");
				}

				os.close();
				in.close();
				file.delete();
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		}

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWebHookInfo(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectWebHookInfo(userId);
	}

	@Override
	public String selectWebHookInfoOne(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectWebHookInfoOne(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectBlList() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectBlList();
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserTrkComBlType(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTrkComBlType(userId);
	}

	@Override
	public void printCommercialPdf(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
		// TODO Auto-generated method stub
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

		PDType0Font ARIAL = PDType0Font.load(doc, english);
		PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);

		float perMM = 1 / (10 * 2.54f) * 72;

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int currenctPage = 0;
			for (int i = 0; i < orderNnoList.size(); i++) {
				CommercialVO commercialInfo = new CommercialVO();
				commercialInfo = mapper.selectCommercialInfo(orderNnoList.get(i));
				commercialInfo.dncryptData();
				// 페이지 추가
				PDRectangle pageStandard = new PDRectangle();
				pageStandard = pageStandard.A4;
				PDPage blankPage = new PDPage(pageStandard);
				float titleWidth = 0;
				int fontSize = 0;
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(currenctPage);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				contentStream.drawLine(3 * perMM, 5 * perMM, (pageStandard.getWidth()) - 3 * perMM, 5 * perMM);
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 26 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 26 * perMM);
				contentStream.drawLine(3 * perMM, 5 * perMM, 3 * perMM, (pageStandard.getHeight()) - 26 * perMM);
				contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 5 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 26 * perMM);
				// 두번째 가로 줄
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 36 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 36 * perMM);
				// 회색 박스
				contentStream.setNonStrokingColor(Color.gray);
				contentStream.fillRect((float) (3 * perMM + 0.5), (pageStandard.getHeight()) - 36 * perMM,
						(float) (pageStandard.getWidth() - 6 * perMM - 1.5), -5 * perMM);
				// 세로줄
				contentStream.drawLine(pageStandard.getWidth() / 2, (pageStandard.getHeight()) - 41 * perMM,
						pageStandard.getWidth() / 2, (pageStandard.getHeight() / 2) - 16 * perMM);
				// 세번째 가로 줄
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 80 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 80 * perMM);
				// 왼쪽 가로줄
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 120 * perMM,
						(pageStandard.getWidth() / 2), (pageStandard.getHeight()) - 120 * perMM);
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 135 * perMM,
						(pageStandard.getWidth() / 2), (pageStandard.getHeight()) - 135 * perMM);
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 150 * perMM,
						(pageStandard.getWidth() / 2), (pageStandard.getHeight()) - 150 * perMM);
				// 왼쪽 세로줄
				contentStream.drawLine((pageStandard.getWidth() / 4) + 3 * perMM,
						(pageStandard.getHeight()) - 135 * perMM, (pageStandard.getWidth() / 4) + 3 * perMM,
						(pageStandard.getHeight() / 2) - 16 * perMM);
				// 오른쪽 가로줄
				contentStream.drawLine((pageStandard.getWidth() / 2), (pageStandard.getHeight()) - 54 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 54 * perMM);
				contentStream.drawLine((pageStandard.getWidth() / 2), (pageStandard.getHeight()) - 67 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 67 * perMM);
				// 네번째 가로줄
				contentStream.drawLine(3 * perMM, (pageStandard.getHeight() / 2) - 16 * perMM,
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight() / 2) - 16 * perMM);

				// 박스

				// Header
				fontSize = 22;
				contentStream.setNonStrokingColor(Color.black);
				titleWidth = NanumGothic.getStringWidth("COMMERCIAL INVOICE") / 1000 * fontSize;
				drawText("COMMERCIAL INVOICE", ARIALBOLD, fontSize, (pageStandard.getWidth() - titleWidth) / 2,
						(pageStandard.getHeight()) - 18 * perMM, contentStream);

				// Hawb No Left
				fontSize = 15;
				drawText(commercialInfo.getHawbNo(), ARIAL, fontSize, 4 * perMM,
						(pageStandard.getHeight()) - 32 * perMM, contentStream);

				// shipper/export
				fontSize = 10;
				float drawTextY = (pageStandard.getHeight()) - 45 * perMM;
				drawText("① Shipper / Export", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);

				drawTextY = (pageStandard.getHeight()) - 52 * perMM;
				drawTextY = drawTextLineLength(commercialInfo.getComEName(), ARIAL, fontSize, 4 * perMM, drawTextY,
						contentStream, 23000);
				drawTextY = drawTextLineLength(
						commercialInfo.getUserEAddrDetail() + " " + commercialInfo.getUserEAddr() + " "
								+ commercialInfo.getShipperCntry(),
						ARIAL, fontSize, 4 * perMM, drawTextY, contentStream, 27500);
				drawTextY = drawTextLineLength("TEL : " + commercialInfo.getUserTel(), ARIAL, fontSize, 4 * perMM,
						drawTextY, contentStream, 23000);

				// For Account & Risk of Messrs
				fontSize = 10;
				drawTextY = (pageStandard.getHeight()) - 84 * perMM;
				drawText("② For Account & Risk of Messrs", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawTextY = drawTextLineLength(commercialInfo.getCneeName(), ARIAL, fontSize, 4 * perMM, drawTextY,
						contentStream, 23000);
				drawTextY = drawTextLineLength(
						commercialInfo.getCneeAddrDetail() + " " + commercialInfo.getCneeAddr() + " "
								+ commercialInfo.getDstnNation(),
						ARIAL, fontSize, 4 * perMM, drawTextY, contentStream, 27500);
				drawTextY = drawTextLineLength("TEL : " + commercialInfo.getCneeTel(), ARIAL, fontSize, 4 * perMM,
						drawTextY, contentStream, 23000);

				// Notify Party
				drawTextY = (pageStandard.getHeight()) - 124 * perMM;
				drawText("③ Notify Party", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				/// Port of loading
				drawTextY = (pageStandard.getHeight()) - 139 * perMM;
				drawText("④ Port of loading", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawTextY = drawTextLineLength(commercialInfo.getShipperCntry(), ARIAL, fontSize, 4 * perMM, drawTextY,
						contentStream, 11500);
				// Final Destination
				drawTextY = (pageStandard.getHeight()) - 139 * perMM;
				drawText("⑤ Final Destination", ARIAL, fontSize, 57 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawTextY = drawTextLineLength(commercialInfo.getDstnNation(), ARIAL, fontSize, 57 * perMM, drawTextY,
						contentStream, 11500);

				// Carrier
				drawTextY = (pageStandard.getHeight()) - 154 * perMM;
				drawText("⑥ Carrier", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawTextY = drawTextLineLength("", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream, 11500);

				// Sailing on or about
				drawTextY = (pageStandard.getHeight()) - 154 * perMM;
				drawText("⑦ Sailing on or about", ARIAL, fontSize, 57 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawTextY = drawTextLineLength(commercialInfo.getDepDate(), ARIAL, fontSize, 57 * perMM, drawTextY,
						contentStream, 11500);

				// No. & Date of Invoice
				drawTextY = (pageStandard.getHeight()) - 45 * perMM;
				drawText("⑧ No. & Date of Invoice", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				if (commercialInfo.getDepDate().equals("")) {
					String orderDate = mapper.selectUploadDate(commercialInfo.getHawbNo());
					drawText(orderDate, ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);					
				} else {
					drawText(commercialInfo.getDepDate(), ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);	
				}

				// No. & date of L/C
				drawTextY = (pageStandard.getHeight()) - 58 * perMM;
				drawText("⑨ No. & date of L/C", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);

				// L/C issuing bank
				drawTextY = (pageStandard.getHeight()) - 71 * perMM;
				drawText("⑩ L/C issuing bank", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);

				// Remarks:
				drawTextY = (pageStandard.getHeight()) - 84 * perMM;
				drawText("⑪ Remarks : ", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				if (!commercialInfo.getPayment().equals("")) {
					drawText("Payment:" + commercialInfo.getPayment(), ARIAL, fontSize, 106 * perMM, drawTextY,
							contentStream);
					drawTextY = drawTextY - 7 * perMM;
				}

				if (!commercialInfo.getBuySite().equals("")) {
					drawTextY = drawTextLineLength("Buy Site URL:" + commercialInfo.getBuySite(), ARIAL, fontSize,
							106 * perMM, drawTextY, contentStream, 23000);
					drawTextY = drawTextY - 7 * perMM;
				}

				// Marks and Number of PKGS Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑫ Marks and", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Number of PKGS ", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);

				// Description of goods Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				titleWidth = NanumGothic.getStringWidth("⑬ Description of goods") / 1000 * fontSize;
				drawText("⑬ Description of goods ", ARIAL, fontSize, 34 * perMM + (93 * perMM - titleWidth) / 2,
						drawTextY, contentStream);
				// Quantity/Unit Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑭ Quantity/", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Unit ", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
				// Unit-price Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;

				drawText("⑮ Unit price", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);

				// Amount(단위)
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("Amount", ARIAL, fontSize, 187 * perMM, drawTextY, contentStream);
				// nno = orderNnoList.get(i)
				ArrayList<CommercialItemVO> commercialItem = new ArrayList<CommercialItemVO>();
				drawTextY = (pageStandard.getHeight()) - 179 * perMM;
				float pivotTextY = (pageStandard.getHeight()) - 179 * perMM;
				float targetTextY = (pageStandard.getHeight()) - 179 * perMM;
				commercialItem = mapper.selectCommercialItem(orderNnoList.get(i));
				String chgCurreny = commercialItem.get(0).getChgCurrency();
				double totalAmount = 0;
				float startY = (pageStandard.getHeight()) - 170 * perMM;
				int pageNum = 1;
				for (int j = 0; j < commercialItem.size(); j++) {
					String itemTitle = commercialItem.get(j).getItemDetail();

					titleWidth = ARIAL.getStringWidth(itemTitle) / 1000 * fontSize;
					if (230.00 < titleWidth) {
						titleWidth = 230;
					}
					targetTextY = drawTextLineLength(itemTitle, ARIAL, fontSize,
							34 * perMM + (93 * perMM - titleWidth) / 2, drawTextY, contentStream, 23000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

					targetTextY = drawTextLineLength(commercialItem.get(j).getItemCnt(), ARIAL, fontSize, 131 * perMM,
							drawTextY, contentStream, 10000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

					titleWidth = ARIAL.getStringWidth(
							commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency())
							/ 1000 * fontSize;
					if (60.00 < titleWidth) {
						titleWidth = 60;
					}
					targetTextY = drawTextLineLength(
							commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency(),
							ARIAL, fontSize, 155 * perMM + (26 * perMM - titleWidth) / 2, drawTextY, contentStream,
							6000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

					double amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
							* Double.parseDouble(commercialItem.get(j).getUnitValue());
					titleWidth = ARIAL.getStringWidth(amount + "   " + commercialItem.get(j).getChgCurrency()) / 1000
							* fontSize;
					if (60.00 < titleWidth) {
						titleWidth = 60;
					}
					targetTextY = drawTextLineLength(amount + "   " + commercialItem.get(j).getChgCurrency(), ARIAL,
							fontSize, 181 * perMM + (26 * perMM - titleWidth) / 2, drawTextY, contentStream, 6000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

					totalAmount += amount;
					drawTextY = pivotTextY - 2 * perMM;

					if (drawTextY < 100) {
						contentStream.setLineDashPattern(new float[] { 3, 2 }, 0);
						contentStream.drawLine(34 * perMM, startY, 34 * perMM, 5 * perMM);
						contentStream.drawLine(127 * perMM, startY, 127 * perMM, 5 * perMM);
						contentStream.drawLine(155 * perMM, startY, 155 * perMM, 5 * perMM);
						contentStream.drawLine(181 * perMM, startY, 181 * perMM, 5 * perMM);

						contentStream.close();
						pageStandard = new PDRectangle();
						pageStandard = pageStandard.A4;
						blankPage = new PDPage(pageStandard);
						currenctPage++;
						doc.addPage(blankPage);
						// 현재 페이지 설정
						page = doc.getPage(currenctPage);
						contentStream = new PDPageContentStream(doc, page);
						contentStream.drawLine(3 * perMM, 5 * perMM, (pageStandard.getWidth()) - 3 * perMM, 5 * perMM);
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 10 * perMM,
								(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 10 * perMM);
						contentStream.drawLine(3 * perMM, 5 * perMM, 3 * perMM,
								(pageStandard.getHeight()) - 10 * perMM);
						contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 5 * perMM,
								(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 10 * perMM);

						// Marks and Number of PKGS Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						drawText("⑫ Marks and", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Number of PKGS ", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);

						// Description of goods Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						titleWidth = NanumGothic.getStringWidth("⑬ Description of goods") / 1000 * fontSize;
						drawText("⑬ Description of goods ", ARIAL, fontSize, 34 * perMM + (93 * perMM - titleWidth) / 2,
								drawTextY, contentStream);
						// Quantity/Unit Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						drawText("⑭ Quantity/", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Unit ", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
						// Unit-price Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;

						drawText("⑮ Unit price", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);

						// Amount(단위)
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						drawText("Amount", ARIAL, fontSize, 187 * perMM, drawTextY, contentStream);

						startY = drawTextY;
						drawTextY = drawTextY - 10 * perMM;
						pivotTextY = drawTextY;

						pageNum++;
					}

				}

				contentStream.setLineDashPattern(new float[] { 3, 2 }, 0);
				contentStream.drawLine(34 * perMM, startY, 34 * perMM, 50 * perMM);
				contentStream.drawLine(127 * perMM, startY, 127 * perMM, 50 * perMM);
				contentStream.drawLine(155 * perMM, startY, 155 * perMM, 50 * perMM);
				contentStream.drawLine(181 * perMM, startY, 181 * perMM, 50 * perMM);

//				for(int j = 0; j < commercialItem.size(); j++) {
//					String itemTitle = commercialItem.get(j).getItemDetail();
//					
//					titleWidth = ARIAL.getStringWidth(itemTitle) / 1000 * fontSize;
//					if(230.00 < titleWidth) {
//						titleWidth = 230;
//					}
//					targetTextY = drawTextLineLength(itemTitle, ARIAL, fontSize, 34*perMM+(93*perMM-titleWidth) / 2, drawTextY,contentStream,23000);
//					if(targetTextY < pivotTextY) {
//						pivotTextY = targetTextY;
//					}
//					
//					targetTextY = drawTextLineLength(commercialItem.get(j).getItemCnt(), ARIAL, fontSize, 131*perMM, drawTextY,contentStream,10000);
//					if(targetTextY < pivotTextY) {
//						pivotTextY = targetTextY;
//					}
//					
//					titleWidth = ARIAL.getStringWidth(commercialItem.get(j).getUnitValue()+"   "+commercialItem.get(j).getChgCurrency()) / 1000 * fontSize;
//					if(60.00 < titleWidth) {
//						titleWidth = 60;
//					}
//					targetTextY = drawTextLineLength(commercialItem.get(j).getUnitValue()+"   "+commercialItem.get(j).getChgCurrency(), ARIAL, fontSize, 155*perMM+(26*perMM-titleWidth)/2, drawTextY,contentStream,6000);
//					if(targetTextY < pivotTextY) {
//						pivotTextY = targetTextY;
//					}
//					
//					double amount = Double.parseDouble(commercialItem.get(j).getItemCnt())*Double.parseDouble(commercialItem.get(j).getUnitValue());
//					titleWidth = ARIAL.getStringWidth(amount+"   "+commercialItem.get(j).getChgCurrency()) / 1000 * fontSize;
//					if(60.00 < titleWidth) {
//						titleWidth = 60;
//					}
//					targetTextY = drawTextLineLength(amount+"   "+commercialItem.get(j).getChgCurrency(), ARIAL, fontSize, 181*perMM+(26*perMM-titleWidth)/2, drawTextY,contentStream,6000);
//					if(targetTextY < pivotTextY) {
//						pivotTextY = targetTextY;
//					}
//					
//					totalAmount += amount;
//					drawTextY = pivotTextY-2*perMM;
//					if(drawTextY < 9.125993) {
//						doc.addPage(blankPage);
//				        // 현재 페이지 설정
//				        page = doc.getPage(currenctPage);
//				        contentStream = new PDPageContentStream(doc, page);
//				        contentStream.drawLine(3*perMM, 5*perMM, (pageStandard.getWidth())-3*perMM, 5*perMM);
//				        contentStream.drawLine(3*perMM, (pageStandard.getHeight())-26*perMM, (pageStandard.getWidth())-3*perMM, (pageStandard.getHeight())-26*perMM);
//				        contentStream.drawLine(3*perMM, 5*perMM, 3*perMM, (pageStandard.getHeight())-26*perMM);
//				        contentStream.drawLine((pageStandard.getWidth())-3*perMM, 5*perMM, (pageStandard.getWidth())-3*perMM, (pageStandard.getHeight())-26*perMM);
//				        
//					}
//					
//				}

				// 다섯번째 가로줄
				contentStream.drawLine(3 * perMM, 30 * perMM, (pageStandard.getWidth()) - 3 * perMM, 30 * perMM);
				// 회색 박스
				contentStream.setNonStrokingColor(Color.gray);
				contentStream.fillRect((float) (3 * perMM + 0.5), 5 * perMM + 1,
						(float) (pageStandard.getWidth() - 6 * perMM - 1.5), 5 * perMM);

				// 점선

				contentStream.setNonStrokingColor(Color.black);
				drawText("INVOICE VALUES :", ARIAL, fontSize, 128 * perMM, 40 * perMM, contentStream);
				drawText(chgCurreny, ARIAL, fontSize, 170 * perMM, 40 * perMM, contentStream);
				drawText(Double.toString(totalAmount), ARIAL, fontSize, 183 * perMM, 40 * perMM, contentStream);

				fontSize = 24;
				drawTextY = drawTextLineLengthRight(commercialInfo.getComEName(), PDType1Font.HELVETICA_BOLD_OBLIQUE,
						fontSize, 60 * perMM, 23 * perMM, contentStream, 20000, pageStandard.getWidth());

				contentStream.close();
				currenctPage++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//			PrinterJob job = PrinterJob.getPrinterJob();
//			job.setPageable(new PDFPageable(doc));
//			job.print();
		doc.close();

	}

	@Override
	public void printCommercialExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
		// TODO Auto-generated method stub
		try {
			String filePath = realFilePath + "excel/downloadData/invoice_temp.xlsx";
			String hawbNo = "";
			String comEName = "";
			FileInputStream fis = new FileInputStream(filePath);
			Row row = null;
			Cell cell = null;
			Workbook wb = WorkbookFactory.create(fis);
			for (int i = 0; i < orderNnoList.size(); i++) {
				CommercialVO commercialInfo = new CommercialVO();
				commercialInfo = mapper.selectCommercialInfo(orderNnoList.get(i));
				commercialInfo.dncryptData();

				Sheet sheet = wb.getSheetAt(i);
				Sheet sheet2 = wb.cloneSheet(i);
				CellStyle styleHawb = wb.createCellStyle();
				styleHawb.setAlignment(CellStyle.ALIGN_LEFT);
				styleHawb.setBorderTop(CellStyle.BORDER_THIN);
				styleHawb.setBorderBottom(CellStyle.BORDER_THIN);
				styleHawb.setBorderLeft(CellStyle.BORDER_THIN);
				styleHawb.setBorderRight(CellStyle.BORDER_THIN);
				wb.setSheetName(i, commercialInfo.getHawbNo());

				CellStyle styleCellLRB = wb.createCellStyle();
				Font font = wb.createFont();
				font.setFontName("맑은 고딕");
				font.setFontHeightInPoints((short) 8);
				styleCellLRB.setAlignment(CellStyle.ALIGN_LEFT);
				styleCellLRB.setBorderBottom(CellStyle.BORDER_THIN);
				styleCellLRB.setBorderLeft(CellStyle.BORDER_THIN);
				styleCellLRB.setBorderRight(CellStyle.BORDER_THIN);
				styleCellLRB.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				styleCellLRB.setWrapText(true);
				styleCellLRB.setFont(font);

				// left right line
				CellStyle styleCellLR = wb.createCellStyle();
				Font font2 = wb.createFont();
				font2.setFontName("맑은 고딕");
				font2.setFontHeightInPoints((short) 8);
				styleCellLR.setAlignment(CellStyle.ALIGN_LEFT);
				styleCellLR.setBorderLeft(CellStyle.BORDER_THIN);
				styleCellLR.setBorderRight(CellStyle.BORDER_THIN);
				styleCellLR.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				styleCellLR.setWrapText(true);
				styleCellLR.setFont(font2);

				// left dot line
				CellStyle styleCellLD = wb.createCellStyle();
				styleCellLD.setAlignment(CellStyle.ALIGN_LEFT);
				styleCellLD.setBorderLeft(CellStyle.BORDER_DOTTED);
				styleCellLD.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				styleCellLD.setWrapText(true);
				styleCellLD.setFont(font2);

				// left dot Right line
				CellStyle styleCellLDR = wb.createCellStyle();
				styleCellLDR.setAlignment(CellStyle.ALIGN_LEFT);
				styleCellLDR.setBorderLeft(CellStyle.BORDER_DOTTED);
				styleCellLDR.setBorderRight(CellStyle.BORDER_THIN);
				styleCellLDR.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				styleCellLDR.setWrapText(true);
				styleCellLDR.setFont(font2);

				// Left line
				CellStyle styleCellL = wb.createCellStyle();
				styleCellL.setAlignment(CellStyle.ALIGN_LEFT);
				styleCellL.setBorderLeft(CellStyle.BORDER_THIN);
				styleCellL.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				styleCellL.setWrapText(true);
				styleCellL.setFont(font2);

				// row1
				row = sheet.getRow(2);

				// cellStyleSetting(row,cell,styleHawb,0,7);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getHawbNo());

				// row2
				row = sheet.getRow(5);
				cellStyleSetting(row, cell, styleCellLR, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getComEName());

				row = sheet.getRow(6);
				cellStyleSetting(row, cell, styleCellLR, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getUserEAddrDetail() + " " + commercialInfo.getUserEAddr() + " "
						+ commercialInfo.getShipperCntry());

				row = sheet.getRow(9);
				cellStyleSetting(row, cell, styleCellLRB, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue("TEL : " + commercialInfo.getUserTel());

				row = sheet.getRow(5);
				cellStyleSetting(row, cell, styleCellLRB, 4, 7);
				cell = row.getCell(4);
				if (commercialInfo.getDepDate().equals("")) {
					String orderDate = mapper.selectUploadDate(commercialInfo.getHawbNo());
					cell.setCellValue(orderDate);			
				} else {
					cell.setCellValue(commercialInfo.getDepDate());
				}

				row = sheet.getRow(11);
				cellStyleSetting(row, cell, styleCellLR, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getCneeName());

				row = sheet.getRow(12);
				cellStyleSetting(row, cell, styleCellLR, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getCneeAddrDetail() + " " + commercialInfo.getCneeAddr() + " "
						+ commercialInfo.getDstnNation());

				row = sheet.getRow(15);
				cellStyleSetting(row, cell, styleCellLRB, 0, 3);
				cell = row.getCell(0);
				cell.setCellValue("TEL : " + commercialInfo.getCneeTel());

				row = sheet.getRow(19);
				cellStyleSetting(row, cell, styleCellLRB, 0, 1);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getShipperCntry());
				cellStyleSetting(row, cell, styleCellLR, 2, 3);
				cell = row.getCell(2);
				cell.setCellValue(commercialInfo.getDstnNation());
				
				row = sheet.getRow(21);
				cellStyleSetting(row, cell, styleCellLRB, 4, 7);
				cell = row.getCell(2);
				cell.setCellValue(commercialInfo.getDepDate());

				row = sheet.getRow(11);
				cellStyleSetting(row, cell, styleCellLR, 4, 7);
				cell = row.getCell(4);

				String remarkString = "";
				if (!commercialInfo.getPayment().equals("")) {
					remarkString += "Payment : " + commercialInfo.getPayment();
					remarkString += "\n";
				}

				if (!commercialInfo.getBuySite().equals("")) {
					remarkString += "Buy Site URL : " + commercialInfo.getBuySite();
					remarkString += "\n";
				}
				cell.setCellValue(remarkString);

				if (i + 1 == orderNnoList.size()) {
					wb.removeSheetAt(i + 1);
				}

				double totalAmount = 0;
				ArrayList<CommercialItemVO> commercialItem = new ArrayList<CommercialItemVO>();
				commercialItem = mapper.selectCommercialItem(orderNnoList.get(i));
				String chgCurreny = commercialItem.get(0).getChgCurrency();
				hawbNo = commercialInfo.getHawbNo();
				comEName = commercialInfo.getComEName();
				for (int j = 0; j < commercialItem.size(); j++) {
					double amount = 0;
					if (j < 13) {
						row = sheet.getRow(25 + j);
						cellStyleSetting(row, cell, styleCellLD, 1, 6);
						cellStyleSetting(row, cell, styleCellLDR, 7, 7);
						cell = row.getCell(1);
						cell.setCellValue(commercialItem.get(j).getItemDetail());
						cell = row.getCell(5);
						cell.setCellValue(commercialItem.get(j).getItemCnt());
						cell = row.getCell(6);
						cell.setCellValue(
								commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
						if (commercialItem.get(j).getItemDetail().length() > 55) {
							row.setHeight((short) 500);
						} else {
							row.setHeight((short) 350);
						}
					} else {
						row = sheet.createRow(25 + j);
						cellStyleSetting(row, cell, styleCellL, 0, 0);
						cellStyleSetting(row, cell, styleCellLD, 1, 6);
						cellStyleSetting(row, cell, styleCellLDR, 7, 7);
						sheet.addMergedRegion(new CellRangeAddress(25 + j, 25 + j, 1, 4));
						cell = row.getCell(1);
						cell.setCellValue(commercialItem.get(j).getItemDetail());
						cell = row.getCell(5);
						cell.setCellValue(commercialItem.get(j).getItemCnt());
						cell = row.getCell(6);
						cell.setCellValue(
								commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
						if (commercialItem.get(j).getItemDetail().length() > 55) {
							row.setHeight((short) 500);
						} else {
							row.setHeight((short) 350);
						}

					}
					totalAmount += amount;
				}
				int rowCnt = 0;
				if (commercialItem.size() > 13) {
					rowCnt = 24 + commercialItem.size();
					row = sheet.createRow(rowCnt+1);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 7));
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cell = row.getCell(0);
					cell.setCellValue("INVOICE VALUES :    " + commercialItem.get(0).getChgCurrency() + totalAmount);
					row.setHeight((short) 500);
					rowCnt++;
				} else {
					rowCnt = 38;
					row = sheet.createRow(rowCnt);
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					cell = row.getCell(0);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 7));

					cell.setCellValue("INVOICE VALUES :    " + commercialItem.get(0).getChgCurrency() + "     "
							+ totalAmount + "     ");
					row.setHeight((short) 500);
				}
				
				row = sheet.getRow(25);
				cell = row.getCell(0);
				cell.setCellValue(false);

				System.out.println("*****");
				System.out.println(rowCnt);
				rowCnt++;
				row = sheet.createRow(rowCnt);

				styleHawb = wb.createCellStyle();
				styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
				styleHawb.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
				styleHawb.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				styleHawb.setBorderTop(CellStyle.BORDER_THIN);
				styleHawb.setBorderBottom(CellStyle.BORDER_THIN);
				styleHawb.setBorderLeft(CellStyle.BORDER_THIN);
				styleHawb.setBorderRight(CellStyle.BORDER_THIN);
				sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 7));

				cellStyleSetting(row, cell, styleHawb, 0, 7);
				cell = row.getCell(0);
				row.setHeight((short) 400);

				rowCnt++;
				row = sheet.createRow(rowCnt);

				styleHawb = wb.createCellStyle();
				Font font3 = wb.createFont();
				font3.setFontName("맑은 고딕");
				font3.setFontHeightInPoints((short) 18);
				font3.setItalic(true);
				font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
				styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
				styleHawb.setBorderTop(CellStyle.BORDER_THIN);
				styleHawb.setBorderBottom(CellStyle.BORDER_THIN);
				styleHawb.setBorderLeft(CellStyle.BORDER_THIN);
				styleHawb.setBorderRight(CellStyle.BORDER_THIN);
				styleHawb.setFont(font3);
				sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 7));

				cellStyleSetting(row, cell, styleHawb, 0, 7);
				cell = row.getCell(0);
				row.setHeight((short) 600);
				cell.setCellValue(commercialInfo.getComEName());
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+hawbNo+"_"+comEName+".xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
/*
			// 컨텐츠 타입과 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=commercial_invoice.xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
*/
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void cellStyleSetting(Row row, Cell cell, CellStyle styleCell, int startIndex, int endIndex) {
		for (int i = startIndex; i <= endIndex; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(styleCell);
		}

	}

	public void insertSendTable(HashMap<String, Object> sendData) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertSendTable(sendData);
	}

	@Override
	public void krPostZPLTest(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList,
			String printType) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String barcodePath2 = pdfPath2 + userId + "2.JPEG";
		String qrPath = pdfPath2 + userId + "qrCode.JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
//				String markPath2 = pdfPath+"/mark/test2.png";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			String terminalNo = "";
			String terminalName = "";
			String centerNo = "";
			String centerName = "";
			String deliveryTeamNo = "";
			String deliveryPointName = "";

			for (int i = 0; i < orderNnoList.size(); i++) {

				pdfPrintInfo = mapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();

				HashMap<String, Object> itemInfos = mapper.selectItemInfos(pdfPrintInfo.getNno());
				// 페이지 추가
				try {
					String addr = URLEncoder.encode(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(),
							"UTF-8");
					// String addr =pdfPrintInfo.getCneeAddr()+" "+pdfPrintInfo.getCneeAddrDetail();
					String obj = "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip="
							+ pdfPrintInfo.getCneeZip() + "&addr=" + addr + "&mdiv=1";

					// String obj =
					// "http://biz.epost.go.kr/KpostPortal/openapi2?regkey=6c1591025283e7f361486099678545&target=delivArea&zip=22376&addr=인천광역시
					// 중구 영종대로252번길 12 운서동 영종LH2단지 아파트 205동1310호&mdiv=1";
					// If response status == 200
					DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
					Document doc2 = dBuilder.parse(obj);
					doc2.getDocumentElement().normalize();
					NodeList resultNodeList = doc2.getElementsByTagName("result");

					Node nNode = resultNodeList.item(0);
					Element eElement = (Element) nNode;
					terminalNo = getTagValue("delivAreaCd", eElement).substring(0, 2);
					terminalName = getTagValue("arrCnpoNm", eElement);
					centerNo = getTagValue("delivAreaCd", eElement).substring(2, 5);
					centerName = getTagValue("delivPoNm", eElement);
					deliveryTeamNo = getTagValue("delivAreaCd", eElement).substring(5, 7);
					deliveryPointName = getTagValue("delivAreaCd", eElement).substring(7, 9);

				} catch (Exception e) {
					throw e;
				}

				PDRectangle pageStandard = new PDRectangle(168 * perMM, 107 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				PDPage page = doc.getPage(i);

				// 컨텐츠 스트림 열기 바코드 1
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getCneeZip());

				barcodes.setSize(120, 800);
				barcodes.setBarHeight(1);
				barcodes.setBarWidth(1);

				File barcodefile = new File(barcodePath);
				BufferedImage source = BarcodeImageHandler.getImage(barcodes);

				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				// 바코드 2
				net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

				barcodes2.setSize(300, 800);
				barcodes2.setBarHeight(1);
				barcodes2.setBarWidth(1);

				File barcodefile2 = new File(barcodePath2);

				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

				File markFile = new File(markPath);
				PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);

				// qrcode 생성
				File qrCodeFile = new File(qrPath);
				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = qrCodeWriter.encode(pdfPrintInfo.getHawbNo(), BarcodeFormat.QR_CODE, 200, 200); // 텍스트,
																														// 바코드
																														// 포맷,가로,세로

				MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF); // 진한색, 연한색
				BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

				ImageIO.write(qrImage, "png", qrCodeFile); // temp 위치에 qr이 이미지 생성됨.
				// InputStream is = new FileInputStream(temp.getAbsolutePath()); // 인풋 스트림으로
				// 변환(향후 S3로 업로드하기위한 작업)

				// 배경이미지 로드

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(qrCodeFile, doc);

				PDImageXObject pdImage3 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

				int yWidth = 0;

				int fontSize = 10; // Or whatever font size you want.

				/* 왼쪽 */
				fontSize = 12;
				yWidth += fontSize;
				float titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getShipperName()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getShipperName(), NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 19 * perMM, contentStream);

				fontSize = 11;
				yWidth += fontSize;
				String lineString = "Order No:" + pdfPrintInfo.getOrderNo();
				drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 19 * perMM, contentStream, 0);

				fontSize = 7;
				yWidth = fontSize;
				lineString = "자가사용 목적 통관물품 상용판매시 처벌 받을 수 있습니다";
				drawText(lineString, NanumGothic, fontSize, 4 * perMM, pageStandard.getHeight() - yWidth - 38 * perMM,
						contentStream);

				yWidth += fontSize;
				lineString = itemInfos.get("itemDetail").toString();
				if (!itemInfos.get("itemTotalCnt").toString().equals("1")) {
					lineString = lineString + " others " + itemInfos.get("itemTotalCnt").toString();
				}
				drawTextLine(lineString, NanumGothic, fontSize, 4 * perMM,
						pageStandard.getHeight() - yWidth - 38 * perMM, contentStream, 0);
				/* 왼쪽 끝 */

				/* 오른쪽 */
				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(terminalNo) / 1000 * fontSize;
				drawText(terminalNo, NanumGothic, fontSize, 63 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
						contentStream);

				fontSize = 8;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(terminalName) / 1000 * fontSize;
				drawText(terminalName, NanumGothic, fontSize, 71 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
						contentStream);

				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(centerNo) / 1000 * fontSize;
				drawText(centerNo, NanumGothic, fontSize, 79 * perMM, pageStandard.getHeight() - yWidth - 8 * perMM,
						contentStream);

				fontSize = 8;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(centerName) / 1000 * fontSize;
				drawText(centerName, NanumGothic, fontSize, 88 * perMM, pageStandard.getHeight() - 15 - 10 * perMM,
						contentStream);

				fontSize = 15;
				yWidth = fontSize;
				titleWidth = NanumGothic.getStringWidth(deliveryTeamNo) / 1000 * fontSize;
				drawText(deliveryTeamNo, NanumGothic, fontSize, 97 * perMM,
						pageStandard.getHeight() - yWidth - 8 * perMM, contentStream);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(deliveryPointName) / 1000 * fontSize;
				drawText(deliveryPointName, NanumGothic, fontSize, 105 * perMM,
						pageStandard.getHeight() - 15 - 8 * perMM, contentStream);

				contentStream.drawImage(pdImage2, 115 * perMM, pageStandard.getHeight() - 15 - 12 * perMM, 40f, 40f);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeZip()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeZip(), NanumGothic, fontSize, 72 * perMM,
						pageStandard.getHeight() - 15 - 22 * perMM, contentStream);

				contentStream.drawImage(pdImage, 95 * perMM, pageStandard.getHeight() - 15 - 27 * perMM, 100f, 30f);

				fontSize = 15;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeName()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeName(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 35 * perMM, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeTel()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getCneeTel(), NanumGothic, fontSize, 120 * perMM,
						pageStandard.getHeight() - 23 - 33 * perMM, contentStream);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddr()) / 1000 * fontSize;
				drawTextLine(pdfPrintInfo.getCneeAddr(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 40 * perMM, contentStream, 0);

				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getCneeAddrDetail()) / 1000 * fontSize;
				drawTextLine(pdfPrintInfo.getCneeAddrDetail(), NanumGothic, fontSize, 68 * perMM,
						pageStandard.getHeight() - 23 - 50 * perMM, contentStream, 0);

				contentStream.drawImage(pdImage3, 68 * perMM, pageStandard.getHeight() - 23 - 80 * perMM, 200f, 30f);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo(), NanumGothic, fontSize, 88 * perMM,
						pageStandard.getHeight() - 23 - 85 * perMM, contentStream);

				/* 오른쪽 끝 */
//						fontSize = 12;
//						titleWidth = NanumGothic.getStringWidth(sagawaInfo.getHawbNo()) / 1000 * fontSize;
//						drawText(sagawaInfo.getHawbNo(),NanumGothic, fontSize, (80*perMM - titleWidth) / 2, 17,contentStream);

				contentStream.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//				PrinterJob job = PrinterJob.getPrinterJob();
//				job.setPageable(new PDFPageable(doc));
//				job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		File barcodefile2 = new File(barcodePath2);
		File barcodefile3 = new File(qrPath);

		if (barcodefile.exists()) {
			barcodefile.delete();
		}

		if (barcodefile2.exists()) {
			barcodefile2.delete();
		}

		if (barcodefile3.exists()) {
			barcodefile3.delete();
		}

	}

	@Override
	public String selectHawbNoByNNO(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbNoByNNO(nno);
	}

	@Override
	@Transactional
	public BlApplyVO selectBlApply(String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		TestssVO parameters = new TestssVO();
		parameters.setUserId(userId);
		parameters.setUserIp(userIp);
		BlApplyVO tmp = new BlApplyVO();
		parameters.setNno(orderNno);

		tmp = mapper.selectBlApply(parameters);
		System.out.println(tmp.getHawbNo());
		System.out.println(tmp.getRstCode());
		System.out.println(tmp.getStatus());
		System.out.println(tmp.getRstMsg());

		return tmp;
	}
	
	@Override
	public void comnBlApply(String orderNno, String transCode, String userId, String userIp, String blType) throws Exception {
		String jsonVal = "";
		ProcedureVO rst = new ProcedureVO();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("nno", orderNno);
		parameterInfo.put("userId", userId);
		parameterInfo.put("userIp", userIp);
		switch (transCode) {
		case "ACI":
			selectBlApply(orderNno, userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "GTS":
			// gtsAPI.selectBlApplyGts(request,
			// orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
			selectBlApply(orderNno, userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "CSE":
			selectBlApply(orderNno, userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "ARA":
			usrService.selectBlApplyARA(orderNno, userId, userIp);
			break;
		case "YSL":
			ysApi.selectBlApplyYSL(orderNno, userId, userIp, "Nomal");
			yslPdf(orderNno, transCode, userId, userIp, blType);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "EFS":
			efsApi.selectBlApplyEFS(orderNno, userId, userIp);
			efsPdf(orderNno, transCode, userId, userIp);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "FED":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
			break;
		case "FES":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
			break;
		case "FEG":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FEG");
			break;
		case "OCS":
			ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
			ocsPdf(orderNno, transCode, userId, userIp);
			break;
		case "EPT":
			ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
			krPostPdf(orderNno, transCode, userId, userIp);
			break;
		case "EMS":
			emsApi.selectBlApply_FakeBl(orderNno, userId, userIp, "Nomal");
			// krPostPdf(request, orderNno, transCode);
			break;
		case "EMN":
			if (blType.equals("TAKEIN")) {
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
			} else {
				emsApi.selectBlApply_Nomal(orderNno, userId, userIp, "Nomal");
			}
			//emsApi.selectBlApply_Nomal(orderNno, userId, userIp, "Nomal");
			// krPostPdf(request, orderNno, transCode);
			break;
		case "CJ":
			selectBlApply(orderNno, userId, userIp);
			break;
		case "ITC":
			itcApi.selectBlApplyITC(orderNno, userId, userIp);
			break;
		case "SEK":
			sekoApi.selectBlApplySEK(orderNno, userId, userIp);
			break;
		case "FB":
			fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
			break;
		case "FB-EMS":
			fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
			break;
		case "ACI-T86":
			selectBlApply(orderNno, userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "HJ":
			hjApi.selectBlApply(orderNno, userId, userIp);
			break;
		case "USP":
			ssApi.selectBlApply(orderNno, userId, userIp);
			break;
		case "YT":
			jsonVal = ytApi.createRequestVal(orderNno);	// RequestBody 생성
			rst = ytApi.createShipment(orderNno, userId, userIp, jsonVal);	// Create Order
			if (rst.getRstStatus().equals("SUCCESS")) {
				ytApi.createLabel(rst.getRstHawbNo(), orderNno, userId, userIp);	// Create Label
				parameterInfo.put("hawbNo", rst.getRstHawbNo());
				createParcelBl(parameterInfo);	// Insert TB from TMP
			} else {
				parameterInfo.put("errorMsg", rst.getRstMsg());
				mapper.insertTbErrorMatch(parameterInfo);	// Insert Error Message
			}
			break;
		default:
			selectBlApply(orderNno, userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		}
	}

	@Override
	public void comnBlApplyCheck(String orderNno, String transCode, String userId, String userIp, String blType)
			throws Exception {
		// TODO Auto-generated method stub
		switch (transCode) {
		case "ACI":
			selectBlApply(orderNno, userId, userIp);
			break;
		case "GTS":
			// gtsAPI.selectBlApplyGts(request,
			// orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
			selectBlApply(orderNno, userId, userIp);
			break;
		case "CSE":
			selectBlApply(orderNno, userId, userIp);
			break;
		case "ARA":// 등록 시, 데이터 값 중 전화번호, 이메일 등은 임의의 값으로 수정 하여 등록 하도록 수정. (check에서만!)
			usrService.selectBlApplyARA(orderNno, userId, userIp);
			break;
		case "YSL":
			ysApi.selectBlApplyYSLCheck(orderNno, userId, userIp);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "EFS":
			efsApi.selectBlApplyEFS(orderNno, userId, userIp);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "FED":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
			break;
		case "FES":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
			break;
		case "FEG":
			fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FEG");
			break;
		case "OCS":
			ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
			break;
		case "EPT":
			ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
			break;
		case "EMS":
			emsApi.selectBlApplyCheck(orderNno, userId, userIp);
			// krPostPdf(request, orderNno, transCode);
			break;
		case "EMN":
			emsApi.selectBlApplyCheck(orderNno, userId, userIp);
			// krPostPdf(request, orderNno, transCode);
			break;
		case "CJ":
			selectBlApply(orderNno, userId, userIp);
			break;
		case "ITC":
			itcApi.selectBlApplyITC(orderNno, userId, userIp);
			break;
		case "SEK":
			sekoApi.selectBlApplySEK(orderNno, userId, userIp);
			break;
		case "T86":
			t86Api.selectBlApplyT86(orderNno, userId, userIp);
			break;
		case "FB":
			fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
			break;
		case "FB-EMS":
			fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
			break;
		case "HJ":
			hjApi.selectBlApply(orderNno, userId, userIp);
			break;
		default:
			selectBlApply(orderNno, userId, userIp);
			break;
		}
	}

	@Override
	public void comnBlApplyInsp(String orderNno, String transCode, String userId, String userIp, String blType)
			throws Exception {
		// TODO Auto-generated method stub
		switch (transCode) {
		case "ACI":
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "GTS":
			// gtsAPI.selectBlApplyGts(request,
			// orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
			break;
		case "CSE":
			savePdf(orderNno, transCode, userId, userIp);
			break;
		case "ARA":
			// usrService.selectBlApplyARA(orderNno,userId, userIp);
			break;
		case "YSL":
			ysApi.selectBlApplyYSL(orderNno, userId, userIp, "INSP");
			yslPdf(orderNno, transCode, userId, userIp, blType);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "EFS":
			efsPdf(orderNno, transCode, userId, userIp);
			// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
//			case "FED":
//				fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FES");
//				break;
//			case "FES":
//				fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FES");
//				break;
//			case "FEG":
//				fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FEG");
//				break;
		case "OCS":
			ocsPdf(orderNno, transCode, userId, userIp);
			break;
		case "EPT":
			krPostPdf(orderNno, transCode, userId, userIp);
			break;
		case "EMS":
			emsApi.selectBlApply_FakeBl(orderNno, userId, userIp, "INSP");
			// krPostPdf(request, orderNno, transCode);
			break;
		case "EMN":
			emsApi.selectBlApply_Nomal(orderNno, userId, userIp, "INSP");
			// krPostPdf(request, orderNno, transCode);
			break;
//			case "CJ":
//				selectBlApply(orderNno,userId, userIp);
//				break;
//			case "ITC":
//				itcApi.selectBlApplyITC(orderNno, userId, userIp);
//				break;
//			case "SEK":
//				sekoApi.selectBlApplySEK(orderNno, userId, userIp);
//				break;
		default:
//				selectBlApply(orderNno,userId, userIp);
			savePdf(orderNno, transCode, userId, userIp);
			break;
		}
	}

	@Override
	public void insertTmpFromOrderList(String nno, String status) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertTmpFromOrderList(nno, status);

	}

	@Override
	public void insertTmpFromOrderItem(String nno) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertTmpFromOrderItem(nno);

	}

	@Override
	public void deleteUserOrderTmp(String[] orderNno, String userId) throws Exception {
		// TODO Auto-generated method stub
		for (int roop = 0; roop < orderNno.length; roop++) {
			mapper.deleteUserOrderItemTmp(orderNno[roop]);
			mapper.deleteUserOrderListTmp(orderNno[roop]);
			mapper.deleteExpLicence(orderNno[roop]);
		}
		mapper.updateUserOrderListStatus(userId);
	}

	@Override
	public void deleteUserOrder(String[] orderNno, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String userId = (String) request.getSession().getAttribute("USER_ID");
		String userIp = (String) request.getSession().getAttribute("USER_IP");
		for (int roop = 0; roop < orderNno.length; roop++) {
			mapper.deleteUserOrder(orderNno[roop], userId, userIp);
		}
		mapper.updateUserOrderListStatus(userId);
	}

	@Override
	public void insertUserOrder(String[] orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub

		for (int roop = 0; roop < orderNno.length; roop++) {
			mapper.insertUserOrderItemFromTmp(orderNno[roop]);
			mapper.insertUserOrderListFromTmp(orderNno[roop]);
			mapper.deleteUserOrderItemTmp(orderNno[roop]);
			mapper.deleteUserOrderListTmp(orderNno[roop]);
		}

	}

	@Override
	public void insertTBFromTMP(String orderNno) throws Exception {
		mapper.insertUserOrderListFromTmp(orderNno);
		mapper.insertUserOrderItemFromTmp(orderNno);
		mapper.deleteUserOrderItemTmp(orderNno);
		mapper.deleteUserOrderListTmp(orderNno);
	}

	@Override
	public void insertTMPFromTB(String orderNno, String status, String userId, String userIp) throws Exception {
		mapper.insertTmpFromOrderList(orderNno, status);
		mapper.insertTmpFromOrderItem(orderNno);
		mapper.deleteUserOrder(orderNno, userId, userIp);
	}

	@Override
	public void insertTMPFromTB_EMS(String orderNno, String status, String userId, String userIp) throws Exception {
		try {
			mapper.insertTmpFromOrderList_EMS(orderNno, status);
			mapper.insertTmpFromOrderItem(orderNno);
			mapper.deleteUserOrder(orderNno, userId, userIp);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public void insertUserOrderItemFromTmp(String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertUserOrderItemFromTmp(orderNno);
	}

	@Override
	public void insertUserOrderListFromTmp(String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertUserOrderListFromTmp(orderNno);
	}

	@Override
	public void deleteUserOrderItemTmp(String nno) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteUserOrderItemTmp(nno);
	}

	@Override
	public void deleteUserOrderItem(String nno) throws Exception {
		mapper.deleteUserOrderItem(nno);
	}

	@Override
	public UserOrderListVO selectUserRegistOrderOne(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserRegistOrderOne(nno);
	}

	@Override
	public void updateHawbNoInTbHawb(String hawbNo, String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateHawbNoInTbHawb(hawbNo, orderNno);
	}

	@Override
	public void deleteHawbNoInTbHawb(String nno) throws Exception {
		mapper.deleteHawbNoInTbHawb(nno);
	}

	@Override
	public void deleteOrderListByNno(String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteOrderListByNno(orderNno);
	}

	@Override
	public void updateUserOrderListStatus(String userId) throws Exception {
		mapper.updateUserOrderListStatus(userId);
	}

	@Override
	public AciFakeBlVO getAciFakeBl(AciFakeBlVO aciFakeBlVO) throws Exception {

		return mapper.getAciFakeBl(aciFakeBlVO);
	}

	public String[] confirmDialNum(String numbers) throws Exception {
		String returnVal[] = null;
		if (numbers.substring(0, 2).equals("82")) {
			String shipperOriTel = numbers.substring(2, numbers.length());
			int checkNum = Integer.parseInt(shipperOriTel.substring(0, 1));
			if (checkNum == 0) {
				checkNum = Integer.parseInt(shipperOriTel.substring(0, 2));
				if (checkNum == 2) {
					String regEx = "(\\d{2})(\\d{3,4})(\\d{4})";
					returnVal = shipperOriTel.replaceAll(regEx, "$1-$2-$3").split("-");
				} else if (checkNum != 2) {
					String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
					returnVal = shipperOriTel.replaceAll(regEx, "$1-$2-$3").split("-");
				}
			} else {
				if (checkNum == 2) {
					String regEx = "(\\d{1})(\\d{3,4})(\\d{4})";
					returnVal = shipperOriTel.replaceAll(regEx, "$1-$2-$3").split("-");
				} else if (checkNum != 2) {
					String regEx = "(\\d{2})(\\d{3,4})(\\d{4})";
					returnVal = shipperOriTel.replaceAll(regEx, "$1-$2-$3").split("-");
				}
			}

		} else {
			int checkNum = Integer.parseInt(numbers.substring(0, 2));
			if (checkNum == 2) {
				String regEx = "(\\d{2})(\\d{3,4})(\\d{4})";
				returnVal = numbers.replaceAll(regEx, "$1-$2-$3").split("-");
			} else if (checkNum == 16) {
				String regEx = "(\\d{2})(\\d{2})(\\d{4})";
				returnVal = numbers.replaceAll(regEx, "$1-$2-$3").split("-");
			} else if (checkNum != 2 || checkNum != 16) {
				String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
				returnVal = numbers.replaceAll(regEx, "$1-$2-$3").split("-");
			}
		}
		return returnVal;
	}

	@Override
	public void updateShipperReference(String shipperReference, String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateShipperReference(shipperReference, orderNno);

	}

	@Override
	public void updateHawbNoInTbOrderList(String hawbNo, String orderNno) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateHawbNoInTbOrderList(hawbNo, orderNno);
	}

	@Override
	public void deleteMatchingInfo(MatchingVO matchVo) throws Exception {
		mapper.deleteMatchingInfo(matchVo);
	}

	@Override
	public void insertMatchingInfo(MatchingVO matchVo) throws Exception {
		mapper.insertMatchingInfo(matchVo);
	}

	@Override
	public String selectOrderDate(String hawbNo) throws Exception {
		return mapper.selectOrderDate(hawbNo);
	}

	@Override
	public String selectHawbDate(String hawbNo) throws Exception {
		return mapper.selectHawbDate(hawbNo);
	}

	@Override
	public String selectNNO() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNNO();
	}

	@Override
	public String selectNewRegNo(ReturnRequestVO rtnVal) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNewRegNo(rtnVal);
	}

	@Override
	public void deleteRegNo(String regNo) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteRegNo(regNo);
	}

	@Override
	public String selectLegacyRegNo(ReturnRequestVO rtnVal) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectLegacyRegNo(rtnVal);
	}

	@Override
	public String selectReturnRequestId(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequestId(parameterInfo);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnRequestStatus(HashMap<String, Object> parameterInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequestStatus(parameterInfo);
	}

	@Override
	public LinkedHashMap<String, Object> selectStockMsg(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockMsg(nno);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectStockFile(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockFile(nno);
	}

	@Override
	public ArrayList<NationVO> selectOrgStation() {
		// TODO Auto-generated method stub
		return mapper.selectOrgStation();
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnStationList(String parameter) {
		// TODO Auto-generated method stub
		return mapper.selectReturnStationList(parameter);
	}

	@Override
	public ArrayList<CurrencyVO> selectCurrencyList() {
		// TODO Auto-generated method stub
		return mapper.selectCurrencyList();
	}

	@Override
	public int selectUserIdCheck(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectUserIdCheck(params);
	}

	@Override
	public UserVO selectUserEmail(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectUserEmail(userId);
	}

	@Override
	public void updateUserPw(ManagerVO userInfo) {
		// TODO Auto-generated method stub
		mapper.updateUserPw(userInfo);
	}

	@Override
	public ArrayList<NationVO> selectUserNationCode(String username) throws Exception {
		return mapper.selectUserNationCode(username);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectCurrencyListByDstnNation(HashMap<String, Object> parameters)
			throws Exception {
		return mapper.selectCurrencyListByDstnNation(parameters);
	}

	@Override
	public ArrayList<NationVO> selectUserDstnNationList(String userId) {
		return mapper.selectUserDstnNationList(userId);
	}

	@Override
	public void updateTmpOrderStatus(String nno) throws Exception {
		mapper.updateTmpOrderStatus(nno);
	}

	@Override
	public ArrayList<String> selectYslOrderList() throws Exception {
		return mapper.selectYslOrderList();
	}

	@Override
	public void comnPrintPacking(HttpServletRequest request, HttpServletResponse response, Model model, ArrayList<String> orderNnoList) throws Exception {
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> pdfPrintInfo = new HashMap<String, Object>();
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
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
		int totalPage = 0;
		try {
			
			for (int i = 0; i < orderNnoList.size(); i++) {
				pdfPrintInfo = mapper.selectPdfPrintInfoSimple(orderNnoList.get(i));
				packingInfo = mapper.selectPdfPrintItemInfo(orderNnoList.get(i));
				// 페이지 추가
				PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);
				// 현재 페이지 설정
				PDPage page = doc.getPage(totalPage);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				
				String transCode = pdfPrintInfo.get("transCode").toString();
				
				contentStream.drawLine(10, 420, (pageStandard.getWidth()) - 10, 420);
				contentStream.drawLine(10, 370, (pageStandard.getWidth()) - 10, 370);
				contentStream.drawLine(10, 420, 10, 370);
				contentStream.drawLine((pageStandard.getWidth()) - 10, 420, (pageStandard.getWidth()) - 10, 370);
				contentStream.drawLine((pageStandard.getWidth()) - 70, 420, (pageStandard.getWidth()) - 70, 370);
				contentStream.drawLine((pageStandard.getWidth()) - 70, 400, (pageStandard.getWidth()) - 10, 400);
			
				if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
					contentStream.beginText();
					contentStream.setFont(NanumGothic, 18);
					contentStream.newLineAtOffset(14.5f, 395.5f);
					contentStream.showText(pdfPrintInfo.get("hawbNo").toString());
					contentStream.endText();
				} else {
					contentStream.beginText();
					contentStream.setFont(NanumGothic, 20);
					contentStream.newLineAtOffset(14.5f, 395.5f);
					contentStream.showText(pdfPrintInfo.get("hawbNo").toString());
					contentStream.endText();
				}
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 14);
				contentStream.newLineAtOffset(15f, 378.5f);
				contentStream.showText(pdfPrintInfo.get("orderNo").toString());
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 10);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 407);
				contentStream.showText("총 내품수량");
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 12);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 45, 383.5f);
				contentStream.showText(String.valueOf(pdfPrintInfo.get("totalItemCnt")));
				contentStream.endText();
				
				contentStream.drawLine(10, 370, 10, 350);
				contentStream.drawLine((pageStandard.getWidth()) - 10, 370, (pageStandard.getWidth()) - 10, 350);
				contentStream.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				
				contentStream.drawLine((pageStandard.getWidth()) - 135, 370, (pageStandard.getWidth()) - 135, 350);
				contentStream.drawLine((pageStandard.getWidth()) - 90, 370, (pageStandard.getWidth()) - 90, 350);
				contentStream.drawLine((pageStandard.getWidth()) - 55, 370, (pageStandard.getWidth()) - 55, 350);
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 10);
				contentStream.newLineAtOffset(15, 355.5f);
				contentStream.showText("내품명");
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 10);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 130, 355.5f);
				contentStream.showText("사입코드");
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 10);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 82, 355.5f);
				contentStream.showText("수량");
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(NanumGothic, 10);
				contentStream.newLineAtOffset((pageStandard.getWidth()) - 45, 355.5f);
				contentStream.showText("Rack");
				contentStream.endText();

				int limit = 0;
				float startLine = 338;
				String itemDetail = "";
				for (int items = 0; items < packingInfo.size(); items++) {
					float fixLine = 5;
					
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
					contentStream.beginText();
					contentStream.setFont(NanumGothic, 9);
					contentStream.newLineAtOffset((pageStandard.getWidth()) - 132, startLine - 3);
					contentStream.showText(packingInfo.get(items).get("takeInCode").toString());
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.setFont(NanumGothic, 9);
					contentStream.newLineAtOffset((pageStandard.getWidth()) - 79, startLine - 3);
					contentStream.showText(packingInfo.get(items).get("itemCnt").toString());
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.setFont(NanumGothic, 9);
					contentStream.newLineAtOffset((pageStandard.getWidth()) - 47, startLine - 3);
					contentStream.showText(packingInfo.get(items).get("rackCode").toString());
					contentStream.endText();
					
					for (int x = 0; x < stringArr.length; x++) {
						contentStream.beginText();
						contentStream.setFont(NanumGothic, 8);
						contentStream.newLineAtOffset(12, startLine - 3.5f);
						contentStream.showText(stringArr[x]);
						contentStream.endText();
						
						if (x == stringArr.length-1)  {
							startLine -= 17;
						} else {
							startLine -= 15;
						}
					}
					//contentStream.drawLine(10, startLine+6.5f, (pageStandard.getWidth()) - 10, startLine+6.5f);
				}
				
				float endLine = startLine;
				
				contentStream.drawLine(10, 350, (pageStandard.getWidth()) - 10, 350);
				contentStream.drawLine(10, 355.5f, 10, endLine+6.5f);
				contentStream.drawLine((pageStandard.getWidth()) - 10, 355.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
				contentStream.drawLine(10, endLine+6.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
				contentStream.close();
				
				totalPage++;
			}
			
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		
		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
		doc.close();
	}

	@Override
	public HashMap<String, Object> selectMatchCarriers(String hawbNo) throws Exception {
		return mapper.selectMatchCarriers(hawbNo);
	}

	@Override
	public String selectAgencyBl(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectAgencyBl(parameters);
	}

	@Override
	@Transactional
	public BlApplyVO createParcelBl(HashMap<String, Object> parameters) throws Exception {
		BlApplyVO blRst = new BlApplyVO();
		blRst = mapper.spTransBl(parameters);
		return blRst;
	}

	@Override
	public String selectSubTransCode(String hawbNo) throws Exception {
		return mapper.selectSubTransCode(hawbNo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnMemoList(HashMap<String, Object> parameters) {
		return mapper.selectReturnMemoList(parameters);
	}

	@Override
	public void insertReturnCsMemo(HashMap<String, Object> parameters) throws Exception {
		mapper.insertReturnCsMemo(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectRecentMemoInfo(HashMap<String, Object> parameters) {
		return mapper.selectRecentMemoInfo(parameters);
	}

	@Override
	public void updateReturnCsReadYn(HashMap<String, Object> parameters) throws Exception {
		mapper.updateReturnCsReadYn(parameters);
	}

	@Override
	public void printBarcodePdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> nnoList)
			throws Exception {
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
		
		String barcodePath = pdfPath2 + request.getSession().getAttribute("USER_ID") + ".JPEG";
		
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

		for (int i = 0; i < nnoList.size(); i++) {
			String nno = nnoList.get(i);
			HashMap<String, Object> printInfo = new HashMap<String, Object>();
			printInfo = mapper.selectReturnPrintInfo(nno);
			String koblNo = printInfo.get("koblNo").toString();
			String trkNo = printInfo.get("trkNo").toString();
			
			PDRectangle pageStandard = new PDRectangle(226, 85);
			PDPage blankPage = new PDPage(pageStandard);
			doc.addPage(blankPage);

			// 현재 페이지 설정
			PDPage page = doc.getPage(totalPage);

			// 컨텐츠 스트림 열기
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(trkNo);

			barcodes.setSize(400, 800);
			barcodes.setBarHeight(0);
			barcodes.setBarWidth(0);

			File barcodefile = new File(barcodePath);
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			
			int pdfImageWidth = pdImage.getWidth();
			int pdfImageHeight = pdImage.getHeight();
			int targetWidth = 200;
			int targetHeight = (int) ((double) targetWidth * pdfImageHeight / pdfImageWidth);
			
			contentStream.drawImage(pdImage, (pageStandard.getWidth() - targetWidth) / 2, 15, targetWidth, targetHeight);
			
			int fontSize = 12;
			float fontHeight = NanumGothic.getFontDescriptor().getFontBoundingBox().getHeight() * fontSize / 1000;
			float pageWidth = page.getMediaBox().getWidth();
			
			float trkNoWidth = NanumGothic.getStringWidth(trkNo) * fontSize / 1000f;
			float trkNoX = (pageWidth - trkNoWidth) / 2;
			
			contentStream.beginText();
			contentStream.newLineAtOffset(trkNoX, 22f + targetHeight);
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.showText(trkNo);
			contentStream.endText();
			
			
			float baseY = 24f + targetHeight + fontHeight;
			float koblNoWidth = NanumGothic.getStringWidth(koblNo) * fontSize / 1000f;
			float koblNoX = (pageWidth - koblNoWidth) / 2;
			
			contentStream.beginText();
			contentStream.newLineAtOffset(koblNoX, baseY);
			contentStream.setFont(NanumGothic, fontSize);
			contentStream.showText(koblNo);
			contentStream.endText();
			

			contentStream.close();
			totalPage++;
		}
		
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		

		
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		job.print();
		doc.close();
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}
	}

	public void insertAraErrorMatch(HashMap<String, Object> araErrorParams) throws Exception {
		mapper.insertAraErrorMatch(araErrorParams);
	}

	@Override
	public String selectTrackingNoYT(String hawbNo) throws Exception {
		return mapper.selectTrackingNoYT(hawbNo);
	}

	@Override
	public String comnBlApplyV2(String orderNno, String transCode, String userId, String userIp, String orderType, String uploadType) throws Exception {
		String returnVal = "S";
		
		try {
			String jsonVal = "";
			ProcedureVO rst = new ProcedureVO();
			ShipmentVO shipment = new ShipmentVO();
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("nno", orderNno);
			parameterInfo.put("userId", userId);
			parameterInfo.put("userIp", userIp);
			parameterInfo.put("uploadType", uploadType);
			parameterInfo.put("transCode", transCode);
			parameterInfo.put("orderType", orderType);
			ProcedureRst procRst = new ProcedureRst();
			
			switch (transCode) {
			case "ACI":
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
				break;
			case "GTS":
				// gtsAPI.selectBlApplyGts(request,
				// orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
				break;
			case "CSE":
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
				break;
			case "ARA":
				usrService.selectBlApplyARA(orderNno, userId, userIp);
				break;
			case "YSL":
				ysApi.selectBlApplyYSL(orderNno, userId, userIp, "Nomal");
				yslPdf(orderNno, transCode, userId, userIp, orderType);
				// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
				break;
			case "EFS":
				efsApi.selectBlApplyEFS(orderNno, userId, userIp);
				efsPdf(orderNno, transCode, userId, userIp);
				// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
				break;
			case "FED":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
				break;
			case "FES":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
				break;
			case "FEG":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FEG");
				break;
			case "OCS":
				ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
				ocsPdf(orderNno, transCode, userId, userIp);
				break;
			case "EMS":
				emsApi.selectBlApply_FakeBl(orderNno, userId, userIp, "Nomal");
				// krPostPdf(request, orderNno, transCode);
				break;
			case "EMN":
				if (orderType.equals("TAKEIN")) {
					selectBlApply(orderNno, userId, userIp);
					savePdf(orderNno, transCode, userId, userIp);
				} else {
					emsApi.selectBlApply_Nomal(orderNno, userId, userIp, "Nomal");
				}
				//emsApi.selectBlApply_Nomal(orderNno, userId, userIp, "Nomal");
				// krPostPdf(request, orderNno, transCode);
				break;
			case "CJ":
				Order order = new Order();
				order.setNno(orderNno);
				order.setWUserId(userId);
				order.setWUserIp(userIp);
				order.setHawbNo("");
				ProcedureRst cjShipment = logisticMapper.execSpRegShipment(order);
				if ("FAIL".equals(cjShipment.getRstStatus())) {
					returnVal = "F";
				}
				//selectBlApply(orderNno, userId, userIp);
				break;
			case "ITC":
				itcApi.selectBlApplyITC(orderNno, userId, userIp);
				break;
			case "SEK":
				sekoApi.selectBlApplySEK(orderNno, userId, userIp);
				break;
			case "FB":
				fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
				break;
			case "FB-EMS":
				fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
				break;
			case "ACI-T86":
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
				break;
			case "HJ":
				hjApi.selectBlApply(orderNno, userId, userIp);
				break;
			case "USP":
				ssApi.selectBlApply(orderNno, userId, userIp);
				break;
			case "EPT":
				jsonVal = shippingService.createRequestBody(parameterInfo);
				shipment = shippingService.createShipment(parameterInfo, jsonVal);
				if (shipment.getRstStatus().equals("SUCCESS")) {
					parameterInfo.put("hawbNo", shipment.getRstHawbNo());
					shippingService.applyShipment(parameterInfo);
				} else {
					returnVal = "F";
					if (shipment.getRstCode().equals("F")) {
						parameterInfo.put("status", shipment.getRstMsg()+",");
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					} else {
						parameterInfo.put("errorMsg", shipment.getRstMsg());
						parameterInfo.put("status", "배송 데이터 오류 발생,");
						shippingMapper.insertTbApiError(parameterInfo);
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					}
				}
				break;
			case "YT":
				jsonVal = shippingService.createRequestBody(parameterInfo);
				shipment = shippingService.createShipment(parameterInfo, jsonVal);
				if (shipment.getRstStatus().equals("SUCCESS")) {
					parameterInfo.put("hawbNo", shipment.getRstHawbNo());
					//shippingService.createLabel(parameterInfo);
					shippingService.applyShipment(parameterInfo);
				} else {
					returnVal = "F";
					if (shipment.getRstCode().equals("F")) {
						parameterInfo.put("status", shipment.getRstMsg()+",");
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					} else {
						parameterInfo.put("errorMsg", shipment.getRstMsg());
						parameterInfo.put("status", "배송 데이터 오류 발생,");
						shippingMapper.insertTbApiError(parameterInfo);
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					}
				}
				break;
			case "VNP":
				Order apiOrder = logisticMapper.selectTmpOrder(parameterInfo);
				apiOrder.dncryptData();
				apiOrder = vnpHandler.createOrder(apiOrder);
				if (apiOrder.getStatus().isEmpty()) {
					procRst = logisticMapper.execSpRegShipment(apiOrder);
					if ("SUCCESS".equals(procRst.getRstStatus().toUpperCase())) {
						commUtils.storeLabelFile(apiOrder);
					} else {
						vnpHandler.cancelOrder(apiOrder);
						returnVal = "F";
					}
				} else {
					logisticMapper.updateTmpOrderStatus(apiOrder);
				}
				break;
			default:
				selectBlApply(orderNno, userId, userIp);
				savePdf(orderNno, transCode, userId, userIp);
				break;
			}
			
			if (!returnVal.equals("F")) {
				ArrayList<HashMap<String, Object>> shopOrderList = new ArrayList<HashMap<String, Object>>();
				shopOrderList.add(parameterInfo);
				shippingService.updateShopFulfillService(shopOrderList);	
			}
			
		} catch (Exception e) {
			returnVal = "F";
		}
		
		return returnVal;
	}

	@Override
	public String comBlApplyCheckV2(String orderNno, String transCode, String userId, String userIp, String orderType, String uploadType) throws Exception {
		String returnVal = "S";
		
		try {
			String jsonVal = "";
			ProcedureVO rst = new ProcedureVO();
			ShipmentVO shipment = new ShipmentVO();
			ProcedureRst procRst = new ProcedureRst();
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			parameterInfo.put("nno", orderNno);
			parameterInfo.put("userId", userId);
			parameterInfo.put("userIp", userIp);
			parameterInfo.put("uploadType", uploadType);
			parameterInfo.put("transCode", transCode);
			
			switch (transCode) {
			case "ACI":
				selectBlApply(orderNno, userId, userIp);
				break;
			case "GTS":
				// gtsAPI.selectBlApplyGts(request,
				// orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
				selectBlApply(orderNno, userId, userIp);
				break;
			case "CSE":
				selectBlApply(orderNno, userId, userIp);
				break;
			case "ARA":// 등록 시, 데이터 값 중 전화번호, 이메일 등은 임의의 값으로 수정 하여 등록 하도록 수정. (check에서만!)
				usrService.selectBlApplyARA(orderNno, userId, userIp);
				break;
			case "YSL":
				ysApi.selectBlApplyYSLCheck(orderNno, userId, userIp);
				// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
				break;
			case "EFS":
				efsApi.selectBlApplyEFS(orderNno, userId, userIp);
				// usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
				break;
			case "FED":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
				break;
			case "FES":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FES");
				break;
			case "FEG":
				fedexApi.selectBlApplyFED(orderNno, userId, userIp, "FEG");
				break;
			case "OCS":
				ocsApi.selectBlApplyOCS(orderNno, userId, userIp);
				break;
			case "EPT":
				jsonVal = shippingService.createRequestBody(parameterInfo);
				shipment = shippingService.createShipment(parameterInfo, jsonVal);
				if (shipment.getRstStatus().equals("SUCCESS")) {
					parameterInfo.put("hawbNo", shipment.getRstHawbNo());
					shippingService.applyShipment(parameterInfo);
				} else {
					returnVal = "F";
					if (shipment.getRstCode().equals("F")) {
						parameterInfo.put("status", shipment.getRstMsg()+",");
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					} else {
						parameterInfo.put("errorMsg", shipment.getRstMsg());
						parameterInfo.put("status", "배송 데이터 오류 발생 [관리자 확인 필요],");
						shippingMapper.insertTbApiError(parameterInfo);
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					}
				}
				break;
			case "EMS":
				emsApi.selectBlApplyCheck(orderNno, userId, userIp);
				// krPostPdf(request, orderNno, transCode);
				break;
			case "EMN":
				emsApi.selectBlApplyCheck(orderNno, userId, userIp);
				// krPostPdf(request, orderNno, transCode);
				break;
			case "CJ":
				//selectBlApply(orderNno, userId, userIp);
				Order order = new Order();
				order.setNno(orderNno);
				order.setWUserId(userId);
				order.setWUserIp(userIp);
				order.setHawbNo("");
				ProcedureRst cjShipment = logisticMapper.execSpRegShipment(order);
				if ("FAIL".equals(cjShipment.getRstStatus())) {
					returnVal = "F";
				}
				break;
			case "ITC":
				itcApi.selectBlApplyITC(orderNno, userId, userIp);
				break;
			case "SEK":
				sekoApi.selectBlApplySEK(orderNno, userId, userIp);
				break;
			case "T86":
				t86Api.selectBlApplyT86(orderNno, userId, userIp);
				break;
			case "FB":
				fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
				break;
			case "FB-EMS":
				fbApi.selectBlApplyFB(orderNno, userId, userIp, transCode);
				break;
			case "HJ":
				hjApi.selectBlApply(orderNno, userId, userIp);
				break;
			case "YT":
				jsonVal = shippingService.createRequestBody(parameterInfo);
				shipment = shippingService.createShipment(parameterInfo, jsonVal);
				if (shipment.getRstStatus().equals("SUCCESS")) {
					parameterInfo.put("hawbNo", shipment.getRstHawbNo());
					shippingService.createLabel(parameterInfo);
					shippingService.applyShipment(parameterInfo);
				} else {
					returnVal = "F";
					if (shipment.getRstCode().equals("F")) {
						parameterInfo.put("status", shipment.getRstMsg()+",");
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					} else {
						parameterInfo.put("errorMsg", shipment.getRstMsg());
						parameterInfo.put("status", "배송 데이터 오류 발생 [관리자 확인 필요],");
						shippingMapper.insertTbApiError(parameterInfo);
						shippingMapper.updateTmpOrderListStatus(parameterInfo);
					}
				}
				break;
			case "VNP":
				Order apiOrder = logisticMapper.selectTmpOrder(parameterInfo);
				apiOrder.dncryptData();
				apiOrder = vnpHandler.createOrder(apiOrder);
				if (apiOrder.getStatus().isEmpty()) {
					procRst = logisticMapper.execSpRegShipment(apiOrder);
					if ("SUCCESS".equals(procRst.getRstStatus().toUpperCase())) {
						commUtils.storeLabelFile(apiOrder);
					} else {
						vnpHandler.cancelOrder(apiOrder);
						returnVal = "F";
					}
				} else {
					logisticMapper.updateTmpOrderStatus(apiOrder);
				}
				break;
			default:
				selectBlApply(orderNno, userId, userIp);
				break;
			}
			
			if (!returnVal.equals("F")) {
				ArrayList<HashMap<String, Object>> shopOrderList = new ArrayList<HashMap<String, Object>>();
				shopOrderList.add(parameterInfo);
				shippingService.updateShopFulfillService(shopOrderList);
			}
		
		} catch (Exception e) {
			returnVal = "F";
		}
			
		return returnVal;
	}

	@Override
	public void printEpostPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) throws Exception {
		String labelSize = "C";
		/*
		if (request.getParameter("labelType") != null) {
			labelSize = request.getParameter("labelType");
		}*/
		String jsonData = "";
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("ACIKey", "3AF35C5E33E439E612576618CFAAE1C11");
		String httpUrl = "";
		
		if (labelSize.equals("")) {
			httpUrl = "https://pod.acieshop.com/api/epostbl/blprint_epost_46.php";
		} else {
			httpUrl = "https://pod.acieshop.com/api/epostbl/blprint_epost_C.php";
		} 
		
		String fileName = URLEncoder.encode("tempPdf", "UTF-8");
		String imgPath = realFilePath + "image/" + "aramex/";
		
		PDDocument document = new PDDocument();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		
		try {
			
			
			for (int i = 0; i < orderNnoList.size(); i++) {
				document = new PDDocument();
				
				parameterInfo = new HashMap<String, Object>();
				String nno = orderNnoList.get(i);
				parameterInfo.put("nno", nno);
				
				PdfPrintVO orderInfo = new PdfPrintVO();
				orderInfo = mapper.selectTempInfo(nno, "EPT");
				orderInfo.dncryptData();
				
				LinkedHashMap<String, Object> printJson = new LinkedHashMap<String, Object>();
				printJson.put("HAWBNO", orderInfo.getHawbNo());
				printJson.put("CNEEZIP", orderInfo.getCneeZip());
				printJson.put("ORDERNO", orderInfo.getOrderNo());
				printJson.put("CNEENAME", orderInfo.getCneeName());
				String cneeTel = orderInfo.getCneeTel().replaceAll("[^0-9]", "");
				printJson.put("CNEETEL", cneeTel);
				String cneeHp = orderInfo.getCneeHp().replaceAll("[^0-9]", "");
				printJson.put("CNEEHP", cneeHp);
				printJson.put("CNEEADDR1", orderInfo.getCneeAddr());
				printJson.put("CNEEADDR2", orderInfo.getCneeAddrDetail());
				printJson.put("SHIPPERNAME", orderInfo.getComName());
				printJson.put("USERID", "WMS");
				printJson.put("SHIPPERTEL", orderInfo.getShipperTel());
				String shipperAddress = orderInfo.getShipperAddr();
				if (!orderInfo.getShipperAddrDetail().equals("")) {
					shipperAddress = shipperAddress + " " + orderInfo.getShipperAddrDetail();
				}
				printJson.put("SHIPPERADDR", shipperAddress);
				printJson.put("DLVCOMMENT", orderInfo.getDlvReqMsg());
				
				
				int itemCnt = Integer.parseInt(orderInfo.getItemCnt());
				itemCnt = itemCnt - 1;
				String itemDetail = orderInfo.getItemDetail();
				
				if (itemCnt > 1) {
					itemDetail += " 외 "+itemCnt+"개";
				}
				printJson.put("ITEMDETAIL", itemDetail);
				//printJson.put("RETURN_ADDR", "인천 계양구 계산새로 76(계산동, 인천계양우체국) 사서함 1277호 ACI WMS");
				printJson.put("RETURN_ADDR", "");
				printJson.put("BLFORM_ITEM", "Y");
				
				Gson gson = new Gson();
				jsonData = gson.toJson(printJson);
				System.out.println(jsonData);
				byte[] pdfFile = apiPostForPdf(jsonData, httpUrl, apiHeader);
				FileOutputStream fos = new FileOutputStream(realFilePath+nno+".pdf");
				
				fos.write(pdfFile);
		        fos.close();
		        
		        File file = new File(realFilePath+nno+".pdf");
				pdfMerger.addSource(file);
				
			}

			pdfMerger.setDestinationStream(response.getOutputStream());
			pdfMerger.mergeDocuments(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] apiPostForPdf(String reqVal, String httpUrl, LinkedHashMap<String, Object> apiHeader) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setDefaultUseCaches(false);

			if (apiHeader != null && !apiHeader.isEmpty()) {
				for (String key : apiHeader.keySet()) {
					Object value = apiHeader.get(key);
					connection.setRequestProperty(key, value.toString());
				}
			}

			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(reqVal);
			wr.flush();
			
	        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            InputStream in = connection.getInputStream();
	            
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = in.read(buffer)) > -1) {
	                baos.write(buffer, 0, len);
	            }
	            baos.flush();

	            in.close();
	            wr.close();
	            connection.disconnect();
	            
	            return baos.toByteArray();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<HashMap<String, Object>> makeEpostPodDetatailArray(String hawbNo, HttpServletRequest request) throws Exception {
		String authKey = "3a18c0024fe0c7fa81703204460327";
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		String url = "http://biz.epost.go.kr/KpostPortal/openapi?regkey="+authKey+"&target=trace&query="+hawbNo;
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		apiHeader.put("Connection", "keep-alive");
		apiHeader.put("Host", "ship.epost.go.kr");
		apiHeader.put("User-Agent", "Apache-HttpClient/4.5.1 (Java/1.8.0_91)");
		ApiAction action = ApiAction.getInstance();
		String responseStr = action.sendGet(url, podDetatil);

		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(responseStr)));
			
			NodeList itemList = doc.getElementsByTagName("item");
			for (int i = itemList.getLength()-1; i > -1; i--) {
				podDetatil  = new LinkedHashMap<String,Object>();
				Node item = itemList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					
					String location = element.getElementsByTagName("eventregiponm").item(0).getTextContent();
					String description = element.getElementsByTagName("tracestatus").item(0).getTextContent();
					String date = element.getElementsByTagName("sortingdate").item(0).getTextContent();
					String time = element.getElementsByTagName("eventhms").item(0).getTextContent();
					
					date = date.replace(".", "-");
					String dateTime = date+" "+time;
					
					if (description.equals("발송")) {
						podDetatil.put("UpdateCode", "400");
						podDetatil.put("UpdateDateTime", dateTime);
						podDetatil.put("UpdateLocation", location);
						podDetatil.put("UpdateDescription", description);
					} else if (description.equals("배달준비")) {
						podDetatil.put("UpdateCode", "500");
						podDetatil.put("UpdateDateTime", dateTime);
						podDetatil.put("UpdateLocation", location);
						podDetatil.put("UpdateDescription", description);
						
					} else if (description.equals("배달완료")) {
						podDetatil.put("UpdateCode", "600");
						podDetatil.put("UpdateDateTime", dateTime);
						podDetatil.put("UpdateLocation", location);
						podDetatil.put("UpdateDescription", description);
					} else {
						continue;
					}
				}
				
				podDetatailArray.add(podDetatil);
			}
			
			String hawbInDate = apiMapper.selectHawbInDate(hawbNo);		// 입고
			String mawbInDate = apiMapper.selectMawbInDate(hawbNo);		// 출고
			String regInDate = apiMapper.selectRegInDate(hawbNo);		// 주문등록
			
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "300"); 
			podDetatil.put("UpdateDateTime", mawbInDate);
			podDetatil.put("UpdateLocation", "Korea, (주) 에이씨아이 월드와이드");
			podDetatil.put("UpdateDescription", "출고 완료");	
			podDetatailArray.add(podDetatil);
			
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "200"); 
			podDetatil.put("UpdateDateTime", hawbInDate);
			podDetatil.put("UpdateLocation", "Korea, (주) 에이씨아이 월드와이드");
			podDetatil.put("UpdateDescription", "입고 완료");	
			podDetatailArray.add(podDetatil);
			
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "100"); 
			podDetatil.put("UpdateDateTime", regInDate);
			podDetatil.put("UpdateLocation", "Korea");
			podDetatil.put("UpdateDescription", "입고 대기 (주문 접수)");	
			podDetatailArray.add(podDetatil);
			
		} catch (Exception E) {
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode", "-200"); 
			podDetatil.put("UpdateDateTime", "");
			podDetatil.put("UpdateLocation", "");
			podDetatil.put("UpdateDescription", "데이터가 없습니다.(No Data)");
			podDetatailArray.add(podDetatil);
		}
		
		
		return podDetatailArray;
	}
	
	
	@Override
	public void createLabel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType, String orderType) {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		
		
		try {
			
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
			
			// 2023.01.19 추가
			PDFMergerUtility merger = new PDFMergerUtility();
			
			String barcodePath = pdfPath2 + userId + ".JPEG";
			String markPath = pdfPath + "/mark/aciMark.jpg";
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
				pdfPrintInfo = mapper.selectPdfPrintInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				String blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
				//String blType = "B";
				if ("".equals(blType)) {
					blType = "C";
				}
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					if (blType.equals("A")) {
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());
	
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
	
						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
						// 배경이미지 로드
	
						barcodes.setBarHeight(70);
						barcodes.setBarWidth(50);
	
						barcodes.setLabel("Barcode creation test...");
						barcodes.setDrawingText(true);
	
						float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
						int fontSize = 12; // Or whatever font size you want.
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
								45f);
	
						/* 사각형 */
						contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								152 * perMM - 149 * perMM);
						contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								152 * perMM - 3 * perMM);
						contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
						contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
								(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);
	
						// 1st Vertical Line
						contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
						// 1st horizontal Line
						// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
						// 2nd horizontal Line
						contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);
	
						// 3rd horizontal Line
						contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);
	
						// 2nd Vertical Line
						contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
						// 3rd Vertical Line
						contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);
	
						// 4th horizontal Line
						contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);
	
						/* 글자 표기 */
						drawText("Piece(s)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
						fontSize = 20;
						titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
						drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 7 * perMM, 115 * perMM,
								contentStream);
	
						fontSize = 17;
						drawText("Weight", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);
	
						fontSize = 8;
						titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
						drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
						// Order No.
						fontSize = 7;
						drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
						fontSize = 12;
						drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);
	
						fontSize = 7;
						drawText("Origin", ARIAL, fontSize, 10 * perMM, 106 * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 15 * perMM, 100 * perMM, contentStream);
						drawText("========>", ARIAL, fontSize, 34 * perMM, 100 * perMM, contentStream);
	
						fontSize = 7;
						drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);
	
						// Shipper
						// 5th horizontal Line
						contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
						fontSize = 9;
						drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
						// 6th horizontal Line
						contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);
	
						fontSize = 7;
						drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
								contentStream);
	
						fontSize = 7;
						drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
						drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
						drawText("State : ", ARIAL, fontSize, 60 * perMM, 78 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 60 * perMM + titleWidth, 78 * perMM,
								contentStream);
	
						fontSize = 7;
						drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
						drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 74 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
						// pageStandard.getHeight()-151,contentStream);
						drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 74 * perMM,
								contentStream, 2);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
						drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
						// pageStandard.getHeight()-151,contentStream);
						drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
								fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);
	
						// receiver
						// 7th horizontal Line
						contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);
	
						// Receiver
						fontSize = 9;
						drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
						// 6th horizontal Line
						contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);
	
						fontSize = 7;
						drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
								contentStream);
	
						fontSize = 7;
						drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
						drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
						drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
								contentStream);
	
						fontSize = 7;
						drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
								contentStream);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
						drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
						fontSize = 9;
						drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
								contentStream, 2);
	
						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
						drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
						fontSize = 9;
						drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
								5 * perMM + titleWidth, 26 * perMM, contentStream, 4);
	
						// 바코드
						contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
						
					} else if (blType.equals("B")) {
						ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
						itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
						String hawbNo = pdfPrintInfo.getHawbNo();
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						float pageWidth = pageStandard.getWidth();
						float pageHeight = pageStandard.getHeight();
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
						hawbBarcode.setBarHeight(70);
						hawbBarcode.setDrawingQuietSection(false);
						
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);
	
						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						
						// aci 로고
						//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

						int fontSize = 15;
						float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;

						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						
						// HawbNo 바코드
						int barcodeW = pdImage.getWidth();
						System.out.println(barcodeW);
						int barcodeH = pdImage.getHeight();
						System.out.println(barcodeH);
						//int pdfWidth = 160;
						//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
						int pdfHeight = 45;
						int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
						
						float leftX = 5;
						float rightX = pageWidth - 5;
						
						float centerX = (rightX - leftX - pdfWidth) / 2 + leftX;
						
						//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
						//contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						contentStream.drawImage(pdImage, centerX, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						
						//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
						contentStream.drawLine(5, 5, 5, (pageHeight - 5));
						contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
						contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
						contentStream.drawLine((pageWidth - 5), 5, 5, 5);
						
						
						PDExtendedGraphicsState graphicsState  = new PDExtendedGraphicsState();
						if (pdfPrintInfo.getUserId().toLowerCase().equals("pickpack")) {
							graphicsState.setNonStrokingAlphaConstant(0.3f);

							int ftSize = 15;
							String textString = "PickPack Worldwide LLC";
							float ftWidth = ARIALBOLD.getStringWidth(textString) / 1000 * ftSize;
							contentStream.beginText();
							contentStream.newLineAtOffset(((pageWidth - 10) - ftWidth), 10);
							contentStream.setFont(ARIALBOLD, ftSize);
							contentStream.setGraphicsStateParameters(graphicsState);
							contentStream.showText(textString);
							contentStream.endText();
						}
						
						PDExtendedGraphicsState graphicsState2  = new PDExtendedGraphicsState();
						graphicsState2.setNonStrokingAlphaConstant(1f);
						contentStream.setGraphicsStateParameters(graphicsState2);

						float xPoint = 5;
						float yPoint = (152 * perMM - 21 * perMM) - 7;
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						xPoint = xPoint + 5;
						
						String contentText = "";
						contentText = hawbNo;
						float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						
						yPoint = yPoint - textHeight;
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint + 2);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 14;
						contentText = pdfPrintInfo.getOrderNo();
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint = yPoint - textHeight;
						
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 10;
						contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						xPoint = (pageWidth - 10) - textWidth;


						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, 342);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						contentStream.drawLine(5, 335, (pageWidth - 5), 335);
						
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint -= (textHeight + 3);
						xPoint = 10;
						contentText = pdfPrintInfo.getShipperName();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperTel();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						float startX = xPoint;
						float startY = yPoint;
						float currentX = startX;
						float currentY = startY;
						float widthLimit = 260;
						float leading = textHeight;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						String[] words = contentText.split(" ");
						
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY - 5;
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getCneeName();
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(contentText);
						contentStream.endText();
						
						String cneeTel = pdfPrintInfo.getCneeTel();
						if (cneeTel.equals("") ) {
							cneeTel = pdfPrintInfo.getCneeHp();
						}
						
						yPoint -= textHeight;
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(cneeTel);
						contentStream.endText();
						
						yPoint -= textHeight;
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
						if (!pdfPrintInfo.getCneeCity().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
						}
						if (!pdfPrintInfo.getCneeState().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeState()+" ";
						}
						cityStateInfo += pdfPrintInfo.getDstnNation();

						words = cityStateInfo.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint -= textHeight;
						String cneeAddr = pdfPrintInfo.getCneeAddr();
						if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
							cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
						}
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						words = cneeAddr.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = (currentY - 5);
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= 12;
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
							String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
							int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
							String itemText = itemDetail + " / " + itemCnt;
							
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							
							String[] itemWords = itemText.split(" ");
							
							for (String word : itemWords) {
								float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
								if (currentX + wordWidth > startX + widthLimit) {
									currentX = startX;
									currentY -= leading;
									contentStream.newLineAtOffset(0, -leading);
								}
								contentStream.showText(word + " ");
								currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
							}
							
							contentStream.endText();
							
							currentY -= leading;
							
						}
						
						
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
					} else if (blType.equals("C")) {
						ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
						itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
						String hawbNo = pdfPrintInfo.getHawbNo();
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						float pageWidth = pageStandard.getWidth();
						float pageHeight = pageStandard.getHeight();
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
						hawbBarcode.setBarHeight(70);
						hawbBarcode.setDrawingQuietSection(false);
						
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);
	
						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						
						// aci 로고
						//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

						int fontSize = 15;
						float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;
						
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						
						// HawbNo 바코드
						int barcodeW = pdImage.getWidth();
						int barcodeH = pdImage.getHeight();
						//int pdfWidth = 160;
						//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
						int pdfHeight = 45;
						int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
						
						float imageX = (pageWidth - pdfWidth) / 2;
						float dstnHeight = (((152 * perMM - 21 * perMM) - 7) - (pageHeight - 5)) / 2;
						
						String dstnNation = pdfPrintInfo.getDstnNation();
						float dstnNationWidth = ARIALBOLD.getStringWidth(dstnNation) / 1000 * fontSize;
						float dstnLineWidth = dstnNationWidth + 15;
						
						//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
						contentStream.drawImage(pdImage, 23, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						
						//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
						contentStream.drawLine(5, 5, 5, (pageHeight - 5));
						contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
						contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
						contentStream.drawLine((pageWidth - 5), 5, 5, 5);
						
						float xPoint = 5;
						float yPoint = (152 * perMM - 21 * perMM) - 7;
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint, ((pageWidth - 5) - dstnLineWidth), (pageHeight - 5));
						contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint + 30, (pageWidth - 5), yPoint + 30);
						
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 7.5f, yPoint + 10);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(pdfPrintInfo.getDstnNation());
						contentStream.endText();
						
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 5, yPoint + 40);
						contentStream.setFont(ARIAL, 13);
						contentStream.showText("Dest");
						contentStream.endText();
						
												
						xPoint = xPoint + 5;
						
						String contentText = "";
						contentText = hawbNo;
						float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						
						yPoint = yPoint - textHeight;
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint + 2);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 14;
						contentText = pdfPrintInfo.getOrderNo();
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						yPoint = yPoint - textHeight;
						
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						String orderDate = pdfPrintInfo.getOrderDate();
						orderDate = "(" + orderDate + ")";
						
						fontSize = 10;
						xPoint = (xPoint + 7) + textWidth;

						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(orderDate);
						contentStream.endText();
						
						//fontSize = 10;
						contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						xPoint = (pageWidth - 10) - textWidth;


						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, 342);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						contentStream.drawLine(5, 335, (pageWidth - 5), 335);
						
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint -= (textHeight + 3);
						xPoint = 10;
						contentText = pdfPrintInfo.getShipperName();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperTel();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						float startX = xPoint;
						float startY = yPoint;
						float currentX = startX;
						float currentY = startY;
						float widthLimit = 260;
						float leading = textHeight;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						String[] words = contentText.split(" ");
						
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY - 5;
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getCneeName();
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(contentText);
						contentStream.endText();
						
						String cneeTel = pdfPrintInfo.getCneeTel();
						if (cneeTel.equals("") ) {
							cneeTel = pdfPrintInfo.getCneeHp();
						}
						
						yPoint -= textHeight;
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(cneeTel);
						contentStream.endText();
						
						yPoint -= textHeight;
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
						if (!pdfPrintInfo.getCneeCity().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
						}
						if (!pdfPrintInfo.getCneeState().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeState()+" ";
						}
						cityStateInfo += pdfPrintInfo.getDstnNation();

						words = cityStateInfo.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY;
						
						yPoint -= textHeight;
						String cneeAddr = pdfPrintInfo.getCneeAddr();
						if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
							cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
						}
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						words = cneeAddr.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = (currentY - 5);
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= 12;
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						// startX ~ widthLimit

						for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
							String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
							int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
							String itemText = itemDetail + " / " + itemCnt;
							float wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
							
							String trimStr = "";
							if (wordWidth <= widthLimit) {
								trimStr = itemText;
							} else {
								float trimRatio = widthLimit / wordWidth;
								int trimmedLength = (int) (itemText.length() * trimRatio);
								trimStr = itemText.substring(0, trimmedLength);	
							}
							
							while (wordWidth > widthLimit) {
								contentStream.beginText();
								contentStream.setFont(ARIAL, fontSize);
								contentStream.newLineAtOffset(startX, currentY);
								contentStream.showText(trimStr);
								contentStream.endText();
								
								currentY -= leading;
								
								itemText = itemText.substring(trimStr.length());
								wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
							}
							
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							contentStream.showText(itemText);
							contentStream.endText();
							
							currentY -= leading;
						}
						
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
					}
				}
			}
			

			// 파일 다운로드 설정
			response.setContentType("application/pdf");
			String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

			// PDF 파일 출력
			doc.save(response.getOutputStream());
			doc.close();
			File barcodefile = new File(barcodePath);
			if (barcodefile.exists()) {
				barcodefile.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void sendEmailToSeller(HashMap<String, Object> parameters) throws Exception {
		
		String sellerEmail = "";
		
		try {
			
			ManagerVO userInfo = new ManagerVO();
			userInfo = mapper.selectUserInfoByUserId(parameters);
			userInfo.dncryptData();
			sellerEmail = userInfo.getUserEmail();
			
			if (sellerEmail.equals("")) {
				return;
			}
			
			smtpService.sendEmailToSeller(sellerEmail);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void createLabelForApi(String orderNno, String printType, String userId, String userIp) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;

			pdfPrintInfo = mapper.selectPdfPrintInfo(orderNno, printType);
			pdfPrintInfo.dncryptData();
			String blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
			
			if ("".equals(blType)) {
				blType = "C";
			}
			
			for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
				
				if (blType.equals("A")) {
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
					// 배경이미지 로드

					barcodes.setBarHeight(70);
					barcodes.setBarWidth(50);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
					int fontSize = 12; // Or whatever font size you want.
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
							45f);

					/* 사각형 */
					contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 149 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							152 * perMM - 3 * perMM);
					contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
					contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
							(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

					// 1st Vertical Line
					contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
					// 1st horizontal Line
					// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
					// 2nd horizontal Line
					contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

					// 3rd horizontal Line
					contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

					// 2nd Vertical Line
					contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
					// 3rd Vertical Line
					contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

					// 4th horizontal Line
					contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);

					/* 글자 표기 */
					drawText("Piece(s)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
					fontSize = 20;
					titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
					drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 7 * perMM, 115 * perMM,
							contentStream);

					fontSize = 17;
					drawText("Weight", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

					fontSize = 8;
					titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
					drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
					// Order No.
					fontSize = 7;
					drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
					fontSize = 12;
					drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

					fontSize = 7;
					drawText("Origin", ARIAL, fontSize, 10 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 15 * perMM, 100 * perMM, contentStream);
					drawText("========>", ARIAL, fontSize, 34 * perMM, 100 * perMM, contentStream);

					fontSize = 7;
					drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
					fontSize = 15;
					drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

					// Shipper
					// 5th horizontal Line
					contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
					fontSize = 9;
					drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 60 * perMM, 78 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 60 * perMM + titleWidth, 78 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 74 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 74 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
					fontSize = 9;
					// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
					// pageStandard.getHeight()-151,contentStream);
					drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
							fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

					// receiver
					// 7th horizontal Line
					contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

					// Receiver
					fontSize = 9;
					drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
					// 6th horizontal Line
					contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

					fontSize = 7;
					drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
							contentStream);

					fontSize = 7;
					drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
					drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
					drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
							contentStream);

					fontSize = 7;
					drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
					titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
					fontSize = 9;
					drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
							contentStream);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
					drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
							contentStream, 2);

					fontSize = 7;
					titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
					drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
					fontSize = 9;
					drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
							5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

					// 바코드
					contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
					
				} else if (blType.equals("B")) {
					ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
					itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
					String hawbNo = pdfPrintInfo.getHawbNo();
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					float pageWidth = pageStandard.getWidth();
					float pageHeight = pageStandard.getHeight();
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
					hawbBarcode.setBarHeight(70);
					hawbBarcode.setDrawingQuietSection(false);
					
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					
					// aci 로고
					//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

					int fontSize = 15;
					float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;

					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					
					// HawbNo 바코드
					int barcodeW = pdImage.getWidth();
					System.out.println(barcodeW);
					int barcodeH = pdImage.getHeight();
					System.out.println(barcodeH);
					//int pdfWidth = 160;
					//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
					int pdfHeight = 45;
					int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
					
					float leftX = 5;
					float rightX = pageWidth - 5;
					
					float centerX = (rightX - leftX - pdfWidth) / 2 + leftX;
					
					//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
					//contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					contentStream.drawImage(pdImage, centerX, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					
					//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
					contentStream.drawLine(5, 5, 5, (pageHeight - 5));
					contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
					contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
					contentStream.drawLine((pageWidth - 5), 5, 5, 5);
					
					
					PDExtendedGraphicsState graphicsState  = new PDExtendedGraphicsState();
					if (pdfPrintInfo.getUserId().toLowerCase().equals("pickpack")) {
						graphicsState.setNonStrokingAlphaConstant(0.3f);

						int ftSize = 15;
						String textString = "PickPack Worldwide LLC";
						float ftWidth = ARIALBOLD.getStringWidth(textString) / 1000 * ftSize;
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 10) - ftWidth), 10);
						contentStream.setFont(ARIALBOLD, ftSize);
						contentStream.setGraphicsStateParameters(graphicsState);
						contentStream.showText(textString);
						contentStream.endText();
					}
					
					PDExtendedGraphicsState graphicsState2  = new PDExtendedGraphicsState();
					graphicsState2.setNonStrokingAlphaConstant(1f);
					contentStream.setGraphicsStateParameters(graphicsState2);

					float xPoint = 5;
					float yPoint = (152 * perMM - 21 * perMM) - 7;
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					xPoint = xPoint + 5;
					
					String contentText = "";
					contentText = hawbNo;
					float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					
					yPoint = yPoint - textHeight;
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint + 2);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 14;
					contentText = pdfPrintInfo.getOrderNo();
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint = yPoint - textHeight;
					
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 10;
					contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					xPoint = (pageWidth - 10) - textWidth;


					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, 342);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					contentStream.drawLine(5, 335, (pageWidth - 5), 335);
					
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint -= (textHeight + 3);
					xPoint = 10;
					contentText = pdfPrintInfo.getShipperName();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperTel();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					float startX = xPoint;
					float startY = yPoint;
					float currentX = startX;
					float currentY = startY;
					float widthLimit = 260;
					float leading = textHeight;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					String[] words = contentText.split(" ");
					
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY - 5;
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getCneeName();
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(contentText);
					contentStream.endText();
					
					String cneeTel = pdfPrintInfo.getCneeTel();
					if (cneeTel.equals("") ) {
						cneeTel = pdfPrintInfo.getCneeHp();
					}
					
					yPoint -= textHeight;
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(cneeTel);
					contentStream.endText();
					
					yPoint -= textHeight;
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
					if (!pdfPrintInfo.getCneeCity().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
					}
					if (!pdfPrintInfo.getCneeState().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeState()+" ";
					}
					cityStateInfo += pdfPrintInfo.getDstnNation();

					words = cityStateInfo.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint -= textHeight;
					String cneeAddr = pdfPrintInfo.getCneeAddr();
					if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
						cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
					}
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					words = cneeAddr.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = (currentY - 5);
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= 12;
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
						String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
						int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
						String itemText = itemDetail + " / " + itemCnt;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, currentY);
						
						String[] itemWords = itemText.split(" ");
						
						for (String word : itemWords) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						currentY -= leading;
						
					}
					
					
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
				} else if (blType.equals("C")) {
					ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
					itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
					String hawbNo = pdfPrintInfo.getHawbNo();
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);
					
					float pageWidth = pageStandard.getWidth();
					float pageHeight = pageStandard.getHeight();
					
					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
					hawbBarcode.setBarHeight(70);
					hawbBarcode.setDrawingQuietSection(false);
					
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

					File markFile = new File(markPath);
					PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
					
					// aci 로고
					//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

					int fontSize = 15;
					float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;
					
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
					
					// HawbNo 바코드
					int barcodeW = pdImage.getWidth();
					int barcodeH = pdImage.getHeight();
					//int pdfWidth = 160;
					//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
					int pdfHeight = 45;
					int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
					
					float imageX = (pageWidth - pdfWidth) / 2;
					float dstnHeight = (((152 * perMM - 21 * perMM) - 7) - (pageHeight - 5)) / 2;
					
					String dstnNation = pdfPrintInfo.getDstnNation();
					float dstnNationWidth = ARIALBOLD.getStringWidth(dstnNation) / 1000 * fontSize;
					float dstnLineWidth = dstnNationWidth + 15;
					
					//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
					contentStream.drawImage(pdImage, 23, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
					
					//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
					contentStream.drawLine(5, 5, 5, (pageHeight - 5));
					contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
					contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
					contentStream.drawLine((pageWidth - 5), 5, 5, 5);
					
					float xPoint = 5;
					float yPoint = (152 * perMM - 21 * perMM) - 7;
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint, ((pageWidth - 5) - dstnLineWidth), (pageHeight - 5));
					contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint + 30, (pageWidth - 5), yPoint + 30);
					
					contentStream.beginText();
					contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 7.5f, yPoint + 10);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(pdfPrintInfo.getDstnNation());
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 5, yPoint + 40);
					contentStream.setFont(ARIAL, 13);
					contentStream.showText("Dest");
					contentStream.endText();
					
											
					xPoint = xPoint + 5;
					
					String contentText = "";
					contentText = hawbNo;
					float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					
					yPoint = yPoint - textHeight;
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint + 2);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					fontSize = 14;
					contentText = pdfPrintInfo.getOrderNo();
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					yPoint = yPoint - textHeight;
					
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIALBOLD, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					String orderDate = pdfPrintInfo.getOrderDate();
					orderDate = "(" + orderDate + ")";
					
					fontSize = 10;
					xPoint = (xPoint + 7) + textWidth;

					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint - 4);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(orderDate);
					contentStream.endText();
					
					//fontSize = 10;
					contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					xPoint = (pageWidth - 10) - textWidth;


					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, 342);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					contentStream.drawLine(5, 335, (pageWidth - 5), 335);
					
					textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
					yPoint -= (textHeight + 3);
					xPoint = 10;
					contentText = pdfPrintInfo.getShipperName();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();
					
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperTel();
					contentStream.beginText();
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.setFont(ARIAL, fontSize);
					contentStream.showText(contentText);
					contentStream.endText();

					yPoint -= textHeight;
					contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
					textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
					float startX = xPoint;
					float startY = yPoint;
					float currentX = startX;
					float currentY = startY;
					float widthLimit = 260;
					float leading = textHeight;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					String[] words = contentText.split(" ");
					
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY - 5;
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= textHeight;
					contentText = pdfPrintInfo.getCneeName();
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(contentText);
					contentStream.endText();
					
					String cneeTel = pdfPrintInfo.getCneeTel();
					if (cneeTel.equals("") ) {
						cneeTel = pdfPrintInfo.getCneeHp();
					}
					
					yPoint -= textHeight;
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(xPoint, yPoint);
					contentStream.showText(cneeTel);
					contentStream.endText();
					
					yPoint -= textHeight;
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;

					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
					if (!pdfPrintInfo.getCneeCity().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
					}
					if (!pdfPrintInfo.getCneeState().equals("")) {
						cityStateInfo += pdfPrintInfo.getCneeState()+" ";
					}
					cityStateInfo += pdfPrintInfo.getDstnNation();

					words = cityStateInfo.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = currentY;
					
					yPoint -= textHeight;
					String cneeAddr = pdfPrintInfo.getCneeAddr();
					if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
						cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
					}
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					contentStream.beginText();
					contentStream.setFont(ARIAL, fontSize);
					contentStream.newLineAtOffset(startX, startY);
					
					words = cneeAddr.split(" ");
					for (String word : words) {
						float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
						if (currentX + wordWidth > startX + widthLimit) {
							currentX = startX;
							currentY -= leading;
							contentStream.newLineAtOffset(0, -leading);
						}
						contentStream.showText(word + " ");
						currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
					}
					
					contentStream.endText();
					
					yPoint = (currentY - 5);
					
					contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
					
					yPoint -= 12;
					
					startX = xPoint;
					startY = yPoint;
					currentX = startX;
					currentY = startY;
					
					// startX ~ widthLimit

					for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
						String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
						int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
						String itemText = itemDetail + " / " + itemCnt;
						float wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
						
						String trimStr = "";
						if (wordWidth <= widthLimit) {
							trimStr = itemText;
						} else {
							float trimRatio = widthLimit / wordWidth;
							int trimmedLength = (int) (itemText.length() * trimRatio);
							trimStr = itemText.substring(0, trimmedLength);	
						}
						
						while (wordWidth > widthLimit) {
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							contentStream.showText(trimStr);
							contentStream.endText();
							
							currentY -= leading;
							
							itemText = itemText.substring(trimStr.length());
							wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
						}
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, currentY);
						contentStream.showText(itemText);
						contentStream.endText();
						
						currentY -= leading;
					}
					
					// 컨텐츠 스트림 닫기
					contentStream.close();
					
					totalPage++;
					
				}

				
			}
			// 페이지 추가
			
			doc.save(realFilePath + orderNno + "PDF.pdf");
//			PrinterJob job = PrinterJob.getPrinterJob();
//			job.setPageable(new PDFPageable(doc));
//			job.print();
			doc.close();
			saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
			File barcodefile = new File(barcodePath);
			if (barcodefile.exists()) {
				barcodefile.delete();
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
	}

	@Override
	public void yslPdfV2(String orderNno, String printType, String userId, String userIp, String blTypeOrg) throws Exception {
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
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
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
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

		// 페이지 생성 후 PDF 만들기.
		try {
			List<PDFont> fonts = new ArrayList<>();
			fonts.add(MsGothic);
			fonts.add(ARIAL);
			fonts.add(ARIALBOLD);
			fonts.add(NanumGothic);
			int totalPage = 0;
			
			String blType = "default";

			
			pdfPrintInfo = mapper.selectTempInfo(orderNno, printType);
			if (pdfPrintInfo == null) {
				return;
			}
			pdfPrintInfo.dncryptData();
			
			
			if (blType.equals("default")) {
				blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
			}
			

			if (blType.equals("A")) {
				PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);

				// 현재 페이지 설정
				//PDPage page = doc.getPage(i);
				PDPage page = doc.getPage(totalPage);

				// 컨텐츠 스트림 열기
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

				barcodes2.setSize(400, 800);
				barcodes2.setBarHeight(0);
				barcodes2.setBarWidth(0);

				barcodes2.setLabel("Barcode creation test...");
				barcodes2.setDrawingText(true);

				File barcodefile2 = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

				PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

				int fontSize = 10; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")")
						/ 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 38, 200f, 30f);

				contentStream.drawImage(pdImage2, (80 * perMM - 200) / 2, 7, 200f, 15f);

				drawText(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
						(80 * perMM - 80), 30 * perMM - 11, contentStream);

				fontSize = 9;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo() + " ▽ "+pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, 10 * perMM, 26, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
				drawText(pdfPrintInfo.getHawbNo2() + " ▽", NanumGothic, fontSize, (10 * perMM), 30 * perMM - 11,
						contentStream);

				// 컨텐츠 스트림 닫기
				contentStream.close();
				totalPage++;
			} else if (blType.equals("B")) {
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					// 페이지 추가
					PDRectangle pageStandard = new PDRectangle(100 * perMM, 55 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);

					contentStream.drawLine(3 * perMM, 3 * perMM, (pageStandard.getWidth()) - 3 * perMM, 3 * perMM);
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight() - 3 * perMM),
							(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight() - 3 * perMM));
					contentStream.drawLine(97 * perMM, 3 * perMM, 97 * perMM,
							(pageStandard.getHeight()) - 3 * perMM);
					contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageStandard.getHeight()) - 3 * perMM);

					// barcode1
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createCode128(pdfPrintInfo.getHawbNo2());
					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);
					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					// barcode2
					net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory
							.createCode128(pdfPrintInfo.getHawbNo());
					barcodes2.setSize(400, 800);
					barcodes2.setBarHeight(0);
					barcodes2.setBarWidth(0);
					barcodes2.setLabel("Barcode creation test...");
					barcodes2.setDrawingText(true);
					File barcodefile2 = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);
					PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

					// barcode print
					contentStream.drawImage(pdImage, (100 * perMM - 48 * perMM) / 2,
							(pageStandard.getHeight()) - 15 * perMM, 200f, 30f);
					contentStream.drawImage(pdImage2, (100 * perMM - 48 * perMM) / 2,
							(pageStandard.getHeight()) - 27 * perMM, 200f, 15f);

					int fontSize = 10; // Or whatever font size you want.
					float titleWidth = NanumGothic
							.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")") / 1000
							* fontSize;

					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo() + " △", NanumGothic, fontSize, 48 * perMM, 24 * perMM,
							contentStream);

					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo2() + "  △", NanumGothic, fontSize, (46 * perMM), 36 * perMM,
							contentStream);

					// 바코드 아래 가로
					contentStream.drawLine(3 * perMM, 22 * perMM, (pageStandard.getWidth()) - 3 * perMM,
							22 * perMM);
					// 바코드 왼쪽 세로
					contentStream.drawLine(27 * perMM, 22 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 3 * perMM);
					// 바코드 왼쪽 위에 가로
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 10 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 10 * perMM);

					// 입고 배송업체 및 개수
					// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
					// fontSize, 6*perMM, (pageStandard.getHeight()-8*perMM),contentStream);
					// 배송국가 header
					// drawText(printType+"입고 ("+pdfPrintInfo.getTotalCnt()+")",NanumGothic,
					// fontSize, 7*perMM, 52*perMM,contentStream);
					contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 17 * perMM, 27 * perMM,
							(pageStandard.getHeight()) - 17 * perMM);
					drawText("도착국가", NanumGothic, fontSize, 8 * perMM, (pageStandard.getHeight() - 15 * perMM),
							contentStream);
					fontSize = 20;
					drawText(pdfPrintInfo.getDstnNation(), NanumGothic, fontSize, 11 * perMM, 28 * perMM,
							contentStream);

					fontSize = 15;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, (100 * perMM - titleWidth) / 2,
							14 * perMM, contentStream);
					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth("(" + pdfPrintInfo.getShipperName() + ")") / 1000
							* fontSize;
					drawText("(" + pdfPrintInfo.getShipperName() + ")", NanumGothic, fontSize,
							(100 * perMM - titleWidth) / 2, 9 * perMM, contentStream);

					// 컨텐츠 스트림 닫기
					contentStream.close();
					totalPage++;
				}
			} else if (blType.equals("C")) {
				String yslNo = pdfPrintInfo.getHawbNo2();
				String aciNo = pdfPrintInfo.getHawbNo();
				String orderNo = pdfPrintInfo.getOrderNo();
				
				String yslBarcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
				String aciBarcodePath = pdfPath2 + userId + "_" + aciNo + ".JPEG";
				
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
				//Barcode aciBarcode = BarcodeFactory.createCode128(orderNo);
				aciBarcode.setBarHeight(70);
				aciBarcode.setDrawingQuietSection(false);
				
				File barcodeFile = new File(yslBarcodePath);
				BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);
				
				File barcodeFile2 = new File(aciBarcodePath);
				BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);
				
				PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
				PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
				
				String pdfText = "";
				pdfText = printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")";
				
				int yslImgW = yslBarcodeImage.getWidth();
				int yslImgH = yslBarcodeImage.getHeight();
				int pdfYslWidth = 250;
				int pdfYslHeight = (int) ((double) pdfYslWidth * yslImgH / yslImgW);
				
				int aciImgW = aciBarcodeImage.getWidth();
				int aciImgH = aciBarcodeImage.getHeight();
				int pdfAciWidth = 220;
				int pdfAciHeight = (int) ((double) pdfAciWidth * aciImgH / aciImgW);
				
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
				//cts.showText(orderNo+" ▽");
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
				
				cts.close();
				totalPage++;
				
				
				File barcodefile = new File(yslBarcodePath);
				if (barcodefile.exists()) {
					barcodefile.delete();
				}
				
				File barcodefile2 = new File(aciBarcodePath);
				if (barcodefile2.exists()) {
					barcodefile2.delete();
				}
			}


		} catch (Exception e) {
			logger.error("Exception", e);
		}

		// 파일 다운로드 설정
		doc.save(realFilePath + orderNno + "PDF.pdf");
		doc.close();
		saveS3server(realFilePath + orderNno + "PDF.pdf", pdfPrintInfo.getUserId(), pdfPrintInfo.getHawbNo());
		File barcodefile = new File(barcodePath);
		if (barcodefile.exists()) {
			barcodefile.delete();
		}

	}

	public void execExportDeclareInfo(UserOrderListVO userOrderList) throws Exception {
		ExportDeclare expInfo = new ExportDeclare();
		expInfo.setNno(userOrderList.getNno());
		expInfo.setUserId(userOrderList.getUserId());
		expInfo.setExpType(userOrderList.getExpType());
		expInfo.setExpCor(userOrderList.getExpCor());
		expInfo.setExpRprsn(userOrderList.getExpRprsn());
		expInfo.setExpAddr(userOrderList.getExpAddr());
		expInfo.setExpZip(userOrderList.getExpZip());
		expInfo.setExpRgstrNo(userOrderList.getExpRgstrNo());
		expInfo.setExpCstCd(userOrderList.getExpCstCd());
		expInfo.setAgtCor(userOrderList.getAgtCor());
		expInfo.setAgtCstCd(userOrderList.getAgtCstCd());
		expInfo.setAgtBizNo(userOrderList.getAgtBizNo());
		expInfo.setExpNo(userOrderList.getExpNo());
		
		expInfo.encryptData();
		
		int expCnt = mapper.selectExportDeclareInfoCnt(expInfo);
		
		if (expCnt > 0) {
			mapper.updateExportDeclareInfo(expInfo);
		} else {
			expInfo.setSendYn("N");
			mapper.insertExportDeclareInfo(expInfo);
		}
	}

	@Override
	public String createZipFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<File> pdfFiles = new ArrayList<>();
		String filePath = realFilePath + "/pdf/zipFiles";
		
		String[] targets = request.getParameterValues("nnoList");
		String transCode = request.getParameter("transCode");
		
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targets));
		ArrayList<FastBoxParameterVO> fbParameters = new ArrayList<FastBoxParameterVO>();
		
		if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
			for (int i = 0; i < orderNnoList.size(); i++) {
				FastBoxParameterVO parameter = new FastBoxParameterVO();
				parameter.setNno(orderNnoList.get(i));
				fbParameters.add(parameter);
			}
		}
		
		switch (transCode) {
		case "ARA":
			pdfFiles = createAramexPdfFiles(request, response, orderNnoList);
			break;
		case "FED":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "EMN":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "EMS":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "USP":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "UPS":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "DHL":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "SEK":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "FEG":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "FES":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "ITC":
			pdfFiles = loadPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "YSL":
			pdfFiles = createYongsungPdfFiles(request, response, orderNnoList);
			break;
		case "ACI":
			pdfFiles = createACIPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "EPT":
			pdfFiles = createACIPdfFiles(request, response, orderNnoList, transCode);
			break;
		case "FB":
			pdfFiles = createFastboxPdfFiles(request, response, fbParameters, transCode);
			break;
		case "FB-EMS":
			pdfFiles = createFastboxPdfFiles(request, response, fbParameters, transCode);
			break;
		default:
			pdfFiles = createACIPdfFiles(request, response, orderNnoList, transCode);
			break;
		}
		
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateTime = format.format(nowDate);
		
		String zipFileName = "filelist_"+dateTime+".zip";
		File zipFile = new File(filePath, zipFileName);
		byte[] buf = new byte[4096];
		
		try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
			for (File file : pdfFiles) {
				try (FileInputStream in = new FileInputStream(file)) {
					ZipEntry ze = new ZipEntry(file.getName());
					out.putNextEntry(ze);
					
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					
					out.closeEntry();
				}
				
			}
		}
		
		for (int i = 0; i < pdfFiles.size(); i++) {
			if (pdfFiles.get(i).exists()) {
				pdfFiles.get(i).delete();
			}
		}
		
		String realFileName = filePath + "/" + zipFileName;
		
		return realFileName;
	}

	private List<File> createFastboxPdfFiles(HttpServletRequest request, HttpServletResponse response, ArrayList<FastBoxParameterVO> orderNnoList, String transCode) throws Exception {
		List<File> pdfFiles = new ArrayList<>();
		String filePath = realFilePath + "/pdf/zipFiles/";
		
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		
		float perMM = 1 / (10 * 2.54f) * 72;
		float perIn = 72;
		
		try {
			
			for (int i = 0; i < orderNnoList.size(); i++) {
				String nno = orderNnoList.get(i).getNno();
				FastBoxParameterVO fbParameterOne = new FastBoxParameterVO();
				fbParameterOne.setNno(nno);
				
				PDDocument document = new PDDocument();
				ClassPathResource cssResource = new ClassPathResource("application.properties");
				String mainPath = cssResource.getURL().getPath();
				String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
				InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

				PDType0Font NanumGothic = PDType0Font.load(document, korean);
				int totalPage = 0;
				FBOrderVO fbOrderList = new FBOrderVO();
				fbOrderList = fbMapper.selectFbNnoOne(fbParameterOne);
				
				PDRectangle pageStandard = new PDRectangle(4 * perIn, 6 * perIn);
				PDPage blankPage = new PDPage(pageStandard);
				//PDPage blankPage = new PDPage(PDRectangle.A6);
				document.addPage(blankPage);
				PDPage page = document.getPage(totalPage);
				PDPageContentStream contentStream = new PDPageContentStream(document, page);
				
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
				itemList = fbMapper.selectFBItem(nno);
				
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
				
				String fileName = fbOrderList.getOrderNo() + "_" + fbOrderList.getHawbNo() + ".pdf";
				fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
				
				File pdfFile = new File(filePath, fileName);
				document.save(pdfFile);
				document.close();
				pdfFiles.add(pdfFile);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pdfFiles;
	}

	private List<File> loadPdfFiles(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String transCode) {
		List<File> pdfFiles = new ArrayList<>();
		
		try {
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nnoList", orderNnoList);
			parameters.put("printType", transCode);
			
			String filePath = realFilePath + "/pdf/zipFiles/";
			ArrayList<HashMap<String, String>> hawbList = new ArrayList<HashMap<String, String>>();
			hawbList = mapper.selectHawbListSaved(parameters);
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			
			for (int i = 0; i < hawbList.size(); i++) {
				if (orgStation == null || orgStation.equals("")) {
					orgStation = hawbList.get(i).get("orgStation");
				}
				
				String orderNo = hawbList.get(i).get("orderNo");
				String hawbNo = hawbList.get(i).get("hawbNo");
				String wDate = hawbList.get(i).get("wDate");
				String year = wDate.substring(0, 4);
				String month = wDate.substring(4, 6);
				String day = wDate.substring(6, 8);
				wDate = year + "-" + month + "-" + day;
				String week = Integer.toString(getWeekOfYear(wDate));
				
				URL website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/" + hawbList.get(i).get("userId") + "_" + hawbList.get(i).get("hawbNo"));
				HttpURLConnection http = (HttpURLConnection) website.openConnection();
				int statusCode = http.getResponseCode();
				
				String fileName = orderNo + "_" + hawbNo + ".pdf";
				fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
				
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				
				File file = new File(filePath + fileName);
				pdfFiles.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pdfFiles.add(null);
		}
		
		return pdfFiles;
	}

	private List<File> createACIPdfFiles(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String transCode) throws Exception {
		List<File> pdfFiles = new ArrayList<>();
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		String printType = transCode;
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		String filePath = realFilePath + "/pdf/zipFiles/";
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
		
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		
		try {
			for (int i = 0; i < orderNnoList.size(); i++) {
				
				PDDocument doc = new PDDocument();
				
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
				
				pdfPrintInfo = mapper.selectPdfPrintInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				String blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
				
				String fileName = pdfPrintInfo.getOrderNo() + "_" + pdfPrintInfo.getHawbNo() + ".pdf";
				fileName = fileName.replaceAll("[\\\\/:*?\"<>| ]", "_");
				
				if (!transCode.equals("ACI")) {
					blType = "C";
				} else {
					if (blType.equals("") || blType == null) {
						blType = "C";
					}
				}
				
				for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
					if (blType.equals("A")) {
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						contentStream.drawImage(markImage, 5 * perMM, 133.5f * perMM, 65f, 35f);
						// 배경이미지 로드

						barcodes.setBarHeight(70);
						barcodes.setBarWidth(50);

						barcodes.setLabel("Barcode creation test...");
						barcodes.setDrawingText(true);

						float titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * 9;
						int fontSize = 12; // Or whatever font size you want.
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f,
								45f);

						/* 사각형 */
						contentStream.drawLine(3 * perMM, 152 * perMM - 149 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								152 * perMM - 149 * perMM);
						contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								152 * perMM - 3 * perMM);
						contentStream.drawLine(3 * perMM, 152 * perMM - 3 * perMM, 3 * perMM, 152 * perMM - 149 * perMM);
						contentStream.drawLine((pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 3 * perMM,
								(pageStandard.getWidth()) - 3 * perMM, 152 * perMM - 149 * perMM);

						// 1st Vertical Line
						contentStream.drawLine(30 * perMM, 149 * perMM, 30 * perMM, 129 * perMM);
						// 1st horizontal Line
						// contentStream.drawLine(3*perMM, 144*perMM, 30*perMM, 144*perMM);
						// 2nd horizontal Line
						contentStream.drawLine(3 * perMM, 129 * perMM, (pageStandard.getWidth()) - 3 * perMM, 129 * perMM);

						// 3rd horizontal Line
						contentStream.drawLine(3 * perMM, 124 * perMM, (pageStandard.getWidth()) - 3 * perMM, 124 * perMM);

						// 2nd Vertical Line
						contentStream.drawLine(40 * perMM, 129 * perMM, 40 * perMM, 109 * perMM);
						// 3rd Vertical Line
						contentStream.drawLine(22 * perMM, 129 * perMM, 22 * perMM, 109 * perMM);

						// 4th horizontal Line
						contentStream.drawLine(3 * perMM, 109 * perMM, (pageStandard.getWidth()) - 3 * perMM, 109 * perMM);

						/* 글자 표기 */
						drawText("Piece(s)", ARIAL, 7, 5 * perMM, 125.5f * perMM, contentStream);
						fontSize = 20;
						titleWidth = ARIAL.getStringWidth((j + 1) + "/" + pdfPrintInfo.getBoxCnt()) / 1000 * fontSize;
						drawText((j + 1) + "/" + pdfPrintInfo.getBoxCnt(), ARIAL, fontSize, 7 * perMM, 115 * perMM,
								contentStream);

						fontSize = 17;
						drawText("Weight", ARIAL, 7, 24 * perMM, 125.5f * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth(pdfPrintInfo.getUserWta()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getUserWta(), ARIAL, fontSize, 26 * perMM, 115 * perMM, contentStream);

						fontSize = 8;
						titleWidth = ARIAL.getStringWidth("KG") / 1000 * fontSize;
						drawText("KG", ARIAL, fontSize, 34 * perMM, 111 * perMM, contentStream);
						// Order No.
						fontSize = 7;
						drawText("BL No. / Order No.", ARIAL, fontSize, 43 * perMM, 125.5f * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getHawbNo(), ARIAL, fontSize, 43 * perMM, 118 * perMM, contentStream);
						fontSize = 12;
						drawText(pdfPrintInfo.getOrderNo(), ARIAL, fontSize, 43 * perMM, 112 * perMM, contentStream);

						fontSize = 7;
						drawText("Origin", ARIAL, fontSize, 10 * perMM, 106 * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getOrgStationName(), ARIAL, fontSize, 15 * perMM, 100 * perMM, contentStream);
						drawText("========>", ARIAL, fontSize, 34 * perMM, 100 * perMM, contentStream);

						fontSize = 7;
						drawText("Destination", ARIAL, fontSize, 70 * perMM, 106 * perMM, contentStream);
						fontSize = 15;
						drawText(pdfPrintInfo.getDstnNation(), ARIAL, fontSize, 80 * perMM, 100 * perMM, contentStream);

						// Shipper
						// 5th horizontal Line
						contentStream.drawLine(3 * perMM, 96 * perMM, (pageStandard.getWidth()) - 3 * perMM, 96 * perMM);
						fontSize = 9;
						drawText("SHIPPER INFO", ARIAL, fontSize, 5 * perMM, 92 * perMM, contentStream);
						// 6th horizontal Line
						contentStream.drawLine(3 * perMM, 90 * perMM, (pageStandard.getWidth()) - 3 * perMM, 90 * perMM);

						fontSize = 7;
						drawText("Name : ", ARIAL, fontSize, 5 * perMM, 86 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getShipperName(), ARIAL, fontSize, 5 * perMM + titleWidth, 86 * perMM,
								contentStream);

						fontSize = 7;
						drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 82 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getShipperTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 82 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
						drawText("Country : ", ARIAL, fontSize, 5 * perMM, 78 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 78 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
						drawText("State : ", ARIAL, fontSize, 60 * perMM, 78 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperState(),ARIAL, fontSize, 10+titleWidth+80,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperState(), ARIAL, fontSize, 60 * perMM + titleWidth, 78 * perMM,
								contentStream);

						fontSize = 7;
						drawText("City : ", ARIAL, fontSize, 5 * perMM, 74 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
						// pageStandard.getHeight()-151,contentStream);
						drawText(pdfPrintInfo.getShipperCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 74 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
						drawText("Zip Code : ", ARIAL, fontSize, 60 * perMM, 74 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getShipperCity(),ARIAL, fontSize, 10+titleWidth+10,
						// pageStandard.getHeight()-151,contentStream);
						drawTextLine(pdfPrintInfo.getShipperZip(), ARIAL, fontSize, 60 * perMM + titleWidth, 74 * perMM,
								contentStream, 2);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
						drawText("ADDRESS(EN) : ", ARIAL, fontSize, 5 * perMM, 70 * perMM, contentStream);
						fontSize = 9;
						// drawText(pdfPrintInfo.getOrgStation(),ARIAL, fontSize, 10+titleWidth+150,
						// pageStandard.getHeight()-151,contentStream);
						drawTextLine(pdfPrintInfo.getShipperAddr() + " " + pdfPrintInfo.getShipperAddrDetail(), ARIAL,
								fontSize, 5 * perMM + titleWidth, 70 * perMM, contentStream, 4);

						// receiver
						// 7th horizontal Line
						contentStream.drawLine(3 * perMM, 60 * perMM, (pageStandard.getWidth()) - 3 * perMM, 60 * perMM);

						// Receiver
						fontSize = 9;
						drawText("RECEIVER INFO", ARIAL, fontSize, 5 * perMM, 56 * perMM, contentStream);
						// 6th horizontal Line
						contentStream.drawLine(3 * perMM, 54 * perMM, (pageStandard.getWidth()) - 3 * perMM, 54 * perMM);

						fontSize = 7;
						drawText("Name : ", ARIAL, fontSize, 5 * perMM, 50 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("Name : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeName(), ARIAL, fontSize, 5 * perMM + titleWidth, 50 * perMM,
								contentStream);

						fontSize = 7;
						drawText("TEL : ", ARIAL, fontSize, 5 * perMM, 46 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("TEL : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeTel(), ARIAL, fontSize, 5 * perMM + titleWidth, 46 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Country : ") / 1000 * fontSize;
						drawText("Country : ", ARIAL, fontSize, 5 * perMM, 42 * perMM, contentStream);
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeCntry(), ARIAL, fontSize, 5 * perMM + titleWidth, 42 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("State : ") / 1000 * fontSize;
						drawText("State : ", ARIAL, fontSize, 5 * perMM, 38 * perMM, contentStream);
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeState(), ARIAL, fontSize, 5 * perMM + titleWidth, 38 * perMM,
								contentStream);

						fontSize = 7;
						drawText("City : ", ARIAL, fontSize, 5 * perMM, 34 * perMM, contentStream);
						titleWidth = ARIAL.getStringWidth("City : ") / 1000 * fontSize;
						fontSize = 9;
						drawText(pdfPrintInfo.getCneeCity(), ARIAL, fontSize, 5 * perMM + titleWidth, 34 * perMM,
								contentStream);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("Zip Code : ") / 1000 * fontSize;
						drawText("Zip Code : ", ARIAL, fontSize, 5 * perMM, 30 * perMM, contentStream);
						fontSize = 9;
						drawTextLine(pdfPrintInfo.getCneeZip(), ARIAL, fontSize, 5 * perMM + titleWidth, 30 * perMM,
								contentStream, 2);

						fontSize = 7;
						titleWidth = ARIAL.getStringWidth("ADDRESS(EN) : ") / 1000 * fontSize;
						drawText("ADDRESS : ", ARIAL, fontSize, 5 * perMM, 26 * perMM, contentStream);
						fontSize = 9;
						drawTextLine(pdfPrintInfo.getCneeAddr() + " " + pdfPrintInfo.getCneeAddrDetail(), ARIAL, fontSize,
								5 * perMM + titleWidth, 26 * perMM, contentStream, 4);

						// 바코드
						contentStream.drawImage(pdImage, 335, 235, 110f, 50f);
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
						
					} else if (blType.equals("B")) {
						ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
						itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
						String hawbNo = pdfPrintInfo.getHawbNo();
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						float pageWidth = pageStandard.getWidth();
						float pageHeight = pageStandard.getHeight();
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
						hawbBarcode.setBarHeight(70);
						hawbBarcode.setDrawingQuietSection(false);
						
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						
						// aci 로고
						//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

						int fontSize = 15;
						float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;

						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						
						// HawbNo 바코드
						int barcodeW = pdImage.getWidth();
						System.out.println(barcodeW);
						int barcodeH = pdImage.getHeight();
						System.out.println(barcodeH);
						//int pdfWidth = 160;
						//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
						int pdfHeight = 45;
						int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
						
						float leftX = 5;
						float rightX = pageWidth - 5;
						
						float centerX = (rightX - leftX - pdfWidth) / 2 + leftX;
						
						//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
						//contentStream.drawImage(pdImage, (100 * perMM - 58 * perMM), (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						contentStream.drawImage(pdImage, centerX, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						
						//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
						contentStream.drawLine(5, 5, 5, (pageHeight - 5));
						contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
						contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
						contentStream.drawLine((pageWidth - 5), 5, 5, 5);
						
						
						PDExtendedGraphicsState graphicsState  = new PDExtendedGraphicsState();
						if (pdfPrintInfo.getUserId().toLowerCase().equals("pickpack")) {
							graphicsState.setNonStrokingAlphaConstant(0.3f);

							int ftSize = 15;
							String textString = "PickPack Worldwide LLC";
							float ftWidth = ARIALBOLD.getStringWidth(textString) / 1000 * ftSize;
							contentStream.beginText();
							contentStream.newLineAtOffset(((pageWidth - 10) - ftWidth), 10);
							contentStream.setFont(ARIALBOLD, ftSize);
							contentStream.setGraphicsStateParameters(graphicsState);
							contentStream.showText(textString);
							contentStream.endText();
						}
						
						PDExtendedGraphicsState graphicsState2  = new PDExtendedGraphicsState();
						graphicsState2.setNonStrokingAlphaConstant(1f);
						contentStream.setGraphicsStateParameters(graphicsState2);

						float xPoint = 5;
						float yPoint = (152 * perMM - 21 * perMM) - 7;
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						xPoint = xPoint + 5;
						
						String contentText = "";
						contentText = hawbNo;
						float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						
						yPoint = yPoint - textHeight;
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint + 2);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 14;
						contentText = pdfPrintInfo.getOrderNo();
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint = yPoint - textHeight;
						
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 10;
						contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						xPoint = (pageWidth - 10) - textWidth;


						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, 342);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						contentStream.drawLine(5, 335, (pageWidth - 5), 335);
						
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint -= (textHeight + 3);
						xPoint = 10;
						contentText = pdfPrintInfo.getShipperName();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperTel();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						float startX = xPoint;
						float startY = yPoint;
						float currentX = startX;
						float currentY = startY;
						float widthLimit = 260;
						float leading = textHeight;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						String[] words = contentText.split(" ");
						
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY - 5;
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getCneeName();
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(contentText);
						contentStream.endText();
						
						String cneeTel = pdfPrintInfo.getCneeTel();
						if (cneeTel.equals("") ) {
							cneeTel = pdfPrintInfo.getCneeHp();
						}
						
						yPoint -= textHeight;
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(cneeTel);
						contentStream.endText();
						
						yPoint -= textHeight;
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
						if (!pdfPrintInfo.getCneeCity().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
						}
						if (!pdfPrintInfo.getCneeState().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeState()+" ";
						}
						cityStateInfo += pdfPrintInfo.getDstnNation();

						words = cityStateInfo.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint -= textHeight;
						String cneeAddr = pdfPrintInfo.getCneeAddr();
						if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
							cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
						}
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						words = cneeAddr.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = (currentY - 5);
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= 12;
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
							String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
							int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
							String itemText = itemDetail + " / " + itemCnt;
							
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							
							String[] itemWords = itemText.split(" ");
							
							for (String word : itemWords) {
								float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
								if (currentX + wordWidth > startX + widthLimit) {
									currentX = startX;
									currentY -= leading;
									contentStream.newLineAtOffset(0, -leading);
								}
								contentStream.showText(word + " ");
								currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
							}
							
							contentStream.endText();
							
							currentY -= leading;
							
						}
						
						
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
					} else if (blType.equals("C")) {
						ArrayList<HashMap<String, Object>> itemInfos = new ArrayList<HashMap<String, Object>>();
						itemInfos = mapper.selectPdfPrintItemInfo(pdfPrintInfo.getNno());
						String hawbNo = pdfPrintInfo.getHawbNo();
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 152 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);
						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);
						
						float pageWidth = pageStandard.getWidth();
						float pageHeight = pageStandard.getHeight();
						
						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);
						Barcode hawbBarcode = BarcodeFactory.createCode128(hawbNo); 
						hawbBarcode.setBarHeight(70);
						hawbBarcode.setDrawingQuietSection(false);
						
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(hawbBarcode, barcodefile);

						File markFile = new File(markPath);
						PDImageXObject markImage = PDImageXObject.createFromFileByContent(markFile, doc);
						
						// aci 로고
						//contentStream.drawImage(markImage, 8 * perMM, 133.5f * perMM, 68f, 35f);

						int fontSize = 15;
						float titleWidth = ARIAL.getStringWidth(hawbNo) / 1000 * fontSize;
						
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
						
						// HawbNo 바코드
						int barcodeW = pdImage.getWidth();
						int barcodeH = pdImage.getHeight();
						//int pdfWidth = 160;
						//int pdfHeight = (int) ((double) pdfWidth * barcodeW / barcodeH);
						int pdfHeight = 45;
						int pdfWidth = (int) ((double) pdfHeight * barcodeW / barcodeH);
						
						float imageX = (pageWidth - pdfWidth) / 2;
						float dstnHeight = (((152 * perMM - 21 * perMM) - 7) - (pageHeight - 5)) / 2;
						
						String dstnNation = pdfPrintInfo.getDstnNation();
						float dstnNationWidth = ARIALBOLD.getStringWidth(dstnNation) / 1000 * fontSize;
						float dstnLineWidth = dstnNationWidth + 15;
						
						//contentStream.drawImage(pdImage, (100 * perMM - 64.5f * perMM), (152 * perMM - 21 * perMM), 160f, 45f);
						contentStream.drawImage(pdImage, 23, (152 * perMM - 20.5f * perMM), pdfWidth, pdfHeight);
						
						//contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageHeight * perMM - 3 * perMM));
						contentStream.drawLine(5, 5, 5, (pageHeight - 5));
						contentStream.drawLine(5, (pageHeight - 5), (pageWidth - 5), (pageHeight - 5));
						contentStream.drawLine((pageWidth - 5), (pageHeight - 5), (pageWidth - 5), 5);
						contentStream.drawLine((pageWidth - 5), 5, 5, 5);
						
						float xPoint = 5;
						float yPoint = (152 * perMM - 21 * perMM) - 7;
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint, ((pageWidth - 5) - dstnLineWidth), (pageHeight - 5));
						contentStream.drawLine(((pageWidth - 5) - dstnLineWidth), yPoint + 30, (pageWidth - 5), yPoint + 30);
						
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 7.5f, yPoint + 10);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(pdfPrintInfo.getDstnNation());
						contentStream.endText();
						
						contentStream.beginText();
						contentStream.newLineAtOffset(((pageWidth - 5) - dstnLineWidth) + 5, yPoint + 40);
						contentStream.setFont(ARIAL, 13);
						contentStream.showText("Dest");
						contentStream.endText();
						
												
						xPoint = xPoint + 5;
						
						String contentText = "";
						contentText = hawbNo;
						float textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						float textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						
						yPoint = yPoint - textHeight;
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint + 2);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						fontSize = 14;
						contentText = pdfPrintInfo.getOrderNo();
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						yPoint = yPoint - textHeight;
						
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIALBOLD, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						String orderDate = pdfPrintInfo.getOrderDate();
						orderDate = "(" + orderDate + ")";
						
						fontSize = 10;
						xPoint = (xPoint + 7) + textWidth;

						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint - 4);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(orderDate);
						contentStream.endText();
						
						//fontSize = 10;
						contentText = pdfPrintInfo.getBoxCnt() + " Piece(s) / " + pdfPrintInfo.getUserWta() + " " + pdfPrintInfo.getWtUnit().toLowerCase();
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						xPoint = (pageWidth - 10) - textWidth;


						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, 342);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						contentStream.drawLine(5, 335, (pageWidth - 5), 335);
						
						textHeight = fontSize * ARIAL.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;
						yPoint -= (textHeight + 3);
						xPoint = 10;
						contentText = pdfPrintInfo.getShipperName();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();
						
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperTel();
						contentStream.beginText();
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.setFont(ARIAL, fontSize);
						contentStream.showText(contentText);
						contentStream.endText();

						yPoint -= textHeight;
						contentText = pdfPrintInfo.getShipperAddr() + " (" + pdfPrintInfo.getShipperZip() + ")";
						textWidth = ARIAL.getStringWidth(contentText) / 1000 * fontSize;
						float startX = xPoint;
						float startY = yPoint;
						float currentX = startX;
						float currentY = startY;
						float widthLimit = 260;
						float leading = textHeight;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						String[] words = contentText.split(" ");
						
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY - 5;
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= textHeight;
						contentText = pdfPrintInfo.getCneeName();
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(contentText);
						contentStream.endText();
						
						String cneeTel = pdfPrintInfo.getCneeTel();
						if (cneeTel.equals("") ) {
							cneeTel = pdfPrintInfo.getCneeHp();
						}
						
						yPoint -= textHeight;
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(xPoint, yPoint);
						contentStream.showText(cneeTel);
						contentStream.endText();
						
						yPoint -= textHeight;
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;

						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						String cityStateInfo = "(" + pdfPrintInfo.getCneeZip() + ") ";
						if (!pdfPrintInfo.getCneeCity().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeCity()+" ";
						}
						if (!pdfPrintInfo.getCneeState().equals("")) {
							cityStateInfo += pdfPrintInfo.getCneeState()+" ";
						}
						cityStateInfo += pdfPrintInfo.getDstnNation();

						words = cityStateInfo.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = currentY;
						
						yPoint -= textHeight;
						String cneeAddr = pdfPrintInfo.getCneeAddr();
						if (!pdfPrintInfo.getCneeAddrDetail().equals("")) {
							cneeAddr += " " + pdfPrintInfo.getCneeAddrDetail();
						}
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						contentStream.beginText();
						contentStream.setFont(ARIAL, fontSize);
						contentStream.newLineAtOffset(startX, startY);
						
						words = cneeAddr.split(" ");
						for (String word : words) {
							float wordWidth = ARIAL.getStringWidth(word) / 1000 * fontSize;
							if (currentX + wordWidth > startX + widthLimit) {
								currentX = startX;
								currentY -= leading;
								contentStream.newLineAtOffset(0, -leading);
							}
							contentStream.showText(word + " ");
							currentX += wordWidth + ARIAL.getStringWidth(" ") / 1000 * fontSize;
						}
						
						contentStream.endText();
						
						yPoint = (currentY - 5);
						
						contentStream.drawLine(5, yPoint, (pageWidth - 5), yPoint);
						
						yPoint -= 12;
						
						startX = xPoint;
						startY = yPoint;
						currentX = startX;
						currentY = startY;
						
						// startX ~ widthLimit

						for (int itemOne = 0; itemOne < itemInfos.size(); itemOne++) {
							String itemDetail = (String) itemInfos.get(itemOne).get("itemDetail");
							int itemCnt = (int) itemInfos.get(itemOne).get("itemCnt");
							String itemText = itemDetail + " / " + itemCnt;
							float wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
							
							String trimStr = "";
							if (wordWidth <= widthLimit) {
								trimStr = itemText;
							} else {
								float trimRatio = widthLimit / wordWidth;
								int trimmedLength = (int) (itemText.length() * trimRatio);
								trimStr = itemText.substring(0, trimmedLength);	
							}
							
							while (wordWidth > widthLimit) {
								contentStream.beginText();
								contentStream.setFont(ARIAL, fontSize);
								contentStream.newLineAtOffset(startX, currentY);
								contentStream.showText(trimStr);
								contentStream.endText();
								
								currentY -= leading;
								
								itemText = itemText.substring(trimStr.length());
								wordWidth = ARIAL.getStringWidth(itemText) / 1000 * fontSize;
							}
							
							contentStream.beginText();
							contentStream.setFont(ARIAL, fontSize);
							contentStream.newLineAtOffset(startX, currentY);
							contentStream.showText(itemText);
							contentStream.endText();
							
							currentY -= leading;
						}
						
						// 컨텐츠 스트림 닫기
						contentStream.close();
						
						totalPage++;
						
					}
				}
				

			File pdfFile = new File(fileName);
			doc.save(pdfFile);
			doc.close();
			pdfFiles.add(pdfFile);
				
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return pdfFiles;
	}

	private List<File> createYongsungPdfFiles(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) throws Exception {
		List<File> pdfFiles = new ArrayList<>();
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		String printType = "YSL";
		PdfPrintVO pdfPrintInfo = new PdfPrintVO();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		String filePath = realFilePath + "/pdf/zipFiles/";
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
		
		String barcodePath = pdfPath2 + userId + ".JPEG";
		String markPath = pdfPath + "/mark/aciMark.jpg";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		
		float perMM = 1 / (10 * 2.54f) * 72;
		
		try {

			for (int i = 0; i < orderNnoList.size(); i++) {
				int totalPage = 0;
				
				PDDocument doc = new PDDocument();
				InputStream japanese = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
				InputStream english = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
				InputStream englishBold = new FileInputStream(subPath + "/static/fonts/ARIALBD.TTF");
				InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
				
				PDType0Font MsGothic = PDType0Font.load(doc, japanese);
				PDType0Font ARIAL = PDType0Font.load(doc, english);
				PDType0Font ARIALBOLD = PDType0Font.load(doc, englishBold);
				PDType0Font NanumGothic = PDType0Font.load(doc, korean);
				
				pdfPrintInfo = mapper.selectTempInfo(orderNnoList.get(i), printType);
				pdfPrintInfo.dncryptData();
				String blType = mapper.selectBlTypeTransCom(pdfPrintInfo);
				String fileName = pdfPrintInfo.getOrderNo() + "_" + pdfPrintInfo.getHawbNo() + ".pdf";
				fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
				
				if (blType.equals("A")) {
					PDRectangle pageStandard = new PDRectangle(80 * perMM, 30 * perMM);
					PDPage blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);

					// 현재 페이지 설정
					//PDPage page = doc.getPage(i);
					PDPage page = doc.getPage(totalPage);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo2());

					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory.createCode128(pdfPrintInfo.getHawbNo());

					barcodes2.setSize(400, 800);
					barcodes2.setBarHeight(0);
					barcodes2.setBarWidth(0);

					barcodes2.setLabel("Barcode creation test...");
					barcodes2.setDrawingText(true);

					File barcodefile2 = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);

					PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

					int fontSize = 10; // Or whatever font size you want.
					float titleWidth = NanumGothic.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")")
							/ 1000 * fontSize;
					contentStream.drawImage(pdImage, (80 * perMM - 200) / 2, 38, 200f, 30f);

					contentStream.drawImage(pdImage2, (80 * perMM - 200) / 2, 7, 200f, 15f);

					drawText(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")", NanumGothic, fontSize,
							(80 * perMM - 80), 30 * perMM - 11, contentStream);

					fontSize = 9;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo() + " ▽ "+pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, 10 * perMM, 26, contentStream);

					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
					drawText(pdfPrintInfo.getHawbNo2() + " ▽", NanumGothic, fontSize, (10 * perMM), 30 * perMM - 11, contentStream);

					contentStream.close();
					totalPage++;
				} else if (blType.equals("B")) {
					for (int j = 0; j < Integer.parseInt(pdfPrintInfo.getBoxCnt()); j++) {
						// 페이지 추가
						PDRectangle pageStandard = new PDRectangle(100 * perMM, 55 * perMM);
						PDPage blankPage = new PDPage(pageStandard);
						doc.addPage(blankPage);

						// 현재 페이지 설정
						PDPage page = doc.getPage(totalPage);

						// 컨텐츠 스트림 열기
						PDPageContentStream contentStream = new PDPageContentStream(doc, page);

						contentStream.drawLine(3 * perMM, 3 * perMM, (pageStandard.getWidth()) - 3 * perMM, 3 * perMM);
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight() - 3 * perMM),
								(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight() - 3 * perMM));
						contentStream.drawLine(97 * perMM, 3 * perMM, 97 * perMM,
								(pageStandard.getHeight()) - 3 * perMM);
						contentStream.drawLine(3 * perMM, 3 * perMM, 3 * perMM, (pageStandard.getHeight()) - 3 * perMM);

						// barcode1
						net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
								.createCode128(pdfPrintInfo.getHawbNo2());
						File barcodefile = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
						barcodes.setSize(400, 800);
						barcodes.setBarHeight(0);
						barcodes.setBarWidth(0);
						barcodes.setLabel("Barcode creation test...");
						barcodes.setDrawingText(true);
						PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

						// barcode2
						net.sourceforge.barbecue.Barcode barcodes2 = BarcodeFactory
								.createCode128(pdfPrintInfo.getHawbNo());
						barcodes2.setSize(400, 800);
						barcodes2.setBarHeight(0);
						barcodes2.setBarWidth(0);
						barcodes2.setLabel("Barcode creation test...");
						barcodes2.setDrawingText(true);
						File barcodefile2 = new File(barcodePath);
						BarcodeImageHandler.saveJPEG(barcodes2, barcodefile2);
						PDImageXObject pdImage2 = PDImageXObject.createFromFileByContent(barcodefile2, doc);

						// barcode print
						contentStream.drawImage(pdImage, (100 * perMM - 48 * perMM) / 2,
								(pageStandard.getHeight()) - 15 * perMM, 200f, 30f);
						contentStream.drawImage(pdImage2, (100 * perMM - 48 * perMM) / 2,
								(pageStandard.getHeight()) - 27 * perMM, 200f, 15f);

						int fontSize = 10; // Or whatever font size you want.
						float titleWidth = NanumGothic
								.getStringWidth(printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")") / 1000
								* fontSize;

						fontSize = 9;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getHawbNo() + " △", NanumGothic, fontSize, 48 * perMM, 24 * perMM,
								contentStream);

						fontSize = 10;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getHawbNo2()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getHawbNo2() + "  △", NanumGothic, fontSize, (46 * perMM), 36 * perMM,
								contentStream);

						// 바코드 아래 가로
						contentStream.drawLine(3 * perMM, 22 * perMM, (pageStandard.getWidth()) - 3 * perMM,
								22 * perMM);
						// 바코드 왼쪽 세로
						contentStream.drawLine(27 * perMM, 22 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 3 * perMM);
						// 바코드 왼쪽 위에 가로
						contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 10 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 10 * perMM);

						contentStream.drawLine(3 * perMM, (pageStandard.getHeight()) - 17 * perMM, 27 * perMM,
								(pageStandard.getHeight()) - 17 * perMM);
						drawText("도착국가", NanumGothic, fontSize, 8 * perMM, (pageStandard.getHeight() - 15 * perMM),
								contentStream);
						fontSize = 20;
						drawText(pdfPrintInfo.getDstnNation(), NanumGothic, fontSize, 11 * perMM, 28 * perMM,
								contentStream);

						fontSize = 15;
						titleWidth = NanumGothic.getStringWidth(pdfPrintInfo.getOrderNo()) / 1000 * fontSize;
						drawText(pdfPrintInfo.getOrderNo(), NanumGothic, fontSize, (100 * perMM - titleWidth) / 2,
								14 * perMM, contentStream);
						fontSize = 9;
						titleWidth = NanumGothic.getStringWidth("(" + pdfPrintInfo.getShipperName() + ")") / 1000
								* fontSize;
						drawText("(" + pdfPrintInfo.getShipperName() + ")", NanumGothic, fontSize,
								(100 * perMM - titleWidth) / 2, 9 * perMM, contentStream);

						// 컨텐츠 스트림 닫기
						contentStream.close();
						totalPage++;
					}
				} else if (blType.equals("C")) {
					String yslNo = pdfPrintInfo.getHawbNo2();
					String aciNo = pdfPrintInfo.getHawbNo();
					String orderNo = pdfPrintInfo.getOrderNo();
					
					String yslBarcodePath = pdfPath2 + userId + "_" + yslNo + ".JPEG";
					String aciBarcodePath = pdfPath2 + userId + "_" + aciNo + ".JPEG";
					
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
					//Barcode aciBarcode = BarcodeFactory.createCode128(orderNo);
					aciBarcode.setBarHeight(70);
					aciBarcode.setDrawingQuietSection(false);
					
					File barcodeFile = new File(yslBarcodePath);
					BarcodeImageHandler.saveJPEG(yslBarcode, barcodeFile);
					
					File barcodeFile2 = new File(aciBarcodePath);
					BarcodeImageHandler.saveJPEG(aciBarcode, barcodeFile2);
					
					PDImageXObject yslBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile, doc);
					PDImageXObject aciBarcodeImage = PDImageXObject.createFromFileByContent(barcodeFile2, doc);
					
					String pdfText = "";
					pdfText = printType + "입고 (" + pdfPrintInfo.getTotalCnt() + ")";
					
					int yslImgW = yslBarcodeImage.getWidth();
					int yslImgH = yslBarcodeImage.getHeight();
					int pdfYslWidth = 250;
					int pdfYslHeight = (int) ((double) pdfYslWidth * yslImgH / yslImgW);
					
					int aciImgW = aciBarcodeImage.getWidth();
					int aciImgH = aciBarcodeImage.getHeight();
					int pdfAciWidth = 220;
					int pdfAciHeight = (int) ((double) pdfAciWidth * aciImgH / aciImgW);
					
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
					//cts.showText(orderNo+" ▽");
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
					
					cts.close();
					totalPage++;
					
					
					File barcodefile = new File(yslBarcodePath);
					if (barcodefile.exists()) {
						barcodefile.delete();
					}
					
					File barcodefile2 = new File(aciBarcodePath);
					if (barcodefile2.exists()) {
						barcodefile2.delete();
					}
				}
				File pdfFile = new File(filePath, fileName);
				doc.save(pdfFile);
				doc.close();
				pdfFiles.add(pdfFile);
			} // for roop end
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pdfFiles;
	}

	private List<File> createAramexPdfFiles(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) {
		List<File> pdfFiles = new ArrayList<>();
		
		try {
			ArrayList<HashMap<String, String>> hawbList = mapper.selectHawbListARA(orderNnoList);

			String filePath = realFilePath + "/pdf/zipFiles/";
			for (int i = 0; i < hawbList.size(); i++) {
				String orderNo = hawbList.get(i).get("orderNo");
				String hawbNo = hawbList.get(i).get("hawbNo");
				String wDate = hawbList.get(i).get("wDate");
				String year = wDate.substring(0, 4);
				String month = wDate.substring(4, 6);
				String day = wDate.substring(6, 8);
				wDate = year + "-" + month + "-" + day;
				String week = Integer.toString(getWeekOfYear(wDate));
				URL website = new URL("http://img.mtuai.com/outbound/hawb/" + year + "/" + week + "/" + hawbList.get(i).get("userId") + "_" + hawbList.get(i).get("hawbNo"));
				HttpURLConnection http = (HttpURLConnection) website.openConnection();
				int statusCode = http.getResponseCode();
				
				String fileName = orderNo + "_" + hawbNo + ".pdf";
				fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				
				File file = new File(filePath + fileName);
				pdfFiles.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pdfFiles.add(null);
		}
		
		return pdfFiles;
	}

	@Override
	public byte[] createOrderListExcelCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			String browserLanguage = request.getHeader("Accept-Language");
			String firstLanguage = "";
			String lang = "";
			String filePath = "";
			
			if (browserLanguage != null && !browserLanguage.isEmpty()) {
				String[] languages = browserLanguage.split(",");
				firstLanguage = languages[0].split(";")[0];
			}
			
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			String userId = (String) request.getSession().getAttribute("USER_ID");

			parameterInfo.put("userId", userId);
			
			if (!orgStation.equals("")) {
				parameterInfo.put("orgStation", orgStation);	
			}
			
			if (request.getParameter("orderType") != null) {
				parameterInfo.put("orderType", request.getParameter("orderType"));
			}
			
			if (request.getParameter("targetInfos") != null) {
				String[] nnoList = request.getParameter("targetInfos").split(",");
				parameterInfo.put("nnoList", nnoList);
			}
			
			if (request.getParameter("startDate") != null) {
				parameterInfo.put("fromDate", request.getParameter("startDate").replaceAll("-", ""));
			}
			
			if (request.getParameter("endDate") != null) {
				parameterInfo.put("toDate", request.getParameter("endDate").replaceAll("-", ""));
			}

			if (firstLanguage.startsWith("ko")) {
				filePath = realFilePath + "excel/downloadData/orderlist_sample_kr.xlsx";
				lang = "kr";
			} else {
				filePath = realFilePath + "excel/downloadData/orderlist_sample_en.xlsx";
				lang = "en";
			}
						
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
			
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 9);
			
			CellStyle leftStyle = wb.createCellStyle();
			leftStyle.setWrapText(false);
			leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
			leftStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			leftStyle.setFont(font);

			CellStyle rightStyle = wb.createCellStyle();
			rightStyle.setWrapText(false);
			rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			rightStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			rightStyle.setFont(font);
			
			CellStyle centerStyle = wb.createCellStyle();
			centerStyle.setWrapText(false);
			centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
			centerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			centerStyle.setFont(font);
			
			
			int rowNo = 1;
			int orderNumber = 1;
			
			ArrayList<ShpngListVO> orderList = new ArrayList<ShpngListVO>();
			orderList = mapper.selectExcelOrderList(parameterInfo);
			
			for (int i = 0; i < orderList.size(); i++) {
				orderList.get(i).dncryptData();
				row = sheet.createRow(rowNo);
				
				String itemInfo = orderList.get(i).getItemInfo();
				String[] itemOne = itemInfo.split("\\|\\^\\|");
				
				cell = row.createCell(0);
				cell.setCellValue(orderNumber);
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(orderList.get(i).getHawbNo());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(orderList.get(i).getOrderNo());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(3);
				cell.setCellValue(orderList.get(i).getOrderDate());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(orderList.get(i).getOrgStation());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(5);
				if (lang.equals("kr")) {
					cell.setCellValue(orderList.get(i).getDstnNationName());	
				} else {
					cell.setCellValue(orderList.get(i).getDstnNationEName());
				}
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(orderList.get(i).getCneeName());
				cell.setCellStyle(centerStyle);
			
				cell = row.createCell(7);
				cell.setCellValue(orderList.get(i).getNativeCneeName());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(orderList.get(i).getCneeTel());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(9);
				cell.setCellValue(orderList.get(i).getCneeZip());
				cell.setCellStyle(centerStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(orderList.get(i).getCneeAddr());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(11);
				cell.setCellValue(orderList.get(i).getCneeAddrDetail());
				cell.setCellStyle(leftStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(orderList.get(i).getBoxCnt());
				cell.setCellStyle(rightStyle);
				
				cell = row.createCell(13);
				cell.setCellValue(orderList.get(i).getUserWta());
				cell.setCellStyle(rightStyle);
				
				cell = row.createCell(14);
				cell.setCellValue(orderList.get(i).getTotalItemValue());
				cell.setCellStyle(rightStyle);				
				
				for (int j = 0; j < itemOne.length; j++) {
					String setOne = itemOne[j];
					
					if (setOne.isEmpty()) {
						continue;
					}
					
					String[] itemPart = setOne.split("\\|");
					String subNo = itemPart[0];
					String itemDetail = itemPart[1];
					String itemCnt = itemPart[2];
					String unitValue = itemPart[3];
					String itemValue = itemPart[4];
					String currency = itemPart[5];
					
					cell = row.createCell(15);
					cell.setCellValue(subNo);
					cell.setCellStyle(centerStyle);
					
					cell = row.createCell(16);
					cell.setCellValue(itemDetail);
					cell.setCellStyle(leftStyle);
					
					cell = row.createCell(17);
					cell.setCellValue(itemCnt);
					cell.setCellStyle(rightStyle);
					
					cell = row.createCell(18);
					cell.setCellValue(unitValue);
					cell.setCellStyle(rightStyle);
					
					cell = row.createCell(19);
					cell.setCellValue(itemValue);
					cell.setCellStyle(rightStyle);
					
					cell = row.createCell(20);
					cell.setCellValue(currency);
					cell.setCellStyle(centerStyle);
					
					if (itemOne.length - j > 1) {
						rowNo++;
						row = sheet.createRow(rowNo);
					}
				}
				
				rowNo++;
				orderNumber++;
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			wb.write(outputStream);
			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectCurrencyListUse() throws Exception {
		return mapper.selectCurrencyListUse();
	}

	@Override
	public ArrayList<HashMap<String, String>> selectTransCodeList(String orgStation) throws Exception {
		return mapper.selectTransCodeList(orgStation);
	}


}
