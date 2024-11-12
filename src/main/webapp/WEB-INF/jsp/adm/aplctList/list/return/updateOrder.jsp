<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<style>
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
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>반품 상세조회</title>
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
								반품
							</li>
							<li class="active">반품 상세조회</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						
						<div id="inner-content-side" >
							<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="post" action="/return/updateExpress">
								<input type="hidden" name="_method" value="patch"/>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="main_div">
									<br>
									<div class="row">
										<p style="font-size: 20px; font-weight: bold; color: black;">운송장 번호</p>
										<table class="hawbTable">
											<tr>
												<td>
													 <label for="hawbNo"></label> <input type="text" name="koblNo" value="${rtn.koblNo}" id="koblNo" readonly>
													 <input type="hidden" id = "nno" name="nno" value="${rtn.nno}">
												</td>
											</tr>
										</table>
									</div>
									<br> <br>
									<div class="infoDiv">
										<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">출발지/도착지: KR  / ${rtn.dstnNation}</font></label>
										<input type="hidden" name="dstnNation" id="dstnNation" value="${rtn.dstnNation}"/>
										<div class="radio_div">
												<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">픽업 방법</font></label> 
												<input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'B' }">checked</c:if> value="B" class="rtn_radio">회수 요청
												<input type="radio" name="pickType" <c:if test="${rtn.pickType eq 'A' }">checked</c:if> value="A" class="rtn_radio">직접 발송
										</div>
										
										<table class="infoTable" id="reTrk" name="reTrk">
											<tr>
												<th class="tableHeader" colspan="2">반송 택배 정보</th>
											</tr>
											<tr>
												<td class="td_name">택배 회사</td>
												<td>
													<input type="text" class = "backYellow info_input" name="reTrkCom" id="reTrkCom" value="${rtn.reTrkCom }" >
												</td>
											</tr>
											<tr>
												<td class="td_name">송장 번호</td>
												<td><input type="text" class = "backYellow info_input" name="reTrkNo" id="reTrkNo" value="${rtn.reTrkNo }"></td>
											</tr>
											<tr>
												<td class="td_name">등록 날짜</td>
												<td><input type="text" class = "backYellow info_input" name="A002Date" id="A002Date" value="${rtn.a002Date }"></td>
											</tr>
										</table>
										
										<table class="infoTable">
											<tr>
												<th class="tableHeader" colspan="2">반품 담당자 정보</th>
											</tr>
											<tr>
												<td class="td_name">이름</td>
												<td><input type="text" name="attnName" id="attnName" class="info_input backYellow" value="${rtn.attnName }"
													></td>
											</tr>
											<tr>
												<td class="td_name">전화번호</td>
												<td><input type="text" name="attnTel" id="attnTel" class="info_input backYellow" value="${rtn.attnTel }"></td>
											</tr>
											<tr>
												<td class="td_name">이메일</td>
												<td><input type="email" name="attnEmail" id="attnEmail" class="info_input backYellow" value="${rtn.attnEmail }"></td>
											</tr>
										</table>
										<br> <br>
										<table class="infoTable">
											<tr>
												<th class="tableHeader" colspan="4">반품 발송 정보</th>
											</tr>
											<tr>
												<td class="td_name">자사 정산 ID</td>
												<td><input type="text" class = "info_input" name="calculateId" id="calculateId" value="${rtn.calculateId }" placeholder="미입력 시, 사용자 ID로 부여 됩니다."></td>
												<td class="td_name">주문 접수 번호</td>
												<td><input type="text" class = "info_input" name="orderReference" id="orderReference" value="${rtn.orderReference }" placeholder="미입력 시, 자동으로 부여 됩니다."></td>
											</tr>
											<tr>
												<td class="td_name">주문번호</td>
												<td><input type="text" class = "backYellow info_input" name="orderNo" id="orderNo" value="${rtn.orderNo }"></td>
												<td class="td_name">주문날짜</td>
												<td><input type="text" class = "backYellow info_input" name="orderDate" id="orderDate" value="${rtn.orderDate }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
											</tr>
											<tr>
												<td class="td_name">이름</td>
												<td><input type="text" class = "backYellow info_input" name="pickupName" id="pickupName" value="${rtn.pickupName }"></td>
												<td class="td_name">우편번호</td>
												<td><input type="text" class = "backYellow info_input" name="pickupZip" id="pickupZip" value="${rtn.pickupZip }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
											</tr>
											<tr>
												<td class="td_name">주소</td>
												<td><input type="text" class = "backYellow info_input" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr }"></td>
												<c:if test="${rtn.orgStation eq 'KR' }">
													<td class="td_name">영문 주소</td>
													<td><input type="text" class = "info_input" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr }"></td>
												</c:if>
												<c:if test="${rtn.orgStation ne 'KR' }">
													<td class="td_name">영문 주소</td>
													<td><input type="text" class = "backYellow info_input" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr }"></td>
												</c:if>
											</tr>
											<tr>
												<td class="td_name">전화번호</td>
												<td><input type="text" class = "backYellow info_input" name="pickupTel" id="pickupTel" value="${rtn.pickupTel }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
												<td class="td_name">휴대전화번호</td>
												<td><input type="text" class = "backYellow info_input" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
											</tr>
										</table>
										<br> <br>
										<table class="infoTable">
											<tr>
												<th class="tableHeader" colspan="2">해외 도착지 정보</th>
											</tr>
											<tr>
												<td class="td_name">회사명</td>
												<td><input type="text" class = "backYellow info_input" name="senderName" id="senderName" value="${rtn.senderName }"></td>
											</tr>
											<tr>
												<td class="td_name">우편번호</td>
												<td><input type="text" class = "backYellow info_input" name="senderZip" id="senderZip" value="${rtn.senderZip}"></td>
											</tr>
											<tr>
												<td class="td_name">주</td>
												<td><input type="text" class = "backYellow info_input" name="senderState" id="senderState" value="${rtn.senderState}"></td>
											</tr>
											<tr>
												<td class="td_name">도시</td>
												<td><input type="text" class = "backYellow info_input" name="senderCity" id="senderCity" value="${rtn.senderCity}"></td>
											</tr>
											<tr>
												<td class="td_name">주소</td>
												<c:choose>
													<c:when test="${!empty rtn.senderAddr && empty rtn.senderAddrDetail}">
														<td><input type="text" class = "backYellow info_input" name="senderAddr" id="senderAddr" value="${rtn.senderAddr}"></td>
													</c:when>
													<c:when test="${empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
														<td><input type="text" class = "backYellow info_input" name="senderAddr" id="senderAddr" value="${rtn.senderAddrDetail}"></td>
													</c:when>
													<c:when test="${!empty rtn.senderAddr && !empty rtn.senderAddrDetail}">
														<td><input type="text" class = "backYellow info_input" name="senderAddr" id="senderAddr" value="${rtn.senderAddr} ${rtn.senderAddrDetail}"></td>
													</c:when>
													<c:otherwise>
														<td><input type="text" class = "backYellow info_input" name="senderAddr" id="senderAddr" value=""></td>
													</c:otherwise>
												</c:choose>
											</tr>
											<c:if test="${rtn.orgStation eq 'US' }">
												<tr>
													<td class="td_name">빌딩 명</td>
													<td><input type="text" class = "backYellow info_input" name="senderBuildNm" id="senderBuildNm" value="" placeholder="빌딩 명을 반드시 입력하여 주시기 바랍니다."></td>
												</tr>
											</c:if>
											<tr>
												<td class="td_name">전화번호</td>
												<td><input type="text" class = "backYellow info_input" name="senderTel" id="senderTel" value="${rtn.senderTel }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
											</tr>
											<%-- <tr>
												<td class="td_name">휴대전화번호</td>
												<td><input type="text" class = "backYellow info_input" name="senderHp" id="senderHp" value="${rtn.senderHp }"></td>
											</tr> --%>
										</table>
									</div>
									<br> <br> <br>
									<div class="radio_div">
										<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">반송 구분</font></label> 
										<input type="radio" name="returnType" <c:if test="${rtn.returnType eq 'N'}"> checked </c:if> value="N" class="rtn_radio" id="returnType1">일반배송
										<input type="radio" name="returnType" <c:if test="${rtn.returnType eq 'E'}"> checked </c:if> value="E" class="rtn_radio" id="returnType2">긴급배송
									
									</div>
									<div class="row">
										<table class="cancel_info">
											<tr height="38">
												<td class="td_name">취소사유</td>
												<td>
													<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'A'}"> checked </c:if> value="A"> <label for="cncl">단순변심</label> 
													<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'B'}"> checked </c:if> value="B"><label for="cncl">파손</label> 
													<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'C'}"> checked </c:if> value="C"><label for="cncl">불량</label>
													<input type="radio" name="returnReason" id="returnReason" <c:if test="${rtn.returnReason eq 'D'}"> checked </c:if> value="D"><label for="cncl">기타</label>
												</td>
											</tr>
											<tr height="20">
												<td class="td_name">취소사유 상세</td>
												<td>
													<input type="text" class="info_input" name="returnReasonDetail" id="returnReasonDetail" value="${rtn.returnReasonDetail }">
												</td>
											</tr>
										</table>
									</div>
									<div class="row">
										<table class="remand_info">
											<tr>
												<th class="tableHeader" colspan="3">위약반송 정보
												<small style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해 주세요</small></th>
											</tr>
											<tr>
												<td class="td_name2">위약 반송</td>
												<td colspan="2">
													<input type="radio" name="taxType" id="taxType" <c:if test="${rtn.taxType eq 'Y' }">checked</c:if> value="Y"><label for="cncl">위약 반송</label>
													<input type="radio" name="taxType" id="taxType" <c:if test="${rtn.taxType eq 'N' }">checked</c:if> value="N"><label for="cncl">일반 반송</label>
													<input type="hidden" name="taxReturn" id="taxReturn" value="C">
												</td>
											</tr>
											<%-- <tr class="hideTax hide">
												<td class="td_name2">세금 환급금 수취 주체</td>
												<td colspan="2">
													<input type="radio" name="taxReturn" id="taxReturn" <c:if test="${rtn.taxReturn eq 'C' }">checked</c:if> value="C"> <label for="cncl">Customer</label>
													<input type="radio" name="taxReturn" id="taxReturn" <c:if test="${rtn.taxReturn eq 'S' }">checked</c:if> value="S"> <label for="cncl">Seller</label>
												</td>
											</tr> --%>
											<tr class="hideTax hide">
												<td class="td_name2">반송사유서</td>
												<td style="padding-left: 4px; width: 300px;">
													<input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason">
												<td style="text-align: left; padding-left: 4px;">
													<a href='${rtn.fileReason}' target='_blank' download>[반송사유서 다운로드]</a>
												</td>
											</tr>
											<tr class="hideTax hide">
												<td class="td_name2">반품접수 캡쳐본</td>
												<td style="padding-left: 4px;">
													<input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture">
												</td>
												<td style="text-align: left; padding-left: 4px;">
													<a href='${rtn.fileCapture}' target='_blank' download>[반품접수 캡쳐본 다운로드]</a>
												</td>
											</tr>
											<tr class="hideTax hide">
												<td class="td_name2">반품 매신져 캡쳐본</td>
												<td style="padding-left: 4px;">
													<input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger">
												</td>
												<td style="text-align: left; padding-left: 4px;">
													<a href='${rtn.fileMessenger}' target='_blank' download>[반품 매신져 캡쳐본 다운로드]</a>
												</td>
											</tr>
											<tr class="hideTax hide">
												<td class="td_name2">반품 commercial invoice</td>
												<td style="padding-left: 4px;">
													<input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl">
												</td>
												<td style="text-align: left; padding-left: 4px;">
													<a href='${rtn.fileCl}' target='_blank' download>[반품 commercial invoice 다운로드]</a>
												</td>
											</tr>
											<tr class="hideTax hide">
												<td class="td_name2">환급 통장 사본</td>
												<td style="padding-left: 4px;">
													<input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank">
												</td>
												<td style="text-align: left; padding-left: 4px;">
													<a href='${rtn.fileCopyBank}' target='_blank' download>[환급 통장 사본 다운로드]</a>
												</td>
											</tr>
										</table>
										<br>
									</div>
									<div class="row">
										<!-- <div id="accordion" class="accordion-style1 panel-group">
										<div class="col-xs-12 col-lg-12" style="padding: 3rem!important; width:90%">
											<input type="button" class="button button1" name="addBtns" id="addBtns" value="상품추가" onclick="addDiv()" style="float:right;">
										</div> -->
										<c:forEach items="${itemList}" var="item" varStatus="voStatus">
											<div id="addForm">
												<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; width:90%">
													<h4>상품  ${voStatus.index+1}</h4>
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">SUBNO</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="subNo" id="subNo" value="${item.subNo}" style="width:200px;" readonly/>
															</div>
															<div class="profile-info-name" style="width:150px;">상품명</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" style="width:90%;" />
															</div>
														</div>
													</div>
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">브랜드</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="brand" id="brand" value="${item.brand}" style="width:90%;" />
															</div>
															<div class="profile-info-name" style="width:150px;">상품 무게</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="itemWta" id="itemWta" value="${item.itemWta}" style="width:90%;" />
															</div>
															<div class="profile-info-name" style="width:150px;">무게 단위</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" style="width:100%;" />
															</div>
														</div>
													</div>
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">상품 개수</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" style="width:100%; "/>
															</div>
															<div class="profile-info-name" style="width:150px;">상품가격</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="unitValue" id="unitValue" value="${item.unitValue}" style="width:100%;"/>
															</div>
															<div class="profile-info-name" style="width:150px;">통화</div>
															<div class="profile-info-value">
																<input class="col-xs-12 backYellow" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" style="width:100%;"/>																		</div>
														</div>
													</div>
														
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">	
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">제조국</div>
															<div class="profile-info-value">
																	<input class="col-xs-12 backYellow" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" style="width:200px;" />
															</div>
															<div class="profile-info-name" style="width:150px;">제조회사</div>
															<div class="profile-info-value">
																	<input class="col-xs-12 backYellow" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" style="width:200px;" />
															</div>
															<div class="profile-info-name" style="width:150px;">HS CODE</div>
															<div class="profile-info-value">
																	<input class="col-xs-12 backYellow" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" style="width:200px;" />
															</div>
														</div>
													</div>
													<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">상품 URL</div>
															<div class="profile-info-value">
																	<input class="col-xs-12 backYellow" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" style="width:80%;" />
															</div>
														</div>		
														<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">		
														</div>
														<div class="profile-user-info  form-group hr-8">
															<div class="profile-info-name" style="width:150px;">상품 이미지 URL</div>
															<div class="profile-info-value">
																	<input class="col-xs-12 backYellow" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" style="width:80%" />
															</div>
														</div>
														<%-- <input type="button" class="button" onclick="getIndex2(${voStatus.index})" name="deleteItemBtn" value="삭제" class="button button2" id="deleteBtn" style="float:right"> --%>
													</div>	
												</div>
											</div>
										</c:forEach>
									</div>
									<div>
										<table class="return_type">
											<tr height="30">
												<td colspan="2" class="td_name2" style="text-align: left; padding-left: 4px; font-size: 16px;">
													<a href="" id="return_a">[반품 단가표]</a>
												</td>
											</tr>
											<tr height="30">
												<td class="td_name2">반품유형 선택</td>
												<td style="font-size: 14px;">
													<input type="radio" name="rtn_type" checked><label for="rtn_type">프리미엄 반품서비스</label>
												</td>
											</tr>
											<tr height="100">
												<td class="td_name2">입고 요청사항</td>
												<td><input type="text" name="whMsg" id="whMsg" class="comm_text"></td>
											</tr>
										</table>
										<br/>
										<div class="rtnBtn_div" style="text-align: center;">
											<input type="button" class="button_base b01_simple_rollover" style="width:10%;height: 50px;font-size: medium;" id="backBtn" name="backBtn" value="뒤로가기">
											<input type="button" class="button_base b01_simple_rollover" style="width:10%;height: 50px;font-size: medium;" id="updateReturn" name="updateReturn" value="정보 수정">
										</div>
									</div>
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
			});
			
			$("#updateReturn").on("click",function(e){
				if(!$('input[name="pickType"]').is(":checked")){
					alert("픽업 방법을 선택해 주세요")
				}
				else if($(".backYellow").size() != 0){
					alert("필수 등록정보를 입력해 주세요.")
					$($(".backYellow")[0]).focus();
					return;
				}/* else{
					$("#returnForm").attr("action","/return/returnCourier");
					$("#returnForm").submit();
				} */

				$("#returnForm").attr("action","/mngr/aplctList/return/"+$("#orderReference").val());
				$("#returnForm").submit();
			});

			$('input[name="pickType"]').on('click',function(e){
				if($(this).val()=='A'){
					$("#reTrkCom").addClass("backYellow");
					$("#reTrkNo").addClass("backYellow");
					$("#A002Date").addClass("backYellow");
				}else{
					$("#reTrkCom").removeClass("backYellow");
					$("#reTrkNo").removeClass("backYellow");
					$("#A002Date").removeClass("backYellow");
				}
			});

			for(var i = 0; i < $(".backYellow").size(); i++){
				if($($(".backYellow")[i]).val().length != 0){
					$($(".backYellow")[i]).removeClass('backYellow');
					i = i-1;
				}
			}
			
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thOne-2").toggleClass('active');   
					
				});
	
				if($("#taxType").val()=='Y'){
					$(".hideTax").removeClass('hide');
				}
				
	
				for(var i = 0; i < $(".backYellow").size(); i++){
					if($($(".backYellow")[i]).val().length != 0){
						$($(".backYellow")[i]).removeClass('backYellow');
						i = i-1;
					}
				}
		});
		</script>
		<!-- script addon end -->
	</body>
</html>
