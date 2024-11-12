<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.balloon_admin {
 position:relative;
 margin: 50px;
 width:400px;
 height:100px;
  background:rgb(255,219,202);
  border-radius: 10px !important;
}
.balloon_admin:after {
 border-top:15px solid rgb(255,219,202);
 border-left: 0px solid transparent;
 border-right: 15px solid transparent;
 border-bottom: 0px solid transparent;
 content:"";
 position:absolute;
 top:10px;
 right:-15px;
}

.balloon_user {
 position:relative;
 margin: 50px;
 width:400px;
 height:100px;
  background:rgb(187,225,201);
  border-radius: 10px !important;
}
.balloon_user:after {
 border-top:15px solid rgb(187,225,201);
 border-left: 15px solid transparent;
 border-right: 0px solid transparent;
 border-bottom: 0px solid transparent;
 content:"";
 position:absolute;
 top:10px;
 left:-15px;
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
								출고
							</li>
							<li class="active">검품 재고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<form id="mgrMsgForm" name="mgrMsgForm">
							<input type="hidden" id="nno" name="nno" value="${parameters.nno}">
							<input type="hidden" id="groupIdx" name="groupIdx" value="${parameters.groupIdx}">
							<input type="hidden" id="subNo" name="subNo" value="${parameters.subNo}">
							<input type="hidden" id="whMemo" name="whMemo" value="">
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
								<table style="width:90%;margin:auto;word-break:break-all;">
								<c:forEach	items="${msgHis}" var="msg" varStatus="status">
									<tr>
										<c:if test="${msg.adminYn eq 'N'}">
											<td style="width:15%;text-align: left;padding:2%;vertical-align: top;">
												<label style="font-size">${msg.userId}</label>
											</td>
											<td class="balloon_user" style="width:35%;text-align: left;color:white;padding:2%;">
													${msg.whMemo}<br/>
											</td>
											<td class="" style="width:35%;text-align: left;vertical-align: bottom;">
												&nbsp;&nbsp;
												${fn:split(msg.date,'.')[0]}
												<c:if test="${status.last}">
													<label class="red">new</label>&nbsp;
												</c:if>
											</td>
											<td style="width:15%;text-align: left;padding:2%;">
											</td>
										</c:if>
										<c:if test="${msg.adminYn eq 'Y'}">
											<td style="width:15%;text-align: left;padding:2%;">
											</td>
											<td class="" style="width:35%;text-align:right;vertical-align: bottom;">
												<c:if test="${status.last}">
													<label class="red">new</label>&nbsp;
												</c:if>
												${fn:split(msg.date,'.')[0]}&nbsp;&nbsp;
											</td>
											<td class="balloon_admin" style="width:35%;text-align: right;color:white;padding:2%;">
													${msg.whMemo}<br/>
											</td>
											<td style="width:15%;text-align: right;padding:2%;vertical-align: top;">
												관리자
											</td>
										</c:if>
									</tr>
									<tr>
										<td><br/></td>
									</tr>
								</c:forEach>
									<tr>
										<td colspan="3">
											<textarea rows="3" id="message" name="message" style="width:100%;resize: none;" class="form-control" placeholder="메시지를 입력하세요" /></textarea>
											<span></span>
										</td>
										<td style="text-align: center;background-color: rgb(222,239,239); cursor: pointer;" id="sendBtn" name="sendBtn">
											<i class="bigger-200 fa fa-paper-plane-o"></i>
										</td>
									</tr>
								</table>
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
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thThr").toggleClass('active');
					$("#message").focus();
				});

				$("#sendBtn").on('click',function(e){
					$("#whMemo").val($("#message").val());
					var formData = $("#mgrMsgForm").serialize();
					$.ajax({
						url:'/mngr/rls/mgrMsg',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							location.reload();
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
				
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
