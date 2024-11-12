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
	  
	  
		table {
			width: 100%;
			border: solid 1px #ddd;
			border-collapse: collapse;
			box-shadow: 1px 1px 1px #ddd;
			font-size: 14px;
			box-shadow: 1px black;
			background: #fff;
		}
		
		table th {
			font-family: 'Open Sans';
			text-align: center;
			padding: 5px 0;
			font-size: 14px;
			color: #000000 !important;
			font-weight: bold;
			background: linear-gradient(to top, #ebeff5, white);
			border: solid 1px #ccc;
		}
		
		input[readonly="readonly"] {
			background-color: "#ebeff5";
		}


	 </style>
	<!-- basic scripts -->
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head> 
	<title>사입 주문 등록</title>
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
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>사입 주문 등록</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">수기 등록</strong></div>
							</div>
						</div>
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
											출발지
										</div>
										<div class="col-xs-2 center">
											<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" id="orgStation" name="orgStation">
												<c:forEach items="${userOrgStation}" var="orgStationList">
													<option value="${orgStationList.stationCode}">${orgStationList.stationName}</option>
												</c:forEach>
											</select>
										</div>
									
										<div class="col-xs-1 center mp07 red">
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
										<!-- <div class="col-xs-1 center mp07">
											택배 회사
										</div>
										<div class="col-xs-2 center">
											<select class="form-control" id="transCode" name="transCode">
												<option value=""></option>												
											</select>
										</div>  -->
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" id="cnt" name="cnt" value=""/>
								<input type="hidden" id="userId" name="userId" value="${userId}"/>
								<div id="inner-content-side" >
								</div>
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
														<input type="text" id="orderDate" name="orderDate" class="form-control" value="${userOrder.orderDate}"/>
													</div>
												</div>
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-1 center mp07">이름</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="shipperName" id="shipperName" value="${userInfo.comEName}" />
											</div>
											<div class="col-xs-1 center mp07">전화번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userInfo.userTel}" />
											</div>
											<div class="col-xs-1 center mp07">휴대전화 번호</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="${userInfo.userHp}" />
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
												<input class="col-xs-12" type="text" name="shipperZip" id="shipperZip" value="${userInfo.userZip}" />
											</div>
											<div class="col-xs-1 center mp07">
												<label>주소</label>
											</div>
											<div class="col-xs-3 center">
												<input class="col-xs-12" type="text" name="shipperAddr" id="shipperAddr" value="${userInfo.userEAddr}" />
											</div>
											<div class="col-xs-1 center mp07">
												<label>상세주소</label>
											</div>
											<div class="col-xs-3 center ">
												<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userInfo.userEAddrDetail}" />
											</div>
										</div>
										<div class="hr dotted"></div>
										<div class="row hr-8">
											<div class="col-xs-2 center mp07">
												<label>입고 메모</label>
											</div>
											<div class="col-xs-6">
												<input class="col-xs-12" type="text" name="whReqMsg" id="whReqMsg" value="${userOrder.whReqMsg}"  />
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
											<div class="col-xs-2 center mp07">
												<label>수취인 요구사항</label>
											</div>
											<div class="col-xs-6 center">
												<input  class="col-xs-12" type="text" name="dlvReqMsg" id="dlvReqMsg" value="" />
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
								<div class="col-xs-12 col-lg-12" >
									<div class="col-xs-12 col-lg-3 form-group" style="font-size: 5;">
										<h2>상품정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div style="text-align: right">
											<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
												삭제
											</button> -->
											<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
												상품 추가
											</button>
										</div>
										<br/>
										<div>
											<table id="itemInfo" class="table table-bordered">
												<thead>
													<tr>
														<th class="center colorBlack">상품코드</th>
														<th class="center colorBlack">상품명</th>
														<th class="center colorBlack">수량</th>
														<th class="center colorBlack">단가</th>
														<th class="center colorBlack">브랜드</th>
														<th class="center colorBlack">상품 이미지 URL</th>
														<th class="center colorBlack"></th>
													</tr>
												</thead>
												<tbody>
													<tr id="trText">
														<td class="center colorBlack" colspan=7>상품 추가를 눌러주세요</td>
													</tr>
												</tbody>
											</table>
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

			$("#addBtns").on('click',function(e){
				var userId = "";
				userId = $("#userId").val();

				window.open("/cstmr/takein/popupTakeinUserItems?userId="+userId,"takeinItemWin", "width=720,height=600");
			});

			function fn_takeinCodeIn(gCusItemCode, gUserId) {
				$.ajax({
					url:'/cstmr/takein/takeinCusItemInJson',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: {userId: gUserId,cusItemCode:gCusItemCode}, 
					success : function(data) {
						var Status = data.rstStatus;
						if(Status == "SUCCESS"){
						
							var chkCnt = 0;
							$(".cusItemCode").each(function(){
							
								if(data.cusItemCode == $(this).val()){
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
								innerHtml += '<td style="padding:0px; width:150px; height:40px;"><input type="text" style="width:150px; height:40px; text-align: center;" class="cusItemCode" readonly="readonly" name="cusItemCode" value="'+data.cusItemCode+'"/></th>';
								innerHtml += '<td style="padding:0px; width:400px; height:40px;"><input type="text" style="width:400px; height:40px;" class="itemDetail" readonly="readonly" name="itemDetail" value="'+data.itemDetail+'"/></th>';
								innerHtml += '<td style="padding:0px; width:100px; height:40px;"><input type="number" style="width:100px; height:40px; text-align:right;" class="itemCnt" name="itemCnt" value="0"/></th>';
								innerHtml += '<td style="padding:0px; width:100px; height:40px;"><input type="number" style="width:100px; height:40px; text-align:right;" class="unitValue" name="unitValue" value="'+data.unitValue+'"/></td>';
								innerHtml += '<td style="padding:0px; width:120px; height:40px;" ><input type="text" style="width:120px; height:40px; text-align: center;" class="brand" name="brand" value="'+data.brand+'"/></th>';
								innerHtml += '<td style="padding:0px; width:600px; height:40px;"><input type="text" style="width:600px; height:40px;" class="itemImgUrl" name="itemImgUrl" value="'+data.itemImgUrl+'"/></td>';
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

			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });

			
			$('#backToList').on('click', function(e){
				history.back();
			});
			

			$(document).on("click",".btnTrDel",function(){
				 $(this).closest("tr").remove(); 
			});


			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
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
			
			
			$("#dstnNation").on('change', function() {
				if ($('#dstnNation').val() != "South Korea") {
					$("#cneeCityDiv").addClass('red');
				}
			})


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
				console.log('TEST');
				console.log(formData);
				
				$.ajax({
					url : '/cstmr/takein/registTakeinOrderInfo',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						
						if (data == "S") {
							alert("등록 되었습니다.");
							location.href="/cstmr/takein/preorder";
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
