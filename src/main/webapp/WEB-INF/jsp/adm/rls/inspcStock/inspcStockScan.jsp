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
	<title>재고 출고 취소</title>
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
							<li class="active">재고 출고 취소</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								재고 출고 취소
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
								</div>
							</div>
							
							<div class="space-20"></div>
						</div>
						</form>
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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
		var cnt = 2;
		var alreadyIn = '';
			jQuery(function($) {
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thSvn").toggleClass('active');
					$("#stockIn").focus();
				});
			});

			$("#registBtn").on('click',function(){
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
						if(rtnVal2 == "S"){
							alert("등록 되었습니다.");
							location.href = "/mngr/rls/inspcStockOut";
						}else{
							alert(rtnVal2);
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
						var list = { stockNo: targetStockNo,
									 /* alreadyInfo : $("#already").val(), */
									}
						$.ajax({
							type : "POST",
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							url : "/mngr/rls/cancleInspStockScan",
							data : list,
							error : function(){
								alert("subpage ajax Error");
							},
							success : function(rtnVal){
								if(rtnVal == "S"){
									showDetail(targetStockNo);
									$("#stockResult").css("font-size","50px");
									$("#stockResult").val("a");
									$("#stockResult").addClass("blue");
									$("#stockResult").removeClass("red");
									$("#stockResult").val("취소되었습니다.");
									$("#stockIn").val("");
									$("#stockIn").focus();
								}else{
									$("#stockResult").css("font-size","20px");
									alert(rtnVal.qryRtn.rstMsg);
									$("#stockResult").addClass("red");
									$("#stockResult").removeClass("blue");
									$("#stockResult").val("a");
									$("#stockResult").val("취소에 실패하였습니다.");
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
						$("#itemCountList")	.html(rtnVal2);
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
						}else{
							$("#box_weight_div").hide();
						}
					}
				})
			}
			
		</script>
		<!-- script addon end -->
		
	</body>
</html>
