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
<title>Nation 등록</title>
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

			<form name="nationForm" id="nationForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">

					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>단가 관리</li>
							<li id="targets" name="targets">
								Nation 관리
							</li>
							<li class="active">Nation 등록</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>Nation 등록</h1>
						</div>
						<div id="inner-content-side">
							<div class="row">
								<!-- 설정정보 Start -->
								<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>nation 정보</h2>
									</div>
									<!-- style="border:1px solid red" -->
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<div class="col-lg-4 col-xs-12 pd-1rem" >출발지 국가</div>
												<div class="col-lg-8 col-xs-12">
													<select class="chosen-select form-control tag-input-style width-100 pd-1rem" id="orgNation" name="orgNation" data-placeholder="국가를 선택해 주세요">
														<option value="">  </option>
														<c:forEach items="${nations}" var="nations">
															<option <c:if test="${nations.nationCode eq legacyInfo.orgNation}">selected </c:if>value="${nations.nationCode}">${nations.nationName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="col-lg-6 col-xs-12">
												<div class="col-lg-4 col-xs-12 pd-1rem" >도착지 국가</div>
												<div class="col-lg-8 col-xs-12">
													<select class="chosen-select form-control tag-input-style width-100 pd-1rem" id="dstnNation" name="dstnNation" data-placeholder="국가를 선택해 주세요">
														<option value="">  </option>
														<c:forEach items="${nations}" var="nations">
															<option <c:if test="${nations.nationCode eq legacyInfo.dstnNation}">selected  </c:if>value="${nations.nationCode}">${nations.nationName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										
										<div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<div class="col-lg-12 col-xs-12 pd-1rem" >택배 회사</div>
												<select class="form-control" id="transCode" name="transCode">
													<option value="">  </option>
													<c:forEach items="${transCom}" var="transComs">
														<option <c:if test="${transComs.transCode eq legacyInfo.transCode}">selected </c:if>value="${transComs.transCode}">${transComs.transName}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-lg-6 col-xs-12">
												<div class="col-lg-3 col-xs-12 pd-1rem" >Zone 선택</div>
												<select class="form-control tag-input-style width-100 pd-1rem" id="zoneCode" name="zoneCode" data-placeholder="Zone을 선택해 주세요">
												<option value="">  </option>
												<option <c:if test="${legacyInfo.zoneCode eq 1}">selected </c:if>value="1">1</option>
												<option <c:if test="${legacyInfo.zoneCode eq 2}">selected </c:if>value="2">2</option>
												<option <c:if test="${legacyInfo.zoneCode eq 3}">selected </c:if>value="3">3</option>
												</select>
											</div>
										</div>
										<input type="hidden" name="idx" id="idx" value="${legacyInfo.idx }"/>
										<!-- <div class="row form-group hr-8">
											<div class="col-lg-6 col-xs-12">
												<div class="col-lg-12 col-xs-12 pd-1rem" >단가</div>
												<input type="text" name="unitPrice" id="unitPrice" class="col-lg-12 col-xs-12" value=""/>
											</div>
										</div> -->
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
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$("#1stMenu").toggleClass('open');
				$("#1stMenu").toggleClass('active');
				$("#1stOne").toggleClass('active');
			});
			
			$('#backToList').on('click', function(e) {
				history.back();
			});	

			$('#rgstrInfo').on('click', function(e) {
				var formData = $("#nationForm").serialize();

				
				$.ajax({
					url:'/mngr/unit/modifyNation',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("수정 되었습니다.");
							location.href="/mngr/unit/nationPrice";
						}else{
							alert("수정중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 관리자에게 문의해 주세요.");
		            }
				});
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
		})
	</script>
	<!-- script addon end -->
</body>
</html>