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
											<input type="hidden" name="addCntVal" id="addCntVal" value=""/>
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
											<div class="row" id="other_input" >
												<div class="col-xs-12 col-lg-12" style="margin-left:10px;">
													<label>
														<font style="font-size: 18px; font-weight: bold; vertical-align: middle;">운송장 번호</font>
														<input style="margin-left:5px; width:300px; height:36px;" class="backYellow" type="text" name="koblNo" value="${koblNo}" id="koblNo_input" placeholder="운송장번호를 입력해주세요" autocomplete="off">
													</label>
												</div>
											</div>
											<div class="row" style="margin-top:10px;">
												<div class="col-xs-12 col-lg-12">
													<table class="station_table" style="">
														<thead>
															<tr>
																<th style="font-size:18px; width:80px; text-align:center;">출발지 :</th>
																<td style="border:none; padding:0px; width:250px;">
																	 <!-- <input type="text" style="width:100%;" name="orgStation" value="KR"/> -->
																	<select class="chosen-select-start form-control tag-input-style width-30 pd-1rem nations" id="orgStation" name="orgStation">
																		<c:forEach items="${nationList}" var="dstnNationList">
																			<option <c:if test="${dstnNationList.nationCode eq 'KR'}"> selected</c:if> value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
																		</c:forEach>
																	</select>
																</td>
																<td style="width:10px; border:none;"></td>
																<th style="font-size:18px; width:80px; text-align:center;">도착지 :</th>
																<td style="border:none; padding:0px; width:250px;">
																	<!-- <input type="text" style="width:100%;" name="dstnNation" value=""/> -->
																	<select class="chosen-select-start form-control tag-input-style width-30 pd-1rem nations" id="dstnNation" name="dstnNation">
																		<c:forEach items="${nationList}" var="dstnNationList">
																			<option value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
																		</c:forEach>
																	</select>
																</td>
															</tr>
														</thead>
													</table>
												</div>
												<br/>
											</div>
											<br/><br/>
											<div>
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
																	<input type="text" style="width:100%;height:36px;" name="attnName" id="attnName" value="${data.attnName}"/>
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
												<div style="text-align:right; margin-right:4px;">
													<input type="button" class="invInfo_btn" onclick="fn_setInvoiceInfo()" value="청구지 정보">
													<input type="button" class="invInfo_btn" onclick="fn_setCurrentInfo()" value="최근 정보">
												</div>
												<br/><br/>
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
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="pickupAddr_ft">*&nbsp;</font>주소
																</th>
																<td style="padding: 0px;">
																	<input type="text" style="width:100%;height:36px;" class="" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr}"/>
																</td>
																<th class="center colorBlack" style="width:10%;">
																	<font class="red" id="pickupEngAddr_ft">*&nbsp;</font>영문주소
																</th>
																<td style="padding: 0px;">
																	<input type="text" style="width:100%;height:36px;" class="require" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr}"/>
																</td>
															</tr>
															<tr>
																<th class="center colorBlack" style="width:10%;"><font class="red" id="pickupTel_ft">*&nbsp;</font>전화번호</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" numberOnly class="require" name="pickupTel" id="pickupTel" value="${rtn.pickupTel}" placeholder="숫자만 입력 가능합니다."/>
																</td>
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="pickupMobile_ft">*&nbsp;</font>휴대전화번호
																</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" numberOnly class="" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile}" placeholder="숫자만 입력 가능합니다."/>
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
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="senderState_ft">*&nbsp;</font>주
																</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="" name="senderState" id="senderState" value="${rtn.senderState}"/>
																</td>
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="senderCity_ft">*&nbsp;</font>도시
																</th>
																<td style="padding:0px;">
																	<input type="text" style="width:100%;height:36px;" class="" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
																</td>
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
																<th class="center colorBlack" style="width:10%;">
																	<font class="red hide" id="senderBuildNm_ft">*&nbsp;</font>빌딩 명
																</th>
																<td style="padding:0px;" colspan="3">
																	<input type="text" style="width:100%;height:36px;" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}"/>
																</td>
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
															<tr class="hideTax">
																<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnIc_ft">*&nbsp;</font>수출신고필증</th>
																<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnIc" name="rtnIc"></td>
																<td style="background:white;"></td>
															</tr>
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
														<div class="col-xs-12 col-lg-12" style="padding: 1rem!important; width:98%; text-align: right">
															<input type="button" class="btn btn-sm btn-white" id="addItem" name="addItem" value="추가">
															<input type="button" class="btn btn-sm btn-white" id="delItem" name="delItem" value="삭제">
														</div>
														<div id="addForm">
															<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
																<div class="col-xs-12 col-lg-12" style="font-size: 3;">
																	<h4>상품 1</h4>
																	<div class="hr dotted"></div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07">
																			<font class="red">*&nbsp;</font><label>상품명</label>
																		</div>
																		<div class="col-xs-3">
																			<input class="col-xs-12 require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 입력해 주세요." />
																		</div>
																		<div class="col-xs-1 center mp07">
																			<font class="red hide" id="nativeItemDetail_ft">*&nbsp;</font><label>상품명 (일문)</label>
																		</div>
																		<div class="col-xs-3">
																			<input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
																		</div>
																		<div class="col-xs-1 center mp07">
																			<label><font class="red hide" id="cusItemCode_ft">*&nbsp;</font>상품코드</label>
																		</div>
																		<div class="col-xs-3">
																			<input class="col-xs-12" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
																		</div>
																	</div>
																	<div class="hr dotted"></div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07">브랜드</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12" type="text" name="brand" id="brand" value="${item.brand}"/>
																		</div>
																		<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 무게</div>
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
																		<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 수량</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 require" numberOnly type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
																		</div>
																		<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>상품 가격</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 require" priceOnly type="text" name="unitValue" id="unitValue" value="${item.unitValue}" />
																		</div>
																		<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>통화</div>
																		<div class="col-xs-3 center ">
																			<select class="chosen-select-start form-control tag-input-style currencies" id="unitCurrency" name="unitCurrency">
																				<c:forEach items="${currencyList}" var="currencyList">
																					<option value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
																				</c:forEach>
																			</select>
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
																		<div class="col-xs-1 center mp07">
																			<label>
																			<font class="red hide" id="hsCode_ft">*&nbsp;</font>
																			<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
																			<i class="fa fa-exclamation-circle"></i>
																			</a></label>
																		</div>
																		<div class="col-xs-3 center ">
																			<input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
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
																			<label><font class="red hide" id="itemImgUrl_ft">*&nbsp;</font>상품 이미지 URL</label>
																		</div>
																		<div class="col-xs-3">
																			<input class="col-xs-12" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
																		</div>
																	</div>
																	<div class="hr dotted"></div>
																</div>
															</div>
														</div>
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

			var addCnt = 1;
			jQuery(function($) {

				var today = new Date();
				var year = today.getFullYear();
				var month = ('0' + (today.getMonth() + 1)).slice(-2);
				var day = ('0' + today.getDate()).slice(-2);

				var date = year + '-' + month + '-' + day;
				var date2 = year+month+day;

				for(var i = 0; i < $(".require").size(); i++) {
					if($($(".require")[i]).val().length != 0){
						$($(".require")[i]).removeClass('require');
						i = i-1;
					}
				}

				$(document).ready(function() {
					$("#orderDate").val(date2);
				})

				$(".require").focusout(function() {
					if ($(this).val().length != 0) {
						$(this).removeClass('require');
					} else {
						$(this).addClass('require');
					}
				});
				
				$("#dstnNation").on('change', function() {
					var dest = $("#dstnNation").val();

					if (dest == 'KR') {
						$("#pickupAddr_ft").removeClass('hide');
						$("#pickupAddr").addClass('require');
						$("#pickupEngAddr_ft").addClass('hide');
						$("#pickupEngAddr").removeClass('require');
					} else {
						$("#pickupAddr_ft").addClass('hide');
						$("#pickupAddr").removeClass('require');
						$("#pickupEngAddr_ft").removeClass('hide');
						$("#pickupEngAddr").addClass('require');
					}

					if (dest == 'US') {
						$("#senderState_ft").removeClass('hide');
						$("#senderState").addClass('require');
						$("#senderCity_ft").removeClass('hide');
						$("#senderCity").addClass('require');
						$("#senderBuildNm_ft").removeClass('hide');
						$("#senderBuildNm").addClass('require');
					} else {
						$("#senderState_ft").addClass('hide');
						$("#senderState").removeClass('require');
						$("#senderCity_ft").addClass('hide');
						$("#senderCity").removeClass('require');
						$("#senderBuildNm_ft").addClass('hide');
						$("#senderBuildNm").removeClass('require');
					}

					if (dest == 'JP') {
						$("#pickupMobile_ft").removeClass('hide');
						$("#pickupMobile").addClass('require');
						$("#nativeItemDetail_ft").removeClass('hide');
						$("#nativeItemDetail").addClass('require');
						$("#cusItemCode_ft").removeClass('hide');
						$("#cusItemCode").addClass('require');
						$("#hsCode_ft").removeClass('hide');
						$("#hsCode").addClass('require');
						$("#itemImgUrl_ft").removeClass('hide');
						$("#itemImgUrl").addClass('require');
						$(".require").focusout(function() {
							if ($(this).val().length != 0) {
								$(this).removeClass('require');
							} else {
								$(this).addClass('require');
							}
						});
					} else {
						$("#pickupMobile_ft").addClass('hide');
						$("#pickupMobile").removeClass('require');
						$("#nativeItemDetail_ft").addClass('hide');
						$("#nativeItemDetail").removeClass('require');
						$("#cusItemCode_ft").addClass('hide');
						$("#cusItemCode").removeClass('require');
						$("#hsCode_ft").addClass('hide');
						$("#hsCode").removeClass('require');
						$("#itemImgUrl_ft").addClass('hide');
						$("#itemImgUrl").removeClass('require');
					}
				})

				$("input[name='pickType']").on('click', function() {
					if($(this).val() == 'A') { /* 직접 전달 */
						$("#reTrkCom").prop('disabled', false);
						$("#reTrkNo").prop('disabled', false);
						$("#A002Date").prop('disabled', false);
						$("#reTrkCom").addClass('require');
						$("#reTrkNo").addClass('require');
						$("#reTrkCom_ft").removeClass('hide');
						$("#reTrkNo_ft").removeClass('hide');
						$("#A002Date").val(date);
						
					} else {
						$("#reTrkCom").prop('disabled', true);
						$("#reTrkNo").prop('disabled', true);
						$("#A002Date").prop('disabled', true);
						$("#reTrkCom_ft").addClass('hide');
						$("#reTrkNo_ft").addClass('hide');
						$("#reTrkCom").removeClass('require');
						$("#reTrkNo").removeClass('require');
						$("#A002Date").val('');
					}
				})

				$('input[name="taxType"]').on('click', function() {
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
						$("#returnForm").attr("action","/return/returnOthers");
						$("#returnForm").attr("method", "POST");
						$("#returnForm").submit();

					}
										
				} else{
					alert("반품 사용 안내서에 동의해주세요.");
					return false;
				}
			});
	

			$("#addItem").on("click",function(e){
				addCnt++
				$.ajax({
					url:'/return/returnItem',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : {
						cnt : addCnt, dstnNation : $("#dstnNation").val()
					},
					success : function(data) {
						$("#addForm").append(data);
						$("#addCntVal").val(addCnt);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});
			
			$("#delItem").on("click",function(e){
				if(addCnt != 1){
					$("#add"+$("#addCntVal").val()).remove();
					addCnt--;
					$("#addCntVal").val(addCnt);
				}
			});

	
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
			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });
	        
			$("input:text[zipCodeOnly]").on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9|-]/g,""));
			});

			$("input:text[priceOnly]").on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9.]/g,""));
			});
			
			function fn_setInvoiceInfo() {
				selectAttnInfo("inv");
			};

			function fn_setCurrentInfo() {
				selectAttnInfo("current");
			};
			

			function selectAttnInfo(type) {
				var data = {type : type};

				$.ajax({
					url : '/return/selectReturnAttnInfo',
					type : 'GET',
					data : data,
					success : function(result) {
						console.log(result);
						if (result.result == "I") {
							$("#attnName").val(result.userInfo.invUserName);
							$("#attnTel").val(result.userInfo.invUserTel);
							$("#attnEmail").val(result.userInfo.invUserEmail);
						} else if (result.result == "S") {
							$("#attnName").val(result.userInfo.ATTN_NAME);
							$("#attnTel").val(result.userInfo.ATTN_TEL);
							$("#attnEmail").val(result.userInfo.ATTN_EMAIL);
						} else {
							alert(result.msg);
						}
					},
					error : function(xhr, status) {
						alert('조회에 실패하였습니다. 관리자에게 문의해 주세요.');
					}
				})
			};
	
		</script>
	</body>
</html>
