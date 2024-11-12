<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
<title>Login Page</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script language="javascript">
	$(document).ready(function(){
		 $("#password").keydown(function(e){
				if(e.keyCode==13){
					fnLogIn();
				}
		 });
		$('#btnLogin').click(function() {
			fnLogIn();
		 });

		if ($("#status").val() == 'S') {
			$("#UserID").val($("#loginId").val());
			$("#password").val($("#loginPw").val());
			fnLogIn();
		} else if ($("#status").val() == 'F') {
			alert($("#loginMsg").val());
		}
	
	});
	
	function fnLogIn(){
			if($('#ChkAdmin').val()!='A'){
				if($('#Station').val()==""){alert('Station Input !!');$('#Station').focus();return false;}
			}
			if($('#UserID').val()==""){alert('User ID Input !!');$('#UserID').focus();return false;}
			if($('#UserPW').val()==""){alert('Passworld Input !!');$('#UserPW').focus();return false;}
			$('#username').val("comn==d="+$('#UserID').val());
			$('#frmLogin').submit();
	}
</script>
<style type="text/css">

	body {
		margin: 0;
	    padding: 0;
	    background-image: linear-gradient(-225deg, #fff1eb 0%, #ace0f9 100%);

	}
	
	.login_container {
	  	position: absolute;
	    top: 35%;
	    left: 50%;
	    width: 400px;
	    padding: 30px 30px 30px 30px;
	    transform: translate(-50%, -50%);
	    background: #fff;
	    box-sizing: border-box;
	    box-shadow: 0 15px 25px rgba(6, 64, 91, 0.61);
	    border-radius: 20px;
	}

	#UserID {
		border-radius: 10px;
	}
	
		
	.modal.modal-center {
	  text-align: center;
	}
	
	@media screen and (min-width: 768px) { 
	  .modal.modal-center:before {
	    display: inline-block;
	    vertical-align: middle;
	    content: " ";
	    height: 100%;
	  }
	}
	
	.modal-dialog.modal-center {
	  display: inline-block;
	  text-align: left;
	  vertical-align: middle; 
	}
	 
	#findModal {
	 	width: 20%;
	}
	
	.modal-header {
		background: rgba(76, 144, 189, 1);
		font-weight: bold;
		color: #fff;
	}
	
	.modal-footer {
		background: white;
		text-align: right;
	}
	
	.modal-body {
		height: 120px;
	}
	
	#findPw:hover {
		cursor: pointer;
	}
	
	#findId {
		width: 98%;
		height: 36px;
		border: 1px solid #ccc;
	}
	
	#back {
		position: absolute;
		z-index: 100;
		background-color: #000000;
		display: none;
		left: 0;
		top: 0;
	}
	
	#loadingBar {
		position: absolute;
		left: 50%;
		top: 40%;
		display: none;
		z-index: 200;
	}
</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<body>
	<div class="main_section">
		<c:if test="${not empty msg}">
			<input type="hidden" id="msgs" name="msgs" value="a"/>
		</c:if>
		<div class="login_container">
			<div class="login_title_img" style="text-align:center;">
				<img src="/image/desk_logo.png"/>
			</div>
			<div class="space"></div>
			<div class="login_title" style="text-align:center;">
				<h4>Please Enter Your Information</h4>
			</div>
			<div class="space"></div>
			<div class="space"></div>
			<div class="login_form">
				<form name="frmLogin" id="frmLogin" method="post" action="/loginProcess">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="status" id="status" value="${returnVal.status}"/>
					<input type="hidden" name="loginId" id="loginId" value="${returnVal.userId}"/>
					<input type="hidden" name="loginPw" id="loginPw" value="${returnVal.userPw}"/>
					<input type="hidden" name="loginMsg" id="loginMsg" value="${returnVal.message}"/>	
					<fieldset>
						<label class="block clearfix">
							<span class="block input-icon input-icon-right">
								<input type="text" class="form-control" placeholder="User ID" name='UserID' id='UserID' onKeyDown="javascript:coEnterTarget('userPw');" />
								<input type="hidden" class="form-control" placeholder="User ID" name='username' id='username' onKeyDown="javascript:coEnterTarget('userPw');" />
								<i class="ace-icon fa fa-user"></i>
							</span>
						</label>
						<label class="block clearfix">
							<span class="block input-icon input-icon-right">
								<input type="password" class="form-control" placeholder="Password" name='password' id='password'/>
								<input type="hidden" class="form-control" name='authorities' id='authorities' value="ROLE_USER"/>
								<i class="ace-icon fa fa-lock"></i>
							</span>
						</label>
						<div class="space"></div>
						<div class="clearfix">
							<a href="/kind/user" style="line-height:2.5;">Sign up</a>
							<!-- <a id="findPw" style="line-height:2.5; margin-left:10px;">Forgot Password?</a> -->
							<button type="button" class="width-35 pull-right btn btn-sm btn-primary"  id='btnLogin' >
								<i class="ace-icon fa fa-key"></i>
								<span class="bigger-110">Login</span>
							</button>
						</div>
	
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	
	<div class="modal modal-center fade" id="modal" role="dialog" data-backdrop="static">
		<div class="modal-dialog modal-center" id="findModal">
			<div class="modal-content">
				<div class="modal-header">
					<h4>비밀번호 찾기</h4>
				</div>
				<div class="modal-body">
					<div class="row" style="margin-left:2px; margin-right:0px;">
						<h5 style="font-weight:bold;">아이디</h5>
					</div>
					<table style="width:100%;">
						<tr>
							<td style="width:88%;">
								<input type="text" name="findId" id="findId">
							</td>
							<td style="width:10%; text-align:center;">
								<input type="button" class="btn btn-primary btn-white btn-sm" name="findBtn" id="findBtn" value="찾기">
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<i class="fa fa-close" style="font-size:24px; color:gray;" onclick="fn_closeModal()"></i>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Main container End-->
	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
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
		$(document).ready(function(){
			var msg = getParam("msg");
			if(msg=="a"){
				alert("로그인 정보가 틀렸습니다.\nNon-existent login information.")
			}else if(msg=="b"){
				alert("승인되지 않은 계정입니다.\nAdministrator approval is required.")
			}

	    });
		function getParam(sname) {
		    var params = location.search.substr(location.search.indexOf("?") + 1);
		    var sval = "";
		    params = params.split("&");
		    for (var i = 0; i < params.length; i++) {
		        temp = params[i].split("=");
		        if ([temp[0]] == sname) { sval = temp[1]; }
		    }
		    return sval;
		}


		$("#findPw").click(function() {
			
			(async () => {
				const {value: getId} = await Swal.fire({
					title: '<br>Please Enter Your ID',
					text: '아이디 제출 시 기존 비밀번호는 변경 됩니다!',
					input: 'text',
					showCancelButton: true,
					confirmButtonText: 'submit',
					closeOnConfirm: false,
					closeOnCalcel: false,
					cancelButtonText: 'close',
					allowOutsideClick: false
				});
				
				if (getId) {
					$.ajax({
						url : '/find/userInfo',
						type : 'POST',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {userId: getId},
						success : function(result) {
							console.log(result);
							if (result.resultCode == 'S') {
								Swal.fire('<br>'+result.email+'<br>인증 메일 전송 완료<br><br> <p style="font-size:14px;">인증키는 10분 뒤 만료됩니다</p>');
							} else if (result.resultCode == 'N') {
								alert(result.resultMsg);
							} else {
								alert(result.resultMsg);
							}
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
				}
			})()
		})


		
	</script>
	<!-- script addon end -->

</body>
</html>