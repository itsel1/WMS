package com.example.temp.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.stereotype.Service;

import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.ecommerce.dto.Shopee;
import com.example.temp.api.ecommerce.dto.Shopify;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Service
public interface MemberECommerceService {
	
	Shopify selectShopifyInfo(HashMap<String, Object> sqlParams);
	int selectShopeeInfoCount(String userId);
	Shopee selectShopeeAppInfo(HashMap<String, Object> sqlParams);
	ArrayList<Shopee> selectShopeeInfo(HashMap<String, Object> sqlParams);
	Shopee selectShopeeInfoOne(HashMap<String, Object> sqlParams);
	void execShopifyInfo(Shopify shopifyDto);
	void updateShopeeInfoUseYn(Shopee shopeeDto);
	void execShopeeInfo(Shopee shopeeDto) throws Exception;
	void execShopeeAppInfo(HashMap<String, Object> sqlParams);
	HashMap<String, Object> getEcommerceOrders(HttpServletRequest request);
	void insertShopifyOrderDetail(Order order) throws Exception;
	void insertShopeeOrderDetail(Order order) throws Exception;
}
