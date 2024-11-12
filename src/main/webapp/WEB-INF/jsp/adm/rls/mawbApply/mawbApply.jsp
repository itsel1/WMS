<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<style type="text/css">
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
	.table>thead>tr>th{
		color: #000000 !important;
	}
	.space-bottom-10{
		margin:0 0 10px 0;
		max-height: 1px;
		min-height: 1px;
		overflow: hidden;
	}
	
	.noneArea {
		margin:0px !important;
		padding:0px 12px !important;
		border-bottom : none;
	}
	
	.containerTable {
		width: 100% !important;
	}
	
	table ,tr td{
		word-break:break-all;
	}
	
	
	#loading {
		height: 100%;
		left: 0px;
		position: fixed;
		_position: absolute;
		top: 0px;
		width: 100%;
		filter: alpha(opacity = 50);
		-moz-opacity: 0.5;
		opacity: 0.5;
	}
	.loading {
		background-color: white;
		z-index: 999999;
	}
	#loading_img {
		position: absolute;
		top: 50%;
		left: 50%;
		/* height: 200px; */
		margin-top: -75px; 
		margin-left: -75px; 
		z-index: 999999;
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
	<title>MAWB Scan APPLY</title>
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
								출고
							</li>
							<li class="active">MAWB APPLY</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content noneArea">
						<div class="page-header">
							<h1>
								MAWB APPLY
							</h1>
						</div>
						<form name="registForm" id="registForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="row">
							<div class="col-xs-12 col-sm-2" style="padding:0px 0px 0px 15px!important">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-calendar bigger-110"></i>
									</span>
									<div class="input-group input-daterange">
										<input type="text" class="dateClass form-control" id="start" name="start" maxlength="10"/>
											<span class="input-group-addon">
												to
											</span>
										<input type="text" class="dateClass form-control" id="end" name="end" />
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding">
								<div class="col-xs-12 col-sm-4 no-padding">
									<input class="center" type="text" style="width:100%" value="MawbNo" readOnly/>
								</div>
								<div class="col-xs-12 col-sm-8 no-padding">
									<input id="MawbNoScrh" class="center" type="text" style="width:100%" />
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding">
								<div class="col-xs-12 col-sm-4 no-padding">
									<input class="center" type="text" style="width:100%" value="편명" readOnly/>
								</div>
								<div class="col-xs-12 col-sm-8 no-padding">
									<input id="fligntNameScrh" class="center" type="text" style="width:100%" />
								</div>
							</div>
							<div class="col-xs-12 col-sm-1 no-padding">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<button type="button" id="scrhBtn" class="btn btn btn-primary no-border btn-sm">검색</button>
							</div>
						</div>
						</form>
						<div class="row">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<br>
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<table id="grid-table"></table>

							<div id="pager"></div>
							<br/>
							<div id="table-contents" class="table-contents center">
									<div class="row no-gutters">
										<div class="col-xs-12 col-sm-12">
											<div class="col-xs-12 col-md-9" style="text-align: left; ">
												<button class="btn btn-primary btn-sm" id="printCheck" name="printCheck">
													운송장 출력
													<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
													</i>
												</button>
												<input type="button" class="btn btn-white btn-inverse" id="printTypeC" value="BlPrint (C)"/>
												<!-- <button id='mailBtn' name='mailBtn' class="btn btn-sm btn-primary">
													 메일테스트
												</button> -->
												&nbsp;
												배송사 구분 : 
												<select id='transCode' name='transCode'>
													<option value="">전체</option>
													<c:forEach items="${transComInfo}" var="transCom" varStatus="status">
														 <option value="${transCom.transCode}" <c:if test="${transCode eq transCom.transCode}">selected</c:if> >${transCom.transName}</option>
													</c:forEach>
												</select>
												&nbsp;
												송장 출력 타입 :
												<select id='blSelect' name='blSelect'>
													<option value="default">기본 설정</option>
													<c:forEach items="${blList}" var="blInfo" varStatus="statusBl">
														 <option value="${blInfo}" >${blInfo} Type</option>
													</c:forEach>
												</select>
												&nbsp;&nbsp;
												<span class="red">※운송장 출력은 같은 운송사끼리만 가능합니다.</span>&nbsp;&nbsp;&nbsp;&nbsp;
												<i class="fa fa-check" style="font-size:14px;color:red"></i><span class="red">&nbsp;: 라벨 생성 필요</span>
												
											</div>
											<div class="col-xs-12 col-md-1" style="text-align: right; "> 
												<label style="font-weight: bold;" class="red">총 WT(A) : <fmt:formatNumber value="${totalWeightWta}" pattern=".00"/></label>
											</div>
											<div class="col-xs-12 col-md-1" style="text-align: right; "> 
												<label style="font-weight: bold;" class="red">총 WT(V) : <fmt:formatNumber value="${totalWeightWtv}" pattern=".00"/></label>
											</div>
											<div class="col-xs-12 col-md-1" style="text-align: right; "> 
												<label style="font-weight: bold;" class="red">미 등록 개수 : ${fn:length(hawbList)}</label>
											</div>
										</div>
										<c:if test="${userId eq 'itsel2' || orgStation eq '213' || userId eq 'admin1'}">
										<div class="row no-gutters" style="margin-top:10px;">
											<div class="col-xs-12 col-sm-12">
												<div class="col-xs-12 col-md-12" style="text-align: left;">
													<button class="btn btn-danger btn-sm" id="mawbApplyCheck" name="mawbApplyCheck">
														MAWB 일괄 적용
													</button>
													<input type="text" name="mawbNoInput" id="mawbNoInput" placeholder="MAWB NO">
													&nbsp;&nbsp;<span class="red">※ Mawb 생성 후 적용하세요</span>
												</div>
											</div>
										</div>
										</c:if>
											<div class="col-xs-12 col-sm-12" style="margin-top:5px;">
												<div class="row">
													<div class="col-sm-12 center">
															<table id="dynamic-table" class="table table-bordered table-hover containerTable">
																<thead>
																	<tr>
																		<th class="center">
																			<label class="pos-rel">
																					<input type="checkbox" class="ace"/> <span class="lbl"></span>
																			</label>
																		</th>
																		<th class="center colorBlack">
																			HawbNo.
																		</th>
																		<th class="center colorBlack">
																			운송사
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
																					<input type="checkbox" class="ace" id="check${status.count}" name="checkNno" value="${hawbVO.nno}"/>
																					<span class="lbl"></span>
																				</label>
																				<input type="hidden" name="transCodeList[]" value="${hawbVO.transCode}"/>
																			</td>
																			<td>
																				<input type="hidden" name="hawbList[]" value="${hawbVO.hawbNo}"/>
																				${hawbVO.hawbNo}
																				<c:if test="${hawbVO.transName eq 'ACI-US'}">
																					<c:if test="${hawbVO.checkCnt eq 0}">
																						<i class="fa fa-check" style="font-size:14px;color:red"></i>
																					</c:if>
																				</c:if>
																			</td>
																			<td>
																				${hawbVO.transName}
																			</td>
																			<td>
																				${hawbVO.userId}
																			</td>
																			<td>
																				${hawbVO.comEName}
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
														</div>
													</div>
												</div>
											</div>
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
		
		
		<!-- script addon start -->
		<script type="text/javascript">
			var loading = "";
			var wnd;
			var cellNum=1;
			var targetMawbNo;
			var grid_data = 
			[ 
			];
			var currentId = "";
			var grid_selector = "#grid-table";
			var pager_selector = "#pager";

			$("#transCode").on('change',function(){
				location.href = "/mngr/rls/mawbAply?transCode="+$("#transCode").val();
			})
			
			$("#scrhBtn").on('click ', function(){
				/* if($(".ui-jqgrid-headlink").hasClass("ui-icon-circle-triangle-s")){
					$('.ui-jqgrid-titlebar-close').trigger("click");
				} */
				$(grid_selector).clearGridData();
				$.ajaxSetup({
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    	xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			    	}
				});
			    $(grid_selector).setGridParam({
			        url:'/mngr/rls/mawbLoad2',    //*.json 으로 controller 호출
			        datatype: 'json',
			        mtype : 'get',
			        postData: {
				        mawbNo: $("#MawbNoScrh").val(),
				        fltNo : $("#fligntNameScrh").val(),
				        arrDate: $("#end").val(),
				        depDate: $("#start").val()
			        }
			    }).trigger("reloadGrid");
			})
		
			jQuery(function($) {
				$(document).ready(function() {
					loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thFiv").toggleClass('active');
					$('.ui-jqgrid-titlebar-close').css("width","100%");
					$.ajaxSetup({
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    	xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				    	}
					});
					
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
				        "scrollY" : 350,
						select: {
							style: 'multi',
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
			
			function mawbShow(mawbNo){
				window.open("/mngr/rls/mawbDetail?target="+mawbNo,"_blank", "width=1500, height=960", "toolbar=no","resizable=yes","directories=no",);
				

			}

			jQuery(function($) {
				$("input[id*='Scrh']").keydown(function(key){
					if(key.keyCode == 13){
						$('#scrhBtn').trigger("click");
					}
				});

				
				var parent_column = $(grid_selector).closest('[class*="col-"]');
				//resize to fit page size
				$(window).on('resize.jqGrid', function () {
					$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			    })
				
				//resize on sidebar collapse/expand
				$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
					if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
						//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
						setTimeout(function() {
							$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
						}, 20);
					}
			    })
				
				jQuery(grid_selector).jqGrid({
					url :"/mngr/rls/mawbLoad",
					mtype : "get",
					/* data: grid_data, */
					datatype: "json",
					height: 150,
					colNames:[' ','', 'MawbNo','도착Station', '운송사','운송사코드','편명','출발일', '출발시간', '도착일','도착시간'],
					colModel:[
						{name:'myac',index:'ss', width:80, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								//delbutton: false,//disable delete button
								
								delOptions:{recreateForm: true, 
											beforeShowForm:beforeDeleteCallback,
											url: "/mngr/rls/mawbDelete",
											datatype: 'json',
											caption : '삭제',
											msg : '해당 Mawb를 삭제 하시겠습니까?',
											delData: {
												mawbNo : function(){
													const selId = $(grid_selector).jqGrid('getGridParam', 'selrow');
						                            const divId = $(grid_selector).jqGrid('getCell', selId, 'mawbNo');
													return divId;
												}

											},
											afterSubmit : function(){
												jQuery(grid_selector).jqGrid("clearGridData");
												jQuery(grid_selector).jqGrid("setGridParam",
													{
														url:"/mngr/rls/mawbLoad",
														datatype: "json"
													}).trigger("reloadGrid");
												$('#testarea').empty();
												return ["success"];
												}
											},
								editformbutton:true, 
								editOptions:{recreateForm: true, 
											 closeAfterEdit: true, 
											 beforeSubmit : function(postdata, formid){
													if(postdata.mawbNo ==""){
														alert("MawbNo를 입력해 주세요")
														return false;
													}
													if(postdata.transName=="ACI-US") {
														postdata.dstnStation="LAX";
													}
													if(postdata.dstnStation==""){
														alert("도착Station을 선택해 주세요")
														return false;
													}
													if(postdata.fltNo ==""){
														alert("편명를 입력해 주세요")
														return false;
													}
													if(postdata.depDate==""){
														alert("출발일을 입력해 주세요")
														return false;
													}
													if(postdata.depTime==""){
														alert("출발시간을 입력해 주세요")
														return false;
													}else{
														var timeFormat = /^([01][0-9]|2[0-3])([0-5][0-9])$/;
														if(!timeFormat.test(postdata.depTime)){
															alert("출발시간을 0000~2359사이로 입력해 주세요")
															return false;
														}
													}
													if(postdata.arrDate==""){
														alert("도착일을 입력해 주세요")
														return false;
													}
													if(postdata.arrTime==""){
														alert("도착시간을 입력해 주세요")
														return false;
													}else{
														var timeFormat = /^([01][0-9]|2[0-3])([0-5][0-9])$/;
														if(!timeFormat.test(postdata.arrTime)){
															alert("도착시간을 0000~2359사이로 입력해 주세요")
															return false;
														}
													}

													var string=postdata.depDate;
													postdata.depDate = string.replace(/[^0-9]/g,'');
													string=postdata.arrDate;
													postdata.arrDate = string.replace(/[^0-9]/g,'');
													return ["success"];
												 },
											 afterSubmit : function(response, postdata) {
												jQuery(grid_selector).jqGrid("clearGridData");
												jQuery(grid_selector).jqGrid("setGridParam",
													{
														url:"/mngr/rls/mawbLoad",
														datatype: "json"
													}).trigger("reloadGrid");
												$('#testarea').empty();
												return ["success"];
											},
											 beforeShowForm:
												 beforeEditCallback},
							}
						},
						{name:'legacyMawbNo', index:'legacyMawbNo', hidden:true, editable:true, sortable: false, sorttype:"int"},
						{name:'mawbNo',index:'mawbNo', width:150, editable:true, sortable: false, sorttype:"int" },
						/* {name:'dstnStation',index:'dstnStation', width:150, editable:true, sortable: false, edittype:"select", editoptions:{dataUrl:"/mngr/rls/nationSearch", buildSelect:pickNation}  }, */
						{name:'dstnStation',index:'dstnStation', width:150, editable:true, sortable: false},
						/* {name:'transName',index:'transName', width:150, editable:true, sortable: false, edittype:"select", editoptions:{dataUrl:"/mngr/rls/transCodeSearch", buildSelect:pickNation}}, */
						{name:'transName',index:'transName', width:150, editable:true, disalbed : true, sortable: false},
						{name:'transCode',index:'transCode', hidden:true, editable:true, sortable: false},
						{name:'fltNo',index:'fltNo', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'depDate',index:'depDate',width:90, editable:true, sorttype:"date",editoptions:{maxlength:10},unformat: pickDate},
						{name:'depTime',index:'depTime', width:90, sortable: false, editable: true,editrules:{number:true},editoptions:{maxlength:4}},
						{name:'arrDate',index:'arrDate',width:90, editable: true, sorttype:"date",editoptions:{maxlength:10},unformat: pickDate},
						{name:'arrTime',index:'arrTime', width:60, sortable: false, editable: true,editrules:{number:true},editoptions:{maxlength:4}},
						, 
					], 
			
					rowNum:10,
					pager : pager_selector,
					loadonce : true,
					//toppager: true,
					//multikey: "ctrlKey",
					altRows: true,
					ignoreCase : true,
					onCellSelect : function(rowid,iCol,cellcontent,e){
						var row = jQuery(grid_selector).getRowData(rowid);
						targetMawbNo = row.mawbNo;
						if(iCol!=0){
							mawbShow(targetMawbNo);
						}
					},
					loadComplete : function(data) {
						$("#nodata").remove();
						$(".HeaderButton ").remove();
						if(data.length==0){
							$(".ui-jqgrid-btable").after("<p id='nodata' style='margin-top: 5px; text-align: center; font-weight: bold;'>검색된 자료가 없습니다</p>");
						}
						
						var table = this;


						
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
						}, 0);
					},
					editurl: "/mngr/rls/mawbUpdate", 
					caption: "오늘자 Mawb"
			
				});

				$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
			
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
							.datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true}); 
					}, 0);
				}

				//enable datepicker
				function pickNation( data ) {
						var result = '<select>';
						for(var idx=0; idx < jQuery.parseJSON(data).length; idx++) {
							result += '<option value="' + jQuery.parseJSON(data)[idx].transCode + '">' + jQuery.parseJSON(data)[idx].transName + '</option>';
						}
						result += '</select>';
						return result;
				}


				function inputTime(cellvalue, options, cell ) {
					setTimeout(function(){
						alert($(cell).find('input[type=text]').val()); 
					}, 0);
				}
			
				// MAWB 추가 Start
				//navButtons
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						
						add: true,
						addicon : 'ace-icon fa fa-plus-circle purple',
						addtext : 'MAWB 추가하기',
						addtitle : "추가",
						edit: false,
						del: false,
						search : false,
						refresh : false
					},
					{
						//edit record form
						//closeAfterEdit: true,
						//width: 700,
						/* recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						} */
					},
					{
						//new record form
						//width: 700,
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						top: 200,
						left: 500,
						addCaption: "MAWB 등록" ,
						url: "/mngr/rls/mawbInsert",
						mtype:"POST",
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
							
							style_edit_form(form);
						},
						closeAfterAdd : true,
						beforeSubmit : function(postdata, formid){

							var dataList = $(grid_selector).getRowData();
							for( var i = 0; i < dataList.length; i++ ){
								if(dataList[i].mawbNo == postdata.mawbNo){
									alert("이미 등롣된 MawbNo 입니다.")
									return false;
								}
							}
							var temp = postdata.mawbNo;
							postdata.mawbNo = temp.trim();
							if(postdata.mawbNo ==""){
								alert("MawbNo를 입력해 주세요")
								return false;
							}
							if(postdata.transName=="ACI-US") {
								postdata.dstnStation="LAX";
							}
							if(postdata.dstnStation==""){
								alert("도착Station을 선택해 주세요")
								return false;
							}
							if(postdata.fltNo ==""){
								alert("편명를 입력해 주세요")
								return false;
							}
							if(postdata.depDate==""){
								alert("출발일을 입력해 주세요")
								return false;
							}
							if(postdata.depTime==""){
								alert("출발시간을 입력해 주세요")
								return false;
							}else{
								var timeFormat = /^([01][0-9]|2[0-3])([0-5][0-9])$/;
								if(!timeFormat.test(postdata.depTime)){
									alert("출발시간을 0000~2359사이로 입력해 주세요")
									return false;
								}
							}
							if(postdata.arrDate==""){
								alert("도착일을 입력해 주세요")
								return false;
							}
							if(postdata.arrTime==""){
								alert("도착시간을 입력해 주세요")
								return false;
							}else{
								var timeFormat = /^([01][0-9]|2[0-3])([0-5][0-9])$/;
								if(!timeFormat.test(postdata.arrTime)){
									alert("도착시간을 0000~2359사이로 입력해 주세요")
									return false;
								}
							}
							
							var string=postdata.depDate;
							postdata.arrDate = string.replace(/[^0-9]/g,'');
							string=postdata.depDate;
							postdata.arrDate = string.replace(/[^0-9]/g,'');
							return ["success"];
						 },
						afterSubmit : function(response, postdata) {
							jQuery(grid_selector).jqGrid("clearGridData");
							jQuery(grid_selector).jqGrid("setGridParam",
								{
									url:"/mngr/rls/mawbLoad",
									datatype: "json"
								}).trigger("reloadGrid");
							return ["success"];
						}
					}
				)
			
				// MAWB 추가 End
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=depDate]').datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true})
					form.find('input[name=arrDate]').datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true})
					form.find('input[name=transName]').attr("readonly",true);
					form.find('input[name=transName]').on('click',function(e){
						if(wnd){
							wnd.close();
						}
						wnd = window.open("mawbTransCom","_blank","toolbar=yes, resizable=no,directories=no,width=480,height=600");
					})
					
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
					buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').hide();
					buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
					buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
					buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					var selectCode = $(grid_selector).jqGrid('getGridParam', "selrow");
					var row = $(grid_selector).getRowData(selectCode);
				 	var mawbNo  =  row.mawbNo;
					$(".FormElement[id*='mawbNo']").attr("readonly",true);
					style_edit_form(form);
				}

				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var selects = dialog.find('.group');
					selects.find('.operators').prop('style','display:none');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
				}

			
				function styleCheckbox(table) {
				/**
					$(table).find('input:checkbox').addClass('ace')
					.wrap('<label />')
					.after('<span class="lbl align-top" />')
			
			
					$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
					.find('input.cbox[type=checkbox]').addClass('ace')
					.wrap('<label />').after('<span class="lbl align-top" />');
				*/
				}
				
			
				//unlike navButtons icons, action icons in rows seem to be hard-coded
				//you can change them like this in here if you want
				function updateActionIcons(table) {
					/**
					var replacement = 
					{
						'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
						'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
						'ui-icon-disk' : 'ace-icon fa fa-check green',
						'ui-icon-cancel' : 'ace-icon fa fa-times red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					*/
				}
				
				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
						'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
						'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
						'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
				}

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


				
			
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
				$(document).one('ajaxloadstart.page', function(e) {
					$.jgrid.gridDestroy(grid_selector);
					$('.ui-jqdialog').remove();
				});
			});

			$("#mailBtn").on('click',function(e){
				var formData = $("#registForm").serialize();
				$("#registForm").attr('action','/mngr/order/sendMailTest');
				$("#registForm").attr('method','post');
				$("#registForm").submit()
			})

			$('#printCheck').on('click',function(e){
				var targets = new Array();

				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});
				if(targets == ""){
					alert("출력할 운송장이 없습니다.");
					return false;
				}
				window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val()+"&blType="+$("#blSelect").val(),"popForm","_blank");

			});


			$("#printTypeC").on('click', function(e) {
				var targets = new Array();
				
				 $("input[name='checkNno']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});

				 if(targets == ""){
					alert("출력할 운송장이 없습니다.");
					return false;
				}
				
				window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#transCode").val()+"&labelType=C","popForm","_blank");
			});

			$("#mawbApplyCheck").on('click', function(e) {
				
				var transCodeList = new Array();
				var targets = new Array();
				var hawbNoList = new Array();
				
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
						transCodeList.push($(row).find("input[name='transCodeList[]']").val());
						hawbNoList.push($(row).find("input[name='hawbList[]']").val());
					}
				});

				if (targets == "") {
					alert("적용할 운송장이 없습니다.");
					return false;
				}

				var uniqueCodeList = new Set(transCodeList);
				var uniqueCodeCnt = uniqueCodeList.size;

				var matchCode = "";

				if (uniqueCodeCnt > 1) {
					alert('하나의 배송사 코드만 선택해 주세요');
					return false;
				} else {
					matchCode = transCodeList[0];
				}

				if ($("#mawbNoInput").val() == "") {
					alert("MAWB NO를 입력해주세요.");
					$("#mawbNoInput").focus();
					return false;
				}


				loading.show();
				
				$.ajax({
					url : '/mngr/rls/checkMawbNo',
					type : 'POST',
					data : {mawbNo : $("#mawbNoInput").val()},
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(result) {
						if (result.STATUS == "FAIL") {
							alert(result.MSG);
							loading.hide();
							location.reload();
						} else {

							if (result.MSG != matchCode) {
								alert("선택한 송장의 배송사 코드와 입력한 Mawb No의 배송사 코드가 일치하지 않습니다.");
								loading.hide();
								location.reload();
							} else {
								callInsertMawbAjax($("#mawbNoInput").val(), targets, hawbNoList, result.MSG);
							}
						}
					},
					error : function(error) {
						alert("처리 중 오류 발생");
						loading.hide();
						location.reload();
					}
				});

			});

			function callInsertMawbAjax(mawbNo, targets, hawbNoList, transCode) {

				$.ajax({
					url : '/mngr/rls/mawbHawbListInsert',
					type : 'POST',
					cache: false,
					data : {nnoList: targets, mawbNo: mawbNo, hawbNoList: hawbNoList, transCode: transCode},
					traditional : true,
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
			    	success : function(result) {
			    		alert(result.MSG);
						loading.hide();
						location.reload();
				    },
				    error : function(error) {
					    alert("통신 실패");
						loading.hide();
						location.reload();
					}
				});

			}


			
		</script>
	</body>
</html>
