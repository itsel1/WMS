<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>반품 접수 내역</title>
<head>
<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>

<style type="text/css">
	input[type=text] {
		border: 1px solid #e6e6e6;
		height: 30px;
	}
	
	input[type=text]:hover, input[type=text]:focus {
		border: 1px solid #e6e6e6;
	}
	
	#tab-content {
		width: 70%;
		height: auto;
		border: 1px solid #527d96;
		margin: auto;
		margin-top: 30px;
		padding: 20px;
	}


	.radios input {
		appearance: none;
		cursor: pointer;
		border-radius: 2px;
		padding: 5px 10px;
		background: #fff;
		/* border: 1px solid #3f414d; */
		border: 1px solid #527d96;
		font-size: 12px;
		transition: all 0.1s;
		margin: 0;
	}
	
	.radios input:checked {
		/* background: #3f414d; */
		background-color: #527d96;
		color: #ececec;
		box-shadow: 0 1px 1px rgba(0, 0, 0, 0.3);
		text-shadow: 0 1px 0px #808080;
		outline: none;
	}
	
	.radios input::before {
		content: attr(label);
		text-align: center;
	}
	
	#return-tbl {
		margin-top: 10px;
	}
	
	#return-tbl th {
		width: 15%;
		padding-left: 15px;
		height: 35px;
	}
	
	#return-tbl td {
		width: 35%;
		height: 35px;
		padding: 0;
	}
	
	#return-tbl input[type=text] {
		width: 100%;
		height: 35px;
		border: none;
	} 

	.req {
		color: #CD3861;
		font-weight: 500;
	}
	
	.headers {
		background-color: rgba(204, 204, 204, 0.19);
	}
	
	.t-headers {
		background-color: rgba(76, 144, 189, 0.72);
		color: #fff;
	}
	
	#return-tbl2 {
		width: 100%;
	}
	
	#return-tbl2 th {
		width: 15%;
		height: 35px;
		background-color: rgba(204, 204, 204, 0.19);
		padding-left: 10px;
	}
	
	#return-tbl2 td {
		padding-left: 2px;
	}
	
	#return-tbl2 td input[type=text] {
		border: none;
		width: 100%;
		height: 35px;
	}
	
	#tip {
		cursor: default;
		font-weight: 100;
		font-size: 14px;
	}
	
	[data-tooltip-text]:hover {
		position: relative;
	}
	
	[data-tooltip-text]:hover:after {
		background-color: #000000;
		background-color: rgba(0, 0, 0, 0.7);
		box-shadow: 0px 0px 3px 1px rgba(50, 50, 50, 0.4);
		border-radius: 5px;
		color: #FFFFFF;
		font-size: 12px;
		content: attr(data-tooltip-text);
		margin-bottom: 10px;
		top: -30px;
		left: 0px;
		width: 240px;
		padding: 7px 12px;
		position: absolute;
		word-wrap: nowrap;
		z-index: 9999;
		font-weight: 100 !important;
	}
	
	.item-tbl th {
		height: 35px;
		width: 15%;
		text-align: center;
		background-color: rgba(204, 204, 204, 0.19);
	}
	
	.item-tbl td {
		padding: 0;
		width: 35%;
	}
	
	.item-tbl td input[type=text] {
		width: 100%;
		height: 35px;
		border: none;
	}
	
	.item-del {
		/* background: #527d96; */
		background-color: rgba(66, 134, 179, 1);
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
	}
	
	.item-del:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3); 
	}
	
	#add-btn {
		background: #527d96;
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
		padding: 5px 10px;
		margin-top: 20px;
		margin-bottom: 5px;
		margin-left: 3px;
	}
	
	#add-btn:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3); 
	}
	
	#search-btn {
		background: #527d96;
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
		padding: 5px 10px;
	}
	
	#search-btn:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3);
	}
	
	#head-tbl {
		width: 100%;
	}
	
	#head-tbl th {
		height: 35px;
		width: 10%;
		padding-left: 10px;
	}
	
	#head-tbl td input[type=text] {
		width: 250px;
	}
	
	#registReturn {
		margin-left: 2px;
		width: 100px;
		height: 40px;
		border-radius: 2px;
		cursor: pointer;
		color: #fff;
		background-color: #527d96;
		border: none;
		box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
		transition: 0.3s;
	}
	
	#registReturn:hover {
		background-color: rgba(82, 125, 150, 0.9);
		box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
	}
	
	#backToList {
		margin-left: 1px;
		width: 100px;
		height: 40px;
		border-radius: 2px;
		cursor: pointer;
		color: #fff;
		background-color: #8c4545;
		border: none;
		box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
		transition: 0.3s;
	}
	
	#backToList:hover {
		background-color: rgba(140, 69, 69, 0.9);
		box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
	}
</style>
</head>
	<body class="no-skin"> 
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
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
	    <div>
			<form name="form" id="form" enctype="multipart/form-data">
			<input type="hidden" id="cnt">
    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    		<input type="hidden" name="siteType" id="siteType" value="${siteType}"/>
				<div class="container">
			       <div class="page-content noneArea">
						<div id="inner-content-side" >
							<div id="tab-content">
								<table id="head-tbl">
									<tr>
										<th>원운송장번호</th>
										<td style="width:20%;"><input type="text" name="koblNo" id="koblNo" value="${koblNo}"></td>
										<td style="padding-left:5px;">
											<input type="button" id="search-btn" value="검색">
										</td>
									</tr>
									<tr>
										<th>집하지</th>
										<td>
											<select id="orgStation" name="orgStation">
												<option value="">::선택::</option>
												<option value="082">서울</option>
											</select>
										</td>
									</tr>
									<tr class="station-addr hide">
										<th>집하지 주소</th>
										<td>서울특별시 강서구 남부순환로19길 121 (외발산동)</td>
									</tr>
									<tr class="station-addr hide">
										<th>집하지 Tel</th>
										<td>02) 2663-3300</td>
									</tr>
									<tr><th colspan="2" style="height:20px !important;"></th></tr>
									<tr>
										<th class="req">픽업방법</th>
										<td>
											<div class="radios">
												<input label="직접발송" type="radio" name="pickType" value="A" checked>
												<input label="회수요청" type="radio" name="pickType" value="B">
											</div>
										</td>
									</tr>
								</table>
								<table style="width:100%;" id="return-tbl">
									<tr>
										<th class="t-headers" colspan="2" style="text-align:center;">반품 택배 정보</th>
										<th class="t-headers" colspan="2" style="text-align:center;">반품 담당자 정보</th>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">	
										<th class="headers">택배회사</th>
										<td><input type="text" name="trkCom" id="trkCom"></td>
										<th class="headers">이름</th>
										<td><input type="text" name="attnName" id="attnName" value="${userInfo.invUserName}"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="headers">택배번호</th>
										<td><input type="text" name="trkNo" id="trkNo"></td>
										<th class="headers">연락처</th>
										<td><input type="text" name="attnTel" id="attnTel" value="${userInfo.invUserTel}"></td>
									</tr>
									<tr>
										<th class="headers">접수일자</th>
										<td><input type="text" name="trkDate" id="trkDate"></td>
										<th class="headers">이메일</th>
										<td><input type="text" name="attnEmail" id="attnEmail" value="${userInfo.invUserEmail}"></td>
									</tr>
									<tr>
										<th class="t-headers"  colspan="4" style="text-align:center;">발송 정보</th>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">주문번호</th>
										<td><input type="text" name="orderNo" id="orderNo" value="${orderList.orderNo}"></td>
										<th class="headers">주문일자</th>
										<td><input type="text" name="orderDate" id="orderDate" value="${orderList.orderDate}"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">이름 (영문)</th>
										<td><input type="text" name="shipperName" id="shipperName" value="${orderList.cneeName}"></td>
										<th class="req headers">우편번호</th>
										<td><input type="text" name="shipperZip" id="shipperZip" zipCodeOnly value="${orderList.cneeZip}"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">주소 (영문)</th>
										<td><input type="text" name="shipperAddr" id="shipperAddr" value="${orderList.cneeAddr}"></td>
										<th class="headers">상세주소 (영문)</th>
										<td><input type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${orderList.cneeAddrDetail}"></td>
									</tr>
									<tr>
										<th class="req headers">전화번호</th>
										<td><input type="text" name="shipperTel" id="shipperTel" value="${orderList.cneeTel}"></td>
										<th class="headers">휴대전화번호</th>
										<td><input type="text" name="shipperHp" id="shipperHp" value="${orderList.cneeHp}"></td>
									</tr>
									<tr>
										<th class="t-headers"  colspan="4" style="text-align:center;">수취업체 정보</th>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">업체명</th>
										<td><input type="text" name="cneeName" id="cneeName" value="${orderList.shipperName}"></td>
										<th class="req headers">국가</th>
										<td>
											<select class="chosen-select-start form-control tag-input-style nations" id="dstnNation" name="dstnNation" >
												<c:forEach items="${nationList}" var="dstnNationList">
													<option value="${dstnNationList.nationCode}" <c:if test="${dstnNationList.nationCode eq 'US'}">selected</c:if>>${dstnNationList.nationEName} (${dstnNationList.nationName})</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">주</th>
										<td><input type="text" name="cneeState" id="cneeState" value="${orderList.shipperState}"></td>
										<th class="req headers">도시</th>
										<td><input type="text" name="cneeCity" id="cneeCity" value="${orderList.shipperCity}"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req headers">주소</th>
										<td><input type="text" name="cneeAddr" id="cneeAddr" value="${orderList.shipperAddr}"></td>
										<th class="headers">상세 주소</th>
										<td><input type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="${orderList.shipperAddrDetail}"></td>
									</tr>
									<tr>
										<th class="req headers">우편번호</th>
										<td><input type="text" name="cneeZip" id="cneeZip" zipCodeOnly value="${orderList.shipperZip}"></td>
										<th class="req headers">전화번호</th>
										<td><input type="text" name="cneeTel" id="cneeTel" value="${orderList.shipperTel}"></td>
									</tr>
								</table>
								<br>
								<table id="return-tbl2">
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19); border-top:1px solid rgba(204, 204, 204, 0.19);">
										<th>반품 구분</th>
										<td colspan="3">
											<div class="radios">
												<input label="일반" type="radio" name="returnType" value="N" checked>
												<input label="긴급" type="radio" name="returnType" value="E">
											</div>
										</td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th>반품 사유</th>
										<td style="width:20%;">
											<div class="radios">
												<input label="단순변심" type="radio" name="returnReason" value="A" checked>
												<input label="파손" type="radio" name="returnReason" value="B">
												<input label="불량" type="radio" name="returnReason" value="C">
												<input label="기타" type="radio" name="returnReason" value="D">
											</div>
										</td>
										<th id="returnDetailRed">기타 상세</th>
										<td><input type="text" name="returnReasonDetail" id="returnReasonDetail"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th>입고 요청사항</th>
										<td colspan="3"><input type="text" name="whMsg" id="whMsg"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th class="req">위약 반송 정보 <i class="fa fa-exclamation-circle" data-tooltip-text="관부과세 환급 시 반드시 작성해 주세요" id="tip"></i></th>
										<td colspan="3">
											<div class="radios">
												<input label="위약반송" type="radio" name="taxType" value="Y">
												<input label="일반반송" type="radio" name="taxType" value="N" checked>
											</div>
										</td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileA" class="req">반송사유서</th>
										<td><input type="file" name="fileReason" id="fileReason" class="taxTypeValue"></td>
										<td colspan="2"><a href="/반송 사유서.xlsx" download>[양식 다운로드]</a></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileB" class="req">반품 접수 캡처본</th>
										<td colspan="4"><input type="file" name="fileCapture" id="fileCapture" class="taxTypeValue"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileC" class="req">반품 메신저 캡처본</th>
										<td colspan="4"><input type="file" name="fileMessenger" id="fileMessenger" class="taxTypeValue"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileD" class="req">반품 Commercial Invoice</th>
										<td><input type="file" name="fileComm" id="fileComm" class="taxTypeValue"></td>
										<td colspan="2"><a href="/반품 커머셜 인보이스.xlsx" download>[양식 다운로드]</a></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileE" class="req">환급 통장 사본</th>
										<td colspan="4"><input type="file" name="fileBank" id="fileBank" class="taxTypeValue"></td>
									</tr>
									<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
										<th id="fileF" class="req">수출신고 필증</th>
										<td colspan="4"><input type="file" name="fileLicense" id="fileLicense" class="taxTypeValue"></td>
									</tr>
								</table>
								<input type="button" id="add-btn" value="상품 추가">
								<span style="margin-left:5px;">※ HS CODE는 도착국가 기준으로 입력</span>
								<div id="accordion">
									<input type="hidden" id="itemInfoCnt" value="${fn:length(itemList)}">
									<c:choose>
										<c:when test="${empty itemList}">
											<table class="panel panel-default panels1 item-tbl" style="width:100%;margin-bottom:0px;">
												<tr>
													<th colspan="4" style="text-align:right !important;padding-right:10px;background:rgba(76, 144, 189, 0.72);">
														<input type="button" class="item-del" value="삭제" onclick="javascript:fn_deleteItem(this);">
													</th>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th class="req">상품명</th>
													<td><input type="text" name="itemDetail" class="itemDetail" placeholder="영문 기입"></td>
													<th>현지 상품명</th>
													<td><input type="text" name="nativeItemDetail" class="nativeItemDetail"></td>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th>상품코드</th>
													<td><input type="text" name="cusItemCode" class="cusItemCode"></td>
													<th>브랜드</th>
													<td><input type="text" name="brand" class="brand"></td>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th>상품 무게</th>
													<td><input type="text" name="itemWta" class="itemWta"></td>
													<th>무게 단위</th>
													<td><input type="text" name="wtUnit" class="wtUnit"></td>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th class="req">상품 수량</th>
													<td><input type="text" name="itemCnt" class="itemCnt"></td>
													<th class="req">판매 단가</th>
													<td><input type="text" name="unitValue" class="unitValue"></td>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th class="req">통화 단위</th>
													<td>
														<select class="chosen-select-start" name="unitCurrency" class="unitCurrency">
															<c:forEach items="${currencyList}" var="currencyList">
																<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
															</c:forEach>
														</select>
													</td>
													<th class="req">제조국</th>
													<td><input type="text" name="makeCntry" class="makeCntry"></td>
												</tr>
												<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
													<th>제조 회사</th>
													<td><input type="text" name="makeCom" class="makeCom"></td>
													<th class="req">HS CODE</th>
													<td><input type="text" name="hsCode" class="hsCode"></td>
												</tr>
												<tr>
													<th>상품 url</th>
													<td><input type="text" name="itemUrl" class="itemUrl"></td>
													<th>상품 이미지 url</th>
													<td><input type="text" name="itemImgUrl" class="itemImgUrl"></td>
												</tr>
											</table>
										</c:when>
										<c:otherwise>
											<c:forEach items="${itemList}" var="itemList" varStatus="status">
												<table class="panel panel-default panels${status.count} item-tbl" style="width:100%;margin-bottom:0px;">
													<tr>
														<th colspan="4" style="text-align:right !important;padding-right:10px;background:rgba(76, 144, 189, 0.72);">
															<input type="button" class="item-del" value="삭제" onclick="javascript:fn_deleteItem(this);">
														</th>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th class="req">상품명</th>
														<td><input type="text" name="itemDetail" class="itemDetail" placeholder="영문 기입" value="${itemList.itemDetail}"></td>
														<th>현지 상품명</th>
														<td><input type="text" name="nativeItemDetail" class="nativeItemDetail" value="${itemList.nativeItemDetail}"></td>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th>상품코드</th>
														<td><input type="text" name="cusItemCode" class="cusItemCode" value="${itemList.cusItemCode}"></td>
														<th>브랜드</th>
														<td><input type="text" name="brand" class="brand" value="${itemList.brand}"></td>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th>상품 무게</th>
														<td><input type="text" name="itemWta" class="itemWta" value="${itemList.userItemWta}"></td>
														<th>무게 단위</th>
														<td><input type="text" name="wtUnit" class="wtUnit" value="${itemList.wtUnit}"></td>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th class="req">상품 수량</th>
														<td><input type="text" name="itemCnt" class="itemCnt" value="${itemList.itemCnt}"></td>
														<th class="req">판매 단가</th>
														<td><input type="text" name="unitValue" class="unitValue" value="${itemList.unitValue}"></td>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th class="req">통화 단위</th>
														<td>
															<select class="chosen-select-start" name="unitCurrency" class="unitCurrency">
																<c:forEach items="${currencyList}" var="currencyList">
																	<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
																</c:forEach>
															</select>
														</td>
														<th class="req">제조국</th>
														<td><input type="text" name="makeCntry" class="makeCntry" value="${itemList.makeCntry}"></td>
													</tr>
													<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
														<th>제조 회사</th>
														<td><input type="text" name="makeCom" class="makeCom" value="${itemList.makeCom}"></td>
														<th class="req">HS CODE</th>
														<td><input type="text" name="hsCode" class="hsCode" value="${itemList.hsCode}"></td>
													</tr>
													<tr>
														<th>상품 url</th>
														<td><input type="text" name="itemUrl" class="itemUrl" value="${itemList.itemUrl}"></td>
														<th>상품 이미지 url</th>
														<td><input type="text" name="itemImgUrl" class="itemImgUrl" value="${itemList.itemImgUrl}"></td>
													</tr>
												</table>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</div>
							</div><!-- tab-content end -->
						</div><!-- inner-content-side end -->
					</div><!-- page-content end -->
				</div><!-- container end -->
				<div style="margin:auto;text-align:center;margin-top:15px;margin-bottom:20px;">
    				<input type="button" id="backToList" value="접수 취소">
    				<input type="button" id="registReturn" value="반품 접수">
    			</div>
			</form>
			<form id="form2">
				<input type="hidden" name="type" id="setType" value="">
				<input type="hidden" name="bl" id="setKoblNo" value="">
			</form>
		</div>
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
		var itemCnt = 1;
		
		var date = new Date();
	    var year = date.getFullYear();
	    var month = ("0" + (1 + date.getMonth())).slice(-2);
	    var day = ("0" + date.getDate()).slice(-2);
		var orderDate = year + month + day;

		jQuery(function($) {
			

			$("#trkDate, #orderDate").attr("placeholder", "ex) 20230101");
			$("#trkDate, #orderDate").attr("autocomplete", "off");
			$("#trkDate, #orderDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})
			
			$("input[name=pickType]").on('click', function() {
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
					$("#returnDetailRed").addClass('req');
				} else {
					$("#returnDetailRed").removeClass('req');
				}
			})
			
			$("#orgStation").change(function() {
				if ($(this).val() == '082') {
					$(".station-addr").removeClass('hide');
				} else {
					$(".station-addr").addClass('hide');
				}
			})
			
			$("input[name=taxType]").on('click', function() {
				if ($(this).val() == 'N') {
					$("#fileA").removeClass('req');
					$("#fileB").removeClass('req');
					$("#fileC").removeClass('req');
					$("#fileD").removeClass('req');
					$("#fileE").removeClass('req');
					$("#fileF").removeClass('req');
				} else {
					$("#fileA").addClass('req');
					$("#fileB").addClass('req');
					$("#fileC").addClass('req');
					$("#fileD").addClass('req');
					$("#fileE").addClass('req');
					$("#fileF").addClass('req');
				}
			})
			
			$("#add-btn").on('click', function() {
				$("#cnt").val(itemCnt);

				$.ajax({
					url : '/cstmr/return/addItem',
					type : 'POST',
					data : {cnt: $("#cnt").val()},
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						cnt++;
						$("#accordion").append(data);
					},
					error : function(xhr, status) {
						alert("다시 시도해 주세요.");
					}
				})
			});

			$("#backToList").on('click', function() {
				location.href = '/cstmr/return/orderList';
			});

			$("#registReturn").on('click', function() {
				var chkCnt = 0;
				if($("#orgStation").val() == "") {
					$("#orgStation").focus();
					chkCnt++;
					return;
				}
				
				if($("#orderNo").val() == "") {
					$("#orderNo").focus();
					chkCnt++;
					return;
				}

				if($("#shipperName").val() == "") {
					$("#shipperName").focus();
					chkCnt++;
					return;
				}

				if($("#shipperZip").val() == "") {
					$("#shipperZip").focus();
					chkCnt++;
					return;
				}

				if($("#shipperAddr").val() == "") {
					$("#shippperAddr").focus();
					chkCnt++;
					return;
				}

				if($("#shipperTel").val() == "") {
					$("#shipperTel").focus();
					chkCnt++;
					return;
				}

				if($("#cneeName").val() == "") {
					$("#cneeName").focus();
					chkCnt++;
					return;
				}

				if($("#cneeState").val() == "") {
					$("#cneeState").focus();
					chkCnt++;
					return;
				}

				if($("#cneeCity").val() == "") {
					$("#cneeCity").focus();
					chkCnt++;
					return;
				}

				if($("#cneeAddr").val() == "") {
					$("#cneeAddr").focus();
					chkCnt++;
					return;
				}

				if($("#cneeZip").val() == "") {
					$("#cneeZip").focus();
					chkCnt++;
					return;
				}

				if($("#cneeTel").val() == "") {
					$("#cneeTel").focus();
					chkCnt++;
					return;
				}
/*
				if($("input[name='taxType']:checked").val() == "Y") {
					for (var i = 0; i < $(".taxTypeValue").size(); i++) {
						if ($($(".taxTypeValue")[i]).val().length == 0) {
							alert("위약반송 필수 서류가 없습니다.");
							chkCnt++;
							return;
						}
					}
				}
*/
				$(".itemDetail").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						chkCnt++;
						return;
					}
				})

				$(".itemCnt").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						chkCnt++;
						return;
					}
				})

				$(".unitValue").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						chkCnt++;
						return;
					}
				})

				$(".makeCntry").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						chkCnt++;
						return;
					}
				})

				$(".hsCode").each(function(index, item) {
					if ($(item).val() == "") {
						$(item).focus();
						chkCnt++;
						return;
					}
				})

				if($("#orderDate").val() == "") {
					$("#orderDate").val(orderDate);
				} 
				
				if (chkCnt == 0) {
					$("#form").attr("action", "/cstmr/return/orderIn");
					$("#form").attr("method", "post");
					$("#form").submit();
				}
			});

			$("#search-btn").on('click', function() {

				if ($("#koblNo").val() == "") {
					alert("원운송장번호를 기입해 주세요.");
					return;	
				}
				
				if ($("#siteType").val() == 'ESHOP') {
					$("#setKoblNo").val($("#koblNo").val());
					$("#setType").val("e");

					$.ajax({
						url : '/cstmr/return/checkEshopBl',
						type : 'POST',
						data : {bl: $("#setKoblNo").val()},
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							if (data.code == 'S') {
								$("#form2").attr("action", "/cstmr/return/orderInAci");
								$("#form2").attr("method", "get");
								$("#form2").submit();
							} else {
								alert(data.msg);
								return;
							}
						},
						error : function(xhr, status) {
							alert("내부 처리 중 오류가 발생 하였습니다. 관리자에게 문의해 주세요.");
						}
					})
					
				} else {

					$("#setKoblNo").val($("#koblNo").val());
					$("#setType").val("w");
					
					$.ajax({
						url : '/cstmr/return/checkBl',
						type : 'POST',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {bl: $("#setKoblNo").val(), siteType: "w"},
						success : function(data) {
							if (data.code == 'F') {
								alert(data.msg);
								return;
							} else {
								$("#form2").attr("action", "/cstmr/return/orderInAci");
								$("#form2").attr("method", "get");
								$("#form2").submit();
							}
						},
						error : function(xhr, status) {
							alert("내부 처리 중 오류가 발생 하였습니다. 관리자에게 문의해 주세요.");
						}
					})
				}

			})
		})
		
		function fn_deleteItem(obj) {
			if ($(".panel").length > 1) {
				obj.closest('.panel').remove();
			} else {
				alert("최소 1개의 상품 정보를 입력해야 합니다.");
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