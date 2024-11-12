<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
	  
	  .colorBlack {color:#000000 !important;}
	  
	  .infoName{background: yellow;}
	  .profile-info-name, .profile-info-value {
	  		border-top: none;
	  		border-bottom: 1px dotted #D5E4F1;
  		}
  		.mp07{
  			margin:0px;
  			padding:7px;
  		}  	
  		.orderNm {
	  	color: #DD5A43;
	  }
	  /* 
	  
		table {
			width: 100%;
			border: solid 1px #ddd;
			border-collapse: collapse;
			box-shadow: 1px 1px 1px #ddd;
			font-size: 14px;
			box-shadow: 1px black;
			background: #fff;
		}
		
		table th {
			font-family: 'Open Sans';
			text-align: center;
			padding: 5px 0;
			font-size: 14px;
			color: #000000 !important;
			font-weight: bold;
			background: linear-gradient(to top, #ebeff5, white);
			border: solid 1px #ccc;
		} */
		
		input[readonly="readonly"] {
			background-color: "#ebeff5";
		}


	 </style>
	<!-- basic scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head> 
	<title>사입 주문 등록</title>
	<body>
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>사입 주문 등록</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">수기 등록</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="page-content">
								<div class="page-header">
									<h3>
										사입 주문 등록
									</h3>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<input type="hidden" id="userId" name="userId" value="${userOrder.userId}"/>
								<input type="hidden" id="nno" name="nno" value="${userOrder.nno}"/>
								<div class="col-xs-12 col-lg-12 " style="font-size: 5;">
									<h2>주문정보</h2>
								</div>
								<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
									<div class="col-xs-12 col-lg-12" style="font-size: 3;">
										<h4>운송장 정보</h4>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 red">
												출발지
											</div>
											<div class="col-xs-3 center">
												<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" disabled id="orgStation" name="orgStation">
													<c:forEach items="${userOrgStation}" var="orgStationList">
														<option value="${orgStationList.stationCode}"<c:if test="${fn:trim(userOrder.orgStation) eq fn:trim(orgStationList.stationCode)}">selected</c:if>>${orgStationList.stationName}</option>
													</c:forEach>
												</select>
											</div>
											<input type="hidden" id="orgStation" name="orgStation" value="${userOrder.orgStation}"/>
											<div class="col-xs-1 center mp07 red">
												도착지
											</div>
											<div class="col-xs-3 center">
												<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" disabled id="dstnNation" name="dstnNation">
													<c:forEach items="${nationList}" var="dstnNationList">
														<option value="${dstnNationList.nationEName}"<c:if test= "${fn:trim(userOrder.dstnNation) eq fn:trim(dstnNationList.nationCode)}">selected</c:if>>${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
													</c:forEach>
												</select>
											</div>
											<input type="hidden" id="dstnNation" name="dstnNation" value="${userOrder.dstnNation}"/>
											<div class="col-xs-1 center mp07 red">
												관세 지급 방식
											</div>
											<div class="col-xs-3 center">
												<select class="form-control" id="payment" name="payment">
													<option value="DDP" <c:if test = "${userOrder.payment eq 'DDP'}"> selected</c:if>>DDP</option>
													<option value="DDU" <c:if test = "${userOrder.payment eq 'DDU'}"> selected</c:if>>DDU</option>
													<option value="FOB" <c:if test = "${userOrder.payment eq 'FOB'}"> selected</c:if>>FOB</option>
													<option value="CFR" <c:if test = "${userOrder.payment eq 'CFR'}"> selected</c:if>>CFR</option>
													<option value="CIF" <c:if test = "${userOrder.payment eq 'CIF'}"> selected</c:if>>CIF</option>
													<option value="EXW" <c:if test = "${userOrder.payment eq 'EXW'}"> selected</c:if>>EXW</option>
													<option value="FCA" <c:if test = "${userOrder.payment eq 'FCA'}"> selected</c:if>>FCA</option>
													<option value="FAS" <c:if test = "${userOrder.payment eq 'FAS'}"> selected</c:if>>FAS</option>
													<option value="CPT" <c:if test = "${userOrder.payment eq 'CPT'}"> selected</c:if>>CPT</option>
													<option value="CIP" <c:if test = "${userOrder.payment eq 'CIP'}"> selected</c:if>>CIP</option>
													<option value="DAF" <c:if test = "${userOrder.payment eq 'DAF'}"> selected</c:if>>DAF</option>
													<option value="DES" <c:if test = "${userOrder.payment eq 'DES'}"> selected</c:if>>DES</option>
													<option value="DEQ" <c:if test = "${userOrder.payment eq 'DEQ'}"> selected</c:if>>DEQ</option>
												</select>
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 red">주문번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
											</div>
											<div class="col-xs-1 center mp07 red">주문날짜</div>
											<div class="col-xs-3 center">
												<div>
													<div class="input-group input-group-sm">
														<span class="input-group-addon">
															<i class="ace-icon fa fa-calendar"></i>
														</span>
														<input type="text" id="orderDate" name="orderDate" class="form-control" value="${userOrder.orderDate}"/>
													</div>
												</div>
											</div>
											<div class="col-xs-1 center mp07">화장품 포함 여부</div>
											<div class="col-xs-3 center">
												<select class="col-xs-12" id="cosmetic" name="cosmetic">
													<option value="N" <c:if test="${userOrder.cosmetic eq 'N'}"> selected </c:if>>N</option>
													<option value="Y" <c:if test="${userOrder.cosmetic eq 'Y'}"> selected </c:if>>Y</option>
												</select>
											</div>
										</div>
										<div class="hr dotted"></div>
									</div>
									
									<div class="col-xs-12 col-lg-12" style="font-size: 3;">
										<h4>발송 정보</h4>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07">이름</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="shipperName" id="shipperName" value="${userInfo.comEName}" />
											</div>
											<div class="col-xs-1 center mp07">전화번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
											</div>
											<div class="col-xs-1 center mp07">휴대전화 번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row form-group hr-8">
											<div class="col-xs-1 center mp07">발송 도시</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="shipperCity" id="shipperCity" value="${userOrder.shipperCity}" />
											</div>
											<div class="col-xs-1 center mp07">발송 State</div>
											<div class="col-xs-3 center ">
												<input class="col-xs-12" type="text" name="shipperState" id="shipperState" value="${userOrder.shipperState}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07"><label>우편주소</label></div>
											<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
											</div>
											<div class="col-xs-1 center mp07"><label>주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
											</div>
											<div class="col-xs-1 center mp07"><label>상세주소</label></div>
											<div class="col-xs-3 center ">
												<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userOrder.shipperAddrDetail}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07"><label>Email</label></div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="userEmail" id="userEmail" value="${userOrder.userEmail}"/>
											</div>
											<div class="col-xs-1 center mp07"><label>Reference</label></div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="shipperReference" id="shipperReference" value="${userOrder.shipperReference}"/>
											</div>
											<div class="col-xs-1 center mp07"><label>입고 메모</label></div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="whReqMsg" id="whReqMsg" value="${userOrder.whReqMsg}"  />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07">
												세금식별 코드
											</div>
											<div class="col-xs-3">
												<select class="width-100" id="shipperTaxType" name="shipperTaxType">
													<c:forEach items="${taxTypeList}" var="taxTypeList">
														<option value="${taxTypeList.code}"  <c:if test="${taxTypeList.code eq userOrder.shipperTaxType}"> selected</c:if>>${taxTypeList.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-xs-1 center mp07">
												세금식별 번호
											</div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="shipperTaxNo" id="shipperTaxNo" value="${userOrder.shipperTaxNo}">
											</div>
										</div>
										<div class="hr dotted"></div>
									</div>
									<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
										<h4>수취 정보</h4>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 red">이름</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName}" />
											</div>
											<div class="col-xs-1 center mp07 red">전화번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다."  numberOnly type="text" name="cneeTel" id="cneeTel" value="${userOrder.cneeTel}" />
											</div>
											<div class="col-xs-1 center mp07">휴대전화 번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다."  numberOnly type="text" name="cneeHp" id="cneeHp" value="${userOrder.cneeHp}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 red">주 (state)</div>
											<div class="col-xs-2 center">
												<input class="col-xs-12" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
											</div>
											<div class="col-xs-1 center mp07 red">도시 (city/province)</div>
											<div class="col-xs-2 center">
												<input class="col-xs-12" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity}" />
											</div>
											<div class="col-xs-1 center mp07">구역 (district)</div>
											<div class="col-xs-2 center">
												<input class="col-xs-12" type="text" name="cneeDistrict" id="cneeDistrict" value="${userOrder.cneeDistrict}" />
											</div>
											<div class="col-xs-1 center mp07">동 (ward)</div>
											<div class="col-xs-2 center">
												<input class="col-xs-12" type="text" name="cneeWard" id="cneeWard" value="${userOrder.cneeWard}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 red"><label>우편주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
											</div>
											<div class="col-xs-1 center mp07 red"><label>주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
											</div>
											<div class="col-xs-1 center mp07"><label>상세주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										
										<div class="row hr-8">
											<div class="col-xs-1 center mp07"><label>현지 이름</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="nativeCneeName" id="nativeCneeName" value="${urderOrder.nativeCneeName}" />
											</div>
											<div class="col-xs-1 center mp07"><label>현지 주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="${userOrder.nativeCneeAddr}" />
											</div>
											<div class="col-xs-1 center mp07"><label>현지 상세주소</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="${userOrder.nativeCneeAddrDetail}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07">E-mail</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="cneeEmail" id="cneeEmail" value="${userOrder.cneeEmail}" />
											</div>
											<div class="col-xs-1 center mp07"><label>수취인 요구사항</label></div>
											<div class="col-xs-3 center">
												<input  class="col-xs-12" type="text" name="dlvReqMsg" id="dlvReqMsg" value="${userOrder.dlvReqMsg}" />
											</div>
											<div class="col-xs-1 center mp07""><label>구매 사이트</label></div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="buySite" id="buySite" value="${userOrder.buySite}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07">
												세금식별 코드
											</div>
											<div class="col-xs-3">
												<select class="width-100" id="cneeTaxType" name="cneeTaxType">
													<c:forEach items="${taxTypeList}" var="taxTypeList">
														<option value="${taxTypeList.code}"  <c:if test="${taxTypeList.code eq userOrder.cneeTaxType}"> selected</c:if>>${taxTypeList.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-xs-1 center mp07">
												세금식별 번호
											</div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="cneeTaxNo" id="cneeTaxNo" value="${userOrder.cneeTaxNo}">
											</div>
											<div class="col-xs-1 center mp07">
												수취인 ID 번호
											</div>
											<div class="col-xs-3">
												<input class="col-xs-12" type="text" name="customsNo" id="customsNo" value="${userOrder.customsNo}"/>
											</div>
										</div>
										<div class="hr dotted"></div>
										
									</div>

									<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
										<h4 style="margin-top: 20px;">수출신고 정보</h4>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07 orderNm">
													수출신고
											</div>
											<div class="col-xs-2 center">
												<select class="col-xs-12 orderVal" id="expType" name="expType">
													<option value="N" <c:if test="${userOrder.expType eq 'N'}"> selected </c:if>>수출신고 안함</option>
													<option value="F" <c:if test="${userOrder.expType eq 'F'}"> selected </c:if>>일반 수출신고</option>
													<option value="S" <c:if test="${userOrder.expType eq 'S'}"> selected </c:if>>간이 수출신고</option>
													<option value="E" <c:if test="${userOrder.expType eq 'E'}"> selected </c:if>>수출목록변환신고</option>
												</select>
											</div>
										</div>
										<div class="row hr-8 hide" id="expInputDiv" name="expInputDiv" style="font-size:12px !important;">
											<div class="col-md-4 col-lg-12">
												<div class="col-lg-2 center mp07" id="expCor-title">수출화주사업자상호명</div>
												<div class="col-lg-4 center">
													<input type="text" name="expCor" id="expCor" value="${userOrder.expCor}" style="width:100%;">
												</div>	
												<div class="col-lg-2 center mp07" id="expRprsn-title">수출화주사업자대표명</div>
												<div class="col-lg-4 center">
													<input type="text" name="expRprsn" id="expRprsn" value="${userOrder.expRprsn}" style="width:100%;">
												</div>	
											</div>
											<br/>
											<div class="col-md-4 col-lg-12 hr-4" >
												<div class="col-lg-2 center mp07" id="expRgstrNo-title">수출화주사업자번호</div>
												<div class="col-lg-4 center">
													<input type="text" name="expRgstrNo" id="expRgstrNo" value="${userOrder.expRgstrNo}" style="width:100%;">
												</div>	
												<div class="col-lg-2 center mp07" id="expAddr-title">수출화주사업자주소</div>
												<div class="col-lg-4 center">
													<input type="text" name="expAddr" id="expAddr" value="${userOrder.expAddr}" style="width:100%;">
												</div>	
											</div>
											<br/>
											<div class="col-md-4 col-lg-12 hr-4">
												<div class="col-lg-2 center mp07" id="expZip-title">수출화주사업자우편번호</div>
												<div class="col-lg-4 center">
													<input type="text" name="expZip" id="expZip" value="${userOrder.expZip}" style="width:100%;">
												</div>	
												<div class="col-lg-2 center mp07" id="expCstCd-title">수출화주통관부호</div>
												<div class="col-lg-4 center">
													<input type="text" name="expCstCd" id="expCstCd" value="${userOrder.expCstCd}" style="width:100%;">
												</div>	
											</div>
											<br/>
											<div class="col-md-4 col-lg-12 hr-4">
												<div class="col-lg-2 center mp07" id="agtCor-title">수출대행자상호명</div>
												<div class="col-lg-4 center">
													<input type="text" name="agtCor" id="agtCor" value="${userOrder.agtCor}" style="width:100%;">
												</div>	
												<div class="col-lg-2 center mp07" id="agtBizNo-title">수출대행자사업장일련번호</div>
												<div class="col-lg-4 center">
													<input type="text" name="agtBizNo" id="agtBizNo" value="${userOrder.agtBizNo}" style="width:100%;">
												</div>
											</div>
											<br/>
											<div class="col-md-4 col-lg-12">
												<div class="col-lg-2 center mp07" id="agtCstCd-title">수출대행자통관부호</div>
												<div class="col-lg-4 center">
													<input type="text" name="agtCstCd" id="agtCstCd" value="${userOrder.agtCstCd}" style="width:100%;">
												</div>	
												<div class="col-lg-2 center mp07" id="expNo-title">수출신고번호</div>
												<div class="col-lg-4 center">
													<input type="text" name="expNo" id="expNo" value="${userOrder.expNo}" style="width:100%;" placeholder="수출면장번호가 있는 경우만 입력">
												</div>	
											</div>
										</div>
										<div class="hr dotted"></div>
									</div>
								</div>
								
								<br/><br/><br/>
								
								<div class="col-xs-12 col-lg-12" >
									<div class="col-xs-12 col-lg-3 form-group" style="font-size: 5;">
										<h2>상품정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div style="text-align: right" id="add-row">
											<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
												삭제
											</button> -->
											<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
												상품 추가
											</button>
										</div>
										<br/>
										<c:forEach items="${userOrderItem}" var="orderItem" varStatus="status">
										<div class="col-xs-12 form-group item-info" style="border: 1px solid #e6e6e6;">
											<div class="row hr-8">
												<div class="col-xs-12" style="text-align:right;">
													<input type="button" class="btnTrDel btn btn-xs btn-primary" value="삭제"/>
												</div>
											</div>
											<div class="row hr-8">
												<div class="col-xs-12">
													<div class="col-xs-1 center mp07 red"><label>상품코드</label></div>
													<div class="col-xs-3">
														<input type="text" class="cusItemCode width-100" name="cusItemCode" readonly value="${orderItem.cusItemCode}"/>
													</div>
													<div class="col-xs-1 center mp07 red"><label>상품명</label></div>
													<div class="col-xs-3">
														<input type="text" class="itemDetail width-100" name="itemDetail" value="${orderItem.itemDetail}"/>
													</div>
													<div class="col-xs-1 center mp07"><label>현지상품명</label></div>
													<div class="col-xs-3">
														<input type="text" class="nativeItemDetail width-100" name="nativeItemDetail" value="${orderItem.nativeItemDetail}"/>
													</div>
												</div>
											</div>
											<div class="row hr-8">
												<div class="col-xs-12">
													<div class="col-xs-1 center mp07 red"><label>수량</label></div>
													<div class="col-xs-3">
														<input type="text" class="itemCnt width-100" numberOnly name="itemCnt" value="${orderItem.itemCnt}"/>
													</div>
													<div class="col-xs-1 center mp07 red"><label>단가</label></div>
													<div class="col-xs-3">
														<input type="text" class="unitValue width-100" floatOnly name="unitValue" value="${orderItem.unitValue}"/>
													</div>
													<div class="col-xs-1 center mp07 red"><label>통화</label></div>
													<div class="col-xs-3">
														<select id="chgCurrency" name="chgCurrency" class="width-100">
															<c:forEach items="${currencyList}" var="currencyList" varStatus="status">
															<option value="${currencyList.currency}" <c:if test="${currencyList.currency eq orderItem.chgCurrency}"> selected</c:if>>${currencyList.currency} / ${currencyList.nationName}</option>
															</c:forEach>
														</select>		
													</div>
												</div>
											</div>
											<div class="row hr-8">
												<div class="col-xs-12">
													<div class="col-xs-1 center mp07"><label>브랜드</label></div>
													<div class="col-xs-3">
														<input type="text" class="brand  width-100" name="brand" value="${orderItem.brand}"/>
													</div>
													<div class="col-xs-1 center mp07">상품 이미지 URL</div>
													<div class="col-xs-7">
														<input type="text" class="itemImgUrl  width-100" name="itemImgUrl" value="${orderItem.itemImgUrl}"/>
													</div>
												</div>
											</div>
										</div>
										</c:forEach>
										</div>
									</div>
									
									
								</div>
								<div class="col-lg-12 col-xs-12">
								<br />
									<div class="col-lg-1 col-xs-6 col-lg-offset-5">
										<div id="button-div">
											<div id="backPage" style="text-align: center">
												<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
											</div>
										</div>
									</div>
									<div class="col-lg-1 col-xs-6">
										<div id="button-div">
											<div id="rgstrUser" style="text-align: center">
												<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">수정</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
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
			//expDiv();
			expDiv2();
			var cnt = 1;

			$("#addBtns").on('click',function(e){
				var userId = "";
				userId = $("#userId").val();

				window.open("/cstmr/takein/popupTakeinUserItems?userId="+userId,"takeinItemWin", "width=720,height=600");
			});

			$('#expValue').on('change', function(e){
				expDiv();
			});

			$("#expType").on('change', function(e) {
				expDiv2();
			});

			function expDiv2() {
				var expType = $("#expType").val();

				if (expType == 'N') {
					$("#expInputDiv").addClass('hide');

					$("#expCor-title").removeClass('orderNm');
					$("#expCor").removeClass('orderVal');

					$("#expRprsn-title").removeClass('orderNm');
					$("#expRprsn").removeClass('orderVal');

					$("#expAddr-title").removeClass('orderNm');
					$("#expAddr").removeClass('orderVal');

					$("#expZip-title").removeClass('orderNm');
					$("#expZip").removeClass('orderVal');

					$("#expRgstrNo-title").removeClass('orderNm');
					$("#expRgstrNo").removeClass('orderVal');
					
				} else {
					$("#expInputDiv").removeClass('hide');

					$("#expCor-title").addClass('orderNm');
					$("#expCor").addClass('orderVal');

					if (expType == 'E') {

						$("#expRprsn-title").addClass('orderNm');
						$("#expRprsn").addClass('orderVal');

						$("#expAddr-title").addClass('orderNm');
						$("#expAddr").addClass('orderVal');

						$("#expZip-title").addClass('orderNm');
						$("#expZip").addClass('orderVal');

						$("#expRgstrNo-title").addClass('orderNm');
						$("#expRgstrNo").addClass('orderVal');

					} else {

						$("#expRprsn-title").removeClass('orderNm');
						$("#expRprsn").removeClass('orderVal');

						$("#expAddr-title").removeClass('orderNm');
						$("#expAddr").removeClass('orderVal');

						$("#expZip-title").removeClass('orderNm');
						$("#expZip").removeClass('orderVal');

						$("#expRgstrNo-title").removeClass('orderNm');
						$("#expRgstrNo").removeClass('orderVal');
					}
				}
			}
			

			function expDiv(){
				if($('#expValue').val() == 'noExplicence'){
					$('#expInputDiv').addClass('hide');
					
					$("#expBusinessNameHead").removeClass('orderNm');
					$("#expBusinessName").removeClass('orderVal');

					$("#expBusinessNumHead").removeClass('orderNm');
					$("#expBusinessNum").removeClass('orderVal');

					$("#agencyBusinessNameHead").removeClass('orderNm');
					$("#agencyBusinessName").removeClass('orderVal');
					
				}else if ($('#expValue').val() == 'registExplicence2'){
					$('#expInputDiv').removeClass('hide');

					//$('#expNoHead').removeClass('hide');
					//$('#expNo').removeClass('hide');

					$('#expNoHead').addClass('orderNm');
					$('#expNo').addClass('orderVal');
					
					
					$("#expBusinessNameHead").addClass('orderNm');
					$("#expBusinessName").addClass('orderVal');

					$("#expBusinessNumHead").addClass('orderNm');
					$("#expBusinessNum").addClass('orderVal');

					$("#agencyBusinessNameHead").removeClass('orderNm');
					$("#agencyBusinessName").removeClass('orderVal');
				}
				else{
					$('#expInputDiv').removeClass('hide');

					//$('#expNoHead').addClass('hide');
					//$('#expNo').addClass('hide');
					
					$("#expBusinessNameHead").addClass('orderNm');
					$("#expBusinessName").addClass('orderVal');

					$("#expBusinessNumHead").addClass('orderNm');
					$("#expBusinessNum").addClass('orderVal');

					if ($("#expValue").val() == 'simpleExplicence') {
						$("#agencyBusinessNameHead").removeClass('orderNm');
						$("#agencyBusinessName").removeClass('orderVal');
					} else {
						$("#agencyBusinessNameHead").addClass('orderNm');
						$("#agencyBusinessName").addClass('orderVal');	

						$('#expNoHead').removeClass('orderNm');
						$('#expNo').removeClass('orderVal');
					}

					$('#expNoHead').removeClass('orderNm');
					$('#expNo').removeClass('orderVal');
				}
			}

			function fn_takeinCodeIn(gCusItemCode, gUserId) {
				$.ajax({
					url:'/cstmr/takein/takeinCusItemInJson',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: gUserId,cusItemCode:gCusItemCode}, 
					success : function(data) {
						var Status = data.rstStatus;
						var currencyList = data.currencyList;
						
						if(Status == "SUCCESS"){
						
							var chkCnt = 0;
							$(".cusItemCode").each(function(){
							
								if(data.cusItemCode == $(this).val()){
									chkCnt++;
								}
							});

							if(chkCnt!=0){
								alert('이미 등록된 상품코드 입니다.');
								$("#cusItemCodeIn").attr("value","");
								$("#cusItemCodeIn").focus();
								return false;
							}

							if(chkCnt == 0){
								var html = "";
								html += '<div class="col-xs-12 form-group item-info" style="border: 1px solid #e6e6e6;">';
								html += '<div class="row hr-8"><div class="col-xs-12" style="text-align:right;">';
								html += '<input type="button" class="btnTrDel btn btn-xs btn-primary" value="삭제"/></div></div>';
								html += '<div class="row hr-8"><div class="col-xs-12">';
								html += '<div class="col-xs-1 center mp07 red"><label>상품코드</label></div>';
								html += '<div class="col-xs-3"><input type="text" class="cusItemCode width-100" name="cusItemCode" readonly value="'+data.cusItemCode+'"/></div>';
								html += '<div class="col-xs-1 center mp07 red"><label>상품명</label></div>';
								html += '<div class="col-xs-3"><input type="text" class="itemDetail width-100" name="itemDetail" value="'+data.itemDetail+'"/></div>';
								html += '<div class="col-xs-1 center mp07"><label>현지상품명</label></div>';
								html += '<div class="col-xs-3"><input type="text" class="nativeItemDetail width-100" name="nativeItemDetail" value="'+data.nativeItemDetail+'"/></div></div></div>';

								html += '<div class="row hr-8"><div class="col-xs-12">';
								html += '<div class="col-xs-1 center mp07 red"><label>수량</label></div>';
								html += '<div class="col-xs-3"><input type="text" numberOnly class="itemCnt width-100" name="itemCnt" value="0"/></div>';
								html += '<div class="col-xs-1 center mp07 red"><label>단가</label></div>';
								html += '<div class="col-xs-3"><input type="text" floatOnly class="unitValue width-100" name="unitValue" value="'+data.unitValue+'"/></div>';
								html += '<div class="col-xs-1 center mp07 red"><label>통화</label></div>';
								html += '<div class="col-xs-3"><select id="chgCurrency" name="chgCurrency" class="width-100">';

								for (var i = 0; i < currencyList.length; i++) {
									html += '<option value="'+currencyList[i].currency +'"';
									if (currencyList[i].currency == data.unitCurrency) {
										html += ' selected';
									}
									html += '>' + currencyList[i].currency + ' / ' + currencyList[i].nationName + '</option>';
								}

								html += '</select></div></div></div>';

								html += '<div class="row hr-8"><div class="col-xs-12">';
								html += '<div class="col-xs-1 center mp07"><label>브랜드</label></div>';
								html += '<div class="col-xs-3"><input type="text" class="brand  width-100" name="brand" value="'+data.brand+'"/></div>';
								html += '<div class="col-xs-1 center mp07">상품 이미지 URL</div>';
								html += '<div class="col-xs-7"><input type="text" class="itemImgUrl  width-100" name="itemImgUrl" value="'+data.itemImgUrl+'"/></div></div></div></div>';
								
								if ($(".item-info").length > 0) {
								    $(".item-info:last").after(html);
								} else {
								    $("#add-row").append(html);
								}

							}

							
						}else{
							alert(data.rstMsg);
							if(data.rstCode == "F10"){
								$("#userId").focus();
							}else{
								$("#cusItemCodeIn").focus();
							}
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			}
			
			$(document).on("keyup", "input:text[numberOnly]", function() {
			    $(this).val($(this).val().replace(/[^0-9]/g, ""));
			});

			$(document).on("keyup", "input:text[floatOnly]", function() {
			    $(this).val($(this).val().replace(/[^.0-9]/g, ""));
			});

			
			$('#backToList').on('click', function(e){
				history.back();
			});
			

			$(document).on("click",".btnTrDel",function(){
				if ($(".item-info").length > 1) {
			        $(this).closest(".item-info").remove();
			    } else {
			        alert("최소 1개의 상품 정보는 필요합니다.");
			        return false;
			    }
			});


			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});
			

			$(".nations").on('change',function(e){

				var param1 = $("#orgStation").val();
				var param2 = $("#dstnNation").val();

				$.ajax({
					url:'/cstmr/apply/transComList',
					type: 'POST',
					datatype: "json",
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: { param1 : param1,
							param2 : param2
						},
					success : function(data) {
						addHtml = "";
						if(data.transComList.length == 0){
							addHtml = "<option value=''>택배회사가 없습니다.</option>";
						}else{
							addHtml = "<option value=''>택배회사를 선택해주세요</option>";
							for(var i = 0; i < data.transComList.length; i++){
								addHtml += "<option value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transName+"</option>'"
							}
						}
						$("#transCode").html(addHtml)
						$("#testTransCode").val(data.transComList[0].transCode);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
				

			})
			
			
			$("#dstnNation").on('change', function() {
				if ($('#dstnNation').val() != "South Korea") {
					$("#cneeCityDiv").addClass('red');
				}
			})


			$("#rgstrInfo").on('click', function() {
				var valiChk = 0;

				if ($("#dstnNation").val() == "") {
					alert("도착지를 선택해주세요");
					return false;
				} 

				if ($("#orderNo").val() == "") {
					alert("주문 번호를 입력해주세요");
					return false;
				}

				if ($("#orderDate").val() == "") {
					alert("주문 날짜를 입력해주세요");
					return false;
					
				}

				if ($("#cneeName").val() == "") {
					alert("수취인 명을 입력해주세요");
					return false;
				}

				if ($("#cneeTel").val() == "") {
					alert("수취인 전화번호를 입력해주세요");
					return false;
				}
/*
				if ($("#cneeEmail").val() == "") {
					alert("수취인 Email을 입력해주세요");
					return false;
				}
*/
				if ($('#dstnNation').val() != "South Korea") {
					if ($("#cneeCity").val() == "") {
						alert("수취인 도시를 입력해주세요");
						return false;
					}
				}

				if ($("#cneeZip").val() == "") {
					alert("수취인 우편번호를 입력해주세요");
					return false;
				}

				$(".orderVal").each(function(e){
					if($(this).val() == ""){
						alert("운송장 정보의 "+$($(".orderNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				
				if(valiChk==1){
					return;
				}

				if ($("#cneeAddr").val() == "") {
					alert("수취인 주소를 입력해주세요");
					return false;
				}
				
				 
				$(".itemDetail").each(function() {
					if ($(this).val() == "") {
						alert("상품명을 입력해주세요.");
						$(this).focus();
						valiChk = 1;
						return false;
					}
				});
				
				if(valiChk==1){
					return;
				}
				
				$(".itemCnt").each(function() {
					if ($(this).val() == "" || $(this).val() == "0") {
						alert("상품 수량을 입력해주세요.");
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				
				if(valiChk==1) {
					return;
				}
				
				if ($(".cusItemCode").length < 1) {
					alert("최소 1개의 상품정보를 입력해야 합니다");
					return false;
				}
				
				

				var formData = $("#registForm").serialize();
				
				$.ajax({
					url : '/cstmr/takein/modifyTakeinOrderInfoUp',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						
						if (data == "S") {
							alert("수정 되었습니다.");
							location.href="/cstmr/takein/preorder";
						} else {
							alert("등록 중 오류가 발생하였습니다.");
						}
		            }, 		    
		            error : function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("수정에 실패하였습니다.");
					}
				})
				
				
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
			
		</script>
		<!-- script addon end -->
	</body>
</html>
