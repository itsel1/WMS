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
							<li class="active">택배회사 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								택배회사 관리
							</h1>
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
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
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
		<!-- ace scripts -->
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			var cellNum=1;
			var targetMawbNo;
			var grid_data = 
			[ 
			];
			var currentId = "";
			var grid_selector = "#grid-table";
			var pager_selector = "#pager";
		
			jQuery(function($) {
				$(document).ready(function() {
					$("#1stMenu").toggleClass('open');
					$("#1stMenu").toggleClass('active'); 
					$("#1stTwo").toggleClass('active');
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
			
			function customOption(targetCode){
				window.open("/mngr/unit/optionByTransCom/"+targetCode+"/DEFAULT","_blank","toolbar=yes,resizable=no,directories=no,width=1100px, height=980px");
			};
			
			jQuery(function($) {
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
					url :"/mngr/unit/transComLoad",
					mtype : "get",
					/* data: grid_data, */
					datatype: "json",
					height: 550,
					colNames:[' ','', '회사명','회사코드','영업사원명', '영업사원 TEL', '영업사원 HP','영업사원 Email','비고','MULTIBOX','유류할증료 Type', '유류할증료'],
					colModel:[
						{name:'myac',index:'ss', width:80, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delbutton: false,//disable delete button
								
								/* delOptions:{recreateForm: true, 
											beforeShowForm:beforeDeleteCallback,
											url: "/mngr/unit/transComDelete",
											datatype: 'json',
											delData: {
												transCode : function(){
													const selId = $(grid_selector).jqGrid('getGridParam', 'selrow');
						                            const divId = $(grid_selector).jqGrid('getCell', selId, 'legacyTransCode');
													return divId;
												}

											},
											afterSubmit : function(){
												jQuery(grid_selector).jqGrid("clearGridData");
												jQuery(grid_selector).jqGrid("setGridParam",
													{
														url:"/mngr/unit/transComLoad",
														datatype: "json"
													}).trigger("reloadGrid");
												$('#testarea').empty();
												return ["success"];
												}
											}, */
								editbutton:true,
								editformbutton:true, 
								editOptions:{recreateForm: true, 
											 closeAfterEdit: true, 
											 afterSubmit : function(response, postdata) {
												jQuery(grid_selector).jqGrid("clearGridData");
												jQuery(grid_selector).jqGrid("setGridParam",
													{
														url:"/mngr/unit/transComLoad",
														datatype: "json"
													}).trigger("reloadGrid");
												$('#testarea').empty();
												return ["success"];
											},
											 beforeShowForm:beforeEditCallback},
							}
						},
						{name:'legacyTransCode', index:'legacyTransCode', hidden:true, editable:true, sortable: false, sorttype:"int"},
						{name:'transName',index:'transName', width:150, editable:true, sortable: false, sorttype:"int" },
						{name:'transCode',index:'transCode', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'attenName',index:'attenName', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'attenTel',index:'attenTel', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'attenHp',index:'attenHp', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'attenEmail',index:'attenEmail', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'remark',index:'remark', width:130, editable:true, edittype:'textarea', editoptions:{size:"20",maxlength:"30"}},
						{name:'multiYn',index:'multiYn', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						{name:'chgType',index:'chgType', width:130, editable:true, edittype:"select", editoptions:{value:"wt:WT;per:%"}},
						{name:'chgFee',index:'chgFee', width:130, editable:true, editoptions:{size:"20",maxlength:"30"}},
						
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
						var transCode = row.transCode;
						if(iCol!=0){
							customOption(transCode);
						}
					},
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					editurl: "/mngr/unit/transComUpdate", 
					caption: "택배회사 목록"
			
				});

				$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
			
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
							.datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true}); 
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
						
						add: true,
						addicon : 'ace-icon fa fa-plus-circle purple',
						addtext : '택배회사 추가하기',
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
						addCaption: "택배회사 등록" ,
						url: "/mngr/unit/transComInsert",
						mtype:"POST",
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
							
							style_edit_form(form);
						},
						closeAfterAdd : true,
						afterSubmit : function(response, postdata) {
							jQuery(grid_selector).jqGrid("clearGridData");
							jQuery(grid_selector).jqGrid("setGridParam",
								{
									url:"/mngr/unit/transComLoad",
									datatype: "json"
								}).trigger("reloadGrid");
							return ["success"];
						}
					}
				)
			
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=depDate]').datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true})
					form.find('input[name=arrDate]').datepicker({format:'yyyy-mm-dd' , autoclose:true, todayHighlight:true})
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
					/* $('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'}); */
				}


				function pickType() {
					var result = '<select>';
					result += '<option value="wt">WT</option>';
					result += '<option value="per">%</option>';
					result += '</select>';
					return result;
			}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
				$(document).one('ajaxloadstart.page', function(e) {
					$.jgrid.gridDestroy(grid_selector);
					$('.ui-jqdialog').remove();
				});
			});
			
		</script>
		<!-- script addon end -->
		
	</body>
</html>
