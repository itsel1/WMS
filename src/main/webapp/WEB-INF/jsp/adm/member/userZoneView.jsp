<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}

.inner-content-side>div {
	text-align: center;
}

input[type=text]{
	border-radius: 0.5rem !important;
}

.vertiMidle{
	vertical-align: middle;
}

.pd-1rem{
	padding: 1rem 1rem;
	font-size: 14px; 
	font-weight: 400;
}
</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>비용 관리</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<!-- SideMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<!-- SideMenu End -->

		<div class="main-content">

			<form name="userInfoForm" id="userInfoForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">

					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
							<li>비용 관리</li>
							<li id="targets" name="targets">
								<c:choose>
									<c:when test="${targetPage eq 'ent'}">
										기업 회원	
									</c:when>
									<c:otherwise>
										개인 회원
									</c:otherwise>
							</c:choose>
							</li>
							<li class="active">비용 수정</li>
						</ul>
						<!-- /.breadcrumb -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>${userId} 비용 정보 수정</h1>
							<input type="hidden" id="userId" name="userId" value="${userId}"/>
						</div>
						<div id="inner-content-side">
							<!--  -->
							<div class="row">
								<div class="col-xs-12 col-lg-10" >
									<!-- 기본정보 Start -->
									<div class="tabbable">
										<ul class="nav nav-tabs" id="myTab">
											<li class="active">
												<a data-toggle="tab" href="#tab1">
													Zone 정보 등록
												</a>
											</li>
											<li >
												<a data-toggle="tab" href="#tab2">
													검품 비용 등록
												</a>
											</li>
											<li >
												<a data-toggle="tab" href="#tab3">
													기타 청구 비용 등록
												</a>
											</li>
										</ul>
										
										<div class="tab-content">
											<div id="tab1" class="tab-pane fade in active">
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-4 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">기본 등록 정보</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-4 col-xs-12">
														수금 지역
													</div>
													<div class="col-lg-3 col-xs-12">
														화폐 단위
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-4 col-xs-12">
														<div class="col-lg-6 col-xs-12">
															<select style="width:100%;" id="invoiceStation" name="invoiceStation">
																<c:forEach items="${stationInfo}" var = "station" varStatus = "stationStatus">
																	<option value="${station.stationCode}" <c:if test="${customerInfo.invoiceNation eq station.stationCode}"> selected</c:if>>${station.stationName}</option>
																</c:forEach>
															</select>
														</div>							
													</div>						
													<div class="col-lg-4 col-xs-12">
														<div class="col-lg-4 col-xs-12">
															<select style="width:100%;" id="currency" name="currency">
																<option <c:if test="${customerInfo.currency eq 'KRW'}"> selected</c:if> value="KRW">KRW</option>
																<option <c:if test="${customerInfo.currency eq 'USD'}"> selected</c:if> value="USD">USD</option>
																<option <c:if test="${customerInfo.currency eq 'JPY'}"> selected</c:if> value="JPY">JPY</option>
																<option <c:if test="${customerInfo.currency eq 'GBP'}"> selected</c:if> value="GBP">GBP</option>
															</select>
														</div>
														<div class="col-lg-1 col-xs-12">
															<input type="button" id="currencyRegist" name="currencyRegist" value="적용하기">
														</div>
													</div>
													
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-2 col-xs-12">
													</div>
												</div>
												
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">[입력 방법 1] 요율표 및 할인율 일괄 등록</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-2 col-xs-12">
														요율표 선택
														<select id="dlvCode" style="width:100%;">
															<c:forEach items="${invoiceList}" var = "invoice" varStatus = "invoiceStatus">
																<option value="${invoice.dlvCode }">${invoice.dlvPriceName }</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-lg-2 col-xs-12">
														기본 할인율 <input type="text" id="menualPercent" name="menualPercent" value="0"/> <label style="font-size: large;font-weight:  bold;">%</label>
													</div>
													<div class="col-lg-2 col-xs-12">
														부피무게 할인율 <input type="text" id="menualVolPercent" name="menualVolPercent" value="0"/> <label style="font-size: large;font-weight:  bold;">%</label>
													</div>
													<div class="col-lg-1 col-xs-12">
														<br>
														<input type="button" id="menualRegist" name="menualRegist" value="적용하기">
													</div>
												</div>
												
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">[입력 방법 2] 요율표 개별 등록</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-2 col-xs-12">
														<button type="button" name="registTarget" id="registTarget" class="btn btn-sm btn-primary">
														 	개별 요율표 Excel 업로드
														</button>
														<div class="hidden">
															<input type="file" id="excelFile" name="excelFile"
															style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
															accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
														</div>
													</div>
												</div>
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-4 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">
															할인율 개별 등록 <span style="font-size: 10px; cursor: pointer; color:blue;" id="allReset" name="allReset">[전체 초기화]</span>
														</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8">
													<div class="col-lg-2 col-xs-12">
														특별 할인 대상 국가 선택 
														<select id="dstnNation" name="dstnNation" style="width:100%;">
															<c:forEach items="${zoneMap}" var = "dstnList" varStatus="dstnStatus">
																<c:forEach items="${dstnList.value}" var = "dstn" varStatus="dstnStatus">
																	<option value="${dstn}">${dstnList.key} - ${dstn}</option>
																</c:forEach>	
															</c:forEach>
														</select>
													</div>
													<div class="col-lg-2 col-xs-12">
														기본 할인율 <input type="text" id="individualPercent" name="individualPercent" value="0"/> <label style="font-size: large;font-weight:  bold;">%</label>
													</div>
													<div class="col-lg-2 col-xs-12">
														부피무게 할인율 <input type="text" id="individualVolPercent" name="individualVolPercent" value="0"/> <label style="font-size: large;font-weight:  bold;">%</label>
													</div>
													<div class="col-lg-1 col-xs-12">
														<br>
														<input type="button" id="individualRegist" name="individualRegist" value="적용하기">
													</div>
													
												</div>
												<%-- <div class="row form-group hr-8">
													<div class="col-lg-2 col-xs-12">
														<input type="button" id="IndividualRegist" name="IndividualRegist" value="할인율 적용 내역" onclick="javascript:fn_individualRegist('${zoneMaps.key}')">
													</div>
												</div> --%>
												
												
											</div>
											<div id="tab2" class="tab-pane fade in">
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-4 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">기본 검품 비용</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-4">
														건당 검품 비용 &nbsp;&nbsp; <input type="text" id="inspPrice" name="inspPrice" value="0"/>
													</div>
												</div>
											</div>
											<div id="tab3" class="tab-pane fade in">
												<div class="row form-group hr-8" >
													<div class="col-xs-12 col-lg-4 form-group" style="font-size: 5;margin-bottom: 0px;">
														<h2 style="margin-bottom: 0px;">기타 청구 비용</h2>
													</div>
												</div>
												<div class="hr dotted"></div>
												<div class="row form-group hr-8" >
													<div class="col-lg-4 col-xs-12">
														적용 지역
													</div>
												</div>
												<div class="row form-group hr-8">
													<div class="col-lg-4 col-xs-12">
														<select id="etcArea" name="etcArea" style="width:100%;">
															<c:forEach items="${zoneMap}" var = "dstnList" varStatus="dstnStatus">
																<c:forEach items="${dstnList.value}" var = "dstn" varStatus="dstnStatus">
																	<option value="${dstn}">${dstnList.key} - ${dstn}</option>
																</c:forEach>	
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row form-group hr-8" >
													<div class="col-lg-3 col-xs-12">
														건당 수수료
													</div>
													<div class="col-lg-3 col-xs-12">
														통관 수수료
													</div>
												</div>
												<div class="row form-group hr-8"> 
													<div class="col-lg-3 col-xs-12">
														<input type="text" id="caseFee" name="caseFee" value="0"/>
													</div>
													<div class="col-lg-3 col-xs-12">
														<input type="text" id="clearanceFee" name="clearanceFee" value="0"/>
													</div>
												</div>
												<div class="row form-group hr-8" >
													<div class="col-lg-3 col-xs-12">
														유류 적용 방식 
													</div>
													<div class="col-lg-3 col-xs-12 hide" id="surType1">
														WT 단위
													</div>
													<div class="col-lg-3 col-xs-12">
														유류 할증료
													</div>
												</div>
												<div class="row form-group hr-8"> 
													<div class="col-lg-3 col-xs-12">
														<input type="radio" id="surchargeType" name="surchargeType" value="WT"> WT&nbsp;&nbsp;
														<input type="radio" id="surchargeType" name="surchargeType" value="per"> PER
													</div>
													<div class="col-lg-3 col-xs-12 hide" id="surWtUnit1">
														<input type="text" id="fuelWtUnit" name="fuelWtUnit" value="0.5">
													</div>
													<div class="col-lg-3 col-xs-12">
														<input type="text" id="fuelSurcharge" name="fuelSurcharge" value="0"/>
													</div>
												</div>
												<div class="row form-group hr-8" >
													<div class="col-lg-3 col-xs-12">
														Surcharge 적용 방식
													</div>
													<div class="col-lg-3 col-xs-12 hide" id="surType2">
														WT 단위
													</div>
													<div class="col-lg-3 col-xs-12">
														Surcharge
													</div>
												</div>
												<div class="row form-group hr-8"> 
													<div class="col-lg-3 col-xs-12">
														<input type="radio" name="surchargeType2" value="WT"> WT&nbsp;&nbsp;  
														<input type="radio" name="surchargeType2" value="per"> PER
													</div>
													<div class="col-lg-3 col-xs-12 hide" id="surWtUnit2">
														<input type="text" id="surWtUnit" name="surWtUnit" value="0.5">
													</div>
													<div class="col-lg-3 col-xs-12">
														<input type="text" id="surcharge" name="surcharge" value="0"/>
													</div>
												</div>
												<div class="row form-group hr-8" >
													<div class="col-lg-3 col-xs-12">
														수출 면장 비용
													</div>
													<div class="col-lg-3 col-xs-12">
														
													</div>
												</div>
												<div class="row form-group hr-8"> 
													<div class="col-lg-3 col-xs-12">
														<input type="text" id="exportDeclFee" name="exportDeclFee" value="0"/>
													</div>
													<div class="col-lg-3 col-xs-12">
														<input type="button" id="etcFeeRegist" name="etcFeeRegist" value="적용하기">
													</div>
												</div>
												
											</div>
											<div class="row form-group hr-8" >
												<div class="col-xs-12 col-lg-4 form-group" style="font-size: 5;margin-bottom: 0px;">
													<h2 style="margin-bottom: 0px;">등록 내역</h2>
												</div>
											</div>
											<div class="hr dotted"></div>
											<div class="row form-group hr-8">
												<div id="table-contents" class="table-contents col-lg-12 col-xs-12" id="registList" name="registList">
													
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10 col-xs-12">
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
					</div>
				</div>
			</form>
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->

	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	

	<!-- script on paging start -->

	<script src="/assets/js/jquery.dataTables.min.js"></script>
	<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
	<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="/assets/js/jquery-ui.min.js"></script>
	<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
	<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
	<script src="/assets/js/dataTables.buttons.min.js"></script>
	<script src="/assets/js/buttons.flash.min.js"></script>
	<script src="/assets/js/buttons.html5.min.js"></script>
	<script src="/assets/js/buttons.print.min.js"></script>
	<script src="/assets/js/buttons.colVis.min.js"></script>
	<script src="/assets/js/dataTables.select.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$("#zeroMenu").toggleClass('open');
				$("#zeroMenu").toggleClass('active');
				
				if($("#targets").text().trim() == "기업 회원"){
					$("#zeroOne").toggleClass('active');
				}else{
					$("#zeroTwo").toggleClass('active');
				}
				fn_RegistList_Load();
			});
			
			var currentTarget = "#tab1";
			$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				//if($(e.target).attr('href') == "#home") doSomethingNow();
			})

			//initiate dataTables plugin
			
			$('#backToList').on('click', function(e) {
				history.back();

				/* if($("#targets").text().trim() == "기업 회원"){
					location.href = '/mngr/acnt/entrpList';
				}else{
					location.href = '/mngr/acnt/indvdList';
				} */
			});
			


			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': '100%'});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': '100%'});
					})
				});
			}
			
		}) 
		
		function fn_RegistList_Load(){
			$.ajax({
				url:'/mngr/acnt/zone/registList/'+$("#userId").val(),
				data:{
					
				},
				type: 'GET',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					$('#table-contents').html(data);
	            }, 		    
	            error : function(xhr, status) {
		            
	            }
			})
		}
		
		$("#menualRegist").on("click",function(e){
			
			$.ajax({
				url:'/mngr/acnt/zone/menual/'+$("#userId").val(),
				data:{
						menualPercent: $("#menualPercent").val(),
						menualVolPercent : $("#menualVolPercent").val(),
						dlvCode : $("#dlvCode").val()
				  	},
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})
		});
			
		$("#individualRegist").on("click",function(e){
			
			$.ajax({
				url:'/mngr/acnt/zone/individual/'+$("#userId").val(),
				data:{
					menualPercent: $("#individualPercent").val(),
					menualVolPercent : $("#individualVolPercent").val(),
					dstnNation : $("#dstnNation").val()
				},
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})
		});
		
		function fn_menualDown(transCode,dstnNation){
			alert("excel로 다운로드");
		}

		$("#registTarget").on("click",function(e){
			$("#excelFile").trigger("click");

		});

		$("#excelFile").on('change',function(e){
			var datass = new FormData();
			datass.append("file", $("#excelFile")[0].files[0]);
			LoadingWithMask();
			
			$.ajax({
				url:'/mngr/rls/zoneExcelUploadIndividual/'+$("#userId").val(),
				type: 'POST',
				data : datass,
				processData: false,
	            contentType: false,
				beforeSend : function(xhr)
				{ 
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다.");
	            },
	            complete : function() {
	            	$('#mask').hide();
	            	$("#excelFile").val("");
	            }
			})
		})
		
		$("#allReset").on('click', function(e){
			
			$.ajax({
				url:'/mngr/acnt/zone/resetList/'+$("#userId").val(),
				data:{
					
				},
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})

		})
		
		$("#currencyRegist").on('click', function(e){
			$.ajax({
				url:'/mngr/acnt/zone/currency/'+$("#userId").val(),
				data:{
					currencyType : $("#currency").val(),
					invoiceStation : $("#invoiceStation").val()
				},
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})
			
		});

		$("#etcFeeRegist").on('click', function(e){
			
			$.ajax({
				url:'/mngr/acnt/zone/etcFee/'+$("#userId").val(),
				data:{
					surchargeType : $('input[name="surchargeType"]:checked').val(),
					etcArea : $("#etcArea").val(),
					caseFee : $("#caseFee").val(),
					clearanceFee : $("#clearanceFee").val(),
					fuelSurcharge : $("#fuelSurcharge").val(),
					exportDeclFee : $("#exportDeclFee").val(),
					surchargeType2 : $('input[name="surchargeType2"]:checked').val(),
					surcharge : $("#surcharge").val(),
					fuelWtUnit : $("#fuelWtUnit").val(),
					surWtUnit : $("#surWtUnit").val()
				},
				type: 'POST',
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})

			
		});

		$("input[name='surchargeType']").change(function(e) {
			var surchargeType = $("input[name='surchargeType']:checked").val();
			if (surchargeType == 'WT') {
				$("#surType1").removeClass('hide');
				$("#surWtUnit1").removeClass('hide');
			} else {
				$("#surType1").addClass('hide');
				$("#surWtUnit1").addClass('hide');
			}
		});

		$("input[name='surchargeType2']").change(function(e) {
			var surchargeType = $("input[name='surchargeType2']:checked").val();
			if (surchargeType == 'WT') {
				$("#surType2").removeClass('hide');
				$("#surWtUnit2").removeClass('hide');
			} else {
				$("#surType2").addClass('hide');
				$("#surWtUnit2").addClass('hide');
			}
		});
		
		function LoadingWithMask() {
		    //화면의 높이와 너비를 구합니다.
		    var maskHeight = $(document).height();
		    var maskWidth  = window.document.body.clientWidth;
		     
		    //화면에 출력할 마스크를 설정해줍니다.
		    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
		  
		    //화면에 레이어 추가
		    $('body')
		        .append(mask)
		        
		    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
		    $('#mask').css({
		            'width' : maskWidth
		            ,'height': maskHeight
		            ,'opacity' :'0.3'
		    });
		  
		    //마스크 표시
		    $('#mask').show();  
		    //로딩중 이미지 표시
		}
		function fn_targetReset(dstnNation) {
			$.ajax({
				url:'/mngr/acnt/zone/resetList/'+$("#userId").val(),
				data:{
					dstnNation : dstnNation
				},
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {  
					fn_RegistList_Load();
					alert("적용 되었습니다.")
	            }, 		    
	            error : function(xhr, status) {
	            	alert("적용에 실패하였습니다. 개발팀에 문의해주세요")
	            }
			})
		}
		

	</script>
	<!-- script addon end -->
</body>
</html>
