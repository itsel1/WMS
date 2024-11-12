package com.example.temp.trans.shipStation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.shopify.ApiShopifyInfoVO;
import com.example.temp.trans.ocs.ApiOrderOcsVO;

@Repository
@Mapper
public interface ShipStationMapper {

	public void updateHawb(Map<String, Object> trackInfo) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public void insertSekoWeight(ShipStationOrderVO orderInfo) throws Exception;

	public ShipStationOrderVO selectListInfoForShipStation(String nno) throws Exception;

	public ArrayList<ShipStationItemVO> selectItemInfoForShipStation(String nno) throws Exception;

	public void insertShipStationId(HashMap<String, Object> stationInfo) throws Exception;

	public HashMap<String, Object> selectShipStationInfo(String nno) throws Exception;

	public ArrayList<HashMap<String, Object>> selectShopifyFulfillInfo(String nno);

	public ApiShopifyInfoVO selectShopifyInfo(String nno);

	public ShipStationOrderVO selectShipStationOrderInfo(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<ShipStationItemVO> selectShipStationItemInfo(HashMap<String, Object> parameters) throws Exception;

	public void updateErrorStatus(HashMap<String, Object> parameters);

	public void insertShipStationTest(HashMap<String, Object> parameters);

	public ArrayList<HashMap<String, Object>> selectShipStationTest();

	public void updateShipStationTest(HashMap<String, Object> parameters);


}
