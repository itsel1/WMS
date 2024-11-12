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
		<title>ETC</title>
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
								ETC 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="" method="get">
						      <input type="hidden" name="idx" value="${etcInfoRow.idx}">
						      <input type="hidden" name="orgStation" value="${etcInfoRow.orgStation}">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;">UserID</th>
										<td style="padding:0px;width:200px;">
											<input id="userId" name="userId" class="onlyEN" 
												<c:if test="${etcInfoRow.userId != null}"> readonly</c:if>
												style="width:100%" type="text" value="${etcInfoRow.userId}">
										</td>
										<th class="center colorBlack" style="width:100px;"><span style="color:red;">* </span>일시</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" id="etcDate" name="etcDate" style="width:100%" value="${etcInfoRow.etcDate}" readonly="readonly"/>
										</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:100px;">HawbNo</th>
										<td style="padding:0px;width:200px;">
											<input type="text" name="hawbNo" style="width:100%" value="${etcInfoRow.hawbNo}">
										</td>
										<th class="center" ><span style="color:red;">* </span>구분</th>
									<td style="padding:0px;width:200px;vertical-align: middle">
										<select id='etcType' name='etcType' style="width:100%">
												<c:forEach items="${etcTypeList}" var="etcTypeList" varStatus="status">
													<option value='${etcTypeList.code}'
														<c:if test="${etcInfoRow.etcType == etcTypeList.code}">selected</c:if>>
														${etcTypeList.name}
													</option>
												</c:forEach>
											</select>
									</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:125px;"><span style="color:red;">* </span>금액</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="Number" id="etcValue" name="etcValue" style="width:100%" value="${etcInfoRow.etcValue}">
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
									</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:100px;">비고</th>
										<td style="padding:0px;width:200px;" colspan=3>
											<input type="text" name="remark" style="width:100%" value="${etcInfoRow.remark}">
										</td>
									</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan=4>
											    <input type="button" id="btnDelete" value="삭제" tabindex="-1" style="float:left"/>
												<input type="button" id="btnInsert" value="등록" style="float:right"/>
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

				$("#userId").focus();

				

				$("#btnInsert").on('click',function(e){

	 				if( $("#userId").val() == ""){
						alert('아이디를 입력해주세요');
						$("#userId").focus();
						return false;
					}

	 				if( $("#ectValue").val() == ""){
						alert('수량을 입력해주세요');
						$("#whInCtn").focus();
						return false;
					}

	 				if( $("#whInDate").val() == ""){
						alert('검수일자를 입력해주세요');
						$("#whInDate").focus();
						return false;
					}


					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/mngr/invoice/popupEtcIn',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							var Status = data.rstStatus;

							alert(Status);

							if(Status == "SUCCESS"){
								alert(data.rstMsg);
								window.close();
								opener.parent.location.reload();
							}else{
								alert(data.rstMsg);
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				})

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
		</script>
		<!-- script addon end -->
	  	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#etcDate" ).datepicker({
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
