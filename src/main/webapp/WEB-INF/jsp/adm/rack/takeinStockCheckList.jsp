<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  
	  .colorBlack {color:#000000 !important;}
	  
	  #moveBtn {
	  	border: 1px solid #dcdcdc;
	  	background: white;
	  }
	  
	  #moveBtn:hover {
	  	border: 1px solid #dcdcdc;
	  	background: #dcdcdc;
	  	font-weight: bold;
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
		<!-- basic scripts End-->
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
	</head> 
	<title>사입 Rack</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
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
								사입 Rack
							</li>
							<li class="active">Rack 재고 파악</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>Rack 재고 파악</h1>
						</div>
						
						<br />
						<div>
						<form id="searchForm" name="searchForm">
							<!-- search form -->
							<table class="table table-bordered" style="height: 20px; width: 700px;">
								<thead>
									<tr>
										<th class="center colorBlack" style="width: 50px;">
											<i class="fa fa-calendar bigger-110"></i>
										</th>
										<td style="padding: 0px; width: 180px;">
											<div style="height: 100%;" class="input-group input-daterange">
												<input style="width: 100%; height: 100%;" type="text" class="form-control" name="checkDate" id="checkDate" value="${parameterInfo.checkDate}">
											</div>
										</td>
										<%-- <th class="center colorBlack" style="width: 100px;">Rack</th>
										<td style="padding: 0px; width: 180px;">
											<input class="form-control" list="rackCodeList" name="rackCode" id="rackCode" style="width: 100%; height: 100%;" value="${parameterInfo.rackCode}">
											<datalist id="rackCodeList" name="rackCodeList" >
											<c:forEach items="${rackCodeList}" var="rackCodeList" varStatus="status">
												<option value="${rackCodeList.rackCode}"
													<c:if test="${rackCodeList eq rackCodeList.rackCode}">selected</c:if>>
													${rackCodeList.rackCode}
												</option>
											</c:forEach>
										</td> --%>
										<th class="center colorBlack" style="width: 100px;">USER ID</th>
										<td style="padding: 0px; width: 180px;">
											<input style="width: 100%; height: 100%;" type="text" class="form-control" name="wrUserId" id="wrUserId" value="${parameterInfo.wrUserId}">
										</td>
										<td style="width: 50px; text-align: center;">
											<input type="submit" style="margin: 0 auto; text-align: center;" value="검색" id="searchBtn">
										</td>
									</tr>
								</thead>
							</table>
							</form>
							<!-- search form -->
						</div>
						<br />
						<div>
							<form name="rgdelbutton" id="rgdelbutton" method="get" action="">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="rgstr-user" style="text-align: left">
									<button type="submit" class="btn btn-sm btn-primary" name="rgstBtn" id="rgstBtn">
									 	등록
									</button>
									<button type="submit" class="btn btn-sm btn-danger" name="delBtn" id="delBtn">
									 	삭제
									</button>
									<input type="hidden" name="targetParm" id="targetParm" value=""/>
								</div>
							</form>
						</div>
						<br />
						<!-- 랙 리스트 -->
						<div id="table-contents" class="table-contents" style="width: 50%; float:left;">
							<table id="dynamic-table" class="table table-bordered table-hover" style="width: 100%;">
								<thead>
									<tr>
										<th class="center">
											<label class="pos-rel">
													<input type="checkbox" class="ace" /> <span class="lbl"></span>
											</label>
										</th>
										<th class="center colorBlack">Station</th>
										<th class="center colorBlack">Date</th>
										<th class="center colorBlack">USER ID</th>
										<th class="center colorBlack">Remark</th>
										<th class="center colorBlack"></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${checkList}" var="checkList">
										<tr>
											<td id="checkboxTd" class="center" style="word-break:break-all;">
												<label class="pos-rel">
													<input type="checkbox" class="ace" value="${checkList.idx}"/>
													<span class="lbl"></span>
												</label>
											</td>
											<td class="center" style="word-break: break-all;">
												${checkList.orgStation}
											</td>
											<td class="center" style="word-break: break-all;">
												${checkList.checkDate}
											</td>
											<td class="center" style="word-break: break-all;">
												${checkList.wrUserId}
											</td>
											<td class="center" style="word-break: break-all;">
												${checkList.remark}
											</td>
											<td class="center" style="word-break: break-all; width:100px;">
												<input type="button" id="moveBtn" onclick="fn_moveToRackList('${checkList.idx}');" value="재고 파악"/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<br />
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>	
						</div>

						<!-- 재고 리스트 -->
						<div id="inner-content-side" style="width:61%; float: right; text-align:center; ">
							<div id="stockList">
							
							</div>
							<br />
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		</div>
		<div id="test">
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
		
		
		
		<!-- script datepicker -->
		
		<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>

		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#15thMenu").toggleClass('open');
					$("#15thMenu").toggleClass('active'); 
					$("#15thThird").toggleClass('active');
				});


				$('#delBtn').on('click',function(e){
					var str = "";
					$("td input:checkbox:checked").each(function (index) {  
				        str += $(this).val() + ",";  
				    });
				    if(str == "")  {
					    alert("삭제할 대상이 없습니다.");
					    return false;
				    }
					
					$('#rgdelbutton').attr('action', '/mngr/takein/rack/deleteStockCheckInfo');
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


				
			});
			
		
		

			
			$('#input[name=date-range-picker]').daterangepicker({
				'applyClass' : 'btn-sm btn-success',
				'cancelClass' : 'btn-sm btn-default',
				locale: {
					applyLabel: 'Apply',
					cancelLabel: 'Cancel',
				}
			});
		
			$('.input-daterange').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			});


			
			$('#rgstBtn').on('click', function(e) {
				window.open("/mngr/takein/rack/registStockCheck", "PopupWin", "width=700,height=420");
			});
			
			function fn_moveToRackList(idx) {
				location.href="/mngr/takein/rack/stockCheckInfo/"+idx;
			};
			
		
		</script>
		<!-- script addon end -->
		<!-- script addon end -->
	</body>
</html>
