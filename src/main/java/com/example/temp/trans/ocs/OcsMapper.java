package com.example.temp.trans.ocs;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OcsMapper {

	ApiOrderOcsVO selectListInfoForOCS(String nno) throws Exception;

	ArrayList<ApiOrderItemOcsVO> selectItemInfoForOCS(String nno) throws Exception;

	String selectNationName(String dstnNation) throws Exception;

	void updateHawbNo(HashMap<String, String> parameters) throws Exception;

}
