<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
* {
	font-family: arial, sans-serif;
}

body {
	margin-left:10px;
}

#receiptBtn {
	width:80px;
	height:30px;
	margin-left:5px;
	border:solid 1px #dcdcdc;
	border-radius:6px;
	background:#dcdcdc;
	font-weight:bold;
	font-size:14px;
}

#receiptBtn:hover {
	border:solid 1px #c8c8c8;
	background:#c8c8c8;
	cursor:pointer;
}

table {
	width:80%;
	border:solid 1px gray;
	border-collapse:collapse;
	margin-top:10px;
	margin-left:4px;
}

table th, td {
	border:solid 1px gray;
}

table th {
	background:#dcdcdc;
	font-size:14px;
}
	
.colorBlack {
	color: #000000 !important;
}
.noneArea {
	margin:0px !important;
	padding:0px !important;
	border-bottom : none;
}
.containerTable {
	width: 100% !important;
}

table ,tr td{
	word-break:break-all;
}
</style>
<html>
<style>
	
.col-xs-12 col-lg-12{
	width: 90%;
}
	
body {
	padding-left: 10px;
	padding-top: 10px;
}

* {
	font-family: arial, sans-serif;
}

.noticeTable {
	width: 90%;
	border-radius: 10px;
	box-shadow: 2px 2px 5px 2px gray;
	padding-left: 15px;
	padding-bottom: 15px;
	padding-right: 15px;
}

.noticeTable th {
	border: none;
}

.noticeTable td {
	border: none;
}

#notice_text {
	border: solid 1px #a0a0a0;
	width: 100%;
	height: 180px;
	box-sizing: border-box;
	resize: both;
	padding: 10px;
	font-size: 13px;
}

.comm_text {
	width: 100%;
	height: 100%;
	border: solid 1px #a0a0a0;
	box-sizing: border-box;
	margin-top: 4px;
}

.hawbTable {
	width: 580px;
	border: solid 1px #ddd;
	background: #E4E6E9;
	border-collapse: collapse;
}

.hawbTable td {
	border: solid 1px #ddd;
	padding-right: 8px;
}

#koblNo {
	width: 100%;
	height: 40px;
	margin-right: 10px;
	border: solid 1px #ddd;
	background-color: white;
	font-size: 26px;
	vertical-align: middle;
	color: #828282;
}

input:focus {
	outline: none;
}

input:enabled {
	background: white;
}

#hawb_btn {
	background: #dcdcdc;
	display: block;
	color: inherit;
	font: inherit;
	margin: 0 auto;
	width: 60px;
	height: 30px;
	cursor: pointer;
	border: solid 1px black;
	border-radius: 2px;
}

#hawb_btn:hover {
	border: solid 1px black;
	background: #ccc;
}

.info_title {
	font-size: 18px;
	font-weight: bold;
	color: black;
}

.infoTable {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	font-size: 14px;
	box-shadow: 1px black;
	background: #fff;
}

.infoTable td {
	border: solid 1px #ccc;
}

.infoTable td:nth-child(odd) {
	color: #707070;
	border: solid 1px #ccc;
	background: linear-gradient(to top, #E4E6E9, white);
}

.info_input {
	width: 100%;
	height: 30px;
	border: none;
	font-size: 14px;
}

.tableHeader {
	font-family: 'Open Sans';
	text-align: center;
	padding: 5px 0;
	font-size: 14px;
	color: #000000 !important;
	font-weight: bold;
	background: linear-gradient(to top, #E4E6E9, white);
	border: solid 1px #ccc;
}

.td_name {
	font-family: 'Open Sans';
	text-align: center;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
	font-weight: bold;
	width: 140px;
	background: linear-gradient(to top, #E4E6E9, white);
}

.rtn_radio {
	margin: 6px;
	vertical-align: middle;
}

.radio_div {
	vertical-align: middle;
}

.cancel_info {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	border: solid 1px #bebebe;
	box-shadow: 1px 1px 1px #ddd;
}

.cancel_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

#cncl_radio {
	margin-right: 8px;
}

.goods_info {
	width: 90%;
	height: 30px;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
}

.goods_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

.remand_info {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.remand_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

.remand_info tr {
	height: 2em;
}

.td_name2 {
	font-family: 'Open Sans';
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
	line-height: 1.5;
	text-align: center;
	font-weight: bold;
	width: 180px;
	background: linear-gradient(to top, #E4E6E9, white);
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: underline;
}

a:active {
	color: black;
	text-decoration: none;
}

.return_type {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.return_type td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	color: #000000 !important;
}

#rtnBtn {
	width: 80px;
	height: 40px;
	font-weight: bold;
	margin-top: 40px;
	color: inherit;
	font: inherit;
	cursor: pointer;
	border: solid 1px black;
	display: block;
	margin: 40px auto;
}

#rtnBtn:hover {
	background: #d8d8d8;
	cursor: pointer;
	border-radius: 2px;
}

::placeholder {
	color: gray;
	opacity: 0.5;
}
.colorBlack {color:#000000 !important;}

.backYellow {background-color: #ffffc9 !important;}

.b01_simple_rollover {
    color: #000000;
    border: #000000 solid 1px;
    padding: 10px;
    background-color: #ffffff;
}

.b01_simple_rollover:hover {
    color: #ffffff;
    background-color: #000000;
}

</style>
</head>
	<title>상품정보</title>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<!-- headMenu End -->
			<!-- Main container Start-->
			<div class="toppn">
				<div class="container">
		        	<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품상세정보</strong>
						</div>
		        	</div>
				</div>
			</div>
	    </div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="container">
			<div class="page-content noneArea">
				<div id="inner-content-side" >
				<br>
				<h2 style="margin-left:4px;">반품상세정보</h2>
				<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
				<!-- Main container Start-->
					<div class="main-container ace-save-state" id="main-container">
						<script type="text/javascript">
							try{ace.settings.loadState('main-container')}catch(e){}
						</script>
						<div class="main-content">
							<form name="returnForm" id="returnForm" enctype="multipart/form-data">
								<input type="hidden" name="orderReference" id="orderReference" value="${returnInspOne.ORDER_REFERENCE}">
								<input type="hidden" name="nno" id="nno" value="${returnInspOne.NNO}">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="table-contents" class="table-contents"">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="vertical-align: middle;">
										<colgroup>
											<col width="12%"/> 
											<col width="15%"/>
											<col width="11%"/>
											<col width="15%"/>
											<col width="21%"/>
											<col width="26%"/>
										</colgroup>
										<tr>
			                    	 		<td>
			                    	 			KOBL NO : ${returnInspOne.KOBL_NO} 
			                    	 		</td>
			                    	 		<td>
			                    	 			ORDER_NO : ${returnInspOne.ORDER_NO }
			                    	 		</td>
			                    	 		<td>출발지/도착지 : KR/${returnInspOne.DSTN_NATION }</td>
			                    	 		<td>주문일자 : ${returnInspOne.ORDER_DATE}</td>
			                    	 		<td>
			                    	 			RETURN TRK COM : ${returnInspOne.RE_TRK_COM}
		                    	 			</td>
			                    	 		<td>
			                    	 			RETURN TRK NO  : ${returnInspOne.RE_TRK_NO}
			                    	 		</td>
		                    	 		</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center;">
			                    	 			반송 신청인 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td>
			                    	 			반송인 : ${returnInspOne.PICKUP_NAME }
			                    	 		</td>
			                    	 		<td>
			                    	 			연락처1: ${returnInspOne.PICKUP_TEL }<br/>
			                    	 			연락처2: ${returnInspOne.PICKUP_MOBILE }
			                    	 		</td>
			                    	 		<td>
			                    	 			우편번호: ${returnInspOne.PICKUP_ZIP }
			                    	 		</td>
			                    	 		<td>
			                    	 			
			                    	 		</td>
			                    	 		<td>
			                    	 			주소: ${returnInspOne.PICKUP_ADDR }
			                    	 		</td>
			                    	 		<td>
			                    	 			영문 주소: ${returnInspOne.PICKUP_ENG_ADDR }
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center;">
			                    	 			수취 업체 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td>
			                    	 			업체명(수취인 명) : ${returnInspOne.SENDER_NAME }
			                    	 		</td>
			                    	 		
			                    	 		<td>
			                    	 			연락처1: ${returnInspOne.SENDER_TEL }<br/>
			                    	 			연락처2: ${returnInspOne.SENDER_HP }
			                    	 		</td>
			                    	 		<td>
			                    	 			우편번호: ${returnInspOne.SENDER_ZIP }
			                    	 		</td>
			                    	 		<td>E-MAIL : ${returnInspOne.SENDER_EMAIL}</td>
			                    	 		<td>
			                    	 			STATE : ${returnInspOne.SENDER_STATE }<br/>
			                    	 			CITY : ${returnInspOne.SENDER_CITY }
			                    	 		</td>
			                    	 		<td>
			                    	 			주소: ${returnInspOne.SENDER_ADDR } ${returnInspOne.SENDER_BUILD_NM}
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center;">
			                    	 			반송 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td>
			                    	 			위약반송 여부 : ${returnInspOne.TAX_TYPE }
			                    	 		</td>
			                    	 		<td>
			                    	 			발송 구분 : 주기 발송<%-- ${returnInspOne.RETURN_TYPE } --%> 
			                    	 		</td>
			                    	 		<td>
			                    	 			반품 사유 : 단순 변심<%-- ${returnInspOne.RETURN_REASON } --%>
			                    	 		</td>
			                    	 		<td>
			                    	 			반품 상세 사유 : ${returnInspOne.RETURN_REASON_DETAIL }
		                    	 			</td>
			                    	 		<td>
			                    	 			배송 메시지: ${returnInspOne.DELIVERY_MSG}
			                    	 		</td>
			                    	 		<td>
			                    	 			창고 입고 메시지 : ${returnInspOne.WARE_HOUSE_MSG }
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center;">
			                    	 			상품 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="vertical-align: middle;">
			                    	 			<div class="row">
			                    	 				<div class="col-xs-12">
					                    	 			<table class="table table-bordered table-hover containerTable">
					                    	 				<colgroup>
																<col width="25%"/>
															</colgroup>
															<input type="hidden" id="itemCnt" name="itemCnt" value="${fn:length(returnInspOne.orderItemList)}">
					                    	 				<c:forEach items="${returnInspOne.orderItemList}" var="inspItemList" varStatus="itemStatus">
						                    	 				<tr>
						                    	 					<td style="text-align: center;">
						                    	 						<span class="profile-picture">
						                    	 							<a href="${inspItemList.ITEM_URL }" target=”_blank”>
						                    	 								<img class="editable img-responsive" src="${inspItemList.ITEM_IMG_URL }" loading="lazy">
						                    	 							</a>
					                    	 							</span>
						                    	 					</td>
						                    	 					<td  style="font-size: x-large;">
						                    	 						<div class="profile-user-info" style="font-size:x-large;">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">상품명 #${itemStatus.count }</div>
						                    	 								<div class="profile-info-value bigger-140">
						                    	 									${inspItemList.ITEM_DETAIL }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">Brand</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.BRAND }
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info " style="font-size:x-large;">
						                    	 							<div class="profile-info-row ">
						                    	 								<div class="profile-info-name">상품 수량</div>
						                    	 								<div class="profile-info-value">
					                    	 										<h1><label id="housCntActive" class="bigger-180 real-red">${inspItemList.ITEM_CNT }</label></h1>
						                    	 								</div>
						                    	 								<div class="profile-info-name">입고 수량</div>
						                    	 								<div class="profile-info-value">
					                    	 										<h1><label id="housCntActive" class="bigger-180 real-red">${inspItemList.STOCK_CNT }</label></h1>
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info ">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name" style="font-size:x-large;">Item 크기</div>
						                    	 									<div class="profile-info-value" style="font-size: medium;">
						                    	 										<div class="row">
																							<div class="col-xs-12">
																								<div class="col-lg-4 col-xs-12">
																									<div class="col-lg-4 col-xs-12">WIDTH  : </div>
																									<div class="col-lg-8 col-xs-12">
																											${inspItemList.ITEM_WIDTH }
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">HEIGHT  : </div>
																									<div class="col-xs-12 col-lg-8">
																											${inspItemList.ITEM_HEIGHT}
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">LENGTH  : </div>
																									<div class="col-xs-12 col-lg-8">
																										${inspItemList.ITEM_LENGTH }
																									</div>
																								</div>
																							</div>
																							<div class="col-xs-12">
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">WTA : </div>
																									<div class="col-xs-12 col-lg-8">
																										${inspItemList.ITEM_WTA }
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">PER  : </div>
																									<div class="col-xs-12 col-lg-8">
																										${inspItemList.ITEM_PER}
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-6">
																										길이 단위 : 
																									${inspItemList.ITEM_DIM_UNIT }
																								</div>
																								<div class="col-xs-12 col-lg-6">
																										무게 단위 : 
																									${inspItemList.ITEM_WT_UNIT }
																								</div>
																							</div>
																						</div>
																					</div>
					                    	 									</div>
					                    	 								</div>
						                    	 						</div>
					                    	 							<div class="profile-user-info " style="font-size:x-large;">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조국</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_CNTRY }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조사</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_COM }
						                    	 								</div>
						                    	 							</div>
						                    	 						</div>
						                    	 						<div class="profile-user-info" name="fileFIled" style="font-size:medium; border-top:none;">
																			<div class="profile-info-row" >
																				<div class="profile-info-name">
																					<label class="bolder red bigger-150">이미지</label> 
																				</div>
																				<div class="profile-info-value">
																					<span class="red"> *미리보기</span>
																					<div>
																						<c:forEach items="${inspItemList.imgList}" var="imgList" varStatus="imgListStatus">
																							<img class="editable img-responsive" src="https://s3.ap-northeast-2.amazonaws.com/${imgList.FILE_DIR }" loading="lazy">
																						</c:forEach>
																						
																				    </div>
																				</div>
																			</div>
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</table>
													</div>
		                    	 					<div class="profile-user-info" style="font-size:large;">
														<div class="profile-info-row">
															<div class="profile-info-name"> 
																<label class="blue" style="font-size: large;">검수상태</label>
															</div>
															<div class="profile-info-value">
																<c:if test="${returnInspOne.STATE eq 'C002'}">정상입고</c:if>
																<c:if test="${returnInspOne.STATE eq 'C003'}">출고 대기</c:if>
																<c:if test="${returnInspOne.STATE eq 'C004'}">출고 승인</c:if>
																<c:if test="${returnInspOne.STATE eq 'D004'}">검수 불합격</c:if>
																<c:if test="${returnInspOne.STATE eq 'D005'}">폐기</c:if>
															</div>
														</div>
													</div>
													<c:if test="${returnInspOne.STATE eq 'D004' or returnInspOne.STATE eq 'D005'}">
			                    	 					<div class="profile-user-info" style="font-size:large;">
															<div class="profile-info-row">
																<div class="profile-info-name"> 
																	<label class="blue" style="font-size: large;">검수 불합격 사유</label>
																	
																</div>
																<div class="profile-info-value">
																	<c:if test="${returnInspOne.FAIL_REASON ne 'WF3'}">${returnInspOne.FAIL_MSG}</c:if>
																	<c:if test="${returnInspOne.FAIL_REASON eq 'WF3'}">기타 사유 (${returnInspOne.FAIL_MSG})</c:if>
																	
																</div>
															</div>
														</div>
													</c:if>
													<div class="profile-user-info" style="font-size:large;">
														<div class="profile-info-row">
															<div class="widget-box transparent" name="rackField">
																<div class="widget-header widget-header-small">
																	<h2 class="widget-title smaller">
																		<i class="ace-icon fa fa-check-square-o bigger-170"></i>
																		Rack 정보 : ${returnInspOne.RACK_CODE}
																	</h2>
																</div>
															</div>
														</div>
													</div>
													
			                    	 				<div class="row">
														<div class="col-xs-12 col-sm-3 col-sm-offset-3">
															<button type="button" class="btn btn-lg" id="backBtn" name="backBtn">뒤로가기</button>
														</div>
													</div>
												</div>
											</td>
										</tr>
		                    	 	</table>
		                    	 </div>
							</form>
						
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			$(".backYellow").focusout(function(){
				if($(this).val().length != 0){
					$(this).removeClass("backYellow");
				}else{
					$(this).addClass("backYellow");
				}
			});
			$("#backBtn").on("click",function(e){
				history.back();
			})

			$('input[name="pickType"]').on('click',function(e){
				if($(this).val()=='A'){
					$("#reTrk").removeClass("hide");
					$("#reTrkCom").addClass("backYellow");
					$("#reTrkNo").addClass("backYellow");
					$("#A002Date").addClass("backYellow");
				}else{
					$("#reTrk").addClass("hide");
					$("#reTrkCom").removeClass("backYellow");
					$("#reTrkNo").removeClass("backYellow");
					$("#A002Date").removeClass("backYellow");
				}
			});
			
			jQuery(function($) {
				$(document).ready(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thThr").toggleClass('active');	

					if($("#taxType").val()=='Y'){
						$(".hideTax").removeClass('hide');
					}

					if($('input[name="pickType"]').val() == "A"){
						$("#reTrk").removeClass("hide");
					}
					
					$('input[name="taxType"]').on('click',function(){
						if($(this).val() == 'Y'){
							$(".hideTax").removeClass('hide');
						}else if($(this).val() == 'N'){
							$(".hideTax").addClass('hide');
						}
					});
	
					for(var i = 0; i < $(".backYellow").size(); i++){
						if($($(".backYellow")[i]).val().length != 0){
							$($(".backYellow")[i]).removeClass('backYellow');
							i = i-1;
						}
					}
	
					
				});
			});
		</script>
		<!-- script addon end -->
	</body>
</html>
