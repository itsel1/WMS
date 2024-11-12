package com.example.temp.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.Export;
import com.example.temp.api.aci.vo.ExportDeclare;
import com.example.temp.api.ecommerce.dto.Item;
import com.example.temp.api.ecommerce.dto.Order;
import com.example.temp.api.ecommerce.dto.Shopee;
import com.example.temp.api.ecommerce.dto.Shopify;

@Repository
@Mapper
public interface MemberECommerceMapper {

	int selectShopifyInfoCount(String userId);
	
	Shopify selectShopifyInfo(HashMap<String, Object> sqlParams);
	
	int selectExistOrderCount(HashMap<String, Object> sqlParams);

	int selectShopeeInfoCount(String userId);

	ArrayList<Shopee> selectShopeeInfo(HashMap<String, Object> sqlParams);
	
	Shopee selectShopeeAppInfo(HashMap<String, Object> sqlParams);

	Shopee selectShopeeInfoOne(HashMap<String, Object> sqlParams);

	void execShopifyInfo(Shopify shopifyDto);

	void updateShopeeInfoUseYn(Shopee shopeeDto);

	void execShopeeInfo(Shopee shopeeDto);
	
	void updateShopeeRefreshToken(Shopee shopeeDto);
	
	void execShopeeAppInfo(HashMap<String, Object> sqlParams);

	void insertTmpOrder(Order order);

	void insertTmpItem(Item item);

	void insertShopifyFulfillment(Order order);

	void insertExportDeclareInfo(Export export);

	Item selectTakeinItem(HashMap<String, Object> sqlParams);

	void deleteShopeeInfo(Shopee shopeeDto);

	void insertShopeeInfo(Shopee shopeeDto);



}
