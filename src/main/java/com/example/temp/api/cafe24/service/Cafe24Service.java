package com.example.temp.api.cafe24.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.temp.api.cafe24.vo.Cafe24OrderParameter;
import com.example.temp.api.cafe24.vo.Cafe24ShopInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24SupplierVo;
import com.example.temp.api.cafe24.vo.Cafe24UserInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24UserTokenInfo;

@Service
public interface Cafe24Service {
	
	
	public void insertUserCafe24Info(Cafe24UserInfoVo cafe24UserInfo) throws Exception;

	public Cafe24UserInfoVo selectUserCafe24Info(Cafe24UserInfoVo cafe24UserInfo);

	public int insertUserCafe24Token(Cafe24UserTokenInfo cafe24Token);
	
	public Cafe24UserTokenInfo userTokenChk(Cafe24UserTokenInfo userToken) throws Exception; 

	public ArrayList<Cafe24UserTokenInfo> selectUserCafe24TokenList(Cafe24UserTokenInfo cafe24TokenInfo);

	public ArrayList<Cafe24UserTokenInfo> selectAdminCafe24TokenList(Cafe24UserTokenInfo prameterInfo);

	public Cafe24UserTokenInfo selectUserCafe24Token(Cafe24UserInfoVo parameterInfo);

	public int selectOrderChk(Cafe24OrderParameter cafe24OrderParameter);

	public ArrayList<Cafe24ShopInfoVo> userCafe24shops(Cafe24UserTokenInfo cafe24TokenInfo);

	public JSONObject callCafe24API(String accessToken, String requestUrl);

	public int getOrdersCnt(Cafe24OrderParameter cafe24OrderParameter);

	public void getOrders(Cafe24OrderParameter cafe24OrderParameter);

	public String getCafe24Datas(Cafe24OrderParameter cafe24OrderParameter) throws Exception;

	public Cafe24SupplierVo getSupplie(Cafe24OrderParameter cafe24OrderParameter) throws Exception;

	public void mergeSupplier(HashMap<String, Object> parameterInfo) throws Exception ;
	 
}

