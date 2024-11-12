package com.example.temp.common.vo;

import java.security.MessageDigest;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MemberVO implements UserDetails{
	private int idx;
	private String role;
	private String etprYn;
	private String aprvYn;
	private String orgStation;
	
	/*기본 상속 변수*/	
	private Collection<? extends GrantedAuthority> authorities;
	private boolean isEnabled = true;
	private String username;
	private String password;
	private String Ip;
	private boolean isCredentialsNonExpired = true;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String encryptSHA256(String str) {
		String sha="";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for(int i =0; i< byteData.length; i++) {
				sb.append(Integer.toString((byteData[i]&0xff)+ 0x100, 16).substring(1));
			}
			sha = sb.toString();
		}catch (Exception e) {
			System.out.println("encrypt Password Error!!");
			sha = null;
		}
		return sha;
	}
	
}
