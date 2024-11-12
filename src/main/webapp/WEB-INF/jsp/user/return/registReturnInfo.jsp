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

<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');

.table {
	margin: 0 auto;
}

.table thead th {
	background: rgba(76, 144, 189, 0.72);
	color:#fff;
	text-align: center;
}

.tab-content * {
	font-family: 'Nanum Gothic', sans-serif;
}

#item_info {
	font-family: 'Nanum Gothic', sans-serif;
}

#shipping_table {
	border:none;
}

#wms_btn {
	border: none;
	border-radius: 5px;
	transition: all 0.2s;
	background: rgba(76, 144, 189, 0.8);
	color: #fff;
	padding: 5px 10px;
	margin-left: 10px;
}

#wms_btn:hover {
	background: #4c90bd;
}

#normal_btn {
	border: none;
	border-radius: 5px;
	transition: all 0.2s;
	background: #00AE68;
	color: #fff;
	padding: 5px 10px;
}

#normal_btn:hover {
	background: #21825B;
}

#eshop_btn {
	border: none;
	border-radius: 5px;
	transition: all 0.2s;
	background: #f5e400;
	color: #fff;
	padding: 5px 10px;
}

#eshop_btn:hover {
	background: #8f8500;
}

label {
	margin-top: 2px;
}

input[type="radio"] {
	vertical-align: text-top;
}

.table {
	border: 1px solid #ddd;
	border-collapse: collapse;
}

.table tbody th {
	background: rgba(204, 204, 204, 0.19);
	width: 10%;
	text-align: center;
}

.table input {
	border: none;
	outline: none;
}


.invInfo_btn {
	padding: 5px 10px;
	border-radius:5px;
	color: #fff;
	border: none;
	background: rgba(76, 144, 189, 0.72);
	margin-left:2px;
}

.invInfo_btn:hover {
	color: #fff;
	background:rgba(76, 144, 189, 0.5);
}

input[type="file"]:focus {
	outline:none;
	border:none;
}

input[type="file"]:hover {
	cursor: pointer;
}

#registReturn {
	width: 100px;
	height: 40px;
	cursor: pointer;
	color: #fff;
	background-color: #527d96;
	border: none;
	box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
	transition: 0.3s;
	font-size: 14px;
}

#registReturn:hover {
	background-color: rgba(82, 125, 150, 0.9);
	box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
}

.modal.modal-center {
  text-align: center;
}

@media screen and (min-width: 768px) { 
  .modal.modal-center:before {
    display: inline-block;
    vertical-align: middle;
    content: " ";
    height: 100%;
  }
}

.modal-dialog.modal-center {
  display: inline-block;
  text-align: left;
  vertical-align: middle; 
}
 
#agreeModal {
 	width: 40%;
}

.modal-body {
	font-family: 'Nanum Gothic', sans-serif;
}

.modal-header {
	background: rgba(76, 144, 189, 1);
	font-weight: bold;
	color: #fff;
}

.modal-footer {
	background: white;
}

input[name="aci_shipping"] {
	margin-top: -1px;
 	vertical-align: middle;
}

</style>
<!-- basic scripts -->
</head>
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
						<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품 접수</strong>
					</div>
	        	</div>
	      	</div>
	    </div>
    </div>
	<form name="returnForm" id="returnForm" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="addCntVal" id="addCntVal" value=""/>
		<input type="hidden" name="wmsOrNot" id="wmsOrNot" value=""/>
		<div class="container">
	       <div class="page-content noneArea">
				<br/>
				<div class="tab-content" id="main_tab" style="margin:0 auto;">
					<input type="hidden" name="koblNo" id="koblNo" value="">
					<h2 style="margin-left:10px;">반품 접수</h2>
					<br/>
					<div class="info_btn">
						<input type="button" id="wms_btn" onclick="fn_getWMSInfo()" value="ACI 배송 정보 가져오기">
					</div>
					<div style="margin-left:10px; height:20px; margin-bottom:20px;" id="aci_radio" class="hide">
						<br/>
						<input type="radio" name="aci_shipping" id="wms_shipping" value="wms"><label for="wms_shipping" style="margin-left:5px;">WMS</label>&nbsp;&nbsp;
						<input type="radio" name="aci_shipping" id="eshop_shipping" value="eshop"><label for="eshop_shipping" style="margin-left:5px;">Eshop</label>
					</div>
					<br/>
					<div class="normal_info">
						<table style="width:auto;" id="shipping_table">
							<tr class="normal_tr">
								<th style="width:100px; text-align:center;"><label class="red" style="margin-right:2px;">*</label>운송장번호</th>
								<td style="padding:0px; width:300px; height:35px;">
									<input type="text" id="koblNo_normal" style="width:100%; height:30px;">
								</td>
							</tr>
							<tr class="wms_tr hide">
								<th style="width:100px; text-align:center;"><label class="red" style="margin-right:2px;">*</label>운송장번호</th>
								<td style="padding:0px; width:300px; height:35px;">
									<input type="text" id="koblNo_wms" style="width:78%; height:30px; vertical-align:middle;">
									<input type="button" onclick="fn_getOrderInfo()" style="width:20%; height:28px; vertical-align:middle;" value="검색">
								</td>
							</tr>
							<tr class="eshop_id hide">
								<th style="width:100px; text-align:center;"><label class="red" style="margin-right:2px;">*</label>Eshop ID</th>
								<td style="padding:0px; width:300px; height:35px;">
									<input type="text" id="eshopId" name="eshopId" style="width:100%; height:30px;">
								</td>
							</tr>
							<tr class="eshop_api hide">
								<th style="width:100px; text-align:center;"><label class="red" style="margin-right:2px;">*</label>API key</th>
								<td style="padding:0px; width:300px; height:35px;">
									<input type="text" id="eshopApiKey" name="eshopApiKey" style="width:100%; height:30px;">
								</td>
							</tr>
							<tr>
								<th style="width:100px; text-align:center;"><font class="red" style="margin-right:2px;">*</font>출발지</th>
								<td style="padding:0px; width:300px; height:35px;">
									<select class="chosen-select-start form-control tag-input-style nations" id="orgStation" name="orgStation" style="width:100%; height:100%;">
										<c:forEach items="${nationList}" var="dstnNationList">
											<option <c:if test="${dstnNationList.nationCode eq 'KR'}"> selected</c:if> value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th style="width:100px; text-align:center;"><font class="red" style="margin-right:2px;">*</font>도착지</th>
								<td style="padding:0px; width:300px; height:35px;">
									<select class="chosen-select-start form-control tag-input-style nations" id="dstnNation" name="dstnNation" style="width:100%; height:100%;">
										<c:forEach items="${nationList}" var="dstnNationList">
											<option value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<br/><br/>
					<div class="shipping_info">
						<div style="float:right;">
							<p class="red" style="font-weight:bold;">* <font style="color:black;font-weight:normal;">필수 입력값입니다</font></p>
						</div>
						<table style="margin-left:20px;">
							<tr>
								<th style="font-size:18px;"><font class="red" style="margin-right:5px;">*</font>픽업 방법</th>
								<td style="padding-left:14px; width:200px; vertical-align:middle;">
									<input type="radio" name="pickType" value="B" id="pickType_B" checked>
									<label for="pickType_B" class="radio_label">회수 요청</label>
									<input type="radio" name="pickType" value="A" id="pickType_A" style="margin-left:10px;">
									<label for="pickType_A">직접 발송</label>
								</td>
							</tr>
						</table>
						<br/>
						<table class="table" style="width:98%;">
							<thead>
								<tr>
									<th colspan="2">반송 택배 정보</th>
									<th colspan="2">반품 담당자 정보</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th style="width:10%; text-align:center;"><font class="red hide" id="reTrkCom_ft" style="margin-right:5px;">*</font>택배 회사</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%; height:36px;" disabled name="reTrkCom" id="reTrkCom">
									</td>
									<th style="width:10%; text-align:center;">이름</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%; height:36px;" name="attnName" id="attnName">
									</td>
								</tr>
								<tr>
									<th style="width:10%; text-align:center;"><font class="red hide" id="reTrkNo_ft" style="margin-right:5px;">*</font>송장 번호</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%; height:36px;" disabled name="reTrkNo" id="reTrkNo">
									</td>
									<th style="width:10%; text-align:center;">연락처</th>
									<td style="padding:0px;">
										<input type="text" numberOnly style="width:100%; height:36px;" name="attnTel" id="attnTel">
									</td>
								</tr>
								<tr>
									<th style="width:10%; text-align:center;">등록 일자</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%; height:36px;" disabled name="a002Date" id="a002Date">
									</td>
									<th style="width:10%; text-align:center;">이메일</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%; height:36px;" name="attnEmail" id="attnEmail">
									</td>
								</tr>
							</tbody>
						</table>
						<div style="text-align:right; margin-right:20px; margin-top:10px;">
							<input type="button" class="invInfo_btn" onclick="fn_setInvoiceInfo()" value="청구지 정보">
							<input type="button" class="invInfo_btn" onclick="fn_setCurrentInfo()" value="최근 정보">
						</div>
						<br/>
						<table class="table" style="width:98%;">
							<thead>
								<tr>
									<th colspan="4">반품 회수지 정보</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>자사 정산 ID</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="calculateId" id="calculateId">
									</td>
									<th>주문 접수 번호</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="orderReference" id="orderReference">
									</td>
								</tr>
								<tr>
									<th><font class="red" id="orderNo_ft" style="margin-right:5px;">*</font>주문번호</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="orderNo" id="orderNo">
									</td>
									<th>주문일자</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="orderDate" id="orderDate">
									</td>
								</tr>
								<tr>
									<th><font class="red" id="pickupName_ft" style="margin-right:5px;">*</font>이름</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="pickupName" id="pickupName">
									</td>
									<th><font class="red" id="pickupZip_ft" style="margin-right:5px;">*</font>우편번호</th>
									<td style="padding:0px;">
										<input type="text" zipCodeOnly style="width:100%;height:36px;" name="pickupZip" id="pickupZip">
									</td>
								</tr>
								<tr>
									<th><font class="red hide" id="pickupAddr_ft" style="margin-right:5px;">*</font>주소</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="pickupAddr" id="pickupAddr">
									</td>
									<th><font class="red" id="pickupEngAddr_ft" style="margin-right:5px;">*</font>영문주소</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="pickupEngAddr" id="pickupEngAddr">
									</td>
								</tr>
								<tr>
									<th><font class="red" id="pickupTel_ft" style="margin-right:5px;">*</font>전화번호</th>
									<td style="padding:0px;">
										<input type="text" numberOnly style="width:100%;height:36px;" name="pickupTel" id="pickupTel">
									</td>
									<th>
										<font class="red hide" id="pickupMobile_ft">*&nbsp;</font>휴대전화번호
									</th>
									<td style="padding:0px;">
										<input type="text" numberOnly style="width:100%;height:36px;" name="pickupMobile" id="pickupMobile">
									</td>
								</tr>
							</tbody>
						</table>
						<br/>
						<table class="table" style="width:98%;">
							<thead>
								<tr>
									<th colspan="4">반품 도착지 정보</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th><font class="red" id="senderName_ft" style="margin-right:5px;">*</font>회사명</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="senderName" id="senderName">
									</td>
									<th><font class="red" id="senderZip_ft" style="margin-right:5px;">*</font>우편번호</th>
									<td style="padding:0px;">
										<input type="text" zipCodeOnly style="width:100%;height:36px;" name="senderZip" id="senderZip">
									</td>
								</tr>
								<tr>
									<th><font class="red hide" id="senderState_ft" style="margin-right:5px;">*</font>주</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="senderState" id="senderState">
									</td>
									<th><font class="red hide" id="senderCity_ft" style="margin-right:5px;">*</font>도시</th>
									<td style="padding:0px">
										<input type="text" style="width:100%;height:36px;" name="senderCity" id="senderCity">
									</td>
								</tr>
								<tr>
									<th><font class="red" id="senderAddr_ft" style="margin-right:5px;">*</font>주소</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="senderAddr" id="senderAddr">
									</td>
									<th><font class="red" id="senderTel_ft" style="margin-right:5px;">*</font>전화번호</th>
									<td style="padding:0px;">
										<input type="text" numberOnly style="width:100%;height:36px;" name="senderTel" id="senderTel">
									</td>
								</tr>
								<tr>
									<th><font class="red hide" id="senderBuildNm_ft" style="margin-right:5px;">*</font>빌딩 명</th>
									<td style="padding:0px;" colspan="3">
										<input type="text" style="width:100%;height:36px;" name="senderBuildNm" id="senderBuildNm">
									</td>
								</tr>
							</tbody>
						</table>
						<br/><br/>
						<table style="margin-left:20px;">
							<tr>
								<th style="font-size:18px;"><font class="red" style="margin-right:5px;">*</font>반송 구분</th>
								<td style="padding-left:14px; width:200px; vertical-align:middle;">
									<input type="radio" name="returnType" value="N" id="returnType_N" checked>
									<label for="returnType_N" class="radio_label">일반</label>
									<input type="radio" name="returnType" value="E" id="returnType_E" style="margin-left:10px;">
									<label for="returnType_E">긴급</label>
								</td>
							</tr>
						</table>
						<br/>
						<table class="table" style="width:98%;">
							<tbody>
								<tr>
									<th><font class="red" style="margin-right:5px;">*</font>반송 사유</th>
									<td style="vertical-align:middle; background:#fff;">
										<input type="radio" name="returnReason" value="A" id="returnReason_A" checked>
										<label for="returnReason_A">단순변심</label>
										<input type="radio" name="returnReason" value="B" id="returnReason_B" style="margin-left:10px;">
										<label for="returnReason_B">파손</label>
										<input type="radio" name="returnReason" value="C" id="returnReason_C" style="margin-left:10px;">
										<label for="returnReason_C">불량</label>
										<input type="radio" name="returnReason" value="D" id="returnReason_D" style="margin-left:10px;">
										<label for="returnReason_D">기타</label>
									</td>
								</tr>
								<tr>
									<th><font class="red hide" id="returnReasonDetail_ft" style="margin-right:5px;">*</font>반송 사유 상세</th>
									<td style="padding:0px;">
										<input type="text" style="width:100%;height:36px;" name="returnReasonDetail" id="returnReasonDetail">
									</td>
								</tr>
							</tbody>
						</table>
						<br/><br/>
						<table class="table" style="width:98%;">
							<thead>
								<tr>
									<th colspan="3">위약 반송 정보 <small style="padding: 6px;">* 관부가세 환급시 반드시 작성해 주세요</small></th>
								</tr>
							</thead>
							<tbody>
								<tr style="height:41px;">
									<th class="center colorBlack" style="width:10%;"><font class="red" style="margin-right:5px;">*</font>위약 반송</th>
									<td style="vertical-align:middle; background:white;" colspan="2">
										<input type="radio" name="taxType" id="taxType_Y" value="Y"><label for="taxType_Y">&nbsp;위약 반송</label>
										&nbsp;<input type="radio" name="taxType" id="taxType_N" value="N" checked><label for="taxType_N">&nbsp;일반 반송</label>
										<input type="hidden" name="taxReturn" id="taxReturn" value="C">
									</td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnReason_ft" style="margin-right:5px;">*</font>반송 사유서</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason"></td>
									<td style="vertical-align: middle; background:white;">
										<a href="/반송 사유서.xlsx" download>[반송사유서 다운로드]</a>
									</td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCapture_ft" style="margin-right:5px;">*</font>반품 접수 캡쳐본</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture"></td>
									<td style="background:white;"></td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnMessenger_ft" style="margin-right:5px;">*</font>반품 메신저 캡쳐본</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger"></td>
									<td style="background:white;"></td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCl_ft" style="margin-right:5px;">*</font>반품 Commercial Invoice</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl" ></td>
									<td style="vertical-align: middle; background:white;">
										<a href="/반품 커머셜 인보이스.xlsx" download>[반품 커머셜 다운로드]</a>
									</td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnCopyBank_ft" style="margin-right:5px;">*</font>환급 통장 사본</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank"></td>
									<td style="background:white;"></td>
								</tr>
								<tr class="hideTax">
									<th class="center colorBlack" style="width:12%;"><font class="red hide" id="rtnIc_ft" style="margin-right:5px;">*</font>수출신고필증</th>
									<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnIc" name="rtnIc"></td>
									<td style="background:white;"></td>
								</tr>
							</tbody>
						</table>
						<br/>
						<table class="table" style="width:98%;">
							<tbody>
								<tr>
									<th>입고 요청사항</th>
									<td style="padding:0px;">
										<input style="width:100%; height:36px;" type="text" name="whMsg" id="whMsg" class="comm_text">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<br/>
				<div id="wms_item_info" class="hide">
					
				</div>
				<div id="eshop_item_info" class="">
					
				</div>
				<div id="item_info" >
					<div class="row">
						<div style="text-align:right;">
							<input type="button" class="btn btn-sm btn-white" id="addItem" name="addItem" value="추가">
							<input type="button" class="btn btn-sm btn-white" id="delItem" name="delItem" value="삭제">
						</div>
						<div id="addForm" style="margin-top:5px;">
							<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
								<div class="col-xs-12 col-lg-12" style="font-size: 3;">
									<h4>상품 1</h4>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-1 center mp07">
											<font class="red">*&nbsp;</font><label>상품명</label>
										</div>
										<div class="col-xs-3">
											<input class="col-xs-12 require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 입력해 주세요." />
										</div>
										<div class="col-xs-1 center mp07">
											<font class="red hide" id="nativeItemDetail_ft">*&nbsp;</font><label>상품명 (일문)</label>
										</div>
										<div class="col-xs-3">
											<input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
										</div>
										<div class="col-xs-1 center mp07">
											<label><font class="red hide" id="cusItemCode_ft">*&nbsp;</font>상품코드</label>
										</div>
										<div class="col-xs-3">
											<input class="col-xs-12" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-1 center mp07">브랜드</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12" type="text" name="brand" id="brand" value="${item.brand}"/>
										</div>
										<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 무게</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12 require" priceOnly type="text" name="itemWta" id="itemWta" value="${item.itemWta}" />
										</div>
										<div class="col-xs-1 center">무게 단위</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" placeholder="KG"/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row form-group hr-8">
										<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 수량</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12 require" numberOnly type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
										</div>
										<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>상품 가격</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12 require" priceOnly type="text" name="unitValue" id="unitValue" value="${item.unitValue}" />
										</div>
										<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>통화</div>
										<div class="col-xs-3 center ">
											<select class="chosen-select-start form-control tag-input-style currencies col-xs-12" id="unitCurrency" name="unitCurrency">
												<c:forEach items="${currencyList}" var="currencyList">
													<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-1 center mp07">
											<label><font class="red">*&nbsp;</font>제조국</label>
										</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12 require" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
										</div>
										<div class="col-xs-1 center mp07">
											<label>제조회사</label>
										</div>
										<div class="col-xs-3 center">
											<input class="col-xs-12" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" />
										</div>
										<div class="col-xs-1 center mp07">
											<label>
											<font class="red hide" id="hsCode_ft">*&nbsp;</font>
											<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
											<i class="fa fa-exclamation-circle"></i>
											</a></label>
										</div>
										<div class="col-xs-3 center ">
											<input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-1 center mp07">
											<label>상품 URL</label>
										</div>
										<div class="col-xs-3">
											<input class="col-xs-12" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
										</div>
										<div class="col-xs-1 center mp07">
											<label><font class="red hide" id="itemImgUrl_ft">*&nbsp;</font>상품 이미지 URL</label>
										</div>
										<div class="col-xs-3">
											<input class="col-xs-12" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
										</div>
									</div>
									<div class="hr dotted"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div style="text-align:center; margin-top: 20px;" id="regist_div">
		<input type="button" class="registReturn" id="registReturn" value="반품 접수">
	</div>
	<br/>
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	<!-- Footer End -->
	
	<div class="modal modal-center fade" id="modal" role="dialog" data-backdrop="static">
		<div class="modal-dialog modal-center" id="agreeModal">
			<div class="modal-content">
				<div class="modal-header">
					<h4>반품 사용 안내서</h4>
				</div>
				<div class="modal-body">
					<table class="noticeTable" style="width:100%;">
						<tr>
							<td style="border:1px solid #ccc; font-size:14px; width:100%; height:140px; padding:10px 14px;">
							ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.
							<br/>
							다만 아래의 경우에는 예외로 합니다.
							<br/><br/>
							- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
							<br/>
							- 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<input type="checkbox" id="agree_chk">
					<label for="agree_chk">동의 합니다</label>
					<!-- <i class="fa fa-times" style="font-size:20px; color:#ccc; cursor:pointer;" onclick="fn_agreement()"></i> -->
				</div>
			</div>
		</div>
	</div>
	
	
	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<!-- <![endif]-->
	<!--[if IE]>
	<script src="/assets/js/jquery-1.11.3.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	
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
		var addCnt = 1;
		
		var today = new Date();
		var year = today.getFullYear();
		var month = ('0' + (today.getMonth() + 1)).slice(-2);
		var day = ('0' + today.getDate()).slice(-2);

		var date = year + '-' + month + '-' + day;
		var date2 = year+month+day;
		
		jQuery(function($) {
			
			$(document).ready(function() {
				$("#wmsOrNot").val("N");

				$("#orderDate").attr("placeholder", "yyyyMMdd");
				$("#orderDate").val(date2);


				$(".wms_table").addClass('hide');
				$(".eshop_table").addClass('hide');
				
				//$("#modal").modal();
				//$("#agree_tab").removeClass('hide');
			});

			$("#agree_chk").on('click', function() {

				$("#agreeModal").addClass('hide');
				
				Swal.fire({
					icon: 'success',
				  	title: '반품 사용 안내서에 동의 하셨습니다.',
					toast: true,
					position: 'center-center',
					showConfirmButton: false,
					timer: 800,
					timerProgressBar: true,
					didOpen: (toast) => {
						toast.addEventListener('mouseenter', Swal.resumeTimer)
						toast.addEventListener('mouseleave', Swal.resumeTimer)
					}
				})
				
				setTimeout(function() {
					$("#modal").modal('hide')
				}, 800);
				
			})

			$('#a002Date').datepicker({
				autoclose:true,
				todayHightlight:true,
				dateFormat: 'yy-mm-dd',
			});

			$('#orderDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				dateFormat: 'yymmdd',
			});

		});

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

		function fn_getWMSInfo() {
			$("input:radio[name='aci_shipping']:radio[value='wms']").prop("checked", true);
			$(".normal_tr").addClass('hide');
			$(".wms_tr").removeClass('hide');
			$("#item_info").addClass('hide');
			$("#aci_radio").removeClass('hide');
			//$(".normal_info").addClass('hide');
		}
		
		function fn_getNormalInfo() {
			$(".normal_tr").removeClass('hide');
			$(".wms_tr").addClass('hide');
			$("#dstnNation").prop('disabled', false).trigger("chosen:updated");
			$("#orgStation").prop('disabled', false).trigger("chosen:updated");
			$("#wms_item_info input").val('');
			$("#wms_item_info").addClass('hide');
			$("#item_info").removeClass('hide');
			$("#wmsOrNot").val("N");
			
		}

		function fn_getOrderInfo() {

			var value = $('input:radio[name="aci_shipping"]:checked').val();
			
			if (value == 'wms') {
				fn_searchOrderInfo();
			} else {
				fn_searchEshopOrderInfo();
			}
		}
		
		function fn_searchEshopOrderInfo() {
			var koblNo = $("#koblNo_wms").val();

			if (koblNo == "") {
				alert("운송장번호를 입력해주세요.");
				$("#koblNo_wms").focus();
				return false;
			} else {
				$.ajax({
					url : '/api/v1/eshopOrderItem',
					type : 'GET',
					data : {blNo: koblNo},
					success : function(data) {
						console.log(data);
						
						var html = '';

						if (data.STATUS == 'SUCCESS') {
							var values = [];
							values = data.ITEM_INFO;

							$('#orgStation').trigger("chosen:updated");
							$('#orgStation').prop('disabled', false).trigger("chosen:updated");	
							$('#dstnNation').trigger("chosen:updated");
							$('#dstnNation').prop('disabled', false).trigger("chosen:updated");
							
							$.each(values, function(index, value) {
								console.log(value);
								html += '<div class="row panel" id="addForm"><div class="col-xs-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important;">';
								html += '<div class="row">';
								html += '<div class="col-xs-3"><h4>상품 '+value.SUB_NO+'</h4></div>';
								html += '<div class="col-xs-9" style="text-align:right; vertical-align:middle;">';
								html += '<button type="button" style="border:none; background:#fff;" onclick="javascript:fn_delete(this);">';
								html += '<i class="fa fa-times" style="font-size:20px;"></i></button></div>';
								html += '</div><div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center">SUB NO</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="subNo" value="'+value.SUB_NO+'" readonly></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center"><font class="red">*</font>상품명</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="itemDetail" id="itemDetail" value="'+value.ITEM_DETAIL+'" placeholder="한국 제외 도착지일 경우 영문으로 입력해 주세요"></div>';
								html += '<div class="col-xs-1 center">현지 상품명</div><div class="col-xs-3 center"><input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value=""></div>';
								html += '<div class="col-xs-1 center">브랜드</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="brand" id="brand" value="'+value.BRAND+'"></div></div><div class="hr dotted"></div>';
								html += '<div class="row hr-8"><div class="col-xs-1 center"><font class="red">*</font>상품 무게</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="itemWta" id="itemWta" value=""></div>';
								html += '<div class="col-xs-1 center">무게 단위</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="wtUnit" id="wtUnit" value=""></div>';
								html += '<div class="col-xs-1 center">상품 수량</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" value="'+value.ITEM_CNT+'" readonly></div></div>';
								html += '<div class="hr dotted"></div><div class="row form-group hr-8">';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>반품 수량</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="itemCnt" id="itemCnt"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>상품 가격</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="unitValue" id="unitValue" value="'+value.UNIT_VALUE+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px">*</font>통화</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="unitCurrency" id="unitCurrency" value="'+value.UNIT_CURRENCY+'"></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>제조국</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="makeCntry" id="makeCntry" value="'+value.MAKE_CNTRY+'"></div>';
								html += '<div class="col-xs-1 center">제조회사</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="makeCom" id="makeCom" value="'+value.MAKE_COM+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>';
								html += '<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE</a></div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="'+value.HS_CODE+'"></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center">상품 URL</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="itemUrl" id="itemUrl" value="'+value.ITEM_URL+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>상품 이미지 URL</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="itemImgUrl" id="itemImgUrl" value="'+value.ITEM_IMG_URL+'"></div>';
								html += '<div class="col-xs-1 center">상품코드</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="cusItemCode" id="cusItemCode" value=""></div></div><div class="hr dotted"></div>';
								
								html += '</div></div>';
							});

							$("#wms_item_info").empty();
							$("#eshop_item_info").empty();
							$("#eshop_item_info").append(html);
						} else {
							alert("유효하지 않는 BL입니다.");
						}
						
					}, error : function(request,status,error) {
						console.log("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
						alert("정보 조회에 실패하였습니다.");
					}
				})
			}
		}
		

		function fn_searchOrderInfo() {
			var koblNo = $("#koblNo_wms").val();
			console.log(koblNo);
			if(koblNo == "") {
				alert("운송장번호를 입력해주세요.");
				$("#koblNo_wms").focus();
				return false;
			} else {

				$(".info_btn").addClass('hide');

				$.ajax({
					url : '/return/getShippingInfo',
					type : 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : {koblNo: koblNo},
					success : function(data) {
						console.log(data);
			/*
						if (data.result == "S") {

							$("#koblNo").val(koblNo);
							$('#orgStation').val(data.rtn.dstnNation);
							$('#orgStation').trigger("chosen:updated");
							$('#orgStation').prop('disabled', true).trigger("chosen:updated");	
							$('#dstnNation').val(data.rtn.orgStation);
							$('#dstnNation').trigger("chosen:updated");
							$('#dstnNation').prop('disabled', true).trigger("chosen:updated");
							$('#calculateId').val(data.rtn.calculateId);
							$('#orderReference').val(data.rtn.orderReference);
							$('#orderNo').val(data.rtn.orderNo);
							$('#orderDate').val(data.rtn.orderDate);
							$('#pickupName').val(data.rtn.pickupName);
							$('#pickupZip').val(data.rtn.pickupZip);
							$('#pickupAddr').val(data.rtn.pickupAddr);
							$('#pickupEngAddr').val(data.rtn.pickupEngAddr);
							$('#pickupTel').val(data.rtn.pickupTel);
							$('#pickupMobile').val(data.rtn.pickupMobile);
							$('#senderName').val(data.rtn.senderName);
							$('#senderZip').val(data.rtn.senderZip);
							$('#senderState').val(data.rtn.senderState);
							$('#senderCity').val(data.rtn.senderCity);
							$('#senderAddr').val(data.rtn.senderAddr);
							$('#senderTel').val(data.rtn.senderTel);
							$('#senderBuildNm').val(data.rtn.senderBuildNm);

							var dest = data.rtn.orgStation;
							var html = '';
							var values = [];
							values = data.itemList;
							$("#wms_item_info").removeClass('hide');
							$("#item_info input[type='text']").val('');
							$("#item_info").addClass('hide');
							$("#wmsOrNot").val("Y");
							
							$.each(values, function(index, value) {
								html += '<div class="row panel" id="addForm"><div class="col-xs-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important;">';
								html += '<div class="row">';
								html += '<div class="col-xs-3"><h4>상품 '+value.subNo+'</h4></div>';
								html += '<div class="col-xs-9" style="text-align:right; vertical-align:middle;">';
								html += '<button type="button" style="border:none; background:#fff;" onclick="javascript:fn_delete(this);">';
								html += '<i class="fa fa-times" style="font-size:20px;"></i></button></div>';
								html += '</div><div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center">SUB NO</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="subNo" value="'+value.subNo+'" readonly></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center"><font class="red">*</font>상품명</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="itemDetail" id="itemDetail" value="'+value.itemDetail+'" placeholder="한국 제외 도착지일 경우 영문으로 입력해 주세요"></div>';
								html += '<div class="col-xs-1 center">현지 상품명</div><div class="col-xs-3 center"><input class="col-xs-12" type="text" name="nativeItemDetail" id="nativeItemDetail" value="'+value.nativeItemDetail+'"></div>';
								html += '<div class="col-xs-1 center">브랜드</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="brand" id="brand" value="'+value.brand+'"></div></div><div class="hr dotted"></div>';
								html += '<div class="row hr-8"><div class="col-xs-1 center"><font class="red">*</font>상품 무게</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="itemWta" id="itemWta" value="'+value.itemWta+'"></div>';
								html += '<div class="col-xs-1 center">무게 단위</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="wtUnit" id="wtUnit" value="'+value.wtUnit+'"></div>';
								html += '<div class="col-xs-1 center">상품 수량</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" value="'+value.itemCnt+'" readonly></div></div>';
								html += '<div class="hr dotted"></div><div class="row form-group hr-8">';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>반품 수량</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="itemCnt" id="itemCnt"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>상품 가격</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="unitValue" id="unitValue" value="'+value.unitValue+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px">*</font>통화</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="unitCurrency" id="unitCurrency" value="'+value.unitCurrency+'"></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>제조국</div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="makeCntry" id="makeCntry" value="'+value.makeCntry+'"></div>';
								html += '<div class="col-xs-1 center">제조회사</div><div class="col-xs-3 center">';
								html += '<input class="col-xs-12" type="text" name="makeCom" id="makeCom" value="'+value.makeCom+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>';
								html += '<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE</a></div>';
								html += '<div class="col-xs-3 center"><input class="col-xs-12" type="text" name="hsCode" id="hsCode" value="'+value.hsCode+'"></div></div>';
								html += '<div class="hr dotted"></div><div class="row hr-8"><div class="col-xs-1 center">상품 URL</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="itemUrl" id="itemUrl" value="'+value.itemUrl+'"></div>';
								html += '<div class="col-xs-1 center"><font class="red" style="margin-right:5px;">*</font>상품 이미지 URL</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="itemImgUrl" id="itemImgUrl" value="'+value.itemImgUrl+'"></div>';
								html += '<div class="col-xs-1 center">상품코드</div><div class="col-xs-3">';
								html += '<input class="col-xs-12" type="text" name="cusItemCode" id="cusItemCode" value="'+value.cusItemCode+'"></div></div><div class="hr dotted"></div>';
								
								html += '</div></div>';
							});

							$("#eshop_item_info").empty();
							$("#wms_item_info").empty();
							$("#wms_item_info").append(html);
							
						} else {
							alert(data.msg);
						}
						*/
					},
					error : function(request,status,error) {
						console.log("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
						alert("정보 조회에 실패하였습니다.");
					}
				})
			}
		}

		function fn_setInvoiceInfo() {
			selectAttnInfo("inv");
		};

		function fn_setCurrentInfo() {
			selectAttnInfo("current");
		};
		

		function selectAttnInfo(type) {
			var data = {type : type};

			$.ajax({
				url : '/return/selectReturnAttnInfo',
				type : 'GET',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : data,
				success : function(result) {
					console.log(result);
					if (result.result == "I") {
						$("#attnName").val(result.userInfo.invUserName);
						$("#attnTel").val(result.userInfo.invUserTel);
						$("#attnEmail").val(result.userInfo.invUserEmail);
					} else if (result.result == "S") {
						$("#attnName").val(result.userInfo.ATTN_NAME);
						$("#attnTel").val(result.userInfo.ATTN_TEL);
						$("#attnEmail").val(result.userInfo.ATTN_EMAIL);
					} else {
						alert(result.msg);
					}
				},
				error : function(request,status,error){
			        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			      }

			})
		};

		$("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });
        
		$("input:text[zipCodeOnly]").on('keyup', function() {
			$(this).val($(this).val().replace(/[^0-9|-]/g,""));
		});

		$("input:text[priceOnly]").on('keyup', function() {
			$(this).val($(this).val().replace(/[^0-9.]/g,""));
		});


		$("#addItem").on("click",function(e){
			addCnt++
			$.ajax({
				url:'/return/returnItem',
				type: 'POST',
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : {
					cnt : addCnt, dstnNation : $("#dstnNation").val()
				},
				success : function(data) {
					$("#addForm").append(data);
					$("#addCntVal").val(addCnt);
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	            }
			})
		});
		
		$("#delItem").on("click",function(e){
			if(addCnt != 1){
				$("#add"+$("#addCntVal").val()).remove();
				addCnt--;
				$("#addCntVal").val(addCnt);
				console.log(addCnt);
			} else {
				alert("최소 1개의 상품정보를 입력해야 합니다.");
			}
		});


		function fn_delete(obj) {
			if ($(".panel").length > 1) {
				obj.closest('.panel').remove();
			} else {
				alert("최소 1개의 상품정보를 입력해야 합니다.");
			}
		}

		$("#dstnNation").on('change', function() {
			var dest = $("#dstnNation").val();

			if (dest == 'JP') {
				$("#pickupMobile_ft").removeClass('hide');
				$("#nativeItemDetail_ft").removeClass('hide');
				$("#cusItemCode_ft").removeClass('hide');
				$("#hsCode_ft").removeClass('hide');
				$("#itemImgUrl_ft").removeClass('hide');
			} else {
				$("#pickupMobile_ft").addClass('hide');
				$("#nativeItemDetail_ft").addClass('hide');
				$("#cusItemCode_ft").addClass('hide');
				$("#hsCode_ft").addClass('hide');
				$("#itemImgUrl_ft").addClass('hide');
			}

			if (dest == 'KR') {
				$("#pickupAddr_ft").removeClass('hide');
				$("#pickupEngAddr_ft").addClass('hide');
			} else {
				$("#pickupAddr_ft").addClass('hide');
				$("#pickupEngAddr_ft").removeClass('hide');
			}

			if (dest == 'US') {
				$("#senderState_ft").removeClass('hide');
				$("#senderCity_ft").removeClass('hide');
				$("#senderBuildNm_ft").removeClass('hide');
			} else {
				$("#senderState_ft").addClass('hide');
				$("#senderCity_ft").addClass('hide');
				$("#senderBuildNm_ft").addClass('hide');
			}
			
		})
		
		$("input[name='aci_shipping']").on('click', function() {

			if ($(this).val() == 'wms') {
				$(".eshop_id").addClass('hide');
				$(".eshop_api").addClass('hide');
			} else {
				$(".eshop_id").removeClass('hide');
				$(".eshop_api").removeClass('hide');
			}
		})
		
		$("input[name='pickType']").on('click', function() {
			
			if ($(this).val() == 'A') {
				$("#reTrkCom_ft").removeClass('hide');
				$("#reTrkCom").prop("disabled", false);
				$("#reTrkNo_ft").removeClass('hide');
				$("#reTrkNo").prop("disabled", false);
				$("#a002Date").val(date);
				$("#a002Date").prop("disabled", false);
				$("#a002Date").attr("placeholder", "yyyy-mm-dd");
			} else {
				$("#reTrkCom_ft").addClass('hide');
				$("#reTrkNo_ft").addClass('hide');
				$("#a002Date").val('');
				$("#reTrkCom").prop("disabled", true);
				$("#reTrkNo").prop("disabled", true);
				$("#a002Date").prop("disabled", true);
				$("#a002Date").attr("placeholder", "");
			}
		})
		
		$("input[name='returnReason']").on('click', function() {
			if ($(this).val() == 'D') {
				$("#returnReasonDetail_ft").removeClass('hide');
			} else {
				$("#returnReasonDetail_ft").addClass('hide');
			}
		})
		
		$("input[name='taxType']").on('click', function() {
			var data = $("#wmsOrNot").val();

			if (data == 'Y') {
				$("#rtnIc").removeClass('taxTypeValue');
				
				if($(this).val() == 'Y') {
					$("#rtnReason_ft").removeClass('hide');
					$("#rtnCapture_ft").removeClass('hide');
					$("#rtnMessenger_ft").removeClass('hide');
					$("#rtnCl_ft").removeClass('hide');
					$("#rtnCopyBank_ft").removeClass('hide');
				} else {
					$("#rtnReason_ft").addClass('hide');
					$("#rtnCapture_ft").addClass('hide');
					$("#rtnMessenger_ft").addClass('hide');
					$("#rtnCl_ft").addClass('hide');
					$("#rtnCopyBank_ft").addClass('hide');
				}

				
			} else {
				$("#rtnIc").addClass('taxTypeValue');
				
				if($(this).val() == 'Y') {
					$("#rtnReason_ft").removeClass('hide');
					$("#rtnCapture_ft").removeClass('hide');
					$("#rtnMessenger_ft").removeClass('hide');
					$("#rtnCl_ft").removeClass('hide');
					$("#rtnCopyBank_ft").removeClass('hide');
					$("#rtnIc_ft").removeClass('hide');
				} else {
					$("#rtnReason_ft").addClass('hide');
					$("#rtnCapture_ft").addClass('hide');
					$("#rtnMessenger_ft").addClass('hide');
					$("#rtnCl_ft").addClass('hide');
					$("#rtnCopyBank_ft").addClass('hide');
					$("#rtnIc_ft").addClass('hide');
				}
			}
		})
		
		$("#registReturn").on('click', function() {

			if ($("#koblNo_normal").val() != "") {
				$("#koblNo").val($("#koblNo_normal").val());
			} else {
				$("#koblNo_normal").focus();
				return false;
			}
			
			var dest = $("#dstnNation").val();


			if ($("#orderNo").val() == "") {
				$("#orderNo").focus();
				return false;
			}

			if ($("#pickupName").val() == "") {
				$("#pickupName").focus();
				return false;
			}

			if ($("#pickupZip").val() == "") {
				$("#pickupZip").focus();
				return false;
			}

			if ($("#pickupTel").val() == "") {
				$("#pickupTel").focus();
				return false;
			}

			if ($("#senderName").val() == "") {
				$("#senderName").focus();
				return false;
			}

			if ($("#senderZip").val() == "") {
				$("#senderZip").focus();
				return false;
			}

			if ($("#senderAddr").val() == "") {
				$("#senderAddr").focus();
				return false;
			}

			if ($("#senderTel").val() == "") {
				$("#senderTel").focus();
				return false;
			}

			$("input[name='itemDetail']").each(function(index, item) {
				if ($(item).val() == "") {
					$(item).focus();
					return false;
				}
			})
			
			$("input[name='itemWta']").each(function(index, item) {
				if ($(item).val() == "") {
					$(item).focus();
					return false;
				}
			})
			
			$("input[name='itemCnt']").each(function(index, item) {
				if ($(item).val() == "") {
					$(item).focus();
					return false;
				}
			})
			
			$("input[name='unitValue']").each(function(index, item) {
				if ($(item).val() == "") {
					$(item).focus();
					return false;
				}
			})
			
			$("input[name='makeCntry']").each(function(index, item) {
				if ($(item).val() == "") {
					$(item).focus();
					return false;
				}
			})
			
			
			if (dest == 'KR') {
				if ($("#pickupAddr").val() == "") {
					$("#pickupAddr").focus();
					return false;
				}
			} 

			if (dest != 'KR') {
				if ($("#pickupEngAddr").val() == "") {
					$("#pickupEngAddr").focus();
					return false;
				}
			}

			if (dest == 'US') {
				if ($("#senderState").val() == "") {
					$("#senderState").focus();
					return false;
				}

				if ($("#senderCity").val() == "") {
					$("#senderCity").focus();
					return false;
				}

				if ($("#senderBuildNm").val() == "") {
					$("#senderBuildNm").focus();
					return false;
				}
			}

			if (dest == 'JP') {
				if ($("#pickupMobile").val() == "") {
					$("#pickupMobile").focus();
					return false;
				}

				$("input[name='nativeItemDetail']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return false;
					}
				})

				$("input[name='cusItemCode']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return false;
					}
				})

				$("input[name='hsCode']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return false;
					}
				})
				
				$("input[name='itemImgUrl']").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						return false;
					}
				})
			}

			if ($("input[name='returnReason']:checked").val() == 'D') {
				if ($("#returnReasonDetail").val() == "") {
					$("#returnReasonDetail").focus();
					return false;
				}
			}

			if ($("input[name='taxType']:checked").val() == 'Y') {
				for (var i = 0 ; i < $(".taxTypeValue").size(); i++) {
					if ($($(".taxTypeValue")[i]).val().length == 0) {
						alert("위약반송 필수 서류가 비어있습니다.");
						return false;
					}
				}
			}

			if ($("input[name='pickType']:checked").val() == 'A') {
				if ($("#reTrkCom").val() == "") {
					$("#reTrkCom").focus();
					return false;
				}

				if ($("#reTrkNo").val() == "") {
					$("#reTrkNo").focus();
					return false;
				} 
			}

			$("#returnForm").attr("action", "/return/returnOthers");
			$("#returnForm").attr("method", "post");
			$("#returnForm").submit();

			
		})
		
		
		
		function fn_agreement() {
			var text = confirm("반품 사용 안내서에 동의 하시겠습니까?");

			if (text) {
				$("#modal").modal('hide');
			} else {
				location.href = "/return/list";
			}
		}
	
		
	</script>
</body>
</html>