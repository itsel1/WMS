<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		.colorBlack {color:#000000 !important;}


		#table-header {
			width: 80%;
			overflow: auto;
			background: rgb(118,129,179);
			background: linear-gradient(90deg, rgba(118,129,179,1) 0%, rgba(195,174,201,1) 48%, rgba(221,190,190,1) 100%)

		}
		
		#table-body {
			width: 80%;
			height:300px;
			overflow-x:auto;
			margin-top: 0px;

		}
		
		table {
			width: 100%;
			table-layout: fixed;
		}
		
		th {
		  	padding: 15px 15px;
			text-align: center;
			font-weight: 500;
			font-size: 13px;
			color: #fff;
			text-transform: uppercase;
		}
		
		td {
			padding: 15px;
	  		text-align: center;
	  		vertical-align:middle;
	  		font-size: 13px;
 			border-bottom: solid 1px rgba(117, 128, 179, 0.28);
		}
		
		tbody tr:hover {
			background-color: rgba(185, 194, 198, 0.1);
		}
		
		::-webkit-scrollbar {
		    width: 6px;
		} 
		::-webkit-scrollbar-track {
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
		} 
		::-webkit-scrollbar-thumb {
		    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
		}
		
		.select-box {
			display: flex;
			width: 100px;
			flex-direction: column;
		}

		#regist-section {
			width: 80%;
			text-align: right;
			padding-right: 10px;
			margin-bottom: 10px;
		}
		
		.select-box .options-container {
			background: #7A6F95;
			color: #f5f6fa;
			max-height: 0px;
			width: 100%;
			opacity: 0;
			transition: all 0.4s;
			border-radius: 8px;
			overflow: hidden;
			
			order: 1;
		}
		
		.selected {
			background: #7A6F95;
			border-radius: 8px;
			margin-bottom: 8px;
			color: #f5f6fa;
			position: relative;
			order: 0;
		}
		
		.selected::after {
			content: "";
			background: url("/image/expand-more.png");
			background-size: contain;
			background-repeat: no-repeat;
			
			position: absolute;
			height: 100%;
			width: 32px;
			right: 10px;
			top: 5px;
			
			transition: all 0.4s;
		}
		
		.select-box .options-container.active {
			max-height: 240px;
			opacity : 1;
			overflow-y: scroll;
		}
		
		.select-box .options-container.active + .selected::after {
			transform: rotateX(180deg);
			top: -6px;
		}
		
		.select-box .options-container::-webkit-scrollbar {
			width: 8px;
			background: #84799F;
			border-radius: 0 8px 8px 0;
		}
		
		.select-box .options-container::-webkit-scrollbar-thumb {
			background: #B4B4DC;
			border-radius: 0 8px 8px 0; 
		}
		
		.select-box .option, .selected {
			padding: 12px 24px;
			cursor: pointer;
		}
		
		.select-box .option:hover {
			background: #B4B4DC;
		}
		
		.select-box label {
			cursor: pointer;
		}
		
		.select-box .option .radio {
			display: none;
		}
		
		.registBtn {
			background-color: #ddbebe;
			border: 1px solid #ddbebe;
			border-radius: 4px;
			box-shadow: rgba(0, 0, 0, 0.1) 0 2px 4px 0;
			box-sizing: border-box;
			color: #fff;
			cursor: pointer;
			font-size: 14px;
			font-weight: 400;
			outline: none;
			outline: 0;
			padding: 5px 20px;
			text-align: center;
			transform: translateY(0);
			transition: transform 150ms, box-shadow 150ms;
		}

		.registBtn:hover {
			box-shadow: rgba(0, 0, 0, 0.15) 0 3px 9px 0;
			transform: translateY(-2px);
		}
		
		
		
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>반품 회수지 관리</title>
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
			
			<div class="main-content"">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								반품
							</li>
							<li class="active">반품 회수지 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content" style="background:white;">
						<div class="page-header">
							<h1>
								반품 회수지 관리
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="search-button" id="search-botton">
								<input type="hidden" name="stationCode" id="stationCode" value="${params.stationCode}"/> 
								<br/>
								<div id="search-section">
									<div>
										<div class="select-box">
											<div class="options-container">
												<div class="option">
													<input type="radio" class="radio" id="All">
													<label for="All">ALL</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="SEL">
													<label for="SEL">082</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="LHR">
													<label for="LHR">441</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="LAX">
													<label for="LAX">213</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="FRA">
													<label for="FRA">049</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="DXB">
													<label for="DXB">472</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="SIN">
													<label for="SIN">460</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="NYC">
													<label for="NYC">718</label>
												</div>
												<div class="option">
													<input type="radio" class="radio" id="LAS">
													<label for="LAS">211</label>
												</div>
											</div>
											<div class="selected">
												ALL
											</div>
										</div>
									</div>
								</div>
								<div id="regist-section">
									<input type="button" class="registBtn" onclick="fn_registAddr()" value="등록" />
								</div>
								<div id="table-header">
									<table>
										<thead>
											<tr>
												<th style="width:5%;">NO</th>
												<th style="width:15%;">Station Code</th>
												<th style="width:15%;">Station Name</th>
												<th style="width:45%;">Return Address</th>
												<th style="width:10%;">Use YN</th>
												<th style="width:10%;">User ID</th>
												<th style="width:10%;"></th>
											</tr>
										</thead>
									</table>
								</div>
								<div id="table-body">
									<table>
										<tbody>
											<c:if test="${!empty stationList}">
												<c:forEach items="${stationList}" var="list" varStatus="status">
													<tr>
														<td style="width:5%;">${status.count}</td>
														<td style="width:15%;">${list.STATION_CODE}</td>
														<td style="width:15%;">${list.STATION_NAME}</td>
														<td style="width:45%;">(${list.STATION_ZIP})&nbsp;&nbsp;${list.STATION_ADDR} ${list.STATION_ADDR_DETAIL}</td>
														<td style="width:10%;">${list.USE_YN}</td>
														<td style="width:10%;">${list.W_USER_ID}</td>
														<td style="width:10%;">
															<a href="" style="text-decoration:none;">
																<img src="/image/edit-icon.png" style="height:20px;"/>
															</a>
															&nbsp;&nbsp;
															<a href="" style="text-decoration:none;">
																<img src="/image/delete-icon.png" style="height:20px;"/>
															</a>
														</td>
													</tr>
												</c:forEach>
											</c:if>
											<c:if test="${empty stationList}">
												<tr>
													<td colspan="7">조회 데이터가 없습니다</td>
												</tr>
											</c:if>
										</tbody>
									</table>
								</div>
							</form>
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thTwo-deposit").toggleClass('active'); 

					var station = $("#stationCode").val();
					if (station == "") {
						document.querySelector(".selected").innerHTML = "ALL";
					} else {
						document.querySelector(".selected").innerHTML = station;
					}
				});
			})
			
			const selected = document.querySelector(".selected");
			const optionsContainer = document.querySelector(".options-container");
			const optionsList = document.querySelectorAll(".option");

			selected.addEventListener("click", () => {
				optionsContainer.classList.toggle("active");
			});


			optionsList.forEach(i => {
				i.addEventListener("click", () => {
					const label = i.querySelector("label").innerHTML;
					selected.innerHTML = label;

					$("#stationCode").val(label);
					$("#search-botton").attr("action", "/mngr/aplctList/return/stationList");
					$("#search-botton").attr("method", "GET");
					$("#search-botton").submit();
					
					optionsContainer.classList.remove("active");
				})
			})
			
			function fn_registAddr() {
				window.open("/mngr/aplctList/return/registStation", "PopupWin", "width=600,height=700");
			}
			
		</script>
		<!-- script addon end -->
	</body>
</html>
