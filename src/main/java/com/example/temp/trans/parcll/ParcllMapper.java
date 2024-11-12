package com.example.temp.trans.parcll;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Repository
@Mapper
public interface ParcllMapper {

	UserOrderListVO selectCreateOrderInfo(HashMap<String, Object> parameterInfo);

	ArrayList<UserOrderItemVO> selectCreateOrderItem(HashMap<String, Object> parameterInfo);

	String selectHawbNoByNNO(String nno);

	String selectUserIdByNno(HashMap<String, Object> parameterInfo);

	void insertApiConn(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> selectMatchInfo(HashMap<String, Object> parameterInfo);

	void updateDelveryInfo(HashMap<String, Object> parameterInfo);

}
