package com.example.temp.manager.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.temp.manager.vo.AramexListVO;


@Service
public interface ManagerExcelService {

	String excelUediDown(String mawbNo, HttpServletResponse response, HttpServletRequest request) throws Exception;

	String rusExcelDown(String parameter, HttpServletResponse response, HttpServletRequest request) throws Exception;
}
