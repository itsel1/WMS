<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="sidebar-menu">
	<!-- <div class="menu-toggle" onclick="fn_toggleSidebar();">
		<i class="fa-sharp fa-solid fa-angle-left arrow"></i>
	</div> -->
	<div class="sidebar">
		<div class="side-title">
			<div></div>
		</div>
		<hr/>
		<div class="menu-links">
			<div class="menu-link">
				<a href="/cstmr/dashboard">
					<img src="/image/icons/analytics.png" style="width:24px;height:24px;">
					Dashboard
				</a>
			</div>
			
			<div class="menu-link">
				<a href="javascript:void(0);">
					<img src="/image/icons/delivery.png" style="width:24px;height:24px;">
					일반 배송
				</a>
				<div class="sub-menu">
					<a href="/cstmr/apply/v1/shpngAgncy">배송 등록</a>
					<a href="#">송장 출력</a>
					<a href="#">발송 대기 목록</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/inspection.png" style="width:24px;height:24px;">
					검품 배송
				</a>
				<div class="sub-menu">
					<a href="#">배송 등록</a>
					<a href="#">등록 내역</a>
					<a href="#">입출고 내역</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/takein.png" style="width:24px;height:24px;">
					사입 관리
				</a>
				<div class="sub-menu">
					<a href="#">상품 정보 등록</a>
					<a href="#">재고 목록</a>
					<a href="#">주문 등록</a>
					<a href="#">주문 내역</a>
					<a href="#">삭제 내역</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/pod.png" style="width:24px;height:24px;">
					배송 현황
				</a>
				<div class="sub-menu">
					<a href="#">발송 현황</a>
					<a href="#">미발송 현황</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/stock.png" style="width:24px;height:24px;">
					재고 관리
				</a>
				<div class="sub-menu">
					<a href="#">검품 현황</a>
					<a href="#">반품 및 폐기 현황</a>
					<a href="#">미확인 물품 현황</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/return.png" style="width:24px;height:24px;">
					반품 관리
				</a>
				<div class="sub-menu">
					<a href="#">수기 접수</a>
					<a href="#">접수 목록</a>
					<a href="#">재고 목록</a>
					<a href="#">출고 내역</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<img src="/image/icons/setting.png" style="width:24px;height:24px;">
					Setting
				</a>
				<div class="sub-menu">
					<a href="#">회사 정보</a>
					<a href="#">API 정보</a>
				</div>
			</div>
		</div>
	</div>
</div>