<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
</head>
<body>
	<div id="sidebar" class="sidebar                  responsive                    ace-save-state sidebar-fixed">
		<script type="text/javascript">
			try{ace.settings.loadState('sidebar')}catch(e){}
		</script>
		<ul class="nav nav-list">
			<li id="zeroMenu" class=""><!-- zeroMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-users"></i>
					<span class="menu-text">
						거래처 관리
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu">
					<li id="zeroOne" class="">
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
					</li>
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
						신청서 리스트
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
					<!-- <li id="3rdOne" class="">
						<a href="/mngr/aplctList/inspcList">
							<i class="menu-icon fa fa-caret-right"></i>
							검품 등록 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="3rdTwo" class="">
						<a href="/mngr/aplctList/prchsList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 등록리스트
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="3rdThr" class="">
						<a href="/mngr/aplctList/shpngList">
							<i class="menu-icon fa fa-caret-right"></i>
							운송장 등록 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id="3rdFor" class="">
						<a href="/mngr/aplctList/dltList">
							<i class="menu-icon fa fa-caret-right"></i>
							삭제 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li>
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
					<!-- <li id="4thOne" class="">
						<a href="/mngr/order/orderInspc">
							<i class="menu-icon fa fa-caret-right"></i>
							검수 작업
						</a>
						<b class="arrow"></b>
					</li> -->
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
				</ul>
			</li>
			<!-- 4thMenu End -->
			<!-- 5thMenu Start -->
			<!-- <li id="5thMenu" class="">
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
						<a href="/mngr/prchs/prdctCode">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 상품 코드 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="5thTwo" class="">
						<a href="/mngr/prchs/prdctRcpt">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 상품 입고 관리
						</a>
						<b class="arrow"></b>
					</li>
					<li id="5thThr" class="">
						<a href="/mngr/prchs/stockList">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 재고 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
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
					</li>
					<li id="6thThr" class="">
						<a href="/mngr/rls/inspcStock">
							<i class="menu-icon fa fa-caret-right"></i>
							검품 재고
						</a>
						<b class="arrow"></b>
					</li> -->
					<li id="6thFor" class="">
						<a href="/mngr/rls/mawbList">
							<i class="menu-icon fa fa-caret-right"></i>
							MAWB LIST
						</a>
						<b class="arrow"></b>
					</li>
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
			<!-- <li id="11thMenu" class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-bullhorn"></i>
					<span class="menu-text">
						게시판
					</span>
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">
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
						<a href="/mngr/board/csList">
							<i class="menu-icon fa fa-caret-right"></i>
							CS 리스트
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li> -->
			<!-- 11thMenu End -->
		</ul><!-- /.nav-list -->
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>

		<!-- <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div> -->
	</div>
</body>
</html>