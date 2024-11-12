<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<style type="text/css">

	.colorBlack {color:#000000 !important;}


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
	<!-- basic scripts End-->
	<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
	</head> 
	<title>출고 내역</title>
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
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								발송 리스트
							</li>
							<li class="active">MAWB APPLY</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								출고 내역
							</h1>
						</div>
						<form name="stockOutForm" id="stockOutForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="row">
								<div class="col-xs-12 col-sm-2" style="padding:0px 0px 0px 15px!important">
									<div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
										<div class="input-group input-daterange">
											<input type="text" class="dateClass form-control" id="fromDate" name="fromDate" maxlength="10" value="${parameterInfo.fromDate}"/>
												<span class="input-group-addon">
													to
												</span>
											<input type="text" class="dateClass form-control" id="toDate" name="toDate" value="${parameterInfo.toDate}"/>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-2 no-padding">
									<div class="col-xs-12 col-sm-4 no-padding">
										<input class="center" type="text" style="width:100%" value="Station" readOnly/>
									</div>
									<div class="col-xs-12 col-sm-8 no-padding">
										<input list="stationCodeList" id="orgStation" name="orgStation" class="center" type="text" style="width:100%" value="${parameterInfo.orgStation}"/>
										<datalist id="stationCodeList" name="stationCodeList" >
										<c:forEach items="${stationCodeList}" var="stationCodeList" varStatus="status">
											<option value="${stationCodeList.orgStation}"
												<c:if test="${stationCodeList eq stationCodeList.orgStation}">selected</c:if>>
												${stationCodeList.orgStation}
											</option>
										</c:forEach>
									</div>
								</div>
								<div class="col-xs-12 col-sm-2 no-padding">
									<div class="col-xs-12 col-sm-4 no-padding">
										<input class="center" type="text" style="width:100%" value="USER ID" readOnly/>
									</div>
									<div class="col-xs-12 col-sm-8 no-padding">
										<input id="searchUserId" name="searchUserId" class="center" type="text" style="width:100%" value="${parameterInfo.searchUserId}"/>
									</div>
								</div>
								<div class="col-xs-12 col-sm-2 no-padding">
									<button type="button" id="scrhBtn" class="btn btn btn-primary no-border btn-sm">검색</button>
									<button type="button" id="excelBtn" class="btn btn btn-primary no-border btn-sm">검색 데이터 다운로드</button>
								</div>
							</div>
						</form>
						<div class="row">
							<div id="table-contents" class="table-contents" style="margin-top:30px; margin-left:20px;">
								<div style=" float:left;" id="stockOutTable">
									<div style="float:left; margin-bottom:5px;">
										<input type="button" class="btn btn-white btn-inverse btn-xs" value="리스트 다운로드" id="allStockOutExcelBtn" >
									</div>
									<table id="dynamic-table-list" class="table table-bordered table-hover containerTable" style="width:420px;">
										<thead>
											<tr>
												<th class="center colorBlack" style="width: 120px;">
													Station Code	
												</th>
												<th class="center colorBlack" style="width: 120px;">
													Station Name
												</th>
												<th class="center colorBlack" style="width: 100px;">
													Dep Month
												</th>
												<th class="center colorBlack" style="display:none;">
													User Id
												</th>
												<th class="center colorBlack" style="width: 80px;">
													수량
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${stockOutList}" var="stockOutList" varStatus="status">
												<tr onclick="javascript:selectedRecord(this);">
													<td class="center colorBlack">
														<input type="hidden" id="orgStation" name="orgStation" value="${stockOutList.orgStation}"/>
														${stockOutList.orgStation}
													</td>
													<td class="center colorBlack">
														<input type="hidden" id="stationName" name="stationName" value="${stockOutList.stationName}"/>
														${stockOutList.stationName}
													</td>
													<td class="center colorBlack">
														<input type="hidden" id="depMonth" name="depMonth" value="${stockOutList.depMonth}"/>
														${stockOutList.depMonth}
													</td>
													<td class="cetner colorBlack" style="display: none;">
														<input type="hidden" id="userId" name="userId" value="${stockOutList.userId}"/>
														${stockOutList.userId}
													</td>
													<td class="colorBlack" style="text-align:right;">
														<input type="hidden" id="cnt" name="cnt" value="${stockOutList.cnt}"/>
														${stockOutList.cnt}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<br>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
								<div id="inner-content-side" style="float: left; margin-left: 20px; text-align:center; ">
									<div id="monthTable">
									</div>
									<br />
									<div id="userTable">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
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
		
		
		
		<!-- script datepicker -->
		
		<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		
		
		<!-- script addon start -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#8thMenu").toggleClass('open');
				$("#8thMenu").toggleClass('active'); 
				$("#8thThird").toggleClass('active');

				$("#allStockOutExcelBtn").click(function() {
					$("#stockOutForm").attr("action", "/mngr/send/stockOutListExcelDown");
					$("#stockOutForm").submit();
					$("#stockOutForm").attr("action", "/mngr/send/stockOutList");
				})
			})
			
			$("#excelBtn").click(function() {
				
				if ($("#fromDate").val() == "" || $("#toDate").val() == "") {
					alert("조회 기간을 설정해주세요");
				} else {
					var fromDate = ($("#fromDate").val().replace(/\-/g, ''));
					var toDate = ($("#toDate").val().replace(/\-/g, ''));
					var station = $("#orgStation").val();
					var userId = $("#searchUserId").val();
					
					window.open("/mngr/send/stockOutExcelDown?orgStation="+station+"&userId="+userId+"&fromDate="+fromDate+"&toDate="+toDate);	
				}
			})
			
			$('input[name=date-range-picker]').daterangepicker({
				'applyClass' : 'btn-sm btn-success',
				'cancelClass' : 'btn-sm btn-default',
				locale: {
					applyLabel: 'Apply',
					cancelLabel: 'Cancel',
				}
			})
			
			//or change it into a date range picker
			$('.input-daterange').datepicker({
				autoclose:true,
				todayHighlight:true,
				format:'yyyymmdd'
			})
	

			$('#scrhBtn').click(function() {
				$('#stockOutForm').attr('action', '/mngr/send/stockOutList');
				$('#stockOutForm').submit();
			})
			
			$("#testBtn").click(function() {
				$("#stockOutForm").attr('action', '/mngr/send/testExcelDown');
				//$("#stockOutForm").submit();
			})
			
			$("#dynamic-table-list").on('click', 'tr', function() {
				var row = $(this).closest('tr');
				var orgStation = row.find('td:eq(0)').find('input').val();
				var depMonth = row.find('td:eq(2)').find('input').val();
				var userId = row.find('td:eq(3)').find('input').val();

				var html = "";
				var target = {orgStation: orgStation, depMonth: depMonth, userId: userId};

				$.ajax({
					url: "/mngr/send/stockOutListByMonth",
					type: "GET",
					data: target,
					success: function(data) {
						$('#monthTable').empty();
						$('#monthTable').html(data);
					},
					error: function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("데이터 조회 실패");
					}
				})
			})

			var myTable = 
				$("#dynamic-table-list").DataTable({
					"dom" : 't',
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : 480,
			        scrollCollapse: true,
				})
			
			function selectedRecord(target) {

				var tbody = target.parentNode;
				var trs = tbody.getElementsByTagName('tr');

				var backColor = "#ffffff";
				var textColor = "black";
				var orgBColor = "#ccc";
				var orgTColor = "black";

				var no = "";
				var no1 = "";

				for (var i = 0; i < trs.length; i++) {
					if (trs[i] != target) {
						trs[i].style.backgroundColor = backColor;
						trs[i].style.color = textColor;
						trs[i].style.fontWeight = "normal";
					} else {
						trs[i].style.backgroundColor = orgBColor;
						trs[i].style.color = orgTColor;
						trs[i].style.fontWeight = "bold";
						var td = trs[i].getElementsByTagName('td');
					}
				}
			}
			
		</script>
		<!-- script addon end -->
		
	</body>
</html>
