package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import com.example.temp.manager.vo.CsBoardVO;


@Service
public interface ManagerCsBoardService {
	
	public ArrayList<CsBoardVO> selectCsList(HashMap<String, Object> infoMap) throws Exception;
	
	public void insertCs(CsBoardVO csBoardVO) throws Exception;

	public ArrayList<CsBoardVO> selectCsInfoList(HashMap<String, Object> params) throws Exception;
	
	public CsBoardVO selectCsDetail(HashMap<String, Object> params) throws Exception;

	public String selectNno() throws Exception;

	public String updateCs(CsBoardVO csBoardVO) throws Exception;

	public HashMap<String, Object> selectCsInfoDetail(HashMap<String, Object> params) throws Exception;
}