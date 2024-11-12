package com.example.temp.smtp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazonaws.regions.AwsSystemPropertyRegionProvider;
import com.example.temp.api.aci.service.ApiService;
import com.example.temp.api.shipment.ApiAction;
import com.example.temp.api.shipment.company.YongSung;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.manager.service.ManagerService;
import com.example.temp.manager.vo.ExpLicenceExcelVO;
import com.example.temp.manager.vo.ManagerVO;
import com.example.temp.trans.efs.EfsAPI;
import com.example.temp.trans.fastbox.FastboxAPI;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.yongsung.YongSungAPI;
import com.google.gson.Gson;

@Controller
@Service
public class SmtpService {
	@Autowired
	YongSungAPI yslApi;
	
	@Autowired
	EfsAPI efsApi;
	
	@Autowired
	FedexAPI fedexApi;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	FastboxAPI fastBoxApi;
	
	@Autowired
	ManagerService mgrService;
	
	@Value("${filePath}")
    String realFilePath;
	
	@Value("${smtpStatus}")
    String smtpStatus;
	
	@Value("${googlePw}")
    String googlePw;
	
	@Autowired
	YongSung yongsung;
	
	@Autowired
	ComnMapper comnMapper;
	
	
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 15 07 * * *")
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(value = "/api/smtp/sendMail", method=RequestMethod.GET)
	public void sendMail() throws AddressException, MessagingException, Exception { 
		System.out.println("smtpStart");
		if(smtpStatus.equals("off")) {
			System.out.println("smtpEnd");
			return;
		}
		ArrayList<ViewYslItemCode> viewList = new ArrayList<ViewYslItemCode>();
		apiService.insertYslItemCode();
		viewList = apiService.selectViewYslItem();
		if(viewList.size()>0) {
			try { 
				System.out.println("smtpTryStart");
				
				
				String savePath = realFilePath + "excel/expLicence/";
				LocalDate currentDate = LocalDate.now();
				String filename = "JKJ ITEM MASTER_ACI("+currentDate+").xlsx" ;
				File xlsFile = new File(savePath+filename);
	//			apiService.selectUserKey("solugate");
				//Excel 생성
				Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
				Sheet sheet1 = xlsxWb.createSheet("Sheet1");
				
				//HEAD 스타일 설정 START
				CellStyle cellStyleHeader = xlsxWb.createCellStyle();
				cellStyleHeader.setWrapText(false);
				cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
				//HEAD 스타일 설정 END
				
				//ROW-CELL 선언 START
				Row row = null;
				Cell cell = null;
				int rowCnt = 0;
				//ROW-CELL 선언 END
				
				//HEADER 생성 START 작업중입니단
				row = sheet1.createRow(rowCnt);
				
				cell = row.createCell(0);
				cell.setCellValue("ITEM CODE");
				cell.setCellStyle(cellStyleHeader);
				
				cell = row.createCell(1);
				cell.setCellValue("SAGAWA CODE");
				cell.setCellStyle(cellStyleHeader);
				
				cell = row.createCell(2);
				cell.setCellValue("ITEM NAME(Eng)");
				cell.setCellStyle(cellStyleHeader);
				
				cell = row.createCell(3);
				cell.setCellValue("ITEM NAME(Jpy)");
				cell.setCellStyle(cellStyleHeader);
				
				cell = row.createCell(4);
				cell.setCellValue("ORIGIN");
				cell.setCellStyle(cellStyleHeader);
				
				cell = row.createCell(5);
				cell.setCellValue("images");
				cell.setCellStyle(cellStyleHeader);
				
				rowCnt++;
				//HEADER 생성 END
				
				for(int i=0; i<viewList.size();i++) {
					row = sheet1.createRow(rowCnt);
					cell = row.createCell(0);
					cell.setCellValue(viewList.get(i).getItemCode());
					
					cell = row.createCell(1);
					cell.setCellValue(viewList.get(i).getItemCode());
					
					cell = row.createCell(2);
					cell.setCellValue(viewList.get(i).getItemNameEng());
					
					cell = row.createCell(3);
					cell.setCellValue(viewList.get(i).getItemNameJp());
					
					cell = row.createCell(4);
					cell.setCellValue(viewList.get(i).getOrigin());
					
					cell = row.createCell(5);
					cell.setCellValue(viewList.get(i).getImgurl());
					
					rowCnt++;
				}
				
				try {
					
					FileOutputStream fileOut = new FileOutputStream(xlsFile);
					xlsxWb.write(fileOut);
				} catch (FileNotFoundException e) {
					System.out.println("smtpEnd");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("smtpEnd");
					e.printStackTrace();
				}
				
				// 메일 환경 변수 설정입니다.
				Properties props = new Properties();
				// 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
				props.setProperty("mail.transport.protocol", "smtp");
				// 메일 호스트 주소를 설정합니다.
				props.setProperty("mail.host", "smtp.gmail.com");
				// ID, Password 설정이 필요합니다.
				props.put("mail.smtp.auth", "true");
				// port는 465입니다.
				props.put("mail.smtp.port", "465");
				// ssl를 사용할 경우 설정합니다.
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.quitwait", "false");
				// id와 password를 설정하고 session을 생성합니다.
				Session session = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("aciexpress.it", googlePw);
					}
				});
				// 디버그 모드입니다.
				session.setDebug(true);
				// 메일 메시지를 만들기 위한 클래스를 생성합니다.
				MimeMessage message = new MimeMessage(session);
				// 송신자 설정
				message.setFrom(getAddress("aciexpress.it@gmail.com"));
				// 수신자 설정
				message.addRecipients(Message.RecipientType.TO, getAddresses("seungki1@yslogic.co.kr,kyh76@yslogic.co.kr"));
			//	message.addRecipients(Message.RecipientType.TO, getAddresses("itsel2@aciexpress.net"));
				//
				//message.addRecipients(Message.RecipientType.TO, getAddresses("itsel@aciexpress.net"));
				// 참조 수신자 설정
				message.addRecipients(Message.RecipientType.CC, getAddresses("itsel2@aciexpress.net,icnopr@aciexpress.net,overseas2@aciexpress.net,ebiz@aciexpress.net,ebiz2@aciexpress.net"));
//				message.addRecipients(Message.RecipientType.CC, getAddresses("neodong@aciexpress.net"));
				// 숨은 참조 수신자 설정
//			message.addRecipients(Message.RecipientType.BCC, getAddresses("nowonbun@gmail.com"));
				// 메일 제목을 설정합니다.
				message.setSubject(currentDate+" ACI 세관신고 파일 송부의 건");
				// 메일 내용을 설정을 위한 클래스를 설정합니다.
				message.setContent(new MimeMultipart());
				// 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
				Multipart mp = (Multipart) message.getContent();
				// html 형식으로 본문을 작성해서 바운더리에 넣습니다.
				mp.addBodyPart(getContents("<html><head></head><body>안녕하십니까 ACI 입니다.<br>표제의 건에 대하여 아래와 같이 신고자료 첨부하오니<br>업무에 참고하여 주시기 비랍니다.<br>이상입니다.</body></html>"));
				// 첨부 파일을 추가합니다.
				mp.addBodyPart(getFileAttachment(savePath+filename));
				// 이미지 파일을 추가해서 contextId를 설정합니다. contextId는 위 본문 내용의 cid로 링크가 설정 가능합니다.
//			mp.addBodyPart(getImage("capture.png", "image"));
				// 메일을 보냅니다.
				Transport.send(message);
				for(int i=0; i<viewList.size();i++) {
					apiService.updateYslItemCode(viewList.get(i).getItemCode());
				}
				System.out.println("smtpEnd");
				xlsFile.delete();
			} catch (Throwable e) {
				System.out.println("smtpEnd");
				e.printStackTrace();
			}
		}
	}
/*
	//@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 15 07 * * *")
	@Transactional(rollbackFor=Exception.class)
	public void weightUpdate() throws AddressException, MessagingException, Exception { 
		System.out.println("Ysl Weight Update Start");
		if(smtpStatus.equals("off")) {
			return;
		}
		
		//fedexApi.getTrackingFedex();
		//efsApi.getEfsWeight();
		yslApi.getYslWeight();
		//fastBoxApi.getFastboxWeight();
	}
	*/
	//@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 45 07 * * *")
	@Transactional(rollbackFor=Exception.class)
	public void weightUpdateFastbox() throws AddressException, MessagingException, Exception {
		System.out.println("Fastbox Weight Update Start");
		fastBoxApi.getFastboxWeight();
	}

	//@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 00,25,50 02 * * *")
	@Transactional(rollbackFor=Exception.class) 
	public void yslExplicenceThread() throws AddressException, MessagingException, Exception {
		System.out.println("Ysl Thread Start");
		if(smtpStatus.equals("off")) {
			return;
		}
		
		ArrayList<String> orderNnoList = new ArrayList<String> ();
		//orderNnoList = mgrService.selectExpLicenceYsl();
		orderNnoList = mgrService.selectExpNoYsl();
		
		if (orderNnoList.size() > 0) {
			for(int i =0; i < orderNnoList.size();i++) {
				HashMap<String, Object> orderInfo = new HashMap<String, Object>();
				orderInfo.put("nno", orderNnoList.get(i));
				yongsung.updateExportLicenseInfo(orderInfo);
				//yslApi.fnMakeYSUpdateExpLicenceNoJson(orderNnoList.get(i));
			}	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 00,25,50 08 * * *")
	@Transactional(rollbackFor=Exception.class) 
	public void fbExplicenceThread() throws AddressException, MessagingException, Exception {
		System.out.println("FB Thread Start");
		ArrayList<String> orderNnoList = new ArrayList<String>();
		//orderNnoList = mgrService.selectExpLicenceFB();
		orderNnoList = mgrService.selectExpNoFB();
		for(int i =0; i < orderNnoList.size();i++) {
			fastBoxApi.fnMakeFastBoxUpdateExpLicenceNoJson(orderNnoList.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 15 04 * * *")
	@Transactional(rollbackFor=Exception.class) 
	public void sendMailToEfs() throws AddressException, MessagingException, Exception { 
		if(smtpStatus.equals("off")) {
			return;
		}
		ArrayList<String> orderNnoList = mgrService.selectNotSendEfsInfo();
		if(orderNnoList.size()>0) {
			try {
				
				String swh = "USPS";
				if(swh.equals("DHL")) {
					
				}else if (swh.equals("USPS")) {
					// TODO Auto-generated method stub
					LocalDate currentDate = LocalDate.now();
					String savePath = realFilePath + "excel/expLicence/";
					String filename = "EFS_USPS_"+currentDate+".xlsx" ;
					File xlsFile = new File(savePath+filename);
					Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
					Sheet sheet1 = xlsxWb.createSheet("Sheet1");
					 
					//HEAD 스타일 설정 START
					CellStyle cellStyleHeader = xlsxWb.createCellStyle();
					cellStyleHeader.setWrapText(false);
					cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
					//HEAD 스타일 설정 END
					
					//ROW-CELL 선언 START
					Row row = null;
					Cell cell = null;
					int rowCnt = 0;
					//ROW-CELL 선언 END
					
					//HEADER 생성 START 작업중입니단
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("DATE");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					
					
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("FLT NO.");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("출발공항");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("ICN");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("도착공항");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("M NO.");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					
					row = sheet1.createRow(rowCnt);
					
					cell = row.createCell(0);
					cell.setCellValue("No");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(1);
					cell.setCellValue("HAWB NO");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(2);
					cell.setCellValue("CT");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(3);
					cell.setCellValue("WT");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(4);
					cell.setCellValue("신고 금액");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(5);
					cell.setCellValue("아이템");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(6);
					cell.setCellValue("보내는화주명");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(7);
					cell.setCellValue("보내는화주주소");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(8);
					cell.setCellValue("받는화주명");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(9);
					cell.setCellValue("받는화주주소");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(10);
					cell.setCellValue("수출신고번호");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(11);
					cell.setCellValue("수출신고 C/T");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(12);
					cell.setCellValue("수출신고 W/T");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(13);
					cell.setCellValue("분할선적(Y/N)");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(14);
					cell.setCellValue("분할선적차수");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(15);
					cell.setCellValue("거래코드 A:전자상거래 B:특송");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(16);
					cell.setCellValue("사업자등록번호");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(17);
					cell.setCellValue("세부번호");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(18);
					cell.setCellValue("Agent Code");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(19);
					cell.setCellValue("보내는화주연락처");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(20);
					cell.setCellValue("받는화주 우편번호");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(21);
					cell.setCellValue("받는화주연락처");
					cell.setCellStyle(cellStyleHeader);

					cell = row.createCell(22);
					cell.setCellValue("받는사람명");
					cell.setCellStyle(cellStyleHeader);

					cell = row.createCell(23);
					cell.setCellValue("받는 사업자 번호");
					cell.setCellStyle(cellStyleHeader);
					
					cell = row.createCell(24);
					cell.setCellValue("보내는 화주담당자");
					cell.setCellStyle(cellStyleHeader);
					rowCnt++;
					//HEADER 생성 END
					 
					
					for(int i=0; i<orderNnoList.size();i++) {
						ArrayList<ExpLicenceExcelVO> expLicence = new ArrayList<ExpLicenceExcelVO>();
						expLicence = mgrService.selectExcelLicenceEFS(orderNnoList.get(i));
						if(expLicence.size()!=0) {
							for(int roop = 0; roop < expLicence.size(); roop++) {
								expLicence.get(roop).dncryptData();
								row = sheet1.createRow(rowCnt);
								cell = row.createCell(0);
								cell.setCellValue(i+1);
								
								cell = row.createCell(1);
								cell.setCellValue(expLicence.get(roop).getHawb());
								
								cell = row.createCell(2);
								cell.setCellValue(expLicence.get(roop).getBoxCnt());
								
								cell = row.createCell(3);
								cell.setCellValue(expLicence.get(roop).getWta());
								
								cell = row.createCell(4);
								cell.setCellValue(expLicence.get(roop).getTotalValue());
								
								cell = row.createCell(5);
								cell.setCellValue(expLicence.get(roop).getItemDetail());
								
								cell = row.createCell(6);
								cell.setCellValue(expLicence.get(roop).getShipperName());
								
								cell = row.createCell(7);
								cell.setCellValue(expLicence.get(roop).getShipperAddr() + " " + expLicence.get(roop).getShipperAddrDetail());
								
								cell = row.createCell(8);
								cell.setCellValue(expLicence.get(roop).getCneeName());
								
								cell = row.createCell(9);
								cell.setCellValue(expLicence.get(roop).getCneeAddr() + " " + expLicence.get(roop).getCneeAddrDetail());
								
								cell = row.createCell(10);
								cell.setCellValue(expLicence.get(roop).getExpNo());
								
								cell = row.createCell(11);
								cell.setCellValue(expLicence.get(roop).getBoxCnt());
								
								cell = row.createCell(12);
								cell.setCellValue(expLicence.get(roop).getTotalValue());
								
								cell = row.createCell(13);
								cell.setCellValue("");
								
								cell = row.createCell(14);
								cell.setCellValue("");
								
								cell = row.createCell(15);
								cell.setCellValue("A");
								
								cell = row.createCell(16);
								cell.setCellValue(expLicence.get(roop).getExpBusinessNum());
								
								cell = row.createCell(17);
								cell.setCellValue("");
								
								cell = row.createCell(18);
								cell.setCellValue("");
								
								cell = row.createCell(19);
								cell.setCellValue("");
								
								cell = row.createCell(20);
								cell.setCellValue("");
								
								cell = row.createCell(21);
								cell.setCellValue("");

								cell = row.createCell(22);
								cell.setCellValue("");

								cell = row.createCell(23);
								cell.setCellValue("");
								
								cell = row.createCell(24);
								cell.setCellValue("");
								rowCnt++;
							}
						}
					}
					try {
			            
			            FileOutputStream fileOut = new FileOutputStream(xlsFile);
			            xlsxWb.write(fileOut);
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
					
					// 메일 환경 변수 설정입니다.
					Properties props = new Properties();
					// 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
					props.setProperty("mail.transport.protocol", "smtp");
					// 메일 호스트 주소를 설정합니다.
					props.setProperty("mail.host", "smtp.gmail.com");
					// ID, Password 설정이 필요합니다.
					props.put("mail.smtp.auth", "true");
					// port는 465입니다.
					props.put("mail.smtp.port", "465");
					// ssl를 사용할 경우 설정합니다.
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.fallback", "false");
					props.setProperty("mail.smtp.quitwait", "false");
					// id와 password를 설정하고 session을 생성합니다.
					Session session = Session.getInstance(props, new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("aciexpress.it", "itn@@2021Exp*");
						}
					});
					// 디버그 모드입니다.
					session.setDebug(true);
					// 메일 메시지를 만들기 위한 클래스를 생성합니다.
					MimeMessage message = new MimeMessage(session);
					// 송신자 설정
					message.setFrom(getAddress("aciexpress.it@gmail.com"));
					// 수신자 설정
					message.addRecipients(Message.RecipientType.TO, getAddresses("jsj29551@efs.asia"));
					//message.addRecipients(Message.RecipientType.TO, getAddresses("itsel@aciexpress.net"));
					// 참조 수신자 설정
					message.addRecipients(Message.RecipientType.CC, getAddresses("inquiry@efs.asia,itsel@aciexpress.net,icnopr@aciexpress.net,neodong@aciexpress.net,overseas2@aciexpress.net,acihq5@aciexpress.net"));
					//message.addRecipients(Message.RecipientType.CC, getAddresses("neodong@aciexpress.net"));
					// 숨은 참조 수신자 설정
//					message.addRecipients(Message.RecipientType.BCC, getAddresses("nowonbun@gmail.com"));
					// 메일 제목을 설정합니다.
					message.setSubject(currentDate+" ACI 수출신고 파일 송부의 건");
					// 메일 내용을 설정을 위한 클래스를 설정합니다.
					message.setContent(new MimeMultipart());
					// 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
					Multipart mp = (Multipart) message.getContent();
					// html 형식으로 본문을 작성해서 바운더리에 넣습니다.
					mp.addBodyPart(getContents("<html><head></head><body>안녕하십니까 ACI 입니다.<br>표제의 건에 대하여 아래와 같이 수출신고자료 첨부하오니<br>업무에 참고하여 주시기 비랍니다.<br>이상입니다.</body></html>"));
					// 첨부 파일을 추가합니다.
					mp.addBodyPart(getFileAttachment(savePath+filename));
					// 이미지 파일을 추가해서 contextId를 설정합니다. contextId는 위 본문 내용의 cid로 링크가 설정 가능합니다.
//				mp.addBodyPart(getImage("capture.png", "image"));
					// 메일을 보냅니다.
					Transport.send(message);
					
					xlsFile.delete();
				}
			
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} 
	}

	// 이미지를 로컬로 부터 읽어와서 BodyPart 클래스로 만든다. (바운더리 변환)
	private BodyPart getImage(String filename, String contextId) throws MessagingException {
		// 파일을 읽어와서 BodyPart 클래스로 받는다.
		BodyPart mbp = getFileAttachment(filename);
		if (contextId != null) {
			// ContextId 설정
			mbp.setHeader("Content-ID", "<" + contextId + ">");
		}
		return mbp;
	}

	// 파일을 로컬로 부터 읽어와서 BodyPart 클래스로 만든다. (바운더리 변환)
	private BodyPart getFileAttachment(String filename) throws MessagingException {
		// BodyPart 생성
		BodyPart mbp = new MimeBodyPart();
		// 파일 읽어서 BodyPart에 설정(바운더리 변환)
		File file = new File(filename);
		DataSource source = new FileDataSource(file);
		mbp.setDataHandler(new DataHandler(source));
		mbp.setDisposition(Part.ATTACHMENT);
		mbp.setFileName(file.getName());
		return mbp;
	}

	// 메일의 본문 내용 설정
	private BodyPart getContents(String html) throws MessagingException {
		BodyPart mbp = new MimeBodyPart();
		// setText를 이용할 경우 일반 텍스트 내용으로 설정된다.
		// mbp.setText(html);
		// html 형식으로 설정
		mbp.setContent(html, "text/html; charset=utf-8");
		return mbp;
	}

	// String으로 된 메일 주소를 Address 클래스로 변환
	private Address getAddress(String address) throws AddressException {
		return new InternetAddress(address);
	}

	// String으로 된 복수의 메일 주소를 콤마(,)의 구분으로 Address array형태로 변환
	private Address[] getAddresses(String addresses) throws AddressException {
		String[] array = addresses.split(",");
		Address[] ret = new Address[array.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = getAddress(array[i]);
		}
		return ret;
	}
	
	public void sendAuthMail(String email, String userId, String userPw) throws AddressException, MessagingException, Exception {
		System.out.println("smtpStart");
		
		try {
			System.out.println("smtpTryStart");
			
			Timestamp time = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("HHmm");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 10);
			String now = format.format(cal.getTime());
			
			
			StringBuffer sb =new StringBuffer();
			Random rand = new Random();
			for (int i = 0; i < 10; i++) {
				int idx = rand.nextInt(3);
				switch (idx) {
				 case 0:
					 sb.append((char) ((int) (rand.nextInt(26)) + 97));
                     break;
                 case 1:
                	 sb.append((char) ((int) (rand.nextInt(26)) + 65));
                     break;
                 case 2:
                	 sb.append((rand.nextInt(10)));
                     break;
				}
			}
			String authKey = sb.toString()+now;
			System.out.println(authKey);
			
			
			// 메일 환경 변수 설정입니다.
			Properties props = new Properties();
			// 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
			props.setProperty("mail.transport.protocol", "smtp");
			// 메일 호스트 주소를 설정합니다.
			props.setProperty("mail.host", "smtp.gmail.com");
			// ID, Password 설정이 필요합니다.
			props.put("mail.smtp.auth", "true");
			// port는 465입니다.
			props.put("mail.smtp.port", "465");
			// ssl를 사용할 경우 설정합니다.
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");
			// id와 password를 설정하고 session을 생성합니다.
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("aciexpress.it", googlePw);
				}
			});
			// 디버그 모드입니다.
			session.setDebug(false);
			// 메일 메시지를 만들기 위한 클래스를 생성합니다.
			MimeMessage message = new MimeMessage(session);
			// 송신자 설정
			message.setFrom(getAddress("aciexpress.it@gmail.com"));
			// 수신자 설정
			message.addRecipients(Message.RecipientType.TO, getAddresses("itsel2@aciexpress.net"));
			// 메일 제목을 설정합니다.
			message.setSubject("WMS 이메일 인증");
			// 메일 내용을 설정을 위한 클래스를 설정합니다.
			message.setContent(new MimeMultipart());
			// 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
			Multipart mp = (Multipart) message.getContent();
			// html 형식으로 본문을 작성해서 바운더리에 넣습니다.
			
			mp.addBodyPart(getContents("<html><head></head><body>안녕하십니까 ACI 입니다.<br>비밀번호 변경을 위해 아래 링크를 클릭해 주시기 바랍니다.<br>"+
					"<a href='http://localhost:8080/emailCheck?userId="+userId+"&authKey="+authKey+"&tempNum="+userPw+"' target='_blank'>이메일 인증하기</a>"
			+"<br><br>인증 완료 후 마이 페이지에서 비밀번호를 변경해 주시기 바랍니다.<br>감사합니다.</body></html>"));
			
			/*
			mp.addBodyPart(getContents("<html><head></head><body>안녕하십니까 ACI 입니다.<br>비밀번호 변경을 위해 아래 링크를 클릭해 주시기 바랍니다.<br>"+
					"<a href='https://wms.acieshop.com/emailCheck?userId="+userId+"&authKey="+authKey+"&tempNum="+userPw+"' target='_blank'>이메일 인증하기</a>"
			+"<br><br>인증 완료 후 마이 페이지에서 비밀번호를 변경해 주시기 바랍니다.<br>감사합니다.</body></html>"));
			*/
			// 메일을 보냅니다.
			Transport.send(message);
			System.out.println("smtpEnd");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
/*
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 0/8 * * *")
	@Transactional(rollbackFor=Exception.class) 
	//@RequestMapping(value = "/comn/insertDelvNo", method = RequestMethod.GET)
	public void fnGetDeliveryNo() throws Exception {
		if(smtpStatus.equals("off")) {
			return;
		}
		
		ArrayList<HashMap<String, Object>> delvList = new ArrayList<HashMap<String, Object>>();
		delvList = apiService.selectViewDeliveryList();
		if (delvList.size() > 0) {
			try {
				for (int i = 0; i < delvList.size(); i++) {
					if (delvList.get(i).get("transCode").toString().contains("FB") || delvList.get(i).get("transCode").toString().contains("FB-EMS")) {
						fastBoxApi.fnGetDeliveryInfo(delvList.get(i).get("nno").toString(), delvList.get(i).get("transCode").toString());
					} else if (delvList.get(i).get("transCode").toString().equals("YSL")) {
						yslApi.fnGetDeliveryInfo(delvList.get(i).get("nno").toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 45 08 * * *", zone = "Asia/Seoul")
	@Transactional(rollbackFor=Exception.class)
	public void sendailForReturnSeller() throws Exception {
		
		if (smtpStatus.equals("off")) {
			return;
		}
		
		ArrayList<ManagerVO> userInfo = new ArrayList<ManagerVO>();
		userInfo = mgrService.selectSendEmailreturnSeller();
		
		if (userInfo.size() > 0) {
			
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("aciexpress.it", googlePw);
				}
			});

			session.setDebug(false);
			
			String mailSubject = "[ACI] 반품 진행 요청의 건/Request for Return Process";
			
			for (int i = 0; i < userInfo.size(); i++) {
				try {
					
					userInfo.get(i).dncryptData();
					String userId = userInfo.get(i).getUserId();
					int cnt = userInfo.get(i).getCnt();
					String userEmail = userInfo.get(i).getUserEmail();
					String nnoList = userInfo.get(i).getNno();
					String[] nno = nnoList.split(",");
					
					MimeMessage message = new MimeMessage(session);
					message.setFrom(getAddress("aciexpress.it@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, getAddresses(userEmail));
					message.addRecipients(Message.RecipientType.CC, getAddresses("ebiz2@aciexpress.net,itsel2@aciexpress.net"));
					message.setSubject(mailSubject);
					
					String html = "<html><head><body>";
					html += "안녕하십니까 ACI 입니다.<br><br>반품 재고가 현재 1건 있습니다.<br>";
					html += "반품 진행 처리를 요청 드립니다.<br><br>감사합니다.<br><br>https://wms.acieshop.com/<br>";
					html += "<br><br>";
					html += "Hello,<br>This is ACI WorldWide Express reaching out.<br>";
					html += "We currently have one return in stock.<br>We request the processing of the return.<br><br>";
					html += "Thank you.<br><br>Best regards,<br>ACI WorldWide Express";
					html += "</body></head></html>";
					
					message.setContent(new MimeMultipart());
					Multipart mp = (Multipart) message.getContent();
					mp.addBodyPart(getContents(html));
					
					Transport.send(message);
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				
				mgrService.insertHisSendEmail(userInfo);
				
				
			}
		}
	}

	// 회원 등록 시 담당자 메일로 승인 요청 메일 전송
	//@RequestMapping(value = "/api/smtp/sendUserInfo", method = RequestMethod.GET)
	public void sendMailForReturnUser(ManagerVO userInfo) throws AddressException, MessagingException, Exception {
		
		if (smtpStatus.equals("off")) {
			return;
		}
		
		try {
			
			userInfo = mgrService.selectUserInfo(userInfo.getUserId());
			String orgStation = mgrService.selectOrgStationByUserId(userInfo.getUserId());
			
			Properties props = new Properties();
			// 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
			props.setProperty("mail.transport.protocol", "smtp");
			// 메일 호스트 주소를 설정합니다.
			props.setProperty("mail.host", "smtp.gmail.com");
			// ID, Password 설정이 필요합니다.
			props.put("mail.smtp.auth", "true");
			// port는 465입니다.
			props.put("mail.smtp.port", "465");
			// ssl를 사용할 경우 설정합니다.
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");
			// id와 password를 설정하고 session을 생성합니다.
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("aciexpress.it", googlePw);
				}
			});
			
			session.setDebug(false);
			// 메일 메시지를 만들기 위한 클래스를 생성합니다.
			MimeMessage message = new MimeMessage(session);
			// 송신자 설정
			message.setFrom(getAddress("aciexpress.it@gmail.com"));
			// 수신자 설정
			if (orgStation.equals("대한민국")) {
				message.addRecipients(Message.RecipientType.TO, getAddresses("ebiz2@aciexpress.net"));
				message.addRecipients(Message.RecipientType.CC, getAddresses("icnopr@aciexpress.net,ebiz@aciexpress.net,itsel2@aciexpress.net"));
			} else {
				message.addRecipients(Message.RecipientType.TO, getAddresses("itsel2@aciexpress.net"));
			}
			// 메일 제목을 설정합니다.
			message.setSubject("WMS 신규 회원 승인 요청의 건");
			// 메일 내용을 설정을 위한 클래스를 설정합니다.
			message.setContent(new MimeMultipart());
			// 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
			Multipart mp = (Multipart) message.getContent();
			// html 형식으로 본문을 작성해서 바운더리에 넣습니다.			
			String html = "<html><head><body>WMS 신규 회원이 등록 되었습니다.<br>회원 승인 처리 진행 바랍니다.<br><br>"
					+ "<br>신규 USER ID : "+userInfo.getUserId()+
					"<br>담당자 명 : "+userInfo.getUserName()+
					"<br>상호명 : "+userInfo.getComName()+
					"<br>Email : "+userInfo.getUserEmail()+
					"<br>Tel : "+userInfo.getUserTel()+
					"<br>Addr : "+userInfo.getUserAddr()+" "+userInfo.getUserAddrDetail()+
					"<br>출발지 :"+orgStation+
					"<br><br><br>https://wms.acieshop.com/prv/login<br><br><br>감사합니다.</body></head></html>";
			mp.addBodyPart(getContents(html));

			/*
			mp.addBodyPart(getContents("<html><head></head><body>안녕하십니까 ACI 입니다.<br>비밀번호 변경을 위해 아래 링크를 클릭해 주시기 바랍니다.<br>"+
					"<a href='https://wms.acieshop.com/emailCheck?userId="+userId+"&authKey="+authKey+"&tempNum="+userPw+"' target='_blank'>이메일 인증하기</a>"
			+"<br><br>인증 완료 후 마이 페이지에서 비밀번호를 변경해 주시기 바랍니다.<br>감사합니다.</body></html>"));
			*/
			// 메일을 보냅니다.
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendEmailToSeller(String userEmail) throws AddressException, MessagingException, Exception {
		
		if (smtpStatus.equals("off")) {
			return;
		}
		
		try {
			
			Properties props = new Properties();
			// 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
			props.setProperty("mail.transport.protocol", "smtp");
			// 메일 호스트 주소를 설정합니다.
			props.setProperty("mail.host", "smtp.gmail.com");
			// ID, Password 설정이 필요합니다.
			props.put("mail.smtp.auth", "true");
			// port는 465입니다.
			props.put("mail.smtp.port", "465");
			// ssl를 사용할 경우 설정합니다.
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");
			// id와 password를 설정하고 session을 생성합니다.
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("aciexpress.it", googlePw);
				}
			});
			
			session.setDebug(false);
			// 메일 메시지를 만들기 위한 클래스를 생성합니다.
			MimeMessage message = new MimeMessage(session);
			// 송신자 설정
			message.setFrom(getAddress("aciexpress.it@gmail.com"));
			// 수신자 설정
			message.addRecipients(Message.RecipientType.TO, getAddresses(userEmail));
			message.addRecipients(Message.RecipientType.CC, getAddresses("ebiz2@aciexpress.net,itsel2@aciexpress.net"));
			// 메일 제목을 설정합니다.
			message.setSubject("[ACI] Return Management Alert: New Message");
			// 메일 내용을 설정을 위한 클래스를 설정합니다.
			message.setContent(new MimeMultipart());
			// 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
			Multipart mp = (Multipart) message.getContent();
			// html 형식으로 본문을 작성해서 바운더리에 넣습니다.
			String html = "<html><head><body>";
			html += "안녕하십니까 ACI 입니다.<br><br>관리자 CS 메세지가 등록 되었습니다.<br>확인 바랍니다.<br><br>감사합니다.<br><br>https://wms.acieshop.com/<br>";
			html += "<br><br>";
			html += "Hello,<br>This is ACI WorldWide Express reaching out.<br>";
			html += "A new CS message has been registered by the administrator.<br>Please check it.<br><br>";
			html += "Thank you.<br><br>Best regards,<br>ACI WorldWide Express";
			html += "</body></head></html>";

			mp.addBodyPart(getContents(html));

			Transport.send(message);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void sendTestEmail() throws Exception {
		System.out.println("SMTP Start!");
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("aciexpress.it", googlePw);
			}
		});

		session.setDebug(false);
		
		String mailSubject = "[ACI] Return Management Alert: New Message";
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(getAddress("aciexpress.it@gmail.com"));
		message.addRecipients(Message.RecipientType.TO, getAddresses("itsel2@aciexpress.net"));
		message.setSubject(mailSubject);
		
		String html = "<html><head><body>";
		html += "안녕하십니까 ACI 입니다.<br><br>관리자 CS 메세지가 등록 되었습니다.<br>확인 바랍니다.<br><br>감사합니다.<br><br>https://wms.acieshop.com/<br>";
		html += "<br><br>";
		html += "Hello,<br>This is ACI WorldWide Express reaching out.<br>";
		html += "A new CS message has been registered by the administrator.<br>Please check it.<br><br>";
		html += "Thank you.<br><br>Best regards,<br>ACI WorldWide Express";
		html += "</body></head></html>";
		
		message.setContent(new MimeMultipart());
		Multipart mp = (Multipart) message.getContent();
		mp.addBodyPart(getContents(html));
		
		Transport.send(message);

		System.out.println("SMTP End!");
	}
	
	@Scheduled(cron = "0 30 15 * * *", zone = "Asia/Seoul")
	@Transactional(rollbackFor=Exception.class)
	public void updateFastboxTrackingInfo() throws Exception {
		
		if (smtpStatus.equals("off")) {
			return;
		}
		
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		
		String mallId = "aciexpress2";
		String consumerKey = "bafb893d6b3651e2fb36dc344ec227e3";
		String auth = "Bearer tz01OTbt6aWiv9dUd81A4Q5FwGqchhOie1mR6aPqfdKI6b9EhbDC+NTXBFTfT3ZfoJj416G3e9Vub/fpQtux4xm8SIqM7nc4hryFUOz3dremxXyLz+8uOnI8r8OI8Je1SLCx9GWjeNjCW4OgX87muvcIN8V7hbR3X9onN3As7LqNza2HA6Wi6yEoWW7rA/bHhzPPYd5i2rPp+aFChjxP8Y1q5MbjX2hUQtbes3Rby7pvqOt0FhxRyHEq77pndBYyaNRywo+l3onrOesFzrdPjXgNGKOsAuKlcT8sZiow2DO2P4J3pQMUfVQmE7h9uIX8hYnxCTEGEM//JCquTWByLw==";
		
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("Authorization", auth);
		apiHeader.put("consumerKey", consumerKey);
		apiHeader.put("Accept", "application/json");
		apiHeader.put("Accept-Language", "ko-KR");
		
		String httpUrl = "https://dhub-api.cafe24.com/api/Tracking";
		ApiAction action = ApiAction.getInstance();
		
		ArrayList<HashMap<String, Object>> getDataList = mgrService.selectFastboxData();
		
		for (int i = 0; i < getDataList.size(); i++) {
			
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				String trkNo = "";
				String nno = (String) getDataList.get(i).get("nno");
				String transCode = (String) getDataList.get(i).get("trans_code");
				String hawbNo = (String) getDataList.get(i).get("hawb_no");
				String matchNo = (String) getDataList.get(i).get("match_no");
				
				params.put("nno", nno);
				params.put("transCode", transCode);
				params.put("hawbNo", hawbNo);
				params.put("matchNo", matchNo);
				
				LinkedHashMap<String, Object> requestData = new LinkedHashMap<>();
				requestData.put("mall_id", mallId);
				requestData.put("fb_invoice_no", hawbNo);
				Gson gson = new Gson();
				String jsonVal = gson.toJson(requestData);
				
				String responseStr = action.sendPost(jsonVal, httpUrl, apiHeader);
				
				if (!responseStr.equals("")) {
					System.out.println("Fastbox Tracking Response Body : " + responseStr);
					JSONObject jsonObject = new JSONObject(responseStr);
					JSONObject meta = jsonObject.optJSONObject("meta");
					
					if (!meta.optString("code").equals("200")) {
						throw new Exception("status code is not 200");
					} else {
						JSONObject response = jsonObject.optJSONObject("response");
						JSONObject order = response.optJSONObject("order");
						trkNo = order.optString("Domestic_Invoice_No");
						
						if (!trkNo.equals("")) {
							params.put("delvNo", trkNo);
							comnMapper.insertDeliveryInfo(params);
						} else {
							throw new Exception("Tracking Number is not exist");
						}
					}
				} else {
					throw new Exception("response body is empty");
				}
			} catch (Exception e) {
				System.out.println("Fastbox Tracking Number Update Error : " + e.toString());
				continue;
			}
		}
	}
}
