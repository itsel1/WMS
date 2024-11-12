package com.example.temp.api.shop;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beust.jcommander.Parameters;
import com.example.temp.api.shop.shopee.HandleShopeeService;
import com.example.temp.api.shop.shopee.ShopeeDTO;

@Controller
public class ShopController {
	
	@Autowired
	Shopify shopify;
	
	@Autowired
	HandleShopeeService shopee;
	
	
	@RequestMapping(value = "/api/shop/collectOrder", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> collectShopOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String shopType = request.getParameter("shopType").toUpperCase().trim();
		
		switch (shopType) {
		case "SHOPIFY":
			result = shopify.collectOrder(request);
			break;
		case "SHOPEE":
			result = shopee.collectOrder(request);
			break;
		default:
			result.put("StatusCode", "D1");
			result.put("Msg", "쇼핑몰 연동 필요");
			break;
		}
		
		return result;
	}
	
	
/*
	@RequestMapping(value = "/api/{userId}/shopeeAuth", method = RequestMethod.GET)
	public String createShopeeToken(@PathVariable("userId") String userId, HttpServletRequest request) {
		
		String redirectUrl = "redirect:/cstmr/integrate/shopee";
		boolean flag_1 = true;
		HashMap<String, Object> newTokenMap = new HashMap<String, Object>();
		HashMap<String, Object> sqlParams = new HashMap<>();
		ShopeeDTO shopeeParameters = new ShopeeDTO();
		shopeeParameters.setUserId(userId);
		sqlParams.put("userId", userId);

		int cnt = shopee.selectShopeeInfoCnt(sqlParams);
		if (cnt > 0) {
			String dUserId = (String) request.getSession().getAttribute("USER_ID");
			String dUserIp = (String) request.getRemoteAddr();
			shopeeParameters.setDUserId(dUserId);
			shopeeParameters.setDUserIp(dUserIp);
			shopee.insertShopeeHistory(shopeeParameters);
			shopee.deleteShopeeList(shopeeParameters);
		}
	
		newTokenMap = shopee.createShopeeToken(userId, request);	// token 신규 발급
		
		if (newTokenMap == null || newTokenMap.get("status").equals("fail")) {
			flag_1 = false;
		}
		
		if (flag_1) {
			ArrayList<ShopeeDTO> shopeeDataList = new ArrayList<>();
			Object value = newTokenMap.get("shopeeData");
			
			if (value instanceof ArrayList<?>) {
				ArrayList<?> valueList = (ArrayList<?>) value;
				for (Object obj : valueList) {
					if (obj instanceof ShopeeDTO) {
						shopeeDataList.add((ShopeeDTO) obj);
					}
				}
			}
			
			for (int si = 0; si < shopeeDataList.size(); si++) {
				ShopeeDTO shopeeDataOne = (ShopeeDTO) shopeeDataList.get(si);
				
				try {
					
					shopeeDataOne = shopee.getShopeeAccessToken(shopeeDataOne);
					
					if (!shopeeDataOne.getErrorMsg().isEmpty()) {
						System.err.println(shopeeDataOne.getErrorMsg());
					}
					
					shopeeDataOne = shopee.getShopeeAuthExpiresDate(shopeeDataOne);
					shopee.insertShopeeInfo(shopeeDataOne);
					
				} catch (ClassCastException e) {
			        System.err.println("형변환 오류가 발생했습니다: " + e.getMessage());
			        continue;
				} catch (NullPointerException e) {
			        System.err.println("널 포인터 오류가 발생했습니다: " + e.getMessage());
			        continue;
			    } catch (Exception e) {
			        System.err.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
			        continue;
			    }
			}
			
		} else {
			redirectUrl = redirectUrl + "?status=0";
			return redirectUrl;
		}

		return redirectUrl;
	}
	*/
/*
	@RequestMapping(value = "/cstmr/integrate/shopify", method = RequestMethod.GET)
	public String cstmrIntegrateShopify(HttpServletRequest request, Model model) throws Exception {
		
		String userId = (String) request.getSession().getAttribute("USER_ID"); 
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("userId", userId);
		
		HashMap<String, Object> shopifyInfo = new HashMap<>();
		shopifyInfo = shopify.selectShopifyInfo(sqlParams);
		
		model.addAttribute("params", sqlParams);
		model.addAttribute("shopifyInfo", shopifyInfo);
		
		return "user/shop/shopify";
	}
*/
	
	/*
	
	@RequestMapping(value = "/cstmr/integrate/shopee", method = RequestMethod.GET)
	public String cstmrIntegrateShopee(HttpServletRequest request, Model model) throws Exception {
		
		String status = "";
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		map.put("userId", userId);
		map.put("merchantYn", "N");
		
		int cnt = shopee.selectShopeeInfoCnt(map);
		ArrayList<ShopeeDTO> shopeeList = shopee.selectShopeeInfoList(map);
		
		model.addAttribute("status", status);
		model.addAttribute("shopeeListCnt", cnt);
		model.addAttribute("shopeeList", shopeeList);
		
		return "user/shop/shopee";
	}
*/
	/*
	@RequestMapping(value = "/cstmr/integrate/shopee/updateShop", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> cstmrIntegrateShopeeUpdateShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> rst = new HashMap<>();
		
		try {
		
			ShopeeDTO shopeeParameters = new ShopeeDTO();
			String dUserId = (String) request.getSession().getAttribute("USER_ID");
			String dUserIp = (String) request.getRemoteAddr();
			String userId = request.getParameter("userId");
			String useYn = request.getParameter("useYn");
			String shopeeId = request.getParameter("id");
			long id = Long.valueOf(shopeeId);
			
			shopeeParameters.setUserId(userId);
			shopeeParameters.setUseYn(useYn);
			shopeeParameters.setShopeeId(id);
			shopeeParameters.setDUserId(dUserId);
			shopeeParameters.setDUserIp(dUserIp);
			
			shopee.updateShopeeUseYn(shopeeParameters);
			
			rst.put("status", "success");
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rst.put("status", "fail");
		}
		
		return rst;
	}
	
*/
	@RequestMapping(value = "/cstmr/integrate/shopee/linkShopee", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> cstmrIntegrateShopeeLink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<>();
		HashMap<String, Object> params = new HashMap<>();
		String userId = (String) request.getSession().getAttribute("USER_ID");
		params.put("userId", userId);
		
		rst = shopee.linkShopeeAuthPage(params);

		return rst;
	}
	
}
