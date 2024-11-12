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
	
	select option[value=""][disabled] {
		display: none;
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
										<th class="center colorBlack"  style="width:100px; hegiht:36px;">Invoice No</th>
										<td style="padding:0px;width:200px; hegiht:36px;">
											<select id="invoiceNoSelect" required name="invoiceNoSelect" style="width:100%; hegiht:100%;">
												<option value="" disabled selected>인보이스를 선택해주세요</option>
												<c:forEach items="${list}" var="list" varStatus="status">
													<option value="${list.INVOICE_NO}">
														${list.INVOICE_NO}
													</option>
												</c:forEach>
											</select> 
										</td>
										<th class="center colorBlack" style="width:100px;">Inv Month</th>
										<td style="padding:0px;width:200px;">
											<input type ="text"  readonly="readonly" id="invMonth" style="width:100%" value="${userInfo.INV_MONTH}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:100px;">User ID</th>
										<td style="padding:0px;width:200px;">
											<input type ="text"  readonly="readonly" id="userId" name="userId" style="width:100%" value="${userInfo.USER_ID}">
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
			
								<div id="invoiceDetail" style="width:100%;float:left">
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

				$("#invoiceNoSelect").change(function() {

					var today = new Date();
					var year = today.getFullYear();
					var month = ('0' + (today.getMonth() + 1)).slice(-2);
					var day = ('0' + today.getDate()).slice(-2);

					var date = year + month + day;

					if ($("#invoiceNoSelect").val() != "") { 
					
						var invoice_no = $("#invoiceNoSelect").val();
						var user_id = $("#userId").val();
						$.ajax({
							url : '/mngr/invoice/popupInvoiceInfo',
							type : 'POST',
							beforeSend : function(xhr) {
								xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data : {invoiceNo: invoice_no, userId: user_id},
							success : function(data) {
								console.log(data.receivedDetailList);
								$("#invMonth").val(data.invoiceDetail[0].invMonth);
	
								var html = "";
								html += '<button type="button" id="codeIn" class="btn btn-white btn-inverse btn-xs">코드 등록</button>';
								html += '<form id="collectForm">';
								html += '<input type="hidden" name="invoiceNo" value="'+data.parameterInfo.invoiceNo+'">';
								html += '<input type="hidden" name="userId" value="'+data.parameterInfo.userId+'">';
								html += '<input type="hidden" id="orgCurrency" name="orgCurrency" value="'+data.invoiceStatusList.orgCurrency+'">';
								html += '<input type="hidden" id="balanceAmt" name="balanceAmt" value="'+data.invoiceStatusList.balanceAmt+'">';
								html += '<table class="table table-bordered" style="width:100%; margin-top:5px;"><thead>';
								html += '<tr><th class="center" style="width:100px;">일시</th>';
								html += '<td style="padding:0px; width:180px;">';
								html += '<input type="text" style="width:100%; hegiht:33px;" id="receivedDate" name="receivedDate" readonly value="'+date+'"/>';
								html += '<th class="center" style="width:100px;">구분</th>';
								html += '<td style="width:100px; padding:0px;"><select id="receivedCode" name="receivedCode" style="width:100%; hegiht:33px;">';
								for (var i = 0; i < data.receivedCodeList.length; i++) {
									html += '<option selected value="'+data.receivedCodeList[i].RECEIVED_CODE+'">'+data.receivedCodeList[i].RECEIVED_NAME+'</option>';
								}
								html += '</select></td>';
								html += '<th class="center" style="width:100px;">금액</th>';
								html += '<td style="padding:0px; width:200px;">';
								html += '<input style="width:100%; height:33px;" type="number" id="receivedAmt" name="receivedAmt" vale=""/></td>';
								html += '<th class="center" style="width:100px;">환율</th>';
								html += '<td style="padding:0px; width:80px;">';
								html += '<input type="number" style="width:100%; height:33px;" id="exchangeRate" name="exchangeRate" value=""></td>';
								html += '<th class="center" style="width:100px;">화폐단위</th>';
								html += '<td style="padding:0px; width:80px;"><select id="receivedCurrency" name="receivedCurrency" style="width:100%; height:33px">';
								for (var j = 0; j < data.currencyList.length; j++) {
									html += '<option selected value="'+data.currencyList[j].code+'">'+data.currencyList[j].name+'</option>';
								}
								html += '</select></td>';
								html += '<th style="padding:0px; vertical-align:middle; width:100px; text-align:center;">';
								html += '<input type="button" id="btnInsert" value="등록"></td>';
								html += '</tr></thead></table></form>';

								html += '<form id="receivedDelForm">';
								html += '<input type="hidden" id="invoiceNo" name="invoiceNo" value="'+data.parameterInfo.invoiceNo+'">';
								html += '<input type="hidden" id="userId" name="userId" value="'+data.parameterInfo.userId+'">';
								html += '<table class="table table-bordered" style="max-width:28%; float:left;"><thead>';
								html += '<tr><th class="center">청구 TYPE</th><th class="center">Amount</th></thead>';
								html += '<tbody>';
								for (var a = 0; a < data.invoiceDetail.length; a++) {
									html += '<tr><td>'+data.invoiceDetail[a].invTypeName+'</td><td style="text-align:right;">'+data.invoiceDetail[a].invAmt.toLocaleString('ko-KR')+'</td></tr>';
								}
								html += '</tbody><tfoot><tr><td style="font-weight:bold; text-align:center;">Total</td>';
								html += '<td style="font-weight:bold; text-align:right;">'+data.invoiceStatusList.invAmt.toLocaleString('ko-KR')+'</td></tr></tfoot></table>';
	
								html += '<table class="table table-bordered" style="max-width:70%; float:right;">';
								html += '<thead><tr><th style="display:none;"></th><th>Received TYPE</th><th>Received Amount</th><th>Received Currency</th>';
								html += '<th>Exchange Rate</th><th>Applied Amount</th><th>Applied Currency</th><th></th></tr>';
								html += '</thead><tbody>';
								for (var z = 0; z < data.receivedDetailList.length; z++) {
									html += '<tr><td style="display:none;"><input type="hidden" class="idx" name="idx" value="'+data.receivedDetailList[z].idx+'"></td>';
									html += '<td style="text-align:center;">'+data.receivedDetailList[z].receivedName+'</td>';
									html += '<td style="text-align:right;">'+data.receivedDetailList[z].receivedAmt.toLocaleString('ko-KR')+'</td>';
									html += '<td style="text-align:right;">'+data.receivedDetailList[z].receivedCurrency+'</td>';
									html += '<td style="text-align:right;">'+data.receivedDetailList[z].exchangeRate+'</td>';
									html += '<td style="text-align:right;">'+data.receivedDetailList[z].invAmt.toLocaleString('ko-KR')+'</td>';
									html += '<td style="text-align:right;">'+data.receivedDetailList[z].invCurrency+'</td>';
									html += '<td style="text-align:right;">';
									html += '<input type="button" value="삭제" class="delBtn" tabindex="-1"></td>';
									html += '</tr>';
								}
								html += '</tbody><tfoot>';
								html += '<tr><td style="font-weight:bold; text-align:center">Total</td>';
								html += '<td style="font-weight:bold; text-align:right" colspan=6>'+data.invoiceStatusList.receivedAmt.toLocaleString('ko-KR')+'</td></tr>';
								html += '</tfoot></table></form>';
								html += '<form id="invoiceClose">';
								html += '<input type="hidden" class="invoiceNo" name="invoiceNo" value="'+data.parameterInfo.invoiceNo+'">';
								html += '<input type="hidden" class="invoiceNo" name="userId" value="'+data.parameterInfo.userId+'">';
								html += '<table style="width:100%; margin-top:20px;">';
								html += '<tr><td style="text-align:right;"><input id="btn_close" type="button" value="정산 처리"></td></tr>';
								html += '</table></form>';
	
								$("#invoiceDetail").empty();
								$("#invoiceDetail").append(html);
	
							},
							error : function(xhr, status) {
								console.log(xhr + " : " + status);
							}
						})
					} else {
						$("#invoiceDetail").empty();
					}
				})
				
				$(document).on('click', '#codeIn', function() {
					var _width = '480';
				    var _height = '380';
				    var _left = Math.ceil(( window.screen.width - _width )/2);
				    var _top = 150;
					window.open("/mngr/invoice/popupReceivedCode","CODE",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
				})
				
				$(document).on('click', '#btnInsert', function(e) {
					
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

					var formData = $("#collectForm").serialize();
					console.log(formData);
					$.ajax({
						url : '/mngr/invoice/popupInvoiceCollectReg',
						type : 'POST',
						data : formData,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(result) {
							if (result == "S") {
								var invoiceNo = $("#invoiceNoSelect").val();
								$("#invoiceNoSelect").val(invoiceNo).trigger('change');
							} else {
								console.log('fail');
							}
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
					
				})

				$(document).on('click', '.delBtn', function() {
					var result = confirm('정말 삭제 하시겠습니까?');

					if (!result) {
						return false;
					}

					var index = $(".delBtn").index(this);
					var idx = $(".idx").eq(index).val();
					var userId = $("#userId").val();
					var invoiceNo = $("#invoiceNo").val();

					var target = {idx: idx, userId: userId, invoiceNo: invoiceNo};
					console.log(target);
					$.ajax({
						url : '/mngr/invoice/invoicePopUpReceivedDel',
						type : 'POST',
						data : target,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(result) {
							if (result == "S") {
								var invoiceNo = $("#invoiceNoSelect").val();
								$("#invoiceNoSelect").val(invoiceNo).trigger('change');

							} else {
								console.log('fail');
							}
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
				})

				$(document).on('click', '#btn_close', function(e) {

					var balanceAmt = $("#balanceAmt").val();
					if (balanceAmt > 0) {
						alert('미수금이 존재합니다.');
						return false;
					}

					var userId = $("#userId").val();
					var invoiceNo = $("#invoiceNo").val();
					var target = {userId: userId, invoiceNo: invoiceNo};

					console.log(target);
					$.ajax({
						url : '/mngr/invoice/invoicePopupClose',
						type : 'POST',
						data : target,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(result) {
							if (result == "S") {
								alert("정상 처리 되었습니다.");
								opener.document.location.reload();
								self.close();
							} else {
								console.log('fail');
							}
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
						
					})
					
					
				})

				
			})
		</script>
		<!-- script addon end -->
	  	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

	</body>
</html>
