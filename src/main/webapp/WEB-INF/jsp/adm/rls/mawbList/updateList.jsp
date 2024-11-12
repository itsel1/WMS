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
	<title>MAWB List</title>
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
								출고
							</li>
							<li class="active">Manifest</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								주문 정보 수정 내역
							</h1>
						</div>
						<div style="margin-top:20px;">
							<table class="table table-bordered" style="width:30%;">
								<thead>
									<tr>
										<th class="center" style="width:20%;">Hawb No</th>
										<td class="center" style="width:30%; background:#fff;">${firstOrderList.hawbNo}</td>
										<th class="center" style="width:20%;">배송사</th>
										<td class="center" style="width:30%; background:#fff;">${firstOrderList.transCode}</td>
									</tr>
								</thead>
							</table>
						</div>
						<div style="width:100%; margin-bottom:20px;">
							<div style="width:100%;">
								<table id="main-table" class="table" style="width:100%;">
									<thead>
										<tr>
											<th colspan="10">주문 정보</th>
										</tr>
										<tr>
											<th class="center">Order No</th>
											<th class="center">Order Date</th>
											<th class="center">Box Cnt</th>
											<th class="center">Shipper</th>
											<th class="center">Shipper Addr</th>
											<th class="center">Cnee</th>
											<th class="center">Cnee Addr</th>
											<th class="center">Cnee Zip</th>
											<th class="center">Cnee Tel</th>
											<th class="center">Cnee Hp</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="center">${firstOrderList.orderNo}</td>
											<td class="center">${firstOrderList.orderDate}</td>
											<td class="center">${firstOrderList.boxCnt}</td>
											<td class="center">${firstOrderList.shipperName}</td>
											<td class="center">${firstOrderList.shipperAddr}</td>
											<td class="center">${firstOrderList.cneeName}</td>
											<td class="center">${firstOrderList.cneeAddr}</td>
											<td class="center">${firstOrderList.cneeZip}</td>
											<td class="center">${firstOrderList.cneeTel}</td>
											<td class="center">${firstOrderList.cneeHp}</td>
										</tr>
										<tr style="background-color:rgba(240, 183, 183, 0.22);">
											<td class="center" colspan="10"><span class="glyphicon glyphicon-chevron-down"></span></td>
										</tr>
										<tr style="border-bottom:1px solid #ccc;">
											<td class="center">
												<c:if test="${empty orderList.orderNo}">
													${firstOrderList.orderNo}
												</c:if>
												<c:if test="${!empty orderList.orderNo}">
													${orderList.orderNo}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.orderDate}">
													${firstOrderList.orderDate}
												</c:if>
												<c:if test="${!empty orderList.orderDate}">
													${orderList.orderDate}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.boxCnt}">
													${firstOrderList.boxCnt}
												</c:if>
												<c:if test="${!empty orderList.boxCnt}">
													${orderList.boxCnt}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.shipperName}">
													${firstOrderList.shipperName}
												</c:if>
												<c:if test="${!empty orderList.shipperName}">
													${orderList.shipperName}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.shipperAddr}">
													${firstOrderList.shipperAddr}
												</c:if>
												<c:if test="${!empty orderList.shipperAddr}">
													${orderList.shipperAddr}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.cneeName}">
													${firstOrderList.cneeName}
												</c:if>
												<c:if test="${!empty orderList.cneeName}">
													${orderList.cneeName}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.cneeAddr}">
													${firstOrderList.cneeAddr}
												</c:if>
												<c:if test="${!empty orderList.cneeAddr}">
													${orderList.cneeAddr}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.cneeZip}">
													${firstOrderList.cneeZip}
												</c:if>
												<c:if test="${!empty orderList.cneeZip}">
													${orderList.cneeZip}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.cneeTel}">
													${firstOrderList.cneeTel}
												</c:if>
												<c:if test="${!empty orderList.cneeTel}">
													${orderList.cneeTel}
												</c:if>
											</td>
											<td class="center">
												<c:if test="${empty orderList.cneeHp}">
													${firstOrderList.cneeHp}
												</c:if>
												<c:if test="${!empty orderList.cneeHp}">
													${orderList.cneeHp}
												</c:if>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class="hr dotted"></div>
						<div style="width:100%; margin-top:20px;">
							<div style="width:100%;">
								<table id="item-table" class="table containerTable">
									<thead>
										<tr>
											<th colspan="10">상품 정보</th>
										</tr>
										<tr>
											<th class="center colorBlack">Sub No</th>
											<th class="center colorBlack">HS Code</th>
											<th class="center colorBlack">Item Detail</th>
											<th class="center colorBlack">Item Cnt</th>
											<th class="center colorBlack">Brand</th>
											<th class="center colorBlack">Unit Value</th>
											<th class="center colorBlack">Currency</th>
											<th class="center colorBlack">Cus Item Code</th>
											<th class="center colorBlack">Make Cntry</th>
											<th class="center colorBlack">Make Com</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orderItem}" var="orderItem" varStatus="status">
											<tr>
												<td class="center">${firstOrderItem[status.index].subNo}</td>
												<td class="center">${firstOrderItem[status.index].hsCode}</td>
												<td class="center">${firstOrderItem[status.index].itemDetail}</td>
												<td class="center">${firstOrderItem[status.index].itemCnt}</td>
												<td class="center">${firstOrderItem[status.index].brand}</td>
												<td class="center">${firstOrderItem[status.index].unitValue}</td>
												<td class="center">${firstOrderItem[status.index].chgCurrency}</td>
												<td class="center">${firstOrderItem[status.index].cusItemCode}</td>
												<td class="center">${firstOrderItem[status.index].makeCntry}</td>
												<td class="center">${firstOrderItem[status.index].makeCom}</td>
											</tr>
											<tr style="background-color:rgba(240, 183, 183, 0.22);">
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"><span class="glyphicon glyphicon-chevron-down"></span></td>
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"></td>
												<td class="center"></td>
											</tr>
											<tr style="border-bottom:1px solid #ccc;">
												<td class="center">${orderItem.subNo}</td>
												<td class="center">
													<c:if test="${!empty orderItem.hsCode}">
														${orderItem.hsCode}
													</c:if>
													<c:if test="${empty orderItem.hsCode}">
														${firstOrderItem[status.index].hsCode}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.itemDetail}">
														${orderItem.itemDetail}
													</c:if>
													<c:if test="${empty orderItem.itemDetail}">
														${firstOrderItem[status.index].itemDetail}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${orderItem.itemCnt ne '0'}">
														${orderItem.itemCnt}
													</c:if>
													<c:if test="${orderItem.itemCnt eq '0'}">
														${firstOrderItem[status.index].itemCnt}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.brand}">
														${orderItem.brand}
													</c:if>
													<c:if test="${empty orderItem.brand}">
														${firstOrderItem[status.index].brand}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${orderItem.unitValue ne '0.0'}">
														${orderItem.unitValue}
													</c:if>
													<c:if test="${orderItem.unitValue eq '0.0'}">
														${firstOrderItem[status.index].unitValue}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.chgCurrency}">
														${orderItem.chgCurrency}
													</c:if>
													<c:if test="${empty orderItem.chgCurrency}">
														${firstOrderItem[status.index].chgCurrency}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.cusItemCode}">
														${orderItem.cusItemCode}
													</c:if>
													<c:if test="${empty orderItem.cusItemCode}">
														${firstOrderItem[status.index].cusItemCode}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.makeCntry}">
														${orderItem.makeCntry}
													</c:if>
													<c:if test="${empty orderItem.makeCntry}">
														${firstOrderItem[status.index].makeCntry}
													</c:if>
												</td>
												<td class="center">
													<c:if test="${!empty orderItem.makeCom}">
														${orderItem.makeCom}
													</c:if>
													<c:if test="${empty orderItem.makeCom}">
														${firstOrderItem[status.index].makeCom}
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
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
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thMani").toggleClass('active');
				})

				var myTable = $('#item-table').DataTable({
				"dom" : "t",
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
		        "autoWidth": false,
		        "scrollY" : 300,
				"language":{
					"zeroRecords": "조회 결과가 없습니다.",
					}
		    	});
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
