<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<meta charset="UTF-8">
<title>반품 접수 내역</title>



<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
		@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');
 		.colorBlack {color:#000000 !important;}
	  
	  
	  .modal-content {
	  	background-color: transparent;
	  	border:none;
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
		  text-align: center;
		  vertical-align: middle; 
		 }
		 
		 #addModal {
		 	width: 50%;
		 }
		 
		 #select:focus {
		 	border: 2px solid #3296FF;
		 }
		 
		 #koblNo:focus {
		 	border: 1px solid #3296FF;
		 }
		 
		 
		 .normal-btn {
		  background:#93a5cf;
		  color:#fff;
		  border:none;
		  border-radius: 20px;
		  position:relative;
		  width: 200px;
		  height:100px;
		  font-size:1.6em;
		  /* padding:0 2em; */
		  cursor:pointer;
		  transition:800ms ease all;
		  outline:none;
		  font-family: "Gowun Dodum", sans-serif;
		}
		
		.normal-btn:hover {
		  background:#fff;
		  color:#93a5cf;
		}
		
		.normal-btn:before, .normal-btn:after {
		  content:'';
		  position:absolute;
		  top:0;
		  right:0;
		  height:2px;
		  width:0;
		  background: #93a5cf;
		  transition:400ms ease all;
		}
		
		.normal-btn:after{
		  right:inherit;
		  top:inherit;
		  left:0;
		  bottom:0;
		}
		
		.normal-btn:hover:before, .normal-btn:hover:after {
		  width:100%;
		  transition:800ms ease all;
		}
		
		.return-btn {
		  background:#93a5cf;
		  color:#fff;
		  border:none;
		  border-radius: 20px;
		  position:relative;
		  width: 200px;
		  height:100px;
		  font-size:1.6em;
		  /* padding:0 2em; */
		  cursor:pointer;
		  transition:800ms ease all;
		  outline:none;
		  font-family: "Gowun Dodum", sans-serif;
		  margin-left: 30px;
		}
		
		.return-btn:hover {
		  background:#fff;
		  color:#93a5cf;
		}
		
		.return-btn:before, .return-btn:after {
		  content:'';
		  position:absolute;
		  top:0;
		  right:0;
		  height:2px;
		  width:0;
		  background: #93a5cf;
		  transition:400ms ease all;
		}
		
		.return-btn:after {
		  right:inherit;
		  top:inherit;
		  left:0;
		  bottom:0;
		}
		
		.return-btn:hover:before, .return-btn:hover:after {
		  width:100%;
		  transition:800ms ease all;
		}
		
		 #searchBtn {
		  	background: white;
		   	border: 1px solid #DCEBFF;
		   	font-weight:bold;
		  }
		  
		  #searchBtn:hover {
		  	background: #3296FF;
		   	border: 1px solid #E8F5FF;
		   	font-weight:bold;
		   	color:white;
		  }



	</style>
	<!-- basic scripts -->
	</head>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
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
		<form name="returnForm" id="returnForm" method="">
		<!-- <input type="hidden" id="_method" name="_method" value="delete"/> -->
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="container">
		       <div class="page-content noneArea">
					<div id="inner-content-side" >
						<br>
						<h2 style="margin-left:4px;">반품 접수 내역</h2>
						<%-- <div id="search-div">
							<br>
							<div class="row">
								<div style="margin-left:20px; margin-bottom:10px; width:100%; vertical-align:middle;">
									<select id="inspType" name="inspType" style="height:34px; width:100px;">
										<option <c:if test="${params.inspType == 'all'}"> selected</c:if> value="all">::전체::</option>
										<option <c:if test="${params.inspType == 'D001'}"> selected</c:if> value="D001">취소</option>
										<option <c:if test="${params.inspType == 'D005'}"> selected</c:if> value="D005">폐기</option>
									</select>
									<input type="text" style="width:220px;" name="koblNo" autocomplete="off" id="koblNo" value="${params.koblNo}" placeholder="원운송장번호를 입력하세요" />
									<button type="submit" style="height:34px; width:60px;" id="searchBtn">조회</button>
								</div>
							</div>
							<br>
							<div id="delete-user" style="text-align: right">
								<!-- <input type="button" onclick="location.href='/return/regist/returnOthers'" value="test"/> -->
								<c:if test="${depositable eq 'T' }">
									<input type="button" id="receiptBtn" value="접수하기" class="btn btn-sm btn-info" onClick="location.href='/return/kind'">
								</c:if>
								<c:if test="${depositable eq 'F' }">
									<font color="red">예치금이 부족하여 접수할 수 없습니다.</font><br/>
									<input type="button" id="receiptBtn" value="접수하기" class="btn btn-sm btn-info" style="color: #000000 !important;" disabled>
								</c:if>
								<!-- <input type="button" id="delBtn" name="delBtn" class="btn btn-sm btn-danger" value="삭제하기"> -->
							</div>
						</div> --%>
						<br/><br/>
						<div class="row" style="margin-left:5px; margin-bottom:10px; width:100%;">
							<div class="col-xs-6">
								<select style="height:34px;" name="code">
									<option <c:if test="${map.code == 'All'}"> selected</c:if> value="All">::전체::</option>
									<option <c:if test="${map.code == 'A000'}"> selected</c:if> value="A000">1차 접수</option>
									<option <c:if test="${map.code == 'A001'}"> selected</c:if> value="A001">접수신청</option>
									<option <c:if test="${map.code == 'A002'}"> selected</c:if> value="A002">접수완료</option>
									<option <c:if test="${map.code == 'B001'}"> selected</c:if> value="B001">수거중</option>
									<option <c:if test="${map.code == 'C001'}"> selected</c:if> value="C001">입고</option>
								</select>
								<input type="text" style="width:220px;" name="koblNo" autocomplete="off" id="koblNo" value="${map.koblNo}" placeholder="원운송장번호를 입력하세요" />
								<button type="submit" style="height:34px; width:60px;" id="searchBtn">조회</button>
							</div>
							<div class="col-xs-6" style="text-align:right;">
								<input type="button" id="receiptBtn" value="접수하기" class="btn btn-sm btn-primary" onClick="fn_returnKind()">
								<%-- <c:if test="${depositable eq 'T' }">
									<input type="button" id="receiptBtn" value="접수하기" class="btn btn-sm btn-primary" onClick="fn_returnKind()">
								</c:if>
								<c:if test="${depositable eq 'F' }">
									<font color="red">예치금이 부족하여 접수할 수 없습니다.</font><br/>
									<input type="button" id="receiptBtn" value="접수하기" class="btn btn-sm btn-primary" style="color: #000000 !important;" disabled>
								</c:if> --%>
							</div>
						</div>
					</div>
					<div style="width:100%; margin-top:10px;">
						<div style="border-radius:10px; border:1px solid #dcdcdc; width:100%; padding:10px; margin-left:10px;">
							<table style="width:100%;">
								<thead style="border-bottom:2px double #ccc;">
									<tr>
										<th style="width:5%; height:50px; text-align:center; border-right:1px dotted #ccc;">No</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">진행상황</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">원운송장번호</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">주문번호</th>
										<th style="width:5%; text-align:center; border-right:1px dotted #ccc;">도착국가</th>
										<th style="width:5%; text-align:center; border-right:1px dotted #ccc;">반송유형</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반품 접수일</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">수거 택배업체</th>
										<th style="width:14%; text-align:center; border-right:1px dotted #ccc;">수거 택배번호</th>
										<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">수거 접수일</th>
										<th style="width:7%; text-align:center;">문의</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!empty vo}">
											<c:forEach items="${vo}" var="test" varStatus="status">
												<tr>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														${status.count}
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														<c:if test="${test.state eq 'A000'}">
															1차 접수
														</c:if>
														<c:if test="${test.state eq 'A001'}">
															접수신청
														</c:if>
														<c:if test="${test.state eq 'A002'}">
															접수완료
														</c:if>
														<c:if test="${test.state eq 'B001'}">
															수거중
														</c:if>
														<c:if test="${test.state eq 'C001'}">
															입고
														</c:if>
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														<a href='/return/orderInfo/${test.nno}' style="font-weight:bold;">${test.koblNo}</a>
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														<%-- <a href='/return/info/${test.orderReference}' style="font-weight:bold;">${test.orderNo}</a> --%>
														<a href='/return/orderInfo/${test.nno}' style="font-weight:bold;">${test.orderNo}</a>
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														${test.dstnNation}
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														<c:if test="${test.returnType eq 'E'}">긴급</c:if>
														<c:if test="${test.returnType eq 'N'}">일반</c:if>
													</td>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
														${test.registDate}
													</td>
													<c:if test="${!empty test.reTrkCom}">
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															${test.reTrkCom}
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															${test.reTrkNo}
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															${test.reTrkDate}
														</td>
													</c:if>
													<c:if test="${empty test.reTrkCom}">
														<td colspan="3" style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															-
														</td>
													</c:if>
													<td style="text-align:center; height:40px; border-top:1px solid #ccc;">
														<c:if test="${!empty test.readYn && test.readYn eq 'Y'}">
															<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${test.nno}', '${test.orgStation}', '${test.sellerId}')">답변완료</label>
														</c:if>
														<c:if test="${!empty test.readYn && test.readYn eq 'N'}">
															<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${test.nno}', '${test.orgStation}', '${test.sellerId}')">접수완료</label>
														</c:if>
														<c:if test="${empty test.readYn}">
															<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${test.nno}', '${test.orgStation}', '${test.sellerId}')">등록</label>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="10" style="text-align:center; height:40px; border-top:1px solid #ccc;">
													조회 결과가 없습니다
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<br/>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</div>
			</div>
		</form>
		<br/>
		<div>
	</div>
	
	<div class="modal modal-center fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-center" id="addModal">
			<div class="modal-content">
				<button class="normal-btn" type="button">
				        자사 배송
				    </button>
				    <button class="return-btn" type="button">
						타사 배송
				    </button>
				<!-- <div class="modal-header" style="height:60px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">배송 구분</h4>
				</div>
				<div class="modal-body" style="width:100%; text-align:center; height:100px;">
					<div class="row">
						<button class="normal-btn" type="button">
					        자사 배송
					    </button>&nbsp;&nbsp;&nbsp;
					    <button class="return-btn" type="button">
							타사 배송
					    </button>
				    </div>
				</div> -->
			</div>
		</div>
	</div>
	
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
		jQuery(function($) {
			
		});

		$("#delBtn").on("click",function(e){
			$("#returnForm").attr("action","/return/list");
			$("#returnForm").attr("method", "post");
			$("#returnForm").submit();
			alert('삭제되었습니다.');
		});

		function fnAcceptReturn(orderReference){
			$("#acceptType").val("accept");
			//$("#_method").val("POST");
			$("#returnForm").attr("action","/return/list/"+orderReference);
			$("#returnForm").attr("method", "post");
            $("#returnForm").submit();
		} 

		function fnUnAcceptReturn(orderReference){
			$("#acceptType").val("unaccept");
			//$("#_method").val("POST");
			$("#returnForm").attr("action","/return/list/"+orderReference);
			$("#returnForm").attr("method", "post");
            $("#returnForm").submit();
		}

		function fnUpdateStatus(orderReference){
			console.log(orderReference);
			$("#returnForm").attr("action","/return/status/"+orderReference);
			$("#returnForm").attr("method", "post");
            $("#returnForm").submit();
		}

		function fn_popupMsg(nno, orgStation, userId) {
			window.open("/return/popupMsg?nno="+nno+"&orgStation="+orgStation+"&userId="+userId, "PopupWin", "width=750, height=700");
		}

		function fn_returnKind() {
			//$("#myModal").modal();
			location.href = "/return/regist";
		}

		$(".normal-btn").on('click', function() {
			location.href = "/return/returnCourier";
		});

		$(".return-btn").on('click', function() {
			location.href = "/return/returnOthers";
		})
		
		</script>


</body>
</html>