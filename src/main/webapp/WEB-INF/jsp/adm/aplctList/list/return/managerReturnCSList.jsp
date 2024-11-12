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
   
   #dynamic-contents {
   	table-layout: fixed;
   	width:98%;
   	margin: auto;
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
   <title>반품 CS 리스트</title>
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
                     <li class="active">반품 CS 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 CS 리스트
                     </h1>
                  </div>
                  <div id="inner-content-side" >
                     <form name="returnForm" id="returnForm">
                     	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                     	<br/>
	                    <div id="search-div" style="margin-left:20px;">
	                    	<div class="row">
								<span class="input-group" style="display: inline-flex; margin-left:12px;">
									<select name="searchType" id="searchType" style="height: 34px;">
										<option <c:if test="${searchType eq 'reTrkNo' }"> selected </c:if>value="reTrkNo">반송장번호</option>
										<option <c:if test="${searchType eq 'koblNo'}"> selected </c:if>value="koblNo">원송장번호</option>
										<option <c:if test="${searchType eq 'userId'}"> selected </c:if>value="userId">Seller ID</option>
									</select>
									<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width:300px;" />
									<button type="submit" class="btn btn-default no-border btn-sm">
										<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
	                    </div>
	                    <br/>
	                    <div class="contents-div" style="margin:auto;">
	                    	<table id="dynamic-contents" class="table table-bordered table-hover">
	                    		<thead>
		                    		<tr>
		                    			<th class="center" style="width:5%;">NO</th>
		                    			<th class="center" style="width:10%;">USER_ID</th>
		                    			<th class="center" style="width:7%;">진행 상황</th>
		                    			<th class="center" style="width:10%;">원운송장번호</th>
		                    			<th class="center" style="width:10%;">반송장번호</th>
		                    			<th class="center" style="width:13%;">문의 접수일</th>
		                    			<th class="center" colspan="3">문의 내용</th>
		                    			<th class="center" style="width:5%;">문의 상태</th>
		                    			<th class="center" style="width:5%;"></th>
		                    		</tr>
	                    		</thead>
	                    		<tbody>
	                    			<c:choose>
	                    				<c:when test="${!empty list}">
			                    			<c:forEach items="${list}" var="list" varStatus="status">
			                    				<tr>
			                    					<td class="center">${status.count}</td>
			                    					<td class="center">${list.USER_ID}</td>
			                    					<td class="center">${list.STATE}</td>
			                    					<td class="center">${list.KOBL_NO}</td>
			                    					<td class="center">${list.RE_TRK_NO}</td>
			                    					<td class="center">${fn:split(list.W_DATE,'.')[0]}</td>
			                    					<td colspan="3" style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
			                    						<c:if test="${list.READ_YN eq 'N'}">
			                    							<small class="red">new!&nbsp;</small>
			                    								${list.WH_MEMO}
			                    						</c:if>
			                    						<c:if test="${list.READ_YN eq 'Y'}">
				                    						${list.WH_MEMO}
			                    						</c:if>
			                    					</td>
			                    					<td class="center">
			                    						<c:if test="${list.READ_YN eq 'N'}">
			                    							문의접수
			                    						</c:if>
			                    						<c:if test="${list.READ_YN eq 'Y'}">
			                    							답변완료
			                    						</c:if>
			                    					</td>
			                    					<td class="center">
			                    						<c:if test="${list.READ_YN eq 'N'}">
			                    							<label style="cursor:pointer; color:#46649B;" onclick="fn_popup('${list.IDX}', '${list.ORG_STATION}', '${list.USER_ID}', 'w')">등록</label>
			                    						</c:if>
			                    						<c:if test="${list.READ_YN eq 'Y'}">
			                    							<label style="cursor:pointer; color:#46649B;" onclick="fn_popup('${list.IDX}', '${list.ORG_STATION}', '${list.USER_ID}', 'm')">수정</label>
			                    						</c:if>
													</td>
			                    				</tr>
			                    			</c:forEach>
		                    			</c:when>
		                    			<c:otherwise>
		                    				<tr>
		                    					<td colspan="10" class="center">조회 결과가 없습니다</td>
		                    				</tr>
		                    			</c:otherwise>
	                    			</c:choose>
	                    		</tbody>
	                    	</table>
	                    </div>
                    </form>
                  </div>
                  <br/>
              	<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
      <!-- excel upload 추가 -->
         jQuery(function($) {
            $(document).ready(function() {
               $("#14thMenu-2").toggleClass('open');
               $("#14thMenu-2").toggleClass('active'); 
               $("#14thTwo-8").toggleClass('active');
   
            });
         

         });

         function fn_popup(idx, orgStation, userId, type) {
        	 window.open("/mngr/aplctList/return/popupMsg?idx="+idx+"&orgStation="+orgStation+"&userId="+userId+"&type="+type, "PopupWin", "width=750, height=500");
         }

			
      </script>
      <!-- script addon end -->

   </body>
</html>