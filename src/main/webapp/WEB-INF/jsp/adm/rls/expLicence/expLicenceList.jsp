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
	<title>수출신고대상목록</title>
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
							<li class="active">수출신고대상목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								수출신고대상목록
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
								<input type="hidden" name="readyCount" id="readyCount" value="${ftpCount.readyCount }">
								<input type="hidden" name="failCount" id="failCount" value="${ftpCount.failCount }">
							</div>
						</div>
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
			var test = 1;
			var arr; 
			var grid_data = 
			[ 
			];
			var currentId = "";
			var grid_selector = "#grid-table";
			var pager_selector = "#pager";
		
			jQuery(function($) {
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thEig").toggleClass('active');
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
				if($(".ui-jqgrid-headlink").hasClass("ui-icon-circle-triangle-s")){
					$('.ui-jqgrid-titlebar-close').trigger("click");
				}
				test = 1;
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
			
			function expLicenceShow(mawbNo){
				
				$('.ui-jqgrid-titlebar-close').trigger("click");
				
				
				$.ajax({
					url:'/mngr/rls/expLicenceListField',
					data:{target: mawbNo},
					type: 'GET',
					success : function(data) {
						$('#testarea').empty();
						$('#testarea').html(data);
						$('#target').focus();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
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
					url :"/mngr/rls/expLicenceLoad",
					mtype : "get",
					/* data: grid_data, */
					datatype: "json",
					height: 400,
					colNames:['', 'MawbNo','도착Station','운송사', '편명','출발일', '출발시간', '도착일','도착시간','성공/대상개수','[총개수 :'+$("#totCount").val()+' ][실패 :'+$("#failCount").val()+' ]'],
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
						{name:'dstnStation',index:'dstnStation', width:150, editable:true, sortable: false},

						{name:'transName',index:'transName', width:150, editable:true, sortable: false, edittype:"select", editoptions:{dataUrl:"/mngr/rls/transCodeSearch", buildSelect:pickNation}
						},
								
						{name:'fltNo',index:'fltNo',  editable:true,  sortable: false,
							editoptions:{
								size:"20",maxlength:"30"},
							searchoptions:{
								clearSearch:false}
						},
						{name:'depDate',index:'depDate', editable:true, sortable: false,unformat: pickDate,
							editoptions:{
								maxlength:10},
							searchoptions:{
								clearSearch:false,
								dataInit:function(elem){
									$(elem).datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true});
								}}
						},
						{name:'depTime',index:'depTime',  sortable: false, editable: true,
							editrules:{
								number:true},
							editoptions:{
								maxlength:4},
							searchoptions:{
								clearSearch:false
								}
						},
						{name:'arrDate',index:'arrDate', editable: true, sortable: false,unformat: pickDate,
							editoptions:{
								maxlength:10},
							searchoptions:{
								clearSearch:false,
								dataInit:function(elem){
									$(elem).datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true});
								}}
						},
						{name:'arrTime',index:'arrTime',  sortable: false, editable: true,
							editrules:{
								number:true},
							editoptions:{
								maxlength:4},
							searchoptions:{
								clearSearch:false}
						},
						{name:'licenceHawbCnt',index:'hawbCnt',  sortable: false, editable: false,
							searchoptions:{
								clearSearch:false}
						},
						{name:'myac',index:'ss',  fixed:true, sortable:false, resize:false, width:350,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								//delbutton: false,//disable delete button
								delbutton: false,
								editbutton: false,
							},
							search:false,
						},
						, 
					], 
			
					rowNum:10,
					pager : pager_selector,
					loadonce : true,
					//toppager: true,
					//multikey: "ctrlKey",
					altRows: true,
					reloadAfterSubmit : true,
					autowidth: true,
					loadComplete : function(data) {
						if(test == 1){
							arr = data;
							addStatus(arr,1);
							test++;
						}else{
							addStatus(arr,data.page);
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


				function addStatus(data,pageNo){
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
						if(data[i].licenceCnt != '0'){
							if(data[i].sendYn == '0'){
								$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<label  style="vertical-align:center;width:100px;" class="label label-success label-white middle" id="maniDown'+i+' name="maniDown">Send Success</label>')
							}
							else{
								$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<label  style="vertical-align:center;width:100px;" class="label label-danger label-white middle" id="maniDown'+i+' name="maniDown">Send Fail</label>')
							}
							//$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-primary label-white middle" onclick="javascript:ftpSend(\''+data[i].mawbNo+'\')" id="ftpSend'+i+' name="ftpSend">ftp 전송</button>')
						}else{
							$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<label  style="vertical-align:center;width:100px;" class="label label-info label-white middle" id="maniDown'+i+' name="maniDown">No Targets</label>')
						}
						$($(".jqgrow").get(i%10)).children().last().append('&nbsp;&nbsp;<button  style="vertical-align:center" class="label label-purple label-white middle" onclick="javascript:expLicenceShow(\''+data[i].mawbNo+'\')" id="showMawb'+i+' name="showMawb">상세보기</button>')
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
				
		</script>
		<!-- script addon end -->
		
	</body>
</html>
