package com.example.temp.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.vo.CodeVO;
import com.example.temp.member.vo.UserGroupStockVO;
import com.example.temp.member.vo.UserInspStationVO;
import com.example.temp.member.vo.stock.OrderWhInOutVO;

@Service
public interface MemberStockService {

	ArrayList<UserInspStationVO> userinSpStation(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<CodeVO> usrStockStatus() throws Exception;

	ArrayList<UserGroupStockVO> userInspStockList(HashMap<String, Object> parameterInfo) throws Exception;

	int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception;

	void insertMsgInfo(StockMsgVO msgInfo) throws Exception;

	ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception;

	int selectTotalCountStockUnIdent(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<UserGroupStockVO> userInspStockUnidentList(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<OrderWhInOutVO> selectinspUserOrderWhInOut(HashMap<String, Object> parameterInfo) throws Exception;

	int selectinspUserOrderWhInOutCnt(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectOrderStation(HashMap<String, Object> parameterInfo);

	ArrayList<OrderWhInOutVO> selectinspUserOrderStrockDetail(HashMap<String, Object> parameterInfo);

	
}
