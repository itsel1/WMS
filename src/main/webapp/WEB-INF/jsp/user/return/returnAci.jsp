<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
		@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');

	  .modal.modal-center {
		  text-align: center;
		}
		
		#addCost:focus {
			border: 1px solid #3296FF;
		}
		
		#etcDetail:focus {
			border: 1px solid #3296FF;
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
		 
		 .modal-header {
		 	background:#6495ED;
		 	color:#fff;
		 }
		 
		 .modal-footer {
		 	background:white;
		 }
		 
		 #koblNo_btn {
		 	height: 38px;
		 }
		 
		 .table th, td {
		 	border: 1px solid #6a85b6;
		 	border-collapse: collapse;
		 }
		 
		 .table thead th {
		 	background: rgba(76, 144, 189, 0.72);
		 	color:#fff;

		}
		
		.table tbody th {
			border: 1px solid #6a85b6;
			background: rgba(204, 204, 204, 0.19);
		}
		
		.table input {
			border: none;
			outline: none;
		}
		
		.table input:focus {
			border: 1px solid #6a85b6;
		}
		
		.table thead tr {
			border-top: 1px solid #6a85b6;
			border-bottom: 1px solid #6a85b6;
		}
		
		input[type="file"]:focus {
			outline:none;
			border:none;
		}
		
		input[type="file"]:hover {
			cursor: pointer;
		}
		
		#registReturn_aci {
			width: 100px;
			height: 40px;
			cursor: pointer;
			color: #fff;
			background-color: #527d96;
			border: none;
			box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
			transition: 0.3s;
			font-size: 14px;
		}
		
		#registReturn_aci:hover {
			background-color: rgba(82, 125, 150, 0.9);
			box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
		}
		
		.inner-content-side {
			font-family: 'Nanum Gothic', sans-serif;

		}
		
		.station_table input[type="text"] {
			border: none;
		}
		

	</style>
	</head>
	<title>반품 접수 </title>
	<body class="no-skin"> 
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<div class="main-container ace-save-state" id="main-container">
				<div class="main-content">
					<div class="main-content-inner">
						<div class="toppn">
							<div class="container">
								<div class="row">
									<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>반품 관리</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품 접수</strong></div>
								</div>
							</div>
						</div>
						<div class="container">
							<div class="page-content">
								<div class="page-header">
									<h3 style="font-weight:bold;">
										반품 접수
									</h3>
								</div>
								<br/>
								<div class="inner-content-side">
									<div class="tab-content">
										<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="get" action="">
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
											<div class="row hr-8">
												<table class="noticeTable" style="width:100%;">
													<tr>
														<th style="font-size: 20px; font-weight: normal; text-align: left; padding: 10px 0 14px 0; background: white;">
															반품 사용 안내서
														</th>
													</tr>
													<tr>
														<td style="border:1px solid #ccc; font-size:14px; width:100%; height:140px; padding-left:10px;">
														ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.
														<br/>
														다만 아래의 경우에는 예외로 합니다.
														<br/><br/>
														- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
														<br/>
														- 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우
														</td>
													</tr>
													<tr style="border:none;">
														<td style="border:none;">
															<p style="margin-top:10px; margin-left:5px;">
															<label for="agreement_check"> <input type="checkbox" id="agreement_check" name="agreement_check"> 
																<font style="font-size: 14px; font-weight: bold;">동의 합니다.</font>
															</label>
															</p>
														</td>
													</tr>
												</table>
											</div>
											<br/>
											<div class="row" id="other_input" >
												<div class="col-xs-12 col-lg-12" style="margin-left:10px;">
													<font style="font-size:20px; font-weight:bold; color:black;">배송 정보 찾기</font>
													<p><c:if test="${eMsg ne '0'}">운송장 번호를 확인해주세요</c:if></p>
													<table class="" style="width:30%; border:none;">
														<thead>
															<tr style="background:#fff;">
																<td style="padding:0px; border:none;">
																	<input type="text" style="width:100%; height:38px;" name="koblNo" id="koblNo" value="${koblNo}" class="search_input" placeholder="운송장번호를 입력해주세요" required autocomplete="off"/>
																</td>
																<td style="width:100px; text-align:center; border:none;">
																	<label for="hawbBtn">
																		<input type="button" name="koblNo_btn" id="koblNo_btn" class="btn btn-sm btn-primary btn-white" value="검색" onclick="searchKobl()">
																		<input type="hidden" id="nno" name="nno" value="${nno}">
																	</label>
																</td>
															</tr>
														</thead>
													</table>
												</div>
											</div>
											<br/>
											<div class="wms_return hide">
												<div class="row">
													<table class="station_table" style="margin-left:20px;">
														<thead>
															<tr>
																<c:if test="${!empty nno}">
																	<th style="font-size:18px; width:70px; text-align:center;">출발지 :</th>
																	<td style="font-size:18px; border:none; padding:0px; width:60px;">${rtn.dstnNation}</td>
																	<th style="font-size:18px; width:70px; text-align:center;">도착지 :</th>
																	<td style="font-size:18px; border:none; padding:0px; width:60px;">${rtn.orgStation}</td>
																</c:if>
															</tr>
														</thead>
													</table>
													<%-- <label for="rtn_div">
														<font style="font-size: 18px; font-weight: bold; color: black;">출발지 ${rtn.dstnNation} / 도착지 ${rtn.orgStation}</font>
													</label> --%>
													<input type="hidden" name="dstnNation" id="dstnNation" value="${rtn.orgStation}"/>
												</div>
												<br/>
												<div class="row">
													<div class="col-xs-12 col-lg-12" style="margin-bottom:8px;">
														<div class="col-xs-9">
															<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-right:10px;">픽업 방법</font>
															<input style="margin-top:8px;" type="radio" name="pickType" value="B" id="pickType_B" class="rtn_radio" checked><label for="pickType_B">&nbsp;회수 요청</label>&nbsp;
															<input style="margin-top:8px;" type="radio" name="pickType" value="A" id="pickType_A" class="rtn_radio"><label for="pickType_A">&nbsp;직접 발송</label>
														</div>
														<div class="col-xs-3" style="text-align:right;">
															<p class="red" style="font-weight:bold;">* <font style="color:black;font-weight:normal;">필수 입력값입니다</font></p>
														</div>
													</div>
												</div>
												<div class="row">
													<table class="table table-bordered" id="reTrk" name="reTrk" style="margin-left:20px; width:98%;">
														<thead>
															<tr>
																<th class="center colorBlack" colspan="2">반송 택배 정보</th>
																<th class="center colorBlack" colspan="2">반품 담당자 정보</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red hide" id="reTrkCom_ft">*&nbsp;</font>택배 회사</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="" disabled name="reTrkCom" id="reTrkCom" value="${data.reTrkCom}" />
																</td>
																<th class="center colorBlack" style="width:10%;">이름</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" name="attnName" id="attnName" value="${data.attnName}" placeholder="미입력 시, 청구지 정보로 자동 설정됩니다."/>
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red hide" id="reTrkNo_ft">*&nbsp;</font>송장 번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="" disabled name="reTrkNo" id="reTrkNo" value="${data.reTrkNo}"/>
																</td>
																<th class="center colorBlack" style="width:10%;">연락처</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" numberOnly name="attnTel" id="attnTel" value="${data.attnTel}" />
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;">등록 날짜</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" disabled name="A002Date" id="A002Date" value="${data.a002Date}"/>
																</td>
																<th class="center colorBlack" style="width:10%;">이메일</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" name="attnEmail" id="attnEmail" value="${data.attnEmail}"/>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<br/>
												<div class="row">
													<table class="table table-bordered" style="margin-left:20px; width:98%;">
														<thead>
															<tr>
																<th class="center colorBlack" colspan="4">반품 회수지 정보</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<th class="center colorBlack" style="width:10%;">자사 정산 ID</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" name="calculateId" id="calculateId" value="${rtn.calculateId}" placeholder="미입력 시, 사용자 ID로 부여됩니다."/>
																</td>
																<th class="center colorBlack" style="width:10%;">주문 접수 번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" name="orderReference" id="orderReference" value="${rtn.orderReference}" placeholder="미입력 시, 자동으로 부여됩니다."/>
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="orderNo_ft">*&nbsp;</font>주문번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" name="orderNo" id="orderNo" value="${rtn.orderNo}"/>
																</td>
																<th class="center colorBlack" style="width:10%;">주문날짜</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" name="orderDate" id="orderDate" value="${rtn.orderDate}"/>
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="pickupName_ft">*&nbsp;</font>이름</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" name="pickupName" id="pickupName" value="${rtn.pickupName}"/>
																</td>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="pickupZip_ft">*&nbsp;</font>우편번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" zipCodeOnly class="require" name="pickupZip" id="pickupZip" value="${rtn.pickupZip}"/>
																</td>
															</tr>
															<tr>
																<c:if test="${rtn.orgStation eq 'KR'}">
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="pickupAddr_ft">*&nbsp;</font>주소
																	</th>
																	<td style="padding: 0px;">
																		<input type="text" style="width:100%;height:36px;" class="require" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr}"/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation ne 'KR'}">
																	<th class="center colorBlack" style="width:10%;">
																		주소
																	</th>
																	<td style="padding:0;">
																		<input type="text" style="width:100%;height:36px;" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr}"/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation ne 'KR'}">
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="pickupAddr_ft">*&nbsp;</font>영문주소
																	</th>
																	<td style="padding: 0px;">
																		<input type="text" style="width:100%;height:36px;" class="require" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr}"/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation eq 'KR'}">
																	<th class="center colorBlack" style="width:10%;">
																		영문주소
																	</th>
																	<td style="padding:0;">
																		<input type="text" style="width:100%;height:36px;" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr}"/>
																	</td>
																</c:if>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="pickupTel_ft">*&nbsp;</font>전화번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" numberOnly class="require" name="pickupTel" id="pickupTel" value="${rtn.pickupTel}" placeholder="숫자만 입력 가능합니다."/>
																</td>
																<c:if test="${rtn.orgStation eq 'JP'}">
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="pickupMobile_ft">*&nbsp;</font>휴대전화번호
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" numberOnly class="require" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile}" placeholder="숫자만 입력 가능합니다."/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation ne 'JP'}">
																	<th class="center colorBlack" style="width:10%;">
																		휴대전화번호
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" numberOnly name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile}" placeholder="숫자만 입력 가능합니다."/>
																	</td>
																</c:if>
															</tr>
														</tbody>
													</table>
												</div>
												<br/>
												<div class="row">
													<table class="table table-bordered" style="margin-left:20px; width:98%;">
														<thead>
															<tr>
																<th class="center colorBlack" colspan="4">반품 도착지 정보</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="senderName_ft">*&nbsp;</font>회사명</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" name="senderName" id="senderName" value="${rtn.senderName}" />
																</td>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="senderZip_ft">*&nbsp;</font>우편번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" zipCodeOnly class="require" name="senderZip" id="senderZip" value="${rtn.senderZip}" />
																</td>
															</tr>
															<tr class="stateOrcity">
																<c:if test="${rtn.orgStation eq 'US'}">
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="senderState_ft">*&nbsp;</font>주
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" class="require" name="senderState" id="senderState" value="${rtn.senderState}"/>
																	</td>
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="senderCity_ft">*&nbsp;</font>도시
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" class="require" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation ne 'US'}">
																	<th class="center colorBlack" style="width:10%;">
																		주
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" name="senderState" id="senderState" value="${rtn.senderState}"/>
																	</td>
																	<th class="center colorBlack" style="width:10%;">
																		도시
																	</th>
																	<td style="padding:0px;">
																		<input type="text" style="width:100%;height:36px;" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
																	</td>
																</c:if>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="senderAddr_ft">*&nbsp;</font>주소</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" name="senderAddr" id="senderAddr" value="${rtn.senderAddr}"/>
																</td>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="senderTel_ft">*&nbsp;</font>전화번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" numberOnly name="senderTel" id="senderTel" value="${rtn.senderTel}" placeholder="숫자만 입력 가능합니다."/>
																</td>
															</tr>
															<tr>
																<c:if test="${rtn.orgStation eq 'US'}">
																	<th class="center colorBlack" style="width:10%;">
																		<font class="red" id="senderBuildNm_ft">*&nbsp;</font>
																		빌딩 명
																	</th>
																	<td style="padding:0px;" colspan="3">
																		<input type="text" style="width:100%;height:36px;" class="require" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}"/>
																	</td>
																</c:if>
																<c:if test="${rtn.orgStation ne 'US'}">
																	<th class="center colorBlack" style="width:10%;">
																		빌딩 명
																	</th>
																	<td style="padding:0px;" colspan="3">
																		<input type="text" style="width:100%;height:36px;" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}"/>
																	</td>
																</c:if>
															</tr>
														</tbody>
													</table>
												</div>
												<br/>
												<div class="row">
													<div class="col-xs-12 col-lg-12">
														<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-left:10px; margin-right:10px;">반송 구분</font>
														<input type="radio" name="returnType" value="N" class="rtn_radio" id="returnType_N" checked><label for="returnType_N">&nbsp;일반배송</label>&nbsp;
														<input type="radio" name="returnType" value="E" class="rtn_radio" id="returnType_E"><label for="returnType_E">&nbsp;긴급배송</label>
													</div>
												</div>
												<div class="row" style="margin-top:8px;">
													<table class="table table-bordered" style="margin-left:20px; width:98%;">
														<tbody>
															<tr>
																<th class="center colorBlack"><font class="red">*&nbsp;</font>반송 사유</th>
																<td style="vertical-align:middle; background:white;">
																	<input type="radio" name="returnReason" id="returnReason_A" value="A" checked>&nbsp;<label for="returnReason_A">단순변심</label>
																	&nbsp;<input type="radio" name="returnReason" id="returnReason_B" value="B">&nbsp;<label for="returnReason_B">파손</label>
																	&nbsp;<input type="radio" name="returnReason" id="returnReason_C" value="C">&nbsp;<label for="returnReason_C">불량</label>
																	&nbsp;<input type="radio" name="returnReason" id="returnReason_D" value="D">&nbsp;<label for="returnReason_D">기타</label>
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="returnReasonDetail_ft">*&nbsp;</font>
																	반송 사유 상세
																</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="" name="returnReasonDetail" id="returnReasonDetail" value=""/>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<br/>
												<div class="row">
													<table class="table table-bordered" style="margin-left:20px; width:98%;">
														<thead>
															<tr>
																<th class="center colorBlack" colspan="3">위약 반송 정보 <small style="padding: 6px;">* 관부가세 환급시 반드시 작성해 주세요</small></th>
															</tr>
														</thead>
														<tbody>
															<tr style="height:41px;">
																<th class="center colorBlack" style="width:10%;"><font class="red">*&nbsp;</font>위약 반송</th>
																<td style="vertical-align:middle; background:white;" colspan="2">
																	<input type="radio" name="taxType" id="taxType_Y" value="Y"><label for="taxType_Y">&nbsp;위약 반송</label>
																	&nbsp;<input type="radio" name="taxType" id="taxType_N" value="N" checked><label for="taxType_N">&nbsp;일반 반송</label>
																	<input type="hidden" name="taxReturn" id="taxReturn" value="C">
																</td>
															</tr>
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnReason_ft">*&nbsp;</font>반송 사유서</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason"></td>
																<td style="vertical-align: middle; background:white;">
																	<a href="/반송 사유서.xlsx" download>[반송사유서 다운로드]</a>
																</td>
															</tr>
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCapture_ft">*&nbsp;</font>반품 접수 캡쳐본</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture"></td>
																<td style="background:white;"></td>
															</tr>
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnMessenger_ft">*&nbsp;</font>반품 메신저 캡쳐본</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger"></td>
																<td style="background:white;"></td>
															</tr>
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCl_ft">*&nbsp;</font>반품 Commercial Invoice</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl" ></td>
																<td style="vertical-align: middle; background:white;">
																	<a href="/반품 커머셜 인보이스.xlsx" download>[반품 커머셜 다운로드]</a>
																</td>
															</tr>
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCopyBank_ft">*&nbsp;</font>환급 통장 사본</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank"></td>
																<td style="background:white;"></td>
															</tr>
															<!--
															<tr class="other_file hide">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnUnipass_ft">*&nbsp;</font>수출신고필증</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnUnipass" name="rtnUnipass"></td>
																<td style="background:white;"></td>
															</tr>
															-->
														</tbody>
													</table>
												</div>
												<div class="row">
													<table class="table table-bordered" style="margin-left:20px; width:98%; margin-top:20px;">
														<tbody>
															<tr>
																<th class="center colorBlack" style="width:10%;">입고시 요청사항</th>
																<td style="padding:0px;">
																	<input style="width:100%; height:36px;" type="text" name="whMsg" id="whMsg" class="comm_text">
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<br/>
												<div class="row">
													<div class="col-xs-12">
														<c:if test="${!empty nno}">
															<c:forEach items="${itemList}" var="item" varStatus="voStatus">
																<div id="addForm" class="panel" style="border:none;">
																	<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; margin-left:20px; width:98%;">
																		<div class="col-xs-12 col-lg-12" style="font-size: 3;">
																			<div class="row">
																				<div class="col-xs-3">
																					<h4>상품 ${item.subNo}</h4>
																				</div>
																				<div class="col-xs-9" style="text-align:right; vertical-align:middle;">
																					<button type="button" class="delItem" style="border:none; background:#fff;" onclick="javascript:fn_delete(this);">
																					<i class="fa fa-times" aria-hidden="true" style="font-size:20px;"></i>
																					</button>
																				</div>
																			</div>
																			<div class="hr dotted"></div>
																			<div class="row hr-8">	
																				<div class="col-xs-1 center">SUB NO</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12 subNo" type="text" name="subNo" id="subNo" value="${item.subNo}" readonly/>
																				</div>
																			</div>
																			<div class="hr dotted"></div>
																			<input type="hidden" name="itemDetailEng" id="itemDetailEng" value=""/>
																			<div class="row hr-8">	
																				<div class="col-xs-1 center">
																					<font class="red">*&nbsp;</font>상품명</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12 require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 기입" />
																				</div>
																				<c:if test="${rtn.orgStation eq 'JP'}">						
																					<div class="col-xs-1 center mp07">
																						<font class="red">*&nbsp;</font>
																						현지 상품명
																					</div>
																					<div class="col-xs-3 center">
																						<input class="col-xs-12 require" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" />
																					</div>
																				</c:if>
																				<c:if test="${rtn.orgStation ne 'JP'}">						
																					<div class="col-xs-1 center mp07">
																						현지 상품명
																					</div>
																					<div class="col-xs-3 center">
																						<input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" />
																					</div>
																				</c:if>
																				<div class="col-xs-1 center mp07">브랜드</div>
																				<div class="col-xs-3 center">
																					<input class="col-xs-12" type="text" name="brand" id="brand" value="${item.brand}"/>
																				</div>
																			</div>
																			<div class="hr dotted"></div>
																			<div class="row hr-8">
																				<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 무게</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12 require" type="number" name="itemWta" id="itemWta" value="${item.itemWta}" />
																				</div>
																				<div class="col-xs-1 center">무게 단위</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
																				</div>
																				<div class="col-xs-1 center">상품 수량</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12" type="number" name="" id="" value="${item.itemCnt}" />
																				</div>
																			</div>
																			<div class="hr dotted"></div>
																			<div class="row form-group hr-8">
																				<div class="col-xs-1 center"><font class="red">*&nbsp;</font>반품 수량</div>
																				<div class="col-xs-3 center ">
																					<input class="col-xs-12 require" type="number" name="itemCnt" id="itemCnt" value="" />
																				</div>
																				<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>상품 가격</div>
																				<div class="col-xs-3 center">
																					<input class="col-xs-12 require" type="number" name="unitValue" id="unitValue" value="${item.unitValue}" />
																				</div>
																				<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>통화</div>
																				<div class="col-xs-3 center ">
																					<select class="chosen-select-start form-control tag-input-style currencies" id="unitCurrency" name="unitCurrency">
																						<c:forEach items="${currencyList}" var="currencyList">
																							<option <c:if test="${currencyList.nationCode eq rtn.dstnNation}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
																						</c:forEach>
																					</select>
																					<%-- <input class="col-xs-12" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" /> --%>
																				</div>
																			</div>
																			<div class="hr dotted"></div>
																			<div class="row hr-8">
																				<div class="col-xs-1 center mp07">
																					<label><font class="red">*&nbsp;</font>제조국</label>
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
																				<c:if test="${rtn.orgStation eq 'JP'}">
																					<div class="col-xs-1 center mp07">
																						<font class="red">*&nbsp;</font>
																						<label>
																							<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE</a>
																						</label>
																					</div>
																					<div class="col-xs-3 center">
																						<input class="col-xs-12 require" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" />
																					</div>
																				</c:if>
																				<c:if test="${rtn.orgStation ne 'JP'}">
																					<div class="col-xs-1 center mp07">
																						<label>
																							<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE</a>
																						</label>
																					</div>
																					<div class="col-xs-3 center">
																						<input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" />
																					</div>
																				</c:if>
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
																					<label><font class="red">*&nbsp;</font>상품 이미지 URL</label>
																				</div>
																				<div class="col-xs-3">
																					<input class="col-xs-12 require" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
																				</div>
																				<c:if test="${rtn.orgStation eq 'JP'}">
																					<div class="col-xs-1 center mp07">
																						<font class="red">*&nbsp;</font>
																						<label>상품코드</label>
																					</div>
																					<div class="col-xs-3">
																						<input type="text" class="col-xs-12 require" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}"/>
																					</div>
																				</c:if>
																				<c:if test="${rtn.orgStation ne 'JP'}">
																					<div class="col-xs-1 center mp07">
																						<label>상품코드</label>
																					</div>
																					<div class="col-xs-3">
																						<input type="text" class="col-xs-12" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}"/>
																					</div>
																				</c:if>
																			</div>
																			<div class="hr dotted"></div>
																		</div>
																	</div>
																</div>
															</c:forEach>
														</c:if>
													</div>
												</div>
												<br/>
												<div class="row" style="text-align:center; margin-top: 20px;">
													<button class="registReturn" id="registReturn_aci"><span class="text">반품 접수</span></button>
												</div>
												<br/>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- /.main-content -->
			</div>
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>
		
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
		<!-- script on paging start -->
		
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
		<script src="/assets/js/jquery.dataTables.min.js"></script>
		<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
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
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
	
				$(document).ready(function() {

					for(var i = 0; i < $(".require").size(); i++){
						if($($(".require")[i]).val().length != 0){
							$($(".require")[i]).removeClass('require');
							i = i-1;
						}
					}

					$(".require").focusout(function() {
						if ($(this).val().length != 0) {
							$(this).removeClass('require');
						} else {
							$(this).addClass('require');
						}
					})
					
				});

				$('input[name="pickType"]').on('click', function() {
					if($(this).val() == 'A') {
						$("#reTrkCom").prop('disabled', false);
						$("#reTrkNo").prop('disabled', false);
						$("#A002Date").prop('disabled', false);
						$("#reTrkCom").addClass('require');
						$("#reTrkNo").addClass('require');
						$("#A002Date").addClass('require');
						$("#reTrkCom_ft").removeClass('hide');
						$("#reTrkNo_ft").removeClass('hide');
						$("#A002Date_ft").removeClass('hide');
					} else {
						$("#reTrkCom").prop('disabled', true);
						$("#reTrkNo").prop('disabled', true);
						$("#A002Date").prop('disabled', true);
						$("#reTrkCom_ft").addClass('hide');
						$("#reTrkNo_ft").addClass('hide');
						$("#A002Date_ft").addClass('hide');
						$("#reTrkCom").removeClass('require');
						$("#reTrkNo").removeClass('require');
						$("#A002Date").removeClass('require');
					}
				})
	
				$('input[name="taxType"]').on('click', function() {
					if($(this).val() == 'Y') {
						$("#rtnReason_ft").removeClass('hide');
						$("#rtnCapture_ft").removeClass('hide');
						$("#rtnMessenger_ft").removeClass('hide');
						$("#rtnCl_ft").removeClass('hide');
						$("#rtnCopyBank_ft").removeClass('hide');
					} else {
						$("#rtnReason_ft").addClass('hide');
						$("#rtnCapture_ft").addClass('hide');
						$("#rtnMessenger_ft").addClass('hide');
						$("#rtnCl_ft").addClass('hide');
						$("#rtnCopyBank_ft").addClass('hide');
					}
				})
	
	
				$("input[name='returnReason']").on('click', function() {
					if ($(this).val() == 'D') {
						$("#returnReasonDetail_ft").removeClass('hide');
						$("#returnReasonDetail").addClass('require');
					} else {
						$("#returnReasonDetail_ft").addClass('hide');
						$("#returnReasonDetail").removeClass('require');
					}
				})

				
			})
			
			
			
			$("#registReturn_aci").on('click', function() {
				let checked = $("#agreement_check").is(":checked");

				if(checked) {
					if ($(".require").size() != 0) {
						alert('필수 정보를 입력해 주세요.');
						$($(".require")[0]).focus();
						return false;
					} else if ($("input[name='taxType']:checked").val() == 'Y') {
						for (var i = 0 ; i < $(".taxTypeValue").size(); i++) {
							if ($($(".taxTypeValue")[i]).val().length == 0) {
								alert("위약반송 필수 서류가 비어있습니다.");
								return false;
							}
						}
					} else {
						$("#returnForm").attr("action","/return/returnCourier");
						$("#returnForm").attr("method", "POST");
						$("#returnForm").submit();

					}
										
				} else{
					alert("반품 사용 안내서에 동의해주세요.");
					return false;
				}
			});
	
			function searchKobl() {
				let checked = $("#agreement_check").is(":checked");

				if (!checked) {
					alert("반품 사용 안내서에 동의해주세요.");
				} else {
					location.href='/return/returnCourier?koblNo='+$("#koblNo").val();
				}
			};
	
			if($("#nno").val().length != 0){
				$("#agreement_check").prop("checked", true);
				$(".wms_return").toggleClass('hide');
			}
	
			function fn_delete(obj) {
				if ($(".panel").length > 1) {
					obj.closest('.panel').remove();
				} else {
					alert("최소 1개의 상품정보를 입력해야 합니다.");
				}
			}
	
	
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
		
	
		</script>
	</body>
</html>
