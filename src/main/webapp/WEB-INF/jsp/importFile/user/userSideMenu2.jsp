<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="utf-8" />
	<div id="sidebar" class="sidebar                  responsive                    ace-save-state sidebar-fixed">
		<script type="text/javascript">
			try{ace.settings.loadState('sidebar')}catch(e){}
		</script>
		<ul class="nav nav-list">
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
						<li class="has-children" ><a>일반배송 신청</a>
							<ul class="dropdown">
								<li><a href="/cstmr/apply/shpngAgncy" >일반배송 대행 신청서</a></li>
								<li><a href="/cstmr/apply/shpngRegistList" >BL 프린트</a></li>
								<!-- <li><a href="/cstmr/apply/shpngRegistErrorList" >Error 목록</a></li> -->
								<li><a href="/cstmr/apply/shpngSendBefore" >발송 대기</a></li>
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
								<li><a href="/cstmr/takein/takeinOrderListTmp">주문 등록</a></li>
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
						<li class="has-children"><a>재고관리</a>
							<ul class="dropdown"> 
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
							<li class="has-children"><a>반품 관리</a>
								<ul class="dropdown"> 
									<!-- <li><a href="/cstmr/myPage/aplctList" >나의 신청서 LIST</a></li> -->
									<li><a href="/return/depositList">예치금 사용 내역</a></li>
									<li><a href="/return/list">반품 접수 내역</a></li>
									<li><a href="/return/inspList">검수 내역</a></li>
									<li><a href="/return/sendOutList">출고 내역</a></li>
									<li><a href="/return/discardList">취소 및 폐기 내역</a></li>
									<!-- <li><a href="">문의 내역</a> -->
								</ul> 
							</li>
						<!-- 9thMenu End -->
						<li class="has-children"><a>마이 페이지</a>
							<ul class="dropdown"> 
								<!-- <li><a href="/cstmr/myPage/aplctList" >나의 신청서 LIST</a></li> -->
								<li><a href="/cstmr/myPage/userInfo">내 정보</a></li>
							<sec:authorize access="hasAnyRole('USER')">
								<li><a href="/cstmr/myPage/userInfoApi">API 정보</a></li>
								<li><a href="/cstmr/myPage/userCafe24Info">Cafe24 API 정보</a></li>
							</sec:authorize>
							</ul> 
						</li>
					</sec:authorize>
			<!-- 14thMenu End -->
		</ul><!-- /.nav-list -->
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>
	</div>
