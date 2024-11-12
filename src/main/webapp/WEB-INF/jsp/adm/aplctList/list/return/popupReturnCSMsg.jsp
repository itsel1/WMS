<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
			
		#save {
			height:50px; 
			text-align:center; 
			cursor:pointer; 
			background:#f1f0f0; 
			border:1px solid #ccc;
			font-weight:bold;
		}
		
		#save:hover {
			background: #ccc;
			border:1px solid #ccc;
		}
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
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								반품관리
							</li>
							<li class="active">문의 메세지</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div id="inner-content-side">
							<form id="returnMsgForm" name="returnMsgForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="orgStation" id="orgStation" value="${parameters.orgStation}" />
								<input type="hidden" name="userId" id="userId" value="${parameters.userId}" />
								<input type="hidden" name="idx" id="idx" value="${parameters.idx}" />
								<input type="hidden" name="type" id="type" value="${parameters.type}" />
								<input type="hidden" name="adminMemo" id="adminMemo" value="" />
								<table style="width:100%; border:1px solid #ccc;">
									<tr>
										<td class="center" style="width:20%; border-right:1px solid #ccc; height:40px; background:#f1f0f0; font-weight:bold;">USER ID</td>
										<td class="center" style="width:30%; border-right:1px solid #ccc;">${msgHis.USER_ID}</td>
										<td class="center" style="width:20%; border-right:1px solid #ccc; background:#f1f0f0; font-weight:bold;">원운송장번호</td>
										<td class="center" style="width:30%;">${msgHis.KOBL_NO}</td>
									</tr>
								</table>
								<table style="margin-top:10px; width:100%; border:1px solid #ccc;">
									<tr>
										<td class="center" style="border-bottom:1px solid #ccc; border-right:1px solid #ccc; background:#f1f0f0; font-weight:bold;">
											내용
										</td>
										<td style="padding:0px; width:80%; height:150px; border-bottom:1px solid #ccc;">
											<textarea style="width:100%; height:100%; resize:none; border:none;" disabled spellcheck="false">${msgHis.WH_MEMO}</textarea>
										</td>
									</tr>
									<tr>
										<td class="center" style="border-bottom:1px solid #ccc; border-right:1px solid #ccc; background:#f1f0f0; font-weight:bold;">
											답변
										</td>
										<td style="padding-top:0px; width:80%; height:150px; border-bottom:1px solid #ccc;">
											<textarea id="adminMsg" name="adminMsg" style="width:100%; height:80%; resize:none; border:none;" spellcheck="false">${msgHis.ADMIN_MEMO}</textarea>
											<span id="textWatcher" style="float:right; margin-right:10px; color:#ccc;">(0 / 250)</span>
										</td>
									</tr>
									<tr>
										<td colspan="2" id="save" onclick="fn_saveMsg()">
											<c:if test="${parameters.type eq 'm'}">
												수정
											</c:if>
											<c:if test="${parameters.type eq 'w'}">
												등록
											</c:if>
										</td>
									</tr>
								</table>
								<input type="hidden" name="nno"	id="nno" value="${msgHis.NNO}">
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {				
				$("#adminMsg").keyup(function(e) {
					var val = $(this).val();
					$("#textWatcher").html("("+val.length+" / 250)");

					if(val.length > 250) {
						alert("최대 250자까지 입력 가능합니다.");
						$(this).val(val.substring(0, 200));
						$("#textWatcher").html("(250 / 250)");
					}
				});
			});

			function fn_saveMsg() {
				$("#adminMemo").val($("#adminMsg").val());
				var formData = $("#returnMsgForm").serialize();

				$.ajax({
					url : '/mngr/aplctList/return/sendCsMsg',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					success : function(data) {
						opener.parent.location.reload();
						location.reload();
					},
					error : function(xhr, status) {
						alert("문의사항 등록에 실패 하였습니다. 다시 시도해주세요.");
					}
				});
				
			};
		</script>
		<!-- script addon end -->
	</body>
</html>
