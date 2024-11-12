<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  
	  .colorBlack {color:#000000 !important;}
	 
	 #dynamic-table td {
	 	display: table-cell;
	 	vertical-align: middle;
	} 
	
	#dynamic-table2 td {
	 	display: table-cell;
	 	vertical-align: middle;
	}
	
	#historyTable td {
	 	display: table-cell;
	 	vertical-align: middle;
	}
	
	.arrow1:after {
		content: '';
	    display:inline-block;
	    width: 4rem;
	    height: 4rem;
	    margin-top: 3rem;
	    margin-right: 1rem;
	    border-top: 0.1rem solid #c8c8c8;
	    border-right: 0.1rem solid #c8c8c8;
	    transform: rotate(45deg);
	}
	
	#newRackCode {
		border: 1px solid #dcdcdc;
		width: 100%;
		height: 36px;
	}
	
	#newRackCode:focus {
		outline: 1px solid #dcdcdc;
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
		<!-- basic scripts End-->
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
	</head> 
	<title>WMS.ESHOP</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
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
								사입 Rack
							</li>
							<li class="active">Rack 재고 파악</li>
						</ul>
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>${idx}회차 재고 파악</h1>
						</div>
						<br />
						<div>
							<!-- search form -->
							<form id="searchForm" name="searchForm">
								 <table class="table table-bordered" style="width: 1300px; height: 20px;">
									<thead>
										<tr>
											<th class="center colorBlack" style="width: 100px;">Rack</th>
											<td style="padding: 0px; width: 180px;">
												<input style="width: 100%; height: 100%;" type="text" name="searchRackName" value="${parameterInfo.rackName}">
											</td>
											<th class="center colorBlack">USER ID</th>
											<td style="padding: 0px; width: 180px;">
												<input style="width: 100%; height: 100%;" name="searchUserId" type="text" value="${parameterInfo.userId}">
											</td>
											<th class="center colorBlack">Customer Item Code</th>
											<td style="padding: 0px; width: 180px;">
												<input style="width: 100%; height: 100%;" name="searchCusItemCode" type="text" value="${parameterInfo.cusItemCode}">
											</td>
											<th class="center colorBlack">상품명</th>
											<td style="padding: 0px; width: 180px;">
												<input style="width: 100%; height: 100%;" name="searchItemDetail" type="text" value="${parameterInfo.itemDetail}">
											</td>
											<td style="width: 50px;">
												<input type="submit" style="margin: 0 auto; text-align: center;" value="조회" id="searchBtn">
												<input type="hidden" name="idx" id="idx" value="${idx}">
												<input type="hidden" name="rackName" id="rackName" value="${rackName}">
												<input type="hidden" name="userId" id="userId" value="${userId}">
												<input type="hidden" name="cusItemCode" id="cusItemCode" value="${cusItemCode}">
												<input type="hidden" name="itemDetail" id="itemDetail" value="${itemDetail}">
											</td>
										</tr>
									</thead>
								</table>
							</form>
							<!-- search form -->
						</div>
						<br />
						<!-- 랙 리스트 -->
						<div id="rackTable" class="table-contents" style="width: 24%; float:left;">
							<table id="dynamic-table" class="table table-bordered table-hover" style="width: 100%;">
								<thead>
									<tr>
										<th class="center colorBlack" style="display:none;"></th>
										<th class="center colorBlack">Rack</th>
										<th class="center colorBlack">Station</th>
										<th class="center colorBlack">수량</th>
										<th class="center colorBlack" style="width: 100px;">체크수량</th>
									</tr>
								</thead>
								<tbody id="rackBody">
									<c:forEach items="${rackList}" var="rackList" varStatus="status">
										<tr onclick="javascript:selectedRecord(this);">
											<td class="center" style="word-break:break-all; display:none;">
												<input type="hidden" name="rackCode" id="rackCode" value="${rackList.rackCode}"/>
												${rackList.rackName}
											</td>
											<td class="center" style="word-break:break-all;">
												<input type="hidden" name="rackName" id="rackName" value="${rackList.rackName}"/>
												${rackList.rackName}
											</td>
											<td class="center" style="word-break:break-all;">
												<input type="hidden" name="orgStation" id="orgStation" value="${rackList.orgStation}"/>
												${rackList.orgStation}
											</td>
											<td style="work-break:break-all; text-align: right;">
												<input type="hidden" name="cnt" id="cnt" value="${rackList.cnt}"/>
												${rackList.cnt}
											</td>
											<td style="work-break:break-all; text-align: right;">
												<input type="hidden" name="rackRemark" id="rackRemark" value="${rackList.checkCnt}"/>
												${rackList.checkCnt}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
						</div>
						
						<!-- 재고 리스트 -->
						<div id="stockTable" style="width:75%; float: right; text-align:center;">
							<div id="table-contents" class="table-contents center" style="width:100%; max-height: 330px; overflow-y: auto;">
								<table id="dynamic-table2" class="table table-bordered table-hover" style="width: 100%;">
									<thead>
										<tr>
											<th class="center colorBlack" style="display: none;">Takein Code</th>
											<th class="center colorBlack">Rack Code</th>
											<th class="center colorBlack">Customer Code</th>
											<th class="center colorBlack">USER ID</th>
											<th class="center colorBlack">상품명</th>
											<th class="center colorBlack">수량</th>
											<th class="center colorBlack">체크 수량</th>
											<th class="center colorBlack">비고</th>
											<th class="center colorBlack"></th>
										</tr>
									</thead>
									<tbody id="dynamicTbody">
										<tr id="stockBody">
											<td class="center" colspan=8>Rack을 선택해주세요</td>
										</tr>
									</tbody>
								</table>
							</div>
							<br>
							<div id="moveRackTable">
								<h6 style="text-align:left; margin-left:14px; font-weight:bold;">재고 파악 및 랙 이동</h6>
								<div id="moveHistory" style="width:48%; float:left;">
									<table id="historyTable" class="table table-bordered" style="width: 100%; margin-left: 10px;">
										<thead>
											<tr>
												<th class="center colorBlack">Rack</th>
												<th class="center colorBlack">Customer Code</th>
												<th class="center colorBlack">USER ID</th>
												<th class="center colorBlack">수량</th>
												<th style="display:none;">TakeInCode</th>
											</tr>
										</thead>
										<tbody>
											<tr style="height: 53px;">
												<td id="rack_code"></td>
												<td id="cus_item_code"></td>
												<td id="user_id"></td>
												<td style="text-align: right;" id="wh_cnt"></td>
												<td id="take_in_code" style="display:none;"></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div style="width:12%; float:left;">
									<div class="arrow1"></div>
								</div>
								<div style="width:38%; float:left;">
									<table id="historyTable" class="table table-bordered" style="width: 100%;">
										<thead>
											<tr>
												<th class="center colorBlack">Rack</th>
												<th class="center colorBlack">수량</th>
												<th class="center colorBlack"></th>
											</tr>
										</thead>
										<tbody id="dynamicTbody2">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
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
		
		
		
		<!-- script datepicker -->
		
		<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>

		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#15thMenu").toggleClass('open');
					$("#15thMenu").toggleClass('active'); 
					$("#15thThird").toggleClass('active');
					
					$("#moveRackTable").hide();
				});

				$(document).on("click", "#dynamic-table tr", function(event, orgStation, idx, rackCode) {

					var orgStation = orgStation;
					var idx = idx;
					var rackCode = rackCode;
					
					if (orgStation == null || idx == null || rackCode == null) {
						var idx = ${idx};
						var thisRow = $(this).closest('tr');
						var rackCode = thisRow.find('td:eq(0)').find('input').val();
						var orgStation = thisRow.find('td:eq(2)').find('input').val();

						console.log("orgStation: " + orgStation + ", idx: " + idx + ", rackCode: " + rackCode);
					}
					
					var html = "";
		
					var target = {rackCode: rackCode, orgStation: orgStation, index: idx};

					
					 $.ajax({
						url : "/mngr/takein/rack/stockInfoList2",
						data : target,
						type : "GET",
						dataType : "json",
						success : function(data) {
							
							var values = [];
							values = data.stockInfo;

							$.each(values, function(index, value) {
								html += '<tr>';
								html += '<td class="center" style="word-break: break-all; display: none;"><input type="hidden" class="takeInCode1" value="'+value.takeInCode+'"/>'
										+value.takeInCode+'</td>';
								html += '<td class="center" style="word-break: break-all;"><input type="hidden" class="rackCode1" value="'+value.rackCode+'"/>'
										+value.rackCode+'</td>';
								html += '<td class="center" style="word-break: break-all;"><a class="cusItemCode" style="font-weight: bold; cursor: pointer;">'
										+value.cusItemCode+'</a></td>';
								html += '<td class="center" style="word-break: break-all;"><input type="hidden" class="userId1" value="'+value.userId+'"/>'
										+value.userId+'</td>';
								html += '<td class="center" style="word-break: break-all; width: 420px;"><input type="hidden" class="itemDetail1" value="'+value.itemDetail+'"/>'
										+value.itemDetail+'</td>';
								html += '<td style="word-break: break-all; text-align: right;"><input type="hidden" class="whCnt1" value="'+value.whCnt+'"/>'
										+value.whCnt+'</td>';
								if (!value.checkCnt) {
									html += '<td style="word-break: break-all; text-align: right; width:100px;"><input type="number" class="checked" style="width:100%; border:none; text-align: right;" value=""/></td>';
								} else {
									html += '<td style="word-break: break-all; text-align: right; width:100px;"><input type="number" class="checked" style="width:100%; border:none; text-align: right;" value="'+value.checkCnt+'"/></td>';
								}
								if (!value.remark) {
									html += '<td class="center" style="word-break: break-all; width: 280px;" ><input type="text" class="remark" style="width:100%; border:none;" value=""/></td>';
								} else {
									html += '<td class="center" style="word-break: break-all; width: 280px;" ><input type="text" class="remark" style="width:100%; border:none;" value="'+value.remark+'"/></td>';
								}
								html += '<td class="center" style="word-break: break-all;"><input type="button" class="registBtn" value="등록" /></td>';
								html += '</tr>';
							})

							$("#dynamicTbody").empty();
							$("#dynamicTbody").append(html);
							
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("데이터 조회 실패");
						}
					})
				});

				$(document).on("click", ".registBtn", function() {
					var index = $(".registBtn").index(this);
					var checkCnt = $(".checked").eq(index).val();
					var remark = $(".remark").eq(index).val();
					var rackCode = $(".rackCode1").eq(index).val();
					var takeInCode = $(".takeInCode1").eq(index).val();
					var cusItemCode = $(".cusItemCode").eq(index).text();
					var whCnt = $(".whCnt1").eq(index).val();
					var userId = $(".userId1").eq(index).val();
					var idx = ${idx};

					var formData = new Object();
					formData.index = idx;
					formData.takeInCode = takeInCode;
					formData.rackCode = rackCode;
					formData.cusItemCode = cusItemCode;
					formData.remark = remark;
					formData.whCnt = whCnt;
					formData.checkCnt = checkCnt;
					console.log(formData);

					$.ajax({
						url : "/mngr/takein/rack/checkStockInfoUp",
						type : "POST",
						data : formData,
						beforeSend: function(xhr) {
							xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							if (data.result == "S") {
								alert("등록 되었습니다.");
								fn_showRackList();																									
							} else {
								 alert("등록 중 오류가 발생 하였습니다.");
							}
						},
						error : function(request,status,error) {
							alert("등록에 실패 하였습니다.");
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
				})

				$(document).on("click", ".cusItemCode", function() {
					var index = $(".cusItemCode").index(this);
					var checkCnt = $(".checked").eq(index).val();
					var rackCode = $(".rackCode1").eq(index).val();
					var takeInCode = $(".takeInCode1").eq(index).val();
					var cusItemCode = $(".cusItemCode").eq(index).text();
					var remark = $(".remark").eq(index).val();
					var whCnt = $(".whCnt1").eq(index).val();
					var userId = $(".userId1").eq(index).val();
					var idx = ${idx};

					$("#moveRackTable").show();
					
					$("#rack_code").text(rackCode);
					$("#cus_item_code").text(cusItemCode);
					$("#user_id").text(userId);
					$("#wh_cnt").text(whCnt);
					$("#take_in_code").text(takeInCode);

					var datass = {takeInCode: takeInCode, rackCode: rackCode, userId: userId, cusItemCode: cusItemCode, whCnt: whCnt, idx: idx};

					$.ajax({
						url : "/mngr/takein/rack/moveRackStockInfoMoved",
						type : "GET",
						data : datass,
						success : function(data) {
							if (data.result == "S") {
								var values = [];
								values = data.rackCodeList;

								addHtml = "";
								addHtml += '<tr>';
								addHtml += '<td><input list="rackCodeList" name="newRackCode" id="newRackCode" placeholder="rack code 입력"/>';
								addHtml += '<datalist id="rackCodeList" name="rackCodeList">';
								for (var i = 0; i < data.rackCodeList.length; i++) {
									addHtml += '<option ';
									if (data.rackCodeList[i].rackCode == $("#newRackCode").val()) {
										addHtml += ' selected';
									}
									addHtml += 'value="'+data.rackCodeList[i].rackCode+'">'+data.rackCodeList[i].rackCode+'</option>';
								}
								addHtml += '</datalist></td>';
								addHtml += '<td><input type="number" id="count" name="count" style="width:80px;" readonly value="'+data.stockInfo.whCnt+'"/>';
								addHtml += '</td>';
								addHtml += '<td><button type="button" id="moveBtn2">이동</button></td></tr>';

								$("#dynamicTbody2").empty();
								$("#dynamicTbody2").append(addHtml);

							} else {
								console.log("Failed");
							}

							$("#moveBtn2").on("click", function() {
								var newRackCode = $('#newRackCode').val();
								var checkCnt = $('#count').val();

								var datalist = {takeInCode: takeInCode, rackCode: rackCode, userId: userId, cusItemCode: cusItemCode, whCnt: whCnt, idx: idx, newRackCode: newRackCode, checkCnt: checkCnt};
								
								if (newRackCode != "") {
									if (confirm("입력한 Rack으로 이동 하시겠습니까?")) {
										$.ajax({
											url : "/mngr/takein/rack/checkStockInfoMoveUp",
											type : "POST",
											data : datalist,
											beforeSend: function(xhr) {
												xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
											},
											success : function(data) {
												if (data.result == "S") {
													alert("이동 되었습니다");
													fn_showRackList();
													fn_showStockList(data.orgStation, data.idx, data.rackCode);
												} else {
													alert("이동에 실패 하였습니다");
												}
											},
											error : function(request, status, error) {
												console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
											}
										})
									} else {
										alert("아니오");
										return false;
									}
								} else {
									alert("이동할 Rack Code를 입력해 주세요.");
								}
							})
						},
						error : function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
				})
			});


			function fn_showStockList(orgStation, idx, rackCode) {

				$("#dynamic-table tr").trigger("click", [orgStation, idx, rackCode]);
			}

	
			function fn_showRackList() {
				
				var formData = $("#searchForm").serialize();
				console.log(formData);
				
				$.ajax({
					url : "/mngr/takein/rack/stockCheckInfo",
					type : "GET",
					dataType : "json",
					data : formData,
					success : function(data) {
						var values = [];
						values = data.rackList;
						html = "";
						$.each(values, function(index, value) {
							html += '<tr onclick="javascript:selectedRecord2(this);">';
							html += '<td class="center" style="word-break:break-all; display:none;">';
							html += '<input type="hidden" name="rackCode" id="rackCode" value="'+value.rackCode+'"/>'+value.rackCode+'</td>';
							html += '<td class="center" style="word-break:break-all;"><input type="hidden" name="rackName" id="rackName" value="'+value.rackName+'"/>'+value.rackName+'</td>';
							html += '<td class="center" style="word-break:break-all;"><input type="hidden" name="orgStation" id="orgStation" value="'+value.orgStation+'"/>'+value.orgStation+'</td>';
							html += '<td style="word-break:break-all; text-align: right;"><input type="hidden" name="cnt" id="cnt" value="'+value.cnt+'"/>'+value.cnt+'</td>';
							html += '<td style="word-break:break-all; text-align: right;"><input type="hidden" name="checkCnt" id="checkCnt" value="'+value.checkCnt+'"/>'+value.checkCnt+'</td>';
							html += '</tr>';
						})

						$("#rackBody").empty();
						$("#rackBody").append(html);



					},
					error : function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("데이터 조회 실패");
					}
				})
			}

			function selectedRecord(target) {

				var tbody = target.parentNode;
				var trs = tbody.getElementsByTagName('tr');

				var backColor = "#ffffff";
				var textColor = "black";
				var orgBColor = "#a9a9a9";
				var orgTColor = "#ffffff";

				var no = "";
				var no1 = "";

				for (var i = 0; i < trs.length; i++) {
					if (trs[i] != target) {
						trs[i].style.backgroundColor = backColor;
						trs[i].style.color = textColor;
						trs[i].style.fontWeight = "normal";
					} else {
						trs[i].style.backgroundColor = orgBColor;
						trs[i].style.color = orgTColor;
						trs[i].style.fontWeight = "bold";
						var td = trs[i].getElementsByTagName('td');
					}
				}
			}

			function selectedRecord2(target) {

				var tbody = target.parentNode;
				var trs = tbody.getElementsByTagName('tr');

				var backColor = "#ffffff";
				var textColor = "black";
				var orgBColor = "#a9a9a9";
				var orgTColor = "#ffffff";

				var no = "";
				var no1 = "";

				for (var i = 0; i < trs.length; i++) {
					if (trs[i] != target) {
						trs[i].style.backgroundColor = backColor;
						trs[i].style.color = textColor;
						trs[i].style.fontWeight = "normal";
					} else {
						trs[i].style.backgroundColor = orgBColor;
						trs[i].style.color = orgTColor;
						trs[i].style.fontWeight = "bold";
						var td = trs[i].getElementsByTagName('td');
					}
				}
			}


		</script>
		<!-- script addon end -->
	</body>
</html>
