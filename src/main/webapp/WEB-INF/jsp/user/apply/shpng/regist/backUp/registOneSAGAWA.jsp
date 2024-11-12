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
						<div class="page-content">
							<div class="page-header">
								<h3>
									배송대행 단일 신청
								</h3>
							</div>
							<form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" id="cnt" name="cnt" value=""/>
							<div id="inner-content-side" >
								<div class="row">
									<div class="col-xs-12 col-lg-12" >
										<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
											<h4>주문정보</h4>
										</div>
										<!-- style="border:1px solid red" -->
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												
												<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
													<h4>SHIPPER 정보</h4>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name shipperNm">이름</div>
														<div class="profile-info-value">
															<input class="col-xs-12 shipperVal" type="text" name="shipperName" id="shipperName" value="${userInfo.userName}" />
														</div>
														<div class="profile-info-name shipperNm">전화번호</div>
														<div class="profile-info-value">
															<input class="col-xs-12 shipperVal" type="text" name="shipperTel" id="shipperTel" value="${userInfo.userTel}" />
														</div>
														<div class="profile-info-name">휴대전화 번호</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="shipperHp" id="shipperHp" value="" />
														</div>
													</div>
													
													<%-- <div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name">SHIPPER 국가</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="shipperCntry" id="shipperCntry" value="${userOrder.shipperCntry}" />
														</div>
														<div class="profile-info-name">SHIPPER 도시</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="shipperCity" id="shipperCity" value="${userOrder.shipperCity}" />
														</div>
														
														<div class="profile-info-name">SHIPPER State</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="shipperState" id="shipperState" value="${userOrder.shipperState}" />
														</div>
													</div> --%>
													
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name shipperNm">
															<label>우편주소</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 shipperVal" type="text" name="shipperZip" id="shipperZip" value="${userInfo.userZip }" />
														</div>
													
														<div class="profile-info-name shipperNm">
															<label>주소</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 shipperVal" type="text" name="shipperAddr" id="shipperAddr" value="${userInfo.userAddr}" />
														</div>
														
														<div class="profile-info-name">
															<label>상세주소</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userInfo.userAddrDetail}"/>
														</div>
													</div>
												</div>
												
												<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
													<h4>수취인 정보</h4>
													<div class="profile-user-info  form-group hr-8">
													<div class=" col-xs-4">
														<div class="profile-info-name cneeNm">이름 (Eng)</div>
														<div class="profile-info-value">
															<input class="col-xs-12 cneeVal" type="text" name="cneeName" id="cneeName" value="" />
														</div>
													</div>
													<div class=" col-xs-4">
														<div class="profile-info-name cneeNm">이름 (Jpn)</div>
														<div class="profile-info-value">
															<input class="col-xs-12 cneeVal" type="text" name="nativeCneeName" id="nativeCneeName" value=""/>
														</div>
													</div>
														
													</div>
													
													<%-- <div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name">수취인 국가</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeCntry" id="cneeCntry" value="${userOrder.cneeCntry}" />
														</div>
														<div class="profile-info-name">수취인 도시</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity}" />
														</div>
														
														<div class="profile-info-name">수취인 State</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
														</div>
													</div> --%>
													
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name cneeNm">전화번호</div>
														<div class="profile-info-value">
															<input class="col-xs-12 cneeVal" type="text" name="cneeTel" id="cneeTel" value="" />
														</div>
														<div class="profile-info-name">휴대전화 번호</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeHp" id="cneeHp" value="" />
														</div>
														<div class="profile-info-name cneeNm">
															<label>우편주소</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 cneeVal" type="text" name="cneeZip" id="cneeZip" value="" />
														</div>
													</div>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name">
															<label>주소 (Eng)</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="" />
														</div>
														
														<div class="profile-info-name">
															<label>상세주소 (Eng)</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="" />
														</div>
													</div>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name cneeNm">
															<label>주소 (Jpn)</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 cneeVal" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="" />
														</div>
														
														<div class="profile-info-name">
															<label>상세주소 (Jpn)</label>
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="" />
														</div>
													</div>
												</div>
												<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
													<h4>운송장 정보</h4>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name orderNm">
															주문 번호
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal" type="text" name="orderNo" id="orderNo" value="" />
														</div>
														<div class="profile-info-name">
															주문 날짜
														</div>
														<div class="profile-info-value">
															<div class="col-xs-12">
																<div class="input-group input-group-sm">
																	<span class="input-group-addon">
																		<i class="ace-icon fa fa-calendar"></i>
																	</span>
																	<input type="text" id="orderDate" name="orderDate" class="col-xs-12" value=""/>
																</div>
															</div>
															<%-- <input class="col-lg-12" type="text" name="orderDate" id="orderDate" value="${userOrder.orderDate}" placeholder="ex):20201231" /> --%>
														</div>
														
														<div class="profile-info-name orderNm">
															CURRENCY
														</div>
														<div class="profile-info-value" style="vertical-align: middle;">
															<select name="chgCurrency" id="chgCurrency${status.count}" >
																<option 
																	<c:if test = "${userOrderItem[0].chgCurrency eq 'USD'}">
																		selected
																	</c:if> 
																value="USD">USD</option>
																<option 
																	<c:if test = "${userOrderItem[0].chgCurrency eq 'GBP'}">
																		selected
																	</c:if>
																value="GBP">GBP</option>
															</select>
															<!-- <input class="col-xs-12 orderVal" type="text" name="chgCurrency" id="chgCurrency" value="" /> -->
														</div>
													</div>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name orderNm">
															Height
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text" name="userHeight" id="userHeight" value="" />
														</div>
														<div class="profile-info-name orderNm">
															Length
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text" name="userLength" id="userLength" value="" />
														</div>
														<div class="profile-info-name orderNm">
															Width
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal volumeWeight" floatOnly type="text" name="userWidth" id="userWidth" value=""/>
														</div>
													</div>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name orderNm">
															무게
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal" floatOnly type="text" name="userWta" id="userWta" value="" />
														</div>
														<div class="profile-info-name orderNm">
															부피 무게
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal" floatOnly type="text" name="userWtc" id="userWtc" value="" />
														</div>
														<div class="profile-info-name orderNm">
															박스 개수
														</div>
														<div class="profile-info-value">
															<input class="col-xs-12 orderVal" numberOnly type="text" name="boxCnt" id="boxCnt" value="" />
														</div>
													</div>
													<div class="profile-user-info  form-group hr-8">
														<div class="profile-info-name orderNm">
															출발지
														</div>
														<div class="profile-info-value">
															<select class="form-control nations orderVal" id="orgStation" name="orgStation">
																<option value="">  </option>
																<c:forEach items="${userOrgStation}" var="orgStationList">
																	<option value="${orgStationList.stationCode}">${orgStationList.stationName}</option>
																</c:forEach>
															</select>
														</div>
														<div class="profile-info-name orderNm">
															도착지
														</div>
														<div class="profile-info-value">
															<select class="form-control nations orderVal" id="dstnNation" name="dstnNation">
																<option value="">  </option>
																<c:forEach items="${userDstnNation}" var="dstnNationList">
																	<option value="${dstnNationList}">${dstnNationList}</option>
																</c:forEach>
															</select>
														</div>
														<div class="profile-info-name orderNm">
															택배 회사
															<input type="hidden" id="selectTrans" name="selectTrans" value="${userOrder.transCode}">
														</div>
														<div class="profile-info-value">
															<select class="form-control orderVal" id="transCode" name="transCode">
																<option value="">  </option>
															</select>
														</div>
													</div>
													
												</div>
											</div>
									</div>
									<!-- 기본정보 End -->
									<!-- 설정정보 Start -->
									<br/>
									<br/>
									<div class="col-xs-12 col-lg-12" >
										<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
											<h2>상품정보</h2>
										</div>
										<!-- style="border:1px solid red" -->
										<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
											<div style="text-align: right">
												<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
													삭제
												</button> -->
												<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
													추가
												</button>
											</div>
											<br/>
											
											
											<div id="accordion" class="accordion-style1 panel-group">
												<div class="panel panel-default panels1">
													<div class="panel-heading">
														<h4 class="panel-title">
															<span class="accordion-toggle" >
																<span data-toggle="collapse" href="#collapseOne" >
																&nbsp;Item #1
																<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
																</span>
																<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
															</span>
														</h4>
													</div>
		
													<div class="panel-collapse collapse multi-collapse in" id="collapseOne">
														<div class="panel-body">
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품명</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100 itemVal" type="text" name="itemDetail" id="itemDetail1" value="" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >Q10 CODE</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100 itemVal" type="text" name="cusItemCode" id="cusItemCode1" value="" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품재질</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100 itemVal" type="text" name="itemMeterial" id="itemMeterial1" value="" />
																	</div>
																</div>
															</div>
														
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100" type="text" name="brandItem" id="brand1" value="" />
																	</div>
																</div>
															</div>
															
															
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품 개수</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100 itemVal" numberOnly type="text" name="itemCnt" id="itemCnt1" value="" />
																	</div>
																</div>
															</div>
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >단가</div>
																	<div class="col-lg-6 col-xs-12">
																		<input class="width-100 itemVal" floatOnly type="text" name="unitValue" id="unitValue1" value="" />
																	</div>
																</div>
															</div>
															
															<div class="row form-group hr-8">
																<div class="col-lg-12 col-xs-12">
																	<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >제조국가</div>
																	<div class="col-lg-6 col-xs-12">
																		<select class="chosen-select form-control tag-input-style width-100 pd-1rem itemVal" id="makeCntry1" name="makeCntry" data-placeholder="국가를 선택해 주세요">
																			<option value="">  </option>
																			<c:forEach items="${nations}" var="nations">
																				<option value="${nations.nationCode}">${nations.nationName}(${nations.nationCode})</option>
																			</c:forEach>
																		</select>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
												
											</div>
										</div>
									</div>
									<!-- 설정정보 End -->
									
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
							</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		</div>
		<div id="modal-form" class="modal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">송장번호를 입력해 주세요</h4>
					</div>
	
					<div class="modal-body">
						<div class="row">
							<div class="col-lg-12">
								<input type="text" name="tags" id="form-field-tags" value="${userOrder.hawbNo}" placeholder="송장번호 추가" maxlength="20"/>
							</div>
						</div>
					</div>
	
					<div class="modal-footer">
						<button class="btn btn-sm" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i>
							Cancel
						</button>
	
						<button class="btn btn-sm btn-primary" id="modalSave" name="modalSave" data-dismiss="modal">
							<i class="ace-icon fa fa-check"></i>
							Save
						</button>
					</div>
				</div>
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
			})
			
			$(".volumeWeight").on('change',function(e){
				var temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/6000;
				$("#userWtc").val(temp.toFixed(2));
			})
			
			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });
	
	        $("input:text[floatOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9.]/g,""));
	        });

			function fn_delete(test){
				if($(".panel").length > 1){
					test.closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}
			};
			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});
			
			/* $('[name*=delBtns]').on('click',function(e){
				if($(".panel").length > 1){
					$(this).closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}*/
				
				/* $('input:checked').val(); 
			}); */

			$('#backToList').on('click', function(e){
				history.back();


			});

			var tag_input = $('#form-field-tags');
			try{
				tag_input.tag(
				  {
					placeholder:tag_input.attr('placeholder'),
					maxlength:20,
					//enable typeahead by specifying the source array
					//source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
					/**
					//or fetch data from database, fetch those that match "query"
					source: function(query, process) {
					  $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
					  .done(function(result_items){
						process(result_items);
					  });
					}
					*/
				  }
				)
		
				//programmatically add/remove a tag
				var $tag_obj = $('#form-field-tags').data('tag');
				var index = $tag_obj.inValues('some tag');
				$tag_obj.remove(index);
			}
			catch(e) {
				//display a textarea for old IE, because it doesn't support this plugin or another one I tried!
				tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
				//autosize($('#form-field-tags'));
			}

			

			$('#modal-form input[type=file]').ace_file_input({
				style:'well',
				btn_choose:'Drop files here or click to choose',
				btn_change:null,
				no_icon:'ace-icon fa fa-cloud-upload',
				droppable:true,
				thumbnail:'large'
			})
			
			//chosen plugin inside a modal will have a zero width because the select element is originally hidden
			//and its width cannot be determined.
			//so we set the width after modal is show
			$('#modal-form').on('shown.bs.modal', function () {
				var $tag_obj = $('#form-field-tags').data('tag');
				var rooplen = $tag_obj.values.length;
				for(var i=0;i<rooplen;i++){
					$tag_obj.remove(0);
				}
				if($('#hawbNo').val()!=""){
					for(var i = 0; i < $('#hawbNo').val().split(',').length; i++){
						$tag_obj.add($('#hawbNo').val().split(',')[i]);
					}
					
				}
				
				$("#form-field-tags").val($('#hawbNo').val());
				if(!ace.vars['touch']) {
					$(this).find('.chosen-container').each(function(){
						$(this).find('a:first-child').css('width' , '210px');
						$(this).find('.chosen-drop').css('width' , '210px');
						$(this).find('.chosen-search input').css('width' , '200px');
					});
				}
			})
			
			$('#modalSave').on('click',function(e){
				$('#hawbNo').val($("#form-field-tags").val());
			})

			$(".nations").on('change',function(e){

				var param1 = $("#orgStation").val();
				var param2 = $("#dstnNation").val();

				$.ajax({
					url:'/cstmr/apply/transComList',
					type: 'POST',
					datatype: "json",
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: { param1 : param1,
							param2 : param2
						},
					success : function(data) {
						addHtml = "";
						for(var i = 0; i < data.transComList.length; i++){
							addHtml += "<option value='"+data.transComList[i].transCode+"'>"+data.transComList[i].transCode+"</option>'"
						}
						$("#transCode").html(addHtml)
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
				

			})

			$('#addBtns').on('click',function(e){
				$("#cnt").val(cnt);
				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/itemList',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						$("#accordion").append(data);
						cnt++;
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});
			
			$('#rgstrInfo').on('click',function(e){
				var valiChk = 0;
				$(".shipperVal").each(function(e){
					if($(this).val() == ""){
						alert("SHIPPER정보의 "+$($(".shipperNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}
				$(".cneeVal").each(function(e){
					if($(this).val() == ""){
						alert("수취인 정보의 "+$($(".cneeNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
						
					}
				})
				if(valiChk==1){
					return;
				}
				$(".orderVal").each(function(e){
					if($(this).val() == ""){
						alert("운송장 정보의 "+$($(".orderNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}
				$(".itemVal").each(function(e){
					if($(this).val() == ""){
						var itemNum = e/7;
						itemNum = parseInt(itemNum)+1;
						alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}

				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/registOrderInfo',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						if(data == "S"){
							alert("등록 되었습니다.");
							location.href="/cstmr/apply/shpngAgncy";
						}else{
							alert("등록중 오류가 발생했습니다. 데이터를 확인해 주세요")
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

			});

		</script>
		<!-- script addon end -->
	</body>
</html>
