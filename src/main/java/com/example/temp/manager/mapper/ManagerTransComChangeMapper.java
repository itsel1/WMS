package com.example.temp.manager.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface ManagerTransComChangeMapper {
	
	ArrayList<HashMap<String, Object>> selectTransComChg(HashMap<String, Object> parameterInfo);

	ArrayList<HashMap<String, Object>> selectNation(HashMap<String, Object> nationParameterInfo);

	ArrayList<HashMap<String, Object>> selectTrans(HashMap<String, Object> transParameterInfo);

	HashMap<String, Object> deleteTransComChangeDel(HashMap<String, Object> parameterInfo);

	HashMap<String, Object> insertTransComChangeIn(HashMap<String, Object> parameterInfo);

}
