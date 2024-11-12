<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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

.translation-links {
	display: flex;
	flex-direction: row;
	list-style: none;
}

.translation-links a {
	margin-left: 20px;
}

.english {
	margin-left: 0px !important;
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
				<input type="hidden" id="current-language" value="${nowLocale}"/>
				<input type="hidden" id="orgNation" name="orgNation" value="KR" />
				<div class="main-content-inner">
					<div class="page-content">
						<ul class="translation-links">
			                <li><a href="/returnSignUp?lang=en" class="english" data-lang="en" title="English"><span class="flag en"></span><span>English</span></a></li>
			                <li><a href="/returnSignUp?lang=ko" class="korean" data-lang="ko" title="Korean"><span class="flag ko"></span><span>Korean</span></a></li>
			                <li><a href="/returnSignUp?lang=ja" class="japanese" data-lang="ko" title="Japanese"><span class="flag ja"></span><span>Japanese</span></a></li>
			                <li><a href="/returnSignUp?lang=zh" class="chinese" data-lang="zh" title="chinese"><span class="flag zh"></span><span>Chinese (traditional)</span></a></li>
			            </ul>
						

						<div id="inner-content-side">
							<!-- 기본정보 Start -->
							<br/>
							<div class="row">
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<spring:message code="signup.noticeText.part1" text="To apply for this service, you must first create an ACI Customer account with ACI." /><br/>
									<spring:message code="signup.noticeText.part2" text="Please provide the required information using the form below." /><br/>
									<spring:message code="signup.noticeText.part3" text="Should you have any questions or difficulties completing this application, please contact gs3row@coupang.onmicrosoft.com for assistance." />
								</div>
							</div>
							<br/>
							<div class="row">
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>ACI Seller Account Creation</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
									<!-- 	<div class="row" style="text-align:right;">
											<small class="red">*</small><small style="font-size:12px;"> 는 필수 입력값입니다</small>
										</div> -->
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userId" text="Account ID"/></label><br>
												<input class="width-70" type="text" name="userId" id="userId" value="" />&nbsp;&nbsp;&nbsp;
												<button id="checkId" class="btn btn-primary btn-sm" name="checkId" type="button"><spring:message code="signup.duplCheck" text="Verify ID"/></button>
												<div class="row" style="margin-left:8px; margin-top:5px;">
													<small class="blue">
														<spring:message code="signup.userId.tooltip.part1" text="Your Account ID must begin with an English letter." /><br/>
														<spring:message code="signup.userId.tooltip.part2" text="Account IDs must be between 6 and 20 characters, including both letters and numbers" />
													</small>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.password" text="password"/></label>
												<input class="width-100" type="password" name="password1" id="password1" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.passwordConfirm" text="Confirm Password"/></label>
												<input class="width-100" type="password" name="password2" id="password2" value="" />
												<!-- <span class="" style="display:block;" id="passwordMsg"></span> -->
											</div>
											
										</div>
										<div class="row form-group hr-8">
											<div class="col-sm-6 col-xs-5">
												<small class="blue" style="margin-left:8px;" id="passwordMsg"><spring:message code="signup.password.tooltip" text="Password must include numbers, letters, special characters (more than 8 chars)" /></small>
											</div>
											<div class="col-lg-6 col-xs-12">
												<input type="hidden" name="userPw" id="userPw"/>
											</div>
											
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label><spring:message code="signup.comName" text="Company Name"/></label>
												<input class="width-100" type="text" name="userName" id="userName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label><spring:message code="signup.userName" text="Main point of Contact"/></label>
												<input class="width-100" type="text" name="comName" id="comName" value="" />
											</div>
										</div> 
										<div class="row form-group hr-8">
											
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userEmail" text="Contact Email"/></label>
												<input class="width-100" type="text" name="userEmail" id="userEmail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110"><spring:message code="signup.userZip" text="Postal Code"/></label><br/>
												<input type="text" class="width-30 hr-8" name="userZip" id="userZip" value="" /> 
												<!-- <button style="margin-left:10px;" id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
													<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
												</button> -->
												<br>
												<label class="bigger-110"><spring:message code="signup.userAddr" text="Address 1"/></label><br/>
												<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="" />
												<label class="bigger-110"><spring:message code="signup.userAddrDetail" text="Address 2"/></label><br/>
												<input type="text" class="width-100 hr-8" name="userAddrDetail" id="userAddrDetail" value="" />
											</div>
										</div>
										
									</div>
								</div>
								<!-- 기본정보 End -->
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2><spring:message code="signup.progress2title.part1" text="Addition Inputs Required for"/> <spring:message code="signup.progress2title.part2" text="Customs Declaration / Customs Clearance"/></h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										※ <spring:message code="signup.progress2subtitle" text="Please fill it out in English"/>
										<div style="width:100%;text-align:right;">
										<input type="checkbox" id="checkAutoFill2">
										<label for="checkAutoFill2" class="auto-fill-label"><spring:message code="signup.checkAutoFill" text="apply as return information entered"/></label>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.comName" text="Company Name"/></label>
												<input class="width-100" type="text" engOnly name="comEName" id="comEName" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userHp" text="Contact Number (Mobile)"/></label>
												<input class="width-100" type="text" numberOnly name="userHp" id="userHp" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userTel" text="Contact Number"/></label>
												<input class="width-100" type="text" numberOnly name="userTel" id="userTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label><spring:message code="signup.userEState" text="State"/></label>
												<input class="width-100" type="text" name="userEState" id="userEState" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label><spring:message code="signup.userECity" text="City"/></label>
												<input class="width-100" type="text" name="userECity" id="userECity" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label class="bigger-110"><spring:message code="signup.userAddr" text="Address 1"/></label><br/>
												<input type="text" class="width-100 hr-8" engOnly name="userEAddr" id="userEAddr" value="" />
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label class="bigger-110"><spring:message code="signup.userAddrDetail" text="Address 2"/></label><br/>
												<input type="text" class="width-100 hr-8" engOnly name="userEAddrDetail" id="userEAddrDetail" value="" />
											</div>
										</div>
									</div>
								</div>
								<!-- 세관신고 정보 End -->
								<div class="col-xs-12 col-lg-6 col-lg-offset-3">
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2><spring:message code="signup.progress3title" text="Enter Billing Information"/></h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div style="width:100%;text-align:right;">
										<input type="checkbox" id="checkAutoFill3">
										<label for="checkAutoFill3" class="auto-fill-label"><spring:message code="signup.checkAutoFill" text="apply as return information entered"/></label>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.comName" text="Company Name"/></label>
												<input class="width-100" type="text" name="invComName" id="invComName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.invUserTel" text="Company Representative Phone"/></label>
												<input class="width-100" numberOnly type="text" name="invComTel" id="invComTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userName" text="Main point of Contact"/></label>
												<input class="width-100" type="text" name="invUserName" id="invUserName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userTel" text="Contact Number"/></label>
												<input class="width-100" type="text" numberOnly name="invUserTel" id="invUserTel" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userEmail" text="Contact Email"/></label>
												<input class="width-100" type="text" name="invUserEmail" id="invUserEmail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label class="bigger-110"><spring:message code="signup.userAddr" text="Address 1"/></label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddr" id="invComAddr" value="" />
												<!-- <label class="red" style="font-size:15pt;">*&nbsp;</label> --><label class="bigger-110"><spring:message code="signup.userAddrDetail" text="Address 2"/></label><br/>
												<input type="text" class="width-100 hr-8" name="invComAddrDetail" id="invComAddrDetail" value="" />
											</div>
										</div>
										
									</div>
								</div>
								<!-- 설정정보 Start -->
								
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>Return Information</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.userType" text="Corporate / Individual"/></label>
												<div class="radio">
													<label>
														<input name="etprYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> <spring:message code="signup.userType.corporate" text="Corporate"/></span>
													</label>
													<label>
														<input name="etprYn" type="radio" class="ace" value="N"/>
														<span class="lbl"> <spring:message code="signup.userType.individual" text="Individual"/></span>
													</label>
												</div>
											</div>
										</div>
										<div class="row form-group hr-8" id="storeDiv" name="storeDiv" style="display:none;">
											<div class="col-lg-6 col-xs-12">
												<label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.storeName" text="storeName"/></label>
												<input class="width-100" type="text" name="storeName" id="storeName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label><spring:message code="signup.storeUrl" text="eCommerce website URL"/></label>
												<input class="width-100" type="text" name="storeUrl" id="storeUrl" value="" />
											</div>
										</div>
										<br/>
										<div class="row form-group hr-8">
											<%-- <div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-3 col-xs-12 pd-1rem" ><label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.orgStation" text="Country of Departure"/></label></div>
													<div class="col-lg-6 col-xs-12 pd-1rem">
														<select class="form-control tag-input-style width-100 pd-1rem" id="orgNation" name="orgNation" 
														data-placeholder="국가를 선택해 주세요" onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect;'>
															<c:forEach items="${orgNations}" var="nations">
																<option value="${nations.nationCode}"<c:if test="${nations.nationCode eq 'KR'}"> selected</c:if>>${nations.nationEName}(${nations.nationName})</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div> --%>
											<div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-4 col-xs-12 pd-1rem" ><label class="red" style="font-size:15pt;">*&nbsp;</label><label><spring:message code="signup.dstnNation" text="Return Destination Country"/></label></div>
													<div class="col-lg-8 col-xs-12 pd-1rem">
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
											 <%-- 
											<div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<div class="col-lg-12 col-xs-12 pd-1rem" ><label class="red" style="font-size:15pt;">*&nbsp;</label><label>반품 회수지</label></div>
													<div class="col-lg-3 col-xs-12 pd-1rem">
														<select class="width-100" id="orgStation" name="orgStation" data-placeholder="">
															<option value="">select option</option>
															<c:forEach items="${orgStations}" var="nations">
																<option value="${nations.stationName}">${nations.stationName}</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-lg-6 col-xs-12 pd-1rem" id="return-station">
														
													</div>
												</div>
											</div>
											 --%>
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
								<input type="hidden" name="returnStation" id="returnStation" />
								<input type="hidden" name="returnAddr" id="returnAddr" />
								<div class="col-lg-12 col-xs-12">
								<br />
									<div class="col-lg-1 col-xs-6 col-lg-offset-5">
										<div id="button-div">
											<div id="backPage" style="text-align: center">
												<button id="backToList" type="button" class="btn btn-sm btn-danger">Cancel</button>
											</div>
										</div>
									</div>
									<div class="col-lg-1 col-xs-6">
										<div id="button-div">
											<div id="rgstrUser" style="text-align: center">
												<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">Register</button>
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
		var currentLang = $("#current-language").val();
		
		jQuery(function($) {
			$(document).ready(function() {
				console.log(currentLang);
				$("#zeroMenu").toggleClass('open');
				$("#zeroMenu").toggleClass('active');
				if($("#targets").text().trim() == "기업 회원"){
					$("#zeroOne").toggleClass('active');
				}else{
					$("#zeroTwo").toggleClass('active');
				}

				$(".goog-logo-link").empty();
			    $('.goog-te-gadget').html($('.goog-te-gadget').children());
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
			            alert("system error occured");
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
				
			});


			$("#checkAutoFill2").click(function() {
				var checked = $("#checkAutoFill2").is(":checked");
				if (checked) {
					$("#comEName").val($("#comName").val());
					$("#userEAddr").val($("#userAddr").val());
					$("#userEAddrDetail").val($("#userAddrDetail").val());
				} 
			});

			$("#checkAutoFill3").click(function() {
				var checked = $("#checkAutoFill3").is(":checked");
				if (checked) {
					$("#invComName").val($("#comName").val());
					$("#invComTel").val($("#userTel").val());
					$("#invUserName").val($("#userName").val());
					$("#invUserTel").val($("#userTel").val());
					$("#invUserEmail").val($("#userEmail").val());
					$("#invComAddr").val($("#userAddr").val());
					$("#invComAddrDetail").val($("#userAddrDetail").val());
				} 
			});

			$('#rgstrInfo').on('click', function(e) {

				$("#returnStation").val($("#orgStation").val());
				$("#returnAddr").val($("#stationIdx").val());


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
				var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
				regExpPw.test($('#password1').val());
				**/

/* 				if($("#userId").val() == ""){
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
					var regExpMail = /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
					if(!regExpMail.test($('#invUserEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#invUserEmail').focus();
						return false;
					}
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
				} */

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
				/* var transComsRtn = false;
				
				for(var i = 0; i < $("#trackComs").find('input').length; i ++){
					if($($("#trackComs").find('input').get(i)).prop('checked')){
						transComsRtn = true;
						break;
					}
					transComsRtn = false;
				} */

				/* if(!transComsRtn){
					alert("택배회사를 선택해 주세요");
					return false;
				} */

				/* var str = "";
				$("input:checkbox:checked").each(function (index) {  
			        str += $(this).val() + ",";  
			    });  

				$('#trkComList').val(str); */

				var alertText1 = "";
				var alertText2 = "";
				var alertText3 = "";
				var alertText4 = "";
				var alertText5 = "";

				if (currentLang == "ko") {
					alertText1 = "ID 중복체크를 진행해 주세요";
					alertText2 = "패스워드를 확인해 주세요";
					alertText3 = "Email을 확인해 주세요";
					alertText4 = "등록 되었습니다";
					alertText5 = "등록 중 오류가 발생하였습니다. 시스템 관리자에게 문의해 주세요.";
				} else if (currentLang == "en"){
					alertText1 = "Please proceed with ID verification";
					alertText2 = "Please confirm the password";
					alertText3 = "Please check the e-mail";
					alertText4 = "Registration has been completed";
					alertText5 = "An error occurred during registration. Please contact the system administrator.";
				}

				if(idCheck == "N"){
					alert(alertText1);
					$("#userId").focus();
					return false;
				}


				if(pwCheck == "N"){
					alert(alertText2);
					return false;
				}
				
				if($('#password1').val() != $('#password2').val()){
					alert(alertText2);
					$('#password1').focus();
					return false;
				}
				
				if($('#userEmail').val() == ""){
					alert(alertText3);
					$('#userEmail').focus();
					return false;
				}
				
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
							alert(alertText4);
							location.href="/comn/login";
						}else{
							alert(alertText5);
						}
		            }, 		    
		            error : function(xhr, status) {
			            alert("system error occured");
		            }
				})
			});

			$('input[id*="password"]').on('change',function(){
				var regExpPw = /(?=.*\d{1,20})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$/;

				var alertText1 = "";
				var alertText2 = "";
				var alertText3 = "";
				var alertText4 = "";

				if (currentLang == "ko") {
					alertText1 = "숫자, 특수문자, 영어 조합 8자리 이상을 입력해 주세요";
					alertText2 = "유효하지 않은 패스워드 입니다";
					alertText3 = "유효한 패스워드 입니다";
					alertText4 = "패스워드가 다릅니다";
				} else if (currentLang == "en"){
					alertText1 = "Please enter at least 8 digits of a combination of numbers, special characters, and English";
					alertText2 = "Invalid password";
					alertText3 = "Valid password";
					alertText4 = "The password is different";
				}
				
				if($('#password1').val()=="" || $('#password2').val()== ""){
					$('#passwordMsg').text('');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').removeClass('blue');
					pwCheck = "N";
					return false;
				}
				if($('#password1').val() == $('#password2').val()){
					if(!regExpPw.test($('#password1').val())){
						alert(alertText1);
						$('#passwordMsg').text(alertText2);
						$('#passwordMsg').removeClass('blue');
						$('#passwordMsg').addClass('red');
						pwCheck="N";
						return false;
					}
					$('#passwordMsg').text(alertText3);
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').addClass('blue');
					pwCheck= "Y";
				}else{
					$('#passwordMsg').text(alertText4);
					$('#passwordMsg').removeClass('blue');
					$('#passwordMsg').addClass('red');
					pwCheck="N";
				}
			})

			$("#checkId").on('click',function(e){
				var alertText1 = "";
				var alertText2 = "";
				var alertText3 = "";
				var alertText4 = "";

				if (currentLang == "ko") {
					alertText1 = "아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다";
					alertText2 = "아이디를 입력해 주세요";
					alertText3 = "사용 가능한 아이디 입니다";
					alertText4 = "중복된 아이디 입니다";
				} else if (currentLang == "en"){
					alertText1 = "Your Account ID must begin with an English letter. Account IDs must be between 6 and 20 characters, including both letters and numbers";
					alertText2 = "Please enter your Account ID";
					alertText3 = "The Account ID is available";
					alertText4 = "The Account ID is already taken";
				}
				
				var idReg = /^[a-zA-Z]+[a-zA-Z0-9]{5,19}$/g;
				if(!idReg.test($('#userId').val())){
					alert(alertText1);
					return false;
				}

				
				if($("#userId").val() == ""){
					alert(alertText2);
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
							alert(alertText3);
							idCheck = "Y";
							return true;
						}else{
							alert(alertText4);
							$("#userId").val("");
							idCheck = "N";
							$("#userId").focus();
							return true;
						}
		            }, 		    
		            error : function(xhr, status) {
			            alert("system error occured");
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
		
		$("#orgStation").on('change', function(e) {
			var orgStation = $("#orgStation").val();

			var datass = {orgStation: orgStation};
			
			$.ajax({
				url : '/returnSignUp',
				type : 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : datass,
				success : function(data) {
					console.log(data);
					var html = "";
					
					if (data.result == "S") {
						var values = [];
						values = data.returnStations;

						if (values.length > 0) {
							html += '<select name="stationIdx" id="stationIdx">';
							for (var i = 0; i < values.length; i++) {
								console.log(values.length);
								html += '<option ';
								if (values[i].IDX == $("#stationIdx").val()) {
									html += ' selected';
								}
								html += 'value="'+values[i].STATION_ADDR+' '+values[i].STATION_ADDR_DETAIL+'">'+values[i].STATION_ADDR+' '+values[i].STATION_ADDR_DETAIL+'</option>';
						}
							html += '</select>';
						} else {
							html += '<select name="stationIdx" id="stationIdx">';
							html += '<option value="">회수지 정보가 존재하지 않습니다</option>';
							html += '</select>';
						}
					
						$("#return-station").empty();
						$("#return-station").append(html);
						
					} else {
						alert("데이터 조회에 실패하였습니다.");
					}
					
				},
				error : function(xhr, status) {
					console.log(xhr + " : " + status);
				}
			})
		})
		
		
	</script>
	<!-- script addon end -->
</body>
</html>