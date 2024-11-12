<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

	<head>
	<style type="text/css">

        input[type=file] {
            display: none;
        }

        .my_button {
            display: inline-block;
            width: 200px;
            text-align: center;
            padding: 10px;
            background-color: #006BCC;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }



        .imgs_wrap {

            border: 2px solid #A8A8A8;
            margin-top: 30px;
            margin-bottom: 30px;
            padding-top: 10px;
            padding-bottom: 10px;

        }
        .imgs_wrap img {
            max-width: 150px;
            margin-left: 10px;
            margin-right: 10px;
        }
        
        .profile-info-name{
        	font-weight: bold;
        }
        .profile-info-value{
        	word-break:break-all;
        }
        
        .verticalM{
        	vertical-align: middle !important;
        }
        
        .boxFont{
        	width: 80px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	background-color:#F5F6CE !important;
        	font-size:40pt !important;
        	font:bold !important;
        	color:#000000 !important;
        }
        
        .sizeFont{
        	width:60px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:15pt !important; 
        	background-color:#F5F6CE !important;
        	font:bold !important;
        	color:#000 !important;
        }
        
        .housCntFont{
        	width:40px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:20pt !important; 
        	color:#000 !important;
        	text-align: center;
        }
        
        .real-red{
        color:red !important;
        }
        
        .profile-info-name{
        	border-right: 1px dotted #D5E4F1;
        }
        
        .wfInput{
        	background-color: rgb(255,227,227);
        }
        
        .woInput{
        	background-color: rgb(227,255,227);
        	
        }

    </style>
	
	
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>

	<style type="text/css">
	.colorBlack {
		color: #000000 !important;
	}
	
	.bottom0 {
		margin-bottom:5px;
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
	
	#excelType option[disabled] {
		display: none;
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<title>입고 작업</title>
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
								입고 작업
							</li>
							<li class="active">입고 무게 등록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1> 
								입고 작업
							</h1>
						</div>
						<div id="inner-content-side">
							<form name="weightUploadForm" id="weightUploadForm" enctype="multipart/form-data">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div class="col-xs-12 form-group" style="margin:0px;">
									<div class="row">
										<div class="col-xs-12 col-md-6 form-group">
											<input type="file" id="excelFile" name="excelFile"
												style="display: initial; width: 90%; cursor: pointer; border: 1px solid #D5D5D5; margin: 0;"
												accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
											<i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
										</div>
										<div class="col-xs-12 col-md-1" style="text-align: left;">
											<button type="button" class="btn btn-sm btn-primary " name="excelUp" id="excelUp">
												엑셀 파일 적용하기
											</button>
										</div>
									</div>
								</div>
								<div class="col-xs-12 form-group" style="margin:0px;">
									<div id="table-contents" class="table-contents">
										<div class="hr hr-8 dotted"></div>
											<div class="row">
											<div class="col-xs-12 col-md-6" id="rgstr" style="text-align: left">
												<button type="button" class="btn btn-sm btn-primary" name="rgstrCheck" id="rgstrCheck">
													입고 무게 자료 등록
													<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
												</button>
												<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
													삭제
													<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
												</button>
											</div>
										</div>
										<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="margin-top:6px;">
											<thead >
												<tr>
													<th class="center">
														<label class="pos-rel"> 
															<input type="checkbox" class="ace" /> 
															<span class="lbl"></span>
														</label>
													</th>
													<th class="center colorBlack">No</th>
													<th class="center colorBlack">비고</th>
													<th class="center colorBlack">Hawb No</th>
													<th class="center colorBlack">
														USER ID
													</th>
													<th class="center colorBlack">실 무게</th>
													<th class="center colorBlack">부피 무게</th>
													<th class="center colorBlack">청구 무게</th>
													<th class="center colorBlack">가로</th>
													<th class="center colorBlack">세로</th>
													<th class="center colorBlack">높이</th>
												</tr>
											</thead>
											<tbody >
												<c:forEach items="${orderWeightExcelData}" var="orderExcelData" varStatus="status">
													<tr>
														<td class="center">
															<label class="pos-rel"> 
																<input type="checkbox" class="form-field-checkbox" value="${orderExcelData.nno}" /> 
																<span class="lbl"></span>
															</label>
														</td>
														<td class="center colorBlack">
															<%-- <a href="/cstmr/apply/modifyShpng?nno=${shpngAllList.value[0].nno}">${shpngAllList.value[0].orderNo}</a> --%>
															${status.count }
														</td>
														<td class="center colorBlack">
															<c:if test="${orderExcelData.errorYn eq 'Y'}">
																<div  class="red errorCol"> ${orderExcelData.errorMsg}입니다. </div>
															</c:if> 
															<c:if test="${orderExcelData.length eq '0.0'}">
																<div  class="red errorCol"> Length가 0입니다. </div >
															</c:if> 
															<c:if test="${orderExcelData.width eq '0.0'}">
																<div class="red errorCol"> Width가 0입니다.</div >
															</c:if> 
															<c:if test="${orderExcelData.height eq '0.0'}">
																<div  class="red errorCol"> Height가 0입니다. </div >
															</c:if> 
														</td>
														<td class="center colorBlack">
															<%-- <a href="/cstmr/apply/modifyShpng?nno=${shpngAllList.value[0].nno}">${shpngAllList.value[0].orderNo}</a> --%>
															${orderExcelData.hawbNo}
														</td>
														<td class="center colorBlack">
															${orderExcelData.userId}
														</td>
														<td class="center colorBlack">
															${orderExcelData.wta}
														</td>
														<td class="center colorBlack">
															${orderExcelData.wtc}
														</td>
														<td class="center colorBlack">
															<c:choose>
																<c:when test="${orderExcelData.wta > orderExcelData.wtc }">
																	${orderExcelData.wta}
																</c:when>
																<c:otherwise>
																	${orderExcelData.wtc}
																</c:otherwise>
															</c:choose>
														</td>
														<td class="center colorBlack">
															${orderExcelData.width}
														</td>
														<td class="center colorBlack">
															${orderExcelData.length}
														</td>
														<td class="center colorBlack">
															${orderExcelData.height}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</form>
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
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#4thMenu").toggleClass('open');
					$("#4thMenu").toggleClass('active'); 
					$("#4thFor").toggleClass('active');
				});

			})
			
			$('#excelUp').on('click', function(e) {
				if($('#excelFile').val() == ""){
					alert("선택된 파일이 없습니다")
					return false;
				}
				var datass = new FormData();
				datass.append("file", $("#excelFile")[0].files[0])
				console.log(datass);
				LoadingWithMask();
				$.ajax({
					type : "POST",
					beforeSend : function(xhr)
					{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					url : "/mngr/order/weightExcelUpload",
					data : datass,
					processData: false,
		            contentType: false,
					error : function(){
						alert("subpage ajax Error");
						location.href="/mngr/order/orderWeight";
					},
					success : function(data){
						if(data == "F"){
							alert("등록중 오류가 발생했습니다.")
							location.href="/mngr/order/orderWeight";
						}
						alert(data);
						location.href="/mngr/order/orderWeight";
					}
				})
			});

			$('#rgstrCheck').on('click',function(e){
				LoadingWithMask();
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						if(row.children[2].childElementCount == 0){
							targets.push($(this).val());
						}
					}
				});

				$.ajax({
					url:'/mngr/order/registOrderWeightList',
					type: 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					traditional : true,
					data: {
						targets : targets
					},
					success : function(data) {
						if(data == "F"){
							alert("등록중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
							location.href="/mngr/order/orderWeight";
						}else if(data =="N"){
							alert("등록할 수 있는 데이터가 없습니다.");
							location.href="/mngr/order/orderWeight";
						}else{
							if(data == "S")
								alert("등록되었습니다.")
							else
								alert(data)
							location.href="/mngr/order/orderWeight";
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 입력정보를 확인해 주세요.");
		                location.href="/mngr/order/orderWeight";
		            }
				})
				
			})
			
			
			$('#delCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					$.ajax({
						url:'/mngr/order/delOrderWeightList',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets
						},
						success : function(data) {
							if(data == "S"){
								alert("삭제 되었습니다.")
								location.href="/mngr/order/orderWeight";
							}else if(data == "F"){
								alert("삭제중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
								location.href="/mngr/order/orderWeight";
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
			                location.href="/mngr/order/orderWeight";
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
		</script>
		<!-- script addon end -->
		
	</body>
</html>
