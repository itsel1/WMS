package com.example.temp.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.common.service.impl.ComnServiceImpl;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.mapper.ManagerReturnOrderMapper;
import com.example.temp.manager.service.ManagerReturnOrderService;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.StockResultVO;
import com.example.temp.manager.vo.StockVO;
import com.example.temp.member.mapper.MemberReturnOrderMapper;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.spire.xls.packages.spra;
import com.spire.xls.packages.spraa;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import com.example.temp.member.vo.UserOrderItemVO;

@Service
@Transactional
public class ManagerReturnOrderServiceImpl implements ManagerReturnOrderService {

	@Value("${filePath}")
	String realFilePath;
	
	@Autowired
	ManagerReturnOrderMapper mapper;
	
	@Autowired
	MemberReturnOrderMapper memberReturnOrderMapper;
	
	@Autowired
	ManagerServiceImpl mgrServiceImpl;
	
	@Autowired
	ComnServiceImpl comnServiceImpl;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	@Override
	public ArrayList<HashMap<String, Object>> selectReturnStateList() {
		return mapper.selectReturnStateList();
	}

	@Override
	public int selectReturnOrderListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderList(parameters);
	}

	@Override
	public void returnOrderEpostExcelDown(HttpServletRequest request, HttpServletResponse response) {

		try {
			ArrayList<ReturnOrderListVO> orderList = new ArrayList<ReturnOrderListVO>();
			orderList = mapper.selectReturnOrderEpostList();
			
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
	         for (int i = 0; i < orderList.size(); i++) {
	        	 orderList.get(i).dncryptData();
	            
	            row = sheet.createRow(rowNo++);
	            cell = row.createCell(0);
	            cell.setCellStyle(cellStyleBody);
	            cell.setCellValue(i+1);
	            
	            cell = row.createCell(1);
	            cell.setCellValue(orderList.get(i).getShipperName());
	            
	            cell = row.createCell(2);
	            cell.setCellValue(convertTelNo(orderList.get(i).getShipperTel()));
	            //cell.setCellValue(testList.get(i).getPickupTel());
	            
	            if (orderList.get(i).getShipperHp().equals("")) {
	            	orderList.get(i).setShipperHp(orderList.get(i).getShipperTel());
	            }
	            cell = row.createCell(3);
	            cell.setCellValue(convertTelNo(orderList.get(i).getShipperHp()));
	            
	            cell = row.createCell(4);
	            cell.setCellValue(orderList.get(i).getShipperZip());
	            
	            cell = row.createCell(5);
	            cell.setCellValue(orderList.get(i).getShipperAddr());
	            
	            cell = row.createCell(6);
	            cell.setCellValue(orderList.get(i).getShipperAddrDetail());
	            
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
	            cell.setCellValue(orderList.get(i).getNno());
	            
	            cell = row.createCell(16);
	            cell.setCellValue("기타");
	            
	            cell = row.createCell(17);
	            cell.setCellValue("");
	            
	            cell = row.createCell(18);
	            cell.setCellValue(orderList.get(i).getItemDetail());
	            
	            cell = row.createCell(19);
	            //cell.setCellValue(String.valueOf(testList.get(i).getItemWta()));
	            cell.setCellValue("2000");
	            
	            cell = row.createCell(20);
	            //cell.setCellValue("");
	            cell.setCellValue("60");
	            
	            cell = row.createCell(21);
	            cell.setCellValue(String.valueOf(orderList.get(i).getItemCnt()));
	            
	            cell = row.createCell(22);
	            cell.setCellValue("N");
	            
	            cell = row.createCell(23);
	            cell.setCellValue("");
	            
	            cell = row.createCell(24);
	            cell.setCellValue("");
	            
	            cell = row.createCell(25);
	            cell.setCellValue("N");

	         }
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);

			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void reeturnOrderEpostExcelUp(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multi) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Map<String, String> resMap = new HashMap<String, String>();
		String excelRoot = realFilePath + "exel/";
		int currentRow = 0;
		
		try {
		
			resMap = epostFilesUpload(multi, excelRoot);
			String filePath = "";
			
			for (String key : resMap.keySet()) {
				filePath = resMap.get(key);
				
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook wb = new XSSFWorkbook(fis);
					XSSFSheet sheet = wb.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						ReturnOrderListVO orderInfo = new ReturnOrderListVO();
						orderInfo.setState("B001");
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
									orderInfo.setTrkNo(value);
									break;
								case 4:
									orderInfo.setNno(value);
									break;
								}
							}
							if (orderInfo.getNno() != null) {
								if (!orderInfo.getNno().equals("")) {
									mapper.updateEpostInfo(orderInfo);
									memberReturnOrderMapper.insertReturnOrderStateLog(orderInfo);
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
						ReturnOrderListVO orderInfo = new ReturnOrderListVO();
						orderInfo.setState("B001");
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
									orderInfo.setTrkNo(value);
									break;
								case 4:
									orderInfo.setNno(value);
									break;
								}
							}
							if (orderInfo.getNno() != null) {
								if (!orderInfo.getNno().equals("")) {
									mapper.updateEpostInfo(orderInfo);
									memberReturnOrderMapper.insertReturnOrderStateLog(orderInfo);
								}
							}
						}
					}
				}
				File delFile = new File(filePath);
				if (delFile.exists()) {
					delFile.delete();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private Map<String, String> epostFilesUpload(MultipartHttpServletRequest multi, String excelRoot) {
		String path = excelRoot; // 저장 경로 설정

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
				e.printStackTrace();
			}
		}
		return resMap;
	}

	@Override
	public int selectReturnOrderCntForWhWork(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderCntForWhWork(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderListForWhWork(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderListForWhWork(parameters);
	}

	@Override
	public HashMap<String, Object> selectReturnOrderCheck(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderCheck(parameters);
	}

	@Override
	public ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderInfo(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnItemInfo(HashMap<String, Object> parameters) {
		return mapper.selectReturnItemInfo(parameters);
	}

	@Transactional
	@Override
	public void execWhInProcess(MultipartHttpServletRequest request) {
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> stockInfos = new ArrayList<HashMap<String, Object>>();
		
		Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String whInDate = formatter.format(now);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String etcDate = formatter2.format(now);
        
		try {
			
			String nno = request.getParameter("nno");
			List<MultipartFile> fileList = request.getFiles("fileList");
			List<String> subNoList = Arrays.asList(request.getParameterValues("subNoList"));
			List<String> whInCntList = Arrays.asList(request.getParameterValues("whInCntList"));
			List<String> whMemoList = Arrays.asList(request.getParameterValues("whMemoList"));
			parameters.put("nno", nno);
			parameters.put("whRst", request.getParameter("whInResult"));
			parameters.put("rackCode", request.getParameter("rackCode"));

			float width = 0;
			float height = 0;
			float length = 0;
			float wta = 0;
			float size = 0;
			
			if (request.getParameter("userWidth") != null) {
				System.out.println(request.getParameter("userWidth"));
				width = Float.parseFloat(request.getParameter("userWidth"));
				size += width;
			}
			
			if (request.getParameter("userHeight") != null) {
				height = Float.parseFloat(request.getParameter("userHeight"));
				size += height;
			}
			
			if (request.getParameter("userLength") != null) {
				length = Float.parseFloat(request.getParameter("userLength"));
				size += length;
			}
			
			if (request.getParameter("wta") != null) {
				wta = Float.parseFloat(request.getParameter("wta"));
			}
			
			parameters.put("wta", wta);
			parameters.put("userWidth", width);
			parameters.put("userHeight", height);
			parameters.put("userLength", length);
			
			float etcValue = 0;
			parameters.put("etcValue", etcValue);
			parameters.put("etcDate", etcDate);
			parameters.put("etcType", "EPOST_RETURN");
			parameters.put("etcCurrency", "KRW");
			parameters.put("etcRemark", "");
			parameters.put("hawbNo", "");
			
			float wtc = 0;
			int per = Integer.parseInt(request.getParameter("per"));
			wtc = width * height * length / per;
			
			parameters.put("per", per);
			parameters.put("wtc", wtc);
			parameters.put("wtUnit", request.getParameter("wtUnit"));
			parameters.put("dimUnit", request.getParameter("dimUnit"));
			parameters.put("orgStation", (String) request.getSession().getAttribute("ORG_STATION"));
			parameters.put("userId", request.getParameter("userId"));
			parameters.put("whInDate", whInDate);
			parameters.put("trkCom", request.getParameter("trkCom"));
			parameters.put("trkNo", request.getParameter("trkNo"));
			parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
			parameters.put("wUserIp", request.getRemoteAddr());
			
			String groupIdx = mapper.selectGroupIdx();
			
			parameters.put("groupIdx", groupIdx);
			
			if (fileList.size() > 0) {
				filesUpload(fileList, parameters);
			}
			
			for (int i = 0; i < subNoList.size(); i++) {
				int whInCnt = Integer.parseInt(whInCntList.get(i));
				for (int j = 0; j < whInCnt; j++) {
					HashMap<String, Object> stockInfo = new HashMap<String, Object>();
					
					stockInfo = mapper.selectStockInfo();
					parameters.put("subNo", subNoList.get(i));
					parameters.put("stockNo", stockInfo.get("stockNo").toString());
					parameters.put("whMemo", whMemoList.get(i));
					stockInfos.add(stockInfo);
					
					mapper.insertStockInfo(parameters);
				}
			}
			
			ReturnOrderListVO orderVO = new ReturnOrderListVO();
			orderVO.setNno(nno);
			
			if (parameters.get("whRst").toString().equals("WO")) {
				parameters.put("whStatus", "");
				parameters.put("whStatusDetail", "");
			} else if (parameters.get("whRst").toString().equals("WF")) {
				parameters.put("whStatus", request.getParameter("whStatus"));
				parameters.put("whStatusDetail", request.getParameter("whStatusDetail"));
			}
			
			orderVO.setState("C002");
			updateReturnOrderStateInfo(orderVO);
			memberReturnOrderMapper.insertReturnOrderStateLog(orderVO);
			mapper.updateReturnOrderWhStatus(parameters);
			//mapper.insertEtcValue(parameters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void filesUpload(List<MultipartFile> fileList, HashMap<String, Object> parameters) {
		String nno = parameters.get("nno").toString();
		Map<String, String> resMap = new HashMap<String, String>();
		String filePath = realFilePath + "image/stock/" + nno + "/";
		File dir = new File(filePath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		
		String fileName = "";
		
		try {
			for (MultipartFile file : fileList) {
				String amazonPath = "";
		        fileName = file.getOriginalFilename();
		        file.transferTo(new File(filePath+fileName));
		        File upFile = new File(filePath+fileName);

				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult asssd = new PutObjectResult();
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
				if(amazonS3 != null) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/inspFile/"+nno, fileName, upFile);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					asssd = amazonS3.putObject(putObjectRequest);
					amazonPath = "img.mtuai.com/outbound/inspFile/"+nno+"/"+fileName;
				}
				parameters.put("filePath", amazonPath);
				mapper.insertInspFile(parameters);
				amazonS3 = null;
				upFile.delete();
	        }	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectStockInfoList(HashMap<String, Object> parameters) {
		return mapper.selectStockInfoList(parameters);
	}

	@Override
	public void updateReturnOrderStateInfo(ReturnOrderListVO orderVO) {
		try {
			mapper.updateReturnOrderStateInfo(orderVO);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public HashMap<String, Object> execSpWhoutStockReturnIn(HashMap<String, Object> parameters) {
		return mapper.execSpWhoutStockReturnIn(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectStockOutList(HashMap<String, Object> parameters) {
		return mapper.selectStockOutList(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectStockUnCheckInfoList(HashMap<String, Object> parameters) {
		return mapper.selectStockUnCheckInfoList(parameters);
	}

	@Override
	public int selectSubNoByStockNo(HashMap<String, Object> parameters) {
		return mapper.selectSubNoByStockNo(parameters);
	}

	@Override
	public void deleteStockOutList(HashMap<String, Object> parameters) {
		mapper.deleteStockOutList(parameters);
	}

	@Transactional
	@Override
	public HashMap<String, Object> execWhoutProc(HttpServletRequest request) {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		HashMap<String, Object> transParams = new HashMap<String, Object>();
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date date = new Date();
		String dateTime = format.format(date); 
		try {
		
			String nno = request.getParameter("nno");
			String transCode = request.getParameter("transCode");
			String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
			String wUserId = (String) request.getSession().getAttribute("USER_ID");
			String wUserIp = request.getRemoteAddr();
			float width = Float.parseFloat(request.getParameter("width"));
			float length = Float.parseFloat(request.getParameter("length"));
			float height = Float.parseFloat(request.getParameter("height"));
			float per = Float.parseFloat(request.getParameter("per"));
			float wta = Float.parseFloat(request.getParameter("wta"));
			float wtc = Float.parseFloat(request.getParameter("wtc"));
			String wtUnit = request.getParameter("wtUnit");
			String dimUnit = request.getParameter("dimUnit");
			int boxCnt = Integer.parseInt(request.getParameter("boxCnt"));

			parameters.put("nno", nno);
			parameters.put("wUserId", wUserId);
			parameters.put("wUserIp", wUserIp);
			parameters.put("wta", wta);
			parameters.put("wtc", wtc);
			parameters.put("width", width);
			parameters.put("length", length);
			parameters.put("height", height);
			
			ReturnOrderListVO returnOrderInfo = new ReturnOrderListVO();
			returnOrderInfo = mapper.selectReturnOrderInfo(parameters);
			returnOrderInfo.dncryptData();
			
			String dstnNation = returnOrderInfo.getDstnNation();
			
			parameters.put("userId", returnOrderInfo.getUserId());
			parameters.put("orgStation", returnOrderInfo.getOrgStation());
			parameters.put("dstnNation", dstnNation);
			parameters.put("dstnStation", returnOrderInfo.getDstnStation());
			
			optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCode, dstnNation);
			optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCode, dstnNation);
			expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCode, dstnNation);
			expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCode, dstnNation);

			String status = "";
			UserOrderListVO userOrderInfo = new UserOrderListVO();
			userOrderInfo.setNno(nno);
			userOrderInfo.setWDate(dateTime);
			userOrderInfo.setWUserId(wUserId);
			userOrderInfo.setWUserIp(wUserIp);
			userOrderInfo.setUserId(returnOrderInfo.getUserId());
			userOrderInfo.setOrgStation(returnOrderInfo.getOrgStation());
			userOrderInfo.setDstnNation(dstnNation);
			userOrderInfo.setDstnStation(returnOrderInfo.getDstnStation());
			userOrderInfo.setOrderType("RETURN");
			userOrderInfo.setOrderNo(returnOrderInfo.getOrderNo());
			userOrderInfo.setOrderDate(returnOrderInfo.getOrderDate());
			userOrderInfo.setBoxCnt(String.valueOf(boxCnt));
			userOrderInfo.setUserWta(String.format("%.2f", wta));
			userOrderInfo.setUserWtc(String.format("%.2f", wtc));
			userOrderInfo.setShipperName(returnOrderInfo.getShipperName());
			userOrderInfo.setShipperZip(returnOrderInfo.getShipperZip());
			userOrderInfo.setShipperTel(returnOrderInfo.getShipperTel());
			userOrderInfo.setShipperHp(returnOrderInfo.getShipperHp());
			if (userOrderInfo.getShipperHp().equals("") && !optionOrderVO.getShipperHpYn().isEmpty()) {
				status += "TMPREQ06,";
			}
			userOrderInfo.setShipperCntry("");
			userOrderInfo.setShipperCity(returnOrderInfo.getShipperCity());
			if (userOrderInfo.getShipperCity().equals("") && !optionOrderVO.getShipperCityYn().isEmpty()) {
				status += "TMPREQ09,";
			}
			userOrderInfo.setShipperState(returnOrderInfo.getShipperState());
			if (userOrderInfo.getShipperState().equals("") && !optionOrderVO.getShipperStateYn().isEmpty()) {
				status += "TMPREQ10,";
			}
			userOrderInfo.setShipperAddr(returnOrderInfo.getShipperAddr());
			userOrderInfo.setShipperAddrDetail(returnOrderInfo.getShipperAddrDetail());
			if (userOrderInfo.getShipperAddrDetail().equals("") && !optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
				status += "TMPREQ12,";
			}
			userOrderInfo.setCneeName(returnOrderInfo.getCneeName());
			userOrderInfo.setCneeAddr(returnOrderInfo.getCneeAddr());
			userOrderInfo.setCneeZip(returnOrderInfo.getCneeZip());
			userOrderInfo.setCneeTel(returnOrderInfo.getCneeTel());
			userOrderInfo.setCneeHp(returnOrderInfo.getCneeHp());
			if (userOrderInfo.getCneeHp().equals("") && !optionOrderVO.getCneeHpYn().isEmpty()) {
				status += "TMPREQ16,";
			}
			userOrderInfo.setCneeCntry("");
			userOrderInfo.setCneeCity(returnOrderInfo.getCneeCity());
			userOrderInfo.setCneeState(returnOrderInfo.getCneeState());
			userOrderInfo.setCneeAddrDetail(returnOrderInfo.getCneeAddrDetail());
			if (userOrderInfo.getCneeAddrDetail().equals("") && !optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
				status += "TMPREQ22,";
			}
			userOrderInfo.setUserLength(String.valueOf(length));
			userOrderInfo.setUserWidth(String.valueOf(width));
			userOrderInfo.setUserHeight(String.valueOf(height));
			userOrderInfo.setTransCode(transCode);
			userOrderInfo.setDimUnit(dimUnit);
			userOrderInfo.setWtUnit(wtUnit);
			userOrderInfo.setStatus(status);
			userOrderInfo.setUserEmail("");
			userOrderInfo.setCneeEmail("");
			userOrderInfo.setCustomsNo("");
			userOrderInfo.setNativeCneeName("");
			userOrderInfo.setNativeCneeAddr("");
			userOrderInfo.setNativeCneeAddrDetail("");
			userOrderInfo.setGetBuy("");
			userOrderInfo.setBuySite("");
			userOrderInfo.setMallType("");
			userOrderInfo.setWhReqMsg("");
			userOrderInfo.setDlvReqMsg("");
			userOrderInfo.setCneeDistrict("");
			userOrderInfo.setShipperReference("");
			userOrderInfo.setCneeReference1("");
			userOrderInfo.setCneeReference2("");
			userOrderInfo.setFood("");
			userOrderInfo.setUploadType("HAND");
			userOrderInfo.encryptData();
			ArrayList<UserOrderItemVO> returnItemInfo = new ArrayList<UserOrderItemVO>();
			returnItemInfo = mapper.selectStockOutReturnItemInfo(parameters);
			String itemStatus = "";
			
			for (int i = 0; i < returnItemInfo.size(); i++) {
				UserOrderItemVO userOrderItem = new UserOrderItemVO();
				itemStatus = "";
				userOrderItem.setNno(nno);
				userOrderItem.setSubNo(returnItemInfo.get(i).getSubNo());
				userOrderItem.setOrgStation(returnItemInfo.get(i).getOrgStation());
				userOrderItem.setUserId(returnItemInfo.get(i).getUserId());
				userOrderItem.setHsCode(returnItemInfo.get(i).getHsCode());
				userOrderItem.setItemDetail(returnItemInfo.get(i).getItemDetail());
				userOrderItem.setNativeItemDetail(returnItemInfo.get(i).getNativeItemDetail());
				if (userOrderItem.getNativeItemDetail().equals("") && !optionItemVO.getNativeItemDetailYn().isEmpty()) {
					itemStatus += "TMPREQ37,";
				}
				userOrderItem.setUnitCurrency(returnItemInfo.get(i).getUnitCurrency());
				if (userOrderItem.getUnitCurrency().equals("") && !optionItemVO.getUnitCurrencyYn().isEmpty()) {
					itemStatus += "TMPREQ38,";
				}
				userOrderItem.setItemCnt(returnItemInfo.get(i).getItemCnt());
				userOrderItem.setUnitValue(returnItemInfo.get(i).getUnitValue());
				userOrderItem.setBrand(returnItemInfo.get(i).getBrand());
				if (userOrderItem.getUnitValue().equals("") && !optionItemVO.getUnitValueYn().isEmpty()) {
					itemStatus += "TMPREQ39,";
				}
				userOrderItem.setMakeCntry(returnItemInfo.get(i).getMakeCntry());
				userOrderItem.setMakeCom(returnItemInfo.get(i).getMakeCom());
				if (userOrderItem.getMakeCom().equals("") && !optionItemVO.getMakeComYn().isEmpty()) {
					itemStatus += "TMPREQ51,";
				}
				userOrderItem.setItemDiv(returnItemInfo.get(i).getItemDiv());
				if (userOrderItem.getItemDiv().equals("") && !optionItemVO.getItemDivYn().isEmpty()) {
					itemStatus += "TMPREQ49,";
				}
				userOrderItem.setWtUnit(returnItemInfo.get(i).getWtUnit());
				userOrderItem.setQtyUnit("");
				if (userOrderItem.getQtyUnit().equals("") && !optionItemVO.getQtyUnitYn().isEmpty()) {
					itemStatus += "TMPREQ41,";
				}
				userOrderItem.setPackageUnit("");
				userOrderItem.setExchangeRate("");
				userOrderItem.setChgCurrency(userOrderItem.getUnitCurrency());
				userOrderItem.setItemMeterial("");
				userOrderItem.setTakeInCode("");
				userOrderItem.setUserWta(0);
				if (userOrderItem.getUserWta() == 0 && !optionItemVO.getItemUserWtaYn().isEmpty()) {
					itemStatus += "TMP13,";
				}
				userOrderItem.setUserWtc(0);
				userOrderItem.setItemUrl(returnItemInfo.get(i).getItemUrl());
				userOrderItem.setItemImgUrl(returnItemInfo.get(i).getItemImgUrl());
				if (userOrderItem.getItemImgUrl().equals("") && !optionItemVO.getItemImgUrlYn().isEmpty()) {
					itemStatus += "TMPREQ48,";
				}
				userOrderItem.setTrkCom("");
				userOrderItem.setTrkNo("");
				userOrderItem.setTrkDate("");
				userOrderItem.setCusItemCode(returnItemInfo.get(i).getCusItemCode());
				if (userOrderItem.getCusItemCode().equals("") && !optionItemVO.getCusItemCodeYn().isEmpty()) {
					itemStatus += "TMPREQ43,";
				}
				userOrderItem.setWUserId(wUserId);
				userOrderItem.setWUserIp(wUserIp);
				userOrderItem.setNationCode("");
				userOrderItem.setDimUnit("");
				userOrderItem.setStatus(itemStatus);
				mapper.insertReturnItemToTmpItemInfo(userOrderItem);
			}
			mapper.insertReturnOrderToTmpOrderInfo(userOrderInfo);
			
			rstMap.put("STATUS", "SUCCESS");
			
		} catch (Exception e) {
			e.printStackTrace();
			rstMap.put("STATUS", "FAIL");
		}
		
		return rstMap;
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTransCodeList(HashMap<String, Object> parameters) {
		return mapper.selectTransCodeList(parameters);
	}

	@Override
	public LinkedHashMap<String, ArrayList<ShpngListVO>> selectReturnTmpList(HashMap<String, Object> parameters) {
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>>();
		ArrayList<ShpngListVO> orderList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tmpOrderList = new ArrayList<ShpngListVO>();
		
		try {
			orderList = mapper.selectReturnOrderTmpList(parameters);
			for (int i = 0; i < orderList.size(); i++) {
				orderList.get(i).dncryptData();
			}
			
			if (orderList.size() == 0) {
				return returnMap;
			}
			
			ArrayList<String> orderError = new ArrayList<String>();
			ArrayList<String> itemError = new ArrayList<String>();
			orderError = mapper.selectErrorList(orderList.get(0).getNno());
			itemError = mapper.selectErrorItem(orderList.get(0).getNno(), "1");
			orderList.get(0).setErrorList(orderError);
			orderList.get(0).setErrorItem(itemError);
			tmpOrderList.add(orderList.get(0));
			
			if (orderError.size() < 1) {
				orderError = mapper.selectErrorMatch(orderList.get(0).getNno());
				orderList.get(0).setErrorList(orderError);
			}
			
			for (int i = 1; i < orderList.size(); i++) {
				orderError = new ArrayList<String>();
				itemError = new ArrayList<String>();
				if (orderList.get(i).getSubNo().equals("1")) {
					returnMap.put(tmpOrderList.get(0).getNno(), tmpOrderList);
					tmpOrderList = new ArrayList<ShpngListVO>();
					orderError = mapper.selectErrorList(orderList.get(i).getNno());
					itemError = mapper.selectErrorItem(orderList.get(i).getNno(), orderList.get(i).getSubNo());
					orderList.get(i).setErrorList(orderError);
					orderList.get(i).setErrorItem(itemError);
					tmpOrderList.add(orderList.get(i));
					if (orderError.size() < 1) {
						orderError = mapper.selectErrorMatch(orderList.get(i).getNno());
						orderList.get(i).setErrorList(orderError);
					}
				} else {
					itemError = mapper.selectErrorItem(orderList.get(i).getNno(), orderList.get(i).getSubNo());
					orderList.get(i).setErrorItem(itemError);
					tmpOrderList.add(orderList.get(i));
				}
			}
			returnMap.put(tmpOrderList.get(0).getNno(), tmpOrderList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnMap;
		}
		
		return returnMap;
	}

	@Override
	public int selectReturnTmpListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnTmpListCnt(parameters);
	}
	
	@Override
	public UserOrderListVO selectUserOrderInfo(HashMap<String, Object> parameters) {
		return mapper.selectUserOrderInfo(parameters);
	}

	@Override
	public ArrayList<UserOrderItemVO> selectUserOrderItem(HashMap<String, Object> parameters) {
		return mapper.selectUserOrderItem(parameters);
	}

	@Override
	public ArrayList<String> selectUserOrgNation(HashMap<String, Object> parameters) {
		return mapper.selectUserOrgNation(parameters);
	}

	@Override
	public ArrayList<UserTransComVO> selectDstnTrans(HashMap<String, Object> parameters) {
		return mapper.selectDstnTrans(parameters);
	}

	@Transactional
	@Override
	public void insertReturnOrderWeightInfo(HashMap<String, Object> parameters) {
		try {
			mapper.insertHawbInfo(parameters);
			mapper.insertHawbItemInfo(parameters);
			mapper.insertVolumeInfo(parameters);
			mapper.updateStockOutNno(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<String> selectGroupIdxByNno(String nno) {
		return mapper.selectGroupIdxByNno(nno);
	}

	@Transactional
	@Override
	public void execReturnTmpOrderCancel(ReturnOrderListVO orderVO) {
		
		try {
			mapper.deleteTmpReturnOrderInfo(orderVO);
			mapper.deleteTmpWhoutStock(orderVO);
			mapper.updateReturnOrderStateInfo(orderVO);
			memberReturnOrderMapper.insertReturnOrderStateLog(orderVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int selectReturnOrderShipmentCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderShipmentCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderShipment(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderShipment(parameters);
	}

	@Override
	public int selectReturnOrderWhoutListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderWhoutListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderWhoutList(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderWhoutList(parameters);
	}

	@Override
	public int selectReturnOrderStockListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderStockListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderStockList(HashMap<String, Object> parameters) {
		return mapper.selectReturnOrderStockList(parameters);
	}

	@Override
	public int selectReturnCsListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnCsListCnt(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnCsList(HashMap<String, Object> parameters) {
		return mapper.selectReturnCsList(parameters);
	}

	@Override
	public void deleteReturnCs(HashMap<String, Object> parameters) {
		mapper.deleteReturnCs(parameters);
	}

	@Override
	public void createReturnStockPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		String nno = request.getParameter("nno");
		String groupIdx = request.getParameter("groupIdx");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nno", nno);
		parameters.put("groupIdx", groupIdx);

		try {
			int marginTop = 30;
			ArrayList<HashMap<String, Object>> tempStockVoList = new ArrayList<HashMap<String, Object>>();
			tempStockVoList = mapper.selectStockByGrpIdx(parameters);
			
			for (int idx = 0; idx < tempStockVoList.size(); idx++) {
				PDRectangle asd = new PDRectangle(80 * perMM, 60 * perMM);
				PDPage blankPage = new PDPage(asd);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(pdfPage);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);

				net.sourceforge.barbecue.Barcode barcodes = BarcodeFactory.createCode128(tempStockVoList.get(idx).get("stockNo").toString());
				barcodes.setSize(400, 800);
				barcodes.setBarHeight(0);
				barcodes.setBarWidth(0);

				barcodes.setLabel("Barcode creation test...");
				barcodes.setDrawingText(true);

				File barcodefile = new File(barcodePath);
				BarcodeImageHandler.saveJPEG(barcodes, barcodefile);

				PDImageXObject pdImage = PDImageXObject.createFromFileByContent(barcodefile, doc);

				int fontSize = 12; // Or whatever font size you want.
				float titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(idx).get("whStatusName").toString())
						/ 1000 * fontSize;
				contentStream.drawImage(pdImage, (80 * perMM - 220) / 2, 37, 220f, 35f);

				drawText(tempStockVoList.get(idx).get("whStatusName").toString(), NanumGothic, 12,
						(80 * perMM - titleWidth) / 2, 145, contentStream);

				fontSize = 11;
				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(idx).get("rackCode").toString()) / 1000
						* fontSize;
				drawText(tempStockVoList.get(idx).get("rackCode").toString(), NanumGothic, 14,
						(80 * perMM - titleWidth) / 2, 130, contentStream);

				fontSize = 10;
				titleWidth = NanumGothic.getStringWidth(
						tempStockVoList.get(idx).get("userId").toString() + "/" + tempStockVoList.get(idx).get("shipperName").toString()) / 1000
						* fontSize;
				drawText(tempStockVoList.get(idx).get("userId").toString() + "/" + tempStockVoList.get(idx).get("shipperName").toString(),
						NanumGothic, 10, (80 * perMM - titleWidth) / 2, 117, contentStream);

				fontSize = 11;
				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(idx).get("koblNo").toString()) / 1000 * fontSize;
				drawText(tempStockVoList.get(idx).get("koblNo").toString(), NanumGothic, 11, (80 * perMM - titleWidth) / 2, 102,
						contentStream);

				fontSize = 12;
				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(idx).get("orderNo").toString()) / 1000 * fontSize;
				drawText(tempStockVoList.get(idx).get("orderNo").toString(), NanumGothic, 12, (80 * perMM - titleWidth) / 2, 90, contentStream);

				fontSize = 13;
				titleWidth = NanumGothic.getStringWidth(tempStockVoList.get(idx).get("stockNo").toString()) / 1000
						* fontSize;
				drawText(tempStockVoList.get(idx).get("stockNo").toString(), NanumGothic, 13,
						(80 * perMM - titleWidth) / 2, 25, contentStream);
				contentStream.close();
				pdfPage++;
			}
		} catch (Exception e) {
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
	
	private void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream contentStream) throws Exception {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(left, bottom);
		contentStream.showText(text);
		contentStream.endText();
	}

	@Override
	public int selectReturnTaxListCnt(HashMap<String, Object> parameters) {
		return mapper.selectReturnTaxListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnTaxList(HashMap<String, Object> parameters) {
		return mapper.selectReturnTaxList(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnWhInItemInfo(HashMap<String, Object> parameters) {
		return mapper.selectReturnWhInItemInfo(parameters);
	}

	@Override
	public void insertEtcInfo(HashMap<String, Object> parameters) {
		mapper.insertEtcValue(parameters);
	}

	@Override
	public HashMap<String, Object> selectReturnOrderFileList(String nno) {
		return mapper.selectReturnOrderFileList(nno);
	}

	@Override
	public void deleteTmpWhoutInfo(HashMap<String, Object> parameters) {
		mapper.deleteTmpWhoutInfo(parameters);
	}


}
