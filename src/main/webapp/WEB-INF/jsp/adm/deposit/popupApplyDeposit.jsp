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
		 	#table-header th {
		 		text-align: center;
		 	}
		 	
		 	#table-body input {
		 		width:100%;
		 		height:36px;
		 	}
		 	
		</style>
		
		<title>예치금 관리</title>
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="/assets/css/chosen.min.css" />
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
								예치금 관리
							</li>
							<li class="active">예치금 신청</li>
						</ul>
					</div>
					<div class="page-content">
						<div id="inner-content-side" >
							<form id="searchForm" id="searchForm">
								<div class="search-content">
									<div class="page-header" style="margin-top:10px; margin-bottom:8px;">
										<table style="width:96%;" id="table-header">
											<tr style="height:25px;">
												<th style="width:25%; height:25px;">Station</th>
												<td style="padding:0px;">
													<select class="chosen-select-start form-control tag-input-style" id="orgStation" name="orgStation" style="width:100%;">
													<c:forEach items="${stationList}" var="stationList">
														<option <c:if test="${stationList.stationCode eq orgStation}"> selected </c:if> value="${stationList.stationCode}">${stationList.stationName} / ${stationList.stationCode}</option>
													</c:forEach>
												</select>
												</td>
											</tr>
											<tr style="height:10px;"></tr>
											<tr style="height:25px;">
												<th style="width:25%; height:25px;" class="center">User ID</th>
												<td style="padding:0px;">
													<select class="chosen-select-start form-control tag-input-style col-xs-12" id="userId" name="userId" style="width:100%;">
														<c:forEach items="${userList}" var="userList">
															<option value="${userList.userId}">${userList.userId}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
										</table>
									</div>
									<div style="margin-top:16px;">
										<table class="table table-bordered" style="width:100%;" id="table-body">
											<thead>
												<tr>
													<th style="width:25%; height:25px;" class="center">입금 예정일</th>
													<td style="padding:0px;">
														<input type="text" name="applyDate" id="applyDate">
													</td>
												</tr>
												<tr>
													<th style="width:25%; height:25px;" class="center">입금 방식</th>
													<td style="padding:0px;">
														<select id="payType" style="width:100%; height:36px;" name="payType">
															<option value="Bank">Bank</option>													
														</select>
													</td>
												</tr>
												<tr>
													<th style="width:25%; height:25px;" class="center">신청 금액</th>
													<td style="padding:0px;">
														<input type="number" name="applyAmt" id="applyAmt" min="0"> 
													</td>
												</tr>
												<tr>
													<th style="width:25%; height:25px;" class="center">통화</th>
													<td style="padding:0px;">
														<select id="receivedCurrency" name="receivedCurrency" style="width:100%; height:36px;">
															<c:forEach items="${currencyList}" var="list">
																<option <c:if test="${list.currency eq 'KRW'}"> selected</c:if> value="${list.currency}">${list.currency} (${list.nationName})</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th style="width:25%; height:25px;" class="center">예금주 명</th>
													<td style="padding:0px;">
														<input type="text" name="depositor" id="depositor">
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<div style="width:100%; text-align:center;">
										<input type="button" id="registBtn" value="등록">
									</div>
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {

				$('#applyDate').datepicker({
					autoclose:true,
					todayHightlight:true,
					format: 'yyyymmdd',
				});
					
			})
			
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

			$("#orgStation").change(function() {
				var code = $("#orgStation").val();

				$.ajax({
					url : '/mngr/deposit/changeUserList',
					type : 'GET',
					data : {stationCode: code},
					success : function(result) {
						$('#userId').empty().trigger('chosen:updated');
						
						var array = result;
						$.each(array, function(index, item) {
							$("#userId").append('<option value="'+item+'">'+item+'</option>');
							$("#userId").trigger("chosen:updated");
						})
					},
					error : function(xhr, status) {
						alert("조회에 실패 하였습니다.");
					}
				})
			})
			
			$("#registBtn").click(function() {
				
				var formData = $("#searchForm").serialize();
				$.ajax({
					url:'/mngr/deposit/popupApplyDeposit',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						window.close();
						opener.parent.location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert("등록에 실패 하였습니다.");
		            }
				})
				
			})
			
		</script>
		<!-- script addon end -->
	</body>
</html>