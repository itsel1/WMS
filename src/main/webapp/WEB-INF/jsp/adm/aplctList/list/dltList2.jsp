<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>삭제 리스트</title>
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
								신청서 리스트
							</li>
							<li class="active">삭제 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								삭제 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="delListForm" id="delListForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<table class="table table-bordered table-hover" style="width:50%;">
								<thead>
									<tr>
										<th class="center">USER ID</th>
										<td style="padding:0px;"><input type="text" name="userId" id="userId" value="${params.userId}"
											style="width:100%;height:36px;"></td>
										<th class="center">ORDER NO</th>
										<td style="padding:0px;"><input type="text" name="orderNo" id="orderNo" value="${params.orderNo}"
											style="width:100%;height:36px;"></td>
										<td style="padding:0px;" class="center">
											<input type="button" value="검색" id="search-btn" style="width:100%;height:36px;">
										</td>
									</tr>
								</thead>
							</table>
							<div id="recover-user" style="text-align: left">
								<button id="recBtn" class="btn btn-sm btn-info">
								 	복구하기
								</button>
							</div>
							<div>
								<table id="dynamic-table" style="width:100%" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="center" style="width:3%;">
													<label class="pos-rel">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:5%;">
													비고
												</th>
												<th class="center colorBlack" style="width:7%;">
													삭제 일시
												</th>
												<th class="center colorBlack" style="width:8%;">
													USER ID
												</th>
												<th class="center colorBlack" style="width:11%;">
													주문 번호
												</th>
												<th class="center colorBlack" style="width:5%;">
													주문 일자
												</th>
												<th class="center colorBlack" style="width:10%;">
													수취인
												</th>
												<th class="center colorBlack" style="width:20%;">
													수취인 주소
												</th>
												<th class="center colorBlack" style="width:5%;">
													수취인<br>우편번호
												</th>
												<th class="center colorBlack" style="width:20%;">
													상품명
												</th>
												<th class="center colorBlack" style="width:3%;">
													상품<br>수량
												</th>
												<th class="center colorBlack" style="width:3%;">
													금액
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${delList}" var="delList" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel">																
															<input type="checkbox" class="form-field-checkbox"/>
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack">
													<input type="hidden" value="${delList.dno}" id="targetList" name="targetList"/>
													<c:if test="${delList.resultCode eq 'A'}">
														${delList.recDate}에 복구 됨
													</c:if>
													</td>
													<td class="center colorBlack">
														${delList.delDate}
													</td>
													<td class="center colorBlack">
														${delList.userId}
													</td>
													<td class="center colorBlack">	
														${delList.orderNo}
													</td>
													<td class="center colorBlack">
														${delList.orderDate}
													</td>
													<td class="center colorBlack">
														${delList.cneeName}
													</td>
													<td class="center colorBlack">
														${delList.cneeAddr} ${delList.cneeAddrDetail}
													</td>
													<td class="center colorBlack">	
														${delList.cneeZip}
													</td>
													<td class="center colorBlack">	
														${delList.itemDetail}
													</td>
													<td class="center colorBlack">
														${delList.itemCnt}
													</td>
													<td class="center colorBlack">
														${delList.itemValue}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<input type="hidden" value="" id="recTarget" name="recTarget"/>
							</form>
						</div>
					</div>
				</div>
				<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
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
		<script src="/assets/js/bootstrap.min.js"></script>
		
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#3rdMenu").toggleClass('open');
					$("#3rdMenu").toggleClass('active'); 
					$("#3rdFor").toggleClass('active');
				});

				$("#recBtn").on('click',function(e){
					if(confirm("복구하시겠습니까?")){
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								if(row.children[1].childElementCount == 1){
									targets.push(row.children[1].firstElementChild.value);
								}
							}
						});
						$("#recTarget").val(targets);

						console.log($("#recTarget"));
						
						var formData = $("#delListForm").serialize();
						$("#delListForm").attr("action", "/mngr/aplctList/recoveryList");    ///board/modify/로
						$("#delListForm").attr("method", "post");            //get방식으로 바꿔서
						$("#delListForm").submit();
					}
					/* $.ajax({
						url:'/mngr/aplctList/deleteShpngList',
						type: 'POST',
						data: formData,
						success : function(data) {
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 관리자에게 문의해 주세요.");
			            }
					}) */ 

				});

				$("#search-btn").on('click', function(e) {
					$("#delListForm").attr("action", "/mngr/aplctList/dltList2");
					$("#delListForm").attr("method", "GET");
					$("#delListForm").submit();
				});

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "lt",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
						select: {
							style: 'multi'
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
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
