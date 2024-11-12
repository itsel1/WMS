package com.example.temp.api.webhook.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.temp.api.webhook.vo.SoluTrkVO;

@Repository
@Mapper
public interface WebhookMapper {

	public void insertSoluTrkData(SoluTrkVO trakingVO) throws Exception;

	public LinkedHashMap<String, Object> selectReturnRequestStatus(HashMap<String, Object> parameterInfo) throws Exception;

}
