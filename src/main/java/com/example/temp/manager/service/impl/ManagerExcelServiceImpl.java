package com.example.temp.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.mapper.ManagerExcelMapper;
import com.example.temp.manager.service.ManagerExcelService;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.UmanifastExcelVO;

@Service
@Transactional
public class ManagerExcelServiceImpl implements ManagerExcelService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ManagerExcelMapper mapper;
	
	@Value("${filePath}")
    String realFilePath;

	@Override
	public String excelUediDown(String mawbNo, HttpServletResponse response, HttpServletRequest request) throws Exception{
		try {
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/adm/rls/mawbList/sampleExcel/umanifast.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
			
		    Workbook wb = WorkbookFactory.create(fis);
		    Sheet sheet = wb.getSheetAt(0);
		 
		    Row row = null;
		    Cell cell = null;
		    
		    ArrayList<UmanifastExcelVO> umanifastExcel = new ArrayList<UmanifastExcelVO>();
		    ArrayList<MawbVO> mawbinfo = new ArrayList<MawbVO>();
		    
		    HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		    
		    parameterInfo.put("mawbNo", mawbNo);
		    parameterInfo.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));			
		    
		    mawbinfo = mapper.selectMawbInfo(parameterInfo);
		   
		    sheet.getRow(0).getCell(1).setCellValue(mawbinfo.get(0).getDepDate());
		    sheet.getRow(1).getCell(1).setCellValue(mawbinfo.get(0).getFltNo());
		    sheet.getRow(2).getCell(1).setCellValue(mawbinfo.get(0).getDstnStation());
		    sheet.getRow(3).getCell(1).setCellValue(mawbinfo.get(0).getMawbNo());
		    
		    int rowNo = 6;
		
			umanifastExcel = mapper.selectUmaniFastExcel(parameterInfo);
			
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
			 font.setFontHeightInPoints((short)10);
			 style.setFont(font);
			 styleLeft.setFont(font);
			 styleRight.setFont(font);
			
			rowNo = 6;
			
			for(int i= 0 ; i < umanifastExcel.size(); i++) {
				
				umanifastExcel.get(i).dncryptData();
	
				row = sheet.createRow(rowNo);
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getHawbNo());
				
				cell = row.createCell(1);
				cell.setCellStyle(styleRight);
				cell.setCellValue(umanifastExcel.get(i).getBoxCnt());
				
				cell = row.createCell(2);
				cell.setCellStyle(styleRight);
				cell.setCellValue(umanifastExcel.get(i).getWta());
				
				cell = row.createCell(3);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getCurrency());
	
				cell = row.createCell(4);
				cell.setCellStyle(styleRight);
				cell.setCellValue(umanifastExcel.get(i).getTotalValue());
				
				cell = row.createCell(5);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getItemDetail());

				cell = row.createCell(6);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getShipperName());
				
				cell = row.createCell(7);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getShipperAddr());
				
				cell = row.createCell(8);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getNationCode());
				
				cell = row.createCell(9);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(10);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(11);
				cell.setCellStyle(style);
				cell.setCellValue("");

				cell = row.createCell(12);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getCneeName());
				
				cell = row.createCell(13);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getCneeAddr());
				
				cell = row.createCell(14);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(15);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(16);
				cell.setCellStyle(style);
				cell.setCellValue("");

				cell = row.createCell(17);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(18);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(19);
				cell.setCellStyle(style);
				cell.setCellValue("");
					
				cell = row.createCell(20);
				cell.setCellStyle(style);
				//cell.setCellValue("AE");
				cell.setCellValue("");

				cell = row.createCell(21);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(umanifastExcel.get(i).getItemDetail());
				
				cell = row.createCell(22);
				cell.setCellStyle(style);
				cell.setCellValue("");
				
				cell = row.createCell(23);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getItemDetail());
				
				cell = row.createCell(24);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getExpBusinessNum());
				
				cell = row.createCell(25);
				cell.setCellStyle(style);
				cell.setCellValue("B");
				
				cell = row.createCell(26);
				cell.setCellStyle(style);
				cell.setCellValue(umanifastExcel.get(i).getHsCode());

				rowNo++;
				
				HashMap<String,Object> uHanExpInfo = mapper.selectUhanExplicence(umanifastExcel.get(i).getHawbNo());
				if(!uHanExpInfo.get("expNo").equals("")) {
					row = sheet.createRow(rowNo);
					cell = row.createCell(5);
					cell.setCellStyle(style);
					cell.setCellValue("01");
					
					cell = row.createCell(6);
					cell.setCellStyle(style);
					cell.setCellValue(uHanExpInfo.get("expNo").toString());
					
					cell = row.createCell(7);
					cell.setCellStyle(style);
					cell.setCellValue(uHanExpInfo.get("boxCnt").toString());
					
					cell = row.createCell(8);
					cell.setCellStyle(style);
					cell.setCellValue(uHanExpInfo.get("wta").toString());
					rowNo++;
				}
				
			}
			
		    // 컨텐츠 타입과 파일명 지정
		    response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename=manifast_"+mawbNo+".xlsx");
		    
		    
		    // 엑셀 출력 
		    OutputStream out = new BufferedOutputStream(response.getOutputStream());
		    wb.write(out);
	        out.flush();
	        out.close();
	        return "S";
        }catch (Exception e) {
        	logger.error("Exception", e);
        	return "F";
		}
	}

	@Override
	public String rusExcelDown(String parameter, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String,Object>> columnName = new ArrayList<HashMap<String,Object>>();
		ArrayList<OrderInspListVO> rusOrder = new ArrayList<OrderInspListVO>(); 
		LocalDate currentDate = LocalDate.now();
		String savePath = realFilePath + "excel/expLicence/";

		String filename = "RUS_FORM_"+currentDate+".xlsx" ;
		
		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		
		
		Sheet sheet1 = xlsxWb.createSheet("RUS_D2D");
		Sheet sheet2 = xlsxWb.createSheet("RUS_POD");
		
		//HEAD 스타일 설정 START
		CellStyle cellStyleHeader = xlsxWb.createCellStyle();
		cellStyleHeader.setWrapText(false);
		cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//HEAD 스타일 설정 END
		
		
		//D2D Sheet
		//ROW-CELL 선언 START
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		int cellCnt = 0;
		//ROW-CELL 선언 END
		
		//SHEET 생성 
		row = sheet1.createRow(rowCnt);
		
		//HEADER 생성 START
		
		
		columnName = mapper.selectExcelColumnName("RUS_FORM_D2D");
		for(int i = 0; i<columnName.size(); i++) {
			cell = row.createCell(cellCnt);
			cell.setCellValue(columnName.get(i).get("COL_NAME").toString());
			cell.setCellStyle(cellStyleHeader);
			sheet1.setColumnWidth(cellCnt, 8000);
			cellCnt++;
			
		}
		rowCnt++;
		
		//HEADER 생성 END
		
		//BODY CREATE START
		
		rusOrder = mapper.selectRusOrder(request.getParameter("mawbNo"),"1");
		
		for(int i = 0 ; i < rusOrder.size(); i++) {
			cellCnt = 0;
			row = sheet1.createRow(rowCnt);
			rusOrder.get(i).dncryptData();

			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getHawbNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeName());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeAddr()+ " "+ rusOrder.get(i).getCneeAddrDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeCity());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeState());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeZip());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getDstnNation());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getBoxCnt());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getInBoxNum());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getUserWta());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemWta());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemCnt());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getUnitValue());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getChgCurrency());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getMakeCntry());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getHsCode());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemExplan());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemUrl());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeEmail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			if(!rusOrder.get(i).getCneeHp().equals("")) {
				cell.setCellValue(rusOrder.get(i).getCneeHp());
			}else {
				cell.setCellValue(rusOrder.get(i).getCneeTel());
			}
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCustomsNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getNationalIdAuthority());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getTaxNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperName());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getOrgNation());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperCity());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperState());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperAddr()+" "+rusOrder.get(i).getShipperAddrDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperZip());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemBarcode());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(request.getParameter("mawbNo"));
			cellCnt++;
			rowCnt++;
		}
		
		
		//RUS_POD Sheet
		rowCnt = 0;
		cellCnt = 0;
		row = sheet2.createRow(rowCnt);
		
		columnName = mapper.selectExcelColumnName("RUS_FORM_POD");
		for(int i = 0; i<columnName.size(); i++) {
			cell = row.createCell(cellCnt);
			cell.setCellValue(columnName.get(i).get("COL_NAME").toString());
			cell.setCellStyle(cellStyleHeader);
			sheet2.setColumnWidth(cellCnt, 8000);
			cellCnt++;
			
		}
		rowCnt++;
		
		rusOrder = mapper.selectRusOrder(request.getParameter("mawbNo"),"2");
		
		for(int i = 0 ; i < rusOrder.size(); i++) {
			cellCnt = 0;
			row = sheet2.createRow(rowCnt);
			rusOrder.get(i).dncryptData();

			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getHawbNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeName());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeAddr());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeAddrDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeCity());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeState());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeZip());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getDstnNation());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getBoxCnt());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getUserWta());
			cellCnt++;
						
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getUnitValue());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getMakeCntry());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getHsCode());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemWta());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemCnt());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemExplan());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeEmail());
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemUrl());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCneeTel());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getItemBarcode());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getInBoxNum());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("POD");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getUserWta());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperName());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getOrgNation());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperCity());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperState());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperAddr()+" "+rusOrder.get(i).getShipperAddrDetail());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getShipperZip());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getOrgNation());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getCustomsNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getNationalIdDate());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getNativeCneeName());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(request.getParameter("mawbNo"));
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getNationalIdAuthority());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getTaxNo());
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(rusOrder.get(i).getChgCurrency());
			cellCnt++;
			
			rowCnt++;
		}
		
		try {
			try {
	            File xlsFile = new File(savePath+filename);
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
	        try{
	            file = new File(savePath, filename);
	            in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }
	        
	        client = request.getHeader("User-Agent");
	   	 
	        // 파일 다운로드 헤더 지정
	        response.reset() ;
	        response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename="+filename);
	 
		    if(!skip){
	            // IE
	            if(client.indexOf("MSIE") != -1){
	                response.setHeader ("Content-Disposition", "attachment; filename="+new String(filename.getBytes("KSC5601"),"ISO8859_1"));
	            }else{
	                // 한글 파일명 처리
	                filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
	                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	            } 
	            response.setHeader ("Content-Length", ""+file.length() );
	            os = response.getOutputStream();
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;
	            while( (leng = in.read(b)) > 0 ){
	                os.write(b,0,leng);
	            }
	        }else{
	            response.setContentType("text/html;charset=UTF-8");
	        }
		    
	        os.close();
	        in.close();
	        file.delete();
		}catch(Exception e){
	      logger.error("Exception", e);
	    }
		
		
		
		return null;
	}


}
