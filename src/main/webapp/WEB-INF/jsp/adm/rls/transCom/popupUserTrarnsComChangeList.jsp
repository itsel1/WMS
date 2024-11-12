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
	<title>WMS 운송사 변경 구간</title>
	</head> 
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<form id="formSearch" action="/comn/transComChange/userTransComChange" method="get">
				<input type="hidden" name="userId" value="${parameterInfo.userId}">
				<input type="hidden" id="dstnNation" name="dstnNation" value="${parameterInfo.dstnNation}">
			</form>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								운송사 변경 구간 설정
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						  <form action="" id="mainForm">
						  <table class="table table-bordered">
						  	<thead>
						  	  <tr>
							  		<th>USER ID</th>
							  		<th  style="padding:0px;" colspan=2>
							  			<input type="text" name="userId" readonly="readonly" value="${parameterInfo.userId}" style="width:100%">
							  		</th>
								  </tr>
								  <tr>
								  <th>
								      무게 기준 
								  </th>
								  <th  colspan="2">
								  		<select id="changeWtType" name="changeWtType" style="width:100%">
								  			<option value="WTA"
								  			<c:if test="${parameterInfo.changeWtType=='WTA'}">selected</c:if>
								  			 >실무게</option>
								  			<option value="WTC"
								  			<c:if test="${parameterInfo.changeWtType=='WTC'}">selected</c:if>
								  			>부피무게</option>
								  			<option value="INWT"
								  				<c:if test="${parameterInfo.changeWtType=='INWT'}">selected</c:if>
								  			>청구무게</option>
							  			</select>
							  		</th>
								  </tr>
						  		 <tr>
						  			<th>국가</th>
						  			<th style="padding:0px;max-width:250px;">
						  				<select id="nationCode" name="nationCode" style="width:100%">
						  					<option value="">::선택::</option>
						  					<c:forEach items="${nationList}" var="nationList">
						  						<option value="${nationList.nationCode}" 
						  							<c:if test="${nationList.nationCode==parameterInfo.dstnNation}">selected</c:if>
						  						>${nationList.nationName}</option>
						  					</c:forEach>
						  				</select>
						  			</th>
						  			<th>from Weight</th>
						  			<th style="padding:0px;">
						  				<input type="number" name="wta" style="width:100%">
						  			</th>
						  			<th>to Weight</th>
						  			<th style="padding:0px;">
						  				<input type="number" name="wta" style="width:100%">
						  			</th>
						  			<th>Weight Unit</th>
						  			<th style="padding:0px;">
						  				<select style="width:100%">
						  					<option>KG</option>
						  					<option>LB</option>
						  				</select>
						  			</th>
						  			<th>배송사</th>
						  			<th style="padding:0px;">
						  				<select style="width:100%">
						  				    <option value="">::선택::</option>
						  					<c:forEach items="${transList}" var="transList">
						  						<option value="${transList.TRANS_CODE}">${transList.TRANS_NAME_REMARK}</option>
						  					</c:forEach>
						  				</select>
						  			</th>
						  			<th>
						  				 <input type="button" value="등록">
						  			</th>
						  		</tr>
						  	</thead>
						  </table>
						  </form>
						  <br>
						  <table class="table table-bordered">
						  	 <thead>
						  	 	<tr>
						  	 		<th style="text-align:center">Station</th>
						  	 		<th style="text-align:center" >도착국가</th>						  	 		
						  	 		<th style="text-align:center" >배송사</th>
						  	 		<th style="text-align:center">from Weight</th>
						  	 		<th style="text-align:center" >to Weight</th>
						  	 		<th style="text-align:center" >Weight Unit</th>
						  	 		<th></th>
						  	 	</tr>
						  	 </thead>
						  	 <tbody>
						  <c:forEach items="${list}" var="list" varStatus="status">
						  	 	<tr>
						  	 		<td style="text-align:center">${list.stationName}</td>						  	 		
						  	 		<td style="text-align:center">${list.nationName}</td>
						  	 		<td style="font-weight:bold;text-align:center">${list.transName}</td>
						  	 		<td style="text-align:right">${list.minWta}</td>
						  	 		<td style="text-align:right">${list.maxWta}</td>
						  	 		<td style="text-align:center;">${list.wtUnit}</td>
						  	 		<td style="text-align:center">
						  	 			<input type="button" class="btn_del" value="삭제" idx="${list.idx}">
						  	 		</td>
						  	 	</tr>
						  	 	</c:forEach>
						  	 </tbody>
						  </table>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js">
		
		</script>
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
		<script type="text/javascript">
		jQuery(function($) {


			$("#nationCode").on('change',function(e){
					//alert($(this).val());
					$("#dstnNation").val($(this).val());
					$("#formSearch").submit();
				
			});

			$("#btn_in").on('click',function(e){

				var data = $("#mainForm").serialize() ;

				$.ajax({
					url:'/comn/transComChange/userTransComIn',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: data, 
					success : function(data) {

						if(data.rstStatus == "SUCESS"){
							alert('삭제 되었습니다.');
							window.location.reload();
						}

		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			});
	
			
			$(".btn_del").on('click',function(e){
				if(!confirm("정말 삭제 하시겠습니까?")){
					return false;
				}
				var idx = $(this).attr('idx');

				$.ajax({
					url:'/comn/transComChange/userTransComDel',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {"idx":idx}, 
					success : function(data) {

						if(data.rstStatus == "SUCESS"){
							alert('삭제 되었습니다.');
							window.location.reload();
						}

		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			});
		});
		</script>

	</body>
</html>
