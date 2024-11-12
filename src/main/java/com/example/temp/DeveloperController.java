package com.example.temp;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.temp.common.encryption.AES256Cipher;

@Controller
public class DeveloperController {
	
	
	@RequestMapping(value = "/dev/getEncryptData", method = RequestMethod.GET)
	public String devGetEncryptedData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String userId = "itsel2";
		String apiKey = "LoFp8L61598LUYP9sUiarUS8EwG28s1M";
		
		Date today = new Date();
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(today);
		String encrytedApiKey = AES256Cipher.AES_Encode(date+"|"+userId, apiKey);
		
		
		model.addAttribute("encrytedKey", encrytedApiKey);
		model.addAttribute("userId", userId);
		model.addAttribute("apiKey", apiKey);
		
		return "dev/dev1";
	}
	
}
