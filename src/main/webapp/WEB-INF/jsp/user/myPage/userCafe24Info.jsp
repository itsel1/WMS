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

</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>마이페이지</title>
<body class="no-skin">
	<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>마이 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">정보 수정</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h1>Cafe24 Api 정보</h1>
							</div>
							<form name="Form" id="mainForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="inner-content-side">
									<!--  -->
									<div class="row">
											<!-- 기본정보 Start -->
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>API 정보</h2>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="row form-group hr-8">
													<table  class="table table-bordered" border="1" style="width:800px;">
														<thead>
														<tr>
															<th style="padding:10px;width:180px;">Mall ID</th>
															<td><input type="text" name="mallId" style="width:75%;" value="${cafe24UserInfo.cafe24Id}">
																<button id="updateInfo" type="button" class="btn btn-sm btn-primary">확인</button>
															</td>
														</tr>
														</thead>
													</table>	
												</div>
												<div class="space-20"></div>
											
												<table  class="table table-bordered" border="1">
													<thead>
													<tr>
														<th>CAFE24_ID</th>
														<th>MALL_ID</th>
														<th>ACCESS_TOKEN</th>
														<th>EXPIRES_AT</th>
														<th>REFRESH_TOKEN</th>
														<th>REFRESH_TOKEN_EXPIRES_AT</th>
														<th>CLIENT_ID</th>
														<th>ISSUED_AT</th>
													</tr>
													</thead>
													<tbody>
														<c:forEach items="${cafe24UserTokenInfo}" var="cafe24UserTokenInfo" varStatus="status">
														 <tr>
														 	<td>${cafe24UserTokenInfo.cafe24Id}</td>
														 	<td>${cafe24UserTokenInfo.mallId}</td>
														 	<td>${cafe24UserTokenInfo.accessToken}</td>
														 	<td>${cafe24UserTokenInfo.expiresAt}</td>
														 	<td>${cafe24UserTokenInfo.refreshToken}</td>
														 	<td>${cafe24UserTokenInfo.refreshTokenExpiresAt}</td>
														 	<td>${cafe24UserTokenInfo.clientId}</td>
														 	<td>${cafe24UserTokenInfo.issuedAt}</td>
														 	<td><input type="button" value="shop 등록" onclick="fn_cafe24_shop('${cafe24UserTokenInfo.userId}','${cafe24UserTokenInfo.mallId}')"></td>
														 </tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										<!-- Key End -->
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
	</div>

	<!-- headMenu Start -->
	<!-- headMenu End -->

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
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$("#10thMenu").toggleClass('open');
				$("#10thMenu").toggleClass('active');
				$("#10thTwo").toggleClass('active');
			});
			
			$('#updateInfo').on('click', function(e) {
				var formData = $("#mainForm").serialize();
				
 				$.ajax({
					url:'/api/cafe24/userGetOAith',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						 window.open(data, 'getOAuth', 'width=800px,height=800px,scrollbars=yes');
		            }, 		    
		            error : function(xhr, status) {
		                //alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 
			});
		})
		
		function fn_getCafeAuth24(url){
				$.ajax({
					url:'/api/cafe24/userGetOAith',
					type: 'POST',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						 alert(data);
						 window.open(data, 'getOAuth', 'width=700px,height=800px,scrollbars=yes');
		            }, 		    
		            error : function(xhr, status) {
		                //alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 
		}
		
		function fn_cafe24_shop(userId,mallId){
			
			url = "/api/cafe24/userShopInfo?userId="+userId+"&mallId="+mallId;
			
			alert(url);
			window.open(url, 'getOAuth', 'width=700px,height=800px,scrollbars=yes');
			
		}
		

	</script>
	<!-- script addon end -->
</body>
</html>