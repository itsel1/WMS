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
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>사용자 관리</title>
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
								Invoice
							</li>
							<li class="active">무게 비교</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								무게 비교
							</h1>
						</div>
						<div id="inner-content-side" >
						
							<div id="search-div">
								<br>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-md-8" style="text-align: left;">
										<form class="hidden-xs hidden-sm" name="searchForm" id="searchForm">
											<div class="input-group">
												<span class="input-group-addon">
													<i class="fa fa-calendar bigger-110"></i>
												</span>
												<div class="input-group input-daterange">
													<input type="text" class="dateClass form-control" name="startDate" id="startDate" value="${params.startDate}" autocomplete="off"/>
														<span class="input-group-addon">
															to
														</span>
													<input type="text" class="dateClass form-control" name="endDate" id="endDate" value="${params.endDate}" autocomplete="off"/>
													<span class="input-group-addon">
														UserID
													</span>
													<input type="text" class="form-control" name="userId" id="userId" value="${params.userId}"/>
													<span class="input-group-addon">
														배송사
													</span>
													<select class="form-control" name="transCode" id="transCode">
														<option value="">::전체::</option>
														<c:forEach items="${transCodeList}" var="transCodeList">
															<option <c:if test="${params.transCode eq transCodeList.transCode}"> selected</c:if> value="${transCodeList.transCode}">${transCodeList.transCode}</option>
														</c:forEach>
													</select>
													<span class="input-group-addon">
														HawbNo
													</span>
													<input type="text" class="form-control" name="hawbNo" id="hawbNo" value="${params.hawbNo}"/>
													<span class="input-group-addon">
														배송사 BL
													</span>
													<input type="text" class="form-control" name="matchHawbNo" id="matchHawbNo" value="${params.matchHawbNo}"/>
													<span style="cursor: pointer;" class="input-group-addon" id="searchBtn" name="searchBtn">
														<i class="ace-icon fa fa-search icon-on-right bigger-120"></i>
													</span>
												</div>
											</div>
										</form>
											
										</div>
										<div class="col-xs-12 col-md-2" style="text-align: left;">
										</div>
									</div>
								<br>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div id="rgstr-user" style="text-align: right">
										<input type="button" class="btn btn-sm btn-primary" name="excelDown" id="excelDown" value="Excel 다운로드">
									 	<div class="hidden">
											<input type="file" id="excelFile" name="excelFile"
											style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
											accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
										</div>
									</div>
							</div>
							<br>
							<div id="table-contents" class="table-contents" >
								<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th class="center colorBlack" >No. </th>
											<th class="center colorBlack" >USER ID</th>
											<th class="center colorBlack" >배송사 코드</th>
											<th class="center colorBlack" >송장번호</th>
											<th class="center colorBlack" >배송사 접수 번호</th>
											<th class="center colorBlack" >WMS 무게</th>
											<th class="center colorBlack" >WMS 부피 무게</th>
											<th class="center colorBlack" >배송사 무게</th>
											<th class="center colorBlack" >배송사 부피 무게</th>
											<th class="center colorBlack" >적용 타입</th>
											<th class="center colorBlack" >적용 무게</th>
											<th class="center colorBlack" >날짜</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items = "${weightList}" var="weight" varStatus="status">
										<tr>
											<td id="checkboxTd" class="center" style="word-break:break-all; vertical-align: middle;">
												${status.count}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.userId}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.transCode}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.hawbNo}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.agencyBl}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.userWta}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.userWtc}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.agencyWta}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.agencyWtc}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.applyType}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.applyWt}
											</td>
											<td class="center" style="word-break:break-all; vertical-align: middle;">
												${weight.wDate}
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<br/>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		
		
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		
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
					$("#invoicethMenu").toggleClass('open');
					$("#invoicethMenu").toggleClass('active'); 
					$("#invoiceZero").toggleClass('active');
				});

				//initiate dataTables plugin
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
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

				
				$("#startDate").datepicker({
					autoclose:true,
					todayHighlight:true,
					format:'yyyymmdd'
				}).on('changeDate', function(e) {
					$("#endDate").val($("#startDate").val());
				});

				$("#endDate").datepicker({
					autoclose:true,
					todayHighlight:true,
					format:'yyyymmdd'
				});
				

				$('.dateClass').on('change',function(){
					if($(this).val().length < 10){
						if($(this).val().length == 4){
							$(this).val($(this).val() + "-");
						}
						if($(this).val().length == 7){
							$(this).val($(this).val() + "-");
						}
					}
				});

				$("#searchBtn").on('click',function(e){
					$("#searchForm").attr("action", "/mngr/invoice/compareWeight");    ///board/modify/로
					$("#searchForm").attr("method", "get");            //get방식으로 바꿔서
					$("#searchForm").submit();
				})

				$("#excelDown").on('click',function(e){
					//excel 다운로드 로직
					//window.open("/mngr/Excel/rusExcelDown?mawbNo="+mawbNo);
					window.open("/mngr/invoice/compareWeightExcelDown?startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&transCode="+$("#transCode").val());
					
				})

					
				
					function LoadingWithMask() {
					    //화면의 높이와 너비를 구합니다.
					    var maskHeight = $(document).height();
					    var maskWidth  = window.document.body.clientWidth;
					     
					    //화면에 출력할 마스크를 설정해줍니다.
					    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
					  
					    //화면에 레이어 추가
					    $('body')
					        .append(mask)
					        
					    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
					    $('#mask').css({
					            'width' : maskWidth
					            ,'height': maskHeight
					            ,'opacity' :'0.3'
					    });
					  
					    //마스크 표시
					    $('#mask').show();  
					    //로딩중 이미지 표시
					}

			})
		</script>
		<!-- script addon end -->
	</body>
</html>