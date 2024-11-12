package com.example.temp.trans.itemCarry;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.manager.vo.ProcedureVO;

@Repository
@Mapper
public interface ItcMapper {

	public ApiOrderListVO selectOrderInfo(String nno) throws Exception;

	public ArrayList<ItcItemVO> selectOrderItem(String nno) throws Exception;

	public void updateHawbOrderList(ProcedureVO returnVal) throws Exception;

	public String selectUserId(String nno) throws Exception;

	public ApiOrderListVO selectOrderInfoHawb(String nno) throws Exception;

	public HashMap<String, Object> selectAciPodInfo(String hawbNo) throws Exception;

	public HashMap<String, Object> selectAciInfo(String orgStation) throws Exception;

	public ArrayList<ItcItemVO> selectOrderItemTakeIn(String nno) throws Exception;


}
