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
<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
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
								<div class="col-xs-12 col-lg-4 col-lg-offset-2" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>기본정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>아이디</label>
												<input class="width-100" type="text" name="userId" id="userId" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>사용자 명</label>
												<input class="width-100" type="text" name="userName" id="userName" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>패스워드</label>
												<input class="width-100" type="password" name="password1" id="password1" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-8 col-xs-12">
												<label>패스워드 확인</label>
												<input class="width-100" type="password" name="password2" id="password2" value="" />
												<span class="" style="display:block;" id="passwordMsg"></span>
											</div>
										</div>
										
										<div class="row form-group">
											<input type="hidden" name="userPw" id="userPw"/>
										</div>
										
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label class="bigger-110">주소</label><br/>
												<input type="text" class="width-30 hr-8" name="userZip" id="userZip" value="" /> 
												<button id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
													<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
												</button>
												<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="" />
												<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="" />
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>Email</label>
												<input class="width-100" type="text" name="userEmail" id="userEmail" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>전화번호</label>
												<input class="width-100" type="text" name="userTel" id="userTel" value="" />
											</div>
										</div>
									</div>
								</div>
								<!-- 기본정보 End -->
								<!-- 설정정보 Start -->
								<div class="col-xs-12 col-lg-4" >
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
														<input name="etprYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> 기업</span>
													</label>
													<label>
														<input name="etprYn" type="radio" class="ace" value="N"/>
														<span class="lbl"> 개인</span>
													</label>
												</div>
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>예치금 사용여부</label>
												<div class="radio">
													<label>
														<input name="depositYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="depositYn" type="radio" checked class="ace" value="N"/>
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
														<input name="inspYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="inspYn" type="radio" checked class="ace" value="N"/>
														<span class="lbl"> 미사용</span>
													</label>
												</div>
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>사입메뉴 사용여부</label>
												<div class="radio">
													<label>
														<input name="takeYn" type="radio" class="ace" value="Y"/>
														<span class="lbl"> 사용</span>
													</label>
													<label>
														<input name="takeYn" type="radio" checked class="ace" value="N"/>
														<span class="lbl"> 미사용</span>
													</label>
												</div>
											</div>
										</div>
										
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >회원 등급</div>
												<div class="col-lg-6 col-xs-12">
													<input class="width-100" type="text" name="userGrade" readonly id="userGrade" value="" />
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
	
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
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
				location.href="/comn/login";
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

				/* if($("#userId").val() == ""){
					alert("아이디를 입력해 주세요");
					return false;
				}

				if($("#userName").val() == ""){
					alert("사용자 명을 입력해 주세요");
					return false;
				}

				if(pwCheck == "N"){
					alert("패스워드를 확인해 주세요");
					return false;
				}
				
				if($('#password1').val() != $('#password2').val()){
					alert("패스워드를 확인해 주세요");
					return false;
				}

				if($('#userEmail').val() == ""){
					alert("Email을 확인해 주세요");
					return false;
				}

				if($('#userTel').val() == ""){
					alert("전화번호를 확인해 주세요");
					return false;
				}

				if($('#inspPrice').val() == ""){
					alert("검품비용을 입력해 주세요");
					return false;
				}

				if($('#wtErrRange').val() == ""){
					alert("무게 오차범위를 입력해 주세요");
					return false
				}
				 */
				$('#userPw').val($('#password1').val());
				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/comn/registUser',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
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
				if($('#password1').val()=="" || $('#password2').val()== ""){
					$('#passwordMsg').text('');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').removeClass('blue');
					pwCheck = "N";
					return false;
				}
				if($('#password1').val() == $('#password2').val()){
					$('#passwordMsg').text('유효한 패스워드 입니다.');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').addClass('blue');
					pwCheck= "Y";
				}else{
					$('#passwordMsg').text('패스워드가 다릅니다.');
					$('#passwordMsg').removeClass('blue');
					$('#passwordMsg').addClass('red');
					pwCheck="Y";
				}
			})
		})
	</script>
	<!-- script addon end -->
</body>
</html>