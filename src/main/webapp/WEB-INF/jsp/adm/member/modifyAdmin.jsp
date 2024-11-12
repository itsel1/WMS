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

input[type=text] {
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

input[type=password] {
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

.vertiMidle {
	vertical-align: middle;
}

.pd-1rem {
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
<title>관리자 페이지</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<!-- headMenu End -->
	
	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container');
			} catch (e) {
			}
		</script>
		<!-- SideMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<!-- SideMenu End -->
		
		<div class="main-content">
			<form name="adminInfoForm" id="adminInfoForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>관리자</li>
							<li>관리자 목록</li>
							<li class="active">정보 수정</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>${adminId} 관리자 정보 수정</h1>
						</div>
						<div id="inner-content-side">
							<!--  -->
							<div class="row">
								<div class="col-xs-12 col-lg-6">
									<!-- 기본정보 Start -->
									<div class="tabbable">
										<ul class="nav nav-tabs" id="myTab">
											<li class="active">
												<a data-toggle="tab" href="#tab1">
													기본정보
												</a>
											</li>
										</ul>
										<div class="tab-content">
											<div id="tab1" class="tab-pane fade in active">
												<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
													<h2>관리자 정보</h2>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-8 col-xs-12">
														<label>아이디</label>
														<div>
															<input class="width-100" type="text" name="adminId" readonly id="adminId" value="${adminVO.adminId}" />
														</div>
													</div>
												</div>
												<div class="col-lg-8 col-xs-12">
													<br/>
												</div>
												<!-- <div class="row form-group" style="margin-left:1px;">
													<div class="col-lg-6 col-xs-12">
														<button type="button" id="changePw" onclick="doDisplay()" class="btn btn-sm btn-round btn-inverse">비밀번호 변경</button>
														<span style="color: #C0C0C0;">&nbsp;비밀번호 변경 시 눌러주세요</span>
													</div>
												</div>
												<div class="row form-group hr-8" id="divPw" style="display:none;">
													<div class="row form-group hr-8">
														<div class="col-lg-6 col-xs-12">
															<label>새 비밀번호</label>
															<input class="width-100" type="password" name="password1" id="password1" value="" placeholder="비밀번호를 입력해 주세요"/>
														</div>
														<div class="col-lg-6 col-xs-12">
															<label>새 비밀번호 확인</label>
															<input class="width-100" type="password" name="password2" id="password2" value="" />
															<span class="" style="display:block;" id="passwordMsg"></span>
														</div>
													</div>
												</div>  -->
												<div class="row form-group" style="margin-left:1px;">
													<div class="col-lg-6 col-xs-12">
														<button type="button" id="resetPw" class="btn btn-sm btn-round btn-inverse">비밀번호 초기화</button>
													</div>
												</div>
												<div class="row form-group">
													<input type="hidden" name="adminPw" id="adminPw"/>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>관리자 명</label>
														<input class="width-100" type="text" name="adminName" id="adminName" value="${adminVO.adminName}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>전화 번호</label>
														<input class="width-100" type="text" name="adminTel" id="adminTel" value="${adminVO.adminTel}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>휴대전화 번호</label>
														<input class="width-100" type="text" name="adminHp" id="adminHp" value="${adminVO.adminHp}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>Email</label>
														<input class="width-100" type="text" name="adminEmail" id="adminEmail" value="${adminVO.adminEmail}" />
													</div>
												</div>
												<div style="height:5px;"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>사용 여부</label>
														<select id="useYn" name="useYn" style="margin-left:5px;">
															<option <c:if test="${adminVO.useYn eq 'Y'}"> selected</c:if> value="Y">사용</option>
															<option <c:if test="${adminVO.useYn eq 'N'}"> selected</c:if> value="N">미사용</option>
														</select>
													</div>
												</div>
											</div>
										</div>
										
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-6 col-xs-12">
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
											<div id="updateAdmin" style="text-align: center">
												<button id="updateInfo"	type="button" class="btn btn-sm btn-primary">수정</button>
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
	
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+ "<"+"/script>");
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
		var pwCheck = "N";
	
		jQuery(function($) {			
			var currentTarget = "#tab1";

			$('#backToList').on('click', function(e) {
				location.href = '/mngr/acnt/adminList';
			});

			$('#resetPw').on('click', function(e) {
				var formData = $('#adminInfoForm').serialize();
				$.ajax({
					url: '/mngr/acnt/admin/resetPw/' + $('#adminId').val(),
					type: 'POST',
					data: formData,
					beforeSend : function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");		
					},
					success : function(data) {
						if (data == "S") {
							alert("초기화 되었습니다.");
						} else {
							alert("변경 중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
					},
					error : function(xhr, status) {
						alert(xhr + " : " + status);
						alert("변경에 실패 하였습니다. 관리자에게 문의해 주세요.");
					}
						
				});
			});

			
			 
			$('#updateInfo').on('click', function(e) {

				if($('#adminName').val() == "") {
					alert("관리자 명을 입력해 주세요");
					return false; 
				}
				
				if($('#adminTel').val() == "") {
					alert("전화 번호를 입력해 주세요");
					return false;
				}

				if($('#adminHp').val() == "") {
					alert("휴대전화 번호를 입력해 주세요");
					return false;
				}

				if($('#adminEmail').val() == "") {
					alert("Email을 입력해 주세요");
					return false;
				} else {
					/*
					var regExpMail = /^[a-zA-z0-9_+.-]+@([a-zA-z0-9-]+\.)+[a-zA-z0-9]{2,4}$/;
					if(!regExpMail.test($('#adminEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#adminEmail').focus();
						return false;
					}*/
				}

				var formData = $('#adminInfoForm').serialize();
				$.ajax({
					url: '/mngr/acnt/modiInfo/'+$('#adminId').val(),
					type: 'PATCH',
					data: formData,
					beforeSend: function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success: function(data) {
						if (data == "S") {
							alert("수정 되었습니다.");
							location.href="/mngr/acnt/adminList";
						} else {
							alert("수정 중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
					},
					error: function(xhr, status) {
						alert(xhr+":"+status);
						alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
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
		});

		function doDisplay() {
			var con = document.getElementById("divPw");
			if(con.style.display=="none") {
				con.style.display = "block";
			} else {
				con.style.display = "none";
			} 
		} 
		
	</script>
	<!-- script addon end -->
	
</body>
</html>