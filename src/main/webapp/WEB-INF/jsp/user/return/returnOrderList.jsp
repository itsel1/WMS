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
		* {
	font-family: arial, sans-serif;
}

body {
	margin-left:10px;
}

.sendBtn {
	font-weight:bold;
	border:none;
	width:50px;
}

.sendBtn:hover {
	background:black;
	color:white;
}
/*
.registReady {
	background:#fffd8a;
}

.registReadyCom {
	background:#8aefff;
}

.collecting {
	background:#908aff;
}

.stables {
	background:#92ff8a
}

.fails{
	background:#f24d1b
}
*/
	
.colorBlack {
	color: #000000 !important;
}
.noneArea {
	margin:0px !important;
	padding:0px !important;
	border-bottom : none;
}
.containerTable {
	width: 100% !important;
	table-layout:fixed;
	margin:auto;
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
						<div id="search-div">
							<br>
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width:100%">
										<select name="option" style="height: 34px">
											<option <c:if test="${option eq 'all'}"> selected </c:if> value="all">ALL</option>
											<option <c:if test="${option eq 'koblNo'}"> selected </c:if> value="hawbNo">국내송장번호</option>
										</select>
										<select name="select" id="select" style="height: 34px">
											<option <c:if test="${select eq 'selectAll'}"> selected </c:if> value="selectAll">::전체::</option>
											<option <c:if test="${select eq 'A000'}"> selected </c:if> value="A000">1차접수</option>
											<option <c:if test="${select eq 'A001'}"> selected </c:if> value="A001">접수신청</option>
											<option <c:if test="${select eq 'A002'}"> selected </c:if> value="A002">접수완료</option>
											<option <c:if test="${select eq 'B001'}"> selected </c:if> value="B001">수거중</option>
											<option <c:if test="${select eq 'C001'}"> selected </c:if> value="C001">입고</option>
											<option <c:if test="${select eq 'C002'}"> selected </c:if> value="C002">출고</option>
											<option <c:if test="${select eq 'D001'}"> selected </c:if> value="D001">취소</option>
											<option <c:if test="${select eq 'D002'}"> selected </c:if> value="D002">접수반려</option>
										</select>
										<input type="text" class="form-control" name="keywords" value="${keywords}" style="width:100%; max-width: 300px"/>
										<button type="submit" id="srchKeyword" class="btn btn-sm btn-white"><i class="ace-icon fa fa-search icon-on-right bigger-100"></i></button>
										<!-- <button id="srchKeyword" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button> -->
									</span>
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
						</div>
					</div>
					<div style="margin-top:4px;">
						<table id="dynamic-table" class="table table-bordered containerTable">
							<thead>
								<tr height="30" >
									<th rowspan="2" class="center" style="width:60px;">NO</th>
									<th rowspan="2" class="center" style="width:100px;">진행상황</th>
									<th rowspan="2" class="center">원운송장번호</th>
									<!-- <th rowspan="2" class="center">ORDER NO</th> -->
									<th rowspan="2" class="center" style="width:100px;">반송유형</th>
									<th rowspan="2" class="center">등록일</th>
									<th colspan="3" class="center">수거 택배</th>
									<th colspan="2" class="center">발송정보</th>
									<th colspan="3" class="center">문의사항</th>
									<th rowspan="2" class="center">출고 승인</th>
								</tr>
								<tr height="30">
									<th class="center">택배사</th>
									<th class="center">택배번호</th>
									<th class="center">접수일</th>
									<th class="center">택배사</th>
									<th class="center">택배번호</th>
									<th class="center">고객 메세지</th>
									<th class="center">관리자 메세지</th>
									<th style="width:60px;"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${vo}" var="test" varStatus="status">
									<tr>
										<td class="center"><c:out value="${status.count}"/></td>
										<c:choose>
											<c:when test="${test.state eq 'A000'}">
												<td class="center colorBlack registReady" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:when test="${test.state eq 'A001'}">
												<td class="center colorBlack registReady" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:when test="${test.state eq 'A002'}">
												<td class="center colorBlack registReadyCom" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:when test="${test.state eq 'B001'}">
												<td class="center colorBlack collecting" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:when test="${test.state eq 'D004'}">
												<td class="center colorBlack fails" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:when test="${test.state eq 'C004'}">
												<td class="center colorBlack stables" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:when>
											<c:otherwise>
												<td class="center colorBlack" style="word-break:break-all;">
													${test.stateKr}
												</td>
											</c:otherwise>
										</c:choose>
										<td class="center colorBlack" style="word-break:break-all;">
											<c:choose>
												<c:when test="${test.state eq 'A001' or test.state eq 'A000' or test.state eq 'A002' or test.state eq 'B001'}">
													<c:if test="${!empty test.koblNo}">
														<a href='/return/info/${test.orderReference}'>${test.koblNo}</a>
													</c:if>
													<c:if test="${empty test.koblNo}">
														<a href='/return/info/${test.orderReference}'>-</a>
													</c:if>
												</c:when>
												<c:when test="${test.state eq 'C003' or test.state eq 'C004' or test.state eq 'C002'}">
													<c:if test="${!empty test.koblNo}">
														<a href='/return/return/C002/${test.orderReference}'>${test.koblNo}</a>
													</c:if>
													<c:if test="${empty test.koblNo}">
														<a href='/return/return/C002/${test.orderReference}'>-</a>
													</c:if>
												</c:when>
												<c:when test="${test.state eq 'D005' or test.state eq 'D004'}">
													<c:if test="${!empty test.koblNo}">
														<a href='/return/return/D005/${test.orderReference}'>${test.koblNo}</a>
													</c:if>
													<c:if test="${empty test.koblNo}">
														<a href='/return/return/D005/${test.orderReference}'>${test.koblNo}</a>
													</c:if>
												</c:when>
												<c:otherwise>
													${test.koblNo}
												</c:otherwise> 
											</c:choose>
										</td>
										<td class="center colorBlack">
											<c:if test="${test.returnType eq 'E'}">긴급</c:if>
											<c:if test="${test.returnType eq 'N'}">일반</c:if>
										</td>
										<td class="center colorBlack">
											${test.registDate}
										</td>
										<td class="center colorBlack">
											${test.reTrkCom}
										</td>
										<td class="center colorBlack" style="word-break:break-all;">
											${test.reTrkNo}<!-- 수거택배번호 -->
										</td> 
										<td class="center colorBlack" style="word-break:break-all;">
											${test.reTrkDate}<!-- 수거접수일 -->
										</td> 
										<td class="center colorBlack" style="word-break:break-all;">
											${test.transCode}<!-- 발송택배사 -->
										</td> 
										<td class="center colorBlack" style="word-break:break-all;">
											${test.hawbNo}<!-- 발송택배번호 -->
										</td> 
										<td class="center colorBlack" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
											${test.userMsg}
										</td> 
										<td class="center colorBlack" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
											${test.adminMsg}
										</td>
										<td class="center colorBlack">
											<label style="cursor:pointer; color:#46649B;" onclick="fn_popupMsg('${test.nno}', '${test.orgStation}', '${test.sellerId}')">등록</label>
										</td> 
										<td style="text-align: center;">
											<input type="hidden" id="acceptType" name="acceptType" value=""/>
											<c:if test="${test.pickType eq 'A' and test.state eq 'A002'}">
												<input type="button" class="sendBtn" id="updateStatus" name="updateStatus" onclick="fnUpdateStatus('${test.orderReference}')" value="발송">
											</c:if>
											<c:if test="${test.state eq 'C003' }">
												<input type="button" class="sendBtn" id="acceptReturn" name="acceptReturn" onclick="fnAcceptReturn('${test.orderReference}')" value="승인">
											</c:if>
											<c:if test="${test.state eq 'C004' }">
												<input type="button" class="sendBtn" id="unAcceptReturn" name="unAcceptReturn" onclick="fnUnAcceptReturn('${test.orderReference}')" value="대기">
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>
		<br/>
		<div>
			<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
			$(document).ready(function() {

			});

			/*
			 $("#chkAll").click(function() {
	               if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다

	                   $("input[name='nno']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
	               } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
	                   $("input[name='nno'").prop("checked",false);
	               }
	           });

*/
			 
			 
			 
			 /*
			 $("#srchKeyword").click(()=>{
				 let selectValue = $("#select").val();
				 alert(selectValue);
				 $.ajax("/mngr/aplctList/return/getOption?select="+selectValue).done(result=>{
					 console.log('success');
				 })
			 }); */
			 
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

		</script>


</body>
</html>