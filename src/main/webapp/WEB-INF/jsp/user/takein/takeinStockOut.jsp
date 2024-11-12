<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
	<style type="text/css">
		.colorBlack {
			color: #000000 !important;
		}
		.noneArea {
			margin:0px !important;
			padding:0px !important;
			border-bottom : none;
		}
		.containerTable {
			width: 100% !important;
		}

		table ,tr td{
			word-break:break-all;
		}
	</style>
	<!-- basic scripts -->
	</head> 
	<title>상품정보</title>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>사입 관리</a> <span class="mx-2 mb-0">/
	          </span> <strong class="text-black">일자별 재고현황</strong></div>
	        </div>
	      </div>
	    </div>
	    <!-- Main container Start-->
		     <div class="container">
		       <div class="page-content noneArea">
						<div class="page-header noneArea">
							<h3>
								일자별 출고현황
							</h3>
						</div>
							<div id="inner-content-side" >
									<br>
									<div class="col-xs-12 form-group" style="margin:0px;">
											<div class="row">											
												<form id="condForm" action="/cstmr/takein/takeinStockOut" method="get" enctype="multipart/form-data">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													
													
													<table  class="table table-bordered" style="max-width:1200px;float:left;">
														<thead>
															<tr>
																<th style="width:100px;" class="center colorBlack">일시</th>
																<th style="padding:0px;"><input class="searchValue" id="fromDate" type="text" name="fromDate" style="width:100%" value="${parameterInfo.fromDate}"/></th>
																<th style="width:10px;" class="center colorBlack">~</th>
																<th style="padding:0px;"><input  id="toDate" class="searchValue" type="text" name="toDate" style="width:100%" value="${parameterInfo.toDate}"/></th>
																<th style="width:100px;" class="center colorBlack">Station</th>
																<td style="padding:0px;padding-top:5px;">
																<select name="orgStation" style="width:100%">
																	<option value=""
																			<c:if test="${parameterInfo.orgStation == ''}" >
																				selected 
																			</c:if>
																		>::전체::${parameterInfo.orgStation}</option>
																	<c:forEach items="${stockStationList}" var="stockStationList" varStatus="status">
																		<option value="${stockStationList.orgStation}"
																			<c:if test="${parameterInfo.orgStation == stockStationList.orgStation}" >
																				selected 
																			</c:if>
																			>${stockStationList.stationName}</option>
																	</c:forEach>
																</select> 
																</td>
																<th style="width:100px;" class="center colorBlack">상품 코드</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="cusItemCode" style="width:100%" value="${parameterInfo.cusItemCode}"/></th>
																<th style="width:100px;" class="center colorBlack">상품명</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="itemDetail" style="width:100%" value="${parameterInfo.itemDetail}"/></th>
																<th class="center" ><input style="width:100%;padding:0px;margin:0px;" class="btn-primary" type="submit" value="조회" id="btn_search"/></th>
															</tr>
														</thead>
													</table>
												</form>
											</div>
									</div>
							</div>
									<div style="text-align:right;float:right">
										<input type="button" class="btn-primary" value="Excel Down" id="btnExcelDown"  >
									</div>
									<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
										<div class="display" >
											<form id="list_form" action="" method="post">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<br>
											<table id="dynamic-table" class="table table-bordered table-striped" Style="margin-top:10px;" >
												<thead>
													<tr>
														<th class="center colorBlack">
															Station
														</th>
														<th class="center colorBlack">
															상품코드
														</th>
														<th class="center colorBlack">
															상품명
														</th>
														<th class="center colorBlack">
															출고수량
														</th>													
														<th class="center colorBlack" >
															재고수량
														</th>
													</tr>
												</thead>												
												<tbody>
												 <c:forEach items="${listRst}" var="listRst" varStatus="status">
														<tr>
														<td class="center">
																${listRst.stationName}
															</td>	
															<td class="center">
																${listRst.cusItemCode}
															</td>															
															<td class="left">
																${listRst.itemDetail}
															</td>
															<td   style="text-align:right;">
																${listRst.outCnt}
															</td>
															<td  style="text-align:right;">
																${listRst.stockCnt}
															</td>
													    </tr> 	 
													</c:forEach> 
												</tbody>
											</table>
											</form>
										</div>
											<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
									</div>
							</div>
					</div>
				</div>

		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
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
		
		<script type="text/javascript">  
		    $(document).ready(function(e){
		    	$("#btnExcelDown").click(function() {
		    		$("#condForm").attr('action','/cstmr/takein/takeinStockOutExcelDown');
		    		$("#condForm").submit();
		    		$("#condForm").attr('action','/cstmr/takein/takeinStockOut');
		    	});
		    });
	    </script> 
		
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
		

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#fromDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
				j$( "#toDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>
				

		
		<!-- script addon end -->
	</body>
</html>
