package com.example.temp.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.example.temp.common.vo.ComnVO;
import com.google.gson.Gson;

@Service
public class CommonUtils {
	
	
	@Value("${filePath}")
	String realFilePath;

	
	public HashMap<String, String> apiPost(String apiUrl, String requestBody, HashMap<String, String> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();
		
		try {
			
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			long startTime = System.currentTimeMillis();
			
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(8000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestBody);
			wr.flush();
			
			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
			} else {
				
				returnMap.put("status", "fail");
				returnMap.put("status_msg", "API Response Code is " + conn.getResponseCode());
				
			}
			
			long endTime = System.currentTimeMillis();
			
			long responseTime = endTime - startTime;
			
			System.out.println("Response Time: " + responseTime + " ms");
			
			String headers = "";
			Map headerFields = conn.getHeaderFields();
			Set<String> keys = headerFields.keySet();
			for (String key : keys) {
				headers += key + " : " + conn.getHeaderField(key) + ", ";
			}

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
	}
	
	
	public HashMap<String, String> apiGet(String apiUrl, HashMap<String, String> apiHeaders) {
		HashMap<String, String> returnMap = new HashMap<>();
		
		try {
			
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoOutput(false);
			
			if (apiHeaders != null && !apiHeaders.isEmpty()) {
				for (String key : apiHeaders.keySet()) {
					Object value = apiHeaders.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}

			StringBuilder builder = new StringBuilder();
			
			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				
				br.close();
				
				String responseBody = builder.toString();
				returnMap.put("status", "success");
				returnMap.put("status_msg", "");
				returnMap.put("responseBody", responseBody);
				
			} else {
				
				returnMap.put("status", "fail");
				returnMap.put("status_msg", "API Response Code is " + conn.getResponseCode());
				
			}
			
			String headers = "";
			Map headerFields = conn.getHeaderFields();
			Set<String> keys = headerFields.keySet();
			for (String key : keys) {
				headers += key + " : " + conn.getHeaderField(key) + ", ";
			}

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			returnMap.put("status", "fail");
			returnMap.put("status_msg", "Invalid URL format : " + e.getMessage());
	    } catch (SocketTimeoutException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Connection Timeout : " + e.getMessage());
	    } catch (IOException e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "I/O Exception : " + e.getMessage());
	    } catch (Exception e) {
	    	returnMap.put("status", "fail");
			returnMap.put("status_msg", "Exception : " + e.getMessage());
	    }
		
		return returnMap;
	}
	

	public String convertStringToMap(HashMap<String, Object> map) {
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	
	public String getDateTime(String fmtStr) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(fmtStr);
		String dateTime = formatter.format(date);
		return dateTime;
	}
	
	
	public HashMap<String, String> buildApiDefaltHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		return headers;
	}
	
	
	public File getPdfFileFromLabelUrl(String nno, String labelUrl) {
		
		File pdfFile = null;
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			String filePath = realFilePath + "pdf/" + nno + ".pdf";
			pdfFile = new File(filePath);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("User-Agent", "SpringBootApp/2.2.6 (Java/1.8.0_91)");
			
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<byte[]> response = restTemplate.exchange(labelUrl, HttpMethod.GET, entity, byte[].class);
			byte[] pdfBytes = response.getBody();
			
			try (InputStream is = new ByteArrayInputStream(pdfBytes);
					OutputStream os = new FileOutputStream(pdfFile)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				
				while ((bytesRead = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			}

			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return pdfFile;
	}
	
	
	public void storePdfFileToBucket(File pdfFile) {
		
		String fileName = pdfFile.getName();
		System.out.println(fileName);
		String nno = "";
		
		if (fileName.indexOf(".") > 0) {
			nno = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println(nno);
		}
		
		if (!nno.isEmpty()) {
			
			String year = nno.substring(0,4);
			String date = nno.substring(4,8);
			System.out.println(year + " / " + date);

			AWSCredentials awsCredentials = new BasicAWSCredentials(ComnVO.getAccessKey(), ComnVO.getSecretKey());
			AmazonS3 awsS3 = AmazonS3ClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
						.withRegion(Regions.AP_NORTHEAST_2)
						.build();
			PutObjectResult putObjectResult = new PutObjectResult();
			
			String bucketPath = ComnVO.getBucketName() + "/outbound/hawb/" + year + "/" + date;
			System.out.println(bucketPath); 
			
			if (awsS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketPath, nno, pdfFile);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				putObjectResult = awsS3.putObject(putObjectRequest);
			}
			
			awsS3 = null;
		}
		
		System.out.println(pdfFile.exists());

		if (pdfFile.exists()) {
			System.out.println("here");
			pdfFile.delete();
		}
	}
	
	
	public void storeLabelFile(Order order) {
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			String nno = order.getNno();
			String labelUrl = order.getLabelUrl();
			String year = nno.substring(0,4);
			String date = nno.substring(4,8);
			
			String filePath = realFilePath + "pdf/" + nno + ".pdf";
			File pdfFile = new File(filePath);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("User-Agent", "SpringBootApp/2.2.6 (Java/1.8.0_91)");
			
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			ResponseEntity<byte[]> response = restTemplate.exchange(labelUrl, HttpMethod.GET, entity, byte[].class);
			byte[] pdfBytes = response.getBody();
			
			try (InputStream inputStream = new ByteArrayInputStream(pdfBytes);
		             OutputStream outputStream = new FileOutputStream(pdfFile)) {
		            byte[] buffer = new byte[1024];
		            int bytesRead;
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }
		        }
			
			
			AWSCredentials awsCredentials = new BasicAWSCredentials(ComnVO.getAccessKey(), ComnVO.getSecretKey());
			AmazonS3 awsS3 = AmazonS3ClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
						.withRegion(Regions.AP_NORTHEAST_2)
						.build();
			PutObjectResult putObjectResult = new PutObjectResult();
			
			String bucketPath = ComnVO.getBucketName() + "/outbound/hawb/" + year + "/" + date;
			System.out.println(bucketPath); 
			
			if (awsS3 != null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketPath, nno, pdfFile);
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				putObjectResult = awsS3.putObject(putObjectRequest);
			}
			
			awsS3 = null;
			
			if (pdfFile.exists()) {
				pdfFile.delete();
			}
			
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}


	public void getLabelFromBucket(HttpServletRequest request, HttpServletResponse response, ArrayList<String> orderNnoList) {
		
		try {

			PDFMergerUtility pdfMerger = new PDFMergerUtility();
			
			AWSCredentials awsCredentials = new BasicAWSCredentials(ComnVO.getAccessKey(), ComnVO.getSecretKey());
			AmazonS3 awsS3 = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
					.withRegion(Regions.AP_NORTHEAST_2)
					.build();
			
			for (String nno : orderNnoList) {
				String year = nno.substring(0,4);
				String date = nno.substring(4,8);
				String key = "outbound/hawb/" + year + "/" + date + "/" + nno;
				
				S3Object s3Object = awsS3.getObject("img.mtuai.com", key);
				
				InputStream inputStream = s3Object.getObjectContent();
                
                pdfMerger.addSource(inputStream);
			}
			
			response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=printLabel.pdf");

            pdfMerger.setDestinationStream(response.getOutputStream());
            pdfMerger.mergeDocuments(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public double getConvertWeightKg(double value, String unit) {
		double weight = 0;
		if ("G".equals(unit)) {
			weight = value*0.001;
		} else if ("LB".equals(unit)) {
			weight = value*0.453592;
		} else if ("OZ".equals(unit)) {
			weight = value/35.274;
		} else {
			weight = value;
		}
		
		return weight;
	}
	
	
	public String encodeToBase64(Object input) throws IOException {
		
		if (input instanceof File) {
			return encodeFileToBase64((File) input);
		} else if (input instanceof String) {
			return encodeStringToBase64((String) input);
		} else {
			throw new IllegalArgumentException("지원하지 않는 타입입니다.");
		}
	}
	
	
	private String encodeFileToBase64(File file) throws IOException {
		FileInputStream fis = null;
		byte[] fileBytes = null;
		
		try {
			fis = new FileInputStream(file);
			fileBytes = new byte[(int) file.length()];
			fis.read(fileBytes);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (fis != null) {
				fis.close();
			}	
		}
		
		return Base64.getEncoder().encodeToString(fileBytes);
	}
	
	private String encodeStringToBase64(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}
}
