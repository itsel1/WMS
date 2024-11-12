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
							<li class="active">통관비용 등록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								통관비용 등록 내역
							</h1>
						</div>
						<div id="inner-content-side" >
						<h2>${registDate} 등록 내역</h2>
						<br/>
						<br/>
							<div id="table-contents" class="table-contents" >
								<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th class="center colorBlack" >No. </th>
											<th class="center colorBlack" >ORG STATION</th>
											<th class="center colorBlack" >USER ID</th>
											<th class="center colorBlack" >HAWB NO</th>
											<th class="center colorBlack">등록일</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items = "${clearanceList}" var="clearance" varStatus="status">
										<tr>
											<td id="checkboxTd" class="center" style="word-break:break-all; width:15%; vertical-align: middle;">
												${status.count}
											</td>
											<td class="center" style="word-break:break-all; width:25%; vertical-align: middle;">
												${clearance.orgStation}
											</td>
											<td class="center" style="word-break:break-all; width:25%; vertical-align: middle;">
												${clearance.userId}
											</td>
											<td class="center" style="word-break:break-all; width:25%; vertical-align: middle;">
												${clearance.hawbNo}
											</td>
											<td class="center" style="word-break:break-all; width:25%; vertical-align: middle;">
												${clearance.wDate}
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
					$("#invoice0").toggleClass('active');
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


				$('input[name=date-range-picker]').daterangepicker({
					'applyClass' : 'btn-sm btn-success',
					'cancelClass' : 'btn-sm btn-default',
					locale: {
						applyLabel: 'Apply',
						cancelLabel: 'Cancel',
					}
				})
				
				//or change it into a date range picker
				$('.input-daterange').datepicker({
					autoclose:true,
					todayHighlight:true,
					format:'yyyy-mm-dd'
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
					$("#searchForm").attr("action", "/mngr/invoice/clearance");    ///board/modify/로
					$("#searchForm").attr("method", "get");            //get방식으로 바꿔서
					$("#searchForm").submit();
				})

				$("#rgstBtn").on('click',function(e){
					$("#excelFile").trigger("click");
				})

				$("#excelFile").on('change',function(e){
						var datass = new FormData();
						datass.append("file", $("#excelFile")[0].files[0]);
						LoadingWithMask();
						
						$.ajax({
							url:'/mngr/invoice/clearanceExcelUpload',
							type: 'POST',
							data : datass,
							processData: false,
				            contentType: false,
							beforeSend : function(xhr)
							{ 
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {
								location.reload();
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
				            },
				            complete : function() {
				            	$('#mask').hide();
				            	$("#excelFile").val("");
				            }
						})
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
			function fn_detail(wDate){
				location.href = "/mngr/invoice/clearanceOne?wDate="+wDate;
			}

			function fn_excelDown(wDate){
				alert(wDate);
			}
		</script>
		<!-- script addon end -->
	</body>
</html>

