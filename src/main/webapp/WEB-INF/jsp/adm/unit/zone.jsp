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
	</head> 
	<title>단가 관리</title>
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
								단가 관리
							</li>
							<li class="active">Zone 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								Zone 관리
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="zoneForm" id="zoneForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="search-div">
									<br>
										<div class="row">
											<div class="col-xs-12 col-sm-11 col-md-10">
												<span class="input-group" style="display: inline-flex; width:100%">
													<input type="text" value="택배회사" readonly style="width:80px"/>
													<input type="text" class="form-control" name="transName" id="transName" placeholder="${transName}" style="width:100%; max-width: 300px"/>
													<input type="text" value="택배회사 코드" readonly style="width:100px"/>
													<input type="text" class="form-control" name="transCode" id="transCode" placeholder="${transCode}" style="width:100%; max-width: 300px"/>
													<input type="text" value="Zone" readonly style="width:50px"/>
													<input type="text" class="form-control" name="zoneCode" id="zoneCode" placeholder="${zoneCode}" style="width:100%; max-width: 300px"/>
													<button type="button" name="srchBtn" id="srchBtn" class="btn btn-default no-border btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
												</span>
											</div>
										</div>
									
									<br>
									<div id="buttonDiv" style="text-align: right">
										<button type="button" name="rgtExcelBtn" id="rgtExcelBtn" class="btn btn-sm btn-primary">
										 	zone 엑셀 등록하기(TEST)
										</button>
										<div class="hidden">
										<input type="file" id="excelFile" name="excelFile"
											style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
											accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
										</div>
										<button type="button" name="rgtBtn" id="rgtBtn" class="btn btn-sm btn-primary">
										 등록하기
										</button>
										<button type="button" name="delBtn" id="delBtn" class="btn btn-sm btn-primary">
										 삭제하기
										</button>
									</div>
								</div>
								<br>
								<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
									<div class="display" style="width:1800px;">
										<table id="dynamic-table" class="table table-bordered table-hover">
											<thead>
												<tr>
													<th class="center">
														<label class="pos-rel">
															<input type="checkbox" class="ace"/>
															<span class="lbl"></span>
														</label>
													</th>
													
													<th class="center colorBlack" >
														택배회사
													</th>
													<th class="center colorBlack" >
														택배회사 코드
													</th>
													<th class="center colorBlack" >
														Zone 코드
													</th>
													<th class="center colorBlack" >
														최소 무게
													</th>
													<th class="center colorBlack" >
														최대 무게
													</th>
													<th class="center colorBlack" >
														무게 단위
													</th>
													<th class="center colorBlack" >
														최소 금액
													</th>
													<th class="center colorBlack" >
														추가 단위
													</th>
													<th class="center colorBlack" >
														단위당 추가 금액
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${zoneListInfo}" var = "zoneInfo" varStatus = "status">
													<tr>
														<td class="center" style="word-break:break-all;" >
															<label class="pos-rel">
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>
													
														<td class="center" style="cursor: pointer;" name="targetName${status.count}" id="targetName${status.count}">
															${zoneInfo.transName}
														</td>
														<td>
															${zoneInfo.transCode}
														</td>
														<td class="center" >
															${zoneInfo.zoneCode}
														</td>
														<td class="center" >
															${zoneInfo.fromWt}
														</td>
														<td class="center" >
															${zoneInfo.toWt}
														</td>
														<td class="center" >
															${zoneInfo.wtUnit}
														</td>
														<td class="center" >
															${zoneInfo.minCharge}
														</td>
														<td class="center" >
															${zoneInfo.perWt}
														</td>
														<td class="center" >
															${zoneInfo.addCharge}
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
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#1stMenu").toggleClass('open');
					$("#1stMenu").toggleClass('active'); 
					$("#1stThr").toggleClass('active');
				});

				$('#rgtBtn').on('click',function(e){
					location.href="/mngr/unit/registZone"
				})
				
				$('#srchBtn').on('click',function(e){
					var formData = $("#zoneForm").serialize();
					$("#zoneForm").attr("action","/mngr/unit/shpComPrice");
					$("#zoneForm").submit();
				})
				
				$('#dynamic-table').on('click', 'td[name*=targetName]' , function(){
					var row = $(this).closest('tr').get(0);
					$('#transName').val(row.children[1].innerText);
					$('#transCode').val(row.children[2].innerText);
					$('#zoneCode').val(row.children[3].innerText);
					$("#zoneForm").attr("action","/mngr/unit/modifyZone/"+row.children[2].innerText);
					$("#zoneForm").submit();
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
			        "scrollY" : 500,
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

				$('#rgtExcelBtn').on('click',function(e){
					$("#excelFile").trigger("click");
				})

				$("#excelFile").on('change',function(e){
						var datass = new FormData();
						datass.append("file", $("#excelFile")[0].files[0]);
						LoadingWithMask();
						
						$.ajax({
							url:'/mngr/rls/zoneExcelUpload',
							type: 'POST',
							data : datass,
							processData: false,
				            contentType: false,
							beforeSend : function(xhr)
							{ 
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {
								
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
		</script>
		<!-- script addon end -->
	</body>
</html>
