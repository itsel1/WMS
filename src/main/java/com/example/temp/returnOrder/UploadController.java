package com.example.temp.returnOrder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.temp.api.aci.service.ApiV1ReturnService;
import com.example.temp.common.vo.ComnVO;
import com.example.temp.manager.util.AES256Cipher;

@Controller
public class UploadController {

	String key = "07412cV8xX8MlRtA9b5lKTy2zXuM4CmO";

	@Autowired
	ApiV1ReturnService rtnService;
	
	// test
	@Value("${filePath}")
	String realFilePath;
	ComnVO comnS3Info;
	// private AmazonS3 amazonS3;

	// upload
	
	@PostMapping("/uploadMulti")
	public String multiFileUpload(@RequestParam("files") MultipartFile[] files) throws Exception {

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			
			try {
				String filename = file.getOriginalFilename();
				String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
				int i = -1;
				i = filename.lastIndexOf(".");
				String realFileName = now + filename.substring(i, filename.length());
				System.out.println("filename -> " +filename);
				System.out.println("realFilename ->" + realFileName);
				
				AES256Cipher aes = new AES256Cipher();
				FileOutputStream fos = new FileOutputStream(realFilePath+file.getOriginalFilename());
				InputStream is = file.getInputStream();
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while ((readCount = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}
				
				is.close();
				fos.close();
				
				aes.aes256Encode(realFilePath, filename, realFileName, key);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return "return/uploadMulti";
	}

	@GetMapping("/uploadMultiPage")
	public String uploadMultiPage() {
		return "return/uploadMulti";
	}

	// download
	@GetMapping("/downloadMultiPage")
	public String fileDownload( String url,HttpServletRequest request,HttpServletResponse response) throws Exception {

		InputStream in = null;
		OutputStream os = null;
		String urlNum = url.substring(75);
		//System.out.println(url.substring(75));
		String storedFileName = url.substring(url.lastIndexOf("m")+1);
		int f = url.lastIndexOf('.');
		String fileExtension = url.substring(f+1, url.length());
		//download(storedFileName);
		//getObject(storedFileName);
		
		AES256Cipher aes = new AES256Cipher();
		aes.aes256Decode(realFilePath, urlNum, urlNum+"."+fileExtension, key);
		return "redirect:/mngr/aplctList/return/returnList";
		
		
		//return "/s3download";
		
	}
	
	/*
	public ResponseEntity<byte[]> download(String filekey) throws Exception {
		return getObject("/returnFiles/32835339305/32835339305_20220214173240873.txt");
		
	}*/
	
	//@GetMapping("/s3download")
	public ResponseEntity<byte[]> getObject(String storedFileName) throws Exception {
		//String storedFileName = url.substring(url.lastIndexOf("m")+1);
		
		AWSCredentials awsCredentials = new BasicAWSCredentials(comnS3Info.getAccessKey(), comnS3Info.getSecretKey());
		AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
		
		S3Object object = amazonS3.getObject(new GetObjectRequest(comnS3Info.getBucketName(), storedFileName));
		S3ObjectInputStream objectInputStream = object.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(objectInputStream);
		
		String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", fileName);
		
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
		
	}

}
