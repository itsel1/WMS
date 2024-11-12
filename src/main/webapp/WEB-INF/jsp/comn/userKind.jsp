<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
	<style type="text/css">
		
		.main-content {
			width: 100%;
			height: 100%;
			background: #7681b3;
		}
		
		#inner-content-side {
			text-align: center;
			margin-top: 50px;
		}
		
		button {
			width: 300px;
			height: 150px;
			position: relative;
		    border: none;
		    display: inline-block;
		    padding: 15px 30px;
		    border-radius: 15px;
		    font-family: sans-serif;
		    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
		    text-decoration: none;
		    font-weight: 600;
		    font-size: 20px;
		    transition: 0.25s;
		    z-index: 0;
		}
		
		button:hover {
			letter-spacing: 2px;
			transform: scale(1.2);
			cursor: pointer;
		}
		
		button:active {
			transform(1.5);
		}
		
		.normal-btn {
			background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #e2ed42);
			color: white;
			background-size: 400% 400%;
    		animation: gradient1 5s ease infinite;
		}
		
		.normal-btn:hover {
			z-index: 1;
		}
		
		.return-btn {
			background: linear-gradient(-45deg, #e2ed42, #23a6d5, #e73c7e, #ee7752);
			color: white;
			background-size: 400% 400%;
			animation: gradient1 5s ease infinite;
		}
		
		.return-btn:hover {
			z-index: 1;
		}

		.return-btn2 {
			background: linear-gradient(-45deg, #e2ed42, #23a6d5, #e73c7e, #ee7752);
			color: white;
			background-size: 400% 400%;
			animation: gradient1 5s ease infinite;
		}

		@keyframes gradient1 {
		    0% {
		        background-position: 0% 50%;
		    }
		    50% {
		        background-position: 100% 50%;
		    }
		    100% {
		        background-position: 0% 50%;
		    }
		}
		
		p {
			font-weight: 700;
			font-size: 20px;
		}


		a:hover {
			text-decoration: none;
		}
		
	</style>
	<!-- basic scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head>
	<title>회원 가입</title>
	<body>
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try {
					ace.settings.loadState('main-container')
				} catch (e) {
				}
			</script>
			<div class="main-content">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" id="current-language" value="${nowLocale}"/>
				<div class="main-content-inner">
					<div class="page-content">
						<div class="page-header">
							<h1>
								<i class="fa fa-leaf"></i>
								WMS
							</h1>
						</div>
						<div class="select-lang">
							<select id="locale-lang">
								<option value="en" <c:if test="${nowLocale eq 'en'}">selected</c:if>><spring:message code="signup.lang.en" text="English" /></option>
								<option value="ko" <c:if test="${nowLocale eq 'ko'}">selected</c:if>><spring:message code="signup.lang.ko" text="Korean" /></option>
								<option value="ja" <c:if test="${nowLocale eq 'ja'}">selected</c:if>><spring:message code="signup.lang.ja" text="Japanese" /></option>
								<option value="zh" <c:if test="${nowLocale eq 'zh'}">selected</c:if>><spring:message code="signup.lang.zh" text="Chinese (Traditional)" /></option>
							</select>
						</div>
						<div id="inner-content-side">
							<!-- 기본정보 Start -->
							<div>
								<p>
									<spring:message code="kinduser.title" text="ACI Account Creation"/>
								</p>
								<br/>
								<button class="normal-btn" type="button">
							        <spring:message code="kinduser.user.normal" text="Regular Seller"/><br>
							        <spring:message code="kinduser.account1" text="Account"/>
							    </button>
							    <button class="return-btn" type="button">
									<spring:message code="kinduser.user.coupang" text="Return Seller"/><br>
							        <spring:message code="kinduser.account2" text="Account (Coupang)"/>
							    </button>
							    <button class="return-btn2" type="button">
						    		<spring:message code="kinduser.user.return" text="Return Seller"/><br>
							        <spring:message code="kinduser.account3" text="Account (ACI)"/>
							    </button>
							</div>
							<br/><br/>
							<a href="/comn/login"><i class="fa fa-angle-left"></i>&nbsp;&nbsp;<spring:message code="kinduser.backToLogin" text="Back to Login Page"/></a>
							
							
							<!-- <div class="row">
								<div class="col-xs-12 col-lg-9 col-lg-offset-3">
									<button class="button_base b01_simple_rollover" style="width:35%;height: 200px;font-size: medium;" id="return1" name="return1">일반 회원가입</button>
									<button class="button_base b01_simple_rollover" style="width:35%;height: 200px;font-size: medium;" id="return2" name="return2">반품접수 회원가입</button>
								</div>
							</div> -->
						</div>
					</div>
				</div>
			</div>
			<!-- /.main-content -->
		</div>
	
	
		<!-- Main container End-->
	
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
		<!-- Footer End -->
	
		<!--[if !IE]> -->
		
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
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
	
		<script src="/assets/js/chosen.jquery.min2.js"></script>
		
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
		
		<!-- script on paging end -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$(".normal-btn").on("click",function(e){
						location.href="/signUp";
					});
					$(".return-btn").on("click",function(e){
						location.href="/returnSignUp";
						//location.href="/return/thridParty/getExpress";
					});
					$(".return-btn2").on("click",function(e){
						location.href="/normalReturnSignUp";
					});
				});

				$("#locale-lang").change(function(e) {
					var value = $("#locale-lang").val();
					location.href = "/kind/user?lang="+value;
				});
			});
	
			
			
		</script>
		<!-- script addon end -->
	</body>
</html>