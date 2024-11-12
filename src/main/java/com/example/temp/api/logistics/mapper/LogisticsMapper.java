package com.example.temp.api.logistics.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.CommonVariables;
import com.example.temp.api.Export;
import com.example.temp.api.Item;
import com.example.temp.api.Order;
import com.example.temp.api.ProcedureRst;
import com.example.temp.api.logistics.dto.CJInfo;
import com.example.temp.api.logistics.dto.CJParameter;

@Repository
@Mapper
public interface LogisticsMapper {

	CJInfo selectCJInfo(HashMap<String, Object> sqlParams);

	void updateCJTokenInfo(CJInfo cjInfo);
	
	Order selectTmpOrder(HashMap<String, Object> sqlParams);

	ArrayList<Item> selectTmpItems(HashMap<String, Object> sqlParams);

	Export selectExportDeclare(HashMap<String, Object> sqlParams);

	ArrayList<Order> selectTmpOrderList(HashMap<String, Object> sqlParams);

	ProcedureRst execSpRegShipment(Order order);

	void insertCJSuccess(CJParameter cjParameter);

	void insertCjFail(CJParameter cjParameter);

	ArrayList<Order> selectCJRegBookOrderList();
	
	HashMap<String, String> selectTrackingDateTime(HashMap<String, Object> sqlParams);

	Order selectCJLabelData(HashMap<String, Object> sqlParams);

	ArrayList<CommonVariables> selectViewFastboxWeight();

	void insertFastboxWeight(CommonVariables cv);

	ArrayList<Order> selectCJRegBookReturnOrderList();
	
	void updateTmpOrderStatus(Order order);

	Order selectTbOrder(HashMap<String, Object> sqlParams);

	ArrayList<Item> selectTbOrderItems(HashMap<String, Object> sqlParams);

}
