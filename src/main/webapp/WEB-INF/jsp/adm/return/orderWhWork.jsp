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
                     <li class="active">입고 검수 작업</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        입고 및 검수 작업
                     </h1>
                  </div>
					<div id="barcodeScanDiv">
                		<div class="row">
                			<div class="col-xs-6">
                				<p id="noticeText">※ 바코드 스캔 후 엔터 클릭 시 입고 처리</p>
                				<input type="text" name="barcodeNo" id="barcodeNo" style="width:50%;height:50px;font-size:30px;">
                			</div>
                		</div>
                		<br>
                		<div class="row">
                			<div class="col-xs-12" style="height:1px;border:1px solid rgba(219, 219, 219, 0.3);"></div>
                		</div>
                	</div>
                  <br>
                  <form name="returnForm" id="returnForm" method="get" action="/mngr/return/orderWHWork">
					<table id="search-table">
						<tr>
							<td><select id="state" name="state">
								<option value="">::선택::</option>
								<option value="B001" <c:if test="${params.state eq 'B001'}"> selected </c:if>>수거중</option>
								<option value="C001" <c:if test="${params.state eq 'C001'}"> selected </c:if>>입고</option>
							</select></td>
							<th>접수일자</th>
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
								<th style="width:5%;">상품이미지</th>
								<th style="width:10%;">USER ID</th>
								<th style="width:10%;">원운송장번호</th>
								<th style="width:10%;">반송장번호</th>
								<th style="width:10%;">주문번호</th>
								<th style="width:7%;">담당자명</th>
								<th style="width:10%;">담당자 Tel</th>
								<th style="width:10%;">담당자 Email</th>
								<th style="width:18%;">대표상품명</th>
								<th style="width:5%;">요청수량</th>
								<th style="width:5%;"></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty orderList}">
									<c:forEach items="${orderList}" var="orderList" varStatus="status">
										<tr>
											<td style="padding:3px !important;width:100px;">
												<c:if test="${!empty orderList.itemImgUrl}">
													<img src="${orderList.itemImgUrl}" style="width:50px;height:50px;">
												</c:if>
												<c:if test="${empty orderList.itemImgUrl}">
													-
												</c:if>
											</td>
											<td>${orderList.userId}</td>
											<td>${orderList.koblNo}</td>
											<td>${orderList.trkNo}</td>
											<td>${orderList.orderNo}</td>
											<td>${orderList.attnName}</td>
											<td>${orderList.attnTel}</td>
											<td>${orderList.attnEmail}</td>
											<td>${orderList.itemDetail}</td>
											<td>${orderList.itemCnt}</td>
											<td>
												<c:if test="${orderList.state eq 'B001'}">
													<input type="button" id="modi-btn" class="buttons" onclick="fn_whIn('${orderList.nno}')" value="입고처리">	
												</c:if>
												<c:if test="${orderList.state eq 'C001'}">
													<input type="button" id="modi-btn" class="buttons" onclick="fn_inspProc('${orderList.nno}')" value="검수작업">	
												</c:if>
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
					</form>
					<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp"%>
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
    			$("#returnMenu-2").toggleClass('active');
            });

          	$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			});
    	  	
	        $("#search-btn").on('click', function() {
				$("#returnForm").submit();
		    });

		    $("#barcodeNo").on('keydown', function(e) {
			    if (e.keyCode === 13) {
			    	if ($("#barcodeNo").val() == "") {
					    alert("송장번호 입력 또는 바코드를 스캔해 주세요");
					    return false;
					} else {
						var barcodeNo = $("#barcodeNo").val();
						$.ajax({
							url : '/mngr/return/whInProcess',
							type : 'POST',
							beforeSend : function(xhr)
							{
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data : {barcodeNo: barcodeNo},
							success : function(result) {
								if (result.STATUS == 'F10') {
									alert(result.MSG);
								} else if (result.STATUS == 'S20') {
									alert(result.MSG);
								} else {
									var nno = result.NNO;
									window.open("/mngr/return/whInProc?nno="+nno);
									location.reload();
								}
							},
							error : function(xhr, status) {
								alert("시스템 오류가 발생 하였습니다. 다시 시도해 주세요.");
							}
						})
					}   
				}
			});
		});

      	// 입고처리
      	function fn_whIn(nno) {
          	$.ajax({
              	url : '/mngr/return/orderUpWhIn',
              	type : 'POST',
				beforeSend : function(xhr)
				{
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : {nno: nno},
              	error : function(xhr, status) {
                  	alert("입고 처리에 실패 하였습니다."); 	
                },
                success : function(response) {
                    alert("입고 처리 되었습니다.");
                    location.reload();
                }
            })
        }

      	// 검수작업
        function fn_inspProc(nno) {
            location.href = "/mngr/return/whInProc?nno="+nno;
        }


      </script>
      <!-- script addon end -->

   </body>
</html>