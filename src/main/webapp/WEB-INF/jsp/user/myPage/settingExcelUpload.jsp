<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}

.column{
	border:none !important;
	border-bottom:1px solid gray !important; 
	border-radius: 0rem !important; 
}

input[readonly]{
	background: white !important; 
}

input.yellow{
	background: yellow !important; 
}

</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
</head>
<title>마이페이지</title>
<body class="no-skin">
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
						<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>마이 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">ExcelUpload Form 설정</strong></div>
					</div>
				</div>
			</div>
			<div class="container">
				<div class="page-content">
					<div class="page-header">
						<h1>ExcelUpload Form 설정</h1>
					</div>
					<form name="userInfoForm" id="userInfoForm">
						<div id="inner-content-side">
							<div class="row">
								<div class="col-xs-12 col-lg-12" >
									<label>
										<h4>국가</h4>
										<select id="orgNationOptions" name="orgNationOptions" style="width:150px;">
											<option value="">선택하세요.</option>
											<c:forEach items="${orgNationOption}" var="orgNationOptionInfo" varStatus="orgNationOptionStatus">
												<option value="${orgNationOptionInfo}">${orgNationOptionInfo }</option>
											</c:forEach>
										</select>
									</label>
									<label>
										<h4>출발 도시</h4>
										<select id="orgStationOptions" name="orgStationOptions" style="width:150px;">
											<option value="">선택하세요</option>
										</select>
									</label>
									<label>
										<h4>도착 국가</h4>
										<select id="dstnNationOptions" name="dstnNationOptions" style="width:150px;">
											<option value="">선택하세요.</option>
										</select>
									</label>
									<label>
										<h4>upload 설정</h4>
										<select id="transCodeOptions" name="transCodeOptions" style="width:150px;">
											<option value="0">선택하세요.</option>
										</select>
									</label>
									<label>
										<button id="loadUploadInfo" type="button" class="btn btn-sm btn-success">불러오기</button>
									</label>
								</div>
							</div>
							<br/>
							<br/>
							<br/>
							<div id="field" name="field">
							<!-- <div class="row">
								<div class="col-xs-12 col-lg-6" >
									기본정보 Start
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<label><h2>배송 기본 정보</h2></label>
									</div>
									style="border:1px solid red"
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div style="text-align: right; color:red;"> 열은 Excel의 영문자 열</div>
										<br/>
										<div class="row form-group hr-8">
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text" value="구분 번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="orderNo" id="orderNo" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="주문 날짜" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="orderDate" id="orderDate" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="출발도시 코드" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="orgStation" id="orgStation" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="도착국가 코드" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="dstnNation" id="dstnNation" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="PAYMENT" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="payment" id="payment" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 명" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperName" id="shipperName" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 전화번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperTel" id="shipperTel" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 전화번호2" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperHp" id="shipperHp" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 우편번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperZip" id="shipperZip" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 주" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperState" id="shipperState" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 도시" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperCity" id="shipperCity" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperAddr" id="shipperAddr" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER 상세 주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperAddrDetail" id="shipperAddrDetail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER E-Mail" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperEmail" id="shipperEmail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="SHIPPER Reference" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="shipperReference" id="shippperReference" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 명" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeName" id="cneeName" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 전화번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeTel" id="cneeTel" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 전화번호2" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeHp" id="cneeHp" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 우편번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeZip" id="cneeZip" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 주" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeState" id="cneeState" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 도시" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeCity" id="cneeCity" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeAddr" id="cneeAddr" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 상세 주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeAddrDetail" id="cneeAddrDetail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee E-Mail" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeEmail" id="cneeEmail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee Reference1" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeReference1" id="cneeReference1" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee Reference2" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeReference2" id="cneeReference2" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 요청사항" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="dlvReqMsg" id="dlvReqMsg" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Box 수량" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="boxCnt" id="boxCnt" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="무게" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="userWta" id="userWta" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="부피 무게" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="userWtc" id="userWtc" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="무게 단위" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="wtUnit" id="wtUni" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="길이" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="userLength" id="userLength" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="폭" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="userWidth" id="userWidth" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="높이" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="userHeight" id="userHeight" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="측정 단위" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="dimUnit" id="dimUnit" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="입고 메모" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="whReqMsg" id="whReqMsg" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="구매 사이트" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="buySite" id="buySite" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="용도" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="getBuy" id="getBuy" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상거래 유형" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="mallType" id="mallType" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 현지명" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nativeCneeName" id="nativeCneeName" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 현지주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nativeCneeAddr" id="nativeCneeAddr" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 현지 상세주소" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="개인 구별 번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="customsNo" id="customsNo" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee ID 발급일" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nationalIdDate" id="nationalIdDate" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="ID번호 발급기관 정보" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nationalIdAuthority" id="nationalIdAuthority" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee 생년월일" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="cneeBirth" id="cneeBirth" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="Conginee Taax 번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="taxNo" id="taxNo" value="" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-lg-6">
									<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
										<h2>배송 상품 정보</h2>
									</div>
									<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
										<div style="text-align: right; color:red;"> 열은 Excel의 영문자 열</div>
										<br/>
										<div class="row form-group hr-8">
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
											<div class="col-lg-3 col-xs-12" style="text-align: center;">
												Column 이름
											</div>
											<div class="col-lg-1 col-xs-12" style="text-align: center;">
												열
											</div>
										</div>
										<div class="row form-group hr-8">
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 이름" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemDetail" id="itemDetail" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="HS CODE" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="hsCode" id="hsCode" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 개수" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemCnt" id="itemCnt" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 단가" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemCnt" id="itemCnt" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="화폐단위" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="chgCurrency" id="chgCurrency" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 무게" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemWta" id="itemWta" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 부피무게" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemWtc" id="itemWtc" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="수량단위" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="qtyUnit" id="qtyUnit" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="브랜드" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="brand" id="brand" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="ITEM CODE" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemCode" id="itemCode" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="현지 택배회사" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="trkCom" id="trkCom" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="현지 택배 번호" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="trkNum" id="trkNum" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="배송 날짜" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="deliveryDate" id="deliveryDate" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 URL" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemUrl" id="itemUrl" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 사진 URL" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemImgUrl" id="itemImgUrl" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 종류" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemDiv" id="itemDiv" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="제조국" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="makeCntry" id="makeCntry" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="제조 회사" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="makeCom" id="makeCom" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 현지 이름" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="nativeItemDetail" id="nativeItemDetail" value="" />
											</div>
											
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="패키지 단위" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="packageUnit" id="packageUnit" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="음식 포함 여부" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="food" id="food" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 설명" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemExplan" id="itemExplan" value="" />
											</div>
											<div class="col-lg-3 col-xs-12">
												<input class="width-100 column" readonly type="text"  value="상품 바코드" />
											</div>
											<div class="col-lg-1 col-xs-12">
												<input class="width-100 column" type="text" maxlength="3" name="itemBarcode" id="itemBarcode" value="" />
											</div>
										</div>
										
									</div>
								</div>
								세관신고 정보 End
							</div>
							<br/>
							<br/>
							<br/>
							<div class="row">
								<div class="col-xs-12" style="text-align: center;">
									<button id="registUploadInfo" type="button" class="btn btn-sm btn-primary">저장</button>
								</div>
							</div> -->
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div><!-- /.main-content -->
	</div>
</div>





	<!-- headMenu Start -->
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<!-- SideMenu Start -->
		<!-- SideMenu End -->

		<div class="main-content">

			
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
	<!-- ace scripts -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
		var pwCheck = "N";
		jQuery(function($) {
			$(document).ready(function() {
				$("#10thMenu").toggleClass('open');
				$("#10thMenu").toggleClass('active');
				$("#10thTwo").toggleClass('active');
			});
		});

		$('#orgNationOptions').on('change',function(e){
			var orgNationOptionsInfo = $('#orgNationOptions').val();
			$.ajax({
				url:'/cstmr/myPage/orgStationOption',
				type:'GET',
				beforeSend : function(xhr)
				{
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : {
					orgNationOptionsInfo : orgNationOptionsInfo
				},
				success: function(data) {
					$('#orgStationOptions').empty();
					if(data.length != 0){
						var option = $("<option value=''>선택하세요.</option>");
						$('#orgStationOptions').append(option);
						for(var i = 0; i < data.length; i++){
							option = $("<option value='"+data[i].stationCode+"'>"+data[i].stationName+"</option>");
							$('#orgStationOptions').append(option);
						}
					}else{
						var option = $("<option value=''>국가를 선택하세요.</option>");
						$('#orgStationOptions').append(option);
					}
					
				},
				error : function(xhr, status) {
					alert(xhr + " : " + status);
					alert("선택 값을 확인해 주세요");
				}

			})

		})
		
		$('#orgStationOptions').on('change',function(e){
			var orgStationOptionsInfo = $('#orgStationOptions').val();
			var orgNationOptionsInfo = $('#orgNationOptions').val();
			$.ajax({
				url:'/cstmr/myPage/dstnNationOptions',
				type:'GET',
				beforeSend : function(xhr)
				{
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : {
					orgNationOptionsInfo : orgNationOptionsInfo,
					orgStationOptionsInfo : orgStationOptionsInfo
				},
				success: function(data) {
					$('#dstnNationOptions').empty();
					if(data.length != 0){
						var option = $("<option value=''>선택하세요.</option>");
						$('#dstnNationOptions').append(option);
						for(var i = 0; i < data.length; i++){
							option = $("<option value='"+data[i]+"'>"+data[i]+"</option>");
							$('#dstnNationOptions').append(option);
						}
					}else{
						var option = $("<option value=''>출발 도시를 선택하세요.</option>");
						$('#dstnNationOptions').append(option);
					}
					
				},
				error : function(xhr, status) {
					alert(xhr + " : " + status);
					alert("선택 값을 확인해 주세요");
				}

			})

		})
		
		$('#dstnNationOptions').on('change',function(e){
			var orgStationOptionsInfo = $('#orgStationOptions').val();
			var orgNationOptionsInfo = $('#orgNationOptions').val();
			var dstnNationOptionsInfo = $('#dstnNationOptions').val();
			dstnNationOptions
			$.ajax({
				url:'/cstmr/myPage/transCodeOptions',
				type:'GET',
				beforeSend : function(xhr)
				{
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				data : {
					orgNationOptionsInfo : orgNationOptionsInfo,
					dstnNationOptionsInfo : dstnNationOptionsInfo,
					orgStationOptionsInfo : orgStationOptionsInfo
				},
				success: function(data) {
					$('#transCodeOptions').empty();
					if(data.length != 0){
						var option = $("<option value=''>선택하세요.</option>");
						$('#transCodeOptions').append(option);
						for(var i = 0; i < data.length; i++){
							option = $("<option value='"+data[i]+"'>"+data[i]+"</option>");
							$('#transCodeOptions').append(option);
						}
					}else{
						var option = $("<option value=''>도착 국가를 선택하세요.</option>");
						$('#transCodeOptions').append(option);
					}
					
				},
				error : function(xhr, status) {
					alert(xhr + " : " + status);
					alert("선택 값을 확인해 주세요");
				}

			})

		})
		
		$('#loadUploadInfo').on('click',function(e){
			var orgStationOptionsInfo = $('#orgStationOptions').val();
			var orgNationOptionsInfo = $('#orgNationOptions').val();
			var dstnNationOptionsInfo = $('#dstnNationOptions').val();
			var transCodeOptionsInfo = $('#transCodeOptions').val();
			
			$.ajax({
				url:'/cstmr/myPage/uploadExcelFormField',
				data:{
					orgNationOptionsInfo : orgNationOptionsInfo,
					dstnNationOptionsInfo : dstnNationOptionsInfo,
					orgStationOptionsInfo : orgStationOptionsInfo,
					transCodeOptionsInfo : transCodeOptionsInfo
				},
				type: 'GET',
				success : function(data) {
					$('#field').empty();
					$('#field').html(data);
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
	            }
			}) 
		})
		
		
		
	</script>
	<!-- script addon end -->
</body>
</html>