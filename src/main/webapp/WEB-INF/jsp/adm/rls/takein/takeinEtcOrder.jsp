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

#orderNo {
	cursor: pointer;
	font-weight: 600;
}

.stockOut {
	width: 80px;
	height: 30px;
	cursor: pointer;
	color: #fff;
	background-color: #527d96;
	border: none;
	box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
	transition: 0.3s;
	font-size: 14px;
}

.stockOut:hover {
	background-color: rgba(82, 125, 150, 0.9);
	box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
}

/*
.stockOutCl {
	width: 80px;
	height: 30px;
	cursor: pointer;
	color: #fff;
	background-color: #A52A2A;
	border: none;
	box-shadow: 0 4px 16px rgba(154, 40, 40, 0.1);
	transition: 0.3s;
	font-size: 14px;
}

.stockOutCl:hover {
	background-color: rgba(154, 40, 40, 0.9);
	box-shadow: 0 2px 4px rgba(154, 40, 40, 0.6);
}
*/
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
							<li class="active">기타 출고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									사입 기타 출고
								</h1>
							</div>
							<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
								<table class="table table-bordered" style="width:1500px;" >
									<thead>
									<tr>
										<th class="center colorBlack">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<th class="center colorBlack" style="width:150px;">OrderNo</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="orderNo" value="${parameterInfo.orderNo}">
										</td>
										<th class="center colorBlack" style="width:150px;">Trk Com</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="trkCom" value="${parameterInfo.trkCom}">
										</td>
										<th class="center colorBlack" style="width:150px;">Trk No</th>
										<td style="padding:0px;">
											<input style="width:85%" type="text" name="trkNo" value="${parameterInfo.trkNo}">
											<input type="submit" value="조회">
										</td>
									</tr>
									</thead>
								</table>
							</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div style="width:100%;">
										<div style="width:50%;float:left;text-align:left;">
											<input type="button" id="cancelRegist" class="btn btn-white btn-inverse btn-xs" value="등록 취소"/>
										</div>
										<div style="width:50%;float:right;text-align:right;">
											<input type="button" id="printCheck" class="btn btn-white btn-inverse btn-xs"  value="BlPrint"/>
											<input type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_popupTakeininEtcOrder()" value="등록하기">
										</div>
									</div>
									<!-- <div class="col-xs-12 col-md-12" style="text-align: left;">
									</div>
									<div class="col-xs-12 col-md-12" style="text-align: right;">
										<button type="button" class="btn btn-white btn-inverse btn-xs" id="btn_Delete" name="">삭제</button>
										<input type="button" id="printCheck" class="btn btn-white btn-inverse btn-xs"  value="BlPrint"/>
										<input type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_popupTakeininEtcOrder()" value="등록하기">
									</div> -->
								</form>
								<br>
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="chkAll"/>
														<span class="lbl"></span>
													</label>
												</th>
											    <th class="center colorBlack">
													No
												</th>
												<th class="center colorBlack">
													요청 일시
												</th>
												<th class="center colorBlack">
													USER ID
												</th>
												<th class="center colorBlack">
													ORDER NO
												</th>
												<th class="center colorBlack">
													배송사
												</th>
												<th class="center colorBlack">
													송장번호
												</th>
												<th class="center colorBlack">
													상품 종류
												</th>
												<th class="center colorBlack">
													상품 수량
												</th>
												<th class="center colorBlack">
													출고 수량
												</th>
												<th class="center colorBlack">
													비고
												</th>
												<th class="center colorBlack">
													기타 출고 비용
												</th>
												<th class="center colorBlack">
												        출고일
												</th>
												<th class="center colorBlack">
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="list" varStatus="status">
											<tr>
												<td style="text-align: center">
													<%-- <input type="checkbox" name="nno[]" value="${list.nno}"> --%>
													<c:if test="${!empty list.outDate}">
														<input type="checkbox" disabled>	
													</c:if>
													<c:if test="${empty list.outDate}">
														<input type="checkbox" name="nno[]" value="${list.nno}">
													</c:if>
												</td>
												<td class="center colorBlack" style="width:80px;">
													${status.count}
												</td>
												<td class="center colorBlack" style="width:120px;">
													${list.orderDate}
												</td>
												<td class="center colorBlack" style="width:120px;">
													${list.userId}
												</td>
												<td class="center colorBlack" style="width:130px;">
													<a id="orderNo" onclick="fn_popupTakeinUpEtcOrder('${list.nno}','${list.userId}')">${list.orderNo}</a>
												</td>
												<td class="center colorBlack" style="width:150px;">
													 ${list.trkCom}
												</td>
												<td style="width:200px;">
													 ${list.trkNo}
												</td>
												<td style="text-align: right;width:80px;">
													${list.itemDiv}
												</td>
												<td  style="text-align: right; width:80px;width:80px;">
													${list.requestCnt}
												</td>
												<td class="center colorBlack">
													${list.outCnt}
												</td>
												<td class="center colorBlack">
													${list.remark}
												</td>
												<td  class="right colorBlack"  style="text-align: right">
													${list.etcFee}
												</td>
												<td class="center colorBlack" >
													${list.outDate}
												</td>
												<td class="center colorBlack" style="width:80px;">
													<c:if test="${empty(list.outDate)}">
														<input type="button" value="출고작업" class="btn btn-primary btn-xs" onclick="fn_popupTakeinEtcStockOut('${list.nno}','${list.userId}')">
													</c:if>
													<c:if test="${!empty(list.outDate)}">
														<a style="cursor:pointer;" onclick="fn_cancelTakeinEtcStockOut('${list.nno}','${list.userId}')">출고 취소</a>
														<%-- <input type="button" value="입고작업" onclick="fn_popupTakeinEtcStockOut('${list.nno}','${list.userId}')"> --%>
													</c:if>
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
				$("#6thThr").toggleClass('active');

			})
			
			$("#chkAll").click(function() {
	            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
	                $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
	            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
	                $("input[name='nno[]'").prop("checked",false);
	            }
	        });

			$('#printCheck').on('click',function(e){

				var targets = new Array();
				
				 $("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});

					if(targets == ""){
						alert("출력할 운송장이 없습니다.");
						return false;
					}
					
					window.open("/comn/printHawb?targetInfo="+targets+"&formType=ETCS","popForm","_blank");
			})
			
			$("#cancelRegist").on('click', function() {
				var targets = new Array();
				$("input[name='nno[]']").each(function() {
					var checked = this.checked;
					if (checked) {
						targets.push($(this).val());
					}
				})

				if (targets == "") {
					alert("취소할 출고 건이 없습니다.");
					return false;
				} else {
					$.ajax({
						url : '/mngr/takein/cancelTakeinOrderRegist',
						type : 'post',
						data : {targets : targets},
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							if (data == "S") {
								location.reload();
							} else {
								alert("출고 취소 중 오류가 발생 하였습니다.");
							}
						},
						error : function(xhr, status) {
							alert("취소에 실패 하였습니다.");
						}
					})
				}
			})
			
			
			function fn_popupTakeinEtcStockOut(nno,userId){
				var _width = '1250';
			    var _height = '650';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			     
				window.open("/mngr/takein/popupTakeinEtcStockOut?nno="+nno+"&userId="+userId,"EtcStockOut",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}
	
			
			function fn_popupTakeinEtcStockOut(nno,userId){
				var _width = '1250';
			    var _height = '650';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			     
				window.open("/mngr/takein/popupTakeinEtcStockOut?nno="+nno+"&userId="+userId,"EtcStockOut",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}


			function fn_popupTakeininEtcOrder(nno,UserId){

				var _width = '720';
			    var _height = '600';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			     
				window.open("/mngr/takein/popupTakeinEtcOrder","PopupWin",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}


			function fn_popupTakeinUpEtcOrder(nno, userId) {

				var _width = '820';
			    var _height = '600';
			    var _left = Math.ceil(( window.screen.width - _width )/2);
			    var _top = 150;
			     
				window.open("/mngr/takein/popupTakeinEtcOrderUp?nno="+nno+"&userId="+userId,"EtcUp",'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}

			function fn_cancelTakeinEtcStockOut(nno, userId) {

				if (confirm("출고 취소 처리 하시겠습니까?")) {

					$.ajax({
						url : '/mngr/takein/cancelTakeinOrderStockOut',
						type : 'post',
						data : {nno: nno, userId: userId},
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success: function(data) {
							location.reload();
						},
						error : function(xhr, status) {
							console.log("FAIL");
						}
					})
					
				} else {
					return false;
				}
			}
		
		</script>
		
	</body>
</html>
