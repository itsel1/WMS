<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
		<title>Login Page</title>
		<script type="text/javascript" src='assets/js/comm.js'></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
		
		<script language="javascript">
		$(document).ready(function(){
			 $("#UserPW").keydown(function(e){
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
				$('#frmLogin').submit();
		}

		</script>
	</head>

	<body class="login-layout light-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												Please Enter Your Information
											</h4>
											<div class="space-6"></div>

											<form name=frmLogin id='frmLogin' method=post action='login_m.php'>
												<fieldset>

													<!-- <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Station"  name='Station' id='Station' onKeyDown="javascript:coEnterTarget('UserID');"/>
															<i class="ace-icon fa fa-globe"></i>
														</span>
													</label> -->

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="User ID" name='UserID' id='UserID' onKeyDown="javascript:coEnterTarget('UserPW');" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" name='UserPW' id='UserPW'/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<a href="/regist">[회원가입]</a>
														</span>
													</label> 

													<div class="space"></div>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-primary"  id='btnLogin' >
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">Login</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
											</div>
										</div><!-- /.widget-main -->

									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
							</div><!-- /.position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');

		</script>
	</body>
</html>
