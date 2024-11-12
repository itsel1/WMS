package com.example.temp.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.mapper.ManagerDepositMapper;
import com.example.temp.manager.service.ManagerDepositService;
import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.DepositVO;

@Service
@Transactional
public class ManagerDepositServiceImpl implements ManagerDepositService {
	
	@Autowired
	ManagerDepositMapper mapper;

	@Override
	public ArrayList<HashMap<String, Object>> selectStationList() throws Exception {
		return mapper.selectStationList();
	}

	@Override
	public int selectDepositListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectDepositListCnt(params);
	}

	@Override
	public ArrayList<DepositVO> selectDepositList(HashMap<String, Object> params) throws Exception {
		return mapper.selectDepositList(params);
	}

	@Override
	public DepositVO selectDepositRecInfo(HashMap<String, Object> params) throws Exception {
		return mapper.selectDepositRecInfo(params);
	}

	@Override
	public void updateDepositRecInfo(HashMap<String, Object> params) throws Exception {
		mapper.updateDepositRecInfo(params);
	}

	@Override
	public void deleteDepositRecInfo(HashMap<String, Object> params) throws Exception {
		mapper.deleteDepositRecInfo(params);
	}

	@Override
	public double selectDepositAmtTot(HashMap<String, Object> params) throws Exception {
		return mapper.selectDepositAmtTot(params);
	}

	@Override
	public ArrayList<DepositVO> selectUserList(HashMap<String, Object> params) throws Exception {
		return mapper.selectUserList(params);
	}

	@Override
	public ArrayList<CurrencyVO> selectCurrencyList() throws Exception {
		return mapper.selectCurrencyList();
	}

	@Override
	public void insertDepositRecInfo(HashMap<String, Object> params) throws Exception {
		mapper.insertDepositRecInfo(params);
	}

	@Override
	public void deleteDepositApplyInfo(HashMap<String, Object> params) throws Exception {
		mapper.deleteDepositApplyInfo(params);
	}

}
