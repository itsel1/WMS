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
							<li class="active">ETC 추가</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									ETC 추가
								</h1>
							</div>
							<form name="uploadAramexExcel" id="cond_form" enctype="multipart/form-data">
								<table class="table table-bordered" style="width:70%" >
									<thead>
									<tr>
										<th class="center colorBlack">Date</th>
										<td style="padding:0px;width:120px;">
											<input style="width:100%"  type="text" name="fromDate" id="fromDate" value="${parameterInfo.fromDate}" autocomplete="off">
										</td>
										<th>~</th>
										<td style="padding:0px;width:120px;">
											<input style="width:100%"  type="text" name="toDate" id="toDate" value="${parameterInfo.toDate}" autocomplete="off">
										</td>
										<th class="center colorBlack">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<th class="center colorBlack">HawbNo</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="hawbNo" type="text" value="${parameterInfo.hawbNo}">
										</td>
										<th class="center colorBlack" style="width:80px;">구분</th>
										<td style="padding:0px;vertical-align: middle">
											<select id='etcType' name='etcType' style="width:100%">
												<option <c:if test="${parameterInfo.useYn == ''}"> selected</c:if> value=''>
													::전체::
												</option>
												<c:forEach items="${etcTypeList}" var="etcTypeList" varStatus="status">
													<option value='${etcTypeList.code}'
														<c:if test="${parameterInfo.etcType == etcTypeList.code}">selected</c:if>>
														${etcTypeList.name}
													</option>
												</c:forEach>
											</select>
										<td style="padding:0px;vertical-align: middle;text-align: center">
											<input type="button" id="btn_search" value="조회">
										</td>
									</tr>
									</thead>
								</table>
								</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="col-xs-12 col-md-offset-9 col-md-3" style="text-align: right;">
										<button type="button" class="btn btn-white btn-inverse btn-xs" name="excelDown" id="excelDown">Excel Form 다운로드</button>
										<button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_popupEtc('')">등록하기</button>
										<button type="button" class="btn btn-white btn-inverse btn-xs" name="excelUp" id="excelUp">Excel로 등록하기</button>
									</div>
									<div class="hidden">
										<input type="file" id="excelFile" name="excelFile"
										style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
										accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									</div>
								</form>
								<br>
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack" style="width:60px;">
													No.
												</th>
												<th class="center colorBlack" style="width:160px;">
													USER_ID
												</th>
												<th class="center colorBlack" style="width:160px;">
													HAWB NO
												</th>
												<th class="center colorBlack" style="width:160px;">
													일시
												</th>
												<th class="center colorBlack" style="width:160px;">
													구분
												</th>
												<th class="center colorBlack" style="width:160px;">
													금액
												</th>
												<th class="center colorBlack" style="width:160px;">
													화폐단위
												</th>
												<th class="center colorBlack">
													비고
												</th>
												<th class="center colorBlack">
													등록ID
												</th>
												<th class="center colorBlack">
													등록일
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="list" varStatus="status">
											<tr>
											<td class="center colorBlack">
													${list.num}
												</td>
												<td class="center colorBlack">
													${list.userId}
												</td>
												<td class="center colorBlack">
													<c:if test="${!empty list.hawbNo}">
														${list.hawbNo}
													</c:if>
													<c:if test="${empty list.hawbNo}">
														기타 출고
													</c:if>
												</td>
												<td class="center colorBlack">
													${list.etcDate}
												</td>
												<td class="center colorBlack">
													${list.etcName}
												</td>
												<td class="center colorBlack">
													<a href="#" onclick="javascript:fn_popupEtc('${list.idx}','${list.userId}')">${list.etcValue}</a>
												</td>
												<td class="center colorBlack">
													${list.etcCurrency}
												</td>
												<td class="colorBlack" style="text-align:left">
													${list.remark}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
													${list.wUserId}
												</td>
												<td class="colorBlack" style="text-align:center;width:100px;">
													${list.wDate}
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
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>

		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
						
					$("#invoicethMenu").toggleClass('open');
					$("#invoiceOne").toggleClass('active');

					$("#btn_search").on('click',function(e){
						$("#cond_form").submit();
					})
					
					$("#etcType").on('change',function(e){
						$("#cond_form").submit();
					})

				})

				$('#fromDate').datepicker({
					autoclose:true,
					todayHightlight:true,
					format: 'yyyymm',
				});

				$('#toDate').datepicker({
					autoclose:true,
					todayHightlight:true,
					format: 'yyyymm',
				});
			})
			
			function fn_popupEtc(idx,userId){

				var _width = '820';
			    var _height = '380';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			     
				window.open("/mngr/invoice/popupEtc?idx="+idx+"&userId="+userId,"ETC",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}

			$('#excelUp').on('click', function(e) {
				$("#excelFile").trigger("click");
			}); 

			$('#excelDown').on('click', function(e) {
				window.open("/mngr/invoice/popupEtcExcelDown")
			});

			$("#excelFile").on('change',function(e){
				var datass = new FormData();
				datass.append("file", $("#excelFile")[0].files[0]);
				//LoadingWithMask();
				
				$.ajax({
					url:'/mngr/invoice/popupEtcInExcel/'+$("#userId").val(),
					type: 'POST',
					data : datass,
					processData: false,
		            contentType: false,
					beforeSend : function(xhr)
					{ 
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						alert("적용 되었습니다.")
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            },
		            complete : function() {
		            	$('#mask').hide();
		            	$("#excelFile").val("");
		            }
				})
			})
		</script>
		<!-- script addon end -->

	</body>
</html>
