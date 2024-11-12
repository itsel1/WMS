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
								<h1>${userInfo.userId} 정보 수정</h1>
							</div>
							<form name="userInfoForm" id="userInfoForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="inner-content-side">
									<!--  -->
									<div class="row">
										<div class="col-xs-12 col-lg-6 col-lg-offset-3" >
											<!-- 기본정보 Start -->
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>API 정보</h2>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<sec:authorize access="hasAnyRole('USER')">
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label>API KEY</label>
													</div>
													<div class="col-lg-12 col-xs-12">
														<c:if test="${empty userInfo.apiKey}">
															<strong>API KEY가 발급되지 않았습니다.</strong>
															<button class="btn btn-xs btn-success btn-white" type="button" id="registApiKey" name="registApiKey">API KEY발급 받기</button>
														</c:if>
														<c:if test="${!empty userInfo.apiKey}">
															<strong>${userInfo.apiKey}</strong>
														</c:if>
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label>접속 가능 IP 지정</label>
													</div>
													<div class="col-lg-6 col-xs-12">
														<input type="text" id="inputIp" name="inputIp" style="background-color:rgb(255,255,237); width:100%">
													</div>
													<div class="col-lg-6 col-xs-12" style="margin-top:4px;">
														<c:if test="${!empty userInfo.apiKey}">
															<button type="button" id="addIp" name="addIp" class="btn btn-primary btn-sm btn-white">추가</button>
														</c:if>
													</div>
												</div>
												<div class="space-20"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label>접속 가능 IP 목록</label>
													</div>
													<div class="col-lg-12 col-xs-12" name="ipList" id="ipList">
														<c:forEach items="${allowIpList }" var="allowIp" varStatus="status">
															<div class="col-lg-6 col-xs-12" id="allowIp${status.count}" name="allowIp">
																<input type="text" ipOnly style="width:130px;height:30px;background-color: white !important;" readonly value="${allowIp.allowIp}">
																<i class="ace-icon fa fa-times" style="padding: 5px; cursor: pointer;" onclick="javascript:fn_deleteIp('allowIp${status.count}')"></i>
															</div>
														</c:forEach>
													</div>
												</div>
												<div class="space-20"></div>
												</sec:authorize>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label><font style="font-weight:bold;">Eshop</font> ID</label>
														<input class="width-100" type="text" name="eshopId" id="eshopId" value="${userInfo.eshopId}"/>
													</div>
													<div class="col-lg-6 col-xs-12">
														<label><font style="font-weight:bold;">Eshop</font> API Key</label>
														<input class="width-100" type="text" name="eshopApiKey" id="eshopApiKey" value="${userInfo.eshopApiKey}"/>
													</div>
												</div>
												<div class="space-20"></div>
												<div class="row form-group hr-8">
												<%-- <div class="row form-group hr-8">
													<label>Webhook 설정</label>
												</div>
												<div class="col-lg-12 col-xs-12" name="webHookList" id="webHookList">
													<c:forEach items="${webhookList}" var="webHook" varStatus="webHookStatus">
														<input type="text" style="border:none; width:25%; border-bottom:1px solid gray; border-radius: 0rem !important; background-color: white !important;" readonly id="divKr${webHookStatus.count}" name="divKr" value="${webHook.divKr }">&nbsp;&nbsp;
														<input type="hidden" id="webHookDiv${webHookList.count}" name="webHookDiv" value="${webHook.divs}">
														<input type="text" style="border:none; width:60%; border-bottom:1px solid gray; border-radius: 0rem !important; background-color: white !important;" id="webHookUrl${webHookStatus.count}" name="webHookUrl" value="${webHook.url }"><br/>
													</c:forEach>
												</div> --%>
											</div>
										</div>
										<!-- Key End -->
									</div>
									<div class="row">
										<div class="col-lg-12 col-xs-12">
											<br />
											<div class="col-lg-1 col-xs-6 col-lg-offset-5">
												<div id="button-div">
													<div id="backPage" style="text-align: center">
														<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
													</div>
												</div>
											</div>
											<div class="col-lg-1 col-xs-6">
												<div id="button-div">
													<div id="updateUser" style="text-align: center">
														<button id="updateInfo" type="button" class="btn btn-sm btn-primary">확인</button>
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
		var pwCheck = "N";
		jQuery(function($) {
			$(document).ready(function() {
				$("#10thMenu").toggleClass('open');
				$("#10thMenu").toggleClass('active');
				$("#10thTwo").toggleClass('active');
			});

			//initiate dataTables plugin
			
			$('#backToList').on('click', function(e) {
				history.back();
			});
			

			$('#updateInfo').on('click', function(e) {

				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/cstmr/myPage/userInfoApi',
					type: 'PATCH',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("수정 되었습니다.");
							location.href="/cstmr/myPage/userInfoApi";
						}else{
							alert("수정중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 
				
				
			});
		}) 
		
		function fn_deleteIp(allowIp){
			var param1 = $("#"+allowIp).children()[0].value;
			$.ajax({
				url:'/cstmr/myPage/deleteAllowIp',
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						$("#"+allowIp).remove();
						alert("지정 IP에서 삭제 되었습니다.");
					}else{
						alert("삭제 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		}

		$("#registApiKey").on('click',function(e){
			$.ajax({
				url:'/cstmr/myPage/apiKeyAllow',
				type: 'POST',
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						alert("APIKEY가 발급되었습니다.");
						location.reload();
					}else{
						alert("등록 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		});

		$("#addIp").on('click',function(e){
			var param1 = $("#inputIp").val();
			var check = 0;
			var num = $("div[name='allowIp']").size()+1;
			if (param1 == ''){
				alert("IP를 입력 해 주세요");
				return false;
			}

			if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(param1)) {  
			    
			}else{
				alert("유효한 IP 주소 형식이 아닙니다.")
				return false;
			}
			    
			$("div[name='allowIp']").each(function(e){
				if($("div[name=allowIp]")[e].children[0].value == $("#inputIp").val()){
					alert("이미 등록된 IP입니다.");
					check = 1;
					return false;
				}
			})
				
			if(check != 0){
				return;
			}
			
			$.ajax({
				url:'/cstmr/myPage/registAllowIp',
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						var addHtml = "";
						
						addHtml += "<div class='col-lg-6 col-xs-12' id='allowIp"+num+"' name='allowIp'>"
						addHtml += "<input type='text' style='width:130px;height:30px;background-color: white !important;' readonly value='"+data.allowIp+"'>";
						addHtml += '<i class="ace-icon fa fa-times" style="padding:5px;cursor: pointer;" onclick="javascript:fn_deleteIp(\'allowIp'+num+'\')"></i>'; 
						addHtml += "</div>";
						$("#ipList").append(addHtml);
						alert("지정 IP에 등록 되었습니다.");
						$("#inputIp").val("");
					}else{
						alert("등록 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		});

	</script>
	<!-- script addon end -->
</body>
</html>