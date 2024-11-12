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

#tooltip {
	display : inline-block;
}

.tooltip-text {
	display : none;
	position : absolute;
	border : 1px solid;
	padding : 5px;
	font-size : 0.8em;
	background : white;
}

.fa-search:hover .tooltip-text {
	display : block;
}

label {
	font-weight: bold;
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

			<form name="adminForm" id="adminForm" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>관리자</li>
							<li>관리자 목록</li>
							<li class="active">관리자 등록</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>관리자 등록</h1>
						</div>
						<div id="inner-content-side">
							<!-- 기본정보 Start -->
							<div class="row">
								<div class="col-xs-12 col-lg-4 ">
									<!-- 기본정보 Start -->
									<div class="col-xs-12 col-lg-12 form-group"
										style="font-size: 5;">
										<h2>관리자 정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12"
										style="border: 1px solid #dee2e6 !important; padding: 3rem !important">
										<div class="row form-group hr-8">
											<div class="col-lg-12 col-xs-12">
												<label>아이디</label><br> <input class="width-70"
													type="text" name="adminId" id="adminId"" value="" />&nbsp;&nbsp;&nbsp;
												<button id="checkId" class="btn btn-primary btn-sm"
													name="checkId" type="button">중복확인</button>
											</div>
										</div>
										<div style="height:10px;"></div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label id="tooltip">패스워드 <i class="fa fa-search">
												<span class="tooltip-text">숫자, 특수문자, 영어 조합 8자리 이상을 입력해 주세요.</span>
												</i></label>
												<input class="width-100" type="password"
													name="password1" id="password1" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>패스워드 확인</label> <input class="width-100"
													type="password" name="password2" id="password2" value="" />
												<span class="" style="display: block;" id="passwordMsg"></span>
											</div>
										</div>
										<div class="row form-group">
											<input type="hidden" name="adminPw" id="adminPw" />
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>관리자 명</label> <input class="width-100" type="text"
													name="adminName" id="adminName" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>전화 번호</label> <input class="width-100"
													type="text" name="adminTel" id="adminTel" value="" />
											</div>
										</div>
										<div style="height:10px;"></div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>휴대전화 번호</label> <input class="width-100" type="text"
													name="adminHp" id="adminHp" value="" />
											</div>
											<div class="col-lg-6 col-xs-12">
												<label>Email</label> <input class="width-100" type="text"
													name="adminEmail" id="adminEmail" value="" />
											</div>
										</div>
										<div style="height:10px;"></div>
										<div style="height:10px;"></div>
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<label>사용 여부</label>
												<select id="useYn" name="useYn" style="margin-left:5px;">
													<option value="Y" selected>사용</option>
													<option value="N">미사용</option>
												</select>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-12 col-xs-12">
											<br />
											<div class="col-lg-1 col-xs-6 col-lg-offset-9">
												<div id="button-div">
													<div id="backPage" style="text-align: center">
														<button id="backToList" type="button"
															class="btn btn-sm btn-danger">취소</button>
													</div>
												</div>
											</div>
											<div class="col-lg-1 col-xs-6" style="margin-left: 10px;">
												<div id="button-div">
													<div id="rgstrUser" style="text-align: center">
														<button id="rgstrInfo" type="button"
															class="btn btn-sm btn-primary">등록</button>
													</div>
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
			
			$('#backToList').on('click', function(e) {
				location.href = '/mngr/acnt/adminList';
				
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

				if($("#adminId").val() == ""){
					/* var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
					regExpPw.test($('#password1').val()); */
					alert("아이디를 입력해 주세요");
					$("#adminId").focus();
					return false;
				}

				if(idCheck == "N"){
					alert("ID 중복확인을 해 주세요")
					$("#adminId").focus();
					return false;
				}

				if($("#adminName").val() == ""){
					alert("관리자 명을 입력해 주세요");
					$("#adminName").focus();
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


				if($("#adminTel").val() ==""){
					alert("전화 번호를 확인해 주세요");
					$("#adminTel").focus();
					return false;
				}
 
				if($("#adminHp").val() ==""){
					alert("휴대전화 번호를 확인해 주세요");
					$("#adminHp").focus();
					return false;
				}

				if($("#adminEmail").val() ==""){
					alert("Email을 확인해 주세요");
					$("#adminEmail").focus();
					return false;
				} else {
					/*
					var regExpMail = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/;
					if(!regExpMail.test($('#adminEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#adminEmail').focus();
						return false;
					}*/
				}

				$('#adminPw').val($('#password1').val());

				var formData = $("#adminForm").serialize();
 				$.ajax({
					url:'/mngr/acnt/registAdmin',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("등록 되었습니다.");
							location.href="/mngr/acnt/adminList";
							
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

			$("#checkId").on('click',function(e){
				if($("#adminId").val() == ""){
					alert("아이디를 입력해 주세요");
					return;
				}
				var formData = $("#adminForm").serialize();
				$.ajax({
					url:'/mngr/acnt/idCntCheck',
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
							$("#adminId").val("");
							idCheck = "N";
							$("#adminId").focus();
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});



			
		});
	</script>
	<!-- script addon end -->

</body>
</html>