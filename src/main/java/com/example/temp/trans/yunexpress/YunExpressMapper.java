package com.example.temp.trans.yunexpress;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Repository
@Mapper
public interface YunExpressMapper {

	UserOrderListVO selectTmpOrderInfo(HashMap<String, Object> parameterInfo);

	ArrayList<UserOrderItemVO> selectTmpItemInfo(HashMap<String, Object> parameterInfo);

	void insertErrorMatch(HashMap<String, Object> parameterInfo);

}
