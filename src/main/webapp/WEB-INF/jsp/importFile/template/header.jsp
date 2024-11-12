<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<nav class="navbar navbar-expand navbar-dark px-2" id="nav-header">
	<!-- <a href="javascript:void(0);"><i class="fa-sharp fa-solid fa-bars" onclick="fn_toggleSidebar();"></i></a> -->
	<img src="/image/icons/bars-solid.svg" style="width:24px;height:24px;cursor:pointer;" onclick="fn_toggleSidebar();">
	<!-- <div class="menu-toggle" onclick="fn_toggleSidebar();">
		<i class="fa-sharp fa-solid fa-angle-left arrow"></i>
	</div> -->
	<!-- <a class="navbar-brand px-2" href="javascript:void(0);"><img src="/image/logo.png" style="width:25px;height:25px;"/> WMS</a> -->
	

	<div class="collapse navbar-collapse profile-logo text-light justify-content-end">
		<%-- <span class="me-2" style="font-family:'Nanum Gothic', sans-serif !important;">
			<sec:authentication property="name" var="username"/>
			<c:out value="${fn:toUpperCase(username)}" /> 님, 환영합니다.
		</span>
		<a href="#"><i class="fa-solid fa-arrow-right-from-bracket me-2"></i></a> --%>
	</div>
	
</nav>