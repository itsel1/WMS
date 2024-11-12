<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:120px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	
	.tableTr{
		 
	}
	
	.disflex{
		display: flex;
	}
	
	.tableTd{
		border-bottom: 1px solid black !important;
		border-right: 1px solid black !important;
		word-break:break-all;
	}
	
	.leftLine{
		border-left: 1px solid black !important;
	}
	.row{
		width:100%;
		
	}
	
	.center{
		text-align: center;
	}
	
	</style>
	
	</head> 
	<body class="no-skin" style="background-color: white;">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="navbar" class="navbar navbar-default ace-save-state ">
			<div class="navbar-container ace-save-state" id="navbar-container">
				<div class="navbar-header pull-left">
					<a class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							필수 입력 사항 설정 - 코드 : ${targetCode} 
						</small>
					</a>
					
				</div>
				<div class="navbar-header pull-right" style="margin-top: 5px;">
					<select id="dstnNationSel" name="dstnNationSel">
						<c:forEach items="${dstnNationList}" var="dstnNationVal">
							<option value="${dstnNationVal}" <c:if test="${dstnNationVal eq dstnNation}">selected</c:if>>${dstnNationVal}</option>
						</c:forEach>
					</select>
				</div>
			</div><!-- /.navbar-container -->
		</div>
		<form name="optionForm" id="optionForm" >
			<input type="hidden" id="targetCode" name="targetCode" value="${targetCode }">
			<div class="main-container ace-save-state" id="main-container">
				<div class="main-content">
					<div class="main-content-inner">
						<div class="tableTr row">
							<div class="tableTd col-xs-12 center">
								<h3>발송 정보</h3>
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								이름
							</div>
							<div class="tableTd col-xs-1">
								전화번호
							</div>
							<div class="tableTd col-xs-1">
								핸드폰 번호
							</div>
							<div class="tableTd col-xs-1">
								국가
							</div>
							<div class="tableTd col-xs-1">
								도시
							</div>
							<div class="tableTd col-xs-1">
								주
							</div>
							<div class="tableTd col-xs-1">
								우편주소	
							</div>
							<div class="tableTd col-xs-1">
								주소
							</div>
							<div class="tableTd col-xs-1">
								상세주소
							</div>
							<div class="tableTd col-xs-1">
								Email
							</div>
							<div class="tableTd col-xs-1">
								입고 메모
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperNameYn" upClas <c:if test="${!empty optionOrderVO.shipperNameYn}"> checked </c:if> name="shipperNameYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTelYn" upClas <c:if test="${!empty optionOrderVO.shipperTelYn}"> checked </c:if> name="shipperTelYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperHpYn" upClas <c:if test="${!empty optionOrderVO.shipperHpYn}"> checked </c:if> name="shipperHpYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperCntryYn" upClas <c:if test="${!empty optionOrderVO.shipperCntryYn}"> checked </c:if> name="shipperCntryYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperCityYn" upClas <c:if test="${!empty optionOrderVO.shipperCityYn}"> checked </c:if> name="shipperCityYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperStateYn" upClas <c:if test="${!empty optionOrderVO.shipperStateYn}"> checked </c:if> name="shipperStateYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperZipYn" upClas <c:if test="${!empty optionOrderVO.shipperZipYn}"> checked </c:if> name="shipperZipYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperAddrYn" upClas <c:if test="${!empty optionOrderVO.shipperAddrYn}"> checked </c:if> name="shipperAddrYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperAddrDetailYn" upClas <c:if test="${!empty optionOrderVO.shipperAddrDetailYn}"> checked </c:if> name="shipperAddrDetailYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userEmailYn" upClas <c:if test="${!empty optionOrderVO.userEmailYn}"> checked </c:if> name="userEmailYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="whReqMsgYn" upClas <c:if test="${!empty optionOrderVO.whReqMsgYn}"> checked </c:if> name="whReqMsgYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperNameYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperNameYnExpress}"> checked </c:if> name="shipperNameYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTelYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperTelYnExpress}"> checked </c:if> name="shipperTelYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperHpYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperHpYnExpress}"> checked </c:if> name="shipperHpYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperCntryYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperCntryYnExpress}"> checked </c:if> name="shipperCntryYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperCityYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperCityYnExpress}"> checked </c:if> name="shipperCityYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperStateYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperStateYnExpress}"> checked </c:if> name="shipperStateYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperZipYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperZipYnExpress}"> checked </c:if> name="shipperZipYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperAddrYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperAddrYnExpress}"> checked </c:if> name="shipperAddrYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperAddrDetailYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperAddrDetailYnExpress}"> checked </c:if> name="shipperAddrDetailYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userEmailYnExpress" doClas <c:if test="${!empty expressOrderVO.userEmailYnExpress}"> checked </c:if> name="userEmailYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="whReqMsgYnExpress" doClas <c:if test="${!empty expressOrderVO.whReqMsgYnExpress}"> checked </c:if> name="whReqMsgYnExpress">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							
							<div class="tableTd col-xs-1">
								발송 Reference
							</div>
							<div class="tableTd col-xs-1">
								세금 식별 코드
							</div>
							<div class="tableTd col-xs-1">
								세금 식별 번호
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperReferenceYn" upClas <c:if test="${!empty optionOrderVO.shipperReferenceYn}"> checked </c:if> name="shipperReferenceYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTaxTypeYn" upClas <c:if test="${!empty optionOrderVO.shipperTaxTypeYn}"> checked </c:if> name="shipperTaxTypeYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTaxNoYn" upClas <c:if test="${!empty optionOrderVO.shipperTaxNoYn}"> checked </c:if> name="shipperTaxNoYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperReferenceYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperReferenceYnExpress}"> checked </c:if> name="shipperReferenceYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTaxTypeYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperTaxTypeYnExpress}"> checked </c:if> name="shipperTaxTypeYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="shipperTaxNoYnExpress" doClas <c:if test="${!empty expressOrderVO.shipperTaxNoYnExpress}"> checked </c:if> name="shipperTaxNoYnExpress">
							</div>
						</div>
						
						<br>
						
						<div class="tableTr row" style="border-top: 1px solid black;">
							<div class="tableTd col-xs-12 center">
								<h3>수취 정보</h3>
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								이름
							</div>
							<div class="tableTd col-xs-1">
								전화번호
							</div>
							<div class="tableTd col-xs-1">
								핸드폰 번호
							</div>
							<div class="tableTd col-xs-1">
								국가
							</div>
							<div class="tableTd col-xs-1">
								도시 city<br>/province
							</div>
							<div class="tableTd col-xs-1">
								주 state
							</div>
							<div class="tableTd col-xs-1">
								구 district
							</div>
							<div class="tableTd col-xs-1">
								동 ward
							</div>
							<div class="tableTd col-xs-1">
								우편주소	
							</div>
							<div class="tableTd col-xs-1">
								주소
							</div>
							<div class="tableTd col-xs-1">
								상세주소
							</div>
							
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeNameYn" upClas <c:if test="${!empty optionOrderVO.cneeNameYn}"> checked </c:if> name="cneeNameYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTelYn" upClas <c:if test="${!empty optionOrderVO.cneeTelYn}"> checked </c:if> name="cneeTelYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeHpYn" upClas <c:if test="${!empty optionOrderVO.cneeHpYn}"> checked </c:if> name="cneeHpYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeCntryYn" upClas <c:if test="${!empty optionOrderVO.cneeCntryYn}"> checked </c:if> name="cneeCntryYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeCityYn" upClas <c:if test="${!empty optionOrderVO.cneeCityYn}"> checked </c:if> name="cneeCityYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeStateYn" upClas <c:if test="${!empty optionOrderVO.cneeStateYn}"> checked </c:if> name="cneeStateYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeDistrictYn" upClas <c:if test="${!empty optionOrderVO.cneeDistrictYn}"> checked </c:if> name="cneeDistrictYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeWardYn" upClas <c:if test="${!empty optionOrderVO.cneeWardYn}"> checked </c:if> name="cneeWardYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeZipYn" upClas <c:if test="${!empty optionOrderVO.cneeZipYn}"> checked </c:if> name="cneeZipYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeAddrYn" upClas <c:if test="${!empty optionOrderVO.cneeAddrYn}"> checked </c:if> name="cneeAddrYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeAddrDetailYn" upClas <c:if test="${!empty optionOrderVO.cneeAddrDetailYn}"> checked </c:if> name="cneeAddrDetailYn">
							</div>
							
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeNameYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeNameYnExpress}"> checked </c:if> name="cneeNameYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTelYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeTelYnExpress}"> checked </c:if> name="cneeTelYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeHpYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeHpYnExpress}"> checked </c:if> name="cneeHpYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeCntryYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeCntryYnExpress}"> checked </c:if> name="cneeCntryYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeCityYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeCityYnExpress}"> checked </c:if> name="cneeCityYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeStateYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeStateYnExpress}"> checked </c:if> name="cneeStateYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeDistrictYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeDistrictYnExpress}"> checked </c:if> name="cneeDistrictYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeWardYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeWardYnExpress}"> checked </c:if> name="cneeWardYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeZipYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeZipYnExpress}"> checked </c:if> name="cneeZipYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeAddrYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeAddrYnExpress}"> checked </c:if> name="cneeAddrYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeAddrDetailYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeAddrDetailYnExpress}"> checked </c:if> name="cneeAddrDetailYnExpress">
							</div>
							
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								Email
							</div>
							<div class="tableTd col-xs-1">
								수취인 메모
							</div>
							<div class="tableTd col-xs-1">
								신분증번호
							</div>
							<div class="tableTd col-xs-1">
								수취인 Reference1
							</div>
							<div class="tableTd col-xs-1">
								수취인 Reference2
							</div>
							<div class="tableTd col-xs-1">
								세금 식별 코드
							</div>
							<div class="tableTd col-xs-1">
								세금 식별 번호
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeEmailYn" upClas <c:if test="${!empty optionOrderVO.cneeEmailYn}"> checked </c:if> name="cneeEmailYn">
							</div>
							
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="dlvReqMsgYn" upClas <c:if test="${!empty optionOrderVO.dlvReqMsgYn}"> checked </c:if> name="dlvReqMsgYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="customsNoYn" upClas <c:if test="${!empty optionOrderVO.customsNoYn}"> checked </c:if> name="customsNoYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeReference1Yn" upClas <c:if test="${!empty optionOrderVO.cneeReference1Yn}"> checked </c:if> name="cneeReference1Yn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeReference2Yn" upClas <c:if test="${!empty optionOrderVO.cneeReference2Yn}"> checked </c:if> name="cneeReference2Yn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTaxTypeYn" upClas <c:if test="${!empty optionOrderVO.cneeTaxTypeYn}"> checked </c:if> name="cneeTaxTypeYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTaxNoYn" upClas <c:if test="${!empty optionOrderVO.cneeTaxNoYn}"> checked </c:if> name="cneeTaxNoYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeEmailYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeEmailYnExpress}"> checked </c:if> name="cneeEmailYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="dlvReqMsgYnExpress" doClas <c:if test="${!empty expressOrderVO.dlvReqMsgYnExpress}"> checked </c:if> name="dlvReqMsgYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="customsNoYnExpress" doClas <c:if test="${!empty expressOrderVO.customsNoYnExpress}"> checked </c:if> name="customsNoYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeReference1YnExpress" doClas <c:if test="${!empty expressOrderVO.cneeReference1YnExpress}"> checked </c:if> name="cneeReference1YnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeReference2YnExpress" doClas <c:if test="${!empty expressOrderVO.cneeReference2YnExpress}"> checked </c:if> name="cneeReference2YnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTaxTypeYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeTaxTypeYnExpress}"> checked </c:if> name="cneeTaxTypeYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cneeTaxNoYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeTaxNoYnExpress}"> checked </c:if> name="cneeTaxNoYnExpress">
							</div>
						</div>
						
						
						<br>
						
						
						<div class="tableTr row" style="border-top: 1px solid black;">
							<div class="tableTd col-xs-12 center">
								<h3>운송장 정보</h3>
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								주문 번호
							</div>
							<div class="tableTd col-xs-1">
								주문 날짜
							</div>
							<div class="tableTd col-xs-1">
								구매 사이트
							</div>
							<div class="tableTd col-xs-1">
								Height
							</div>
							<div class="tableTd col-xs-1">
								Length
							</div>
							<div class="tableTd col-xs-1">
								Width
							</div>
							<div class="tableTd col-xs-1">
								길이 단위	
							</div>
							<div class="tableTd col-xs-1">
								실무게
							</div>
							<div class="tableTd col-xs-1">
								부피무게
							</div>
							<div class="tableTd col-xs-1">
								박스 개수
							</div>
							<div class="tableTd col-xs-1">
								무게 단위
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="orderNoYn" upClas <c:if test="${!empty optionOrderVO.orderNoYn}"> checked </c:if> name="orderNoYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="orderDateYn" upClas <c:if test="${!empty optionOrderVO.orderDateYn}"> checked </c:if> name="orderDateYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="buySiteYn" upClas <c:if test="${!empty optionOrderVO.buySiteYn}"> checked </c:if> name="buySiteYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userHeightYn" upClas <c:if test="${!empty optionOrderVO.userHeightYn}"> checked </c:if> name="userHeightYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userLengthYn" upClas <c:if test="${!empty optionOrderVO.userLengthYn}"> checked </c:if> name="userLengthYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWidthYn" upClas <c:if test="${!empty optionOrderVO.userWidthYn}"> checked </c:if> name="userWidthYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="dimUnitYn" upClas <c:if test="${!empty optionOrderVO.dimUnitYn}"> checked </c:if> name="dimUnitYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWtaYn" upClas <c:if test="${!empty optionOrderVO.userWtaYn}"> checked </c:if> name="userWtaYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWtcYn" upClas <c:if test="${!empty optionOrderVO.userWtcYn}"> checked </c:if> name="userWtcYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="boxCntYn" upClas <c:if test="${!empty optionOrderVO.boxCntYn}"> checked </c:if> name="boxCntYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="wtUnitYn" upClas <c:if test="${!empty optionOrderVO.wtUnitYn}"> checked </c:if> name="wtUnitYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="orderNoYnExpress" doClas <c:if test="${!empty expressOrderVO.orderNoYnExpress}"> checked </c:if> name="orderNoYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="orderDateYnExpress" doClas <c:if test="${!empty expressOrderVO.orderDateYnExpress}"> checked </c:if> name="orderDateYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="buySiteYnExpress" doClas <c:if test="${!empty expressOrderVO.buySiteYnExpress}"> checked </c:if> name="buySiteYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userHeightYnExpress" doClas <c:if test="${!empty expressOrderVO.userHeightYnExpress}"> checked </c:if> name="userHeightYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userLengthYnExpress" doClas <c:if test="${!empty expressOrderVO.userLengthYnExpress}"> checked </c:if> name="userLengthYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWidthYnExpress" doClas <c:if test="${!empty expressOrderVO.userWidthYnExpress}"> checked </c:if> name="userWidthYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="dimUnitYnExpress" doClas <c:if test="${!empty expressOrderVO.dimUnitYnExpress}"> checked </c:if> name="dimUnitYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWtaYnExpress" doClas <c:if test="${!empty expressOrderVO.userWtaYnExpress}"> checked </c:if> name="userWtaYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="userWtcYnExpress" doClas <c:if test="${!empty expressOrderVO.userWtcYnExpress}"> checked </c:if> name="userWtcYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="boxCntYnExpress" doClas <c:if test="${!empty expressOrderVO.boxCntYnExpress}"> checked </c:if> name="boxCntYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="wtUnitYnExpress" doClas <c:if test="${!empty expressOrderVO.wtUnitYnExpress}"> checked </c:if> name="wtUnitYnExpress">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								사용 용도
							</div>
							<div class="tableTd col-xs-1">
								세관신고 항목
							</div>
							<div class="tableTd col-xs-1">
								음식 포함 여부
							</div>
							<div class="tableTd col-xs-1">
								화장품 포함 여부
							</div>
							<div class="tableTd col-xs-1">
								대면 서명 여부
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="getBuyYn" upClas <c:if test="${!empty optionOrderVO.getBuyYn}"> checked </c:if> name="getBuyYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="declTypeYn" upClas <c:if test="${!empty optionOrderVO.declTypeYn}"> checked </c:if> name="declTypeYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="foodYn" upClas <c:if test="${!empty optionOrderVO.foodYn}"> checked </c:if> name="foodYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cosmeticYn" upClas <c:if test="${!empty optionOrderVO.cosmeticYn}"> checked </c:if>name="cosmeticYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="signYn" upClas <c:if test="${!empty optionOrderVO.signYn}"> checked </c:if>name="signYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="getBuyYnExpress" doClas <c:if test="${!empty expressOrderVO.getBuyYnExpress}"> checked </c:if> name="getBuyYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="declTypeYnExpress" doClas <c:if test="${!empty expressOrderVO.declTypeYnExpress}"> checked </c:if> name="declTypeYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="foodYnExpress" doClas <c:if test="${!empty expressOrderVO.foodYnExpress}"> checked </c:if> name="foodYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cosmeticYnExpress" doClas <c:if test="${!empty expressOrderVO.cosmeticYnExpress}"> checked </c:if> name="cosmeticYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="signYnExpress" doClas <c:if test="${!empty expressOrderVO.signYnExpress}"> checked </c:if> name="signYnExpress">
							</div>
						</div>
						
						<br>
						
						
						
						<div class="tableTr row" style="border-top: 1px solid black;">
							<div class="tableTd col-xs-12 center">
								<h3>상품 정보</h3>
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
							</div>
							<div class="tableTd col-xs-1">
								HS CODE
							</div>
							<div class="tableTd col-xs-1">
								상품명
							</div>
							<div class="tableTd col-xs-1">
								상품 재질
							</div>
							
							<div class="tableTd col-xs-1">
								상품 개수
							</div>
							<div class="tableTd col-xs-1">
								상품 단가
							</div>
							<div class="tableTd col-xs-1">
								브랜드
							</div>
							<div class="tableTd col-xs-1">
								제조국	
							</div>
							<div class="tableTd col-xs-1">
								제조 회사
							</div>
							<div class="tableTd col-xs-1">
								상품 종류
							</div>
							<div class="tableTd col-xs-1">
								무게 단위
							</div>
							<div class="tableTd col-xs-1">
								상품 단위
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="hsCodeYn" upClas <c:if test="${!empty optionItemVO.hsCodeYn}"> checked </c:if> name="hsCodeYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemDetailYn" upClas <c:if test="${!empty optionItemVO.itemDetailYn}"> checked </c:if> name="itemDetailYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemMeterialYn" upClas <c:if test="${!empty optionItemVO.itemMeterialYn}"> checked </c:if> name="itemMeterialYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemCntYn" upClas <c:if test="${!empty optionItemVO.itemCntYn}"> checked </c:if> name="itemCntYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="unitValueYn" upClas <c:if test="${!empty optionItemVO.unitValueYn}"> checked </c:if> name="unitValueYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="brandYn" upClas <c:if test="${!empty optionItemVO.brandYn}"> checked </c:if> name="brandYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="makeCntryYn" upClas <c:if test="${!empty optionItemVO.makeCntryYn}"> checked </c:if> name="makeCntryYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="makeComYn" upClas <c:if test="${!empty optionItemVO.makeComYn}"> checked </c:if> name="makeComYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemDivYn" upClas <c:if test="${!empty optionItemVO.itemDivYn}"> checked </c:if> name="itemDivYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemWtUnitYn" upClas <c:if test="${!empty optionItemVO.itemWtUnitYn}"> checked </c:if> name="itemWtUnitYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="qtyUnitYn" upClas <c:if test="${!empty optionItemVO.qtyUnitYn}"> checked </c:if> name="qtyUnitYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="hsCodeYnExpress" doClas <c:if test="${!empty expressItemVO.hsCodeYnExpress}"> checked </c:if> name="hsCodeYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemDetailYnExpress" doClas <c:if test="${!empty expressItemVO.itemDetailYnExpress}"> checked </c:if> name="itemDetailYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemMeterialYnExpress" doClas <c:if test="${!empty expressItemVO.itemMeterialYnExpress}"> checked </c:if> name="itemMeterialYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemCntYnExpress" doClas <c:if test="${!empty expressItemVO.itemCntYnExpress}"> checked </c:if> name="itemCntYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="unitValueYnExpress" doClas <c:if test="${!empty expressItemVO.unitValueYnExpress}"> checked </c:if> name="unitValueYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="brandYnExpress" doClas <c:if test="${!empty expressItemVO.brandYnExpress}"> checked </c:if> name="brandYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="makeCntryYnExpress" doClas <c:if test="${!empty expressItemVO.makeCntryYnExpress}"> checked </c:if> name="makeCntryYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="makeComYnExpress" doClas <c:if test="${!empty expressItemVO.makeComYnExpress}"> checked </c:if> name="makeComYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemDivYnExpress" doClas <c:if test="${!empty expressItemVO.itemDivYnExpress}"> checked </c:if> name="itemDivYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemWtUnitYnExpress" doClas <c:if test="${!empty expressItemVO.itemWtUnitYnExpress}"> checked </c:if> name="itemWtUnitYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="qtyUnitYnExpress" doClas <c:if test="${!empty expressItemVO.qtyUnitYnExpress}"> checked </c:if> name="qtyUnitYnExpress">
							</div>
						</div>
						
						
						
						<br>
						
						
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
							</div>
							<!-- <div class="tableTd col-xs-1">
								환율
							</div>
							<div class="tableTd col-xs-1">
								환전 여부
							</div>
							<div class="tableTd col-xs-1">
								환전 금액
							</div> -->
							
							<!-- <div class="tableTd col-xs-1">
								사입 코드
							</div> -->
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								통화 단위
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								상품 코드
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								상품 무게
							</div> 
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								상품 URL
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								상품 이미지 URL
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								국내 택배 회사
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								운송장 번호
							</div>
							<div class="tableTd col-xs-1" style="border-top: 1px solid black;">
								운송 날짜
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
									필수 여부
							</div>
							<%-- <div class="tableTd col-xs-1">
								<input type="checkbox" id="exchangeRateYn" upClas <c:if test="${!empty optionItemVO.exchangeRateYn}"> checked </c:if> name="exchangeRateYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgCurrencyYn" upClas <c:if test="${!empty optionItemVO.chgCurrencyYn}"> checked </c:if> name="chgCurrencyYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgAmtYn" upClas <c:if test="${!empty optionItemVO.chgAmtYn}"> checked </c:if> name="chgAmtYn">
							</div> --%>
							
							<%-- <div class="tableTd col-xs-1">
								<input type="checkbox" id="takeInCodeYn" upClas <c:if test="${!empty optionItemVO.takeInCodeYn}"> checked </c:if> name="takeInCodeYn">
							</div> --%>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgCurrencyYn" upClas <c:if test="${!empty optionItemVO.chgCurrencyYn}"> checked </c:if> name="chgCurrencyYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cusItemCodeYn" upClas <c:if test="${!empty optionItemVO.cusItemCodeYn}"> checked </c:if> name="cusItemCodeYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemUserWtaYn" upClas <c:if test="${!empty optionItemVO.itemUserWtaYn}"> checked </c:if> name="itemUserWtaYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemUrlYn" upClas <c:if test="${!empty optionItemVO.itemUrlYn}"> checked </c:if> name="itemUrlYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemImgUrlYn" upClas <c:if test="${!empty optionItemVO.itemImgUrlYn}"> checked </c:if> name="itemImgUrlYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkComYn" upClas <c:if test="${!empty optionItemVO.trkComYn}"> checked </c:if> name="trkComYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkNoYn" upClas <c:if test="${!empty optionItemVO.trkNoYn}"> checked </c:if> name="trkNoYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkDateYn" upClas <c:if test="${!empty optionItemVO.trkDateYn}"> checked </c:if> name="trkDateYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1">
								노출 여부
							</div>
							<%-- <div class="tableTd col-xs-1">
								<input type="checkbox" id="exchangeRateYnExpress" doClas <c:if test="${!empty expressItemVO.exchangeRateYnExpress}"> checked </c:if> name="exchangeRateYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgCurrencyYnExpress" doClas <c:if test="${!empty expressItemVO.chgCurrencyYnExpress}"> checked </c:if> name="chgCurrencyYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgAmtYnExpress" doClas <c:if test="${!empty expressItemVO.chgAmtYnExpress}"> checked </c:if> name="chgAmtYnExpress">
							</div> --%>
							<%-- <div class="tableTd col-xs-1">
								<input type="checkbox" id="takeInCodeYnExpress" doClas <c:if test="${!empty expressItemVO.takeInCodeYnExpress}"> checked </c:if> name="takeInCodeYnExpress">
							</div> --%>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="chgCurrencyYnExpress" doClas <c:if test="${!empty expressItemVO.chgCurrencyYnExpress}"> checked </c:if> name="chgCurrencyYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="cusItemCodeYnExpress" doClas <c:if test="${!empty expressItemVO.cusItemCodeYnExpress}"> checked </c:if> name="cusItemCodeYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemUserWtaYnExpress" upClas <c:if test="${!empty expressItemVO.itemUserWtaYnExpress}"> checked </c:if> name="itemUserWtaYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemUrlYnExpress" doClas <c:if test="${!empty expressItemVO.itemUrlYnExpress}"> checked </c:if> name="itemUrlYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="itemImgUrlYnExpress" doClas <c:if test="${!empty expressItemVO.itemImgUrlYnExpress}"> checked </c:if> name="itemImgUrlYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkComYnExpress" doClas <c:if test="${!empty expressItemVO.trkComYnExpress}"> checked </c:if> name="trkComYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkNoYnExpress" doClas <c:if test="${!empty expressItemVO.trkNoYnExpress}"> checked </c:if> name="trkNoYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="trkDateYnExpress" doClas <c:if test="${!empty expressItemVO.trkDateYnExpress}"> checked </c:if> name="trkDateYnExpress">
							</div>
						</div>
						
						<br>
						<br>
						<div class="tableTr row" style="border-top: 1px solid black;">
							<div class="tableTd col-xs-12 center leftLine">
								<h3>현지 정보 추가</h3> 
							</div>
						</div>
						<div class="tableTr row center disflex" >
							<div class="tableTd col-xs-1 leftLine">
							</div>
							<div class="tableTd col-xs-1">
								현지 이름
							</div>
							<div class="tableTd col-xs-1">
								현지 주소
							</div>
							<div class="tableTd col-xs-1">
								현지 상세 주소 
							</div>
							<div class="tableTd col-xs-1">
								현지 상품명 
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1 leftLine">
									필수 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeNameYn" upClas <c:if test="${!empty optionOrderVO.nativeCneeNameYn}"> checked </c:if> name="nativeCneeNameYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeAddrYn" upClas <c:if test="${!empty optionOrderVO.nativeCneeAddrYn}"> checked </c:if> name="nativeCneeAddrYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeAddrDetailYn" upClas <c:if test="${!empty optionOrderVO.nativeCneeAddrDetailYn}"> checked </c:if> name="nativeCneeAddrDetailYn">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeItemDetailYn" upClas <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> checked </c:if> name="nativeItemDetailYn">
							</div>
						</div>
						<div class="tableTr row center disflex">
							<div class="tableTd col-xs-1 leftLine">
								노출 여부
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeNameYnExpress" doClas <c:if test="${!empty expressOrderVO.nativeCneeNameYnExpress}"> checked </c:if> name="nativeCneeNameYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeAddrYnExpress" doClas <c:if test="${!empty expressOrderVO.nativeCneeAddrYnExpress}"> checked </c:if> name="nativeCneeAddrYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeCneeAddrDetailYnExpress" doClas <c:if test="${!empty expressOrderVO.nativeCneeAddrDetailYnExpress}"> checked </c:if> name="nativeCneeAddrDetailYnExpress">
							</div>
							<div class="tableTd col-xs-1">
								<input type="checkbox" id="nativeItemDetailYnExpress" doClas <c:if test="${!empty expressItemVO.nativeItemDetailYnExpress}"> checked </c:if> name="nativeItemDetailYnExpress">
							</div>
						</div>
						
						<br>
						<br>
						
						<c:if test="${targetCode eq 'CSE' or targetCode eq 'GTS' }">
							<div class="tableTr row" style="border-top: 1px solid black;">
								<div class="tableTd col-xs-12 center leftLine">
									<h3>러시아 정보 추가 </h3> 
								</div>
							</div>
							<div class="tableTr row center disflex" >
								<div class="tableTd col-xs-1 leftLine">
								</div>
								<div class="tableTd col-xs-1">
									상품 설명
								</div>
								<div class="tableTd col-xs-1">
									수취인 ID 번호 발급인
								</div>
								<div class="tableTd col-xs-1">
									수취인 ID 번호 발급기관/이름/주소
								</div>
								<div class="tableTd col-xs-1">
									수취인 생년월일 
								</div>
								<div class="tableTd col-xs-1">
									수취인 TAX 번호
								</div>
								<div class="tableTd col-xs-1">
									수취인 Barcode 번호
								</div>
							</div>
							<div class="tableTr row center disflex">
								<div class="tableTd col-xs-1 leftLine">
										필수 여부
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="itemExplanYn" upClas <c:if test="${!empty optionItemVO.itemExplanYn}"> checked </c:if> name="itemExplanYn">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="nationalIdDateYn" upClas <c:if test="${!empty optionOrderVO.nationalIdDateYn}"> checked </c:if> name="nationalIdDateYn">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="nationalIdAuthorityYn" upClas <c:if test="${!empty optionOrderVO.nationalIdAuthorityYn}"> checked </c:if> name="nationalIdAuthorityYn">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="cneeBirthYn" upClas <c:if test="${!empty optionOrderVO.cneeBirthYn}"> checked </c:if> name="cneeBirthYn">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="taxNoYn" upClas <c:if test="${!empty optionOrderVO.taxNoYn}"> checked </c:if> name="taxNoYn">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="itemBarcodeYn" upClas <c:if test="${!empty optionItemVO.itemBarcodeYn}"> checked </c:if> name="itemBarcodeYn">
								</div>
							</div>
							<div class="tableTr row center disflex">
								<div class="tableTd col-xs-1 leftLine">
									노출 여부
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="itemExplanYnExpress" doClas <c:if test="${!empty expressItemVO.itemExplanYnExpress}"> checked </c:if> name="itemExplanYnExpress">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="nationalIdDateYnExpress" doClas <c:if test="${!empty expressOrderVO.nationalIdDateYnExpress}"> checked </c:if> name="nationalIdDateYnExpress">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="nationalIdAuthorityYnExpress" doClas <c:if test="${!empty expressOrderVO.nationalIdAuthorityYnExpress}"> checked </c:if> name="nationalIdAuthorityYnExpress">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="cneeBirthYnExpress" doClas <c:if test="${!empty expressOrderVO.cneeBirthYnExpress}"> checked </c:if> name="cneeBirthYnExpress">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="taxNoYnExpress" doClas <c:if test="${!empty expressOrderVO.taxNoYnExpress}"> checked </c:if> name="taxNoYnExpress">
								</div>
								<div class="tableTd col-xs-1">
									<input type="checkbox" id="itemBarcodeYnExpress" doClas <c:if test="${!empty expressItemVO.itemBarcodeYnExpress}"> checked </c:if> name="itemBarcodeYnExpress">
								</div>
							</div>
						</c:if>
						<br>
						<br>
						<div class="row">
							<div class="col-xs-12 center">
								<button type="button" class="btn btn-danger btn-md" id="backBtn" name="backBtn">
									닫기
								</button>
								<button type="button" class="btn btn-primary btn-md" id="registBtn" name="registBtn">
									저장
								</button>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</form>
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script addon start -->
		<script src="/assets/js/bootstrap.min.js"></script>
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
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min2.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		
		
		
		
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).load(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thTwo").toggleClass('active');
				});
				$("input:checkbox[upClas]").on("click", function() {
		            if($(this).is(":checked") == true){
		            	$("#"+$(this).attr("name")+"Express").prop("checked", true);
		            }else{
		            	$("#"+$(this).attr("name")+"Express").prop("checked", false);
		            }
		        });
				$("input:checkbox[doClas]").on("click", function() {
		            if($(this).is(":checked") == false){
		            	$("#"+$(this).attr("name").replace("Express","")).prop("checked", false);
		            }
		        });
			});

			$("#backBtn").on('click',function(e){
				window.close();

			});

			$("#registBtn").on('click',function(e){
				var formData = $("#optionForm").serialize();
				$.ajax({
					type : "POST",
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					url : "/mngr/unit/optionByTransCom/"+$("#targetCode").val()+"/"+$("#dstnNationSel").val(),
					error : function(){
						alert("subpage ajax Error");
					},
					success : function(rtnVal2){
						if(rtnVal2 == "S"){
							alert("등록 되었습니다.");
							location.href = "/mngr/unit/optionByTransCom/"+$("#targetCode").val()+"/"+$("#dstnNationSel").val();
						}else{
							alert(rtnVal2);
						}
					}
				})
			});

			$("#dstnNationSel").on('change',function(e){
				location.href="/mngr/unit/optionByTransCom/"+$("#targetCode").val()+"/"+$("#dstnNationSel").val();

			});
			
		/* 	$("#registBtn").on('click',function(){
				var formData = $("#weightForm").serialize();
				$.ajax({
					type : "POST",
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					url : "/mngr/rls/registStockOutVolume",
					error : function(){
						alert("subpage ajax Error");
					},
					success : function(rtnVal2){
						if(rtnVal2 == "S"){
							alert("등록 되었습니다.");
							location.href = "/mngr/rls/inspcStockOut";
						}else{
							alert(rtnVal2);
						}
					}
				})

			}) */
			
			
		</script>
		<!-- script addon end -->
	</body>
</html>
