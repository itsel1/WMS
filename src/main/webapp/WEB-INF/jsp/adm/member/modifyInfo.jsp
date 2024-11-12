<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}

.inner-content-side>div {
	text-align: center;
}

input[type=text]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}
input[type=password]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

.vertiMidle{
	vertical-align: middle;
}

.pd-1rem{
	padding: 1rem 1rem;
	font-size: 14px; 
	font-weight: 400;
}
</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
</head>
<title>사용자 관리</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<!-- SideMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<!-- SideMenu End -->

		<div class="main-content">

			<form name="userInfoForm" id="userInfoForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">

					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>거래처 관리</li>
							<li id="targets" name="targets">
								<c:choose>
									<c:when test="${targetPage eq 'ent'}">
										기업 회원	
									</c:when>
									<c:otherwise>
										개인 회원
									</c:otherwise>
							</c:choose>
							</li>
							<li class="active">정보 수정</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>${userInfo.userId} 정보 수정</h1>
						</div>
						<div id="inner-content-side">
							<!--  -->
							<div class="row">
								<div class="col-xs-12 col-lg-4 col-lg-offset-2" >
									<!-- 기본정보 Start -->
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>기본정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>아이디</label>
												<input class="width-100" type="text" name="userId" readonly id="userId" value="${userInfo.userId}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>패스워드 초기화</label>
												<button id="resetPw" type="button" class="btn btn-sm btn-round btn-inverse">초기화</button>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>담당자 명</label>
												<input class="width-100" type="text" name="userName" id="userName" value="${userInfo.userName}" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>이름 또는 상호명</label>
												<input class="width-100" type="text" name="comName" id="comName" value="${userInfo.comName}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>휴대전화 번호</label>
												<input class="width-100" type="text" name="userHp" id="userHp" value="${userInfo.userHp}" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>Email</label>
												<input class="width-100" type="text" name="userEmail" id="userEmail" value="${userInfo.userEmail}" />
											</div>
										</div>
										
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">주소</label><br/>
												<input type="text" class="width-30 hr-8" name="userZip" id="userAddr" value="${userInfo.userZip}" /> 
												<button id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
													<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
												</button>
												<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="${userInfo.userAddr}" />
												<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="${userInfo.userAddrDetail}" />
											</div>
										</div>
										
										
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>상점명</label>
												<input class="width-100" type="text" name="storeName" id="storeName" value="${userInfo.storeName }" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>쇼핑몰 URL</label>
												<input class="width-100" type="text" name="storeUrl" id="storeUrl" value="${userInfo.storeUrl}" />
											</div>
										</div>
									</div>
									
									
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>세관 신고 정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>이름 또는 상호명(영문)</label> 
												<input class="width-100" type="text" name="comEName" id="comEName" value="${userInfo.comEName}" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>전화번호</label>
												<input class="width-100" type="text" name="userTel" id="userTel" value="${userInfo.userTel}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" name="userEAddr" id="userEAddr" value="${userInfo.userEAddr}" />
												<label class="bigger-110">상세 주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" name="userEAddrDetail" placeholder="상세 주소(영문)" id="userEAddrDetail" value="${userInfo.userEAddrDetail}" />
											</div>
										</div>
									</div>
									
									
									<!-- Key Start -->
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>API 정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label>API KEY</label>
											</div>
											<div class="col-lg-12 col-xs-12">
												<c:if test="${empty userInfo.apiKey}">
													<strong>API KEY가 발급되지 않았습니다.</strong>
													<button class="btn btn-xs btn-success btn-white" type="button" id="registApiKey" name="registApiKey">API KEY발급 받기</button>
												</c:if>
												<c:if test="${!empty userInfo.apiKey}">
													<strong>${userInfo.apiKey}</strong>
												</c:if>
											</div>
										</div>
										
										
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label>접속 가능 IP 지정</label>
											</div>
											<div class="col-lg-6 col-xs-12">
												<input type="text" id="inputIp" name="inputIp" style="background-color:rgb(255,255,237)">
											</div>
											<div class="col-lg-6 col-xs-12" style="margin-top:4px;">
												<c:if test="${!empty userInfo.apiKey}">
													<button type="button" id="addIp" name=addIp" class="btn btn-primary btn-sm btn-white">추가</button>
												</c:if>
												
											</div>
										</div>
										<div class="space-20"></div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label>접속 가능 IP 목록</label>
											</div>
											<div class="col-lg-12 col-xs-12" name="ipList" id="ipList">
												<c:forEach items="${allowIpList }" var="allowIp" varStatus="status">
													<div class="col-lg-6 col-xs-12" id="allowIp${status.count}" name="allowIp">
														<input type="text" ipOnly style="width:130px;height:30px;background-color: white !important;" readonly value="${allowIp.allowIp}">
														<i class="ace-icon fa fa-times" style="padding: 5px; cursor: pointer;" onclick="javascript:fn_deleteIp('allowIp${status.count}')"></i>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
									<!-- Key End -->



								</div>
								<div class="col-xs-12 col-lg-4" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>청구지 정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>업체명</label>
												<input class="width-100" type="text" name="invComName" id="invComName" value="${invUserInfo.invComName }" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>업체 전화번호</label>
												<input class="width-100" type="text" name="invComTel" id="invComTel" value="${invUserInfo.invComTel}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>담당자명</label>
												<input class="width-100" type="text" name="invUserName" id="invUserName" value="${invUserInfo.invUserName}" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>담당자 전화번호</label>
												<input class="width-100" type="text" name="invUserTel" id="invUserTel" value="${invUserInfo.invUserTel}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>청구지 Email</label>
												<input class="width-100" type="text" name="invUserEmail" id="invUserEmail" value="${invUserInfo.invUserEmail}" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">청구지 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddr" id="invComAddr" value="${invUserInfo.invComAddr}" />
												<label class="bigger-110">청구지 상세 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddrDetail" id="invComAddrDetail" value="${invUserInfo.invComAddrDetail}" />
											</div>
										</div>
										
									</div>
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>설정정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>기업/개인</label>
												<div class="radio">
													<label>
														<input name="etprView" type="radio" class="ace" value="Y" <c:if test="${userInfo.etprYn eq 'Y'}"> checked</c:if>/>
														<span class="lbl"> 기업</span>
													</label>
													<label>
														<input name="etprView" type="radio" class="ace" value="N" <c:if test="${userInfo.etprYn eq 'N'}"> checked</c:if>/>
														<span class="lbl"> 개인</span>
													</label>
													<input type="hidden" id="etprYn" name="etprYn" 
														<c:if test="${targetPage eq 'ent'}">
															value="Y"
														</c:if>
														<c:if test="${targetPage ne 'ent'}">
															value="N"
														</c:if>
													/>
												</div>
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>예치금 사용여부</label>
												<div class="radio">
													<label>
														<input name="depositYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.depositYn eq 'Y'}"> checked</c:if>/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="depositYn" type="radio" class="ace" value="N" <c:if test="${userInfo.depositYn eq 'N'}"> checked</c:if>/>
														<span class="lbl"> 미사용</span>
													</label>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>검품메뉴 사용여부</label>
												<div class="radio">
													<label>
														<input name="inspYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.inspYn eq 'Y'}"> checked</c:if>/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="inspYn" type="radio" class="ace" value="N" <c:if test="${userInfo.inspYn eq 'N'}"> checked</c:if>/>
														<span class="lbl"> 미사용</span>
													</label>
												</div>
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>사입메뉴 사용여부</label>
												<div class="radio">
													<label>
														<input name="takeYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.takeYn eq 'Y'}"> checked</c:if>/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="takeYn" type="radio" class="ace" value="N" <c:if test="${userInfo.takeYn eq 'N'}"> checked</c:if>/>
														<span class="lbl"> 미사용</span>
													</label>
												</div>
											</div>
										</div>
										<div class="col-xs-12 col-lg-12">
											<label>재고 무게 측정</label>
											<div class="radio">
												<label>
													<input name="stockWeightYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.stockWeightYn eq 'Y'}"> checked</c:if>/>
													<span class="lbl"> 사용</span>
												</label>
												<label>
													<input name="stockWeightYn" type="radio" class="ace" value="N" <c:if test="${userInfo.stockWeightYn eq 'N'}"> checked</c:if>/>
													<span class="lbl"> 미사용</span>
												</label>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >회원 등급</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="userGrade" id="userGrade" readonly value="${userInfo.userGrade}" />
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >검품 비용</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="inspPrice" id="inspPrice" value="${userInfo.inspPrice}" />
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >무게 오차범위</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="wtErrRange" id="wtErrRange" value="${userInfo.wtErrRange}" />
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >출발지</div>
												<div class="col-lg-6 col-xs-12">
												<c:forEach items="${userOrgNation}" var="userOrgNation">
													<input type="hidden" id="userOrgNation" name="userOrgNation" value="${userOrgNation}">
												</c:forEach>
													
													<select multiple class="chosen-select form-control tag-input-style width-100 pd-1rem" id="orgNation" name="orgNation" data-placeholder="국가를 선택해 주세요">
														<option value="">  </option>
														<c:forEach items="${nations}" var="nations">
															<option value="${nations.nationCode}">${nations.nationName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >도착지</div>
												<div class="col-lg-6 col-xs-12">
												<c:forEach items="${userDstnNation}" var="userDstnNation">
													<input type="hidden" id="userDstnNation" name="userDstnNation" value="${userDstnNation}">
												</c:forEach>
													
													<select multiple class="chosen-select form-control tag-input-style width-100 pd-1rem" id="dstnNation" name="dstnNation" data-placeholder="국가를 선택해 주세요">
														<option value="">  </option>
														<c:forEach items="${nations}" var="nations">
															<option value="${nations.nationCode}">${nations.nationName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >택배 회사</div>
												<div class="col-lg-6 col-xs-12" name="trackComs" id="trackComs">
													
												</div>
											</div>
											<input type="hidden" id="trkComList" name="trkComList" value="${userInfo.trkComList}">
											<input type="hidden" id="trkComValueList" name="trkComValueList" >
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >승인여부</div>
												<div class="col-lg-6 col-xs-12">
													<div class="radio">
														<label>
															<input name="aprvYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.aprvYn eq 'Y'}"> checked</c:if>/>
															<span class="lbl"> 승인</span>
														</label>
														<label>
															<input name="aprvYn" type="radio" class="ace" value="N" <c:if test="${userInfo.aprvYn eq 'N'}"> checked</c:if>/>
															<span class="lbl"> 미승인</span>
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
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
											<div id="updateUser" style="text-align: center">
												<button id="updateInfo" type="button" class="btn btn-sm btn-primary">수정</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->

	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	

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
	<script src="/assets/js/bootstrap.min.js"></script>
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$("#zeroMenu").toggleClass('open');
				$("#zeroMenu").toggleClass('active');
				if($("#targets").text().trim() == "기업 회원"){
					$("#zeroOne").toggleClass('active');
				}else{
					$("#zeroTwo").toggleClass('active');
				}

				var orgNation = new Array() ;
				$("input[name=userOrgNation]").each(function(index, item){
					orgNation.push($(item).val());
				});
				$("#orgNation").val(orgNation);
				$("#orgNation option:selected").trigger("chosen:updated");

				var dstnNation = new Array() ;
				$("input[name=userDstnNation]").each(function(index, item){
					dstnNation.push($(item).val());
				});
				$("#dstnNation").val(dstnNation);
				$("#dstnNation option:selected").trigger("chosen:updated");
				$("#dstnNation").trigger("change");
			});

			//initiate dataTables plugin
			
			$('#backToList').on('click', function(e) {
				location.href = "/mngr/acnt/userList";
				/* if($("#targets").text().trim() == "기업 회원"){
					location.href = '/mngr/acnt/entrpList';
				}else{
					location.href = '/mngr/acnt/indvdList';
				}*/
			});
			
			$('#resetPw').on('click', function(e) {
				var formData = $("#userInfoForm").serialize();
				$.ajax({
					url:'/mngr/acnt/pwReset/'+$('#userId').val(),
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("변경 되었습니다.");
						}else{
							alert("변경중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("변경에 실패 하였습니다. 관리자에게 문의해 주세요.");
		            }
				});

			});	

			/* $('#resetPw').on('click', function(e) {
				var formData = $("#userInfoForm").serialize();
				$.ajax({
					url:'/mngr/acnt/pwReset/'+$('#userId').val(),
					type: 'POST',
					data: formData,
					success : function(data) {
						if(data == "S"){
							alert("변경 되었습니다.");
						}else{
							alert("변경중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("변경에 실패 하였습니다. 관리자에게 문의해 주세요.");
		            }
				});

			}); */

			$('#updateInfo').on('click', function(e) {
				if($("#userId").val() == ""){
					alert("아이디를 입력해 주세요");
					return false;
				}

				/*if($("#userName").val() == ""){
					alert("사용자 명을 입력해 주세요");
					return false;
				}

				if($('#userEmail').val() == ""){
					alert("Email을 확인해 주세요");
					return false;
				}else{
					var regExpMail = /^[a-zA-z0-9_+.-]+@([a-zA-z0-9-]+\.)+[a-zA-z0-9]{2,4}$/;
					if(!regExpMail.test($('#userEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#userEmail').focus();
						return false;
					}
				} 
				
				if($('#userTel').val() == ""){
					alert("전화번호를 확인해 주세요");
					return false;
				} */

				if($('#inspPrice').val() == ""){
					alert("검품비용을 입력해 주세요");
					return false;
				}

				if($('#wtErrRange').val() == ""){
					alert("무게 오차범위를 입력해 주세요");
					return false
				}

				if($('#invComName').val() == ""){
					alert("청구지 업체명을 확인해 주세요");
					$('#invComName').focus();
					return false;
				}

				if($('#invComTel').val() == ""){
					alert("청구지 업체 전화번호를 확인해 주세요");
					$('#invComTel').focus();
					return false;
				}

				if($('#invUserName').val() == ""){
					alert("담당자 명을 확인해 주세요");
					$('#invUserName').focus();
					return false;
				}

				if($('#invUserTel').val() == ""){
					alert("담당자 전화번호를 확인해 주세요");
					$('#invUserTel').focus();
					return false;
				}


				if($('#invUserEmail').val() == ""){
					alert("청구지 Email을 확인해 주세요");
					$('#invUserEmail').focus();
					return false;
				}else{
					/*
					var regExpMail = /^[a-zA-z0-9_+.-]+@([a-zA-z0-9-]+\.)+[a-zA-z0-9]{2,4}$/;
					if(!regExpMail.test($('#invUserEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#invUserEmail').focus();
						return false;
					}*/
				}

				
				if($('#invComAddr').val() == ""){
					alert("청구지 주소를 확인해 주세요");
					$('#invComAddr').focus();
					return false;
				}

				if($('#invComAddrDetail').val() == ""){
					alert("청구지 상세주소를 확인해 주세요");
					$('#invComAddrDetail').focus();
					return false;
				}

				if($('#orgNation').val() == null){
					alert("출발지를 선택해 주세요");
					return false;
				}

				if($('#dstnNation').val() == null){
					alert("도착지를 선택해 주세요");
					return false;
				} 
				
				var str = "";
				$(".targetCom:checked").each(function (index) {  
			        str += $(this).val() + ",";  
			    });  

			   /*  if(str == ""){
					alert("택배회사를 체크해 주세요");
					return false;
			    } */
				
				$('#trkComList').val(str);
				$("#etprYn").val($("#etprView").val());
				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/mngr/acnt/modify/'+$('#userId').val(),
					type: 'PATCH',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("수정 되었습니다.");
							location.href="/mngr/acnt/userList";
							/* if($("#targets").text().trim() == "기업 회원"){
								location.href="/mngr/acnt/entrpList";
							}else{
								location.href="/mngr/acnt/indvdList";
							} */
						}else{
							alert("수정중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
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

			$('.chosen-select').on('change',function(){
				var str2 = [];
				$(".targetCom:checked").each(function (index) {  
			        str2.push($(this).val());
			    });  
				$('#trkComValueList').val(str2);
				var formData = $("#userInfoForm").serialize();
				if($('.chosen-select').val() == null){
					$("#trackComs").empty();
					return;
				}
				$.ajax({
					url:'/mngr/acnt/transCom',
					type: 'GET',
					data:formData,
					success : function(data) {
						$("#trackComs").empty();
						$('#trackComs').html(data);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});
		}) 
		function fn_deleteIp(allowIp){
			var param1 = $("#"+allowIp).children()[0].value;
			$.ajax({
				url:'/mngr/acnt/modify/deleteAllowIp/'+$("#userId").val(),
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						$("#"+allowIp).remove();
						alert("지정 IP에서 삭제 되었습니다.");
					}else{
						alert("삭제 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		}


		$("#registApiKey").on('click',function(e){
			$.ajax({
				url:'/mngr/acnt/modify/apiKeyAllow/'+$("#userId").val(),
				type: 'POST',
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						alert("APIKEY가 발급되었습니다.");
						location.reload();
					}else{
						alert("등록 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		});

		

		$("#addIp").on('click',function(e){
			var param1 = $("#inputIp").val();
			var check = 0;
			var num = $("div[name='allowIp']").size()+1;
			if (param1 == ''){
				alert("IP를 입력 해 주세요");
				return false;
			}

			if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(param1)) {  
			    
			}else{
				alert("유효한 IP 주소 형식이 아닙니다.")
				return false;
			}
			    
			$("div[name='allowIp']").each(function(e){
				if($("div[name=allowIp]")[e].children[0].value == $("#inputIp").val()){
					alert("이미 등록된 IP입니다.");
					check = 1;
					return false;
				}
			})
				
			if(check != 0){
				return;
			}
			
			$.ajax({
				url:'/mngr/acnt/modify/registAllowIp/'+$("#userId").val(),
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						var addHtml = "";
						
						addHtml += "<div class='col-lg-6 col-xs-12' id='allowIp"+num+"' name='allowIp'>"
						addHtml += "<input type='text' style='width:130px;height:30px;background-color: white !important;' readonly value='"+data.allowIp+"'>";
						addHtml += '<i class="ace-icon fa fa-times" style="padding:5px;cursor: pointer;" onclick="javascript:fn_deleteIp(\'allowIp'+num+'\')"></i>'; 
						addHtml += "</div>";
						$("#ipList").append(addHtml);
						alert("지정 IP에 등록 되었습니다.");
						$("#inputIp").val("");
					}else{
						alert("등록 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		})
	</script>
	<!-- script addon end -->
</body>
</html>