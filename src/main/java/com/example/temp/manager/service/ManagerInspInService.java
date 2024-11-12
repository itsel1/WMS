package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;


@Service
public interface ManagerInspInService {

	ArrayList<HashMap<String, Object>> selectInspaddList(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<HashMap<String, Object>> selectInspaddStockDetail(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> selectInspaddGroupStockInfo(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> updateInspGroupStockUpdate(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectReqReturnList(HashMap<String, Object> parameterInfo) throws Exception;

	int selectReqReturnListCnt(HashMap<String, Object> parameterInfo)  throws Exception;

	HashMap<String, Object> selectReqReturnDetatil(HashMap<String, Object> parameterInfo)  throws Exception;

	ArrayList<HashMap<String, Object>> selectReqReturnItemDetatil(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectReturnWhoutStockList(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> spWhoutStockReturn(HashMap<String, Object> parameterInfo);

}
