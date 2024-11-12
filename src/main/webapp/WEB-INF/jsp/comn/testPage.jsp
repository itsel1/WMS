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
	<title>공지사항</title>
	<body class="no-skin">
		<form id="orderInspForm" name="orderInspForm" enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="file" name="files" id="files"/>
			<input type="button" name="registBtn" id="registBtn" value="등록">
			<input type="button" name="download" id="download" value="다운">
		</form>
	</body>
	<script type="text/javascript">
	$("#registBtn").on('click',function(e){
		$('#orderInspForm').attr("action","/uploadEncryptTest");
		$('#orderInspForm').attr("method","post");
		$('#orderInspForm').submit();
		
		/* var formData = $("#uploadForm").serialize();
		$.ajax({
			type : "POST",
			beforeSend : function(xhr)
			{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
			    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			url : "/uploadEncryptTest",
			data : formData,
			processData: false,
            contentType: false,
			error : function(){
				alert("subpage ajax Error");
			},
			success : function(data){
				alert("SUCCESS");
			}
		}) */
	});

	$("#download").on('click',function(e){
		$('#orderInspForm').attr("action","/downloadDecryptTest");
		$('#orderInspForm').attr("method","post");
		$('#orderInspForm').submit();
		
		/* var formData = $("#uploadForm").serialize();
		$.ajax({
			type : "POST",
			beforeSend : function(xhr)
			{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
			    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			url : "/uploadEncryptTest",
			data : formData,
			processData: false,
            contentType: false,
			error : function(){
				alert("subpage ajax Error");
			},
			success : function(data){
				alert("SUCCESS");
			}
		}) */
	});
	</script>
</html>
