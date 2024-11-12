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
								사입 기타 출고 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="/mngr/takein/EtcOrderIn" method="post">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>UserID</th>
										<td style="padding:0px;width:200px;">
											<input id="userId" name="userId" class="onlyEN" style="width:100%" type="text" value="${takeinInfo.userId}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:100px;">비용</th>
										<td style="padding:0px;width:200px;">
											<input id="shippingFee" name="shippingFee" style="width:100%" type="number" value="${takeinInfo.shippingFee}">
										</td>
										<th class="center" >화폐단위</th>
										<td style="padding:0px;width:200px;vertical-align: middle">
										<select id='etcCurrency' name='etcCurrency' style="width:100%">
											<c:forEach items="${currencyList}" var="currencyList" varStatus="status">
												<option value='${currencyList.code}'
												<c:if test="${currencyList.chk == 'CHKED'}">selected</c:if>>
												${currencyList.name}</option>
											</c:forEach>
										</select>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;"><span style="color:red;">* </span>일시</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" id="orderDate" name="orderDate" style="width:100%" value="" readonly="readonly"/>
										</td>
										<%-- <th class="center" >BOX TYPE</th>
										<td style="padding:0px;">
											<select id='boxType' name='boxType' style="width:100%">
												<c:forEach items="${boxSizeList}" var="boxSizeList" varStatus="status">
													<option value='${boxSizeList.code}'
													<c:if test="${boxSizeList.boxChk == 'CHK'}">selected</c:if>>
													${boxSizeList.code} (${boxSizeList.name})</option>
												</c:forEach>
											</select>
										</td> --%>
									</tr>
									<tr>
										<th class="center" >Trk Com</th>
										<td style="padding:0px;">
											<input type="text" style="width:100%" name="trkCom" value="${ takeinInfo.cusItemCode}">
										</td>
										<th class="center" >Trk No</th>
										<td style="padding:0px;">
											<input type="text" style="width:100%" name="trkNo" value="${ takeinInfo.hsCode}">
										</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:100px;">Remark</th>
										<td style="padding:0px;width:200px;" colspan=3>
											<input type="text" style="width:100%" name="remark" value="${takeinInfo.itemDetail}">
										</td>
									</tr>
									</thead>
								</table>
								<br>
								<div class="col-xs-7 col-md-7" style="text-align: left;">
								<table class="table table-bordered" style="max-width:400px;">
									<thead>
										<tr>
											<th class="center colorBlack"  style="width:120px;padding:0px;" >CusItemCode</th>
											<th class="center colorBlack" style="padding:0px;">
												<input type="text" id="cusItemCodeIn" style="width:100%">
											</th>
											<th class="center colorBlack"  style="padding:0px;" >
												<input type="button" id= "btnItemIn" value="등록">
											</th>
										</tr>
									</thead>
								</table>
								</div>
								<div class="col-xs-5 col-md-5" style="text-align: right;vertical-align: middle">
									<input type="button" class="btn btn-white btn-inverse btn-xs" id="btnUserItems" value="상품리스트보기">
								</div>
								<table id="itemInfo" class="table table-bordered" style="width:650px;">
									<thead>
										<tr>
											<th class="center colorBlack" style="width:120px;">TakeInCode</th>
											<th class="center colorBlack" style="width:120px;">CusItemCode</th>
											<th class="center colorBlack">ItemDetail</th>
											<th class="center colorBlack" style="width:50px;">itemCnt</th>
											<th class="center colorBlack" style="width:80px;"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
										<tfoot>
										<tr>
											<td colspan=5 style="text-align:right">
												<input type="button" id="btnInsert" value="등록" />
											</td>
										</tr>
									</tfoot>
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
		
		
		<!-- script addon start -->
		
	<script type="text/javascript">
			jQuery(function($) {

				$(".onlyEN").keyup(function(event){ 
					 if (!(event.keyCode >=37 && event.keyCode<=40)) {
					    var inputVal = $(this).val();
					    $(this).val(inputVal.replace(/[^a-z0-9 ]/gi,''));
					   } 
			     });

				$('#userId').focusout(function() {
					if($('#userId').val() !=""){
						fn_userIdChk();
					}
				});

				$("#userId").keydown(function(key) {
					if (key.keyCode == 13) {
						fn_userIdChk();
					}
				});
					
				$("#btnUserItems").on('click',function(e){
					var userId = "";
					userId = $("#userId").val();

					if(userId ==""){
						alert('아이디를 기입해주세요.');
						$("#userId").focus();
						return false;
					}
					window.open("/mngr/takein/popupTakeinUserItems?userId="+userId,"takeinItemWin", "width=720,height=600");
				});
	

				$("#btnItemIn").on('click',function(e){

					if($("#userId").val() == ""){
						alert('아이디를 입력해주세요.');
						$("#userId").focus();
						return false;
					}

					if($("#cusItemCodeIn").val()==""){
						alert('상품코드를 입력해주세요.');
						$("#cusItemCodeIn").focus();
						return false;
					}
					
					fn_takeinCodeIn($("#cusItemCodeIn").val(),$("#userId").val());
				});

				$("#cusItemCodeIn").keydown(function(key) {

					if (key.keyCode == 13) {

						if($("#userId").val() == ""){
							alert('아이디를 입력해주세요.');
							$("#userId").focus();
							return false;
						}

						if($(this).val()==""){
							alert('상품코드를 입력해주세요.');
							$(this).focus();
							return false;
						}
						
						fn_takeinCodeIn($(this).val(),$("#userId").val());
					}
				});


				$(document).on("click",".btnTrDel",function(){
					 $(this).closest("tr").remove(); 
				}); 

				$("#btnInsert").on('click',function(e){

					if($("#userId").val() ==""){
						alert('아이디를 입력해주세요.');
						$("#userId").focus();
						return false;
					}

					if($("#userId").val() ==""){
						alert('아이디를 입력해주세요.');
						$("#userId").focus();
						return false;
					}
					

					if($("#userId").val() ==""){
						alert('아이디를 입력해주세요.');
						$("#userId").focus();
						return false;
					}

					if($(".itemCnt").length==0){
						alert('등록된 상품이 존재 하지 않습니다.');
						return false;
					}

					$(".itemCnt").each(function(){
						if($(this).val() <= 0){
							alert('상품 수량을 등록해주세요');
							$(this).foucus();
							return false;
						}
					});
					var data = $("#mgrForm").serialize();

					$.ajax({
						url:'/mngr/takein/EtcOrderIn',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: data, 
						success : function(data) {
							var Status = data.rstStatus;

							if(Status == "SUCCESS"){
								opener.document.location.reload();
								self.close();
							}else{
								alert("등록에 실패했습니다.");
							}
			            }, 		    
			            error : function(request,status,error) {
							console.log("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
							alert("정보 조회에 실패하였습니다.");
						}
					})

				})
				
			})
			
			
			function fn_userIdChk(){
				$.ajax({
					url:'/mngr/takein/userIdChk',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: $("#userId").val()}, 
					success : function(data) {
						var Status = data.rstStatus;						
						if(Status == "SUCCESS"){
							$("#userId").attr("readOnly","true");
						}else{
							alert('등록되지 않은 아이디 입니다.');
							$("#userId").focus();
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			}

			function fn_takeinCodeDel(){
			    $(this).closest("tr").remove(); 
			}
			
			function fn_takeinCodeIn(gCusItemCode,gUserId){

				$.ajax({
					url:'/mngr/takein/takeinCusItemInJson',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: gUserId,cusItemCode:gCusItemCode}, 
					success : function(data) {
						var Status = data.rstStatus;

						if(Status == "SUCCESS"){

							var chkCnt = 0;
							$(".cusItemCode").each(function(){
							
								if(data.rstCusItemCode == $(this).val()){
									chkCnt++;
								}
							});

							if(chkCnt!=0){
								alert('이미 등록된 상품코드 입니다.');
								$("#cusItemCodeIn").attr("value","");
								$("#cusItemCodeIn").focus();
								return false;
							}

							if(chkCnt == 0){
								var innerHtml = "";
								innerHtml += '<tr>';
								innerHtml += '<th class="center colorBlack" Style="padding:0px"><input type="text" class="takeInCode" readonly name="takeInCode[]" value="'+data.rstTakeInCode+'"/></th>';
								innerHtml += '<th class="center colorBlack" Style="padding:0px"><input type="text" class="cusItemCode" readonly name="cusItemCode[]" value="'+data.rstCusItemCode+'"/></th>';
								innerHtml += '<th style="text-align:left;padding:0px" ><input type="text" style="width:100%" class="itemDetail" readonly name="itemDetail[]" value="'+data.rstItemDetail+'"/></th>';
								innerHtml += '<th class="right colorBlack" style="padding:0px"><input type="number" style="text-align:right" class="itemCnt" name="itemCnt[]" value="0"/></th>';
								innerHtml += '<td style="text-align:center"><input type="button"  class="btnTrDel" value="삭제"></td>';
								innerHtml += '</tr>';
								$('#itemInfo > tbody:last').append(innerHtml);
							}
							
						}else{
							alert(data.rstMsg);
							if(data.rstCode == "F10"){
								$("#userId").focus();
							}else{
								$("#cusItemCodeIn").focus();
							}
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})

			}
		</script>
	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
		

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#orderDate" ).datepicker({
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
