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
	<title>사입</title>
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
					<div class="page-content">
						<div style="width:100%;">
							<input type="button" id="cancelBtn" class="btn btn-sm btn-white" value="출고 취소"/>
							<table class="table table-bordered" style="width:100%;margin-top:8px;" id="dynamic-table">
								<thead>
									<tr>
										<th class="center">
											<label class="pos-rel">
												<input type="checkbox" class="ace allcheck" /> <span class="lbl"></span>
											</label>
										</th>
										<th class="center colorBlack">주문번호</th>
										<th class="center colorBlack">배송사</th>
										<th class="center colorBlack">운송장번호</th>
										<th class="center colorBlack">UserID</th>
										<th class="center colorBlack">Company</th>
										<th class="center colorBlack">수취인명</th>
										<th class="center colorBlack">도착 국가</th>
										<th class="center colorBlack">주소</th>
										<th class="center colorBlack">전화번호</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${takeinOrderList}" var="orderList" varStatus="status">
										<tr>
											<td class="center">
												<label class="pos-rel">
													<input type="checkbox" class="ace" value="${orderList.hawbNo}"/>
													<span class="lbl"></span>
												</label>
											</td>
											<td class="center">${orderList.orderNo}</td>
											<td class="center">${orderList.transCode}</td>
											<td class="center">${orderList.hawbNo}</td>
											<td class="center">${orderList.userId}</td>
											<td class="center">${orderList.comName}</td>
											<td class="center">${orderList.cneeName}</td>
											<td class="center">${orderList.dstnNation}</td>
											<td style="text-align:left;">
												${orderList.cneeZip} ${orderList.cneeAddr} ${orderList.cneeAddrDetail}
											</td>
											<td class="center">
												<c:if test="${!empty orderList.cneeTel}">
													${orderList.cneeTel}	
												</c:if>
												<c:if test="${empty orderList.cneeTel}">
													${orderList.cneeHp}
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
			</div>
		</div>
		<!-- /.main-content -->
		<!-- Footer Start -->
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
				var myTable = $('#dynamic-table').DataTable({
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
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

				$("#cancelBtn").on('click', function(e) {
					var targets = new Array();
					$("#dynamic-table").find('tbody > tr > td input[type=checkbox]').each(function() {
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if (td_checked) {
							targets.push($(this).val());
						}
					})
					if (targets == "") {
						alert("선택한 운송장이 없습니다.");
						return false;
					}

					$.ajax({
						url : '/mngr/takein/cancelTakeinOrderAll',
						type : 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {targets: targets},
						success : function(data) {
							if (data == 'S') {
								location.reload();
							} else {
								alert("출고 취소 처리 중 오류가 발생 하였습니다.");
							}
						},
						error : function(xhr, status) {
							alert("출고 취소에 실패 하였습니다.");
						}
					})
				})
			
			})			
		</script>
		
	</body>
</html>
