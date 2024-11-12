package com.example.temp.trans.comn;

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
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.MatchingVO;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.MawbVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.security.SecurityKeyVO;
import com.example.temp.trans.cj.CJParameterVo;
import com.example.temp.trans.gts.GtsAPI;

@Service
public class ComnAPI {
//	@AutowiredorderItem
//	ApiMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	ComnApiMapper mapper;
	
	@Autowired
	GtsAPI gtsApi;
	
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public ArrayList<MawbVO> mawbListLookUp(String sDate, String eDate) throws Exception{
		ArrayList<MawbVO> mawbList = new ArrayList<MawbVO>();
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put("sDate", sDate);
		parameters.put("eDate", eDate);
		mawbList = comnService.mawbListLookUp(parameters);
		return mawbList;
	}

	public ArrayList<String> hawbLookUpInMawb(String mawbNo) throws Exception{
		// TODO Auto-generated method stub
		return comnService.selectHawbByMawb(mawbNo);
	}

	public HawbLookUpVo hawbLookUp(String hawbNo) throws Exception{
		return comnService.selectHawbInfoJson(hawbNo);
	}
	
	public void comnSaveHawb(String userId, String userIp, String transCode, String orderNno) throws Exception {
		String printType = transCode;
		
		if("".equals(printType)|| printType == null || "undefined".equals(printType))
			printType = comnService.selectTransComByNno(orderNno);

		switch (printType) {
			case "YSL":
				//comnService.yslPdf(orderNno,printType, userId, userIp, "A");
				comnService.yslPdfV2(orderNno,printType,userId,userIp,"C");
				break;
			case "ACI":
				//comnService.savePdf(orderNno,printType, userId, userIp);
				comnService.createLabelForApi(orderNno, printType, userId, userIp);
				break;
			case "GTS":
				comnService.savePdf(orderNno,printType, userId, userIp);
				break;
			case "YES":
				comnService.savePdf(orderNno,printType, userId, userIp);
				break;
			case "SAGAWA": 
				comnService.savePdf(orderNno,printType, userId, userIp);
				break;
			case "OCS": 
				comnService.ocsPdf(orderNno,printType,userId, userIp);
				break;
			case "EPT": 
				comnService.krPostPdf(orderNno,printType, userId, userIp);
				break;
			case "EFS": 
				comnService.efsPdf(orderNno,printType, userId, userIp);
				break;
			case "ETCS":
				comnService.etcsPdf(userId,orderNno);
				break;
			case "CJ":
				ArrayList<CJParameterVo> cJParameters = new ArrayList<CJParameterVo>();
					CJParameterVo parameter = new CJParameterVo();
					parameter.setNno(orderNno);
					cJParameters.add(parameter);
				comnService.cjPdf(cJParameters, userId, userIp);
				break;
			case "YT":
				//comnService.savePdf(orderNno,printType, userId, userIp);
				comnService.createLabelForApi(orderNno, printType, userId, userIp);
				break;
			default :
				//comnService.savePdf(orderNno,printType, userId, userIp);
				comnService.createLabelForApi(orderNno, printType, userId, userIp);
				break;
		}
	}
	
//	public void setTrackEventOzon(String nno, int reference, String transCode)  throws Exception{
//		HashMap<String,Object> parameters = new HashMap<String,Object>();
//		parameters = mapper.selectOrderInfo(nno);
//		parameters.put("transCode", transCode);
//		parameters.put("reference", reference);
//		mapper.insertPodRecord(parameters);
//		if(transCode.toUpperCase().equals("GTS")){
//			//gtsApi.
//		}else {
//			
//		}
//	}
	
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
