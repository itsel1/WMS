package com.example.temp.manager.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.AdminVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.YslExcelVO;

@Repository
@Mapper
public interface ManagerAdminMapper {

	public int getTotalUserCnt(HashMap<String, Object> map);
	public ArrayList<AdminVO> getUserData(HashMap<String, Object> map);
	public int selectUserCnt(String adminId);
	public void insertAdminInfos(AdminVO adminVO);
	public void deleteUserInfo(String string);
	public AdminVO getSelectAdminInfo(String adminId);
	public void updateAdminInfo(AdminVO adminVO);
	public int selectAdminListCnt(HashMap<String, Object> params);
	public ArrayList<AdminVO> selectAdminList(HashMap<String, Object> params);
	public void resetAdminPw(AdminVO adminVO);
	public ArrayList<YslExcelVO> selectYslExcelList(YslExcelVO yslExcelVO);
	public ArrayList<SendVO> selectStockOutList(SendVO sendVO);
	public int selectFastboxUserListCnt(HashMap<String, Object> params);
	public ArrayList<FastboxUserInfoVO> selectFastboxUserList(HashMap<String, Object> params);
	public ArrayList<HashMap<String, Object>> selectUserList();
	public FastboxUserInfoVO selectUserInfo(String userId);
	public void insertFastboxUserInfo(FastboxUserInfoVO userInfo);
	public FastboxUserInfoVO selectFastboxUserInfo(HashMap<String, Object> params);
	public void updateFastboxUserInfo(FastboxUserInfoVO userInfo);

	
	
}
