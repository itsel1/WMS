<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
<style type="text/css">
.main-content {
	background-color: #e6e6e6 !important;
}

#content-layout {
	margin: auto;
	width: 70%;
}

#list-table {
	width: 100%;	
}

.table {
	width: 100%;
	border-bottom: 1px solid #eee;
	border: 1px solid #e6e6e6;
	border-radius: 10px;
	margin-top: 10px;
}

.table a {
	color: #585858;
	text-decoration: none;
	font-weight: 600;
}

.table a:hover {
	text-decoration: underline;
}

.table-header {
	display: flex;
	width: 100%;
	color: black;
	padding: 0;
	color: #fff;
	background-color: #3c3c3c; 
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}

.table-row {
	cusor: pointer;
	display: relative;
	width: 100%;
	height: auto;
	position: relative;
	overflow: hidden;
	transition: all 0.3s ease-in-out;
	font-size: 15px;
	border-top: 1px solid #eee;
}

.table-data {
	padding: 15px 0px;
	text-align: center;
	width: 10%;
	align-items: center; 
	float: left;
	word-break: nowrap;
	font-size: 14px;
}

.header_item {
	padding: 5px 0;
	text-align: center;
	align-items: center;
	word-break: nowrap;
	float: left;
	font-weight: 600;
}

.table-row:hover {
	background-color: rgba(242, 242, 243, 0.8);
}

#search_form {
	display: flex;
	flex-direction: row;
	flex-wrap: nowrap;
	justify-content: flex-start;
	align-items: stretch;
}

.search_element:nth-child(1) {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 34px;
	width: 15%;
	border: 1px solid #e6e6e6;
	border-top-left-radius: 5px;
	border-bottom-left-radius: 5px;
	background-color: #3c3c3c;
	color: #fff;
}

.search_element:nth-child(2), .search_element:nth-child(3) {
	padding: 0;
	width: 15%;
}

.search_element select {
	width: 100%;
	height: 34px;
}

.search_element:nth-child(4) {
	width: 48%;
}

.search_element:nth-child(4) input[name=searchKeyword] {
	width: 100%;
	height: 34px;
}

.search_element:nth-child(5) {
	width: 7%;
}

#searchBtn {
	text-align: center;
	width: 100%;
	height: 34px;
	background-color: #3c3c3c;
	color: #fff;
	border: none;
	border-radius: 3px;
}

#orderInBtn {
	margin-top: 4px;
	width: 60px;
	height: 30px;
    cursor: pointer;
    color: #fff;
    background-color: rgba(105, 105, 105, 0.8);
    border: none;
    border-radius: 3px;
    box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.3);
    transition: 0.3s;
    text-align: center;
}

#orderInBtn:hover {
    background-color: rgba(105, 105, 105, 0.63);
    box-shadow: 0 1px 1px rgba(82, 125, 150, 0.6);
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
		                    	<div class="search_layout" style="width:100%;overflow:hidden;">
		                    		<div style="width:50%;float:left;">
		                    			<div id="search_form">
		                    				<div class="search_element">
		                    					분류
		                    				</div>
		                    				<div class="search_element">
		                    					<select id="category" name="category">
		                    						<option value="">::선택::</option>
		                    						<option value="A" <c:if test="${params.category eq 'A'}">selected</c:if>>출하지시</option>
		                    						<option value="B" <c:if test="${params.category eq 'B'}">selected</c:if>>입고안내</option>
		                    					</select>
		                    				</div>
		                    				<div class="search_element">
		                    					<select id="searchType" name="searchType">
		                    						<option value="all" <c:if test="${params.searchType eq 'all'}">selected</c:if>>::전체::</option>
		                    						<option value="userId" <c:if test="${params.searchType eq 'userId'}">selected</c:if>>USER ID</option>
		                    						<option value="title" <c:if test="${params.searchType eq 'title'}">selected</c:if>>제목</option>
		                    					</select>
		                    				</div>
		                    				<div class="search_element">
		                    					<input type="text" name="searchKeyword" id="searchKeyword" value="${params.searchKeyword}">
		                    				</div>
		                    				<div class="search_element">
		                    					<button type="button" id="searchBtn">
		                    						<i class="fa fa-search"></i>
		                    					</button>
		                    				</div>
		                    			</div>
		                    		</div>
		                    		<div style="width:50%;float:right;text-align:right;padding-right:10px;">
		                    			<input type="button" id="orderInBtn" onclick="fn_openOrderIn()" value="등록">
		                    		</div>
		                    	</div>
		                    	<div class="table">
		                    		<div class="table-header">
		                    			<div class="header_item" style="width:5%;border-right:1px solid #e6e6e6;">No</div>
		                    			<div class="header_item" style="width:15%;border-right:1px solid #e6e6e6;">분류</div>
		                    			<div class="header_item" style="width:40%;border-right:1px solid #e6e6e6;">제목</div>
		                    			<div class="header_item" style="width:10%;border-right:1px solid #e6e6e6;">첨부파일</div>
		                    			<div class="header_item" style="width:10%;border-right:1px solid #e6e6e6;">USER ID</div>
		                    			<div class="header_item" style="width:20%;">작성일</div>
		                    		</div>
		                    		<div class="table-content" id="target">
		                    			<c:forEach items="${noticeList}" var="list" varStatus="status">
											<div class="table-row">
		                    					<input type="hidden" class="status" value="${list.status}">
		                    					<div class="table-data" style="width:5%;">${status.count}</div>
		                    					<div class="table-data" style="width:15%;">
		                    						<c:if test="${list.category eq 'A'}">
		                    							출하지시
		                    						</c:if>
		                    						<c:if test="${list.category eq 'B'}">
		                    							입고안내
		                    						</c:if>
		                    					</div>
		                    					<div class="table-data" style="width:40%;text-align:left !important;padding-left:1%;">
		                    						<c:forEach var="i" begin="1" end="${list.indent}">
		                    							<c:if test="${i eq list.indent}">
		                    								⤷ 
		                    							</c:if>
		                    							<c:if test="${i ne list.indent}">
		                    								&nbsp;&nbsp;
		                    							</c:if>
		                    						</c:forEach>
		                    						<c:if test="${list.status ne 'D'}">
		                    							<a href="/mngr/board/noticeDetail?idx=${list.idx}&groupIdx=${list.groupIdx}">${list.title}</a>
		                    						</c:if>
		                    						<c:if test="${list.status eq 'D'}">
		                    							<a style="cursor:not-allowed;">삭제된 게시글입니다.</a>
		                    						</c:if>
		                    					</div>
		                    					<div class="table-data" style="width:10%;">
		                    						<c:if test="${fn:length(list.fileList) != 0}">
		                    							<c:forEach items="${list.fileList}" var="fileList">
		                    								<c:if test="${list.status ne 'D'}">
		                    									<a href="${fileList.fileDir}" target="_blank" title="${fileList.fileName}" download><i class="fa fa-download" style="font-size:14px"></i></a>
		                    								</c:if>
		                    							</c:forEach>
		                    						</c:if>
												</div>
			                    				<div class="table-data" style="width:10%;">${list.WUserId}</div>
			                    				<div class="table-data" style="width:20%;">${list.WDate}</div>
		                    				</div>
		                    			</c:forEach>
		                    		</div>
		                    	</div>
							</form>
							
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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

			$("#searchBtn").on('click', function() {
				$("#whoutForm").attr("action", "/mngr/board/noticeList");
				$("#whoutForm").attr("method", "get");
				$("#whoutForm").submit();
			})
		})
		
		function fn_openOrderIn() {
			location.href = "/mngr/board/noticeIn";
		}

      </script>
   </body>
</html>