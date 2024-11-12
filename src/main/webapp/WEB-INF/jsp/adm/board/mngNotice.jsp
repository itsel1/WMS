<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
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
		<!-- ace scripts -->
	<!-- basic scripts End-->
	</head> 
	<title>공지사항</title>
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
								고객센터
							</li>
							<li class="active">공지사항</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								공지사항
							</h1>
						</div>
						<div id="inner-content-side">
						<!-- 사용자 권한 확인 후 관리자만 등록 가능하게 c:if 걸어서 오픈 -->
							<div id="search-div">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="rgstr-user" style="text-align: right">
									<button id="rgstr-button" name="rgstr-button" class="btn btn-sm btn-primary">
									 등록하기
									</button>
								</div>
							</div>
							<br>
						<!-- 사용자 권한 확인 후 관리자만 등록 가능하게 c:if 걸어서 오픈 -->
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display" style="min-width: 800px;">
									<table id="dynamic-table" class="table table-bordered table-hover">
									<colgroup>
										<col width="10%"/>
										<col width="70%"/>
										<col width="20%"/>
									</colgroup>
									<thead>
										<tr>
											<th class="center">
												번호
											</th>
											<th >
												제목
											</th>
											<th class="center">
												작성일
											</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${fn:length(noticeInfo) eq 0}">
											<tr>	
												<td colspan='3'>
													조회결과가 없습니다.
												</td>
											</tr>
										</c:if>
										<c:forEach items="${noticeInfo}" var="noticeVO">
											<tr style="cursor: pointer;" onclick="javascript:fnTrClick('${noticeVO.idx}')">
												<td class="center">
													${noticeVO.rownum}
												</td>
												<td >
													${noticeVO.title}
												</td>
												<td class="center">
													${noticeVO.date}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
		function fnTrClick(idx){
			location.href="/mngr/board/ntcDetail?ntcNo="+idx;
		}
		
			jQuery(function($) {
				//
				$(document).ready(function() {
					$("#11thMenu").toggleClass('open');
					$("#11thMenu").toggleClass('active'); 
					$("#11thTwo").toggleClass('active');
				});

				
				//initiate dataTables plugin
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom": 't',
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "lengthChange" : false,
			        "pageLength" : 40,
			        "autoWidth" : false,
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

				

				$('.show-details-btn').on('click', function(e) {
					e.preventDefault();
					if($("[class*='open']")[0] != $(this).closest('tr').next()[0]){
						$("[class*='open']").toggleClass('open'); // 다찾아서 다닫기
					}
					$(this).closest('tr').next().toggleClass('open'); // 누른거 근처만 열기, 열려있으면 닫기
				});

				$("#rgstr-button").on('click',function(e){
					location.href="/mngr/board/ntcRegist"
				})
			})
		</script>
		<!-- script addon end -->
	</body>
	</body>
</html>
