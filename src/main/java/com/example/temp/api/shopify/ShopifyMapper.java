package com.example.temp.api.shopify;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiAdminVO;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.member.vo.BlApplyVO;

@Repository
@Mapper
public interface ShopifyMapper {

	public ArrayList<ApiShopifyInfoVO> selectStoreUrl(String orgStation) throws Exception;
	
	public String selectTransCodeByUserId(HashMap<String, Object> transCodeParam) throws Exception;
	
	public void updateApiOrderListHawb(ApiOrderListVO updateShopifyOrder) throws Exception;

	public void insertApiOrderList(ApiOrderListVO shopifyOrder) throws Exception;

	public void insertApiOrderItem(ApiOrderItemListVO apiOrderItemListVO) throws Exception;

	public int selectCount(HashMap<String,String> parameters) throws Exception;

	public int selectTakeItemCnt(String cusItemCode) throws Exception;

	public void insertShopifyFulfilInfo(HashMap<String, Object> shopifyFulfilInfo) throws Exception;

	public String selectTotalWeight(@Param("nno")String newNno, @Param("wtUnit")String wtUnit) throws Exception;

	public HashMap<String, Object> selectFulfilInfo(String nno) throws Exception;

	public void insertShopifyFulfillData(HashMap<String, Object> shopItemInfo);

	public BlApplyVO selectBlApply(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String,Object>> selectFulfillment(String string);
}
