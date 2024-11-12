package com.example.temp.api.shipment;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.shop.shopee.ShopeeDTO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Repository
@Mapper
public interface ShippingMapper {
	public UserOrderListVO selectTmpOrderListInfo(HashMap<String, Object> params);
	public ArrayList<UserOrderItemVO> selectTmpOrderItemInfo(HashMap<String, Object> params);
	public UserOrderItemVO selectTmpOrderItemInfoOne(HashMap<String, Object> params);
	public ShipmentVO spTransBl(HashMap<String, Object> params);
	public ShipmentVO spTransBlApply(HashMap<String, Object> params);
	public void insertTbApiError(HashMap<String, Object> params);
	public void updateTmpOrderListStatus(HashMap<String, Object> params);
	public ArrayList<HashMap<String, Object>> selectTmpOrderListAll(HashMap<String, Object> params);
	public int selectExportChk(HashMap<String, Object> orderInfo);
	public ExportVO selectExportInfo(HashMap<String, Object> orderInfo);
	public void updateExportLicenseInfo(HashMap<String, Object> orderInfo);
	public ResaleMallVO selectFastboxMallInfo(String userId);
	// 테스트
	public UserOrderListVO selectTmpOrderListInfoTest(HashMap<String, Object> parameterInfo);
	public ArrayList<UserOrderItemVO> selectTmpOrderItemInfoTest(HashMap<String, Object> parameterInfo);
	// 테스트
	public ExportVO selectExportDelareRequestBody(HashMap<String, Object> params);
	// Shopee
	public int selectShopeeInfoCnt(HashMap<String, Object> sqlParams);
	public ArrayList<ShopeeDTO> selectShopeeInfoList(HashMap<String, Object> sqlParams);
	public void insertShopeeInfo(ShopeeDTO sqlParams);
	public void updateShopeeRefreshToken(ShopeeDTO sqlParams);
	public void updateShopeeUseYn(ShopeeDTO sqlParams);
	public void insertShopeeHistory(ShopeeDTO sqlParams);
	public void deleteShopeeList(ShopeeDTO sqlParams);
}
