package com.example.temp.security;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.temp.common.vo.MemberVO;

public interface SecurityService extends UserDetailsService {
    // 시큐리티 사용자 인증
    public UserDetails loadUserByUsername(String id);
    public UserDetails loadAdminByUsername(String id);
    //회원가입
    int setInsertMember(MemberVO member)throws Exception;
    
    public MemberVO getSelectMeberInfo(String id);
    public MemberVO getSelectAdminInfo(String id);
	public void insertSessionInfo(HashMap<String, String> sessionInfo);
	
	
}
