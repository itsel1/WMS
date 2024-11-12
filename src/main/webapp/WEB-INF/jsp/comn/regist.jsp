<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
		<title>regist Page</title>
		<script type="text/javascript" src='assets/js/comm.js'></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
		
		<script language="javascript">
		$(document).ready(function(){
			$('#btn_Send').click(function() {
		
		            if(!$('#agree').is(':checked')){
		                    alert('회원약간 동의 후 진행 가능합니다.');
		                    return false;
		            }
		
		            if(!$('#agree2').is(':checked')){ 
		                    alert('서비스약관 동의 후 진행 가능합니다.');
		                    return false;
		            }
		
					if(!$('#agree3').is(':checked')){
							alert('개인정보취급방침 동의 후 진행 가능합니다.');
							return false;
					}
		
		            window.location.href = "/signUp";
			 });
		});
		
		</script>
	</head>

	<body>
		<div class="login-layout light-login">
			<div class="main-container">
				<div class="main-content">
					<div class="row">
						<div class="col-sm-12">
								<div class="space-6"></div>
								<div class="position-relative">
									<div id="login-box" class="login-box visible widget-box no-border" style="width:60%;margin-left:auto;margin-right:auto;">
											<div class="widget-main">
												<h4 class="header blue lighter bigger">
													<i class="ace-icon fa fa-coffee green"></i>
	                                                회원가입
												</h4>
	                                            <div>
	                                            </br>
	                                                <table>
	                                                    <tr>
	                                                        <td style="font-weight:bold">회원약관</td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td>
	                                                            <textarea name="name" rows="8" cols="145" style="width:100%;" readonly>
	
	                                                            </textarea>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td><label><input type="checkbox" id="agree">동의 합니다.</label></td>
	                                                    </tr>
	                                                </table>
	                                            </br>
	                                                <table>
	                                                    <tr>
	                                                        <td style="font-weight:bold">서비스약관</td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td>
	                                                            <textarea name="name" rows="8" cols="145" style="width:100%;" readonly>
	
	                                                            </textarea>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td><label><input type="checkbox" id="agree2">동의 합니다.</label></td>
	                                                    </tr>
	                                                </table>
												</br>
													<table>
														<tr>
															<td style="font-weight:bold">개인정보취급방침</td>
														</tr>
														<tr>
															<td>
																<textarea name="name" rows="8" cols="145" style="width:100%;" readonly>
	
																</textarea>
															</td>
														</tr>
														<tr>
															<td><label><input type="checkbox" id="agree3">동의 합니다.</label></td>
														</tr>
													</table>
	                                        </br>
	                                                <table style="width:100%;">
	                                                    <tr>
	                                                        <td align=right><button id="btn_Send" class="btn btn-sm btn-primary">가입하기</button></td>
	                                                    </tr>
	                                                </table>
	                                            </br>
	                                            </br>
												</div>
										</div><!-- /.widget-body -->
								</div><!-- /.position-relative -->
							</div> 
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container -->
		</div><!-- /.login-layout -->
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
