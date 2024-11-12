<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
<style type="text/css">
#content-layout {
	margin: auto;
	width: 60%;
}

#list-table {
	width: 100%;	
}
</style>   
</head> 
   <title>게시판</title>
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
                      	게시판
                     </li>
                     <li class="active">출고지시</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
					<div class="page-header">
						<h1>
							출고 지시
						</h1>
					</div>
					<div id="inner-content-side" >
						<div id="content-layout">
							<form name="whoutForm" id="whoutForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<input type="button" onclick="fn_openOrderIn()" value="등록">

		                    	<table id="list-table" class="">
		                    		<thead>
		                    			<tr>
		                    				<th>No</th>
		                    				<th>카테고리</th>
		                    				<th>제목</th>
		                    				<th>USER ID</th>
		                    				<th>View</th>
		                    			</tr>
		                    		</thead>
		                    		<tbody>
		                    			<c:forEach items="${noticeList}" var="list" varStatus="status">
		                    				<tr>
		                    					<td>${status.count}</td>
		                    					<td>
		                    						<c:if test="${list.category eq 'A'}">
		                    							출하지시
		                    						</c:if>
		                    						<c:if test="${list.category eq 'B'}">
		                    							입고안내
		                    						</c:if>
		                    					</td>
		                    					<td>
		                    						<c:forEach var="i" begin="1" end="${list.indent}">
		                    							<c:if test="${i eq list.indent}">
		                    								ㄴ
		                    							</c:if>
		                    							<c:if test="${i ne list.indent}">
		                    								&nbsp;&nbsp;
		                    							</c:if>
		                    						</c:forEach>
		                    						<a href="/mngr/board/whoutOrderDetail?idx=${list.idx}">${list.title}</a>
		                    					</td>
		                    					<td>${list.userId}</td>
		                    					<td>${list.viewCnt}</td>
		                    				</tr>
		                    			</c:forEach>
		                    		</tbody>
		                    	</table>
							</form>
						</div>
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
				$("#11thMenu").toggleClass('open');
           		$("#11thMenu").toggleClass('active'); 
           		$("#whoutOrder").toggleClass('active');
			})
		})

		function fn_openOrderIn() {
			location.href = "/mngr/board/whoutOrderIn";
		}
      </script>
      <!-- script addon end -->

   </body>
</html>