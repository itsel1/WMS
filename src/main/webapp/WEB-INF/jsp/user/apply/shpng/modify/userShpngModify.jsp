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
	  .orderNm {
	  	color: #DD5A43;
	  }
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
	</head> 
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	<title>일반배송 신청서 수정</title>
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
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">일반배송 신청서 수정</strong></div>
							</div>
						</div>
					</div>
						<div class="container">
						<form id="registForm" name="registForm">
							<div class="page-content">
								<div class="page-header">
									<h3>
										일반배송 신청서 수정
									</h3>
								</div>
								<div class="row hr-8">
									<input type="hidden" id="urlType" name="urlType" value="${urlType}">
									<div class="col-xs-1 center mp07">
										출발지
									</div>
									<div class="col-xs-2 center">
										<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" disabled id="orgStation" name="orgStation">
											<option value="">  </option>
											<c:forEach items="${userOrgStation}" var="orgStationList">
												<option value="${orgStationList.stationCode}"<c:if test= "${fn:trim(userOrder.orgStation) eq fn:trim(orgStationList.stationCode)}">selected</c:if>>${orgStationList.stationName}</option>
											</c:forEach>
										</select>
									</div>
									<input type="hidden" style="width:100%;" id="orgStation" name="orgStation" value="${userOrder.orgStation}">
									<div class="col-xs-1 center mp07">
										도착지
									</div>
									<div class="col-xs-2 center">
										<select class="chosen-select form-control tag-input-style width-100 pd-1rem nations" disabled id="dstnNation" name="dstnNation">
											<option value="">  </option>
											<c:forEach items="${nationList}" var="dstnNationList">
												<option value="${dstnNationList.nationCode}"<c:if test= "${fn:trim(userOrder.dstnNation) eq fn:trim(dstnNationList.nationCode)}">selected</c:if>>${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
											</c:forEach>
										</select>
									</div>
									<input type="hidden" style="width:100%;" id="dstnNation" name="dstnNation" value="${userOrder.dstnNation}">
									<div class="col-xs-1 center mp07">
										택배 회사
									</div>
									<div class="col-xs-2 center">
										<input type="hidden" style="width:100%;" id="transCode" name="transCode" value="${userOrder.transCode}">
										<select class="form-control" id="transCodeList" name="transCodeList">
											<option value=""></option>
										</select>
									</div>
									<div class="col-xs-1 center mp07">
										관세 지급 방식
									</div>
									<div class="col-xs-2 center">
										<select class="form-control" disabled >
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
									<input type="hidden" style="width:100%;" id="payment" name="payment" value="${userOrder.payment}">
								</div>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<input type="hidden" name="hawbNo" id="hawbNo" value="${userOrder.hawbNo}" />
								<input type="hidden" name="nno" id="nno" value="${userOrder.nno }"/>
								<input type="hidden" name="userId" id="userId" value="${userId}"/>
								<div id="inner-content-side" >
									<div class="row">
										<div class="col-xs-12 col-lg-12" >
											<div class="col-xs-12 col-lg-12 " style="font-size: 5;">
												<h2>주문정보</h2>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
													<h4 >발송 정보</h4>
													<c:if test="${(!empty expressOrderVO.shipperNameYnExpress) or (!empty expressOrderVO.shipperTelYnExpress) or (!empty expressOrderVO.shipperHpYnExpress)}"> 
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.shipperNameYnExpress}"> 
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperNameYn}"> shipperNm </c:if>">이름</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperNameYn}"> shipperVal </c:if>" type="text" name="shipperName" id="shipperName" value="${userOrder.shipperName}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperTelYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperTelYn}"> shipperNm </c:if>">전화번호</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperTelYn}"> shipperVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userOrder.shipperTel}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperHpYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperHpYn}"> shipperNm </c:if>">휴대전화 번호</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperHpYn}"> shipperVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="${userOrder.shipperHp}" />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.shipperCntryYnExpress) or (!empty expressOrderVO.shipperCityYnExpress) or (!empty expressOrderVO.shipperStateYnExpress)}">
														<div class="row form-group hr-8">
														<c:if test="${!empty expressOrderVO.shipperCntryYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperCntryYn}"> shipperNm </c:if>">발송 국가</div>
															<div class="col-xs-3 center">
																<select class="chosen-select form-control chosen-select-one tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.shipperCntryYn}"> shipperVal </c:if> " id="shipperCntry" name="shipperCntry">
																	<option value="">  </option>
																	<c:forEach items="${nationList}" var="nation">
																		<option value="${nation.nationCode}" <c:if test="${userOrder.shipperCntry eq nation.nationCode}"> selected </c:if>>${nation.nationEName}(${nation.nationName })</option>
																	</c:forEach>
																</select>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperCityYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperCityYn}"> shipperNm </c:if>">발송 도시</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperCityYn}"> shipperVal </c:if>" type="text" name="shipperCity" id="shipperCity" value="${userOrder.shipperCity }" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperStateYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperStateYn}"> shipperNm </c:if>">발송 State</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperStateYn}"> shipperVal </c:if>" type="text" name="shipperState" id="shipperState" value="${userOrder.shipperState }"  />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.shipperZipYnExpress) or (!empty expressOrderVO.shipperAddrYnExpress) or (!empty expressOrderVO.shipperAddrDetailYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.shipperZipYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperZipYn}"> shipperNm </c:if>">
																<label>우편주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperZipYn}"> shipperVal </c:if>" type="text" name="shipperZip" id="shipperZip" value="${userOrder.shipperZip}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperAddrYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperAddrYn}"> shipperNm </c:if>">
																<label>주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperAddrYn}"> shipperVal </c:if>" type="text" name="shipperAddr" id="shipperAddr" value="${userOrder.shipperAddr}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperAddrDetailYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperAddrDetailYn}"> shipperNm </c:if>">
																<label>상세주소</label>
															</div>
															<div class="col-xs-3 center ">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperAddrDetailYn}"> shipperVal </c:if>" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userOrder.shipperAddrDetail}" />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.userEmailYnExpress) or (!empty expressOrderVO.whReqMsgYnExpress) or (!empty expressOrderVO.shipperReferenceYnExpress)}">
														<div class="row hr-8">
															<c:if test="${!empty expressOrderVO.userEmailYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userEmailYn}"> shipperNm </c:if>">
																	<label>Email</label>
																</div>
																<div class="col-xs-3">
																	<input class="col-xs-12 <c:if test="${!empty optionOrderVO.userEmailYn}"> shipperVal </c:if>" type="text" name="userEmail" id="userEmail" value="${userInfo.userEmail}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.shipperReferenceYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperReferenceYn}"> shipperNm </c:if>">
																	<label>Reference</label>
																</div>
																<div class="col-xs-3">
																	<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperReferenceYn}"> shipperVal </c:if>" type="text" name="shipperReference" id="shipperReference" value="${userOrder.shipperReference}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.whReqMsgYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.whReqMsgYn}"> shipperNm </c:if>">
																	<label>입고 메모</label>
																</div>
																<div class="col-xs-3">
																	<input class="col-xs-12 <c:if test="${!empty optionOrderVO.whReqMsgYn}"> shipperVal </c:if>" type="text" name="whReqMsg" id="whReqMsg" value="${userOrder.whReqMsg}"  />
																</div>
															</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.shipperTaxNoYnExpress) or (!empty expressOrderVO.shipperTaxTypeYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.shipperTaxTypeYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperTaxTypeYn}"> shipperNm </c:if>">
																<label>세금식별 코드</label>
															</div>
															<div class="col-xs-3">
																<select class="chosen-select-one form-control tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.shipperTaxTypeYn}"> shipperVal </c:if>" id="shipperTaxType" name="shipperTaxType">
																	<c:forEach items="${taxTypeList}" var="taxTypeList">
																		<option value="${taxTypeList.code}"  <c:if test="${taxTypeList.code eq userOrder.shipperTaxType}"> selected</c:if>>${taxTypeList.name}</option>
																	</c:forEach>
																</select>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.shipperTaxNoYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperTaxNoYn}"> shipperNm </c:if>">
																<label>세금식별 번호</label>
															</div>
															<div class="col-xs-3">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperTaxNoYn}"> shipperVal </c:if>" type="text" name="shipperTaxNo" id="shipperTaxNo" value="${userOrder.shipperTaxNo}" />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>

												</div>
												<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
													<h4 style="margin-top: 20px;">수취 정보</h4>
													<c:if test="${(!empty expressOrderVO.cneeNameYnExpress) or (!empty expressOrderVO.cneeTelYnExpress) or (!empty expressOrderVO.cneeHpYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.cneeNameYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeNameYn}"> cneeNm </c:if>">이름</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeNameYn}"> cneeVal </c:if>" type="text" name="cneeName" id="cneeName" value="${userOrder.cneeName }" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cneeTelYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeTelYn}"> cneeNm </c:if>">전화번호</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeTelYn}"> cneeVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeTel" id="cneeTel" value="${userOrder.cneeTel}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cneeHpYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeHpYn}"> cneeNm </c:if>">휴대전화 번호</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeHpYn}"> cneeVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeHp" id="cneeHp" value="${userOrder.cneeHp}" />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													<c:if test="${(!empty expressOrderVO.cneeWardYnExpress) or (!empty expressOrderVO.cneeCityYnExpress) or (!empty expressOrderVO.cneeStateYnExpress) or(!empty expressOrderVO.cneeDistrictYnExpress)}">
														<div class="row hr-8">
														<%-- 
														<c:if test="${!empty expressOrderVO.cneeCntryYnExpress}"> 
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeCntryYn}"> cneeNm </c:if>">수취 국가</div>
															<div class="col-xs-2 center">
																<select class="chosen-select form-control tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.cneeCntryYn}"> cneeVal </c:if>" id="cneeCntry" name="cneeCntry">
																	<option value="">  </option>
																	<c:forEach items="${nationList}" var="nation">
																		<option value="${nation.nationCode}" <c:if test="${nation.nationCode eq userOrder.cneeCntry }"> selected </c:if>>${nation.nationEName}(${nation.nationName })</option>
																	</c:forEach>
																</select>
															</div>
														</c:if>
														 --%>
														<c:if test="${!empty expressOrderVO.cneeStateYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeStateYn}"> cneeNm </c:if>">주 (state)</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeStateYn}"> cneeVal </c:if>" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
															</div>
														</c:if>
														
														<c:if test="${!empty expressOrderVO.cneeCityYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeCityYn}"> cneeNm </c:if>">도시 (city/province)</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeCityYn}"> cneeVal </c:if>" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity }" />
															</div>
														</c:if>
														
														<c:if test="${!empty expressOrderVO.cneeDistrictYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeDistrictYn}"> cneeNm </c:if>">구역 (district)</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeDistrictYn}"> cneeVal </c:if>" type="text" name="cneeDistrict" id="cneeDistrict" value="${userOrder.cneeDistrict}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cneeWardYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeWardYn}"> cneeNm </c:if>">동 (ward)</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeWardYn}"> cneeVal </c:if>" type="text" name="cneeWard" id="cneeWard" value="${userOrder.cneeWard}" />
															</div>
														</c:if>
														</div>
													<div class="hr dotted"></div>
													</c:if>
													<c:if test="${(!empty expressOrderVO.cneeZipYnExpress) or (!empty expressOrderVO.cneeAddrYnExpress) or (!empty expressOrderVO.cneeAddrDetailYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.cneeZipYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeZipYn}"> cneeNm </c:if>">
																<label>우편주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeZipYn}"> cneeVal </c:if>" type="text" name="cneeZip" id="cneeZip" value="${userOrder.cneeZip}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cneeAddrYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeAddrYn}"> cneeNm </c:if>">
																<label>주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeAddrYn}"> cneeVal </c:if>" type="text" name="cneeAddr" id="cneeAddr" value="${userOrder.cneeAddr}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cneeAddrDetailYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeAddrDetailYn}"> cneeNm </c:if>">
																<label>상세주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeAddrDetailYn}"> cneeVal </c:if>" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${userOrder.cneeAddrDetail}" />
															</div>
														</c:if>
														</div>
													<div class="hr dotted"></div>
													</c:if>
													<c:if test="${(!empty expressOrderVO.nativeCneeNameYnExpress) or (!empty expressOrderVO.nativeCneeAddrYnExpress) or (!empty expressOrderVO.nativeCneeAddrDetailYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.nativeCneeNameYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeNameYn}"> cneeNm </c:if>">
																<label>현지 이름</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeNameYn}"> cneeVal </c:if>" type="text" name="nativeCneeName" id="nativeCneeName" value="${userOrder.nativeCneeName}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.nativeCneeAddrYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeAddrYn}"> cneeNm </c:if>">
																<label>현지 주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeAddrYn}"> cneeVal </c:if>" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="${userOrder.nativeCneeAddr}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.nativeCneeAddrDetailYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeAddrDetailYn}"> cneeNm </c:if>">
																<label>현지 상세주소</label>
															</div>
															<div class="col-xs-3 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeAddrDetailYn}"> cneeVal </c:if>" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="${userOrder.nativeCneeAddrDetail}" />
															</div>
														</c:if>
														</div>
													<div class="hr dotted"></div>
													</c:if>
													<c:if test="${(!empty expressOrderVO.cneeEmailYnExpress) or (!empty expressOrderVO.customsNoYnExpress)}">
														<div class="row hr-8">
															<c:if test="${!empty expressOrderVO.cneeEmailYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeEmailYn}"> cneeNm </c:if>">
																	<label>Email</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeEmailYn}"> cneeVal </c:if>" type="text" name="cneeEmail" id="cneeEmail" value="${userOrder.cneeEmail}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.customsNoYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.customsNoYn}"> cneeNm </c:if>">
																	<label>
																	<c:if test="${userOrder.dstnNation eq  'KR'}">
																		개인통관부호
																	</c:if>
																	<c:if test="${userOrder.dstnNation ne  'KR'}">
																		수취인 ID 번호
																	</c:if>
																	</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.customsNoYn}"> cneeVal </c:if>" type="text" name="customsNo" id="customsNo" value="${userOrder.customsNo}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.cneeBirthYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeBirthYn}"> cneeNm </c:if>">
																	<label>수취인 생년월일 (day/month/year)</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeBirthYn}"> cneeVal </c:if>" type="text" name="cneeBirth" id="cneeBirth" value="${userOrder.cneeBirth}" />
																</div>
															</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.nationalIdDateYnExpress) or (!empty expressOrderVO.nationalIdAuthorityYnExpress) or (!empty expressOrderVO.taxNoYnExpress)}">
														<div class="row hr-8">
															<c:if test="${!empty expressOrderVO.nationalIdDateYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nationalIdDateYn}"> cneeNm </c:if>">
																	<label>수취인 ID번호 발급일 (day/month/year)</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.nationalIdDateYn}"> cneeVal </c:if>" type="text" name="nationalIdDate" id="nationalIdDate" value="${userOrder.nationalIdDate}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.nationalIdAuthorityYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nationalIdAuthorityYn}"> cneeNm </c:if>">
																	<label>수취인 ID번호 발급기관 이름/주소</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.nationalIdAuthorityYn}"> cneeVal </c:if>" type="text" name="nationalIdAuthority" id="nationalIdAuthority" value="${userOrder.nationalIdAuthority}" />
																</div>
															</c:if>
															<%-- <c:if test="${!empty expressOrderVO.taxNoYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.taxNoYn}"> cneeNm </c:if>">
																	<label>수취인 TAX 번호</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.taxNoYn}"> cneeVal </c:if>" type="text" name="taxNo" id="taxNo" value="${userOrder.taxNo}" />
																</div>
															</c:if> --%>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.dlvReqMsgYnExpress) or (!empty expressOrderVO.cneeReference1YnExpress) or (!empty expressOrderVO.cneeReference2YnExpress)}">
														<div class="row hr-8">
															<c:if test="${!empty expressOrderVO.cneeReference1YnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeReference1Yn}"> cneeNm </c:if>">
																	<label>Reference1</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeReference1Yn}"> cneeVal </c:if>" type="text" name="cneeReference1" id="cneeReference1" value="${userOrder.cneeReference1}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.cneeReference2YnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeReference2Yn}"> cneeNm </c:if>">
																	<label>Reference2</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeReference2Yn}"> cneeVal </c:if>" type="text" name="cneeReference2" id="cneeReference2" value="${userOrder.cneeReference2}" />
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.dlvReqMsgYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.dlvReqMsgYn}"> cneeNm </c:if>">
																	<label>수취인 요구사항</label>
																</div>
																<div class="col-xs-3 center">
																	<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.dlvReqMsgYn}"> cneeVal </c:if>" type="text" name="dlvReqMsg" id="dlvReqMsg" value="${userOrder.dlvReqMsg}" />
																</div>
															</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.cneeTaxTypeYnExpress) or (!empty expressOrderVO.cneeTaxNoYnExpress)}">
														<div class="row hr-8">
															<c:if test="${!empty expressOrderVO.cneeTaxTypeYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeTaxTypeYn}"> cneeNm </c:if>">
																	<label>세금식별 코드</label>
																</div>
																<div class="col-xs-3">
																	<select class="chosen-select-one form-control tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.cneeTaxTypeYn}"> cneeVal </c:if>" id="cneeTaxType" name="cneeTaxType">
																		<c:forEach items="${taxTypeList}" var="taxTypeList">
																			<option value="${taxTypeList.code}"  <c:if test="${taxTypeList.code eq userOrder.cneeTaxType}"> selected</c:if>>${taxTypeList.name}</option>
																		</c:forEach>
																	</select>
																</div>
															</c:if>
															<c:if test="${!empty expressOrderVO.cneeTaxNoYnExpress}">
																<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeTaxNoYn}"> cneeNm </c:if>">
																	<label>세금식별 번호</label>
																</div>
																<div class="col-xs-3">
																	<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeTaxNoYn}"> cneeVal </c:if>" type="text" name="cneeTaxNo" id="cneeTaxNo" value="${userOrder.cneeTaxNo}" />
																</div>
															</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>

												</div>
												
												<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
													<h4 style="margin-top: 20px;">운송장 정보</h4>
													<c:if test="${(!empty expressOrderVO.orderNoYnExpress) or (!empty expressOrderVO.orderDateYnExpress) or (!empty expressOrderVO.buySiteYnExpress) or (!empty expressOrderVO.getBuyYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.orderNoYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.orderNoYn}"> orderNm </c:if>">
																주문 번호
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.orderNoYn}"> orderVal </c:if>" type="text" name="orderNo" id="orderNo" value="${userOrder.orderNo}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.orderDateYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.orderDateYn}"> orderNm </c:if>">
																주문 날짜
															</div>
															<div class="col-xs-2 center">
																<div class="col-lg-12">
																	<div class="input-group input-group-sm">
																		<span class="input-group-addon">
																			<i class="ace-icon fa fa-calendar"></i>
																		</span>
																		<input type="text" id="orderDate" name="orderDate" class="form-control <c:if test="${!empty optionOrderVO.orderDateYn}"> orderVal </c:if>" value="${userOrder.orderDate}"/>
																	</div>
																</div>
																<%-- <input class="col-lg-12" type="text" name="orderDate" id="orderDate" value="${userOrder.orderDate}" placeholder="ex):20201231" /> --%>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.buySiteYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.buySiteYn}"> orderNm </c:if>">
																구매 사이트
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.buySiteYn}"> orderVal </c:if>" type="text" name="buySite" id="buySite" value="${userOrder.buySite}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.getBuyYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.getBuyYn}"> orderNm </c:if>">
																사용 용도
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.getBuyYn}"> orderVal </c:if>" type="text" placeholder="1: 개인,  2: 사업자" name="getBuy" id="getBuy" value="${userOrder.getBuy}" />
															</div>
														</c:if>
														</div>
														<div class="hr dotted"></div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.declTypeYnExpress) or (!empty expressOrderVO.foodYnExpress) or (!empty expressOrderVO.cosmeticYnExpress) or (!empty expressOrderVO.signYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.declTypeYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.declTypeYn}"> orderNm </c:if>">
																세관신고 유형
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 <c:if test="${!empty optionOrderVO.declTypeYn}"> orderVal </c:if> " id="declType" name="declType">
																<option <c:if test="${userOrder.declType eq '1'}"> selected</c:if> value="1">Gift</option>
																<option <c:if test="${userOrder.declType eq '2'}"> selected</c:if> value="2">Sample</option>
																<option <c:if test="${userOrder.declType eq '3'}"> selected</c:if> value="3">Documents</option>
																<option <c:if test="${userOrder.declType eq '4'}"> selected</c:if> value="4">Others (default)</option>
																</select>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.foodYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.foodYn}"> orderNm </c:if>">
																음식포함 여부
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 <c:if test="${!empty optionOrderVO.foodYn}"> orderVal </c:if>" id="food" name="food">
																<option <c:if test="${userOrder.food eq 'Y'}"> selected</c:if> value="Y">Y</option>
																<option <c:if test="${userOrder.food eq 'N'}"> selected</c:if> value="N">N</option>
																</select>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.cosmeticYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cosmeticYn}"> orderNm </c:if>">
																화장품포함 여부
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 <c:if test="${!empty optionOrderVO.cosmeticYn}"> orderVal </c:if>" id="cosmetic" name="cosmetic">
																<option <c:if test="${userOrder.cosmetic eq 'Y'}"> selected</c:if> value="Y">Y</option>
																<option <c:if test="${userOrder.cosmetic eq 'N'}"> selected</c:if> value="N">N</option>
																</select>
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.signYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.signYn}"> orderNm </c:if>">
																대면서명 여부
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 <c:if test="${!empty optionOrderVO.signYn}"> orderVal </c:if>" id="sign" name="sign">
																<option <c:if test="${userOrder.sign eq 'Y'}"> selected</c:if> value="Y">Y</option>
																<option <c:if test="${userOrder.sign eq 'N'}"> selected</c:if> value="N">N</option>
																</select>
															</div>
														</c:if>
														</div><div class="hr dotted"></div>
													</c:if>
													

													<c:if test="${(!empty expressOrderVO.userHeightYnExpress) or (!empty expressOrderVO.userLengthYnExpress) or (!empty expressOrderVO.userWidthYnExpress) or (!empty expressOrderVO.dimUnitYnExpress)}">
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.userHeightYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userHeightYn}"> orderNm </c:if>">
																Height
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 volumeWeight <c:if test="${!empty optionOrderVO.userHeightYn}"> orderVal </c:if>" floatOnly type="text" name="userHeight" id="userHeight" value="${userOrder.userHeight}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.userLengthYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userLengthYn}"> orderNm </c:if>">
																Length
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 volumeWeight <c:if test="${!empty optionOrderVO.userLengthYn}"> orderVal </c:if>" floatOnly type="text" name="userLength" id="userLength" value="${userOrder.userLength}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.userWidthYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userWidthYn}"> orderNm </c:if>">
																Width
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 volumeWeight <c:if test="${!empty optionOrderVO.userWidthYn}"> orderVal </c:if>" floatOnly type="text" name="userWidth" id="userWidth" value="${userOrder.userWidth}" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.dimUnitYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.dimUnitYn}"> orderNm </c:if>">
																길이 단위
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 volumeWeight <c:if test="${!empty optionOrderVO.dimUnitYn}"> orderVal </c:if>" id="dimUnit" name="dimUnit" >
																	<option value="CM">CM</option>
																	<option value="IN">INCH</option>
																</select>
															</div>
														</c:if>
														</div>
													</c:if>
													
													<c:if test="${(!empty expressOrderVO.userWtaYnExpress) or (!empty expressOrderVO.userWtcYnExpress) or (!empty expressOrderVO.boxCntYnExpress) or (!empty expressOrderVO.wtUnitYnExpress)}"> 
														<div class="row hr-8">
														<c:if test="${!empty expressOrderVO.userWtaYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userWtaYn}"> orderNm </c:if>">
																실무게
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.userWtaYn}"> orderVal </c:if>" floatOnly type="text" name="userWta" id="userWta" value="${userOrder.userWta }" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.userWtcYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userWtcYn}"> orderNm </c:if>">
																부피 무게
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.userWtcYn}"> orderVal </c:if>" floatOnly type="text" name="userWtc" id="userWtc" value="${userOrder.userWtc }" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.boxCntYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.boxCntYn}"> orderNm </c:if>">
																박스 개수
															</div>
															<div class="col-xs-2 center">
																<input class="col-xs-12 <c:if test="${!empty optionOrderVO.boxCntYn}"> orderVal </c:if>" numberOnly type="text" name="boxCnt" id="boxCnt" value="${userOrder.boxCnt }" />
															</div>
														</c:if>
														<c:if test="${!empty expressOrderVO.wtUnitYnExpress}">
															<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.wtUnitYn}"> orderNm </c:if>">
																무게 단위
															</div>
															<div class="col-xs-2 center">
																<select class="col-xs-12 <c:if test="${!empty optionOrderVO.wtUnitYn}"> orderVal </c:if>" id="wtUnit" name="wtUnit">
																	<option value="KG" <c:if test="${userOrder.wtUnit eq 'KG'}">selected</c:if>>KG</option>
																<option value="LB" <c:if test="${userOrder.wtUnit eq 'LB'}">selected</c:if>>LB</option>
																</select>
															</div>
														</c:if>
														</div>
														
														<div class="hr dotted"></div>
													</c:if>
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
													<div class="row hr-8 disable" id="expInputDiv" name="expInputDiv" style="font-size:12px !important;">
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
										</div>
										<!-- 기본정보 End -->
										<!-- 설정정보 Start -->
										<br/>
										<br/>
										<div class="col-xs-12 col-lg-12" >
											<div class="col-xs-12 col-lg-3 form-group" style="font-size: 5;">
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
																	<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress)}">
																	<div class="row  hr-8">
																	<c:if test="${!empty expressItemVO.trkComYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkComYn}"> itemNm </c:if>" >국내 택배 회사</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkComYn}"> itemVal </c:if>" type="text" name="trkCom" id="trkCom${status.count}" value="${orderItem.trkCom}" />
																				<input class="width-100" type="hidden" name="subNo" id="subNo${status.count}" value="${orderItem.subNo}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.trkDateYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkDateYn}"> itemNm </c:if>" >운송날짜</div>
																			<div class="col-md-9 col-xs-12">
																				<div class="input-group input-group-sm">
																					<span class="input-group-addon">
																						<i class="ace-icon fa fa-calendar"></i>
																					</span>
																					<input type="text" id="trkDate${status.count}" name="trkDate" class="width-100 checkItem <c:if test="${!empty optionItemVO.trkDateYn}"> itemVal </c:if>" onclick="javascript:test(this);" value="${orderItem.trkDate}"/>
																				</div>
																			</div>
																		</div>
																	</c:if>
																	</div>
																	</c:if>
																	<c:if test="${(!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}">
																	<div class="row  hr-8">
																	<c:if test="${!empty expressItemVO.trkNoYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkNoYn}"> itemNm </c:if>" >송장 번호</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkNoYn}"> itemVal </c:if>" type="text" name="trkNo" id="trkNo${status.count}" value="${orderItem.trkNo}" />
																				<%-- <div class="input-group input-group-sm">
																					
																					<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
																						<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
																					</span>
																				</div> --%>
																			</div>
																		</div>
																		</c:if>
																		<c:if test="${!empty expressItemVO.hsCodeYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.hsCodeYn}"> itemNm </c:if>" >
																				<a style="font-weight:bold; cursor:pointer;" onclick="fn_popupSearchCode('${status.count}')">HS CODE&nbsp;<i class="fa fa-external-link" id="searchIcon" style="font-size:14px; cursor:pointer;"></i></a>
																			</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.hsCodeYn}"> itemVal </c:if>" type="text" name="hsCode" id="hsCode${status.count}" value="${orderItem.hsCode}" />
																			</div>
																		</div>
																		</c:if>
																	</div>
																	</c:if>
																	<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress) or (!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}"> 
																	<div class="hr dotted"></div>
																	</c:if>
																	
																	
																	
																	<div class="row  hr-8">
																	<c:if test="${!empty expressItemVO.itemDetailYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDetailYn}"> itemNm </c:if>" >상품명</div>
																			<div class="col-lg-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDetailYn}"> itemVal </c:if>" type="text" name="itemDetail" id="itemDetail${status.count}" value="${orderItem.itemDetail}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.nativeItemDetailYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemNm </c:if>" >현지 상품명</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemVal </c:if>" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${orderItem.nativeItemDetail}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.itemExplanYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemExplanYn}"> itemNm </c:if>" >상품 상세설명</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemExplanYn}"> itemVal </c:if>" type="text" name="itemExplan" id="itemExplan${status.count}" value="${orderItem.itemExplan}" />
																			</div>
																		</div>
																		
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm " >BOX 번호</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem itemVal" type="text" name="inBoxNum" id="inBoxNum${status.count}" value="${orderItem.inBoxNum}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.itemCntYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemCntYn}"> itemNm </c:if>" >상품 개수</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemCntYn}"> itemVal </c:if>" type="text" numberOnly name="itemCnt" id="itemCnt${status.count}" value="${orderItem.itemCnt}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.qtyUnitYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemNm </c:if>" >상품 단위</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemVal </c:if>" type="text" name="qtyUnit" id="qtyUnit${status.count}" value="${orderItem.qtyUnit}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.brandYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.brandYn}"> itemNm </c:if>" >BRAND</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.brandYn}"> itemVal </c:if>" type="text" name="brandItem" id="brand${status.count}" value="${orderItem.brand}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.unitValueYnExpress}">		
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.unitValueYn}"> itemNm </c:if>">상품 단가</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.unitValueYn}"> itemVal </c:if>" type="text" floatOnly name="unitValue" id="unitValue${status.count}" value="${orderItem.unitValue}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.itemDivYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDivYn}"> itemNm </c:if>" >상품 종류</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDivYn}"> itemVal </c:if>" type="text" name="itemDiv" id="itemDiv${status.count}" value="${orderItem.itemDiv}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.cusItemCodeYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemNm </c:if>" >상품 코드</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemVal </c:if>" type="text" name="cusItemCode" id="cusItemCode${status.count}" value="${orderItem.cusItemCode}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.itemMeterialYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemNm </c:if>" >상품재질</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemVal </c:if>" type="text" name="itemMeterial" id="itemMeterial${status.count}" value="${orderItem.itemMeterial}" />
																			</div>
																		</div>
																	</c:if>
																	<div class="col-md-6 col-xs-12 hr-8">
																		<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemNm </c:if>" >상품 무게</div>
																		<div class="col-md-9 col-xs-12">
																			<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemVal </c:if>" floatOnly type="text" name="userItemWta" id="userItemWta${status.count}" value="${orderItem.userItemWta}" />
																		</div>
																	</div>
																	<%-- <c:if test="${!empty expressItemVO.hsCodeYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 실무게</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem" type="text" name="userWtaItem" id="userWta${status.count}" value="" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.hsCodeYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 부피무게</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem" type="text" name="userWtcItem" id="userWtc${status.count}" value="" />
																			</div>
																		</div>
																	</c:if> --%>
																	<c:if test="${!empty expressItemVO.itemWtUnitYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemNm </c:if>" >무게 단위</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemVal </c:if>" type="text" name="wtUnitItem" id="wtUnitItem${status.count}" value="${orderItem.wtUnit}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.chgCurrencyYnExpress}">
																			<div class="col-md-6 col-xs-12">
																				<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemNm </c:if>" >통화 단위</div>
																				<div class="col-md-9 col-xs-12">
																					<input class="width-100 checkItem <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemVal </c:if>" type="text" name="chgCurrency" id="chgCurrency${status.count}" value="${orderItem.chgCurrency}" />
																				</div>
																			</div>
																	</c:if>
																	
																	<c:if test="${!empty expressItemVO.itemBarcodeYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemNm </c:if>" >상품 바코드</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemVal </c:if>" type="text" name="itemBarcode" id="itemBarcode${status.count}" value="${orderItem.itemBarcode}" />
																			</div>
																		</div>
																	</c:if>	
																		
																	</div>
																	<div class="hr dotted"></div>

																	
																	<c:if test="${(!empty expressItemVO.makeCntryYnExpress) or (!empty expressItemVO.makeComYnExpress)}">
																	<div class="row  hr-8">
																	<c:if test="${!empty expressItemVO.makeCntryYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeCntryYn}"> itemNm </c:if>" >제조국</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeCntryYn}"> itemVal </c:if>" type="text" name="makeCntry" id="makeCntry${status.count}" value="${orderItem.makeCntry}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.makeComYnExpress}">
																		<div class="col-md-6 col-xs-12">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeComYn}"> itemNm </c:if>" >제조 회사</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeComYn}"> itemVal </c:if>" type="text" name="makeCom" id="makeCom${status.count}" value="${orderItem.makeCom}" />
																			</div>
																		</div>
																	</c:if>
																	</div>
																	</c:if>
																	<c:if test="${(!empty expressItemVO.itemUrlYnExpress) or (!empty expressItemVO.itemImgUrlYnExpress)}">
																	<div class="row  hr-8">
																	<c:if test="${!empty expressItemVO.itemUrlYnExpress}">	
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUrlYn}"> itemNm </c:if>">상품 URL</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUrlYn}"> itemVal </c:if>" type="text" name="itemUrl" id="itemUrl${status.count}" value="${orderItem.itemUrl}" />
																			</div>
																		</div>
																	</c:if>
																	<c:if test="${!empty expressItemVO.itemImgUrlYnExpress}">
																		<div class="col-md-6 col-xs-12 hr-8">
																			<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemNm </c:if>">상품 이미지 URL</div>
																			<div class="col-md-9 col-xs-12">
																				<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemVal </c:if>" type="text" name="itemImgUrl" id="itemImgUrl${status.count}" value="${orderItem.itemImgUrl}" />
																			</div>
																		</div>
																	</c:if>
																	</div>
																	</c:if>
																	
																	
																	
																	
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
					$(".shipperNm").addClass('red');
					$(".cneeNm").addClass('red');
					$(".orderNm").addClass('red');
					$(".itemNm").addClass('red');
					$('#dstnNation').trigger("change");
					
					/* $("#totals").val($("#transCode").val());
					$("#totals").trigger('change'); */
					
					/* if($("#transCode").val() == 'YSL'){
						$("input").attr("readonly","true");
						$("#addBtns").hide();
					} */
					for(var i = 1 ; i <= cnt; i++){
						$("#trkDate"+i).trigger('click');
					}
					$("#shipperName").focus();

					//expDiv();
					expDiv2();
				});
				//select target form change
			})
			
			
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

				}else{
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
			$('#transCode').on('change', function(){
				/* var splitString = $("#totals").val().split('_');
				$("#orgStation").val(splitString[0]);
				$("#dstnNation").val(splitString[1]);
				$("#transCode").val(splitString[2]); */

				if($("#transCode").val() == ''){
					$("#table-contents").html("");
					return;
				}
			});
			
			/* $('[name*=delBtns]').on('click',function(e){
				if($(".panel").length > 1){
					$(this).closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}*/
				
				/* $('input:checked').val(); 
			}); */

			$('#backToList').on('click', function(e){
				//location.href = "/cstmr/apply/shpngAgncy?transCode="+$("#transCode").val();
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
							addHtml = "<option value=''>택배회사가 없습니다.</option>";
						}else{
							addHtml = "<option value=''>택배회사를 선택해주세요</option>";
							for(var i = 0; i < data.transComList.length; i++){
								addHtml += "<option ";
								if(data.transComList[i].transCode == $("#transCode").val()){
									addHtml += "selected ";
								}
								addHtml += "value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transName+"</option>'"
							}
						}
						$("#transCodeList").html(addHtml)
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
					if($(this).attr("name") == "unitValue" || $(this).attr("name") == "itemCnt"){
						if($(this).val() == 0){
							var itemNum = e/($('.itemNm').length/cnt);
							itemNum = parseInt(itemNum)+1;
							alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"는 0이 될 수 없습니다.")
							valiChk = 1;
							return false;
						}
					}
					if($(this).val() == ""){
						var itemNum = e/($('.itemNm').length/cnt);
						itemNum = parseInt(itemNum)+1;
						alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
					
					if ($("#transCode").val() == "VNP") {
						if ($(this).attr("name") == "chgCurrency") {
							if ($(this).val().toUpperCase() != "VND") {
								var itemNum = e/($('.itemNm').length/cnt);
								itemNum = parseInt(itemNum)+1;
								alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"는 VND외에 입력 불가능 합니다.")
								valiChk = 1;
								return false;
							}	
						}
					}
				})
				if(valiChk==1){
					return;
				}

				var format = /^(19[7-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
				$("input[name=trkDate]").each(function(e){
					if($(this).val() != ''){
						if(!format.test($(this).val())){
							alert("날짜형식(YYYYMMDD)에 어긋납니다.");
							$(this).focus();
							valiChk = 1;
							return false;
						}
					}
				})
				if(valiChk==1){
					return;
				}

				$("input[name='boxCnt']").each(function(e) {
					if($(this).val() < 1) {
						alert("박스 개수는 최소 1개 이상이어야 됩니다.");
						valiChk = 1;
						return false;
					}
				})
				
				if(valiChk==1){
					return;
				}


				$(".checkItem").each(function(e){
					if(!$(this).hasClass("itemVal")){
						if($(this).val() == ""){
							$(this).val("d#none#b");
						}
					}
				})
				
				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/modifyOrderInfo',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						if(data == "S"){
							$(".checkItem").each(function(e){
								if(!$(this).hasClass("itemVal")){
									if($(this).val() == "d#none#b"){
										$(this).val("");
									}
								}
							})
							alert("수정 되었습니다.");
							location.href="/cstmr/apply/shpngAgncy?transCode="+$("#transCode").val();
						}else{
							$(".checkItem").each(function(e){
								if(!$(this).hasClass("itemVal")){
									if($(this).val() == "d#none#b"){
										$(this).val("");
									}
								}
							})
							alert("수정중 오류가 발생했습니다. 데이터를 확인해 주세요")
						}
		            }, 		    
		            error : function(xhr, status) {
		            	$(".checkItem").each(function(e){
							if(!$(this).hasClass("itemVal")){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							}
						})
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

			function fn_hsCodeIn(gIdx, gHsCode, gCategory, gItemDetail, gMaterial, gCnt) {

				$("#hsCode"+gCnt).val(gHsCode);
				$("#itemDetail"+gCnt).val(gItemDetail);
				$("#itemDiv"+gCnt).val(gCategory);
				$("#itemMeterial"+gCnt).val(gMaterial);

				if(gMaterial.length > 0) {
					$("#itemMeterial"+gCnt).val(gMaterial);
				}
			}
			

			function fn_popupSearchCode(num) {
				var _width = '520';
			    var _height = '360';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
			    
				window.open("/cstmr/apply/searchItemCode?cnt="+num, "HS_CODE", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}	

		</script>
		<!-- script addon end -->
	</body>
</html>
		