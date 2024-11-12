package com.example.temp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.temp.common.vo.MemberVO;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler  {
    
    @Autowired
    SecurityService securityService;
    
    
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
        String id = "";
        String msg = "";
        MemberVO member = new MemberVO();
        try {
            id = exception.getMessage();
            String target = (String)request.getRequestURI();
    		if(request.getHeader("referer").split("/")[3].equals("comn")) {
    			member = securityService.getSelectMeberInfo(id);
    			response.sendRedirect("/comn/login?msg=a");
    		}else {
    			member = securityService.getSelectAdminInfo(id);
    			response.sendRedirect("/prv/login?msg=a");
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
