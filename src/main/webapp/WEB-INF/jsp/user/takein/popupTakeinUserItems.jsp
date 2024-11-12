<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
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
								User 사입 상품 리스트
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="" method="get">
								<table class="table table-bordered" style="width:320px;">
								  <thead>
									<tr>
										<th class="center colorBlack"  style="width:100px;">UserID</th>
										<td style="padding:0px;width:200px;">
											<input id="userId" name="userId" class="onlyEN" style="width:100%" type="text" value="${parameterInfo.userId}" readonly>
										</td>
										<!-- <td style="text-align:center;padding:0px;vertical-align: middle">
										 <input type="button" value="조회"> 
										</td> -->
									</tr>
									</thead>
								</table>
							</form>
							
							<table class="table table-bordered" style="">
								<thead>
									<tr>
									    <th>USER ID</th>
										<th>고객상품코드</th>
										<th>상품명</th>
										<th>상품 옵션</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cusItemList}" var="cusItemList" varStatus="status">
										<tr>
										    <td>${cusItemList.userId}</td>
											<td>${cusItemList.cusItemCode}</td>
											<td>${cusItemList.itemDetail}</td>
											<td>${cusItemList.itemOption}</td>
											<td>
											   <input type="button" onclick="fn_cusItemReg('${cusItemList.cusItemCode}','${cusItemList.userId}');" value="등록">
											</td>
										</tr>
									</c:forEach>
								</tbody>
								
							</table>
							
							<br>
							<table style="width:100%">
								<tfoot>
									<tr>
										<td colspan=6 style="text-align: right;">
										  	 <input type="button" onclick="fn_close()" value="닫기">
										</td>
									</tr>
								</tfoot>
							</table>
					
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

			function fn_close(){
				self.close(); 
			}
		
			function fn_cusItemReg(userId,cusItemCode){
				opener.parent.fn_takeinCodeIn(userId,cusItemCode);	

			}

			
		</script>

	  	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#whInDate" ).datepicker({
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
