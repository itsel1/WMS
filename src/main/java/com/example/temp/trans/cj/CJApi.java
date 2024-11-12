package com.example.temp.trans.cj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.encoding.AnyContentType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.mapper.ApiV1ReturnMapper;
import com.example.temp.common.encryption.AES256Cipher;
import com.example.temp.common.mapper.ComnMapper;
import com.example.temp.common.service.ComnService;
import com.example.temp.security.SecurityKeyVO;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;


@Service
@Component
@Controller
public class CJApi {
	
	@Autowired
	ApiMapper mapper;
	
	@Autowired
	CJMapper cjMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ComnMapper comnMapper;
	
	private String cjStatus = "off";
	
	private static final String UPPERCASE_ASCII =
	    "AEIOU" // grave
	    + "AEIOUY" // acute
	    + "AEIOUY" // circumflex
	    + "AON" // tilde
	    + "AEIOUY" // umlaut
	    + "A" // ring
	    + "C" // cedilla
	    + "OU" // double acute
	    ;

	private static final String UPPERCASE_UNICODE =
	    "\u00C0\u00C8\u00CC\u00D2\u00D9"
	    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
	    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
	    + "\u00C3\u00D5\u00D1"
	    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
	    + "\u00C5"
	    + "\u00C7"
	    + "\u0150\u0170"
	    ;

	
	public  Connection getConnercion(){		
		
		Connection conn = null; 
		
        try {  
            String user = "aci"; 
            String pw = "aci!@#$";
            String url = "jdbc:oracle:thin:@210.98.159.153:1521:OPENDB";
            Class.forName("oracle.jdbc.driver.OracleDriver");        
            conn = DriverManager.getConnection(url, user, pw); 
            
        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : "+sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return conn;     
	}
	
	public  Connection getConnercionTest(){		
		Connection conn = null;
		
        try {
            String user = "aci"; 
            String pw = "acidev!#$1";
            String url = "jdbc:oracle:thin:@210.98.159.153:1523:OPENDBT";
            Class.forName("oracle.jdbc.driver.OracleDriver");        
            conn = DriverManager.getConnection(url, user, pw);
            
        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : "+sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return conn;     
	}
	
	

	//@Scheduled(cron = "0 0/30 10-18 * * *")
	@RequestMapping(value="/api/cj/sendAllData",method=RequestMethod.GET)
    public void sendAllData() {   	
		if(cjStatus.equals("off")) {
			return;
		}
    	try { 

    	CJParameterVo cjParameter = new CJParameterVo();
    
    	Date now = new Date();
    	SimpleDateFormat formatYmd = new SimpleDateFormat ( "yyyyMMdd"); 
    	String nowYmd = formatYmd.format(now);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
    	cal.add(Calendar.DATE,-5);
    	
    	String nowYmd3ago = formatYmd.format(cal.getTime());
    	cjParameter.setDepDate(nowYmd3ago);
    	
    	ArrayList<CJOrderVo> cjOrderList = new ArrayList<CJOrderVo>();
		cjOrderList = cjMapper.selectCjHawbNo(cjParameter);
    	SecurityKeyVO keyVO = new SecurityKeyVO();
    	
    	String symmetryKey = keyVO.getSymmetryKey();    	
    	SimpleDateFormat formatYmdHms2 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    	
 		Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;     
	  	conn =  getConnercion();

    	for(int i = 0 ; i < cjOrderList.size() ; i++) {
    		System.out.println(cjOrderList.get(i).getNno());
    		System.out.println(cjOrderList.get(i).getHawbNo());
        	String nno = "";
        	String orgStation = "";
        	String userId="";
        	String HawbNo = "";
     		String mpckKey = ""; 
     		String shipperName = "";
     		String cneeName = "";
     		String cneeAddr = "" ,cneeAddrDetail = "";
     		String cneeTel = "";
     		String cneeHP = "";
     		String sysDate = formatYmdHms2.format(now);
     		String cneeTel1 = "",cneeTel2 = "" ,cneeTel3 = "";
     		String cneeHP1 = "" ,cneeHP2 = "" ,cneeHP3 = "";
     		String cneeZip = "";
     		String dlvMsg = "";
     		String itemDetail = "";
    		orgStation = cjOrderList.get(i).getOrgStation();
    		userId = cjOrderList.get(i).getUserId();
    		nno = cjOrderList.get(i).getNno();
    		shipperName =cjOrderList.get(i).getShipperName(); 
    		cneeName = cjOrderList.get(i).getCneeName();
    		cneeTel = cjOrderList.get(i).getCneeTel();
    		cneeHP = cjOrderList.get(i).getCneeHp();

     		String errorYn = "";
     		String errorMsg = "";

        	cneeTel = AES256Cipher.AES_Decode(cneeTel, symmetryKey);
        	cneeTel = cneeTel.replaceAll("[^0-9]","");
        	cneeHP = AES256Cipher.AES_Decode(cneeHP, symmetryKey);
        	cneeHP = cneeHP.replaceAll("[^0-9]","");
        	
        	if(cneeTel.equals("")) {
        		cneeTel  = cneeHP;
        	}
        	
        	if(cneeTel.equals("")) {
        		
        		errorYn = "Y";
    			errorMsg = errorMsg + "cneeTel Error ";
        	}
        	
        	if(cneeHP.equals("")) {
        		cneeHP = cneeTel;
        	}
        	
			if(cneeTel.length() > 9) {
				String[] tellArray = phoneNumberSplit(cneeTel);
				cneeTel1 = tellArray[0];
	    		cneeTel2 = tellArray[1];
	    		cneeTel3 = tellArray[2];
			}
			
			if(cneeHP.length() > 10) {
				String[] phoneArray = phoneNumberSplit(cneeHP);
	    		cneeHP1  = phoneArray[0];
	    		cneeHP2  = phoneArray[1];
	    		cneeHP3  = phoneArray[2];
			}
			
    		cneeZip = cjOrderList.get(i).getCneeZip();
    		if(cneeZip.equals("")) {
    			errorYn = "Y";
    			errorMsg = errorMsg + "cneeZip Error ";
    		}

    		if(cneeZip.length() > 6) {
    			errorYn = "Y";
    			errorMsg = errorMsg + "cneeZip Error ";
    		}
    		
    		cneeAddr = cjOrderList.get(i).getCneeAddr();    		
    		cneeAddr = AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
    		cneeAddr = cneeAddr.replace("'", "");
    		
    		if( cneeAddr.length()>100){
    			cneeAddr = cneeAddr.substring(0, 99);
    		}
    	
    		if(cneeAddr.equals("")) {
    			errorYn = "Y";
    			errorMsg = errorMsg  + "cneeAddr Error ";
    		}

    		cneeAddrDetail = cjOrderList.get(i).getCneeAddrDetail();
    		cneeAddrDetail = AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
    		cneeAddrDetail = cneeAddrDetail.replace("'","");
    		    		
    		if(cneeAddrDetail.equals("")) {
    			String[]  addrArray = cneeAddr.split(" ");
    			if(addrArray.length >0) {
	    			cneeAddr = cneeAddr.replace(addrArray[addrArray.length-1], "");
	    			cneeAddrDetail = addrArray[addrArray.length-1];
    			}
    		}

    		shipperName = cjOrderList.get(i).getShipperName();
    		dlvMsg = cjOrderList.get(i).getDlvReqMsg();
    		itemDetail = cjOrderList.get(i).getItemDetail();
    		HawbNo = cjOrderList.get(i).getHawbNo(); 
    		itemDetail = itemDetail.replace("'", "");

    		mpckKey = nowYmd+"_ACI_"+HawbNo;
    	 	CJInterfaceVo cJInterfaceVo = new CJInterfaceVo();
    	 	
    		cJInterfaceVo.setCustId("30350744");
    		cJInterfaceVo.setRcptYmd(nowYmd);
    		cJInterfaceVo.setCustUseNo(nno);
    		cJInterfaceVo.setRcptDv("01");
    		cJInterfaceVo.setWorkDvCd("01");
    		cJInterfaceVo.setReqDvCd("01");
    		cJInterfaceVo.setMpckKey(mpckKey);
    		cJInterfaceVo.setMpckSeq("1");
    		cJInterfaceVo.setCalDvCd("01");
    		cJInterfaceVo.setFrtDvCd("03");
    		cJInterfaceVo.setCntrItemCd("01");
    		cJInterfaceVo.setBoxTypeCd("01");
    		cJInterfaceVo.setBoxQty("1");
    		cJInterfaceVo.setCustMgmtDlcmCd("30350744");
    		cJInterfaceVo.setSendrNm(shipperName);
    		cJInterfaceVo.setSendrTelNo1("032");
    		cJInterfaceVo.setSendrTelNo2("744");
    		cJInterfaceVo.setSendrTelNo3("9550");
    		cJInterfaceVo.setSendrZipNo("07641");
    		cJInterfaceVo.setSendrAddr("서울특별시 강서구");
    		cJInterfaceVo.setSendrDetailAddr("남부순환로19길 121(외발산동)");
    		cJInterfaceVo.setRcvrNm(cneeName);
    		cJInterfaceVo.setRcvrTelNo1(cneeTel1);
    		cJInterfaceVo.setRcvrTelNo2(cneeTel2);
    		cJInterfaceVo.setRcvrTelNo3(cneeTel3);
    		cJInterfaceVo.setRcvrCellNo1(cneeHP1);
    		cJInterfaceVo.setRcvrCellNo2(cneeHP2);
    		cJInterfaceVo.setRcvrCellNo3(cneeHP3);
    		cJInterfaceVo.setRcvrZipNo(cneeZip);
    		cJInterfaceVo.setRcvrAddr(cneeAddr);
    		cJInterfaceVo.setRcvrDetailAddr(cneeAddrDetail);
    		cJInterfaceVo.setInvcNo(HawbNo);
    		cJInterfaceVo.setOriInvcNo("");
    		cJInterfaceVo.setPrtSt("02");
    		cJInterfaceVo.setRemark1(dlvMsg);
    		cJInterfaceVo.setGdsNm(itemDetail);
    		cJInterfaceVo.setDlvDv("01");
    		cJInterfaceVo.setRcptErrYn("N");
    		cJInterfaceVo.setEaiPrgsSt("01");
    		cJInterfaceVo.setRegEmpId("ACI");
    		cJInterfaceVo.setRegDtime(sysDate);
    		cJInterfaceVo.setModiEmpId("ACI");
    		cJInterfaceVo.setModiDtime(sysDate);

    		CJStatusVo cJStatusVo = new CJStatusVo();
    		cJStatusVo.setOrgStation(orgStation);
			cJStatusVo.setUserId(userId);
			cJStatusVo.setNno(nno);
			cJStatusVo.setHawbNo(HawbNo);
    		
    		if(errorYn.equals("Y")) {
    			cJStatusVo.setErrorYn(errorYn);
    			cJStatusVo.setErrorMsg(errorMsg);
    			cJStatusVo.setWUserId("sysScheduler");
    			cJStatusVo.setWUserIp("wmsSystem");
        		cjMapper.insertCJError(cJStatusVo);
    		}else {
    			String dbError = "";
    			errorYn = "N";
    			try {	
 
    				String query = "SELECT CUST_ID,RCPT_YMD,CUST_USE_NO,"
    						            + "RCPT_DV,WORK_DV_CD,REQ_DV_CD,MPCK_KEY,"
    						            + "MPCK_SEQ,CAL_DV_CD,FRT_DV_CD,CNTR_ITEM_CD,BOX_TYPE_CD,"
    						            + "BOX_QTY,FRT,CUST_MGMT_DLCM_CD,SENDR_NM,SENDR_TEL_NO1,SENDR_TEL_NO2,"
    						            + "SENDR_TEL_NO3,SENDR_CELL_NO1,SENDR_CELL_NO2,SENDR_CELL_NO3,SENDR_SAFE_NO1,"
    						            + "SENDR_SAFE_NO2,SENDR_SAFE_NO3,SENDR_ZIP_NO,SENDR_ADDR,SENDR_DETAIL_ADDR,RCVR_NM,"
    						            + "RCVR_TEL_NO1,RCVR_TEL_NO2,RCVR_TEL_NO3,RCVR_CELL_NO1,RCVR_CELL_NO2,"
    						            + "RCVR_CELL_NO3,RCVR_SAFE_NO1,RCVR_SAFE_NO2,RCVR_SAFE_NO3,RCVR_ZIP_NO,"
    						            + "RCVR_ADDR,RCVR_DETAIL_ADDR,ORDRR_NM,ORDRR_TEL_NO1,ORDRR_TEL_NO2,"
    						            + "ORDRR_TEL_NO3,ORDRR_CELL_NO1,ORDRR_CELL_NO2,ORDRR_CELL_NO3,"
    						            + "ORDRR_SAFE_NO1,ORDRR_SAFE_NO2,ORDRR_SAFE_NO3,ORDRR_ZIP_NO,"
    						            + "ORDRR_ADDR,ORDRR_DETAIL_ADDR,INVC_NO,ORI_INVC_NO,ORI_ORD_NO,"
    						            + "COLCT_EXPCT_YMD,COLCT_EXPCT_HOUR,SHIP_EXPCT_YMD,SHIP_EXPCT_HOUR,PRT_ST,"
    						            + "ARTICLE_AMT,REMARK_1,REMARK_2,REMARK_3,COD_YN,GDS_CD,GDS_NM,GDS_QTY,"
    						            + "UNIT_CD,UNIT_NM,GDS_AMT,ETC_1,ETC_2,ETC_3,ETC_4,ETC_5,DLV_DV,"
    						            + "RCPT_ERR_YN,RCPT_ERR_MSG,EAI_PRGS_ST,EAI_ERR_MSG,REG_EMP_ID,"
    						            + "REG_DTIME,MODI_EMP_ID,MODI_DTIME "
    						            + "FROM V_RCPT_ACI010 "
    						            + "WHERE CUST_USE_NO = ?";
    				
    				pstm = conn.prepareStatement(query);
    				pstm.setString(1, nno);
    				rs = pstm.executeQuery();

    				String custUseNo = "";
    				//String regDitme = "";
    				while(rs.next()) {
    					custUseNo = rs.getString("CUST_USE_NO");
    				}
    				
    				/*
    				if(!custUseNo.equals("")) {
    					cJStatusVo.setNno(custUseNo);
    					cJStatusVo.setHawbNo(HawbNo);
    					cJStatusVo.setWUserId("sysScheduler");
    	    			cJStatusVo.setWUserIp("CJDBSystem");
    	    			//cJStatusVo.setWDate(regDitme);    					
    					cjMapper.insertCJSuccess(cJStatusVo);    					
    					continue;
    				}
    				*/
    				
    				query = "insert into V_RCPT_ACI010 "
    						 + "("+"CUST_ID,"+"RCPT_YMD,"+"CUST_USE_NO,"+"RCPT_DV,"+"WORK_DV_CD,"
                                  +"REQ_DV_CD,"+"MPCK_KEY,"+"MPCK_SEQ,"+"CAL_DV_CD,"+"FRT_DV_CD,"
                                  +"CNTR_ITEM_CD,"+"BOX_TYPE_CD,"+"BOX_QTY,"+"CUST_MGMT_DLCM_CD,"
                                  +"SENDR_NM,"+"SENDR_TEL_NO1,"+"SENDR_TEL_NO2,"+"SENDR_TEL_NO3,"
                                  +"SENDR_ZIP_NO,"+"SENDR_ADDR,"+"SENDR_DETAIL_ADDR,"+"RCVR_NM,"+"RCVR_TEL_NO1,"
                                  +"RCVR_TEL_NO2,"+"RCVR_TEL_NO3,"+"RCVR_CELL_NO1,"+"RCVR_CELL_NO2,"
                                  +"RCVR_CELL_NO3,"+"RCVR_ZIP_NO,"+"RCVR_ADDR,"+"RCVR_DETAIL_ADDR,"+"INVC_NO,"
                                  +"ORI_INVC_NO,"+"PRT_ST,"+"REMARK_1,"+"GDS_NM,"+"DLV_DV,"+"RCPT_ERR_YN,"+"EAI_PRGS_ST,"
                                  +"REG_EMP_ID,"+"REG_DTIME,"+"MODI_EMP_ID,"+"MODI_DTIME"
    						+ ") values ("
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "?,?,?,?,?,"
    									+ "SYSDATE,?,SYSDATE)";
    				
    				pstm = conn.prepareStatement(query);
    				int ii = 1;
    				pstm.setString(ii,cJInterfaceVo.getCustId());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcptYmd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getCustUseNo());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcptDv());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getWorkDvCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getReqDvCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getMpckKey());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getMpckSeq());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getCalDvCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getFrtDvCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getCntrItemCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getBoxTypeCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getBoxQty());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getCustMgmtDlcmCd());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrNm());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrTelNo1());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrTelNo2());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrTelNo3());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrZipNo());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrAddr());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getSendrDetailAddr());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrNm());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrTelNo1());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrTelNo2());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrTelNo3());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrCellNo1());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrCellNo2());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrCellNo3());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrZipNo());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrAddr());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcvrDetailAddr());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getInvcNo());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getOriInvcNo());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getPrtSt());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRemark1());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getGdsNm());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getDlvDv());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRcptErrYn());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getEaiPrgsSt());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getRegEmpId());
    				ii++;
    				pstm.setString(ii,cJInterfaceVo.getModiEmpId());
    				pstm.executeUpdate();
    				
	    		}catch (SQLException sqle) {		
	                sqle.printStackTrace();
	                errorYn = "Y";
	                dbError = sqle.getMessage();
	            }
    			cJStatusVo.setWUserId("sysScheduler");
    			cJStatusVo.setWUserIp("CJDBSystem");
    			if(errorYn.equals("Y")) {
    				cJStatusVo.setErrorYn(errorYn);
    				cJStatusVo.setErrorMsg(dbError);
    	    		cjMapper.insertCJError(cJStatusVo);
    			}else {
    				cjMapper.insertCJSuccess(cJStatusVo);
    			}
    		}
    	}
    	
    	 try{
             if ( rs != null ){rs.close();}   
             if ( pstm != null ){pstm.close();}   
             if ( conn != null ){conn.close(); }
         }catch(Exception e){
             throw new RuntimeException(e.getMessage());
         }

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static String[] phoneNumberSplit(String phoneNumber) throws Exception{
        Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
        Matcher matcher = tellPattern.matcher(phoneNumber);
        if(matcher.matches()) {
            //정규식에 적합하면 matcher.group으로 리턴
            return new String[]{ matcher.group(1), matcher.group(2), matcher.group(3)};
        }else{
            //정규식에 적합하지 않으면 substring으로 휴대폰 번호 나누기
            String str1 = phoneNumber.substring(0, 3);
            String str2 = phoneNumber.substring(3, 7);
            String str3 = phoneNumber.substring(7, 11);
            return new String[]{str1, str2, str3};
        }
    }



	
	public CJTerminalVo fnCJTerminal(CJParameterVo cJParameterVo) {
		
		CJTerminalVo cJTerminalVo = new CJTerminalVo();

		HttpURLConnection connection = null;
		OutputStream os =null;

		StringBuffer xml = new StringBuffer();
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.address.nplus.doortodoor.co.kr/\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<web:getAddressInformationByValue>");
		xml.append("<arg0>");
		xml.append("<boxTyp>1</boxTyp>");
		xml.append("<clntMgmCustCd>30281324</clntMgmCustCd>");
		xml.append("<clntNum>30281324</clntNum>");
		xml.append("<cntrLarcCd>01</cntrLarcCd>");
		xml.append("<fareDiv>03</fareDiv>");
		xml.append("<orderNo>");
		xml.append(cJParameterVo.getNno());
		xml.append("</orderNo>");
		xml.append("<prngDivCd>01</prngDivCd>");
		xml.append("<rcvrAddr>");
		String cJAddr = cJParameterVo.getAddr().replaceAll("&amp;", "&").replaceAll("&", "&amp;");
		xml.append(cJAddr);
		xml.append("</rcvrAddr>");
		xml.append("<sndprsnAddr>");
		xml.append("서울시 강서구 외발산동 217-1");
		xml.append("</sndprsnAddr>");
		xml.append("</arg0>");
		xml.append("</web:getAddressInformationByValue>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
		
		String xmlData = xml.toString();
		try {
			
			URL url = new URL("https://address.doortodoor.co.kr:443/address/address_webservice.korex");
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty( "Content-Type", "text/xml;charset=\"utf-8\"");
			connection.setRequestProperty( "Accept", "text/xml;charset=\"utf-8\"");
			connection.setRequestProperty( "Cache-Control", "no-cache");
			connection.setRequestProperty( "Pragma", "no-cache");
			connection.setRequestProperty("SOAPAction","myactionMethod");
            connection.setRequestProperty( "Content-Length", Integer.toString(xmlData.length()) );
            
            os = connection.getOutputStream();
            os.write( xmlData.getBytes("utf-8") );
            os.flush();
            os.close();

            InputStreamReader in = new InputStreamReader(connection.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(in);
            String sb =  br.readLine();
           
            DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder    =  factory.newDocumentBuilder();
            Document document  =  builder.parse(new InputSource(new StringReader(sb)));
            
            
            String errorCd = document.getElementsByTagName("errorCd").item(0).getChildNodes().item(0).getNodeValue();
            String errorMsg = document.getElementsByTagName("errorMsg").item(0).getChildNodes().item(0).getNodeValue();
            
   
            
            if(errorCd.equals("0")) {
	            String dlvClsfCd = document.getElementsByTagName("dlvClsfCd").item(0).getChildNodes().item(0).getNodeValue(); 
	            String dlvSubClsfCd = document.getElementsByTagName("dlvSubClsfCd").item(0).getChildNodes().item(0).getNodeValue();
	            String dlvPreArrBranShortNm = document.getElementsByTagName("dlvPreArrBranShortNm").item(0).getChildNodes().item(0).getNodeValue();
	            
	            String dlvPreArrEmpNm = document.getElementsByTagName("dlvPreArrEmpNm").item(0).getChildNodes().item(0).getNodeValue();
	            String rcvrClsfAddr = document.getElementsByTagName("rcvrClsfAddr").item(0).getChildNodes().item(0).getNodeValue();
	            String rcvrShortAddr = document.getElementsByTagName("rcvrShortAddr").item(0).getChildNodes().item(0).getNodeValue();
	            String rcvrEtcAddr = "";
	            String dlvPreArrEmpNickNm = "";
	            
	            
	            if(document.getElementsByTagName("dlvPreArrEmpNickNm").item(0) != null) {
	            	dlvPreArrEmpNickNm = document.getElementsByTagName("dlvPreArrEmpNickNm").item(0).getChildNodes().item(0).getNodeValue();
	            }
	            
	            if(document.getElementsByTagName("rcvrEtcAddr").item(0) != null) {
	            	rcvrEtcAddr = document.getElementsByTagName("rcvrEtcAddr").item(0).getChildNodes().item(0).getNodeValue();
	            }
	            
	              
	            dlvPreArrEmpNickNm = "";

	            cJTerminalVo.setDlvClsfCd(dlvClsfCd);
	            cJTerminalVo.setDlvSubClsfCd(dlvSubClsfCd);
	            cJTerminalVo.setDlvPreArrBranShortNm(dlvPreArrBranShortNm);
	            cJTerminalVo.setDlvPreArrEmpNickNm(dlvPreArrEmpNickNm);
	            cJTerminalVo.setDlvPreArrEmpNm(dlvPreArrEmpNm);
	            cJTerminalVo.setRcvrClsfAddr(rcvrClsfAddr);
	            cJTerminalVo.setRcvrShortAddr(rcvrShortAddr);
	            cJTerminalVo.setRcvrEtcAddr(rcvrEtcAddr);
            }
            
            cJTerminalVo.setErrorCd(errorCd);
            cJTerminalVo.setErrorMsg(errorMsg);

		} catch (Exception e){
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		return cJTerminalVo;
		
	}

	public void cjLogFile(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		SimpleDateFormat formatYmdHms = new SimpleDateFormat ( "yyyyMMdd");
    	Date now = new Date();
    	String foler = formatYmdHms.format(now);
    	String file =formatYmdHms.format(now)+".txt";
				
        String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/cjlog/test11.txt";
        String folerPath = this.getClass().getResource("").getPath()+"/log/"+foler+"/";

        String txt = "테스트입니다!22!";

        File Folder = new File(folerPath);
        
        if (!Folder.exists()) {
    		try{
    		    Folder.mkdir(); //폴더 생성합니다.
    		    System.out.println("폴더가 생성되었습니다.");
    	        } 
    	        catch(Exception e){
    		    e.getStackTrace();
    		}        
             }else {
    		System.out.println("이미 폴더가 생성되어 있습니다.");
    	}
        

        try{
            // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
            BufferedWriter fw = new BufferedWriter(new FileWriter(folerPath+file, true));            
            // 파일안에 문자열 쓰기
            fw.write(txt);
            fw.flush();
            // 객체 닫기
            fw.close();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailArray(String hawbNo){
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
			ArrayList<CJPodVo> cjPodList = new ArrayList<CJPodVo>();
			cjPodList = CJPod(hawbNo);
			
			for(int i = cjPodList.size()-1; i > -1; i--) {
				if (cjPodList.get(i).getCrgSt().equals("91")) {
					podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode","S500");
					podDetatil.put("UpdateDateTime", cjPodList.get(i).getScanTime());
					podDetatil.put("UpdateLocation", "Korea, "+cjPodList.get(i).getDealtBranNm());
					podDetatil.put("UpdateDescription", cjPodList.get(i).getCrgStName());
					podDetatil.put("ProblemCode","S001"); 
					podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
					podDetatailArray.add(podDetatil);
				}else {
					podDetatil  = new LinkedHashMap<String,Object>();
					podDetatil.put("UpdateCode",cjPodList.get(i).getCrgSt());
					podDetatil.put("UpdateDateTime", cjPodList.get(i).getScanTime());
					podDetatil.put("UpdateLocation", "Korea, "+cjPodList.get(i).getDealtBranNm());
					podDetatil.put("UpdateDescription", cjPodList.get(i).getCrgStName());
					podDetatil.put("ProblemCode",""); 
					podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
					podDetatailArray.add(podDetatil);
				}
			}
		
			
			String RegistInDate = comnService.selectOrderDate(hawbNo);
			String housIndate = comnService.selectHawbDate(hawbNo);
			
			if(!housIndate.equals("")) {
				podDetatil  = new LinkedHashMap<String,Object>();
				podDetatil.put("UpdateCode","S011");
				podDetatil.put("UpdateDateTime", housIndate);
				podDetatil.put("UpdateLocation", "Korea");
				podDetatil.put("UpdateDescription", "Finished warehousing.");
				podDetatil.put("ProblemCode",""); 
				podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
				podDetatailArray.add(podDetatil);
			}
			podDetatil  = new LinkedHashMap<String,Object>();
			podDetatil.put("UpdateCode","S001");
			podDetatil.put("UpdateDateTime", RegistInDate);
			podDetatil.put("UpdateLocation", "Korea");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatil.put("ProblemCode",""); 
			podDetatil.put("Comments", "(주)에이씨아이월드와이드(ACI WORLDWIDE)");
			podDetatailArray.add(podDetatil);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return podDetatailArray;
	}
	
	public ArrayList<HashMap<String, Object>> makePodDetailForArray(String hawbNo){
		ArrayList<HashMap<String, Object>> podDetatailArray =  new ArrayList<HashMap<String, Object>>();
		try {
			LinkedHashMap<String,Object> podDetatil  = new LinkedHashMap<String,Object>();
			ArrayList<CJPodVo> cjPodList = new ArrayList<CJPodVo>();
			cjPodList = CJPod(hawbNo);
			
			String hawbInDate = mapper.selectHawbInDate(hawbNo);	// 입고
			String mawbInDate = mapper.selectMawbInDate(hawbNo);	// 출고
			String regInDate = mapper.selectRegInDate(hawbNo);		// 주문등록
			
			for(int i = cjPodList.size()-1; i > -1; i--) {
				podDetatil  = new LinkedHashMap<String,Object>();
				String time = cjPodList.get(i).getScanTime();
				
				if (cjPodList.get(i).getCrgSt().equals("91")) {
					podDetatil.put("UpdateCode", "600");
					podDetatil.put("UpdateDateTime", time.substring(0, time.length() - 5));
					podDetatil.put("UpdateLocation", "Korea, "+cjPodList.get(i).getDealtBranNm());
					podDetatil.put("UpdateDescription", "Delivered");
				} else if (cjPodList.get(i).getCrgSt().equals("82")) {
					podDetatil.put("UpdateCode", "500");
					podDetatil.put("UpdateDateTime", time.substring(0, time.length() - 5));
					podDetatil.put("UpdateLocation", "Korea, "+cjPodList.get(i).getDealtBranNm());
					podDetatil.put("UpdateDescription", "Out for Delivery");
				} else if (cjPodList.get(i).getCrgSt().equals("11")) {
					podDetatil.put("UpdateCode", "400");
					podDetatil.put("UpdateDateTime", time.substring(0, time.length() - 5));
					podDetatil.put("UpdateLocation", "Korea, "+cjPodList.get(i).getDealtBranNm());
					podDetatil.put("UpdateDescription", "Arrival in destination country");
				} else {
					continue;
				}
				podDetatailArray.add(podDetatil);
			}
			
			podDetatil = new LinkedHashMap<String, Object>();
			podDetatil.put("UpdateCode", "300");
			podDetatil.put("UpdateDateTime", mawbInDate.substring(0, mawbInDate.length() - 3));
			podDetatil.put("UpdateLocation", "Republic of Korea");
			podDetatil.put("UpdateDescription", "Picked up by Shipping Partner");
			podDetatailArray.add(podDetatil);
			
			podDetatil = new LinkedHashMap<String, Object>();
			podDetatil.put("UpdateCode", "200");
			podDetatil.put("UpdateDateTime", hawbInDate.substring(0, hawbInDate.length() - 3));
			podDetatil.put("UpdateLocation", "Republic of Korea");
			podDetatil.put("UpdateDescription", "Finished warehousing");
			podDetatailArray.add(podDetatil);
			
			podDetatil = new LinkedHashMap<String, Object>();
			podDetatil.put("UpdateCode", "100");
			podDetatil.put("UpdateDateTime", regInDate);
			podDetatil.put("UpdateLocation", "Republic of Korea");
			podDetatil.put("UpdateDescription", "Order information has been entered");
			podDetatailArray.add(podDetatil);

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return podDetatailArray;
	}
	
	public ArrayList<CJPodVo> CJPod(String blNo) {
		
		
		ArrayList<CJPodVo> podInfo = new ArrayList<CJPodVo>();
	  	try {
	  		
	  		
	  		Connection conn = null;
		    PreparedStatement pstm = null;
		    ResultSet rs = null;     
		  	conn =  getConnercion();
		  	
	  		 try{
			  	//HashMap<String,Object> rst = new HashMap<"String", "String">();  
		  		String query = "SELECT \r\n"
		  				+ "      SERIAL,CUST_ID,RCPT_DV,INVC_NO,CUST_USE_NO,CRG_ST,\r\n"
		  				+ "      SCAN_YMD,SCAN_HOUR,TO_DATE(SCAN_YMD||SCAN_HOUR,'YYYY-MM-DD HH24:MI')  AS SCAN_TIME ,\r\n"
		  				+ "      DEALT_BRAN_NM,DEALT_BRAN_TEL,\r\n"
		  				+ "      DEALT_EMP_NM,DEALT_EMP_TEL,ACPTR_NM,\r\n"
		  				+ "      NO_CLDV_RSN_CD,DETAIL_RSN,EAI_PRGS_ST,\r\n"
		  				+ "      EAI_ERR_MSG,REG_EMP_ID,REG_DTIME,\r\n"
		  				+ "      MODI_EMP_ID,MODI_DTIME,\r\n"
		  				+ "      ACPTR_NM"
		  				+ "      FROM V_TRACE_ACI020\r\n"
		  				+ "WHERE INVC_NO = ?\r\n"
		  				+ "ORDER BY TO_DATE(SCAN_YMD||SCAN_HOUR,'YYYY-MM-DD HH24:MI') ASC ,CRG_ST  ASC";
		  		
		  		pstm = conn.prepareStatement(query);
		  		pstm.setNString(1, blNo);
		  		rs = pstm.executeQuery();
		  		
		  		while(rs.next()) {
		  			CJPodVo podInfoDetial = new CJPodVo();
		  			podInfoDetial.setCustUseNo(rs.getString("CUST_USE_NO"));
		  			podInfoDetial.setInvcNo(rs.getString("INVC_NO"));
		  			podInfoDetial.setScanTime(rs.getString("SCAN_TIME"));
		  			podInfoDetial.setCrgSt(rs.getString("CRG_ST"));
		  		    podInfoDetial.setCrgStName(cJCrgStName(rs.getString("CRG_ST")));
		  		    podInfoDetial.setNoCldvRsnCd(rs.getString("NO_CLDV_RSN_CD"));
		  		    podInfoDetial.setNoCldvRsnCdName(cJnoCldvRsnCdName(rs.getString("CRG_ST"),rs.getString("NO_CLDV_RSN_CD")));
		  		    podInfoDetial.setDealtBranNm(rs.getString("DEALT_BRAN_NM"));
		  		    podInfoDetial.setDealtBranTel(rs.getString("DEALT_BRAN_TEL"));
		  		    podInfoDetial.setDealtEmpNm(rs.getString("DEALT_EMP_NM"));
		  		    podInfoDetial.setDealtEmpTel(rs.getString("DEALT_EMP_NM"));
		  		    podInfoDetial.setAcptrNm(rs.getString("ACPTR_NM"));
		  			podInfo.add(podInfoDetial);
				}
		  		
	         }catch(Exception e){
	        	 
	             throw new RuntimeException(e.getMessage());
	             
	         } finally {
	        	 if ( rs != null ){rs.close();}   
	             if ( pstm != null ){pstm.close();}   
	             if ( conn != null ){conn.close(); }
	         }
		} catch (Exception e) {
			e.printStackTrace();
		}
	  	
	  	return podInfo;
		
	}
	
	public String cJCrgStName(String crgSt) {
		String codeName = "";
		
		switch(crgSt){
			case "01": codeName = "집하지시"; break;
			case "11": codeName = "집하처리"; break;
			case "12": codeName = "미집하"; break;
			case "41": codeName = "간선상차"; break;
			case "42": codeName = "간선하차"; break;
			case "82": codeName = "배송출발"; break;
			case "84": codeName = "미배달"; break;
			case "91": codeName = "배송완료"; break;
		}
		return codeName; 
	}
	
	public String cJnoCldvRsnCdName(String crgSt , String noCldvRsnCd ){
		String codeName = "";
		
		switch(crgSt){
		case "12" : 
			switch(noCldvRsnCd){
				case "49": codeName = "집화이관";break;
				case "02": codeName = "업체미출고";break;
				case "03": codeName = "기집화";break;
				case "01": codeName = "재고부족";break;
				case "06": codeName = "타택배"; break;
				case "07": codeName = "천재지변"; break;
				case "08": codeName = "주문취소(일반건)"; break;
				case "09": codeName = "집배구역불일치"; break;
				case "11": codeName = "집화예정"; break;
				case "12": codeName = "토요휴무"; break;
				case "13": codeName = "취급불가/규격외품"; break;
				case "14": codeName = "반품취소/거부"; break;
				case "16": codeName = "고객사용중"; break;
				case "17": codeName = "지정일회수"; break;
				case "18": codeName = "고객부재"; break;
				case "21": codeName = "고객정보오류"; break;
				case "22": codeName = "교환물건미도착"; break;
				case "23": codeName = "회수건없음(업체오류)"; break;
				case "25": codeName = "통화안됨(4일이상)"; break;
				case "26": codeName = "합포장(미사용)"; break;
				case "33": codeName = "시간부족"; break;
				case "34": codeName = "차량고장"; break;
				case "35": codeName = "포장미비"; break;
				case "38": codeName = "도서/외곽지역"; break;
				case "44": codeName = "중복예약"; break;
				case "48": codeName = "기타2"; break;
				case "50": codeName = "보내는분 요청"; break;
				case "51": codeName = "받는분 요청"; break;
			}
			break;
		case "84" :
			switch(noCldvRsnCd){
				case "01":	codeName ="고객정보오류"; break;
				case "02":	codeName ="고객부재"; break;
				case "05":	codeName ="지연도착"; break;
				case "06":	codeName ="분류오류"; break;
				case "08":	codeName ="통화불가능"; break;
				case "09":	codeName ="수취거부"; break;
				case "11":	codeName ="천재지변"; break;
				case "16":	codeName ="착지변경"; break;
				case "21":	codeName ="상품사고(파손/분실)"; break;
				case "23":	codeName ="토요휴무"; break;
				case "24":	codeName ="지정일배달"; break;
				case "32":	codeName ="차량고장/사고"; break;
				case "33":	codeName ="도서/외곽지역"; break;
				case "42":	codeName ="특판잔류"; break;
				case "55":	codeName ="결재불가"; break;
				case "56":	codeName ="배달전취소"; break;
			}
			break;
		}
		
		return codeName;
	}
	

	public void cjPrint(HttpServletRequest request, HttpServletResponse response, ArrayList<CJParameterVo> cJParameters, String orderType) throws IOException, BarcodeException {
		ArrayList<HashMap<String, Object>> packingInfo = new ArrayList<HashMap<String, Object>>();
		String pdfPath = request.getSession().getServletContext().getRealPath("/") + "/pdf";
		String markPath = pdfPath+"/mark/aciMark.jpg";
		
		String pdfPath2 = pdfPath + "/barcode/";
		File dir2 = new File(pdfPath2);
		if (!dir2.isDirectory()) {
			dir2.mkdir();
		}
		
		try {
			PDDocument doc = new PDDocument();
			response.setContentType("application/pdf");
			String fileName;
			
			Date now = new Date();
			SimpleDateFormat formatYmd = new SimpleDateFormat ( "yyyyMMdd");
	    	String nowYmd = formatYmd.format(now);

			ClassPathResource cssResource = new ClassPathResource("application.properties");
			String mainPath = cssResource.getURL().getPath();
			String subPath = mainPath.substring(0, mainPath.lastIndexOf("/"));
			InputStream korean = new FileInputStream(subPath + "/static/fonts/NanumBarunGothic.ttf");

			PDType0Font NanumGothic = PDType0Font.load(doc, korean);
			float perMM = 1 / (10 * 2.54f) * 72;
			
			SecurityKeyVO securityKey = new SecurityKeyVO();
			String symmetryKey = securityKey.getSymmetryKey();
			
			int totalPage = 0;

			for(int i = 0 ; i < cJParameters.size() ; i++) {

				String nno = cJParameters.get(i).getNno();
				CJParameterVo cJPatemterOne = new CJParameterVo();
				cJPatemterOne.setNno(nno);
				CJOrderVo cjOrderList = new CJOrderVo();
				cjOrderList = cjMapper.selectCjNnoOne(cJPatemterOne);

				PDRectangle	pageStandard = new PDRectangle(123*perMM, 100*perMM);
				PDPage blankPage = new PDPage(pageStandard);
				doc.addPage(blankPage);
				PDPage page = doc.getPage(totalPage);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
					
				nno = cjOrderList.getNno();
				
				String orgStation = cjOrderList.getOrgStation();
				String dstnNation = cjOrderList.getDstnNation();
				String hawbNo = cjOrderList.getHawbNo();
				String hawbNoFomat = hawbNo.substring(0,4)+"-"+hawbNo.substring(4,8)+"-"+hawbNo.substring(8,12);
				String cneeName = cjOrderList.getCneeName();
				String cneeAddr = cjOrderList.getCneeAddr(); 
				String cneeAddrDetail = cjOrderList.getCneeAddrDetail();
				String cneeTel = cjOrderList.getCneeTel();
				String cneeHp = cjOrderList.getCneeHp();
				String shipperName = cjOrderList.getShipperName();
				String shipperTel = cjOrderList.getShipperTel();
				String shipperAddr = cjOrderList.getShipperAddr();
				String shipperAddrDetail = cjOrderList.getShipperAddrDetail();
				String dlvMsg = cjOrderList.getDlvReqMsg();
			    String itemDetail = cjOrderList.getItemDetail();
			    String buySite = cjOrderList.getBuySite();
			    String cusItemCode = cjOrderList.getCusItemCode();
			    String userId = cjOrderList.getUserId();
			    int totalItemCnt = cjOrderList.getTotalItemCnt();
			    String orderNo = cjOrderList.getOrderNo();
			    
				try {
					cneeTel =  AES256Cipher.AES_Decode(cneeTel, symmetryKey);
					cneeHp =  AES256Cipher.AES_Decode(cneeHp, symmetryKey);
					cneeAddr =  AES256Cipher.AES_Decode(cneeAddr, symmetryKey);
					cneeAddrDetail =  AES256Cipher.AES_Decode(cneeAddrDetail, symmetryKey);
					shipperTel =  AES256Cipher.AES_Decode(shipperTel, symmetryKey);
					shipperAddr = AES256Cipher.AES_Decode(shipperAddr, symmetryKey);
					shipperAddrDetail = AES256Cipher.AES_Decode(shipperAddrDetail, symmetryKey);
					
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}	
				
	        	cneeTel = cneeTel.replaceAll("[^0-9]","");
	        	cneeHp = cneeHp.replaceAll("[^0-9]","");
	        	
	        	String cneeTel1 = "";
	        	String cneeTel2 = "";
	        	String cneeTel3 = "";
	        	String cneeHp1 = "";
	        	String cneeHp2 = "";
	        	String cneeHp3 = "";
	        	
	      
	        
				if(cneeTel.length() > 9) {
					String[] tellArray;
					try {
						tellArray = phoneNumberSplit(cneeTel);
						cneeTel1 = tellArray[0];
			    		cneeTel2 = tellArray[1];
			    		cneeTel3 = tellArray[2];
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				if(cneeHp.length() > 10) {
					String[] phoneArray;
					try {
						phoneArray = phoneNumberSplit(cneeHp);
						cneeHp1  = phoneArray[0];
						cneeHp2  = phoneArray[1];
						cneeHp3  = phoneArray[2];
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				CJParameterVo cJParameterVo = new CJParameterVo();
				CJTerminalVo cJTerminalVo = new CJTerminalVo ();
				
				String cjTerminal1 = "";
				String cjTerminal2 = "";
				String cjSubTermianl = "";
				String cjRcvrClsfAddr ="";
				String cjDlvClsfCd = "";
				String cjDlvPreArr =""; 
				
				try {
					cJParameterVo.setAddr(cneeAddr+" "+cneeAddrDetail);
					cJParameterVo.setNno(nno);
					cJTerminalVo  = fnCJTerminal( cJParameterVo);

					if(cJTerminalVo.getDlvClsfCd().length() > 0) {
						 cjDlvClsfCd = cJTerminalVo.getDlvClsfCd();
						 cjTerminal1 = cJTerminalVo.getDlvClsfCd().substring(0,1);
						 cjTerminal2 = cJTerminalVo.getDlvClsfCd().substring(1,4);
						 cjSubTermianl = cJTerminalVo.getDlvSubClsfCd();
						 cjRcvrClsfAddr = cJTerminalVo.getRcvrClsfAddr();
						 cjDlvPreArr = cJTerminalVo.getDlvPreArrBranShortNm()+"-"+cJTerminalVo.getDlvPreArrEmpNm()+"-"+cJTerminalVo.getDlvPreArrEmpNickNm();
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}


			
				if(!cJTerminalVo.getErrorCd().equals("0")) {

					contentStream.beginText();
					contentStream.newLineAtOffset(12*perMM, 50*perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream.showText(cJTerminalVo.getErrorMsg());
					contentStream.endText();
				}

				if(cneeAddr.length() > 40) {
					cneeAddrDetail = cneeAddr.substring(41,cneeAddr.length())+" "+cneeAddrDetail;
					cneeAddr = cneeAddr.substring(0,40);
				}
				
				contentStream.beginText();
				contentStream.newLineAtOffset(12*perMM, 94*perMM);
				contentStream.setFont(NanumGothic, 12);
				contentStream.showText(hawbNoFomat);
				contentStream.endText();

				contentStream.beginText();
				contentStream.newLineAtOffset(50*perMM, 94*perMM);
				contentStream.setFont(NanumGothic, 8);
				contentStream.showText(nowYmd);
				contentStream.endText();
				
				
				if(cJTerminalVo.getErrorCd().equals("0")) {
				
					contentStream.beginText();
					contentStream.newLineAtOffset(38*perMM, 79*perMM);
					contentStream.setFont(NanumGothic, 32);
					contentStream.showText(cjTerminal1);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(46*perMM, 79*perMM);
					contentStream.setFont(NanumGothic, 52);
					contentStream.showText(cjTerminal2);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(83*perMM, 79*perMM);
					contentStream.setFont(NanumGothic, 32);
					contentStream.showText("-"+cjSubTermianl);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 56*perMM);
					contentStream.setFont(NanumGothic, 20);
					contentStream.showText(cjRcvrClsfAddr);
					contentStream.endText();
					
					//cjDlvPrearr
					contentStream.beginText();
					contentStream.newLineAtOffset(3*perMM, 1*perMM);
					contentStream.setFont(NanumGothic, 12);
					contentStream.showText(cjDlvPreArr);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 71*perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream.showText(cneeName);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(30*perMM, 71*perMM);
					contentStream.setFont(NanumGothic, 10);
					contentStream.showText(cneeTel1+"-"+cneeTel2+"-****"+" / "+cneeHp1+"-"+cneeHp2+"-****");
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 67*perMM);
					contentStream.setFont(NanumGothic, 9);
					contentStream.showText(cneeAddr);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 63*perMM);
					contentStream.setFont(NanumGothic, 9);
					contentStream.showText(cneeAddrDetail);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 52*perMM);
					contentStream.setFont(NanumGothic, 7);
					contentStream.showText(shipperName+" "+shipperTel);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(7*perMM, 49*perMM);
					contentStream.setFont(NanumGothic, 7);
					contentStream.showText(shipperAddr);
					contentStream.endText();

					
					String text = itemDetail.toUpperCase();
					StringBuilder builder = new StringBuilder();
					int n = text.length();
					for (int j = 0; j < n; j++) {
						char c = text.charAt(j);
						int pos = UPPERCASE_UNICODE.indexOf(c);
						if (pos > -1) {
							builder.append(UPPERCASE_ASCII.charAt(pos));
						} else {
							builder.append(c);
						}
					}
					
					if (userId.equals("mustit")) {
						contentStream.beginText();
						contentStream.newLineAtOffset(5*perMM, 39*perMM);
						contentStream.setFont(NanumGothic, 9);
						contentStream.showText(cusItemCode);
						contentStream.endText();
					} else {
						contentStream.beginText();
						contentStream.newLineAtOffset(5*perMM, 39*perMM);
						contentStream.setFont(NanumGothic, 9);
						contentStream.showText(builder.toString());	
						contentStream.endText();	
					}
					
					contentStream.beginText();
					contentStream.newLineAtOffset(2*perMM, 11*perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(cjRcvrClsfAddr);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(2*perMM, 8*perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(dlvMsg);
					contentStream.endText();
					
					contentStream.beginText();
					contentStream.newLineAtOffset(55*perMM, 30*perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText(buySite);
					contentStream.endText();
				}
				
				if (orgStation.equals("082") && dstnNation.equals("KR")) {	
				} else {
					contentStream.beginText();
					contentStream.newLineAtOffset(55*perMM, 25*perMM);
					contentStream.setFont(NanumGothic, 8);
					contentStream.showText("자가사용 목적 통관물품 상용판매시 처벌 받을수 있습니다");
					contentStream.endText();
				}
			
				Barcode barcodes = BarcodeFactory.createCode128(hawbNo);
				barcodes.setLabel("Barco");
				barcodes.setDrawingText(true);

				String barcodePath = pdfPath2 +hawbNo+".JPEG";
				File barcodefile = new File(barcodePath);
				
				try {
					BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
				} catch (OutputException e) {
					e.printStackTrace();
				}
				

				PDImageXObject hawbBarCode = PDImageXObject.createFromFileByContent(barcodefile, doc);
				contentStream.drawImage(hawbBarCode, 73*perMM, 70*perMM, 38*perMM, 5*perMM);
				
				contentStream.drawImage(hawbBarCode, 83*perMM, 3*perMM, 35*perMM, 11*perMM);
				
				contentStream.beginText();
				contentStream.newLineAtOffset(90*perMM, 0*perMM);
				contentStream.setFont(NanumGothic, 7);
				contentStream.showText(hawbNo);
				contentStream.endText();
				
				if(cJTerminalVo.getErrorCd().equals("0")) {
					barcodes = BarcodeFactory.createCode128(cjDlvClsfCd);
					barcodePath = pdfPath2 +"cjDlvClsfCd"+".JPEG";
					barcodefile = new File(barcodePath);
					try {
						BarcodeImageHandler.saveJPEG(barcodes, barcodefile);
					} catch (OutputException e) {
						e.printStackTrace();
					}
				
					PDImageXObject cjDlvClsfCdBarCode = PDImageXObject.createFromFileByContent(barcodefile, doc);
					contentStream.drawImage(cjDlvClsfCdBarCode, 3*perMM, 77*perMM, 35*perMM, 15*perMM);
				}
				
				
				File aciLogFile = new File(markPath);
				PDImageXObject aciLog = PDImageXObject.createFromFileByContent(aciLogFile, doc);
				contentStream.drawImage(aciLog, 3*perMM, 15*perMM, 30*perMM, 15*perMM);
				contentStream.close();
				
			
				if( barcodefile.exists() ){ 
					if(barcodefile.delete()){ 
						System.out.println("파일삭제 성공"); 
					}else{ 
							System.out.println("파일삭제 실패"); 
					}
				}
				
				
				totalPage++;
				packingInfo = comnMapper.selectPdfPrintItemInfo(nno);
				if (orderType.toLowerCase().equals("y")) {
					pageStandard = new PDRectangle(123*perMM, 100*perMM);
					System.out.println(pageStandard.getWidth() +" : "+ pageStandard.getHeight());
					blankPage = new PDPage(pageStandard);
					doc.addPage(blankPage);
					page = doc.getPage(totalPage);
					PDPageContentStream cts = new PDPageContentStream(doc, page);
					
					cts.drawLine(10, 270, (pageStandard.getWidth()) - 10, 270);
					cts.drawLine(10, 220, (pageStandard.getWidth()) - 10, 220);
					cts.drawLine(10, 270, 10, 220);
					cts.drawLine((pageStandard.getWidth()) - 10, 270, (pageStandard.getWidth()) - 10, 220);
					cts.drawLine((pageStandard.getWidth()) - 70, 270, (pageStandard.getWidth()) - 70, 220);
					cts.drawLine((pageStandard.getWidth()) - 70, 250, (pageStandard.getWidth()) - 10, 250);
					
					cts.beginText();
					cts.setFont(NanumGothic, 20);
					cts.newLineAtOffset(14.5f, 245f);
					cts.showText(hawbNo);
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 14);
					cts.newLineAtOffset(15f, 227f);
					cts.showText(orderNo);
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 63.5f, 257);
					cts.showText("총 내품수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 12);
					cts.newLineAtOffset((pageStandard.getWidth()) - 45, 233.5f);
					cts.showText(String.valueOf(totalItemCnt));
					cts.endText();
					
					cts.drawLine(10, 220, 10, 200);
					cts.drawLine((pageStandard.getWidth()) - 10, 220, (pageStandard.getWidth()) - 10, 200);
					cts.drawLine(10, 200, (pageStandard.getWidth()) - 10, 200);
					
					cts.drawLine((pageStandard.getWidth()) - 135, 220, (pageStandard.getWidth()) - 135, 200);
					cts.drawLine((pageStandard.getWidth()) - 90, 220, (pageStandard.getWidth()) - 90, 200);
					cts.drawLine((pageStandard.getWidth()) - 55, 220, (pageStandard.getWidth()) - 55, 200);
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset(15, 205.5f);
					cts.showText("내품명");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 130, 205.5f);
					cts.showText("사입코드");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 82, 205.5f);
					cts.showText("수량");
					cts.endText();
					
					cts.beginText();
					cts.setFont(NanumGothic, 10);
					cts.newLineAtOffset((pageStandard.getWidth()) - 45, 205.5f);
					cts.showText("Rack");
					cts.endText();

					int limit = 47;
					float startLine = 188;
					String itemName = "";
					
					for (int items = 0; items < packingInfo.size(); items++) {
						itemName = packingInfo.get(items).get("itemDetail").toString();
						
						if (itemName.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
							limit = 37;
						} 
						int arraySize = (int) Math.ceil((double) itemName.length() / limit);
						String[] stringArr = new String[arraySize];
						
						int index = 0;
						for (int start = 0; start < itemName.length(); start += limit) {
							stringArr[index++] = itemName.substring(start, Math.min(start+limit, itemName.length()));
						}
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 132, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("takeInCode").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 79, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("itemCnt").toString());
						cts.endText();
						
						cts.beginText();
						cts.setFont(NanumGothic, 9);
						cts.newLineAtOffset((pageStandard.getWidth()) - 47, startLine - 3.5f);
						cts.showText(packingInfo.get(items).get("rackCode").toString());
						cts.endText();
						
						for (int x = 0; x < stringArr.length; x++) {
							cts.beginText();
							cts.setFont(NanumGothic, 8);
							cts.newLineAtOffset(12, startLine - 3.5f);
							cts.showText(stringArr[x]);
							cts.endText();
							
							if (x == stringArr.length-1)  {
								startLine -= 17;
							} else {
								startLine -= 15;
							}
						}
					}
					float endLine = startLine;
					
					cts.drawLine(10, 200, (pageStandard.getWidth()) - 10, 200);
					cts.drawLine(10, 205.5f, 10, endLine+6.5f);
					cts.drawLine((pageStandard.getWidth()) - 10, 205.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					cts.drawLine(10, endLine+6.5f, (pageStandard.getWidth()) - 10, endLine+6.5f);
					cts.close();
					
					totalPage++;
					
				}
				
				}
				
			
				fileName = URLEncoder.encode("샘플PDF", "UTF-8");
				response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
					doc.save(response.getOutputStream());
					doc.close();
				} catch (UnsupportedEncodingException e) {
				
					e.printStackTrace();
				}

	}

	public CJOrderVo selectCjNnoOne(CJParameterVo cJPatemterOne) throws Exception{
		// TODO Auto-generated method stub
		return cjMapper.selectCjNnoOne(cJPatemterOne);
	}

}
