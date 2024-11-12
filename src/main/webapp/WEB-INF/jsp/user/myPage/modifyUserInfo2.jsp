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
	.contents {
		background: #ccc;
	}
</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>마이 페이지</title>
<body class="no-skin">
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
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>마이 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">정보 수정</strong></div>
							</div>
						</div>
					</div>
					<div class="">
						<div class="page-content">
							<div class="page-header">
								<h1>정보 수정</h1>
							</div>
							<form name="userInfoForm" id="userInfoForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div class="progress">
									<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="1" aria-valuemin="1" aria-valuemax="5" style="width: 20%;">
										기본 정보
									</div>
								</div>
								<div class="navbar">
									<div class="navbar-inner">
										<ul class="nav nav-pills">
											<li class="active"><a href="#normal" data-toggle="tab" data-step="1">기본 정보</a></li>
											<li><a href="#inv" data-toggle="tab" data-step="2">청구지 정보</a></li>
											<li><a href="#customs" data-toggle="tab" data-step="3">세관 신고 정보</a></li>
											<li><a href="#settings" data-toggle="tab" data-step="4">설정 정보</a></li>
											<li><a href="#eshop" data-toggle="tab" data-step="5">Eshop 정보</a></li>
										</ul>
									</div>
								</div>
								<div class="tab-content">
									<div class="tab-pane fade in active" id="normal">
										<div class="contents">
										</div>
										<a class="btn btn-default btn-lg next" href="#">Continue</a>
									</div>
									<div class="tab-pane fade" id="inv">
										<div class="contents">
										</div>
										<a class="btn btn-default btn-lg next" href="#">Continue</a>
									</div>
									<div class="tab-pane fade" id="customs">
										<div class="contents">
										</div>
										<a class="btn btn-default btn-lg next" href="#">Continue</a>
									</div>
									<div class="tab-pane fade" id="settings">
										<div class="contents">
										</div>
										<a class="btn btn-default btn-lg next" href="#">Continue</a>
									</div>
									<div class="tab-pane fade" id="eshop">
										<div class="contents">
										</div>
										<a class="btn btn-default btn-lg next" href="#">Continue</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
	</div>





	<!-- headMenu Start -->
	<!-- headMenu End -->

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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			$(".next").click(function() {
				var nextId = $(this).parents('.tab-pane').next().attr('id');
				$('[href=#'+nextId']').tab('show');
				return false;
			})

			$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
				var step = $(e.target).data('step');
				var percent = (parseInt(step) / 5) * 100;

				$('.progress-bar').cass({width: percent + '%'});
				$('.progress-bar').text("Step " + step + " of 5");
			})

			$('.first').click(function(){

			  $('#myWizard a:first').tab('show');
			
			})
		})	
	</script>
	<!-- script addon end -->
</body>
</html>