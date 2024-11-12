<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<meta charset="UTF-8">
<title>취소 및 폐기 내역</title>

<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<style type="text/css">
	 .colorBlack {color:#000000 !important;}
	  
	  .addBtn {
	  	background: #3296FF;
	   	height: 25px;
	   	color: white;
	   	border: none;
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
	  
	  
	  .addBtn:hover {
	  	background: #50B4FF;
	  }
	  
	  .delBtn {
	  	background: white;
	  	height: 35px;
	   	width: 80px;
	   	border: 1px solid #3296FF;
	  }
	  
	  .delBtn:Hover {
	  	background: #B8D7FF;
	  	color: white;
	  	border: none;
	  }
	  
	  .modal.modal-center {
		  text-align: center;
		}
		
		#addCost:focus {
			border: 1px solid #3296FF;
		}
		
		#etcDetail:focus {
			border: 1px solid #3296FF;
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
		 	width: 20%;
		 }
		 
		 #delModal {
			width: 20%; 
		}
		 
		 .modal-footer {
		 	background:white;
		 }
		 
		 #select:focus {
		 	border: 2px solid #3296FF;
		 }
		 
		 #koblNo:focus {
		 	border: 1px solid #3296FF;
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
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품 폐기 내역</strong>
						</div>
		        	</div>
		      	</div>
		    </div>
	    </div>
		<form name="returnForm" id="returnForm">
			<div class="container">
		       <div class="page-content noneArea">
					<div id="inner-content-side" >
						<br/>
						<h2 style="margin-left:4px;">반품 폐기 내역</h2>
						<br/><br/>
						<div style="margin-left:20px; margin-bottom:10px; width:100%; vertical-align:middle;">
							<select id="inspType" name="inspType" style="height:34px; width:100px;">
								<option <c:if test="${params.inspType == 'all'}"> selected</c:if> value="all">::전체::</option>
								<option <c:if test="${params.inspType == 'D001'}"> selected</c:if> value="D001">취소</option>
								<option <c:if test="${params.inspType == 'D005'}"> selected</c:if> value="D005">폐기</option>
							</select>
							<input type="text" style="width:220px;" name="koblNo" autocomplete="off" id="koblNo" value="${params.koblNo}" placeholder="원운송장번호를 입력하세요" />
							<button type="submit" style="height:34px; width:60px;" id="searchBtn">조회</button>
						</div>
						<div style="width:100%;">
							<div style="border-radius:10px; border:1px solid #dcdcdc; width:100%; padding:10px; margin-left:10px;">
								<table style="width:100%;">
									<thead style="border-bottom:2px double #ccc;">
										<tr>
											<th style="width:5%; height:50px; text-align:center; border-right:1px dotted #ccc;">No</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반송상태</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반송유형</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">원운송장번호</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">주문번호</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">등록일</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반송 택배사</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반송 송장번호</th>
											<th style="width:10%; text-align:center; border-right:1px dotted #ccc;">반송 접수일</th>
											<th style="width:7%; text-align:center;">문의</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!empty inspList}">
												<c:forEach items="${inspList}" var="list" varStatus="status">
													<tr>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${status.count}</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															<c:if test="${list.STATE eq 'D001'}">
																취소
															</c:if>
															<c:if test="${list.STATE eq 'D005'}">
																폐기
															</c:if>
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															<c:if test="${list.RETURN_TYPE eq 'N'}">
																일반배송
															</c:if>
															<c:if test="${list.RETURN_TYPE eq 'E'}">
																긴급배송
															</c:if>
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															<a href="/return/return/D005/${list.ORDER_REFERENCE}" style="font-weight:bold;">${list.KOBL_NO}</a>
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">
															<a href="/return/return/D005/${list.ORDER_REFERENCE}" style="font-weight:bold;">${list.ORDER_NO}</a>
														</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${fn:split(list.W_DATE,'.')[0]}</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${list.RE_TRK_COM}</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${list.RE_TRK_NO}</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc; border-right:1px dotted #ccc;">${fn:split(list.A002_DATE,'.')[0]}</td>
														<td style="text-align:center; height:40px; border-top:1px solid #ccc;">
															<c:if test="${!empty list.READ_YN && list.READ_YN eq 'Y'}">
																<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${list.NNO}', '${list.ORG_STATION}', '${list.SELLER_ID}')">답변완료</label>
															</c:if>
															<c:if test="${!empty list.READ_YN && list.READ_YN eq 'N'}">
																<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${list.NNO}', '${list.ORG_STATION}', '${list.SELLER_ID}')">접수완료</label>
															</c:if>
															<c:if test="${empty list.READ_YN}">
																<label style="cursor:pointer; color:#46649B; font-weight:bold;" onclick="fn_popupMsg('${list.NNO}', '${list.ORG_STATION}', '${list.SELLER_ID}')">등록</label>
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="9" style="text-align:center; height:40px; border-top:1px solid #ccc;">
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
			</div>
		</form>
		<br/>
	
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
	
	
	<script src="/assets/js/bootstrap-datepicker.min.js"></script>
	<!-- script on paging end -->
	
	<!-- script addon start -->
	<script type="text/javascript">
	
		function fn_popupMsg(nno, orgStation, userId) {
			window.open("/return/popupMsg?nno="+nno+"&orgStation="+orgStation+"&userId="+userId, "PopupWin", "width=750, height=700");
		}
		

		
	</script>


</body>
</html>