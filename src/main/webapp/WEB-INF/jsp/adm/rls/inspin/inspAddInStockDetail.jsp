<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
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
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								추가 입고 재고
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
						 <form id="mgrForm" action="inspAddInGroupStockModity" method="POST">
						 		<input type="hidden" name="groupIdx" value="${groupStockinfo.groupIdx}">
						 		<input type="hidden" name="orgStation" value="${groupStockinfo.orgStation}">
						 		<input type="hidden" name="orgStation" value="${groupStockinfo.userId}">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:125px;">
										<span style="padding:0;color:red">*</span> UserId</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" id="userId" style="width:100%" type="text" readOnly value="${groupStockinfo.userId}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;" >Brand</th>
										<td style="padding:0px;">
											<input name="brand" style="width:100%" type="text" readOnly value="${groupStockinfo.brand}">
										</td>
										<th class="center colorBlack" style="width:100px;  "  >Hs_code</th>
										<td style="padding:0px;">
											<input name="hsCode" class="onlyEN" style="width:100%" type="text" readOnly  value="${groupStockinfo.hsCode}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack">상품명(영문)</th>
										<td style="padding:0px;" colspan=3>
											<input id="itemDetail" name="itemDetail" class="onlyEN" style="width:100%" type="text" readOnly value="${groupStockinfo.itemDetail}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">입고 일시</th>
										<td style="padding:0px;">
											<input style="width:100%" id="whInDate" name="whInDate" type="text" readonly value="${groupStockinfo.whInDate}">
										</td>
										<th class="center colorBlack" style="width:100px;">Rack</th>
										<td style="padding:0px;">
											<input style="width:100%" name="rackCode" type="text" value="${groupStockinfo.rackCode}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">Remark</th>
										<td style="padding:0px;" colspan="3">
											<input style="width:100%" id="remark" name="remark" type="text" value="${groupStockinfo.remark}">
										</td>
									</tr>
									
									<tr>
										<td colspan ="4" style="text-align:right">
											<input type=button id="btnUpdate" value="수정">						
										</td>
									</tr>
									</thead>
								</table>
							</form>
							
							<form id="subForm" action="" method="get">
							    <input type="hidden" name="groupIdx" value="${groupStockinfo.groupIdx}">
						 		<input type="hidden" name="orgStation" value="${groupStockinfo.orgStation}">
						 		<input type="hidden" name="userId" value="${groupStockinfo.userId}">
								<table class="table table-bordered" style="width:350px;">
								  <thead>
								  		<tr>
										<th class="center colorBlack" style="width:100px;">재고 상태</th>
										<td style="padding:0px;">
											<select id="whStatus" name="whStatus" style="width:100%">
												<option value=""
												<c:if test="${parameterInfo.whStatus == ''}">selected</c:if>
												>::전체::</option>
												<option value="WO"
												<c:if test="${parameterInfo.whStatus == 'WO'}">selected</c:if>
												>정상</option>
												<option value="WF"
												<c:if test="${parameterInfo.whStatus == 'WF'}">selected</c:if>
												>검수불합격</option>
											</select>
										</td>
									</tr>
								  </thead>
							    </table>
							 </form>
							 
							 <form id="addForm" action="" method="get">
							 	<table style="width:100%;">
							 		<tr>
							 			<td>
							 				<input id="btnPrintStock" type="button" value="라벨출력">
							 			</td>
							 			<td style="text-align: right;">
							 				<input id="btnAddStock" type="button" value="추가"/>
							 			</td>
							 		</tr>
							 	</table>
							 </form>
							<br/>
							<form id="tableForm" name="tableForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<table id="dynamic-table" class="table table-bordered">
									<thead>
										<tr>
											<th style="width:80px;text-align: center;">
												<label class="pos-rel">
														<input type="checkbox" class="ace" /> <span class="lbl"></span>
												</label>
											</th>
											<th class="center colorBlack" Style="width:280px;">
												재고번호
											</th>
											<th class="center colorBlack" Style="width:180px;">
												재고상태
											</th>
											<th class="center colorBlack">
												BL
											</th>
											<th>
											
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${infoList}" var="infoList" varStatus="status">
										<tr>
											<td class="center colorBlack">
												<label class="pos-rel">
													<input type="checkbox" id="stockNo" name="stockNo" class="ace" value="${infoList.stockNo}"/>
													<span class="lbl"></span>
												</label>
											</td>
											<td class="center colorBlack">
												${infoList.stockNo}
											</td>
											<td class="center colorBlack">
												${infoList.whStatusName}	
											</td>
											<td class="center colorBlack" style="text-algn:center">
												${infoList.BL}	
											</td>
											<td style="width:80px;text-align: center;">
												<input type="button" value="삭제" onclick="fn_delStock('${infoList.stockNo}')">
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
		jQuery(function($) {
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
			
			$(".onlyEN").keyup(function(event){
				if (!(event.keyCode >=37 && event.keyCode<=40)) {
					var inputVal = $(this).val();
					$(this).val(inputVal.replace(/[^a-z0-9 ]/gi,''));
				} 
		    });


		    $("#btnAddStock").on('click',function(e){
		    	if($("#whStatus").val()==""){
					alert('재고 상태를 선택 후 추가해주세요.');
					return false;
		    	}
			    alert('test');
			});

			$("#btnPrintStock").on('click',function(e){
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});
				var targetForm = document.tableForm;
				window.open("","stockPrint","toolbar=yes","resizable=no","directories=no","width=480, height=360")

				targetForm.action = "/mngr/order/pdfInspAddPopup";
				targetForm.method="post";
				targetForm.target="stockPrint";
				targetForm.submit();
			 })

			$("#btnUpdate").on('click',function(e){

				var data = $("#mgrForm").serialize();

				$.ajax({
					url:'/mngr/inspin/inspAddInGroupStockModity',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: data, 
					success : function(data) {
						var Status = data.rstStatus;
						if(Status == "SUCCESS"){
							alert( data.rstMsg);
							opener.document.location.reload();
							//self.close();
						}else{
							alert("등록에 실패했습니다.");
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
				
				
			});


			$("#whStatus").change(function(){
				$("#subForm").submit();
			});

		})
		
		function fn_delStock(stockNo){
			alert(stockNo);
		}
			
		</script>
		<!-- script addon end -->
	  	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#whInDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>

	</body>
</html>
