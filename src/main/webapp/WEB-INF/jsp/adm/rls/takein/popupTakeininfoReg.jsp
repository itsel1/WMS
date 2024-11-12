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
								재고 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="" method="get">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;">UserID</th>
										<td style="padding:0px;width:200px;">
											<input id="userId" name="userId" class="onlyEN" readonly="readonly" style="width:100%" type="text" value="${takeinInfo.userId}">
										</td>
										<th class="center colorBlack"   style="width:125px;">TAKEIN_CODE</th>
										<td style="padding:0px;width:200px;">
											<input id="takeInCode" name="takeInCode" readonly="readonly" style="width:100%" type="text" value="${parameterInfo.takeInCode}">
										</td>
									</tr>
									<tr>
										<th class="center" >CusItemCode</th>
										<td style="padding:0px;">
											<input type="text" style="width:100%" value="${ takeinInfo.cusItemCode}" readonly="readonly">
										</td>
										<th class="center" >HsCode</th>
										<td style="padding:0px;">
											<input type="text" style="width:100%" value="${ takeinInfo.hsCode}" readonly="readonly">
										</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:100px;">상품명</th>
										<td style="padding:0px;width:200px;" colspan=3>
											<input type="text" readonly="readonly" style="width:100%" value="${takeinInfo.itemDetail}">
										</td>
									</tr>
									<tr>
									<th class="center" ><span style="color:red;">* </span>Rack</th>
									<td style="padding:0px;width:200px;">
										<input type="text" id="rackCode" name="rackCode" value="" style="width:100%">
									</td>
									<th class="center colorBlack" style="width:125px;"><span style="color:red;">* </span>수량</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="Number" id="whInCtn" name="whInCtn" style="width:100%" value="">
										</td>
									</tr>
																		<tr>
									<th class="center colorBlack" style="width:100px;"><span style="color:red;">* </span>검수일자</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" id="whInDate" name="whInDate" style="width:100%" value="" readonly="readonly"/>
										</td>
										<th class="center colorBlack" style="width:125px;">검수자</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" name="inSpector" style="width:100%" value=""/>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:125px;">INVNO</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" name="cusInvNo" style="width:100%" value=""/>
										</td>
										<th class="center colorBlack" style="width:125px;">SUPPLIER</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" name="cusSupplier" style="width:100%" value=""/>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:125px;">SUPPLIER_ADDR</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" name="cusSupplierAddr" style="width:100%" value=""/>
										</td>
										<th class="center colorBlack" style="width:125px;">SUPPLIER_TEL</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" name="cusSupplierTel" style="width:100%" value=""/>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:125px;">입고 종류</th>
										<td style="padding:0px;width:200px;vertical-align: middle" colspan=3>
											<label>
												<input type="radio" name="whInType" value="NOMAL" checked="checked"> 일반입고
											</label>
											<label style="margin-left: 10px;">
												<input type="radio" name="whInType" value="RETURNIN"> 반품입고
											</label>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:125px;">유통기한</th>
										<td style="padding:0px;width:200px;vertical-align:middle;" colspan="3">
											<input type="text" name="mnDate" id="mnDate" value="" placeholder="YYYYMMDD">
										</td>
									</tr>
									<!-- <tr>
										<th class="center colorBlack" style="width:125px;">파렛트 비용</th>
										<td style="padding:0px;width:200px;vertical-align: middle" colspan=3>
											<label>
												<input type="radio" name="palletWorkYn" value="N" checked="checked"> 미사용
											</label>
											<label style="margin-left: 10px;">
												<input type="radio" name="palletWorkYn" value="Y"> 사용
											</label>
										</td>
									</tr> -->
									</thead>
									<tbody>
										<tr>
											<td colspan=4 style="text-align:right">
												<input type="button" id="btnInsert" value="등록" />
											</td>
										</tr>
									</tbody>
								</table>
								<br>
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
		
		<!-- script addon start -->
			<script type="text/javascript">
			jQuery(function($) {

				$(".onlyEN").keyup(function(event){ 
					 if (!(event.keyCode >=37 && event.keyCode<=40)) {
					    var inputVal = $(this).val();
					    $(this).val(inputVal.replace(/[^a-z0-9 ]/gi,''));
					   } 
			     });


				$("#rackCode").focus();

				$("#btnInsert").on('click',function(e){


	 				if( $("#rackCode").val() == ""){
						alert('Rack을 입력해주세요');
						$("#rackCode").focus();
						return false;
					}

	 				if( $("#whInCtn").val() == ""){
						alert('수량을 입력해주세요');
						$("#whInCtn").focus();
						return false;
					}

	 				if( $("#whInDate").val() == ""){
						alert('검수일자를 입력해주세요');
						$("#whInDate").focus();
						return false;
					}
	 				
	 				var mnDate = $('#mnDate').val();
	 				
	 				if (mnDate != "") {
		 				if (mnDate.length != 8) {
		 					alert("제조일자는 YYYYMMDD 형식으로 입력해주세요.");
		 					return false;
		 				}
		 			}


					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/mngr/takein/takeinStockInReg',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							alert(data.rstMsg);
							//alert(data.rstGroupIdx);
							var Status = data.rstStatus;
		
							if(Status == "SUCCSESS"){
								opener.parent.location.reload();
								var url = "/mngr/takein/pdfTakeInPopup?groupIdx="+data.rstGroupIdx;
								$(location).attr('href',url);

							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				});
				
				$('#mnDate').keyup(function(e){
					$(this).val($(this).val().replace(/[^0-9]/gi, ""));
				});
				
			})
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
