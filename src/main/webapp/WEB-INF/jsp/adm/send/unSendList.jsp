<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>

	<style type="text/css">
	.colorBlack {
		color: #000000 !important;
	}
	
	.bottom0 {
		margin-bottom:5px;
	}
	
	.containerTable {
		width: 100% !important;
	}
	table ,tr td{
		word-break:break-all;
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
	<title>미발송 목록</title>
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
								발송 리스트
							</li>
							<li class="active">미발송 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								미발송 목록
							</h1>
						</div>
						<div id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="search-button" id="search-botton" method="get" action="/mngr/send/unSendList">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-12">
											<div class="row">
												<div class="col-xs-8 col-sm-11 col-md-12">
													<div class="col-xs-8 col-sm-11 col-md-2" style="padding:0px 0px 0px 15px!important">
														<div class="input-group">
															<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
															</span>
															<div class="input-group input-daterange">
																<input type="text" class=" form-control" name="startDate" value="${search.startDate}"/>
																	<span class="input-group-addon">
																		to
																	</span>
																<input type="text" class=" form-control" name="endDate" value="${search.endDate}"/>
															</div>
															
														</div>
													</div>
													<div class="col-md-10" style="padding:0px!important">
														<span class="input-group col-md-12" style="display: inline-flex;">
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 60px; text-align: center;" value="USER ID" readonly="true"/>
															<input type="text" class="form-control" name="userId" placeholder="${search.userId}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 85px; text-align: center;" value="mawbNo" readonly="true"/>
															<input type="text" class="form-control" name="mawbNo" placeholder="${search.mawbNo}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="hawbNo" readonly="true"/>
															<input type="text" class="form-control" name="hawbNo" placeholder="${search.hawbNo}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="orderNo" readonly="true"/>
															<input type="text" class="form-control" name="orderNo" placeholder="${search.orderNo}" style="width:100%; max-width: 150px"/>
															<button type="submit" class="btn btn-default no-border btn-sm">
																<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
															</button>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>
								<div class="col-xs-12 col-sm-12 hidden-md hidden-lg">
									<button type="submit" class="btn btn-default no-border btn-sm" data-toggle="modal" data-target="#searchModal">
										<i class="ace-icon fa fa-search icon-on-right bigger-110">검색조건 입력하기</i>
									</button>
								</div>
								<br>
							</div>
							<br/>
							<div id="table-contents" class="table-contents">
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
													Mawb No
												</th>
												<th class="center colorBlack">
													Hawb No
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
														${send.mawbNo }
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
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Modal Input -->
		<div class="modal fade" id="searchModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true" >
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content" style="border-radius: 40px 40px;">
					<div class="modal-header" style="text-align: center">
					<h3 class="modal-title" id="exampleModalScrollableTitle">상세 조건</h3>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body ">
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="조회날짜" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control" name="start" />
												<input type="text" class="input-sm form-control" name="end" />
											</div>
										</div>
									</div>
									
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5">
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="발송여부" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7">
											<select style="width: 100%; height:34px">
												<option>발송</option>
												<option>미발송</option>
											</select>
										</div>
									</div>
									<%-- <br/>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5">
											<select style="width: 100%;">
												<option>수취인 이름</option>
												<option>수취인 HP</option>
												<option>수취인 주소</option>
											</select>
										</div>
										<div class="col-xs-7 col-sm-7">
											<input type="text" name="keywords1" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5">
											<select style="width: 100%;">
												<option>입고 날짜</option>
												<option>입고 형태</option>
												<option>입고 수량</option>
											</select>
										</div>
										<div class="col-xs-7 col-sm-7">
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div> --%>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="업체ID" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
										<br/>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="송장번호" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="주문번호" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="TrackNo." readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="수취인 이름" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="text-align: center;border-radius: 0px 0px 40px 40px;">
						<button type="button" class="btn btn-secondary " data-dismiss="modal">닫기</button>
						
						<button type="button" class="btn btn-success">검색</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal Input End -->
		
		
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
					$("#8thMenu").toggleClass('open');
					$("#8thMenu").toggleClass('active'); 
					$("#8thTwo").toggleClass('active');
				});



				//daterangepicker plugin
				//to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
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
					format: 'yyyymmdd',

				});
				
				
				//Table Function

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "lt",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
				        "scrollY": 500,
						select: {
							style: 'multi',
							selector: 'td:first-of-type'
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
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
