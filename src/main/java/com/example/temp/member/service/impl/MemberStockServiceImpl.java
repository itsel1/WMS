package com.example.temp.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.member.mapper.MemberStockMapper;
import com.example.temp.member.service.MemberStockService;
import com.example.temp.member.vo.CodeVO;
import com.example.temp.member.vo.UserGroupStockVO;
import com.example.temp.member.vo.UserInspStationVO;
import com.example.temp.member.vo.stock.OrderWhInOutVO;

@Service
@Transactional
public class MemberStockServiceImpl implements MemberStockService{
	
	@Autowired
	private MemberStockMapper mapper;

	@Override
	public ArrayList<UserInspStationVO> userinSpStation(HashMap<String, Object> parameterInfo) throws Exception {
		
		
		return  mapper.userinSpStation(parameterInfo);
	}

	@Override
	public ArrayList<CodeVO> usrStockStatus() throws Exception {
		// TODO Auto-generated method stub
		return mapper.usrStockStatus();
	}

	@Override
	public ArrayList<UserGroupStockVO> userInspStockList(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.userInspStockList(parameterInfo);
	}

	@Override
	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStock(parameterInfo);
	}


	@Override
	public void insertMsgInfo(com.example.temp.manager.vo.stockvo.StockMsgVO msgInfo) throws Exception {

		mapper.insertMsgInfo(msgInfo);
		
	}

	@Override
	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) {
		// TODO Auto-generated method stub
		return mapper.selectMsgHist(parameters);
	}

	@Override
	public int selectTotalCountStockUnIdent(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStockUnIdent(parameterInfo);
	}

	@Override
	public ArrayList<UserGroupStockVO> userInspStockUnidentList(HashMap<String, Object> parameterInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.userInspStockUnidentList(parameterInfo);
	}

	@Override
	public ArrayList<OrderWhInOutVO> selectinspUserOrderWhInOut(HashMap<String, Object> parameterInfo)
			throws Exception {
		
		return mapper.selectinspUserOrderWhInOut(parameterInfo);
	}

	@Override
	public int selectinspUserOrderWhInOutCnt(HashMap<String, Object> parameterInfo) {

		return mapper.selectinspUserOrderWhInOutCnt(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectOrderStation(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectOrderStation(parameterInfo);
	}

	@Override
	public ArrayList<OrderWhInOutVO> selectinspUserOrderStrockDetail(HashMap<String, Object> parameterInfo) {
		// TODO Auto-generated method stub
		return mapper.selectinspUserOrderStrockDetail(parameterInfo);
	}



}
