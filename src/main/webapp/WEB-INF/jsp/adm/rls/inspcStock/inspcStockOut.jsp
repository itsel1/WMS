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
	<title>검품 재고 출고</title>
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
							<li class="active">검품 재고 출고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								검품 재고 출고
							</h1>
						</div>
						<form id="weightForm">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<div class="row">
								<div class="col-xs-12 col-sm-4">
									<div class="col-xs-12">
										<input id="stockIn" name="stockIn" type="text" style="font-size:50px;width:100%;"/>
									</div>
									<div class="col-xs-12">
										<input id="stockResult" class="red" type="text" disabled style="font-size:50px;width:100%;height:88px;" value=""/>
									</div>
									<div class="col-xs-12" style="text-align: right;cursor: pointer !important; ">
										<button type="button" class="borderNone Val" style="text-align: right; color: blue; font-size: large;" id="resetBtn" name="resetBtn">
											데이터 초기화
										</button>
									</div>
								</div>
							</div>
							
							<div class="space-20"></div>
							<div id="itemCountList"> 
								<%-- <div class="row">
									<div class="col-xs-12 col-md-4 bigger-200 center" >
										상품명
									</div>
									<div class="col-xs-6 col-md-3 bigger-200 center" >
										확인 개수
									</div>
									<div class="col-xs-6 col-md-3 bigger-200 center" >
										총 개수
									</div>
								</div>
								<div class="space-10"></div>
								<c:forEach items="${inspStockOut}" var ="stockOut" varStatus="status">
									<div class="row">
										<div class="col-xs-12 col-md-4 bigger-120" >
											<label>${stockOut.itemDetail}</label>
										</div>
										<div class="col-xs-6 col-md-3 center">
											<input type="text" class="borderNone Val" readonly id="${stockOut.subNo}" name="subNoCnt" value="0"/>
										</div>
										<div class="col-xs-6 col-md-3 center">
											<!-- 총 개수 -->
											<input type="text" class="borderNone redbg" id="subNoTotal${stockOut.subNo}" readonly name="subNoTotal" value="${stockOut.itemCnt}"/>
											<!-- 총 개수 -->
										</div>
									</div>
									<div class="space-2"></div>
								</c:forEach>
								<div class="space-10"></div> --%>
							</div>
							
							<div id="box_weight_div" style="display:none;">
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div class="row">
									<div class="col-xs-12 col-sm-5">
										<div class="widget-box transparent" name="nomalField">
											<div class="widget-header widget-header-small">
												<h2 class="widget-title smaller">
													<i class="ace-icon fa fa-check-square-o bigger-170"></i>
													box
												</h2>
											</div>
								
											<div class="widget-body">
												<div class="widget-main">
													<div class="row" >
														<div class="col-xs-12 col-sm-6" >
															<label class="bolder bigger-150 verticalM">box 개수</label>
															<input id="boxCnt" name="boxCnt" class="boxFont" readonly numberOnly size="5" type="text" value="1"/>
															<label class="bolder bigger-150">개</label>
														</div>
														<div class="col-xs-12 col-sm-6">
															<label class="bolder bigger-150 verticalM">실제 무게<br/>W/T(A)</label>
															<input id="boxWeight" name="boxWeight" class="boxFont" floatOnly size="5" type="text" tabindex="1" />
															<label class="bolder bigger-180 light-blue">
															<select id="wtUnit" name="wtUnit" style="color:#93CBF9 !important;height:40px;">
																<option>
																	KG
																</option>
																<option>
																	LB
																</option>
															</select>
															</label>
														</div>
													</div>
													<br/>
													<br/>
													<div class="row">
														<div class="col-xs-12">
															<label style="vertical-align: middle;" class="bolder bigger-180">청구 W/T : </label><label id="" style="vertical-align: middle;" class="bolder bigger-280 real-red"></label>
															<input type="text" id="wta" name="wta" class="bolder bigger-280 real-red" style="background-color: none;border:none;" />
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								
									<div class="col-xs-12 col-sm-7">
										<div class="widget-box transparent" name="nomalField">
											<div class="widget-header widget-header-small header-color-blue2">
												<h2 class="widget-title smaller">
													<i class="ace-icon fa fa-check-square-o bigger-170"></i>
													크기
												</h2>
											</div>
								
											<div class="widget-body">
												<div class="widget-main padding-16" id="volumns" name="volumns">
													<div class="row">
														<div class="col-xs-12 col-sm-12">
															<span class="col-xs-2">
																<label class="bolder bigger-130 verticalM">가로</label><br/>
																<label class="light-grey bolder bigger-130 verticalM ">width</label>
															</span>
															<span class="col-xs-2">
																<label class="bolder bigger-130 verticalM">세로</label><br/>
																<label class="light-grey bolder bigger-130 verticalM ">length</label>
															</span>
															<span class="col-xs-2">
																<label class="bolder bigger-130 verticalM">높이</label><br/>
																<label class="light-grey bolder bigger-130 verticalM ">height</label>
															</span>
															<span class="col-xs-2">
																<label class="bolder bigger-130 verticalM">PER</label><br/>
															</span>
															<span class="col-xs-2">
																<label class="bolder bigger-130 verticalM">값</label><br/>
															</span>
															<span class="col-xs-2">
																<select id="dimUnit" name="dimUnit" style="color:#93CBF9 !important;height:35px;">
																	<option value="CM">
																		CM
																	</option>
																	<option value="IN">
																		INCH
																	</option>
																</select>
																<button class="btn btn-primary btn-white" type="button" id="addBtn" name="addBtn">Add +</button><br/>
															</span>
														</div>
													</div>
													<div class="row" id="div1">
														<div class="col-xs-12 col-sm-12">
															<span class="col-xs-2">
																<input id="width1" name="width" type="text" class="sizeFont" floatOnly tabindex="1"/>*
															</span>
															<span class="col-xs-2">
																<input id="length1" name="length" type="text" class="sizeFont" floatOnly tabindex="1"/>*
															</span>
															<span class="col-xs-2">
																<input id="height1" name="height" type="text" class="sizeFont" floatOnly tabindex="1"/>/
															</span>
															<span class="col-xs-2">
																<input id="per1" name="per" type="text" class="sizeFont" floatOnly value="6000" tabindex="1"/>
															</span>
															<span class="col-xs-2">
																=<input id="result1" name="result" type="text" class="sizeFont" readOnly/><br/>
															</span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12 col-md-11" style="text-align: center;">
										<button type="button" class="btn btn-sm btn-danger" id="cancleBtn" name="cancleBtn">취소</button>
										<button type="button" class="btn btn-sm btn-primary" id="registBtn" name="registBtn">등록</button>
										<button type="button" class="btn btn-sm btn-danger" id="registForceBtn" name="registForceBtn">강제 출고</button>
									</div>
								</div>
							</div>
							<div class="row" id="forceBtn_div" style="display:none;">
								<div class="col-sm-12 col-md-11" style="text-align: center;">
									<button type="button" class="btn btn-sm btn-danger" id="forceBtn" name="forceBtn">강제 출고</button>
								</div>
							</div>
						</div>
						</form>
						
						
						
						<%-- <div class="col-xs-6">
						<br/><br/><br/>
							<table class="table table table-bordered table-hover">
								<thead>
									<tr>
										<th>
											Rack Code
										</th>
										<th>
											Item Detail
										</th>
										<th>
											재고번호
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${stockRack}" var ="stockRackInfo" varStatus="status">
										<tr>
											<td>
												${stockRackInfo.rackCode}
											</td>
											<td>
												${stockRackInfo.itemDetail}
											</td>
											<td>
												${stockRackInfo.stockNo}
											</td>
										</tr>
									</c:forEach>
								</tbody>
								
								
							</table>
						</div> --%>
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
		var cnt = 2;
		var alreadyIn = '';
		jQuery(function($) {
			$(document).ready(function() {
				$("#6thMenu").toggleClass('open');
				$("#6thMenu").toggleClass('active'); 
				$("#6thSix").toggleClass('active');
				showDetail('');
				$("#stockIn").focus();
			});
		});

		$("#forceBtn").on('click',function(){
			if(confirm("요청 수량과 확인 수량이 다릅니다.\n그래도 출고과정을 진행 하시겠습니까?")){
				$("#box_weight_div").show();
				$("#registForceBtn").show();
				$("#registBtn").hide();
				$("#forceBtn").hide();
			}
		})
			
		$("#registForceBtn").on('click',function(){

			if($("#wta").val() == ""){
				alert("실제 무게를 확인해 주세요");
				return false;
			}
			
			var formData = $("#weightForm").serialize();
			$.ajax({
				type : "POST",
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : formData,
				url : "/mngr/rls/registStockOutVolume",
				error : function(){
					alert("subpage ajax Error");
				},
				success : function(rtnVal2){
					if(rtnVal2.rstStatus == "SUCCESS"){
						alert("등록 되었습니다.");
						$("#nno").val(rtnVal2.outNno);
						window.open("/mngr/rls/printInspcHawb?nno="+$("#nno").val(),"popForm","_blank");
						location.href = "/mngr/rls/inspcStockOut";
					}else{
						alert(rtnVal2.rstMsg);
					}
				}
			})
		})

		$("#registBtn").on('click',function(){
			if($("#wta").val() == ""){
				alert("실제 무게를 확인해 주세요");
				return false;
			}
			var formData = $("#weightForm").serialize();
			$.ajax({
				type : "POST",
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : formData,
				url : "/mngr/rls/registStockOutVolume",
				error : function(){
					alert("subpage ajax Error");
				},
				success : function(rtnVal2){
					if(rtnVal2.rstStatus == "SUCCESS"){
						
						alert("등록 되었습니다.");
						$("#nno").val(rtnVal2.outNno);

						if(rtnVal2.rstTransCode == "ARA") {
							window.open("/mngr/rls/printInspcHawb?nno="+$("#nno").val(),"popForm","_blank");
						} else {
							window.open("/comn/printHawb?targetInfo="+$("#nno").val()+"&formType="+rtnVal2.rstTransCode+"&labelType=C","popForm","_blank");	
						}
						location.href = "/mngr/rls/inspcStockOut";
						/*
						if(rtnVal2.rstTransCode == "ARA")
							window.open("/mngr/rls/printInspcHawb?nno="+$("#nno").val(),"popForm","_blank");
						else(rtnVal2.rstTransCode == "YES")
							window.open("/comn/printHawb?targetInfo="+$("#nno").val()+"&formType="+rtnVal2.rstTransCode,"popForm","_blank");
						location.href = "/mngr/rls/inspcStockOut";
						*/
					}else{
						
						alert(rtnVal2.rstMsg);
						
					}
				}
			})

		})
				

		$("#resetBtn").on('click',function(){
			location.href = "/mngr/rls/inspcStockOutReset";

		})
			
		$("#cancleBtn").on('click',function(){
			history.back();
		})
		
			
		$("#stockIn").keydown(function (key) {
				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					var targetStockNo = $(this).val();
					var audio = new Audio();
					var list = { stockNo: targetStockNo,
								 /* alreadyInfo : $("#already").val(), */
								}
					$.ajax({
						type : "POST",
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						url : "/mngr/rls/inspcStockOutChk",
						data : list,
						error : function(){
							alert("subpage ajax Error");
						},
						success : function(rtnVal){
							if(rtnVal.result == "S"){
								audio.src = '/js/pass.mp3';
								audio.play();
								showDetail(targetStockNo);
								$("#stockResult").css("font-size","50px");
								$("#stockResult").val("a");
								$("#stockResult").addClass("blue");
								$("#stockResult").removeClass("red");
								$("#stockResult").val("적용되었습니다.");
								$("#stockIn").val("");
								$("#stockIn").focus();
							}else{
								audio.src = '/js/stop.mp3';
								audio.play();
								$("#stockResult").css("font-size","20px");
								alert(rtnVal.qryRtn.rstMsg);
								$("#stockResult").addClass("red");
								$("#stockResult").removeClass("blue");
								$("#stockResult").val("a");
								$("#stockResult").val(rtnVal.qryRtn.rstMsg);
								$("#stockIn").val("");
								$("#stockIn").focus();
							}
							
						}
					})
				}
		});

		function showDetail(targetStockNo){
			var list = { stockNo: targetStockNo,
						 /* alreadyInfo : $("#already").val(), */
						 }
			$.ajax({
				type : "POST",
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : list,
				url : "/mngr/rls/inspcStockOutDetail",
				error : function(){
					alert("subpage ajax Error");
				},
				success : function(rtnVal2){
					var chk = "F";
					$("#itemCountList").html(rtnVal2);
					$("[name=itemStatus]").each(function(){
						if($(this).val() == "T"){
							chk = "T";
						}else{
							chk = "F";
							return false;
						}
					})
					if(chk == "T"){
						$("#box_weight_div").show();
						$("#registForceBtn").hide();
						$("#forceBtn_div").hide();
						
					}else{
						if($("#nno").val() != ""){
							$("#registForceBtn").show();
							$("#forceBtn_div").show();
						}
						$("#box_weight_div").hide();
					}
				}
			})
		}

		$(document).on("keyup", "input:text[numberOnly]", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });

        $(document).on("keyup", "input:text[floatOnly]", function() {
            $(this).val($(this).val().replace(/[^0-9.]/g,""));
        });

        $(document).on('change', "input[name*='width']",function(e){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "input[name*='length']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change',"input[name*='height']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "input[name*='per']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "#boxWeight",function(){
        	maximum();
        });

        function maximum(){
        	var size = $("input[name*='result']").size();
        	var resultWta = parseFloat($("#boxWeight").val());
        	var resultWtc = 0;
			for(loop=0;loop<size;loop++){	
				resultWtc = parseFloat(resultWtc)+parseFloat($("input[name*='result']").get(loop).value)*1;
			}
	        if(parseFloat(resultWta) < parseFloat(resultWtc)){
	        	$("#wta").val(parseFloat(resultWtc));
	        }else{
	        	$("#wta").val(parseFloat(resultWta));
	        }
			
        };

        function wtCalc(){
            var width = 0;
            var length = 0;
            var height = 0;
            var per = 0;
            var result = 0;
        	var size = $("input[name*='width']").size();
        	for(loop=0;loop<size;loop++){
        		width = $("input[name*='width']").get(loop).value;
        		length = $("input[name*='length']").get(loop).value;
        		height = $("input[name*='height']").get(loop).value;
        		per = $("input[name*='per']").get(loop).value;
        		result = (width*length*height)/per;
        		result = result.toFixed(1);
        		if(result != 0){
        			$("input[name*='result']").get(loop).value = result;
          		}else{
          			$("input[name*='result']").get(loop).value = "";
           		}
           	}
        };

        $("#addBtn").on('click',function(e){
			var addHtml ="";
			console.log(this);
			addHtml += 
			'<div class="row" id="div'+cnt+'">'+
				'<div class="col-xs-12 col-sm-12">'+
					'<span class="col-xs-2">'+
						'<input id="width'+cnt+'" name="width" type="text" class="sizeFont" floatOnly tabindex="1"/>*'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="length'+cnt+'" name="length" type="text" class="sizeFont" floatOnly tabindex="1"/>*'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="height'+cnt+'" name="height" type="text" class="sizeFont" floatOnly tabindex="1"/>/'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="per'+cnt+'" name="per" type="text" class="sizeFont" floatOnly value="6000" tabindex="1"/>'+
					'</span>'+
					'<span class="col-xs-2">'+
						'=<input id="result'+cnt+'" name="result" type="text" class="sizeFont" readOnly/>'+
						'<i class="bigger-190 red fa fa-minus-circle cancleWeight" role="button" onclick="test('+cnt+')" style="position:absolute; margin:10px;"></i>'+
					'</span>'+
				'</div>'+
			'</div>';
/* 
			<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
				<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
			</span> */

            cnt++;
			$("#boxCnt").val($("#boxCnt").val()*1+1);
            $("#volumns").append(addHtml);


		});
        function test(target){
            $("#div"+target).remove();
            $("#boxCnt").val($("#boxCnt").val()*1-1);
		};
			
		</script>
		<!-- script addon end -->
		
	</body>
</html>
