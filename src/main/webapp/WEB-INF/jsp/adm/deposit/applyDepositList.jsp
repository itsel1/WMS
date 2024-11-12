<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>
		
		<style type="text/css">
		  input[type="text"] {
		  	border: none;
		  	width: 100%;
		  	height: 36px;
		  }
		  
		  #stationCode {
		  	border: none;
		  	width: 100%;
		  	height: 36px;
		  	font-weight: normal;
		  }
		  
		  #searchBtn {
		  	background: none;
		  	border: none;
		  }
		  
		  #recBtn {
		  	border: none;
		  	background: none;
		  }
		  
		</style>
		
		<title>예치금 관리</title>
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
	</head>
	<body class="no-skin">
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
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
								예치금 관리
							</li>
							<li class="active">예치금 신청</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								예치금 신청 내역
							</h1>
						</div>
						<div id="inner-content-side" >
							<form id="searchForm" id="searchForm">
								<div class="search-content">
									<table id="search-table" class="table table-bordered" style="width:40%;">
										<thead>
											<tr>
												<th class="center">Date</th>
												<th style="width:15%; padding:0px;">
													<input type="text" name="fromDate" id="fromDate" value="${params.fromDate}" autocomplete="off">
												</th>
												<th class="center">~</th>
												<th style="width:15%; padding:0px;">
													<input type="text" name="toDate" id="toDate" value="${params.toDate}" autocomplete="off">
												</th>
												<th class="center">Station</th>
												<th style="width:20%; padding:0px;">
													<select name="stationCode" id="stationCode">
														<option value="all">::전체::</option>
														<c:forEach items="${stationList}" var="stationList">
															<option <c:if test="${params.stationCode eq stationList.stationCode}"> selected</c:if> value="${stationList.stationCode}">${stationList.stationName}</option>
														</c:forEach>
													</select>
												</th>
												<th class="center" style="width:5%;">
													<button type="submit" id="searchBtn">
														<i class="fa fa-search" style="font-size:16px;"></i>
													</button>
												</th>
											</tr>
										</thead>
									</table>
								</div>
							</form>
							<div class="row hr-8">
								<button type="button" class="btn btn-sm btn-white" onclick="fn_registDepositAppyl()">신청 등록</button>
							</div>
							<div class="list-content" style="background:#fff;">
								<table id="main-table" class="table table-bordered table-striped" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th class="center">No</th>
											<th class="center">USER ID</th>
											<th class="center">Station</th>
											<th class="center">입금예정일</th>
											<th class="center">예금주명</th>
											<th class="center">신청 금액</th>
											<th class="center">입금일</th>
											<th class="center">입금액</th>
											<th class="center">통화</th>
											<th class="center">환율</th>
											<th class="center">예치금</th>
											<th class="center">Total AMT</th>
											<th class="center"></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!empty depositList}">
												<c:forEach items="${depositList}" var="list" varStatus="status">
													<tr>
														<td class="center">${status.count}</td>
														<td class="center">${list.userId}</td>
														<td class="center">${list.orgStation}</td>
														<td class="center">${list.applyDate}</td>
														<td class="center">${list.depositor}</td>
														<td class="center">
															<c:if test="${orgStation eq '082'}">
																<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.applyAmt}"/>
															</c:if>
															<c:if test="${orgStation ne '082'}">
																${list.applyAmt}
															</c:if>
														</td>
														<td class="center">${list.receivedDate}</td>
														<td class="center">
															<c:if test="${list.receivedAmt eq '0.0'}">
																
															</c:if>
															<c:if test="${list.receivedAmt ne '0.0'}">
																<c:if test="${orgStation eq '082'}">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.receivedAmt}"/>
																</c:if>
																<c:if test="${orgStation ne '082'}">
																	${list.receivedAmt}
																</c:if>
															</c:if>
														</td>
														<td class="center">${list.depositCurrency}</td>
														<td class="center">
															<c:if test="${list.exchangeRate eq '0.0'}">
																	
															</c:if>
															<c:if test="${list.exchangeRate ne '0.0'}">
																${list.exchangeRate}
															</c:if>
														</td>
														<td class="center">
															<c:if test="${list.depositAmt eq '0.0'}">
																	
															</c:if>
															<c:if test="${list.depositAmt ne '0.0'}">
																<c:if test="${orgStation eq '082'}">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.depositAmt}"/>
																</c:if>
																<c:if test="${orgStation ne '082'}">
																	${list.depositAmt}
																</c:if>
															</c:if>
														</td>
														<td class="center">
															<c:if test="${list.depositAmtTotal eq '0.0'}">
																	
															</c:if>
															<c:if test="${list.depositAmtTotal ne '0.0'}">
																<c:if test="${orgStation eq '082'}">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.depositAmtTotal}"/>
																</c:if>
																<c:if test="${orgStation ne '082'}">
																	${list.depositAmtTotal}
																</c:if>
															</c:if>
														</td>
														<td class="center">
															<input type="button" id="recBtn" onclick="fn_depositDel('${list.idx}', '${list.userId}')"  value="[삭제]">
															<input type="button" id="recBtn" onclick="fn_depositDetail('${list.idx}', '${list.userId}')"  value="[입금처리]">
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="13" class="center">
														조회 결과가 없습니다.
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
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
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				
				$(document).ready(function() {
					$("#16thMenu").toggleClass('open');
					$("#16thMenu").toggleClass('active');
					$("#16th-1").toggleClass('active');
				})
			})
			
				
			$('#fromDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			}).on('changeDate', function(e) {
				$("#toDate").val($("#fromDate").val());
			});

			$('#toDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			});

			function fn_depositDetail(idx, userId) {

				location.href = "/mngr/deposit/depositApplyDetail?idx="+idx+"&userId="+userId;

			}

			function fn_registDepositAppyl() {
				var _width = '480';
			    var _height = '480';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			    
				window.open("/mngr/deposit/popupApplyDeposit","CODE",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}

			$("#main-table").DataTable();

			function fn_depositDel(idx, userId) {
				var formData = {idx: idx, userId: userId};

				if (confirm("정말 삭제 하시겠습니까?")) {
					$.ajax({
						url : '/mngr/deposit/delDepositRecInfo',
						type : 'GET',
						data : formData,
						success : function(data) {
							location.reload();
						},
						error : function(xhr, status) {
							alert("삭제에 실패 하였습니다.");
						}
					})	
				} else {
					return false;
				}
			}
		</script>
		<!-- script addon end -->
	</body>
</html>