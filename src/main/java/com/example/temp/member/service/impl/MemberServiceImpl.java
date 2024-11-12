package com.example.temp.member.service.impl;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.api.ApiAction;
import com.example.temp.api.aci.service.impl.ApiServiceImpl;
import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.shopify.ApiShopifyResultVO;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.service.impl.ComnServiceImpl;
import com.example.temp.common.vo.CommercialItemVO;
import com.example.temp.common.vo.CommercialVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.manager.service.impl.ManagerServiceImpl;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.StockMsgVO;
import com.example.temp.manager.vo.insp.InspStockListVO;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.AllowIpVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.ExcelMatchingVO;
import com.example.temp.member.vo.KukkiwonVO;
import com.example.temp.member.vo.ShpngListVO;
import com.example.temp.member.vo.TestssVO;
import com.example.temp.member.vo.UserOrderItemListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserTransComVO;
import com.example.temp.member.vo.UserVO;
import com.example.temp.trans.comn.ComnAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.yongsung.YongSungAPI;

import ch.qos.logback.core.property.FileExistsPropertyDefiner;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private ApiServiceImpl apiServiceImpl;
	
	@Autowired
	private ManagerServiceImpl mgrServiceImpl;
	
	@Autowired
	private FedexAPI fedexApi;
	
	@Autowired
	private OcsAPI ocsApi;
	
	@Autowired
	private EfsAPI efsApi;
	
	@Autowired
	private YongSungAPI yslApi;
	
	@Autowired
	private ItcAPI itcApi;
	
	@Autowired
	private GtsAPI gtsApi;
	
	@Autowired
	private ComnService comnService;
	
	@Autowired
	private SekoAPI sekoApi;
	
	@Autowired
	private ComnAPI comnApi;

	@Autowired
	private FastboxAPI fastboxApi;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	private ComnMapper comnMapper;
	
	@Autowired
	private ComnServiceImpl comnServiceImpl;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public String selectTest() throws Exception {
		return mapper.getSelectTest();
		
	}

	@Override
	public void insertTest() throws Exception {
		// TODO Auto-generated method stub
		mapper.insertTest();
	}

	@Override
	public ArrayList<String> selectUserOrgNation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserOrgNation(userId);
	}

	@Override
	public ArrayList<UserTransComVO> selectUserDstnNation(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserDstnNation(userId);
	}

	@Override
	public UserVO selectUserInfo(String userId) throws Exception {
		// TODO Auto-generated method stub
		UserVO resultVal = new UserVO();
		resultVal = mapper.selectUserInfo(userId); 
		resultVal.dncryptData();
		return resultVal;
	}

	@Override
	public void updateUserInfo(UserVO userInfoVO, InvUserInfoVO invUserInfo, HttpServletRequest request) throws Exception {
		if(userInfoVO.getUserId() != null || !userInfoVO.getUserId().equals(null)) {
			//mapper.deleteUserTrkCom(userInfoVO.getUserId());
			HashMap<String,String> tests = new HashMap<String, String>();
			tests.put("userId", userInfoVO.getUserId());
			tests.put("wUserId", userInfoVO.getWUserId());
			tests.put("wUserIp", userInfoVO.getWUserIp());
			tests.put("wDate", userInfoVO.getWDate());
//			int indexCnt = 0;
//			String[] orgNationList = request.getParameterValues("orgNation");
//			String[] dstnNationList = request.getParameterValues("dstnNation");
//			for(int i = 0; i < orgNationList.length; i++) {
//				for(int j = 0; j < dstnNationList.length; j++) {
//					tests.put("orgNation",orgNationList[i]);
//					tests.put("dstnNation",dstnNationList[j]);
//					tests.put("transCode","");
//					tests.put("seq", Integer.toString((indexCnt+1)));
//					mapper.insertUserTransCom(tests);
//				}
//			}
			mapper.updateUserInfo(userInfoVO);
			invUserInfo.setInvUserId(userInfoVO.getUserId());
			invUserInfo.encryptData();
			mapper.updateInvUserInfo(invUserInfo);
		}
	}

	@Override
	public void insertUserOrderListSagawa(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		// TODO Auto-generated method stub
		String pattern = "^[0-9]*$"; //숫자만
		String nno = comnService.selectNNO();
		userOrderList.setNno(nno);
		if(userOrderList.getOrderDate().equals("")) {
			userOrderList.setOrderDate(userOrderList.getWDate());
		}else {
			String orderDate = userOrderList.getOrderDate().replaceAll("[^0-9]","");
			userOrderList.setOrderDate(orderDate);
		}
		
		int orderTempStatus = mapper.confirmOrderListTemp(userOrderList);	
		int orderStatus = mapper.confirmOrderList(userOrderList);
		
		if(orderStatus > 0 || orderTempStatus > 0) {
			userOrderList.setStatus("TMP08");
		}
		userOrderList.encryptData();
		userOrderList.setUserWta(String.format("%.2f", Double.parseDouble(userOrderList.getUserWta())));
		userOrderList.setUserWtc(String.format("%.2f", Double.parseDouble(userOrderList.getUserWtc())));
		mapper.insertUserOrderListTemp(userOrderList);
		userOrderList.dncryptData();
		UserOrderItemVO orderItem = new UserOrderItemVO();
		for(int roop = 0; roop < userOrderItemList.getItemDetail().length; roop++) {
			/* userOrderItems.getBrand(). */
			if(userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
				userOrderItemList.getBrandItem()[roop] = "";
			}
			orderItem.setTransCode(userOrderList.getTransCode());
			orderItem.setBrand(userOrderItemList.getBrandItem()[roop]);
			/* orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]); */
			orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]);
			/* orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]); */
			/* orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]); */
			/* orderItem.setHsCode(userOrderItemList.getHsCode()[roop]); */
			orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);
			orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]);
			/* orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]); */
			/* orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]); */
			orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]);
			/* orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]); */
			orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]);
			/* orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]); */
			/* orderItem.setNationCode(userOrderItemList.getNationCode()[roop]); */
			orderItem.setNationCode("");
			orderItem.setNno(nno);
			/* orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]); */
			/* orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]); */
			orderItem.setOrgStation(userOrderList.getOrgStation());
			/* orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]); */
			/* orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]); */
			orderItem.setSubNo(Integer.toString(roop+1));
			/* orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); */
			orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]);
			orderItem.setUserId(userOrderList.getUserId()); 
			/* orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]); */
			/* orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]); */
			orderItem.setWDate(userOrderList.getWDate());
			/* orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]); */
			orderItem.setWUserId(userOrderList.getWUserId());
			orderItem.setWUserIp(userOrderList.getWUserIp());
			orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]);
			if(orderStatus > 0) {
				orderItem.setStatus("TMP08");
			}
			mapper.insertUserOrderItemTemp(orderItem);
		}
	}
	
//	@Override
//	public void insertUserOrderListARA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
//		// TODO Auto-generated method stub
//		String pattern = "^[0-9]*$"; //숫자만
//		String nno = comnService.selectNNO();
//		userOrderList.setNno(nno);
//		if(userOrderList.getOrderDate().equals("")) {
//			userOrderList.setOrderDate(userOrderList.getWDate());
//		}else {
//			String orderDate = userOrderList.getOrderDate().replaceAll("[^0-9]","");
//			userOrderList.setOrderDate(orderDate);
//		}
//		
//		int orderTempStatus = mapper.confirmOrderListTemp(userOrderList);	
//		int orderStatus = mapper.confirmOrderList(userOrderList);
//		
//		if(orderStatus > 0 || orderTempStatus > 0) {
//			userOrderList.setStatus("TMP09");
//		}
//		userOrderList.encryptData();
//		userOrderList.setUserWta(String.format("%.2f", Double.parseDouble(userOrderList.getUserWta())));
//		userOrderList.setUserWtc(String.format("%.2f", Double.parseDouble(userOrderList.getUserWtc())));
//		mapper.insertUserOrderListTemp(userOrderList);
//		userOrderList.dncryptData();
//		UserOrderItemVO orderItem = new UserOrderItemVO();
//		for(int roop = 0; roop < userOrderItemList.getItemDetail().length; roop++) {
//			
//			orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]);
//			orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]);
//			orderItem.setTrkDate(userOrderItemList.getTrkDate()[roop]);
//			
//
//			/* userOrderItems.getBrand(). */
//			orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]);
//			orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]);
//			if(userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
//				userOrderItemList.getBrandItem()[roop] = "";
//			}
//			orderItem.setBrand(userOrderItemList.getBrandItem()[roop]);
//			orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]);
//			orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);
//			orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]);
//			orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]);
//			/* orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]); */
//			/* orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]); */
//			/* orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]); */
//			/* orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]); */
//			orderItem.setHsCode(userOrderItemList.getHsCode()[roop]);
//			
//			orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]);
//			orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]);
//			
//			
//			orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]);
//			orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]);
//			
//			/* orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]); */
//			
//			orderItem.setNno(nno);
//			orderItem.setOrgStation(userOrderList.getOrgStation());
//			orderItem.setNationCode(userOrderList.getDstnNation());
//			orderItem.setSubNo(Integer.toString(roop+1));
//			
//			
//			orderItem.setUserId(userOrderList.getUserId());
//			orderItem.setWDate(userOrderList.getWDate());
//			orderItem.setWUserId(userOrderList.getWUserId());
//			orderItem.setWUserIp(userOrderList.getWUserIp());
//			
//			/* orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]); */
//			/* orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); */
//			/* orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]); */
//			/* orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]); */
//			/* orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]); */
//			if(orderStatus > 1) {
//				orderItem.setStatus("TMP09");
//			}
//			mapper.insertUserOrderItemTemp(orderItem);
//		}
//	}
	
	@Override
	public void insertUserOrderListARA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		// TODO Auto-generated method stub
		String pattern = "^[0-9]*$"; //숫자만
		String nno = comnService.selectNNO();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		userOrderList.setNno(nno);
		if(userOrderList.getOrderDate().equals("")) {
			userOrderList.setOrderDate(userOrderList.getWDate());
		}else {
			String orderDate = userOrderList.getOrderDate().replaceAll("[^0-9]","");
			userOrderList.setOrderDate(orderDate);
		}
		
		int orderTempStatus = mapper.confirmOrderListTemp(userOrderList);	
		int orderStatus = mapper.confirmOrderList(userOrderList);
		
		if(orderStatus > 0 || orderTempStatus > 0) {
			userOrderList.setStatus("TMP08");
		}
		userOrderList.setUploadType("HAND");
		userOrderList.encryptData();
		if(userOrderList.getUserWta() == null || userOrderList.getUserWta().equals("")) {
			userOrderList.setUserWta("0"); 
		}
		if(userOrderList.getUserWtc() == null|| userOrderList.getUserWtc().equals("")) {
			userOrderList.setUserWtc("0");	
		}
		if(userOrderList.getBoxCnt() == null || userOrderList.getBoxCnt().equals("")) {
			userOrderList.setBoxCnt("1");
		}
		if(userOrderList.getWtUnit() == null || userOrderList.getWtUnit().equals("")) {
			userOrderList.setWtUnit("KG");
		}
		if(userOrderList.getDimUnit() == null || userOrderList.getDimUnit().equals("")) {
			userOrderList.setDimUnit("CM");
		}
		userOrderList.setUserWta(String.format("%.2f", Double.parseDouble(userOrderList.getUserWta())));
		userOrderList.setUserWtc(String.format("%.2f", Double.parseDouble(userOrderList.getUserWtc())));
		/*무게에 따른 TransComChg 시작*/
		ProcedureVO rstValue = new ProcedureVO();
		HashMap<String,Object> transParameter = new HashMap<String,Object>();
		transParameter.put("nno", userOrderList.getNno());
		transParameter.put("orgStation", userOrderList.getOrgStation());
		transParameter.put("dstnNation", userOrderList.getDstnNation());
		transParameter.put("userId", userOrderList.getUserId());
		transParameter.put("wta", userOrderList.getUserWta());
		transParameter.put("wtc", userOrderList.getUserWtc());
		transParameter.put("wUserId", userOrderList.getWUserId());
		transParameter.put("wUserIp", userOrderList.getWUserIp());
		transParameter.put("transCode", userOrderList.getTransCode());
		rstValue  = comnService.selectTransComChangeForVo(transParameter);
		String transCom= rstValue.getRstTransCode();
		userOrderList.setTransCode(transCom);
		userOrderList.setUploadType("HAND");
		/*무게에 따른 TransComChg 끝*/	
		try {
			mapper.insertUserOrderListTemp(userOrderList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		userOrderList.dncryptData();
		UserOrderItemVO orderItem = new UserOrderItemVO();
		String[] tempString = new String[userOrderItemList.getItemDetail().length];
		String[] tempString2 = new String[userOrderItemList.getItemDetail().length];
		Arrays.fill(tempString, "");
		if(userOrderItemList.getTrkCom()== null) {
			userOrderItemList.setTrkCom(tempString);
		}
		if(userOrderItemList.getTrkNo()== null) {
			userOrderItemList.setTrkNo(tempString);
		}
		if(userOrderItemList.getTrkDate()== null) {
			Arrays.fill(tempString2, time1);
			userOrderItemList.setTrkDate(tempString2);
		}
		if(userOrderItemList.getHsCode()== null) {
			userOrderItemList.setHsCode(tempString);
		}
		if(userOrderItemList.getItemDetail()== null) {
			userOrderItemList.setItemDetail(tempString);
		}
		if(userOrderItemList.getItemCnt()== null) {
			userOrderItemList.setItemCnt(tempString);
		}
		if(userOrderItemList.getQtyUnit()== null) {
			Arrays.fill(tempString, "EA");
			userOrderItemList.setQtyUnit(tempString);
			Arrays.fill(tempString, "");
		}
		if(userOrderItemList.getBrandItem()== null) {
			userOrderItemList.setBrandItem(tempString);
		}
		if(userOrderItemList.getUnitValue()== null) {
			userOrderItemList.setUnitValue(tempString);
		}
		if(userOrderItemList.getItemDiv()== null) {
			userOrderItemList.setItemDiv(tempString);
		}
		if(userOrderItemList.getCusItemCode()== null) {
			userOrderItemList.setCusItemCode(tempString);
		}
		if(userOrderItemList.getItemMeterial()== null) {
			userOrderItemList.setItemMeterial(tempString);
		}
		if(userOrderItemList.getWtUnitItem()== null) {
			userOrderItemList.setWtUnitItem(tempString);
		}
		if(userOrderItemList.getUnitCurrency()== null) {
			userOrderItemList.setUnitCurrency(tempString);
		}
		if(userOrderItemList.getChgCurrency()== null) {
			Arrays.fill(tempString, "USD");
			userOrderItemList.setChgCurrency(tempString);
			Arrays.fill(tempString, "");
		}
		if(userOrderItemList.getMakeCntry()== null) {
			userOrderItemList.setMakeCntry(tempString);
		}
		if(userOrderItemList.getMakeCom()== null) {
			userOrderItemList.setMakeCom(tempString);
		}
		if(userOrderItemList.getItemUrl()== null) {
			userOrderItemList.setItemUrl(tempString);
		}
		if(userOrderItemList.getItemImgUrl()== null) {
			userOrderItemList.setItemImgUrl(tempString);
		}
		if(userOrderItemList.getUserItemWta()== null) {
			userOrderItemList.setUserItemWta(tempString);
		}
		if(userOrderItemList.getItemExplan()== null) {
			userOrderItemList.setItemExplan(tempString);;
		}
		if(userOrderItemList.getItemBarcode()== null) {
			userOrderItemList.setItemBarcode(tempString);
		}
		if(userOrderItemList.getNativeItemDetail()== null) {
			userOrderItemList.setNativeItemDetail(tempString);
		}
		
		for(int roop = 0; roop < userOrderItemList.getItemDetail().length; roop++) {
			if(userOrderItemList.getTrkCom()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkCom()[roop] = "";
			}
			if(userOrderItemList.getTrkNo()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkNo()[roop] = "";
			}
			if(userOrderItemList.getTrkDate()[roop].equals("d#none#b")) {
				userOrderItemList.getTrkDate()[roop] = time1;
			}
			if(userOrderItemList.getHsCode()[roop].equals("d#none#b")) {
				userOrderItemList.getHsCode()[roop] = "";
			}
			if(userOrderItemList.getItemDetail()[roop].equals("d#none#b")) {
				userOrderItemList.getItemDetail()[roop] = "";
			}
			if(userOrderItemList.getItemCnt()[roop].equals("d#none#b")) {
				userOrderItemList.getItemCnt()[roop] = "";
			}
			if(userOrderItemList.getQtyUnit()[roop].equals("d#none#b")) {
				userOrderItemList.getQtyUnit()[roop] = "EA";
			}
			if(userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
				userOrderItemList.getBrandItem()[roop] = "";
			}
			if(userOrderItemList.getUnitValue()[roop].equals("d#none#b")) {
				userOrderItemList.getUnitValue()[roop] = "";
			}
			if(userOrderItemList.getItemDiv()[roop].equals("d#none#b")) {
				userOrderItemList.getItemDiv()[roop] = "";
			}
			if(userOrderItemList.getCusItemCode()[roop].equals("d#none#b")) {
				userOrderItemList.getCusItemCode()[roop] = "";
			}
			if(userOrderItemList.getItemMeterial()[roop].equals("d#none#b")) {
				userOrderItemList.getItemMeterial()[roop] = "";
			}
			if(userOrderItemList.getWtUnitItem()[roop].equals("d#none#b")) {
				userOrderItemList.getWtUnitItem()[roop] = "";
			}
			if(userOrderItemList.getUnitCurrency()[roop].equals("d#none#b")) {
				userOrderItemList.getUnitCurrency()[roop] = "";
			}
			if(userOrderItemList.getChgCurrency()[roop].equals("d#none#b")) {
				userOrderItemList.getChgCurrency()[roop] = "";
			}
			if(userOrderItemList.getMakeCntry()[roop].equals("d#none#b")) {
				userOrderItemList.getMakeCntry()[roop] = "";
			}
			if(userOrderItemList.getMakeCom()[roop].equals("d#none#b")) {
				userOrderItemList.getMakeCom()[roop] = "";
			}
			if(userOrderItemList.getItemUrl()[roop].equals("d#none#b")) {
				userOrderItemList.getItemUrl()[roop] = "";
			}
			if(userOrderItemList.getItemImgUrl()[roop].equals("d#none#b")) {
				userOrderItemList.getItemImgUrl()[roop] = "";
			}
			if(userOrderItemList.getUserItemWta()[roop].equals("d#none#b")) {
				userOrderItemList.getUserItemWta()[roop] = "";
			}
			if(userOrderItemList.getItemExplan()[roop].equals("d#none#b")) {
				userOrderItemList.getItemExplan()[roop] = "";
			}
			if(userOrderItemList.getItemBarcode()[roop].equals("d#none#b")) {
				userOrderItemList.getItemBarcode()[roop] = "";
			}
			if(userOrderItemList.getNativeItemDetail()[roop].equals("d#none#b")) {
				userOrderItemList.getNativeItemDetail()[roop] = "";
			}
			
			orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]); //국내 택배
			orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]); //국내 택배 운송장
			orderItem.setTrkDate(userOrderItemList.getTrkDate()[roop]); //국내 택배 운송날짜
			orderItem.setHsCode(userOrderItemList.getHsCode()[roop]); // HS 코드
			orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]); // 상품명
			orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);//상품 개수
			orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]);//상품 단위
			orderItem.setBrand(userOrderItemList.getBrandItem()[roop]); //브랜드
			orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]); //상품 단가
			orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]); //상품 종류
			orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]); //상품코드			
			orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]); //상품 재질
			orderItem.setWtUnit(userOrderItemList.getWtUnitItem()[roop]);//무게 단위
			orderItem.setUnitCurrency(userOrderItemList.getUnitCurrency()[roop]);//무게 단위
			orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[roop]);//무게 단위
			orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]); //제조국
			orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]); //제조회사
			orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]); //상품 URL
			orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]); //상품 IMG URL
			orderItem.setUserItemWta(userOrderItemList.getUserItemWta()[roop]);
			orderItem.setItemExplan(userOrderItemList.getItemExplan()[roop]);
			orderItem.setItemBarcode(userOrderItemList.getItemBarcode()[roop]);
			orderItem.setNativeItemDetail(userOrderItemList.getNativeItemDetail()[roop]);
		
			/* orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]); */
			/* orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]); */
			/* orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]); */
			/* orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]); */
			
			orderItem.setTransCode(userOrderList.getTransCode());
			orderItem.setNno(nno);
			orderItem.setOrgStation(userOrderList.getOrgStation());
			orderItem.setNationCode(userOrderList.getDstnNation());
			orderItem.setSubNo(Integer.toString(roop+1));
			orderItem.setUserId(userOrderList.getUserId());
			orderItem.setWDate(userOrderList.getWDate());
			orderItem.setWUserId(userOrderList.getWUserId());
			orderItem.setWUserIp(userOrderList.getWUserIp());
			/* orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]); */
			/* orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); */
			/* orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]); */
			/* orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]); */
			/* orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]); */
			if(orderStatus > 1) {
				orderItem.setStatus("TMP08");
			}
			mapper.insertUserOrderItemTemp(orderItem);
		}
		
		if (!userOrderList.getExpType().equals("N")) {
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
			expInfo.setSendYn("N");
			comnMapper.insertExportDeclareInfo(expInfo);
		}
		/*
		if(userOrderList.getExpValue().equals("noExplicence")) {
			
		}else {
			insertExpLicenceInfo(userOrderList);
		}
		*/
	}
	public void insertExpLicenceInfo(UserOrderListVO userOrderList) throws Exception {
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
	public void updateUserOrderListSAGAWA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		// TODO Auto-generated method stub
//		mapper.deleteUserOrderListTmp(userOrderList.getNno(),userOrderList.getUserId());
//		mapper.deleteUserOrderItemTmp(userOrderList.getNno(),userOrderList.getUserId());
		if(userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {//temp_order_list
			comnService.deleteUserOrderItemTmp(userOrderList.getNno());
		}else {//tb_order_List
			comnService.deleteUserOrderItem(userOrderList.getNno());
		}
		
		if(userOrderList.getOrderDate().equals("")) {
			userOrderList.setOrderDate(userOrderList.getWDate());
		}else {
			userOrderList.setOrderDate(userOrderList.getOrderDate().replaceAll("-", ""));
		}
		
		int orderTempStatus = mapper.confirmOrderListTemp(userOrderList);	
		int orderStatus = mapper.confirmOrderList(userOrderList);
		
		if(orderStatus > 1 || orderTempStatus > 1) {
			userOrderList.setStatus("TMP08");
		}else {
			userOrderList.setStatus("");
		}
		userOrderList.encryptData();
		if(userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {//temp_order_list
			mapper.updateUserOrderListTemp(userOrderList);
		}else {//tb_order_List
			mapper.updateUserOrderList(userOrderList);
		}
		userOrderList.dncryptData();
		UserOrderItemVO orderItem = new UserOrderItemVO();
		for(int roop = 0; roop < userOrderItemList.getItemDetail().length; roop++) {
			/* userOrderItems.getBrand(). */
			if(userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
				userOrderItemList.getBrandItem()[roop] = "";
			}
			orderItem.setTransCode(userOrderList.getTransCode());
			orderItem.setBrand(userOrderItemList.getBrandItem()[roop]);
			/* orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]); */
			orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]);
			/* orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]); */
			/* orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]); */
			/* orderItem.setHsCode(userOrderItemList.getHsCode()[roop]); */
			orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);
			orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]);
			/* orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]); */
			/* orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]); */
			orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]);
			/* orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]); */
			orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]);
			/* orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]); */
			/* orderItem.setNationCode(userOrderItemList.getNationCode()[roop]); */
			orderItem.setNationCode(userOrderList.getDstnNation());
			orderItem.setNno(userOrderList.getNno());
			/* orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]); */
			/* orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]); */
			orderItem.setOrgStation(userOrderList.getOrgStation());
			/* orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]); */
			/* orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]); */
			orderItem.setSubNo(Integer.toString(roop+1));
			/* orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); */
			orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]);
			orderItem.setUserId(userOrderList.getUserId()); 
			/* orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]); */
			/* orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]); */
			orderItem.setWDate(userOrderList.getWDate());
			/* orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]); */
			orderItem.setWUserId(userOrderList.getWUserId());
			orderItem.setWUserIp(userOrderList.getWUserIp());
			orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]);
			if(orderStatus > 0) {
				orderItem.setStatus("TMP08");
			}
			if(userOrderList.getHawbNo().isEmpty()) {//temp_order_list
				mapper.insertUserOrderItemTemp(orderItem);
			}else {//tb_order_List
				mapper.insertUserOrderItem(orderItem);
			}
			 
		}
	}
	
	public void updateUserOrderListARA(UserOrderListVO userOrderList, UserOrderItemListVO userOrderItemList) throws Exception {
		// TODO Auto-generated method stub
//		mapper.deleteUserOrderListTmp(userOrderList.getNno(),userOrderList.getUserId());
//		mapper.deleteUserOrderItemTmp(userOrderList.getNno(),userOrderList.getUserId());
		try {
		
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
			Date time = new Date();
			String time1 = format1.format(time);
			if(userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {//temp_order_list
				comnService.deleteUserOrderItemTmp(userOrderList.getNno());
			}else {//tb_order_List
				comnService.deleteUserOrderItem(userOrderList.getNno());
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
				mapper.deleteOrderErrorMatch(userOrderList.getNno());
			}
			userOrderList.encryptData();
			if(userOrderList.getHawbNo().isEmpty() || userOrderList.getHawbNo() == null) {//temp_order_list
				mapper.updateUserOrderListTemp(userOrderList);
			}else {//tb_order_List
				mapper.updateUserOrderList(userOrderList);
			}
			userOrderList.dncryptData();
			
			int itemSize = userOrderItemList.getItemDetail().length;
			System.out.println(itemSize);
			System.out.println(itemSize);
			
			
			String[] tempString = new String[userOrderItemList.getItemDetail().length];
			String[] tempString2 = new String[userOrderItemList.getItemDetail().length];
			Arrays.fill(tempString, "");
			if(userOrderItemList.getTrkCom()== null) {
				userOrderItemList.setTrkCom(tempString);
			}
			if(userOrderItemList.getTrkNo()== null) {
				userOrderItemList.setTrkNo(tempString);
			}
			if(userOrderItemList.getTrkDate()== null) {
				Arrays.fill(tempString2, time1);
				userOrderItemList.setTrkDate(tempString2);
			}
			if(userOrderItemList.getHsCode()== null) {
				userOrderItemList.setHsCode(tempString);
			}
			if(userOrderItemList.getItemDetail()== null) {
				userOrderItemList.setItemDetail(tempString);
			}
			if(userOrderItemList.getItemCnt()== null) {
				userOrderItemList.setItemCnt(tempString);
			}
			if(userOrderItemList.getQtyUnit()== null) {
				userOrderItemList.setQtyUnit(tempString);
			}
			if(userOrderItemList.getBrandItem()== null) {
				userOrderItemList.setBrandItem(tempString);
			}
			if(userOrderItemList.getUnitValue()== null) {
				userOrderItemList.setUnitValue(tempString);
			}
			if(userOrderItemList.getItemDiv()== null) {
				userOrderItemList.setItemDiv(tempString);
			}
			if(userOrderItemList.getCusItemCode()== null) {
				userOrderItemList.setCusItemCode(tempString);
			}
			if(userOrderItemList.getItemMeterial()== null) {
				userOrderItemList.setItemMeterial(tempString);
			}
			if(userOrderItemList.getWtUnitItem()== null) {
				userOrderItemList.setWtUnitItem(tempString);
			}
			if(userOrderItemList.getUnitCurrency()== null) {
				userOrderItemList.setUnitCurrency(tempString);
			}
			if(userOrderItemList.getChgCurrency()== null) {
				userOrderItemList.setChgCurrency(tempString);
			}
			if(userOrderItemList.getMakeCntry()== null) {
				userOrderItemList.setMakeCntry(tempString);
			}
			if(userOrderItemList.getMakeCom()== null) {
				userOrderItemList.setMakeCom(tempString);
			}
			if(userOrderItemList.getItemUrl()== null) {
				userOrderItemList.setItemUrl(tempString);
			}
			if(userOrderItemList.getItemImgUrl()== null) {
				userOrderItemList.setItemImgUrl(tempString);
			}
			if(userOrderItemList.getUserItemWta()== null) {
				userOrderItemList.setUserItemWta(tempString);
			}
			if(userOrderItemList.getItemExplan()== null) {
				userOrderItemList.setItemExplan(tempString);
			}
			if(userOrderItemList.getItemBarcode()== null) {
				userOrderItemList.setItemBarcode(tempString);
			}
			if(userOrderItemList.getNativeItemDetail()== null) {
				userOrderItemList.setNativeItemDetail(tempString);
			}
			 
			UserOrderItemVO orderItem = new UserOrderItemVO();
			for(int roop = 0; roop < itemSize; roop++) {
				if(userOrderItemList.getTrkCom()[roop].equals("d#none#b")) {
					userOrderItemList.getTrkCom()[roop] = "";
				}
				if(userOrderItemList.getTrkNo()[roop].equals("d#none#b")) {
					userOrderItemList.getTrkNo()[roop] = "";
				}
				if(userOrderItemList.getTrkDate()[roop].equals("d#none#b")) {
					userOrderItemList.getTrkDate()[roop] = time1;
				}
				if(userOrderItemList.getHsCode()[roop].equals("d#none#b")) {
					userOrderItemList.getHsCode()[roop] = "";
				}
				if(userOrderItemList.getItemDetail()[roop].equals("d#none#b")) {
					userOrderItemList.getItemDetail()[roop] = "";
				}
				if(userOrderItemList.getItemCnt()[roop].equals("d#none#b")) {
					userOrderItemList.getItemCnt()[roop] = "";
				}
				if(userOrderItemList.getQtyUnit()[roop].equals("d#none#b")) {
					userOrderItemList.getQtyUnit()[roop] = "";
				}
				if(userOrderItemList.getBrandItem()[roop].equals("d#none#b")) {
					userOrderItemList.getBrandItem()[roop] = "";
				}
				if(userOrderItemList.getUnitValue()[roop].equals("d#none#b")) {
					userOrderItemList.getUnitValue()[roop] = "";
				}
				if(userOrderItemList.getItemDiv()[roop].equals("d#none#b")) {
					userOrderItemList.getItemDiv()[roop] = "";
				}
				if(userOrderItemList.getCusItemCode()[roop].equals("d#none#b")) {
					userOrderItemList.getCusItemCode()[roop] = "";
				}
				if(userOrderItemList.getItemMeterial()[roop].equals("d#none#b")) {
					userOrderItemList.getItemMeterial()[roop] = "";
				}
				if(userOrderItemList.getWtUnitItem()[roop].equals("d#none#b")) {
					userOrderItemList.getWtUnitItem()[roop] = "";
				}
				if(userOrderItemList.getUnitCurrency()[roop].equals("d#none#b")) {
					userOrderItemList.getUnitCurrency()[roop] = "";
				}
				if(userOrderItemList.getChgCurrency()[roop].equals("d#none#b")) {
					userOrderItemList.getChgCurrency()[roop] = "";
				}
				if(userOrderItemList.getMakeCntry()[roop].equals("d#none#b")) {
					userOrderItemList.getMakeCntry()[roop] = "";
				}
				if(userOrderItemList.getMakeCom()[roop].equals("d#none#b")) {
					userOrderItemList.getMakeCom()[roop] = "";
				}
				if(userOrderItemList.getItemUrl()[roop].equals("d#none#b")) {
					userOrderItemList.getItemUrl()[roop] = "";
				}
				if(userOrderItemList.getItemImgUrl()[roop].equals("d#none#b")) {
					userOrderItemList.getItemImgUrl()[roop] = "";
				}
				if(userOrderItemList.getUserItemWta()[roop].equals("d#none#b")) {
					userOrderItemList.getUserItemWta()[roop] = "";
				}
				if(userOrderItemList.getItemExplan()[roop].equals("d#none#b")) {
					userOrderItemList.getItemExplan()[roop] = "";
				}
				if(userOrderItemList.getItemBarcode()[roop].equals("d#none#b")) {
					userOrderItemList.getItemBarcode()[roop] = "";
				}
				if(userOrderItemList.getNativeItemDetail()[roop].equals("d#none#b")) {
					userOrderItemList.getNativeItemDetail()[roop] = "";
				}
				
				orderItem.setNno(userOrderList.getNno());
				orderItem.setTrkCom(userOrderItemList.getTrkCom()[roop]); //국내 택배
				orderItem.setTrkNo(userOrderItemList.getTrkNo()[roop]); //국내 택배 운송장
				orderItem.setTrkDate(userOrderItemList.getTrkDate()[roop]); //국내 택배 운송날짜
				orderItem.setHsCode(userOrderItemList.getHsCode()[roop]); // HS 코드
				orderItem.setItemDetail(userOrderItemList.getItemDetail()[roop]); // 상품명
				orderItem.setItemCnt(userOrderItemList.getItemCnt()[roop]);//상품 개수
				orderItem.setQtyUnit(userOrderItemList.getQtyUnit()[roop]);//상품 단위
				orderItem.setBrand(userOrderItemList.getBrandItem()[roop]); //브랜드
				orderItem.setUnitValue(userOrderItemList.getUnitValue()[roop]); //상품 단가
				orderItem.setItemDiv(userOrderItemList.getItemDiv()[roop]); //상품 종류
				orderItem.setCusItemCode(userOrderItemList.getCusItemCode()[roop]); //상품코드			
				orderItem.setItemMeterial(userOrderItemList.getItemMeterial()[roop]); //상품 재질
				orderItem.setWtUnit(userOrderItemList.getWtUnitItem()[roop]);//무게 단위
				orderItem.setUnitCurrency(userOrderItemList.getUnitCurrency()[roop]);//무게 단위
				orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[roop]);//무게 단위
				orderItem.setMakeCntry(userOrderItemList.getMakeCntry()[roop]); //제조국
				orderItem.setMakeCom(userOrderItemList.getMakeCom()[roop]); //제조회사
				orderItem.setItemUrl(userOrderItemList.getItemUrl()[roop]); //상품 URL
				orderItem.setItemImgUrl(userOrderItemList.getItemImgUrl()[roop]); //상품 IMG URL
				orderItem.setOrgStation(userOrderList.getOrgStation());
				orderItem.setNationCode(userOrderList.getDstnNation());
				orderItem.setSubNo(Integer.toString(roop+1));
				orderItem.setUserId(userOrderList.getUserId());
				orderItem.setWDate(userOrderList.getWDate());
				orderItem.setWUserId(userOrderList.getWUserId());
				orderItem.setWUserIp(userOrderList.getWUserIp());
				
				orderItem.setUserItemWta(userOrderItemList.getUserItemWta()[roop]);
				orderItem.setItemExplan(userOrderItemList.getItemExplan()[roop]);
				orderItem.setItemBarcode(userOrderItemList.getItemBarcode()[roop]);
				orderItem.setNativeItemDetail(userOrderItemList.getNativeItemDetail()[roop]);
				/* orderItem.setChgAmt(userOrderItemList.getChgAmt()[roop]); */
				/* orderItem.setChgCurrency(userOrderItemList.getChgCurrency()[0]); */
				/* orderItem.setChnItemDetail(userOrderItemList.getChnItemDetail()[roop]); */
				/* orderItem.setExchangeRate(userOrderItemList.getExchangeRate()[roop]); */
				orderItem.setTransCode(userOrderList.getTransCode());
				
				/* orderItem.setPackageUnit(userOrderItemList.getPackageUnit()[roop]); */
				/* orderItem.setTakeInCode(userOrderItemList.getTakeInCode()[roop]); */
				/* orderItem.setUserWta(userOrderItemList.getUserWtaItem()[roop]); */
				/* orderItem.setUserWtc(userOrderItemList.getUserWtcItem()[roop]); */
				/* orderItem.setWtUnit(userOrderItemList.getWtUnit()[roop]); */
				if(orderStatus > 1) {
					orderItem.setStatus("TMP08");
				}
				if(userOrderList.getHawbNo().isEmpty()) {//temp_order_list
					mapper.insertUserOrderItemTemp(orderItem);
				}else {//tb_order_List
					mapper.insertUserOrderItem(orderItem);
				}
			}
			/*
			if(userOrderList.getExpValue().equals("noExplicence")) {
				
			}else {
				updateExpLicenceInfo(userOrderList);
			}
			*/
			
			if (userOrderList.getExpType().equals("N")) {
				comnMapper.deleteExportDeclareInfo(userOrderList);
			} else {
				comnServiceImpl.execExportDeclareInfo(userOrderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<UserTransComVO> userTransCom(HashMap<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		return mapper.userTransCom(params);
	}

	@Override
	public LinkedHashMap<String, ArrayList<ShpngListVO>> selectShpngList(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>> (); 
//		parameter.put("orgStation", types[0]);
//		parameter.put("dstnNation", types[1]);
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try{
			selectShpngList = mapper.selectShpngList(parameter);
			for(int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
				/*
				if (parameter.get("userId").toString().equals("vision2038") || parameter.get("userId").toString().equals("itsel2")) {
					HashMap<String, Object> danParams = new HashMap<String, Object>();
					danParams.put("nno", selectShpngList.get(index).getNno());
					danParams.put("orderNo", selectShpngList.get(index).getOrderNo());
					selectShpngList.get(index).setDanList(mapper.selectDanList(danParams));
				}
				*/
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return returnMap;
		}
		if(selectShpngList.size() == 0) {
			return returnMap;
		}
		ArrayList<String> tempError = new ArrayList<String>();
		ArrayList<String> tempError2 = new ArrayList<String>();
		ArrayList<String> tempError3 = new ArrayList<String>();
		//ArrayList<HashMap<String, Object>> danList = new ArrayList<HashMap<String, Object>>();
		tempError = mapper.selectErrorList(selectShpngList.get(0).getNno());
		tempError2 = mapper.selectErrorItem(selectShpngList.get(0).getNno(),"1");
		selectShpngList.get(0).setErrorList(tempError);
		selectShpngList.get(0).setErrorItem(tempError2);
		tempSelectShpngList.add(selectShpngList.get(0));
		
		if (tempError.size() < 1) {
			tempError = mapper.selectErrorMatch(selectShpngList.get(0).getNno());
			selectShpngList.get(0).setErrorList(tempError);
			//danList = mapper.selectDanList(selectShpngList.get(0).getNno());
			//tempSelectShpngList.get(0).setDanList(danList);
		}
		
		for(int roop=1; roop<selectShpngList.size();roop++) {
			tempError = new ArrayList<String>();
			tempError2 = new ArrayList<String>();
			
 			if(selectShpngList.get(roop).getSubNo().equals("1")) {
				returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
				tempSelectShpngList = new ArrayList<ShpngListVO>();
				tempError = mapper.selectErrorList(selectShpngList.get(roop).getNno());
				tempError2 = mapper.selectErrorItem(selectShpngList.get(roop).getNno(),selectShpngList.get(roop).getSubNo());
				selectShpngList.get(roop).setErrorList(tempError);
				selectShpngList.get(roop).setErrorItem(tempError2);
				tempSelectShpngList.add(selectShpngList.get(roop));
				if (tempError.size() < 1) {
					tempError = mapper.selectErrorMatch(selectShpngList.get(roop).getNno());
					selectShpngList.get(roop).setErrorList(tempError);
				}
			}else {
				tempError2 = mapper.selectErrorItem(selectShpngList.get(roop).getNno(),selectShpngList.get(roop).getSubNo());
				selectShpngList.get(roop).setErrorItem(tempError2);
				tempSelectShpngList.add(selectShpngList.get(roop));
			}
		}
		returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
		return returnMap;
	}
	
	@Override
	public String insertExcelData(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception {
		String result="F";
		String transCom = "";
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmssSS");
		Date time = new Date();
		String pattern = "^[0-9]*$"; //숫자만
		String patternFloat = "^[0-9]*\\.?[0-9]*$"; //숫자만 Float *실수 타입*
		String patternJP = "^[ぁ-ゔ]+|[ァ-ヴー]+[々〆〤]*$"; //일본어만
		String time1 = format1.format(time);
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "excel/";
		resMap = filesUploadNomalOrder(multi, excelRoot, userId);
		String filePath = "";
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			UserOrderListVO userOrderExcelList = new UserOrderListVO();
			String regercyOrgStation = "";
			String nno="";
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					String iossNo = "";
					XSSFRow rowPivot = sheet.getRow(0);
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
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
							if(!rowEmpty) {
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								userOrderExcelItem.setTrkDate(time1);
								int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
//								CustomerDetail vo = new CustomerDetail();
//								vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//								vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//								customerNo = customerNo + 1;
//								vo.setStat("Y"); // 여부

								String value = "";
								boolean shipperInfoYn = true;
								boolean listFlag = false;
							
								
								boolean transChgYN = false;
								double wta = 0;
								double length = 0;
								double width = 0;
								double height = 0;
								double wtc = 0;
								
								HashMap<String,Object> transParameter = new HashMap<String,Object>();
								
								transParameter.put("userId", userId);
								transParameter.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transParameter.put("wUserIp", (String)request.getSession().getAttribute("USER_IP"));
								transParameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
								transParameter.put("wtc", Double.toString(wtc));
								transParameter.put("wta", Double.toString(wta));
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										default:
											value = cell.getStringCellValue() + "";
											break;
										}
										switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
											case "출발도시코드":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												transParameter.put("orgStation",value);
												break;
											}
											case "도착국가":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												String dstnNation = mapper.selectNationCode(value);
												transParameter.put("dstnNation",dstnNation);
												transParameter.put("dstnStation",dstnNation);
												break;
											}
											case "무게":{
												if(!value.equals("")) {
													transChgYN = true;		
													wta = Double.parseDouble(value);
												}else {
													value = "0";
												}
												transParameter.put("wta", Double.toString(wta));
												break;
											}
												
											case "무게단위":{
												if(value.equals("LB")) {
													wta = wta*2.20462;
													transParameter.put("wta", Double.toString(wta));
												}
												break;
											}
											case "길이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												length = Double.parseDouble(value);
												break;
											}
												
											case "폭":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												width = Double.parseDouble(value);
												break;
											}
												
											case "높이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												height = Double.parseDouble(value);
												break;
											}
											
											case "측정단위":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												
												if(value.equals("IN")) {
													wtc = (height*width*length)/133;
												}else {
													wtc = (height*width*length)/5000;
												}
												transParameter.put("wtc", Double.toString(wtc));
												break;
											}
										}
									}
								}
								
								ProcedureVO rstValue = new ProcedureVO();
								if(transChgYN) {
									nno = comnService.selectNNO();
									transParameter.put("nno",nno);
									transCom = comnService.selectUserTransCode(transParameter);
									transParameter.put("transCode",transCom);
									int cnts = apiServiceImpl.checkNation(transParameter);
									if(cnts==0) {
										HashMap<String,Object> tempparameters = new HashMap<String,Object>();
										tempparameters.put("userId", transParameter.get("userId"));
										tempparameters.put("orgStation",transParameter.get("orgStation"));
										tempparameters.put("dstnNation","DEF");
										if(apiServiceImpl.selectDefaultTransCom(tempparameters) != null) {
											transCom = apiServiceImpl.selectDefaultTransCom(tempparameters);
										}
									}
									
									transParameter.put("transCode",transCom);
									rstValue  = comnService.selectTransComChangeForVo(transParameter);
									transCom= rstValue.getRstTransCode();
									optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,transParameter.get("dstnNation").toString());
									optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,transParameter.get("dstnNation").toString());
									expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,transParameter.get("dstnNation").toString());
									expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,transParameter.get("dstnNation").toString());
								}
								
								
								ArrayList<String> checkColumnArray = new ArrayList<String>();
								boolean checkColumn = false;
								
								checkColumnArray = checkColumnFilter(rowPivot,optionOrderVO,optionItemVO,transCom);
								
								if(checkColumnArray.size() != 0) {
									checkColumn = true;
								}
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										default:
											value = cell.getStringCellValue() + "";
											break;
										}
									}else {
										if(columnIndex==0) {
											listFlag = false;
										}
										value="";
									}
										
									switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
										case "주문번호":{
											if(!optionOrderVO.getOrderNoYn().isEmpty()) {
												if("".equals(value)) {
													listFlag = false;
												}else {
													userOrderExcelList = new UserOrderListVO();
													userOrderExcelList.setWDate(time2);
													userOrderExcelList.setUserId(userId);
													userOrderExcelList.setWUserId(userId);
													userOrderExcelList.setWUserIp(request.getRemoteAddr());
													userOrderExcelList.setTypes("Excel");
													userOrderExcelList.setOrderType(orderType);
													
													userOrderExcelList.dncryptData();
													if(value.length() > 50) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
														value = value.substring(0,40);
													}
													userOrderExcelList.setOrderNo(value); // 커럼3
												}
											}else {
												listFlag = false;
												userOrderExcelList = new UserOrderListVO();
												if("".equals(value)) {
													String _orderNo = time2.substring(2, time2.length()-3);
													userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
												}
												userOrderExcelList.setWDate(time2);
												userOrderExcelList.setUserId(userId);
												userOrderExcelList.setWUserId(userId);
												userOrderExcelList.setWUserIp(request.getRemoteAddr());
												userOrderExcelList.setTypes("Excel");
												userOrderExcelList.setOrderType(orderType);
												userOrderExcelList.dncryptData();
												if(value.length() > 50) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
													value = value.substring(0,40);
												}
												userOrderExcelList.setOrderNo(value); // 커럼3
											}
											break;
										}
										case "주문날짜":{
											if(value.length() != 8) {
												value = "";
											}
											
											if(value.equals("")) {
												value = time1;
												listFlag = false;
											}
											userOrderExcelList.setOrderDate(value); 
											
											break;
										}
										case "출발도시코드":{
											if(!"".equals(value)) {
												userOrderExcelList.setOrgStation(value); // 컬럼0
												if(!listFlag) {
													listFlag = true;
												}
											}else {
												userOrderExcelList.setOrgStation(regercyOrgStation);
												if(!optionOrderVO.getOrgStationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ01,");
												}
											}
											break;
										}
										case "도착국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												String dstnNation = mapper.selectNationCode(value);
//												optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,dstnNation);
//												optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,dstnNation);
//												expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,dstnNation);
//												expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,dstnNation);
												userOrderExcelList.setDstnNation(value); // 컬럼1
											}else {
												userOrderExcelList.setDstnNation(""); // 컬럼1
												if(!optionOrderVO.getDstnNationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ02,");
												}
											}
											break;
										}
										
										case "PAYMENT": {
											if (!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setPayment(value.toUpperCase());
											} else {
												userOrderExcelList.setPayment("DDU");
											}
										break;
										}
										case "운송업체":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTransCode(transCom);
											}else {
												if(!optionOrderVO.getTransCodeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ03,");
												}
											}
											break;
										}
										case "송하인명":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperName(value);
											}else {
												if(!optionOrderVO.getShipperNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ04,");
												}
											}
											break;
										}
										case "송하인전화번호":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP03,");
													userOrderExcelList.setShipperTel("");
												}
											}else {
												if(!optionOrderVO.getShipperTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ05,");
												}
											}
											break;
										}
											
										case "송하인핸드폰번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP04,");
													userOrderExcelList.setShipperHp("");
												}
											}else {
												if(!optionOrderVO.getShipperHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ06,");
												}
											}
											break;
										}
										case "송하인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												if(value.length() < 9) {
													userOrderExcelList.setShipperZip(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
													userOrderExcelList.setShipperZip(value.substring(0,7));
												}
											}else {
												if(!optionOrderVO.getShipperZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ07,");
												}
											}
											break;
										}
										case "송하인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCntry(value);
											}else {
												if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ08,");
												}
											}
											break;
										}
										case "송하인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCity(value);
											}else {
												if(!optionOrderVO.getShipperCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ09,");
												}
											}
											break;
										}
										case "송하인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperState(value);
											}else {
												if(!optionOrderVO.getShipperStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ10,");
												}
											}
											break;
										}
										case "송하인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddr(value);
											}else {
												if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ11,");
												}
											}
											break;
										}
										case "송하인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddrDetail(value);
											}else {
												if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ12,");
												}
											}
											break;
										}	
										case "송하인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}	
										case "송하인e-mail":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "송하인reference":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperReference(value);
											}else {
												if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 reference가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int shipperTaxType = Integer.parseInt(value);
													if (shipperTaxType < 0 || shipperTaxType > 12) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														userOrderExcelList.setShipperTaxType(value);
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getShipperTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별번호": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxNo(value);
											} else {
												if(!optionOrderVO.getShipperTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인reference1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference1(value);
											}else {
												if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference1이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인reference2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference2(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference2이 비어있습니다.,");
												}
											}
											break;
										}
										case "payment":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setPayment(value);
											}
//											else {
//												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
//													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"관세 지불방식이 비어있습니다.,");
//												}
//											}
											break;
										}
										case "수취인명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인일본명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인현지이름":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인연락처1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP05,");
													userOrderExcelList.setCneeTel("");
												}
											}else {
												if(!optionOrderVO.getCneeTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
												}
											}
											userOrderExcelList.setCneeTel(value);
											break;
										}
										case "수취인연락처2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP06,");
													userOrderExcelList.setCneeHp("");
												}
											}else {
												if(!optionOrderVO.getCneeHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ16,");
												}
											}
											userOrderExcelList.setCneeHp(value);
											break;
										}
										case "수취인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.length() < 9) {
													userOrderExcelList.setCneeZip(value);
												}else {

													if (!userOrderExcelList.getDstnNation().toLowerCase().equals("brazil")) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
														userOrderExcelList.setCneeZip(value.substring(0,7));	
													} else {
														userOrderExcelList.setCneeZip(value);	
													}
												}
											}else {
												if(!optionOrderVO.getCneeZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
												}
											}
											break;
										}
										case "수취인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCntry(value);									
											}else {
												if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ18,");
												}
											}
											break;
										}
										case "수취인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCity(value);									
											}else {
												if(!optionOrderVO.getCneeCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ19,");
												}
											}
											break;
										}
										case "수취인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeState(value);
											}else {
												if(!optionOrderVO.getCneeStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ20,");
												}
											}
											break;
										}
										case "수취인구역":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeDistrict(value);
											} else {
												if (!optionOrderVO.getCneeDistrictYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 구역이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인동": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeWard(value);
											} else {
												if (!optionOrderVO.getCneeWardYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 동이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int cneeTaxType = Integer.parseInt(value);
													if (cneeTaxType < 0 || cneeTaxType > 12) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														userOrderExcelList.setCneeTaxType(value);
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getCneeTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인세금식별번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeTaxNo(value);
											} else {
												if(!optionOrderVO.getCneeTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인일본주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인현지주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인상세주소2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인현지상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeEmail(value);
											}else {
												if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ23,");
												}
											}
											break;
										}
										case "수취인요청사항":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDlvReqMsg(value);
											}else {
												if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ24,");
												}
											}
											break;
										}
										case "상거래유형":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setMallType(value);
											}else {
												if(!optionOrderVO.getMallTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ25,");
												}
											}
											break;
										}
										case "사용용도":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.equals("")) {
													value="1";
												}
												userOrderExcelList.setGetBuy(value);
											}else {
												if(!optionOrderVO.getGetBuyYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ26,");
												}
											}
											break;
										}
										case "box수량":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												if (value.equals("0")) {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											} else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											break;
										}
										case "무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWta(value);
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
										
										case "부피무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWtc(value);
												}else {
													userOrderExcelList.setUserWtc("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtcYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
											
										case "무게단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWtUnit(value);
											}else {
												if(!optionOrderVO.getWtUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ29,");
												}
											}
											break;
										}
										case "길이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "폭":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWidth(value);
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "높이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
										case "측정단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDimUnit(value);
											}else {
												if(!optionOrderVO.getDimUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ33,");
												}
												userOrderExcelList.setDimUnit("CM");
											}
											break;
										}
										case "입고메모":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWhReqMsg(value);
											}else {
												if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ34,");
												}
											}
											break;
										}
										case "구매사이트":{
											if(!"".equals(value)) {
												userOrderExcelList.setBuySite(value);
											}else {
												if(!optionOrderVO.getBuySiteYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ35,");
												}
											}
											break;
										}
										case "hscode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setHsCode(value);
											}else {
												if(!optionItemVO.getHsCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ36,");
												}
											}
											break;
										}
										case "상품이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDetail(value);
											}else {
												if(!optionItemVO.getItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품이름2":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품현지이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										
										case "화폐단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
												if ("VNP".equals(transCom)) {
													if (!"VND".equals(value.toUpperCase())) {
														userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ60,");
													}
												}
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
											break;
										}
										case "상품단가":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setUnitValue(value);
												}else {
													userOrderExcelItem.setUnitValue("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
												}
											}else {
												if(!optionItemVO.getUnitValueYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
												}
											}
											break;
										}
										case "상품개수":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setItemCnt(value);
												}else {
													userOrderExcelItem.setItemCnt("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
												}
											}else {
												if(!optionItemVO.getItemCntYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
												}
											}
											break;
										}
										case "수량단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setQtyUnit(value);
											}else {
												if(!optionItemVO.getQtyUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ41,");
												}
											}
											break;
										}
										case "브랜드":{
											if(!"".equals(value)) {
												userOrderExcelItem.setBrand(value);
											}else {
												if(!optionItemVO.getBrandYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ42,");
												}
											}
											break;
										}
										case "itemcode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setCusItemCode(value);
											}else {
												if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
												}
											}
											break;
										}
										case "현지택배회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkCom(value);
											}else {
												if(!optionItemVO.getTrkComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ44,");
												}
											}
											break;
										}
										case "현지택배번호":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkNo(value);
											}else {
												if(!optionItemVO.getTrkNoYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ45,");
												}
											}
											break;
										}
										case "배송날짜":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkDate(value);
											}else {
												if(!optionItemVO.getTrkDateYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ46,");
												}
											}
											break;
										}
										case "상품url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemUrl(value);
											}else {
												if(!optionItemVO.getItemUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ47,");
												}
											}
											break;
										}
										case "상품사진url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemImgUrl(value);
											}else {
												if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ48,");
												}
											}
											break;
										}
										case "상품색상":
											//
											break;
										case "상품무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWta(value);
											}else {
												if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품부피무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWtc(value);
											}else {
												if(!optionItemVO.getItemUserWtcYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품무게단위":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setWtUnit(value);
											}else {
												if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ58,");
												}
											}
											break;
										case "상품size":
											//
											break;
										case "사서함반호":
											//
											break;
										case "상품종류":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDiv(value);
											}else {
												if(!optionItemVO.getItemDivYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ49,");
												}
											}
											break;
										}
										case "제조국":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCntry(value);
											}else {
												if(!optionItemVO.getMakeCntryYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ50,");
												}
											}
											break;
										}
										case "제조회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCom(value);
											}else {
												if(!optionItemVO.getMakeComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ51,");
												}
											}
											break;
										}
										case "음식포함여부" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setFood(value);
											}else {
												if(!optionOrderVO.getFoodYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ56,");
												}
											}
											break;
										}
										case "수취인id번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"수취인ID번호 오류,");
												}
											}
											break;
										}
										case "개인구별번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"개인구별번호 오류,");
												}
											}
											break;
										}
										case "화장품포함여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setCosmetic(value);
											} else {
												userOrderExcelList.setCosmetic("N");
											}
											break;
										}
										case "대면서명여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setSign(value);
											} else {
												userOrderExcelList.setSign("N");
											}
											break;
										}
										case "수출신고구분" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												if (!value.equals("N")) {
													if (value.equals("S") || value.equals("E") || value.equals("F")) {
														userOrderExcelList.setExpType(value);
													} else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분 값이 유효하지 않습니다,");
													}
												} else {
													userOrderExcelList.setExpType("N");
												}

											} else {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분이 비어있습니다,");
											}
											break;
										}
										
										case "수출화주사업자상호명": {
											if (!userOrderExcelList.getExpType().equals("N")) {
												if (!"".equals(value)) {
													if (!listFlag) {
														listFlag = true;
													}
													
													userOrderExcelList.setExpCor(value);
												} else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
												}
											} else {
												userOrderExcelList.setExpCor("");
											}
											break;
										}
										
										case "수출화주사업자대표명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRprsn(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRgstrNo(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자주소": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpAddr(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자우편번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpZip(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주통관부호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpCstCd(value);
											} else {
												userOrderExcelList.setExpCstCd("");
											}
											break;
										}
										
										case "수출대행자상호명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setAgtCor(value);
											} else {
												userOrderExcelList.setAgtCor("");
											}
											break;
										}
										
										case "수출대행자사업장일련번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtBizNo(value);
											} else {
												userOrderExcelList.setAgtBizNo("");
											}
											break;
										}

										case "수출대행자통관부호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtCstCd(value);
											} else {
												userOrderExcelList.setAgtCstCd("");
											}
											break;
										}

										case "수출면장번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpNo(value);
											} else {
												userOrderExcelList.setExpNo("");
											}
											break;
										}
										
										case "ioss번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxType("6");
												userOrderExcelList.setShipperTaxNo(value);
											} else {
												
											}
											break;
										}

										
										/*러시아 추가 부분*/
										case "수취인id번호발급일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdDate(value);
											}else {
												if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 발급일 오류,");
												}
											}
											break;
										}
										case "수취인id번호발급기관정보" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdAuthority(value);
											}else {
												if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 번호 발급기관 정보 오류,");
												}
											}
											break;
										}
										case "수취인생년월일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeBirth(value);
											}else {
												if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 생년월일 오류,");
												}
											}
											break;
										}
										case "수취인tax번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTaxNo(value);
											}else {
												if(!optionOrderVO.getTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인TAX 번호 오류,");
												}
											}
											break;
										}
										case "상품설명" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemExplan(value);
											}else {
												if(!optionItemVO.getItemExplanYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"상품설명 오류,");
												}
											}
											break;
										}
										case "상품바코드번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemBarcode(value);
											}else {
												if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"바코드 오류,");
												}
											}
											break;
										}
										case "box번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setInBoxNum(value);
											}else {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"BOX번호 오류,");
											}
											break;
										}
									}
								}// 현재row vo에 set 완료
								// vo 검증로직은 여기
//								resultList.add(vo); // 검증된 vo 리스트에 추가
								if(listFlag) {
									if(shipperInfoYn) {
										UserOrderListVO userOrderExcelListTemp = new UserOrderListVO();
										userOrderExcelListTemp = mapper.selectUserForExcel(userId);
										userOrderExcelListTemp.dncryptData();
										
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ04,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ05,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ06,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ07,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ08,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ09,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ10,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ11,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ12,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ13,", ""));
										
										userOrderExcelList.setShipperName(userOrderExcelListTemp.getShipperName());
										userOrderExcelList.setShipperTel(userOrderExcelListTemp.getShipperTel());
										userOrderExcelList.setShipperHp(userOrderExcelListTemp.getShipperHp());
										userOrderExcelList.setShipperZip(userOrderExcelListTemp.getShipperZip());
										userOrderExcelList.setShipperCity(userOrderExcelListTemp.getShipperCity());
										userOrderExcelList.setShipperState(userOrderExcelListTemp.getShipperState());
										userOrderExcelList.setShipperAddr(userOrderExcelListTemp.getShipperAddr());
										userOrderExcelList.setShipperAddrDetail(userOrderExcelListTemp.getShipperAddrDetail());
										userOrderExcelList.setUserEmail(userOrderExcelListTemp.getUserEmail());
									}
									if((userOrderExcelList.getUserLength() != null && userOrderExcelList.getUserWidth() != null && userOrderExcelList.getUserHeight() !=null && userOrderExcelList.getDimUnit() != null) && (!userOrderExcelList.getUserLength().equals("")&& !userOrderExcelList.getUserWidth().equals("")&& !userOrderExcelList.getUserHeight().equals("")&& !userOrderExcelList.getDimUnit().equals(""))){
										if(userOrderExcelList.getDimUnit().equals("IN") || userOrderExcelList.getDimUnit().equals("INCH")) {
											userOrderExcelList.setDimUnit("IN");
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 166;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
										if(userOrderExcelList.getDimUnit().equals("CM")){
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 5000;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
									}
									
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									/* nno = comnService.selectNNO(); */
									userOrderExcelList.setNno(nno);
									userOrderExcelList.setUploadType("EXCEL");
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08,");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									if(userOrderExcelList.getOrderNo().equals("")) {
										String _orderNo = time2.substring(2, time2.length()-3);
										userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
									}
									userOrderExcelList.setTransCode(transCom);
									if(userOrderExcelList.getOrderDate() == "" || userOrderExcelList.getOrderDate() == null)
										userOrderExcelList.setOrderDate(time1);
									
									// 수출신고
									if (!userOrderExcelList.getExpType().equals("N")) {
										
										if (userOrderExcelList.getExpType().toUpperCase().equals("S") || 
												userOrderExcelList.getExpType().toUpperCase().equals("F") ||
												userOrderExcelList.getExpType().toUpperCase().equals("E")) {
											
											if (userOrderExcelList.getExpCor().equals("") || userOrderExcelList.getExpCor() == null) {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
											}
											
											if (userOrderExcelList.getExpType().toUpperCase().equals("E")) {
												if (userOrderExcelList.getExpRprsn().equals("") || userOrderExcelList.getExpRprsn() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
												
												if (userOrderExcelList.getExpRgstrNo().equals("") || userOrderExcelList.getExpRgstrNo() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}

												if (userOrderExcelList.getExpAddr().equals("") || userOrderExcelList.getExpAddr() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}

												if (userOrderExcelList.getExpZip().equals("") || userOrderExcelList.getExpZip() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											
											comnServiceImpl.execExportDeclareInfo(userOrderExcelList);
										}
									}
									
									userOrderExcelList.encryptData();
									if(checkColumn) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+",필수 컬럼이 부족합니다.("+checkColumnArray.toString()+")");
									}
									mapper.insertUserOrderListTemp(userOrderExcelList);
									
									/*
									if(userOrderExcelList.getExpValue().equals("noExplicence")) {
										
									}else {
										insertExpLicenceInfo(userOrderExcelList);
									}
									*/
									regercyOrgStation = userOrderExcelList.getOrgStation();
									userOrderExcelList.setOrderNo("");
									
								}else {
									//item insert 주문번호가 같은 상품 여러개
								}
								
								userOrderExcelItem.setTransCode(transCom);
								userOrderExcelItem.setNno(nno);
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								userOrderExcelItem.setOrgStation(regercyOrgStation);
								userOrderExcelItem.setNationCode(userOrderExcelList.getDstnNation());
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								UserOrderItemVO temp = new UserOrderItemVO();
								/*
								 * temp = userOrderExcelItem; userOrderExcelItemList.add(temp);
								 */
								subNo++;
							}else {
								break;
							}
							
						}
						// 엑셀 전체row수가 500이상인 경우,
//						if (rows > 500) 
//						{
//							if (resultList.size() >= 500) 
//							{ // 500건 단위로 잘라서 DB INSERT
//								result = customerRepository.insertExcel(resultList);
//								FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//								resultList = new ArrayList<>();
//							}
//						}


					}
					// 엑셀 전체row수가 500이하인 경우,
//					if (rows <= 500) { // 한꺼번에 DB INSERT(LIST size는 반드시 500이하임)
//						result = customerRepository.insertExcel(resultList);
//						FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//					}
					/* 	mapper.insertUserOrderItemListTemp(userOrderExcelItemList) */;

				}else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					String iossNo = "";
					HSSFRow rowPivot = sheet.getRow(0);
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						
						if (row != null) 
						{
							for (int cellNum = rowPivot.getFirstCellNum(); cellNum < rowPivot.getLastCellNum(); cellNum++) {
								HSSFCell cell = row.getCell(cellNum);
						        if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK && !"".equals(cell.toString())) {
						        	rowEmpty = false;
						        	break;
						        }
						    }
							if(!rowEmpty) {
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								userOrderExcelItem.setTrkDate(time1);
								int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
//								CustomerDetail vo = new CustomerDetail();
//								vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//								vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//								customerNo = customerNo + 1;
//								vo.setStat("Y"); // 여부

								String value = "";
								boolean shipperInfoYn = true;
								boolean listFlag = false;
							
								
								boolean transChgYN = false;
								double wta = 0;
								double length = 0;
								double width = 0;
								double height = 0;
								double wtc = 0;
								
								HashMap<String,Object> transParameter = new HashMap<String,Object>();
								
								transParameter.put("userId", userId);
								transParameter.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transParameter.put("wUserIp", (String)request.getSession().getAttribute("USER_IP"));
								transParameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
								transParameter.put("wtc", Double.toString(wtc));
								transParameter.put("wta", Double.toString(wta));
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
											case "출발도시코드":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												transParameter.put("orgStation",value);
												break;
											}
											case "도착국가":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												String dstnNation = mapper.selectNationCode(value);
												transParameter.put("dstnNation",dstnNation);
												transParameter.put("dstnStation",dstnNation);
												break;
											}
											case "무게":{
												if(!value.equals("")) {
													transChgYN = true;		
													wta = Double.parseDouble(value);
												}else {
													value = "0";
												}
												transParameter.put("wta", Double.toString(wta));
												break;
											}
												
											case "무게단위":{
												if(value.equals("LB")) {
													wta = wta*2.20462;
													transParameter.put("wta", Double.toString(wta));
												}
												break;
											}
											case "길이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												length = Double.parseDouble(value);
												break;
											}
												
											case "폭":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												width = Double.parseDouble(value);
												break;
											}
												
											case "높이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												height = Double.parseDouble(value);
												break;
											}
											
											case "측정단위":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												
												if(value.equals("IN")) {
													wtc = (height*width*length)/133;
												}else {
													wtc = (height*width*length)/5000;
												}
												transParameter.put("wtc", Double.toString(wtc));
												break;
											}
										}
									}
								}
								
								ProcedureVO rstValue = new ProcedureVO();
								if(transChgYN) {
									nno = comnService.selectNNO();
									transParameter.put("nno",nno);
									transCom = comnService.selectUserTransCode(transParameter);
									transParameter.put("transCode",transCom);
									int cnts = apiServiceImpl.checkNation(transParameter);
									if(cnts==0) {
										HashMap<String,Object> tempparameters = new HashMap<String,Object>();
										tempparameters.put("userId", transParameter.get("userId"));
										tempparameters.put("orgStation",transParameter.get("orgStation"));
										tempparameters.put("dstnNation","DEF");
										if(apiServiceImpl.selectDefaultTransCom(tempparameters) != null) {
											transCom = apiServiceImpl.selectDefaultTransCom(tempparameters);
										}
									}
									
									transParameter.put("transCode",transCom);
									rstValue  = comnService.selectTransComChangeForVo(transParameter);
									transCom= rstValue.getRstTransCode();
									optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,transParameter.get("dstnNation").toString());
									optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,transParameter.get("dstnNation").toString());
									expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,transParameter.get("dstnNation").toString());
									expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,transParameter.get("dstnNation").toString());
								}
								
								
								ArrayList<String> checkColumnArray = new ArrayList<String>();
								boolean checkColumn = false;
								
								checkColumnArray = checkColumnFilter2(rowPivot,optionOrderVO,optionItemVO,transCom);
								
								if(checkColumnArray.size() != 0) {
									checkColumn = true;
								}
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
									}else {
										if(columnIndex==0) {
											listFlag = false;
										}
										value="";
									}
										
									switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
										case "주문번호":{
											if(!optionOrderVO.getOrderNoYn().isEmpty()) {
												if("".equals(value)) {
													listFlag = false;
												}else {
													userOrderExcelList = new UserOrderListVO();
													userOrderExcelList.setWDate(time2);
													userOrderExcelList.setUserId(userId);
													userOrderExcelList.setWUserId(userId);
													userOrderExcelList.setWUserIp(request.getRemoteAddr());
													userOrderExcelList.setTypes("Excel");
													userOrderExcelList.setOrderType(orderType);
													
													userOrderExcelList.dncryptData();
													if(value.length() > 50) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
														value = value.substring(0,40);
													}
													userOrderExcelList.setOrderNo(value); // 커럼3
												}
											}else {
												listFlag = false;
												userOrderExcelList = new UserOrderListVO();
												if("".equals(value)) {
													String _orderNo = time2.substring(2, time2.length()-3);
													userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
												}
												userOrderExcelList.setWDate(time2);
												userOrderExcelList.setUserId(userId);
												userOrderExcelList.setWUserId(userId);
												userOrderExcelList.setWUserIp(request.getRemoteAddr());
												userOrderExcelList.setTypes("Excel");
												userOrderExcelList.setOrderType(orderType);
												userOrderExcelList.dncryptData();
												if(value.length() > 50) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
													value = value.substring(0,40);
												}
												userOrderExcelList.setOrderNo(value); // 커럼3
											}
											break;
										}
										case "주문날짜":{
											if(value.length() != 8) {
												value = "";
											}
											
											if(value.equals("")) {
												value = time1;
												listFlag = false;
											}
											userOrderExcelList.setOrderDate(value); 
											
											break;
										}
										case "출발도시코드":{
											if(!"".equals(value)) {
												userOrderExcelList.setOrgStation(value); // 컬럼0
												if(!listFlag) {
													listFlag = true;
												}
											}else {
												userOrderExcelList.setOrgStation(regercyOrgStation);
												if(!optionOrderVO.getOrgStationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ01,");
												}
											}
											break;
										}
										case "도착국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												String dstnNation = mapper.selectNationCode(value);
//												optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,dstnNation);
//												optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,dstnNation);
//												expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,dstnNation);
//												expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,dstnNation);
												userOrderExcelList.setDstnNation(value); // 컬럼1
											}else {
												userOrderExcelList.setDstnNation(""); // 컬럼1
												if(!optionOrderVO.getDstnNationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ02,");
												}
											}
											break;
										}
										case "운송업체":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTransCode(transCom);
											}else {
												if(!optionOrderVO.getTransCodeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ03,");
												}
											}
											break;
										}
										case "송하인명":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperName(value);
											}else {
												if(!optionOrderVO.getShipperNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ04,");
												}
											}
											break;
										}
										case "송하인전화번호":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP03,");
													userOrderExcelList.setShipperTel("");
												}
											}else {
												if(!optionOrderVO.getShipperTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ05,");
												}
											}
											break;
										}
											
										case "송하인핸드폰번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP04,");
													userOrderExcelList.setShipperHp("");
												}
											}else {
												if(!optionOrderVO.getShipperHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ06,");
												}
											}
											break;
										}
										case "송하인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												if(value.length() < 9) {
													userOrderExcelList.setShipperZip(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
													userOrderExcelList.setShipperZip(value.substring(0,7));
												}
											}else {
												if(!optionOrderVO.getShipperZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ07,");
												}
											}
											break;
										}
										case "송하인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCntry(value);
											}else {
												if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ08,");
												}
											}
											break;
										}
										case "송하인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCity(value);
											}else {
												if(!optionOrderVO.getShipperCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ09,");
												}
											}
											break;
										}
										case "송하인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperState(value);
											}else {
												if(!optionOrderVO.getShipperStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ10,");
												}
											}
											break;
										}
										case "송하인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddr(value);
											}else {
												if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ11,");
												}
											}
											break;
										}
										case "송하인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddrDetail(value);
											}else {
												if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ12,");
												}
											}
											break;
										}	
										case "송하인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}	
										case "송하인e-mail":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "송하인reference":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperReference(value);
											}else {
												if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 reference가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int shipperTaxType = Integer.parseInt(value);
													if (shipperTaxType < 0 || shipperTaxType > 12) {
														userOrderExcelList.setShipperTaxType(value);
													} else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getShipperTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별번호": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxNo(value);
											} else {
												if(!optionOrderVO.getShipperTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										
										case "수취인reference1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference1(value);
											}else {
												if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference1이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인reference2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference2(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference2이 비어있습니다.,");
												}
											}
											break;
										}
										case "payment":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setPayment(value);
											}
//											else {
//												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
//													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"관세 지불방식이 비어있습니다.,");
//												}
//											}
											break;
										}
										case "수취인명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인일본명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인현지이름":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인연락처1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP05,");
													userOrderExcelList.setCneeTel("");
												}
											}else {
												if(!optionOrderVO.getCneeTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
												}
											}
											userOrderExcelList.setCneeTel(value);
											break;
										}
										case "수취인연락처2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP06,");
													userOrderExcelList.setCneeHp("");
												}
											}else {
												if(!optionOrderVO.getCneeHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ16,");
												}
											}
											userOrderExcelList.setCneeHp(value);
											break;
										}
										case "수취인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.length() < 9) {
													userOrderExcelList.setCneeZip(value);
												}else {
													if (!userOrderExcelList.getDstnNation().toLowerCase().equals("brazil")) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
														userOrderExcelList.setCneeZip(value.substring(0,7));	
													}  else {
														userOrderExcelList.setCneeZip(value);	
													}
												}
											}else {
												if(!optionOrderVO.getCneeZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
												}
											}
											break;
										}
										case "수취인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCntry(value);									
											}else {
												if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ18,");
												}
											}
											break;
										}
										case "수취인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCity(value);									
											}else {
												if(!optionOrderVO.getCneeCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ19,");
												}
											}
											break;
										}
										case "수취인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeState(value);
											}else {
												if(!optionOrderVO.getCneeStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ20,");
												}
											}
											break;
										}
										case "수취인구역":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeDistrict(value);
											} else {
												if (!optionOrderVO.getCneeDistrictYn().isEmpty()) {
													userOrderExcelList.setStatus("수취인구역이 비어있습니다,");
												}
											}
											break;
										}
										case "수취인동": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeWard(value);
											} else {
												if (!optionOrderVO.getCneeWardYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 동이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인일본주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인현지주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인상세주소2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인현지상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeEmail(value);
											}else {
												if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ23,");
												}
											}
											break;
										}
										case "수취인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int cneeTaxType = Integer.parseInt(value);
													if (cneeTaxType < 0 || cneeTaxType > 12) {
														userOrderExcelList.setCneeTaxType(value);
													} else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getCneeTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인세금식별번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeTaxNo(value);
											} else {
												if(!optionOrderVO.getCneeTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인요청사항":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDlvReqMsg(value);
											}else {
												if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ24,");
												}
											}
											break;
										}
										case "상거래유형":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setMallType(value);
											}else {
												if(!optionOrderVO.getMallTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ25,");
												}
											}
											break;
										}
										case "사용용도":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.equals("")) {
													value="1";
												}
												userOrderExcelList.setGetBuy(value);
											}else {
												if(!optionOrderVO.getGetBuyYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ26,");
												}
											}
											break;
										}
										case "box수량":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												if (value.equals("0")) {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											} else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											break;
										}
										case "무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWta(value);
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
										
										case "부피무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWtc(value);
												}else {
													userOrderExcelList.setUserWtc("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtcYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
											
										case "무게단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWtUnit(value);
											}else {
												if(!optionOrderVO.getWtUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ29,");
												}
											}
											break;
										}
										case "길이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "폭":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWidth(value);
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "높이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
										case "측정단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDimUnit(value);
											}else {
												if(!optionOrderVO.getDimUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ33,");
												}
												userOrderExcelList.setDimUnit("CM");
											}
											break;
										}
										case "입고메모":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWhReqMsg(value);
											}else {
												if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ34,");
												}
											}
											break;
										}
										case "구매사이트":{
											if(!"".equals(value)) {
												userOrderExcelList.setBuySite(value);
											}else {
												if(!optionOrderVO.getBuySiteYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ35,");
												}
											}
											break;
										}
										case "hscode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setHsCode(value);
											}else {
												if(!optionItemVO.getHsCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ36,");
												}
											}
											break;
										}
										case "상품이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDetail(value);
											}else {
												if(!optionItemVO.getItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품이름2":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품현지이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										
										case "화폐단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
												if ("VNP".equals(transCom)) {
													if (!"VND".equals(value.toUpperCase())) {
														userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ60,");
													}
												}
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
											break;
										}
										case "상품단가":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setUnitValue(value);
												}else {
													userOrderExcelItem.setUnitValue("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
												}
											}else {
												if(!optionItemVO.getUnitValueYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
												}
											}
											break;
										}
										case "상품개수":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setItemCnt(value);
												}else {
													userOrderExcelItem.setItemCnt("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
												}
											}else {
												if(!optionItemVO.getItemCntYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
												}
											}
											break;
										}
										case "수량단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setQtyUnit(value);
											}else {
												if(!optionItemVO.getQtyUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ41,");
												}
											}
											break;
										}
										case "브랜드":{
											if(!"".equals(value)) {
												userOrderExcelItem.setBrand(value);
											}else {
												if(!optionItemVO.getBrandYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ42,");
												}
											}
											break;
										}
										case "itemcode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setCusItemCode(value);
											}else {
												if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
												}
											}
											break;
										}
										case "현지택배회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkCom(value);
											}else {
												if(!optionItemVO.getTrkComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ44,");
												}
											}
											break;
										}
										case "현지택배번호":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkNo(value);
											}else {
												if(!optionItemVO.getTrkNoYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ45,");
												}
											}
											break;
										}
										case "배송날짜":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkDate(value);
											}else {
												if(!optionItemVO.getTrkDateYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ46,");
												}
											}
											break;
										}
										case "상품url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemUrl(value);
											}else {
												if(!optionItemVO.getItemUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ47,");
												}
											}
											break;
										}
										case "상품사진url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemImgUrl(value);
											}else {
												if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ48,");
												}
											}
											break;
										}
										case "상품색상":
											//
											break;
										case "상품무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWta(value);
											}else {
												if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품부피무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWtc(value);
											}else {
												if(!optionItemVO.getItemUserWtcYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품무게단위":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setWtUnit(value);
											}else {
												if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ58,");
												}
											}
											break;
										case "상품size":
											//
											break;
										case "사서함반호":
											//
											break;
										case "상품종류":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDiv(value);
											}else {
												if(!optionItemVO.getItemDivYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ49,");
												}
											}
											break;
										}
										case "제조국":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCntry(value);
											}else {
												if(!optionItemVO.getMakeCntryYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ50,");
												}
											}
											break;
										}
										case "제조회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCom(value);
											}else {
												if(!optionItemVO.getMakeComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ51,");
												}
											}
											break;
										}
										case "음식포함여부" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setFood(value);
											}else {
												if(!optionOrderVO.getFoodYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ56,");
												}
											}
											break;
										}
										case "수취인id번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"수취인ID번호 오류,");
												}
											}
											break;
										}
										case "개인구별번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"개인구별번호 오류,");
												}
											}
											break;
										}
										case "화장품포함여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setCosmetic(value);
											} else {
												userOrderExcelList.setCosmetic("N");
											}
											break;
										}
										case "대면서명여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setSign(value);
											} else {
												userOrderExcelList.setSign("N");
											}
											break;
										}

										case "수출신고구분" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												if (!value.equals("N")) {
													if (value.equals("S") || value.equals("E") || value.equals("F")) {
														userOrderExcelList.setExpType(value);
													} else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분 값이 유효하지 않습니다,");
													}
												} else {
													userOrderExcelList.setExpType("N");
												}

											} else {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분이 비어있습니다,");
											}
											break;
										}
										
										case "수출화주사업자상호명": {
											if (!userOrderExcelList.getExpType().equals("N")) {
												if (!"".equals(value)) {
													if (!listFlag) {
														listFlag = true;
													}
													
													userOrderExcelList.setExpCor(value);
												} else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
												}
											} else {
												userOrderExcelList.setExpCor("");
											}
											break;
										}
										
										case "수출화주사업자대표명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRprsn(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRgstrNo(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자주소": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpAddr(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자우편번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpZip(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주통관부호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpCstCd(value);
											} else {
												userOrderExcelList.setExpCstCd("");
											}
											break;
										}
										
										case "수출대행자상호명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setAgtCor(value);
											} else {
												userOrderExcelList.setAgtCor("");
											}
											break;
										}
										
										case "수출대행자사업장일련번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtBizNo(value);
											} else {
												userOrderExcelList.setAgtBizNo("");
											}
											break;
										}

										case "수출대행자통관부호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtCstCd(value);
											} else {
												userOrderExcelList.setAgtCstCd("");
											}
											break;
										}

										case "수출면장번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpNo(value);
											} else {
												userOrderExcelList.setExpNo("");
											}
											break;
										}
										
										case "ioss번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxType("6");
												userOrderExcelList.setShipperTaxNo(value);
											}
											break;
										}

										/*러시아 추가 부분*/
										case "수취인id번호발급일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdDate(value);
											}else {
												if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 발급일 오류,");
												}
											}
											break;
										}
										case "수취인id번호발급기관정보" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdAuthority(value);
											}else {
												if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 번호 발급기관 정보 오류,");
												}
											}
											break;
										}
										case "수취인생년월일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeBirth(value);
											}else {
												if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 생년월일 오류,");
												}
											}
											break;
										}
										case "수취인tax번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTaxNo(value);
											}else {
												if(!optionOrderVO.getTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인TAX 번호 오류,");
												}
											}
											break;
										}
										case "상품설명" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemExplan(value);
											}else {
												if(!optionItemVO.getItemExplanYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"상품설명 오류,");
												}
											}
											break;
										}
										case "상품바코드번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemBarcode(value);
											}else {
												if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"바코드 오류,");
												}
											}
											break;
										}
										case "box번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setInBoxNum(value);
											}else {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"BOX번호 오류,");
											}
											break;
										}
									}
								}// 현재row vo에 set 완료
								// vo 검증로직은 여기
//								resultList.add(vo); // 검증된 vo 리스트에 추가
								if(listFlag) {
									if(shipperInfoYn) {
										UserOrderListVO userOrderExcelListTemp = new UserOrderListVO();
										userOrderExcelListTemp = mapper.selectUserForExcel(userId);
										userOrderExcelListTemp.dncryptData();
										
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ04,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ05,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ06,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ07,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ08,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ09,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ10,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ11,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ12,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ13,", ""));
										
										userOrderExcelList.setShipperName(userOrderExcelListTemp.getShipperName());
										userOrderExcelList.setShipperTel(userOrderExcelListTemp.getShipperTel());
										userOrderExcelList.setShipperHp(userOrderExcelListTemp.getShipperHp());
										userOrderExcelList.setShipperZip(userOrderExcelListTemp.getShipperZip());
										userOrderExcelList.setShipperCity(userOrderExcelListTemp.getShipperCity());
										userOrderExcelList.setShipperState(userOrderExcelListTemp.getShipperState());
										userOrderExcelList.setShipperAddr(userOrderExcelListTemp.getShipperAddr());
										userOrderExcelList.setShipperAddrDetail(userOrderExcelListTemp.getShipperAddrDetail());
										userOrderExcelList.setUserEmail(userOrderExcelListTemp.getUserEmail());
									}
									if((userOrderExcelList.getUserLength() != null && userOrderExcelList.getUserWidth() != null && userOrderExcelList.getUserHeight() !=null && userOrderExcelList.getDimUnit() != null) && (!userOrderExcelList.getUserLength().equals("")&& !userOrderExcelList.getUserWidth().equals("")&& !userOrderExcelList.getUserHeight().equals("")&& !userOrderExcelList.getDimUnit().equals(""))){
										if(userOrderExcelList.getDimUnit().equals("IN") || userOrderExcelList.getDimUnit().equals("INCH")) {
											userOrderExcelList.setDimUnit("IN");
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 166;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
										if(userOrderExcelList.getDimUnit().equals("CM")){
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 5000;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
									}
									
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									/* nno = comnService.selectNNO(); */
									userOrderExcelList.setNno(nno);
									userOrderExcelList.setUploadType("EXCEL");
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08,");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									if(userOrderExcelList.getOrderNo().equals("")) {
										String _orderNo = time2.substring(2, time2.length()-3);
										userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
									}
									userOrderExcelList.setTransCode(transCom);
									if(userOrderExcelList.getOrderDate() == "" || userOrderExcelList.getOrderDate() == null)
										userOrderExcelList.setOrderDate(time1);
									
									// 수출신고
									if (!userOrderExcelList.getExpType().equals("N")) {
										
										if (userOrderExcelList.getExpType().toUpperCase().equals("S") || 
												userOrderExcelList.getExpType().toUpperCase().equals("F") ||
												userOrderExcelList.getExpType().toUpperCase().equals("E")) {
											
											if (userOrderExcelList.getExpCor().equals("") || userOrderExcelList.getExpCor() == null) {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
											}
											
											if (userOrderExcelList.getExpType().toUpperCase().equals("E")) {
												if (userOrderExcelList.getExpRprsn().equals("") || userOrderExcelList.getExpRprsn() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
												
												if (userOrderExcelList.getExpRgstrNo().equals("") || userOrderExcelList.getExpRgstrNo() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}

												if (userOrderExcelList.getExpAddr().equals("") || userOrderExcelList.getExpAddr() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}

												if (userOrderExcelList.getExpZip().equals("") || userOrderExcelList.getExpZip() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											
											comnServiceImpl.execExportDeclareInfo(userOrderExcelList);
										}
									}
									
									userOrderExcelList.encryptData();
									if(checkColumn) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+",필수 컬럼이 부족합니다.("+checkColumnArray.toString()+")");
									}
									mapper.insertUserOrderListTemp(userOrderExcelList);
									
									
									/*
									if(userOrderExcelList.getExpValue().equals("noExplicence")) {
										
									}else {
										insertExpLicenceInfo(userOrderExcelList);
									}
									*/
									regercyOrgStation = userOrderExcelList.getOrgStation();
									userOrderExcelList.setOrderNo("");

								}else {
									//item insert 주문번호가 같은 상품 여러개
								}
								userOrderExcelItem.setTransCode(transCom);
								userOrderExcelItem.setNno(nno);
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								userOrderExcelItem.setOrgStation(regercyOrgStation);
								userOrderExcelItem.setNationCode(userOrderExcelList.getDstnNation());
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								UserOrderItemVO temp = new UserOrderItemVO();
								/*
								 * temp = userOrderExcelItem; userOrderExcelItemList.add(temp);
								 */
								subNo++;
							}else {
								break;
							}
							
						}
						// 엑셀 전체row수가 500이상인 경우,
//						if (rows > 500) 
//						{
//							if (resultList.size() >= 500) 
//							{ // 500건 단위로 잘라서 DB INSERT
//								result = customerRepository.insertExcel(resultList);
//								FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//								resultList = new ArrayList<>();
//							}
//						}


					}
					// 엑셀 전체row수가 500이하인 경우,
//					if (rows <= 500) { // 한꺼번에 DB INSERT(LIST size는 반드시 500이하임)
//						result = customerRepository.insertExcel(resultList);
//						FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//					}
					/* 	mapper.insertUserOrderItemListTemp(userOrderExcelItemList) */;

				}
				
			} catch (IOException e) {
				e.printStackTrace();
				result = "F";
			} catch (Exception e) {
				e.printStackTrace();
				result = "F";
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				//delFile.delete();
			}
		}
		result = "등록되었습니다.";
		return result;
	}
	
	
	private ArrayList<String> checkColumnFilter2(HSSFRow rowPivot,OrderListOptionVO optionOrderVO, OrderItemOptionVO optionItemVO, String transCom) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> temp = new ArrayList<String> ();
		ArrayList<String> returnVal = new ArrayList<String> ();
		for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) {
			temp.add(rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase());
		}
		
		if(!optionOrderVO.getBoxCntYn().isEmpty()) {
			if(!temp.remove("box수량")) {
				returnVal.add("box수량");
			}
		}
		
		if(!optionOrderVO.getBuySiteYn().isEmpty()) {
			if(!temp.remove("구매사이트")) {
				returnVal.add("구매사이트");
			}
		}
		
		if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
			if(!temp.remove("수취인상세주소")) {
				returnVal.add("수취인상세주소");
			}
		}
		
		if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
			if(!temp.remove("수취인주소")) {
				returnVal.add("수취인주소");
			}
		}
		
		if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
			if(!temp.remove("수취인생년월일")) {
				returnVal.add("수취인생년월일");
			}
		}
		
		if(!optionOrderVO.getCneeCityYn().isEmpty()) {
			if(!temp.remove("수취인도시")) {
				returnVal.add("수취인도시");
			}
		}
		
		if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
			if(!temp.remove("수취인국가")) {
				returnVal.add("수취인국가");
			}
		}
		
		if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
			if(!temp.remove("수취인email") && !temp.remove("수취인e-mail")) {
				returnVal.add("수취인email");
			}
		}
		
		if(!optionOrderVO.getCneeHpYn().isEmpty()) {
			if(!temp.remove("수취인연락처2")) {
				returnVal.add("수취인연락처2");
			}
		}
		
		if(!optionOrderVO.getCneeNameYn().isEmpty()) {
			if(!temp.remove("수취인명")) {
				returnVal.add("수취인명");
			}
		}
		
		if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
			if(!temp.remove("수취인reference1")) {
				returnVal.add("수취인reference1");
			}
		}
		
		if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
			if(!temp.remove("수취인reference2")) {
				returnVal.add("수취인reference2");
			}
		}
		
		if(!optionOrderVO.getCneeStateYn().isEmpty()) {
			if(!temp.remove("수취인주")) {
				returnVal.add("수취인주");
			}
		}
		
		if(!optionOrderVO.getCneeTelYn().isEmpty()) {
			if(!temp.remove("수취인연락처1")) {
				returnVal.add("수취인연락처1");
			}
		}
		
		if(!optionOrderVO.getCneeZipYn().isEmpty()) {
			if(!temp.remove("수취인우편주소")) {
				returnVal.add("수취인우편주소");
			}
		}
		
		if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
			if(transCom.equals("CSE") || transCom.equals("GTS")) {
				if(!temp.remove("수취인id번호")) {
					returnVal.add("수취인id번호");
				}
			}else {
				if(!temp.remove("개인구별번호")) {
					returnVal.add("개인구별번호");
				}
			}
		}
		
		if(!optionOrderVO.getDimUnitYn().isEmpty()) {
			if(!temp.remove("측정단위")) {
				returnVal.add("측정단위");
			}
		}
		
		if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
			if(!temp.remove("수취인요청사항")) {
				returnVal.add("수취인요청사항");
			}
		}
		
		if(!optionOrderVO.getDstnNationYn().isEmpty()) {
			if(!temp.remove("도착국가")) {
				returnVal.add("도착국가");
			}
		}
		
		if(!optionOrderVO.getFoodYn().isEmpty()) {
			if(!temp.remove("음식포함여부")) {
				returnVal.add("음식포함여부");
			}
		}
		
		if(!optionOrderVO.getGetBuyYn().isEmpty()) {
			if(!temp.remove("사용용도")) {
				returnVal.add("사용용도");
			}
		}
		
		if(!optionOrderVO.getMallTypeYn().isEmpty()) {
			if(!temp.remove("상거래유형")) {
				returnVal.add("상거래유형");
			}
		}
		
		if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
			if(!temp.remove("수취인id번호발급기관정보")) {
				returnVal.add("수취인id번호발급기관정보");
			}
		}
		
		if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
			if(!temp.remove("수취인id번호발급일")) {
				returnVal.add("수취인id번호발급일");
			}
		}
		
		if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
			if(!temp.remove("수취인현지상세주소") && !temp.remove("수취인상세주소2")) {
				returnVal.add("수취인현지상세주소");
			}
		}
		
		if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
			if(!temp.remove("수취인현지주소") && !temp.remove("수취인일본주소")) {
				returnVal.add("수취인현지주소");
			}
		}
		
		if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
			if(!temp.remove("수취인현지이름") && !temp.remove("수취인일본명")) {
				returnVal.add("수취인현지이름");
			}
		}
		
		if(!optionOrderVO.getOrgStationYn().isEmpty()) {
			if(!temp.remove("출발도시코드")) {
				returnVal.add("출발도시코드");
			}
		}
		
		if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
			if(!temp.remove("송하인상세주소")) {
				returnVal.add("송하인상세주소");
			}
		}
		
		if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
			if(!temp.remove("송하인주소")) {
				returnVal.add("송하인주소");
			}
		}
		
		if(!optionOrderVO.getShipperCityYn().isEmpty()) {
			if(!temp.remove("송하인도시")) {
				returnVal.add("송하인도시");
			}
		}
		
		if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
			if(!temp.remove("송하인국가")) {
				returnVal.add("송하인국가");
			}
		}
		
		if(!optionOrderVO.getShipperHpYn().isEmpty()) {
			if(!temp.remove("송하인핸드폰번호")) {
				returnVal.add("송하인핸드폰번호");
			}
		}
		
		if(!optionOrderVO.getShipperNameYn().isEmpty()) {
			if(!temp.remove("송하인명")) {
				returnVal.add("송하인명");
			}
		}
		
		if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
			if(!temp.remove("송하인reference")) {
				returnVal.add("송하인reference");
			}
		}
		
		if(!optionOrderVO.getShipperStateYn().isEmpty()) {
			if(!temp.remove("송하인주")) {
				returnVal.add("송하인주");
			}
		}
		
		if(!optionOrderVO.getShipperTelYn().isEmpty()) {
			if(!temp.remove("송하인전화번호")) {
				returnVal.add("송하인전화번호");
			}
		}
		
		if(!optionOrderVO.getShipperZipYn().isEmpty()) {
			if(!temp.remove("송하인우편주소")) {
				returnVal.add("송하인우편주소");
			}
		}
		
		if(!optionOrderVO.getTaxNoYn().isEmpty()) {
			if(!temp.remove("수취인tax번호")) {
				returnVal.add("수취인tax번호");
			}
		}
		
		if(!optionOrderVO.getUserEmailYn().isEmpty()) {
			if(!temp.remove("송하인email") && !temp.remove("송하인e-mail")) {
				returnVal.add("송하인email");
			}
		}
		
		if(!optionOrderVO.getUserHeightYn().isEmpty()) {
			if(!temp.remove("높이")) {
				returnVal.add("높이");
			}
		}
		
		if(!optionOrderVO.getUserLengthYn().isEmpty()) {
			if(!temp.remove("길이")) {
				returnVal.add("길이");
			}
		}
		
		if(!optionOrderVO.getUserWidthYn().isEmpty()) {
			if(!temp.remove("폭")) {
				returnVal.add("폭");
			}
		}
		
		if(!optionOrderVO.getUserWtaYn().isEmpty()) {
			if(!temp.remove("무게")) {
				returnVal.add("무게");
			}
		}
		
		if(!optionOrderVO.getUserWtcYn().isEmpty()) {
			if(!temp.remove("부피무게")) {
				returnVal.add("부피무게");
			}
		}
		
		if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
			if(!temp.remove("입고메모")) {
				returnVal.add("입고메모");
			}
		}
		
		if(!optionOrderVO.getWtUnitYn().isEmpty()) {
			if(!temp.remove("무게단위")) {
				returnVal.add("무게단위");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
			if(!temp.remove("화폐단위")) {
				returnVal.add("화폐단위");
			}
		}
		
		if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
			if(!temp.remove("itemcode")) {
				returnVal.add("itemcode");
			}
		}
		
		if(!optionItemVO.getHsCodeYn().isEmpty()) {
			if(!temp.remove("hscode")) {
				returnVal.add("hscode");
			}
		}
		
		if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
			if(!temp.remove("상품바코드번호")) {
				returnVal.add("상품바코드번호");
			}
		}
		
		if(!optionItemVO.getItemCntYn().isEmpty()) {
			if(!temp.remove("상품개수")) {
				returnVal.add("상품개수");
			}
		}
		
		if(!optionItemVO.getItemDetailYn().isEmpty()) {
			if(!temp.remove("상품이름")) {
				returnVal.add("상품이름");
			}
		}
		
		if(!optionItemVO.getItemDivYn().isEmpty()) {
			if(!temp.remove("상품종류")) {
				returnVal.add("상품종류");
			}
		}
		
		if(!optionItemVO.getItemExplanYn().isEmpty()) {
			if(!temp.remove("상품설명")) {
				returnVal.add("상품설명");
			}
		}
		
		if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
			if(!temp.remove("상품사진url")) {
				returnVal.add("상품사진url");
			}
		}
		
		if(!optionItemVO.getItemMeterialYn().isEmpty()) {
			if(!temp.remove("상품재질")) {
				returnVal.add("상품재질");
			}
		}
		
		if(!optionItemVO.getItemUrlYn().isEmpty()) {
			if(!temp.remove("상품url")) {
				returnVal.add("상품url");
			}
		}
		
		if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
			if(!temp.remove("상품무게")) {
				returnVal.add("상품무게");
			}
		}
		
		if(!optionItemVO.getItemUserWtcYn().isEmpty()) {
			if(!temp.remove("상품부피무게")) {
				returnVal.add("상품부피무게");
			}
		}
		
		if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
			if(!temp.remove("상품무게단위")) {
				returnVal.add("상품무게단위");
			}
		}
		
		if(!optionItemVO.getMakeCntryYn().isEmpty()) {
			if(!temp.remove("제조국")) {
				returnVal.add("제조국");
			}
		}
		
		if(!optionItemVO.getMakeComYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("제조회사")) {
				returnVal.add("제조회사");
			}
		}
		
		if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
			if(!temp.remove("상품현지이름") && !temp.remove("상품이름2") ) {
				returnVal.add("상품현지이름");
			}
		}
		
		if(!optionItemVO.getQtyUnitYn().isEmpty()) {
			if(!temp.remove("수량단위")) {
				returnVal.add("수량단위");
			}
		}
		
		if(!optionItemVO.getTrkComYn().isEmpty()) {
			if(!temp.remove("현지택배회사")) {
				returnVal.add("현지택배회사");
			}
		}
		
		if(!optionItemVO.getTrkDateYn().isEmpty()) {
			if(!temp.remove("배송날짜")) {
				returnVal.add("배송날짜");
			}
		}
		
		if(!optionItemVO.getTrkNoYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("현지택배번호")) {
				returnVal.add("현지택배번호");
			}
		}
		
		if(!optionItemVO.getUnitValueYn().isEmpty()) {
			if(!temp.remove("상품단가")) {
				returnVal.add("상품단가");
			}
		}
		
//		if(!optionItemVO.getUserIdYn().isEmpty()) {
//			if(!temp.remove("브랜드")) {
//				returnVal.add("브랜드");
//			}
//		}
		
		
		return returnVal;
	}
	
	 
	private ArrayList<String> checkColumnFilter(XSSFRow rowPivot,OrderListOptionVO optionOrderVO, OrderItemOptionVO optionItemVO, String transCom) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> temp = new ArrayList<String> ();
		ArrayList<String> returnVal = new ArrayList<String> ();
		for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) {
			temp.add(rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase());
		}
		
		if(!optionOrderVO.getBoxCntYn().isEmpty()) {
			if(!temp.remove("box수량")) {
				returnVal.add("box수량");
			}
		}
		
		if(!optionOrderVO.getBuySiteYn().isEmpty()) {
			if(!temp.remove("구매사이트")) {
				returnVal.add("구매사이트");
			}
		}
		
		if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
			if(!temp.remove("수취인상세주소")) {
				returnVal.add("수취인상세주소");
			}
		}
		
		if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
			if(!temp.remove("수취인주소")) {
				returnVal.add("수취인주소");
			}
		}
		
		if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
			if(!temp.remove("수취인생년월일")) {
				returnVal.add("수취인생년월일");
			}
		}
		
		if(!optionOrderVO.getCneeCityYn().isEmpty()) {
			if(!temp.remove("수취인도시")) {
				returnVal.add("수취인도시");
			}
		}
		
		if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
			if(!temp.remove("수취인국가")) {
				returnVal.add("수취인국가");
			}
		}
		
		if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
			if(!temp.remove("수취인email") && !temp.remove("수취인e-mail")) {
				returnVal.add("수취인email");
			}
		}
		
		if(!optionOrderVO.getCneeHpYn().isEmpty()) {
			if(!temp.remove("수취인연락처2")) {
				returnVal.add("수취인연락처2");
			}
		}
		
		if(!optionOrderVO.getCneeNameYn().isEmpty()) {
			if(!temp.remove("수취인명")) {
				returnVal.add("수취인명");
			}
		}
		
		if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
			if(!temp.remove("수취인reference1")) {
				returnVal.add("수취인reference1");
			}
		}
		
		if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
			if(!temp.remove("수취인reference2")) {
				returnVal.add("수취인reference2");
			}
		}
		
		if(!optionOrderVO.getCneeStateYn().isEmpty()) {
			if(!temp.remove("수취인주")) {
				returnVal.add("수취인주");
			}
		}
		
		if(!optionOrderVO.getCneeTelYn().isEmpty()) {
			if(!temp.remove("수취인연락처1")) {
				returnVal.add("수취인연락처1");
			}
		}
		
		if(!optionOrderVO.getCneeZipYn().isEmpty()) {
			if(!temp.remove("수취인우편주소")) {
				returnVal.add("수취인우편주소");
			}
		}
		
		if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
			if(transCom.equals("CSE") || transCom.equals("GTS")) {
				if(!temp.remove("수취인id번호")) {
					returnVal.add("수취인id번호");
				}
			}else {
				if(!temp.remove("개인구별번호")) {
					returnVal.add("개인구별번호");
				}
			}
		}
		
		if(!optionOrderVO.getDimUnitYn().isEmpty()) {
			if(!temp.remove("측정단위")) {
				returnVal.add("측정단위");
			}
		}
		
		if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
			if(!temp.remove("수취인요청사항")) {
				returnVal.add("수취인요청사항");
			}
		}
		
		if(!optionOrderVO.getDstnNationYn().isEmpty()) {
			if(!temp.remove("도착국가")) {
				returnVal.add("도착국가");
			}
		}
		
		if(!optionOrderVO.getFoodYn().isEmpty()) {
			if(!temp.remove("음식포함여부")) {
				returnVal.add("음식포함여부");
			}
		}
		
		if(!optionOrderVO.getGetBuyYn().isEmpty()) {
			if(!temp.remove("사용용도")) {
				returnVal.add("사용용도");
			}
		}
		
		if(!optionOrderVO.getMallTypeYn().isEmpty()) {
			if(!temp.remove("상거래유형")) {
				returnVal.add("상거래유형");
			}
		}
		
		if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
			if(!temp.remove("수취인id번호발급기관정보")) {
				returnVal.add("수취인id번호발급기관정보");
			}
		}
		
		if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
			if(!temp.remove("수취인id번호발급일")) {
				returnVal.add("수취인id번호발급일");
			}
		}
		
		if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
			if(!temp.remove("수취인현지상세주소") && !temp.remove("수취인상세주소2")) {
				returnVal.add("수취인현지상세주소");
			}
		}
		
		if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
			if(!temp.remove("수취인현지주소") && !temp.remove("수취인일본주소")) {
				returnVal.add("수취인현지주소");
			}
		}
		
		if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
			if(!temp.remove("수취인현지이름") && !temp.remove("수취인일본명")) {
				returnVal.add("수취인현지이름");
			}
		}
		
		if(!optionOrderVO.getOrgStationYn().isEmpty()) {
			if(!temp.remove("출발도시코드")) {
				returnVal.add("출발도시코드");
			}
		}
		
		if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
			if(!temp.remove("송하인상세주소")) {
				returnVal.add("송하인상세주소");
			}
		}
		
		if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
			if(!temp.remove("송하인주소")) {
				returnVal.add("송하인주소");
			}
		}
		
		if(!optionOrderVO.getShipperCityYn().isEmpty()) {
			if(!temp.remove("송하인도시")) {
				returnVal.add("송하인도시");
			}
		}
		
		if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
			if(!temp.remove("송하인국가")) {
				returnVal.add("송하인국가");
			}
		}
		
		if(!optionOrderVO.getShipperHpYn().isEmpty()) {
			if(!temp.remove("송하인핸드폰번호")) {
				returnVal.add("송하인핸드폰번호");
			}
		}
		
		if(!optionOrderVO.getShipperNameYn().isEmpty()) {
			if(!temp.remove("송하인명")) {
				returnVal.add("송하인명");
			}
		}
		
		if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
			if(!temp.remove("송하인reference")) {
				returnVal.add("송하인reference");
			}
		}
		
		if(!optionOrderVO.getShipperStateYn().isEmpty()) {
			if(!temp.remove("송하인주")) {
				returnVal.add("송하인주");
			}
		}
		
		if(!optionOrderVO.getShipperTelYn().isEmpty()) {
			if(!temp.remove("송하인전화번호")) {
				returnVal.add("송하인전화번호");
			}
		}
		
		if(!optionOrderVO.getShipperZipYn().isEmpty()) {
			if(!temp.remove("송하인우편주소")) {
				returnVal.add("송하인우편주소");
			}
		}
		
		if(!optionOrderVO.getTaxNoYn().isEmpty()) {
			if(!temp.remove("수취인tax번호")) {
				returnVal.add("수취인tax번호");
			}
		}
		
		if(!optionOrderVO.getUserEmailYn().isEmpty()) {
			if(!temp.remove("송하인email") && !temp.remove("송하인e-mail")) {
				returnVal.add("송하인email");
			}
		}
		
		if(!optionOrderVO.getUserHeightYn().isEmpty()) {
			if(!temp.remove("높이")) {
				returnVal.add("높이");
			}
		}
		
		if(!optionOrderVO.getUserLengthYn().isEmpty()) {
			if(!temp.remove("길이")) {
				returnVal.add("길이");
			}
		}
		
		if(!optionOrderVO.getUserWidthYn().isEmpty()) {
			if(!temp.remove("폭")) {
				returnVal.add("폭");
			}
		}
		
		if(!optionOrderVO.getUserWtaYn().isEmpty()) {
			if(!temp.remove("무게")) {
				returnVal.add("무게");
			}
		}
		
		if(!optionOrderVO.getUserWtcYn().isEmpty()) {
			if(!temp.remove("부피무게")) {
				returnVal.add("부피무게");
			}
		}
		
		if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
			if(!temp.remove("입고메모")) {
				returnVal.add("입고메모");
			}
		}
		
		if(!optionOrderVO.getWtUnitYn().isEmpty()) {
			if(!temp.remove("무게단위")) {
				returnVal.add("무게단위");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
			if(!temp.remove("화폐단위")) {
				returnVal.add("화폐단위");
			}
		}
		
		if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
			if(!temp.remove("itemcode")) {
				returnVal.add("itemcode");
			}
		}
		
		if(!optionItemVO.getHsCodeYn().isEmpty()) {
			if(!temp.remove("hscode")) {
				returnVal.add("hscode");
			}
		}
		
		if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
			if(!temp.remove("상품바코드번호")) {
				returnVal.add("상품바코드번호");
			}
		}
		
		if(!optionItemVO.getItemCntYn().isEmpty()) {
			if(!temp.remove("상품개수")) {
				returnVal.add("상품개수");
			}
		}
		
		if(!optionItemVO.getItemDetailYn().isEmpty()) {
			if(!temp.remove("상품이름")) {
				returnVal.add("상품이름");
			}
		}
		
		if(!optionItemVO.getItemDivYn().isEmpty()) {
			if(!temp.remove("상품종류")) {
				returnVal.add("상품종류");
			}
		}
		
		if(!optionItemVO.getItemExplanYn().isEmpty()) {
			if(!temp.remove("상품설명")) {
				returnVal.add("상품설명");
			}
		}
		
		if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
			if(!temp.remove("상품사진url")) {
				returnVal.add("상품사진url");
			}
		}
		
		if(!optionItemVO.getItemMeterialYn().isEmpty()) {
			if(!temp.remove("상품재질")) {
				returnVal.add("상품재질");
			}
		}
		
		if(!optionItemVO.getItemUrlYn().isEmpty()) {
			if(!temp.remove("상품url")) {
				returnVal.add("상품url");
			}
		}
		
		if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
			if(!temp.remove("상품무게")) {
				returnVal.add("상품무게");
			}
		}
		
		if(!optionItemVO.getItemUserWtcYn().isEmpty()) {
			if(!temp.remove("상품부피무게")) {
				returnVal.add("상품부피무게");
			}
		}
		
		if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
			if(!temp.remove("상품무게단위")) {
				returnVal.add("상품무게단위");
			}
		}
		
		if(!optionItemVO.getMakeCntryYn().isEmpty()) {
			if(!temp.remove("제조국")) {
				returnVal.add("제조국");
			}
		}
		
		if(!optionItemVO.getMakeComYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("제조회사")) {
				returnVal.add("제조회사");
			}
		}
		
		if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
			if(!temp.remove("상품현지이름") && !temp.remove("상품이름2") ) {
				returnVal.add("상품현지이름");
			}
		}
		
		if(!optionItemVO.getQtyUnitYn().isEmpty()) {
			if(!temp.remove("수량단위")) {
				returnVal.add("수량단위");
			}
		}
		
		if(!optionItemVO.getTrkComYn().isEmpty()) {
			if(!temp.remove("현지택배회사")) {
				returnVal.add("현지택배회사");
			}
		}
		
		if(!optionItemVO.getTrkDateYn().isEmpty()) {
			if(!temp.remove("배송날짜")) {
				returnVal.add("배송날짜");
			}
		}
		
		if(!optionItemVO.getTrkNoYn().isEmpty()) {
			if(!temp.remove("브랜드")) {
				returnVal.add("브랜드");
			}
		}
		
		if(!optionItemVO.getBrandYn().isEmpty()) {
			if(!temp.remove("현지택배번호")) {
				returnVal.add("현지택배번호");
			}
		}
		
		if(!optionItemVO.getUnitValueYn().isEmpty()) {
			if(!temp.remove("상품단가")) {
				returnVal.add("상품단가");
			}
		}
		
//		if(!optionItemVO.getUserIdYn().isEmpty()) {
//			if(!temp.remove("브랜드")) {
//				returnVal.add("브랜드");
//			}
//		}
		
		
		return returnVal;
	}

	@Override
	public String insertExcelDataForSagawaManifast(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception {
		String result= "F";
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmssSS");
		Date time = new Date();
		
		String time1 = format1.format(time);
		
		
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			/*
			 * List<UserOrderItemVO> userOrderExcelItemList = new
			 * ArrayList<UserOrderItemVO>();
			 */
			/* UserOrderItemVO userOrderExcelItem = new UserOrderItemVO(); */
			UserOrderListVO userOrderExcelList = new UserOrderListVO();
			userOrderExcelList = mapper.selectUserForExcel(userId);
			/*
			 * userOrderExcelItem.setWUserId(userId);
			 * userOrderExcelItem.setWUserIp(request.getRemoteAddr());
			 * userOrderExcelItem.setWDate(time1);
			 */
			userOrderExcelList.dncryptData();
			userOrderExcelList.setWUserId(userId);
			userOrderExcelList.setWUserIp(request.getRemoteAddr());
			
			userOrderExcelList.setOrderType(orderType);
			
			String nno="";
			
			
//			List<CustomerDetail> resultList = new ArrayList();
//			Long customerNo = customerRepository.getSequence(vo); // 고객번호 획득
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
							userOrderExcelItem.setUserId(userId);
							userOrderExcelItem.setWUserId(userId);
							userOrderExcelItem.setWUserIp(request.getRemoteAddr());
							userOrderExcelItem.setWDate(time1);
							
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
							/*
							 * list에 담을 현재row정보를 저장할 vo생성
							 */
//							CustomerDetail vo = new CustomerDetail();
//							vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//							vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//							customerNo = customerNo + 1;
//							vo.setStat("Y"); // 여부

							String value = "";
							
							boolean listFlag = true;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) 
							{
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:
									cell.setCellType( HSSFCell.CELL_TYPE_STRING );
									value = cell.getStringCellValue();
									if(value.split("[.]")[1].equals("0")) {
										value=value.split("[.]")[0];
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
								case 0:
									// 컬럼0
									userOrderExcelList.setOrderNo(value);
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus("TMP08");
									}else {
										userOrderExcelList.setStatus("");
									}
									
									break;
								case 1:
									// 컬럼1
									nno = comnService.selectNNO();
									userOrderExcelList.setNno(nno);
									userOrderExcelList.setHawbNo(value); 
									break;
								case 2:
									break;
								case 3:
									// 컬럼3
									if(!value.equals("")) {
										userOrderExcelList.setShipperName(value); 
									}
									break;
								case 4:
									// 컬럼4
									if(!value.equals("")) {
										userOrderExcelList.setShipperTel(value); 
									}
									break;
								case 5:
									// 컬럼5
									if(!value.equals("")) {
										userOrderExcelList.setShipperAddr(value); 
									}else {
										userOrderExcelList.setShipperAddr(userOrderExcelList.getShipperAddr()+" "+userOrderExcelList.getShipperAddrDetail()); 
									}
									break;
								case 6:
									// 컬럼6
									userOrderExcelList.setCneeTel(value); 
									break;
								case 7:
									// 컬럼7
									userOrderExcelList.setNativeCneeName(value); 
									break;
								case 8:
									// 컬럼8
									userOrderExcelList.setCneeName(value); 
									break;
								case 9:
									// 컬럼9
									String restr = String.format("%07d", Integer.parseInt(value.replaceAll("[^0-9]","")));

									userOrderExcelList.setCneeZip(value.replaceAll("[^0-9]","")); 
									break;
								case 10:
									// 컬럼10
									userOrderExcelList.setCneeAddr(value);
									break;
								case 11:
									userOrderExcelList.setUserWta(value);
									// 컬럼11
									break;
								case 12:
									userOrderExcelList.setUserLength(value);
									// 컬럼12
									break;
								case 13:
									// 컬럼13
									userOrderExcelList.setUserWidth(value); 
									break;
								case 14:
									// 컬럼14
									userOrderExcelList.setUserHeight(value); 
									break;
								case 15:
									 // 컬럼15 Declare Quantity
									subNo = 1;
									break;
								case 16:
									// 컬럼16 Currency
									userOrderExcelItem.setChgCurrency(value);
									break;
								case 17:
									// 컬럼17 1st declare
									userOrderExcelItem.setItemDetail(value);
									break;
								case 18:
									userOrderExcelItem.setItemMeterial(value); // 컬럼18
									break;
								case 19:
									userOrderExcelItem.setItemCnt(value); // 컬럼19
									break;
								case 20:
									userOrderExcelItem.setUnitValue(value); // 컬럼20
									
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									break;
								case 21:
									// 컬럼21
									subNo = 2; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 22:
									// 컬럼22
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 23:
									// 컬럼23
									userOrderExcelItem.setItemCnt(value); 
									break;
								case 24:
									/* userOrderExcelList.setTransCode(value); */ // 컬럼24
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem);
									}
									 
									break;
								case 25:
									// 컬럼25
									subNo = 3; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 26:
									// 컬럼26
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 27:
									// 컬럼27
									userOrderExcelItem.setItemCnt(value);
									break;
								case 28:
									// 컬럼28
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 29:
									// 컬럼29
									subNo = 4; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 30:
									// 컬럼30
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 31:
									// 컬럼31
									userOrderExcelItem.setItemCnt(value);
									break;
								case 32:
									// 컬럼32
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem);
									}
									break;
								case 33:
									// 컬럼33
									subNo = 5; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 34:
									// 컬럼34
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 35:
									// 컬럼35
									userOrderExcelItem.setItemCnt(value);
									break;
								case 36:
									// 컬럼36
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 37:
									// 컬럼37
									userOrderExcelList.setOrgStation(value);
									break;
								}
							} // 현재row vo에 set 완료
								// vo 검증로직은 여기
							//하드코딩 이거 수정 되어야 함
							userOrderExcelList.setOrgStation("441");
							userOrderExcelList.setDstnNation("JP");
							userOrderExcelList.setDstnStation("JP");
							userOrderExcelList.setTransCode("SAGAWA");
							//하드코딩
							userOrderExcelList.encryptData();
							mapper.insertUserOrderListTemp(userOrderExcelList);
						}
					}
				}else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
							userOrderExcelItem.setUserId(userId);
							userOrderExcelItem.setWUserId(userId);
							userOrderExcelItem.setWUserIp(request.getRemoteAddr());
							userOrderExcelItem.setWDate(time1);
							
							int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수

							String value = "";
							
							boolean listFlag = true;
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) 
							{
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
								case HSSFCell.CELL_TYPE_NUMERIC:
									cell.setCellType( HSSFCell.CELL_TYPE_STRING );
									value = cell.getStringCellValue();
									if(value.split("[.]")[1].equals("0")) {
										value=value.split("[.]")[0];
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
								case 0:
									// 컬럼0
									userOrderExcelList.setOrderNo(value);
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus("TMP08");
									}else {
										userOrderExcelList.setStatus("");
									}
									
									break;
								case 1:
									// 컬럼1
									nno = comnService.selectNNO();
									userOrderExcelList.setNno(nno);
									userOrderExcelList.setHawbNo(value); 
									break;
								case 2:
									break;
								case 3:
									// 컬럼3
									if(!value.equals("")) {
										userOrderExcelList.setShipperName(value); 
									}
									break;
								case 4:
									// 컬럼4
									if(!value.equals("")) {
										userOrderExcelList.setShipperTel(value); 
									}
									break;
								case 5:
									// 컬럼5
									if(!value.equals("")) {
										userOrderExcelList.setShipperAddr(value); 
									}else {
										userOrderExcelList.setShipperAddr(userOrderExcelList.getShipperAddr()+" "+userOrderExcelList.getShipperAddrDetail()); 
									}
									break;
								case 6:
									// 컬럼6
									userOrderExcelList.setCneeTel(value); 
									break;
								case 7:
									// 컬럼7
									userOrderExcelList.setNativeCneeName(value); 
									break;
								case 8:
									// 컬럼8
									userOrderExcelList.setCneeName(value); 
									break;
								case 9:
									// 컬럼9
									String restr = String.format("%07d", Integer.parseInt(value.replaceAll("[^0-9]","")));
									userOrderExcelList.setCneeZip(restr); 
									break;
								case 10:
									// 컬럼10
									userOrderExcelList.setCneeAddr(value);
									break;
								case 11:
									userOrderExcelList.setUserWta(value);
									// 컬럼11
									break;
								case 12:
									userOrderExcelList.setUserLength(value);
									// 컬럼12
									break;
								case 13:
									// 컬럼13
									userOrderExcelList.setUserWidth(value); 
									break;
								case 14:
									// 컬럼14
									userOrderExcelList.setUserHeight(value); 
									break;
								case 15:
									 // 컬럼15 Declare Quantity
									subNo = 1;
									break;
								case 16:
									// 컬럼16 Currency
									userOrderExcelItem.setChgCurrency(value);
									break;
								case 17:
									// 컬럼17 1st declare
									userOrderExcelItem.setItemDetail(value);
									break;
								case 18:
									userOrderExcelItem.setItemMeterial(value); // 컬럼18
									break;
								case 19:
									userOrderExcelItem.setItemCnt(value); // 컬럼19
									break;
								case 20:
									userOrderExcelItem.setUnitValue(value); // 컬럼20
									userOrderExcelItem.setWDate(time1);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem);
									}
									break;
								case 21:
									// 컬럼21
									subNo = 2; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 22:
									// 컬럼22
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 23:
									// 컬럼23
									userOrderExcelItem.setItemCnt(value); 
									break;
								case 24:
									/* userOrderExcelList.setTransCode(value); */ // 컬럼24
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 25:
									// 컬럼25
									subNo = 3; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 26:
									// 컬럼26
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 27:
									// 컬럼27
									userOrderExcelItem.setItemCnt(value);
									break;
								case 28:
									// 컬럼28
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 29:
									// 컬럼29
									subNo = 4; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 30:
									// 컬럼30
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 31:
									// 컬럼31
									userOrderExcelItem.setItemCnt(value);
									break;
								case 32:
									// 컬럼32
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 33:
									// 컬럼33
									subNo = 5; 
									userOrderExcelItem.setItemDetail(value);
									break;
								case 34:
									// 컬럼34
									userOrderExcelItem.setItemMeterial(value);
									break;
								case 35:
									// 컬럼35
									userOrderExcelItem.setItemCnt(value);
									break;
								case 36:
									// 컬럼36
									userOrderExcelItem.setUnitValue(value);
									userOrderExcelItem.setNno(userOrderExcelList.getNno());
									userOrderExcelItem.setSubNo(Integer.toString(subNo));
									if(!userOrderExcelItem.getItemDetail().equals("")) {
										mapper.insertUserOrderItemTemp(userOrderExcelItem); 
									}
									break;
								case 37:
									// 컬럼37
									userOrderExcelList.setOrgStation(value);
									break;
								}
							} // 현재row vo에 set 완료
								// vo 검증로직은 여기
							//하드코딩 이거 수정 되어야 함
							userOrderExcelList.setOrgStation("441");
							userOrderExcelList.setDstnNation("JP");
							userOrderExcelList.setDstnStation("JP");
							userOrderExcelList.setTransCode("SAGAWA");
							//하드코딩
							userOrderExcelList.setWDate(time1);
							userOrderExcelList.encryptData();
							mapper.insertUserOrderListTemp(userOrderExcelList);
						}

					}

				}
				
			} catch (IOException e) {
				e.printStackTrace();
				result = "F";
				
			} catch (Exception e) {
				e.printStackTrace();
				result = "F";
			}
		}
		result = "S";
		return result;
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class) 
	public String insertExcelDataForSagawa(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType, String types) throws Exception {
		String result= "F";
		String transCom = types;
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		
		optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,"JP");
		optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,"JP");
		 
		String pattern = "^[0-9]*$"; //숫자만
		String patternFloat = "^[0-9]*\\.?[0-9]*$"; //숫자만
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmssSS");
		Date time = new Date();
		
		String time1 = format1.format(time);
		String defaultOrderNo = format2.format(time);
		int currentRow = 0;
		
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "/excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			/*
			 * List<UserOrderItemVO> userOrderExcelItemList = new
			 * ArrayList<UserOrderItemVO>();
			 */
			/* UserOrderItemVO userOrderExcelItem = new UserOrderItemVO(); */
			UserOrderListVO userOrderExcelList = new UserOrderListVO();
			userOrderExcelList = mapper.selectUserForExcel(userId);
			/*
			 * userOrderExcelItem.setWUserId(userId);
			 * userOrderExcelItem.setWUserIp(request.getRemoteAddr());
			 * userOrderExcelItem.setWDate(time1);
			 */
			userOrderExcelList.setWUserId(userId);
			userOrderExcelList.setWUserIp(request.getRemoteAddr());
			
			userOrderExcelList.setOrderType(orderType);
			userOrderExcelList.setOrderDate(time1);
			
			String nno="";
			
			
//			List<CustomerDetail> resultList = new ArrayList();
//			Long customerNo = customerRepository.getSequence(vo); // 고객번호 획득
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					int cells = sheet.getRow(0).getPhysicalNumberOfCells(); // 한 row당 cell개수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
						XSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
								XSSFCell cell = row.getCell(cellNum);
						        if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && !"".equals(cell.toString())) {
						        	rowEmpty = false;
						        	break;
						        }
						    }
							if(!rowEmpty) {
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								
								
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
	//							CustomerDetail vo = new CustomerDetail();
	//							vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
	//							vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
	//							customerNo = customerNo + 1;
	//							vo.setStat("Y"); // 여부
	
								String value = "";
								
								boolean listFlag = true;
								currentRow = rowIndex;
								String rowResult="Err!:";
								boolean columnFlag = true;
								int emptyCell = 0;
								for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) 
								{
									Date times2 = new Date();
									String time2 = format2.format(times2);
									userOrderExcelList.setWDate(time2);
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell==null) {
										emptyCell++;
										cell = row.createCell(columnIndex);
										cell.setCellValue("");
										cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
									}
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
											value = cell.getStringCellValue();
											if(value.split("[.]")[1].equals("0")) {
												value=value.split("[.]")[0];
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
									case 0:
										if(value.equals("")) 
										{
											columnFlag = false;
											listFlag = false;
											for(int roop=1; roop < 12;roop++) {
												String value2 = "";
												XSSFCell cellTemp = row.getCell(roop); // 셀에 담겨있는 값을 읽는다.
												if(cellTemp==null) {
													cellTemp = row.createCell(columnIndex);
													cellTemp.setCellValue("");
													cellTemp.setCellType(HSSFCell.CELL_TYPE_BLANK);
												}
												switch (cellTemp.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
													case HSSFCell.CELL_TYPE_NUMERIC:
														cell.setCellType( HSSFCell.CELL_TYPE_STRING );
														value2 = cell.getStringCellValue();
														if(value2.split("[.]")[1].equals("0")) {
															value2=value2.split("[.]")[0];
														}
														break;
													case HSSFCell.CELL_TYPE_STRING:
														value2 = cellTemp.getStringCellValue() + "";
														break;
													case HSSFCell.CELL_TYPE_BLANK:
														value2 = "";
														break;
													case HSSFCell.CELL_TYPE_ERROR:
														value2 = cellTemp.getErrorCellValue() + "";
														break;
												}
												if(!value2.equals("")) {
													columnFlag=true;
													listFlag = true;
													break;
												}
											}
										}
										
										if(columnFlag) {
											userOrderExcelList.setStatus("");
											userOrderExcelItem.setStatus("");
											userOrderExcelList.dncryptData();
											if(value.length() > 50) {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
												value = value.substring(0,40);  
											}
											userOrderExcelList.setOrderNo(value); 
										}
										break;
									
									case 1:
										// CneeName 및 기본 정보
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setNativeCneeName(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ52,");
												}
											}
											break;
										}
									case 2:{
										// CneeName Eng
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setCneeName(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
										}
										break;
									}
									case 3:{
										// CneeTel
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeTel(value);
											}else {
												if(!optionOrderVO.getCneeTelYn().isEmpty()) {
													userOrderExcelList.setCneeTel(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
												}
											}
										}
										break;
									}
									case 4:{
										// PostCode
										if(columnFlag) {
											if(!"".equals(value)) {
												String restr = String.format("%07d", Integer.parseInt(value = value.replaceAll("[^0-9]","")));
												if(value.length() < 9) {
													userOrderExcelList.setCneeZip(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
													userOrderExcelList.setCneeZip(value.substring(0,7));
												}
											}else {
												userOrderExcelList.setCneeZip("");
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
											}
										}
										break;
									}
									case 5:{
										// CneeAddr
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setNativeCneeAddr(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ53,");
												}
											}
										}
										
										break;
									}
									case 6:{
										// CneeAddr Eng
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setCneeAddr(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
										}
										
										break;
									}
									case 7:{
										// Box Qty
										if(columnFlag) {
											if(!"".equals(value)) {
									            boolean regex = Pattern.matches(pattern, value.trim());
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											}else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											
												 
										}
										break;
									}
									case 8:{
										// ActualWeight
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserWta(String.format("%.2f", Double.parseDouble(value)));
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
										}
										
										break;
									}
									case 9:{
										// width
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserWidth(value); 
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
											}
										}
										break;
									}
									case 10:{
										// length
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
											}
											
										}
										
										break;
									}
									case 11:{
										// height
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
											}
										}
										break;
									}
									case 12:{
										// CURRENCY
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
										}
										break;
									}
									case 13:{// Q10Code
										if(!"".equals(value)) {
											userOrderExcelItem.setCusItemCode(value);
										}else {
											if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
											}
										}
										break;
									}
									case 14:{
										// ItemName
										if(!"".equals(value)) {
											userOrderExcelItem.setItemDetail(value);
										}else {
											if(!optionItemVO.getItemDetailYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
											}
										}
										break;
									}
									case 15:{
										if(!"".equals(value)) {
											// quality of meterial
											userOrderExcelItem.setItemMeterial(value);
										}else {
											if(!optionItemVO.getItemMeterialYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ54,");
											}
										}
										break;
									}
									case 16:{
										// itemCnt / itemPCS
										if(!"".equals(value)) {
											boolean regex = Pattern.matches(pattern, value.trim());
											if(regex) {
												userOrderExcelItem.setItemCnt(value);
											}else {
												userOrderExcelItem.setItemCnt("0");
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
											}
											
										}else {
											if(!optionItemVO.getItemCntYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
											}
										}
										break;
									}
									case 17:{
										 // Value
										if(!"".equals(value)) {
											boolean regex = Pattern.matches(patternFloat, value.trim());
											if(regex) {
												userOrderExcelItem.setUnitValue(value);
											}else {
												userOrderExcelItem.setUnitValue("0");
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
											}
										}else {
											if(!optionItemVO.getUnitValueYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
											}
										}
										break;
									}
									case 18:
										/*
										 * if(!value.equals("")) { int a =
										 * Integer.parseInt(userOrderExcelItem.getItemCnt()); int b =
										 * Integer.parseInt(userOrderExcelItem.getUnitValue()); int total = a*b;
										 * if(!value.equals(Integer.toString(total))) { throw new
										 * Exception(currentRow+"행의 Total Value가 다릅니다."); } }else { throw new
										 * Exception(currentRow+"행의 Total Value에 오류가 있습니다."); }
										 */
										// 컬럼16 Currency
										break;
									case 19:
										// 컬럼17 1st declare
										userOrderExcelItem.setOrgStation("441");
										userOrderExcelItem.setMakeCntry(value);
										
										break;
									}
								} // 현재row vo에 set 완료
									// vo 검증로직은 여기
								if(emptyCell == 20) {
									return "등록되었습니다";
								}
								
								if(listFlag) {
									//하드코딩
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									nno = comnService.selectNNO();
									userOrderExcelList.setNno(nno);
	//								Date orderNoTime = new Date();
	//								defaultOrderNo = format2.format(orderNoTime);
	//								defaultOrderNo = defaultOrderNo+rowIndex;
	//								if(userOrderExcelList.getOrderNo().equals("")){
	//									userOrderExcelList.setOrderNo(defaultOrderNo);
	//								}
									
									//하드코딩
									userOrderExcelList.setOrgStation("441");
									userOrderExcelList.setDstnNation("JP");
									userOrderExcelList.setDstnStation("JP");
									userOrderExcelList.setTransCode(transCom);
									//하드코딩
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									userOrderExcelList.encryptData();
									mapper.insertUserOrderListTemp(userOrderExcelList);  
								}
								
								userOrderExcelItem.setNno(userOrderExcelList.getNno());
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								subNo++;
							}
						}
					}
				}else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					int cells = sheet.getRow(0).getPhysicalNumberOfCells(); // 한 row당 cell개수
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보
						if (row != null) 
						{
							for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
								HSSFCell cell = row.getCell(cellNum);
						        if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK && !"".equals(cell.toString())) {
						        	rowEmpty = false;
						        	break;
						        }
						    }
							if(!rowEmpty) {
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								
								
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
	//							CustomerDetail vo = new CustomerDetail();
	//							vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
	//							vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
	//							customerNo = customerNo + 1;
	//							vo.setStat("Y"); // 여부
	
								String value = "";
								
								boolean listFlag = true;
								currentRow = rowIndex;
								String rowResult="Err!:";
								boolean columnFlag = true;
								int emptyCell = 0;
								for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) 
								{
									Date times2 = new Date();
									String time2 = format2.format(times2);
									userOrderExcelList.setWDate(time2);
									HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell==null) {
										emptyCell++;
										cell = row.createCell(columnIndex);
										cell.setCellValue("");
										cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
									}
									switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
											value = cell.getStringCellValue();
											if(value.split("[.]")[1].equals("0")) {
												value=value.split("[.]")[0];
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
									case 0:
										if(value.equals("")) 
										{
											columnFlag = false;
											listFlag = false;
											for(int roop=1; roop < 12;roop++) {
												String value2 = "";
												HSSFCell cellTemp = row.getCell(roop); // 셀에 담겨있는 값을 읽는다.
												if(cellTemp==null) {
													cellTemp = row.createCell(columnIndex);
													cellTemp.setCellValue("");
													cellTemp.setCellType(HSSFCell.CELL_TYPE_BLANK);
												}
												switch (cellTemp.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
													case HSSFCell.CELL_TYPE_NUMERIC:
														cell.setCellType( HSSFCell.CELL_TYPE_STRING );
														value2 = cell.getStringCellValue();
														if(value2.split("[.]")[1].equals("0")) {
															value2=value2.split("[.]")[0];
														}
														break;
													case HSSFCell.CELL_TYPE_STRING:
														value2 = cellTemp.getStringCellValue() + "";
														break;
													case HSSFCell.CELL_TYPE_BLANK:
														value2 = "";
														break;
													case HSSFCell.CELL_TYPE_ERROR:
														value2 = cellTemp.getErrorCellValue() + "";
														break;
												}
												if(!value2.equals("")) {
													columnFlag=true;
													listFlag = true;
													break;
												}
											}
										}
										
										if(columnFlag) {
											userOrderExcelList.setStatus("");
											userOrderExcelItem.setStatus("");
											userOrderExcelList.dncryptData();
											if(value.length() > 50) {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
												value = value.substring(0,40);  
											}
											userOrderExcelList.setOrderNo(value); 
										}
										break;
									
									case 1:
										// CneeName 및 기본 정보
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setNativeCneeName(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ52,");
												}
											}
											break;
										}
									case 2:{
										// CneeName Eng
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setCneeName(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
										}
										break;
									}
									case 3:{
										// CneeName Eng
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeTel(value);
											}else {
												if(!optionOrderVO.getCneeTelYn().isEmpty()) {
													userOrderExcelList.setCneeTel(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
												}
											}
										}
										break;
									}
										// CneeTel
									case 4:{
										// PostCode
										if(columnFlag) {
											if(!"".equals(value)) {
												String restr = String.format("%07d", Integer.parseInt(value = value.replaceAll("[^0-9]","")));
												if(value.length() < 9) {
													userOrderExcelList.setCneeZip(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
													userOrderExcelList.setCneeZip(value.substring(0,7));
												}
											}else {
												userOrderExcelList.setCneeZip("");
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
											}
										}
										break;
									}
									case 5:{
										// CneeAddr
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setNativeCneeAddr(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ53,");
												}
											}
										}
										
										break;
									}
									case 6:{
										// CneeAddr Eng
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setCneeAddr(value);
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
										}
										
										break;
									}
									case 7:{
										// Box Qty
										if(columnFlag) {
											if(!"".equals(value)) {
									            boolean regex = Pattern.matches(pattern, value.trim());
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											}else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											
												 
										}
										break;
									}
									case 8:{
										// ActualWeight
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserWta(String.format("%.2f", Double.parseDouble(value)));
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
										}
										
										break;
									}
									case 9:{
										// width
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserWidth(value); 
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
											}
										}
										break;
									}
									case 10:{
										// length
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
											}
											
										}
										
										break;
									}
									case 11:{
										// height
										if(columnFlag) {
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value.trim());
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
											}
										}
										break;
									}
									case 12:{
										// CURRENCY
										if(columnFlag) {
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
										}
										break;
									}
									case 13:{// Q10Code
										if(!"".equals(value)) {
											userOrderExcelItem.setCusItemCode(value);
										}else {
											if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
											}
										}
										break;
									}
									case 14:{
										// ItemName
										if(!"".equals(value)) {
											userOrderExcelItem.setItemDetail(value);
										}else {
											if(!optionItemVO.getItemDetailYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
											}
										}
										break;
									}
									case 15:{
										if(!"".equals(value)) {
											// quality of meterial
											userOrderExcelItem.setItemImgUrl(value);
										}else {
											if(!optionItemVO.getItemMeterialYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ54,");
											}
										}
										break;
									}
									case 16:{
										// itemCnt / itemPCS
										if(!"".equals(value)) {
											boolean regex = Pattern.matches(pattern, value.trim());
											if(regex) {
												userOrderExcelItem.setItemCnt(value);
											}else {
												userOrderExcelItem.setItemCnt("0");
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
											}
											
										}else {
											if(!optionItemVO.getItemCntYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
											}
										}
										break;
									}
									case 17:{
										 // Value
										if(!"".equals(value)) {
											boolean regex = Pattern.matches(patternFloat, value.trim());
											if(regex) {
												userOrderExcelItem.setUnitValue(value);
											}else {
												userOrderExcelItem.setUnitValue("0");
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
											}
										}else {
											if(!optionItemVO.getUnitValueYn().isEmpty()) {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
											}
										}
										break;
									}
									case 18:
										/*
										 * if(!value.equals("")) { int a =
										 * Integer.parseInt(userOrderExcelItem.getItemCnt()); int b =
										 * Integer.parseInt(userOrderExcelItem.getUnitValue()); int total = a*b;
										 * if(!value.equals(Integer.toString(total))) { throw new
										 * Exception(currentRow+"행의 Total Value가 다릅니다."); } }else { throw new
										 * Exception(currentRow+"행의 Total Value에 오류가 있습니다."); }
										 */
										// 컬럼16 Currency
										break;
									case 19:
										// 컬럼17 1st declare
										userOrderExcelItem.setOrgStation("441");
										userOrderExcelItem.setMakeCntry(value);
										
										break;
									}
								}// 현재row vo에 set 완료
									// vo 검증로직은 여기
								if(emptyCell == 20) {
									return "등록되었습니다";
								}
								if(listFlag) {
									//하드코딩
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									nno = comnService.selectNNO();
									
									userOrderExcelList.setNno(nno);
									Date orderNoTime = new Date();
									defaultOrderNo = format2.format(orderNoTime);
									defaultOrderNo = defaultOrderNo+rowIndex;
									if(userOrderExcelList.getOrderNo().equals("")){
										userOrderExcelList.setOrderNo(defaultOrderNo);
									}
									
									//하드코딩
									userOrderExcelList.setOrgStation("441");
									userOrderExcelList.setDstnNation("JP");
									userOrderExcelList.setDstnStation("JP");
									userOrderExcelList.setTransCode(transCom);
									//하드코딩
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									userOrderExcelList.encryptData();
									 mapper.insertUserOrderListTemp(userOrderExcelList);  
								}
								
								userOrderExcelItem.setNno(userOrderExcelList.getNno());
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								subNo++;
							}
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
		}
		
		result = "등록되었습니다.";
		return result;
	}
	public Map filesUploadNomalOrder(MultipartHttpServletRequest multi, String uploadPaths, String userId) {
	    String path = uploadPaths +"tmpOrder/"; // 저장 경로 설정
	    
	    String newFileName = ""; // 업로드 되는 파일명
	    File dir = new File(path);
	    if(!dir.isDirectory()){
	        dir.mkdir();
	    }

	    Date today = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String dateTime = sdf.format(today);
	    
	    Iterator<String> files = multi.getFileNames();
	    Map<String, String> resMap = new HashMap<String, String>();
	    String fileName = "";
	    while(files.hasNext()){
	        String uploadFile = files.next();
	        MultipartFile mFile = multi.getFile(uploadFile);
	        fileName = mFile.getOriginalFilename();
	        newFileName = userId+"_"+dateTime+"."+fileName.substring(fileName.lastIndexOf(".")+1);
	        try {
	            mFile.transferTo(new File(path+newFileName));
	            resMap.put(newFileName, path+newFileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return resMap;
	}
	
	
	public Map filesUpload(MultipartHttpServletRequest multi, String uploadPaths) {
	    String path = uploadPaths +"tmpOrder/"; // 저장 경로 설정
	    
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
	public UserOrderListVO selectUserOrderOne(String nno) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.selectUserOrderOne(nno);
	}

	@Override
	public ArrayList<UserOrderItemVO> selectUserOrderItemOne(UserOrderListVO userOrder) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserOrderItemOne(userOrder);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<ShpngListVO>> selectRegistOrderList(HashMap<String, Object> parameter)
			throws Exception {
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try{
			selectShpngList = mapper.selectRegistOrderList(parameter);
			for(int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
				/*
				if (parameter.get("userId").toString().equals("itsel2") || parameter.get("userId").toString().equals("vision2038")) {
					HashMap<String, Object> danParams = new HashMap<String, Object>();
					danParams.put("nno", selectShpngList.get(index).getNno());
					danParams.put("orderNo", selectShpngList.get(index).getOrderNo());
					selectShpngList.get(index).setDanList(mapper.selectDanList(danParams));
				}*/
			}
		}catch (Exception e) {
			// TODO: handle exception
			return returnMap;
		}
		if(selectShpngList.size() == 0) {
			return returnMap;
		}
		tempSelectShpngList.add(selectShpngList.get(0));
		for(int roop=1; roop<selectShpngList.size();roop++) {
			if(selectShpngList.get(roop).getSubNo().equals("1")) {
				returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
				tempSelectShpngList = new ArrayList<ShpngListVO>();
				tempSelectShpngList.add(selectShpngList.get(roop));
			}else {
				tempSelectShpngList.add(selectShpngList.get(roop));
			}
		}
		returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
		return returnMap;
	}

	@Override
	public ArrayList<UserOrderItemVO> selectUserRegistOrderItemOne(UserOrderListVO userOrder) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserRegistOrderItemOne(userOrder);
	}

	@Override
	public ArrayList<String> selectNationArrayToStation(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNationArrayToStation(parameters);
	}

//	@Override
//	@Transactional
//	public BlApplyVO selectBlApply(String orderNno, String userId, String userIp) throws Exception {
//		// TODO Auto-generated method stub
//		TestssVO parameters = new TestssVO();
//		ArrayList<BlApplyVO> blApply = new ArrayList<BlApplyVO>();
//		
//		parameters.setUserId(userId);
//		parameters.setUserIp(userIp);
//		BlApplyVO tmp = new BlApplyVO();
//		parameters.setNno(orderNno);
//		
//		tmp = mapper.selectBlApply(parameters);
//		
//		blApply.add(tmp);
//		return tmp;
//	}

	@Override
	public ArrayList<InspStockListVO> selectInspStockOrderInfo(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		//ArrayList<String> nnoList = mapper.selectNnoList(parameters);
		ArrayList<InspStockListVO> returnList = new ArrayList<InspStockListVO>();
		returnList = mapper.selectInspStockList(parameters);
		for(int index =0; index < returnList.size(); index++) {
			InspStockListVO temp = new InspStockListVO();
			temp = returnList.get(index);
			parameters.put("nno",temp.getNno());
			parameters.put("subNo",temp.getSubNo());
			parameters.put("groupIdx",temp.getGroupIdx());
			returnList.get(index).setFilePath(mapper.selectFilePath(parameters));
		}
		return returnList;
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
	public ArrayList<UserTransComVO> selectTrkComList(String userId) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<UserTransComVO> returnList = new ArrayList<UserTransComVO> ();
		returnList =mapper.selectTrkComList(userId); 
		return returnList;
	}

	@Override
	public ArrayList<UserTransComVO> selectDstnTrans(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDstnTrans(userId);
	}

	@Override
	public ArrayList<UserTransComVO> selectUserOrgTrans(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserOrgTrans(userId);
	}

	@Override
	public ArrayList<SendVO> selectSendList(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSendList(search);
	} 
	
	@Override
	public ArrayList<SendVO> selectUnSendList(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUnSendList(search);
	}

	@Override
	public ArrayList<AllowIpVO> selectAllowIpList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectAllowIpList(userId);
	}

	@Override
	public void insertAllowIp(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertAllowIp(parameters);
	}

	@Override
	public void deleteAllowIp(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteAllowIp(parameters);
	}

	@Override
	public String selectApiKey(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectApiKey(userId);
	}

	@Override
	public void insertApiKey(String apiKey, String userId) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertApiKey(apiKey,userId);
	}

	@Override
	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStock(parameterInfo);
	}
	
	@Override
	public BlApplyVO selectBlApplyARA(String orderNno, String userId, String userIp) throws Exception {
		// TODO Auto-generated method stub
		
		TestssVO parameters = new TestssVO();
		parameters.setUserId(userId);
		parameters.setUserIp(userIp);
		BlApplyVO tmp = new BlApplyVO();
		
		try {
			parameters.setNno(orderNno);
			comnService.insertTBFromTMP(orderNno);
			String result = mgrServiceImpl.excuteAramexHawbMemberOut(orderNno, userId, userIp);
		}catch (Exception e) {
			// TODO: handle exception
		}
			
		return tmp;
		
	}
	
	
	
	

	@Override
	public HashMap<String, ArrayList<ShpngListVO>> selectSendBeforeOrderList(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try{
			selectShpngList = mapper.selectSendBeforeOrderList(parameter);
			for(int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
			}
		}catch (Exception e) {
			// TODO: handle exception
			return returnMap;
		}
		if(selectShpngList.size() == 0) {
			return returnMap;
		}
		tempSelectShpngList.add(selectShpngList.get(0));
		for(int roop=1; roop<selectShpngList.size();roop++) {
			if(selectShpngList.get(roop).getSubNo().equals("1")) {
				returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
				tempSelectShpngList = new ArrayList<ShpngListVO>();
				tempSelectShpngList.add(selectShpngList.get(roop));
			}else {
				tempSelectShpngList.add(selectShpngList.get(roop));
			}
		}
		returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
		return returnMap;
	}

	@Override
	public HashMap<String, ArrayList<ShpngListVO>> selectSendAfterOrderList(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try{
			selectShpngList = mapper.selectSendAfterOrderList(parameter);
			for(int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
			}
		}catch (Exception e) {
			// TODO: handle exception
			return returnMap;
		}
		if(selectShpngList.size() == 0) {
			return returnMap;
		}
		tempSelectShpngList.add(selectShpngList.get(0));
		for(int roop=1; roop<selectShpngList.size();roop++) {
			if(selectShpngList.get(roop).getSubNo().equals("1")) {
				returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
				tempSelectShpngList = new ArrayList<ShpngListVO>();
				tempSelectShpngList.add(selectShpngList.get(roop));
			}else {
				tempSelectShpngList.add(selectShpngList.get(roop));
			}
		}
		returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
		return returnMap;
	}

	@Override
	public void updateItemTrkNo(String trkNo, String nno, String subNo) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateItemTrkNo(trkNo, nno, subNo);
	}

	@Override
	public int selectSendListCount(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSendListCount(search);
	}

	@Override
	public int selectUnSendListCount(SendVO search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUnSendListCount(search) ;
	}

	@Override
	public String selectTransCom(HashMap<String, Object> parameters1) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCom(parameters1);
	}

	@Override
	public HashMap<String,Object> selectTransIdx(String idx) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransIdx(idx);
	}

	@Override
	public int selectShpngListCount(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectShpngListCount(parameter);
	}

	@Override
	public String selectTransCodeFromBigo(String transName) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeFromBigo(transName);
	}

	@Override
	public int selectRegistOrderCount(HashMap<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRegistOrderCount(parameter);
	} 
	
	public String randomAlphaWord(int wordLength) {

		Random r = new Random();
		StringBuilder sb = new StringBuilder(wordLength);
		for(int i = 0; i < wordLength; i++) {
			char tmp = (char) ('A' + r.nextInt('Z' - 'A'));
			sb.append(tmp);
		}
		return sb.toString();

	}

	@Override
	public HashMap<String, ArrayList<ShpngListVO>> selectSendErrorOrderList(HashMap<String, Object> parameter)
			throws Exception {
		LinkedHashMap<String, ArrayList<ShpngListVO>> returnMap = new LinkedHashMap<String, ArrayList<ShpngListVO>> (); 
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		ArrayList<ShpngListVO> tempSelectShpngList = new ArrayList<ShpngListVO>();
		try{
			selectShpngList = mapper.selectSendErrorOrderList(parameter);
			for(int index = 0; index < selectShpngList.size(); index++) {
				selectShpngList.get(index).dncryptData();
			}
		}catch (Exception e) {
			// TODO: handle exception
			return returnMap;
		}
		if(selectShpngList.size() == 0) {
			return returnMap;
		}
		tempSelectShpngList.add(selectShpngList.get(0));
		for(int roop=1; roop<selectShpngList.size();roop++) {
			if(selectShpngList.get(roop).getSubNo().equals("1")) {
				returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
				tempSelectShpngList = new ArrayList<ShpngListVO>();
				tempSelectShpngList.add(selectShpngList.get(roop));
			}else {
				tempSelectShpngList.add(selectShpngList.get(roop));
			}
		}
		returnMap.put(tempSelectShpngList.get(0).getNno(), tempSelectShpngList);
		return returnMap;
	}

	@Override
	public String selectTransCodeFromNNO(String nno) throws Exception {
		// TODO Auto-generated method stub 
		return mapper.selectTransCodeFromNNO(nno);
	}

	@Override
	public String createExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
			LocalDate currentDate = LocalDate.now();
			String savePath = realFilePath + "excel/expLicence/";
			String filename = "userUploadForm"+request.getSession().getAttribute("USER_ID").toString()+".xlsx" ;
			HashMap<String, Object> userTransInfo = new HashMap<String,Object>();
			userTransInfo = selectUserTransInfo(request.getParameter("idx"));
			String transCode = userTransInfo.get("transCode").toString();
			String dstnNation = userTransInfo.get("dstnNation").toString();
			String orgNation = userTransInfo.get("orgNation").toString();
			
			
			String transCode2 = "";
			if(userTransInfo.get("transCode").equals("OCS")) {
				transCode2=userTransInfo.get("transCode").toString();
			}else if(userTransInfo.get("transCode").equals("GTS")){
				transCode2=userTransInfo.get("transCode").toString();
			}else if(userTransInfo.get("transCode").equals("CSE")){
				transCode2=userTransInfo.get("transCode").toString();
			}else {
				transCode2="";
			}
			OrderListOptionVO listFilterVO = new OrderListOptionVO(); //필수
			OrderListExpOptionVO listExpVO = new OrderListExpOptionVO(); //노출
			OrderItemOptionVO itemFilterVO = new OrderItemOptionVO(); //아이템 필수
			OrderItemExpOptionVO itemExpVO = new OrderItemExpOptionVO(); //아이템 노출
			listFilterVO = mgrServiceImpl.SelectOrderListOption(transCode, dstnNation);//필수
			listExpVO = mgrServiceImpl.SelectExpressListOption(transCode, dstnNation);//노출
			itemFilterVO = mgrServiceImpl.SelectOrderItemOption(transCode, dstnNation);//필수
			itemExpVO = mgrServiceImpl.SelectExpressItemOption(transCode, dstnNation);//노출
			
			HashMap<String,String> excelColumn = new HashMap<String,String>();
			
			excelColumn = mapper.selectExcelColumn(transCode2);
			
			Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
			Sheet sheet1 = xlsxWb.createSheet("Form");
			
			Font font = xlsxWb.createFont();
			font.setFontHeightInPoints((short) 10);
			
			Font font2 = xlsxWb.createFont();
			font2.setFontHeightInPoints((short) 9);
			
			CellStyle cellStyle = xlsxWb.createCellStyle();
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setFont(font2);
			
			
			//HEAD 스타일 설정 START
			CellStyle cellStyleHeader = xlsxWb.createCellStyle();
			cellStyleHeader.setWrapText(false);
			cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyleHeader.setFont(font);
			//HEAD 스타일 설정 END
			
			//HEAD Filter 스타일 설정 START
			CellStyle cellStyleFilterHeader = xlsxWb.createCellStyle();
			cellStyleFilterHeader.setWrapText(false);
			cellStyleFilterHeader.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			cellStyleFilterHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleFilterHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleFilterHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyleFilterHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleFilterHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyleFilterHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyleFilterHeader.setFont(font);
			//HEAD 스타일 설정 END

			//ROW-CELL 선언 START
			Row row = null;
			Cell cell = null;
			int rowCnt = 0;
			int cellCnt = 0;
			int cellPivoit=0;
			//ROW-CELL 선언 END
			
			//HEADER 생성 START
			row = sheet1.createRow(rowCnt);
			
			if(listExpVO.getOrderNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ORDER_NO"));
				if(listFilterVO.getOrderNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getOrderDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ORDER_DATE"));
				if(listFilterVO.getOrderDateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getOrgStationYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ORG_STATION"));
				if(listFilterVO.getOrgStationYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getDstnNationYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("DSTN_NATION"));
				if(listFilterVO.getDstnNationYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			cell = row.createCell(cellCnt);
			cell.setCellValue(excelColumn.get("PAYMENT"));
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			if(listExpVO.getShipperNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_NAME"));
				if(listFilterVO.getShipperNameYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperTelYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_TEL"));
				if(listFilterVO.getShipperTelYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperHpYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_HP"));
				if(listFilterVO.getShipperHpYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperZipYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_ZIP"));
				if(listFilterVO.getShipperZipYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_CNTRY"));
				if(listFilterVO.getShipperCntryYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperCityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_CITY"));
				if(listFilterVO.getShipperCityYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperStateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_STATE"));
				if(listFilterVO.getShipperStateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_ADDR"));
				if(listFilterVO.getShipperAddrYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_ADDR_DETAIL"));
				if(listFilterVO.getShipperAddrDetailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getUserEmailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_EMAIL"));
				if(listFilterVO.getUserEmailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperReferenceYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("SHIPPER_REFERENCE"));
				if(listFilterVO.getShipperReferenceYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperTaxTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("송하인 세금식별코드");
				if (listFilterVO.getShipperTaxTypeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				} else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getShipperTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("송하인 세금식별번호");
				if (listFilterVO.getShipperTaxNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				} else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_NAME"));
				if(listFilterVO.getCneeNameYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeTelYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_TEL"));
				if(listFilterVO.getCneeTelYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeHpYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_HP"));
				if(listFilterVO.getCneeHpYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeZipYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_ZIP"));
				if(listFilterVO.getCneeZipYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_CNTRY"));
				if(listFilterVO.getCneeCntryYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeCityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_CITY"));
				if(listFilterVO.getCneeCityYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeStateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_STATE"));
				if(listFilterVO.getCneeStateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeDistrictYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_DISTRICT"));
				if(listFilterVO.getCneeDistrictYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeWardYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("수취인 동");
				if(listFilterVO.getCneeWardYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				} else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_ADDR"));
				if(listFilterVO.getCneeAddrYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_ADDR_DETAIL"));
				if(listFilterVO.getCneeAddrDetailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getNativeCneeNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIVE_CNEE_NAME"));
				if(listFilterVO.getNativeCneeNameYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(listExpVO.getNativeCneeAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIVE_CNEE_ADDR"));
				if(listFilterVO.getNativeCneeAddrYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(listExpVO.getNativeCneeAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIVE_CNEE_ADDR_DETAIL"));
				if(listFilterVO.getNativeCneeAddrDetailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCneeEmailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_EMAIL"));
				if(listFilterVO.getCneeEmailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getCustomsNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CUSTOMS_NO"));
				if(listFilterVO.getCustomsNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if (listExpVO.getCneeTaxTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("수취인 세금식별코드");
				if (listFilterVO.getCneeTaxTypeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				} else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if (listExpVO.getCneeTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("수취인 세금식별번호");
				if (listFilterVO.getTaxNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				} else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			//러시아 추가 컬럼
			if(listExpVO.getNationalIdDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIONAL_ID_DATE"));
				if(listFilterVO.getNationalIdDateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(listExpVO.getNationalIdAuthorityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIONAL_ID_AUTHORITY"));
				if(listFilterVO.getNationalIdAuthorityYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(listExpVO.getCneeBirthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_BIRTH"));
				if(listFilterVO.getCneeBirthYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("TAX_NO"));
				if(listFilterVO.getTaxNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			///

			if(listExpVO.getCneeReference1YnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_REFERENCE1"));
				if(listFilterVO.getCneeReference1Yn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getCneeReference2YnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CNEE_REFERENCE2"));
				if(listFilterVO.getCneeReference2Yn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getDlvReqMsgYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("DLV_REQ_MSG"));
				if(listFilterVO.getDlvReqMsgYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getBoxCntYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("BOX_CNT"));
				if(listFilterVO.getBoxCntYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getUserWtaYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_WTA"));
				if(listFilterVO.getUserWtaYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getUserWtcYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_WTC"));
				if(listFilterVO.getUserWtcYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getWtUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("WT_UNIT"));
				if(listFilterVO.getWtUnitYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getUserLengthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_LENGTH"));
				if(listFilterVO.getUserLengthYn().equals("on")) { 
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getUserWidthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_WIDTH"));
				if(listFilterVO.getUserWidthYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getUserHeightYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("USER_HEIGHT"));
				if(listFilterVO.getUserHeightYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getDimUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("DIM_UNIT"));
				if(listFilterVO.getDimUnitYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getWhReqMsgYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("WH_REQ_MSG"));
				if(listFilterVO.getWhReqMsgYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getBuySiteYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("BUY_SITE"));
				if(listFilterVO.getBuySiteYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(listExpVO.getGetBuyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("GET_BUY"));
				if(listFilterVO.getGetBuyYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getMallTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("MALL_TYPE"));
				if(listFilterVO.getMallTypeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(listExpVO.getFoodYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("FOOD"));
				if(listFilterVO.getFoodYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("화장품 포함 여부");
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			if(listExpVO.getSignYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("대면 서명 여부");
				if (listFilterVO.getSignYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			// 수출신고 관련
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주사업자상호명");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주사업자대표명");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주사업자번호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주사업자주소");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주사업자우편번호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("수출화주통관부호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("수출대행자상호명");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("수출대행자사업장일련번호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("수출대행자통관부호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("수출면장번호");
			cell.setCellStyle(cellStyleFilterHeader);
			cellCnt++;
			
			//cell = row.createCell(cellCnt);xx
			
		
			if(itemExpVO.getHsCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("HS_CODE"));
				if(itemFilterVO.getHsCodeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_DETAIL"));
				if(itemFilterVO.getItemDetailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getUnitCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("UNIT_CURRENCY"));
				if(itemFilterVO.getUnitCurrencyYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getUnitValueYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("UNIT_VALUE"));
				if(itemFilterVO.getUnitValueYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getItemCntYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_CNT"));
				if(itemFilterVO.getItemCntYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtaYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_USER_WTA"));
				if(itemFilterVO.getItemUserWtaYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtcYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_USER_WTC"));
				if(itemFilterVO.getItemUserWtcYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getQtyUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("QTY_UNIT"));
				if(itemFilterVO.getQtyUnitYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getItemWtUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_WT_UNIT"));
				if(itemFilterVO.getItemWtUnitYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getBrandYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("BRAND"));
				if(itemFilterVO.getBrandYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getCusItemCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CUS_ITEM_CODE"));
				if(itemFilterVO.getCusItemCodeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}

			if(itemExpVO.getTrkComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("TRK_COM"));
				if(itemFilterVO.getTrkComYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getTrkNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("TRK_NO"));
				if(itemFilterVO.getTrkNoYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getTrkDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("TRK_DATE"));
				if(itemFilterVO.getTrkDateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getItemUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_URL"));
				if(itemFilterVO.getItemUrlYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getItemImgUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_IMG_URL"));
				if(itemFilterVO.getItemImgUrlYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(itemExpVO.getItemDivYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_DIV"));
				if(itemFilterVO.getItemDivYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(itemExpVO.getMakeCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("MAKE_CNTRY"));
				if(itemFilterVO.getMakeCntryYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			if(itemExpVO.getMakeComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("MAKE_COM"));
				if(itemFilterVO.getMakeComYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			

			if(itemExpVO.getNativeItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("NATIVE_ITEM_DETAIL"));
				if(itemFilterVO.getNativeItemDetailYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
//			if(listExpVO.getCustomsNoYnExpress().equals("on")) {
//				cell = row.createCell(cellCnt);
//				cell.setCellValue(excelColumn.get("CUSTOMS_NO"));
//				if(listFilterVO.getCustomsNoYn().equals("on")) {
//					cell.setCellStyle(cellStyleFilterHeader);
//				}else {
//					cell.setCellStyle(cellStyleHeader);
//				}
//				sheet1.setColumnWidth(cellCnt, 2000);
//				cellCnt++;
//			}
			
			if(itemExpVO.getPackageUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("PACKAGE_UNIT"));
				if(itemFilterVO.getPackageUnitYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getExchangeRateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("EXCHANGE_RATE"));
				if(itemFilterVO.getExchangeRateYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getChgCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CHG_CURRENCY"));
				if(itemFilterVO.getChgCurrencyYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getChgAmtYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("CHG_AMT"));
				if(itemFilterVO.getChgAmtYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getItemMeterialYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_METERIAL"));
				if(itemFilterVO.getItemMeterialYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			if(itemExpVO.getTakeInCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("TAKE_IN_CODE"));
				if(itemFilterVO.getTakeInCodeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			///러시아 추가
			if(itemExpVO.getItemExplanYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_EXPLAN"));
				if(itemFilterVO.getItemExplanYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue("BOX 번호");
				cell.setCellStyle(cellStyleFilterHeader);
				cellCnt++;
			}
			if(itemExpVO.getItemBarcodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumn.get("ITEM_BARCODE"));
				if(itemFilterVO.getItemBarcodeYn().equals("on")) {
					cell.setCellStyle(cellStyleFilterHeader);
				}else {
					cell.setCellStyle(cellStyleHeader);
				}
				cellCnt++;
			}
			
			//////
			
			rowCnt++;
			//HEADER 생성 END
			
			row = sheet1.createRow(rowCnt);
			cellCnt = 0;
			if(listExpVO.getOrderNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("TEST-ORDERNO-1");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getOrderDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("20240322");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getOrgStationYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				if (orgNation.equals("US")) {
					cell.setCellValue("213");
				} else {
					cell.setCellValue("082");	
				}
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getDstnNationYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USA");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("DDU");
			cell.setCellStyle(cellStyle);
			cellCnt++;
			
			if(listExpVO.getShipperNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperTelYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperHpYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperZipYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getShipperCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USA");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperCityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("Casper");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperStateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("Wyoming");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			///
			if(listExpVO.getShipperAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("shipper address1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("shipper address2");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getUserEmailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("userEmail@mail.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperReferenceYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("shipper reference");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getShipperTaxTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("0:NONE(default), 1:CNPJ, 2:CPF, 3:EIN, 4:EORI, 5:GST, 6:IOSS, 7:SSN, 8:TAN, 9:TIN, 10:VAT, 11:VOEC, 12:OTHER");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getShipperTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getCneeNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeTelYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeHpYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeZipYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USA");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeCityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("City/Province");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeStateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("States");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getCneeDistrictYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("District");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeWardYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("Ward/Commune");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getCneeAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cnee Address1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cnee Address2");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getNativeCneeNameYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("현지 이름");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(listExpVO.getNativeCneeAddrYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("배송 주소(현지언어로)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(listExpVO.getNativeCneeAddrDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("배송 상세주소 (현지언어로)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeEmailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cneeEmail@email.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			

			////// 러시아
			if(listExpVO.getCustomsNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("990122 (수취인 ID 번호)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getCneeTaxTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("0:NONE(default), 1:CNPJ, 2:CPF, 3:EIN, 4:EORI, 5:GST, 6:IOSS, 7:SSN, 8:TAN, 9:TIN, 10:VAT, 11:VOEC, 12:OTHER");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getCneeTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			if(listExpVO.getNationalIdDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("22/01/2021");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(listExpVO.getNationalIdAuthorityYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("center, seoul");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(listExpVO.getCneeBirthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("22/01/2002");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(listExpVO.getTaxNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1234567890(수취인TAX번호)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			////// 러시아
			if(listExpVO.getCneeReference1YnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cneeReference1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getCneeReference2YnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cneeReference2");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}

			if(listExpVO.getDlvReqMsgYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("message");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getBoxCntYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getUserWtaYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getUserWtcYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getWtUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KG");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getUserLengthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getUserWidthYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(listExpVO.getUserHeightYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");		
				cell.setCellStyle(cellStyle);		
				cellCnt++;
			}

			if(listExpVO.getDimUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("cm");		
				cell.setCellStyle(cellStyle);		
				cellCnt++;
			}

			if(listExpVO.getWhReqMsgYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("message");	
				cell.setCellStyle(cellStyle);			
				cellCnt++;
			}
			
			if(listExpVO.getBuySiteYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("http://buySite.com");	
				cell.setCellStyle(cellStyle);			
				cellCnt++;
			}

			if(listExpVO.getGetBuyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1 = 개인, 2= 사업자");			
				cell.setCellStyle(cellStyle);	
				cellCnt++;
			}
			
			if(listExpVO.getMallTypeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("A");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(listExpVO.getFoodYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("N");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}


			cell = row.createCell(cellCnt);
			cell.setCellValue("N:미포함 (default), Y:포함");
			cell.setCellStyle(cellStyle);
			cellCnt++;
			
			if(listExpVO.getSignYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("N:사용안함 (default), Y:사용");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("N: 수출신고안함, F: 일반수출신고, S: 간이수출신고, E: 수출목록변환신고");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분이 N이 아닌 경우 필수");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분이 E인 경우 필수");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분이 E인 경우 필수");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분이 E인 경우 필수");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("수출신고구분이 E인 경우 필수");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("통관부호가 있는 경우 입력");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;

			cell = row.createCell(cellCnt);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("통관부호가 있는 경우 입력");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("기존에 발급받은 면장번호가 있는 경우 입력");
			cell.setCellStyle(cellStyle);
			
			cellCnt++;
			
			if(itemExpVO.getHsCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				if(cellPivoit == 0) {
					cellPivoit = cellCnt;
				}
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("item Detail");
				if(cellPivoit == 0) {
					cellPivoit = cellCnt;
				}
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getUnitCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("ex:) USD");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getUnitValueYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemCntYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtaYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtcYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getQtyUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("EA");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemWtUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KG");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getBrandYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("Brand Name");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getCusItemCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("itemCode1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getTrkComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KPost");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTrkNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("123456789");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTrkDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("20210101");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("http://itemUrl.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemImgUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("http://itemImgUrl.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemDivYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("item div");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getMakeCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KR");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getMakeComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("mkCom");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getNativeItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품명(현지언어로)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
//			if(listExpVO.getCustomsNoYnExpress().equals("on")) {
//				cell = row.createCell(cellCnt);
//				cell.setCellValue("수취인 ID 번호");
//				
//				cellCnt++;
//			}
			
			if(itemExpVO.getPackageUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("packageUnit");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getExchangeRateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("122");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getChgCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USD");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getChgAmtYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemMeterialYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTakeInCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			
			////// 러시아
			
			if(itemExpVO.getItemExplanYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품 상세 설명");
				cell.setCellStyle(cellStyle);
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품이 있는 BOX 번호");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			if(itemExpVO.getItemBarcodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품 별 바코드");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			//////
			
			rowCnt++;
			////////////////////
			
			row = sheet1.createRow(rowCnt);
			cellCnt = cellPivoit;
			
			if(itemExpVO.getHsCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				cellPivoit = cellCnt;
				cellCnt++;
			}
			
			if(itemExpVO.getItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("item Detail");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getUnitCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USD");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getUnitValueYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemCntYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtaYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemUserWtcYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getQtyUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("EA");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getItemWtUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KG");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getBrandYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("Brand Name");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getCusItemCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("itemCode1");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getTrkComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KPost");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTrkNoYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("123456789");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTrkDateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("20210101");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("http://itemUrl.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemImgUrlYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("http://itemImgUrl.com");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(itemExpVO.getItemDivYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("item div");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(itemExpVO.getMakeCntryYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("KR");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			if(itemExpVO.getMakeComYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("mkCom");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}

			if(itemExpVO.getNativeItemDetailYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품명(현지언어로)");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
//			if(listExpVO.getCustomsNoYnExpress().equals("on")) {
//				cell = row.createCell(cellCnt);
//				cell.setCellValue("수취인 ID 번호");
//				
//				cellCnt++;
//			}
			
			if(itemExpVO.getPackageUnitYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("packageUnit");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getExchangeRateYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("122");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getChgCurrencyYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("USD");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getChgAmtYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getItemMeterialYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			if(itemExpVO.getTakeInCodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			////// 러시아
			
			if(itemExpVO.getItemExplanYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품 상세 설명");
				cell.setCellStyle(cellStyle);
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품이 있는 BOX 번호");
				cell.setCellStyle(cellStyle);
				cellCnt++;
			}
			if(itemExpVO.getItemBarcodeYnExpress().equals("on")) {
				cell = row.createCell(cellCnt);
				cell.setCellValue("상품 별 바코드");
				cell.setCellStyle(cellStyle);
				
				cellCnt++;
			}
			
			//////
			
			
			
			ArrayList<NationVO> nationList = new ArrayList<NationVO>();
			
			nationList = comnService.selectNationCode();
			
			Sheet sheet2 = xlsxWb.createSheet("국가명_영문명");
			
			rowCnt = 0;
			cellCnt = 0;
			cellPivoit=0;
			//ROW-CELL 선언 END
			
			//HEADER 생성 START 작업중입니단
			row = sheet2.createRow(rowCnt);
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("NATION NAME");
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("NATION ENG_NAME");
			cell.setCellStyle(cellStyleHeader);
			
			rowCnt++;
			
				
			for(int i =0; i < nationList.size();i++) {
				cellCnt=0;
				row = sheet2.createRow(rowCnt);
				cell = row.createCell(cellCnt);
				cell.setCellValue(nationList.get(i).getNationName().toString());
				cellCnt++;
				cell = row.createCell(cellCnt);
				cell.setCellValue(nationList.get(i).getNationEName().toString());
				rowCnt++;
			}
			
			sheet2.setColumnWidth(0, 8000);
			sheet2.setColumnWidth(1, 8000);
				
			Sheet sheet3 = xlsxWb.createSheet("참고사항");
			rowCnt = 0;
			cellCnt = 0;
			cellPivoit=0;
			//ROW-CELL 선언 END
			
			ArrayList<HashMap<String,String>> excelColumnExplain = new ArrayList<HashMap<String,String>>();
			
			excelColumnExplain = mapper.selectExcelColumnExplain();
			row = sheet3.createRow(rowCnt);
			cell = row.createCell(cellCnt);
			cell.setCellValue("분류");
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("컬럼명");
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("Type");
			cell.setCellStyle(cellStyleHeader);
			cellCnt++;
			
			cell = row.createCell(cellCnt);
			cell.setCellValue("비    고");
			cell.setCellStyle(cellStyleHeader);
			
			rowCnt++;
			//HEADER 생성 START 작업중입니단
			
			for(int i = 0; i < excelColumnExplain.size(); i++) {
				cellCnt = 0;
				row = sheet3.createRow(rowCnt);
				cell = row.createCell(cellCnt);
				if(excelColumnExplain.get(i).get("TRANS_ETC").equals("default")) {
					if(excelColumnExplain.get(i).get("SUB_TITLE").equals("송하인 정보") || excelColumnExplain.get(i).get("SUB_TITLE").equals("수취인 정보")) {
						cell.setCellValue(excelColumnExplain.get(i).get("SUB_TITLE"));
					}else {
						cell.setCellValue("일반 등록 정보");
					}
				}
				else if (excelColumnExplain.get(i).get("TRANS_ETC").contains(transCode)){
					cell.setCellValue(excelColumnExplain.get(i).get("SUB_TITLE"));
				}
				
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumnExplain.get(i).get("TITLE"));
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumnExplain.get(i).get("TYPE"));
				cellCnt++;
				
				cell = row.createCell(cellCnt);
				cell.setCellValue(excelColumnExplain.get(i).get("ETC"));
				cellCnt++;
				
				rowCnt++;
			}
			
			sheet3.setColumnWidth(0, 8000);
			sheet3.setColumnWidth(1, 10000);
			sheet3.setColumnWidth(2, 8000);
			sheet3.setColumnWidth(3, 12000);
			
			try {
				try {
		            File xlsFile = new File(savePath+filename);
		            FileOutputStream fileOut = new FileOutputStream(xlsFile);
		            xlsxWb.write(fileOut);
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
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
		      e.printStackTrace();
		    }
			return "SSS";
	}

	@Override
	public void updateErrorTmp(String userId) throws Exception {
		// TODO Auto-generated method stub
		mapper.updateErrorTmp(userId);
	}

	@Override
	public void updateWebHook(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		/*
		mgrServiceImpl.deleteWebHookList(request.getSession().getAttribute("USER_ID").toString());
		for(int i = 0 ; i < request.getParameterMap().get("webHookDiv").length; i++) {
			HashMap<String,Object> webHookList = new HashMap<String,Object>();
			webHookList.put("divKr", request.getParameterMap().get("divKr")[i].toString());
			webHookList.put("webHookDiv", request.getParameterMap().get("webHookDiv")[i].toString());
			webHookList.put("webHookUrl", request.getParameterMap().get("webHookUrl")[i].toString());
			webHookList.put("userId", request.getSession().getAttribute("USER_ID").toString());
			webHookList.put("wUserId", request.getSession().getAttribute("USER_ID").toString());
			webHookList.put("wUserIp", request.getSession().getAttribute("USER_IP").toString());
			mgrServiceImpl.insertWebHookList(webHookList);
		}
		*/
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("eshopId", request.getParameter("eshopId"));
		parameters.put("eshopApiKey", request.getParameter("eshopApiKey"));
		parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
		mapper.updateEshopInfo(parameters);
	}

	@Override
	public String insertExcelDataNew(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception {
		// TODO Auto-generated method stub
		String result="F";
		String transCom = "";
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmssSS");
		Date time = new Date();
		String pattern = "^[0-9]*$"; //숫자만
		String patternFloat = "^[0-9]*\\.?[0-9]*$"; //숫자만 Float *실수 타입*
		String patternJP = "^[ぁ-ゔ]+|[ァ-ヴー]+[々〆〤]*$"; //일본어만

		ExcelMatchingVO excelCol = new ExcelMatchingVO();
		HashMap<String,Object> matchingParam = new HashMap<String,Object>();
		matchingParam.put("userId", request.getSession().getAttribute("USER_ID"));
		matchingParam.put("transCode", request.getParameter("formSelect"));
		matchingParam.put("orgStation", request.getSession().getAttribute("ORG_STATION"));
		
		String time1 = format1.format(time);
		
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "excel/";
		resMap = filesUpload(multi, excelRoot);
		String filePath = "";
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			UserOrderListVO userOrderExcelList = new UserOrderListVO();
			String regercyOrgStation = "";
			String nno="";
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					String prvOrderNo = "";
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
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
							if(!rowEmpty) {
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								userOrderExcelItem.setTrkDate(time1);
								int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
//								CustomerDetail vo = new CustomerDetail();
//								vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//								vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//								customerNo = customerNo + 1;
//								vo.setStat("Y"); // 여부

								String value = "";
								
								boolean shipperInfoYn = true;
								boolean listFlag = true;
								
								boolean transChgYN = false;
								double wta = 0;
								double length = 0;
								double width = 0;
								double height = 0;
								double wtc = 0;
								
								HashMap<String,Object> transParameter = new HashMap<String,Object>();
								
								transParameter.put("userId", userId);
								transParameter.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transParameter.put("wUserIp", (String)request.getSession().getAttribute("USER_IP"));
								//transParameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
								transParameter.put("orgStation", "441");
								matchingParam.put("orgStation", "GB");
								transParameter.put("wtc", Double.toString(wtc));
								transParameter.put("wta", Double.toString(wta));
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									boolean stopFlag = false; 
									matchingParam.put("columnNum",columnIndex);
									excelCol = mapper.selectExcelMatching(matchingParam);
									if(excelCol == null) {
										continue;
									}
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										
										switch (excelCol.getMatchingName()) {
											case "orderNo":{
												if(prvOrderNo.equals(value)) {
													stopFlag = true;
												}
												break;
											}
											case "dstnNation":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												String dstnNation = mapper.selectNationCode(value);
												transParameter.put("dstnNation",dstnNation);
												transParameter.put("dstnStation",dstnNation);
												break;
											}
											case "userWta":{
												if(!value.equals("")) {
													transChgYN = true;		
													wta = Double.parseDouble(value);
												}else {
													value = "0";
												}
												transParameter.put("wta", Double.toString(wta));
												break;
											}
											case "wtUnit":{
												if(value.equals("LB")) {
													wta = wta*2.20462;
													transParameter.put("wta", Double.toString(wta));
												}
												break;
											}
											case "userLength":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												length = Double.parseDouble(value);
												break;
											}
											case "userWidth":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												width = Double.parseDouble(value);
												break;
											}
											case "userHeight":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												height = Double.parseDouble(value);
												break;
											}
											case "dimUnit":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												if(value.equals("IN")) {
													wtc = (height*width*length)/133;
												}else {
													wtc = (height*width*length)/5000;
												}
												transParameter.put("wtc", Double.toString(wtc));
												break;
											}
										}
									}
									if(stopFlag) {
										transChgYN = false;
										break;
									}
								}
								
								ProcedureVO rstValue = new ProcedureVO();
								if(transChgYN) {
									nno = comnService.selectNNO();
									transParameter.put("nno",nno);
									transCom = comnService.selectUserTransCode(transParameter);
									transParameter.put("transCode",transCom);
									int cnts = apiServiceImpl.checkNation(transParameter);
									if(cnts==0) {
										HashMap<String,Object> tempparameters = new HashMap<String,Object>();
										tempparameters.put("userId", transParameter.get("userId"));
										tempparameters.put("orgStation",transParameter.get("orgStation"));
										tempparameters.put("dstnNation","DEF");
										if(apiServiceImpl.selectDefaultTransCom(tempparameters) != null) {
											transCom = apiServiceImpl.selectDefaultTransCom(tempparameters);
										}
									}
									
									transParameter.put("transCode",transCom);
									rstValue  = comnService.selectTransComChangeForVo(transParameter);
									transCom= rstValue.getRstTransCode();
									optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,"DEFAULT");
									optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,"DEFAULT");
									expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,"DEFAULT");
									expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,"DEFAULT");
								}
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									matchingParam.put("columnNum",columnIndex);
									excelCol = mapper.selectExcelMatching(matchingParam);
									if(excelCol == null) {
										continue;
									}
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
									}else {
										if(columnIndex==0) {
											listFlag = false;
										}
										value="";
									}
								
									
									switch (excelCol.getMatchingName()) {
										case "orderNo":{
											if(!optionOrderVO.getOrderNoYn().isEmpty()) {
												if("".equals(value)) {
													listFlag = false;
												}else if (userOrderExcelList.getOrderNo().equals(value)) {
													listFlag = false;
												}else {
													userOrderExcelList = new UserOrderListVO();
													userOrderExcelList.setWDate(time2);
													userOrderExcelList.setUserId(userId);
													userOrderExcelList.setWUserId(userId);
													userOrderExcelList.setWUserIp(request.getRemoteAddr());
													userOrderExcelList.setTypes("Excel");
													userOrderExcelList.setOrderType(orderType);
													userOrderExcelList.setOrgStation("441");
													
													userOrderExcelList.dncryptData();
													if(value.length() > 50) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
														value = value.substring(0,40);
													}
													userOrderExcelList.setOrderNo(value); // 커럼3
													prvOrderNo = value;
													
												}
											}else {
												
												listFlag = false;
												userOrderExcelList = new UserOrderListVO();
												if("".equals(value)) {
													String _orderNo = time2.substring(2, time2.length()-3);
													userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
												}
												userOrderExcelList.setWDate(time2);
												userOrderExcelList.setUserId(userId);
												userOrderExcelList.setWUserId(userId);
												userOrderExcelList.setWUserIp(request.getRemoteAddr());
												userOrderExcelList.setTypes("Excel");
												userOrderExcelList.setOrderType(orderType);
												userOrderExcelList.setOrgStation("441");
												
												
												userOrderExcelList.dncryptData();
												if(value.length() > 50) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
													value = value.substring(0,40);
												}
												userOrderExcelList.setOrderNo(value); // 커럼3
											}
											break;
											
										}
										case "orderDate":{
											if(value.length() != 8) {
												value = "";
											}
											
											if(value.equals("")) {
												value = time1;
											}
											userOrderExcelList.setOrderDate(value); 
											
											break;
										}
										case "orgStation":{
											if(!"".equals(value)) {
												userOrderExcelList.setOrgStation(value); // 컬럼0
											}else {
												userOrderExcelList.setOrgStation(regercyOrgStation);
												if(!optionOrderVO.getOrgStationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ01,");
												}
											}
											break;
										}
										case "dstnNation":{
											if(!"".equals(value)) {
												String dstnNation = mapper.selectNationCode(value);
												optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,dstnNation);
												optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,dstnNation);
												expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,dstnNation);
												expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,dstnNation);
												userOrderExcelList.setDstnNation(value); // 컬럼1
											}else {
												userOrderExcelList.setDstnNation(""); // 컬럼1
												if(!optionOrderVO.getDstnNationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ02,");
												}
											}
											
											break;
										}
										case "transCom":{
											if(!"".equals(value)) {
												userOrderExcelList.setTransCode(transCom);
											}else {
												if(!optionOrderVO.getTransCodeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ03,");
												}
											}
											break;
										}
										case "shipperName":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperName(value);
											}else {
												if(!optionOrderVO.getShipperNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ04,");
												}
											}
											break;
										}
										case "shipperTel":{
											if(!"".equals(value)) {
													shipperInfoYn = false;
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setShipperTel(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP03,");
														userOrderExcelList.setShipperTel("");
													}
												}else {
													if(!optionOrderVO.getShipperTelYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ05,");
													}
												}
											break;
										}
											
										case "shipperHp":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
												if(regex) {
													userOrderExcelList.setShipperHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP04,");
													userOrderExcelList.setShipperHp("");
												}
											}else {
												if(!optionOrderVO.getShipperHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ06,");
												}
											}
											break;
										}
										case "shipperZip":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""));
												if(regex) {
													if(value.length() < 9) {
														userOrderExcelList.setShipperZip(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
														userOrderExcelList.setShipperZip(value.substring(0,7));
													}
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
													userOrderExcelList.setShipperZip(value.substring(0,7));
												}
												
											}else {
												if(!optionOrderVO.getShipperZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ07,");
												}
											}
											break;
										}
										case "shipperCntry":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperCntry(value);
											}else {
												if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ08,");
												}
											}
											break;
										}
										case "shipprtCity":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperCity(value);
											}else {
												if(!optionOrderVO.getShipperCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ09,");
												}
											}
											break;
										}
										case "shipperState":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperState(value);
											}else {
												if(!optionOrderVO.getShipperStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ10,");
												}
											}
											break;
										}
										case "shipperAddr":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddr(value);
											}else {
												if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ11,");
												}
											}
											break;
										}
										case "shipperAddrDetail":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddrDetail(value);
											}else {
												if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ12,");
												}
											}
											break;
										}	
										case "shipperEmail":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}	
										case "shipperReference":{
											if(!"".equals(value)) {
												userOrderExcelList.setShipperReference(value);
											}else {
												if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "cneeReference1":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeReference1(value);
											}else {
												if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "cneeReference2":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeReference2(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "payment":{
											if(!"".equals(value)) {
												userOrderExcelList.setPayment(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "CNEE_NAME":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "nativeCneeName":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "cneeTel":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setCneeTel(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP05,");
														userOrderExcelList.setCneeTel("");
													}
												}else {
													if(!optionOrderVO.getCneeTelYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
													}
												}
												userOrderExcelList.setCneeTel(value);
												break;
											}
										case "cneeHp":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setCneeHp(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP06,");
														userOrderExcelList.setCneeHp("");
													}
												}else {
													if(!optionOrderVO.getCneeHpYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ16,");
													}
												}
												userOrderExcelList.setCneeHp(value);
												break;
											}
										case "cneeZip":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""));
													if(regex) {
														if(value.length() < 9) {
															userOrderExcelList.setCneeZip(value);
														}else {
															userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
															userOrderExcelList.setCneeZip(value.substring(0,7));
														}
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
														userOrderExcelList.setCneeZip(value.substring(0,7));
													}
													
												}else {
													if(!optionOrderVO.getCneeZipYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
													}
												}
												break;
											}
										case "cneeCntry":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeCntry(value);									
											}else {
												if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ18,");
												}
											}
											break;
										}
										case "cneeCity":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeCity(value);									
											}else {
												if(!optionOrderVO.getCneeCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ19,");
												}
											}
											break;
										}
										case "cneeState":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeState(value);
											}else {
												if(!optionOrderVO.getCneeStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ20,");
												}
											}
											break;
										}
										case "cneeAddr":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "nativeCneeAddr":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "cneeAddrDetail":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "nativeCneeAddrDetail":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "cneeEmail":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeEmail(value);
											}else {
												if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ23,");
												}
											}
											break;
										}
										case "dlvReqMsg":{
											if(!"".equals(value)) {
												userOrderExcelList.setDlvReqMsg(value);
											}else {
												if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ24,");
												}
											}
											break;
										}
										case "mallType":{
											if(!"".equals(value)) {
												userOrderExcelList.setMallType(value);
											}else {
												if(!optionOrderVO.getMallTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ25,");
												}
											}
											break;
										}
										case "getBuy":{
											if(!"".equals(value)) {
												userOrderExcelList.setGetBuy(value);
											}else {
												if(!optionOrderVO.getGetBuyYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ26,");
												}
											}
											break;
										}
										case "boxCnt":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											}else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											break;
										}
										case "userWta":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWta(value);
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
											
										case "wtUnit":{
											if(!"".equals(value)) {
												userOrderExcelList.setWtUnit(value);
											}else {
												if(!optionOrderVO.getWtUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ29,");
												}
											}
											break;
											
										}
										case "userLength":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "userWidth":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWidth(value);
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "userHeight":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
										case "dimUnit":{
											if(!"".equals(value)) {
												userOrderExcelList.setDimUnit(value);
											}else {
												if(!optionOrderVO.getDimUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ33,");
												}
												userOrderExcelList.setDimUnit("CM");
											}
											break;
										}
										case "whReqMsg":{
											if(!"".equals(value)) {
												userOrderExcelList.setWhReqMsg(value);
											}else {
												if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ34,");
												}
											}
											break;
										}
										case "buySite":{
											if(!"".equals(value)) {
												userOrderExcelList.setBuySite(value);
											}else {
												if(!optionOrderVO.getBuySiteYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ35,");
												}
											}
											break;
										}
										case "hsCode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setHsCode(value);
											}else {
												if(!optionItemVO.getHsCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ36,");
												}
											}
											break;
										}
										case "itemDetail":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDetail(value);
											}else {
												if(!optionItemVO.getItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "nativeItemDetail":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "chgCurrency":{
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
											break;
										}
										case "unitValue":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setUnitValue(value);
												}else {
													userOrderExcelItem.setUnitValue("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
												}
											}else {
												if(!optionItemVO.getUnitValueYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
												}
											}
											break;
										}
										case "itemCnt":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setItemCnt(value);
												}else {
													userOrderExcelItem.setItemCnt("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
												}
											}else {
												if(!optionItemVO.getItemCntYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
												}
											}
											break;
										}
										case "qtyUnit":{
											if(!"".equals(value)) {
												userOrderExcelItem.setQtyUnit(value);
											}else {
												if(!optionItemVO.getQtyUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ41,");
												}
											}
											break;
										}
										case "brand":{
											if(!"".equals(value)) {
												userOrderExcelItem.setBrand(value);
											}else {
												if(!optionItemVO.getBrandYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ42,");
												}
											}
											break;
										}
										case "cusItemCode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setCusItemCode(value);
											}else {
												if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
												}
											}
											break;
										}
										case "trkCom":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkCom(value);
											}else {
												if(!optionItemVO.getTrkComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ44,");
												}
											}
											break;
										}
										case "trkNo":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkNo(value);
											}else {
												if(!optionItemVO.getTrkNoYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ45,");
												}
											}
											break;
										}
										case "trkDate":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkDate(value);
											}else {
												if(!optionItemVO.getTrkDateYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ46,");
												}
											}
											break;
										}
										case "itemUrl":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemUrl(value);
											}else {
												if(!optionItemVO.getItemUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ47,");
												}
											}
											break;
										}
										case "itemImgUrl":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemImgUrl(value);
											}else {
												if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ48,");
												}
											}
											break;
										}
										case "상품색상":
											//
											break;
										case "itemWta":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWta(value);
											}else {
												if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "ITEM_WT_UNIT":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setWtUnit(value);
											}else {
												if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ58,");
												}
											}
											break;
										case "상품size":
											//
											break;
										case "사서함반호":
											//
											break;
										case "itemDiv":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDiv(value);
											}else {
												if(!optionItemVO.getItemDivYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ49,");
												}
											}
											break;
										}
										case "makeCntry":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCntry(value);
											}else {
												if(!optionItemVO.getMakeCntryYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ50,");
												}
											}
											break;
										}
										case "makeCom":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCom(value);
											}else {
												if(!optionItemVO.getMakeComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ51,");
												}
											}
											break;
										}
										case "food" : {
											if(!"".equals(value)) {
												userOrderExcelList.setFood(value);
											}else {
												if(!optionOrderVO.getFoodYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ56,");
												}
											}
											break;
										}
										case "customsNo" : {
											if(!"".equals(value)) {
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"수취인ID번호 오류,");
												}
											}
											break;
										}
										case "expYn" : {
											if(!"".equals(value)) {
												
												if(value.equals("Y")) {
													userOrderExcelList.setSimpleYn("N");
													userOrderExcelList.setExpValue("registExplicence1");
												}else if (value.equals("S")) {
													userOrderExcelList.setSimpleYn("Y");
													userOrderExcelList.setExpValue("simpleExplicence");
												}else {
													userOrderExcelList.setSimpleYn("");
													userOrderExcelList.setExpValue("noExplicence");
												}
											}else {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분이 비어있습니다.");
											}
											break;
										}
										case "expNo" : {
											if(!"".equals(value)) {
												userOrderExcelList.setExpValue("registExplicence2");
												userOrderExcelList.setExpNo(value);
												
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {
													if(userOrderExcelList.getExpValue().equals("registExplicence2"))
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출면장번호가 비어있습니다.");
												}
											}
											break;
										}
										case "expBusinessName" : {
											if(!"".equals(value)) {
												
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpBusinessName(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpBusinessName("");
												}
												
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출자사업자명이 비어있습니다.");
											}
											break;
										}
										case "expBusinessNum" : {
											if(!"".equals(value)) {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpBusinessNum(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpBusinessNum("");
												}
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출자사업자번호가 비어있습니다.");
											}
											break;
										}
										case "expShipperCode" : {
											if(!"".equals(value)) {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpShipperCode(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpShipperCode("");
												}
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주통관부호가 비어있습니다.");
											}
											break;
										}
										/*러시아 추가 부분*/
										case "nationalIdDate" : {//수취인id번호발급일
											if(!"".equals(value)) {
												userOrderExcelList.setNationalIdDate(value);
											}else {
												if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 발급일 오류,");
												}
											}
											break;
										}
										case "nationalIdAuthority" : {//수취인id번호발급기관정보
											if(!"".equals(value)) {
												userOrderExcelList.setNationalIdAuthority(value);
											}else {
												if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 번호 발급기관 정보 오류,");
												}
											}
											break;
										}
										case "cneeBirth" : {//수취인생년월일
											if(!"".equals(value)) {
												userOrderExcelList.setCneeBirth(value);
											}else {
												if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 생년월일 오류,");
												}
											}
											break;
										}
										case "taxNo" : {//수취인tax번호
											if(!"".equals(value)) {
												userOrderExcelList.setTaxNo(value);
											}else {
												if(!optionOrderVO.getTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인TAX 번호 오류,");
												}
											}
											break;
										}
										case "itemExplan" : {//상품설명
											if(!"".equals(value)) {
												userOrderExcelItem.setItemExplan(value);
											}else {
												if(!optionItemVO.getItemExplanYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"상품설명 오류,");
												}
											}
											break;
										}
										case "itemBarcode" : {//상품바코드번호
											if(!"".equals(value)) {
												userOrderExcelItem.setItemBarcode(value);
											}else {
												if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"바코드 오류,");
												}
											}
											break;
										}
										case "boxNum" : {//BOX번호
											if(!"".equals(value)) {
												userOrderExcelItem.setInBoxNum(value);
											}else {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"BOX번호 오류,");
											}
											break;
										}
									}
								}// 현재row vo에 set 완료
								// vo 검증로직은 여기
//								resultList.add(vo); // 검증된 vo 리스트에 추가
								if(listFlag) {
									if(shipperInfoYn) {
										UserOrderListVO userOrderExcelListTemp = new UserOrderListVO();
										userOrderExcelListTemp = mapper.selectUserForExcel(userId);
										userOrderExcelListTemp.dncryptData();
										
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ04,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ05,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ06,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ07,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ08,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ09,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ10,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ11,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ12,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ13,", ""));
										
										userOrderExcelList.setShipperName(userOrderExcelListTemp.getShipperName());
										userOrderExcelList.setShipperTel(userOrderExcelListTemp.getShipperTel());
										userOrderExcelList.setShipperHp(userOrderExcelListTemp.getShipperHp());
										userOrderExcelList.setShipperZip(userOrderExcelListTemp.getShipperZip());
										userOrderExcelList.setShipperCity(userOrderExcelListTemp.getShipperCity());
										userOrderExcelList.setShipperState(userOrderExcelListTemp.getShipperState());
										userOrderExcelList.setShipperAddr(userOrderExcelListTemp.getShipperAddr());
										userOrderExcelList.setShipperAddrDetail(userOrderExcelListTemp.getShipperAddrDetail());
										userOrderExcelList.setUserEmail(userOrderExcelListTemp.getUserEmail());
									}
									if((userOrderExcelList.getUserLength() != null && userOrderExcelList.getUserWidth() != null && userOrderExcelList.getUserHeight() !=null && userOrderExcelList.getDimUnit() != null) && (!userOrderExcelList.getUserLength().equals("")&& !userOrderExcelList.getUserWidth().equals("")&& !userOrderExcelList.getUserHeight().equals("")&& !userOrderExcelList.getDimUnit().equals(""))){
										if(userOrderExcelList.getDimUnit().equals("IN") || userOrderExcelList.getDimUnit().equals("INCH")) {
											userOrderExcelList.setDimUnit("IN");
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 166;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
										if(userOrderExcelList.getDimUnit().equals("CM")){
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 5000;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
									}
									
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									/* nno = comnService.selectNNO(); */
									userOrderExcelList.setNno(nno);
									
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08,");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									if(userOrderExcelList.getOrderNo().equals("")) {
										String _orderNo = time2.substring(2, time2.length()-3);
										userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
									}
									userOrderExcelList.setTransCode(transCom);
									if(userOrderExcelList.getOrderDate() == "" || userOrderExcelList.getOrderDate() == null)
										userOrderExcelList.setOrderDate(time1);
									userOrderExcelList.encryptData();
									mapper.insertUserOrderListTemp(userOrderExcelList);
									
									if(userOrderExcelList.getExpValue().equals("noExplicence")) {
										
									}else {
										insertExpLicenceInfo(userOrderExcelList);
									}
									regercyOrgStation = userOrderExcelList.getOrgStation();
								}else {
									//item insert 주문번호가 같은 상품 여러개
									
								}
								userOrderExcelItem.setTransCode(transCom);
								userOrderExcelItem.setNno(nno);
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								userOrderExcelItem.setOrgStation(regercyOrgStation);
								userOrderExcelItem.setNationCode(userOrderExcelList.getDstnNation());
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								UserOrderItemVO temp = new UserOrderItemVO();
								/*
								 * temp = userOrderExcelItem; userOrderExcelItemList.add(temp);
								 */
								subNo++;
							}else {
								break;
							}
							
						}
						// 엑셀 전체row수가 500이상인 경우,
//						if (rows > 500) 
//						{
//							if (resultList.size() >= 500) 
//							{ // 500건 단위로 잘라서 DB INSERT
//								result = customerRepository.insertExcel(resultList);
//								FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//								resultList = new ArrayList<>();
//							}
//						}


					}
					// 엑셀 전체row수가 500이하인 경우,
//					if (rows <= 500) { // 한꺼번에 DB INSERT(LIST size는 반드시 500이하임)
//						result = customerRepository.insertExcel(resultList);
//						FTKAssert.isTrue(result >= 1, "엑셀 업로드중 오류가 발생하였습니다.");
//					}
					/* 	mapper.insertUserOrderItemListTemp(userOrderExcelItemList) */;

				}else if (filePath.substring(filePath.lastIndexOf(".")).equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀 workbook
					HSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					String prvOrderNo = "";
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
						HSSFRow rowPivot = sheet.getRow(0);
						HSSFRow row = sheet.getRow(rowIndex); // 현재 row정보

						if (row != null) 
						{
							for (int cellNum = rowPivot.getFirstCellNum(); cellNum < rowPivot.getLastCellNum(); cellNum++) {
								HSSFCell cell = row.getCell(cellNum);
						        if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && !"".equals(cell.toString())) {
						        	rowEmpty = false;
						        	break;
						        }
						    }
							if(!rowEmpty) {
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								userOrderExcelItem.setTrkDate(time1);
								int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
//								CustomerDetail vo = new CustomerDetail();
//								vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//								vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//								customerNo = customerNo + 1;
//								vo.setStat("Y"); // 여부

								String value = "";
								
								boolean shipperInfoYn = true;
								boolean listFlag = false;
							
								
								boolean transChgYN = false;
								double wta = 0;
								double length = 0;
								double width = 0;
								double height = 0;
								double wtc = 0;
								
								HashMap<String,Object> transParameter = new HashMap<String,Object>();
								
								transParameter.put("userId", userId);
								transParameter.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transParameter.put("wUserIp", (String)request.getSession().getAttribute("USER_IP"));
								//transParameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
								transParameter.put("orgStation", "441");
								matchingParam.put("orgStation", "GB");
								transParameter.put("wtc", Double.toString(wtc));
								transParameter.put("wta", Double.toString(wta));
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									boolean stopFlag = false; 
									matchingParam.put("columnNum",columnIndex);
									excelCol = mapper.selectExcelMatching(matchingParam);
									if(excelCol == null) {
										continue;
									}
									HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										
										switch (excelCol.getMatchingName()) {
											case "ORDER_NO":{
												if(prvOrderNo.equals(value)) {
													stopFlag = true;
												}
												break;
											}
											case "DSTN_NATION":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												String dstnNation = mapper.selectNationCode(value);
												transParameter.put("dstnNation",dstnNation);
												transParameter.put("dstnStation",dstnNation);
												break;
											}
											case "USER_WTA":{
												if(!value.equals("")) {
													transChgYN = true;		
													wta = Double.parseDouble(value);
												}else {
													value = "0";
												}
												transParameter.put("wta", Double.toString(wta));
												break;
											}
												
											case "WT_UNIT":{
												if(value.equals("LB")) {
													wta = wta*2.20462;
													transParameter.put("wta", Double.toString(wta));
												}
												break;
											}
											case "USER_LENGTH":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												length = Double.parseDouble(value);
												break;
											}
												
											case "USER_WIDTH":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												width = Double.parseDouble(value);
												break;
											}
												
											case "USER_HEIGHT":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												height = Double.parseDouble(value);
												break;
											}
											case "DIM_UNIT":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												
												if(value.equals("IN")) {
													wtc = (height*width*length)/133;
												}else {
													wtc = (height*width*length)/5000;
												}
												transParameter.put("wtc", Double.toString(wtc));
												break;
											}
										}
									}
									if(stopFlag) {
										transChgYN = false;
										break;
									}
								}
								
								ProcedureVO rstValue = new ProcedureVO();
								if(transChgYN) {
									nno = comnService.selectNNO();
									transParameter.put("nno",nno);
									transCom = comnService.selectUserTransCode(transParameter);
									transParameter.put("transCode",transCom);
									int cnts = apiServiceImpl.checkNation(transParameter);
									if(cnts==0) {
										HashMap<String,Object> tempparameters = new HashMap<String,Object>();
										tempparameters.put("userId", transParameter.get("userId"));
										tempparameters.put("orgStation",transParameter.get("orgStation"));
										tempparameters.put("dstnNation","DEF");
										if(apiServiceImpl.selectDefaultTransCom(tempparameters) != null) {
											transCom = apiServiceImpl.selectDefaultTransCom(tempparameters);
										}
									}
									
									transParameter.put("transCode",transCom);
									rstValue  = comnService.selectTransComChangeForVo(transParameter);
									transCom= rstValue.getRstTransCode();
									optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,"DEFAULT");
									optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,"DEFAULT");
									expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,"DEFAULT");
									expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,"DEFAULT");
								}
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									matchingParam.put("columnNum",columnIndex);
									excelCol = mapper.selectExcelMatching(matchingParam);
									if(excelCol == null) {
										continue;
									}
									HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
									}else {
										if(columnIndex==0) {
											listFlag = false;
										}
										value="";
									}
								
									switch (excelCol.getMatchingName()) {
										case "ORDER_NO":{
											if(!optionOrderVO.getOrderNoYn().isEmpty()) {
												if("".equals(value)) {
													listFlag = false;
												}else {
													userOrderExcelList = new UserOrderListVO();
													userOrderExcelList.setWDate(time2);
													userOrderExcelList.setUserId(userId);
													userOrderExcelList.setWUserId(userId);
													userOrderExcelList.setWUserIp(request.getRemoteAddr());
													userOrderExcelList.setTypes("Excel");
													userOrderExcelList.setOrderType(orderType);
													userOrderExcelList.setOrgStation("441");
													
													userOrderExcelList.dncryptData();
													if(value.length() > 50) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
														value = value.substring(0,40);
													}
													userOrderExcelList.setOrderNo(value); // 커럼3
													prvOrderNo = value;
												}
											}else {
												listFlag = false;
												userOrderExcelList = new UserOrderListVO();
												if("".equals(value)) {
													String _orderNo = time2.substring(2, time2.length()-3);
													userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
												}
												userOrderExcelList.setWDate(time2);
												userOrderExcelList.setUserId(userId);
												userOrderExcelList.setWUserId(userId);
												userOrderExcelList.setWUserIp(request.getRemoteAddr());
												userOrderExcelList.setTypes("Excel");
												userOrderExcelList.setOrderType(orderType);
												userOrderExcelList.setOrgStation("441");
												
												
												userOrderExcelList.dncryptData();
												if(value.length() > 50) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
													value = value.substring(0,40);
												}
												userOrderExcelList.setOrderNo(value); // 커럼3
											}
											break;
											
										}
										case "ORDER_DATE":{
											if(value.length() != 8) {
												value = "";
											}
											
											if(value.equals("")) {
												value = time1;
												listFlag = false;
											}
											userOrderExcelList.setOrderDate(value); 
											
											break;
										}
										case "ORG_STATION":{
											if(!"".equals(value)) {
												userOrderExcelList.setOrgStation(value); // 컬럼0
											}else {
												userOrderExcelList.setOrgStation(regercyOrgStation);
												if(!optionOrderVO.getOrgStationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ01,");
												}
											}
											break;
										}
										case "DSTN_NATION":{
											if(!"".equals(value)) {
												String dstnNation = mapper.selectNationCode(value);
												optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,dstnNation);
												optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,dstnNation);
												expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,dstnNation);
												expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,dstnNation);
												userOrderExcelList.setDstnNation(value); // 컬럼1
											}else {
												userOrderExcelList.setDstnNation(""); // 컬럼1
												if(!optionOrderVO.getDstnNationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ02,");
												}
											}
											
											break;
										}
										case "TRANS_COM":{
											if(!"".equals(value)) {
												userOrderExcelList.setTransCode(transCom);
											}else {
												if(!optionOrderVO.getTransCodeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ03,");
												}
											}
											break;
										}
										case "SHIPPER_NAME":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperName(value);
											}else {
												if(!optionOrderVO.getShipperNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ04,");
												}
											}
											break;
										}
										case "SHIPPER_TEL":{
											if(!"".equals(value)) {
													shipperInfoYn = false;
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setShipperTel(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP03,");
														userOrderExcelList.setShipperTel("");
													}
												}else {
													if(!optionOrderVO.getShipperTelYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ05,");
													}
												}
											break;
										}
											
										case "SHIPPER_HP":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
												if(regex) {
													userOrderExcelList.setShipperHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP04,");
													userOrderExcelList.setShipperHp("");
												}
											}else {
												if(!optionOrderVO.getShipperHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ06,");
												}
											}
											break;
										}
										case "SHIPPER_ZIP":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""));
												if(regex) {
													if(value.length() < 9) {
														userOrderExcelList.setShipperZip(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
														userOrderExcelList.setShipperZip(value.substring(0,7));
													}
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
													userOrderExcelList.setShipperZip(value.substring(0,7));
												}
												
											}else {
												if(!optionOrderVO.getShipperZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ07,");
												}
											}
											break;
										}
										case "SHIPPER_CNTRY":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperCntry(value);
											}else {
												if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ08,");
												}
											}
											break;
										}
										case "SHIPPER_CITY":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperCity(value);
											}else {
												if(!optionOrderVO.getShipperCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ09,");
												}
											}
											break;
										}
										case "SHIPPER_STATE":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperState(value);
											}else {
												if(!optionOrderVO.getShipperStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ10,");
												}
											}
											break;
										}
										case "SHIPPER_ADDR":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddr(value);
											}else {
												if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ11,");
												}
											}
											break;
										}
										case "SHIPPER_ADDR_DETAIL":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddrDetail(value);
											}else {
												if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ12,");
												}
											}
											break;
										}	
										case "SHIPPER_EMAIL":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}	
										case "SHIPPER_REFERENCE":{
											if(!"".equals(value)) {
												userOrderExcelList.setShipperReference(value);
											}else {
												if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "CNEE_REFERENCE1":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeReference1(value);
											}else {
												if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "CNEE_REFERENCE2":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeReference2(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "PAYMENT":{
											if(!"".equals(value)) {
												userOrderExcelList.setPayment(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "CNEE_NAME":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "NATIVE_CNEE_NAME":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "CNEE_TEL":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setCneeTel(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP05,");
														userOrderExcelList.setCneeTel("");
													}
												}else {
													if(!optionOrderVO.getCneeTelYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
													}
												}
												userOrderExcelList.setCneeTel(value);
												break;
											}
										case "CNEE_HP":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", ""));
													if(regex) {
														userOrderExcelList.setCneeHp(value);
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP06,");
														userOrderExcelList.setCneeHp("");
													}
												}else {
													if(!optionOrderVO.getCneeHpYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ16,");
													}
												}
												userOrderExcelList.setCneeHp(value);
												break;
											}
										case "CNEE_ZIP":{
												if(!"".equals(value)) {
													boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""));
													if(regex) {
														if(value.length() < 9) {
															userOrderExcelList.setCneeZip(value);
														}else {
															userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
															userOrderExcelList.setCneeZip(value.substring(0,7));
														}
													}else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
														userOrderExcelList.setCneeZip(value.substring(0,7));
													}
													
												}else {
													if(!optionOrderVO.getCneeZipYn().isEmpty()) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
													}
												}
												break;
											}
										case "CNEE_CNTRY":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeCntry(value);									
											}else {
												if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ18,");
												}
											}
											break;
										}
										case "CNEE_CITY":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeCity(value);									
											}else {
												if(!optionOrderVO.getCneeCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ19,");
												}
											}
											break;
										}
										case "CNEE_STATE":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeState(value);
											}else {
												if(!optionOrderVO.getCneeStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ20,");
												}
											}
											break;
										}
										case "CNEE_ADDR":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "NATIVE_CNEE_ADDR":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "CNEE_ADDR_DETAIL":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "NATIVE_CNEE_ADDR_DETAIL":{
											if(!"".equals(value)) {
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "CNEE_EMAIL":{
											if(!"".equals(value)) {
												userOrderExcelList.setCneeEmail(value);
											}else {
												if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ23,");
												}
											}
											break;
										}
										case "DLV_REQ_MSG":{
											if(!"".equals(value)) {
												userOrderExcelList.setDlvReqMsg(value);
											}else {
												if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ24,");
												}
											}
											break;
										}
										case "MALL_TYPE":{
											if(!"".equals(value)) {
												userOrderExcelList.setMallType(value);
											}else {
												if(!optionOrderVO.getMallTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ25,");
												}
											}
											break;
										}
										case "GET_BUY":{
											if(!"".equals(value)) {
												userOrderExcelList.setGetBuy(value);
											}else {
												if(!optionOrderVO.getGetBuyYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ26,");
												}
											}
											break;
										}
										case "BOX_CNT":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											}else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											break;
										}
										case "USER_WTA":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWta(value);
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
											
										case "WT_UNIT":{
											if(!"".equals(value)) {
												userOrderExcelList.setWtUnit(value);
											}else {
												if(!optionOrderVO.getWtUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ29,");
												}
											}
											break;
											
										}
										case "USER_LENGTH":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "USER_WIDTH":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWidth(value);
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "USER_HEIGHT":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
										case "DIM_UNIT":{
											if(!"".equals(value)) {
												userOrderExcelList.setDimUnit(value);
											}else {
												if(!optionOrderVO.getDimUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ33,");
												}
												userOrderExcelList.setDimUnit("CM");
											}
											break;
										}
										case "WH_REQ_MSG":{
											if(!"".equals(value)) {
												userOrderExcelList.setWhReqMsg(value);
											}else {
												if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ34,");
												}
											}
											break;
										}
										case "BUY_SITE":{
											if(!"".equals(value)) {
												userOrderExcelList.setBuySite(value);
											}else {
												if(!optionOrderVO.getBuySiteYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ35,");
												}
											}
											break;
										}
										case "HS_CODE":{
											if(!"".equals(value)) {
												userOrderExcelItem.setHsCode(value);
											}else {
												if(!optionItemVO.getHsCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ36,");
												}
											}
											break;
										}
										case "ITEM_DETAIL":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDetail(value);
											}else {
												if(!optionItemVO.getItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "NATIVE_ITEM_DETAIL":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "CHG_CURRENCY":{
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
											break;
										}
										case "UNIT_VALUE":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setUnitValue(value);
												}else {
													userOrderExcelItem.setUnitValue("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
												}
											}else {
												if(!optionItemVO.getUnitValueYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
												}
											}
											break;
										}
										case "ITEM_CNT":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setItemCnt(value);
												}else {
													userOrderExcelItem.setItemCnt("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
												}
											}else {
												if(!optionItemVO.getItemCntYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
												}
											}
											break;
										}
										case "QTY_UNIT":{
											if(!"".equals(value)) {
												userOrderExcelItem.setQtyUnit(value);
											}else {
												if(!optionItemVO.getQtyUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ41,");
												}
											}
											break;
										}
										case "BRAND":{
											if(!"".equals(value)) {
												userOrderExcelItem.setBrand(value);
											}else {
												if(!optionItemVO.getBrandYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ42,");
												}
											}
											break;
										}
										case "ITEM_CODE":{
											if(!"".equals(value)) {
												userOrderExcelItem.setCusItemCode(value);
											}else {
												if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
												}
											}
											break;
										}
										case "TRK_COM":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkCom(value);
											}else {
												if(!optionItemVO.getTrkComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ44,");
												}
											}
											break;
										}
										case "TRK_NO":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkNo(value);
											}else {
												if(!optionItemVO.getTrkNoYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ45,");
												}
											}
											break;
										}
										case "TRK_DATE":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkDate(value);
											}else {
												if(!optionItemVO.getTrkDateYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ46,");
												}
											}
											break;
										}
										case "ITEM_URL":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemUrl(value);
											}else {
												if(!optionItemVO.getItemUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ47,");
												}
											}
											break;
										}
										case "ITEM_IMG_URL":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemImgUrl(value);
											}else {
												if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ48,");
												}
											}
											break;
										}
										case "상품색상":
											//
											break;
										case "ITEM_USER_WTA":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWta(value);
											}else {
												if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "ITEM_WT_UNIT":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setWtUnit(value);
											}else {
												if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ58,");
												}
											}
											break;
										case "상품size":
											//
											break;
										case "사서함반호":
											//
											break;
										case "ITEM_DIV":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDiv(value);
											}else {
												if(!optionItemVO.getItemDivYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ49,");
												}
											}
											break;
										}
										case "MAKE_CNTRY":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCntry(value);
											}else {
												if(!optionItemVO.getMakeCntryYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ50,");
												}
											}
											break;
										}
										case "MAKE_COMPANY":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCom(value);
											}else {
												if(!optionItemVO.getMakeComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ51,");
												}
											}
											break;
										}
										case "FOOD" : {
											if(!"".equals(value)) {
												userOrderExcelList.setFood(value);
											}else {
												if(!optionOrderVO.getFoodYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ56,");
												}
											}
											break;
										}
										case "CUSTOMS_NO" : {
											if(!"".equals(value)) {
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"수취인ID번호 오류,");
												}
											}
											break;
										}
										case "EXP_YN" : {
											if(!"".equals(value)) {
												if(value.equals("Y")) {
													userOrderExcelList.setSimpleYn("N");
													userOrderExcelList.setExpValue("registExplicence1");
												}else if (value.equals("S")) {
													userOrderExcelList.setSimpleYn("Y");
													userOrderExcelList.setExpValue("simpleExplicence");
												}else {
													userOrderExcelList.setSimpleYn("");
													userOrderExcelList.setExpValue("noExplicence");
												}
											}else {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분이 비어있습니다.");
											}
											break;
										}
										case "EXP_NO" : {
											if(!"".equals(value)) {
												userOrderExcelList.setExpValue("registExplicence2");
												userOrderExcelList.setExpNo(value);
												
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {
													if(userOrderExcelList.getExpValue().equals("registExplicence2"))
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출면장번호가 비어있습니다.");
												}
											}
											break;
										}
										case "EXP_BUSINESS_NAME" : {
											if(!"".equals(value)) {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpBusinessName(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpBusinessName("");
												}
												
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출자사업자명이 비어있습니다.");
											}
											break;
										}
										case "EXP_BUSINESS_NUM" : {
											if(!"".equals(value)) {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpBusinessNum(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpBusinessNum("");
												}
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출자사업자번호가 비어있습니다.");
											}
											break;
										}
										case "EXP_SHIPPER_CODE" : {
											if(!"".equals(value)) {
												if(!userOrderExcelList.getExpValue().equals("noExplicence")) {//정식 신고 및 대행
													userOrderExcelList.setExpShipperCode(value);
												}else {//신고 안함, 간이 신고??????????????
													userOrderExcelList.setExpShipperCode("");
												}
											}else {
												if(!userOrderExcelList.getExpValue().equals("noExplicence"))
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주통관부호가 비어있습니다.");
											}
											break;
										}
										/*러시아 추가 부분*/
										case "NATIONAL_ID_DATE" : {//수취인id번호발급일
											if(!"".equals(value)) {
												userOrderExcelList.setNationalIdDate(value);
											}else {
												if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 발급일 오류,");
												}
											}
											break;
										}
										case "NATIONAL_ID_AUTHORITY" : {//수취인id번호발급기관정보
											if(!"".equals(value)) {
												userOrderExcelList.setNationalIdAuthority(value);
											}else {
												if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 번호 발급기관 정보 오류,");
												}
											}
											break;
										}
										case "CNEE_BIRTH" : {//수취인생년월일
											if(!"".equals(value)) {
												userOrderExcelList.setCneeBirth(value);
											}else {
												if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 생년월일 오류,");
												}
											}
											break;
										}
										case "TAX_NO" : {//수취인tax번호
											if(!"".equals(value)) {
												userOrderExcelList.setTaxNo(value);
											}else {
												if(!optionOrderVO.getTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인TAX 번호 오류,");
												}
											}
											break;
										}
										case "ITEM_EXPLAIN" : {//상품설명
											if(!"".equals(value)) {
												userOrderExcelItem.setItemExplan(value);
											}else {
												if(!optionItemVO.getItemExplanYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"상품설명 오류,");
												}
											}
											break;
										}
										case "ITEM_BARCODE" : {//상품바코드번호
											if(!"".equals(value)) {
												userOrderExcelItem.setItemBarcode(value);
											}else {
												if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"바코드 오류,");
												}
											}
											break;
										}
										case "BOX_NUM" : {//BOX번호
											if(!"".equals(value)) {
												userOrderExcelItem.setInBoxNum(value);
											}else {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"BOX번호 오류,");
											}
											break;
										}
									}
								}// 현재row vo에 set 완료
								// vo 검증로직은 여기
//								resultList.add(vo); // 검증된 vo 리스트에 추가
								if(listFlag) {
									if(shipperInfoYn) {
										UserOrderListVO userOrderExcelListTemp = new UserOrderListVO();
										userOrderExcelListTemp = mapper.selectUserForExcel(userId);
										userOrderExcelListTemp.dncryptData();
										
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ04,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ05,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ06,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ07,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ08,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ09,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ10,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ11,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ12,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ13,", ""));
										
										userOrderExcelList.setShipperName(userOrderExcelListTemp.getShipperName());
										userOrderExcelList.setShipperTel(userOrderExcelListTemp.getShipperTel());
										userOrderExcelList.setShipperHp(userOrderExcelListTemp.getShipperHp());
										userOrderExcelList.setShipperZip(userOrderExcelListTemp.getShipperZip());
										userOrderExcelList.setShipperCity(userOrderExcelListTemp.getShipperCity());
										userOrderExcelList.setShipperState(userOrderExcelListTemp.getShipperState());
										userOrderExcelList.setShipperAddr(userOrderExcelListTemp.getShipperAddr());
										userOrderExcelList.setShipperAddrDetail(userOrderExcelListTemp.getShipperAddrDetail());
										userOrderExcelList.setUserEmail(userOrderExcelListTemp.getUserEmail());
									}
									if((userOrderExcelList.getUserLength() != null && userOrderExcelList.getUserWidth() != null && userOrderExcelList.getUserHeight() !=null && userOrderExcelList.getDimUnit() != null) && (!userOrderExcelList.getUserLength().equals("")&& !userOrderExcelList.getUserWidth().equals("")&& !userOrderExcelList.getUserHeight().equals("")&& !userOrderExcelList.getDimUnit().equals(""))){
										if(userOrderExcelList.getDimUnit().equals("IN") || userOrderExcelList.getDimUnit().equals("INCH")) {
											userOrderExcelList.setDimUnit("IN");
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 166;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
										if(userOrderExcelList.getDimUnit().equals("CM")){
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 5000;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
									}
									
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									/* nno = comnService.selectNNO(); */
									userOrderExcelList.setNno(nno);
									
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08,");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									if(userOrderExcelList.getOrderNo().equals("")) {
										String _orderNo = time2.substring(2, time2.length()-3);
										userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
									}
									userOrderExcelList.setTransCode(transCom);
									if(userOrderExcelList.getOrderDate() == "" || userOrderExcelList.getOrderDate() == null)
										userOrderExcelList.setOrderDate(time1);
									userOrderExcelList.encryptData();
									mapper.insertUserOrderListTemp(userOrderExcelList);
									
									if(userOrderExcelList.getExpValue().equals("noExplicence")) {
										
									}else {
										insertExpLicenceInfo(userOrderExcelList);
									}
									regercyOrgStation = userOrderExcelList.getOrgStation();
									userOrderExcelList.setOrderNo("");
								}else {
									//item insert 주문번호가 같은 상품 여러개
									
								}
								userOrderExcelItem.setTransCode(transCom);
								userOrderExcelItem.setNno(nno);
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								userOrderExcelItem.setOrgStation(regercyOrgStation);
								userOrderExcelItem.setNationCode(userOrderExcelList.getDstnNation());
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								UserOrderItemVO temp = new UserOrderItemVO();
								/*
								 * temp = userOrderExcelItem; userOrderExcelItemList.add(temp);
								 */
								subNo++;
							}else {
								break;
							}
							
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				result = "F";
			} catch (Exception e) {
				e.printStackTrace();
				result = "F";
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				delFile.delete();
			}
		}
		result = "등록되었습니다.";
		return result;
		
	}

	@Override
	public ArrayList<String> selectOrgNationOption(String userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrgNationOption(userId);
	}

	@Override
	public ArrayList<HashMap<String,Object>> selectOrgStationOption(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectOrgStationOption(parameters);
	}

	@Override
	public ArrayList<String> selectDstnNationOption(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDstnNationOption(parameters);
	}

	@Override
	public ArrayList<String> selectTransCodeOption(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTransCodeOption(parameters);
	}

	@Override
	public void insertUploadSet(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteUploadSet(parameters);
		mapper.insertUploadSet(parameters);
	}

	@Override
	public void insertUploadColumn(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,String> columnNo = new HashMap<String,String>();
		HashMap<String,Object> columnInfo = new HashMap<String,Object>();
		columnInfo.put("orgStation", request.getParameter("orgStationOptions"));
		columnInfo.put("dstnNation", request.getParameter("dstnNationOptions"));
		columnInfo.put("transCode", request.getParameter("transCodeOptions"));
		columnInfo.put("userId", request.getSession().getAttribute("USER_ID"));
		mapper.deleteUploadColumn(columnInfo);
		columnNo = settingColumnNo();
		for(String key : request.getParameterMap().keySet()) {
			if(key.equals("orgStationOptions")||key.equals("dstnNationOptions")||key.equals("transCodeOptions")||key.equals("orgNationOptions"))
				continue;
			columnInfo = new HashMap<String,Object>();
			String value = request.getParameterMap().get(key)[0].toUpperCase();
			if(value.equals(""))
				continue;
			columnInfo.put("column", value);
			columnInfo.put("columnNum", columnNo.get(value));
			columnInfo.put("columnName", key);
			columnInfo.put("matchingName", key);
			columnInfo.put("orgStation", request.getParameter("orgStationOptions"));
			columnInfo.put("dstnNation", request.getParameter("dstnNationOptions"));
			columnInfo.put("transCode", request.getParameter("transCodeOptions"));
			columnInfo.put("userId", request.getSession().getAttribute("USER_ID"));
			mapper.insertUploadColumn(columnInfo);
		}
		
//		orderNo
//		orderDate
//		orgStation
//		dstnNation
//		payment
//		shipperName
//		shipperTel
//		shipperHp
//		shipperZip
//		shipperState
//		shipperCity
//		shipperAddr
//		shipperAddrDetail
//		shipperEmail
//		shipperReference
//		cneeName
//		cneeTel
//		cneeHp
//		cneeZip
//		cneeState
//		cneeCity
//		cneeAddr
//		cneeAddrDetail
//		cneeEmail
//		cneeReference1
//		cneeReference2
//		dlvReqMsg
//		boxCnt
//		userWta
//		userWtc
//		wtUnit
//		userLength
//		userWidth
//		userHeight
//		dimUnit
//		whReqMsg
//		buySite
//		food
//		getBuy
//		mallType
//		nativeCneeName
//		nativeCneeAddr
//		nativeCneeAddrDetail
//		customsNo
//		nationalIdDate
//		nationalIdAuthority
//		cneeBirth
//		taxNo
		
	}
	
	public HashMap<String,String> settingColumnNo(){
		HashMap<String,String> columnNo = new HashMap<String,String>();
		columnNo.put("A", "0");
		columnNo.put("B", "1");
		columnNo.put("C", "2");
		columnNo.put("D", "3");
		columnNo.put("E", "4");
		columnNo.put("F", "5");
		columnNo.put("G", "6");
		columnNo.put("H", "7");
		columnNo.put("I", "8");
		columnNo.put("J", "9");
		columnNo.put("K", "10");
		columnNo.put("L", "11");
		columnNo.put("M", "12");
		columnNo.put("N", "13");
		columnNo.put("O", "14");
		columnNo.put("P", "15");
		columnNo.put("Q", "16");
		columnNo.put("R", "17");
		columnNo.put("S", "18");
		columnNo.put("T", "19");
		columnNo.put("U", "20");
		columnNo.put("V", "21");
		columnNo.put("W", "22");
		columnNo.put("X", "23");
		columnNo.put("Y", "24");
		columnNo.put("Z", "25");
		columnNo.put("AA", "26");
		columnNo.put("AB", "27");
		columnNo.put("AC", "28");
		columnNo.put("AD", "29");
		columnNo.put("AE", "30");
		columnNo.put("AF", "31");
		columnNo.put("AG", "32");
		columnNo.put("AH", "33");
		columnNo.put("AI", "34");
		columnNo.put("AJ", "35");
		columnNo.put("AK", "36");
		columnNo.put("AL", "37");
		columnNo.put("AM", "38");
		columnNo.put("AN", "39");
		columnNo.put("AO", "40");
		columnNo.put("AP", "41");
		columnNo.put("AQ", "42");
		columnNo.put("AR", "43");
		columnNo.put("AS", "44");
		columnNo.put("AT", "45");
		columnNo.put("AU", "46");
		columnNo.put("AV", "47");
		columnNo.put("AW", "48");
		columnNo.put("AX", "49");
		columnNo.put("AY", "50");
		columnNo.put("AZ", "51");
		columnNo.put("BA", "52");
		columnNo.put("BB", "53");
		columnNo.put("BC", "54");
		columnNo.put("BD", "55");
		columnNo.put("BE", "56");
		columnNo.put("BF", "57");
		columnNo.put("BG", "58");
		columnNo.put("BH", "59");
		columnNo.put("BI", "60");
		columnNo.put("BJ", "61");
		columnNo.put("BK", "62");
		columnNo.put("BL", "63");
		columnNo.put("BM", "64");
		columnNo.put("BN", "65");
		columnNo.put("BO", "66");
		columnNo.put("BP", "67");
		columnNo.put("BQ", "68");
		columnNo.put("BR", "69");
		columnNo.put("BS", "70");
		columnNo.put("BT", "71");
		columnNo.put("BU", "72");
		columnNo.put("BV", "73");
		columnNo.put("BW", "74");
		columnNo.put("BX", "75");
		columnNo.put("BY", "76");
		columnNo.put("BZ", "77");
		return columnNo;
	}

	@Override
	public ArrayList<ShpngListVO> selectShpngListAll(HashMap<String, Object> parameter)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ShpngListVO> selectShpngList = new ArrayList<ShpngListVO>();
		selectShpngList = mapper.selectShpngListAll(parameter);
		return selectShpngList;
	}

	@Override
	public HashMap<String, Object> selectUserTransInfo(String parameter) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectUserTransInfo(parameter);
	}

	@Override
	public HashMap<String, Object> selectUserDeposit(HttpServletRequest request, String userId) throws Exception {
		userId = request.getSession().getAttribute("USER_ID").toString();
		return mapper.selectUserDeposit(userId);
	}

	@Override
	public UserVO selectUserPwCheck(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectUserPwCheck(userId);
	}

	@Override
	public int selectUserPassword(ManagerVO userInfo) {
		// TODO Auto-generated method stub
		return mapper.selectUserPassword(userInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectHsCodeList() throws Exception {
		return mapper.selectHsCodeList();
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserCsList(HashMap<String, Object> parameterInfo) throws Exception {
		return mapper.selectUserCsList(parameterInfo);
	}

	@Override
	public void excelDownUserDlvry(HttpServletRequest request, HttpServletResponse response, ArrayList<String> nnoList)
			throws Exception {
		
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("fromDate", request.getParameter("startDate"));
			params.put("toDate", request.getParameter("endDate"));
			params.put("hawbNo", request.getParameter("hawbNo"));
			params.put("orderNo", request.getParameter("orderNo"));
			String fileName = params.get("fromDate")+"-"+params.get("toDate")+"_발송목록";
			
			String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/send/excelSample/sendListExcel.xlsx";
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
			
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			
			Font font = wb.createFont();
			font.setFontHeightInPoints((short)9);
			font.setFontName("맑은 고딕");
			
			CellStyle leftStyle = wb.createCellStyle();
			leftStyle.setFont(font);
			leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			CellStyle rightStyle = wb.createCellStyle();
			rightStyle.setFont(font);
			rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			
			int rowNo = 1;
			String agencyBl = "";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			for (int i = 0; i < nnoList.size(); i++) {
				params.put("nno", nnoList.get(i));
				ArrayList<OrderInspListVO> orderInfo = mapper.selectSendExcelOrderInfo(params);
				
				if (orderInfo.size()!=0) {
					for (int j = 0; j < orderInfo.size(); j++) {
						orderInfo.get(j).dncryptData();
						row = sheet.createRow(rowNo);
						parameters.put("bl", orderInfo.get(j).getHawbNo());
						parameters.put("hawbNo", orderInfo.get(j).getHawbNo());
						parameters.put("transCode", orderInfo.get(j).getTransCode());
						
						if (orderInfo.get(j).getSubNo().equals("1")) {
							cell = row.createCell(0);
							cell.setCellValue(orderInfo.get(j).getOrderNo());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(1);
							cell.setCellValue(orderInfo.get(j).getOrgStation());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(2);
							cell.setCellValue(orderInfo.get(j).getDstnNation());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(3);
							cell.setCellValue(orderInfo.get(j).getHawbNo());
							cell.setCellStyle(leftStyle);

							cell = row.createCell(4);
							cell.setCellValue(orderInfo.get(j).getAgencyBl());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(5);
							cell.setCellValue(orderInfo.get(j).getCneeName());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(6);
							cell.setCellValue(orderInfo.get(j).getCneeZip());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(7);
							cell.setCellValue(orderInfo.get(j).getCneeTel());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(8);
							cell.setCellValue(orderInfo.get(j).getCneeState());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(9);
							cell.setCellValue(orderInfo.get(j).getCneeCity());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(10);
							cell.setCellValue(orderInfo.get(j).getCneeAddr());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(11);
							cell.setCellValue(orderInfo.get(j).getCneeAddrDetail());
							cell.setCellStyle(leftStyle);
							
							cell = row.createCell(12);
							cell.setCellValue(orderInfo.get(j).getWta());
							cell.setCellStyle(rightStyle);
							
							cell = row.createCell(13);
							cell.setCellValue(orderInfo.get(j).getWtc());
							cell.setCellStyle(rightStyle);
							
						}

						cell = row.createCell(14);
						cell.setCellValue(orderInfo.get(j).getItemDetail());
						cell.setCellStyle(leftStyle);
						
						cell = row.createCell(15);
						cell.setCellValue(orderInfo.get(j).getUnitValue());
						cell.setCellStyle(rightStyle);
						
						cell = row.createCell(16);
						cell.setCellValue(orderInfo.get(j).getItemCnt());
						cell.setCellStyle(rightStyle);
						
						cell = row.createCell(17);
						cell.setCellValue(orderInfo.get(j).getTotalValue());
						cell.setCellStyle(rightStyle);
						
						rowNo++;
					}
				}	
			}

			String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
			response.setContentType("ms_vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ outputFileName + ".xlsx");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public ArrayList<String> selectSendNnoList(HashMap<String, Object> params) throws Exception {
		return mapper.selectSendNnoList(params);
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
						if (!commercialInfo.getPayment().equals("")) {
							drawText("Payment : " + commercialInfo.getPayment(), ARIAL, fontSize, 106 * perMM, drawTextY,
									contentStream);
							drawTextY = drawTextY - 7 * perMM;
						}
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

//						for(int j = 0; j < commercialItem.size(); j++) {
//							String itemTitle = commercialItem.get(j).getItemDetail();
//							
//							titleWidth = ARIAL.getStringWidth(itemTitle) / 1000 * fontSize;
//							if(230.00 < titleWidth) {
//								titleWidth = 230;
//							}
//							targetTextY = drawTextLineLength(itemTitle, ARIAL, fontSize, 34*perMM+(93*perMM-titleWidth) / 2, drawTextY,contentStream,23000);
//							if(targetTextY < pivotTextY) {
//								pivotTextY = targetTextY;
//							}
//							
//							targetTextY = drawTextLineLength(commercialItem.get(j).getItemCnt(), ARIAL, fontSize, 131*perMM, drawTextY,contentStream,10000);
//							if(targetTextY < pivotTextY) {
//								pivotTextY = targetTextY;
//							}
//							
//							titleWidth = ARIAL.getStringWidth(commercialItem.get(j).getUnitValue()+"   "+commercialItem.get(j).getChgCurrency()) / 1000 * fontSize;
//							if(60.00 < titleWidth) {
//								titleWidth = 60;
//							}
//							targetTextY = drawTextLineLength(commercialItem.get(j).getUnitValue()+"   "+commercialItem.get(j).getChgCurrency(), ARIAL, fontSize, 155*perMM+(26*perMM-titleWidth)/2, drawTextY,contentStream,6000);
//							if(targetTextY < pivotTextY) {
//								pivotTextY = targetTextY;
//							}
//							
//							double amount = Double.parseDouble(commercialItem.get(j).getItemCnt())*Double.parseDouble(commercialItem.get(j).getUnitValue());
//							titleWidth = ARIAL.getStringWidth(amount+"   "+commercialItem.get(j).getChgCurrency()) / 1000 * fontSize;
//							if(60.00 < titleWidth) {
//								titleWidth = 60;
//							}
//							targetTextY = drawTextLineLength(amount+"   "+commercialItem.get(j).getChgCurrency(), ARIAL, fontSize, 181*perMM+(26*perMM-titleWidth)/2, drawTextY,contentStream,6000);
//							if(targetTextY < pivotTextY) {
//								pivotTextY = targetTextY;
//							}
//							
//							totalAmount += amount;
//							drawTextY = pivotTextY-2*perMM;
//							if(drawTextY < 9.125993) {
//								doc.addPage(blankPage);
//						        // 현재 페이지 설정
//						        page = doc.getPage(currenctPage);
//						        contentStream = new PDPageContentStream(doc, page);
//						        contentStream.drawLine(3*perMM, 5*perMM, (pageStandard.getWidth())-3*perMM, 5*perMM);
//						        contentStream.drawLine(3*perMM, (pageStandard.getHeight())-26*perMM, (pageStandard.getWidth())-3*perMM, (pageStandard.getHeight())-26*perMM);
//						        contentStream.drawLine(3*perMM, 5*perMM, 3*perMM, (pageStandard.getHeight())-26*perMM);
//						        contentStream.drawLine((pageStandard.getWidth())-3*perMM, 5*perMM, (pageStandard.getWidth())-3*perMM, (pageStandard.getHeight())-26*perMM);
//						        
//							}
//							
//						}

						// 다섯번째 가로줄
						contentStream.drawLine(3 * perMM, 30 * perMM, (pageStandard.getWidth()) - 3 * perMM, 30 * perMM);
						// 회색 박스
						contentStream.setNonStrokingColor(Color.gray);
						contentStream.fillRect((float) (3 * perMM + 0.5), 5 * perMM + 1,
								(float) (pageStandard.getWidth() - 6 * perMM - 1.5), 5 * perMM);

						// 점선

						contentStream.setNonStrokingColor(Color.black);
						drawText("INVOICE VALUES :", ARIAL, fontSize, 88 * perMM, 40 * perMM, contentStream);
						drawText(String.valueOf(commercialInfo.getTotalItemCnt()), ARIAL, fontSize, 131 * perMM, 40 * perMM, contentStream);
						drawText(String.valueOf(commercialInfo.getTotalItemValue()) + " " + chgCurreny, ARIAL, fontSize, 187 * perMM, 40 * perMM, contentStream);
						//drawText(chgCurreny, ARIAL, fontSize, 170 * perMM, 40 * perMM, contentStream);
						//drawText(Double.toString(totalAmount), ARIAL, fontSize, 183 * perMM, 40 * perMM, contentStream);

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
//					PrinterJob job = PrinterJob.getPrinterJob();
//					job.setPageable(new PDFPageable(doc));
//					job.print();
				doc.close();

	}
	
	private void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream contentStream) throws Exception {
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

	@Override
	public void printCommercialExcel(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception {
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
					//String orderDate = mapper.selectUploadDate(commercialInfo.getHawbNo());
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
				//cellStyleSetting(row, cell, styleCellLR, 4, 7);
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
						//cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						//cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
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
						//cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						amount = Double.parseDouble(commercialItem.get(j).getItemCnt())
								* Double.parseDouble(commercialItem.get(j).getUnitValue());
						cell = row.getCell(7);
						cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
						//cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
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
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 4));
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cell = row.getCell(0);
					cell.setCellValue("INVOICE VALUES : ");
					row.setHeight((short) 500);
					cell = row.getCell(5);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemCnt()));
					cell = row.getCell(7);
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemValue()) + "  " + commercialItem.get(0).getChgCurrency());
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
					cell.setCellValue(String.valueOf(commercialInfo.getTotalItemValue()) + "  " + commercialItem.get(0).getChgCurrency());
				}
				
				Font boxFont = wb.createFont();
				boxFont.setFontName("나눔고딕");
				boxFont.setFontHeight((short)800);
				
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
					sheet.addMergedRegion(new CellRangeAddress(25, 25+commercialItem.size(), 0, 0));
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
			response.setHeader("Content-Disposition", "attachment;filename="+hawbNo+"_"+comEName+".xlsx");

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
				//cellStyleSetting(row, cell, styleCellLR, 4, 7);
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
				if ("TAKEIN".equals(commercialInfo.getOrderType().toUpperCase())) {
					commercialItem = mapper.selectCommercialTakeinItem(orderNnoList.get(i));
				} else {
					commercialItem = mapper.selectCommercialItem(orderNnoList.get(i));	
				}
				
				double totalWta = 0;
				double totalWtc = 0;
				String chgCurreny = commercialItem.get(0).getChgCurrency();
				hawbNo = commercialInfo.getHawbNo();
				comEName = commercialInfo.getComEName();
				for (int j = 0; j < commercialItem.size(); j++) {
					
					totalWta += (commercialItem.get(j).getWta()*Integer.parseInt(commercialItem.get(j).getItemCnt()));
					totalWtc += (commercialItem.get(j).getWtc()*Integer.parseInt(commercialItem.get(j).getItemCnt()));

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
						//cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(String.format("%.2f", commercialItem.get(j).getWta()));
						cell = row.getCell(7);
						//cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(String.format("%.2f", commercialItem.get(j).getWtc()));
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
						//cell.setCellValue(commercialItem.get(j).getUnitValue() + "   " + commercialItem.get(j).getChgCurrency());
						cell.setCellValue(String.format("%.2f", commercialItem.get(j).getWta()));
						cell = row.getCell(7);
						cell.setCellValue(String.format("%.2f", commercialItem.get(j).getWtc()));
						//cell.setCellValue(amount + "   " + commercialItem.get(j).getChgCurrency());
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
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 5));
					styleHawb.setAlignment(CellStyle.ALIGN_RIGHT);
					cell = row.getCell(0);
					cell.setCellValue("TOTAL WEIGHT : ");
					row.setHeight((short) 500);
					cell = row.getCell(6);
					cell.setCellValue(String.format("%.2f", totalWta));
					cell = row.getCell(7);
					cell.setCellValue(String.format("%.2f", totalWtc));
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
					cell.setCellValue(String.format("%.2f", totalWta));
					cell = row.getCell(7);
					cell.setCellValue(String.format("%.2f", totalWtc));
				}
				
				Font boxFont = wb.createFont();
				boxFont.setFontName("나눔고딕");
				boxFont.setFontHeight((short)800);
				
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
					sheet.addMergedRegion(new CellRangeAddress(25, 25+commercialItem.size(), 0, 0));
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
			response.setHeader("Content-Disposition", "attachment;filename="+hawbNo+"_"+comEName+".xlsx");

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
	public void createKukkiwonExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, Object> insertKukkiwonOrder(HttpServletRequest request, MultipartHttpServletRequest multi) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			String transCom = "";
			String userId = request.getSession().getAttribute("USER_ID").toString();
			String userIp = request.getRemoteAddr();
			String pattern = "^[0-9]*$"; //숫자만
			String patternFloat = "^[0-9]*\\.?[0-9]*$"; //숫자만 Float *실수 타입*
			Date date = new Date();
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMddHHmmssSS");
			SimpleDateFormat formatDateTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTime1 = formatDate.format(date);
			String dateTime2 = formatDateTime.format(date);
			String dateTime3 = formatDateTime2.format(date);
			
			HashMap<String,Object> transParameter = new HashMap<String,Object>(); 
			transParameter.put("userId", request.getSession().getAttribute("USER_ID"));
			transParameter.put("orgStation", "082");

			Map<String, String> resMap = new HashMap<String, String>();
			String excelRoot = request.getSession().getServletContext().getRealPath("/") + "excel/";
			
			resMap = filesUpload(multi, excelRoot);
			String filePath = "";

			for (String key : resMap.keySet()) {
				filePath = resMap.get(key);
				
				FileInputStream fis = new FileInputStream(filePath);
				String ext = filePath.substring(filePath.lastIndexOf("."));
				
				if (ext.equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					XSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					int subNo = 1;

					int orderTempStatus = 0;	
					int orderStatus = 0;
					String chkOrderNo = "";
					String chkNno = "";
					
					for (int rowIdx = 1; rowIdx < rows; rowIdx++) {

						UserOrderListVO userOrder = new UserOrderListVO();
						UserOrderItemVO userOrderItem = new UserOrderItemVO();
						KukkiwonVO kukkiwonVO = new KukkiwonVO();
						
						boolean rowEmpty = true;
						XSSFRow row = sheet.getRow(rowIdx);
						XSSFRow rowPivot = sheet.getRow(0);
						
						if (row != null) {
							for (int cellNum = rowPivot.getFirstCellNum(); cellNum < rowPivot.getLastCellNum(); cellNum++) {
								XSSFCell cell = row.getCell(cellNum);
								if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK && !cell.toString().equals("")) {
									rowEmpty = false;
									break;
								}
							}
							String value = "";
							
							if (!rowEmpty) {
								userOrder.setOrgStation("082");
								
								int cells = row.getPhysicalNumberOfCells();
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) {
									int valSize = 0;
									XSSFCell cell = row.getCell(columnIndex);
									if (cell == null) {
										value = "";
									} else {
										switch (cell.getCellType()) {
										case XSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType(XSSFCell.CELL_TYPE_STRING);
											value = cell.getStringCellValue();
											break;
										case XSSFCell.CELL_TYPE_STRING:
											value = cell.getStringCellValue()+"";
											break;
										case XSSFCell.CELL_TYPE_BLANK:
											value = "";
											break;
										case XSSFCell.CELL_TYPE_ERROR:
											value = cell.getErrorCellValue()+"";
											break;
										default:
											value = cell.getStringCellValue()+"";
											break;
										}
									}
									
									String orgCellName = rowPivot.getCell(columnIndex).getStringCellValue().replaceAll("line.separator".toString(), ""); 
									String cellName = orgCellName.replaceAll(" ", "").toLowerCase();
									
									switch (cellName) {
									case "입금번호":
										if (!value.equals("")) {
											userOrder.setOrderNo(value);
											if (value.length() > 10) {
												userOrder.setStatus(userOrder.getStatus()+"TMP01,");
												value = value.substring(0, 10);
											}
										} else {
											userOrder.setOrderNo("");
										}
										break;
									case "심사일자":
										if (!value.equals("")) {
											userOrder.setOrderDate(value);
										} else {
											userOrder.setOrderDate(dateTime1);
										}
										break;
									case "도착국가":
										if (!value.equals("")) {
											userOrder.setDstnNation(value);
											transParameter.put("dstnNation", value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ02,");
											userOrder.setDstnNation("");
											transParameter.put("dstnNation", "");
										}
										break;
									case "수취인명":
										if (!value.equals("")) {
											userOrder.setCneeName(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ14,");
											userOrder.setCneeName("");
										}
										break;
									case "수취인연락처1":
										if (!value.equals("")) {
											userOrder.setCneeTel(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ15,");
											userOrder.setCneeTel("");
										}
										break;
									case "수취인연락처2":
										if (!value.equals("")) {
											userOrder.setCneeHp(value);
										} else {
											userOrder.setCneeHp(userOrder.getCneeTel());
										}
										break;
									case "수취인우편주소":
										if (!value.equals("")) {
											userOrder.setCneeZip(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ17,");
											userOrder.setCneeZip("");
										}
										break;
									case "수취인도시":
										if (!value.equals("")) {
											userOrder.setCneeCity(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ19,");
											userOrder.setCneeCity("");
										}
										break;
									case "수취인주":
										if (!value.equals("")) {
											userOrder.setCneeState(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ20,");
											userOrder.setCneeState("");
										}
										break;
									case "수취인주소":
										if (!value.equals("")) {
											userOrder.setCneeAddr(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ21,");
											userOrder.setCneeAddr("");
										}
										break;
									case "무게":
										if (!value.equals("")) {
											userOrder.setUserWta(value);
										} else {
											userOrder.setUserWta("0.1");
										}
										break;
									case "일련번호":
										kukkiwonVO.setSerialNo(value);
										break;
									case "품단":
										kukkiwonVO.setPoomDan(value);
										break;
									case "단번호":
										kukkiwonVO.setDanNo(value);
										break;
									case "성명":
										kukkiwonVO.setCneeName(value);
										break;
									}
								}
								
								String transCode = "";
								transCode = comnService.selectUserTransCode(transParameter);
								transParameter.put("transCode", transCode);
								userOrder.setTransCode(transCode);
								
								
								if (userOrder.getOrderNo().equals("") || userOrder.getOrderNo().equals(chkOrderNo)) {
									userOrder.setNno(chkNno);
									userOrder.setOrderNo(chkOrderNo);
									
								} else {
									String newNno = "";
									newNno = comnService.selectNNO();
									
									userOrder.setNno(newNno);
									userOrder.setWDate(dateTime2);
									userOrder.setUserId(userId);
									userOrder.setWUserId(userId);
									userOrder.setWUserIp(userIp);
									userOrder.setTypes("Excel");
									userOrder.setOrderType("NOMAL");
									userOrder.setPayment("DDU");
									userOrder.setWDate(dateTime1);
									userOrder.setTransCode(transCode);
									userOrder.setShipperName("KUKKIWON");
									userOrder.setShipperTel("02-567-1058");
									userOrder.setShipperHp("");
									userOrder.setShipperZip("06130");
									userOrder.setShipperCntry("");
									userOrder.setShipperCity("SEOUL");
									userOrder.setShipperState("SEOUL");
									userOrder.setShipperAddr("32, Teheran-ro 7-gil, Gangnam-gu, Seoul, Republic of Korea");
									userOrder.setShipperAddrDetail("2F");
									userOrder.setUserEmail("");
									userOrder.setShipperReference("");
									userOrder.setCneeReference1("");
									userOrder.setCneeReference2("");
									userOrder.setNativeCneeName("");
									userOrder.setCneeCntry(userOrder.getDstnNation());
									userOrder.setCneeAddrDetail("");
									userOrder.setNativeCneeAddrDetail("");
									userOrder.setNativeCneeAddr("");
									userOrder.setCneeEmail("");
									userOrder.setDlvReqMsg("");
									userOrder.setMallType("");
									userOrder.setGetBuy("");
									userOrder.setBoxCnt("1");
									userOrder.setUserWtc("0");
									userOrder.setWtUnit("KG");
									userOrder.setUserLength("0");
									userOrder.setUserWidth("0");
									userOrder.setUserHeight("0");
									userOrder.setDimUnit("CM");
									userOrder.setWhReqMsg("");
									userOrder.setBuySite("");
									if (userOrder.getUserWta().equals("")) {
										userOrder.setUserWta("0.1");
									}
									userOrder.encryptData();
									
									userOrderItem.setNno(newNno);
									userOrderItem.setSubNo("1");
									userOrderItem.setHsCode("4911990000");
									userOrderItem.setItemDetail("TAEKWONDO CERTIFICATE");
									userOrderItem.setNativeItemDetail("文件");
									userOrderItem.setChgCurrency("USD");
									userOrderItem.setUnitValue("3");
									userOrderItem.setItemCnt("1");
									userOrderItem.setQtyUnit("EA");
									userOrderItem.setBrand("");
									userOrderItem.setCusItemCode("");
									userOrderItem.setTrkCom("");
									userOrderItem.setTrkNo("");
									userOrderItem.setItemUrl("");
									userOrderItem.setItemImgUrl("");
									userOrderItem.setUserItemWta(userOrder.getUserWta());
									userOrderItem.setUserItemWtc("0");
									userOrderItem.setWtUnit("KG");
									userOrderItem.setItemDiv("");
									userOrderItem.setMakeCntry("");
									userOrderItem.setMakeCom("");
									userOrderItem.setWDate(dateTime3);
									userOrderItem.setTrkDate(dateTime3);
									
									kukkiwonVO.setNno(userOrder.getNno());
									kukkiwonVO.setOrderNo(userOrder.getOrderNo());
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrder);
									orderStatus = mapper.confirmOrderList(userOrder);

									if (orderStatus > 0 || orderTempStatus > 0) {
										userOrder.setStatus(userOrder.getStatus()+"TMP08,");
									} else {
										userOrder.setStatus(userOrder.getStatus());
									}
									
									mapper.insertUserOrderListTemp(userOrder);
									mapper.insertUserOrderItemTemp(userOrderItem);
									
									
									
								}

								kukkiwonVO.setNno(userOrder.getNno());
								kukkiwonVO.setOrderNo(userOrder.getOrderNo());
								
								if (!kukkiwonVO.getPoomDan().equals("")) {
									mapper.insertKukkiwonList(kukkiwonVO);	
								}
								
								chkNno = userOrder.getNno();
								chkOrderNo = userOrder.getOrderNo();
								
							}
						}
					}
				} else if (ext.equals(".xls")) {
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					HSSFSheet sheet = workbook.getSheetAt(0);
					int rows = sheet.getPhysicalNumberOfRows();
					int subNo = 1;

					int orderTempStatus = 0;	
					int orderStatus = 0;
					String chkOrderNo = "";
					String chkNno = "";
					
					for (int rowIdx = 1; rowIdx < rows; rowIdx++) {

						UserOrderListVO userOrder = new UserOrderListVO();
						UserOrderItemVO userOrderItem = new UserOrderItemVO();
						KukkiwonVO kukkiwonVO = new KukkiwonVO();
						
						boolean rowEmpty = true;
						HSSFRow row = sheet.getRow(rowIdx);
						HSSFRow rowPivot = sheet.getRow(0);
						
						if (row != null) {
							for (int cellNum = rowPivot.getFirstCellNum(); cellNum < rowPivot.getLastCellNum(); cellNum++) {
								HSSFCell cell = row.getCell(cellNum);
								if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK && !cell.toString().equals("")) {
									rowEmpty = false;
									break;
								}
							}
							String value = "";
							
							if (!rowEmpty) {
								userOrder.setOrgStation("082");
								
								int cells = row.getPhysicalNumberOfCells();
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) {
									int valSize = 0;
									HSSFCell cell = row.getCell(columnIndex);
									if (cell == null) {
										value = "";
									} else {
										switch (cell.getCellType()) {
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType(HSSFCell.CELL_TYPE_STRING);
											value = cell.getStringCellValue();
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = cell.getStringCellValue()+"";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = cell.getErrorCellValue()+"";
											break;
										default:
											value = cell.getStringCellValue()+"";
											break;
										}
									}
									
									String orgCellName = rowPivot.getCell(columnIndex).getStringCellValue().replaceAll("line.separator".toString(), ""); 
									String cellName = orgCellName.replaceAll(" ", "").toLowerCase();
									
									switch (cellName) {
									case "입금번호":
										if (!value.equals("")) {
											userOrder.setOrderNo(value);
											if (value.length() > 10) {
												userOrder.setStatus(userOrder.getStatus()+"TMP01,");
												value = value.substring(0, 10);
											}
										} else {
											userOrder.setOrderNo("");
										}
										break;
									case "심사일자":
										if (!value.equals("")) {
											userOrder.setOrderDate(value);
										} else {
											userOrder.setOrderDate(dateTime1);
										}
										break;
									case "도착국가":
										if (!value.equals("")) {
											userOrder.setDstnNation(value);
											transParameter.put("dstnNation", value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ02,");
											userOrder.setDstnNation("");
											transParameter.put("dstnNation", "");
										}
										break;
									case "수취인명":
										if (!value.equals("")) {
											userOrder.setCneeName(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ14,");
											userOrder.setCneeName("");
										}
										break;
									case "수취인연락처1":
										if (!value.equals("")) {
											userOrder.setCneeTel(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ15,");
											userOrder.setCneeTel("");
										}
										break;
									case "수취인연락처2":
										if (!value.equals("")) {
											userOrder.setCneeHp(value);
										} else {
											userOrder.setCneeHp("");
										}
										break;
									case "수취인우편주소":
										if (!value.equals("")) {
											userOrder.setCneeZip(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ17,");
											userOrder.setCneeZip("");
										}
										break;
									case "수취인도시":
										if (!value.equals("")) {
											userOrder.setCneeCity(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ19,");
											userOrder.setCneeCity("");
										}
										break;
									case "수취인주":
										if (!value.equals("")) {
											userOrder.setCneeState(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ20,");
											userOrder.setCneeState("");
										}
										break;
									case "수취인주소":
										if (!value.equals("")) {
											userOrder.setCneeAddr(value);
										} else {
											userOrder.setStatus(userOrder.getStatus()+"TMPREQ21,");
											userOrder.setCneeAddr("");
										}
										break;
									case "무게":
										if (!value.equals("")) {
											userOrder.setUserWta(value);
										} else {
											userOrder.setUserWta("0.1");
										}
										break;
									case "일련번호":
										kukkiwonVO.setSerialNo(value);
										break;
									case "품단":
										kukkiwonVO.setPoomDan(value);
										break;
									case "단번호":
										kukkiwonVO.setDanNo(value);
										break;
									case "성명":
										kukkiwonVO.setCneeName(value);
										break;
									}
								}
								
								String transCode = "";
								transCode = comnService.selectUserTransCode(transParameter);
								transParameter.put("transCode", transCode);
								userOrder.setTransCode(transCode);
								
								
								if (userOrder.getOrderNo().equals("") || userOrder.getOrderNo().equals(chkOrderNo)) {
									userOrder.setNno(chkNno);
									userOrder.setOrderNo(chkOrderNo);
									
								} else {
									String newNno = "";
									newNno = comnService.selectNNO();
									
									userOrder.setNno(newNno);
									userOrder.setWDate(dateTime2);
									userOrder.setUserId(userId);
									userOrder.setWUserId(userId);
									userOrder.setWUserIp(userIp);
									userOrder.setTypes("Excel");
									userOrder.setOrderType("NOMAL");
									userOrder.setPayment("DDU");
									userOrder.setWDate(dateTime1);
									userOrder.setTransCode(transCode);
									userOrder.setShipperName("KUKKIWON");
									userOrder.setShipperTel("02-567-1058");
									userOrder.setShipperHp("");
									userOrder.setShipperZip("06130");
									userOrder.setShipperCntry("");
									userOrder.setShipperCity("SEOUL");
									userOrder.setShipperState("SEOUL");
									userOrder.setShipperAddr("32, Teheran-ro 7-gil, Gangnam-gu, Seoul, Republic of Korea");
									userOrder.setShipperAddrDetail("2F");
									userOrder.setUserEmail("");
									userOrder.setShipperReference("");
									userOrder.setCneeReference1("");
									userOrder.setCneeReference2("");
									userOrder.setNativeCneeName("");
									userOrder.setCneeCntry(userOrder.getDstnNation());
									userOrder.setCneeAddrDetail("");
									userOrder.setNativeCneeAddrDetail("");
									userOrder.setNativeCneeAddr("");
									userOrder.setCneeEmail("");
									userOrder.setDlvReqMsg("");
									userOrder.setMallType("");
									userOrder.setGetBuy("");
									userOrder.setBoxCnt("1");
									userOrder.setUserWtc("0");
									userOrder.setWtUnit("KG");
									userOrder.setUserLength("0");
									userOrder.setUserWidth("0");
									userOrder.setUserHeight("0");
									userOrder.setDimUnit("CM");
									userOrder.setWhReqMsg("");
									userOrder.setBuySite("");
									userOrder.encryptData();
									
									userOrderItem.setNno(newNno);
									userOrderItem.setSubNo("1");
									userOrderItem.setHsCode("4911990000");
									userOrderItem.setItemDetail("TAEKWONDO CERTIFICATE");
									userOrderItem.setNativeItemDetail("");
									userOrderItem.setChgCurrency("USD");
									userOrderItem.setUnitValue("3");
									userOrderItem.setItemCnt("1");
									userOrderItem.setQtyUnit("EA");
									userOrderItem.setBrand("");
									userOrderItem.setCusItemCode("");
									userOrderItem.setTrkCom("");
									userOrderItem.setTrkNo("");
									userOrderItem.setItemUrl("");
									userOrderItem.setItemImgUrl("");
									userOrderItem.setUserItemWta(userOrder.getUserWta());
									userOrderItem.setUserItemWtc("0");
									userOrderItem.setWtUnit("KG");
									userOrderItem.setItemDiv("");
									userOrderItem.setMakeCntry("");
									userOrderItem.setMakeCom("");
									
									kukkiwonVO.setNno(userOrder.getNno());
									kukkiwonVO.setOrderNo(userOrder.getOrderNo());
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrder);
									orderStatus = mapper.confirmOrderList(userOrder);

									if (orderStatus > 0 || orderTempStatus > 0) {
										userOrder.setStatus(userOrder.getStatus()+"TMP08,");
									} else {
										userOrder.setStatus(userOrder.getStatus());
									}
									
									mapper.insertUserOrderListTemp(userOrder);
									mapper.insertUserOrderItemTemp(userOrderItem);
									
									
									
								}

								kukkiwonVO.setNno(userOrder.getNno());
								kukkiwonVO.setOrderNo(userOrder.getOrderNo());
								if (!kukkiwonVO.getPoomDan().equals("")) {
									mapper.insertKukkiwonList(kukkiwonVO);	
								}
								chkNno = userOrder.getNno();
								chkOrderNo = userOrder.getOrderNo();
								
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
			rst.put("status", "FAIL");
		}
		
		rst.put("status", "SUCCESS");
		return rst;
	}

	@Override
	public int selectAllOrderListCount(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectAllOrderListCount(parameters);
	}

	@Override
	public ArrayList<SendVO> selectAllOrderList(HashMap<String, Object> parameters) {
		return mapper.selectAllOrderList(parameters);
	}

	@Override
	public ArrayList<SendVO> selectAllExcelOrderList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectAllExcelOrderList(parameters);
	}

	@Override
	public String checkExcelData(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) throws Exception {
		
		return null;
	}

	@Override
	public int selectShopeeAuthCnt(HashMap<String, Object> params) {
		return mapper.selectShopeeAuthCnt(params);
	}

	@Override
	public String selectShopeeAuthExpiryDate(HashMap<String, Object> params) throws Exception {
		return mapper.selectShopeeAuthExpiryDate(params);
	}

	@Override
	public void updateInspItemTrkNo(HashMap<String, Object> parameters) throws Exception {
		mapper.updateInspItemTrkNo(parameters);
	}

	@Override
	public void printPackingListPdf(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList, String printType) throws Exception {
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
				titleWidth = NanumGothic.getStringWidth("PACKING LIST") / 1000 * fontSize;
				drawText("PACKING LIST", ARIALBOLD, fontSize, (pageStandard.getWidth() - titleWidth) / 2,
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

				drawText("⑯ Net ", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Weight (KG)", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);
				
				// Amount(단위)
				drawTextY = (pageStandard.getHeight()) - 169 * perMM;
				drawText("⑰ Volume", ARIAL, fontSize, 184 * perMM, drawTextY, contentStream);
				drawTextY = drawTextY - 4 * perMM;
				drawText("Weight (KG)", ARIAL, fontSize, 184 * perMM, drawTextY, contentStream);
				// nno = orderNnoList.get(i)
				ArrayList<CommercialItemVO> commercialItem = new ArrayList<CommercialItemVO>();
				drawTextY = (pageStandard.getHeight()) - 179 * perMM;
				float pivotTextY = (pageStandard.getHeight()) - 179 * perMM;
				float targetTextY = (pageStandard.getHeight()) - 179 * perMM;


				if ("TAKEIN".equals(commercialInfo.getOrderType().toUpperCase())) {
					commercialItem = mapper.selectCommercialTakeinItem(orderNnoList.get(i));
				} else {
					commercialItem = mapper.selectCommercialItem(orderNnoList.get(i));
				}
				String chgCurreny = commercialItem.get(0).getChgCurrency();
				double totalAmount = 0;
				float startY = (pageStandard.getHeight()) - 170 * perMM;
				int pageNum = 1;
				
				double totalWta = 0;
				double totalWtc = 0;
				for (int j = 0; j < commercialItem.size(); j++) {

					totalWta += (commercialItem.get(j).getWta()*Integer.parseInt(commercialItem.get(j).getItemCnt()));
					totalWtc += (commercialItem.get(j).getWtc()*Integer.parseInt(commercialItem.get(j).getItemCnt()));
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
					
					String formattedString = String.format("%.2f", commercialItem.get(j).getWta());
					titleWidth = ARIAL.getStringWidth(formattedString) / 1000 * fontSize;
					
					if (60.00 < titleWidth) {
						titleWidth = 60;
					}
					targetTextY = drawTextLineLength(formattedString, ARIAL, fontSize, 155 * perMM + (26 * perMM - titleWidth) / 2, drawTextY, contentStream, 6000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

					formattedString = String.format("%.2f", commercialItem.get(j).getWtc());
					titleWidth = ARIAL.getStringWidth(formattedString) / 1000 * fontSize;
					if (60.00 < titleWidth) {
						titleWidth = 60;
					}
					targetTextY = drawTextLineLength(formattedString, ARIAL,
							fontSize, 181 * perMM + (26 * perMM - titleWidth) / 2, drawTextY, contentStream, 6000);
					if (targetTextY < pivotTextY) {
						pivotTextY = targetTextY;
					}

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
						drawText("⑬ Marks and", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Number of PKGS ", ARIAL, fontSize, 4 * perMM, drawTextY, contentStream);
						
						
						// Description of goods Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						titleWidth = NanumGothic.getStringWidth("⑬ Description of goods") / 1000 * fontSize;
						drawText("⑭ Description of goods ", ARIAL, fontSize, 34 * perMM + (93 * perMM - titleWidth) / 2,
								drawTextY, contentStream);
						// Quantity/Unit Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						drawText("⑮ Quantity/", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Unit ", ARIAL, fontSize, 131 * perMM, drawTextY, contentStream);
						// Unit-price Title
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;

						drawText("⑯ Net", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Weight (KG)", ARIAL, fontSize, 158 * perMM, drawTextY, contentStream);
						// Amount(단위)
						drawTextY = (pageStandard.getHeight()) - 15 * perMM;
						drawText("⑰ Volume", ARIAL, fontSize, 187 * perMM, drawTextY, contentStream);
						drawTextY = drawTextY - 4 * perMM;
						drawText("Weight (KG)", ARIAL, fontSize, 184 * perMM, drawTextY, contentStream);

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
				// 다섯번째 가로줄
				contentStream.drawLine(3 * perMM, 30 * perMM, (pageStandard.getWidth()) - 3 * perMM, 30 * perMM);
				// 회색 박스
				contentStream.setNonStrokingColor(Color.gray);
				contentStream.fillRect((float) (3 * perMM + 0.5), 5 * perMM + 1,
						(float) (pageStandard.getWidth() - 6 * perMM - 1.5), 5 * perMM);

				// 점선

				contentStream.setNonStrokingColor(Color.black);
				drawText("TOTAL WEIGHT :", ARIAL, fontSize, 88 * perMM, 40 * perMM, contentStream);
				drawText(String.format("%.2f", totalWta), ARIAL, fontSize, 155 * perMM, 40 * perMM, contentStream);
				drawText(String.format("%.2f", totalWtc), ARIAL, fontSize, 187 * perMM, 40 * perMM, contentStream);

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

		doc.close();

	}

	@Override
	public String insertExcelDataTest(MultipartHttpServletRequest multi, HttpServletRequest request, String userId, String orderType) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		
		String result="F";
		String transCom = "";
		OrderListOptionVO optionOrderVO = new OrderListOptionVO();
		OrderItemOptionVO optionItemVO = new OrderItemOptionVO();
		OrderListExpOptionVO expressOrderVO = new OrderListExpOptionVO();
		OrderItemExpOptionVO expressItemVO = new OrderItemExpOptionVO();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyyMMddHHmmssSS");
		Date time = new Date();
		String pattern = "^[0-9]*$"; //숫자만
		String patternFloat = "^[0-9]*\\.?[0-9]*$"; //숫자만 Float *실수 타입*
		String patternJP = "^[ぁ-ゔ]+|[ァ-ヴー]+[々〆〤]*$"; //일본어만
		String time1 = format1.format(time);
		Map<String, String> resMap = new HashMap<String, String>(); //엑셀 업로드 리턴
		String excelRoot = request.getSession().getServletContext().getRealPath("/") + "excel/";
		resMap = filesUploadNomalOrder(multi, excelRoot, userId);
		String filePath = "";
		for (String key : resMap.keySet())
		{
			filePath = resMap.get(key);
			UserOrderListVO userOrderExcelList = new UserOrderListVO();
			String regercyOrgStation = "";
			String nno="";
			try {
				FileInputStream fis = new FileInputStream(filePath);
				if(filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis); // 엑셀 workbook
					XSSFSheet sheet = workbook.getSheetAt(0); // 해당 Sheet
					int rows = sheet.getPhysicalNumberOfRows(); // 해당 Sheet의 row수
					int subNo = 1;
					int orderTempStatus = 0;	
					int orderStatus = 0;
					String iossNo = "";
					XSSFRow rowPivot = sheet.getRow(0);
					for (int rowIndex = 1; rowIndex < rows; rowIndex++) 
					{ // row=0은 헤더이므로 1부터 시작
						boolean rowEmpty = true;
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
							if(!rowEmpty) {
								Date times2 = new Date();
								String time2 = format2.format(times2);
								userOrderExcelList.setWDate(time2);
								UserOrderItemVO userOrderExcelItem = new UserOrderItemVO();
								userOrderExcelItem.setUserId(userId);
								userOrderExcelItem.setWUserId(userId);
								userOrderExcelItem.setWUserIp(request.getRemoteAddr());
								userOrderExcelItem.setWDate(time1);
								userOrderExcelItem.setTrkDate(time1);
								int cells = row.getPhysicalNumberOfCells(); // 한 row당 cell개수
								/*
								 * list에 담을 현재row정보를 저장할 vo생성
								 */
//								CustomerDetail vo = new CustomerDetail();
//								vo.setStrCd(vo.getStrCd()); // 코드(항상같은정보)
//								vo.setCustNo(Long.toString(customerNo)); // 번호(+1증가)
//								customerNo = customerNo + 1;
//								vo.setStat("Y"); // 여부

								String value = "";
								boolean shipperInfoYn = true;
								boolean listFlag = false;
							
								
								boolean transChgYN = false;
								double wta = 0;
								double length = 0;
								double width = 0;
								double height = 0;
								double wtc = 0;
								
								HashMap<String,Object> transParameter = new HashMap<String,Object>();
								
								transParameter.put("userId", userId);
								transParameter.put("wUserId", (String)request.getSession().getAttribute("USER_ID"));
								transParameter.put("wUserIp", (String)request.getSession().getAttribute("USER_IP"));
								transParameter.put("orgStation", (String)request.getSession().getAttribute("ORG_STATION"));
								transParameter.put("wtc", Double.toString(wtc));
								transParameter.put("wta", Double.toString(wta));
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										default:
											value = cell.getStringCellValue() + "";
											break;
										}
										switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
											case "출발도시코드":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												transParameter.put("orgStation",value);
												break;
											}
											case "도착국가":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												String dstnNation = mapper.selectNationCode(value);
												transParameter.put("dstnNation",dstnNation);
												transParameter.put("dstnStation",dstnNation);
												break;
											}
											case "무게":{
												if(!value.equals("")) {
													transChgYN = true;		
													wta = Double.parseDouble(value);
												}else {
													value = "0";
												}
												transParameter.put("wta", Double.toString(wta));
												break;
											}
												
											case "무게단위":{
												if(value.equals("LB")) {
													wta = wta*2.20462;
													transParameter.put("wta", Double.toString(wta));
												}
												break;
											}
											case "길이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												length = Double.parseDouble(value);
												break;
											}
												
											case "폭":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												width = Double.parseDouble(value);
												break;
											}
												
											case "높이":{
												if(!value.equals("")) {
													transChgYN = true;
												}else {
													value = "0";
												}
												height = Double.parseDouble(value);
												break;
											}
											
											case "측정단위":{
												if(!value.equals("")) {
													transChgYN = true;
												}
												
												if(value.equals("IN")) {
													wtc = (height*width*length)/133;
												}else {
													wtc = (height*width*length)/5000;
												}
												transParameter.put("wtc", Double.toString(wtc));
												break;
											}
										}
									}
								}
								
								ProcedureVO rstValue = new ProcedureVO();
								if(transChgYN) {
									nno = comnService.selectNNO();
									transParameter.put("nno",nno);
									transCom = comnService.selectUserTransCode(transParameter);
									transParameter.put("transCode",transCom);
									int cnts = apiServiceImpl.checkNation(transParameter);
									if(cnts==0) {
										HashMap<String,Object> tempparameters = new HashMap<String,Object>();
										tempparameters.put("userId", transParameter.get("userId"));
										tempparameters.put("orgStation",transParameter.get("orgStation"));
										tempparameters.put("dstnNation","DEF");
										if(apiServiceImpl.selectDefaultTransCom(tempparameters) != null) {
											transCom = apiServiceImpl.selectDefaultTransCom(tempparameters);
										}
									}
									
									transParameter.put("transCode",transCom);
									rstValue  = comnService.selectTransComChangeForVo(transParameter);
									transCom= rstValue.getRstTransCode();
									optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,transParameter.get("dstnNation").toString());
									optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,transParameter.get("dstnNation").toString());
									expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,transParameter.get("dstnNation").toString());
									expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,transParameter.get("dstnNation").toString());
								}
								
								
								ArrayList<String> checkColumnArray = new ArrayList<String>();
								boolean checkColumn = false;
								
								checkColumnArray = checkColumnFilter(rowPivot,optionOrderVO,optionItemVO,transCom);
								
								if(checkColumnArray.size() != 0) {
									checkColumn = true;
								}
								
								for (int columnIndex = 0; columnIndex < rowPivot.getLastCellNum(); columnIndex++) 
								{
									XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
									if(cell != null) {
										switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고, 해당 타입에 맞게 가져온다.
										case HSSFCell.CELL_TYPE_NUMERIC:
											cell.setCellType( HSSFCell.CELL_TYPE_STRING );
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
										default:
											value = cell.getStringCellValue() + "";
											break;
										}
									}else {
										if(columnIndex==0) {
											listFlag = false;
										}
										value="";
									}
										
									switch (rowPivot.getCell(columnIndex).getStringCellValue().replaceAll(" ", "").toLowerCase()) {
										case "주문번호":{
											if(!optionOrderVO.getOrderNoYn().isEmpty()) {
												if("".equals(value)) {
													listFlag = false;
												}else {
													userOrderExcelList = new UserOrderListVO();
													userOrderExcelList.setWDate(time2);
													userOrderExcelList.setUserId(userId);
													userOrderExcelList.setWUserId(userId);
													userOrderExcelList.setWUserIp(request.getRemoteAddr());
													userOrderExcelList.setTypes("Excel");
													userOrderExcelList.setOrderType(orderType);
													
													userOrderExcelList.dncryptData();
													if(value.length() > 50) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
														value = value.substring(0,40);
													}
													userOrderExcelList.setOrderNo(value); // 커럼3
												}
											}else {
												listFlag = false;
												userOrderExcelList = new UserOrderListVO();
												if("".equals(value)) {
													String _orderNo = time2.substring(2, time2.length()-3);
													userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
												}
												userOrderExcelList.setWDate(time2);
												userOrderExcelList.setUserId(userId);
												userOrderExcelList.setWUserId(userId);
												userOrderExcelList.setWUserIp(request.getRemoteAddr());
												userOrderExcelList.setTypes("Excel");
												userOrderExcelList.setOrderType(orderType);
												userOrderExcelList.dncryptData();
												if(value.length() > 50) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP01,");
													value = value.substring(0,40);
												}
												userOrderExcelList.setOrderNo(value); // 커럼3
											}
											break;
										}
										case "주문날짜":{
											if(value.length() != 8) {
												value = "";
											}
											
											if(value.equals("")) {
												value = time1;
												listFlag = false;
											}
											userOrderExcelList.setOrderDate(value); 
											
											break;
										}
										case "출발도시코드":{
											if(!"".equals(value)) {
												userOrderExcelList.setOrgStation(value); // 컬럼0
												if(!listFlag) {
													listFlag = true;
												}
											}else {
												userOrderExcelList.setOrgStation(regercyOrgStation);
												if(!optionOrderVO.getOrgStationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ01,");
												}
											}
											break;
										}
										case "도착국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												String dstnNation = mapper.selectNationCode(value);
//												optionOrderVO = mgrServiceImpl.SelectOrderListOption(transCom,dstnNation);
//												optionItemVO = mgrServiceImpl.SelectOrderItemOption(transCom,dstnNation);
//												expressOrderVO = mgrServiceImpl.SelectExpressListOption(transCom,dstnNation);
//												expressItemVO = mgrServiceImpl.SelectExpressItemOption(transCom,dstnNation);
												userOrderExcelList.setDstnNation(value); // 컬럼1
											}else {
												userOrderExcelList.setDstnNation(""); // 컬럼1
												if(!optionOrderVO.getDstnNationYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ02,");
												}
											}
											break;
										}
										
										case "PAYMENT": {
											if (!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setPayment(value.toUpperCase());
											} else {
												userOrderExcelList.setPayment("DDU");
											}
										break;
										}
										case "운송업체":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTransCode(transCom);
											}else {
												if(!optionOrderVO.getTransCodeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ03,");
												}
											}
											break;
										}
										case "송하인명":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperName(value);
											}else {
												if(!optionOrderVO.getShipperNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ04,");
												}
											}
											break;
										}
										case "송하인전화번호":{
											if(!"".equals(value)) {
												shipperInfoYn = false;
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP03,");
													userOrderExcelList.setShipperTel("");
												}
											}else {
												if(!optionOrderVO.getShipperTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ05,");
												}
											}
											break;
										}
											
										case "송하인핸드폰번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setShipperHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP04,");
													userOrderExcelList.setShipperHp("");
												}
											}else {
												if(!optionOrderVO.getShipperHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ06,");
												}
											}
											break;
										}
										case "송하인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												if(value.length() < 9) {
													userOrderExcelList.setShipperZip(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP02,");
													userOrderExcelList.setShipperZip(value.substring(0,7));
												}
											}else {
												if(!optionOrderVO.getShipperZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ07,");
												}
											}
											break;
										}
										case "송하인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCntry(value);
											}else {
												if(!optionOrderVO.getShipperCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ08,");
												}
											}
											break;
										}
										case "송하인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperCity(value);
											}else {
												if(!optionOrderVO.getShipperCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ09,");
												}
											}
											break;
										}
										case "송하인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperState(value);
											}else {
												if(!optionOrderVO.getShipperStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ10,");
												}
											}
											break;
										}
										case "송하인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddr(value);
											}else {
												if(!optionOrderVO.getShipperAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ11,");
												}
											}
											break;
										}
										case "송하인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setShipperAddrDetail(value);
											}else {
												if(!optionOrderVO.getShipperAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ12,");
												}
											}
											break;
										}	
										case "송하인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}	
										case "송하인e-mail":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												shipperInfoYn = false;
												userOrderExcelList.setUserEmail(value);
											}else {
												if(!optionOrderVO.getUserEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ13,");
												}
											}
											break;
										}
										case "송하인reference":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperReference(value);
											}else {
												if(!optionOrderVO.getShipperReferenceYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 reference가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int shipperTaxType = Integer.parseInt(value);
													if (shipperTaxType < 0 || shipperTaxType > 12) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														userOrderExcelList.setShipperTaxType(value);
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getShipperTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "송하인세금식별번호": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxNo(value);
											} else {
												if(!optionOrderVO.getShipperTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"송하인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인reference1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference1(value);
											}else {
												if(!optionOrderVO.getCneeReference1Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference1이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인reference2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeReference2(value);
											}else {
												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 reference2이 비어있습니다.,");
												}
											}
											break;
										}
										case "payment":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setPayment(value);
											}
//											else {
//												if(!optionOrderVO.getCneeReference2Yn().isEmpty()) {
//													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"관세 지불방식이 비어있습니다.,");
//												}
//											}
											break;
										}
										case "수취인명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeName(value);
											}else {
												if(!optionOrderVO.getCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인일본명":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인현지이름":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeName(value);
											}else {
												if(!optionOrderVO.getNativeCneeNameYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ14,");
												}
											}
											break;
										}	
										case "수취인연락처1":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeTel(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP05,");
													userOrderExcelList.setCneeTel("");
												}
											}else {
												if(!optionOrderVO.getCneeTelYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ15,");
												}
											}
											userOrderExcelList.setCneeTel(value);
											break;
										}
										case "수취인연락처2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll("[^0-9]", ""));
												if(regex) {
													userOrderExcelList.setCneeHp(value);
												}else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP06,");
													userOrderExcelList.setCneeHp("");
												}
											}else {
												if(!optionOrderVO.getCneeHpYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ16,");
												}
											}
											userOrderExcelList.setCneeHp(value);
											break;
										}
										case "수취인우편주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.length() < 9) {
													userOrderExcelList.setCneeZip(value);
												}else {

													if (!userOrderExcelList.getDstnNation().toLowerCase().equals("brazil")) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP07,");
														userOrderExcelList.setCneeZip(value.substring(0,7));	
													} else {
														userOrderExcelList.setCneeZip(value);	
													}
												}
											}else {
												if(!optionOrderVO.getCneeZipYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ17,");
												}
											}
											break;
										}
										case "수취인국가":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCntry(value);									
											}else {
												if(!optionOrderVO.getCneeCntryYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ18,");
												}
											}
											break;
										}
										case "수취인도시":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeCity(value);									
											}else {
												if(!optionOrderVO.getCneeCityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ19,");
												}
											}
											break;
										}
										case "수취인주":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeState(value);
											}else {
												if(!optionOrderVO.getCneeStateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ20,");
												}
											}
											break;
										}
										case "수취인구역":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeDistrict(value);
											} else {
												if (!optionOrderVO.getCneeDistrictYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 구역이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인동": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeWard(value);
											} else {
												if (!optionOrderVO.getCneeWardYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 동이 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddr(value);
											}else {
												if(!optionOrderVO.getCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인세금식별코드": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												try {
													int cneeTaxType = Integer.parseInt(value);
													if (cneeTaxType < 0 || cneeTaxType > 12) {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값은 0~12 사이의 숫자여야 합니다.,");
													} else {
														userOrderExcelList.setCneeTaxType(value);
													}
												} catch (NumberFormatException e) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드 값이 잘못된 형식입니다.,");
												}
												
											} else {
												if(!optionOrderVO.getCneeTaxTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별코드가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인세금식별번호":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeTaxNo(value);
											} else {
												if(!optionOrderVO.getCneeTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 세금식별번호가 비어있습니다.,");
												}
											}
											break;
										}
										case "수취인일본주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인현지주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddr(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ21,");
												}
											}
											break;
										}
										case "수취인상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인상세주소2":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인현지상세주소":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNativeCneeAddrDetail(value);
											}else {
												if(!optionOrderVO.getNativeCneeAddrDetailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ22,");
												}
											}
											break;
										}
										case "수취인email":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeEmail(value);
											}else {
												if(!optionOrderVO.getCneeEmailYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ23,");
												}
											}
											break;
										}
										case "수취인요청사항":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDlvReqMsg(value);
											}else {
												if(!optionOrderVO.getDlvReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ24,");
												}
											}
											break;
										}
										case "상거래유형":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setMallType(value);
											}else {
												if(!optionOrderVO.getMallTypeYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ25,");
												}
											}
											break;
										}
										case "사용용도":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												if(value.equals("")) {
													value="1";
												}
												userOrderExcelList.setGetBuy(value);
											}else {
												if(!optionOrderVO.getGetBuyYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ26,");
												}
											}
											break;
										}
										case "box수량":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												
												if (value.equals("0")) {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setBoxCnt(value);
												}else {
													userOrderExcelList.setBoxCnt("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP09,");
												}
											} else {
												if(!optionOrderVO.getBoxCntYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ27,");
												}
											}
											break;
										}
										case "무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWta(value);
												}else {
													userOrderExcelList.setUserWta("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtaYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
										
										case "부피무게":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWtc(value);
												}else {
													userOrderExcelList.setUserWtc("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP13,");
												}
											}else {
												if(!optionOrderVO.getUserWtcYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ28,");
												}
											}
											break;
										}
											
										case "무게단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWtUnit(value);
											}else {
												if(!optionOrderVO.getWtUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ29,");
												}
											}
											break;
										}
										case "길이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserLength(value);
												}else {
													userOrderExcelList.setUserLength("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP10,");
												}
											}else {
												if(!optionOrderVO.getUserLengthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ30,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "폭":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserWidth(value);
												}else {
													userOrderExcelList.setUserWidth("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP11,");
												}
											}else {
												if(!optionOrderVO.getUserWidthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ31,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
											
										case "높이":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelList.setUserHeight(value);
												}else {
													userOrderExcelList.setUserHeight("0");
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP12,");
												}
											}else {
												if(!optionOrderVO.getUserHeightYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ32,");
												}
												userOrderExcelList.setUserLength("0");
											}
											break;
										}
										case "측정단위":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setDimUnit(value);
											}else {
												if(!optionOrderVO.getDimUnitYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ33,");
												}
												userOrderExcelList.setDimUnit("CM");
											}
											break;
										}
										case "입고메모":{
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setWhReqMsg(value);
											}else {
												if(!optionOrderVO.getWhReqMsgYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ34,");
												}
											}
											break;
										}
										case "구매사이트":{
											if(!"".equals(value)) {
												userOrderExcelList.setBuySite(value);
											}else {
												if(!optionOrderVO.getBuySiteYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ35,");
												}
											}
											break;
										}
										case "hscode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setHsCode(value);
											}else {
												if(!optionItemVO.getHsCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ36,");
												}
											}
											break;
										}
										case "상품이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDetail(value);
											}else {
												if(!optionItemVO.getItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품이름2":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										case "상품현지이름":{
											if(!"".equals(value)) {
												userOrderExcelItem.setNativeItemDetail(value);
											}else {
												if(!optionItemVO.getNativeItemDetailYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ37,");
												}
											}
											break;
										}
										
										case "화폐단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setChgCurrency(value);
											}else {
												if(!optionItemVO.getChgCurrencyYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ38,");
												}
											}
											break;
										}
										case "상품단가":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(patternFloat, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setUnitValue(value);
												}else {
													userOrderExcelItem.setUnitValue("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP14,");
												}
											}else {
												if(!optionItemVO.getUnitValueYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ39,");
												}
											}
											break;
										}
										case "상품개수":{
											if(!"".equals(value)) {
												boolean regex = Pattern.matches(pattern, value = value.replaceAll(" ", ""));
												if(regex) {
													userOrderExcelItem.setItemCnt(value);
												}else {
													userOrderExcelItem.setItemCnt("0");
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMP15,");
												}
											}else {
												if(!optionItemVO.getItemCntYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ40,");
												}
											}
											break;
										}
										case "수량단위":{
											if(!"".equals(value)) {
												userOrderExcelItem.setQtyUnit(value);
											}else {
												if(!optionItemVO.getQtyUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ41,");
												}
											}
											break;
										}
										case "브랜드":{
											if(!"".equals(value)) {
												userOrderExcelItem.setBrand(value);
											}else {
												if(!optionItemVO.getBrandYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ42,");
												}
											}
											break;
										}
										case "itemcode":{
											if(!"".equals(value)) {
												userOrderExcelItem.setCusItemCode(value);
											}else {
												if(!optionItemVO.getCusItemCodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ43,");
												}
											}
											break;
										}
										case "현지택배회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkCom(value);
											}else {
												if(!optionItemVO.getTrkComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ44,");
												}
											}
											break;
										}
										case "현지택배번호":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkNo(value);
											}else {
												if(!optionItemVO.getTrkNoYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ45,");
												}
											}
											break;
										}
										case "배송날짜":{
											if(!"".equals(value)) {
												userOrderExcelItem.setTrkDate(value);
											}else {
												if(!optionItemVO.getTrkDateYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ46,");
												}
											}
											break;
										}
										case "상품url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemUrl(value);
											}else {
												if(!optionItemVO.getItemUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ47,");
												}
											}
											break;
										}
										case "상품사진url":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemImgUrl(value);
											}else {
												if(!optionItemVO.getItemImgUrlYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ48,");
												}
											}
											break;
										}
										case "상품색상":
											//
											break;
										case "상품무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWta(value);
											}else {
												if(!optionItemVO.getItemUserWtaYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품부피무게":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setUserItemWtc(value);
											}else {
												if(!optionItemVO.getItemUserWtcYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ57,");
												}
											}
											break;
										case "상품무게단위":
											//
											if(!"".equals(value)) {
												userOrderExcelItem.setWtUnit(value);
											}else {
												if(!optionItemVO.getItemWtUnitYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ58,");
												}
											}
											break;
										case "상품size":
											//
											break;
										case "사서함반호":
											//
											break;
										case "상품종류":{
											if(!"".equals(value)) {
												userOrderExcelItem.setItemDiv(value);
											}else {
												if(!optionItemVO.getItemDivYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ49,");
												}
											}
											break;
										}
										case "제조국":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCntry(value);
											}else {
												if(!optionItemVO.getMakeCntryYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ50,");
												}
											}
											break;
										}
										case "제조회사":{
											if(!"".equals(value)) {
												userOrderExcelItem.setMakeCom(value);
											}else {
												if(!optionItemVO.getMakeComYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"TMPREQ51,");
												}
											}
											break;
										}
										case "음식포함여부" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setFood(value);
											}else {
												if(!optionOrderVO.getFoodYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMPREQ56,");
												}
											}
											break;
										}
										case "수취인id번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"수취인ID번호 오류,");
												}
											}
											break;
										}
										case "개인구별번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCustomsNo(value);
											}else {
												if(!optionOrderVO.getCustomsNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelItem.getStatus()+"개인구별번호 오류,");
												}
											}
											break;
										}
										case "화장품포함여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setCosmetic(value);
											} else {
												userOrderExcelList.setCosmetic("N");
											}
											break;
										}
										case "대면서명여부": {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
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
												userOrderExcelList.setSign(value);
											} else {
												userOrderExcelList.setSign("N");
											}
											break;
										}
										case "수출신고구분" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												if (!value.equals("N")) {
													if (value.equals("S") || value.equals("E") || value.equals("F")) {
														userOrderExcelList.setExpType(value);
													} else {
														userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분 값이 유효하지 않습니다,");
													}
												} else {
													userOrderExcelList.setExpType("N");
												}

											} else {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출신고구분이 비어있습니다,");
											}
											break;
										}
										
										case "수출화주사업자상호명": {
											if (!userOrderExcelList.getExpType().equals("N")) {
												if (!"".equals(value)) {
													if (!listFlag) {
														listFlag = true;
													}
													
													userOrderExcelList.setExpCor(value);
												} else {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
												}
											} else {
												userOrderExcelList.setExpCor("");
											}
											break;
										}
										
										case "수출화주사업자대표명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRprsn(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpRgstrNo(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자주소": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpAddr(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주사업자우편번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpZip(value);
											} else {
												if (userOrderExcelList.getExpType().equals("E")) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											break;
										}

										case "수출화주통관부호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpCstCd(value);
											} else {
												userOrderExcelList.setExpCstCd("");
											}
											break;
										}
										
										case "수출대행자상호명": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setAgtCor(value);
											} else {
												userOrderExcelList.setAgtCor("");
											}
											break;
										}
										
										case "수출대행자사업장일련번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtBizNo(value);
											} else {
												userOrderExcelList.setAgtBizNo("");
											}
											break;
										}

										case "수출대행자통관부호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setAgtCstCd(value);
											} else {
												userOrderExcelList.setAgtCstCd("");
											}
											break;
										}

										case "수출면장번호" : {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												
												userOrderExcelList.setExpNo(value);
											} else {
												userOrderExcelList.setExpNo("");
											}
											break;
										}
										
										case "ioss번호": {
											if (!"".equals(value)) {
												if (!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setShipperTaxType("6");
												userOrderExcelList.setShipperTaxNo(value);
											} else {
												
											}
											break;
										}

										
										/*러시아 추가 부분*/
										case "수취인id번호발급일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdDate(value);
											}else {
												if(!optionOrderVO.getNationalIdDateYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 발급일 오류,");
												}
											}
											break;
										}
										case "수취인id번호발급기관정보" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setNationalIdAuthority(value);
											}else {
												if(!optionOrderVO.getNationalIdAuthorityYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 ID 번호 발급기관 정보 오류,");
												}
											}
											break;
										}
										case "수취인생년월일" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setCneeBirth(value);
											}else {
												if(!optionOrderVO.getCneeBirthYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인 생년월일 오류,");
												}
											}
											break;
										}
										case "수취인tax번호" : {
											if(!"".equals(value)) {
												if(!listFlag) {
													listFlag = true;
												}
												userOrderExcelList.setTaxNo(value);
											}else {
												if(!optionOrderVO.getTaxNoYn().isEmpty()) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수취인TAX 번호 오류,");
												}
											}
											break;
										}
										case "상품설명" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemExplan(value);
											}else {
												if(!optionItemVO.getItemExplanYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"상품설명 오류,");
												}
											}
											break;
										}
										case "상품바코드번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setItemBarcode(value);
											}else {
												if(!optionItemVO.getItemBarcodeYn().isEmpty()) {
													userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"바코드 오류,");
												}
											}
											break;
										}
										case "box번호" : {
											if(!"".equals(value)) {
												userOrderExcelItem.setInBoxNum(value);
											}else {
												userOrderExcelItem.setStatus(userOrderExcelItem.getStatus()+"BOX번호 오류,");
											}
											break;
										}
									}
								}// 현재row vo에 set 완료
								// vo 검증로직은 여기
//								resultList.add(vo); // 검증된 vo 리스트에 추가
								if(listFlag) {
									if(shipperInfoYn) {
										UserOrderListVO userOrderExcelListTemp = new UserOrderListVO();
										userOrderExcelListTemp = mapper.selectUserForExcel(userId);
										userOrderExcelListTemp.dncryptData();
										
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ04,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ05,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ06,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ07,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ08,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ09,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ10,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ11,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ12,", ""));
										userOrderExcelList.setStatus(userOrderExcelList.getStatus().replace("TMPREQ13,", ""));
										
										userOrderExcelList.setShipperName(userOrderExcelListTemp.getShipperName());
										userOrderExcelList.setShipperTel(userOrderExcelListTemp.getShipperTel());
										userOrderExcelList.setShipperHp(userOrderExcelListTemp.getShipperHp());
										userOrderExcelList.setShipperZip(userOrderExcelListTemp.getShipperZip());
										userOrderExcelList.setShipperCity(userOrderExcelListTemp.getShipperCity());
										userOrderExcelList.setShipperState(userOrderExcelListTemp.getShipperState());
										userOrderExcelList.setShipperAddr(userOrderExcelListTemp.getShipperAddr());
										userOrderExcelList.setShipperAddrDetail(userOrderExcelListTemp.getShipperAddrDetail());
										userOrderExcelList.setUserEmail(userOrderExcelListTemp.getUserEmail());
									}
									if((userOrderExcelList.getUserLength() != null && userOrderExcelList.getUserWidth() != null && userOrderExcelList.getUserHeight() !=null && userOrderExcelList.getDimUnit() != null) && (!userOrderExcelList.getUserLength().equals("")&& !userOrderExcelList.getUserWidth().equals("")&& !userOrderExcelList.getUserHeight().equals("")&& !userOrderExcelList.getDimUnit().equals(""))){
										if(userOrderExcelList.getDimUnit().equals("IN") || userOrderExcelList.getDimUnit().equals("INCH")) {
											userOrderExcelList.setDimUnit("IN");
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 166;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
										if(userOrderExcelList.getDimUnit().equals("CM")){
											double tempVal = (Double.parseDouble(userOrderExcelList.getUserLength()) * 
															  Double.parseDouble(userOrderExcelList.getUserWidth()) * 
															  Double.parseDouble(userOrderExcelList.getUserHeight())) / 5000;
											userOrderExcelList.setUserWtc(String.format("%.2f", tempVal));
										}
									}
									
									//list insert, item insert, 새로운 상품 주문
									subNo = 1;
									/* nno = comnService.selectNNO(); */
									userOrderExcelList.setNno(nno);
									userOrderExcelList.setUploadType("EXCEL");
									
									orderTempStatus = mapper.confirmOrderListTemp(userOrderExcelList);	
									orderStatus = mapper.confirmOrderList(userOrderExcelList);
									
									if(orderStatus > 0 || orderTempStatus > 0) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"TMP08,");
									}else {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus());
									}
									if(userOrderExcelList.getOrderNo().equals("")) {
										String _orderNo = time2.substring(2, time2.length()-3);
										userOrderExcelList.setOrderNo(_orderNo+randomAlphaWord(3)+rowIndex);
									}
									userOrderExcelList.setTransCode(transCom);
									if(userOrderExcelList.getOrderDate() == "" || userOrderExcelList.getOrderDate() == null)
										userOrderExcelList.setOrderDate(time1);
									
									// 수출신고
									if (!userOrderExcelList.getExpType().equals("N")) {
										
										if (userOrderExcelList.getExpType().toUpperCase().equals("S") || 
												userOrderExcelList.getExpType().toUpperCase().equals("F") ||
												userOrderExcelList.getExpType().toUpperCase().equals("E")) {
											
											if (userOrderExcelList.getExpCor().equals("") || userOrderExcelList.getExpCor() == null) {
												userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자상호명이 비어있습니다,");
											}
											
											if (userOrderExcelList.getExpType().toUpperCase().equals("E")) {
												if (userOrderExcelList.getExpRprsn().equals("") || userOrderExcelList.getExpRprsn() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자대표명이 비어있습니다,");
												}
												
												if (userOrderExcelList.getExpRgstrNo().equals("") || userOrderExcelList.getExpRgstrNo() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자번호가 비어있습니다,");
												}

												if (userOrderExcelList.getExpAddr().equals("") || userOrderExcelList.getExpAddr() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자주소가 비어있습니다,");
												}

												if (userOrderExcelList.getExpZip().equals("") || userOrderExcelList.getExpZip() == null) {
													userOrderExcelList.setStatus(userOrderExcelList.getStatus()+"수출화주사업자우편번호가 비어있습니다,");
												}
											}
											
											comnServiceImpl.execExportDeclareInfo(userOrderExcelList);
										}
									}
									
									userOrderExcelList.encryptData();
									if(checkColumn) {
										userOrderExcelList.setStatus(userOrderExcelList.getStatus()+",필수 컬럼이 부족합니다.("+checkColumnArray.toString()+")");
									}
									mapper.insertUserOrderListTemp(userOrderExcelList);
									
									/*
									if(userOrderExcelList.getExpValue().equals("noExplicence")) {
										
									}else {
										insertExpLicenceInfo(userOrderExcelList);
									}
									*/
									regercyOrgStation = userOrderExcelList.getOrgStation();
									userOrderExcelList.setOrderNo("");
									
								}else {
									//item insert 주문번호가 같은 상품 여러개
								}
								
								userOrderExcelItem.setTransCode(transCom);
								userOrderExcelItem.setNno(nno);
								userOrderExcelItem.setSubNo(Integer.toString(subNo));
								userOrderExcelItem.setOrgStation(regercyOrgStation);
								userOrderExcelItem.setNationCode(userOrderExcelList.getDstnNation());
								mapper.insertUserOrderItemTemp(userOrderExcelItem); 
								UserOrderItemVO temp = new UserOrderItemVO();

								subNo++;
							}else {
								break;
							}
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				result = "F";
			} catch (Exception e) {
				e.printStackTrace();
				result = "F";
			}
			File delFile = new File(filePath);
			if(delFile.exists()) {
				//delFile.delete();
			}
		}
		result = "등록되었습니다.";
		return result;
	}


}
