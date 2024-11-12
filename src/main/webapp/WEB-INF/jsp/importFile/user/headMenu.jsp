<%@page import="org.springframework.web.context.request.RequestContextHolder"%>
<%@page import="org.springframework.web.context.request.ServletRequestAttributes"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" href="/assets/css/flags.css">
<style type="text/css">
</style>
</head>
<body>
	<header class="site-navbar" role="banner">
		<div class="site-navbar-top">
	    	<div class="container" style="width:90%">
	    		<div class="row align-items-center text-right" style="margin-bottom:10px;margin-right:10px;">
	    			<div id="google_translate_element" style="display:none;"></div>
	    			<select id="translation-options">
	    				<option value="ko"><span>Korean</span></option>
	    				<option value="en"><span>English</span></option>
	    				<option value="ja"><span>Japanese</span></option>
	    				<option value="zh-CN"><span>Chinese(Simplified)</span></option>
	    				<option value="zh-TW"><span>Taiwanese(Traditional)</span></option>
	    			</select>
					<script src="https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
					<script type="text/javascript">
		                function googleTranslateElementInit() {new google.translate.TranslateElement({pageLanguage: 'ko',autoDisplay: true}, 'google_translate_element');}
						var select = document.getElementById('translation-options');
						select.addEventListener('change', function(event) {
		                	let tolang = select.value;
		                	if (tolang != null) {
		                		 const gtcombo = document.querySelector('.goog-te-combo');
		                        if (gtcombo == null) {
		                            alert("Error: Could not find Google translate Combolist.");
		                            return false;
		                        }
		                        gtcombo.value = tolang; // 변경할 언어 적용
		                        gtcombo.dispatchEvent(new Event('change')); // 변경 이벤트 트리거
		                	}
		                })
					</script>
	    		</div>
	      		<div class="row align-items-center">
	        		<div class="col-6 col-md-12 order-3 order-md-3 text-right">
	        			<input type="hidden" name="userId" id="userId" value='<sec:authentication property="name"/>' />
	          			<div class="site-top-icons">
	            			<ul>
	            			<!-- <a href="#"><span class="fa fa-credit-card-alt"></span></a>
	            			<li id="userDeposit">
	            			</li>
	            			<li></li> -->
	            			<!-- <li>
	            				<a href="/comn/terms" style="font-weight:700;color:#000;">ACI 운송약관 <i class="fa fa-download"></i></a>
	            			</li> -->
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
	  	<nav class="site-navigation text-right text-md-center" style="border-bottom: 1px solid black; border-top: 1px solid black;" role="navigation">
	    	<div class="container center" style="width:90%">
	      		<ul class="site-menu js-clone-nav hidden-xs hidden-sm">
	      			<sec:authorize access="hasAnyRole('USER')">
		        		<!-- <li class="has-children"><a>배송대행이란</a>
			          		<ul class="dropdown">
			            		<li><a href="/cstmr/shpngAgncy/Info" style="font-size: 10">배송대행 안내</a></li>
			            		<li><a href="/cstmr/shpngAgncy/ovrssAdres">해외 주소지</a></li>
			            		<li><a href="/cstmr/shpngAgncy/applyInfo">신청 방법 안내</a></li>
			            		<li><a href="/cstmr/shpngAgncy/member">회원 등급</a></li>
			          		</ul>
		        		</li>
		        		<li class="has-children"><a>요금안내</a>
			          		<ul class="dropdown">
			          			<li><a href="/cstmr/rates/ovrssChrgs">배송대행 해외 지역별 요금</a></li>
								<li><a href="/cstmr/rates/rtrnChrgs">해외 반송 해외지역별 요금</a></li>
								<li><a href="/cstmr/rates/extraSrvc">부가서비스 요금</a></li>
			          		</ul>
		        		</li>
		        		<li class="has-children"><a>관세 및 통관</a>
			          		<ul class="dropdown">
			          			<li><a href="/cstmr/csdcsClrnc/gdnc" >관·부과세 안내</a></li>
								<li><a href="/cstmr/csdcsClrnc/lstgnClrnc">목록통관 및 일반통관</a></li>
								<li><a href="/cstmr/csdcsClrnc/itmimExprt">수입·수출 금지품목</a></li>
								<li><a href="/cstmr/csdcsClrnc/exchn">관세청 고시 환율</a></li>
			          		</ul>
		        		</li>
		        		<li class="has-children" ><a>쇼핑하기</a>
							<ul class="dropdown">
								<li><a href="/cstmr/shop/ovrss" >해외 쇼핑몰</a></li>
								<li><a href="/cstmr/shop/dmstc">국내 쇼핑몰</a></li>
							</ul>
						</li> -->
						<!-- <li><a href="/cstmr/home">메인 페이지</a></li> -->
						<li class="has-children" ><a>일반배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/shpngAgncy" >일반배송 대행 신청서</a></li>
								<li><a href="/cstmr/apply/shpngRegistList" >BL 프린트</a></li>
								<!-- <li><a href="/cstmr/apply/shpngRegistErrorList" >Error 목록</a></li> -->
								<li><a href="/cstmr/apply/shpngSendBefore" >발송 대기</a></li>
								<li><a href="/cstmr/apply/shpngList">등록 내역</a></li>
								<!-- <li><a href="/cstmr/apply/shpngSendAfter" >발송 완료</a></li> -->
								<!-- <li><a href="/cstmr/apply/pymnt">결제하기</a></li> -->
							</ul>
						</li>
						<li class="has-children" ><a>검품배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/inspDlvry">검품배송 신청</a></li>
								<li><a href="/cstmr/apply/inspRegistList">검품배송 등록내역</a></li>
								<li><a href="/cstmr/stock/inspUserOrderWhInOut">검품 입출고 내역</a></li>
								<!-- <li><a href="/cstmr/apply/inspSendBefore" >발송 대기</a></li> -->
								<!-- <li><a href="/cstmr/apply/inspSendAfter" >발송 완료</a></li> -->
								<!-- <li><a href="/cstmr/apply/pymnt">결제하기</a></li> -->
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
						<!-- <li class="has-children" ><a>사입배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/prchsDlvry">사입배송 신청</a></li>
								<li><a href="/cstmr/apply/prchsDlvry">사입배송 등록내역</a></li>
								<li><a href="/cstmr/apply/pymnt">결제하기</a></li>
								forthMenu End
							</ul>
						</li> -->
						
						<!-- <li class="has-children" ><a>반송 신청</a>
							<ul class="dropdown">
								forthMenu End
								<li id= "5thOne" class=""><a href="/cstmr/rtrn/rtrnAplct" >해외 반송 신청서</a></li>
								<li id= "5thTwo" class=""><a href="/cstmr/rtrn/rtrnPymnt">결제하기</a></li>
							</ul>
						</li> -->
		
						<!-- 5thMenu End -->
						<li class="has-children"><a>배송 현황</a>
							<ul class="dropdown"> 
								<!-- <li><a href="/cstmr/dlvry/undlvStts">수출신고현황 확인</a></li> -->
								<li><a href="/cstmr/dlvry/stts">발송 현황</a></li>
								<li><a href="/cstmr/dlvry/undlvStts">미발송 현황</a></li>
								<!-- <li><a href="/cstmr/dlvry/podLokup">POD 조회</a></li> -->
							</ul> <!-- subMenu End -->
						</li>
						<!-- 6thMenu End -->
						<!-- <li class="has-children"><a>예치금 관리</a>
							<ul class="dropdown"> 
								<li><a href="/cstmr/dpst/dpstAplct" >예치금 신청</a></li>
								<li><a href="/cstmr/dpst/wthdrList">예치금 입출금 리스트</a></li>
								<li><a href="/cstmr/dpst/rqstRfnd">예치금 환불 신청</a></li>
							</ul> 
						</li>
						7thMenu End
						<li class="has-children"><a>고객센터</a>
							<ul class="dropdown"> 
								<li><a href="/cstmr/srvc/ntc" >공지사항</a></li>
								<li><a href="/cstmr/srvc/frqntAskqs">자주하는 질문</a></li>
								<li><a href="/cstmr/srvc/cntct">1:1 문의하기</a></li>
								<li><a href="/cstmr/srvc/rvws">이용 후기</a></li>
								<li><a href="/cstmr/srvc/event">이벤트</a></li>
							</ul> 
						</li> -->
						<!-- 8thMenu End -->
						<li class="has-children"><a>재고 관리</a>
							<ul class="dropdown">
								<li><a href="/cstmr/stock/checkWarehousing">입고 현황</a></li>
								<li><a href="/cstmr/stock/inspcUserStock">검품 현황</a></li>
								<!-- <li><a href="/cstmr/stock/prdctRgstr" >상품등록</a></li>
								<li><a href="/cstmr/stock/prchsRgstr">사입상품 입고 등록</a></li>
								<li><a href="/cstmr/stock/invntStts">사입 재고 현황</a></li> -->
								<li><a href="/cstmr/stock/inspcUserDisposalStock">반품 및 폐기 현황</a></li>
								<li><a href="/cstmr/stock/inspcUserUnIdentifiedItems">미확인 물품 현황</a></li>
							</ul> 
						</li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('USER','RETURN')">
							<!-- 10thMenu End -->
							<li class="has-children" ><a>반품 관리</a>
								<ul class="dropdown">
									<!-- <li><a href="/cstmr/return/tempOrderList">엑셀 등록</a><li> -->
									<li><a href="/cstmr/return/orderCheck">수기 접수</a></li>
									<li><a href="/cstmr/return/orderList">접수 내역</a></li>
									<li><a href="/cstmr/return/stockList">재고 내역</a></li>
									<li><a href="/cstmr/return/whoutList">출고 내역</a></li>
								</ul>
							</li>
						<!-- 9thMenu End -->
						<li class="has-children"><a>마이 페이지</a>
							<ul class="dropdown"> 
								<!-- <li><a href="/cstmr/myPage/aplctList" >나의 신청서 LIST</a></li> -->
								<li><a href="/cstmr/myPage/userInfo">내 정보</a></li>
							<sec:authorize access="hasAnyRole('USER','RETURN')">
								<li><a href="/cstmr/myPage/userInfoApi">API 정보</a></li>
								<!-- <li><a href="/cstmr/myPage/userCafe24Info">Cafe24 API 정보</a></li> -->
								<!-- <li><a href="/cstmr/myPage/userCsList">문의 내역</a> -->
							</sec:authorize>
							</ul> 
						</li>
						<!-- <li class="has-children"><a>쇼핑몰 연동</a>
							<ul class="dropdown">
								<li><a href="/cstmr/integrate/shopify">Shopify</a></li>
								<li><a href="/cstmr/integrate/shopee">Shopee</a></li>
							</ul>
						</li> -->
					</sec:authorize>

				</ul>
			</div>
		</nav>
	</header>
	
	<script src="https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	</body>
</html>