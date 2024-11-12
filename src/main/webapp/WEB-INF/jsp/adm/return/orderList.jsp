<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
	
	.colorBlack {
		color: #000000 !important;
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
	
	#return-table th, td {
		text-align: center;
		word-break: break-all;
	}
	
	#return-table thead th {
		background-color: #527d96;
		color: #fff;
	}
</style>
<!-- basic scripts -->
<!-- ace scripts -->

</head>
<title>반품 리스트</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<!-- headMenu End -->

	<!-- Main container Start-->
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
            try{ace.settings.loadState('main-container')}catch(e){}
         </script>
		<!-- SideMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<!-- SideMenu End -->

		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
						<li>반품</li>
						<li class="active">반품 접수 리스트</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>반품 접수 리스트</h1>
					</div>
					<div style="margin-bottom: 10px;">
						<button type="button" class="btn btn-sm btn-success btn-white" name="excelDown" id="excelDown">접수 자료 다운로드</button>
						<!-- <button type="button" class="btn btn-sm btn-primray btn-white" name="sendCjReturn" id="sendCjReturn">CJ 반품 접수</button> -->
						<div class="row" style="margin-top: 8px;">
							<div class="col-xs-12 col-md-7 form-group">
								<h4>우체국 접수 자료 업로드</h4>
								<input type="file" id="excelFile" name="excelFile" style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
								<button type="button" style="margin-left: 20px; margin-bottom: 2px;" class="btn btn-sm btn-white" name="excelUp" id="excelUp">업로드</button>
							</div>
						</div>
						<br />
					</div>
					<form name="returnForm" id="returnForm" method="get" action="/mngr/return/orderList">
						<table id="search-table">
							<tr>
								<td><select id="state" name="state">
									<option value="">::선택::</option>
									<option value="A000" <c:if test="${params.state eq 'A000'}"> selected </c:if>>1차접수</option>
									<option value="A001" <c:if test="${params.state eq 'A001'}"> selected </c:if>>접수신청</option>
									<option value="A002" <c:if test="${params.state eq 'A002'}"> selected </c:if>>접수완료</option>
									<option value="B001" <c:if test="${params.state eq 'B001'}"> selected </c:if>>수거중</option>
									<option value="C001" <c:if test="${params.state eq 'C001'}"> selected </c:if>>입고</option>
								</select></td>
								<th>접수일자</th>
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
						</table><br>
						<input type="button" class="btn btn-xs btn-white" id="printBarcode" value="바코드 출력" style="margin-bottom:2px;">
						<table class="table table-bordered table-hover" id="return-table">
							<thead>
								<tr>
									<th style="width:2%;">
										<input type="checkbox" id="checkAll">
									</th>
									<th style="width:8%;">USER ID</th>
									<th style="width:5%;">진행상황</th>
									<th style="width:9%;">원송장번호</th>
									<th style="width:9%;">반송장번호</th>
									<th style="width:5%;">도착지</th>
									<th style="width:5%;">반품 유형</th>
									<th style="width:5%;">픽업 유형</th>
									<th style="width:5%;">환급 여부</th>
									<th style="width:7%;">담당자명</th>
									<th style="width:10%;">담당자 Tel</th>
									<th style="width:13%;">담당자 Email</th>
									<th style="width:9%;">반품 등록일자</th>
									<th style="width:8%;"></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty orderList}">
										<c:forEach items="${orderList}" var="orderList" varStatus="status">
											<tr>
												<td>
													<input type="checkbox" name="chk" value="${orderList.nno}">
													<input type="hidden" name="state[]" value="${orderList.state}">
												</td>
												<td>${orderList.userId}</td>
												<td>${orderList.stateName}</td>
												<td><c:if test="${!empty orderList.koblNo}">
													${orderList.koblNo}
												</c:if> <c:if test="${empty orderList.koblNo}">
													-
												</c:if></td>
												<td>${orderList.trkNo}</td>
												<td>${orderList.dstnNation}</td>
												<td><c:if test="${orderList.returnType eq 'N'}">
													일반 발송
												</c:if> <c:if test="${orderList.returnType eq 'E'}">
													긴급 발송
												</c:if></td>
												<td><c:if test="${orderList.pickType eq 'A'}">
													직접 전달
												</c:if> <c:if test="${orderList.pickType eq 'B'}">
													회수 요청
												</c:if></td>
												<td><c:if test="${orderList.taxType eq 'Y'}">
													위약 반송
												</c:if> <c:if test="${orderList.taxType eq 'N'}">
													일반 반송
												</c:if></td>
												<td>${orderList.attnName}</td>
												<td>${orderList.attnTel}</td>
												<td>${orderList.attnEmail}</td>
												<td><fmt:parseDate value="${orderList.WDate}" var="writeDate" pattern="yyyy-MM-dd HH:mm" /> <fmt:formatDate var="wDate" pattern="yyyy-MM-dd HH:mm" value="${writeDate}" /> <c:out value="${wDate}" /></td>
												<td>
													<input type="button" id="modi-btn" class="buttons" onclick="fn_modify('${orderList.nno}', '${orderList.userId}')" value="수정">
													<input type="button" id="del-btn" class="buttons" onclick="fn_delete('${orderList.nno}', '${orderList.state}', '${orderList.stateName}')" value="삭제">
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="13" style="text-align: center;">검색 결과가 존재하지 않습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</form>
					<form id="printForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="targets" id="targets" />
					</form>
				</div>
				<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp"%>
			</div>
		</div>
		<!-- /.main-content -->
	</div>

	<!-- Main container End-->

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
          
          $(document).ready(function() {
        	 	$("#14thMenu-2").toggleClass('open');
	  			$("#14thMenu-2").toggleClass('active');
	  			$("#returnMenu").toggleClass('active');
          });
          
          $("#fromDate, #toDate").datepicker({
				autoclose: true,
				todayHightlight: true,
				dateFormat: 'yymmdd'
			})

	        $("#search-btn").on('click', function() {
				$("#returnForm").submit();
		    })

		    $("#excelDown").on('click', function() {
			    window.open("/mngr/order/epostExcelDown");
			});

	        $('#excelUp').on('click', function(e) {
	               if($('#excelFile').val() == ""){
	                  alert("선택된 파일이 없습니다")
	                  return
	               }
	               var datass = new FormData();
	               datass.append("file", $("#excelFile")[0].files[0])
	               $.ajax({
	                  type : "POST",
	                  beforeSend : function(xhr)
	                  {   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
	                      xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	                  },
	                  url : "/mngr/order/epostExcelUp",
	                  data : datass,
	                  processData: false,
	                     contentType: false,
	                  error : function(){
	                     alert("등록에 실패하였습니다.");
	                     $("#searchIdx").trigger('change');
	                     $('#mask').hide();
	                     location.reload(true);
	                  },
	                  success : function(data){
	                     if(data == "F"){
	                        alert("등록중 오류가 발생했습니다.")
	                        location.reload(true);
	                     } else {
	                        alert("등록되었습니다.");
	                        $("#searchIdx").trigger('change');
	                        $('#mask').hide();
	                        location.reload(true);
	                     }
	                  }
	               })
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
			/*
			$("#sendCjReturn").on('click', function(e) {
				$.ajax({
					url : '/mngr/return/sendCjReturn',
					type : 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						
					},
					error : function(error) {
						
					}
				});
			});
			*/
		});

		function fn_chat(nno) {
			// cs 팝업 생성
		}

		function fn_modify(nno, userId) {
			location.href = '/mngr/return/orderInfo?nno='+nno+'&userId='+userId;
		}

		function fn_delete(nno, state, stateName) {
			var conf = "삭제처리 시 복구가 불가능 합니다.\n정말 삭제 하시겠습니까?";
			if (confirm(conf)) {
				$.ajax({
					url : '/mngr/return/orderDel',
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
			}
		}
			
      </script>
	<!-- script addon end -->

</body>
</html>