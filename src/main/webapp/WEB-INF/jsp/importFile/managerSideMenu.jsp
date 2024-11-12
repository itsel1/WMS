<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<meta charset="utf-8" />
	<div id="sidebar" class="sidebar                  responsive                    ace-save-state sidebar-fixed">
		<script type="text/javascript">
			try{ace.settings.loadState('sidebar')}catch(e){}
		</script>
		<ul class="nav nav-list">
			<li id="zeroMenu" class=""><!-- zeroMenu Start -->
				<a href="#" class="dropdown-toggle" >
					<i class="menu-icon fa fa-users"></i>
					<span class="menu-text">
						회원 관리
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu">
					<li id="zeroOne0" class="">
						<a href="/mngr/acnt/userList">
							<i class="menu-icon fa fa-caret-right"></i>
							거래처 목록
						</a>
					</li>
					<!-- <li id="zeroOne" class="">
						<a href="/mngr/acnt/entrpList">
							<i class="menu-icon fa fa-caret-right"></i>
							기업 회원
						</a>
						<b class="arrow"></b>
					</li>
					<li id="zeroTwo" class="">
						<a href="/mngr/acnt/indvdList">
							<i class="menu-icon fa fa-caret-right"></i>
							개인 회원
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="14thOne" class="">
						<a href="/mngr/acnt/adminList">
							<i class="menu-icon fa fa-caret-right"></i>
							관리자 목록
						</a>
					</li>
					<li id="14thTwo" class="">
						<a href="/mngr/acnt/fastboxUserList">
							<i class="menu-icon fa fa-caret-right"></i>
							FB 판매사 목록
						</a>
					</li>
					<c:if test="${sessionScope.ORG_STATION eq '082' && (sessionScope.USER_ID eq 'itsel2' || sessionScope.USER_ID eq 'admin1')}">
					<li id="14thThird" class="">
						<a href="/mngr/acnt/userBizList">
							<i class="menu-icon fa fa-caret-right"></i>
							수출화주사업자 정보
						</a>
					</li>
					</c:if>
				</ul>
			</li><!-- zeroMenu End -->
			<li id="1stMenu" class=""><!-- 1stMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-bar-chart-o"></i>
					<span class="menu-text">
						단가 관리
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu">
					<li id="1stTwo" class="">
						<a href="/mngr/unit/transCom">
							<i class="menu-icon fa fa-caret-right"></i>
							택배회사 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="1stThr" class="">
						<a href="/mngr/unit/shpComPrice">
							<i class="menu-icon fa fa-caret-right"></i>
							Zone 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="1stOne" class="">
						<a href="/mngr/unit/nationPrice">
							<i class="menu-icon fa fa-caret-right"></i>
							Nation 관리
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li><!-- 1stMenu End -->
			
			<!-- <li id="16thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-credit-card-alt"></i>
				 	<span class="menu-text">
						예치금 관리
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="16th-1" class="">
						<a href="/mngr/deposit/depositApply">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 신청 내역
						</a>
					<b class="arrow"></b>
					</li>
					<li id="16th-2" class="">
						<a href="/mngr/deposit/userDepositList">
							<i class="menu-icon fa fa-caret-right"></i>
							업체별 예치금
						</a>
					<b class="arrow"></b>
					</li>
					<li id="16th-3" class="">
						<a href="">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 사용 내역
						</a>
					<b class="arrow"></b>
					</li>
					<li id="16th-4" class="">
						<a href="">
							<i class="menu-icon fa fa-caret-right"></i>
							추가 비용
						</a>
					<b class="arrow"></b>
					</li>
					<li id="16th-5" class="">
						<a href="">
							<i class="menu-icon fa fa-caret-right"></i>
							추가 검수 비용
						</a>
					<b class="arrow"></b>
					</li>
					<li id="16th-6" class="">
						<a href="">
							<i class="menu-icon fa fa-caret-right"></i>
							ETC 추가
						</a>
					<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			
			<!-- 2ndMenu Start -->
			<!-- <li id="2ndMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-credit-card"></i>
					<span class="menu-text">
						요금 안내
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="2ndOne" class="">
						<a href="/mngr/rates/dpstMngmn">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="2ndTwo" class="">
						<a href="/mngr/rates/dpstStts">
							<i class="menu-icon fa fa-caret-right"></i>
							업체별 예치금 현황
						</a>
						<b class="arrow"></b>
					</li>
					<li id="2ndThr" class="">
						<a href="/mngr/rates/aplctStts">
							<i class="menu-icon fa fa-caret-right"></i>
							무통장 입금 신청현황
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			<!-- 2ndMenu End -->
			<!-- 3rdMenu Start -->
			<li id="3rdMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-book"></i>
					<span class="menu-text">
						삭제 리스트
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					
					<!-- <li id="3rdTwo" class="">
						<a href="/mngr/aplctList/prchsList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 등록리스트
						</a>
						<b class="arrow"></b>
					</li> -->
					
					<li id="3rdFor" class="">
						<a href="/mngr/aplctList/dltList2">
							<i class="menu-icon fa fa-caret-right"></i>
							삭제 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
			<!-- <li id="17thMenu" class="">
				<a href="/mngr/rls/scan">
					<i class="menu-icon fa fa-desktop"></i>
					스캔 조회
				</a>
			</li> -->
			<!-- 3rdMenu End -->
			<!-- 4thMenu Start -->
			<li id="4thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-arrow-circle-o-down"></i>
					<span class="menu-text">
						입고 작업
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="4thFiv" class="">
						<a href="/mngr/aplctList/shpngList">
							<i class="menu-icon fa fa-caret-right"></i>
							일반 입고 등록 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="4thThr" class="">
						<a href="/mngr/order/orderRcptList">
							<i class="menu-icon fa fa-caret-right"></i>
							입고 작업
						</a>
						<b class="arrow"></b>
					</li>
					<li id="4thTwo" class="">
						<a href="/mngr/order/orderRcpt">
							<i class="menu-icon fa fa-caret-right"></i>
							입고 스캔 작업
						</a>
						<b class="arrow"></b>
					</li>
					<li id="4thFor" class="">
						<a href="/mngr/order/orderWeight">
							<i class="menu-icon fa fa-caret-right"></i>
							입고 무게 등록
						</a>
						<b class="arrow"></b>
					</li>
					<li id="4thForth" class="">
						<a href="/mngr/order/unRegOrderList">
							<i class="menu-icon fa fa-caret-right"></i>
							운송장 미등록 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
			
			<!-- 4thMenu End -->
			<!-- 5thMenu Start -->
			<li id="5thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-download"></i>
					<span class="menu-text">
						사입
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="5thOne" class="">
						<a href="/mngr/takein/takeinInfoList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 상품 코드 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="5thTwo" class="">
						<a href="/mngr/takein/takeinItemList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 상품 입고 관리
						</a>
						<b class="arrow"></b>
					</li>
					<!-- <li id="5thSp" class="">
						<a href="/mngr/takein/takeinOrderListTmp">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 주문 등록
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="5thSixth" class="">
						<a href="/mngr/takein/takeinOrderListAll">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 운송장 등록 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="5thThr" class="">
						<a href="/mngr/takein/takeinOrderList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 출고 작업
						</a>
						<b class="arrow"></b>
					</li>
					<li id="cancelTakein" class="">
						<a href="/mngr/takein/takeinCancelOrder">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 출고 취소
						</a>
						<b class="arrow"></b>
					</li>
					<li id="6thThr" class="">
						<a href="/mngr/takein/takeinEtcOrder">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 기타 출고
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
			<li id="15thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-cube"></i>
				 	<span class="menu-text">
						사입 Rack
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="15thOne" class="">
					<a href="/mngr/takein/rack/rackCodeList">
						<i class="menu-icon fa fa-caret-right"></i>
						Rack 관리 
					</a>
					<b class="arrow"></b>
					</li>
					<li id="15thTwo" class="">
					<a href="/mngr/takein/rack/rackStockList">
						<i class="menu-icon fa fa-caret-right"></i>
						Rack 재고 관리
					</a>
					<b class="arrow"></b>
					</li>
					<li id="15thThird" class="">
					<a href="/mngr/takein/rack/stockCheckList">
						<i class="menu-icon fa fa-caret-right"></i>
						Rack 재고 파악
					</a>
					<b class="arrow"></b>
					</li>
				</ul>
			</li>
			<li id="12thMenu" class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-download"></i>
				<span class="menu-text">
					검품
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="12thThr" class="">
					<a href="/mngr/aplctList/inspcList">
						<i class="menu-icon fa fa-caret-right"></i>
						검품 등록 리스트
					</a>
					<b class="arrow"></b>
				</li>
				<li id="12thFor" class="">
					<a href="/mngr/order/orderInspc">
						<i class="menu-icon fa fa-caret-right"></i>
						입고 작업
					</a>
					<b class="arrow"></b>
				</li>
				<li id="12thOne" class="">
					<a href="/mngr/stock/inspcAdminStock">
						<i class="menu-icon fa fa-caret-right"></i>
						재고 리스트
					</a>
					<b class="arrow"></b>
				</li>
				<li id="12thTwo" class="">
					<a href="/mngr/stock/inspcAdminStockDisposal">
						<i class="menu-icon fa fa-caret-right"></i>
						재고 반품&폐기 리스트
					</a>
					<b class="arrow"></b>
				</li>
				<li id="12thFiv" class="">
					<a href="/mngr/stock/unIdentifiedItems">
						<i class="menu-icon fa fa-caret-right"></i>
						미확인 물품
					</a>
					<b class="arrow"></b>
				</li>
				<li id="13thThr" class="">
					<a href="/mngr/inspin/returnReqList">
						<i class="menu-icon fa fa-caret-right"></i>
						검품 기타 출고 요청
					</a>
					<b class="arrow"></b>
				</li>
				<!-- <li id="14thThr" class="">
					<a href="/mngr/aplctList/inspcList">
						<i class="menu-icon fa fa-caret-right"></i>
						검품 기타 출고 내역
					</a>
					<b class="arrow"></b>
				</li> -->
				
			</ul>
		</li>
			<!-- 5thMenu End -->
			<!-- 6thMenu Start -->
			<li id="6thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-fighter-jet"></i>
					<span class="menu-text">
						출고
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<!-- <li id="6thOne" class="">
						<a href="/mngr/rls/prchsDlvry">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 발송 관리
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- <li id="6th1st" class="">
						<a href="/mngr/rls/whoutRequest">
							<i class="menu-icon fa fa-caret-right"></i>
							출고 지시
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="6thChange" class="">
						<a href="/mngr/rls/changeLabel">
							<i class="menu-icon fa fa-caret-right"></i>
							송장 교체 작업
						</a>
					</li>
					<!-- <li id="6thType" class="">
						<a href="/mngr/rls/type86Apply">
							<i class="menu-icon fa fa-caret-right"></i>
							Type86 Apply
						</a>
					</li> -->
					<li id="6thFiv" class="">
						<a href="/mngr/rls/mawbAply">
							<i class="menu-icon fa fa-caret-right"></i>
							MAWB APPLY
						</a>
						<b class="arrow"></b>
					</li>
					<!-- <li id="6thTwo" class="">
						<a href="/mngr/rls/mawbScanAply">
							<i class="menu-icon fa fa-caret-right"></i>
							MAWB Scan APPLY
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- <li id="6thThr" class="">
						<a href="/mngr/rls/inspcStock">
							<i class="menu-icon fa fa-caret-right"></i>
							검품 재고
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="6thSix" class="">
						<a href="/mngr/rls/inspcStockOut">
							<i class="menu-icon fa fa-caret-right"></i>
							재고 출고
						</a>
						<b class="arrow"></b>
					</li>
					<!-- <li id="6thSvn" class="">
						<a href="/mngr/rls/inspcStockScan">
							<i class="menu-icon fa fa-caret-right"></i>
							재고 취소 스캔
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="6thMani" class="">
						<a href="/mngr/rls/manifest">
							<i class="menu-icon fa fa-caret-right"></i>
							Manifest Update
						</a>
					</li>
					<li id="6thFor" class="">
						<a href="/mngr/rls/mawbList">
							<i class="menu-icon fa fa-caret-right"></i>
							출발지 MAWB LIST
						</a>
						<b class="arrow"></b>
					</li>
					<li id="6thNin" class="">
						<a href="/mngr/rls/mawbListArr">
							<i class="menu-icon fa fa-caret-right"></i>
							도착지 MAWB LIST
						</a>
						<b class="arrow"></b>
					</li>
					<li id="6thEig" class="">
						<a href="/mngr/rls/expLicenceList">
							<i class="menu-icon fa fa-caret-right"></i>
							수출신고
						</a>
						<b class="arrow"></b>
					</li>
					<c:if test="${sessionScope.ORG_STATION eq '082' && sessionScope.USER_ID eq 'itsel2'}">
					<li id="14thThird" class="">
						<a href="/mngr/rls/export">
							<i class="menu-icon fa fa-caret-right"></i>
							수출신고 v2
						</a>
					</li>
					</c:if>
				</ul>
			</li>
			<!-- 6thMenu End -->
			<!-- 7thMenu Start -->
			<!-- <li id="7thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-inbox"></i>
					<span class="menu-text">
						WMS
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="7thOne" class="">
						<a href="/mngr/wms/rackMngmn">
							<i class="menu-icon fa fa-caret-right"></i>
							Rack 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="7thTwo" class="">
						<a href="/mngr/wms/rackStts">
							<i class="menu-icon fa fa-caret-right"></i>
							Rack 재고 현황
						</a>
						<b class="arrow"></b>
					</li>
					<li id="7thThr" class="">
						<a href="/mngr/wms/rackChk">
							<i class="menu-icon fa fa-caret-right"></i>
							Rack 재고 체크
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			<!-- 7thMenu End -->
			<!-- 8thMenu Start -->
			<li id="8thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-external-link"></i>
					<span class="menu-text">
						발송 리스트
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="8thOne" class="">
						<a href="/mngr/send/sendList">
							<i class="menu-icon fa fa-caret-right"></i>
							발송 목록
						</a>
						<b class="arrow"></b>
					</li>
					<li id="8thTwo" class="">
						<a href="/mngr/send/unSendList">
							<i class="menu-icon fa fa-caret-right"></i>
							미발송 목록
						</a>
						<b class="arrow"></b>
					</li>
					<li id="8thThird" class="">
						<a href="/mngr/send/stockOut">
							<i class="menu-icon fa fa-caret-right"></i>
							출고 내역
						</a>
					</li>
					<!-- <li id="8thFourth" class="">
						<a href="/mngr/send/sendListTest">
							<i class="menu-icon fa fa-caret-right"></i>
							발송 목록 Beta
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="sendStatus" class="">
						<a href="/mngr/send/stockOutStatus">
							<i class="menu-icon fa fa-caret-right"></i>
							출고 현황
						</a>
					</li>
				</ul>
			</li>
			<!-- 8thMenu End -->
			<!-- 9thMenu Start -->
			<!-- <li id="9thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-exchange"></i>
					<span class="menu-text">
						반품 관리
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="9thOne" class="">
						<a href="/mngr/rtrn/rtrnRcptList">
							<i class="menu-icon fa fa-caret-right"></i>
							반품 접수 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thTwo" class="">
						<a href="/mngr/rtrn/postOfice">
							<i class="menu-icon fa fa-caret-right"></i>
							우체국 접수
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thThr" class="">
						<a href="/mngr/rtrn/rcpt">
							<i class="menu-icon fa fa-caret-right"></i>
							입고
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thFor" class="">
						<a href="/mngr/rtrn/stock">
							<i class="menu-icon fa fa-caret-right"></i>
							재고
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thFiv" class="">
						<a href="/mngr/rtrn/rls">
							<i class="menu-icon fa fa-caret-right"></i>
							출고
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thSix" class="">
						<a href="/mngr/rtrn/rlsList">
							<i class="menu-icon fa fa-caret-right"></i>
							출고 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="9thSev" class="">
						<a href="/mngr/rtrn/rtrnCS">
							<i class="menu-icon fa fa-caret-right"></i>
							반품 CS
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			<!-- 9thMenu End -->
			<!-- 10thMenu Start -->
			<!-- <li id="10thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-info-circle"></i>
					<span class="menu-text">
						INVOICE
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="10thOne" class="">
						<a href="/mngr/invoice/priceApply">
							<i class="menu-icon fa fa-caret-right"></i>
							Price Apply
						</a>
						<b class="arrow"></b>
					</li>
					<li id="10thTwo" class="">
						<a href="/mngr/invoice/expln">
							<i class="menu-icon fa fa-caret-right"></i>
							Invoice
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			<!-- 10thMenu End -->
			<!-- 11thMenu Start -->
			<li id="11thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-bullhorn"></i>
					<span class="menu-text">
						게시판
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="whoutOrder" class="">
						<a href="/mngr/board/noticeList">
							<i class="menu-icon fa fa-caret-right"></i>
							출고지시
						</a>
					</li>
					<li id="11thOne" class="">
						<a href="/mngr/board/qstns">
							<i class="menu-icon fa fa-caret-right"></i>
							문의사항
						</a>
						<b class="arrow"></b>
					</li>
					<li id="11thTwo" class="">
						<a href="/mngr/board/ntc">
							<i class="menu-icon fa fa-caret-right"></i>
							공지사항
						</a>
						<b class="arrow"></b>
					</li>
					<li id="11thThr" class="">
						<a href="/mngr/cs/csInfoDetail">
							<i class="menu-icon fa fa-caret-right"></i>
							CS 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
			
			<li id="invoicethMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-bar-chart-o"></i>
					<span class="menu-text">
						인보이스
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="invoiceZero" class="">
						<a href="/mngr/invoice/compareWeight">
							<i class="menu-icon fa fa-caret-right"></i>
							무게 비교
						</a>
						<b class="arrow"></b>
					</li>
					<li id="invoice0" class="">
						<a href="/mngr/invoice/clearance">
							<i class="menu-icon fa fa-caret-right"></i>
							통관비 등록
						</a>
						<b class="arrow"></b>
					</li>
					<li id="invoice1" class="">
						<a href="/mngr/invoice/invPriceApplyList">
							<i class="menu-icon fa fa-caret-right"></i>
							Price Apply
						</a>
						<b class="arrow"></b>
					</li>
					<li id="invoice2" class="">
						<a href="/mngr/invoice/invoiceList">
							<i class="menu-icon fa fa-caret-right"></i>
							Invoice List
						</a>
						<b class="arrow"></b>
					</li>
					<li id="invoiceOne" class="">
						<a href="/mngr/invoice/invoiceEtcList">
							<i class="menu-icon fa fa-caret-right"></i>
							Etc
						</a>
						<b class="arrow"></b>
					</li>
					<!-- 
					<li id="ARControl" class="">
						<a href="/mngr/invoice/ARControlList">
							<i class="menu-icon fa fa-bar-chart-o"></i>
							A/R Control
						</a>
						<b class="arrow"></b>
					</li>
					<li id="invoiceTwo" class="">
						<a href="/mngr/invoice/ARControlList">
							<i class="menu-icon fa fa-bar-chart-o"></i>
							A/R Control
						</a>
						<b class="arrow"></b>
					</li>
					<li id="ARComfirmList" class="">
						<a href="/mngr/invoice/ARComfirmList">
							<i class="menu-icon fa fa-bar-chart-o"></i>
							A/R Comfirm
						</a>
						<b class="arrow"></b>
					</li>
					<li id="ARDecisionList" class="">
						<a href="/mngr/invoice/ARDecisionList">
							<i class="menu-icon fa fa-bar-chart-o"></i>
							A/R DecisionList
						</a>
						<b class="arrow"></b>
					</li>
					 -->
				</ul>
			</li>
			
			<li id="13thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-external-link"></i>
				 	<span class="menu-text">
						Warehouse
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="13thOne" class="">
						<a href="/mngr/rls/inRegistMawb">
							<i class=	"menu-icon fa fa-caret-right"></i>
							Warehouse 입고
						</a>
					<b class="arrow"></b>
					</li>
					<li id="13thTwo" class="">
						<a href="/mngr/rls/outRegistMawb">
							<i class="menu-icon fa fa-caret-right"></i>
							Warehouse POD
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
			
			<!-- 11thMenu End -->
			<li id="1-9thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-external-link"></i>
				 	<span class="menu-text">
						아라맥스
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<li id="1-9thOne" class="">
						<a href="/mngr/aramex/aramexList">
							<i class=	"menu-icon fa fa-caret-right"></i>
							아라멕스 Excel 업로드
						</a>
					<b class="arrow"></b>
					</li>
				</ul>
			</li>
			
			
			<!-- 14thMenu End -->
			<li id="14thMenu-2" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-external-link"></i>
				 	<span class="menu-text">
						반품
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<!-- <li id="14thTwo-5" class="">
						<a href="/mngr/aplctList/return/depositList">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="14thOne-2-1" class="">
					<a href="/mngr/aplctList/return/taxReturnList">
						<i class="menu-icon fa fa-caret-right"></i>
						위약 반송 리스트
					</a>
					<b class="arrow"></b>
					</li>
					</li> 
					<li id="14thOne-2-1" class="">
						<a href="/mngr/aplctList/return/stationList">
							<i class="menu-icon fa fa-caret-right"></i>
							반품 회수지 관리 
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- 
					<li id="14thTwo-deposit" class="">
						<a href="/mngr/aplctList/return/deposit">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 관리
						</a>
						<b class="arrow"></b>
					</li>
					 -->
					 
					<!-- *********  
					<li id="14thOne-2" class="">
					<a href="/mngr/order/return/list">
						<i class="menu-icon fa fa-caret-right"></i>
						반품 접수 리스트 
					</a>
					<b class="arrow"></b>
					</li>
					<li id="14thOne-2-1" class="">
						<a href="/mngr/aplctList/return/taxReturnList">
							<i class="menu-icon fa fa-caret-right"></i>
							위약 반송 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="14thTwo-2" class="">
						<a href="/mngr/aplctList/returnOrder/inspList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 작업
						</a>
						<b class="arrow"></b>
					</li>
					 -->
					
					
					<li id="returnMenu" class="">
						<a href="/mngr/return/orderList">
							<i class="menu-icon fa fa-caret-right"></i>
							접수 목록
						</a>
					</li>
					<li id="returnMenu-2" class="">
						<a href="/mngr/return/orderWHWork">
							<i class="menu-icon fa fa-caret-right"></i>
							입고 작업
						</a>
						<b class="arrow"></b>
					</li>
					<!-- 
					<li id="returnMenu-2-mb" class="">
						<a href="/mngr/return/orderWHWorkMb">
							<i class="menu-icon fa fa-caret-right"></i>
							입고 작업 (모바일)
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="returnMenu-2-1" class="">
						<a href="/mngr/return/stockList">
							<i class="menu-icon fa fa-caret-right"></i>
							재고 목록
						</a>
						<b class="arrow"></b>
					</li>
					<li id="returnMenu-3" class="">
						<a href="/mngr/return/orderShipment">
							<i class="menu-icon fa fa-caret-right"></i>
							출고 작업
						</a>
					</li>
					<li id="returnMenu-4" class="">
						<a href="/mngr/return/hawbNoList">
							<i class="menu-icon fa fa-caret-right"></i>
							배송 등록
						</a>
					</li>
					<li id="returnMenu-5" class="">
						<a href="/mngr/return/whoutList">
							<i class="menu-icon fa fa-caret-right"></i>
							발송 목록
						</a>
					</li>
					<li id="returnMenu-6" class="">
						<a href="/mngr/return/csList">
							<i class="menu-icon fa fa-caret-right"></i>
							CS 목록
						</a>
					</li>
					<li id="returnMenu-7" class="">
						<a href="/mngr/return/taxReturnList">
							<i class="menu-icon fa fa-caret-right"></i>
							위약반송 목록
						</a>
					</li>
					
					<!-- *******
					<li id="14thTwo-3-1" class="">
						<a href="/mngr/order/return/stockList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 재고
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- <li id="14thTwo-3-1" class="">
						<a href="/mngr/aplctList/return/japanSuccessList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 합격 리스트 (일본)
						</a>
						<b class="arrow"></b>
					</li>
					<li id="14thTwo-3" class="">
						<a href="/mngr/aplctList/return/returnSuccessList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 합격 리스트
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- <li id="14thTwo-4" class="">
						<a href="/mngr/aplctList/return/returnFailList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 불합격 리스트
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- ******** 
					<li id="14thTwo-7" class="">
						<a href="/mngr/aplctList/return/D005">
							<i class="menu-icon fa fa-caret-right"></i>
							재고 폐기 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="14thTwo-6" class="">
						<a href="/mngr/aplctList/return/C002">
							<i class="menu-icon fa fa-caret-right"></i>
							반품 출고 리스트
						</a>
						<b class="arrow"></b>
					</li>
					 -->
					<!-- <li id="14thTwo-8" class="">
						<a href="/mngr/aplctList/return/csList">
							<i class="menu-icon fa fa-caret-right"></i>
							CS 리스트
						</a>
						<b class="arrow"></b>
					</li> -->
					<!-- 
					<li id="14thTwo-5" class="">
						<a href="/mngr/aplctList/return/depositList">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="" class="">
						<a href="/mngr/aplctList/returnOrder/inspList">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 작업
						</a>
					</li> -->
				</ul>
			</li>
			
		
<!-- 
			<li id="14thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-user"></i>
					<span class="menu-text">
						관리자
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>			
				<ul class="submenu">
					<li id="14thOne" class="">
						<a href="/mngr/acnt/adminList">
							<i class="menu-icon fa fa-caret-right"></i>
							관리자 목록
						</a>
					</li>
				</ul>
			</li>
			 -->
			<!-- 14thMenu End -->
		</ul><!-- /.nav-list -->
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>
	</div>