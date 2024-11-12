<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="/assets/css/userMenuTemplate.css"/>
	<link href="https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
<title>WMS</title>
</head>
<body>
	<div class="sidebar">
		<div class="logo-details">
			<i class="bx bxl-c-plus-plus"></i>
			<span class="logo_name">ACI Express</span>
		</div>
		<ul class="nav-links">
			<li>
				<a href="#" class="active">
					<i class="las la-home"></i>
					<span class="links_name">Dashboard</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-shipping-fast"></i>
					<span class="links_name">일반 배송</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-box-open"></i>
					<span class="links_name">검품 배송</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-boxes"></i>
					<span class="links_name">사입 관리</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-plane"></i>
					<span class="links_name">배송 현황</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-pallet"></i>
					<span class="links_name">재고 관리</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-undo"></i>
					<span class="links_name">반품 관리</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="las la-user-cog"></i>
					<span class="links_name">Setting</span>
				</a>
			</li>
			<li class="log_out">
				<a href="#">
					<i class="bx bx-log-out"></i>
					<span class="links_name">Log out</span>
				</a>
			</li>
		</ul>
	</div>
	<%-- <%@ include file="/WEB-INF/jsp/importFile/user/userMenuTemplate.jsp"%> --%>
	<div>Main Home</div>
</body>
</html>