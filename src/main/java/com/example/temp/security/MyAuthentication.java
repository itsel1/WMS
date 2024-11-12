package com.example.temp.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.temp.common.vo.MemberVO;

import lombok.Data;

@Data
public class MyAuthentication extends UsernamePasswordAuthenticationToken{
    private static final long serialVersionUID = 1L;
    
    MemberVO member;
    
    public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorityList, MemberVO member) {
        super(member, password, grantedAuthorityList);
        this.member = member;
    }
}
