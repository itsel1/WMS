<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
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

label {
	font-weight: 500;
}

.fa-exclamation-circle {
	color: DarkSalmon;
	font-weight: normal !important;
	cursor: pointer;
}



</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>쇼핑몰 연동</title>
<body class="no-skin">
	<div class="site-wrap">
	<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>쇼핑몰 연동</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">Shopify</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h1>Shopify 연동</h1>
							</div>
							<form name="userInfoForm" id="userInfoForm">
								<input type="hidden" id="statusCode" value="${status}"/>
								<div id="inner-content-side">
									<div class="row">
										<div class="col-xs-12 col-lg-6 col-lg-offset-3" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div class="row form-group hr-8">
												<div class="col-lg-12 col-xs-12">
													<label style="font-weight:bold;font-size:20px;">API 정보
													<a href="/Shopify_api_manual.docx"><i class="fa fa-exclamation-circle"  style="color:DodgerBlue;" id="shopify_manual"></i></a>
													</label>
												</div>
												<div class="space-20" style="padding:1rem !important;"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label>도메인 URL</label>
														<input type="text" class="width-100" name="shopifyUrl" id="shopifyUrl" value="${shopifyInfo.shopifyUrl}">
													</div>
													<div class="row" style="padding: 1rem !important;"></div>
													<div class="col-lg-6 col-xs-12">
														<label>API KEY</label>
														<input type="text" class="width-100" name="apiKey" id="apiKey" value="${shopifyInfo.apiKey}"/>
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>API Token</label>
														<input type="text" class="width-100" name="apiToken" id="apiToken" value="${shopifyInfo.apiToken}">
													</div>
												</div>
												<div class="row" style="padding: 1rem !important;"></div>
												<div class="row form-group hr-8" style="text-align:center;">
													<input type="button" id="regist-Btn" class="btn-primary" value="등록"/>
												</div>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<!-- SideMenu Start -->
		<!-- SideMenu End -->

		<div class="main-content">

			
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			
			$(document).ready(function(e) {

			});
		});
		
		$("#regist-Btn").on('click', function(e) {
			
			if ($("#shopifyUrl").val() == "") {
				$("#shopifyUrl").focus();
				return false;
			}
			
			if ($("#apiKey").val() == "") {
				$("#apiKey").focus();
				return false;
			}
			
			if ($("#apiToken").val() == "") {
				$("#apiToken").focus();
				return false;
			}
			
			$.ajax({
				url : '/cstmr/integrate/shopify',
				type : 'POST',
				data : $("#userInfoForm").serialize(),
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(response) {
					var msg = response.msg;
					alert(msg);
					location.reload();
				},
				error : function(error) {
					alert("등록 요청 중 시스템 오류가 발생 하였습니다.\n관리자에게 문의해 주세요.");
					return false;
				}
			})
		});


	</script>
	<!-- script addon end -->
</body>
</html>