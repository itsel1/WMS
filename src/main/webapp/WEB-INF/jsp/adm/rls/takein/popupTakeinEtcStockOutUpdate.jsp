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
		<script src="/assets/js/bootstrap.min.js">
		</script>
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
							<!-- <i class="ace-icon fa fa-home home-icon"></i> -->
								<p style="font-weight:bold">${takeinOrderList.hawbNo}</p>	 
							</li>
						</ul>
					</div>
				<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
						<form id="searchForm">
							<input type="hidden" name="nno" value="${takeInEtcOrder.nno}">
							<input type="hidden" name="userId" value="${takeInEtcOrder.userId}">
						</form>

							<table class="table table-bordered" style="width:650px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>UserID</th>
										<td style="padding:0px;width:200px;">
											<input id="userId" name="userId" readonly="readonly" style="width:100%" type="text" value="${takeInEtcOrder.userId}">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack"  style="width:100px;">비용</th>
										<td style="padding:0px;width:200px;">
											<input id="shippingFee" name="shippingFee" style="width:100%" type="number" value="${takeInEtcOrder.shippingFee}">
										</td>										
										<th class="center" >화폐단위</th>
										<td style="padding:0px;width:200px;vertical-align: middle">
										<select id='etcCurrency' name='etcCurrency' style="width:100%">
											<c:forEach items="${currencyList}" var="currencyList" varStatus="status">
												<option value='${currencyList.code}'
													<c:if test="${currencyList.code == takeInEtcOrder.etcCurrency}">selected</c:if>
												>
												${currencyList.name}</option>
											</c:forEach>
										</select>
									</tr>
									<tr>
										<th class="center colorBlack" style="width:100px;"><span style="color:red;">* </span>일시</th>
										<td style="padding:0px;width:200px;" colspan=1>
											<input type="text" id="orderDate" name="orderDate" value="${ takeInEtcOrder.orderDate}" style="width:100%" value="" readonly="readonly"/>
										</td>
									</tr>
									<tr>
										<th class="center" >Trk Com</th>
										<td style="padding:0px;">
											<input id="trkCom" type="text" style="width:100%" name="trkCom" value="${ takeInEtcOrder.trkCom}">
										</td>
										<th class="center" >Trk No</th>
										<td style="padding:0px;">
											<input id="trkNo" type="text" style="width:100%" name="trkNo" value="${ takeInEtcOrder.trkNo}">
										</td>
									</tr>
									<tr>
									<th class="center colorBlack" style="width:100px;">Remark</th>
										<td style="padding:0px;width:200px;" colspan=3>
											<input id="remark" type="text" style="width:100%" name="remark" value="${takeInEtcOrder.remark}">
										</td>
									</tr>
									</thead>
								</table>

						<div id="rstMsg" style="color:red;font-weight:bold;text-align:center;font-size:30px;float:left;height:50px;width:100%;">
							
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

				var audio = new Audio();
				searchItem();
				$("#stockNo").focus();


				$("#btnSend").on('click',function(e){
					

				    $("#morderDate").attr('value',$("#orderDate").val());
				    $("#mtrkCom").attr('value',$("#trkCom").val());
				    $("#mtrkNo").attr('value',$("#trkNo").val());
				    $("#mshippingFee").attr('value',$("#shippingFee").val());
				    $("#metcCurrency").attr('value',$("#etcCurrency").val());
				    $("#mremark").attr('value',$("#remark").val());


				    if($("#restCnt").val() >0){
						audio.src = '/js/stop.mp3';
						audio.play();
						 $("#rstMsg").text('미등록 재고가 존재 합니다.');
						return false;
					};

					var formData = $("#mgrForm").serialize();

					$.ajax({
						url:'/mngr/takein/takeInEtcStockOut', 
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
							
							 //$("#rstMsg").text(data.rstMsg);
							 //searchItem();
							// $("#stockNo").val('');
							// $("#stockNo").focus(); 
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
	
					//
					
				});

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
							url:'/mngr/takein/takeInEtcStockIn', 
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
								
								 $("#rstMsg").text(data.rstMsg);
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

			function searchItem(){
				$("#takeinItem tbody").empty();
				var formData = $("#searchForm").serialize();

				$.ajax({
					url:'/mngr/takein/takeInEtcItemJson',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						
						var row = ''; 
						var restCnt = 0;
						var total_wta = 0;
						var total_wtc = 0;

						$.each(data,function(key,value) {
							row = row + '<tr>';
							row = row + '<td style="text-align:center;font-weight:bold;font-size: 30px;">'+value.cusItemCode+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;">'+value.itemDetail+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;text-align:right">'+value.itemCnt+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;text-align:right">'+value.outCnt+'</td>';
							row = row + '</tr>';
							restCnt =  restCnt+parseInt(value.itemCnt-value.outCnt);
						
						});

						$("#restCnt").val(restCnt);
							
		                $("#takeinItem").append(row);

		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}
					
		</script>
		<!-- script addon end -->

	</body>
</html>