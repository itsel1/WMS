<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>반품 리스트</title>
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
                     <li class="active">운송장 미등록 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
					<div class="page-header">
						<h1>
							운송장 미등록 리스트
						</h1>
					</div>
					<div id="inner-content-side" >
						<form name="registForm" id="registForm">
	                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                    	<div id="search-div">
	                    		<table id="search-table" class="table table-bordered table-hover containerTable" style="width:40%;">
	                    			<thead>
		                    			<tr>
		                    				<th class="center">주문번호</th>
		                    				<td style="padding:0px;">
		                    					<input type="text" style="width:100%;height:42px;" name="orderNo" id="orderNo" value="${params.orderNo}"/>
		                    				</td>
		                    				<th class="center">User ID</th>
		                    				<td style="padding:0px;">
		                    					<input type="text" style="width:100%;height:42px;" name="userId" id="userId" value="${params.userId}"/>
		                    				</td>
		                    				<td class="center">
		                    					<input type="button" id="searchBtn" value="Search"/>
		                    				</td>
		                    			</tr>
		                    		</thead>
	                    		</table>
							</div>
							<br/>
							<div style="text-align:right;">
								<button id="downExcel" class="btn btn-primary btn-white" style="margin-bottom:10px;">용성 메일 다운로드</button>
							</div>
							<div id="table-contents">
								<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="width:100%;">
									<thead>
										<tr style="border-bottom: 2px solid #ccc;">
											<th class="center">
												<label class="pos-rel"> 
													<input type="checkbox" class="ace" /> 
													<span class="lbl"></span>
												</label>
											</th>
											<th class="center">User ID</th>
											<th class="center">Order No</th>
											<th class="center">Order Date</th>
											<th class="center">Error Msg</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!empty orderList}">
												<c:forEach items="${orderList}" var="orderList" varStatus="status">
													<tr>
														<td class="center">
															<label class="pos-rel"> 
																<input type="checkbox" class="ace" value="${orderList.nno}" /> 
																<span class="lbl"></span>
															</label>
														</td>
														<td class="center">${orderList.userId}</td>
														<td class="center">${orderList.orderNo}</td>
														<td class="center">${orderList.orderDate}</td>
														<td class="center">${orderList.errorMsg}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="center" colspan="5">검색 결과를 찾을 수 없습니다</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
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
				$("#4thMenu").toggleClass('open');
           		$("#4thMenu").toggleClass('active'); 
           		$("#4thForth").toggleClass('active');   

			})

			$("#downExcel").on('click', function() {
				$("#registForm").attr('action', '/mngr/order/cusItemCodeDownExcel');
				$("#registForm").attr('method', 'GET');
				$("#registForm").submit();
			})

			$("#searchBtn").on('click', function() {
				$("#registForm").attr('action', '/mngr/order/unRegOrderList');
				$("#registForm").attr('method', 'GET');
				$("#registForm").submit();
			})
		})

      </script>
      <!-- script addon end -->

   </body>
</html>