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
		<!-- basic scripts End-->
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	</head>
	<body class="no-skin">
		
		<form id="userStockOutForm" name="userStockOutForm">
			<div id="detail-contents" class="detail-contents" >
				<div id="home" class="tab-pane in active">
					<div style="text-align:right;float:left; margin-bottom:5px;">
						<input type="button" class="btn btn-white btn-inverse btn-xs" value="리스트 다운로드" id="userStockOutExcelBtn"  >
					</div>
					<div id="table-contents" class="table-contents center" style="width:100%;">
						<table id="stockOutList" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th class="center colorBlack" style="width: 180px;">USER ID</th>
									<th class="center colorBlack" style="width: 120px;">Date</th>
									<th class="center colorBlack" style="width: 100px;">수량</th>
									<th class="center colorBlack" style="display: none;">station</th>
									<th class="center colorBlack" style="display: none;">dep month</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${stockOutInfo}" var="stockOutInfo" varStatus="status">
									<tr>
										<td class="center" style="word-break: break-all;">
											<input type="hidden" name="userId" id="userId" value="${stockOutInfo.userId}" />
											${stockOutInfo.userId}</td>
										<td class="center" style="word-break: break-all;">
											<input type="hidden" name="depDate" id="depDate" value="${stockOutInfo.depDate}" />
											${stockOutInfo.depDate}</td>
										<td class="right" style="word-break: break-all; text-align:right;">
											<input type="hidden" name="cnt" id="cnt" value="${stockOutInfo.cnt}"/>
											${stockOutInfo.cnt}</td>
										<td class="center" style="word-break: break-all; display: none;">
											<input type="hidden" name="orgStation" id="orgStation" value="${stockOutInfo.orgStation}"/>
											${stockOutInfo.orgStation}</td>
										<td class="right" style="word-break: break-all; display: none;">
											<input type="hidden" name="depMonth" id="depMonth" value="${stockOutInfo.depMonth}"/>
											${stockOutInfo.depMonth}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div><!-- /#home -->
			</div>
		</form>
		<br />
		<div>
			
			<div id="stockInfoTable">
				<!-- 일자 별 리스트 -->
			</div>
		</div>
		
		<!-- script addon start -->
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
		
		<!-- script addon End -->
		<script type="text/javascript">
			var count = 0;
			jQuery(function($) {
				
				$(document).ready(function() {
					$('li').on('click', function() {
						
						var id = $(this).attr('id');

						$("li[id="+id+"]").toggleClass('open');
						$("li[id="+id+"]").toggleClass('active'); 
					})
				});

				$(document).on("click", "#userStockOutExcelBtn", function() {
					$("#userStockOutForm").attr("action", "/mngr/send/stockOutListByUserIdExcel");
					$("#userStockOutForm").submit();
				})
				
				var myTable =
					$("#stockOutList")
					.DataTable({
						"dom" : 't',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : 180,
				        scrollCollapse: true,
					});
			})
			
		</script>
		<!-- script addon end -->
	</body>
</html>