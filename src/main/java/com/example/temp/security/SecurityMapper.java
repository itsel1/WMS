package com.example.temp.security;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.temp.common.vo.MemberVO;

@Mapper
public interface SecurityMapper {

	public MemberVO getSelectMeberInfo(String id);

	public MemberVO getSelectMeberInfoAdmin(String id);

	public void insertSessionInfo(HashMap<String, String> sessionInfo);


	/* int setInsertMember(MemberVO member); */

}
