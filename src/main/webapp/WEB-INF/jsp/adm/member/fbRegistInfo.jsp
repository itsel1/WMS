<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	 	.colorBlack {color:#000000 !important;}
	 	@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');
		
		* {
			font-family: 'Gowun Dodum', sans-serif;
		}
		
		input[type=text]{
			border-radius: 0.5rem !important;
			padding: 1rem 1rem;
		}
		
		.bg-color {
			background:#EB0000;
			font-size:12px;
			color:#fff;
			font-weight:500;
		}
		
		.modal.modal-center {
		  text-align: center;
		}
		
		#addCost:focus {
			border: 1px solid #3296FF;
		}
		
		#etcDetail:focus {
			border: 1px solid #3296FF;
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
		 
		 .modal-footer {
		 	background:white;
		 }
	</style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>회원 관리</title>
	<body class="no-skin">
<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								회원 관리
							</li>
							<li class="active">FB 판매사 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								FB 판매사 등록
							</h1>
						</div>
						<br/>
						<div id="inner-content-side" >
							<form id="userForm" name="userForm">
								<div class="container-fluid">
									<div class="row">
										<div class="col-md-6" style="border:1px solid #ccc;padding:3rem!important;">
											<div class="row">
												<div class="col-md-4">
													<div class="form-group">
														<label for="userId"><font class="red">&#42;</font> USER ID</label>
														<input type="text" name="userId" id="userId" placeholder="아이디 찾기" readonly class="form-control">
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label for="sellerName"><font class="red">&#42;</font> 판매사명</label>
														<input type="text" name="sellerName" id="sellerName" readonly class="form-control">
														<p class="help-block text-right green">&#10003; 판매사명은 수정이 불가능 합니다</p>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label for="shipperName">발송인명</label>
														<input type="text" name="shipperName" id="shipperName" class="form-control">
														<p class="help-block text-right green">&#10003; 현지 송장 상 발송인 명</p>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label for="userId"><font class="red">&#42;</font> 상점 브랜드명</label>
														<input type="text" name="brandName" id="brandName" class="form-control">
														<p class="help-block text-right green">&#10003; 영문, 숫자만 입력 가능합니다 (공백 허용 X)</p>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="attnName"><font class="red">&#42;</font> 상점 대표자명</label>
														<input type="text" name="attnName" id="attnName" class="form-control">
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label for="comName"><font class="red">&#42;</font> 대표 상호</label>
														<input type="text" name="comName" id="comName" class="form-control">
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="comRegNo"><font class="red">&#42;</font> 사업자등록번호</label>
														<input type="text" name="comRegNo" id="comRegNo" class="form-control required">
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label for="sellerAddr"><font class="red">&#42;</font> 상점 주소 (영문)</label>
														<input type="text" name="sellerAddr" id="sellerAddr" class="form-control">
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="sellerAddrDetail">상점 상세주소 (영문)</label>
														<input type="text" name="sellerAddrDetail" id="sellerAddrDetail" class="form-control">
													</div>
												</div>
											</div>
											<br/>
											<div class="row">
												<div class="col-md-4">
													<div class="form-group">
														<label for="fbUseYn">패스트박스 사용 여부</label>
														<select id="fbUseYn" name="fbUseYn" class="form-control">
															<option value="Y">사용</option>
															<option value="N">미사용</option>
														</select>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label for="expUseYn">패스트박스 수출신고 대행</label>
														<select id="expUseYn" name="expUseYn" class="form-control">
															<option value="F">미사용</option>
															<option value="P">사용</option>
														</select>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label for="expUnitValue">패스트박스 수출신고 기준 금액</label>
														<select id="expUnitValue" name="expUnitValue" class="form-control">
															<option value="S">판매가</option>
															<option value="D">판매가 + 할인가</option>
														</select>
													</div>
												</div>
											</div>
											<br/>
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label for="customsNo"><font class="red hide" id="customsNoText">&#42;</font> 사업자통관고유부호</label>
														<input type="text" name="customsNo" id="customsNo" class="form-control">
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="shipperZip"><font class="red hide" id="shipperZipText">&#42;</font> 수출신고 화주 우편번호</label>
														<input type="text" name="shipperZip" id="shipperZip" class="form-control">
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label for="shipperAddr"><font class="red hide" id="shipperAddrText">&#42;</font> 수출신고 화주 주소 (한글)</label>
														<input type="text" name="shipperAddr" id="shipperAddr" class="form-control">
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="shipperAddrDetail">수출신고 화주 상세주소 (한글)</label>
														<input type="text" name="shipperAddrDetail" id="shipperAddrDetail" class="form-control">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group text-center">
											<input type="button" class="btn btn-sm btn-warning" id="cancelBtn" value="취소">
											<input type="button" class="btn btn-sm btn-primary" id="registBtn" value="등록">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		<div class="modal modal-center fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-center" id="addModal">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">아이디 찾기</h4>
					</div>
					<div class="modal-body" style="padding:3rem!important;">
						<select class="chosen-select-start form-control" id="selectUserId" name="selectUserId" data-placeholder="국가를 선택해 주세요">
							<c:forEach items="${userList}" var="userList">
								<option value="${userList.userId}">${userList.userId} | ${userList.comName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" id="saveBtn">저장</button>
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">취소</button>
					</div>
				</div>
			</div>
		</div>
		
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<!--[if !IE]> -->
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
		
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<!-- script on paging start -->
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#zeroMenu").toggleClass('open');
					$("#zeroMenu").toggleClass('active');
					$("#14thTwo").toggleClass('active');
				})

				$("#userId").click(function() {
					$("#myModal").modal();
				})

				if(!ace.vars['touch']) {
					$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true }); 
					//resize the chosen on window resize
			
					$(window)
					.off('resize.chosen')
					.on('resize.chosen', function() {
						$('.chosen-select-start').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': '100%'});
						})
					}).trigger('resize.chosen');
					//resize chosen on sidebar collapse/expand
					$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
						if(event_name != 'sidebar_collapsed') return;
						$('.chosen-select-start').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': '100%'});
						})
					});
				}

				$("#saveBtn").click(function() {
					var userId = $("#selectUserId").val();

					$.ajax({
						url : '/mngr/acnt/getSelectUserInfo',
						type : 'POST',
						data : {userId: userId},
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							var userInfo = data.userInfo;
							$("#userId").val(userInfo.userId);
							$("#sellerName").val(userInfo.userId);
							$("#brandName").val(userInfo.sellerName);
							$("#shipperName").val(userInfo.sellerName);
							$("#attnName").val(userInfo.attnName);
							$("#comName").val(userInfo.comName);
							$("#sellerAddr").val(userInfo.sellerAddr);
							$("#sellerAddrDetail").val(userInfo.sellerAddrDetail);
							$("#shipperZip").val(userInfo.shipperZip);
							$("#shipperAddr").val(userInfo.shipperAddr);
							$("#shipperAddrDetail").val(userInfo.shipperAddrDetail);

							$("#myModal").modal('hide');
						},
						error : function(xhr, status) {
							alert("데이터 가져오기에 실패 하였습니다.");							
						}
					})
				})
				
				$("#expUseYn").change(function() {
					if($("#expUseYn").val() == "P") {
						$("#customsNoText").removeClass('hide');
						$("#shipperZipText").removeClass('hide');
						$("#shipperAddrText").removeClass('hide');
					} else {
						$("#customsNoText").addClass('hide');
						$("#shipperZipText").addClass('hide');
						$("#shipperAddrText").addClass('hide');
					}
				})

				$("#cancelBtn").click(function() {
					history.back();
				})
				
				$("#registBtn").click(function() {
					var regex = /^[A-Za-z0-9+]*$/;
						
					if($("#userId").val() == "") {
						alert("아이디는 필수 정보입니다.");
						return false;
					}

					if($("#sellerName").val() == "") {
						alert("판매사명은 필수 정보입니다.");
						$("#sellerName").focus();
						return false;
					}

					if($("#brandName").val() == "") {
						alert("상점 브랜드명은 필수 정보입니다.");
						$("#brandName").focus();
						return false;
					}

					if(!regex.test($("#brandName").val())) {
						alert("브랜드명은 영문, 숫자만 입력 가능합니다.");
						$("#brandName").focus();
						return false;
					}

					if($("#attnName").val() == "") {
						alert("상점 대표자명은 필수 정보입니다.");
						$("#attnName").focus();
						return false;
					}

					if($("#comName").val() == "") {
						alert("대표 상호는 필수 정보입니다.");
						$("#comName").focus();
						return false;
					}

					if($("#comRegNo").val() == "") {
						alert("사업자 등록번호는 필수 정보입니다.");
						$("#comRegNo").focus();
						return false;
					}

					if($("#sellerAddr").val() == "") {
						alert("상점 주소는 필수 정보입니다.");
						$("#sellerAddr").focus();
						return false;
					}

					if($("#expUseYn").val() == "P") {
						if($("#customsNo").val() == "") {
							alert("사업자 통관고유부호는 필수 정보입니다.");
							$("#customsNo").focus();
							return false;
						}

						if($("#shipperZip").val() == "") {
							alert("화주 우편번호는 필수 정보입니다.");
							$("#shipperZip").focus();
							return false;
						}

						if($("#shipperAddr").val() == "") {
							alert("화주 주소는 필수 정보입니다.");
							$("#shipperAddr").focus();
							return false;
						}
					}

					var formData = $("#userForm").serialize();
					
					$.ajax({
						url : '/mngr/acnt/registFastboxUserInfo',
						type : 'POST',
						data : formData,
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							alert("등록 되었습니다.");
							location.href = "/mngr/acnt/fastboxUserList";
						},
						error : function(xhr, status) {
							alert("등록 중 오류가 발생 하였습니다.");
						}
					})
				})

			})

			
		</script>
		<!-- script addon end -->
</body>
</html>