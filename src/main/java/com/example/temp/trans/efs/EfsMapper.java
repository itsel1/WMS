package com.example.temp.trans.efs;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.common.vo.MatchingVO;

@Repository
@Mapper
public interface EfsMapper {

	public ArrayList<ApiOrderItemEFSVO> selectItemInfoForEFS(String nno) throws Exception;

	public ArrayList<ApiOrderEFSVO> selectListInfoForEFS(String nno) throws Exception;

	public String selectHawbNoByNNO(String nno) throws Exception;

	public String selectNNOByHawbNo(String hawbNo) throws Exception;

	public void insertErrorMatch(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception;

	public String selectKeyHawbByMatchNo(String matchNo) throws Exception;

	public Object selectTakeInCode(@Param("cusItemCode")String cusItemCode, @Param("userId")String userId) throws Exception;

	public String selectStateNameByCode(String cneeState) throws Exception;

	public String selectStateNameByName(String cneeState) throws Exception;

	public String selectOrderNoFromNNO(String nno) throws Exception;

	public void updateHawbNo(HashMap<String, String> parameters) throws Exception;

	public String selectOrderDate(String hawbNo) throws Exception;

	public String selectHawbDate(String hawbNo) throws Exception;

	public ArrayList<String> selectMatchNumAll() throws Exception;

	public void insertEfsWeight(HashMap<String, String> parameters) throws Exception;
}
