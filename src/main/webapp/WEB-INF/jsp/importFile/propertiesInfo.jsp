<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		.title-label {
			height: 50px;
			display: flex;
			align-items: center;
			padding: 15px 17px;
		}
		
		.title-on {
			background: #148CFF;
			border: none;
			width: 25px;
			height: 25px;
			border-radius: 50%;
			appearance: none;
		}
		
		.title-off {
			background: #d2d2d2;
			border: none;
			width: 25px;
			height: 25px;
			border-radius: 50%;
			appearance: none;
		}
		
		.title-label-text {
			font-size: 16px;
			font-weight: 600;
			padding-left: 10px;
		}
		
		.body-title {
			overflow: hidden;
			display: flex;
			align-items: center;
		}
	
		.body-label-text {
			font-size: 16px;
			font-weight: 600;
		}
		
		.table-title {
			width: 20%;
			height: 40px;
			font-size: 14px;
			font-weight: 600;
		}
	
		.table-on {
			background: #148CFF;
			border: none;
			width: 20px;
			height: 20px;
			border-radius: 50%;
			appearance: none;
		}
		
		.table-off {
			background: #d2d2d2;
			border: none;
			width: 20px;
			height: 20px;
			border-radius: 50%;
			appearance: none;
		}
	
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
	<title>WMS.ESHOP</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<div class="page-header">
							<h1>
								application.properties
							</h1>
						</div>
						<div class="inner-content-side">
							<div style="width:13%;overflow:hidden;margin-left:10px;margin-top:20px;">
								<div class="title-label" style="width:50%;float:left;">
									<div class="title-on"></div><div class="title-label-text">ON</div>
								</div>
								<div class="title-label" style="width:50%;float:right;">
									<div class="title-off"></div><div class="title-label-text">OFF</div>
								</div>
							</div>
							<br/><br/>
							<table style="width:50%;margin-left:20px;">
								<tr>
									<td class="table-title">filePath</td>
									<td>${realFilePath}</td>
								</tr>
								<tr>
									<td class="table-title">smtpStatus</td>
									<td>
										<c:if test="${smtpStatus eq 'on'}">
											<div class="table-on"></div>
										</c:if>
										<c:if test="${smtpStatus ne 'on'}">
											<div class="table-off"></div>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="table-title">orderInfoChkStatus</td>
									<td>
										<c:if test="${orderInfoChkStatus eq 'on'}">
											<div class="table-on"></div>
										</c:if>
										<c:if test="${orderInfoChkStatus ne 'on'}">
											<div class="table-off"></div>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="table-title">schedulerStatus</td>
									<td>
										<c:if test="${schedulerStatus eq 'on'}">
											<div class="table-on"></div>
										</c:if>
										<c:if test="${schedulerStatus ne 'on'}">
											<div class="table-off"></div>
										</c:if>
									</td>
								</tr>
							</table>
							<br>
							<!-- <input type="button" value="test" id="test" class="btn btn-xs btn-primary"> -->
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
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
		<script type="text/javascript">

			jQuery(function($) {
				$("#test").on('click', function() {
					$.ajax({
						url : '/comn/fastbox/getWeight',
						type : 'GET',
						data : {test: "test"},
						success : function(result) {
							console.log(result);
						},
						error : function(error) {
							alert("fail");
						}
					}) 
				})
			})
		
		</script>
	</body>
</html>
