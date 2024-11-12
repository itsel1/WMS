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
	
	});
	
	function fnLogIn(){
		if($('#ChkAdmin').val()!='A'){
			if($('#Station').val()==""){alert('Station Input !!');$('#Station').focus();return false;}
		}
		if($('#UserID').val()==""){alert('User ID Input !!');$('#UserID').focus();return false;}
		if($('#UserPW').val()==""){alert('Passworld Input !!');$('#UserPW').focus();return false;}
		$('#username').val("prv==d="+$('#UserID').val());
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
	
	#forgotPwLink {
		cursor: pointer;
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
				<h4>Please Enter Your <font style="font-weight:bold;">Admin</font> Information</h4>
			</div>
			<div class="space"></div>
			<div class="space"></div>
			<div class="login_form">
				<form name="frmLogin" id="frmLogin" method="post" action="/loginProcess">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<fieldset>
						<label class="block clearfix">
							<span class="block input-icon input-icon-right">
								<input type="text" class="form-control" placeholder="User ID" name='UserID' id='UserID'/>
								<input type="hidden" class="form-control" placeholder="User ID" name='username' id='username'/>
								<i class="ace-icon fa fa-user"></i>
							</span>
						</label>
						<label class="block clearfix">
							<span class="block input-icon input-icon-right">
								<input type="password" class="form-control" placeholder="Password" name='password' id='password'/>
								<input type="hidden" class="form-control" name='authorities' id='authorities' value="ROLE_ADMIN"/>
								<i class="ace-icon fa fa-lock"></i>
							</span>
						</label>
						<div class="space"></div>
						<div class="clearfix">
							<!-- <a id="forgotPwLink" onclick="fn_openFindPwPopup()">forgot password?</a> -->
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
		$(document).ready(function(){
			var msg = getParam("msg");
			if(msg=="a"){
				alert("로그인 정보가 틀렸습니다.\nNon-existent login information.")
			}else if(msg=="b"){
				alert("승인되지 않은 계정입니다.\nUnapproved account.")
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
/*
		function fn_openFindPwPopup() {
			let width = 500;
			let height = 300;
			let left = (screen.width-width)/2;
			let top = (screen.height-height)/2;
			
			
			window.open('/findAdminPw', 'findPwPage', 'width='+width+',height='+height+',left='+left+',top='+top);
		}
		*/
	</script>
	<!-- script addon end -->

</body>
</html>