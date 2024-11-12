<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  #admin_btn {
	  	border: none;
	  	border-radius: 3px;
	  	color: white;
	  	background-color: #b3380f;
	  	font-weight: 500;
	  	font-size: 12px;
	  	margin-left: 5px;
	  }
	  
	  #com_btn {
	  	border: none;
	  	background: none;
	  	font-weight: 600;
	  }
	  
	  .btn:focus {
	  	outline: none;
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
		<!-- ace scripts -->
	<!-- basic scripts End-->
	</head> 
	<title>CS 리스트</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								게시판
							</li>
							<li class="active">게시글</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								
							</h1>
						</div>
						<br/>
						<div class="btn_section" style="width:60%;text-align:right;">
							<input type="button" class="btn btn-sm btn-primary btn-white" value="이전" id="prev_btn"/>
							<input type="button" class="btn btn-sm btn-primary btn-white" value="목록" id="list_btn"/>
							<input type="button" class="btn btn-sm btn-primary btn-white" value="다음" id="next_btn"/>
						</div>
						<div id="inner-content-side" style="width:60%;margin-top:10px;">
							<div class="cs_board" id="board_section" style="border:1px solid #dcdcdc;">
								<br/>
								<div style="padding:10px 20px;">
									<div>
										<h4 style="font-weight:600;">${csInfo.title}</h4>	
									</div>
									<div>
										<font style="font-weight:600;">${csInfo.userId}</font>
										<c:if test="${csInfo.adminYn eq 'Y'}">
											<button id="admin_btn">관리자</button>
										</c:if>
									</div>
									<div style="margin-top:4px;">
										<div style="width:50%;float:left;">
											<font style="color:#828282;">${csInfo.regDate}</font>
										</div>
										<div style="width:50%;float:right;text-align:right;">
											<button id="com_btn">
												<i class="fa fa-commenting-o"></i>&nbsp;<font>댓글</font>&nbsp;${csInfo.commentCnt}
											</button>
										</div>
									</div>
									<br/>
									<div style="text-align:center;margin-top:10px;">
										<div style="border:1px solid #ccc;width:100%;">
										</div>
									</div>
								</div>
								<div style="padding:20px;">
									<div>
										${csInfo.contents}
									</div>
								</div>
							</div>
							<br/>
							<div class="cs_comments" id="com_section">
								<br/><br/>
								댓글 부분
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
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
					$("#11thMenu").toggleClass('open');
					$("#11thMenu").toggleClass('active');
					$("#11thThr").toggleClass('active');

					scrollToComment();
				})

				$("#list_btn").on('click', function() {
					location.href = "/mngr/cs/csBoard";
				})
			})
			
			function scrollToComment() {
				var offset = $("#com_section").offset();
				console.log(offset);
				$("html, body").animate({scrollTop: offset.top}, 400);
			}

		</script>
		<!-- script addon end -->
	</body>
	</body>
</html>
