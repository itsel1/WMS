<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		#chat-div {
			display: flex;
			margin: auto;
			border: 1px solid lightgray;
			border-radius: 10px;
			background-color: WhiteSmoke;
			flex-direction: column;
			margin-top: 5px;
			height: 85vh;
			overflow: hidden;
			width: 100%;
		}
		
		#read-div {
			width: 100%;
			height: 70%;
			overflow-y: scroll;
		}
		
		#write-div {
			width: 100%;
			height: 30%;
		}
		
		#regist-btn {
			width: 100%;
			height: 20%;
			padding: 0 !important;
		}
		
		textarea {
			resize: none;
			width: 100%;
			height: 80%;
			border-bottom-left-radius: 10px !important;
			border-bottom-right-radius: 10px !important;
		}
		
		textarea:focus {
			border: 1px solid #e2e2e2;
			outline: none;
		}
		
		.chat-style {
			max-width: 70%;
			padding: 10px;
			margin: 10px;
			border: 1px solid #e2e2e2;
			border-radius: 7px;
			background-color: #fff;
			display: flex;
			flex-direction: column;
		}
		
		#read-div::-webkit-scrollbar {
		    width: 6px;
		}
		
		#read-div::-webkit-scrollbar-thumb {
		    -webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
		}
		
		#read-div::-webkit-scrollbar-track {
		   -webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
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
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<form id="memoForm" name="memoForm">
								<input type="hidden" name="nno" id="nno" value="${nno}">
								<div id="chat-div">
									<div id="read-div">
										<c:forEach items="${memoList}" var="memoList" varStatus="status">
											<c:if test="${role eq 'ADMIN' && memoList.adminYn eq 'Y'}">
												<div style="width:100%;display:flex;flex-direction:row-reverse;">
													<div class="chat-style">
														<div>${memoList.content}</div>
														<div style="text-align:right;padding-top:5px;">${memoList.wDate}</div>
													</div>
												</div>
											</c:if>
											<c:if test="${role eq 'ADMIN' && memoList.adminYn eq 'N'}">
												<div style="width:100%;display:flex;flex-direction:row;">
													<div class="chat-style">
														<div>${memoList.content}</div>
														<div style="padding-top:5px;"><font style="color:RoyalBlue;">${memoList.userId}</font> ${memoList.wDate}</div>
													</div>
												</div>
											</c:if>
											<c:if test="${role ne 'ADMIN' && memoList.adminYn eq 'Y'}">
												<div style="width:100%;display:flex;flex-direction:row;">
													<div class="chat-style">
														<div>${memoList.content}</div>
														<div style="padding-top:5px;"><font style="color:RoyalBlue;">관리자</font> ${memoList.wDate}</div>
													</div>
												</div>
											</c:if>
											<c:if test="${role ne 'ADMIN' && memoList.adminYn eq 'N'}">
												<div style="width:100%;display:flex;flex-direction:row-reverse;">
													<div class="chat-style">
														<div>${memoList.content}</div>
														<div style="text-align:right;padding-top:5px;">${memoList.wDate}</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
									<div id="write-div">
										<input type="button" id="regist-btn" class="btn btn-primary" value="등록">
										<textarea id="content"></textarea>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
     	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
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
			jQuery(function($) {
				$(document).ready(function() {
					$("textarea").focus();
				});

				$("#regist-btn").click(function() {
					$.ajax({
						url : '/comn/returnCsMemo',
						type : 'POST',
						data : {nno: $("#nno").val(), content: $("#content").val()},
						beforeSend : function(xhr) {
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(result) {
							alert("등록 되었습니다.");
							location.reload();
						},
						error : function(xhr, status) {
							alert("등록에 실패 하였습니다.");
							return false;
						}
					});
				});
			});
		</script>
		<!-- script addon end -->
		
	</body>
</html>
