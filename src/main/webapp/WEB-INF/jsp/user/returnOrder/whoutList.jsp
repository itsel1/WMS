<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>반품 접수 내역</title>
<head>
<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>

<style type="text/css">
	.tbl-return {
		margin-top: 14px;
		box-shadow: 0px 0px 5px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-return table {
		width: 100%;
		table-layout: fixed;
	}
	
	.tbl-return .tbl-header {
		background-color: #527d96;
		box-shadow: 0px -1px 3px rgba(82, 125, 150, 0.6);
	}
	
	.tbl-return .tbl-content {
		max-height: 50vh;
		height: auto;
		overflow-x: auto;
		margin-top: 0px;
	}
	
	.tbl-return th {
		text-align: left;
		font-weight: 600;
		font-size: 13px;
		color: #fff;
		text-transform: uppercase;
		text-align: center;
		padding: 10px;
		word-break: break-all;
	}
	
	.tbl-return td {
		padding-top: 3px;
		padding-bottom: 3px;
		text-align: center;
		vertical-align: middle;
		font-weight: 300;
		font-size: 12px;
		border-bottom: 1px solid #e8e8e8;
		word-break: break-all;
	}
	
	.tbl-content::-webkit-scrollbar {
		width: 6px;
	}
	
	.tbl-content::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-content::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
	}
	
	.select-box {
		display: flex;
		flex-direction: row;
		margin-left: 2px;
	}
	
	.select {
		width: 150px;
		display: inline-block;
		background-color: #fff;
		border-radius: 2px;
		box-shadow: 0 0 2px rgb(204, 204, 204);
		transition: all .5s ease;
		position: relative;
		font-size: 14px;
		color: #474747;
		height: 100%;
		text-align: left;
		outline: none;
	}
	
	.select .option-default {
		cursor: pointer;
		display: block;
		padding: 10px;
		outline: none;
	}
	
	.select .option-default > i {
		font-size: 13px;
		color: #888;
		cursor: pointer;
		transition: all .3s ease-in-out;
		float: right;
		line-height: 20px;
	}
	
	.select:hover {
		box-shadow: 0 0 4px rgb(204, 204, 204);
	}
	
	.select:active {
		background-color: #f8f8f8;
		outline: none;
	}
	
	.select.active:hover, .select.active {
		box-shadow: 0 0 4px rgb(204, 204, 204);
		border-radius: 2px 2px 0 0;
		background-color: #f8f8f8;
		outline: none;
	}
	
	.select.active .option-default > i {
		transform: rotate(-90deg);
	}
	
	.select .option-list {
		position: absolute;
		background-color: #fff;
		width: 100%;
		left: 0;
		margin: 1px 0 0 0;
		box-shadow: 0 1px 2px rgb(204, 204, 204);
		border-radius: 0 1px 2px 2px;
		overflow: hidden;
		display: none;
		max-height: 144px;
		overflow-y: auto;
		z-index: 9999;
	}
	
	.select .option-list li {
		padding: 10px;
		transition: all .2s ease-in-out;
		cursor: pointer;
	}
	
	.select .option-list {
		padding: 0;
		list-style: none;
	}
	
	.select .option-list li:hover {
		background-color: #f2f2f2;
	}
	
	.select .option-list li:active {
		background-color: #e2e2e2;
	}
	
	.option-list::-webkit-scrollbar {
		width: 6px;
	}
	
	.option-list::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
	}
	
	.option-list::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
	}
	
	#keyword {
		width:300px;
		border: 1px solid rgba(204, 204, 204, 0.3);
		box-shadow: 0 0 2px rgb(204, 204, 204);
		outline: none;
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

</style>
</head>
	<body class="no-skin"> 
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">출고 내역</strong>
						</div>
		        	</div>
		      	</div>
		    </div>
	    </div>
	    <div>
			<form name="returnForm" id="returnForm" method="get" action="/cstmr/return/whoutList">
				<div class="container">
			       <div class="page-content noneArea">
						<div id="inner-content-side" >
							<h2 style="margin-left:4px;">반품 출고 내역</h2>
							<br>
							<table id="search-table">
								<tr>
									<th>출고일자</th>
									<td><input type="text" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}"></td>
									<td>-</td>
									<td><input type="text" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}"></td>
									<th>원송장번호</th>
									<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
									<th>반송장번호</th>
									<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
									<th>출고송장번호</th>
									<td><input type="text" name="hawbNo" id="hawbNo" value="${params.hawbNo}"></td>
									<td>
										<input type="button" id="search-btn" value="검색">
									</td>
								</tr>
							</table>
							<div class="tbl-return">
								<div class="tbl-header">
									<table cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th style="border-right:1px solid #fff;width:10%;">작업일자</th>
												<th style="border-right:1px solid #fff;width:10%;">택배사</th>
												<th style="border-right:1px solid #fff;width:10%;">출고송장번호</th>
												<th style="border-right:1px solid #fff;width:10%;">원송장번호</th>
												<th style="border-right:1px solid #fff;width:10%;">반송장번호</th>
												<th style="border-right:1px solid #fff;width:20%;">대표상품명</th>
												<th style="border-right:1px solid #fff;width:20%;">비고</th>
												<th style="width:10%;"></th>
											</tr>
										</thead>
									</table>
								</div>
								<div class="tbl-content">
									<table cellpadding="0" cellspacing="0" border="0">
										<tbody>
											<c:choose>
												<c:when test="${!empty orderList}">
													<c:forEach items="${orderList}" var="orderList" varStatus="status">
														<tr>
															<td style="width:10%;">${orderList.WDate}</td>
															<td style="width:10%;">${orderList.transCode}</td>
															<td style="width:10%;">
																${orderList.hawbNo}
																<c:if test="${!empty orderList.agencyBl}">
																	<br>${orderList.agencyBl}
																</c:if>
															</td>
															<td style="width:10%;">
																<a href="/cstmr/return/stockListDetail?nno=${orderList.nno}">${orderList.koblNo}</a>
															</td>
															<td style="width:10%;">
																<a href="/cstmr/return/stockListDetail?nno=${orderList.nno}">${orderList.trkNo}</a>
															</td>
															<td style="width:20%;">${orderList.itemDetail}</td>
															<td style="text-align:right;padding:0px;width:20%;">
																<span style="font-size:12px;"><a style="cursor:pointer;" onclick="popupMemo('${orderList.nno}')">[고객]</a></span>
																<input type="text" style="border-radius:5px !important;width:80%;height:25px;font-size:11px;" value="${orderList.userMemo}">
																<br/>
																<span style="font-size:12px;"><a style="cursor:pointer;" onclick="popupMemo('${orderList.nno}')">[관리자]</a></span>
																<input type="text" style="border-radius:5px !important;width:80%;height:25px;font-size:11px;" value="${orderList.adminMemo}">
															</td>
															<td style="width:10%;">
																<c:if test="${!empty orderList.mawbNo}">
																	<a style="cursor:pinter;" onclick="fn_openPod('${orderList.hawbNo}')">[Tracking]</a>	
																</c:if>
																<c:if test="${empty orderList.mawbNo}">
																	출고 대기 중	
																</c:if>
															</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="10" style="text-align:center;">검색 결과가 존재하지 않습니다.</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<br>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</div>
			</form>
		</div>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	<script src="/testView/js/main.js"></script>
	
	<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
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
	
	<script src="/assets/js/bootstrap-tag.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<script type="text/javascript">
		jQuery(function($) {
			$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})
			
	        $("#search-btn").on('click', function() {
		        if ($("#keyword").val() == "") {
					if ($("#state").val() == "") {
						$("#state").val("all");
					}
			    } else {
					if ($("#searchType").val() == "") {
						alert("검색 항목을 선택해주세요.");
						return;
					}
				}
				$("#returnForm").submit();
		    });
		});

	 function popupMemo(nno) {
            var url = "/comn/returnCsMemo?nno="+nno;
           	window.open(url, 'popupMemo', 'width=500,height=600');
		}

		function fn_openPod(hawbNo) {
			var url = "/comn/deliveryTrack/"+hawbNo;
			window.open(url, 'popupPod', 'width=600,height=800');
		}
	</script>
</body>
</html>