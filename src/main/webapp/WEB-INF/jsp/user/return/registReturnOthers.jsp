<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
	.colorBlack {color:#000000 !important;}
	input {
		border: none;
	}
	
	
	.backYellow {background-color: #FDF5E6 !important;}
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>반품 접수</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="row" style="margin-left:10px;">
							<div class="col-md-12 mb-0" style="font-size:14px !important;">
								<a>반품 관리</a> 
								<span class="mx-2 mb-0">/</span> 
								<strong class="text-black">반품 접수</strong>
							</div>
						</div>
				    </div>
					<div class="container">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="page-content">
							<div class="page-header">
								<h3 style="font-weight:bold;">
									반품 접수 (타사 배송)
								</h3>
								<br/>
								<div class="row hr-8">
									<table class="noticeTable" style="width:100%;">
										<tr>
											<th style="font-size: 20px; font-weight: normal; text-align: left; padding: 10px 0 14px 0; background: white;">
												반품 사용 안내서
											</th>
										</tr>
										<tr>
											<td style="border:1px solid #ccc; font-size:14px; width:100%; height:140px; padding-left:10px;">
												ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.
												<br/>
												다만 아래의 경우에는 예외로 합니다.
												<br/><br/>
												- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
												<br/>
												- 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우
												</td>
										</tr>
										<tr>
											<td>
												<p style="margin-top:10px;">
												<label for="agreement_check"> <input type="checkbox" id="agreement_check" name="agreement_check"> 
													<font style="font-size: 14px; font-weight: bold;">동의 합니다.</font>
												</label>
												</p>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<br>
							<div class="inner-content-side">
								<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="get" action="/return/returnCourier">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="addCntVal" id="addCntVal" value=""/>
									<div class="row">
										<div class="col-xs-12 col-lg-12 ">
											<label>
												<font style="font-size: 18px; font-weight: bold; vertical-align: middle;"">운송장 번호</font>
												<input style="margin-left:5px; width:300px; height:36px;" class="backYellow" type="text" name="koblNo" value="${koblNo}" id="koblNo_input" placeholder="운송장 번호를 입력해주세요.">
											</label>
										</div>
									</div>
									<br/>
									<div class="row">
										<div class="col-xs-12 col-lg-12">
											<label>
												<font style="font-size: 18px; font-weight: bold; vertical-align: middle;">출발지 / 도착지 : KR&nbsp; / </font>
												&nbsp;
												<select class="chosen-select-start form-control tag-input-style width-30 pd-1rem nations" id="dstnNation" name="dstnNation">
													<c:forEach items="${nationList}" var="dstnNationList">
														<option value="${dstnNationList.nationCode}">${dstnNationList.nationEName}(${dstnNationList.nationName})</option>
													</c:forEach>
												</select>
											</label>
										</div>
									</div>
									<br/>
									<div class="row">
										<div class="col-xs-12 col-lg-12">
											<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-right:10px;">픽업 방법</font>
											<label><input style="line-height:1.5;" type="radio" name="pickType" value="B" class="rtn_radio">&nbsp;회수 요청</label>&nbsp;
											<label><input style="line-height:1.5;" type="radio" name="pickType" value="A" class="rtn_radio">&nbsp;직접 발송</label>
										</div>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered hide" id="reTrk" name="reTrk" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="2">반송 택배 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">택배 회사</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="reTrkCom" id="reTrkCom" value="${data.reTrkCom}" placeholder="입력하세요"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">송장 번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="reTrkNo" id="reTrkNo" value="${data.reTrkNo}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">등록 날짜</th>
													<td style="padding:0px;">
														<input type="date" style="width:100%;height:36px;" max="2999-12-31" class="backYellow" name="A002Date" id="A002Date" value="${data.a002Date}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="2">반품 담당자 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이름</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="attnName" id="attnName" value="${data.attnName}" placeholder="입력하세요"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">연락처</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="attnTel" id="attnTel" value="${data.attnTel}" placeholder="숫자만 입력 가능합니다."/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이메일</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="attnEmail" id="attnEmail" value="${data.attnEmail}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">반품 발송 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">자사 정산 ID</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" name="calculateId" id="calculateId" value="${rtn.calculateId}" placeholder="미입력 시, 사용자 ID로 부여됩니다."/>
													</td>
													<th class="center colorBlack" style="width:10%;">주문 접수 번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" name="orderReference" id="orderReference" value="${rtn.orderReference}" placeholder="미입력 시, 자동으로 부여됩니다."/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주문번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="orderNo" id="orderNo" value="${rtn.orderNo}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">주문날짜</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="orderDate" id="orderDate" value="${rtn.orderDate}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">이름</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupName" id="pickupName" value="${rtn.pickupName}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" zipCodeOnly class="backYellow" name="pickupZip" id="pickupZip" value="${rtn.pickupZip}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주소</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupAddr" id="pickupAddr" value="${rtn.pickupAddr}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">영문주소</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="pickupEngAddr" id="pickupEngAddr" value="${rtn.pickupEngAddr}" />
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="pickupTel" id="pickupTel" value="${rtn.pickupTel}" placeholder="숫자만 입력 가능합니다."/>
													</td>
													<th class="center colorBlack" style="width:10%;">휴대전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="pickupMobile" id="pickupMobile" value="${rtn.pickupMobile}" placeholder="숫자만 입력 가능합니다."/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="4">반품 도착지 정보</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">회사명</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderName" id="senderName" value="${rtn.senderName}" />
													</td>
													<th class="center colorBlack" style="width:10%;">우편번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" zipCodeOnly class="backYellow" name="senderZip" id="senderZip" value="${rtn.senderZip}" />
													</td>
												</tr>
												<tr id="stateCityTr" class="">
													<th class="center colorBlack" style="width:10%;">주</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderState" id="senderState" value="${rtn.senderState}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">도시</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderCity" id="senderCity" value="${rtn.senderCity}"/>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">주소</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderAddr" id="senderAddr" value="${rtn.senderAddr}"/>
													</td>
													<th class="center colorBlack" style="width:10%;">전화번호</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" numberOnly class="backYellow" name="senderTel" id="senderTel" value="${rtn.senderTel}" placeholder="숫자만 입력 가능합니다."/>
													</td>
												</tr>
												<tr id="usTr" class="hide">
													<th class="center colorBlack" style="width:10%;">빌딩 명</th>
													<td style="padding:0px;" colspan="3">
														<input type="text" style="width:100%;height:36px;" class="backYellow" name="senderBuildNm" id="senderBuildNm" value="${rtn.senderBuildNm}"/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<div class="col-xs-12 col-lg-12">
											<label>
												<font style="font-size: 18px; font-weight: bold; vertical-align: middle; margin-left:10px; margin-right:10px;">반송 구분</font>
												<input style="line-height:1.5;" type="radio" name="returnType" value="N" class="rtn_radio">&nbsp;일반배송
												&nbsp;<input style="line-height:1.5;" type="radio" name="returnType" value="E" class="rtn_radio">&nbsp;긴급배송
											</label>
										</div>
									</div>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack">취소 사유</th>
													<td style="vertical-align:middle; background:white;">
														<label for="cncl">
															<input type="radio" name="returnReason" id="returnReason" value="A">&nbsp;단순변심
															&nbsp;<input type="radio" name="returnReason" id="returnReason" value="B">&nbsp;파손
															&nbsp;<input type="radio" name="returnReason" id="returnReason" value="C">&nbsp;불량
															&nbsp;<input type="radio" name="returnReason" id="returnReason" value="D">&nbsp;기타
														</label>
													</td>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">취소 사유 상세</th>
													<td style="padding:0px;">
														<input type="text" style="width:100%;height:36px;" name="returnReasonDetail" id="returnReasonDetail" value=""/>
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="row">
										<table class="table table-bordered" style="margin-left:20px; width:98%;">
											<thead>
												<tr>
													<th class="center colorBlack" colspan="3">위약 반송 정보 <small style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해 주세요</small></th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:10%;">위약 반송</th>
													<td style="vertical-align:middle; background:white;" colspan="2">
														<label for="cncl">
															<input type="radio" name="taxType" id="taxType" value="Y">&nbsp;위약 반송
															&nbsp;<input type="radio" name="taxType" id="taxType" value="N">&nbsp;일반 반송
														</label>
														<input type="hidden" name="taxReturn" id="taxReturn" value="C">
													</td>
												</tr>
												<tr class="hideTax hide">
													<th class="center colorBlack" style="width:12%;">반송 사유서</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnReason" name="rtnReason"></td>
													<td style="vertical-align: middle; background:white;">
														<a href="/반송 사유서.xlsx" download>[반송사유서 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax hide">
													<th class="center colorBlack" style="width:12%;">반품 접수 캡쳐본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCapture" name="rtnCapture"></td>
													<td style="background:white;"></td>
												</tr>
												<tr class="hideTax hide">
													<th class="center colorBlack" style="width:12%;">반품 메신저 캡쳐본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnMessenger" name="rtnMessenger"></td>
													<td style="background:white;"></td>
												</tr>
												<tr class="hideTax hide">
													<th class="center colorBlack" style="width:12%;">반품 Commercial Invoice</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCl" name="rtnCl"></td>
													<td style="vertical-align: middle; background:white;">
														<a href="/반품 커머셜 인보이스.xlsx" download>[반품 커머셜 다운로드]</a>
													</td>
												</tr>
												<tr class="hideTax hide">
													<th class="center colorBlack" style="width:12%;">환급 통장 사본</th>
													<td style="width:10%; background:white;"><input type="file" class="taxTypeValue" id="rtnCopyBank" name="rtnCopyBank"></td>
													<td style="background:white;"></td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="col-xs-12 col-lg-12" style="padding: 1rem!important; width:100%; text-align: right">
										<input type="button" class="btn btn-sm btn-white" id="addItem" name="addItem" value="추가">
										<input type="button" class="btn btn-sm btn-white" id="delItem" name="delItem" value="삭제">
									</div>
									<div class="row">
										<div id="addForm">
											<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
												<div class="col-xs-12 col-lg-12" style="font-size: 3;">
													<h4>상품 1</h4>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label>상품명</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 backYellow" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>상품명 (영문)</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 itemDetailEng" type="text" name="itemDetailEng" id="itemDetailEng" value="${item.itemDetailEng}" placeholder="도착지가 일본일 경우 필수입니다."/>
														</div>
														<div class="col-xs-1 center mp07">
															<label>상품명 (일문)</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 nativeItemDetail" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">브랜드</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="text" name="brand" id="brand" value="${item.brand}"/>
														</div>
														<div class="col-xs-1 center">상품 무게</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="number" name="itemWta" id="itemWta" value="${item.itemWta}" />
														</div>
														<div class="col-xs-1 center">무게 단위</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row form-group hr-8">
														<div class="col-xs-1 center">상품 개수</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="number" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
														</div>
														<div class="col-xs-1 center mp07">상품 가격</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="number" name="unitValue" id="unitValue" value="${item.unitValue}" />
														</div>
														<div class="col-xs-1 center mp07">통화</div>
														<div class="col-xs-3 center ">
															<input class="col-xs-12 backYellow" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" />
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label>제조국</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>제조회사</label>
														</div>
														<div class="col-xs-3 center">
															<input class="col-xs-12 backYellow" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label><a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
															<i class="fa fa-exclamation-circle"></i>
															</a></label>
														</div>
														<div class="col-xs-3 center ">
															<input class="col-xs-12 hsCode" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
														</div>
													</div>
													<div class="hr dotted"></div>
													<div class="row hr-8">
														<div class="col-xs-1 center mp07">
															<label>상품 URL</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 backYellow" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>상품 이미지 URL</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 backYellow" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
														</div>
														<div class="col-xs-1 center mp07">
															<label>상품코드</label>
														</div>
														<div class="col-xs-3">
															<input class="col-xs-12 cusItemCode" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
														</div>
													</div>
													<div class="hr dotted"></div>
												</div>
											</div>
										</div>
									</div>
									<br/><br/>
									<div class="row">
										<table class="table table-bordered" style="width:100%;">
											<thead>
												<tr>
													<th class="center colorBlack" style="width:10%;">입고시 요청사항</th>
													<td style="padding:0px;">
														<input style="width:100%; height:36px;" type="text" name="whMsg" id="whMsg" class="comm_text">
													</td>
												</tr>
											</thead>
										</table>
									</div>
									<br/>
									<div class="rtnBtn_div" style="text-align:center;">
										<input type="button" class="btn btn-outline-secondary btn-lg" id="registReturn" name="registReturn" value="반품 접수">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
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
		
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		jQuery(function($) {
			$(document).ready(function() {
				
				$("#12thMenu").toggleClass('open');
				$("#12thMenu").toggleClass('active'); 
				$("#12thThr").toggleClass('active');	
				
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
				});
				$("#A002Date").on("change",function(){
					if($(this).val().length != 0){
						$(this).removeClass("backYellow");
					}else{
						$(this).addClass("backYellow");
					}
				});
				
			});
		
		});
	
		
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
				}else if($(".backYellow").size() != 0){
					alert("필수 등록정보를 입력해 주세요.")
					$($(".backYellow")[0]).focus();
					return;
				}else if($('input[name="taxType"]:checked').val() == "Y"){
					for(var i = 0; i < $(".taxTypeValue").size(); i++){
						if($($(".taxTypeValue")[i]).val().length == 0){
							return alert("위약반송 필수서류가 비어있습니다.")
						}
					}
				}
				$("#returnForm").attr("action","/return/returnOthers");
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
		
		$("#dstnNation").on('change',function(e){
			if($("#dstnNation").val() == 'KR'){
				$("#pickupEngAddr").removeClass("backYellow");
				$("#senderCity").removeClass('backYellow');
				$("#senderState").removeClass('backYellow');
			}else{
				$("#pickupEngAddr").addClass("backYellow");
				$("#senderCity").addClass('backYellow');
				$("#senderState").addClass('backYellow');
			}
			
			if($("#dstnNation").val() == 'US'){
				$("#usTr").removeClass("hide");
				$("#senderBuildNm").addClass("backYellow");
			}else{
				$("#usTr").addClass("hide");
				$("#senderBuildNm").removeClass("backYellow");
			}
			
			if($("#dstnNation").val() != 'JP') {
				$(".itemDetailEng").removeClass('backYellow');
				$(".nativeItemDetail").removeClass('backYellow');
				$(".cusItemCode").removeClass('backYellow');
				$(".hsCode").removeClass('backYellow');
				$("#senderState").addClass('backYellow');
				$("#senderCity").addClass('backYellow');
			} else {
				$(".itemDetailEng").addClass('backYellow');
				$(".nativeItemDetail").addClass('backYellow');
				$(".cusItemCode").addClass('backYellow');
				$("#senderState").removeClass('backYellow');
				$("#senderCity").removeClass('backYellow');
				$(".hsCode").addClass('backYellow');
				$(".backYellow").focusout(function(){
					if($(this).val().length != 0){
						$(this).removeClass("backYellow");
					}else{
						$(this).addClass("backYellow");
					}
				});
			}
		})
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
					$("#addCntVal").val(addCnt)
					;
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
	            }
			})
		});
		$("#delItem").on("click",function(e){
			if(addCnt != 1){
				$("#add"+$("#addCntVal").val()).remove();
				addCnt--;
				$("#addCntVal").val(addCnt);
			}
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

			$("#orderDate").datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});
			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });
			$("input:text[zipCodeOnly]").on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9|-]/g,""));
			})
			
		</script>
		<!-- script addon end -->
	</body>
</html>