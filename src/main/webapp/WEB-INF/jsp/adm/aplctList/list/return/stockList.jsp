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
		
		.clickBtn {
			margin-bottom:2px;
			width:100%;
		}

	
		.modal.modal-center {
		  text-align: center;
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
		 	width: 700px;
		 }
		  .modal-footer {
	 	background:white;
	 }
  
  		@import url(https://fonts.googleapis.com/css?family=Varela+Round);

		.slides {
		    padding: 0;
		    width: 609px;
		    height: 480px;
		    display: block;
		    margin: 0 auto;
		    position: relative;
		}
		
		.slides * {
		    user-select: none;
		    -ms-user-select: none;
		    -moz-user-select: none;
		    -khtml-user-select: none;
		    -webkit-user-select: none;
		    -webkit-touch-callout: none;
		}
		
		.slides input { display: none; }
		
		.slide-container { display: block; }
		
		.slide {
		    top: 0;
		    opacity: 0;
		    width: 609px;
		    height: 480px;
		    display: block;
		    position: absolute;
		
		    transform: scale(0);
		
		    transition: all .7s ease-in-out;
		}
		
		.slide img {
		    width: 100%;
		    height: 100%;
		}
		
		.nav label {
		    width: 200px;
		    height: 100%;
		    display: none;
		    position: absolute;
		
			  opacity: 0;
		    z-index: 9;
		    cursor: pointer;
		
		    transition: opacity .2s;
		
		    color: #FFF;
		    font-size: 156pt;
		    text-align: center;
		    line-height: 380px;
		    font-family: "Varela Round", sans-serif;
		    background-color: rgba(255, 255, 255, .3);
		    text-shadow: 0px 0px 15px rgb(119, 119, 119);
		}
		
		.slide:hover + .nav label { opacity: 0.5; }
		
		.nav label:hover { opacity: 1; }
		
		.nav .next { right: 0; }
		
		input:checked + .slide-container  .slide {
		    opacity: 1;
		
		    transform: scale(1);
		
		    transition: opacity 1s ease-in-out;
		}
		
		input:checked + .slide-container .nav label { display: block; }
		
		.nav-dots {
			width: 100%;
			bottom: 9px;
			height: 11px;
			display: block;
			position: absolute;
			text-align: center;
		}
		
		.nav-dots .nav-dot {
			top: -5px;
			width: 11px;
			height: 11px;
			margin: 0 4px;
			position: relative;
			border-radius: 100%;
			display: inline-block;
			background: #c0beb9;
		}
		
		.nav-dots .nav-dot:hover {
			cursor: pointer;
			background: #c0beb9;
		}
		
		input#img-1:checked ~ .nav-dots label#img-dot-1,
		input#img-2:checked ~ .nav-dots label#img-dot-2,
		input#img-3:checked ~ .nav-dots label#img-dot-3,
		input#img-4:checked ~ .nav-dots label#img-dot-4,
		input#img-5:checked ~ .nav-dots label#img-dot-5,
		input#img-6:checked ~ .nav-dots label#img-dot-6 {
			background-color: rgba(0, 0, 0, 0.8);
		}
		
		.mailBtn {
			padding: 5px 10px;
			border: 1px solid #ccc;
			margin-bottom: 5px;
			box-shadow: 2px 2px 2px #ccc;
			font-weight: bold;
		}
		
		.mailBtn:active {
			box-shadow : none;
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
	<title>검수 재고</title>
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
							<li class="active">검수 재고</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								검수 재고
							</h1>
						</div>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="dtSearchForm" id="dtSearchForm">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
											<input type="text" class="form-control" style="max-width: 80px;text-align:center;" value="UserID" disabled>
											<input type="text" class="form-control" name="userId" style="max-width: 80px;" value="${parameterInfo.userId}">
											<input type="text" class="form-control" style="max-width: 80px;text-align:center;" value="OrderNo" disabled>
											<input type="text" class="form-control" name="orderNo" value="${parameterInfo.orderNo}" style="max-width: 130px;" value="">
											<input type="text" class="form-control" style="max-width: 80px;text-align:center;" value="reTrkNo" disabled>
											<input type="text" class="form-control" name="hawbNo" value="${parameterInfo.hawbNo}" style="max-width: 130px;" value="">
												<!-- <input type="text" class="form-control" name="prdctName" style="width:100%; max-width: 50px; text-align: center;" value="상품명" disabled/>
												<input type="text" class="form-control" id="prdctNameSch" name="prdctNameSch" style="width:100%; max-width: 150px"/> -->
												<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
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
								
							</div>
							<br>
							<div style="margin-bottom:10px;">
								<input type="button" class="mailBtn" id="sendMail" value="용성 메일 전송"/>
								<span class="red">&nbsp;&#42; 용성 출고 건인 경우 아이템 코드 등록을 반드시 확인 후 출고 작업을 진행해 주세요</span>
							</div>
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<form id="returnForm">
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display">
									<table id="dynamic-table" class="table table-bordered" style="width:100%;">
										<thead>
											<tr>
												<th style="width:7%;" class="center colorBlack">
													그룹 재고 번호
												</th>
												<th style="width:6%;" class="center colorBlack">
													Rack
												</th>
												<th style="width:20%;" class="center colorBlack">
													주문 정보
												</th>
												<th style="width:20%;" class="center colorBlack">
													수취인 정보
												</th>
												<th style="width:9%;" class="center colorBlack">
													입고 상태
												</th>
												<th style="width:20%;" class="center colorBlack">
													입고 메모
												</th>
												<th style="width:8%;" class="center colorBlack">
													
												</th>
												<th style="width:10%;" class="center colorBlack">
													입고 사진
												</th>																																			
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${inspStockList}" var="inspStockInfo" varStatus="status">
												<tr>
													<td>
														<span>${inspStockInfo.groupIdx}</span>
													</td>
													<td class="center">
														<span style="font-weight:bold;text-align:center">${inspStockInfo.rackCode}</span>
													</td>
													<td>
														<table class="table table-bordered" style="border-top: 1px solid #ddd;width:100%;background-color: rgba( 255, 255, 255, 0.5 ); margin:0;">
														<tbody>
															<tr>
																<th style="text-align: left;width:20%;">User ID</th>
																<td style="text-align: left;width:80%;">${inspStockInfo.userId}</td>
															</tr>
															<tr>
																<th style="text-align: left;">OrderNo</th>
																<td style="text-align: left;">${inspStockInfo.orderNo}</td>
															</tr>
															<tr>
																<th style="text-align: left;">reTrkNo</th>
																<td style="text-align: left;">${inspStockInfo.hawbNo}</td>
															</tr>
															</tbody>
														</table>
													</td>
													<td>
														<table class="table table-bordered" style="border-top: 1px solid #ddd;background-color: rgba( 255, 255, 255, 0.5 ); margin:0;">
															<tbody>
																<tr>
																	<th style="text-align: left;width:20%;color:black;font-weight: bold;">수취인</th>
																	<td style="text-align: left;width:80%;color:black;">${inspStockInfo.cneeName}</td>
																</tr>
																<tr>
																	<th style="text-align: left;color:black;">연락처</th>
																	<td style="text-align: left;color:black;">${inspStockInfo.cneeTel}</td>
																</tr>
																<tr>
																	<th style="text-align: left;color:black;">주소</th>
																	<td style="text-align: left;color:black;">${fn:substring(inspStockInfo.cneeAddr,0,30)}</td>
																</tr>
															</tbody>
														</table>
													</td>
													<td class="center">
														<span style="font-weight:bold;">${inspStockInfo.whStatusName}<br/>&#x2014;<br/>${inspStockInfo.stateKr}</span>
													</td>
													<td>
														<table class="table table-bordered" style="border-top:1px solid #ddd;background-color:rgba( 255, 255, 255, 0.5 ); margin:0;">
															<tbody>
																<tr>
																	<th style="text-align: left;width:30%;color:black;font-weight: bold;">입고 메모</th>
																	<td style="text-align: left;width:70%;color:black;">${inspStockInfo.whMemo}</td>
																</tr>
																<tr>
																	<th style="text-align: left;color:black;">User Msg</th>
																	<td style="text-align: left;color:black;">${inspStockInfo.userMsg}</td>
																</tr>
																<tr>
																	<th style="text-align: left;color:black;">Admin Msg</th>
																	<td style="text-align: left;color:black;">${inspStockInfo.adminMsg}</td>
																</tr>
															</tbody>
														</table>
														<div style="text-align:right; margin-top:-1px;">
															<a href="javascript:fn_popupOpen('${inspStockInfo.groupIdx}','${inspStockInfo.orgStation}','${inspStockInfo.userId}')">History</a>
														</div>
													</td>
													<td class="center">
														<input type="button" onclick="inspDel('${inspStockInfo.orderReference}','${inspStockInfo.userId}')" class="btn btn-white btn-xs btn-danger clickBtn" value="폐기">
														<input type="button" onclick="fn_popupStockCancle('${inspStockInfo.groupIdx}','${inspStockInfo.orgStation}','${inspStockInfo.userId}','${inspStockInfo.nno}')" class="btn btn-white btn-xs btn-danger clickBtn" value="입고 취소">
														<input type="button" onclick="javascript:fn_pdfPopup('${inspStockInfo.groupIdx}','${inspStockInfo.nno}')" class="btn btn-white btn-xs btn-primary clickBtn" value="재고번호 출력">
														<c:if test="${inspStockInfo.state eq 'C004'}">
															<input type="button" onclick="inspOut('${inspStockInfo.orderReference}')" class="btn btn-white btn-xs btn-primary clickBtn" value="출고 작업">
														</c:if>
														<c:if test="${inspStockInfo.state eq 'D004'}">
															<input type="button" class="btn btn-sm btn-success btn-white clickBtn" onclick="inspOut('${inspStockInfo.orderReference}')" value="강제 출고"/>
														</c:if>
													</td>
													<c:if test="${inspStockInfo.fileCnt ne '0'}">
														<td class="center" style="cursor:pointer;font-weight:bold;" onclick="fn_showSlider('${inspStockInfo.groupIdx}')" >
															View Image
														</td>
													</c:if>	
													<c:if test="${inspStockInfo.fileCnt eq '0'}">
														<td class="center" style="font-weight:bold;">
															No Image
														</td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								</form>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
						</div>
						<form id="stockForm" action="">
							<input type="hidden" id = "orgStation" name="orgStation" value="">
							<input type="hidden" id = "groupIdx" name="groupIdx" value="">
							<input type="hidden" id = "userId" name="userId" value="">
							<input type="hidden" id = "nno" name="nno" value="">
						</form>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		<div class="modal modal-center fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-center" id="addModal">
				<div class="modal-content">
					<div class="modal-header">
					</div>
					<div class="modal-body">
						
					</div>
					<div class="modal-footer">
						<!-- <button type="button" class="delBtn" data-dismiss="modal">취소</button> -->
					</div>
				</div>
			</div>
		</div>

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
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu-2").toggleClass('open');
	           		$("#14thMenu-2").toggleClass('active'); 
	           		$("#14thTwo-3-1").toggleClass('active');	
				})

				var myTable = $('#dynamic-table').DataTable({
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
				})

				$("#sendMail").on('click', function() {
					$("#stockForm").attr("action", "/mngr/aplctList/return/sendItemCode");
					$("#stockForm").attr("method", "get");
					$("#stockForm").submit();
				})
			})
			
			// 입고 취소
			function fn_popupStockCancle(groupIdx, OrgStation, userId, nno) {
				$("#groupIdx").val(groupIdx);
				$("#orgStation").val(OrgStation);
				$("#userId").val(userId);
				$("#nno").val(nno);

				var result = confirm("입고 취소 하시겠습니까?");
				if (result) {
					var formData = $("#stockForm").serialize();
					$.ajax({
						url:'/comn/StockCancleReturn',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert("입고 취소에 실패 하였습니다.");
			            }
					})
				}
			}

			function fn_popupTrash(groupIdx, OrgStation, userId, nno){
				 window.open("/mngr/stock/popupTrash?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500"); 
			}

			// 폐기
			function inspDel(orderReference, userId){
	        	 window.open("/mngr/aplctList/return/inspDel/"+orderReference+"/"+userId, "PopupWin", "width=1000,height=800");
	         }

			// 출고 작업, 강제 출고
			function inspOut(orderReference) {
				location.href="/mngr/aplctList/return/inspListOut/"+orderReference;
			}

			// 재고번호 출력
			function fn_pdfPopup(groupIdx, nno){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				var _width = '700';
			    var _height = '670';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
				window.open("/mngr/aplctList/return/pdfPopup?nno="+nno+"&groupIdx="+groupIdx, "_blank", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}

			
			function fn_showSlider(idx) {
				var _width = '700';
			    var _height = '670';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
			    
				//window.open('/mngr/order/return/loadImage?groupIdx='+idx, "INSP_IMG", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);

				$.ajax({
					url : '/mngr/order/return/loadInspImage',
					type : 'POST',
					data : {groupIdx : idx},
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(result) {
						console.log(result);
						var listSize = result.length;
						var last = listSize-1;
						
						var html = '';
						html += '<button type="button" class="close" data-dismiss="modal">&times;</button>';
						html += '<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">그룹 재고 번호  '+idx+'</h4>';

						$(".modal-header").empty();
						$(".modal-header").append(html);

						var body = '';
						body += '<ul class="slides">';
						$.each(result, function(index, value) {
							var cnt = index+1;
							var next = cnt+1;
							if (index == 0) {
								body += '<input type="radio" name="radio-btn" id="img-'+cnt+'" checked/>';
								body += '<li class="slide-container"><div class="slide"><img src="http://'+value+'"/></div>';
								body += '<div class="nav"><label for="img-'+listSize+'" class="prev">&#x2039;</label>';
								body += '<label for="img-'+next+'" class="next">&#x203a;</label></div></li>';
							} else if (index == last) {
								body += '<input type="radio" name="radio-btn" id="img-'+cnt+'"/>';
								body += '<li class="slide-container"><div class="slide"><img src="http://'+value+'"/></div>';
								body += '<div class="nav"><label for="img-'+index+'" class="prev">&#x2039;</label>';
								body += '<label for="img-1" class="next">&#x203a;</label></div></li>';
							} else {
								body += '<input type="radio" name="radio-btn" id="img-'+cnt+'"/>';
								body += '<li class="slide-container"><div class="slide"><img src="http://'+value+'"/></div>';
								body += '<div class="nav"><label for="img-'+index+'" class="prev">&#x2039;</label>';
								body += '<label for="img-'+next+'" class="next">&#x203a;</label></div></li>';
							}
						})
						
						body += '<li class="nav-dots">';
						$.each(result, function(index, value) {
							var cnt = index+1;
							body += '<label for="img-'+cnt+'" class="nav-dot" id="img-dot-'+cnt+'"></label>';
						})
						
						body += '</li></ul>';
						$(".modal-body").empty();
						$(".modal-body").append(body);
						
						$("#myModal").modal();
					},
		            error : function(xhr, status) {
						alert("이미지 불러오기 실패");
		            }
				})
				
			}
			

		</script>
	</body>
</html>
