<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>ACI</title>
<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
<link rel="stylesheet" href="/custom/css/new_login.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<div id="main-section">
		<c:if test="${not empty msg}">
			<input type="hidden" id="msgs" name="msgs" value="a"/>
		</c:if>
		<form id="loginForm" method="post" action="/loginProcess">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="status" id="status" value="${returnVal.status}"/>
			<input type="hidden" name="loginId" id="loginId" value="${returnVal.userId}"/>
			<input type="hidden" name="loginPw" id="loginPw" value="${returnVal.userPw}"/>
			<input type="hidden" name="loginMsg" id="loginMsg" value="${returnVal.message}"/>
			<input type="hidden" id="nowLocale" value="${nowLocale}"/>
			<fieldset>
				<div class="select-lang">
					<select id="locale-lang">
						<option value="en" <c:if test="${nowLocale eq 'en'}">selected</c:if>><spring:message code="signup.lang.en" text="English" /></option>
						<option value="ko" <c:if test="${nowLocale eq 'ko'}">selected</c:if>><spring:message code="signup.lang.ko" text="Korean" /></option>
						<option value="ja" <c:if test="${nowLocale eq 'ja'}">selected</c:if>><spring:message code="signup.lang.ja" text="Japanese" /></option>
						<option value="zh" <c:if test="${nowLocale eq 'zh'}">selected</c:if>><spring:message code="signup.lang.zh" text="Chinese (Traditional)" /></option>
					</select>
				</div>
				<div class="main-logo"><img src="/image/desk_logo.png" alt="" class="aci_logo"></div>
				<div class="fieldset-input-form">
					<div class="line-item">
						<div class="input id">
							<input type="text" id="UserID" name="UserID" autocomplete="username">
							<input type="hidden" class="form-control" placeholder="User ID" name='username' id='username'/>
							<img src="/image/user-icon.png" class="label-icon">
							<img src="/image/del-icon2.png" class="del-icon">
						</div>
						<div class="input password">
							<input type="password" id="password" name="password" autocomplete="current-password">
							<input type="hidden" class="form-control" name='authorities' id='authorities' value="ROLE_USER"/>
							<img src="/image/lock-icon.png" class="label-icon">
							<img src="/image/del-icon2.png" class="del-icon">
							<img src="/image/eye-icon3.png" class="eye-icon">
						</div>
					</div>
					<div class="line-item">
						<div class="line-item-row">
							<div class="login-alink">
								<%-- <a href=""><spring:message code="login.findPw" text="Forgot Password?" /></a> --%>
							</div>
							<div class="login-alink">
								<a href="/kind/user"><spring:message code="login.signUp" text="Sign Up" /></a>
							</div>
						</div>
						<div class="center button-div">
							<button type="button" id="btnLogin"><spring:message code="login.loginButton" text="Login" /></button>
						</div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script type="text/javascript">

		var language = $("#nowLocale").val();
		var errMsg1 = "";
		var errMsg2 = "";
		var errMsg3 = "";
		var errMsg4 = "";
		
		jQuery(function($) {
			$(document).ready(function(e) {

				if (language == "ko") {
					errMsg1 = "로그인 정보가 틀렸습니다.";
					errMsg2 = "승인되지 않은 계정입니다.";
					errMsg3 = "아이디를 입력해 주세요.";
					errMsg4 = "비밀번호를 입력해 주세요.";
					
				} else if (language == "en") {
					errMsg1 = "Non-existent login information.";
					errMsg2 = "Administrator approval is required.";
					errMsg3 = "Please enter your Account ID.";
					errMsg4 = "Please enter your password.";
					
				} else if (language == "ja") {
					errMsg1 = "存在しないログイン情報です。";
					errMsg2 = "管理者の承認が必要です。";
					errMsg3 = "アカウントIDを入力してください。";
					errMsg4 = "パスワードを入力してください。";
					
				} else if (language == "zh") {
					errMsg1 = "不存在的登錄信息。";
					errMsg2 = "需要管理员批准。";
					errMsg3 = "請輸入帳號ID。";
					errMsg4 = "請輸入密碼。";
					
				} else {
					errMsg1 = "Non-existent login information.";
					errMsg2 = "Administrator approval is required.";
					errMsg3 = "Please enter your Account ID.";
					errMsg4 = "Please enter your password.";
				}
				
				var msg = getParam("msg");
				if (msg != "") {
					if(msg=="a"){
						alert(errMsg1);
					}else if(msg=="b"){
						alert(errMsg2);
					}
					$("#locale-lang").trigger('change');
				}
				/* if(msg=="a"){
					alert(errMsg1);
				}else if(msg=="b"){
					alert(errMsg2);
				} */
			});
		});
		
		

		$("#locale-lang").change(function(e) {
			location.href = "/comn/login?lang="+$("#locale-lang").val();
			
		});

		$(".eye-icon").on('click', function(e) {
			$(".input.password").toggleClass('active');

			if ($(".input.password").hasClass('active') == true) {
				$("#password").attr("type", "text");
			} else {
				$("#password").attr("type", "password");
			}
		});

		$(".del-icon").on('click', function() {
			$(this).parents('.input').find('input').val('');
		});

		$("#password").keydown(function(e) {
			if (e.keyCode==13) {
				fnLogIn();
			}
		});

		$("#btnLogin").on('click', function() {
			fnLogIn();	
		});

		function fnLogIn() {
			
			if ($('#UserID').val()=="") {
				alert(errMsg3);
				$('#UserID').focus();
				return false;
			}
			
			if ($('#password').val()=="") {
				alert(errMsg4);
				$('#password').focus();
				return false;
			}
			
			$('#username').val("comn==d="+$('#UserID').val());
			$('#loginForm').submit();
		}
		
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

	</script>
</body>
</html>