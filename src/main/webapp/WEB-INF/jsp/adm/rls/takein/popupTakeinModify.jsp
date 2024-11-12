<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
	<style type="text/css">
	
</style>
	<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								주문 정보
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						 <form id="registForm" name="registForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div style="width: 50%; float: left;">
									<table class="table table-bordered" style="width:760px;">
									  	<thead>
										  	<tr>
										  		<th colspan=4>발송 정보</th>
										  	</tr>
											<tr>
												<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>UserID</th>
												<td style="padding:0px;width:200px;">
													<input id="userId" name="userId" class="onlyEN" style="width:100%;" type="text" readonly value="${takeinInfo.userId}">
												</td>
												<th colspan=2>
												</th>
											</tr>
											<tr>
												<th class="center colorBlack"  style="width:100px;"><span style="color:red;">* </span>주문번호</th>
												<td style="padding:0px;width:200px;">
													<input type="text" style="width:100%;" id="orderNo" name="orderNo" value="${takeinInfo.orderNo}" >
												</td>
												<th class="center" ><span style="color:red;">* </span>주문날짜</th>
												<td style="padding:0px;width:200px;vertical-align: middle">
													<input type="text" id="orderDate" name="orderDate" style="width:100%" value="${takeinInfo.orderDate}">
												</td>
											</tr>
											<tr>
												<th class="center colorBlack"  style="width:140px;"><span style="color:red;">* </span>출발도시 코드</th>
												<td style="padding:0px;width:200px;" id="orgStationTd">
													<input list="userOrgStation" class="form-control" name="orgStation" id="orgStation" value="${takeinInfo.orgStation}">
												</td>
												<th class="center" style="width:140px;"><span style="color:red;">* </span>도착국가</th>
												<td style="padding:0px;width:200px;vertical-align: middle" id="dstnNationTd">
													<input list="nationList" class="form-control" name="dstnNation" id="dstnNation" value="${takeinInfo.dstnEName}">
												</td>
											</tr>
											<tr>
												<th class="center" >송하인 명</th>
												<td style="padding:0px;" >
													<input type="text" style="width:100%;" name="shipperName" id="shipperName" value="${takeinInfo.shipperName}">
												</td>
												<th colspan=2>
												</th>
											</tr>
											<tr>
												<th class="center">전화번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperTel" name="shipperTel" value="${takeinInfo.shipperTel}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
												<th class="center">휴대전화 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperHp" name="shipperHp" value="${takeinInfo.shipperHp}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
											</tr>
											<tr>
												<th class="center">E-mail</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperEmail" name="shipperEmail" value="${takeinInfo.shipperEmail}">
												</td>
												<th class="center">우편주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperZip" name="shipperZip" value="${takeinInfo.shipperZip}">
												</td>
											</tr>
											<tr>
												<th class="center">도시</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperCity" name="shipperCity" value="${takeinInfo.shipperCity}">
												</td>
												<th class="center">주</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperState" name="shipperState" value="${takeinInfo.shipperState}">
												</td>
											</tr>
											<tr>
												<th class="center">주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperAddr" name="shipperAddr" value="${takeinInfo.shipperAddr}">
												</td>
												<th class="center">상세주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="shipperAddrDetail" name="shipperAddrDetail" value="${takeinInfo.shipperAddrDetail}">
												</td>
											</tr>
										  	<tr>
										  		<th colspan=4>수취 정보</th>
										  	</tr>
											<tr>
												<th class="center" ><span style="color:red;">* </span>수취인 명</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeName" name="cneeName" value="${takeinInfo.cneeName}" >
												</td>
												<th colspan=2></th>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>전화번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeTel" name="cneeTel" value="${takeinInfo.cneeTel}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
												<th class="center">휴대전화 번호</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeHp" name="cneeHp" value="${takeinInfo.cneeHp}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</td>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>E-mail</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeEmail" name="cneeEmail" value="${takeinInfo.cneeEmail}" >
												</td>
												<th class="center"><span style="color:red;">* </span>우편주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeZip" name="cneeZip" value="${takeinInfo.cneeZip}">
												</td>
											</tr>
											<tr>
												<th class="center">도시</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeCity" name="cneeCity" value="${takeinInfo.cneeCity}">
												</td>
												<th class="center">주</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeState" name="cneeState" value="${takeinInfo.cneeState}">
												</td>
											</tr>
											<tr>
												<th class="center"><span style="color:red;">* </span>주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeAddr" name="cneeAddr" value="${takeinInfo.cneeAddr}">
												</td>
												<th class="center">상세주소</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="cneeAddrDetail" name="cneeAddrDetail" value="${takeinInfo.cneeAddrDetail}">
												</td>
											</tr>
											<tr>
												<th class="center">수취인 요청사항</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="dlvReqMsg" name="dlvReqMsg" value="${takeinInfo.dlvReqMsg}">
												</td>
												<th class="center">구매 사이트</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="buySite" name="buySite" value="${takeinInfo.buySite}">
												</td>
											</tr>
											<tr>
												<th class="center">입고메모</th>
												<td style="padding:0px;">
													<input type="text" style="width:100%;" id="whReqMsg" name="whReqMsg" value="${takeinInfo.whReqMsg}">
												</td>
												<th colspan=2></th>
											</tr>
										</thead>
									</table>
								</div>
								<br>
								<div style="width: 48%; float: right;">
									<div class="col-xs-7 col-md-7" style="text-align: left; padding-left:0;">
										<table class="table table-bordered" style="max-width:400px;">
											<thead>
												<tr>
													<th class="center colorBlack"  style="width:120px;padding:0px;" ><span style="color:red;">* </span>CusItemCode</th>
													<th class="center colorBlack" style="padding:0px;">
														<input type="text" id="cusItemCodeIn" style="width:100%">
													</th>
													<th class="center colorBlack"  style="padding:0px;" >
														<input type="button" id= "btnItemIn" value="등록">
													</th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="col-xs-5 col-md-5" style="text-align: right;vertical-align: middle; padding-right:0">
										<input type="button" class="btn btn-white btn-inverse btn-xs" id="btnUserItems" value="상품리스트보기">
									</div>
									<table id="itemInfo" class="table table-bordered" style="width:760px;">
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
										<%-- <tbody>
											<c:forEach items="${takeinItemList}" var="takeinItemList" varStatus="status">
												<tr>
													<td style="padding:0px; width:80px; height: 40px;">
														<input type="text" style="width:80px; height:40tpx;" class="takeInCode" name="takeInCode" value="${takeinItemList.takeInCode}" readonly>
													</td>
													<td style="padding:0px; width:80px; height: 40px;">
														<input type="text" style="width:80px; height:40px;" class="cusItemCode" name="cusItemCode" value="${takeinItemList.cusItemCode}" readonly>
													</td>
													<td style="padding:0px; width:280px; height:40px;">
														<input type="text" style="width:280px; height:40px;" class="itemDetail" name="itemDetail" value="${takeinItemList.itemDetail}" readonly>
													</td>
													<td style="padding:0px; height:40px;">
														<input type="number" style="width:80px; height:40px; text-align:right;" class="itemCnt" name="itemCnt" value="${takeinItemList.itemCnt}">
													</td>
													<td style="padding:0px; height:40px;">
														<input type="number" style="width:80px; height:40px; text-align:right" class="unitValue" name="unitValue" value="${takeinItemList.unitValue}">
													</td>
													<td>
														<input type="button" class="btnTrDel" value="삭제">
													</td>
												</tr>
											</c:forEach>
										</tbody> --%>
											<tfoot>
											<tr>
												<td colspan=5 style="text-align:right">
													<input type="button" id="btnUpdate" value="수정" />
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		
		<!-- script addon start -->
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {

				})
			})
			
		</script>
	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
		

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#orderDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>
		

	</body>
</html>
