<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
   <link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
   	.page-content {
   		font-weight: normal !important;
   	}
   	
	.tbl-return {
		width: 100%;
		margin-top: 8px;
		box-shadow: 0px 0px 5px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-return table {
		width: 100%;
		table-layout: fixed;
	}
	
	.tbl-return .tbl-header {
		background-color: #527d96;
		box-shadow: 0px -1px 3px rgba(82, 125, 150, 0.6);
	}
	
	.tbl-return .tbl-content {
		/* max-height: 50vh; */
		height: auto;
		overflow-x: auto;
		margin-top: 0px;
	}

	.tbl-return th {
		text-align: left;
		color: #fff;
		text-transform: uppercase;
		text-align: center;
		padding: 10px;
	}
	
	.tbl-return td {
		text-align: center;
		vertical-align: middle;
		border-bottom: 1px solid #e8e8e8;
	}
	
	.tbl-content::-webkit-scrollbar {
		width: 6px;
	}
	
	.tbl-content::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-content::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
	}
	
	.select-box {
		display: flex;
		flex-direction: row;
		margin-left: 2px;
	}
	
	.select {
		width: 150px;
		display: inline-block;
		background-color: #fff;
		border-radius: 2px;
		box-shadow: 0 0 2px rgb(204, 204, 204);
		transition: all .5s ease;
		position: relative;
		font-size: 14px;
		color: #474747;
		height: 100%;
		text-align: left;
		outline: none;
	}
	
	.select .option-default {
		cursor: pointer;
		display: block;
		padding: 10px;
		outline: none;
	}
	
	.select .option-default > i {
		font-size: 13px;
		color: #888;
		cursor: pointer;
		transition: all .3s ease-in-out;
		float: right;
		line-height: 20px;
	}
	
	.select:hover {
		box-shadow: 0 0 4px rgb(204, 204, 204);
	}
	
	.select:active {
		background-color: #f8f8f8;
		outline: none;
	}
	
	.select.active:hover, .select.active {
		box-shadow: 0 0 4px rgb(204, 204, 204);
		border-radius: 2px 2px 0 0;
		background-color: #f8f8f8;
		outline: none;
	}
	
	.select.active .option-default > i {
		transform: rotate(-90deg);
	}
	
	.select .option-list {
		position: absolute;
		background-color: #fff;
		width: 100%;
		left: 0;
		margin: 1px 0 0 0;
		box-shadow: 0 1px 2px rgb(204, 204, 204);
		border-radius: 0 1px 2px 2px;
		overflow: hidden;
		display: none;
		max-height: 144px;
		overflow-y: auto;
		z-index: 9999;
	}
	
	.select .option-list li {
		padding: 10px;
		transition: all .2s ease-in-out;
		cursor: pointer;
	}
	
	.select .option-list {
		padding: 0;
		list-style: none;
	}
	
	.select .option-list li:hover {
		background-color: #f2f2f2;
	}
	
	.select .option-list li:active {
		background-color: #e2e2e2;
	}
	
	.option-list::-webkit-scrollbar {
		width: 6px;
	}
	
	.option-list::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
	}
	
	.option-list::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
	}
	
	.select-box input[type="text"] {
		width:200px;
		border: 1px solid rgba(204, 204, 204, 0.3);
		box-shadow: 0 0 2px rgb(204, 204, 204);
		outline: none;
	}
	
	input[type="text"][disabled] {
		width: 100px;
		text-align: center;
		background: WhiteSmoke !important;
		font-weight: 600;
	}
	
	#search-btn {
		width: 45px;
		background-color: #527d96;
		outline: none;
		color: #fff;
		border: none;
		border-radius: 2px;
		box-shadow: 2px 2px 4px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
	}
	
	#search-btn:hover {
		background-color: rgba(82, 125, 150, 0.8);
		box-shadow: 1px 1px 4px rgba(82, 125, 150, 0.3); 
	}
	
	.buttons {
		box-shadow: 2px 2px 3px rgba(82, 125, 150, 0.6);
		transition: 0.3s;
		outline: none;
		border-radius: 2px;
		padding: 5px 10px;
		background-color: #6b95ae;
		border: 1px solid #6b95ae;
		color: #fff;
		font-size: 13px;
		font-weight: normal;
	}
	
	.buttons:hover, .buttons:active {
		box-shadow: 1px 1px 3px rgba(82, 125, 150, 0.3);
	}

	#contents-table td div {
		border-right: 1px solid #e2e2e2;
	}
	
	.highlight-row {
		background-color: Snow;
	}
	
	#contents-table a {
		text-decoration: none;
		cursor: pointer;
	}
	
	
	
	#search-table {
	border: 1px solid #e2e2e2;
	border-collapse: collapse;
}

	#search-table th {
		border: 1px solid #e2e2e2;
		border-collapse: collapse;
		padding-left: 10px !important;
		padding-right: 10px !important;
		background-color: #527d96;
		color: #fff;
	}
	
	#search-table th, td {
		height: 30px !important;
		vertical-align: middle;
	}
	
	#search-table td input[type="text"] {
		height: 30px !important;
		border: none;
	}
	
	#search-table td select {
		height: 30px !important;
	}
	
	#search-btn {
		height: 30px !important;
		background-color: #527d96;
		color: #fff;
		border: none;
		border-radius: 3px;
		padding-left: 10px;
		padding-right: 10px;
	}
	
	#fromDate, #toDate {
		text-align: center;
	}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>반품 리스트</title>
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
                     <li class="active">배송 등록</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        배송 정보 등록
                     </h1>
                  </div>
                  <form name="returnForm" id="returnForm" method="get" action="/mngr/return/hawbNoList">
              		<table id="search-table">
						<tr>
							<th>등록일자</th>
							<td><input type="text" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}"></td>
							<td>-</td>
							<td><input type="text" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}"></td>
							<th>원송장번호</th>
							<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
							<th>반송장번호</th>
							<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
							<th>USER ID</th>
							<td><input type="text" name="userId" id="userId" value="${params.userId}"></td>
							<td><input type="button" id="search-btn" value="검색"></td>
						</tr>
					</table>
					<br/>
					<input type="button" value="출고 취소" id="cancel-btn" class="btn btn-xs btn-white">
					<input type="button" value="배송 등록" id="regist-btn" class="btn btn-xs btn-white">
					<div style="width:100%;">
						<div class="tbl-return">
							<div class="tbl-header">
								<table cellpadding="0" cellspacing="0" border="0">
									<thead>
										<tr>
											<th style="border-right:1px solid #fff;width:5%;">
												<label class="pos-rel">
													<input type="checkbox" class="ace" id="checkAll">
													<span class="lbl"></span>
												</label>
											</th>
											<th style="border-right:1px solid #fff;width:95%;">주문정보</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="tbl-content">
								<table cellpadding="0" cellspacing="0" border="0" id="contents-table">
									<tbody>
										<c:choose>
											<c:when test="${!empty orderList}">
												<c:forEach items="${orderList}" var="orderList" varStatus="orderState">
													<tr>
														<td style="width:5%;border-right:1px solid #e0e0e0;border-top:2px solid #e2e2e2;">
															<input type="hidden" name="transCode[]" value="${orderList.value[0].transCode}">
															<input type="hidden" name="userId[]" value="${orderList.value[0].userId}">
															<label class="pos-rel">
																<input type="checkbox" class="ace" name="chk" value="${orderList.value[0].nno}">
																<span class="lbl"></span>
															</label>
														</td>
														<td style="width:95%;border-top:2px solid #e2e2e2;" class="center colorBlack no-padding">
															<div class="col-xs-12 no-padding" style="border-bottom:1px solid #e2e2e2;">
																<div class="col-xs-1 no-padding" style="font-weight:bold;">USER ID</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">주문번호</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">원송장번호</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">반송장번호</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">배송사</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">도착국가</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">수취인</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">TEL</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">우편번호</div>
																<div class="col-xs-3 no-padding" style="font-weight:bold;">주소</div>
															</div>
															<div class="col-xs-12 no-padding" style="border-bottom:1px solid #e2e2e2;">
																<div class="col-xs-1 no-padding">${orderList.value[0].userId}</div>	
																<div class="col-xs-1 no-padding">
																	<a onclick="fn_modifyOrder('${orderList.value[0].nno}', '${orderList.value[0].userId}')">${orderList.value[0].orderNo}</a>
																</div>
																<div class="col-xs-1 no-padding">
																	<a onclick="fn_modifyOrder('${orderList.value[0].nno}', '${orderList.value[0].userId}')">${orderList.value[0].koblNo}</a>
																</div>
																<div class="col-xs-1 no-padding">
																	<a onclick="fn_modifyOrder('${orderList.value[0].nno}', '${orderList.value[0].userId}')">${orderList.value[0].trkNo}</a>
																</div>
																<div class="col-xs-1 no-padding">${orderList.value[0].transCode}</div>
																<div class="col-xs-1 no-padding">${orderList.value[0].dstnNation}</div>
																<div class="col-xs-1 no-padding">${orderList.value[0].cneeName}</div>
																<div class="col-xs-1 no-padding">${orderList.value[0].cneeTel}</div>
																<div class="col-xs-1 no-padding">${orderList.value[0].cneeZip}</div>
																<div class="col-xs-3 no-padding">${orderList.value[0].cneeAddr} ${orderList.value[0].cneeAddrDetail}</div>
															</div>
															<div class="col-xs-12 no-padding" style="border-bottom:1px solid #e2e2e2;">
																<div class="col-xs-1 no-padding" style="font-weight:bold;">상품 번호</div>
																<div class="col-xs-7 no-padding" style="font-weight:bold;">상품명</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">상품 개수</div>
																<div class="col-xs-1 no-padding" style="font-weight:bold;">단가</div>
																<div class="col-xs-2 no-padding" style="font-weight:bold;">금액</div>
															</div>
															<div class="col-xs-12 no-padding" style="border-bottom:1px solid #e2e2e2;">
																<div class="col-xs-1 no-padding">
																	<c:forEach items="${orderList.value}" var="itemList">
																		${itemList.subNo}<br/>
																	</c:forEach>
																</div>
																<div class="col-xs-7 no-padding">
																	<c:forEach items="${orderList.value}" var="itemList">
																		${itemList.itemDetail}<br/>
																	</c:forEach>
																</div>
																<div class="col-xs-1 no-padding">
																	<c:forEach items="${orderList.value}" var="itemList">
																		${itemList.itemCnt}<br/>
																	</c:forEach>
																</div>
																<div class="col-xs-1 no-padding">
																	<c:forEach items="${orderList.value}" var="itemList">
																		${itemList.unitValue}<br/>
																	</c:forEach>
																</div>
																<div class="col-xs-2 no-padding">
																	<c:forEach items="${orderList.value}" var="itemList">
																		${itemList.itemValue}<br/>
																		<c:set var="itemTotalValue" value="${itemTotalVal+itemList.itemValue}"/>
																	</c:forEach>
																</div>
															</div>
															<div class="col-xs-12 no-padding">
																<div class="col-xs-2 col-xs-offset-8 no-padding righ" style="font-weight:bold;">Total Value</div>
																<div class="col-xs-2 no-padding">
																	<fmt:formatNumber value="${itemTotalValue}" pattern=".00"/>
																	<c:set var="itemTotalVal" value=""/>
																</div>
															</div>
															<div class="col-xs-12" style="text-align:left;padding-left:10px;border-top:1px solid #e2e2e2;">
																<c:forEach items="${orderList.value}" var="orderList">
																	<c:forEach items="${orderList.errorList}" var="errorList">
																		<span class="red errorCol">주문정보의 ${errorList}<br/></span>
																	</c:forEach>
																	<c:forEach items="${orderList.errorItem}" var="errorItem">
																		<span class="red errorCol">상품정보[${orderList.subNo}]의 ${errorItem}<br/></span>
																	</c:forEach>
																</c:forEach>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="2">검색 결과가 존재하지 않습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
						<br/>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
					</form>
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
      
	  <script src="/assets/js/jquery-ui.min.js"></script>
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
      <!-- script on paging end -->
      

      
      <!-- script addon start -->
      <script type="text/javascript">
      <!-- excel upload 추가 -->
		jQuery(function($) {
			$(document).ready(function(e) {
				$("#14thMenu-2").toggleClass('open');
				$("#14thMenu-2").toggleClass('active');
				$("#returnMenu-4").toggleClass('active');
				$(".errorCol").closest('tr').addClass('highlight-row');
			});

			$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})
			
	        $("#search-btn").on('click', function() {
				$("#returnForm").submit();
		    });

		    $("#checkAll").change(function() {
			    if (this.checked) {
				    $("input[name='chk']").each(function() {
					    this.checked = true;
					});
				} else {
					$("input[name='chk']").each(function() {
						this.checked = false;
					});
				}
			});

			$("input[name='chk']").click(function() {
				if ($(this).is(":checked")) {
					var isAllChecked = 0;

					$("input[name='chk']").each(function() {
						if (!this.checked) {
							isAllChecked = 1;
						}
					});

					if (isAllChecked == 0) {
						$("#checkAll").prop("checked", true);
					}
				} else {
					$("#checkAll").prop("checked", false);
				}
			});

			$('#regist-btn').on('click',function(e){
				var targets = new Array();
				var transCodes = new Array();
				var userInfo = new Array();
				
				$("input[name='chk']").each(function(e) {
					var row = $(this).closest('tr').get(0);
					var checked = this.checked;
										
					if ($(row).hasClass('highlight-row')) {
						return;
					}
					if (checked) {
						targets.push($(this).val());
						transCodes.push($(row).find("input[name='transCode[]']").val());
						userInfo.push($(row).find("input[name='userId[]']").val());
					}
				});

				$.ajax({
					url : '/mngr/return/orderRegist',
					type : 'POST',
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					traditional : true,
					data : {targets: targets, userList: userInfo, transCodeList: transCodes},
					error : function(xhr, status) {
						alert("처리 중 시스템 오류가 발생 하였습니다.");
						return;
					},
					success : function(response) {
						if (response.STATUS == 'S') {
							//location.reload();
						} else if (response.STATUS == 'N') {
							alert("등록할 데이터가 없습니다.");
						} else if (response.STATUS == 'F') {
							alert("등록 중 오류가 발생 하였습니다. 다시 시도해 주세요.");
						}
					}
				});
			});

			$("#cancel-btn").on('click', function(e) {
				var targets = new Array();

				$("input[name='chk']").each(function(e) {
					var row = $(this).closest('tr').get(0);
					var checked = this.checked;
					
					if (checked) {
						targets.push($(this).val());
					}
				});

				if (targets.length < 1) {
					alert("선택된 데이터가 없습니다.");
					return;
				} else {
					if (confirm("출고 취소 시 출고 작업 데이터가 삭제 됩니다.\n계속 진행 하시겠습니까?")) {
						$.ajax({
							url : '/mngr/return/orderCancel',
							type : 'POST',
							beforeSend : function(xhr)
							{   
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							traditional : true,
							data : {targets: targets},
							error : function(xhr, status) {
								alert("처리 중 시스템 오류가 발생 하였습니다.");
								return;
							},
							success : function(response) {
								alert(response.msg);
								location.reload();
							}
						});	
					} else {
						return;
					}		
				}
			});
		});

		function fn_modifyOrder(nno, userId) {
			location.href = "/mngr/return/orderHawbUp?nno="+nno+"&userId="+userId;
		}

      </script>
      <!-- script addon end -->

   </body>
</html>