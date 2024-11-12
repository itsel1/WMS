package com.example.temp.api.shop;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ExpLicenceVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;

@Repository
@Mapper
public interface ShopMapper {

	public HashMap<String, Object> selectShopifyApiInfo(HashMap<String, Object> params);
	public UserVO selectShipperInfoByUserId(HashMap<String, Object> params);
	public int selectDuplicatedOrderCnt(HashMap<String, Object> params);
	public void insertUserShopOrderItemTemp(UserOrderItemVO shopifyItem);
	public void insertUserShopOrderListTemp(UserOrderListVO shopifyOrder);
	public void insertShopifyFulfillment(HashMap<String, Object> params);
	public void deleteShopifyFulfillment(HashMap<String, Object> params);
	public void deleteTmpOrder(String newNno);
	public HashMap<String, Object> selectShopifyFulfillInfo(HashMap<String, Object> orderInfo);
	public void updateShopifyFulfillment(HashMap<String, Object> orderInfo);
	public void insertExpLicenceInfo(ExpLicenceVO licence);
	public void insertShopeeAccessToken(HashMap<String, Object> params);
	public void updateShopeeAccessToken(HashMap<String, Object> params);
	public void putShopAccessToken(HashMap<String, Object> sqlParams);
	public void putMerchantAccessToken(HashMap<String, Object> sqlParams);
	

}
