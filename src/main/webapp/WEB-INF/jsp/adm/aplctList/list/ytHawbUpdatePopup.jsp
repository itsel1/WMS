<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
			
		#save {
			height:50px; 
			text-align:center; 
			cursor:pointer; 
			background:#f1f0f0; 
			border:1px solid #ccc;
			font-weight:bold;
		}
		
		#save:hover {
			background: #ccc;
			border:1px solid #ccc;
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
								Home
							</li>
	
							<li>
								입고 작업
							</li>
							<li class="active">YT Hawb 업데이트</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div id="inner-content-side">
							<form id="form" name="form">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="nno" id="nno" value="${parameters.nno}">
								<input type="hidden" name="transCode" id="transCode" value="${parameters.transCode}">
								<input type="hidden" name="hawbNo" id="hawbNo" value="${orderInfo.hawbNo}">								
								<br/>
								<table class="table table-bordered table-hover containerTable">
									<tr>
										<th class="center" colspan="2">ACI 정보</th>
									</tr>
									<tr>
										<th class="center">Nno</th>
										<td>${orderInfo.nno}</td>
									</tr>
									<tr>
										<th class="center">UserID</th>
										<td>${orderInfo.userId}
									</tr>
									<tr>
										<th class="center">Company</th>
										<td>${orderInfo.companyName}
									</tr>
									<tr>
										<th class="center">OrderNo</th>
										<td>${orderInfo.orderNo}</td>
									</tr>
									<tr>
										<th class="center">HawbNo</th>
										<td>${orderInfo.hawbNo}</td>
									</tr>
									<tr>
										<th class="center">수취인</th>
										<td>${orderInfo.cneeName}</td>
									</tr>
									<tr>
										<th class="center">주소</th>
										<td>${orderInfo.cneeAddr}</td>
									</tr>
									<tr>
										<th class="center" colspan="2">Yun Express 정보</th>
									</tr>
									<tr>
										<th class="center">YT 번호</th>
										<td style="padding:0;"><input type="text" name="matchNo" id="matchNo" style="width:100%;height:100%;"></td>
									</tr>
								</table>
								<br/>
								<div style="width:100%;text-align:center">
									<input type="button" id="updateBtn" value="등록">
								</div>
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
				$("#updateBtn").click(function() {
					$.ajax({
						url : '/mngr/order/ytHawbUpdate',
						type : 'post',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : $("#form").serialize(),
						success : function(data) {
							if (data.status == 'S') {
								alert("등록 되었습니다.");
								opener.location.reload();
                                window.close();
							} else {
								alert("업데이트 처리 중 문제가 발생하였습니다.");
								return;
							}
						},
						error : function(error) {
							alert("등록에 실패 하였습니다.");
						}
					})
					
				});
			});

		</script>
		<!-- script addon end -->
	</body>
</html>
