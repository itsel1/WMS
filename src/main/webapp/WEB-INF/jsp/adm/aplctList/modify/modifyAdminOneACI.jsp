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
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
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
															<div class="row hr-8">
																<div class="col-xs-1 center mp07">
																	출발지
																</div>
																<div class="col-xs-3 center">
																	<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" disabled id="orgStation" name="orgStation">
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
																	<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" disabled  id="dstnNation" name="dstnNation">
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
																	<select class="form-control shipperVal" disabled  id="transCode" name="transCode">
																		<option value=""></option>
																		<%-- <c:forEach items="${transComList}" var="transCom">
																			<option value="${transCom.transCode }">${transCom.transName}</option>
																		</c:forEach> --%>
																	</select>
																</div>
															</div>
															<!-- style="border:1px solid red" -->
															<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
																<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
																	<h4 >발송 정보</h4>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07 shipperNm">이름</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperName" id="shipperName" value="${userOrder.shipperName}" />
																		</div>
																		<div class="col-xs-1 center mp07 shipperNm">전화번호</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 shipperVal" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
																		</div>
																		<div class="col-xs-1 center mp07">휴대전화 번호</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
																		</div>
																	</div>
																	
																	<div class="hr dotted"></div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07 shipperNm">
																			<label>우편주소</label>
																		</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
																		</div>
																	
																		<div class="col-xs-1 center mp07 shipperNm">
																			<label>주소</label>
																		</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 shipperVal" type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
																		</div>
																		
																		<div class="col-xs-1 center mp07">
																			<label>상세주소</label>
																		</div>
																		<div class="col-xs-3 center ">
																			<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userOrder.shipperAddrDetail}" />
																		</div>
																	</div>
																</div>
																<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
																	<h4 style="margin-top: 20px;">수취 정보</h4>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07 cneeNm">이름 (Eng)</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 cneeVal" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName}" />
																		</div>
																		<div class="col-xs-1 center mp07">이름 (Jpn)</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12" type="text" name="nativeCneeName" id="nativeCneeName" value="${userOrder.nativeCneeName}" />
																		</div>
																	</div>
																	<div class="row hr-8">
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
																		<div class="col-xs-1 center mp07 cneeNm">
																			<label>우편주소</label>
																		</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12 cneeVal" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
																		</div>
																	</div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07">
																			<label>주소 (Eng)</label>
																		</div>
																		<div class="col-xs-4 center">
																			<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
																		</div>
																		
																		<div class="col-xs-1 center mp07">
																			<label>상세주소 (Eng)</label>
																		</div>
																		<div class="col-xs-4 center">
																			<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
																		</div>
																	</div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07 cneeNm">
																			<label>주소 (Jpn)</label>
																		</div>
																		<div class="col-xs-4 center">
																			<input class="col-xs-12 cneeVal" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="${userOrder.nativeCneeAddr}" />
																		</div>
																		
																		<div class="col-xs-1 center mp07">
																			<label>상세주소 (Jpn)</label>
																		</div>
																		<div class="col-xs-4 center">
																			<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="${userOrder.nativeCneeAddrDetail}" />
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
																			<input class="col-lg-12 orderVal" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
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
																		<div class="col-xs-1 center orderNm">
																			CURRENCY
																		</div>
																		<div class="col-xs-3 center" style="vertical-align: middle;">
																			<select class="orderVal" name="chgCurrency" id="chgCurrency${status.count}" style="width:100%;">
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
																			<!-- <input class="col-xs-12 orderVal" type="text" name="chgCurrency" id="chgCurrency" value="" /> -->
																		</div>
																		<!-- <div class="col-xs-1 center mp07">
																			구매 사이트
																		</div>
																		<div class="col-xs-3 center">
																			<input class="col-xs-12" type="text" name="buySite" id="buySite" value="http://" />
																		</div> -->
																	</div>
																	<div class="hr dotted"></div>
																	<div class="row hr-8">
																		<div class="col-xs-1 center mp07 orderNm">
																			Height
																		</div>
																		<div class="col-xs-2 center">
																			<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userHeight" id="userHeight" value="${userOrder.userHeight }" />
																		</div>
																		<div class="col-xs-1 center mp07 orderNm">
																			Length
																		</div>
																		<div class="col-xs-2 center">
																			<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userLength" id="userLength" value="${userOrder.userLength }" />
																		</div>
																		<div class="col-xs-1 center mp07 orderNm">
																			Width
																		</div>
																		<div class="col-xs-2 center">
																			<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userWidth" id="userWidth" value="${userOrder.userWidth }" />
																		</div>
																		<div class="col-xs-1 center mp07 orderNm">
																			길이 단위
																		</div>
																		<div class="col-xs-2 center">
																			<select class="volumeWeight orderVal" id="dimUnit" name="dimUnit" style="width:100%;">
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
							
																		<div class="panel-collapse collapse multi-collapse in" id="collapse${status.count}">
																				<div class="panel-body">
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품명</div>
																							<div class="col-lg-6 col-xs-12">
																								<input class="width-100 itemVal"  type="text" name="itemDetail" id="itemDetail${status.count}" value="${orderItem.itemDetail}" />
																								<input class="width-100" type="hidden" name="subNo" id="subNo${status.count}" value="${orderItem.subNo}" />
																							</div>
																						</div>
																					</div>
																					
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >Q10 CODE</div>
																							<div class="col-lg-6 col-xs-12">
																								<input class="width-100 itemVal"  type="text" name="cusItemCode" id="cusItemCode${status.count}" value="${orderItem.cusItemCode}" />
																							</div>
																						</div>
																					</div>
																					
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품재질</div>
																							<div class="col-lg-6 col-xs-12">
																								<input class="width-100 itemVal"  type="text" name="itemMeterial" id="itemMeterial${status.count}" value="${orderItem.itemMeterial}" />
																							</div>
																						</div>
																					</div>
																					
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
																							<div class="col-lg-6 col-xs-12">
																								<input class="width-100"  type="text" name="brandItem" id="brand${status.count}" value="${orderItem.brand}" />
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
																					
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >단가</div>
																							<div class="col-lg-6 col-xs-12">
																								<input class="width-100 itemVal" floatOnly  type="text" name="unitValue" id="unitValue${status.count}" value="${orderItem.unitValue}" />
																							</div>
																						</div>
																					</div>
																					
																					<div class="row form-group hr-8">
																						<div class="col-lg-12 col-xs-12">
																							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >MAKE_CNTRY</div>
																							<div class="col-lg-6 col-xs-12">
																								<select class="chosen-select form-control tag-input-style width-100 pd-1rem itemVal" id="makeCntry1" name="makeCntry" data-placeholder="국가를 선택해 주세요">
																									<option value="">  </option>
																									<c:forEach items="${nationList}" var="nation">
																										<option value="${nation.nationCode}" <c:if test="${nation.nationCode eq orderItem.makeCntry}">selected</c:if>>${nation.nationEName}(${nation.nationName })</option>
																									</c:forEach>
																								</select>
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
		<script src="/assets/js/chosen.jquery.min2.js"></script>
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
						if(data.transComList.length == 0){
							addHtml = "<option value='ACI'>ACI</option>";
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
