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
	
			.inputStyle {
				width:100%; 
				height:42px;
			}
			
			.chosen-select-start {
				height: 42px;
			}
			
			#delBtn {
				border: none;
				background: none;
			}
			
			#delBtn:hover {
				font-weight:bold;
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
								예치금 신청 정보
							</h1>
						</div>
						<div id="inner-content-side" >
							<form id="searchForm" id="searchForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="idx" id="idx" value="${idx}" />
								<div class="table-info">
									<table class="table table-bordered" style="width:40%;">
										<thead>
											<tr>
												<th class="center" style="width:20%;">USER ID</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="userId" id="userId" value="${depositInfo.userId}">	
														${depositInfo.userId}
													</td>
												<th class="center" style="width:20%;">회사 명</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="comName" id="comName" value="${depositInfo.comName}">
													${depositInfo.comName}
												</td>
											</tr>
											<tr>
												<th class="center" style="width:20%;">등록일</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="applyDate" id="applyDate" value="${depositInfo.applyDate}">
													${depositInfo.applyDate}
												</td>
												<th class="center" style="width:20%;">예금주 명</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="depositor" id="depositor" value="${depositInfo.depositor}">
													${depositInfo.depositor}
												</td>
											</tr>
											<tr>
												<th class="center" style="width:20%;">신청 금액</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="applyAmt" id="applyAmt" value="${depositInfo.applyAmt}">
													${depositInfo.applyAmt}
												</td>
												<th class="center" style="width:20%;">신청 통화</th>
												<td style="width:30%; background:#fff;">
													<input type="hidden" name="receivedCurrency" id="receivedCurrency" value="${depositInfo.receivedCurrency}">
													${depositInfo.receivedCurrency}
												</td>
											</tr>
										</thead>
									</table>
								</div>
								<br/>
								<div class="received-info">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="center" style="width:6%;">입금일</th>
												<td style="width:10%; padding:0px;">
													<input type="text" name="receivedDate" id="receivedDate" class="inputStyle">
												</td>
												<th class="center" style="width:6%;">입금액</th>
												<td style="width:10%; padding:0px;">
													<input type="number" name="receivedAmt" id="receivedAmt" class="inputStyle">
												</td>
												<th class="center" style="width:6%;">입금 통화</th>
												<td style="width:10%; padding:0px; background:#fff;">
													<select id="depositCurrency" name="depositCurrency" style="width:100%; height:42px;">
														<c:forEach items="${currencyList}" var="currencyList">
															<option <c:if test="${depositInfo.receivedCurrency eq currencyList.currency}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName})</option>
														</c:forEach>
													</select>
												</td>
												<th class="center" style="width:6%;">환율</th>
												<td style="width:10%; padding:0px;">
													<input type="text" name="exchangeRate" id="exchangeRate" class="inputStyle">
												</td>
												<th class="center" style="width:6%;">예치금</th>
												<td style="width:10%; padding:0px;">
													<input type="number" name="depositAmt" id="depositAmt" class="inputStyle">
												</td>
												<th class="center" style="width:6%;">비고</th>
												<td style="width:10%; padding:0px;">
													<input type="text" name="remark" id="remark" class="inputStyle">
												</td>
												<th class="center" style="width:4%;">
													<input type="button" value="등록" id="registBtn">
												</th>
											</tr>
										</thead>
									</table>
								</div>
								<br/>
								<c:if test="${!empty depositInfo.receivedDate}">
									<div class="receivedHis">
										<table class="table table-bordered" style="width:100%;">
											<thead>
												<tr>
													<th colspan="7" class="center">등록 내역</th>
												</tr>
												<tr>
													<th class="center" style="width:10%;">입금일</th>
													<th class="center" style="width:10%;">입금액</th>
													<th class="center" style="width:10%;">통화</th>
													<th class="center" style="width:10%;">환율</th>
													<th class="center" style="width:10%;">예치금</th>
													<th class="center" style="width:40%;">비고</th>
													<th class="center" style="width:10%;"></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td class="center">${depositInfo.receivedDate}</td>
													<td style="text-align:right;">${depositInfo.receivedAmt}</td>
													<td class="center">${depositInfo.depositCurrency}</td>
													<td style="text-align:right;">${depositInfo.exchangeRate}</td>
													<td style="text-align:right;">${depositInfo.depositAmt}</td>
													<td>${depositInfo.remark}</td>
													<td class="center">
														<input type="button" id="delBtn" value="[삭제]">
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</c:if>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!--[if !IE]> -->
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	
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
	
	
			$('#receivedDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			});
	
			if(!ace.vars['touch']) {
				$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
	
			$("#receivedAmt").focusout(function() {
				if ($("#receivedCurrency").val() == $("#depositCurrency").val()) {
					//$("#exchangeRate").attr('readonly', true);
					$("#exchangeRate").val(1);
				}
	
				var rAmt = 0;
				var eRate = 1;
				var result = 0;
	
				if ($("#receivedAmt").val() != '') {
					rAmt = $("#receivedAmt").val();
				}
	
				if ($("#exchangeRate").val() != '') {
					eRate = $("#exchangeRate").val();
				}
	
				result = rAmt / eRate;
				result = result.toFixed(2);
				$("#depositAmt").val(result);
			})
			
			$("#receivedCurrency").change(function() {
				if ($("#receivedCurrency").val() == $("#depositCurrency").val()) {
					//$("#exchangeRate").attr('readonly', true);
				} else {
					$("#exchangeRate").val('');
					$("#exchangeRate").attr('readonly', false);
				}
	
				if ($("#receivedCurrency").val() == 'KRW') {
					var rAmt = 0;
					var eRate = 1;
					var result = 0;
	
					if ($("#receivedAmt").val() != '') {
						rAmt = $("#receivedAmt").val();
					}
					if ($("#exchangeRate").val() != '') {
						eRate = $("#exchangeRate").val();
					}
					result = rAmt/eRate;
					result = result.toFixed(2);
					$("#depositAmt").val(result);
				}
			})
			
		  	$("#exchangeRate").change(function() {
		        var rAmt = 0;
		        var eRate = 1;
		        var result = 0;
		
		        if($("#receivedAmt").val() != ""){
		            rAmt = $("#receivedAmt").val();
		        }
		        if($("#exchangeRate").val() != ""){
		            eRate = $("#exchangeRate").val();
		        }
		
		        result = rAmt/eRate;
		        result = result.toFixed(2);
		
		        $('#depositAmt').val(result);
			});
			
			$("#registBtn").click(function() {

				if ($("#receivedDate").val() == '') {
					alert("입금일을 입력해 주세요.");
					return false;
				}

				if ($("#receivedAmt").val() == '') {
					alert("입금액을 입력해 주세요.");
					return false;
				}

				if ($("#exchangeRate").val() == '') {
					alert("환율을 입력해 주세요.");
					return false;
				}

				if ($("#depositAmt").val() == '') {
					alert("예치금을 입력해 주세요.");
					return false;
				}

				$("#searchForm").attr("action", "/mngr/deposit/registDepositApply");
				$("#searchForm").attr("method", "post");
				$("#searchForm").submit();
			})
			
			$("#delBtn").click(function() {
				$("#searchForm").attr('action', '/mngr/deposit/registDepositDel');
				$("#searchForm").attr('method', 'post');
				$("#searchForm").submit();
			})

		</script>
		<!-- script addon end -->
	</body>
</html>