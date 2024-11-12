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
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfobject/2.1.1/pdfobject.js"></script>
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
	<title>검수 작업</title>
	<body class="no-skin" oncontextmenu="return false">
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
						<div class="col-xs-12 col-sm-12" id="inner-content-side" style="height:100%">
							<div class="row center">
								<div class="col-xs-12 col-md-12">
									<span 
									<c:choose>
										<c:when test="${stckInfos[0].whStatus eq 'WO'}">
											class="blue"
										</c:when>
										<c:when test="${stckInfos[0].whStatus eq 'WF'}">
											class="red"
										</c:when>
										<c:otherwise>
											class="light-orange"
										</c:otherwise>
									</c:choose>
									 style="font-size:45px">${stckInfos[0].whStatusName}<br/></span>
								</div>
								<span style="font-size:30px;">재고번호 : </span>
								<c:forEach items="${stckInfos}" var="stckInfos"><span style="font-size:25px;">
									${stckInfos.stockNo} </span>asdfasdf
									${stckInfos.groupIdx}
								</c:forEach><br>
								<span style="font-size:30px;">RACK :	</span><span style="font-size:25px;">${stckInfos[0].rackCode}</span><br/>
								<c:forEach items="${targetStockList}" var="stockInfosAdd" varStatus="stckStatus"> 
									<input type="hidden" id="groupIdx${stckStatus.count}" name="groupIdx" value="${stockInfosAdd.groupIdx}">
								</c:forEach>
									<input type="hidden" id="nno" name="nno" value="${stckInfos[0].nno}">
									<br>
								<button class="btn btn-primary" type="button" id="stockPrint" name="stockPrint">재고번호 출력</button>
								<c:if test="${chkCount eq 'OK'}">
									<button class="btn btn-primary" type="button" id="stockOut" name="stockOut">재고상품 출고</button>
								</c:if>
								<button class="btn btn-primary" type="button" id="stockContinue" name="stockContinue">검수작업 계속하기</button>
							</div>
							
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
			jQuery(function($) {
				$(document).ready(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFor").toggleClass('active');
					$("#stockPrint").trigger("click");
					
				});
			})
			$("#stockPrint").on('click',function(e){
				var groupIdx = new Array();
				$("[name='groupIdx']").each(function(e){
					groupIdx.push($(this).val());
				})
				window.open("pdfPopup?nno="+$("#nno").val()+"&groupIdx="+groupIdx,"_blank","toolbar=yes,resizable=no,directories=no,width=480, height=360");
			})
			$("#stockOut").on('click',function(e){
				location.href="/mngr/rls/inspcStockOut?nno="+$("#nno").val();
			})
			
			$("#stockContinue").on('click',function(e){
				location.href="/mngr/order/orderInspc";
			})
			function noEvent() {
				if (event.keyCode == 116) {
					event.keyCode= 2;
					return false;
				}else if(event.ctrlKey && (event.keyCode==78 || event.keyCode == 82))
				{
					return false;
				}
			}
			document.onkeydown = noEvent;


		</script>
		<!-- script addon end -->
		
	</body>
</html>
