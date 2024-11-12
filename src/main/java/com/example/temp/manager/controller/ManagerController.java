package com.example.temp.manager.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

import org.apache.catalina.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.params.AbstractHttpParams;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.aci.service.impl.ApiServiceImpl;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.api.aci.vo.BizInfo;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.NoticeVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.manager.service.ManagerInvoiceService;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.service.impl.ManagerServiceImpl;
import com.example.temp.manager.vo.DlvChgInfoVO;
import com.example.temp.manager.vo.DlvPriceVO;
import com.example.temp.manager.vo.ExpLicenceListVO;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.HawbChkVO;
import com.example.temp.manager.vo.HawbListVO;
import com.example.temp.manager.vo.HawbVO;
import com.example.temp.manager.vo.ManagerVO;
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
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.StockOutVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.TransComVO;
import com.example.temp.manager.vo.VolumeVO;
import com.example.temp.manager.vo.WhNoticeVO;
import com.example.temp.manager.vo.insp.InnerProductVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.AllowIpVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.security.SecurityService;
import com.example.temp.smtp.SmtpService;
import com.example.temp.smtp.ViewMatchingInfo;
import com.example.temp.smtp.ViewYslItemCode;
import com.example.temp.trans.comn.HawbLookUpVo;
import com.example.temp.trans.cse.CseAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.example.temp.trans.ozon.OzonAPI;
import com.example.temp.trans.parcll.ParcllAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Controller
public class ManagerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ManagerService mgrService;

	@Autowired
	ComnService comnService;

	@Autowired
	MemberService usrService;

	@Autowired
	ManagerInvoiceService invService;
	
	@Autowired
	SecurityService securityService;

	@Autowired
	YongSungAPI ysApi;

	@Autowired
	SmtpService smtpService;

	@Autowired
	FedexAPI fdxApi;

	@Autowired
	EfsAPI efsApi;

	@Autowired
	SekoAPI sekoApi;

	@Autowired
	GtsAPI gtsApi;

	@Autowired
	CseAPI cseApi;

	@Autowired
	OzonAPI ozonApi;

	@Autowired
	EmsApi emsApi;

	@Autowired
	Type86API type86Api;

	@Autowired
	FastboxAPI fastboxApi;

	@Autowired
	ApiServiceImpl apiServiceImpl;

	@Autowired
	ApiService apiService;
	
	@Autowired
	ParcllAPI pclApi;

	@Value("${filePath}")
	String realFilePath;

	@Value("${smtpStatus}")
	String smtpStatus;

	@Value("${orderInfoChkStatus}")
	String orderInfoChkStatus;

	@Value("${schedulerStatus}")
	String schedulerStatus;
	
	
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

	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
	}
	
	@RequestMapping(value = "/mngr/homeTest", method = RequestMethod.GET)
	public String managerHomeTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "importFile/indexTest";
	}

	@RequestMapping(value = "/mngr/home", method = RequestMethod.GET)
	public String managerMainIndexPage(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Date time = new Date();
		// String month = "202209";
		String month = format.format(time);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		params.put("userId", request.getParameter("userId"));

		if (request.getParameter("depMonth") == null) {
			params.put("depMonth", month);
		} else {
			params.put("depMonth", request.getParameter("depMonth"));
		}

		ArrayList<StockOutVO> list = new ArrayList<StockOutVO>();
		list = mgrService.selectUserStockOutList(params);

		if (list.size() > 0) {
			Gson gson = new Gson();
			JsonArray jsonArray = new JsonArray();

			Iterator<StockOutVO> iterator = list.iterator();
			while (iterator.hasNext()) {
				StockOutVO stockOutVO = iterator.next();
				JsonObject jsonObject = new JsonObject();

				String userId = stockOutVO.getUserId();
				int cnt = stockOutVO.getCnt();

				jsonObject.addProperty("userId", userId);
				jsonObject.addProperty("cnt", cnt);
				jsonArray.add(jsonObject);
			}

			String json = gson.toJson(jsonArray);

			ArrayList<HashMap<String, Object>> transList = new ArrayList<HashMap<String, Object>>();
			transList = mgrService.selectTransCodeFilter(params);

			JsonArray jsonArray2 = new JsonArray();
			for (Map<String, Object> map : transList) {
				JsonObject obj = new JsonObject();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					try {
						obj.addProperty(key, String.valueOf(value));
					} catch (JSONException e) {
						logger.error("Exception", e);
					}
				}

				jsonArray2.add(obj);
			}

			int transTotalCnt = mgrService.selectTransCodeTotalCnt(params);
			int inCnt = mgrService.selectInBoundCnt(params);
			int outCnt = mgrService.selectOutBoundCnt(params);

			HashMap<String, Object> params2 = new HashMap<String, Object>();
			params2.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

			ArrayList<StockOutVO> stockOutList = new ArrayList<StockOutVO>();
			stockOutList = mgrService.selectStockOutMonthlyList(params2);
			Gson stockOutGson = new Gson();
			JsonArray stockOutArray = new JsonArray();

			Iterator<StockOutVO> iter = stockOutList.iterator();
			while (iter.hasNext()) {
				StockOutVO vo = iter.next();
				JsonObject jsonObject = new JsonObject();
				String depMonth = vo.getDepMonth();
				String stationName = vo.getStationName();
				int cnt = vo.getCnt();

				jsonObject.addProperty("depMonth", depMonth);
				jsonObject.addProperty("stationName", stationName);
				jsonObject.addProperty("cnt", cnt);
				stockOutArray.add(jsonObject);
			}
			String stockOutJson = stockOutGson.toJson(stockOutArray);

			ArrayList<HashMap<String, Object>> dailyChart = new ArrayList<HashMap<String, Object>>();
			dailyChart = mgrService.selectDailyChart(params);

			JsonArray dailyArray = new JsonArray();
			for (Map<String, Object> map : dailyChart) {
				JsonObject obj = new JsonObject();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					try {
						obj.addProperty(key, String.valueOf(value));
					} catch (JSONException e) {
						logger.error("Exception", e);
					}
				}

				dailyArray.add(obj);
			}

			model.addAttribute("dailyChart", dailyArray);
			model.addAttribute("stockOut", stockOutJson);
			model.addAttribute("inCnt", inCnt);
			model.addAttribute("outCnt", outCnt);
			model.addAttribute("transTotalCnt", transTotalCnt);
			model.addAttribute("json2", jsonArray2);
			model.addAttribute("json", json);

			String result = "S";
			model.addAttribute("result", result);
		} else {
			String result = "F";
			model.addAttribute("result", result);
			model.addAttribute("month", month.substring(4));
		}
		/*
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", member.getUsername());
		infoMap.put("role", member.getRole());
		
		ArrayList<NoticeVO> noticeInfo = mgrService.selectNotice(infoMap);

		for (int i = 0; i < noticeInfo.size(); i++) {
			noticeInfo.get(i).setDate(noticeInfo.get(i).getWDate());
		}

		model.addAttribute("noticeInfo", noticeInfo);
		*/
		model.addAttribute("params", params);

		return "importFile/index";
		// return "adm/test3";
	}

	@RequestMapping(value = "/mngr/home/checkProps", method = RequestMethod.GET)
	public String managerCheckProperties(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		model.addAttribute("realFilePath", realFilePath);
		model.addAttribute("smtpStatus", smtpStatus);
		model.addAttribute("orderInfoChkStatus", orderInfoChkStatus);
		model.addAttribute("schedulerStatus", schedulerStatus);
		
		return "importFile/propertiesInfo";
	}

	@RequestMapping(value = "/mngr/acnt/userList", method = RequestMethod.GET)
	public String managerAcntUserList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		int curPage = 1;
		String userId = request.getParameter("keywords");

		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		infoMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		infoMap.put("chk", request.getParameter("chkYn"));
		
		int totalCount = mgrService.selectTotalCountInfo(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 30);
		// paging 끝

		infoMap.put("paging", paging);
		ArrayList<ManagerVO> comUserInfo = mgrService.selectUserList(infoMap);

		model.addAttribute("infoMap", infoMap);
		model.addAttribute("searchKeyword", userId);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("paging", paging);

		return "adm/member/userList";
	}

	/**
	 * Page : Manager 거래처 관리 - 개인 회원 URI : mngr/acnt/entrp request.getParameter :
	 * keywords = USER ID Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/indvdList", method = RequestMethod.GET)
	public String managerAcntIndividual(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		String userId = request.getParameter("keywords");

		/*
		 * paging start 함수로 뺄것.
		 */
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		infoMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		infoMap.put("etprYn", "N");
		int totalCount = mgrService.selectTotalCountInfo(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		// paging 끝

		infoMap.put("paging", paging);
		ArrayList<ManagerVO> comUserInfo = mgrService.selectUserList(infoMap);

		model.addAttribute("searchKeyword", userId);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("paging", paging);
		return "adm/member/indvdList";
	}

	@RequestMapping(value = "/mngr/acnt/zone/{userId}", method = RequestMethod.GET)
	public String managerZone(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, ArrayList<String>> zoneMap = new HashMap<String, ArrayList<String>>();
		ArrayList<DlvPriceVO> invoiceList = new ArrayList<DlvPriceVO>();
		ArrayList<NationVO> stationInfo = new ArrayList<NationVO>();
		ManagerVO customerInfo = new ManagerVO();

		zoneMap = mgrService.selectZoneMap(userId, request.getSession().getAttribute("ORG_STATION").toString());
		invoiceList = mgrService.selectInvoiceName();
		stationInfo = mgrService.selectStationInfo();
		customerInfo = mgrService.selectUserInfo(userId);
		model.addAttribute("zoneMap", zoneMap);
		model.addAttribute("userId", userId);
		model.addAttribute("invoiceList", invoiceList);
		model.addAttribute("stationInfo", stationInfo);
		model.addAttribute("customerInfo", customerInfo);
		return "adm/member/userZoneView";
	}

	@RequestMapping(value = "/mngr/acnt/zone/menual/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerMenualZonePrice(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<String> menualDstnList = new ArrayList<String>();

		menualDstnList = mgrService.selectMenualDstnList(userId,
				request.getSession().getAttribute("ORG_STATION").toString());
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", userId);
		parameters.put("volumeDiscount", request.getParameter("menualVolPercent"));
		parameters.put("actualDiscount", request.getParameter("menualPercent"));
		parameters.put("dlvCode", request.getParameter("dlvCode"));
		parameters.put("dvlChgType", "Menual");
		parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getSession().getAttribute("USER_IP"));

		mgrService.deleteCustomerChgInfo(parameters);
		for (int index = 0; index < menualDstnList.size(); index++) {
			parameters.put("dstnNation", menualDstnList.get(index));
			mgrService.insertCustomerChgInfo(parameters);
		}

		return "OK";
	}

	@RequestMapping(value = "/mngr/acnt/zone/individual/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerIndividualZonePrice(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", userId);
		parameters.put("volumeDiscount", request.getParameter("menualVolPercent"));
		parameters.put("actualDiscount", request.getParameter("menualPercent"));
		parameters.put("dvlChgType", "Individual");

		parameters.put("dstnNation", request.getParameter("dstnNation"));
		mgrService.updateCustomerChgInfo(parameters);

		return "OK";
	}

	@RequestMapping(value = "/mngr/acnt/zone/etcFee/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerEtcFee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", userId);
		parameters.put("surchargeType", request.getParameter("surchargeType"));
		parameters.put("etcArea", request.getParameter("etcArea"));
		parameters.put("caseFee", request.getParameter("caseFee"));
		parameters.put("clearanceFee", request.getParameter("clearanceFee"));
		parameters.put("fuelSurcharge", request.getParameter("fuelSurcharge"));
		parameters.put("exportDeclFee", request.getParameter("exportDeclFee"));
		parameters.put("surchargeType2", request.getParameter("surchargeType2"));
		parameters.put("surcharge", request.getParameter("surcharge"));
		parameters.put("fuelWtUnit", request.getParameter("fuelWtUnit"));
		parameters.put("surWtUnit", request.getParameter("surWtUnit"));

		mgrService.updateCustomerEtcFee(parameters);

		return "OK";
	}

	@RequestMapping(value = "/mngr/acnt/zone/registList/{userId}", method = RequestMethod.GET)
	public String managerLoadRegistList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {

		ArrayList<DlvChgInfoVO> dlvChgInfo = new ArrayList<DlvChgInfoVO>();

		dlvChgInfo = mgrService.selectDlvChgInfo(userId, request.getSession().getAttribute("ORG_STATION").toString());
		model.addAttribute("dlvChgInfo", dlvChgInfo);
		return "adm/member/userZoneRegistList";
	}

	@RequestMapping(value = "/mngr/acnt/zone/resetList/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerLoadResetList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", userId);
		parameters.put("dstnNation", request.getParameter("dstnNation"));
		mgrService.resetCustomerChgInfo(parameters);

		return "OK";
	}

	@RequestMapping(value = "/mngr/acnt/zone/currency/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerCustomerCurrency(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("invoiceStation", request.getParameter("invoiceStation"));
		parameters.put("currency", request.getParameter("currencyType"));
		parameters.put("userId", userId);
		mgrService.insertCustoerInvoice(parameters);

		return "OK";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 URI : mngr/acnt/entrp request.getParameter :
	 * keywords = USER ID Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/entrpList", method = RequestMethod.GET)
	public String managerAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		String userId = request.getParameter("keywords");

		/*
		 * paging start 함수로 뺄것.
		 */
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		infoMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		infoMap.put("etprYn", "Y");
		int totalCount = mgrService.selectTotalCountInfo(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		// paging 끝

		infoMap.put("paging", paging);
		ArrayList<ManagerVO> comUserInfo = mgrService.selectUserList(infoMap);

		model.addAttribute("searchKeyword", userId);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("paging", paging);
		return "adm/member/entrpList";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 등록 페이지 URI : mngr/acnt/indvdList/regist
	 * Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/regist", method = RequestMethod.GET)
	public String managerRegistAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		String referUrl = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/"));

		if (referUrl.contains("/indvdList")) {
			model.addAttribute("targetPage", "ind");

		} else if (referUrl.contains("/entrpList")) {
			model.addAttribute("targetPage", "ent");
		}

		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		return "adm/member/registInfo";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 등록 로직 URI : /mngr/acnt/indvdList/regist
	 * Method : POST
	 */
	@RequestMapping(value = "/mngr/acnt/regist", method = RequestMethod.POST)
	@ResponseBody
	public String managerRegistUserInfo(HttpServletRequest request, HttpServletResponse response, Model model,
			ManagerVO userInfo, String nationCodes, InvUserInfoVO invUserInfo) throws Exception {
		String queryResult = "";
		try {
			if (!userInfo.getUserPw().isEmpty()) {
				userInfo.setUserPw(userInfo.encryptSHA256(userInfo.getUserPw()));
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
				Date time = new Date();
				String time1 = format1.format(time);
				MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				userInfo.setUserUid("");
				userInfo.setWUserIp(request.getRemoteAddr());
				userInfo.setWDate(time1);
				userInfo.setRole("USER");
				userInfo.encryptData();
				mgrService.insertMemberInfos(userInfo, request, invUserInfo);
				queryResult = "S";
			} else {
				queryResult = "F";
			}
		} catch (Exception e) {
			queryResult = "F";
		}
		return queryResult;
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 - 택배회사 리스트 표출 Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/transCom", method = RequestMethod.POST)
	public String managerTransComList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, ArrayList<ZoneVO>> trkMap = new HashMap<String, ArrayList<ZoneVO>>();
		ArrayList<HashMap<String, Object>> blList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> TransMaps = new HashMap<String, Object>();
		TransMaps.put("orgs", request.getParameterValues("orgNation"));
		TransMaps.put("dstns", request.getParameterValues("dstnNation"));
		trkMap = comnService.makeTransMap(TransMaps);
		String userTrkValueList = request.getParameter("trkComValueList");
		blList = comnService.selectBlList();
		ArrayList<HashMap<String, Object>> userTrkCode = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> userTrkCodeBlType = new ArrayList<HashMap<String, Object>>();
		String[] userTrk = null;
		if (userTrkValueList == null || userTrkValueList.isEmpty()) {
			userTrkCode = comnService.selectUserTrkCom(request.getParameter("userId"));
			userTrkCodeBlType = comnService.selectUserTrkComBlType(request.getParameter("userId"));
			model.addAttribute("userTrkCode", userTrkCode);
			model.addAttribute("userTrkCodeBlType", userTrkCodeBlType);
		} else {
			userTrk = userTrkValueList.split(",");
			userTrkCodeBlType = comnService.selectUserTrkComBlType(request.getParameter("userId"));
			model.addAttribute("userTrkCode", userTrk);
			model.addAttribute("userTrkCodeBlType", userTrkCodeBlType);

		}
		model.addAttribute("trkMap", trkMap);
		model.addAttribute("blList", blList);
		return "comn/transCom/trackComList";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 수정 페이지 URI : mngr/acnt/{userId} Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/acnt/modify/{userId}", method = RequestMethod.GET)
	public String managerModifyAcntEnterprise(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {

		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		ArrayList<String> userOrgNation = new ArrayList<String>();
		ArrayList<String> userDstnNation = new ArrayList<String>();
		ArrayList<AllowIpVO> allowIpList = new ArrayList<AllowIpVO>();
		InvUserInfoVO invUserInfo = new InvUserInfoVO();

		String referUrl = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/"));
		if (referUrl.contains("/indvdList")) {
			model.addAttribute("targetPage", "ind");

		} else if (referUrl.contains("/entrpList")) {
			model.addAttribute("targetPage", "ent");
		}

		userOrgNation = mgrService.selectUserOrgNation(userId);
		userDstnNation = mgrService.selectUserDstnNation(userId);
		ManagerVO comUserInfo = mgrService.selectUserInfo(userId);
		/* comUserInfo.dncryptData(); */
		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		allowIpList = usrService.selectAllowIpList(userId);
		invUserInfo = comnService.selectInvUserInfo(userId);
		invUserInfo.dncryptData();

		model.addAttribute("userOrgNation", userOrgNation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("invUserInfo", invUserInfo);
		model.addAttribute("allowIpList", allowIpList);

		return "adm/member/modifyInfo";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 수정 페이지 URI : mngr/acnt/{userId} Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/acnt/modifys/{userId}", method = RequestMethod.GET)
	public String managerModifiesAcntEnterprise(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {

		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		ArrayList<String> userOrgNation = new ArrayList<String>();
		ArrayList<String> userDstnNation = new ArrayList<String>();
		ArrayList<AllowIpVO> allowIpList = new ArrayList<AllowIpVO>();
		ArrayList<HashMap<String, Object>> webhookList = new ArrayList<HashMap<String, Object>>();
		InvUserInfoVO invUserInfo = new InvUserInfoVO();

		String referUrl = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/"));
		if (referUrl.contains("/indvdList")) {
			model.addAttribute("targetPage", "ind");
		} else if (referUrl.contains("/entrpList")) {
			model.addAttribute("targetPage", "ent");
		}

		FastboxInfoVO fastboxInfo = new FastboxInfoVO();
		fastboxInfo.setUserId(userId);
		int mallCnt = mgrService.selectUserMallCnt(fastboxInfo);

		if (mallCnt > 0) {
			fastboxInfo = mgrService.selectUserMallInfo(userId);
			model.addAttribute("fastboxInfo", fastboxInfo);
		}

		userOrgNation = mgrService.selectUserOrgNation(userId);
		userDstnNation = mgrService.selectUserDstnNation(userId);
		ManagerVO comUserInfo = mgrService.selectUserInfo(userId);
		/* comUserInfo.dncryptData(); */
		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		allowIpList = usrService.selectAllowIpList(userId);
		invUserInfo = comnService.selectInvUserInfo(userId);
		invUserInfo.dncryptData();

		webhookList = comnService.selectWebHookInfo(userId);

		model.addAttribute("userOrgNation", userOrgNation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("invUserInfo", invUserInfo);
		model.addAttribute("allowIpList", allowIpList);
		model.addAttribute("webhookList", webhookList);

		return "adm/member/modifyInfoTest";
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 수정 로직 URI : mngr/acnt/{userId} Method :
	 * PATCH
	 */
	@RequestMapping(value = "/mngr/acnt/modify/{userId}", method = RequestMethod.PATCH)
	@ResponseBody
	public String managerModifyUserInfo(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model, ManagerVO userInfoVO, InvUserInfoVO invUserInfo)
			throws Exception {
		String queryResult = "";
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FastboxInfoVO fastboxInfo = new FastboxInfoVO();

		String mallId = request.getParameter("mallId");
		String sellerName = request.getParameter("sellerName");
		String consKey = request.getParameter("consumerKey");
		String token = request.getParameter("token");

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time2 = new Date();
			String day = format.format(time2);

			userInfoVO.setWDate(day);
			userInfoVO.setWUserId(member.getUsername());
			userInfoVO.setWUserIp(request.getRemoteAddr());
			userInfoVO.setCode("A001");
			userInfoVO.setCalculation("A");
			mgrService.insertDepositPrice(userInfoVO);

			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
			Date time = new Date();
			String time1 = format1.format(time);

			userInfoVO.setWUserId(member.getUsername());
			userInfoVO.setWUserIp(request.getRemoteAddr());
			userInfoVO.setWDate(time1);
			userInfoVO.encryptData();
			mgrService.updateUserInfo(userInfoVO, invUserInfo, request);

			fastboxInfo.setMallId(mallId);
			fastboxInfo.setSellerName(sellerName);
			fastboxInfo.setConsumerKey(consKey);
			fastboxInfo.setToken(token);
			fastboxInfo.setUserId(userId);
			fastboxInfo.setWUserId(member.getUsername());
			fastboxInfo.setWUserIp(request.getRemoteAddr());

			if (mallId != null) {
				int mallCnt = mgrService.selectUserMallCnt(fastboxInfo);

				if (mallCnt > 0) {
					mgrService.updateUserMallInfo(fastboxInfo);
				} else {
					mgrService.insertUserMallInfo(fastboxInfo);
				}
			}

			queryResult = "S";
		} catch (Exception e) {
			queryResult = "F";
			logger.error("Exception", e);
		}

		return queryResult;
	}

	/**
	 * Page : 관리자 - 사용자 > 접속 IP 설정 - 등록 URI :
	 * /mngr/acnt/modify/registAllowIp/{userId} Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/modify/registAllowIp/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> userMypageRegistAllowIp(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("allowIp", request.getParameter("param1"));
		parameters.put("userId", userId);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));

		String apiKey = usrService.selectApiKey(parameters.get("userId"));

		if (apiKey.equals("")) {
			result.put("result", "F");
			return result;
		}

		try {
			usrService.insertAllowIp(parameters);
			result.put("result", "S");
			result.put("allowIp", request.getParameter("param1"));
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}

	/**
	 * Page : 관리자 - 사용자 > 접속 IP 설정 - 등록 - API KEY 발급 URI :
	 * /mngr/acnt/modify/apiKeyAllow/{userId} Method : GET
	 */
	@RequestMapping(value = "/mngr/acnt/modify/apiKeyAllow/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> userMypageApiKeyAllow(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", userId);

		String apiKey = usrService.selectApiKey(parameters.get("userId"));

		try {
			if (apiKey.equals("") | apiKey.isEmpty()) {
				StringBuffer temp = new StringBuffer();
				Random rnd = new Random();
				for (int i = 0; i < 32; i++) {
					int rIndex = rnd.nextInt(3);
					switch (rIndex) {
					case 0:
						// a-z
						temp.append((char) ((int) (rnd.nextInt(26)) + 97));
						break;
					case 1:
						// A-Z
						temp.append((char) ((int) (rnd.nextInt(26)) + 65));
						break;
					case 2:
						// 0-9
						temp.append((rnd.nextInt(10)));
						break;
					}
				}
				usrService.insertApiKey(temp.toString(), parameters.get("userId"));
			}
			result.put("result", "S");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}

	/**
	 * Page : 관리자 - 사용자 > 접속 IP 설정 - 삭제 URI :
	 * /mngr/acnt/modify/deleteAllowIp/{userId} Method : POST
	 */
	@RequestMapping(value = "/mngr/acnt/modify/deleteAllowIp/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> userMypageDeleteAllowIp(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model) throws Exception {
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("allowIp", request.getParameter("param1"));
		parameters.put("userId", userId);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));

		try {
			usrService.deleteAllowIp(parameters);
			result.put("result", "S");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "F");
		}
		return result;
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 수정 로직 URI : mngr/acnt/{userId} Method :
	 * PATCH
	 */
	@RequestMapping(value = "/mngr/acnt/delete", method = RequestMethod.GET)
	public String managerDeleteUserInfo(HttpServletRequest request, HttpServletResponse response, Model model,
			ManagerVO userInfoVO, String targetParm) throws Exception {
		String Result = "";
		String referUrl = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/"));
		/* 삭제 동작 */
		mgrService.deleteUserInfo(targetParm);
		Result = "redirect:/mngr/acnt/userList";
		/*
		 * if (referUrl.contains("/indvdList")) { model.addAttribute("targetPage",
		 * "ind"); Result = "redirect:/mngr/acnt/indvdList";
		 * 
		 * } else if (referUrl.contains("/entrpList")) {
		 * model.addAttribute("targetPage", "ent"); Result =
		 * "redirect:/mngr/acnt/entrpList"; }
		 */
		return Result;
	}

	/**
	 * Page : Manager 거래처 관리 - 기업회원 - 회원정보 수정 로직 URI : mngr/acnt/{userId} Method :
	 * PATCH
	 */
	@RequestMapping(value = "/mngr/acnt/pwReset/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public String managerPasswordReset(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId, Model model, ManagerVO userInfoVO) throws Exception {
		String queryResult = "";
		try {
			String defaultPw = "me2uKK!";
			userInfoVO.setUserPw(userInfoVO.encryptSHA256(defaultPw));
			mgrService.resetUserPw(userInfoVO);
			queryResult = "S";
		} catch (Exception e) {
			queryResult = "F";
		}
		return queryResult;
	}

	/**
	 * Page : Manager 단가관리 - nation별 단가 URI : mngr/unit/nationPrice Method : GET
	 */
	@RequestMapping(value = "/mngr/unit/nationPrice", method = RequestMethod.GET)
	public String managerUnitStation(HttpServletRequest request, HttpServletResponse response, Model model,
			ZoneVO zoneVO) throws Exception {
		int curPage = 1;
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("transName", zoneVO.getTransName());
		infoMap.put("zoneCode", zoneVO.getZoneCode());
		infoMap.put("transCode", zoneVO.getTransCode());
		infoMap.put("orgNation", zoneVO.getOrgNation());
		infoMap.put("dstnNation", zoneVO.getDstnNation());
		int totalCount = mgrService.selectTotalCountNation(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		infoMap.put("paging", paging);
		ArrayList<ZoneVO> nationListInfo = mgrService.selectRgstNationList(infoMap);
		model.addAllAttributes(infoMap);
		model.addAttribute("nationListInfo", nationListInfo);
		model.addAttribute("paging", paging);
		return "adm/unit/nation";
	}

	/**
	 * Page : Manager 단가관리 - nation 등록 URI : mngr/unit/registNation Method : GET
	 */
	@RequestMapping(value = "/mngr/unit/registNation", method = RequestMethod.GET)
	public String managerRegistNation(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<TransComVO> transComVO = new ArrayList<TransComVO>();

		transComVO = mgrService.selectTransCom();
		nations = comnService.selectNationCode();
		model.addAttribute("nations", nations);
		model.addAttribute("transCom", transComVO);
		return "adm/unit/nationRegist";
	}

	@RequestMapping(value = "/mngr/unit/registNation", method = RequestMethod.POST)
	@ResponseBody
	public String managerRegistNationPOST(HttpServletRequest request, HttpServletResponse response, Model model,
			ZoneVO zoneVO) throws Exception {
		String result = "F";
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		zoneVO.setWUserId(member.getUsername());
		zoneVO.setWUserIp(request.getRemoteAddr());
		try {
			mgrService.insertTransZone(zoneVO);
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* mgrService.selectTest(zoneVO); */
		return result;
	}

	/**
	 * Page : Manager 단가관리 - nation 등록 URI : mngr/unit/registNation Method : GET
	 */
	@RequestMapping(value = "/mngr/unit/modifyNation", method = RequestMethod.GET)
	public String managerModifyNation(HttpServletRequest request, HttpServletResponse response, Model model,
			ZoneVO zoneVO) throws Exception {
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<TransComVO> transComVO = new ArrayList<TransComVO>();

		transComVO = mgrService.selectTransCom();
		nations = comnService.selectNationCode();
		model.addAttribute("nations", nations);
		model.addAttribute("transCom", transComVO);
		model.addAttribute("legacyInfo", zoneVO);
		return "adm/unit/nationModify";
	}

	@RequestMapping(value = "/mngr/unit/modifyNation", method = RequestMethod.POST)
	@ResponseBody
	public String managerModifyNationPOST(HttpServletRequest request, HttpServletResponse response, Model model,
			ZoneVO zoneVO) throws Exception {
		String result = "F";
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		zoneVO.setWUserId(member.getUsername());
		zoneVO.setWUserIp(request.getRemoteAddr());
		try {
			mgrService.updateTransZone(zoneVO);
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* mgrService.selectTest(zoneVO); */
		return result;
	}

	/*
	 * {userId}", method = RequestMethod.GET) public String
	 * managerModifyAcntEnterprise(HttpServletRequest request, HttpServletResponse
	 * response,
	 * 
	 * @PathVariable("userId") String userId, Model model) throws Exception {
	 * 
	 */

	@RequestMapping(value = "/mngr/unit/optionByTransCom/{targetCode}/{dstnNation}", method = RequestMethod.GET)
	public String managerOptionByTransCom(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("targetCode") String targetCode, @PathVariable("dstnNation") String dstnNation, Model model)
			throws Exception {
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		ArrayList<String> dstnNationList = mgrService.selectOrderListOptionNation(targetCode);
		try {
			optionOrderVO = mgrService.SelectOrderListOption(targetCode, dstnNation);
			optionItemVO = mgrService.SelectOrderItemOption(targetCode, dstnNation);
			expressOrderVO = mgrService.SelectExpressListOption(targetCode, dstnNation);
			expressItemVO = mgrService.SelectExpressItemOption(targetCode, dstnNation);
			dstnNationList.add("DEFAULT");

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("dstnNationList", dstnNationList);
		model.addAttribute("targetCode", targetCode);

		return "adm/unit/optionByTransCom";
	}

	@RequestMapping(value = "/mngr/unit/optionByTransCom/{targetCode}/{dstnNation}", method = RequestMethod.POST)
	@ResponseBody
	public String managerOptionByTransComPost(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("targetCode") String targetCode, @PathVariable("dstnNation") String dstnNation,
			OrderListOptionVO optionOrderVO, OrderListExpOptionVO expressOrderVO, OrderItemOptionVO optionItemVO,
			OrderItemExpOptionVO expressItemVO, Model model) throws Exception {
//		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
//		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();

//		optionOrderVO = mgrService.SelectOrderListOption(targetCode);
//		optionItemVO = mgrService.SelectOrderItemOption(targetCode);
		optionOrderVO.setTargetCode(targetCode);
		optionItemVO.setTargetCode(targetCode);
		expressOrderVO.setTargetCode(targetCode);
		expressItemVO.setTargetCode(targetCode);
		optionOrderVO.setDstnNation(dstnNation);
		optionItemVO.setDstnNation(dstnNation);
		expressOrderVO.setDstnNation(dstnNation);
		expressItemVO.setDstnNation(dstnNation);

		try {
			mgrService.insertOrderListOption(optionOrderVO, targetCode);
			mgrService.insertOrderItemOption(optionItemVO, targetCode);
			mgrService.insertOrderListExpOption(expressOrderVO, targetCode);
			mgrService.insertOrderItemExpOption(expressItemVO, targetCode);
		} catch (Exception e) {
			return "F";
		}

//		model.addAttribute("optionOrderVO", optionOrderVO);
//		model.addAttribute("optionItemVO", optionItemVO);
		return "S";
	}

	@RequestMapping(value = "/mngr/unit/transCom", method = RequestMethod.GET)
	public String managerTransCom(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/unit/transCom";
	}

	@RequestMapping(value = "/mngr/unit/transComLoad", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<TransComVO> managerTransComLoad(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		ArrayList<TransComVO> transComVO = new ArrayList<TransComVO>();
		transComVO = mgrService.selectTransCom();
		for (int i = 0; i < transComVO.size(); i++) {
			if (transComVO.get(i).getChgType().equals("per")) {
				double chgFee = Double.parseDouble(transComVO.get(i).getChgFee()) * 100;
				transComVO.get(i).setChgFee(Double.toString(chgFee));
			}
		}
		return transComVO;
	}

	/**
	 * Page : Manager 단가관리 - 택배회사 관리 URI : mngr/unit/transComDelete Method : POST
	 */
	@RequestMapping(value = "mngr/unit/transComDelete", method = RequestMethod.POST)
	@ResponseBody
	public void managerTransComDelete(HttpServletRequest request, HttpServletResponse response, Model model,
			TransComVO TransComVO) throws Exception {
		mgrService.deleteTransCom(request.getParameter("transCode"));
	}

	/**
	 * Page : Manager 단가관리 - 택배회사 관리 URI : mngr/unit/transComDelete Method : POST
	 */
	@RequestMapping(value = "/mngr/unit/transComUpdate", method = RequestMethod.POST)
	@ResponseBody
	public void managerTransComUpdate(HttpServletRequest request, HttpServletResponse response, Model model,
			TransComVO transComVO) throws Exception {
		transComVO.setWUserId(request.getSession().getAttribute("USER_ID").toString());
		transComVO.setWUserIp(request.getSession().getAttribute("USER_IP").toString());
		transComVO.setOrgStation(request.getSession().getAttribute("ORG_STATION").toString());
		if (transComVO.getChgType().equals("per")) {
			double chgFee = Double.parseDouble(transComVO.getChgFee()) / 100;
			transComVO.setChgFee(Double.toString(chgFee));
		}

		mgrService.updateTransCom(transComVO);
	}

	/**
	 * Page : Manager 단가관리 - 택배회사 관리 URI : mngr/unit/transComInsert Method : POST
	 */
	@RequestMapping(value = "/mngr/unit/transComInsert", method = RequestMethod.POST)
	@ResponseBody
	public void managerTransComInsert(HttpServletRequest request, HttpServletResponse response, Model model,
			TransComVO transComVO) throws Exception {
		transComVO.setWUserId(request.getSession().getAttribute("USER_ID").toString());
		transComVO.setWUserIp(request.getSession().getAttribute("USER_IP").toString());
		transComVO.setOrgStation(request.getSession().getAttribute("ORG_STATION").toString());
		if (transComVO.getChgType().equals("per")) {
			double chgFee = Double.parseDouble(transComVO.getChgFee()) / 100;
			transComVO.setChgFee(Double.toString(chgFee));
		}
		mgrService.transComInsert(transComVO);
	}

	/**
	 * Page : Manager 단가관리 - 배송사별 단가 URI : mngr/unit/shpComPrice Method : GET
	 */
	@RequestMapping(value = "/mngr/unit/shpComPrice", method = RequestMethod.GET)
	public String managerUnitShopCompany(HttpServletRequest request, HttpServletResponse response, Model model,
			PriceVO priceVO) throws Exception {

		int curPage = 1;
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("zoneCode", priceVO.getZoneCode());
		infoMap.put("transCode", priceVO.getTransCode());
		infoMap.put("transName", priceVO.getTransName());

		int totalCount = mgrService.selectTotalCountZone(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		infoMap.put("paging", paging);
		ArrayList<PriceVO> zoneListInfo = mgrService.selectRgstZoneList(infoMap);
		model.addAllAttributes(infoMap);
		model.addAttribute("zoneListInfo", zoneListInfo);
		model.addAttribute("paging", paging);

		return "adm/unit/zone";
	}

	@RequestMapping(value = "/mngr/unit/modifyZone/{transCode}", method = RequestMethod.GET)
	public String managerModifyZone(HttpServletRequest request, HttpServletResponse response, Model model,
			PriceVO priceVO, @PathVariable("transCode") String transCode) throws Exception {
		ArrayList<TransComVO> transComVO = new ArrayList<TransComVO>();
		transComVO = mgrService.selectTransCom();
		priceVO = mgrService.selectZoneOne(priceVO);
		model.addAttribute("transCom", transComVO);
		model.addAttribute("priceVO", priceVO);
		return "adm/unit/zoneModify";
	}

	@RequestMapping(value = "/mngr/unit/modifyZone", method = RequestMethod.POST)
	@ResponseBody
	public String managerModifyZonePOST(HttpServletRequest request, HttpServletResponse response, Model model,
			PriceVO priceVO) throws Exception {
		String result = "F";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		priceVO.setWUserId(member.getUsername());
		priceVO.setWUserIp(request.getRemoteAddr());
		priceVO.setApplyDate(time1);

		try {
			mgrService.updatePrice(priceVO);
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		return result;
	}

	@RequestMapping(value = "/mngr/unit/registZone", method = RequestMethod.GET)
	public String managerRegistZone(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<TransComVO> transComVO = new ArrayList<TransComVO>();
		transComVO = mgrService.selectTransCom();
		model.addAttribute("transCom", transComVO);
		return "adm/unit/zoneRegist";
	}

	@RequestMapping(value = "/mngr/unit/registZone", method = RequestMethod.POST)
	@ResponseBody
	public String managerRegistZonePOST(HttpServletRequest request, HttpServletResponse response, Model model,
			PriceVO priceVO) throws Exception {
		String result = "F";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		priceVO.setWUserId(member.getUsername());
		priceVO.setWUserIp(request.getRemoteAddr());
		priceVO.setApplyDate(time1);

		try {
			mgrService.insertPrice(priceVO);
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		return result;
	}

	/**
	 * Page : Manager 요금안내 - 예치금 관리 URI : mngr/rates/dpstMngmn Method : GET
	 */
	@RequestMapping(value = "/mngr/rates/dpstMngmn", method = RequestMethod.GET)
	public String managerDpstMng(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 요금안내 - 업체별 예치금 현황 URI : mngr/rates/dpstStts Method : GET
	 */
	@RequestMapping(value = "/mngr/rates/dpstStts", method = RequestMethod.GET)
	public String managerDpstStatus(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 요금안내 - 무통장 입금 신청현황 URI : mngr/rates/aplctStts Method : GET
	 */
	@RequestMapping(value = "/mngr/rates/aplctStts", method = RequestMethod.GET)
	public String managerAplctStatus(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 신청서 리스트 - 검품등록 리스트 URI : mngr/aplctList/aplctStts Method : GET
	 */
	@RequestMapping(value = "/mngr/aplctList/inspcList", method = RequestMethod.GET)
	public String managerInspcList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;

		HashMap<String, ArrayList<ShpngListVO>> inspList = new HashMap<String, ArrayList<ShpngListVO>>();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		String transCode = request.getParameter("transCode");
		parameter.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		ArrayList<String> transCodeList = new ArrayList<String>();
		transCodeList = mgrService.selectTransCodeForAdmin((String) parameter.get("wUserId"));

		parameter.put("orderType", "INSP");
		parameter.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		String searchType = "";
		String searchKeywords = "";

		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}

		parameter.put("searchType", searchType);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", request.getParameter("keywords"));
		if (searchType.equals("0")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", searchKeywords);
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", searchKeywords);
		} else if (searchType.equals("1")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("2")) {
			parameter.put("userId", "");
			parameter.put("transName", searchKeywords);
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("3")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("4")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", "");
		} else if (searchType.equals("5")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", searchKeywords);
		} else {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}
		int totalCount = mgrService.selectShpngListCount(parameter);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		// paging 끝

		parameter.put("paging", paging);

		if (transCode != null) {
			parameter.put("transCode", transCode);
		}
		try {
			inspList = mgrService.selectShpngList(parameter, "INSP");
		} catch (Exception e) {
			// TODO: handle exception
			inspList = null;
		}

		model.addAttribute("transCodeList", transCodeList);
		model.addAttribute("inspList", inspList);
		model.addAttribute("paging", paging);
		return "adm/aplctList/list/inspcList";
	}

	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/deleteinspList", method = RequestMethod.POST)
	public String managerDeleteinspList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] nnoList = request.getParameter("delTarget").split(",");
		String[] targetUserList = request.getParameter("delTargetUser").split(",");
		managerDeleteOrder(nnoList, targetUserList, member.getUsername(), request.getRemoteAddr());
		return "redirect:/mngr/aplctList/inspcList";
	}

	/**
	 * Page : Manager 신청서 리스트 - 사입등록 리스트 URI : mngr/aplctList/prchsList Method : GET
	 */
	@RequestMapping(value = "/mngr/aplctList/prchsList", method = RequestMethod.GET)
	public String managerPrchsList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/aplctList/list/prchs/prchsList";
	}

	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/shpngList", method = RequestMethod.GET)
	public String managerShpngList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> shpngList = new HashMap<String, ArrayList<ShpngListVO>>();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("wUserId", member.getUsername());
		parameter.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameter.put("orderType", "NOMAL");
		String searchType = "";
		String searchKeywords = "";

		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}

		parameter.put("searchType", searchType);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", request.getParameter("keywords"));
		if (searchType.equals("0")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", searchKeywords);
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", searchKeywords);
		} else if (searchType.equals("1")) {
			parameter.put("userId", searchKeywords);
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("2")) {
			parameter.put("userId", "");
			parameter.put("transName", searchKeywords);
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("3")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", searchKeywords);
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		} else if (searchType.equals("4")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", searchKeywords);
			parameter.put("cneeZip", "");
		} else if (searchType.equals("5")) {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", searchKeywords);
		} else {
			parameter.put("userId", "");
			parameter.put("transName", "");
			parameter.put("orderNo", "");
			parameter.put("hawbNo", "");
			parameter.put("cneeZip", "");
		}

		int totalCount = mgrService.selectShpngListCount(parameter);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		// paging 끝

		parameter.put("paging", paging);

		try {
			shpngList = mgrService.selectShpngList(parameter, "NOMAL");
		} catch (Exception e) {
			// TODO: handle exception
			shpngList = null;
		}
		model.addAttribute("shpngList", shpngList);
		model.addAttribute("paging", paging);
		return "adm/aplctList/list/shpngList";
	}

	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/modifyAdminOne", method = RequestMethod.GET)
	public String managerModifyAdminOne(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserOrderListVO userOrder = new UserOrderListVO();
		userOrder = comnService.selectUserRegistOrderOne(request.getParameter("nno"));

		userOrder.dncryptData();
		ArrayList<UserOrderItemVO> userOrderItem = new ArrayList<UserOrderItemVO>();
		userOrderItem = mgrService.selectUserRegistOrderItemOne(userOrder);
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		ArrayList<String> userOrgNation = mgrService.selectUserOrgNation(userOrder.getUserId());
		ArrayList<UserTransComVO> userDstnNation = usrService.selectUserDstnNation(userOrder.getUserId());

		String types = request.getParameter("types");
		parameters.put("userOrgNation", userOrgNation);
//		ArrayList<String> userOrgStation = usrService.selectNationArrayToStation(parameters);
		ArrayList<NationVO> userOrgStation = comnService.selectStationToNationList(parameters);
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = comnService.selectNationCode();
		ArrayList<HashMap<String, Object>> taxTypeList = new ArrayList<HashMap<String, Object>>();
		taxTypeList = mgrService.selectTaxTypeList();

		String transCom = request.getParameter("transCode");

		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		String dstnNation = userOrder.getDstnNation();
		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);
		
		
		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("targetCode", transCom);

		model.addAttribute("nationList", nationList);
		model.addAttribute("types", types);
		model.addAttribute("userOrder", userOrder);
		model.addAttribute("userOrderItem", userOrderItem);
		model.addAttribute("userOrgStation", userOrgStation);
		model.addAttribute("userDstnNation", userDstnNation);
		model.addAttribute("taxTypeList", taxTypeList);

		return "adm/aplctList/modify/modifyAdminOneAll";
	}

	/**
	 * Page : Manager Mawb 도착지 체크 URI : mngr/rls/mawbListArrChk Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/outRegistMawb", method = RequestMethod.GET)
	public String mawbListArrChk(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/rls/mawbList/outRegistMawb";
	}

	@RequestMapping(value = "/mngr/rls/inRegistMawb", method = RequestMethod.GET)
	public String inRegistMawb(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "adm/rls/mawbScan/inRegistMawb";
	}

	@RequestMapping(value = "/mngr/rls/mawbWareOut", method = RequestMethod.GET)
	public String mawbWareOut(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> mawbArrInfo = new HashMap<String, Object>();
		mawbArrInfo = mgrService.selectMawbArrInfo(request.getParameter("target"));

		model.addAttribute("mawbArrInfo", mawbArrInfo);
		return "adm/rls/mawbList/mawbWareOut";
	}

	@RequestMapping(value = "/mngr/rls/mawbHawbInfoChk", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> mawbHawbInfoChk(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HashMap<String, Object> mawbArrInfo = new HashMap<String, Object>();
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			mawbArrInfo = mgrService.selectMawbArrInfo(request.getParameter("target"));

			if (mawbArrInfo == null) {
				result.put("result", "warehouse에 입고되지 않았습니다.");
			} else {
				result.put("result", "SUCCESS");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "Fail - 전산팀에 문의하세요");
		}

		return result;
	}

	@RequestMapping(value = "/mngr/rls/mawbWareOut", method = RequestMethod.POST)
	public String mawbWareOutPost(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> mawbArrInfo = new HashMap<String, Object>();
		mawbArrInfo.put("nno", request.getParameter("nno"));
		mawbArrInfo.put("hawbNo", request.getParameter("hawbNo"));
		mawbArrInfo.put("inspector", request.getParameter("name"));
		mawbArrInfo.put("remarks", request.getParameter("remarks"));
		mawbArrInfo.put("userDate", request.getParameter("userDate"));
		mawbArrInfo.put("chTrkCom", request.getParameter("chTrkCom"));
		mawbArrInfo.put("chTrkNo", request.getParameter("chTrkNo"));
		String uDate = mgrService.selectUdate(mawbArrInfo);
		if (uDate.substring(0, 4).equals("1900")) {
			mawbArrInfo.put("uDate", "");
		} else {
			mawbArrInfo.put("uDate", request.getParameter("uDate"));
		}

		mgrService.updateMawbArr(mawbArrInfo);

		model.addAttribute("mawbArrInfo", mawbArrInfo);
		return "adm/rls/mawbList/mawbWareOut";
	}

//	@RequestMapping(value = "/mngr/rls/mawbDetail", method = RequestMethod.GET)
//	public String managerMawDetail(HttpServletRequest request, HttpServletResponse response, Model model)
//			throws Exception {
//		model.addAttribute("targetMawb", request.getParameter("target"));
//		String hawbCnt = mgrService.hawbCount(request.getParameter("target"));
//		model.addAttribute("hawbCnt", hawbCnt);
//		return "adm/rls/mawbScan/mawbField";
//	}
	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/modifyAdminOne", method = RequestMethod.POST)
	@ResponseBody
	public String managerModifyAdminOnePOST(HttpServletRequest request, HttpServletResponse response, Model model,
			UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		String result = "F";
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
		ArrayList<OrderInspVO> userOrderListRegacy = new ArrayList<OrderInspVO>();
		userOrderListRegacy = mgrService.selectOrderInspcItem(userOrderList.getNno());

		Date time = new Date();
		String time1 = format1.format(time);
		/* orderList */
		/* userOrderList.setDstnNation(userOrderList.getDstnNation()); */
		userOrderList.setDstnStation(userOrderList.getDstnNation());
		userOrderList.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		if (request.getParameter("types").equals("shpng"))
			userOrderList.setOrderType("NOMAL");
		else
			userOrderList.setOrderType("INSP");

		/* orderItem */
		/* userOrderItems = request.getParameter(name) */
		userOrderList.setUserId(userOrderListRegacy.get(0).getUserId());
		userOrderList.setWUserIp(request.getRemoteAddr());
		userOrderList.setWUserId(member.getUsername());
		userOrderList.setWDate(time1);

		try {
			String[] orderNno = null;
			userOrderList.encryptData();
			mgrService.updateUserOrderList(userOrderList, userOrderItemList);
			result = "S";
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		/* usrService.insertTest(); */
		return result;
	}

	/**
	 * Page : USER 배송대행 단일 신청페이지 추가 아이템 공통 URI : /mngr/aplctList/itemList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/itemList", method = RequestMethod.POST)
	public String userItemList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int cnt = Integer.parseInt(request.getParameter("cnt")) + 1;
		model.addAttribute("cnt", cnt);
		String transCom = request.getParameter("selectTrans");
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		String dstnNation = request.getParameter("dstnNation");

		optionOrderVO = mgrService.SelectOrderListOption(transCom, dstnNation);
		optionItemVO = mgrService.SelectOrderItemOption(transCom, dstnNation);
		expressOrderVO = mgrService.SelectExpressListOption(transCom, dstnNation);
		expressItemVO = mgrService.SelectExpressItemOption(transCom, dstnNation);

		model.addAttribute("optionOrderVO", optionOrderVO);
		model.addAttribute("optionItemVO", optionItemVO);
		model.addAttribute("expressOrderVO", expressOrderVO);
		model.addAttribute("expressItemVO", expressItemVO);
		model.addAttribute("targetCode", transCom);

		return "adm/aplctList/itemList";
	}

	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/deleteShpngList", method = RequestMethod.POST)
	public String managerDeleteShpngList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String referer = (String) request.getHeader("referer");
		String returnUrl = "";

		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] nnoList = request.getParameter("delTarget").split(",");
		String[] targetUserList = request.getParameter("delTargetUser").split(",");
		managerDeleteOrder(nnoList, targetUserList, member.getUsername(), request.getRemoteAddr());

		if (referer.contains("takeinOrderListAll")) {
			returnUrl = "redirect:/mngr/takein/takeinOrderListAll";
		} else if (referer.contains("shpngList")) {
			returnUrl = "redirect:/mngr/aplctList/shpngList";
		}
		return returnUrl;
	}

	public void managerDeleteOrder(String[] nnoList, String[] targetUserList, String userId, String userIp)
			throws Exception {
		mgrService.deleteOrderList(nnoList, targetUserList, userId, userIp);
	}

	/**
	 * Page : Manager 신청서 리스트 - 삭제 리스트 URI : mngr/aplctList/dltList Method : GET
	 */
	@RequestMapping(value = "/mngr/aplctList/dltList", method = RequestMethod.GET)
	public String managerDltList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, ArrayList<ShpngListVO>> delList = new HashMap<String, ArrayList<ShpngListVO>>();

		try {

			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orgStation", orgStation);

			int curPage = 1;
			int totalCount = mgrService.selectRecoveryListCnt(params);

			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}

			PagingVO paging = new PagingVO(curPage, totalCount, 10, 50);

			params.put("paging", paging);

			delList = mgrService.selectRecoveryList(params);

			model.addAttribute("paging", paging);

		} catch (Exception e) {
			// TODO: handle exception
			delList = null;
		}
		model.addAttribute("delList", delList);
		return "adm/aplctList/list/dltList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/dltList2", method = RequestMethod.GET)
	public String managerDltList2(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<ShpngListVO> delList = new ArrayList<ShpngListVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		params.put("orgStation", orgStation);
		params.put("userId", request.getParameter("userId"));
		params.put("orderNo", request.getParameter("orderNo"));
		
		try {
			int curPage = 1;
			int totalCount = mgrService.selectDeleteOrderListCnt(params);
			
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			PagingVO paging = new PagingVO(curPage, totalCount, 10, 100);
			params.put("paging", paging);
			
			delList = mgrService.selectDeleteOrderList(params);
			
			for(int i = 0; i < delList.size(); i++) {
				delList.get(i).dncryptData();
			}
			model.addAttribute("paging", paging);
			model.addAttribute("params", params);
			model.addAttribute("delList", delList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "adm/aplctList/list/dltList2";
	}

	/**
	 * Page : Manager 신청서 리스트 - 운송장 등록 리스트 URI : mngr/aplctList/wyblList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/aplctList/recoveryList", method = RequestMethod.POST)
	public String managerRecoveryList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] dnoList = request.getParameter("recTarget").split(",");
		mgrService.recoveryOrderList(dnoList, member.getUsername(), request.getRemoteAddr());
		return "redirect:/mngr/aplctList/dltList";
	}

	/**
	 * Page : Manager 입고작업 - 검수작업 URI : mngr/order/inspc Method : GET
	 */
	@RequestMapping(value = "/mngr/order/orderInspc", method = RequestMethod.GET)
	public String managerOrderInspc(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/order/inspc/orderInspc";
	}

	/**
	 * Page : Manager 입고작업 - 검수 - Ajax - 상세정보 표출
	 * 
	 * 
	 */
	@RequestMapping(value = "/mngr/order/orderInspcDetail", method = RequestMethod.POST)
	public String managerOrderInspcDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		
		OrderInspListVO orderInspListVO = new OrderInspListVO();
		OrderInspVO orderInspVO = new OrderInspVO();
		StationDefaultVO stationDefault = new StationDefaultVO();
//		ArrayList<TESTVO> test = new ArrayList<TESTVO>();
//		test = mgrService.selectTestSS();
		int count = 0;
		orderInspListVO.setCneeName("");
		orderInspListVO.setItemDetail("");
		orderInspListVO.setTrkNo("");
		orderInspListVO.setHawbNo("");
		orderInspListVO.setOrderNo("");
		orderInspListVO.setUserId("");
		orderInspListVO.setNno(request.getParameter("housText"));
		
		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		orderInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		
		String searchType = request.getParameter("cneeType");
		String searchKeywords = request.getParameter("housText");

		orderInfo.put("searchType", searchType);
		orderInfo.put("searchKeywords", searchKeywords);

		orderInfo.put("orderInspListVO", orderInspListVO);
		orderInfo.put("orderInspVO", orderInspVO);
//		if(!request.getParameter("nno").isEmpty()) {
//			orderInfo.put("nno", request.getParameter("nno"));
//		}

		count = mgrService.selectOrderInspcCnt(orderInfo);
		int totalCount = count;
		if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		orderInfo.put("paging", paging);

		stationDefault = mgrService.selectStationDefaultCond((String) request.getSession().getAttribute("USER_ID"));

		/* orderInspcItem = mgrService.selectOrderInspcItem(orderInspcList); */
		if (count == 1) {
			// 메모 조회 분리
			ArrayList<OrderInspListOneVO> orderInsp = new ArrayList<OrderInspListOneVO>();
			orderInsp = mgrService.selectOrderInspc(orderInfo);
			model.addAttribute("stationDefault", stationDefault);
			model.addAttribute("orderInspList", orderInsp.get(0));
			return "adm/order/inspc/orderInspcDetail";
		} else if (count == 0) {
			model.addAttribute("trkNo", request.getParameter("housText"));

			return "adm/order/inspc/orderInspcUnRegist";

		} else {
			ArrayList<OrderInspListVO> orderInspList = new ArrayList<OrderInspListVO>();
			orderInspList = mgrService.selectOrderInspcList(orderInfo);
			model.addAttribute("orderInspList", orderInspList);
			model.addAttribute("paging", paging);
			model.addAttribute("housText", request.getParameter("housText"));
			return "adm/order/inspc/orderInspcList";
		}
	}

	/**
	 * Page : Manager 입고작업 - 검수 - Ajax - 상세정보 표출
	 * 
	 * 
	 */
	@RequestMapping(value = "/mngr/order/orderInspcDetail2", method = RequestMethod.GET)
	public String managerOrderInspcDetail2(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		OrderInspListVO orderInspListVO = new OrderInspListVO();
		OrderInspVO orderInspVO = new OrderInspVO();
		int count = 0;
		ArrayList<OrderInspListOneVO> orderInspList = new ArrayList<OrderInspListOneVO>();
		if (request.getParameter("cneeTypeM").equals("1")) {
			orderInspListVO.setCneeName(request.getParameter("cneeTextM"));
		} else if (request.getParameter("cneeTypeM").equals("2")) {
			orderInspListVO.setCneeHp(request.getParameter("cneeTextM"));
		} else if (request.getParameter("cneeTypeM").equals("3")) {
			orderInspListVO.setCneeAddr(request.getParameter("cneeTextM"));
		}

		if (request.getParameter("housTypeM").equals("1")) {
			orderInspVO.setWDate(request.getParameter("housTextM"));
		} else if (request.getParameter("housTypeM").equals("2")) {
			orderInspVO.setHsCode(request.getParameter("housTextM"));
		} else if (request.getParameter("housTypeM").equals("3")) {
			orderInspVO.setItemCnt(request.getParameter("housTextM"));
		}

		orderInspVO.setItemDetail(request.getParameter("itemDetailSchM"));
		orderInspListVO.setCneeZip(request.getParameter("cneeZipM"));
		orderInspListVO.setHawbNo(request.getParameter("hawbNoSchM"));

		HashMap<String, Object> orderInfo = new HashMap<String, Object>();
		orderInfo.put("orderInspListVO", orderInspListVO);
		orderInfo.put("orderInspVO", orderInspVO);

		count = mgrService.selectOrderInspcCnt(orderInfo);
		orderInspList = mgrService.selectOrderInspc(orderInfo);
		for (int index = 0; index < orderInspList.size(); index++)
			orderInspList.get(index).dncryptData();
		/* orderInspcItem = mgrService.selectOrderInspcItem(orderInspcList); */
		if (count == 1) {
			model.addAttribute("orderInspList", orderInspList.get(0));
			return "adm/order/inspc/orderInspcDetail";
		} else {
			model.addAttribute("orderInspList", orderInspList);
			return "adm/order/inspc/orderInspcList";
		}
	}

	/**
	 * Page : Manager 입고작업 - 재고등록 로직
	 * 
	 * 
	 */
	@RequestMapping(value = "/mngr/order/orderInspcWhProcess", method = RequestMethod.POST)
	public String orderInspcWhProcess(HttpServletRequest request, MultipartHttpServletRequest multi,
			HttpServletResponse response, Model model, MultipartFile file, RedirectAttributes redirect)
			throws Exception {
		ArrayList<StockResultVO> targetStockList = new ArrayList<StockResultVO>();
		ArrayList<StockResultVO> reslutStockList = new ArrayList<StockResultVO>();
		targetStockList = mgrService.orderInspcWhProcess(request, multi, file);

		ArrayList<StockVO> stckInfos = new ArrayList<StockVO>();
		if (targetStockList.size() != 0) {
			/* createPdfreal(request, response, model, targetStockList); */
			ArrayList<String> test = new ArrayList<String>();
			for (int i = 0; i < targetStockList.size(); i++) {
				if (!test.contains(targetStockList.get(i).getGroupIdx())) {
					test.add(targetStockList.get(i).getGroupIdx());
					reslutStockList.add(targetStockList.get(i));
				}
			}
			for (int j = 0; j < test.size(); j++)
				stckInfos.addAll(mgrService.selectStockByGrpIdx(test.get(j)));
		} else {
			return "adm/order/inspc/orderInspc";
		}
		String chkCount = mgrService.selectChkCntStock(targetStockList.get(0).getNno());
		redirect.addFlashAttribute("stckInfos", stckInfos);
		redirect.addFlashAttribute("targetStockList", reslutStockList);
		redirect.addFlashAttribute("chkCount", chkCount);

		return "redirect:/mngr/order/orderInspResult";
	}

	/**
	 * Page : Manager 입고작업 - 미확인 재고 등록 로직
	 */

	@RequestMapping(value = "/mngr/order/unRegistWhProcess", method = RequestMethod.POST)
	public String unRegistWhProcess(HttpServletRequest request, MultipartHttpServletRequest multi,
			HttpServletResponse response, Model model, MultipartFile file, RedirectAttributes redirect)
			throws Exception {
		ArrayList<StockVO> stckInfos = new ArrayList<StockVO>();
		ArrayList<StockResultVO> reslutStockList = new ArrayList<StockResultVO>();
		StockResultVO tmpResultStock = new StockResultVO();
		stckInfos = mgrService.unRegistWhProcess(request, multi, file);
		tmpResultStock.setGroupIdx(stckInfos.get(0).getGroupIdx());
		reslutStockList.add(tmpResultStock);

		redirect.addFlashAttribute("stckInfos", stckInfos);
		redirect.addFlashAttribute("targetStockList", reslutStockList);
		return "redirect:/mngr/order/orderInspResult";
	}

	@RequestMapping(value = "/mngr/order/orderInspResult")
	public String managerOrderorderInspcResult(HttpServletResponse response, Model model) throws Exception {
		/* , @RequestParam("stckInfos") ArrayList<StockVO> stockResultList */

		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		return "adm/order/inspc/orderInspResult";
	}

	@RequestMapping(value = "/mngr/order/pdfPopup", method = RequestMethod.GET)
	public String managerOrderorderInspcPdfPopup(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		model.addAttribute("nno", request.getParameter("nno"));
		model.addAttribute("groupIdx", request.getParameter("groupIdx"));
		createPdfreal2(request, response, model);
		return "adm/order/inspc/pdfPopup";
	}

	@RequestMapping(value = "/mngr/order/pdfInspAddPopup", method = RequestMethod.POST)
	public String managerOrderorderInspcAddPdfPopup(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		model.addAttribute("stockNo", request.getParameterValues("stockNo"));
		createPdfStock(request, response, model);
		return "adm/order/inspc/pdfPopup";
	}

	@RequestMapping(value = "/mngr/order/orderInspcSearchId", method = RequestMethod.GET)
	public String managerOrderInspcSearchId(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ManagerVO temp = new ManagerVO();
		if (!request.getParameter("idArea").equals("")) {
			temp = mgrService.selectUserInfo(request.getParameter("idArea"));
			if (temp == null) {
				model.addAttribute("tempResult", "F");
			} else {
				model.addAttribute("tempResult", "T");
			}
		} else {
			model.addAttribute("tempResult", "F");
		}

		ArrayList<ManagerVO> idList = mgrService.selectInfo(request.getParameter("idArea"));
		model.addAttribute("idList", idList);
		model.addAttribute("searchIdVal", request.getParameter("idArea"));
		return "adm/order/inspc/orderInspcSearchId";
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/orderRcpt", method = RequestMethod.GET)
	public String managerOrderRcpt(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/order/rcpt/orderRcpt";
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/orderRcptList", method = RequestMethod.GET)
	public String managerOrderRcptList(HttpServletRequest request, HttpServletResponse response, Model model,
			OrderListVO parameters) throws Exception {
		ArrayList<OrderListVO> orderList = new ArrayList<OrderListVO>();
		parameters.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		parameters.setOrderType("NOMAL");
		if (parameters.getStartDate().equals("") && parameters.getEndDate().equals("")) {
			LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			String firstDateString = firstDate.format(DateTimeFormatter.ISO_DATE);
			firstDateString = firstDateString.replaceAll("-", "");
			LocalDate lastdDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			String lastDateString = lastdDate.format(DateTimeFormatter.ISO_DATE);
			lastDateString = lastDateString.replaceAll("-", "");
			parameters.setStartDate(firstDateString);
			parameters.setEndDate(lastDateString);
		}
		orderList = mgrService.selectOrderRcptList(parameters);
		for (int index = 0; index < orderList.size(); index++) {
			orderList.get(index).dncryptData();
		}

		HashMap<String, String> rtnVal = new HashMap<String, String>();
		rtnVal.put("userId", parameters.getUserId());
		rtnVal.put("hawbNo", parameters.getHawbNo());
		rtnVal.put("cneeName", parameters.getCneeName());
		rtnVal.put("startDate", parameters.getStartDate());
		rtnVal.put("endDate", parameters.getEndDate());

		model.addAttribute("parameter", rtnVal);
		model.addAttribute("orderList", orderList);
		return "adm/order/rcpt/orderRcptList";
	}

	/**
	 * Page : Manager 입고작업 - 패스트박스 무게 업데이트 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/updateWeightFB", method = RequestMethod.POST)
	public String updateWeightFastbox(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		fastboxApi.getFastboxWeight();
		return "redirect:/mngr/order/orderRcptList";
	}

	/**
	 * Page : Manager 입고작업 - 용성 무게 업데이트 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/updateWeightYS", method = RequestMethod.POST)
	public String updateWeightYS(HttpServletRequest request, HttpServletResponse response, Model model,
			OrderListVO parameters) throws Exception {
		// mgrAramexService.sendAramexWeightFtp();
		// smtpService.weightUpdate();
		ysApi.getYslWeight();
		// efsApi.getEfsWeight();
		return "redirect:/mngr/order/orderRcptList";
	}

	/**
	 * Page : Manager 입고작업 - 메일테스트 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/sendMailTest", method = RequestMethod.POST)
	public String sendMailTest(HttpServletRequest request, HttpServletResponse response, Model model,
			OrderListVO parameters) throws Exception {
		// cseApi.createShipment("202106151030393168DFB");
		// comnService.comnBlApply("202202031632494455AFJ", "EMS", "piu8909",
		// "127.0.0.1", "default");
		// emsApi.makeEmsParameter("202202031632494455AFJ");
		// gtsApi.insertPostalCode();
		// gtsApi.setThread();
		// emsApi.createEmsPdf();
		// System.out.println("ASDA");
		// ozonApi.parcelSchdule();
//		gtsApi.createShipment("202106151030383164DDI");
		// fdxApi.getTrackingFedex();
//		ApiOrderListVO apiOrderList = new ApiOrderListVO();
//		ArrayList<ApiOrderItemListVO> apiOrderItemList = new ArrayList<ApiOrderItemListVO>();
//		SecurityKeyVO originKey = new SecurityKeyVO();
//		String nno = "202105171521425177JBE";
//		apiOrderList = mgrService.selectOrderListAramex(nno);
//		apiOrderList.setSymmetryKey(originKey.getSymmetryKey());
//		apiOrderList.dncryptData();
//		apiOrderItemList = mgrService.selectOrderItemAramexMember(nno);
//		if (apiOrderList.getCneeEmail().equals(""))
//			apiOrderList.setCneeEmail("-");
//
//		if (apiOrderList.getShipperEmail().equals(""))
//			apiOrderList.setShipperEmail("-");
//
//		for (int i = 0; i < apiOrderItemList.size(); i++) {
//			apiOrderItemList.get(i).setNno(apiOrderList.getNno());
//		}
//
//		ShipmentCreationResponse resultAramex = new ShipmentCreationResponse();
//		apiServiceImpl.updateAramexApi(apiOrderList, apiOrderItemList, request);
		// smtpService.sendMailToEfs();
		// mgrService.sendMailEfs();
		return "redirect:/mngr/order/orderRcptList";
	}

	@RequestMapping(value = "/mngr/order/sendMailTest2", method = RequestMethod.POST)
	public String sendMailTest2(HttpServletRequest request, HttpServletResponse response, Model model,
			OrderListVO parameters) throws Exception {
		gtsApi.setThreadStop();
		return "redirect:/mngr/order/orderRcptList";
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/order/orderRcptListPost", method = RequestMethod.POST)
	public String managerOrderRcptListPost(HttpServletRequest request, HttpServletResponse response, Model model,
			OrderListVO parameters, RedirectAttributes redirectAttr) throws Exception {
		String[] targetNno = request.getParameterValues("checkboxs");
		ProcedureVO rstVal2 = new ProcedureVO();
		rstVal2 = mgrService.execOrderRcptList(targetNno, request);

		redirectAttr.addFlashAttribute("results", rstVal2);
		return "redirect:/mngr/order/orderRcptList";
	}

	/**
	 * Page : Manager 입고작업 - 입고 로직 Method : POST
	 */
	@RequestMapping(value = "/mngr/order/orderRcptRegist", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> managerOrderRcptRegist(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HawbVO hawbVo = new HawbVO();
		HawbChkVO hawbChkVO = new HawbChkVO();
		int startStr = 0;
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String transCode = "";
		if (request.getParameter("hawbIn").length() > 15
				&& !request.getParameter("hawbIn").toString().toUpperCase().startsWith("FB")
				&& !request.getParameter("hawbIn").toString().toUpperCase().startsWith("YT")) {
			startStr = request.getParameter("hawbIn").length() - 15;
			transCode = mgrService.selectSekoHawb(
					request.getParameter("hawbIn").substring(startStr, request.getParameter("hawbIn").length()));
			if (transCode == null || transCode.equals("")) {
				startStr = request.getParameter("hawbIn").length() - 20;
				transCode = mgrService.selectSekoHawb(
						request.getParameter("hawbIn").substring(startStr, request.getParameter("hawbIn").length()));
				if (transCode == null || transCode.equals("")) {
					startStr = request.getParameter("hawbIn").length() - 22;
					transCode = mgrService.selectSekoHawb(request.getParameter("hawbIn").substring(startStr,
							request.getParameter("hawbIn").length()));
					if (transCode == null || transCode.equals("")) {
						startStr = request.getParameter("hawbIn").length() - 34;
						transCode = mgrService.selectSekoHawb(request.getParameter("hawbIn").substring(startStr,
								request.getParameter("hawbIn").length()));
					}
				}
			}
		}

		if (transCode.equals("SEK")) {
			hawbVo.setHawbNo(
					request.getParameter("hawbIn").substring(startStr, request.getParameter("hawbIn").length()));
		} else {
			hawbVo.setHawbNo(request.getParameter("hawbIn"));
		}

//		String transCode = comnService.selectTransCodeFromHawb(request.getParameter("hawbIn").substring(1,request.getParameter("hawbIn").length()-1));
//		
//		if(transCode != null) {
//			if(transCode.equals("OCS")) {
//				hawbVo.setHawbNo(request.getParameter("hawbIn").substring(1,request.getParameter("hawbIn").length()-1));
//			}else {
//				hawbVo.setHawbNo(request.getParameter("hawbIn"));
//			}
//		}else {
//			hawbVo.setHawbNo(request.getParameter("hawbIn"));
//		}

		hawbVo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		hawbVo.setOrgStation(mgrService.selectAdminStation(hawbVo.getWUserId()));
		hawbVo.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		String result = "";
		String status = "";
		try {
			HashMap<String, String> hawbChk = new HashMap<String, String>();
			hawbChkVO = mgrService.execNomalHawbChk(hawbVo);

			if (hawbChkVO.getStatus().equals("SUCCESS")) {
				// hawbChkVO.getRstNno();
				result = "S";
			} else {
				result = hawbChkVO.getRstMsg();
				status = hawbChkVO.getStatus();

			}
			System.out.println(hawbChkVO.getRstMsg());
			System.out.println(hawbChkVO.getCode());
			System.out.println(hawbChkVO.getStatus());
		} catch (Exception e) {
			result = "F";
		}
		resultMap.put("result", result);
		resultMap.put("hawbNo", hawbVo.getHawbNo());
		resultMap.put("status", status);
		return resultMap;
	}

	@RequestMapping(value = "/mngr/order/weightForm", method = RequestMethod.GET)
	public String managerOrderWeightForm(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		System.out.println("NNO =====>" + request.getParameter("nno"));
		model.addAttribute("nno", request.getParameter("nno"));
		return "adm/order/rcpt/weightForm";
	}

	@RequestMapping(value = "/mngr/order/weightForm", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> managerOrderWeightFormPost(HttpServletRequest request, HttpServletResponse response,
			Model model, OrderRcptVO orderRcpt) throws Exception {
		String result = "F";
		HashMap<String, String> resultMap = new HashMap<String, String>();
		ProcedureVO procedure = new ProcedureVO();

		VolumeVO volume = new VolumeVO();
		orderRcpt.setWta(request.getParameter("wta"));
		orderRcpt.setWtc(request.getParameter("wtc"));
		String transCode = "";
		if (request.getParameter("hawbIn").length() == 30) {
			transCode = mgrService.selectSekoHawb(
					request.getParameter("hawbIn").substring(10, request.getParameter("hawbIn").length()));
		}

		if (transCode.equals("SEK")) {
			orderRcpt.setHawbNo(request.getParameter("hawbIn").substring(10, request.getParameter("hawbIn").length()));
		} else {
			orderRcpt.setHawbNo(request.getParameter("hawbIn"));
		}
		orderRcpt.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		orderRcpt.setOrgStation(mgrService.selectAdminStation(orderRcpt.getWUserId()));
		orderRcpt.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		volume.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		volume.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		volume.setDimUnit(request.getParameter("dimUnit"));
		volume.setWtUnit(request.getParameter("wtUnit"));
		Map<String, String[]> parameterMaps = request.getParameterMap();

		try {
			result = mgrService.execNomalHawbIn(orderRcpt, volume, parameterMaps, request);
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}

		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/orderWeight Method : GET
	 */
	@RequestMapping(value = "/mngr/order/orderWeight", method = RequestMethod.GET)
	public String managerOrderWeight(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<OrderWeightVO> orderWeightExcelVO = new ArrayList<OrderWeightVO>();

		orderWeightExcelVO = mgrService
				.selectOrderWeightExcel((String) request.getSession().getAttribute("ORG_STATION"));

		model.addAttribute("orderWeightExcelData", orderWeightExcelVO);
		return "adm/order/rcpt/orderWeight";
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/registOrderWeightList Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/order/registOrderWeightList", method = RequestMethod.POST)
	@ResponseBody
	public String managerRegistOrderWeightList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String result = "F";

		try {

			if (request.getParameterValues("targets") == null) {
				return "N";
			}

			result = mgrService.execOrderWeightList(request.getParameterValues("targets"), request);

		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		return result;
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/delOrderWeightList Method : GET
	 */
	@RequestMapping(value = "/mngr/order/delOrderWeightList", method = RequestMethod.POST)
	@ResponseBody
	public String managerDelOrderWeightList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String result = "F";
		try {
			if (request.getParameterValues("targets") == null) {
				return "N";
			}
			result = mgrService.delOrderWeightList(request.getParameterValues("targets"), request);
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		return result;
	}

	/**
	 * Page : Manager 입고작업 - 입고 작업 URI : mngr/order/weightExcelUpload Method : GET
	 */
	@RequestMapping(value = "/mngr/order/weightExcelUpload", method = RequestMethod.POST)
	@ResponseBody
	public String managerWeightExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			MultipartHttpServletRequest multi) throws Exception {
		System.out.println("**********************");
		String result = "F";
		String types = request.getParameter("formSelect");
		HashMap<String, String> parameters = new HashMap<String, String>();
		String switchOrgStation = (String) request.getSession().getAttribute("ORG_STATION");
		parameters.put("orgStation", switchOrgStation);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", (String) request.getSession().getAttribute("USER_IP"));
		try {
			switch (switchOrgStation) {
			case "441":
				result = mgrService.insertOrderWeightExcel(multi, request, parameters);
				break;
			case "082":
				result = mgrService.insertOrderWeightExcel(multi, request, parameters);
				break;
			case "213":
//					result = mgrService.insertExcelData(multi, request,member.getUsername(), "NOMAL");
				result = "등록된 양식이 없습니다.";
				break;
			default:
				result = "등록된 양식이 없습니다.";
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}

		return result;
	}

	/**
	 * Page : Manager 사입 - 상품코드 관리 URI : mngr/prchs/prdctCode Method : GET
	 */
	@RequestMapping(value = "/mngr/prchs/prdctCode", method = RequestMethod.GET)
	public String managerPrdctCode(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/prchs/prdctCode";
	}

	/**
	 * Page : Manager 사입 - 상품 입고 관리 URI : mngr/prchs/prdctRcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/prchs/prdctRcpt", method = RequestMethod.GET)
	public String managerPrdctRcpt(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/prchs/prdctRcpt";
	}

	/**
	 * Page : Manager 사입 - 상품 재고 리스트 URI : mngr/prchs/stockList Method : GET
	 */
	@RequestMapping(value = "/mngr/prchs/stockList", method = RequestMethod.GET)
	public String managerStockList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/prchs/stockList";
	}

	/**
	 * Page : Manager 출고 - 사입 발송 관리 URI : mngr/rls/prchsDlvry Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/prchsDlvry", method = RequestMethod.GET)
	public String managerPrchsDlvry(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/rls/prchsDlvry";
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbScanAply", method = RequestMethod.GET)
	public String managerMawbScanAply(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		/*
		 * ArrayList<MawbVO> mawbVO = new ArrayList<MawbVO>();
		 * 
		 * mawbVO = mgrService.selectMawbList(); model.addAttribute("mawbVO", mawbVO);
		 */
		return "adm/rls/mawbScan/mawbScanAply";
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbAply", method = RequestMethod.GET)
	public String managerMawbAply(HttpServletRequest request, HttpServletResponse response, Model model,
			String transCode) throws Exception {
		ArrayList<HawbListVO> hawbList = new ArrayList<HawbListVO>();
		ArrayList<HashMap<String, Object>> transComInfo = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> blList = new ArrayList<String>();
		String orgStation = request.getSession().getAttribute("ORG_STATION").toString();
		String userId = (String) request.getSession().getAttribute("USER_ID");

		model.addAttribute("targetMawb", request.getParameter("target"));
		hawbList = mgrService.selectRegistHawbList(userId, transCode);
		transComInfo = mgrService.selectHawbTrans(orgStation);
		double totalWeightWta = 0;
		double totalWeightWtv = 0;
		for (int index = 0; index < hawbList.size(); index++) {
			hawbList.get(index).dncryptData();
			totalWeightWta = totalWeightWta + Double.parseDouble(hawbList.get(index).getWta());
			totalWeightWtv = totalWeightWtv + Double.parseDouble(hawbList.get(index).getWtc());
		}
		blList = mgrService.selectDistinctBlList();

		model.addAttribute("userId", userId);
		model.addAttribute("blList", blList);
		model.addAttribute("transCode", transCode);
		model.addAttribute("transComInfo", transComInfo);
		model.addAttribute("totalWeightWta", totalWeightWta);
		model.addAttribute("totalWeightWtv", totalWeightWtv);
		model.addAttribute("hawbList", hawbList);
		model.addAttribute("orgStation",orgStation);
		return "adm/rls/mawbApply/mawbApply";
	}

	@RequestMapping(value = "/mngr/rls/mawbTransCom", method = RequestMethod.GET)
	public String managerMawbTransCom(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ManagerVO temp = new ManagerVO();
		ArrayList<TransComVO> transComList = mgrService.selectTransCom();
		model.addAttribute("transComList", transComList);

		return "adm/rls/mawbApply/mawbTransCom";
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY - mawbLoad (읽기) URI : mngr/rls/mawbLoad
	 * Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbLoad", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<MawbVO> managerMawbLoad(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<MawbVO> mawbVO = new ArrayList<MawbVO>();
		String parameter = (String) request.getSession().getAttribute("ORG_STATION");
		mawbVO = mgrService.selectMawbList(parameter);
		return mawbVO;
	}

	@RequestMapping(value = "/mngr/rls/mawbLoad2", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<MawbVO> managerMawbLoad2(HttpServletRequest request, HttpServletResponse response, Model model,
			MawbVO mawbVOne) throws Exception {
		ArrayList<MawbVO> mawbVO = new ArrayList<MawbVO>();
		String parameter = (String) request.getSession().getAttribute("ORG_STATION");
		MawbVO parameters = new MawbVO();
		parameters.setOrgStation(parameter);
		parameters.setMawbNo(request.getParameter("mawbNo"));
		parameters.setFltNo(request.getParameter("fltNo"));

		String depDate = "";
		String arrDate = "";

		if (request.getParameter("depDate") != null) {
			depDate = request.getParameter("depDate").replaceAll("-", "");
		} else {
			LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			String firstDateString = firstDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			depDate = firstDateString;
		}

		if (request.getParameter("arrDate") != null) {
			arrDate = request.getParameter("arrDate").replaceAll("-", "");
		} else {
			LocalDate lastdDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			String lastDateString = lastdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			arrDate = lastDateString;

		}

		parameters.setDepDate(depDate);
		parameters.setArrDate(arrDate);

		mawbVO = mgrService.selectMawbList2(parameters);
		return mawbVO;
	}

	@RequestMapping(value = "/mngr/rls/mawbLoad3", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<MawbVO> managerMawbLoad3(HttpServletRequest request, HttpServletResponse response, Model model,
			MawbVO mawbVOne) throws Exception {
		ArrayList<MawbVO> mawbVO = new ArrayList<MawbVO>();
		String parameter = (String) request.getSession().getAttribute("ORG_STATION");
		MawbVO parameters = new MawbVO();
		parameters.setOrgStation(parameter);
		parameters.setMawbNo(request.getParameter("mawbNo"));
		parameters.setFltNo(request.getParameter("fltNo"));

		String depDate = "";
		String arrDate = "";

		if (request.getParameter("depDate") != null) {
			depDate = request.getParameter("depDate").replaceAll("-", "");
		}

		if (request.getParameter("arrDate") != null) {
			arrDate = request.getParameter("arrDate").replaceAll("-", "");
		}
		parameters.setDepDate(depDate);
		parameters.setArrDate(arrDate);

		mawbVO = mgrService.selectMawbList3(parameters);
		return mawbVO;
	}

	/**
	 * @name expLicenceLoad
	 * @param request
	 * @param response
	 * @param model
	 * @param mawbVOne
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mngr/rls/expLicenceLoad", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<ExpLicenceListVO> managerExpLicenceLoad(HttpServletRequest request, HttpServletResponse response,
			Model model, MawbVO mawbVOne) throws Exception {
		ArrayList<ExpLicenceListVO> expLicenceListVO = new ArrayList<ExpLicenceListVO>();
		String parameter = (String) request.getSession().getAttribute("ORG_STATION");
		ExpLicenceListVO parameters = new ExpLicenceListVO();
		parameters.setOrgStation(parameter);
		parameters.setMawbNo(request.getParameter("mawbNo"));
		parameters.setFltNo(request.getParameter("fltNo"));

		String depDate = "";
		String arrDate = "";

		if (request.getParameter("depDate") != null) {
			depDate = request.getParameter("depDate").replaceAll("-", "");
		}

		if (request.getParameter("arrDate") != null) {
			arrDate = request.getParameter("arrDate").replaceAll("-", "");
		}
		parameters.setDepDate(depDate);
		parameters.setArrDate(arrDate);

		expLicenceListVO = mgrService.selectLicenceList(parameters);
		return expLicenceListVO;
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbInsert", method = RequestMethod.POST)
	@ResponseBody
	public void managerMawbInsert(HttpServletRequest request, HttpServletResponse response, Model model, MawbVO mawbVO)
			throws Exception {
		mawbVO.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		mawbVO.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		mawbVO.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		mgrService.insertMawb(mawbVO);
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbUpdate", method = RequestMethod.POST)
	@ResponseBody
	public void managerMawbUpdate(HttpServletRequest request, HttpServletResponse response, Model model, MawbVO mawbVO)
			throws Exception {
		mawbVO.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		mawbVO.setWUserIp((String) request.getSession().getAttribute("USER_IP"));

		mawbVO.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		mgrService.updateMawb(mawbVO);
		mgrService.updateHawbMawb(mawbVO);
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbDelete", method = RequestMethod.POST)
	@ResponseBody
	public void managerMawbmawbDelete(HttpServletRequest request, HttpServletResponse response, Model model,
			MawbVO mawbVO) throws Exception {
		mgrService.deleteMawbOne(request.getParameter("mawbNo"));
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbDetail", method = RequestMethod.GET)
	public String managerMawDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		model.addAttribute("targetMawb", request.getParameter("target"));
		String hawbCnt = mgrService.hawbCount(request.getParameter("target"));
		model.addAttribute("hawbCnt", hawbCnt);
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = member.getUsername();

		model.addAttribute("userId", userId);
		return "adm/rls/mawbScan/mawbField";
	}

	/**
	 * Page : Manager 출고 - MAWB APPLY URI : mngr/rls/mawbHawbRegist Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbHawbRegist", method = RequestMethod.GET)
	public String managerMawbHawbRegist(HttpServletRequest request, HttpServletResponse response, Model model,
			String transCode) throws Exception {
		ArrayList<HawbListVO> hawbList = new ArrayList<HawbListVO>();
		model.addAttribute("targetMawb", request.getParameter("target"));
		hawbList = mgrService.selectRegistHawbList((String) request.getSession().getAttribute("USER_ID"), transCode);
		for (int index = 0; index < hawbList.size(); index++) {
			hawbList.get(index).dncryptData();
		}
		model.addAttribute("hawbList", hawbList);
		return "adm/rls/mawbApply/hawbSelField";
	}

	/**
	 * Page : Manager 출고 - MAWB APPLY URI : mngr/rls/mawbHawbRegist Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbHawbRegist", method = RequestMethod.POST)
	@ResponseBody
	public String managerMawbHawbRegisPost(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String[] hawbList = request.getParameterValues("targetData[]");
		String mawbNo = request.getParameter("mawbNo");
		String result = "";
		try {
			result = mgrService.insertHawbMawb(mawbNo, hawbList, request);
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}

		return result;
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbScanAply Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbHawbInsert", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> managerMawbHawbInsert(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HawbVO hawbVo = new HawbVO();
		HashMap<String, String> result = new HashMap<String, String>();
		String transCode = "";
		String results = "";

		/* String result = ""; */
		if (request.getParameter("target").length() == 30) {
			transCode = mgrService.selectSekoHawb(
					request.getParameter("target").substring(10, request.getParameter("target").length()));
		}

		if (transCode.equals("SEK")) {
			hawbVo.setHawbNo(request.getParameter("target").substring(10, request.getParameter("target").length()));
		} else {
			hawbVo.setHawbNo(request.getParameter("target"));
		}

		
		String bagNo = "";
		if (request.getParameter("bagNo") != null || !request.getParameter("bagNo").equals("")) {
			bagNo = request.getParameter("bagNo");
		}
		hawbVo.setBagNo(bagNo);
		hawbVo.setMawbNo(request.getParameter("targetMawb"));
		hawbVo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		hawbVo.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		try {
			result.put("result", mgrService.insertHawb(hawbVo));
//			if(result.get("result").equals("SUCESS")) {
//				int cnt = mgrService.expLicenceChk(hawbVo);
//				if (cnt != 0) {
//					mgrService.makeExplicence(hawbVo);
//				}
//			}

			// mawb apply 시 FB인 건은 배송 지시 API를 호출 (출고 지시)
			/*
			 * if (result.get("result").equals("SUCCESS")) { if (transCode.equals("FB")) {
			 * ProcedureVO rtnVal = new ProcedureVO(); rtnVal =
			 * fastboxApi.requestFastboxDelivery(request.getParameter("target"),
			 * request.getParameter("targetMawb"));
			 * //fastboxApi.requestDelivery(request.getParameter("target"),
			 * request.getParameter("targetMawb")); if
			 * (rtnVal.getRstStatus().equals("FAIL")) { String[] hawbList = new String[1];
			 * hawbList[0] = request.getParameter("target").toString(); String userId =
			 * (String) request.getSession().getAttribute("USER_ID"); String userIp =
			 * (String) request.getRemoteAddr(); results =
			 * mgrService.execMawbCancle(hawbList, userId, userIp); throw new Exception(); }
			 * } }
			 */
			if (result.get("result").toString().equals("FAIL")) {
				String[] hawbList = new String[1];
				hawbList[0] = request.getParameter("target").toString();
				String userId = (String) request.getSession().getAttribute("USER_ID");
				String userIp = (String) request.getRemoteAddr();
				results = mgrService.execMawbCancle(hawbList, userId, userIp);
				throw new Exception();
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "Insert Fail");
		}
		String hawbCnt = mgrService.hawbCount(hawbVo.getMawbNo()).toString();
		result.put("hawbCnt", hawbCnt);

		return result;
	}

	@RequestMapping(value = "/mngr/rls/mawbHawbInserUp", method = RequestMethod.POST)
	@ResponseBody
	public String managerMawbHawbInsertUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "F";
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			String mawbNo = request.getParameter("mawbNo");
			String hawbNo = request.getParameter("hawbNo");
			String bagNo = request.getParameter("bagNo");

			params.put("mawbNo", mawbNo);
			params.put("hawbNo", hawbNo);
			params.put("bagNo", bagNo);

			mgrService.updateMawbHawb(params);

			result = "S";
		} catch (Exception e) {
			result = "F";
		}

		return result;
	}

	/**
	 * Page : Manager 출고 - MAWB Scan APPLY URI : mngr/rls/mawbHawbInsertArr Method :
	 * POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbHawbInsertArr", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> managerMawbHawbInsertArr(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HawbVO hawbVo = new HawbVO();
		HashMap<String, String> result = new HashMap<String, String>();
		String transCode = "";
		/* String result = ""; */
		if (request.getParameter("target").length() == 30) {
			transCode = mgrService.selectSekoHawb(
					request.getParameter("target").substring(10, request.getParameter("target").length()));
		}

		if (transCode.equals("SEK")) {
			hawbVo.setHawbNo(request.getParameter("target").substring(10, request.getParameter("target").length()));
		} else {
			hawbVo.setHawbNo(request.getParameter("target"));
		}

		hawbVo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		hawbVo.setWUserIp((String) request.getSession().getAttribute("USER_IP"));
		hawbVo.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		try {
			HashMap<String, Object> checkData = new HashMap<String, Object>();
			checkData = mgrService.checkMawbArr(hawbVo.getHawbNo());
			if (!checkData.get("alreadyInsert").equals("N")) {
				result.put("result", "이미 입고된 HAWB NO 입니다.");
				return result;
			} else if (checkData.get("hawbNoYn").equals("N")) {
				result.put("result", "등록되지 않은 HAWB NO 입니다.");
				return result;
			}

			mgrService.insertMawbArr(hawbVo);
			result.put("result", "SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
			result.put("result", "Insert Fail");
		}
		return result;
	}

	/**
	 * Page : Manager 출고 - 검품 재고 URI : mngr/rls/inspcStock Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/inspcStock", method = RequestMethod.GET)
	public String managerInspcStock(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		ArrayList<InspStockListVO> inspStockList = new ArrayList<InspStockListVO>();

		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("itemDetail", request.getParameter("prdctNameSch"));
		parameterInfo.put("cneeZip", request.getParameter("adresSch"));
		parameterInfo.put("trkNo", request.getParameter("trackNoSch"));
		parameterInfo.put("wUserId", member.getUsername());
		String orgStation = mgrService.selectAdminStation(member.getUsername());
		parameterInfo.put("orgStation", orgStation);
		int totalCount = mgrService.selectTotalCountStock(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		parameterInfo.put("paging", paging);

		inspStockList = mgrService.selectInspStockOrderInfo(parameterInfo);
		for (int index = 0; index < inspStockList.size(); index++) {
			inspStockList.get(index).dncryptData();
		}

		model.addAttribute("inspStockList", inspStockList);
		model.addAttribute("paging", paging);
		return "adm/rls/inspcStock/inspcStock";
	}

	/**
	 * Page : Manager 출고 - 검품 재고 출고 URI : mngr/rls/inspcStockOut Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/inspcStockOut", method = RequestMethod.GET)
	public String managerInspcStockOut(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String referUrl = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/"));
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		if (referUrl.contains("inspcStock")) {
			model.addAttribute("returnPage", "/mngr/rls/inspcStock");
		} else if (referUrl.contains("orderInspResult")) {
			model.addAttribute("returnPage", "/mngr/order/orderInspc");
		}

		return "adm/rls/inspcStock/inspcStockOut";
	}

	/**
	 * Page : Manager 출고 - 검품 재고 개수 체크 URI : mngr/rls/inspcStockOutChk Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/inspcStockOutChk", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerInspcStockOutChk(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HashMap<String, Object> rtnVal = new HashMap<String, Object>();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ProcedureVO execRtn = new ProcedureVO();
		parameter.put("stockNo", request.getParameter("stockNo"));
		parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		execRtn = mgrService.execSpWhoutStock(parameter);
		rtnVal.put("qryRtn", execRtn);
		if (!"SUCCSESS".equals(execRtn.getRstStatus())) {
			rtnVal.put("result", "F");
		} else {
			rtnVal.put("result", "S");
		}
		return rtnVal;
	}

	/**
	 * Page : Manager 출고 - 검품 재고 리셋 URI : mngr/rls/inspcStockOutReset Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/inspcStockOutReset", method = RequestMethod.GET)
	public String managerInspcStockOutReset(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("stockNo", request.getParameter("stockNo"));
		parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));

		mgrService.deleteStockOut(parameter);

		return "redirect:/mngr/rls/inspcStockOut";
	}

	@RequestMapping(value = "/mngr/rls/inspcStockOutDetail", method = RequestMethod.POST)
	public String managerInspcStockOutDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ArrayList<InspStockOutVO> stockOut = new ArrayList<InspStockOutVO>();
		String targetSubNo = "";
		parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		stockOut = mgrService.selectStockOutTarget(parameter);
		targetSubNo = mgrService.selectTargetSubNo(request.getParameter("stockNo"));
		ArrayList<InspStockOutVO> stockRack = new ArrayList<InspStockOutVO>();
		stockRack = mgrService.selectStockRackInfo(parameter);
		model.addAttribute("stockRack", stockRack);

		model.addAttribute("inspStockOut", stockOut);
		model.addAttribute("scanSubNo", targetSubNo);

		return "adm/rls/inspcStock/inspcStockOutDetail";
	}

	@RequestMapping(value = "/mngr/rls/registStockOutVolume", method = RequestMethod.POST)
	@ResponseBody
	public ProcedureVO managerRegistStockOutVolume(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		Map<String, String[]> parameterMaps = request.getParameterMap();
		ProcedureVO rtnVal = new ProcedureVO();
		rtnVal = mgrService.execStockHawbIn(parameterMaps, request, response);
		String transCode = mgrService.selectTransCodeInStock(request.getParameter("nno"));
		// String rtnss = mgrService.managerPrintInspcHawb(request);

		rtnVal.setRstTransCode(transCode);

		return rtnVal;
	}

	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : mngr/rls/cancleInspStock Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/cancleInspStock", method = RequestMethod.POST)
	public String managerCancleInspcStock(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, String> cancleInspInfo = new HashMap<String, String>();
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cancleInspInfo.put("stockNo", request.getParameter("stockNo"));
		cancleInspInfo.put("groupIdx", request.getParameter("groupIdx"));
		cancleInspInfo.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		cancleInspInfo.put("userIp", (String) request.getSession().getAttribute("USER_IP"));
		mgrService.execStockDel(cancleInspInfo);

		return "redirect:/mngr/rls/inspcStock";
	}

	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : mngr/rls/cancleInspStock Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/cancleInspStockScan", method = RequestMethod.POST)
	@ResponseBody
	public String managerCancleInspcStockScan(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, String> cancleInspInfo = new HashMap<String, String>();
		String rtnString = "";
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StockVO tempStockVO = new StockVO();
		tempStockVO = mgrService.selectStockByNo2(request.getParameter("stockNo"));

		cancleInspInfo.put("stockNo", tempStockVO.getStockNo());
		cancleInspInfo.put("groupIdx", tempStockVO.getGroupIdx());
		cancleInspInfo.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		cancleInspInfo.put("userIp", (String) request.getSession().getAttribute("USER_IP"));
		try {
			mgrService.execStockDel(cancleInspInfo);
			rtnString = "S";
		} catch (Exception e) {
			// TODO: handle exception
			rtnString = "F";
		}

		return rtnString;
	}

	/**
	 * Page : Manager 출고 - 검품 재고 출고 후 송장 출력 URI : mngr/rls/printInspcHawb Method :
	 * POST
	 */
	@RequestMapping(value = "/mngr/rls/printInspcHawb", method = RequestMethod.GET)
	public void managerPrintInspcHawb(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		// TODO Auto-generated method stub
		ProcedureVO resultVO = new ProcedureVO();
		String resultMsg = new String();
		String nno = request.getParameter("nno");
		// 1. nno로 transcom check 하고, aci면 sagawa/ aramex면 aramex 동작 수행.
		String userTransCom = mgrService.selectUserTransComByNno(nno);
		String hawbNoChk = mgrService.selectHawbNnoCheck(nno);
		HashMap<String, Object> userInfo = mgrService.selectUserIdByNno(nno);
		String userId = userInfo.get("userId").toString();
		String userIp = userInfo.get("userIp").toString();
		ArrayList<String> orderNnoList = new ArrayList<String>();
		orderNnoList.add(nno);
		// userTransCom = "ACI";

		switch (userTransCom) {
		case "ACI":
			if (hawbNoChk.contains("ADD_")) {
				// 송장 재발급
				String[] targetNno = new String[1];
				targetNno[0] = nno;
				ProcedureVO tempVO = new ProcedureVO();
				tempVO = mgrService.execAddBlApply(nno, (String) request.getSession().getAttribute("USER_ID"),
						(String) request.getSession().getAttribute("USER_IP"));
				if (tempVO.getRstStatus().equals("FAIL")) {
					mgrService.deleteInspcStockOutCzFail(nno);
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('송장 등록에 실패하였습니다.'); </script>");
					out.flush();
				} else {
					resultVO.setRstMsg("등록되었습니다.");
					// comnService.sagawaPdf(request, response, orderNnoList, userTransCom);
				}
			} else {
				comnService.savePdf(nno, userTransCom, userId, userIp);
				// comnService.sagawaPdf(request, response, orderNnoList, userTransCom);
			}
			break;
		case "ARA":
			/* 송장발급 */

			if (hawbNoChk.contains("ADD_")) {
				resultMsg = mgrService.excuteAramexHawb(request, nno);
				if (resultMsg.equals("F")) {
					mgrService.deleteInspcStockOutCzFail(nno);
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('송장 등록에 실패하였습니다.'); </script>");
					out.flush();
				} else {
					comnService.aramexPdf(request, response, orderNnoList, "");
				}
			} else {
				comnService.aramexPdf(request, response, orderNnoList, "");
			}
			// 프린트 pdf가야지
			break;
		default:
			if (hawbNoChk.contains("ADD_")) {
				// 송장 재발급
				String[] targetNno = new String[1];
				targetNno[0] = nno;
				ProcedureVO tempVO = new ProcedureVO();
				tempVO = mgrService.execAddBlApply(nno, (String) request.getSession().getAttribute("USER_ID"),
						(String) request.getSession().getAttribute("USER_IP"));
				if (tempVO.getRstStatus().equals("FAIL")) {
					mgrService.deleteInspcStockOutCzFail(nno);
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('송장 등록에 실패하였습니다.'); </script>");
					out.flush();
				} else {
					resultVO.setRstMsg("등록되었습니다.");
					// comnService.sagawaPdf(request, response, orderNnoList, userTransCom);
				}
			} else {
				comnService.savePdf(nno, userTransCom, userId, userIp);
				comnService.loadPdf(request, response, orderNnoList, userTransCom);
				// comnService.sagawaPdf(request, response, orderNnoList, userTransCom);
			}
			break;
		}

	}

	/**
	 * Page : Manager 출고 - 재고 취소 스캔 URI : mngr/rls/inspcStockScan Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/inspcStockScan", method = RequestMethod.GET)
	public String managerInspcStockScan(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/rls/inspcStock/inspcStockScan";
	}

	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : mngr/rls/popupMsg Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/popupMsg", method = RequestMethod.GET)
	public String managerPopupMsg(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("nno", request.getParameter("nno"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));
		parameters.put("subNo", request.getParameter("subNo"));

		msgHis = mgrService.selectMsgHist(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", parameters);

		return "adm/rls/inspcStock/popupMsgHis";
	}

	/**
	 * Page : Manager 출고 - 검품 재고 입고취소 URI : mngr/rls/mgrMsg Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/mgrMsg", method = RequestMethod.POST)
	public String managerMgrMsg(HttpServletRequest request, HttpServletResponse response, Model model,
			StockMsgVO msgInfo) throws Exception {
		ArrayList<StockMsgVO> msgHis = new ArrayList<StockMsgVO>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		msgInfo.setMsgDiv("MSG");
		msgInfo.setAdminYn("Y");
		msgInfo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
		msgInfo.setWUserIp(request.getRemoteAddr());

		parameters.put("nno", request.getParameter("nno"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));

		mgrService.insertMsgInfo(msgInfo);

		msgHis = mgrService.selectMsgHist(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", parameters);

		return "adm/rls/inspcStock/popupMsgHis";
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/mawbList Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbList", method = RequestMethod.GET)
	public String managerMawbList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HawbVO ftpCount = new HawbVO();
		ftpCount = mgrService.selectFtpCount((String) request.getSession().getAttribute("ORG_STATION"));
		model.addAttribute("ftpCount", ftpCount);
		return "adm/rls/mawbList/mawbList";
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/mawbList Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbListArr", method = RequestMethod.GET)
	public String managerMawbListArr(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HawbVO ftpCount = new HawbVO();
		ftpCount = mgrService.selectFtpCount((String) request.getSession().getAttribute("ORG_STATION"));
		model.addAttribute("ftpCount", ftpCount);
		return "adm/rls/mawbList/mawbListArr";
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/mawbListField Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbListField", method = RequestMethod.GET)
	public String managerMawbListField(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String mawbNo = request.getParameter("target");
		ArrayList<HawbVO> hawbVO = new ArrayList<HawbVO>();
		ArrayList<HashMap<String, String>> bagNoList = new ArrayList<HashMap<String, String>>();

		try {

			bagNoList = mgrService.selectBagNoList(mawbNo);
			model.addAttribute("bagNoList", bagNoList);

			hawbVO = mgrService.selectHawbList(mawbNo);
			for (int index = 0; index < hawbVO.size(); index++) {
				hawbVO.get(index).dncryptData();
			}
			model.addAttribute("hawbVoList", hawbVO);
			model.addAttribute("mawbNo", mawbNo);
			model.addAttribute("adminId", (String) request.getSession().getAttribute("USER_ID"));
		} catch (Exception e) {
			logger.error("Exception", e);
			e.getMessage();
		}

		return "adm/rls/mawbList/mawbListField";
	}

	@RequestMapping(value = "/mngr/rls/mawbListBagField", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerMawbListBagField(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> hawbList = new ArrayList<HashMap<String, Object>>();

		String bagNo = request.getParameter("bagNo");
		String mawbNo = request.getParameter("mawbNo");

		params.put("mawbNo", mawbNo);

		if (bagNo.equals("all")) {
			params.put("bagNo", "all");
		} else {
			params.put("bagNo", bagNo);
		}

		hawbList = mgrService.selectHawbListByBagNo(params);
		result.put("hawbList", hawbList);

		return result;
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/mawbListField Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbListFieldArr", method = RequestMethod.GET)
	public String managerMawbListFieldArr(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String mawbNo = request.getParameter("target");
		ArrayList<HawbVO> hawbVO = new ArrayList<HawbVO>();
		hawbVO = mgrService.selectHawbListArr(mawbNo);

		ArrayList<HashMap<String, String>> bagNoList = new ArrayList<HashMap<String, String>>();

		bagNoList = mgrService.selectBagNoList(mawbNo);
		model.addAttribute("bagNoList", bagNoList);

		for (int index = 0; index < hawbVO.size(); index++) {
			hawbVO.get(index).dncryptData();
		}
		model.addAttribute("hawbVoList", hawbVO);
		model.addAttribute("mawbNo", mawbNo);
		return "adm/rls/mawbList/mawbListFieldArr";
	}

	/**
	 */
	@RequestMapping(value = "/mngr/rls/expLicenceListField", method = RequestMethod.GET)
	public String managerExpLicenceListField(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String mawbNo = request.getParameter("target");
		ArrayList<HawbVO> hawbVO = new ArrayList<HawbVO>();
		// hawbVO = mgrService.selectHawbList(mawbNo);
		hawbVO = mgrService.selectExpFieldList(mawbNo);
		/*
		for (int index = 0; index < hawbVO.size(); index++) {
			hawbVO.get(index).dncryptData();
		}*/
		model.addAttribute("hawbVoList", hawbVO);
		model.addAttribute("mawbNo", mawbNo);
		return "adm/rls/expLicence/expLicenceField";
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "/mngr/rls/expLicence", method = RequestMethod.POST)
	@ResponseBody
	public void managerExpLicence(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String printType = request.getParameter("formType");
		HashMap<String, String> results = new HashMap<String, String>();
		String[] targetTmp = request.getParameter("targetInfos").split(",");

		if ("".equals(printType) || printType == null || "undefined".equals(printType))
			printType = comnService.selectTransComByNno(targetTmp[0]);

		ArrayList<String> orderNnoList = new ArrayList<String>(Arrays.asList(targetTmp));
		String rtn = "";
		String val = "";
		/*
		if (printType.equals("YSL")) {
			for (int i = 0; i < orderNnoList.size(); i++) {
				String chk = mgrService.selectExpLicenceYslChk(orderNnoList.get(i));
				if (!chk.equals("")) {
					rtn = ysApi.fnMakeYSUpdateExpLicenceNoJson(orderNnoList.get(i));
				}
			}
		} else if (printType.equals("EFS")) {

		}*/
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "/mngr/rls/TEST123", method = RequestMethod.POST)
	@ResponseBody
	public void TEST123(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String printType = request.getParameter("formType");
		HashMap<String, String> results = new HashMap<String, String>();
		String[] targetTmp = request.getParameter("targetInfos").split(",");

		if ("".equals(printType) || printType == null || "undefined".equals(printType))
			printType = comnService.selectTransComByNno(targetTmp[0]);

		ArrayList<String> orderNnoList = new ArrayList<String>(Arrays.asList(targetTmp));
		String rtn = "";
		String val = "";
		if (printType.equals("YSL")) {
			for (int i = 0; i < orderNnoList.size(); i++) {
				rtn = ysApi.makeYongsungJson(orderNnoList.get(i));
			}
		} else if (printType.equals("EFS")) {

		}
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "/mngr/rls/downExpLicence", method = RequestMethod.POST)
	@ResponseBody
	public String managerDownExpLicence(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String[] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String>(Arrays.asList(targetTmp));
		String temps = mgrService.makeExpLicenceExcel(request, response, orderNnoList);
		return "";
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "/mngr/rls/downEfsInfo", method = RequestMethod.POST)
	@ResponseBody
	public String managerDownEfsInfo(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String[] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String>(Arrays.asList(targetTmp));
		String temps = mgrService.makeEfsExcel(request, response, orderNnoList);
		return "";
	}

	/**
	 * 수출신고자료 다운로드
	 */
	@RequestMapping(value = "/mngr/rls/downExpLicenceInfo", method = RequestMethod.POST)
	@ResponseBody
	public String managerDownExpLicenceInfo(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String[] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String>(Arrays.asList(targetTmp));
		String temps = mgrService.makeDownExpLicenceInfo(request, response, orderNnoList);
		return "";
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "mngr/rls/downMawbExcel", method = RequestMethod.POST)
	@ResponseBody
	public String managerDownMawbExcel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String temps = mgrService.makeMawbExcel(request, response);
		return "";
	}

	/**
	 * 임시 임시
	 */
	@RequestMapping(value = "/mngr/rls/upExpLicence", method = RequestMethod.POST)
	@ResponseBody
	public void managerUpExpLicence(HttpServletRequest request, HttpServletResponse response, Model model,
			MultipartHttpServletRequest multi) throws Exception {
		String excelRoot = realFilePath + "excel/expLicence/";
		mgrService.insertExpLicence(multi, request, excelRoot);

	}

	/**
	 * Page : Zone 관리 - Zone별 Price 등록 URI : mngr/unit/shpComPrice Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/zoneExcelUpload", method = RequestMethod.POST)
	@ResponseBody
	public void managerZoneExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			MultipartHttpServletRequest multi) throws Exception {
		String excelRoot = realFilePath + "excel/expLicence/";
		mgrService.insertZoneExcelUpload(multi, request, excelRoot);

	}

	/**
	 * Page : Zone 관리 - Zone별 Price 등록 URI : mngr/unit/shpComPrice Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/zoneExcelUploadIndividual/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public void managerZoneExcelUploadIndividual(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("userId") String userId, MultipartHttpServletRequest multi) throws Exception {
		String excelRoot = realFilePath + "excel/expLicence/";
		mgrService.zoneExcelUploadIndividual(multi, request, excelRoot, userId);

	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/expLicenceList Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/expLicenceList", method = RequestMethod.GET)
	public String managerExpLicenceList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HawbVO ftpCount = new HawbVO();
		ftpCount = mgrService.selectExpLicenceCount((String) request.getSession().getAttribute("ORG_STATION"));
		model.addAttribute("ftpCount", ftpCount);
		return "adm/rls/expLicence/expLicenceList";
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/mawbManiDown Method : GET
	 */
	@RequestMapping(value = "/mngr/rls/mawbManiDown", method = RequestMethod.GET)
	public String managerMawbManiDown(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String result = mgrService.selectManifastMawb(request.getParameter("targets"), response, request);
		return "adm/rls/mawbList/mawbList";
	}

	/**
	 * Page : Manager 출고 - MAWB bList - 출고 취소 URI : mngr/rls/mawbHawbReturn Method :
	 * POST
	 */
	@RequestMapping(value = "/mngr/rls/mawbHawbReturn", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> managerMawbHawbReturn(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		String mawbNo = request.getParameter("mawbNo");
		String[] hawbList = request.getParameterValues("target[]");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String result = "F";
		ArrayList<HawbVO> hawbVO = new ArrayList<HawbVO>();

		try {
			result = mgrService.execMawbCancle(hawbList, (String) request.getSession().getAttribute("USER_ID"),
					(String) request.getSession().getAttribute("USER_IP"));
		} catch (Exception e) {
			// TODO: handle exception
			result = "F";
		}
		resultMap.put("resultMsg", result);
		resultMap.put("mawbNo", mawbNo);

		return resultMap;
	}
	
	@RequestMapping(value = "/mngr/rls/updateBagNo", method = RequestMethod.POST)
	public void updateBagNo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String bagNo = request.getParameter("bagNo");
		String[] hawbList = request.getParameterValues("target[]");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		HashMap<String,Object> params = new HashMap<String,Object>();

		for (int i = 0; i < hawbList.length; i++) {
			params = new HashMap<String,Object>();
			params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			params.put("bagNo", bagNo);
			params.put("hawbNo", hawbList[i]);
			mgrService.updateBagNo(params);
		}
	}
	
	@RequestMapping(value = "/mngr/rls/fastboxSendDelivery", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerSendDelivery(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String mawbNo = request.getParameter("mawbNo");
		String[] hawbList = request.getParameterValues("target[]");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String result = "F";
		ArrayList<HawbVO> hawbVO = new ArrayList<HawbVO>();
		
		try {
			result = mgrService.fastboxSendDelivery(hawbList, mawbNo);
		} catch (Exception e) {
			result = "F";
		}
		
		resultMap.put("resultMsg", result);
		resultMap.put("mawbNo", mawbNo);
		return resultMap;
	}

	@RequestMapping(value = "/mngr/rls/requestFastboxDevliery", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mgrRequestFbDelivery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mawbNo = request.getParameter("mawbNo");
		HashMap<String, Object> rst = new HashMap<>();
		if (mawbNo == null) {
			rst.put("code", "F");
			rst.put("msg", "요청데이터인 Mawb No를 찾을 수 없습니다.");
		} else {
			rst = fastboxApi.requestShipping(mawbNo);
		}
		
		return rst;
	}
	
	/**
	 * Page : Manager WMS - Rack 관리 URI : mngr/wms/rackMngmn Method : GET
	 */
	@RequestMapping(value = "/mngr/wms/rackMngmn", method = RequestMethod.GET)
	public String managerRackMngmn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager WMS - Rack 재고 현황 URI : mngr/wms/rackStts Method : GET
	 */
	@RequestMapping(value = "/mngr/wms/rackStts", method = RequestMethod.GET)
	public String managerRackStatus(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager WMS - Rack 재고 체크 URI : mngr/wms/rackChk Method : GET
	 */
	@RequestMapping(value = "/mngr/wms/rackChk", method = RequestMethod.GET)
	public String managerRackCheck(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 발송 - 발송 목록 URI : mngr/send/sendList Method : GET
	 */
	@RequestMapping(value = "/mngr/send/sendList", method = RequestMethod.GET)
	public String managerSendList(HttpServletRequest request, HttpServletResponse response, Model model, SendVO search)
			throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		ArrayList<HashMap<String, Object>> transCodeList = new ArrayList<HashMap<String, Object>>();
		try {

			transCodeList = invService.selectTransCodeList(orgStation);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int curPage = 1;
		search.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		int totalCount = mgrService.selectSendListCount(search);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);

		search.setPaging(paging);

		ArrayList<SendVO> sendList = mgrService.selectSendList(search);

		for (int i = 0; i < sendList.size(); i++) {
			sendList.get(i).dncryptData();
		}

		model.addAttribute("orgStation", orgStation);
		model.addAttribute("transCodeList", transCodeList);
		model.addAttribute("paging", paging);
		model.addAttribute("sendList", sendList);
		model.addAttribute("search", search);
		return "adm/send/sendList";
	}
	
	@RequestMapping(value = "/mngr/send/v1/sendList", method = RequestMethod.GET)
	public String managerSendListV2(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		try {
			
			/*
			HashMap<String, Object> parameterInfo = new HashMap<>();
			ArrayList<HashMap<String, Object>> transCodeList = new ArrayList<>();
			
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			parameterInfo.put("orgStation", orgStation);
			
			
			transCodeList = mgrService.selectTransCodeList(parameterInfo);
			
			int curPage = 1;
			int totalCount = mgrService.selectMawbApplyListCnt(parameterInfo);
			
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			PagingVO paging = new PagingVO(curPage, totalCount, 10, 100);
			ArrayList<SendVO> mawbApplyList = new ArrayList<>();
			mawbApplyList = mgrService.selectMawbApplyList(parameterInfo);
			
			for (int i = 0; i < mawbApplyList.size(); i++) {
				mawbApplyList.get(i).dncryptData();
			}
			
			model.addAttribute("paging", paging);
			model.addAttribute("mawbApplyList", mawbApplyList);
			model.addAttribute("transCodeList", transCodeList);
			model.addAttribute("parameterInfo", parameterInfo);
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "adm/send/v1/sendList";
	}

	@RequestMapping(value = "/mngr/send/sendListTest", method = RequestMethod.GET)
	public String managerSendListTest(HttpServletRequest request, HttpServletResponse response, Model model,
			SendVO search) throws Exception {

		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		ArrayList<HashMap<String, Object>> transCodeList = new ArrayList<HashMap<String, Object>>();
		try {

			transCodeList = invService.selectTransCodeList(orgStation);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int curPage = 1;
		search.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		int totalCount = mgrService.selectSendListCount(search);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);

		search.setPaging(paging);

		ArrayList<SendVO> sendList = mgrService.selectSendList(search);

		for (int i = 0; i < sendList.size(); i++) {
			sendList.get(i).dncryptData();
		}

		model.addAttribute("transCodeList", transCodeList);
		model.addAttribute("paging", paging);
		model.addAttribute("sendList", sendList);
		model.addAttribute("search", search);

		return "adm/send/sendListTest";
	}

	@RequestMapping(value = "/mngr/send/sendList/excelDown", method = RequestMethod.GET)
	public void managerSendListExcelDown(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		params.put("startDate", request.getParameter("startDate"));
		params.put("endDate", request.getParameter("endDate"));
		params.put("userId", request.getParameter("userId"));
		params.put("mawbNo", request.getParameter("mawbNo"));
		params.put("hawbNo", request.getParameter("hawbNo"));
		params.put("orderNo", request.getParameter("orderNo"));
		params.put("transCode", request.getParameter("transCode"));

		SendVO sendVO = new SendVO();
		mgrService.selectSendListExcelDown(sendVO, params, request, response);
	}

	/**
	 * Page : Manager 발송 - 미발송 목록 URI : mngr/send/unSendList Method : GET
	 */
	@RequestMapping(value = "/mngr/send/unSendList", method = RequestMethod.GET)
	public String managerUnSendList(HttpServletRequest request, HttpServletResponse response, Model model,
			SendVO search) throws Exception {
		search.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		int curPage = 1;
		search.setOrgStation((String) request.getSession().getAttribute("ORG_STATION"));
		int totalCount = mgrService.selectUnSendListCount(search);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);

		search.setPaging(paging);

		ArrayList<SendVO> sendList = mgrService.selectUnSendList(search);

		for (int i = 0; i < sendList.size(); i++) {
			sendList.get(i).dncryptData();
		}

		model.addAttribute("paging", paging);
		model.addAttribute("sendList", sendList);
		model.addAttribute("search", search);
		return "adm/send/unSendList";
	}

	/**
	 * Page : Manager 반품관리 - 반품 접수 리스트 URI : mngr/rtrn/rtrnRcptList Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/rtrnRcptList", method = RequestMethod.GET)
	public String managerRtrnRcptList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/rtrn/rtrnRcptList";
	}

	/**
	 * Page : Manager 반품관리 - 우체국 접수 URI : mngr/rtrn/postOfice Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/postOffice", method = RequestMethod.GET)
	public String managerPostOfice(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 반품관리 - 입고 URI : mngr/rtrn/rcpt Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/rcpt", method = RequestMethod.GET)
	public String managerRtrnRcpt(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 반품관리 - 재고 URI : mngr/rtrn/stock Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/stock", method = RequestMethod.GET)
	public String managerRtrnStock(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 반품관리 - 출고 URI : mngr/rtrn/rls Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/rls", method = RequestMethod.GET)
	public String managerRtrnRls(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 반품관리 - 출고 리스트 URI : mngr/rtrn/rlsList Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/rlsList", method = RequestMethod.GET)
	public String managerRtrnRlsList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 반품관리 - 반품 CS URI : mngr/rtrn/rtrnCS Method : GET
	 */
	@RequestMapping(value = "/mngr/rtrn/rtrnCS", method = RequestMethod.GET)
	public String managerRtrnCS(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager INVOICE - Price Apply URI : mngr/invoic/priceApply Method :
	 * GET
	 */
	@RequestMapping(value = "/mngr/invoice/priceApply", method = RequestMethod.GET)
	public String managerPriceApply(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager INVOICE - invoice URI : mngr/invoice/expln Method : GET
	 */
	@RequestMapping(value = "/mngr/invoice/expln", method = RequestMethod.GET)
	public String managerExpln(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "default_Page2";
	}

	/**
	 * Page : Manager 게시판 - 문의사항 URI : mngr/board/qstns Method : GET
	 */
	@RequestMapping(value = "/mngr/board/qstns", method = RequestMethod.GET)
	public String managerQstns(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "adm/board/qnaBoard";
	}

	/**
	 * Page : Manager 게시판 - 공지사항 URI : mngr/board/ntc comn/notice2 Method : GET
	 */
	@RequestMapping(value = "/mngr/board/ntc", method = RequestMethod.GET)
	public String managerNotice(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", member.getUsername());
		infoMap.put("role", member.getRole());
		infoMap.put("searchWord", request.getParameter("searchWord"));
		int totalCount = comnService.selectTotalCntNotice(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount, 20, 50);
		infoMap.put("paging", paging);

		ArrayList<NoticeVO> noticeInfo = comnService.selectNotice(infoMap);
		for (int i = 0; i < noticeInfo.size(); i++) {
			noticeInfo.get(i).setDate(noticeInfo.get(i).getWDate());
		}
		model.addAttribute("noticeInfo", noticeInfo);
		model.addAttribute("paging", paging);
		return "adm/board/mngNotice";
	}

	@RequestMapping(value = "/mngr/board/ntcDetail", method = RequestMethod.GET)
	public String managerNoticeDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("role", member.getRole());
		infoMap.put("userId", member.getUsername());
		infoMap.put("ntcNo", request.getParameter("ntcNo"));

		NoticeVO noticeInfoDetail = comnService.selectNoticeDetail(infoMap);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format1.parse(noticeInfoDetail.getWDate());
		String time1 = format2.format(date);
		noticeInfoDetail.setDate(time1);
		model.addAttribute("noticeDetail", noticeInfoDetail);
		return "adm/board/mngNoticeDetail";
	}

	@RequestMapping(value = "/mngr/board/ntcRegist", method = RequestMethod.GET)
	public String managerNoticeRegist(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/board/mngNoticeRegist";
	}

	/**
	 * Page : Manager 게시판 - CS리스트 URI : mngr/board/CSList Method : GET
	 */
	/*
	 * @RequestMapping(value = "/mngr/board/csList", method = RequestMethod.GET)
	 * public String managerCsList(HttpServletRequest request, HttpServletResponse
	 * response, Model model) throws Exception { return "default_Page2"; }
	 */
	/*
	 * @RequestMapping(value = "/testT1", method = RequestMethod.GET) public String
	 * testT1(HttpServletRequest request, HttpServletResponse response, Model model)
	 * throws Exception { String tests = mgrService.selectTestSS();
	 * model.addAttribute("testssss", tests); return "default_Page2"; }
	 */

	@RequestMapping(value = "/mngr/board/toastImageUp", method = RequestMethod.POST)
	@ResponseBody
	public String testImageUp(MultipartHttpServletRequest request, HttpServletResponse response, Model model,
			MultipartFile file) throws Exception {
		ServerSocket ss = null;
		file = request.getFiles("files").get(0);
		try {
			String imageroot = request.getSession().getServletContext().getRealPath("/") + "/image/";
			FileOutputStream fos = new FileOutputStream(imageroot + file.getOriginalFilename());
			InputStream is = file.getInputStream();
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
			is.close();
			fos.close();
			/*
			 * ss = new ServerSocket(1234); System.out.println("서버 작동...."); Socket socket =
			 * ss.accept(); InputStream is = socket.getInputStream(); BufferedImage bimg =
			 * ImageIO.read(is); String imageroot =
			 * request.getSession().getServletContext().getRealPath("/") + "\\image";
			 * FileOutputStream fout = new FileOutputStream(imageroot); ImageIO.write(bimg,
			 * "png", fout); fout.close(); System.out.println("서버: 이미지수신 및 파일에 저장완료");
			 */
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return file.getOriginalFilename();
	}

	@RequestMapping(value = "/mngr/board/registToastNotice", method = RequestMethod.POST)
	@ResponseBody
	public void registToastNotice(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		NoticeVO noticeInfoVO = new NoticeVO();
		noticeInfoVO.setContents(request.getParameter("toastData"));
		noticeInfoVO.setTitle(request.getParameter("title"));
		noticeInfoVO.setWDate(request.getParameter("wDate"));
		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		noticeInfoVO.setWUserId(member.getUsername());
		noticeInfoVO.setWUserIp(request.getRemoteAddr());
		mgrService.insertNotice(noticeInfoVO);
	}

	@RequestMapping(value = "/mngr/board/createPdf", method = RequestMethod.GET)
	@ResponseBody
	public void createPdf(HttpServletRequest request, HttpServletResponse response, Model model,
			ArrayList<StockResultVO> stockResultList) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/"
				+ stockResultList.get(0).getNno() + "/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		File file = new File(pdfPath + stockResultList.get(0).getGroupIdx() + ".pdf");
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(1, mainPath.lastIndexOf("/"));
		// css
		String scss = subPath + "/static/assets/css/bootstrap.min.css";
		// font
		String font = subPath + "/static/fonts/NanumBarunGothic.ttf";
		try {
			// Document 생성 595, 842
			Rectangle POSTCARDs = new RectangleReadOnly(842, 595);
			Document document = new Document(POSTCARDs, 50, 50, 50, 50);

			// 파일 다운로드 설정
			response.setContentType("application/pdf");

			response.setHeader("Content-Transper-Encoding", "binary");
			response.setHeader("Content-Disposition",
					"inline; filename=" + stockResultList.get(0).getGroupIdx() + ".pdf");

			// PdfWriter 생성
			// PdfWriter writer = PdfWriter.getInstance(document, new
			// FileOutputStream("d:/test.pdf")); // 바로 다운로드.
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			writer.setInitialLeading(12.5f);

			// Document 오픈
			document.open();
			XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
			PdfContentByte cb = writer.getDirectContent();

			for (int pdfIndex = 0; pdfIndex < stockResultList.size(); pdfIndex++) {
				StockResultVO tempResultVO = new StockResultVO();
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				tempResultVO = stockResultList.get(pdfIndex);
				tempStockVoList = mgrService.selectStockByGrpIdx(tempResultVO.getGroupIdx());
				document.newPage();

				for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
					/* BarcodeCodabar NM7 바코드 */

					Barcode128 barcode128 = new Barcode128();
					barcode128.setCode(tempStockVoList.get(stockIndex).getStockNo());
					barcode128.setCodeType(Barcode.CODE128);
					com.itextpdf.text.Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
					BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
					cb.saveState();
					cb.beginText();
					cb.moveText(10, 20);
					cb.setFontAndSize(bf, 12);
					cb.endText();
					cb.restoreState();

					/*
					 * PdfPTable table = new PdfPTable(1); table.setWidthPercentage(20); Font fonts
					 * = new Font(bf, 11, Font.NORMAL);
					 * table.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * table.addCell(setChildCell(tempStockVoList.get(stockIndex).getWhStatusName(),
					 * fonts));
					 * table.addCell(setChildCell(tempStockVoList.get(stockIndex).getStockNo(),
					 * fonts)); table.addCell(setChildCell(tempResultVO.getItemDetail(), fonts));
					 * table.addCell(setChildCell(tempResultVO.getCneeName()+"sa", fonts)); PdfPCell
					 * imageCell = new PdfPCell(); imageCell.setImage(code128Image);
					 * imageCell.setBorder(0); table.addCell(imageCell); document.add(table);
					 */
				}
			}

			document.close();
			writer.close();
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/mngr/board/createPdfreal", method = RequestMethod.GET)
	@ResponseBody
	public void createPdfreal(HttpServletRequest request, HttpServletResponse response, Model model,
			ArrayList<StockResultVO> stockResultList) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		File file = new File(
				pdfPath + stockResultList.get(0).getNno() + "/" + stockResultList.get(0).getGroupIdx() + ".pdf");
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		// css
		String scss = subPath + "/static/assets/css/bootstrap.min.css";
		// font
		String font = subPath + "/static/fonts/NanumBarunGothic.ttf";
		try {
			// Document 생성 595, 842
			Rectangle POSTCARDs = new RectangleReadOnly(842, 595);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(POSTCARDs, 50, 50, 50, 50);

			// 파일 다운로드 설정
			response.setContentType("application/pdf");
			response.setHeader("Content-Transper-Encoding", "binary");
			response.setHeader("Content-Disposition",
					"inline; filename=" + stockResultList.get(0).getGroupIdx() + ".pdf");
			// PdfWriter 생성
			// PdfWriter writer = PdfWriter.getInstance(document, new
			// FileOutputStream("d:/test.pdf")); // 바로 다운로드.
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			writer.setInitialLeading(12.5f);

			// Document 오픈
			document.open();
			XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
			PdfContentByte cb = writer.getDirectContent();
			for (int pdfIndex = 0; pdfIndex < stockResultList.size(); pdfIndex++) {
				StockResultVO tempResultVO = new StockResultVO();
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				tempResultVO = stockResultList.get(pdfIndex);
				tempStockVoList = mgrService.selectStockByGrpIdx(tempResultVO.getGroupIdx());
				document.newPage();

				for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
					/* BarcodeCodabar NM7 바코드 */
					Barcode128 barcode128 = new Barcode128();
					barcode128.setCode(tempStockVoList.get(stockIndex).getStockNo());
					barcode128.setCodeType(Barcode.CODE128);
					com.itextpdf.text.Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
					BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
					// CSS
					CSSResolver cssResolver = new StyleAttrCSSResolver();
					CssFile cssFile = helper.getCSS(new FileInputStream(scss));
					cssResolver.addCss(cssFile);

					// HTML, 폰트 설정
					XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(
							XMLWorkerFontProvider.DONTLOOKFORFONTS);
					fontProvider.register(font, "NanumBarunGothic"); // MalgunGothic은
					CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
					HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
					htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

					// Pipelines
					PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
					HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
					CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
					XMLWorker worker = new XMLWorker(css, true);
					XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));

					// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
					String sHtml = "<html><head></head><body style='font-family: NanumBarunGothic;width:200;height:150;'>"
							+ "<table>" + "<tr><td style='text-align:center;'>" + "<h4>"
							+ tempStockVoList.get(stockIndex).getWhStatusName() + "</h4>" + "</td></tr>"
							+ "<tr><td style='text-align:center;'>" + "<h3>"
							+ tempStockVoList.get(stockIndex).getStockNo() + "</h3>" + "</td></tr>"
							+ "<tr><td style='text-align:center;'>" + "<h5>" + tempResultVO.getItemDetail() + "</h5>"
							+ "</td></tr>" + "<tr><td style='text-align:center;'>" + "<h5>" + tempResultVO.getCneeName()
							+ "</h5>" + "<img src='" + pdfPath2 + tempStockVoList.get(stockIndex).getStockNo() + ".JPEG"
							+ "'/><br/>" + tempStockVoList.get(stockIndex).getStockNo() + "</td></tr>" + "<tr><td>"
							+ "</td></tr>" + "</table><br/><br/><br/>" + "</body></html>";
					StringReader strReader = new StringReader(sHtml);
					xmlParser.parse(strReader);
					code128Image.setAlignment(code128Image.ALIGN_CENTER);
				}
			}
			// Document 생성 595, 842
			document.close();
			writer.close();
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/mngr/board/createPdfreal2", method = RequestMethod.GET)
	@ResponseBody
	public void createPdfreal2(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}

		String[] tempGroupIdx = request.getParameter("groupIdx").split(",");

		String barcodePath = pdfPath2 + tempGroupIdx[0] + ".JPEG";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));

		final PDDocument doc = new PDDocument();
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);
		float perMM = 1 / (10 * 2.54f) * 72;

		int pdfPage = 0;

		try {
			int marginTop = 30;
			for (int pdfIndex = 0; pdfIndex < tempGroupIdx.length; pdfIndex++) {
				ArrayList<StockResultVO> stockResultList = new ArrayList<StockResultVO>();
				stockResultList = mgrService.selectStockResultVO(request, tempGroupIdx[pdfIndex]);
				/* StockResultVO tempResultVO = new StockResultVO(); */
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				tempStockVoList = mgrService.selectStockByGrpIdx(stockResultList.get(0).getGroupIdx());
				// 컨텐츠 스트림 열기
				for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
					StockResultVO tempResultVO = new StockResultVO();
					tempResultVO = stockResultList.get(stockIndex);
					PDRectangle asd = new PDRectangle(80 * perMM, 60 * perMM);
					PDPage blankPage = new PDPage(asd);
					doc.addPage(blankPage);
					PDPage page = doc.getPage(pdfPage);
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);

					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createCode128(tempStockVoList.get(stockIndex).getStockNo());
					barcodes.setSize(400, 800);
					barcodes.setBarHeight(0);
					barcodes.setBarWidth(0);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					File barcodefile = new File(barcodePath);
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					int fontSize = 12; // Or whatever font size you want.
					float titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getWhStatusName())
							/ 1000 * fontSize;
					contentStream.drawImage(pdImage, (80 * perMM - 220) / 2, 37, 220f, 35f);

					drawText(tempStockVoList.get(stockIndex).getWhStatusName(), NanumGothic, 12,
							(80 * perMM - titleWidth) / 2, 145, contentStream);

					fontSize = 14;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getHawbNo()) / 1000
							* fontSize;
					drawText(tempStockVoList.get(stockIndex).getHawbNo(), NanumGothic, 14,
							(80 * perMM - titleWidth) / 2, 130, contentStream);

					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(
							tempStockVoList.get(stockIndex).getUserId() + "/" + tempResultVO.getCneeName()) / 1000
							* fontSize;
					drawText(tempStockVoList.get(stockIndex).getUserId() + "/" + tempResultVO.getCneeName(),
							NanumGothic, 10, (80 * perMM - titleWidth) / 2, 117, contentStream);

					fontSize = 11;
					titleWidth = NanumGothic.getStringWidth(tempResultVO.getRackCode()) / 1000 * fontSize;
					drawText(tempResultVO.getRackCode(), NanumGothic, 11, (80 * perMM - titleWidth) / 2, 102,
							contentStream);

					fontSize = 12;
					String itemDetailSub = tempResultVO.getItemDetail();
					String itemDetail = "";
					String text = itemDetail.toUpperCase();
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
					
					itemDetail = builder.toString();
					
					if (NanumGothic.getStringWidth(itemDetail) / 1000 * fontSize > 80 * perMM) {
						itemDetail = itemDetail.substring(0, 15);
						itemDetail = itemDetail + "...";
					}

					titleWidth = NanumGothic.getStringWidth(itemDetail) / 1000 * fontSize;
					drawText(itemDetail, NanumGothic, 12, (80 * perMM - titleWidth) / 2, 90, contentStream);

					fontSize = 13;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getStockNo()) / 1000
							* fontSize;
					drawText(tempStockVoList.get(stockIndex).getStockNo(), NanumGothic, 13,
							(80 * perMM - titleWidth) / 2, 25, contentStream);
					contentStream.close();
					pdfPage++;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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

	@ResponseBody
	public void createPdfStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}

		String[] tempStockNo = request.getParameterValues("stockNo");

		String barcodePath = pdfPath2 + tempStockNo[0] + ".JPEG";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));

		final PDDocument doc = new PDDocument();
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);
		float perMM = 1 / (10 * 2.54f) * 72;

		int pdfPage = 0;

		try {
			int marginTop = 30;
			for (int pdfIndex = 0; pdfIndex < tempStockNo.length; pdfIndex++) {
				StockResultVO stockResultList = new StockResultVO();
				stockResultList = mgrService.selectStockResultStockVO(request, tempStockNo[pdfIndex]);
				/* StockResultVO tempResultVO = new StockResultVO(); */
				StockVO tempStockVoList = new StockVO();
				tempStockVoList = mgrService.selectStockByStockNo(tempStockNo[pdfIndex]);
				// 컨텐츠 스트림 열기
				PDRectangle asd = new PDRectangle(80 * perMM, 60 * perMM);
				PDPage blankPage = new PDPage(asd);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(pdfPage);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);

				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(tempStockNo[pdfIndex]);
				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				int fontSize = 12; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(tempStockVoList.getWhStatusName()) / 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 220) / 2, 37, 220f, 35f);

				drawText(tempStockVoList.getWhStatusName(), NanumGothic, 12, (80 * perMM - titleWidth) / 2, 145,
						contentStream);

				fontSize = 14;
				titleWidth = NanumGothic.getStringWidth("추가입고 상품") / 1000 * fontSize;
				drawText("추가입고 상품", NanumGothic, 14, (80 * perMM - titleWidth) / 2, 130, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic
						.getStringWidth(tempStockVoList.getUserId() + "/" + stockResultList.getCneeName()) / 1000
						* fontSize;
				drawText(tempStockVoList.getUserId() + "/" + stockResultList.getCneeName(), NanumGothic, 10,
						(80 * perMM - titleWidth) / 2, 117, contentStream);

				fontSize = 11;
				titleWidth = NanumGothic.getStringWidth(stockResultList.getRackCode()) / 1000 * fontSize;
				drawText(stockResultList.getRackCode(), NanumGothic, 11, (80 * perMM - titleWidth) / 2, 102,
						contentStream);

				fontSize = 12;
				String itemDetailSub = stockResultList.getItemDetail();
				if (NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize > 80 * perMM) {
					itemDetailSub = itemDetailSub.substring(0, 15);
					itemDetailSub = itemDetailSub + "...";
				}

				titleWidth = NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize;
				drawText(itemDetailSub, NanumGothic, 12, (80 * perMM - titleWidth) / 2, 90, contentStream);

				fontSize = 13;
				titleWidth = NanumGothic.getStringWidth(tempStockVoList.getStockNo()) / 1000 * fontSize;
				drawText(tempStockVoList.getStockNo(), NanumGothic, 13, (80 * perMM - titleWidth) / 2, 25,
						contentStream);
				contentStream.close();
				pdfPage++;

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
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

	@RequestMapping(value = "/mngr/board/createPdftest", method = RequestMethod.GET)
	@ResponseBody
	public void createPdftest(HttpServletRequest request, HttpServletResponse response, Model model,
			ArrayList<StockResultVO> stockResultList) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		File file = new File(pdfPath + "test.pdf");
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(1, mainPath.lastIndexOf("/"));
		// css
		String scss = subPath + "/static/assets/css/bootstrap.min.css";
		// font
		String font = subPath + "/static/fonts/NanumBarunGothic.ttf";
		try {

//			/* BarcodeCodabar  NM7 바코드*/  
//			
//			Barcode128 barcode128 = new Barcode128();
//			barcode128.setCode(tempStockVoList.get(stockIndex).getStockNo());
//			barcode128.setCodeType(Barcode.CODE128); 
//			com.itextpdf.text.Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
//			
//			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128A(tempStockVoList.get(stockIndex).getStockNo());
//
//			barcodes.setBarHeight(50);
//			barcodes.setBarWidth(1);
//
//			barcodes.setLabel("Barcode creation test...");
//			barcodes.setDrawingText(true);
//			
//			File barcodefile = new File(pdfPath2 + tempStockVoList.get(stockIndex).getStockNo() + ".JPEG");
//			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//			Image image = Image.getInstance(ImageIO.read(barcodefile), null);
//
//			/*
//			 * Barcode128 code128 = new Barcode128(); code128.setCode("stasdfasdfjsdsdj");
//			 * code128.setCodeType(Barcode.CODE128);
//			 * code128.setCodeSet(Barcode128CodeSet.A); code128.createImageWithBarcode(cb,
//			 * new BaseColor(255,255,255), new BaseColor(0,0,0));
//			 */
//			/* document.add(code128.createImageWithBarcode(cb, null, null)); */
//			// CSS
//			CSSResolver cssResolver = new StyleAttrCSSResolver();
//			CssFile cssFile = helper.getCSS(new FileInputStream(scss));
//			cssResolver.addCss(cssFile);
//			
//
//			// HTML, 폰트 설정
//			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
//			fontProvider.register(font, "NanumBarunGothic"); // MalgunGothic은
//
//			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
//
//			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
//			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
//
//			// Pipelines
//			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
//			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
//			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
//
//			XMLWorker worker = new XMLWorker(css, true);
//			XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
//
//			// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
//			String sHtml = "<html><head></head><body style='font-family: NanumBarunGothic;width:200;height:150;'>" +
//					"<table>"+
//					"<tr><td style='text-align:center;'>"+
//					"<h4>"+tempStockVoList.get(stockIndex).getWhStatusName()+"</h4>"+
//					"</td></tr>"+
//					"<tr><td style='text-align:center;'>"+
//					"<h3>"+tempStockVoList.get(stockIndex).getStockNo()+"</h3>"+
//					"</td></tr>"+
//					"<tr><td style='text-align:center;'>"+
//					"<h5>"+tempResultVO.getItemDetail()+"</h5>"+
//					"</td></tr>"+
//					"<tr><td style='text-align:center;'>"+
//					"<h5>"+tempResultVO.getCneeName()+"</h5>"+
//					"<img src='"+pdfPath2+tempStockVoList.get(stockIndex).getStockNo() + ".JPEG"+"'/><br/>"+
//					tempStockVoList.get(stockIndex).getStockNo()+
//					"</td></tr>"+
//					"</table><br/><br/><br/>"+
//					"</body></html>";
//			StringReader strReader = new StringReader(sHtml);
//			xmlParser.parse(strReader);
//			code128Image.setAlignment(code128Image.ALIGN_CENTER);
//			document.add(code128Image);

			// Document 생성 595, 842
			Rectangle POSTCARDs = new RectangleReadOnly(842, 595);
			Document document = new Document(POSTCARDs, 50, 50, 50, 50);

			// 파일 다운로드 설정
			response.setContentType("application/pdf");

			response.setHeader("Content-Transper-Encoding", "binary");
			response.setHeader("Content-Disposition", "inline; filename=" + "test.pdf");

			// PdfWriter 생성
			// PdfWriter writer = PdfWriter.getInstance(document, new
			// FileOutputStream("d:/test.pdf")); // 바로 다운로드.
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			writer.setInitialLeading(12.5f);

			// Document 오픈
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			document.newPage();

			/* BarcodeCodabar NM7 바코드 */

			Barcode128 barcode128 = new Barcode128();
			barcode128.setCode("asdasd");
			barcode128.setCodeType(Barcode.CODE128);
			com.itextpdf.text.Image code128Image = barcode128.createImageWithBarcode(cb, null, null);

			BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(20);
			table.setWidths(new int[] { 3 });
			Font headFont = new Font(bf, 11, Font.BOLD);
			Font fonts = new Font(bf, 11, Font.NORMAL);
			table.addCell(setChildCell("필드1", fonts));

			table.addCell(setChildCell("한글입력", fonts));
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell();
			cell.setImage(code128Image);
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);
			document.close();
			writer.close();
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/mngr/board/createPdftest2", method = RequestMethod.GET)
	@ResponseBody
	public void createPdftest2(HttpServletRequest request, HttpServletResponse response, Model model,
			ArrayList<StockResultVO> stockResultList) throws Exception {
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		File file = new File(pdfPath + "test.pdf");

		PDDocument doc = new PDDocument();

		response.setContentType("application/pdf");

		String fileName = URLEncoder.encode("testPDF", "UTF-8");

		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		PDPage blankPage = new PDPage(PDRectangle.A4);

		doc.addPage(blankPage);

		PDPage page = doc.getPage(0);

		try {

			for (int pdfIndex = 0; pdfIndex < stockResultList.size(); pdfIndex++) {
				StockResultVO tempResultVO = new StockResultVO();
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				tempResultVO = stockResultList.get(pdfIndex);
				tempStockVoList = mgrService.selectStockByGrpIdx(tempResultVO.getGroupIdx());

				for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
					/* BarcodeCodabar NM7 바코드 */

					net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
							.createCode128A(tempStockVoList.get(stockIndex).getStockNo());

					barcodes.setBarHeight(50);
					barcodes.setBarWidth(1);

					barcodes.setLabel("Barcode creation test...");
					barcodes.setDrawingText(true);

					File barcodefile = new File(pdfPath2 + tempStockVoList.get(stockIndex).getStockNo() + ".JPEG");
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					Image image = Image.getInstance(ImageIO.read(barcodefile), null);
					BufferedImage img = ImageIO.read(barcodefile);

					String imgstr;
					imgstr = new String(encodeToString(img, "JPEG"));

					/* code128Image.setAlignment(code128Image.ALIGN_CENTER); */

					// 배경이미지 로드
					PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

					// 컨텐츠 스트림 열기
					PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					// 배경 이미지 그리기
					contentStream.drawImage(pdImage, 595, 842, 100, 200);
				}
			}

			// Document 생성 595, 842
			doc.save(response.getOutputStream());

			doc.close();

		} catch (Exception e) {
			throw e;
		}
	}

	private PdfPCell setHeadCell(String content, Font font) {
		PdfPCell hcell = new PdfPCell(new Phrase(content, font));
		hcell.setBorder(0);
		hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setFixedHeight(25f);
		return hcell;
	}

	private PdfPCell setChildCell(String content, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(content, font));
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setFixedHeight(20f);
		return cell;
	}

	/**
	 * Decode string to image
	 * 
	 * @param imageString The string to decode
	 * @return decoded image
	 */
	/*
	 * public static BufferedImage decodeToImage(String imageString) {
	 * 
	 * BufferedImage image = null; byte[] imageByte; try { BASE64Decoder decoder =
	 * new BASE64Decoder(); imageByte = decoder.decodeBuffer(imageString);
	 * ByteArrayInputStream bis = new ByteArrayInputStream(imageByte); image =
	 * ImageIO.read(bis); bis.close(); } catch (Exception e) {
	 * logger.error("Exception", e); } return image; }
	 */

	/**
	 * Encode image to string
	 * 
	 * @param image The image to encode
	 * @param type  jpeg, bmp, ...
	 * @return encoded string
	 */
	public static byte[] encodeToString(BufferedImage image, String type) {
		byte[] imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();

			Base64 encoder = new Base64();
			imageString = encoder.encodeBase64(imageBytes);

			bos.close();
		} catch (IOException e) {

		}
		return imageString;
	}

	/**
	 * Page : USER 재고관리/사입 재고 현황 URI : /cstmr/stock/gdsrcRgstr Method : GET
	 */
	@RequestMapping(value = "/cstmr/test/invoicePrintss", method = RequestMethod.GET)
	public String userInvoicePrintss() throws Exception {
		return "user/apply/registList/invoicePrint";
	}

	/*
	 * public static void main (String args[]) throws IOException { Test image to
	 * string and string to image start BufferedImage img = ImageIO.read(new
	 * File("files/img/TestImage.png")); BufferedImage newImg; String imgstr; imgstr
	 * = new String(encodeToString(img, "png")); System.out.println(imgstr); newImg
	 * = decodeToImage(imgstr); ImageIO.write(newImg, "png", new
	 * File("files/img/CopyOfTestImage.png")); Test image to string and string to
	 * image finish }
	 */

	@RequestMapping(value = "/mngr/board/createPdftest3", method = RequestMethod.GET)
	public void pdf(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		int pageCount = 2;
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf/";
		File dir = new File(pdfPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		String webroot = pdfPath;
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(1, mainPath.lastIndexOf("/"));
		// 문서 만들기
		final PDDocument doc = new PDDocument();

		// 폰트 생성
		// ttf 파일 사용하기
		InputStream fontStream = new FileInputStream(subPath + "/static/fonts/msgothic.ttf");
		InputStream fontStream2 = new FileInputStream(subPath + "/static/fonts/ArialUnicodeMS.ttf");
		InputStream fontStream3 = new FileInputStream(subPath + "/static/fonts/ARIALBD.ttf");
		PDType0Font msGothic = PDType0Font.load(doc, fontStream);
		PDType0Font ARIAL = PDType0Font.load(doc, fontStream2);
		PDType0Font ARIALBOLD = PDType0Font.load(doc, fontStream3);
		PDType1Font tesst = PDType1Font.HELVETICA;

//	    for (int pdfIndex = 0; pdfIndex < stockResultList.size(); pdfIndex++) {
//			StockResultVO tempResultVO = new StockResultVO();
//			ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
//			tempResultVO = stockResultList.get(pdfIndex);
//			tempStockVoList = mgrService.selectStockByGrpIdx(tempResultVO.getGroupIdx());
//
//			for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
//				/* BarcodeCodabar NM7 바코드 */
//
//				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory
//						.createCode128A(tempStockVoList.get(stockIndex).getStockNo());
//
//				barcodes.setBarHeight(50);
//				barcodes.setBarWidth(1);
//
//				barcodes.setLabel("Barcode creation test...");
//				barcodes.setDrawingText(true);
//
//				File barcodefile = new File(pdfPath2 + tempStockVoList.get(stockIndex).getStockNo() + ".JPEG");
//				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
//				Image image = Image.getInstance(ImageIO.read(barcodefile), null);
//				BufferedImage img = ImageIO.read(barcodefile);
//
//				String imgstr;
//				imgstr = new String(encodeToString(img, "JPEG"));
//
//				/* code128Image.setAlignment(code128Image.ALIGN_CENTER); */
//				
//				// 배경이미지 로드
//				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
//				
//				// 컨텐츠 스트림 열기
//				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//				// 배경 이미지  그리기
//				contentStream.drawImage(pdImage, 595, 842, 100, 200);
//
//		
//		
//			}
//		}
		float perMM = 1 / (10 * 2.54f) * 72;

		// 두 개의 페이지를 만든다.
		for (int i = 0; i < pageCount; i++) {
			// 페이지 추가
			PDRectangle asd = new PDRectangle(105 * perMM, 210 * perMM);
			PDPage blankPage = new PDPage(asd);
			doc.addPage(blankPage);

			// 현재 페이지 설정
			PDPage page = doc.getPage(i);

			// 컨텐츠 스트림 열기
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);

			net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCodabar("c1234012d");

			barcodes.setBarHeight(50);
			barcodes.setBarWidth(1);

			barcodes.setLabel("Barcode creation test...");
			barcodes.setDrawingText(true);

			BufferedImage image = BarcodeImageHandler.getImage(barcodes);
			File barcodefile = new File(pdfPath2 + "test.JPEG");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			// 배경이미지 로드
			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

			/*
			 * pdImage = PDImageXObject.createFromFile(
			 * "C:\\Users\\operation\\git\\outBound\\tempProject\\src\\main\\webapp\\image\\test222.JPG"
			 * ,doc); contentStream.drawImage(pdImage, 0, 0, 105*perMM, 210*perMM);
			 */

			// 배경 이미지 그리기
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 20, 540, 63.7f, 28.3f);

			// 글씨 쓰기
			drawText("発送日: 20年6月30日", msGothic, 8, 83, 563, contentStream);
			drawText("個数 3", msGothic, 8, 180, 560, contentStream);

			drawText("便種 : 便種", msGothic, 9, 83, 540, contentStream);
			String texts = "415";
			String text2 = "";
			if (texts.length() < 5) {
				int test = 5 - texts.length();
				for (int j = 0; j < test; j++) {
					text2 += "  ";
				}
				text2 += texts;
			} else {
				text2 = texts;
			}
			drawText(text2, ARIALBOLD, 30, 160, 535, contentStream);
			drawText(text2, ARIALBOLD, 18, 230, 535, contentStream);

			drawText("干 8104", msGothic, 6, 23, 529, contentStream);
			drawText("TEL 075-661-8675", msGothic, 6, 73, 529, contentStream);

			drawTextLine("東京都江東区亀戸1-1-131東京都江東区亀戸1-1-13東京都江東区亀戸1-1-13", msGothic, 6, 23, 523, contentStream);
			drawTextLine("東京都江東区亀戸1-1-13", msGothic, 9, 23, 510, contentStream);
			drawTextLine("合也送合也送合也送", msGothic, 9, 23, 498, contentStream);

			barcodes = BarcodeFactory.createCodabar("d123456789012d");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 150, 493, 140f, 32.6f);

			drawText("お問合也送リ状: d123456789012d", msGothic, 7, 165, 486, contentStream);
			drawText("お問", msGothic, 6, 193, 479, contentStream);
			drawText("TEL 0120-333-803", msGothic, 7, 220, 479, contentStream);

			drawTextLineEng("Office 14, Big yellow 1-3 Wyvern estate Surrey", msGothic, 9, 23, 486, contentStream, 0);
			drawTextLineEng("S2L TRADING LTD", msGothic, 9, 23, 460, contentStream, 0);
			drawTextLine("Tel. 0300000000000000000", msGothic, 6, 152, 468, contentStream);

			drawText("発送日20年6月30日", msGothic, 7, 140, 406, contentStream);
			drawText("お問合也送リ状: d123456789012d", msGothic, 7, 140, 398, contentStream);
			drawText("個数: 3", msGothic, 7, 250, 404, contentStream);

			drawText("干 810", msGothic, 6, 23, 390, contentStream);
			drawText("TEL 075-661-8675", msGothic, 6, 73, 390, contentStream);
			drawTextLine("東京都江東区亀戸1-1-13", msGothic, 6, 23, 384, contentStream);
			drawTextLine("東京都江東区亀戸1-1-13", msGothic, 9, 23, 368, contentStream);
			drawTextLine("合也送合也送合也ssss送", msGothic, 9, 23, 355, contentStream);

			drawTextLine("東京都江東区亀戸1-1-13", msGothic, 6, 23, 345, contentStream);
			drawTextLineEng("Office 14, Big yellow 1-3 Wyvern estate Surrey", msGothic, 9, 23, 333, contentStream, 0);
			drawTextLineEng("S2L TRADING LTD", msGothic, 9, 23, 313, contentStream, 0);

			drawTextLineStar("***************" + "***************" + "***************" + "***************"
					+ "***************" + "***************", msGothic, 9, 145, 390, contentStream, 1);

			barcodes = BarcodeFactory.createCodabar("d002b");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 228, 375, 40f, 17f);
			drawText("2KG", msGothic, 7, 245, 368, contentStream);

			barcodes = BarcodeFactory.createCodabar("d005b");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 228, 347, 40f, 17f);
			drawText("5KG", msGothic, 7, 245, 340, contentStream);

			barcodes = BarcodeFactory.createCodabar("d010b");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 228, 320, 40f, 17f);
			drawText("10KG", msGothic, 7, 242, 313, contentStream);

			drawTextLineStar("*********" + "*********" + "*********" + "*********" + "*********" + "*********"
					+ "*********" + "*********" + "*********" + "*********", msGothic, 9, 190, 305, contentStream, 2);

			drawTextLineStar("*********" + "*********" + "*********" + "*********" + "*********" + "*********"
					+ "*********" + "*********" + "*********" + "*********", msGothic, 9, 240, 305, contentStream, 2);

			drawTextLineStar("**************", msGothic, 7, 20, 235, contentStream, 1);
			drawText("お問", msGothic, 6, 145, 240, contentStream);
			drawText("TEL 0120-333-803", msGothic, 6, 180, 235, contentStream);
			drawText("発送日20年6月30日", msGothic, 6, 145, 225, contentStream);

			drawText("3", msGothic, 11, 255, 237, contentStream);

			drawText("干 8104", msGothic, 9, 23, 217, contentStream);
			drawText("TEL 075-661-8675", msGothic, 9, 63, 222, contentStream);
			drawTextLine("東京都江東区亀戸1-1-131東京都江東区亀戸1-1-13東京都江東区亀戸1-1-13", msGothic, 13, 49, 210, contentStream);
			drawTextLine("東京都江東区亀戸1-1-13", msGothic, 13, 49, 187, contentStream);
			drawTextLine("合也送合也送合也送", msGothic, 13, 49, 167, contentStream);

			barcodes = BarcodeFactory.createCodabar("d123456789012d");
			BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
			pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);
			contentStream.drawImage(pdImage, 35, 129, 150.2f, 33.0f);

			drawText("830011", msGothic, 13, 55, 117, contentStream);
			drawText("400026269241", msGothic, 13, 100, 117, contentStream);
			drawText("119720230001", msGothic, 13, 190, 117, contentStream);

			drawTextLineEng("Office 14, Big yellow 1-3 Wyvern estate Surrey", msGothic, 7, 23, 90, contentStream, 1);
			drawTextLineEng("S2L TRADING LTD", msGothic, 9, 23, 75, contentStream, 1);
			drawTextLine("Tel. 0300000000000000000", msGothic, 7, 150, 85, contentStream);
			drawText("便種 :  便種", msGothic, 7, 245, 45, contentStream);

			drawTextLineStar("**************" + "**************" + "**************" + "**************", msGothic, 9,
					210, 151, contentStream, 3);

			// 컨텐츠 스트림 닫기
			contentStream.close();
		}

		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");

		// PDF 파일 출력
		doc.save(response.getOutputStream());
		doc.close();

	}

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

	private void drawText2(String text, PDFont font, int fontSize, PDPageContentStream contentStream) throws Exception {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.showText(text);
		contentStream.endText();
	}

	private void drawTextLine(String text, PDFont font, int fontSize, float left, float bottom,
			PDPageContentStream contentStream) throws Exception {
		String asd = "";
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

		if (text.length() > splitSize) {
			for (int i = 0; i <= (int) (text.length() / splitSize); i++) {
				contentStream.beginText();
				contentStream.setFont(font, fontSize);
				contentStream.newLineAtOffset(left, bottom - (fontss * i));
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

	private void drawTextLineEng(String text, PDFont font, int fontSize, float left, float bottom,
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

	@RequestMapping(value = "/mngr/rls/nationSearch", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<NationVO> customerNationSearch(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		nations = mgrService.selectUserDstnNationInfo((String) request.getSession().getAttribute("ORG_STATION"));
		return nations;
	}

	@RequestMapping(value = "/mngr/rls/transCodeSearch", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<TransComVO> customerTransCodeSearch(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		ArrayList<TransComVO> transCodes = new ArrayList<TransComVO>();
		transCodes = mgrService.selectTransCom();
		return transCodes;
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/ftpTest Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/ftpSend", method = RequestMethod.POST)
	@ResponseBody
	public String managerFtpSend(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		String txtTail = request.getParameter("target") + "_" + currentTime.toString();
		LocalDate currentDate = LocalDate.now(); // 컴퓨터의 현재 날짜 정보 2018-07-26
		ArrayList<InnerProductVO> tempVO = new ArrayList<InnerProductVO>();
		ArrayList<String> sendTargetFile = new ArrayList<String>();
		HashMap<String, String> parameters = new HashMap();
		String returnVal = "";
		parameters.put("mawbNo", request.getParameter("target"));
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("userIp", (String) request.getSession().getAttribute("USER_IP"));
		tempVO = mgrService.selectInnerProductList(parameters);
		String path = request.getSession().getServletContext().getRealPath("/") + "IN_OUT/"
				+ currentTime.toLocalDate().toString() + "/";
		String fileName = "IN_" + txtTail + ".txt";
		String fileName2 = "OUT_" + txtTail + ".txt";
		// 파일 객체 생성
		File file = new File(path + fileName);
		File file2 = new File(path + fileName2);

		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		String officeCode = "10610";
		String inYear = Integer.toString(currentDate.getYear());
		String serialNum = "";
		String outType = "11";
		String outTargetCode = "    ";
		String ItemCode = "";
		String ItemDetail = "";
		String unitPackage = "EA";
		String itemCnt = "";
		String wtValue = "";
		String outDate = "";
		String makeCntry = "KRSEL";
		String storageLocation = String.format("%-50s", "BA04");
		String storageEndDate = "";
		String cargoOwnerCode = "BA04";
		String cargoOwner = String.format("%-100s", "ACI");
		String outReason = String.format("%-100s", "반출");
		String productSign = "A";
		String outProductSign = "A";
		String dataType = "I";
		String outDataType = "I";
		String reportNumber = "";
		String resultVal = "";
		String dataCreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		String EOF = "*";

		int tempSize = tempVO.size();
		// true 지정시 파일의 기존 내용에 이어서 작성
		if (tempSize != 0) {
			FileWriter fw = new FileWriter(file, false);
			FileWriter fw2 = new FileWriter(file2, false);
			try {
				for (int i = 0; i < tempSize; i++) {
					resultVal = "";
					serialNum = tempVO.get(i).getCarryInNum();
					ItemCode = String.format("%-20s", serialNum);
					ItemDetail = String.format("%-200s", tempVO.get(i).getItemDetail());
					unitPackage = tempVO.get(i).getQtyUnit();
					itemCnt = String.format("%010d", Integer.parseInt(tempVO.get(i).getItemCnt()));
					if (tempVO.get(i).getWta() != null && tempVO.get(i).getWtc() == null) {
						wtValue = tempVO.get(i).getWta();
					} else if (tempVO.get(i).getWta() == null && tempVO.get(i).getWtc() != null) {
						wtValue = tempVO.get(i).getWtc();
					} else if (tempVO.get(i).getWta() != null && tempVO.get(i).getWtc() != null) {
						if (Double.parseDouble(tempVO.get(i).getWta()) > Double.parseDouble(tempVO.get(i).getWtc())) {
							wtValue = tempVO.get(i).getWta();
						} else {
							wtValue = tempVO.get(i).getWtc();
						}
					}

					wtValue = String.format("%016.1f", Double.parseDouble(wtValue));
					outDate = tempVO.get(i).getInDate();
					makeCntry = "KRSEL";
					storageEndDate = tempVO.get(i).getEndDate();
					String txt = officeCode + inYear + serialNum + ItemCode + ItemDetail + unitPackage + itemCnt
							+ wtValue + outDate + makeCntry + storageLocation + storageEndDate + cargoOwnerCode
							+ cargoOwner + productSign + dataType + dataCreateDate + EOF + "\n";
					// serialNum.substring(2, serialNum.length())
					// reportNumber = makeReportNum("274474");

					reportNumber = count36(Integer.parseInt(serialNum));
					for (int j = 0; j < 6 - reportNumber.length(); j++) {
						resultVal = resultVal + "0";
					}
					resultVal = resultVal + reportNumber;

					String txt2 = officeCode + inYear + serialNum + inYear + serialNum + outType + outTargetCode
							+ ItemCode + ItemDetail + itemCnt + wtValue + outDate + cargoOwnerCode + cargoOwner
							+ outReason + outProductSign + outDataType + dataCreateDate
							+ (officeCode + inYear.substring(0, 2) + resultVal + "X") + "   " + EOF + "\n";

//					String txt2 = officeCode+inYear+serialNum+inYear+serialNum+outType+outTargetCode+ItemCode+ItemDetail+
//							itemCnt+wtValue+outDate+cargoOwnerCode+cargoOwner+outReason+outProductSign+outDataType+dataCreateDate+(officeCode+inYear.substring(0, 2)+serialNum.substring(2, serialNum.length())+"X")+"  "+EOF+"\n";

					fw.write(txt);
					fw2.write(txt2);
					fw.flush();
					fw2.flush();
				}

				// 객체 닫기
				mgrService.updateInnerProductList(parameters);
				sendFtpServer("183.102.113.12", 21, "ACI", "kctdiftp1741#!", "/ACI/EXPORT/IN", path, fileName);
				sendFtpServer("183.102.113.12", 21, "ACI", "kctdiftp1741#!", "/ACI/EXPORT/OUT", path, fileName2);
				returnVal = returnVal + "MAWB_NO : " + request.getParameter("target") + "가 등록되었습니다.\n";
			} catch (Exception e) {
				logger.error("Exception", e);
				fw = new FileWriter(file, false);
				fw2 = new FileWriter(file2, false);
				returnVal = returnVal + "MAWB_NO : " + request.getParameter("target") + "가 등록에 실패 하였습니다.\n";
			} finally {
				fw.close();
				fw2.close();
			}

		} else {
			returnVal = returnVal + "MAWB_NO : " + request.getParameter("target") + "에 등록할 대상이 없습니다.\n";
		}
		return returnVal;
	}

	/**
	 * Page : Manager 출고 - MAWB bList URI : mngr/rls/ftpTest Method : POST
	 */
	@RequestMapping(value = "/mngr/rls/ftpSendAll", method = RequestMethod.POST)
	@ResponseBody
	public String managerFtpSendAll(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		String txtTail = request.getParameter("target") + "_" + currentTime.toLocalTime().getHour()
				+ currentTime.toLocalTime().getMinute() + currentTime.toLocalTime().getSecond()
				+ Integer.toString(currentTime.toLocalTime().getNano()).substring(0, 3);
		LocalDate currentDate = LocalDate.now(); // 컴퓨터의 현재 날짜 정보 2018-07-26
		ArrayList<InnerProductVO> tempVO = new ArrayList<InnerProductVO>();
		ArrayList<String> sendTargetFile = new ArrayList<String>();
		HashMap<String, String> parameters = new HashMap();
		String returnVal = "";
		ArrayList<MawbVO> mawbList = new ArrayList<MawbVO>();

		mawbList = mgrService.selectMawbList((String) request.getSession().getAttribute("ORG_STATION"));

		for (int mawbIndex = 0; mawbIndex < mawbList.size(); mawbIndex++) {
			parameters.put("mawbNo", mawbList.get(mawbIndex).getMawbNo());
			parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
			parameters.put("userIp", (String) request.getSession().getAttribute("USER_IP"));
			tempVO = mgrService.selectInnerProductListAll(parameters);
			String path = request.getSession().getServletContext().getRealPath("/") + "IN_OUT/"
					+ currentTime.toLocalDate().toString() + "/";
			String fileName = "IN_" + txtTail + ".txt";
			String fileName2 = "OUT_" + txtTail + ".txt";
			// 파일 객체 생성
			File file = new File(path + fileName);
			File file2 = new File(path + fileName2);

			File dir = new File(path);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}

			String officeCode = "10610";
			String inYear = Integer.toString(currentDate.getYear());
			String serialNum = "";
			String outType = "11";
			String outTargetCode = "    ";
			String ItemCode = "";
			String ItemDetail = "";
			String unitPackage = "EA";
			String itemCnt = "";
			String wtValue = "";
			String outDate = "";
			String makeCntry = "KRSEL";
			String storageLocation = String.format("%-50s", "BA04");
			String storageEndDate = "";
			String cargoOwnerCode = "BA04";
			String cargoOwner = String.format("%-100s", "ACI");
			String outReason = String.format("%-100s", "반출");
			String productSign = "A";
			String outProductSign = "A";
			String dataType = "I";
			String outDataType = "I";
			String reportNumber = "";
			String resultVal = "";
			String dataCreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
			String EOF = "*";

			int tempSize = tempVO.size();
			// true 지정시 파일의 기존 내용에 이어서 작성
			if (tempSize != 0) {
				FileWriter fw = new FileWriter(file, false);
				FileWriter fw2 = new FileWriter(file2, false);
				try {
					for (int i = 0; i < tempSize; i++) {
						resultVal = "";
						serialNum = tempVO.get(i).getCarryInNum();
						ItemCode = String.format("%-20s", serialNum);
						ItemDetail = String.format("%-200s", tempVO.get(i).getItemDetail());
						unitPackage = tempVO.get(i).getQtyUnit();
						itemCnt = String.format("%010d", Integer.parseInt(tempVO.get(i).getItemCnt()));
						if (tempVO.get(i).getWta() != null && tempVO.get(i).getWtc() == null) {
							wtValue = tempVO.get(i).getWta();
						} else if (tempVO.get(i).getWta() == null && tempVO.get(i).getWtc() != null) {
							wtValue = tempVO.get(i).getWtc();
						} else if (tempVO.get(i).getWta() != null && tempVO.get(i).getWtc() != null) {
							if (Double.parseDouble(tempVO.get(i).getWta()) > Double
									.parseDouble(tempVO.get(i).getWtc())) {
								wtValue = tempVO.get(i).getWta();
							} else {
								wtValue = tempVO.get(i).getWtc();
							}
						}

						wtValue = String.format("%016.1f", Double.parseDouble(wtValue));
						outDate = tempVO.get(i).getInDate();
						makeCntry = "KRSEL";
						storageEndDate = tempVO.get(i).getEndDate();
						String txt = officeCode + inYear + serialNum + ItemCode + ItemDetail + unitPackage + itemCnt
								+ wtValue + outDate + makeCntry + storageLocation + storageEndDate + cargoOwnerCode
								+ cargoOwner + productSign + dataType + dataCreateDate + EOF + "\n";
						// serialNum.substring(2, serialNum.length())
						// reportNumber = makeReportNum("274474");

						reportNumber = count36(Integer.parseInt(serialNum));
						for (int j = 0; j < 6 - reportNumber.length(); j++) {
							resultVal = resultVal + "0";
						}
						resultVal = resultVal + reportNumber;

						String txt2 = officeCode + inYear + serialNum + inYear + serialNum + outType + outTargetCode
								+ ItemCode + ItemDetail + itemCnt + wtValue + outDate + cargoOwnerCode + cargoOwner
								+ outReason + outProductSign + outDataType + dataCreateDate
								+ (officeCode + inYear.substring(0, 2) + resultVal + "X") + "   " + EOF + "\n";

//						String txt2 = officeCode+inYear+serialNum+inYear+serialNum+outType+outTargetCode+ItemCode+ItemDetail+
//								itemCnt+wtValue+outDate+cargoOwnerCode+cargoOwner+outReason+outProductSign+outDataType+dataCreateDate+(officeCode+inYear.substring(0, 2)+serialNum.substring(2, serialNum.length())+"X")+"  "+EOF+"\n";

						fw.write(txt);
						fw2.write(txt2);
						fw.flush();
						fw2.flush();
					}

					// 객체 닫기
					mgrService.updateInnerProductList(parameters);
//					sendFtpServer("183.102.113.12",21,"ACI","kctdiftp1741#!","/ACI/EXPORT/IN",path,fileName);
//					sendFtpServer("183.102.113.12",21,"ACI","kctdiftp1741#!","/ACI/EXPORT/OUT",path,fileName2);
					returnVal = returnVal + "MAWB_NO : " + mawbList.get(mawbIndex).getMawbNo() + "가 등록되었습니다.\n";
				} catch (Exception e) {
					logger.error("Exception", e);
					fw = new FileWriter(file, false);
					fw2 = new FileWriter(file2, false);
					returnVal = returnVal + "MAWB_NO : " + mawbList.get(mawbIndex).getMawbNo() + "가 등록에 실패 하였습니다.\n";
				} finally {
					fw.close();
					fw2.close();
				}

			} else {
				returnVal = returnVal + "MAWB_NO : " + mawbList.get(mawbIndex).getMawbNo() + "에 등록할 대상이 없습니다.\n";
			}
		}
		return returnVal;
	}

	public boolean sendFtpServer(String ip, int port, String id, String password, String folder, String localPath,
			String file) {
		boolean isSuccess = false;
		FTPClient ftp = null;
		int reply;
		try {
			ftp = new FTPClient();
			ftp.connect(ip, port);
			System.out.println("Connected to " + ip + " on " + ftp.getRemotePort());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			if (!ftp.login(id, password)) {
				ftp.logout();
				throw new Exception("ftp 서버에 로그인하지 못했습니다.");
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			try {
				ftp.makeDirectory(folder);
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			ftp.changeWorkingDirectory(folder);

			// ftp서버에 한글파일을 쓸때 한글깨짐 방지
			String tempFileName = new String(file.getBytes("utf-8"), "iso_8859_1");
			String sourceFile = localPath + file;
			File uploadFile = new File(sourceFile);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(uploadFile);
				// tempFileName 업로드 될 타겟의 풀 경로가 들어가야 함. ex) /A/A01/A001/aa.zip 파일명만 들어갈경우 unix에서
				// 전송 실패하는 경우가 생김.
				isSuccess = ftp.storeFile(tempFileName, fis);
			} catch (IOException e) {
				logger.error("Exception", e);
				isSuccess = false;
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			} // end try

			ftp.logout();
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
				}
			}
		}
		return isSuccess;
	}

	public String makeReportNum(String targetNum) {
		String[] aphList = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		int aphNumTarget = Integer.parseInt(targetNum.substring(0, targetNum.length() - 4));
		int aphNumOne = aphNumTarget / 26;
		int aphNumTwo = aphNumTarget % 26;
		String rtnVal = aphList[aphNumOne] + aphList[aphNumTwo] + targetNum.substring(2, targetNum.length());
		return rtnVal;
	}

	public String count36(int targetNum) {
		String[] aphList = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String ss = "";

		int targetOne = targetNum / 36;
		int targetTwo = targetNum % 36;

		if (targetOne != 0) {
			ss = count36(targetOne);
		}

		ss = ss + aphList[targetTwo];

		return ss;
	}

	@RequestMapping(value = "/mngr/aplctList/updateTrkNos", method = RequestMethod.POST)
	@ResponseBody
	public String userUpdateTrkNos(HttpServletRequest request, HttpServletResponse response, Model model,
			UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		for (int i = 0; i < request.getParameterValues("trkNo").length; i++) {
			mgrService.updateItemTrkNo(request.getParameterValues("trkNo")[i], userOrderList.getNno(),
					userOrderItemList.getSubNo()[i]);
		}
		return "F";
	}

	@RequestMapping(value = "/mngr/send/stockOutListByMonth", method = RequestMethod.GET)
	public String managerStockOutList2(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");
		String searchUserId = request.getParameter("searchUserId");
		String depMonth = request.getParameter("depMonth");
		String dlvType = request.getParameter("dlvType");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);
		parameterInfo.put("depMonth", depMonth);

		if (dlvType != null) {
			System.out.println(dlvType);
			parameterInfo.put("dlvType", dlvType);
		}

		ArrayList<StockOutVO> stockOutInfo = new ArrayList<StockOutVO>();
		stockOutInfo = mgrService.stockOutListByMonth(parameterInfo);

		model.addAttribute("searchUserId", searchUserId);
		model.addAttribute("stockOutInfo", stockOutInfo);

		return "adm/send/stockOutListByMonth";
	}

	@RequestMapping(value = "/mngr/send/stockOutByUserId", method = RequestMethod.GET)
	public String managerStockOutList3(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");
		String depMonth = request.getParameter("depMonth");
		String dlvType = request.getParameter("dlvType");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);
		parameterInfo.put("depMonth", depMonth);

		if (dlvType != null) {
			System.out.println(dlvType);
			parameterInfo.put("dlvType", dlvType);
		}

		ArrayList<StockOutVO> stockOutInfo = new ArrayList<StockOutVO>();
		stockOutInfo = mgrService.selectStockOutByUserId(parameterInfo);

		model.addAttribute("stockOutInfo", stockOutInfo);

		return "adm/send/stockOutByUserId";

	}

	@RequestMapping(value = "/mngr/send/stockOutListExcelDown", method = RequestMethod.GET)
	public void stockOutListExcelDown(StockOutVO stockOutVO, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", request.getParameter("orgStation"));
		parameterInfo.put("depMonth", request.getParameter("depMonth"));
		parameterInfo.put("userId", request.getParameter("userId"));

		mgrService.selectStockOutListExcel(stockOutVO, response, request, parameterInfo);

	}

	@RequestMapping(value = "/mngr/send/stockOutListByMonthExcel", method = RequestMethod.GET)
	@ResponseBody
	public void stockOutListByMonthExcel(StockOutVO stockOutVO, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", request.getParameter("org_station"));
		parameterInfo.put("depMonth", request.getParameter("dep_month"));
		System.out.println(request.getParameter("org_station"));
		System.out.println(request.getParameter("dep_month"));

		String[] userId = request.getParameterValues("user_id");
		System.out.println(userId);
		int cnt = userId.length;

		if (cnt == 1) {
			parameterInfo.put("userId", userId[0]);
		}

		mgrService.stockOutListByMonthExcel(stockOutVO, response, request, parameterInfo);
	}

	@RequestMapping(value = "/mngr/send/stockOutListByUserIdExcel", method = RequestMethod.GET)
	@ResponseBody
	public void stockOutListByUserIdExcel(StockOutVO stockOutVO, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");
		String depMonth = request.getParameter("depMonth");

		System.out.println(orgStation);
		System.out.println(userId);
		System.out.println(depMonth);

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);
		parameterInfo.put("depMonth", depMonth);

		mgrService.stockOutListByUserIdExcel(stockOutVO, response, request, parameterInfo);
	}
	/*
	 * @RequestMapping(value = "/mngr/send/stockOutStatus", method =
	 * RequestMethod.GET) public String getStockOutStatus(HttpServletRequest
	 * request, HttpServletResponse response, Model model) throws Exception {
	 * 
	 * HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
	 * 
	 * ArrayList<HashMap<String, Object>> stationCodeList = new
	 * ArrayList<HashMap<String, Object>>(); stationCodeList =
	 * mgrService.selectStationCodeList(parameterInfo);
	 * 
	 * parameterInfo.put("orgStation", request.getParameter("orgStation"));
	 * parameterInfo.put("depMonth", request.getParameter("depMonth"));
	 * parameterInfo.put("userId", request.getParameter("userId"));
	 * parameterInfo.put("year", request.getParameter("selectedOption"));
	 * 
	 * parameterInfo.put("fromDate", request.getParameter("fromDate"));
	 * parameterInfo.put("toDate", request.getParameter("toDate"));
	 * 
	 * int curPage = 1; int totalCount =
	 * mgrService.selectStockOutListCnt(parameterInfo);
	 * 
	 * if (request.getParameter("page") != null) { curPage =
	 * Integer.parseInt(request.getParameter("page")); }
	 * 
	 * PagingVO paging = new PagingVO(curPage, totalCount, 5, 15);
	 * parameterInfo.put("paging", paging);
	 * 
	 * ArrayList<StockOutVO> stockOutChartList = new ArrayList<StockOutVO>();
	 * stockOutChartList = mgrService.selectStockOutListChart(parameterInfo);
	 * 
	 * Gson gson = new Gson(); JsonArray jsonArray = new JsonArray();
	 * 
	 * Iterator<StockOutVO> iterator = stockOutChartList.iterator(); while
	 * (iterator.hasNext()) { StockOutVO stockOutVO = iterator.next(); JsonObject
	 * jsonObject = new JsonObject();
	 * 
	 * String depMonth = stockOutVO.getDepMonth(); String stationName =
	 * stockOutVO.getStationName(); int cnt = stockOutVO.getCnt();
	 * 
	 * jsonObject.addProperty("depMonth", depMonth);
	 * jsonObject.addProperty("stationName", stationName);
	 * jsonObject.addProperty("cnt", cnt); jsonArray.add(jsonObject); }
	 * 
	 * String json = gson.toJson(jsonArray);
	 * 
	 * model.addAttribute("json", json); model.addAttribute("paging", paging);
	 * model.addAttribute("stationCodeList", stationCodeList);
	 * model.addAttribute("parameterInfo", parameterInfo);
	 * 
	 * return "adm/send/stockOutStatus"; }
	 */

	@RequestMapping(value = "/mngr/send/stockOutList", method = RequestMethod.GET)
	public String getstockOutList2(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<StockOutVO> stockOutList = new ArrayList<StockOutVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> stationCodeList = new ArrayList<HashMap<String, Object>>();
		stationCodeList = mgrService.selectStationCodeList(parameterInfo);

		String orgStation = request.getParameter("orgStation");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String userId = request.getParameter("searchUserId");

		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("fromDate", fromDate);
		parameterInfo.put("toDate", toDate);
		parameterInfo.put("searchUserId", userId);

		int curPage = 1;
		int totalCount = mgrService.selectStockOutListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 5, 20);
		parameterInfo.put("paging", paging);

		try {
			stockOutList = mgrService.selectStockOutList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		model.addAttribute("searchUserId", request.getParameter("searchUserId"));
		model.addAttribute("paging", paging);
		model.addAttribute("stationCodeList", stationCodeList);
		model.addAttribute("stockOutList", stockOutList);
		model.addAttribute("parameterInfo", parameterInfo);

		return "adm/send/stockOutList2";
	}

	@RequestMapping(value = "/mngr/send/stockOutExcelDown", method = RequestMethod.GET)
	public void downloadStockOutExcel(StockOutVO stockOutVO, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("fromDate", fromDate);
		parameterInfo.put("toDate", toDate);
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);

		mgrService.stockOutExcelDown(stockOutVO, request, response, parameterInfo);

	}

	@RequestMapping(value = "/mngr/send/stockOutChartMonth", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectClickedChartData(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();

		parameterInfo.put("fromDate", request.getParameter("start"));
		parameterInfo.put("toDate", request.getParameter("toDate"));
		parameterInfo.put("depMonth", request.getParameter("depMonth"));
		parameterInfo.put("stationName", request.getParameter("stationName"));

		ArrayList<StockOutVO> list = new ArrayList<StockOutVO>();
		list = mgrService.selectClickedChart(parameterInfo);

		result.put("list", list);

		return result;
	}

	@RequestMapping(value = "/mngr/send/stockOut", method = RequestMethod.GET)
	public String managerSendStockOut(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		ArrayList<StockOutVO> stockOutList = new ArrayList<StockOutVO>();
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("fromDate", request.getParameter("fromDate"));
		parameterInfo.put("toDate", request.getParameter("toDate"));
		parameterInfo.put("searchUserId", request.getParameter("searchUserId"));

		if (request.getParameter("orgStation") == null) {
			parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			System.out.println("orgStation is null");
		} else {
			parameterInfo.put("orgStation", request.getParameter("orgStation"));
			System.out.println(parameterInfo.get("orgStation"));
		}

		if (request.getParameter("dlvType") != null) {
			parameterInfo.put("dlvType", request.getParameter("dlvType"));
			System.out.println(parameterInfo.get("dlvType"));
		} else {
			System.out.println("dlvType is null");
		}

		ArrayList<HashMap<String, Object>> stationCodeList = new ArrayList<HashMap<String, Object>>();
		stationCodeList = mgrService.selectStationCodeList(parameterInfo);

		int curPage = 1;
		int totalCount = mgrService.selectStockOutListCnt(parameterInfo);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 5, 20);
		parameterInfo.put("paging", paging);

		try {
			stockOutList = mgrService.selectStockOutList(parameterInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String nowYear = formatter.format(date);
		
		SimpleDateFormat formatter2 = new SimpleDateFormat("MM");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		String nowMonth = formatter2.format(cal.getTime());

		ArrayList<HashMap<String, Object>> monthList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < 12; i++) {
			HashMap<String, Object> monthInfo = new HashMap<String, Object>();
			String month = String.valueOf(i+1);
			if (month.length() < 2) {
				month = "0"+month;
			}
			monthInfo.put("month", month);
			monthInfo.put("monthValue", i+1);
			monthList.add(monthInfo);
		}
		

		model.addAttribute("searchUserId", request.getParameter("searchUserId"));
		model.addAttribute("paging", paging);
		model.addAttribute("stationCodeList", stationCodeList);
		model.addAttribute("stockOutList", stockOutList);
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("monthList", monthList);
		model.addAttribute("sekYear", nowYear);
		model.addAttribute("sekMonth", nowMonth);
		model.addAttribute("station", (String) request.getSession().getAttribute("ORG_STATION"));

		return "adm/send/stockOut";

	}
	
	@RequestMapping(value = "/mngr/send/monthReportExcelDown", method = RequestMethod.GET)
	public void monthReportExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		mgrService.monthReportExcelDown(request, response);
	}

	@RequestMapping(value = "/mngr/send/stockOutListByMonth2", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerStockOutByMonth(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");
		String searchUserId = request.getParameter("searchUserId");
		String depMonth = request.getParameter("depMonth");
		String dlvType = request.getParameter("dlvType");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);
		parameterInfo.put("depMonth", depMonth);

		if (dlvType != null) {
			parameterInfo.put("dlvType", dlvType);
		}

		ArrayList<StockOutVO> stockOutInfo = new ArrayList<StockOutVO>();
		stockOutInfo = mgrService.stockOutListByMonth(parameterInfo);

		result.put("stockOutInfo", stockOutInfo);

		return result;

	}

	@RequestMapping(value = "/mngr/send/stockOutByUserId2", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerStockOutByUserId(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		String orgStation = request.getParameter("orgStation");
		String userId = request.getParameter("userId");
		String depMonth = request.getParameter("depMonth");
		String dlvType = request.getParameter("dlvType");

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("userId", userId);
		parameterInfo.put("depMonth", depMonth);

		if (dlvType != null) {
			parameterInfo.put("dlvType", dlvType);
		}

		ArrayList<StockOutVO> stockOutInfo = new ArrayList<StockOutVO>();
		stockOutInfo = mgrService.selectStockOutByUserId(parameterInfo);

		result.put("stockOutInfo", stockOutInfo);

		return result;
	}

	@RequestMapping(value = "/mngr/send/stockOutStatus", method = RequestMethod.GET)
	public String managerStockOutTest(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Date time = new Date();
		String month = format.format(time);

		MemberVO member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		// params.put("userId", member.getUsername());
		params.put("userId", request.getParameter("userId"));

		if (request.getParameter("depMonth") == null) {
			params.put("depMonth", month);
		} else {
			params.put("depMonth", request.getParameter("depMonth"));
		}

		ArrayList<StockOutVO> list = new ArrayList<StockOutVO>();
		list = mgrService.selectUserStockOutList(params);

		Gson gson = new Gson();
		JsonArray jsonArray = new JsonArray();

		Iterator<StockOutVO> iterator = list.iterator();
		while (iterator.hasNext()) {
			StockOutVO stockOutVO = iterator.next();
			JsonObject jsonObject = new JsonObject();

			String userId = stockOutVO.getUserId();
			int cnt = stockOutVO.getCnt();

			jsonObject.addProperty("userId", userId);
			jsonObject.addProperty("cnt", cnt);
			jsonArray.add(jsonObject);
		}

		String json = gson.toJson(jsonArray);

		ArrayList<HashMap<String, Object>> transList = new ArrayList<HashMap<String, Object>>();
		transList = mgrService.selectTransCodeFilter(params);

		JsonArray jsonArray2 = new JsonArray();
		for (Map<String, Object> map : transList) {
			JsonObject obj = new JsonObject();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					obj.addProperty(key, String.valueOf(value));
				} catch (JSONException e) {
					logger.error("Exception", e);
				}
			}

			jsonArray2.add(obj);
		}

		int transTotalCnt = mgrService.selectTransCodeTotalCnt(params);
		int inCnt = mgrService.selectInBoundCnt(params);
		int outCnt = mgrService.selectOutBoundCnt(params);

		HashMap<String, Object> params2 = new HashMap<String, Object>();
		params2.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		ArrayList<StockOutVO> stockOutList = new ArrayList<StockOutVO>();
		stockOutList = mgrService.selectStockOutMonthlyList(params2);
		Gson stockOutGson = new Gson();
		JsonArray stockOutArray = new JsonArray();

		Iterator<StockOutVO> iter = stockOutList.iterator();
		while (iter.hasNext()) {
			StockOutVO vo = iter.next();
			JsonObject jsonObject = new JsonObject();
			String depMonth = vo.getDepMonth();
			String stationName = vo.getStationName();
			int cnt = vo.getCnt();

			jsonObject.addProperty("depMonth", depMonth);
			jsonObject.addProperty("stationName", stationName);
			jsonObject.addProperty("cnt", cnt);
			stockOutArray.add(jsonObject);
		}
		String stockOutJson = stockOutGson.toJson(stockOutArray);

		ArrayList<HashMap<String, Object>> dailyChart = new ArrayList<HashMap<String, Object>>();
		dailyChart = mgrService.selectDailyChart(params);

		JsonArray dailyArray = new JsonArray();
		for (Map<String, Object> map : dailyChart) {
			JsonObject obj = new JsonObject();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					obj.addProperty(key, String.valueOf(value));
				} catch (JSONException e) {
					logger.error("Exception", e);
				}
			}

			dailyArray.add(obj);
		}

		model.addAttribute("dailyChart", dailyArray);
		model.addAttribute("stockOut", stockOutJson);
		model.addAttribute("inCnt", inCnt);
		model.addAttribute("outCnt", outCnt);
		model.addAttribute("transTotalCnt", transTotalCnt);
		model.addAttribute("json2", jsonArray2);
		model.addAttribute("json", json);
		model.addAttribute("params", params);

		return "adm/send/stockOutChart";
	}

	@RequestMapping(value = "/mngr/send/sendYearData", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerSendStockOutData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();

		String depMonth = request.getParameter("sendData");
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		params.put("depMonth", depMonth);
		params.put("orgStation", orgStation);

		ArrayList<HashMap<String, Object>> dailyList = new ArrayList<HashMap<String, Object>>();
		dailyList = mgrService.selectDailyChart(params);

		result.put("dailyList", dailyList);

		return result;
	}

	@RequestMapping(value = "/mngr/rls/manifest", method = RequestMethod.GET)
	public String managerManifest(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> infoMap = new HashMap<String, Object>();

		String mawbNo = request.getParameter("mawbNo");
		infoMap.put("mawbNo", mawbNo);

		int curPage = 1;
		int totalCount = mgrService.selectMawbManifestListCnt(infoMap);

		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 10, 15);

		infoMap.put("paging", paging);
		ArrayList<HashMap<String, Object>> mawbList = new ArrayList<HashMap<String, Object>>();
		mawbList = mgrService.selectMawbManifestList(infoMap);

		model.addAttribute("params", infoMap);
		model.addAttribute("paging", paging);
		model.addAttribute("mawbList", mawbList);

		return "adm/rls/mawbList/manifestList";
	}

	@RequestMapping(value = "/mngr/rls/searchItemCode", method = RequestMethod.GET)
	public String managerSearchHsCode(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		ArrayList<HashMap<String, Object>> codeList = new ArrayList<HashMap<String, Object>>();
		codeList = usrService.selectHsCodeList();
		model.addAttribute("codeList", codeList);

		return "adm/rls/mawbList/searchItemCode";
	}

	@RequestMapping(value = "/mngr/rls/updateHistory", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerManiUpdateHistory(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String status = "";
		String mawbNo = request.getParameter("mawbNo");
		ArrayList<HashMap<String, Object>> changeList = new ArrayList<HashMap<String, Object>>();

		try {
			changeList = mgrService.selectChangeOrderList(mawbNo);
			status = "S";
		} catch (Exception e) {
			logger.error("Exception : ", e);
			status = "F";
		}

		result.put("status", status);
		result.put("changeList", changeList);

		return result;
	}

	@RequestMapping(value = "/mngr/rls/updateHawbInfo", method = RequestMethod.GET)
	public String managerUpdateHisView(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String nno = request.getParameter("nno");

		ArrayList<UserOrderListVO> orderList = new ArrayList<UserOrderListVO>();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> firstOrderItem = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> firstOrderInfo = new LinkedHashMap<String, Object>();
		try {
			orderList = mgrService.selectChangeOrderInfo(nno);
			for (int i = 0; i < orderList.size(); i++) {
				orderList.get(i).dncryptData();
				if (orderList.get(i).getNno().length() > 0) {
					orderInfo.put("nno", orderList.get(i).getNno());
				} else {
					orderInfo.put("nno", "");
				}
				if (orderList.get(i).getOrderNo().length() > 0) {
					orderInfo.put("orderNo", orderList.get(i).getOrderNo());
				} else {
					orderInfo.put("orderNo", "");
				}
				if (orderList.get(i).getOrderDate().length() > 0) {
					orderInfo.put("orderDate", orderList.get(i).getOrderDate());
				} else {
					orderInfo.put("orderDate", "");
				}
				if (!orderList.get(i).getBoxCnt().toString().equals("0")) {
					orderInfo.put("boxCnt", orderList.get(i).getBoxCnt());
				} else {
					orderInfo.put("boxCnt", "");
				}
				if (orderList.get(i).getShipperName().length() > 0) {
					orderInfo.put("shipperName", orderList.get(i).getShipperName());
				} else {
					orderInfo.put("shipperName", "");
				}
				if (orderList.get(i).getShipperAddr().length() > 0) {
					orderInfo.put("shipperAddr", orderList.get(i).getShipperAddr());
				} else {
					orderInfo.put("shipperAddr", "");
				}
				if (orderList.get(i).getCneeName().length() > 0) {
					orderInfo.put("cneeName", orderList.get(i).getCneeName());
				} else {
					orderInfo.put("cneeName", "");
				}
				if (orderList.get(i).getCneeAddr().length() > 0) {
					orderInfo.put("cneeAddr", orderList.get(i).getCneeAddr());
				} else {
					orderInfo.put("cneeAddr", "");
				}
				if (orderList.get(i).getCneeZip().length() > 0) {
					orderInfo.put("cneeZip", orderList.get(i).getCneeZip());
				} else {
					orderInfo.put("cneeZip", "");
				}
				if (orderList.get(i).getCneeTel().length() > 0) {
					orderInfo.put("cneeTel", orderList.get(i).getCneeTel());
				} else {
					orderInfo.put("cneeTel", "");
				}
				if (orderList.get(i).getCneeHp().length() > 0) {
					orderInfo.put("cneeHp", orderList.get(i).getCneeHp());
				} else {
					orderInfo.put("cneeHp", "");
				}
				orderInfo.put("wDate", orderList.get(i).getWDate());
			}

			UserOrderListVO firstOrder = new UserOrderListVO();
			firstOrder = mgrService.selectFirstOrderInfo(nno);
			firstOrder.dncryptData();
			firstOrderInfo.put("nno", firstOrder.getNno());
			firstOrderInfo.put("hawbNo", firstOrder.getHawbNo());
			firstOrderInfo.put("transCode", firstOrder.getTransCode());
			firstOrderInfo.put("orderNo", firstOrder.getOrderNo());
			firstOrderInfo.put("orderDate", firstOrder.getOrderDate());
			firstOrderInfo.put("boxCnt", firstOrder.getBoxCnt());
			firstOrderInfo.put("shipperName", firstOrder.getShipperName());
			firstOrderInfo.put("shipperAddr", firstOrder.getShipperAddr());
			firstOrderInfo.put("cneeName", firstOrder.getCneeName());
			firstOrderInfo.put("cneeAddr", firstOrder.getCneeAddr());
			firstOrderInfo.put("cneeZip", firstOrder.getCneeZip());
			firstOrderInfo.put("cneeTel", firstOrder.getCneeTel());
			firstOrderInfo.put("cneeHp", firstOrder.getCneeHp());
			model.addAttribute("firstOrderList", firstOrderInfo);

			HashMap<String, Object> params = new HashMap<String, Object>();
			ArrayList<String> subNoList = new ArrayList<String>();
			params.put("nno", nno);
			orderItem = mgrService.selectChangeItemInfo(nno);
			for (int x = 0; x < orderItem.size(); x++) {
				subNoList.add(orderItem.get(x).get("subNo").toString());
			}
			params.put("subNo", subNoList);

			if (orderItem.size() > 0) {
				firstOrderItem = mgrService.selectFirstOrderItemInfo(params);
				model.addAttribute("firstOrderItem", firstOrderItem);
			}

			model.addAttribute("orderList", orderInfo);
			model.addAttribute("orderItem", orderItem);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		return "adm/rls/mawbList/updateList";
	}

	/*
	 * @RequestMapping(value = "/mngr/rls/updateHawbInfo", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public HashMap<String, Object>
	 * managerUpdateHawbInfo(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { HashMap<String, Object> view = new
	 * HashMap<String, Object>(); String nno = request.getParameter("nno"); String
	 * type = request.getParameter("type"); String status = "";
	 * 
	 * ArrayList<UserOrderListVO> orderList = new ArrayList<UserOrderListVO> ();
	 * ArrayList<LinkedHashMap<String, Object>> orderItem = new
	 * ArrayList<LinkedHashMap<String, Object>>(); ArrayList<LinkedHashMap<String,
	 * Object>> firstOrderItem = new ArrayList<LinkedHashMap<String, Object>>();
	 * LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String,
	 * Object>(); LinkedHashMap<String, Object> firstOrderInfo = new
	 * LinkedHashMap<String, Object>(); try { orderList =
	 * mgrService.selectChangeOrderInfo(nno); for (int i = 0; i < orderList.size();
	 * i++) { orderList.get(i).dncryptData(); if (orderList.get(i).getNno().length()
	 * > 0) { orderInfo.put("nno", orderList.get(i).getNno()); } else {
	 * orderInfo.put("nno", ""); } if (orderList.get(i).getOrderNo().length() > 0) {
	 * orderInfo.put("orderNo", orderList.get(i).getOrderNo()); } else {
	 * orderInfo.put("orderNo", ""); } if (orderList.get(i).getOrderDate().length()
	 * > 0) { orderInfo.put("orderDate", orderList.get(i).getOrderDate()); } else {
	 * orderInfo.put("orderDate", ""); } if
	 * (!orderList.get(i).getBoxCnt().toString().equals("0")) {
	 * orderInfo.put("boxCnt", orderList.get(i).getBoxCnt()); } else {
	 * orderInfo.put("boxCnt", ""); } if (orderList.get(i).getShipperName().length()
	 * > 0) { orderInfo.put("shipperName", orderList.get(i).getShipperName()); }
	 * else { orderInfo.put("shipperName", ""); } if
	 * (orderList.get(i).getShipperAddr().length() > 0) {
	 * orderInfo.put("shipperAddr", orderList.get(i).getShipperAddr()); } else {
	 * orderInfo.put("shipperAddr", ""); } if
	 * (orderList.get(i).getCneeName().length() > 0) { orderInfo.put("cneeName",
	 * orderList.get(i).getCneeName()); } else { orderInfo.put("cneeName", ""); } if
	 * (orderList.get(i).getCneeAddr().length() > 0) { orderInfo.put("cneeAddr",
	 * orderList.get(i).getCneeAddr()); } else { orderInfo.put("cneeAddr", ""); } if
	 * (orderList.get(i).getCneeZip().length() > 0) { orderInfo.put("cneeZip",
	 * orderList.get(i).getCneeZip()); } else { orderInfo.put("cneeZip", ""); } if
	 * (orderList.get(i).getCneeTel().length() > 0) { orderInfo.put("cneeTel",
	 * orderList.get(i).getCneeTel()); } else { orderInfo.put("cneeTel", ""); } if
	 * (orderList.get(i).getCneeHp().length() > 0) { orderInfo.put("cneeHp",
	 * orderList.get(i).getCneeHp()); } else { orderInfo.put("cneeHp", ""); }
	 * orderInfo.put("wDate", orderList.get(i).getWDate()); } UserOrderListVO
	 * firstOrder = new UserOrderListVO(); firstOrder =
	 * mgrService.selectFirstOrderInfo(nno); firstOrder.dncryptData();
	 * firstOrderInfo.put("nno", firstOrder.getNno()); firstOrderInfo.put("orderNo",
	 * firstOrder.getOrderNo()); firstOrderInfo.put("orderDate",
	 * firstOrder.getOrderDate()); firstOrderInfo.put("boxCnt",
	 * firstOrder.getBoxCnt()); firstOrderInfo.put("shipperName",
	 * firstOrder.getShipperName()); firstOrderInfo.put("shipperAddr",
	 * firstOrder.getShipperAddr()); firstOrderInfo.put("cneeName",
	 * firstOrder.getCneeName()); firstOrderInfo.put("cneeAddr",
	 * firstOrder.getCneeAddr()); firstOrderInfo.put("cneeZip",
	 * firstOrder.getCneeZip()); firstOrderInfo.put("cneeTel",
	 * firstOrder.getCneeTel()); firstOrderInfo.put("cneeHp",
	 * firstOrder.getCneeHp()); view.put("firstOrderList", firstOrderInfo);
	 * 
	 * 
	 * HashMap<String, Object> params = new HashMap<String, Object>();
	 * ArrayList<String> subNoList = new ArrayList<String>(); params.put("nno",
	 * nno); orderItem = mgrService.selectChangeItemInfo(nno); for (int x = 0; x <
	 * orderItem.size(); x++) {
	 * subNoList.add(orderItem.get(x).get("subNo").toString()); }
	 * params.put("subNo", subNoList);
	 * 
	 * if (orderItem.size() > 0) { firstOrderItem =
	 * mgrService.selectFirstOrderItemInfo(params); view.put("firstOrderItem",
	 * firstOrderItem); }
	 * 
	 * view.put("orderList", orderInfo); view.put("orderItem", orderItem); status =
	 * "S"; } catch (Exception e) { logger.error("Exception", e); status = "F"; }
	 * 
	 * view.put("type", type); view.put("status", status);
	 * 
	 * return view; }
	 */
	@RequestMapping(value = "/mngr/rls/manifest/excelDown", method = RequestMethod.GET)
	public void manifestExcelDown(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String mawbNo = request.getParameter("mawbNo");
		mgrService.orderInfoExcelDown(mawbNo, request, response);
	}

	@RequestMapping(value = "/mngr/rls/manifest/excelUp", method = RequestMethod.POST)
	@ResponseBody
	public String manifestExcelUp(HttpServletRequest request, HttpServletResponse response, Model model,
			MultipartHttpServletRequest multi) throws Exception {
		String result = "";

		try {
			result = mgrService.updateOrderExcel(multi, request, response);
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}

		return result;
	}

	@RequestMapping(value = "/mngr/rls/mawbManiExcelDown", method = RequestMethod.GET)
	public void managerMawbManiExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String mawbNo = request.getParameter("targets");
		mgrService.selectMawbExcelDown(mawbNo, request, response);
	}

	@RequestMapping(value = "/mngr/rls/type86Apply", method = RequestMethod.GET)
	public String managerType86Apply(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		String searchType = "";
		String searchKeywords = "";

		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}

		params.put("searchType", searchType);

		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeywords", request.getParameter("keywords"));

		if (searchType.equals("0")) {
			params.put("userId", searchKeywords);
			params.put("orderNo", searchKeywords);
			params.put("hawbNo", searchKeywords);
		} else if (searchType.equals("1")) {
			params.put("userId", searchKeywords);
			params.put("orderNo", "");
			params.put("hawbNo", "");
		} else if (searchType.equals("2")) {
			params.put("userId", "");
			params.put("orderNo", searchKeywords);
			params.put("hawbNo", "");
		} else {
			params.put("userId", "");
			params.put("orderNo", "");
			params.put("hawbNo", searchKeywords);
		}

		ArrayList<UserOrderListVO> orderList = new ArrayList<UserOrderListVO>();
		orderList = mgrService.selectOrderListT86(params);

		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}

		double totalWeightWta = 0;
		double totalWeightWtv = 0;

		ArrayList<HawbListVO> hawbList = new ArrayList<HawbListVO>();
		hawbList = mgrService.selectType86HawbList(params);

		for (int index = 0; index < hawbList.size(); index++) {
			hawbList.get(index).dncryptData();
			totalWeightWta = totalWeightWta + Double.parseDouble(hawbList.get(index).getWta());
			totalWeightWtv = totalWeightWtv + Double.parseDouble(hawbList.get(index).getWtc());
		}

		model.addAttribute("orderList", orderList);
		model.addAttribute("params", params);
		model.addAttribute("hawbList", hawbList);

		return "adm/rls/mawbList/type86Apply";
	}

	@RequestMapping(value = "/mngr/rls/sendType86Api", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerSendType86API(@RequestParam(value = "targets[]") ArrayList<String> targets,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		String[] orderNno = targets.toArray(new String[targets.size()]);

		try {
			for (int i = 0; i < orderNno.length; i++) {
				rst = type86Api.selectBlApplyT86(orderNno[i], userId, userIp);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			rst.put("STATUS", "FAIL");
		}

		return rst;
	}
	
	@RequestMapping(value = "/mngr/rls/sendParcllApi", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerSendParcllApi(@RequestParam(value = "targets[]") ArrayList<String> targets, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		String[] orderNno = targets.toArray(new String[targets.size()]);

		try {
			for (int i = 0; i < orderNno.length; i++) {
				rst = pclApi.createShipment(orderNno[i], userId, userIp);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			rst.put("STATUS", "FAIL");
		}

		return rst;
	}

	@RequestMapping(value = "/mngr/order/unRegOrderList", method = RequestMethod.GET)
	public String managerUnRegistOrderList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		ArrayList<HashMap<String, Object>> orderList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", orgStation);
		params.put("orderNo", request.getParameter("orderNo"));
		params.put("userId", request.getParameter("userId"));

		orderList = mgrService.selectOrderListForErr(params);

		model.addAttribute("orderList", orderList);
		model.addAttribute("params", params);

		return "adm/aplctList/list/unShpngList";
	}

	@RequestMapping(value = "/mngr/order/cusItemCodeDownExcel", method = RequestMethod.GET)
	public void managerCusItemCodeExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ViewYslItemCode> viewList = new ArrayList<ViewYslItemCode>();
		viewList = apiService.selectViewYslItem();
		if (viewList.size() > 0) {
			mgrService.cusItemCodeExcelDown(request, response, viewList);
		}

	}

	@RequestMapping(value = "/mngr/board/noticeList", method = RequestMethod.GET)
	public String mngrBoardNoticeList(HttpServletRequest request, HttpServletResponse respones, Model model)
			throws Exception {

		ArrayList<WhNoticeVO> noticeList = new ArrayList<WhNoticeVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		ArrayList<HashMap<String, Object>> noticeList2 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> whNotice = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();

		if (request.getParameter("category") != null) {
			if (request.getParameter("category") != "") {
				params.put("category", request.getParameter("category"));
			}
		}

		String searchKeyword = "";
		String searchType = "";
		if (request.getParameter("searchType") != null) {
			searchType = request.getParameter("searchType");
			searchKeyword = request.getParameter("searchKeyword");
		}

		params.put("searchType", searchType);
		params.put("searchKeyword", searchKeyword);

		if (searchType.equals("userId")) {
			params.put("userId", searchKeyword);
			params.put("title", "");
		} else if (searchType.equals("title")) {
			params.put("title", searchKeyword);
			params.put("userId", "");
		} else {
			params.put("title", searchKeyword);
			params.put("userId", searchKeyword);
		}

		int curPage = 1;
		int totalCnt = mgrService.selectWhNoticeListCnt(params);

		if (request.getParameter("page") != null)
			curPage = Integer.parseInt(request.getParameter("page"));

		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
		params.put("paging", paging);

		noticeList = mgrService.selectWhNoticeList(params);

		for (int i = 0; i < noticeList.size(); i++) {
			whNotice = new HashMap<String, Object>();
			whNotice.put("idx", noticeList.get(i).getIdx());
			whNotice.put("groupIdx", noticeList.get(i).getGroupIdx());
			whNotice.put("status", noticeList.get(i).getStatus());
			whNotice.put("category", noticeList.get(i).getCategory());
			whNotice.put("title", noticeList.get(i).getTitle());
			whNotice.put("indent", noticeList.get(i).getIndent());
			whNotice.put("WUserId", noticeList.get(i).getWUserId());
			whNotice.put("WDate", noticeList.get(i).getWDate());
			fileList = mgrService.selectWhNoticeFiles(noticeList.get(i).getIdx());
			whNotice.put("fileList", fileList);
			noticeList2.add(whNotice);
		}

		model.addAttribute("params", params);
		model.addAttribute("paging", paging);
		model.addAttribute("noticeList", noticeList2);
		return "adm/board/noticeList";
	}

	@RequestMapping(value = "/mngr/board/noticeIn", method = RequestMethod.GET)
	public String mngrBoardNoticeInGet(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		return "adm/board/noticeIn";
	}

	@RequestMapping(value = "/mngr/board/noticeIn", method = RequestMethod.POST)
	public String mngrBoardNoticeInPost(HttpServletRequest request, MultipartHttpServletRequest multi)
			throws Exception {
		mgrService.insertWhNoticeInfo(request, multi);
		return "redirect:/mngr/board/noticeList";
	}

	@RequestMapping(value = "/mngr/board/noticeDetail", method = RequestMethod.GET)
	public String mngrBoardNoticeDetail(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int groupIdx = Integer.parseInt(request.getParameter("groupIdx"));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", idx);
		params.put("groupIdx", groupIdx);

		WhNoticeVO noticeDetail = new WhNoticeVO();
		noticeDetail = mgrService.selectWhNoticeDetail(params);

		int fileYn = mgrService.selectWhNoticeFileYn(params);

		ArrayList<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();

		if (fileYn > 0) {
			fileList = mgrService.selectWhNoticeFileList(params);
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("noticeDetail", noticeDetail);
		model.addAttribute("idx", idx);
		model.addAttribute("userId", (String) request.getSession().getAttribute("USER_ID"));

		return "adm/board/noticeDetail";
	}

	@RequestMapping(value = "/mngr/board/noticeUp", method = RequestMethod.POST)
	public String mngrBoardNoticeUp(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest multi) throws Exception {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int groupIdx = Integer.parseInt(request.getParameter("groupIdx"));
		mgrService.updateWhNoticeInfo(request, multi);
		return "redirect:/mngr/board/noticeDetail?idx=" + idx + "&groupIdx=" + groupIdx;
	}

	@RequestMapping(value = "/mngr/board/noticeFileDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrBoardNoticeFileDel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", Integer.parseInt(request.getParameter("idx")));
		params.put("noticeIdx", Integer.parseInt(request.getParameter("noticeIdx")));

		try {
			mgrService.noticeFileDel(params);
			rst.put("status", "S");
			rst.put("msg", "삭제 되었습니다.");
		} catch (Exception e) {
			rst.put("status", "F");
			rst.put("msg", "삭제 중 오류가 발생 하였습니다. 다시 시도해 주세요.");
		}

		return rst;
	}

	@RequestMapping(value = "/mngr/board/noticeReplyIn", method = RequestMethod.GET)
	public String mngrBoardNoticeReplyInGet(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int groupIdx = Integer.parseInt(request.getParameter("groupIdx"));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", idx);
		params.put("groupIdx", groupIdx);
		WhNoticeVO noticeInfo = new WhNoticeVO();
		noticeInfo = mgrService.selectWhNoticeDetail(params);

		model.addAttribute("noticeInfo", noticeInfo);
		return "adm/board/noticeReplyIn";
	}

	@RequestMapping(value = "/mngr/board/noticeReplyIn", method = RequestMethod.POST)
	public String mngrBoardNoticeReplyInPost(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest multi) throws Exception {
		mgrService.insertWhNoticeReplyIn(request, multi);

		return "redirect:/mngr/board/noticeList";
	}

	@RequestMapping(value = "/mngr/board/noticeDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrBoardNoticeDel(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("idx", Integer.parseInt(request.getParameter("idx")));
		params.put("groupIdx", Integer.parseInt(request.getParameter("groupIdx")));
		params.put("step", Integer.parseInt(request.getParameter("step")));
		params.put("indent", Integer.parseInt(request.getParameter("indent")));
		HashMap<String, Object> rst = new HashMap<String, Object>();
		try {
			rst = mgrService.execNoticeDel(params);
		} catch (Exception e) {
			rst.put("rstStatus", "FAIL");
			rst.put("rstCode", "F10");
			rst.put("rstMsg", "처리 중 오류가 발생 하였습니다. 다시 시도해주세요.");
		}
		return rst;
	}
	
	@RequestMapping(value = "/mngr/printCommercial", method = RequestMethod.GET)
	public void userPrintCommercial(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String printType = request.getParameter("formType");
		String[] targetTmp = request.getParameter("targetInfo").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		mgrService.printCommercialPdf(request, response, orderNnoList, printType);
	}
	
	@RequestMapping(value = "/mngr/printCommercialExcel", method = RequestMethod.GET)
	public void userPrintCommercialExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		mgrService.printCommercialExcel(request,response, orderNnoList,printType);
	}
	
	@RequestMapping(value = "/mngr/printPackingListExcel", method = RequestMethod.GET)
	public void userPrintPackingListExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//comnPrintHawbLegacy(request, response, model);
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		mgrService.printPackingListExcel(request,response, orderNnoList,printType);
	}

	@RequestMapping(value = "/mngr/rls/changeLabel", method = RequestMethod.GET)
	public String mngrChangeLabel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		
		String searchType = "";
		String searchKeyword = "";
		
		int curPage = 1;
		
		if (request.getParameter("keyword") != null) {
			searchType = request.getParameter("searchType");
			searchKeyword = request.getParameter("keyword");
		}
		
		parameterInfo.put("searchType", searchType);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		
		if (searchType.equals("0")) {
			parameterInfo.put("userId", searchKeyword);
			parameterInfo.put("hawbNo", searchKeyword);
		} else if (searchType.equals("1")) {
			parameterInfo.put("userId", searchKeyword);
			parameterInfo.put("hawbNo", "");
		} else if (searchType.equals("2")) {
			parameterInfo.put("userId", "");
			parameterInfo.put("hawbNo", searchKeyword);
		}
		
		int totalCount = mgrService.selectOrderListLabelChangeCnt(parameterInfo);
		
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage, totalCount, 10, 15);
		parameterInfo.put("paging", paging);
		
		ArrayList<UserOrderListVO> orderList = new ArrayList<UserOrderListVO>();
		orderList = mgrService.selectOrderListLabelChange(parameterInfo);
		
		
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}
		
		model.addAttribute("parameterInfo", parameterInfo);
		model.addAttribute("paging", paging);
		model.addAttribute("orderList", orderList);
		
		return "adm/rls/mawbList/changeLabel";
	}
	
	@RequestMapping(value = "/mngr/rls/changeLabelList", method = RequestMethod.GET)
	public String mngrChangeLabelList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		parameterInfo.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));

		ArrayList<HawbListVO> hawbList = new ArrayList<HawbListVO>();
		hawbList = mgrService.selectChangeLabelList(parameterInfo);

		model.addAttribute("hawbList", hawbList);
		
		return "adm/rls/mawbList/changeLabelList";
	}
	
	@RequestMapping(value = "/mngr/rls/changeLabelList", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrChnageLabelPost(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("nno[]") String[] nnoList, @RequestParam("transCode[]") String[] transCodeList, @RequestParam("weight[]") String[] wtList) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getRemoteAddr();
		int errorCnt = 0;
		
		try {
			for (int i = 0; i < nnoList.length; i++) {
				String nno = nnoList[i];
				String transCode = transCodeList[i];
				Double wta = Double.parseDouble(wtList[i]);
				HashMap<String, Object> weights = new HashMap<String, Object>();
				
				if (transCode.equals("ACI-US")) {
					weights = mgrService.selectWeightInfo(nno);
					String wtUnit = weights.get("wtUnit").toString();
					double totalWeight = Double.parseDouble(String.valueOf(weights.get("userWta")));
					double sendWeight = 0;
					if (wtUnit.toUpperCase().equals("LB")) {
						sendWeight = totalWeight / 2.2051;
					} else if (wtUnit.toUpperCase().equals("OZ")) {
						sendWeight = totalWeight / 35.274;
					} else {
						sendWeight = totalWeight;
					}
					sendWeight = Math.round(sendWeight * 1000.0) / 1000.0;
					
					if (sendWeight > 7) {
						rst = type86Api.selectBlApplyT86(nno, userId, userIp);
						if (rst.get("STATUS").equals("FAIL")) {
							errorCnt++;
						}
					} else {
						rst = pclApi.createShipment(nno, userId, userIp);
						if (rst.get("STATUS").equals("FAIL")) {
							errorCnt++;
						}
					}
				} else if (transCode.equals("ACI-T86")) {
					rst = type86Api.selectBlApplyT86(nno, userId, userIp);
					if (rst.get("STATUS").equals("FAIL")) {
						errorCnt++;
					}
				}
			}
		} catch (Exception e) {
			result.put("STATUS", "L10");
			result.put("MSG", "시스템 오류가 발생하였습니다. 시스템 담당자에게 문의해 주세요.");
		}
		
		if (errorCnt > 0) {
			result.put("STATUS", "D10");
			result.put("MSG", "선택하신 송장 중 " + errorCnt + "건이 라벨 생성에 실패 하였습니다.");
		} else {
			result.put("STATUS", "S10");
			result.put("MSG", "라벨 생성이 완료 되었습니다.");
		}
		
		return result;
	}

	@RequestMapping(value = "/mngr/redirectToUserPage", method = RequestMethod.GET)
	public String redirectToUserPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		String userId = request.getParameter("userId");
		
		MemberVO member = getUserInfo(userId, session);
		
		if (member != null) {
			Authentication adminAuthentication = SecurityContextHolder.getContext().getAuthentication();
			Authentication userAuthentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(userAuthentication);
			
			return "redirect:/cstmr/apply/shpngAgncy";
		} else {
			return "redirect:/mngr/acnt/userList";
		}
	}

	private MemberVO getUserInfo(String userId, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute(userId);
		if (member == null) {
			return null;
		}
		
		if (!member.isEnabled()) {
			session.removeAttribute(userId);
			return null;
		}
		
		return member;
	}
	
	@RequestMapping(value = "/mngr/aplctList/downloadYunExpList", method = RequestMethod.GET)
	public void downloadYunExpExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			mgrService.downloadYunExpExcel(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/mngr/order/ytList", method = RequestMethod.GET)
	public String ytHawbUpdateList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("transCode", "YT");
		String hawbNo = "";
		String orderNo = "";
		String userId = "";
		
		if (request.getParameter("hawbNo") != null) {
			hawbNo = request.getParameter("hawbNo");
		}
		if (request.getParameter("orderNo") != null) {
			orderNo = request.getParameter("orderNo");
		}
		if (request.getParameter("userId") != null) {
			userId = request.getParameter("userId");
		}
		
		parameters.put("hawbNo", hawbNo);
		parameters.put("orderNo", orderNo);
		parameters.put("userId", userId);
		
		ArrayList<ShpngListVO> orderList = new ArrayList<ShpngListVO>();
		orderList = mgrService.selectYunExpOrderList(parameters);
		
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).dncryptData();
		}
		
		model.addAttribute("parameters", parameters);
		model.addAttribute("orderList", orderList);
		
		return "adm/aplctList/list/ytHawbUpdate";
	}
	
	@RequestMapping(value = "/mngr/order/ytListExcelDown", method = RequestMethod.POST)
	public void ytHawbUpdateListExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		mgrService.yunExpOrderListExcelDown(request, response);
	}
	
	@RequestMapping(value = "/mngr/order/ytHawbUpdate", method = RequestMethod.GET)
	public String ytHawbUpdatePopup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String nno = request.getParameter("nno");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("nno", nno);
		parameters.put("transCode", "YT");
		
		UserOrderListVO orderInfo = new UserOrderListVO();
		orderInfo = mgrService.selectYunExpOrderInfo(parameters);
		orderInfo.dncryptData();
		
		
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("parameters", parameters);
		
		return "adm/aplctList/list/ytHawbUpdatePopup";
	}
	
	@RequestMapping(value = "/mngr/order/ytHawbUpdate", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> ytHawbUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", request.getParameter("nno"));
		parameters.put("matchNo", request.getParameter("matchNo"));
		/*
		String trkNo = request.getParameter("matchNo");
		if (request.getParameter("trkNo") != null) {
			trkNo = request.getParameter("trkNo");
		}
		parameters.put("trkNo", trkNo);
		*/
		parameters.put("transCode", request.getParameter("transCode"));
		parameters.put("nno", request.getParameter("nno"));
		parameters.put("hawbNo", request.getParameter("hawbNo"));
		
		try {
			mgrService.insertYunExpDeliveryInfo(parameters);
			rst.put("status", "S");
		} catch (Exception e) {
			rst.put("status", "F");
			e.printStackTrace();
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/rls/checkMawbNo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrCheckMawbNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			String mawbNo = request.getParameter("mawbNo");
			HashMap<String, Object> mawbCheck = new HashMap<String, Object>();
			mawbCheck = mgrService.selectCheckMawbNo(mawbNo);
			String transCode = mawbCheck.get("transCode").toString();
			
			if (transCode.equals("")) {
				rst.put("STATUS", "FAIL");
				rst.put("MSG", "Mawb No를 찾을 수 없습니다.");
			} else {
				rst.put("STATUS", "SUCCESS");
				rst.put("MSG", transCode);
			}
			
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
			rst.put("MSG", "Mawb No 유효성 검사 중 오류 발생");
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/mngr/rls/mawbHawbListInsert", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrMawbHawbListInsert(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();

		String mawbNo = request.getParameter("mawbNo");
		String transCode = request.getParameter("transCode");
		
		int totalCnt = 0;
		
		try {
			if (request.getParameterValues("nnoList") != null) {
				int succCnt = 0;
				String[] nnoList = request.getParameterValues("nnoList");
				String[] hawbNoList = request.getParameterValues("hawbNoList");
				totalCnt = nnoList.length;
				
				for (int i = 0; i < nnoList.length; i++) {
					HawbVO hawbVo = new HawbVO();
					String nno = nnoList[i];
					String hawbNo = hawbNoList[i];

					if (transCode.equals("SEK")) {
						hawbVo.setHawbNo(hawbNo.substring(10, hawbNo.length()));
					} else {
						hawbVo.setHawbNo(hawbNo);
					}
					
					hawbVo.setTransCode(transCode);
					hawbVo.setNno(nno);
					hawbVo.setBagNo("1");
					hawbVo.setMawbNo(mawbNo);
					hawbVo.setWUserId((String) request.getSession().getAttribute("USER_ID"));
					hawbVo.setWUserIp(request.getRemoteAddr());
					
					try {
						mgrService.updateMawbNo(hawbVo);
						
					} catch (Exception e) {
						continue;
					}
					
					succCnt++;
				}
				
				rst.put("STATUS", "SUCCESS");
				rst.put("MSG", totalCnt + "건 중 " + succCnt + "건 적용 되었습니다");
			} else {
				rst.put("STATUS", "FAIL");
				rst.put("MSG", "데이터 오류 발생, 관리자에게 문의해 주세요.");
			}
			
			
		} catch (Exception e) {
			rst.put("STATUS", "FAIL");
			rst.put("MSG", e.getMessage());
		}

		return rst;
	}
	
	@RequestMapping(value = "/mngr/acnt/userBizList", method = RequestMethod.GET)
	public String mngrUserBizList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String userId = "";
		String comName = "";
		
		if (request.getParameter("searchUserId") != null) {
			userId = request.getParameter("searchUserId");
		}
		
		if (request.getParameter("searchComName") != null) {
			comName = request.getParameter("searchComName");
		}
		
		parameters.put("userId", userId);
		parameters.put("comName", comName);
		
		int curPage = 1;
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCnt = mgrService.selectBizInfoListCnt(parameters);
		
		PagingVO paging = new PagingVO(curPage, totalCnt, 10, 50);
		
		parameters.put("paging", paging);
		
		ArrayList<BizInfo> bizList = new ArrayList<BizInfo>();
		bizList = mgrService.selectBizInfoList(parameters);
		
		for (int i = 0; i < bizList.size(); i++) {
			bizList.get(i).dncryptData();
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("bizList", bizList);
		model.addAttribute("parameters", parameters);
		
		return "adm/member/bizList";
	}
	
	@RequestMapping(value = "/mngr/acnt/registBizInfo", method = RequestMethod.GET)
	public String mngrRegistBizInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userId = "";
		String actWork = "";
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		if (request.getParameter("userId") != null) {
			userId = request.getParameter("userId");
		}
		
		if (!"".equals(userId)) {
			actWork = "update";
			parameters.put("userId", userId);
			BizInfo info = new BizInfo();
			info = mgrService.selectBizInfoOne(parameters);
			info.dncryptData();
			model.addAttribute("info", info);
			
		} else {
			actWork = "insert";
			ArrayList<HashMap<String, Object>> userInfoList = new ArrayList<HashMap<String, Object>>();
			parameters.put("orgNation", "KR");
			userInfoList = mgrService.selectUserInfoList(parameters);
			model.addAttribute("userInfoList", userInfoList);
		}
		
		model.addAttribute("actWork", actWork);
		return "adm/member/bizListPopup";
	}
	
	@RequestMapping(value = "/mngr/acnt/registBizInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> mngrRegistBizInfoPost(HttpServletRequest request, HttpServletResponse response, BizInfo bizInfo) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String actWork = "";

		
		try {
			
			String wUserId = (String) request.getSession().getAttribute("USER_ID");
			String wUserIp = (String) request.getRemoteAddr();
			bizInfo.setWUserId(wUserId);
			bizInfo.setWUserIp(wUserIp);
			bizInfo.encryptData();
			
			if (request.getParameter("actWork") != null) {
				actWork = request.getParameter("actWork");
			}
			
			if (actWork.toLowerCase().equals("insert")) {
				mgrService.insertBizInfo(bizInfo);
			} else {
				mgrService.updateBizInfo(bizInfo);
			}
			rst.put("status", "success");
			
		} catch (Exception e) {
			rst.put("status", "fail");
		} 

		return rst;
	}
	
	@RequestMapping(value = "/mngr/rls/downMawbNoManifest", method = RequestMethod.GET)
	public ResponseEntity<byte[]> mngrManifestExcelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			byte[] excelBytes = mgrService.selectDownloadManifest(request, response);
					
			HttpHeaders headers = new HttpHeaders();
			
			String fileName = request.getParameter("mawbNo") + ".xlsx";

            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("filename", fileName);
            headers.setContentLength(excelBytes.length);
            
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/mngr/rls/export", method = RequestMethod.GET)
	public String adminExportMawbList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		return "admin/mawb/exportList";
	}
	
	@RequestMapping(value = "/mngr/rls/scan", method = RequestMethod.GET)
	public String adminLabelScan(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "admin/scan/scanLabel";
	}
	
}
