package com.example.temp.manager.mapper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.stockvo.StockMsgVO;
import com.example.temp.manager.vo.stockvo.GroupStockDetailVO;
import com.example.temp.manager.vo.stockvo.GroupStockInfoVO;
import com.example.temp.manager.vo.stockvo.GroupStockVO;


@Repository
@Mapper
public interface ManagerStockMapper {

	int selectTotalCountStock(HashMap<String, Object> parameterInfo)  throws Exception;


	ArrayList<String> selectFilePath(HashMap<String, Object> parameters);
	
	public ArrayList<GroupStockVO> selectInspStockList(HashMap<String, Object> parameterInfo) throws Exception;


	ArrayList<StockMsgVO> selectMsgHist(HashMap<String, String> parameters);


	void insertMsgInfo(StockMsgVO msgInfo);


	GroupStockInfoVO selectGgroupStock(HashMap<String, Object> parameterInfo) throws Exception;


	ArrayList<GroupStockDetailVO> selectGgroupStockDetail(HashMap<String, Object> parameterInfo) throws Exception;


	HashMap<String, Object> spStockDisposal(Map<String, Object> parameters) throws Exception;


	int selectStockDisposalCnt(HashMap<String, Object> parameterInfo) throws Exception;


	ArrayList<GroupStockVO> selectStockDisposal(HashMap<String, Object> parameterInfo) throws Exception;


	HashMap<String, Object> spStockDisposalcancle(HashMap<String, Object> parameters) throws Exception;


	HashMap<String, Object> spStockCancle(HashMap<String, Object> parameters) throws Exception;


	int selectStockUnidentifiedItemCount(HashMap<String, Object> parameterInfo) throws Exception;


	ArrayList<GroupStockVO> selectStockUnidentifiedItem(HashMap<String, Object> parameterInfo) throws Exception;


	int selectTotalCountStockAdd(HashMap<String, Object> parameterInfo) throws Exception;


	ArrayList<GroupStockVO> selectInspStockAddList(HashMap<String, Object> parameters) throws Exception;


	
}
