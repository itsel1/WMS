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
	

	input[type="text"]:read-only:not([read-only="false"]) {
		
		color : black;
	}
	
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>반품 접수</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="row" style="margin-left:10px;">
							<div class="col-md-12 mb-0" style="font-size:14px !important;">
								<a>반품 페이지</a> 
								<span class="mx-2 mb-0">/</span> 
								<strong class="text-black">반품 정보</strong>
							</div>
						</div>
				    </div>
					<div class="container">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="page-content">
							<div class="page-header">
								<h3 style="font-weight:bold;">
									반품 상세정보
								</h3>
								<br/>
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
											<tr>
												<td>
													<p style="margin-top:10px;">
													<label for="agreement_check"><input type="checkbox" id="agreement_check" name="agreement_check" checked disabled> 
														<font style="font-size: 14px; font-weight: bold;">동의 합니다.</font>
													</label>
												</p>
											</td>
										</tr>
									</table>
									<br/>
									<p style="color: #FF0000; font-weight: bold; line-height: 1;">
										* 해외로 발송되는 반품건만 입력해주세요
									</p>
									<p style="color: #FF0000; font-weight: bold; line-height: 1;">
										* 국내 회수 및 반품은 입력하지 말아 주세요
									</p>
								</div>
							</div>
							<br/><br/>
							<div class="inner-content-side">
								<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="post" action="/return/updateExpress">
									<input type="hidden" name="_method" value="patch"/>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<div class="row">
										<div class="col-xs-12 col-lg-12 ">
											<label>
												<font style="font-size: 18px; font-weight: bold; vertical-align: middle;">운송장 번호</font>
												<input style="margin-left:5px; width:300px; height:36px;" type="text" name="koblNo" value="${koblNo}" id="koblNo" readonly>
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
												<label><input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'B'}">checked</c:if> value="B" class="rtn_radio" disabled> 회수 요청</label>
												<label><input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'A'}">checked</c:if> value="A" class="rtn_radio" disabled> 직접 발송</label>
										</div>
									</div>
									<br/>
									<div class="row">
										<c:if test="${rtn.pickType eq 'B'}">
											<table class="table table-bordered hide" id="reTrk" name="reTrk" style="margin-left:20px; width:98%;">
												<thead>
													<tr>
														<th class="center colorBlack" colspan="2">반송 택배 정보</th>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">택배 회사</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.reTrkCom}"/>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">송장 번호</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.reTrkNo}"/>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">등록 날짜</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.a002Date}"/>
														</td>
													</tr>
												</thead>
											</table>
										</c:if>
										<c:if test="${rtn.pickType eq 'A'}">
											<table class="table table-bordered" id="reTrk" name="reTrk" style="margin-left:20px; width:98%;">
												<thead>
													<tr>
														<th class="center colorBlack" colspan="2">반송 택배 정보</th>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">택배 회사</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.reTrkCom}"/>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">송장 번호</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.reTrkNo}"/>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:10%;">등록 날짜</th>
														<td style="padding:0px;">
															<input type="text" style="width:100%;height:36px;" readonly value="${rtn.a002Date}"/>
														</td>
													</tr>
												</thead>
											</table>
										</c:if>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="2">반품 담당자 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이름</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.attnName}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">연락처</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.attnTel}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이메일</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.attnEmail}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">반품 발송 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">자사 정산 ID</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.calculateId}" />
													</td>
													<th class="center colorBlack" style="width:10%;">주문 접수 번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.orderReference}" />
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주문번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.orderNo}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">주문날짜</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.orderDate}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이름</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupName}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupZip}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주소</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupAddr}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">영문주소</th>
													<td style="padding:0px;" title="영문주소는 수정이 불가능합니다.">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupEngAddr}" readonly/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupTel}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">휴대전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.pickupMobile}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">해외 도착지 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">회사명</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderName}" />
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderZip}" />
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderState}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">도시</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderCity}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주소</th>
													<c:choose>
														<c:when test="${!empty rtn.senderAddr && empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderAddr}"/>
															</td>
														</c:when>
														<c:when test="${empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:when test="${!empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderAddr} ${rtn.senderAddrDetail}"/>
															</td>
														</c:when>
														<c:otherwise>
															<td style="padding:0px;">
																<input type="text" style="width:100%;height:36px;" readonly value=""/>
															</td>
														</c:otherwise>
													</c:choose>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderTel}"/>
													</td>
												</tr>
												<c:if test="${rtn.dstnNation eq 'US'}">
													<tr>
													<th class="center colorBlack" style="width:10%;">빌딩 명</th>
													<td style="padding:0px;" colspan="3">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.senderBuildNm}"/>
													</td>
												</tr>
												</c:if>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<div class="col-xs-12 col-lg-12">
											<label>
												<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-left:10px; margin-right:10px;">반송 구분</font>
												<input style="line-height:1.5;" type="radio" name="returnType" id="returnType1" disabled <c:if test="${rtn.returnType eq 'N'}"> checked </c:if> value="N" class="rtn_radio">&nbsp;일반배송
												&nbsp;<input style="line-height:1.5;" type="radio" name="returnType" id="returnType2" disabled <c:if test="${rtn.returnType eq 'E'}"> checked </c:if> value="E" class="rtn_radio">&nbsp;긴급배송
											</label>
										</div>
									</div>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack">취소 사유</th>
													<td style="vertical-align:middle; background:white;">
														<input type="radio" name="returnReason" id="returnReason" disabled <c:if test="${rtn.returnReason eq 'A'}"> checked </c:if> value="A"><label for="cncl">&nbsp;단순변심</label> 
														<input type="radio" name="returnReason" id="returnReason" disabled <c:if test="${rtn.returnReason eq 'B'}"> checked </c:if> value="B"><label for="cncl">&nbsp;파손</label> 
														<input type="radio" name="returnReason" id="returnReason" disabled <c:if test="${rtn.returnReason eq 'C'}"> checked </c:if> value="C"><label for="cncl">&nbsp;불량</label>
														<input type="radio" name="returnReason" id="returnReason" disabled <c:if test="${rtn.returnReason eq 'D'}"> checked </c:if> value="D"><label for="cncl">&nbsp;기타</label>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">취소 사유 상세</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" readonly value="${rtn.returnReasonDetail}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="3">위약 반송 정보 <small style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해 주세요</small></th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">위약 반송</th>
													<td style="vertical-align:middle; background:white;">
														<input type="radio" name="taxType" id="taxType" disabled <c:if test="${rtn.taxType eq 'Y' }">checked</c:if> value="Y"><label for="cncl">&nbsp;위약 반송</label>
														<input type="radio" name="taxType" id="taxType" disabled <c:if test="${rtn.taxType eq 'N' }">checked</c:if> value="N"><label for="cncl">&nbsp;일반 반송</label>
														<input type="hidden" name="taxReturn" id="taxReturn" value="C">
													</td>
												</tr>
												<c:if test="${rtn.taxType eq 'Y'}">
													<tr>
														<th class="center colorBlack" style="width:12%;">반송 사유서</th>
														<td style="vertical-align: middle; background:white;">
															<c:if test="${rtn.taxType eq 'Y'}">
																<a href="${rtn.fileReason}" target='_blank' download>[반송사유서 다운로드]</a>
															</c:if>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:12%;">반품 접수 캡쳐본</th>
														<td style="vertical-align: middle; background:white;">
															<c:if test="${rtn.taxType eq 'Y'}">
																<a href='${rtn.fileCapture}' target='_blank' download>[반품접수 캡쳐본 다운로드]</a>
															</c:if>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:12%;">반품 메신저 캡쳐본</th>
														<td style="vertical-align: middle; background:white;">
															<c:if test="${rtn.taxType eq 'Y'}">
																<a href='${rtn.fileMessenger}' target='_blank' download>[반품 매신져 캡쳐본 다운로드]</a>
															</c:if>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:12%;">반품 Commercial Invoice</th>
														<td style="vertical-align: middle; background:white;">
															<c:if test="${rtn.taxType eq 'Y'}">
																<a href='${rtn.fileCl}' target='_blank' download>[반품 commercial invoice 다운로드]</a>
															</c:if>
														</td>
													</tr>
													<tr>
														<th class="center colorBlack" style="width:12%;">환급 통장 사본</th>
														<td style="vertical-align: middle; background:white;">
															<c:if test="${rtn.taxType eq 'Y'}">
																<a href='${rtn.fileCopyBank}' target='_blank' download>[환급 통장 사본 다운로드]</a>
															</c:if>
														</td>
													</tr>
												</c:if>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<c:forEach items="${itemList}" var="item" varStatus="voStatus">
											<div id="addForm">
												<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
													<div class="col-xs-12 col-lg-12" style="font-size: 3;">
														<h4>상품 ${voStatus.index+1}</h4>
														<div class="hr dotted"></div>
														<div class="row hr-8">
															<div class="col-xs-1 center">SUB NO</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="text" readonly name="subNo" id="subNo" value="${item.subNo}" readonly/>
															</div>
															<div class="col-xs-1 center mp07">상품명</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 backYellow" type="text" readonly name="itemDetail" id="itemDetail" value="${item.itemDetail}" />
															</div>
															<div class="col-xs-1 center mp07 ">브랜드</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 backYellow" type="text" readonly name="brand" id="brand" value="${item.brand}"/>
															</div>
														</div>
														<div class="hr dotted"></div>
														<div class="row hr-8">
															<div class="col-xs-1 center">상품 무게</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="number" readonly name="itemWta" id="itemWta" value="${item.itemWta}" />
															</div>
															<div class="col-xs-1 center">무게 단위</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="text" readonly name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
															</div>
															<div class="col-xs-1 center">상품 개수</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="number" readonly name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
															</div>
														</div>
														<div class="hr dotted"></div>
														<div class="row form-group hr-8">
															<div class="col-xs-1 center mp07">상품 가격</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 backYellow" type="number" readonly name="unitValue" id="unitValue" value="${item.unitValue}" />
															</div>
															<div class="col-xs-1 center mp07">통화</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="text" readonly name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" />
															</div>
														</div>
														<div class="hr dotted"></div>
														<div class="row hr-8">
															<div class="col-xs-1 center mp07">
																<label>제조국</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 backYellow" type="text" readonly name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
															</div>
															<div class="col-xs-1 center mp07">
																<label>제조회사</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 backYellow" type="text" readonly name="makeCom" id="makeCom" value="${item.makeCom}" />
															</div>
															<div class="col-xs-1 center mp07">
																<label><a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" style="text-decoration:underline;">HS CODE</a></label>
															</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 backYellow" type="text" readonly name="hsCode" id="hsCode" value="${item.hsCode}" />
															</div>
														</div>
														<div class="hr dotted"></div>
														<div class="row hr-8">
															<div class="col-xs-1 center mp07">
																<label>상품 URL</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 backYellow" type="text" readonly name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
															</div>
															<div class="col-xs-1 center mp07">
																<label>상품 이미지 URL</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 backYellow" type="text" readonly name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
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
										<table class="table table-bordered" style="width:100%;">
											<thead>
												<tr>
													<th class="center colorBlack" style="width:10%;">입고 요청사항</th>
													<td style="padding:0px;">
														<input style="width:100%; height:36px;" type="text" readonly name="whMsg" value="${rtn.whMsg}" id="whMsg" class="comm_text">
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="rtnBtn_div" style="text-align:center;">
										<input type="button" class="btn btn-outline-secondary btn-lg" style="width:10%;height: 50px;font-size: medium;" id="backBtn" name="backBtn" value="뒤로가기">
									</div>
								</form>
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
		
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		$("#backBtn").on("click",function(e){
			window.location.href = "/return/list";
		});


		</script>
		<!-- script addon end -->
	</body>
</html>
