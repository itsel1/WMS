<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<style type="text/css">
	.has-children {
		border:1px solid #ccc;
		border-radius: 10px;
	}
	
</style>
</head>
<body>
	<header class="site-navbar" role="banner">
		<div class="site-navbar-top">
	    	<div class="container" style="width:90%">
	      		<div class="row align-items-center">
	        		<div class="col-6 col-md-12 order-3 order-md-3 text-right">
	        			<input type="hidden" name="userId" id="userId" value='<sec:authentication property="name"/>' />
	          			<div class="site-top-icons">
	            			<ul>
	            			<a href="#"><span class="fa fa-credit-card-alt"></span></a>
	            			<li id="userDeposit">
	            			</li>
	            			<li></li>
	            			<sec:authorize access="hasAnyRole('USER')">
	            				<li>
	            					<a href="/comn/menual">[매뉴얼 다운로드]</a>
	            				</li>
	            			</sec:authorize>
	              				<li><a href="#">
	              					<sec:authentication property="name"/> 님
	              					<span class="ace-icon icon-person"></span></a></li>
	              				<li><a href="#"><span class="ace-icon icon-heart-o"></span></a></li>
	              				<li>
									<a href="#">
										<i class="ace-icon fa fa-power-off"></i>
											<sec:authorize access="isAuthenticated()">
											  <a href="#" onclick="document.getElementById('logout').submit();">로그아웃</a>
											</sec:authorize>
										<form id="logout" action="/comn/logout" method="POST">
											<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
										</form>
									</a>
								</li>
	              				<li class="hidden-md hidden-lg">
	              					<a href="#" class="site-menu-toggle js-menu-toggle">
	              						<span class="icon-menu"></span>
	           						</a>
	       						</li>
	            			</ul>
	          			</div> 
	        		</div>
	      		</div>
	    	</div>
	  	</div> 
	  	<nav class="site-navigation text-right text-md-center" style="" role="navigation">
	    	<div class="container center" style="width:auto;">
	      		<ul class="site-menu js-clone-nav hidden-xs hidden-sm">
	      			<sec:authorize access="hasAnyRole('USER')">
						<li class="has-children" ><a>일반배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/shpngAgncy" >일반배송 대행 신청서</a></li>
								<li><a href="/cstmr/apply/shpngRegistList" >BL 프린트</a></li>
								<li><a href="/cstmr/apply/shpngSendBefore" >발송 대기</a></li>
							</ul>
						</li>
						<li class="has-children" ><a>검품배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/inspDlvry">검품배송 신청</a></li>
								<li><a href="/cstmr/apply/inspRegistList">검품배송 등록내역</a></li>
								<li><a href="/cstmr/stock/inspUserOrderWhInOut">검품 입출고 내역</a></li>
							</ul>
						</li>
						<li class="has-children" ><a>사입 관리</a>
							<ul class="dropdown">
								<li><a href="/cstmr/takein/takeinInfoList">상품정보 등록</a></li>
								<li><a href="/cstmr/takein/takeinUserStockList">재고 리스트</a></li>
								<!-- <li><a href="/cstmr/takein/takeinOrderListTmp">주문 등록</a></li> -->
								<li><a href="/cstmr/takein/preorder">주문 등록</a></li>
								<li><a href="/cstmr/takein/takeinOrderList">주문 등록 내역</a></li>
								<li><a href="/cstmr/takein/takeinDelOrderList">삭제 내역</a></li>
								<li><a href="/cstmr/takein/userTakeinStockInMonth">월별 입출고내역</a></li>
								<li><a href="/cstmr/takein/takeinStockOut">일자별 출고현황</a></li>
								<li><a href="/cstmr/takein/takeinStockInOut">일자별 입출고현황</a></li>
								<li><a href="/cstmr/takein/takeinOrderStockOut">주문별 출고현황</a></li>
							</ul>
						</li>
						<!-- 5thMenu End -->
						<li class="has-children"><a>배송 현황</a>
							<ul class="dropdown"> 
								<li><a href="/cstmr/dlvry/stts">발송 현황</a></li>
								<li><a href="/cstmr/dlvry/undlvStts">미발송 현황</a></li>
							</ul> <!-- subMenu End -->
						</li>
						<!-- 8thMenu End -->
						<li class="has-children"><a>재고관리</a>
							<ul class="dropdown"> 
								<li><a href="/cstmr/stock/inspcUserStock">검품 현황</a></li>
								<li><a href="/cstmr/stock/inspcUserDisposalStock">반품 및 폐기 현황</a></li>
								<li><a href="/cstmr/stock/inspcUserUnIdentifiedItems">미확인 물품 현황</a></li>
							</ul> 
						</li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('USER','RETURN')">
							<!-- 10thMenu End -->
							<li class="has-children"><a>반품 관리</a>
								<ul class="dropdown"> 
									<li><a href="/return/depositList">예치금 사용 내역</a></li>
									<li><a href="/return/list">반품 접수 내역</a></li>
									<li><a href="/return/inspList">검수 내역</a></li>
									<li><a href="/return/sendOutList">출고 내역</a></li>
									<li><a href="/return/discardList">취소 및 폐기 내역</a></li>
								</ul> 
							</li>
						<!-- 9thMenu End -->
						<li class="has-children"><a>마이 페이지</a>
							<ul class="dropdown"> 
								<li><a href="/cstmr/myPage/userInfo">내 정보</a></li>
							<sec:authorize access="hasAnyRole('USER')">
								<li><a href="/cstmr/myPage/userInfoApi">API 정보</a></li>
								<li><a href="/cstmr/myPage/userCafe24Info">Cafe24 API 정보</a></li>
							</sec:authorize>
							</ul> 
						</li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
	</header>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				var userId = $("#userId").val();

				$.ajax({
					url : '/cstmr/myPage/userDeposit',
					type : 'GET',
					data : {userId: userId},
					success : function(result) {

						var value = result.deposit.DEPOSIT_PRICE;

						var li = document.getElementById("userDeposit");
						var text = document.createTextNode(value);
						li.appendChild(text);
					},
					error : function(request, status, error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				})
			})
		})
	</script>
	</body>
</html>