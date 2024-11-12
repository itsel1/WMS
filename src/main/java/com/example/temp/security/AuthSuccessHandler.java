package com.example.temp.security;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.temp.common.vo.MemberVO;
import com.example.temp.member.vo.LoginUserVO;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    
    @Autowired
    SecurityService securityService;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
    	String returnRole = "";
    	MemberVO member = (MemberVO)authentication.getPrincipal();
    	if(!request.getHeader("referer").contains("prv/login")) {
    		if(member.getRole().equals("ADMIN")) {
    			returnRole = "/prv/login";
    			super.setDefaultTargetUrl(returnRole);
                super.onAuthenticationSuccess(request, response, authentication);
    		}
    	}else {
    		if(!member.getRole().equals("ADMIN")) {
    			returnRole = "/comn/login";
    			super.setDefaultTargetUrl(returnRole);
                super.onAuthenticationSuccess(request, response, authentication);
    		}
    	}
    	
    	if(member.getAprvYn().equals("N")) {
    		returnRole="/comn/login?msg=b";
    		super.setDefaultTargetUrl(returnRole);
            super.onAuthenticationSuccess(request, response, authentication);
    	}
    	
    	HashMap<String, String> sessionInfo = new HashMap<String, String>();
        
    	if(member.getRole().equals("ADMIN")) {
    		returnRole = "/mngr/home";
    		//returnRole = "/mngr/acnt/userList";
    		sessionInfo.put("adminYn","Y");
    	}else if(member.getRole().equals("USER")) {
    		//returnRole = "/cstmr/home";
    		returnRole = "/cstmr/apply/shpngAgncy";
    		sessionInfo.put("adminYn","N");
    	}else if(member.getRole().equals("RETURN")) {
    		returnRole = "/cstmr/return/orderList";
    		sessionInfo.put("adminYn","N");
    	}
        HttpSession session = request.getSession();
        session.setAttribute("USER_ID", member.getUsername());
        session.setAttribute("USER_IP", request.getRemoteAddr());
        session.setAttribute("ORG_STATION", member.getOrgStation());
        session.setAttribute("ROLE", member.getRole());
        sessionInfo.put("loginId",member.getUsername());
        sessionInfo.put("sessionKey", request.getSession().getId());
        sessionInfo.put("accessIp", request.getRemoteAddr());
        securityService.insertSessionInfo(sessionInfo);
        
        
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
            	super.setDefaultTargetUrl(returnRole);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
        	super.setDefaultTargetUrl(returnRole);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    	
    }
    
    
}
