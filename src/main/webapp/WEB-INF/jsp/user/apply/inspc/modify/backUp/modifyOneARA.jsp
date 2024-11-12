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
  		
  		.mp07 {
			margin: 0px;
			padding: 7px;
		}
</style>
	<!-- basic scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
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
						<form id="registForm" name="registForm">
							<div class="page-content">
								<div class="page-header">
									<h3>
										검품배송 신청 수정
									</h3>
								</div>
								
								<div class="row hr-8">
									<div class="col-xs-1 center mp07">
										출발지
									</div>
									<div class="col-xs-3 center">
										<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" id="orgStation" name="orgStation">
											<option value="">  </option>
											<c:forEach items="${userOrgStation}" var="orgStationList">
												<option value="${orgStationList.stationCode}"<c:if test= "${fn:trim(userOrder.orgStation) eq fn:trim(orgStationList.stationCode)}">selected</c:if>>${orgStationList.stationName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-xs-1 center mp07">
										도착지
									</div>
									<div class="col-xs-3 center">
										<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" id="dstnNation" name="dstnNation">
											<option value="">  </option>
											<c:forEach items="${nationList}" var="dstnNationList">
												<option value="${dstnNationList.nationCode}"<c:if test= "${fn:trim(userOrder.dstnNation) eq fn:trim(dstnNationList.nationCode)}">selected</c:if>>${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-xs-1 center mp07 shipperNm">
										택배 회사
									</div>
									<div class="col-xs-3 center">
										<input type="hidden" style="width:100%;" readOnly id="selectTrans" name="selectTrans" value="${userOrder.transCode}">
										<select class="form-control shipperVal" id="transCode" name="transCode">
											<option value=""></option>
											<%-- <c:forEach items="${transComList}" var="transCom">
												<option value="${transCom.transCode }">${transCom.transName}</option>
											</c:forEach> --%>
										</select>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<input type="hidden" name="hawbNo" id="hawbNo" value="${userOrder.hawbNo}" />
								<div id="inner-content-side" >
									<div class="row">
										<div class="col-xs-12 col-lg-12" >
											<div class="col-xs-1 center mp07" style="font-size: 5;">
												<h4>주문정보</h4>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												
												<div class="col-xs-12 col-lg-12" style="font-size: 3;">
													<h4>SHIPPER 정보</h4>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07   shipperNm">이름</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 shipperVal" type="text" name="shipperName" id="shipperName" value="${userOrder.shipperName}" />
														</div>
														<div class="col-xs-1 center mp07  shipperNm">전화번호</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 shipperVal"  type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
														</div>
														<div class="col-xs-1 center mp07 ">휴대전화 번호</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
														</div>
													</div>
													
													<div class="hr dotted"></div>
														<div class="row form-group hr-8">
															<%-- <div class="col-xs-1 center mp07 shipperNm">발송 국가</div>
															<div class="col-xs-3 center">
																<select class="chosen-select form-control tag-input-style width-100 pd-1rem shipperVal" id="shipperCntry" name="shipperCntry">
																	<option value="">  </option>
																	<c:forEach items="${nationList}" var="nation">
																		<option value="${nation.nationCode}" <c:if test="${userOrder.shipperCntry eq nation.nationCode}"> selected </c:if>>${nation.nationEName}(${nation.nationName })</option>
																	</c:forEach>
																</select>
															</div> --%>
															<div class="col-xs-1 center mp07">발송 도시</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12" type="text" name="shipperCity" id="shipperCity" value="${userOrder.shipperCity }" />
															</div>
															
															<div class="col-xs-1 center mp07">발송 State</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12" type="text" name="shipperState" id="shipperState" value="${userOrder.shipperState }"  />
															</div>
													</div>
													
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label class="shipperNm">우편주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 shipperVal"  type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
														</div>
													
														<div class="col-xs-1 center mp07 ">
															<label class=" shipperNm">주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 shipperVal"  type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
														</div>
														
														<div class="col-xs-1 center mp07 ">
															<label>상세주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12"  type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userOrder.shipperAddrDetail}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 shipperNm">
															<label>Email</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 shipperVal" type="text" name="userEmail" id="userEmail" value="${userOrder.userEmail}" />
														</div>
													</div>
												</div>
												
												<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
													<h4 style="margin-top: 20px;">수취 정보</h4>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 cneeNm">이름</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 cneeVal" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName }" />
														</div>
														<div class="col-xs-1 center mp07 cneeNm">전화번호</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 cneeVal" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeTel" id="cneeTel" value="${userOrder.cneeTel}" />
														</div>
														<div class="col-xs-1 center mp07">휴대전화 번호</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeHp" id="cneeHp" value="${userOrder.cneeHp}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
<%-- 														<div class="col-xs-1 center mp07 cneeNm">수취 국가</div>
														<div class="col-xs-3 center">
															<select class="chosen-select form-control tag-input-style width-100 pd-1rem cneeVal" id="cneeCntry" name="cneeCntry">
																<option value="">  </option>
																<c:forEach items="${nationList}" var="nation">
																	<option value="${nation.nationCode}" <c:if test="${nation.nationCode eq userOrder.cneeCntry }"> selected </c:if>>${nation.nationEName}(${nation.nationName })</option>
																</c:forEach>
															</select>
														</div>
 --%>														<div class="col-xs-1 center mp07">수취 도시</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity }" />
														</div>
														
														<div class="col-xs-1 center mp07">수취 State</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 cneeNm">
															<label>우편주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 cneeVal" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
														</div>
													
														<div class="col-xs-1 center mp07 cneeNm">
															<label>주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 cneeVal" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
														</div>
														
														<div class="col-xs-1 center mp07">
															<label>상세주소</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 cneeNm">
															<label>Email</label>
														</div>
														<div class="col-xs-3 center">
															<input  class="col-xs-12 cneeVal" type="text" name="cneeEmail" id="cneeEmail" value="${userOrder.cneeEmail}" />
														</div>
													</div>
												</div>
												
												<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
													<h4 style="margin-top: 20px;">운송장 정보</h4>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 orderNm">
															주문 번호
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 orderVal" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
															<input type="hidden" name="nno" id="nno" value="${userOrder.nno }">
														</div>
														<div class="col-xs-1 center mp07">
															주문 날짜
														</div>
														<div class="col-xs-3 center">
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
														<div class="col-xs-1 center mp07">
															구매 사이트
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12" type="text" name="buySite" id="buySite" value="${userOrder.buySite}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 orderNm">
															Height
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userHeight" id="userHeight" value="${userOrder.userHeight}" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															Length
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userLength" id="userLength" value="${userOrder.userLength}" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															Width
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userWidth" id="userWidth" value="${userOrder.userWidth}" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															길이 단위
														</div>
														<div class="col-xs-2 center">
															<select class="col-xs-12 volumeWeight orderVal" id="dimUnit" name="dimUnit" >
																<option value="CM" <c:if test="${userOrder.dimUnit eq 'CM'}">selected</c:if>>CM</option>
																<option value="IN" <c:if test="${userOrder.dimUnit eq 'IN'}">selected</c:if>>INCH</option>
															</select>
														</div>
													</div>
													
													<div class="row hr-8">
														<div class="col-xs-1 center mp07 orderNm">
															실무게
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 orderVal" floatOnly type="text" name="userWta" id="userWta" value="${userOrder.userWta }" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															부피 무게
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 orderVal" floatOnly type="text" name="userWtc" id="userWtc" value="${userOrder.userWtc }" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															박스 개수
														</div>
														<div class="col-xs-2 center">
															<input class="col-xs-12 orderVal" numberOnly type="text" name="boxCnt" id="boxCnt" value="${userOrder.boxCnt }" />
														</div>
														<div class="col-xs-1 center mp07 orderNm">
															무게 단위
														</div>
														<div class="col-xs-2 center">
															<select id="wtUnit orderVal" name="wtUnit" style="width:100%;">
																<option value="KG" <c:if test="${userOrder.wtUnit eq 'KG'}">selected</c:if>>KG</option>
																<option value="LB" <c:if test="${userOrder.wtUnit eq 'LB'}">selected</c:if>>LB</option>
															</select>
														</div>
													</div>
													
													<div class="hr dotted"></div>
													
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
												<input type="hidden" id="itemInfoCnt" name="itemInfoCnt" value="${fn:length(userOrderItem)}">
												
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
															<div class="panel-body">
																<div class="row  hr-8">
																	<!-- <div class="col-lg-4 col-xs-12">
																		<div class="col-lg-3 col-xs-12 pd-1rem center mp07"" >HS_CODE</div>
																		<div class="col-md-3 col-xs-12">
																			<input class="width-100" type="text" name="hsCode" id="hsCode1" value="" />
																			<input class="width-100" type="hidden" name="subno" id="subno" value="1" />
																		</div>
																	</div> -->
																	
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >국내 택배 회사</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="trkCom" id="trkCom${status.count}" value="${orderItem.trkCom}" />
																			<input class="width-100" type="hidden" name="subNo" id="subNo${status.count}" value="${orderItem.subNo}" />
																		</div>
																	</div>
																
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07" >운송날짜</div>
																		<div class="col-md-9 col-xs-12">
																			<div class="input-group input-group-sm">
																				<span class="input-group-addon">
																					<i class="ace-icon fa fa-calendar"></i>
																				</span>
																				<input type="text" id="trkDate${status.count}" name="trkDate" class="form-control" onclick="javascript:test(this);" value="${orderItem.trkDate}"/>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >송장 번호</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="trkNo" id="trkNo${status.count}" value="${orderItem.trkNo}" />
																			<%-- <div class="input-group input-group-sm">
																				
																				<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
																					<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
																				</span>
																			</div> --%>
																		</div>
																	</div>
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >HS CODE</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="hsCode" id="hsCode${status.count}" value="${orderItem.hsCode}" />
																		</div>
																	</div>
																</div>
																
																<div class="hr dotted"></div>
																
																
																
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >상품 코드</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="cusItemCode" id="cusItemCode${status.count}" value="${orderItem.cusItemCode}" />
																		</div>
																	</div>
																	
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07" >상품 종류</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="itemDiv" id="itemDiv${status.count}" value="${orderItem.itemDiv}" />
																		</div>
																	</div>
																</div>
																
																
																
																
																
																
																
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07" >BRAND</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="brandItem" id="brand${status.count}" value="${orderItem.brand}" />
																		</div>
																	</div>
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >상품명</div>
																		<div class="col-lg-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="itemDetail" id="itemDetail${status.count}" value="${orderItem.itemDetail}" />
																		</div>
																	</div>
																	
																<!-- <div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품재질</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="itemMeterial" id="itemMeterial${status.count}" value="" />
																		</div>
																	</div>
																</div> -->
																</div>
																
																<!-- <div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >USER_WTA</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="userWtaItem" id="userWta${status.count}" value="" />
																		</div>
																	</div>
																</div>
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >USER_WTC</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="userWtcItem" id="userWtc${status.count}" value="" />
																		</div>
																	</div>
																</div> 
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >WT_UNIT</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="wtUnit" id="wtUnit${status.count}" value="" />
																		</div>
																	</div>
																</div>-->
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >상품 개수</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="itemCnt" id="itemCnt${status.count}" value="${orderItem.itemCnt}" />
																		</div>
																	</div>
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm">상품 단위 가격</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="unitValue" id="unitValue${status.count}" value="${orderItem.unitValue}" />
																		</div>
																	</div>
																</div>
																
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07" >상품 단위</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="qtyUnit" id="qtyUnit${status.count}" value="${orderItem.qtyUnit}" />
																		</div>
																	</div>
																</div>
																<div class="hr dotted"></div>
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >제조국</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="makeCntry" id="makeCntry${status.count}" value="${orderItem.makeCntry}" />
																		</div>
																	</div>
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm" >제조 회사</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 itemVal" type="text" name="makeCom" id="makeCom${status.count}" value="${orderItem.makeCom}" />
																		</div>
																	</div>
																</div>
																<div class="hr dotted"></div>
																<div class="row  hr-8">
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07">ITEM_URL</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="itemUrl" id="itemUrl${status.count}" value="${orderItem.itemUrl}" />
																		</div>
																	</div>
																	<div class="col-md-6 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07">ITEM_IMAGE_URL</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="itemImgUrl" id="itemImgUrl${status.count}" value="${orderItem.itemImgUrl}" />
																		</div>
																	</div>
																</div>
																
																<!-- <div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >PACKAGE_UNIT</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="packageUnit" id="packageUnit${status.count}" value="" />
																		</div>
																	</div>
																</div>
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >EXCHANGE_RATE</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="exchangeRate" id="exchangeRate${status.count}" value="" />
																		</div>
																	</div>
																</div>
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >CHG_CURRENCY</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="chgCurrency" id="chgCurrency${status.count}" value="" />
																		</div>
																	</div>
																</div>
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >CHG_AMT</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="chgAmt" id="chgAmt${status.count}" value="" />
																		</div>
																	</div>
																</div>
																
																<div class="row  hr-8">
																	<div class="col-md-4 col-xs-12">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >TAKE_IN_CODE</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100" type="text" name="takeInCode" id="takeInCode${status.count}" value="" />
																		</div>
																	</div>
																</div> -->
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
							</div>
							</form>
						</div>
						</div>
				</div>
			</div><!-- /.main-content -->
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
		<script src="/assets/js/chosen.jquery.min2.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			var cnt = $("#itemInfoCnt").val();
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					$('#dstnNation').trigger("change");
				});
				//select target form change
			})
			
			$(".volumeWeight").on('change',function(e){
				var temp = "";
				if($("#dimUnit").val() == 'CM'){
					temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/6000;
				}else{
					temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/166;
				}
				
				$("#userWtc").val(temp.toFixed(2));
			})

			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });

	        $("input:text[floatOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9.]|\.(?=.*\.)/g,""));
	        });
			
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

			function test(id){
				$(id).datepicker({
					showOtherMonths: true,
					selectOtherMonths: false,
					dateFormat:'yymmdd' , 
					autoclose:true, 
					todayHighlight:true
				});
			}

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
						if(data.transComList.length == 0){
							addHtml = "<option value='ARA'>ARAMEX</option>";
						}else{
							addHtml = "<option value=''>택배회사를 선택해주세요</option>";
							for(var i = 0; i < data.transComList.length; i++){
								addHtml += "<option ";
								if(data.transComList[i].transCode == $("#selectTrans").val()){
									addHtml += "selected ";
								}
								addHtml += "value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transCode+"</option>'"
							}
						}
						$("#transCode").html(addHtml)
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("조회에 실패 하였습니다.");
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
						cnt++;
						$("#accordion").append(data);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("추가에 실패 하였습니다.");
		            }
				})
			});

			$('#rgstrInfo').on('click',function(e){
				var valiChk = 0;
				$(".shipperVal").each(function(e){
					if($(this).val() == ""){
						alert("SHIPPER정보의 "+$($(".shipperNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}
				$(".cneeVal").each(function(e){
					if($(this).val() == ""){
						alert("수취인 정보의 "+$($(".cneeNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
						
					}
				})
				if(valiChk==1){
					return;
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
				$(".itemVal").each(function(e){
					if($(this).val() == ""){
						var itemNum = e/7;
						itemNum = parseInt(itemNum)+1;
						alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}

				$("[name=brandItem]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})
				$("[name=qtyUnit]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})
				
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
							$("[name=brandItem]").each(function(e){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							})
							$("[name=qtyUnit]").each(function(e){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							})
							alert("수정 되었습니다.");
							location.href="/cstmr/apply/inspDlvry?transCode="+$("#transCode").val();
						}else{
							alert("수정중 오류가 발생했습니다. 데이터를 확인해 주세요")
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

			});
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}

		</script>
		<!-- script addon end -->
	</body>
</html>
