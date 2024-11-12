<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
<link rel="stylesheet" href="/testView/css/jquery-ui.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<style type="text/css">
	#main-content {
		width: 100%;
		box-sizing: border-box;
	}
	
	#search-content {
		width: 100%;
		box-sizing: border-box;
		display: flex;
		flex-direction: row;
		flex-wrap: nowrap;
		justify-content: flex-start;
		align-content: center;
		align-items: stretch;
	}
	
	#search-content span {
		display: flex;
	    align-items: center;
	    padding-left: 10px;
	    padding-right: 10px;
	    background-color: #e6e6e6;
	    font-weight: 600;
	    border: :1px solid #e6e6e6;
	}

	#search-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #666;
		color: #fff;
		border: 1px solid #282828;
	} 
	
	#data-table {
		width: 100%;
		border-collapse: collapse;
		border: 1px solid #e6e6e6;
	}
	
	#data-table td {
		padding: 8px;
		border-collapse: collapse;
		border: 1px solid #e6e6e6;
	}
	
	#data-table th {
		text-align: center;
		background: #666;
		border: 1px solid #e6e6e6;
		border-collapse: collapse;
		color: #fff;
		z-index: 1;
		padding: 10px 15px;
	}
	
	#wrapper {
		border: 1px solid #e6e6e6;
		width: 100%;
		max-height: 500px;
		overflow-y: auto;
		margin-top: 10px;
	}
	
	.sticky-header {
		position: sticky;
		top: 0px;
		height: 30px;
		border: 1px solid #e6e6e6;
	}

	#wrapper::-webkit-scrollbar {
		width: 10px;
	}
	
	#wrapper::-webkit-scrollbar-thumb {
		background-color: #666;
		border-radius: 10px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	
	#wrapper::-webkit-scrollbar-track {
		background-color: GhostWhite;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
	
	#excel-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #337ab7;
		color: #fff;
		border: 1px solid #337ab7;
		margin-left: 5px;
	}
	
	#loading {
		height: 100%;
		left: 0px;
		position: fixed;
		_position: absolute;
		top: 0px;
		width: 100%;
		filter: alpha(opacity = 50);
		-moz-opacity: 0.5;
		opacity: 0.5;
	}
	
	.loading {
		background-color: white;
		z-index: 999999;
	}
	
	#loading_img {
		position: absolute;
		top: 50%;
		left: 50%;
		/* height: 200px; */
		margin-top: -75px; 
		margin-left: -75px; 
		z-index: 999999;
	}
</style>
<!-- basic scripts -->
</head> 
<title>배송대행 신청 - 등록 내역</title>
<body class="no-skin">
		<!-- headMenu Start -->
	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> 
						<strong class="text-black">등록 내역</strong>
					</div>
				</div>
			</div>
		</div>
		    <!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>등록 내역</h3>
				</div>
				<form name="form" id="form" action="/cstmr/apply/shpngList" method="GET">
					<div id="main-content">
						<div id="search-content">
							<span>Date</span>
							<input type="text" name="fromDate" id="fromDate" value="${parameters.fromDate}" autocomplete="off">
							<span>~</span>
							<input type="text" name="toDate" id="toDate" value="${parameters.toDate}" autocomplete="off">
							<span>BL No</span>
							<input type="text" name="hawbNo" id="hawbNo" value="${parameters.hawbNo}" autocomplete="off">
							<span>Order No</span>
							<input type="text" name="orderNo" id="orderNo" value="${parameters.orderNo}" autocomplete="off">
							<button type="button" id="search-button">search</button>
							<button type="button" id="excel-button">Excel Download</button>
						</div>
						<br/>
						<div id="table-content" style="width:100%;">
							<div id="wrapper">
								<table id="data-table">
									<thead>
										<tr>
											<th class="sticky-header">NO</th>
											<th class="sticky-header">등록일</th>
											<th class="sticky-header">도착국가</th>
											<th class="sticky-header">Hawb No</th>
											<th class="sticky-header">Match No</th>
											<th class="sticky-header">Order No</th>
											<th class="sticky-header">수취인</th>
											<th class="sticky-header">대표 상품</th>
											<th class="sticky-header">상품 금액</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list}" var="list" varStatus="status">
											<c:if test="${list.subNo eq 1}">
											<tr>
												<td style="text-align:center;">${list.rnum}</td>
												<td style="text-align:center;">${fn:substring(list.nno,0,8)}</td>
												<td style="text-align:center;">${list.dstnNationName}</td>
												<td style="text-align:center;">${list.hawbNo}</td>
												<td style="text-align:center;">
													<c:if test="${!empty list.matchNo}">
													${list.matchNo}
													</c:if>
													<c:if test="${empty list.matchNo}">
													${list.hawbNo}
													</c:if>
												</td>
												<td style="text-align:center;">${list.orderNo}</td>
												<td style="text-align:center;">${list.cneeName}</td>
												<td style="text-align:center;">${list.itemDetail}</td>
												<td style="text-align:center;">${list.totalItemValue}</td>
											</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<br/>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</form>
			</div>
		</div>
	</div>
		
			
		
	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	<!-- Footer End -->
	
	<script src="/testView/js/jquery-3.3.1.min.js"></script>
	<script src="/testView/js/jquery-ui.min.js"></script>
	<!-- script on paging end -->
	
	<!-- script addon start -->
		
		
	<script type="text/javascript">
		var loading = "";
		jQuery(function($) {
			loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
			$(document).ready(function() {
	
			});

			$("#fromDate").datepicker({
				dateFormat: 'yymmdd'
			});
	
			$("#toDate").datepicker({
				dateFormat: 'yymmdd'
			});

			$("#search-button").on('click', function(e) {
				$("#form").submit();
			});
	
			$("#excel-button").on('click', function(e) {
				if ($("#fromDate").val() == "" || $("#toDate").val() == "") {
					alert("기간을 입력해 주세요");
					return;
				}
	
				var formData = $("#form").serialize();

				loading.show();
	
				$.ajax({
					url : '/cstmr/apply/shpngListExcelDown',
					method : 'GET',
					data : formData,
					xhrFields : {
						responseType : 'blob'
					},
					success : function(response) {
					    var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
					    var link = document.createElement('a');
					    link.href = window.URL.createObjectURL(blob);
					    link.download = getToday()+'_shipment_list.xlsx';
					    link.click();
					    loading.hide();
					},
					error : function(xhr, status) {
						alert("시스템 오류가 발생 하였습니다");
						loading.hide();
						return;	
					}
				});
			});
		});

		function getToday(){
		    var date = new Date();
		    var year = date.getFullYear();
		    var month = ("0" + (1 + date.getMonth())).slice(-2);
		    var day = ("0" + date.getDate()).slice(-2);

		    return year + month + day;
		}
	</script>
		<!-- script addon end -->
</body>
</html>
