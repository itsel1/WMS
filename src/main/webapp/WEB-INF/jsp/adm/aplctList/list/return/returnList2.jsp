<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>
	.button {
	  background-color: rgb(67,142,185); /* Green */
	  border: none;
	  color: white;
	  padding: 0px;
	  text-align: center;
	  text-decoration: none;
	  display: inline-block;
	  font-size: 12px;
	  margin: 4px 2px;
	  cursor: pointer;
	  -webkit-transition-duration: 0.4s; /* Safari */
	  transition-duration: 0.4s;
	  width: 100px;
	  height: 30px;
	}
	
	.button2:hover {
	  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
	}
	</style>
	
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>반품 검수 리스트</title>
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
							<li class="active">반품 검수 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 검수 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm" action="/mngr/aplctList/return/returnList2" method="GET">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div id="search-div">
								<br>
								<div class="row">
									<div class="col-xs-12 col-sm-11 col-md-10">
										<span class="input-group" style="display: inline-flex; width:100%">
											<select name="option" style="height: 34px">
												<option <c:if test="${option eq 'all'}"> selected </c:if> value="all">ALL</option>
												<option <c:if test="${option eq 'userId'}"> selected </c:if> value="userId">USER ID</option>
												<option <c:if test="${option eq 'hawbNo'}"> selected </c:if> value="hawbNo">송장번호</option>
											</select>
											<select name="select" id="select" style="height: 34px">
													<option <c:if test="${select eq 'selectAll'}"> selected </c:if> value="selectAll">반송상태를 선택하세요</option>
													<option <c:if test="${select eq '접수'}"> selected </c:if> value="접수">접수</option>
													<option <c:if test="${select eq '수거중'}"> selected </c:if> value="수거중">수거중</option>
													<option <c:if test="${select eq '입고'}"> selected </c:if> value="입고">입고</option>
													<option <c:if test="${select eq '출고'}"> selected </c:if> value="출고">출고</option>
													<option <c:if test="${select eq '취소'}"> selected </c:if> value="취소">취소</option>
											</select>

											<input type="text" class="form-control" name="keywords" value="${keywords}" style="width:100%; max-width: 300px"/>
												<button id="srchKeyword" class="btn btn-default no-border btn-sm">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>			
										</span>
										
									</div>
								</div>
								
								<br>
								<div id="delete-user" style="text-align: right">
									<input type="button" id="delBtn" class="btn btn-sm btn-danger" value="삭제하기" onclick="deleteOrder()">
								
								</div>
								
							</div>
							<br>
							<div id="table-contents" class="table-contents" style="width:100%;">
								<div class="display" style="width:1600px;">
									<table id="dynamic-table" class="table table-bordered table-hover">
											<colgroup>
												<col width="1%"/> 
												<col width="3%"/>
												<col width="3%"/>
												<col width="2%"/>
												<col width="2%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												<col width="3%"/>
												
											
											</colgroup>
											<thead >
											<tr>
												<th class="center">
													<label class="pos-rel"> 
														<input type="checkbox" class="ace" id="chkAll" /> 
														<span class="lbl" ></span>
													</label>
												</th>
												<th class="center colorBlack">ORDER NO</th>
												<th class="center colorBlack">HAWB NO</th>
												<th class="center colorBlack">도착 국가</th>
												<th class="center colorBlack">출발 도시</th>
												<th class="center colorBlack">USER ID</th>
												<th class="center colorBlack">ORDER TYPE</th>
												<th class="center colorBlack">ORDER DATE</th>
												<th class="center colorBlack">USER WTC</th>
												<th class="center colorBlack">BOX CNT</th>
												<th class="center colorBlack">USER WTA</th>
												<th class="center colorBlack">반송사유서</th>
												<th class="center colorBlack">통장사본</th>
												<th class="center colorBlack">네이버반품 캡쳐본</th>
												<th class="center colorBlack">TOCKTOCK 캡쳐본</th>
												<th class="center colorBlack">반품 커머셜</th>
												
											</tr>
										</thead>
											<tbody>
													<c:forEach items="${vo}" var="test" varStatus="voStatus">
													
														<tr>
								
															<td class="center" >
															<label class="pos-rel"> 
																<input type="checkbox" name="nno[]" class="form-field-checkbox" value="${test.nno}" /> 
																<span class="lbl"></span>
															</label>
															</td>
															<td class="center colorBlack" style="word-break:break-all;">
																${test.orderNo}
															</td>
															<td class="center colorBlack" style="word-break:break-all;">
																<a href='/mngr/aplctList/return/updateOrder2?nno=${test.nno}'>${test.hawbNo}</a>
															</td>
										
															<td class="center colorBlack" style="word-break:break-all;">
																${test.dstnNation}
															</td>
															<td class="center colorBlack" style="word-break:break-all;">
																${test.orgStation}
															</td>
															<td class="center colorBlack" style="word-break:break-all;">
																${test.userId}
															</td>
															
															<td class="center colorBlack" style="word-break:break-all;">
																${test.returnType}
															</td>
															
															<td class="center colorBlack" style="word-break:break-all;">
																${test.orderDate}
															</td>
															<td class="center colorBlack" style="word-break:break-all;">
																${test.userWta}
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																${test.boxCnt}
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																${test.userWta}
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
												
																<a href='http://localhost:8080/downloadMultiPage?url=${test.rtnReasonUrl}'><c:if test="${test.rtnReasonUrl ne '' }">[반송사유서]</c:if></a>
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																<a href='http://localhost:8080/downloadMultiPage?url=${test.bankBookUrl }'><c:if test="${test.bankBookUrl ne '' }">[통장사본]</c:if></a>
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																<a href='http://localhost:8080/downloadMultiPage?url=${test.naverCapUrl }'><c:if test="${test.naverCapUrl ne '' }">[네이버반품 캡쳐본]</c:if></a>
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																<a href='http://localhost:8080/downloadMultiPage?url=${test.tockTockUrl }'><c:if test="${test.tockTockUrl ne '' }">[TOCKTOCK 캡쳐본]</c:if></a>
															</td> 
															<td class="center colorBlack" style="word-break:break-all;">
																<a href='http://localhost:8080/downloadMultiPage?url=${test.rtnCommUrl }'><c:if test="${test.rtnCommUrl ne '' }">[반품 커머셜]</c:if></a>
															</td> 
														</tr>
													</c:forEach>
												
											</tbody>
										</table>
									</div>
								</div>
								<input type="hidden" value="" id="delTarget" name="delTarget"/>
								<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
							</form>
						</div>
					</div>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		
			function selectOption() {
				 $("#returnForm").attr("action","/mngr/aplctList/return/getOption");
				 $("#returnForm").attr("method","get");
				 $("#returnForm").submit();
				 
			}
			
			// 검수리스트 항목 삭제하기 
			function deleteOrder() {
				 $("#returnForm").attr("action","/mngr/aplctList/return/deleteItem2");
				 $("#returnForm").attr("method","POST");
				 $("#returnForm").submit();
				alert('삭제되었습니다.'); 
			}
			
			
			jQuery(function($) {
				$(document).ready(function() {

					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thTwo-2").toggleClass('active');	
					

					
				});
			
				
				 
				 
				 $("#chkAll").click(function() {
		               if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
		                   $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
		               } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
		                   $("input[name='nno[]'").prop("checked",false);
		               }
		           });
				 
				
				 
			});
			
			
		</script>
		<!-- script addon end -->
	</body>
</html>
