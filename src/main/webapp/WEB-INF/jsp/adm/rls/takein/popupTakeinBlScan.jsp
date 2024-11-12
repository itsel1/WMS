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
								사입코드 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
						<form id="hawbOutForm" action="/mngr/takein/takeinOrderDetail" method="get">
							<input type="hidden" id="searchHawbNo" name="hawbNo" value="">
						</form>
						
						
						 <form id="mgrForm" action="" method="get">
								<table class="table table-bordered" style="width:100%;margin-top:10%;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style=""><span style="padding:0;color:red">*</span> HawbNo</th>
										<td style="padding:0px;height:80px;">
											<input id="hawbNo" name="hawbNo" class="onlyEN"  style="width:100%;height:80px;font-size:30px;" type="text" value="">
										</td>
									</tr>
									</thead>
									<tbody>
										<tr>
											<td style="vertical-align:middle;" colspan=2>
												<p id="errorMsg" style="margin-top:30px;vertical-align:middle;height:80px;text-align:center;color:red;font-size:30px;font-weight:bold">
												
												</p>
											</td>
										</tr>
										<tr>
											<td style="vertical-align:left;" colspan=2>
												<p id="errorMsg" style="margin-top:30px;vertical-align:middle;height:80px;text-align:left;font-size:30px;font-weight:bold">
													작업 건수 [ ${Rst.workCnt}] 
												</p>
											</td>
										</tr>
									</tbody>
								</table>
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

				var audio = new Audio();

				$("#hawbNo").focus();

				$(".onlyEN").keyup(function(event){ 
					 if (!(event.keyCode >=37 && event.keyCode<=40)) {
					    var inputVal = $(this).val();
					    $(this).val(inputVal.replace(/[^a-z0-9 ]/gi,''));
					   } 
			     });

				$("#hawbNo").keydown(function(key) {
					if (key.keyCode == 13) {
						if($(this).val()==""){
							alert('운송장을 등록해주세요');
							return false;
						};

						var formData = $("#mgrForm").serialize();

						$.ajax({
							url:'/mngr/takein/takeInHawbChk',
							type: 'GET',
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data: formData, 
							success : function(data) {

								$("#errorMsg").text(data.rstMsg);
								var Status = data.rstStatus;
								var Code = data.rstCode; 

								if(Code=="F10"){
									audio.src = '/js/stop.mp3';
									audio.play();
								}
											
								if(Status == "SUCCESS"){

									$("#searchHawbNo").val(data.rstHawbNo);
									$("#hawbOutForm").submit();
								};
								
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
				            },
				            complete : function(){
				            	//opener.location.reload();
				            } 
						})
						return false;					
					}
				});
			})
		</script>
	</body>
</html>
