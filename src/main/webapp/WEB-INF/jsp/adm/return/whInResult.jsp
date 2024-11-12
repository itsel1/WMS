<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
</style>
</head>
<title>검수 작업</title>
<body class="no-skin">
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
						<li>반품</li>
						<li class="active">입고 검수 작업</li>
					</ul>
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>검수 작업</h1>
					</div>
					<div style="margin:0 auto; text-align:center; width:100%; margin-top:20px;">
						<input type="hidden" name="nno" id="nno" value="${nno}">
						<p style="color:#428BCA;font-weight:700;font-size:34px;">
						<c:if test="${whRst eq 'WO'}">
							정상 입고
						</c:if>
						<c:if test="${whRst eq 'WF'}">
							검수 불합격
						</c:if>
						</p>
						<input type="hidden" name="groupIdx" value="${stockInfo[0].groupIdx}">
						<c:forEach items="${stockInfo}" var="stockInfo">
							<p style="font-weight:600;font-size:18px;">재고 번호 : ${stockInfo.stockNo}</p>
							<input type="hidden" name="stockNo[]" value="${stockInfo.stockNo}">
						</c:forEach>
						<br/>
						<input type="button" class="btn btn-primary" id="printLabel" value="재고번호 출력">
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->

	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>" + "<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
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


	<script type="text/javascript">
		jQuery(function($) {
			$("#printLabel").on('click', function() {
				var url = "/mngr/return/pdfPopup?nno="+$("#nno").val()+"&groupIdx="+$("input[name='groupIdx']").val();
				window.open(url, "_blank", "toolbar=yes,resizable=no,directories=no,width=480, height=360");
			});
		})
	</script>
</body>
</html>