package com.example.temp.manager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.s3.AmazonS3;
import com.example.temp.api.aci.service.ApiV1ReturnService;
import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.api.webhook.controller.WebhookController;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerReturnService;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.OrderInspcVO2;
import com.example.temp.manager.vo.OrderListVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.StationDefaultVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;
import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.service.MemberReturnService;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.smtp.SmtpService;
import com.example.temp.trans.yongsung.YongSungAPI;

import ch.qos.logback.core.joran.action.ParamAction;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import oracle.net.aso.p;

@Controller
public class ManagerReturnController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	String url;
	
	String key = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO";
	@Autowired
	ManagerService mgrService;

	@Autowired
	MemberReturnService memberReturnService;
	
	@Autowired
	ManagerReturnService rtnService;
	
	@Autowired
	WebhookController webhookController;
	
	@Autowired
	ApiV1ReturnService returnService;
	
	@Autowired
	YongSungAPI yslApi;
	
	@Autowired
	private SmtpService smtpService;
	
	
	@Value("${filePath}")
	String realFilePath;
	

	// 반품 회수지 관리
	@RequestMapping(value = "/mngr/aplctList/return/stationList", method = RequestMethod.GET)
	public String getReturnStationList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ArrayList<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		
		if (request.getParameter("stationCode") != null) {
			if (request.getParameter("stationCode").equals("ALL")) {
				params.put("stationCode", "");
			} else {
				params.put("stationCode", request.getParameter("stationCode"));	
			}
		}
		
		
		stationList = rtnService.selectReturnStation(params);
		
		model.addAttribute("params", params);
		model.addAttribute("stationList", stationList);
		
		return "adm/aplctList/list/return/returnStationList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/registStation", method = RequestMethod.GET)
	public String managerRegistReturnStation(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		
		
		return "adm/aplctList/list/return/registReturnStation";
	}
	
	// 반품 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/returnList", method = RequestMethod.GET) 
	public String getReturnPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int curPage = 1;
		ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		String option = "";
		String select = "";
		String keywords = "";
	 
		map.put("orgStation", orgStation);
		
		if(request.getParameter("keywords")!=null) {
			keywords = request.getParameter("keywords");
		}
		option = request.getParameter("option");
		select = request.getParameter("select");
		
		map.put("option", option);
		map.put("select", select);
		map.put("keywords", keywords);
		model.addAttribute("option", option);
		model.addAttribute("select", select);
		model.addAttribute("keywords", keywords);
		
		int totalCount = rtnService.getTotalReturnRequestCnt(map);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		map.put("paging", paging);
		
		try {
			vo = rtnService.getReturnRequestData(map);
		} catch (Exception e) {
			vo = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", map);
		model.addAttribute("vo", vo);
	
		return "adm/aplctList/list/return/returnList";
	}
	
	/**
	 * 엑셀 파일 업로드
	 * 
	 * */
	@RequestMapping(value="/mngr/aplctList/return/excelUpload", method=RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi) throws Exception {
		String result = "F";
		HashMap<String, String> parameters = new HashMap<String, String>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		parameters.put("orgStation", orgStation);
		//parameters.put("sellerId", (String) request.getSession().getAttribute("USER_ID"));
		
		
		try {
		   //result = rtnService.insertReturnExcel(multi, request, parameters);
		   result = rtnService.updateReturnExcel(multi, request, parameters);
		} catch (Exception e) {
		   result = "F";
		}
		
		return result;
	}
	
	/**
	 * 엑셀 파일 다운로드
	 * 
	 * */
	@RequestMapping(value = "/mngr/aplctList/return/excelDown", method = RequestMethod.GET)
	public void returnExcelDown(ReturnVO returnVO, ReturnItemVO returnItemVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		returnVO = new ReturnVO();
//		returnVO.dncryptData();
		rtnService.returnExcelDown(returnVO, request, response);
	}
	
	// 반품정보페이지
	@RequestMapping(value = "/mngr/aplctList/return/{orderReference}", method = RequestMethod.GET)
	public String returnRequestView(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("orderReference") String orderReference
			) throws Exception{
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		ReturnVO rtn = new ReturnVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		//rtn = rtnService.getAllExpressData(orderReference, userId);
		itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());
//		ReturnVO vo = new ReturnVO();
//		ArrayList<ReturnItemVO> items = new ArrayList<ReturnItemVO>();
//		vo = rtnService.getReturnInfo(nno);
//		items = rtnService.getReturnItemByNno(nno);
//		vo.dncryptData();
		model.addAttribute("koblNo", koblNo);
		model.addAttribute("rtn", rtn);
		model.addAttribute("itemList", itemList);
		model.addAttribute("nno", rtn.getNno());
		return "adm/aplctList/list/return/updateOrder";
	}
	
	@Transactional
	@RequestMapping(value = "/mngr/aplctList/return/{orderReference}", method = RequestMethod.PATCH)
	public String returnCourierUpdateData(HttpServletRequest request, MultipartHttpServletRequest multi, HttpServletResponse response, Model model, ReturnRequestVO rtnRequestVO, @PathVariable("orderReference") String orderReference) throws Exception{
		try {
			
		
		Map<String, String> resMap = new HashMap<String, String>();
		int subNoLen = request.getParameterValues("subNo").length;
		//String koblNo = request.getParameter("koblNo");
		String nno = request.getParameter("nno");
		String userId = request.getParameter("sellerId");
		String wUserId = request.getSession().getAttribute("USER_ID").toString();
		String wUserIp = request.getRemoteAddr();
		//String userIp = request.getSession().getAttribute("USER_IP").toString();
		
		if(rtnRequestVO.getTaxType().equals("Y")) {
			resMap = memberReturnService.filesUpload(multi,nno,userId);
		}
		
		rtnRequestVO.setOrgStation("082");
		rtnRequestVO.setDstnNation(request.getParameter("dstnNation").toString());
		rtnRequestVO.setSellerId(userId);
		if(request.getParameter("calculateId").isEmpty()) {
			rtnRequestVO.setCalculateId(userId);
		}else {
			rtnRequestVO.setOrderReference(request.getParameter("calculateId"));
		}
		rtnRequestVO.setUserId("");
		rtnRequestVO.setOrderReference(orderReference);
		rtnRequestVO.setOrderDate(request.getParameter("orderDate"));
		rtnRequestVO.setOrderNo(request.getParameter("orderNo"));
		String state = request.getParameter("state");
		if (state.equals("A000") || state.equals("A001") || state.equals("A002")) {
			if (request.getParameter("pickType").equals("A")) {
				rtnRequestVO.setState("B001");
			}
		}

		rtnRequestVO.setRootSite("WMS");
		rtnRequestVO.setWUserId(wUserId);
		rtnRequestVO.setWUserIp(wUserIp);
		//memberReturnService.insertReturnRequest(rtnRequestVO);
		//rtnRequestVO.encryptData();
		memberReturnService.updateReturnRequest(rtnRequestVO, resMap);
		
		memberReturnService.deleteReturnRequestItem(nno);
		for(int i=0; i<subNoLen; i++) {
			HashMap<String,Object> itemMap = new HashMap<String,Object>();
			itemMap.put("nno", nno);
			itemMap.put("orgStation", "082");
			itemMap.put("userId", userId);
			itemMap.put("subNo", request.getParameterValues("subNo")[i]);
			itemMap.put("itemDetail", request.getParameterValues("itemDetail")[i]);
			itemMap.put("brand", request.getParameterValues("brand")[i]);
			itemMap.put("itemWta", request.getParameterValues("itemWta")[i]);
			itemMap.put("wtUnit", request.getParameterValues("wtUnit")[i]);
			itemMap.put("itemCnt", request.getParameterValues("itemCnt")[i]);
			itemMap.put("unitValue", request.getParameterValues("unitValue")[i]);
			itemMap.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
			itemMap.put("makeCntry", request.getParameterValues("makeCntry")[i]);
			itemMap.put("makeCom", request.getParameterValues("makeCom")[i]);
			itemMap.put("hsCode", request.getParameterValues("hsCode")[i]);
			itemMap.put("itemUrl", request.getParameterValues("itemUrl")[i]);
			itemMap.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
			if (request.getParameter("dstnNation").equals("JP")) {
				itemMap.put("cusItemCode", request.getParameterValues("cusItemCode")[i]);
				itemMap.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);	
			}
			itemMap.put("wUserId", wUserId);
			itemMap.put("wUserIp", wUserIp);
			memberReturnService.insertReturnRequestItem(itemMap);
		}
		
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return "redirect:/mngr/order/return/list";
	}
	
	
	// 반품 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/inspList", method = RequestMethod.GET) 
	public String getInspListPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		int totalCount = rtnService.selectReturnInspListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,10);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnInspList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		return "adm/aplctList/list/return/returnInspList";
	}
	
	// 반품 검수 합격 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/returnSuccessList", method = RequestMethod.GET) 
	public String getInspSucccessListPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		parameterOption.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			parameterOption.put("reTrkNo", searchKeywords);
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", "");
		} else if (searchType.equals("koblNo")) {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", searchKeywords);
			parameterOption.put("userId", "");
		} else {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", searchKeywords);
		}
		
		
		int totalCount = rtnService.selectReturnInspSuccessListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,10);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnSuccessInspList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		//return "adm/aplctList/list/return/returnInspSuccessList";
		return "adm/aplctList/list/return/managerReturnSuccessList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/japanSuccessList", method = RequestMethod.GET) 
	public String getInspSucccessListPageJapan(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		parameterOption.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			parameterOption.put("reTrkNo", searchKeywords);
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", "");
		} else if (searchType.equals("koblNo")) {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", searchKeywords);
			parameterOption.put("userId", "");
		} else {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", searchKeywords);
		}
		
		
		int totalCount = rtnService.selectReturnJapanSuccessListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,10);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnJapanSuccessList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
			logger.error("Exception", e);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		return "adm/aplctList/list/return/managerJapanReturnList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/japanExcelDown", method = RequestMethod.GET)
	public void managerReturnJapanExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String[] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		
		try {
			for (int i = 0; i < orderNnoList.size(); i++) {
				
			}
			
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/sendItemCode", method = RequestMethod.GET)
	public void managerSendCusItemCodeMail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			smtpService.sendMail();	
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	
	@RequestMapping(value = "/mngr/aplctList/return/pdfPopup", method = RequestMethod.GET)
	public String managerReturnStockPdfPopup(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		model.addAttribute("nno", request.getParameter("nno"));
		model.addAttribute("groupIdx", request.getParameter("groupIdx"));
		createPdf(request,response,model);
		return "adm/aplctList/list/return/pdfPopup";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/createPdfreal", method = RequestMethod.GET)
	@ResponseBody
	public void createPdf(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
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
		
		String barcodePath = pdfPath2 +  tempGroupIdx[0] + ".JPEG";
		ClassPathResource cssResource = new ClassPathResource("application.properties");
		String mainPath = cssResource.getURL().getPath();
		String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
		
		
		final PDDocument doc = new PDDocument();
		InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");
		PDType0Font NanumGothic = PDType0Font.load(doc, korean);
		float perMM = 1 / (10 * 2.54f) * 72;
		
		int pdfPage = 0;
		
		String nno = request.getParameter("nno");
		System.out.println(nno);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nno", nno);
		try {
			int marginTop = 30;
			for (int pdfIndex = 0; pdfIndex < tempGroupIdx.length; pdfIndex++) {
				ArrayList<StockResultVO> stockResultList = new ArrayList<StockResultVO>();
				map.put("groupIdx", tempGroupIdx[pdfIndex]);
				//stockResultList = returnService.selectStockResultVO2(nno, tempGroupIdx[pdfIndex]);
				stockResultList = returnService.selectStockResultVO2(map);
			/* StockResultVO tempResultVO = new StockResultVO(); */
				ArrayList<StockVO> tempStockVoList = new ArrayList<StockVO>();
				try {
					tempStockVoList = returnService.selectStockByGrpIdx2(stockResultList.get(0).getGroupIdx());
				} catch (Exception e) {
					logger.error("Exception", e);
				}
				//tempStockVoList = returnService.selectStockByGrpIdx2(stockResultList.get(0).getGroupIdx());
		        // 컨텐츠 스트림 열기
		        for (int stockIndex = 0; stockIndex < tempStockVoList.size(); stockIndex++) {
		        	StockResultVO tempResultVO = new StockResultVO();
		        	tempResultVO = stockResultList.get(stockIndex);
		        	PDRectangle	asd = new PDRectangle(80*perMM, 60*perMM);
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
					float titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getWhStatusName()) / 1000 * fontSize;
					contentStream.drawImage(pdImage, (80*perMM - 220) / 2, 37, 220f,35f);
					
					drawText(tempStockVoList.get(stockIndex).getWhStatusName(),NanumGothic, 12, (80*perMM - titleWidth) / 2, 145,contentStream);
					
					String pickType = "";
					
					if (tempStockVoList.get(stockIndex).getPickType().equals("E")) {
						pickType = "긴급";
						fontSize = 10;
						titleWidth = NanumGothic.getStringWidth(pickType) / 1000 * fontSize;
						drawText(pickType,NanumGothic, 10, (80*perMM - titleWidth) / 2, 130,contentStream);
					} else {
						pickType = "일반";
						fontSize = 10;
						titleWidth = NanumGothic.getStringWidth(pickType) / 1000 * fontSize;
						drawText(pickType,NanumGothic, 10, (80*perMM - titleWidth) / 2, 130,contentStream);
					}
					
					fontSize = 10;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getUserId()+"/"+tempResultVO.getCneeName(),NanumGothic, 10, (80*perMM - titleWidth) / 2, 117,contentStream);
					
					fontSize = 11;
					titleWidth = NanumGothic.getStringWidth(tempResultVO.getRackCode()) / 1000 * fontSize;
					drawText(tempResultVO.getRackCode(),NanumGothic, 11, (80*perMM - titleWidth) / 2, 102,contentStream);
					
					fontSize = 12;
					String itemDetailSub = tempResultVO.getItemDetail();
					if(NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize > 80*perMM) {
						itemDetailSub = itemDetailSub.substring(0,15);
						itemDetailSub = itemDetailSub+"...";
					}
					
					titleWidth = NanumGothic.getStringWidth(itemDetailSub) / 1000 * fontSize;
					drawText(itemDetailSub,NanumGothic, 12, (80*perMM - titleWidth) / 2, 90,contentStream);
					
					fontSize = 13;
					titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(stockIndex).getStockNo()) / 1000 * fontSize;
					drawText(tempStockVoList.get(stockIndex).getStockNo(),NanumGothic, 13, (80*perMM - titleWidth) / 2, 25,contentStream);
					contentStream.close();
					pdfPage++;
		        }
		        
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("샘플PDF", "UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		
		// PDF 파일 출력
		doc.save(response.getOutputStream());
		doc.close();
		
		File barcodefile = new File(barcodePath); 
		if(barcodefile.exists()) {
			barcodefile.delete(); 
		}
		
	}
	
	private void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream contentStream) throws Exception {
        contentStream.beginText(); 
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(left, bottom);
        contentStream.showText(text);
        contentStream.endText();
	}
	
	// 반품 검수 불합격 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/returnFailList", method = RequestMethod.GET) 
	public String getInspFailListPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		parameterOption.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			parameterOption.put("reTrkNo", searchKeywords);
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", "");
		} else if (searchType.equals("koblNo")) {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", searchKeywords);
			parameterOption.put("userId", "");
		} else {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", searchKeywords);
		}
		
		
		int totalCount = rtnService.selectReturnInspFailListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,10);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnFailInspList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		//return "adm/aplctList/list/return/returnInspFailList";
		return "adm/aplctList/list/return/managerReturnFailList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/returnFailList/{orderReference}", method = RequestMethod.GET) 
	public String getCancleInspFailListPage(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("orderReference") String orderReference) throws Exception{
		rtnService.cancleStock(orderReference);
		
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		int totalCount = rtnService.selectReturnInspFailListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,10);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnFailInspList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		return "adm/aplctList/list/return/managerReturnFailList";
	}
	
	
	// 반품 검수 불합격 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/C002", method = RequestMethod.GET) 
	public String getC002tPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		parameterOption.put("viState","C002");
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		parameterOption.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			parameterOption.put("reTrkNo", searchKeywords);
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", "");
			parameterOption.put("hawbNo", "");
		} else if (searchType.equals("koblNo")) {
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", searchKeywords);
			parameterOption.put("userId", "");
			parameterOption.put("hawbNo", "");
		} else if (searchType.equals("hawbNo")) {
			parameterOption.put("hawbNo", searchKeywords);
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", "");
			parameterOption.put("reTrkNo", "");
		} else {
			parameterOption.put("hawbNo", "");
			parameterOption.put("reTrkNo", "");
			parameterOption.put("koblNo", "");
			parameterOption.put("userId", searchKeywords);
		}
		
		int totalCount = rtnService.selectReturnOptionCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,30);
		parameterOption.put("paging", paging);
		
		try {
			returnInspList = rtnService.selectReturnOption(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspList", returnInspList);
	
		//return "adm/aplctList/list/return/returnC002List";
		return "adm/aplctList/list/return/managerReturnSendOutList";
	}
	
	// 반품 검수 불합격 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/D005", method = RequestMethod.GET) 
	public String getD005Page(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
				
			int curPage = 1;
			HashMap<String, Object> params = new HashMap<String, Object>();
			String searchType="";
			String searchKeywords="";
			
			if (request.getParameter("keywords") != null) {
				searchType = request.getParameter("searchType");
				searchKeywords = request.getParameter("keywords");
			}
			
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchKeywords", searchKeywords);
			
			params.put("searchType", searchType);
			
			if (searchType.equals("reTrkNo")) {
				params.put("reTrkNo", searchKeywords);
			}
			
			ArrayList<ReturnRequestVO> inspList = new ArrayList<ReturnRequestVO>();
			
			int totalCnt = rtnService.selectTrashReturnListCnt(params);
			
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			PagingVO paging = new PagingVO(curPage, totalCnt, 10, 30);
			params.put("paging", paging);
			params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			inspList = rtnService.selectTrashReturnList(params);
			for (int i = 0; i < inspList.size(); i++) {
				inspList.get(i).dncryptData();
			}
			
			model.addAttribute("paging", paging);
			model.addAttribute("map", params);
			model.addAttribute("returnInspList", inspList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "adm/aplctList/list/return/returnD005List";
		return "adm/aplctList/list/return/managerReturnDiscardList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/detail/{orderReference}", method = RequestMethod.GET) 
	public String getD005DetailPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		String status = "";
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = rtnService.selectReturnInspReadOne(parameterOption);
			status = rtnService.selectReturnStatus(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			logger.error("Exception", e);
		}
		model.addAttribute("state", status);
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne);
		return "adm/aplctList/list/return/returnInspRead2";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/C002/{orderReference}", method = RequestMethod.GET) 
	public String getC002DetailPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = rtnService.selectReturnInspReadOne(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			logger.error("Exception", e);
		}
		 
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne);
		return "adm/aplctList/list/return/returnInspRead";
	}
		
	
	
	// 반품 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/inspList/{orderReference}", method = RequestMethod.GET) 
	public String getInspOrderPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		
		ReturnRequestVO returnOrder = new ReturnRequestVO();
		returnOrder = rtnService.selectReturnInspOrder(orderReference);
		returnOrder.dncryptData();
		
		ArrayList<ReturnRequestItemVO> returnItem = new ArrayList<ReturnRequestItemVO>();
		returnItem = rtnService.selectReturnInspOrderItem(returnOrder.getNno());
		
		model.addAttribute("returnItem", returnItem);
		model.addAttribute("returnOrder", returnOrder);
		//return "adm/aplctList/list/return/returnInspOne";
		return "adm/aplctList/list/return/managerReturnInspOne";
	}
	
	// 반품 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/inspDel/{orderReference}/{userId}", method = RequestMethod.GET) 
	public String getInspOrderDelPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference, @PathVariable("userId") String userId) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = rtnService.selectReturnInspDelOne(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			logger.error("Exception", e);
		}
		 
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne); 
		return "adm/aplctList/list/return/returnInspDel";
	}
	
	// 반품 LIST 페이지 출력
	@RequestMapping(value = "/mngr/aplctList/return/add/{orderReference}", method = RequestMethod.GET) 
	public String getInspOrderCostAddPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		parameterOption.put("orderReference", orderReference);
		try {
			returnInspOne = rtnService.selectReturnInspAddOne(parameterOption);
		} catch (Exception e) {
			returnInspOne = null;
			logger.error("Exception", e);
		}
		
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne); 
		return "adm/aplctList/list/return/returnInspCostAdd";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/add/{orderReference}", method = RequestMethod.POST)
	@ResponseBody
	public String postInspOrderCostAddPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		rtnService.insertDepositAddHis(request); 
		return "S";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/inspDel/{orderReference}", method = RequestMethod.POST)
	@ResponseBody
	public String postInspOrderDelPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		rtnService.insertDepositDelHis(request);
		return "S";
	}
	
	
	
	@RequestMapping(value = "/mngr/aplctList/return/inspList/{orderReference}", method = RequestMethod.POST) 
	public String postInspOrderPage(HttpServletRequest request, MultipartHttpServletRequest multi, 
			HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		try {
			
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		parameterOption.put("orderReference", orderReference);
		//rtnService.updateOrderRegistInReturn("B001",orderReference);
		try {
			if(request.getParameter("whStatus").equals("WO")) {
				returnInspOne = rtnService.returnRequestWhProcess(request,response,multi,orderReference);
			}else if(request.getParameter("whStatus").equals("WF")) {
				returnInspOne = rtnService.returnRequestWhFailProcess(request,multi,response,orderReference);
			}
		} catch (Exception e) {
			returnInspOne = null;
			logger.error("Exception", e);
		}
		
		model.addAttribute("map", parameterOption);
		model.addAttribute("returnInspOne", returnInspOne);
		

		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		String rtnVal = "";
		if (request.getParameter("whStatus").equals("WO")) {
			rtnVal = "redirect:/mngr/aplctList/returnOrder/inspList";
		} else {
			rtnVal = "redirect:/mngr/aplctList/returnOrder/inspList";
		}
		return rtnVal;
	}

	@RequestMapping(value = "/mngr/order/return/stockList", method = RequestMethod.GET)
	public String managerInspStock(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<GroupStockVO> inspStockList = new ArrayList<GroupStockVO>();
		int curPage = 1;
		int fileCnt = 1;
		
		try {	
			HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			parameterInfo.put("orgStation", orgStation);
			parameterInfo.put("userId", request.getParameter("userId"));
	
			int totalCnt = rtnService.selectStockTotalCnt(parameterInfo);
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			PagingVO paging = new PagingVO(curPage, totalCnt, 1, 30);
			parameterInfo.put("paging", paging);
			
			ReturnRequestVO returnInfo = new ReturnRequestVO();
			inspStockList = rtnService.selectReturnInspOrderInfo(parameterInfo);
			for (int i = 0; i < inspStockList.size(); i++) {
				returnInfo = rtnService.selectReturnInfo(inspStockList.get(i).getNno());
				returnInfo.dncryptData();
				inspStockList.get(i).setCneeName(returnInfo.getSenderName());
				inspStockList.get(i).setCneeTel(returnInfo.getSenderTel());
				inspStockList.get(i).setCneeHp(returnInfo.getSenderHp());
				inspStockList.get(i).setCneeAddr(returnInfo.getSenderAddr());
				inspStockList.get(i).setStateKr(returnInfo.getStateKr());
				inspStockList.get(i).setState(returnInfo.getState());
			}
			
			model.addAttribute("inspStockList", inspStockList);
			model.addAttribute("paging", paging);
			model.addAttribute("parameterInfo", parameterInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "adm/aplctList/list/return/stockList";
	}
	
	@RequestMapping(value = "/mngr/order/return/loadImage", method = RequestMethod.GET)
	public String managerLoadInspImage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<String> fileList = new ArrayList<String>();
		String groupIdx = request.getParameter("groupIdx");
		
		fileList = rtnService.selectLoadFileImage(groupIdx);
		
		model.addAttribute("groupIdx", groupIdx);
		model.addAttribute("fileList", fileList);
		return "adm/aplctList/list/return/loadInspImage";
	}
 
	@RequestMapping(value = "/mngr/order/return/loadInspImage", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<String> returnLoadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> fileList = new ArrayList<String>();
		String groupIdx = request.getParameter("groupIdx");
		
		fileList = rtnService.selectLoadFileImage(groupIdx);
		
		return fileList;
	}
	
	
	@RequestMapping(value = "/mngr/aplctList/return/orderIn/{orderReference}", method = RequestMethod.POST)
	public String orderRegistInReturn(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		int curPage = 1;
		ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		String option = "";
		String select = "";
		String keywords = "";
		
		rtnService.updateOrderRegistInReturn(request.getParameter("targetStatus"),orderReference);
	 
		map.put("orgStation", orgStation);
		
		if(request.getParameter("keywords")!=null) {
			option = request.getParameter("option");
			select = request.getParameter("select");
			keywords = request.getParameter("keywords");
		}
		
		map.put("option", option);
		map.put("select", select);
		map.put("keywords", keywords);
		model.addAttribute("option", option);
		model.addAttribute("select", select);
		model.addAttribute("keywords", keywords);
		
		int totalCount = rtnService.getTotalReturnRequestCnt(map);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		map.put("paging", paging);
		
		try {
			vo = rtnService.getReturnRequestData(map);
		} catch (Exception e) {
			vo = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", map);
		model.addAttribute("vo", vo);
	
		return "adm/aplctList/list/return/returnList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/inspListOut/{orderReference}", method = RequestMethod.GET) 
	public String getInspOrderOutPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("orderReference") String orderReference) throws Exception{
		/*
		ReturnRequestVO returnInfo = new ReturnRequestVO();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orderReference", orderReference);
		params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		returnInfo = rtnService.selectReturnInfoForStockOut(params);
		returnInfo.dncryptData();
		model.addAttribute("returnInfo", returnInfo);
		*/
		
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orderReference", orderReference);
			String nno = rtnService.selectReturnNno(params);
			params.put("nno", nno);
			params.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			params.put("wUserId", request.getSession().getAttribute("USER_ID"));
			params.put("wUserIp", request.getSession().getAttribute("USER_IP"));
			
			ReturnRequestVO returnInfo = new ReturnRequestVO();
			
			returnInfo = rtnService.selectReturnRequestInfo(params);
			//returnInfo = rtnService.selectReturnRequestApply(params);
			returnInfo.dncryptData();
			params.put("userId", returnInfo.getSellerId());
			
			ArrayList<UserTransComVO> transList = new ArrayList<UserTransComVO>();
			String dstnNation = returnInfo.getDstnNation();
			transList = rtnService.selectReturnInspTransList(dstnNation);
			
			model.addAttribute("transList", transList);
			
			rtnService.deleteReturnStockOut(params);
			
			
			OrderInspVO orderInfo = new OrderInspVO();
			orderInfo = rtnService.selectReturnOrderStock(params);
			
			model.addAttribute("returnInfo", returnInfo);
			model.addAttribute("orderInfo", orderInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return "adm/aplctList/list/return/returnInspStockOut";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/inspListOut/StockOutDetail", method = RequestMethod.POST)
	public String managerInspcStockOutDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		ArrayList<InspStockOutVO> stockOut = new ArrayList<InspStockOutVO>();  
		String targetSubNo = "";
		parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		parameter.put("orderReference", request.getParameter("orderReference"));
		targetSubNo = mgrService.selectTargetSubNo(request.getParameter("stockNo"));
		ArrayList<InspStockOutVO> stockRack = new ArrayList<InspStockOutVO>();
		stockRack = rtnService.selectStockRackInfo(parameter);
		
		stockOut = rtnService.selectReturnStockOutTarget(parameter);
		
		LinkedHashMap<String, Object> returnInspOne = new LinkedHashMap<String, Object>();
		returnInspOne = rtnService.selectReturnInspOne(parameter);
		ArrayList<UserTransComVO> transList = new ArrayList<UserTransComVO>();
		String dstnNation = returnInspOne.get("DSTN_NATION").toString();
		transList = rtnService.selectReturnInspTransList(dstnNation);
		
		model.addAttribute("transList", transList);
		model.addAttribute("stockRack", stockRack);
		model.addAttribute("inspStockOut", stockOut);
		model.addAttribute("scanSubNo",targetSubNo);
		
		return "adm/aplctList/list/return/returnStockOutDetail";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/inspListOut/registReturnStockOutVolume", method = RequestMethod.POST)
	@ResponseBody
	public ProcedureVO managerRegistStockOutVolume(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, String[]> parameterMaps = request.getParameterMap();  
		ProcedureVO rtnVal = new ProcedureVO();


		//1. temp -> 입고
		//2.
		try {
			
			String insertResult = rtnService.insertOrderInfo(request);
			if(!insertResult.equals("F")) {
				rtnVal = mgrService.execStockHawbIn(parameterMaps, request, response);
				rtnVal.setRstTransCode(insertResult);
				
				HashMap<String, Object> parameterOption = new HashMap<String, Object>();
				parameterOption.put("orderReference", request.getParameter("orderReference"));
				parameterOption.put("status", "C002");
				
				rtnService.updateReturnStatus(parameterOption);
				//검수 비용 적용 1차 기본비용 2차 추가비용
				rtnService.insertDepositHisRequest(request);
				//webhook
				webhookController.returnRequestWebHook(request.getParameter("orderReference"), "", "");
				
			}else {
				String errorMsg = rtnService.selectErrorMessage(request.getParameter("nno"));
				rtnVal.setRstMsg(errorMsg);
				//rtnVal.setRstMsg("등록중 오류가 발생하였습니다. 목록에서 오류내역을 확인하여 주세요.");
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		
		return rtnVal;
	}
	
	
	@RequestMapping(value = "/mngr/aplctList/return/depositList", method = RequestMethod.GET)
	public String managerDepositList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		int curPage = 1;
		String userId = request.getParameter("keywords");
		/*
		 * paging start 함수로 뺄것.
		 */
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		infoMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		infoMap.put("type", "deposit"); 
		int totalCount = mgrService.selectTotalCountInfo(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20,50);
		// paging 끝

		infoMap.put("paging", paging);
		ArrayList<HashMap<String,Object>> comUserInfo = rtnService.selectDepositUserList(infoMap);

		model.addAttribute("searchKeyword", userId);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("paging", paging);
		return "adm/member/depositList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/depositListp", method = RequestMethod.POST)
	public String managerDepositCalculate(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		HashMap<String, Object> depositInfo = new HashMap<String, Object>();
		
		if(request.getParameter("calculate").equals("A")) {
			//[USER_ID], COST, [CALCULATION], CODE
			depositInfo.put("userId", request.getParameter("depositId"));
			
			Double costNow = rtnService.selectDepositCostNow(depositInfo);
			Double balCost = costNow - Double.parseDouble(request.getParameter("costValue"));
			
			depositInfo.put("cost", request.getParameter("costValue"));
			depositInfo.put("calculation", "A");
			depositInfo.put("code", "A001");
			depositInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
			depositInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
			depositInfo.put("costNow", balCost);
		}else if(request.getParameter("calculate").equals("B")) {
			depositInfo.put("userId", request.getParameter("depositId"));
			
			Double costNow = rtnService.selectDepositCostNow(depositInfo);
			
			depositInfo.put("cost", request.getParameter("costValue"));
			depositInfo.put("calculation", "B");
			depositInfo.put("code", "D001");
			depositInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
			depositInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		}
		
		rtnService.insertDepositHis(depositInfo);
		
		//request.getParameter("cost")
		return "redirect:/mngr/aplctList/return/depositList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/deposit/{userId}", method = RequestMethod.GET)
	public String managerDepositUser(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("userId") String userId)
			throws Exception {
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		ArrayList<HashMap<String,Object>> depositHis = rtnService.selectDepositUserHis(userId);
		HashMap<String,Object> userInfo = rtnService.selectDepositUserOne(userId);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("depositHis", depositHis);
		return "adm/member/depositOne";
	}
	
	@RequestMapping(value = "/mngr/order/return/list", method = RequestMethod.GET)
	public String managerOrderReturnList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int curPage = 1;
		ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		String option = "";
		String select = "";
		String keywords = "";
	 
		map.put("orgStation", orgStation);
		
		if(request.getParameter("keywords")!=null) {
			keywords = request.getParameter("keywords");
		}
		option = request.getParameter("option");
		select = request.getParameter("select");
		
		map.put("option", option);
		map.put("select", select);
		map.put("keywords", keywords);
		model.addAttribute("option", option);
		model.addAttribute("select", select);
		model.addAttribute("keywords", keywords);
		
		//int totalCount = rtnService.getTotalReturnRequestCnt(map);
		int totalCount = rtnService.selectReturnRequestCnt(map);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,30);
		map.put("paging", paging);
		
		try {
			//vo = rtnService.getReturnRequestData(map);
			vo = rtnService.selectReturnRequest(map); 
		} catch (Exception e) {
			vo = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", map);
		model.addAttribute("vo", vo);
		
		return "adm/aplctList/list/return/managerReturnOrderList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/info/{orderReference}", method = RequestMethod.GET)
	public String returnRequestView2(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("orderReference") String orderReference) throws Exception{
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = memberReturnService.selectNationCode();
		model.addAttribute("nationList", nationList);
		
		ReturnVO rtn = new ReturnVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		//rtn = rtnService.getAllExpressData(orderReference, userId);
		itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());

		model.addAttribute("koblNo", koblNo);
		model.addAttribute("rtn", rtn);
		model.addAttribute("itemList", itemList);
		model.addAttribute("nno", rtn.getNno());
		
		return "adm/aplctList/list/return/updateReturnInfo";
	}
	
	
	@RequestMapping(value = "/mngr/aplctList/returnInfo/{nno}", method = RequestMethod.GET)
	public String managerReturnOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("nno") String nno) throws Exception {
		String koblNo = request.getParameter("koblNo");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		
		ArrayList<NationVO> nationList = new ArrayList<NationVO>();
		nationList = memberReturnService.selectNationCode();
		model.addAttribute("nationList", nationList);
		
		String orderReference = memberReturnService.selectReturnOrderReference(nno);
		
		ReturnRequestVO rtn = new ReturnRequestVO();
		ArrayList<ReturnItemVO> itemList = new ArrayList<ReturnItemVO>();
		rtn = rtnService.getAllExpressData(orderReference, userId);
		rtn.dncryptData();
		itemList = memberReturnService.getAllExpressItemsData(rtn.getNno());

		model.addAttribute("koblNo", koblNo);
		model.addAttribute("orderInfo", rtn);
		model.addAttribute("itemList", itemList);
		model.addAttribute("nno", rtn.getNno());
		
		
		return "adm/aplctList/list/return/returnOrderInfo";
	}
	
	@RequestMapping(value = "/mngr/aplctList/returnInfo/taxReturn", method = RequestMethod.GET)
	public String popupReturnTaxReturnInfo(HttpServletRequest request, Model model) throws Exception {
		
		String nno = request.getParameter("nno");
		ReturnRequestVO orderInfo = new ReturnRequestVO();
		orderInfo = rtnService.selectTaxReturnInfo(nno);
		
		model.addAttribute("orderInfo", orderInfo);
		return "adm/aplctList/list/return/popupTaxReturn";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/deleteItem/{nno}", method = RequestMethod.POST)
	public String mngrReturnOrderDel(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("nno") String nno) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		String sellerId = request.getParameter("sellerId");
		String state = request.getParameter("state");
		try {
			params.put("nno", nno);
			params.put("sellerId", sellerId);
			params.put("state", state);
			rtnService.deleteReturnOrder(params);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return "redirect:/mngr/order/return/list";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/taxReturnList", method = RequestMethod.GET)
	public String mngrTaxReturnList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		int curPage = 1;
		ArrayList<ReturnListVO> vo = new ArrayList<ReturnListVO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");
		String option = "";
		String keywords = "";
	 
		map.put("orgStation", orgStation);
		
		if(request.getParameter("keywords")!=null) {
			keywords = request.getParameter("keywords");
		}
		option = request.getParameter("option");
		
		map.put("option", option);
		map.put("keywords", keywords);
		
		model.addAttribute("option", option);
		model.addAttribute("keywords", keywords);
		
		int totalCount = rtnService.selectTaxReturnRequestCnt(map);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,50);
		map.put("paging", paging);
		
		try {
			vo = rtnService.selectTaxReturnRequest(map);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", map);
		model.addAttribute("vo", vo);
		
		return "adm/aplctList/list/return/managerTaxReturnOrderList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/returnOrder/inspList", method = RequestMethod.GET)
	public String mngrReturnOrderInspList(HttpServletRequest request, HttpServletResponse response, Model model, ReturnRequestVO parameters) throws Exception {
		
		int curPage = 1;
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		
		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		parameterOption.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			parameterOption.put("reTrkNo", searchKeywords);
		}
		
		ArrayList<ReturnRequestVO> inspList = new ArrayList<ReturnRequestVO>();
		
		ArrayList<LinkedHashMap<String,Object>> returnInspList = new ArrayList<LinkedHashMap<String,Object>>();
		
		//int totalCount = rtnService.selectReturnInspListCnt(parameterOption);
		int totalCount = rtnService.selectB001ReturnListCnt(parameterOption);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,30);
		parameterOption.put("paging", paging);
		
		parameters.setOrgStation((String)request.getSession().getAttribute("ORG_STATION"));
		
		try {
			inspList = rtnService.selectReturnOrderInspList(parameters);
			for(int i = 0; i < inspList.size(); i++) {
				inspList.get(i).dncryptData();
			}
			//returnInspList = rtnService.selectReturnInspList(parameterOption);
			//returnInspList = rtnService.selectB001ReturnList(parameterOption);
			//inspList = rtnService.selectReturnInspList(parameterOption);
		} catch (Exception e) {
			returnInspList = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", parameterOption);
		model.addAttribute("inspList", inspList);
		//model.addAttribute("returnInspList", returnInspList);
		
		return "adm/aplctList/list/return/managerReturnInspList";
	}
	
	@RequestMapping(value="/comn/StockCancleReturn",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> spStockCancle(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashMap<String, Object> parameters = new HashMap<String,Object>();
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("groupIdx", request.getParameter("groupIdx"));
		parameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		String nno = request.getParameter("nno");
		
		Rst = rtnService.spStockCancle(parameters);
		rtnService.updateStockCancel(nno);
		
		return Rst;
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/stockIn", method = RequestMethod.POST)
	@ResponseBody
	public void mngrReturnSockInCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String nno = request.getParameter("nno");
		rtnService.updateReturnStockIn(nno);
	}
	
	@RequestMapping(value = "/mngr/rls/return/inspcStockOutChk", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> managerReturnInspcStockOutChk(HttpServletRequest request, HttpServletResponse response, Model model, String targetParm) throws Exception {
		
		HashMap<String,Object> rtnVal = new HashMap<String,Object>();
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		
		targetParm = request.getParameter("targetParm");
		System.out.println(targetParm);
		String[] targets = targetParm.split(",");
		for (int roop = 0; roop < targets.length; roop++) {
			ProcedureVO execRtn = new ProcedureVO();
			parameter.put("stockNo", targets[roop]);
			parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
			parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
			parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
			execRtn = mgrService.execSpWhoutStock(parameter);
			rtnVal.put("qryRtn",execRtn);
			if(!"SUCCSESS".equals(execRtn.getRstStatus())) {
				rtnVal.put("result", "F");
			}else {
				rtnVal.put("result", "S");
			}
		}
		
		return rtnVal;
		
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/InspStockIn", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> managerReturnInspcStockOutChk2(HttpServletRequest request, HttpServletResponse response, Model model, String targetParm) throws Exception {

		HashMap<String, Object> Rst = new HashMap<String, Object>();
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		
		try {
			ProcedureVO execRtn = new ProcedureVO();
			parameter.put("stockNo", request.getParameter("stockNo"));
			//parameter.put("userId", request.getParameter("userId"));
			//parameter.put("nno", request.getParameter("nno"));
			parameter.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
			parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
			parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
				
			execRtn = mgrService.execSpWhoutStock(parameter);
			Rst.put("qryRtn",execRtn);
			if(!"SUCCSESS".equals(execRtn.getRstStatus())) {
				Rst.put("result", "F");
			}else {
				Rst.put("result", "S");
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return Rst;
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/inspStockInJson", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<HashMap<String, Object>> managerInspOrderItemJson(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		params.put("orderReference", request.getParameter("orderReference"));
		params.put("nno", request.getParameter("nno"));
		params.put("userId", request.getParameter("userId"));
		
		ArrayList<HashMap<String, Object>> orderItem = new ArrayList<HashMap<String, Object>>();
		orderItem = rtnService.selectReturnOrderInfo2(params);
		
		return orderItem; 
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/stockInBatch", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> returnOrderItemStockInBatch(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String,Object> rtnVal = new HashMap<String,Object>();
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		parameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
		parameter.put("nno", request.getParameter("nno"));
		parameter.put("userId", request.getParameter("userId"));
		parameter.put("wUserId", request.getSession().getAttribute("USER_ID"));
		parameter.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		
		ArrayList<OrderInspVO> orderInfo = new ArrayList<OrderInspVO>();
		orderInfo = rtnService.selectReturnInspOrderItemList(parameter);
		
		
		if (orderInfo.size() > 0) {
			for (int i = 0; i < orderInfo.size(); i++) {
				ProcedureVO execRtn = new ProcedureVO();
				params.put("stockNo", orderInfo.get(i).getStockNo());
				params.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
				params.put("wUserId", request.getSession().getAttribute("USER_ID"));
				params.put("wUserIp", request.getSession().getAttribute("USER_IP"));
				params.put("nno", request.getParameter("nno"));
				
				execRtn = mgrService.execSpWhoutStock(params);
				rtnVal.put("qryRtn",execRtn);
				if(!"SUCCSESS".equals(execRtn.getRstStatus())) {
					rtnVal.put("result", "F");
				}else {
					rtnVal.put("result", "S");
				}
			}
		} else {
			rtnVal.put("result", "F");	
		}
		
		return rtnVal;
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/csList", method = RequestMethod.GET)
	public String managerReturnCSList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		try {
		
		
		int curPage = 1;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgStation = (String)request.getSession().getAttribute("ORG_STATION");	 
		map.put("orgStation", orgStation);


		String searchType="";
		String searchKeywords = "";
		
		if (request.getParameter("keywords") != null) {
			searchType = request.getParameter("searchType");
			searchKeywords = request.getParameter("keywords");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeywords);
		
		map.put("searchType", searchType);
		
		if (searchType.equals("reTrkNo")) {
			map.put("reTrkNo", searchKeywords);
			map.put("koblNo", "");
			map.put("userId", "");
		} else if (searchType.equals("koblNo")) {
			map.put("reTrkNo", "");
			map.put("koblNo", searchKeywords);
			map.put("userId", "");
		} else {
			map.put("reTrkNo", "");
			map.put("koblNo", "");
			map.put("userId", searchKeywords);
		}
		
		
		int totalCount = rtnService.selectReturnCSListCnt(map);
		
		if(request.getParameter("page")!=null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PagingVO paging = new PagingVO(curPage,totalCount,10,20);
		map.put("paging", paging);
		
		try {
			//vo = rtnService.getReturnRequestData(map);
			list = rtnService.selectReturnCSList(map); 
		} catch (Exception e) {
			list = null;
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("map", map);
		model.addAttribute("list", list);
		
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return "adm/aplctList/list/return/managerReturnCSList";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/popupMsg", method = RequestMethod.GET)
	public String managerReturnCSPopup(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		
		HashMap<String, Object> msgHis = new HashMap<String, Object>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("idx", request.getParameter("idx"));
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("type", request.getParameter("type"));
		
		msgHis = returnService.selectMsgHis(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", parameters);
		
		return "adm/aplctList/list/return/popupReturnCSMsg";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/sendCsMsg", method = RequestMethod.POST)
	public String managerSendReturnCSMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> msgHis = new HashMap<String, Object>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		
		parameters.put("idx", request.getParameter("idx"));
		parameters.put("orgStation", request.getParameter("orgStation"));
		parameters.put("userId", request.getParameter("userId"));
		
		msgHis = returnService.selectMsgHis(parameters);
		model.addAttribute("msgHis", msgHis);
		model.addAttribute("parameters", parameters);
		
		params.put("orgStation", request.getParameter("orgStation"));
		params.put("userId", request.getParameter("userId"));
		params.put("idx", request.getParameter("idx"));
		params.put("msgDiv", "RETURN");
		params.put("nno", request.getParameter("nno"));
		params.put("readYn", "Y");
		
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String mDate = format.format(nowDate);
		String wDate = format.format(nowDate);
		
		params.put("adminMemo", request.getParameter("adminMemo"));
		
		if (request.getParameter("type").equals("m")) {
			params.put("mDate", mDate);
		} else {
			params.put("adminWDate", wDate);	
		}
		
		
		returnService.updateMsgInfo(params);
		
		return "adm/aplctList/list/return/popupReturnCSMsg";
	}
	
	@RequestMapping(value = "/mngr/aplctList/return/deposit", method = RequestMethod.GET)
	public String managerReturnDeposit(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		int curPage = 1;
		String userId = request.getParameter("keywords");
		/*
		 * paging start 함수로 뺄것.
		 */
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("userId", userId);
		infoMap.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		infoMap.put("type", "deposit"); 
		int totalCount = mgrService.selectTotalCountInfo(infoMap);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		PagingVO paging = new PagingVO(curPage, totalCount,20,50);
		// paging 끝

		infoMap.put("paging", paging);
		ArrayList<HashMap<String,Object>> comUserInfo = rtnService.selectDepositUserList(infoMap);

		model.addAttribute("searchKeyword", userId);
		model.addAttribute("userInfo", comUserInfo);
		model.addAttribute("paging", paging);
		
		
		return "adm/aplctList/list/return/managerReturnDepositList";
	}
	
	
	@RequestMapping(value = "/mngr/aplctList/depositDetail/{userId}", method = RequestMethod.GET)
	public String managerDepositDetail(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("userId") String userId) throws Exception {
		
		try {
			
			HashMap<String, Object> depoistPrice = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> depositInfo = new ArrayList<HashMap<String, Object>>();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			
			params.put("calculation", request.getParameter("calculation"));
			
			if (request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {
				params.put("fromDate", request.getParameter("fromDate"));
				params.put("toDate", request.getParameter("toDate"));	
			}
			
			
			int curPage = 1;
			int totalCount = rtnService.selectDepositInfoCnt(params);
			
			if (request.getParameter("page") != null) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			PagingVO paging = new PagingVO(curPage, totalCount, 5, 10);
			
			params.put("paging", paging);
			
	
			depoistPrice = rtnService.selectDepositTotal(userId);
			depositInfo = rtnService.selectDepositInfo(params);
			model.addAttribute("params", params);
			model.addAttribute("depoistPrice", depoistPrice);
			model.addAttribute("depositInfo", depositInfo);
			model.addAttribute("userId", userId);
			model.addAttribute("paging", paging);
			
			
		} catch (Exception e) {
			 logger.error("Exception", e);
		}
		
		return "adm/aplctList/list/return/managerDepositDetail";
	}
	
	@RequestMapping(value = "/mngr/aplctList/deposit/add", method = RequestMethod.POST)
	@ResponseBody
	public String managerReturnDepositAddPrice(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String result = "";
		String code = request.getParameter("depositCode");
		String userId = request.getParameter("userId");
		Double cost = Double.parseDouble(request.getParameter("depositPrice"));
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		try {
			params.put("code", code);
			params.put("userId", userId);
			params.put("cost", cost);
			params.put("wUserId", request.getSession().getAttribute("USER_ID"));
			params.put("wUserIp", request.getSession().getAttribute("USER_IP"));
			params.put("calculation", "A");
			params.put("remark", "");
			
			Double costNow = rtnService.selectDepositCostNow(params);
			Double depositNow = costNow + cost;
			params.put("depositNow", depositNow);
			
			rtnService.insertDepositAdd(params);
			
			result = "S";
			
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "/mngr/aplctList/deposit/del", method = RequestMethod.POST)
	@ResponseBody
	public String managerReturnDepositDelPrice(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String result = "";
		String code = request.getParameter("depositCode");
		String userId = request.getParameter("userId");
		Double cost = Double.parseDouble(request.getParameter("depositPrice"));
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		try {
			params.put("code", code);
			params.put("userId", userId);
			params.put("cost", cost);
			params.put("wUserId", request.getSession().getAttribute("USER_ID"));
			params.put("wUserIp", request.getSession().getAttribute("USER_IP"));
			params.put("calculation", "B");
			
			if (request.getParameter("remark") != null) {
				params.put("remark", request.getParameter("remark"));
			} else {
				params.put("remark", "");
			}
			
			Double costNow = rtnService.selectDepositCostNow(params);
			Double depositNow = costNow - cost;
			params.put("depositNow", depositNow);
			
			rtnService.insertDepositAdd(params);
			
			result = "S";
			
		} catch (Exception e) {
			logger.error("Exception", e);
			result = "F";
		}
		
		return result;
	}


	
}

	
	