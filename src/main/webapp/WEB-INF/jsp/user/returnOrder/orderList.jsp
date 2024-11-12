<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>반품 접수 내역</title>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>

<style type="text/css">
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
	
	.tbl-return {
		margin-top: 14px;
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
		max-height: 50vh;
		height: auto;
		overflow-x: auto;
		margin-top: 0px;
	}
	
	.tbl-return th {
		text-align: left;
		font-weight: 600;
		font-size: 13px;
		color: #fff;
		text-transform: uppercase;
		text-align: center;
		padding: 10px;
	}
	
	.tbl-return td {
		padding: 15px;
		text-align: center;
		vertical-align: middle;
		font-weight: 300;
		font-size: 12px;
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
	
	#keyword {
		width:300px;
		border: 1px solid rgba(204, 204, 204, 0.3);
		box-shadow: 0 0 2px rgb(204, 204, 204);
		outline: none;
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
		padding: 2px 6px;
		background-color: #6b95ae;
		border: 1px solid #6b95ae;
		color: #fff;
	}
	
	.buttons:hover, .buttons:active {
		box-shadow: 1px 1px 3px rgba(82, 125, 150, 0.3);
	}
	
</style>
</head>
	<body class="no-skin"> 
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품 접수 내역</strong>
						</div>
		        	</div>
		      	</div>
		    </div>
	    </div>
	    <div>
			<form name="returnForm" id="returnForm" method="get" action="/cstmr/return/orderList">
				<div class="container">
			       <div class="page-content noneArea">
						<div id="inner-content-side" >
							<h2 style="margin-left:4px;">반품 접수 내역</h2>
							<br>
							<table id="search-table">
								<tr>
									<th>주문일자</th>
									<td><input type="text" id="fromDate" name="fromDate" autocomplete="off" value="${params.fromDate}"></td>
									<td>-</td>
									<td><input type="text" id="toDate" name="toDate" autocomplete="off" value="${params.toDate}"></td>
									<th>원송장번호</th>
									<td><input type="text" name="koblNo" id="koblNo" value="${params.koblNo}"></td>
									<th>반송장번호</th>
									<td><input type="text" name="trkNo" id="trkNo" value="${params.trkNo}"></td>
									<td>
										<select id="status" name="status">
											<option value="">::선택::</option>
											<option value="A000" <c:if test="${params.state eq 'A000'}"> selected </c:if>>1차접수</option>
											<option value="A001" <c:if test="${params.state eq 'A001'}"> selected </c:if>>접수신청</option>
											<option value="A002" <c:if test="${params.state eq 'A002'}"> selected </c:if>>접수완료</option>
											<option value="B001" <c:if test="${params.state eq 'B001'}"> selected </c:if>>수거중</option>
											<option value="C001" <c:if test="${params.state eq 'C001'}"> selected </c:if>>입고</option>
										</select>
									</td>
									<td>
										<input type="button" id="search-btn" value="검색">
									</td>
								</tr>
							</table>
							<br/>
							<input type="button" class="btn btn-xs btn-white" id="printBarcode" value="바코드 출력">
							<div class="tbl-return">
								<div class="tbl-header">
									<table cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th style="width:2%;">
													<input type="checkbox" id="checkAll">
												</th>
												<th style="border-right:1px solid #fff;width:5%;">진행상황</th>
												<th style="border-right:1px solid #fff;width:10%;">원운송장번호</th>
												<th style="border-right:1px solid #fff;width:10%;">주문번호</th>
												<th style="border-right:1px solid #fff;width:7%;">도착지</th>
												<th style="border-right:1px solid #fff;width:8%;">수거 택배사</th>
												<th style="border-right:1px solid #fff;width:10%;">수거 택배번호</th>
												<th style="border-right:1px solid #fff;width:10%;">수거 접수일자</th>
												<th style="border-right:1px solid #fff;width:10%;">반품 등록일자</th>
												<th style="border-right:1px solid #fff;width:20%;">메세지</th>
												<th style="width:8%;"></th>
											</tr>
										</thead>
									</table>
								</div>
								<div class="tbl-content">
									<table cellpadding="0" cellspacing="0" border="0">
										<tbody>
											<c:choose>
												<c:when test="${!empty orderList}">
													<c:forEach items="${orderList}" var="orderList" varStatus="status">
														<tr>
															<td style="width:2%;">
																<input type="checkbox" name="chk" value="${orderList.nno}">
																<input type="hidden" name="state[]" value="${orderList.state}">
															</td>
															<td style="width:5%;">${orderList.stateName}</td>
															<td style="width:10%;">
																<c:if test="${!empty orderList.koblNo}">
																	${orderList.koblNo}
																</c:if>
																<c:if test="${empty orderList.koblNo}">
																	-
																</c:if>
															</td>
															<td style="width:10%;">${orderList.orderNo}</td>
															<td style="width:7%;">${orderList.dstnNation}</td>
															<td style="width:8%;">${orderList.trkCom}</td>
															<td style="width:10%;">${orderList.trkNo}</td>
															<td style="width:10%;">${orderList.trkDate}</td>
															<td style="width:10%;">
																<fmt:parseDate value="${orderList.WDate}" var="writeDate" pattern="yyyy-MM-dd HH:mm" />
																<fmt:formatDate var="wDate" pattern="yyyy-MM-dd HH:mm" value="${writeDate}"/>
																<c:out value="${wDate}"/>
															</td>
															<td style="width:20%;text-align:right;padding:0px;">
																<span style="font-size:12px;"><a style="cursor:pointer;" onclick="popupMemo('${orderList.nno}')">[고객]</a></span>
																<input type="text" class="cs-input" style="border-radius:5px !important;width:80%;height:25px;font-size:11px;" value="${orderList.userMemo}">
																<br/>
																<span style="font-size:12px;"><a style="cursor:pointer;" onclick="popupMemo('${orderList.nno}')">[관리자]</a></span>
																<input type="text" class="cs-input" style="border-radius:5px !important;width:80%;height:25px;font-size:11px;" value="${orderList.adminMemo}">
															</td>
															<td style="width:8%;">
																<input type="button" id="modi-btn" class="buttons" onclick="fn_modify('${orderList.nno}')" value="수정">
																<input type="button" id="del-btn" class="buttons" onclick="fn_delete('${orderList.nno}', '${orderList.state}', '${orderList.stateName}')" value="삭제">
															</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="10" style="text-align:center;">검색 결과가 존재하지 않습니다.</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<br>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</div>
			</form>
			<form id="printForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="targets" id="targets" />
			</form>
		</div>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	<script src="/testView/js/main.js"></script>
	
	<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
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
	
	<script src="/assets/js/bootstrap-tag.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<script type="text/javascript">
		jQuery(function($) {
			$("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})
			
			if ($("#state").val() != "") {
				var state = $("#state").val();
				var text = $('#'+state).text();
				$("#state-select").find("span").text(text);
			}

			if ($("#searchType").val() != "") {
				var searchType = $("#searchType").val();
				var text = $("#"+searchType).text();
				$("#search-select").find("span").text(text);
			}

			$(window).on("load resize", function() {
				var scrollWidth = $(".tbl-content").width() - $(".tbl-content table").width();
				$(".tbl-header").css({"padding-right":scrollWidth});
			}).resize();

	        $('.select').click(function () {
	            $(this).attr('tabindex', 1).focus();
	            $(this).toggleClass('active');
	            $(this).find('.option-list').slideToggle(300);
	            });
	        $('.select').focusout(function () {
	            $(this).removeClass('active');
	            $(this).find('.option-list').slideUp(300);
	        });
	        $('.select .option-list li').click(function () {
	            $(this).parents('.select').find('span').text($(this).text());
	            $(this).parents('.select').find('input').attr('value', $(this).attr('id'));
	        });


	        $("#search-btn").on('click', function() {
		        if ($("#keyword").val() == "") {
					if ($("#state").val() == "") {
						$("#state").val("all");
					}
			    } else {
					if ($("#searchType").val() == "") {
						alert("검색 항목을 선택해주세요.");
						return;
					}
				}
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

			$("#printBarcode").on('click', function(e) {
				
				var targets = new Array();

				$("input[name='chk']").each(function(e) {
					var row = $(this).closest('tr').get(0);
					var checked = this.checked;

					if (checked) {
						var nno = $(this).val();
						var state = $(row).find("input[name='state[]']").val();
						if (state != 'B001') {
							return;
						} else {
							targets.push(nno);
						}
					}
				});

				if (targets != "") {
					$("#targets").val(targets);

					var popupWin = window.open("", "_epostPrint",'width=1000, height=600, toolbar=yes, menubar=yes, scrollbars=yes');
					
					$("#printForm").attr("target", "_epostPrint");
					$("#printForm").attr("action", "/comn/return/printEpostBarcode");
					$("#printForm").attr("method", "post");
					$("#printForm").submit();	
				} else {
					alert("수거 중인 반품 건만 출력 가능합니다.");
				}
			});

		});

		function fn_chat(nno) {
			// cs 팝업 생성
		}

		function fn_modify(nno) {
			location.href = '/cstmr/return/orderInfo?nno='+nno;
		}

		function fn_delete(nno, state, stateName) {
			if (state == "A000" || state == "A001" || state == "A002") {
				var conf = "삭제처리 시 복구가 불가능 합니다.\n해당 접수 건을 삭제 하시겠습니까?";
				if (confirm(conf)) {
					$.ajax({
						url : '/cstmr/return/orderDel',
						type : 'POST',
						beforeSend : function(xhr)
						{
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {nno: nno},
						success : function(data) {
							if (data.STATUS == 'SUCCESS') {
								alert("삭제 되었습니다.");
								location.reload();
							} else {
								alert("삭제 중 오류가 발생 하였습니다.");
								return false;
							}
						},
						error : function(error) {
							alert("시스템 오류가 발생 하였습니다. 다시 시도해 주세요.");
						}
					})
				} else {
					return;
				}
			} else {
				alert(stateName+"인 건은 삭제할 수 없습니다. 관리자에게 문의하세요.");
				return;	
			}
		}
		function popupMemo(nno) {
            var url = "/comn/returnCsMemo?nno="+nno;
           	window.open(url, 'popupMemo', 'width=500,height=600');
		}
	</script>
</body>
</html>