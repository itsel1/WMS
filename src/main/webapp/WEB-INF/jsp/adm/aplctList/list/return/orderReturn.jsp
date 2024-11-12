<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>

	<style type="text/css">
	.colorBlack {
		color: #000000 !important;
	}
	.noneArea {
		margin:0px !important;
		padding:0px !important;
		border-bottom : none;
	}
	
	.containerTable {
		width: 100% !important;
	}
	
	table ,tr td{
		word-break:break-all;
	}
	.bottom0 {
		margin-bottom:5px;
	}
	</style>
<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<title>반품</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								반품
							</li>
							<li class="active">반품 입고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 입고 작업
							</h1>
						</div>
						<div id="inner-content-side">
							<div id="search-div">
								
							</div>
							<div id="rgstr-user" style="text-align: left">
								<button type="button" id='registBtn' name='registBtn' class="btn btn-sm btn-primary">
								 입고처리
								</button>
							</div>
							<form name="registForm" id="registForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="table-contents" class="table-contents" style="margin-top:10px;">
									<table id="dynamic-table" class="table table-bordered table-dark containerTable">
										<thead>
											<tr>
												<th class="center" style="width:4%">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:4%">
													도착지
												</th>
												<th class="center colorBlack" style="width:7%">
													USER ID
												</th>
												<th class="center colorBlack" style="width:9%">
													HAWB NO
												</th>
												<th class="center colorBlack" style="width:10%">
													수취인 명
												</th>
												<th class="center colorBlack" style="width:7%">
													Tel.
												</th>
												<th class="center colorBlack" style="width:5%">
													우편번호
												</th>
												<th class="center colorBlack">
													수취인 주소
												</th>
												<th class="center colorBlack" style="width:10%">
													대표 상품명
												</th>
												<th class="center colorBlack" style="width:6%">
													총 금액
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${orderList}" var="orderItem" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel">
															<input type="checkbox" class="ace" id="checkboxs" name="checkboxs" value="${orderItem.nno}"/><span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack">
														${orderItem.dstnNation}
													</td>
													<td class="center colorBlack">
														${orderItem.sellerId}
													</td>
													<td class="center colorBlack">
														${orderItem.koblNo}
													</td>
													<td class="center colorBlack">
														${orderItem.senderName}<br/>
													</td>
													<td class="center colorBlack">
														${orderItem.senderTel}
													</td>
													<td class="center colorBlack">
														${orderItem.senderZip}
													</td>
													<td class="center colorBlack" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">
														${orderItem.senderState} ${orderItem.senderCity} ${orderItem.senderAddr}
													</td>
													<td class="center colorBlack">
														${orderItem.mainItem}
													</td>
													<td class="center colorBlack">
														${orderItem.totalValue}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
			
		<!-- Main container End-->
		
		<!-- Modal Input -->
		<div class="modal fade" id="searchModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true" >
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content" style="border-radius: 40px 40px;">
					<div class="modal-header" style="text-align: center">
					<h3 class="modal-title" id="exampleModalScrollableTitle">상세 조건</h3>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body ">
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="조회날짜" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control" name="start" />
												<input type="text" class="input-sm form-control" name="end" />
											</div>
										</div>
									</div>
									
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5">
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="발송여부" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7">
											<select style="width: 100%; height:34px">
												<option>발송</option>
												<option>미발송</option>
											</select>
										</div>
									</div>
									<%-- <br/>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5">
											<select style="width: 100%;">
												<option>수취인 이름</option>
												<option>수취인 HP</option>
												<option>수취인 주소</option>
											</select>
										</div>
										<div class="col-xs-7 col-sm-7">
											<input type="text" name="keywords1" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5">
											<select style="width: 100%;">
												<option>입고 날짜</option>
												<option>입고 형태</option>
												<option>입고 수량</option>
											</select>
										</div>
										<div class="col-xs-7 col-sm-7">
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div> --%>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="업체ID" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
										<br/>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="송장번호" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="주문번호" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="TrackNo." readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 bottom0">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="수취인 이름" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="text-align: center;border-radius: 0px 0px 40px 40px;">
						<button type="button" class="btn btn-secondary " data-dismiss="modal">닫기</button>
						
						<button type="button" class="btn btn-success">검색</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal Input End -->
		
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<!-- script on paging start -->
		
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
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
	           		$("#14thMenu-2").toggleClass('active'); 
	           		$("#14thTwo-2").toggleClass('active');  
				});

			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
