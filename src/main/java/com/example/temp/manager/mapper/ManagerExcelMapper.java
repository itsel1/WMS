package com.example.temp.manager.mapper;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.OrderInspListVO;
import com.example.temp.manager.vo.UmanifastExcelVO;


@Repository
@Mapper
public interface ManagerExcelMapper {

	public String excelUediDown(String parameter);

	public ArrayList<UmanifastExcelVO> selectUmaniFastExcel(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<MawbVO> selectMawbInfo(HashMap<String, Object> parameterInfo)  throws Exception;

	public ArrayList<HashMap<String,Object>> selectExcelColumnName(String formName) throws Exception;

	public ArrayList<OrderInspListVO> selectRusOrder(@Param("mawbNo")String mawbNo, @Param("shipperReference")String shipperReference) throws Exception;

	public HashMap<String,Object> selectUhanExplicence(String hawbNo) throws Exception;


}
