<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="UTF-8">
<title>반품 접수 내역</title>
<head>
<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<link rel="stylesheet" href="/assets/css/orderIn.css"/>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
</head>
<body>
 	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
	<!-- headMenu End -->
	<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size:14px !important;">
						<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품 접수</strong>
					</div>
	        	</div>
	      	</div>
	    </div>
    </div>
    <form name="returnForm" id="returnForm" enctype="multipart/form-data">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" id="cnt" name="cnt" value=""/>
		<input type="hidden" name="siteDiv" id="siteDiv" />
    	<div class="container">
    		<div class="page-content">
    			<div class="tab-content" style="margin:auto;margin-top:20px;">
					<h2 style="margin-left: 10px;">반품 접수</h2>
					<br>
					<div class="table-layout" id="div-1"><!-- 송장 정보 -->
						<table>
							<tr>
								<th style="font-size:18px;padding-right:10px;">
									원운송장번호
								</th>
								<td style="padding:0;"><input type="text" id="koblNo" name="koblNo" style="width:200px;height:33px;"></td>
							</tr>
						</table>
					</div>
					<div class="table-layout" id="div-2"><!-- 집하지 정보 -->
						<table>
							<tr>
								<th style="width:118px;font-size:18px;"><font class="red">*</font> 집화지</th>
								<td style="padding:0;">
									<select id="orgStation" name="orgStation" style="width:80px;height:33px;">
										<option value="" selected>::선택::</option>
										<option value="082">서울</option>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<br>
					<div class="table-layout" id="div-3"><!-- 픽업 정보 -->
						<table style="width:100%;" class="radio-table">
							<tr>
								<th style="font-size:20px;width:120px;"><font class="red">*</font> 픽업 방법</th>
								<td>
									<input type="radio" name="pickType" value="A" id="a_pick" checked>
									<label for="a_pick" class="labels">직접 발송</label>
									<input type="radio" name="pickType" value="B" id="b_pick">
									<label for="b_pick" class="labels">회수 요청</label>
								</td>
								<th style="text-align:right;"><font class="red">*</font> 필수</th>
							</tr>
						</table>
						<table class="input-table">
							<thead>
								<tr>
									<th colspan="2">반품 택배 정보</th>
									<th colspan="2">반품 담당자 정보</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>택배회사</th>
									<td><input type="text" name="trkCom" id="trkCom"></td>
									<th>이름</th>
									<td><input type="text" name="attnName" id="attnName"></td>
								</tr>
								<tr>
									<th>송장번호</th>
									<td><input type="text" name="trkNo" id="trkNo"></td>
									<th>연락처</th>
									<td><input type="text" name="attnTel" id="attnTel"></td>
								</tr>
								<tr>
									<th>발송일</th>
									<td><input type="text" name="trkDate" id="trkDate" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"></td>
									<th>이메일</th>
									<td><input type="text" name="attnEmail" id="attnEmail"></td>
								</tr>
							</tbody>
						</table>
						<div style="width:100%;text-align:right;margin-top:10px;font-weight:300 !important;">
							<input type="button" onclick="fn_setInvoiceInfo();" class="attn-btn" value="청구지 정보">
							<input type="button" onclick="fn_setCurrentInfo();" class="attn-btn" value="최근 정보">
						</div>
						<br>
						<table class="input-table">
							<thead>
								<tr>
									<th colspan="4">발송인 정보</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th><font class="red">*</font> 주문번호</th>
									<td><input type="text" name="orderNo" id="orderNo"></td>
									<th>주문일자</th>
									<td><input type="text" name="orderDate" id="orderDate" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"></td>
								</tr>
								<tr>
									<th><font class="red">*</font> 이름 (영문)</th>
									<td><input type="text" name="shipperName" id="shipperName"></td>
									<th><font class="red">*</font> 우편번호</th>
									<td><input type="text" name="shipperZip" id="shipperZip" zipCodeOnly></td>
								</tr>
								<tr>
									<th><font class="red">*</font> 주소 (영문)</th>
									<td><input type="text" name="shipperAddr" id="shipperAddr"></td>
									<th>상세주소 (영문)</th>
									<td><input type="text" name="shipperAddrDetail" id="shipperAddrDetail"></td>
								</tr>
								<tr>
									<th><font class="red">*</font> 전화번호</th>
									<td><input type="text" name="shipperTel" id="shipperTel" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"></td>
									<th>휴대전화번호</th>
									<td><input type="text" name="shipperHp" id="shipperHp" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"></td>
								</tr>
							</tbody>
						</table>
						<table class="input-table">
							<thead>
								<tr><th colspan="4">수취업체 정보</th></tr>
							</thead>
							<tbody>
								<tr>
									<th><font class="red">*</font> 업체명</th>
									<td><input type="text" name="cneeName" id="cneeName"></td>
									<th><font class="red">*</font> 국가</th>
									<td>
										<select class="chosen-select-start form-control tag-input-style nations" id="dstnNation" name="dstnNation" >
											<c:forEach items="${nationList}" var="dstnNationList">
												<option value="${dstnNationList.nationCode}" <c:if test="${dstnNationList.nationCode eq 'US'}">selected</c:if>>${dstnNationList.nationEName} (${dstnNationList.nationName})</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th><font class="red">*</font> 주 <i class="far fa-question-circle"  data-tooltip-text="미국 도착인 경우 2CODE로 기입해야 합니다. 예) CA, AL, LA" id="tip"></i></th>
									<td><input type="text" name="cneeState" id="cneeState"></td>
									<th><font class="red">*</font> 도시</th>
									<td><input type="text" name="cneeCity" id="cneeCity"></td>
								</tr>
								<tr>
									<th><font class="red">*</font> 주소</th>
									<td><input type="text" name="cneeAddr" id="cneeAddr"></td>
									<th>상세 주소</th>
									<td><input type="text" name="cneeAddrDetail" id="cneeAddrDetail"></td>
								</tr>
								<tr>
									<th><font class="red">*</font> 우편번호</th>
									<td><input type="text" name="cneeZip" id="cneeZip" zipCodeOnly></td>
									<th><font class="red">*</font> 전화번호</th>
									<td><input type="text" name="cneeTel" id="cneeTel" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"></td>
								</tr>
							</tbody>
						</table>
						<br><br>
						<table style="width:100%;" class="radio-table">
							<tr>
								<th style="font-size:20px;width:120px;"><font class="red">*</font> 반품 구분</th>
								<td>
									<input type="radio" name="returnType" value="N" id="n_pick" checked>
									<label for="n_pick" class="labels">일반</label>
									<input type="radio" name="returnType" value="E" id="e_pick">
									<label for="e_pick" class="labels">긴급</label>
								</td>
							</tr>
						</table>
						<table class="nohead-table">
							<tbody>
								<tr>
									<th style="height:33px;"><font class="red">*</font> 반품 사유</th>
									<td style="padding-left:10px;">
										<input type="radio" name="returnReason" value="A" id="a_reason" checked>
										<label for="a_reason" class="labels">단순변심</label>
										<input type="radio" name="returnReason" value="B" id="b_reason">
										<label for="b_reason" class="labels">파손</label>
										<input type="radio" name="returnReason" value="C" id="c_reason">
										<label for="c_reason" class="labels">불량</label>
										<input type="radio" name="returnReason" value="D" id="d_reason">
										<label for="d_reason" class="labels">기타</label>
									</td>
								</tr>
								<tr>
									<th><font class="red hide" id="returnDetailRed">*</font> 반품 상세사유</th>
									<td style="padding:0;"><input style="width:100%;height:33px;border:none;outline:none;" type="text" name="returnReasonDetail" id="returnReasonDetail"></td>
								</tr>
								<tr>
									<th>입고 요청사항</th>
									<td style="padding:0;"><input style="width:100%;height:33px;border:none;outline:none;" type="text" name="whMsg" id="whMsg"></td>
								</tr>
							</tbody>
						</table>
						<br>
						<table id="tax-table">
							<thead>
								<tr>
									<th colspan="3">위약 반송 정보 <small style="padding: 6px;font-weight:300 !important;">* 관부가세 환급시 반드시 작성해 주세요</small></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th><font class="red">*</font> 위약 반송</th>
									<td colspan="2">
										<input type="radio" name="taxType" id="a_tax" value="Y">
										<label class="labels" for="a_tax">위약 반송</label>
										<input type="radio" name="taxType" id="n_tax" value="N" checked>
										<label class="labels" for="n_tax">일반 반송</label>
									</td>
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 반송 사유서</th>
									<td><input type="file" name="fileReason" id="fileReason" class="taxTypeValue"></td>
									<td><a href="/반송 사유서.xlsx" download>[반송사유서 다운로드]</a></td>
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 반품 접수 캡처본</th>
									<td><input type="file" name="fileCapture" id="fileCapture" class="taxTypeValue"></td>
									<td></td>	
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 반품 메신저 캡처본</th>
									<td><input type="file" name="fileMessenger" id="fileMessenger" class="taxTypeValue"></td>
									<td></td>
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 반품 Commercial Invoice</th>
									<td><input type="file" name="fileComm" id="filComm" class="taxTypeValue"></td>
									<td><a href="/반품 커머셜 인보이스.xlsx" download>[반품 커머셜 다운로드]</a></td>
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 환급 통장 사본</th>
									<td><input type="file" name="fileBank" id="fileBank" class="taxTypeValue"></td>
									<td></td>
								</tr>
								<tr class="hide-tax hide">
									<th><font class="red">*</font> 수출신고 필증</th>
									<td><input type="file" name="fileLicense" id="fileLicense" class="taxTypeValue"></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="station-info">
						<div id="station-desc">
							<p style="font-weight:600;font-size:16px;margin-top:6px;">(주) 에이씨와이 월드와이드</p>
							<p style="font-size:14px;">서울특별시 강서구 남부순환로19길 121 (외발산동)</p>
							<p style="font-size:14px;">02) 2663-3300</p>
						</div>
						<img src="/image/full-logo.jpg" id="logo-img">
					</div>
    			</div>
    			<br><!-- 반품정보 End -->
    			<div style="width:100%;text-align:right;">
    				<button type="button" id="addBtns" name="addBtns">
    					상품 추가 <i class="fa fa-plus" style="margin-left:3px;"></i>
   					</button>
    			</div>
				<div class="accordion-style1 panel-group" id="accordion">
					<div class="panel panel-default panels1">
						<div class="panel-heading">
							<h4 class="panel-title">
								<span class="accordion-toggle">
									<span data-toggle="collapse" href="#collapseOne">
										Item #1
										<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
									</span>
									<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
								</span>
							</h4>
						</div>
						<div class="panel-collapse collapse multi-collapse in" id="collapseOne">
							<div class="panel-body">
								<div class="col-xs-12 col-lg-12">
    								<div class="col-xs-12 col-lg-12" style="font-size: 3;">
    									<div class="row hr-8">
    										<div class="col-xs-1 center mp07">
    											<label class="red">상품명</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 itemDetail" type="text" name="itemDetail[]" id="itemDetail" placeholder="영문 기입">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>현지 상품명</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 nativeItemDetail" type="text" name="nativeItemDetail[]" id="nativeItemDetail">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>상품코드</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 cusItemCode" type="text" name="cusItemCode[]" id="cusItemCode">
    										</div>
    									</div>
    									<div class="hr dotted"></div>
    									<div class="row hr-8">
    										<div class="col-xs-1 center mp07">
    											<label>브랜드</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 brand" type="text" name="brand[]" id="brand">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>상품 무게</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 itemWta" type="text" name="itemWta[]" id="itemWta" onkeyup="$(this).val($(this).val().replace(/[^0-9|.]/g,''));">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>무게 단위</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 wtUnit" type="text" name="wtUnit[]" id="wtUnit">
    										</div>
    									</div>
    									<div class="hr dotted"></div>
    									<div class="row hr-8">
    										<div class="col-xs-1 center mp07">
    											<label class="red">상품 수량</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 itemCnt" type="text" name="itemCnt[]" id="itemCnt" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''));">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label class="red">판매 단가</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 unitValue" type="text" name="unitValue[]" id="unitValue" onkeyup="$(this).val($(this).val().replace(/[^0-9|.]/g,''));">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label class="red">통화 단위</label>
    										</div>
    										<div class="col-xs-3">
    											<select class="chosen-select-start unitCurrency" id="unitCurrency" name="unitCurrency[]">
													<c:forEach items="${currencyList}" var="currencyList">
														<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
													</c:forEach>
												</select>
    										</div>
    									</div>
    									<div class="hr dotted"></div>
    									<div class="row hr-8">
    										<div class="col-xs-1 center mp07">
    											<label class="red">제조국</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 makeCntry" type="text" name="makeCntry[]" id="makeCntry">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>제조 회사</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 makeCom" type="text" name="makeCom[]" id="makeCom">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label>
    												<a href="https://unipass.customs.go.kr/clip/index.do" style="color:#DD5A43;" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
    												<i class="fa fa-external-link"></i></a>
												</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 hsCode" type="text" name="hsCode[]" id="hsCode" onkeyup="$(this).val($(this).val().replace(/[^0-9|.-]/g,''));">
    										</div>
    									</div>
    									<div class="hr dotted"></div>
    									<div class="row hr-8">
    										<div class="col-xs-1 center mp07">
    											<label>상품 URL</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 itemUrl" type="text" name="itemUrl[]" id="itemUrl">
    										</div>
    										<div class="col-xs-1 center mp07">
    											<label class="red">상품 이미지 URL</label>
    										</div>
    										<div class="col-xs-3">
    											<input class="col-xs-12 itemImgUrl" type="text" name="itemImgUrl[]" id="itemImgUrl">
    										</div>
    									</div>
    									<div class="hr dotted"></div>
    								</div>
    							</div>
							</div>
						</div>
					</div>
				</div>
    			<div style="margin:auto;text-align:center;margin-top:30px;">
    				<input type="button" id="backToList" value="접수 취소">
    				<input type="button" id="registReturn" value="반품 접수">
    			</div>
    		</div>
   		</div>
    </form>
    <%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	<script src="/testView/js/main.js"></script>
	
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
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<script type="text/javascript">
	 	jQuery(function($) {
		 	var cnt = 1;
		 	var date = new Date();
		    var year = date.getFullYear();
		    var month = ("0" + (1 + date.getMonth())).slice(-2);
		    var day = ("0" + date.getDate()).slice(-2);
			var orderDate = year + month + day;
			$(document).ready(function(e) {

			})
			
			$("#trkDate, #orderDate").attr("placeholder", "ex) 20230101");
			$("#trkDate, #orderDate").attr("autocomplete", "off");
			$("#trkDate, #orderDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})

			$("input[name='pickType']").on('click', function() {
				if ($(this).val() == 'A') {
					$("#trkCom").attr("readonly", false);
					$("#trkNo").attr("readonly", false);
					$("#trkDate").attr("readonly", false);
				} else {
					$("#trkCom").attr("readonly", true);
					$("#trkNo").attr("readonly", true);
					$("#trkDate").attr("readonly", true);
				}
			})

			$("input:text[zipCodeOnly]").on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9|-]/g,""));
			})
			
			$("input[name='returnReason']").on('click', function() {
				if ($(this).val() == 'D') {
					$("#returnDetailRed").removeClass('hide');
				} else {
					$("#returnDetailRed").addClass('hide');
				}
			})
			
			$("input[name='taxType']").on('click', function() {
				if ($(this).val() == 'Y') {
					$(".hide-tax").removeClass('hide');
				} else {
					$(".hide-tax").addClass('hide');
				}
			})
			
			$("#orgStation").change(function() {
				if ($(this).val() == '082') {
					$("#station-info").css("opacity", 1);
				}
			})
			
			$("#addBtns").on('click', function() {
				$("#cnt").val(cnt);
				var formData = $("#returnForm").serialize();
				
				$.ajax({
					url : '/cstmr/return/itemList',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					success : function(data) {
						cnt++;
						$("#accordion").append(data);
					},
					error : function(xhr, status) {
						alert("다시 시도해 주세요.");
					}
				})
			})

			$("#backToList").on('click', function() {
				location.href="/return/list";
			});

			$("#registReturn").on('click', function() {
				if($("#orgStation").val() == "") {
					$("#orgStation").focus();
					return;
				}
				
				if($("#orderNo").val() == "") {
					$("#orderNo").focus();
					return;
				}

				if($("#shipperName").val() == "") {
					$("#shipperName").focus();
					return;
				}

				if($("#shipperZip").val() == "") {
					$("#shipperZip").focus();
					return;
				}

				if($("#shipperAddr").val() == "") {
					$("#shippperAddr").focus();
					return;
				}

				if($("#shipperTel").val() == "") {
					$("#shipperTel").focus();
					return;
				}

				if($("#cneeName").val() == "") {
					$("#cneeName").focus();
					return;
				}

				if($("#cneeState").val() == "") {
					$("#cneeState").focus();
					return;
				}

				if($("#cneeCity").val() == "") {
					$("#cneeCity").focus();
					return;
				}

				if($("#cneeAddr").val() == "") {
					$("#cneeAddr").focus();
					return;
				}

				if($("#cneeZip").val() == "") {
					$("#cneeZip").focus();
					return;
				}

				if($("#cneeTel").val() == "") {
					$("#cneeTel").focus();
					return;
				}

				if($("input[name='taxType']:checked").val() == "Y") {
					for (var i = 0; i < $(".taxTypeValue").size(); i++) {
						if ($($(".taxTypeValue")[i]).val().length == 0) {
							alert("위약반송 필수 서류가 없습니다.");
							return;
						}
					}
				}

				$("input[name='itemDetail[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})

				$("input[name='itemCnt[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})

				$("input[name='unitValue[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})

				$("input[name='makeCntry[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})

				$("input[name='hsCode[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})

				$("input[name='itemImgUrl[]']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return;
					}
				})
				
				if($("#orderDate").val() == "") {
					$("#orderDate").val(orderDate);
				} 

				$("#returnForm").attr("action", "/cstmr/return/orderIn");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
			})
		})
		
		function fn_delete(obj) {
			if ($(".panel").length > 1) {
				obj.closest('.panel').remove();
			} else {
				alert("최소 1개의 상품정보를 입력해야 합니다.");
			}
		}

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
</body>
</html>