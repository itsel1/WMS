package com.example.temp.trans.yongsung;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.common.vo.MatchingVO;

@Repository
@Mapper
public interface YongSungMapper {

	public ArrayList<ApiOrderItemYSVO> selectItemInfoForYS(String nno) throws Exception;

	public ArrayList<ApiOrderYSVO> selectListInfoForYS(String nno) throws Exception;

	public String selectHawbNoByNNO(String nno) throws Exception;

	public String selectNNOByHawbNo(String hawbNo) throws Exception;

	public void insertErrorMatch(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception;

	public String selectKeyHawbByMatchNo(String matchNo) throws Exception;

	public Object selectTakeInCode(@Param("cusItemCode")String cusItemCode, @Param("userId")String userId) throws Exception;
	
	public Object selectNomalCode(@Param("cusItemCode")String cusItemCode, @Param("userId")String userId) throws Exception;

	public String selectStateNameByCode(String cneeState) throws Exception;

	public String selectStateNameByName(String cneeState) throws Exception;

	public ArrayList<String> selectMatchNumAll() throws Exception;

	public void insertYslWeight(HashMap<String, String> parameters) throws Exception;
	
	public YsApiHawbVO selectHawbInfoForYS(String nno)  throws Exception;

	public void updateExpLicence(@Param("nno")String nno, @Param("rtnMsg")String rtnMsg) throws Exception;

	public void updateExpLicenceN(@Param("nno")String nno, @Param("rtnMsg")String rtnMsg) throws Exception;
	
	public void insertCusItemCode(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> selectYslOrderInfo(String nno) throws Exception;

	public void insertSagawaInfo(HashMap<String, String> parameters) throws Exception;

	public int selectSagawaInfoCnt(String nno) throws Exception;

	public String selectMatchNum(String hawbNo) throws Exception;

	public YsApiHawbVO selectYslOrderExpInfo(String orderNno) throws Exception;

	public int selectExpLicenceChk(String orderNno) throws Exception;

}
