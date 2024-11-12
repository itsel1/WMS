<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>반품 엑셀 등록</title>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>

<style type="text/css">
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
	}
	
	.tbl-return td {
		padding: 15px;
		text-align: center;
		vertical-align: middle;
		font-weight: 300;
		font-size: 12px;
		border-bottom: 1px solid #e8e8e8;
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
	
	#excelUp {
		width: 70px;
		height: 30px;
		background-color: #527d96;
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
	}
	
	#excelUp:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3); 
	}
	
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
	}
	
	.buttons:hover, .buttons:active {
		box-shadow: 1px 1px 3px rgba(82, 125, 150, 0.3);
	}
	
	#excelSample {
		margin-left: 10px;
		margin-right: 10px;
		cursor: pointer;
		font-weight: 700;
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
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">엑셀 등록</strong>
						</div>
		        	</div>
		      	</div>
		    </div>
	    </div>
	    <div>
			<form name="returnForm" id="returnForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="container">
			       <div class="page-content noneArea">
						<div id="inner-content-side" >
							<h2 style="margin-left:4px;">엑셀 등록</h2>
							<br>
							<table id="search-table">
								<tr>
									<th>파일 업로드</th>
									<td><input type="file" id="excelFile" name="excelFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"></td>
									<td><input type="button" id="excelUp" value="업로드"></td>
									<td><a href="/cstmr/return/excelFormDown" id="excelSample">양식 다운로드</a></td>
								</tr>
							</table>
							<%-- <table id="search-table">
								<tr>
									<th>주문일자</th>
									<td><input type="text" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}"></td>
									<td>-</td>
									<td><input type="text" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}"></td>
									<th>원송장번호</th>
									<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
									<th>반송장번호</th>
									<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
									<td>
										<select id="status" name="status">
											<option value="">::선택::</option>
											<option value="A000" <c:if test="${params.state eq 'A000'}"> selected </c:if>>1차접수</option>
											<option value="A001" <c:if test="${params.state eq 'A001'}"> selected </c:if>>접수신청</option>
											<option value="A002" <c:if test="${params.state eq 'A002'}"> selected </c:if>>접수완료</option>
											<option value="B001" <c:if test="${params.state eq 'B001'}"> selected </c:if>>수거중</option>
											<option value="C001" <c:if test="${params.state eq 'C001'}"> selected </c:if>>입고</option>
										</select>
									</td>
									<td>
										<input type="button" id="search-btn" value="검색">
									</td>
								</tr>
							</table> --%>
							<div class="tbl-return">
								<div class="tbl-header">
									<table cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th style="border-right:1px solid #fff;width:10%;">원운송장번호</th>
												<th style="border-right:1px solid #fff;width:10%;">주문번호</th>
												<th style="border-right:1px solid #fff;width:7%;">도착지</th>
											</tr>
										</thead>
									</table>
								</div>
								<div class="tbl-content">
									<table cellpadding="0" cellspacing="0" border="0">
										<tbody>
											
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
			$(document).ready(function() {
				
			});

			$("#excelUp").on('click', function(e) {
				if ($("#excelFile").val() == "") {
					alert("파일을 선택해 주세요.");
					return;
				}

				var formData = new FormData();
				formData.append("file", $("#excelFile")[0].files[0]);
				LoadingWithMask();

				$.ajax({
					type : "POST",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					url : "/cstmr/return/orderExcelUp",
					data : formData,
					processData: false,
		            contentType: false,
					error : function(request, error) {
						$('#mask').hide();
						alert("엑셀 업로드 중 오류가 발생 하였습니다.");
					},
					success : function(data) {
						$('#mask').hide();
						location.reload();
					}
				});
			});
		});

		function LoadingWithMask() {
		    //화면의 높이와 너비를 구합니다.
		    var maskHeight = $(document).height();
		    var maskWidth  = window.document.body.clientWidth;
		     
		    //화면에 출력할 마스크를 설정해줍니다.
		    var mask = "<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
		  
		    //화면에 레이어 추가
		    $('body').append(mask);
		        
		    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
		    $('#mask').css({
	            'width' : maskWidth,
	            'height': maskHeight,
	            'opacity' :'0.3'
		    });
		  
		    //마스크 표시
		    $('#mask').show();  
		    //로딩중 이미지 표시
		}
	</script>
</body>
</html>