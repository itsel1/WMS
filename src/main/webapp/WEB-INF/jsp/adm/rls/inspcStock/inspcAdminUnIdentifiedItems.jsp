<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.padding0 {
	padding: 0px !important;
}

.colorBlack {
	color: #000000 !important;
}

.table>tbody>tr>td {
	vertical-align: middle !important;
}

.profile-info-name {
	padding: 0px;
	color: black !important;
	text-align: center !important;
}

.profile-user-info-striped {
	border: 0px solid white !important;
}
/* .profile-user-info-striped{border: 0px solid white !important;}
	  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
.profile-user-info {
	width: 100% !important;
	margin: 0px;
}

/* .col-xs-12 .col-sm-12 {
	padding: 0px !important;
}
 */
.tdBackColor {
	background-color: #FFFFFF;
}

.tdHeader {
	border: 1px solid black;
	padding: 0px !important;
}

.tdHeaderB {
	border: 1px solid black;
	border-bottom: none;
}

.tdHeaderT {
	border: 1px solid black;
	border-top: none;
}

.tdHeaderL {
	border: 1px solid black;
	border-left: none;
	border-right: none;
}

.tdHeaderR {
	border: 1px solid black;
	border-right: none;
	border-left: none;
}

.tdHeaderTR {
	border: 1px solid black;
	border-top: none;
}

@media ( min-width :768px) {
	.tdHeaderB {
		border: 1px solid black;
		border-bottom: none;
	}
	.tdHeaderT {
		border: 1px solid black;
		border-top: none;
	}
	.tdHeaderL {
		border: 1px solid black;
		border-left: none;
	}
	.tdHeaderR {
		border: 1px solid black;
		border-right: none;
	}
	.tdHeaderTR {
		border: 1px solid black;
		border-right: none;
		border-top: none;
	}
}

@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
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
	<title>검품</title>
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
								재고
							</li>
							<li class="active">미확인 물품</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								미확인 물품
							</h1>
						</div>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="dtSearchForm" id="dtSearchForm">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
											<input type="text" class="form-control" style="max-width: 80px;" value="UserID" disabled>
											<input type="text" class="form-control" name="userId" style="max-width: 80px;" value="${parameterInfo.userId}">
											<input type="text" class="form-control" style="max-width: 80px;" value="OrderNo" disabled>
											<input type="text" class="form-control" name="orderNo" value="${parameterInfo.orderNo}" style="max-width: 130px;" value="">
											<input type="text" class="form-control" style="max-width: 80px;" value="HawbNo" disabled>
											<input type="text" class="form-control" name="hawbNo" value="${parameterInfo.hawbNo}" style="max-width: 130px;" value="">
												<!-- <input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 50px; text-align: center;" value="상품명" disabled/>
												<input type="text" class="form-control" id="prdctNameSch" name="prdctNameSch" style="width:100%; max-width: 150px"/> -->
												<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
											</span>
										</div>
									</div>
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
							<div id="table-contents" class="table-contents">
									<table id="dynamic-table" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="center colorBlack">
													입고 사진
												</th>
												<th class="center colorBlack">
													그룹 재고 번호
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
														<td style="width:100px;">
															<c:if test="${!empty inspStockInfo.fileDir}">
																<a href="http://${inspStockInfo.fileDir}" target="_blank">
																	<img src="http://${inspStockInfo.fileDir}" style="width:80px">
																</a>
															</c:if>
															<c:if test="${!empty inspStockInfo.fileDir2}">
																<a href="http://${inspStockInfo.fileDir2}" target="_blank">
																	<img src="http://${inspStockInfo.fileDir2}" style="width:80px">
																</a>
															</c:if>
															<c:if test="${!empty inspStockInfo.fileDir3}">
																<a href="http://${inspStockInfo.fileDir3}" target="_blank">
																	<img src="http://${inspStockInfo.fileDir3}" style="width:80px">
																</a>
															</c:if>
															<c:if test="${!empty inspStockInfo.fileDir4}">
																<a href="http://${inspStockInfo.fileDir4}" target="_blank">
																	<img src="http://${inspStockInfo.fileDir4}" style="width:80px">
																</a>
															</c:if>
															<c:if test="${!empty inspStockInfo.fileDir5}">
																<a href="http://${inspStockInfo.fileDir5}" target="_blank">
																	<img src="http://${inspStockInfo.fileDir5}" style="width:80px">
																</a>
															</c:if>
														</td>
														<td >
															<span>${inspStockInfo.groupIdx}</span>
														</td>
															<td >
															<table class="table table-bordered" style="border-top: 1px solid #ddd;width:100%;background-color: rgba( 255, 255, 255, 0.5 );">
															<tbody>
																<tr>
																	<th style="text-align: left;width:80px;">User ID</th>
																	<td style="text-align: left;width:200px;">${inspStockInfo.userId}</td>
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
														
														<td style="">
															<table class="table table-bordered" style="border-top: 1px solid #ddd;background-color: rgba( 255, 255, 255, 0.5 );">
																<tbody>
																	<tr>
																		<th style="text-align: left;width:100px;color:black;font-weight: bold;">수취인</th>
																		<td style="text-align: left;width:200px;color:black;">${inspStockInfo.cneeName}</td>
																	</tr>
																	<tr>
																		<th style="text-align: left;color:black;">수취인 연락처</th>
																		<td style="text-align: left;color:black;">${inspStockInfo.cneeHp}</td>
																	</tr>
																	<tr>
																		<th style="text-align: left;color:black;">주소</th>
																		<td style="text-align: left;color:black;">${fn:substring(inspStockInfo.cneeAddr,0,30)}</td>
																	</tr>
																	</tbody>
																</table>
														</td>
														<td style="text-align:center;width:100px;">
															<span style="font-weight: bold;">${inspStockInfo.whStatusName}</span>
														</td>
														<td>
															<table class="table table-bordered" style="border-top: 1px solid #ddd;background-color: rgba( 255, 255, 255, 0.5 );">
																<tbody>
																	<tr>
																		<th style="text-align: left;width:80px;color:black;font-weight: bold;">입고메모</th>
																		<td style="text-align: left;width:200px;color:black;">${inspStockInfo.whMemo}</td>
																	</tr>
																	<tr>
																		<th style="text-align: left;color:black;">User Msg</th>
																		<td style="text-align: left;color:black;">${inspStockInfo.userMsg}</td>
																	</tr>
																	<tr>
																		<th style="text-align: left;color:black;">관지라 Msg</th>
																		<td style="text-align: left;color:black;">${inspStockInfo.adminMsg}</td>
																	</tr>
																	</tbody>
																</table>
															<div style="text-align: right">
																<c:if test="${inspStockInfo.newYn eq 'N'}"><span style="font-size: 4px;" class="red">new </span></c:if> <a href="javascript:fn_popupOpen('${inspStockInfo.groupIdx}','${inspStockInfo.orgStation}','${inspStockInfo.userId}')">History</a>
															</div>
														</td>
														<td style="all:none;padding:0px;vertical-align:top;text-align: center;">
															<input type="button" onclick="fn_popupCancle('${inspStockInfo.groupIdx}','${inspStockInfo.orgStation}','${inspStockInfo.userId}')" class="btn btn-white btn-danger" value="${inspStockInfo.whStatusName}취소">
														</td>
														
													</tr>
											</c:forEach>
										</tbody>
									</table>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		
		<form id="mgrForm">
			<input type="hidden" name="orgStation" id="orgStation" value="">
			<input type="hidden" name="userId" id="userId" value="">
			<input type="hidden" name="groupIdx" id="groupIdx" value="">
		</form>
			
		<!-- Main container End-->
		<!-- 모바일 검색 조건 입력 modal -->
		<!-- Modal -->
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
		
		<script type="text/javascript">
			$("#dtSearchBtn").on('click',function(){
				$("#dtSearchForm").attr("method", "get");            //get방식으로 바꿔서
				$("#dtSearchForm").submit();
			});
		
			function fn_popupCancle(groupIdx, OrgStation, userId){	

				$("#orgStation").val(OrgStation);
				$("#userId").val(userId);
				$("#groupIdx").val(groupIdx);

				var formData = $("#mgrForm").serialize();
				var result = confirm('정말 취소 하시겠습니까?');
				if(result) {
					$.ajax({
						url:'/comn/StockCancle',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							
							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				}
			}

			function fn_popupOpen(groupIdx, OrgStation, userId){	
				window.open("/mngr/rls/groupidx/popupMsg?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500");
			}
		</script>		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">				
			jQuery(function($) {
				$(document).ready(function() {

					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFiv").toggleClass('active');

					var myTable = $('#dynamic-table').DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
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
			});
		</script>
	</body>
</html>
