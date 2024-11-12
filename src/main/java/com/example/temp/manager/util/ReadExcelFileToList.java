package com.example.temp.manager.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.temp.api.aci.vo.ReturnExcelVO;




public class ReadExcelFileToList {
	
	public static List<ReturnExcelVO> readExcelData(String fileName) {

    List<ReturnExcelVO> list = new ArrayList<ReturnExcelVO>();

    try {
    	
        FileInputStream fis = new FileInputStream(fileName);
      
       
        Workbook workbook = null;
        
        if(fileName.toLowerCase().endsWith("xlsx")){
        
            workbook = new XSSFWorkbook(fis);
        
        }else if(fileName.toLowerCase().endsWith("xls")){
        
            workbook = new HSSFWorkbook(fis);
        }
         
        int numberOfSheets = workbook.getNumberOfSheets();
     
       
        for(int i=0; i < numberOfSheets; i++){
           
            Sheet sheet = workbook.getSheetAt(i);
           
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) 
            {
            	String nno = "";
            	String pickupName = "";
            	String pickupTel = "";
            	String pickupHp = "";
            	String pickupZip = "";
            	String pickupAddr = "";
            	String pickupAddrDetail = "";
            	
            	
            	
                Row row = rowIterator.next();

                
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) 
                {

                   
                    Cell cell = cellIterator.next();

                    
                    switch(cell.getCellType()){
                 
                    case Cell.CELL_TYPE_STRING:
                        if(nno.equalsIgnoreCase("")){

                        	nno = cell.getStringCellValue().trim();
                        	

                        }else if(pickupName.equalsIgnoreCase("")){

                        	pickupName = cell.getStringCellValue().trim();
                        	
                        }else if(pickupTel.equalsIgnoreCase("")){

                        	pickupTel = cell.getStringCellValue().trim();
                        	
                        }else if(pickupHp.equalsIgnoreCase("")){

                        	pickupHp = cell.getStringCellValue().trim();
                        	
                        }else if(pickupZip.equalsIgnoreCase("")){

                        	pickupZip = cell.getStringCellValue().trim();
                        	
                        }else if(pickupAddr.equalsIgnoreCase("")){

                        	pickupAddr = cell.getStringCellValue().trim();
                        	
                        }else if(pickupAddrDetail.equalsIgnoreCase("")){
 
                        	pickupAddrDetail = cell.getStringCellValue().trim();
                        	
                        }else{
                           System.out.println("X");
                           System.out.println("Random data::"+cell.getStringCellValue());
                        }
                        break;
                        
                    case Cell.CELL_TYPE_NUMERIC: 

                    	if(nno.equalsIgnoreCase("")){
                    		nno = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupName.equalsIgnoreCase("")){

                    	 pickupName = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupTel.equalsIgnoreCase("")){

                    	 pickupTel = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupHp.equalsIgnoreCase("")){

                    	 pickupHp = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupZip.equalsIgnoreCase("")){

                    	 pickupZip = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupAddr.equalsIgnoreCase("")){

                     	pickupAddr = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else if(pickupAddrDetail.equalsIgnoreCase("")){

                    	 pickupAddrDetail = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
                     	
                     }else{
                     	 System.out.println("X");
                         System.out.println("Random data::"+cell.getNumericCellValue());
                     }
				  break;
				  
                    }
                } 

               nno=nno.replace(".0","");

               ReturnExcelVO vo = new ReturnExcelVO();

               vo.setPickupName(pickupName);
               vo.setPickupTel(pickupTel);
               vo.setPickupHp(pickupHp);
               vo.setPickupZip(pickupZip);
               vo.setPickupAddr(pickupAddr);
               vo.setPickupAddrDetail(pickupAddrDetail);
               
               
               list.add(vo);
               System.out.println("=-==================");
               System.out.println(list.size());
               System.out.println("=-==================");

            }
             
             
        } 

       
        fis.close();
         
    } catch (IOException e) {
        e.printStackTrace();
    }
     

    return list;
}



}
