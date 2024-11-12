<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
		
		.colorBlack { color:black !important; }
		
		.padding0 {
			padding: 0px !important;
		}
		
		.colorBlack { 
			color: #000000 !important;
		}
		
		.table>tbody>tr>td {
			vertical-align: middle !important;
		}
		
		.profile-info-name {
			padding: 0px;
			color: black !important;
			text-align: center !important;
		}
		
		.profile-user-info-striped {
			border: 0px solid white !important;
		}
		/* .profile-user-info-striped{border: 0px solid white !important;}
			  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
		.profile-user-info {
			width: 100% !important;
			margin: 0px;
		}
		
		/* .col-xs-12 .col-sm-12 {
			padding: 0px !important;
		}
		 */
		.tdBackColor {
			background-color: #FFFFFF;
		}
		
		.tdHeader {
			border: 1px solid black;
			padding: 0px !important;
		}
		
		.tdHeaderB {
			border: 1px solid black;
			border-bottom: none;
		}
		
		.tdHeaderT {
			border: 1px solid black;
			border-top: none;
		}
		
		.tdHeaderL {
			border: 1px solid black;
			border-left: none;
			border-right: none;
		}
		
		.tdHeaderR {
			border: 1px solid black;
			border-right: none;
			border-left: none;
		}
		
		.tdHeaderTR {
			border: 1px solid black;
			border-top: none;
		}
		
		@media ( min-width :768px) {
			.tdHeaderB {
				border: 1px solid black;
				border-bottom: none;
			}
			.tdHeaderT {
				border: 1px solid black;
				border-top: none;
			}
			.tdHeaderL {
				border: 1px solid black;
				border-left: none;
			}
			.tdHeaderR {
				border: 1px solid black;
				border-right: none;
			}
			.tdHeaderTR {
				border: 1px solid black;
				border-right: none;
				border-top: none;
			}
		}
		
		@media only screen and (max-width:480px) {
			.hidden-480 {
				display: none !important;
			}
		}
		
		@media only screen and (max-width:480px) {
			.hidden-489 {
				display: inline!important;
			}
		}
		
		#searchBtn {
			border:none;
			height:36px;
			width:100%;
			font-weight:bold;
			color:#fff;
			background:rgba(52, 122, 183, 0.9);
		}
		
		#searchBtn:hover {
			font-weight:bold;
			background:rgba(52, 122, 183, 0.7);
		}
		
		#text_label {
			font-family: 'Nanum Gothic', sans-serif;
			font-weight: 700;
			color: #428BCA;
			font-size: large;
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
	<title>사입</title>
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
								사입
							</li>
							<li class="active">출고 대기</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								사입 출고 대기 리스트
							</h1>
						</div>
						<div style="height:230px; padding-top:10px;margin-left:20px;">
							<label id="text_label"><!-- * 운송장 번호 스캔 또는 입력 후 엔터 -->
								<small class="red">
									※ 운송장 번호 스캔 또는 입력 후 엔터 클릭 시 출고 취소 처리
								</small>
							</label>
							<form id="mainForm" name="mainForm">
								<div style="width:40%;margin-top:10px;">
									<input type="text" name="hawbNoIn" id="hawbNoIn" style="font-size:50px;width:100%;">
								</div>
								<div style="width:40%;">
									<input type="text" id="stockResult" style="font-size:50px;width:100%;height:88px;color:red;" disabled>
								</div>
							</form>
						</div>
						<div style="width:100%;">
							<iframe src="/mngr/takein/takeinCancelDetail" frameborder="0" id="iframe" style="width:100%;height:750px;"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.main-content -->
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
	
		<!-- script addon end -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#5thMenu").toggleClass('open');
					$("#5thMenu").toggleClass('active');
					$("#cancelTakein").toggleClass('active');
				})
				
				$("#hawbNoIn").keydown(function(key) {
					if (key.keyCode == 13) {
						var targetHawbNo = $(this).val();

						$.ajax({
							url : '/mngr/takein/cancelTakeinOrder',
							type : 'POST',
							beforeSend : function(xhr)
							{   
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data : {hawbNo : targetHawbNo},
							success : function(data) {
								if (data == 'S') {
									$("#stockResult").css("font-size","50px");
									$("#stockResult").val("a");
									$("#stockResult").addClass("blue");
									$("#stockResult").removeClass("red");
									$("#stockResult").val("출고취소 처리 되었습니다.");
									$("#stockIn").val("");
									$("#stockIn").focus();
									var url = $("#iframe").attr('src');
									$("#iframe").attr('src', url);
								} else if (data == 'N') {
									$("#stockResult").css("font-size","40px");
									$("#stockResult").val("a");
									$("#stockResult").addClass("blue");
									$("#stockResult").removeClass("red");
									$("#stockResult").val("존재하지 않는 송장번호 입니다.");
									$("#stockIn").focus();
								} else {
									$("#stockResult").css("font-size","50px");
									$("#stockResult").val("a");
									$("#stockResult").addClass("red");
									$("#stockResult").removeClass("blue");
									$("#stockResult").val("출고 취소에 실패 하였습니다.");
									$("#stockIn").val("");
									$("#stockIn").focus();
								}
							},
							error : function(xhr, status) {
								$("#stockResult").css("font-size","50px");
								$("#stockResult").val("a");
								$("#stockResult").addClass("red");
								$("#stockResult").removeClass("blue");
								$("#stockResult").val("출고 취소에 실패 하였습니다.");
								$("#stockIn").val("");
								$("#stockIn").focus();
							}
						})
					}
				})

			})			
		</script>
		
	</body>
</html>
