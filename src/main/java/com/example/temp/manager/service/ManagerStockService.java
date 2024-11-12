package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.manager.vo.stockvo.GroupStockDetailVO;
import com.example.temp.manager.vo.stockvo.GroupStockInfoVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;


@Service
public interface ManagerStockService {

	int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<GroupStockVO> selectInspStockOrderInfo(HashMap<String,Object> parameters) throws Exception;

	ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception;

	void insertMsgInfo(StockMsgVO msgInfo) throws Exception;

	GroupStockInfoVO selectGgroupStock(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<GroupStockDetailVO> selectGgroupStockDetail(HashMap<String, Object> parameterInfo) throws Exception;

	

	int selectStockDisposalCnt(HashMap<String, Object> parameterInfo) throws Exception;
	

	public HashMap<String, Object> spStockDisposal(HashMap<String,Object> parameters) throws Exception;

	ArrayList<GroupStockVO> selectStockDisposal(HashMap<String, Object> parameterInfo) throws Exception;

	HashMap<String, Object> spStockDisposalcancle(HashMap<String, Object> parameters) throws Exception;

	HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters) throws Exception;

	int selectStockUnidentifiedItemCount(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<GroupStockVO> selectStockUnidentifiedItem(HashMap<String, Object> parameterInfo) throws Exception;

	int selectTotalCountStockAdd(HashMap<String, Object> parameterInfo) throws Exception;

	ArrayList<GroupStockVO> selectInspStockAddOrderInfo(HashMap<String, Object> parameterInfo) throws Exception;

}
