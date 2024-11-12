<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	#navbar {
		width: 100%;
		background: #283c86;  /* fallback for old browsers */
		background: -webkit-linear-gradient(to right, #45a247, #283c86);  /* Chrome 10-25, Safari 5.1-6 */
		background: linear-gradient(to right, #45a247, #283c86); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
		margin: 0;
		padding-left: 0;
		padding-right: 0;
		border-radius: 0;
		box-shadow: none;
		min-height: 45px;
		right 0;
		left: 0;
		top: 0;
		position: fixed;
		z-index: 10000;
		border: 1px solid transparent;
	}
	
	#dropdown > li:first-child {
		border-left-width: 0;
		background: #283c86;
	}
	
	
</style>
</head>
<body>
	<div id="navbar">
		<div id="navbar-container">
			<div style="float: left;">
				<a href="/mngr/home" class="navbar-brand">
					<small>
						<i class="fa fa-leaf"></i>
						WMS
					</small>
				</a>
			</div>
			<div role="navigation" style="float:right;">
				<ul style="height:100%;margin:0 !important;list-style:none;padding-left:0;" id="dropdown-modal">
					<li>
						<a data-toggle="dropdown" href="#">
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
									<form id="logout" action="/comn/logout" method="POST">
										<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
									</form>
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>