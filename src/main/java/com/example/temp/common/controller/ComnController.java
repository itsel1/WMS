package com.example.temp.common.controller;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.temp.api.CommonUtils;
import com.example.temp.api.Tracking;
import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.service.CJHandler;
import com.example.temp.api.logistics.service.FastboxHandler;
import com.example.temp.api.logistics.service.LogisticsService;
import com.example.temp.api.logistics.service.VietnamPostHandler;
import com.example.temp.api.logistics.service.YongsungHandler;
import com.example.temp.api.shipment.company.YongSung;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.DeliverTrackVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.common.vo.MemberVO;
import com.example.temp.common.vo.ZoneVO;
import com.example.temp.conf.TranslationVO;
import com.example.temp.manager.service.ManagerAramexService;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.service.MemberService;
import com.example.temp.member.vo.UserVO;
import com.example.temp.security.AuthUserProvider;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.security.SecurityService;
import com.example.temp.smtp.SmtpService;
import com.example.temp.trans.aci.AciAPI;
import com.example.temp.trans.cj.CJApi;
import com.example.temp.trans.cj.CJOrderVo;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.cj.CJPodVo;
import com.example.temp.trans.cse.CseAPI;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.ems.EmsApi;
import com.example.temp.trans.fastbox.FastBoxParameterVO;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.gts.GtsAPI;
import com.example.temp.trans.hanjin.HanjinAPI;
import com.example.temp.trans.itemCarry.ItcAPI;
import com.example.temp.trans.ocs.OcsAPI;
import com.example.temp.trans.parcll.ParcllAPI;
import com.example.temp.trans.seko.SekoAPI;
import com.example.temp.trans.t86.Type86API;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.example.temp.trans.yunexpress.YunExpressAPI;
import com.itextpdf.text.log.SysoCounter;

@Controller
public class ComnController {
	
	@Autowired
    private LocaleResolver localeResolver;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	ManagerAramexService mgrAramexService;
	
	@Autowired
	MemberService usrService;
	
	@Autowired
	YongSungAPI ysApi;
	
	@Autowired
	EfsAPI efsApi;
	
	@Autowired
	AciAPI aciApi;
	
	@Autowired
	OcsAPI ocsApi;
	
	@Autowired
	SekoAPI sekoApi;
	
	@Autowired
	ItcAPI itcApi;
	
	@Autowired
	GtsAPI gtsAPI;
	
	@Autowired
	CseAPI cseAPI;
	
	@Autowired
	CJApi cjApi;
	
	@Autowired
	EmsApi emsApi;
	
	@Autowired
	FastboxAPI fbApi;
	
	@Autowired
	Type86API t86Api;
	
	@Autowired
	SmtpService smtp;
	
	@Autowired
	FastboxAPI fastboxApi;
	
	@Autowired
	HanjinAPI hjApi;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Autowired
	ParcllAPI prclApi;
	
	@Autowired
	YunExpressAPI yunApi;
	
	@Autowired
	YongSung yongsung;
	
	@Autowired
	LogisticsService logisticsService;
	
	@Autowired
	CJHandler cjHandler;
	
	@Autowired
	VietnamPostHandler vnpHandler;
	
	@Autowired
	YongsungHandler ysHandler;
	
	@Autowired
	FastboxHandler fbHandler;
	
	@Autowired
	CommonUtils commUtils;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	@RequestMapping(value = "/comn/fastbox/getWeight", method = RequestMethod.GET)
	@ResponseBody
	public String getFastboxWeight(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		fastboxApi.getFastboxWeight();
		
		return "test";
	}
	
	@RequestMapping(value = "/comn/logout", method = RequestMethod.POST)
	public String comnLogout() {
		return "redirect:/comn/login"; 
	}
	
	@RequestMapping(value = "/prv/logout", method = RequestMethod.POST)
	public String prvLogout() {
		return "redirect:/prv/login";
	}
	
	@RequestMapping(value="/prv/login",method=RequestMethod.GET)
	public String privateLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		//return "comn/loginForAdm";
		return "comn/loginForAdm_2";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String indexPage1(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "redirect:/comn/login";
	}
	
	@RequestMapping(value="/comn/login",method=RequestMethod.GET)
	public String indexPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String browserLanguage = request.getHeader("Accept-Language");
		String firstLanguage = "";
		
		if (browserLanguage != null && !browserLanguage.isEmpty()) {
			String[] languages = browserLanguage.split(",");
			firstLanguage = languages[0].split(";")[0];
		}
		
		System.out.println(browserLanguage);
		System.out.println(firstLanguage);
		String target = (String)request.getRequestURI();

		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			if (firstLanguage.equals("")) {
				firstLanguage = "en";
			} else {
				if (firstLanguage.startsWith("en")) {
					firstLanguage = "en";
				} else if (firstLanguage.startsWith("zh")) {
					firstLanguage = "zh";
				} else if (firstLanguage.startsWith("ja")) {
					firstLanguage = "ja";
				} else if (firstLanguage.startsWith("ko")) {
					firstLanguage = "ko";
				} else {
					firstLanguage = "ko";
				}
			}
			localeResolver.setLocale(request, response, new Locale(firstLanguage));
			model.addAttribute("nowLocale", firstLanguage);
		}
		
		if(target.equals("/comn/login") || target.equals("")) {
			return "comn/new_login";
			//return "comn/login_2";
		}else {
			//return "comn/loginForAdm";
			return "comn/loginForAdm_2";
		}
		
	}
	/*
	@RequestMapping(value = "/findAdminPw", method = RequestMethod.GET)
	public String findAdminPassword(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
	}
	*/
	@RequestMapping(value = "/registUser", method = RequestMethod.GET)
	public String registUser(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		System.out.println("test");
		
		return "comn/registUser";
	}
	
	@RequestMapping(value = "/find/userInfo", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> findUserPassword(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		
		String newPw = "";
		int idx = 0;
		for (int i = 0; i < 12; i++) {
			idx = (int) (charSet.length * Math.random());
			newPw += charSet[idx];
		}
		System.out.println(newPw);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		UserVO userInfo = new UserVO();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getParameter("userId"));
		
		ManagerVO userInfos = new ManagerVO();
		
		userInfos.setUserId(request.getParameter("userId"));
		userInfos.setUserPw(userInfo.encryptSHA256(newPw));
		
		int cnt = comnService.selectUserIdCheck(params);
		
		if (cnt > 0) {
			userInfo = comnService.selectUserEmail(params.get("userId").toString());
			if (userInfo.getUserEmail().equals("")) {
				result.put("resultCode", "N");
				result.put("resultMsg", "Email 정보가 없습니다. 관리자에게 문의 하세요.");
			} else {
				String userId = params.get("userId").toString();
				userInfo.setSymmetryKey(originKey.getSymmetryKey());
				userInfo.dncryptData();
				String email = userInfo.getUserEmail();
				result.put("resultCode", "S");
				result.put("email", email);
				smtp.sendAuthMail(email, userId, newPw);
				comnService.updateUserPw(userInfos);
			}
			
		} else {
			result.put("resultCode", "F");
			result.put("resultMsg", "존재하지 않는 ID 입니다.");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/emailCheck", method = RequestMethod.GET)
	public String emailConfirm(@RequestParam("authKey") String authKey, @RequestParam("userId") String userId, @RequestParam("tempNum") String userPw, 
			Model model, HttpServletRequest request) throws Exception {
	
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("HHmm");
		String now = format.format(time);
		String sendTime = authKey.substring(authKey.length()-4, authKey.length());
		
		int nowInt = Integer.parseInt(now);
		int sendInt = Integer.parseInt(sendTime);
		
		HashMap<String, Object> returnVal = new HashMap<String, Object>();
		System.out.println(userPw);
		
		if (sendInt < nowInt) {
			returnVal.put("msg", "인증 키가 만료 되었습니다. 다시 시도해 주세요.");
			returnVal.put("status", "F");
			returnVal.put("userId", "");
			returnVal.put("userPw", "");
			model.addAttribute("returnVal", returnVal);
		} else {
			returnVal.put("msg", "");
			returnVal.put("status", "S");
			returnVal.put("userId", userId);
			returnVal.put("userPw", userPw);
			model.addAttribute("returnVal", returnVal);
		}
		return "comn/login_2";
	}
	
	@RequestMapping(value="/sessionOut",method=RequestMethod.GET)
	public String sessionOut(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		HashMap<String,String> sessionInfo = new HashMap<String,String> ();
		sessionInfo.put("loginId", request.getRemoteAddr());
		
		String newIps = comnService.selectSessionInfo(sessionInfo);
		/*
		String prevUrl = "";
		if (request.getHeader("referer").contains("/mngr/")) {
			prevUrl = "ADMIN";
		} else if (request.getHeader("referer").contains("/cstmr/")) {
			prevUrl = "USER";
		} else if (request.getHeader("referer").contains("/comn/") || request.getHeader("referer").contains("/prv/")) {
			prevUrl = "COMN";
		}
		model.addAttribute("prevUrl", prevUrl);
		*/
		model.addAttribute("rtnIp",newIps);
		return "comn/sessionOut";
	}
	
	/*
	@RequestMapping(value="/regist",method=RequestMethod.GET)
	public String customerRegist(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "comn/regist";
	}
	*/
	
	@RequestMapping(value="/signUp",method=RequestMethod.GET)
	public String customerSignUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String browserLanguage = request.getHeader("Accept-Language");
		String firstLanguage = "";

		if (browserLanguage != null && !browserLanguage.isEmpty()) {
			String[] languages = browserLanguage.split(",");
			firstLanguage = languages[0].split(";")[0];
		}

		
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgStations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		
		orgStations = comnService.selectOrgStation();
				
		model.addAttribute("orgStations", orgStations);
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			if (firstLanguage.equals("")) {
				firstLanguage = "en";
			} else {
				if (firstLanguage.startsWith("en")) {
					firstLanguage = "en";
				} else if (firstLanguage.startsWith("zh")) {
					firstLanguage = "zh";
				} else if (firstLanguage.startsWith("ja")) {
					firstLanguage = "ja";
				} else if (firstLanguage.startsWith("ko")) {
					firstLanguage = "ko";
				} else {
					firstLanguage = "ko";
				}
			}
			localeResolver.setLocale(request, response, new Locale(firstLanguage));
			model.addAttribute("nowLocale", firstLanguage);
		}

		//return "comn/sign_up";
		return "comn/signUp";
		//return "comn/NewFile";
	}
	
	@RequestMapping(value="/returnSignUp",method=RequestMethod.GET)
	public String customerReturnSignUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		/*
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgNations = new ArrayList<NationVO>();
		ArrayList<NationVO> orgStations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		orgNations = comnService.selectNationInStation();
		orgStations = comnService.selectOrgStation();

		model.addAttribute("orgStations", orgStations);
		model.addAttribute("nations", nations);
		model.addAttribute("orgNations", orgNations);
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			localeResolver.setLocale(request, response, new Locale("en"));
			model.addAttribute("nowLocale", "en");
		}
		
		return "comn/return_sign_up";
		*/
		
		String browserLanguage = request.getHeader("Accept-Language");
		String firstLanguage = "";

		if (browserLanguage != null && !browserLanguage.isEmpty()) {
			String[] languages = browserLanguage.split(",");
			firstLanguage = languages[0].split(";")[0];
		}

		
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		model.addAttribute("nations", nations);
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			if (firstLanguage.equals("")) {
				firstLanguage = "en";
			} else {
				if (firstLanguage.startsWith("en")) {
					firstLanguage = "en";
				} else if (firstLanguage.startsWith("zh")) {
					firstLanguage = "zh";
				} else if (firstLanguage.startsWith("ja")) {
					firstLanguage = "ja";
				} else if (firstLanguage.startsWith("ko")) {
					firstLanguage = "ko";
				} else {
					firstLanguage = "ko";
				}
			}
			
			localeResolver.setLocale(request, response, new Locale(firstLanguage));
			model.addAttribute("nowLocale", firstLanguage);
		}
		
		return "comn/returnSignUp";
	}

	@RequestMapping(value = "/normalReturnSignUp", method = RequestMethod.GET)
	public String customerNormalReturnSignUp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String browserLanguage = request.getHeader("Accept-Language");
		String firstLanguage = "";

		if (browserLanguage != null && !browserLanguage.isEmpty()) {
			String[] languages = browserLanguage.split(",");
			firstLanguage = languages[0].split(";")[0];
		}
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		model.addAttribute("nations", nations);
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			if (firstLanguage.equals("")) {
				firstLanguage = "en";
			}
			localeResolver.setLocale(request, response, new Locale(firstLanguage));
			model.addAttribute("nowLocale", firstLanguage);
		}
		
		return "comn/normal_return_sign_up";
	}
	
	@RequestMapping(value = "/returnSignUpTest", method = RequestMethod.GET)
	public String customerReturnSignUpTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ArrayList<NationVO> nations = new ArrayList<NationVO>();
		nations = comnService.selectNationCode();
		model.addAttribute("nations", nations);
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			localeResolver.setLocale(request, response, new Locale("en"));
			model.addAttribute("nowLocale", "en");
		}
		
		return "comn/returnSignUp";
	}
	
	@RequestMapping(value="/returnSignUp",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> selectReturnStation(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> returnStations = new ArrayList<HashMap<String, Object>>();
		String orgStation = request.getParameter("orgStation");
		
		try {
			returnStations = comnService.selectReturnStationList(orgStation);
			result.put("returnStations", returnStations);
			result.put("result", "S");
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("result", "F");
		}
		
		
		
		
		return result;
		
	}
	
	@RequestMapping(value="/kind/user",method=RequestMethod.GET)
	public String customerKindChoice(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String browserLanguage = request.getHeader("Accept-Language");
		String firstLanguage = "";

		if (browserLanguage != null && !browserLanguage.isEmpty()) {
			String[] languages = browserLanguage.split(",");
			firstLanguage = languages[0].split(";")[0];
		}
		
		if (request.getParameter("lang") != null) {
			String language = request.getParameter("lang");
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("nowLocale", language);
		} else {
			if (firstLanguage.equals("")) {
				firstLanguage = "en";
			}
			localeResolver.setLocale(request, response, new Locale(firstLanguage));
			model.addAttribute("nowLocale", firstLanguage);
		}
		
		return "comn/userKind";
	}
	
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		return "user/apply/shpng/registOne";
	}

	@RequestMapping(value = "/comn/denied", method = RequestMethod.GET)
	public String deniedPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "comn/denied";
	}
	
	// POD 조회
	@RequestMapping(value = "/comn/deliveryTrack/{hawbNo}", method = RequestMethod.GET)
	public String deliveryTrack1(HttpServletRequest request, HttpServletResponse response, @PathVariable("hawbNo") String hawbNo, Model model) throws Exception {
		String rtnUrl = "";
		String transCode = comnService.selectTransCodeFromHawb(hawbNo);
		if(transCode == null || transCode.equals("")) {
			return "/comn/blpod";
		}
		
		if(transCode.equals("SAGAWA")) {		// SAGAWA
			DeliverTrackVO deliverVO = new DeliverTrackVO();
			deliverVO = comnService.selectDeliverInfo(hawbNo, (String)request.getSession().getAttribute("ORG_STATION"));
			deliverVO.dncryptData();
			
			if(!deliverVO.getCneeName().equals("")) {
				String tempStr = deliverVO.getCneeName().substring(0,2);
				tempStr += "**";
				deliverVO.setCneeName(tempStr);
				tempStr = deliverVO.getNativeCneeAddr() +" "+ deliverVO.getNaticeCneeAddrDetail();
				tempStr = tempStr.substring(0, 9);
				tempStr += tempStr + ".....";
				deliverVO.setNativeCneeAddr(tempStr);
				deliverVO.setNaticeCneeAddrDetail("");
			}
			
			//hawbNo = deliverVO.getOrderNo();
			
			String requestUrl = "https://tracking.sagawa-sgx.com/sgx/trackeng.asp?cat=ref&awb="+deliverVO.getOrderNo()+"&enc=jpn";
			Document doc = Jsoup.connect(requestUrl).get();
			Elements tempHtmlTag = new Elements();
			tempHtmlTag = doc.select("#ShipmentDetail>table>tbody>tr");
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			if(tempHtmlTag.size()!=0) {
				String newHawb= new String();
				newHawb = tempHtmlTag.get(0).child(0).text();
				
				requestUrl = "https://tracking.sagawa-sgx.com/sgx/trackeng.asp?cat=awb&awb="+newHawb+"&enc=jpn";
				doc = Jsoup.connect(requestUrl).get();
				
				tempHtmlTag = doc.select("#ShipmentDetail>table>tbody>tr");
				  
				int tagLength = tempHtmlTag.size();
				
				HashMap<String, Object> tempStatus = null;
				
				for(int i = 0; i < tagLength; i++) {
					tempStatus = new HashMap<String, Object>();
					String dateEvent = tempHtmlTag.get(i).child(0).text().substring(4, 6) + "-" + tempHtmlTag.get(i).child(0).text().substring(6, 8);
					dateEvent += " "+tempHtmlTag.get(i).child(0).text().substring(tempHtmlTag.get(i).child(0).text().length()-4, tempHtmlTag.get(i).child(0).text().length()-2) + ":" + tempHtmlTag.get(i).child(0).text().substring(tempHtmlTag.get(i).child(0).text().length()-2, tempHtmlTag.get(i).child(0).text().length());
					//tempHtmlTag.get(i).child(0).text()
					tempStatus.put("DATE_EVENT", dateEvent);
					tempStatus.put("TRACE_STATUS", tempHtmlTag.get(i).child(1).text());
					tempStatus.put("POSITION", tempHtmlTag.get(i).child(2).text());
					list.add(tempStatus);
				}
			}
			
			model.addAttribute("deliverVO",deliverVO);
			model.addAttribute("hawbNo", hawbNo);
			model.addAttribute("rtnList",list);
			rtnUrl ="comn/delivery/deliverySagawa"; 
			return rtnUrl;
			
		} else if(transCode.equals("ACI")) {		// ACI
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = aciApi.makePodDetatailArray(hawbNo, request);

			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			
			if (blinfo.get("userId").toString().toLowerCase().equals("vision2038") && !blinfo.get("trkNo").toString().equals("")) {
				String trkUrl = "https://tools.usps.com/go/TrackConfirmAction?tRef=fullpage&tLc=2&text28777=&tLabels="+blinfo.get("trkNo").toString()+"%2C&tABt=false";
				model.addAttribute("trkUrl", trkUrl);
			} 
			
			rtnUrl = "/comn/blpod";
			return rtnUrl;	
	
		} else if(transCode.equals("YSL")) {		// YSL
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = ysApi.processYslPod(hawbNo, request);
			/*
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			String rtnJson = ysApi.makeYoungSungPodKR(hawbNo); 
			String rtnJson2 = ysApi.makeYoungSungPodEN(hawbNo);
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = ysApi.makePodDetatailArray(rtnJson, rtnJson2, hawbNo, request);
			//podDetatailArray = ysApi.makePodDetailForArray(rtnJson, hawbNo, request);
			*/
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			blinfo.put("delvNo", podDetatailArray.get(0).get("delvNo"));
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if(transCode.equals("ACI-T86")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			String rtnJson = t86Api.makeT86Pod(hawbNo);
			
			HashMap<String, Object> carriers = new HashMap<String, Object>();
			carriers = comnService.selectMatchCarriers(hawbNo);

			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			podDetailArray = t86Api.makePodDetailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("carriers", carriers);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
			
		} else if (transCode.equals("ACI-US")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			HashMap<String, Object> carriers = new HashMap<String, Object>();
			
			String subTransCode = comnService.selectSubTransCode(hawbNo);
			String rtnJson = "";
			
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			
			if (subTransCode.equals("ACI-T86")) {
				rtnJson = t86Api.makeT86Pod(hawbNo);
				carriers = comnService.selectMatchCarriers(hawbNo);
				podDetailArray = t86Api.makePodDetailArray(rtnJson, hawbNo, request);
			} else if (subTransCode.equals("PARCLL")) {
				String podType = "V";
				podDetailArray = prclApi.makeParcllPod(hawbNo, request, podType);
			}
			
			parameters.put("bl", hawbNo);
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("carriers", carriers);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if (transCode.equals("FB")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			String rtnJson = fastboxApi.makeFastBoxPod(hawbNo,request);
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			podDetailArray = fastboxApi.makePodDetailArrayView(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			parameters.put("transCode", "FB-EMS");
			parameters.put("hawbNo", hawbNo);
			String agencyBl = comnService.selectAgencyBl(parameters);
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("agencyBl", agencyBl);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if (transCode.equals("FB-EMS")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			String rtnJson = fastboxApi.makeFastBoxPod(hawbNo,request);
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			podDetailArray = fastboxApi.makePodDetailArrayView(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			parameters.put("transCode", "FB-EMS");
			parameters.put("hawbNo", hawbNo);
			String agencyBl = comnService.selectAgencyBl(parameters);
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("agencyBl", agencyBl);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if (transCode.equals("EFS")) { // EFS
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			String rtnJson = efsApi.makeEfsPod(hawbNo);
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = efsApi.makePodDetailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
			
		} else if(transCode.equals("OCS")) {		// OCS
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			String rtnJson = ocsApi.makeOCSPod(hawbNo);
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = ocsApi.makePodDetailArray(rtnJson, hawbNo, request);
			HashMap<String,Object> dutyTax = new HashMap<String,Object>();
			//dutyTax = ocsApi.fnMakeOcsDutyTax(hawbNo);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod"; 
			return rtnUrl;
			
		} else if(transCode.equals("ARA")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			parameters.put("bl",hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			podDetatailArray = mgrAramexService.aramexPodPage(hawbNo);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if(transCode.equals("FED")||transCode.equals("FEG")||transCode.equals("FES")) {
			rtnUrl = "https://www.fedex.com/apps/fedextrack/index.html?tracknumbers="+hawbNo+"&cntry_code=kr";
			
			return "redirect:"+rtnUrl;
			
		} else if(transCode.equals("GTS")) {		// GTS
			
			try {
				HashMap<String, Object> blinfo = new HashMap<String, Object>();
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
				podDetatailArray = gtsAPI.makePodDetatailArray(hawbNo);
				//podDetatailArray = sekoApi.makePodDetatailArray(rtnJson, hawbNo, request);
				parameters.put("bl", hawbNo);
				
				blinfo = mgrAramexService.selectPodBlInfo(parameters);
				model.addAttribute("blinfo", blinfo);
				model.addAttribute("podDetatailArray", podDetatailArray);
				rtnUrl = "/comn/blpod";
				return rtnUrl;
			}catch (Exception e) {
				// TODO: handle exception
				rtnUrl = "https://www.aftership.com/track/gbs-broker/"+hawbNo;
				return "redirect:"+rtnUrl;
			}
			
		} else if(transCode.equals("CSE")) {		// CSE
			try {
				HashMap<String, Object> blinfo = new HashMap<String, Object>();
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
				podDetatailArray = cseAPI.makePodDetatailArray(hawbNo);
				//podDetatailArray = sekoApi.makePodDetatailArray(rtnJson, hawbNo, request);
				parameters.put("bl", hawbNo);
				
				blinfo = mgrAramexService.selectPodBlInfo(parameters);
				model.addAttribute("blinfo", blinfo);
				model.addAttribute("podDetatailArray", podDetatailArray);
				rtnUrl = "/comn/blpod";
				return rtnUrl;
			}catch (Exception e) {
				// TODO: handle exception
				rtnUrl = "https://www.cse.ru/mow/track/?numbers="+hawbNo;
				return "redirect:"+rtnUrl;
			}
			
		} else if(transCode.equals("SEK")) {		// SEK
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = sekoApi.makeSekoPod(hawbNo);
			//podDetatailArray = sekoApi.makePodDetatailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
			
		}else if(transCode.equals("ITC")) {
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = itcApi.makeItcPod(hawbNo);
			//podDetatailArray = sekoApi.makePodDetatailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
			
		}else if(transCode.equals("CJ")){
			// CJ
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("bl", hawbNo);

			ArrayList<CJPodVo> cjPodInfoList = new ArrayList<CJPodVo>();
			cjPodInfoList = cjApi.CJPod(hawbNo);
			
			HashMap<String, Object> podAddinfo = new HashMap<String, Object>();
			String acptrNm = "";

			for(int i = 0 ; i < cjPodInfoList.size() ; i++) {
				if (cjPodInfoList.get(i).getCrgSt().equals("91")) {
					acptrNm = cjPodInfoList.get(i).getAcptrNm();				
				}
			}

			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podAddinfo", podAddinfo);
			model.addAttribute("cjPodInfoList", cjPodInfoList);
			
		
			rtnUrl = "/comn/blpodCJ";
			return rtnUrl;
			
		}else if(transCode.equals("EMN")) {		// EMN
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = emsApi.makeEmsPod(hawbNo);
			//podDetatailArray = sekoApi.makePodDetatailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
			
		} else if (transCode.equals("HJ")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			String podType = "V";
			podDetailArray = hjApi.makeHJPod(hawbNo, request, podType, "");
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if (transCode.equals("YT")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("bl", hawbNo);
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();

			if (!hawbNo.startsWith("YT")) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("hawbNo", hawbNo);
				params.put("transCode", "YT");
				hawbNo = comnService.selectAgencyBl(params);
			}

			podDetailArray = yunApi.createTracking(hawbNo, request);

			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else if (transCode.equals("EPT")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("bl", hawbNo);
			ArrayList<HashMap<String, Object>> podDetailArray = new ArrayList<HashMap<String, Object>>();
			podDetailArray = comnService.makeEpostPodDetatailArray(hawbNo, request);

			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetailArray);
			rtnUrl = "/comn/blpod";
			return rtnUrl;
		} else {
			return rtnUrl;
		}
	}
	
	@RequestMapping(value = "/comn/deliveryTrack/{transCode}/{hawbNo}", method = RequestMethod.GET)
	public String deliveryTrack(HttpServletRequest request, HttpServletResponse response, @PathVariable("hawbNo") String hawbNo, @PathVariable("transCode") String transCode, Model model) throws Exception {
		String rtnUrl = "";
		if(transCode.equals("SAW")) {
			DeliverTrackVO deliverVO = new DeliverTrackVO();
			deliverVO = comnService.selectDeliverInfo(hawbNo, (String)request.getSession().getAttribute("ORG_STATION"));
			deliverVO.dncryptData();
			
			if(!deliverVO.getCneeName().equals("")) {
				String tempStr = deliverVO.getCneeName().substring(0,2);
				tempStr += "**";
				deliverVO.setCneeName(tempStr);
				tempStr = deliverVO.getNativeCneeAddr() +" "+ deliverVO.getNaticeCneeAddrDetail();
				tempStr = tempStr.substring(0, 9);
				tempStr += tempStr + ".....";
				deliverVO.setNativeCneeAddr(tempStr);
				deliverVO.setNaticeCneeAddrDetail("");
			}
			
			//hawbNo = deliverVO.getOrderNo();
			
			String requestUrl = "https://tracking.sagawa-sgx.com/sgx/trackeng.asp?cat=ref&awb="+deliverVO.getOrderNo()+"&enc=jpn";
			Document doc = Jsoup.connect(requestUrl).get();
			Elements tempHtmlTag = new Elements();
			tempHtmlTag = doc.select("#ShipmentDetail>table>tbody>tr");
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			if(tempHtmlTag.size()!=0) {
				String newHawb= new String();
				newHawb = tempHtmlTag.get(0).child(0).text();
				
				requestUrl = "https://tracking.sagawa-sgx.com/sgx/trackeng.asp?cat=awb&awb="+newHawb+"&enc=jpn";
				doc = Jsoup.connect(requestUrl).get();
				
				tempHtmlTag = doc.select("#ShipmentDetail>table>tbody>tr");
				  
				int tagLength = tempHtmlTag.size();
				
				HashMap<String, Object> tempStatus = null;
				
				for(int i = 0; i < tagLength; i++) {
					tempStatus = new HashMap<String, Object>();
					String dateEvent = tempHtmlTag.get(i).child(0).text().substring(4, 6) + "-" + tempHtmlTag.get(i).child(0).text().substring(6, 8);
					dateEvent += " "+tempHtmlTag.get(i).child(0).text().substring(tempHtmlTag.get(i).child(0).text().length()-4, tempHtmlTag.get(i).child(0).text().length()-2) + ":" + tempHtmlTag.get(i).child(0).text().substring(tempHtmlTag.get(i).child(0).text().length()-2, tempHtmlTag.get(i).child(0).text().length());
					//tempHtmlTag.get(i).child(0).text()
					tempStatus.put("DATE_EVENT", dateEvent);
					tempStatus.put("TRACE_STATUS", tempHtmlTag.get(i).child(1).text());
					tempStatus.put("POSITION", tempHtmlTag.get(i).child(2).text());
					list.add(tempStatus);
				}
			}
			
			model.addAttribute("deliverVO",deliverVO);
			model.addAttribute("hawbNo", hawbNo);
			model.addAttribute("rtnList",list);
			rtnUrl ="comn/delivery/deliverySagawa"; 
			
		} else if(transCode.equals("YSL")) {
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			String rtnJson = ysApi.makeYoungSungPodKR(hawbNo);
			String rtnJson2 = ysApi.makeYoungSungPodEN(hawbNo);
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = ysApi.makePodDetatailArray(rtnJson, rtnJson2, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			
		} else if(transCode.equals("EFS")) {
			
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			String rtnJson = efsApi.makeEfsPod(hawbNo);
			
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = efsApi.makePodDetailArray(rtnJson, hawbNo, request);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
			
		}  else if(transCode.equals("OCS")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			String rtnJson = ocsApi.makeOCSPod(hawbNo);
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			podDetatailArray = ocsApi.makePodDetailArray(rtnJson, hawbNo, request);
			HashMap<String,Object> dutyTax = new HashMap<String,Object>();
			dutyTax = ocsApi.fnMakeOcsDutyTax(hawbNo);
			parameters.put("bl", hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("dutyTax",dutyTax);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
		} else if(transCode.equals("ARA")) {
			HashMap<String, Object> blinfo = new HashMap<String, Object>();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
			parameters.put("bl",hawbNo);
			
			blinfo = mgrAramexService.selectPodBlInfo(parameters);
			podDetatailArray = mgrAramexService.aramexPodPage(hawbNo);
			model.addAttribute("blinfo", blinfo);
			model.addAttribute("podDetatailArray", podDetatailArray);
			rtnUrl = "/comn/blpod";
		} else if(transCode.equals("FED")||transCode.equals("FEG")||transCode.equals("FES")) {
			rtnUrl = "https://www.fedex.com/apps/fedextrack/index.html?tracknumbers="+hawbNo+"&cntry_code=kr";
			Desktop.getDesktop().browse(new URI(rtnUrl));
			return "";
		}
		
		return rtnUrl;
	}
	
	public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
		HashMap<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }
	
	private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
	
	public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }
	
	
	@RequestMapping(value = "/comn/registUser", method = RequestMethod.POST)
	@ResponseBody
	public String comnRegistUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, ManagerVO userInfo, InvUserInfoVO invUserInfo) throws Exception {
		/* ManagerVO userInfo = new ManagerVO(); */
		boolean memberResult = false;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		userInfo.setWUserId(userInfo.getUserId());
		userInfo.setWUserIp(request.getRemoteAddr());
		userInfo.setWDate(time1);
		memberResult = comnService.insertMemberInfos(userInfo, invUserInfo,request);
		if(memberResult) {
			return "S";
		}else {
			return "F";
		}
		/*
		 * try { if(!userInfo.getUserPw().isEmpty()) {
		 * comnService.insertMemberInfos(userInfo); queryResult = "S"; }else {
		 * queryResult = "F"; } }catch (Exception e) { queryResult = "F"; }
		 */
		 
	}
	
	@RequestMapping(value = "/comn/idCntCheck", method = RequestMethod.POST)
	@ResponseBody
	public String comnIdCntCheck(HttpServletRequest request, HttpServletResponse response, Model model, String nationCodes) throws Exception {
		ManagerVO userInfo = new ManagerVO(); 
		String queryResult = "F";
		int cnt = comnService.selectUserCnt(request.getParameter("userId"));
		if(cnt > 0) {
			return "F";
		}else {
			return "S";
		}
	}
	
	@RequestMapping(value = "/comn/printHawb", method = RequestMethod.GET)
	public void comnPrintHawb(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//comnPrintHawbLegacy(request, response, model);
		try {
			String orderType = "";
			if (request.getParameter("packChk") != null) {
				orderType = "Y";
			} else {
				orderType = "N";
			}

			String userId = request.getSession().getAttribute("USER_ID").toString();

			String printType = request.getParameter("formType");
			
			String [] targetTmp = request.getParameter("targetInfo").split(",");
			
			if("".equals(printType)|| printType == null || "undefined".equals(printType))
				printType = comnService.selectTransComByNno(targetTmp[0]);
			
			ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
			String temp = "";
			

		
			comnPrintHawbLegacy(request, response, model, orderType);
			/*
			if(!request.getSession().getAttribute("USER_ID").equals("bringko")) {
	
				comnPrintHawbLegacy(request, response, model, orderType);
			} else {
				switch (printType) {
					case "ARA": 
						comnService.aramexPdf(request,response, orderNnoList,orderType);
						break;
					default :
						
						comnService.loadPdf(request,response, orderNnoList,printType);
						break;
				}
			}
			*/
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	
	@RequestMapping(value = "/comn/printPacking", method = RequestMethod.GET)
	public void comnPrintPacking(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String[] targetTmp = request.getParameter("targetInfo").split(",");
			ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
			comnService.comnPrintPacking(request, response, model, orderNnoList);
		} catch (Exception e) { 
			logger.error("Exception", e);
		}
	}
	@RequestMapping(value = "/comn/printCommercial", method = RequestMethod.GET)
	public void comnPrintCommercial(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//comnPrintHawbLegacy(request, response, model);
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfo").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		comnService.printCommercialPdf(request,response, orderNnoList,printType);
	}
	
	@RequestMapping(value = "/comn/printCommercialExcel", method = RequestMethod.GET)
	public void comnPrintCommercialExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//comnPrintHawbLegacy(request, response, model);
		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfos").split(",");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		comnService.printCommercialExcel(request,response, orderNnoList,printType);
	}
	
	
	public void comnPrintHawbLegacy(HttpServletRequest request, HttpServletResponse response, Model model, String orderType) throws Exception {

		String printType = request.getParameter("formType");
		String [] targetTmp = request.getParameter("targetInfo").split(",");
		
		if("".equals(printType)|| printType == null || "undefined".equals(printType))
			printType = comnService.selectTransComByNno(targetTmp[0]);
		System.out.println("*************");
		System.out.println(printType);

		ArrayList<FastBoxParameterVO> fbParameters = new ArrayList<FastBoxParameterVO>();
		
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		switch (printType) {
			case "ARA": 
				comnService.aramexPdf(request,response, orderNnoList,orderType);
				break;
			case "FED":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "EMN":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "EMS":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "USP":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "UPS":
				comnService.loadPdf(request, response, orderNnoList, printType);
				break;
			case "DHL":
				comnService.loadPdf(request, response, orderNnoList, printType);
				break;
			case "SEK":
				comnService.savedPdfSek(request,response, orderNnoList,printType);
				break;
			case "FEG":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "FES":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "ITC":
				comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "YSL":
				comnService.yslPdfLegacy(request,response, orderNnoList,printType,orderType);
				//yongsung.createLabel(request,response, orderNnoList,printType,orderType);
				break;
			case "ACI":
				//comnService.printPdfLegacy(request,response, orderNnoList,printType,orderType);
				comnService.createLabel(request, response, orderNnoList, printType, orderType);
				break;
			case "GTS":
				//comnService.savePdf(request, orderNnoList.get(0), printType); 
				comnService.printGTSPdfLegacy(request,response, orderNnoList,printType);
				break;
			case "YES":
				comnService.printPdfLegacy(request,response, orderNnoList,printType,orderType);
				break;
			case "SAGAWA": 
				comnService.printPdfLegacy(request,response, orderNnoList,printType,orderType);
				break;
			case "OCS": 
				comnService.ocsPdfLegacy(request,response, orderNnoList,printType);
				break;
			case "EPT": 
				//comnService.krPostPdfLegacy(request,response, orderNnoList,printType);
				//comnService.krPostZPLTest(request,response, orderNnoList,printType);
				comnService.printEpostPdf(request,response,orderNnoList);
				break;
			case "EFS": 
				comnService.efsPdfLegacy(request,response, orderNnoList,printType);
				break;
			case "ETCS":
				comnService.etcsPdfLegacy(request,response, orderNnoList);
				break;
			case "CJ":
				/*
				ArrayList<CJParameterVo> cJParameters = new ArrayList<CJParameterVo>();
				for(int i = 0 ; i < orderNnoList.size();i++) {
					CJParameterVo parameter = new CJParameterVo();
					parameter.setNno(orderNnoList.get(i));
					cJParameters.add(parameter);
				}
				cjApi.cjPrint(request, response, cJParameters, orderType);
				*/
				cjHandler.createCJLabel(request, response, orderNnoList, orderType);
				break;
			case "ACI-T86":
				comnService.loadPdf(request, response, orderNnoList, printType);
				break;
			case "FB":
				fbParameters = new ArrayList<FastBoxParameterVO>();
				for (int i = 0; i < orderNnoList.size(); i++) {
					FastBoxParameterVO parameter = new FastBoxParameterVO();
					parameter.setNno(orderNnoList.get(i));
					fbParameters.add(parameter);
				}
				fbApi.pdfFastBox(request, response, fbParameters, orderType);
				break;
			case "FB-EMS":
				fbParameters = new ArrayList<FastBoxParameterVO>();
				for (int i = 0; i < orderNnoList.size(); i++) {
					FastBoxParameterVO parameter = new FastBoxParameterVO();
					parameter.setNno(orderNnoList.get(i));
					fbParameters.add(parameter);
				}
				fbApi.pdfFastBox(request, response, fbParameters, orderType);
				break;
			case "HJ":
				hjApi.hjPrint(request, response, orderNnoList, orderType);
				//comnService.loadPdf(request,response, orderNnoList,printType);
				break;
			case "PARCLL":
				comnService.loadPdf(request, response, orderNnoList, printType);
				break;
			case "ACI-US":
				comnService.loadPdf(request, response, orderNnoList, printType);
				break;
			case "YT":
				//comnService.loadPdf(request, response, orderNnoList, printType);
				comnService.createLabel(request, response, orderNnoList, printType, orderType);
				break;
			case "VNP":
				commUtils.getLabelFromBucket(request, response, orderNnoList);
				//comnService.getLabelFromBucket(request, response, orderNnoList);
				break;
			default :
				comnService.printPdfLegacy(request,response, orderNnoList,printType,orderType);
				break;
		}
	
	}
	
	
	/**
	 * Page : User 마이 페이지 - 회원정보 - 택배회사 리스트 표출
	 * Method : GET
	 */
	@RequestMapping(value = "/comn/myPage/transCom", method = RequestMethod.GET)
	public String userTransComList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String,ArrayList<ZoneVO>> trkMap = new HashMap<String,ArrayList<ZoneVO>>();
		HashMap<String,Object> TransMaps = new HashMap<String,Object>(); 
		TransMaps.put("orgs", request.getParameterValues("orgNation"));
		TransMaps.put("dstns", request.getParameterValues("dstnNation"));
		trkMap = comnService.makeTransMap(TransMaps);
		model.addAttribute("trkMap", trkMap);
		return "comn/transCom/trackComList";
	}

	@RequestMapping(value = "/comn/holdBlJson", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public HashMap<String, Object> holdBlJson(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String,Object> pramaterInfo = new HashMap<String,Object>();
		pramaterInfo.put("userId",request.getParameter("userId"));
		pramaterInfo.put("hawbNo",request.getParameter("hawbNo"));
		pramaterInfo.put("holdYn",request.getParameter("holdYn"));
		pramaterInfo.put("remark",request.getParameter("remark"));
		pramaterInfo.put("wUserId",request.getParameter("wUserId"));
		pramaterInfo.put("wUserIp",request.getParameter("wUserIp"));

		HashMap<String,Object> rst = new HashMap<String,Object>();
		rst = comnService.execSpHoldBl(pramaterInfo);
		
		return rst;
	}
	
	@RequestMapping(value = "/comn/downBlExcel", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public void downBlExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		try {
			
		
			HashMap<String,Object> excelParameter = new HashMap<String,Object>();
			String startDate ="";
			String endDate="";
			excelParameter.put("userId", request.getSession().getAttribute("USER_ID"));
			//excelParameter.put("userId", "trexi1");
			//excelParameter.put("userId", "happychan");
			//excelParameter.put("orderType", "NOMAL");
			excelParameter.put("orderType", request.getParameter("orderType"));
			excelParameter.put("idx", request.getParameter("idx"));
			
			if(!request.getParameter("startDate").isEmpty()) {
				startDate = request.getParameter("startDate").replaceAll("-", "");
				excelParameter.put("startDate", startDate);
			}else {
				excelParameter.put("startDate", startDate);
			}
			
			if(!request.getParameter("endDate").isEmpty()) {
				endDate = request.getParameter("endDate").replaceAll("-", "");
				excelParameter.put("endDate", endDate);
			}else {
				excelParameter.put("endDate", endDate);
			}
			
			comnService.downExcelData(excelParameter, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/comn/menual", method= RequestMethod.GET, produces = "application/json; charset=utf8")
	public void menaulDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	    String filename="WMS 플렛폼 사용 설명서.pdf";
	    
	    try{
            file = new File(realFilePath+"pdf/", filename);
            in = new FileInputStream(file);
        }catch(FileNotFoundException fe){
            skip = true;
        }
		
	    client = request.getHeader("User-Agent");
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
	}
	
	@RequestMapping(value = "/comn/terms", method= RequestMethod.GET, produces = "application/json; charset=utf8")
	public void termsDown(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	    String filename="ACI Worldwide Express 국제운송약관.pdf";
	    
	    try{
            file = new File(realFilePath+"pdf/", filename);
            in = new FileInputStream(file);
        }catch(FileNotFoundException fe){
            skip = true;
        }
		
	    client = request.getHeader("User-Agent");
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
	}
	
	public void comnBlApply(HttpServletRequest request, String orderNno, String transCode) throws Exception {
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getSession().getAttribute("USER_IP").toString();
		String blType = request.getParameter("blType");
		comnService.comnBlApply(orderNno, transCode, userId, userIp,blType);
	}
	
	@RequestMapping(value = "/comn/returnCsMemo", method = RequestMethod.GET)
	public String comnReturnCsMemo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String nno = request.getParameter("nno");
		String role = (String) request.getSession().getAttribute("ROLE");
		String adminYn = "";
		if (role.equals("ADMIN")) {
			adminYn = "Y";
		} else {
			adminYn = "N";
		}
		parameters.put("nno", nno);
		parameters.put("wUserId", (String) request.getSession().getAttribute("USER_ID"));
		parameters.put("wUserIp", request.getRemoteAddr());
		
		ArrayList<HashMap<String, Object>> memoList = new ArrayList<HashMap<String, Object>>();
		memoList = comnService.selectReturnMemoList(parameters);

		for (int idx = 0; idx < memoList.size(); idx++) {
			if (memoList.get(idx).get("readYn").equals("N") && !memoList.get(idx).get("adminYn").equals(adminYn)) {
				parameters.put("idx", memoList.get(idx).get("idx"));
				comnService.updateReturnCsReadYn(parameters);
			}
		}

		model.addAttribute("role", role);
		model.addAttribute("nno", nno);
		model.addAttribute("memoList", memoList);
		return "comn/returnCsMemo";
	}
	
	@RequestMapping(value = "/comn/returnCsMemo", method = RequestMethod.POST)
	public void comnReturnCsMemoPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		boolean adminYn = false;
		
		try {
			parameters.put("nno", request.getParameter("nno"));
			parameters.put("content", request.getParameter("content"));
			parameters.put("userId", (String) request.getSession().getAttribute("USER_ID"));
			parameters.put("wUserIp", request.getRemoteAddr());
			if (request.getSession().getAttribute("ROLE").equals("ADMIN")) {
				parameters.put("adminYn", "Y");
				adminYn = true;
			} else {
				parameters.put("adminYn", "N");
			}
			parameters.put("readYn", "N");
			
			comnService.insertReturnCsMemo(parameters);
			
			if (adminYn) {
				comnService.sendEmailToSeller(parameters);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/api/aesTest", method = RequestMethod.GET)
	public String apiAESTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "comn/aesTest";
	}
	
	@RequestMapping(value = "/api/aesTestResult", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> apiAESTestResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		String str = request.getParameter("str");
		String key = request.getParameter("key");
		String type = request.getParameter("type");
		
		String result = "";
		if (type.equals("enc")) {
			result = AES256Cipher.AES_Encode(str, key);
		} else {
			result = AES256Cipher.AES_Decode(str, key);
		}
		
		rst.put("str", result);
		
		return rst;
	}
	
	
	
//	@RequestMapping(value="/testPages",method=RequestMethod.GET)
//	public String testPages(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
//		return "comn/testPage";
//	}
//	
//	@RequestMapping(value="/uploadEncryptTest",method=RequestMethod.POST)
//	public void uploadEncryptTest(HttpServletRequest request, MultipartHttpServletRequest multi, HttpServletResponse response, Model model, MultipartFile file) throws Exception{
//		//file = request.getFiles("files").get(0);
//		file = multi.getFiles("files").get(0);
//		try {
//			AES256Cipher aesTest = new AES256Cipher();
//			//KEY = 07412cV8xX8MlRtA9b5lKTy2zXuM4CmO
//			FileOutputStream fos = new FileOutputStream(realFilePath+"test3.jpg");
//			InputStream is = file.getInputStream();
//			int readCount = 0;
//			byte[] buffer = new byte[1024];
//			while ((readCount = is.read(buffer)) != -1) {
//				fos.write(buffer, 0, readCount);
//			}
//			is.close();
//			fos.close();
//			
//			aesTest.aes256Encode(realFilePath, "test3.jpg", "test4.jpg", "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO");
//			
//			
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	@RequestMapping(value="/downloadDecryptTest",method=RequestMethod.POST)
//	public void downloadDecryptTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
//		InputStream in = null;
//	    OutputStream os = null;
//	    String client = "";
//	    AES256Cipher aesTest = new AES256Cipher();
//        aesTest.aes256Decode(realFilePath, "test4.jpg", "test5_Decrypt.jpg", "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO");
//        
//	}
	
	
	@RequestMapping(value = "/comn/return/printEpostBarcode", method = RequestMethod.POST)
	public void mngrReturnEpostBarcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] targets = request.getParameter("targets").split(",");
		ArrayList<String> nnoList = new ArrayList<String>(Arrays.asList(targets));
		comnService.printBarcodePdf(request, response, nnoList);
	}
	
	@RequestMapping(value = "/comn/createPdfZipFile", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> createPdfZipFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		
		try {
			
			String fileName = ""; 
			fileName = comnService.createZipFiles(request, response);
			
			rst.put("status", "S");
			rst.put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
			rst.put("status", "F");
		}
		
		return rst;
	}
	
	
	@RequestMapping(value = "/comn/downloadPdfZipFile", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadPdfZipFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		try {
			String filePath = request.getParameter("fileName");
			Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {

                String fileName = path.getFileName().toString();

                ResponseEntity<Resource> responseEntity = ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource);

                return responseEntity;

            } else {
                return ResponseEntity.notFound().build();
            }
		} catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.badRequest().build();
        }

	}
	
	
	@RequestMapping(value = "/comn/order/excelDownCheck", method = RequestMethod.GET)
	public ResponseEntity<byte[]> comnOrderExcelDownCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			
			byte[] excelBytes = comnService.createOrderListExcelCheck(request, response);
					
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			headers.setContentLength(excelBytes.length);
			
			return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/comn/order/excelDownByDate", method = RequestMethod.GET)
	public ResponseEntity<byte[]> comnOrderExcelDownByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			byte[] excelBytes = comnService.createOrderListExcelCheck(request, response);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			headers.setContentLength(excelBytes.length);
			
			return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/comn/tracking", method = RequestMethod.GET)
	public String commonTrackingView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<>();
		String pageUrl = "comn/tracking";
		String hawbNo = request.getParameter("hawbNo");
		String userRole = (String) request.getSession().getAttribute("ROLE");
		String userId = (String) request.getSession().getAttribute("USER_ID");
		
		sqlParams.put("hawbNo", hawbNo);
		sqlParams.put("userRole", userRole);
		sqlParams.put("userId", userId);
		
		ArrayList<Tracking> fullTracking = new ArrayList<Tracking>();
		ArrayList<Tracking> aciTracking = logisticsService.getAciTrackingList(sqlParams);
		
		for (int i = 0; i < aciTracking.size(); i++) {
			fullTracking.add(aciTracking.get(i));
		}
		
		if (!"-200".equals(fullTracking.get(0).getStatusCode())) {
			String transCode = fullTracking.get(0).getTransCode().toUpperCase();
			
			ArrayList<Tracking> logisticsTracking = new ArrayList<Tracking>();
			
			switch (transCode) {
			case "CJ":
				pageUrl = "comn/tracking_cj";
				CJInfo cjInfo = cjHandler.checkCJTokenInfo();
				logisticsTracking = cjHandler.requestOneGoodsTracking(cjInfo.getTokenNo(), hawbNo);
				break;
			case "VNP":
				logisticsTracking = vnpHandler.getOrderTrackingDetails(hawbNo);
				break;
			case "YSL":
				String matchNo = aciTracking.get(0).getMatchNo();
				logisticsTracking = ysHandler.getTracking(matchNo);
				break;
			case "FB":
				logisticsTracking = fbHandler.getTracking(hawbNo);
				break;
			case "FB-EMS":
				logisticsTracking = fbHandler.getTracking(hawbNo);
				break;
			default:
				break;
			}
			
			if (logisticsTracking != null) {
				for (int i = 0; i < logisticsTracking.size(); i++) {
					fullTracking.add(logisticsTracking.get(i));
				}
			}
		}

		model.addAttribute("hawbNo", hawbNo);
		model.addAttribute("trkNo", fullTracking.get(0).getTrkNo());
		model.addAttribute("orderNo", fullTracking.get(0).getOrderNo());
		model.addAttribute("cneeName", fullTracking.get(0).getCneeName());
		Collections.reverse(fullTracking);
		model.addAttribute("fullTracking", fullTracking);
		
		return pageUrl;
	}

}
