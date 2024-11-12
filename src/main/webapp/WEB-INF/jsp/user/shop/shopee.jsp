<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}

.inner-content-side>div {
	text-align: center;
}

input[type=text]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}
input[type=password]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

.vertiMidle{
	vertical-align: middle;
}

.pd-1rem{
	padding: 1rem 1rem;
	font-size: 14px; 
	font-weight: 400;
}

#shopee_table {
	width: 100%;
	border: 1px solid #e6e6e6;
}

#shopee_table th, td {
	border: 1px solid #e6e6e6;
	border-collapse: border-collapse;
	padding: 5px;
	text-align: center;
}

#shopee_table th {
	text-align: center;
}

.active-button {
	border: none;
	font-weight: 500;
	background: none;
	margin: 1px;
	cursor: pointer;
}

.use-icon:hover {
	font-weight: 700;
	font-size: 14px;
}

.fa-exclamation-circle {
	color: DarkSalmon;
	font-weight: normal !important;
	cursor: help;
}



</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>쇼핑몰 연동</title>
<body class="no-skin">
	<div class="site-wrap">
	<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>쇼핑몰 연동</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">Shopee</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h1>Shopee 연동</h1>
							</div>
							<form name="userInfoForm" id="userInfoForm">
								<input type="hidden" id="statusCode" value="${status}"/>
								<div id="inner-content-side">
									<div class="row">
										<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
											<div class="col-xs-12 col-lg-12" style="padding: 2rem !important;">
												<%-- <div class="row form-group hr-8">
													<label style="font-weight:bold;">Shopee 앱 정보</label>
												</div>
												<div class="space-10"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-4 col-xs-12">
														<label><i class="fa fa-circle red vertical-middle" style="font-size:8px;"></i> Live Partner ID</label>
														<input class="width-100" type="text" name="partnerId" id="partnerId" value="${shopeeAppInfo.partnerId}"/>
													</div>
													<div class="col-lg-4 col-xs-12">
														<label><i class="fa fa-circle red vertical-middle" style="font-size:8px;"></i> Live Partner Key</label>
														<input class="width-100" type="text" name="partnerKey" id="partnerKey" value="${shopeeAppInfo.partnerKey}"/>
													</div>
													<div class="col-lg-4 col-xs-12">
														<label><i class="fa fa-circle red vertical-middle" style="font-size:8px;"></i> Live Key Expiry Date</label>
														<input class="width-100" type="text" name="keyExpiryDate" id="keyExpiryDate" value="${shopeeAppInfo.keyExpiryDate}" readonly/>
													</div>
												</div>
												<div class="space-10"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12 text-center">
														<input type="button" id="auth-btn" class="btn-success" value="연동하기"  onclick="fn_integrateShopee();"/>
													<!-- <input type="button" id="save-btn" class="btn-primary" value="저장"/> -->
													</div>
													<!-- <div class="col-lg-6 col-xs-12">
													<input type="button" id="auth-btn" class="btn-success" value="인증받기"/>
													</div> -->
												</div>
												<div class="space-20"></div> --%>
												<div class="row form-group hr-8">
													<label style="font-weight:bold;">Shopee 연동 정보</label>
													<input type="button" class="btn-success" value="연동하기" id="auth-btn" style="margin-left:5px;"/>
													<!-- <i class="fa fa-exclamation-circle"  style="color:DodgerBlue;margin-left:5px;margin-right:5px;cursor:default;"></i>
													재 인증 완료 시 기존 연동 정보는 초기화 됩니다 -->
													<div class="space-8"></div>
													<div class="col-lg-12 col-xs-12"> 
														<table id="shopee_table">
															<thead>
																<tr>
																	<th>상점명</th>
																	<th>지역 코드</th>
																	<th>셀러 타입</th>
																	<th>만료일자</th>
																	<th>활성화 여부
																		<i class="fa fa-exclamation-circle" title="Shopee API 연동 시 활성화 상태인 상점의 데이터만 조회됩니다."></i>
																	</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shopeeInfoList}">
																<c:forEach items="${shopeeInfoList}" var="shopee" varStatus="status">
																<tr>
																	<td>${shopee.shopName}
																		<input type="hidden" name="shopeeId" id="shopeeId" value="${shopee.shopeeId}">
																	</td>
																	<td>${shopee.shopRegion}</td>
																	<td>
																	<c:if test="${shopee.merchantYn eq 'Y'}">Merchant</c:if>
																	<c:if test="${shopee.merchantYn eq 'N'}">Shop</c:if>
																	</td>	
																	<td>
																		<fmt:parseDate value="${shopee.authExpires}" var="authExpires" pattern="yyyy-MM-dd HH:mm" />
																		<fmt:formatDate value="${authExpires}" pattern="yyyy-MM-dd HH:mm" />
																	</td>
																	<td>
																	<c:if test="${shopee.useYn eq 'Y'}">
																	<button type="button" onclick="fn_inactive('${shopee.userId}', '${shopee.shopeeId}', 'N')" class="active-button active">
																		<i class="fa fa-check use-icon"  style="color:DodgerBlue;"></i>
																	</button>
																	</c:if>
																	<c:if test="${shopee.useYn eq 'N'}">
																	<button type="button" onclick="fn_active('${shopee.userId}', '${shopee.shopeeId}', 'Y')" class="active-button inactive">
																		<i class="fa fa-close use-icon"  style="color:IndianRed;"></i>
																	</button>
																	</c:if>
																	</td>
																</tr>
																</c:forEach>
																</c:if>
																<c:if test="${empty shopeeInfoList}">
																<tr>
																	<td colspan="5">연동 정보가 없습니다</td>
																</tr>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<!-- SideMenu Start -->
		<!-- SideMenu End -->

		<div class="main-content">

			
		</div>
		<!-- /.main-content -->
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
    <script src="/assets/js/bootstrap-datepicker.min.js"></script>
    <script src="/assets/js/bootstrap-timepicker.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			
			$(document).ready(function(e) {
				if ($("#statusCode").val() != "") {
					if ($("#statusCode").val() == "0") {
						alert("Shopee 토큰 발급에 실패 하였습니다.\n다시 시도하거나 관리자에게 문의해 주세요.");
					}
				}
			});
			
			
			$('#keyExpiryDate').datepicker({
				autoclose: true,
				todayHightlight: true,
				format: 'yyyy-mm-dd',
			});
			
			
			$("#auth-btn").click(function(e) {

				$.ajax({
					url : '/cstmr/integrate/shopeeAuth',
					type : 'POST',
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						
						if (data == null || data == "") {
							alert("인증 페이지 URL 생성에 실패 하였습니다. 다시 시도해 주세요.");
							return false;
						}
						
						window.open(data);
						
					},
					error : function(error) {
						alert("처리 중 시스템 오류가 발생 하였습니다.\n관리자에게 문의해 주세요.");
						return false;
					}
				});
			});
		});
		
		function fn_active(userId, shopId, useYn) {
			if (confirm("해당 상점 연동을 활성화 하시겠습니까?")) {
				updateUseYn(userId, shopId, useYn);
			}
		}
		
		function fn_inactive(userId, shopId, useYn) {
			if (confirm("해당 상점 연동을 비활성화 하시겠습니까?")) {
				updateUseYn(userId, shopId, useYn);
			}
		}
		
		function updateUseYn(userId, shopId, useYn) {
			
			var formData = {userId: userId, id: shopId, useYn: useYn};
			
			$.ajax({
				url : '/cstmr/integrate/updateShopeeInfo',
				type : 'POST',
				data : formData,
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(response) {
					var msg = response.msg;
					alert(msg);
					location.reload();
				},
				error : function(error) {
					alert("변경 요청 중 시스템 오류가 발생 하였습니다.\n관리자에게 문의해 주세요.");
					return false;
				}
			});
		}
		
		function fn_integrateShopee() {
			
			if ($("#partnerId").val() == "") {
				$("#partnerId").focus();
				return false;
			}
			
			if ($("#partnerKey").val() == "") {
				$("#partnerKey").focus();
				return false;
			}
			
			if ($("#keyExpiryDate").val() == "") {
				$("#keyExpiryDate").focus();
				return false;
			}
			
			
			var formData = {partnerKey: $("#partnerKey").val(), partnerId: $("#partnerId").val(), keyExpiryDate: $("#keyExpiryDate").val()};
			
			$.ajax({
				url : '/cstmr/integrate/linkShopeeAuthPage',
				type : 'POST',
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : formData,
				success : function(data) {
					
					if (data == null || data == "") {
						alert("인증 페이지 URL 생성에 실패 하였습니다. 다시 시도해 주세요.");
						return false;
					}
					
					window.open(data);
					
				},
				error : function(error) {
					alert("처리 중 시스템 오류가 발생 하였습니다.\n관리자에게 문의해 주세요.");
					return false;
				}
			});
		}
	</script>
	<!-- script addon end -->
</body>
</html>