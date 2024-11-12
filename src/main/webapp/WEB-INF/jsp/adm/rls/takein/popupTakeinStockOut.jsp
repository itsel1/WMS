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
							<input type="hidden" name="nno" value="${takeinOrderList.nno}">
							<input type="hidden" name="userId" value="${takeinOrderList.userId}">
						</form>
						
						<table class="table table-bordered">
								  <thead>
								 	<tr>
									   <th class="center" style="width:120px;">HawbNo</th>
									   <th style="background-color:#ffffff;color: #393939;">${takeinOrderList.hawbNo}</th>
									</tr>
									<tr>
									   <th class="center">수취인명</th>
									   <th style="background-color:#ffffff;color: #393939;">${takeinOrderList.cneeName}</th>
									</tr>
									<tr>
									   <th class="center">수취인 주소</th>
									   <th style="background-color:#ffffff;color: #393939;">${takeinOrderList.cneeAddr}${takeinOrderList.cneeAddrDetail}</th>
									</tr>
									<tr>
									   <th class="center">orderNo</th>
									   <th style="background-color:#ffffff;color: #393939;">${takeinOrderList.orderNo}</th>
									</tr>
									</thead>
								</table>
								<br>
					
						<form id="stockForm" onsubmit="return false" action="/mngr/takein/takeInStockIn" method="get">
						    <input type="hidden" name="userId" value="${takeinOrderList.userId}">
						    <input type="hidden" name="orgStation" value="${takeinOrderList.orgStation}">
						    <input type="hidden" name="nno" value="${takeinOrderList.nno}">
						    <input type="hidden" id="adminId" name="adminId" value="${adminId}">
						    <button type="button" id="batchBarcode" name="batchBarcode" class="btn btn-primary">barcode Batch</button>
						    <!-- <button type="button" id="batchBarcodeTest" class="btn-white">Barcode Batch (Test)</button> -->
						   <%--  <c:if test="${adminId eq 'itsel2'}">
						    	<button type="button" id="batchBarcode" name="batchBarcode" class="btn btn-primary">barcode Batch</button>
						    </c:if> --%>
						    <br/><br/>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th style="padding:0px;width:80px;text-align:center;">BarCode</th>
										<th style="padding:0px;">
											<input type="text" id="stockNo" name="stockNo" value="" style="font-size:30px;width:100%;">
										</th>
									</tr>
								</thead>
							</table>
						</form>
						<div id="rstMsg" style="color:red;font-weight:bold;text-align:center;font-size:30px;float:left;height:50px;width:100%;">
							
						</div>
						
						  <input type="hidden" id="restCnt" value="">
						  
						 <form id="mgrForm" action="/mngr/takein/TakeinHawbIn" method="get">
						 
						 	    <input type="hidden" name="userId" value="${takeinOrderList.userId}">
						    	<input type="hidden" name="userId" value="${takeinOrderList.orgStation}">
						    	<input type="hidden" name="nno" value="${takeinOrderList.nno}">
						    	
						    	<input type="hidden" name="wtUnit" value="${takeinOrderList.wtUnit}">
						    	<input type="hidden" name="dimUnit" value="${takeinOrderList.dimUnit}">
						    	<input type="hidden" name="per" value="${takeinOrderList.per}">
						    	
						 		
								<table id="takeinItem" class="table table-bordered">
								  <thead>
								 	<tr>
								 	   <th class="center" style="">상품코드</th>
									   <th class="center" style="">상품명</th>
									   <th class="center" style="width:120px;">요청수량</th>
									   <th class="center" style="width:120px;">출고수량</th>
									</tr>
								 </thead>
								  <tbody>

								</tbody>
								</table>
								<br>
							
								<table class="table table-bordered" style="width:80px;float:left;">
								  <thead>
								  
										<tr>
											<th  style="width:80px;"> Box </th>
										</tr>
										
										<tr>
											<th style="padding:0px;"><input id="boxCnt" name="boxCnt" type="number" style="width:80px;text-align:center" value="1"  /></th>
										</tr>
										
								  </thead>
								</table>
									
								<table class="table table-bordered" style="width:250px;float:left;margin-left:15px;">
								  <thead>
								 		<tr>
											<th >실무게 ${takeinOrderList.wtUnit}</th>
											<th >부피 무게 ${takeinOrderList.wtUnit}</th>
										</tr>
										<tr>
											<th style="padding:0px;"><input id="wta" name="wta" type="number" style="width:100%;" /></th>
											<th style="padding:0px;"><input id="wtc" name="wtc" type="number" style="width:100%;"  readonly="readonly"/></th>
										</tr>
								  </thead>
								  
								</table>

								<table id="wtcTable" class="table table-bordered" style="width:560px;margin-left:15px; border:1px solid balck;float:left">
								  <thead>
										<tr>
											<th>부피 무게   가로(${takeinOrderList.dimUnit}) * 세로가로(${takeinOrderList.dimUnit}) * 높이(${takeinOrderList.dimUnit})</th>
											<th style="padding:0px;width:90px;">
												<input id="btnAddwtc" tabindex="6" type="button" value="추가">
												<input id="btnDelwtc" tabindex="7" type="button" value="삭제">
											</th>
										</tr>
								  </thead>
								  <tbody>
								  	<tr class="item1">
											<th style="padding:0px;" colspan=2>
												<input class="width" name="width[]" type="number" style="width:80px;"> * 
												<input class="height" name="height[]" type="number" style="width:80px;"> *
												<input class="length" name="length[]" type="number" style="width:80px;"> *
												<input class="per" name="per[]"  type="text" readonly="readonly" style="width:80px;" value="${takeinOrderList.per}">
											</th>
									</tr>
									</tbody>
								</table>
								<br>
								<br>
								<br>
								
								<div style="width:100%;float:left;text-align:right">
									<input id="btnSend" type="button" value="등록">
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
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
			<script type="text/javascript">
			
			function fn_volume(){
				var volume = 0 ;
				var size = $(".width").length;
				var per = $(".per").eq(0).val();

				for ( var i = 0 ; i < size ; i++){
					var width =0.0 ;
					width  = $(".width").eq(i).val();

					var height =0.0 ;
					height  = $(".height").eq(i).val();
				
					var length =0.0 ;
					length  = $(".length").eq(i).val();

					if(width==""){width =0;};
					if(height==""){height =0;};
					if(length==""){length =0;};

					if(width*height*length>0){
						var  thisvolume = 0 ;
						thisvolume = width*height*length/per;
						volume = volume + thisvolume;
					}
				}
				$("#wtc").val(volume.toFixed(2));
			}

		
			
			jQuery(function($) {

				var audio = new Audio();
				$("#stockNo").focus();
		
				$(document).on("change",".width",function(){
					fn_volume();
				});

				$(document).on("change",".height",function(){
					//var index = $(".height").index(this);
					fn_volume();
				});

				$(document).on("change",".length",function(){
					//var index = $(".length").index(this);
					fn_volume();
				});

				searchItem();

				$(document).on("keydown","#boxCnt",function(key){

					if (key.keyCode == 13) {
						$("#wta").focus();
					}
				});
				

				$(document).on("keydown","#wta",function(key){
					if (key.keyCode == 13) {
						$(".width").focus();
					}
				});


				$(document).on("keydown",".width",function(key){
					if (key.keyCode == 13) {
						$(".height").focus();
					}
				});

				$(document).on("keydown",".height",function(key){
					if (key.keyCode == 13) {
						$(".length").focus();
					}
				});

				$(document).on("keydown",".length",function(key){
					if (key.keyCode == 13) {
						$("#btnSend").focus();
					}
				});	
				


				$("#stockNo").keydown(function(key) {
					
					if (key.keyCode == 13) {
						
						if($(this).val()==""){
							return false;
						};
						
						if($(this).val()=="aci_boxcnt"){
							$("#stockNo").val('');
							$("#boxCnt").focus();
							return false;
						};

						if($(this).val()=="aci_enter"){
							$("#btnSend").trigger("click");
							return false;
						};

						var formData = $("#stockForm").serialize();
						$.ajax({
							url:'/mngr/takein/takeInStockIn', 
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

				$("#batchBarcode").on('click',function(key) {
					LoadingWithMask();
					var formData = $("#stockForm").serialize();
					$.ajax({
						url:'/mngr/takein/takeInStockInBatch', 
						type: 'GET',
						timeout: 600000,
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
			            },
			            complete : function() {
			            	$('#mask').hide();
			            }
					})
				});
				
				$("#batchBarcodeTest").on('click', function(e) {
					LoadingWithMask();
					var formData = $("#stockForm").serialize();
					$.ajax({
						url : '/mngr/takein/takeinStockBarcodeBatch',
						type : 'POST',
						data : formData,
						beforeSend : function(xhr) {
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(response) {
							var status = response.rstStatus;
							var msg = response.rstMsg;
							var code = response.rstCode;
							
							var alertMsg = "Result Code : " + code + "\n" + "Result Message : " + msg;
							
							if (status == "FAIL") {
								alert(alertMsg);
							} else {
								audio.src = '/js/pass.mp3';
								audio.play();
							 	searchItem();
							 	$("#stockNo").val('');
							 	$("#stockNo").focus();
							}
						},
						error : function(error) {
							alert("Request failed");
						},
			            complete : function() {
			            	$('#mask').hide();
			            }
					});
				});


				$("#btnAddwtc").on('click',function(e){

					$("#boxCnt").val(parseInt($("#boxCnt").val())+1);

					var lastItemNo = $("#wtcTable tr:last").attr("class").replace("item", "");
	                var newitem = $("#wtcTable tr:eq(1)").clone();

	                newitem.removeClass();
	                newitem.find("td:eq(0)").attr("rowspan", "1");
	                newitem.addClass("item"+(parseInt(lastItemNo)+1));

	                newitem.find(".width").val("");
	                newitem.find(".height").val("");
	                newitem.find(".length").val("");
					
	                $("#wtcTable").append(newitem);

				});

				$("#btnDelwtc").on('click',function(e){
					if($("#wtcTable tbody tr").length == 1){
						return false;
					};
					
					$("#wtcTable tbody tr:last").remove();
					$("#boxCnt").val(parseInt($("#boxCnt").val())-1);
				});
				
				

				$("#btnSend").on('click',function(e){
					if($("#restCnt").val() >0){
						alert('미등록 재고가 존재 합니다.');
						return false;
					};

					if($("#wta").val()==""){
						alert('무게를 등록해주세요.');
						$("#wta").focus();
						return false;
					}
	
					var formData = $("#mgrForm").serialize();
					
					$.ajax({
						url : '/mngr/takein/TakeinHawbIn',
						type : 'POST',
						data : formData,
						success : function(response) {
							
							var Code = response.rstCode;
							
							if(Code =="S10"){
								//alert("정상 처리 되었습니다.");
								location.href = '/mngr/takein/popupTakeinBlScan';
							} else if (Code =="F10") {
								alert("DB 오류가 발생 되었습니다. 데이터를 확인해 주세요.");
								return false;
							}
							
						},
						error : function(xhr, status) {
							alert("등록에 실패 하였습니다.");
						},
						beforeSend : function(xhr) {
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						}
					});
					
					
					//$("#mgrForm").submit();
					
				});
	
			})

			
			function searchItem(){

				$("#takeinItem tbody").empty();
				var formData = $("#searchForm").serialize();

				$.ajax({
					url:'/mngr/takein/takeInOrderItemJson',
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
							row = row + '<td style="font-weight:bold;font-size: 30px;text-align:right">'+value.whOutCnt+'</td>';
							row = row + '</tr>';
							restCnt =  restCnt+parseInt(value.itemCnt-value.whOutCnt);
							total_wta  = total_wta+parseFloat(value.itemWta*value.whOutCnt);
							total_wtc  = total_wtc+parseFloat(value.itemWtc*value.whOutCnt);
						});

						$("#wta").val(total_wta);
						$("#wtc").val(total_wtc);
						
						$("#restCnt").val(restCnt);

						if(restCnt ==0){
							//$("#boxCnt").focus();
						}
							
		                $("#takeinItem").append(row);

		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}
			
			function LoadingWithMask() {
			    //화면의 높이와 너비를 구합니다.
			    var maskHeight = $(document).height();
			    var maskWidth  = window.document.body.clientWidth;
			     
			    //화면에 출력할 마스크를 설정해줍니다.
			    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
			  
			    //화면에 레이어 추가
			    $('body')
			        .append(mask)
			        
			    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
			    $('#mask').css({
			            'width' : maskWidth
			            ,'height': maskHeight
			            ,'opacity' :'0.3'
			    });
			  
			    //마스크 표시
			    $('#mask').show();  
			    //로딩중 이미지 표시
			}
					
		</script>
		<!-- script addon end -->

	</body>
</html>
