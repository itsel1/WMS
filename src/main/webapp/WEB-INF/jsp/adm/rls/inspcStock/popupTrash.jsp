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
								반품
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
								<table class="table table-bordered" style="width:330px;">
								   <thead>
									<tr>
										<th>User ID</th>
										<td style="background: #fff;">${inspStockList.userId}</td>
									</tr>
								    <tr>
										<th style="width:100px;">OrderNo</th>
										<td style="background: #fff;">
											${inspStockList.orderNo}
											</td>
									</tr>
									<tr>
										<th >HawbNo</th>
										<td style="background: #fff;">${inspStockList.hawbNo}</td>
									</tr>
									<tr>
										<th>Total 수량</th>
										<td style="background: #fff;">${inspStockList.stockCnt}</td>
									</tr>
									</thead>
							</table>

							<table class="table table-bordered" style="border-top: 1px solid #ddd;width:40%;background-color: rgba( 255, 255, 255, 0.5 );">
								<thead>
									<tr>
										<th>상품명</th><th>수량</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${groupSubNoStockDetail}" var="groupSubNoStockDetailInfo" varStatus="status">
									<tr>
										<td style="text-align: left;">${groupSubNoStockDetailInfo.itemDetail}</td>
										<td style="text-align: left;width:130px;">${groupSubNoStockDetailInfo.unitCnt}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						
							<form id="mgrForm" action="" method="post">
								<input type="hidden" name="groupIdx" value="${inspStockList.groupIdx}">
								<input type="hidden" name="orgStation" value="${inspStockList.orgStation}">
								<input type="hidden" name="userId" value="${inspStockList.userId}">
								<input type="hidden" name="disposalType" value="TR">
								<input type="hidden" id="trkNo" name="trkNo" value="" style="width:100%">
								<input type="hidden" id="trkCom" name="trkCom" value="" style="width:100%">
								<table class="table table-bordered" style="width:330px;">
									<thead>
										<tr>
											<th class="center">Date</th>
											<td style="text-align: left;padding:0px;"><input id="trkDate" id="trkDate" readonly type="text" name="trkDate" value="" style="width:100%"></td>
										</tr>
										<tr>
											<th class="center">폐기비용</th>
											<td style="text-align: left;padding:0px;"><input type="number" id="chgAmt" name="chgAmt" value="" style="width:100%"></td>
										</tr>
										<tr>
											<td colspan="2" style="background: #fff;text-align:right">
													<input id="sendBtn" type="button" value="등록">
											</td>
										</tr>
									</thead>
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

				$("#sendBtn").on('click',function(e){
			
					if($("#trkDate").val()==""){
						alert('일자 를 입력해주세요');
						$("#trkDate").focus();
						return false;
					}


					if($("#chgAmt").val()==""){
						alert('금액을 입력해주세요');
						$("#chgAmt").focus();
						return false;
					}
					
					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/comn/popupReturnJson',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								opener.parent.location.reload();
								window.close();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
				
			})
		</script>
		<!-- script addon end -->
	  	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#trkDate" ).datepicker({
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
