<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<style type="text/css">
#result-table {
	border: 1px solid #e6e6e6;
	border-collapse: collapse;
}

#result-table th {
	padding: 10px 15px;
	color: #fff;
	background: #1E3269;
	border: 1px solid #fff;
	text-align: center;
}

#result-table td {
	padding: 10px 15px;
	border: 1px solid #e6e6e6;
	text-align: center;
	color: black;
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
<title>입고 현황</title>
<body class="no-skin">
	<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>재고관리</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">입고 현황</strong></div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>
						입고 현황
					</h3>
					<span class="red">입고 여부 확인 가능 시간 09:00 ~ 17:30 (주말/공휴일 제외, 영업일 기준)</span>
				</div>
				<div class="col-xs-12 col-sm-12" id="inner-content-side">
					<div id="search-div">
						<h3 style="font-weight:700;color:#6e6e6e;">※ ACI 접수번호 또는 상품 Tracking Number 입력 후 엔터</h3>
						<div style="box-sizing:border-box;background:#e6e6e6;width:60%;">
						<input type="text" id="inputNumber" style="width:100%;font-size:50px;">
						</div>
						<div style="width:60%;box-sizing:border-box;display:none;" id="result-block">
							<br/>
							<table style="width:100%;" id="result-table">
								<thead>
									<tr>
										<th style="width:20%;">입고 여부</th>
										<th style="width:20%;">입고 송장번호</th>
										<th style="width:20%;">입고 일자</th>
										<th style="width:15%;">무게 (kg)</th>
										<th style="width:25%;">부피 (가로x세로x높이)</th>
									</tr>	
								</thead>
								<tbody>
									<td id="td_whInYn"></td>
									<td id="td_trackNo"></td>
									<td id="td_whInDate"></td>
									<td id="td_wta"></td>
									<td id="td_volume"></td>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
	<!-- ace scripts -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		var loading = "";
		loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
		
		$("#inputNumber").keyup(function(e) {
			if (e.which == 13) {
				
				if ($("#inputNumber").val() == "") {
					alert("조회할 번호를 입력해 주세요");
					return false;
				}

				var trackNo = $("#inputNumber").val();
				
				loading.show();
				
				$.ajax({
					url : '/cstmr/stock/checkTrackingNo',
					type : 'POST',
					data : {trackNo: trackNo},
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(response) {
						console.log(response);
						var Status = response.Status;
						var StatusCode = response.StatusCode;

						if (Status != "Success") {
							if (StatusCode == "F0") {
								alert("시스템 오류가 발생 하였습니다.");
								$("#result-table td").text("");
							} else if (StatusCode == "F1") {
								var data = response.Data;
								var volumeText = "";
								$("#result-block").css("display", "block");
								
								$("#td_whInYn").text(data.whinYn);
								$("#td_trackNo").text(data.trackNo);
								$("#td_whInDate").text(data.whinDate);
								$("#td_wta").text(data.wta);
								$("#td_volume").text(volumeText);
							}
						} else {
							var data = response.Data;
							var volumeText = data.width + " x " + data.length + " x " + data.height + " (cm)";
							$("#result-block").css("display", "block");
							
							$("#td_whInYn").text(data.whinYn);
							$("#td_trackNo").text(data.trackNo);
							$("#td_whInDate").text(data.whinDate);
							$("#td_wta").text(data.wta);
							$("#td_volume").text(volumeText);
						}
						loading.hide();
					},
					error : function(xhr, status) {
						$("#result-table td").text("");
						loading.hide();
						alert("시스템 오류가 발생 하였습니다.");
					}
				})
				
			} 
		})
	</script>
	<!-- script addon end -->
</body>
</html>