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



        .imgs_wrap {

            border: 2px solid #A8A8A8;
            margin-top: 30px;
            margin-bottom: 30px;
            padding-top: 10px;
            padding-bottom: 10px;

        }
        .imgs_wrap img {
            max-width: 150px;
            margin-left: 10px;
            margin-right: 10px;
        }
        
        .profile-info-name{
        	font-weight: bold;
        }
        .profile-info-value{
        	word-break:break-all;
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
        
        .housCntFont{
        	width:40px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:20pt !important; 
        	color:#000 !important;
        	text-align: center;
        }
        
        .real-red{
        color:red !important;
        }
        
        .profile-info-name{
        	border-right: 1px dotted #D5E4F1;
        }
        
        .wfInput{
        	background-color: rgb(255,227,227);
        }
        
        .woInput{
        	background-color: rgb(227,255,227);
        	
        }

    </style>
	
	
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>

	<style type="text/css">
	.colorBlack {
		color: #000000 !important;
	}
	
	.bottom0 {
		margin-bottom:5px;
	}
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
	<title>입고 작업</title>
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
								입고 작업
							</li>
							<li class="active">입고 스캔 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								입고 스캔 작업
							</h1>
						</div>
						<div id="inner-content-side">
							<form id="weightForm" name="weightForm">
								<div class="row">
									<div class="col-xs-12 col-sm-4">
										<div class="col-xs-12">
											<input id="hawbIn" name="hawbIn" type="text" style="font-size:50px;width:100%;"/>
										</div>
										<div class="col-xs-12">
											<input id="hawbResult" class="blue" type="text" readOnly style="font-size:50px;width:100%;" value=""/>
										</div>
										<div class="col-xs-12 align-right">
											<label class="bigger-200">등록 건수 :</label><label class="bigger-200" id="currentIn"></label><label class="bigger-200">건</label>
										</div>
									</div>
								</div>
								<div id="test" name="test">
								
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
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<script src="/js/jquery.sound.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
		var count=0;
			jQuery(function($) {
				$(document).ready(function() {
					$("#4thMenu").toggleClass('open');
					$("#4thMenu").toggleClass('active'); 
					$("#4thTwo").toggleClass('active');
					$("#hawbIn").focus();
				});

				$("#hawbIn").keydown(function (key) {
					if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
						var hawbNo;
						$("#hawbIn").attr("readonly", true);
						$("#test").empty();
						var audio = new Audio();
						/* var string=$("#hawbIn").val();
						var no=string.replace(/[^0-9]/g,'');
						$("#hawbIn").val(no) */
						if($("#hawbIn").val().substring(0,1) == 'A'){
							if($("#hawbIn").val().substring($("#hawbIn").val().length-1,$("#hawbIn").val().length) == 'A'){
								hawbNo = $("#hawbIn").val().substring(1,$("#hawbIn").val().length-1);
								$("#hawbIn").val(hawbNo);
							}else{
								hawbNo = $("#hawbIn").val();
							}
						}else{
							hawbNo = $("#hawbIn").val();
						}
						
						if(hawbNo==""){
							audio.src = '/js/stop.mp3';
							audio.play();
							$("#hawbIn").attr("readonly", false);
							$("#hawbResult").removeClass("blue");
							$("#hawbResult").addClass("red");
							$("#hawbResult").val("잘못된 송장번호 입니다.");
							$("#hawbIn").val("");
							$("#hawbIn").focus();
				            return false;
				        }
						
						$.ajax({
							url:'/mngr/order/orderRcptRegist',
							type: 'POST',
							data: {hawbIn : hawbNo},
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {

								/* alert(data.result);
								alert(data.result.substring(0,1)); */

		                        var result = data.result;
		             	        var resultNno = data.hawbNo;

								if(result=="S"){
									$.ajax({
										url:'/mngr/order/weightForm',
										type: 'GET',
										data: {hawbIn : hawbNo , nno :  resultNno },
										beforeSend : function(xhr)
										{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
										    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
										},
										success : function(innerdata) {
											audio.src = '/js/pass.mp3';
											audio.play();
											$("#hawbResult").val("");
											
											$("#test").html(innerdata);
							            }, 		    
							            error : function(xhr, status) {
							                alert(xhr + " : " + status);
							                alert("페이지 로드에 실패 하였습니다.");
							            }
									});
									
						        }else if(result=="F"){
						        	$("#hawbIn").attr("readonly", false);
						        	audio.src = '/js/stop.mp3';
									audio.play();
						        	alert("등록에 실패하였습니다. 송장번호를 확인해 주세요");
						        	$("#hawbResult").removeClass("blue");
									$("#hawbResult").addClass("red");
									$("#hawbResult").val("등록 실패");
									$("#hawbIn").val("");
									$("#hawbIn").focus();
						        }else{
									audio.src = '/js/beep11.wav';
									audio.play();
						        	$("#hawbIn").attr("readonly", false);
						        	$("#hawbResult").removeClass("blue");
									$("#hawbResult").addClass("red");
						        	$("#hawbResult").val(data.result);
						        	$("#hawbIn").val("");
						        	$("#hawbIn").focus();
						        }
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다.");
				            }
						});

						
				       /*  
						 */
				        /* searchDetail(); */
					}
				});
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
