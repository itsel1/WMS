package com.example.temp.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.temp.common.vo.MemberVO;

@Component
public class AuthAdminProvider implements AuthenticationProvider{

	@Autowired
    SecurityService securityService;
	
	@Autowired
	SHA256Encryption sha256Enryption;
 
    //로그인 버튼을 누를 경우
 
    //첫번째 실행
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        return authenticate(id, password);
    }
    
    //두번쨰 실행
    private Authentication authenticate(String id, String password) throws AuthenticationException{
        
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        
        MemberVO member = new MemberVO();
        
        if(id.split("==d=")[0].equals("prv")) {
       	 	id = id.split("==d=")[1];
            member = (MemberVO)securityService.loadAdminByUsername(id);
            password = sha256Enryption.encode(password);
		}else {
			member = null;
		}
    
        if ( member == null ){
            throw new UsernameNotFoundException(id);
        }else if(member != null && !member.getPassword().equals(password) ) {
            throw new BadCredentialsException(id);
        } 
    
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
        member.setPassword("");
        return new MyAuthentication(id, password, grantedAuthorityList, member);
 
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
 
}
