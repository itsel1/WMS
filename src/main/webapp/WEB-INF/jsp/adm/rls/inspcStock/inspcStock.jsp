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
		

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<title>검품 재고</title>
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
								출고
							</li>
							<li class="active">검품 재고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								검품 재고
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
												<select style="height: 34px" id="rcvrType" name="rcvrType">
													<option selected value="1">수취인 이름</option>
													<option value="2">수취인 HP</option>
													<option value="3">수취인 주소</option>
												</select>
												<input type="text" class="form-control" id="rcvrText" name="rcvrText" style="width:100%; max-width: 200px"/>
												<select style="height: 34px" id="housType" name="housType">
													<option selected value="1">입고 날짜</option>
													<option value="2">입고 형태</option>
												</select>
												<input type="text" class="form-control" id="housText" name="housText" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 50px; text-align: center;" value="상품명" disabled/>
												<input type="text" class="form-control" id="prdctNameSch" name="prdctNameSch" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="adres" style="width:100%; max-width: 65px; text-align: center;" value="우편번호" disabled/>
												<input type="text" class="form-control" id="adresSch" name="adresSch" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="trackNo" style="width:100%; max-width: 70px; text-align: center;" value="TrackNo" disabled/>
												<input type="text" class="form-control" id="trackNoSch" name="trackNoSch" style="width:100%; max-width: 150px"/>
												<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
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
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display" style="width:1620px">
									<table id="dynamic-table" class="table table-bordered table-dark">
										<thead>
											<tr>
												<th class="center col-xs-1">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													입고 사진
												</th>
												<th class="center colorBlack">
													입고 정보
												</th>
												<th class="center colorBlack">
													상품명
												</th>
												<th class="center colorBlack">
													우편번호
												</th>
												<th class="center colorBlack">
													TrackCom./TrackNo./StockNo.
												</th>
												<th class="center colorBlack">
													메모
												</th>
												<th class="center colorBlack">
													고객 메시지
												</th>
												<th class="center colorBlack">
													업로드 이미지
												</th>
												<th class="center colorBlack">
													관리
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${inspStockList}" var="inspStockInfo" varStatus="status">
												<c:if test="${inspStockList[status.index].nno ne inspStockList[status.index*1-1].nno}">
													<tr style="background-color: #ABBAC3;">
														<td class="col-sm-12" colspan="10">
															<table class="table table-bordered table-dark" style="border-top:1px solid #ddd;">
																<tr>
																	<td>
																		주문 번호
																	</td>
																	<td>
																		<span>${inspStockInfo.orderNo}</span>
																	</td>
																	<td>
																		수취인 이름
																	</td>
																	<td>
																		<span>${inspStockInfo.cneeName}</span>
																	</td>
																	<td>
																		수취인 HP
																	</td>
																	<td>
																		<span>${inspStockInfo.cneeHp}</span>
																	</td>
																	<td>
																		수취인 주소
																	</td>
																	<td>
																		<span>${inspStockInfo.cneeAddr} ${inspStockInfo.cneeAddrDetail}</span>
																	</td>
																	
																</tr>
															</table>
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
														<td style="display: none;">
														</td>
													</tr>
												</c:if>
												<tr>
													<td id="checkboxTd${inspStockInfo.stockNo}" name="checkboxTd" class="center" style="word-break:break-all;width:20px;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" />
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center">
														<span>
															<img src="${inspStockInfo.itemImgUrl}" width="100"/>
														</span>
													</td>
												
													<td class="center padding0" style="width:300px">
														<div class="col-xs-12 col-sm-12">
															<div class="profile-user-info profile-user-info-striped">
																<div class="profile-info-row">
																	<div class="profile-info-name"> 상품 번호</div>
					
																	<div class="profile-info-value">
																		<span>${inspStockInfo.subNo}</span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> 재고 날짜 </div>
																	<div class="profile-info-value">
																		<span>${inspStockInfo.inDate}</span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> 입고 형태 </div>
					
																	<div class="profile-info-value">
																		<span>${inspStockInfo.whStatusName}</span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> 재고 수량 </div>
					
																	<div class="profile-info-value">
																		<span>${inspStockInfo.whCnt} / ${inspStockInfo.totalWhCnt}</span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> RACK 위치</div>
					
																	<div class="profile-info-value">
																		<span>${inspStockInfo.rackCode}</span>
																	</div>
																</div>
															</div>
														</div>
													</td>
												
													<td class="center" style="width:250px">
														<span>
															${inspStockInfo.itemDetail}
														</span>
													</td>
													<td id="testTarget" class="center" style="width:100px">
														<span>
															${inspStockInfo.cneeZip}
															
														</span>
													</td>
													<td class="center" style="width:150px">
														<span>
															${inspStockInfo.trkCom}/${inspStockInfo.trkNo}/${inspStockInfo.stockNo}
														</span>
													</td>
													<td class="center" style="width:240px">
														<input type="text" readonly id="whMemo${inspStockInfo.stockNo}" name="whMemo" value="${inspStockInfo.whMemo}"/>
													</td>
													<td class="left" style="width:200px">
														<input type="text" id="userMsg${inspStockInfo.stockNo}" name="userMsg" readonly value="${inspStockInfo.userMsg}"/>
														<div style="text-align: right">
															<a href="javascript:fn_popupOpen('${inspStockInfo.groupIdx}','${inspStockInfo.nno}','${inspStockInfo.subNo}')">History</a>
														</div>
														<input type="text" id="adminMsg${inspStockInfo.stockNo}" name="adminMsg" value="${inspStockInfo.adminMsg}"/>
													</td>
													<td class="center" style="width:220px">
														<span>
															<c:forEach items="${inspStockInfo.filePath}" var="file" varStatus="status">
																<a href="http://${file}" target="_blank">
																	<img src="http://${file}" style="width:150px">
																</a> 
															</c:forEach>
														</span>
													</td>
													<td class="center padding0" style="width:120px">
														<div>
															<button type="button" class="btn btn-white btn-xs btn-danger" style="margin:2px;">반품</button><br/>
															<button type="button" class="btn btn-white btn-xs btn-danger" style="margin:2px;">폐기</button><br/>
															<button type="button" onclick="javascript:fn_returnInsp('${inspStockInfo.groupIdx}','${inspStockInfo.stockNo}')" class="btn btn-white btn-xs btn-danger" style="margin:2px">입고 취소</button><br/>
															<button type="button" onclick="javascript:fn_pdfPopup('${inspStockInfo.groupIdx}','${inspStockInfo.nno}')"class="btn btn-white btn-xs btn-primary" style="margin:2px;">재고번호 출력</button>
														</div>
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
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		<!-- 모바일 검색 조건 입력 modal -->
		<!-- Modal -->
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
									</div>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="상품명" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
										
									</div>
									<div class="col-xs-12 col-sm-12 ">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="우편번호" readonly="true"/>
										</div>
										<div class="col-xs-7 col-sm-7" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%;"/>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12">
										<div class="col-xs-5 col-sm-5" >
											<input type="text" name="keywords2" placeholder="${searchKeyword}" style="width:100%; text-align: center;" value="TrackNo" readonly="true"/>
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
		
		<!-- script addon start -->
		<script type="text/javascript">
			$("#dtSearchBtn").on('click',function(){
				$("#dtSearchForm").attr("method", "get");            //get방식으로 바꿔서
				$("#dtSearchForm").submit();
			});
			
			function fn_popupOpen(groupIdx, nno, subNo){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				$("#subNo").val(subNo);
				window.open("/mngr/rls/popupMsg?nno="+$("#nno").val()+"&groupIdx="+$("#groupIdx").val()+"&subNo="+$("#subNo").val(),"_blank","toolbar=yes","resizable=no","directories=no","width=580, height=360");
			}
				
			function fn_returnInsp(groupIdx, stockNo){
				$("#groupIdx").val(groupIdx);
				$("#stockNo").val(stockNo);

				$("#dtSearch").attr('action','/mngr/rls/cancleInspStock');
				$("#dtSearch").attr('method','post');
				$("#dtSearch").submit();
			}

			function fn_pdfPopup(groupIdx, nno, subNo){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				window.open("/mngr/order/pdfPopup?nno="+$("#nno").val()+"&groupIdx="+$("#groupIdx").val(),"_blank","toolbar=yes","resizable=no","directories=no","width=480, height=360");
			}
			
			jQuery(function($) {
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thThr").toggleClass('active');


					var myTable = $('#dynamic-table').DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : "580",
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
			});
		</script>
		<!-- script addon end -->
		
	</body>
</html>
