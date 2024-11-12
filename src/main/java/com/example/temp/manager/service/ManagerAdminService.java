package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.example.temp.manager.vo.AdminVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.YslExcelVO;

@Service
public interface ManagerAdminService {

	public int getTotalUserCnt(HashMap<String, Object> map);
	public ArrayList<AdminVO> getUserData(HashMap<String, Object> map);
	public int selectUserCnt(String adminId);		// id 중복여부 체크
	public void insertAdminInfos(AdminVO adminVO);
	public void deleteUserInfo(String targetParm);
	public AdminVO selectAdminInfo(String adminId);
	public void updateAdminInfo(AdminVO adminVO, HttpServletRequest request);
	public int selectAdminListCnt(HashMap<String, Object> params);
	public ArrayList<AdminVO> selectAdminList(HashMap<String, Object> params);
	public void resetAdminPw(AdminVO adminVO);
	public void yslExcelDown(YslExcelVO yslExcelVO, HttpServletRequest request, HttpServletResponse response);
	public int selectFastboxUserListCnt(HashMap<String, Object> params) throws Exception;
	public ArrayList<FastboxUserInfoVO> selectFastboxUserList(HashMap<String, Object> params) throws Exception;
	public ArrayList<HashMap<String, Object>> selectUserList() throws Exception;
	public FastboxUserInfoVO selectUserInfo(String userId) throws Exception;
	public void insertFastboxUserInfo(FastboxUserInfoVO userInfo) throws Exception;
	public FastboxUserInfoVO selectFastboxUserInfo(HashMap<String, Object> params) throws Exception;
	public void updateFastboxUserInfo(FastboxUserInfoVO userInfo) throws Exception;
	
	
	
	
}
