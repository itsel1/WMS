package com.example.temp.common.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.member.vo.BlApplyVO;

@Repository
@Mapper
public interface ComnOrderMapper {

	BlApplyVO spTransBlApply(HashMap<String, Object> parameters) throws Exception;

	//void insertOrderToTmp(HashMap<String, Object> parameters) throws Exception;

}
