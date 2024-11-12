package com.example.temp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import com.example.temp.common.vo.MemberVO;

public class CustomSessionAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

	public CustomSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		MemberVO member = (MemberVO) authentication.getPrincipal();
		
		String userId = member.getUsername().toLowerCase();
		
		if (userId.equals("itsel2") || userId.equals("happychan") || userId.equals("dralthea")) {
			setMaximumSessions(10);
		} else {
			setMaximumSessions(1);
		}
		super.onAuthentication(authentication, request, response);
	}
	
}
