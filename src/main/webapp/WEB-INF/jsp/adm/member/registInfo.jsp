<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

			<form name="userInfoForm" id="userInfoForm" method="post">
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
							<li class="active">회원 등록</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>회원 등록</h1>
						</div>
						<div id="inner-content-side">
							<!-- 기본정보 Start -->
							<div class="row">
								<div class="col-xs-12 col-lg-4 col-lg-offset-2" >
									<!-- 기본정보 Start -->
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>기본정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label>아이디</label><br>
												<input class="width-70" type="text" name="userId" id="userId" value="" />&nbsp;&nbsp;&nbsp;
												<button id="checkId" class="btn btn-primary btn-sm" name="checkId" type="button">중복확인</button>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>패스워드</label>
												<input class="width-100" type="password" name="password1" id="password1" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>패스워드 확인</label>
												<input class="width-100" type="password" name="password2" id="password2" value="" />
												<span class="" style="display:block;" id="passwordMsg"></span>
											</div>
										</div>
										<div class="row form-group">
											<input type="hidden" name="userPw" id="userPw"/>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>담당자 명</label>
												<input class="width-100" type="text" name="userName" id="userName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>이름 또는 상호명</label>
												<input class="width-100" type="text" name="comName" id="comName" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>휴대전화 번호</label>
												<input class="width-100" type="text" name="userHp" id="userHp" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>Email</label>
												<input class="width-100" type="text" name="userEmail" id="userEmail" value="" />
											</div>
										</div>
										
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">주소</label><br/>
												<input type="text" class="width-30 hr-8" name="userZip" id="userAddr" value="" /> 
												<button id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
													<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
												</button>
												<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="" />
												<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="" />
											</div>
										</div>
										
										
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>상점명</label>
												<input class="width-100" type="text" name="storeName" id="storeName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>쇼핑몰 URL</label>
												<input class="width-100" type="text" name="storeUrl" id="storeUrl" value="" />
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
												<input class="width-100" type="text" name="comEName" id="comEName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>전화번호</label>
												<input class="width-100" type="text" name="userTel" id="userTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>주(영문)</label>
												<input class="width-100" type="text" numberOnly name="userEState" id="userEState" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>도시(영문)</label>
												<input class="width-100" type="text" numberOnly name="userECity" id="userECity" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" name="userEAddr" id="userEAddr" value="" />
												<label class="bigger-110">상세 주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" name="userEAddrDetail" placeholder="상세 주소(영문)" id="userEAddrDetail" value="" />
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
												<button type="button" id="addIp" name="addIp" class="btn btn-primary btn-sm btn-white">추가</button>
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
												<input class="width-100" type="text" name="invComName" id="invComName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>업체 전화번호</label>
												<input class="width-100" type="text" name="invComTel" id="invComTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>담당자명</label>
												<input class="width-100" type="text" name="invUserName" id="invUserName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>담당자 전화번호</label>
												<input class="width-100" type="text" name="invUserTel" id="invUserTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>청구지 Email</label>
												<input class="width-100" type="text" name="invUserEmail" id="invUserEmail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">청구지 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddr" id="invComAddr" value="" />
												<label class="bigger-110">청구지 상세 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddrDetail" id="invComAddrDetail" value="" />
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
														<input name="etprYn" type="radio" class="ace" value="Y" <c:if test="${userInfo.etprYn eq 'Y'}"> checked</c:if>/>
														<span class="lbl"> 기업</span>
													</label>
													<label>
														<input name="etprYn" type="radio" class="ace" value="N" <c:if test="${userInfo.etprYn eq 'N'}"> checked</c:if>/>
														<span class="lbl"> 개인</span>
													</label>
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
													<input class="width-100" type="text" name="userGrade" id="userGrade" readonly value="" />
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >검품 비용</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="inspPrice" id="inspPrice" value="" />
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >무게 오차범위</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="wtErrRange" id="wtErrRange" value="" />
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
														<c:forEach items="${orgNations}" var="nations">
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
							
								<!-- 설정정보 End -->
								
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
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>

	<!-- script on paging start -->

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
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		var idCheck = "N";
		var pwCheck = "N";
		jQuery(function($) {
			$(document).ready(function() {
				$("#zeroMenu").toggleClass('open');
				$("#zeroMenu").toggleClass('active');
				if($("#targets").text().trim() == "기업 회원"){
					$("#zeroOne").toggleClass('active');
				}else{
					$("#zeroTwo").toggleClass('active');
				}
			});

			/* $('#zipBtn').on('click', function(e){
				window

	
			}) */
			
			$('#backToList').on('click', function(e) {
				location.href = '/mngr/acnt/userList';
			
			});	

			$('#rgstrInfo').on('click', function(e) {
				/**
				1. ID  -  중복 -> 등록시 확인, 코드 부여
				2. 사용자명 - 입력 여부
				3. 패스워드 자리수  - 8자리 이상 
				4. 패스워드 유효성  - 영문+숫자
				5. 우편 주소 - 카카오인가 뭔가 갖다 쓰기
				6. 주소 - 우편주소 입력 시, 자동으로 입력
				7. 상세 주소 - 본인이 입력
				+ email
				+ 전화번호
				8. 예치금 - 기본 N
				9. 검품메뉴 - 기본 N
				10. 사입메뉴 - 기본 N
				11. 회원 등급 - DISABLED
				12. 검품 비용 - ??
				13. 무게 오차범위 -??
 				14. 주 거래 국가, 택배회사 - 최소 1개 이상
				15. 승인 여부 - 기본 N
				**/

				if($("#userId").val() == ""){
					/* var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
					regExpPw.test($('#password1').val()); */
					alert("아이디를 입력해 주세요");
					$("#userId").focus();
					return false;
				}

				if(idCheck == "N"){
					alert("ID 중복확인을 해 주세요")
					$("#userId").focus();
					return false;
				}

				if($("#userName").val() == ""){
					alert("사용자 명을 입력해 주세요");
					$("#userName").focus();
					return false;
				}

				if(pwCheck == "N"){
					alert("패스워드를 확인해 주세요");
					return false;
				}
				
				if($('#password1').val() != $('#password2').val()){
					alert("패스워드를 확인해 주세요");
					$('#password1').focus();
					return false;
				}


				if($("#comName").val() ==""){
					alert("상호명을 확인해 주세요");
					$("#comName").focus();
					return false;
				}
 
				if($("#comEName").val() ==""){
					alert("상호명(영문)을 확인해 주세요");
					$("#comEName").focus();
					return false;
				}

				if($("#userZip").val() ==""){
					alert("우편번호를 확인해 주세요");
					$("#userZip").focus();
					return false;
				}

				/* if($("#userAddr").val() ==""){
					alert("주소를 확인해 주세요");
					$("#userAddr").focus();
					return false;
				}

				if($("#userAddrDetail").val() ==""){
					alert("상세주소를 확인해 주세요");
					$("#userAddrDetail").focus();
					return false;
				} */

				if($("#userEAddr").val() ==""){
					alert("주소(영문)를 확인해 주세요");
					$("#userEAddr").focus();
					return false;
				}

				if($("#userEAddrDetail").val() ==""){
					alert("상세주소(영문)를 확인해 주세요");
					$("#userEAddrDetail").focus();
					return false;
				} 
				
				if($('#userEmail').val() == ""){
					alert("Email을 확인해 주세요");
					$('#userEmail').focus();
					return false;
				}else{
					var regExpMail = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/;
					/*
					if(!regExpMail.test($('#userEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#userEmail').focus();
						return false;
					}
					*/
				}

				

				if($('#userTel').val() == ""){
					alert("전화번호를 확인해 주세요");
					$('#userTel').focus();
					return false;
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
					alert("담당잦 명을 확인해 주세요");
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
					var regExpMail = /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
					if(!regExpMail.test($('#invUserEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#invUserEmail').focus();
						return false;
					}
					*/
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

				if($('input[name="etprYn"]:checked').length == 0){
					alert("기업/개인을 선택 해 확인해 주세요");
					$('input[name="etprYn"]').focus();
					return false;
				}

				if($("#orgNation").val()==null){
					alert("출발지 국가를 선택해 주세요");
					$("#orgNation").focus();
					return false
				}

				if($("#dstnNation").val()==null){
					alert("도착지 국가를 선택해 주세요");
					$("#dstnNation").focus();
					return false
				}

				var transComsRtn = false;
				

				var str = "";
				$("input:checkbox:checked").each(function (index) {  
			        str += $(this).val() + ",";  
			    });  

				$('#trkComList').val(str);
				
				$('#userPw').val($('#password1').val());

				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/mngr/acnt/regist',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("등록 되었습니다.");
							location.href="/mngr/acnt/userList";

						}else{
							alert("등록중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});
			
			$('input[id*="password"]').on('change',function(){
				var regExpPw = /(?=.*\d{1,20})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$/;
				regExpPw.test($('#password1').val());
				/* if(){
				} */

				
				if($('#password1').val()=="" || $('#password2').val()== ""){
					$('#passwordMsg').text('');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').removeClass('blue');
					pwCheck = "N";
					return false;
				}
				if($('#password1').val() == $('#password2').val()){
					if(!regExpPw.test($('#password1').val())){
						alert("숫자, 특수문자, 영어 조합 8자리 이상을 입력해 주세요.");
						$('#passwordMsg').text('유효하지 않은 패스워드 입니다.');
						$('#passwordMsg').removeClass('blue');
						$('#passwordMsg').addClass('red');
						pwCheck="N";
						return false;
					}
					$('#passwordMsg').text('유효한 패스워드 입니다.');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').addClass('blue');
					pwCheck= "Y";
				}else{
					$('#passwordMsg').text('패스워드가 다릅니다.');
					$('#passwordMsg').removeClass('blue');
					$('#passwordMsg').addClass('red');
					pwCheck="N";
				}
			})

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

			/* $('.chosen-select').on('change',function(){
				var str = [];
				$("input:checkbox:checked").each(function (index) {  
			        str.push($(this).text());  
			    });  
				$('#trkComCheckList').val(str);
				var str2 = [];
				$("input:checkbox:checked").each(function (index) {  
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
					data: formData,
					success : function(data) {
						$('#trackComs').html(data);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("택배회사 불러오기에 실패 하였습니다.");
		            }
				})
			});  */

			$("#checkId").on('click',function(e){
				if($("#userId").val() == ""){
					alert("아이디를 입력해 주세요");
					return;
				}
				var formData = $("#userInfoForm").serialize();
				$.ajax({
					url:'/comn/idCntCheck',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("사용 가능한 아이디 입니다.");
							idCheck = "Y";
						}else{
							alert("중복된 아이디 입니다.");
							$("#userId").val("");
							idCheck = "N";
							$("#userId").focus();
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			})
		})
	</script>
	<!-- script addon end -->
</body>
</html>