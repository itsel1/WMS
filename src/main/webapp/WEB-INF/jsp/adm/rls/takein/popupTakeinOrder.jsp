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
								주문 정보
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div style="width: 50%; float: left;">
									<table class="table table-bordered" style="width:760px;">
									  	<thead>
										  	<tr>
										  		<th colspan=4>발송 정보</th>
										  	</tr>
											<tr>
												<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>UserID</th>
												<td style="padding:0px;width:200px;">
													<input list="customerList" id="userId" name="userId" class="onlyEN" style="width:100%;" type="text" value="${takeinInfo.userId}" required="required">
													<datalist id="customerList" name="customerList">
														<c:forEach items="${userList}" var="userList" varStatus="status">
															<option value="${userList.userId}"
																<c:if test="${userList eq userList.userId}">selected</c:if>>${userList.userId}
														</c:forEach>
													</datalist>
												</td>
												<th colspan=2>
												</th>
											</tr>
											<tr>
												<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>주문번호</th>
												<td style="padding:0px;width:200px;">
													<input type="text" style="width:100%;" id="orderNo" name="orderNo" value="" >
												</td>
												<th class="center" ><span style="color:red;">* </span>주문날짜</th>
												<td style="padding:0px;width:200px;vertical-align: middle">
													<input type="text" id="orderDate" name="orderDate" style="width:100%" value="" readonly="readonly">
												</td>
											</tr>
											<tr>
												<th class="center colorBlack"  style="width:140px;"><span style="color:red;">* </span>출발도시 코드</th>
												<td style="padding:0px;width:200px;" id="orgStationTd">
													<input list="userOrgStation" class="form-control" name="orgStation" id="orgStation">
												</td>
												<th class="center" style="width:140px;"><span style="color:red;">* </span>도착국가</th>
												<td style="padding:0px;width:200px;vertical-align: middle" id="dstnNationTd">
													<input list="nationList" class="form-control" name="dstnNation" id="dstnNation">
												</td>
											</tr>
											<tr>
												<th class="center" >송하인 명</th>
												<td style="padding:0px;" >
													<input type="text" style="width:100%;" name="shipperName" id="shipperName" value="${userInfo.shipperName}">
												</td>
												<th colspan=2>
												</th>
											</tr>
											<tr>
												<th class="center">전화번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperTel" name="shipperTel" value="${userInfo.shipperTel}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
												<th class="center">휴대전화 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperHp" name="shipperHp" value="${userInfo.shipperHp}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
											</tr>
											<tr>
												<th class="center">E-mail</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperEmail" name="shipperEmail" value="${userInfo.shipperEmail}">
												</td>
												<th class="center">우편주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperZip" name="shipperZip" value="${userInfo.shipperZip}">
												</td>
											</tr>
											<tr>
												<th class="center">도시</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperCity" name="shipperCity" value="${userInfo.shipperCity}">
												</td>
												<th class="center">주</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperState" name="shipperState" value="${userInfo.shipperState}">
												</td>
											</tr>
											<tr>
												<th class="center">주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperAddr" name="shipperAddr" value="${userInfo.shipperAddr}">
												</td>
												<th class="center">상세주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperAddrDetail" name="shipperAddrDetail" value="${userInfo.shipperAddrDetail}">
												</td>
											</tr>
										  	<tr>
										  		<th colspan=4>수취 정보</th>
										  	</tr>
											<tr>
												<th class="center" ><span style="color:red;">* </span>수취인 명</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeName" name="cneeName" value="" >
												</td>
												<th colspan=2></th>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>전화번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeTel" name="cneeTel" value="" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
												<th class="center">휴대전화 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeHp" name="cneeHp" value="" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>E-mail</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeEmail" name="cneeEmail" value="" >
												</td>
												<th class="center"><span style="color:red;">* </span>우편주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeZip" name="cneeZip" value="">
												</td>
											</tr>
											<tr>
												<th class="center">도시</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeCity" name="cneeCity" value="">
												</td>
												<th class="center">주</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeState" name="cneeState" value="">
												</td>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeAddr" name="cneeAddr" value="">
												</td>
												<th class="center">상세주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeAddrDetail" name="cneeAddrDetail" value="">
												</td>
											</tr>
											<tr>
												<th class="center">수취인 요청사항</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="dlvReqMsg" name="dlvReqMsg" value="">
												</td>
												<th class="center">구매 사이트</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="buySite" name="buySite" value="">
												</td>
											</tr>
											<tr>
												<th class="center">입고메모</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="whReqMsg" name="whReqMsg" value="">
												</td>
												<th colspan=2></th>
											</tr>
										</thead>
									</table>
								</div>
								<br>
								<div style="width: 48%; float: right;">
									<div class="col-xs-7 col-md-7" style="text-align: left; padding-left:0;">
										<table class="table table-bordered" style="max-width:400px;">
											<thead>
												<tr>
													<th class="center colorBlack"  style="width:120px;padding:0px;" ><span style="color:red;">* </span>CusItemCode</th>
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
									<div class="col-xs-5 col-md-5" style="text-align: right;vertical-align: middle; padding-right:0">
										<input type="button" class="btn btn-white btn-inverse btn-xs" id="btnUserItems" value="상품리스트보기">
									</div>
									<table id="itemInfo" class="table table-bordered" style="width:760px;">
										<thead>
											<tr>
												<th class="center colorBlack">사입코드</th>
												<th class="center colorBlack">상품코드</th>
												<th class="center colorBlack">상품명</th>
												<th class="center colorBlack">수량</th>
												<th class="center colorBlack">단가</th>
												<th class="center colorBlack"></th>
											</tr>
										</thead>
										<tbody>
										
										</tbody>

									</table>
									<div style="float: right;">
										<input type="button" id="btnInsert" value="등록" />
									</div>
								</div>
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
		
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
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

				/* $("#userId").keydown(function(key) {
					if (key.keyCode == 13) {
						fn_userIdChk();
					}
				}); */
					
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

					if($("#orderNo").val() == "") {
						alert('주문번호를 입력해주세요.');
						$("#orderNo").focus();
						return false;
					}

					if($("#orderDate").val() == "") {
						alert('주문날짜를 입력해주세요.');
						$("#orderDate").focus();
						return false;
					}

					if($("#orgStation").val() == "") {
						alert('출발도시 코드를 입력해주세요');
						$("#orgStation").focus();
						return false;
					}

					if($("#dstnNation").val() == "") {
						alert('도착국가 코드를 입력해주세요.');
						$("#dstnNation").focus();
						return false;
					}

					if($("#cneeName").val() == "") {
						alert('수취인 명을 입력해주세요');
						$("#cneeName").focus();
						return false;
					}

					if($("#cneeTel").val() == "") {
						alert('수취인 연락처를 입력해주세요');
						$("#cneeTel").focus();
						return false;
					}

					if($("#cneeEmail").val() == "") {
						alert('수취인 E-mail을 입력해주세요');
						$("#cneeEmail").focus();
						return false;
					}

					if($("#cneeZip").val() == "") {
						alert('수취인 우편번호를 입력해주세요');
						$("#cneeZip").focus();
						return false;
					}

					if($("#dstnNation").val() != "South Korea") {
						if($("#cneeCity").val() == "") {
							alert('수취인 도시를 입력해주세요');
							$("#cneeCity").focus();
							return false;
						}
					}

					if($("#cneeAddr").val() == "") {
						alert('수취인 주소를 입력해주세요');
						$("#cneeAddr").focus();
						return false;
					}

					if($(".cusItemCode").val() == "") {
						alert('상품을 등록해주세요');
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
					var data = $("#registForm").serialize();

					$.ajax({
						url:'/mngr/takein/registTakeinOrderInfo',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: data, 
						success : function(data) {
							
							if (data == "S") {
								alert("등록 되었습니다.");
								opener.document.location.reload();
								self.close();
							} else {
								alert("등록 중 오류가 발생하였습니다.");
							}
			            }, 		    
			            error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("등록에 실패하였습니다.");
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
							fn_getTakeinOrderInfo();
							
						}else{
							alert('등록되지 않은 아이디 입니다.');
							//$("#userId").focus();
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			}

			function fn_getTakeinOrderInfo() {
				$.ajax({
					url : '/mngr/takein/popupTakeinOrderInfo',
					type : 'GET',
					data : {userId: $("#userId").val()},
					success : function(data) {
						var addHtml = "";
						var addHtml2 = "";
						
						addHtml += '<input list="userOrgStation" class="form-control" name="orgStation" id="orgStation"/>';
						addHtml += '<datalist id="userOrgStation" name="userOrgStation">';
						for (var i = 0; i < data.userOrgStation.length; i++) {
							addHtml += '<option ';
							if (data.userOrgStation[i].stationCode == $("#orgStation").val()) {
								addHtml += ' selected';
							} 
							addHtml += 'value="'+data.userOrgStation[i].stationCode+'">'+data.userOrgStation[i].stationName+'</option>';
						}
						addHtml += '</datalist></td>';

						addHtml2 += '<input list="nationList" class="form-control" name="dstnNation" id="dstnNation"/>';
						addHtml2 += '<datalist id="nationList" name="nationList">';
						for (var i = 0; i < data.nationList.length; i++) {
							addHtml2 += '<option ';
							if (data.nationList[i].nationCode == $("#dstnNation").val()) {
								addHtml2 += ' selected';
							} 
							addHtml2 += 'value="'+data.nationList[i].nationEName+'">'+data.nationList[i].nationName+'</option>';
						}
						addHtml2 += '</datalist></td>';

						$("#orgStation").html(addHtml); 
						$("#dstnNation").html(addHtml2); 

						console.log(data.userInfo);
						$("#shipperName").val(data.userInfo.comEName);
						$("#shipperTel").val(data.userInfo.userTel);
						$("#shipperHp").val(data.userInfo.userHp);
						$("#shipperEmail").val(data.userInfo.userEmail);
						$("#shipperZip").val(data.userInfo.userZip);
						$("#shipperAddr").val(data.userInfo.userEAddr);
						$("#shipperAddrDetail").val(data.userInfo.userEAddrDetail);

						
					},
					error : function(request, status, error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
						console.log(data);
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
								innerHtml += '<th style="padding:0px; width:80px; height:40px;"><input style="width:80px; height:40px;" type="text" class="takeInCode" readonly name="takeInCode" value="'+data.rstTakeInCode+'"/></th>';
								innerHtml += '<th style="padding:0px; width:80px; height:40px;"><input style="width:80px; height:40px;" type="text" class="cusItemCode" readonly name="cusItemCode" value="'+data.rstCusItemCode+'"/></th>';
								innerHtml += '<th style="padding:0px; width:280px; height:40px;" ><input type="text" style="width:280px; height:40px;" class="itemDetail" readonly name="itemDetail" value="'+data.rstItemDetail+'"/></th>';
								innerHtml += '<th style="padding:0px; height:40px;"><input style="width:80px; height:40px;" type="number" style="text-align:right" class="itemCnt" name="itemCnt" value="0"/></th>';
								innerHtml += '<td style="padding:0px; height:40px;"><input style="width:80px; height:40px;" type="number" style="text-align: right;" class="unitValue" name="unitValue" value=""/></td>';
								innerHtml += '<td><input type="button"  class="btnTrDel" value="삭제"></td>';
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

			$("#searchId").on('click',function(e){
				window.open("/mngr/order/orderInspcSearchId?idArea="+$("#userId").val(),"_blank","toolbar=yes,resizable=no,directories=no,width=480, height=360");

			})

			
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
