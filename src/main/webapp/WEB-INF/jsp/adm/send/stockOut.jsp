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
	
	.tableFixHead {
	  width: 100%;
	}
	
	.tableFixHead, .tableFixHead td {
	  border-collapse: collapse;
	  border: 1px solid #dcdcdc;
	}
	
	.tableFixHead thead {
	  display: table;
	  width: calc(100% - 17px);
	  border-collapse: collapse;
	}
	
	.tableFixHead tbody {
	  display: block; 
	  max-height: 200px; 
	  overflow-y: scroll;
	}
	
	.tableFixHead th {
	  	font-family: 'Open Sans';
		text-align: center;
		padding: 5px 0;
		font-size: 14px;
		color: #000000 !important;
		font-weight: bold;
		background: linear-gradient(to top, #E4E6E9, white);
	  	border-collapse: collapse;
	  	border-right: 1px solid #dcdcdc;
	}
	
	.tableFixHead th, .tableFixHead td {
	  width: 33.33%;
	  padding: 5px;
	  height: 35px;
	  word-break: break-all;
	}
	
	.tableFixHead tr {
	  display: table;
	  width: 100%;
	  box-sizing: border-box; 
	}
	
	.tableFixHead td {
	  text-align: center;
	  border-bottom: none;
	  border-left: none;
	}
	
	#sekYear {
		height: 30px;
		width: 80px;
	}
	
	#sekMonth {
		height: 30px;
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
							<table class="table table-bordered" style="width:1085px;">
								<thead>
									<tr>
										<th class="center" style="width:50px;">
											<i class="fa fa-calendar bigger-110"></i>
										</th>
										<td style="padding:0px;width:100px;" class="input-daterange">
											<input type="text" style="width:100px; height:43px;"class="dateClass form-control" id="fromDate" name="fromDate" maxlength="10" value="${parameterInfo.fromDate}"/>
										</td>
										<th class="center">~</th>
										<td style="padding:0px;width:100px;" class="input-daterange">
											<input type="text" style="width:100px; height:43px;"class="dateClass form-control" id="toDate" name="toDate" maxlength="10" value="${parameterInfo.toDate}"/>
										</td>
										<th class="center" style="width:80px;">
											Station
										</th>
										<td style="padding:0px;width:100px;">
											<input type="text" class="center" list="stationCodeList" id="orgStation" name="orgStation" style="width:100px; height:43px;" value="${parameterInfo.orgStation}"/>
											<datalist id="stationCodeList">
												<c:forEach items="${stationCodeList}" var="stationCodeList" varStatus="status">
													<option value="${stationCodeList.orgStation}"
													<c:if test="${stationCodeList eq stationCodeList.orgStation}"> selected</c:if>>
													${stationCodeList.orgStation}
													</option>
												</c:forEach>
											</datalist>
										</td>
										<th class="center" style="width:80px;">USER ID</th>
										<td style="padding:0px;width:120px;">
											<input type="text" id="searchUserId" name="searchUserId" style="width:100%; height:43px;" value="${parameterInfo.searchUserId}"/>
										</td>
										<th class="center" style="width:100px;">발송구분</th>
										<td style="padding:0px;width:100px;">
											<select style="width:100%;height:43px;" name="dlvType" id="dlvType">
												<option <c:if test="${parameterInfo.dlvType == ''}"> selected</c:if> value=''>
												::전체::
												</option>
												<option <c:if test="${parameterInfo.dlvType == 'In'}"> selected</c:if> value='In'>
												국내
												</option>
												<option <c:if test="${parameterInfo.dlvType == 'Out'}"> selected</c:if> value="Out">
												해외
												</option>
											</select>
										</td>
										<td class="center" style="width:60px;">
											<button type="button" id="scrhBtn">검색</button>
										</td>
										<td class="center" style="width:150px;padding:0px;">
											<button type="button" id="excelBtn" style="height:43px;" class="btn btn btn-primary no-border btn-sm">검색 데이터 다운로드</button>		
										</td>
									</tr>
								</thead>
							</table>
						</form>
						<c:if test="${station eq '082'}">
							<form id="month-report-form" action="/mngr/send/monthReportExcelDown" method="get">
								<input type="text" name="sekYear" id="sekYear" value="${sekYear}">
								<select id="sekMonth" name="sekMonth">
									<c:forEach items="${monthList}" var="monthList">
										<option value="${monthList.month}" <c:if test="${monthList.month eq sekMonth}">selected</c:if>>${monthList.monthValue}</option>
									</c:forEach>
								</select>
								(월)
								<input type="button" id="monthReportBtn" value="출고 자료 다운로드">
							</form>
						</c:if>
						<div class="row">
							<div id="table-contents" class="table-contents" style="margin-top:30px; margin-left:20px;">
								<div style=" float:left;" id="stockOutTable">
									<div style="float:left; margin-bottom:5px;">
										<input type="button" class="btn btn-white btn-inverse btn-xs" value="excel" id="allStockOutExcelBtn" >
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
													Cnt
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
								<div id="inner-content-side" style="float: left; margin-left: 20px; text-align:center; width:420px;">
									<form id="monthStockOutForm">
										<div id="monthTable" style="width:100%;">
											<div style="text-align:right;float:left; margin-bottom:5px;">
												<input type="button" class="btn btn-white btn-inverse btn-xs" value="excel" id="monthStockOutExcelBtn"  >
											</div>
											<table id="stockListTable" class="tableFixHead" style="width:100%;">
												<thead>
													<tr>
														<th class="center colorBlack" style="display:none;">Station</th>
														<th class="center colorBlack" style="width: 180px;">USER ID</th>
														<th class="center colorBlack" style="width: 120px;">Dep Month</th>
														<th class="center colorBlack" style="width: 100px;">Cnt</th>
													</tr>
												</thead>
												<tbody id="monthBody">
												</tbody>
											</table>
										</div>
									</form>
									<br />
									<form id="userStockOutForm">
										<div id="userTable" style="width:100%;">
											<div style="text-align:right;float:left; margin-bottom:5px;">
												<input type="button" class="btn btn-white btn-inverse btn-xs" value="excel" id="userStockOutExcelBtn"  >
											</div>
											<table class="tableFixHead" id="stockOutList" style="width:100%;">
												<thead>
													<tr>
														<th class="center colorBlack" style="width: 180px;">USER ID</th>
														<th class="center colorBlack" style="width: 120px;">Dep Date</th>
														<th class="center colorBlack" style="width: 100px;">Cnt</th>
														<th class="center colorBlack" style="display: none;">station</th>
														<th class="center colorBlack" style="display: none;">dep month</th>
													</tr>
												</thead>
												<tbody id="userBody">
												</tbody>
											</table>
										</div>
									</form>
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

				$("#monthTable").hide();
				$("#userTable").hide();

				$("#allStockOutExcelBtn").click(function() {
					$("#stockOutForm").attr("action", "/mngr/send/stockOutListExcelDown");
					$("#stockOutForm").submit();
					$("#stockOutForm").attr("action", "/mngr/send/stockOut");
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
				console.log($("#stockOutForm").serialize());
				$('#stockOutForm').attr('action', '/mngr/send/stockOut');
				$('#stockOutForm').submit();
			});


			$("#monthReportBtn").click(function() {
				$("#month-report-form").submit();
			});

			
			$("#dynamic-table-list").on('click', 'tr', function() {
				var row = $(this).closest('tr');
				var orgStation = row.find('td:eq(0)').find('input').val();
				var depMonth = row.find('td:eq(2)').find('input').val();
				var userId = row.find('td:eq(3)').find('input').val();
				var dlvType = $("#dlvType").val();

				var html = "";
				var target = {orgStation: orgStation, depMonth: depMonth, userId: userId, dlvType: dlvType};

				$.ajax({
					url: "/mngr/send/stockOutListByMonth2",
					type: "GET",
					data: target,
					success: function(data) {
						$("#monthTable").show();
						var html = '';
						
						var values = [];
						values = data.stockOutInfo;

						$.each(values, function(index, value) {
							html += '<tr onclick="javascript:selectedRecord(this);">';
							html += '<td class="center" style="display:none;"><input type="hidden" name="org_station" value="'+value.orgStation+'"/>'+value.orgStation+'</td>';
							html += '<td class="center" style="width: 180px;"><input type="hidden" name="user_id" value="'+value.userId+'"/>'+value.userId+'</td>';
							html += '<td class="center" style="width: 120px;"><input type="hidden" name="dep_month" value="'+value.depMonth+'"/>'+value.depMonth+'</td>';
							html += '<td style="width: 100px; text-align: right;"><input type="hidden" value="'+value.cnt+'"/>'+value.cnt+'</td>';
							html += '</tr>';
						})

						$("#monthBody").empty();
						$("#monthBody").append(html);
					
					},
					error: function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("데이터 조회 실패");
					}
				})
			})
			
			$(document).on('click', '#stockListTable tr', function() {

				var row = $(this).closest('tr');
			    var orgStation = row.find('td:eq(0)').find('input').val();
		 	    var userId = row.find('td:eq(1)').find('input').val();
			    var depMonth = row.find('td:eq(2)').find('input').val();
				var dlvType = $("#dlvType").val();

				var html = "";

				var target = {orgStation:orgStation, depMonth:depMonth, userId:userId, dlvType: dlvType};

				console.log(target);
				
				$.ajax({
					url : "/mngr/send/stockOutByUserId2",
					data : target,
					type : "GET",
					success : function(data) {
						$("#userTable").show();
						var html = '';
						
						var values = [];
						values = data.stockOutInfo;

						$.each(values, function(index, value) {
							html += '<tr>';
							html += '<td class="center" style="width: 180px;"><input type="hidden" name="userId" value="'+value.userId+'"/>'+value.userId+'</td>';
							html += '<td class="center" style="width: 120px;"><input type="hidden" value="'+value.depDate+'"/>'+value.depDate+'</td>';
							html += '<td style="width: 100px; text-align: right;"><input type="hidden" value="'+value.cnt+'"/>'+value.cnt+'</td>';
							html += '<td class="center" style="display:none;"><input type="hidden" name="orgStation" value="'+value.orgStation+'"/>'+value.orgStation+'</td>';
							html += '<td class="center" style="display:none;"><input type="hidden" name="depMonth" value="'+value.depMonth+'"/>'+value.depMonth+'</td>';
							html += '</tr>';
						})

						$("#userBody").empty();
						$("#userBody").append(html);
					},
					error : function(request, status, error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
				});
				
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

			$(document).on('click', '#monthStockOutExcelBtn', function() {
				$("#monthStockOutForm").attr('action', '/mngr/send/stockOutListByMonthExcel');
				$("#monthStockOutForm").attr('method', 'GET');
				$("#monthStockOutForm").submit();
			})
			
			$(document).on('click', '#userStockOutExcelBtn', function() {
				$("#userStockOutForm").attr("action", "/mngr/send/stockOutListByUserIdExcel");
				$("#userStockOutForm").attr('method', 'GET');
				$("#userStockOutForm").submit();
			})
			
			function press(e) {
				if (e.keyCode == 13) {
					$('#stockOutForm').attr('action', '/mngr/send/stockOut');
					$('#stockOutForm').submit();
				}
			}

		</script>
		<!-- script addon end -->
		
	</body>
</html>
