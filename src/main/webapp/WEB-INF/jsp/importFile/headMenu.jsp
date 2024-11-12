<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<style type="text/css">
	#navbar {
		/* background-color: #553453; */
		background: #283c86;  /* fallback for old browsers */
		background: -webkit-linear-gradient(to right, #45a247, #283c86);  /* Chrome 10-25, Safari 5.1-6 */
		background: linear-gradient(to right, #45a247, #283c86); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	}
	
</style>
<script>

</script>
</head>
<body>
	<div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top">
	<!-- <div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top" style="background: linear-gradient(45deg, #49a09d, #5f2c82);"> -->
			<div class="navbar-container ace-save-state" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header pull-left" style="height:45px;">
					<a href="/mngr/home" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							WMS
						</small>
					</a>
					<sec:authentication property="name" var="loginUserId"/>
					<c:if test="${loginUserId eq 'itsel2'}">
						<small style="font-size:16px;position:relative;">
							<a href="/mngr/home/checkProps" style="position:absolute;top:13px;color:#fff;"><i class="fa fa-check"></i></a>
						</small>
					</c:if>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<!-- <a>index</a> -->
					<ul class="nav ace-nav">
						<li class="light-blue dropdown-modal">
							<!-- <a data-toggle="dropdown" href="#" class="dropdown-toggle"> -->
							<a data-toggle="dropdown" href="#" class="dropdown-toggle" style="background:#283c86;">
								<!-- DB로 가져오기 사용자 이름 -->
									<sec:authentication property="name"/> 님
								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										Settings
									</a>
								</li>

								<li>
									<a href="profile.html">
										<i class="ace-icon fa fa-user"></i>
										Profile
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="#">
										<i class="ace-icon fa fa-power-off"></i>
											<sec:authorize access="isAuthenticated()">
											  <a href="#" onclick="document.getElementById('logout').submit();">로그아웃</a>
											</sec:authorize>
										<form id="logout" action="/prv/logout" method="POST">
											<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
										</form>
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>
</body>
</html>