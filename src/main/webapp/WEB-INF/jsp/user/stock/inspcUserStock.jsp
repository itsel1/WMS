<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
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
body{
	font-size:12px;
}
</style>
<!-- basic scripts -->
</head>
<title>검품 재고 리스트</title>
<body class="no-skin">
	<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>재고관리</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">검품 현황</strong></div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>
						검품 현황
					</h3>
					<span class="blue">현지 창고 입고된 상품만 노출됩니다.</span>
				</div>
				<div class="col-xs-12 col-sm-12" id="inner-content-side">
					<div id="search-div">
						<br>
						<form class="hidden-xs hidden-sm" id="dtSearchForm" method="post" action= "/cstmr/stock/inspcUserStock">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width:100%">
									<input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 90px; text-align: center;" value="지역" readonly="true"/>
									<select style="height: 34px;width:80px;" id="orgStation" name="orgStation">
										<c:forEach items="${inspStation}" var="inspStation" varStatus="status">
												<option   value="${inspStation.station}"
														<c:if test="${inspStation.station eq  parameterInfo.orgStation}"> selected</c:if>
												>${inspStation.stationName}   
												</option>
										</c:forEach>
									</select>
									<input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 90px; text-align: center;" value="재고상태" readonly="true"/>
									<select style="height: 34px;width:80px;" id="whStatus" name="whStatus">
									         <option   value=""<c:if test="${parameterInfo.whStatus eq ''}"> selected</c:if>> ::전체::</option>
										<c:forEach items="${whStatus}" var="whStatus" varStatus="status">
												<option   value="${whStatus.code}" 
												<c:if test="${whStatus.code eq  parameterInfo.whStatus}"> selected</c:if>
												> ${whStatus.name}</option>
										</c:forEach>
									</select>
									
									<input type="text" class="form-control" style="width:100%; max-width: 80px; text-align: center;" value="수취인명" readonly="true"/>
									<input type="text" class="form-control" id="cneeName" name="cneeName" style="width:100%; max-width: 150px" value="${parameterInfo.cneeName}"/>
									<input type="text" class="form-control" style="width:100%; max-width: 80px; text-align: center;" value="주문번호" readonly="true"/>
									<input type="text" class="form-control" id="orderNo" name="orderNo" style="width:100%; max-width: 150px" value="${parameterInfo.orderNo}"/>
									<input type="text" class="form-control" style="width:100%; max-width: 80px; text-align: center;" value="운송장번호" readonly="true"/>
									<input type="text" class="form-control" id="hawbNo" name="hawbNo" style="width:100%; max-width: 150px" value="${parameterInfo.hawbNo}"/>
									
										<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
											<i  id="btn_search" class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>
							</div>
							<input type="hidden" id="stockNo" name="stockNo" value="">
							<input type="hidden" id="groupIdx" name="groupIdx" value="">
							<input type="hidden" id="subNo" name="subNo" value="">
							<input type="hidden" id="nno" name="nno" value="">
						</form>
						<div class="col-xs-12 col-sm-12 hidden-md hidden-lg">
							<button type="submit" class="btn btn-default no-border btn-sm" data-toggle="modal" data-target="#searchModal">
								<i class="ace-icon fa fa-search icon-on-right bigger-110">검색조건 입력하기</i>
							</button>
						</div>
						<br>
						
					</div>
					<br>
					<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
						<table id="dynamic-table" class="table table-bordered table-striped containerTable">
							<thead>
								<tr>
									<th class="center colorBlack">
										입고 사진
									</th>	
									<th class="center colorBlack">
										그룹 재고 번호
									</th>
									<th class="center colorBlack">
										Rack
									</th>
									<th class="center colorBlack">
										주문 정보
									</th>
									<th class="center colorBlack">
										수취인 정보
									</th>
									<th class="center colorBlack">
										입고 상태
									</th>
									<th class="center colorBlack">
										입고 메모
									</th>
									<th class="center colorBlack">
										
									</th>
																																											
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${inspStockList}" var="inspStockInfo" varStatus="status">
										<tr>
											<td>
												<c:if test="${!empty inspStockInfo.fileDir}">
													<a href="http://${inspStockInfo.fileDir}" target="_blank">
														<img src="http://${inspStockInfo.fileDir}" style="width:50px">
													</a>
													<br/>
												</c:if>
												<c:if test="${!empty inspStockInfo.fileDir2}">
													<a href="http://${inspStockInfo.fileDir2}" target="_blank">
														<img src="http://${inspStockInfo.fileDir2}" style="width:50px">
													</a>
													<br/>
												</c:if>
												<c:if test="${!empty inspStockInfo.fileDir3}">
													<a href="http://${inspStockInfo.fileDir3}" target="_blank">
														<img src="http://${inspStockInfo.fileDir3}" style="width:50px">
													</a>
													<br/>
												</c:if>
												<c:if test="${!empty inspStockInfo.fileDir4}">
													<a href="http://${inspStockInfo.fileDir4}" target="_blank">
														<img src="http://${inspStockInfo.fileDir4}" style="width:50px">
													</a>
													<br/>
												</c:if>
												<c:if test="${!empty inspStockInfo.fileDir5}">
													<a href="http://${inspStockInfo.fileDir5}" target="_blank">
														<img src="http://${inspStockInfo.fileDir5}" style="width:50px">
													</a>
													<br/>
												</c:if>
											</td>
											<td style="text-align:center;vertical-align: middle;" >
												<span>${inspStockInfo.groupIdx}</span>
											</td>
											<td style="text-align:center;vertical-align: middle;" >
												<span style="font-weight:bold;text-align:center">${inspStockInfo.rackCode}</span>
											</td>
												<td>
												<table class="table table-bordered" style="border-top: 1px solid #ddd;width:100%;background-color: rgba( 255, 255, 255, 0.5 );">
													<tbody>
														<tr>
															<th style="text-align: left;width:80px;">User ID</th>
															<td style="text-align: left;width:150px;">${inspStockInfo.userId}</td>
														</tr>
														<tr>
															<th style="text-align: left;">OrderNo</th>
															<td style="text-align: left;">${inspStockInfo.orderNo}</td>
														</tr>
														<tr>
															<th style="text-align: left;">HawbNo</th>
															<td style="text-align: left;">${inspStockInfo.hawbNo}</td>
														</tr>
													</tbody>
												</table>
											</td>
											
											<td >
												<table class="table table-bordered" style="border-top: 1px solid #ddd;background-color: rgba( 255, 255, 255, 0.5 );">
													<tbody>
														<tr>
															<th style="text-align: left;width:80px;color:black;font-weight: bold;">수취인</th>
															<td style="text-align: left;width:150px;color:black;word-break:break-all">${inspStockInfo.cneeName}</td>
														</tr>
														<tr>
															<th style="text-align: left;color:black;">연락처</th>
															<td style="text-align: left;color:black;word-break:break-all">${inspStockInfo.cneeHp}</td>
														</tr>
														<tr>
															<th style="text-align: left;color:black;">주소</th>
															<td style="text-align: left;color:black;word-break:break-all">${fn:substring(inspStockInfo.cneeAddr,0,30)}</td>
														</tr>
														</tbody>
													</table>
											</td>
											<td style="text-align:center; vertical-align: middle;">
												<span style="font-weight: bold;">${inspStockInfo.whStatusName}</span>
											</td>
											<td >
												<table class="table table-bordered" style="border-top: 1px solid #ddd;background-color: rgba( 255, 255, 255, 0.5 );">
													<tbody>
														<tr>
															<th style="text-align: left;width:80px;color:black;font-weight: bold;">입고메모</th>
															<td style="text-align: left;width:150px;color:black;word-break:break-all">${inspStockInfo.whMemo}</td>
														</tr>
														<tr>
															<th style="text-align: left;color:black;">User Msg</th>
															<td style="text-align: left;color:black;word-break:break-all">${inspStockInfo.userMsg}</td>
														</tr>
														<tr>
															<th style="text-align: left;color:black;">관지라 Msg</th>
															<td style="text-align: left;color:black;word-break:break-all">${inspStockInfo.adminMsg}</td>
														</tr>
													</tbody>
												</table>
											</td>
											<td style="padding:0px;text-align:center;vertical-align:middle;">
												<a class="btn btn-white btn-xs btn-danger" href="javascript:fn_popupOpen('${inspStockInfo.groupIdx}','${inspStockInfo.orgStation}','${inspStockInfo.userId}')">문의하기</a>
											</td>
											
										</tr>
								</c:forEach>
							</tbody>
						</table>
					<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
				</div>
			</div>
		</div>
	</div>





	<!-- headMenu Start -->
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<!-- SideMenu Start -->
		<!-- SideMenu End -->

		<div class="main-content">

			
		</div>
		<!-- /.main-content -->
	</div>


	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
	<script src="/assets/js/bootstrap.min.js"></script>

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
	<!-- ace scripts -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">

			$("#dtSearchBtn").on('click',function(){
				
				$("#dtSearchForm").attr("method", "get");            //get방식으로 바꿔서
				$("#dtSearchForm").submit();
			});


			/*
			function fn_popupOpen(groupIdx, OrgStation, userId){	
				window.open("/mngr/rls/groupidx/popupMsg?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500");
			}
			*/


			function fn_popupOpen(groupIdx, OrgStation, userId){	
				window.open("/cstmr/stock/popupGroupMsg?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500");
			}
			
		
			jQuery(function($) {
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
					select: {
						style: 'multi',
						selector: 'td:first-of-type'
					},
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			
				
				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
			
			
			
			
				/////////////////////////////////
				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable.row(row).select();
						else  myTable.row(row).deselect();
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(this.checked) myTable.row(row).deselect();
					else myTable.row(row).select();
				});
				
			})
			
			$("#testbtn").on("click",function(e){
				
			});
		</script>
	<!-- script addon end -->
</body>
</html>