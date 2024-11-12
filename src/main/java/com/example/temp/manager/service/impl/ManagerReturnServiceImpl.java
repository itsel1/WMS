package com.example.temp.manager.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocPartName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestItemVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.api.webhook.controller.WebhookController;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.mapper.ManagerReturnMapper;
import com.example.temp.manager.service.ManagerReturnService;
import com.example.temp.manager.vo.OrderInspVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.StockAllVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.insp.InspStockOutVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.smtp.SmtpService;
import com.example.temp.trans.aci.AciAPI;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.cse.CseAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.itextpdf.text.pdf.PRAcroForm;

@Service
@Transactional
public class ManagerReturnServiceImpl implements ManagerReturnService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SmtpService smtpService;
	
	@Autowired
	private ManagerReturnMapper mapper;
	
	@Autowired
	WebhookController webhookController;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	private ManagerMapper mgrMapper;
	
	@Value("${filePath}")
    String realFilePath;
	
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
	FedexAPI fedexApi;
	
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	private SecurityKeyVO originKey = new SecurityKeyVO();

	@Override
	public int getTotalReturnRequestCnt(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getTotalReturnRequestCnt(map);
	}

	@Override
	public ArrayList<ReturnListVO> getReturnRequestData(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getReturnRequestData(map);
	}
	
	@Override
	public String updateReturnExcel(MultipartHttpServletRequest multi, HttpServletRequest request, HashMap<String, String> parameters) {
		String result = "F";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date time = new Date();
		DataFormatter formatter = new DataFormatter();

		String time1 = format1.format(time);
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
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						ReturnVO vo = new ReturnVO();
						vo.setState("A002");
						XSSFRow row = sheet.getRow(rowIndex);
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							currentRow = rowIndex;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								XSSFCell cell = row.getCell(columnIndex);
								if (cell == null) {
									value = "";
								} else {
									switch (cell.getCellType()) {
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
								case 0:
									vo.setReTrkNo(value);
									break;
								case 4:
									vo.setKoblNo(value);
									break;
								}
							}
							if(vo.getKoblNo() != null) {
								if(!vo.getKoblNo().equals("")) {
									mapper.updateReturnExcel(vo);
									webhookController.returnRequestWebHook("", "", vo.getKoblNo());
									String orderReference = mapper.selectOrderReferenceByKobl(vo.getKoblNo());
									updateOrderRegistInReturn("A002",orderReference);
								}
							}
						}
					}
				} else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						ReturnVO vo = new ReturnVO();
						vo.setState("A002");
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							currentRow = rowIndex;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
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
									vo.setReTrkNo(value);
									break;
								case 4:
									vo.setKoblNo(value);
									break;
								}
							}
							if(vo.getKoblNo() != null) {
								if(!vo.getKoblNo().equals("")) {
									mapper.updateReturnExcel(vo);
									webhookController.returnRequestWebHook("", "", vo.getKoblNo());
									String orderReference = mapper.selectOrderReferenceByKobl(vo.getKoblNo());
									updateOrderRegistInReturn("A002",orderReference);
								}
							}
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
		String path = uploadPaths; // 저장 경로 설정

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
	   public void returnExcelDown(ReturnVO returnVO, HttpServletRequest request, HttpServletResponse response) {
	      
	      try {
	         
	         ArrayList<ReturnVO> testList = mapper.selectReturnListExcel(returnVO);
	         
	         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	         Date now = new Date();
	         
	         String date = format.format(now);
	         
	         String filename = "epost_"+date+"_"+request.getSession().getAttribute("USER_ID").toString()+".xlsx";
	         
	         Workbook workbook = new XSSFWorkbook();
	         Sheet sheet = workbook.createSheet("epost");
	         
	         // Font Style
	         Font font = workbook.createFont();
	         font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	         font.setFontName("NanumGothic");
	         font.setFontHeightInPoints((short)9);
	         
	         Font font2 = workbook.createFont();
	         font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
	         font2.setFontName("NanumGothic");
	         font2.setFontHeightInPoints((short)9);
	         font2.setColor(HSSFColor.DARK_RED.index);
	         
	         Font font3 = workbook.createFont();
	         font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
	         font3.setFontName("NanumGothic");
	         font3.setFontHeightInPoints((short)9);
	         font3.setColor(HSSFColor.WHITE.index);
	         
	         // Cell Style 
	         CellStyle cellStyleHeader = workbook.createCellStyle();
	         cellStyleHeader.setWrapText(false);
	         cellStyleHeader.setAlignment(CellStyle.ALIGN_CENTER);
	         cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	         cellStyleHeader.setFont(font);
	         cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
	         cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	         cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
	         cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	         
	         CellStyle cellStyleBody = workbook.createCellStyle();
	         cellStyleBody.setAlignment(CellStyle.ALIGN_CENTER);
	         cellStyleBody.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	         
	         CellStyle cellStyleOrange = workbook.createCellStyle();
	         cellStyleOrange.setWrapText(false);
	         cellStyleOrange.setAlignment(CellStyle.ALIGN_CENTER);
	         cellStyleOrange.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	         cellStyleOrange.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
	         cellStyleOrange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	         cellStyleOrange.setFont(font2);
	         cellStyleOrange.setBorderRight(HSSFCellStyle.BORDER_THIN);
	         cellStyleOrange.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	         cellStyleOrange.setBorderTop(HSSFCellStyle.BORDER_THIN);
	         cellStyleOrange.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	         
	         CellStyle cellStyleRed = workbook.createCellStyle();
	         cellStyleRed.setWrapText(false);
	         cellStyleRed.setAlignment(CellStyle.ALIGN_CENTER);
	         cellStyleRed.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	         cellStyleRed.setFillForegroundColor(HSSFColor.RED.index);
	         cellStyleRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	         cellStyleRed.setFont(font3);
	         cellStyleRed.setBorderRight(HSSFCellStyle.BORDER_THIN);
	         cellStyleRed.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	         cellStyleRed.setBorderTop(HSSFCellStyle.BORDER_THIN);
	         cellStyleRed.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	         
	         
	         Row row = null;
	         Cell cell = null;
	         int rowNo = 0;
	         
	         row = sheet.createRow(rowNo);
	         cell = row.createCell(0);
	         cell.setCellValue("No");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(1);
	         cell.setCellValue("주문자명");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(2);
	         cell.setCellValue("주문자전화번호");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(3);
	         cell.setCellValue("주문자전화번호2");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(4);
	         cell.setCellValue("주문자우편번호");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(5);
	         cell.setCellValue("주문자주소");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(6);
	         cell.setCellValue("주문자상세주소");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(7);
	         cell.setCellValue("수취인명");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(8);
	         cell.setCellValue("수취인전화번호");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(9);
	         cell.setCellValue("수취인전화번호2");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(10);
	         cell.setCellValue("수취인우편번호");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(11);
	         cell.setCellValue("수취인주소");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(12);
	         cell.setCellValue("수취인상세주소");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(13);
	         cell.setCellValue("지불방법");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(14);
	         cell.setCellValue("등기번호");
	         cell.setCellStyle(cellStyleRed);
	         
	         cell = row.createCell(15);
	         cell.setCellValue("주문번호 (영수증번호+배달순서)");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(16);
	         cell.setCellValue("상품구분");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(17);
	         cell.setCellValue("상품코드");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(18);
	         cell.setCellValue("상품명");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(19);
	         cell.setCellValue("중량(g)");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(20);
	         cell.setCellValue("부피(cm)");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(21);
	         cell.setCellValue("수량");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(22);
	         cell.setCellValue("초소형");
	         cell.setCellStyle(cellStyleOrange);
	         
	         cell = row.createCell(23);
	         cell.setCellValue("요금");
	         cell.setCellStyle(cellStyleRed);
	         
	         cell = row.createCell(24);
	         cell.setCellValue("발송메세지");
	         cell.setCellStyle(cellStyleHeader);
	         
	         cell = row.createCell(25);
	         cell.setCellValue("당일특급");
	         cell.setCellStyle(cellStyleOrange);
	         
	         sheet.setColumnWidth(0, 1000);
	         sheet.setColumnWidth(1, 4000);
	         sheet.setColumnWidth(2, 5000);
	         sheet.setColumnWidth(3, 5000);
	         sheet.setColumnWidth(4, 5000);
	         sheet.setColumnWidth(5, 10000);
	         sheet.setColumnWidth(6, 15000);
	         sheet.setColumnWidth(7, 8000);
	         sheet.setColumnWidth(8, 5000);
	         sheet.setColumnWidth(9, 5000);
	         sheet.setColumnWidth(10, 5000);
	         sheet.setColumnWidth(11, 6000);
	         sheet.setColumnWidth(12, 10000);
	         sheet.setColumnWidth(14, 4000);
	         sheet.setColumnWidth(15, 7000);

	         sheet.setZoom(17, 20);
	         
	         rowNo = 1;
	         for (int i = 0; i < testList.size(); i++) {
	            //testList.get(i).dncryptData();
	            
	            row = sheet.createRow(rowNo++);
	            cell = row.createCell(0);
	            cell.setCellStyle(cellStyleBody);
	            cell.setCellValue(i+1);
	            
	            cell = row.createCell(1);
	            cell.setCellValue(testList.get(i).getPickupName());
	            
	            cell = row.createCell(2);
	            cell.setCellValue(convertTelNo(testList.get(i).getPickupTel()));
	            //cell.setCellValue(testList.get(i).getPickupTel());
	            
	            cell = row.createCell(3);
	            System.out.println(testList.get(i).getPickupMobile());
	            cell.setCellValue(convertTelNo(testList.get(i).getPickupHp()));
	            //cell.setCellValue(testList.get(i).getPickupMobile());
	            
	            cell = row.createCell(4);
	            cell.setCellValue(testList.get(i).getPickupZip());
	            
	            cell = row.createCell(5);
	            cell.setCellValue(testList.get(i).getPickupAddr());
	            
	            cell = row.createCell(6);
	            cell.setCellValue("");
	            
	            cell = row.createCell(7);
	            cell.setCellValue("(주)에이씨아이월드와이드");
	            
	            cell = row.createCell(8);
	            cell.setCellValue("032-744-9550");
	   
	            cell = row.createCell(9);
	            cell.setCellValue("032-744-9550");
	   
	            cell = row.createCell(10);
	            cell.setCellValue("22382");
	   
	            cell = row.createCell(11);
	            cell.setCellValue("인천광역시 중구 운서동");
	   
	            cell = row.createCell(12);
	            cell.setCellValue("2850번지 자유무역지역 G5블럭 203호 ACI");
	            
	            cell = row.createCell(13);
	            cell.setCellValue("착불");
	   
	            cell = row.createCell(14);
	            cell.setCellValue("");
	            
	            cell = row.createCell(15);
	            cell.setCellStyle(cellStyleBody);
	            cell.setCellValue(testList.get(i).getKoblNo());
	            
	            cell = row.createCell(16);
	            cell.setCellValue("기타");
	            
	            cell = row.createCell(17);
	            cell.setCellValue("");
	            
	            cell = row.createCell(18);
	            cell.setCellValue(testList.get(i).getItemDetail());
	            
	            cell = row.createCell(19);
	            //cell.setCellValue(String.valueOf(testList.get(i).getItemWta()));
	            cell.setCellValue("2000");
	            
	            cell = row.createCell(20);
	            //cell.setCellValue("");
	            cell.setCellValue("60");
	            
	            cell = row.createCell(21);
	            cell.setCellValue(String.valueOf(testList.get(i).getItemCnt()));
	            
	            cell = row.createCell(22);
	            cell.setCellValue("N");
	            
	            cell = row.createCell(23);
	            cell.setCellValue("");
	            
	            cell = row.createCell(24);
	            cell.setCellValue("");
	            
	            cell = row.createCell(25);
	            cell.setCellValue("N");
	            
	/*
	            if(testList.get(i).getRtnType() == "일반배송") {
	               cell.setCellValue("N");
	            } else {
	               cell.setCellValue("Y");
	            }
	*/         
	         }

	           // 컨텐츠 타입과 파일명 지정
	          response.setContentType("ms-vnd/excel");
	          response.setHeader("Content-Disposition", "attachment;filename="+filename);
	          
	          BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
	          workbook.write(out);
	           out.flush();
	           out.close();
	         
	      } catch (Exception e) {
	         logger.error("Exception", e);
	      }
	   }

	@Override
	public ArrayList<ReturnItemVO> getReturnItemByNno(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getReturnItemByNno(nno);
	}

	@Override
	public ReturnRequestVO getAllExpressData(String orderReference, String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAllExpressData(orderReference, userId);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspList(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectReturnInspList(parameterOption);
		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}

	@Override
	public int selectReturnInspListCnt(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspListCnt(parameterOption);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnInspOne(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>(); 
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		ReturnRequestVO returnInspOrder = new ReturnRequestVO();
		returnInspOrder = mapper.selectReturnInspOrder2(parameterOption);
		returnInspOrder.dncryptData();
		
		orderInfo.put("orderReference", returnInspOrder.getOrderReference());
		orderInfo.put("nno", returnInspOrder.getNno());
		orderInfo.put("sellerId", returnInspOrder.getSellerId());
		orderInfo.put("koblNo", returnInspOrder.getKoblNo());
		orderInfo.put("reTrkNo", returnInspOrder.getReTrkNo());
		orderInfo.put("reTrkCom", returnInspOrder.getReTrkCom());
		orderInfo.put("senderName", returnInspOrder.getSenderName());
		orderInfo.put("senderTel", returnInspOrder.getSenderTel());
		orderInfo.put("senderAddr", returnInspOrder.getSenderAddr());
		orderInfo.put("senderZip", returnInspOrder.getSenderZip());
		orderInfo.put("dstnNation", returnInspOrder.getDstnNation());
		orderInfo.put("whMsg", returnInspOrder.getWhMsg());
		
		orderItem = mapper.selectReturnInspItem(orderInfo.get("nno"));
		
		orderInfo.put("orderItemList", orderItem);
		return orderInfo ;
	}
	
	@Override
	public LinkedHashMap<String, Object> selectReturnInspReadOne(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>(); 
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<LinkedHashMap<String, Object>> imgList = new ArrayList<LinkedHashMap<String, Object>>();
		ReturnRequestVO returnOrder = new ReturnRequestVO();
		//LinkedHashMap<String, Object> imgList = new LinkedHashMap<String, Object>();
		orderInfo = mapper.selectReturnInspOne(parameterOption);
		HashMap<String, Object> stockMsg = new HashMap<String, Object>();
		int cnt = mapper.selectStockMsgCnt(orderInfo.get("NNO"));
		if (cnt > 0) {
			stockMsg = mapper.selectStockMsg(orderInfo.get("NNO"));
			orderInfo.put("FAIL_MSG", stockMsg.get("WH_MEMO").toString());
			orderInfo.put("FAIL_REASON", stockMsg.get("STATUS").toString());
		} else {
			orderInfo.put("FAIL_MSG", "");
			orderInfo.put("FAIL_REASON", "");
		}
		
		returnOrder = mapper.selectReturnInfos(parameterOption);
		returnOrder.dncryptData();
		orderItem = mapper.selectReturnInspReadItem(orderInfo.get("NNO"));
		
		orderInfo.put("PICKUP_TEL", returnOrder.getPickupTel());
		orderInfo.put("PICKUP_MOBILE", returnOrder.getPickupMobile());
		orderInfo.put("PICKUP_ADDR", returnOrder.getPickupAddr());
		orderInfo.put("PICKUP_ENG_ADDR", returnOrder.getPickupEngAddr());
		orderInfo.put("SENDER_TEL", returnOrder.getSenderTel());
		orderInfo.put("SENDER_HP", returnOrder.getSenderHp());
		orderInfo.put("SENDER_ADDR", returnOrder.getSenderAddr());
		orderInfo.put("SENDER_BUILD_NM", returnOrder.getSenderBuildNm());
		double itemPrice = 0;
		for(int j=0;j<orderItem.size();j++) {
			imgList = new ArrayList<LinkedHashMap<String, Object>>();
			itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			imgList = mapper.selectStockImgList(orderInfo.get("NNO").toString(),orderItem.get(j).get("SUB_NO").toString());
			if(imgList.size() != 0) {
				for (int i = 0; i < imgList.size(); i++) {
					String urls = imgList.get(i).get("FILE_DIR").toString();
					//String urls = imgList.get("FILE_DIR").toString(); 
					
					String[] urlpart = urls.split("/");
					String newUrl = "";
					for(int k = 0; k < urlpart.length; k++) {
						if(k != urlpart.length-1) {
							newUrl += urlpart[k];
							newUrl += "/";
						}else {
							String aaa = URLEncoder.encode(urlpart[k],"UTF-8");
							newUrl += aaa;
						}
					}
					imgList.get(i).put("FILE_DIR", newUrl);
					//imgList.put("FILE_DIR", newUrl);
				}
				
			}
			orderItem.get(j).put("imgList", imgList);
		}
		orderInfo.put("TOTAL_VALUE", itemPrice);
		orderInfo.put("orderItemList", orderItem);
		return orderInfo ;
	}

	@Override
	public LinkedHashMap<String, Object> returnRequestWhProcess(HttpServletRequest request,
			HttpServletResponse response, MultipartHttpServletRequest multi, String orderReference) throws Exception {
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		LinkedHashMap<String, Object> rtnValues = new LinkedHashMap<String, Object>();
		//LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		ReturnRequestVO returnOrder = new ReturnRequestVO();
		
		String nno = mapper.selectReturnOrderNNO(orderReference);
		parameterOption.put("nno", nno);
		parameterOption.put("orderReference", orderReference);
		//returnInspOne = selectReturnInspOne(parameterOption);
		returnOrder = mapper.selectReturnInspOrder(orderReference);
		returnOrder.dncryptData();
		StockAllVO stockAll = new StockAllVO();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		int itemCnt = Integer.parseInt(request.getParameter("itemCnt"));
		Map<String, String[]> parameterMaps = request.getParameterMap();
		ZoneId zoneId = ZoneId.of("Asia/Seoul");
		//ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime zoneDateTime = ZonedDateTime.now((zoneId));
		String whInDate = zoneDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		//set stock infomation 
		int pivotIndex1 = 0;
		int pivotIndex2 = 0;
		
		stockAll.setNno(nno);
		stockAll.setUserId(returnOrder.getSellerId());
		stockAll.setOrgStation(request.getSession().getAttribute("ORG_STATION").toString());
		stockAll.setRackCode(request.getParameter("rack"));
		stockAll.setWhStatus(request.getParameter("whStatus"));
		stockAll.setWUserId(userId);
		stockAll.setWUserIp(request.getRemoteHost());
		stockAll.setWhInDate(whInDate);
		stockAll.setTrkCom(returnOrder.getReTrkCom());
		stockAll.setTrkNo(returnOrder.getReTrkNo());
		stockAll.setWhMemo(request.getParameter("whMemo"));
		stockAll.setGroupIdx("");
		for (int itemIndex = 0; itemIndex < itemCnt; itemIndex++) {
			StockResultVO temp = new StockResultVO();
			stockAll.setSubNo(Integer.toString(itemIndex+1));
			stockAll.setWhInCnt(parameterMaps.get("housCnt")[itemIndex]);
			stockAll.setWidth(parameterMaps.get("width")[itemIndex]);
			stockAll.setHeight(parameterMaps.get("height")[itemIndex]);
			stockAll.setLength(parameterMaps.get("length")[itemIndex]);
			stockAll.setPer(parameterMaps.get("per")[itemIndex]);
			stockAll.setWta(parameterMaps.get("wta")[itemIndex]);
			stockAll.setWtUnit(parameterMaps.get("wtUnit")[itemIndex]);
			stockAll.setDimUnit(parameterMaps.get("dimUnit")[itemIndex]);
			
			//stockAll.setGroupIdx("");
			temp = mapper.insertReturnStock(stockAll);
			pivotIndex1 = pivotIndex2;
			pivotIndex2 = pivotIndex2 + Integer.parseInt(parameterMaps.get("itemImgCnt")[itemIndex]);
			String imageDir = realFilePath + "image/" + "stock/" + stockAll.getNno() + "/";
			File dir = new File(imageDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			imageDir = imageDir + stockAll.getSubNo() + "/";
			File dir2 = new File(imageDir);
			if (!dir2.exists()) {
				dir2.mkdir();
			}
			
			stockAll.setGroupIdx(temp.getGroupIdx());
			int fileIdx = 0;
			
			for (int fileIndex = pivotIndex1; fileIndex < pivotIndex2; fileIndex++) {
				fileIdx++;
				MultipartFile file = multi.getFiles("input_imgs").get(fileIndex);
				
				if (file.isEmpty()) {
					break;
				}
				try {
					amazonS3 = new AmazonS3Client(awsCredentials);
					FileOutputStream fos = new FileOutputStream(
							imageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
					InputStream is = file.getInputStream();
					int readCount = 0;
					byte[] buffer = new byte[1024];
					while ((readCount = is.read(buffer)) != -1) {
						fos.write(buffer, 0, readCount);
					}
					is.close();
					fos.close();
					File uploadFile = new File(imageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
					if (amazonS3 != null && !"".equals(file.getOriginalFilename())) {
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()
								+ "/outbound/stock/return/" + stockAll.getNno() + "/" + stockAll.getSubNo(),
								uploadFile.getName(), uploadFile);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						amazonS3.putObject(putObjectRequest);
					}
					stockAll.setFileDir("img.mtuai.com/outbound/stock/return/" + stockAll.getNno() + "/"
							+ stockAll.getSubNo() + "/" + uploadFile.getName());
					stockAll.setFileIdx(Integer.toString(fileIdx));

					mapper.insertReturnStockFile(stockAll);
					uploadFile.delete();
					amazonS3 = null;
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		parameterOption.put("status", "C003");
		mapper.updateReturnStatus(parameterOption);
		//webhook C003
		webhookController.returnRequestWebHook(orderReference, "", "");
		
		return rtnValues;
	}

	@Override
	public LinkedHashMap<String, Object> returnRequestWhFailProcess(HttpServletRequest request,
			MultipartHttpServletRequest multi, HttpServletResponse response, String orderReference) throws Exception {
		// TODO Auto-generated method stub
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		LinkedHashMap<String, Object> rtnValues = new LinkedHashMap<String, Object>();
		LinkedHashMap<String,Object> returnInspOne = new LinkedHashMap<String,Object>();
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		parameterOption.put("orderReference", orderReference);
		StockAllVO stockAll = new StockAllVO();
		ZoneId zoneId = ZoneId.of("Asia/Seoul");
		//ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime zoneDateTime = ZonedDateTime.now((zoneId));
		String whInDate = zoneDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		String userId = request.getSession().getAttribute("USER_ID").toString();
		List<MultipartFile> fileList = multi.getFiles("input_imgs");
		int itemCnt = Integer.parseInt(request.getParameter("itemCnt"));
		Map<String, String[]> parameterMaps = request.getParameterMap();
		String nno = request.getParameter("nno");
		
		ReturnRequestVO returnOrder = new ReturnRequestVO();
		//returnInspOne = selectReturnInspOne(parameterOption);
		returnOrder = mapper.selectReturnInspOrder(orderReference);
		returnOrder.dncryptData();
		
		int pivotIndex1 = 0;
		int pivotIndex2 = 0;
		//set stock infomation
		stockAll.setNno(nno);
		stockAll.setUserId(returnOrder.getSellerId());
		stockAll.setOrgStation(request.getSession().getAttribute("ORG_STATION").toString());
		stockAll.setRackCode(request.getParameter("rack"));
		stockAll.setWhStatus(request.getParameter("whStatus"));
		stockAll.setWUserId(userId);
		stockAll.setWUserIp(request.getRemoteHost());
		stockAll.setWhInDate(whInDate);
		stockAll.setTrkCom(returnOrder.getReTrkCom());
		stockAll.setTrkNo(returnOrder.getReTrkNo());
		if(request.getParameter("whDetailStatus").equals("WF1")) {
			stockAll.setWhMemo("부분 입고");
			stockAll.setWhDetailStatus("WF1");
		}else if(request.getParameter("whDetailStatus").equals("WF2")) {
			stockAll.setWhMemo("상품 파손");
			stockAll.setWhDetailStatus("WF2");
		}else if(request.getParameter("whDetailStatus").equals("WF3")) {
			stockAll.setWhMemo(request.getParameter("failReason"));
			stockAll.setWhDetailStatus("WF3");
		}
		
		
		for (int itemIndex = 0; itemIndex < itemCnt; itemIndex++) {
			StockResultVO temp = new StockResultVO();
			stockAll.setSubNo(Integer.toString(itemIndex+1));
			stockAll.setWhInCnt(parameterMaps.get("housCnt")[itemIndex]);
			stockAll.setWidth(parameterMaps.get("width")[itemIndex]);
			stockAll.setHeight(parameterMaps.get("height")[itemIndex]);
			stockAll.setLength(parameterMaps.get("length")[itemIndex]);
			stockAll.setPer(parameterMaps.get("per")[itemIndex]);
			stockAll.setWta(parameterMaps.get("wta")[itemIndex]);
			stockAll.setWtUnit(parameterMaps.get("wtUnit")[itemIndex]);
			stockAll.setDimUnit(parameterMaps.get("dimUnit")[itemIndex]);
			stockAll.setGroupIdx("");
			temp = mapper.insertReturnStock(stockAll);
			mapper.updateStockMsgStatus(stockAll);
			pivotIndex1 = pivotIndex2;
			pivotIndex2 = pivotIndex2 + Integer.parseInt(parameterMaps.get("itemImgCnt")[itemIndex]);
			String imageDir = realFilePath + "image/" + "stock/" + stockAll.getNno() + "/";
			File dir = new File(imageDir);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			imageDir = imageDir + stockAll.getSubNo() + "/";
			File dir2 = new File(imageDir);
			if (!dir2.isDirectory()) {
				dir2.mkdir();
			}
			
			stockAll.setGroupIdx(temp.getGroupIdx());
			int fileIdx = 0;
			
			for (int fileIndex = pivotIndex1; fileIndex < pivotIndex2; fileIndex++) {
				fileIdx++;
				MultipartFile file = multi.getFiles("input_imgs").get(fileIndex);
				if (file.isEmpty()) {
					break;
				}
				try {
					amazonS3 = new AmazonS3Client(awsCredentials);
					FileOutputStream fos = new FileOutputStream(
							imageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
					InputStream is = file.getInputStream();
					int readCount = 0;
					byte[] buffer = new byte[1024];
					while ((readCount = is.read(buffer)) != -1) {
						fos.write(buffer, 0, readCount);
					}
					is.close();
					fos.close();
					File uploadFile = new File(imageDir + stockAll.getSubNo() + "_" + file.getOriginalFilename());
					if (amazonS3 != null && !"".equals(file.getOriginalFilename())) {
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()
								+ "/outbound/stock/return/" + stockAll.getNno() + "/" + stockAll.getSubNo(),
								uploadFile.getName(), uploadFile);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						amazonS3.putObject(putObjectRequest);
					}
					stockAll.setFileDir("img.mtuai.com/outbound/stock/return/" + stockAll.getNno() + "/"
							+ stockAll.getSubNo() + "/" + uploadFile.getName());
					stockAll.setFileIdx(Integer.toString(fileIdx));

					mapper.insertReturnStockFile(stockAll);
					uploadFile.delete();
					amazonS3 = null;
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		parameterOption.put("status", "D004");
		mapper.updateReturnStatus(parameterOption);
		//webhook D004
		webhookController.returnRequestWebHook(orderReference, "", "");
		return rtnValues;
	}

	@Override
	public void updateOrderRegistInReturn(String targetStatus, String orderReference) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("발송TEST");
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		if(targetStatus.equals("A002")) {
			parameterOption.put("status", "B001");
		}else if (targetStatus.equals("B001")) {
			parameterOption.put("status", "C001");
		}
		parameterOption.put("orderReference", orderReference);
		mapper.updateReturnStatus(parameterOption);
		webhookController.returnRequestWebHook(orderReference, "", "");
	}
	
	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnSuccessInspList(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectReturnSuccessInspList(parameterOption);
		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}
	
	@Override
	public int selectReturnInspSuccessListCnt(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspSuccessListCnt(parameterOption);
	}
	
	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnFailInspList(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectReturnFailInspList(parameterOption);
		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}
	
	@Override
	public int selectReturnInspFailListCnt(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspFailListCnt(parameterOption);
	}
	
	@Override
	public ArrayList<InspStockOutVO> selectStockRackInfo(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnStockRackInfo(parameter);
	}

	@Override
	public ArrayList<InspStockOutVO> selectReturnStockOutTarget(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnStockOutTarget(parameter);
	}

	@Override
	public void deleteReturnStockOut(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteReturnStockOut(parameter);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnInspPassOne(String orderReference) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspPassOne(orderReference);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnInspItem(Object object) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspItem(object);
	}

	@Override
	public String selectReturnTransCode(Object dstnNation, Object sellerId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnTransCode(dstnNation,sellerId);
	}

	@Override
	public String insertOrderInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			
		
			LinkedHashMap<String,Object> selectReturnInspPassOne = new LinkedHashMap<String,Object>();
			String orderReference = request.getParameter("orderReference");
			ArrayList<LinkedHashMap<String,Object>> selectReturnInspPassItem = new ArrayList<LinkedHashMap<String,Object>>();
			
			selectReturnInspPassOne = mapper.selectReturnInspPassOne(orderReference);
			selectReturnInspPassItem = mapper.selectReturnInspItem(selectReturnInspPassOne.get("NNO"));
			String transCode = "";
			String hawbNo = "";
			
			// 선택한 배송업체 값 가져오기
			if (request.getParameter("transCode") != null) {
				transCode = request.getParameter("transCode");
			} else {
				transCode = mapper.selectReturnTransCode(selectReturnInspPassOne.get("DSTN_NATION"),selectReturnInspPassOne.get("SELLER_ID"));
				if(transCode == null) {
					transCode = mapper.selectReturnTransCode("DEF",selectReturnInspPassOne.get("SELLER_ID"));
				}
			}
			
			
			if (request.getParameter("hawbNo") != null) {
				hawbNo = request.getParameter("hawbNo");
			} else {
				hawbNo = "";
			}
			// 선택한 배송업체 값 가져오기
			
			/*
			transCode = mapper.selectReturnTransCode(selectReturnInspPassOne.get("DSTN_NATION"),selectReturnInspPassOne.get("SELLER_ID"));
			if(transCode == null) {
				transCode = mapper.selectReturnTransCode("DEF",selectReturnInspPassOne.get("SELLER_ID"));
			}
			*/
			UserOrderListVO tmpOrderInfo = new UserOrderListVO();
			UserOrderItemVO tmpOrderItem = new UserOrderItemVO();
			mapper.deleteTmpInfo(selectReturnInspPassOne.get("NNO").toString());
			tmpOrderInfo.setNno(selectReturnInspPassOne.get("NNO").toString());
			tmpOrderInfo.setOrgStation("082");
			tmpOrderInfo.setDstnNation(selectReturnInspPassOne.get("DSTN_NATION").toString());
			tmpOrderInfo.setDstnStation(selectReturnInspPassOne.get("DSTN_NATION").toString());
			tmpOrderInfo.setUserId(selectReturnInspPassOne.get("SELLER_ID").toString());
			tmpOrderInfo.setOrderType("RETURN");
			tmpOrderInfo.setOrderNo(selectReturnInspPassOne.get("ORDER_NO").toString());
			tmpOrderInfo.setOrderDate(selectReturnInspPassOne.get("ORDER_DATE").toString());
			tmpOrderInfo.setBoxCnt(request.getParameter("boxCnt"));
			tmpOrderInfo.setUserWta(request.getParameter("wta"));
			
			tmpOrderInfo.setShipperAddr("Seo-gu, Incheon, Republic of Korea");
			tmpOrderInfo.setShipperAddrDetail("40, Bukhang-ro 177beon-gil,");
			tmpOrderInfo.setShipperCity("");
			tmpOrderInfo.setShipperHp("07044366138");
			tmpOrderInfo.setShipperName("ACI WORLDWIDE EXPRESS CORP.");
			tmpOrderInfo.setShipperState("");
			tmpOrderInfo.setShipperTel("07044366138");
			tmpOrderInfo.setShipperZip("22856");
					
			tmpOrderInfo.setCneeName(selectReturnInspPassOne.get("SENDER_NAME").toString());
			tmpOrderInfo.setCneeAddr(selectReturnInspPassOne.get("SENDER_ADDR").toString());
			//tmpOrderInfo.setCneeAddrDetail(selectReturnInspPassOne.get("SENDER_ADDR").toString());
			tmpOrderInfo.setCneeZip(selectReturnInspPassOne.get("SENDER_ZIP").toString());
			//String str = selectReturnInspPassOne.get("SENDER_TEL").toString();
			//String restr = str.replaceAll("[^0-9]","");
			tmpOrderInfo.setCneeTel(selectReturnInspPassOne.get("SENDER_TEL").toString());
			//str = selectReturnInspPassOne.get("SENDER_HP").toString();
			//restr = str.replaceAll("[^0-9]","");
			//tmpOrderInfo.setCneeHp(restr);
			tmpOrderInfo.setCneeState(selectReturnInspPassOne.get("SENDER_STATE").toString());
			tmpOrderInfo.setCneeCity(selectReturnInspPassOne.get("SENDER_CITY").toString());
			tmpOrderInfo.setCneeDistrict(selectReturnInspPassOne.get("SENDER_BUILD_NM").toString());
			tmpOrderInfo.setUserLength(request.getParameterMap().get("length")[0]);
			tmpOrderInfo.setUserWidth(request.getParameterMap().get("width")[0]);
			tmpOrderInfo.setUserHeight(request.getParameterMap().get("height")[0]);
			// 선택한 배송사 값 넣기
			tmpOrderInfo.setTransCode(transCode);
			// 선택한 배송사 값 넣기
			tmpOrderInfo.setWtUnit(request.getParameter("wtUnit"));
			tmpOrderInfo.setDimUnit(request.getParameter("dimUnit"));
			tmpOrderInfo.setTypes("RETURN");
			tmpOrderInfo.setPayment("DDU");
			if(selectReturnInspPassOne.get("SENDER_BUILD_NM").toString().equals("")) {
				tmpOrderInfo.setFood("N");
			}else {
				tmpOrderInfo.setFood("N");
				//tmpOrderInfo.setFood(selectReturnInspPassOne.get("SENDER_BUILD_NM").toString());
			}
			tmpOrderInfo.setWUserId(selectReturnInspPassOne.get("W_USER_ID").toString());
			tmpOrderInfo.setWUserIp(selectReturnInspPassOne.get("W_USER_IP").toString());
			tmpOrderInfo.encryptData();
			System.out.println(tmpOrderInfo);
			System.out.println("*******************");
			mapper.insertTmpReturnOrder(tmpOrderInfo);
			
			
			for(int i =0; i< selectReturnInspPassItem.size(); i++) {
				tmpOrderItem = new UserOrderItemVO();
				tmpOrderItem.setNno(selectReturnInspPassItem.get(i).get("NNO").toString());
				tmpOrderItem.setSubNo(selectReturnInspPassItem.get(i).get("SUB_NO").toString());
				tmpOrderItem.setOrgStation(selectReturnInspPassItem.get(i).get("ORG_STATION").toString());
				tmpOrderItem.setUserId(selectReturnInspPassItem.get(i).get("USER_ID").toString());
				tmpOrderItem.setHsCode(selectReturnInspPassItem.get(i).get("HS_CODE").toString());
				/*
				if (tmpOrderInfo.getDstnNation().toString().equals("JP")) {
					tmpOrderItem.setItemDetail(selectReturnInspPassItem.get(i).get("ITEM_DETAIL_ENG").toString());
				} else {
					tmpOrderItem.setItemDetail(selectReturnInspPassItem.get(i).get("ITEM_DETAIL").toString());	
				}
				*/
				
				tmpOrderItem.setItemDetail(selectReturnInspPassItem.get(i).get("ITEM_DETAIL").toString());	
				//tmpOrderItem.setItemDetail(selectReturnInspPassItem.get(i).get("ITEM_DETAIL").toString());
				tmpOrderItem.setUnitCurrency(selectReturnInspPassItem.get(i).get("UNIT_CURRENCY").toString());
				tmpOrderItem.setItemCnt(selectReturnInspPassItem.get(i).get("ITEM_CNT").toString());
				tmpOrderItem.setBrand(selectReturnInspPassItem.get(i).get("BRAND").toString());
				tmpOrderItem.setUserItemWta(selectReturnInspPassItem.get(i).get("ITEM_WTA").toString());
				tmpOrderItem.setWtUnit(selectReturnInspPassItem.get(i).get("WT_UNIT").toString());
				
				tmpOrderItem.setCusItemCode(selectReturnInspPassItem.get(i).get("CUS_ITEM_CODE").toString());
				tmpOrderItem.setNativeItemDetail(selectReturnInspPassItem.get(i).get("NATIVE_ITEM_DETAIL").toString());
				
				tmpOrderItem.setUnitValue(selectReturnInspPassItem.get(i).get("UNIT_VALUE").toString());
				
				tmpOrderItem.setChgCurrency(selectReturnInspPassItem.get(i).get("UNIT_CURRENCY").toString());
				tmpOrderItem.setMakeCntry(selectReturnInspPassItem.get(i).get("MAKE_CNTRY").toString());
				tmpOrderItem.setMakeCom(selectReturnInspPassItem.get(i).get("MAKE_COM").toString());
				
				tmpOrderItem.setItemUrl(selectReturnInspPassItem.get(i).get("ITEM_URL").toString());
				tmpOrderItem.setItemImgUrl(selectReturnInspPassItem.get(i).get("ITEM_IMG_URL").toString());
				tmpOrderItem.setWUserId(selectReturnInspPassOne.get("W_USER_ID").toString());
				tmpOrderItem.setWUserIp(selectReturnInspPassOne.get("W_USER_IP").toString());
				mapper.insertTmpReturnItem(tmpOrderItem);
				
			}
	
			if (hawbNo.length() < 1) {
				/* Hawb No가 없을 경우 */
				comnService.comnBlApply(selectReturnInspPassOne.get("NNO").toString(),transCode,selectReturnInspPassOne.get("SELLER_ID").toString(),selectReturnInspPassOne.get("W_USER_IP").toString(), "RETURN");
			} else {
				comnBlApply(selectReturnInspPassOne.get("NNO").toString(), transCode, selectReturnInspPassOne.get("SELLER_ID").toString(), selectReturnInspPassOne.get("W_USER_IP").toString(), hawbNo, "RETURN");	
			}
			
			//comnService.comnBlApply(selectReturnInspPassOne.get("NNO").toString(),transCode,selectReturnInspPassOne.get("SELLER_ID").toString(),selectReturnInspPassOne.get("W_USER_IP").toString(), "RETURN");

			int count = mapper.selectTempInfo(selectReturnInspPassOne.get("NNO").toString());
			
			if(count == 0) {
				return transCode;
			}else {
				return "F";
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			return "F";
		}
		
	}

	private void comnBlApply(String orderNno, String transCode, String userId, String userIp, String hawbNo, String blType) throws Exception {

		switch (transCode) {
		case "ACI":
			comnService.selectBlApply(orderNno, userId, userIp);
			comnService.savePdf(orderNno, transCode, userId, userIp);
			break;
		case "GTS":
			//gtsAPI.selectBlApplyGts(request, orderNno,(String)request.getSession().getAttribute("USER_ID"),request.getRemoteAddr());
			comnService.selectBlApply(orderNno,userId, userIp);
			comnService.savePdf(orderNno, transCode, userId, userIp);
			break;
		case "CSE":
			comnService.selectBlApply(orderNno,userId, userIp);
			comnService.savePdf(orderNno, transCode, userId, userIp);
			break;
		case "ARA":
			usrService.selectBlApplyARA(orderNno,userId, userIp);
			break;
		case "YSL":
			ysApi.selectBlApplyYSL(orderNno,userId, userIp, "Nomal");
			comnService.yslPdf(orderNno, transCode, userId, userIp, blType);
			//usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "EFS":
			efsApi.selectBlApplyEFS(orderNno,userId, userIp);
			comnService.efsPdf(orderNno, transCode, userId, userIp);
			//usrService.selectBlApply(orderNno[roop],member.getUsername(),request.getRemoteAddr());
			break;
		case "FED":
			fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FES");
			break;
		case "FES":
			fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FES");
			break;
		case "FEG":
			fedexApi.selectBlApplyFED(orderNno,userId, userIp,"FEG");
			break;
		case "OCS":
			ocsApi.selectBlApplyOCS(orderNno,userId, userIp);
			comnService.ocsPdf(orderNno, transCode,userId, userIp);
			break;
		case "EPT":
			ocsApi.selectBlApplyOCS(orderNno,userId, userIp);
			comnService.krPostPdf(orderNno, transCode, userId, userIp);
			break;
		case "EMS":
			emsApi.selectBlApply_FakeBl(orderNno,userId, userIp, "Nomal");
			//krPostPdf(request, orderNno, transCode);
			break;
		case "EMN":
			selectBlApply_Nomal(orderNno,userId, userIp, hawbNo, "Nomal");
			//krPostPdf(request, orderNno, transCode);
			break;
		case "CJ":
			comnService.selectBlApply(orderNno,userId, userIp);
			break;
		case "ITC":
			itcApi.selectBlApplyITC(orderNno, userId, userIp);
			break;
		case "SEK":
			sekoApi.selectBlApplySEK(orderNno, userId, userIp);
			break;
		default:
			comnService.selectBlApply(orderNno,userId, userIp);
			comnService.savePdf(orderNno, transCode, userId, userIp);
			break;
	}
		
	}
	
	public BlApplyVO selectBlApply_Nomal(String orderNno, String userId, String userIp, String hawbNo, String inputType) throws Exception {
		// TODO Auto-generated method stub
		TestssVO parameters = new TestssVO();
		parameters.setUserId(userId);
		parameters.setUserIp(userIp);
		BlApplyVO tmp = new BlApplyVO();
		parameters.setNno(orderNno);
		parameters.setHawbNo(hawbNo);
		
		tmp = mapper.selectBlApply(parameters);
		
		System.out.println("select BL Apply ----------->");
		System.out.println(tmp.getHawbNo());
		System.out.println(tmp.getRstCode());
		System.out.println(tmp.getRstMsg());
		System.out.println(tmp.getStatus());
		
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		
		try {
			resultShopify = createShipment(orderNno, hawbNo, inputType);
			if(resultShopify.getStatus().equals("OK")) {
				comnService.updateHawbNoInTbOrderList(resultShopify.getHawbNo(), orderNno);
				comnService.updateShipperReference(resultShopify.getShipperReference(), orderNno);
			}else {
				throw new Exception();				
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception", e);
			comnService.deleteHawbNoInTbHawb(orderNno);
			if(resultShopify.getErrorMsg().equals("")) {
				comnService.insertTMPFromTB(orderNno, e.getMessage(), userId, userIp);
			}else {
				comnService.insertTMPFromTB(orderNno, resultShopify.getErrorMsg(), userId, userIp);
			}
			
		}
		
		return tmp;
	}
	
	public ApiShopifyResultVO createShipment(String nno, String hawbNo, String type) throws Exception{
		ApiShopifyResultVO resultShopify = new ApiShopifyResultVO();
		String parameter = "";
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		parameter = emsApi.makeEmsParameter(nno);
		
		//URL url = new URL("http://eship.epost.go.kr/api.EmsApplyInsertReceiveTempCmdNew.ems?"+"key=ae17dd59a93f47fb41641258087370"+"&regData="+parameter);
		URL url = new URL("http://eship.epost.go.kr/api.EmsApplyInsertReceiveTempCmdNewDEV.ems?"+"key=ae17dd59a93f47fb41641258087370"+"&regData="+parameter);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);

		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/plain");
		
		int responseCode = connection.getResponseCode();

//		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//		while((inputLine = in.readLine())!=null) {
//			outResult.append(inputLine);
//		}
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		InputStream inputStream = connection.getInputStream();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		NodeList errNode = doc.getElementsByTagName("error_code");
		Node errChk = errNode.item(0);
		if(errChk == null) {
			resultShopify.setStatus("OK");
			resultShopify.setHawbNo(hawbNo);
			resultShopify.setShipperReference(doc.getElementsByTagName("reqno").item(0).getTextContent().trim());
			resultShopify.setTreatporegipocd(doc.getElementsByTagName("treatporegipocd").item(0).getTextContent().trim());
			if(doc.getElementsByTagName("exchgPoCd").item(0)==null) {
				resultShopify.setExchgPoCd("");
			}else {
				resultShopify.setExchgPoCd(doc.getElementsByTagName("exchgPoCd").item(0).getTextContent().trim());
			}
			resultShopify.setPrerecevprc(doc.getElementsByTagName("prerecevprc").item(0).getTextContent().trim());
			
			emsApi.createEmsPdf(resultShopify, nno);
		}else {
			resultShopify.setStatus("FAIL");
			resultShopify.setErrorMsg(doc.getElementsByTagName("message").item(0).getTextContent().trim());//error 메시지.
		}
		
		return resultShopify;
	}

	@Override
	public void updateReturnStatus(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateReturnStatus(parameterOption);
	}

	@Override
	public void cancleStock(String orderReference) throws Exception {
		// TODO Auto-generated method stub
		String nno = mapper.selectNnoByOrderReference(orderReference);
		mapper.cancleStock(nno);
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		parameterOption.put("status", "C001");
		parameterOption.put("orderReference", orderReference);
		mapper.updateReturnStatus(parameterOption);
		
	}

	@Override
	public void insertDepositHisRequest(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		//검수 비용 적용 1차 기본비용 2차 추가비용
		HashMap<String, Object> depositInfo = new HashMap<String, Object>();

		depositInfo.put("userId", request.getParameter("userId"));
		Double costNow = mapper.selectDepositCostNow(depositInfo);
		Double inspPrice = mapper.selectDepositInspPrice(depositInfo);
		
		Double balPrice = costNow - inspPrice;
		
		depositInfo.put("inspPrice", inspPrice);
		depositInfo.put("balPrice", balPrice);
		depositInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
		depositInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		depositInfo.put("orderReference", request.getParameter("orderReference"));
		depositInfo.put("calculation", "B");
		depositInfo.put("code", "B001");
		
		mapper.insertDepositHisRequest(depositInfo);

	}
	
	@Override
	public void insertDepositDelHis(HttpServletRequest request) throws Exception {
		HashMap<String, Object> stockInfo = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> Rst = new HashMap<String, Object>();
		stockInfo = mapper.selectStockInfo(request.getParameter("orderReference"));
		
		parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", request.getParameter("userId"));
		parameters.put("groupIdx", stockInfo.get("groupIdx"));
		parameters.put("trkCom", stockInfo.get("reTrkCom"));
		parameters.put("trkNo", stockInfo.get("reTrkNo"));
		parameters.put("trkDate", request.getParameter("trkDate"));
		parameters.put("chgAmt", request.getParameter("chgAmt"));
		parameters.put("disposalType", "TR");
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		
		Rst = mapper.spStockDisposal(parameters);
		/*
		HashMap<String, Object> depositInfo = new HashMap<String, Object>();
		depositInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
		depositInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		depositInfo.put("orderReference", request.getParameter("orderReference"));
		depositInfo.put("userId", request.getParameter("userId"));
		
		Double balNow = mapper.selectDepositCostNow(depositInfo);
		Double balPrice = balNow - Double.parseDouble(request.getParameter("chgAmt"));
		
		depositInfo.put("calculation", "B");
		depositInfo.put("code", "B003");
		depositInfo.put("cost", request.getParameter("chgAmt"));
		depositInfo.put("balPrice", balPrice);
		mapper.insertDepositHisRequest(depositInfo);
		*/
		HashMap<String, Object> parameterOption = new HashMap<String, Object>();
		parameterOption.put("orderReference", request.getParameter("orderReference"));
		parameterOption.put("status", "D005");
		parameterOption.put("d005Date", request.getParameter("trkDate"));
		mapper.updateReturnStatus(parameterOption);
		webhookController.returnRequestWebHook(request.getParameter("orderReference"), "", "");
		
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnInspDelOne(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>(); 
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderInfo = mapper.selectReturnInspDelOne(parameterOption);
		orderItem = mapper.selectReturnInspItem(orderInfo.get("NNO"));
		double itemPrice = 0;
		for(int j=0;j<orderItem.size();j++) {
			itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
		}
		orderInfo.put("TOTAL_VALUE", itemPrice);
		orderInfo.put("orderItemList", orderItem);
		return orderInfo ;
	}
	
	@Override
	public void insertDepositHis(HashMap<String, Object> depositInfo) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertDepositHis(depositInfo);
	}
	
	@Override
	public ArrayList<HashMap<String,Object>> selectDepositUserList(HashMap<String, Object> infoMap) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDepositUserList(infoMap);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectDepositUserHis(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDepositUserHis(userId);
	}
	
	@Override
	public HashMap<String, Object> selectDepositUserOne(String userId) throws Exception{
		return mapper.selectDepositUserOne(userId);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnInspAddOne(HashMap<String, Object> parameterOption)
			throws Exception {
		LinkedHashMap<String, Object> orderInfo = new LinkedHashMap<String, Object>(); 
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderInfo = mapper.selectReturnInspAddOne(parameterOption); 
		orderItem = mapper.selectReturnInspItem(orderInfo.get("NNO"));
		double itemPrice = 0;
		for(int j=0;j<orderItem.size();j++) {
			itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
		}
		orderInfo.put("TOTAL_VALUE", itemPrice);
		orderInfo.put("orderItemList", orderItem);
		return orderInfo ;
	}
	
	@Override
	public void insertDepositAddHis(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> depositInfo = new HashMap<String, Object>();
		
		depositInfo.put("userId", request.getParameter("userId"));
		
		Double costNow = mapper.selectDepositCostNow(depositInfo);
		Double balCost = costNow - Double.parseDouble(request.getParameter("chgAmt"));
		
		depositInfo.put("balPrice", balCost);
		depositInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
		depositInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
		depositInfo.put("orderReference", request.getParameter("orderReference"));
		depositInfo.put("calculation", "B");
		depositInfo.put("code", request.getParameter("costType"));
		if(request.getParameter("costType").equals("B007")) {
			depositInfo.put("remark", request.getParameter("etcDetail"));
		}else {
			depositInfo.put("remark", "");
		}
		depositInfo.put("cost", request.getParameter("chgAmt"));
		mapper.insertDepositHisRequest(depositInfo);
	}

	@Override
	public int selectReturnOptionCnt(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectReturnOptionCnt(parameterOption);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnOption(HashMap<String, Object> parameterOption)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectReturnOption(parameterOption);
		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}

	@Override
	public int selectReturnRequestCnt(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequestCnt(map);
	}

	@Override
	public ArrayList<ReturnListVO> selectReturnRequest(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequest(map);
	}
	
	public static String convertTelNo(String arg) {
		
		String telNo = arg;
		
		if (telNo != null) {
			telNo = telNo.replaceAll(Pattern.quote("-"), "");
			
			if (telNo.length() == 11) {
				telNo = telNo.substring(0,3) + "-" + telNo.substring(3,7) + "-" + telNo.substring(7);
			} else if (telNo.length() == 8) {
				telNo = telNo.substring(0, 4) + "-" + telNo.substring(4);
			} else {
				if (telNo.startsWith("02")) {
					if (telNo.length() == 10) {
						telNo = telNo.substring(0,2) + "-" + telNo.substring(2,6) + "-" + telNo.substring(6);
					} else {
						telNo = telNo.substring(0,2) + "-" + telNo.substring(2,5) + "-" + telNo.substring(5);
					}
				} else {
					telNo = telNo.substring(0,3) + "-" + telNo.substring(3,6) + "-" + telNo.substring(6);
				}
			}
		}
		
		return telNo;
		
	}

	@Override
	public void deleteReturnOrder(HashMap<String, Object> params) {
		mapper.deleteReturnOrder(params);
	}

	@Override
	public int selectTaxReturnRequestCnt(HashMap<String, Object> map) {
		return mapper.selectTaxReturnRequestCnt(map);
	}

	@Override
	public ArrayList<ReturnListVO> selectTaxReturnRequest(HashMap<String, Object> map) {
		return mapper.selectTaxReturnRequest(map);
	}

	@Override
	public int selectB001ReturnListCnt(HashMap<String, Object> parameterOption) {
		return mapper.selectB001ReturnListCnt(parameterOption);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectB001ReturnList(HashMap<String, Object> parameterOption) throws Exception {
		
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectB001ReturnList(parameterOption);
		ArrayList<ReturnRequestVO> inspList = new ArrayList<ReturnRequestVO>();

		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());
			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}

	@Override
	public String selectReturnNno(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnNno(params);
	}

	@Override
	public void updateReturnStockIn(String nno) {
		// TODO Auto-generated method stub
		mapper.updateReturnStockIn(nno);
	}

	@Override
	public ArrayList<UserTransComVO> selectReturnInspTransList(String dstnNation) {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspTransList(dstnNation);
	}

	@Override
	public LinkedHashMap<String, Object> selectReturnOrderInfo(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnOrderInfo(params);
	}

	@Override
	public OrderInspVO selectReturnOrderStock(HashMap<String, Object> params) {
		return mapper.selectReturnOrderStock(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnOrderInfo2(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnOrderInfo2(params);
	}

	@Override
	public ArrayList<OrderInspVO> selectReturnInspOrderItemList(HashMap<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspOrderItemList(parameter);
	}

	@Override
	public ArrayList<ReturnListVO> selectReturnRequest2(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequest2(map);
	}

	@Override
	public int selectReturnRequestCnt2(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnRequestCnt2(map);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectDepositInfo(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectDepositInfo(params);
	}

	@Override
	public HashMap<String, Object> selectDepositTotal(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectDepositTotal(userId);
	}

	@Override
	public int selectDepositInfoCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectDepositInfoCnt(params);
	}

	@Override
	public Double selectDepositCostNow(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectDepositCostNow(params);
	}

	@Override
	public void insertDepositAdd(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		mapper.insertDepositAdd(params);
	}

	@Override
	public int selectReturnCSListCnt(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnCSListCnt(map);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnCSList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReturnCSList(map);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnStation(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnStation(params);
	}

	@Override
	public int selectReturnJapanSuccessListCnt(HashMap<String, Object> parameterOption) {
		// TODO Auto-generated method stub
		return mapper.selectReturnJapanSuccessListCnt(parameterOption);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> selectReturnJapanSuccessList(HashMap<String, Object> parameterOption) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<LinkedHashMap<String, Object>> orderList = new ArrayList<LinkedHashMap<String, Object>> ();
		ArrayList<LinkedHashMap<String, Object>> orderItem = new ArrayList<LinkedHashMap<String, Object>>();
		orderList = mapper.selectReturnJapanSuccessList(parameterOption);
		for(int i = 0; i < orderList.size();i++) {
			double itemPrice = 0;
			String yslCode  = "";
			orderItem = mapper.selectReturnInspItem(orderList.get(i).get("NNO"));
			for(int j=0;j<orderItem.size();j++) {
				itemPrice += Double.parseDouble(orderItem.get(j).get("UNIT_VALUE").toString()) * Integer.parseInt(orderItem.get(j).get("ITEM_CNT").toString());


			}
			orderList.get(i).put("TOTAL_VALUE", itemPrice);
			orderList.get(i).put("orderItemList", orderItem);
			
		}
		
		return orderList;
	}

	@Override
	public ArrayList<UserOrderItemVO> selectReturnItemOne(String nno) {
		return mapper.selectReturnItemOne(nno);
	}

	@Override
	public String selectErrorMessage(String nno) {
		return mapper.selectErrorMessage(nno);
	}

	@Override
	public ReturnRequestVO selectTaxReturnInfo(String nno) throws Exception {
		return mapper.selectTaxReturnInfo(nno);
	}

	@Override
	public int selectOrderInspcCnt(HashMap<String, Object> orderInfo) throws Exception {
		return mapper.selectOrderInspcCnt(orderInfo);
	}

	@Override
	public ArrayList<ReturnRequestVO> selectReturnOrderInspList(ReturnRequestVO parameters) throws Exception {
		return mapper.selectReturnOrderInspList(parameters);
	}

	@Override
	public ReturnRequestVO selectReturnInspOrder(String orderReference) throws Exception {
		return mapper.selectReturnInspOrder(orderReference);
	}

	@Override
	public ArrayList<ReturnRequestItemVO> selectReturnInspOrderItem(String nno) throws Exception {
		return mapper.selectReturnInspOrderItem(nno);
	}

	@Override
	public String selectReturnOrderNNO(String orderReference) throws Exception {
		return mapper.selectReturnOrderNNO(orderReference);
	}

	@Override
	public int selectStockTotalCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectStockTotalCnt(parameterInfo);
	}

	@Override
	public ArrayList<GroupStockVO> selectReturnInspOrderInfo(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectReturnInspOrderInfo(parameterInfo);
	}

	@Override
	public ReturnRequestVO selectReturnInfo(String nno) throws Exception {
		return mapper.selectReturnInfo(nno);
	}

	@Override
	public ArrayList<String> selectLoadFileImage(String groupIdx) throws Exception {
		return mapper.selectLoadFileImage(groupIdx);
	}

	@Override
	public HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters) throws Exception {
		return mapper.spStockCancle(parameters);
	}

	@Override
	public void updateStockCancel(String nno) throws Exception {
		mapper.updateStockCancel(nno);
	}

	@Override
	public ReturnRequestVO selectReturnRequestInfo(HashMap<String, Object> params) throws Exception {
		return mapper.selectReturnRequestInfo(params);
	}

	@Override
	public int selectTrashReturnListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectTrashReturnListCnt(params);
	}

	@Override
	public ArrayList<ReturnRequestVO> selectTrashReturnList(HashMap<String, Object> params) throws Exception {
		return mapper.selectTrashReturnList(params);
	}

	@Override
	public ReturnRequestVO selectReturnRequestApply(HashMap<String, Object> params) throws Exception {
		return mapper.selectReturnRequestApply(params);
	}

	@Override
	public ReturnRequestVO selectReturnInfoForStockOut(HashMap<String, Object> params) throws Exception {
		return mapper.selectReturnInfoForStockOut(params);
	}

	@Override
	public String selectReturnStatus(HashMap<String, Object> parameterOption) {
		return mapper.selectReturnStatus(parameterOption);
	}


}