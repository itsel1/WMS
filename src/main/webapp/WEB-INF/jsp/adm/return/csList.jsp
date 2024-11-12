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
		font-family:'Nanum Gothic', sans-serif !important;
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
	
	#return-table {
		overflow: hidden;
	}
	
	#return-table th, td {
		text-align: center;
		word-break: break-all;
	}
	
	#return-table thead th {
		background-color: #527d96;
		color: #fff;
	}
	
	#newChk {
		position: relative;
		top: 1.5px;
		margin-left: 3px;
	}
	
	#newChkLabel {
		margin-left: 5px;
		position: relative;
		top: -1px;
		font-size: 12px;
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
                     <li class="active">CS 목록</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 CS 목록
                     </h1>
                  </div>
                  <form name="returnForm" id="returnForm" method="get" action="/mngr/return/csList">
                  	<input type="hidden" name="chk" id="chk">
                  	<input type="hidden" name="userId" id="userId" value="${userId}">
              		<table id="search-table">
						<tr>
							<th>원송장번호</th>
							<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
							<th>반송장번호</th>
							<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
							<th>USER ID</th>
							<td><input type="text" name="searchUserId" id="searchUserId" value="${params.userId}"></td>
							<td><input type="button" id="search-btn" value="검색"></td>
						</tr>
					</table><br/>
					<input type="checkbox" id="newChk" <c:if test="${params.chk eq 'Y'}">checked</c:if>><label for="newChk" id="newChkLabel">Check New</label>
					<table class="table table-bordered table-hover" id="return-table">
						<thead>
							<tr>
								<th style="width:7%;">업체ID</th>
								<th style="width:7%;">접수일자</th>
								<th style="width:10%;">원송장번호</th>
								<th style="width:10%;">반송장번호</th>
								<th style="width:7%;">택배사</th>
								<th style="width:7%;">발송인명</th>
								<th style="width:7%;">등록ID</th>
								<th style="width:22%;">내용</th>
								<th style="width:10%;">등록일자</th>
								<th style="width:5%;"></th>
								<th style="width:3%;"></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty csList}">
									<c:forEach items="${csList}" var="csList" varStatus="status">
										<tr <c:if test="${csList.adminYn eq 'Y'}">style="background-color:Snow;"</c:if>>
											<td class="align-middle">${csList.userId}</td>
											<td class="align-middle">${csList.registDate}</td>
											<td class="align-middle">${csList.koblNo}</td>
											<td class="align-middle">${csList.trkNo}</td>
											<td class="align-middle">${csList.trkCom}</td>
											<td class="align-middle">${csList.shipperName}</td>
											<td class="align-middle">${csList.wUserId}</td>
											<td class="align-middle" style="padding:0;">
												<input type="text" value="${csList.content}" style="width:98%;font-size:12px;height:28px;border-radius:7px!important;">
											</td>
											<td class="align-middle">${csList.wDate}</td>
											<td class="align-middle">
												<c:if test="${csList.adminYn eq 'Y'}">
													<a style="cursor:pointer;" onclick="deleteMemo('${csList.nno}','${csList.idx}','${csList.wUserId}')">[삭제]</a>
												</c:if>
												<c:if test="${csList.adminYn ne 'Y'}">
													<a style="cursor:pointer;" onclick="popupMemo('${csList.nno}')">[등록]</a>
												</c:if>
											</td>
											<td class="align-middle">
												<c:if test="${csList.newYn eq 'Y'}">
													<font class="red">New</font>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="11" style="text-align:center;">검색 결과가 존재하지 않습니다.</td>
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
				$("#returnMenu-6").toggleClass('active');
            });

          	$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			});

          	$("#search-btn").on('click', function() {
				$("#returnForm").submit();
		    });

		    $("#newChk").on('click', function(e) {
			    if ($(this).prop('checked')) {
				    $("#chk").val("Y");
				} else {
					$("#chk").val("N");
				}

				$("#returnForm").submit();
			});
        });

        function popupMemo(nno) {
            var url = "/comn/returnCsMemo?nno="+nno;
           	window.open(url, 'popupMemo', 'width=500,height=600');
		}

		function deleteMemo(nno, idx, wUserId) {
			var userId = $("#userId").val();
			if (wUserId != userId) {
				alert("등록한 관리자 ID와 접속한 ID가 다릅니다.");
				return;
			} else {
				$.ajax({
					url : '/mngr/return/deleteReturnCs',
					type : 'POST',
					data : {nno: nno, idx: idx},
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(result) {
						alert("삭제 되었습니다.");
						location.reload();
					},
					error: function(xhr, status) {
						alert("삭제 처리 중 오류가 발생 하였습니다.\n다시 시도해 주세요.");
						return;
					}
				});	
			}
		}
      </script>
      <!-- script addon end -->

   </body>
</html>