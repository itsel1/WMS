<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <style>
   .button {
     background-color: rgb(67,142,185); /* Green */
     border: none;
     color: white;
     padding: 0px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
     font-size: 12px;
     margin: 4px 2px;
     cursor: pointer;
     -webkit-transition-duration: 0.4s; /* Safari */
     transition-duration: 0.4s;
     width: 100px;
     height: 30px;
   }
   
   .button2:hover {
     box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
   }
   
   .active2 {
   	border:1px solid #dcdcdc;
   	background:white;
   }
   
   .active2:hover {
   	background:black;
   	color:white;
   }
   
   	table td {
	  vertical-align: middle !important;
	}
   </style>
   
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
     .colorBlack {color:#000000 !important;}
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
                        반품
                     </li>
                     <li class="active">반품 입고</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 입고 작업
                     </h1>
                  </div>
                  <div id="inner-content-side" >
                     <form name="returnForm" id="returnForm">
                     	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    	<div id="search-div">
							<div class="row">
								<span class="input-group" style="display: inline-flex; margin-left:12px; margin-top:20px;">
									<select name="searchType" id="searchType" style="height: 34px;">
										<option <c:if test="${searchType eq 'reTrkNo' }"> selected </c:if>value="reTrkNo">반송장번호</option>
									</select>
									<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width:300px;" />
									<button type="submit" class="btn btn-default no-border btn-sm">
										<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
						<br/>
	                    <div id="table-contents" class="table-contents">
	                    	<table class="table table-bordered table-hover" style="width:100%; table-layout:fixed;">
	                    		<thead>
	                    			<tr>
	                    				<th class="center colorBlack" style="width:3%;">NO</th>
	                    				<th class="center colorBlack" style="width:6%;">업체 ID</th>
	                    				<th class="center colorBlack" style="width:10%;">KOBL NO</th>
	                    				<th class="center colorBlack" style="width:10%;">반송장번호</th>
	                    				<th class="center colorBlack" style="width:25%;">주소</th>
	                    				<th class="center colorBlack" style="width:10%;">전화번호</th>
	                    				<th class="center colorBlack" style="width:20%;">대표 상품명</th>
	                    				<th class="center colorBlack" style="width:5%;">상품 개수</th>
	                    				<th class="center colorBlack" style="width:5%;">총 금액</th>
	                    				<th class="center colorBlack" style="width:6%;"></th>     				
	                    			</tr>
	                    		</thead>
	                    		<tbody style="vertical-align:middle;">
		                    		<c:choose>
		                    			<c:when test="${!empty inspList}">
			                    			<c:forEach items="${inspList}" var="list" varStatus="status">
			                    				<tr>
			                    					<td class="center" style="word-break:break-all;">${status.count}</td>
			                    					<td class="center" style="word-break:break-all;">${list.sellerId}</td>
			                    					<td class="center" style="word-break:break-all;">${list.koblNo}</td>
			                    					<td class="center" style="word-break:break-all;">${list.reTrkNo}</td>
													<td class="center" style="word-break:break-all;">
														<c:if test="${list.dstnNation eq 'JP'}">
															${list.senderState} ${list.senderCity} ${list.senderAddr}
														</c:if>
														<c:if test="${list.dstnNation ne 'JP'}">
															${list.senderAddr} ${list.senderCity} ${list.senderState}
														</c:if>
													</td>
													<td class="center" style="word-break:break-all;">${list.senderTel}</td>
													<td class="center" style="word-break:break-all;">${list.mainItem}</td>
													<td style="word-break:break-all; text-align:right;">${list.itemCnt}</td>
													<td style="text-align:right;">
														<fmt:formatNumber value="${list.totalValue}" pattern=".00"/>
													</td>
													<c:if test="${list.state eq 'B001'}"> <!-- 수거중 -->
														<td class="center" style="word-break:break-all;">
															<button type="button" class="btn btn-sm btn-primary btn-white" onclick="stockIn('${list.nno}')">입고</button>
														</td>
													</c:if>
													<c:if test="${list.state eq 'C001'}"> <!-- 입고 -->
														<td class="center" style="word-break:break-all;">
															<input type="button" class="btn btn-sm btn-success btn-white" onclick="inspStart('${list.orderReference}')" value="검수하기"/>
														</td>
													</c:if>
			                    				</tr>
			                    			</c:forEach>
			                    		</c:when>
			                    		<c:otherwise>
			                    			<tr>
			                    				<td colspan="9" class="center colorBlack">조회 결과가 없습니다</td>
			                    			</tr>
			                    		</c:otherwise>
	                    			</c:choose>
	                    		</tbody>
	                    	</table>
	                    </div>
	                    <input type="hidden" value="" id="targetStatus" name="targetStatus"/>
                    </form>
                  </div>
               	</div>
              	<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
           		$("#14thTwo-2").toggleClass('active');   

			})
		})
		
		function stockIn(nno) {
			$.ajax({
				type : "POST",
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				url : "/mngr/aplctList/return/stockIn",
				data : {nno: nno},
				error : function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				},
				success : function(rtnVal) {
					location.href = '/mngr/aplctList/returnOrder/inspList';
				}
			})
		}
		

      	function inspStart(orderReference) {
			location.href = "/mngr/aplctList/return/inspList/"+orderReference;	      	
	    }


		
      

      </script>
      <!-- script addon end -->

   </body>
</html>