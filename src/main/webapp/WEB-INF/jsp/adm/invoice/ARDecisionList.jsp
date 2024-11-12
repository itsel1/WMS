<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
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
							<li class="active">A/R Control</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									A/R Control
								</h1>
							</div>
							<form name="uploadAramexExcel" id="cond_form" enctype="multipart/form-data">
								<table class="table table-bordered" style="width:30%" >
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
								</table>
							</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="col-xs-12 col-md-12" style="text-align: left;">
									</div>
									<div class="col-xs-12 col-md-12" style="text-align: right;">
										<!-- <button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_popupEtc('')">등록하기</button> -->
									</div>
								</form>
								<br>
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack" style="width:160px;">
													USER_ID
												</th>
												<th class="center colorBlack" style="width:160px;">
													Inv Month
												</th>
												<th class="center colorBlack" style="width:160px;">
													Inv No
												</th>
												<th class="center colorBlack" style="width:160px;">
													기 간
												</th>
												<th class="center colorBlack" style="width:160px;">
													Amount
												</th>
												<th class="center colorBlack">
													Received
												</th>
													<th class="center colorBlack">
													Balance
												</th>
												<th class="center colorBlack">
													Bl Cnt
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="list" varStatus="status">
											<tr>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.userId}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.comName}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   <a href="#"  onclick="fn_popupCollect('${list.userId}','${list.invoiceNo}')">${list.invoiceNo}</a>
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.invFromDate} ~   ${list.invToDate}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.invAmt}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.receivedAmt}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.BalanceAmt}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
												   ${list.blCnt}
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
									<form id="mgrMsgForm" name="mgrMsgForm">
									</form>								
								</div>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {

					$("#invoicethMenu").toggleClass('open');
					$("#ARDecisionList").toggleClass('active');

					$("#btn_search").on('click',function(e){
						$("#cond_form").submit();
					})
					
					$("#etcType").on('change',function(e){
						$("#cond_form").submit();
					})
				})
			})
		
			function fn_popupCollect(userId,invoiceNo){
				var _width = '1220';
			    var _height = '580';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
				window.open("/mngr/invoice/popupInvoiceCollect?invoiceNo="+invoiceNo+"&userId="+userId,"Invoice Collect",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			} 
			
		</script>
		<!-- script addon end -->

	</body>
</html>
