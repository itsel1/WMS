package com.example.temp.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.manager.mapper.ManagerStockMapper;
import com.example.temp.manager.service.ManagerStockService;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.manager.vo.stockvo.GroupStockDetailVO;
import com.example.temp.manager.vo.stockvo.GroupStockInfoVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;

@Service
@Transactional
public class ManagerStockServiceImpl implements ManagerStockService{
	
	@Autowired
	private ManagerStockMapper mapper;

	@Override
	public int selectTotalCountStock(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStock(parameterInfo);
	}
	@Override
	public int selectTotalCountStockAdd(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTotalCountStockAdd(parameterInfo);
	}

	@Override
	public ArrayList<GroupStockVO> selectInspStockOrderInfo(HashMap<String, Object> parameters) throws Exception  {
		
		ArrayList<GroupStockVO> returnList = new ArrayList<GroupStockVO>();

		returnList = mapper.selectInspStockList(parameters);

		return returnList;
	}
	
	@Override
	public ArrayList<GroupStockVO> selectInspStockAddOrderInfo(HashMap<String, Object> parameters) throws Exception  {
		
		ArrayList<GroupStockVO> returnList = new ArrayList<GroupStockVO>();

		returnList = mapper.selectInspStockAddList(parameters);

		return returnList;
	}
	
	
	@Override
	public ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMsgHist(parameters);
	}

	@Override
	public void insertMsgInfo(StockMsgVO msgInfo) throws Exception {
		mapper.insertMsgInfo(msgInfo);
	}

	@Override
	public GroupStockInfoVO selectGgroupStock(HashMap<String, Object> parameterInfo) throws Exception {
		
		return mapper.selectGgroupStock(parameterInfo);
	}

	@Override
	public ArrayList<GroupStockDetailVO> selectGgroupStockDetail(HashMap<String, Object> parameterInfo)
			throws Exception {
		return mapper.selectGgroupStockDetail(parameterInfo);
	}


	@Override
	public HashMap<String, Object> spStockDisposal(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return  mapper.spStockDisposal(parameters);
	}


	@Override
	public int selectStockDisposalCnt(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockDisposalCnt(parameterInfo);
	}

	@Override
	public ArrayList<GroupStockVO> selectStockDisposal(HashMap<String, Object> parameterInfo) throws Exception {
		
		ArrayList<GroupStockVO> returnList = new ArrayList<GroupStockVO>();
		returnList = mapper.selectStockDisposal(parameterInfo);

		return returnList;
	}

	@Override
	public HashMap<String, Object> spStockDisposalcancle(HashMap<String, Object> parameters) throws Exception {
		return  mapper.spStockDisposalcancle(parameters);
	}

	@Override
	public HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return  mapper.spStockCancle(parameters);
	}

	@Override
	public int selectStockUnidentifiedItemCount(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStockUnidentifiedItemCount(parameterInfo);
	}

	@Override
	public ArrayList<GroupStockVO> selectStockUnidentifiedItem(HashMap<String, Object> parameterInfo) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<GroupStockVO> returnList = new ArrayList<GroupStockVO>();
		returnList = mapper.selectStockUnidentifiedItem(parameterInfo);

		return returnList;
	}


}
