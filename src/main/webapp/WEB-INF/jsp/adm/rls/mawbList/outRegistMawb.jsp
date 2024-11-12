<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:80px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	
	</style>
	
	</head>
	<title>Warehouse 입고</title> 
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
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
								Warehouse
							</li>
							<li class="active">Warehouse POD</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								Warehouse POD
							</h1>
						</div>
						<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
						<div id="inner-content-side">
							<div class="row no-gutters">
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-12 ">
										<label style="font-size:100px;">Warehouse POD : </label>
									</div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-12 ">
										<input type="text" class="inStyle" id="target" name="target"/>
									</div>
									<div class="col-xs-12 col-sm-12 ">
										<input type="text" class="inStyle real-blue" id="targetResult" readonly value=""/>
									</div>
								</div>
							</div>
							<div class="space-20"></div>
						</div><!-- /#home -->
					</div>
				</div>
			</div>
		</div>
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!-- script addon start -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		
		
		<script src="/assets/js/moment.min.js"></script>
		
		<script src="/js/jquery.sound.js"></script>
		
		
		<script type="text/javascript">
			var count = 0;
			$("#target").focus();
			jQuery(function($) {
				$(document).ready(function() {
					$("#13thMenu").toggleClass('open');
					$("#13thMenu").toggleClass('active'); 
					$("#13thTwo").toggleClass('active');
				});
			})

				$("#target").keydown(function (key) {
					if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
						var string=$("#target").val();
						var no=string.replace(' ','');
						$("#target").val(no)
						var trkNo = $("#target").val();
						var audio = new Audio();
						if(trkNo==""){
							audio.src = '/js/stop.mp3';
							audio.play();
							$("#targetResult").removeClass("real-blue");
							$("#targetResult").addClass("real-red");
							$("#targetResult").val("잘못된 송장번호 입니다.");
							$("#target").val("");
							$("#target").focus(); 
				            return false;
				        }
						$.ajax({
							url:'/mngr/rls/mawbHawbInfoChk',
							data:{target: trkNo},
							type: 'POST',
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {  
							
								var status = data.result;
								if(status=="SUCCESS"){
									audio.src = '/js/pass.mp3';
									audio.play();
									$("#targetResult").addClass("real-blue");
									$("#targetResult").removeClass("real-red");
									$("#targetResult").val(status);
									count++;
									window.open("/mngr/rls/mawbWareOut?target="+trkNo,"_blank", "width=660, height=700", "toolbar=no","resizable=yes","directories=no",);
									$("#currentIn").text(count);
									$("#target").val("");
									$("#target").focus();
								}else{
						        	audio.src = '/js/stop.mp3';
									audio.play();
						        	$("#targetResult").removeClass("real-blue");
									$("#targetResult").addClass("real-red");
									$("#targetResult").val(status);
									$("#target").val("");
									$("#target").focus();
						        }
								
				            }, 		    
				            error : function(xhr, status) {
				            	audio.src = '/js/stop.mp3';
								audio.play();
				                alert("등록에 실패 하였습니다. 등록사항을 확인해 주세요.");
				                $("#targetResult").removeClass("real-blue");
								$("#targetResult").addClass("real-red");
								$("#targetResult").val("등록 실패");
								$("#target").val("");
								$("#target").focus();
				            }
						})
				        
				       /*  if(trkNo=="a"){
							$("#targetResult").removeClass("real-blue");
							$("#targetResult").addClass("real-red");
							$("#targetResult").val("등록 실패");
				        }else{
				        	$("#targetResult").addClass("real-blue");
							$("#targetResult").removeClass("real-red");
							$("#targetResult").val("등록 성공");
							count++;
							$("#target").val("");
							$("#target").focus();
							$("#currentIn").text(count);
				        } */
						/* alert("입고 로직 수행"); */
				        /* searchDetail(); */
					}
				});
		
		</script>
		<!-- script addon end -->
	</body>
</html>
