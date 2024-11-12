<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		#data-table {
			width: 100%;
			border-collapse: collapse;
			border: 1px solid #ddd;
		}
		
		#data-table td {
			padding: 8px;
			border-collapse: collapse;
			border: 1px solid #787878;
		}
		
		#data-table th {
			text-align: center;
			background: Gainsboro;
			border: 1px solid #787878;
			border-collapse: collapse;
			color: #000;
			z-index: 900;
		}
		
		#wrapper {
			border: 1px solid #ddd;
			width: 100%;
			height: 600px;
			overflow-y: auto;
			margin-top: 10px;
		}
		
		.sticky-header {
			position: sticky;
			top: 0px;
			height: 30px;
		}
		
		.sticky-header2 {
			position: sticky;
			top: 30px;
			height: 30px;
		}
		
		#wrapper::-webkit-scrollbar {
			width: 10px;
		}
		
		#wrapper::-webkit-scrollbar-thumb {
			background-color: Gainsboro;
			border-radius: 10px;
			background-clip: padding-box;
			border: 2px solid transparent;
		}
		
		#wrapper::-webkit-scrollbar-track {
			background-color: GhostWhite;
			border-radius: 10px;
			box-shadow: inset 0px 0px 5px white;
		}

		.price-app-btn {
			background-color: Gainsboro;
			border-radius: 12px;
			color: #000;
			cursor: pointer;
			font-weight: bold;
			padding: 5px 10px;
			text-align: center;
			transition: 200ms;
			box-sizing: border-box;
			border: 0;
			user-select: none;
			-webkit-user-select: none;
			touch-action: manipulation;
		}
		
		.price-app-btn:not(:disabled):hover, .price-app-btn:not(:disabled):focus {
			outline: 0;
			background: #fff000;
			color: #000;
			box-shadow: 0 0 0 2px rgba(0,0,0,.2), 0 3px 8px 0 rgba(0, 0, 0, .15);
		}
		
		.price-app-btn:disabled {
			filter: saturate(0.2) opacity(0.5);
			-webkit-filter: saturate(0.2) opacity(0.5);
			cursor: not-allowed;
		}
		
		input.search-input {
			box-sizing: border-box;
			-webkit-border-radius: 10px !important;
			-moz-border-radius: 10px !important;
			border-radius: 10px !important;
			border: 1px solid #ddd;
			width: 200px;
		}
		
		input.search-input:focus {
			border: 2px solid #45a247;
		}
		
		#search-table td {
			height: 40px;
		}


		#search {
			align-items: center;
			appearance: none;
			border-radius: 4px;
			border-style: none;
			box-shadow: rgba(0, 0, 0, .2) 0 3px 1px -2px,rgba(0, 0, 0, .14) 0 2px 2px 0,rgba(0, 0, 0, .12) 0 1px 5px 0;
			box-sizing: border-box;
			color: #fff;
			cursor: pointer;
			display: inline-flex;
			font-family: Roboto,sans-serif;
			font-size: .875rem;
			font-weight: 500;
			height: 36px;
			justify-content: center;
			letter-spacing: .0892857em;
			line-height: normal;
			min-width: 64px;
			outline: none;
			overflow: visible;
			padding: 0 16px;
			position: relative;
			text-align: center;
			text-decoration: none;
			text-transform: uppercase;
			transition: box-shadow 280ms cubic-bezier(.4, 0, .2, 1);
			user-select: none;
			-webkit-user-select: none;
			touch-action: manipulation;
			vertical-align: middle;
			will-change: transform,opacity;
		}

		#search:hover {
			box-shadow: rgba(0, 0, 0, .2) 0 2px 4px -1px, rgba(0, 0, 0, .14) 0 4px 5px 0, rgba(0, 0, 0, .12) 0 1px 10px 0;
		}
		
		#search:disabled {
			background-color: rgba(0, 0, 0, .12);
			box-shadow: rgba(0, 0, 0, .2) 0 0 0 0, rgba(0, 0, 0, .14) 0 0 0 0, rgba(0, 0, 0, .12) 0 0 0 0;
			color: rgba(0, 0, 0, .37);
			cursor: default;
			pointer-events: none;
		}
		
		#search:not(:disabled) {
			background-color: #6200ee;
		}
		
		#search:focus {
			box-shadow: rgba(0, 0, 0, .2) 0 2px 4px -1px, rgba(0, 0, 0, .14) 0 4px 5px 0, rgba(0, 0, 0, .12) 0 1px 10px 0;
		}

		#search:active {
			box-shadow: rgba(0, 0, 0, .2) 0 5px 5px -3px, rgba(0, 0, 0, .14) 0 8px 10px 1px, rgba(0, 0, 0, .12) 0 3px 14px 2px;
			background: #A46BF5;
		}
	</style>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	</head> 
	<title>WMS.ESHOP</title>
	<body class="no-skin">
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								Invoice
							</li>
							<li class="active">Price Apply</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								Price Apply
							</h1>
						</div>
						<div class="page-contents">
							<form id="priceForm" action="/mngr/invoice/v1/invPriceApplyList" method="get">
								<input type="hidden" name="fromDate" id="fromDate" value="${parameters.fromDate}">
								<input type="hidden" name="toDate" id="toDate" value="${parameters.toDate}">
								<input type="hidden" name="orgStation" id="orgStation" value="${parameters.orgStation}">
								<div style="width:1300px;">
									<table id="search-table">
										<tr>
											<td style="font-weight:bold;padding:5px 10px;font-size:14px;">USER ID</td>
											<td>
												<input type="text" name="userId" id="userId" class="search-input" value="${parameters.userId}">
											</td>
											<td style="font-weight:bold;padding:5px 10px;font-size:14px;">기간</td>
											<td>
												<input type="text" name="dates" id="dates" style="z-index:1000;" class="search-input"/>
											</td>
											<td style="padding-left:10px;padding-right:10px;">
												<input type="button" value="search" id="search">
											</td>
										</tr>
									</table>
									<div id="wrapper">
										<table id="data-table">
											<thead>
												<tr>
													<th rowspan="2" class="sticky-header" style="width:5%;">NO</th>
													<th rowspan="2" class="sticky-header" style="width:15%;">USER ID</th>
													<th rowspan="2" class="sticky-header" style="width:20%;">Company Name</th>
													<th colspan="3" class="sticky-header" style="width:25%;">미청구</th>
													<th colspan="3" class="sticky-header" style="width:25%;">청구</th>
													<th rowspan="2" class="sticky-header" style="width:10%;"></th>							
												</tr>
												<tr>
													<th class="sticky-header2">BL Cnt</th>
													<th class="sticky-header2">기타출고</th>
													<th class="sticky-header2">Total</th>
													<th class="sticky-header2">BL Cnt</th>
													<th class="sticky-header2">기타출고</th>
													<th class="sticky-header2">Total</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${dataList}" var="dataList" varStatus="status">
													<tr>
														<td style="text-align:center;">${status.count}</td>
														<td style="text-align:center;">${dataList.user_id}</td>
														<td style="text-align:center;">${dataList.company_name}</td>
														<td style="text-align:right;">
															<c:if test="${dataList.unprice_bl_cnt eq 0}">-</c:if>
															<c:if test="${dataList.unprice_bl_cnt ne 0}">${dataList.unprice_bl_cnt}</c:if>
														</td>
														<td style="text-align:right;">
															<c:if test="${dataList.unprice_etc_cnt eq 0}">-</c:if>
															<c:if test="${dataList.unprice_etc_cnt ne 0}">${dataList.unprice_etc_cnt}</c:if>
														</td>
														<td style="text-align:right;background:WhiteSmoke;font-weight:bold;">
															<c:if test="${dataList.unprice_cnt eq 0}">-</c:if>
															<c:if test="${dataList.unprice_cnt ne 0}">${dataList.unprice_cnt}</c:if>
														</td>
														<td style="text-align:right;">
															<c:if test="${dataList.price_bl_cnt eq 0}">-</c:if>
															<c:if test="${dataList.price_bl_cnt ne 0}">${dataList.price_bl_cnt}</c:if>
														</td>
														<td style="text-align:right;">
															<c:if test="${dataList.price_etc_cnt eq 0}">-</c:if>
															<c:if test="${dataList.price_etc_cnt ne 0}">${dataList.price_etc_cnt}</c:if>
														</td>
														<td style="text-align:right;background:WhiteSmoke;font-weight:bold;">
															<c:if test="${dataList.price_cnt eq 0}">-</c:if>
															<c:if test="${dataList.price_cnt ne 0}">${dataList.price_cnt}</c:if>
														</td>
														<td style="text-align:center;">
															<input type="button" class="price-app-btn" value="price apply">
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		

		<script type="text/javascript">
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			
			jQuery(function($) {
				$(document).ready(function() {
					
					$("#dates").daterangepicker({
						opens: 'right',
						locale: {
							format: 'YYYYMMDD'
						},
						startDate: fromDate,
						endDate: toDate
					}, function(start, end, label) {
						$("#fromDate").val(start.format('YYYYMMDD'));
						$("#toDate").val(end.format('YYYYMMDD'));
					});

					
				});

				$("#search").click(function() {
					console.log($("#fromDate").val());
					console.log($("#toDate").val());
				});
			});

		</script>
	</body>
</html>
