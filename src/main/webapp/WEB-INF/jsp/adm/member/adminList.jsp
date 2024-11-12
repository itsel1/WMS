<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>회원 관리</title>
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
								관리자
							</li>
							<li class="active">관리자 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								관리자
							</h1>
						</div>
						<div id="inner-content-side" >
						
							<div id="search-div">
								<br>
								<form name="search-button" id="search-botton" method="get" action="/mngr/acnt/adminList">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<table class="table table-bordered" style="width:360px;">
										<thead>
											<tr>
												<th class="center colorBlack" style="width:70px;">USER ID</th>
												<td style="padding:0px; width:230px;">
													<input type="text" name="userId" id="userId" style="width:230px; height:42px;" value="${params.userId}"/>
												</td>
												<td style="text-align:center; width:60px;">
													<input type="button" value="조회">
												</td>
											</tr>
										</thead>
									</table>
								</form>
								<br>
								<form name="yslExcelForm" id="yslExcelForm" action="/mngr/ysl/excelDown" method="get">
									<div style="text-align:right; display:none;">
										<input type="submit" value="일본 수출신고 excel down">
									</div>
								</form>
								<div>
								<form name="rgdelbutton" id="rgdelbutton" method="get" action="/mngr/acnt/registAdmin">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div id="rgstr-user" style="text-align: left">
										<button type="submit" class="btn btn-sm btn-primary" name="rgstBtn" id="rgstBtn">
										 	등록하기
										</button>
										<button type="submit" class="btn btn-sm btn-danger" name="delBtn" id="delBtn" >
											삭제하기
										</button>
										<input type="hidden" name="targetParm" id="targetParm" value=""/>
									</div>
								</form>
								</div>
							</div>
							<br>
							<div id="table-contents" class="table-contents" style="width:60%">
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
										<thead>
											<tr>
												<th class="center" style="width:60px;">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:160px;">아이디</th>
												<th class="center colorBlack" style="width:120px;">관리자 명</th>
												<th class="center colorBlack" style="width:150px;">전화 번호</th>
												<th class="center colorBlack" style="width:150px;">휴대전화 번호</th>
												<th class="center colorBlack" style="width:200px;">Email</th>
												<th class="center colorBlack" style="width:100px;">권한</th>
												<th class="center colorBlack" style="width:80px;">사용 여부</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userList}" var="list" varStatus="status">
												<tr>
													<td id="checkboxTd" class="center" style="word-break:break-all;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" value="${list.adminId}"/>
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center" style="word-break:break-all;">
														<a href="/mngr/acnt/modiInfo/${list.adminId}">${list.adminId}</a>
													</td>
													<td class="center" style="word-break:break-all;">
														${list.adminName}
													</td>
													<td class="center" style="word-break:break-all;">
														<c:if test="${empty list.adminTel}">
														-
														</c:if>
														<c:if test="${!empty list.adminTel}">
														${list.adminTel}
														</c:if>														
													</td>
													<td class="center" style="word-break:break-all;">
														<c:if test="${empty list.adminHp}">
														-
														</c:if>
														<c:if test="${!empty list.adminHp}">
														${list.adminHp}
														</c:if>			
													</td>
													<td class="center" style="word-break:break-all;">
														<c:if test="${empty list.adminEmail}">
														-
														</c:if>
														<c:if test="${!empty list.adminEmail}">
														${list.adminEmail}
														</c:if>
													</td>
													<td class="center" style="word-break:break-all;">
														${list.role}
													</td>
													<td class="center" style="word-break:break-all;">
														${list.useYn}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<br/>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
							<br />
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#zeroMenu").toggleClass('open');
					$("#zeroMenu").toggleClass('active');
					$("#14thOne").toggleClass('active');
				})

				$('#delBtn').on('click',function(e){
					var str = "";
					$("td input:checkbox:checked").each(function (index) {  
				        str += $(this).val() + ",";  
				    });
				    if(str == "")  {
					    alert("삭제할 대상이 없습니다.");
					    return false;
				    }
					
					$('#rgdelbutton').attr('action', '/mngr/acnt/deleteAdmin');
				   	$('#targetParm').val(str)
				});

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "t",
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

		</script>
		<!-- script addon end -->
</body>
</html>