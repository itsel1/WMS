<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
</head>
<title>WMS</title>
<body class="no-skin">
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
				try {ace.settings.loadState('main-container')} catch(e) {}
			</script>
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<div class="main-content"></div>
	</div>

	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>


	<script type="text/javascript">
	</script>
</body>
</html>