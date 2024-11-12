<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<style type="text/css">
		.tableFixHead {
			height: 250px;
			overflow: auto;
		}
		
		.fixed-table {
			width: 100%;
			background-color: rgba(242, 242, 243, 0.1);
		}
		
		.fixed-table thead th {
			padding-top: 1px;
			font-weight: bold;
			color: #fff;
			background-color: #428BCA;
			border-bottom: 2px solid rgba(0, 0, 0, 0.12);
			z-index: 100;
			vertical-align: center;
			height: 40px;
		}
		
		.fixed-table tbody td {
			text-align: center;
			vertical-align: center;
			border-bottom: 1px solid rgba(0, 0, 0, 0.12);
			height: 35px;
		}
		
		.header {
			text-align: center;
			position: sticky;
			top: 0;
		}
		
		.fixed-table tbody tr:hover {
			background-color: rgba(242, 242, 243, 0.8);
		}
		
		.tableFixHead::-webkit-scrollbar {
			width: 8px;
		}
		
		.tableFixHead::-webkit-scrollbar-thumb {
			background-color: #428BCA;
			border-radius: 5px;
			background-clip: padding-box;
			border: 1px solid transparent;
		}
		
		.tableFixHead::-webkit-scrollbar-track {
			background-color: rgba(141, 141, 191, 0.36);
			box-shadow: inset 0px 0px 5px white;
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
	<title>MAWB List</title>
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
							<li class="active">Type86 Apply</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<br/>
					<div class="page-content">
						<form id="searchForm" name="searchForm">
							<div class="page-header">
								<div>
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px" id="searchType" name="searchType">
													<option <c:if test="${searchType eq '0'}"> selected </c:if>value="0">::전체::</option>
													<option <c:if test="${searchType eq '1'}"> selected </c:if>value="1">USER ID</option>
													<option <c:if test="${searchType eq '2'}"> selected </c:if>value="2">주문번호</option>
													<option <c:if test="${searchType eq '3'}"> selected </c:if>value="3">Hawb No</option>
												</select>
												<input type="text" class="form-control" name="keywords" value="${searchKeywords}" style="width:100%; max-width: 300px"/>
												<button id="srchKeyword" class="btn btn-default no-border btn-sm">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
											</span>
										</div>
									</div>
									<br/>
									<div class="row">
										<div class="col-xs-12">
											<input type="button" class="btn btn-primary btn-sm" onclick="fn_createLabel()" value="Type86 라벨 생성">
											<input type="button" class="btn btn-primary btn-sm" onclick="fn_createParcll()" value="PARCLL 라벨 생성">
										</div>
									</div>
									<div class="tableFixHead" style="margin-top:10px;">
										<table class="fixed-table">
											<thead>
												<tr>
													<th class="header" style="width:5%; border-right:1px solid #fff; border-left:1px solid rgba(0, 0, 0, 0.12);">
														<label class="pos-rel">
																<input type="checkbox" class="ace allcheck" /> <span class="lbl"></span>
														</label>
													</th>
													<th class="header" style="width:5%; border-right:1px solid #fff;">NO</th>
													<th class="header" style="width:12%; border-right:1px solid #fff;">USER ID</th>
													<th class="header" style="width:12%; border-right:1px solid #fff;">운송장번호</th>
													<th class="header" style="width:12%; border-right:1px solid #fff;">주문번호</th>
													<th class="header" style="width:12%; border-right:1px solid #fff;">수취인</th>
													<th class="header" style="width:15%; border-right:1px solid #fff;">수취인 주소</th>
													<th class="header" style="width:12%; border-right:1px solid #fff;">수취인 Tel</th>
													<th class="header" style="width:5%; border-right:1px solid #fff;">Box Cnt</th>
													<th class="header" style="width:5%; border-right:1px solid #fff;">WTA</th>
													<th class="header" style="width:5%; border-right:1px solid #fff;">WTC</th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
													<c:when test="${!empty orderList}">
														<c:forEach items="${orderList}" var="orderList" varStatus="status">
															<tr>
																<td style="width:5%; border-right:1px solid rgba(0, 0, 0, 0.12); border-left:1px solid rgba(0, 0, 0, 0.12);">
																	<label class="pos-rel">
																		<input type="checkbox" class="ace" value="${orderList.nno}"/>
																		<span class="lbl"></span>
																	</label>
																</td>
																<td style="width:5%; border-right:1px solid rgba(0, 0, 0, 0.12);">${status.count}</td>
																<td style="width:12%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.userId}</td>
																<td style="width:12%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.hawbNo}</td>
																<td style="width:12%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.orderNo}</td>
																<td style="width:12%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.cneeName}</td>
																<td style="width:15%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.cneeAddr}</td>
																<td style="width:12%; border-right:1px solid rgba(0, 0, 0, 0.12);">${orderList.cneeTel}</td>
																<td style="width:5%; border-right:1px solid rgba(0, 0, 0, 0.12); text-align:right; padding-right:5px;">${orderList.boxCnt}</td>
																<td style="width:5%; border-right:1px solid rgba(0, 0, 0, 0.12); text-align:right; padding-right:5px;">${orderList.wta}</td>
																<td style="width:5%; border-right:1px solid rgba(0, 0, 0, 0.12); text-align:right; padding-right:5px;">${orderList.wtc}</td>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td class="center" colspan="11">조회 결과가 없습니다</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</form>
						<!-- 운송장 출력 -->
						<div>
							<form id="printForm" name="printForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12">
										<button class="btn btn-primary btn-sm" id="printCheck" name="printCheck">
											운송장 출력
											<i class="ace-icon fa fa-print align-top bigger-125 icon-on-right">
											</i>
										</button>
										<span style="vertical-align:bottom;margin-left:4px;color:red;">새로 생성된 라벨로 재 출력 해주시기 바랍니다</span>
									</div>
									<div class="col-xs-12" style="margin-top:8px;" id="printCheckContainer">
										<table id="printHawbTable" class="table table-bordered table-hover containerTable">
											<thead>
												<tr>
													<th class="center">
														<label class="pos-rel">
																<input type="checkbox" class="ace"/> <span class="lbl"></span>
														</label>
													</th>
													<th class="center colorBlack">
														HAWB NO
													</th>
													<th class="center colorBlack">
														Agency BL
													</th>
													<th class="center colorBlack">
														운송사
													</th>
													<th class="center colorBlack">
														USER ID
													</th>
													<th class="center colorBlack">
														Company
													</th>
													<th class="center colorBlack">
														수취인
													</th>
													<th class="center colorBlack">
														BOX
													</th>
													<th class="center colorBlack">
														W/T(A)
													</th>
													<th class="center colorBlack">
														W/T(C)
													</th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
													<c:when test="${!empty hawbList}">
														<c:forEach items="${hawbList}" var="hawbList" varStatus="status">
															<tr>
																<td class="center" style="word-break:break-all;vertical-align: middle;">
																	<label class="pos-rel">
																		<input type="checkbox" class="ace" id="check${status.count}" name="checkNno" value="${hawbList.nno}"/>
																		<span class="lbl"></span>
																	</label>
																</td>
																<td class="center">
																	${hawbList.hawbNo}
																</td>
																<td class="center">
																	${hawbList.agencyBl}
																</td>
																<td class="center">
																	${hawbList.transName}
																</td>
																<td class="center">
																	${hawbList.userId}
																</td>
																<td class="center">
																	${hawbList.comEName}
																</td>
																<td class="center">
																	${hawbList.cneeName}
																</td>
																<td style="text-align:right;">
																	${hawbList.boxCnt}
																</td>
																<td style="text-align:right;">
																	${hawbList.wta}
																</td>
																<td style="text-align:right;">
																	${hawbList.wtc}
																</td>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td colspan="9" class="center">
																조회 결과가 없습니다
															</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!--[if !IE]> -->
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
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thType").toggleClass('active');
				})

				var table = $(".fixed-table");
				$("input[type='checkbox']", table).on('change', function() {
					var check = $(this).prop("checked");

					if ($(this).hasClass("allcheck")) {
						$("input[type='checkbox']", table).prop("checked", check);
					} else {
						var all = $("input[type='checkbox']", table).not(".allcheck").length;
						var ckLen = $("input[type='checkbox']:checked", table).not(".allcheck").length;

						if (all == ckLen) {
							all.prop("checked", true);
						} else {
							all.prop("checked", false);
						}
					}
				})

			})
			
			var myTable = 
					$('#printHawbTable')
					.DataTable( {
				"dom" : "t",
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
		        "autoWidth": false,
		        "scrollY" : 250,
				select: {
					style: 'multi',
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

			var printCheckTable = $("#printHawbTable");
			
			$('th input[type=checkbox], td input[type=checkbox]', printCheckTable).prop('checked', false);
			
			//select/deselect all rows according to table header checkbox
			$('#printHawbTable > thead > tr > th input[type=checkbox], #printHawbTable_wrapper input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$('#printHawbTable').find('tbody > tr').each(function(){
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

			function fn_createParcll() {
				var targets = new Array();
				$(".fixed-table").find('tbody > tr > td input[type=checkbox]').each(function() {
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if (td_checked) {
						targets.push($(this).val());
					}
				})

				if (targets == "") {
					alert("선택된 데이터가 없습니다.");
					return false;
				} else {
					$.ajax({
						url : '/mngr/rls/sendParcllApi',
						type : 'post',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {"targets" : targets},
						success : function(result) {
							if (result == 'S') {
								location.reload();	
							} else {
								alert("라벨 생성 중 오류가 발생 하였습니다.");
							}
						},
						error : function(xhr, status) {
							alert("라벨 생성에 실패 하였습니다.");
						}
					})
				}
			}
					
			
			function fn_createLabel() {
				var targets = new Array();
				$(".fixed-table").find('tbody > tr > td input[type=checkbox]').each(function() {
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if (td_checked) {
						targets.push($(this).val());
					}
				})
				LoadingWithMask();
				if (targets == "") {
					alert("선택된 데이터가 없습니다.");
					return false;
				} else {
					$.ajax({
						url : '/mngr/rls/sendType86Api',
						type : 'post',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {"targets" : targets},
						success : function(result) {
							if (result.STATUS == 'SUCCESS') {
								location.reload();		
							} else {
								alert("라벨 생성 중 오류가 발생 하였습니다.");
							}
						},
						error : function(xhr, status) {
							alert("라벨 생성에 실패 하였습니다.");
						}
					})
				}
			}

			$("#printCheck").on('click', function(e) {
				var targets = new Array();
				$("#printHawbTable").find('tbody > tr > td input[type=checkbox]').each(function() {
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if (td_checked) {
						targets.push($(this).val());
					}
				})
				if (targets == "") {
					alert("출력할 운송장이 없습니다.");
					return false;
				} else {
					window.open("/comn/printHawb?targetInfo="+targets+"&formType=","popForm","_blank");
				}
			})
			
			function LoadingWithMask() {
               var maskHeight = $(document).height();
               var maskWidth = window.document.body.clientWidth;

               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
                 
                //화면에 레이어 추가
                $('body')
                    .append(mask)
                    
                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
                $('#mask').css({
                        'width' : maskWidth
                        ,'height': maskHeight
                        ,'opacity' :'0.3'
                });
              
                //마스크 표시
				$('#mask').show();  
                //로딩중 이미지 표시
			}

		</script>
		<!-- script addon end -->
		
	</body>
</html>
