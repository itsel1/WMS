package com.example.temp.trans.t86;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.member.vo.TestssVO;

@Repository
@Mapper
public interface Type86Mapper {

	ArrayList<ApiOrderListVO> selectOrderList(String nno) throws Exception;

	void insertErrorMatch(HashMap<String, Object> params) throws Exception;

	String selectHawbNoByNNO(String nno);

	BlApplyVO selectBlApply(TestssVO parameters);

	void updateOrderListDate(HashMap<String, Object> params);

	String selectUserIdByNNO(String nno);

	void insertApiConn(HashMap<String, Object> params);

	String selectMatchNumByHawb(String hawbNo);
	
	ApiOrderListVO selectOrderInfoOne(String nno) throws Exception;

	ArrayList<ApiOrderListVO> selectTmpOrderList(String orderNno);

}
