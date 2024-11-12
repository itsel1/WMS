package com.example.temp.trans.cj;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CJMapper {
	
	public ArrayList<CJOrderVo> selectCjHawbNo(CJParameterVo parmeterInfo) throws Exception;

	public void insertCJError(CJStatusVo cJStatusVo);

	public void insertCJSuccess(CJStatusVo cJStatusVo); 

	public CJOrderVo selectCjHawbNoOne(CJParameterVo cJPatemterOne);
	 
	public CJOrderVo selectCjNnoOne(CJParameterVo cJPatemterOne);

	public ArrayList<CJOrderVo> selectCusItemCode(String nno);

}
