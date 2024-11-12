package com.example.temp.manager.service.impl;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.ibatis.io.ResolverUtil.Test;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.aci.service.impl.ApiServiceImpl;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.BizInfo;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.api.shipment.ExportDeclareAPI;
import com.example.temp.api.shipment.company.Dk;
import com.example.temp.api.shipment.company.Mus;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.impl.ComnServiceImpl;
import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.DlvChgInfoVO;
import com.example.temp.manager.vo.DlvPriceVO;
import com.example.temp.manager.vo.ExpLicenceExcelVO;
import com.example.temp.manager.vo.ExpLicenceListVO;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.HawbChkVO;
import com.example.temp.manager.vo.HawbListVO;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.ManifastVO;
import com.example.temp.manager.vo.ManifestVO;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderInspListOneVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.OrderListVO;
import com.example.temp.manager.vo.OrderRcptVO;
import com.example.temp.manager.vo.OrderWeightVO;
import com.example.temp.manager.vo.PriceVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StationDefaultVO;
import com.example.temp.manager.vo.StockAllVO;
import com.example.temp.manager.vo.StockFileVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.StockOutVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.VolumeVO;
import com.example.temp.manager.vo.WhNoticeFileVO;
import com.example.temp.manager.vo.WhNoticeVO;
import com.example.temp.manager.vo.insp.InnerProductVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.manager.vo.takein.TakeinOrderListVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UpdateOrderItemVO;
import com.example.temp.member.vo.UpdateOrderListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.ViewYslItemCode;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.ozon.OzonAPI;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PRAcroForm;
import com.openhtmltopdf.css.parser.property.PrimitivePropertyBuilders.VerticalAlign;

import net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ManagerMapper mapper;

	@Autowired
	private MemberMapper mapper2;

	@Autowired
	private ComnMapper comnMapper;

	@Autowired
	ComnServiceImpl comnServiceImpl;

	@Autowired
	ApiServiceImpl apiServiceImpl;

	@Autowired
	YongSungAPI ysApi;

	@Autowired
	OcsAPI ocsApi;

	@Autowired
	EfsAPI efsApi;

	@Autowired
	ItcAPI itcApi;

	@Autowired
	OzonAPI ozonApi;

	@Autowired
	FastboxAPI fastboxApi;
	
	@Autowired
	ExportDeclareAPI expDeclApi;
	
	@Autowired
	Dk dk;
	
	@Autowired
	Mus mus;

	@Value("${filePath}")
	String realFilePath;

	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	private SecurityKeyVO originKey = new SecurityKeyVO();

	LinkedBlockingQueue<HawbVO> queue = new LinkedBlockingQueue<HawbVO>();

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 500)
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/api/smtp/sendMail/mus", method = RequestMethod.GET)
	public void insertHawbQueue() throws Exception {
		try {
			if (queue.peek() != null) {
				HawbVO temp = new HawbVO();
				temp = queue.poll();
				//musExpReceive(temp);
				expNoReceive(temp);
				temp = null;
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
		}
	}

	public ArrayList<ManagerVO> testSelect() throws Exception {
		return mapper.getTest();
	}

	@Override
	public ArrayList<ManagerVO> selectInfo(String userId) throws Exception {
		return mapper.getSelectInfo(userId);
	}

	@Override
	public int selectTotalCountInfo(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getTotalCountInfo(infoMap);
	}

	@Override
	public ArrayList<ManagerVO> selectUserList(HashMap<String, Object> testMap) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ManagerVO> returnVal = new ArrayList<ManagerVO>();
		returnVal = mapper.getSelectUserList(testMap);
		for (int i = 0; i < returnVal.size(); i++) {
			returnVal.get(i).dncryptData();
		}
		return returnVal;
	}

	@Override
	public void insertMemberInfos(ManagerVO userInfoVO, HttpServletRequest request, InvUserInfoVO invUserInfo)
			throws Exception {
		if (userInfoVO.getUserId() != null || !userInfoVO.getUserId().equals(null)) {
			HashMap<String, String> tests = new HashMap<String, String>();
			tests.put("userId", userInfoVO.getUserId());
			tests.put("wUserId", userInfoVO.getWUserId());
			tests.put("wUserIp", userInfoVO.getWUserIp());
			tests.put("wDate", userInfoVO.getWDate());
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

		String uid = comnMapper.selectUID();
		userInfoVO.setUserUid(uid);
		comnMapper.insertMemberInfo(userInfoVO);
		invUserInfo.setInvUserId(userInfoVO.getUserId());
		invUserInfo.encryptData();
		comnMapper.insertInvUserInfo(invUserInfo);
		/*
		 * if (userInfoVO.getUserId() != null || !userInfoVO.getUserId().equals(null)) {
		 * mapper.deleteUserTrkCom(userInfoVO.getUserId()); if
		 * (!userInfoVO.getTrkComList().isEmpty()) { String test[] =
		 * userInfoVO.getTrkComList().split(","); HashMap<String, String> tests = new
		 * HashMap<String, String>(); tests.put("userId", userInfoVO.getUserId());
		 * tests.put("wUserId", userInfoVO.getWUserId()); tests.put("wUserIp",
		 * userInfoVO.getWUserIp()); tests.put("wDate", userInfoVO.getWDate()); for (int
		 * i = 0; i < test.length; i++) { String test2[] = test[i].split(" ");
		 * tests.put("orgNation", test2[0]); tests.put("dstnNation", test2[1]);
		 * tests.put("transCode", test2[2]); tests.put("seq", Integer.toString((i +
		 * 1))); mapper.insertUserTransCom(tests); } }
		 * mapper.insertMemberInfos(userInfoVO); }
		 */
	}

	@Override
	public ManagerVO selectUserInfo(String userId) throws Exception {
		// TODO Auto-generated method stub
		ManagerVO returnVal = new ManagerVO();
		returnVal = mapper.getSelectUserInfo(userId);
		if (returnVal != null)
			returnVal.dncryptData();
		return returnVal;
	}

	@Override
	public void updateUserInfo(ManagerVO userInfoVO, InvUserInfoVO invUserInfo, HttpServletRequest request)
			throws Exception {

		if (userInfoVO.getUserId() != null || !userInfoVO.getUserId().equals(null)) {
			mapper.deleteUserTrkCom(userInfoVO.getUserId());
			HashMap<String, String> tests = new HashMap<String, String>();
			tests.put("userId", userInfoVO.getUserId());
			tests.put("wUserId", userInfoVO.getWUserId());
			tests.put("wUserIp", userInfoVO.getWUserIp());
			tests.put("wDate", userInfoVO.getWDate());
			int indexCnt = 0;
			String transComList = request.getParameter("trkComList");
			String[] targets = transComList.split(",");
			System.out.println(transComList);
			for (int i = 0; i < targets.length; i++) {
				String[] temps = targets[i].split("_");
				tests.put("orgNation", temps[0]);
				tests.put("dstnNation", temps[1]);
				tests.put("transCode", temps[2]);

				tests.put("blType", request.getParameter("blType_" + targets[i]));

				tests.put("seq", Integer.toString((indexCnt + 1)));

				System.out.println(tests.toString());

				mapper.insertUserTransCom(tests);
			}

			mapper.deleteWebHookList(tests.get("userId"));
			for (int i = 0; i < request.getParameterMap().get("webHookDiv").length; i++) {
				HashMap<String, Object> webHookList = new HashMap<String, Object>();
				webHookList.put("divKr", request.getParameterMap().get("divKr")[i].toString());
				webHookList.put("webHookDiv", request.getParameterMap().get("webHookDiv")[i].toString());
				webHookList.put("webHookUrl", request.getParameterMap().get("webHookUrl")[i].toString());
				webHookList.put("userId", tests.get("userId"));
				webHookList.put("wUserId", tests.get("wUserId"));
				webHookList.put("wUserIp", tests.get("wUserIp"));
				mapper.insertWebHookList(webHookList);
			}

			mapper.updateUserInfo(userInfoVO);
			invUserInfo.setInvUserId(userInfoVO.getUserId());
			invUserInfo.encryptData();
			mapper.updateInvUserInfo(invUserInfo);
		}

	}

	@Override
	public int selectOrderInspcCnt(HashMap<String, Object> orderInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderInspcCnt(orderInfo);
	}

	@Override
	public ArrayList<OrderInspListVO> selectOrderInspcList(HashMap<String, Object> orderInfo) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<OrderInspListVO> orderInspList = new ArrayList<OrderInspListVO>();
		orderInspList = mapper.selectOrderInspcList(orderInfo);

		for (int index = 0; index < orderInspList.size(); index++)
			orderInspList.get(index).dncryptData();
		return orderInspList;
	}

	@Override
	public ArrayList<OrderInspListOneVO> selectOrderInspc(HashMap<String, Object> orderInfo) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<OrderInspListOneVO> orderInspList = new ArrayList<OrderInspListOneVO>();
		orderInspList = mapper.selectOrderInspc(orderInfo);
		for (int index = 0; index < orderInspList.size(); index++)
			orderInspList.get(index).dncryptData();
		HashMap<String, String> stockParameter = new HashMap<String, String>();
		for (int listIndex = 0; listIndex < orderInspList.size(); listIndex++) {
			orderInspList.get(listIndex)
					.setOrderInspItem(mapper.selectOrderInspcItem(orderInspList.get(listIndex).getNno()));
			stockParameter.put("nno", orderInspList.get(listIndex).getNno());
//			for (int itemIndex = 0; itemIndex < orderInspList.get(listIndex).getOrderInspItem().size(); itemIndex++) {
//				stockParameter.put("subNo", orderInspList.get(listIndex).getOrderInspItem().get(itemIndex).getSubNo());
//				orderInspList.get(listIndex).getOrderInspItem().get(itemIndex)
//						.setStockAllCol(mapper.selectWhMemo(stockParameter));
//				orderInspList.get(listIndex).getOrderInspItem().get(itemIndex).setCusItemVO(mapper.selectDefaultInfo(
//						orderInspList.get(listIndex).getOrderInspItem().get(itemIndex).getCusItemCode()));
//			}
		}
		return orderInspList;
	}

	@Override
	public ArrayList<MawbVO> selectMawbList(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMawbList(orgStation);
	}

	@Override
	public void insertMawb(MawbVO mawbVO) throws Exception {
		// TODO Auto-generated method stub
		mawbVO.setArrDate(mawbVO.getArrDate().replaceAll("-", ""));
		mawbVO.setDepDate(mawbVO.getDepDate().replaceAll("-", ""));
		mawbVO.setMawbNo(mawbVO.getMawbNo().replaceAll(" ", ""));
		mapper.insertMawb(mawbVO);
	}

	@Override
	public int existMawb(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.existMawb(mawbNo);
	}

	@Override
	public void updateMawb(MawbVO mawbVO) throws Exception {
		mapper.updateMawb(mawbVO);

	}

	@Override
	public ArrayList<StockResultVO> selectTestSS() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTestSS();
	}

	@Override
	public ArrayList<MawbVO> selectMawbList2(MawbVO mawbVO) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMawbList2(mawbVO);
	}

	@Override
	public ArrayList<HawbVO> selectHawbList(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbList(mawbNo);
	}

	@Override
	public void deleteMawbOne(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteMawbOne(mawbNo);
	}

	@Override
	@Transactional
	public String insertHawb(HawbVO hawbVo) throws Exception {
		String result = "";

		if (!hawbVo.getTransCode().equals("ACI-US")) {

			ProcedureVO rstProcedure = new ProcedureVO();
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("hawbNo", hawbVo.getHawbNo());
			parameters.put("mawbNo", hawbVo.getMawbNo());
			parameters.put("wUserId", hawbVo.getWUserId());
			parameters.put("wUserIp", hawbVo.getWUserIp());
			parameters.put("bagNo", hawbVo.getBagNo());
			rstProcedure = mapper.insertHawbMawb(parameters);
			if (!rstProcedure.getRstStatus().equals("SUCESS")) {
				result = rstProcedure.getRstMsg();
				return result;
			}

			String nno = mapper.selectNNOByHawbNo(hawbVo.getHawbNo());
			String transCode = mapper.selectTransCodeByNNO(nno);
			String orderNo = mapper.selectOrderNoFromNNO(nno);
			if (transCode.equals("ITC")) {
				itcApi.itcJsonUpdate(nno);
			} else if (transCode.equals("GTS")) {
				HashMap<String, Object> parameterHawb = new HashMap<String, Object>();
				parameterHawb.put("reference", "778");
				parameterHawb.put("transCode", "GTS");
				parameterHawb.put("hawbNo", hawbVo.getHawbNo());
				parameterHawb.put("orderNo", orderNo);
				parameterHawb.put("time", LocalDateTime.now(Clock.systemUTC()).minusHours(2));
				if (ozonApi.insertOzonTrack(parameterHawb)) {
					parameterHawb.put("reference", "779");
					parameterHawb.put("time", LocalDateTime.now(Clock.systemUTC()).minusMinutes(30));
					if (ozonApi.insertOzonTrack(parameterHawb)) {
						parameterHawb.put("time", LocalDateTime.now(Clock.systemUTC()));
						parameterHawb.put("reference", "780");
						if (ozonApi.insertOzonTrack(parameterHawb)) {

						}
					}
				}
			} else if (transCode.equals("FB") || transCode.equals("FB-EMS")) {
				/*
				 * ProcedureVO rtnVal = new ProcedureVO(); rtnVal =
				 * fastboxApi.requestFastboxDelivery(hawbVo.getHawbNo(), hawbVo.getMawbNo()); if
				 * (rtnVal.getRstStatus().equals("FAIL")) { rstProcedure.setRstMsg("FAIL"); }
				 */
			}

			hawbVo.setNno(nno);
			queue.add(hawbVo);

			result = rstProcedure.getRstMsg();
		}

		return result;
	}
	
	public void expNoReceive(HawbVO hawbVo) throws Exception {
		
		UserOrderListVO expOrderInfo = new UserOrderListVO();
		expOrderInfo = mapper.selectExportOrderInfo(hawbVo);
		String nno = "";
		
		if (expOrderInfo != null) {
			if (!expOrderInfo.getExpType().equals("N")) {
				HashMap<String, Object> apiResult = new HashMap<String, Object>();
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				nno = expOrderInfo.getNno();
				
				parameters.put("nno", nno);
				parameters.put("userId", expOrderInfo.getUserId());
				
				if (expOrderInfo.getExpType().equals("E")) { // 대교플러스 전송
					// API 연동 필요
					
				} else { // 무츄얼 전송
					String jsonVal = expDeclApi.createMusRequestBody(parameters);
					
					System.out.println("Create Mus Json RequestBody");
					System.out.println(jsonVal);
					
					String inputLine = null;
					StringBuffer outResult = new StringBuffer();
					
					URL url = new URL("https://if.tlogin.net/aci/exp/json/receive.do");
					// URL url = new URL("https://test");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
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
					
					conn.disconnect();
					
					System.out.println("Must Response ----->");
					System.out.println(outResult.toString());
					
					if (!outResult.toString().equals("")) {
						JSONObject json = new JSONObject(String.valueOf(outResult.toString()));
						
						if (json.optString("result_cd").equals("S")) {
							
							JSONObject json2 = new JSONObject(String.valueOf(json.get("result_data").toString()));
							JSONArray json3 = new JSONArray(String.valueOf(json2.get("awb_result").toString()));
							JSONObject json4 = json3.getJSONObject(0);
							HashMap<String, String> expLicenceIn = new HashMap<String, String>();
							expLicenceIn.put("expNo", json4.getString("expo_singo_no"));
							expLicenceIn.put("expRegNo", json4.getString("hawb"));
							expLicenceIn.put("nno", nno);
							
							mapper.updateLicenceInfoV2(expLicenceIn);
							
						}
					}
				}

			}
		}
		
	}

	public void musExpReceive(HawbVO hawbVo) throws Exception {
		System.out.println("Queue start");
		HawbVO hawbNoInfo = new HawbVO();
		ProcedureVO resultProcedureVo = new ProcedureVO();
		String nno = "";
		int cnt = expLicenceChk(hawbVo);
		if (cnt != 0) {
			ArrayList<ExpLicenceExcelVO> expLicence = new ArrayList<ExpLicenceExcelVO>();
			nno = mapper.selectNNOByHawbNo(hawbVo.getHawbNo());
			mapper.updateExpLicenceInfo(hawbVo.getHawbNo());
			expLicence = mapper.selectExcelLicenceEFS(nno);
			hawbVo.setNno(nno);

			if (expLicence.get(0).getExpNo().equals("")) {
				String jsonVal = makeExplicence(hawbVo, expLicence);
				String inputLine = null;
				StringBuffer outResult = new StringBuffer();
				try {
					HashMap<String, Object> sendData = new HashMap<String, Object>();
					sendData.put("hawbNo", hawbVo.getNno());
					sendData.put("serviceName", "explicenceSendMusReady");
					sendData.put("status", "Success");
					sendData.put("sequence", "Mus Url Call");
					comnServiceImpl.insertSendTable(sendData);
					URL url = new URL("https://if.tlogin.net/aci/exp/json/receive.do");
					// URL url = new URL("https://test");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setRequestProperty("Accept-Language", "ko-KR");
					conn.setConnectTimeout(50000);
					conn.setReadTimeout(50000);

					OutputStream os = conn.getOutputStream();

					os.write(jsonVal.getBytes());
					os.flush();
					sendData = new HashMap<String, Object>();
					sendData.put("hawbNo", hawbVo.getNno());
					sendData.put("serviceName", "explicenceSendMus");
					sendData.put("status", "Success");
					sendData.put("sequence", "Mus Url Connect");
					comnServiceImpl.insertSendTable(sendData);
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					while ((inputLine = in.readLine()) != null) {
						outResult.append(inputLine);
					}
					conn.disconnect();
					sendData = new HashMap<String, Object>();
					sendData.put("hawbNo", hawbVo.getNno());
					sendData.put("serviceName", "explicenceSendMusEnd");
					sendData.put("status", "Success");
					sendData.put("sequence", "Mus Url Disconnect");
					comnServiceImpl.insertSendTable(sendData);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("Exception", e);
				}
				resultProcedureVo = getMusResult(outResult.toString(), hawbVo.getHawbNo(), nno);
			} else {
				HashMap<String, Object> sendData = new HashMap<String, Object>();
				sendData.put("hawbNo", hawbVo.getNno());
				sendData.put("serviceName", "explicenceSendMusFail");
				sendData.put("status", "Fail");
				sendData.put("sequence", "already receive explicence > NNO : " + nno + " expLicence No : "
						+ expLicence.get(0).getExpNo());
				comnServiceImpl.insertSendTable(sendData);
			}

		}

		if (resultProcedureVo.getRstStatus().equals("S")) {
			hawbNoInfo = mapper.selectHawbInfo(hawbVo);
			if (hawbNoInfo.getTransCode().equals("YSL")) {
				// String rtn = ysApi.fnMakeYSUpdateExpLicenceNoJson(nno);
			} else if (hawbNoInfo.getTransCode().equals("EFS")) {

			}
		}
	}

	@Override
	public String hawbCount(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.hawbCount(mawbNo);
	}

	@Override
	public void updateHawbMawb(MawbVO mawbVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateHawbMawb(mawbVO);
	}

	@Override
	public ArrayList<String> selectUserTrkNation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTrkNation(userId);
	}

	@Override
	public ArrayList<OrderInspVO> selectOrderInspcItem(String targetNNO) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderInspcItem(targetNNO);
	}

	@Override
	public HawbVO selectHawbInfo(HawbVO hawbVo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbInfo(hawbVo);
	}

	@Override
	public void deleteUserInfo(String targetParm) throws Exception {
		// TODO Auto-generated method stub
		String[] targets = targetParm.split(",");
		for (int roop = 0; roop < targets.length; roop++) {
			mapper.deleteUserInfo(targets[roop]);
		}
	}

	@Override
	public void resetUserPw(ManagerVO userInfoVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.resetUserPw(userInfoVO);
	}

	@Override
	public ArrayList<TransComVO> selectTransCom() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCom();
	}

	@Override
	public void deleteTransCom(String parameter) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteTransCom(parameter);
	}

	@Override
	public void updateTransCom(TransComVO transComVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateTransCom(transComVO);
	}

	@Override
	public void transComInsert(TransComVO transComVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.transComInsert(transComVO);
	}

	@Override
	public void insertTransZone(ZoneVO zoneVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertTransZone(zoneVO);
	}

	@Override
	public void selectTest(ZoneVO zoneVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.selectTest(zoneVO);
	}

	@Override
	public void insertPrice(PriceVO priceVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertPrice(priceVO);
	}

	@Override
	public int selectTotalCountNation(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountNation(infoMap);
	}

	@Override
	public ArrayList<ZoneVO> selectRgstNationList(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRgstNationList(infoMap);
	}

	@Override
	public void updateTransZone(ZoneVO zoneVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateTransZone(zoneVO);
	}

	@Override
	public int selectTotalCountZone(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountZone(infoMap);
	}

	@Override
	public ArrayList<PriceVO> selectRgstZoneList(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRgstZoneList(infoMap);
	}

	@Override
	public PriceVO selectZoneOne(PriceVO priceVO) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectZoneOne(priceVO);
	}

	@Override
	public void updatePrice(PriceVO priceVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.updatePrice(priceVO);
	}

	@Override
	public ArrayList<String> selectUserOrgNation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserOrgNation(userId);
	}

	@Override
	public ArrayList<String> selectUserDstnNation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserDstnNation(userId);
	}

	@Override
	public void insertNotice(NoticeVO noticeInfoVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertNotice(noticeInfoVO);
	}

	@Override
	public HashMap<String, ArrayList<ShpngListVO>> selectShpngList(HashMap<String, Object> parameter, String orderType)
			throws Exception {
		// TODO Auto-generated method stub

		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>>();
		parameter.put("orderType", orderType);
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try {
			selectShpngList = mapper.selectShpngList(parameter);
			for (int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
			}
		} catch (Exception e) {
			// TODO: handle exception
			return returnMap;
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
		return returnMap;
	}

	@Override
	public void deleteOrderList(String[] nnoList, String[] targetUserList, String userId, String remoteAddr)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> deleteInfo = new HashMap<String, String>();
		deleteInfo.put("userId", userId);
		deleteInfo.put("userIp", remoteAddr);
		for (int i = 0; i < nnoList.length; i++) {
			deleteInfo.put("nno", nnoList[i]);
			mapper.deleteOrderList(deleteInfo);
			// comnServiceImpl.updateUserOrderListStatus(targetUserList[i]);
		}

	}

	@Override
	public HashMap<String, ArrayList<ShpngListVO>> selectRecoveryList(HashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub

		// HashMap<String, String> parameter = new HashMap<String, String>();
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>>();
		// parameter.put("orgStation", orgStation);
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		int tatgets = 0;
		try {

			selectShpngList = mapper.selectDeleteList(params);
			for (int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
				tatgets = index;
			}

			tempSelectShpngList.add(selectShpngList.get(0));
			// 12593
			// selectShpngList.size()
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
			return returnMap;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
			return returnMap;
		}
	}

	@Override
	public void recoveryOrderList(String[] dnoList, String userId, String remoteAddr) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> recoverInfo = new HashMap<String, String>();
		recoverInfo.put("userId", userId);
		recoverInfo.put("userIp", remoteAddr);
		for (int i = 0; i < dnoList.length; i++) {
			recoverInfo.put("dno", dnoList[i]);
			mapper.recoveryOrderList(recoverInfo);
		}
	}

	@Override
	public void insertStockFile(StockAllVO stockAll) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertStockFile(stockAll);
	}

	@Override
	public void insertStockMsg(StockAllVO stockAll) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertStockMsg(stockAll);
	}

	@Override
	public StockResultVO insertStock(StockAllVO stockAll) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertStock(stockAll);
	}

	@Override
	public ArrayList<StockVO> selectStockByGrpIdx(String groupIdx) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockByGrpIdx(groupIdx);
	}

	@Override
	public void insertStockVolumn(StockAllVO stockAll) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertStockVolumn(stockAll);
	}

	@Override
	public ArrayList<UserOrderItemVO> selectUserRegistOrderItemOne(UserOrderListVO userOrder) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserRegistOrderItemOne(userOrder);
	}

	@Override
	public ArrayList<InspStockListVO> selectInspStockList(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectInspStockList(parameterInfo);
	}

	@Override
	public String selectAdminStation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectAdminStation(userId);
	}

	@Override
	public ArrayList<InspStockListVO> selectInspStockOrderInfo(HashMap<String, Object> parameters) throws Exception {
		ArrayList<InspStockListVO> returnList = new ArrayList<InspStockListVO>();

		returnList = mapper.selectInspStockList(parameters);
		for (int index = 0; index < returnList.size(); index++) {
			InspStockListVO temp = new InspStockListVO();
			temp = returnList.get(index);
			parameters.put("nno", temp.getNno());
			parameters.put("subNo", temp.getSubNo());
			parameters.put("groupIdx", temp.getGroupIdx());
			returnList.get(index).setFilePath(mapper.selectFilePath(parameters));
		}

		return returnList;
	}

	@Override
	@Transactional
	public ArrayList<StockResultVO> orderInspcWhProcess(HttpServletRequest request, MultipartHttpServletRequest multi,
			MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());

		ServerSocket ss = null;
		ArrayList<StockResultVO> targetStockList = new ArrayList<StockResultVO>();
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int itemCnt = Integer.parseInt(request.getParameter("itemCnt"));
		Map<String, String[]> parameterMaps = request.getParameterMap();
		String nno = request.getParameter("nno");
		String userId = request.getParameter("userId");
		StockAllVO stockAll = new StockAllVO();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		stockAll.setNno(nno);
		stockAll.setUserId(userId);
		stockAll.setOrgStation(request.getSession().getAttribute("ORG_STATION").toString());
		stockAll.setRackCode(request.getParameter("rack"));
		stockAll.setWhStatus(request.getParameter("whStatus"));
		stockAll.setWUserId(member.getUsername());
		stockAll.setWUserIp(request.getRemoteHost());
		stockAll.setWhInDate(time1);
		int pivotIndex1 = 0;
		int pivotIndex2 = 0;
		String swichGroupIdx = "";
		for (int itemIndex = 0; itemIndex < itemCnt; itemIndex++) {
			pivotIndex1 = pivotIndex2;
			pivotIndex2 = pivotIndex2 + Integer.parseInt(parameterMaps.get("itemImgCnt")[itemIndex]);

			if (parameterMaps.get("rgstYN")[itemIndex].equals("Y")) {
				StockResultVO temp = new StockResultVO();
				String ImageDir = multi.getSession().getServletContext().getRealPath("/") + "image/" + "stock/"
						+ stockAll.getNno() + "/";
				stockAll.setSubNo(parameterMaps.get("subNo")[itemIndex]);
				stockAll.setTrkCom(parameterMaps.get("trackComName")[itemIndex]);
				stockAll.setTrkNo(parameterMaps.get("comTrackNo")[itemIndex]);
				int initItemCnt = 0;
				if (parameterMaps.get("addOkCnt")[itemIndex] == null
						|| parameterMaps.get("addOkCnt")[itemIndex].isEmpty()) {
					initItemCnt = Integer.parseInt(parameterMaps.get("housCnt")[itemIndex]);
				} else {
					initItemCnt = Integer.parseInt(parameterMaps.get("housCnt")[itemIndex])
							- Integer.parseInt(parameterMaps.get("addOkCnt")[itemIndex])
							- Integer.parseInt(parameterMaps.get("addFailCnt")[itemIndex]);
				}

				stockAll.setWhInCnt(Integer.toString(initItemCnt));
				stockAll.setWhMemo(parameterMaps.get("orderMemo")[itemIndex]);
				stockAll.setWidth(parameterMaps.get("width")[itemIndex]);
				stockAll.setHeight(parameterMaps.get("height")[itemIndex]);
				stockAll.setLength(parameterMaps.get("length")[itemIndex]);
				stockAll.setWta(parameterMaps.get("wta")[itemIndex]);
				stockAll.setWtc(parameterMaps.get("wtc")[itemIndex]);
				stockAll.setPer(parameterMaps.get("per")[itemIndex]);
				stockAll.setWtUnit(parameterMaps.get("wtUnit")[itemIndex]);
				stockAll.setDimUnit(parameterMaps.get("dimUnit")[itemIndex]);

				File dir = new File(ImageDir);
				if (!dir.isDirectory()) {
					dir.mkdir();
				}
				ImageDir = ImageDir + stockAll.getSubNo() + "/";
				File dir2 = new File(ImageDir);
				if (!dir2.isDirectory()) {
					dir2.mkdir();
				}

				stockAll.setGroupIdx(swichGroupIdx);
				temp = insertStock(stockAll);
				temp.setBrand(parameterMaps.get("brand")[itemIndex]);
				temp.setCneeName(request.getParameter("cneeName"));
				temp.setItemDetail(parameterMaps.get("itemDetail")[itemIndex]);
				stockAll.setGroupIdx(temp.getGroupIdx());
				int fileIdx = 0;
				for (int fileIndex = pivotIndex1; fileIndex < pivotIndex2; fileIndex++) {
					fileIdx++;
					file = multi.getFiles("input_imgs").get(fileIndex);
					if (file.isEmpty()) {
						break;
					}
					try {
						amazonS3 = new AmazonS3Client(awsCredentials);
						FileOutputStream fos = new FileOutputStream(
								ImageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
						InputStream is = file.getInputStream();
						int readCount = 0;
						byte[] buffer = new byte[1024];
						while ((readCount = is.read(buffer)) != -1) {
							fos.write(buffer, 0, readCount);
						}
						is.close();
						fos.close();
						File uploadFile = new File(ImageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
						if (amazonS3 != null && !"".equals(file.getOriginalFilename())) {
							PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()
									+ "/outbound/stock/" + stockAll.getNno() + "/" + stockAll.getSubNo(),
									uploadFile.getName(), uploadFile);
							putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
							amazonS3.putObject(putObjectRequest);
						}
						stockAll.setFileDir("img.mtuai.com/outbound/stock/" + stockAll.getNno() + "/"
								+ stockAll.getSubNo() + "/" + uploadFile.getName());
						stockAll.setFileIdx(Integer.toString(fileIdx));

						insertStockFile(stockAll);
						uploadFile.delete();
						amazonS3 = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}

				if (temp.getGroupIdx() != swichGroupIdx) {
					targetStockList.add(temp);
				}
				StockResultVO temp2 = new StockResultVO();
				stockAll.setAddPassCnt(parameterMaps.get("addOkCnt")[itemIndex]);
				stockAll.setAddFailCnt(parameterMaps.get("addFailCnt")[itemIndex]);
				try {
					temp2 = mapper.execStockAddInsert(stockAll);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("Exception", e);
				}
				swichGroupIdx = stockAll.getGroupIdx();
				temp2.setGroupIdx(temp2.getRstIdx());
				stockAll.setGroupIdx(temp2.getRstIdx());

				targetStockList.add(temp2);

				// 추가 재고 입고 분에 대해 등록 하고 사진 있으면 추가하는 부분
				int fileSize = multi.getFiles("add_imgs" + (itemIndex + 1)).size();
				fileIdx = 0;
				for (int imgIndex = 0; imgIndex < fileSize; imgIndex++) {
					file = multi.getFiles("add_imgs" + (itemIndex + 1)).get(imgIndex);
					fileIdx++;
					if (file.isEmpty()) {
						break;
					} else {
						try {
							amazonS3 = new AmazonS3Client(awsCredentials);
							FileOutputStream fos = new FileOutputStream(
									ImageDir + stockAll.getSubNo() + "_ADD_" + file.getOriginalFilename());
							InputStream is = file.getInputStream();
							int readCount = 0;
							byte[] buffer = new byte[1024];
							while ((readCount = is.read(buffer)) != -1) {
								fos.write(buffer, 0, readCount);
							}
							is.close();
							fos.close();
							File uploadFile = new File(
									ImageDir + stockAll.getSubNo() + "_ADD_" + file.getOriginalFilename());
							if (amazonS3 != null && !"".equals(file.getOriginalFilename())) {
								try {
									PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()
											+ "/outbound/stock/" + stockAll.getNno() + "/" + stockAll.getSubNo(),
											uploadFile.getName(), uploadFile);
									putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
									amazonS3.putObject(putObjectRequest);
								} catch (Exception e) {
									// TODO: handle exception
									logger.error("Exception", e);
								}
							}
							stockAll.setFileDir("img.mtuai.com/outbound/stock/" + stockAll.getNno() + "/"
									+ stockAll.getSubNo() + "/" + uploadFile.getName());
							stockAll.setFileIdx(Integer.toString(fileIdx));
							insertStockFile(stockAll);
							uploadFile.delete();
							amazonS3 = null;
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}

		}
		return targetStockList;
	}

	@Override
	@Transactional
	public ArrayList<StockResultVO> selectStockResultVO(HttpServletRequest request, String groupIdx) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		ArrayList<StockResultVO> targetStockList = new ArrayList<StockResultVO>();
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		targetStockList.addAll(mapper.selectStockResultVO(groupIdx, request.getParameter("nno")));

		return targetStockList;
	}

	@Override
	public void execStockDel(HashMap<String, String> cancleInspInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.execStockDel(cancleInspInfo);
	}

	@Override
	public StationDefaultVO selectStationDefaultCond(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStationDefaultCond(userId);
	}

	@Override
	public HawbChkVO execNomalHawbChk(HawbVO hawbVo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.execNomalHawbChk(hawbVo);
	}

	@Override
	@Transactional
	public String execNomalHawbIn(OrderRcptVO orderRcpt, VolumeVO volume, Map<String, String[]> parameterMaps,
			HttpServletRequest request) throws Exception {
		String result = "";
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		ProcedureVO procedure = new ProcedureVO();
		ProcedureVO rtnVal = new ProcedureVO();
		ProcedureVO procedureVol = new ProcedureVO();
		procedure = mapper.execNomalHawbIn(orderRcpt);
		String nno = procedure.getRstNno();
		String transCode = "";
		transCode = mapper.selectTransCodeByNNO(nno);
		if (procedure.getRstStatus().equals("SUCCESS")) {
			volume.setNno(nno);
			int roopCnt = parameterMaps.get("per").length;
			mapper.deleteVolume(volume.getNno());
			for (int volumeIndex = 0; volumeIndex < roopCnt; volumeIndex++) {
				volume.setWidth(parameterMaps.get("width")[volumeIndex]);
				volume.setHeight(parameterMaps.get("height")[volumeIndex]);
				volume.setLength(parameterMaps.get("length")[volumeIndex]);
				volume.setPer(parameterMaps.get("per")[volumeIndex]);
				try {
					procedureVol = mapper.execNomalHawbVolume(volume);
					if (!procedureVol.getRstStatus().equals("SUCCESS")) {
						result = procedureVol.getRstMsg();
						break;
					}
					result = "S";
				} catch (Exception e) {
					// TODO: handle exception
					result = "F";
				}
			}
			if (result.equals("S")) {
				/*
				if (transCode.equals("YSL")) {
					if (ysApi.selectMatchNoByNNo(nno) == null) {
						String ysRtn = ysApi.fnMakeYongsungJson(nno);
						rtnVal = ysApi.getYongSungRegNo(ysRtn, nno, userId, userIp);
						result = rtnVal.getRstMsg();
					}
				} else if (transCode.equals("EFS")) {
					if (efsApi.selectMatchNoByNNo(nno) == null) {
						String rtnMsg = efsApi.fnMakeEfsJson(nno);
						rtnVal = efsApi.getCheckResult(rtnMsg, nno);
						result = rtnVal.getRstMsg();
					}
				}
				*/
			}

		} else {
			result = procedure.getRstMsg();
		}
		return result;
	}

	@Override
	public ProcedureVO execNomalHawbVolume(VolumeVO volume) throws Exception {
		// TODO Auto-generated method stub

		return mapper.execNomalHawbVolume(volume);
	}

	@Override
	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMsgHist(parameters);
	}

	@Override
	public void insertMsgInfo(StockMsgVO msgInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertMsgInfo(msgInfo);
	}

	@Override
	public ArrayList<HawbListVO> selectRegistHawbList(String wUserId, String transCode) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRegistHawbList(wUserId, transCode);
	}

	@Override
	@Transactional
	public String insertHawbMawb(String mawbNo, String[] hawbList, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> parameters = new HashMap<String, String>();
		HashMap<String, Object> parameterHawb = new HashMap<String, Object>();
		ProcedureVO rstProcedure = new ProcedureVO();
		String result = "S";
		parameters.put("mawbNo", mawbNo);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));
		for (int hawbIndex = 0; hawbIndex < hawbList.length; hawbIndex++) {
			parameters.put("hawbNo", hawbList[hawbIndex]);
			rstProcedure = mapper.insertHawbMawb(parameters);
			if (!rstProcedure.getRstStatus().equals("SUCESS")) {
				result = rstProcedure.getRstMsg();
				break;
			}
			String nno = mapper.selectNNOByHawbNo(hawbList[hawbIndex]);
			String transCode = mapper.selectTransCodeByNNO(nno);
			if (transCode.equals("GTS")) {
				String orderNo = mapper.selectOrderNoFromNNO(nno);
				parameterHawb.put("reference", "781");
				parameterHawb.put("transCode", transCode);
				parameterHawb.put("hawbNo", hawbList[hawbIndex]);
				parameterHawb.put("orderNo", orderNo);
				parameterHawb.put("time", LocalDateTime.now(Clock.systemUTC()));
				if (ozonApi.insertOzonTrack(parameterHawb)) {

				}
			}

			result = "S";
		}
		return result;
	}

	@Override
	public String execMawbCancle(String[] hawbList, String wUserId, String wUserIp) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> parameters = new HashMap<String, String>();
		ProcedureVO rstProcedure = new ProcedureVO();
		String result = "S";
		parameters.put("wUserId", wUserId);
		parameters.put("wUserIp", wUserIp);
		for (int hawbIndex = 0; hawbIndex < hawbList.length; hawbIndex++) {
			parameters.put("hawbNo", hawbList[hawbIndex]);
			rstProcedure = mapper.execMawbCancle(parameters);
			if (!rstProcedure.getRstStatus().equals("SUCESS")) {
				result = rstProcedure.getRstMsg();
				break;
			}
			result = "S";
		}
		return result;
	}

	@Override
	public ArrayList<OrderListVO> selectOrderRcptList(OrderListVO parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderRcptList(parameters);
	}

	@Override
	public ProcedureVO execOrderRcptList(String[] targetNno, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> parameters = new HashMap<String, String>();
		ProcedureVO rtnVal = new ProcedureVO();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		parameters.put("wUserId", userId);
		parameters.put("wUserIp", userIp);
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		for (int nnoIndex = 0; nnoIndex < targetNno.length; nnoIndex++) {
			if (targetNno[nnoIndex].equals(""))
				continue;

			String[] targetInfos = targetNno[nnoIndex].split("[,]");
			String nno = targetInfos[1];
			String transCode = targetInfos[0];
			parameters.put("nno", nno);
			if (transCode.equals("YSL")) {
				if (ysApi.selectMatchNoByNNo(nno) == null) {
					String ysRtn = ysApi.fnMakeYongsungJson(nno);
					rtnVal = ysApi.getYongSungRegNo(ysRtn, nno, userId, userIp);
				} else {
					rtnVal.setRstStatus("SUCCESS");
				}
				/* ysApi.fnMakeYSUpdateExpLicenceNoJson(nno); */
			} else if (transCode.equals("EFS")) {
				if (efsApi.selectMatchNoByNNo(nno) == null) {
					String efsVal = efsApi.fnMakeEfsJson(nno);
					if (efsVal.equals("FAIL")) {
						rtnVal.setRstStatus("FAIL");
						rtnVal.setRstMsg("주문번호 " + mapper.selectOrderNoFromNNO(nno) + "가 API 전송에 실패했습니다.");
						break;
					}
					rtnVal = efsApi.getCheckResult(efsVal, nno);
				}
			} else {
				rtnVal.setRstStatus("SUCCESS");
			}
			if (!rtnVal.getRstStatus().equals("SUCCESS")) {
				rtnVal.setRstStatus("FAIL");
				break;
			}
			rtnVal = mapper.execOrderRcptList(parameters);
		}
		return rtnVal;
	}

	@Override
	public String selectManifastMawb(String mawbNo, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		ArrayList<ManifastVO> targetMani = new ArrayList<ManifastVO>();
		targetMani = mapper.selectManifastMawb(mawbNo);
		String filename = mawbNo;
		// 워크북 생성
		try {
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Manifast");
			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			// 테이블 헤더용 스타일
			CellStyle headStyle = wb.createCellStyle();
			// 가는 경계선을 가집니다.

			row = sheet.createRow(rowNo++);

			headStyle.setBorderTop(CellStyle.BORDER_THIN);

			headStyle.setBorderBottom(CellStyle.BORDER_THIN);

			headStyle.setBorderLeft(CellStyle.BORDER_THIN);

			headStyle.setBorderRight(CellStyle.BORDER_THIN);

			// 배경색은 노란색입니다.
			/*
			 * headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
			 * headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			 */
			// 데이터는 가운데 정렬합니다.
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);

			// 데이터용 경계 스타일 테두리만 지정
			CellStyle bodyStyle = wb.createCellStyle();
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);

			// 헤더 생성
			cell = row.createCell(0);
			cell.setCellStyle(headStyle);
			cell.setCellValue("CS ID");

			cell = row.createCell(1);
			cell.setCellStyle(headStyle);
			cell.setCellValue("SAGAWA No");

			cell = row.createCell(2);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Carton No");

			cell = row.createCell(3);
			cell.setCellStyle(headStyle);
			cell.setCellValue("SHIPPER NAME");
			cell = row.createCell(4);
			cell.setCellStyle(headStyle);
			cell.setCellValue("SHIPPER TEL");
			cell = row.createCell(5);
			cell.setCellStyle(headStyle);
			cell.setCellValue("SHIPPER ADD");
			cell = row.createCell(6);
			cell.setCellStyle(headStyle);
			cell.setCellValue("CNEE TEL NO");
			cell = row.createCell(7);
			cell.setCellStyle(headStyle);
			cell.setCellValue("CNEE NAME(JAPANESS)");
			cell = row.createCell(8);
			cell.setCellStyle(headStyle);
			cell.setCellValue("CNEE NAME(ENG)");
			cell = row.createCell(9);
			cell.setCellStyle(headStyle);
			cell.setCellValue("POST CODE");
			cell = row.createCell(10);
			cell.setCellStyle(headStyle);
			cell.setCellValue("ADD");
			cell = row.createCell(11);
			cell.setCellStyle(headStyle);
			cell.setCellValue("weight(KG)");
			cell = row.createCell(12);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Length(CM)");
			cell = row.createCell(13);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Width(CM)");
			cell = row.createCell(14);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Height(CM)");
			cell = row.createCell(15);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Declare Quantity");
			cell = row.createCell(16);
			cell.setCellStyle(headStyle);
			cell.setCellValue("CURRENCY");
			cell = row.createCell(17);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Q10 CODE");
			cell = row.createCell(18);
			cell.setCellStyle(headStyle);
			cell.setCellValue("1st declare commodity");
			cell = row.createCell(19);
			cell.setCellStyle(headStyle);
			cell.setCellValue("material content");
			cell = row.createCell(20);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Quantity");
			cell = row.createCell(21);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Unit Price");
			cell = row.createCell(22);
			cell.setCellStyle(headStyle);
			cell.setCellValue("2nd declare commodity");
			cell = row.createCell(23);
			cell.setCellStyle(headStyle);
			cell.setCellValue("material content");
			cell = row.createCell(24);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Quantity");
			cell = row.createCell(25);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Unit Price");
			cell = row.createCell(26);
			cell.setCellStyle(headStyle);
			cell.setCellValue("3nd declare commodity");
			cell = row.createCell(27);
			cell.setCellStyle(headStyle);
			cell.setCellValue("material content");
			cell = row.createCell(28);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Quantity");
			cell = row.createCell(29);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Unit Price");
			cell = row.createCell(30);
			cell.setCellStyle(headStyle);
			cell.setCellValue("4th declare commodity");
			cell = row.createCell(31);
			cell.setCellStyle(headStyle);
			cell.setCellValue("material content");
			cell = row.createCell(32);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Quantity");
			cell = row.createCell(33);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Unit Price");
			cell = row.createCell(34);
			cell.setCellStyle(headStyle);
			cell.setCellValue("5th declare commodity");
			cell = row.createCell(35);
			cell.setCellStyle(headStyle);
			cell.setCellValue("material content");
			cell = row.createCell(36);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Quantity");
			cell = row.createCell(37);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Unit Price");
			cell = row.createCell(38);
			cell.setCellStyle(headStyle);
			cell.setCellValue("Country of Origin");

			// 데이터 부분 생성
			for (ManifastVO target : targetMani) {
				target.dncryptData();
				row = sheet.createRow(rowNo++);
//		        cell = row.createCell(0);
//			    cell.setCellStyle(bodyStyle);
//			    cell.setCellValue(target.getCsid());
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getHawbNo());
				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue("");
				cell = row.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getBoxCnt());
				cell = row.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getShipperName());
				cell = row.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getShipperTel());
				cell = row.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getShipperAddr());
				cell = row.createCell(6);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getCneeTel());
				cell = row.createCell(7);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getNativeCneeName());
				cell = row.createCell(8);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getCneeName());
				cell = row.createCell(9);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getCneeZip());
				cell = row.createCell(10);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getNativeCneeAddr() + " " + target.getNativeCneeAddrDetail());
				cell = row.createCell(11);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUserWta());
				cell = row.createCell(12);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUserLength());
				cell = row.createCell(13);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUserWidth());
				cell = row.createCell(14);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUserHeight());
				cell = row.createCell(15);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getDeclareQuantity());
				cell = row.createCell(16);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getChgCurrency());

				cell = row.createCell(17);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getCusItemCode1());

				cell = row.createCell(18);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemDetail1());
				cell = row.createCell(19);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemMeterial1());
				cell = row.createCell(20);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemCnt1());
				cell = row.createCell(21);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUnitValue1());
				cell = row.createCell(22);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemDetail2());
				cell = row.createCell(23);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemMeterial2());
				cell = row.createCell(24);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemCnt2());
				cell = row.createCell(25);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUnitValue2());
				cell = row.createCell(26);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemDetail3());
				cell = row.createCell(27);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemMeterial3());
				cell = row.createCell(28);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemCnt3());
				cell = row.createCell(29);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUnitValue3());
				cell = row.createCell(30);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemDetail4());
				cell = row.createCell(31);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemMeterial4());
				cell = row.createCell(32);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemCnt4());
				cell = row.createCell(33);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUnitValue4());
				cell = row.createCell(34);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemDetail5());
				cell = row.createCell(35);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemMeterial5());
				cell = row.createCell(36);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getItemCnt5());
				cell = row.createCell(37);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getUnitValue5());
				cell = row.createCell(38);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(target.getMakeCntry());
				for (int i = 0; i < 39; i++) {
					sheet.autoSizeColumn(i);
					sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 512);
				}
			}

			// 컨텐츠 타입과 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=manifast_" + filename + ".xlsx");
			// 엑셀 출력

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			return "S";
		} catch (Exception e) {
			// TODO: handle exception
			return "F";
		}
	}

	@Override
	public ArrayList<SendVO> selectSendList(SendVO parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSendList(parameters);
	}

	@Override
	public void selectSendListExcelDown(SendVO sendVO, HashMap<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			ArrayList<SendVO> list = mapper.selectSendExcelList(params);

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/send/excelSample/sendListForm.xlsx";

			FileInputStream fis = new FileInputStream(filePath);

			Row row = null;
			Cell cell = null;

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);

			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 10);
			font.setFontName("맑은 고딕");

			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(false);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderTop(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			cellStyle.setFont(font);

			CellStyle rightStyle = wb.createCellStyle();
			rightStyle.setWrapText(false);
			rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			rightStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			rightStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			rightStyle.setBorderTop(CellStyle.BORDER_DOTTED);
			rightStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			rightStyle.setFont(font);

			CellStyle leftStyle = wb.createCellStyle();
			leftStyle.setWrapText(false);
			leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
			leftStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			leftStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			leftStyle.setBorderTop(CellStyle.BORDER_DOTTED);
			leftStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			leftStyle.setFont(font);

			int cellNo = 0;
			int rowNo = 1;

			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(rowNo);

				list.get(i).dncryptData();

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellValue(rowNo);
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getDepDate());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getUserId());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getNationCode());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getDstnNation());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getTransCode());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getMawbNo());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getHawbNo());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getValueMatchNo());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getOrderNo());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeName());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				if (list.get(i).getCneeTel().toString().equals("")) {
					cell.setCellValue(list.get(i).getCneeHp());
					cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(list.get(i).getCneeTel());
					cell.setCellStyle(cellStyle);
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeAddr() + " " + list.get(i).getCneeAddrDetail());
				cell.setCellStyle(leftStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeZip());
				cell.setCellStyle(cellStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getMainItem());
				cell.setCellStyle(leftStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getTotalValue());
				cell.setCellStyle(rightStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getWta());
				cell.setCellStyle(rightStyle);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getWtc());
				cell.setCellStyle(rightStyle);

				rowNo++;

			}

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + "Send List" + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	@Override
	public ArrayList<SendVO> selectUnSendList(SendVO parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUnSendList(parameters);
	}

	@Override
	public void updateUserOrderList(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList)
			throws Exception {
		// TODO Auto-generated method stub
//		mapper.deleteUserOrderListTmp(userOrderList.getNno(),userOrderList.getUserId());
//		mapper.deleteUserOrderItemTmp(userOrderList.getNno(),userOrderList.getUserId());
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		comnServiceImpl.deleteUserOrderItem(userOrderList.getNno());
		if (userOrderList.getOrderDate().equals("")) {
			userOrderList.setOrderDate(userOrderList.getWDate());
		} else {
			userOrderList.setOrderDate(userOrderList.getOrderDate().replaceAll("-", ""));
		}
		mapper2.updateUserOrderList(userOrderList);

		String[] tempString = new String[userOrderItemList.getItemDetail().length];
		String[] tempString2 = new String[userOrderItemList.getItemDetail().length];
		Arrays.fill(tempString, "");
		if (userOrderItemList.getTrkCom() == null) {
			userOrderItemList.setTrkCom(tempString);
		}
		if (userOrderItemList.getTrkNo() == null) {
			userOrderItemList.setTrkNo(tempString);
		}
		if (userOrderItemList.getTrkDate() == null) {
			Arrays.fill(tempString2, time1);
			userOrderItemList.setTrkDate(tempString2);
		}
		if (userOrderItemList.getHsCode() == null) {
			userOrderItemList.setHsCode(tempString);
		}
		if (userOrderItemList.getItemDetail() == null) {
			userOrderItemList.setItemDetail(tempString);
		}
		if (userOrderItemList.getItemCnt() == null) {
			userOrderItemList.setItemCnt(tempString);
		}
		if (userOrderItemList.getQtyUnit() == null) {
			userOrderItemList.setQtyUnit(tempString);
		}
		if (userOrderItemList.getBrandItem() == null) {
			userOrderItemList.setBrandItem(tempString);
		}
		if (userOrderItemList.getUnitValue() == null) {
			userOrderItemList.setUnitValue(tempString);
		}
		if (userOrderItemList.getItemDiv() == null) {
			userOrderItemList.setItemDiv(tempString);
		}
		if (userOrderItemList.getCusItemCode() == null) {
			userOrderItemList.setCusItemCode(tempString);
		}
		if (userOrderItemList.getItemMeterial() == null) {
			userOrderItemList.setItemMeterial(tempString);
		}
		if (userOrderItemList.getWtUnitItem() == null) {
			userOrderItemList.setWtUnitItem(tempString);
		}
		if (userOrderItemList.getUnitCurrency() == null) {
			userOrderItemList.setUnitCurrency(tempString);
		}
		if (userOrderItemList.getChgCurrency() == null) {
			userOrderItemList.setChgCurrency(tempString);
		}
		if (userOrderItemList.getMakeCntry() == null) {
			userOrderItemList.setMakeCntry(tempString);
		}
		if (userOrderItemList.getMakeCom() == null) {
			userOrderItemList.setMakeCom(tempString);
		}
		if (userOrderItemList.getItemUrl() == null) {
			userOrderItemList.setItemUrl(tempString);
		}
		if (userOrderItemList.getItemImgUrl() == null) {
			userOrderItemList.setItemImgUrl(tempString);
		}
		if (userOrderItemList.getUserItemWta() == null) {
			userOrderItemList.setUserItemWta(tempString);
		}
		if (userOrderItemList.getItemExplan() == null) {
			userOrderItemList.setItemExplan(tempString);
		}
		if (userOrderItemList.getItemBarcode() == null) {
			userOrderItemList.setItemBarcode(tempString);
		}
		if (userOrderItemList.getInBoxNum() == null) {
			userOrderItemList.setInBoxNum(tempString);
		}
		if (userOrderItemList.getTakeInCode() == null) {
			userOrderItemList.setTakeInCode(tempString);
		}
		if (userOrderItemList.getNativeItemDetail() == null) {
			userOrderItemList.setNativeItemDetail(tempString);
		}

		UserOrderItemVO orderItem = new UserOrderItemVO();
		for (int roop = 0; roop < userOrderItemList.getItemDetail().length; roop++) {
			/* userOrderItems.getBrand(). */
			if (userOrderItemList.getTrkCom()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkCom()[roop] = "";
			}
			if (userOrderItemList.getTrkNo()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkNo()[roop] = "";
			}
			if (userOrderItemList.getTrkDate()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkDate()[roop] = time1;
			}
			if (userOrderItemList.getHsCode()[roop].equals("d#none#b")) {
				userOrderItemList.getHsCode()[roop] = "";
			}
			if (userOrderItemList.getItemDetail()[roop].equals("d#none#b")) {
				userOrderItemList.getItemDetail()[roop] = "";
			}
			if (userOrderItemList.getItemCnt()[roop].equals("d#none#b")) {
				userOrderItemList.getItemCnt()[roop] = "";
			}
			if (userOrderItemList.getQtyUnit()[roop].equals("d#none#b")) {
				userOrderItemList.getQtyUnit()[roop] = "";
			}
			if (userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
				userOrderItemList.getBrandItem()[roop] = "";
			}
			if (userOrderItemList.getUnitValue()[roop].equals("d#none#b")) {
				userOrderItemList.getUnitValue()[roop] = "";
			}
			if (userOrderItemList.getItemDiv()[roop].equals("d#none#b")) {
				userOrderItemList.getItemDiv()[roop] = "";
			}
			if (userOrderItemList.getCusItemCode()[roop].equals("d#none#b")) {
				userOrderItemList.getCusItemCode()[roop] = "";
			}
			if (userOrderItemList.getItemMeterial()[roop].equals("d#none#b")) {
				userOrderItemList.getItemMeterial()[roop] = "";
			}
			if (userOrderItemList.getWtUnitItem()[roop].equals("d#none#b")) {
				userOrderItemList.getWtUnitItem()[roop] = "";
			}
			if (userOrderItemList.getUnitCurrency()[roop].equals("d#none#b")) {
				userOrderItemList.getUnitCurrency()[roop] = "";
			}
			if (userOrderItemList.getChgCurrency()[roop].equals("d#none#b")) {
				userOrderItemList.getChgCurrency()[roop] = "";
			}
			if (userOrderItemList.getMakeCntry()[roop].equals("d#none#b")) {
				userOrderItemList.getMakeCntry()[roop] = "";
			}
			if (userOrderItemList.getMakeCom()[roop].equals("d#none#b")) {
				userOrderItemList.getMakeCom()[roop] = "";
			}
			if (userOrderItemList.getItemUrl()[roop].equals("d#none#b")) {
				userOrderItemList.getItemUrl()[roop] = "";
			}
			if (userOrderItemList.getItemImgUrl()[roop].equals("d#none#b")) {
				userOrderItemList.getItemImgUrl()[roop] = "";
			}
			if (userOrderItemList.getUserItemWta()[roop].equals("d#none#b")) {
				userOrderItemList.getUserItemWta()[roop] = "";
			}
			if (userOrderItemList.getItemExplan()[roop].equals("d#none#b")) {
				userOrderItemList.getItemExplan()[roop] = "";
			}
			if (userOrderItemList.getItemBarcode()[roop].equals("d#none#b")) {
				userOrderItemList.getItemBarcode()[roop] = "";
			}
			if (userOrderItemList.getInBoxNum()[roop].equals("d#none#b")) {
				userOrderItemList.getInBoxNum()[roop] = "";
			}
			if (userOrderItemList.getNativeItemDetail()[roop].equals("d#none#b")) {
				userOrderItemList.getNativeItemDetail()[roop] = "";
			}

			orderItem.setNno(userOrderList.getNno());
			orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]); // 국내 택배
			orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]); // 국내 택배 운송장
			orderItem.setTrkDate(userOrderItemList.getTrkDate()[roop]); // 국내 택배 운송날짜
			orderItem.setHsCode(userOrderItemList.getHsCode()[roop]); // HS 코드
			orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]); // 상품명
			orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);// 상품 개수
			orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]);// 상품 단위
			orderItem.setBrand(userOrderItemList.getBrandItem()[roop]); // 브랜드
			orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]); // 상품 단가
			orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]); // 상품 종류
			orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]); // 상품코드
			orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); // 사입코드
			orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]); // 상품 재질
			orderItem.setWtUnit(userOrderItemList.getWtUnitItem()[roop]);// 무게 단위
			orderItem.setUnitCurrency(userOrderItemList.getUnitCurrency()[roop]);// 무게 단위
			orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[roop]);// 무게 단위
			orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]); // 제조국
			orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]); // 제조회사
			orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]); // 상품 URL
			orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]); // 상품 IMG URL
			orderItem.setOrgStation(userOrderList.getOrgStation());
			orderItem.setNationCode(userOrderList.getDstnNation());
			orderItem.setSubNo(Integer.toString(roop + 1));
			orderItem.setUserId(userOrderList.getUserId());
			orderItem.setWDate(userOrderList.getWDate());
			orderItem.setWUserId(userOrderList.getWUserId());
			orderItem.setWUserIp(userOrderList.getWUserIp());
			orderItem.setUserItemWta(userOrderItemList.getUserItemWta()[roop]);
			orderItem.setItemExplan(userOrderItemList.getItemExplan()[roop]);
			orderItem.setItemBarcode(userOrderItemList.getItemBarcode()[roop]);
			orderItem.setInBoxNum(userOrderItemList.getInBoxNum()[roop]);
			orderItem.setTransCode(userOrderList.getTransCode());
			orderItem.setNativeItemDetail(userOrderItemList.getNativeItemDetail()[roop]);
			/*
			 * orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]);
			 * orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]);
			 * orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]);
			 * orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]);
			 * orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);
			 * orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]);
			 * orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]);
			 * orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]);
			 * orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]);
			 * orderItem.setNationCode(userOrderItemList.getNationCode()[roop]);
			 * orderItem.setNationCode(""); orderItem.setNno(userOrderList.getNno());
			 * orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]);
			 * orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]);
			 * orderItem.setOrgStation(userOrderList.getOrgStation());
			 * orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]);
			 * orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]);
			 * orderItem.setSubNo(Integer.toString(roop+1));
			 * orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]);
			 * orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]);
			 * orderItem.setUserId(userOrderList.getUserId());
			 * orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]);
			 * orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]);
			 * orderItem.setWDate(userOrderList.getWDate());
			 * orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]);
			 * orderItem.setWUserId(userOrderList.getWUserId());
			 * orderItem.setWUserIp(userOrderList.getWUserIp());
			 * orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]);
			 */
			mapper2.insertUserOrderItem(orderItem);

		}
		
		/*
		if (userOrderList.getExpValue().equals("noExplicence")) {
			mapper.deleteExpLicenceInfo(userOrderList.getNno().toString());
		} else {
			updateExpLicenceInfo(userOrderList);
		}
		 */
		
		if (userOrderList.getExpType().equals("N")) {
			comnMapper.deleteExportDeclareInfo(userOrderList);
		} else {
			userOrderList.dncryptData();
			comnServiceImpl.execExportDeclareInfo(userOrderList);
		}
	}

	public void updateExpLicenceInfo(UserOrderListVO userOrderList) throws Exception {
		ExpLicenceVO licence = new ExpLicenceVO();

		licence.setExpRegNo(userOrderList.getHawbNo());
		licence.setOrderNo(userOrderList.getOrderNo());
		licence.setNno(userOrderList.getNno());
		licence.setExpBusinessNum(userOrderList.getExpBusinessNum());
		licence.setExpShipperCode(userOrderList.getExpShipperCode());
		licence.setExpBusinessName(userOrderList.getExpBusinessName());
		licence.setExpValue(userOrderList.getExpValue());
		licence.setExpNo(userOrderList.getExpNo());
		licence.setAgencyBusinessName(userOrderList.getAgencyBusinessName());

		if (userOrderList.getExpValue().equals("registExplicence1")) {
			licence.setSimpleYn("N");
			licence.setSendYn("N");
		} else if (userOrderList.getExpValue().equals("simpleExplicence")) {
			licence.setSimpleYn("Y");
			licence.setSendYn("Y");
		} else if (userOrderList.getExpValue().equals("registExplicence2")) {
			licence.setSimpleYn("N");
			licence.setSendYn("M");
		}

		int expCnt = expLicenceChk2(userOrderList.getHawbNo());
		if (expCnt == 0) {
			apiServiceImpl.insertExpBaseInfo(licence);
		} else {
			apiServiceImpl.insertExpBaseInfo(licence);
		}
	}

	@Override
	public String insertOrderWeightExcel(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("*********** excel upload ************");
		String result = "F";
		String pattern = "^[0-9]*$"; // 숫자만
		String patternFloat = "^[0-9]+(.[0-9]+)*$"; // 숫자만
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date time = new Date();
		DataFormatter formatter = new DataFormatter();

		String time1 = format1.format(time);
		String defaultOrderNo = format2.format(time);
		int currentRow = 0;

		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			OrderWeightVO orderWeightExcelVO = new OrderWeightVO();
			orderWeightExcelVO.setWUserId(parameters.get("wUserId"));
			orderWeightExcelVO.setWUserIp(parameters.get("wUserIp"));
			orderWeightExcelVO.setOrgStation(parameters.get("orgStation"));

			try {
				System.out.println("test1");
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					System.out.println(rows);
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // row=0은 헤더이므로 1부터 시작
						int perDim = 0;
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							currentRow = rowIndex;
							String rowResult = "Err!:";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								int valSize = 0;
								Date times2 = new Date();
								String time2 = format2.format(times2);
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]").length != 1) {
											if (value.split("[.]")[1].equals("0")) {
												value = value.split("[.]")[0];
											}
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

								switch (columnIndex) {
								case 12:
									if (value.equals("")) {
										continue;
									} else {
										// String hawbNo = mapper.selectHawbNo(value);
										orderWeightExcelVO.setHawbNo(value);
										perDim = mapper.selectPerDimByHawbNo(value);
										break;
									}
									// Item Number - HawbNo

								case 2:
									// Weight - wta
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWta(target);
											else
												orderWeightExcelVO.setWta("0");
											break;
										}
									}
									break;
								case 3:
									// Dim Wgt - wtc
									valSize = value.length();
									
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWtc(target);
											else
												orderWeightExcelVO.setWtc("0");
											break;
										}
									}
									break;
								case 6:
									// Length - Length
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setLength(target);
											else
												orderWeightExcelVO.setLength("0");
											break;
										}
									}
									break;
								case 5:
									// width - width
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWidth(target);
											else
												orderWeightExcelVO.setWidth("0");
											break;
										}
									}
									break;
								case 7:
									// Height - Height
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setHeight(target);
											else
												orderWeightExcelVO.setHeight("0");
											break;
										}
									}
									break;
								}
							} // 현재row vo에 set 완료
							/* OrderListVO */
							Double length = Double.parseDouble(orderWeightExcelVO.getLength());
							Double width = Double.parseDouble(orderWeightExcelVO.getWidth());
							Double height = Double.parseDouble(orderWeightExcelVO.getHeight());
							Double weightC = length * width * height / perDim; // TransCode = YSL
							orderWeightExcelVO.setPer(String.valueOf(perDim));
							orderWeightExcelVO.setWtc(String.valueOf(String.format("%.2f", weightC)));
							mapper.insertTmpExcelVolume(orderWeightExcelVO);
							// vo 검증로직은 여기
							System.out.println(orderWeightExcelVO.getLength());
						}

					}
				} else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // row=0은 헤더이므로 1부터 시작
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							currentRow = rowIndex;
							String rowResult = "Err!:";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								int valSize = 0;
								Date times2 = new Date();
								String time2 = format2.format(times2);
								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]")[1].equals("0")) {
											value = value.split("[.]")[0];
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

								switch (columnIndex) {
								case 0:
									// Item Number - HawbNo
									orderWeightExcelVO.setHawbNo(value);
									break;
								case 1:
									// Weight - wta
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWta(target);
											else
												orderWeightExcelVO.setWta("0");
											break;
										}
									}
									break;
								case 2:
									// Dim Wgt - wtc
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWtc(target);
											else
												orderWeightExcelVO.setWtc("0");
											break;
										}
									}
									break;
								case 3:
									// Length - Length
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setLength(target);
											else
												orderWeightExcelVO.setLength("0");
											break;

										}
									}
									break;
								case 4:
									// width - width
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setWidth(target);
											else
												orderWeightExcelVO.setWidth("0");
											break;
										}
									}
									break;
								case 5:
									// Height - Height
									valSize = value.length();
									for (int index = 0; index < valSize; index++) {
										if (value.charAt(index) != '0') {
											String target = value.substring(index, valSize);
											if (target.substring(0, 1).equals(".")) {
												target = 0 + target;
											}
											boolean regex = Pattern.matches(patternFloat, target);
											if (regex)
												orderWeightExcelVO.setHeight(target);
											else
												orderWeightExcelVO.setHeight("0");
											break;
										}
									}
									break;
								}
							} // 현재row vo에 set 완료
								// vo 검증로직은 여기
							mapper.insertTmpExcelVolume(orderWeightExcelVO);
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
			if (delFile.exists()) {
				delFile.delete();
			}
		}

		result = "등록되었습니다.";
		return result;
	}

	public Map filesUpload(MultipartHttpServletRequest multi, String uploadPaths) {
		String path = uploadPaths + "weight/"; // 저장 경로 설정

		String newFileName = ""; // 업로드 되는 파일명
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		Iterator<String> files = multi.getFileNames();
		Map<String, String> resMap = new HashMap<String, String>();
		String fileName = "";
		while (files.hasNext()) {
			String uploadFile = files.next();
			MultipartFile mFile = multi.getFile(uploadFile);
			fileName = mFile.getOriginalFilename();
			newFileName = System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
			try {
				mFile.transferTo(new File(path + newFileName));
				resMap.put(newFileName, path + newFileName);
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		}
		return resMap;
	}

	@Override
	public ArrayList<OrderWeightVO> selectOrderWeightExcel(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderWeightExcel(orgStation);
	}

	@Override
	@Transactional
	public String execOrderWeightList(String[] targetHawb, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ProcedureVO rtnVal = new ProcedureVO();
		String result = "";
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		for (int roop = 0; roop < targetHawb.length; roop++) {
			ProcedureVO procedureRcpt = new ProcedureVO();
			String nno = targetHawb[roop];
			//String nno = mapper.selectNNOByHawbNo(targetHawb[roop]);
			String transCode = mapper.selectTransCodeByNNO(nno);
			String[] targetHawbInfo = new String[1];

			targetHawbInfo[0] = transCode + "," + nno;
			parameters.put("hawbNo", targetHawb[roop]);
			parameters.put("nno", nno);
			procedureRcpt = execOrderRcptList(targetHawbInfo, request);
			if (procedureRcpt.getRstStatus().equals("SUCCESS")) {
				rtnVal = mapper.execOrderWeightList(parameters);
				if (!rtnVal.getRstStatus().equals("SUCCESS")) {
					result = rtnVal.getRstMsg();
					break;
				}
			}
		}
		result = "S";
		return result;
	}

	@Override
	public String delOrderWeightList(String[] targetHawb, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ProcedureVO rtnVal = new ProcedureVO();
		String result = "";
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		try {
			for (int roop = 0; roop < targetHawb.length; roop++) {
				parameters.put("hawbNo", targetHawb[roop]);
				mapper.delOrderWeightList(parameters);
			}
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}

		return result;
	}

	@Override
	public ArrayList<NationVO> selectUserDstnNationInfo(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserDstnNationInfo(orgStation);
	}

	@Override
	public StockVO selectStockByNo(String stockNo, String nno, String alreadyInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockByNo(stockNo, nno, alreadyInfo);
	}

	@Override
	public String selectChkCntStock(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectChkCntStock(nno);
	}

	@Override
	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStock(parameterInfo);
	}

	@Override
	public ProcedureVO execSpWhoutStock(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.execSpWhoutStock(parameter);
	}

	@Override
	public ArrayList<InspStockOutVO> selectStockOutTarget(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockOutTarget(parameter);
	}

	@Override
	public String selectTargetSubNo(String stockNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTargetSubNo(stockNo);
	}

	@Override
	public void deleteStockOut(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteStockOut(parameter);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcedureVO execStockHawbIn(Map<String, String[]> parameterMaps, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ProcedureVO rtnVal1 = new ProcedureVO();
		ProcedureVO rtnVal2 = new ProcedureVO();
		String nno = request.getParameter("nno");
		HashMap<String, String> stockInParam = new HashMap<String, String>();
		stockInParam.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		stockInParam.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		stockInParam.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));
		stockInParam.put("wta", request.getParameter("boxWeight"));

		try {
			rtnVal1 = mapper.execStockHawbIn(stockInParam);
			if ("SUCCESS".equals(rtnVal1.getRstStatus())) {
				VolumeVO volume = new VolumeVO();
				volume.setNno(nno);
				volume.setWUserId((String) request.getSession().getAttribute("USER_ID"));
				volume.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
				volume.setDimUnit(request.getParameter("dimUnit"));
				volume.setWtUnit(request.getParameter("wtUnit"));
				int roopCnt = parameterMaps.get("per").length;
				mapper.deleteVolume(volume.getNno());
				for (int volumeIndex = 0; volumeIndex < roopCnt; volumeIndex++) {
					volume.setWidth(parameterMaps.get("width")[volumeIndex]);
					volume.setHeight(parameterMaps.get("height")[volumeIndex]);
					volume.setLength(parameterMaps.get("length")[volumeIndex]);
					volume.setPer(parameterMaps.get("per")[volumeIndex]);
					try {
						rtnVal2 = mapper.execNomalHawbVolume(volume);
						if (!rtnVal2.getRstStatus().equals("SUCCESS")) {
							break;
						}
					} catch (Exception e) {
						// TODO: handle exception
						return rtnVal2;
					}
				}
				// TODO 실제 송장 등록 로직 추가, 추가 시 itemcarry는 업데이트로 할 것. 나머지 삭제 없는 애들은 그냥 두고, 삭제 있는
				// 애들만 추가 등록...
				InspStockListVO orderInfo = new InspStockListVO();
				orderInfo = mapper.selectInspStockOrderInfo(nno);
				// comnServiceImpl.comnBlApplyInsp(nno, orderInfo.getTransCode(),
				// stockInParam.get("wUserId"), stockInParam.get("wUserIp"), "");
			} else {
				return rtnVal1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return rtnVal1;
		}

		rtnVal2.setOutNno(rtnVal1.getOutNno());
		return rtnVal2;
	}

	@Override
	public String selectUserTransComByNno(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTransComByNno(nno);
	}

	@Override
	public String excuteAramexHawb(HttpServletRequest request, String nno) throws Exception {// 검품 등록 시, 기존꺼에서 hawb업데이트
																								// 쳐버림 // 사용자의 HAWB가
																								// 변함//
		// TODO Auto-generated method stub
		ApiOrderListVO apiOrderList = new ApiOrderListVO();
		ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();

		apiOrderList = mapper.selectOrderListAramex(nno);
		apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
		apiOrderList.dncryptData();
		apiOrderItemList = mapper.selectOrderItemAramex(nno);
		if (apiOrderList.getCneeEmail().equals(""))
			apiOrderList.setCneeEmail("-");

		if (apiOrderList.getShipperEmail().equals(""))
			apiOrderList.setShipperEmail("-");

		for (int i = 0; i < apiOrderItemList.size(); i++) {
			apiOrderItemList.get(i).setNno(apiOrderList.getNno());
		}

		ShipmentCreationResponse resultAramex = new ShipmentCreationResponse();
		resultAramex = apiServiceImpl.aramexApi(apiOrderList, apiOrderItemList,
				request.getSession().getAttribute("USER_ID").toString(),
				request.getSession().getAttribute("USER_IP").toString());
		if (resultAramex.getHasErrors()) {
			return "F";
		} else {
			comnServiceImpl.updateHawbNoInTbHawb(resultAramex.getShipments()[0].getID(), nno);
			comnServiceImpl.updateHawbNoInTbOrderList(resultAramex.getShipments()[0].getID(), nno);
		}
		return "S";
	}

	@Override
	public String excuteAramexHawbMemberOut(String nno, String userId, String userIp) throws Exception {// 최초 등록 시 사용
		// TODO Auto-generated method stub
		ApiOrderListVO apiOrderList = new ApiOrderListVO();
		ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();

		apiOrderList = mapper.selectOrderListAramexMember(nno);
		apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
		apiOrderList.dncryptData();
		apiOrderItemList = mapper.selectOrderItemAramexMember(nno);
		if (apiOrderList.getCneeEmail().equals(""))
			apiOrderList.setCneeEmail("-");

		if (apiOrderList.getShipperEmail().equals(""))
			apiOrderList.setShipperEmail("-");

		for (int i = 0; i < apiOrderItemList.size(); i++) {
			apiOrderItemList.get(i).setNno(apiOrderList.getNno());
		}

		ShipmentCreationResponse resultAramex = new ShipmentCreationResponse();
		resultAramex = apiServiceImpl.aramexApi(apiOrderList, apiOrderItemList, userId, userIp);

		if (resultAramex.getHasErrors()) {
			HashMap<String, Object> araErrorParams = new HashMap<String, Object>();
			String errCode = resultAramex.getShipments()[0].getNotifications()[0].getCode();
			String errMsg = resultAramex.getShipments()[0].getNotifications()[0].getMessage();
			comnServiceImpl.deleteHawbNoInTbHawb(nno);
			comnServiceImpl.insertTmpFromOrderList(nno, errCode);
			araErrorParams.put("nno", nno);
			araErrorParams.put("errorMsg", errMsg);
			araErrorParams.put("useYn", "Y");
			araErrorParams.put("wUserId", userId);
			araErrorParams.put("wUserIp", userIp);
			comnServiceImpl.insertAraErrorMatch(araErrorParams);
			// 2023.11.27 주소 관련 에러 코드로는 정확한 오류 발생 항목을 파악하기 힘들어 오류 메세지 테이블에 저장하는 부분 추가
			comnServiceImpl.insertTmpFromOrderItem(nno);
			comnServiceImpl.deleteOrderListByNno(nno);

		} else {
			comnServiceImpl.updateHawbNoInTbHawb(resultAramex.getShipments()[0].getID(), nno);
			comnServiceImpl.updateHawbNoInTbOrderList(resultAramex.getShipments()[0].getID(), nno);
		}
		return "S";
	}

	@Override
	public ProcedureVO managerPrintInspcHawb(HttpServletRequest request, String nno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectHawbNnoCheck(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbNnoCheck(nno);
	}

	@Override
	public void deleteInspcStockOutCzFail(String nno) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteInspcStockOutCzFail(nno);
	}

	@Override
	public StockVO selectStockByNo2(String stockNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockByNo2(stockNo);
	}

	@Override
	public ProcedureVO execAddBlApply(String nno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		return mapper.execAddBlApply(nno, userId, userIp);
	}

	@Override
	public String selectStockMaxNo() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockMaxNo();
	}

	@Override
	public ArrayList<StockVO> unRegistWhProcess(HttpServletRequest request, MultipartHttpServletRequest multi,
			MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		HashMap<String, String> tempMap = new HashMap<String, String>();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);

		tempMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		tempMap.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		tempMap.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));
		tempMap.put("trkNo", request.getParameter("trkNo"));
		tempMap.put("rackNo", request.getParameter("rackNo"));
		tempMap.put("whMemo", request.getParameter("whMemo"));
		tempMap.put("subNo", "1");
		tempMap.put("whInDate", time1);
		tempMap.put("stockNo", mapper.selectStockMaxNo());
		tempMap.put("groupIdx", mapper.selectGroupIdx());
		if (!request.getParameter("userId").isEmpty()) {
			tempMap.put("userId", request.getParameter("userId"));
		}
		mapper.insertStockUnRgt(tempMap);

		if (!tempMap.get("whMemo").equals("")) {
			mapper.insertUnStockMsg(tempMap);
		}

		String ImageDir = multi.getSession().getServletContext().getRealPath("/") + "image/" + "stock/unRgt/";

		File dir = new File(ImageDir);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		for (int fileIndex = 1; fileIndex < 6; fileIndex++) {
			if (multi.getFiles("input_imgs" + fileIndex).get(0).isEmpty()) {
				continue;
			}
			file = multi.getFiles("input_imgs" + fileIndex).get(0);
			try {
				amazonS3 = new AmazonS3Client(awsCredentials);
				FileOutputStream fos = new FileOutputStream(ImageDir + fileIndex + "_" + file.getOriginalFilename());
				InputStream is = file.getInputStream();
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while ((readCount = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}
				is.close();
				fos.close();
				File uploadFile = new File(ImageDir + fileIndex + "_" + file.getOriginalFilename());
				if (amazonS3 != null && !"".equals(file.getOriginalFilename())) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(
							comnS3Info.getBucketName() + "/outbound/stock/unRgt/" + time1 + tempMap.get("trkNo"),
							uploadFile.getName(), uploadFile);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					amazonS3.putObject(putObjectRequest);
				}
				tempMap.put("fileDir", "img.mtuai.com/outbound/stock/unRgt/" + time1 + tempMap.get("trkNo") + "/"
						+ uploadFile.getName());
				tempMap.put("fileIdx", Integer.toString(fileIndex));
				mapper.insertUnRgtStockFile(tempMap);
				uploadFile.delete();
				amazonS3 = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		ArrayList<StockVO> rtnVal = new ArrayList<StockVO>();
		StockVO temp = new StockVO();
		temp.setGroupIdx(tempMap.get("groupIdx"));
		temp.setOrgStation(tempMap.get("orgStation"));
		temp.setWUserId(tempMap.get("wUserId"));
		temp.setWUserIp(tempMap.get("wUserIp"));
		temp.setTrkNo(tempMap.get("trkNo"));
		temp.setRackCode(tempMap.get("rackNo"));
		temp.setSubNo(tempMap.get("subNo"));
		temp.setWhInDate(tempMap.get("whInDate"));
		temp.setStockNo(tempMap.get("stockNo"));
		rtnVal.add(temp);
		return rtnVal;
	}

	@Override
	public ArrayList<String> selectTransCodeForAdmin(String adminId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeForAdmin(adminId);
	}

	@Override
	public ArrayList<InnerProductVO> selectInnerProductList(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectInnerProductList(parameters);
	}

	@Override
	public void updateInnerProductList(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateInnerProductList(parameters);
	}

	@Override
	public HawbVO selectFtpCount(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectFtpCount(orgStation);
	}

	@Override
	public ArrayList<InnerProductVO> selectInnerProductListAll(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectInnerProductListAll(parameters);
	}

	@Override
	public int selectShpngListCount(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectShpngListCount(parameter);
	}

	@Override
	public void updateItemTrkNo(String trkNo, String nno, String subNo) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateItemTrkNo(trkNo, nno, subNo);
	}

	@Override
	public ArrayList<InspStockOutVO> selectStockRackInfo(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockRackInfo(parameter);
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
	public String selectTransCodeInStock(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeInStock(nno);
	}

	@Override
	public OrderListOptionVO SelectOrderListOption(String targetCode, String dstnNation) throws Exception {
		// TODO Auto-generated method stub
		int count = mapper.selectCountOrderListOption(targetCode, dstnNation);
		if (count == 0) {
			dstnNation = "ARA";
			count = mapper.selectCountOrderListOption(targetCode, dstnNation);

			if (count == 0) {
				dstnNation = "DEFAULT";
			}
		}
		return mapper.selectOrderListOption(targetCode, dstnNation);
	}

	@Override
	public OrderItemOptionVO SelectOrderItemOption(String targetCode, String dstnNation) throws Exception {
		// TODO Auto-generated method stub
		int count = mapper.selectCountOrderItemOption(targetCode, dstnNation);

		if (count == 0) {
			dstnNation = "ARA";
			count = mapper.selectCountOrderItemOption(targetCode, dstnNation);

			if (count == 0) {
				dstnNation = "DEFAULT";
			}
		}

		return mapper.selectOrderItemOption(targetCode, dstnNation);
	}

	@Override
	public OrderListExpOptionVO SelectExpressListOption(String targetCode, String dstnNation) throws Exception {
		// TODO Auto-generated method stub
		int count = mapper.selectCountExpressListOption(targetCode, dstnNation);

		if (count == 0) {
			dstnNation = "ARA";
			count = mapper.selectCountExpressListOption(targetCode, dstnNation);

			if (count == 0) {
				dstnNation = "DEFAULT";
			}
		}

		return mapper.selectExpressListOption(targetCode, dstnNation);
	}

	@Override
	public OrderItemExpOptionVO SelectExpressItemOption(String targetCode, String dstnNation) throws Exception {
		// TODO Auto-generated method stub
		int count = mapper.selectCountExpressItemOption(targetCode, dstnNation);

		if (count == 0) {
			dstnNation = "ARA";
			count = mapper.selectCountExpressItemOption(targetCode, dstnNation);

			if (count == 0) {
				dstnNation = "DEFAULT";
			}
		}
		return mapper.selectExpressItemOption(targetCode, dstnNation);
	}

	@Override
	public void insertOrderListOption(OrderListOptionVO optionOrderVO, String targetCode) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteOrderListOption(targetCode, optionOrderVO.getDstnNation());
		try {
			mapper.insertOrderListOption(optionOrderVO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}

	}

	@Override
	public void insertOrderItemOption(OrderItemOptionVO optionItemVO, String targetCode) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteOrderpItemOtion(targetCode, optionItemVO.getDstnNation());
		try {
			mapper.insertOrderItemOption(optionItemVO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
	}

	@Override
	public void insertOrderListExpOption(OrderListExpOptionVO expressOrderVO, String targetCode) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteOrderListExpOption(targetCode, expressOrderVO.getDstnNation());
		try {
			mapper.insertOrderListExpOption(expressOrderVO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
	}

	@Override
	public void insertOrderItemExpOption(OrderItemExpOptionVO expressItemVO, String targetCode) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteOrderItemExpOption(targetCode, expressItemVO.getDstnNation());
		try {
			mapper.insertOrderItemExpOption(expressItemVO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
		}
	}

	@Override
	public ArrayList<String> selectOrderListOptionNation(String targetCode) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderListOptionNation(targetCode);
	}

	@Override
	public String makeExpLicenceExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList) throws Exception {
		// TODO Auto-generated method stub
		LocalDate currentDate = LocalDate.now();
		String savePath = realFilePath + "excel/expLicence/";
		String filename = "expLicence" + currentDate + ".xlsx";

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
		// ROW-CELL 선언 END

		// HEADER 생성 START 작업중입니단
		row = sheet1.createRow(rowCnt);

		cell = row.createCell(0);
		cell.setCellValue("HAWB");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(1);
		cell.setCellValue("대행자");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(2);
		cell.setCellValue("수출(화주)");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(3);
		cell.setCellValue("제조자");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(4);
		cell.setCellValue("구매자상호명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(5);
		cell.setCellValue("목적국");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(6);
		cell.setCellValue("거래품명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(7);
		cell.setCellValue("표준품명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(8);
		cell.setCellValue("상품명(모델규격)");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(9);
		cell.setCellValue("상표명(BRAND)");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(10);
		cell.setCellValue("수량");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(11);
		cell.setCellValue("단가");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(12);
		cell.setCellValue("금액");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(13);
		cell.setCellValue("HS코드");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(14);
		cell.setCellValue("총중량");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(15);
		cell.setCellValue("총포장갯수");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(16);
		cell.setCellValue("결제통화");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(17);
		cell.setCellValue("결제금액");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(18);
		cell.setCellValue("INVOICE_NUMBER");
		cell.setCellStyle(cellStyleHeader);
		rowCnt++;
		// HEADER 생성 END

		for (int i = 0; i < orderNnoList.size(); i++) {
			ArrayList<ExpLicenceExcelVO> expLicence = new ArrayList<ExpLicenceExcelVO>();
			expLicence = mapper.selectExcelLicence(orderNnoList.get(i));
			if (expLicence.size() != 0) {
				for (int roop = 0; roop < expLicence.size(); roop++) {
					row = sheet1.createRow(rowCnt);
					cell = row.createCell(0);
					cell.setCellValue(expLicence.get(roop).getHawb());

					cell = row.createCell(1);
					cell.setCellValue(expLicence.get(roop).getShipperName());

					cell = row.createCell(2);
					cell.setCellValue(expLicence.get(roop).getShipperName());

					cell = row.createCell(3);
					cell.setCellValue("");

					cell = row.createCell(4);
					cell.setCellValue(expLicence.get(roop).getCneeName());

					cell = row.createCell(5);
					cell.setCellValue(expLicence.get(roop).getDstnNation());

					cell = row.createCell(6);
					cell.setCellValue("");

					cell = row.createCell(7);
					cell.setCellValue(expLicence.get(roop).getItemDetail());

					cell = row.createCell(8);
					cell.setCellValue(expLicence.get(roop).getItemDetail());

					cell = row.createCell(9);
					cell.setCellValue(expLicence.get(roop).getBrand());

					cell = row.createCell(10);
					cell.setCellValue(expLicence.get(roop).getItemCnt());

					cell = row.createCell(11);
					cell.setCellValue(expLicence.get(roop).getUnitValue());

					cell = row.createCell(12);
					cell.setCellValue(expLicence.get(roop).getValue());

					cell = row.createCell(13);
					cell.setCellValue("");

					cell = row.createCell(14);
					cell.setCellValue(expLicence.get(roop).getWta());

					cell = row.createCell(15);
					cell.setCellValue(expLicence.get(roop).getBoxCnt());

					cell = row.createCell(16);
					cell.setCellValue(expLicence.get(roop).getCurrency());

					cell = row.createCell(17);
					cell.setCellValue(expLicence.get(roop).getTotalValue());

					cell = row.createCell(18);
					cell.setCellValue(expLicence.get(roop).getHawb());
					rowCnt++;
				}

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
		return "SSS";
	}

	@Override
	public void insertExpLicence(MultipartHttpServletRequest multi, HttpServletRequest request, String excelRoot)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		HashMap<String, String> expLicenceIn = new HashMap<String, String>();
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			ExpLicenceVO expLicencoList = new ExpLicenceVO();
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // row=0은 헤더이므로 1부터 시작
						expLicenceIn = new HashMap<String, String>();
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							String value = "";
							for (int columnIndex = 0; columnIndex < 2; columnIndex++) {
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (cell != null) {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
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

									switch (columnIndex) {
									case 1: {
										expLicenceIn.put("expNo", value);
										break;
									}
									case 0: {
										expLicenceIn.put("expRegNo", value);
										break;
									}

									}
								}
							}
							mapper.updateLicenceInfo(expLicenceIn);
						}
					}
				} else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // row=0은 헤더이므로 1부터 시작
						expLicenceIn = new HashMap<String, String>();
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							String value = "";
							for (int columnIndex = 0; columnIndex < 2; columnIndex++) {
								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (cell != null) {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
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

									switch (columnIndex) {
									case 1: {
										expLicenceIn.put("expNo", value);
										break;
									}
									case 0: {
										expLicenceIn.put("expRegNo", value);
										break;
									}

									}
								}
							}
							mapper.updateLicenceInfo(expLicenceIn);
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void insertZoneExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request, String excelRoot)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		HashMap<String, Object> zoneExcelHead = new HashMap<String, Object>();
		HashMap<String, Object> zoneNumber = new HashMap<String, Object>();
		HashMap<Integer, ArrayList<String>> zoneNationList = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> wtSection = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> zonePriceList = new HashMap<Integer, ArrayList<String>>();
		HashMap<String, Object> zoneExcelbody = new HashMap<String, Object>();
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					XSSFRow row = sheet.getRow(0); // 현재 row정보
					for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
						row = sheet.getRow(rowIndex); // 현재 row정보
						String _nameValue = "";
						String _value = "";
						XSSFCell _cellName = row.getCell(0); // 셀에 담겨있는 값을 읽는다.
						XSSFCell _cellValue = row.getCell(1); // 셀에 담겨있는 값을 읽는다.
						if (_cellName == null) {
							_nameValue = "";
						} else {
							switch (_cellName.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
							case HSSFCell.CELL_TYPE_NUMERIC:

								if (DateUtil.isCellDateFormatted(_cellName)) {
									Date date = _cellName.getDateCellValue();
									_nameValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									_cellName.setCellType(HSSFCell.CELL_TYPE_STRING);
									_nameValue = _cellName.getStringCellValue();
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:
								_nameValue = _cellName.getStringCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								_nameValue = "";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								_nameValue = _cellName.getErrorCellValue() + "";
								break;
							}

							switch (_cellValue.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
							case HSSFCell.CELL_TYPE_NUMERIC:

								if (DateUtil.isCellDateFormatted(_cellValue)) {
									Date date = _cellValue.getDateCellValue();
									_value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									_cellValue.setCellType(HSSFCell.CELL_TYPE_STRING);
									_value = _cellValue.getStringCellValue();
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:
								_value = _cellValue.getStringCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								_value = "";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								_value = _cellValue.getErrorCellValue() + "";
								break;
							}

						}
						switch (_nameValue.replaceAll(" ", "")) {
						case "통화": {
							zoneExcelHead.put("currency", _value);
							break;
						}
						case "제목": {
							zoneExcelHead.put("invoiceName", _value);
							break;
						}
						case "무게단위": {
							zoneExcelHead.put("wtUnit", _value);
							break;
						}
						}

					}

					row = sheet.getRow(3); // 현재 row정보
					if (row != null) {

						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							String _zoneNumValue = "";
							XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
							if (_cell == null) {
								_zoneNumValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneNumValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneNumValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneNumValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneNumValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneNumValue = _cell.getErrorCellValue() + "";
									break;
								}
								zoneNumber.put(_zoneNumValue, _zoneNumValue);

							}
						}

					}

					row = sheet.getRow(4); // 현재 row정보
					if (row != null) {
						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							ArrayList<String> zoneNation = new ArrayList<String>();
							String _zoneNationValue = "";
							XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
							if (_cell == null) {
								_zoneNationValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneNationValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneNationValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneNationValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneNationValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneNationValue = _cell.getErrorCellValue() + "";
									break;
								}
								if (_zoneNationValue.replaceAll(" ", "").split(",").length != 1) {
									String[] tempZoneNation = _zoneNationValue.replaceAll(" ", "").split(",");
									for (int i = 0; i < tempZoneNation.length; i++) {
										zoneNation.add(tempZoneNation[i]);
									}
								} else {
									zoneNation.add(_zoneNationValue);
								}
								zoneNationList.put(columnIndex, zoneNation);
							}
						}
					}

					if (row != null) {
						int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
						for (int rowIndex = 6; rowIndex < rows; rowIndex++) {
							row = sheet.getRow(rowIndex); // 현재 row정보
							XSSFCell _cell = row.getCell(0); // 셀에 담겨있는 값을 읽는다.
							String _zoneWtValue = "";
							if (_cell == null) {
								_zoneWtValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneWtValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneWtValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneWtValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneWtValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneWtValue = _cell.getErrorCellValue() + "";
									break;
								}
							}
							wtSection.add(_zoneWtValue);
						}
						row = sheet.getRow(6); // 현재 row정보
						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							ArrayList<String> zonePrice = new ArrayList<String>();
							for (int rowIndex = 6; rowIndex < rows; rowIndex++) {
								String _zonePriceValue = "";
								XSSFRow subRow = sheet.getRow(rowIndex); // 현재 row정보
								XSSFCell _cell = subRow.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.

								if (_cell == null) {
									_zonePriceValue = "";
								} else {
									switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:

										if (DateUtil.isCellDateFormatted(_cell)) {
											Date date = _cell.getDateCellValue();
											_zonePriceValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
										} else {
											_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
											_zonePriceValue = _cell.getStringCellValue();
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										_zonePriceValue = _cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										_zonePriceValue = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										_zonePriceValue = _cell.getErrorCellValue() + "";
										break;
									}
									zonePrice.add(_zonePriceValue);
								}
							}
							zonePriceList.put(columnIndex, zonePrice);
						}

					}
					zoneExcelHead.put("wUserId", request.getSession().getAttribute("USER_ID"));
					zoneExcelHead.put("wUserIp", request.getSession().getAttribute("USER_IP"));
					zoneExcelHead.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
					ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
					String nameHead = nowSeoul.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					zoneExcelHead.put("dlvCode", nameHead + "_MENUAL");

					ArrayList<DlvPriceVO> dlvPriceVo = new ArrayList<DlvPriceVO>();
					DlvPriceVO dlvPriceDetail = new DlvPriceVO();

					dlvPriceDetail.setDlvCode(zoneExcelHead.get("dlvCode").toString());
					dlvPriceDetail.setWUserId(zoneExcelHead.get("wUserId").toString());
					dlvPriceDetail.setWUserIp(zoneExcelHead.get("wUserIp").toString());
					dlvPriceDetail.setWtUnit(zoneExcelHead.get("wtUnit").toString());
					dlvPriceDetail.setCurrency(zoneExcelHead.get("currency").toString());
					dlvPriceDetail.setOrgStation(zoneExcelHead.get("orgStation").toString());

					dlvPriceDetail.setDlvPriceName(zoneExcelHead.get("invoiceName").toString());

					// zone 개수 만큼 반복
					for (int i = 0; i < zoneNumber.size(); i++) {
						dlvPriceDetail.setZoneCode(zoneNumber.get(Integer.toString(i + 1)).toString());
						ArrayList<String> nationList = zoneNationList.get(i + 1);
						ArrayList<String> priceList = zonePriceList.get(i + 1);
						// zone 별 국가 수 만큼 반복
						for (int j = 0; j < nationList.size(); j++) {
							dlvPriceDetail.setDstnNation(nationList.get(j));// 국가 2코드
							for (int k = 0; k < wtSection.size(); k++) {
								dlvPriceDetail.setWt(wtSection.get(k));// 무게 구간
								dlvPriceDetail.setPrice(priceList.get(k));
								mapper.insertDlvPrice(dlvPriceDetail);
							}
						}
					}

//					zoneExcelHead;
//					zoneNumber;
//					zoneNationList;
//					wtSection;
//					zonePriceList;

				}
//					else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
//					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
//					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
//					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
//					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
//					{ // row=0은 헤더이므로 1부터 시작
//						expLicenceIn = new HashMap<String,String>(); 
//						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
//						if (row != null) 
//						{
//							String value = "";
//							for (int columnIndex = 0; columnIndex < 2; columnIndex++) 
//							{
//								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
//								if(cell != null) {
//									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
//									case HSSFCell.CELL_TYPE_NUMERIC:
//										cell.setCellType( HSSFCell.CELL_TYPE_STRING );
//										value = cell.getStringCellValue();
//										break;
//									case HSSFCell.CELL_TYPE_STRING:
//										value = cell.getStringCellValue() + "";
//										break;
//									case HSSFCell.CELL_TYPE_BLANK:
//										value = "";
//										break;
//									case HSSFCell.CELL_TYPE_ERROR:
//										value = cell.getErrorCellValue() + "";
//										break;
//									}
//									
//									switch (columnIndex) {
//										case 1:{
//											expLicenceIn.put("expNo", value);
//											break;
//										}
//										case 0:{
//											expLicenceIn.put("expRegNo", value);
//											break;
//										}
//									
//									}
//								}
//							}
//							mapper.updateLicenceInfo(expLicenceIn);
//						}
//					}
//				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	@Override
	public ArrayList<ExpLicenceListVO> selectLicenceList(ExpLicenceListVO parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectLicenceList(parameters);
	}

	@Override
	public ArrayList<HawbVO> selectExpFieldList(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectExpFieldList(mawbNo);
	}

	@Override
	public HawbVO selectExpLicenceCount(String attribute) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectExpLicenceCount(attribute);
	}

	@Override
	public String makeMawbExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		LocalDate currentDate = LocalDate.now();
		String savePath = realFilePath + "excel/expLicence/";
		String filename = "mawb" + currentDate + ".xlsx";

		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		// HEAD 스타일 설정 START
		CellStyle cellStyleHeader = xlsxWb.createCellStyle();
		cellStyleHeader.setWrapText(false);
		cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

		ArrayList<OrderInspListVO> orderListInfo = new ArrayList<OrderInspListVO>();
		orderListInfo = mapper.selectorderListByMawb(request.getParameter("targetInfos"));
		// HEAD 스타일 설정 END
		// 상품명 수량 무게 value
		// ROW-CELL 선언 START
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		// ROW-CELL 선언 END

		Sheet sheet = xlsxWb.createSheet("Sheet1");
		row = sheet.createRow(rowCnt);

		cell = row.createCell(0);
		cell.setCellValue("Hawb No.");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(1);
		cell.setCellValue("Piece");
		cell.setCellStyle(cellStyleHeader);
		rowCnt++;

		if (orderListInfo.size() != 0) {
			for (int roop = 0; roop < orderListInfo.size(); roop++) {
				row = sheet.createRow(rowCnt);
				cell = row.createCell(0);
				cell.setCellValue(orderListInfo.get(roop).getHawbNo());

				cell = row.createCell(1);
				cell.setCellValue(orderListInfo.get(roop).getBoxCnt());
				rowCnt++;
			}
		}

		// 신규 sheet 선언 및 초기화 Start
		Sheet sheet1 = xlsxWb.createSheet("Sheet2");
		row = null;
		cell = null;
		rowCnt = 0;
		// 신규 sheet 선언 및 초기화 Start

		// HEADER 생성 START 작업중입니단
		row = sheet1.createRow(rowCnt);

		cell = row.createCell(0);
		cell.setCellValue("주문번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(1);
		cell.setCellValue("출발도시");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(2);
		cell.setCellValue("도착국가");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(3);
		cell.setCellValue("USER ID");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(4);
		cell.setCellValue("BL No.");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(5);
		cell.setCellValue("Shipper Name");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(6);
		cell.setCellValue("Shipper Zip");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(7);
		cell.setCellValue("Shipper Tel");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(8);
		cell.setCellValue("Shipper State");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(9);
		cell.setCellValue("Shipper City");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(10);
		cell.setCellValue("Shipper District");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(11);
		cell.setCellValue("Shipper Addr");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(12);
		cell.setCellValue("Shipper Addr Detail");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(13);
		cell.setCellValue("Reciever Name");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(14);
		cell.setCellValue("Reciver Zip");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(15);
		cell.setCellValue("Reciver Tel");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(16);
		cell.setCellValue("Reciver State");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(17);
		cell.setCellValue("Reciver City");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(18);
		cell.setCellValue("Reciver Addr");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(19);
		cell.setCellValue("Reciver Addr Detail");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(20);
		cell.setCellValue("BL No2");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(21);
		cell.setCellValue("상품명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(22);
		cell.setCellValue("상품 단가");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(23);
		cell.setCellValue("상품 개수");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(24);
		cell.setCellValue("총 금액");
		cell.setCellStyle(cellStyleHeader);
		rowCnt++;
		// HEADER 생성 END

		if (orderListInfo.size() != 0) {
			for (int roop = 0; roop < orderListInfo.size(); roop++) {
				orderListInfo.get(roop).dncryptData();
				row = sheet1.createRow(rowCnt);
				if (orderListInfo.get(roop).getSubNo().equals("1")) {
					cell = row.createCell(0);
					cell.setCellValue(orderListInfo.get(roop).getOrderNo());

					cell = row.createCell(1);
					cell.setCellValue(orderListInfo.get(roop).getOrgStation());

					cell = row.createCell(2);
					cell.setCellValue(orderListInfo.get(roop).getDstnNation());

					cell = row.createCell(3);
					cell.setCellValue(orderListInfo.get(roop).getUserId());

					cell = row.createCell(4);
					cell.setCellValue(orderListInfo.get(roop).getHawbNo());

					cell = row.createCell(5);
					cell.setCellValue(orderListInfo.get(roop).getShipperName());

					cell = row.createCell(6);
					cell.setCellValue(orderListInfo.get(roop).getShipperZip());

					cell = row.createCell(7);
					cell.setCellValue(orderListInfo.get(roop).getShipperTel());

					cell = row.createCell(8);
					cell.setCellValue(orderListInfo.get(roop).getShipperState());

					cell = row.createCell(9);
					cell.setCellValue(orderListInfo.get(roop).getShipperCity());

					cell = row.createCell(10);
					cell.setCellValue("");

					cell = row.createCell(11);
					cell.setCellValue(orderListInfo.get(roop).getShipperAddr());

					cell = row.createCell(12);
					cell.setCellValue(orderListInfo.get(roop).getShipperAddrDetail());

					cell = row.createCell(13);
					cell.setCellValue(orderListInfo.get(roop).getCneeName());

					cell = row.createCell(14);
					cell.setCellValue(orderListInfo.get(roop).getCneeZip());

					cell = row.createCell(15);
					cell.setCellValue(orderListInfo.get(roop).getCneeTel());

					cell = row.createCell(16);
					cell.setCellValue(orderListInfo.get(roop).getCneeState());

					cell = row.createCell(17);
					cell.setCellValue(orderListInfo.get(roop).getCneeCity());

					cell = row.createCell(18);
					cell.setCellValue(orderListInfo.get(roop).getCneeAddr());

					cell = row.createCell(19);
					cell.setCellValue(orderListInfo.get(roop).getCneeAddrDetail());

					cell = row.createCell(20);
					cell.setCellValue(orderListInfo.get(roop).getBlNo2());
				}
				cell = row.createCell(21);
				cell.setCellValue(orderListInfo.get(roop).getItemDetail());

				cell = row.createCell(22);
				cell.setCellValue(orderListInfo.get(roop).getUnitValue());

				cell = row.createCell(23);
				cell.setCellValue(orderListInfo.get(roop).getItemCnt());

				cell = row.createCell(24);
				double totalVal = Double.parseDouble(orderListInfo.get(roop).getUnitValue())
						* Double.parseDouble(orderListInfo.get(roop).getItemCnt());
				cell.setCellValue(totalVal);

				rowCnt++;
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
		return "SSS";
	}

	@Override
	public String makeEfsExcel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList)
			throws Exception {
		// TODO Auto-generated method stub
		String transCode = request.getParameter("transCodes");
		String swh = "USPS";
		if (swh.equals("DHL")) {

		} else if (swh.equals("USPS")) {
			// TODO Auto-generated method stub
			LocalDate currentDate = LocalDate.now();
			String savePath = realFilePath + "excel/expLicence/";
			String filename = "EFS_USPS_" + currentDate + ".xlsx";

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
			// ROW-CELL 선언 END

			// HEADER 생성 START 작업중입니단
			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("DATE");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;

			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("FLT NO.");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;

			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("출발공항");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("ICN");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;

			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("도착공항");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;

			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("M NO.");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;

			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("No");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("HAWB NO");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(2);
			cell.setCellValue("CT");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(3);
			cell.setCellValue("WT");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(4);
			cell.setCellValue("신고 금액");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(5);
			cell.setCellValue("아이템");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(6);
			cell.setCellValue("보내는화주명");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(7);
			cell.setCellValue("보내는화주주소");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(8);
			cell.setCellValue("받는화주명");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(9);
			cell.setCellValue("받는화주주소");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(10);
			cell.setCellValue("수출신고번호");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(11);
			cell.setCellValue("수출신고 C/T");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(12);
			cell.setCellValue("수출신고 W/T");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(13);
			cell.setCellValue("분할선적(Y/N)");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(14);
			cell.setCellValue("분할선적차수");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(15);
			cell.setCellValue("거래코드 A:전자상거래 B:특송");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(16);
			cell.setCellValue("사업자등록번호");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(17);
			cell.setCellValue("세부번호");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(18);
			cell.setCellValue("Agent Code");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(19);
			cell.setCellValue("보내는화주연락처");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(20);
			cell.setCellValue("받는화주 우편번호");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(21);
			cell.setCellValue("받는화주연락처");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(22);
			cell.setCellValue("받는사람명");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(23);
			cell.setCellValue("받는 사업자 번호");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(24);
			cell.setCellValue("보내는 화주담당자");
			cell.setCellStyle(cellStyleHeader);
			rowCnt++;
			// HEADER 생성 END

			for (int i = 0; i < orderNnoList.size(); i++) {
				ArrayList<ExpLicenceExcelVO> expLicence = new ArrayList<ExpLicenceExcelVO>();
				expLicence = mapper.selectExcelLicenceEFS(orderNnoList.get(i));
				if (expLicence.size() != 0) {
					for (int roop = 0; roop < expLicence.size(); roop++) {
						expLicence.get(roop).dncryptData();
						row = sheet1.createRow(rowCnt);
						cell = row.createCell(0);
						cell.setCellValue(i + 1);

						cell = row.createCell(1);
						cell.setCellValue(expLicence.get(roop).getHawb());

						cell = row.createCell(2);
						cell.setCellValue(expLicence.get(roop).getBoxCnt());

						cell = row.createCell(3);
						cell.setCellValue(expLicence.get(roop).getWta());

						cell = row.createCell(4);
						cell.setCellValue(expLicence.get(roop).getTotalValue());

						cell = row.createCell(5);
						cell.setCellValue(expLicence.get(roop).getItemDetail());

						cell = row.createCell(6);
						cell.setCellValue(expLicence.get(roop).getShipperName());

						cell = row.createCell(7);
						cell.setCellValue(expLicence.get(roop).getShipperAddr() + " "
								+ expLicence.get(roop).getShipperAddrDetail());

						cell = row.createCell(8);
						cell.setCellValue(expLicence.get(roop).getCneeName());

						cell = row.createCell(9);
						cell.setCellValue(
								expLicence.get(roop).getCneeAddr() + " " + expLicence.get(roop).getCneeAddrDetail());

						cell = row.createCell(10);
						cell.setCellValue(expLicence.get(roop).getExpNo());

						cell = row.createCell(11);
						cell.setCellValue(expLicence.get(roop).getBoxCnt());

						cell = row.createCell(12);
						cell.setCellValue(expLicence.get(roop).getTotalValue());

						cell = row.createCell(13);
						cell.setCellValue("");

						cell = row.createCell(14);
						cell.setCellValue("");

						cell = row.createCell(15);
						cell.setCellValue("A");

						cell = row.createCell(16);
						cell.setCellValue(expLicence.get(roop).getExpBusinessNum());

						cell = row.createCell(17);
						cell.setCellValue("");

						cell = row.createCell(18);
						cell.setCellValue("");

						cell = row.createCell(19);
						cell.setCellValue("");

						cell = row.createCell(20);
						cell.setCellValue("");

						cell = row.createCell(21);
						cell.setCellValue("");

						cell = row.createCell(22);
						cell.setCellValue("");

						cell = row.createCell(23);
						cell.setCellValue("");

						cell = row.createCell(24);
						cell.setCellValue("");
						rowCnt++;
					}
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
			return "SSS";
		}

		return null;
	}

	@Override
	public String makeDownExpLicenceInfo(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList) throws Exception {
		// TODO Auto-generated method stub
		String transCode = request.getParameter("transCodes");
		String swh = "USPS";

		// TODO Auto-generated method stub
		LocalDate currentDate = LocalDate.now();
		String savePath = realFilePath + "excel/expLicence/";
		String filename = "EFS_USPS_" + currentDate + ".xlsx";

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
		// ROW-CELL 선언 END

		// HEADER 생성 START 작업중입니단
		row = sheet1.createRow(rowCnt);

		cell = row.createCell(0);
		cell.setCellValue("No");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(1);
		cell.setCellValue("HAWB NO");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(2);
		cell.setCellValue("CT");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(3);
		cell.setCellValue("WT");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(4);
		cell.setCellValue("신고 금액");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(5);
		cell.setCellValue("아이템");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(6);
		cell.setCellValue("보내는화주명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(7);
		cell.setCellValue("보내는화주주소");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(8);
		cell.setCellValue("받는화주명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(9);
		cell.setCellValue("받는화주주소");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(10);
		cell.setCellValue("수출신고번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(11);
		cell.setCellValue("수출신고 C/T");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(12);
		cell.setCellValue("수출신고 W/T");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(13);
		cell.setCellValue("분할선적(Y/N)");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(14);
		cell.setCellValue("분할선적차수");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(15);
		cell.setCellValue("거래코드 A:전자상거래 B:특송");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(16);
		cell.setCellValue("사업자등록번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(17);
		cell.setCellValue("세부번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(18);
		cell.setCellValue("Agent Code");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(19);
		cell.setCellValue("보내는화주연락처");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(20);
		cell.setCellValue("받는화주 우편번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(21);
		cell.setCellValue("받는화주연락처");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(22);
		cell.setCellValue("받는사람명");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(23);
		cell.setCellValue("받는 사업자 번호");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(24);
		cell.setCellValue("보내는 화주담당자");
		cell.setCellStyle(cellStyleHeader);
		rowCnt++;
		// HEADER 생성 END

		for (int i = 0; i < orderNnoList.size(); i++) {
			ArrayList<ExpLicenceExcelVO> expLicence = new ArrayList<ExpLicenceExcelVO>();
			expLicence = mapper.selectExcelLicenceEFS(orderNnoList.get(i));
			if (expLicence.size() != 0) {
				for (int roop = 0; roop < expLicence.size(); roop++) {
					expLicence.get(roop).dncryptData();
					row = sheet1.createRow(rowCnt);
					cell = row.createCell(0);
					cell.setCellValue(i + 1);

					cell = row.createCell(1);
					cell.setCellValue(expLicence.get(roop).getHawb());

					cell = row.createCell(2);
					cell.setCellValue(expLicence.get(roop).getBoxCnt());

					cell = row.createCell(3);
					cell.setCellValue(expLicence.get(roop).getWta());

					cell = row.createCell(4);
					cell.setCellValue(expLicence.get(roop).getTotalValue());

					cell = row.createCell(5);
					cell.setCellValue(expLicence.get(roop).getItemDetail());

					cell = row.createCell(6);
					cell.setCellValue(expLicence.get(roop).getShipperName());

					cell = row.createCell(7);
					cell.setCellValue(
							expLicence.get(roop).getShipperAddr() + " " + expLicence.get(roop).getShipperAddrDetail());

					cell = row.createCell(8);
					cell.setCellValue(expLicence.get(roop).getCneeName());

					cell = row.createCell(9);
					cell.setCellValue(
							expLicence.get(roop).getCneeAddr() + " " + expLicence.get(roop).getCneeAddrDetail());

					cell = row.createCell(10);
					cell.setCellValue(expLicence.get(roop).getExpNo());

					cell = row.createCell(11);
					cell.setCellValue(expLicence.get(roop).getBoxCnt());

					cell = row.createCell(12);
					cell.setCellValue(expLicence.get(roop).getWta());

					cell = row.createCell(13);
					cell.setCellValue("");

					cell = row.createCell(14);
					cell.setCellValue("");

					cell = row.createCell(15);
					cell.setCellValue("A");

					cell = row.createCell(16);
					cell.setCellValue(expLicence.get(roop).getExpBusinessNum());

					cell = row.createCell(17);
					cell.setCellValue("");

					cell = row.createCell(18);
					cell.setCellValue("");

					cell = row.createCell(19);
					cell.setCellValue("");

					cell = row.createCell(20);
					cell.setCellValue("");

					cell = row.createCell(21);
					cell.setCellValue("");

					cell = row.createCell(22);
					cell.setCellValue("");

					cell = row.createCell(23);
					cell.setCellValue("");

					cell = row.createCell(24);
					cell.setCellValue("");
					rowCnt++;
				}
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
		return "SSS";
	}

	@Override
	public String makeExplicence(HawbVO hawbVo, ArrayList<ExpLicenceExcelVO> expLicence) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> rtnJsonArray = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();

		ArrayList<LinkedHashMap<String, Object>> itemList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> itemOne = new LinkedHashMap<String, Object>();
		String hawbNo = hawbVo.getHawbNo();

		ArrayList<ExpLicenceExcelVO> expLicenceItem = new ArrayList<ExpLicenceExcelVO>();

		expLicenceItem = mapper.selectExcelLicence(hawbVo.getNno());
		HashMap<String, Object> businessNames = new HashMap<String, Object>();
		businessNames = mapper.selectExpBusinessInfo(expLicence.get(0).getHawb());
		String expBusinessName = businessNames.get("expBusinessName").toString();
		String agencyBusinessName = businessNames.get("agencyBusinessName").toString();
		
		//String agency = mapper.selectExpCom(expLicence.get(0).getHawb());

		try {
			String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			rtnJsonArray.put("req_date", dateTime);
			rtnJsonArray.put("req_com_cd", "ACIX");
			rtnJsonArray.put("req_cus_cd", "MUSI");
			rtnJsonArray.put("req_awb_cnt", "1");

			dataOne.put("hawb", expLicence.get(0).getHawb());
			dataOne.put("agency", agencyBusinessName);
			dataOne.put("exporter", expBusinessName);
			dataOne.put("maker", "미상");
			dataOne.put("buyer", expLicence.get(0).getCneeName());
			dataOne.put("dest_cd", expLicence.get(0).getDstnNation());
			dataOne.put("t_wt", expLicence.get(0).getWta());
			dataOne.put("t_pkg", expLicence.get(0).getBoxCnt());
			dataOne.put("c_unit", expLicence.get(0).getCurrency());
			dataOne.put("t_amt", expLicence.get(0).getTotalValue());
			dataOne.put("inv_no", expLicence.get(0).getHawb());
			dataOne.put("inco_gb", expLicence.get(0).getPayment());
			dataOne.put("doc_return_gb", "N");

			for (int i = 0; i < expLicenceItem.size(); i++) {
				itemOne = new LinkedHashMap<String, Object>();
				itemOne.put("g_pum", expLicenceItem.get(i).getItemDetail());
				itemOne.put("p_pum", "");
				itemOne.put("item_nm", expLicenceItem.get(i).getItemDetail());
				itemOne.put("brand", expLicenceItem.get(i).getBrand());
				itemOne.put("qty", expLicenceItem.get(i).getItemCnt());
				itemOne.put("up", expLicenceItem.get(i).getUnitValue());
				itemOne.put("amt", expLicenceItem.get(i).getValue());
				itemOne.put("hscode", expLicenceItem.get(i).getHsCode());
				itemList.add(itemOne);
			}
			dataOne.put("item_info", itemList);
			dataList.add(dataOne);
			rtnJsonArray.put("awb_info", dataList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
			rtnJsonArray.put("failStatus", e.getMessage());
		}

		return getJsonStringFromMap(rtnJsonArray);
	}

	@Override
	public int expLicenceChk(HawbVO hawbVo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.expLicenceChk(hawbVo);
	}

	public int expLicenceChk2(String hawbNo) throws Exception {
		return mapper.expLicenceChk2(hawbNo);
	}

	public String getJsonStringFromMap(HashMap<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}

		return jsonObject.toString();
	}

	public ProcedureVO getMusResult(String rtnMsg, String hawbNo, String nno) throws Exception {
		ProcedureVO returnVal = new ProcedureVO();

		JSONObject json = new JSONObject(String.valueOf(rtnMsg));
		HashMap<String, Object> sendData = new HashMap<String, Object>();
		sendData.put("hawbNo", nno);
		sendData.put("serviceName", "explicenceSendMus");
		sendData.put("status", "Success");
		sendData.put("sequence", "Mus Result Function Call");
		comnServiceImpl.insertSendTable(sendData);
		if (json.getString("result_cd").equals("S")) {
			JSONObject json2 = new JSONObject(String.valueOf(json.get("result_data").toString()));
			JSONArray json3 = new JSONArray(String.valueOf(json2.get("awb_result").toString()));
			JSONObject json4 = json3.getJSONObject(0);
			HashMap<String, String> expLicenceIn = new HashMap<String, String>();
			expLicenceIn.put("expNo", json4.getString("expo_singo_no"));
			expLicenceIn.put("expRegNo", json4.getString("hawb"));
			try {
				mapper.updateLicenceInfo(expLicenceIn);
				returnVal.setRstCode("S");
				returnVal.setRstMsg(json4.getString("expo_singo_no"));
				returnVal.setRstStatus("S");

				sendData = new HashMap<String, Object>();
				sendData.put("hawbNo", nno);
				sendData.put("serviceName", "explicenceSendMus");
				sendData.put("status", "Success");
				sendData.put("sequence", "Mus Result > Update Explicence Table");
				comnServiceImpl.insertSendTable(sendData);
			} catch (Exception e) {
				// TODO: handle exception
				mapper.updateLicenceInfoFail(expLicenceIn);
				returnVal.setRstCode("F");
				returnVal.setRstMsg(e.getMessage());
				returnVal.setRstStatus("F");

				sendData = new HashMap<String, Object>();
				sendData.put("hawbNo", nno);
				sendData.put("serviceName", "explicenceSendMus");
				sendData.put("status", "Fail");
				sendData.put("sequence", "Mus Result > Update Explicence Table");
				comnServiceImpl.insertSendTable(sendData);
			}

		} else {
			sendData = new HashMap<String, Object>();
			sendData.put("hawbNo", nno);
			sendData.put("serviceName", "explicenceSendMus");
			sendData.put("status", "Fail");
			sendData.put("sequence", "Mus Result Receive Fail");
			comnServiceImpl.insertSendTable(sendData);

			HashMap<String, String> expLicenceIn = new HashMap<String, String>();
			expLicenceIn.put("expNo", hawbNo);
			expLicenceIn.put("expRegNo", hawbNo);
			mapper.updateLicenceInfoFail(expLicenceIn);
			returnVal.setRstCode("F");
			returnVal.setRstMsg(json.getString("result_msg"));
			returnVal.setRstStatus("F");
		}
		return returnVal;
	}

	@Override
	public ArrayList<String> selectNotSendEfsInfo() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNotSendEfsInfo();
	}

	@Override
	public ArrayList<ExpLicenceExcelVO> selectExcelLicenceEFS(String orderNno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectExcelLicenceEFS(orderNno);
	}

	@Override
	public StockVO selectStockByStockNo(String stockNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockByStockNo(stockNo);
	}

	@Override
	public StockResultVO selectStockResultStockVO(HttpServletRequest request, String stockNo) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		StockResultVO targetStockList = new StockResultVO();
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		targetStockList = mapper.selectStockResultStockVO(stockNo);

		return targetStockList;
	}

	@Override
	public ApiOrderListVO selectOrderListAramex(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderListAramex(nno);
	}

	@Override
	public ArrayList<ApiOrderItemListVO> selectOrderItemAramex(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderItemAramex(nno);
	}

	@Override
	public HashMap<String, ArrayList<String>> selectZoneMap(String userId, String orgStation) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<String>> rtnMap = new HashMap<String, ArrayList<String>>();
		String orgNation = comnServiceImpl.selectStationToNation(orgStation);

		ArrayList<String> zoneNationList = new ArrayList<String>();
		zoneNationList = mapper.selectDstnList(userId, orgNation);
		rtnMap.put(orgNation, zoneNationList);

		return rtnMap;
	}

	@Override
	public ArrayList<DlvPriceVO> selectInvoiceName() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectInvoiceName();
	}

	@Override
	public ArrayList<NationVO> selectStationInfo() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStationInfo();
	}

	@Override
	public ArrayList<String> selectMenualDstnList(String userId, String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMenualDstnList(userId, orgStation);
	}

	@Override
	public void insertCustomerChgInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertCustomerChgInfo(parameters);
	}

	@Override
	public void updateCustomerChgInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateCustomerChgInfo(parameters);
	}

	@Override
	public void deleteCustomerChgInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteCustomerChgInfo(parameters);
	}

	@Override
	public ArrayList<DlvChgInfoVO> selectDlvChgInfo(String userId, String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDlvChgInfo(userId, orgStation);
	}

	@Override
	public void zoneExcelUploadIndividual(MultipartHttpServletRequest multi, HttpServletRequest request,
			String excelRoot, String userId) throws Exception {
		// TODO Auto-generated method stub

		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		HashMap<String, Object> zoneExcelHead = new HashMap<String, Object>();
		HashMap<Integer, Object> zoneNumber = new HashMap<Integer, Object>();
		HashMap<Integer, ArrayList<String>> zoneNationList = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> wtSection = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> zonePriceList = new HashMap<Integer, ArrayList<String>>();
		HashMap<String, Object> zoneExcelbody = new HashMap<String, Object>();
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					XSSFRow row = sheet.getRow(0); // 현재 row정보
					for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
						row = sheet.getRow(rowIndex); // 현재 row정보
						String _nameValue = "";
						String _value = "";
						XSSFCell _cellName = row.getCell(0); // 셀에 담겨있는 값을 읽는다.
						XSSFCell _cellValue = row.getCell(1); // 셀에 담겨있는 값을 읽는다.
						if (_cellName == null) {
							_nameValue = "";
						} else {
							switch (_cellName.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
							case HSSFCell.CELL_TYPE_NUMERIC:

								if (DateUtil.isCellDateFormatted(_cellName)) {
									Date date = _cellName.getDateCellValue();
									_nameValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									_cellName.setCellType(HSSFCell.CELL_TYPE_STRING);
									_nameValue = _cellName.getStringCellValue();
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:
								_nameValue = _cellName.getStringCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								_nameValue = "";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								_nameValue = _cellName.getErrorCellValue() + "";
								break;
							}

							switch (_cellValue.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
							case HSSFCell.CELL_TYPE_NUMERIC:

								if (DateUtil.isCellDateFormatted(_cellValue)) {
									Date date = _cellValue.getDateCellValue();
									_value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									_cellValue.setCellType(HSSFCell.CELL_TYPE_STRING);
									_value = _cellValue.getStringCellValue();
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:
								_value = _cellValue.getStringCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								_value = "";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								_value = _cellValue.getErrorCellValue() + "";
								break;
							}

						}
						switch (_nameValue.replaceAll(" ", "")) {
						case "통화": {
							zoneExcelHead.put("currency", _value);
							break;
						}
						case "제목": {
							zoneExcelHead.put("invoiceName", _value);
							break;
						}
						case "무게단위": {
							zoneExcelHead.put("wtUnit", _value);
							break;
						}
						}

					}

					row = sheet.getRow(3); // 현재 row정보
					if (row != null) {

						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							String _zoneNumValue = "";
							XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
							if (_cell == null) {
								_zoneNumValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneNumValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneNumValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneNumValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneNumValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneNumValue = _cell.getErrorCellValue() + "";
									break;
								}
								zoneNumber.put(columnIndex, _zoneNumValue);

							}
						}

					}

					row = sheet.getRow(4); // 현재 row정보
					if (row != null) {
						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							ArrayList<String> zoneNation = new ArrayList<String>();
							String _zoneNationValue = "";
							XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
							if (_cell == null) {
								_zoneNationValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneNationValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneNationValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneNationValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneNationValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneNationValue = _cell.getErrorCellValue() + "";
									break;
								}
								if (_zoneNationValue.replaceAll(" ", "").split(",").length != 1) {
									String[] tempZoneNation = _zoneNationValue.replaceAll(" ", "").split(",");
									for (int i = 0; i < tempZoneNation.length; i++) {
										zoneNation.add(tempZoneNation[i]);
									}
								} else {
									zoneNation.add(_zoneNationValue);
								}
								zoneNationList.put(columnIndex, zoneNation);
							}
						}
					}

					if (row != null) {
						int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
						for (int rowIndex = 6; rowIndex < rows; rowIndex++) {
							row = sheet.getRow(rowIndex); // 현재 row정보
							XSSFCell _cell = row.getCell(0); // 셀에 담겨있는 값을 읽는다.
							String _zoneWtValue = "";
							if (_cell == null) {
								_zoneWtValue = "";
							} else {
								switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:

									if (DateUtil.isCellDateFormatted(_cell)) {
										Date date = _cell.getDateCellValue();
										_zoneWtValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_zoneWtValue = _cell.getStringCellValue();
									}
									break;
								case HSSFCell.CELL_TYPE_STRING:
									_zoneWtValue = _cell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									_zoneWtValue = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									_zoneWtValue = _cell.getErrorCellValue() + "";
									break;
								}
							}
							wtSection.add(_zoneWtValue);
						}
						row = sheet.getRow(6); // 현재 row정보
						for (int columnIndex = 1; columnIndex < row.getLastCellNum(); columnIndex++) {
							ArrayList<String> zonePrice = new ArrayList<String>();
							for (int rowIndex = 6; rowIndex < rows; rowIndex++) {
								String _zonePriceValue = "";
								XSSFRow subRow = sheet.getRow(rowIndex); // 현재 row정보
								XSSFCell _cell = subRow.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.

								if (_cell == null) {
									_zonePriceValue = "";
								} else {
									switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:

										if (DateUtil.isCellDateFormatted(_cell)) {
											Date date = _cell.getDateCellValue();
											_zonePriceValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
										} else {
											_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
											_zonePriceValue = _cell.getStringCellValue();
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										_zonePriceValue = _cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										_zonePriceValue = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										_zonePriceValue = _cell.getErrorCellValue() + "";
										break;
									}
									zonePrice.add(_zonePriceValue);
								}
							}
							zonePriceList.put(columnIndex, zonePrice);
						}

					}
					zoneExcelHead.put("wUserId", request.getSession().getAttribute("USER_ID"));
					zoneExcelHead.put("wUserIp", request.getSession().getAttribute("USER_IP"));
					zoneExcelHead.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
					ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
					String nameHead = nowSeoul.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					zoneExcelHead.put("dlvCode", nameHead + "_INDIVIDUAL");

					ArrayList<DlvPriceVO> dlvPriceVo = new ArrayList<DlvPriceVO>();
					DlvPriceVO dlvPriceDetail = new DlvPriceVO();

					dlvPriceDetail.setDlvCode(zoneExcelHead.get("dlvCode").toString());
					dlvPriceDetail.setWUserId(zoneExcelHead.get("wUserId").toString());
					dlvPriceDetail.setWUserIp(zoneExcelHead.get("wUserIp").toString());
					dlvPriceDetail.setWtUnit(zoneExcelHead.get("wtUnit").toString());
					dlvPriceDetail.setCurrency(zoneExcelHead.get("currency").toString());
					dlvPriceDetail.setOrgStation(zoneExcelHead.get("orgStation").toString());

					dlvPriceDetail.setDlvPriceName(zoneExcelHead.get("invoiceName").toString());

					// zone 개수 만큼 반복
					for (int i = 0; i < zoneNumber.size(); i++) {
						dlvPriceDetail.setZoneCode(zoneNumber.get(i + 1).toString());
						ArrayList<String> nationList = zoneNationList.get(i + 1);
						ArrayList<String> priceList = zonePriceList.get(i + 1);
						// zone 별 국가 수 만큼 반복
						for (int j = 0; j < nationList.size(); j++) {
							dlvPriceDetail.setDstnNation(nationList.get(j));// 국가 2코드
							for (int k = 0; k < wtSection.size(); k++) {
								if (priceList.size() - 1 < k) {
									continue;
								} else {
									dlvPriceDetail.setWt(wtSection.get(k));// 무게 구간
									dlvPriceDetail.setPrice(priceList.get(k));
									mapper.insertDlvPrice(dlvPriceDetail);
								}
							}
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("orgStation", dlvPriceDetail.getOrgStation());
							parameters.put("userId", userId);
							parameters.put("volumeDiscount", 0);
							parameters.put("wUserId", dlvPriceDetail.getWUserId());
							parameters.put("wUserIp", dlvPriceDetail.getWUserIp());
							parameters.put("dvlChgType", "Individual");
							parameters.put("dstnNation", dlvPriceDetail.getDstnNation());
							parameters.put("dlvCode", dlvPriceDetail.getDlvCode());
							parameters.put("actualDiscount", 0);
							mapper.insertCustomerChgInfoIndividual(parameters);
						}
					}
				}

//					else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
//					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
//					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
//					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
//					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
//					{ // row=0은 헤더이므로 1부터 시작
//						expLicenceIn = new HashMap<String,String>(); 
//						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
//						if (row != null) 
//						{
//							String value = "";
//							for (int columnIndex = 0; columnIndex < 2; columnIndex++) 
//							{
//								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
//								if(cell != null) {
//									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
//									case HSSFCell.CELL_TYPE_NUMERIC:
//										cell.setCellType( HSSFCell.CELL_TYPE_STRING );
//										value = cell.getStringCellValue();
//										break;
//									case HSSFCell.CELL_TYPE_STRING:
//										value = cell.getStringCellValue() + "";
//										break;
//									case HSSFCell.CELL_TYPE_BLANK:
//										value = "";
//										break;
//									case HSSFCell.CELL_TYPE_ERROR:
//										value = cell.getErrorCellValue() + "";
//										break;
//									}
//									
//									switch (columnIndex) {
//										case 1:{
//											expLicenceIn.put("expNo", value);
//											break;
//										}
//										case 0:{
//											expLicenceIn.put("expRegNo", value);
//											break;
//										}
//									
//									}
//								}
//							}
//							mapper.updateLicenceInfo(expLicenceIn);
//						}
//					}
//				}

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	@Override
	public void resetCustomerChgInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.resetCustomerChgInfo(parameters);
	}

	@Override
	public void insertCustoerInvoice(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateCustoerInvoice(parameters);
	}

	@Override
	public void updateCustomerEtcFee(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateCustomerEtcFee(parameters);
	}

	@Override
	public ArrayList<ApiOrderItemListVO> selectOrderItemAramexMember(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrderItemAramexMember(nno);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectHawbTrans(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbTrans(orgStation);
	}

	@Override
	public String selectSekoHawb(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSekoHawb(hawbNo);
	}

	@Override
	public ArrayList<HawbVO> selectHawbListArr(String mawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectHawbListArr(mawbNo);
	}

	@Override
	public ArrayList<MawbVO> selectMawbList3(MawbVO parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMawbList3(parameters);
	}

	@Override
	public void insertMawbArr(HawbVO hawbVo) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertMawbArr(hawbVo);
	}

	@Override
	public HashMap<String, Object> selectMawbArrInfo(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMawbArrInfo(hawbNo);
	}

	@Override
	public void updateMawbArr(HashMap<String, Object> mawbArrInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateMawbArr(mawbArrInfo);
	}

	@Override
	public String selectUdate(HashMap<String, Object> mawbArrInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUdate(mawbArrInfo);
	}

	@Override
	public String selectNNOByHawbNo(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNNOByHawbNo(hawbNo);
	}

	@Override
	public HashMap<String, Object> checkMawbArr(String hawbNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.checkMawbArr(hawbNo);
	}

	@Override
	public ArrayList<String> selectDistinctBlList() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDistinctBlList();
	}

	public void deleteWebHookList(String userId) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteWebHookList(userId);
	}

	public void insertWebHookList(HashMap<String, Object> webHookList) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertWebHookList(webHookList);
	}

	@Override
	public ArrayList<String> selectExpLicenceYsl() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectExpLicenceYsl();
	}

	@Override
	public String selectExpLicenceYslChk(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectExpLicenceYslChk(nno);
	}

	// 출고 목록 추가 ---------------
	@Override
	public ArrayList<StockOutVO> selectStockOutList(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockOutList(parameterInfo);
	}

	@Override
	public int selectStockOutListCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockOutListCnt(parameterInfo);
	}

	@Override
	public ArrayList<StockOutVO> stockOutListByMonth(HashMap<String, Object> parameterInfo) {
		return mapper.stockOutListByMonth(parameterInfo);
	}

	@Override
	public ArrayList<StockOutVO> selectStockOutByUserId(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockOutByUserId(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectStationCodeList(HashMap<String, Object> parameterInfo) {
		return mapper.selectStationCodeList(parameterInfo);
	}

	@Override
	public ArrayList<StockOutVO> selectStockOutListChart(HashMap<String, Object> parameterInfo) {
		return mapper.selectStockOutListChart(parameterInfo);
	}

	@Override
	public ArrayList<StockOutVO> selectStockOutListChart(StockOutVO vo) {
		return mapper.selectStockOutListChart(vo);
	}

	@Override
	public void selectStockOutListExcel(StockOutVO stockOutVO, HttpServletResponse response, HttpServletRequest request,
			HashMap<String, Object> parameterInfo) {

		ArrayList<StockOutVO> list = mapper.selectStockOutListExcel(parameterInfo);

		try {
			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/send/excelSample/stock_out.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			sheet.createFreezePane(0, 1);

			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			Font font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setBorderTop(CellStyle.BORDER_THIN);
			headStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headStyle.setBorderRight(CellStyle.BORDER_THIN);
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setFont(font);

			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);

			CellStyle cntStyle = workbook.createCellStyle();
			cntStyle.setBorderTop(CellStyle.BORDER_THIN);
			cntStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cntStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cntStyle.setBorderRight(CellStyle.BORDER_THIN);
			cntStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			String[] headerArray = { "Station Code", "Station Name", "Dep Month", "CNT" };
			row = sheet.createRow(rowNo++);
			for (int i = 0; i < headerArray.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(headerArray[i]);
			}

			for (StockOutVO data : list) {
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getOrgStation());

				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getStationName());

				cell = row.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getDepMonth());

				cell = row.createCell(3);
				cell.setCellStyle(cntStyle);
				cell.setCellValue(data.getCnt());
			}

			MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId = member.getUsername();
			String fileName = userId + "_stockOutList";

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public void stockOutListByMonthExcel(StockOutVO stockOutVO, HttpServletResponse response,
			HttpServletRequest request, HashMap<String, Object> parameterInfo) {
		System.out.println("^^^^");
		ArrayList<StockOutVO> list = mapper.stockOutListByMonth(parameterInfo);

		try {
			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/send/excelSample/stock_out_by_month.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			sheet.createFreezePane(0, 1);

			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			Font font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setBorderTop(CellStyle.BORDER_THIN);
			headStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headStyle.setBorderRight(CellStyle.BORDER_THIN);
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setFont(font);

			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);

			CellStyle cntStyle = workbook.createCellStyle();
			cntStyle.setBorderTop(CellStyle.BORDER_THIN);
			cntStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cntStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cntStyle.setBorderRight(CellStyle.BORDER_THIN);
			cntStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			String[] headerArray = { "USER ID", "Dep Month", "CNT" };
			row = sheet.createRow(rowNo++);
			for (int i = 0; i < headerArray.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(headerArray[i]);
			}

			for (StockOutVO data : list) {
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getUserId());

				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getDepMonth());

				cell = row.createCell(2);
				cell.setCellStyle(cntStyle);
				cell.setCellValue(data.getCnt());
			}

			MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId = member.getUsername();
			String fileName = userId + "_stockOutList";

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public void stockOutListByUserIdExcel(StockOutVO stockOutVO, HttpServletResponse response,
			HttpServletRequest request, HashMap<String, Object> parameterInfo) {
		System.out.println("$$$$");
		ArrayList<StockOutVO> list = mapper.selectStockOutByUserId(parameterInfo);

		try {
			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/send/excelSample/stock_out_by_userId.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			sheet.createFreezePane(0, 1);

			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			Font font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setBorderTop(CellStyle.BORDER_THIN);
			headStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headStyle.setBorderRight(CellStyle.BORDER_THIN);
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setFont(font);

			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);

			CellStyle cntStyle = workbook.createCellStyle();
			cntStyle.setBorderTop(CellStyle.BORDER_THIN);
			cntStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cntStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cntStyle.setBorderRight(CellStyle.BORDER_THIN);
			cntStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			String[] headerArray = { "USER ID", "Dep Date", "CNT" };
			row = sheet.createRow(rowNo++);
			for (int i = 0; i < headerArray.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(headerArray[i]);
			}

			for (StockOutVO data : list) {
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getUserId());

				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getDepDate());

				cell = row.createCell(2);
				cell.setCellStyle(cntStyle);
				cell.setCellValue(data.getCnt());
			}

			MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId = member.getUsername();
			String fileName = userId + "_stockOutList";

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public void stockOutExcelDown(StockOutVO stockOutVO, HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) {

		ArrayList<StockOutVO> monthList = mapper.stockOutListByMonthExcel2(parameterInfo);
		ArrayList<StockOutVO> detailList = mapper.selectStockOutByUserId2(parameterInfo);

		try {

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/send/excelSample/stockOutByMonth.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Sheet sheet2 = workbook.createSheet("일자별 출고내역");
			sheet2.setDisplayGridlines(false);

			sheet2.setColumnWidth(2, (short) 5000);
			sheet2.setColumnWidth(1, (short) 4000);
			sheet2.createFreezePane(0, 1);

			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			Row row2 = null;
			Cell cell2 = null;
			int rowNo2 = 0;

			Font headFont = workbook.createFont();
			headFont.setFontName("맑은 고딕");
			headFont.setFontHeightInPoints((short) 12);
			headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headStyle.setBorderRight(CellStyle.BORDER_THIN);
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setFont(headFont);

			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle cntStyle = workbook.createCellStyle();
			cntStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cntStyle.setBorderTop(CellStyle.BORDER_THIN);
			cntStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cntStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cntStyle.setBorderRight(CellStyle.BORDER_THIN);

			String[] headerArray = { "Station", "Dep Month", "USER ID", "CNT" };

			String[] headerArray2 = { "Station", "Dep Date", "USER ID", "CNT" };

			row = sheet.createRow(rowNo++);
			for (int i = 0; i < headerArray.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(headerArray[i]);
			}

			for (StockOutVO data : monthList) {
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getOrgStation());

				String depMonth = data.getDepMonth();
				Date date = new SimpleDateFormat("yyyyMM").parse(depMonth);
				String newDepMonth = new SimpleDateFormat("yyyy-MM").format(date);

				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(newDepMonth);

				cell = row.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(data.getUserId());

				cell = row.createCell(3);
				cell.setCellStyle(cntStyle);
				cell.setCellValue(data.getCnt());
			}

			row2 = sheet2.createRow(rowNo2++);
			for (int i = 0; i < headerArray2.length; i++) {
				cell2 = row2.createCell(i);
				cell2.setCellStyle(headStyle);
				cell2.setCellValue(headerArray2[i]);
			}

			for (StockOutVO data : detailList) {
				row2 = sheet2.createRow(rowNo2++);
				cell2 = row2.createCell(0);
				cell2.setCellStyle(bodyStyle);
				cell2.setCellValue(data.getOrgStation());

				String depDate = data.getDepDate();
				Date date = new SimpleDateFormat("yyyyMMdd").parse(depDate);
				String newDepDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

				cell2 = row2.createCell(1);
				cell2.setCellStyle(bodyStyle);
				cell2.setCellValue(newDepDate);

				cell2 = row2.createCell(2);
				cell2.setCellStyle(bodyStyle);
				cell2.setCellValue(data.getUserId());

				cell2 = row2.createCell(3);
				cell2.setCellStyle(cntStyle);
				cell2.setCellValue(data.getCnt());

			}

			MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId = member.getUsername();
			String fileName = userId + "_stockOutList";

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public String selectStationCode(String stationName) {
		String orgStation = mapper.selectStationCode(stationName);

		return orgStation;
	}

	@Override
	public ArrayList<StockOutVO> selectClickedChart(HashMap<String, Object> parameterInfo) {
		return mapper.selectClickedChart(parameterInfo);
	}

	@Override
	public void selectInvoiceExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) {

		try {
			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/invoice/invoice_aci.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Row row = null;
			Cell cell = null;

			Row row2 = null;
			Cell cell2 = null;

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Sheet sheet2 = workbook.createSheet("etc_detail");

			CellStyle styleRight = workbook.createCellStyle();
			styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			styleRight.setBorderTop(CellStyle.BORDER_THIN);
			styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			styleRight.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);

			int cellNo = 0;
			int rowNo = 0;

			HashMap<String, Object> invoiceInfo = new HashMap<String, Object>();
			invoiceInfo = mapper.selectInvoiceInfo(parameterInfo);

			rowNo = 4;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue((String) invoiceInfo.get("STATION_ADDR"));

			rowNo = 5;
			cellNo = 0;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue((String) invoiceInfo.get("STATION_TEL"));

			rowNo = 8;
			cellNo = 0;
			row = sheet.getRow(rowNo);

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public ArrayList<StockOutVO> selectUserStockOutList(HashMap<String, Object> params) {
		return mapper.selectUserStockOutList(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTransCodeFilter(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeFilter(params);
	}

	@Override
	public int selectTransCodeTotalCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeTotalCnt(params);
	}

	@Override
	public int selectInBoundCnt(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectInBoundCnt(parameterInfo);
	}

	@Override
	public int selectOutBoundCnt(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectOutBoundCnt(parameterInfo);
	}

	@Override
	public ArrayList<StockOutVO> selectStockOutMonthlyList(HashMap<String, Object> params2) {
		// TODO Auto-generated method stub
		return mapper.selectStockOutMonthlyList(params2);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectDailyChart(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectDailyChart(params);
	}

	@Override
	public String selectNNOByStockNo(HashMap<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectNNOByStockNo(parameter);
	}

	@Override
	public ArrayList<NoticeVO> selectNotice(HashMap<String, Object> infoMap) {
		// TODO Auto-generated method stub
		return mapper.selectNoticeList(infoMap);
	}

	@Override
	public int selectDepositPrice(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectDepositPrice(userId);
	}

	@Override
	public void insertDepositPrice(ManagerVO userInfoVO) {
		mapper.insertDepositPrice(userInfoVO);

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectMawbManifestList(HashMap<String, Object> infoMap) {
		// TODO Auto-generated method stub
		return mapper.selectMawbManifestList(infoMap);
	}

	@Override
	public int selectMawbManifestListCnt(HashMap<String, Object> infoMap) {
		// TODO Auto-generated method stub
		return mapper.selectMawbManifestListCnt(infoMap);
	}

	@Override
	public void orderInfoExcelDown(String mawbNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList<UserOrderListVO> orderInfo = new ArrayList<UserOrderListVO>();
		ArrayList<UserOrderItemVO> itemInfo = new ArrayList<UserOrderItemVO>();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mawbNo", mawbNo);

		orderInfo = mapper.selectOrderInfo(params);

		String filePath = request.getSession().getServletContext().getRealPath("/")
				+ "WEB-INF/jsp/adm/rls/mawbList/sampleExcel/mawbInfo.xlsx";

		FileInputStream fis = new FileInputStream(filePath);

		Row row = null;
		Cell cell = null;

		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);
		wb.setSheetName(wb.getSheetIndex(sheet), mawbNo);

		int rowNo = 1;

		float wta = 0;
		float wtc = 0;

		Font font = wb.createFont();
		font.setFontName("NanumGothic");
		font.setFontHeightInPoints((short) 9);

		CellStyle style = wb.createCellStyle();
		style.setWrapText(false);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(font);

		CellStyle styleR = wb.createCellStyle();
		styleR.setWrapText(false);
		styleR.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleR.setFont(font);
		styleR.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle styleC = wb.createCellStyle();
		styleC.setWrapText(false);
		styleC.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleC.setFont(font);
		styleC.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle styleWarn = wb.createCellStyle();
		styleWarn.setWrapText(false);
		styleWarn.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleWarn.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		styleWarn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleWarn.setFont(font);
		styleWarn.setAlignment(CellStyle.ALIGN_RIGHT);

		Double totalAmount = 0.0;

		for (int i = 0; i < orderInfo.size(); i++) {
			row = sheet.createRow(rowNo);

			orderInfo.get(i).dncryptData();

			cell = row.createCell(0);
			cell.setCellValue(mawbNo);
			cell.setCellStyle(styleC);

			cell = row.createCell(1);
			cell.setCellValue(orderInfo.get(i).getHawbNo());
			cell.setCellStyle(styleC);

			cell = row.createCell(2);
			cell.setCellValue(orderInfo.get(i).getOrderNo());
			cell.setCellStyle(styleC);

			cell = row.createCell(3);
			cell.setCellValue(orderInfo.get(i).getOrderDate());
			cell.setCellStyle(styleC);

			cell = row.createCell(4);
			cell.setCellValue(orderInfo.get(i).getShipperName());
			cell.setCellStyle(styleC);

			cell = row.createCell(5);
			cell.setCellValue(orderInfo.get(i).getShipperAddr());
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue(orderInfo.get(i).getCneeName());
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue(orderInfo.get(i).getCneeZip());
			cell.setCellStyle(styleC);

			cell = row.createCell(8);
			cell.setCellValue(orderInfo.get(i).getCneeAddr());
			cell.setCellStyle(style);

			/*
			 * cell = row.createCell(9);
			 * cell.setCellValue(orderInfo.get(i).getCneeAddrDetail());
			 * cell.setCellStyle(style);
			 */

			cell = row.createCell(9);
			cell.setCellValue(orderInfo.get(i).getCneeState());
			cell.setCellStyle(style);

			cell = row.createCell(10);
			cell.setCellValue(orderInfo.get(i).getCneeCity());
			cell.setCellStyle(style);

			cell = row.createCell(11);
			cell.setCellValue(orderInfo.get(i).getCneeTel());
			cell.setCellStyle(style);

			cell = row.createCell(12);
			cell.setCellValue(orderInfo.get(i).getCneeHp());
			cell.setCellStyle(styleC);

			cell = row.createCell(13);
			cell.setCellValue(orderInfo.get(i).getWta());
			cell.setCellStyle(styleR);

			cell = row.createCell(14);
			cell.setCellValue(orderInfo.get(i).getWtc());
			cell.setCellStyle(styleR);

			cell = row.createCell(15);
			cell.setCellValue(orderInfo.get(i).getBoxCnt());
			cell.setCellStyle(styleR);

			itemInfo = mapper.selectOrderItem(orderInfo.get(i).getNno());

			for (int idx = 0; idx < itemInfo.size(); idx++) {
				totalAmount += Double.parseDouble(itemInfo.get(idx).getUnitValue());
			}

			cell = row.createCell(16);
			cell.setCellValue(totalAmount);
			if (totalAmount > 800) {
				cell.setCellStyle(styleWarn);
			} else {
				cell.setCellStyle(styleR);
			}

			for (int j = 0; j < itemInfo.size(); j++) {

				cell = row.createCell(17);
				cell.setCellValue(itemInfo.get(j).getBrand());
				cell.setCellStyle(style);

				cell = row.createCell(18);
				cell.setCellValue(itemInfo.get(j).getItemDetail());
				cell.setCellStyle(style);

				cell = row.createCell(19);
				cell.setCellValue(itemInfo.get(j).getItemCnt());
				cell.setCellStyle(styleR);

				cell = row.createCell(20);
				cell.setCellValue(itemInfo.get(j).getUnitValue());
				cell.setCellStyle(styleR);

				cell = row.createCell(21);
				cell.setCellValue(itemInfo.get(j).getCusItemCode());
				cell.setCellStyle(style);

				cell = row.createCell(22);
				cell.setCellValue(itemInfo.get(j).getChgCurrency());
				cell.setCellStyle(style);

				cell = row.createCell(23);
				cell.setCellValue(itemInfo.get(j).getMakeCntry());
				cell.setCellStyle(style);

				cell = row.createCell(24);
				cell.setCellValue(itemInfo.get(j).getMakeCom());
				cell.setCellStyle(style);

				cell = row.createCell(25);
				cell.setCellValue(itemInfo.get(j).getHsCode());
				cell.setCellStyle(style);

				cell = row.createCell(26);
				cell.setCellValue(orderInfo.get(i).getNno());
				cell.setCellStyle(style);

				cell = row.createCell(27);
				cell.setCellValue(itemInfo.get(j).getSubNo());
				cell.setCellStyle(style);

				rowNo++;
				row = sheet.createRow(rowNo);
			}

		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + mawbNo + ".xlsx");

		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		out.close();

	}

	@Override
	public String updateOrderExcel(MultipartHttpServletRequest multi, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";

		int currentRow = 0;

		Map<String, String> resMap = new HashMap<String, String>();
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";

		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);

			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					XSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					System.out.println("엑셀 로우 수");
					System.out.println(rows - 1);
					// Reading Rows
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

						System.out.println("Reading ...");
						System.out.println("now index of Row : " + rowIndex);

						// UserOrderListVO beforeOrder = new UserOrderListVO();
						// UserOrderItemVO beforeItem = new UserOrderItemVO();

						UpdateOrderListVO updateOrderList = new UpdateOrderListVO();
						UpdateOrderItemVO updateItemList = new UpdateOrderItemVO();

						UserOrderListVO orderList = new UserOrderListVO();
						UserOrderItemVO itemList = new UserOrderItemVO();
						HashMap<String, Object> params = new HashMap<String, Object>();
						// HashMap<String, Object> updateParams = new HashMap<String, Object>();

						int diffCnt = 0;

						XSSFRow row = sheet.getRow(rowIndex);
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells();

							if (cells == 11) {
								cells = 28;
							} else {
								cells = cells;
							}

							String value = "";
							currentRow = rowIndex;

							// Reading Cells
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								XSSFCell cell = row.getCell(columnIndex);
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) {
									case XSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(XSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]").length != 1) {
											if (value.split("[.]")[1].equals("0")) {
												value = value.split("[.]")[0];
											}
										}
										break;
									case XSSFCell.CELL_TYPE_STRING:
										value = cell.getStringCellValue() + "";
										break;
									case XSSFCell.CELL_TYPE_BLANK:
										value = "";
										break;
									case XSSFCell.CELL_TYPE_ERROR:
										value = cell.getErrorCellValue() + "";
										break;
									}

									switch (columnIndex) {
									case 1:
										updateOrderList.setHawbNo(value);
										break;
									case 2:
										updateOrderList.setOrderNo(value);
										break;
									case 3:
										updateOrderList.setOrderDate(value);
										break;
									case 4:
										updateOrderList.setShipperName(value);
										break;
									case 5:
										updateOrderList.setShipperAddr(value);
										break;
									case 6:
										updateOrderList.setCneeName(value);
										break;
									case 7:
										updateOrderList.setCneeZip(value);
										break;
									case 8:
										updateOrderList.setCneeAddr(value);
										break;
									case 11:
										updateOrderList.setCneeTel(value);
										break;
									case 12:
										updateOrderList.setCneeHp(value);
										break;
									case 15:
										updateOrderList.setBoxCnt(value);
										break;
									case 17:
										updateItemList.setBrand(value);
										break;
									case 18:
										updateItemList.setItemDetail(value);
										break;
									case 19:
										updateItemList.setItemCnt(value);
										break;
									case 20:
										updateItemList.setUnitValue(value);
										break;
									case 21:
										updateItemList.setCusItemCode(value);
										break;
									case 22:
										updateItemList.setChgCurrency(value);
										break;
									case 23:
										updateItemList.setMakeCntry(value);
										break;
									case 24:
										updateItemList.setMakeCom(value);
										break;
									case 25:
										updateItemList.setHsCode(value);
										break;
									case 26:
										updateItemList.setNno(value);
										updateOrderList.setNno(value);
										break;
									case 27:
										updateItemList.setSubNo(value);
										break;
									}
								}
							}

							params.put("nno", updateItemList.getNno());
							params.put("subNo", updateItemList.getSubNo());

							orderList = mapper.selectOrderInfoByNNO(params);
							itemList = mapper.selectOrderItemByNNO(params);

							orderList.dncryptData();

							if (!updateOrderList.getHawbNo().equals("")) {
								if (!orderList.getOrderNo().equals(updateOrderList.getOrderNo())) {
									diffCnt++;
								} else {
									updateOrderList.setOrderNo(orderList.getOrderNo());
								}

								if (!orderList.getOrderDate().equals(updateOrderList.getOrderDate())) {
									diffCnt++;
								} else {
									updateOrderList.setOrderDate(orderList.getOrderDate());
								}

								if (!orderList.getShipperName().equals(updateOrderList.getShipperName())) {
									diffCnt++;
								} else {
									updateOrderList.setShipperName(orderList.getShipperName());
								}

								if (!orderList.getShipperAddr().equals(updateOrderList.getShipperAddr())) {
									diffCnt++;
								} else {
									updateOrderList.setShipperAddr(orderList.getShipperAddr());
								}

								if (!orderList.getCneeName().equals(updateOrderList.getCneeName())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeName(orderList.getCneeName());
								}

								if (!orderList.getCneeAddr().equals(updateOrderList.getCneeAddr())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeAddr(orderList.getCneeAddr());
								}

								if (!orderList.getCneeZip().equals(updateOrderList.getCneeZip())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeZip(orderList.getCneeZip());
								}

								if (!orderList.getCneeTel().equals(updateOrderList.getCneeTel())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeTel(orderList.getCneeTel());
								}

								if (!orderList.getCneeHp().equals(updateOrderList.getCneeHp())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeHp(orderList.getCneeHp());
								}

								if (!orderList.getBoxCnt().equals(updateOrderList.getBoxCnt())) {
									diffCnt++;
								} else {
									updateOrderList.setBoxCnt(orderList.getBoxCnt());
								}

							}

							updateOrderList.setWUserId((String) request.getSession().getAttribute("USER_ID"));
							updateOrderList.setWUserIp((String) request.getRemoteAddr());

							updateOrderList.encryptData();

							if (diffCnt > 0) {
								mapper.insertNewOrderInfo(updateOrderList);
							}

							diffCnt = 0;

							if (!itemList.getBrand().equals(updateItemList.getBrand())) {
								diffCnt++;
							} else {
								updateItemList.setBrand(itemList.getBrand());
							}

							if (!itemList.getItemDetail().equals(updateItemList.getItemDetail())) {
								diffCnt++;
							} else {
								updateItemList.setItemDetail(itemList.getItemDetail());
							}

							if (!itemList.getItemCnt().equals(updateItemList.getItemCnt())) {
								diffCnt++;
							} else {
								updateItemList.setItemCnt(itemList.getItemCnt());
							}

							if (!itemList.getUnitValue().equals(updateItemList.getUnitValue())) {
								diffCnt++;
							} else {
								updateItemList.setUnitValue(itemList.getUnitValue());
							}

							if (!itemList.getCusItemCode().equals(updateItemList.getCusItemCode())) {
								diffCnt++;
							} else {
								updateItemList.setCusItemCode(itemList.getCusItemCode());
							}

							if (!itemList.getChgCurrency().equals(updateItemList.getChgCurrency())) {
								diffCnt++;
							} else {
								updateItemList.setChgCurrency(itemList.getChgCurrency());
							}

							if (!itemList.getMakeCntry().equals(updateItemList.getMakeCntry())) {
								diffCnt++;
							} else {
								updateItemList.setMakeCntry(itemList.getMakeCntry());
							}

							if (!itemList.getMakeCom().equals(updateItemList.getMakeCom())) {
								diffCnt++;
							} else {
								updateItemList.setMakeCom(itemList.getMakeCom());
							}

							if (!itemList.getHsCode().equals(updateItemList.getHsCode())) {
								diffCnt++;
							} else {
								updateItemList.setHsCode(itemList.getHsCode());
							}

							updateItemList.setWUserId((String) request.getSession().getAttribute("USER_ID"));
							updateItemList.setWUserIp((String) request.getRemoteAddr());

							if (diffCnt > 0) {
								mapper.insertNewOrderItemInfo(updateItemList);
							}
						}
					} // End Rows
				} else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					HSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					System.out.println("엑셀 로우 수");
					System.out.println(rows - 1);
					// Reading Rows
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {

						System.out.println("Reading ...");
						System.out.println("now index of Row : " + rowIndex);

						UpdateOrderListVO updateOrderList = new UpdateOrderListVO();
						UpdateOrderItemVO updateItemList = new UpdateOrderItemVO();

						UserOrderListVO orderList = new UserOrderListVO();
						UserOrderItemVO itemList = new UserOrderItemVO();
						HashMap<String, Object> params = new HashMap<String, Object>();

						int diffCnt = 0;

						HSSFRow row = sheet.getRow(rowIndex);
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							currentRow = rowIndex;

							// Reading Cells
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								HSSFCell cell = row.getCell(columnIndex);
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) {
									case HSSFCell.CELL_TYPE_NUMERIC:
										cell.setCellType(XSSFCell.CELL_TYPE_STRING);
										value = cell.getStringCellValue();
										if (value.split("[.]").length != 1) {
											if (value.split("[.]")[1].equals("0")) {
												value = value.split("[.]")[0];
											}
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

									switch (columnIndex) {
									case 1:
										updateOrderList.setHawbNo(value);
										break;
									case 2:
										updateOrderList.setOrderNo(value);
										break;
									case 3:
										updateOrderList.setOrderDate(value);
										break;
									case 4:
										updateOrderList.setShipperName(value);
										break;
									case 5:
										updateOrderList.setShipperAddr(value);
										break;
									case 6:
										updateOrderList.setCneeName(value);
										break;
									case 7:
										updateOrderList.setCneeZip(value);
										break;
									case 8:
										updateOrderList.setCneeAddr(value);
										break;
									case 11:
										updateOrderList.setCneeTel(value);
										break;
									case 12:
										updateOrderList.setCneeHp(value);
										break;
									case 15:
										updateOrderList.setBoxCnt(value);
										break;
									case 17:
										updateItemList.setBrand(value);
										break;
									case 18:
										updateItemList.setItemDetail(value);
										break;
									case 19:
										updateItemList.setItemCnt(value);
										break;
									case 20:
										updateItemList.setUnitValue(value);
										break;
									case 21:
										updateItemList.setCusItemCode(value);
										break;
									case 22:
										updateItemList.setChgCurrency(value);
										break;
									case 23:
										updateItemList.setMakeCntry(value);
										break;
									case 24:
										updateItemList.setMakeCom(value);
										break;
									case 25:
										updateItemList.setHsCode(value);
										break;
									case 26:
										updateItemList.setNno(value);
										updateOrderList.setNno(value);
										break;
									case 27:
										updateItemList.setSubNo(value);
										break;
									}
								}
							}

							params.put("nno", updateItemList.getNno());
							params.put("subNo", updateItemList.getSubNo());

							orderList = mapper.selectOrderInfoByNNO(params);
							itemList = mapper.selectOrderItemByNNO(params);

							orderList.dncryptData();

							if (!updateOrderList.getHawbNo().equals("")) {
								if (!orderList.getOrderNo().equals(updateOrderList.getOrderNo())) {
									diffCnt++;
								} else {
									updateOrderList.setOrderNo(orderList.getOrderNo());
								}

								if (!orderList.getOrderDate().equals(updateOrderList.getOrderDate())) {
									diffCnt++;
								} else {
									updateOrderList.setOrderDate(orderList.getOrderDate());
								}

								if (!orderList.getShipperName().equals(updateOrderList.getShipperName())) {
									diffCnt++;
								} else {
									updateOrderList.setShipperName(orderList.getShipperName());
								}

								if (!orderList.getShipperAddr().equals(updateOrderList.getShipperAddr())) {
									diffCnt++;
								} else {
									updateOrderList.setShipperAddr(orderList.getShipperAddr());
								}

								if (!orderList.getCneeName().equals(updateOrderList.getCneeName())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeName(orderList.getCneeName());
								}

								if (!orderList.getCneeAddr().equals(updateOrderList.getCneeAddr())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeAddr(orderList.getCneeAddr());
								}

								if (!orderList.getCneeZip().equals(updateOrderList.getCneeZip())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeZip(orderList.getCneeZip());
								}

								if (!orderList.getCneeTel().equals(updateOrderList.getCneeTel())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeTel(orderList.getCneeTel());
								}

								if (!orderList.getCneeHp().equals(updateOrderList.getCneeHp())) {
									diffCnt++;
								} else {
									updateOrderList.setCneeHp(orderList.getCneeHp());
								}

								if (!orderList.getBoxCnt().equals(updateOrderList.getBoxCnt())) {
									diffCnt++;
								} else {
									updateOrderList.setBoxCnt(orderList.getBoxCnt());
								}

							}

							updateOrderList.setWUserId((String) request.getSession().getAttribute("USER_ID"));
							updateOrderList.setWUserIp((String) request.getRemoteAddr());

							updateOrderList.encryptData();

							if (diffCnt > 0) {
								mapper.insertNewOrderInfo(updateOrderList);
							}

							diffCnt = 0;

							if (!itemList.getBrand().equals(updateItemList.getBrand())) {
								diffCnt++;
							} else {
								updateItemList.setBrand(itemList.getBrand());
							}

							if (!itemList.getItemDetail().equals(updateItemList.getItemDetail())) {
								diffCnt++;
							} else {
								updateItemList.setItemDetail(itemList.getItemDetail());
							}

							if (!itemList.getItemCnt().equals(updateItemList.getItemCnt())) {
								diffCnt++;
							} else {
								updateItemList.setItemCnt(itemList.getItemCnt());
							}

							if (!itemList.getUnitValue().equals(updateItemList.getUnitValue())) {
								diffCnt++;
							} else {
								updateItemList.setUnitValue(itemList.getUnitValue());
							}

							if (!itemList.getCusItemCode().equals(updateItemList.getCusItemCode())) {
								diffCnt++;
							} else {
								updateItemList.setCusItemCode(itemList.getCusItemCode());
							}

							if (!itemList.getChgCurrency().equals(updateItemList.getChgCurrency())) {
								diffCnt++;
							} else {
								updateItemList.setChgCurrency(itemList.getChgCurrency());
							}

							if (!itemList.getMakeCntry().equals(updateItemList.getMakeCntry())) {
								diffCnt++;
							} else {
								updateItemList.setMakeCntry(itemList.getMakeCntry());
							}

							if (!itemList.getMakeCom().equals(updateItemList.getMakeCom())) {
								diffCnt++;
							} else {
								updateItemList.setMakeCom(itemList.getMakeCom());
							}

							if (!itemList.getHsCode().equals(updateItemList.getHsCode())) {
								diffCnt++;
							} else {
								updateItemList.setHsCode(itemList.getHsCode());
							}

							updateItemList.setWUserId((String) request.getSession().getAttribute("USER_ID"));
							updateItemList.setWUserIp((String) request.getRemoteAddr());

							if (diffCnt > 0) {
								mapper.insertNewOrderItemInfo(updateItemList);
							}
						}
					} // End Rows
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
			if (delFile.exists()) {
				delFile.delete();
			}
		}

		return result;
	}

	@Override
	public void selectMawbExcelDown(String mawbNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			ArrayList<ManifestVO> orderInfo = new ArrayList<ManifestVO>();
			ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mawbNo", mawbNo);

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/rls/mawbList/sampleExcel/manifest_excel.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Row row = null;
			Cell cell = null;

			Row row2 = null;
			Cell cell2 = null;

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			wb.setSheetName(wb.getSheetIndex(sheet), mawbNo);

			orderInfo = mapper.selectOrderInfoMani(params);
			float wta = 0;
			float wtc = 0;

			int rowNo = 1;

			ArrayList<String> nnoList = new ArrayList<String>();

			Font font = wb.createFont();
			font.setFontName("NanumGothic");
			font.setFontHeightInPoints((short) 9);

			CellStyle style = wb.createCellStyle();
			style.setWrapText(false);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setFont(font);

			CellStyle styleR = wb.createCellStyle();
			styleR.setWrapText(false);
			styleR.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleR.setFont(font);
			styleR.setAlignment(CellStyle.ALIGN_RIGHT);

			CellStyle styleC = wb.createCellStyle();
			styleC.setWrapText(false);
			styleC.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleC.setFont(font);
			styleC.setAlignment(CellStyle.ALIGN_CENTER);

			String match = "[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]";
			int cnt = 0;

			try {

				for (int i = 0; i < orderInfo.size(); i++) {
					row = sheet.createRow(rowNo);
					orderInfo.get(i).dncryptData();
					
					int cellCnt = 0;

					// cnt = mapper.selectOrderListNewInfo(orderInfo.get(i).getNno());

					//int idx = mawbNo.indexOf("-");

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getMawbNo());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getHawbNo());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getOrgNation());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getDstnNation());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getBoxCnt());
					cell.setCellStyle(styleR);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getWta());
					cell.setCellStyle(styleR);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getDepDate());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getShipperName());
					cell.setCellStyle(style);
					cellCnt++;

					String shipperAddress = orderInfo.get(i).getShipperAddr();
					if (!orderInfo.get(i).getShipperAddrDetail().equals("")) {
						shipperAddress = shipperAddress + " " + orderInfo.get(i).getShipperAddrDetail(); 
					}
					
					cell = row.createCell(cellCnt);
					cell.setCellValue(shipperAddress);
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getShipperCity());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getShipperState());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getShipperZip());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getCneeName());
					cell.setCellStyle(style);
					cellCnt++;
					
					String cneeAddress = orderInfo.get(i).getCneeAddr();
					if (!orderInfo.get(i).getCneeAddrDetail().equals("")) {
						cneeAddress = cneeAddress + " " + orderInfo.get(i).getCneeAddrDetail(); 
					}

					cell = row.createCell(cellCnt);
					cell.setCellValue(cneeAddress);
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getCneeCity());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getCneeState());
					cell.setCellStyle(styleR);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getCneeZip());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getItemDetail());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getItemCnt());
					cell.setCellStyle(styleR);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getUnitValue());
					cell.setCellStyle(styleR);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getChgCurrency());
					cell.setCellStyle(style);
					cellCnt++;

					cell = row.createCell(cellCnt);
					cell.setCellValue(orderInfo.get(i).getHsCode());
					cell.setCellStyle(style);
					cellCnt++;

					rowNo++;
				}

				response.setContentType("ms-vnd/excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + mawbNo + "_manifest.xlsx");

				OutputStream out = new BufferedOutputStream(response.getOutputStream());
				wb.write(out);
				out.flush();
				out.close();

			} catch (Exception e) {
				logger.error("Exception", e);
			}

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public ArrayList<UserOrderListVO> selectOrderListT86(HashMap<String, Object> params) throws Exception {
		return mapper.selectOrderListT86(params);
	}

	@Override
	public ArrayList<HawbListVO> selectType86HawbList(HashMap<String, Object> params) throws Exception {
		return mapper.selectType86HawbList(params);
	}

	@Override
	public ArrayList<HashMap<String, String>> selectBagNoList(String mawbNo) throws Exception {
		return mapper.selectBagNoList(mawbNo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectHawbListByBagNo(HashMap<String, Object> params) throws Exception {
		return mapper.selectHawbListByBagNo(params);
	}

	@Override
	public String[] selectNnoListByMawbNo(String mawbNo) throws Exception {
		return mapper.selectNnoListByMawbNo(mawbNo);
	}

	@Override
	public ArrayList<UserOrderListVO> selectOrderInfo(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectOrderInfoHis(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectChangeOrderList(String mawbNo) throws Exception {
		return mapper.selectChangeOrderList(mawbNo);
	}

	@Override
	public ArrayList<UserOrderListVO> selectChangeOrderInfo(String nno) throws Exception {
		return mapper.selectChangeOrderInfo(nno);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectChangeItemInfo(String nno) throws Exception {
		return mapper.selectChangeItemInfo(nno);
	}

	@Override
	public UserOrderListVO selectFirstOrderInfo(String nno) throws Exception {
		return mapper.selectFirstOrderInfo(nno);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectFirstOrderItemInfo(HashMap<String, Object> params)
			throws Exception {
		return mapper.selectFirstOrderItemInfo(params);
	}

	@Override
	public ArrayList<String> selectExpLicenceFB() throws Exception {
		return mapper.selectExpLicenceFB();
	}

	@Override
	public int selectUserMallCnt(FastboxInfoVO fastboxInfo) throws Exception {
		return mapper.selectUserMallCnt(fastboxInfo);
	}

	@Override
	public void updateUserMallInfo(FastboxInfoVO fastboxInfo) throws Exception {
		mapper.updateUserMallInfo(fastboxInfo);
	}

	@Override
	public void insertUserMallInfo(FastboxInfoVO fastboxInfo) throws Exception {
		mapper.insertUserMallInfo(fastboxInfo);
	}

	@Override
	public FastboxInfoVO selectUserMallInfo(String userId) throws Exception {
		return mapper.selectUserMallInfo(userId);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectOrderListForErr(HashMap<String, Object> params) throws Exception {
		return mapper.selectOrderListForErr(params);
	}

	@Override
	public ArrayList<String> selectErrorMsgByNNO(String nno) throws Exception {
		return mapper.selectErrorMsgByNNO(nno);
	}

	@Override
	public void cusItemCodeExcelDown(HttpServletRequest request, HttpServletResponse response,
			ArrayList<ViewYslItemCode> viewList) throws Exception {
		try {
			String savePath = realFilePath + "excel/expLicence/";
			LocalDate currentDate = LocalDate.now();
			String filename = "JKJ ITEM MASTER_ACI(" + currentDate + ").xlsx";
			File xlsFile = new File(savePath + filename);
//			apiService.selectUserKey("solugate");
			// Excel 생성
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
			// ROW-CELL 선언 END

			// HEADER 생성 START 작업중입니단
			row = sheet1.createRow(rowCnt);

			cell = row.createCell(0);
			cell.setCellValue("ITEM CODE");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(1);
			cell.setCellValue("SAGAWA CODE");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(2);
			cell.setCellValue("ITEM NAME(Eng)");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(3);
			cell.setCellValue("ITEM NAME(Jpy)");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(4);
			cell.setCellValue("ORIGIN");
			cell.setCellStyle(cellStyleHeader);

			cell = row.createCell(5);
			cell.setCellValue("images");
			cell.setCellStyle(cellStyleHeader);

			rowCnt++;
			// HEADER 생성 END

			for (int i = 0; i < viewList.size(); i++) {
				row = sheet1.createRow(rowCnt);
				cell = row.createCell(0);
				cell.setCellValue(viewList.get(i).getItemCode());

				cell = row.createCell(1);
				cell.setCellValue(viewList.get(i).getItemCode());

				cell = row.createCell(2);
				cell.setCellValue(viewList.get(i).getItemNameEng());

				cell = row.createCell(3);
				cell.setCellValue(viewList.get(i).getItemNameJp());

				cell = row.createCell(4);
				cell.setCellValue(viewList.get(i).getOrigin());

				cell = row.createCell(5);
				cell.setCellValue(viewList.get(i).getImgurl());

				rowCnt++;
			}

			try {

				FileOutputStream fileOut = new FileOutputStream(xlsFile);
				xlsxWb.write(fileOut);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			xlsFile.delete();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateMawbHawb(HashMap<String, Object> params) throws Exception {
		mapper.updateMawbHawb(params);
	}

	@Override
	public int selectRecoveryListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectRecoveryListCnt(params);
	}

	@Override
	public void insertWhoutNoticeIn(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", "N");
		parameters.put("category", request.getParameter("category"));
		parameters.put("title", request.getParameter("title"));
		parameters.put("content", request.getParameter("content"));
		parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateNow = formatter.format(now);

		mapper.insertWhNoticeIn(parameters);
		int idx = (int) parameters.get("idx");
		parameters.put("idx", idx);
		mapper.updateWhNoticeIn(parameters);
		int index = 0;
		String filePath = realFilePath + "/image/board/";
		String fileName = "";
		String exten = "";

		List<MultipartFile> files = multi.getFiles("addFiles");

		int fileIndex = 0;

		if (files.size() > 0 && !files.get(0).getOriginalFilename().equals("")) {
			for (MultipartFile file : files) {
				fileIndex++;
				String uploadFile = file.getOriginalFilename();
				index = uploadFile.lastIndexOf(".");
				exten = uploadFile.substring(index + 1);
				fileName = uploadFile.substring(0, index) + "." + exten;

				file.transferTo(new File(filePath + fileName));
				File upFile = new File(filePath + fileName);
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(),
						comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult result = new PutObjectResult();
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				if (amazonS3 != null) {
					PutObjectRequest objReq = new PutObjectRequest(
							comnS3Info.getBucketName() + "/outbound/board/" + year + "/" + dateNow, fileName, upFile);
					objReq.setCannedAcl(CannedAccessControlList.PublicRead);
					result = amazonS3.putObject(objReq);
					String amazonPath = "http://img.mtuai.com/outbound/board/" + year + "/" + dateNow + "/" + fileName;
					parameters.put("fileDir", amazonPath);
					mapper.insertWhoutFile(parameters);
				}
				amazonS3 = null;
				upFile.delete();
			}
		}

	}

	@Override
	public void insertWhoutOrderIn(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateNow = formatter.format(now);

		int index = 0;
		String filePath = realFilePath + "/image/board/";
		String fileName = "";
		String exten = "";
		String str = dateNow;
		for (int i = 0; i < 3; i++) {
			char ch = (char) ((Math.random() * 26) + 65);
			str += ch;
		}
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("boardId", str);
		parameters.put("status", "N");
		parameters.put("category", request.getParameter("category"));
		parameters.put("title", request.getParameter("title"));
		parameters.put("content", request.getParameter("content"));
		parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());

		mapper.insertWhoutOrderIn(parameters);

		List<MultipartFile> files = multi.getFiles("addFiles");

		int fileIndex = 0;
		for (MultipartFile file : files) {
			fileIndex++;
			String uploadFile = file.getOriginalFilename();
			index = uploadFile.lastIndexOf(".");
			exten = uploadFile.substring(index + 1);
			fileName = uploadFile.substring(0, index) + "." + exten;

			file.transferTo(new File(filePath + fileName));
			File upFile = new File(filePath + fileName);
			AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(),
					comnS3Info.getSecretKey());
			amazonS3 = new AmazonS3Client(awsCredentials);
			PutObjectResult result = new PutObjectResult();
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			if (amazonS3 != null) {
				PutObjectRequest objReq = new PutObjectRequest(
						comnS3Info.getBucketName() + "/outbound/board/" + year + "/" + dateNow, fileName, upFile);
				objReq.setCannedAcl(CannedAccessControlList.PublicRead);
				result = amazonS3.putObject(objReq);
				String amazonPath = "http://img.mtuai.com/outbound/board/" + year + "/" + dateNow + "/" + fileName;
				parameters.put("fileDiv", "M");
				parameters.put("fileDir", amazonPath);
				mapper.insertWhoutFile(parameters);
			}
			amazonS3 = null;
			upFile.delete();
		}
	}

	@Override
	public HashMap<String, Object> selectWhoutOrder(HashMap<String, Object> paramters) throws Exception {
		return mapper.selectWhoutOrder(paramters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWhoutFiles(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectWhoutFiles(parameters);
	}

	@Override
	public void deleteWhoutFile(HashMap<String, Object> parameters) throws Exception {
		mapper.deleteWhoutFile(parameters);
	}

	@Override
	public int selectNoticeListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectNoticeListCnt(params);
	}

	@Override
	public HashMap<String, Object> selectNoticeInfo(int idx) throws Exception {
		return mapper.selectNoticeInfo(idx);
	}

	@Override
	public void insertWhNoticeReply(HashMap<String, Object> parameters) throws Exception {
		mapper.insertReplyNotice(parameters);
	}

	@Override
	public int selectWhNoticeListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectWhNoticeListCnt(params);
	}

	@Override
	public ArrayList<WhNoticeVO> selectWhNoticeList(HashMap<String, Object> params) throws Exception {
		return mapper.selectWhNoticeList(params);
	}

	@Override
	public void insertWhNoticeInfo(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", "N");
		parameters.put("category", request.getParameter("category"));
		parameters.put("title", request.getParameter("title"));
		parameters.put("content", request.getParameter("content"));
		parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateNow = formatter.format(now);

		mapper.insertWhNoticeInfo(parameters);
		int idx = (int) parameters.get("idx");
		parameters.put("idx", idx);
		mapper.updateWhNoticeGIdx(parameters);
		int index = 0;
		String filePath = realFilePath + "/image/board/";
		String fileName = "";
		String exten = "";

		List<MultipartFile> files = multi.getFiles("addFiles");

		int fileIndex = 0;

		if (files.size() > 0 && !files.get(0).getOriginalFilename().equals("")) {
			for (MultipartFile file : files) {
				fileIndex++;
				String uploadFile = file.getOriginalFilename();
				index = uploadFile.lastIndexOf(".");
				exten = uploadFile.substring(index + 1);
				fileName = uploadFile.substring(0, index) + "." + exten;

				file.transferTo(new File(filePath + fileName));
				File upFile = new File(filePath + fileName);
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(),
						comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult result = new PutObjectResult();
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				if (amazonS3 != null) {
					PutObjectRequest objReq = new PutObjectRequest(
							comnS3Info.getBucketName() + "/outbound/board/" + year + "/" + dateNow, fileName, upFile);
					objReq.setCannedAcl(CannedAccessControlList.PublicRead);
					result = amazonS3.putObject(objReq);
					String amazonPath = "http://img.mtuai.com/outbound/board/" + year + "/" + dateNow + "/" + fileName;
					parameters.put("fileDir", amazonPath);
					mapper.insertWhNoticeFile(parameters);
				}
				amazonS3 = null;
				upFile.delete();
			}
		}
	}

	@Override
	public WhNoticeVO selectWhNoticeDetail(HashMap<String, Object> params) throws Exception {
		return mapper.selectWhNoticeDetail(params);
	}

	@Override
	public int selectWhNoticeFileYn(HashMap<String, Object> params) throws Exception {
		return mapper.selectWhNoticeFileYn(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWhNoticeFileList(HashMap<String, Object> params) throws Exception {
		return mapper.selectWhNoticeFileList(params);
	}

	@Override
	public void noticeFileDel(HashMap<String, Object> params) throws Exception {
		mapper.noticeFileDel(params);
	}

	@Override
	public void updateWhNoticeInfo(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", "Y");
		parameters.put("category", request.getParameter("category"));
		parameters.put("title", request.getParameter("title"));
		parameters.put("content", request.getParameter("content"));
		parameters.put("idx", Integer.parseInt(request.getParameter("idx")));

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateNow = formatter.format(now);

		mapper.updateWhNoticeInfo(parameters);
		int index = 0;
		String filePath = realFilePath + "/image/board/";
		String fileName = "";
		String exten = "";

		List<MultipartFile> files = multi.getFiles("addFiles");

		int fileIndex = 0;

		if (files.size() > 0 && !files.get(0).getOriginalFilename().equals("")) {
			for (MultipartFile file : files) {
				fileIndex++;
				String uploadFile = file.getOriginalFilename();
				index = uploadFile.lastIndexOf(".");
				exten = uploadFile.substring(index + 1);
				fileName = uploadFile.substring(0, index) + "." + exten;

				file.transferTo(new File(filePath + fileName));
				File upFile = new File(filePath + fileName);
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(),
						comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult result = new PutObjectResult();
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				if (amazonS3 != null) {
					PutObjectRequest objReq = new PutObjectRequest(
							comnS3Info.getBucketName() + "/outbound/board/" + year + "/" + dateNow, fileName, upFile);
					objReq.setCannedAcl(CannedAccessControlList.PublicRead);
					result = amazonS3.putObject(objReq);
					String amazonPath = "http://img.mtuai.com/outbound/board/" + year + "/" + dateNow + "/" + fileName;
					parameters.put("fileDir", amazonPath);
					mapper.insertWhNoticeFile(parameters);
				}
				amazonS3 = null;
				upFile.delete();
			}
		}
	}

	@Override
	public void insertWhNoticeReplyIn(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", "N");
		parameters.put("category", request.getParameter("category"));
		parameters.put("title", request.getParameter("title"));
		parameters.put("content", request.getParameter("content"));
		parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameters.put("groupIdx", Integer.parseInt(request.getParameter("groupIdx")));
		// parameters.put("idx", Integer.parseInt(request.getParameter("idx")));
		parameters.put("step", Integer.parseInt(request.getParameter("step")));
		parameters.put("indent", Integer.parseInt(request.getParameter("indent")));

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateNow = formatter.format(now);

		mapper.insertWhNoticeReplyInfo(parameters);
		int idx = (int) parameters.get("nowIdx");
		parameters.put("idx", idx);
		int index = 0;
		String filePath = realFilePath + "/image/board/";
		String fileName = "";
		String exten = "";

		List<MultipartFile> files = multi.getFiles("addFiles");

		int fileIndex = 0;

		if (files.size() > 0 && !files.get(0).getOriginalFilename().equals("")) {
			for (MultipartFile file : files) {
				fileIndex++;
				String uploadFile = file.getOriginalFilename();
				index = uploadFile.lastIndexOf(".");
				exten = uploadFile.substring(index + 1);
				fileName = uploadFile.substring(0, index) + "." + exten;

				file.transferTo(new File(filePath + fileName));
				File upFile = new File(filePath + fileName);
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(),
						comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult result = new PutObjectResult();
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				if (amazonS3 != null) {
					PutObjectRequest objReq = new PutObjectRequest(
							comnS3Info.getBucketName() + "/outbound/board/" + year + "/" + dateNow, fileName, upFile);
					objReq.setCannedAcl(CannedAccessControlList.PublicRead);
					result = amazonS3.putObject(objReq);
					String amazonPath = "http://img.mtuai.com/outbound/board/" + year + "/" + dateNow + "/" + fileName;
					parameters.put("fileDir", amazonPath);
					mapper.insertWhNoticeFile(parameters);
				}
				amazonS3 = null;
				upFile.delete();
			}
		}
	}

	@Override
	public HashMap<String, Object> execNoticeDel(HashMap<String, Object> params) throws Exception {
		return mapper.execNoticeDel(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWhNoticeFiles(int idx) throws Exception {
		return mapper.selectWhNoticeFiles(idx);
	}

	@Override
	public String fastboxSendDelivery(String[] hawbList, String mawbNo) throws Exception {
		HashMap<String, String> parameters = new HashMap<String, String>();
		ProcedureVO rst = new ProcedureVO();
		String result = "S";
		for (int idx = 0; idx < hawbList.length; idx++) {
			rst = fastboxApi.requestFastboxDelivery(hawbList[idx], mawbNo);
			if (!rst.getRstStatus().equals("SUCCESS")) {
				result = "F";
				break;
			}
			result = "S";
		}

		return result;
	}

	@Override
	public void printCommercialPdf(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
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
						(pageStandard.getWidth()) - 3 * perMM, (pageStandard.getHeight()) - 120 * perMM);
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
				drawText(commercialInfo.getHawbNo() + " / " + commercialInfo.getMatchNo(), ARIAL, fontSize, 4 * perMM,
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
					drawText(commercialInfo.getOrderDate(), ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				} else {
					drawText(commercialInfo.getDepDate(), ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				}

				// No. & date of L/C
				drawTextY = (pageStandard.getHeight()) - 58 * perMM;
				drawText("⑨ No. & Date of L/C", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);

				// L/C issuing bank
				drawTextY = (pageStandard.getHeight()) - 71 * perMM;
				drawText("⑩ L/C issuing bank", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);

				// Remarks:
				drawTextY = (pageStandard.getHeight()) - 84 * perMM;
				drawText("⑪ Remarks", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				if (!commercialInfo.getBuySite().equals("")) {
					drawTextY = drawTextLineLength("Buy Site URL:" + commercialInfo.getBuySite(), ARIAL, fontSize,
							106 * perMM, drawTextY, contentStream, 23000);
					drawTextY = drawTextY - 7 * perMM;
				}
				/*
				 * if (!commercialInfo.getPayment().equals("")) { drawText("Payment : " +
				 * commercialInfo.getPayment(), ARIAL, fontSize, 106 * perMM, drawTextY,
				 * contentStream); drawTextY = drawTextY - 7 * perMM; }
				 */
				// Terms
				drawTextY = (pageStandard.getHeight()) - 124 * perMM;
				drawText("⑫ Terms", ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 7 * perMM;
				drawText(commercialInfo.getPayment(), ARIAL, fontSize, 106 * perMM, drawTextY, contentStream);

				// Marks and Number of PKGS Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑬ Marks and", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Number of PKGS ", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 20 * perMM;
				drawText(String.valueOf(commercialInfo.getBoxCnt()), ARIAL, 18, 16 * perMM, drawTextY, contentStream);

				// Description of goods Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				titleWidth = NanumGothic.getStringWidth("⑬ Description of goods") / 1000 * fontSize;
				drawText("⑭ Description of goods ", ARIAL, fontSize, 34 * perMM + (93 * perMM - titleWidth) / 2,
						drawTextY, contentStream);
				// Quantity/Unit Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑮ Quantity/", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Unit ", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
				// Unit-price Title
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;

				drawText("⑯ Unit price", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);

				// Amount(단위)
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑰ Amount", ARIAL, fontSize, 184 * perMM, drawTextY, contentStream);
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
						drawText("⑯ Amount", ARIAL, fontSize, 187 * perMM, drawTextY, contentStream);

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
				drawText("INVOICE VALUES :", ARIAL, fontSize, 88 * perMM, 40 * perMM, contentStream);
				drawText(String.valueOf(commercialInfo.getTotalItemCnt()), ARIAL, fontSize, 131 * perMM, 40 * perMM,
						contentStream);
				drawText(String.valueOf(commercialInfo.getTotalItemValue()) + " " + chgCurreny, ARIAL, fontSize,
						187 * perMM, 40 * perMM, contentStream);
				// drawText(chgCurreny, ARIAL, fontSize, 170 * perMM, 40 * perMM,
				// contentStream);
				// drawText(Double.toString(totalAmount), ARIAL, fontSize, 183 * perMM, 40 *
				// perMM, contentStream);

				fontSize = 24;
				drawTextY = drawTextLineLengthRight(commercialInfo.getComEName(), PDType1Font.HELVETICA_BOLD_OBLIQUE,
						fontSize, 60 * perMM, 23 * perMM, contentStream, 20000, pageStandard.getWidth());

				contentStream.close();
				currenctPage++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	private void drawText(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream) throws Exception {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(left, bottom);
		contentStream.showText(text);
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

	@Override
	public void printCommercialExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
		try {
			String filePath = realFilePath + "excel/downloadData/com_invoice_sample.xlsx";
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
				cell.setCellValue(commercialInfo.getHawbNo() + " / " + commercialInfo.getMatchNo());

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
					// String orderDate = mapper.selectUploadDate(commercialInfo.getHawbNo());
					cell.setCellValue(commercialInfo.getOrderDate());
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
				// cellStyleSetting(row, cell, styleCellLR, 4, 7);
				cell = row.getCell(4);

				String remarkString = "";
				if (!commercialInfo.getBuySite().equals("")) {
					remarkString += "Buy Site URL : " + commercialInfo.getBuySite();
					remarkString += "\n";
				}
				cell.setCellValue(remarkString);

				row = sheet.getRow(17);
				cell = row.getCell(4);
				cell.setCellValue(commercialInfo.getPayment());

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
						// cell.setCellValue(commercialItem.get(j).getUnitValue() + " " +
						// commercialItem.get(j).getChgCurrency());
						cell.setCellValue(
								commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						// cell.setCellValue(amount + " " + commercialItem.get(j).getChgCurrency());
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
						// cell.setCellValue(commercialItem.get(j).getUnitValue() + " " +
						// commercialItem.get(j).getChgCurrency());
						cell.setCellValue(
								commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
						// cell.setCellValue(amount + " " + commercialItem.get(j).getChgCurrency());
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
					row = sheet.createRow(rowCnt + 1);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt + 1, rowCnt + 1, 0, 4));
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cell = row.getCell(0);
					cell.setCellValue("INVOICE VALUES : ");
					row.setHeight((short) 500);
					cell = row.getCell(5);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemCnt()));
					cell = row.getCell(7);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemValue()) + "  "
							+ commercialItem.get(0).getChgCurrency());
					rowCnt++;
				} else {
					rowCnt = 38;
					row = sheet.createRow(rowCnt);
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					cell = row.getCell(0);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 4));
					cell.setCellValue("INVOICE VALUES : ");
					row.setHeight((short) 500);
					cell = row.getCell(5);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemCnt()));
					cell = row.getCell(7);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemValue()) + "  "
							+ commercialItem.get(0).getChgCurrency());
				}

				Font boxFont = wb.createFont();
				boxFont.setFontName("나눔고딕");
				boxFont.setFontHeight((short) 800);

				CellStyle centerStyle = wb.createCellStyle();
				centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
				centerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				centerStyle.setWrapText(true);
				centerStyle.setFont(boxFont);

				row = sheet.getRow(25);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getBoxCnt());
				cell.setCellStyle(centerStyle);
				if (commercialItem.size() > 13) {
					sheet.addMergedRegion(new CellRangeAddress(25, 25 + commercialItem.size() - 1, 0, 0));
				} else {
					sheet.addMergedRegion(new CellRangeAddress(25, 37, 0, 0));
				}

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
			response.setHeader("Content-Disposition", "attachment;filename=commercial_invoice.xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cellStyleSetting(Row row, Cell cell, CellStyle styleCell, int startIndex, int endIndex) {
		for (int i = startIndex; i <= endIndex; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(styleCell);
		}

	}

	@Override
	public void printPackingListExcel(HttpServletRequest request, HttpServletResponse response,
			ArrayList<String> orderNnoList, String printType) throws Exception {
		try {
			String filePath = realFilePath + "excel/downloadData/packing_list_sample.xlsx";
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
				cell.setCellValue(commercialInfo.getHawbNo() + " / " + commercialInfo.getMatchNo());

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
					cell.setCellValue(commercialInfo.getOrderDate());
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
				// cellStyleSetting(row, cell, styleCellLR, 4, 7);
				cell = row.getCell(4);

				String remarkString = "";
				if (!commercialInfo.getBuySite().equals("")) {
					remarkString += "Buy Site URL : " + commercialInfo.getBuySite();
					remarkString += "\n";
				}
				cell.setCellValue(remarkString);

				row = sheet.getRow(17);
				cell = row.getCell(4);
				cell.setCellValue(commercialInfo.getPayment());

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
						// cell.setCellValue(commercialItem.get(j).getUnitValue() + " " +
						// commercialItem.get(j).getChgCurrency());
						cell.setCellValue(Math.round(commercialInfo.getUserWta() * 100) / 100.0);
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						// cell.setCellValue(amount + " " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(Math.round(commercialInfo.getUserWtc() * 100) / 100.0);
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
						// cell.setCellValue(commercialItem.get(j).getUnitValue() + " " +
						// commercialItem.get(j).getChgCurrency());
						cell.setCellValue(Math.round(commercialInfo.getUserWta() * 100) / 100.0);
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						cell.setCellValue(Math.round(commercialInfo.getUserWtc() * 100) / 100.0);
						// cell.setCellValue(amount + " " + commercialItem.get(j).getChgCurrency());
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
					row = sheet.createRow(rowCnt + 1);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 5));
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cell = row.getCell(0);
					cell.setCellValue("TOTAL WEIGHT : ");
					row.setHeight((short) 500);
					cell = row.getCell(6);
					cell.setCellValue(Math.round(commercialInfo.getUserWta() * 100) / 100.0);
					cell = row.getCell(7);
					cell.setCellValue(Math.round(commercialInfo.getUserWtc() * 100) / 100.0);
					rowCnt++;
				} else {
					rowCnt = 38;
					row = sheet.createRow(rowCnt);
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cellStyleSetting(row, cell, styleHawb, 0, 7);
					cell = row.getCell(0);
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 5));
					cell.setCellValue("TOTAL WEIGHT : ");
					row.setHeight((short) 500);
					cell = row.getCell(6);
					cell.setCellValue(Math.round(commercialInfo.getUserWta() * 100) / 100.0);
					cell = row.getCell(7);
					cell.setCellValue(Math.round(commercialInfo.getUserWtc() * 100) / 100.0);
				}

				Font boxFont = wb.createFont();
				boxFont.setFontName("나눔고딕");
				boxFont.setFontHeight((short) 800);

				CellStyle centerStyle = wb.createCellStyle();
				centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
				centerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				centerStyle.setWrapText(true);
				centerStyle.setFont(boxFont);

				row = sheet.getRow(25);
				cell = row.getCell(0);
				cell.setCellValue(commercialInfo.getBoxCnt());
				cell.setCellStyle(centerStyle);
				if (commercialItem.size() > 13) {
					sheet.addMergedRegion(new CellRangeAddress(25, 25 + commercialItem.size() - 1, 0, 0));
				} else {
					sheet.addMergedRegion(new CellRangeAddress(25, 37, 0, 0));
				}

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
			response.setHeader("Content-Disposition", "attachment;filename=packing_list.xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<UserOrderListVO> selectOrderListLabelChange(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectOrderListLabelChange(parameterInfo);
	}

	@Override
	public int selectOrderListLabelChangeCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectOrderListLabelChangeCnt(parameterInfo);
	}

	@Override
	public ArrayList<HawbListVO> selectChangeLabelList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectChangeLabelList(parameterInfo);
	}

	@Override
	public void monthReportExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String year = request.getParameter("sekYear");
		String month = request.getParameter("sekMonth");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
		String sekFrom = year + month + "01";
		String sekTo = year + month + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("sekFrom", sekFrom);
		parameters.put("sekTo", sekTo);

		try {
			String savePath = realFilePath + "excel/expLicence/";
			String fileName = year+"년 "+month+"월 전체 개수.xlsx";
			
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(year+month);
			
			CellStyle cellStyleH = wb.createCellStyle();
			cellStyleH.setWrapText(false);
			cellStyleH.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			
			Row row = null;
			Cell cell = null;
			int rowCnt = 0;
			
			
			row = sheet.createRow(rowCnt);
			cell = row.createCell(0);
			cell.setCellValue("업체명");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(1);
			cell.setCellValue("도착국가");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(2);
			cell.setCellValue("계측기 측정 무게");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(3);
			cell.setCellValue("계측기 측정 부피");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(4);
			cell.setCellValue("운송사 측정 무게");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(5);
			cell.setCellValue("운송사 측정 부피");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(6);
			cell.setCellValue("적용 타입");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(7);
			cell.setCellValue("적용 무게");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(8);
			cell.setCellValue("W_DATE");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(9);
			cell.setCellValue("MAWB_NO");
			cell.setCellStyle(cellStyleH);
			
			cell = row.createCell(10);
			cell.setCellValue("배송사");
			cell.setCellStyle(cellStyleH);
			
			rowCnt++;
			
			ArrayList<HashMap<String, Object>> hawbList = new ArrayList<HashMap<String, Object>>();
			hawbList = mapper.selectMonthReportHawbList(parameters);
			
			for (int i = 0; i < hawbList.size(); i++) {
				HashMap<String, Object> hawbInfo = hawbList.get(i);
				row = sheet.createRow(rowCnt);
				
				cell = row.createCell(0);
				cell.setCellValue(hawbInfo.get("comName").toString());
				
				cell = row.createCell(1);
				cell.setCellValue(hawbInfo.get("dstnNation").toString());
				
				cell = row.createCell(2);
				cell.setCellValue(hawbInfo.get("wta").toString());
				
				cell = row.createCell(3);
				cell.setCellValue(hawbInfo.get("wtc").toString());
				
				cell = row.createCell(4);
				cell.setCellValue(hawbInfo.get("agencyWta").toString());
				
				cell = row.createCell(5);
				cell.setCellValue(hawbInfo.get("agencyWtc").toString());
				
				cell = row.createCell(6);
				cell.setCellValue(hawbInfo.get("wtType").toString());
				
				cell = row.createCell(7);
				cell.setCellValue(hawbInfo.get("wt").toString());
				
				cell = row.createCell(8);
				cell.setCellValue(hawbInfo.get("wDate").toString());
				
				cell = row.createCell(9);
				cell.setCellValue(hawbInfo.get("mawbNo").toString());
				
				cell = row.createCell(10);
				cell.setCellValue(hawbInfo.get("transCode").toString());
				
				rowCnt++;
			}


			
			File xlsFile = new File(savePath + fileName);
			FileOutputStream output = new FileOutputStream(xlsFile);
			wb.write(output);
			
			InputStream in = null;
			OutputStream out = null;
			File file = null;
			String client = "";
			boolean skip = false;
			
			try {
				file = new File(savePath, fileName);
				in = new FileInputStream(file);	
			} catch (FileNotFoundException e) {
				skip = true;
			}
			
			
			client = request.getHeader("User-Agent");
			response.reset();
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			
			if (!skip) {
				// IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; fileName=" + new String(fileName.getBytes("KSC5601"), "ISO8859_1"));
				} else {
					// 한글 파일명 처리
					fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
					response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				out = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					out.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html;charset=UTF-8");
			}

			out.close();
			in.close();
			file.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<String, Object> selectWeightInfo(String nno) throws Exception {
		return mapper.selectWeightInfo(nno);
	}

	@Override
	public int selectDeleteOrderListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectDeleteOrderListCnt(params);
	}

	@Override
	public ArrayList<ShpngListVO> selectDeleteOrderList(HashMap<String, Object> params) throws Exception {
		return mapper.selectDeleteOrderList(params);
	}

	@Override
	public void downloadYunExpExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = realFilePath + "excel/downloadData/YunExpress_Template.xlsx";
		String fileName = "";
		Date nowDate = new Date();
		
		String datePattern = "yyyyMMdd";
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		String date = format.format(nowDate);
		
		FileInputStream fis = new FileInputStream(filePath);
		Row row = null;
		Cell cell = null;
		Workbook wb = WorkbookFactory.create(fis);
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("date", date);
		parameters.put("transCode", "YT");
		
		ArrayList<UserOrderListVO> orderInfo = new ArrayList<UserOrderListVO>();
		orderInfo = mapper.selectYunExpressOrderInfo(parameters);
		
		Sheet sheet = wb.getSheetAt(0);
		int rowNo = 1;
		
		for (int i = 0; i < orderInfo.size(); i++) {
			ArrayList<UserOrderItemVO> orderItem = new ArrayList<UserOrderItemVO>();
			orderInfo.get(i).dncryptData();
			parameters.put("nno", orderInfo.get(i).getNno());
			parameters.put("userId", orderInfo.get(i).getUserId());
			
			row = sheet.createRow(rowNo);
			cell = row.createCell(0);
			cell.setCellValue(orderInfo.get(i).getOrderNo());
			
			cell = row.createCell(1);
			cell.setCellValue("KRTHZXR");
			
			cell = row.createCell(7);
			cell.setCellValue(orderInfo.get(i).getDstnNation());
			
			cell = row.createCell(8);
			cell.setCellValue(orderInfo.get(i).getCneeName());
			
			
			String cneeAddr = orderInfo.get(i).getCneeAddr();
			if (!orderInfo.get(i).getCneeAddrDetail().equals("")) {
				cneeAddr += orderInfo.get(i).getCneeAddrDetail();
			}
			
			cell = row.createCell(11);
			cell.setCellValue(cneeAddr);
			
			cell = row.createCell(12);
			cell.setCellValue(orderInfo.get(i).getCneeCity());
			
			cell = row.createCell(13);
			cell.setCellValue(orderInfo.get(i).getCneeState());
			
			cell = row.createCell(14);
			cell.setCellValue(orderInfo.get(i).getCneeZip());
			
			cell = row.createCell(15);
			cell.setCellValue(orderInfo.get(i).getCneeTel());
			
			cell = row.createCell(18);
			cell.setCellValue(orderInfo.get(i).getBoxCnt());
			
			cell = row.createCell(19);
			cell.setCellValue(orderInfo.get(i).getUserWta());
			
			
			orderItem = mapper.selectYunExpressItemInfo(parameters);
			for (int j = 0; j < orderItem.size(); j++) {
				
				if (!orderItem.get(j).getSubNo().equals("1")) {
					row = sheet.createRow(rowNo);
				}
				
				String currency = "";
				if (orderItem.get(j).getUnitCurrency().equals("")) {
					currency = orderItem.get(j).getChgCurrency();
				} else {
					currency = orderItem.get(j).getUnitCurrency();
				}
				
				cell = row.createCell(28);
				cell.setCellValue(currency);
				
				cell = row.createCell(30);
				cell.setCellValue(orderItem.get(j).getItemDetail());
				
				cell = row.createCell(31);
				cell.setCellValue(orderItem.get(j).getNativeItemDetail());
				
				cell = row.createCell(32);
				cell.setCellValue(orderItem.get(j).getItemCnt());
				
				cell = row.createCell(33);
				cell.setCellValue(orderItem.get(j).getUnitValue());
				
				cell = row.createCell(34);
				cell.setCellValue(orderItem.get(j).getUserWta());
				
				cell = row.createCell(35);
				cell.setCellValue(orderItem.get(j).getHsCode());
				
				rowNo++;
			}
			
		}
		
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=YunExpressOrder_"+date+".xlsx");

		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		out.close();
	}

	@Override
	public ArrayList<ShpngListVO> selectYunExpOrderList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectYunExpOrderList(parameters);
	}

	@Override
	public void yunExpOrderListExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(date);
		String[] numberStr = request.getParameter("cntList").split(",");
		int[] cntList = Arrays.stream(numberStr).mapToInt(Integer::parseInt).toArray();
		int maxNumber = Arrays.stream(cntList).max().orElse(1);
		// 템플릿 header 생성을 위해 상품 수량 최대값 추출 
		String fileName = today+"_YT_OrderList";
		String[] nnoList = request.getParameter("nnoList").split(",");
		
		try {
			
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			Row row = null;
			Cell cell = null;
			int rowNo = 0;
			int cellNo = 0;
			
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("CustomerOrderNo.");
			cellNo++;
			
			cell = row.createCell(cellNo);
			cell.setCellValue("RoutingCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Trackingnumber");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("AdditionalServices");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("VatNumber");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("EoriNumber");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("IossCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("CountryCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Name");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("CertificateCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Company");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Street");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("City");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Province/State");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("ZipCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("phone");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("HouseNumber");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("Email");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("PackageNumber");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("PackageWeight");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderFiastName");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderCompany");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderStreet");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderCity");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderProvince");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderPostalCode");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderCountry");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("SenderTelephone");
			cellNo++;

			cell = row.createCell(cellNo);
			cell.setCellValue("CurrencyCode");
			cellNo++;
			
			// 상품정보 
			
			for (int i = 1; i <= maxNumber; i++) {
				
				cell = row.createCell(cellNo);
				cell.setCellValue("SKU"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("ItemDescription"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("ForeignItemDescription"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("DeclaredQuantity"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("UnitPrice"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("UnitWeight"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("HsCode"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("Remarks"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("SalesLink"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("Materials"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("Use"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("Brand"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("ModelType"+i);
				cellNo++;

				cell = row.createCell(cellNo);
				cell.setCellValue("Specs"+i);
				cellNo++;
				
			}

			
			for (int roop = 0; roop < nnoList.length; roop++) {
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				String nno = nnoList[roop];
				parameters.put("nno", nno);

				UserOrderListVO orderInfo = new UserOrderListVO();
				orderInfo = mapper.selectYunExpOrderInfo(parameters);
				orderInfo.dncryptData();

				rowNo++;
				
				row = sheet.createRow(rowNo);
				cell = row.createCell(0);
				cell.setCellValue(orderInfo.getNno());
				cellNo++;
				
				cell = row.createCell(1);
				cell.setCellValue("KRTHZXR");
				
				cell = row.createCell(7);
				cell.setCellValue(orderInfo.getDstnNation());
				
				cell = row.createCell(8);
				cell.setCellValue(orderInfo.getCneeName());
				
				String cneeAddr = orderInfo.getCneeAddr();
				if (!orderInfo.getCneeAddrDetail().equals("")) {
					cneeAddr += " "+orderInfo.getCneeAddrDetail();
				}
				
				cell = row.createCell(11);
				cell.setCellValue(cneeAddr);
				
				cell = row.createCell(12);
				cell.setCellValue(orderInfo.getCneeCity());
				
				cell = row.createCell(13);
				cell.setCellValue(orderInfo.getCneeState());
				
				cell = row.createCell(14);
				cell.setCellValue(orderInfo.getCneeZip());
				
				cell = row.createCell(15);
				cell.setCellValue(orderInfo.getCneeTel());
				
				cell = row.createCell(18);
				cell.setCellValue(orderInfo.getBoxCnt());
				
				cell = row.createCell(19);
				cell.setCellValue(orderInfo.getUserWta());
				
				cell = row.createCell(28);
				cell.setCellValue(orderInfo.getUnitCurrency());
				
				ArrayList<UserOrderItemVO> orderItem = new ArrayList<UserOrderItemVO>();
				parameters.put("userId", orderInfo.getUserId());
				orderItem = mapper.selectYunExpressItemInfo(parameters);
				
				cellNo = 29;
				
				for (int jj = 0; jj < orderItem.size(); jj++) {
					
					cell = row.createCell(cellNo);
					cell.setCellValue("");
					cellNo++;
					
					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getItemDetail());
					cellNo++;
					
					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getNativeItemDetail());
					cellNo++;
					
					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getItemCnt());
					cellNo++;
					
					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getUnitValue());
					cellNo++;

					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getUserWta());
					cellNo++;

					// 35 - AJ
					cell = row.createCell(cellNo);
					cell.setCellValue(orderItem.get(jj).getHsCode());
					cellNo++;
					
					cell = row.createCell(cellNo);
					cellNo++;
					
					cell = row.createCell(cellNo);
					cellNo++;

					cell = row.createCell(cellNo);
					cellNo++;

					cell = row.createCell(cellNo);
					cellNo++;

					cell = row.createCell(cellNo);
					cellNo++;

					cell = row.createCell(cellNo);
					cellNo++;

					cell = row.createCell(cellNo);
					cellNo++;
					
				}
				
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public UserOrderListVO selectYunExpOrderInfo(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectYunExpOrderInfo(parameters);
	}

	@Override
	public void insertYunExpDeliveryInfo(HashMap<String, Object> parameters) throws Exception {
		mapper.insertYunExpDeliveryInfo(parameters);
	}

	@Override
	public HashMap<String, Object> selectUserIdByNno(String nno) throws Exception {
		return mapper.selectUserIdByNno(nno);
	}

	@Override
	public String selectOrgStationByUserId(String userId) throws Exception {
		return mapper.selectOrgStationByUserId(userId);
	}

	@Override
	public HashMap<String, Object> selectCheckMawbNo(String mawbNo) throws Exception {
		return mapper.selectCheckMawbNo(mawbNo);
	}

	@Override
	@Transactional
	public void updateMawbNo(HawbVO hawbVo) throws Exception {
		mapper.updateMawbNo(hawbVo);
		mapper.insertHisMawbApply(hawbVo);
	}

	@Override
	public void updateBagNo(HashMap<String, Object> params) throws Exception {
		mapper.updateBagNo(params);
	}

	@Override
	public ArrayList<ManagerVO> selectSendEmailreturnSeller() throws Exception {
		return mapper.selectSendEmailreturnSeller();
	}

	@Override
	public void insertHisSendEmail(ArrayList<ManagerVO> userInfo) throws Exception {
		mapper.insertHisSendEmail(userInfo);
	}

	@Override
	public ArrayList<BizInfo> selectBizInfoList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectBizInfoList(parameters);
	}

	@Override
	public int selectBizInfoListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectBizInfoListCnt(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserInfoList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectUserInfoList(parameters);
	}

	@Override
	public void insertBizInfo(BizInfo bizInfo) throws Exception {
		mapper.insertBizInfo(bizInfo);
	}

	@Override
	public BizInfo selectBizInfoOne(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectBizInfoOne(parameters);
	}

	@Override
	public void updateBizInfo(BizInfo bizInfo) throws Exception {
		mapper.updateBizInfo(bizInfo);
	}

	@Override
	public ArrayList<String> selectExpNoYsl() throws Exception {
		return mapper.selectExpNoYsl();
	}

	@Override
	public ArrayList<String> selectExpNoFB() throws Exception {
		return mapper.selectExpNoFB();
	}

	@Override
	public byte[] selectDownloadManifest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
		
			String mawbNo = request.getParameter("mawbNo");
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("mawbNo", mawbNo);
			parameters.put("orgStation", orgStation);
			
			HashMap<String, Object> mawbInfo = new HashMap<String, Object>();
			mawbInfo = mapper.selectMawbInfo(parameters);
			
			String origin = "";
			String eta = (String) mawbInfo.get("eta");
			String etd = (String) mawbInfo.get("etd");
			String dstnStation = (String) mawbInfo.get("dstnStation");
			
			if (mawbInfo.get("orgStation").toString().equals("213")) {
				origin = "LAX";
			} else if (mawbInfo.get("orgStation").toString().equals("082")) {
				origin = "SEL";
			} else if (mawbInfo.get("orgStation").toString().equals("441")) {
				origin = "LHR";
			} else if (mawbInfo.get("orgStation").toString().equals("049")) {
				origin = "FRA";
			}
			
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/mawbList/sampleExcel/manifest_sample.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
				
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			String pdfPath = realFilePath + "/pdf/barcode/";
			File dir = new File(pdfPath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			
			String barcodePath = pdfPath + mawbNo + ".jpeg";
			Barcode barcode = BarcodeFactory.createCode128(mawbNo);
			barcode.setBarHeight(65);
			barcode.setDrawingQuietSection(false);
			
			File barcodeFile = new File(barcodePath);
			
			BarcodeImageHandler.saveJPEG(barcode, barcodeFile);
			
			String aciMarkPath = realFilePath + "/pdf/mark/aciMark.jpg";
			File markFile = new File(aciMarkPath);
			
			FileInputStream is = new FileInputStream(barcodeFile);
			FileInputStream is2 = new FileInputStream(markFile);
			
			byte[] bytes = IOUtils.toByteArray(is);
			byte[] bytes2 = IOUtils.toByteArray(is2);
			
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			int pictureIdx2 = wb.addPicture(bytes2, Workbook.PICTURE_TYPE_JPEG);
			
			XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

			XSSFClientAnchor anchor = new XSSFClientAnchor();
			XSSFClientAnchor anchor2 = new XSSFClientAnchor();

			anchor.setCol1(3);
			anchor.setCol2(6);
			anchor.setRow1(1);
			anchor.setRow2(4);
			
			anchor2.setCol1(0);
			anchor2.setCol2(2);
			anchor2.setRow1(1);
			anchor2.setRow2(4);
			
			drawing.createPicture(anchor, pictureIdx);
			drawing.createPicture(anchor2, pictureIdx2);
			
			
			row = sheet.getRow(1);
			cell = row.getCell(14);
			cell.setCellValue(mawbNo);
			
			row = sheet.getRow(2);
			cell = row.getCell(14);
			cell.setCellValue(etd);
			
			cell = row.getCell(18);
			cell.setCellValue(eta);
			
			row = sheet.getRow(3);
			cell = row.getCell(14);
			cell.setCellValue(origin);
			
			cell = row.getCell(18);
			cell.setCellValue(dstnStation);

			Font font = wb.createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 9);

			
			CellStyle borderRightBottom = wb.createCellStyle();
			borderRightBottom.setBorderRight(CellStyle.BORDER_THIN);
			borderRightBottom.setBorderBottom(CellStyle.BORDER_THIN);
			borderRightBottom.setBorderLeft(CellStyle.BORDER_THIN);
			borderRightBottom.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			borderRightBottom.setAlignment(CellStyle.ALIGN_CENTER);
			borderRightBottom.setFont(font);
			
			CellStyle borderBottomAlignCenter = wb.createCellStyle();
			borderBottomAlignCenter.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			borderBottomAlignCenter.setAlignment(CellStyle.ALIGN_CENTER);
			borderBottomAlignCenter.setBorderBottom(CellStyle.BORDER_THIN);
			borderBottomAlignCenter.setFont(font);
			
			CellStyle borderBottomAlignLeft = wb.createCellStyle();
			borderBottomAlignLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			borderBottomAlignLeft.setAlignment(CellStyle.ALIGN_LEFT);
			borderBottomAlignLeft.setBorderBottom(CellStyle.BORDER_THIN);
			borderBottomAlignLeft.setWrapText(true);
			borderBottomAlignLeft.setFont(font);

			CellStyle borderRightBottomRight = wb.createCellStyle();
			borderRightBottomRight.setBorderLeft(CellStyle.BORDER_THIN);
			borderRightBottomRight.setBorderRight(CellStyle.BORDER_THIN);
			borderRightBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
			borderRightBottomRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			borderRightBottomRight.setAlignment(CellStyle.ALIGN_RIGHT);
			borderRightBottomRight.setFont(font);
			
			
			ArrayList<UserOrderListVO> order = new ArrayList<UserOrderListVO>();
			order = comnMapper.selectMawbOrderInfo(parameters);

			int rowNo = 7;
			
			int totalBoxCnt = 0;
			double totalWta = 0;
			double totalValue = 0;
			

			for (int i = 0; i < order.size(); i++) {
				UserOrderListVO orderInfo = order.get(i);
				orderInfo.dncryptData();
				
				int number = i+1;
				int startRow = rowNo;
				
				row = sheet.createRow(rowNo);
				
				cell = row.createCell(0);
				cell.setCellValue(number);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(1);
				cell.setCellValue(orderInfo.getHawbNo());
				cell.setCellStyle(borderBottomAlignCenter);
				
				cell = row.createCell(2);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(3);
				cell.setCellStyle(borderRightBottom);
				
				
				String shipperInfo = orderInfo.getShipperName();
				if (!orderInfo.getShipperZip().equals("")) {
					shipperInfo += "\n" + orderInfo.getShipperZip();
				}
				
				shipperInfo += " " + orderInfo.getShipperAddr();
				if (!orderInfo.getShipperAddrDetail().equals("")) {
					shipperInfo += " " + orderInfo.getShipperAddrDetail();
				}
				
				
				cell = row.createCell(4);
				cell.setCellValue(shipperInfo);
				cell.setCellStyle(borderBottomAlignLeft);
				cell.getRow().setHeight((short)480);

				cell = row.createCell(5);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(6);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(7);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(8);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(9);
				cell.setCellValue(orderInfo.getDstnNation());
				cell.setCellStyle(borderRightBottom);
				
				
				cell = row.createCell(10);
				cell.setCellValue(orderInfo.getBoxCnt());
				cell.setCellStyle(borderRightBottomRight);
				
				totalBoxCnt += Integer.parseInt(orderInfo.getBoxCnt());
				
				String[] itemInfos = orderInfo.getItemDetail().split("\\|");
				String commodity = "";
				for (int idx = 0; idx < itemInfos.length; idx++) {
					String itemInfo = itemInfos[idx];
					
					if (idx == 0) {
						commodity += itemInfo;
					} else {
						commodity += "\n" + itemInfo;
					}
				}
				
				cell = row.createCell(11);
				cell.setCellValue(commodity);
				cell.setCellStyle(borderBottomAlignLeft);


				cell = row.createCell(12);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(13);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(14);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(15);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(16);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(17);
				cell.setCellStyle(borderRightBottom);
				
				
				cell = row.createCell(18);
				cell.setCellValue(orderInfo.getTotalItemValue());
				cell.setCellStyle(borderRightBottomRight);
				
				double totalItemValue = Double.parseDouble(orderInfo.getTotalItemValue());
				totalValue += totalItemValue;

				cell = row.createCell(19);
				cell.setCellStyle(borderRightBottom);
				
				
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 4, 8));
				
				rowNo++;
				
				row = sheet.createRow(rowNo);

				cell = row.createCell(0);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(1);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(2);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(3);
				cell.setCellStyle(borderRightBottom);
				
				
				String cneeInfo = orderInfo.getCneeName();
				if (!orderInfo.getCneeTel().equals("")) {
					cneeInfo += " TEL : " + orderInfo.getCneeTel();
				}
				
				cneeInfo += "\n" + orderInfo.getCneeAddr();
				
				if (!orderInfo.getCneeAddrDetail().equals("")) {
					cneeInfo += " " + orderInfo.getCneeAddrDetail();
				}
				
				cneeInfo += " " + orderInfo.getCneeZip();
				
				cell = row.createCell(4);
				cell.setCellValue(cneeInfo);
				cell.setCellStyle(borderBottomAlignLeft);
				cell.getRow().setHeight((short)480);

				cell = row.createCell(5);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(6);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(7);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(8);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(9);
				cell.setCellStyle(borderRightBottom);
				
				cell = row.createCell(10);
				cell.setCellValue(orderInfo.getWta());
				cell.setCellStyle(borderRightBottomRight);
				
				double wta = Double.parseDouble(orderInfo.getWta());
				totalWta += wta;
				
				cell = row.createCell(11);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(12);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(13);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(14);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(15);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(16);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(17);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(18);
				cell.setCellStyle(borderRightBottom);

				cell = row.createCell(19);
				cell.setCellStyle(borderRightBottom);
				
				sheet.addMergedRegion(new CellRangeAddress(startRow, rowNo, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(startRow, rowNo, 1, 3));
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 4, 8));
				sheet.addMergedRegion(new CellRangeAddress(startRow, rowNo, 9, 9));
				sheet.addMergedRegion(new CellRangeAddress(startRow, rowNo, 11, 17));
				sheet.addMergedRegion(new CellRangeAddress(startRow, rowNo, 18, 19));

				rowNo++;
			}
			
			int footerRowNo = rowNo;
			
			row = sheet.createRow(footerRowNo);
			
			cell = row.createCell(0);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(borderRightBottomRight);

			cell = row.createCell(1);
			cell.setCellStyle(borderRightBottom);
			
			cell = row.createCell(2);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(3);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(4);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(5);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(6);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(7);
			cell.setCellStyle(borderRightBottom);
			
			cell = row.createCell(8);
			cell.setCellStyle(borderRightBottomRight);
			
			cell = row.createCell(9);
			cell.setCellValue(totalBoxCnt);
			cell.setCellStyle(borderRightBottomRight);
			
			cell = row.createCell(10);
			cell.setCellValue(Math.round(totalWta*100.0)/100.0);
			cell.setCellStyle(borderRightBottomRight);

			cell = row.createCell(11);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(12);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(13);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(14);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(15);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(16);
			cell.setCellStyle(borderRightBottom);

			cell = row.createCell(17);
			cell.setCellStyle(borderRightBottom);
			
			cell = row.createCell(18);
			cell.setCellValue(Math.round(totalValue*100.0)/100.0);
			cell.setCellStyle(borderRightBottomRight);

			cell = row.createCell(19);
			cell.setCellStyle(borderRightBottom);
			
			sheet.addMergedRegion(new CellRangeAddress(footerRowNo, footerRowNo, 0, 8));
			sheet.addMergedRegion(new CellRangeAddress(footerRowNo, footerRowNo, 11, 17));
			sheet.addMergedRegion(new CellRangeAddress(footerRowNo, footerRowNo, 18, 19));
			

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            return outputStream.toByteArray();
            
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectFastboxData() throws Exception {
		return mapper.selectFastboxData();
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTransCodeList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTransCodeList(parameterInfo);
	}

	@Override
	public int selectMawbApplyListCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectMawbApplyListCnt(parameterInfo);
	}

	@Override
	public ArrayList<SendVO> selectMawbApplyList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectMawbApplyList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTaxTypeList() throws Exception {
		return mapper.selectTaxTypeList();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}