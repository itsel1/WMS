package com.example.temp.api.cafe24.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.cafe24.vo.Cafe24OrderParameter;
import com.example.temp.api.cafe24.vo.Cafe24OrdersItemsVo;
import com.example.temp.api.cafe24.vo.Cafe24UserInfoVo;
import com.example.temp.api.cafe24.vo.Cafe24UserTokenInfo;

@Repository
@Mapper
public interface Cafe24Mapper {

	void insertCafe24UserInfo(Cafe24UserInfoVo cafe24UserInfo);

	Cafe24UserInfoVo selectUserCafe24Info(Cafe24UserInfoVo cafe24UserInfo);

	int insertUserCafe24Token(Cafe24UserTokenInfo cafe24Token);


	ArrayList<Cafe24UserTokenInfo> selectAdminCafe24TokenList(Cafe24UserTokenInfo cafe24TokenInfo);

	ArrayList<Cafe24UserTokenInfo> selectUserCafe24TokenList(Cafe24UserTokenInfo cafe24TokenInfo);

	Cafe24UserTokenInfo selectUserCafe24Token(Cafe24UserInfoVo parameterInfo);	

	int selectOrderChk(Cafe24OrderParameter cafe24OrderParameter);

	void insertCafe24OrderInfo(Cafe24OrderParameter cafe24OrderParameter);

	void insertcafe24OrderItemSuppliers(Cafe24OrdersItemsVo cafe24OrderItem);

	 
}
