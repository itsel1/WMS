<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
		
		.colorBlack { color:black !important; }
		
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
		<!-- headMenu Start -->
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div>
						<div class="row">
							<div class="col-xs-12">
								<input type="button" class="btn btn-primary btn-sm" id="printCheck" value="라벨 출력">
							</div>
						</div>
						<table class="table table-bordered table-hover" style="margin-top:10px;" id="printHawbTable">
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
									<th class="center">Agency Bl</th>
									<th class="center">배송사</th>
									<th class="center">수취인</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${hawbList}" var="hawbList" varStatus="status">
									<tr>
										<td class="center">
											<label class="pos-rel">
												<input type="checkbox" class="ace" value="${hawbList.nno}"/>
												<span class="lbl"></span>
											</label>
										</td>
										<td class="center">${status.count}</td>
										<td class="center">${hawbList.userId}</td>
										<td class="center">${hawbList.hawbNo}</td>
										<td class="center">${hawbList.agencyBl}</td>
										<td class="center">${hawbList.transCode}</td>
										<td class="center">${hawbList.cneeName}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- /.main-content -->
		<!-- Footer Start -->
		<!-- Footer End -->

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

				$("#printCheck").on('click', function(e) {
					var targets = new Array();
					$("#printHawbTable").find('tbody > tr > td input[type=checkbox]').each(function() {
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if (td_checked) {
							targets.push($(this).val());
						}
					})
					if (targets == "") {
						alert("출력할 운송장이 없습니다.");
						return false;
					} else {
						window.open("/comn/printHawb?targetInfo="+targets+"&formType=","popForm","_blank");
					}
				});

				var myTable2 = 
					$('#printHawbTable')
					.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "autoWidth": false,
			        "scrollY" : 550,
					select: {
						style: 'multi',
					},
					"language":{
						"zeroRecords": "조회 결과가 없습니다",
					}
			    } );
			
				
				myTable2.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable2.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable2.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable2.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
	
				var printCheckTable = $("#printHawbTable");
				
				$('th input[type=checkbox], td input[type=checkbox]', printCheckTable).prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#printHawbTable > thead > tr > th input[type=checkbox], #printHawbTable_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#printHawbTable').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable2.row(row).select();
						else  myTable2.row(row).deselect();
					});
				});
			})			
		</script>
		
	</body>
</html>
