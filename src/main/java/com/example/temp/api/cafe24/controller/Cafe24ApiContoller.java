package com.example.temp.api.cafe24.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.temp.api.cafe24.service.Cafe24Service;
import com.example.temp.api.cafe24.vo.Cafe24OrderParameter;
import com.example.temp.api.cafe24.vo.Cafe24ShopInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24UserInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24UserTokenInfo;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.security.SHA256Encryption;
import com.example.temp.security.SecurityService;

@Controller
public class Cafe24ApiContoller {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	Cafe24Service cafe24Service;
	@Autowired
	SecurityService securityService;
	@Autowired
	SHA256Encryption sha256Enryption;

	@RequestMapping(value="/api/cafe24/token/success",method=RequestMethod.GET)
	public String tokenSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "comn/cafe24Success";
	};
	

	@RequestMapping(value="/api/cafe24/userReg",method=RequestMethod.GET)
	public String userReg(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		HashMap<String,Object> parameterInfo = new HashMap<String,Object>();
		parameterInfo.put("cafe24Id", request.getParameter("user_id"));
		parameterInfo.put("mallId", request.getParameter("mall_id"));
		
		model.addAttribute("parameterInfo",parameterInfo);

		return "comn/cafe24reg";
	}
	

	@RequestMapping(value="/api/cafe24/userLoginChk",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> userLoginChk(HttpServletRequest request, HttpServletResponse response , Cafe24UserInfoVo cafe24UserInfo ) throws Exception{
		
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		String userId = request.getParameter("userId"); 
		String userPw = request.getParameter("userPw");
		
        MemberVO member = new MemberVO();
        member = securityService.getSelectMeberInfo(userId);
        userPw = sha256Enryption.encode(userPw);
        
        
        if(userPw.equals(member.getPassword())) {
        	rst.put("rstCode", "success");
        	rst.put("rstMsg", "인증 되었습니다.");
        }else {
        	rst.put("rstCode", "fail");
        	rst.put("rstMsg", "아이디 또는 비밀번호를 확인해주세요.");
        }
        
		return rst; 
	}
	

	@RequestMapping(value="/api/cafe24/userGetOAith",method=RequestMethod.POST)
	@ResponseBody
	public String userGetOAith(HttpServletRequest request, HttpServletResponse response , Cafe24UserInfoVo cafe24UserInfo ) throws Exception{
		
		cafe24UserInfo.setUserId((String)request.getSession().getAttribute("USER_ID"));
		//String cafe24Id = cafe24UserInfo.getCafe24Id();
		String mallId = cafe24UserInfo.getMallId();
		
		String clientId = "nXedVhOIDC0S3VF6gMegWM"; 
		String redirectUri = "https://"+request.getServerName()+"/api/cafe24/token";
		String scope = "mall.read_store,mall.read_order,mall.read_product,mall.read_collection,mall.read_supply";
	
		try {
			cafe24Service.insertUserCafe24Info(cafe24UserInfo);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		String url = "https://"+mallId+".cafe24api.com/api/v2/oauth/authorize?response_type=code"
				+ "&client_id="+clientId
				+ "&redirect_uri="+redirectUri
				+ "&scope="+scope;
		return url;
	}

	
	
	@RequestMapping(value="/api/cafe24/getOAuth",method=RequestMethod.GET)
	public void getOAuth(HttpServletRequest request, HttpServletResponse response , Cafe24UserInfoVo cafe24UserInfo) throws Exception{
		
		String cafe24Id = "aciexp";
		String clientId = "nXedVhOIDC0S3VF6gMegWM";
		String redirectUri = "https://"+request.getServerName()+"/api/cafe24/token";
		String scope = "mall.read_store,mall.read_order,mall.read_product,mall.read_collection";

		String url = "https://"+cafe24Id+".cafe24api.com/api/v2/oauth/authorize?response_type=code"
				+ "&client_id="+clientId
				+ "&redirect_uri="+redirectUri
				+ "&scope="+scope;
		
		response.sendRedirect(url);
	}

	
	@RequestMapping(value="/api/cafe24/token",method=RequestMethod.GET)
	public void getTokenRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String userId = (String)request.getSession().getAttribute("USER_ID");
			Cafe24UserInfoVo cafe24UserInfo = new Cafe24UserInfoVo();
			cafe24UserInfo.setUserId(userId);
			cafe24UserInfo = cafe24Service.selectUserCafe24Info(cafe24UserInfo);
			
			HttpURLConnection connection = null;
			String accessToken = request.getParameter("code");

			String mallId = "";
			String clientId = "";
			String clientSecretKey = "";
			
			mallId = cafe24UserInfo.getMallId();
			clientId = "nXedVhOIDC0S3VF6gMegWM";
			clientSecretKey  = "3F3UsSWkMRTsTxKZRyINAB";
			
			String requestUrl = "https://"+mallId+".cafe24api.com/api/v2/oauth/token";
			String redirectUri = "https://"+request.getServerName()+"/api/cafe24/token";
			String authorization = clientId+":"+clientSecretKey; 
			String base64Auth = "";
			base64Auth = new String(Base64.encodeBase64(authorization.getBytes()));
			/*
			Encoder encoder = Base64.getEncoder(); 
			byte[] encodedBytes = encoder.encode(targetBytes);
			System.out.println("인코딩 text : " + new String(encodedBytes)); 
			System.out.println("디코딩 text : " + new String(decodedBytes));
			Decoder decoder = Base64.getDecoder(); byte[] decodedBytes = decoder.decode(encodedBytes);
			*/
			
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization","Basic "+base64Auth);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			String parameter = "grant_type=authorization_code&code="+accessToken+"&redirect_uri="+redirectUri;		
			OutputStream os = connection.getOutputStream();
			os.write(parameter.getBytes("UTF-8"));
			os.flush();
			
			int responseCode = connection.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String sb =  in.readLine();

			JSONObject jsonObject = new JSONObject(sb);
			
			if(responseCode == 200) {
				TimeZone tz;
				String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
				SimpleDateFormat df = new SimpleDateFormat(dateFormat);
				tz = TimeZone.getTimeZone("Asia/Seoul"); 
				df.setTimeZone(tz);
				
				String seoulDateFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(seoulDateFormat);		
				
				Cafe24UserTokenInfo cafe24Token = new Cafe24UserTokenInfo();
				cafe24Token.setUserId(userId);
				cafe24Token.setAccessToken((String)jsonObject.get("access_token"));
				cafe24Token.setRefreshToken((String)jsonObject.get("refresh_token"));
				cafe24Token.setClientId((String)jsonObject.get("client_id"));
				cafe24Token.setCafe24Id((String)jsonObject.get("user_id"));
				cafe24Token.setMallId((String)jsonObject.get("mall_id"));
				
				Date date =  df.parse((String)jsonObject.get("expires_at"));				
				cafe24Token.setExpiresAt(sdf.format(date));
				date = df.parse((String)jsonObject.get("refresh_token_expires_at"));
				cafe24Token.setRefreshTokenExpiresAt(sdf.format(date));
				date = df.parse((String)jsonObject.get("issued_at"));
				cafe24Token.setIssuedAt(sdf.format(date));
				
				int rst = 0 ;
				rst = cafe24Service.insertUserCafe24Token(cafe24Token);
				
				response.sendRedirect("/api/cafe24/token/success");
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}

	}
	
	@RequestMapping(value="/api/cafe24/userShopInfo",method=RequestMethod.GET)
	public void userShopInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String userId = request.getParameter("userId");
		String mallId = request.getParameter("mallId");
		
		Cafe24UserInfoVo parameterInfo = new Cafe24UserInfoVo();
		parameterInfo.setUserId(userId);
		parameterInfo.setMallId(mallId);
		
		Cafe24UserTokenInfo userToken = new Cafe24UserTokenInfo(); 
		userToken = cafe24Service.selectUserCafe24Token(parameterInfo);
		userToken = cafe24Service.userTokenChk(userToken);
		
		ArrayList<Cafe24ShopInfoVo> userCafe24ShopList = new ArrayList<Cafe24ShopInfoVo>();  
		userCafe24ShopList  = cafe24Service.userCafe24shops(userToken);
		
		for(int i = 0 ; i < userCafe24ShopList.size(); i++) {
			//System.out.println(userCafe24ShopList.get(i).getShopNo());
		}
		
	}

	//일괄 토큰 Refresh
	@RequestMapping(value="/api/cafe24/adminTokenRefresh",method=RequestMethod.GET)
	public void getTokenRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String clientId = "nXedVhOIDC0S3VF6gMegWM";
			String clientSecretKey = "3F3UsSWkMRTsTxKZRyINAB";
			
			String authorization = clientId+":"+clientSecretKey; 
			String base64Auth = "";
			base64Auth = new String(Base64.encodeBase64(authorization.getBytes()));
			
			ArrayList<Cafe24UserTokenInfo> cafe24TokenInfoList = new ArrayList<Cafe24UserTokenInfo>();
			
			Cafe24UserTokenInfo prameterInfo = new Cafe24UserTokenInfo();
			prameterInfo.setUserId("");
			cafe24TokenInfoList = cafe24Service.selectAdminCafe24TokenList(prameterInfo);
			
			HttpURLConnection connection = null;
			
			for(int i = 0 ; i < cafe24TokenInfoList.size() ; i++) {
				String userId = "";
				String mallId = "";
				String refreshToken = "";
				
				userId = cafe24TokenInfoList.get(i).getUserId();
				mallId = cafe24TokenInfoList.get(i).getMallId();
				refreshToken = cafe24TokenInfoList.get(i).getRefreshToken();
				
	
				String requestUrl = "https://"+mallId+".cafe24api.com/api/v2/oauth/token";
				
				URL url = new URL(requestUrl);
				connection = (HttpURLConnection)url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Authorization","Basic "+base64Auth);
				connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
	
				String parameter = "grant_type=refresh_token&refresh_token="+refreshToken;		
				OutputStream os = connection.getOutputStream();
				os.write(parameter.getBytes("UTF-8"));
				os.flush();
				
				int responseCode = connection.getResponseCode();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String sb =  in.readLine();
	
				JSONObject jsonObject = new JSONObject(sb);
				
				if(responseCode == 200) {
				
					TimeZone tz;
					String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
					SimpleDateFormat df = new SimpleDateFormat(dateFormat);
					tz = TimeZone.getTimeZone("Asia/Seoul"); 
					df.setTimeZone(tz);
					
					String seoulDateFormat = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat sdf = new SimpleDateFormat(seoulDateFormat);		
					
					Cafe24UserTokenInfo cafe24Token = new Cafe24UserTokenInfo();
					cafe24Token.setUserId(userId);
					cafe24Token.setAccessToken((String)jsonObject.get("access_token"));
					cafe24Token.setRefreshToken((String)jsonObject.get("refresh_token"));
					cafe24Token.setClientId((String)jsonObject.get("client_id"));
					cafe24Token.setCafe24Id((String)jsonObject.get("user_id"));
					cafe24Token.setMallId((String)jsonObject.get("mall_id"));
					
					Date date =  df.parse((String)jsonObject.get("expires_at"));				
					cafe24Token.setExpiresAt(sdf.format(date));
					date = df.parse((String)jsonObject.get("refresh_token_expires_at"));
					cafe24Token.setRefreshTokenExpiresAt(sdf.format(date));
					date = df.parse((String)jsonObject.get("issued_at"));
					cafe24Token.setIssuedAt(sdf.format(date));
					
					int rst = 0 ;
					rst = cafe24Service.insertUserCafe24Token(cafe24Token);
				}else {
					System.out.println(responseCode);
				}
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	// 주문데이터 호출
	@RequestMapping(value="/api/cafe24/getDataAdmin",method=RequestMethod.GET)
	public void getDataAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
			String mallId = request.getParameter("mallId");
			String orderId = request.getParameter("orderNo");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String wUserId = (String)request.getSession().getAttribute("USER_ID");
			fromDate = "2021-12-01";
			if(wUserId == null||wUserId.equals("")){
				wUserId = "System";
			}
			if(userId==null) {
				userId = "";
			}
			if(mallId==null) {
				mallId = "";
			}
			ArrayList<Cafe24UserTokenInfo> cafe24TokenInfoList = new ArrayList<Cafe24UserTokenInfo>();
			Cafe24UserTokenInfo prameterInfo = new Cafe24UserTokenInfo();
			prameterInfo.setUserId(userId);
			prameterInfo.setMallId(mallId);	
			cafe24TokenInfoList = cafe24Service.selectAdminCafe24TokenList(prameterInfo);
			Cafe24UserTokenInfo cafe24TokenInfo =  new Cafe24UserTokenInfo();
			for(int i = 0 ; i < cafe24TokenInfoList.size() ; i++) {
				cafe24TokenInfo = cafe24TokenInfoList.get(i);
				cafe24TokenInfo = cafe24Service.userTokenChk(cafe24TokenInfo);
				ArrayList<Cafe24ShopInfoVo> cafe24ShopInfoList = new ArrayList<Cafe24ShopInfoVo>();
				cafe24ShopInfoList = cafe24Service.userCafe24shops(cafe24TokenInfo);
				//멀티샵 일경우 해당 샵의 수 만큼 배열
				for(int ii = 0 ; ii < cafe24ShopInfoList.size(); ii++) {
					cafe24ShopInfoList.get(ii).getShopNo();
					Cafe24OrderParameter cafe24OrderParameter = new Cafe24OrderParameter();
					cafe24OrderParameter.setUserId(cafe24TokenInfo.getUserId());
					cafe24OrderParameter.setAccessToken(cafe24TokenInfo.getAccessToken());
					cafe24OrderParameter.setMallId(cafe24TokenInfo.getMallId());
					cafe24OrderParameter.setShopNo(cafe24ShopInfoList.get(ii).getShopNo());
					cafe24OrderParameter.setStartDate(fromDate);
					cafe24OrderParameter.setEndDate(toDate);
					cafe24OrderParameter.setWUserId(wUserId);
					cafe24OrderParameter.setRemoteAddr(request.getRemoteAddr());
					cafe24Service.getCafe24Datas(cafe24OrderParameter);
				}
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
	}
}
