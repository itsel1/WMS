package com.example.temp.trans.fedex;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiAdminVO;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;

@Repository
@Mapper
public interface FedexMapper {

	public ApiOrderListVO selectFedexInfo(HashMap<String, Object> tempParameter) throws Exception;
	
	public ArrayList<ApiOrderItemListVO> selectFedexItemInfo(HashMap<String, Object> tempParameter) throws Exception;
	
	public ApiAdminVO selectApiAdminInfo(String orgStation) throws Exception;

	public ApiOrderListVO selectFedexInfoTmp(HashMap<String, Object> tempParameter) throws Exception;
	
	public ArrayList<ApiOrderItemListVO> selectFedexItemInfoTmp(HashMap<String, Object> tempParameter) throws Exception;

	public void insertZoneInfo(ApiOrderListVO tempApiOrderInfo) throws Exception;

	public ArrayList<ApiOrderFedexVO> selectFedexSmartHawbNo() throws Exception;

	public void insertDlvResult(HashMap<String, String> parameter) throws Exception;

	public ArrayList<ApiOrderListVO> selectTmpOrderInfo(HashMap<String, Object> nnoList);
}
