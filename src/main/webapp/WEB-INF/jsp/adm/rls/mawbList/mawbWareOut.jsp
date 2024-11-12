<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:120px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	
	</style>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	</head> 
	<body class="no-skin" style="background-color: white;">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="detail-contents" class="detail-contents">
			<div id="home" class="tab-pane in active">
				<form name="mawbArrForm" id="mawbArrForm">
					<div class="row no-gutters">
						<div class="col-xs-12 col-sm-12">
							<div class="col-xs-12 col-sm-12 ">
								<label style="font-size:50px;">HAWB NO : </label><label class="light-red" style="font-size:45px;">${mawbArrInfo.hawbNo }</label>
								<input type="hidden" id="hawbNo" name="hawbNo" value="${mawbArrInfo.hawbNo}"/>
								<input type="hidden" id="nno" name="nno" value="${mawbArrInfo.nno}"/>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12">
							<div class="col-xs-6 col-sm-12 ">
								<h3>검수자</h3>
							</div>
							<div class="col-xs-6 col-sm-12">
								<h3>날짜 </h3>
							</div>
							<div class="col-xs-6 col-sm-12 ">
								<input type="text" id="name" name="name" value=""/>
							</div>
							<div class="col-xs-6 col-sm-12 ">
								<input type="text" id="userDate" name="userDate" value="" maxlength="16"/>
							</div>
							<div class="col-xs-12 col-sm-12 ">
								<h3>기타 메모</h3>
								<textarea cols="85" rows="5" style="resize:none;"id="remarks" name="remarks"></textarea>
							</div>
							<div class="col-xs-12 col-sm-12 ">
								<br><br>
								<label class="light-red">택배회사 변경 시, 입력</label>
							</div>
							<div class="col-xs-6 col-sm-12 ">
								<h3>택배회사</h3>
							</div>
							<div class="col-xs-6 col-sm-12">
								<h3>택배번호</h3>
							</div>
							<div class="col-xs-6 col-sm-12 ">
								<input type="text" style ="width:100%;" id="chTrkCom" name="chTrkCom" value=""/>
							</div>
							<div class="col-xs-6 col-sm-12 ">
								<input type="text" style ="width:100%;" id="chTrkNo" name="chTrkNo" value="" />
							</div>
						</div>
					</div>
				</form>
				<div class="space-20"></div>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<div class="col-lg-1 col-xs-6 col-lg-offset-5">
							<div id="button-div">
								<div id="backPage" style="text-align: center">
									<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
								</div>
							</div>
						</div>
					
						<div class="col-lg-1 col-xs-6">
							<div id="button-div">
								<div id="rgstrUser" style="text-align: center">
									<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">등록</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="space-20"></div>
			</div><!-- /#home -->
		</div>
			
		<!-- script addon start -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		<script src="/js/jquery.sound.js"></script>
		
		<script type="text/javascript">
			var count = 0;
			$("#target").focus();
			jQuery(function($) {
				$(document).load(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thTwo").toggleClass('active');
				});

				
				$("#userDate").keypress(function(key){
					var datetime_pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$/; 
										
					if(key.keyCode == "8" || key.keyCode == "46"){
						return false;
					}

					if($("#userDate").val().length == "4"){
						$("#userDate").val($("#userDate").val()+"-");
					}else if($("#userDate").val().length == "7"){
						$("#userDate").val($("#userDate").val()+"-");
					}else if($("#userDate").val().length == "10"){
						$("#userDate").val($("#userDate").val()+" ");
					}else if($("#userDate").val().length == "13"){
						$("#userDate").val($("#userDate").val()+":");
					}

					if($("#userDate").val().length == "16"){
						if(!datetime_pattern.test($("#userDate").val())){
							alert("올바른 날짜-시간 형식이 아닙니다.");
							$('#userDate').val("");
							$('#userDate').focus();
							return false;
						}
					}


					
				})
				
				$("#rgstrInfo").on('click',function(e){

					var formData = $("#mawbArrForm").serialize();
					if($("#name").val() == ""){
						alert("검수자가 비었습니다.")
						return false;
					}

					if($("#userDate").val() == ""){
						alert("날짜가 비었습니다.")
						return false;
					}

					$.ajax({
						url:'/mngr/rls/mawbWareOut',
						data: formData,
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {  
							if(confirm("등록되었습니다. 창을 닫으시겠습니까?")){
								window.close();
							}
							
			            }, 		    
			            error : function(xhr, status) {
			                alert("등록에 실패 하였습니다. 등록사항을 확인해 주세요.");
			            }
					})


				})

				$("#backToList").on('click',function(e){
					window.close();

				})

			})
		</script>
		<!-- script addon end -->
	</body>
</html>
