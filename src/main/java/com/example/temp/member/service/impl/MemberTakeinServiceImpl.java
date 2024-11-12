package com.example.temp.member.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.formula.functions.Vlookup;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.aci.service.impl.ApiServiceImpl;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.service.impl.ComnServiceImpl;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.mapper.MemberTakeinMapper;
import com.example.temp.member.service.MemberTakeinService;
import com.example.temp.member.vo.CustomerVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.takein.OrgStationVO;
import com.example.temp.member.vo.takein.TakeInUserStockOutVO;
import com.example.temp.member.vo.takein.TakeinInfoVO;
import com.example.temp.member.vo.takein.TakeinUserStockListVO;
import com.example.temp.member.vo.takein.TmpTakeinCodeVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemListVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderItemVO;
import com.example.temp.member.vo.takein.TmpTakeinOrderVO;
import com.example.temp.member.vo.takein.UserTransVO;
import com.spire.xls.HyperLink;

@Service
@Transactional
public class MemberTakeinServiceImpl implements MemberTakeinService{
	
	String pattern = "^[0-9]*$"; //숫자만
	String patternFloat = "^[0-9.]+([0-9])*$"; //숫자만
	
	@Autowired
	private MemberTakeinMapper mapper;
	@Autowired
	private ComnService commMapper;

	@Autowired
	private MemberMapper usrMapper;
	
	@Autowired
	private ApiServiceImpl apiServiceImpl;
	
	@Autowired
	private ComnServiceImpl comnServiceImpl;
	
	@Autowired
	private ComnMapper comnMapper;


	@Override
	public String insertTakeinExcelUpload(MultipartHttpServletRequest multi, HashMap<String, String> parameters) {
		return null;
	}

	@Override
	public void deleteTmptakeinUserList(HashMap<String, String> deleteNno) {
		mapper.deleteTmptakeinUserList(deleteNno);
		mapper.deleteExplicenceInfo(deleteNno);
	}

	@Override
	public String insertTmpTakeinExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, String> parameters) throws Exception {
	
		String result="F";
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String transCode = "";
		HashMap<String,Object> transParameter = new HashMap<String,Object>(); 
		
		transParameter.put("userId", request.getSession().getAttribute("USER_ID"));
		

		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		
		CustomerVO customerInfo = new CustomerVO();

		HashMap<String,String> userId = new HashMap<String,String>();
		userId.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		
		customerInfo = mapper.selectCustomInfo(userId);
		customerInfo.dncryptData();

		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					
					String chkOrderNo = "";
					String chkNno = "";
					String chkOrgStaion="";
					String chkDstnStaion="";
					int setSubNo = 1;
					String iossNo = "";
					
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작

						TmpTakeinOrderVO  takeinInfo = new TmpTakeinOrderVO();
						TmpTakeinOrderItemVO  takeinItem = new TmpTakeinOrderItemVO();
						ExpLicenceVO licence = new ExpLicenceVO();
						ExportDeclare expInfo = new ExportDeclare();
						boolean rowEmpty = true;
						XSSFRow rowPivot = sheet.getRow(0);
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							for (int cellNum = rowPivot.getFirstCellNum(); cellNum < rowPivot.getLastCellNum(); cellNum++) {
								XSSFCell cell = row.getCell(cellNum);
								if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && !"".equals(cell.toString())) {
									rowEmpty = false;
									break;
								}
							}
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							String rowResult="Err!:";
		
							for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++)  {
								int valSize = 0;
								Date times2 = new Date();
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.	
								if(cell==null) {
									value="";
								}else {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											if(DateUtil.isCellDateFormatted(cell)) {
												Date date = cell.getDateCellValue();
												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											}else {
												cell.setCellType(HSSFCell.CELL_TYPE_STRING );
												value = cell.getStringCellValue();
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
								
								try {
									String orgCellName = rowPivot.getCell(columnIndex).getStringCellValue().replaceAll("line.separator".toString(), ""); 
									String cellName = orgCellName.replaceAll(" ", "").toLowerCase();
									
									switch (cellName) {
										case "주문번호":
											takeinInfo.setOrderNo(value);
										break;
										case "주문날짜":
											takeinInfo.setOrderDate(value);
										break;
										case "출발도시코드":
											takeinInfo.setOrgStation(value);
											transParameter.put("orgStation", value);
										break;
										case "도착국가":
											takeinInfo.setDstnNation(value);
											transParameter.put("dstnNation", value);
										break;
										case "payment":
											if(value.equals("")) {
												takeinInfo.setPayment("DDU");
											} else {
												takeinInfo.setPayment(value.toUpperCase());
											}
										break;
										case "송하인명":
											if(value.equals("")) {
												if (takeinInfo.getDstnNation().replaceAll(" ", "").toLowerCase().equals("southkorea")) {
													takeinInfo.setShipperName(customerInfo.getComName());
												} else {
													takeinInfo.setShipperName(customerInfo.getComEName());
												}
											}else {
												takeinInfo.setShipperName(value);
											}
										break;
										case "송하인전화번호":
											if(value.equals("")) {
												takeinInfo.setShipperTel(customerInfo.getUserTel());
											}else {
												takeinInfo.setShipperTel(value);
											}
											
										break;
										case "송하인핸드폰번호":
											if(value.equals("")) {
												takeinInfo.setShipperHp(customerInfo.getUserHp());
											}else {
												takeinInfo.setShipperHp(value);
											}
										break;
										case "송하인우편주소":
											if(value.equals("")) {
												takeinInfo.setShipperZip(customerInfo.getUserZip());
											}else {
												takeinInfo.setShipperZip(value);
											}
										break;
										case "송하인주":
											takeinInfo.setShipperState(value);
										break;
										case "송하인도시":
											takeinInfo.setShipperCity(value);
										break;
										case "송하인주소":
											if(value.equals("")) {
												if (takeinInfo.getDstnNation().replaceAll(" ", "").toLowerCase().equals("southkorea")) {
													takeinInfo.setShipperAddr(customerInfo.getUserAddr());
												} else {
													takeinInfo.setShipperAddr(customerInfo.getUserEAddr());
												}
											}else {
												takeinInfo.setShipperAddr(value);
											}
										break;
										case "송하인상세주소":
											if(value.equals("")) {
												if (takeinInfo.getDstnNation().replaceAll(" ", "").toLowerCase().equals("southkorea")) {
													takeinInfo.setShipperAddrDetail(customerInfo.getUserAddrDetail());
												} else {
													takeinInfo.setShipperAddrDetail(customerInfo.getUserEAddrDetail());
												}
											}else{
												takeinInfo.setShipperAddrDetail(value);
											}
										break;
										case "송하인email":
											if(value.equals("")) {
												takeinInfo.setUserEmail(customerInfo.getUserEmail());
											}else {
												takeinInfo.setUserEmail(value);
											}
										break;
										case "송하인세금식별코드":
											if(!"".equals(value)) {

												try {
													int shipperTaxType = Integer.parseInt(value);
													if (shipperTaxType < 0 || shipperTaxType > 12) {
														takeinInfo.setStatus(takeinInfo.getStatus()+"송하인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														takeinInfo.setShipperTaxType(value);
													}
												} catch (NumberFormatException e) {
													takeinInfo.setStatus(takeinInfo.getStatus()+"송하인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												takeinInfo.setShipperTaxType("0");
											}
										break;
										case "송하인세금식별번호":
											takeinInfo.setShipperTaxNo(value);
										break;	
										case "수취인명":
											takeinInfo.setCneeName(value);
										break;
										case "수취인일본명":
											takeinInfo.setNativeCneeName(value);
										break;
										case "수취인현지이름":
											takeinInfo.setNativeCneeName(value);
										break;
										case "수취인전화번호":
											takeinInfo.setCneeTel(value);
										break;
										case "수취인핸드폰번호":
											takeinInfo.setCneeHp(value);
										break;
										case "수취인우편번호":
											takeinInfo.setCneeZip(value);
										break;
										case "수취인주":
											takeinInfo.setCneeState(value);
										break;
										case "수취인도시":
											takeinInfo.setCneeCity(value);
										break;
										case "수취인구역":
											takeinInfo.setCneeDistrict(value);
										break;
										case "수취인구":
											takeinInfo.setCneeWard(value);
										break;
										case "수취인주소":
											takeinInfo.setCneeAddr(value);
										break;
										case "수취인일본주소":
											takeinInfo.setNativeCneeAddr(value);
											break;
										case "수취인현지주소":
											takeinInfo.setNativeCneeAddr(value);
											break;
										case "수취인상세주소":
											takeinInfo.setCneeAddrDetail(value);
										break;
										case "수취인일본상세주소":
											takeinInfo.setNativeCneeAddrDetail(value);
											break;
										case "수취인현지상세주소":
											takeinInfo.setNativeCneeAddrDetail(value);
											break;
										case "수취인email":
											takeinInfo.setCneeEmail(value);
										break;
										case "수취인세금식별코드":
											if(!"".equals(value)) {

												try {
													int cneeTaxType = Integer.parseInt(value);
													if (cneeTaxType < 0 || cneeTaxType > 12) {
														takeinInfo.setStatus(takeinInfo.getStatus()+"수취인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														takeinInfo.setCneeTaxType(value);
													}
												} catch (NumberFormatException e) {
													takeinInfo.setStatus(takeinInfo.getStatus()+"송하인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												takeinInfo.setCneeTaxType("0");
											}
										break;
										case "수취인세금식별번호":
											takeinInfo.setCneeTaxNo(value);
										break;
										case "수취인요청사항":
											takeinInfo.setDlvReqMsg(value);
										break;
										case "요청사항":
											takeinInfo.setWhReqMsg(value);
										break;
										case "화장품포함여부":
											if(!"".equals(value)) {
												String cosmeticYn = value.toUpperCase();
												switch (cosmeticYn) {
												case "Y":
													value = "Y";
													break;
												case "N":
													value = "N";
												break;
												default:
													value = "N";
												break;
												}
												takeinInfo.setCosmetic(value);
											} else {
												takeinInfo.setCosmetic("N");
											}
										break;
										case "대면서명여부":
											if(!"".equals(value)) {
												String signYn = value.toUpperCase();
												switch (signYn) {
												case "Y":
													value = "Y";
													break;
												case "N":
													value = "N";
												break;
												default:
													value = "N";
												break;
												}
												takeinInfo.setSign(value);
											} else {
												takeinInfo.setSign("N");
											}
										break;
										case "수출신고구분":
											if (!value.equals("")) {
												if (value.toUpperCase().equals("S") || value.toUpperCase().equals("F") || value.toUpperCase().equals("E")) {
													expInfo.setExpType(value);	
												} else {
													expInfo.setExpType("N");
												}
												
											} else {
												expInfo.setExpType("N");
											}
										break;
										case "수출화주사업자상호명":
											expInfo.setExpCor(value);
										break;
										case "수출화주사업자대표명":
											expInfo.setExpRprsn(value);
											break;
										case "수출화주사업자번호":
											expInfo.setExpRgstrNo(value);
											break;
										case "수출화주사업자주소":
											expInfo.setExpAddr(value);
											break;
										case "수출화주사업자우편번호":
											expInfo.setExpZip(value);
											break;
										case "수출화주통관부호":
											expInfo.setExpCstCd(value);
											break;
										case "수출대행자상호명":
											expInfo.setAgtCor(value);
											break;
										case "수출대행자사업장일련번호":
											expInfo.setAgtBizNo(value);
											break;
										case "수출대행자통관부호":
											expInfo.setAgtCstCd(value);
											break;
										case "수출면장번호":
											expInfo.setExpNo(value);
											break;
										case "구매사이트":
											takeinInfo.setBuySite(value);
										break;
										case "수취인id번호":
											takeinInfo.setCustomsNo(value);
										break;
										case "상품코드":
											takeinItem.setCusItemCode(value);
										break;
										case "수량":
											if(value.replaceAll("[^0-9]","").equals("")) {
												value ="0";
											}
											takeinItem.setItemCnt(value);
										break;
										case "단가":
											boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
											if(!regex) {
												takeinItem.setUnitValue("");
											}else {
												takeinItem.setUnitValue(value);
											}
										break;
										case "화폐단위":
											takeinItem.setChgCurrency(value);
										break;
										case "자국상품명":
											takeinItem.setNativeItemDetail(value);
										break;
										default:
										break;
									}
								} catch (Exception e) {
									e.printStackTrace();
									
								}
							}

							takeinInfo.setUploadType("EXCEL");
							takeinInfo.setTransCode(commMapper.selectUserTransCode(transParameter));

							if(takeinItem.getCusItemCode().equals("")) {
								break;
							}

							if(takeinInfo.getOrderNo().equals("")||takeinInfo.getOrderNo().equals(chkOrderNo)) {
								
								takeinInfo.setOrderNo(chkOrderNo);
								takeinInfo.setNno(chkNno);
								takeinInfo.setOrgStation(chkOrgStaion);
								takeinInfo.setDstnNation(chkDstnStaion);
								setSubNo++;
								
							}else {

								String newNno = "";
								newNno = mapper.selectNNO();
								takeinInfo.setNno(newNno);
								takeinInfo.setUserId((String)request.getSession().getAttribute("USER_ID"));
								takeinInfo.setWUserId((String)request.getSession().getAttribute("USER_ID"));
								takeinInfo.setWUserIp(request.getRemoteAddr());
								takeinInfo.setOrderType("TAKEIN");
								takeinInfo.encryptData();
								
								HashMap<String, String> transComChangeParameters = new HashMap<String, String>();
								transComChangeParameters.put("nno", newNno);
								transComChangeParameters.put("orgStation", takeinInfo.getOrgStation());
								transComChangeParameters.put("dstnNation", takeinInfo.getDstnNation());
								transComChangeParameters.put("userId", (String)request.getSession().getAttribute("USER_ID"));
								transComChangeParameters.put("transCode", takeinInfo.getTransCode());
								transComChangeParameters.put("wta", "1");
								transComChangeParameters.put("wtc", "1");
								transComChangeParameters.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transComChangeParameters.put("wUserIp", request.getRemoteAddr());
								HashMap<String, String> transComChangeRs = new HashMap<String, String>();
								transComChangeRs = commMapper.selectTransComChange(transComChangeParameters);
								takeinInfo.setTransCode(transComChangeRs.get("rstTransCode"));
								
								mapper.insertTmpTakeinOrder(takeinInfo);
								setSubNo = 1;
								
								if (expInfo.getExpType().equals("") || expInfo.getExpType() == null) {
									
								} else {
									if (!expInfo.getExpType().equals("N")) {
										
										if (expInfo.getExpType().toUpperCase().equals("E")
												|| expInfo.getExpType().toUpperCase().equals("S")
												|| expInfo.getExpType().toUpperCase().equals("F")) {

											expInfo.setNno(takeinInfo.getNno());
											expInfo.setUserId(takeinInfo.getUserId());
											expInfo.setSendYn("N");
											expInfo.encryptData();
											
											comnMapper.insertExportDeclareInfo(expInfo);
										}
									}	
								}

							}

							String getSubNo = Integer.toString(setSubNo);
							takeinItem.setOrgStation(takeinInfo.getOrgStation());
							takeinItem.setNno(takeinInfo.getNno());
							takeinItem.setSubNo(getSubNo);
							takeinItem.setUserId((String)request.getSession().getAttribute("USER_ID"));
							takeinItem.setWUserId(takeinInfo.getWUserId());
							takeinItem.setWUserIp(takeinInfo.getWUserIp());
							mapper.insertTmpTakeinOrderItem(takeinItem);
							chkOrderNo = takeinInfo.getOrderNo();
							chkNno = takeinInfo.getNno();
							chkOrgStaion=takeinInfo.getOrgStation();
							chkDstnStaion=takeinInfo.getDstnNation();
							
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
				
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				delFile.delete();
			}
	
		 result = "등록되었습니다.";
		}
		
		return result;
	}
	
	
	public Map<String, String> filesUpload(MultipartHttpServletRequest multi, String uploadPaths) {
	    String path = uploadPaths; // 저장 경로 설정
	    
	    String newFileName = ""; // 업로드 되는 파일명
	    File dir = new File(path);
	    if(!dir.isDirectory()){
	        dir.mkdir();
	    }
	    
	    Iterator<String> files = multi.getFileNames();
	    Map<String, String> resMap = new HashMap<String, String>();
	    String fileName = "";
	    while(files.hasNext()){
	        String uploadFile = files.next();
	        MultipartFile mFile = multi.getFile(uploadFile);
	        fileName = mFile.getOriginalFilename();
	        newFileName = System.currentTimeMillis()+"."+fileName.substring(fileName.lastIndexOf(".")+1);
	        try {
	            mFile.transferTo(new File(path+newFileName));
	            resMap.put(newFileName, path+newFileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return resMap;
	}

	@Override
	public ArrayList<TmpTakeinOrderVO> selectTmpTakeinUserList(HashMap<String, String> parameterInfo) throws IOException {
		
		return mapper.selectTmpTakeinUserList(parameterInfo);
	}

	@Override
	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem(HashMap<String, String> itemParmeter) {
		return mapper.selectTmpTakeinUserOrderItem(itemParmeter);
	}

	@Override
	public void insertTakeinDlv(HashMap<String, String> insertNno) {
		mapper.insertTakeinDlv(insertNno);
	}



	@Override
	public ArrayList<TmpTakeinOrderVO> selectTakeinUserList(HashMap<String, Object> parameterInfo) { 
		return mapper.selectTakeinUserList(parameterInfo);
	}



	@Override
	public ArrayList<TmpTakeinOrderItemListVO> selectTakeinUserOrderItem(HashMap<String, String> itemParmeter) {

		return  mapper.selectTakeinUserOrderItem(itemParmeter);
	}



	@Override
	public int selectTotalCountTakeinList(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectTotalCountTakeinList(parameterInfo);
	}



	@Override
	public void deleteTakeinUserList(HashMap<String, String> deleteNno) {
		mapper.deleteTakeinUserList(deleteNno);
	}



	@Override
	public int selectDelTotalCountTakeinList(HashMap<String, Object> parameterInfo) {
		return mapper.selectDelTotalCountTakeinList(parameterInfo);
	}



	@Override
	public ArrayList<TmpTakeinOrderVO> selectDelTakeinUserList(HashMap<String, Object> parameterInfo) {

		return mapper.selectDelTakeinUserList(parameterInfo);
	}



	@Override
	public ArrayList<TmpTakeinOrderItemListVO> selectDelTakeinUserOrderItem(HashMap<String, String> itemParmeter) {
		return mapper.selectDelTakeinUserOrderItem(itemParmeter);
	}



	@Override
	public void recoveryTakeinUserList(HashMap<String, String> recoveryNno) {
	
		mapper.recoveryTakeinUserList(recoveryNno);
	}



	@Override
	public int selectUserTakeinCountTakeinList(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTakeinCountTakeinList(parameterInfo);
	}



	@Override
	public ArrayList<TakeinInfoVO> selectUserTakeInInfo(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectUserTakeInInfo(parameterInfo);
	}



	@Override
	public TakeinInfoVO selectTakeInCode(HashMap<String, Object> parameterInfo) {

		return mapper.selectTakeInCode(parameterInfo);
	}



	@Override
	public ArrayList<OrgStationVO> selectOrgStation(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectOrgStation(parameterInfo);
	}



	@Override
	public HashMap<String, Object> insertUserTakeinInfo(HashMap<String, Object> parameterInfo) {
		
		return mapper.insertUserTakeinInfo(parameterInfo);
	}



	@Override
	public HashMap<String, Object> updateUserTakeinInfo(HashMap<String, Object> parameterInfo) {
	
		return mapper.updateUserTakeinInfo(parameterInfo);
	}



	@Override
	public ArrayList<TmpTakeinCodeVO> selectUserTmpTakeInInfo(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTmpTakeInInfo(parameterInfo);
	}



	@Override
	public void deleteUserTakeinCode(HashMap<String, Object> parameterInfo) {
		mapper.deleteUserTakeinCode(parameterInfo);
	}
	
	
	@Override
	public String insertTmpTakeinInfoExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request,
			HashMap<String, String> parameters) throws IOException {
	
		String result="F";
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴

		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		
		CustomerVO customerInfo = new CustomerVO();

		HashMap<String,String> userId = new HashMap<String,String>();
		userId.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		
		customerInfo = mapper.selectCustomInfo(userId);
		customerInfo.dncryptData();

		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			try {
				FileInputStream fis = new FileInputStream(filePath);
				
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
	
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작

						TmpTakeinCodeVO  tmpTakeinCodeVO = new TmpTakeinCodeVO();						

						XSSFRow rowPivot = sheet.getRow(0);
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							int cells = rowPivot.getPhysicalNumberOfCells(); // 한 row당 cell개수
							String value = "";
							String rowResult="Err!:";
		
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) 
							{
								int valSize = 0;
								Date times2 = new Date();
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.	
								if(cell==null) {
									value="";
								}else {
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
							
											if( DateUtil.isCellDateFormatted(cell)) {
												Date date = cell.getDateCellValue();
												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											}else {
												cell.setCellType( HSSFCell.CELL_TYPE_STRING );
												value = cell.getStringCellValue();
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
								
								String strGetCell="";
								strGetCell = rowPivot.getCell(columnIndex).getStringCellValue();
								strGetCell = strGetCell.replaceAll(" ", "");
								switch (strGetCell){
									case "Station":
										tmpTakeinCodeVO.setOrgStation(value);
									break;
									case "상품코드":
										tmpTakeinCodeVO.setCusItemCode(value);
									break;
									case "Brand":
										tmpTakeinCodeVO.setBrand(value);
									break;
									case "HsCode":
										tmpTakeinCodeVO.setHsCode(value);
									break;
									case "상품명(영문)":
										tmpTakeinCodeVO.setItemDetail(value);
									break;
									case "상품명(자국어)":
										tmpTakeinCodeVO.setNativeItemDetail(value);
									break;
									case "상품옵션":
										tmpTakeinCodeVO.setItemOption(value);
									break;
									case "상품가격":
										tmpTakeinCodeVO.setUnitValue(value);
									break;
									case "화폐단위":
										tmpTakeinCodeVO.setUnitCurrency(value);
									break;
									case "상품구분":
										tmpTakeinCodeVO.setItemDiv(value);
									break;
									case "제조국":
										tmpTakeinCodeVO.setMakeCntry(value);
									break;
									case "제조사":
										tmpTakeinCodeVO.setMakeCom(value);
									break;
									case "상품제질":
										tmpTakeinCodeVO.setItemMeterial(value);
									break;
									case "상품무게":
										tmpTakeinCodeVO.setWta(value);
									break;
									case "무게단위":
										tmpTakeinCodeVO.setWtUnit(value);
									break;
									case "수량단위":
										tmpTakeinCodeVO.setQtyUnit(value);
									break;
									case "상품COLOR":
										tmpTakeinCodeVO.setItemColor(value);
									break;	
									case "상품SIZE":
										tmpTakeinCodeVO.setItemSize(value);
									break;
									case "상품Url":
										tmpTakeinCodeVO.setItemUrl(value);
									break;
									case "상품imgUrl":
										tmpTakeinCodeVO.setItemImgUrl(value);
									break;
								}
							}
							
							if(tmpTakeinCodeVO.getCusItemCode().equals("")) {
								break;
							}
							
							OrgStationVO orgStationVO = new OrgStationVO();
							HashMap<String,Object> parameterInfo = new HashMap<String,Object>();
							parameterInfo.put("orgStation", tmpTakeinCodeVO.getOrgStation());
							
							orgStationVO = mapper.selectOrgStationCheck(parameterInfo);
							tmpTakeinCodeVO.setOrgStation(orgStationVO.getStationCode());
							tmpTakeinCodeVO.setUserId((String)request.getSession().getAttribute("USER_ID"));
							tmpTakeinCodeVO.setWUserId((String)request.getSession().getAttribute("USER_ID"));
							tmpTakeinCodeVO.setWUserIp( request.getRemoteAddr());
							
							mapper.insertTmpTakeinInfo(tmpTakeinCodeVO);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
				
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = e.toString();
				return result;
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				delFile.delete();
			}
		 result = "등록되었습니다.";
		}
		return result;
	}



	@Override
	public TmpTakeinCodeVO selectTmpTakeinCode(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectTmpTakeinCode(parameterInfo);
	}



	@Override
	public void delteUserTmpTakeinCode(HashMap<String, Object> parameterInfo) {
		
		 mapper.delteUserTmpTakeinCode(parameterInfo);
	}



	@Override
	public ArrayList<TakeinUserStockListVO> selectTakeinUserStockList(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectTakeinUserStockList(parameterInfo);
	}



	@Override
	public int selectTakeinUserStockListCnt(HashMap<String, Object> parameterInfo) {

		return mapper.selectTakeinUserStockListCnt(parameterInfo);
	}

	@Override
	public ArrayList<UserTransVO> selectUserTransCom(HashMap<String, String> parameterInfo) {
		
		return mapper.selectUserTransCom(parameterInfo);
	}

	@Override
	public ArrayList<String> selectErrorMsg(String status) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectErrorMsg(status);
	}

	@Override
	public ArrayList<TakeInUserStockOutVO> selectTakeInUserStockOutList(HashMap<String, Object> parameterInfo) {
	
		return mapper.selectTakeInUserStockOutList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserTakeinStockInMonth(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTakeinStockInMonth(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectStockStationList(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectStockStationList(parameterInfo);
	}

	@Override
	public int selectUserTakeinStockInCnt(HashMap<String, Object> parameterInfo) {
	
		return mapper.selectUserTakeinStockInCnt(parameterInfo);
	}
	
	@Override
	public int selectUserTakeinStockOutDayCnt(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTakeinStockOutDayCnt(parameterInfo);
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> selectUserTakeinStockOutDay(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectUserTakeinStockOutDay(parameterInfo);
	}
	
	@Override
	public void takeinStockOutExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) {
	
		try {	
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = mapper.takeinStockOutExcelDown(parameterInfo);

			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/takein/excelSample/takeinStockOut.xlsx";
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
			 style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 CellStyle styleLeft = wb.createCellStyle();
			 styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
			 styleLeft.setBorderTop(CellStyle.BORDER_THIN);
			 styleLeft.setBorderBottom(CellStyle.BORDER_THIN);
			 styleLeft.setBorderLeft(CellStyle.BORDER_THIN);
			 styleLeft.setBorderRight(CellStyle.BORDER_THIN);
			 styleLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			 
			 CellStyle styleRight = wb.createCellStyle();
			 styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			 styleRight.setBorderTop(CellStyle.BORDER_THIN);
			 styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			 styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			 styleRight.setBorderRight(CellStyle.BORDER_THIN);
			 styleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 Font font = wb.createFont();
			 font.setFontHeightInPoints((short)10);
			 style.setFont(font);
			 styleLeft.setFont(font);
			 styleRight.setFont(font);
			 
			 
		     int cellNo = 0;
		     row = sheet.createRow(0);
				
			cellNo = 0;
			cell = row.createCell(cellNo);
			cell.setCellValue((String)parameterInfo.get("fromDate")+" ~ "+(String)parameterInfo.get("toDate"));
			cell.setCellStyle(styleLeft);

			rowNo = 2;
			
			for(int index = 0 ; index <listRst.size(); index++) {
				row = sheet.createRow(rowNo);
				
				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("stationName"));
				cell.setCellStyle(style);
		
				cellNo = 1;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("cusItemCode"));
				cell.setCellStyle(style);
				
				cellNo = 2;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("itemDetail"));
				cell.setCellStyle(styleLeft);
				
				cellNo = 3;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("outCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 4;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("stockCnt").toString()));
				cell.setCellStyle(styleRight);

				rowNo++;
			}
			
			String fileName= (String)parameterInfo.get("fromDate")+"_"+(String)parameterInfo.get("toDate")+"_"+(String)parameterInfo.get("userId");

		    response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
		    
		    OutputStream out = new BufferedOutputStream(response.getOutputStream());
		    wb.write(out);
	        out.flush();
	        out.close();
			
		   }catch (Exception e) {
	       		e.printStackTrace();
		   }
	}


	@Override
	public int selectInOutStockListCnt(HashMap<String, Object> parameterInfo) {
	
		return mapper.selectInOutStockListCnt(parameterInfo);
	}


	@Override
	public ArrayList<HashMap<String, Object>> selectInOutStockList(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectInOutStockList(parameterInfo);
	}

	@Override
	public int selectUserTakeinOrderStockOutCnt(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTakeinOrderStockOutCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserTakeinOrderStockOut(HashMap<String, Object> parameterInfo) {

		return mapper.selectUserTakeinOrderStockOut(parameterInfo);
	
	}

	public void selectUserTakeinStockInMonthExcel(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameterInfo) {
		
		try {	
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = mapper.selectUserTakeinStockInMonthExcel(parameterInfo);

			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/takein/excelSample/monthStock.xlsx";
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
			 style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 CellStyle styleLeft = wb.createCellStyle();
			 styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
			 styleLeft.setBorderTop(CellStyle.BORDER_THIN);
			 styleLeft.setBorderBottom(CellStyle.BORDER_THIN);
			 styleLeft.setBorderLeft(CellStyle.BORDER_THIN);
			 styleLeft.setBorderRight(CellStyle.BORDER_THIN);
			 styleLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			 
			 CellStyle styleRight = wb.createCellStyle();
			 styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			 styleRight.setBorderTop(CellStyle.BORDER_THIN);
			 styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			 styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			 styleRight.setBorderRight(CellStyle.BORDER_THIN);
			 styleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 Font font = wb.createFont();
			 font.setFontHeightInPoints((short)10);
			 style.setFont(font);
			 styleLeft.setFont(font);
			 styleRight.setFont(font);

			rowNo = 1;

			
			for(int index = 0 ; index <listRst.size(); index++) {
				row = sheet.createRow(rowNo);
				int cellNo =0;
				
				
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("stationName"));
				cell.setCellStyle(style);
				
				cellNo = 1;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("stockMonth"));
				cell.setCellStyle(style);

				cellNo = 2;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("cusItemCode"));
				cell.setCellStyle(style);
				
				cellNo = 3;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("itemDetail"));
				cell.setCellStyle(styleLeft);
				
				cellNo = 4;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("nomalInCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 5;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("returnInCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 6;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("outCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 7;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("stockCnt").toString()));
				cell.setCellStyle(styleRight);

				rowNo++;
			}

			
			String fileName= (String)parameterInfo.get("userId")+"_monthStock";

			  // 컨텐츠 타입과 파일명 지정
		    response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
		    
		    OutputStream out = new BufferedOutputStream(response.getOutputStream());
		    wb.write(out);
	        out.flush();
	        out.close();
			
		   }catch (Exception e) {
	       		e.printStackTrace();
		   }
		}

	@Override
	public void takeinOrderStockOutExcelDownDef(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameterInfo) {
		
	try {	
		ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
		listRst = mapper.selectUserTakeinOrderStockOutExcel(parameterInfo);

		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/takein/excelSample/orderStockOut.xlsx";
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
		 style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 
		 CellStyle styleLeft = wb.createCellStyle();
		 styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
		 styleLeft.setBorderTop(CellStyle.BORDER_THIN);
		 styleLeft.setBorderBottom(CellStyle.BORDER_THIN);
		 styleLeft.setBorderLeft(CellStyle.BORDER_THIN);
		 styleLeft.setBorderRight(CellStyle.BORDER_THIN);
		 styleLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		 
		 CellStyle styleRight = wb.createCellStyle();
		 styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
		 styleRight.setBorderTop(CellStyle.BORDER_THIN);
		 styleRight.setBorderBottom(CellStyle.BORDER_THIN);
		 styleRight.setBorderLeft(CellStyle.BORDER_THIN);
		 styleRight.setBorderRight(CellStyle.BORDER_THIN);
		 styleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 
		 Font font = wb.createFont();
		 font.setFontHeightInPoints((short)10);
		 style.setFont(font);
		 styleLeft.setFont(font);
		 styleRight.setFont(font);

		rowNo = 1;


		for(int index = 0 ; index <listRst.size(); index++) {
			
			row = sheet.createRow(rowNo);
			int cellNo = 0;
			int subRow =  Integer.parseInt(listRst.get(index).get("subRow").toString());

	
			cellNo = 0;
			cell = row.createCell(cellNo);
			cell.setCellStyle(style);
			
			cellNo = 1;
			cell = row.createCell(cellNo);
			cell.setCellStyle(style);
			cellNo = 2;
			cell = row.createCell(cellNo);
			cell.setCellStyle(style);
			cellNo = 3;
			cell = row.createCell(cellNo);
			cell.setCellStyle(style);
			
			
			if(subRow == 1) {
			
				int fromRow = rowNo + 1;
				int toRow = fromRow + Integer.parseInt(listRst.get(index).get("rowCnt").toString())-1;
				CellRangeAddress region = CellRangeAddress.valueOf("A"+fromRow+":A"+toRow);
				sheet.addMergedRegion(region);

				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String)listRst.get(index).get("stationName"));
		
				cellNo = 1;
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+fromRow+":B"+toRow));
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String)listRst.get(index).get("outDate"));
				
				cellNo = 2;
				sheet.addMergedRegion(CellRangeAddress.valueOf("C"+fromRow+":C"+toRow));
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String)listRst.get(index).get("orderNo"));
				
				cellNo = 3;
				sheet.addMergedRegion(CellRangeAddress.valueOf("D"+fromRow+":D"+toRow));
				cell = row.createCell(cellNo);
				cell.setCellStyle(style);
				cell.setCellValue((String)listRst.get(index).get("blNo"));
			}
			
			cellNo = 4;
			cell = row.createCell(cellNo);
			cell.setCellValue((String)listRst.get(index).get("cusItemCode"));
			cell.setCellStyle(style);
			
			
			cellNo = 5;
			cell = row.createCell(cellNo);
			cell.setCellValue((String)listRst.get(index).get("itemDetail"));
			cell.setCellStyle(styleLeft);
			
			cellNo = 6;
			cell = row.createCell(cellNo);
			cell.setCellValue(Integer.parseInt(listRst.get(index).get("outCnt").toString()));
			cell.setCellStyle(styleRight);
			
			rowNo++;
		}
		

		
		String fileName= (String)parameterInfo.get("toDate");
		
	
		
		  // 컨텐츠 타입과 파일명 지정
	    response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
	    
	    OutputStream out = new BufferedOutputStream(response.getOutputStream());
	    wb.write(out);
        out.flush();
        out.close();
		
	   }catch (Exception e) {
       		e.printStackTrace();
	   }
	}

	@Override
	public int selectUserTakeinStockInMonthCnt(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectUserTakeinStockInMonthCnt(parameterInfo);
	}

	@Override
	public void takeinStockInOutExcelDown(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameterInfo) {
		
		try {	
			ArrayList<HashMap<String, Object>> listRst = new ArrayList<HashMap<String, Object>>();
			listRst = mapper.takeinStockInOutExcelDown(parameterInfo);

			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/takein/excelSample/takeinStockInOut.xlsx";
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
			 style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 CellStyle styleLeft = wb.createCellStyle();
			 styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
			 styleLeft.setBorderTop(CellStyle.BORDER_THIN);
			 styleLeft.setBorderBottom(CellStyle.BORDER_THIN);
			 styleLeft.setBorderLeft(CellStyle.BORDER_THIN);
			 styleLeft.setBorderRight(CellStyle.BORDER_THIN);
			 styleLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			 
			 CellStyle styleRight = wb.createCellStyle();
			 styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
			 styleRight.setBorderTop(CellStyle.BORDER_THIN);
			 styleRight.setBorderBottom(CellStyle.BORDER_THIN);
			 styleRight.setBorderLeft(CellStyle.BORDER_THIN);
			 styleRight.setBorderRight(CellStyle.BORDER_THIN);
			 styleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			 
			 Font font = wb.createFont();
			 font.setFontHeightInPoints((short)10);
			 style.setFont(font);
			 styleLeft.setFont(font);
			 styleRight.setFont(font);

			rowNo = 1;
			int cellNo = 0;
			for(int index = 0 ; index <listRst.size(); index++) {
				row = sheet.createRow(rowNo);
				
				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("stationName"));
				cell.setCellStyle(style);
				
				cellNo = 1;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("stockDate"));
				cell.setCellStyle(style);
				
				cellNo = 2;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("cusItemCode"));
				cell.setCellStyle(style);
				
				cellNo = 3;
				cell = row.createCell(cellNo);
				cell.setCellValue((String)listRst.get(index).get("itemDetail"));
				cell.setCellStyle(styleLeft);
				
				cellNo = 4;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("whInCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 5;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("outCnt").toString()));
				cell.setCellStyle(styleRight);
				
				cellNo = 6;
				cell = row.createCell(cellNo);
				cell.setCellValue(Integer.parseInt(listRst.get(index).get("stockCnt").toString()));
				cell.setCellStyle(styleRight);
		

				rowNo++;
			}
			

			
			String fileName= (String)parameterInfo.get("fromDate")+"_"+(String)parameterInfo.get("toDate")+"_"+(String)parameterInfo.get("userId");

		    response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
		    
		    OutputStream out = new BufferedOutputStream(response.getOutputStream());
		    wb.write(out);
	        out.flush();
	        out.close();
			
		   }catch (Exception e) {
	       		e.printStackTrace();
		   }
	
	}

	@Override
	public ArrayList<TmpTakeinOrderItemListVO> selectTmpTakeinUserOrderItem2(HashMap<String, Object> itemParmeter2)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTmpTakeinUserOrderItem2(itemParmeter2);
	}

	@Override
	public String insertTakeinOrderInfo(HttpServletRequest request, HashMap<String, String> parameterInfo) {
		String result = "F";
		String transCode = "";
		
		Map<String, String> resMap = new HashMap<String, String>();	
		HashMap<String, Object> transParameter = new HashMap<String, Object>();
		
		transParameter.put("userId", request.getParameter("userId"));
		
		CustomerVO customerInfo = new CustomerVO();
		
		HashMap<String, String> userId = new HashMap<String, String>();
		userId.put("userId", request.getParameter("userId"));
		
		customerInfo = mapper.selectCustomInfo(userId);
		customerInfo.dncryptData();
		
		return "";
		
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserCusItemList(HashMap<String, Object> parameterInfo) {
		return mapper.selectUserCusItemList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectTakeinCusItemInJson(HashMap<String, Object> parameterInfo) {
		return mapper.selectTakeinCusItemInJson(parameterInfo);
	}

	@Override
	public ArrayList<CurrencyVO> selectCurrencyList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectCurrencyList(parameterInfo);
	}

	@Override
	public void updateUserTakeinOrderList(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
			Date time = new Date();
			String tim1 = format1.format(time);
			
			if (userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {
				mapper.deleteUserOrderItemTmp(userOrderList.getNno());
			} else {
				mapper.deleteUserOrderItem(userOrderList.getNno());	
			}
			
			if(userOrderList.getOrderDate().equals("")) {
				userOrderList.setOrderDate(userOrderList.getWDate());
			}else {
				userOrderList.setOrderDate(userOrderList.getOrderDate().replaceAll("-", ""));
			}
			
			int orderTempStatus = mapper.confirmOrderListTemp(userOrderList);
			int orderStatus = mapper.confirmOrderList(userOrderList);
			
			if(orderStatus > 0 || orderTempStatus > 1) {
				userOrderList.setStatus("TMP08");
			}else {
				userOrderList.setStatus("");
			}
			
			userOrderList.setOrderType("TAKEIN");
			userOrderList.encryptData();
			if(userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {//temp_order_list
				usrMapper.updateUserOrderListTemp(userOrderList);
			}else {//tb_order_List
				usrMapper.updateUserOrderList(userOrderList);
			}
			userOrderList.dncryptData();
			
			UserOrderItemVO orderItem = new UserOrderItemVO();
			
			for (int i = 0; i < userOrderItemList.getItemDetail().length; i++) {
				orderItem.setOrgStation(userOrderList.getOrgStation());;
				orderItem.setNno(userOrderList.getNno());
				orderItem.setSubNo(Integer.toString(i+1));
				orderItem.setUserId(userOrderList.getUserId());
				orderItem.setUnitValue(userOrderItemList.getUnitValue()[i]);
				orderItem.setItemCnt(userOrderItemList.getItemCnt()[i]);
				orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[i]);
				if (userOrderItemList.getNativeItemDetail().length == 0) {
					orderItem.setNativeItemDetail("");
				} else {
					orderItem.setNativeItemDetail(userOrderItemList.getNativeItemDetail()[i]);
				}
				
				orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[i]);
				orderItem.setWUserId(userOrderList.getWUserId());
				orderItem.setWUserIp(userOrderList.getWUserIp());
				
				if(orderStatus > 1) {
					orderItem.setStatus("TMP08");
				}
				if (userOrderList.getHawbNo().isEmpty()) {
					mapper.insertTakeinOrderItemTemp(orderItem);
				} else {
					mapper.insertTakeinOrderItem(orderItem);
				}
			}
			
			if (userOrderList.getExpType().equals("N")) {
				comnMapper.deleteExportDeclareInfo(userOrderList);
			} else {
				comnServiceImpl.execExportDeclareInfo(userOrderList);
			}
			/*
			if(!userOrderList.getExpValue().equals("noExplicence")) {
				updateExpLicenceInfo(userOrderList);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();	
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
		
		if(userOrderList.getExpValue().equals("registExplicence1")) {
			licence.setSimpleYn("N");
			licence.setSendYn("N");
		}else if(userOrderList.getExpValue().equals("simpleExplicence")) {
			licence.setSimpleYn("Y");
			licence.setSendYn("Y");
		}else if(userOrderList.getExpValue().equals("registExplicence2")) {
			licence.setSimpleYn("N");
			licence.setSendYn("M");
		}
		apiServiceImpl.insertExpBaseInfo(licence);
	}

	@Override
	public int selectTakeinCodeListCnt(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectTakeinCodeListCnt(parameterInfo);
	}


}
