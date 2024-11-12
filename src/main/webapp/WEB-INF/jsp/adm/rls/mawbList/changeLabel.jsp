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
							<li class="active">송장 교체 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<br/>
					<div class="page-content">
						<form id="mainForm" action="/mngr/rls/changeLabel" action="get">
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width: 100%;">
										<select style="height: 34px" id="searchType" name="searchType">
											<option <c:if test="${searchType eq '0'}"> selected</c:if> value="0">::전체::</option>
											<option <c:if test="${searchType eq '1'}"> selected</c:if> value="1">USER ID</option>
											<option <c:if test="${searchType eq '2'}"> selected</c:if> value="2">Hawb No</option>
										</select>
										<input type="text" class="form-control" name="keyword" value="${searchKeyword}" style="width:100%; max-width:300px;">
										<button id="srchKeyword" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>
							</div>
							<br/>
							<div class="table-layout" style="width: 100%;">
								<div style="float:left;width:50%;height:650px;">
									<div class="row">
										<div class="col-xs-12">
											<input type="button" class="btn btn-primary btn-sm" onclick="fn_createLabel()" value="라벨 생성">
										</div>
									</div>
									<table class="table table-bordered table-hover" style="margin-top:10px;" id="dynamic-table">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace allcheck" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center">No</th>
												<th class="center">User ID</th>
												<th class="center">Hawb No</th>
												<th class="center">배송사</th>
												<th class="center">수취인</th>
												<th class="center">수취인 주소</th>
												<th class="center">수취인 Tel</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${orderList}" var="orderList" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel">
															<input type="checkbox" class="ace" name="nno[]" value="${orderList.nno}"/>
															<span class="lbl"></span>
														</label>
														<input type="hidden" name="wta[]" value="${orderList.wta}"/>
														<input type="hidden" name="transCode[]" value="${orderList.transCode}">
													</td>
													<td class="center">${status.count}</td>
													<td class="center">${orderList.userId}</td>
													<td class="center">${orderList.hawbNo}</td>
													<td class="center">${orderList.transCode}</td>
													<td class="center">${orderList.cneeName}</td>
													<td class="center">${orderList.cneeAddr}</td>
													<td class="center">${orderList.cneeTel}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<br/>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
								<div style="width:49%;float:right;">
									<iframe src="/mngr/rls/changeLabelList" frameborder="0" id="iframe" style="width:100%;height:650px;"></iframe>
								</div>
							</div>
						</form>
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
					$("#6thChange").toggleClass('active');
				});

				var myTable = 
					$('#dynamic-table')
					.DataTable({
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : "500",
						select: {
							style: 'multi',
						},
						"language":{
							"zeroRecords": "조회 결과가 없습니다.",
						}
				    });

				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				});
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				});

				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable.row(row).select();
						else  myTable.row(row).deselect();
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(this.checked) myTable.row(row).deselect();
					else myTable.row(row).select();
				});
			})
			
			function fn_createLabel() {
				var checkedNno = [];
				var transCodes = [];
				var weights = [];

				$("input[name='nno[]']:checked").each(function() {
					checkedNno.push($(this).val());
					weights.push($("input[name='wta[]']", $(this).closest("td")).val());
					transCodes.push($("input[name='transCode[]']", $(this).closest("td")).val());
				});

				/*
				var checkedNno = $("input[name='nno[]']:checked").map(function() {
					return $(this).val();
				}).get();
				*/
				
				$.ajax({
					url : '/mngr/rls/changeLabelList',
					type : 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : {nno : checkedNno, transCode: transCodes, weight: weights},
					success : function(result) {
						alert(result.MSG);
						location.reload();
					},
					error : function(xhr, status, error) {
						console.log(error);
						alert("라벨 생성에 실패 하였습니다.");
					}
				})
			}
		</script>
		<!-- script addon end -->
		
	</body>
</html>
