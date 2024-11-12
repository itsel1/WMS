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
		
		
		#chatWrap {
			width: 100%;
			height: expression( this.scrollHeight > 99 ? "100px" : "auto" );
			max-height:420px;
			overflow-y: auto;
			border: 1px solid #ddd;
			margin: 0 auto;
			padding: 10px;
		}
		
		.myMsg {
			text-align:right;
		}
		
		.adminMsg {
			text-align:left;
			margin-bottom: 5px;
		}
		
		.msg {
			display: inline-block;
			border-radius: 15px;
			padding: 7px 15px;
			margin-bottom: 10px;
			margin-top: 5px;
			font-size:12px;
			max-width: 40%;
			word-break: break-all;
		}
		
		.adminMsg > .msg {
			background-color: #f1f0f0;
		}
		
		.myMsg > .msg {
			background-color: #3296FF;
			color: #fff;
		}
		
	 .addBtn {
	  	background: #3296FF;
	   	height: 25px;
	   	color: white;
	   	border: none;
	  }
	  
	  #searchBtn {
	  	background: #E8F5FF;
	   	border: 1px solid #DCEBFF;
	   	font-weight:bold;
	  }
	  
	  #searchBtn:hover {
	  	background: #DCEBFF;
	   	border: 1px solid #E8F5FF;
	   	font-weight:bold;
	  }
	  
	  .addBtn:hover {
	  	background: #50B4FF;
	  }
	  
		.custom::-webkit-scrollbar{
		    width: 6px;
		}
		
		.custom::-webkit-scrollbar-thumb{
		    height: 17%;
		    background-color: rgba(23, 95, 151, 1);
		    border-radius: 10px;  
		}
		
		.custom::-webkit-scrollbar-track{
		    background-color: rgba(23, 95, 151, 0.24);
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
						<div id="inner-content-side" style="margin-top:10px; width:100%;">
							<form id="returnMsgForm" name="returnMsgForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="orgStation" id="orgStation" value="${parameters.orgStation}" />
								<input type="hidden" name="userId" id="userId" value="${parameters.userId}" />
								<input type="hidden" name="nno" id="nno" value="${parameters.nno}" />
								<input type="hidden" name="whMemo" id="whMemo" value="" />
								<div id="chatWrap" class="scroll custom">
									<c:choose>
										<c:when test="${!empty msgHis}">
											<c:forEach items="${msgHis}" var="msg" varStatus="status">
												<div id="chatLog">
													<c:if test="${msg.adminYn eq 'N'}">
														<div class="myMsg">
															<c:if test="${msg.readYn eq 'N'}">
																<span>
																	<i onclick="fn_delete('${msg.idx}')" style="cursor:pointer;" class="fa fa-times" aria-hidden="true"></i>
																</span>
															</c:if>
															<span style="font-size:11px;">
																${fn:split(msg.date,'.')[0]}&nbsp;&nbsp;
															</span>
															<span class="msg" style="text-align:left;">
																${msg.whMemo}
															</span>
														</div>
													</c:if>
													<c:if test="${msg.readYn eq 'Y'}">
														<div class="adminMsg">
															<div style="font-size:12px;">관리자</div>
															<span class="msg" style="text-align:left;">
																${msg.adminMemo}
															</span>
															<span style="font-size:11px;">
																${fn:split(msg.date,'.')[0]}&nbsp;&nbsp;
															</span>
														</div>
													</c:if>
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
										문의 내역이 없습니다
										</c:otherwise>
									</c:choose>
								</div>
								<div style="width:100%; height:100px; margin:0 auto; border:1px solid #ccc;">
									<textarea style="width:100%; height:80%; resize:none; border:none;" id="message" name="message" autocomplete="off"></textarea>
									<span id="textWatcher" style="float:right; margin-right:10px; color:#ccc;">(0 / 250)</span>
								</div>
								<div style="width:100%; text-align:right; margin-top:10px;">
									<input type="button" id="sendBtn" class="addBtn" value="등록">
								</div>
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

				$("#message").keyup(function(e) {
					var val = $(this).val();
					$("#textWatcher").html("("+val.length+" / 250)");

					if(val.length > 250) {
						alert("최대 250자까지 입력 가능합니다.");
						$(this).val(val.substring(0, 200));
						$("#textWatcher").html("(250 / 250)");
					}
				});
				
				$("#sendBtn").on('click', function(e) {
					$("#whMemo").val($("#message").val());
					var formData = $("#returnMsgForm").serialize();
					$.ajax({
						url : '/return/sendCsMsg',
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
					})
				})
				
				
			})
			
			function fn_delete(idx) {
				if(!confirm("해당 문의를 삭제하시겠습니까?")) {
					return false;
				} else {
					var userId = $("#userId").val();
					var nno = $("#nno").val();
					var data = {idx: idx, userId: userId, nno: nno};
					
					$.ajax({
						url : '/return/delCsMsg',
						type : 'post',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : data,
						success : function(data) {
							opener.parent.location.reload();
							location.reload();
						},
						error : function(xhr, status) {
							alert("삭제에 실패하였습니다. 다시 시도해 주세요.");
						}
					})
				}
			}
		</script>
		<!-- script addon end -->
	</body>
</html>