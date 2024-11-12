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
.tableFixHead, .tableFixHead td {
  border-collapse: collapse;
  border: 1px solid #dcdcdc;
}

.tableFixHead th {
	  	font-family: 'Open Sans';
		text-align: center;
		padding: 5px 0;
		font-size: 14px;
		color: #000000 !important;
		font-weight: bold;
		background: linear-gradient(to top, #ddd, white);
	  	border-collapse: collapse;
	}

#paymentBtn {
	border: 1px solid #dcdcdc;
	background: white;
}

#paymentBtn:hover {
	border: 1px solid #FDCD8C;
	background: #FDCD8C;
	font-weight: bold;
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
	<title>WMS.ESHOP</title>
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
								Invoice
							</li>
							<li class="active">A/R Control</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								A/R Control
							</h1>
						</div>
						<form id="mainForm" action="">
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display:inline-flex; width:100%;">
										<select name="searchType" id="searchType" style="height:34px;">
											<option <c:if test="${searchType eq '0' }"> selected </c:if>value="0">USER ID</option>
											<option <c:if test="${searchType eq '1' }"> selected </c:if>value="1">COMPANY</option>
										</select>
										<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width:300px;">
										<button type="submit" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>	
							</div>
							<br/>
							<br/>
							<div class="inner-content-side">
								<div id="table-contents" class="table-contents" style="width:50%; float:left;">
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
										<thead>
											<tr>
												<th class="center colorBlack" style="width:120px;">USER ID</th>
												<th class="center colorBlack" style="width:200px;">Company</th>
												<th class="center colorBlack" style="width:140px;">Invoice AMT</th>
												<th class="center colorBlack" style="width:140px;">Received AMT</th>
												<th class="center colorBlakc" style="width:140px;">Balance AMT</th>
												<th style="width:90px;"></th>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${!empty ARControlList}">
													<c:forEach items="${ARControlList}" var="ARList" varStatus="status">
														<tr>
															<td class="center" style="word-break:break-all;">
																<input type="hidden" id="userId" name="userId" value="${ARList.userId}"/>
																<a style="font-weight:bold; cursor:pointer;" onclick="fn_popup('${ARList.userId}');">${ARList.userId}</a>
															</td>
															<td class="center" style="word-break:break-all;">
																${ARList.company}
															</td>
															<td style="word-break:break-all; text-align:right;">
																<fmt:formatNumber type="number" maxFractionDigits="3" value="${ARList.invAmt}"/>
															</td>
															<td style="word-break:break-all; text-align:right;">
																<c:if test="${empty ARList.receivedAmt}">
																-
																</c:if>
																<c:if test="${!empty ARList.receivedAmt}">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${ARList.receivedAmt}"/>
																</c:if>
															</td>
															<td style="word-break:break-all; text-align:right;">
																<c:if test="${empty ARList.balanceAmt}">
																-
																</c:if>
																<c:if test="${!empty ARList.balanceAmt}">
																	<fmt:formatNumber type="number" maxFractionDigits="3" value="${ARList.balanceAmt}"/>
																</c:if>
															</td>
															<td class="center" >
																<input type="button" value="Detail" class="paymentBtn" id="paymentBtn" onclick="javascript:fn_payment('${ARList.userId}'); return false;">
															</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="6" class="center colorBlack">조회 결과가 없습니다
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
								<div id="payment-contents" style="float:left; margin-left:25px; width:48%;">
									
								</div>
							</div>
						</form>
					</div>
				</div>
			</div><!-- /.main-content -->
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
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#invoicethMenu").toggleClass('open');
					$("#ARControl").toggleClass('active');
					
				})
			})
			
			$(".paymentBtn").on('click', function() {
				var index = $(".paymentBtn").index(this);

				$(".paymentBtn").css({
					"background": "white",
					"border": "1px solid #dcdcdc",
					"font-weight": "normal"
				})
				
				$(".paymentBtn").eq(index).css({
					"background": "#FDCD8C",
					"font-weight": "bold",
					"border": "1px solid #FDCD8C"
				})
			
			})
			
			function fn_payment(userId) {
				var html = "";
				
				$.ajax({
					url : '/mngr/invoice/payment',
					type : 'POST',
					data : {userId: userId},
					beforeSend : function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						console.log(data);
						
						html += '<table class="table table-bordered">';
						html += '<thead><tr><th colspan="5" class="center">'+userId+' 수금 내역</th></tr>';
						html += '<thead><tr><th class="center" style="width:140px;">Invoice Month</th>';
						html += '<th class="center" style="width:250px;">Invoice No</th>';
						html += '<th class="center" style="width:140px;">Invoice AMT</th>';
						html += '<th class="center" style="width:140px;">Received AMT</th>';
						html += '<th class="center" style="width:130px;">Balance AMT</th></tr></thead>';
						html += '<tbody>';
						for (var i = 0; i < data.invoiceDetail.length; i++) {
							html += '<tr><td class="center" style="width:140px;">'+data.invoiceDetail[i].invMonth+'</td>';
							html += '<td class="center" style="width:250px;">'+data.invoiceDetail[i].invoiceNo+'</td>';
							html += '<td style="text-align:right; width:140px;">'+data.invoiceDetail[i].invAmt.toLocaleString('ko-KR')+'</td>';
							html += '<td style="text-align:right; width:140px;">'+data.invoiceDetail[i].receivedAmt.toLocaleString('ko-KR')+'</td>';
							html += '<td style="text-align:right; width:130px;">'+data.invoiceDetail[i].balanceAmt.toLocaleString('ko-KR')+'</td>';
							html += '</tr>';
						}
						html += '</tbody>';
						
						$("#payment-contents").empty();
						$("#payment-contents").append(html);
					},
					error : function(request,status,error) {
						alert('데이터 조회에 실패 하였습니다');
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				})
			}

			function fn_popup(userId) {
				var _width = '1220';
			    var _height = '700';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
				window.open('/mngr/invoice/popupInvoice?userId='+userId,"Invoice Collect",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}
		
		
		</script>
		<!-- script addon end -->

	</body>
</html>
