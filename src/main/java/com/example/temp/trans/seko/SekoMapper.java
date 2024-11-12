package com.example.temp.trans.seko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.trans.ocs.ApiOrderOcsVO;

@Repository
@Mapper
public interface SekoMapper {

	public ApiOrderSekoVO selectListInfoForSeko(String nno) throws Exception;

	public ArrayList<ApiOrderItemSekoVO> selectItemInfoForSeko(String nno) throws Exception;

	public void updateHawb(Map<String, Object> trackInfo) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public void insertSekoWeight(ApiOrderSekoVO orderInfo) throws Exception;

}
