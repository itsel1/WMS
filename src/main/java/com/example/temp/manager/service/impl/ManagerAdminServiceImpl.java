package com.example.temp.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.temp.manager.mapper.ManagerAdminMapper;
import com.example.temp.manager.service.ManagerAdminService;
import com.example.temp.manager.vo.AdminVO;
import com.example.temp.manager.vo.FastboxUserInfoVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.SendVO;
import com.example.temp.manager.vo.YslExcelVO;

@Service
public class ManagerAdminServiceImpl implements ManagerAdminService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ManagerAdminMapper mapper;
	
	// get List
	@Override
	public int getTotalUserCnt(HashMap<String, Object> map) {
		return mapper.getTotalUserCnt(map);
	}

	@Override
	public ArrayList<AdminVO> getUserData(HashMap<String, Object> map) {
		ArrayList<AdminVO> adminVO = new ArrayList<AdminVO>();
		adminVO = mapper.getUserData(map);
		return adminVO;
		//return mapper.getUserData(map);
	}


	@Override
	public int selectUserCnt(String adminId) {
		return mapper.selectUserCnt(adminId);
	}

	@Override
	public void insertAdminInfos(AdminVO adminVO) {
		if (adminVO.getAdminId() != null || !adminVO.getAdminId().equals(null)) {
			mapper.insertAdminInfos(adminVO);
		}
	}

	@Override
	public void deleteUserInfo(String targetParm) {
		String[] targets = targetParm.split(",");
		for (int roop = 0; roop < targets.length; roop++) {
			mapper.deleteUserInfo(targets[roop]);
		}
		
	}

	@Override
	public AdminVO selectAdminInfo(String adminId) {
		AdminVO adminVO = new AdminVO();
		adminVO = mapper.getSelectAdminInfo(adminId);
		
		return adminVO;
	}

	@Override
	public void updateAdminInfo(AdminVO adminVO, HttpServletRequest request) {

		if (adminVO.getAdminId() != null || !adminVO.getAdminId().equals(null)) {
			mapper.updateAdminInfo(adminVO);
		}
		
	}

	@Override
	public int selectAdminListCnt(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectAdminListCnt(params);
	}

	@Override
	public ArrayList<AdminVO> selectAdminList(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectAdminList(params);
	}

	@Override
	public void resetAdminPw(AdminVO adminVO) {
		mapper.resetAdminPw(adminVO);
		
	}

	@Override
	public void yslExcelDown(YslExcelVO yslExcelVO, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			ArrayList<YslExcelVO> list = mapper.selectYslExcelList(yslExcelVO);
		
			
			String filePath = request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/jsp/adm/member/ysl_data_excel.xlsx";
			
			FileInputStream fis = new FileInputStream(filePath);
			
			Row row = null;
			Cell cell = null;
			
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheetAt(0);
			
			int cellNo = 0;
			int rowNo = 1;
			
			for (int i = 0 ; i < list.size(); i++) {
				row = sheet.createRow(rowNo);
				
				list.get(i).dncryptData();
				
				cellNo = 0;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getHawbNo());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getOrderDate());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getOrderNo());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getItemDetail());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getBoxCnt());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getUserWta());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeName());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeAddr());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeAddrDetail());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeCity());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeCntry());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeState());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getNativeCneeAddr());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getNativeCneeAddrDetail());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeTel());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getCneeZip());
				
				cellNo++;
				cell = row.createCell(cellNo);
				cell.setCellValue(list.get(i).getDstnNation());
				
				
				rowNo++;
				
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + "용성 출고 데이터" + ".xlsx");
			
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
	}

	@Override
	public int selectFastboxUserListCnt(HashMap<String, Object> params) throws Exception {
		return mapper.selectFastboxUserListCnt(params);
	}

	@Override
	public ArrayList<FastboxUserInfoVO> selectFastboxUserList(HashMap<String, Object> params) throws Exception {
		return mapper.selectFastboxUserList(params);
	}

	@Override
	public ArrayList<HashMap<String, Object>> selectUserList() throws Exception {
		return mapper.selectUserList();
	}

	@Override
	public FastboxUserInfoVO selectUserInfo(String userId) throws Exception {
		return mapper.selectUserInfo(userId);
	}

	@Override
	public void insertFastboxUserInfo(FastboxUserInfoVO userInfo) throws Exception {
		mapper.insertFastboxUserInfo(userInfo);
	}

	@Override
	public FastboxUserInfoVO selectFastboxUserInfo(HashMap<String, Object> params) throws Exception {
		return mapper.selectFastboxUserInfo(params);
	}

	@Override
	public void updateFastboxUserInfo(FastboxUserInfoVO userInfo) throws Exception {
		mapper.updateFastboxUserInfo(userInfo);
	}


	
	
}