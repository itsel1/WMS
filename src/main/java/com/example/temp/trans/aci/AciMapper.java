package com.example.temp.trans.aci;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AciMapper {

	public HashMap<String, Object> selectOrderDate(String hawbNo) throws Exception;

	public HashMap<String, Object> selectHawbDate(String hawbNo) throws Exception;

	public HashMap<String,String> selectMawbDate(String hawbNo) throws Exception;
}
