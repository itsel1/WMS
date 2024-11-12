package com.example.temp.trans.hanjin;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;

@Repository
@Mapper
public interface HanjinMapper {

	UserOrderListVO selectAddressInfo(HashMap<String, Object> parameters);

	void updateErrorStatus(HashMap<String, Object> parameters);

	UserOrderListVO selectPrintOrderInfo(HashMap<String, Object> parameters);

	ArrayList<UserOrderItemVO> selectPrintItemInfo(HashMap<String, Object> parameters);

	String selectNnoByHawbNo(String hawbNo);

	void insertRefnAddrInfo(HanjinVO hjInfo);

	HanjinVO selectPrintLabelInfo(HashMap<String, Object> parameterInfo);

	UserOrderListVO selectHjOrderInfo(HashMap<String, Object> parameters);

	void updateCneeZipInfo(HanjinVO hjInfo);
}
