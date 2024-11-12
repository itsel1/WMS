<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<style type="text/css">
		input[type=file] {
            display: none;
        }

        .my_button {
            display: inline-block;
            width: 200px;
            text-align: center;
            padding: 10px;
            background-color: #006BCC;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        .verticalM{
        	vertical-align: middle !important;
        }
        
        .boxFont{
        	width: 80px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	background-color:#F5F6CE !important;
        	font-size:40pt !important;
        	font:bold !important;
        	color:#000000 !important;
        }
        
        .sizeFont{
        	width:60px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:15pt !important; 
        	background-color:#F5F6CE !important;
        	font:bold !important;
        	color:#000 !important;
        }
        
        .real-red{
        color:red !important;
        }
        
	
		input[disabled].chgVal {
			background-color: rgb(255,255,202) !important;
			text-align: center;
			font-weight: bold;
		}
		input[disabled].Val {
			background-color: white !important;
			text-align: center;
			font-weight: bold;
		}
		input[disabled].borderNone {
			border: 0px;
		}
		input[disabled].redbg {
			background-color: rgb(255,237,237) !important;
			text-align: center;
			font-weight: bold;
		}
		input[disabled].greenbg {
			background-color: rgb(237,255,237) !important;
			text-align: center;
			font-weight: bold;
		}
		
		input[disabled].red {
			color : #DD5A43 !important;
		}
		
		input[disabled].blue {
			color : #478FCA !important;
		}
		
		button.Val {
			background-color: white !important;
			text-align: center;
			font-weight: bold;
		}
		button.borderNone {
			border: 0px;
		}
		
		select option[value=""][disabled] {
			display :none;
		}

	</style>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>
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
	<title>반품 재고 출고</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								출고
							</li>
							<li class="active">반품 재고 출고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content" style="width:70%;">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
						<form id="searchForm">
							<input type="hidden" name="nno" value="${returnInfo.nno}">
							<input type="hidden" name="userId" value="${returnInfo.sellerId}">
							<input type="hidden" name="orderReference" value="${returnInfo.orderReference}"/>
						</form>
						<br>
						<table class="table table-bordered">
						  <thead>
						 	<tr>
							   <th class="center" style="width:120px;">KOBL NO</th>
							   <th style="background-color:#ffffff;color: #393939;">${returnInfo.koblNo}</th>
							</tr>
							<tr>
								<th class="center">도착지</th>
								<th style="background-color:#ffffff;color: #393939;">${returnInfo.dstnNationName} (${returnInfo.dstnNation})</th>
							</tr>
							<tr>
							   <th class="center">수취업체 명</th>
							   <th style="background-color:#ffffff;color: #393939;">${returnInfo.senderName}</th>
							</tr>
							<tr>
							   <th class="center">수취업체 주소</th>
							   <th style="background-color:#ffffff;color: #393939;">${returnInfo.senderAddr} ${returnInfo.senderAddrDetail}</th>
							</tr>
							<tr>
							   <th class="center">주문번호</th>
							   <th style="background-color:#ffffff;color: #393939;">${returnInfo.orderNo}</th>
							</tr>
							</thead>
						</table>
						<form id="stockForm" onsubmit="return false" action="" method="get">
						    <input type="hidden" name="userId" value="${returnInfo.sellerId}">
					    	<input type="hidden" name="orgStation" value="${returnInfo.orgStation}">
					    	<input type="hidden" name="nno" value="${returnInfo.nno}">
						    <button type="button" id="batchBarcode" name="batchBarcode" class="btn btn-primary">바코드 일괄적용</button>
						    <br/>
						    <br/>
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
						  
						 <form id="mgrForm" action="" method="get">
						 
						 	    <input type="hidden" name="userId" value="${returnInfo.sellerId}">
						    	<input type="hidden" name="orgStation" value="${returnInfo.orgStation}">
						    	<input type="hidden" name="nno" value="${returnInfo.nno}">
						    	<input type="hidden" name="orderReference" value="${returnInfo.orderReference}"/>
						    	<input type="hidden" name="wtUnit" value="${orderInfo.wtUnit}">
						    	<input type="hidden" name="dimUnit" value="${orderInfo.dimUnit}">
						    	<input type="hidden" name="per" value="${orderInfo.per}">
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
								<table class="table table-bordered" style="width:80px;float:left;">
								  <thead>
										<tr>
											<th class="center" style="width:80px;">Box 수량</th>
										</tr>
										<tr>
											<th style="padding:0px;"><input id="boxCnt" name="boxCnt" type="number" style="width:80px;text-align:center" value="1"  /></th>
										</tr>
								  </thead>
								</table>
									
								<table class="table table-bordered" style="width:250px;float:left;margin-left:15px;">
								  <thead>
								 		<tr>
											<th class="center">실무게 <small>${orderInfo.wtUnit}</small></th>
											<th class="center">부피 무게 <small>${orderInfo.wtUnit}</small></th>
										</tr>
										<tr>
											<th style="padding:0px;"><input id="wta" name="wta" type="number" style="width:100%;" value="${orderInfo.wta}"/></th>
											<th style="padding:0px;"><input id="wtc" name="boxWeight" type="number" style="width:100%;"  readonly="readonly"/></th>
										</tr>
								  </thead>
								  
								</table>

								<table id="wtcTable" class="table table-bordered" style="width:560px;margin-left:15px; border:1px solid balck;float:left">
								  <thead>
										<tr>
											<th>부피 무게  가로<small>(${orderInfo.dimUnit})</small> * 세로<small>(${orderInfo.dimUnit})</small> * 높이<small>(${orderInfo.dimUnit})</small></th>
											<th style="padding:0px;width:90px;">
												<input id="btnAddwtc" tabindex="6" type="button" value="추가">
												<input id="btnDelwtc" tabindex="7" type="button" value="삭제">
											</th>
										</tr>
								  </thead>
								  <tbody>
								  	<tr class="item1">
											<th style="padding:0px;" colspan=2>
												<%-- 
												<input class="width" id="width1" name="width[]" type="number" style="width:80px;"> * 
												<input class="height" name="height[]" type="number" style="width:80px;"> *
												<input class="length" name="length[]" type="number" style="width:80px;"> *
												<input class="per" name="per[]"  type="text" readonly="readonly" style="width:80px;" value="${orderInfo.per}">
												 --%>
												<input class="width" id="width1" style="width:80px;" name="width" type="number" tabindex="1"/> * 
												<input class="height" id="height1" style="width:80px;" name="height" type="number" tabindex="1"/> *
												<input class="length" id="length1" style="width:80px;" name="length" type="number" tabindex="1"/> / 
												<input class="per" id="per1" name="per" style="width:80px;" type="text" value="${orderInfo.per}" tabindex="1"/>
											</th>
									</tr>
									</tbody>
								</table>
								<br>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th colspan="4">선택 사항</th>
										</tr>
										<tr>
											<th class="center">배송업체</th>
											<th style="padding:0px; background-color:#ffffff;">
												<select id="transCode" name="transCode" style="width:100%; height:36px; font-weight:normal;">
													<option value="" disabled selected>select option</option>
													<c:forEach items="${transList}" var="transList">
														<option value="${transList.transCode}">${transList.transCode} / ${transList.nationCode}</option>
													</c:forEach>
												</select>
											</th>
											<th class="center">HAWB NO (EMS)</th>
											<th style="padding:0px; background-color:#ffffff; font-weight:normal;">
												<input type="text" name="hawbNo" style="width:100%; height:36px;">
											</th>
										</tr>
									</thead>
								</table>
								<div style="width:100%;float:left;text-align:right">
									<input id="btnSend" type="button" value="등록">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
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
		<script src="/js/jquery.sound.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {

				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
	           		$("#14thMenu-2").toggleClass('active'); 
	           		$("#14thTwo-3-1").toggleClass('active');   
	           		
           		});
           		
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

						if($(this).val()=="aci_enter"){
							$("#btnSend").trigger("click");
							return false;
						};
						
						var formData = $("#stockForm").serialize();
						$.ajax({
							url:'/mngr/aplctList/return/InspStockIn', 
							type: 'GET',
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data: formData, 
							success : function(data) {
								var Status = data.qryRtn.rstStatus;
								var Code = data.qryRtn.rstCode;

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
								
								$("#rstMsg").text(data.qryRtn.rstMsg);
								 searchItem();
								 $("#stockNo").val('');
								 $("#stockNo").focus(); 
								
				            }, 		    
				            error : function(request,status,error) {
								console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								alert("데이터 등록 실패");
							}
						})
					};
				});


					$("#batchBarcode").on('click',function(key) {
						LoadingWithMask();
						var formData = $("#stockForm").serialize();
						$.ajax({
							url:'/mngr/aplctList/return/stockInBatch', 
							type: 'GET',
							timeout: 600000,
							beforeSend : function(xhr)
							{   
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data: formData, 
							success : function(data) {
								console.log(data);

								if (data.result == "S") {
									var Status = data.qryRtn.rstStatus;
									var Code = data.qryRtn.rstCode;
									
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
									
									 $("#rstMsg").text(data.qryRtn.rstMsg);
									 searchItem();
									 $("#stockNo").val('');
									 $("#stockNo").focus();
										
								} else {
									alert("등록에 실패하였습니다.");
									return false;
								}
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
				console.log(formData);
				$.ajax({
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					url : "/mngr/aplctList/return/inspListOut/registReturnStockOutVolume",
					error : function(xhr, status) {
						alert("subpage ajax Error");
					},
					success : function(rtnVal2){
						if(rtnVal2.rstStatus == "SUCCESS"){
							alert("등록 되었습니다.");
							$("#nno").val(rtnVal2.outNno);
							window.open("/comn/printHawb?targetInfo="+$("#nno").val()+"&formType="+rtnVal2.rstTransCode,"popForm","_blank");
							location.href = "/mngr/aplctList/return/returnSuccessList"; 
						}else{
							alert(rtnVal2.rstMsg);
						}
					}
				})
				
			});

			

			function searchItem(){

				$("#takeinItem tbody").empty();
				var formData = $("#searchForm").serialize();

				$.ajax({
					url:'/mngr/aplctList/return/inspStockInJson',
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
							row = row + '<td style="text-align:center;font-weight:bold;font-size: 30px;">'+value.CUS_ITEM_CODE+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;">'+value.ITEM_DETAIL+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;text-align:right">'+value.ITEM_CNT+'</td>';
							row = row + '<td style="font-weight:bold;font-size: 30px;text-align:right">'+value.WH_OUT_CNT+'</td>';
							row = row + '</tr>';
							restCnt =  restCnt+parseInt(value.ITEM_CNT-value.WH_OUT_CNT);
							if (value.WT_UNIT == 'g') {
								total_wta = total_wta+parseFloat(value.ITEM_WTA/1000*value.WH_OUT_CNT);
							} else {
								total_wta  = total_wta+parseFloat(value.ITEM_WTA*value.WH_OUT_CNT);	
							}
							total_wtc  = total_wtc+parseFloat(value.ITEM_WTC*value.WH_OUT_CNT);
						});

						$("#wta").val(total_wta);
						$("#wtc").val(total_wtc);
						
						$("#restCnt").val(restCnt);

						if(restCnt ==0){
							//$("#boxCnt").focus();
							$("#wta").focus();
						}
							
		                $("#takeinItem").append(row);

		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}

			
			function fn_volume() {
				var volume = 0;
				var size = $(".width").length;
				var per = $(".per").eq(0).val();

				for (var i = 0; i < size; i++) {
					var width = 0.0;
					width = $(".width").eq(i).val();

					var height = 0.0;
					height = $(".height").eq(i).val();

					var length = 0.0;
					length = $(".length").eq(i).val();

					if(width == "") {
						width = 0;
					}
					if(height == "") {
						heigh = 0;	
					}
					if(length == "") {
						length = 0;
					}

					if(width*height*length>0) {
						var thisVolume = 0;
						thisVolume = width*height*length/per;
						volume = volume + thisVolume;
					}
				}

				$("#wtc").val(volume.toFixed(2));
				
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
