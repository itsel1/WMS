<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
	<style type="text/css">
		
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
	
	#regist-content {
		width: 100%;
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		flex-wrap: nowrap;
		justify-content: flex-start;
		align-content: center;
		align-items: stretch;
	}
	
	#regist-content span {
		margin-top: 8px;
		margin-bottom: 2px;
		font-weight: 600;
	}
	
	#regist-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #666;
		color: #fff;
		border: 1px solid #282828;
	} 
	
	#update-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #666;
		color: #fff;
		border: 1px solid #282828;
	} 
</style>
	<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<title>사업자 정보 등록</title>
	<!-- basic scripts End-->
	</head> 
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								사업자 정보 등록
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<form id="form">
						<input type="hidden" id="actWork" name="actWork" value="${actWork}"/>
						<div class="page-content">
							<div id="regist-content">
								<c:if test="${actWork eq 'insert'}">
								<span>USER ID</span>
								<select id="userId" name="userId">
								<c:forEach items="${userInfoList}" var="list" varStatus="status">
									<option value="${list.userId}">${list.userId} / ${list.comName}</option>
								</c:forEach>
								</select>
								<span>법인명</span>
								<input type="text" name="regName" id="regName">
								<span>대표명</span>
								<input type="text" name="regRprsn" id="regRprsn">
								<span>사업자번호</span>
								<input type="text" name="regNo" id="regNo">
								<span>사업지주소</span>
								<input type="text" name="regAddr" id="regAddr">
								<span>사업지우편번호</span>
								<input type="text" name="regZip" id="regZip">
								<span>사업자통관번호</span>
								<input type="text" name="bizCustomsNo" id="bizCustomsNo">
								<div style="text-align:center;margin-top:20px;">
									<button type="button" id="regist-button">등록</button>
								</div>
								</c:if>
								<c:if test="${actWork eq 'update'}">
								<input type="text" name="userId" id="userId" readonly value="${info.userId}">
								<span>법인명</span>
								<input type="text" name="regName" id="regName" value="${info.regName}">
								<span>대표명</span>
								<input type="text" name="regRprsn" id="regRprsn" value="${info.regRprsn}">
								<span>사업자번호</span>
								<input type="text" name="regNo" id="regNo" value="${info.regNo}">
								<span>사업지주소</span>
								<input type="text" name="regAddr" id="regAddr" value="${info.regAddr}">
								<span>사업지우편번호</span>
								<input type="text" name="regZip" id="regZip" value="${info.regZip}">
								<span>사업자통관번호</span>
								<input type="text" name="bizCustomsNo" id="bizCustomsNo" value="${info.bizCustomsNo}">
								<div style="text-align:center;margin-top:20px;">
									<button type="button" id="update-button">수정</button>
								</div>
								</c:if>
							</div>
						</div>
					</form>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			var loading = "";
			jQuery(function($) {
				loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
				
				$("#regist-button").on('click', function(e) {
					if ($("#userId").val() == "") {
						alert("USER ID는 필수 입니다.");
						return;
					}

					loading.show();
					procBizInfo();
				});

				$("#update-button").on('click', function(e) {
					loading.show();
					procBizInfo();
				});
				
			});

			function procBizInfo() {

				$.ajax({
					url : '/mngr/acnt/registBizInfo',
					type : 'POST',
					data : $("#form").serialize(),
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(response) {
						if (response.status) {
							alert("처리 되었습니다.");
							opener.location.reload();
							self.close();
						} else {
							alert("처리 중 오류가 발생 하였습니다.");
							loading.hide();
							return;
						}
					},
					error : function(error) {
						alert("System Error");
						loading.hide();
						return;
					}
				});
			}
		</script>
		<!-- script addon end -->
	</body>
</html>
