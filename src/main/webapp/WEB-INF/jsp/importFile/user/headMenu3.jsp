<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<style type="text/css">
	
	/* body {
		font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		line-height: 1.42857143;
	} */
</style>
<header class="site-navbar" role="banner">
	<div class="site-navbar-top">
    	<div class="container" style="width:90%">
      		<div class="row align-items-center">
        		<div class="col-6 col-md-12 order-3 order-md-3 text-right">
          			<div class="site-top-icons">
            			<ul>
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
  	<nav class="site-navigation text-right text-md-center" role="navigation">
    	<div class="container center" style="width:90%">
      		<ul class="site-menu js-clone-nav hidden-xs hidden-sm">
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
				<li class="has-children" ><a>대행 신청</a>
					<ul class="dropdown">
						<li class="has-children" ><a>배송대행 신청</a>
							<ul class="dropdown"> <!-- subMenu Start -->
								<li><a href="/cstmr/apply/shpngAgncy" >배송대행 신청서</a></li>
								<!-- <li><a href="/cstmr/apply/inspDlvry">검품배송 신청</a></li>
								<li><a href="/cstmr/apply/prchsDlvry">사입배송 신청</a></li>
								<li><a href="/cstmr/apply/pymnt">결제하기</a></li> -->
							</ul> <!-- subMenu End -->
						</li><!-- forthMenu End -->
						<li class="has-children" ><a>등록 내역</a>
							<ul class="dropdown"> <!-- subMenu Start -->
								<li><a href="/cstmr/apply/shpngRegistList" >BL 프린트</a></li>
								<!-- <li><a href="/cstmr/apply/inspRegistList">검품배송 등록내역</a></li>
								<li><a href="/cstmr/apply/prchsDlvry">사입배송 등록내역</a></li> -->
							</ul> <!-- subMenu End -->
						</li><!-- forthMenu End -->
						<!-- <li class="has-children"><a>반송 대행</a>
							<ul class="dropdown">
								<li id= "5thOne" class=""><a href="/cstmr/rtrn/rtrnAplct" >해외 반송 신청서</a></li>
								<li id= "5thTwo" class=""><a href="/cstmr/rtrn/rtrnPymnt">결제하기</a></li>
							</ul> subMenu End
						</li> --><!-- 5thMenu End -->
					</ul>
				</li>
				<li class="has-children"><a>배송 현황</a>
					<ul class="dropdown"> 
						<li><a href="/cstmr/dlvry/stts">발송 현황</a></li>
						<li><a href="/cstmr/dlvry/undlvStts">미발송 현황</a></li>
						<!-- <li><a href="/cstmr/dlvry/podLokup">POD 조회</a></li> -->
					</ul> <!-- subMenu End -->
				</li><!-- 6thMenu End -->
				<!-- <li class="has-children"><a>예치금 관리</a>
					<ul class="dropdown"> 
						<li><a href="/cstmr/dpst/dpstAplct" >예치금 신청</a></li>
						<li><a href="/cstmr/dpst/wthdrList">예치금 입출금 리스트</a></li>
						<li><a href="/cstmr/dpst/rqstRfnd">예치금 환불 신청</a></li>
					</ul> subMenu End
				</li> --><!-- 7thMenu End -->
				<!-- <li class="has-children"><a>고객센터</a>
					<ul class="dropdown"> 
						<li><a href="/cstmr/srvc/ntc" >공지사항</a></li>
						<li><a href="/cstmr/srvc/frqntAskqs">자주하는 질문</a></li>
						<li><a href="/cstmr/srvc/cntct">1:1 문의하기</a></li>
						<li><a href="/cstmr/srvc/rvws">이용 후기</a></li>
						<li><a href="/cstmr/srvc/event">이벤트</a></li>
					</ul> subMenu End
				</li> --><!-- 8thMenu End -->
				<!-- <li class="has-children"><a>재고관리</a>
					<ul class="dropdown"> 
						<li><a href="/cstmr/stock/inspcStock">검품 현황</a></li>
						<li><a href="/cstmr/stock/prdctRgstr" >상품등록</a></li>
						<li><a href="/cstmr/stock/prchsRgstr">사입상품 입고 등록</a></li>
						<li><a href="/cstmr/stock/invntStts">사입 재고 현황</a></li>
					</ul> subMenu End
				</li> --><!-- 9thMenu End -->
				<li class="has-children"><a>마이 페이지</a>
					<ul class="dropdown"> 
						<!-- <li><a href="/cstmr/myPage/aplctList" >나의 신청서 LIST</a></li> -->
						<li><a href="/cstmr/myPage/userInfo">내 정보</a></li>
					</ul> 
				</li>
			</ul>
		</div>
	</nav>
</header>