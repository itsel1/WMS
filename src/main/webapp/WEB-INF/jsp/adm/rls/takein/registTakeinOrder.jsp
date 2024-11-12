<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
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
	<title>사입 주문 등록</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
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
								사입 주문 등록
							</li>
							<li class="active">
								주문 정보
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="container">
						<form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="page-content">
								<div class="page-header">
									<h3>
										사입 주문 등록
									</h3>
									<br/>
									<div class="row hr-8">
										<div class="col-xs-1 center mp07 red">
											USER ID
										</div>
										<div class="col-xs-2 center">
											<input list="userList" class="form-control" id="userId" name="userId" placeholder="USER ID를 입력하세요">
											<datalist id="userList" name="userList">
												<c:forEach items="${userList}" var="userList">
													<option value="${userList.userId}">${userList.userId}</option>
												</c:forEach>
											</datalist>
										</div>
										<div class="col-xs-1 center mp07 red">
											출발지
										</div>
										<div class="col-xs-2 center" id="orgSelect">
											<input class="form-control" list="orgStationList" name="orgStation" id="orgStation" placeholder="select an option">
										</div>
										<div class="col-xs-1 center mp07 red">
											도착지
										</div>
										<div class="col-xs-2 center" id="dstnSelect">
											<input class="form-control" list="dstnNationList" name="dstnNation" id="dstnNation" placeholder="select an option">
										</div>
									</div>
									
								</div>
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<div id="inner-content-side" >
									<br>
									<div class="col-xs-12 col-lg-12 " style="font-size: 5;">
										<h2>주문정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div class="col-xs-12 col-lg-12" style="font-size: 3;">
											<h4>발송 정보</h4>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07 red">주문번호</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="orderNo" id="orderNo" value="" />
												</div>
												<div class="col-xs-1 center mp07 red">주문날짜</div>
												<div class="col-xs-3 center">
													<div>
														<div class="input-group input-group-sm">
															<span class="input-group-addon">
																<i class="ace-icon fa fa-calendar"></i>
															</span>
															<input type="text" id="orderDate" name="orderDate" class="form-control" value=""/>
														</div>
													</div>
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">이름</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="shipperName" id="shipperName" value="" />
												</div>
												<div class="col-xs-1 center mp07">전화번호</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="" />
												</div>
												<div class="col-xs-1 center mp07">휴대전화 번호</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row form-group hr-8">
												<div class="col-xs-1 center mp07">발송 도시</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="shipperCity" id="shipperCity" value="" />
												</div>
												<div class="col-xs-1 center mp07">발송 State</div>
												<div class="col-xs-3 center ">
													<input class="col-xs-12" type="text" name="shipperState" id="shipperState" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">
													<label>우편주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="shipperZip" id="shipperZip" value="" />
												</div>
												<div class="col-xs-1 center mp07">
													<label>주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="shipperAddr" id="shipperAddr" value="" />
												</div>
												<div class="col-xs-1 center mp07">
													<label>상세주소</label>
												</div>
												<div class="col-xs-3 center ">
													<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">
													<label>입고 메모</label>
												</div>
												<div class="col-xs-3">
													<input class="col-xs-12" type="text" name="whReqMsg" id="whReqMsg" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
										</div>
										<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
											<h4>수취 정보</h4>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07 red">이름</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeName" id="cneeName" value="" />
												</div>
												<div class="col-xs-1 center mp07 red">전화번호</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" placeholder="숫자만 입력 가능합니다."  numberOnly type="text" name="cneeTel" id="cneeTel" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">휴대전화 번호</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" placeholder="숫자만 입력 가능합니다."  numberOnly type="text" name="cneeHp" id="cneeHp" value="" />
												</div>
												<div class="col-xs-1 center mp07 red">E-mail</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeEmail" id="cneeEmail" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">수취 State</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeState" id="cneeState" value="" />
												</div>
												<div class="col-xs-1 center mp07" id="cneeCityDiv">수취 도시</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeCity" id="cneeCity" value="" />
												</div>
												<div class="col-xs-1 center mp07">수취 동</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeDistrict" id="cneeDistrict" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07 red">
													<label>우편주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeZip" id="cneeZip" value="" />
												</div>
												<div class="col-xs-1 center mp07 red">
													<label>주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="" />
												</div>
												<div class="col-xs-1 center mp07">
													<label>상세주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">
													<label>현지 이름</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="nativeCneeName" id="nativeCneeName" value="" />
												</div>
												<div class="col-xs-1 center mp07">
													<label>현지 주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="" />
												</div>
												<div class="col-xs-1 center mp07"">
													<label>현지 상세주소</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row hr-8">
												<div class="col-xs-1 center mp07">
													<label>수취인 요구사항</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="dlvReqMsg" id="dlvReqMsg" value="" />
												</div>
												<div class="col-xs-1 center mp07"">
													<label>구매 사이트</label>
												</div>
												<div class="col-xs-3 center">
													<input class="col-xs-12" type="text" name="buySite" id="buySite" value="" />
												</div>
											</div>
											<div class="hr dotted"></div>
										</div>
									</div>
									<br/><br/><br/>
									<div class="col-xs-6 col-lg-6">
										<div class="col-xs-12 col-lg-3 form-group" style="font-weight: 5;">
											<h2>상품정보</h2>
										</div>
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div style="text-align:right;">
												<input type="button" class="btn btn-white btn-inverse btn-xs" id="btnUserItems" value="상품리스트">
											</div>
											<br/>
											<div>
												<table id="itemInfo" class="table table-bordered">
													<thead>
														<tr>
															<th class="center colorBlack">사입코드</th>
															<th class="center colorBlack">상품코드</th>
															<th class="center colorBlack">상품명</th>
															<th class="center colorBlack">수량</th>
															<th class="center colorBlack">단가</th>
															<th class="center colorBlack"></th>
														</tr>
													</thead>
													<tbody>
														<tr id="trText">
															<td class="center colorBlack" colspan=7>상품리스트를 눌러주세요</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
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
											<div id="rgstrUser" style="text-align: center">
												<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">등록</button>
											</div>
										</div>
									</div>
								</div>
								<br /><br /><br />
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
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">

			jQuery(function($) {
				$(document).ready(function() {
					$("#5thMenu").toggleClass('open');
					$("#5thMenu").toggleClass('active');
					$("#5thSp").toggleClass('active');

					$("#userId").focus();
				})
			})
			
			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});

			$("#btnUserItems").on('click',function(e){
				var userId = "";
				userId = $("#userId").val();

				if(userId ==""){
					alert('아이디를 기입해주세요.');
					$("#userId").focus();
					return false;
				}
				window.open("/mngr/takein/popupTakeinUserItems?userId="+userId,"takeinItemWin", "width=720,height=600");
			});

			$("#userId").on('change', function(e) {
				var userId = $("#userId").val();
				console.log(userId);

				$.ajax({
					url : '/mngr/takein/nationList',
					type : 'POST',
					datatype: 'json',
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: userId},
					success : function(data) {
						var addHtml = "";
						var addHtml2 = "";
						console.log(data);
						addHtml += '<input list="userOrgStation" class="form-control" name="orgStation" id="orgStation" placeholder="출발지를 입력하세요"/>';
						addHtml += '<datalist id="userOrgStation" name="userOrgStation">';
						for (var i = 0; i < data.userOrgStation.length; i++) {
							addHtml += '<option ';
							if (data.userOrgStation[i].stationCode == $("#orgStation").val()) {
								addHtml += ' selected';
							} 
							addHtml += 'value="'+data.userOrgStation[i].stationCode+'">'+data.userOrgStation[i].stationName+'</option>';
						}
						addHtml += '</datalist>';

						addHtml2 += '<input list="nationList" class="form-control" name="dstnNation" id="dstnNation" placeholder="도착지를 입력하세요"/>';
						addHtml2 += '<datalist id="nationList" name="nationList">';
						for (var j = 0; j < data.nationList.length; j++) {
							addHtml2 += '<option ';
							if (data.nationList[j].nationCode == $("#dstnNation").val()) {
								addHtml2 += ' selected';
							} 
							addHtml2 += 'value="'+data.nationList[j].nationEName+'">'+data.nationList[j].nationName+'</option>';
						}
						addHtml2 += '</datalist>';
						
						$("#orgSelect").empty();
						$("#orgSelect").html(addHtml); 

						$("#dstnSelect").empty();
						$("#dstnSelect").html(addHtml2); 

						$("#shipperName").val(data.userInfo.comEName);
						$("#shipperTel").val(data.userInfo.userTel);
						$("#shipperHp").val(data.userInfo.userHp);
						$("#shipperEmail").val(data.userInfo.userEmail);
						$("#shipperZip").val(data.userInfo.userZip);
						$("#shipperAddr").val(data.userInfo.userEAddr);
						$("#shipperAddrDetail").val(data.userInfo.userEAddrDetail);
						
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

				
			})
			
			
			$(document).on('change', '#dstnNation', function(e) {
				if ($("#dstnNation").val() != "South Korea") {
					$("#cneeCityDiv").addClass('red');
				}
			})
			
			function fn_takeinCodeDel(){
			    $(this).closest("tr").remove(); 
			}
			
			function fn_takeinCodeIn(gCusItemCode,gUserId){

				$.ajax({
					url:'/mngr/takein/takeinCusItemInJson',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: gUserId,cusItemCode:gCusItemCode}, 
					success : function(data) {
						var Status = data.rstStatus;
						console.log(data);
						if(Status == "SUCCESS"){

							var chkCnt = 0;
							$(".cusItemCode").each(function(){
							
								if(data.rstCusItemCode == $(this).val()){
									chkCnt++;
								}
							});

							if(chkCnt!=0){
								alert('이미 등록된 상품코드 입니다.');
								$("#cusItemCodeIn").attr("value","");
								$("#cusItemCodeIn").focus();
								return false;
							}

							if(chkCnt == 0){
								$("#trText").remove();
								var innerHtml = "";
								innerHtml += '<tr>';
								innerHtml += '<th style="padding:0px; width:100px; height:40px;"><input style="width:100px; height:40px;" type="text" class="takeInCode" readonly name="takeInCode" value="'+data.rstTakeInCode+'"/></th>';
								innerHtml += '<th style="padding:0px; width:100px; height:40px;"><input style="width:100px; height:40px;" type="text" class="cusItemCode" readonly name="cusItemCode" value="'+data.rstCusItemCode+'"/></th>';
								innerHtml += '<th style="padding:0px; width:300px; height:40px;" ><input type="text" style="width:300px; height:40px;" class="itemDetail" readonly name="itemDetail" value="'+data.rstItemDetail+'"/></th>';
								innerHtml += '<th style="padding:0px; width:80px; height:40px;"><input style="width:80px; height:40px; text-align:right" type="number" class="itemCnt" name="itemCnt" value="1"/></th>';
								innerHtml += '<td style="padding:0px; width:80px; height:40px;"><input style="width:80px; height:40px; text-align:right" type="number" class="unitValue" name="unitValue" value="'+data.rstUnitValue+'"/></td>';
								innerHtml += '<td style="text-align: center;"><input type="button" style="background-color: white; border: 1px solid #ccc;" class="btnTrDel" value="삭제"></td>';
								innerHtml += '</tr>';
								$('#itemInfo > tbody:last').append(innerHtml);
							}
							
						}else{
							alert(data.rstMsg);
							if(data.rstCode == "F10"){
								$("#userId").focus();
							}else{
								$("#cusItemCodeIn").focus();
							}
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				})

			}



			$(document).on("click",".btnTrDel",function(){
				 $(this).closest("tr").remove(); 
			});

			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });

			
			$('#backToList').on('click', function(e){
				history.back();
			});

			$("#rgstrInfo").on('click', function() {

				if ($("#dstnNation").val() == "") {
					alert("도착지를 선택해주세요");
					return false;
				} 

				if ($("#orderNo").val() == "") {
					alert("주문 번호를 입력해주세요");
					return false;
				}

				if ($("#orderDate").val() == "") {
					alert("주문 날짜를 입력해주세요");
					return false;
					
				}

				if ($("#cneeName").val() == "") {
					alert("수취인 명을 입력해주세요");
					return false;
				}

				if ($("#cneeTel").val() == "") {
					alert("수취인 전화번호를 입력해주세요");
					return false;
				}

				if ($("#cneeEmail").val() == "") {
					alert("수취인 Email을 입력해주세요");
					return false;
				}

				if ($('#dstnNation').val() != "South Korea") {
					if ($("#cneeCity").val() == "") {
						alert("수취인 도시를 입력해주세요");
						return false;
					}
				}

				if ($("#cneeZip").val() == "") {
					alert("수취인 우편번호를 입력해주세요");
					return false;
				}

				if ($("#cneeAddr").val() == "") {
					alert("수취인 주소를 입력해주세요");
					return false;
				}

				if ($(".cusItemCode").length < 1) {
					alert("최소 1개의 상품정보를 입력해야 합니다");
					return false;
				}

				if ($(".itemCnt").val() == "0") {
					alert("상품 수량을 입력해주세요");
					return false;
				}

				var formData = $("#registForm").serialize();

				$.ajax({
					url : '/mngr/takein/registTakeinOrderInfo',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						
						if (data == "S") {
							alert("등록 되었습니다.");
							location.href="/mngr/takein/takeinOrderListTmp";
						} else {
							alert("등록 중 오류가 발생하였습니다.");
						}
		            }, 		    
		            error : function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("등록에 실패하였습니다.");
					}
				})
				
				
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
