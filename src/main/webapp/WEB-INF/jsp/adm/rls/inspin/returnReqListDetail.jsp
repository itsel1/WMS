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
								반품 PACKING
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
						 <form id="mgrForm" action="inspAddInGroupStockModity" method="POST">
						 		<input type="hidden" name="groupIdx" value="${rstInfo.nno}">
						 		<input type="hidden" name="orgStation" value="${rstInfo.orgStation}">
						 		<input type="hidden" name="orgStation" value="${rstInfo.userId}">
								<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:125px;">UserId</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" id="userId" style="width:100%" type="text"  value="${rstInfo.userId}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:125px;">Return No</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" id="userId" style="width:100%" type="text"  value="${rstInfo.returnNo}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:125px;">반품 타입</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" style="width:100%" type="text"  value="${rstInfo.returnTypeName}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:125px;">택배사</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" style="width:100%" type="text"  value="${rstInfo.trkCom}">
										</td>
										<th class="center colorBlack"  style="width:125px;">운송장번호</th>
										<td style="padding:0px;">
											<input name="userId"class="onlyEN" style="width:100%" type="text"  value="${rstInfo.trkNo}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;">반품 사유</th>
										<td style="padding:0px;" colspan="3">
											<input style="width:100%" id="remark" name="remark" type="text" value="${rstInfo.returnReason}">
										</td>
									</tr>
								<!-- 	<tr>
										<td colspan ="4" style="text-align:right">
											<input type=button id="btnUpdate" value="수정">						
										</td>
									</tr> -->
									</thead>
								</table>
							</form>
							
							<form id="stockForm" method="get">
							<input type="hidden" name="nno" value="${rstInfo.nno}">
						 		<input type="hidden" name="orgStation" value="${rstInfo.orgStation}">
						 		<input type="hidden" name="userId" value="${rstInfo.userId}">
						 		<input type="hidden" name="nno" value="${rstInfo.nno}">
						 		<input type="text"  id="" name="" value="" style="width:100%;font-size:20px;visibility: hidden">
						 		
								<table class="table table-bordered" style="width:600px;">
								  <thead>
								  	<tr>
										<th class="center colorBlack" style="width:100px;">Stock No</th>
										<td style="padding:0px;">
											<input type="text" id="stockNo" name="stockNo" value="" style="width:100%;font-size:20px;">
										</td>
									</tr>
									<tr>
										<td style="padding:0px;" colspan=2>
											<input type="text" id="rstMsg" readonly="readonly" value="" style="width:100%;font-size:20px;color:red;font-weight:bold">
										</td>
									</tr>
								  </thead>
							    </table>
							 </form>
							 	<br>
							 	<br>
								<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack" Style="width:280px;">
													Rack
												</th>
												<th class="center colorBlack" Style="width:180px;">
													Stock No
												</th>
												<th class="center colorBlack">
													Item Detail
												</th>
												<th class="center colorBlack">
													요청 수량
												</th>
												<th class="center colorBlack">
													등록 수량
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${itemInfo}" var="itemInfo" varStatus="status">
											<tr>
												<td class="center colorBlack">
													${itemInfo.rackCode}
												</td>
												<td class="center colorBlack">
													${itemInfo.stockNo}
												</td>
												<td class="center colorBlack">
													${itemInfo.itemDetail}
												</td>
												<td class="center colorBlack">
													${itemInfo.reqCnt}
												</td>
												<td class="center colorBlack">
													${itemInfo.resCnt}
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
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

				var audio = new Audio();

			     $("#stockNo").focus();

					$("#stockNo").keydown(function(key) {
						
						if (key.keyCode == 13) {
							if($(this).val()==""){
								return false;
							};

							if($(this).val()=="aci_enter"){
								$("#btnSend").trigger("click");
								return false;
							};
							
							var formData = $("#stockForm").serialize();
							$.ajax({
								url:'/mngr/inspin/ReturnStockWhoutTmp', 
								type: 'GET',
								beforeSend : function(xhr)
								{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
								    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
								},
								data: formData, 
								success : function(data) {
									var Status = data.rstStatus;
									var Code = data.rstCode;

									
									if(Code =="S10"){
										audio.src = '/js/pass.mp3';
										audio.play();
									}
									if(Code =="A10"){
										audio.src = '/js/beep11.wav';
										audio.play();
									}

									if(Code =="F15"){
										audio.src = '/js/stop.mp3';
										audio.play();
									}

									if(Code =="F100"){
										audio.src = '/js/stop.mp3';
										audio.play();
									}
									
									 $("#rstMsg").val(data.rstMsg);
									 searchItem();
									 $("#stockNo").val('');
									 $("#stockNo").focus(); 
					            }, 		    
					            error : function(xhr, status) {
					                alert(xhr + " : " + status);
					                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
					            }
							})
						};
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
