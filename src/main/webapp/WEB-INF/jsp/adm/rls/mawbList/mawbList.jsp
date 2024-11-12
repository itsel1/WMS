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
	.ui-jqgrid .ui-jqgrid-bdiv {
		overflow-x: auto;
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
		<!-- 
		<script src="/testView/js/jquery-3.3.1.min.js"></script>
		<script src="/testView/js/jquery-ui.min.js"></script> -->
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
	<title>MAWB List</title>
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
							<li class="active">출발지 MAWB List</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								출발지 MAWB List
							</h1>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-2" style="padding:0px 0px 0px 15px!important">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-calendar bigger-110"></i>
									</span>
									<div class="input-group input-daterange">
										<input type="text" class="dateClass form-control" id="start" name="start" maxlength="10" tabIndex="1" value=""/>
											<span class="input-group-addon">
												to
											</span>
										<input type="text" class="dateClass form-control" id="end" name="end" maxlength="10" tabIndex="1" value=""/>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding">
								<div class="col-xs-12 col-sm-4 no-padding">
									<input class="center" type="text" style="width:100%" value="MawbNo" readOnly/>
								</div>
								<div class="col-xs-12 col-sm-8 no-padding">
									<input id="MawbNoScrh" class="center" type="text" style="width:100%" tabIndex="1"/>
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding">
								<div class="col-xs-12 col-sm-4 no-padding">
									<input class="center" type="text" style="width:100%" value="편명" readOnly/>
								</div>
								<div class="col-xs-12 col-sm-8 no-padding">
									<input id="fligntNameScrh" class="center" type="text" style="width:100%" tabIndex="1"/>
								</div>
							</div>
							<div class="col-xs-12 col-sm-1 no-padding">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<button type="button" id="scrhBtn" class="btn btn btn-primary no-border btn-sm">검색</button>
							</div>
							<div class="col-xs-12 col-sm-5" style="text-align: right; padding-top: 10px;">
								<!-- <button type="button" class="btn btn-small btn-success" name="allRegist" id="allRegist">ftp 일괄 전송</button> -->
								<input type="hidden" name="regCount" id="regCount" value="${ftpCount.registCount }">
								<input type="hidden" name="totCount" id="totCount" value="${ftpCount.totalCount }">
							</div>
						</div>
						<iframe id="downloadFrame" style="display: none;"></iframe>
						<div class="row">
							<div class="col-xs-12 col-sm-12" id="inner-content-side">
								
								<br>
								<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
								<table id="grid-table"></table>
	
								<div id="pager"></div>
								<br/>
								<div id="testarea">
								
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
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<!-- script on paging start -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		
		<!-- script on paging end -->
		
		<!-- script datepicker -->
		<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<!-- script addon start -->
		<script type="text/javascript">
			var loading = "";
			var wnd;
			var test = 1;
			var arr; 
			var grid_data = 
			[ 
			];
			var currentId = "";
			var grid_selector = "#grid-table";
			var pager_selector = "#pager";
		
			jQuery(function($) {
				loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
				$(document).ready(function() {
					
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thFor").toggleClass('active');
					$('.ui-jqgrid-titlebar-close').css("width","100%");
					/* loadMawb();
					jQuery(grid_selector).trigger("reloadGrid"); */
					$.ajaxSetup({
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    	xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				    	}
					});
					
				});
			})
			
			$("#scrhBtn").on('click ', function(){
				settingDate();
				if($(".ui-jqgrid-headlink").hasClass("ui-icon-circle-triangle-s")){
					$('.ui-jqgrid-titlebar-close').trigger("click");
				}
				test = 1;
				$(grid_selector).clearGridData();
				//alert($("#start").val());
				//alert($("#end").val());
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
			
			function mawbShow(mawbNo){
				
				$('.ui-jqgrid-titlebar-close').trigger("click");
				
				
				$.ajax({
					url:'/mngr/rls/mawbListField',
					data:{target: mawbNo},
					type: 'GET',
					success : function(data) {
						$('#testarea').empty();
						$('#testarea').html(data);
						$('#target').focus();
		            }, 		    
		            error : function(request, status, error) {
			            alert("데이터 조회에 실패 하였습니다.");
			            console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
		            }
				})
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
					url :"/mngr/rls/mawbLoad2",
					mtype : "get",
					/* data: grid_data, */
					datatype: "json",
					height: 400,
					colNames:['', 'MawbNo','도착Station','운송사','운송사코드', '편명','출발일', '출발시간', '도착일','도착시간','운송장 개수','총 실무게', '총 부피무게', 'ftp 전송 현황:'+$("#regCount").val()+'/'+$("#totCount").val()],
					colModel:[
						
						{name:'legacyMawbNo', index:'legacyMawbNo', hidden:true, editable:true, sortable: false, sorttype:"int"},
						{name:'mawbNo',index:'mawbNo',  editable:true, sortable: false, sorttype:"int",
							searchoptions:{
								clearSearch:false} },
								
						/* {name:'dstnStation',index:'dstnStation', width:150, editable:true, sortable: false, edittype:"select", 
									editoptions:{
										dataUrl:"/mngr/rls/nationSearch", 
										buildSelect:pickNation
									}  
						} */
						{name:'dstnStation',index:'dstnStation', width:100, editable:true, sortable: false},

						/* {name:'transName',index:'transName', width:150, editable:true, sortable: false, edittype:"select", editoptions:{dataUrl:"/mngr/rls/transCodeSearch", buildSelect:pickNation}
						}, */
						{name:'transName',index:'transName', width:150, editable:true, disalbed : true, sortable: false},

						{name:'transCode',index:'transCode', hidden:true, editable:true, sortable: false},
								
						{name:'fltNo',index:'fltNo', width:100,  editable:true,  sortable: false,
							editoptions:{
								size:"20",maxlength:"30"},
							searchoptions:{
								clearSearch:false}
						},
						{name:'depDate',index:'depDate', width:100, editable:true, sortable: false,unformat: pickDate,
							editoptions:{
								maxlength:10},
							searchoptions:{
								clearSearch:false,
								dataInit:function(elem){
									$(elem).datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true});
								}}
						},
						{name:'depTime',index:'depTime', width:100,  sortable: false, editable: true,
							editrules:{
								number:true},
							editoptions:{
								maxlength:4},
							searchoptions:{
								clearSearch:false
								}
						},
						{name:'arrDate',index:'arrDate', width:100, editable: true, sortable: false,unformat: pickDate,
							editoptions:{
								maxlength:10},
							searchoptions:{
								clearSearch:false,
								dataInit:function(elem){
									$(elem).datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true});
								}}
						},
						{name:'arrTime',index:'arrTime', width:100,  sortable: false, editable: true,
							editrules:{
								number:true},
							editoptions:{
								maxlength:4},
							searchoptions:{
								clearSearch:false}
						},
						{name:'hawbCnt',index:'hawbCnt', width:100,  sortable: false, editable: false,
							searchoptions:{
								clearSearch:false}
						},
						{name:'totalWta',index:'totalWta', width:100,  sortable: false, editable: false,
							searchoptions:{
								clearSearch:false}
						},
						{name:'totalWtc',index:'totalWtc', width:100,  sortable: false, editable: false,
							searchoptions:{
								clearSearch:false}
						},
						{name:'myac',index:'ss',  fixed:true, sortable:false, resize:true, width:530,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								//delbutton: false,//disable delete button
								
								delOptions:{
											recreateForm: true, 
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
											beforeSubmit : function(postdata, formid){
												var selectCode = $(grid_selector).jqGrid('getGridParam', "selrow");
												var row = $(grid_selector).getRowData(selectCode);
											 	var hawbCnt  =  row.hawbCnt;

												
												if(hawbCnt > 0){
													alert("삭제 할 수 없습니다. 남아있는 Hawb가 있습니다.")
													return false;
												}
												return ["success"];
											 },
											afterSubmit : function(response, postdata){
												jQuery(grid_selector).jqGrid("clearGridData");
												jQuery(grid_selector).jqGrid("setGridParam",
													{
														url:"/mngr/rls/mawbLoad2",
														datatype: "json"
													}).trigger("reloadGrid");
													if(response.statusText == "success"){
														alert("삭제 되었습니다.");
													}else{
														alert("삭제에 실패 하였습니다.");
													}
													return [response.statusText];
												}
											},
								editformbutton:true, 
								editOptions:{
											 recreateForm: true, 
											 closeAfterEdit: true, 
											 beforeSubmit : function(postdata, formid){
													if(postdata.mawbNo ==""){
														alert("MawbNo를 입력해 주세요")
														return false;
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
													if(response.statusText == "success"){
														alert("수정 되었습니다.");
													}else{
														alert("수정에 실패 하였습니다.");
													}
													
												return [response.statusText];
											},
											beforeShowForm:beforeEditCallback
								},
							},
							search:false,
						},
						, 
					], 
			
					rowNum:10,
					pager : pager_selector,
					loadonce : true,
					shrinkToFit : false,
					forceFit:true,
					//toppager: true,
					//multikey: "ctrlKey",
					altRows: true,
					reloadAfterSubmit : true,
					beforeRequest: function() {
						settingDate();
					},
					autowidth: true,
					loadComplete : function(data) {
						if(test == 1){
							arr = data;
							testemp(arr,1);
							test++;
						}else{
							testemp(arr,data.page);
						}
					},
					/* onCellSelect : function(rowid,iCol,cellcontent,e){
						var row = jQuery(grid_selector).getRowData(rowid);
						targetMawbNo = row.mawbNo;
						if(iCol!=10){
							mawbShow(targetMawbNo);
						}
					}, */
					/* onSelectRow: function(){
						var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
						var row = jQuery(grid_selector).getRowData(selr);
						mawbShow(row.mawbNo);
					}, */
					editurl: "/mngr/rls/mawbUpdate",//nothing is saved 
					caption: "MAWB 목록"
			
				});
				/* jQuery(grid_selector).jqGrid('filterToolbar',{
					searchOnEnter : true,
					stringResult: true, 
				}); */


				function testemp(data,pageNo){
					var max = 0;
					$("#nodata").remove();
					if(data.length==0){
						$(".ui-jqgrid-btable").after("<p id='nodata' style='margin-top: 5px; text-align: center; font-weight: bold;'>검색된 자료가 없습니다</p>");
					}
					if(data.length < (10*pageNo)){
						max = data.length;
					}else{
						max = (10*pageNo);
					}
					for(var i = (10*(pageNo-1)); i < max; i++){
						/* if(data[i].transCode == 'ARA'){
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-success label-white middle" onclick="javascript:maniFastDownARA(\''+data[i].mawbNo+'\',\''+data[i].hawbCnt+'\')" id="maniDown'+i+' name="maniDown">적하신고</button>')
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-primary label-white middle" onclick="javascript:ftpSend(\''+data[i].mawbNo+'\')" id="ftpSend'+i+' name="ftpSend">ftp 전송</button>')
						}
						else if(data[i].transCode == 'YES'){
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-success label-white middle" onclick="javascript:maniFastDownARA(\''+data[i].mawbNo+'\',\''+data[i].hawbCnt+'\')" id="maniDown'+i+' name="maniDown">적하신고</button>')
						}
						else if (data[i].transCode == 'ACI'){
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-success label-white middle" onclick="javascript:maniFastDownACI(\''+data[i].mawbNo+'\',\''+data[i].hawbCnt+'\')" id="maniDown'+i+' name="maniDown">적하신고</button>')
						} */
						if(data[i].orgStation == '082' || data[i].orgStation == '441'){
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center; cursor:pointer;" class="label label-success label-white middle" onclick="javascript:maniFastDownARA(\''+data[i].mawbNo+'\',\''+data[i].hawbCnt+'\')" id="maniDown'+i+' name="maniDown">적하신고</button>')
						}
						if(data[i].transCode.includes('CSE') || data[i].transCode.includes('GTS') ){
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center; cursor:pointer;" class="label label-success label-white middle" onclick="javascript:rusExcelDown(\''+data[i].mawbNo+'\',\''+data[i].hawbCnt+'\')" id="maniDown'+i+' name="maniDown">Excel Down</button>')
						}
						$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button style="vertical-align:center; cursor:pointer;" class="label label-danger label-white middle" onclick="javascript:mawbShow(\''+data[i].mawbNo+'\')" id="showMawb'+i+' name="showMawb">상세보기</button>')
						$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button style="vertical-align:center; cursor:pointer;" class="label label-warning label-white middle" onclick="javascript:maniShow(\''+data[i].mawbNo+'\')" id="maniMawb'+i+' name="maniMawb">MawbList</button>')
						
						if(data[i].orgStation == '213') {
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button style="vertical-align:center; cursor:pointer;" class="label label-notice label-white middle" onclick="javascript:maniDown(\''+data[i].mawbNo+'\')" id="maniDown'+i+' name="maniMawb">Manifest</button>')
						}
						if(data[i].transCode == 'FB' || data[i].transCode == 'FB-EMS') {
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button style="vertical-align:center; cursor:pointer;" class="label label-notice label-white middle" onclick="javascript:reqFbDlv(\''+data[i].mawbNo+'\')">배송지시</button>')
						}
					}

						
					
					var table = this;
					setTimeout(function(){
						styleCheckbox(table);
						
						updateActionIcons(table);
						updatePagerIcons(table);
						enableTooltips(table);
					}, 0);

				}

				
				$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
							.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}

				function inputTime(cellvalue, options, cell ) {
					setTimeout(function(){
						alert($(cell).find('input[type=text]').val()); 
					}, 0);
				}
			
			
				//navButtons
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						
						add: false,
						addicon : 'ace-icon fa fa-plus-circle purple',
						addtext : 'MAWB 추가하기',
						edit: false,
						del: false,
						search: false,
						searchicon : 'ace-icon fa fa-search orange',
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
					},
					{
						//delete record form
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							//alert(1);
						}
					},
				)
				
				$(".jqgrow").attr("checked",true);
				
			
			
			
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
					form.find('input[name=edate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
					form.find('input[name=transName]').attr("readonly",true);
					form.find('input[name=transName]').on('click',function(e){
						if(wnd){
							wnd.close();
						}
						wnd = window.open("mawbTransCom","_blank","toolbar=yes, resizable=no,directories=no,width=480,height=600");
					})
					
					form.find('input[type=text]').width('60%');
					
							
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
			
			
			
				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
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
			
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
				$(document).one('ajaxloadstart.page', function(e) {
					$.jgrid.gridDestroy(grid_selector);
					$('.ui-jqdialog').remove();
				});
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
				format:'yyyymmdd'
			});

			//enable datepicker
			function pickNation( data ) {
					var result = '<select>';
					for(var idx=0; idx < jQuery.parseJSON(data).length; idx++) {
						result += '<option value="' + jQuery.parseJSON(data)[idx].transCode + '">' + jQuery.parseJSON(data)[idx].transName + '</option>';
					}
					result += '</select>';
					return result;
			}

			$('#returnHawb').on('click', function(){
				$('input[id*="check"]:checked').each(function() {
					alert($(this).val());
				});
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

			function maniFastDownACI(mawbNo,hawbCnt){
				if(hawbCnt == 0){
					alert("운송장이 없습니다.")
				}else{
					window.open("/mngr/rls/mawbManiDown?targets="+mawbNo);
				}
				
			}
			function maniFastDownARA(mawbNo,hawbCnt){
				if(hawbCnt == 0){
					alert("운송장이 없습니다.")
				}else{
					window.open("/mngr/Excel/UediDown?mawbNo="+mawbNo);
				}
				
			}
			function rusExcelDown(mawbNo,hawbCnt){
				if(hawbCnt == 0){
					alert("운송장이 없습니다.")
				}else{
					window.open("/mngr/Excel/rusExcelDown?mawbNo="+mawbNo);
				}
				
			}

			function ftpSend(mawbNo){
				$.ajax({
					url:'/mngr/rls/ftpSend',
					data:{target: mawbNo},
					type: 'POST',
					success : function(data) {
						alert(data);
							
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("ftp 등록에 실패 하였습니다. - aJax 오류");
		            }
				})
			}

			$("#allRegist").on('click',function(){
				$.ajax({
					url:'/mngr/rls/ftpSendAll',
					type: 'POST',
					success : function(data) {
						alert(data);
							
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				})
			})
			
			
			// 배송지시
			function reqFbDlv(mawbNo) {
				loading.show();
				$.ajax({
					url : '/mngr/rls/requestFastboxDevliery',
					type : 'POST',
					data : {mawbNo: mawbNo},
					success : function(data) {
						alert(data.msg);
						loading.hide();
					},
					error : function(error) {
						alert("오류가 발생 하였습니다. 다시 시도해 주세요.");
						loading.hide();
					}
				})
			}
			
			function settingDate(){
				if($("#start").val() == ""){
					var startOfMonth = moment().startOf('month').format('YYYYMMDD');
					var endOfMonth = moment().endOf('month').format('YYYYMMDD');
					$("#start").val(startOfMonth);
					$("#end").val(endOfMonth);
				}else if($("#end").val() == ""){
					var startOfMonth = moment().startOf('month').format('YYYYMMDD');
					var endOfMonth = moment().endOf('month').format('YYYYMMDD');
					$("#start").val(startOfMonth);
					$("#end").val(endOfMonth);
				}
			}

			function maniShow(mawbNo) {
				window.open("/mngr/rls/mawbManiExcelDown?targets="+mawbNo);
			}

			function maniDown(mawbNo) {

				//alert("기능 준비 중입니다.");
				//return;
				
				var iframe = document.getElementById('downloadFrame');
				iframe.src = '/mngr/rls/downMawbNoManifest?mawbNo='+mawbNo;

				var checkInterval = setInterval(function() {
					if (iframe.contentDocument && iframe.contentDocument.readyState == 'complete' 
						&& iframe.contentDocument.documentElement && iframe.contentDocument.documentElement.outerHTML.length > 0) {
						//loading.hide();
						clearInterval(checkInterval);
					}
				},800);
				/*
				$.ajax({
					url : '/mngr/rls/downMawbNoManifest?mawbNo='+mawbNo,
					type : 'GET',
					xhrFields : {
						responseType : 'blob'
					},
					success : function(response) {
						var blob = new Blob([response], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
						var link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = mawbNo + '.xlsx';
						link.click();
					},
					error : function(xhr, status) {
						
					}
				});*/
			}
				
		</script>
		<!-- script addon end -->
		
	</body>
</html>
