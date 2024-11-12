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
<title>회원 가입</title>
<body class="no-skin">
	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<div class="main-content">
			<form name="userInfoForm" id="userInfoForm" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">
					<div class="page-content">
						<div class="page-header">
							<h1>회원 가입</h1>
						</div>
						<div id="inner-content-side">
							<!-- 기본정보 Start -->
							<div class="row">
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>기본정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>아이디</label><br>
												<input class="width-70" type="text" name="userId" id="userId" value="" />&nbsp;&nbsp;&nbsp;
												<button id="checkId" class="btn btn-primary btn-sm" name="checkId" type="button">중복확인</button>
												<div class="row" style="margin-left:8px; margin-top:5px;">
													<small class="blue">아이디는 영문으로 시작하며 영문 또는 숫자를 포함한 6~20자리여야 합니다.</small>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>패스워드</label>
												<input class="width-100" type="password" name="password1" id="password1" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>패스워드 확인</label>
												<input class="width-100" type="password" name="password2" id="password2" value="" />
												<span class="" style="display:block;" id="passwordMsg"></span>
											</div>
											
										</div>
										<div class="row form-group hr-8">
											<div class="col-sm-6 col-xs-5">
												<span class="blue">숫자/영어/특수문자를 조합하여 8자리 이상으로 구성해 주세요</span>
											</div>
											<div class="col-lg-6 col-xs-12">
												<input type="hidden" name="userPw" id="userPw"/>
											</div>
											
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>담당자 명</label>
												<input class="width-100" type="text" name="userName" id="userName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>상호명</label>
												<input class="width-100" type="text" name="comName" id="comName" value="" />
											</div>
										</div> 
										<div class="row form-group hr-8">
											
											<div class="col-lg-6 col-xs-12">
												<label>Email</label>
												<input class="width-100" type="text" name="userEmail" id="userEmail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">우편번호</label><br/>
												<input type="text" class="width-30 hr-8" name="userZip" id="userZip" value="" /> 
												<button style="margin-left:10px;" id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
													<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
												</button>
												<br>
												<label class="bigger-110">주소</label><br/>
												<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="" />
												<label class="bigger-110">상세 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="" />
											</div>
										</div>
										
									</div>
								</div>
								<!-- 기본정보 End -->
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>세관 신고 정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>이름 또는 상호명(영문)</label>
												<input class="width-100" type="text" engOnly name="comEName" id="comEName" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>휴대전화번호</label>
												<input class="width-100" type="text" numberOnly name="userHp" id="userHp" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>전화번호</label>
												<input class="width-100" type="text" numberOnly name="userTel" id="userTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>주(영문)</label>
												<input class="width-100" type="text" name="userEState" id="userEState" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>도시(영문)</label>
												<input class="width-100" type="text" name="userECity" id="userECity" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label class="bigger-110">주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" engOnly name="userEAddr" id="userEAddr" value="" />
												<label class="red" style="font-size:15pt;">*</label><label class="bigger-110">상세 주소(영문)</label><br/>
												<input type="text" class="width-100 hr-8" engOnly name="userEAddrDetail" placeholder="상세 주소(영문)" id="userEAddrDetail" value="" />
											</div>
										</div>
									</div>
								</div>
								<!-- 세관신고 정보 End -->
								<div class="col-xs-12 col-lg-6 col-lg-offset-3">
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>청구지 정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>업체명</label>
												<input class="width-100" type="text" name="invComName" id="invComName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>대표 전화번호</label>
												<input class="width-100" numberOnly type="text" name="invComTel" id="invComTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>담당자명</label>
												<input class="width-100" type="text" name="invUserName" id="invUserName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>담당자 전화번호</label>
												<input class="width-100" type="text" numberOnly name="invUserTel" id="invUserTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>청구지 Email</label>
												<input class="width-100" type="text" name="invUserEmail" id="invUserEmail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label class="bigger-110">청구지 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddr" id="invComAddr" value="" />
												<label class="red" style="font-size:15pt;">*</label><label class="bigger-110">청구지 상세 주소</label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddrDetail" id="invComAddrDetail" value="" />
											</div>
										</div>
										
									</div>
								</div>
								<!-- 설정정보 Start -->
								
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>설정정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>기업/개인</label>
												<div class="radio">
													<label>
														<input name="etprYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> 기업</span>
													</label>
													<label>
														<input name="etprYn" type="radio" class="ace" value="N"/>
														<span class="lbl"> 개인</span>
													</label>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8" id="storeDiv" name="storeDiv" style="display:none;">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*</label><label>상점명</label>
												<input class="width-100" type="text" name="storeName" id="storeName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>쇼핑몰 URL</label>
												<input class="width-100" type="text" name="storeUrl" id="storeUrl" value="" />
											</div>
										</div>
										<br/>
										<div class="row form-group hr-8">
											<div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-3 col-xs-12 pd-1rem" ><label class="red" style="font-size:15pt;">*</label><label>출발지</label></div>
													<div class="col-lg-6 col-xs-12 pd-1rem">
														<select class="chosen-select form-control tag-input-style width-100 pd-1rem" id="orgNation" name="orgNation" data-placeholder="국가를 선택해 주세요">
															<option value="">  </option>
															<c:forEach items="${orgNations}" var="nations">
																<option value="${nations.nationCode}">${nations.nationEName}(${nations.nationName})</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-3 col-xs-12 pd-1rem" ><label class="red" style="font-size:15pt;">*</label><label>도착지</label></div>
													<div class="col-lg-6 col-xs-12 pd-1rem">
														<select multiple class="chosen-select form-control tag-input-style width-100 pd-1rem" id="dstnNation" name="dstnNation" data-placeholder="국가를 선택해 주세요">
															<option value="">  </option>
															<c:forEach items="${nations}" var="nations">
																<option value="${nations.nationCode}">${nations.nationEName}(${nations.nationName})</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<br/>
											<!-- <div class="row form-group hr-8">
												<div class="col-lg-6 col-xs-12">
													<label>Eshop ID</label>
													<input class="width-100" type="text" name="eshopId" id="eshopId" value="" />
												</div>
												<div class="col-lg-6 col-xs-12">
													<label>Eshop API Key</label>
													<input class="width-100" type="text" name="eshopApiKey" id="eshopApiKey" value="" />
												</div>
											</div> -->
											<%-- <div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-3 col-xs-12 pd-1rem" >택배 회사</div>
													<div class="col-lg-6 col-xs-12" name="trackComs" id="trackComs">
														
													</div>
												</div>
												<input type="hidden" id="trkComList" name="trkComList" value="${userInfo.trkComList}">
												<input type="hidden" id="trkComValueList" name="trkComValueList" >
											</div> --%>
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
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
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
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>

	<script src="/assets/js/chosen.jquery.min2.js"></script>
	
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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	
	<!-- script on paging end -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
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

			$('.chosen-select').on('change',function(){
				/* alert($('.chosen-select').val()); */
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
					url:'/comn/myPage/transCom',
					type: 'GET',
					data:formData,
					success : function(data) {
						$('#trackComs').html(data);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}); 
			
			$('#backToList').on('click', function(e) {
				location.href="/kind/user";
			});	

			$('input[name="etprYn"]').on('change', function(e) {
				if($('input[name="etprYn"]:checked').val() == "Y"){
					$("#storeDiv").show();
				}else{
					$("#storeDiv").hide();
				}
				
			})

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


				if(pwCheck == "N"){
					alert("패스워드를 확인해 주세요");
					return false;
				}
				
				if($('#password1').val() != $('#password2').val()){
					alert("패스워드를 확인해 주세요");
					$('#password1').focus();
					return false;
				}


				if($("#comEName").val() ==""){ 
					alert("세관 신고 정보의 상호명(영문)을 확인해 주세요");
					$("#comEName").focus();
					return false;
				}

				if($('#userHp').val() == ""){
					alert("세관 신고 정보의 휴대전화번호를 확인해 주세요");
					$('#userHp').focus();
					return false;
				}

				if($('#userTel').val() == ""){
					alert("세관 신고 정보의 전화번호를 확인해 주세요");
					$('#userTel').focus();
					return false;
				}

				if($('#userTel').val() == $('#userHp').val()){
					alert("세관 신고 정보의 전화번호와 휴대전화 번호가 동일합니다.");
					$('#userHp').focus();
					return false;
				}

				if($("#userEAddr").val() ==""){
					alert("세관 신고 정보의 주소(영문)를 확인해 주세요");
					$("#userEAddr").focus();
					return false;
				}

				if($("#userEAddrDetail").val() ==""){
					alert("세관 신고 정보의 상세주소(영문)를 확인해 주세요");
					$("#userEAddrDetail").focus();
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
					alert("청구지 담당자 명을 확인해 주세요");
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
				
				if($('input[name="etprYn"]:checked').length == 0){
					alert("기업/개인을 선택 해 확인해 주세요");
					$('input[name="etprYn"]').focus();
					return false;
				}

				if($('input[name="etprYn"]:checked').val() == "Y"){
					if($("#storeUrl").val() ==""){ 
						alert("쇼핑몰 URL을 확인해 주세요");
						$("#storeUrl").focus();
						return false;
					}
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


				

				

				


				

			/* 	if($("#comName").val() ==""){
					alert("상호명을 확인해 주세요");
					$("#comName").focus();
					return false;
				}

				

				if($("#userZip").val() ==""){
					alert("우편번호를 확인해 주세요");
					$("#userZip").focus();
					return false;
				} */

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

				
				
				/* if($('#userEmail').val() == ""){
					alert("Email을 확인해 주세요");
					$('#userEmail').focus();
					return false;
				}else{
					var regExpMail = /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
					if(!regExpMail.test($('#userEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#userEmail').focus();
						return false;
					}
				}
 */
				

				var transComsRtn = false;
				
				for(var i = 0; i < $("#trackComs").find('input').length; i ++){
					if($($("#trackComs").find('input').get(i)).prop('checked')){
						transComsRtn = true;
						break;
					}
					transComsRtn = false;
				}

				/* if(!transComsRtn){
					alert("택배회사를 선택해 주세요");
					return false;
				} */

				/* var str = "";
				$("input:checkbox:checked").each(function (index) {  
			        str += $(this).val() + ",";  
			    });  

				$('#trkComList').val(str); */
				
				$('#userPw').val($('#password1').val());
				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/comn/registUser',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("등록 되었습니다.");
							location.href="/comn/login";
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

			$("#checkId").on('click',function(e){
				var idReg = /^[a-zA-Z]+[a-zA-Z0-9]{5,19}$/g;
				if(!idReg.test($('#userId').val())){
					alert("아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.");
					return false;
				}

				
				if($("#userId").val() == ""){
					alert("아이디를 입력해 주세요");
					return false;
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
							return true;
						}else{
							alert("중복된 아이디 입니다.");
							$("#userId").val("");
							idCheck = "N";
							$("#userId").focus();
							return true;
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			})
		})
		
		$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });

		$("input:text[engOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,""));
            
        });
		$("input:text[engOnly]").on("change", function() {
            $(this).val($(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,""));
            
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

		$("#zipBtn").on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					var fullRoadAddr = data.roadAddress;
					var extraRoadAddr = "";

					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraRoadAddr += data.bname;
					}

					if(data.buildingName !== '' && data.apartment === 'Y'){
		                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		                }
		                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		                if(extraRoadAddr !== ''){
		                    extraRoadAddr = ' (' + extraRoadAddr + ')';
		                }
		                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
		                if(fullRoadAddr !== ''){
		                    fullRoadAddr += extraRoadAddr;
		                }
		                
		                $("#userZip").val(data.zonecode);
		                $("#userAddr").val(fullRoadAddr);
				}
			}).open();
		})
		
	</script>
	<!-- script addon end -->
</body>
</html>