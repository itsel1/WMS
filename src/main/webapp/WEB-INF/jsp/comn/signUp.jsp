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
<link rel="stylesheet" href="/custom/css/returnSignUp.css" />
<link href="https://harvesthq.github.io/chosen/chosen.css" rel="stylesheet"/>

</head>
<body>
	<div id="main-section">
		<div class="select-lang">
			<select id="locale-lang">
				<option value="en" <c:if test="${nowLocale eq 'en'}">selected</c:if>><spring:message code="signup.lang.en" text="English" /></option>
				<option value="ko" <c:if test="${nowLocale eq 'ko'}">selected</c:if>><spring:message code="signup.lang.ko" text="Korean" /></option>
				<option value="ja" <c:if test="${nowLocale eq 'ja'}">selected</c:if>><spring:message code="signup.lang.ja" text="Japanese" /></option>
				<option value="zh" <c:if test="${nowLocale eq 'zh'}">selected</c:if>><spring:message code="signup.lang.zh" text="Chinese (Traditional)" /></option>
			</select>
		</div>
		<div class="main-title">
			<div class="main-logo"><img src="/image/desk_logo.png" alt="" class="aci_logo"></div>
		</div>
		<form id="registForm">
			<input type="hidden" id="current-language" value="${nowLocale}"/>
			<input type="hidden" id="userPw" name="userPw"/>
			<fieldset>
				<h2 class="form-title center"><spring:message code="signup.title" text="ACI Seller Account Creation"/></h2>
	    		<h3 class="form-subtitle center"></h3>
	    		<div class="fieldset-input-form">
	    			<div class="line-item-row">
	    				<div class="line-item-column">
							<label for="userId" class="input-label required-text">
								<spring:message code="signup.userId" text="Account ID"/>
							</label>
			    			<input type="text" id="userId" name="userId" autocomplete="off">
						</div>
						<div class="line-item-column">
							<label for="userId" class="input-label">
								<div style="height:25px;"></div>
							</label>
							<spring:message code="signup.duplCheck" text="Check ID Availability" var="duplCheck"/>
							<input type="button" id="check-button" value="${duplCheck}"/>
						</div>
	    			</div>
	    			<div class="line-item line-item-tooltip">
	    				<span class="tooltip-alert">
	    					&#10004; <spring:message code="signup.userId.tooltip.part1" text="Your Account ID must begin with an English letter." /><br/>
							&#10004; <spring:message code="signup.userId.tooltip.part2" text="Account IDs must be between 6 and 20 characters, including both letters and numbers" />
	    				</span>
	    			</div>
					<div class="line-item-row">
						<div class="line-item-column">
							<label for="password1" class="input-label required-text"><spring:message code="signup.password" text="password"/></label>
			    			<input type="password" id="password1" autocomplete="new-password">
						</div>
						<div class="line-item-column">
							<label for="password2" class="input-label required-text"><spring:message code="signup.passwordConfirm" text="Confirm Password"/></label>
		    				<input type="password" id="password2" autocomplete="off">
						</div>
		    		</div>
		    		<div class="line-item line-item-tooltip">
						<span class="tooltip-alert" id="passwordMsg">
							&#10004; <spring:message code="signup.password.tooltip" text="Passwords must contain at least 8 characters and include at least 1 number, letter and special character." />
						</span>
	    			</div>
		    		<div class="line-item-row">
						<div class="line-item-column">
							<label for="comName" class="input-label required-text"><spring:message code="signup.comName" text="Company Name"/></label>
			 				<input type="text" id="comName" name="comName" autocomplete="off">
						</div>
						<div class="line-item-column">
							<label for="userName" class="input-label required-text"><spring:message code="signup.userName" text="Main point of Contact"/></label>
			 				<input type="text" id="userName" name="userName" autocomplete="off">
						</div>
		    		</div>
		    		
		    		<div class="line-item-row">
		    			<div class="line-item-column">
		    				<label for="userEmail" class="input-label required-text"><spring:message code="signup.userEmail" text="Contact Email"/></label>
							<input type="text" id="userEmail" name="userEmail" autocomplete="off">
			 			</div>
			 			<div class="line-item-column">
		    				<label for="userTel" class="input-label required-text"><spring:message code="signup.userTel" text="Contact Number"/></label>
			 				<input type="text" id="userTel" name="userTel" autocomplete="off">
			 			</div>
		    		</div>
		    		<div class="line-item-row">
		    			<div class="line-item-column">
		    				<label for="userHp" class="input-label"><spring:message code="signup.userHp" text="Contact Number (Mobile)"/></label>
							<input type="text" id="userHp" name="userHp" autocomplete="off">
			 			</div>
			 			<div class="line-item-column">
			 				<label for="userZip" class="input-label"><spring:message code="signup.userZip" text="Postal Code"/></label>
							<input type="text" id="userZip" name="userZip" autocomplete="off">
			 			</div>
		    		</div>
		    		<div class="line-item">
		    			<label for="userAddr" class="input-label required-text"><spring:message code="signup.userAddr" text="Address 1"/></label>
						<input type="text" id="userAddr" name="userAddr" autocomplete="off">
		    		</div>
		    		<div class="line-item">
		    			<label for="userAddrDetail" class="input-label"><spring:message code="signup.userAddrDetail" text="Address 2"/></label>
						<input type="text" id="userAddrDetail" name="userAddrDetail" autocomplete="off">
		    		</div>
		    		<div class="line-item-row">
		    			<label class="input-label required-text"><spring:message code="signup.userType" text="Please enter company type : "/></label>
		    			<div class="radio-layout">
		    				<input type="radio" id="corporate" name="etprYn" value="Y">
		    				<label for="corporate" class="radio-label"><spring:message code="signup.userType.corporate" text="Corporation"/></label>
		    				<input type="radio" id="individual" name="etprYn" value="N">
		    				<label for="individual" class="radio-label"><spring:message code="signup.userType.individual" text="Individual"/></label>
		    			</div>
		    		</div>
		    		
		    		<div class="line-item-row" id="store-layout">
		    			<div class="line-item-column">
		    				<label for="storeName" class="input-label"><spring:message code="signup.storeName" text="Store Name"/></label>
							<input type="text" id="storeName" name="storeName" autocomplete="off">
			 			</div>
			 			<div class="line-item-column">
			 				<label for="storeUrl" class="input-label"><spring:message code="signup.storeUrl" text="eCommerce website URL"/></label>
							<input type="text" id="storeUrl" name="storeUrl" autocomplete="off">
			 			</div>
		    		</div>
		    		<div class="line-item">
		    			<label for="orgNation" class="input-label required-text"><spring:message code="signup.normal.orgNation" text="Departure Country"/></label>
		    			<select class="chosen-select" name="orgNation" id="orgNation">
		    				<c:forEach items="${orgNations}" var="nations">
								<option value="${nations.nationCode}">${nations.nationEName} (${nations.nationName})</option>
							</c:forEach>
		    			</select>
		    		</div>
		    		<div class="line-item">
		    			<label for="dstnNation" class="input-label required-text"><spring:message code="signup.normal.dstnNation" text="Destination Country"/></label>
						<select class="chosen-select"  name="dstnNation" id="dstnNation" multiple="multiple">
			    			<c:forEach items="${nations}" var="nations">
			    				<option value="${nations.nationCode}">${nations.nationEName} (${nations.nationName})</option>
			    			</c:forEach>
			    		</select>
		    		</div>
			 	</div>
			</fieldset>
			<fieldset>
				<h2 class="form-title center">
					<spring:message code="signup.progress2title.part1" text="Addition Inputs Required for"/><br/>
					<spring:message code="signup.progress2title.part2" text="Customs Declaration / Customs Clearance"/>
				</h2>
				<h3 class="form-subtitle center">
					※ <spring:message code="signup.progress2subtitle" text="Please fill it out in English"/>
				</h3>
				<div class="fieldset-input-form">
					<div class="line-item-row">
						<input type="checkbox" id="checkAutoFill2">
						<label for="checkAutoFill2" class="auto-fill-label"><spring:message code="signup.checkAutoFill" text="Apply the same details as ACI Seller Account info"/></label>
					</div>
					<div class="line-item">
						<label for="comEName" class="input-label required-text"><spring:message code="signup.comName" text="Company Name"/></label>
						<input type="text" id="comEName" name="comEName" autocomplete="off">
					</div>
					<div class="line-item-row">
						<div class="line-item-column">
							<label for="userEState" class="input-label"><spring:message code="signup.userEState" text="State"/></label>
			 				<input type="text" id="userEState" name="userEState" autocomplete="off">
						</div>
						<div class="line-item-column">
							<label for="userECity" class="input-label"><spring:message code="signup.userECity" text="City"/></label>
			 				<input type="text" id="userECity" name="userECity" autocomplete="off">
						</div>
		    		</div>
					<div class="line-item">
						<label for="userEAddr" class="input-label required-text"><spring:message code="signup.userAddr" text="Address 1"/></label>
						<input type="text" id="userEAddr" name="userEAddr" autocomplete="off"> 
					</div>
					<div class="line-item">
						<label for="userEAddrDetail" class="input-label"><spring:message code="signup.userAddrDetail" text="Address 2"/></label>
						<input type="text" id="userEAddrDetail" name="userEAddrDetail" autocomplete="off"> 
					</div>
				</div>
			</fieldset>
			<fieldset>
				<h2 class="form-title center"><spring:message code="signup.progress3title" text="Enter Billing Information"/></h2>
				<h3 class="form-subtitle center"></h3>
				<div class="fieldset-input-form">
					<div class="line-item-row">
						<input type="checkbox" id="checkAutoFill3">
						<label for="checkAutoFill3" class="auto-fill-label"><spring:message code="signup.checkAutoFill" text="Apply the same details as ACI Seller Account info"/></label>
					</div>
					<div class="line-item">
						<label for="invComName" class="input-label required-text"><spring:message code="signup.comName" text="Company Name"/></label>
						<input type="text" id="invComName" name="invComName" autocomplete="off">
					</div>
					<div class="line-item-row">
		    			<div class="line-item-column">
		    				<label for="invComTel" class="input-label required-text"><spring:message code="signup.invUserTel" text="Company Representative Phone"/></label>
			 				<input type="text" id="invComTel" name="invComTel" autocomplete="off">
			 			</div>
			 			<div class="line-item-column">
		    				<label for="invUserName" class="input-label required-text"><spring:message code="signup.userName" text="Main point of Contact"/></label>
							<input type="text" id="invUserName" name="invUserName" autocomplete="off">
			 			</div>
		    		</div>
					<div class="line-item-row">
		    			<div class="line-item-column">
		    				<label for="invUserTel" class="input-label required-text"><spring:message code="signup.userTel" text="Contact Number"/></label>
			 				<input type="text" id="invUserTel" name="invUserTel" autocomplete="off">
			 			</div>
			 			<div class="line-item-column">
		    				<label for="invUserEmail" class="input-label required-text"><spring:message code="signup.userEmail" text="Contact Email"/></label>
							<input type="text" id="invUserEmail" name="invUserEmail" autocomplete="off" >
			 			</div>
		    		</div>
					<div class="line-item">
						<label for="invComAddr" class="input-label required-text"><spring:message code="signup.userAddr" text="Address 1"/></label>
						<input type="text" id="invComAddr" name="invComAddr" autocomplete="off">
					</div>
					<div class="line-item">
						<label for="invComAddrDetail" class="input-label"><spring:message code="signup.userAddrDetail" text="Address 2"/></label>
						<input type="text" id="invComAddrDetail" name="invComAddrDetail">
					</div>
				</div>
			</fieldset>
			<div class="form-button-field">
				<button type="button" id="backToList"><spring:message code="signup.button.cancel" text="Cancel"/></button>
				<button type="button" id="rgstrInfo"><spring:message code="signup.button.register" text="Register"/></button>
			</div>
		</form>
	</div>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script type="text/javascript">
	
		var idCheck = "N";
		var pwCheck = "N";
		var currentLang = $("#current-language").val();
		var chosenText1;
		var chosenText2;
		var	checkIdAlert1;
		var checkIdAlert2;
		var checkIdAlert3;
		var checkIdAlert4;
		var checkPwAlert1;
		var checkPwAlert2;
		var checkPwAlert3;
		var checkPwAlert4;
		var registAlert1;
		var registAlert2;
		var registAlert3;
		var registAlert4;
		var registAlert5;
		var registAlert6;
		var registAlert7;
		var errorAlert;
		
		
		if (currentLang == "ko") {
			chosenText1 = "국가를 선택해 주세요";
			chosenText2 = "결과를 찾지 못했습니다";
			checkIdAlert1 = "아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다";
			checkIdAlert2 = "아이디를 입력해 주세요";
			checkIdAlert3 = "사용 가능한 아이디 입니다";
			checkIdAlert4 = "중복된 아이디 입니다";
			checkPwAlert1 = "숫자, 특수문자, 영어 조합 8자리 이상을 입력해 주세요";
			checkPwAlert2 = "유효하지 않은 패스워드 입니다";
			checkPwAlert3 = "유효한 패스워드 입니다";
			checkPwAlert4 = "패스워드가 다릅니다";
			registAlert1 = "ID 중복체크를 진행해 주세요";
			registAlert2 = "패스워드를 확인해 주세요";
			registAlert3 = "Email을 확인해 주세요";
			registAlert4 = "등록 되었습니다";
			registAlert5 = "등록 중 오류가 발생하였습니다. 시스템 관리자에게 문의해 주세요";
			registAlert6 = "회사 유형을 선택해 주세요";
			registAlert7 = "반품 도착국가를 선택해 주세요";
			errorAlert = "시스템 오류가 발생하였습니다.";
			
		} else if (currentLang == "en") {
			chosenText1 = "Please choose a country";
			chosenText2 = "No results found";
			checkIdAlert1 = "Your Account ID must begin with an English letter. Account IDs must be between 6 and 20 characters, including both letters and numbers";
			checkIdAlert2 = "Please enter your Account ID";
			checkIdAlert3 = "The Account ID is available";
			checkIdAlert4 = "The Account ID is already taken";
			checkPwAlert1 = "Please enter at least 8 digits of a combination of numbers, special characters, and English";
			checkPwAlert2 = "Invalid password";
			checkPwAlert3 = "Valid password";
			checkPwAlert4 = "The password is different";
			registAlert1 = "Please proceed with ID verification";
			registAlert2 = "Please confirm the password";
			registAlert3 = "Please check the e-mail";
			registAlert4 = "Registration has been completed";
			registAlert5 = "An error occurred during registration. Please contact the system administrator";
			registAlert6 = "Please enter company type";
			registAlert7 = "Please select return destination country";
			errorAlert = "system error occured";
			
		} else if (currentLang == "ja") {
			chosenText1 = "国を選択してください";
			chosenText2 = "結果が見つかりませんでした";
			checkIdAlert1 = "あなたのアカウントIDは英文字で始まる必要があります。アカウントIDは6〜20文字で、英字と数字の両方を含む必要があります";
			checkIdAlert2 = "アカウントIDを入力してください";
			checkIdAlert3 = "アカウントIDが利用可能です";
			checkIdAlert4 = "アカウントIDはすでに使用されています";
			checkPwAlert1 = "数字、特殊文字、英字の組み合わせで少なくとも8桁を入力してください";
			checkPwAlert2 = "無効なパスワード";
			checkPwAlert3 = "有効なパスワードです";
			checkPwAlert4 = "パスワードが異なります";
			registAlert1 = "ID確認を進めてください";
			registAlert2 = "パスワードを確認してください";
			registAlert3 = "メールを確認してください";
			registAlert4 = "登録が完了しました";
			registAlert5 = "登録中にエラーが発生しました。システム管理者に連絡してください";
			registAlert6 = "会社の種類を入力してください";
			registAlert7 = "返品先の国を選択してください";
			errorAlert = "システムエラーが発生しました";
			
		} else if (currentLang == "zh") {
			chosenText1 = "請選擇國家";
			chosenText2 = "未找到結果";
			checkIdAlert1 = "您的帳戶ID必須以英文字母開頭。帳戶ID必須在6到20個字符之間，包括字母和數字";
			checkIdAlert2 = "請輸入您的帳戶ID";
			checkIdAlert3 = "帳戶ID是可用的";
			checkIdAlert4 = "帳戶ID已被佔用";
			checkPwAlert1 = "請輸入至少8個數字、特殊字符和英文字母的組合";
			checkPwAlert2 = "無效的密碼";
			checkPwAlert3 = "密碼有效";
			checkPwAlert4 = "密碼不相同";
			registAlert1 = "請進行身份驗證";
			registAlert2 = "請確認密碼";
			registAlert3 = "請檢查電子郵件";
			registAlert4 = "註冊已完成";
			registAlert5 = "註冊過程中發生錯誤。請聯繫系統管理員";
			registAlert6 = "請輸入公司類型";
			registAlert7 = "請選擇退貨目的地國家";
			errorAlert = "系統錯誤發生";
			
		}

		
		$(document).ready(function() {
			$("#store-layout").hide();
		});

		$("input[name='etprYn']").on('change', function(e) {
			if ($("input[name='etprYn']:checked").val() == "Y") {
				$("#store-layout").show();
				$("#storeName").closest('.line-item-row').find('label[for="storeName"]').addClass('required-text');
			} else {
				$("#store-layout").hide();
				$("#storeName").closest('.line-item-row').find('label[for="storeName"]').removeClass('required-text');
			}
		});
		
		$(".chosen-select").chosen({
			placeholder_text_multiple: chosenText1,
			placeholder_text_single: chosenText1,
		    no_results_text: chosenText2
		});

		$('#backToList').on('click', function(e) {
			location.href = "/comn/login";
		});	

		$("#locale-lang").change(function(e) {
			var value = $("#locale-lang").val();
			location.href = "/signUp?lang="+value;
		});

		$("#checkAutoFill2").on('click', function(e) {
			var checked = $("#checkAutoFill2").is(":checked");
			if (checked) {
				$("#comEName").val($("#comName").val());
				$("#userEAddr").val($("#userAddr").val());
				$("#userEAddrDetail").val($("#userAddrDetail").val());
			}
		});

		$("#checkAutoFill3").on('click', function(e) {
			var checked = $("#checkAutoFill3").is(":checked");
			if (checked) {
				$("#invComName").val($("#comName").val());
				$("#invComTel").val($("#userTel").val());
				$("#invUserName").val($("#userName").val());
				$("#invUserTel").val($("#userTel").val());
				$("#invUserEmail").val($("#userEmail").val());
				$("#invComAddr").val($("#userAddr").val());
				$("#invComAddrDetail").val($("#userAddrDetail").val());
			}
		});

		$("#check-button").on('click', function(e) {

			if($("#userId").val() == ""){
				alert(checkIdAlert2);
				$("#userId").focus();
				return false;
			}
			
			var idReg = /^[a-zA-Z]+[a-zA-Z0-9]{5,19}$/g;
			if(!idReg.test($('#userId').val())){
				alert(checkIdAlert1);
				return false;
			}

			var formData = $("#registForm").serialize();
			$.ajax({
				url: '/comn/idCntCheck',
				type: 'POST',
				data: formData,
				beforeSend : function(xhr) {
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data == "S"){
						alert(checkIdAlert3);
						idCheck = "Y";
						return true;
					}else{
						alert(checkIdAlert4);
						$("#userId").val("");
						idCheck = "N";
						$("#userId").focus();
						return true;
					}
	            }, 		    
	            error : function(xhr, status) {
		            alert(errorAlert);
	            }
			});

		});

		$('input[id*="password"]').on('change', function(e) {
			var regExpPw = /(?=.*\d{1,20})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$/;

			if($('#password1').val() == "" || $('#password2').val()== "") {
				pwCheck = "N";
				return false;
			}
			
			if($('#password1').val() == $('#password2').val()) {
				
				if(!regExpPw.test($('#password1').val())){
					alert(checkPwAlert1);
					$('#passwordMsg').text(checkPwAlert2);
					$('#passwordMsg').css("color", "#7B241C");
					pwCheck="N";
					return false;
				} else {
					$('#passwordMsg').text(checkPwAlert3);
					$('#passwordMsg').css("color", "#229954");
					pwCheck= "Y";	
				}
				
			} else {
				$('#passwordMsg').text(checkPwAlert4);
				$('#passwordMsg').css("color", "#7B241C");
				pwCheck="N";
			}
		});

		$("#rgstrInfo").on('click', function(e) {


			if (idCheck == "N") {
				alert(registAlert1);
				$("#userId").focus();
				return false;
			}

			if (pwCheck == "N") {
				alert(registAlert2);
				$("#password1").focus();
				return false;
			}

			if ($("#password1").val() != $("#password2").val()) {
				alert(registAlert2);
				$("#password1").focus();
				return false;
			}

			if ($("#comName").val() == "") {
				$("#comName").focus();
				return false;
			}

			if ($("#userName").val() == "") {
				$("#userName").focus();
				return false;
			}

			if ($("#userEmail").val() == "") {
				$("#userEmail").focus();
				return false;
			}

			if ($("#userTel").val() == "") {
				$("#userTel").focus();
				return false;
			}

			if ($("#userAddr").val() == "") {
				$("#userAddr").focus();
				return false;
			}

			if (!$("input[name='etprYn']").is(':checked')) {
				alert(registAlert6);
				return false;
			}

			if ($("input[name='etprYn']:checked").val() == "Y") {
				if ($("#storeName").val() == "") {
					$("#storeName").focus();
					return false;
				}
			}

			var selected = true;
			$(".chosen-select").each(function(e) {
				if ($(this).val() == null) {
					selected = false;
					return false;
				}
			});

			if (!selected) {
				alert(registAlert7);
				return false;
			}

			if ($("#comEName").val() == "") {
				$("#comEName").focus();
				return false;
			}

			if ($("#comEAddr").val() == "") {
				$("#comEAddr").focus();
				return false;
			}

			if ($("#invComName").val() == "") {
				$("#invComName").focus();
				return false;
			}

			if ($("#invComTel").val() == "") {
				$("#invComTel").focus();
				return false;
			}

			if ($("#invUserName").val() == "") {
				$("#invUserName").focus();
				return false;	
			}

			if ($("#invUserTel").val() == "") {
				$("#invUserTel").focus();
				return false;
			}

			if ($("#invUserEmail").val() == "") {
				$("#invUserEmail").focus();
				return false;
			}

			if ($("#invComAddr").val() == "") {
				$("#invComAddr").focus();
				return false;
			}

			$("#userPw").val($("#password1").val());
			
			var formData = $("#registForm").serialize();
			$.ajax({
				url: '/comn/registUser',
				type: 'POST',
				data: formData,
				beforeSend : function(xhr) {   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data == "S"){
						alert(registAlert4);
						location.href = "/comn/login";
					}else{
						alert(registAlert5);
					}
	            }, 		    
	            error : function(xhr, status) {
		            alert(errorAlert);
	            }
			});
		});


	</script>
</body>
</html>