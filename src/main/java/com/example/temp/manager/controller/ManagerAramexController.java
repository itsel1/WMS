package com.example.temp.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.common.vo.PagingVO;
import com.example.temp.manager.service.ManagerAramexService;
import com.example.temp.manager.vo.AramexListVO;

import net.aramex.ws.ShippingTrackAPI.v1.TrackingClientInfo;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingResult;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingService_1_0Proxy;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentRequest;
import net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentResponse;

@Controller
public class ManagerAramexController {
	
	@Value("${smtpStatus}")
    String smtpStatus;

	@Autowired
	ManagerAramexService mgrAramexService;

	@RequestMapping(value = "/mngr/aramex/aramexList", method = RequestMethod.GET)
	public String managerAcntEnterprise(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		ArrayList<AramexListVO> aramexList = new ArrayList<AramexListVO>();
		int curPage = 1;

		HashMap<String, Object> parameterInfo = new HashMap<String, Object>();
		String orgStation = (String) request.getSession().getAttribute("ORG_STATION");
		parameterInfo.put("orgStation", orgStation);
		parameterInfo.put("errorYn", request.getParameter("errorYn"));
		parameterInfo.put("hsCodeYn", request.getParameter("hsCodeYn"));

		int totalCount = mgrAramexService.selectTotalCountAramex(parameterInfo);
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}

		PagingVO paging = new PagingVO(curPage, totalCount, 1, 1000);
		parameterInfo.put("paging", paging);

		try {
			aramexList = mgrAramexService.selectAramexList(parameterInfo);
			for (int index = 0; index < aramexList.size(); index++) {
				// aramexList.get(index).dncryptData();

				aramexList.get(index).setErrorCode("N");
				aramexList.get(index).setErrorMsg("");
				
				aramexList.get(index).dncryptData();

				String getAwb = aramexList.get(index).getAwb();
				String getMawbno = aramexList.get(index).getMawbno();
				String getConsigneename = aramexList.get(index).getConsigneename();
				String getConsigneeaddress = aramexList.get(index).getConsigneeaddress();
				String getDestzipcode = aramexList.get(index).getDestzipcode();
				String getCommoditydescription = aramexList.get(index).getCommoditydescription();
				String getCustoms = aramexList.get(index).getCustoms();
				String getResYn = aramexList.get(index).getResYn();
				String getDupYn = aramexList.get(index).getDupYn();
				String setErrorMsg ="";

			    if(getAwb.equals("")||getAwb ==null) {
				   aramexList.get(index).setErrorCode("Y");
				   setErrorMsg = setErrorMsg+"Bl no Input/"; 
			    }
			    
			    if(getMawbno.equals("")||getMawbno ==null) {
					aramexList.get(index).setErrorCode("Y");
					setErrorMsg = setErrorMsg+"MawbNo no Input / "; 
				 }
			    
			    if(getConsigneename.equals("")||getConsigneename ==null) {
					   aramexList.get(index).setErrorCode("Y");
					   setErrorMsg = setErrorMsg+"Consigneename no Input / ";
				 }
			    
			    if(getConsigneeaddress.equals("")||getConsigneeaddress ==null) {
					   aramexList.get(index).setErrorCode("Y");
					   setErrorMsg = setErrorMsg+"Consigneeaddress no Input / ";
				 }
			    
//			    if(getDestzipcode.equals("")||getDestzipcode==null) {
//			    	 aramexList.get(index).setErrorCode("Y");
//					 setErrorMsg = setErrorMsg+"Destzipcode no Inpu / ";
//			    }
			    
			    if(getCustoms.equals("")||getCustoms==null) {
			    	aramexList.get(index).setErrorCode("Y");
					setErrorMsg = setErrorMsg+"Customs no Input / ";
			    }
				  
			    if(!getResYn.equals("0")) {
			    	aramexList.get(index).setErrorCode("Y");					
					setErrorMsg = setErrorMsg+"이미 등록된 Bl / ";
			    }
				 
			    if(!getDupYn.equals("1")) {
			    	aramexList.get(index).setErrorCode("Y");
					setErrorMsg = setErrorMsg+"중복된 Bl / ";
			    }
			    
			    if(getCommoditydescription.equals("")||getCommoditydescription==null) {
			    	aramexList.get(index).setErrorCode("Y");
					setErrorMsg = setErrorMsg+"Commoditydescription no Input / ";
			    }
			    
			    aramexList.get(index).setErrorMsg(setErrorMsg);
			    
			    if(aramexList.get(index).getErrorCode().equals("Y")) {
			    	parameterInfo.put("awb", getAwb);
			    	parameterInfo.put("errorcode", "Y");
			    	mgrAramexService.updateAramexListError(parameterInfo);
			    }
			    
			}

		} catch (Exception e) {
			aramexList = null;
		}
	
	
		model.addAttribute("aramexList", aramexList);
		model.addAttribute("paging", paging);
		model.addAttribute("parameterInfo", parameterInfo);
		return "adm/rls/aramex/aramexList";
	}
	
	
	
	/**
	 * Page : Manager 입고작업 - 입고 작업 
	 * URI : mngr/aramex/aramexExcelUpload
	 * Method : GET
	 */
	@RequestMapping(value = "/mngr/aramex/aramexExcelUpload", method = RequestMethod.POST)
	@ResponseBody
	public String managerAramexExcelUpload(HttpServletRequest request, HttpServletResponse response, Model model, MultipartHttpServletRequest multi)
			throws Exception {
		String result = "F";
		String types = request.getParameter("formSelect");
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		try {
			result = mgrAramexService.insertAramexExcelUpload(multi, request,parameters);
 		}catch (Exception e) {
			// TODO: handle exception
 			result = "F";
		}
 		
		return result;
	}
	
	
	@RequestMapping(value = "/mngr/aramex/aramexAplly", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> aramexApply(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		
		HashMap<String, Object> parameters = new HashMap<String,Object>();		
		parameters.put("orgStation",(String)request.getSession().getAttribute("ORG_STATION"));
		parameters.put("userId", (String)request.getSession().getAttribute("USER_ID"));
		parameters.put("userIp", request.getRemoteAddr());
		
		HashMap<String, Object> Rst = new HashMap<String, Object>();		
		
		

		try {
			Rst = mgrAramexService.applyAramex(parameters);
 		}catch (Exception e) {
		
 			Rst = null;
		}
 		
		return Rst;
	}

//	@RequestMapping(value = "/mngr/aramexWeight", method = RequestMethod.POST)
//	public void aramexWeightUpdate(HttpServletRequest request, HttpServletResponse response, Model model)
//			throws Exception {
//		mgrAramexService.sendAramexWeightFtp();
//	}
	
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 0,9,18 * * *")
	@Transactional(rollbackFor=Exception.class) 
	@RequestMapping(value = "/api/aramex/sendSftp", method = RequestMethod.GET)
	public void aramexWeightUpdate() throws Exception {
		if(smtpStatus.equals("on")) {
			mgrAramexService.sendAramexWeightFtp();
		}

		//System.out.println("ASDASD");
		///return "";
	}
}
