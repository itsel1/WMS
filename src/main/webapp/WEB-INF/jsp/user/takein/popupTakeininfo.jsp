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
								사입코드 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="" method="get">
						 		<input type="hidden" name="takeInCode" value="${takeInInfo.takeInCode}">
						 		<input type="hidden" name="useYn" value="${takeInInfo.useYn}">
						 		<input type="hidden" name="appvYn" value="${takeInInfo.appvYn}">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:125px;">
										<span style="padding:0;color:red">*</span>Station</th>
										<td>
											<select name="orgStation" style="width:100%">
												<c:forEach items="${orgStation}" var="orgStation" varStatus="status">
													<option value="${orgStation.stationCode}"
														<c:if test="${takeInInfo.orgStation == orgStation.stationCode}" >
															selected 
														</c:if>
														>${orgStation.stationName}</option>
												</c:forEach>
											</select>
										</td>
										<td style="padding:0px;" rowspan="2">
											<img alt="itemImg" style="width:100%;height:80px;" src="${takeInInfo.itemImgUrl}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;"><span style="padding:0;color:red">*</span> 상품코드</th>
										<td style="padding:0px;">
											<input name="cusItemCode"class="onlyEN" id="cusItemCode" style="width:100%" type="text" required value="${takeInInfo.cusItemCode}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">Brand</th>
										<td style="padding:0px;">
											<input name="brand" style="width:100%" type="text" value="${takeInInfo.brand}">
										</td>
										<th class="center colorBlack" style="width:100px;">Hs_code</th>
										<td style="padding:0px;">
											<input name="hsCode" class="onlyEN" style="width:100%" type="text" value="${takeInInfo.hsCode}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"><span style="padding:0;color:red">*</span>  상품명(영문)</th>
										<td style="padding:0px;" colspan=3>
											<input id="itemDetail" name="itemDetail" class="onlyEN" style="width:100%" type="text" required value="${takeInInfo.itemDetail}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack">자국 상품명</th>
										<td style="padding:0px;" colspan=3>
											<input  name="nativeItemDetail" style="width:100%" type="text" value="${takeInInfo.nativeItemDetail}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack">상품옵션</th>
										<td style="padding:0px;" colspan=3>
											<input name="itemOption" style="width:100%" type="text" value="${takeInInfo.itemOption}">
										</td>
									</tr>
									 <tr>
										<th class="center colorBlack">상품 Color</th>
										<td style="padding:0px;">
											<input name="itemColor" style="width:100%" type="text" value="${takeInInfo.itemColor}">
										</td>
										<th class="center colorBlack">상품 Size</th>
										<td style="padding:0px;">
											<input name="itemSize" style="width:100%" type="text" value="${takeInInfo.itemSize}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"><span style="padding:0;color:red">*</span>  상품가격</th>
										<td style="padding:0px;">
											<input id="unitValue" name="unitValue" style="width:100%;text-algn:right"  type="number" value="${takeInInfo.unitValue }" required>
										</td>
										<th class="center colorBlack">화폐단위</th>
										<td style="padding:0px;">
											<select style="width:100%" name="unitCurrency">
												<c:forEach items="${currList}" var="list">
													<option value="${list.currency}"
														<c:if test="${!empty takeInInfo.unitCurrency}">
															<c:if test="${takeInInfo.unitCurrency eq list.currency}"> selected</c:if>
														</c:if>
														<c:if test="${empty takeInInfo.unitCurrency}">
															<c:if test="${list.currency eq 'USD'}"> selected</c:if>
														</c:if>
													>${list.currency} / ${list.nationName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack">상품 품목명</th><td style="padding:0px;">
											<input style="width:100%" type="text" name="itemDiv" value="${takeInInfo.itemDiv }">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack">
										<span style="padding:0;color:red">*</span> 제조국</th>
										<td style="padding:0px;">
											<input style="width:100%" type="text" name="makeCntry" value="${takeInInfo.makeCntry }">
										</td>
										<th class="center colorBlack">제조사</th>
										<td style="padding:0px;">
											<input style="width:100%" type="text" name="makeCom"  value="${takeInInfo.makeCom }">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">상품 재질</th>
										<td style="padding:0px;">
											<input name="itemMeterial" style="width:100%" type="text" value="${takeInInfo.itemMeterial }">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">상품 실무게</th>
										<td style="padding:0px;">
											<input name="wta" style="width:100%" type="number" value="${takeInInfo.wta }">
										</td>
									</tr>
										<tr>
										<th class="center colorBlack" style="width:100px;">무게 단위</th>
										<td style="padding:0px;">
											<select name="wtUnit" style="width:100%">
												<option value="KG">KG</option>
												<option value="LB">LB</option>
											</select>
										</td>
										<th class="center colorBlack" style="width:100px;">수량 단위</th>
										<td style="padding:0px;">
											<input style="width:100%" name="qtyUnit" type="text" value="EA">
										</td>
									</tr>
										<tr>
											<th class="center colorBlack"style="width:100px;">상품Url</th>
											<td style="padding:0px;" colspan=3>
												<input name="itemUrl" style="width:100%;" type="text" value="${takeInInfo.itemUrl}"> 
											</td>
										</tr>
										<tr>
											<th class="center colorBlack"style="width:100px;">상품imgUrl</th>
										<td style="padding:0px;" colspan=3>
											<input   name="itemImgUrl"  style="width:100%" type="text" value="${takeInInfo.itemImgUrl }">
										</td>
									</tr>
									<tr>
										<td colspan ="4" style="text-align:right">
											<c:if test="${takeInInfo.appvYn eq 'N'}">											
													<input type=button id="btnUpdate" value="수정">
											</c:if>
											
											<c:if test="${takeInInfo.appvYn eq 'Y'}">
												승인 처리된 상품은 수정 할 수 없습니다. 관리자에게 문의 주세요.	
											</c:if>
											
											<c:if test="${ empty   takeInInfo.takeInCode }">
													<input type=button id="btnInsert" value="등록">												
											</c:if>
										</td>
									</tr>
									</thead>
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

				$("#btnInsert").on('click',function(e){

					if($("#cusItemCode").val()==""){
						alert('상품코드를 입력하세요');
						$("#cusItemCode").focus();
						return false;
					}

					if( $("#userId").val() == ""){
						alert('UserID를 입력해주세요');
						$("#userId").focus();
						return false;
					}

					if($("#itemDetail").val()==""){
						alert('상품명을 입력해주세요');
						$("#itemDetail").focus();
						return false;
					}

					if($("#unitValue").val()==""){
						alert('상품 단가를 입력해주세요');
						$("#unitValue").focus();
						return false;
					}
					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/cstmr/takein/TakeinIn',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							alert(data.rstMsg);
							var Status = data.rstStatus;

							if(Status == "SUCCESS"){
								opener.parent.location.reload();
								window.close();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
					
				})

				$("#btnUpdate").on('click',function(e){

					if($("#cusItemCode").val()==""){
						alert('상품코드를 입력하세요');
						$("#cusItemCode").focus();
						return false;
					}

					if( $("#userId").val() == ""){
						alert('UserID를 입력해주세요');
						$("#userId").focus();
						return false;
					}

					if($("#itemDetail").val()==""){
						alert('상품명을 입력해주세요');
						$("#itemDetail").focus();
						return false;
					}

					if($("#unitValue").val()==""){
						alert('상품 단가를 입력해주세요');
						$("#unitValue").focus();
						return false;
					}

					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/cstmr/takein/TakeinUp',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							alert(data.rstMsg);
							var Status = data.rstStatus;

							if(Status == "SUCCESS"){
								opener.parent.location.reload();
								window.close();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				})
				
			})
		</script>
		<!-- script addon end -->
	  	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#trkDate" ).datepicker({
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
