<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  .addBtn {
	  	background: #3296FF;
	   	height: 35px;
	   	width: 60px;
	   	color: white;
	   	border: none;
	  }
	  
	  .addBtn:hover {
	  	background: #50B4FF;
	  }
	  
	  .delBtn {
	  	background: white;
	  	height: 35px;
	   	width: 60px;
	   	border: 1px solid #3296FF;
	  }
	  
	  .delBtn:Hover {
	  	background: #B8D7FF;
	  	color: white;
	  	border: none;
	  }
	  
	  .modal.modal-center {
		  text-align: center;
		}
		
		#addCost:focus {
			border: 1px solid #3296FF;
		}
		
		#etcDetail:focus {
			border: 1px solid #3296FF;
		}
	
	@media screen and (min-width: 768px) { 
	  .modal.modal-center:before {
	    display: inline-block;
	    vertical-align: middle;
	    content: " ";
	    height: 100%;
	  }
	}
	
	.modal-dialog.modal-center {
	  display: inline-block;
	  text-align: left;
	  vertical-align: middle; 
	 }
	 
	 #addModal {
	 	width: 20%;
	 }
	 
	 #delModal {
		width: 20%; 
	}
	 
	 .modal-footer {
	 	background:white;
	 }
  
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
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
							<li class="active">예치금 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								${userId} 예치금 내역
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="search-button" id="search-botton">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<br/><br/>
								<div style="border:1px solid #dcdcdc; border-radius:10px; width:20%; padding:20px; margin-left:10px;">
									<h4 style="font-weight:bold;">현재 예치금</h4>
									<div style="text-align:right;">
										<font style="color:#FF8200; font-size:18px; font-weight:bold;">
											<fmt:formatNumber type="number" maxFractionDigits="3" value="${depoistPrice.DEPOSIT_COST}"/>
										</font>&nbsp;원
									</div>
								</div>
								<br/><br/>
								<div class="row" style="margin-left:8px; margin-bottom:10px; width:80%;">
									<div class="col-xs-6">
										<input type="button" class="addBtn" onclick="fn_addPrice()" value="충전"/>
										<input type="button" class="delBtn" onclick="fn_delPrice()" value="차감"/>
									</div>
									<div class="col-xs-6" style="text-align:right;">
										<select style="height:34px;" name="calculation">
											<option <c:if test="${params.calculation == 'All'}"> selected</c:if> value="All">::전체::</option>
											<option <c:if test="${params.calculation == 'A'}"> selected</c:if> value="A">충전</option>
											<option <c:if test="${params.calculation == 'B'}"> selected</c:if> value="B">사용</option>
										</select>
										<input type="text" style="width:120px; text-align:center;" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}" /> ~ 
										<input type="text" style="width:120px; text-align:center;" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}" />
										<input type="button" style="margin-left:2px;"class="addBtn" id="searchBtn" value="조회"/>
									</div>
								</div>
								<div style="width:80%;">
									<div style="border-radius:10px; border:1px solid #dcdcdc; width:100%; padding:10px; margin-left:10px;">
										<table style="width:100%;">
											<thead style="border-bottom:2px double #ccc;">
												<tr>
													<th style="width:5%; height:50px; text-align:center; border-right:1px dotted #ccc;">No</th>
													<th style="width:15%; text-align:center; border-right:1px dotted #ccc;">일시</th>
													<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">구분</th>
													<th style="width:15%; text-align:center; border-right:1px dotted #ccc;">원운송장번호</th>
													<th style="width:35%; text-align:center; border-right:1px dotted #ccc;">내용</th>
													<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">금액</th>
													<th style="width:10%; text-align:center;">잔액</th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
													<c:when test="${!empty depositInfo}">
														<c:forEach items="${depositInfo}" var="list" varStatus="status">
															<tr>
																<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${status.count}</td>
																<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${fn:split(list.W_DATE,'.')[0]}</td>
																<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
																	<c:if test="${list.CALCULATION eq 'A'}">
																		충전
																	</c:if>
																	<c:if test="${list.CALCULATION eq 'B'}">
																		사용
																	</c:if>
																</td>
																<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
																	<c:if test="${list.CALCULATION eq 'A' || list.CODE eq 'D001'}">
																		-
																	</c:if>
																	<c:if test="${list.CALCULATION eq 'B'}">
																		${list.KOBL_NO}
																	</c:if>
																</td>
																<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
																	<c:if test="${empty list.REMARK}">
																		${list.CODE_DIV}
																	</c:if>
																	<c:if test="${!empty list.REMARK}">
																		${list.CODE_DIV} (${list.REMARK})
																	</c:if>
																</td>
																<td style="text-align:right; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc; padding-right:10px;">
																	<c:if test="${list.CALCULATION eq 'A'}">
																		<label style="color:#5050FF; font-weight:bold;">+&nbsp;<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.COST}"/></label>
																	</c:if>
																	<c:if test="${list.CALCULATION eq 'B'}">
																		<label style="color:#FF5675; font-weight:bold; font-weight:bold;">-&nbsp;<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.COST}"/></label>
																	</c:if>
																</td>
																<td style="text-align:right; height:40px; border-top:1px solid #ccc; padding-right:10px;">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${list.COST_NOW}"/>
																</td>
															</tr>
														</c:forEach>
													</c:when>
												<c:otherwise>
													<tr>
														<td style="text-align:center; padding-top:10px;" colspan="7">
															예치금 사용 내역이 없습니다
														</td>
													</tr>
												</c:otherwise>	
												</c:choose>
											</tbody>
										</table>
										<input type="hidden" name="userId" id="userId" value="${userId}"/>
									</div>
									<br/>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
								
								<!-- 예치금 충전 modal -->
								<div class="modal modal-center fade" id="myModal" role="dialog">
									<div class="modal-dialog modal-center" id="addModal">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">예치금 충전</h4>
											</div>
											<div class="modal-body">
												<table style="width:100%; height:100%;">
													<tr>
														<td style="width:10%;">금액</td>
														<td class="center" style="width:90%;"><input type="number" name="addCost" id="addCost" style="width:100%; height:100%;" value=""/></td>
													</tr>
												</table>
											</div>
											<div class="modal-footer">
												<button id="modalSubmit" type="button" class="addBtn">저장</button>
												<button type="button" class="delBtn" data-dismiss="modal">취소</button>
											</div>
										</div>
									</div>
								</div>
						
								<!-- 예치금 차감 modal -->
								<div class="modal modal-center fade" id="myModal2" role="dialog">
									<div class="modal-dialog modal-center" id="delModal">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">예치금 차감</h4>
											</div>
											<div class="modal-body">
												<table style="width:100%; height:100%; margin:0 auto;">
													<tr>
														<th style="padding-left:10px;">구분</th>
														<td style="height:34px;">
															단순감액
														</td>
													</tr>
													<tr>
														<th style="padding-left:10px;">상세 내용</th>
														<td>
															<input type="text" style="width:100%;" name="etcDetail" id="etcDetail"/>
														</td>
													</tr>
													<tr>
														<th style="padding-left:10px;">차감 금액</th>
														<td>
															<input type="number" name="delCost" id="delCost" style="width:100%;" value=""/>
														</td>
													</tr>
													<tr>
														<td colspan="2"  style="height:34px; text-align:right; padding-top:10px;">
															<input type="button" onclick="fn_toOrderList('${userId}')" value="반품 추가비용 청구" class="btn btn-sm btn-primary btn-white"/>
														</td>
													</tr>
												</table>
											</div>
											<div class="modal-footer">
												<button id="modalDelSubmit" type="button" class="addBtn">저장</button>
												<button type="button" class="delBtn" data-dismiss="modal">취소</button>
											</div>
										</div>
									</div>
								</div>
								<input type="hidden" name="depositPrice" id="depositPrice" value=""/>
								<input type="hidden" name="depositCode" id="depositCode" value="" />	
								<input type="hidden" name="depositRemark" id="depositRemark" value="" />				
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
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		
		
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thTwo-deposit").toggleClass('active'); 


				});
				
			});
			
			$('#fromDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yymmdd',
			});

			$('#toDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yymmdd',
			});

			$("#searchBtn").click(function() {
				$("#search-botton").attr('action', '/mngr/aplctList/depositDetail/'+$("#userId").val());
				$("#search-botton").attr('method', 'get');
				$("#search-botton").submit();
			});

			function fn_addPrice() {
				$("#myModal").modal();
			};

			$("#modalSubmit").click(function() {
				var value = document.getElementById('addCost').value;
				if (value.length < 1) {
					alert("충전 금액이 없습니다.");
					return false;
				}

				var data = {depositPrice: $("#addCost").val(), depositCode: 'A001', userId: $("#userId").val()};
				$.ajax({
					url : '/mngr/aplctList/deposit/add',
					type : 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : data,
					success : function(result) {
						if(result == 'S') {
							location.reload();
						} else {
							alert("예치금 충전에 실패했습니다.");
							location.reload();
						}
					},
					erorr : function(xhr, status) {
						alert(xhr + " : " + status);
					}
				});
			});
			
			function fn_delPrice() {
				$("#myModal2").modal();
			};

			$("#modalDelSubmit").click(function() {
				var value = document.getElementById('etcDetail').value;
				if (value.length < 1) {
					alert("상세 내용은 필수입니다.");
					return false;
				} 

				var value2 = document.getElementById('delCost').value;
				if (value2.length < 1) {
					alert('차감 금액을 입력해주세요.');
					return false;
				}

				var data = {depositPrice: $("#delCost").val(), depositCode: 'D001', userId: $("#userId").val(), remark: $("#etcDetail").val()};
				$.ajax({
					url : '/mngr/aplctList/deposit/del',
					type : 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : data,
					success : function(result) {
						if(result == 'S') {
							location.reload();
						} else {
							alert("예치금 차감에 실패했습니다.");
							location.reload();
						}
					},
					erorr : function(xhr, status) {
						alert(xhr + " : " + status);
					}
				});
			});

			function fn_toOrderList(userId) {
				 window.open('/mngr/order/return/list?option=sellerId&select=selectAll&keywords='+userId);
			}

		</script>
		<!-- script addon end -->
	</body>
</html>
