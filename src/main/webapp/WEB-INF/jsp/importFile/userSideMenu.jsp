<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
</head>
<body>
	<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
		<script type="text/javascript">
			try{ace.settings.loadState('sidebar')}catch(e){}
		</script>
		<ul class="nav nav-list">
			<li id="zeroMenu" class=""><!-- zeroMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						배송대행 이란
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu">
					<li id= "zeroOne" class="">
						<a href="/cstmr/shpngAgncy/Info">
							<i class="menu-icon fa fa-caret-right"></i>
							배송대행 안내
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "zeroTwo" class="">
						<a href="/cstmr/shpngAgncy/ovrssAdres">
							<i class="menu-icon fa fa-caret-right"></i>
							해외 주소지
						</a>
						<b class="arrow"></b>
					</li>

					<li id= "zeroThr" class="">
						<a href="/cstmr/shpngAgncy/applyInfo">
							<i class="menu-icon fa fa-caret-right"></i>
							신청 방법 안내
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "zeroFor" class="">
						<a href="/cstmr/shpngAgncy/member">
							<i class="menu-icon fa fa-caret-right"></i>
							회원 등급
						</a>
						<b class="arrow"></b>
					</li>
				</ul>
			</li><!-- zeroMenu End -->
			<li id="firstMenu" class=""><!-- firstMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						요금안내
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu">
					<li id= "1stOne" class="">
						<a href="/cstmr/rates/ovrssChrgs">
							<i class="menu-icon fa fa-caret-right"></i>

							배송대행 해외 지역별 요금
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "1stTwo" class="">
						<a href="/cstmr/rates/rtrnChrgs">
							<i class="menu-icon fa fa-caret-right"></i>
							해외 반송 해외지역별 요금
						</a>

						<b class="arrow"></b>
					</li>

					<li id= "1stThr" class="">
						<a href="/cstmr/rates/extraSrvc">
							<i class="menu-icon fa fa-caret-right"></i>
							부가서비스 요금
						</a>

						<b class="arrow"></b>
					</li>
				</ul>
			</li><!-- firstMenu End -->
			<li id="secondMenu" class=""><!-- secondMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						관세 및 통관
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "2ndOne" class="">
						<a href="/cstmr/csdcsClrnc/gdnc" >
							<i class="menu-icon fa fa-caret-right"></i>
							관·부과세 안내
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "2ndTwo" class="">
						<a href="/cstmr/csdcsClrnc/lstgnClrnc">
							<i class="menu-icon fa fa-caret-right"></i>
							목록통관 및 일반통관
						</a>
						<b class="arrow"></b>
					</li>

					<li id= "2ndThr" class="">
						<a href="/cstmr/csdcsClrnc/itmimExprt">
							<i class="menu-icon fa fa-caret-right"></i>
							수입·수출 금지품목
						</a>

						<b class="arrow"></b>
					</li>
					<li id= "2ndFor" class="">
						<a href="/cstmr/csdcsClrnc/exchn">
							<i class="menu-icon fa fa-caret-right"></i>
							관세청 고시 환율
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- secondMenu End -->
			
			<li id="thirdMenu" class=""><!-- thirdMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						쇼핑하기
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "3rdOne" class="">
						<a href="/cstmr/shop/ovrss" >
							<i class="menu-icon fa fa-caret-right"></i>
							해외 쇼핑몰
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "3rdTwo" class="">
						<a href="/cstmr/shop/dmstc">
							<i class="menu-icon fa fa-caret-right"></i>
							국내 쇼핑몰
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
					</li>
					
					<!-- 하부에 해외 쇼핑몰 및 국내 쇼핑몰 URL 연결 -->
				</ul> <!-- subMenu End -->
			</li><!-- thirdMenu End -->
			<li id="forthMenu" class=""><!-- forthMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						배송대행 신청
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "4thOne" class="">
						<a href="/cstmr/apply/shpngAgncy" >
							<i class="menu-icon fa fa-caret-right"></i>
							배송대행 신청서 
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "4thTwo" class="">
						<a href="/cstmr/apply/inspcDlvry">
							<i class="menu-icon fa fa-caret-right"></i>
							검품배송 신청
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "4thThr" class="">
						<a href="/cstmr/apply/prchsDlvry">
							<i class="menu-icon fa fa-caret-right"></i>
							사입배송 신청
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "4thFor" class="">
						<a href="/cstmr/apply/pymnt">
							<i class="menu-icon fa fa-caret-right"></i>
							결제하기
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- forthMenu End -->
			<li id="5thMenu" class=""><!-- 5thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						반송 대행
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "5thOne" class="">
						<a href="/cstmr/rtrn/rtrnAplct" >
							<i class="menu-icon fa fa-caret-right"></i>
							해외 반송 신청서
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "5thTwo" class="">
						<a href="/cstmr/rtrn/rtrnPymnt">
							<i class="menu-icon fa fa-caret-right"></i>
							결제하기
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 5thMenu End -->
			<li id="6thMenu" class=""><!-- 6thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						배송 현황
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "6thOne" class="">
						<a href="/cstmr/dlvry/stts">
							<i class="menu-icon fa fa-caret-right"></i>
							발송 현황
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "6thTwo" class="">
						<a href="/cstmr/dlvry/undlvStts">
							<i class="menu-icon fa fa-caret-right"></i>
							미발송 현황
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "6thThr" class="">
						<a href="/cstmr/dlvry/podLokup">
							<i class="menu-icon fa fa-caret-right"></i>
							POD 조회
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 6thMenu End -->
			<li id="7thMenu" class=""><!-- 7thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						예치금 관리
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "7thOne" class="">
						<a href="/cstmr/dpst/dpstAplct" >
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 신청
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "7thTwo" class="">
						<a href="/cstmr/dpst/wthdrList">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 입출금 리스트
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "7thThr" class="">
						<a href="/cstmr/dpst/rqstRfnd">
							<i class="menu-icon fa fa-caret-right"></i>
							예치금 환불 신청
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 7thMenu End -->
			<li id="8thMenu" class=""><!-- 8thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						고객센터
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "8thOne" class="">
						<a href="/cstmr/srvc/ntc" >
							<i class="menu-icon fa fa-caret-right"></i>
							공지사항
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "8thTwo" class="">
						<a href="/cstmr/srvc/frqntAskqs">
							<i class="menu-icon fa fa-caret-right"></i>
							자주하는 질문
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "8thThr" class="">
						<a href="/cstmr/srvc/cntct">
							<i class="menu-icon fa fa-caret-right"></i>
							1:1 문의하기
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "8thFor" class="">
						<a href="/cstmr/srvc/rvws">
							<i class="menu-icon fa fa-caret-right"></i>
							이용 후기
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "8thFiv" class="">
						<a href="/cstmr/srvc/event">
							<i class="menu-icon fa fa-caret-right"></i>
							이벤트
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 8thMenu End -->
			<li id="9thMenu" class=""><!-- 9thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						재고관리
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "9thOne" class="">
						<a href="/cstmr/stock/prdctRgstr" >
							<i class="menu-icon fa fa-caret-right"></i>
							상품등록
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "9thTwo" class="">
						<a href="/cstmr/stock/prchsRgstr">
							<i class="menu-icon fa fa-caret-right"></i>
							사입상품 입고 등록
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "9thThr" class="">
						<a href="/cstmr/stock/invntStts">
							<i class="menu-icon fa fa-caret-right"></i>
							사입 재고 현황
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 9thMenu End -->
			<li id="10thMenu" class=""><!-- 10thMenu Start -->
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text">
						마이 페이지
					</span>

					<b class="arrow fa fa-angle-down"></b>
				</a>
				
				<b class="arrow"></b>
				
				<ul class="submenu"> <!-- subMenu Start -->
					<li id= "10thOne" class="">
						<a href="/cstmr/myPage/aplctList" >
							<i class="menu-icon fa fa-caret-right"></i>
							나의 신청서 LIST
						</a>
						<b class="arrow"></b>
					</li>
					<li id= "10thTwo" class="">
						<a href="/cstmr/myPage/userInfo">
							<i class="menu-icon fa fa-caret-right"></i>
							내 정보
						</a>
						<b class="arrow"></b>
					</li>
				</ul> <!-- subMenu End -->
			</li><!-- 10thMenu End -->
		</ul><!-- /.nav-list -->

		<!-- <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div> -->
	</div>
</body>
</html>