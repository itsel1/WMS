<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
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
		<title>Invoice collect</title>
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
								Invoice collect
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
					
						      <input type="hidden" name="orgStation" value="${etcInfoRow.orgStation}">
								<table class="table table-bordered" style="">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;">Invoice No</th>
										<td style="padding:0px;width:200px;">
											<input type ="text" readonly="readonly"  style="width:100%" value="${parameterInfo.invoiceNo}">
										</td>
										<th class="center colorBlack" style="width:100px;">Inv Month</th>
										<td style="padding:0px;width:200px;">
											<input type ="text"  readonly="readonly"  style="width:100%" value="${userInfo.INV_MONTH}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:100px;">User ID</th>
										<td style="padding:0px;width:200px;">
											<input type ="text"  readonly="readonly" style="width:100%" value="${userInfo.USER_ID}">
										</td>
										<th class="center colorBlack"  style="width:100px;">Company</th>
										<td style="padding:0px;width:200px;">
											<input type ="text" readonly="readonly" style="width:100%" value="${userInfo.COM_NAME}">
										</td>
									</tr>
										<tr>
										<th class="center colorBlack"  style="width:100px;">Tel</th>
										<td style="padding:0px;width:200px;">
											<input type ="text" readonly="readonly"  style="width:100%" value="${userInfo.USER_TEL}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:100px;">Address</th>
										<td style="padding:0px;width:200px;" colspan="3" >
											<input type ="text"  readonly="readonly"  style="width:100%" value="${userInfo.USER_ADDR}">
										</td>
									</tr>
									</thead>
								</table>
			
								<div style="width:100%;float:left">
									<table class="table table-bordered" style="max-width:45%;float:left">
									<thead>
										<tr>
											<th>청구 TYPE</th><th>Amount</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${invoiceDetailList}" var="invoiceDetailList" varStatus="status">
										<tr>
											<td>${invoiceDetailList.invTypeName}</td><td style="text-align: right">${invoiceDetailList.invAmt}</td>
										</tr>
									</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<td style="font-weight:bold;text-align:center">Total</td>
											<td style="font-weight:bold;text-align:right">${invoiceStatusList.invAmt}</td>
										</tr>
									</tfoot>
								</table>
								<table class="table table-bordered" style="max-width:45%;float:right;margin-left:15px;">
									<thead>
										<tr>
											<th>Received TYPE</th>
											<th>Received Amount</th>
											<th>Received Currency</th>
											<th>Exchange Rate</th>
											<th>Applied Amount</th>
											<th>Applied Currency</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${receivedDetailList}" var="receivedDetailList" varStatus="status">
										<tr>
											<td>${receivedDetailList.receivedName}</td>
											<td style="text-align: right">${receivedDetailList.receivedAmt}</td>
											<td style="text-align: right">${receivedDetailList.receivedCurrency}</td>
											<td style="text-align: right">${receivedDetailList.exchangeRate}</td>
											<td style="text-align: right">${receivedDetailList.invAmt}</td>
											<td style="text-align: right">${receivedDetailList.invCurrency}</td>
										</tr>
									</c:forEach>
									</tbody>
									<tfoot>
										<tr>
										    <td style="font-weight:bold;text-align:center">Total</td>
											<td style="font-weight:bold;text-align:right" colspan=5>${invoiceStatusList.receivedAmt}</td>
										</tr>
									</tfoot>
								</table>
								<form id="receivedDelForm" action="invoiceReceivedDel" method="get">
									<input id="idx" type="hidden" name="idx" value="">
									<input type ="hidden" name="invoiceNo" style="width:100%" value="${parameterInfo.invoiceNo}">
								 	<input type ="hidden" name="userId" style="width:100%" value="${parameterInfo.userId}">
								</form>
							</div>

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

				$("#userId").focus();

				$("#btn_close").on('click',function(e){

					var balanceAmt = $("#balanceAmt").val();

					if(balanceAmt > 0){
						alert('미 수금이 존재 합니다.');
						return false;
					}

					$("#invoiceClose").submit();

				});

				$("#btnInsert").on('click',function(e){

					var receivedAmt = $("#receivedAmt").val();
					var orgCurrency = $("#orgCurrency").val();
					var receivedCurrency = $("#receivedCurrency").val();
				    var balanceAmt = $("#balanceAmt").val();

					if(receivedAmt > 99999999999){
						alert('입력가능한 금액이 초과되었습니다.');
						return false;
					}

					if(receivedAmt == ""){
						alert('금액을 입력해주세요.');
						$("#receivedAmt").focus();
						return false;
					}

					if(orgCurrency!=receivedCurrency){
						if(exchangeRate==""){
							alert('환율을 입력해주세요.');
							$("#exchangeRate").focus();
							return false;
						}
					}else{
						$("#exchangeRate").val(1);
					}


					if($("#exchangeRate").val()==""){
						$("#exchangeRate").val(1); 
					}

					var exchangeRate = $("#exchangeRate").val();
					var chgAmt = receivedAmt * exchangeRate;

					if(balanceAmt < chgAmt){
						alert('수금금액이  초과되었습니다.');
						$("#receivedAmt").focus();
						return false;
					}
										
					$("#collectForm").submit();
				});

				$("#btnDelete").on('click',function(e){
					var result = confirm('정말 삭제 하시겠습니까?');

					if(!result) {
						return false;
					}
					
					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/mngr/invoice/popupEtcDel',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							var Status = data.rstStatus;

							if(Status == "SUCCESS"){
								alert(data.rstMsg);
								window.close();
								opener.parent.location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				});
				
			})

			function fn_delReceived(idx){
				var result = confirm('정말 삭제 하시겠습니까?');
				if(!result) {
					return false;
				}

				$("#idx").val(idx);
				$("#receivedDelForm").submit();

			}
			
			function fn_popupCode(){
				var _width = '480';
			    var _height = '380';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
				window.open("/mngr/invoice/popupReceivedCode","CODE",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}
		</script>
		<!-- script addon end -->
	  	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#collectDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yy-mm-dd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>
		

	</body>
</html>
