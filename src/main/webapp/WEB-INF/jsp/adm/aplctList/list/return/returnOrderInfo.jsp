<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');
	
	* {
		font-family: Helvetica, sans-serif;
	}
	
	.table {
		margin: 0 auto;
	}
	
	.table thead th {
		background: rgba(76, 144, 189, 0.72);
		color:#fff;
		text-align: center;
	}
	
	#shipping_table {
		border:none;
	}
	
	#wms_btn {
		border: none;
		border-radius: 5px;
		transition: all 0.2s;
		background: rgba(76, 144, 189, 0.8);
		color: #fff;
		padding: 5px 10px;
	}
	
	#wms_btn:hover {
		background: #4c90bd;
	}
	
	#normal_btn {
		border: none;
		border-radius: 5px;
		transition: all 0.2s;
		background: #00AE68;
		color: #fff;
		padding: 5px 10px;
	}
	
	#normal_btn:hover {
		background: #21825B;
	}
	
	label {
		margin-top: 2px;
	}
	
	input[type="radio"] {
		vertical-align: text-top;
	}
	
	.table {
		border: 1px solid #ddd;
		border-collapse: collapse;
	}
	
	.table tbody th {
		background: rgba(204, 204, 204, 0.19);
		width: 10%;
		text-align: center;
	}
	
	.table input {
		border: none;
		outline: none;
	}
	
	
	.invInfo_btn {
		padding: 5px 10px;
		border-radius:5px;
		color: #fff;
		border: none;
		background: rgba(76, 144, 189, 0.72);
		margin-left:2px;
	}
	
	.invInfo_btn:hover {
		color: #fff;
		background:rgba(76, 144, 189, 0.5);
	}
	
	input[type="file"]:focus {
		outline:none;
		border:none;
	}
	
	input[type="file"]:hover {
		cursor: pointer;
	}
	
	#cancelBtn {
		width: 100px;
		height: 40px;
		cursor: pointer;
		color: #fff;
		background-color: #84063f;
		border: none;
		box-shadow: 0 4px 16px rgba(106, 62, 82, 0.3);
		transition: 0.3s;
		font-size: 14px;
	}
	
	#cancelBtn:hover {
		background-color: rgba(106, 62, 82, 0.9);
		box-shadow: 0 2px 4px rgba(106, 62, 82, 0.6);
	}
	
	#updateReturn {
		width: 100px;
		height: 40px;
		cursor: pointer;
		color: #fff;
		background-color: #527d96;
		border: none;
		box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
		transition: 0.3s;
		font-size: 14px;
		margin-left:10px;
	}
	
	#updateReturn:hover {
		background-color: rgba(82, 125, 150, 0.9);
		box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
	}
	
	.modal.modal-center {
	  text-align: center;
	}
	
	@media screen and (min-width: 768px) { 
	  .modal.modal-center:before {
	    display: inline-block;
	    vertical-align: middle;
	    content: " ";
	    height: 100%;
	  }
	}
	
	.modal-dialog.modal-center {
	  display: inline-block;
	  text-align: left;
	  vertical-align: middle; 
	}
	 
	#agreeModal {
	 	width: 40%;
	}
	
	.modal-body {
		font-family: 'Nanum Gothic', sans-serif;
	}
	
	.modal-header {
		background: rgba(76, 144, 189, 1);
		font-weight: bold;
		color: #fff;
	}
	
	.modal-footer {
		background: white;
	}
	

	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head> 
	<title>WMS.ESHOP</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner" id = "main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								반품
							</li>
							<li class="active">
								반품 정보
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="post">
							<input type="hidden" name="_method" value="patch"/>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="sellerId" id="sellerId" value="${orderInfo.sellerId}"/>
							<input type="hidden" id="trk_com" value="${orderInfo.reTrkCom}"/>
							<input type="hidden" id="trk_no" value="${orderInfo.reTrkNo}"/>
							<input type="hidden" id="state" name="state" value="${orderInfo.state}"/>
 							<%-- <input type="hidden" id="a002_date" value="${orderInfo.a002Date}"/> --%>
							<input type="hidden" id="pick_type" value="${pick_type}"/>
							<input type="hidden" name="koblNo" id="koblNo" value="${orderInfo.koblNo}"/>
							<input type="hidden" name="dstnNation" id="dstnNation" value="${orderInfo.dstnNation}"/>
							<input type="hidden" id="nno" name="nno" value="${orderInfo.nno}"/>
							<div>
								<h2 style="margin-left:10px;">반품 정보</h2>
								<br/>
								<div>
									<table style="margin-left:20px;">
										<tr style="height:36px;">
											<th style="font-size:18px; width:100px;">운송장번호</th>
											<td style="font-size:18px;">${orderInfo.koblNo}</td>
										</tr>
									</table>
									<table style="margin-left:20px;" id="nation_table">
										<tr style="height:36px;">
											<th style="font-size:18px; width:100px;">배송정보</th>
											<td style="font-size:18px;">${orderInfo.orgNationName} &nbsp;<i class="fa fa-fighter-jet" aria-hidden="true"></i>  &nbsp;${orderInfo.dstnNationName}</td>
										</tr>
									</table>
								</div>
								<br/>
								<div>
									<div style="float:right;margin-right:20px;">
										<p class="red" style="font-weight:bold;">* <font style="color:black;font-weight:normal;">필수 입력값입니다</font></p>
									</div>
									<table style="margin-left:20px;">
										<tr>
											<th style="font-size:18px;"><font class="red" style="margin-right:5px;">*</font>픽업 방법</th>
											<td style="padding-left:14px; width:200px; vertical-align:middle;">
												<input type="radio" name="pickType" value="B" id="pickType_B" <c:if test="${orderInfo.pickType eq 'B'}"> checked</c:if>>
												<label for="pickType_B" class="radio_label">회수 요청</label>
												<input type="radio" name="pickType" value="A" id="pickType_A" style="margin-left:10px;" <c:if test="${orderInfo.pickType eq 'A'}"> checked</c:if>>
												<label for="pickType_A">직접 발송</label>
											</td>
										</tr>
									</table>
									<br/>
									<table class="table" style="width:98%;">
										<thead>
											<tr>
												<th colspan="2">반송 택배 정보</th>
												<th colspan="2">반품 담당자 정보</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<th style="width:10%; text-align:center;"><font class="red hide" id="reTrkCom_ft" style="margin-right:5px;">*</font>택배 회사</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%; height:36px;" name="reTrkCom" id="reTrkCom" value="${orderInfo.reTrkCom}" >
												</td>
												<th style="width:10%; text-align:center;">이름</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%; height:36px;" name="attnName" id="attnName" value="${orderInfo.attnName}">
												</td>
											</tr>
											<tr>
												<th style="width:10%; text-align:center;"><font class="red hide" id="reTrkNo_ft" style="margin-right:5px;">*</font>송장 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%; height:36px;" name="reTrkNo" id="reTrkNo" value="${orderInfo.reTrkNo}">
												</td>
												<th style="width:10%; text-align:center;">연락처</th>
												<td style="padding:0px;">
													<input type="text" numberOnly style="width:100%; height:36px;" name="attnTel" id="attnTel" value="${orderInfo.attnTel}">
												</td>
											</tr>
											<tr>
												<th style="width:10%; text-align:center;">등록 일자</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%; height:36px;" name="a002Date" id="a002Date" value="${orderInfo.a002Date}">
												</td>
												<th style="width:10%; text-align:center;">이메일</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%; height:36px;" name="attnEmail" id="attnEmail" value="${orderInfo.attnEmail}">
												</td>
											</tr>
										</tbody>
									</table>
									<c:if test="${empty orderInfo.attnName}">
										<div style="text-align:right; margin-right:20px; margin-top:10px;">
											<input type="button" class="invInfo_btn" onclick="fn_setInvoiceInfo()" value="청구지 정보">
											<input type="button" class="invInfo_btn" onclick="fn_setCurrentInfo()" value="최근 정보">
										</div>
									</c:if>
									<br/>
									<table class="table" style="width:98%;">
										<thead>
											<tr>
												<th colspan="4">반품 회수지 정보</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<th>자사 정산 ID</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="calculateId" id="calculateId" value="${orderInfo.calculateId}">
												</td>
												<th>주문 접수 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="orderReference" id="orderReference" value="${orderInfo.orderReference}">
												</td>
											</tr>
											<tr>
												<th><font class="red" id="orderNo_ft" style="margin-right:5px;">*</font>주문번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="orderNo" id="orderNo" value="${orderInfo.orderNo}">
												</td>
												<th>주문일자</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="orderDate" id="orderDate" value="${orderInfo.orderDate}">
												</td>
											</tr>
											<tr>
												<th><font class="red" id="pickupName_ft" style="margin-right:5px;">*</font>이름</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="pickupName" id="pickupName" value="${orderInfo.pickupName}">
												</td>
												<th><font class="red" id="pickupZip_ft" style="margin-right:5px;">*</font>우편번호</th>
												<td style="padding:0px;">
													<input type="text" zipCodeOnly style="width:100%;height:36px;" name="pickupZip" id="pickupZip" value="${orderInfo.pickupZip}">
												</td>
											</tr>
											<tr>
												<th><font class="red hide" id="pickupAddr_ft" style="margin-right:5px;">*</font>주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="pickupAddr" id="pickupAddr" value="${orderInfo.pickupAddr}">
												</td>
												<th>
												<c:if test="${orderInfo.dstnNation ne 'KR'}">
													<font class="red" id="pickupEngAddr_ft" style="margin-right:5px;">*</font>
												</c:if>
													영문주소
												</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="pickupEngAddr" id="pickupEngAddr" value="${orderInfo.pickupEngAddr}">
												</td>
											</tr>
											<tr>
												<th><font class="red" id="pickupTel_ft" style="margin-right:5px;">*</font>전화번호</th>
												<td style="padding:0px;">
													<input type="text" numberOnly style="width:100%;height:36px;" name="pickupTel" id="pickupTel" value="${orderInfo.pickupTel}">
												</td>
												<th>
													<font class="red hide" id="pickupMobile_ft">*&nbsp;</font>휴대전화번호
												</th>
												<td style="padding:0px;">
													<input type="text" numberOnly style="width:100%;height:36px;" name="pickupMobile" id="pickupMobile" value="${orderInfo.pickupMobile}">
												</td>
											</tr>
										</tbody>
									</table>
									<br/>
									<table class="table" style="width:98%;">
										<thead>
											<tr>
												<th colspan="4">반품 도착지 정보</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<th><font class="red" id="senderName_ft" style="margin-right:5px;">*</font>회사명</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="senderName" id="senderName" value="${orderInfo.senderName}">
												</td>
												<th><font class="red" id="senderZip_ft" style="margin-right:5px;">*</font>우편번호</th>
												<td style="padding:0px;">
													<input type="text" zipCodeOnly style="width:100%;height:36px;" name="senderZip" id="senderZip" value="${orderInfo.senderZip}">
												</td>
											</tr>
											<tr>
												<th><font class="red hide" id="senderState_ft" style="margin-right:5px;">*</font>주</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="senderState" id="senderState" value="${orderInfo.senderState}">
												</td>
												<th><font class="red hide" id="senderCity_ft" style="margin-right:5px;">*</font>도시</th>
												<td style="padding:0px">
													<input type="text" style="width:100%;height:36px;" name="senderCity" id="senderCity" value="${orderInfo.senderCity}">
												</td>
											</tr>
											<tr>
												<th><font class="red" id="senderAddr_ft" style="margin-right:5px;">*</font>주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="senderAddr" id="senderAddr" value="${orderInfo.senderAddr}">
												</td>
												<th><font class="red" id="senderTel_ft" style="margin-right:5px;">*</font>전화번호</th>
												<td style="padding:0px;">
													<input type="text" numberOnly style="width:100%;height:36px;" name="senderTel" id="senderTel" value="${orderInfo.senderTel}">
												</td>
											</tr>
											<tr>
												<th><font class="red hide" id="senderBuildNm_ft" style="margin-right:5px;">*</font>빌딩 명</th>
												<td style="padding:0px;" colspan="3">
													<input type="text" style="width:100%;height:36px;" name="senderBuildNm" id="senderBuildNm" value="${orderInfo.senderBuildNm}">
												</td>
											</tr>
										</tbody>
									</table>
									<br/><br/>
									<table style="margin-left:20px;">
										<tr>
											<th style="font-size:18px;"><font class="red" style="margin-right:5px;">*</font>반송 구분</th>
											<td style="padding-left:14px; width:200px; vertical-align:middle;">
												<input type="radio" name="returnType" value="N" id="returnType_N" <c:if test="${orderInfo.returnType eq 'N'}"> checked</c:if> >
												<label for="returnType_N" class="radio_label">일반</label>
												<input type="radio" name="returnType" value="E" id="returnType_E" <c:if test="${orderInfo.returnType eq 'E'}"> checked</c:if> style="margin-left:10px;">
												<label for="returnType_E">긴급</label>
											</td>
										</tr>
									</table>
									<br/>
									<table class="table" style="width:98%;">
										<tbody>
											<tr>
												<th><font class="red" style="margin-right:5px;">*</font>반송 사유</th>
												<td style="vertical-align:middle; background:#fff;">
													<input type="radio" name="returnReason" value="A" id="returnReason_A" <c:if test="${orderInfo.returnReason eq 'A'}"> checked</c:if>>
													<label for="returnReason_A">단순변심</label>
													<input type="radio" name="returnReason" value="B" id="returnReason_B" <c:if test="${orderInfo.returnReason eq 'B'}"> checked</c:if> style="margin-left:10px;">
													<label for="returnReason_B">파손</label>
													<input type="radio" name="returnReason" value="C" id="returnReason_C" <c:if test="${orderInfo.returnReason eq 'C'}"> checked</c:if> style="margin-left:10px;">
													<label for="returnReason_C">불량</label>
													<input type="radio" name="returnReason" value="D" id="returnReason_D" <c:if test="${orderInfo.returnReason eq 'D'}"> checked</c:if> style="margin-left:10px;">
													<label for="returnReason_D">기타</label>
												</td>
											</tr>
											<tr>
												<th><font class="red hide" id="returnReasonDetail_ft" style="margin-right:5px;">*</font>반송 사유 상세</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" name="returnReasonDetail" id="returnReasonDetail" value="${orderInfo.returnReasonDetail}">
												</td>
											</tr>
										</tbody>
									</table>
									<br/><br/>
									<table class="table" style="width:98%;">
										<thead>
											<tr>
												<th colspan="3">위약 반송 정보 <small style="padding: 6px;">* 관부가세 환급시 반드시 작성해 주세요</small></th>
											</tr>
										</thead>
										<tbody>
											<tr style="height:41px;">
												<th class="center colorBlack" style="width:10%; vertical-align:middle;"><font class="red" style="margin-right:5px;">*</font>위약 반송</th>
												<td style="vertical-align:middle; background:white;" colspan="2">
													<c:if test="${orderInfo.taxType eq 'Y'}">
														<input type="radio" name="taxType" id="taxType_Y" value="Y" checked disabled><label for="taxType_Y">&nbsp;위약 반송</label>
														&nbsp;<input type="radio" name="taxType" id="taxType_N" value="N" disabled><label for="taxType_N">&nbsp;일반 반송</label>
														<input type="hidden" name="taxReturn" id="taxReturn" value="C">
													</c:if>
													<c:if test="${orderInfo.taxType eq 'N'}">
														<input type="radio" name="taxType" id="taxType_Y" value="Y"><label for="taxType_Y">&nbsp;위약 반송</label>
														&nbsp;<input type="radio" name="taxType" id="taxType_N" value="N" checked><label for="taxType_N">&nbsp;일반 반송</label>
														<input type="hidden" name="taxReturn" id="taxReturn" value="C">
													</c:if>
												</td>
											</tr>
											<c:if test="${orderInfo.taxType eq 'Y'}">
												<tr>
													<th class="center" style="width:12%;">반송 사유서</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileReason}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
												<tr>
													<th class="center" style="width:12%;">반품 접수 캡쳐본</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileCapture}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
												<tr>
													<th class="center" style="width:12%;">반품 메신저 캡쳐본</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileMessenger}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
												<tr>
													<th class="center" style="width:12%;">반품 Commercial Invoice</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileCl}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
												<tr>
													<th class="center" style="width:12%;">환급 통장 사본</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileCopyBank}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
												<tr>
													<th class="center" style="width:12%;">수출 신고 필증</th>
													<td style="width:88%; background:#fff;" colspan="2">
														<a href="${orderInfo.fileIc}" target="_blank" download>파일 다운로드</a>
													</td>
												</tr>
											</c:if>
											<c:if test="${orderInfo.taxType eq 'N'}">
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnReason_ft" style="margin-right:5px;">*</font>반송 사유서</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason"></td>
													<td style="vertical-align: middle; background:white;">
														<a href="/반송 사유서.xlsx" download>[반송사유서 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCapture_ft" style="margin-right:5px;">*</font>반품 접수 캡쳐본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture"></td>
													<td style="background:white;"></td>
												</tr>
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnMessenger_ft" style="margin-right:5px;">*</font>반품 메신저 캡쳐본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger"></td>
													<td style="background:white;"></td>
												</tr>
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCl_ft" style="margin-right:5px;">*</font>반품 Commercial Invoice</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl" ></td>
													<td style="vertical-align: middle; background:white;">
														<a href="/반품 커머셜 인보이스.xlsx" download>[반품 커머셜 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCopyBank_ft" style="margin-right:5px;">*</font>환급 통장 사본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank"></td>
													<td style="background:white;"></td>
												</tr>
												<tr class="hideTax">
													<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnIc_ft" style="margin-right:5px;">*</font>수출신고필증</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnIc" name="rtnIc"></td>
													<td style="background:white;"></td>
												</tr>
											</c:if>
										</tbody>
									</table>
									<br/>
									<table class="table" style="width:98%;">
										<tbody>
											<tr>
												<th>입고 요청사항</th>
												<td style="padding:0px;">
													<input style="width:100%; height:36px;" type="text" name="whMsg" id="whMsg" class="comm_text" value="${orderInfo.whMsg}">
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<br/>
							<div id="item_info" style="width:96%; margin:0 auto;">
								<div class="row">
									<div id="addForm" style="margin-top:5px;">
										<c:forEach items="${itemList}" var="item" varStatus="status">
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="col-xs-12 col-lg-12" style="font-size: 3;">
													<h4>상품 ${status.index+1}</h4>
													<div class="hr dotted"></div>
													<c:if test="${orderInfo.dstnNation ne 'JP'}">
														<div class="row hr-8">
															<div class="col-xs-1 center mp07">
																<label>SUB NO</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 require" type="text" name="subNo" id="subNo" value="${item.subNo}" readonly/>
															</div>
															<div class="col-xs-1 center mp07">
																<label>상품명</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 입력해 주세요." />
															</div>
														</div>
													</c:if>
													<c:if test="${orderInfo.dstnNation eq 'JP'}">
														<div class="row hr-8">
															<div class="col-xs-1 center mp07">
																<label>SUB NO</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 require" type="text" name="subNo" id="subNo" value="${item.subNo}" readonly/>
															</div>
														</div>
														<div class="hr dotted"></div>
														<div class="row hr-8">
															<div class="col-xs-1 center mp07">
																<label>상품명</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 입력해 주세요." />
															</div>
															<div class="col-xs-1 center mp07">
																<label>상품명 (일문)</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
															</div>
															<div class="col-xs-1 center mp07">
																<label>상품코드</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
															</div>
														</div>
													</c:if>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">브랜드</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="brand" id="brand" value="${item.brand}"/>
														</div>
														<div class="col-xs-1 center">상품 무게</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 require" priceOnly type="text" name="itemWta" id="itemWta" value="${item.itemWta}" />
														</div>
														<div class="col-xs-1 center">무게 단위</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row form-group hr-8">
														<div class="col-xs-1 center">상품 수량</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 require" numberOnly type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
														</div>
														<div class="col-xs-1 center mp07">상품 가격</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 require" priceOnly type="text" name="unitValue" id="unitValue" value="${item.unitValue}" />
														</div>
														<div class="col-xs-1 center mp07">통화</div>
														<div class="col-xs-3 center ">
															<input class="col-xs-12" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label>제조국</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 require" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>제조회사</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>
															<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
															<i class="fa fa-exclamation-circle"></i>
															</a></label>
														</div>
														<div class="col-xs-3 center ">
															<input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="${item.hsCode}"/>
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label>상품 URL</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>상품 이미지 URL</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
														</div>
													</div>
													<div class="hr dotted"></div>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<br/>
							<div style="text-align:center; margin-top: 20px;" id="regist_div">
								<input type="button" class="cancelBtn" id="cancelBtn" value="취소">
								<input type="button" class="updateReturn" id="updateReturn" value="정보 수정">
							</div>
						</form>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- script on paging start -->
		
		<script src="/assets/js/jquery.dataTables.min.js"></script>
		<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/assets/js/dataTables.buttons.min.js"></script>
		<script src="/assets/js/buttons.flash.min.js"></script>
		<script src="/assets/js/buttons.html5.min.js"></script>
		<script src="/assets/js/buttons.print.min.js"></script>
		<script src="/assets/js/buttons.colVis.min.js"></script>
		<script src="/assets/js/dataTables.select.min.js"></script>
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min2.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
	               $("#14thMenu-2").toggleClass('open');
	               $("#14thMenu-2").toggleClass('active'); 
	               $("#14thOne-2").toggleClass('active');   
	   
					var picked = $("#pick_type").val();
					if (picked == 'B') {
						$("#reTrkCom").prop("disabled", true);
						$("#reTrkNo").prop("disabled", true);
						$("#a002Date").prop("disabled", true);
					}
				})
				
				$("input[name='taxType']").on('click', function() {
					if($(this).val() == 'Y') {
						$("#rtnReason_ft").removeClass('hide');
						$("#rtnCapture_ft").removeClass('hide');
						$("#rtnMessenger_ft").removeClass('hide');
						$("#rtnCl_ft").removeClass('hide');
						$("#rtnCopyBank_ft").removeClass('hide');
						$("#rtnIc_ft").removeClass('hide');
					} else {
						$("#rtnReason_ft").addClass('hide');
						$("#rtnCapture_ft").addClass('hide');
						$("#rtnMessenger_ft").addClass('hide');
						$("#rtnCl_ft").addClass('hide');
						$("#rtnCopyBank_ft").addClass('hide');
						$("#rtnIc_ft").addClass('hide');
					}
				})
				
				$("input[name='pickType']").on('click', function() {
					var reTrkCom = $("#trk_com").val();
					var reTrkNo = $("#trk_no").val();
					var reTrkDate = $("#a002_date").val();
	
					if (reTrkDate == "        ") {
						reTrkDate = "";	
					}
					
					if($(this).val() == "A") {
						$("#reTrkCom_ft").removeClass('hide');
						$("#reTrkNo_ft").removeClass('hide');
						$("#reTrkCom").prop('disabled', false);
						$("#reTrkNo").prop('disabled', false);
						$("#a002Date").prop('disabled', false);
						$("#reTrkCom").val(reTrkCom);
						$("#reTrkNo").val(reTrkNo);
						$("#a002Date").val(reTrkDate);
					} else {
						$("#reTrkCom_ft").addClass('hide');
						$("#reTrkNo_ft").addClass('hide');
						$("#a002Date").val('');
						$("#reTrkCom").val('');
						$("#reTrkNo").val('');
						$("#reTrkCom").prop("disabled", true);
						$("#reTrkNo").prop("disabled", true);
						$("#a002Date").prop("disabled", true);
						$("#a002Date").attr("placeholder", "");
					}
				})
				
				$("input[name='returnReason']").on('click', function() {
					if ($(this).val() == 'D') {
						$("#returnReasonDetail_ft").removeClass('hide');
					} else {
						$("#returnReasonDetail_ft").addClass('hide');
					}
				})
				
				$("#cancelBtn").on('click', function() {
					location.href = "/mngr/order/return/list";
				})
	
				$("#updateReturn").on('click', function() {
					var dest = $("#dstnNation").val();
	
					if ($("#orderNo").val() == "") {
						$("#orderNo").focus();
						return false;
					}
	
					if ($("#pickupName").val() == "") {
						$("#pickupName").focus();
						return false;
					}
	
					if ($("#pickupZip").val() == "") {
						$("#pickupZip").focus();
						return false;
					}
	
					if ($("#pickupTel").val() == "") {
						$("#pickupTel").focus();
						return false;
					}
	
					if ($("#senderName").val() == "") {
						$("#senderName").focus();
						return false;
					}
	
					if ($("#senderZip").val() == "") {
						$("#senderZip").focus();
						return false;
					}
	
					if ($("#senderAddr").val() == "") {
						$("#senderAddr").focus();
						return false;
					}
	
					if ($("#senderTel").val() == "") {
						$("#senderTel").focus();
						return false;
					}
					
					$("input[name='itemDetail']").each(function(index, item) {
						if ($(item).val() == "") {
							$(item).focus();
							return false;
						}
					})
					
					$("input[name='itemWta']").each(function(index, item) {
						if ($(item).val() == "") {
							$(item).focus();
							return false;
						}
					})
					
					$("input[name='itemCnt']").each(function(index, item) {
						if ($(item).val() == "") {
							$(item).focus();
							return false;
						}
					})
					
					$("input[name='unitValue']").each(function(index, item) {
						if ($(item).val() == "") {
							$(item).focus();
							return false;
						}
					})
					
					$("input[name='makeCntry']").each(function(index, item) {
						if ($(item).val() == "") {
							$(item).focus();
							return false;
						}
					})
					
					
					if (dest == 'KR') {
						if ($("#pickupAddr").val() == "") {
							$("#pickupAddr").focus();
							return false;
						}
					} 
	
					if (dest != 'KR') {
						if ($("#pickupEngAddr").val() == "") {
							$("#pickupEngAddr").focus();
							return false;
						}
					}
	
					if (dest == 'US') {
						if ($("#senderState").val() == "") {
							$("#senderState").focus();
							return false;
						}
	
						if ($("#senderCity").val() == "") {
							$("#senderCity").focus();
							return false;
						}
	
						if ($("#senderBuildNm").val() == "") {
							$("#senderBuildNm").focus();
							return false;
						}
					}
	
					if (dest == 'JP') {
						if ($("#pickupMobile").val() == "") {
							$("#pickupMobile").focus();
							return false;
						}
	
						$("input[name='nativeItemDetail']").each(function(index, item) {
							if ($(item).val() == "") {
								$(item).focus();
								return false;
							}
						})
	
						$("input[name='cusItemCode']").each(function(index, item) {
							if ($(item).val() == "") {
								$(item).focus();
								return false;
							}
						})
	
						$("input[name='hsCode']").each(function(index, item) {
							if ($(item).val() == "") {
								$(item).focus();
								return false;
							}
						})
						
						$("input[name='itemImgUrl']").each(function(index, item) {
							if ($(item).val() == "") {
								$(item).focus();
								return false;
							}
						})
					}
	
					if ($("input[name='returnReason']:checked").val() == 'D') {
						if ($("#returnReasonDetail").val() == "") {
							$("#returnReasonDetail").focus();
							return false;
						}
					}
	
					if ($("input[name='taxType']:checked").val() == 'Y') {
						for (var i = 0 ; i < $(".taxTypeValue").size(); i++) {
							if ($($(".taxTypeValue")[i]).val().length == 0) {
								alert("위약반송 필수 서류가 비어있습니다.");
								return false;
							}
						}
					}
	
					if ($("input[name='pickType']:checked").val() == 'A') {
						if ($("#reTrkCom").val() == "") {
							$("#reTrkCom").focus();
							return false;
						}
	
						if ($("#reTrkNo").val() == "") {
							$("#reTrkNo").focus();
							return false;
						} 
					}

					console.log($("#sellerId").val());
					$("#returnForm").attr("action","/mngr/aplctList/return/"+$("#orderReference").val());
					$("#returnForm").submit();
					/* 
					if(confirm("해당 반품 건은 더 이상 수정하실 수 없습니다. 진행 하시겠습니까?")) {
						$("#returnForm").attr("action","/mngr/aplctList/return/"+$("#orderReference").val());
						$("#returnForm").submit();	
					} else {
						return false;
					} */
	
	
					
				})
				
				if(!ace.vars['touch']) {
					$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true }); 
					//resize the chosen on window resize
			
					$(window)
					.off('resize.chosen')
					.on('resize.chosen', function() {
						$('.chosen-select-start').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						})
					}).trigger('resize.chosen');
					//resize chosen on sidebar collapse/expand
					$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
						if(event_name != 'sidebar_collapsed') return;
						$('.chosen-select-start').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						})
					});
				}
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
