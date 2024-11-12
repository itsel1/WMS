package com.example.temp.trans.cse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository

@Mapper
public interface CseMapper {

	public void updateHawb(Map<String, Object> trackInfo) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public CseOrderVO selectCseShipmentInfo(String nno) throws Exception;

	public ArrayList<CseItemVO> selectCseShipmentItemInfo(String nno) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public HashMap<String,Object> selectOzonTrack(HashMap<String, Object> parameters) throws Exception;

}
