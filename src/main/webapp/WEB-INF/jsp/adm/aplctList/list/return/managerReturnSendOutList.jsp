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
	</style>
   
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>반품 출고 리스트</title>
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
							<li class="active">반품 출고 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 출고 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="search-div">
									<div class="row">
										<span class="input-group" style="display: inline-flex; margin-left:12px;">
											<select name="searchType" id="searchType" style="height: 34px;">
												<option <c:if test="${searchType eq 'hawbNo' }"> selected </c:if>value="hawbNo">HAWB NO</option>
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
		                    	<div id="table-contents" class="table-contents"">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable">
		                    			<thead>
		                    				<tr style="border-bottom: 3px solid rgba(204, 204, 204, 0.8) !important;">
		                    					<th class="center colorBlack" style="width:140px;">HAWB NO</th>
		                    					<th class="center colorBlack" style="width:140px;">원송장번호</th>
		                    					<th class="center colorBlack" style="width:140px;">반송장번호</th>
		                    					<th class="center colorBlack" style="width:120px;">도착국가</th>
		                    					<th class="center colorBlack" style="width:80px;">배송사</th>
		                    					<th class="center colorBlack" style="width:120px;">반송인</th>
		                    					<th class="center colorBlack" style="width:120px;">수취인</th>
		                    					<th class="center colorBlack" style="width:180px;">Company</th>
		                    					<th class="center colorBlack" style="width:60px;">BOX</th>
		                    					<th class="center colorBlack" style="width:60px;">WTA</th>
		                    					<th class="center colorBlack" style="width:60px;">WTC</th>
		                    					<th class="center colorBlack" style="width:60px;"></th>
		                    				</tr>
		                    			</thead>
		                    			<tbody>
		                    				<c:forEach items="${returnInspList}" var="inspList" varStatus="status">
		                    					<tr>
		                    					<td class="center align-middle">
		                    						<c:if test="${!empty inspList.HAWB_NO}">
			                    						<a href="/mngr/aplctList/return/detail/${inspList.ORDER_REFERENCE}" style="font-weight:bold;">
			                    							${inspList.HAWB_NO}
			                    							<c:if test="${!empty inspList.YSL_HAWB}">
			                    								<br/>${inspList.YSL_HAWB}
			                    							</c:if>
			                    						</a>
			                    					</c:if>
			                    					<c:if test="${empty inspList.HAWB_NO}">
			                    						<a href="/mngr/aplctList/return/detail/${inspList.ORDER_REFERENCE}" style="font-weight:bold;">-</a>
			                    					</c:if>
			                    				</td>
		                    					<td class="center align-middle">
		                    						<a href="/mngr/aplctList/return/detail/${inspList.ORDER_REFERENCE}" style="font-weight:bold;">${inspList.KOBL_NO}</a>
		                    					</td>
		                    					<td class="center align-middle">${inspList.RE_TRK_NO}</td>
		                    					<td class="center align-middle">${inspList.NATION_NAME}</td>
		                    					<td class="center align-middle">${inspList.TRANS_CODE}</td>
		                    					<td class="center align-middle">${inspList.PICKUP_NAME}</td>
		                    					<td class="center align-middle">${inspList.SELLER_ID}</td>
		                    					<td class="center align-middle">${inspList.SENDER_NAME}</td>
		                    					<td class="align-middle" style="text-align:right;">${inspList.BOX_CNT}</td>
		                    					<td class="align-middle" style="text-align:right;">${inspList.WTA}</td>
		                    					<td class="align-middle" style="text-align:right;">${inspList.WTC}</td>
		                    					<td class="center align-middle">
		                    						<input type="button" value="POD" class="active2" onclick="fn_pod('${inspList.HAWB_NO}')"/>
		                    					</td>
		                    					</tr>
		                    				</c:forEach>
		                    			</tbody>
		                    		</table>
		                    	 </div>
							</form>
							<br/>
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
      <!-- excel upload 추가 -->
         jQuery(function($) {
            $(document).ready(function() {
               $("#14thMenu-2").toggleClass('open');
               $("#14thMenu-2").toggleClass('active');
               $("#14thTwo-6").toggleClass('active');
            });

            function LoadingWithMask() {
               var maskHeight = $(document).height();
               var maskWidth = window.document.body.clientWidth;

               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
                 
                //화면에 레이어 추가
                $('body')
                    .append(mask)
                    
                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
                $('#mask').css({
                        'width' : maskWidth
                        ,'height': maskHeight
                        ,'opacity' :'0.3'
                });
              
                //마스크 표시
				$('#mask').show();  
                //로딩중 이미지 표시
			}
            
         });

         function fnRead(orderReference){
        	 location.href="/mngr/aplctList/return/C002/"+orderReference;
         }

         function fn_pod(hawbNo) {
        	 window.open('/comn/deliveryTrack/'+hawbNo,'배종조회','width=650,height=800');
         }

      </script>
      <!-- script addon end -->

   </body>
</html>