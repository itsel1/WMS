package com.example.temp.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.vo.CodeVO;
import com.example.temp.member.vo.UserGroupStockVO;
import com.example.temp.member.vo.UserInspStationVO;
import com.example.temp.member.vo.stock.OrderWhInOutVO;

@Repository
@Mapper
public interface MemberStockMapper {
	
	ArrayList<UserInspStationVO> userinSpStation(HashMap<String, Object> parameterInfo);

	ArrayList<CodeVO> usrStockStatus();

	ArrayList<UserGroupStockVO> userInspStockList(HashMap<String, Object> parameterInfo); 
	
	int selectTotalCountStock(HashMap<String, Object> parameterInfo);

	void insertMsgInfo(com.example.temp.manager.vo.stockvo.StockMsgVO msgInfo);

	ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters);

	int selectTotalCountStockUnIdent(HashMap<String, Object> parameterInfo);

	ArrayList<UserGroupStockVO> userInspStockUnidentList(HashMap<String, Object> parameterInfo);

	ArrayList<OrderWhInOutVO> selectinspUserOrderWhInOut(HashMap<String, Object> parameterInfo);

	int selectinspUserOrderWhInOutCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectOrderStation(HashMap<String, Object> parameterInfo);

	ArrayList<OrderWhInOutVO> selectinspUserOrderStrockDetail(HashMap<String, Object> parameterInfo);

}
