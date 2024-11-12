package com.example.temp.trans.krPost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.security.SecurityKeyVO;

@Service
public class KrPostAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	KrPostMapper mapper;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public String fnSendKrPost(String nno) throws Exception{
		
		
		
		return "";
	}
	
	public String krPostPdf(String nno) throws Exception{
		
		return "";
	}

	
	
//	public String selectMatchNoByNNo(String nno) throws Exception {
//		return comnService.selectMatchNumByHawb(mapper.selectHawbNoByNNO(nno));
//	}
//	
//	public static String getJsonStringFromMap( HashMap<String, Object> map )
//    {
//        JSONObject jsonObject = new JSONObject();
//        for( Map.Entry<String, Object> entry : map.entrySet() ) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            jsonObject.put(key, value);
//        }
//        
//        return jsonObject.toString();
//    }
//	
//	public HashMap<String, Object> selectPodBlInfo(HashMap<String, Object> parameters) throws Exception{
//		// TODO Auto-generated method stub
//		return mapper.selectPodBlInfo(parameters);
//	}
//	
//	public String selectKeyHawbByMatchNo(String matchNo) throws Exception{
//		return mapper.selectKeyHawbByMatchNo(matchNo);
//	}

}
