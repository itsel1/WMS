package com.example.temp.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.temp.common.vo.MemberVO;

@Service
public class UserServiceImpl implements SecurityService {
    
    @Autowired
    SecurityMapper loingMapper;
    
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    	
  
         MemberVO member = loingMapper.getSelectMeberInfo(id);
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
         System.out.println("");
         
         if(member != null) {
             authorities.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
             member.setAuthorities(authorities);
         }
         return member;
    }
    
    public UserDetails loadAdminByUsername(String id) throws UsernameNotFoundException {
         MemberVO member = loingMapper.getSelectMeberInfoAdmin(id);
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
         if(member != null) {
             authorities.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
             member.setAuthorities(authorities);
         }
         return member;
    }
    
    @Override
    public int setInsertMember(MemberVO member) throws Exception{
    	
		/* return loingMapper.setInsertMember(member); */
    	return 1;
    }

	@Override
	public MemberVO getSelectMeberInfo(String id) {
		return loingMapper.getSelectMeberInfo(id);
	}
	
	@Override
	public MemberVO getSelectAdminInfo(String id) {
		return loingMapper.getSelectMeberInfoAdmin(id);
	}

	@Override
	public void insertSessionInfo(HashMap<String, String> sessionInfo) {
		// TODO Auto- generated method stub
		loingMapper.insertSessionInfo(sessionInfo);
	}
}
