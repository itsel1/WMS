<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<meta charset="UTF-8">
<title>예치금 사용 내역</title>

<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
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
	</head> 
	<title>반품관리</title>
	
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">예치금 사용 내역</strong>
						</div>
		        	</div>
		      	</div>
		    </div>
	    </div>
		<form name="returnForm" id="returnForm" method="post">
		<input type="hidden" id="_method" name="_method" value="delete"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="container">
		       <div class="page-content noneArea">
					<div id="inner-content-side" >
						<br/><br/>
						<div style="border:1px solid #B4B4DC; border-radius:10px; width:20%; padding:20px; margin-left:10px;">
							<h4 style="font-weight:bold;">예치금 잔액</h4>
							<div style="text-align:right;">
								<font style="color:#FF8200; font-size:18px; font-weight:bold;">
									<fmt:formatNumber type="number" maxFractionDigits="3" value="${depoistPrice.DEPOSIT_COST}"/>
								</font>&nbsp;원
							</div>
						</div>
						<br/>
						<div class="row" style="margin-left:8px; margin-bottom:10px; width:100%;">
							<div class="col-xs-6" style="padding-top:15px;">
								<font style="color:#181818;">예치금 입금은 관리자에게 <b style="font-weight:bold; color:#3296FF; cursor:pointer;">문의</b>하시기 바랍니다</font>
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
						<div style="width:100%;">
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
					</div>
				</div>
			</div>
		</form>
		<br/>
	
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
			$("#returnForm").attr('action', '/return/depositList');
			$("#returnForm").attr('method', 'get');
			$("#returnForm").submit();
		});

	</script>


</body>
</html>