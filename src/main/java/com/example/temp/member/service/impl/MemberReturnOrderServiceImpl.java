package com.example.temp.member.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.service.ComnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.vo.NationVO;
import com.example.temp.member.mapper.MemberReturnOrderMapper;
import com.example.temp.member.service.MemberReturnOrderService;
import com.example.temp.member.vo.ReturnOrderListVO;
import com.example.temp.member.vo.UserOrderItemVO;
import com.example.temp.member.vo.UserOrderListVO;
import com.example.temp.member.vo.UserVO;
import com.google.gson.Gson;

import oracle.security.crypto.core.AES;

@Service
@Transactional
public class MemberReturnOrderServiceImpl implements MemberReturnOrderService {
	@Autowired
	private MemberReturnOrderMapper mapper;
	
	@Value("${filePath}")
	String realFilePath;
	
	ComnVO comnS3Info;
	private AmazonS3 amazonS3;
	
	@Autowired
	ComnService comnService;

	@Override
	public ArrayList<NationVO> selectDestinationCode(HashMap<String, Object> params) throws Exception {
		return mapper.selectDestinationCode(params);
	}

	@Override
	public UserOrderListVO selectOrderList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectOrderList(parameters);
	}

	@Override
	public ArrayList<UserOrderItemVO> selectOrderItemList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectOrderItemList(parameters);
	}

	@Override
	public int selectOrderListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectOrderListCnt(parameters);
	}
	
	@Override
	public void insertReturnOrderInfoProc(HttpServletRequest request, MultipartHttpServletRequest multi, ReturnOrderListVO order) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		int itemCnt = request.getParameterValues("itemDetail").length;
		String nno = comnService.selectNNO();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String userIp = request.getRemoteAddr();
		parameters.put("nno", nno);
		parameters.put("userId", userId);
		
		try {
			if (order.getTaxType().equals("Y")) {
				resMap = filesUpload(multi, parameters);
			}
			order.setNno(nno);
			if (order.getDstnNation().equals("US")) {
				order.setDstnStation("213");
			}
			
			if (order.getPickType().equals("A")) {
				if (!order.getTrkNo().equals("")) {
					order.setState("B001");
				} else {
					order.setState("A002");
				}
			} else {
				order.setState("A001");
			}
			
			order.setOrderReference(nno);
			order.setCalculateId(userId);
			order.setUserId(userId);
			order.setWUserId(userId);
			order.setWUserIp(userIp);
			order.setCdRemark("");
			order.setTaxReturn("C");
			order.encrypData();
			
			mapper.insertReturnOrderList(order);
			mapper.insertReturnOrderStateLog(order);
			
			for (String key : resMap.keySet()) {
				if (key.equals("fileReason")) {
					parameters.put("fileReason", resMap.get(key));
				} else if (key.equals("fileCapture")) {
					parameters.put("fileCapture", resMap.get(key));
				} else if (key.equals("fileMessenger")) {
					parameters.put("fileMessenger", resMap.get(key));
				} else if (key.equals("fileComm")) {
					parameters.put("fileComm", resMap.get(key));
				} else if (key.equals("fileBank")) {
					parameters.put("fileBank", resMap.get(key));
				} else if (key.equals("fileLicense")) {
					parameters.put("fileLicense", resMap.get(key));
				}
			}
			
			if (parameters.get("fileReason") != null) {
				mapper.insertReturnFileList(parameters);	
			}
			
			
			for (int i = 0; i < itemCnt; i++) {
				HashMap<String, Object> itemInfo = new HashMap<String, Object>();
				itemInfo.put("nno", nno);
				itemInfo.put("orgStation", order.getOrgStation());
				itemInfo.put("userId", userId);
				itemInfo.put("subNo", i+1);
				itemInfo.put("itemDetail", request.getParameterValues("itemDetail")[i]);
				itemInfo.put("hsCode", request.getParameterValues("hsCode")[i]);
				itemInfo.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);
				itemInfo.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
				itemInfo.put("itemCnt", request.getParameterValues("itemCnt")[i]);
				itemInfo.put("unitValue", request.getParameterValues("unitValue")[i]);
				itemInfo.put("brand", request.getParameterValues("brand")[i]);
				itemInfo.put("makeCntry", request.getParameterValues("makeCntry")[i]);
				itemInfo.put("makeCom", request.getParameterValues("makeCom")[i]);
				itemInfo.put("itemWta", request.getParameterValues("itemWta")[i]);
				itemInfo.put("wtUnit", request.getParameterValues("wtUnit")[i]);
				itemInfo.put("itemUrl", request.getParameterValues("itemUrl")[i]);
				itemInfo.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
				itemInfo.put("wUserId", userId);
				itemInfo.put("wUserIp", userIp);
				mapper.insertReturnItemList(itemInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mapper.deleteReturnOrderInfo(parameters);
		}
	}

	public Map<String, String> filesUpload(MultipartHttpServletRequest multi, HashMap<String, Object> parameters) throws Exception {
		Map<String, String> resMap = new HashMap<String, String>();
		
		try {
			String nno = parameters.get("nno").toString();
			String userId = parameters.get("userId").toString();
			String filePath = realFilePath + "excel/tmpOrder";
			File dir = new File(filePath);
			if (!dir.isDirectory()) {
				dir.mkdir();
			}
			
			Iterator<String> files = multi.getFileNames();
			String fileName = "";
			
			while(files.hasNext()) {
				String newFileName = "";
				String exten = "";
				String uploadFile = files.next();
				MultipartFile mFile = multi.getFile(uploadFile);
				fileName = mFile.getOriginalFilename();
				int idx = fileName.lastIndexOf(".");
				
				if (idx > 0) {
					exten = fileName.substring(idx+1);
					if (uploadFile.equals("fileReason")) {
						newFileName = nno+"_FR."+exten;
					} else if (uploadFile.equals("fileCapture")) {
						newFileName = nno+"_FC."+exten;
					} else if (uploadFile.equals("fileMessenger")) {
						newFileName = nno+"_FM."+exten;
					} else if (uploadFile.equals("fileComm")) {
						newFileName = nno+"_FC."+exten;
					} else if (uploadFile.equals("fileBank")) {
						newFileName = nno+"_FB."+exten;
					} else if (uploadFile.equals("fileLicense")) {
						newFileName = nno+"_FL."+exten;
					}
					
					try {
						mFile.transferTo(new File(filePath+newFileName));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					File upFile = new File(filePath+newFileName);
					AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
					amazonS3 = new AmazonS3Client(awsCredentials);
					PutObjectResult asssd = new PutObjectResult();
					Calendar c = Calendar.getInstance();
					String year = String.valueOf(c.get(Calendar.YEAR));
					if(amazonS3 != null) {
						PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/return/"+year+"/"+userId+"/"+nno, newFileName, upFile);
						putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
						asssd = amazonS3.putObject(putObjectRequest);
						String amazonPath = "http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+newFileName;
						resMap.put(uploadFile, amazonPath);
					}
					amazonS3 = null;
					upFile.delete();
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	@Override
	public Map<String, String> filesUpload(MultipartHttpServletRequest multi, String nno, String userId) throws Exception {
		String path = realFilePath + "excel/tmpOrder";
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		Iterator<String> files = multi.getFileNames();
		Map<String, String> resMap = new HashMap<String, String>();
		String fileName = "";
		
		while(files.hasNext()) {
			String newFileName = "";
			String exten = "";
			String uploadFile = files.next();
			MultipartFile mFile = multi.getFile(uploadFile);
			fileName = mFile.getOriginalFilename();
			int idx = fileName.lastIndexOf(".");
			if (idx > 0) {
				exten = fileName.substring(idx+1);
				if (uploadFile.equals("fileReason")) {
					newFileName = nno+"_FR."+exten;
				} else if (uploadFile.equals("fileCapture")) {
					newFileName = nno+"_FC."+exten;
				} else if (uploadFile.equals("fileMessenger")) {
					newFileName = nno+"_FM."+exten;
				} else if (uploadFile.equals("fileComm")) {
					newFileName = nno+"_FCL."+exten;
				} else if (uploadFile.equals("fileBank")) {
					newFileName = nno+"_FCB."+exten;
				} else if (uploadFile.equals("fileLicense")) {
					newFileName = nno+"_FL."+exten;
				}
				
				try {
					mFile.transferTo(new File(path+newFileName));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				File upFile = new File(path+newFileName);
				AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
				amazonS3 = new AmazonS3Client(awsCredentials);
				PutObjectResult asssd = new PutObjectResult();
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
				if(amazonS3 != null) {
					PutObjectRequest putObjectRequest = new PutObjectRequest(comnS3Info.getBucketName()+"/outbound/return/"+year+"/"+userId+"/"+nno, newFileName, upFile);
					putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
					asssd = amazonS3.putObject(putObjectRequest);
					String amazonPath = "http://img.mtuai.com/outbound/return/"+year+"/"+userId+"/"+nno+"/"+newFileName;
					resMap.put(uploadFile, amazonPath);
				}
				amazonS3 = null;
				upFile.delete();
			}
		}
		
		return resMap;
	}

	@Override
	public void insertReturnOrderList(ReturnOrderListVO returnOrder, Map<String, String> resMap) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nno", returnOrder.getNno());
	
		for (String key : resMap.keySet()) {
			if (key.equals("fileReason")) {
				params.put("fileReason", resMap.get(key));
			} else if (key.equals("fileCapture")) {
				params.put("fileCapture", resMap.get(key));
			} else if (key.equals("fileMessenger")) {
				params.put("fileMessenger", resMap.get(key));
			} else if (key.equals("fileComm")) {
				params.put("fileComm", resMap.get(key));
			} else if (key.equals("fileBank")) {
				params.put("fileBank", resMap.get(key));
			} else if (key.equals("fileLicense")) {
				params.put("fileLicense", resMap.get(key));
			}
		}
		try {
			mapper.insertReturnOrderList(returnOrder);
			mapper.insertReturnFileList(params);
		} catch (Exception e) {
			throw new Exception();
			//e.printStackTrace();
		}
	}

	@Override
	public void insertReturnItemList(HashMap<String, Object> itemMap) throws Exception {
		mapper.insertReturnItemList(itemMap);
	}

	@Override
	public void deleteReturnOrderInfo(HashMap<String, Object> params) throws Exception {
		mapper.deleteReturnOrderInfo(params);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnOrderList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnOrderList(parameters);
	}

	@Override
	public int selectReturnOrderListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnOrderListCnt(parameters);
	}

	@Override
	public ReturnOrderListVO selectReturnOrderInfo(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnOrderInfo(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnItemList(HashMap<String, Object> parameters)
			throws Exception {
		return mapper.selectReturnItemList(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectReturnStateList() throws Exception {
		return mapper.selectReturnStateList();
	}

	@Override
	public int selectHawbCheck(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectHawbCheck(parameters);
	}

	@Override
	public UserVO selectUserEshopInfo(HashMap<String, Object> params) throws Exception {
		return mapper.selectUserEshopInfo(params);
	}

	@Override
	public HashMap<String, Object> getEshopBlCheck(HttpServletRequest request) throws Exception {
		HashMap<String, Object> rst = new HashMap<String, Object>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String bl = request.getParameter("bl");
		String userId = request.getSession().getAttribute("USER_ID").toString();
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		userInfo = mapper.selectEshopApiInfo(userId);
		String eshopId = userInfo.get("eshopId").toString().toUpperCase();
		String eshopApiKey = userInfo.get("eshopApiKey").toString();
		
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		dataOne.put("HawbNo", bl);
		dataList.add(dataOne);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(dataList);
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		String dateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
		String aciKey = "";
		
		try {
			aciKey = AES256Cipher.AES_Encode(eshopId+"|"+dateTime, eshopApiKey);
			
			URL url = new URL("https://acieshop.com/eshopapi/getBlCheck.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setRequestProperty("UserID", eshopId);
			conn.setRequestProperty("ACIKey", aciKey);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String result = outResult.toString();
		
		JSONObject json = new JSONObject(String.valueOf(result));
		try {
			if (!json.get("Status").toString().equals("A10")) {
				throw new Exception();
			} else {
				rst.put("code", "S");
				rst.put("msg", "success");
			}
			
		} catch (Exception e) {
			String msg = json.get("ErrMsg").toString();
			rst.put("code", "F");
			rst.put("msg", msg);
		}
		
		parameters.put("nno", "");
		parameters.put("connUrl", "/eshopapi/getBlCheck.php");
		parameters.put("headContents", "");
		parameters.put("bodyContents", jsonString);
		parameters.put("rstContents", result);
		parameters.put("wUserId", userId);
		parameters.put("wUserIp", request.getRemoteAddr());
		mapper.insertApiConn(parameters);
		
		return rst;
	}

	@Override
	public HashMap<String, Object> getEshopBlInfo(HashMap<String, Object> parameters) throws Exception {
		HashMap<String, Object> blInfo = new HashMap<String, Object>();
		UserOrderListVO orderList = new UserOrderListVO();
		ArrayList<UserOrderItemVO> itemList = new ArrayList<UserOrderItemVO>();
		String jsonResult = fnEshopBlInfo(parameters);
		try {
			JSONObject json = new JSONObject(String.valueOf(jsonResult));
			if (!json.get("Status").toString().equals("A10")) {
				throw new Exception();
			} else {
				System.out.println(json);
				JSONArray jsonArr = new JSONArray(String.valueOf(json.get("OrderInfo").toString()));
				if (jsonArr.length() > 0) {
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject orderInfo = (JSONObject) jsonArr.get(i);
						orderList.setOrderNo(orderInfo.optString("OrderNo"));
						orderList.setOrderDate(orderInfo.optString("OrderDate"));
						orderList.setShipperName(orderInfo.optString("ShipperName"));
						orderList.setShipperTel(orderInfo.optString("ShipperTel"));
						orderList.setShipperAddr(orderInfo.optString("ShipperAddr"));
						orderList.setCneeName(orderInfo.optString("CneeName"));
						orderList.setCneeTel(orderInfo.optString("CneeTel"));
						orderList.setCneeHp(orderInfo.optString("CneeHp"));
						orderList.setCneeZip(orderInfo.optString("CneeZip"));
						orderList.setCneeAddr(orderInfo.optString("CneeAddr"));
						orderList.setCneeAddrDetail(orderInfo.optString("CneeAddrDetail"));
						JSONArray itemArr = new JSONArray(String.valueOf(orderInfo.get("ItemList").toString()));
						for (int x = 0; x < itemArr.length(); x++) {
							JSONObject itemInfo = (JSONObject) itemArr.getJSONObject(x);
							UserOrderItemVO items = new UserOrderItemVO();
							if (itemInfo.get("Brand").toString().equals("null")) {
								items.setBrand("");
							} else {
								items.setBrand(itemInfo.get("Brand").toString());
							}
							if (itemInfo.get("MakeCntry").toString().equals("null")) {
								items.setMakeCntry("");
							} else {
								items.setMakeCntry(itemInfo.get("MakeCntry").toString());
							}
							if (itemInfo.get("MakeCom").toString().equals("null")) {
								items.setMakeCom("");
							} else {
								items.setMakeCom(itemInfo.get("MakeCom").toString());
							}
							
							items.setItemDetail(itemInfo.getString("ItemDetail"));
							items.setUnitValue(itemInfo.getString("UnitValue"));
							items.setHsCode(itemInfo.getString("HsCode"));
							items.setCusItemCode(itemInfo.getString("CusItemCode"));
							items.setItemCnt(itemInfo.getString("ItemCnt"));
							items.setItemImgUrl(itemInfo.getString("ItemImgUrl"));
							items.setUnitCurrency(itemInfo.getString("UnitCurrency"));
							
							itemList.add(items);
						}
					}
				}
				blInfo.put("itemList", itemList);
				blInfo.put("orderList", orderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return blInfo;
	}

	public String fnEshopBlInfo(HashMap<String, Object> parameters) throws Exception {
		ArrayList<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> dataOne = new LinkedHashMap<String, Object>();
		dataOne.put("HawbNo", parameters.get("hawbNo").toString());
		dataList.add(dataOne);
		Gson gson = new Gson();
		String jsonString = gson.toJson(dataList);
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		String dateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
		String aciKey = "";
		try {
			aciKey = AES256Cipher.AES_Encode(parameters.get("eshopId").toString()+"|"+dateTime, parameters.get("eshopApiKey").toString());
			
			URL url = new URL("https://acieshop.com/eshopapi/getBlInfo.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "ko-KR");
			conn.setRequestProperty("UserID", parameters.get("eshopId").toString());
			conn.setRequestProperty("ACIKey", aciKey);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outResult.toString();
	}

	@Override
	public HashMap<String, Object> selectReturnFileList(String nno) throws Exception {
		return mapper.selectReturnFileList(nno);
	}

	@Override
	public void updateReturnOrderInfoProc(HttpServletRequest request, MultipartHttpServletRequest multi,
			ReturnOrderListVO order) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		int itemCnt = request.getParameterValues("itemDetail").length;
		String nno = request.getParameter("nno");
		String userId = request.getParameter("userId");
		String wUserId = request.getSession().getAttribute("USER_ID").toString();
		String wUserIp = request.getRemoteAddr();
		parameters.put("nno", nno);
		parameters.put("userId", userId);
		parameters.put("wUserId", wUserId);
		parameters.put("wUserIp", wUserIp);
		
		try {
			if (order.getTaxType().equals("Y")) {
				resMap = filesUpload(multi, parameters);
			}
			
			order.setNno(nno);
			if (order.getDstnNation().equals("US")) {
				order.setDstnStation("213");
			}
			
			if (order.getState().equals("C002") || order.getState().equals("C003")) {
				order.setState(order.getState());
			} else {
				if (order.getPickType().equals("A")) {	// 직접전달
					if (!order.getTrkNo().equals("")) {
						order.setState("B001");
					} else {
						order.setState("A002");
					}
				} else {
					order.setState("A001");
				}	
			}
			
			
			order.setOrderType("RETURN");
			order.setWUserId(wUserId);
			order.setWUserIp(wUserIp);
			order.setCdRemark("");
			order.setTaxReturn("C");
			order.encrypData();
			
			int fileCnt = 0;
			
			for (String key : resMap.keySet()) {
				if (key.equals("fileReason")) {
					parameters.put("fileReason", resMap.get(key));
					fileCnt++;
				} else if (key.equals("fileCapture")) {
					parameters.put("fileCapture", resMap.get(key));
					fileCnt++;
				} else if (key.equals("fileMessenger")) {
					parameters.put("fileMessenger", resMap.get(key));
					fileCnt++;
				} else if (key.equals("fileComm")) {
					parameters.put("fileComm", resMap.get(key));
					fileCnt++;
				} else if (key.equals("fileBank")) {
					parameters.put("fileBank", resMap.get(key));
					fileCnt++;
				} else if (key.equals("fileLicense")) {
					parameters.put("fileLicense", resMap.get(key));
					fileCnt++;
				}
			}
			
			int fileYn = 0;
			if (fileCnt > 0) {
				fileYn = mapper.selectReturnFileCnt(parameters);
				if (fileYn > 0) {
					mapper.updateReturnFileList(parameters);	
				} else {
					mapper.insertReturnFileList(parameters);
				}
			}
			
			mapper.updateReturnOrderList(order);
			mapper.insertReturnOrderStateLog(order);
			mapper.deleteReturnItemList(order);
			for (int i = 0; i < itemCnt; i++) {
				HashMap<String, Object> itemInfo = new HashMap<String, Object>();
				itemInfo.put("nno", nno);
				itemInfo.put("orgStation", order.getOrgStation());
				itemInfo.put("userId", userId);
				itemInfo.put("subNo", i+1);
				itemInfo.put("itemDetail", request.getParameterValues("itemDetail")[i]);
				itemInfo.put("hsCode", request.getParameterValues("hsCode")[i]);
				itemInfo.put("nativeItemDetail", request.getParameterValues("nativeItemDetail")[i]);
				itemInfo.put("unitCurrency", request.getParameterValues("unitCurrency")[i]);
				itemInfo.put("itemCnt", request.getParameterValues("itemCnt")[i]);
				itemInfo.put("unitValue", request.getParameterValues("unitValue")[i]);
				itemInfo.put("brand", request.getParameterValues("brand")[i]);
				itemInfo.put("makeCntry", request.getParameterValues("makeCntry")[i]);
				itemInfo.put("makeCom", request.getParameterValues("makeCom")[i]);
				itemInfo.put("itemWta", request.getParameterValues("itemWta")[i]);
				itemInfo.put("wtUnit", request.getParameterValues("wtUnit")[i]);
				itemInfo.put("itemUrl", request.getParameterValues("itemUrl")[i]);
				itemInfo.put("itemImgUrl", request.getParameterValues("itemImgUrl")[i]);
				itemInfo.put("cusItemCode", request.getParameterValues("cusItemCode")[i]);
				itemInfo.put("wUserId", wUserId);
				itemInfo.put("wUserIp", wUserIp);
				mapper.updateReturnItemList(itemInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteReturnOrderAllList(HashMap<String, Object> rst) throws Exception {
		mapper.deleteReturnOrderAllList(rst);
	}

	@Override
	public String selectCustomerEshopInfoYn(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectCustomerEshopInfoYn(parameters);
	}

	@Override
	public int selectReturnStockListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnStockListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnStockList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnStockList(parameters);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectWhInImageList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectWhInImageList(parameters);
	}

	@Override
	public void insertReturnOrderStateLog(ReturnOrderListVO orderVo) throws Exception {
		try {
			mapper.insertReturnOrderStateLog(orderVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int selectReturnWhoutListCnt(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnWhoutListCnt(parameters);
	}

	@Override
	public ArrayList<ReturnOrderListVO> selectReturnWhoutList(HashMap<String, Object> parameters) throws Exception {
		return mapper.selectReturnWhoutList(parameters);
	}

	@Override
	public int selectTmpOrderChk(String nno) throws Exception {
		return mapper.selectTmpOrderChk(nno);
	}

	@Override
	public void returnOrderExcelFormDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/jsp/user/returnOrder/excelsample/";
	    String filename = "return_sample.xlsx";
	    
	    InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	    
	    try{
	        // 파일을 읽어 스트림에 담기
	        try{
	            file = new File(filePath, filename);
	            in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }
	
	        client = request.getHeader("User-Agent");
	 
	        // 파일 다운로드 헤더 지정
	        response.reset() ;
	        response.setContentType("ms-vnd/excel");
		    response.setHeader("Content-Disposition", "attachment;filename=return_sample.xlsx");

	        if(!skip){

	            // IE
	            if(client.indexOf("MSIE") != -1){
	                response.setHeader ("Content-Disposition", "attachment; filename="+new String(filename.getBytes("KSC5601"),"ISO8859_1"));
	 
	            }else{
	                // 한글 파일명 처리
	            	filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
	
	                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	            } 
	             
	            response.setHeader ("Content-Length", ""+file.length() );
	
	            os = response.getOutputStream();
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;
	             
	            while( (leng = in.read(b)) > 0 ){
	                os.write(b,0,leng);
	            }
	 
	        }else{
	            response.setContentType("text/html;charset=UTF-8");
	        }
	         
	        in.close();
	        os.close();
	 
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    
	}

	@Override
	public UserVO selectUserComInfo(String userId) throws Exception {
		return mapper.selectUserComInfo(userId);
	}

}
