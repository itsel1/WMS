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
	margin-top:10px;
	margin-left:4px;
	border-collapse : unset !important;
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

.searchTable {
	width: 40%;
	border: solid 1px #ddd;
	background: #E4E6E9;
	display: inline-table;
}

.sellerIdTable {
	width: 15%;
	border: solid 1px #ddd;
	background: #E4E6E9;
	display: inline-table;
}

.searchTable td {
	border: solid 1px #ddd;
	padding-right: 8px;
}

.search_input {
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

#koblNo_btn {
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

#koblNo_btn:hover {
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
	width: 150px;
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
	border: solid 1px #bebebe;
	box-shadow: 1px 1px 1px #ddd;
}

.cancel_info td {
	border: solid 1px #ccc;
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
	box-shadow: 1px 1px 1px #ddd;
}

.goods_info td {
	border: solid 1px #ccc;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

.remand_info {
	width: 90%;
	border: solid 1px #ddd;
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.remand_info td {
	border: solid 1px #ccc;
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
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.return_type td {
	border: solid 1px #ccc;
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
	<title>반품 접수 </title>
	
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
							<a>반품 관리</a> 
							<span class="mx-2 mb-0">/</span> 
							<strong class="text-black">반품 접수</strong>
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
					<h2 style="margin-left:4px;">반품 접수</h2>
					<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
			<!-- Main container Start-->
					<div class="main-container ace-save-state" id="main-container">
						<script type="text/javascript">
							try{ace.settings.loadState('main-container')}catch(e){}
						</script>
						
						
						<div class="main-content">
							<table class="noticeTable">
					<!-- 반품 사용 안내서 -->
								<tr>
									<th style="font-size: 28px; font-weight: normal; text-align: left; padding: 10px 0 14px 0; background: white;">
										반품 사용 안내서
									</th>
								</tr>
								<tr>
									<td>
										<textarea id="notice_text" readonly style="line-height: 1.4;">
ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.

다만 아래의 경우에는 예외로 합니다.
 - 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
 - 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우
										</textarea>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<label for="agreement_check"> <input type="checkbox" id="agreement_check" name="agreement_check"> 
												<font style="font-size: 14px; font-weight: bold;">동의 합니다.</font>
											</label>
										</p>
									</td>
								</tr>
							</table>
							<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="get" action="/return/returnCourier">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="main_div">
									<br>
									<br>
									<div class="row">
										<p style="font-size: 20px; font-weight: bold; color: black;">배송 정보 찾기</p>
										<!-- <p>&nbsp;aci e-shop 배송정보 검색하기&nbsp;&nbsp;<input type="checkbox" id="eshopAdd" name="eshopAdd"/></p> -->
										
										<table class="hide sellerIdTable">
											<tr>
												<td>
													<input type="text" name="sellerId" value="${sellerId }" id="sellerId" class="search_input" placeholder="Seller Id를 입력해주세요." >
													<p><c:if test="${eMsg ne '0' }">Seller Id를 확인해주세요</c:if></p>
												</td>
											</tr>
										</table>										  
										<table class="searchTable">
											<tr>
												<td>
													<input type="text" name="koblNo" id="koblNo" value="${koblNo }" class="search_input" placeholder="운송장 번호를 입력해주세요." >
													<p><c:if test="${eMsg ne '0' }">운송장 번호를 확인해주세요</c:if></p>
												</td>
												<td width="100">
													<label for="hawbBtn"></label> <input type="button" name="koblNo_btn" id="koblNo_btn" value="검색" onClick="searchKobl()"> 
													<input type="hidden" id = "nno" name="nno" value="${nno}">
												</td>
											</tr>
										</table>
									</div>
									<br> <br>
									<div id="hidenDiv" name="hidenDiv" class="hide">
										<div class="infoDiv">
											<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">출발지/도착지: KR  / ${rtn.orgStation}</font></label>
											<div class="radio_div">
													<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">픽업 방법</font></label> 
													<input type="radio" name="pickType" value="B" class="rtn_radio">회수 요청
													<input type="radio" name="pickType" value="A" class="rtn_radio">직접 발송
											</div>
											
											<table class="infoTable hide" id="reTrk" name="reTrk">
												<tr>
													<th class="tableHeader" colspan="2">반송 택배 정보</th>
												</tr>
												<tr>
													<td class="td_name">택배 회사</td>
													<td>
														<input type="text" class = "backYellow info_input" name="reTrkCom" id="reTrkCom" value="${data.reTrkCom }" >
													</td>
												</tr>
												<tr>
													<td class="td_name">송장 번호</td>
													<td><input type="text" class = "backYellow info_input" name="reTrkNo" id="reTrkNo" value="${data.reTrkNo }"></td>
												</tr>
												<tr>
													<td class="td_name">등록 날짜</td>
													<td><input type="text" class = "backYellow info_input" name="A002Date" id="A002Date" value="${data.a002Date }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
												</tr>
											</table>
											
											<table class="infoTable">
												<tr>
													<th class="tableHeader" colspan="2">반품 담당자 정보</th>
												</tr>
												<tr>
													<td class="td_name">이름</td>
													<td>
														<input type="text" class = "backYellow info_input" name="attnName" id="attnName" value="${data.attnName }" placeholder="입력하세요">
													</td>
												</tr>
												<tr>
													<td class="td_name">전화번호</td>
													<td><input type="text" class = "backYellow info_input" name="attnTel" id="attnTel" value="${data.attnTel }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
												</tr>
												<tr>
													<td class="td_name">이메일</td>
													<td><input type="email" class = "backYellow info_input" name="attnEmail" id="attnEmail" value="${data.attnEmail }"></td>
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
														<td><input type="text" class = "info_input" name="pickupEngAddr" id=pickupEngAddr value="${rtn.pickupEngAddr }"></td>
													</c:if>
													<c:if test="${rtn.orgStation ne 'KR' }">
														<td class="td_name">영문 주소</td>
														<td><input type="text" class = "backYellow info_input" name="pickupEngAddr" id=pickupEngAddr value="${rtn.pickupEngAddr }"></td>
													</c:if>
												</tr>
												<tr>
													<td class="td_name">전화번호</td>
													<td><input type="text" class = "backYellow info_input" name="pickupTel" id="pickupTel" value="${rtn.pickupTel }"></td>
													<td class="td_name">휴대전화번호</td>
													<td><input type="text" class = "backYellow info_input" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile }"></td>
												</tr>
											</table>
											<br> <br>
											<table class="infoTable">
												<tr>
													<th class="tableHeader" colspan="4">해외 도착지 정보</th>
												</tr>
												<tr>
													<td class="td_name">회사명</td>
													<td><input type="text" class = "backYellow info_input" name="senderName" id="senderName" value="${rtn.senderName }"></td>
													<td class="td_name">우편번호</td>
													<td><input type="text" class = "backYellow info_input" name="senderZip" id="senderZip" value="${rtn.senderZip}"></td>
												</tr>
												<tr>
													<td class="td_name">주</td>
													<td><input type="text" class = "backYellow info_input" name="senderState" id="senderState" value="${rtn.senderState}"></td>
													<td class="td_name">도시</td>
													<td><input type="text" class = "backYellow info_input" name="senderCity" id="senderCity" value="${rtn.senderCity}"></td>
												</tr>
												<tr>
													<td class="td_name">주소</td>
													<td ><input type="text" class = "backYellow info_input" name="senderAddr" id="senderAddr" value="${rtn.senderAddr }"></td>
													<td class="td_name">전화번호</td>
													<td><input type="text" class = "backYellow info_input" name="senderTel" id="senderTel" value="${rtn.senderTel }" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
												</tr>
												<c:if test="${rtn.orgStation eq 'US' }">
													<tr>
														<td class="td_name">빌딩 명</td>
														<td colspan="3"><input type="text" class = "backYellow info_input" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm }" placeholder="빌딩 명을 반드시 입력하여 주시기 바랍니다."></td>
													</tr>
												</c:if>
												<tr>
													
												</tr>
												<%-- <tr>
													<td class="td_name">휴대전화번호</td>
													<td><input type="text" class = "backYellow info_input" name="senderHp" id="senderHp" value="${rtn.senderHp }"></td>
												</tr> --%>
											</table>
										</div>
										<br> <br> <br>
										<div class="radio_div">
											<label for="rtn_div"><font style="font-size: 18px; font-weight: bold; color: black;">반송구분</font></label> 
											<input type="radio" name="returnType" value="N" class="rtn_radio">일반배송
											<input type="radio" name="returnType" value="E" class="rtn_radio">긴급배송
										</div>
										<div class="row">
											<table class="cancel_info">
												<tr height="38">
													<td class="td_name">취소사유</td>
													<td>
														<input type="radio" name="returnReason" id="returnReason" value="A"> <label for="cncl">단순변심</label> 
														<input type="radio" name="returnReason" id="returnReason" value="B"><label for="cncl">파손</label> 
														<input type="radio" name="returnReason" id="returnReason" value="C"><label for="cncl">불량</label>
														<input type="radio" name="returnReason" id="returnReason" value="D"><label for="cncl">기타</label></td>
												</tr>
												<tr height="20">
													<td class="td_name">취소사유 상세</td>
													<td>
														<input type="text" class = "info_input" name="returnReasonDetail" id="returnReasonDetail">
													</td>
												</tr>
											</table>
										</div>
										<div class="row">
											<table class="remand_info">
												<tr>
													<th class="tableHeader" colspan="3">
														위약반송 정보 <small style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해 주세요</small>
													</th>
												</tr>
												<tr>
													<td class="td_name2">위약 반송</td>
													<td colspan="2">
														<input type="radio" name="taxType" id="taxType" value="Y"><label for="cncl">위약 반송</label>
														<input type="radio" name="taxType" id="taxType" value="N"><label for="cncl">일반 반송</label>
														<input type="hidden" name="taxReturn" id="taxReturn" value="C">
													</td>
												</tr>
												<!-- <tr class="hideTax hide">
													<td class="td_name2">세금 환급금 수취 주체</td>
													<td colspan="2">
														<input type="radio" name="taxReturn" id="taxReturn" value="C"> <label for="cncl">Customer</label>
														<input type="radio" name="taxReturn" id="taxReturn" value="S"> <label for="cncl">Seller</label>
													</td>
												</tr> -->
												<tr class="hideTax hide">
													<td class="td_name2">반송 사유서</td>
													<td style="padding-left: 4px; width: 300px;">
														<input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason">
													</td>
													<td style="text-align: left; padding-left: 4px;">
														<a href="/반송사유서.xlsx" download>[반송사유서 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax hide">
													<td class="td_name2">반품접수 캡쳐본</td>
													<td style="padding-left: 4px;">
														<input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture">
													</td>
													<td></td>
												</tr>
												<tr class="hideTax hide">
													<td class="td_name2">반품 매신져 캡쳐본</td>
													<td style="padding-left: 4px;">
														<input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger">
													</td>
													<td></td>
												</tr>
												<tr class="hideTax hide">
													<td class="td_name2">반품 commercial invoice</td>
													<td style="padding-left: 4px;">
														<input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl">
													</td>
													<td style="text-align: left; padding-left: 4px;">
														<a href="/반품커머셜.xlsx" download>[반품 커머셜 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax hide">
													<td class="td_name2">환급 통장 사본</td>
													<td style="padding-left: 4px;">
														<input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank">
													</td>
													<td></td>
												</tr>
											</table>
											<br>
										</div>
										<!-- <div class="col-xs-12 col-lg-12" style="padding: 3rem!important; width:90%">
											<input type="button" class="button button1" name="addBtns" id="addBtns" value="상품추가" onclick="addDiv()" style="float:right;">
										</div> -->
										<div class="row">
											<c:forEach items="${itemList }" var="item" varStatus="voStatus">
												<div id="addForm">
													<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; width:90%">
														<h4>상품  ${voStatus.index+1}</h4>
														<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
															<div class="profile-user-info  form-group hr-8">
																<div class="profile-info-name subNo" style="width:150px;">SUBNO</div>
																<div class="profile-info-value">
																		<input class="col-xs-12 subNo" type="text" name="subNo" id="subNo" value="${item.subNo}" style="width:200px;" readonly/>
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
																		<input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" style="width:200px;" />
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
												<input type="button" class="button_base b01_simple_rollover" style="width:40%;height: 100px;font-size: medium;" id="registReturn" name="registReturn" value="반송접수">
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
		
		function searchKobl() {
			location.href='/return/returnCourier?koblNo='+$("#koblNo").val();
		}
		
		function getExpress() {
			$("#returnForm").attr("action","return/getExpress?hawbNo="+hawbNo);
			$("#returnForm").attr("method","get");
			$("#returnForm").submit();
		}
		
		function update(){
			let checked = $("#agreement_check").is(":checked");
			if(checked){
				$("#returnForm").attr("action","/return/Page");
				 $("#returnForm").attr("method", "POST");
				 $("#returnForm").submit();
				 alert('신청이 완료되었습니다');
			}
			else {
				alert("반품 사용 안내서에 동의해주세요.")
			} 
		}

		$("#registReturn").on("click",function(e){
			let checked = $("#agreement_check").is(":checked");
			if(checked){
				if(!$('input[name="pickType"]').is(":checked")){
					alert("픽업 방법을 선택해 주세요")
				} else if($(".backYellow").size() != 0){
					alert("필수 등록정보를 입력해 주세요.")
					$($(".backYellow")[0]).focus();
					return;
				} else if($('input[name="taxType"]:checked').val() == "Y") {
					for(var i = 0; i < $(".taxTypeValue").size(); i++){
						if($($(".taxTypeValue")[i]).val().length == 0){
							return alert("위약반송 필수서류가 비어있습니다.")
						}
					}
				}/* else{
				$("#returnForm").attr("action","/return/returnCourier");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
			} */

				$("#returnForm").attr("action","/return/returnCourier");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
			}else{
				alert("반품 사용 안내서에 동의해주세요.")
			}
		});

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
				
				if($("#nno").val().length != 0){
					$("#hidenDiv").toggleClass('hide');
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

				$(".backYellow").focusout(function(){
					if($(this).val().length != 0){
						$(this).removeClass("backYellow");
					}else{
						$(this).addClass("backYellow");
					}
				});`

				$("#eshopAdd").on("change",function(){
					$(".sellerIdTable").toggleClass('hide');
				})
			});
		
		});
		</script>
	</body>
</html>
