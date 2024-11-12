<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>사용자 관리</title>
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
								거래처 관리
							</li>
							<li class="active">예치금 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								예치금 관리
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="search-button" id="search-botton" method="get" action="/mngr/aplctList/return/depositList">
								<div id="search-div">
									<br>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px">
													<option>USER ID</option>
												</select>
												<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width: 300px"/>
													<button type="submit" class="btn btn-default no-border btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
											</span>
										</div>
									</div>
									<br>
								</div>
								<br>
								<div id="table-contents" class="table-contents">
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%">
										<colgroup>
											<col width="15%"/>
											<col width="20%"/> 
										</colgroup>
										<thead>
											<tr>
												<th class="center colorBlack" >아이디</th>
												<th class="center colorBlack" >현재 예치금</th>
												<th class="center colorBlack" >예치금 조정</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userInfo}" var="test">
												<tr>
													<td class="center" style="word-break:break-all;">
														<a href='/mngr/aplctList/return/deposit/${test.USER_ID}'>${test.USER_ID}</a>
														
													</td>
													<td class="center" id="depositCostDiv" name="depositCostDiv" style="word-break:break-all;">
														${test.DEPOSIT_COST}
													</td>
													<td class="center" style="word-break:break-all;">
														<input type="number" id="costValue${test.USER_ID}" name="costValue${test.USER_ID}">
														<input type="button" id="add" name="add" onclick="addDeposit('${test.USER_ID}')" value="증액">
														<input type="button" id="minus" name="minus" onclick="minusDeposit('${test.USER_ID}')" value="감액">
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<input type="hidden" id="calculate" name="calculate" value="">
								<input type="hidden" id="depositId" name="depositId" value="">
								<input type="hidden" id="costValue" name="costValue" value="${costValue}">
							</form>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		<script src="/assets/js/bootstrap.min.js"></script>
		
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thTwo-5").toggleClass('active'); 

					$("[name=depositCostDiv]").each(function(){
						var money = $(this).text();
						var money2 = money.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
						$(this).text(money2);
					});
					
					
					
					/* $("#getSelectIndvdList").toggleClass('active'); */
					
				});
				
				//initiate dataTables plugin
				
				
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "autoWidth": false,
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			});
			function addDeposit(userId){
				$("#costValue").val($("#costValue"+userId).val())
				$("#depositId").val(userId)
				$("#calculate").val("A")
				$("#search-botton").attr("action", "/mngr/aplctList/return/depositListp");
				$("#search-botton").attr("method", "post");            
				$("#search-botton").submit();
				
			};

			function minusDeposit(userId){
				$("#costValue").val($("#costValue"+userId).val())
				$("#depositId").val(userId)
				$("#calculate").val("B")
				$("#search-botton").attr("action", "/mngr/aplctList/return/depositListp");
				$("#search-botton").attr("method", "post");
				$("#search-botton").submit();
			};
			
			
		</script>
		<!-- script addon end -->
	</body>
</html>
