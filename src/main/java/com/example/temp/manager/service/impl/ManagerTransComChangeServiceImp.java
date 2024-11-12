package com.example.temp.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.mapper.ManagerTransComChangeMapper;
import com.example.temp.manager.service.ManagerTransComChangeService;

@Service
@Transactional
public class ManagerTransComChangeServiceImp implements ManagerTransComChangeService{
	
	@Autowired
	private ManagerTransComChangeMapper mapper;

	@Override
	public ArrayList<HashMap<String, Object>> selectTransComChg(HashMap<String, Object> parameterInfo) {
		return mapper.selectTransComChg(parameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectNation(HashMap<String, Object> nationParameterInfo) {
		return mapper.selectNation(nationParameterInfo);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectTrans(HashMap<String, Object> transParameterInfo) {
		
		
		return mapper.selectTrans(transParameterInfo);
	}

	@Override
	public HashMap<String, Object> deleteTransComChangeDel(HashMap<String, Object> parameterInfo) {
		
		return mapper.deleteTransComChangeDel(parameterInfo);
	}

	@Override
	public HashMap<String, Object> insertTransComChangeIn(HashMap<String, Object> parameterInfo) {
	
		return mapper.insertTransComChangeIn(parameterInfo);
	}

}
