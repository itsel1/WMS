<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
	  
	  .colorBlack {color:#000000 !important;}
	  
	  .infoName{background: yellow;}
	  .profile-info-name, .profile-info-value {
	  		border-top: none;
	  		border-bottom: 1px dotted #D5E4F1;
  		}
  		.mp07{
  			margin:0px;
  			padding:7px;
  		}  	
  		.orderNm {
	  	color: #DD5A43;
	  }
	  
	 </style>
	<!-- basic scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head> 
	<title>배송대행 신청서</title>
	<body>
		<!-- headMenu Start -->
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
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">배송대행 단일 신청</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<form id="registForm" name="registForm">
							<div class="page-content">
								<div class="page-header">
									<h3>
										주문 등록
									</h3>
									<br>
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px">
													<option>USER ID</option>
												</select>
												<input type="text" class="onlyEN" name="userId" id="userId" style="width:100%; max-width: 300px"/>

											</span>
										</div>
									</div>
									<div class="row hr-8" id="options">
										<br/>
										<div class="col-xs-1 center mp07">
											출발지
										</div>
										<div class="col-xs-2 center">
											<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" id="orgStation" name="orgStation">
												<c:forEach items="${userOrgStation}" var="orgStationList">
													<option value="${orgStationList.stationCode}">${orgStationList.stationName}</option>
												</c:forEach>
											</select>
										</div>
									
										<div class="col-xs-1 center mp07">
											도착지
										</div>
										<div class="col-xs-2 center">
											<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" id="dstnNation" name="dstnNation">
												<option value="">  </option>
												<c:forEach items="${nationList}" var="dstnNationList">
													<option value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-xs-1 center mp07">
											택배 회사
										</div>
										<div class="col-xs-2 center">
											<select class="form-control" id="transCode" name="transCode">
												<option value=""></option>												
											</select>
										</div>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<div id="inner-content-side" >
								</div>
							</div>
						</form>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
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
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			var cnt = 1;
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
				});
				//select target form change
			})
			
			function fn_userIdChk(){
				$.ajax({
					url:'/mngr/takein/userIdChk',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: $("#userId").val()}, 
					success : function(data) {
						var Status = data.rstStatus;						
						if(Status == "SUCCESS"){
							$("#userId").attr("readOnly","true");
						}else{
							alert('등록되지 않은 아이디 입니다.');
							$("#userId").focus();
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})
			}
			
			$('#userId').focusout(function() {
				if($('#userId').val() !=""){
					fn_userIdChk();
				}
			});

			$("#userId").keydown(function(key) {
				if (key.keyCode == 13) {
					fn_userIdChk();
				}
			});


			

			$('#backToList').on('click', function(e){
				history.back();
			});

			$(".nations").on('change',function(e){

				var param1 = $("#orgStation").val();
				var param2 = $("#dstnNation").val();

				$.ajax({
					url:'/cstmr/apply/transComList',
					type: 'POST',
					datatype: "json",
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: { param1 : param1,
							param2 : param2
						},
					success : function(data) {
						addHtml = "";
						if(data.transComList.length == 0){
							addHtml = "<option value=''>택배회사가 없습니다.</option>";
						}else{
							addHtml = "<option value=''>택배회사를 선택해주세요</option>";
							for(var i = 0; i < data.transComList.length; i++){
								addHtml += "<option value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transName+"</option>'"
							}
						}
						$("#transCode").html(addHtml)
						$("#testTransCode").val(data.transComList[0].transCode);
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
				

			})

			$("#transCode").on("change", function(){
				if($("#transCode").val() == ""){
					alert("택배회사를 먼저 선택해 주세요");
					return false;
				}else{
					var formData = $("#registForm").serialize();
					$.ajax({
						url:'/cstmr/apply/shpng/registOne',
						type: 'GET',
						beforeSend : function(xhr)
						{  
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							$("#inner-content-side").html(data)
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				}
				
			}) 
			
			if(!ace.vars['touch']) {
				$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
			

		</script>
		<!-- script addon end -->
	</body>
</html>
