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
	<title>사입</title>
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
								사입
							</li>
							<li class="active">상품 입고 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									상품 입고 관리
								</h1>
							</div>
							<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
								<table class="table table-bordered" style="width:1500px;" >
									<thead>
									<tr>
										
										<th class="center colorBlack" style="width:100px;">Takein Code</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="takeInCode" value="${parameterInfo.takeInCode}" >
										</td>
										<th class="center colorBlack">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<th class="center colorBlack" style="width:150px;">Customer Item Code</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="cusItemCode" value="${parameterInfo.cusItemCode}">
										</td>
										<th class="center colorBlack" style="width:150px;">상품명</th>
										<td style="padding:0px;">
											<input style="width:85%" type="text" name="itemDetail" value="${parameterInfo.itemDetail}">
											<input type="submit" value="조회">
										</td>
									</tr>
									</thead>
								</table>
							</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="col-xs-12 col-md-12" style="text-align: left;">
									</div>
									<div class="col-xs-12 col-md-12" style="text-align: right;">
										<!-- <button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_takeinInfo('')">등록하기</button> -->
										<!-- <button type="button" class="btn btn-white btn-inverse btn-xs" id="btn_Delete" name="">삭제</button> -->
									</div>
								</form>
								<br>
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack">
													TAKEIN CODE
												</th>
												<th class="center colorBlack">
													USER ID
												</th>
												<th class="center colorBlack">
													Customer Item Code
												</th>
												<th class="center colorBlack">
													상품명
												</th>
												<th class="center colorBlack">
													Rack
												</th>
												<th class="center colorBlack">
													수량단위
												</th>
												<th class="center colorBlack">
													입고수량
												</th>
												<th class="center colorBlack">
													출고수량
												</th>
												<th class="center colorBlack">
													재고수량
												</th>
												<th class="center colorBlack">
													유통기한
												</th>
												<th class="center colorBlack">
													인보이스 No
												</th>
												<th class="center colorBlack">
													공급처
												</th>
												<th class="center colorBlack">
													공급처 주소
												</th>
												<th class="center colorBlack">
													공급처 연락처
												</th>
												<th class="center colorBlack">
													검수일자
												</th>
												<th class="center colorBlack">
													검수자
												</th>
												<th class="center colorBlack">
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${takeinItemList}" var="takeinItemList" varStatus="status">
											<tr>
												<td class="center colorBlack">
													<a href="#" class="takeInfo" onclick="fn_takeinItemInfo('${takeinItemList.orgStation}','${takeinItemList.userId}','${takeinItemList.takeInCode}','${takeinItemList.groupIdx}')" style="font-weight:bold;">
														${takeinItemList.takeInCode}
													</a>
												</td>
												<td class="center colorBlack">
													${takeinItemList.userId}
												</td>
												<td class="center colorBlack">
													${takeinItemList.cusItemCode}
												</td>
												<td class="center colorBlack">
													${takeinItemList.itemDetail}
												</td>
												<td class="center colorBlack">
													 ${takeinItemList.rackCode}
												</td>
												<td class="center colorBlack">
													 ${takeinItemList.qtyUnit}
												</td>
												<td style="text-align: right;">
													<a href="#" style="font-weight:bold;" onclick="fn_takeinStockList('${takeinItemList.orgStation}','${takeinItemList.userId}','${takeinItemList.groupIdx}')">${takeinItemList.whInCnt}</a>
												</td>
												<td style="text-align: right;">
													${takeinItemList.whOutCnt}
												</td>
												<td  style="text-align: right; font-weight:bold;">
													${takeinItemList.whInCnt - takeinItemList.whOutCnt}
												</td>
												<td class="center colorBlack">
													${takeinItemList.mnDate}
												</td>
												<td class="center colorBlack">
													${takeinItemList.cusInvNo}
												</td>
												<td class="center colorBlack">
													${takeinItemList.cusSupplier}
												</td>
												<td class="center colorBlack">
													${takeinItemList.cusSupplierAddr}
												</td>
												<td class="center colorBlack">
													${takeinItemList.cusSupplierTel}
												</td>
												<td class="center colorBlack">
													${takeinItemList.whInDate}
												</td>
												<td class="center colorBlack">
													${takeinItemList.inSpector}
												</td>
												<td class="center colorBlack">
													<input type="button" onclick="fn_popupTakeinStock('${takeinItemList.groupIdx}')" value="재고출력">
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
									<form id="stockForm" action="">
									<input type="hidden" id = "orgStation" name="orgStation" value="">
									<input type="hidden" id = "groupIdx" name="groupIdx" value="">
									<input type="hidden" id = "userId" name="userId" value="">
									</form>
									<form id="mgrMsgForm" name="mgrMsgForm">
									</form>								
								</div>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
							</div>
					</div>
				</div>
			</div><!-- /.main-content -->
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

				$("#5thMenu").toggleClass('open');
				$("#5thTwo").toggleClass('active');

				 
				$("#btn_Delete").on('click',function(e){
					$.ajax({
						url:'/mngr/aramex/aramexDelete',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: '', 
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
				})
			})
			
			
			
			function fn_takeinItemInfo(orgStation,userId,takeInCode,groupIdx){
				window.open("/mngr/takein/popupTakeininfoUp?orgStation="+orgStation+"&userId="+userId+"&takeInCode="+takeInCode+"&groupIdx="+groupIdx,"PopupWin", "width=700,height=420");
			}
			
			

			function fn_takeinStockList(orgStation,userId,groupIdx){
				window.open("/mngr/takein/popupTakeinStockList?orgStation="+orgStation+"&userId="+userId+"&groupIdx="+groupIdx,"PopupWin", "width=1820,height=600");
			}
			
			function fn_popupTakeinStock(groupIdx){
				window.open("/mngr/takein/pdfTakeInPopup?groupIdx="+groupIdx,"PopupWin", "width=720,height=600");			
			}
		</script>
		
	</body>
</html>
