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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- ace scripts -->
	<!-- basic scripts End-->
	</head> 
	<title>검수 작업</title>
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
								입고 작업
							</li>
							<li class="active">검수 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								검수 작업
							</h1>
						</div>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="search-button" id="search-botton" method="get" action="/mngr/acnt/entrp">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px">
													<option>수취인 이름</option>
													<option>수취인 HP</option>
													<option>수취인 주소</option>
												</select>
												<input type="text" class="form-control" name="keywords1" placeholder="${searchKeyword}" style="width:100%; max-width: 200px"/>
												<select style="height: 34px">
													<option>입고 날짜</option>
													<option>입고 형태</option>
													<option>입고 수량</option>
												</select>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 50px; text-align: center;" value="상품명" readonly="true"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 65px; text-align: center;" value="우편번호" readonly="true"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="TrackNo" readonly="true"/>
												<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
												<button type="submit" class="btn btn-default no-border btn-sm">
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
								<form name="rgstr-button" id="search-botton" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div id="rgstr-user" style="text-align: right">
										<button type="button" class="btn btn-sm btn-primary">
										바코드
										</button>
										<button type="button" class="btn btn-sm btn-primary">
										 검수합격
										</button>
									</div>
								</form>
							</div>
							<br>
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display" style="width:1620px;">
									<table id="dynamic-table" class="table table-bordered table-dark">
										<colgroup>
											<col width="20"/>
											<col width="270"/><!-- 수취인정보 -->
											<col width="270"/><!-- 입고정보 -->
											<%-- <col width="220"/><!-- 입고사진 --> --%>
											<col width="130"/><!-- 우편번호 -->
											<col width="300"/><!-- 상품명 -->
											<col width="240"/><!-- TrackNo. -->
										</colgroup>
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													수취인 정보
												</th>
												<th class="center colorBlack">
													입고 정보
												</th>
												<!-- <th class="center colorBlack">
													입고 사진
												</th> -->
												<th class="center colorBlack">
													상품명
												</th>
												<th class="center colorBlack">
													우편번호
												</th>
												<th class="center colorBlack">
													TrackNo.
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${orderInspcList}" var="orderInspcVO" varStatus="status">
											<c:if test="${orderInspcVO.rank eq 1 }">
												<tr>
													<td class="center" style="word-break:break-all;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" />
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center padding0 col-xs-1 col-sm-1 col-md-2 col-lg-2">
														<div class="col-xs-12 col-sm-12">
															<div class="profile-user-info profile-user-info-striped">
																<div class="profile-info-row">
																	<div class="profile-info-name"> 수취인 이름 </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.rcvrName}</span>
																	</div>
																</div>
	
																<div class="profile-info-row">
																	<div class="profile-info-name"> 수취인 HP </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.rcvrHp}</span>
																	</div>
																</div>
	
																<div class="profile-info-row">
																	<div class="profile-info-name"> 수취인 주소 </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.rcvrAdres}</span>
																	</div>
																</div>
															</div>
														</div>
													</td>
													<td class="center padding0 col-xs-1 col-sm-1 col-md-2 col-lg-2">
														<div class="col-xs-12 col-sm-12">
															<div class="profile-user-info profile-user-info-striped">
																<div class="profile-info-row">
																	<div class="profile-info-name"> 입고 날짜 </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.housDate}</span>
																	</div>
																</div>
	
																<div class="profile-info-row">
																	<div class="profile-info-name"> 입고 형태 </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.housType}</span>
																	</div>
																</div>
	
																<div class="profile-info-row">
																	<div class="profile-info-name"> 입고 수량 </div>
	
																	<div class="profile-info-value">
																		<span>${orderInspcVO.housCnt}</span>
																	</div>
																</div>
															</div>
														</div>
													</td>
													
													<td class="center">
														<span>
															<br/>
															${orderInspcVO.adresNum}
														</span>
													</td>
													
													<%-- <td class="center">
														<a href = "${orderInspcVO.housImg}" target="_blank">
															<img src="${orderInspcVO.housImg}" alt="test" width="100">
														</a>
													</td> --%>
													<td id="testTarget" class="center" data-toggle="modal" data-target="#exampleModalScrollable" style="cursor:pointer;">
													<c:if test="${orderInspcList[status.index].rank eq 1}">
														<span>
															${orderInspcVO.prdctName}
														</span>
													</c:if>
													<c:if test="${orderInspcList[status.index+1].rank ne 1 and not empty orderInspcList[status.index+1] }">
														<c:forEach items="${orderInspcList}" var="orderInspcSubVO" begin="${status.index+1}" varStatus="subStatus">
															<c:if test="${orderInspcList[subStatus.index].rank ne 1}">
																<span>
																	<br/>
																	${orderInspcSubVO.prdctName}
																</span>
															</c:if>
														</c:forEach>
													</c:if>
													</td>
													<td class="center">
													<input type="hidden" id = "trackNo" value="${orderInspcVO.trackNo}"/>
													
													<c:if test="${orderInspcList[status.index].rank eq 1}">
														${orderInspcVO.trackNo}
													</c:if>
													<c:if test="${orderInspcList[status.index+1].rank ne 1 and not empty orderInspcList[status.index+1] }">
														<c:forEach items="${orderInspcList}" var="orderInspcSubVO" begin="${status.index+1}" varStatus="subStatus">
															<c:if test="${orderInspcList[subStatus.index].rank ne 1}">
																<span>
																	<br/>
																	${orderInspcVO.trackNo}
																</span>
															</c:if>
														</c:forEach>
													</c:if>
													</td>
												</tr> 
											</c:if>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Modal Strat -->
		<!-- Modal -->
		<!-- 임시 적용 내용, 나중에 적용시, attr등을 사용한 동적으로 처리 가능하게 변경 -->
		<div class="modal fade" id="exampleModalScrollable" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true" >
			<div class="modal-dialog modal-dialog-scrollable modal-lg" role="document">
				<div class="modal-content" style="border-radius: 40px 40px;">
					<div class="modal-header" style="text-align: center">
					<h3 class="modal-title" id="exampleModalScrollableTitle">상세 정보</h3>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body-info col-xs-12 col-sm-12 col-md-12 col-lg-12" style="background-color: white">
						<div class="row" id ="modalBody">
							<!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
								<img src="" alt="test" width="50%" id="modalsHousImg">
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">
											수취인 정보
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											수취인 이름
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrName">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor" >
											수취인 HP
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrHp">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											수취인 주소
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrAdres">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeaderT tdBackColor">
											입고 정보
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 날짜
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousDate">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 형태
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousType">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 수량
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousCnt">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											상품명
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsPrdctName">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											우편번호
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsAdresNum">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3  tdHeaderTR tdBackColor">
											Track No.
										</div>
										<div class="col-xs-12 col-sm-9  tdHeaderT" id="modalsTrackNo">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">
											검수 불합격 사유
										</div>
										<div class="col-xs-12 col-sm-12  tdHeaderT">
											<textarea rows="5" class="col-xs-12 col-sm-12" style="resize:none"></textarea>
										</div>
									</div>
								</div>
							</div>-->
						</div> 
						<br/>
					</div>
					<div class="modal-footer" style="text-align: center;border-radius: 0px 0px 40px 40px;">
						<button type="button" class="btn btn-secondary " data-dismiss="modal">닫기</button>
						
						<button type="button" class="btn btn-danger">검수 불합격</button>
						
						<button type="button" class="btn btn-success">검수 합격</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal End -->
		
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
		<!-- ace scripts -->
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFor").toggleClass('active');
				});


				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : "500",
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

				$('#dynamic-table > tbody > tr > td[id=testTarget]').on('click', function(){
					$.ajax({
						url:'/mngr/order/modalInspc',
						type: 'POST',
						data: {trackNo:$(this).parent().find("#trackNo").val().trim()},
						success : function(data) {
							var addHtml = "";
							addHtml = addHtml + "";
							$.each(data, function(idx,val){
								/* addHtml = addHtml + '<div class="row">                                                                  '     ; */
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">  '     ;
								addHtml = addHtml + '<img src="'+data[idx].housImg+'" alt="test" width="50%" id="modalsHousImg">                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">                             '     ;
								addHtml = addHtml + '수취인 정보                                                                           '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '수취인 이름                                                                           '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrName">                     '     ;
								addHtml = addHtml + data[idx].rcvrName;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor" >                           '     ;
								addHtml = addHtml + '수취인 HP                                                                            '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrHp">                       '     ;
								addHtml = addHtml + data[idx].rcvrHp;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '수취인 주소                                                                           '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrAdres">                    '     ;
								addHtml = addHtml + data[idx].rcvrAdres;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeaderT tdBackColor">                            '     ;
								addHtml = addHtml + '입고 정보                                                                            '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '입고 날짜                                                                            '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousDate">                     '     ;
								addHtml = addHtml + data[idx].housDate;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '입고 형태                                                                            '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousType">                     '     ;
								addHtml = addHtml + data[idx].housType;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '입고 수량                                                                            '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousCnt">                      '     ;
								addHtml = addHtml + data[idx].housCnt;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '상품명                                                                               '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsPrdctName">                    '     ;
								addHtml = addHtml + data[idx].prdctName;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">                            '     ;
								addHtml = addHtml + '우편번호                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsAdresNum">                     '     ;
								addHtml = addHtml + data[idx].adresNum;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-3  tdHeaderTR tdBackColor">                           '     ;
								addHtml = addHtml + 'Track No.                                                                          '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-9  tdHeaderT" id="modalsTrackNo">                     '     ;
								addHtml = addHtml + data[idx].trackNo;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                              '     ;
								addHtml = addHtml + '<div class="row">                                                                  '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">                             '     ;
								addHtml = addHtml + '검수 불합격 사유                                                                       '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '<div class="col-xs-12 col-sm-12  tdHeaderT">                                       '     ;
								addHtml = addHtml + '<textarea rows="5" class="col-xs-12 col-sm-12" style="resize:none"></textarea>     '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								addHtml = addHtml + '</div>                                                                             '     ;
								/* addHtml = addHtml + '</div>                                                                             '     ; */
							})
							   $("#modalBody").html(addHtml);
							   
							   



							
			              /*   $("#modalsRcvrName").text(data.rcvrName);
			                $("#modalsRcvrHp").text(data.rcvrHp);
			                $("#modalsRcvrAdres").text(data.rcvrAdres);
			                $("#modalsHousDate").text(data.housDate);
			                $("#modalsHousType").text(data.housType);
			                $("#modalsHousCnt").text(data.housCnt);
			                $("#modalsHousImg").attr('src',data.housImg);
			                $("#modalsPrdctName").text(data.prdctName);
			                $("#modalsAdresNum").text(data.adresNum);
			                $("#modalsTrackNo").text(data.trackNo);


			                <div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
								<img src="" alt="test" width="50%" id="modalsHousImg">
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">
											수취인 정보
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											수취인 이름
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrName">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor" >
											수취인 HP
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrHp">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											수취인 주소
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsRcvrAdres">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeaderT tdBackColor">
											입고 정보
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 날짜
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousDate">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 형태
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousType">
										</div>
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											입고 수량
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsHousCnt">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											상품명
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsPrdctName">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3 tdHeaderTR tdBackColor">
											우편번호
										</div>
										<div class="col-xs-12 col-sm-9 tdHeaderT" id="modalsAdresNum">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-3  tdHeaderTR tdBackColor">
											Track No.
										</div>
										<div class="col-xs-12 col-sm-9  tdHeaderT" id="modalsTrackNo">
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12 tdHeader" style="text-align: center;">
										<div class="col-xs-12 col-sm-12 tdHeader tdBackColor">
											검수 불합격 사유
										</div>
										<div class="col-xs-12 col-sm-12  tdHeaderT">
											<textarea rows="5" class="col-xs-12 col-sm-12" style="resize:none"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>

 */




			                
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
			            }
					}) 
				});


				$("#InputTrkNo").focus();
				$("#InputTrkNo").keydown(function (key) {
				    if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				        var trkNo = $("#InputTrkNo").val();
				
				        if(trkNo==""){
				            return false;
				        }
				
				        $.ajax({url: "firstin_m.php",
				               type: "post",
				               data:{actWork:'firstin',TrkNo:$("#InputTrkNo").val()} ,
				         }).done(function(data) {
				             var obj = JSON.parse(data);
				             var audio = new Audio();
				
				             if(obj.Status == "duple"){
				                 audio.src = '/js/beep11.wav';
				                 audio.play();
				             }
				             if(obj.Status == "stop"){
				                 audio.src = '/js/stop.mp3';
				                 audio.play();
				             }
				
				             if(obj.Status == "pass"){
				                 audio.src = '/js/pass.mp3';
				                 audio.play();
				             }
				
				             if(obj.cnt != ""){
				                      $("#regCNT").text(obj.cnt);
				             }
				
				             $("#ChkMsg").attr('value',obj.Msg);
				            $("#InputTrkNo").attr('value','');
				         });
				    }
				});
				
				
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
