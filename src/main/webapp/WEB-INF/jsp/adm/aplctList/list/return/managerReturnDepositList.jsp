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
	  
	  #detailBtn {
	  	border:none;
	  	background:gray;
	  	
	  }
	  
	  #detailBtn {
			border: 1px solid #dcdcdc;
			background: white;
		}
		
		#detailBtn:hover {
			border: 1px solid #FDCD8C;
			background: #FDCD8C;
			font-weight: bold;
		}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>반품</title>
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
								반품
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
							<form name="search-button" id="search-botton" method="get" action="">
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
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:60%;">
										<thead>
											<tr>
												<th class="center colorBlack">USER ID</th>
												<th class="center colorBlack">사용자 명</th>
												<th class="center colorBlack">회원 구분</th>
												<th class="center colorBlack">현재 예치금</th>
												<th class="center colorBlack">최근 사용일</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userInfo}" var="depositList" varStatus="status">
												<tr>
													<td class="center">${depositList.USER_ID}</td>
													<td class="center">
														<c:if test="${!empty depositList.USER_NAME}">
															${depositList.USER_NAME}
														</c:if>
														<c:if test="${empty depositList.USER_NAME}">
															${depositList.USER_ID}
														</c:if>
													</td>
													<td class="center">
														<c:if test="${depositList.USER_DIV eq 'ETPR_Y'}">
															기업
														</c:if>
														<c:if test="${depositList.USER_DIV eq 'ETPR_N'}">
															개인
														</c:if>
														<c:if test="${depositList.USER_DIV eq 'RETURN'}">
															반품
														</c:if>
													</td>
													<td style="text-align:right;">
														<fmt:formatNumber type="number" maxFractionDigits="3" value="${depositList.DEPOSIT_COST}"/>
													</td>
													<td class="center">
														<c:if test="${!empty depositList.W_DATE}">
															${fn:split(depositList.W_DATE,'.')[0]}
														</c:if>
														<c:if test="${empty depositList.W_DATE}">
															-
														</c:if>
													</td>
													<td class="center">
														<input type="button" id="detailBtn" onclick="fn_detail('${depositList.USER_ID}')" value="Detail" />
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
					$("#14thTwo-deposit").toggleClass('active'); 

					
				});

			})
				
			function fn_detail(userId) {
				location.href = '/mngr/aplctList/depositDetail/'+userId;
			}
			
		</script>
		<!-- script addon end -->
	</body>
</html>
