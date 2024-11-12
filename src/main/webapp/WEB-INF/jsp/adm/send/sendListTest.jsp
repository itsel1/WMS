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
	
	.bottom0 {
		margin-bottom:5px;
	}
	.containerTable {
		width: 100% !important;
	}
	table ,tr td{
		word-break:break-all;
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
	<title>발송 목록</title>
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
								발송 리스트
							</li>
							<li class="active">발송 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								발송 목록
							</h1>
						</div>
						<div id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="search-button" id="search-botton" method="get" action="">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-12">
											<div class="row">
												<div class="col-xs-8 col-sm-11 col-md-12">
													<div class="col-xs-8 col-sm-11 col-md-2" style="padding:0px 0px 0px 15px!important">
														<div class="input-group">
															<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
															</span>
															<div class="input-group input-daterange">
																<input type="text" class=" form-control" id="startDate" name="startDate" value="${search.startDate}" autocomplete="off"/>
																	<span class="input-group-addon">
																		to
																	</span>
																<input type="text" class=" form-control" id="endDate" name="endDate" value="${search.endDate}" autocomplete="off"/>
															</div>
															
														</div>
													</div>
													<div class="col-md-10" style="padding:0px!important">
														<span class="input-group col-md-12" style="display: inline-flex;">
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 60px; text-align: center;" value="USER ID" readonly="true"/>
															<input type="text" class="form-control" name="userId" id="userId" value="${search.userId}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 85px; text-align: center;" value="mawbNo" readonly="true"/>
															<input type="text" class="form-control" name="mawbNo" id="mawbNo" value="${search.mawbNo}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="hawbNo" readonly="true"/>
															<input type="text" class="form-control" name="hawbNo" id="hawbNo" value="${search.hawbNo}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="orderNo" readonly="true"/>
															<input type="text" class="form-control" name="orderNo" id="orderNo" value="${search.orderNo}" style="width:100%; max-width: 150px"/>
															<select id="transCode" name="transCode" style="height:34px;">
																<option value="">배송사 선택</option>
																<c:forEach items="${transCodeList}" var="transCodeList">
																	<option <c:if test="${search.transCode eq transCodeList.transCode}"> selected</c:if> value="${transCodeList.transCode}">${transCodeList.transCode}</option>
																</c:forEach>
															</select>
															<button type="button" onclick="fn_submit()" class="btn btn-default no-border btn-sm">
																<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
															</button>
															<button type="button" onclick="fn_excelDown()" class="btn btn-sm btn-primary btn-white" style="margin-left:10px;">
																검색 데이터 엑셀 다운
															</button>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
							<br/>
							<div id="table-contents">
								<table id="sendList-table" class="table table-bordered table-hover containerTable">
									<thead>
										<tr>
												<th class="center colorBlack" style="width:80px;">
												UserID
											</th>
											<th class="center colorBlack">
												도착국가
											</th>
												<th class="center colorBlack" style="width:100px;">
												배송사
											</th>
											<th class="center colorBlack" style="width:120px;">
												Mawb No
											</th>
											<th class="center colorBlack" style="width:130px;">
												Hawb No
											</th>
											<th class="center colorBlack" style="width:160px;">
												Order No
											</th>
												<th class="center colorBlack" style="width:120px;">
												수취인 이름
											</th>
											<th class="center colorBlack">
												수취인 전화번호
											</th>
											<th class="center colorBlack" style="width:90px;">
												우편주소
											</th>
											<th class="center colorBlack" style="width:200px;">
												대표 상품
											</th>
											<th class="center colorBlack">
												Total Value
											</th>
											<th class="center colorBlack">
												WTA
											</th>
											<th class="center colorBlack">
												WTC
											</th>
											<th class="center colorBlack" style="width:90px;">
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${sendList}" var="send" varStatus="status">
											<tr>
												<td class="center colorBlack">
													${send.userId }
												</td>
												<td class="center colorBlack">
													${send.dstnNation }
												</td>
												<td class="center colorBlack">
													${send.transName }
												</td>
												<td class="center colorBlack">
													${send.mawbNo }
												</td>
												<td class="center colorBlack">
													${send.hawbNo }
												</td>
												<td class="center colorBlack">
													${send.orderNo }
												</td>
												<td class="center colorBlack">
													${send.cneeName}<br/>${send.nativeCneeName}
												</td>
												<td class="center colorBlack">
													${send.cneeTel}
												</td>
												<td class="center colorBlack">
													${send.cneeZip}
												</td>
												<td class="center colorBlack">
													${send.mainItem}
												</td>
												<td class="center colorBlack">
													${send.totalValue}
												</td>
												<td class="center colorBlack">
													${send.wta}
												</td>
												<td class="center colorBlack">
													${send.wtc}
												</td>
												<td class="center colorBlack">
													<c:choose>
													    <c:when test="${send.transCode eq 'FED'}">
													      	<a style="cursor: pointer;"onclick="window.open('https://www.fedex.com/apps/fedextrack/index.html?tracknumbers=${send.hawbNo}&cntry_code=kr','배종조회','width=650,height=800')">[POD]</a>
													    </c:when>
													    <%-- <c:when test="${send.transCode eq 'SEK'}">
													      	<a style="cursor: pointer;"onclick="window.open('http://track.omniparcel.com/1489353-${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>
													    </c:when> --%>
													    <c:otherwise>
													      	<a style="cursor: pointer;"onclick="window.open('/comn/deliveryTrack/${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>        	
													    </c:otherwise>
													</c:choose>
												</td>
											</tr>	
									 	</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
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
					
				})

				$("#sendList-table").DataTable();
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
