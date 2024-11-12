package com.example.temp.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.manager.mapper.ManagerCsBoardMapper;
import com.example.temp.manager.service.ManagerCsBoardService;
import com.example.temp.manager.vo.CsBoardVO;

@Service
public class ManagerCsBoardServiceImpl implements ManagerCsBoardService {

	@Autowired
	ManagerCsBoardMapper mapper;
	
	@Override
	public ArrayList<CsBoardVO> selectCsList(HashMap<String, Object> infoMap) {
		// TODO Auto-generated method stub
		return mapper.selectCsList(infoMap);
	}

	@Override
	public void insertCs(CsBoardVO csBoardVO) throws Exception {
		// TODO Auto-generated method stub
		
		mapper.insertCs(csBoardVO);

	}

	@Override
	public ArrayList<CsBoardVO> selectCsInfoList(HashMap<String, Object> params) throws Exception {
		return mapper.selectCsInfoList(params);
	}

	@Override
	public CsBoardVO selectCsDetail(HashMap<String, Object> params) throws Exception {
		
		return mapper.selectCsDetail(params);
	}

	@Override
	public String selectNno() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectNno();
	}

	@Override
	public String updateCs(CsBoardVO csBoardVO) throws Exception {
		
		return mapper.updateCs();
		
	}

	@Override
	public HashMap<String, Object> selectCsInfoDetail(HashMap<String, Object> params) throws Exception {
		
		return mapper.selectCsInfoDetail(params);
	}

}