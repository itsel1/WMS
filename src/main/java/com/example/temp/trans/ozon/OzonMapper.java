package com.example.temp.trans.ozon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository

@Mapper
public interface OzonMapper {

	public void updateHawb(Map<String, Object> trackInfo) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public OzonOrderVO selectCseShipmentInfo(String nno) throws Exception;

	public ArrayList<OzonItemVO> selectCseShipmentItemInfo(String nno) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectOzonUserInfo() throws Exception;

	public void updateOzonInfo(HashMap<String, Object> ozonUpdateInfo) throws Exception;

	public HashMap<String, Object> selectOrderInfo(String hawbNo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectTargetTrackingInfo(String clientId) throws Exception;

	public HashMap<String, Object> selectEventInfo(HashMap<String, Object> parameters) throws Exception;

	public String selectTokenByHawb() throws Exception;

	public void insertOzonTrack(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<String> selectUidList() throws Exception;

}
