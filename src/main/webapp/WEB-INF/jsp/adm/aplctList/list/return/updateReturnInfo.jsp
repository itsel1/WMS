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
	 .colorBlack {color:#000000 !important;}
	
	.backYellow {background-color: #FDF5E6 !important;}
	
	input[type="date"]::-webkit-calendar-picker-indicator,
	input[type="date"]::-webkit-inner-spin-button {
		display : none;
		appearance : none;
	}
	
	input[type="date"]::-webkit-calendar-picker-indicator {
		color : rgba(0,0,0,0);
		opacity : 1;
		display : block;
	}
	
	select {
		height:36px;

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
						<div class="page-header">
							<h1></h1>
						</div>
							<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="post" action="/return/updateExpress">
							<input type="hidden" name="_method" value="patch"/>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="sellerId" id="sellerId" value="${rtn.sellerId}"/>
							<div class="row">
							<div class="col-xs-12 col-lg-12 ">
								<label>
									<font style="font-size: 18px; font-weight: bold; vertical-align: middle;">운송장 번호</font>
									<input style="margin-left:5px; width:300px; height:36px;" type="text" name="koblNo" value="${rtn.koblNo}" id="koblNo" class="backYellow">
									<input type="hidden" id="nno" name="nno" value="${nno}"/>
								</label>
							</div>
							</div>
							<br/>
							<div class="row">
								<div class="col-xs-12 col-lg-12">
									<label>
										<font style="font-size: 18px; font-weight: bold; vertical-align: middle;" id="textbox">출발지 / 도착지 : KR / ${rtn.dstnNation}</font>
										<input type="hidden" name="dstnNation" id="dstnNation" value="${rtn.dstnNation}"/>
									</label>
								</div>
							</div>
							<br/>
							<div class="row">
								<div class="col-xs-12 col-lg-12">
										<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-right:10px;">픽업 방법</font>
										<label><input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'B'}">checked</c:if> value="B" class="rtn_radio">회수 요청</label>
										<label><input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'A'}">checked</c:if> value="A" class="rtn_radio">직접 발송</label>
								</div>
							</div>
							<br/>
							<div class="row">
								<c:if test="${rtn.pickType eq 'B'}">
									<table class="table table-bordered hide" id="reTrk" name="reTrk" style="margin-left:20px; width:90%;">
										<thead>
											<tr>
												<th class="center colorBlack" colspan="2">반송 택배 정보</th>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">택배 회사</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="reTrkCom" id="reTrkCom" value="${rtn.reTrkCom}"/>
												</td>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">송장 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="reTrkNo" id="reTrkNo" value="${rtn.reTrkNo}"/>
												</td>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">등록 날짜</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="A002Date" id="A002Date" value="${rtn.a002Date}"/>
												</td>
											</tr>
										</thead>
									</table>
								</c:if>
								<c:if test="${rtn.pickType eq 'A'}">
									<table class="table table-bordered" id="reTrk" name="reTrk" style="margin-left:20px; width:90%;">
										<thead>
											<tr>
												<th class="center colorBlack" colspan="2">반송 택배 정보</th>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">택배 회사</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="reTrkCom" id="reTrkCom" value="${rtn.reTrkCom}"/>
												</td>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">송장 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="reTrkNo" id="reTrkNo" value="${rtn.reTrkNo}"/>
												</td>
											</tr>
											<tr>
												<th class="center colorBlack" style="width:10%;">등록 날짜</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;height:36px;" class="" name="A002Date" id="A002Date" value="${rtn.a002Date}"/>
												</td>
											</tr>
										</thead>
									</table>
								</c:if>
							</div>
							<br/>
							<div class="row">
								<table class="table table-bordered" style="margin-left:20px; width:90%;">
									<thead>
										<tr>
											<th class="center colorBlack" colspan="2">반품 담당자 정보</th>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">이름</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="attnName" id="attnName" value="${rtn.attnName}"/>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">연락처</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="attnTel" id="attnTel" value="${rtn.attnTel}"/>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">이메일</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="attnEmail" id="attnEmail" value="${rtn.attnEmail}"/>
											</td>
										</tr>
									</thead>
								</table>
							</div>
							<br/>
							<div class="row">
								<table class="table table-bordered" style="margin-left:20px; width:90%;">
									<thead>
										<tr>
											<th class="center colorBlack" colspan="4">반품 발송 정보</th>
										</tr>
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
											<th class="center colorBlack" style="width:10%;">주문번호</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="orderNo" id="orderNo" value="${rtn.orderNo}"/>
											</td>
											<th class="center colorBlack" style="width:10%;">주문날짜</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="orderDate" id="orderDate" value="${rtn.orderDate}"/>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">이름</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupName" id="pickupName" value="${rtn.pickupName}"/>
											</td>
											<th class="center colorBlack" style="width:10%;">우편번호</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupZip" id="pickupZip" value="${rtn.pickupZip}"/>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">주소</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr}"/>
											</td>
											<th class="center colorBlack" style="width:10%;">영문주소</th>
											<td style="padding:0px;" title="영문주소는 수정이 불가능합니다.">
												<input type="text" style="width:100%;height:36px;" class="" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr}" readonly/>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">전화번호</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="pickupTel" id="pickupTel" value="${rtn.pickupTel}"/>
											</td>
											<th class="center colorBlack" style="width:10%;">휴대전화번호</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile}"/>
											</td>
										</tr>
									</thead>
								</table>
							</div>
							<br/>
							<c:choose>
								<c:when test="${rtn.state == 'A000'}">
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:90%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">해외 도착지 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">국가</th>
													<td style="padding:0px; width:610px; height:36px;">
														<select class="chosen-select-start form-control tag-input-stylepd-1rem  nations">
															<c:forEach items="${nationList}" var="dstnNationList">
																<option <c:if test="${rtn.dstnNation eq dstnNationList.nationCode}"> selected </c:if>value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
															</c:forEach>
														</select>
													</td>
													<th class="center colorBlack" style="width:10%;">주</th>
													<td style="padding:0px; width:650px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderState" id="senderState" value="${rtn.senderState}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">도시</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderZip" id="senderZip" value="${rtn.senderZip}" />
													</td>
												</tr>
												<tr>									
													<th class="center colorBlack" style="width:10%;">주소</th>
													<c:choose>
														<c:when test="${!empty rtn.senderAddr && empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddr}"/>
															</td>
														</c:when>
														<c:when test="${empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:when test="${!empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddr} ${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:otherwise>
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value=""/>
															</td>
														</c:otherwise>
													</c:choose>
													<th class="center colorBlack" style="width:10%;">회사명</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderName" id="senderName" value="${rtn.senderName}" />
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="senderTel" id="senderTel" value="${rtn.senderTel}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">빌딩 명</th>
													<td style="padding:0px;" colspan="3">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}"/>
													</td>
											</thead>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:90%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">해외 도착지 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">회사명</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderName" id="senderName" value="${rtn.senderName}" />
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderZip" id="senderZip" value="${rtn.senderZip}" />
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderState" id="senderState" value="${rtn.senderState}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">도시</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주소</th>
													<c:choose>
														<c:when test="${!empty rtn.senderAddr && empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddr}"/>
															</td>
														</c:when>
														<c:when test="${empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:when test="${!empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddr} ${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:otherwise>
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" class="" name="senderAddr" id="senderAddr" value=""/>
															</td>
														</c:otherwise>
													</c:choose>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="senderTel" id="senderTel" value="${rtn.senderTel}"/>
													</td>
												</tr>
												<c:if test="${rtn.dstnNation eq 'US'}">
													<tr>
													<th class="center colorBlack" style="width:10%;">빌딩 명</th>
													<td style="padding:0px;" colspan="3">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}" placeholder="빌딩 명을 반드시 입력하여 주시기 바랍니다."/>
													</td>
												</tr>
												</c:if>
											</thead>
										</table>
									</div>
								</c:otherwise>
							</c:choose>
							
							<br/>
							<div class="row">
								<div class="col-xs-12 col-lg-12">
									<label>
										<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-left:10px; margin-right:10px;">반송 구분</font>
										<input style="line-height:1.5;" type="radio" name="returnType" id="returnType1" <c:if test="${rtn.returnType eq 'N'}"> checked </c:if> value="N" class="rtn_radio">&nbsp;일반배송
										&nbsp;<input style="line-height:1.5;" type="radio" name="returnType" id="returnType2" <c:if test="${rtn.returnType eq 'E'}"> checked </c:if> value="E" class="rtn_radio">&nbsp;긴급배송
									</label>
								</div>
							</div>
							<div class="row">
								<table class="table table-bordered" style="margin-left:20px; width:90%;">
									<thead>
										<tr>
											<th class="center colorBlack">취소 사유</th>
											<td style="vertical-align:middle; background:white;">
												<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'A'}"> checked </c:if> value="A"><label for="cncl">단순변심</label> 
												<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'B'}"> checked </c:if> value="B"><label for="cncl">파손</label> 
												<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'C'}"> checked </c:if> value="C"><label for="cncl">불량</label>
												<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'D'}"> checked </c:if> value="D"><label for="cncl">기타</label>
											</td>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">취소 사유 상세</th>
											<td style="padding:0px;">
												<input type="text" style="width:100%;height:36px;" name="returnReasonDetail" id="returnReasonDetail" value="${rtn.returnReasonDetail}"/>
											</td>
										</tr>
									</thead>
								</table>
							</div>
							<br/>
							<div class="row">
								<table class="table table-bordered" style="margin-left:20px; width:90%;">
									<thead>
										<tr>
											<th class="center colorBlack" colspan="3">위약 반송 정보 <small style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해 주세요</small></th>
										</tr>
										<tr>
											<th class="center colorBlack" style="width:10%;">위약 반송</th>
											<td style="vertical-align:middle; background:white;" colspan="2">
												<input type="radio" name="taxType" id="taxType" <c:if test="${rtn.taxType eq 'Y' }">checked</c:if> value="Y"><label for="cncl">위약 반송</label>
												<input type="radio" name="taxType" id="taxType" <c:if test="${rtn.taxType eq 'N' }">checked</c:if> value="N"><label for="cncl">일반 반송</label>
												<input type="hidden" name="taxReturn" id="taxReturn" value="C">
											</td>
										</tr>
										<tr class="hideTax">
											<th class="center colorBlack" style="width:12%;">반송 사유서</th>
											<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason"></td>
											<td style="vertical-align: middle; background:white;">
												<c:if test="${rtn.taxType eq 'Y'}">
													<c:if test="${empty rtn.fileReason}">
														File Not Found
													</c:if>
													<c:if test="${!empty rtn.fileReason}">
														<a href="${rtn.fileReason}" target='_blank' download>[반송사유서 다운로드]</a>
													</c:if>
												</c:if>
											</td>
										</tr>
										<tr class="hideTax">
											<th class="center colorBlack" style="width:12%;">반품 접수 캡쳐본</th>
											<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture"></td>
											<td style="vertical-align: middle; background:white;">
												<c:if test="${rtn.taxType eq 'Y'}">
													<c:if test="${empty rtn.fileCapture}">
														File Not Found
													</c:if>
													<c:if test="${!empty rtn.fileReason}">
														<a href='${rtn.fileCapture}' target='_blank' download>[반품접수 캡쳐본 다운로드]</a>
													</c:if>
												</c:if>
											</td>
										</tr>
										<tr class="hideTax">
											<th class="center colorBlack" style="width:12%;">반품 메신저 캡쳐본</th>
											<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger"></td>
											<td style="vertical-align: middle; background:white;">
												<c:if test="${rtn.taxType eq 'Y'}">
													<c:if test="${empty rtn.fileMessenger}">
														File Not Found
													</c:if>
													<c:if test="${!empty rtn.fileMessenger}">
														<a href='${rtn.fileMessenger}' target='_blank' download>[반품 매신져 캡쳐본 다운로드]</a>
													</c:if>
												</c:if>
											</td>
										</tr>
										<tr class="hideTax">
											<th class="center colorBlack" style="width:12%;">반품 Commercial Invoice</th>
											<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl"></td>
											<td style="vertical-align: middle; background:white;">
												<c:if test="${rtn.taxType eq 'Y'}">
													<c:if test="${empty rtn.fileCl}">
														File Not Found
													</c:if>
													<c:if test="${!empty rtn.fileCl}">
														<a href='${rtn.fileCl}' target='_blank' download>[반품 commercial invoice 다운로드]</a>
													</c:if>
												</c:if>
											</td>
										</tr>
										<tr class="hideTax">
											<th class="center colorBlack" style="width:12%;">환급 통장 사본</th>
											<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank"></td>
											<td style="vertical-align: middle; background:white;">
												<c:if test="${rtn.taxType eq 'Y'}">
													<c:if test="${empty rtn.fileCopyBank}">
														File Not Found
													</c:if>
													<c:if test="${!empty rtn.fileCopyBank}">
														<a href='${rtn.fileCopyBank}' target='_blank' download>[환급 통장 사본 다운로드]</a>
													</c:if>
												</c:if>
											</td>
										</tr>
									</thead>
								</table>
							</div>
							<br/>
							<div class="row" style="width:90%; margin-left:10px;">
								<c:forEach items="${itemList}" var="item" varStatus="voStatus">
									<div id="addForm">
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div class="col-xs-12 col-lg-12" style="font-size: 3;">
												<h4>상품 ${voStatus.index+1}</h4>
												<div class="hr dotted"></div>
												<div class="row hr-8">
													<div class="col-xs-1 center mp07">
														<label>SUB NO</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 backYellow" type="text" name="subNo" id="subNo" value="${item.subNo}" readonly />
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row hr-8">
													<div class="col-xs-1 center mp07">
														<label>상품명</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 backYellow" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" />
													</div>
													<div class="col-xs-1 center mp07">
														<label>상품명 (영문)</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 itemDetailEng" type="text" name="itemDetailEng" id="itemDetailEng" value="${item.itemDetailEng}" placeholder="도착지가 일본일 경우 필수입니다."/>
													</div>
													<div class="col-xs-1 center mp07">
														<label>상품명 (일문)</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 nativeItemDetail"  type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row hr-8">
													<div class="col-xs-1 center mp07">브랜드</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="text" name="brand" id="brand" value="${item.brand}"/>
													</div>
													<div class="col-xs-1 center">상품 무게</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="number" name="itemWta" id="itemWta" value="${item.itemWta}" />
													</div>
													<div class="col-xs-1 center">무게 단위</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8">
													<div class="col-xs-1 center">상품 개수</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="number" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
													</div>
													<div class="col-xs-1 center mp07">상품 가격</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="number" name="unitValue" id="unitValue" value="${item.unitValue}" />
													</div>
													<div class="col-xs-1 center mp07">통화</div>
													<div class="col-xs-3 center ">
														<input class="col-xs-12 backYellow" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" />
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row hr-8">
													<div class="col-xs-1 center mp07">
														<label>제조국</label>
													</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
													</div>
													<div class="col-xs-1 center mp07">
														<label>제조회사</label>
													</div>
													<div class="col-xs-3 center">
														<input class="col-xs-12 backYellow" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" />
													</div>
													<div class="col-xs-1 center mp07">
														<label><a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
														<i class="fa fa-exclamation-circle"></i>
														</a></label>
													</div>
													<div class="col-xs-3 center ">
														<input class="col-xs-12 hsCode" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row hr-8">
													<div class="col-xs-1 center mp07">
														<label>상품 URL</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 backYellow" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
													</div>
													<div class="col-xs-1 center mp07">
														<label>상품 이미지 URL</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 backYellow" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
													</div>
													<div class="col-xs-1 center mp07">
														<label>상품코드</label>
													</div>
													<div class="col-xs-3">
														<input class="col-xs-12 cusItemCode" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
													</div>
												</div>
												<div class="hr dotted"></div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<br/><br/>
							<div class="row">
								<table class="table table-bordered" style="width:90%; margin-left:20px;">
									<thead> 
										<tr>
											<th class="center colorBlack" style="width:10%;">입고 요청사항</th>
											<td style="padding:0px;">
												<input style="width:100%; height:36px;" type="text" name="whMsg" id="whMsg" value="${rtn.whMsg}" class="comm_text">
											</td>
										</tr>
									</thead>
								</table>
							</div>
							<br/>
							<div class="rtnBtn_div" style="text-align:center;">
								<input type="button" class="btn btn-outline-secondary btn-lg" style="width:10%;height: 50px;font-size: medium;" id="backBtn" name="backBtn" value="뒤로가기">
								<input type="button" class="btn btn-outline-secondary btn-lg" style="width:10%;height: 50px;font-size: medium;" id="updateReturn" name="updateReturn" value="정보 수정">
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
		$(".backYellow").focusout(function(){
			if($(this).val().length != 0){
				$(this).removeClass("backYellow");
			}else{
				$(this).addClass("backYellow");
			}
		});
		$("#backBtn").on("click",function(e){
			window.location.href = "/mngr/order/return/list";
		});

		$('input[name="pickType"]').on('click',function(e){
			if($(this).val()=='A'){
				$("#reTrk").removeClass("hide");
				$("#reTrkCom").addClass("backYellow");
				$("#reTrkNo").addClass("backYellow");
				$("#A002Date").addClass("backYellow");
			}else{
				$("#reTrk").addClass("hide");
				$("#reTrkCom").removeClass("backYellow");
				$("#reTrkNo").removeClass("backYellow");
				$("#A002Date").removeClass("backYellow");
			}
		});
		
		
		$("#updateReturn").on("click",function(e){
			
			if(!$('input[name="pickType"]').is(":checked")){
				alert("픽업 방법을 선택해 주세요")
				
			} else if($(".backYellow").size() != 0){
				alert("필수 등록정보를 입력해 주세요.")
				$($(".backYellow")[0]).focus();
				return;
			} else {
				$("#returnForm").attr("action","/mngr/aplctList/return/"+$("#orderReference").val());
				$("#returnForm").submit();
			}	
		});


		for(var i = 0; i < $(".backYellow").size(); i++){
			if($($(".backYellow")[i]).val().length != 0){
				$($(".backYellow")[i]).removeClass('backYellow');
				i = i-1;
			}
		}

		$("#reTrkCom").on("change",function(){
			if($(this).val().length != 0){
				$(this).removeClass("backYellow");
			}else{
				$(this).addClass("backYellow");
			}
		});

		$("#reTrkNo").on("change",function(){
			if($(this).val().length != 0){
				$(this).removeClass("backYellow");
			}else{
				$(this).addClass("backYellow");
			}
		});

		$("#A002Date").on("change",function(){
			if($(this).val().length != 0){
				$(this).removeClass("backYellow");
			}else{
				$(this).addClass("backYellow");
			}
		});
		
		jQuery(function($) {
			$(document).ready(function() {
				/* if($("#taxType").val()=='Y'){
					$(".hideTax").removeClass('hide');
				} */
				if($("input[name='taxType']:checked").val() == "N") {
					$(".hideTax").addClass('hide');
				}

				if($("input[name='taxType']:checked").val() == "Y") {
					$(".hideTax").removeClass('hide');
				}

				if($('input[name="pickType"]').val() == "A"){
					$("#reTrk").removeClass("hide");
				}
				
				$('input[name="taxType"]').on('click',function(){
					if($(this).val() == 'Y'){
						$(".hideTax").removeClass('hide');
					}else if($(this).val() == 'N'){
						$(".hideTax").addClass('hide');
					}
				});

				for(var i = 0; i < $(".backYellow").size(); i++){
					if($($(".backYellow")[i]).val().length != 0){
						$($(".backYellow")[i]).removeClass('backYellow');
						i = i-1;
					}
				}

				
			});


		});

		

		$("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });


		if(!ace.vars['touch']) {
			$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true}); 
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
					 $this.next().css({ 'width': $this.parent().width() });
				})
			});
		}

		$('.chosen-select-start').change(function() {
			var value = $(".chosen-select-start").val();
			$("#textbox").text("출발지 / 도착지 : KR / "+value);
			$("#dstnNation").val(value);

			if (value == 'US') {
				$("#senderBuildNm").addClass('backYellow');
			} else {
				$("#senderBuildNm").removeClass('backYellow');
			}
			
		})
		if ($("#dstnNation").val() != 'US') {
				$("#senderBuildNm").removeClass('backYellow');
			} else {
				$("#senderBuildNm").addClass('backYellow');
			}
		</script>
		<!-- script addon end -->
	</body>
</html>
