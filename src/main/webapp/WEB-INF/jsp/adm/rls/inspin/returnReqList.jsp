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
								검품
							</li>
							<li class="active">추가 입고 재고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									추가 입고 재고
								</h1>
							</div>
							<form  id="searchFom" method="get">
								<table class="table table-bordered" style="width:100%" >
									<thead>
									<tr>
										<th class="center colorBlack" style="width:100px;">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										
										<th class="center colorBlack" style="width:100px;">Trk Com</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="trkCom" value="${parameterInfo.takeinCode}" >
										</td>
										<th class="center colorBlack" style="width:150px;">Trk No</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="trkNo" value="${parameterInfo.cusItemCode}">
										</td>
										<th class="center colorBlack" style="width:150px;">상품명</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="itemDetail" value="${parameterInfo.itemDetail}">
										</td>
										<td style="padding:0px;">
											<input type="submit" value="조회">
										</td>
									</tr>
									</thead>
								</table>
							</form>
							
							<form enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div style="text-align: left;width:45%;float:left">
									    <input type="button" class="btn btn-white btn-inverse btn-xs"  value="출고 재고" onclick="fn_takeinStockList()"/>
									</div>
									<!-- <div style="text-align: right;width:45%;float:right">
										<button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_takeinBlScan()">출고작업</button>
									</div> -->
							</form>
								<br>
							
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack">
													No
												</th>
												<th class="center colorBlack">
													USER_ID
												</th>
												<th class="center colorBlack">
													Return No
												</th>
												<th class="center colorBlack">
													수취인 명
												</th>
												<th class="center colorBlack">
													Tel
												</th>
												<th class="center colorBlack">
													Hp
												</th>
												<th class="center colorBlack">
													Email
												</th>
												<th class="center colorBlack">
													우편번호
												</th>
												<th class="center colorBlack">
													수취인 주소
												</th>
												<th class="center colorBlack">
													출고 승인여부
												</th>
												<th class="center colorBlack">
													
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${rstList}" var="rstList" varStatus="status">
											<tr>
												<td class="center colorBlack">
													${rstList.nno}
												</td>
												<td class="center colorBlack">
													${rstList.userId}
												</td>
												<td class="center colorBlack">
													${rstList.returnNo}
												</td>
												<td class="center colorBlack">
													${rstList.cneeName}
												</td>
												<td class="center colorBlack">
													${rstList.cneeTel}
												</td>
												<td class="center colorBlack">
													${rstList.cneeHp}
												</td>
												<td class="center colorBlack">
													${rstList.cneeEmail}
												</td>
												<td class="center colorBlack">
													${rstList.cneeZip}
												</td>
												<td class="center colorBlack">
													${rstList.cneeAddr} ${rstList.cneeAddrAddr}
												</td>
												<td class="center colorBlack">
													${rstList.apprvYn}
												</td>
												<td class="center colorBlack">
													<input type="button" value="출고 작업" onclick="fn_popupReturnStockOut('${rstList.orgStation}','${rstList.userId}','${rstList.nno}');">  
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
									<form id="stockForm" action="">
									<input type="hidden" id = "orgStation" name="orgStation" value="">
									<input type="hidden" id = "groupIdx" name="groupIdx" value="">
									<input type="hidden" id = "userId" name="userId" value="">
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
					$("#12thMenu").toggleClass('open');
					$("#13thThr").toggleClass('active');
				})
			})
			
			function fn_ExcelDown(orgStation,userId,invoiceNo){
				$("#excelDownorgStation").val(orgStation);
				$("#excelDownuserId").val(userId);
				$("#excelDownInvoiceNo").val(invoiceNo);
				$("#excelDown").submit();				
			}
		</script>
		
		<script type="text/javascript">

		function fn_popupReturnStockOut(orgStation,userId,nno){
			var _width = '1250';
		    var _height = '650';
		    var _left = Math.ceil(( window.screen.width - _width )/2);
		    var _top = 150;
			window.open("/mngr/inspin/returnReqListDetail?orgStation="+orgStation+"&userId="+userId+"&nno="+nno,"EtcStockOut",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
		}

		function fn_takeinStockList(){
			window.open("/mngr/inspin/popupReturnStockList","PopupStock", "width=1350,height=860");
		}

		</script>

	</body>
</html>
