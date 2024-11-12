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
	<title></title>
	<body class="no-skin">
		<input type="hidden" name="ip" id="ip" value="${rtnIp}">
		<%-- <input type="hidden" name="prevUrl" id="prevUrl" value="${prevUrl}"> --%>
	</body>
	<script type="text/javascript">
			//alert("아이디가 새로 로그인 되어 로그아웃 되었습니다. 로그인 IP : "+$("#ip").val());
/*
			if ($("#prevUrl").val() == "ADMIN") {
				alert("세션이 만료되어 로그아웃 되었습니다.");
				location.href="/prv/login";
			} else if ($("#prevUrl").val() == "USER") {
				alert("세션이 만료되어 로그아웃 되었습니다.");
				location.href="/comn/login";
			} else {
				location.href="/comn/login";
			}
*/
			alert("아이디가 새로 로그인 되어 로그아웃 되었습니다. 로그인 IP : "+$("#ip").val());
			location.href="/comn/login";
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active');
					$("#4thOne").toggleClass('active');
				});
			});
	</script>
			
				
</html>
