<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}

.inner-content-side>div {
	text-align: center;
}

input[type=text]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}
input[type=password]{
	border-radius: 0.5rem !important;
	padding: 1rem 1rem;
}

.vertiMidle{
	vertical-align: middle;
}

.pd-1rem{
	padding: 1rem 1rem;
	font-size: 14px; 
	font-weight: 400;
}

.addBtn {
 	background: #3296FF;
  	height: 35px;
  	width: 60px;
  	color: white;
  	border: none;
 }
 
 .addBtn:hover {
 	background: #50B4FF;
 }
 
 .delBtn {
 	background: white;
 	height: 35px;
  	width: 60px;
  	border: 1px solid #3296FF;
 }
 
 .delBtn:Hover {
 	background: #B8D7FF;
 	color: white;
 	border: none;
 }

#changePw {
	width: 130px;
	height: 40px;
	border: 1px solid #3C5A91;
	border-radius: 5px;
	background: #3C5A91;
	color: white;
	box-shadow: 3px 3px 3px #ccc;
}

#changePw:hover {
	border: 1px solid #506EA5;
	border-radius: 5px;
	background: #506EA5;
	color: white;
}

#changePw:active {
	margin-left: 3px;
	box-shadow: none;
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
 
 #addModal {
 	width: 20%;
 }
 
 #delModal {
	width: 20%; 
}
 
 .modal-footer {
 	background:white;
 }

</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>마이페이지</title>
<body class="no-skin">
	<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>마이 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">정보 수정</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h1>${userInfo.userId} 정보 수정</h1>
							</div>
							<form name="userInfoForm" id="userInfoForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="inner-content-side">
									<!--  -->
									<div class="row">
										<div class="col-xs-12 col-lg-6" >
											<!-- 기본정보 Start -->
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>기본정보</h2>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>아이디</label>
														<input class="width-100" type="text" name="userId" readonly id="userId" value="${userInfo.userId}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>고객 번호</label>
														<input class="width-100" type="text" name="userUid" readonly id="userUid" value="${userInfo.userUid}"/>
													</div>
												</div>
												<div class="row form-group hr-8" id="changePw-btn" style="margin-top:20px;">
													<div class="col-lg-6 col-xs-12">
														<input type="button" id="changePw" value="비밀번호 변경" />
													</div>
												</div>
												<div class="row form-group hr-8" id="password-layout">
													<div class="col-lg-6 col-xs-12">
														<label>패스워드</label>
														<input class="width-100" type="password" name="password1" id="password1" value="" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>패스워드 확인</label>
														<input class="width-100" type="password" name="password2" id="password2" value="" />
														<span class="" style="display:block;" id="passwordMsg"></span>
													</div>
												</div>
												<div class="row form-group">
													<input type="hidden" name="userPw" id="userPw"/>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>담당자 명</label>
														<input class="width-100" type="text" name="userName" id="userName" value="${userInfo.userName}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>상호명</label>
														<input class="width-100" type="text" name="comName" id="comName" value="${userInfo.comName }" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>Email</label>
														<input class="width-100" type="text" name="userEmail" id="userEmail" value="${userInfo.userEmail }" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label class="bigger-110">우편번호</label><br/>
														<input type="text" class="width-30 hr-8" name="userZip" id="userZip" value="${userInfo.userZip }" /> 
														<button style="margin-left:10px;" id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
															<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
														</button>
														<br>
														<label class="bigger-110">주소</label><br/>
														<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="${userInfo.userAddr }" />
														<label class="bigger-110">상세 주소</label><br/>
														<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="${userInfo.userAddrDetail }" />
													</div>
												</div>
												
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>상점명</label>
														<input class="width-100" type="text" name="storeName" id="storeName" value="${userInfo.storeName }" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>쇼핑몰 URL</label>
														<input class="width-100" type="text" name="storeUrl" id="storeUrl" value="${userInfo.storeUrl}" />
													</div>
												</div> 
											</div>
										</div>
										<div class="col-xs-12 col-lg-6">
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>청구지 정보</h2>
											</div>
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>업체명</label>
														<input class="width-100" type="text" name="invComName" id="invComName" value="${invUserInfo.invComName }" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>대표 전화번호</label>
														<input class="width-100" type="text" name="invComTel" id="invComTel" value="${invUserInfo.invComTel}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>담당자명</label>
														<input class="width-100" type="text" name="invUserName" id="invUserName" value="${invUserInfo.invUserName}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>담당자 전화번호</label>
														<input class="width-100" type="text" name="invUserTel" id="invUserTel" value="${invUserInfo.invUserTel}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-8 col-xs-12">
														<label>청구지 Email</label>
														<input class="width-100" type="text" name="invUserEmail" id="invUserEmail" value="${invUserInfo.invUserEmail}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label class="bigger-110">청구지 주소</label><br/>
														<input type="text" class="width-100 hr-8" name="invComAddr" id="invComAddr" value="${invUserInfo.invComAddr}" />
														<label class="bigger-110">청구지 상세 주소</label><br/>
														<input type="text" class="width-100 hr-8" name="invComAddrDetail" id="invComAddrDetail" value="${invUserInfo.invComAddrDetail}" />
													</div>
												</div>
											</div>
										</div>
										<!-- 세관신고 정보 End -->
									</div>
									<div class="row">
										<div class="col-xs-12 col-lg-6" >
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>세관 신고 정보</h2>
											</div>
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>이름 또는 상호명(영문)</label>
														<input class="width-100" type="text" name="comEName" id="comEName" value="${userInfo.comEName}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>휴대전화번호</label>
														<input class="width-100" type="text" name="userHp" id="userHp" value="${userInfo.userHp }" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>전화번호</label>
														<input class="width-100" type="text" name="userTel" id="userTel" value="${userInfo.userTel}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>주(영문)</label>
														<input class="width-100" type="text" numberOnly name="userEState" id="userEState" value="${userInfo.userEState}" />
													</div>
													<div class="col-lg-6 col-xs-12">
														<label>도시(영문)</label>
														<input class="width-100" type="text" numberOnly name="userECity" id="userECity" value="${userInfo.userECity}" />
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<label class="bigger-110">주소(영문)</label><br/>
														<input type="text" class="width-100 hr-8" name="userEAddr" id="userEAddr" value="${userInfo.userEAddr }" />
														<label class="bigger-110">상세 주소(영문)</label><br/>
														<input type="text" class="width-100 hr-8" name="userEAddrDetail" placeholder="상세 주소(영문)" id="userEAddrDetail" value="${userInfo.userEAddrDetail }" />
													</div>
												</div>
											</div>
										</div>
										<!-- 설정정보 Start -->
									</div>
									<div class="row">
										<div class="col-xs-12 col-lg-6" >
											<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
												<h2>설정정보</h2>
											</div>
											<!-- style="border:1px solid red" -->
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="row form-group hr-8">
													<div class="col-lg-6 col-xs-12">
														<label>기업/개인</label>
														<div class="radio">
															<label>
																<input name="etprView" type="radio" class="ace" value="Y" disabled <c:if test="${userInfo.etprYn eq 'Y'}"> checked</c:if>/>
																<span class="lbl"> 기업</span>
															</label>
															<label>
																<input name="etprView" type="radio" class="ace" value="N" disabled <c:if test="${userInfo.etprYn eq 'N'}"> checked</c:if>/>
																<span class="lbl"> 개인</span>
															</label>
															<input type="hidden" id="etprYn" name="etprYn" 
																<c:if test="${targetPage eq 'ent'}">
																	value="Y"
																</c:if>
																<c:if test="${targetPage ne 'ent'}">
																	value="N"
																</c:if>
															/>
														</div>
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<div class="col-lg-3 col-xs-12 pd-1rem" >출발지</div>
														<div class="col-lg-6 col-xs-12">
														<c:forEach items="${userOrgNation}" var="userOrgNation">
															<input type="hidden" id="userOrgNation" name="userOrgNation" value="${userOrgNation}">
														</c:forEach>
															
															<select disabled multiple class="chosen-select form-control tag-input-style width-100 pd-1rem" id="orgNation" name="orgNation" data-placeholder="국가를 선택해 주세요">
																<option value="">  </option>
																<c:forEach items="${orgNations}" var="nations">
																	<option value="${nations.nationCode}">${nations.nationName}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-12 col-xs-12">
														<div class="col-lg-3 col-xs-12 pd-1rem" >도착지</div>
														<div class="col-lg-6 col-xs-12">
														<c:forEach items="${userDstnNation}" var="userDstnNation">
															<input type="hidden" id="userDstnNation" name="userDstnNation" value="${userDstnNation.dstnNation}">
														</c:forEach>
															
															<select disabled multiple class="chosen-select form-control tag-input-style width-100 pd-1rem" id="dstnNation" name="dstnNation" data-placeholder="국가를 선택해 주세요">
																<option value="">  </option>
																<c:forEach items="${nations}" var="nations">
																	<option value="${nations.nationCode}">${nations.nationName}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 설정정보 End -->
									<div class="row">
										<div class="col-lg-12 col-xs-12">
											<br />
											<div class="col-lg-1 col-xs-6 col-lg-offset-5">
												<div id="button-div">
													<div id="backPage" style="text-align: center">
														<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
													</div>
												</div>
											</div>
											<div class="col-lg-1 col-xs-6">
												<div id="button-div">
													<div id="updateUser" style="text-align: center">
														<button id="updateInfo" type="button" class="btn btn-sm btn-primary">수정</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal modal-center fade" id="myModal" role="dialog">
									<div class="modal-dialog modal-center" id="addModal">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">비밀번호 확인</h4>
											</div>
											<div class="modal-body">
												<div class="row form-group hr-8">
													<div class="col-xs-12">
														<label>아이디</label>
														<input class="width-100" type="text" value="${userInfo.userId}" disabled/>
													</div>
												</div>
												<div class="row form-group hr-8" style="margin-top:10px;">
													<div class="col-xs-12">
														<label>현재 비밀번호</label>
														<input class="width-100" type="password" name="checkPw" id="checkPw" value="" />
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button id="modalSubmit" type="button" class="addBtn">확인</button>
												<button type="button" class="delBtn" data-dismiss="modal">취소</button>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
	</div>




	<!-- headMenu Start -->
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<!-- SideMenu Start -->
		<!-- SideMenu End -->

		<div class="main-content">

			
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	
	<script src="/testView/js/main.js"></script>
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
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		var pwCheck = "N";
		jQuery(function($) {
			$(document).ready(function() {
				$("#10thMenu").toggleClass('open');
				$("#10thMenu").toggleClass('active');
				$("#10thTwo").toggleClass('active');

				var orgNation = new Array() ;
				$("input[name=userOrgNation]").each(function(index, item){
					orgNation.push($(item).val());
				});
				$("#orgNation").val(orgNation);
				$("#orgNation option:selected").trigger("chosen:updated");

				var dstnNation = new Array() ;
				$("input[name=userDstnNation]").each(function(index, item){
					dstnNation.push($(item).val());
				});
				$("#dstnNation").val(dstnNation);
				$("#dstnNation option:selected").trigger("chosen:updated");
				$("#dstnNation").trigger("change");

				$("#password-layout").addClass('hide');
			});

			//initiate dataTables plugin
			
			$('#backToList').on('click', function(e) {
				history.back();
			});
			

			$('#updateInfo').on('click', function(e) {
				if($("#userId").val() == ""){
					alert("아이디를 입력해 주세요");
					return false;
				}

				if($("#userName").val() == ""){
					alert("사용자 명을 입력해 주세요");
					return false;
				}

				if($('#password1').val() != ""){
					if(pwCheck == "N"){
						alert("패스워드를 확인해 주세요");
						return false;
					}

					if($('#password1').val() != $('#password2').val()){
						alert("패스워드를 확인해 주세요");
						return false;
					}
				}
				

				if($('#userEmail').val() == ""){
					alert("Email을 확인해 주세요");
					return false;
				}else{
					/*
					var regExpMail = /^[a-zA-z0-9_+.-]+@([a-zA-z0-9-]+\.)+[a-zA-z0-9]{2,4}$/;
					if(!regExpMail.test($('#userEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#userEmail').focus();
						return false;
					}*/
				}

				if($('#userTel').val() == ""){
					alert("전화번호를 확인해 주세요");
					return false;
				}

				if($('#invComName').val() == ""){
					alert("청구지 업체명을 확인해 주세요");
					$('#invComName').focus();
					return false;
				}

				if($('#invComTel').val() == ""){
					alert("청구지 업체 전화번호를 확인해 주세요");
					$('#invComTel').focus();
					return false;
				}

				if($('#invUserName').val() == ""){
					alert("담당잦 명을 확인해 주세요");
					$('#invUserName').focus();
					return false;
				}

				if($('#invUserTel').val() == ""){
					alert("담당자 전화번호를 확인해 주세요");
					$('#invUserTel').focus();
					return false;
				}


				if($('#invUserEmail').val() == ""){
					alert("청구지 Email을 확인해 주세요");
					$('#invUserEmail').focus();
					return false;
				}else{
					/*
					var regExpMail = /^[a-zA-z0-9_+.-]+@([a-zA-z0-9-]+\.)+[a-zA-z0-9]{2,4}$/;
					if(!regExpMail.test($('#invUserEmail').val())){
						alert("올바른 Email 형식이 아닙니다.");
						$('#invUserEmail').focus();
						return false;
					}*/
				}

				
				if($('#invComAddr').val() == ""){
					alert("청구지 주소를 확인해 주세요");
					$('#invComAddr').focus();
					return false;
				}

				if($('#invComAddrDetail').val() == ""){
					alert("청구지 상세주소를 확인해 주세요");
					$('#invComAddrDetail').focus();
					return false;
				}

				if($('#orgNation').val() == null){
					alert("출발지를 선택해 주세요");
					return false;
				}

				if($('#dstnNation').val() == null){
					alert("도착지를 선택해 주세요");
					return false;
				}
				
			   /*  if(str == ""){
					alert("택배회사를 체크해 주세요");
					return false;
			    } */
				
			    $('#userPw').val($('#password1').val());
				var formData = $("#userInfoForm").serialize();
 				$.ajax({
					url:'/cstmr/myPage/userInfo',
					type: 'PATCH',
					data: formData,
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if(data == "S"){
							alert("수정 되었습니다.");
							location.href="/cstmr/myPage/userInfo";
						}else{
							alert("수정중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
		                
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 
			});
			
			$('input[id*="password"]').on('change',function(){
				if($('#password1').val()=="" || $('#password2').val()== ""){
					$('#passwordMsg').text('');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').removeClass('blue');
					pwCheck = "N";
					return false;
				}
				if($('#password1').val() == $('#password2').val()){
					$('#passwordMsg').text('유효한 패스워드 입니다.');
					$('#passwordMsg').removeClass('red');
					$('#passwordMsg').addClass('blue');
					pwCheck= "Y";
				}else{
					$('#passwordMsg').text('패스워드가 다릅니다.');
					$('#passwordMsg').removeClass('blue');
					$('#passwordMsg').addClass('red');
					pwCheck="Y";
				}
			})
			

			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}

			/* $('.chosen-select').on('change',function(){
				var str2 = [];
				$("input:checkbox:checked").each(function (index) {  
			        str2.push($(this).val());  
			    });  
				$('#trkComValueList').val(str2);
				var formData = $("#userInfoForm").serialize();
				if($('.chosen-select').val() == null){
					$("#trackComs").empty();
					return;
				}
				$.ajax({
					url:'/cstmr/myPage/transCom',
					type: 'GET',
					data:formData,
					success : function(data) {
						$('#trackComs').html(data);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});  */
		}) 
		
		function fn_deleteIp(allowIp){
			var param1 = $("#"+allowIp).children()[0].value;
			$.ajax({
				url:'/cstmr/myPage/deleteAllowIp',
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						$("#"+allowIp).remove();
						alert("지정 IP에서 삭제 되었습니다.");
					}else{
						alert("삭제 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		}

		$("#addIp").on('click',function(e){
			var param1 = $("#inputIp").val();
			var check = 0;
			var num = $("div[name='allowIp']").size()+1;
			if (param1 == ''){
				alert("IP를 입력 해 주세요");
				return false;
			}

			if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(param1)) {  
			    
			}else{
				alert("유효한 IP 주소 형식이 아닙니다.")
				return false;
			}
			    
			$("div[name='allowIp']").each(function(e){
				if($("div[name=allowIp]")[e].children[0].value == $("#inputIp").val()){
					alert("이미 등록된 IP입니다.");
					check = 1;
					return false;
				}
			})
				
			if(check != 0){
				return;
			}
			
			$.ajax({
				url:'/cstmr/myPage/registAllowIp',
				type: 'POST',
				data: { param1 : param1 },
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result == "S"){
						var addHtml = "";
						
						addHtml += "<div class='col-lg-6 col-xs-12' id='allowIp"+num+"' name='allowIp'>"
						addHtml += "<input type='text' style='width:130px;height:30px;background-color: white !important;' readonly value='"+data.allowIp+"'>";
						addHtml += '<i class="ace-icon fa fa-times" style="padding:5px;cursor: pointer;" onclick="javascript:fn_deleteIp(\'allowIp'+num+'\')"></i>'; 
						addHtml += "</div>";
						$("#ipList").append(addHtml);
						alert("지정 IP에 등록 되었습니다.");
						$("#inputIp").val("");
					}else{
						alert("등록 과정에 오류가 발생하였습니다.");
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		})
		
		$("#zipBtn").on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					var fullRoadAddr = data.roadAddress;
					var extraRoadAddr = "";

					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraRoadAddr += data.bname;
					}

					if(data.buildingName !== '' && data.apartment === 'Y'){
		                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		                }
		                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		                if(extraRoadAddr !== ''){
		                    extraRoadAddr = ' (' + extraRoadAddr + ')';
		                }
		                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
		                if(fullRoadAddr !== ''){
		                    fullRoadAddr += extraRoadAddr;
		                }
		                
		                $("#userZip").val(data.zonecode);
		                $("#userAddr").val(fullRoadAddr);
				}
			}).open();
		});

		$("#changePw").click(function() {
			$("#myModal").modal();
		});


		$("#modalSubmit").click(function() {
			var datas = {userPw: $("#checkPw").val()};

			$.ajax({
				url : '/cstmr/myPage/userPwCheck',
				type : 'GET',
				data : datas,
				success : function(result) {
					console.log(result);
					$("#myModal").modal('hide');

					if (result == 'Y') {
						$("#changePw-btn").addClass('hide');
						$("#password-layout").removeClass('hide');	
					} else {
						alert("비밀번호가 틀렸습니다.");
						return false;
					}
				},
				error : function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			})
		})
	</script>
	<!-- script addon end -->
</body>
</html>