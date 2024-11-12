<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
   	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		#return-table th, td {
			text-align: center;
			word-break: break-all;
		}
		
		#return-table thead th {
			background-color: #527d96;
			color: #fff;
		}
		
		#return-item-table th, td {
			text-align: center;
			word-break: break-all;
		}
		
		#return-item-table thead th {
			background-color: #527d96;
			color: #fff;
		}
		
		#dispIn-table th, td {
			text-align: center;
		}
		
		#dispIn-table th {
			background-color: #527d96;
			color: #fff;
		}
		
		#dispIn-table td input[type="text"], input[type="number"] {
			width: 100%;
			height: 28px;
			font-size: 12px;
			border-radius: 7px !important;
		}
		
		#dispIn-table td select {
			width: 100% !important;
			height: 28px;
			font-size: 12px;
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
								폐기 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<br>
					<div class="page-content">
						<div id="inner-content-side">
							<form id="returnForm" name="returnForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="nno" id="nno" value="${nno}">
								<table class="table table-bordered table-hover" id="return-table">
									<thead>
										<tr>
											<th style="width:20%;">업체명</th>
											<th style="width:25%;">담당자명</th>
											<th style="width:25%;">담당자 Tel</th>
											<th style="width:30%;">담당자 Email</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${orderInfo.cneeName}</td>
											<td>${orderInfo.attnName}</td>
											<td>${orderInfo.attnTel}</td>
											<td>${orderInfo.attnEmail}</td>
										</tr>
									</tbody>
								</table>
								<table class="table table-bordered table-hover" id="return-item-table">
									<thead>
										<tr>
											<th style="width:70%;">상품명</th>
											<th style="width:15%;">요청수량</th>
											<th style="width:15%;">입고수량</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${itemList}" var="itemList">
										<tr>
											<td>${itemList.itemDetail}</td>
											<td>${itemList.itemCnt}</td>
											<td>${itemList.whInCnt}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<table class="table table-bordered table-hover" id="dispIn-table">
									<tr>
										<th style="width:20%;" class="align-middle">USER ID</th>
										<td style="width:30%;padding:0;" class="align-middle">
											<input type="text" name="userId" id="userId" readonly value="${orderInfo.userId}">
										</td>
										<th style="width:20%;" class="align-middle">* 일시</th>
										<td style="width:30%;padding:0;" class="align-middle">
											<input type="text" name="etcDate" id="etcDate" readonly>
										</td>
									</tr>
									<tr>
										<th class="align-middle">HawbNo</th>
										<td class="align-middle" style="padding:0;"><input type="text" name="hawbNo" id="hawbNo" value="" readonly></td>
										<th class="align-middle">구분</th>
										<td class="align-middle" style="padding:0;">
											<select name="etcType" id="etcType">
											<c:forEach items="${etcTypeList}" var="etcTypeList" varStatus="status">
												<option value='${etcTypeList.code}'
													<c:if test="${'INSP_TRASH' == etcTypeList.code}">selected</c:if>>
													${etcTypeList.name}
												</option>
											</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th class="align-middle">* 금액</th>
										<td class="align-middle" style="padding:0;"><input type="number" name="etcValue" id="etcValue"></td>
										<th class="align-middle">화폐단위</th>
										<td class="align-middle" style="padding:0;"><input type="text" name="etcCurrency" id="etcCurrency" value="KRW"></td>
									</tr>
									<tr>
										<th class="align-middle">비고</th>
										<td colspan="3" class="align-middle" style="padding:0;">
											<input type="text" name="remark" id="remark">
										</td>
									</tr>
								</table>
								<div style="width:100%;text-align:right;">
									<input type="button" class="btn btn-sm btn-white" value="등록" id="regist-btn">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
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
      
	  <script src="/assets/js/jquery-ui.min.js"></script>
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

				});

				$("#regist-btn").click(function() {
					var formData = $("#returnForm").serialize();
					$.ajax({
						url : '/mngr/return/dispIn',
						type : 'POST',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : formData,
						success : function(response) {
							alert("등록 되었습니다.");
							window.close();
						},
						error : function(xhr, status) {
							alert("등록에 실패 하였습니다");
							return;
						}
					});
				});

				$("#etcDate").datepicker({
					autoclose: true,
					todayHightlight: true,
					dateFormat: 'yy-mm-dd'
				});
			});
		</script>
		<!-- script addon end -->
		
	</body>
</html>
