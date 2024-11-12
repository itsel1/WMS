<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
	<style type="text/css">
	
</style>
	<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								EMS 송장 업데이트
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side"><br>
						 	<form id="mgrForm">
						 		<c:choose>
						 			<c:when test="${!empty blInfo}">
										<input type="hidden" name="nno" id="nno" value="${nno}">
										<input type="hidden" name="transCode" id="transCode" value="${blInfo.transCode}"/>
										<input type="hidden" name="hawbNo" id="hawbNo" value="${blInfo.hawbNo}"/>
										<table class="table table-bordered table-hover" style="width:100%;margin-top:10px;">
											<thead>
												<tr>
													<th class="center" style="width:20%;">USER ID</th>
													<td style="background-color:#fff;">${blInfo.userId}</td>
													<th class="center" style="width:20%;">도착지</th>
													<td style="background-color:#fff;">${blInfo.nationName}</td>
												</tr>
												<tr>
													<th class="center" style="width:20%;">주문번호</th>
													<td style="background-color:#fff;">${blInfo.orderNo}</td>
													<th class="center" style="width:20%;">수취인</th>
													<td style="background-color:#fff;">${blInfo.cneeName}</td>
												</tr>
											</thead>
										</table>
										<table class="table table-bordered table-hover" style="width:100%;">
											<thead>
												<tr>
													<th class="center" style="width:20%">ACI BL</th>
													<td style="background-color:#fff;">${blInfo.hawbNo}</td>
												</tr>
												<tr>
													<th class="center" style="width:20%;">EMS BL</th>
													<td style="background-color:#fff;padding:0px;">
														<input type="text" name="agencyBl" id="agencyBl" style="width:100%;height:36px;padding-left:8px;" value="${blInfo.agencyBl}"/>
													</td>
												</tr>
											</thead>
										</table>
										<br/>
										<div style="text-align:center;">
											<input type="button" value="등록" class="btn btn-primary btn-sm" id="registBtn">
										</div>
									</c:when>
									<c:otherwise>
										<div style="text-align:center;">
											EMS 배송 건만 업데이트가 가능합니다.
										</div>
									</c:otherwise>
								</c:choose>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		
		<!-- script on paging end -->
		
		
		<!-- script addon start -->
		
	<script type="text/javascript">
		jQuery(function($) {
			$("#registBtn").click(function() {
				var dataForm = $("#mgrForm").serialize();
				
				$.ajax({
					url : '/mngr/takein/updateTakeinEmsOrderBl',
					type : 'POST',
					data : dataForm,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(result) {
						alert("등록 되었습니다.");
						window.close();
						window.opener.location.reload();
					},
					error : function(xhr, status) {
						alert("등록 중 오류가 발생 하였습니다.");
					}
				})
			})
		})
	</script>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

	</body>
</html>
