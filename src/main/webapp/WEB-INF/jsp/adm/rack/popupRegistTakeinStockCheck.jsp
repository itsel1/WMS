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
.colorBlack {
	color: #000000 !important;
}

.inner-content-side>div {
	text-align: center;
}

input[type=text]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}
input[type=number]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

.vertiMidle{
	vertical-align: middle;
}

.pd-1rem{
	padding: 1rem 1rem;
	font-size: 14px; 
	font-weight: 400;
}

</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>사용자 관리</title>
<body class="no-skin">
	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<div class="main-content">
			<form name="stockCheckForm" id="stockCheckForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs" >
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>Rack 관리</li>
							<li class="active">Rack 등록</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>
					<br />
					<div class="page-content">
					<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<table class="table table-bordered" style="width: 650px;">
								<thead>
									<tr>
										<th class="center colorBlack">USER ID</th>
										<td style="padding: 0px; width: 200px;" colspan=3>
											<input id="userId" name="userId" style="width: 100%" type="text"  value="${stockCheckInfo.wrUserId}" readonly/>
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width: 125px;">재고파악 일시</th>
										<td style="padding: 0px; width: 200px;" colspan=3>
											<input id="checkDate" name="checkDate"  style="width: 100%" type="text" value="" placeholder="예) 20220101">
										</td>
									</tr>
									<tr>
										<th class="center colorBlack" style="width: 100px;">Remark</th>
										<td style="padding: 0px; width: 200px;" colspan=3>
											<input type="text" style="width: 100%" value="" id="remark" name="remark">
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan=4 style="text-align: right">
											<input type="button" id="btnCancel" value="취소" />
											<input type="button" id="btnUpdate" value="등록" style="margin-left:6px;"/>
										</td>
									</tr>
								</tbody>
							</table>
							<br>
					</div>
				</div>
				</div>
			</form>
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->
	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
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
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		var codeCheck = "N";
		
		jQuery(function($) {

			
			$('#btnCancel').on('click', function(e) {
				if(!confirm("취소 하시겠습니까?")) {
				} else {
					window.close();
				}
			});

			$('#btnUpdate').on('click', function(e) {
				var formData = $('#stockCheckForm').serialize();
				$.ajax({
					url: '/mngr/takein/rack/registStockCheck',
					type: 'POST',
					data: formData,
					beforeSend: function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success: function(data) {
						if(data == "S") {
							alert("등록 되었습니다.");
							window.close();
							opener.location.reload();
						} else {
							alert("등록 중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
					},
					error: function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		                alert("등록에 실패 하였습니다.");
					}
				})
			});

			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
		});
		

	</script>
	<!-- script addon end -->

</body>
</html>