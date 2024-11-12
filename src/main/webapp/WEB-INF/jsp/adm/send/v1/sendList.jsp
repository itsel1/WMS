<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<style type="text/css">

	</style>
	
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	</head> 
	<title>WMS.ESHOP</title>
	<body class="no-skin">
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								발송 리스트
							</li>
							<li class="active">발송 목록</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								발송 목록
							</h1>
						</div>
						<div class="page-contents">
							<form id="priceForm" action="/mngr/invoice/v1/invPriceApplyList" method="get">

							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<script src="/testView/js/jquery-3.3.1.min.js"></script>
		<script src="/testView/js/jquery-ui.min.js"></script>

		<script type="text/javascript">


		</script>
	</body>
</html>
