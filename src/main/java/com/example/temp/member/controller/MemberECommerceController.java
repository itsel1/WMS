package com.example.temp.member.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.api.ecommerce.dto.Shopee;
import com.example.temp.api.ecommerce.dto.Shopify;
import com.example.temp.api.ecommerce.service.ShopeeHandler;
import com.example.temp.member.service.MemberECommerceService;

@Controller
public class MemberECommerceController {
	
	@Autowired
	MemberECommerceService service;
	
	@Autowired
	ShopeeHandler shopeeHandler;
	
	
	@RequestMapping(value = "/cstmr/integrate/shopify", method = RequestMethod.GET)
	public String userIntegrateShopifyView(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		HashMap<String, Object> sqlParams = new HashMap<>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		sqlParams.put("userId", userId);
		
		Shopify shopifyInfo = service.selectShopifyInfo(sqlParams);
		
		model.addAttribute("shopifyInfo", shopifyInfo);
		
		return "user/shop/shopify";
	}
	

	@RequestMapping(value = "/cstmr/integrate/shopify", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userIntegrateShopify(HttpServletRequest request) {
		HashMap<String, Object> returnMap = new HashMap<>();
		
		Shopify shopifyDto = new Shopify();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		if (request.getParameter("shopifyUrl") == null || request.getParameter("apiKey") == null || request.getParameter("apiToken") == null) {
			returnMap.put("status", "fail");
			returnMap.put("msg", "요청 항목을 찾을 수 없습니다. 다시 시도해 주세요.");
			return returnMap;
		}
		
		shopifyDto.setUserId(userId);
		shopifyDto.setShopifyUrl(request.getParameter("shopifyUrl").trim());
		shopifyDto.setApiKey(request.getParameter("apiKey").trim());
		shopifyDto.setApiToken(request.getParameter("apiToken").trim());
		shopifyDto.setUseYn("Y");
		
		service.execShopifyInfo(shopifyDto);
		
		returnMap.put("status", "success");
		returnMap.put("msg", "정상 처리 되었습니다.");
		
		return returnMap;
	}
	
	
	@RequestMapping(value = "/cstmr/integrate/shopee", method = RequestMethod.GET)
	public String userIntegrateShopeeView(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> sqlParams = new HashMap<>();
		
		String status = "";
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}

		String userId = (String) request.getSession().getAttribute("USER_ID");
		sqlParams.put("userId", userId);
		//Shopee shopeeAppInfo = service.selectShopeeAppInfo(sqlParams);
		ArrayList<Shopee> shopeeInfoList = service.selectShopeeInfo(sqlParams);
		
		//model.addAttribute("shopeeAppInfo", shopeeAppInfo);
		model.addAttribute("status", status);
		model.addAttribute("shopeeInfoList", shopeeInfoList);
		
		return "user/shop/shopee";
	}
	
	
	@RequestMapping(value = "/cstmr/integrate/shopeeAuth", method = RequestMethod.POST)
	@ResponseBody
	public String userIntegrateShopeeAuthUrl(HttpServletRequest request, HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		try {
			
			String pageUrl = shopeeHandler.createShopeeAuthPageUrl(userId);
			
			if (pageUrl == null || pageUrl.isEmpty()) {
				return null;
			}
			
			return pageUrl;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@RequestMapping(value = "/cstmr/integrate/linkShopeeAuthPage", method = RequestMethod.POST)
	@ResponseBody
	public String userLinkShopeeAuthPage(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = (String) request.getSession().getAttribute("USER_ID");
		long partnerId = Long.valueOf(request.getParameter("partnerId").trim());
		String partnerKey = request.getParameter("partnerKey");
		String keyExpiryDate = request.getParameter("keyExpiryDate").trim();
		
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("userId", userId);
		sqlParams.put("partnerId", partnerId);
		sqlParams.put("partnerKey", partnerKey);
		sqlParams.put("keyExpiryDate", keyExpiryDate);
		
		try {
			
			service.execShopeeAppInfo(sqlParams);
			
			String pageUrl = shopeeHandler.createShopeeAuthPageUrl(userId);
			// 셀러 app 사용시 아래 메서드 호출로 변경
			//String pageUrl = shopeeHandler.createShopeeSellerAuthPageUrl(sqlParams);
			
			if (pageUrl == null || pageUrl.isEmpty()) {
				return null;
			}
			
			return pageUrl;

		} catch (Exception e) {
			return null;
		}
	}
	
	
	@RequestMapping(value = "/api/{userId}/submitAuth", method = RequestMethod.GET)
	public String processShopeeAuthConfirm(HttpServletRequest request, @PathVariable("userId") String userId) {
		userId = "testuser";
		String redirectUrl = "redirect:/cstmr/integrate/shopee";
		boolean flag = true;
		
		if (request.getParameter("code") == null || request.getParameter("code") == "") {
			System.err.println("Failed to grant authorization");
			redirectUrl += "?code=1";
			return redirectUrl;
		}
		
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("userId", userId);
		
		Shopee shopeeAuthDto = new Shopee();
		// 셀러 app 사용 시 아래 주석 해제
		//shopeeAuthDto = service.selectShopeeAppInfo(sqlParams);
		
		String code = request.getParameter("code");
		
		shopeeAuthDto.setUserId(userId);

		if (request.getParameter("main_account_id") != null && request.getParameter("main_account_id") != "") {
			shopeeAuthDto.setMerchantYn("Y");
			shopeeAuthDto.setShopeeId(Long.valueOf(request.getParameter("main_account_id")));
		}
		
		if (request.getParameter("shop_id") != null && request.getParameter("shop_id") != "") {
			shopeeAuthDto.setMerchantYn("N");
			shopeeAuthDto.setShopeeId(Long.valueOf(request.getParameter("shop_id")));
		}

		shopeeAuthDto.setCode(code);
		
		ArrayList<Shopee> authList = shopeeHandler.getToken(shopeeAuthDto);
		
		if (!authList.get(0).getErrorMsg().isEmpty()) {
			System.err.println(authList.get(0).getErrorMsg());
			redirectUrl += "?code=3";
			return redirectUrl;
		}
		
		for (int i = 0; i < authList.size(); i++) {
			Shopee shopeeDto = authList.get(i);

			try {

				shopeeDto = shopeeHandler.getRefreshToken(shopeeDto);
				shopeeDto = shopeeHandler.getShopInfo(shopeeDto);
				
				service.execShopeeInfo(shopeeDto);
				
			} catch (SqlSessionException e) {
				System.err.println("SqlSession error occured : " + e.getMessage());
				continue;
		    } catch (RuntimeException e) {
				System.err.println("Runtime error occured : " + e.getMessage());
				continue;
			} catch (Exception e) {
				System.err.println("An unknown error occurred : " + e.getMessage());
				continue;
			}
		}
		
		return redirectUrl;
	}

	
	@RequestMapping(value = "/cstmr/integrate/updateShopeeInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userUpdateShopeeInfo(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> returnMap = new HashMap<>();
		
		if (request.getParameter("id") == null || request.getParameter("userId") == null) {
			returnMap.put("status", "fail");
			returnMap.put("msg", "요청 항목을 찾을 수 없습니다. 다시 시도해 주세요.");
			return returnMap;
		}
		
		String userId = request.getParameter("userId");
		String userIp = request.getRemoteAddr();
		String useYn = request.getParameter("useYn");
		String shopeeId = request.getParameter("id");
		long id = Long.valueOf(shopeeId);
		
		Shopee shopeeDto = new Shopee();
		shopeeDto.setUserId(userId);
		shopeeDto.setUseYn(useYn);
		shopeeDto.setShopeeId(id);
		
		service.updateShopeeInfoUseYn(shopeeDto);
		
		returnMap.put("status", "success");
		returnMap.put("msg", "정상 처리 되었습니다.");
		
		return returnMap;
	}
	
	@RequestMapping(value = "/cstmr/integrate/collectOrders", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> collectEcommerceOrder(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> returnMap = new HashMap<>();
		
		try {
			
			if (request.getParameter("shopType") == null || request.getParameter("shopType").isEmpty()) {
				throw new NullPointerException("shopType");
			}
			
			if (request.getParameter("fromDate") == null || request.getParameter("fromDate").isEmpty()) {
				throw new NullPointerException("fromDate");
			}
			
			if (request.getParameter("toDate") == null || request.getParameter("toDate").isEmpty()) {
				throw new NullPointerException("toDate");
			}
			
			returnMap = service.getEcommerceOrders(request);
			
		} catch (NullPointerException e) {
			System.err.println("A null pointer exception occured : " + e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "["+ e.getMessage() +"] 요청 항목을 찾을 수 없습니다. 다시 시도해 주세요.");
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
			returnMap.put("status", "fail");
			returnMap.put("msg", "SYSTEM ERROR: 일시적인 문제가 발생 되었습니다. 다시 시도해주세요.");
		}

		return returnMap;
	}
	
	
	
	
}
