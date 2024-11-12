package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.manager.vo.AramexListVO;


@Service
public interface ManagerAramexService {

	public int selectTotalCountAramex(HashMap<String, Object> parameterInfo) throws Exception;

	public ArrayList<AramexListVO> selectAramexList(HashMap<String, Object> parameterInfo) throws Exception;

	public void updateAramexListError(HashMap<String, Object> parameterInfo) throws Exception;

	public String insertAramexExcelUpload(MultipartHttpServletRequest multi, HttpServletRequest request, HashMap<String, String> parameters) throws Exception;

	public HashMap<String, Object> applyAramex(HashMap<String, Object> parameters) throws Exception;

	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception;
	
	public ArrayList<HashMap<String, Object>>  aramexPodPage(String hawbNo) throws Exception;

	public void sendAramexWeightFtp() throws Exception;
	public ArrayList<HashMap<String, Object>>  aramexPodPage2(String hawbNo) throws Exception;
}
