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
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;height:300px;">
								<table style="width:90%;margin:auto;word-break:break-all;">
								<c:forEach	items="${msgHis}" var="msg" varStatus="status">
									<tr>
										<c:if test="${msg.adminYn eq 'Y'}">
											<td style="width:15%;text-align: left;padding:2%;vertical-align: top;">
												관리자
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
										<c:if test="${msg.adminYn eq 'N'}">
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
												<label style="font-size">${msg.userId}</label>
											</td>
										</c:if>
									</tr>
									<tr>
										<td><br/></td>
									</tr>
								</c:forEach>
									
								</table>
								</div>
									</br>
									
								<form id="mgrMsgForm" name="mgrMsgForm">
								<input type="hidden" id="nno" name="userId" value="${parameters.userId}">
								<input type="hidden" id="groupIdx" name="groupIdx" value="${parameters.groupIdx}">
								<input type="hidden" id="subNo" name="orgStation" value="${parameters.orgStation}">
								<input type="hidden" id="whMemo" name="whMemo" value="">
								<table id="dynamic-table" class="table table-bordered table-striped" style="width:90%;margin:auto;word-break:break-all;">
								<tr>								
									<td style="vertical-align: middle;text-align:center;max-width:50px;">
										<select id="whReqStatus" name="whReqStatus">
										    <option value="">구분</option>										
											<option value="[강제출고 요청]">강제 출고 요청</option>
											<option value="[반품 요청]">반품 요청</option>
											<option value="[폐기 요청]">폐기 요청</option>
										</select>
									</td>
										<td colspan="3">
											<textarea rows="1" id="message" name="message" style="width:100%;resize: none;" class="form-control" placeholder="메시지를 입력하세요" /></textarea>
											<span></span>
										</td>
										<td style="text-align: center;background-color: rgb(222,239,239); cursor: pointer;" id="sendBtn" name="sendBtn">
											<i class="bigger-200 fa fa-paper-plane-o"></i>
										</td>
									</tr>
								</table>	
							</form>
							
							</br>
							
			<%-- 				<input type="hidden" value="${parameters.groupIdx}">
							<input type="hidden" value="${parameters.userId}">
							<input type="hidden" value="${parameters.orgStation}">
							<table id="dynamic-table" class="table table-bordered table-striped" style="width:90%;margin:auto;word-break:break-all;">
								<thead>
									<tr>
										<th colspan="2"> 요청 하기</th>
									</tr>
								</thead>
								<tbody>
								<tr>
									<td>
										<select name="whReqStatus">
										    <option value="">구분</option>										
										    <option value="">구분</option>
											<option value="">강체 출고 요청</option>
											<option value="">반품 요청</option>
											<option value="">폐기 요청</option>
										</select>
									</td>
									<td>
										<input type="button" value="요청하기">
									</td>
								</tr>
							</table> --%>
							
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
					
					$("#whMemo").val($("#whReqStatus").val()+'  '+$("#message").val());
					var formData = $("#mgrMsgForm").serialize();

					$.ajax({
						url:'/cstmr/stock/popupGroupMsg',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							location.reload();
							opener.parent.location.reload();
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
