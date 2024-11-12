package com.example.temp.manager.mapper;

import java.util.ArrayList; import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper; import
org.springframework.stereotype.Repository;

import com.example.temp.manager.vo.CsBoardVO;

@Repository
@Mapper 
public interface ManagerCsBoardMapper {

	public void insertCs(CsBoardVO csBoardVO) throws Exception;

	public ArrayList<CsBoardVO> selectCsList(HashMap<String, Object> infoMap);

	public ArrayList<CsBoardVO> selectCsInfoList(HashMap<String, Object> params);

	public CsBoardVO selectCsDetail(HashMap<String, Object> params);

	public String selectNno();

	public String updateCs();

	public HashMap<String, Object> selectCsInfoDetail(HashMap<String, Object> params);

//public ArrayList<NoticeVO> selectNoticeList(HashMap<String, Object> infoMap);
//
//public void insertNotice(NoticeVO noticeInfoVO) throws Exception; }

}