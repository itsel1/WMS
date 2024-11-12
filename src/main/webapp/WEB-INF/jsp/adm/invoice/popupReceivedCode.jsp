<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
	<style type="text/css">
		.table>tbody>tr>td {height:36px; padding: 5px 0 0 12px}
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
		<title>ETC</title>
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
								코드 관리
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="mgrForm" action="" method="get">
								<table id="codeTable" class="table table-bordered" style="width:350px;">
								  <thead>
								  		<tr>
								  			<th colspan=2 class="center colorBlack"  style="width:100%;">코드명</th>
								  		</tr>
									</thead>
									<tbody>
										<tr> 
											<td style="padding:0px; width:80%;">
												<input type="text" name="receivedCodeName" id="receivedCodeName" maxlength="30" style="width:90%"/>
											</td>
											<td style="width:20%;">
												<input type="button" name="insertCode" id="insertCode" value="저장"/>
											</td>
										</tr>
								  		<c:forEach items="${receivedCodeList}" var="receivedCodeList" varStatus="status">
											<tr>
												<td style="width:80%;">
													${receivedCodeList.name}
												</td>
												<td style="width:20%;">
													<input type="button" onclick="fn_delete('${receivedCodeList.code}');" value="삭제"/>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<br>
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
		
		<script type="text/javascript">
			jQuery(function($) {


				$("#receivedCodeName").keydown(function(key) {
					if (key.keyCode == 13) {
						fn_code_in();
					}
				});

				
				$("#insertCode").on('click',function(e){
					fn_code_in();
				})
			});


			function fn_code_in(){
					if( $("#receivedCodeName").val() == ""){
						alert('코드명을 입력해주세요');
						$("#receivedCodeName").focus();
						return false;
					}
					
					var formData = $("#mgrForm").serialize();
					$.ajax({
						url:'/mngr/invoice/insertReceivedCode',
						type: 'PUT',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							var Status = data.rstStatus;
	
							if(Status == "SUCCESS"){
								alert(data.rstMsg);
								//window.close();
								opener.parent.location.reload();
							}else{
								alert(data.rstMsg);
								
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
			}
		
			function fn_delete(code) {
					$.ajax({
						url:'/mngr/invoice/deleteReceivedCode',
						type: 'PUT',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: {'receivedCode': code}, 
						success : function(data) {
							var Status = data.rstStatus;

							if(Status == "SUCCESS"){
								alert(data.rstMsg);
								//window.close();
								location.reload();
							}else{
								alert(data.rstMsg);
								opener.parent.location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
			}
			
		</script>
	  	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		
		

	</body>
</html>
