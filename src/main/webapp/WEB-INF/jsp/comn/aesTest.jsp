<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		.clock {
		    font-size: 24px;
		    font-weight: bold;
		    padding: 10px;
		    background-color: #f2f2f2;
		    border: 1px solid #ccc;
		    border-radius: 5px;
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
						<div id="inner-content-side">
							<h1></h1>
							<form id="form" name="form">
								<table style="width:50%;">
									<tr>
										<th colspan="2" style="text-align:center;height:30px;">
											<div class="clock" id="clock">YYYYMMDDHHIISS</div>
										</th>
									</tr>
									<tr>
										<th style="text-align:center;height:28px;" colspan="2">Encryption</th>
									</tr>
									<tr>
										<th style="width:20%;text-align:center;">Enter text<br>to be Encrypted</th>
										<td style="width:80%;">
											<input type="text" name="encrypt-str" id="encrypt-str" value="" style="width:100%;">
										</td>
									</tr>
									<tr>
										<th style="text-align:center;">Secret Key</th>
										<td>
											<input type="text" name="encrypt-key" id="encrypt-key" value="" style="width:100%;">
										</td>
									</tr>
									<tr><td colspan="2" style="height:5px;"></td></tr>
									<tr>
										<th></th>
										<td><input type="button" id="encrypt-btn" value="Encrypt" style="width:100%;"></td>
									</tr>
									<tr><td colspan="2" style="height:5px;"></td></tr>
									<tr>
										<th style="text-align:center;">Encrypted Output</th>
										<td>
											<input type="text" id="encrypted-str" value="" style="width:100%;" readonly>
										</td>
									</tr>
								</table>
								<br/><br/>
								<table style="width:50%;">
									<tr>
										<th style="text-align:center;height:28px;" colspan="2">Decryption</th>
									</tr>
									<tr>
										<th style="width:20%;text-align:center;">Enter text<br>to be Decrypted</th>
										<td style="width:80%;">
											<input type="text" name="decrypt-str" id="decrypt-str" value="" style="width:100%;">
										</td>
									</tr>
									<tr>
										<th style="text-align:center;">Secret Key</th>
										<td>
											<input type="text" name="decrypt-key" id="decrypt-key" value="" style="width:100%;">
										</td>
									</tr>
									<tr><td colspan="2" style="height:5px;"></td></tr>
									<tr>
										<th></th>
										<td><input type="button" id="decrypt-btn" value="Decrypt" style="width:100%;"></td>
									</tr>
									<tr><td colspan="2" style="height:5px;"></td></tr>
									<tr>
										<th style="text-align:center;">Decrypted Output</th>
										<td>
											<input type="text" id="decrypted-str" value="" style="width:100%;" readonly>
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
				$("#encrypt-btn").click(function() {
					var encryptString = $("#encrypt-str").val();
					var secretKey = $("#encrypt-key").val();
					sendAesTest(encryptString, secretKey, 'enc');
				});

				$("#decrypt-btn").click(function() {
					var decryptString = $("#decrypt-str").val();
					var secretKey = $("#decrypt-key").val();
					sendAesTest(decryptString, secretKey, 'dec');
				});
			});
			

			function sendAesTest(str, key, type) {
				$.ajax({
					url : '/api/aesTestResult',
					type : 'GET',
					data : {str:str, key:key, type:type},
					success : function(result) {
						if (type == 'enc') {
							$("#encrypted-str").val(result.str);
						} else {
							$("#decrypted-str").val(result.str);
						}
					},
					error : function(xhr, status) {
						alert("failed");
					}
				});
			}

			function updateClock() {
				console.log(123);
			    const now = new Date();
			    console.log(now);
			    const year = now.getFullYear();
			    const month = String(now.getMonth() + 1).padStart(2, '0');
			    const day = String(now.getDate()).padStart(2, '0');
			    const hours = String(now.getHours()).padStart(2, '0');
			    const minutes = String(now.getMinutes()).padStart(2, '0');
			    const seconds = String(now.getSeconds()).padStart(2, '0');
				var nowTime = year+month+day+hours+minutes+seconds;
			    const formattedTime = `${year}${month}${day}${hours}${minutes}${seconds}`;
			    document.getElementById('clock').textContent = nowTime;
			}

			setInterval(updateClock, 1000);

			updateClock();
		</script>
		<!-- script addon end -->
		
	</body>
</html>
