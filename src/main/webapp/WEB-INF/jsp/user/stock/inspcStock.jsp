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
<script src="/assets/js/jquery-2.1.4.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
</head>
<title>마이페이지</title>
<body class="no-skin">
	<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="toppn">
						<div class="container">
							<div class="row">
								<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>마이 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">정보 수정</strong></div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="page-content">
							<div class="page-header">
								<h1>
									검품 재고
								</h1>
							</div>
							<div class="col-xs-12 col-sm-12" id="inner-content-side">
								<div id="search-div">
									<br>
									<form class="hidden-xs hidden-sm" name="dtSearch" id="dtSearch">
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
														<option value="3">입고 수량</option>
													</select>
													<input type="text" class="form-control" id="housText" name="housText" style="width:100%; max-width: 150px"/>
													<input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 50px; text-align: center;" value="상품명" readonly="true"/>
													<input type="text" class="form-control" id="prdctNameSch" name="prdctNameSch" style="width:100%; max-width: 150px"/>
													<input type="text" class="form-control" name="adres" style="width:100%; max-width: 65px; text-align: center;" value="우편번호" readonly="true"/>
													<input type="text" class="form-control" id="adresSch" name="adresSch" style="width:100%; max-width: 150px"/>
													<input type="text" class="form-control" name="trackNo" style="width:100%; max-width: 70px; text-align: center;" value="TrackNo" readonly="true"/>
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
				</div>
			</div><!-- /.main-content -->
		</div>
	</div>





	<!-- headMenu Start -->
	<!-- headMenu End -->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
	<!-- <script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/testView/js/bootstrap.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	
	<script src="/testView/js/main.js"></script> -->

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
	<!-- <script src="/assets/js/ace-elements.min.js"></script> -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<!-- <script src="/assets/js/ace.min.js"></script> -->
	<!-- script on paging end -->

	<!-- script addon start -->
	<script type="text/javascript">
			function fn_popupOpen(groupIdx, nno, subNo){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				$("#subNo").val(subNo);
				window.open("/cstmr/stock/popupMsg?nno="+$("#nno").val()+"&groupIdx="+$("#groupIdx").val()+"&subNo="+$("#subNo").val(),"_blank","toolbar=yes","resizable=no","directories=no","width=580, height=360");
			}
			
			jQuery(function($) {
				$(document).ready(function() {
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
			
			$("#testbtn").on("click",function(e){
				
			});
		</script>
	<!-- script addon end -->
</body>
</html>