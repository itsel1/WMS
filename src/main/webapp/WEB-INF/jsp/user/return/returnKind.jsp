<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
		* {
	font-family: arial, sans-serif;
}

body {
	margin-left:10px;
}

.b01_simple_rollover {
    color: #000000;
    border: #000000 solid 1px;
    padding: 10px;
    background-color: #ffffff;
}

.b01_simple_rollover:hover {
    color: #ffffff;
    background-color: #000000;
}

</style>
</head>
	<title>반품 접수 </title>
	
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<!-- headMenu End -->
			<!-- Main container Start-->
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 관리</a> 
							<span class="mx-2 mb-0">/</span> 
							<strong class="text-black">반품 접수</strong>
						</div>
					</div>
				</div>
		    </div>
	    </div>

		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="container">
	       <div class="page-content noneArea">
				<div id="inner-content-side" >
					<br>
					<h2 style="margin-left:4px;">반품 접수</h2>
					<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
					<!-- Main container Start-->
					<div class="main-container ace-save-state" id="main-container">
						<script type="text/javascript">
							try{ace.settings.loadState('main-container')}catch(e){}
						</script>
						<div class="main-content" style="text-align: center;">
							<button class="button_base b01_simple_rollover" style="width:45%;height: 200px;font-size: medium;" id="return1" name="return1">자사 배송 반품접수</button>
							<button class="button_base b01_simple_rollover" style="width:45%;height: 200px;font-size: medium;" id="return2" name="return2">타사 배송 반품접수</button>
						</div>
					</div>
				</div><!-- /.main-content -->
			</div>
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
		
			jQuery(function($) {
				$(document).ready(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thThr").toggleClass('active');	
				});
				// 파일 업로드 
				//return/getExpress
			});
			$("#return1").on("click",function(e){
				location.href="/return/returnCourier";
			});
			$("#return2").on("click",function(e){
				location.href="/return/returnOthers";
				//location.href="/return/thridParty/getExpress";
			});
		</script>
		<!-- script addon end -->
	</body>
</html>
