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
  		
  		
	  
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>검품배송 신청 수정</title>
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
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">검품배송 신청 수정</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h3>
									검품배송 신청 수정
								</h3>
							</div>
							<form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" id="cnt" name="cnt" value=""/>
							<div id="inner-content-side" >
								<div class="row">
									<div class="col-xs-12 col-lg-12" >
										<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
											<h4>주문정보</h4>
										</div>
										<!-- style="border:1px solid red" -->
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
												<h5>사용자 입력 정보</h5>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														주문 번호
													</div>
													<div class="profile-info-value">
														<input class="col-lg-12" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
														<input type="hidden" name="nno" id="nno" value="${userOrder.nno }">
													</div>
													<div class="profile-info-name">
														송장 번호
													</div>
													<div class="profile-info-value" style="display:table-cell; vertical-align: middle; ">
															<%-- <div class="col-lg-12">
																<input type="text" name="tags" id="form-field-tags" value="${userOrder.hawbNo}" placeholder="송장번호 추가" />
															</div> --%>
														<div class="col-lg-12">
															<div class="input-group input-group-sm">
																<input class="col-lg-12" type="text" readonly name="hawbNo" id="hawbNo" value="${userOrder.hawbNo}" />
																<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
																	<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
																</span>
															</div>
														</div>
													</div>
													<div class="profile-info-name">
														주문 날짜
													</div>
													<div class="profile-info-value">
														<div class="col-lg-12">
															<div class="input-group input-group-sm">
																<span class="input-group-addon">
																	<i class="ace-icon fa fa-calendar"></i>
																</span>
																<input type="text" id="orderDate" name="orderDate" class="form-control" value="${userOrder.orderDate}"/>
															</div>
														</div>
														<%-- <input class="col-lg-12" type="text" name="orderDate" id="orderDate" value="${userOrder.orderDate}" placeholder="ex):20201231" /> --%>
													</div>
												</div>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														무게
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="userWta" id="userWta" value="${userOrder.userWta}" />
													</div>
													<div class="profile-info-name">
														부피 무게
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="userWtc" id="userWtc" value="${userOrder.userWtc}" />
													</div>
													<div class="profile-info-name">
														박스 개수
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="boxCnt" id="boxCnt" value="${userOrder.boxCnt}" />
													</div>
												</div>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														사용자 입력 높이
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="userHeight" id="userHeight" value="${userOrder.userHeight}" />
													</div>
													<div class="profile-info-name">
														사용자 입력 길이
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="userLength" id="userLength" value="${userOrder.userLength}" />
													</div>
													<div class="profile-info-name">
														사용자 입력 넓이
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="userWidth" id="userWidth" value="${userOrder.userWidth}" />
													</div>
												</div>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														출발지
													</div>
													<div class="profile-info-value">
														<select class="form-control nations" id="orgStation" name="orgStation">
															<option value="">  </option>
															<c:forEach items="${userOrgStation}" var="orgStationList">
																<option <c:if test="${userOrder.orgStation eq orgStationList.stationCode}"> selected </c:if>value="${orgStationList.stationCode}">${orgStationList.stationName}</option>
															</c:forEach>
														</select>
													</div>
													<div class="profile-info-name">
														도착지
													</div>
													<div class="profile-info-value">
														<select class="form-control nations" id="dstnNation" name="dstnNation">
															<option value="">  </option>
															<c:forEach items="${userDstnNation}" var="dstnNationList">
																<option <c:if test="${userOrder.dstnNation eq dstnNationList}"> selected </c:if>value="${dstnNationList}">${dstnNationList}</option>
															</c:forEach>
														</select>
													</div>
													<div class="profile-info-name">
														택배 회사
														<input type="hidden" id="selectTrans" name="selectTrans" value="${userOrder.transCode}">
													</div>
													<div class="profile-info-value">
														<select class="form-control" id="transCode" name="transCode">
															<option value="">  </option>
														</select>
													</div>
												</div>
												
											</div>
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
												<h4>발송 정보</h4>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">이름</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperName" id="shipperName" value="${userOrder.shipperName}" />
													</div>
													<div class="profile-info-name">전화번호</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
													</div>
													<div class="profile-info-name">휴대전화 번호</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
													</div>
												</div>
												
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">발송 국가</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperCntry" id="shipperCntry" value="${userOrder.shipperCntry}" />
													</div>
													<div class="profile-info-name">발송 도시</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperCity" id="shipperCity" value="${userOrder.shipperCity}" />
													</div>
													
													<div class="profile-info-name">발송 State</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperState" id="shipperState" value="${userOrder.shipperState}" />
													</div>
												</div>
												
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														<label>우편주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
													</div>
												
													<div class="profile-info-name">
														<label>주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
													</div>
													
													<div class="profile-info-name">
														<label>상세주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userOrder.shipperAddrDetail}" />
													</div>
												</div>
											</div>
											
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
												<h4>수취 정보</h4>
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">이름</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName}" />
													</div>
													<div class="profile-info-name">전화번호</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeTel" id="cneeTel" value="${userOrder.cneeTel}" />
													</div>
													<div class="profile-info-name">휴대전화 번호</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeHp" id="cneeHp" value="${userOrder.cneeHp}" />
													</div>
												</div>
												
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">수취 국가</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeCntry" id="cneeCntry" value="${userOrder.cneeCntry}" />
													</div>
													<div class="profile-info-name">수취 도시</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity}" />
													</div>
													
													<div class="profile-info-name">수취 State</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
													</div>
												</div>
												
												<div class="profile-user-info  form-group hr-8">
													<div class="profile-info-name">
														<label>우편주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
													</div>
												
													<div class="profile-info-name">
														<label>주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
													</div>
													
													<div class="profile-info-name">
														<label>상세주소</label>
													</div>
													<div class="profile-info-value">
														<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 기본정보 End -->
									<!-- 설정정보 Start -->
									<br/>
									<br/>
									<div class="col-xs-12 col-lg-12" >
										<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
											<h2>상품정보</h2>
										</div>
										<!-- style="border:1px solid red" -->
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div style="text-align: right">
												<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
													삭제
												</button> -->
												<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
													추가
												</button>
											</div>
											<br/>
											<input type="hidden" id="itemCnt" name="itemCnt" value="${fn:length(userOrderItem)}">
											
											<div id="accordion" class="accordion-style1 panel-group">
											<c:forEach items="${userOrderItem}" var="orderItem" varStatus="status">
												<div class="panel panel-default panels${status.count}">
													<div class="panel-heading">
														<h4 class="panel-title">
															<span class="accordion-toggle" >
																<span data-toggle="collapse" href="#collapse${status.count}" >
																&nbsp;Item #${status.count}
																<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
																</span>
																<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
															</span>
														</h4>
													</div>
		
													<div class="panel-collapse collapse multi-collapse in" id="collapse${status.count}">
														<div class="panel-body">
															<div class="row form-group hr-8">
															<div class="col-lg-12 col-xs-12">
																<div class="col-lg-4 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >HS_CODE</div>
																	<div class="col-lg-9 col-xs-12">
																		<input class="width-100" type="text" name="hsCode" id="hsCode${status.count}" value="${orderItem.hsCode}" />
																		<input class="width-100" type="hidden" name="subNo" id="subNo${status.count}" value="${status.count}" />
																	</div>
																</div>
																<div class="col-lg-4 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >상품 종류</div>
																	<div class="col-lg-9 col-xs-12">
																		<input class="width-100" type="text" name="itemDiv" id="itemDiv${status.count}" value="${orderItem.itemDiv}" />
																	</div>
																</div>
																<div class="col-lg-4 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
																	<div class="col-lg-9 col-xs-12">
																		<input class="width-100" type="text" name="brandItem" id="brand${status.count}" value="${orderItem.brand}" />
																	</div>
																</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >송장 번호</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="trkNo" id="trkNo${status.count}" value="${orderItem.trkNo}" />
																		<%-- <div class="input-group input-group-sm">
																			<input class="col-lg-12" type="text" readonly name="hawbNo" id="hawbNo1" value="${userOrder.hawbNo}" />
																			<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
																				<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
																			</span>
																		</div> --%>
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >택배 회사</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="trkCom" id="trkCom${status.count}" value="${orderItem.trkCom}" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >상품명</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="itemDetail" id="itemDetail${status.count}" value="${orderItem.itemDetail}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >상품 개수</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="itemCnt" id="itemCnt${status.count}" value="${orderItem.itemCnt}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >상품재질</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="itemMeterial" id="itemMeterial${status.count}" value="${orderItem.itemMeterial}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >ITEM_URL</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="itemUrl" id="itemUrl${status.count}" value="${orderItem.itemUrl}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >ITEM_IMAGE_URL</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="itemImgUrl" id="itemImgUrl${status.count}" value="${orderItem.itemImgUrl}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >USER_WTA</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="userWtaItem" id="userWta${status.count}" value="${orderItem.userWta}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >USER_WTC</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="userWtcItem" id="userWtc${status.count}" value="${orderItem.userWtc}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >WT_UNIT</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="wtUnit" id="wtUnit${status.count}" value="${orderItem.wtUnit}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >UNIT_VALUE</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="unitValue" id="unitValue${status.count}" value="${orderItem.unitValue}" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >QTY_UNIT</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="qtyUnit" id="qtyUnit${status.count}" value="${orderItem.qtyUnit}" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >MAKE_CNTRY</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="makeCntry" id="makeCntry${status.count}" value="${orderItem.makeCntry}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >MAKE_COM</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="makeCom" id="makeCom${status.count}" value="${orderItem.makeCom}" />
																	</div>
																</div>
															</div>
															
															
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >PACKAGE_UNIT</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="packageUnit" id="packageUnit${status.count}" value="${orderItem.packageUnit}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >EXCHANGE_RATE</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="exchangeRate" id="exchangeRate${status.count}" value="${orderItem.exchangeRate}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >CHG_CURRENCY</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="chgCurrency" id="chgCurrency${status.count}" value="${orderItem.chgCurrency}" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >CHG_AMT</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="chgAmt" id="chgAmt${status.count}" value="${orderItem.chgAmt}" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >TAKE_IN_CODE</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="takeInCode" id="takeInCode${status.count}" value="${orderItem.takeInCode}" />
																	</div>
																</div>
															</div>
															
															
	
														</div>
													</div>
												</div>
												</c:forEach>
											</div>
										</div>
									</div>
									<!-- 설정정보 End -->
									
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
							</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		</div>
		
		<div id="modal-form" class="modal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">송장번호를 입력해 주세요</h4>
					</div>
	
					<div class="modal-body">
						<div class="row">
							<div class="col-lg-12">
								<input type="text" name="tags" id="form-field-tags" value="${userOrder.hawbNo}" placeholder="송장번호 추가" maxlength="20"/>
							</div>
						</div>
					</div>
	
					<div class="modal-footer">
						<button class="btn btn-sm" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i>
							Cancel
						</button>
	
						<button class="btn btn-sm btn-primary" id="modalSave" name="modalSave" data-dismiss="modal">
							<i class="ace-icon fa fa-check"></i>
							Save
						</button>
					</div>
				</div>
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
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			var cnt = $("#itemCnt").val();
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					$('#dstnNation').trigger("change");
				});
				//select target form change
			})
			
			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});
			function fn_delete(test){
				if($(".panel").length > 1){
					test.closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}
			};
			
			/* $('[name*=delBtns]').on('click',function(e){
				if($(".panel").length > 1){
					$(this).closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}*/
				
				/* $('input:checked').val(); 
			}); */

			$('#backToList').on('click', function(e){
				history.back();


			});

			$(".nations").on('change',function(e){

				var param1 = $("#orgStation").val();
				var param2 = $("#dstnNation").val();

				$.ajax({
					url:'/cstmr/apply/transComList',
					type: 'POST',
					datatype: "json",
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: { param1 : param1,
							param2 : param2
						},
					success : function(data) {
						addHtml = "";
						for(var i = 0; i < data.transComList.length; i++){
							addHtml += "<option ";
							if(data.transComList[i].transCode == $("#selectTrans").val()){
								addHtml += "selected ";
							}
							addHtml += "value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transCode+"</option>'"
						}
						$("#transCode").html(addHtml)
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
				

			})

			$('#addBtns').on('click',function(e){
				$("#cnt").val(cnt);
				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/itemList',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						$("#accordion").append(data);
						cnt++;
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});

			$('#rgstrInfo').on('click',function(e){

				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/modifyOrderInfoInsp',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						if(data == "S"){
							alert("등록 되었습니다.");
							location.href="/cstmr/apply/shpngAgncy";
						}else{
							alert("등록중 오류가 발생했습니다. 데이터를 확인해 주세요")
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

			});

			var tag_input = $('#form-field-tags');
			try{
				tag_input.tag(
				  {
					placeholder:tag_input.attr('placeholder'),
					maxlength:20,
					//enable typeahead by specifying the source array
					//source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
					/**
					//or fetch data from database, fetch those that match "query"
					source: function(query, process) {
					  $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
					  .done(function(result_items){
						process(result_items);
					  });
					}
					*/
				  }
				)
		
				//programmatically add/remove a tag
				var $tag_obj = $('#form-field-tags').data('tag');
				var index = $tag_obj.inValues('some tag');
				$tag_obj.remove(index);
			}
			catch(e) {
				//display a textarea for old IE, because it doesn't support this plugin or another one I tried!
				tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
				//autosize($('#form-field-tags'));
			}

			

			$('#modal-form input[type=file]').ace_file_input({
				style:'well',
				btn_choose:'Drop files here or click to choose',
				btn_change:null,
				no_icon:'ace-icon fa fa-cloud-upload',
				droppable:true,
				thumbnail:'large'
			})
			
			//chosen plugin inside a modal will have a zero width because the select element is originally hidden
			//and its width cannot be determined.
			//so we set the width after modal is show
			$('#modal-form').on('shown.bs.modal', function () {
				var $tag_obj = $('#form-field-tags').data('tag');
				var rooplen = $tag_obj.values.length;
				for(var i=0;i<rooplen;i++){
					$tag_obj.remove(0);
				}
				if($('#hawbNo').val()!=""){
					for(var i = 0; i < $('#hawbNo').val().split(',').length; i++){
						$tag_obj.add($('#hawbNo').val().split(',')[i]);
					}
					
				}
				
				$("#form-field-tags").val($('#hawbNo').val());
				if(!ace.vars['touch']) {
					$(this).find('.chosen-container').each(function(){
						$(this).find('a:first-child').css('width' , '210px');
						$(this).find('.chosen-drop').css('width' , '210px');
						$(this).find('.chosen-search input').css('width' , '200px');
					});
				}
			})
			
			$('#modalSave').on('click',function(e){
				$('#hawbNo').val($("#form-field-tags").val());
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
