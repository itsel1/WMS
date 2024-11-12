package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.temp.manager.vo.CurrencyVO;
import com.example.temp.manager.vo.DepositVO;

@Service
public interface ManagerDepositService {

	public ArrayList<HashMap<String, Object>> selectStationList() throws Exception;
	public int selectDepositListCnt(HashMap<String, Object> params) throws Exception;
	public ArrayList<DepositVO> selectDepositList(HashMap<String, Object> params) throws Exception;
	public DepositVO selectDepositRecInfo(HashMap<String, Object> params) throws Exception;
	public void updateDepositRecInfo(HashMap<String, Object> params) throws Exception;
	public void deleteDepositRecInfo(HashMap<String, Object> params) throws Exception;
	public double selectDepositAmtTot(HashMap<String, Object> params) throws Exception;
	public ArrayList<DepositVO> selectUserList(HashMap<String, Object> params) throws Exception;
	public ArrayList<CurrencyVO> selectCurrencyList() throws Exception;
	public void insertDepositRecInfo(HashMap<String, Object> params) throws Exception;
	public void deleteDepositApplyInfo(HashMap<String, Object> params) throws Exception;

}
