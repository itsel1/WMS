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
	.bottom0 {
		margin-bottom:5px;
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
							<li class="active">입고 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								입고 작업
							</h1>
						</div>
						<div id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="searchForm" id="searchForm">
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
																<input type="text" class=" form-control" id="startDate" name="startDate" value="${parameter.startDate }"/>
																	<span class="input-group-addon">
																		to
																	</span>
																<input type="text" class=" form-control" id="endDate" name="endDate" value="${parameter.endDate }"/>
															</div>
																														
														</div>
													</div>
													<div class="col-md-10" style="padding:0px!important">
														<span class="input-group col-md-12" style="display: inline-flex;">
															<input type="text" class="form-control" style="width:100%; max-width: 70px; text-align: center;" value="발송여부" readonly="true"/>
															<select style="height: 34px">
																<option>발송</option>
																<option>미발송</option>
															</select>
															<input type="text" class="form-control" style="width:100%; max-width: 60px; text-align: center;" value="업체ID" readonly="true"/>
															<input type="text" class="form-control" name="userId" placeholder="${searchKeyword}" style="width:100%; max-width: 150px" value="${parameter.userId }"/>
															<input type="text" class="form-control" style="width:100%; max-width: 65px; text-align: center;" value="송장번호" readonly="true"/>
															<input type="text" class="form-control" name="hawbNo" placeholder="${searchKeyword}" style="width:100%; max-width: 150px" value="${parameter.hawbNo }"/>
															<input type="text" class="form-control" style="width:100%; max-width: 90px; text-align: center;" value="수취인 이름" readonly="true"/>
															<input type="text" class="form-control" name="cneeName" placeholder="${searchKeyword}" style="width:100%; max-width: 150px" value="${parameter.cneeName }"/>
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
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<input type="hidden" id="alertMsg" name="alertMsg" value="${results.rstMsg}">
									<button type="button" id="searchBtn" name="searchBtn" class="btn btn-default no-border btn-sm" data-toggle="modal" data-target="#searchModal">
										<i class="ace-icon fa fa-search icon-on-right bigger-110">검색조건 입력하기</i>
									</button>
								</div>
								<br>
								
								
								<div id="rgstr-user" style="text-align: right">
									<span class="red">※운송장 출력은 같은 운송사끼리만 가능합니다.</span> &nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary btn-sm" id="printCheck" name="printCheck">
										운송장 출력
										<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
										</i>
									</button>
									<button type="button" id='registBtn' name='registBtn' class="btn btn-sm btn-primary">
									 입고처리
									</button>
									<button id='updateWeightBtn' name='updateWeightBtn' class="btn btn-sm btn-primary">
										 무게업데이트(용성)
									</button>
									<!-- <button id='updateFbWeightBtn' name='updateFbWeightBtn' class="btn btn-sm btn-primary">
										 무게업데이트(FB)
									</button> -->
									<button id='mailBtn' name='mailBtn' class="btn btn-sm btn-primary">
										 메일테스트
									</button>
									<button id='mailBtn2' name='mailBtn2' class="btn btn-sm btn-primary">
										 메일테스트2
									</button>
								</div>
								
								
							</div>
							<br/>
							<form name="registForm" id="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="table-contents" class="table-contents">
										<table id="dynamic-table" class="table table-bordered table-dark containerTable" style="table-layout:fixed">
											<thead>
												<tr>
													<th class="center" style="width:4%">
														<label class="pos-rel">
																<input type="checkbox" class="ace" /> <span class="lbl"></span>
														</label>
													</th>
													<th class="center" style="width:4%">
														번호
													</th>
													<th class="center colorBlack" style="width:4%">
														도착지
													</th>
													<th class="center colorBlack" style="width:7%">
														USER ID
													</th>
													<th class="center colorBlack" style="width:9%">
														HAWB NO
													</th>
													<th class="center colorBlack" style="width:4%">
														BOX 수량
													</th>
													<th class="center colorBlack" style="width:10%">
														수취인 명
													</th>
													<th class="center colorBlack" style="width:7%">
														Tel.
													</th>
													<th class="center colorBlack" style="width:5%">
														우편번호
													</th>
													<th class="center colorBlack">
														수취인 주소
													</th>
													<th class="center colorBlack" style="width:4%">
														배송사
													</th>
													<th class="center colorBlack" style="width:4%">
														실무게
													</th>
													<th class="center colorBlack" style="width:7%">
														부피무게
													</th>
													<th class="center colorBlack" style="width:10%">
														대표 상품명
													</th>
													<th class="center colorBlack" style="width:6%">
														총 금액
													</th>
													
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${orderList}" var="orderItem" varStatus="status">
													<tr>
														<td class="center">
															<label class="pos-rel">
																	<input type="checkbox" class="ace" id="checkboxs" name="checkboxs" 
																	<%-- <c:if test="${(orderItem.userWta ne '0.0') || (orderItem.userWtc ne '0.0')}"> --%>
																		value="${orderItem.transCode},${orderItem.nno}"
																	<%-- </c:if> --%>
																	<%-- <c:if test="${(orderItem.userWta eq '0.0') && (orderItem.userWtc eq '0.0')}">
																		value=""
																	</c:if> --%>
																	/> <span class="lbl"></span>
															</label>
														</td>
														<td class="center colorBlack">
															${status.count}
														</td>
														<td class="center colorBlack">
															${orderItem.dstnNation}
														</td>
														<td class="center colorBlack">
															${orderItem.userId}
														</td>
														<td class="center colorBlack">
															${orderItem.hawbNo }
														</td>
														<td class="center colorBlack">
															${orderItem.boxCnt }
														</td>
														<td class="center colorBlack">
															${orderItem.cneeName }<br/>
															<%-- ${orderItem.nativeCneeName } --%>
														</td>
														<td class="center colorBlack">
															${orderItem.cneeTel }
														</td>
														<td class="center colorBlack">
															${orderItem.cneeZip }
														</td>
														<td class="center colorBlack" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">
															${orderItem.cneeAddr } ${orderItem.cneeAddrDetail }
														</td>
														<td class="center colorBlack">
															${orderItem.transCode }
														</td>
														<td class="center colorBlack">
															${orderItem.userWta }
														</td>
														<td class="center colorBlack">
															${orderItem.userWtc }
														</td>
														<td class="center colorBlack">
															${orderItem.mainItem }
														</td>
														<td class="center colorBlack">
															${orderItem.totalValue }
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
			
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
					$("#4thMenu").toggleClass('open');
					$("#4thMenu").toggleClass('active'); 
					$("#4thThr").toggleClass('active');
					if($("#alertMsg").val() != ""){
						alert($("#alertMsg").val());
					}
				});


				$("#registBtn").on('click',function(e){
					var formData = $("#registForm").serialize();
					$("#registForm").attr('action','/mngr/order/orderRcptListPost');
					$("#registForm").attr('method','post');
					$("#registForm").submit()


				})
				
				$("#updateFbWeightBtn").on('click', function(e) {
					var formData = $("#registForm").serialize();
					$("#registForm").attr('action','/mngr/order/updateWeightFB');
					$("#registForm").attr('method','post');
					$("#registForm").submit();
				})
				$("#updateWeightBtn").on('click',function(e){
					var formData = $("#registForm").serialize();
					$("#registForm").attr('action','/mngr/order/updateWeightYS');
					$("#registForm").attr('method','post');
					$("#registForm").submit()
				})
				$("#mailBtn").on('click',function(e){
					var formData = $("#registForm").serialize();
					$("#registForm").attr('action','/mngr/order/sendMailTest');
					$("#registForm").attr('method','post');
					$("#registForm").submit()
				})
				$("#mailBtn2").on('click',function(e){
					var formData = $("#registForm").serialize();
					$("#registForm").attr('action','/mngr/order/sendMailTest2');
					$("#registForm").attr('method','post');
					$("#registForm").submit()
				})
					


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
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : 550,
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

					$('#printCheck').on('click',function(e){
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								var temp = $(this).val().split(",");
								targets.push(temp[1]);
							}
						});
						window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");

					})


				
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
