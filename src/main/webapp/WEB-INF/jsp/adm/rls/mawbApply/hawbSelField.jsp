<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<style type="text/css">
	.colorBlack {color:#000000 !important;}
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:70px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	
	</style>
	</head> 
	<body class="no-skin">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="detail-contents" class="detail-contents">
			<div id="home" class="tab-pane in active">
				<div class="row no-gutters">
					<div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 col-sm-12 "">
							<div class="col-xs-6"> 
								<label style="font-size:40px;">대상 MAWB : </label><label class="light-red" style="font-size:500%;">${targetMawb }</label> 
							</div>
							<input type="hidden" id="targeMawbNo" name="targeMawbNo" value="${targetMawb}"/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 " style="text-align: right; "> 
							<label style="font-weight: bold;" class="red">미 등록 개수 : ${fn:length(hawbList)}</label>
						</div>
						<div class="row">
							<div class="col-sm-12 center">
									<table id="dynamic-table" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													HawbNo.
												</th>
												<th class="center colorBlack">
													UserID
												</th>
												<th class="center colorBlack">
													Company
												</th>
												<th class="center colorBlack">
													수취인
												</th>
												<th class="center colorBlack">
													BOX
												</th>
												<th class="center colorBlack">
													W/T(A)
												</th>
												<th class="center colorBlack">
													W/T(V) 
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${hawbList}" var="hawbVO" varStatus="status">
												<tr>
													<td class="center" style="word-break:break-all;vertical-align: middle;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" id="check${status.count}" name="check${status.count}" value="${hawbVO.hawbNo}"/>
															<span class="lbl"></span>
														</label>
													</td>
													<td>
														${hawbVO.hawbNo}
													</td>
													<td>
														${hawbVO.userId}
													</td>
													<td>
														${hawbVO.shipperName}
													</td>
													<td>
														${hawbVO.cneeName}
													</td>
													<td>
														${hawbVO.boxCnt}
													</td>
													<td>
														${hawbVO.wta}
													</td>
													<td>
														${hawbVO.wtc}
													</td>
												</tr>
												
											</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<button type="button" class="btn btn-danger" id="cancleBtn" name="cancleBtn">취소</button>		
											<button type="button" class="btn btn-primary" id="registBtn" name="registBtn">등록</button>
										</div>
										
									</div>
								</div>
							</div>
					</div>
				</div>
				<div class="space-20"></div>
			</div><!-- /#home -->
		</div>
		
		
		
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
			
		<!-- script addon start -->
		<script type="text/javascript">
			var count = 0;
			jQuery(function($) {
				$(document).load(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thTwo").toggleClass('active');
				});


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

				$("#cancleBtn").on('click',function(e){
					history.back();
				})

				$("#registBtn").on('click',function(e){
					var targets = new Array();
					$('input[id*="check"]:checked').each(function(i) {
						targets.push($(this).val());
					});
					var list = { targetData: targets,
								 mawbNo : $("#targeMawbNo").val() }
					$.ajax({
						url:'/mngr/rls/mawbHawbRegist',
						type: 'POST',
						data: list,
						success : function(data) {
							if(data == "S"){
								alert("등록 되었습니다.");
								location.reload();
							}else{
								alert(data);
							}
							
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("mawb입력에 실패 하였습니다. 관리자에게 문의해 주세요.");
			            }
					})

				})
				
				
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
