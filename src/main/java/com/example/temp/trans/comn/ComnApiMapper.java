package com.example.temp.trans.comn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository

@Mapper
public interface ComnApiMapper {

	public void insertPodRecord(HashMap<String,Object> parameter) throws Exception;

	public HashMap<String, Object> selectOrderInfo(String nno) throws Exception;

}
