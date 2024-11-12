<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>
		<c:if test="${types eq 'shpng'}">
			운송장 등록 리스트 수정
		</c:if>
		<c:if test="${types eq 'insp'}">
			검품 등록 리스트 수정
		</c:if>
	</title>
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
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								신청서 리스트
							</li>
							<li class="active">
								<c:if test="${types eq 'shpng'}">
									<input type="hidden" id="types" name="types" value="${types}">
									운송장 등록 리스트 수정
								</c:if>
								<c:if test="${types eq 'insp'}">
									<input type="hidden" id="types" name="types" value="${types}">	
									검품 등록 리스트 수정
								</c:if>
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								<c:if test="${types eq 'shpng'}">
									운송장 등록 리스트 수정
								</c:if>
								<c:if test="${types eq 'insp'}">
									검품 등록 리스트 수정
								</c:if>
							</h1>
						</div>
							<form name="registForm" id="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div id="search-div">
								<br>
								<div class="row">
									<div class="col-xs-12 col-sm-11 col-md-10">
										<span class="input-group" style="display: inline-flex; width:100%">
											<select style="height: 34px">
												<option>USER ID</option>
											</select>
											<input type="text" class="form-control" name="keywords" placeholder="${searchKeyword}" style="width:100%; max-width: 300px">
												<button id="srchKeyword" class="btn btn-default no-border btn-sm">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
										</span>
									</div>
								</div>
								<br>
								<div id="delete-user" style="text-align: right">
									<button id="delBtn" class="btn btn-sm btn-danger">
									 	삭제하기
									</button>
								</div>
							</div>
							<br>
							<div class="main-content">
								<div class="main-content-inner">
											<div class="page-content">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
												<div id="inner-content-side" >
													<div class="row">
														<div class="col-xs-12 col-lg-12" >
															<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
																<h4>${userOrder.hawbNo} 주문정보 (USER ID : ${userOrder.userId })</h4>
																<input type="hidden" id="userId" name="userId" value="${userOrder.userId }">
															</div>
															<!-- style="border:1px solid red" -->
															<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
																
																<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
																	<h4>SHIPPER 정보</h4>
																	<input type="hidden" name="hawbNo" id="hawbNo" value="${userOrder.hawbNo}" />
																	<input type="hidden" name="nno" id="nno" value="${userOrder.nno}" />
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name shipperNm">이름</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperName" id="shipperName" value="${userOrder.shipperName}" />
																		</div>
																		<div class="profile-info-name shipperNm">전화번호</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
																		</div>
																		<div class="profile-info-name">휴대전화 번호</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12" type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
																		</div>
																	</div>
																	
																	<%-- <div class="profile-user-info  form-group hr-8">
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
																	</div> --%>
																	
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name shipperNm">
																			<label>우편주소</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
																		</div>
																	
																		<div class="profile-info-name shipperNm">
																			<label>주소</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
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
																	<h4>수취인 정보</h4>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name cneeNm">이름 (Eng)</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 cneeVal" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName}" />
																		</div>
																		<div class="profile-info-name cneeNm">이름 (Jpn)</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 cneeVal" type="text" name="nativeCneeName" id="nativeCneeName" value="${userOrder.nativeCneeName}" />
																		</div>
																	</div>
																	
																	<%-- <div class="profile-user-info  form-group hr-8">
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
																	</div> --%>
																	
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name cneeNm">전화번호</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 cneeVal" type="text" name="cneeTel" id="cneeTel" value="${userOrder.cneeTel}" />
																		</div>
																		<div class="profile-info-name ">휴대전화 번호</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 " type="text" name="cneeHp" id="cneeHp" value="${userOrder.cneeHp}" />
																		</div>
																		<div class="profile-info-name cneeNm">
																			<label>우편주소</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 cneeVal" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
																		</div>
																	</div>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name">
																			<label>주소 (Eng)</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
																		</div>
																		
																		<div class="profile-info-name">
																			<label>상세주소 (Eng)</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
																		</div>
																	</div>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name cneeNm">
																			<label>주소 (Jpn)</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 cneeVal" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="${userOrder.nativeCneeAddr}" />
																		</div>
																		
																		<div class="profile-info-name">
																			<label>상세주소 (Jpn)</label>
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="${userOrder.nativeCneeAddrDetail}" />
																		</div>
																	</div>
																</div>
																<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
																	<h4>운송장 정보</h4>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name orderNm">
																			주문 번호
																		</div>
																		<div class="profile-info-value">
																			<input class="col-lg-12 orderVal" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
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
																		<div class="profile-info-name orderNm">
																			CURRENCY
																		</div>
																		<div class="profile-info-value" style="vertical-align: middle;">
																			<select name="chgCurrency" id="chgCurrency${status.count}">
																				<option 
																					<c:if test = "${userOrderItem[0].chgCurrency eq 'USD'}">
																						selected
																					</c:if> 
																				value="USD">USD</option>
																				<option 
																					<c:if test = "${userOrderItem[0].chgCurrency eq 'GBP'}">
																						selected
																					</c:if>
																				value="GBP">GBP</option>
																			</select>
																			<%-- <input class="col-xs-12 orderVal" type="text" name="chgCurrency" id="chgCurrency" value="${userOrderItem[0].chgCurrency}" /> --%>
																		</div>
																	</div>
																	
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name orderNm">
																			Height
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text" name="userHeight" id="userHeight" value="${userOrder.userHeight}" />
																		</div>
																		<div class="profile-info-name orderNm">
																			Length
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text" name="userLength" id="userLength" value="${userOrder.userLength}" />
																		</div>
																		<div class="profile-info-name orderNm">
																			Width
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text"  name="userWidth" id="userWidth" value="${userOrder.userWidth}" />
																		</div>
																	</div>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name orderNm">
																			무게
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal" floatOnly type="text" name="userWta" id="userWta" value="${userOrder.userWta}" />
																		</div>
																		<div class="profile-info-name orderNm">
																			부피 무게
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal" floatOnly type="text" name="userWtc" id="userWtc" value="${userOrder.userWtc}" />
																		</div>
																		<div class="profile-info-name orderNm">
																			박스 개수
																		</div>
																		<div class="profile-info-value">
																			<input class="col-xs-12 orderVal" numberOnly type="text" name="boxCnt" id="boxCnt" value="${userOrder.boxCnt}" />
																		</div>
																	</div>
																	<div class="profile-user-info  form-group hr-8">
																		<div class="profile-info-name orderNm">
																			출발지
																		</div>
																		<div class="profile-info-value">
																			<select class="form-control nations orderVal" id="orgStation" name="orgStation">
																				<option value="">  </option>
																				<c:forEach items="${userOrgStation}" var="orgStationList">
																					<option <c:if test="${userOrder.orgStation eq orgStationList.stationCode}"> selected </c:if>value="${orgStationList.stationCode}">${orgStationList.stationName}</option>
																				</c:forEach>
																			</select>
																		</div>
																		<div class="profile-info-name orderNm">
																			도착지
																		</div>
																		<div class="profile-info-value">
																			<select class="form-control nations orderVal" id="dstnNation" name="dstnNation">
																				<option value="">  </option>
																				<c:forEach items="${userDstnNation}" var="dstnNationList">
																					<option <c:if test="${userOrder.dstnNation eq dstnNationList}"> selected </c:if>value="${dstnNationList}">${dstnNationList}</option>
																				</c:forEach>
																			</select>
																		</div>
																		<div class="profile-info-name orderNm">
																			택배 회사
																			<input type="hidden" id="selectTrans" name="selectTrans" value="${userOrder.transCode}">
																		</div>
																		<div class="profile-info-value">
																			<select class="form-control orderVal" id="transCode" name="transCode">
																				<option value="">  </option>
																			</select>
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
																<input type="hidden" id="cnt" name="cnt" value="${fn:length(userOrderItem)}">
																
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
																						<div class="col-lg-3 col-xs-12 pd-1rem" >HS_CODE</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="hsCode" id="hsCode${status.count}" value="${orderItem.hsCode}" />
																							<input class="width-100" type="hidden" name="subNo" id="subNo" value="${orderItem.subNo}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem" >상품 종류</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="itemDiv" id="itemDiv${status.count}" value="${orderItem.itemDiv}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품명</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemVal" type="text" name="itemDetail" id="itemDetail${status.count}" value="${orderItem.itemDetail}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >Q10 CODE</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemVal" type="text" name="cusItemCode" id="cusItemCode${status.count}" value="${orderItem.cusItemCode}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품재질</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemVal" type="text" name="itemMeterial" id="itemMeterial${status.count}" value="${orderItem.itemMeterial}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="brandItem" id="brand${status.count}" value="${orderItem.brand}" />
																						</div>
																					</div>
																				</div>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품 개수</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemVal" numberOnly type="text" name="itemCnt" id="itemCnt${status.count}" value="${orderItem.itemCnt}" />
																						</div>
																					</div>
																				</div>
																				
																				<%-- <div class="row form-group hr-8">
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
																				</div> --%>
																				<%-- <div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem" >WT_UNIT</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="wtUnit" id="wtUnit${status.count}" value="${orderItem.wtUnit}" />
																						</div>
																					</div>
																				</div> --%>
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >단가</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemNmVal" floatOnly type="text" name="unitValue" id="unitValue${status.count}" value="${orderItem.unitValue}" />
																						</div>
																					</div>
																				</div>
																				
																				<%-- <div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem" >QTY_UNIT</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="qtyUnit" id="qtyUnit${status.count}" value="${orderItem.qtyUnit}" />
																						</div>
																					</div>
																				</div> --%>
																				
																				<div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >제조국가</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100 itemVal" type="text" name="makeCntry" id="makeCntry${status.count}" value="${orderItem.makeCntry}" />
																						</div>
																					</div>
																				</div>
																				<%-- <div class="row form-group hr-8">
																					<div class="col-lg-12 col-xs-12">
																						<div class="col-lg-3 col-xs-12 pd-1rem" >MAKE_COM</div>
																						<div class="col-lg-6 col-xs-12">
																							<input class="width-100" type="text" name="makeCom" id="makeCom${status.count}" value="${orderItem.makeCom}" />
																						</div>
																					</div>
																				</div> --%>
																				
																				
																				
																				<%-- <div class="row form-group hr-8">
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
																				</div> --%>
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
																		<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">등록</button>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
								</div>
							</div><!-- /.main-content -->
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
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					if($("#types").val()=="shpng"){
						$("#4thMenu").toggleClass('open');
						$("#4thMenu").toggleClass('active'); 
						$("#4thFiv").toggleClass('active');
					}else if($("#types").val()=="insp"){
						$("#12thMenu").toggleClass('open');
						$("#12thMenu").toggleClass('active'); 
						$("#12thThr").toggleClass('active');
					}
					$('#dstnNation').trigger("change");
				});
			});

			$(".volumeWeight").on('change',function(e){
				var temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/6000;
				$("#userWtc").val(temp.toFixed(2));
			})
			
			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });
	
	        $("input:text[floatOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9.]/g,""));
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
			

			$('#backToList').on('click', function(e){
				history.back();


			});

			$(".nations").on('change',function(e){

				var param1 = $("#orgStation").val();
				var param2 = $("#dstnNation").val();
				var param3 = $("#userId").val()

				$.ajax({
					url:'/cstmr/apply/transComList',
					type: 'POST',
					datatype: "json",
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: { param1 : param1,
							param2 : param2,
							param3 : param3
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
		                alert("조회에 실패 하였습니다.");
		            }
				})
				

			})

			$('#addBtns').on('click',function(e){
				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/mngr/aplctList/itemList',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						$("#accordion").append(data);
						$("#cnt").val($("#cnt").val()*1+1);
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
				
				$("[name=hsCode]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})
				
				$("[name=itemDiv]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})
				
				$("[name=itemImgUrl]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})
				$("[name=itemUrl]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})

				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/mngr/aplctList/modifyAdminOne',
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
							$("[name=hsCode]").each(function(e){
								if($(this).val()== "d#none#b"){
									$(this).val("");
								}
							})
							
							$("[name=itemDiv]").each(function(e){
								if($(this).val()== "d#none#b"){
									$(this).val("");
								}
							})
							
							$("[name=itemImgUrl]").each(function(e){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							})
							$("[name=itemUrl]").each(function(e){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							})
							alert("수정 되었습니다.");
							location.href="/mngr/aplctList/shpngList";
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
			});
			
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
			});
			
			$('#modalSave').on('click',function(e){
				$('#hawbNo').val($("#form-field-tags").val());
			});
			
			</script>
		<!-- script addon end -->
	</body>
</html>
