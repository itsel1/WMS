package com.example.temp.trans.gts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository

@Mapper
public interface GtsMapper {

	public void updateHawb(Map<String, Object> trackInfo) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public GtsOrderVO selectGtsShipmentInfo(String nno) throws Exception;

	public ArrayList<HashMap<String,Object>> selectGtsItemInfo(String nno) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public void insertOzonTrack(HashMap<String, Object> parameters) throws Exception;

	public String selectShipperReference(String hawbNo) throws Exception;

	public ArrayList<HashMap<String, Object>> selectGtsLockerInfo() throws Exception;

	public void updateGtsPostalCode(@Param("postalCode")String postalCode, @Param("lockerNumber")String lockerNumber);

	public ArrayList<HashMap<String, Object>> selectGtsLockerInfos() throws Exception;

	public void updateSendYn(String uid) throws Exception;

}
