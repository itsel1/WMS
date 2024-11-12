<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
   <link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
	#search-btn {
		width: 45px;
		background-color: #527d96;
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
	}
	
	#search-btn:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3); 
	}
	
	.buttons {
		box-shadow: 2px 2px 3px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
		outline: none;
		border-radius: 2px;
		padding: 2px 6px;
		background-color: #6b95ae;
		border: 1px solid #6b95ae;
		color: #fff;
		font-size: 13px;
		font-weight: normal;
	}
	
	.buttons:hover, .buttons:active {
		box-shadow: 1px 1px 3px rgba(82, 125, 150, 0.3);
	}
	
	#contents-table tbody td {
		font-size: 13px !important;
		font-weight: 600;
	}
	
	#noticeText {
		font-weight: 500 !important;
		font-size: 20px;
		color: #527d96;
	}
	
	
	#search-table {
	border: 1px solid #e2e2e2;
	border-collapse: collapse;
}

	#search-table th {
		border: 1px solid #e2e2e2;
		border-collapse: collapse;
		padding-left: 10px !important;
		padding-right: 10px !important;
		background-color: #527d96;
		color: #fff;
	}
	
	#search-table th, td {
		height: 30px !important;
		vertical-align: middle;
	}
	
	#search-table td input[type="text"] {
		height: 30px !important;
		border: none;
	}
	
	#search-table td select {
		height: 30px !important;
	}
	
	#search-btn {
		height: 30px !important;
		background-color: #527d96;
		color: #fff;
		border: none;
		border-radius: 3px;
		padding-left: 10px;
		padding-right: 10px;
	}
	
	#fromDate, #toDate {
		text-align: center;
	}
	
	#return-table th, td {
		text-align: center;
		word-break: break-all;
	}
	
	#return-table thead th {
		background-color: #527d96;
		color: #fff;
	}
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
                     <li class="active">출고 작업</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 출고 작업
                     </h1>
                  </div>
                  <form name="returnForm" id="returnForm" method="get" action="/mngr/return/orderShipment">
              		<table id="search-table">
						<tr>
							<th>검수일자</th>
							<td><input type="text" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}"></td>
							<td>-</td>
							<td><input type="text" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}"></td>
							<th>원송장번호</th>
							<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
							<th>반송장번호</th>
							<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
							<th>USER ID</th>
							<td><input type="text" name="userId" id="userId" value="${params.userId}"></td>
							<td><input type="button" id="search-btn" value="검색"></td>
						</tr>
					</table><br/>
					<table class="table table-bordered table-hover" id="return-table">
						<thead>
							<tr>
								<th style="width:7%;">USER ID</th>
								<th style="width:5%;">진행상황</th>
								<th style="width:8%;">검수일자</th>
								<th style="width:10%;">원송장번호</th>
								<th style="width:10%;">반송장번호</th>
								<th style="width:10%;">주문번호</th>
								<th style="width:7%;">도착지</th>
								<th style="width:10%;">담당자명</th>
								<th style="width:10%;">담당자 Tel</th>
								<th style="width:13%;">담당자 Email</th>
								<th style="width:10%;"></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty orderList}">
									<c:forEach items="${orderList}" var="orderList" varStatus="status">
										<tr>
											<td>${orderList.userId}</td>
											<td>${orderList.stateName}</td>
											<td>${orderList.WDate}</td>
											<td>${orderList.koblNo}</td>
											<td>${orderList.trkNo}</td>
											<td>${orderList.orderNo}</td>
											<td>${orderList.dstnNation}</td>
											<td>${orderList.attnName}</td>
											<td>${orderList.attnTel}</td>
											<td>${orderList.attnEmail}</td>
											<td>
												<c:if test="${orderList.state ne 'C003'}">
												승인 대기 중
												</c:if>
												<c:if test="${orderList.state eq 'C003' && orderList.tmpChk ne '0'}">
												출고 진행 중
												</c:if>
												<c:if test="${orderList.state eq 'C003' && orderList.tmpChk eq '0'}">
												<a href="/mngr/return/orderShipOut?nno=${orderList.nno}">출고작업</a>
												</c:if><input type="button" class="btn btn-white btn-xs" onclick="fn_printPdf('${orderList.nno}','${orderList.groupIdx}')" value="재고출력">
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="11" style="text-align: center;">검색 결과가 존재하지 않습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</form>
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
      
	  <script src="/assets/js/jquery-ui.min.js"></script>
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
				$("#returnMenu-3").toggleClass('active');
            });

          	$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})

          	$("#search-btn").on('click', function() {
				$("#returnForm").submit();
		    })
        });

        function fn_printPdf(nno, groupIdx) {
        	var url = "/mngr/return/pdfPopup?nno="+nno+"&groupIdx="+groupIdx;
			window.open(url, "_blank", "toolbar=yes,resizable=no,directories=no,width=480, height=360");
        }
      </script>
      <!-- script addon end -->

   </body>
</html>