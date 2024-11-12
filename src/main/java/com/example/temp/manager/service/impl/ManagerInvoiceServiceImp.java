package com.example.temp.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.manager.mapper.ManagerExcelMapper;
import com.example.temp.manager.mapper.ManagerInvoiceMapper;
import com.example.temp.manager.service.ManagerInvoiceService;
import com.example.temp.security.SecurityKeyVO;

@Service
@Transactional
public class ManagerInvoiceServiceImp implements ManagerInvoiceService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${filePath}")
	String realFilePath;

	@Autowired
	private ManagerInvoiceMapper mapper;

	@Autowired
	private ManagerExcelMapper excelMapper;

	@Override
	public ArrayList<HashMap<String, Object>> selectEtc(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectEtc(parameterInfo);
	}

	@Override
	public int selectEtcCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectEtcCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectEtcType(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectEtcType(parameterInfo);
	}

	@Override
	public HashMap<String, Object> mgrEctInUp(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.mgrEctInUp(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectEtcInfoRow(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectEtcInfoRow(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectCurrencyList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectCurrencyList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> mgrEctDel(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.mgrEctDel(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInvList(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectInvList(parameterInfo);
	}

	@Override
	public int selectInvListCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectInvListCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUnPricelist(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectUnPricelist(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectPricelist(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectPricelist(parameterInfo);
	}

	@Override
	public int selectPricelistCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectPricelistCnt(parameterInfo);
	}

	@Override
	public HashMap<String, Object> spInvoiceApply(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.spInvoiceApply(parameterInfo);
	}

	@Override
	public void invExcelDownAci(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) throws Exception {

		try {

			DecimalFormat format = new DecimalFormat("###,###");

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/invoice/invoice_aci_test.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			int nowRowNo = 0;
			
			Row row = null;
			Cell cell = null;

			Row row2 = null;
			Cell cell2 = null;

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			// Sheet sheet2 = wb.createSheet("ETC_Detail");
			
			CellStyle styleRight = wb.createCellStyle();
			styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			styleRight.setBorderTop(CellStyle.BORDER_THIN);
			styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			styleRight.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);

			Font font = wb.createFont();
			font.setColor(IndexedColors.WHITE.getIndex());
			font.setFontHeightInPoints((short) 10);
			font.setFontName("맑은 고딕");

			Font font2 = wb.createFont();
			font2.setColor(IndexedColors.WHITE.getIndex());
			font2.setFontHeightInPoints((short) 12);
			font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font2.setFontName("맑은 고딕");

			Font infoFont = wb.createFont();
			infoFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			infoFont.setFontHeightInPoints((short) 10);
			infoFont.setFontName("맑은 고딕");

			CellStyle totalStyle = wb.createCellStyle();
			totalStyle.setAlignment(CellStyle.ALIGN_CENTER);
			totalStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			totalStyle.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			totalStyle.setFont(font);

			CellStyle totalStyle2 = wb.createCellStyle();
			totalStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
			totalStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderBottom(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderRight(CellStyle.BORDER_DOTTED);
			totalStyle2.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			totalStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			totalStyle2.setFont(font);

			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			cellStyle.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFont(font2);

			CellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderBottom(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderRight(CellStyle.BORDER_DOTTED);
			cellStyle2.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle2.setFont(font2);

			CellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setFont(infoFont);

			CellStyle sheetStyle = wb.createCellStyle();
			sheetStyle.setAlignment(CellStyle.ALIGN_CENTER);
			sheetStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			sheetStyle.setFillForegroundColor(HSSFColor.BROWN.index);
			sheetStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheetStyle.setFont(font);

			int cellNo = 0;
			int rowNo = 0;

			HashMap<String, Object> rstInvoiceInfo = new HashMap<String, Object>();
			rstInvoiceInfo = mapper.selectInvoiceInfo(parameterInfo);

			rowNo = 4;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("STATION_ADDR"));

			cellNo = 0;
			rowNo = 5;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("Tel: " + (String) rstInvoiceInfo.get("STATION_TEL"));

			cellNo = 0;
			rowNo = 8;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue("Bill To: " + (String) rstInvoiceInfo.get("COM_NAME"));

			SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
			String userAddr = AES256Cipher.AES_Decode(rstInvoiceInfo.get("USER_ADDR").toString(),
					SecurityKeyVO.getSymmetryKey());
			String userAddrDetail = AES256Cipher.AES_Decode(rstInvoiceInfo.get("USER_ADDR_DETAIL").toString(),
					SecurityKeyVO.getSymmetryKey());

			cellNo = 0;
			rowNo = 9;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(userAddr);

			cellNo = 0;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(userAddrDetail);

			cellNo = 11;
			rowNo = 9;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INVOICE_NO"));

			String invoiceNo = rstInvoiceInfo.get("INVOICE_NO").toString();

			cellNo = 11;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INV_TO_DATE"));

			cellNo = 15;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INV_TO_DATE"));

			// Double totalValue =
			// Double.parseDouble(String.valueOf(rstInvoiceInfo.get("TOTAL_VALUE")));

			cellNo = 11;
			rowNo = 11;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(
					(String) rstInvoiceInfo.get("INV_FROM_DATE") + " - " + (String) rstInvoiceInfo.get("INV_TO_DATE"));

			ArrayList<HashMap<String, Object>> rstInvoicelist = new ArrayList<HashMap<String, Object>>();
			rstInvoicelist = mapper.selecInvoiceList(parameterInfo);

			rowNo = 14;
			// rowNo = 15;
			int RowNum = 1;

			Double totalDlvChg = 0.0;
			Double totalFscChg = 0.0;
			Double totalInspChg = 0.0;
			Double totalAddBlChg = 0.0;
			Double totalClearanceChg = 0.0;
			Double totalQuarantinChg = 0.0;
			Double totalExportChg = 0.0;
			Double totalPapChg = 0.0;
			Double totalSurChg = 0.0;
			Double totalEtcChg = 0.0;
			Double totalAmountChg = 0.0;
			Double totalWt = 0.0;
			int totalBoxCnt = 0;
			int totalHawbCnt = 0;
			Double totalEtcDetailChg = 0.0;

			for (int index = 0; index < rstInvoicelist.size(); index++) {
				row = sheet.createRow(rowNo);

				Double dlvChg = 0.0;
				Double fscChg = 0.0;
				Double inspChg = 0.0;
				Double addBlChg = 0.0;
				Double clearanceChg = 0.0;
				Double quarantinChg = 0.0;
				Double exportChg = 0.0;
				Double papChg = 0.0;
				Double etcChg = 0.0;
				Double surChg = 0.0;
				Double amountChg = 0.0;

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(RowNum);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("DEP_DATE").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("HAWB_NO").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("ORDER_NO").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("CNEE_NAME").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("DSTN_NATION").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("APPLY_WT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("BOX_CNT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoicelist.get(index).get("DLV_CHG").toString());
				//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG")))));
				// cell.setCellValue(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG"))));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("FSC_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("INSP_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("ADDBL_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("CLEARANCE_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("QUARANTINE_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("EXPORT_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("PAP_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("SUR_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("SUR_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("SUR_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(rstInvoicelist.get(index).get("ETC_CHG").toString());
					//cell.setCellValue(format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG")))));
				}

				dlvChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG")));
				fscChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG")));
				inspChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG")));
				addBlChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG")));
				clearanceChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG")));
				quarantinChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG")));
				exportChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG")));
				papChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG")));
				surChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("SUR_CHG")));
				etcChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG")));

				amountChg = dlvChg + fscChg + inspChg + addBlChg + clearanceChg + quarantinChg + exportChg + papChg
						+ surChg + etcChg;

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(amountChg));
				// cell.setCellValue(amountChg);

				totalDlvChg = totalDlvChg + dlvChg;
				totalFscChg = totalFscChg + fscChg;
				totalInspChg = totalInspChg + inspChg;
				totalAddBlChg = totalAddBlChg + addBlChg;
				totalClearanceChg = totalClearanceChg + clearanceChg;
				totalQuarantinChg = totalQuarantinChg + quarantinChg;
				totalExportChg = totalExportChg + exportChg;
				totalPapChg = totalPapChg + papChg;
				totalEtcChg = totalEtcChg + etcChg;
				totalSurChg = totalSurChg + surChg;
				totalAmountChg = totalAmountChg + amountChg;

				RowNum++;
				rowNo++;

				totalHawbCnt = RowNum;
				totalBoxCnt = totalBoxCnt + Integer.parseInt(rstInvoicelist.get(index).get("BOX_CNT").toString());
				totalWt = totalWt + Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("APPLY_WT")));
			}

			System.out.println("*******");
			System.out.println(RowNum);
			System.out.println(rowNo);
			System.out.println("*******");

			ArrayList<HashMap<String, Object>> rstInvoiceEtclist = new ArrayList<HashMap<String, Object>>();
			rstInvoiceEtclist = mapper.selecInvoiceEtcList(parameterInfo);

			for (int i = 0; i < rstInvoiceEtclist.size(); i++) {
				row = sheet.createRow(rowNo);

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(RowNum);

				cellNo = 1;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoiceEtclist.get(i).get("INV_DATE").toString().replace("-", ""));

				String fromC = "C" + String.valueOf(rowNo + 1);
				String toF = "F" + String.valueOf(rowNo + 1);
				String mergeCell = fromC + ":" + toF;
				sheet.addMergedRegion(CellRangeAddress.valueOf(mergeCell));

				cellNo = 2;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoiceEtclist.get(i).get("REMARK").toString());

				cellNo = 3;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);

				cellNo = 4;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);

				cellNo = 5;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);

				cellNo = 6;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 7;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 8;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 9;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 10;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 11;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 12;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 13;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 14;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 15;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 16;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				Double etcChg = 0.0;
				etcChg = Double.parseDouble(String.valueOf(rstInvoiceEtclist.get(i).get("INV_AMT")));

				cellNo = 17;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(etcChg));

				double amountChg = 0.0;
				amountChg = amountChg + etcChg;

				cellNo = 18;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(amountChg));

				totalAmountChg = totalAmountChg + amountChg;
				totalEtcChg = totalEtcChg + etcChg;

				rowNo++;
				RowNum++;

				totalHawbCnt = RowNum;

			}

			System.out.println(totalAmountChg);

			String merge1 = "A" + String.valueOf(rowNo + 1);
			String merge2 = "B" + String.valueOf(rowNo + 1);
			String merge = merge1 + ":" + merge2;

			sheet.addMergedRegion(CellRangeAddress.valueOf(merge));
			cellNo = 0;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(totalStyle);

			cellNo = 1;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 2;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalHawbCnt - 1);
			cell.setCellStyle(totalStyle);

			cellNo = 3;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 4;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 5;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 6;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalWt);
			cell.setCellStyle(totalStyle);

			cellNo = 7;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalBoxCnt);
			cell.setCellStyle(totalStyle);

			cellNo = 8;
			cell = row.createCell(cellNo);
			cell.setCellValue(format.format(totalDlvChg));
			cell.setCellStyle(totalStyle2);

			cellNo = 9;
			cell = row.createCell(cellNo);
			if (totalFscChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalFscChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 10;
			cell = row.createCell(cellNo);
			if (totalInspChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalInspChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 11;
			cell = row.createCell(cellNo);
			if (totalAddBlChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalAddBlChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 12;
			cell = row.createCell(cellNo);
			if (totalClearanceChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalClearanceChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 13;
			cell = row.createCell(cellNo);
			if (totalQuarantinChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalQuarantinChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 14;
			cell = row.createCell(cellNo);
			if (totalExportChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalExportChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 15;
			cell = row.createCell(cellNo);
			if (totalPapChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalPapChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 16;
			cell = row.createCell(cellNo);
			if (totalSurChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalSurChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 17;
			cell = row.createCell(cellNo);
			if (totalEtcChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalEtcChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 18;
			cell = row.createCell(cellNo);
			if (totalAmountChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalAmountChg));
			}
			cell.setCellStyle(totalStyle2);

			rowNo++;

			Double vatChg = 0.0;

			int cnt = mapper.selectDstnNationByInvNo(parameterInfo);
			System.out.println(cnt);

			if (cnt >= 1) {

				vatChg = totalAmountChg * 0.1;

				row = sheet.createRow(rowNo);
				cellNo = 17;
				cell = row.createCell(cellNo);
				cell.setCellValue("VAT");
				cell.setCellStyle(totalStyle);

				cellNo = 18;
				cell = row.createCell(cellNo);
				cell.setCellValue(format.format(vatChg));
				cell.setCellStyle(totalStyle2);

				rowNo++;

			}

			Double totalChg = totalAmountChg + vatChg;

			row = sheet.createRow(rowNo);
			cellNo = 17;
			cell = row.createCell(cellNo);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(cellStyle);

			cellNo = 18;
			cell = row.createCell(cellNo);
			cell.setCellValue(format.format(totalChg));
			cell.setCellStyle(cellStyle2);

			rowNo++;
			rowNo++;
			rowNo++;
			System.out.println(rowNo);
			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("* 무통장 입금 또는 인터넷 뱅킹으로 입금하실 경우, 반드시 (주)에이씨아이월드와이드 시스템에 등록된");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("BANK INFORMATION : ");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("  상호로 입금하여 주시기 바랍니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("은행명 : 국민은행");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("  다른 명의로 입금하시어 확인이 불가능할 경우, 지속적으로 미결처리 되실 수 있음을 알려드립니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("계좌번호 : 649337-04-000571");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("** 당월 기타비용 추가 발생 시 익월 내역서에 추가청구 드리겠습니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("예금주 : (주)에이씨아이월드와이드");
			cell.setCellStyle(cellStyle3);
			
			nowRowNo = rowNo;

			CellStyle formatCs = wb.createCellStyle();
			formatCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("_($*#,##0_);_($*(#,##0);_($* \"-\"_);_(@_)"));

			cellNo = 15;
			rowNo = 11;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(totalChg + totalEtcDetailChg);
			cell.setCellStyle(formatCs);
			
			wb.setPrintArea(0, 0, 18, 0, nowRowNo);

			// 컨텐츠 타입과 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + invoiceNo + ".xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	@Override
	public void invExcelDownDef(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) throws Exception {
		try {
			ArrayList<HashMap<String, Object>> rstInvoice = new ArrayList<HashMap<String, Object>>();
			rstInvoice = mapper.invoiceExcelDown(parameterInfo);

			// parameterInfo.get(arg0)
			/*
			 * parameterInfo.put("orgStation",(String)request.getSession().getAttribute(
			 * "ORG_STATION")); parameterInfo.put("userId",request.getParameter("userId"));
			 * parameterInfo.put("invoiceNo",request.getParameter("invoiceNo"));
			 */

			String invoiceNo = (String) parameterInfo.get("invoiceNo");

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/invoice/invoice_def.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row = null;
			Cell cell = null;

			int rowNo = 6;
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle styleLeft = wb.createCellStyle();
			styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
			styleLeft.setBorderTop(CellStyle.BORDER_THIN);
			styleLeft.setBorderBottom(CellStyle.BORDER_THIN);
			styleLeft.setBorderLeft(CellStyle.BORDER_THIN);
			styleLeft.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle styleRight = wb.createCellStyle();
			styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			styleRight.setBorderTop(CellStyle.BORDER_THIN);
			styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			styleRight.setBorderRight(CellStyle.BORDER_THIN);

			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 10);
			style.setFont(font);
			styleLeft.setFont(font);
			styleRight.setFont(font);

			rowNo = 1;

			SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();

			for (int index = 0; index < rstInvoice.size(); index++) {

				row = sheet.createRow(rowNo);
				int cellNo = 0;

				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("INV_DATE"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("DSTN_NATION"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("ORDER_NO"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("HAWB_NO"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("AGENCY_BL"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("ORDER_TYPE_NAME"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("SHIPPER_NAME"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("CNEE_NAME"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("CNEE_STATE"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("CNEE_CITY"));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String) rstInvoice.get(index).get("CNEE_ZIP"));

				String cneeAddr = AES256Cipher.AES_Decode(rstInvoice.get(index).get("CNEE_ADDR").toString(),
						SecurityKeyVO.getSymmetryKey());
				String cneeAddrDetail = AES256Cipher.AES_Decode(
						rstInvoice.get(index).get("CNEE_ADDR_DETAIL").toString(), SecurityKeyVO.getSymmetryKey());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(cneeAddr + cneeAddrDetail);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("BOX_CNT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("SKU_CNT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(rstInvoice.get(index).get("CUS_ITEM_CODE").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("TOTAL_ITEM_CNT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(rstInvoice.get(index).get("ITEM_DETATIL").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(rstInvoice.get(index).get("UNIT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("WTA").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("WTC").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("INV_WT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(rstInvoice.get(index).get("VOLUME").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("DLV_AMT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("CLEARANCE_AMT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("EXPORTDECL_AMT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("PAP_AMT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(rstInvoice.get(index).get("ETC_AMT").toString());

				
				if (rstInvoice.get(index).get("ORG_STATION").toString().equals("082")) {
					cellNo++;
					cell = row.createCell(cellNo);
					cell.setCellStyle(styleRight);
					cell.setCellValue(rstInvoice.get(index).get("DLV_APPLY_WT").toString());
				}

				/*
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("SKU_CNT"));
				 * 
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("TOTAL_ITEM_CNT"));
				 * 
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("INV_WT"));
				 * 
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("DLV_AMT"));
				 * 
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("CUSTOMS_AMT"));
				 * 
				 * 
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(style);
				 * cell.setCellValue((String)rstInvoice.get(index).get("ETC_AMT"));
				 */
				rowNo++;
			}

			// 컨텐츠 타입과 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + invoiceNo + ".xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectBoxSizeList(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectBoxSizeList(nno);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectClearance(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String startDate = request.getParameter("wDate");
		String endDate = request.getParameter("wDate");
		return mapper.selectClearance(startDate, endDate);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectClearanceCnt(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		return mapper.selectClearanceCnt(startDate, endDate);
	}

	@Override
	public void insertClearanceExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			String excelRoot) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						HashMap<String, Object> clearanceInfo = new HashMap<String, Object>();
						row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String _value = "";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (_cell == null) {
									_value = "";
								} else {
									switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_value = _cell.getStringCellValue();
										if (_value.split("[.]").length != 1) {
											if (_value.split("[.]")[1].equals("0")) {
												_value = _value.split("[.]")[0];
											}
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										_value = _cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										_value = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										_value = _cell.getErrorCellValue() + "";
										break;
									}
								}

								switch (columnIndex) {
								case 0: {
									clearanceInfo.put("orgStation", _value);
									break;
								}
								case 1: {
									clearanceInfo.put("userId", _value);
									break;
								}
								case 2: {
									clearanceInfo.put("hawbNo", _value);
									break;
								}
								}
							}
							clearanceInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
							clearanceInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
							if (mapper.selectChkClearance(clearanceInfo) == null) {
								mapper.insertClearance(clearanceInfo);
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Exception", e);
			}
		}
	}

	public Map filesUpload(MultipartHttpServletRequest multi, String uploadPaths) throws Exception {
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

	public int selectARListCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectARListCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectARList(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectARList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectUserInfo(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectUserInfo(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectreceivedCodeList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectreceivedCodeList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReceivedCodeList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectReceivedCodeList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertReceivedCode(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.insertReceivedCode(parameterInfo);
	}

	@Override
	public HashMap<String, Object> deleteReceivedCode(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.deleteReceivedCode(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.insertInvoiceReceived(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInvoiceDetailList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectInvoiceDetailList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReceivedDetailList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectReceivedDetailList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> deleteInvoiceReceived(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.deleteInvoiceReceived(parameterInfo);

	}

	@Override
	public HashMap<String, Object> selectInvoiceStatusList(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectInvoiceStatusList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.insertInvoiceComfirm(parameterInfo);
	}

	@Override
	public int selectARComfirmCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectARComfirmCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectARComfirmList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectARComfirmList(parameterInfo);
	}

	@Override
	public int selectARDecisionCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.selectARDecisionCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectARDecisionList(HashMap<String, Object> parameterInfo)
			throws Exception {

		return mapper.selectARDecisionList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> spInvoiceDecision(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.spInvoiceDecision(parameterInfo);
	}

	@Override
	public void deleteInvoiceComfirm(HashMap<String, Object> parameterInfo) throws Exception {

		mapper.deleteInvoiceComfirm(parameterInfo);
	}

	@Override
	public HashMap<String, Object> spInvoiceUnposting(HashMap<String, Object> parameterInfo) throws Exception {

		return mapper.spInvoiceUnposting(parameterInfo);
	}
	
	@Override
	public int selectWeightListCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectWeightListCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWeightList(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectWeightList(parameterInfo);
	}

	@Override
	public void weightListExcelDown(HttpServletResponse response, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> weightExcelList = new ArrayList<HashMap<String, Object>>();
		parameters.put("startDate", request.getParameter("startDate"));
		parameters.put("endDate", request.getParameter("endDate"));
		parameters.put("orgStation", request.getSession().getAttribute("ORG_STATION").toString());
		parameters.put("transCode", request.getParameter("transCode"));

		weightExcelList = mapper.selectWeightListExcel(parameters);

		String savePath = realFilePath + "excel/expLicence/";
		String filename = "weightListExcel" + request.getSession().getAttribute("USER_ID").toString() + ".xlsx";

		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		Sheet sheet1 = xlsxWb.createSheet("Form");

		// HEAD 스타일 설정 START
		CellStyle cellStyleHeader = xlsxWb.createCellStyle();
		cellStyleHeader.setWrapText(false);
		cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// HEAD 스타일 설정 END

		// ROW-CELL 선언 START
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		int cellCnt = 0;
		// ROW-CELL 선언 END

		// HEADER 생성 START
		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("No");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 2000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("도착지 국가");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("USER ID");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("배송사 코드");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("송장번호");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("배송사 접수 번호");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("WMS 측정 넓이");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("WMS 측정 높이");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("WMS 측정 길이");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("기준값");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("길이 단위");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("무게 단위");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("WMS 실무게");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("WMS 부피 무게");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("배송사 실무게");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("배송사 부피 무게");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("적용 타입");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("적용 무게");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("날짜");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		for (int i = 0; i < weightExcelList.size(); i++) {
			cellCnt = 0;
			rowCnt++;
			row = sheet1.createRow(rowCnt);

			cell = row.createCell(cellCnt);
			cell.setCellValue(i + 1);
			sheet1.setColumnWidth(cellCnt, 2000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("dstnNation").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("userId").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("transCode").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("hawbNo").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("agencyBl").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("width").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("height").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("length").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("per").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("dimUnit").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("wtUnit").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("userWta").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("userWtc").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("agencyWta").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("agencyWtc").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("applyType").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("applyWt").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(weightExcelList.get(i).get("wDate").toString());
			sheet1.setColumnWidth(cellCnt, 4000);
			cellCnt++;

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

	@Override
	public void invExcelDownAciHm(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> columnName = new ArrayList<HashMap<String, Object>>();
		LocalDate currentDate = LocalDate.now();
		String savePath = realFilePath + "excel/expLicence/";
		String filename = "INVOICE_HMINTER_" + currentDate + ".xlsx";

		ArrayList<HashMap<String, Object>> rstInvoicelist = new ArrayList<HashMap<String, Object>>();
		rstInvoicelist = mapper.selecInvoiceListHm(parameterInfo);

		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		Sheet sheet1 = xlsxWb.createSheet("청구서");

		// HEAD 스타일 설정 START
		CellStyle cellStyleHeader = xlsxWb.createCellStyle();
		cellStyleHeader.setWrapText(false);
		cellStyleHeader.setFillForegroundColor(HSSFColor.AQUA.index);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// HEAD 스타일 설정 END
		// ROW-CELL 선언 START
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		int cellCnt = 0;
		// ROW-CELL 선언 END

		row = sheet1.createRow(rowCnt);
		columnName = excelMapper.selectExcelColumnName("HMINTER_INVOICE");
		for (int i = 0; i < columnName.size(); i++) {
			cell = row.createCell(cellCnt);
			cell.setCellValue(columnName.get(i).get("COL_NAME").toString());
			cell.setCellStyle(cellStyleHeader);
			sheet1.setColumnWidth(cellCnt, 8000);
			cellCnt++;
		}
		rowCnt++;

		for (int i = 0; i < rstInvoicelist.size(); i++) {
			cellCnt = 0;
			row = sheet1.createRow(rowCnt);

			cell = row.createCell(cellCnt);
			cell.setCellValue(i + 1);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("DSTN_NATION").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("브로스");
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("ORDER_DATE").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("SHIPPER_REFERENCE").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("CNEE_REFERENCE1").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("HAWB_NO").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("WIDTH").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("LENGTH").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("HEIGHT").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("WTC").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("WTA").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("APPLY_WT").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("DLV_CHG").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("FSC_CHG").toString());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rstInvoicelist.get(i).get("SUR_CHG").toString());
			cellCnt++;

			double dlvChg = Double.parseDouble(rstInvoicelist.get(i).get("DLV_CHG").toString());
			double fscChg = Double.parseDouble(rstInvoicelist.get(i).get("FSC_CHG").toString());
			double surChg = Double.parseDouble(rstInvoicelist.get(i).get("SUR_CHG").toString());
			double total = dlvChg + fscChg + surChg;

			cell = row.createCell(cellCnt);
			cell.setCellValue(total);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("SUR1: 유류할증료, SUR2: 코로나 SURCHARGE");
			cellCnt++;

			rowCnt++;
		}

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
		try {
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

	@Override
	public void insertEtcExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request, String excelRoot)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resMap = new HashMap<String, String>(); // 엑셀 업로드 리턴
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet()) {
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if (filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						HashMap<String, Object> etcInfo = new HashMap<String, Object>();
						row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) {
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String _value = "";
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								XSSFCell _cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								if (_cell == null) {
									_value = "";
								} else {
									switch (_cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
									case HSSFCell.CELL_TYPE_NUMERIC:
										if (DateUtil.isCellDateFormatted(_cell)) {
											Date date = _cell.getDateCellValue();
											_value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											break;
										}
										_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
										_value = _cell.getStringCellValue();
										if (_value.split("[.]").length != 1) {
											if (_value.split("[.]")[1].equals("0")) {
												_value = _value.split("[.]")[0];
											}
										}

										break;
									case HSSFCell.CELL_TYPE_STRING:
										_value = _cell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										_value = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										_value = _cell.getErrorCellValue() + "";
										break;
									}
								}

								switch (columnIndex) {
								case 0: {
									etcInfo.put("hawbNo", _value);
									break;
								}
								case 1: {
									etcInfo.put("etcType", _value);
									break;
								}
								case 2: {
									etcInfo.put("etcValue", _value);
									break;
								}
								case 3: {
									etcInfo.put("etcCurrency", _value);
									break;
								}
								case 4: {
									etcInfo.put("etcDate", _value);
									break;
								}
								case 5: {
									etcInfo.put("remark", _value);
									break;
								}

								}
							}
							etcInfo.put("wUserId", request.getSession().getAttribute("USER_ID"));
							etcInfo.put("wUserIp", request.getSession().getAttribute("USER_IP"));
							etcInfo.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
							etcInfo.put("idx", "");
							mapper.insertEtcExcel(etcInfo);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Exception", e);
			}

		}
	}

	@Override
	public void createEtcExcel(HttpServletResponse response, HttpServletRequest request, String excelRoot)
			throws Exception {
		// TODO Auto-generated method stub
		String savePath = realFilePath + "excel/expLicence/";
		String filename = "EtcExcel" + request.getSession().getAttribute("USER_ID").toString() + ".xlsx";

		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		Sheet sheet1 = xlsxWb.createSheet("Form");

		// HEAD 스타일 설정 START
		CellStyle cellStyleHeader = xlsxWb.createCellStyle();
		cellStyleHeader.setWrapText(false);
		cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// HEAD 스타일 설정 END

		// ROW-CELL 선언 START
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		int cellCnt = 0;
		int cellPivoit = 0;
		// ROW-CELL 선언 END

		// HEADER 생성 START
		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("HAWB NO");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("ETC TYPE");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("금액");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("화폐 단위");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("ETC DATE");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("설명");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;
		cellCnt++;

		cell = row.createCell(cellCnt);
		cell.setCellValue("ETC TYPE 종류");
		cell.setCellStyle(cellStyleHeader);
		sheet1.setColumnWidth(cellCnt, 4000);
		rowCnt++;

		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("ETC");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;
		cell = row.createCell(cellCnt);
		cell.setCellValue("기타");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt--;
		rowCnt++;

		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("ETC_FEE");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;
		cell = row.createCell(cellCnt);
		cell.setCellValue("기타 배송비");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt--;
		rowCnt++;

		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("PACKING");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;
		cell = row.createCell(cellCnt);
		cell.setCellValue("포장비");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt--;
		rowCnt++;

		row = sheet1.createRow(rowCnt);
		cell = row.createCell(cellCnt);
		cell.setCellValue("PALET");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt++;
		cell = row.createCell(cellCnt);
		cell.setCellValue("보관비");
		sheet1.setColumnWidth(cellCnt, 4000);
		cellCnt--;

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

	@Override
	public int selectARControlListCnt(HashMap<String, Object> parameterInfo) {
		return mapper.selectARControlListCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectARControlList(HashMap<String, Object> parameterInfo) {
		return mapper.selectARControlList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInvoiceNo(HashMap<String, Object> params) {
		return mapper.selectInvoiceNo(params);
	}

	@Override
	public HashMap<String, Object> selectUserInfo2(HashMap<String, Object> params) {
		return mapper.selectUserInfo2(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInvoiceDetail(HashMap<String, Object> params) {
		return mapper.selectInvoiceDetail(params);
	}

	@Override
	public void insertInvoiceReceived2(HashMap<String, Object> parameterInfo) {
		mapper.insertInvoiceReceived2(parameterInfo);

	}

	@Override
	public void deleteInvoiceReceived2(HashMap<String, Object> parameterInfo) {
		mapper.deleteInvoiceReceived2(parameterInfo);

	}

	@Override
	public void insertInvoiceComfirm2(HashMap<String, Object> parameterInfo) {
		mapper.insertInvoiceComfirm2(parameterInfo);

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInvoiceDetailByCom(HashMap<String, Object> params) {
		return mapper.selectInvoiceDetailByCom(params);
	}

	@Override
	public void invExcelDownEtc(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) {
		try {

			DecimalFormat format = new DecimalFormat("###,###");

			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/invoice/invoice_aci_etc.xlsx";
			FileInputStream fis = new FileInputStream(filePath);

			Row row = null;
			Cell cell = null;

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);

			CellStyle styleRight = wb.createCellStyle();
			styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			styleRight.setBorderTop(CellStyle.BORDER_THIN);
			styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			styleRight.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);

			CellStyle descStyle = wb.createCellStyle();
			descStyle.setAlignment(CellStyle.ALIGN_LEFT);
			descStyle.setBorderTop(CellStyle.BORDER_THIN);
			descStyle.setBorderBottom(CellStyle.BORDER_THIN);
			descStyle.setBorderLeft(CellStyle.BORDER_THIN);
			descStyle.setBorderRight(CellStyle.BORDER_THIN);
			descStyle.setWrapText(true);

			Font font = wb.createFont();
			font.setColor(IndexedColors.WHITE.getIndex());
			font.setFontHeightInPoints((short) 10);
			font.setFontName("맑은 고딕");

			Font font2 = wb.createFont();
			font2.setColor(IndexedColors.WHITE.getIndex());
			font2.setFontHeightInPoints((short) 12);
			font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font2.setFontName("맑은 고딕");

			Font infoFont = wb.createFont();
			infoFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			infoFont.setFontHeightInPoints((short) 10);
			infoFont.setFontName("맑은 고딕");

			CellStyle totalStyle = wb.createCellStyle();
			totalStyle.setAlignment(CellStyle.ALIGN_CENTER);
			totalStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			totalStyle.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			totalStyle.setFont(font);

			CellStyle totalStyle2 = wb.createCellStyle();
			totalStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
			totalStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderBottom(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			totalStyle2.setBorderRight(CellStyle.BORDER_DOTTED);
			totalStyle2.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			totalStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			totalStyle2.setFont(font);

			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			cellStyle.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFont(font2);

			CellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderBottom(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderLeft(CellStyle.BORDER_DOTTED);
			cellStyle2.setBorderRight(CellStyle.BORDER_DOTTED);
			cellStyle2.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle2.setFont(font2);

			CellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setFont(infoFont);

			CellStyle sheetStyle = wb.createCellStyle();
			sheetStyle.setAlignment(CellStyle.ALIGN_CENTER);
			sheetStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderLeft(CellStyle.BORDER_DOTTED);
			sheetStyle.setBorderRight(CellStyle.BORDER_DOTTED);
			sheetStyle.setFillForegroundColor(HSSFColor.BROWN.index);
			sheetStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheetStyle.setFont(font);

			int cellNo = 0;
			int rowNo = 0;

			HashMap<String, Object> rstInvoiceInfo = new HashMap<String, Object>();
			rstInvoiceInfo = mapper.selectInvoiceInfo(parameterInfo);

			rowNo = 4;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("STATION_ADDR"));

			cellNo = 0;
			rowNo = 5;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("Tel: " + (String) rstInvoiceInfo.get("STATION_TEL"));

			cellNo = 0;
			rowNo = 8;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue("Bill To: " + (String) rstInvoiceInfo.get("COM_NAME"));

			SecurityKeyVO SecurityKeyVO = new SecurityKeyVO();
			String userAddr = AES256Cipher.AES_Decode(rstInvoiceInfo.get("USER_ADDR").toString(),
					SecurityKeyVO.getSymmetryKey());
			String userAddrDetail = AES256Cipher.AES_Decode(rstInvoiceInfo.get("USER_ADDR_DETAIL").toString(),
					SecurityKeyVO.getSymmetryKey());

			cellNo = 0;
			rowNo = 9;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(userAddr);

			cellNo = 0;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(userAddrDetail);

			cellNo = 11;
			rowNo = 9;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INVOICE_NO"));

			String invoiceNo = rstInvoiceInfo.get("INVOICE_NO").toString();

			cellNo = 11;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INV_TO_DATE"));

			cellNo = 15;
			rowNo = 10;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue((String) rstInvoiceInfo.get("INV_TO_DATE"));

			// Double totalValue =
			// Double.parseDouble(String.valueOf(rstInvoiceInfo.get("TOTAL_VALUE")));

			cellNo = 11;
			rowNo = 11;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(
					(String) rstInvoiceInfo.get("INV_FROM_DATE") + " - " + (String) rstInvoiceInfo.get("INV_TO_DATE"));

			ArrayList<HashMap<String, Object>> rstInvoicelist = new ArrayList<HashMap<String, Object>>();
			rstInvoicelist = mapper.selecInvoiceList(parameterInfo);

			rowNo = 14;
			// rowNo = 15;
			int RowNum = 1;

			Double totalDlvChg = 0.0;
			Double totalFscChg = 0.0;
			Double totalInspChg = 0.0;
			Double totalAddBlChg = 0.0;
			Double totalClearanceChg = 0.0;
			Double totalQuarantinChg = 0.0;
			Double totalExportChg = 0.0;
			Double totalPapChg = 0.0;
			Double totalSurChg = 0.0;
			Double totalEtcChg = 0.0;
			Double totalAmountChg = 0.0;
			Double totalWt = 0.0;
			int totalBoxCnt = 0;
			int totalHawbCnt = 0;
			Double totalEtcDetailChg = 0.0;

			for (int index = 0; index < rstInvoicelist.size(); index++) {
				row = sheet.createRow(rowNo);

				Double dlvChg = 0.0;
				Double fscChg = 0.0;
				Double inspChg = 0.0;
				Double addBlChg = 0.0;
				Double clearanceChg = 0.0;
				Double quarantinChg = 0.0;
				Double exportChg = 0.0;
				Double papChg = 0.0;
				Double etcChg = 0.0;
				Double surChg = 0.0;
				Double amountChg = 0.0;

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(RowNum);

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("DEP_DATE").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("HAWB_NO").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("ORDER_NO").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("CNEE_NAME").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("DSTN_NATION").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("APPLY_WT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoicelist.get(index).get("BOX_CNT").toString());

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(
						format.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG")))));
				// cell.setCellValue(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG"))));

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format.format(
							Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format.format(
							Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG")))));
				}

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG")))));
				}
				/*
				 * cellNo++; cell = row.createCell(cellNo); cell.setCellStyle(styleRight); if
				 * (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("SUR_CHG")))
				 * == 0) { cell.setCellValue("-"); } else {
				 * cell.setCellValue(format.format(Double.parseDouble(String.valueOf(
				 * rstInvoicelist.get(index).get("SUR_CHG"))))); }
				 */
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				if (Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG"))) == 0) {
					cell.setCellValue("-");
				} else {
					cell.setCellValue(format
							.format(Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG")))));
				}

				dlvChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("DLV_CHG")));
				fscChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("FSC_CHG")));
				inspChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("INSP_CHG")));
				addBlChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ADDBL_CHG")));
				clearanceChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("CLEARANCE_CHG")));
				quarantinChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("QUARANTINE_CHG")));
				exportChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("EXPORT_CHG")));
				papChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("PAP_CHG")));
				surChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("SUR_CHG")));
				etcChg = Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("ETC_CHG")));

				amountChg = dlvChg + fscChg + inspChg + addBlChg + clearanceChg + quarantinChg + exportChg + papChg
						+ surChg + etcChg;

				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(amountChg));
				// cell.setCellValue(amountChg);

				totalDlvChg = totalDlvChg + dlvChg;
				totalFscChg = totalFscChg + fscChg;
				totalInspChg = totalInspChg + inspChg;
				totalAddBlChg = totalAddBlChg + addBlChg;
				totalClearanceChg = totalClearanceChg + clearanceChg;
				totalQuarantinChg = totalQuarantinChg + quarantinChg;
				totalExportChg = totalExportChg + exportChg;
				totalPapChg = totalPapChg + papChg;
				totalEtcChg = totalEtcChg + etcChg;
				totalSurChg = totalSurChg + surChg;
				totalAmountChg = totalAmountChg + amountChg;

				RowNum++;
				rowNo++;

				totalHawbCnt = RowNum;
				totalBoxCnt = totalBoxCnt + Integer.parseInt(rstInvoicelist.get(index).get("BOX_CNT").toString());
				totalWt = totalWt + Double.parseDouble(String.valueOf(rstInvoicelist.get(index).get("APPLY_WT")));
			}

			ArrayList<HashMap<String, Object>> rstInvoiceEtclist = new ArrayList<HashMap<String, Object>>();
			rstInvoiceEtclist = mapper.selecInvoiceEtcList(parameterInfo);

			for (int i = 0; i < rstInvoiceEtclist.size(); i++) {
				row = sheet.createRow(rowNo);

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(RowNum);

				cellNo = 1;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue(rstInvoiceEtclist.get(i).get("INV_DATE").toString().replace("-", ""));

				String fromC = "C" + String.valueOf(rowNo + 1);
				String toF = "F" + String.valueOf(rowNo + 1);
				String mergeCell = fromC + ":" + toF;
				sheet.addMergedRegion(CellRangeAddress.valueOf(mergeCell));

				cellNo = 2;
				cell = row.createCell(cellNo);
				cell.setCellStyle(descStyle);
				cell.setCellValue(rstInvoiceEtclist.get(i).get("REMARK").toString());

				cellNo = 3;
				cell = row.createCell(cellNo);
				cell.setCellStyle(descStyle);

				cellNo = 4;
				cell = row.createCell(cellNo);
				cell.setCellStyle(descStyle);

				cellNo = 5;
				cell = row.createCell(cellNo);
				cell.setCellStyle(descStyle);

				cellNo = 6;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 7;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 8;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 9;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 10;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 11;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 12;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 13;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 14;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				cellNo = 15;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue("-");

				Double etcChg = 0.0;
				etcChg = Double.parseDouble(String.valueOf(rstInvoiceEtclist.get(i).get("INV_AMT")));

				cellNo = 16;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(etcChg));

				double amountChg = 0.0;
				amountChg = amountChg + etcChg;

				cellNo = 17;
				cell = row.createCell(cellNo);
				cell.setCellStyle(styleRight);
				cell.setCellValue(format.format(amountChg));

				totalAmountChg = totalAmountChg + amountChg;
				totalEtcChg = totalEtcChg + etcChg;

				rowNo++;
				RowNum++;

				totalHawbCnt = RowNum;

			}

			System.out.println(totalAmountChg);

			String merge1 = "A" + String.valueOf(rowNo + 1);
			String merge2 = "B" + String.valueOf(rowNo + 1);
			String merge = merge1 + ":" + merge2;

			sheet.addMergedRegion(CellRangeAddress.valueOf(merge));
			cellNo = 0;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(totalStyle);

			cellNo = 1;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 2;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalHawbCnt - 1);
			cell.setCellStyle(totalStyle);

			cellNo = 3;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 4;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 5;
			cell = row.createCell(cellNo);
			cell.setCellStyle(totalStyle);

			cellNo = 6;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalWt);
			cell.setCellStyle(totalStyle);

			cellNo = 7;
			cell = row.createCell(cellNo);
			cell.setCellValue(totalBoxCnt);
			cell.setCellStyle(totalStyle);

			cellNo = 8;
			cell = row.createCell(cellNo);
			cell.setCellValue(format.format(totalDlvChg));
			cell.setCellStyle(totalStyle2);

			cellNo = 9;
			cell = row.createCell(cellNo);
			if (totalFscChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalFscChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 10;
			cell = row.createCell(cellNo);
			if (totalInspChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalInspChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 11;
			cell = row.createCell(cellNo);
			if (totalAddBlChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalAddBlChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 12;
			cell = row.createCell(cellNo);
			if (totalClearanceChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalClearanceChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 13;
			cell = row.createCell(cellNo);
			if (totalQuarantinChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalQuarantinChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 14;
			cell = row.createCell(cellNo);
			if (totalExportChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalExportChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 15;
			cell = row.createCell(cellNo);
			if (totalPapChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalPapChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 16;
			cell = row.createCell(cellNo);
			if (totalEtcChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalEtcChg));
			}
			cell.setCellStyle(totalStyle2);

			cellNo = 17;
			cell = row.createCell(cellNo);
			if (totalAmountChg == 0) {
				cell.setCellValue("-");
			} else {
				cell.setCellValue(format.format(totalAmountChg));
			}
			cell.setCellStyle(totalStyle2);

			rowNo++;

			Double vatChg = 0.0;
			vatChg = totalAmountChg * 0.1;

			row = sheet.createRow(rowNo);
			cellNo = 16;
			cell = row.createCell(cellNo);
			cell.setCellValue("VAT");
			cell.setCellStyle(totalStyle);

			cellNo = 17;
			cell = row.createCell(cellNo);
			cell.setCellValue(format.format(vatChg));
			cell.setCellStyle(totalStyle2);

			rowNo++;
			Double totalChg = totalAmountChg + vatChg;

			row = sheet.createRow(rowNo);
			cellNo = 16;
			cell = row.createCell(cellNo);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(cellStyle);

			cellNo = 17;
			cell = row.createCell(cellNo);
			cell.setCellValue(format.format(totalChg));
			cell.setCellStyle(cellStyle2);

			rowNo++;
			rowNo++;
			rowNo++;
			System.out.println(rowNo);
			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("* 무통장 입금 또는 인터넷 뱅킹으로 입금하실 경우, 반드시 (주)에이씨아이월드와이드 시스템에 등록된");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("BANK INFORMATION : ");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("  상호로 입금하여 주시기 바랍니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("은행명 : 국민은행");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("  다른 명의로 입금하시어 확인이 불가능할 경우, 지속적으로 미결처리 되실 수 있음을 알려드립니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("계좌번호 : 649337-04-000571");
			cell.setCellStyle(cellStyle3);

			rowNo++;

			cellNo = 1;
			row = sheet.createRow(rowNo);
			cell = row.createCell(cellNo);
			cell.setCellValue("** 당월 기타비용 추가 발생 시 익월 내역서에 추가청구 드리겠습니다.");

			cellNo = 13;
			cell = row.createCell(cellNo);
			cell.setCellValue("예금주 : (주)에이씨아이월드와이드");
			cell.setCellStyle(cellStyle3);

			CellStyle formatCs = wb.createCellStyle();
			formatCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("_($*#,##0_);_($*(#,##0);_($* \"-\"_);_(@_)"));

			cellNo = 15;
			rowNo = 11;
			row = sheet.getRow(rowNo);
			cell = row.getCell(cellNo);
			cell.setCellValue(totalChg + totalEtcDetailChg);
			cell.setCellStyle(formatCs);

			// 컨텐츠 타입과 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + invoiceNo + ".xlsx");

			// 엑셀 출력
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTransCodeList(String orgStation) throws Exception {
		return mapper.selectTransCodeList(orgStation);
	}

	@Override
	public void selectClearanceExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			String startDate = request.getParameter("wDate");
			String endDate = request.getParameter("wDate");
			ArrayList<HashMap<String, Object>> clearanceList = new ArrayList<HashMap<String, Object>>();
			clearanceList = mapper.selectClearance(startDate, endDate);
			
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/invoice/excelSample/clearance_sample.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
			
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			
			int rowNo = 1;
			
			Font font = wb.createFont();
			font.setFontName("NanumGothic");
			font.setFontHeightInPoints((short)9);
			
			CellStyle style = wb.createCellStyle();
			style.setWrapText(false);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(font);
			
			for (int i = 0; i < clearanceList.size(); i++) {
				row = sheet.createRow(rowNo);
				
				cell = row.createCell(0);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(clearanceList.get(i).get("userId").toString());
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(clearanceList.get(i).get("hawbNo").toString());
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(clearanceList.get(i).get("agencyBl").toString());
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(clearanceList.get(i).get("wDate").toString());
				cell.setCellStyle(style);
				
				rowNo++;
			}
			
			String fileName = "통관비 등록 내역 "+startDate+".xlsx";
			String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+outputFileName);
			
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	@Override
	public void deleteTakeinEtc(HashMap<String, Object> parameterInfo) {
		mapper.deleteTakeinEtc(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectPriceApplyDataList(HashMap<String, Object> parameters) {
		return mapper.selectPriceApplyDataList(parameters);
	}

}