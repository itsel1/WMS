package com.example.temp.trans.ems;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderListVO;

@Repository
@Mapper
public interface EmsMapper {

	public ApiOrderListVO selectOrderInfo(String nno) throws Exception;

	public ArrayList<HashMap<String, Object>> selectOrderItem(String nno) throws Exception;

	public String selectHawbNoInTmp(String orderNno) throws Exception;

	public String selectHawbNoInTB(String orderNno) throws Exception;

}
