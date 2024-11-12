package com.example.temp.member.service.impl; 

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.api.aci.vo.ReturnItemVO;
import com.example.temp.api.aci.vo.ReturnListVO;
import com.example.temp.api.aci.vo.ReturnRequestVO;
import com.example.temp.api.aci.vo.ReturnVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.common.vo.InvUserInfoVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.mapper.MemberReturnMapper;
import com.example.temp.member.service.MemberReturnService;

@Service
public class MemberReturnServiceImpl implements MemberReturnService{
	@Autowired
	private MemberReturnMapper mapper;
	
	@Autowired
	private ComnService comnService;

	@Value("${filePath}")
	String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;

	@Override
	public ReturnVO getReturnOrders(String koblNo, String userId) throws Exception {
		
		return mapper.getReturnOrders(koblNo, userId);
	}

	@Override
	public ArrayList<ReturnListVO> getReturnData(HashMap<String, Object> map) throws Exception {

		return mapper.getReturnData(map);
	}

	@Override
	public String getNno(String orderReference, String userId) throws Exception {

		return mapper.getNno(orderReference, userId);
	}

	@Override
	public int getTotalOrderCnt(HashMap<String, Object> map) throws Exception {

		return mapper.getTotalOrderCnt(map);
	}

	@Override
	public void updateItems(HashMap<String, String> map) throws Exception {
		mapper.updateItems(map);
		
	}

	@Override
	public void updateReturnData(HashMap<String, String> map1) throws Exception {
		mapper.updateReturnData(map1);
	}


	@Override
	public ReturnVO getAllExpressData(String orderReference, String userId) throws Exception {

		return mapper.getAllExpressData(orderReference, userId);
	}

	@Override
	public ArrayList<ReturnItemVO> getAllExpressItemsData(String nno) throws Exception {

		return mapper.getAllExpressItemsData(nno);
	}

	@Override
	public ArrayList<ReturnItemVO> getReturnOrdersItem(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getReturnOrdersItem(nno);
	}

	@Override
	public Map filesUpload(MultipartHttpServletRequest multi,String nno, String userId) throws Exception {
		// TODO Auto-generated method stub
		String path = realFilePath+"excel/tmpOrder/";
		File dir = new File(path);
	    if(!dir.isDirectory()){
	        dir.mkdir();
	    }
		Iterator<String> files = multi.getFileNames();
		Map<String, String> resMap = new HashMap<String, String>();
	    String fileName = "";

	    		
	    while(files.hasNext()){
	    	String newFileName = ""; // 업로드 되는 파일명
	    	String exten = "";
	        String uploadFile = files.next();
	        MultipartFile mFile = multi.getFile(uploadFile);
	        fileName = mFile.getOriginalFilename();
	        int index = fileName.lastIndexOf(".");
	        if(index > 0) {
	        	exten = fileName.substring(index+1);
        		if(uploadFile.equals("rtnReason")) {
		        	newFileName = nno+"_FR."+exten;
		        }else if(uploadFile.equals("rtnCapture")) {
		        	newFileName = nno+"_FC."+exten;
		        }else if(uploadFile.equals("rtnMessenger")) {
		        	newFileName = nno+"_FM."+exten;
		        }else if(uploadFile.equals("rtnCl")) {//X
		        	newFileName = nno+"_FCL."+exten;
		        }else if(uploadFile.equals("rtnIc")) {//X
		        	newFileName = nno+"_FIC."+exten;
		        }else if(uploadFile.equals("rtnCopyBank")) {
		        	newFileName = nno+"_FCB."+exten;
		        }
		        
		        try {
		            mFile.transferTo(new File(path+newFileName+exten));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        File UploadFile = new File(path+newFileName+exten);
		        //s3 업로드 로직 추가
		        AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult asssd = new PutObjectResult();
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
				if(amazonS3 != null) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/return/"+year+"/"+userId+"/"+nno, newFileName, UploadFile);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					asssd = amazonS3.putObject(putObjectRequest);
					String amazonPath = "http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+newFileName;
					resMap.put(uploadFile, amazonPath);
				}
				amazonS3 = null;
				UploadFile.delete();
		    }
	    }
		return resMap;
	}

	@Override
	public HashMap<String, Object> selectTempData(String nno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectTempData(nno);
	}

	@Override
	public void insertReturnRequest(ReturnRequestVO rtnRequestVO, Map<String, String> resMap) throws Exception {
		// TODO Auto-generated method stub
		for( String key : resMap.keySet() ){
            if(key.equals("rtnReason")) {
            	rtnRequestVO.setFileReason(resMap.get(key));
            }else if(key.equals("rtnCapture")) {
            	rtnRequestVO.setFileCapture(resMap.get(key));
            }else if(key.equals("rtnMessenger")) {
            	rtnRequestVO.setFileMessenger(resMap.get(key));
            }else if(key.equals("rtnCl")) {
            	rtnRequestVO.setFileCl(resMap.get(key));
            }else if(key.equals("rtnCopyBank")) {
            	rtnRequestVO.setFileCopyBank(resMap.get(key));
            }else if(key.equals("rtnIc")) {
            	rtnRequestVO.setFileIc(resMap.get(key));;
            }
        }
		rtnRequestVO.setOrderType("RETURN_WMS");
		rtnRequestVO.setRegNo(comnService.selectNewRegNo(rtnRequestVO));
		try{
			mapper.insertReturnRequest(rtnRequestVO);
		}catch (Exception e) {
			// TODO: handle exception
			comnService.deleteRegNo(rtnRequestVO.getRegNo());
		}
	}

	@Override
	public void insertReturnRequestItem(HashMap<String, Object> itemMap) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertReturnRequestItem(itemMap);
	}

	@Override
	public void updateReturnRequest(ReturnRequestVO rtnRequestVO, Map<String, String> resMap) throws Exception {
		// TODO Auto-generated method stub
		for( String key : resMap.keySet() ){
            if(key.equals("rtnReason")) {
            	rtnRequestVO.setFileReason(resMap.get(key));
            }else if(key.equals("rtnCapture")) {
            	rtnRequestVO.setFileCapture(resMap.get(key));
            }else if(key.equals("rtnMessenger")) {
            	rtnRequestVO.setFileMessenger(resMap.get(key));
            }else if(key.equals("rtnCl")) {
            	rtnRequestVO.setFileCl(resMap.get(key));
            }else if(key.equals("rtnCopyBank")) {
            	rtnRequestVO.setFileCopyBank(resMap.get(key));
            }
        }
		
		mapper.updateReturnRequest(rtnRequestVO);
	}

	@Override
	public void deleteReturnRequest(String nno) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteReturnRequest(nno);
	}

	@Override
	public void deleteReturnRequestItem(String nno) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteReturnRequestItem(nno);
	}

	@Override
	public void updateReturnState(String type, String orderReference) throws Exception {
		// TODO Auto-generated method stub
		if(type.equals("accept")) {
			mapper.updateReturnState("C004", orderReference);
		}else if(type.equals("unaccept")) {
			mapper.updateReturnState("C003", orderReference);
		}
		
	}

	@Override
	public double selectDepositInfo(Object userId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDepositInfo(userId);
	}

	@Override
	public ArrayList<NationVO> selectNationCode() {
		return mapper.selectNationCode();
	}

	@Override
	public int selectReturnInspListCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspListCnt(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnInspList(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnInspList(params);
	}

	@Override
	public int selectReturnSendOutListCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnSendOutListCnt(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnSendOutList(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnSendOutList(params);
	}

	@Override
	public int selectReturnDiscardListCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnDiscardListCnt(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnDiscardList(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnDiscardList(params);
	}

	@Override
	public InvUserInfoVO selectAttnInfo(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectAttnInfo(userId);
	}

	@Override
	public ReturnRequestVO selectCurrentInfo(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectCurrentInfo(userId);
	}

	@Override
	public int selectCurrentInfoCnt(String userId) {
		// TODO Auto-generated method stub
		return mapper.selectCurrentInfoCnt(userId);
	}

	@Override
	public ReturnRequestVO selectReturnOrder(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnOrder(params);
	}

	@Override
	public ArrayList<ReturnItemVO> selectReturnOrderItem(String nno) {
		// TODO Auto-generated method stub
		return mapper.selectReturnOrderItem(nno);
	}

	@Override
	public int selectAciOrderCnt(String koblNo) {
		// TODO Auto-generated method stub
		return mapper.selectAciOrderCnt(koblNo);
	}

	@Override
	public String selectReturnNno(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectReturnNno(params);
	}

	@Override
	public ArrayList<ReturnItemVO> selectCusItemCode(String nno) {
		// TODO Auto-generated method stub
		return mapper.selectCusItemCode(nno);
	}

	@Override
	public void insertCusItemCodeInfo(HashMap<String, Object> params2) {
		// TODO Auto-generated method stub
		mapper.insertCusItemCodeInfo(params2);
	}

	@Override
	public int selectItemCode(HashMap<String, Object> params2) {
		// TODO Auto-generated method stub
		return mapper.selectItemCode(params2);
	}

	@Override
	public String selectDstnNation(String nno) {
		// TODO Auto-generated method stub
		return mapper.selectDstnNation(nno);
	}

	@Override
	public String selectReturnOrderReference(String nno) {
		// TODO Auto-generated method stub
		return mapper.selectReturnOrderReference(nno);
	}

}