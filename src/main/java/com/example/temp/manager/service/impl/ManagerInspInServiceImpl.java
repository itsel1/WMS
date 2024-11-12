package com.example.temp.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.mapper.ManagerInspInMapper;
import com.example.temp.manager.service.ManagerInspInService;

@Service
@Transactional
public class ManagerInspInServiceImpl implements ManagerInspInService{
	
	
	@Autowired
	private ManagerInspInMapper mapper;

	@Override
	public ArrayList<HashMap<String, Object>> selectInspaddList(HashMap<String, Object> parameterInfo) throws Exception{
	
		return mapper.selectInspaddList(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectInspaddStockDetail(HashMap<String, Object> parameterInfo){
	
		return mapper.selectInspaddStockDetail(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectInspaddGroupStockInfo(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectInspaddGroupStockInfo(parameterInfo);
	}

	@Override
	public HashMap<String, Object> updateInspGroupStockUpdate(HashMap<String, Object> parameterInfo) {

		return mapper.updateInspGroupStockUpdate(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReqReturnList(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectReqReturnList(parameterInfo);
	}

	@Override
	public int selectReqReturnListCnt(HashMap<String, Object> parameterInfo) throws Exception {

		return  mapper.selectReqReturnListCnt(parameterInfo);
	}

	@Override
	public HashMap<String, Object> selectReqReturnDetatil(HashMap<String, Object> parameterInfo) throws Exception {
	
		return mapper.selectReqReturnDetatil(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReqReturnItemDetatil(HashMap<String, Object> parameterInfo) {
		
		return mapper.selectReqReturnItemDetatil(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnWhoutStockList(HashMap<String, Object> parameterInfo) {
	
		return mapper.selectReturnWhoutStockList(parameterInfo);
	}

	@Override
	public HashMap<String, Object> spWhoutStockReturn(HashMap<String, Object> parameterInfo) {
		
		return mapper.spWhoutStockReturn(parameterInfo);
	}

}
