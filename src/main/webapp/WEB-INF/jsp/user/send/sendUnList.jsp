<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp"%>
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}
.noneArea {
	margin:0px !important;
	padding:0px !important;
	border-bottom : none;
}

.containerTable {
	width: 100% !important;
}

table ,tr td{
	word-break:break-all;
}
</style>
<!-- basic scripts -->
</head>
<title>배송 현황</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp"%>
		<!-- headMenu End -->

		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size: 14px !important;">
						<a>배송 현황</a> <span class="mx-2 mb-0">/</span> <strong
							class="text-black">미발송목록</strong>
					</div>
				</div>
			</div>
		</div>
		<!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>미발송목록</h3>
				</div>
				<form name="uploadForm" id="uploadForm" enctype="multipart/form-data">
					<div id="inner-content-side">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="col-xs-12 form-group" style="margin:0px;">
							<div id="table-contents" class="table-contents">
								<div class="hr hr-8 dotted"></div>
								<div class="space-10"></div>
									<table id="dynamic-table" class="table table-bordered table-hover containerTable">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													UserID
												</th>
												<th class="center colorBlack">
													BL No
												</th>
												<th class="center colorBlack">
													Order No
												</th>
												<th class="center colorBlack">
													수취인 이름
												</th>
												<th class="center colorBlack">
													수취인 전화번호
												</th>
												<th class="center colorBlack">
													우편주소
												</th>
												<th class="center colorBlack">
													대표 상품
												</th>
												<th class="center colorBlack">
													Total Value
												</th>
												<th class="center colorBlack">
													실 무게
												</th>
												<th class="center colorBlack">
													부피 무게
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${sendList}" var="send" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel">
																<input type="checkbox" class="ace" /> <span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack">
														${send.userId }
													</td>
													<td class="center colorBlack">
														${send.hawbNo }
													</td>
													<td class="center colorBlack">
														${send.orderNo }
													</td>
													<td class="center colorBlack">
														${send.cneeName}
													</td>
													<td class="center colorBlack">
														${send.cneeTel}
													</td>
													<td class="center colorBlack">
														${send.cneeZip}
													</td>
													<td class="center colorBlack">
														${send.mainItem}
													</td>
													<td class="center colorBlack">
														${send.totalValue}
													</td>
													<td class="center colorBlack">
														${send.wta}
													</td>
													<td class="center colorBlack">
														${send.wtc}
													</td>
												</tr>	
										 	</c:forEach>
										</tbody>
									</table>
							</div>
						</div>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</form>
			</div>
		</div>
	</div>
	



	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>

	<script src="/testView/js/main.js"></script>

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
	<!-- ace scripts -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->


	<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {

					
				});


				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 'lt',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
				        "scrollY": 500,
						select: {
							style: 'multi',
							selector:'td:first-child'
						},
						
						"language":{
							"zeroRecords": "조회 결과가 없습니다.",
						}
				    } );
					
					myTable.on( 'select', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
						}
					} );
					myTable.on( 'deselect', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
						}
					} );
					 
					/////////////////////////////////
					//table checkboxes
					$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);


					//select/deselect all rows according to table header checkbox
					$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
						var th_checked = this.checked;//checkbox inside "TH" table header
						
						$('#dynamic-table').find('tbody > tr').each(function(){
							var row = this;
							if(th_checked) {
								if(!row.children[0].firstElementChild.firstElementChild.disabled)
									myTable.row(row).select();
							}
							else  myTable.row(row).deselect();
						});
					});

					$("#testBtn").on('click',function(e){
						window.open("/cstmr/apply/excelFormDown")

					})
			})
		</script>
	<!-- script addon end -->
</body>
</html>
