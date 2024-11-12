package com.example.temp.manager.mapper;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.AramexListVO;
import com.example.temp.manager.vo.HawbVO;


@Repository
@Mapper
public interface ManagerAramexMapper {

	public int selectTotalCountAramex(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<AramexListVO> selectAramexList(HashMap<String, Object> parameterInfo) throws Exception;

	public void updateAramexListError(HashMap<String, Object> parameterInfo) throws Exception;

	public void insertAramexExcelUpload(AramexListVO aramexListVO) throws Exception;

	public HashMap<String, Object> applyAramex(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> deleteAramex() throws Exception;

	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception;

	public ArrayList<HawbVO> selectAramexHawbList() throws Exception;

}
