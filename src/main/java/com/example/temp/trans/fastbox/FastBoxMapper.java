package com.example.temp.trans.fastbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.manager.vo.FastboxInfoVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;

@Repository
@Mapper
public interface FastBoxMapper {

	public ArrayList<ApiOrderListVO> selectOrderList(String nno);
	public ArrayList<ApiOrderItemListVO> selectOrderItemList(String nno);
	public void selectBlApply(String orderNno, String userId, String userIp);
	public void insertErrorMatch(HashMap<String, Object> parameters);
	public void insertApiConn(HashMap<String, Object> params);
	public FBOrderVO selectFbNnoOne(FastBoxParameterVO fbParameterOne);
	public ArrayList<LinkedHashMap<String, Object>> selectFBItem(String nno);
	public LinkedHashMap<String, Object> selectFBOrderInfo(String nno);
	public ApiOrderListVO selectOrderInfo(String nno);
	public String selectArrDateMawbNo(String mawbNo);
	public HashMap<String, Object> selectOrderExpLicenceInfo(String nno);
	public String selectNNObyHawbNo(String hawbNo);
	public void updateExpLicenceN(HashMap<String, Object> params);
	public void updateExpLicence(HashMap<String, Object> params);
	public FastboxInfoVO selectMallInfo(String userId);
	public HashMap<String, Object> selectUserInfo(String nno);
	public HashMap<String, Object> selectFastBoxMallInfo(String userId);
	public String selectUserId(String nno);
	public void updateApiConn(HashMap<String, Object> params);
	public void updateFastboxSendYn(HashMap<String, Object> results);
	public FastboxUserInfoVO selectFastboxCusInfo(String userId);
	public void insertApiRespData(HashMap<String, Object> params);
	public ArrayList<HashMap<String, Object>> selectMatchNumAll();
	public void insertFastboxWeight(HashMap<String, Object> parameters);
	public int selectBlCnt(HashMap<String, Object> parameters);
	public HashMap<String, Object> selectBlInfo(HashMap<String, Object> parameters);
	public void insertMatchingInfo(HashMap<String, Object> parameters);
	public String selectAgencyBl(HashMap<String, Object> parameters);
	public String selectHawbNoByNNO(String nno);
	public int selectDuplicatedWeight(String rtnHawbNo);
	public ArrayList<HashMap<String, Object>> selectFastboxViewList();
	public HashMap<String, Object> selectOrderExpInfo(String nno);
	public ArrayList<HashMap<String, Object>> selectRequestShippingDataList(String mawbNo);
	public void insertTbFastboxSend(HashMap<String, Object> sqlParams);
}
