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
.padding0 {
	padding: 0px !important;
}

.colorBlack {
	color: #000000 !important;
}

.table>tbody>tr>td {
	vertical-align: middle !important;
}

.profile-info-name {
	padding: 0px;
	color: black !important;
	text-align: center !important;
}

.profile-user-info-striped {
	border: 0px solid white !important;
}
/* .profile-user-info-striped{border: 0px solid white !important;}
	  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
.profile-user-info {
	width: 100% !important;
	margin: 0px;
}

/* .col-xs-12 .col-sm-12 {
	padding: 0px !important;
}
 */
.tdBackColor {
	background-color: #FFFFFF;
}

.tdHeader {
	border: 1px solid black;
	padding: 0px !important;
}

.tdHeaderB {
	border: 1px solid black;
	border-bottom: none;
}

.tdHeaderT {
	border: 1px solid black;
	border-top: none;
}

.tdHeaderL {
	border: 1px solid black;
	border-left: none;
	border-right: none;
}

.tdHeaderR {
	border: 1px solid black;
	border-right: none;
	border-left: none;
}

.tdHeaderTR {
	border: 1px solid black;
	border-top: none;
}

@media ( min-width :768px) {
	.tdHeaderB {
		border: 1px solid black;
		border-bottom: none;
	}
	.tdHeaderT {
		border: 1px solid black;
		border-top: none;
	}
	.tdHeaderL {
		border: 1px solid black;
		border-left: none;
	}
	.tdHeaderR {
		border: 1px solid black;
		border-right: none;
	}
	.tdHeaderTR {
		border: 1px solid black;
		border-right: none;
		border-top: none;
	}
}

@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
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
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								Invoice
							</li>
							<li class="active">Invoice List</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									Invoice List
								</h1>
							</div>
							<form name="uploadAramexExcel" id="cond_form" enctype="multipart/form-data">
								<table class="table table-bordered" style="width: 800px;">
									<thead>
										<tr>
											<th class="center colorBlack" style="width: 50px;">
												<i class="fa fa-calendar bigger-110"></i>
											</th>
											<td style="padding:0px;width:200px;height:20px;">
												<div style="height:100%; width:100%;" class="input-group input-daterange">
													<input type="text" style="width:100%; height:100%;" class="form-control" name="invMonth" id="invMonth" value="${parameterInfo.invMonth}"/>
												</div>
											</td>
											<th class="center colorBlack" style="width: 100px;">발송 구분</th>
											<td style="padding:0px;width:100px;height:20px;">
												<select style="width:100%;height:100%;" name="dlvType" id="dlvType">
													<option <c:if test="${parameterInfo.dlvType == ''}"> selected</c:if> value=''>
													::전체::
													</option>
													<option <c:if test="${parameterInfo.dlvType == 'In'}"> selected</c:if> value='In'>
													국내발송
													</option>
													<option <c:if test="${parameterInfo.dlvType == 'Out'}"> selected</c:if> value="Out">
													해외발송
													</option>
												</select>
											</td>
											<th class="center colorBlack" style="width: 100px;">USER ID</th>
											<td style="padding:0px;height:20px;width:180px;">
												<input type="text" style="width:190px; height:100%;" value="${parameterInfo.userId}" id="userId" name="userId" /> 
											</td>
											<td style="width:50px; text-align:center;">
												<input type="submit" style="text-align:center;" value="검색" id="searchBtn">
											</td>
										</tr>
									</thead>
								</table>
								<%-- <table class="table table-bordered" style="width:30%" >
									<thead>
									<tr>
										<th class="center colorBlack">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<td style="padding:0px;vertical-align: middle;text-align: left">
											<input type="button" id="btn_search" value="조회">
										</td>
									</tr>
									</thead>
								</table> --%>
							</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div style="text-align: left;">
										<!-- <button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_popupEtc('')">등록하기</button> -->
									</div>
								</form>
								<br/>
								<div id="table-contents" class="table-contents" style="overflow: auto; width:80%;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered" style="width:1200px;">
										<thead>
											<tr>
												<th class="center colorBlack" style="width:160px;">
													USER_ID
												</th>
												<th class="center colorBlack" style="width:130px;">
													Invoice Month
												</th>
												<th class="center colorBlack" style="width:160px;">
													Invoice No
												</th>
												<th class="center colorBlack" style="width:180px;">
													기 간
												</th>
												<th class="center colorBlack" style="width:80px;">
													Bl Cnt
												</th>
												<th class="center colorBlack" style="width:80px;">
													ETC Cnt
												</th>
												<th class="center colorBlack" style="width:150px;">
													금액
												</th>
												<th class="center colorBlack">
													
												</th>
												<th class="center colorBlack" style="width:180px;">
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="list" varStatus="status">
											<tr>
												<td class="colorBlack" style="text-align:center;">
												   ${list.USER_ID}
												</td>
												<td class="colorBlack" style="text-align:center;">
												   ${list.INV_MONTH}
												</td>
												<td class="colorBlack" style="text-align:center;">
												   ${list.INVOICE_NO}
												</td>
												<td class="colorBlack" style="text-align:center;">
												   ${list.FROM_DATE} ~ ${list.TO_DATE}
												</td>
												<td class="colorBlack" style="text-align:right;">
													${list.BL_CNT}
												</td>
												<td class="colorBlack" style="text-align:right;">
													${list.ETC_ORDER_CNT}
												</td>
												<td class="colorBlack" style="text-align:right;">
													<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.TOTAL_INV_AMT}"/>
												</td>
												<td class="colorBlack" style="text-align:center;">
													<a href="#" onclick="fn_Unposting('${list.ORG_STATION}','${list.USER_ID}','${list.INVOICE_NO}')">[Unposting]</a>
												</td>
												<td class="colorBlack" style="text-align:center;">
												    <input type="button" value="Invoice" onclick="fn_InvExcelDown('${list.ORG_STATION}','${list.USER_ID}','${list.INVOICE_NO}')">
												    <input type="button" value="Data" onclick="fn_DataExcelDown('${list.ORG_STATION}','${list.USER_ID}','${list.INVOICE_NO}')">
												</td>
											</tr>
											</c:forEach>
										</tbody>
										
									</table>
									<form id="excelDown" action="/mngr/invoice/invExcelDown" method="get">
										<input type="hidden" id = "excelDownorgStation" name="orgStation" value="">
										<input type="hidden" id = "excelDownuserId" name="userId" value="">
										<input type="hidden" id = "excelDownInvoiceNo" name="invoiceNo" value="">
									</form>
								</div>
								<div style="text-align: center;">
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
							</div>
							</div>
					</div>
				</div>
			</div><!-- /.main-content -->
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
		
			<!-- script datepicker -->
		
		<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>

		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
						
					$("#invoicethMenu").toggleClass('open');
					$("#invoice2").toggleClass('active');
					$("#btn_search").on('click',function(e){
						$("#cond_form").submit();
					})
					
					$("#etcType").on('change',function(e){
						$("#cond_form").submit();
					})
				})
			})
			
			function fn_Unposting(orgStation,userId,invoiceNo){

				var result = confirm('정말 삭제 하시겠습니까?');
				if(!result) {
					return false;
				}
				
				var parameter = new Object();
				parameter.orgStation = orgStation;
				parameter.userId = userId;
				parameter.invoiceNo = invoiceNo;

				$.ajax({
					url:'/mngr/invoice/invoceUnposting',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: parameter, 
					success : function(data) {
						var Status = data.rstStatus;

						if(Status == "SUCCESS"){
							alert(data.rstMsg);
							location.reload();
						}else{
							alert(data.rstMsg);
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				});

			}

			function fn_InvExcelDown(orgStation,userId,invoiceNo){
				$("#excelDown").attr("action","/mngr/invoice/invExcelDown");
				$("#excelDownuserId").val(userId);
				$("#excelDownInvoiceNo").val(invoiceNo);
				$("#excelDown").submit();				
			}
			
			function fn_DataExcelDown(orgStation,userId,invoiceNo){
				$("#excelDown").attr("action","/mngr/invoice/dataExcelDown");
				$("#excelDownorgStation").val(orgStation);
				$("#excelDownuserId").val(userId);
				$("#excelDownInvoiceNo").val(invoiceNo);
				$("#excelDown").submit();				
			}

			$("#excelTest").on('click', function() {
				$("#excelDown").attr('action', '/mngr/invoice/invExcelDown');
				$("#excelDownorgStation").val("082");
				$("#excelDownuserId").val("korpsa");
				$("#excelDownInvoiceNo").val("20220430_082_KORPSA");
				$("#excelDown").submit();
			})
			
			$('#input[name=date-range-picker]').daterangepicker({
				'applyClass' : 'btn-sm btn-success',
				'cancelClass' : 'btn-sm btn-default',
				locale: {
					applyLabel: 'Apply',
					cancelLabel: 'Cancel',
				}
			});
		
			$('.input-daterange').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymm',
			});
		</script>
		<!-- script addon end -->

	</body>
</html>
